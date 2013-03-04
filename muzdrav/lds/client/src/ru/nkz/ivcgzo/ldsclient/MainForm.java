package ru.nkz.ivcgzo.ldsclient;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import org.apache.thrift.TException;

import com.sun.java.swing.plaf.windows.resources.windows;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.ldsThrift.LDSThrift;
import ru.nkz.ivcgzo.ldsThrift.N_lds;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import sun.awt.WindowClosingListener;

public class MainForm extends Client<LDSThrift.Client> {
	Option winOpt;
	PIslForm winPat;
	LabGur winGur;

	
	public static LDSThrift.Client ltc;
	
	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(conMan, authInfo, LDSThrift.Client.class, configuration.appId, configuration.thrPort, lncPrm);

		initialize();
		
		setFrame(frame);
	}

	private JFrame frame;
	private JMenu mnNewMenu_2;
	private JMenuItem mntmNewMenuItem_4;
	
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		winOpt = new Option();		
		winPat = new PIslForm();
		winGur = new LabGur();
		
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Ввод");
		menuBar.add(mnNewMenu);
		
		JMenuItem mntmNewMenuItem = new JMenuItem("Данные");
		mntmNewMenuItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {				
				winPat.frame.setVisible(true);
				winPat.frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				try {
					winPat.cBpcisl.setData(ltc.GetKlasS_ot01(authInfo.cpodr));
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				winPat.filtPat();
				
			}
		});
		mnNewMenu.add(mntmNewMenuItem);
		
		JMenuItem mntmNewMenuItem_1 = new JMenuItem("Ввод значений показателей");
		mnNewMenu.add(mntmNewMenuItem_1);
		JMenu mnNewMenu_1 = new JMenu("Сервис");
		mntmNewMenuItem_1.setVisible(false);
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
		mntmNewMenuItem_3.setVisible(false);
		
		
		mnNewMenu_2 = new JMenu("Сводки");
		mnNewMenu_2.setVisible(false);
		menuBar.add(mnNewMenu_2);
		
		mntmNewMenuItem_4 = new JMenuItem("Лабораторный журнал");
		mntmNewMenuItem_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					winGur.cBpcislLG.setData(ltc.GetKlasS_ot01(authInfo.cpodr));
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				winGur.setVisible(true);
				
				
				
			}
		});
		mnNewMenu_2.add(mntmNewMenuItem_4);
		
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
			if (authInfo.cslu ==3){			
				try {
				
				
					List<N_lds> nlds;
				
				
					try {
					

						nlds = ltc.getN_lds(authInfo.cpodr);
						//System.out.println(authInfo.cpodr);
						PostPer.tip = nlds.get(0).tip;
						PostPer.name = nlds.get(0).name;
						PostPer.clpu = nlds.get(0).clpu;
					
					
						if (!nlds.get(0).tip.equals("Л")){
							winPat.tabbedPane.remove(1);
							winPat.btnNewButton_9.setVisible(true);
						}else {
							winPat.tabbedPane.remove(0);
							mnNewMenu_2.setVisible(true);
							mntmNewMenuItem_4.setVisible(true);
							
						}
					

					} catch (TException e) {
					 
					// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
					/*TabPos.setStringClassifierSelector(2, Classifiers.n_s00);
					c_obr.setData(MainForm.tcl.getP0c());
					cbrez.setData(MainForm.tcl.getAp0());
					cbish.setData(MainForm.tcl.getAq0());*/

				
					//winPat.cBpcisl.setData(ltc.GetKlasS_ot01(2000004));
					winPat.cBprichina.setData(ltc.GetKlasCpos2());
					winPat.cBpopl.setData(ltc.GetKlasPopl());
					winPat.cBnapravl.setData(ltc.GetKlasNapr());
					winPat.cBvopl.setData(ltc.GetKlasOpl());
					winPat.cBrez.setData(ltc.GetKlasArez());
					winPat.cBSvrach.setData(ltc.GetKlasSvrach(authInfo.cpodr));
					winPat.cBCuser.setData(ltc.GetKlasAllSvrach(authInfo.cpodr));
					//winPat.cBKod_ter.setData(ltc.GetKlasKod_ter());
				
					if (PostPer.tip.equals("Л")){
						winOpt.p0e1.setData(ltc.GetKlasP0e1(1));
					}else{
						winOpt.p0e1.setData(ltc.GetKlasNoLabP0e1(1));
					}
				
					winOpt.n_nz1.setData(ltc.GetKlasNz1());
					winOpt.ts_ot01.setData(ltc.GetMinS_ot01(authInfo.cpodr));
			
				
				} catch (TException e) {
					e.printStackTrace();
					conMan.reconnect(e);
				}

			}else{
				JOptionPane.showMessageDialog(frame, "Нет настроек на лабораторно-диагностическую службу");
				
				//frame.getDefaultCloseOperation();
				System.exit(0); 
				
				
			}	
				
				
		}
	}

}
