package ru.nkz.ivcgzo.clientGenReestr;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import java.awt.Font;


public class ReestrForm extends JFrame {
	private static final long serialVersionUID = 1L;
	private SettingsForm sfrm;
    JFileChooser fc;
    String pathfile = "";

	public ReestrForm() {
		//setExtendedState(JFrame.MAXIMIZED_BOTH);
		setBounds(50, 50, 432, 311); //ширина, высота
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(configuration.appName+" за отчетный период");
		
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu RunMenu = new JMenu("Выполнить");
		RunMenu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		menuBar.add(RunMenu);
		JMenu menu2 = new JMenu("Выгрузка файлов для контроля в ТФ ОМС");
		menu2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		JMenu menu3 = new JMenu ("Загрузка файла контроля/оплаты реестров");
		menu3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		RunMenu.add(menu2);
		RunMenu.add(menu3);
		
		JMenuItem ReestrPolMenu = new JMenuItem ("Реестры поликлиники");
		ReestrPolMenu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		ReestrPolMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sfrm = new SettingsForm();
				sfrm.Cslu = 2;
				sfrm.showSettingsForm();
			}
		});

		JMenuItem ReestrLDSMenu = new JMenuItem ("Реестры ЛДС");
		ReestrLDSMenu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		ReestrLDSMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sfrm = new SettingsForm();
				sfrm.Cslu = 3;
				sfrm.showSettingsForm();
			}
		});

		JMenuItem ReestrStMenu = new JMenuItem ("Реестры стационара");
		ReestrStMenu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		ReestrStMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sfrm = new SettingsForm();
				sfrm.Cslu = 1;
				sfrm.showSettingsForm();
			}
		});
		
		JMenuItem ReestrDSPMenu = new JMenuItem ("Реестры ДСП");
		ReestrDSPMenu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		ReestrDSPMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sfrm = new SettingsForm();
				sfrm.Cslu = 4;
				sfrm.showSettingsForm();
			}
		});

		JMenuItem LoadReestrPolMenu = new JMenuItem ("Реестры поликлиники");
		LoadReestrPolMenu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		LoadReestrPolMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        OpenWindowFileChooser();
				try {
					pathfile = new File(pathfile).getParentFile().getAbsolutePath();
//					MainForm.conMan.transferFileToServer(pathfile, pathfile = new File(pathfile).getParentFile().getAbsolutePath());
					String servPath = MainForm.tcl.getProtokolErrPol(pathfile);
					String cliPath = File.createTempFile("protokol", ".htm").getAbsolutePath();
					MainForm.conMan.transferFileFromServer(servPath, cliPath);
					MainForm.conMan.openFileInTextProcessor(cliPath, false);
				} catch (KmiacServerException e) {
					e.printStackTrace();
				} catch (TException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});

		JMenuItem LoadReestrLDSMenu = new JMenuItem ("Реестры ЛДС");
		LoadReestrLDSMenu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		LoadReestrLDSMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        OpenWindowFileChooser();
				try {
					pathfile = new File(pathfile).getParentFile().getAbsolutePath();
//					MainForm.conMan.transferFileToServer(pathfile, pathfile = new File(pathfile).getParentFile().getAbsolutePath());
					String servPath = MainForm.tcl.getProtokolErrLDS(pathfile);
					String cliPath = File.createTempFile("protokol", ".htm").getAbsolutePath();
					MainForm.conMan.transferFileFromServer(servPath, cliPath);
					MainForm.conMan.openFileInTextProcessor(cliPath, false);
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		JMenuItem LoadReestrStMenu = new JMenuItem ("Реестры стационара");
		LoadReestrStMenu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		LoadReestrStMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		        OpenWindowFileChooser();
				try {
					pathfile = new File(pathfile).getParentFile().getAbsolutePath();
//					MainForm.conMan.transferFileToServer(pathfile, pathfile = new File(pathfile).getParentFile().getAbsolutePath());
					String servPath = MainForm.tcl.getProtokolErrGosp(pathfile);
					String cliPath = File.createTempFile("protokol", ".htm").getAbsolutePath();
					MainForm.conMan.transferFileFromServer(servPath, cliPath);
					MainForm.conMan.openFileInTextProcessor(cliPath, false);
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		JMenuItem LoadReestrDSPMenu = new JMenuItem ("Реестры ДСП");
		LoadReestrDSPMenu.setFont(new Font("Tahoma", Font.PLAIN, 12));
		LoadReestrDSPMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        OpenWindowFileChooser();
				try {
					pathfile = new File(pathfile).getParentFile().getAbsolutePath();
					String servPath = MainForm.tcl.getProtokolErrDSP(pathfile);//????
					String cliPath = File.createTempFile("protokol", ".htm").getAbsolutePath();
					MainForm.conMan.transferFileFromServer(servPath, cliPath);
					MainForm.conMan.openFileInTextProcessor(cliPath, false);
				} catch (KmiacServerException e) {
					e.printStackTrace();
				} catch (TException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		});
		menu2.add (ReestrPolMenu);
		menu2.add (ReestrLDSMenu);
		menu2.add (ReestrStMenu);
		menu2.add (ReestrDSPMenu);
		menu3.add (LoadReestrPolMenu);
		menu3.add (LoadReestrLDSMenu);
		menu3.add (LoadReestrStMenu);
		menu3.add (LoadReestrDSPMenu);
		
		JPanel panel = new JPanel();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 550, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGap(0, 36, Short.MAX_VALUE)
		);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
				.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 424, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 214, Short.MAX_VALUE))
		);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 424, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 214, Short.MAX_VALUE)
		);
		panel_1.setLayout(gl_panel_1);
		getContentPane().setLayout(groupLayout);
		
        
	}

	private void OpenWindowFileChooser() {
		fc = new JFileChooser();
//		FileNameExtensionFilter filter = new FileNameExtensionFilter(
//				".rar", "rar");
//		fc.setFileFilter(filter);
		int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
			pathfile = fc.getSelectedFile().getAbsolutePath();
            System.out.println(pathfile);
        }
	}

	public void onConnect() {
//		try {
//			System.out.println(MainForm.authInfo.getCslu());
//			System.out.println(Integer.toString(MainForm.authInfo.getCpodr()).length());
//			System.out.println(MainForm.tcl.getPolForCurrentLpu(MainForm.authInfo.getCpodr()));
//			if (Integer.toString(MainForm.authInfo.getCpodr()).length() == 3) MainForm.tcl.getPolForCurrentLpu(MainForm.authInfo.getCpodr());
//			if (Integer.toString(MainForm.authInfo.getCpodr()).length() == 2) MainForm.tcl.getAllPolForCurrentLpu(MainForm.authInfo.getCpodr());
//		} catch (TException e) {
//			e.printStackTrace();
//			MainForm.conMan.reconnect(e);
//		} catch (KmiacServerException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
