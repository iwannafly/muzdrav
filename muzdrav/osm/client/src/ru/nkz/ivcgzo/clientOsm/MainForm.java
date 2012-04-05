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
import javax.swing.JScrollPane;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm;
import ru.nkz.ivcgzo.thriftOsm.ZapVr;

public class MainForm extends Client {
	public static ThriftOsm.Client tcl;
	private JFrame frame;
	private CustomTable<ZapVr, ZapVr._Fields> table;
	private Vvod vvod;
	private UserAuthInfo authInfo;

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
		this.authInfo = authInfo;
		
		initialize();
		if (conMan != null) {
			conMan.add(ThriftOsm.Client.class, configuration.thrPort);
			conMan.setLocalForm(frame);
		} else //такой подход рекомендуется только на начальных этапах разработки
			try {
				TTransport transport = new TFramedTransport(new TSocket("localhost", configuration.thrPort));
				transport.open();
				onConnect(new ThriftOsm.Client(new TBinaryProtocol(transport)));
			} catch (TTransportException e) {
				e.printStackTrace();
				System.exit(1);
			}
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton btnNewButton = new JButton("Выбор");
		btnNewButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				vvod.showVvod(authInfo, table.getSelectedItem());
			}
		});
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
						.addComponent(btnNewButton))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnNewButton)
					.addContainerGap())
		);
		
		table = new CustomTable<>(false, true, ZapVr.class, ZapVr._Fields.values(), 3,"Фамилия",4, "Имя", 5, "Отчество",6,"Серия полиса",7,"Номер полиса");
		table.setFillsViewportHeight(true);
		scrollPane.setViewportView(table);
		frame.getContentPane().setLayout(groupLayout);
		
		vvod = new Vvod();
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
				table.setData(tcl.getZapVr(6, "3", SimpleDateFormat.getDateInstance().parse("27.03.2012").getTime()));
			} catch (TException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
