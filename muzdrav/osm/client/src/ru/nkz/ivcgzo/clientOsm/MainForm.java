package ru.nkz.ivcgzo.clientOsm;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm;
import ru.nkz.ivcgzo.thriftOsm.ZapVr;

public class MainForm extends Client<ThriftOsm.Client> {
	public static ThriftOsm.Client tcl;
	public static MainForm instance;
	private static String [] month = {"Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря"};
	private JFrame frame;
	private JButton btnSelect;
	private CustomTable<ZapVr, ZapVr._Fields> table;
	private Vvod vvod;
	private Timer timer;
	private ZapVr prevZapvr;
	private boolean searched;
	private int searchedNpasp;
	
	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(conMan, authInfo, ThriftOsm.Client.class, configuration.appId, configuration.thrPort, lncPrm);
		
		initialize();
		
		setFrame(frame);
		
		instance = this;
		searched = false;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 721, 508);
		
		JScrollPane scrollPane = new JScrollPane();
		
		btnSelect = new JButton("Выбор");
		btnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedItem() != null)
					vvod.showVvod(table.getSelectedItem());
			}
		});
		
		final JButton btnZpr = new JButton("Вернуться к записанным на прием");
		btnZpr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnZpr.setEnabled(false);
				
				searched = false;
				updateZapList();
			}
		});
		btnZpr.setEnabled(false);
		JButton btnSearch = new JButton("Поиск");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] res = conMan.showPatientSearchForm("Поиск пациентов", false, true);
				
				if (res != null) {
					btnZpr.setEnabled(true);
					
					searched = true;
					searchedNpasp = res[0];
					updateZapList();
				}
			}
		});
		
		timer = new Timer(5000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				updateZapList();
			}
		});
		
		JButton btnView = new JButton("Просмотр");
		btnView.setVisible(false);
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (table.getSelectedItem() != null)
				MainForm.conMan.showPatientInfoForm(String.format("Просмотр информации на пациента %s %s %s", table.getSelectedItem().fam, table.getSelectedItem().im, table.getSelectedItem().oth), table.getSelectedItem().npasp);
			}
		});
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnSearch)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSelect)
							.addGap(18)
							.addComponent(btnView)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnZpr)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSearch)
						.addComponent(btnSelect)
						.addComponent(btnView)
						.addComponent(btnZpr))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18))
		);
		
		table = new CustomTable<>(false, true, ZapVr.class, 9, "Посл. посещение", 1, "Фамилия", 2, "Имя", 3, "Отчество", 4, "Серия полиса", 5 ,"Номер полиса");
		table.setDateField(0);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					btnSelect.doClick();
			}
		});
		ZapVrCellRenderer zcr = new ZapVrCellRenderer();
		table.setDefaultRenderer(String.class, zcr);
		table.setDefaultRenderer(Date.class, zcr);
			table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		frame.getContentPane().setLayout(groupLayout);
	}

	@Override
	public String getName() {
		return configuration.appName;
	}

	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		super.onConnect(conn);
		if (conn instanceof ThriftOsm.Client) {
			tcl = thrClient;
			updateZapList();
			if (vvod == null) {
				vvod = new Vvod();
				addChildFrame(vvod);
			}
			vvod.onConnect();
		}
	}
	
	public void updateZapList() {
		if (!searched) {
			Calendar calendar = GregorianCalendar.getInstance();
			calendar.setTimeInMillis(System.currentTimeMillis());
			frame.setTitle("Записанные на прием на "+calendar.get(Calendar.DATE)+" "+month[calendar.get(Calendar.MONTH)]+" "+calendar.get(Calendar.YEAR)+" г.");
		} else {
			frame.setTitle("Найденные пациенты");
		}
		
		try {
			prevZapvr = table.getSelectedItem();
			if (!searched)
				table.setData(tcl.getZapVr(authInfo.getPcod(), authInfo.getCdol(), System.currentTimeMillis()));
			else
				table.setData(tcl.getZapVrSrc(searchedNpasp, authInfo.pcod, authInfo.cdol));
			if (prevZapvr != null)
				for (int i = 0; i < table.getData().size(); i++)
					if (table.getData().get(i).id_pvizit == prevZapvr.id_pvizit) {
						table.changeSelection(i, 0, false, false);
						break;
					}
		} catch (KmiacServerException e) {
			e.printStackTrace();
		} catch (TException e) {
			conMan.reconnect(e);
		}
		
		timer.restart();
	}
	
	public void setVisible(boolean value) {
		frame.setVisible(value);
	}
	
	private class ZapVrCellRenderer extends DefaultTableCellRenderer {
		private static final long serialVersionUID = -4802283599161874163L;
		private Color defCol = UIManager.getColor("Table.background");
		private Color selCol = UIManager.getColor("Table.selectionBackground");
		private SimpleDateFormat dateFrm = new CustomDateEditor().getDateFormatter();
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Component cmp = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			
			if (cmp instanceof JLabel) {
				JLabel lbl = (JLabel) cmp;
				
				if (searched && MainForm.this.table.getData().get(row).id_pvizit > 0) {
					if (isSelected) {
						lbl.setBackground(selCol.darker());
					} else {
						lbl.setBackground(Color.gray.brighter());
					}
				} else {
					if (isSelected) {
						lbl.setBackground(selCol);
					} else {
						lbl.setBackground(defCol);
					}
				}
				
				if (value instanceof Date)
					lbl.setText(dateFrm.format(value));
			}
			
			return cmp;
		}
	}
}
