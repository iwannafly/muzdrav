package ru.nkz.ivcgzo.clientOsm;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.SimpleDateFormat;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Pvizit;
import ru.nkz.ivcgzo.thriftOsm.PvizitAmb;
import ru.nkz.ivcgzo.thriftOsm.RdDinStruct;

public class FormRdDin extends JFrame {
	private static final long serialVersionUID = 553969304358351170L;
	private JPanel contentPane;
	private RdDinStruct rddin;
	public static PvizitAmb pvizitAmb;
	public static Pvizit pvizit;
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
	private int mes;
	private int br;
	private int rost;
	private double ves;
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
		setExtendedState(Frame.MAXIMIZED_BOTH);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
//				JOptionPane.showMessageDialog(FormRdDin.this,  Vvod.zapVr.getId_pvizit());
				try {
					System.out.println(Vvod.zapVr.getId_pvizit());
	 			    fam.setText(Vvod.zapVr.getFam());
					im.setText(Vvod.zapVr.getIm());
					ot.setText(Vvod.zapVr.getOth());
					SDataPos.setDate(Vvod.pvizitAmb.getDatap());
					tablePos.setData(MainForm.tcl.getRdDinInfo(Vvod.pvizitAmb.id_obr, Vvod.pvizitAmb.npasp));
//					System.out.println(tablePos.getRowCount());		
				} catch (KmiacServerException e) {
					e.printStackTrace();
				} catch (TException e) {
					MainForm.conMan.reconnect(e);
				}
				
			}
		});
		setTitle("Динамика диспансерного наблюдения за беременной");
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
			}
		});
		JLabel LChcc = new JLabel("ЧСС плода");
		
		JLabel LPolPl = new JLabel("Положение плода");
		
		JLabel LPredPl = new JLabel("Предлежание плода");
		
		JLabel LSerd = new JLabel("Сердцебиение плода");
		
		SChcc = new JSpinner();
		SChcc.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
 		
		CBPolPl = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db1);
		
		CBPredPl = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db2);
		
		CBCerd = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db3);
		
		CBSerd1 = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db4);
		
		SDataPos = new CustomDateEditor();
		
        SSrok = new JSpinner();
		SSrok.setModel(new SpinnerNumberModel(0, 0, 42, 1));
		
		SVes = new JSpinner();
		SVes.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(1)));
		
		SOkrj = new JSpinner();
		SOkrj.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		
		SVdm = new JSpinner();
		SVdm.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		
		CBDiag = new ThriftStringClassifierCombobox<>(StringClassifiers.n_db6);
		
		SPsad = new JSpinner();
		SPsad.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		
		SLdad = new JSpinner();
		SLdad.setModel(new SpinnerNumberModel(0, 0, 220, 1));
		
		SLsad = new JSpinner();
		SLsad.setModel(new SpinnerNumberModel(0, 0, 120, 1));
		
		STolP = new JSpinner();
		STolP.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		
		CBOteki = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db5);
		
		SDataSl = new CustomDateEditor();
		setBounds(100, 100, 810, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
//		rddin = new RdDinStruct();
//		patient = new PatientCommonInfo();
//		if (rddin.isSetPredpl())
//			CBPredPl.setSelectedPcod(rddin.getPredpl());
//		else
//			CBPredPl.setSelectedItem(null);
//		if (rddin.isSetPolpl())
//			CBPolPl.setSelectedPcod(rddin.getPolpl());
//		else
//			CBPolPl.setSelectedItem(null);
//		if (rddin.isSetSerd())
//			CBCerd.setSelectedPcod(rddin.getSerd());
//		else
//			CBCerd.setSelectedItem(null);
//		if (rddin.isSetSerd1())
//			CBSerd1.setSelectedPcod(rddin.getSerd1());
//		else
//			CBSerd1.setSelectedItem(null);
//		if (rddin.isSetOteki())
//			CBOteki.setSelectedPcod(rddin.getOteki());
//		else
//			CBOteki.setSelectedItem(null);
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		
		JPanel panel_2 = new JPanel();
		
		SPdad = new JSpinner();
		SPdad.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 478, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE)
					.addGap(13))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(11)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(135)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(19, Short.MAX_VALUE))
		);
		
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
						.addComponent(CBSerd1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(CBPredPl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(CBCerd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(CBPolPl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(SChcc, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(115, Short.MAX_VALUE))
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
		
		
		JButton SButton = new JButton("");
		SButton.setToolTipText("Сохранить");
		SButton.setIcon(new ImageIcon(FormRdDin.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1341981970_Accept.png")));
		SButton.addActionListener(new ActionListener() {
	public void actionPerformed(ActionEvent e) {
		try {
			SimpleDateFormat frm = new SimpleDateFormat("MM");
			int mes = Integer.parseInt(frm.format(Vvod.zapVr.getDatar()));
			if (mes == 1) br = br+1;
			if (mes == 2) br = br+1;
			if (mes == 12) br = br+1;
			rddin.setGrr(0);
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
		if (CBDiag.getSelectedPcod() != null)
		{	rddin.setDspos(CBDiag.getSelectedPcod());
		rddin.setGrr(1);
		System.out.println("риск");		
}
		    else rddin.unsetDspos();
		rddin.setArt1((int) SPdad.getModel().getValue());
		rddin.setArt2((int) SPsad.getModel().getValue());
		rddin.setArt3((int) SLdad.getModel().getValue());
		rddin.setArt4((int) SLsad.getModel().getValue());
		rddin.setChcc((int) SChcc.getModel().getValue());
		rddin.setHdm((int) SVdm.getModel().getValue());
		rddin.setOj((int) SOkrj.getModel().getValue());
		rddin.setSpl((int) STolP.getModel().getValue());
		rddin.setSrok((int) SSrok.getModel().getValue());
		rddin.setVes((double) SVes.getModel().getValue());
		ves = (double) SVes.getModel().getValue();
		rost = FormPostBer.rdSlStruct.getRost();	
		if (rost<=154) br = br+1;
		if (FormRdInf.RdInfStruct.getObr() == 3)  br = br+1; 
		if (FormRdInf.RdInfStruct.getObr() == 5)  br = br+1; 
		if (FormRdInf.RdInfStruct.getSem() == 3)  br = br+1; 
		if (FormRdInf.RdInfStruct.getOsoco() >=1) br = br+1;
		if (rost != 0) 
		{ves = ves/rost/rost*10000;
		if (ves<17) br = br+1;
		if (ves>=32) br = br+1;}
		rddin.setBall(br); 
		System.out.println(rost);
			MainForm.tcl.UpdateRdDin(rddin);
		} catch (KmiacServerException | TException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println(rddin);		
	}
//		rddin.setDspos((string) CBDspos.getSelectedKod_Mkb.)//диагноз заменить на kod_mkb (вместо PCOD)
			}
		});
		
		JButton Nbutton = new JButton("");
		Nbutton.setIcon(new ImageIcon(FormRdDin.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
		Nbutton.setToolTipText("Новое посещение");
		Nbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				Vvod.btnPosAdd.doClick();

				RdDinStruct rddin = new RdDinStruct();
	//			setDefaultValues();
				rddin.setNpasp(Vvod.pvizitAmb.npasp);
				rddin.setId_pos(Vvod.pvizitAmb.id);
				rddin.setId_pvizit(Vvod.pvizitAmb.id_obr);
				rddin.setGrr(0);
				rddin.setBall(0);
				rddin.setArt1((int) SPdad.getModel().getValue());
				rddin.setArt2((int) SPsad.getModel().getValue());
				rddin.setArt3((int) SLdad.getModel().getValue());
				rddin.setArt4((int) SLsad.getModel().getValue());
				rddin.setChcc((int) SChcc.getModel().getValue());
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
				if (CBDiag.getSelectedPcod() != null)
					rddin.setDspos(CBDiag.getSelectedPcod());
					else rddin.unsetDspos();
			System.out.println(rddin);		
				MainForm.tcl.AddRdDin(rddin);
				} catch (KmiacServerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				}

			private void showMessage(RdDinStruct rddin) {
				// TODO Auto-generated method stub
				
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
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(26)
							.addComponent(Nbutton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(SButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnNewButton))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel_1.createSequentialGroup()
										.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
											.addGroup(gl_panel_1.createSequentialGroup()
												.addComponent(LLdad)
												.addGap(18)
												.addComponent(SLdad, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE))
											.addGroup(gl_panel_1.createSequentialGroup()
												.addComponent(LPdad)
												.addGap(18)
												.addComponent(SPdad, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)))
										.addGap(18)
										.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
											.addGroup(gl_panel_1.createSequentialGroup()
												.addComponent(LLsad)
												.addGap(18)
												.addComponent(SLsad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											.addGroup(gl_panel_1.createSequentialGroup()
												.addComponent(LPsad)
												.addGap(18)
												.addComponent(SPsad))))
									.addComponent(LVes)
									.addComponent(LSrok)
									.addGroup(gl_panel_1.createSequentialGroup()
										.addComponent(LtolPlac)
										.addGap(18)
										.addComponent(STolP, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_panel_1.createSequentialGroup()
										.addComponent(LOteki)
										.addPreferredGap(ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
										.addComponent(CBOteki, GroupLayout.PREFERRED_SIZE, 318, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_panel_1.createSequentialGroup()
										.addComponent(fam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(im, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(ot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_panel_1.createSequentialGroup()
										.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
											.addComponent(LDataPos)
											.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
												.addGroup(gl_panel_1.createSequentialGroup()
													.addComponent(LVdm)
													.addGap(100)
													.addComponent(SVdm, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_panel_1.createSequentialGroup()
													.addComponent(LOkrJ)
													.addGap(18)
													.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
														.addComponent(SVes, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
														.addComponent(SSrok, GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
														.addComponent(SDataPos, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
														.addComponent(SOkrj, GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)))))
										.addGap(18)
										.addComponent(LDataSl)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(SDataSl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(LDiag)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(CBDiag, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap())
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
						.addComponent(Nbutton)
						.addComponent(btnNewButton)
						.addComponent(SButton))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(24)
							.addComponent(LDataPos))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(SDataPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(LDataSl)
								.addComponent(SDataSl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
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
						.addComponent(LLsad)
						.addComponent(SLsad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
					.addGap(52))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		tablePos = new CustomTable<>(true, true, RdDinStruct.class, 3, "Срок", 21, "Вес", 6, "Окружность живота", 7 ,"ВДМ", 8 , "Диагноз");
//		tablePos.setDateField(0);
		scrollPane.setViewportView(tablePos);
		tablePos.setFillsViewportHeight(true);
		tablePos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()) {
					rddin = tablePos.getSelectedItem();
					
					SDataPos.setDate(Vvod.pvizitAmb.getDatap());
					SSrok.setValue(rddin.getSrok());
					SVes.setValue(rddin.getVes());
					SOkrj.setValue(rddin.getOj());
					SVdm.setValue(rddin.getHdm());
					SPdad.setValue(rddin.getArt1());
					SPsad.setValue(rddin.getArt2());
					SLdad.setValue(rddin.getArt3());
					SLsad.setValue(rddin.getArt4());
					SChcc.setValue(rddin.getChcc());
					STolP.setValue(rddin.getSpl());
					
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
					if (rddin.isSetDspos())
						CBDiag.setSelectedPcod(rddin.getDspos());
				}
			}
		});
		scrollPane.setViewportView(tablePos);
	}
	protected void setDefaultValues() {
		// TODO Auto-generated method stub
//	rddin.setId_pvizit(Vvod.zapVr.getId_pvizit());
//	rddin.setNpasp(Vvod.zapVr.getNpasp());
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
	System.out.println("присвоение");		
	System.out.println(rddin);		
	}
	public void onConnect() throws PatientNotFoundException {
		fam.setText(Vvod.zapVr.fam);
		im.setText(Vvod.zapVr.im);
		ot.setText(Vvod.zapVr.oth);
	}

}
