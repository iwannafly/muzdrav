package ru.nkz.ivcgzo.clientOsm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm;
import ru.nkz.ivcgzo.thriftOsm.ZapVr;

public class MainForm extends Client<ThriftOsm.Client> {
	public static ThriftOsm.Client tcl;
	public static Client<ThriftOsm.Client> instance;
	private JFrame frame;
	public static  CustomTable<ZapVr, ZapVr._Fields> table;
	private Vvod vvod;
	
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
		String [] month = {"Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря"};
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		frame.setTitle("Записанные на прием на "+calendar.get(Calendar.DATE)+" "+month[calendar.get(Calendar.MONTH)]+" "+calendar.get(Calendar.YEAR)+" г.");
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				//frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				}
		});
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 721, 508);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnNewButton = new JButton("Выбор");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (table.getSelectedItem() != null)
					vvod.showVvod(table.getSelectedItem());
			}
		});
		
		final JCheckBox cbzpr = new JCheckBox("Вернуться к записанным на прием");
		cbzpr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String [] month = {"Января", "Февраля", "Марта", "Апреля", "Мая", "Июня", "Июля", "Августа", "Сентября", "Октября", "Ноября", "Декабря"};
					Calendar calendar = GregorianCalendar.getInstance();
					calendar.setTimeInMillis(System.currentTimeMillis());
					frame.setTitle("Записанные на прием на "+calendar.get(Calendar.DATE)+" "+month[calendar.get(Calendar.MONTH)]+" "+calendar.get(Calendar.YEAR)+" г.");

					table.setData(tcl.getZapVr(6,"3", SimpleDateFormat.getDateInstance().parse("30.01.2012").getTime()));
				} catch (KmiacServerException e) {
					e.printStackTrace();
				} catch (TException e) {
					conMan.reconnect(e);
				} catch (ParseException e) {
					e.printStackTrace();
				}

			}
		});
		cbzpr.setEnabled(false);
		JButton btnSearch = new JButton("Поиск");
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] res = conMan.showPatientSearchForm("Поиск пациентов", false, false);
				
				try {
					if (res == null){
						cbzpr.setEnabled(false);
						table.setData(tcl.getZapVr(6,"3", SimpleDateFormat.getDateInstance().parse("30.01.2012").getTime()));}
					else{
						frame.setTitle("Найденные пациенты");
						cbzpr.setEnabled(true);
						table.setData(tcl.getZapVrSrc(Arrays.toString(res).replace('[', '(').replace(']', ')')));
					}
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
					conMan.reconnect(e1);
				} catch (ParseException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JButton btnView = new JButton("Просмотр");
		btnView.setVisible(false);
		btnView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MainForm.conMan.showPatientInfoForm(table.getSelectedItem().getNpasp());
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
							.addComponent(btnNewButton)
							.addGap(18)
							.addComponent(btnView)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(cbzpr)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSearch)
						.addComponent(btnNewButton)
						.addComponent(btnView)
						.addComponent(cbzpr))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18))
		);
		
		table = new CustomTable<>(false, true, ZapVr.class, 3,"Фамилия",4, "Имя", 5, "Отчество",6,"Серия полиса",7,"Номер полиса");
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
			try {
				//table.setData(tcl.getZapVr(authInfo.getPcod(),authInfo.getCdol(), SimpleDateFormat.getDateInstance().parse("30.01.2012").getTime()));
				table.setData(tcl.getZapVr(6,"3", SimpleDateFormat.getDateInstance().parse("30.01.2012").getTime()));
				if (vvod == null) {
					vvod = new Vvod();
					addChildFrame(vvod);
				}
				
				vvod.onConnect();
			} catch (KmiacServerException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TException e) {
				e.printStackTrace();
				conMan.reconnect(e);
			}
		}
	}
}
