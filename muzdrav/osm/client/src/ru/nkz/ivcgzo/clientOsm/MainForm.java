package ru.nkz.ivcgzo.clientOsm;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientOsm.patientInfo.Classifiers;
import ru.nkz.ivcgzo.clientOsm.patientInfo.PatientInfoViewMainForm;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm;
import ru.nkz.ivcgzo.thriftOsm.ZapVr;

public class MainForm extends Client {
	public static ThriftOsm.Client tcl;
	public static ConnectionManager conMan;
	private JFrame frame;
	private CustomTable<ZapVr, ZapVr._Fields> table;
	private Vvod vvod;
	public static UserAuthInfo authInfo;
	
	private PatientInfoViewMainForm patInfoView;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainForm(null, null, 0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(conMan, authInfo, lncPrm);
		
		conMan.add(ThriftOsm.Client.class, configuration.thrPort);
		conMan.setLocalForm(frame);
		
		this.authInfo = authInfo;
		this.conMan = conMan;
		
		initialize();
		this.conMan.setLocalForm(frame);
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 450, 300);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnNewButton = new JButton("Выбор");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				vvod.showVvod(authInfo, table.getSelectedItem());
			}
		});
		
		JButton btnPatInfoView = new JButton("Информация");
		btnPatInfoView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tcl.getServerVersion();
				patInfoView.showForm(tcl, table.getSelectedItem().npasp);
				} catch (TException e1) {
					conMan.reconnect(e1);
				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnPatInfoView, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnPatInfoView))
					.addContainerGap())
		);
		
		table = new CustomTable<>(false, true, ZapVr.class, 3,"Фамилия",4, "Имя", 5, "Отчество",6,"Серия полиса",7,"Номер полиса");
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		frame.getContentPane().setLayout(groupLayout);
		
		vvod = new Vvod();
		patInfoView = new PatientInfoViewMainForm(conMan, authInfo, lncPrm);
	}

	@Override
	public String getVersion() {
		return configuration.appVersion;
	}

	@Override
	public int getId() {
		return configuration.appId;
	}

	@Override
	public String getName() {
		return configuration.appName;
	}

	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		if (conn instanceof ThriftOsm.Client) {
			tcl = (ThriftOsm.Client) conn;
			try {
				if (!Classifiers.load(tcl)) {
					JOptionPane.showMessageDialog(frame, "Ошибка загрузки классификаторов", "Необработанная ошибка", JOptionPane.ERROR_MESSAGE);
					return;
				}
				table.setData(tcl.getZapVr(6, "3", SimpleDateFormat.getDateInstance().parse("27.03.2012").getTime()));
				vvod.onConnect();
				Classifiers.load(tcl);
			} catch (KmiacServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
