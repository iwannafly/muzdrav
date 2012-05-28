package ru.nkz.ivcgzo.clientOsm;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;

import javax.swing.JOptionPane;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.RdDinStruct;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;

import org.apache.thrift.TException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTable;

public class FormRdDin extends JFrame {

	private JPanel contentPane;
	private RdDinStruct rddin;
	private CustomTable<RdDinStruct, RdDinStruct._Fields> tablePos;
    private String oslname;
    private String oslcode;
    private int iw1;
    private int iw2;
    private int iw3;
    private int iw4;
    private int iw5;
    private String otname;
    private String polplname;
    private String predname;
    private String cerdname;
    private String cerdname1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormRdDin frame = new FormRdDin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FormRdDin() {
		setTitle("Динамика диспансерного наблюдения за беременной");
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
			}
		});
				setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 793, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		rddin = new RdDinStruct();
		oslcode = rddin.getDspos();
		if (oslcode.equals("O21")){oslname = "Чрезмерная рвота";}
		if (oslcode.equals("O44")){oslname = "Предлежание плаценты";}
		if (oslcode.equals("O45")){oslname = "Отслойка плаценты";}
		if (oslcode.equals("O23.0")){oslname = "Инфекция почек";}
		if (oslcode.equals("O24")){oslname = "Сахарный диабет";}
		if (oslcode.equals("O30")){oslname = "Многоплодная беременность";}
		if (oslcode.equals("O32")){oslname = "Неправильное предлежание плода";}
		if (oslcode.equals("O36.0")){oslname = "Изоиммунизация";}
		if (oslcode.equals("O99.0")){oslname = "Анемия";}
		if (oslcode.equals("O48")){oslname = "Переношенная беременность";}
		if (oslcode.equals("O11")){oslname = "Существовавшая ранее гипертензия с присоед.протеинурией";}
		if (oslcode.equals("O12")){oslname = "Вызванные беременностью отеки и протеинурия без гипертензии";}
		if (oslcode.equals("O13")){oslname = "Гипертензия без значительной протеинурии";}
		if (oslcode.equals("O14")){oslname = "Гипертензия со значительной протеинурией";}
		if (oslcode.equals("O15")){oslname = "Экламсия";}
		if (oslcode==null){oslname = "";}
        iw1 = rddin.getPolpl();
		if (iw1 == 1){polplname ="Продольное";}
		if (iw1 == 2){polplname ="Поперечное";}
		if (iw1 == 3){polplname ="Косое";}
        iw2 = rddin.getOteki();
        if (iw2 == 1){otname ="Нижние конечности";}
        if (iw2 == 2){otname ="Верхние конечности";}
        if (iw2 == 3){otname ="Верхняя брюшная стенка";}
        if (iw2 == 4){otname ="Генерализованные";}
        if (iw2 == 0){otname ="Нет";}
         iw3 = rddin.getSerd();
		if (iw3==0){cerdname = "";}
		if (iw3==1){cerdname = "Ясное";}
		if (iw3==2){cerdname = "Приглушенное";}
		if (iw3==3){cerdname = "Глухое";}
		iw4 = rddin.getPredpl();
		if (iw4==0){predname = "";}
		if (iw4==1){predname = "Голова";}
		if (iw4==2){predname = "Таз";}
		iw5 = rddin.getSerd1();
		if (iw5==0){cerdname1 = "";}
		if (iw5==1){cerdname1 = "Ритмичное";}
		if (iw5==2){cerdname1 = "Аритмичное";}
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		
		JPanel panel_2 = new JPanel();
		
		JButton Nbutton = new JButton("Новое посещение");
		
		final JSpinner SPdad = new JSpinner();
		SPdad.setModel(new SpinnerNumberModel(new Integer(rddin.art1), null,new Integer(220),new Integer(1)));
		rddin.setArt1((int) SPdad.getModel().getValue());
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Nbutton)
							.addGap(34))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
							.addGap(38)
							.addComponent(Nbutton))
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(19, Short.MAX_VALUE))
		);
		
		JLabel LChcc = new JLabel("ЧСС плода");
		
		JLabel LPolPl = new JLabel("Положение плода");
		
		JLabel LPredPl = new JLabel("Предлежание плода");
		
		JLabel LSerd = new JLabel("Сердцебиение плода");
		
		final JSpinner SChcc = new JSpinner();
		SChcc.setModel(new SpinnerNumberModel(new Integer(rddin.chcc), new Integer(60), null, new Integer(1)));
        rddin.setChcc((int) SChcc.getModel().getValue());
		
		final JComboBox CBPolPl = new JComboBox();
		CBPolPl.setModel(new DefaultComboBoxModel(new String[] {"", "Продольное", "Поперечное", "Косое"}));
		CBPolPl.setSelectedItem(polplname);
		
		final JComboBox CBPredPl = new JComboBox();
		CBPredPl.setModel(new DefaultComboBoxModel(new String[] {"", "Голова", "Таз"}));
		CBPredPl.setSelectedItem(predname);
		
		final JComboBox CBCerd = new JComboBox();
		CBCerd.setModel(new DefaultComboBoxModel(new String[] {"", "Ритмичное", "Аритмичное"}));
		CBCerd.setSelectedItem(cerdname);
		
		final JComboBox CBSerd1 = new JComboBox();
		CBSerd1.setModel(new DefaultComboBoxModel(new String[] {"", "Ясное", "Приглушенное", "Глухое"}));
		CBSerd1.setSelectedItem(cerdname1);
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addContainerGap()
							.addComponent(LChcc))
						.addComponent(LPolPl)
						.addComponent(LSerd)
						.addComponent(LPredPl))
					.addGap(29)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(CBSerd1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel_2.createSequentialGroup()
								.addComponent(CBPredPl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(115))
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(CBCerd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addComponent(CBPolPl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(SChcc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addContainerGap(108, Short.MAX_VALUE))))))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(LChcc)
						.addComponent(SChcc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(LPolPl)
						.addComponent(CBPolPl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(LPredPl)
						.addComponent(CBPredPl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(LSerd)
						.addComponent(CBCerd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(13)
					.addComponent(CBSerd1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);
		
		JLabel LDataPos = new JLabel("Дата посещения");
		
		JLabel LSrok = new JLabel("Срок беременности");
		
		JLabel LVes = new JLabel("Вес");
		
		JLabel LOkrJ = new JLabel("Окружность живота");
		
		JLabel LVdm = new JLabel("ВДМ");
		
		JLabel LDiag = new JLabel("Диагноз при наблюдении");
		
		JLabel LPdad = new JLabel("Правая          ДАД");
		
		JLabel LPsad = new JLabel(" САД");
		
		JLabel LLdad = new JLabel(" Левая           ДАД");
		
		JLabel LLsad = new JLabel(" САД");
		
		JLabel LtolPlac = new JLabel("Толщина плаценты");
		
		JLabel LOteki = new JLabel("Отеки");
		
		JLabel LDataSl = new JLabel("Дата следующего посещения");
		
		final JSpinner SDataPos = new JSpinner();
		SDataPos.setModel(new SpinnerDateModel(new Date(rddin.datapos), null, null, Calendar.DAY_OF_YEAR));
        rddin.setDatapos((int) SDataPos.getModel().getValue());
		
        final JSpinner SSrok = new JSpinner();
		SSrok.setModel(new SpinnerNumberModel(new Integer(rddin.srok),null, new Integer(40),new Integer(1)));
		rddin.setSrok((int) SSrok.getModel().getValue());
		
		final JSpinner SVes = new JSpinner();
		SVes.setModel(new SpinnerNumberModel(new Integer(rddin.ves), null, null, new Integer(1)));
		rddin.setVes((int) SVes.getModel().getValue());
		
		final JSpinner SOkrj = new JSpinner();
		SOkrj.setModel(new SpinnerNumberModel(new Integer(rddin.oj), null, null, new Integer(1)));
		rddin.setOj((int) SOkrj.getModel().getValue());
		
		final JSpinner SVdm = new JSpinner();
		SVdm.setModel(new SpinnerNumberModel(new Integer(rddin.hdm), null, null, new Integer(1)));
		rddin.setHdm((int) SVdm.getModel().getValue());
		
		final JComboBox CBDiag = new JComboBox();
		CBDiag.setModel(new DefaultComboBoxModel(new String[] {"", "Чрезмерная рвота", "Предлежание плаценты","Отслойка плаценты" ,"Инфекция почек", "Сахарный диабет", "Многоплодная беременность", "Неправильное предлежание плода", "Изоиммунизация", "Анемия", "Переношенная беременность", "Существовавшая ранее гипертензия с присоед.протеинурией", "Вызванные беременностью отеки и протеинурия без гипертензии", "Гипертензия без значительной протеинурии", "Гипертензия со значительной протеинурией", "Экламсия"}));
		CBDiag.setSelectedItem(oslname);
		
		final JSpinner SPsad = new JSpinner();
		SPsad.setModel(new SpinnerNumberModel(new Integer(rddin.art2), null ,new Integer(120),new Integer(1)));
		rddin.setArt2((int) SPsad.getModel().getValue());
		
		final JSpinner SLdad = new JSpinner();
		SLdad.setModel(new SpinnerNumberModel(new Integer(rddin.art3), new Integer(50),new Integer(220),new Integer(1)));
		rddin.setArt3((int) SLdad.getModel().getValue());
		
		final JSpinner SLsad = new JSpinner();
		SLsad.setModel(new SpinnerNumberModel(new Integer(rddin.art4), new Integer(30),new Integer(120),new Integer(1)));
		rddin.setArt4((int) SLsad.getModel().getValue());
		
		final JSpinner STolP = new JSpinner();
		STolP.setModel(new SpinnerNumberModel(new Integer(rddin.spl), new Integer(1), null, new Integer(1)));
		rddin.setSpl((int) STolP.getModel().getValue());
		
		final JComboBox CBOteki = new JComboBox();
		CBOteki.setModel(new DefaultComboBoxModel(new String[] {"Нет", "Нижние конечности", "Верхние конечности", "Верхняя брюшная стенка", "Генерализованные"}));
		CBOteki.setSelectedItem(otname);
		
		final JSpinner SDataSl = new JSpinner();
		SDataSl.setModel(new SpinnerDateModel(new Date(1335373200000L), null, null, Calendar.DAY_OF_YEAR));

		
		JButton SButton = new JButton("Сохранить");
		SButton.addActionListener(new ActionListener() {
				private int iw1(int iw1){
					if (CBDiag.getSelectedItem().equals("Продольное")){iw1 = 1;}
					if (CBDiag.getSelectedItem().equals("Поперечное")){iw1 = 2;}
					if (CBDiag.getSelectedItem().equals("Косое")){iw1 = 3;}
					return iw1;
				};
				private String oslcode (String oslcode){
								if (CBDiag.getSelectedItem().equals("Чрезмерная рвота")){oslcode="O21";}
					if (CBDiag.getSelectedItem().equals("Предлежание плаценты")){oslcode="O44";}
					if (CBDiag.getSelectedItem().equals("Отслойка плаценты")){oslcode="O45";}
					if (CBDiag.getSelectedItem().equals("Инфекция почек")){oslcode="O23.0";}
					if (CBDiag.getSelectedItem().equals("Сахарный диабет")){oslcode="O24";}
					if (CBDiag.getSelectedItem().equals("Многоплодная беременность")){oslcode="O30";}
					if (CBDiag.getSelectedItem().equals("Неправильное предлежание плода")){oslcode="O32";}
					if (CBDiag.getSelectedItem().equals("Изоиммунизация")){oslcode="O32";}
					if (CBDiag.getSelectedItem().equals("Анемия")){oslcode="O99.0";}
					if (CBDiag.getSelectedItem().equals("Переношенная беременность")){oslcode="O48";}
					if (CBDiag.getSelectedItem().equals("Существовавшая ранее гипертензия с присоед.протеинурией")){oslcode="O11";}
					if (CBDiag.getSelectedItem().equals("Вызванные беременностью отеки и протеинурия без гипертензии")){oslcode="O12";}
					if (CBDiag.getSelectedItem().equals("Гипертензия без значительной протеинурии")){oslcode="O13";}
					if (CBDiag.getSelectedItem().equals("Гипертензия со значительной протеинурией")){oslcode="O14";}
					if (CBDiag.getSelectedItem().equals("Экламсия")){oslcode="O15";}
					if (CBDiag.getSelectedItem().equals("")){oslcode = null;}
					return oslcode;
				};
				private int ppp1(int iw2){
					if (CBOteki.getSelectedItem().equals("Нижние конечности")){iw2 = 1;}
					if (CBOteki.getSelectedItem().equals("Верхние конечности")){iw2 = 2;}
					if (CBOteki.getSelectedItem().equals("Верхняя брюшная стенка")){iw2 = 3;}
					if (CBOteki.getSelectedItem().equals("Генерализованные")){iw2 = 2;}
					if (CBOteki.getSelectedItem().equals("")){iw2 = 0;}
					return iw2;
				};
				private int ppp2(int iw3){
					if (CBCerd.getSelectedItem().equals("Ясное")){iw3 = 1;}
					if (CBCerd.getSelectedItem().equals("Приглушенное")){iw3 = 2;}
					if (CBCerd.getSelectedItem().equals("Глухое")){iw3 = 3;}
					if (CBCerd.getSelectedItem().equals("")){iw3 = 0;}
					return iw3;
				};
				private int ppp3(int iw4){
					if (CBDiag.getSelectedItem().equals("Голова")){iw4 = 1;}
					if (CBDiag.getSelectedItem().equals("Таз")){iw4 = 2;}
					if (CBDiag.getSelectedItem().equals("")){iw4 = 0;}
					return iw4;
				};
				private int ppp4(int iw5){
					if (CBDiag.getSelectedItem().equals("Ритмичное")){iw5 = 1;}
					if (CBDiag.getSelectedItem().equals("Аритмичное")){iw5 = 2;}
					if (CBDiag.getSelectedItem().equals("")){iw5 = 0;}
					return iw5;
				};
				
	public void actionPerformed(ActionEvent e) {
		rddin.setArt1((int) SPdad.getModel().getValue());
		rddin.setArt2((int) SPsad.getModel().getValue());
		rddin.setArt3((int) SLdad.getModel().getValue());
		rddin.setArt4((int) SLsad.getModel().getValue());
		rddin.setChcc((int) SChcc.getModel().getValue());
		rddin.setDatapos((long) SDataPos.getModel().getValue());
		rddin.setDatasl((long) SDataSl.getModel().getValue());
		rddin.setHdm((int) SVdm.getModel().getValue());
		rddin.setOj((int) SOkrj.getModel().getValue());
		rddin.setSpl((int) STolP.getModel().getValue());
		rddin.setSrok((int) SSrok.getModel().getValue());
		rddin.setVes((int) SVes.getModel().getValue());
		rddin.setDspos(oslcode);
		rddin.setPolpl(iw1);
		rddin.setOteki(iw2);
		rddin.setPredpl(iw4);
		rddin.setSerd1(iw5);
		rddin.setSerd(iw3);
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel_1.createSequentialGroup()
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
									.addComponent(LDataPos)
									.addComponent(LSrok)
									.addComponent(LVes)
									.addComponent(LOkrJ)
									.addComponent(LVdm)
									.addComponent(LDiag)
									.addGroup(gl_panel_1.createSequentialGroup()
										.addComponent(LPdad)
										.addGap(18)
										.addComponent(SPdad, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
									.addGroup(gl_panel_1.createSequentialGroup()
										.addComponent(LLdad)
										.addGap(18)
										.addComponent(SLdad, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
									.addComponent(LDataSl))
								.addGap(18)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel_1.createSequentialGroup()
										.addComponent(LPsad)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
											.addComponent(SDataSl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_panel_1.createSequentialGroup()
												.addGap(73)
												.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
													.addComponent(CBOteki, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
													.addGroup(gl_panel_1.createSequentialGroup()
														.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
															.addComponent(CBDiag, 0, 89, Short.MAX_VALUE)
															.addComponent(SSrok, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
															.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
																.addComponent(SVdm, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																.addComponent(SOkrj, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																.addComponent(SVes, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
															.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
																.addComponent(STolP, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																.addComponent(SLsad, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																.addComponent(SPsad, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 52, Short.MAX_VALUE)))
														.addGap(67))))
											.addComponent(SDataPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
									.addComponent(LLsad))
								.addGap(44))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addComponent(LtolPlac)
								.addContainerGap(375, Short.MAX_VALUE))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addComponent(LOteki)
								.addContainerGap(441, Short.MAX_VALUE)))
						.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
							.addComponent(SButton)
							.addContainerGap())))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(19)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(LDataPos)
						.addComponent(SDataPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(15)
							.addComponent(LSrok)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(LVes)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(LOkrJ)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(LVdm)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(LDiag))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(6)
							.addComponent(SSrok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(SVes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(SOkrj, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(SVdm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(CBDiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(LPdad)
						.addComponent(SPdad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(LPsad)
						.addComponent(SPsad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(LLdad)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(SLdad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(LLsad)
							.addComponent(SLsad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(LtolPlac)
						.addComponent(STolP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(LOteki)
						.addComponent(CBOteki, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(41)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addComponent(LDataSl)
								.addComponent(SDataSl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(SButton)
							.addGap(19))))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		tablePos = new CustomTable<>(true, true, RdDinStruct.class, 2, "Дата посещения", 3, "Срок",4, "Вес", 5, "Окружность живота",6 ,"ВДМ",7 , "Диагноз", 12, "Дата след. посещ.");
		tablePos.setDateField(2);
		tablePos.setFillsViewportHeight(true);
		scrollPane.setViewportView(tablePos);
	}
	};


