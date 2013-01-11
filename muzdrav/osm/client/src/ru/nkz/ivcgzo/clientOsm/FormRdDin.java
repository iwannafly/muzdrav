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
import ru.nkz.ivcgzo.thriftOsm.RdInfStruct;
import ru.nkz.ivcgzo.thriftOsm.RdSlStruct;
import javax.swing.JCheckBox;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;

public class FormRdDin extends JFrame {
	private static final long serialVersionUID = 553969304358351170L;
	private JPanel contentPane;
	private RdDinStruct rddin;
	public static PvizitAmb pvizitAmb;
	public static Pvizit pvizit;
	public static RdInfStruct RdInfStruct;
	public static RdSlStruct rdSlStruct;
	private CustomTable<RdDinStruct, RdDinStruct._Fields> tablePos;
//    private String oslname;
    private String oslcode;
    private int iw1;
    private int iw2;
    private int iw3;
    private int iw4;
//    private int iw5;
    private int otname;
    private int polplname;
    private int predname;
    private int cerdname;
    private int cerdname1;
    CustomDateEditor SDataPos;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBPolPl;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBPredPl;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBCerd;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBSerd1;
	private int mes;
	private int br;
	private int rost;
	private double ves;
	private int chcc;
	private int hdm;
	private int oj;
	private int spl;
	private int srok;
	private JCheckBox ChBot1;
	private JCheckBox ChBot2;
	private JCheckBox ChBot3;
	private JCheckBox ChBot4;
	private int oslrod;
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
// 			    fam.setText(Vvod.zapVr.getFam());
//				im.setText(Vvod.zapVr.getIm());
//				ot.setText(Vvod.zapVr.getOth());
//				RdDinStruct rddin = new RdDinStruct();
//				setDefaultValues();
//				System.out.println(rddin);
//				try {
//					MainForm.tcl.AddRdDin(rddin);
//				} catch (KmiacServerException | TException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
				try {
//					SDataPos.setDate(Vvod.pvizitAmb.getDatap());
					tablePos.setData(MainForm.tcl.getRdDinInfo(Vvod.pvizitAmb.id_obr, Vvod.pvizitAmb.npasp));
				} catch (KmiacServerException e) {
					e.printStackTrace();
				} catch (TException e) {
					MainForm.conMan.reconnect(e);
				}
				
			}
		});
		setTitle("Динамика диспансерного наблюдения за беременной");
		setTitle(String.format(" %s - %d, %s %s %s, %6$td.%6$tm.%6$tY ", getTitle(), Vvod.zapVr.getNpasp(), Vvod.zapVr.getFam(), Vvod.zapVr.getIm(), Vvod.zapVr.getOth(),  Vvod.zapVr.getDatar()));
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
			}
		});
		JLabel LChcc = new JLabel("ЧСС плода");
		LChcc.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LPolPl = new JLabel("Положение плода");
		LPolPl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LPredPl = new JLabel("Предлежание плода");
		LPredPl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LSerd = new JLabel("Сердцебиение плода");
		LSerd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		SChcc = new JSpinner();
		SChcc.setFont(new Font("Tahoma", Font.BOLD, 12));
		SChcc.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
 		
		CBPolPl = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db1);
		CBPolPl.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		CBPredPl = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db2);
		CBPredPl.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		CBCerd = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db3);
		CBCerd.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		CBSerd1 = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db4);
		CBSerd1.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		SDataPos = new CustomDateEditor();
		SDataPos.setFont(new Font("Tahoma", Font.BOLD, 12));
		
        SSrok = new JSpinner();
        SSrok.setFont(new Font("Tahoma", Font.BOLD, 12));
		SSrok.setModel(new SpinnerNumberModel(0, 0, 42, 1));
		
		SVes = new JSpinner();
		SVes.setFont(new Font("Tahoma", Font.BOLD, 12));
		SVes.setModel(new SpinnerNumberModel(new Double(0), new Double(0), null, new Double(1)));
		
		SOkrj = new JSpinner();
		SOkrj.setFont(new Font("Tahoma", Font.BOLD, 12));
		SOkrj.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		
		SVdm = new JSpinner();
		SVdm.setFont(new Font("Tahoma", Font.BOLD, 12));
		SVdm.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		
		SPsad = new JSpinner();
		SPsad.setFont(new Font("Tahoma", Font.BOLD, 12));
		SPsad.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		
		SLdad = new JSpinner();
		SLdad.setFont(new Font("Tahoma", Font.BOLD, 12));
		SLdad.setModel(new SpinnerNumberModel(0, 0, 220, 1));
		
		SLsad = new JSpinner();
		SLsad.setFont(new Font("Tahoma", Font.BOLD, 12));
		SLsad.setModel(new SpinnerNumberModel(0, 0, 120, 1));
		
		STolP = new JSpinner();
		STolP.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		setBounds(100, 100, 810, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		SPdad = new JSpinner();
		SPdad.setFont(new Font("Tahoma", Font.BOLD, 12));
		SPdad.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JLabel LOteki = new JLabel("Отеки");
		LOteki.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		
		 ChBot1 = new JCheckBox("Нижние конечности");
		 ChBot1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		 
		  ChBot2 = new JCheckBox("Верхние конечности");
		  ChBot2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		  
		   ChBot3 = new JCheckBox("Верхняя брюшная стенка");
		   ChBot3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		   
		    ChBot4 = new JCheckBox("Генерализованные");
		    ChBot4.setFont(new Font("Tahoma", Font.PLAIN, 12));
		    GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		    gl_panel_3.setHorizontalGroup(
		    	gl_panel_3.createParallelGroup(Alignment.LEADING)
		    		.addGroup(gl_panel_3.createSequentialGroup()
		    			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
		    				.addGroup(gl_panel_3.createSequentialGroup()
		    					.addGap(3)
		    					.addComponent(ChBot2))
		    				.addGroup(gl_panel_3.createSequentialGroup()
		    					.addGap(4)
		    					.addComponent(ChBot1))
		    				.addGroup(gl_panel_3.createSequentialGroup()
		    					.addGap(2)
		    					.addComponent(ChBot3))
		    				.addGroup(gl_panel_3.createSequentialGroup()
		    					.addGap(1)
		    					.addComponent(ChBot4))
		    				.addGroup(gl_panel_3.createSequentialGroup()
		    					.addGap(61)
		    					.addComponent(LOteki)))
		    			.addContainerGap(5, Short.MAX_VALUE))
		    );
		    gl_panel_3.setVerticalGroup(
		    	gl_panel_3.createParallelGroup(Alignment.LEADING)
		    		.addGroup(Alignment.TRAILING, gl_panel_3.createSequentialGroup()
		    			.addGap(2)
		    			.addComponent(LOteki)
		    			.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
		    			.addComponent(ChBot1)
		    			.addPreferredGap(ComponentPlacement.RELATED)
		    			.addComponent(ChBot2)
		    			.addPreferredGap(ComponentPlacement.RELATED)
		    			.addComponent(ChBot3)
		    			.addPreferredGap(ComponentPlacement.RELATED)
		    			.addComponent(ChBot4)
		    			.addGap(3))
		    );
		    panel_3.setLayout(gl_panel_3);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 404, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(51)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE)
							.addGap(11))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
							.addGap(84))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 316, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)))
					.addGap(117))
		);
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(3)
							.addComponent(LSerd))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(3)
							.addComponent(LPredPl))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(4)
							.addComponent(LChcc))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(4)
							.addComponent(LPolPl)))
					.addGap(3)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(29)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(CBSerd1, 0, 0, Short.MAX_VALUE)
								.addComponent(SChcc, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
								.addComponent(CBPolPl, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
								.addComponent(CBPredPl, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(27)
							.addComponent(CBCerd, GroupLayout.PREFERRED_SIZE, 147, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addContainerGap()
							.addComponent(SChcc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(14)
							.addComponent(LChcc)))
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(12)
							.addComponent(LPolPl))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(CBPolPl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(10)
							.addComponent(LPredPl))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(5)
							.addComponent(CBPredPl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(3)
							.addComponent(CBCerd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(CBSerd1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(8)
							.addComponent(LSerd)))
					.addGap(18))
		);
		panel_2.setLayout(gl_panel_2);
		
		JLabel LDataPos = new JLabel("Дата посещения");
		LDataPos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LSrok = new JLabel("Срок беременности");
		LSrok.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LVes = new JLabel("Вес");
		LVes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LOkrJ = new JLabel("Окружность живота");
		LOkrJ.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LVdm = new JLabel("ВДМ");
		LVdm.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LPdad = new JLabel("Правая          ДАД");
		LPdad.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LPsad = new JLabel(" САД");
		LPsad.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LLdad = new JLabel(" Левая           ДАД");
		LLdad.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LLsad = new JLabel(" САД");
		LLsad.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LtolPlac = new JLabel("Толщина плаценты");
		LtolPlac.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		
		JButton SButton = new JButton("");
		SButton.setToolTipText("Сохранить");
		SButton.setIcon(new ImageIcon(FormRdDin.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1341981970_Accept.png")));
		SButton.addActionListener(new ActionListener() {
            private void calcOteci(){
    			 oslrod = 0;
            if (ChBot1.isSelected()){oslrod=oslrod+1;}
            if (ChBot2.isSelected()){oslrod=oslrod+2;}
            if (ChBot3.isSelected()){oslrod=oslrod+4;}
            if (ChBot4.isSelected()){oslrod=oslrod+8;}
            };
	public void actionPerformed(ActionEvent e) {
		try {
			SimpleDateFormat frm = new SimpleDateFormat("MM");
			int mes = Integer.parseInt(frm.format(Vvod.zapVr.getDatar()));
			if (mes == 1) br = br+1;
			if (mes == 2) br = br+1;
			if (mes == 12) br = br+1;
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
//		if (CBOteki.getSelectedPcod() != null)
//			rddin.setOteki(CBOteki.getSelectedPcod());
//			else rddin.unsetOteki();
		calcOteci();
		rddin.setOteki(oslrod);
//		if (CBDiag.getSelectedPcod() != null)
//		{	rddin.setDspos(CBDiag.getSelectedPcod());
//		rddin.setGrr(1);}
//		    else rddin.setDspos("");
//		rost = FormPostBer.rdSlStruct.getRost();	
//		System.out.println(rost);
//		if (rost<=154) br = br+1;
//		if (FormRdInf.RdInfStruct.getObr() == 3)  br = br+1; 
//		if (FormRdInf.RdInfStruct.getObr() == 5)  br = br+1; 
//		if (FormRdInf.RdInfStruct.getSem() == 3)  br = br+1; 
//		if (FormRdInf.RdInfStruct.getOsoco() >=1) br = br+1;
//		if (rost != 0) 
//		{ves = ves/rost/rost*10000;
//		if (ves<17) br = br+1;
//		if (ves>=32) br = br+1;}
		rddin.setBall(br); 
			MainForm.tcl.UpdateRdDin(rddin);
		} catch (TException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
	}
		try {
			tablePos.setData(MainForm.tcl.getRdDinInfo(Vvod.pvizitAmb.id_obr, Vvod.pvizitAmb.npasp));
		} catch (TException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
			}
		});
		
		JButton Nbutton = new JButton("");
		Nbutton.setIcon(new ImageIcon(FormRdDin.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
		Nbutton.setToolTipText("Новое посещение");
		Nbutton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
				RdDinStruct rddin = new RdDinStruct();
				rddin.setDatap(Vvod.zapVr.datap);
				rddin.setNpasp(Vvod.pvizitAmb.npasp);
				rddin.setId_pos(Vvod.pvizitAmb.id);
				rddin.setId_pvizit(Vvod.pvizitAmb.id_obr);
				rddin.setGrr(0);
				rddin.setBall(0);
				rddin.setOteki(oslrod);
				rddin.setArt1((int) SPdad.getModel().getValue());
				if ( (int) SPdad.getModel().getValue() == 0) rddin.setArt1(120);
				rddin.setArt2((int) SPsad.getModel().getValue());
				if ( (int) SLdad.getModel().getValue() == 0) rddin.setArt2(80);
				rddin.setArt3((int) SLdad.getModel().getValue());
				if ( (int) SLdad.getModel().getValue() == 0) rddin.setArt3(120);
				rddin.setArt4((int) SLsad.getModel().getValue());
				if ( (int) SLdad.getModel().getValue() == 0) rddin.setArt4(80);
				rddin.setChcc((int) SChcc.getModel().getValue());
				if ( (int) SChcc.getModel().getValue() == 0) rddin.setChcc(0);
				rddin.setHdm((int) SVdm.getModel().getValue());
				if ( (int) SVdm.getModel().getValue() == 0) rddin.setHdm(20);
				rddin.setOj((int) SOkrj.getModel().getValue());
				if ( (int) SOkrj.getModel().getValue() == 0) rddin.setOj(100);
  		        rddin.setSpl((int) STolP.getModel().getValue());
				if ( (int) STolP.getModel().getValue() == 0) rddin.setSpl(2);
			    rddin.setSrok((int) SSrok.getModel().getValue());
				if ( (int) SSrok.getModel().getValue() == 0) rddin.setSrok(4);
				rddin.setVes((double) SVes.getModel().getValue());
				if ( (double) SVes.getModel().getValue() == 0) rddin.setVes(60);
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
//				if (CBOteki.getSelectedPcod() != null)
//					rddin.setOteki(CBOteki.getSelectedPcod());
//					else rddin.unsetOteki();
//				if (CBDiag.getSelectedPcod() != null)
//					rddin.setDspos(CBDiag.getSelectedPcod());
//					else rddin.setDspos("");
			System.out.println(rddin);		
				MainForm.tcl.AddRdDin(rddin);
				} catch (KmiacServerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
try {
	tablePos.setData(MainForm.tcl.getRdDinInfo(Vvod.pvizitAmb.id_obr, Vvod.pvizitAmb.npasp));
	if (tablePos.getRowCount() > 0)
		tablePos.setRowSelectionInterval(tablePos.getRowCount() - 1, tablePos.getRowCount() - 1);
} catch (TException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
			}

			private void showMessage(RdDinStruct rddin) {
				// TODO Auto-generated method stub
				
			}
		});
//		ot.setText(Vvod.zapVr.oth);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(142)
							.addComponent(Nbutton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(SButton))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_panel_1.createSequentialGroup()
											.addComponent(LPdad)
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(SPdad, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
											.addGap(7))
										.addGroup(gl_panel_1.createSequentialGroup()
											.addComponent(LLdad)
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(SLdad, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
											.addGap(5)))
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_panel_1.createSequentialGroup()
											.addComponent(LLsad)
											.addGap(18)
											.addComponent(SLsad, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel_1.createSequentialGroup()
											.addComponent(LPsad)
											.addGap(18)
											.addComponent(SPsad, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))))
								.addComponent(LVes)
								.addComponent(LSrok)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
									.addGroup(gl_panel_1.createSequentialGroup()
										.addComponent(LVdm)
										.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(SVdm, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
										.addGap(9))
									.addGroup(gl_panel_1.createSequentialGroup()
										.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
											.addComponent(LOkrJ)
											.addComponent(LDataPos))
										.addGap(18)
										.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
											.addComponent(SDataPos, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
												.addComponent(SVes, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
												.addComponent(SSrok, GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
												.addComponent(SOkrj, GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)))))
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(LtolPlac)
									.addGap(29)
									.addComponent(STolP, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(Nbutton)
						.addComponent(SButton))
					.addGap(18)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(LDataPos)
						.addComponent(SDataPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(LVdm))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(5)
							.addComponent(SVdm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(LPdad)
							.addComponent(LPsad)
							.addComponent(SPsad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(SPdad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(3)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
							.addComponent(LLdad)
							.addComponent(LLsad)
							.addComponent(SLsad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(SLdad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(12)
							.addComponent(STolP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(15)
							.addComponent(LtolPlac)))
					.addGap(133))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		tablePos = new CustomTable<>(true, true, RdDinStruct.class, 22, "Дата", 3, "Срок", 21, "Вес", 6, "Объем живота", 7 ,"ВДМ",9,"ДАД",10,"САД");
		tablePos.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tablePos.setDateField(0);
		scrollPane.setViewportView(tablePos);
		tablePos.setFillsViewportHeight(true);
		tablePos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting() && tablePos.getSelectedItem() != null) {
					rddin = tablePos.getSelectedItem();
//					if (tablePos.getRowCount() > 0)
//					tablePos.setRowSelectionInterval(0, tablePos.getRowCount() -1);
//					tablePos.setRequestFocusEnabled(false);
//					tablePos.setRowMargin(tablePos.getRowCount() -1);
					SDataPos.setDate(rddin.getDatap());
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
//					if (rddin.isSetOteki())
//						CBOteki.setSelectedPcod(rddin.getOteki());
//					else
//						CBOteki.setSelectedItem(null);
                    oslrod = rddin.getOteki();
//        			method2();
            		if ((oslrod-8)>=0)
            		{ChBot4.setSelected(true);   iw1=oslrod-8;}
            		else {iw1=oslrod;ChBot4.setSelected(false);}
            		if ((iw1-4)>=0)
            		{ChBot3.setSelected(true);		iw1=iw1-4;}
            		else ChBot3.setSelected(false);
            		if ((iw1-2)>=0)
            		{ChBot2.setSelected(true);		iw1=iw1-2;}	
            		else  ChBot2.setSelected(false);   		
            		ChBot1.setSelected(iw1 ==1 );
//					if (rddin.isSetDspos())
//						CBDiag.setSelectedPcod(rddin.getDspos());
//					else
//						CBDiag.setSelectedItem(null);
				}
			}
		});
		scrollPane.setViewportView(tablePos);
	}
