package ru.nkz.ivcgzo.clientDisp;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftDisp.Pdisp_ds;
import ru.nkz.ivcgzo.thriftDisp.ThriftDisp;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ButtonGroup;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.JCheckBox;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class MainForm extends Client<ThriftDisp.Client>{
	public static ThriftDisp.Client tcl;
	public static MainForm instance;
	private JFrame frame;
	private CustomDateEditor tbDataPostup;
	private ButtonGroup bgVedomPr;
	private JRadioButton rbtVedomPrZdrav;
	private JRadioButton rbtVedomPrObr;
	private JRadioButton rbtVedomPrSoc;
	private ButtonGroup bgIpr;
	private JRadioButton rbtIpr1;
	private JRadioButton rbtIpr2;
	private JRadioButton rbtIpr3;
	private JRadioButton rbtIpr4;
	private ButtonGroup bgVib;
	private JRadioButton rbtVib1;
	private JRadioButton rbtVib2;
	private JRadioButton rbtVib3;
	private JRadioButton rbtVib4;
	private JRadioButton rbtVib5;
	private ButtonGroup bgVib2;
	private JRadioButton rbtVib2_1;
	private JRadioButton rbtVib2_2;
	private JRadioButton rbtVib2_3;
	private JRadioButton rbtVib2_4;
	private ButtonGroup bgGrzd;
	private JRadioButton rbtGrzd1;
	private JRadioButton rbtGrzd2;
	private JRadioButton rbtGrzd3;
	private JRadioButton rbtGrzd4;
	private JRadioButton rbtGrzd5;
	private JPanel pnlProfPriv;
	private JRadioButton rbtProfPriv1;
	private JRadioButton rbtProfPriv2;
	private JRadioButton rbtProfPriv3;
	private ButtonGroup bgProfPriv;
	private JPanel pnlPrNePrivit;
	private JRadioButton rbtNeprivit1;
	private JRadioButton rbtNeprivit2;
	private ButtonGroup bgPrneprivit;
	private JRadioButton rbtBcg_vr1;
	private JRadioButton rbtBcg_vr2;
	private JRadioButton rbtBcg_vr3;
	private ButtonGroup bgBcg_vr;
	private JRadioButton rbtPolio_vr1;
	private JRadioButton rbtPolio_vr2;
	private JRadioButton rbtPolio_vr3;
	private JRadioButton rbtPolio_vr4;
	private JRadioButton rbtPolio_vr5;
	private JRadioButton rbtPolio_vr6;
	private ButtonGroup bgPolio;
	private JRadioButton rbtAkds1;
	private JRadioButton rbtAkds2;
	private JRadioButton rbtAkds3;
	private ButtonGroup bgAkds;
	private JRadioButton rbtKor1;
	private JRadioButton rbtKor2;
	private ButtonGroup bgKor;
	private JRadioButton rbtParotit1;
	private JRadioButton rbtParotit2;
	private ButtonGroup bgParotit;
	private JRadioButton rbtKrasn1;
	private JRadioButton rbtKrasn2;
	private ButtonGroup bgKrasn;
	private JRadioButton rbtGepatit1;
	private JRadioButton rbtGepatit2;
	private ButtonGroup bgGepatit;
	private CustomTextField tbVes;
	private JLabel lblVes;
	private JPanel pnlVes;
	private ButtonGroup bgVes;
	private JRadioButton rbtVes1;
	private JRadioButton rbtVes2;
	private JRadioButton rbtVes3;
	private ButtonGroup bgRost;
	private JRadioButton rbtRost1;
	private JRadioButton rbtRost2;
	private JRadioButton rbtRost3;
	private ButtonGroup bgPe;
	private JRadioButton rbtPe1;
	private JRadioButton rbtPe2;
	private JPanel pnlPp;
	private JRadioButton rbtPp1;
	private JRadioButton rbtPp2;
	private ButtonGroup bgPp;
	private JLabel lblOcenps;
	private JRadioButton rbtPi1;
	private JRadioButton rbtPi2;
	private ButtonGroup bgPi;
	private JPanel pnl04y;
	private JLabel lbl04y;
	private JLabel lblPf1;
	private JPanel pnlOcenPr;
	private JLabel lblPf;
	private JLabel lblPfm1;
	private ButtonGroup bgPdf5;
	private JRadioButton rbtPdf5_1;
	private JRadioButton rbtPdf5_2;
	private JRadioButton rbtMenses1_1;
	private JRadioButton rbtMenses1_2;
	private ButtonGroup bgMenses1;
	private ButtonGroup bgD_do;
	private JRadioButton rbtD_do1;
	private JRadioButton rbtD_do2;
	private CustomTable<Pdisp_ds, Pdisp_ds._Fields> tabDiag_do;
	private JRadioButton rbtObs_n1;
	private JRadioButton rbtObs_n2;
	private ButtonGroup bgObs_n;
	private JRadioButton rbtObs_v1;
	private JRadioButton rbtObs_v2;
	private JRadioButton rbtObs_v3;
	private ButtonGroup bgObs_v;
	private JRadioButton rbtLech_n1;
	private JRadioButton rbtLech_n2;
	private ButtonGroup bgLech_n;
	private ButtonGroup bgLech_v;
	private JRadioButton rbtLech_v1;
	private JRadioButton rbtLech_v2;
	private JRadioButton rbtLech_v3;
	private ButtonGroup bgDiag_po;
	private JRadioButton rbtDiag_po1;
	private JRadioButton rbtDiag_po2;
	private CustomTable<Pdisp_ds, Pdisp_ds._Fields> tabDiag_po;
	private ButtonGroup bgXzab;
	private JRadioButton rbtXzab1;
	private JRadioButton rbtXzab2;
	private ButtonGroup bgPu;
	private JRadioButton rbtPu1;
	private JRadioButton rbtPu2;
	private ButtonGroup bgDisp;
	private JRadioButton rbtDisp1;
	private JRadioButton rbtDisp2;
	private JRadioButton rbtDisp3;
	private ButtonGroup bgVmp1;
	private JRadioButton rbtVmp1_1;
	private JRadioButton rbtVmp1_2;
	private ButtonGroup bgVmp2;
	private JRadioButton rbtVmp2_1;
	private JRadioButton rbtVmp2_2;
	private JRadioButton rbtAkds4;


	public MainForm (ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(conMan, authInfo, ThriftDisp.Client.class, configuration.appId, configuration.thrPort, lncPrm);
		initialize();
		
		setFrame(frame);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		JButton btnSrc = new JButton("Поиск");
		btnSrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				int[] res = conMan.showPatientSearchForm("Поиск пациентов", false, true);
//				
//				if (res != null) {
//				}
			}
		});
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 967, Short.MAX_VALUE)
						.addComponent(btnSrc))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(btnSrc)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 650, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		bgVedomPr = new ButtonGroup();
		bgIpr = new ButtonGroup();
		bgVib = new ButtonGroup();
		bgVib2 = new ButtonGroup();
		bgGrzd = new ButtonGroup();
		bgProfPriv = new ButtonGroup();
		bgPrneprivit = new ButtonGroup();
		
		JPanel pnlSveden = new JPanel();
		tabbedPane.addTab("Сведения", null, pnlSveden, null);
		
		JLabel label = new JLabel("Дата поступления в учреждение постоянного пребывания");
		
		tbDataPostup = new  CustomDateEditor();
		tbDataPostup.setColumns(10);
		
		JLabel lblProfil = new JLabel("Профиль");
		
		JComboBox cmbProfil = new JComboBox();
		
		JLabel lblVedomPr = new JLabel("<html>Ведомственная <br>принадлежность &nbsp&nbspорганы</html>");
		
		JPanel pnlVedomPr = new JPanel();
		pnlVedomPr.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JPanel pnlIpr = new JPanel();
		pnlIpr.setBorder(new TitledBorder(null, "\u0418\u043D\u0434\u0438\u0432\u0438\u0434\u0443\u0430\u043B\u044C\u043D\u0430\u044F \u043F\u0440\u043E\u0433\u0440\u0430\u043C\u043C\u0430 \u0440\u0435\u0430\u0431\u0438\u043B\u0438\u0442\u0430\u0446\u0438\u0438 (\u0418\u041F\u0420)", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JLabel lblVib = new JLabel("Выбыл");
		
		JPanel pnlVib = new JPanel();
		pnlVib.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		 rbtVib1 = new JRadioButton("по возрасту");
		 bgVib.add(rbtVib1);
		 
		  rbtVib2 = new JRadioButton("усыновлен");
		  bgVib.add(rbtVib2);
		  
		   rbtVib3 = new JRadioButton("переведен");
		   bgVib.add(rbtVib3);
		   
		    rbtVib4 = new JRadioButton("умер");
		    bgVib.add(rbtVib4);
		    
		     rbtVib5 = new JRadioButton("другое");
		     bgVib.add(rbtVib5);
		     
		GroupLayout gl_pnlVib = new GroupLayout(pnlVib);
		gl_pnlVib.setHorizontalGroup(
			gl_pnlVib.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlVib.createSequentialGroup()
					.addGap(6)
					.addComponent(rbtVib1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtVib2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtVib3)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtVib4)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtVib5, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(36, Short.MAX_VALUE))
		);
		gl_pnlVib.setVerticalGroup(
			gl_pnlVib.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlVib.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_pnlVib.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlVib.createParallelGroup(Alignment.BASELINE)
							.addComponent(rbtVib4)
							.addComponent(rbtVib5))
						.addGroup(gl_pnlVib.createParallelGroup(Alignment.BASELINE)
							.addComponent(rbtVib1)
							.addComponent(rbtVib2)
							.addComponent(rbtVib3)))
					.addContainerGap())
		);
		pnlVib.setLayout(gl_pnlVib);
		
		 rbtIpr1 = new JRadioButton("полностью");
		 bgIpr.add(rbtIpr1);
		 
		  rbtIpr2 = new JRadioButton("частично");
		  bgIpr.add(rbtIpr2);
		  
		   rbtIpr3 = new JRadioButton("начато");
		   bgIpr.add(rbtIpr3);
		   
		    rbtIpr4 = new JRadioButton("не выполнено");
		    bgIpr.add(rbtIpr4);
		    
		GroupLayout gl_pnlIpr = new GroupLayout(pnlIpr);
		gl_pnlIpr.setHorizontalGroup(
			gl_pnlIpr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlIpr.createSequentialGroup()
					.addComponent(rbtIpr1, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(rbtIpr2, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
					.addComponent(rbtIpr3, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtIpr4)
					.addContainerGap(66, Short.MAX_VALUE))
		);
		gl_pnlIpr.setVerticalGroup(
			gl_pnlIpr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlIpr.createSequentialGroup()
					.addGroup(gl_pnlIpr.createParallelGroup(Alignment.LEADING)
						.addComponent(rbtIpr2)
						.addGroup(gl_pnlIpr.createParallelGroup(Alignment.BASELINE)
							.addComponent(rbtIpr3)
							.addComponent(rbtIpr4))
						.addComponent(rbtIpr1))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlIpr.setLayout(gl_pnlIpr);
		
		 rbtVedomPrZdrav = new JRadioButton("здравоохранения");
		 bgVedomPr.add(rbtVedomPrZdrav);
		 
		  rbtVedomPrObr = new JRadioButton("образования");
		  bgVedomPr.add(rbtVedomPrObr);
		  
		   rbtVedomPrSoc = new JRadioButton("соцзащиты");
		   bgVedomPr.add(rbtVedomPrSoc);
		   
		GroupLayout gl_pnlVedomPr = new GroupLayout(pnlVedomPr);
		gl_pnlVedomPr.setHorizontalGroup(
			gl_pnlVedomPr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlVedomPr.createSequentialGroup()
					.addGap(6)
					.addComponent(rbtVedomPrZdrav)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtVedomPrObr)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtVedomPrSoc)
					.addContainerGap(8, Short.MAX_VALUE))
		);
		gl_pnlVedomPr.setVerticalGroup(
			gl_pnlVedomPr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlVedomPr.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_pnlVedomPr.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtVedomPrZdrav)
						.addComponent(rbtVedomPrObr)
						.addComponent(rbtVedomPrSoc)))
		);
		pnlVedomPr.setLayout(gl_pnlVedomPr);
		
		JLabel lblVib2 = new JLabel("<html>Отсутствует на момент <br>проведения диспансеризации</html>");
		
		JPanel pnlVib2 = new JPanel();
		pnlVib2.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		 rbtVib2_1 = new JRadioButton("на учебе");
		 bgVib2.add(rbtVib2_1);
		 
		  rbtVib2_2 = new JRadioButton("в бегах");
		  bgVib2.add(rbtVib2_2);
		  
		   rbtVib2_3 = new JRadioButton("в санатории");
		   bgVib2.add(rbtVib2_3);
		   
		    rbtVib2_4 = new JRadioButton("другое");
		    bgVib2.add(rbtVib2_4);
		    
		GroupLayout gl_pnlVib2 = new GroupLayout(pnlVib2);
		gl_pnlVib2.setHorizontalGroup(
			gl_pnlVib2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 396, Short.MAX_VALUE)
				.addGroup(gl_pnlVib2.createSequentialGroup()
					.addGap(6)
					.addComponent(rbtVib2_1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtVib2_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtVib2_3)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtVib2_4)
					.addContainerGap(86, Short.MAX_VALUE))
		);
		gl_pnlVib2.setVerticalGroup(
			gl_pnlVib2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 34, Short.MAX_VALUE)
				.addGroup(gl_pnlVib2.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_pnlVib2.createParallelGroup(Alignment.LEADING)
						.addComponent(rbtVib2_4)
						.addGroup(gl_pnlVib2.createParallelGroup(Alignment.BASELINE)
							.addComponent(rbtVib2_1)
							.addComponent(rbtVib2_2)
							.addComponent(rbtVib2_3)))
					.addContainerGap())
		);
		pnlVib2.setLayout(gl_pnlVib2);
		
		JLabel lblDatNaznIpr = new JLabel("Дата назначения ИПР");
		
		CustomDateEditor tbDatNaznIpr = new CustomDateEditor();
		tbDatNaznIpr.setColumns(10);
		
		JPanel pnlGrzd = new JPanel();
		pnlGrzd.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0413\u0440\u0443\u043F\u043F\u0430 \u0437\u0434\u043E\u0440\u043E\u0432\u044C\u044F", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		rbtGrzd1 = new JRadioButton("I");
		bgGrzd.add(rbtGrzd1);
		
		rbtGrzd2 = new JRadioButton("II");
		bgGrzd.add(rbtGrzd2);
		
		rbtGrzd3 = new JRadioButton("III");
		bgGrzd.add(rbtGrzd3);
		
		rbtGrzd4 = new JRadioButton("IV");
		bgGrzd.add(rbtGrzd4);
		
		rbtGrzd5 = new JRadioButton("V");
		bgGrzd.add(rbtGrzd5);
		
		GroupLayout gl_pnlGrzd = new GroupLayout(pnlGrzd);
		gl_pnlGrzd.setHorizontalGroup(
			gl_pnlGrzd.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlGrzd.createSequentialGroup()
					.addComponent(rbtGrzd1, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtGrzd2)
					.addGap(10)
					.addComponent(rbtGrzd3, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtGrzd4)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtGrzd5, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(16, Short.MAX_VALUE))
		);
		gl_pnlGrzd.setVerticalGroup(
			gl_pnlGrzd.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlGrzd.createSequentialGroup()
					.addGroup(gl_pnlGrzd.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtGrzd1)
						.addComponent(rbtGrzd2)
						.addComponent(rbtGrzd3)
						.addComponent(rbtGrzd4)
						.addComponent(rbtGrzd5))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlGrzd.setLayout(gl_pnlGrzd);
		
		pnlProfPriv = new JPanel();
		pnlProfPriv.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0413\u0440\u0443\u043F\u043F\u0430 \u0437\u0434\u043E\u0440\u043E\u0432\u044C\u044F", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		rbtProfPriv1 = new JRadioButton("привит по календарному плану");
		bgProfPriv.add(rbtProfPriv1);
		
		rbtProfPriv2 = new JRadioButton("не привит по мед.показаниям");
		bgProfPriv.add(rbtProfPriv2);
		
		rbtProfPriv3 = new JRadioButton("не привит по другим причинам");
		bgProfPriv.add(rbtProfPriv3);
		
		pnlPrNePrivit = new JPanel();
		pnlPrNePrivit.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		GroupLayout gl_pnlProfPriv = new GroupLayout(pnlProfPriv);
		gl_pnlProfPriv.setHorizontalGroup(
			gl_pnlProfPriv.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlProfPriv.createSequentialGroup()
					.addGroup(gl_pnlProfPriv.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlProfPriv.createSequentialGroup()
							.addComponent(rbtProfPriv1)
							.addGap(18))
						.addGroup(gl_pnlProfPriv.createSequentialGroup()
							.addComponent(rbtProfPriv3, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pnlPrNePrivit, GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGap(26))
				.addGroup(gl_pnlProfPriv.createSequentialGroup()
					.addComponent(rbtProfPriv2)
					.addContainerGap(227, Short.MAX_VALUE))
		);
		gl_pnlProfPriv.setVerticalGroup(
			gl_pnlProfPriv.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlProfPriv.createSequentialGroup()
					.addGroup(gl_pnlProfPriv.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlProfPriv.createSequentialGroup()
							.addComponent(rbtProfPriv1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rbtProfPriv2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rbtProfPriv3))
						.addGroup(gl_pnlProfPriv.createSequentialGroup()
							.addGap(37)
							.addComponent(pnlPrNePrivit, GroupLayout.PREFERRED_SIZE, 21, Short.MAX_VALUE)))
					.addContainerGap())
		);
		
		rbtNeprivit1 = new JRadioButton("полностью");
		bgPrneprivit.add(rbtNeprivit1);
		
		rbtNeprivit2 = new JRadioButton("частично");
		bgPrneprivit.add(rbtNeprivit2);
		
		GroupLayout gl_pnlPrNePrivit = new GroupLayout(pnlPrNePrivit);
		gl_pnlPrNePrivit.setHorizontalGroup(
			gl_pnlPrNePrivit.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlPrNePrivit.createSequentialGroup()
					.addContainerGap(14, Short.MAX_VALUE)
					.addComponent(rbtNeprivit1, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
					.addGap(2)
					.addComponent(rbtNeprivit2, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_pnlPrNePrivit.setVerticalGroup(
			gl_pnlPrNePrivit.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlPrNePrivit.createSequentialGroup()
					.addGroup(gl_pnlPrNePrivit.createParallelGroup(Alignment.LEADING)
						.addComponent(rbtNeprivit1)
						.addComponent(rbtNeprivit2))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlPrNePrivit.setLayout(gl_pnlPrNePrivit);
		pnlProfPriv.setLayout(gl_pnlProfPriv);
		
		JCheckBox cbPriv_n = new JCheckBox("нуждается  в проведении вакцинации/ревакцинации");
		
		JCheckBox cbBcg = new JCheckBox("БЦЖ");
		
		JCheckBox cbPolio = new JCheckBox("Полиомиелит");
		
		JCheckBox cbAkds = new JCheckBox("АКДС");
		
		JCheckBox cbAdsm = new JCheckBox("АДСМ");
		
		JCheckBox cbKor = new JCheckBox("Корь");
		
		JCheckBox cbParotit = new JCheckBox("Эпид.паротит");
		
		JCheckBox cbKrasn = new JCheckBox("Краснуха");
		
		JCheckBox cbGepatit = new JCheckBox("Гепатит В");
		
		JPanel pnlBcg_vr = new JPanel();
		pnlBcg_vr.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		bgBcg_vr = new ButtonGroup();
		
		 rbtBcg_vr1 = new JRadioButton("V");
		 bgBcg_vr.add(rbtBcg_vr1);
		 
		 rbtBcg_vr2 = new JRadioButton("R1");
		 bgBcg_vr.add(rbtBcg_vr2);
		
		 rbtBcg_vr3 = new JRadioButton("R2");
		 bgBcg_vr.add(rbtBcg_vr3);
		 
		GroupLayout gl_pnlBcg_vr = new GroupLayout(pnlBcg_vr);
		gl_pnlBcg_vr.setHorizontalGroup(
			gl_pnlBcg_vr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlBcg_vr.createSequentialGroup()
					.addContainerGap()
					.addComponent(rbtBcg_vr1)
					.addGap(2)
					.addComponent(rbtBcg_vr2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtBcg_vr3)
					.addGap(2))
		);
		gl_pnlBcg_vr.setVerticalGroup(
			gl_pnlBcg_vr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlBcg_vr.createSequentialGroup()
					.addGroup(gl_pnlBcg_vr.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtBcg_vr2, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbtBcg_vr3, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbtBcg_vr1, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlBcg_vr.setLayout(gl_pnlBcg_vr);
		
		JPanel pnlPolio = new JPanel();
		pnlPolio.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		bgPolio = new ButtonGroup();
		
		 rbtPolio_vr1 = new JRadioButton("V1");
		 bgPolio.add(rbtPolio_vr1);
		
		 rbtPolio_vr2 = new JRadioButton("V2");
		 bgPolio.add(rbtPolio_vr2);
		
		 rbtPolio_vr3 = new JRadioButton("V3");
		 bgPolio.add(rbtPolio_vr3);
		
		 rbtPolio_vr4 = new JRadioButton("R1");
		 bgPolio.add(rbtPolio_vr4);
		
		 rbtPolio_vr5 = new JRadioButton("R2");
		 bgPolio.add(rbtPolio_vr5);
		
		 rbtPolio_vr6 = new JRadioButton("R3");
		 bgPolio.add(rbtPolio_vr6);
		
		GroupLayout gl_pnlPolio = new GroupLayout(pnlPolio);
		gl_pnlPolio.setHorizontalGroup(
			gl_pnlPolio.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlPolio.createSequentialGroup()
					.addContainerGap()
					.addComponent(rbtPolio_vr1, GroupLayout.PREFERRED_SIZE, 36, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtPolio_vr2, GroupLayout.PREFERRED_SIZE, 36, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtPolio_vr3, GroupLayout.PREFERRED_SIZE, 36, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtPolio_vr4, GroupLayout.PREFERRED_SIZE, 38, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtPolio_vr5, GroupLayout.PREFERRED_SIZE, 38, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtPolio_vr6, GroupLayout.PREFERRED_SIZE, 38, Short.MAX_VALUE)
					.addGap(18))
		);
		gl_pnlPolio.setVerticalGroup(
			gl_pnlPolio.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlPolio.createSequentialGroup()
					.addGroup(gl_pnlPolio.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtPolio_vr1, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbtPolio_vr3, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbtPolio_vr2, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbtPolio_vr4, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbtPolio_vr5, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbtPolio_vr6, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlPolio.setLayout(gl_pnlPolio);
		
		JPanel pnlAkds = new JPanel();
		pnlAkds.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		bgAkds = new ButtonGroup();
		
		 rbtAkds1 = new JRadioButton("V1");
		 bgAkds.add(rbtAkds1);
		
		 rbtAkds2 = new JRadioButton("V2");
		 bgAkds.add(rbtAkds2);
		
		 rbtAkds3 = new JRadioButton("V3");
		 bgAkds.add(rbtAkds3);
		
		 rbtAkds4 = new JRadioButton("R");
		  bgAkds.add(rbtAkds4);
		 
		GroupLayout gl_pnlAkds = new GroupLayout(pnlAkds);
		gl_pnlAkds.setHorizontalGroup(
			gl_pnlAkds.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlAkds.createSequentialGroup()
					.addContainerGap()
					.addComponent(rbtAkds1)
					.addGap(2)
					.addComponent(rbtAkds2, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtAkds3)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtAkds4, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(8))
		);
		gl_pnlAkds.setVerticalGroup(
			gl_pnlAkds.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlAkds.createSequentialGroup()
					.addGroup(gl_pnlAkds.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtAkds2, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbtAkds1, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbtAkds3, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbtAkds4, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlAkds.setLayout(gl_pnlAkds);
		
		JCheckBox cbAdm = new JCheckBox("АДМ");
		
		JPanel pnlKor = new JPanel();
		pnlKor.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		bgKor = new ButtonGroup();
		
		 rbtKor1 = new JRadioButton("V");
		 bgKor.add(rbtKor1);
		
		 rbtKor2 = new JRadioButton("R");
		 bgKor.add(rbtKor2);
		
		GroupLayout gl_pnlKor = new GroupLayout(pnlKor);
		gl_pnlKor.setHorizontalGroup(
			gl_pnlKor.createParallelGroup(Alignment.LEADING)
				.addGap(0, 123, Short.MAX_VALUE)
				.addGroup(gl_pnlKor.createSequentialGroup()
					.addContainerGap()
					.addComponent(rbtKor1)
					.addGap(2)
					.addComponent(rbtKor2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(41))
		);
		gl_pnlKor.setVerticalGroup(
			gl_pnlKor.createParallelGroup(Alignment.LEADING)
				.addGap(0, 23, Short.MAX_VALUE)
				.addGroup(gl_pnlKor.createSequentialGroup()
					.addGroup(gl_pnlKor.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtKor2, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbtKor1, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlKor.setLayout(gl_pnlKor);
		
		JPanel pnlParotit = new JPanel();
		pnlParotit.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		bgParotit = new ButtonGroup();
		
		 rbtParotit1 = new JRadioButton("V");
		 bgParotit.add(rbtParotit1);
		
		 rbtParotit2 = new JRadioButton("R");
		  bgParotit.add(rbtParotit2);
		 
		GroupLayout gl_pnlParotit = new GroupLayout(pnlParotit);
		gl_pnlParotit.setHorizontalGroup(
			gl_pnlParotit.createParallelGroup(Alignment.LEADING)
				.addGap(0, 87, Short.MAX_VALUE)
				.addGap(0, 123, Short.MAX_VALUE)
				.addGroup(gl_pnlParotit.createSequentialGroup()
					.addContainerGap()
					.addComponent(rbtParotit1)
					.addGap(2)
					.addComponent(rbtParotit2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(41))
		);
		gl_pnlParotit.setVerticalGroup(
			gl_pnlParotit.createParallelGroup(Alignment.LEADING)
				.addGap(0, 23, Short.MAX_VALUE)
				.addGap(0, 23, Short.MAX_VALUE)
				.addGroup(gl_pnlParotit.createSequentialGroup()
					.addGroup(gl_pnlParotit.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtParotit2, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbtParotit1, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlParotit.setLayout(gl_pnlParotit);
		
		JPanel pnlKrasn = new JPanel();
		pnlKrasn.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		bgKrasn = new ButtonGroup();
		
		
		 rbtKrasn1 = new JRadioButton("V");
		 bgKrasn.add(rbtKrasn1);
		
		 rbtKrasn2 = new JRadioButton("R");
		 bgKrasn.add(rbtKrasn2);
		
		GroupLayout gl_pnlKrasn = new GroupLayout(pnlKrasn);
		gl_pnlKrasn.setHorizontalGroup(
			gl_pnlKrasn.createParallelGroup(Alignment.LEADING)
				.addGap(0, 87, Short.MAX_VALUE)
				.addGap(0, 123, Short.MAX_VALUE)
				.addGroup(gl_pnlKrasn.createSequentialGroup()
					.addContainerGap()
					.addComponent(rbtKrasn1)
					.addGap(2)
					.addComponent(rbtKrasn2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(41))
		);
		gl_pnlKrasn.setVerticalGroup(
			gl_pnlKrasn.createParallelGroup(Alignment.LEADING)
				.addGap(0, 23, Short.MAX_VALUE)
				.addGap(0, 23, Short.MAX_VALUE)
				.addGroup(gl_pnlKrasn.createSequentialGroup()
					.addGroup(gl_pnlKrasn.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtKrasn2, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbtKrasn1, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlKrasn.setLayout(gl_pnlKrasn);
		
		JPanel pnlGepatit = new JPanel();
		pnlGepatit.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		bgGepatit = new ButtonGroup();
		
		 rbtGepatit1 = new JRadioButton("V");
		 bgGepatit.add(rbtGepatit1);
		
		 rbtGepatit2 = new JRadioButton("R");
		 bgGepatit.add(rbtGepatit2);
		
		GroupLayout gl_pnlGepatit = new GroupLayout(pnlGepatit);
		gl_pnlGepatit.setHorizontalGroup(
			gl_pnlGepatit.createParallelGroup(Alignment.LEADING)
				.addGap(0, 87, Short.MAX_VALUE)
				.addGap(0, 123, Short.MAX_VALUE)
				.addGroup(gl_pnlGepatit.createSequentialGroup()
					.addContainerGap()
					.addComponent(rbtGepatit1)
					.addGap(2)
					.addComponent(rbtGepatit2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(41))
		);
		gl_pnlGepatit.setVerticalGroup(
			gl_pnlGepatit.createParallelGroup(Alignment.LEADING)
				.addGap(0, 23, Short.MAX_VALUE)
				.addGap(0, 23, Short.MAX_VALUE)
				.addGroup(gl_pnlGepatit.createSequentialGroup()
					.addGroup(gl_pnlGepatit.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtGepatit2, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbtGepatit1, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlGepatit.setLayout(gl_pnlGepatit);
		
		JLabel lblDateOsm = new JLabel("Дата осмотра");
		
		CustomDateEditor tbDateOsm = new CustomDateEditor();
		tbDateOsm.setColumns(10);
		
		JCheckBox cbPrb = new JCheckBox("Относится к категории часто болеющих детей");
		
		JCheckBox cbPrk = new JCheckBox("Потребность в медико-педагогической коррекции");
		
		JCheckBox bcPrs = new JCheckBox("Потребность в медико-социальной коррекции");
		GroupLayout gl_pnlSveden = new GroupLayout(pnlSveden);
		gl_pnlSveden.setHorizontalGroup(
			gl_pnlSveden.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlSveden.createSequentialGroup()
					.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlSveden.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlSveden.createSequentialGroup()
									.addComponent(label)
									.addGap(10)
									.addComponent(tbDataPostup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_pnlSveden.createSequentialGroup()
									.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_pnlSveden.createSequentialGroup()
											.addComponent(lblProfil)
											.addGap(4)
											.addComponent(cmbProfil, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_pnlSveden.createSequentialGroup()
											.addComponent(lblVedomPr, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)
											.addGap(4)
											.addComponent(pnlVedomPr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_pnlSveden.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_pnlSveden.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_pnlSveden.createSequentialGroup()
													.addComponent(lblVib2, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.addComponent(pnlVib2, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE))
												.addGroup(Alignment.LEADING, gl_pnlSveden.createSequentialGroup()
													.addComponent(lblVib, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(pnlVib, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE)))))
									.addGap(32)
									.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
										.addComponent(cbPrb)
										.addGroup(gl_pnlSveden.createSequentialGroup()
											.addComponent(lblDatNaznIpr, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(tbDatNaznIpr, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
										.addComponent(pnlIpr, GroupLayout.PREFERRED_SIZE, 433, GroupLayout.PREFERRED_SIZE)
										.addComponent(pnlGrzd, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_pnlSveden.createSequentialGroup()
											.addComponent(lblDateOsm, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
											.addGap(4)
											.addComponent(tbDateOsm, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
										.addComponent(cbPrk)
										.addComponent(bcPrs)))))
						.addGroup(gl_pnlSveden.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
								.addComponent(cbPriv_n)
								.addComponent(pnlProfPriv, GroupLayout.PREFERRED_SIZE, 420, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_pnlSveden.createSequentialGroup()
							.addGap(44)
							.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlSveden.createSequentialGroup()
									.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
										.addComponent(cbPolio)
										.addComponent(cbBcg)
										.addComponent(cbAkds)
										.addComponent(cbKor)
										.addComponent(cbParotit)
										.addComponent(cbKrasn)
										.addComponent(cbGepatit))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_pnlSveden.createSequentialGroup()
											.addGap(19)
											.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
												.addComponent(pnlKor, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
												.addComponent(pnlParotit, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
												.addComponent(pnlKrasn, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
												.addComponent(pnlGepatit, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)))
										.addGroup(gl_pnlSveden.createSequentialGroup()
											.addGap(18)
											.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
												.addComponent(pnlBcg_vr, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
												.addComponent(pnlPolio, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE)
												.addComponent(pnlAkds, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)))))
								.addComponent(cbAdsm)
								.addComponent(cbAdm))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pnlSveden.setVerticalGroup(
			gl_pnlSveden.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlSveden.createSequentialGroup()
					.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlSveden.createSequentialGroup()
							.addGap(11)
							.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlSveden.createSequentialGroup()
									.addGap(3)
									.addComponent(label))
								.addComponent(tbDataPostup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlSveden.createSequentialGroup()
									.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_pnlSveden.createSequentialGroup()
											.addGap(3)
											.addComponent(lblProfil))
										.addComponent(cmbProfil, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
									.addGap(11)
									.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_pnlSveden.createSequentialGroup()
											.addGap(6)
											.addComponent(lblVedomPr))
										.addComponent(pnlVedomPr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_pnlSveden.createSequentialGroup()
									.addGap(19)
									.addComponent(pnlIpr, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)))
							.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlSveden.createSequentialGroup()
									.addGap(18)
									.addGroup(gl_pnlSveden.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblVib, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
										.addComponent(pnlVib, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_pnlSveden.createSequentialGroup()
											.addGap(18)
											.addComponent(pnlVib2, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_pnlSveden.createSequentialGroup()
											.addGap(24)
											.addComponent(lblVib2))))
								.addGroup(gl_pnlSveden.createSequentialGroup()
									.addGap(14)
									.addGroup(gl_pnlSveden.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblDatNaznIpr)
										.addComponent(tbDatNaznIpr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(pnlGrzd, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(cbPrb)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_pnlSveden.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_pnlSveden.createSequentialGroup()
									.addComponent(pnlProfPriv, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
									.addGap(3)
									.addComponent(cbPriv_n))
								.addGroup(gl_pnlSveden.createSequentialGroup()
									.addGap(18)
									.addComponent(bcPrs)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_pnlSveden.createSequentialGroup()
											.addGap(3)
											.addComponent(lblDateOsm))
										.addComponent(tbDateOsm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlSveden.createParallelGroup(Alignment.TRAILING)
								.addComponent(cbBcg)
								.addComponent(pnlBcg_vr, 0, 0, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlSveden.createParallelGroup(Alignment.TRAILING)
								.addComponent(cbPolio)
								.addComponent(pnlPolio, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlSveden.createParallelGroup(Alignment.TRAILING)
								.addComponent(cbAkds)
								.addComponent(pnlAkds, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(cbAdsm)
							.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlSveden.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cbAdm)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(cbKor)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(cbParotit))
								.addGroup(gl_pnlSveden.createSequentialGroup()
									.addGap(23)
									.addComponent(pnlKor, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
									.addGap(5)
									.addComponent(pnlParotit, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
								.addComponent(cbKrasn)
								.addComponent(pnlKrasn, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlSveden.createParallelGroup(Alignment.LEADING)
								.addComponent(cbGepatit)
								.addComponent(pnlGepatit, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_pnlSveden.createSequentialGroup()
							.addGap(228)
							.addComponent(cbPrk)))
					.addContainerGap())
		);
		pnlSveden.setLayout(gl_pnlSveden);
		
		JPanel pnlFizRazv = new JPanel();
		tabbedPane.addTab("Физическое развитие", null, pnlFizRazv, null);
		
		JLabel lblOcenFiz = new JLabel("<html>Оценка<br> физического<br> развития</html>");
		
		tbVes = new CustomTextField();
		tbVes.setColumns(10);
		
		lblVes = new JLabel("Вес (кг.гр)");
		
		pnlVes = new JPanel();
		pnlVes.setBorder(new TitledBorder(null, "\u0412\u0435\u0441", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		bgVes = new ButtonGroup();
		
		JLabel lblRost = new JLabel("Рост (м)");
		
		CustomTextField tbRost = new CustomTextField();
		tbRost.setColumns(10);
		
		JPanel pnlRost = new JPanel();
		pnlRost.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0420\u043E\u0441\u0442", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		bgRost = new ButtonGroup();
		
		rbtRost1 = new JRadioButton("норма");
		bgRost.add(rbtRost1);
		
		rbtRost2 = new JRadioButton("больше");
		bgRost.add(rbtRost2);
		
		rbtRost3 = new JRadioButton("меньше");
		bgRost.add(rbtRost3);
		
		GroupLayout gl_pnlRost = new GroupLayout(pnlRost);
		gl_pnlRost.setHorizontalGroup(
			gl_pnlRost.createParallelGroup(Alignment.LEADING)
				.addGap(0, 197, Short.MAX_VALUE)
				.addGroup(gl_pnlRost.createSequentialGroup()
					.addComponent(rbtRost1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtRost2)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtRost3)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pnlRost.setVerticalGroup(
			gl_pnlRost.createParallelGroup(Alignment.LEADING)
				.addGap(0, 47, Short.MAX_VALUE)
				.addGroup(gl_pnlRost.createSequentialGroup()
					.addGroup(gl_pnlRost.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtRost1)
						.addComponent(rbtRost2)
						.addComponent(rbtRost3))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlRost.setLayout(gl_pnlRost);
		
		JLabel lblOkr = new JLabel("Окружность головы, см (0-4 лет)");
		
		CustomTextField tbOkr = new CustomTextField();
		tbOkr.setColumns(10);
		
		JLabel lblVrk = new JLabel("Весо-ростовой коэффициент");
		
		JPanel pnlPe = new JPanel();
		pnlPe.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u042D\u043C\u043E\u0446\u0438\u043E\u043D\u0430\u043B\u044C\u043D\u043E-\u0432\u0435\u0433\u0435\u0442\u0430\u0442\u0438\u0432\u043D\u0430\u044F \u0441\u0444\u0435\u0440\u0430", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		bgPe = new ButtonGroup();
		
		pnlPp = new JPanel();
		pnlPp.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u041F\u0441\u0438\u0445\u043E\u043C\u043E\u0442\u043E\u0440\u043D\u0430\u044F \u0441\u0444\u0435\u0440\u0430", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		bgPp = new ButtonGroup();
		
		rbtPp1 = new JRadioButton("норма");
		bgPp.add(rbtPp1);
		
		rbtPp2 = new JRadioButton("отклонение");
		bgPp.add(rbtPp2);
		
		GroupLayout gl_pnlPp = new GroupLayout(pnlPp);
		gl_pnlPp.setHorizontalGroup(
			gl_pnlPp.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 209, Short.MAX_VALUE)
				.addGroup(gl_pnlPp.createSequentialGroup()
					.addContainerGap(13, Short.MAX_VALUE)
					.addComponent(rbtPp1)
					.addGap(18)
					.addComponent(rbtPp2)
					.addGap(22))
		);
		gl_pnlPp.setVerticalGroup(
			gl_pnlPp.createParallelGroup(Alignment.LEADING)
				.addGap(0, 46, Short.MAX_VALUE)
				.addGroup(gl_pnlPp.createSequentialGroup()
					.addGroup(gl_pnlPp.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtPp1)
						.addComponent(rbtPp2))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlPp.setLayout(gl_pnlPp);
		
		lblOcenps = new JLabel("<html>Оценка <br>психического <be>здоровья</html>");
		
		CustomTextField tbVrk = new CustomTextField();
		tbVrk.setColumns(10);
		
		JPanel pnlPi = new JPanel();
		pnlPi.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0418\u043D\u0442\u0435\u043B\u043B\u0435\u043A\u0442", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		bgPi = new ButtonGroup();
		
		 rbtPi1 = new JRadioButton("норма");
		 bgPi.add(rbtPi1);
		
		 rbtPi2 = new JRadioButton("отклонение");
		 bgPi.add(rbtPi2);
		 
		GroupLayout gl_pnlPi = new GroupLayout(pnlPi);
		gl_pnlPi.setHorizontalGroup(
			gl_pnlPi.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 209, Short.MAX_VALUE)
				.addGroup(gl_pnlPi.createSequentialGroup()
					.addContainerGap(13, Short.MAX_VALUE)
					.addComponent(rbtPi1)
					.addGap(18)
					.addComponent(rbtPi2)
					.addGap(22))
		);
		gl_pnlPi.setVerticalGroup(
			gl_pnlPi.createParallelGroup(Alignment.LEADING)
				.addGap(0, 46, Short.MAX_VALUE)
				.addGroup(gl_pnlPi.createSequentialGroup()
					.addGroup(gl_pnlPi.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtPi1)
						.addComponent(rbtPi2))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlPi.setLayout(gl_pnlPi);
		
		pnl04y = new JPanel();
		pnl04y.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		lbl04y = new JLabel("0-4 лет");
		
		JLabel lblOcenPf = new JLabel("<html>Оценка <br>полового <br>развития <br>с 10 лет</html>");
		lblOcenPf.setHorizontalAlignment(SwingConstants.LEFT);
		lblOcenPf.setVerticalAlignment(SwingConstants.TOP);
		
		pnlOcenPr = new JPanel();
		pnlOcenPr.setBorder(UIManager.getBorder("PopupMenu.border"));
		
		GroupLayout gl_pnlFizRazv = new GroupLayout(pnlFizRazv);
		gl_pnlFizRazv.setHorizontalGroup(
			gl_pnlFizRazv.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlFizRazv.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_pnlFizRazv.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlFizRazv.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_pnlFizRazv.createSequentialGroup()
								.addGroup(gl_pnlFizRazv.createParallelGroup(Alignment.LEADING)
									.addComponent(lblOcenFiz, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblOcenps, GroupLayout.PREFERRED_SIZE, 105, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED))
							.addGroup(gl_pnlFizRazv.createSequentialGroup()
								.addComponent(lbl04y)
								.addGap(34)))
						.addGroup(gl_pnlFizRazv.createSequentialGroup()
							.addComponent(lblOcenPf, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
							.addGap(22)))
					.addGroup(gl_pnlFizRazv.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlFizRazv.createSequentialGroup()
							.addComponent(pnlPe, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(pnlPp, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(pnlPi, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlFizRazv.createSequentialGroup()
							.addComponent(lblVrk)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tbVrk, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlFizRazv.createSequentialGroup()
							.addGroup(gl_pnlFizRazv.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_pnlFizRazv.createSequentialGroup()
									.addGroup(gl_pnlFizRazv.createParallelGroup(Alignment.LEADING)
										.addComponent(tbRost, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblRost, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
									.addGap(18)
									.addComponent(pnlRost, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_pnlFizRazv.createSequentialGroup()
									.addGroup(gl_pnlFizRazv.createParallelGroup(Alignment.LEADING)
										.addComponent(tbVes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblVes))
									.addGap(18)
									.addComponent(pnlVes, GroupLayout.PREFERRED_SIZE, 197, GroupLayout.PREFERRED_SIZE)))
							.addGap(33)
							.addGroup(gl_pnlFizRazv.createParallelGroup(Alignment.LEADING)
								.addComponent(lblOkr, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_pnlFizRazv.createSequentialGroup()
									.addGap(35)
									.addComponent(tbOkr, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))))
						.addComponent(pnl04y, GroupLayout.PREFERRED_SIZE, 625, GroupLayout.PREFERRED_SIZE)
						.addComponent(pnlOcenPr, GroupLayout.PREFERRED_SIZE, 545, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(178, Short.MAX_VALUE))
		);
		gl_pnlFizRazv.setVerticalGroup(
			gl_pnlFizRazv.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlFizRazv.createSequentialGroup()
					.addGroup(gl_pnlFizRazv.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlFizRazv.createSequentialGroup()
							.addGap(19)
							.addGroup(gl_pnlFizRazv.createParallelGroup(Alignment.LEADING)
								.addComponent(lblVes)
								.addGroup(gl_pnlFizRazv.createSequentialGroup()
									.addGap(22)
									.addComponent(tbVes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_pnlFizRazv.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_pnlFizRazv.createSequentialGroup()
										.addComponent(lblOkr)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(tbOkr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addComponent(pnlVes, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlFizRazv.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlFizRazv.createParallelGroup(Alignment.BASELINE)
									.addComponent(pnlRost, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblRost))
								.addGroup(gl_pnlFizRazv.createSequentialGroup()
									.addGap(22)
									.addComponent(tbRost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_pnlFizRazv.createSequentialGroup()
							.addGap(62)
							.addComponent(lblOcenFiz)))
					.addGroup(gl_pnlFizRazv.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlFizRazv.createSequentialGroup()
							.addGap(6)
							.addGroup(gl_pnlFizRazv.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblVrk)
								.addComponent(tbVrk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(33)
							.addGroup(gl_pnlFizRazv.createParallelGroup(Alignment.BASELINE)
								.addComponent(pnlPe, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addComponent(pnlPp, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addComponent(pnlPi, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_pnlFizRazv.createSequentialGroup()
							.addGap(93)
							.addComponent(lblOcenps)))
					.addGroup(gl_pnlFizRazv.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlFizRazv.createSequentialGroup()
							.addGap(11)
							.addComponent(pnl04y, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlFizRazv.createSequentialGroup()
							.addGap(54)
							.addComponent(lbl04y)))
					.addGroup(gl_pnlFizRazv.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlFizRazv.createSequentialGroup()
							.addGap(71)
							.addComponent(lblOcenPf))
						.addGroup(gl_pnlFizRazv.createSequentialGroup()
							.addGap(18)
							.addComponent(pnlOcenPr, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(68, Short.MAX_VALUE))
		);
		
		lblPf = new JLabel("половая формула мальчика:");
		
		lblPfm1 = new JLabel("Р");
		
		CustomTextField tbPfm1 = new CustomTextField();
		tbPfm1.setColumns(10);
		
		JLabel lblPfm2 = new JLabel("Ах");
		
		CustomTextField tbPfm2 = new CustomTextField();
		tbPfm2.setColumns(10);
		
		JLabel lblPfm3 = new JLabel("Fa");
		
		CustomTextField tbPfm3 = new CustomTextField();
		tbPfm3.setColumns(10);
		
		JLabel lblPfd = new JLabel("половая формула девочки:");
		
		JLabel lblPfd1 = new JLabel("Р");
		
		CustomTextField tbPdf1 = new CustomTextField();
		tbPdf1.setColumns(10);
		
		JLabel lblPdf2 = new JLabel("Мф");
		
		CustomTextField tbPdf2 = new CustomTextField();
		tbPdf2.setColumns(10);
		
		JLabel lblPdf3 = new JLabel("Ах");
		
		CustomTextField tbPdf3 = new CustomTextField();
		tbPdf3.setColumns(10);
		
		JLabel lblPdf4 = new JLabel("Ме");
		
		CustomTextField tbPdf4 = new CustomTextField();
		tbPdf4.setColumns(10);
		
		JLabel lblMenarhe = new JLabel("Menarhe (лет, месяцев)");
		
		CustomTextField tbPfd5 = new CustomTextField();
		tbPfd5.setColumns(10);
		
		JLabel lblMenses = new JLabel("Menses(хар-ка)");
		
		JPanel pnlMenses = new JPanel();
		pnlMenses.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		bgPdf5 = new ButtonGroup();
		
		rbtPdf5_1 = new JRadioButton("регулярные");
		bgPdf5.add(rbtPdf5_1);
		
		rbtPdf5_2 = new JRadioButton("нерегулярные");
		bgPdf5.add(rbtPdf5_2);
		
		GroupLayout gl_pnlMenses = new GroupLayout(pnlMenses);
		gl_pnlMenses.setHorizontalGroup(
			gl_pnlMenses.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlMenses.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(rbtPdf5_1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtPdf5_2)
					.addGap(38))
		);
		gl_pnlMenses.setVerticalGroup(
			gl_pnlMenses.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlMenses.createSequentialGroup()
					.addGroup(gl_pnlMenses.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtPdf5_1)
						.addComponent(rbtPdf5_2))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlMenses.setLayout(gl_pnlMenses);
		
		JPanel pnlMenses1 = new JPanel();
		pnlMenses1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		bgMenses1 = new ButtonGroup();
		
		 rbtMenses1_1 = new JRadioButton("обильные");
		 bgMenses1.add(rbtMenses1_1);
		
		 rbtMenses1_2 = new JRadioButton("скудные");
		 bgMenses1.add(rbtMenses1_2);
		 
		GroupLayout gl_pnlMenses1 = new GroupLayout(pnlMenses1);
		gl_pnlMenses1.setHorizontalGroup(
			gl_pnlMenses1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlMenses1.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(rbtMenses1_1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtMenses1_2)
					.addGap(38))
		);
		gl_pnlMenses1.setVerticalGroup(
			gl_pnlMenses1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 32, Short.MAX_VALUE)
				.addGroup(gl_pnlMenses1.createSequentialGroup()
					.addGroup(gl_pnlMenses1.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtMenses1_1)
						.addComponent(rbtMenses1_2))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlMenses1.setLayout(gl_pnlMenses1);
		GroupLayout gl_pnlOcenPr = new GroupLayout(pnlOcenPr);
		gl_pnlOcenPr.setHorizontalGroup(
			gl_pnlOcenPr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlOcenPr.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlOcenPr.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlOcenPr.createSequentialGroup()
							.addComponent(lblPf)
							.addGap(10)
							.addComponent(lblPfm1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tbPfm1, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblPfm2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tbPfm2, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblPfm3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tbPfm3, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlOcenPr.createSequentialGroup()
							.addGroup(gl_pnlOcenPr.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlOcenPr.createSequentialGroup()
									.addComponent(lblMenarhe)
									.addGap(18)
									.addComponent(tbPfd5, 0, 0, Short.MAX_VALUE))
								.addGroup(gl_pnlOcenPr.createSequentialGroup()
									.addComponent(lblPfd, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
									.addGap(10)
									.addComponent(lblPfd1, GroupLayout.PREFERRED_SIZE, 6, GroupLayout.PREFERRED_SIZE)
									.addGap(10)
									.addComponent(tbPdf1, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblPdf2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addGap(4)
									.addComponent(tbPdf2, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblPdf3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addGap(4)
									.addComponent(tbPdf3, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblPdf4, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tbPdf4, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_pnlOcenPr.createSequentialGroup()
							.addComponent(lblMenses)
							.addGap(10)
							.addComponent(pnlMenses, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pnlMenses1, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)))
					.addGap(42))
		);
		gl_pnlOcenPr.setVerticalGroup(
			gl_pnlOcenPr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlOcenPr.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlOcenPr.createParallelGroup(Alignment.LEADING)
						.addComponent(tbPfm3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tbPfm2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlOcenPr.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblPfm1)
							.addComponent(tbPfm1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblPf))
						.addGroup(gl_pnlOcenPr.createSequentialGroup()
							.addGap(3)
							.addComponent(lblPfm2))
						.addGroup(gl_pnlOcenPr.createSequentialGroup()
							.addGap(3)
							.addComponent(lblPfm3)))
					.addGap(18)
					.addGroup(gl_pnlOcenPr.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlOcenPr.createSequentialGroup()
							.addGap(3)
							.addComponent(lblPfd))
						.addGroup(gl_pnlOcenPr.createSequentialGroup()
							.addGap(3)
							.addComponent(lblPfd1))
						.addComponent(tbPdf1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlOcenPr.createSequentialGroup()
							.addGap(3)
							.addComponent(lblPdf2))
						.addComponent(tbPdf2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlOcenPr.createSequentialGroup()
							.addGap(3)
							.addComponent(lblPdf3))
						.addGroup(gl_pnlOcenPr.createParallelGroup(Alignment.BASELINE)
							.addComponent(tbPdf3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblPdf4)
							.addComponent(tbPdf4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlOcenPr.createParallelGroup(Alignment.LEADING)
						.addComponent(lblMenarhe)
						.addComponent(tbPfd5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_pnlOcenPr.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlOcenPr.createSequentialGroup()
							.addGap(26)
							.addComponent(lblMenses))
						.addGroup(gl_pnlOcenPr.createSequentialGroup()
							.addGap(18)
							.addGroup(gl_pnlOcenPr.createParallelGroup(Alignment.LEADING)
								.addComponent(pnlMenses1, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
								.addComponent(pnlMenses, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlOcenPr.setLayout(gl_pnlOcenPr);
		
		lblPf1 = new JLabel("<html>Познавательная функция (возраст <br>развития, мес.)</html>");
		
		CustomTextField tbPf1 = new CustomTextField();
		tbPf1.setColumns(10);
		
		JLabel lblEf1 = new JLabel("<html>Эмоциональная и социальная функции <br>(контакт с окруж.миром) <br>(возраст развития, мес.)</html>");
		
		CustomTextField tbEf1 = new CustomTextField();
		tbEf1.setColumns(10);
		
		JLabel lblMf1 = new JLabel("<html>Моторная функция<br> (возраст развития, мес.)</html>");
		
		CustomTextField tbMf1 = new CustomTextField();
		tbMf1.setColumns(10);
		
		JLabel lblRf1 = new JLabel("<html>Предречевое и речевое <br> развитие (возраст развития, мес.)</html>");
		
		CustomTextField tbRf1 = new CustomTextField();
		tbRf1.setColumns(10);
		GroupLayout gl_pnl04y = new GroupLayout(pnl04y);
		gl_pnl04y.setHorizontalGroup(
			gl_pnl04y.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl04y.createSequentialGroup()
					.addGap(4)
					.addGroup(gl_pnl04y.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnl04y.createSequentialGroup()
							.addComponent(lblPf1, GroupLayout.PREFERRED_SIZE, 193, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGap(18)
							.addComponent(tbPf1, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnl04y.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblEf1, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tbEf1, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)))
					.addGap(27)
					.addGroup(gl_pnl04y.createParallelGroup(Alignment.LEADING)
						.addComponent(lblRf1, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMf1, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnl04y.createParallelGroup(Alignment.LEADING)
						.addComponent(tbMf1, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
						.addComponent(tbRf1, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
					.addGap(52))
		);
		gl_pnl04y.setVerticalGroup(
			gl_pnl04y.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnl04y.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnl04y.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblPf1)
						.addComponent(tbPf1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMf1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(tbMf1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnl04y.createParallelGroup(Alignment.TRAILING)
						.addComponent(tbEf1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEf1)
						.addComponent(lblRf1)
						.addComponent(tbRf1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11))
		);
		pnl04y.setLayout(gl_pnl04y);
		
		rbtPe1 = new JRadioButton("норма");
		bgPe.add(rbtPe1);
		
		rbtPe2 = new JRadioButton("отклонение");
		bgPe.add(rbtPe2);
		
		GroupLayout gl_pnlPe = new GroupLayout(pnlPe);
		gl_pnlPe.setHorizontalGroup(
			gl_pnlPe.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlPe.createSequentialGroup()
					.addContainerGap(13, Short.MAX_VALUE)
					.addComponent(rbtPe1)
					.addGap(18)
					.addComponent(rbtPe2)
					.addGap(22))
		);
		gl_pnlPe.setVerticalGroup(
			gl_pnlPe.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlPe.createSequentialGroup()
					.addGroup(gl_pnlPe.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtPe1)
						.addComponent(rbtPe2))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlPe.setLayout(gl_pnlPe);
		
		rbtVes1 = new JRadioButton("норма");
		bgVes.add(rbtVes1);
		
		rbtVes2 = new JRadioButton("больше");
		bgVes.add(rbtVes2);
		
		rbtVes3 = new JRadioButton("меньше");
		bgVes.add(rbtVes3);
		
		GroupLayout gl_pnlVes = new GroupLayout(pnlVes);
		gl_pnlVes.setHorizontalGroup(
			gl_pnlVes.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlVes.createSequentialGroup()
					.addComponent(rbtVes1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtVes2)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtVes3)
					.addContainerGap(38, Short.MAX_VALUE))
		);
		gl_pnlVes.setVerticalGroup(
			gl_pnlVes.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlVes.createSequentialGroup()
					.addGroup(gl_pnlVes.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtVes1)
						.addComponent(rbtVes2)
						.addComponent(rbtVes3))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlVes.setLayout(gl_pnlVes);
		pnlFizRazv.setLayout(gl_pnlFizRazv);
		
		JPanel pnlDiagDo = new JPanel();
		tabbedPane.addTab("Диагнозы до дисп.", null, pnlDiagDo, null);
		
		JPanel pnlD_do = new JPanel();
		pnlD_do.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		bgD_do = new ButtonGroup();
		
		 rbtD_do1 = new JRadioButton("практически здоров");
		 bgD_do.add(rbtD_do1);
		
		 rbtD_do2 = new JRadioButton("болен (заболевания, подлежащие диспансерному наблюдению)");
		 bgD_do.add(rbtD_do2);
		 
		GroupLayout gl_pnlD_do = new GroupLayout(pnlD_do);
		gl_pnlD_do.setHorizontalGroup(
			gl_pnlD_do.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.LEADING, gl_pnlD_do.createSequentialGroup()
					.addContainerGap()
					.addComponent(rbtD_do1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtD_do2)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pnlD_do.setVerticalGroup(
			gl_pnlD_do.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlD_do.createSequentialGroup()
					.addGroup(gl_pnlD_do.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtD_do1)
						.addComponent(rbtD_do2))
					.addContainerGap(27, Short.MAX_VALUE))
		);
		pnlD_do.setLayout(gl_pnlD_do);
		
		JLabel lblD_do = new JLabel("Состояние здоровья до проведения настоящего диспансерного обследования");
		
		JButton btnAdd = new JButton("+");
		
		JButton btnDel = new JButton("-");
		
		JButton btnSave = new JButton("v");
		
		JScrollPane spDiag_do = new JScrollPane();
		
		JLabel label_1 = new JLabel("Доосбледование (по результатам прошлой диспансеризации)");
		
		JPanel pnlObs_n = new JPanel();
		pnlObs_n.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		bgObs_n = new ButtonGroup();
		
		rbtObs_n1 = new JRadioButton("назначено");
		bgObs_n.add(rbtObs_n1);
		
		rbtObs_n2 = new JRadioButton("не назначено");
		bgObs_n.add(rbtObs_n2);
		
		GroupLayout gl_pnlObs_n = new GroupLayout(pnlObs_n);
		gl_pnlObs_n.setHorizontalGroup(
			gl_pnlObs_n.createParallelGroup(Alignment.LEADING)
				.addGap(0, 197, Short.MAX_VALUE)
				.addGroup(gl_pnlObs_n.createSequentialGroup()
					.addComponent(rbtObs_n1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtObs_n2)
					.addContainerGap(61, Short.MAX_VALUE))
		);
		gl_pnlObs_n.setVerticalGroup(
			gl_pnlObs_n.createParallelGroup(Alignment.LEADING)
				.addGap(0, 47, Short.MAX_VALUE)
				.addGroup(gl_pnlObs_n.createSequentialGroup()
					.addGroup(gl_pnlObs_n.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtObs_n1)
						.addComponent(rbtObs_n2))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlObs_n.setLayout(gl_pnlObs_n);
		
		JPanel pnlObs_v = new JPanel();
		pnlObs_v.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		bgObs_v = new ButtonGroup();
		
		rbtObs_v1 = new JRadioButton("выполнено");
		bgObs_v.add(rbtObs_v1);
		
		rbtObs_v2 = new JRadioButton("начато");
		bgObs_v.add(rbtObs_v2);
		
		rbtObs_v3 = new JRadioButton("не проведено");
		bgObs_v.add(rbtObs_v3);
		
		GroupLayout gl_pnlObs_v = new GroupLayout(pnlObs_v);
		gl_pnlObs_v.setHorizontalGroup(
			gl_pnlObs_v.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlObs_v.createSequentialGroup()
					.addComponent(rbtObs_v1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtObs_v2)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtObs_v3)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pnlObs_v.setVerticalGroup(
			gl_pnlObs_v.createParallelGroup(Alignment.LEADING)
				.addGap(0, 47, Short.MAX_VALUE)
				.addGroup(gl_pnlObs_v.createSequentialGroup()
					.addGroup(gl_pnlObs_v.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtObs_v1)
						.addComponent(rbtObs_v2)
						.addComponent(rbtObs_v3))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlObs_v.setLayout(gl_pnlObs_v);
		
		JPanel pnlLech_n = new JPanel();
		pnlLech_n.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		bgLech_n = new ButtonGroup();
		
		rbtLech_n1 = new JRadioButton("назначено");
		bgLech_n.add(rbtLech_n1);
		
		rbtLech_n2 = new JRadioButton("не назначено");
		bgLech_n.add(rbtLech_n2);
		
		GroupLayout gl_pnlLech_n = new GroupLayout(pnlLech_n);
		gl_pnlLech_n.setHorizontalGroup(
			gl_pnlLech_n.createParallelGroup(Alignment.LEADING)
				.addGap(0, 189, Short.MAX_VALUE)
				.addGroup(gl_pnlLech_n.createSequentialGroup()
					.addComponent(rbtLech_n1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtLech_n2)
					.addContainerGap(11, Short.MAX_VALUE))
		);
		gl_pnlLech_n.setVerticalGroup(
			gl_pnlLech_n.createParallelGroup(Alignment.LEADING)
				.addGap(0, 29, Short.MAX_VALUE)
				.addGroup(gl_pnlLech_n.createSequentialGroup()
					.addGroup(gl_pnlLech_n.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtLech_n1)
						.addComponent(rbtLech_n2))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlLech_n.setLayout(gl_pnlLech_n);
		
		JLabel lblLech = new JLabel("Доосбледование (по результатам прошлой диспансеризации)");
		
		JPanel pnlLech_v = new JPanel();
		pnlLech_v.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		bgLech_v = new ButtonGroup();
		
		rbtLech_v1 = new JRadioButton("выполнено");
		bgLech_v.add(rbtLech_v1);
		
		rbtLech_v2 = new JRadioButton("начато");
		bgLech_v.add(rbtLech_v2);
		
		rbtLech_v3 = new JRadioButton("не проведено");
		bgLech_v.add(rbtLech_v3);
		
		GroupLayout gl_pnlLech_v = new GroupLayout(pnlLech_v);
		gl_pnlLech_v.setHorizontalGroup(
			gl_pnlLech_v.createParallelGroup(Alignment.LEADING)
				.addGap(0, 251, Short.MAX_VALUE)
				.addGroup(gl_pnlLech_v.createSequentialGroup()
					.addComponent(rbtLech_v1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtLech_v2)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtLech_v3)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pnlLech_v.setVerticalGroup(
			gl_pnlLech_v.createParallelGroup(Alignment.LEADING)
				.addGap(0, 29, Short.MAX_VALUE)
				.addGroup(gl_pnlLech_v.createSequentialGroup()
					.addGroup(gl_pnlLech_v.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtLech_v1)
						.addComponent(rbtLech_v2)
						.addComponent(rbtLech_v3))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlLech_v.setLayout(gl_pnlLech_v);
		GroupLayout gl_pnlDiagDo = new GroupLayout(pnlDiagDo);
		gl_pnlDiagDo.setHorizontalGroup(
			gl_pnlDiagDo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiagDo.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlDiagDo.createParallelGroup(Alignment.LEADING)
						.addComponent(pnlD_do, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblD_do)
						.addGroup(gl_pnlDiagDo.createSequentialGroup()
							.addComponent(btnAdd)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnDel)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnSave))
						.addComponent(spDiag_do, GroupLayout.PREFERRED_SIZE, 335, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlDiagDo.createSequentialGroup()
							.addComponent(label_1)
							.addGap(10)
							.addComponent(pnlObs_n, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(pnlObs_v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlDiagDo.createSequentialGroup()
							.addComponent(lblLech, GroupLayout.PREFERRED_SIZE, 315, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(pnlLech_n, GroupLayout.PREFERRED_SIZE, 189, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(pnlLech_v, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(10, Short.MAX_VALUE))
		);
		gl_pnlDiagDo.setVerticalGroup(
			gl_pnlDiagDo.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiagDo.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblD_do)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlD_do, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDiagDo.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnAdd)
						.addComponent(btnDel)
						.addComponent(btnSave))
					.addGap(18)
					.addComponent(spDiag_do, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
					.addGap(21)
					.addGroup(gl_pnlDiagDo.createParallelGroup(Alignment.LEADING)
						.addComponent(pnlObs_v, Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
						.addGroup(gl_pnlDiagDo.createParallelGroup(Alignment.LEADING, false)
							.addComponent(label_1, Alignment.TRAILING)
							.addComponent(pnlObs_n, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 29, Short.MAX_VALUE)))
					.addGap(18)
					.addGroup(gl_pnlDiagDo.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlDiagDo.createSequentialGroup()
							.addGap(15)
							.addComponent(lblLech))
						.addComponent(pnlLech_n, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
						.addComponent(pnlLech_v, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addGap(296))
		);
		
		tabDiag_do = new CustomTable<>(false, false, Pdisp_ds.class, 3, "Диагноз");
		tabDiag_do.setFillsViewportHeight(true);
		spDiag_do.setViewportView(tabDiag_do);
		pnlDiagDo.setLayout(gl_pnlDiagDo);
		
		
		JPanel pnlDiagPosle = new JPanel();
		tabbedPane.addTab("Диагнозы после дисп.", null, pnlDiagPosle, null);
		
		JLabel lblDiag_po = new JLabel("Диагнозы по результатам проведения настоящего диспансерного обследования");
		
		JPanel pnlDiag_po = new JPanel();
		pnlDiag_po.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		bgDiag_po = new ButtonGroup();
		
		rbtDiag_po1 = new JRadioButton("практически здоров");
		bgDiag_po.add(rbtDiag_po1);
		
		rbtDiag_po2 = new JRadioButton("болен (заболевания, подлежащие диспансерному наблюдению)");
		bgDiag_po.add(rbtDiag_po2);
		
		GroupLayout gl_pnlDiag_po = new GroupLayout(pnlDiag_po);
		gl_pnlDiag_po.setHorizontalGroup(
			gl_pnlDiag_po.createParallelGroup(Alignment.LEADING)
				.addGap(0, 502, Short.MAX_VALUE)
				.addGroup(gl_pnlDiag_po.createSequentialGroup()
					.addContainerGap()
					.addComponent(rbtDiag_po1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtDiag_po2)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pnlDiag_po.setVerticalGroup(
			gl_pnlDiag_po.createParallelGroup(Alignment.LEADING)
				.addGap(0, 28, Short.MAX_VALUE)
				.addGroup(gl_pnlDiag_po.createSequentialGroup()
					.addGroup(gl_pnlDiag_po.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtDiag_po1)
						.addComponent(rbtDiag_po2))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlDiag_po.setLayout(gl_pnlDiag_po);
		
		JButton btnSave1 = new JButton("v");
		
		JButton btnDel1 = new JButton("-");
		
		JButton btnAdd1 = new JButton("+");
		
		JScrollPane scrollPane = new JScrollPane();
		
		JPanel pnlXzab = new JPanel();
		pnlXzab.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		bgXzab = new ButtonGroup();
		
		rbtXzab1 = new JRadioButton("функциональные отклонения");
		bgXzab.add(rbtXzab1);
		
		rbtXzab2 = new JRadioButton("хроническое заболевание");
		bgXzab.add(rbtXzab2);
		
		GroupLayout gl_pnlXzab = new GroupLayout(pnlXzab);
		gl_pnlXzab.setHorizontalGroup(
			gl_pnlXzab.createParallelGroup(Alignment.LEADING)
				.addGap(0, 502, Short.MAX_VALUE)
				.addGroup(gl_pnlXzab.createSequentialGroup()
					.addContainerGap()
					.addComponent(rbtXzab1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtXzab2)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pnlXzab.setVerticalGroup(
			gl_pnlXzab.createParallelGroup(Alignment.LEADING)
				.addGap(0, 28, Short.MAX_VALUE)
				.addGroup(gl_pnlXzab.createSequentialGroup()
					.addGroup(gl_pnlXzab.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtXzab1)
						.addComponent(rbtXzab2))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlXzab.setLayout(gl_pnlXzab);
		
		JPanel pnlPu = new JPanel();
		pnlPu.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		bgPu = new ButtonGroup();
		
		rbtPu1 = new JRadioButton("предварительный");
		bgPu.add(rbtPu1);
		
		rbtPu2 = new JRadioButton("уточненный");
		bgPu.add(rbtPu2);
		
		GroupLayout gl_pnlPu = new GroupLayout(pnlPu);
		gl_pnlPu.setHorizontalGroup(
			gl_pnlPu.createParallelGroup(Alignment.LEADING)
				.addGap(0, 353, Short.MAX_VALUE)
				.addGroup(gl_pnlPu.createSequentialGroup()
					.addContainerGap()
					.addComponent(rbtPu1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtPu2)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pnlPu.setVerticalGroup(
			gl_pnlPu.createParallelGroup(Alignment.LEADING)
				.addGap(0, 28, Short.MAX_VALUE)
				.addGroup(gl_pnlPu.createSequentialGroup()
					.addGroup(gl_pnlPu.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtPu1)
						.addComponent(rbtPu2))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlPu.setLayout(gl_pnlPu);
		
		JLabel lblDisp = new JLabel("Необходимость постановки на диспансерный учет");
		
		JPanel pnlDisp = new JPanel();
		pnlDisp.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		bgDisp = new ButtonGroup();
		
		rbtDisp1 = new JRadioButton("нет");
		bgDisp.add(rbtDisp1);
		
		rbtDisp2 = new JRadioButton("состоял ранее");
		bgDisp.add(rbtDisp2);
		
		rbtDisp3 = new JRadioButton("взят впервые");
		bgDisp.add(rbtDisp3);
		
		GroupLayout gl_pnlDisp = new GroupLayout(pnlDisp);
		gl_pnlDisp.setHorizontalGroup(
			gl_pnlDisp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDisp.createSequentialGroup()
					.addContainerGap()
					.addComponent(rbtDisp1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtDisp2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtDisp3)
					.addContainerGap(8, Short.MAX_VALUE))
		);
		gl_pnlDisp.setVerticalGroup(
			gl_pnlDisp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDisp.createSequentialGroup()
					.addGroup(gl_pnlDisp.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtDisp1)
						.addComponent(rbtDisp2)
						.addComponent(rbtDisp3))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlDisp.setLayout(gl_pnlDisp);
		
		JPanel pnlProvOzd = new JPanel();
		pnlProvOzd.setBorder(UIManager.getBorder("PopupMenu.border"));
		
		JLabel lblVmp = new JLabel("Высокотехнологичные виды медицинской помощи");
		
		JPanel pnlVmp1 = new JPanel();
		pnlVmp1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u043F\u043E \u0438\u0442\u043E\u0433\u0430\u043C \u043F\u0440\u043E\u0448\u043B\u043E\u0433\u043E \u0433\u043E\u0434\u0430", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		bgVmp1 = new ButtonGroup();
		
		rbtVmp1_1 = new JRadioButton("нет");
		bgVmp1.add(rbtVmp1_1);
		
		rbtVmp1_2 = new JRadioButton("да");
		bgVmp1.add(rbtVmp1_2);
		
		GroupLayout gl_pnlVmp1 = new GroupLayout(pnlVmp1);
		gl_pnlVmp1.setHorizontalGroup(
			gl_pnlVmp1.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 209, Short.MAX_VALUE)
				.addGroup(gl_pnlVmp1.createSequentialGroup()
					.addContainerGap(13, Short.MAX_VALUE)
					.addComponent(rbtVmp1_1)
					.addGap(18)
					.addComponent(rbtVmp1_2)
					.addGap(22))
		);
		gl_pnlVmp1.setVerticalGroup(
			gl_pnlVmp1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 46, Short.MAX_VALUE)
				.addGroup(gl_pnlVmp1.createSequentialGroup()
					.addGroup(gl_pnlVmp1.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtVmp1_1)
						.addComponent(rbtVmp1_2))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlVmp1.setLayout(gl_pnlVmp1);
		
		JPanel pnlVmp2 = new JPanel();
		pnlVmp2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u043F\u043E \u0438\u0442\u043E\u0433\u0430\u043C \u0442\u0435\u043A\u0443\u0449\u0435\u0439 \u0434\u0438\u0441\u043F\u0430\u043D\u0441\u0435\u0440\u0438\u0437\u0430\u0446\u0438\u0438", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		bgVmp2 = new ButtonGroup();
		
		 rbtVmp2_1 = new JRadioButton("рекомендованы");
		 bgVmp2.add(rbtVmp2_1);
		
		 rbtVmp2_2 = new JRadioButton("не рекомендованы");
		 bgVmp2.add(rbtVmp2_2);
		 
		GroupLayout gl_pnlVmp2 = new GroupLayout(pnlVmp2);
		gl_pnlVmp2.setHorizontalGroup(
			gl_pnlVmp2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlVmp2.createSequentialGroup()
					.addComponent(rbtVmp2_1)
					.addPreferredGap(ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
					.addComponent(rbtVmp2_2)
					.addGap(40))
		);
		gl_pnlVmp2.setVerticalGroup(
			gl_pnlVmp2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlVmp2.createSequentialGroup()
					.addGroup(gl_pnlVmp2.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtVmp2_2)
						.addComponent(rbtVmp2_1))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlVmp2.setLayout(gl_pnlVmp2);
		
		JLabel lblRecdop = new JLabel("Рекомендации по дополнительному обследованию для уточнения диагноза");
		
		JCheckBox cbRecdop3 = new JCheckBox("на федеральном уровне");
		cbRecdop3.setHorizontalTextPosition(SwingConstants.RIGHT);
		
		JCheckBox cbRecdop2 = new JCheckBox("на областном/краевом/республиканском уровне");
		cbRecdop2.setHorizontalTextPosition(SwingConstants.RIGHT);
		
		JCheckBox cbRecdop1 = new JCheckBox("на окружном уровне");
		cbRecdop1.setHorizontalTextPosition(SwingConstants.RIGHT);
		GroupLayout gl_pnlDiagPosle = new GroupLayout(pnlDiagPosle);
		gl_pnlDiagPosle.setHorizontalGroup(
			gl_pnlDiagPosle.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiagPosle.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_pnlDiagPosle.createParallelGroup(Alignment.LEADING)
						.addComponent(pnlDiag_po, GroupLayout.PREFERRED_SIZE, 502, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlDiagPosle.createSequentialGroup()
							.addComponent(btnAdd1, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(btnDel1, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(btnSave1, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 335, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDiag_po, GroupLayout.PREFERRED_SIZE, 455, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pnlDiagPosle.createSequentialGroup()
							.addComponent(pnlXzab, GroupLayout.PREFERRED_SIZE, 353, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pnlPu, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlDiagPosle.createSequentialGroup()
							.addComponent(lblDisp)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(pnlDisp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlDiagPosle.createSequentialGroup()
							.addComponent(pnlProvOzd, GroupLayout.PREFERRED_SIZE, 499, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addGroup(gl_pnlDiagPosle.createParallelGroup(Alignment.LEADING)
								.addComponent(cbRecdop1, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_pnlDiagPosle.createSequentialGroup()
									.addComponent(pnlVmp1, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(pnlVmp2, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblVmp)
								.addGroup(gl_pnlDiagPosle.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(cbRecdop2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(lblRecdop, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(cbRecdop3, GroupLayout.PREFERRED_SIZE, 339, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(27, Short.MAX_VALUE))
		);
		gl_pnlDiagPosle.setVerticalGroup(
			gl_pnlDiagPosle.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDiagPosle.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblDiag_po)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlDiag_po, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addGroup(gl_pnlDiagPosle.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAdd1)
						.addComponent(btnDel1)
						.addComponent(btnSave1))
					.addGap(18)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlDiagPosle.createParallelGroup(Alignment.LEADING)
						.addComponent(pnlXzab, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(pnlPu, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDiagPosle.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblDisp)
						.addComponent(pnlDisp, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_pnlDiagPosle.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlDiagPosle.createSequentialGroup()
							.addComponent(lblVmp)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pnlDiagPosle.createParallelGroup(Alignment.BASELINE)
								.addComponent(pnlVmp1, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addComponent(pnlVmp2, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblRecdop)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbRecdop1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(cbRecdop2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(cbRecdop3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))
						.addComponent(pnlProvOzd, GroupLayout.PREFERRED_SIZE, 297, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(16, Short.MAX_VALUE))
		);
		
		JLabel lblProvOzd = new JLabel("<html>Проведены лечебно-оздоровительные<br> и реабилитационные меропрития</html>");
		
		JLabel lblRecomLech = new JLabel("<html>Рекомендации по дальнейшему<br> лечению</html>");
		
		JLabel lblNet = new JLabel("нет");
		
		JLabel lblUslReb = new JLabel("в условиях дома ребенка");
		
		JLabel lblObrU = new JLabel("в образовательном учреждении");
		
		JLabel lblUSz = new JLabel("в учреждении соц.защиты");
		
		JLabel lblAmb = new JLabel("амбулаторное лечение");
		
		JLabel lblStMun = new JLabel("в стационаре муниц.уровня");
		
		JLabel lblStao = new JLabel("в стационаре АО");
		
		JLabel lblSubRf = new JLabel("в стационаре субъекта РФ");
		
		JLabel lblFedU = new JLabel("в стационаре федерального уровня");
		
		JLabel lblSan = new JLabel("в санатории");
		
		JCheckBox cbVrec1 = new JCheckBox("");
		
		JCheckBox cbVrec2 = new JCheckBox("");
		
		JCheckBox cbVrec3 = new JCheckBox("");
		
		JCheckBox cbVrec4 = new JCheckBox("");
		
		JCheckBox cbVrec5 = new JCheckBox("");
		
		JCheckBox cbVrec6 = new JCheckBox("");
		
		JCheckBox cbVrec7 = new JCheckBox("");
		
		JCheckBox cbVrec8 = new JCheckBox("");
		
		JCheckBox cbVrec9 = new JCheckBox("");
		
		JCheckBox cbVrec10 = new JCheckBox("");
		
		JCheckBox cbNrec1 = new JCheckBox("");
		
		JCheckBox cbNrec2 = new JCheckBox("");
		
		JCheckBox cbNrec3 = new JCheckBox("");
		
		JCheckBox cbNrec4 = new JCheckBox("");
		
		JCheckBox cbNrec5 = new JCheckBox("");
		
		JCheckBox cbNrec6 = new JCheckBox("");
		
		JCheckBox cbNrec7 = new JCheckBox("");
		
		JCheckBox cbNrec8 = new JCheckBox("");
		
		JCheckBox cbNrec9 = new JCheckBox("");
		
		JCheckBox cbNrec10 = new JCheckBox("");
		GroupLayout gl_pnlProvOzd = new GroupLayout(pnlProvOzd);
		gl_pnlProvOzd.setHorizontalGroup(
			gl_pnlProvOzd.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlProvOzd.createSequentialGroup()
					.addGroup(gl_pnlProvOzd.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlProvOzd.createSequentialGroup()
							.addGap(95)
							.addComponent(lblProvOzd, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlProvOzd.createSequentialGroup()
							.addGap(20)
							.addGroup(gl_pnlProvOzd.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNet)
								.addComponent(lblUslReb)
								.addComponent(lblObrU)
								.addComponent(lblUSz)
								.addComponent(lblAmb)
								.addComponent(lblStMun)
								.addComponent(lblStao)
								.addComponent(lblSubRf)
								.addComponent(lblSan)
								.addComponent(lblFedU))
							.addGap(47)
							.addGroup(gl_pnlProvOzd.createParallelGroup(Alignment.LEADING)
								.addComponent(cbVrec4, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbVrec5, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbVrec6, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbVrec7, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbVrec8, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbVrec9, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbVrec3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbVrec2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbVrec1)
								.addComponent(cbVrec10, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE))))
					.addGap(18)
					.addGroup(gl_pnlProvOzd.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlProvOzd.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_pnlProvOzd.createParallelGroup(Alignment.LEADING)
								.addComponent(cbNrec1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbNrec2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbNrec3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbNrec4, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbNrec5, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbNrec6, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbNrec7, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbNrec8, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbNrec9, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbNrec10, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
						.addComponent(lblRecomLech, GroupLayout.PREFERRED_SIZE, 180, GroupLayout.PREFERRED_SIZE)))
		);
		gl_pnlProvOzd.setVerticalGroup(
			gl_pnlProvOzd.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlProvOzd.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_pnlProvOzd.createParallelGroup(Alignment.LEADING)
						.addComponent(lblProvOzd)
						.addComponent(lblRecomLech))
					.addGap(7)
					.addGroup(gl_pnlProvOzd.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlProvOzd.createSequentialGroup()
							.addGroup(gl_pnlProvOzd.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_pnlProvOzd.createSequentialGroup()
									.addComponent(lblNet)
									.addGap(6)
									.addComponent(lblUslReb)
									.addGap(11)
									.addComponent(lblObrU)
									.addGap(11)
									.addComponent(lblUSz)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblAmb)
									.addGap(11)
									.addComponent(lblStMun)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblStao)
									.addGap(13)
									.addComponent(lblSubRf)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblFedU))
								.addGroup(gl_pnlProvOzd.createSequentialGroup()
									.addComponent(cbVrec1)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cbVrec2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cbVrec3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addGap(6)
									.addComponent(cbVrec4, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(cbVrec5, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(cbVrec6, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(cbVrec7, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(cbVrec8, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(cbVrec9, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
							.addGap(13)
							.addGroup(gl_pnlProvOzd.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblSan)
								.addComponent(cbVrec10, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_pnlProvOzd.createSequentialGroup()
							.addComponent(cbNrec1, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addComponent(cbNrec2, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addComponent(cbNrec3, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(cbNrec4, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(cbNrec5, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(cbNrec6, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(cbNrec7, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(cbNrec8, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(cbNrec9, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
							.addGap(13)
							.addComponent(cbNrec10, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		pnlProvOzd.setLayout(gl_pnlProvOzd);
		
		tabDiag_po = new CustomTable<>(false, false, Pdisp_ds.class, 9, "Диагноз");
		tabDiag_po.setFillsViewportHeight(true);
		scrollPane.setViewportView(tabDiag_po);
		pnlDiagPosle.setLayout(gl_pnlDiagPosle);
		frame.getContentPane().setLayout(groupLayout);
		
		instance = this;
	}

	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Диспансеризация");
		frame.setBounds(100, 100, 995, 737);
	}

	@Override
	public String getName() {
		return configuration.appName;
	}
}
