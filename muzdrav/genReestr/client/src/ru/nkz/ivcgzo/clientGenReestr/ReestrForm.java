package ru.nkz.ivcgzo.clientGenReestr;

import java.awt.BorderLayout;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JTabbedPane;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.thriftCommon.fileTransfer.OpenFileException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftGenReestr.ReestrNotFoundException;


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
		menuBar.add(RunMenu);
		JMenu menu2 = new JMenu("Выгрузка файлов для контроля в ТФ ОМС");
		JMenu menu3 = new JMenu ("Загрузка файла контроля/оплаты реестров");
		RunMenu.add(menu2);
		RunMenu.add(menu3);
		
		JMenuItem ReestrPolMenu = new JMenuItem ("Реестры поликлиники");
		ReestrPolMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sfrm = new SettingsForm();
				sfrm.showSettingsForm();
			}
		});

		JMenuItem ReestrLDSMenu = new JMenuItem ("Реестры ЛДС");
		ReestrLDSMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				sfrm = new SettingsForm();
				sfrm.showSettingsForm();
			}
		});

		JMenuItem ReestrStMenu = new JMenuItem ("Реестры стационара");
		
		JMenuItem LoadReestrPolMenu = new JMenuItem ("Реестры поликлиники");
		LoadReestrPolMenu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		        OpenWindowFileChooser();
				try {
					String servPath = MainForm.tcl.getProtokolErrPol(pathfile);
					String cliPath = File.createTempFile("protokol", ".htm").getAbsolutePath();
					MainForm.conMan.transferFileFromServer(servPath, cliPath);
					MainForm.conMan.openFileInEditor(cliPath, false);
				} catch (TException e) {
					e.printStackTrace();
				} catch (KmiacServerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ru.nkz.ivcgzo.thriftCommon.fileTransfer.FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (OpenFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		});

		JMenuItem LoadReestrLDSMenu = new JMenuItem ("Реестры ЛДС");

		JMenuItem LoadReestrStMenu = new JMenuItem ("Реестры стационара");
		menu2.add (ReestrPolMenu);
		menu2.add (ReestrLDSMenu);
		//menu2.add (ReestrStMenu);
		menu3.add (LoadReestrPolMenu);
		menu3.add (LoadReestrLDSMenu);
		//menu3.add (LoadReestrStMenu);
//		RunMenu.addSeparator(); 

		JPanel panel1 = new JPanel();
		JPanel panel2 = new JPanel();
		JTabbedPane tbMain = new JTabbedPane();
		tbMain.add("Реестры", panel1);
		GroupLayout gl_panel1 = new GroupLayout(panel1);
		gl_panel1.setHorizontalGroup(
			gl_panel1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 550, Short.MAX_VALUE)	//ширина
		);
		gl_panel1.setVerticalGroup(
			gl_panel1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 400, Short.MAX_VALUE)	//высота
		);
		panel1.setLayout(gl_panel1);
		tbMain.add("Протокол проверки", panel2);
		GroupLayout gl_panel2 = new GroupLayout(panel2);
		gl_panel2.setHorizontalGroup(
			gl_panel2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 550, Short.MAX_VALUE)
		);
		gl_panel2.setVerticalGroup(
			gl_panel2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 400, Short.MAX_VALUE)
		);
		panel2.setLayout(gl_panel2);
		getContentPane().add(tbMain);
		
		JPanel panel = new JPanel();
		getContentPane().add(panel, BorderLayout.NORTH);
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
		
        
	}

	private void OpenWindowFileChooser() {
		// TODO Auto-generated method stub
		fc = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
				".rar files", "rar");
		fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(null);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
            pathfile = fc.getSelectedFile().getPath();
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
