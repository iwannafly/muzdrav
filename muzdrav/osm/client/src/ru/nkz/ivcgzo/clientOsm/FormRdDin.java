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

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.PatientCommonInfo;
import ru.nkz.ivcgzo.thriftOsm.RdDinStruct;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
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
import javax.swing.ImageIcon;

public class FormRdDin extends JFrame {

	private JPanel contentPane;
	private RdDinStruct rddin;
	private PatientCommonInfo patient;
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
    CustomDateEditor SDataPos;
    CustomDateEditor SDataSl;
	private ThriftStringClassifierCombobox<StringClassifier> CBDiag;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBPolPl;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBPredPl;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBCerd;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBSerd1;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBOteki;
	private JTextField fam;
	private JTextField im;
	private JTextField ot;

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
		setBounds(100, 100, 810, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		rddin = new RdDinStruct();
		patient = new PatientCommonInfo();
/*		oslcode = rddin.getDspos();
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
		if (iw5==2){cerdname1 = "Аритмичное";}*/
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		
		JPanel panel_2 = new JPanel();
		
		final JSpinner SPdad = new JSpinner();
		SPdad.setModel(new SpinnerNumberModel(new Integer(rddin.art1), null,new Integer(220),new Integer(1)));
		rddin.setArt1((int) SPdad.getModel().getValue());
/*		patient.setFam((String) fam.getText());
		patient.setIm((String)im.getText());
		patient.setOt((String) ot.getText());*/
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 478, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE)
					.addGap(53))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(27)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(11)
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
		
		CBPolPl = new ThriftIntegerClassifierCombobox<>(true);
		
		CBPredPl = new ThriftIntegerClassifierCombobox<>(true);
		
		CBCerd = new ThriftIntegerClassifierCombobox<>(true);
		
		CBSerd1 = new ThriftIntegerClassifierCombobox<>(true);
		
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
		
//		final JSpinner SDataPos = new JSpinner();
//		SDataPos.setModel(new SpinnerDateModel(new Date(rddin.datapos), null, null, Calendar.DAY_OF_YEAR));
//        rddin.setDatapos((int) SDataPos.getModel().getValue());
		SDataPos = new CustomDateEditor();
		
        final JSpinner SSrok = new JSpinner();
		SSrok.setModel(new SpinnerNumberModel(4, 0, 42, 1));
		rddin.setSrok((int) SSrok.getModel().getValue());
		
		final JSpinner SVes = new JSpinner();
		SVes.setModel(new SpinnerNumberModel(new Integer(60), null, null, new Integer(1)));
		rddin.setVes((int) SVes.getModel().getValue());
		
		final JSpinner SOkrj = new JSpinner();
		SOkrj.setModel(new SpinnerNumberModel(new Integer(60), null, null, new Integer(1)));
		rddin.setOj((int) SOkrj.getModel().getValue());
		
		final JSpinner SVdm = new JSpinner();
		SVdm.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		rddin.setHdm((int) SVdm.getModel().getValue());
		
		CBDiag = new ThriftStringClassifierCombobox<>(true);
		
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
		
		CBOteki = new ThriftIntegerClassifierCombobox<>(true);
		
