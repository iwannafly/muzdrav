package ru.nkz.ivcgzo.clientPbol;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftPbol.Pbol;
import ru.nkz.ivcgzo.thriftPbol.ThriftPbol;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

public class MainForm extends Client<ThriftPbol.Client>{
	public static ThriftPbol.Client tcl;
	public static MainForm instance;
	private JFrame frame;
	private CustomTable<Pbol, Pbol._Fields> tblPbol;

	public MainForm (ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(conMan, authInfo, ThriftPbol.Client.class, configuration.appId, configuration.thrPort, lncPrm);
		initialize();
		
		setFrame(frame);
		
		JScrollPane spPbol = new JScrollPane();
		
		JButton button = new JButton("+");
		
		JButton button_1 = new JButton("-");
		
		JButton btnV = new JButton("v");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(spPbol, GroupLayout.DEFAULT_SIZE, 967, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(button)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(button_1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnV)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(spPbol, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(button)
						.addComponent(button_1)
						.addComponent(btnV))
					.addContainerGap(412, Short.MAX_VALUE))
		);
		
		tblPbol = new CustomTable<>(true, true, Pbol.class, 3,"Причина открытия б/л",4,"Дата открытия б/л",5,"Дата закрытия б/л",6,"Пол (по уходу)",7,"Возраст (по уходу)",8,"Номер б/л") ;
		tblPbol.setFillsViewportHeight(true);
		spPbol.setViewportView(tblPbol);
		frame.getContentPane().setLayout(groupLayout);
		
	}

	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Больничный лист");
		frame.setBounds(100, 100, 995, 737);
	}

	@Override
	public String getName() {
		return configuration.appName;
	}
	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		super.onConnect(conn);
		if (conn instanceof ThriftPbol.Client) {
			tcl = thrClient;
			onTclConnect();
		}
		
	}
	
	public void onTclConnect() {
//		try {
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
