package ru.nkz.ivcgzo.clientOsm;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
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
	private JFrame frame;
	private JButton btnSelect;
	private JScrollPane spPos;
	private CustomTable<ZapVr, ZapVr._Fields> tblPos;
	private Vvod vvod;
	private Timer timer;
	private ZapVr prevZapvr;
	private CustomDateEditor tfDatZap;
	private JButton btnOk;
	
	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(conMan, authInfo, ThriftOsm.Client.class, configuration.appId, configuration.thrPort, lncPrm);
		
		initialize();
		
		setFrame(frame);
		
		instance = this;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 721, 508);
		
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				timer.stop();
			}
		});
		
		spPos = new JScrollPane();
		
		btnSelect = new JButton("Выбор");
		btnSelect.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				tfDatZap.setVisible(true);
				btnOk.setVisible(true);
				if (tblPos.getSelectedItem() != null)
					vvod.showVvod(tblPos.getSelectedItem());
			}
		});
		JButton btnSearch = new JButton("Поиск");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int[] res = conMan.showPatientSearchForm("Поиск пациентов", false, true);
					
					if (res != null) {
						vvod.showVvod(MainForm.tcl.getZapVrSrc(res[0]));
					}
				} catch (KmiacServerException e1) {
					JOptionPane.showMessageDialog(frame, "Не удалось загрузить информацию на пациента.", "Ошибка", JOptionPane.ERROR_MESSAGE);
				} catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, "Не удалось отобразить форму поиска.", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
				if (tblPos.getSelectedItem() != null)
					MainForm.conMan.showPatientInfoForm(String.format("Просмотр информации на пациента %s %s %s", tblPos.getSelectedItem().fam, tblPos.getSelectedItem().im, tblPos.getSelectedItem().oth), tblPos.getSelectedItem().npasp);
			}
		});
		
		tfDatZap = new CustomDateEditor();
		tfDatZap.setDate(System.currentTimeMillis());
		tfDatZap.setColumns(10);
		
		 btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			updateZapList();
			}
		});
		
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(spPos, GroupLayout.DEFAULT_SIZE, 693, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnSearch)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSelect)
							.addGap(18)
							.addComponent(btnView)
							.addGap(29)
							.addComponent(tfDatZap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnOk)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSearch)
						.addComponent(btnSelect)
						.addComponent(btnView)
						.addComponent(tfDatZap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnOk))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spPos, GroupLayout.DEFAULT_SIZE, 189, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tblPos = new CustomTable<>(false, true, ZapVr.class, 9, "Посл. посещение", 1, "Фамилия", 2, "Имя", 3, "Отчество", 4, "Серия полиса", 5 ,"Номер полиса");
		tblPos.setDateField(0);
		tblPos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					btnSelect.doClick();
			}
		});
		ZapVrCellRenderer zcr = new ZapVrCellRenderer();
		tblPos.setDefaultRenderer(String.class, zcr);
		tblPos.setDefaultRenderer(Date.class, zcr);
			tblPos.setFillsViewportHeight(true);
		spPos.setViewportView(tblPos);
		
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
			if (vvod == null) {
				vvod = new Vvod(authInfo.priznd == 7);
				addChildFrame(vvod);
			}
			vvod.onConnect();
			if (authInfo.priznd != 7)
				updateZapList();
			else
				vvod.showVvod(new ZapVr());
		}
	}
	
	public void updateZapList() {
		frame.setTitle("Записанные на прием на "+DateFormat.getDateInstance().format(new Date(tfDatZap.getDate().getTime()))+" г.");
		
		try {
			prevZapvr = tblPos.getSelectedItem();
			tblPos.setData(tcl.getZapVr(authInfo.getPcod(), authInfo.getCdol(), tfDatZap.getDate().getTime(), authInfo.getCpodr()));
			if (prevZapvr != null)
				for (int i = 0; i < tblPos.getData().size(); i++)
					if (tblPos.getData().get(i).id_pvizit == prevZapvr.id_pvizit) {
						tblPos.changeSelection(i, 0, false, false);
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
		if (value && (authInfo.priznd != 7))
			timer.restart();
		else
			timer.stop();
		
		frame.setVisible(value);
	}
	
	public void close() {
		frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
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
				
				if (MainForm.this.tblPos.getData().get(row).hasPvizit) {
					if (isSelected)
						lbl.setBackground(selCol.darker());
					else
						lbl.setBackground(Color.green.brighter());
				} else {
					if (isSelected)
						lbl.setBackground(selCol);
					else
						lbl.setBackground(defCol);
				}
				
				if (value instanceof Date)
					lbl.setText(dateFrm.format(value));
			}
			
			return cmp;
		}
	}
}