//	protected void method2() {
//		if ((oslrod-8)>=0)
//		{ChBot4.setSelected(true);   iw1=oslrod-8;}	
//		if ((iw1-4)>=0)
//		{ChBot3.setSelected(true);		iw1=iw1-4;}	
//		if ((iw1-2)>=0)
//		{ChBot2.setSelected(true);		iw1=iw1-2;}	
//		ChBot1.setSelected(iw1 ==1 );
//		
//	}
	protected void setDefaultValues() {
		// TODO Auto-generated method stub
//		rddin.setNpasp(Vvod.pvizitAmb.npasp);
//		rddin.setId_pos(Vvod.pvizitAmb.id);
//		rddin.setId_pvizit(Vvod.pvizitAmb.id_obr);
	ves = (double) SVes.getModel().getValue();
	if (ves == 0) ves = 60; 
	chcc = (int) SChcc.getModel().getValue();
	if (chcc == 0) chcc = 110; 
	hdm = (int) SVdm.getModel().getValue();
	if (hdm == 0) hdm = 20;
	oj = (int) SOkrj.getModel().getValue();
	if (oj == 0) oj = 90; 
	spl = (int) STolP.getModel().getValue();
	if (spl == 0) spl = 2;
	srok = (int) SSrok.getModel().getValue();
	if (srok == 0)srok = 8;
	iw1 = (int) SLdad.getModel().getValue();
	if (iw1 == 0) iw1 = 120;
	iw2 = (int) SLsad.getModel().getValue();
	if (iw2 == 0) iw2 = 80;
	iw3 = (int) SPdad.getModel().getValue();
	if (iw3 == 0) iw3 = 120;
	iw4 = (int) SPsad.getModel().getValue();
	if (iw4 == 0) iw4 = 80;
//	polplname = rddin.getPolpl();
//	predname = rddin.getPredpl();
//	cerdname = rddin.getSerd();
//	cerdname1 = rddin.getSerd1();
//	otname = rddin.getOteki();
	
//	rddin.setArt1(iw1);
//	rddin.setArt2(iw2);
//	rddin.setArt3(iw3);
//	rddin.setArt4(iw4);
//	rddin.setChcc(chcc);
//	rddin.setHdm(hdm);
//	rddin.setOj(oj);
//	rddin.setSpl(spl);
//	rddin.setSrok(srok);
//	rddin.setPolpl(polplname);
//	rddin.setPredpl(predname);
//	rddin.setSerd(cerdname);
//	rddin.setSerd1(cerdname1);
//	rddin.setOteki(otname);
//	rddin.setDatap(Vvod.zapVr.datap);
	}
	public void onConnect() throws PatientNotFoundException {
//		fam.setText(Vvod.zapVr.fam);
//		im.setText(Vvod.zapVr.im);
//		ot.setText(Vvod.zapVr.oth);
	}
}
