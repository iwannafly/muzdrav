package ru.nkz.ivcgzo.clientOsm;

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
import ru.nkz.ivcgzo.clientManager.common.IClient;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientOsm.patientInfo.Classifiers;
import ru.nkz.ivcgzo.clientOsm.patientInfo.PInfo;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm;
import ru.nkz.ivcgzo.thriftOsm.ZapVr;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
						.addComponent(btnNewButton))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnNewButton)
					.addContainerGap())
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
				if (!Classifiers.load(tcl)) {
					JOptionPane.showMessageDialog(frame, "Ошибка загрузки классификаторов", "Необработанная ошибка", JOptionPane.ERROR_MESSAGE);
					return;
				}
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
