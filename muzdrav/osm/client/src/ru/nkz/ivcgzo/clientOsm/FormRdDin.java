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
import javax.swing.JTextField;
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
    private String oslcode;
    private int iw1;
    private int iw2;
    private int iw3;
    private int iw4;
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
	private JTextField SSrok ;
	private JTextField SVes;
	private JTextField SOkrj;
	private JTextField SVdm;
	private JTextField SPsad;
	private JTextField SLdad;
	private JTextField SLsad;
	private JTextField STolP;
	private JTextField SChcc;
	private JTextField SPdad;
	

	/**
	 * Create the frame.
	 */
	public FormRdDin() {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				try {
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
		
		SChcc = new JTextField();
		SChcc.setFont(new Font("Tahoma", Font.BOLD, 12));
 		
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
		
        SSrok = new JTextField();
        SSrok.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		SVes = new JTextField();
		SVes.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		SOkrj = new JTextField();
		SOkrj.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		SVdm = new JTextField();
		SVdm.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		SPsad = new JTextField();
		SPsad.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		SLdad = new JTextField();
		SLdad.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		SLsad = new JTextField();
		SLsad.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		STolP = new JTextField();
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
		
		SPdad = new JTextField();
		SPdad.setFont(new Font("Tahoma", Font.BOLD, 12));
		
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
				rddin.setArt1(Integer.valueOf(SPdad.getText()));
				rddin.setArt2(Integer.valueOf(SPsad.getText()));
				rddin.setArt3(Integer.valueOf(SLdad.getText()));
				rddin.setArt4(Integer.valueOf(SLsad.getText()));
				rddin.setChcc(Integer.valueOf(SChcc.getText()));
				rddin.setHdm(Integer.valueOf(SVdm.getText()));
				rddin.setOj(Integer.valueOf(SOkrj.getText()));
  		        rddin.setSpl(Integer.valueOf(STolP.getText()));
			    rddin.setSrok(Integer.valueOf(SSrok.getText()));
				rddin.setVes(Double.valueOf(SVes.getText()));
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
			rddin.setArt1(Integer.valueOf(SPdad.getText()));
			rddin.setArt2(Integer.valueOf(SPsad.getText()));
			rddin.setArt3(Integer.valueOf(SLdad.getText()));
			rddin.setArt4(Integer.valueOf(SLsad.getText()));
			rddin.setChcc(Integer.valueOf(SChcc.getText()));
			rddin.setHdm(Integer.valueOf(SVdm.getText()));
			rddin.setOj(Integer.valueOf(SOkrj.getText()));
			rddin.setSpl(Integer.valueOf(STolP.getText()));
			rddin.setSrok(Integer.valueOf(SSrok.getText()));
			rddin.setVes(Double.valueOf(SVes.getText()));
			ves = Double.valueOf(SVes.getText());
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
		calcOteci();
		rddin.setOteki(oslrod);
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
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 404, GroupLayout.PREFERRED_SIZE)
							.addGap(51)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 314, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE))
							.addGap(11))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addComponent(Nbutton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(SButton)
							.addGap(334))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel_1, 0, 0, Short.MAX_VALUE))
					.addGap(45)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(Nbutton)
						.addComponent(SButton))
					.addGap(99))
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
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
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
							.addComponent(STolP, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(118, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel_1.createSequentialGroup()
					.addContainerGap()
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
					.addGap(184))
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
					SDataPos.setDate(rddin.getDatap());
					SSrok.setText(String.valueOf(rddin.getSrok()));
					SVes.setText(String.valueOf(rddin.getVes()));
					SOkrj.setText(String.valueOf(rddin.getOj()));
					SVdm.setText(String.valueOf(rddin.getHdm()));
					SPdad.setText(String.valueOf(rddin.getArt1()));
					SPsad.setText(String.valueOf(rddin.getArt2()));
					SLdad.setText(String.valueOf(rddin.getArt3()));
					SLsad.setText(String.valueOf(rddin.getArt4()));
					SChcc.setText(String.valueOf(rddin.getChcc()));
					STolP.setText(String.valueOf(rddin.getSpl()));
					
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
                    oslrod = rddin.getOteki();
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
				}
			}
		});
		scrollPane.setViewportView(tablePos);
	}
	protected void setDefaultValues() {
		// TODO Auto-generated method stub
//	ves = Double.valueOf(SVes.getText());
//	if (ves == 0) ves = 60; 
	chcc = Integer.valueOf(SChcc.getText());
	if (chcc == 0) chcc = 110; 
	hdm = Integer.valueOf(SVdm.getText());
	if (hdm == 0) hdm = 20;
	oj = Integer.valueOf(SOkrj.getText());
	if (oj == 0) oj = 90; 
	spl = Integer.valueOf(STolP.getText());
	if (spl == 0) spl = 2;
	srok = Integer.valueOf(SSrok.getText());
	if (srok == 0)srok = 8;
//	iw1 = Integer.valueOf(SLdad.getText());
//	if (iw1 == 0) iw1 = 120;
//	iw2 = Integer.valueOf(SLsad.getText());
//	if (iw2 == 0) iw2 = 80;
//	iw3 = Integer.valueOf(SPdad.getText());
//	if (iw3 == 0) iw3 = 120;
//	iw4 = Integer.valueOf(SPsad.getText());
//	if (iw4 == 0) iw4 = 80;
	}
	public void onConnect() throws PatientNotFoundException {
//		fam.setText(Vvod.zapVr.fam);
//		im.setText(Vvod.zapVr.im);
//		ot.setText(Vvod.zapVr.oth);
	}
}