//		final JSpinner SDataSl = new JSpinner();
//		SDataSl.setModel(new SpinnerDateModel(new Date(1335373200000L), null, null, Calendar.DAY_OF_YEAR));
		SDataSl = new CustomDateEditor();

		
		JButton SButton = new JButton("");
		SButton.setToolTipText("Сохранить");
		SButton.setIcon(new ImageIcon(FormRdDin.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1341981970_Accept.png")));
		SButton.addActionListener(new ActionListener() {
	/*			private int iw1(int iw1){
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
				};*/
				
	public void actionPerformed(ActionEvent e) {
		rddin.setArt1((int) SPdad.getModel().getValue());
		rddin.setArt2((int) SPsad.getModel().getValue());
		rddin.setArt3((int) SLdad.getModel().getValue());
		rddin.setArt4((int) SLsad.getModel().getValue());
		rddin.setChcc((int) SChcc.getModel().getValue());
		rddin.setDatapos(SDataPos.getDate().getTime());
		rddin.setDatasl(SDataSl.getDate().getTime());
		rddin.setHdm((int) SVdm.getModel().getValue());
		rddin.setOj((int) SOkrj.getModel().getValue());
		rddin.setSpl((int) STolP.getModel().getValue());
		rddin.setSrok((int) SSrok.getModel().getValue());
		rddin.setVes((int) SVes.getModel().getValue());
//		rddin.setDspos(CBDiag.setData(MainForm.tcl.getn_db6()));
//		rddin.setPolpl(CBPolPl.setData(MainForm.tcl.getn_db1()));
//		rddin.setOteki(CBPredPl.setData(MainForm.tcl.getn_db2()));
//		rddin.setPredpl(CBCerd.setData(MainForm.tcl.getn_db3()));
//		rddin.setSerd1(CBSerd1.setData(MainForm.tcl.getn_db4()));
//		rddin.setSerd(CBOteki.setData(MainForm.tcl.getn_db5()));
			}
		});
		
		JButton Nbutton = new JButton("");
		Nbutton.setIcon(new ImageIcon(FormRdDin.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
		Nbutton.setToolTipText("Новое посещение");
		Nbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setToolTipText("Удалить");
		btnNewButton.setIcon(new ImageIcon(FormRdDin.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789259_Delete.png")));
		
		fam = new JTextField();
		fam.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		fam.setColumns(10);
		fam.setText(patient.getFam());
		
		im = new JTextField();
		im.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		im.setColumns(10);
		im.setText(patient.getIm());
		
		ot = new JTextField();
		ot.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		ot.setColumns(10);
		ot.setText(patient.getOt());
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_panel_1.createSequentialGroup()
											.addComponent(LLdad)
											.addGap(18)
											.addComponent(SLdad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addComponent(LDataSl)
										.addComponent(LDiag)
										.addComponent(LOteki)
										.addComponent(LtolPlac)
										.addGroup(gl_panel_1.createSequentialGroup()
											.addComponent(LPdad)
											.addGap(18)
											.addComponent(SPdad, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)))
									.addGap(18)
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addComponent(LLsad)
										.addComponent(LPsad)))
								.addComponent(LVes)
								.addComponent(LSrok)
								.addComponent(LDataPos))
							.addGap(3)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, 78, Short.MAX_VALUE)
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addComponent(SDataSl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(CBDiag, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
										.addComponent(CBOteki, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_panel_1.createSequentialGroup()
											.addGap(67)
											.addComponent(STolP, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))))
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(16)
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
										.addComponent(SLsad, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(SPsad, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE)
										.addComponent(SVdm, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
										.addComponent(SOkrj, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
										.addComponent(SVes, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
										.addComponent(SDataPos, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
										.addComponent(SSrok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(257))))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(LVdm)
							.addContainerGap(446, Short.MAX_VALUE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(LOkrJ)
							.addContainerGap(364, Short.MAX_VALUE))))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(26)
					.addComponent(Nbutton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(SButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnNewButton)
					.addContainerGap(323, Short.MAX_VALUE))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(fam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(im, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(176, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(fam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(im, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(ot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(Nbutton)
								.addComponent(btnNewButton))
							.addGap(18)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
								.addComponent(LDataPos)
								.addComponent(SDataPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(SButton))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(LSrok)
						.addComponent(SSrok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(LVes)
						.addComponent(SVes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(LOkrJ)
						.addComponent(SOkrj, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(LVdm)
						.addComponent(SVdm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(LPdad)
						.addComponent(SPdad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(LPsad)
						.addComponent(SPsad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(LLdad)
						.addComponent(SLdad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(SLsad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(LLsad))
					.addGap(24)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(LtolPlac)
						.addComponent(STolP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(LOteki)
						.addComponent(CBOteki, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(17)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(LDiag)
						.addComponent(CBDiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(14)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(LDataSl)
						.addComponent(SDataSl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(24))
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


