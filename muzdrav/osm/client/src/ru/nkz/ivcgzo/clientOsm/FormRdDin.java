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
import ru.nkz.ivcgzo.thriftOsm.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.RdDinStruct;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;

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
    JSpinner SSrok ;
	JSpinner SVes;
	JSpinner SOkrj;
	JSpinner SVdm;
	JSpinner SPsad;
	JSpinner SLdad;
	JSpinner SLsad;
	JSpinner STolP;
	JSpinner SChcc;
	JSpinner SPdad;
	

	/**
	 * Create the frame.
	 */
	public FormRdDin() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				JOptionPane.showMessageDialog(FormRdDin.this,  Vvod.zapVr.getId_pvizit());
		//		rdsl = MainForm.tcl.getRdSlInfo(Vvod.zapVr.getId_pvizit(), Vvod.zapVr.getNpasp());
		//		setPostBerData();
		//		rddin = MainForm.tcl.getRdDinInfo(Vvod.zapVr.getId_pvizit(), Vvod.zapVr.getNpasp());
				fam.setText(Vvod.zapVr.getFam());
				im.setText(Vvod.zapVr.getIm());
				ot.setText(Vvod.zapVr.getOth());
			}
		});
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
		if (rddin.isSetPredpl())
			CBPredPl.setSelectedPcod(rddin.getPredpl());
		else
			CBPredPl.setSelectedItem(null);
		if (rddin.isSetPolpl())
			CBPolPl.setSelectedPcod(rddin.getPolpl());
		else
			CBPolPl.setSelectedItem(null);
		if (rddin.isSetSerd())
			CBCerd.setSelectedPcod(rddin.getSerd());
		else
			CBCerd.setSelectedItem(null);
		if (rddin.isSetSerd1())
			CBSerd1.setSelectedPcod(rddin.getSerd1());
		else
			CBSerd1.setSelectedItem(null);
		if (rddin.isSetOteki())
			CBOteki.setSelectedPcod(rddin.getOteki());
		else
			CBOteki.setSelectedItem(null);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		
		JPanel panel_2 = new JPanel();
		
		SPdad = new JSpinner();
		SPdad.setModel(new SpinnerNumberModel(new Integer(rddin.art1), null,new Integer(220),new Integer(1)));
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
		
		SChcc = new JSpinner();
		SChcc.setModel(new SpinnerNumberModel(new Integer(rddin.chcc), new Integer(60), null, new Integer(1)));
 		
		CBPolPl = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db1);
		
		CBPredPl = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db2);
		
		CBCerd = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db3);
		
		CBSerd1 = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db4);
		
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
		
		SDataPos = new CustomDateEditor();
		
        SSrok = new JSpinner();
		SSrok.setModel(new SpinnerNumberModel(4, 0, 42, 1));
		
		SVes = new JSpinner();
		SVes.setModel(new SpinnerNumberModel(new Double(60), null, null, new Double(0)));
		
		SOkrj = new JSpinner();
		SOkrj.setModel(new SpinnerNumberModel(new Integer(60), null, null, new Integer(1)));
		
		SVdm = new JSpinner();
		SVdm.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		
		CBDiag = new ThriftStringClassifierCombobox<>(StringClassifiers.n_db6);
		
		SPsad = new JSpinner();
		SPsad.setModel(new SpinnerNumberModel(new Integer(80), null ,new Integer(120),new Integer(1)));
		
		SLdad = new JSpinner();
		SLdad.setModel(new SpinnerNumberModel(new Integer(120), new Integer(50),new Integer(220),new Integer(1)));
		
		SLsad = new JSpinner();
		SLsad.setModel(new SpinnerNumberModel(new Integer(80), new Integer(30),new Integer(120),new Integer(1)));
		
		STolP = new JSpinner();
		STolP.setModel(new SpinnerNumberModel(new Integer(2), new Integer(1), null, new Integer(1)));
		
		CBOteki = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db5);
		
		SDataSl = new CustomDateEditor();

		
		JButton SButton = new JButton("");
		SButton.setToolTipText("Сохранить");
		SButton.setIcon(new ImageIcon(FormRdDin.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1341981970_Accept.png")));
		SButton.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		rddin.setArt1((int) SPdad.getModel().getValue());
		rddin.setArt2((int) SPsad.getModel().getValue());
		rddin.setArt3((int) SLdad.getModel().getValue());
		rddin.setArt4((int) SLsad.getModel().getValue());
		rddin.setChcc((int) SChcc.getModel().getValue());
//		rddin.setDatapos(SDataPos.getDate().getTime());
//		rddin.setDatasl(SDataSl.getDate().getTime());
		rddin.setHdm((int) SVdm.getModel().getValue());
		rddin.setOj((int) SOkrj.getModel().getValue());
		rddin.setSpl((int) STolP.getModel().getValue());
		rddin.setSrok((int) SSrok.getModel().getValue());
		rddin.setVes((double) SVes.getModel().getValue());
		if (CBPredPl.getSelectedPcod() != null)
			rddin.setPredpl(CBPredPl.getSelectedPcod());
			else rddin.unsetPredpl();
		if (CBPolPl.getSelectedPcod() != null)
			rddin.setPolpl(CBPolPl.getSelectedPcod());
			else rddin.unsetPolpl();
		if (CBSerd1.getSelectedPcod() != null)
			rddin.setSerd1(CBSerd1.getSelectedPcod());
			else rddin.unsetSerd1();
		if (CBCerd.getSelectedPcod() != null)
			rddin.setSerd(CBCerd.getSelectedPcod());
			else rddin.unsetSerd();
		if (CBOteki.getSelectedPcod() != null)
			rddin.setOteki(CBOteki.getSelectedPcod());
			else rddin.unsetOteki();
//		rddin.setDspos((string) CBDspos.getSelectedKod_Mkb.)//диагноз заменить на kod_mkb (вместо PCOD)
			}
		});
		
		JButton Nbutton = new JButton("");
		Nbutton.setIcon(new ImageIcon(FormRdDin.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
		Nbutton.setToolTipText("Новое посещение");
		Nbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				RdDinStruct rddin = new RdDinStruct();
				setDefaultValues();
			}
		});
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setToolTipText("Удалить");
		btnNewButton.setIcon(new ImageIcon(FormRdDin.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789259_Delete.png")));
		
		fam = new JTextField();
		fam.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		fam.setColumns(10);
//		fam.setText(Vvod.zapVr.fam);
		
		im = new JTextField();
		im.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		im.setColumns(10);
//		im.setText(Vvod.zapVr.im);
		
		ot = new JTextField();
		ot.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		ot.setColumns(10);
//		ot.setText(Vvod.zapVr.oth);
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
	protected void setDefaultValues() {
		// TODO Auto-generated method stub
	rddin.setId_pvizit(Vvod.zapVr.getId_pvizit());
	rddin.setNpasp(Vvod.zapVr.getNpasp());
	rddin.setArt1(120);
	rddin.setArt2(80);
	rddin.setArt3(120);
	rddin.setArt4(80);
	rddin.setChcc(70);
	rddin.setHdm(0);
//	rddin.setDspos(Vvod.zapVr.)//диагноз при постановке
//	rddin.setId_rd_sl(FormRdSl.rdsl.id);
	rddin.setOj(60);
	rddin.setSpl(0);
//	rddin.setSrok(srok);
	}
	public void onConnect() throws PatientNotFoundException {
		try {
			PatientCommonInfo inf;
		inf = MainForm.tcl.getPatientCommonInfo(Vvod.zapVr.npasp);
//						tfPatient.setText("Пациент: "+inf.getFam()+" "+inf.getIm()+" "+inf.getOt()+" Номер и серия полиса: "+inf.getPoms_ser()+"  "+inf.getPoms_nom());
fam.setText(inf.getFam());
im.setText(inf.getIm());
ot.setText(inf.getOt());
		    CBDiag.setData(MainForm.tcl.get_n_db6());
			CBPolPl.setData(MainForm.tcl.get_n_db1());
			CBPredPl.setData(MainForm.tcl.get_n_db2());
  	        CBCerd.setData(MainForm.tcl.get_n_db3());
			CBSerd1.setData(MainForm.tcl.get_n_db4());
			CBOteki.setData(MainForm.tcl.get_n_db5());
			
			
		} catch (KmiacServerException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
			MainForm.conMan.reconnect(e);
		}
	}

	};


