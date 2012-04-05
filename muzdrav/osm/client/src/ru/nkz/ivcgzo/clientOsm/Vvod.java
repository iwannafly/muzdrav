package ru.nkz.ivcgzo.clientOsm;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.thriftOsm.PdiagAmb;
import ru.nkz.ivcgzo.thriftOsm.Priem;
import ru.nkz.ivcgzo.thriftOsm.Pvizit;
import ru.nkz.ivcgzo.thriftOsm.PvizitAmb;
import ru.nkz.ivcgzo.thriftOsm.ZapVr;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import java.awt.Component;


public class Vvod extends JFrame {

	private static final long serialVersionUID = 1L;
//	private JComboBox<String> cobr;
	private FormSign sign;
	private ThriftOsm.Client tcl;
	private Pvizit pvizit;
	private PvizitAmb pos;
	private PdiagAmb pdiagamb;
	private Priem priem;
	private PvizitAmb pvizitAmb;
	private JTextField tfdiag;
	private JTextField tfnamed;
	private JTextField tftemp;
	private JTextField tfad;
	private JTextField tfrost;
	private JTextField tfves;
	private JTextField tfchss;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public Vvod() {
		 pvizit=new Pvizit();
		 sign = new FormSign();
		 pos=new PvizitAmb();
		 pdiagamb=new PdiagAmb();
		priem = new Priem();

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1029, 747);
		//JPanel JPanel = new JPanel();
		setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane();
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(getContentPane());
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
		);
		
		JPanel panel = new JPanel();
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollPane.setRowHeaderView(panel);
		
		JButton btnAnamz = new JButton("Анамнез жизни");
		btnAnamz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			sign.setVisible(true);}
		});
				
		JButton button = new JButton("Аллергоанамнез");
		
		JButton button_1 = new JButton("Сохранить");
		button_1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				//pvizit.setZtext(tfjalob.getText());
				pdiagamb.setDiag(tfdiag.getText());
				pdiagamb.setNamed(tfnamed.getText());
				//priem.setOsmotr(tpjalob.getText()+tpanamn.getText()+tftemp.getText()+tfchss.getText()+tfad.getText()+tfrost.GetText()+tfves.getText()+);+возможно не подойдет,придется либо concat или append;

				try {
					tcl.AddPvizit(pvizit);
					tcl.AddPvizitAmb(pos);
					tcl.AddPdiagAmb(pdiagamb);
					tcl.AddPriem(priem);

				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JButton button_2 = new JButton("Сохранить как шаблон");
		
		JButton button_3 = new JButton("Загрузить из шаблона");
		
		JButton button_4 = new JButton("Печатные формы");
		
		JLabel lblTalon = new JLabel("Талон амбулаторного пациента");
		
		JCheckBox cbTalon = new JCheckBox("Скрыть/раскрыть");
		cbTalon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		
		JPanel panel_Talon = new JPanel();
				panel_Talon.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JLabel label_14 = new JLabel("Диагнозы");
		
		JCheckBox checkBox_7 = new JCheckBox("Скрыть/раскрыть");
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JButton button_5 = new JButton("Удалить");
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		
		JPanel Jalob = new JPanel();
		tabbedPane.addTab("Жалобы", null, Jalob, null);
		
		JPanel panel_jalob = new JPanel();
		
		JTextPane tpJalob = new JTextPane();
		GroupLayout gl_panel_jalob = new GroupLayout(panel_jalob);
		gl_panel_jalob.setHorizontalGroup(
			gl_panel_jalob.createParallelGroup(Alignment.LEADING)
				.addComponent(tpJalob, GroupLayout.DEFAULT_SIZE, 845, Short.MAX_VALUE)
		);
		gl_panel_jalob.setVerticalGroup(
			gl_panel_jalob.createParallelGroup(Alignment.LEADING)
				.addComponent(tpJalob, GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
		);
		panel_jalob.setLayout(gl_panel_jalob);
		
		JLabel lbljalob = new JLabel("Жалобы на:");
		GroupLayout gl_Jalob = new GroupLayout(Jalob);
		gl_Jalob.setHorizontalGroup(
			gl_Jalob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Jalob.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_Jalob.createParallelGroup(Alignment.LEADING)
						.addComponent(lbljalob)
						.addComponent(panel_jalob, GroupLayout.PREFERRED_SIZE, 663, GroupLayout.PREFERRED_SIZE)))
		);
		gl_Jalob.setVerticalGroup(
			gl_Jalob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Jalob.createSequentialGroup()
					.addGap(11)
					.addComponent(lbljalob)
					.addGap(7)
					.addComponent(panel_jalob, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		Jalob.setLayout(gl_Jalob);
		
		JPanel pAnanmnesis = new JPanel();
		tabbedPane.addTab("Anamnesis Morbi", null, pAnanmnesis, null);
		
		JPanel panel_anamn = new JPanel();
		panel_anamn.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JTextPane tpanamn = new JTextPane();
		
		JLabel lblanamn = new JLabel("Анамнез заболевания");
		GroupLayout gl_panel_anamn = new GroupLayout(panel_anamn);
		gl_panel_anamn.setHorizontalGroup(
			gl_panel_anamn.createParallelGroup(Alignment.LEADING)
				.addComponent(tpanamn, GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE)
		);
		gl_panel_anamn.setVerticalGroup(
			gl_panel_anamn.createParallelGroup(Alignment.LEADING)
				.addComponent(tpanamn, GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
		);
		panel_anamn.setLayout(gl_panel_anamn);
		GroupLayout gl_pAnanmnesis = new GroupLayout(pAnanmnesis);
		gl_pAnanmnesis.setHorizontalGroup(
			gl_pAnanmnesis.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pAnanmnesis.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_pAnanmnesis.createParallelGroup(Alignment.LEADING)
						.addComponent(lblanamn, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_anamn, GroupLayout.PREFERRED_SIZE, 663, GroupLayout.PREFERRED_SIZE)))
		);
		gl_pAnanmnesis.setVerticalGroup(
			gl_pAnanmnesis.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pAnanmnesis.createSequentialGroup()
					.addGap(11)
					.addComponent(lblanamn)
					.addGap(6)
					.addComponent(panel_anamn, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
		);
		pAnanmnesis.setLayout(gl_pAnanmnesis);
		
		JPanel pStatuspr = new JPanel();
		tabbedPane.addTab("Status Praesense", null, pStatuspr, null);
		
		JPanel panel_1 = new JPanel();
		
		JLabel lbltemp = new JLabel("Температура");
		
		tftemp = new JTextField();
		tftemp.setColumns(10);
		
		JLabel lblad = new JLabel("АД");
		
		tfad = new JTextField();
		tfad.setColumns(10);
		
		JLabel lblrost = new JLabel("Рост");
		
		tfrost = new JTextField();
		tfrost.setColumns(10);
		
		JLabel lblves = new JLabel("Вес");
		
		tfves = new JTextField();
		tfves.setColumns(10);
		
		JLabel lblchss = new JLabel("ЧСС");
		
		tfchss = new JTextField();
		tfchss.setColumns(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lbltemp)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tftemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblad)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tfad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblrost)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tfrost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblves, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfves, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblchss)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tfchss, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(92, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(tftemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbltemp)
						.addComponent(lblad)
						.addComponent(tfad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblrost)
						.addComponent(tfrost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblves)
						.addComponent(tfves, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblchss)
						.addComponent(tfchss, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(38, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		GroupLayout gl_pStatuspr = new GroupLayout(pStatuspr);
		gl_pStatuspr.setHorizontalGroup(
			gl_pStatuspr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pStatuspr.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 1819, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pStatuspr.setVerticalGroup(
			gl_pStatuspr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pStatuspr.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(139, Short.MAX_VALUE))
		);
		pStatuspr.setLayout(gl_pStatuspr);
		
		JPanel pfizikob = new JPanel();
		tabbedPane.addTab("Физикальное обследование", null, pfizikob, null);
		GroupLayout gl_pfizikob = new GroupLayout(pfizikob);
		gl_pfizikob.setHorizontalGroup(
			gl_pfizikob.createParallelGroup(Alignment.LEADING)
				.addGap(0, 683, Short.MAX_VALUE)
		);
		gl_pfizikob.setVerticalGroup(
			gl_pfizikob.createParallelGroup(Alignment.LEADING)
				.addGap(0, 250, Short.MAX_VALUE)
		);
		pfizikob.setLayout(gl_pfizikob);
		
		JLabel label_15 = new JLabel("Код");
		
		tfdiag = new JTextField();
		tfdiag.setColumns(10);
		
		JLabel label_16 = new JLabel("Медицинское описание");
		
		tfnamed = new JTextField();
		tfnamed.setColumns(10);
		GroupLayout gl_panel_7 = new GroupLayout(panel_7);
		gl_panel_7.setHorizontalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_15)
					.addGap(45)
					.addComponent(tfdiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(109)
					.addComponent(label_16)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tfnamed, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(173, Short.MAX_VALUE))
		);
		gl_panel_7.setVerticalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_7.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_15)
						.addComponent(tfdiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_16)
						.addComponent(tfnamed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(24, Short.MAX_VALUE))
		);
		panel_7.setLayout(gl_panel_7);
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGap(0, 847, Short.MAX_VALUE)
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGap(0, 75, Short.MAX_VALUE)
		);
		panel_6.setLayout(gl_panel_6);
		
		JLabel lblvid_opl = new JLabel("Вид оплаты");
		
		JComboBox vid_opl = new JComboBox();
		
		JLabel lblcobr = new JLabel("Цель обращения");
		
		JComboBox c_obr = new JComboBox();
		GroupLayout gl_panel_Talon = new GroupLayout(panel_Talon);
		gl_panel_Talon.setHorizontalGroup(
			gl_panel_Talon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_Talon.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_Talon.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_Talon.createSequentialGroup()
							.addComponent(lblcobr)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(c_obr, GroupLayout.PREFERRED_SIZE, 333, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(411, Short.MAX_VALUE))
						.addGroup(gl_panel_Talon.createSequentialGroup()
							.addComponent(lblvid_opl)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(vid_opl, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(411))))
		);
		gl_panel_Talon.setVerticalGroup(
			gl_panel_Talon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_Talon.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel_Talon.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblvid_opl)
						.addComponent(vid_opl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_Talon.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblcobr)
						.addComponent(c_obr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		panel_Talon.setLayout(gl_panel_Talon);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(btnAnamz)
					.addGap(10)
					.addComponent(button)
					.addGap(10)
					.addComponent(button_1)
					.addGap(10)
					.addComponent(button_2)
					.addGap(10)
					.addComponent(button_3)
					.addGap(10)
					.addComponent(button_4)
					.addGap(6)
					.addComponent(button_5))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(lblTalon)
					.addGap(6)
					.addComponent(cbTalon))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(panel_Talon, GroupLayout.DEFAULT_SIZE, 1241, Short.MAX_VALUE)
					.addGap(2775))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, 1864, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(label_14)
					.addGap(6)
					.addComponent(checkBox_7)
					.addGap(2)
					.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 1859, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(40)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 974, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(3012, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAnamz)
						.addComponent(button)
						.addComponent(button_1)
						.addComponent(button_2)
						.addComponent(button_3)
						.addComponent(button_4)
						.addComponent(button_5))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(4)
							.addComponent(lblTalon))
						.addComponent(cbTalon))
					.addGap(2)
					.addComponent(panel_Talon, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 561, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(30)
							.addComponent(label_14))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(26)
							.addComponent(checkBox_7))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(51)
							.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))))
		);
		
		JPanel plocst = new JPanel();
		tabbedPane.addTab("Localis status", null, plocst, null);
		GroupLayout gl_plocst = new GroupLayout(plocst);
		gl_plocst.setHorizontalGroup(
			gl_plocst.createParallelGroup(Alignment.LEADING)
				.addGap(0, 683, Short.MAX_VALUE)
		);
		gl_plocst.setVerticalGroup(
			gl_plocst.createParallelGroup(Alignment.LEADING)
				.addGap(0, 250, Short.MAX_VALUE)
		);
		plocst.setLayout(gl_plocst);
		
		JPanel pds = new JPanel();
		tabbedPane.addTab("Диагноз", null, pds, null);
		GroupLayout gl_pds = new GroupLayout(pds);
		gl_pds.setHorizontalGroup(
			gl_pds.createParallelGroup(Alignment.LEADING)
				.addGap(0, 683, Short.MAX_VALUE)
		);
		gl_pds.setVerticalGroup(
			gl_pds.createParallelGroup(Alignment.LEADING)
				.addGap(0, 250, Short.MAX_VALUE)
		);
		pds.setLayout(gl_pds);
		
		JPanel pnazn = new JPanel();
		tabbedPane.addTab("Назначения", null, pnazn, null);
		GroupLayout gl_pnazn = new GroupLayout(pnazn);
		gl_pnazn.setHorizontalGroup(
			gl_pnazn.createParallelGroup(Alignment.LEADING)
				.addGap(0, 683, Short.MAX_VALUE)
		);
		gl_pnazn.setVerticalGroup(
			gl_pnazn.createParallelGroup(Alignment.LEADING)
				.addGap(0, 250, Short.MAX_VALUE)
		);
		pnazn.setLayout(gl_pnazn);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(gl_contentPane);
	}
	private void setBorder(EmptyBorder emptyBorder) {
		// TODO Auto-generated method stub
		
	}
	public void showVvod(UserAuthInfo authInfo, ZapVr zapVr) {
		pvizit = new Pvizit();
		pvizit.setNpasp(zapVr.npasp);
		pvizit.setCod_sp(authInfo.pcod);
		pvizit.setDatap(System.currentTimeMillis());//системная дата
		pvizit.setDataz(System.currentTimeMillis());//системная дата
		pvizitAmb.setCod_sp(authInfo.pcod);
		pvizitAmb.setDatap(System.currentTimeMillis());//системная дата
		pvizitAmb.setNpasp(zapVr.npasp);
		
		setVisible(true);
	}
}
