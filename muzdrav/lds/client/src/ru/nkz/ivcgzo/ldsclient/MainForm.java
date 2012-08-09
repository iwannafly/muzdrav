package ru.nkz.ivcgzo.ldsclient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.ldsThrift.LDSThrift;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;

public class MainForm extends Client<LDSThrift.Client> {
	Option winOpt;
	PIslForm winPat;
	public static LDSThrift.Client ltc;
	
	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(conMan, authInfo, LDSThrift.Client.class, configuration.appId, configuration.thrPort, lncPrm);

		initialize();
		
		setFrame(frame);
	}

	private JFrame frame;

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		winOpt = new Option();		
		winPat = new PIslForm();
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Ввод");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Данные");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
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
	public String getName() {
		return configuration.appName;
	}

	@Override
	public void onConnect(KmiacServer.Client conn) {
		super.onConnect(conn);
		if (conn instanceof LDSThrift.Client) {
			ltc = thrClient;
			
			try {
				/*TabPos.setStringClassifierSelector(2, Classifiers.n_s00);
				c_obr.setData(MainForm.tcl.getP0c());
				cbrez.setData(MainForm.tcl.getAp0());
				cbish.setData(MainForm.tcl.getAq0());*/

				winPat.cBprichina.setData(ltc.GetKlasCpos2());
				winPat.cBpopl.setData(ltc.GetKlasPopl());
				winPat.cBnapravl.setData(ltc.GetKlasNapr());
				winPat.cBvopl.setData(ltc.GetKlasOpl());
				winPat.cBrez.setData(ltc.GetKlasArez());
				
				winOpt.p0e1.setData(ltc.GetKlasP0e1());
				winOpt.n_nz1.setData(ltc.GetKlasNz1());
				winOpt.ts_ot01.setData(ltc.GetMinS_ot01(authInfo.cpodr));
				
			} catch (TException e) {
				e.printStackTrace();
				conMan.reconnect(e);
			}

		}
	}

}
