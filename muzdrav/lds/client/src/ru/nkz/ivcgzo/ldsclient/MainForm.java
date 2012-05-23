package ru.nkz.ivcgzo.ldsclient;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.ldsThrift.LDSThrift;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainForm extends Client{
	Option winOpt;
	public static LDSThrift.Client ltc;
	
	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) {
		super(conMan, authInfo, lncPrm);

		initialize();
		if (conMan != null) {
			try {
				conMan.add(LDSThrift.Client.class, configuration.thrPort);
				conMan.setLocalForm(frame);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else
			try {
				TTransport transport = new TFramedTransport(new TSocket("localhost", configuration.thrPort));
				transport.open();
				onConnect(new LDSThrift.Client(new TBinaryProtocol(transport)));
			} catch (TTransportException e) {
				e.printStackTrace();
				System.exit(1);
			}
		frame.setVisible(true);
	}

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm(null, null, 0);
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		winOpt = new Option();		
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Ввод");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Данные");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				PIslForm winPat = new PIslForm();
				winPat.frame.setVisible(true);
				winPat.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Ввод значений показателей");
		mnNewMenu.add(mntmNewMenuItem_1);
		
		JMenu mnNewMenu_1 = new JMenu("Сервис");
		menuBar.add(mnNewMenu_1);

		
		
		JMenuItem mntmNewMenuItem_2 = new JMenuItem("Настройка");
		mntmNewMenuItem_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				winOpt.frame.setVisible(true);
				winOpt.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);				
			}
		});
		mnNewMenu_1.add(mntmNewMenuItem_2);
		
		JMenuItem mntmNewMenuItem_3 = new JMenuItem("Исправление ошибок");
		mnNewMenu_1.add(mntmNewMenuItem_3);
		
	}

	@Override
	public String getVersion() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getId() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onConnect(
			ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		if (conn instanceof LDSThrift.Client) {
			ltc = (LDSThrift.Client) conn;
			
			try {
				/*TabPos.setStringClassifierSelector(2, Classifiers.n_s00);
				c_obr.setData(MainForm.tcl.getP0c());
				cbrez.setData(MainForm.tcl.getAp0());
				cbish.setData(MainForm.tcl.getAq0());*/
				winOpt.p0e1.setData(ltc.GetKlasP0e1());
				winOpt.n_nz1.setData(ltc.GetKlasNz1());
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
