package ru.nkz.ivcgzo.clientOsm;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import java.awt.Font;

import javax.swing.JEditorPane;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Calendar;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;

import javax.swing.JOptionPane;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.KartaBer;
//import ru.nkz.ivcgzo.thriftOsm.PsignNotFoundException;
//import ru.nkz.ivcgzo.;
import ru.nkz.ivcgzo.thriftOsm.PatientCommonInfo;
import ru.nkz.ivcgzo.thriftOsm.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Pvizit;
import ru.nkz.ivcgzo.thriftOsm.PvizitAmb;
import ru.nkz.ivcgzo.thriftOsm.PvizitNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.RdSlStruct;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;

import org.apache.thrift.TException;

import com.oracle.net.Sdp;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;

import javax.swing.ImageIcon;

public class FormPostBer extends JFrame {

	private JPanel contentPane;
	private JTextField TNKart;
    private RdSlStruct rdsl;
	private PatientCommonInfo patient;
    private int oslrod;
    private int or1;
    private int or2;
    private int or3;
    private int or4;
    private int or5;
    private int or6;
    private int or7;
    private int or8;
    private int iw1;
    private int iw2;
    private int iw3;
    private String oslname;
    private String oslcode;
    private FormRdInf inform;
    private FormRdDin dinform;
    private JSpinner SVes;
    private JSpinner SDsp;
    private JSpinner SDcr;
    private JSpinner SDtroch;
    private JSpinner SCext;
    private JSpinner SindSol;
    private CustomDateEditor SDataPos;
    private JSpinner SParRod;
    private JSpinner SKolBer;
    private CustomDateEditor SDataOsl;
    private CustomDateEditor SDataM;
    private CustomDateEditor TDataSn;
   private CustomDateEditor SDataRod;
    private CustomDateEditor SDataSert;
    private CustomDateEditor TDataab;
    private JSpinner SYavka;
    private JSpinner SKolAb;
    private JSpinner SVozMen;
    private JSpinner SMenC;
    private JSpinner SKolDet;
    private JSpinner SPolJ;
    private JSpinner SSrokA;
    private JSpinner SCDiag;
    private JSpinner SCvera;
    private JCheckBox CBKrov; 
    private JCheckBox CBEkl; 
    private JCheckBox CBGnoin; 
    private JCheckBox CBTromb; 
    private JCheckBox CDKesar; 
    private JCheckBox CBAkush; 
    private JCheckBox CBIiiiv; 
    private JCheckBox CBRazrProm; 
	private ThriftStringClassifierCombobox<StringClassifier> CBOslAb;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBRod;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBPrishSn;
	/**
	 * Launch the application.
	 */
	private JCheckBox CBKontr;
	private JTextField fam;
	private JTextField im;
	private JTextField ot;
	private JTextField TSSert;
	private JTextField TNSert;
	private JEditorPane TPrRod;
	private JTextField TDatasn;

	/**
	 * Create the frame.
	 */
	public FormPostBer() {
		setTitle("Постановка на учет по беременности");
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
			}
		});
		
addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
try {
	rdsl = MainForm.tcl.getRdSlInfo(Vvod.zapVr.getId_pvizit(), Vvod.zapVr.getNpasp());
	setPostBerData();
	fam.setText(Vvod.zapVr.getFam());
	im.setText(Vvod.zapVr.getIm());
	ot.setText(Vvod.zapVr.getOth());
} catch (KmiacServerException | TException e) {
	JOptionPane.showMessageDialog(FormPostBer.this, e.getLocalizedMessage(), "Ошибка выбора", JOptionPane.ERROR_MESSAGE);
	// TODO Auto-generated catch block
	e.printStackTrace();
}			}
		});
		setTitle("Постановка на учет по беременности");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1000, 700);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setIcon(new ImageIcon(FormPostBer.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
		btnNewButton.setToolTipText("Постановка на учет");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					rdsl = new RdSlStruct();
					setDefaultValues();
					rdsl.setId(MainForm.tcl.AddRdSl(rdsl));
//					rdsl.setId_pvizit(Vvod.zapVr.getId_pvizit());
//					rdsl.setNpasp(Vvod.zapVr.getNpasp());
					rdsl = MainForm.tcl.getRdSlInfo(Vvod.zapVr.getId_pvizit(), Vvod.zapVr.getNpasp());
					setPostBerData();
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(FormPostBer.this, e1.getLocalizedMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
				} catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				}
			}
		});
		
		JButton ButSave = new JButton("");
		ButSave.setIcon(new ImageIcon(FormPostBer.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1341981970_Accept.png")));
		ButSave.setToolTipText("Сохранить");
		ButSave.addActionListener(new ActionListener() {
            private void calcOslrod(){
    			oslrod=0;
        if (CBKrov.isSelected()){oslrod=oslrod+1;}
            if (CBEkl.isSelected()){oslrod=oslrod+2;}
            if (CBGnoin.isSelected()){oslrod=oslrod+4;}
            if (CBTromb.isSelected()){oslrod=oslrod+8;}
            if (CDKesar.isSelected()){oslrod=oslrod+16;}
            if (CBAkush.isSelected()){oslrod=oslrod+32;}
            if (CBIiiiv.isSelected()){oslrod=oslrod+64;}
            if (CBRazrProm.isSelected()){oslrod=oslrod+128;}
//			System.out.println(oslrod);		
            };
			public void actionPerformed(ActionEvent arg0) {
				try {

					rdsl.setId_pvizit(Vvod.zapVr.id_pvizit);
			rdsl.setAbort((int) SKolAb.getValue());
			rdsl.setCext((int) SCext.getModel().getValue());
 			if (SDataM.getDate() != null)
			rdsl.setDataM( SDataM.getDate().getTime());
 			if (SDataOsl.getDate() != null)
			rdsl.setDataosl( SDataOsl.getDate().getTime());
 			if (TDataSn.getDate() != null)
			rdsl.setDatasn( TDataSn.getDate().getTime());
 			if (SDataRod.getDate() != null)
			rdsl.setDatasn( SDataRod.getDate().getTime());
 			if (SDataSert.getDate() != null)
			rdsl.setDatasert( SDataSert.getDate().getTime());
			rdsl.setSsert(getTextOrNull(TSSert.getText()));
			rdsl.setNsert(getTextOrNull(TNSert.getText()));
			rdsl.setPrrod(getTextOrNull(TPrRod.getText()));
//			rdsl.setPrrod(TPrRod.getText());
 			if (TDataab.getDate() != null)
			rdsl.setDataab( TDataab.getDate().getTime());
			rdsl.setSsert(getTextOrNull(TSSert.getText()));
			rdsl.setNsert(getTextOrNull(TNSert.getText()));
 			if (SDataPos.getDate() != null)
			rdsl.setDatay(SDataPos.getDate().getTime());
			rdsl.setKont(CBKontr.isSelected());
			rdsl.setDeti((int) SKolDet.getModel().getValue());
			rdsl.setDsp((int) (SDsp.getModel()).getValue());
			rdsl.setDsr((int) SDcr.getModel().getValue());
			rdsl.setDTroch((int) SDtroch.getModel().getValue());
			rdsl.setIndsol((int) SindSol.getModel().getValue());
			rdsl.setShet((int) SKolBer.getModel().getValue());
			rdsl.setKolrod((int) SParRod.getModel().getValue());
			rdsl.setPolj((int) SPolJ.getModel().getValue());
			rdsl.setPrmen((int) SMenC.getModel().getValue());
//			rdsl.setRost((int) SRost.getModel().getValue());
			rdsl.setVesd((Double) SVes.getModel().getValue());
			rdsl.setYavka1((int) SYavka.getModel().getValue());
           	rdsl.setCdiagt((int) SCDiag.getModel().getValue());
           	rdsl.setCvera((int) SCvera.getModel().getValue());
           	rdsl.setDataz(System.currentTimeMillis());
			calcOslrod();
			rdsl.setOslrod(oslrod);
			if (CBOslAb.getSelectedPcod() != null)
				rdsl.setOslab(CBOslAb.getSelectedPcod());
				else rdsl.unsetOslab();
			if (CBRod.getSelectedPcod() != null)
				rdsl.setPlrod(CBRod.getSelectedPcod());
				else rdsl.unsetPlrod();
			if (CBPrishSn.getSelectedPcod() != null)
				rdsl.setIshod(CBPrishSn.getSelectedPcod());
				else rdsl.unsetIshod();
	System.out.println(rdsl);		
		//		JOptionPane.showMessageDialog(FormPostBer.this, "Ошибка обновления");
				MainForm.tcl.UpdateRdSl(rdsl);
			} catch (KmiacServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	} 		
		});
		JButton button = new JButton("Дополнительная информация");
		button.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					inform = new FormRdInf();
					inform.setVisible(true);
					inform.onConnect();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					JOptionPane.showMessageDialog(FormPostBer.this, e.getLocalizedMessage(), "Ошибка открытия доп. инф.", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		JButton btnNewButton_1 = new JButton("Динамическое наблюдение");
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dinform = new FormRdDin();
						dinform.setVisible(true);
			}
		});
		
		JButton ButDelete = new JButton("");
		ButDelete.setIcon(new ImageIcon(FormPostBer.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789259_Delete.png")));
		ButDelete.setToolTipText("Удалить");
		ButDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					MainForm.tcl.DeleteRdSl(rdsl.getId(), rdsl.getNpasp());
				} catch (KmiacServerException e) {
					e.printStackTrace();
				} catch (TException e) {
					MainForm.conMan.reconnect(e);
				}
			}
		});
		
		
		fam = new JTextField();
		fam.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		fam.setColumns(10);
//		fam.setText(Vvod.zapVr.fam);
		
		im = new JTextField();
		im.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
//		im.setText("");
		im.setColumns(10);
//		im.setText(Vvod.zapVr.im);
		
		ot = new JTextField();
		ot.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
//		ot.setText("");
		ot.setColumns(10);
		
		JButton BPeshOK = new JButton("Печать обменной карты");
		BPeshOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					KartaBer kartaber = new KartaBer();
					kartaber.setId_pos(Vvod.pvizitAmb.getId());
					kartaber.setId_pvizit(Vvod.pvizit.getId());
					kartaber.setNpasp(Vvod.zapVr.getNpasp());
					kartaber.setId_rd_sl(0);
					String servPath = MainForm.tcl.printKartaBer(kartaber);
					String cliPath;
					cliPath = File.createTempFile("kart1", ".htm").getAbsolutePath();
					MainForm.conMan.transferFileFromServer(servPath, cliPath);

			}
			catch (TException e1) {
				e1.printStackTrace();
				MainForm.conMan.reconnect(e1);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			}
		});
		BPeshOK.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
//		ot.setText(Vvod.zapVr.oth);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(fam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(im, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(ot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(button))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnNewButton)
							.addGap(18)
							.addComponent(ButSave)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(ButDelete)
							.addGap(18)
							.addComponent(btnNewButton_1)
							.addGap(27)
							.addComponent(BPeshOK)))
					.addContainerGap(304, Short.MAX_VALUE))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 965, Short.MAX_VALUE)
					.addGap(7))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(fam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(im, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(ot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(button))
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnNewButton)
								.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
									.addComponent(ButSave)
									.addComponent(ButDelete))))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnNewButton_1)
								.addComponent(BPeshOK))))
					.addGap(18)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 581, GroupLayout.PREFERRED_SIZE))
		);
		
		JLabel LNslu = new JLabel("Номер обменной карты");
		
		JLabel LDatap = new JLabel("Дата первого посещения");
		
		JLabel LKolp = new JLabel("Количество беременностей");
		
		JLabel LKolAb = new JLabel("Количество абортов");
		
		JLabel LOslAb = new JLabel("Осложнение после аборта");
		
		JLabel LDataOsl = new JLabel("Дата первого шевеления плода");
		
		JLabel LDataMes = new JLabel("Дата последних месячных");
		
		JLabel LYavka = new JLabel("Первая явка (недель)");
		
		JLabel LPlanRod = new JLabel("Планируемые роды");
		
		JLabel LDataPlRod = new JLabel("Планируемая дата родов");
		
		JLabel LPrish = new JLabel("Причина снятия с учета");
		
		JLabel LKolRod = new JLabel("Паритет родов");
		
		JLabel LVozMen = new JLabel("Возраст Менархе");
		
		JLabel LProdMen = new JLabel("Продолжительность менстр. цикла");
		
		JLabel LKolDet = new JLabel("Количество живых детей");
		
		JLabel LpolJ = new JLabel("Половая жизнь со скольки лет");
		
		 CBKontr = new JCheckBox("Контрацепция");
//		CBKontr.setSelected(rdsl.kont == true);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(212, 208, 200));
		
		JPanel panel_2 = new JPanel();
		
		JLabel LVes = new JLabel("Вес до беременности");
		
		JLabel LTaz = new JLabel("Таз:");
		LTaz.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblDsp = new JLabel("DSP");
		
		JLabel lblDcr = new JLabel("DCR");
		
		JLabel lblDtroch = new JLabel("DTROCH");
		
		JLabel lblCext = new JLabel("C.ext");
		
		JLabel LIndSol = new JLabel("Индекс Соловьева");

		SVes = new JSpinner();
		SVes.setModel(new SpinnerNumberModel(0.0, 0.0, 250.0, 1.0));
//		rdsl.setVesd((int) SVes.getModel().getValue());
		
		SDsp = new JSpinner();
		SDsp.setModel(new SpinnerNumberModel(0, 0, 27, 1));
//		rdsl.setDsp((int) SDsp.getModel().getValue());
		
		SDcr = new JSpinner();
		SDcr.setModel(new SpinnerNumberModel(0, 0, 30, 1));
//		rdsl.setDsr((int) SDcr.getModel().getValue());
		
		SDtroch = new JSpinner();
		SDtroch.setModel(new SpinnerNumberModel(0,0,33,1));
//		rdsl.setDTroch((int) SDtroch.getModel().getValue());
		
		SCext = new JSpinner();
		SCext.setModel(new SpinnerNumberModel(0,0, 35, 1));
//		rdsl.setCext((int) SCext.getModel().getValue());
		
		SindSol = new JSpinner();
		SindSol.setModel(new SpinnerNumberModel(0, 0, 20,1));
//		rdsl.setIndsol((int) SindSol.getModel().getValue());
		
		CBKrov = new JCheckBox("Кровотечение");
		CBKrov.setSelected(or1 == 1);
		
		JLabel LIshPoslB = new JLabel("Осложнения предыдущих родов");
		LIshPoslB.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		CBEkl = new JCheckBox("Проэкламсия - экламсия");
		CBEkl.setSelected(or2 ==1);
		
		CBGnoin = new JCheckBox("Гнойно-септические осложнения");
		CBGnoin.setSelected(or3 ==1);
		
		CBTromb = new JCheckBox("Тромбоэмболитические осложнения");
		CBTromb.setSelected(or4 ==1);
		
		CDKesar = new JCheckBox("Кесарево сечение");
		CDKesar.setSelected(or5 ==1);
		
		CBAkush = new JCheckBox("Акушерские щипцы");
		CBAkush.setSelected(or6 ==1);
		
		CBIiiiv = new JCheckBox("Разрав промежности III-IV степени");
		CBIiiiv.setSelected(or7 ==1);
		
		CBRazrProm = new JCheckBox("Разрав шейки матки III степени");
		CBRazrProm.setSelected(or8 ==1);
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(CBRazrProm)
						.addComponent(CBIiiiv)
						.addComponent(CBAkush)
						.addComponent(CDKesar)
						.addComponent(CBTromb)
						.addComponent(CBGnoin)
						.addComponent(CBEkl)
						.addComponent(CBKrov)
						.addComponent(LIshPoslB))
					.addContainerGap(7, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(LIshPoslB)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(CBKrov)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(CBEkl)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(CBGnoin)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(CBTromb)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(CDKesar)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(CBAkush)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(CBIiiiv)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(CBRazrProm)
					.addContainerGap(23, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		TNKart = new JTextField();
		TNKart.setColumns(10);
		SDataPos = new CustomDateEditor();
		
		SParRod = new JSpinner();
		SParRod.setModel(new SpinnerNumberModel(0, 0, 20, 1));
//		rdsl.setKolrod((int) SParRod.getModel().getValue());
		
		SKolBer = new JSpinner();
		SKolBer.setModel(new SpinnerNumberModel(0, 0, 50, 1));
//		rdsl.setShet((int) SKolBer.getModel().getValue());
		
		SDataOsl = new CustomDateEditor();
		
		SDataSert = new CustomDateEditor();

		SYavka = new JSpinner();
		SYavka.setModel(new SpinnerNumberModel(0,0, 40,1));
//		rdsl.setYavka1((int) SYavka.getModel().getValue());
		
		SDataM = new CustomDateEditor();
		
		SDataRod = new CustomDateEditor();
		SDataRod.setColumns(10);
//		Calendar cal1 = Calendar.getInstance();
//		cal1.setTimeInMillis(rdsl.getDatay());
//		cal1.add(Calendar.DAY_OF_MONTH, (280-(rdsl.getYavka1()*7)));
//		SDataRod.setDate(cal1);
		
		SKolAb = new JSpinner();
		SKolAb.setModel(new SpinnerNumberModel(0, 0, 50, 1));
//		rdsl.setAbort((int) SKolAb.getModel().getValue());
		
		SVozMen = new JSpinner();
		SVozMen.setModel(new SpinnerNumberModel(8 , 8, 30, 1));
//		rdsl.setVozmen((int) SVozMen.getModel().getValue());
		
		SMenC = new JSpinner();
		SMenC.setModel(new SpinnerNumberModel(20, 20,  60, 1));
//		rdsl.setPrmen((int) SMenC.getModel().getValue());
		
		SKolDet = new JSpinner();
		SKolDet.setModel(new SpinnerNumberModel(0, 0, 20, 1));
//		rdsl.setDeti((int) SKolDet.getModel().getValue());
		
		SPolJ = new JSpinner();
		SPolJ.setModel(new SpinnerNumberModel(9, 9, 40, 1));
//		rdsl.setPolj((int) SPolJ.getModel().getValue());
		
//		final JSpinner SDataSn = new JSpinner();
//		SDataSn.setBackground(new Color(212, 208, 200));
//		SDataSn.setModel(new SpinnerDateModel(new Date(rdsl.Datasn), System.currentTimeMillis(),(System.currentTimeMillis()+280), Calendar.DAY_OF_YEAR));
//		rdsl.setDatasn((long) SDataSn.getModel().getValue());
		TDataSn = new CustomDateEditor();
		
		CBPrishSn = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db7);
		
		CBRod = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db8);
		
		CBOslAb = new ThriftStringClassifierCombobox<>(StringClassifiers.n_db9);
		
		JLabel lblNewLabel = new JLabel("Дата выдачи Родового сертификата");
		
		JLabel lblNewLabel_1 = new JLabel("Серия");
		
		TSSert = new JTextField();
		TSSert.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Номер");
		
		TNSert = new JTextField();
		TNSert.setColumns(10);
		
		JLabel LDataAb = new JLabel("Дата аборта");
		
		TDataab = new CustomDateEditor();
		
		JLabel lblNewLabel_4 = new JLabel("Срок");
		
		SSrokA = new JSpinner();
		SSrokA.setModel(new SpinnerNumberModel(0,0, 40,1 ));
		
		JLabel LPrBer = new JLabel("Описание предыдущих родов");
		LPrBer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		TPrRod = new JEditorPane();
		TPrRod.setBorder(UIManager.getBorder("TextField.border"));
		
		JLabel LDataSn = new JLabel("Дата снятия с учета");
		
		TDatasn = new JTextField();
		TDatasn.setColumns(10);
//		rdsl.setSrokab((int) SSrokA.getModel().getValue());

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(LNslu)
										.addComponent(LDatap)
										.addComponent(LKolp))
									.addGap(40)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(TNKart, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
											.addComponent(SKolBer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(SDataPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED, 70, Short.MAX_VALUE)
											.addComponent(LDataMes))
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(8)
											.addComponent(LKolRod)
											.addGap(18)
											.addComponent(SParRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
								.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 450, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(LDataOsl)
										.addComponent(LPlanRod)
										.addComponent(LDataPlRod))
									.addGap(21)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(SDataRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(SDataOsl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(CBRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(92))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblNewLabel_1)
									.addGap(13)
									.addComponent(TSSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(12)
									.addComponent(lblNewLabel_2)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(TNSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblNewLabel)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(SDataSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(LPrish)
										.addComponent(LDataSn))
									.addGap(32)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(TDatasn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(CBPrishSn, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
									.addGap(122)))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addPreferredGap(ComponentPlacement.RELATED, 2, Short.MAX_VALUE)
											.addComponent(SDataM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(LYavka)
											.addGap(18)
											.addComponent(SYavka, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(502))
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(LKolAb)
											.addGap(18)
											.addComponent(SKolAb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(581))))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(30)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(LPrBer)
										.addComponent(TPrRod, GroupLayout.PREFERRED_SIZE, 349, GroupLayout.PREFERRED_SIZE)
										.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE))
									.addContainerGap(126, Short.MAX_VALUE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(18)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(LOslAb)
										.addComponent(LDataAb))
									.addGap(18)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(TDataab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(lblNewLabel_4)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(SSrokA, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
										.addComponent(CBOslAb, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE))
									.addGap(329))))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(LVozMen)
							.addGap(18)
							.addComponent(SVozMen, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
							.addGap(29)
							.addComponent(LProdMen)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(SMenC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(LpolJ)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(SPolJ, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(340, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(LKolDet)
							.addGap(18)
							.addComponent(SKolDet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(CBKontr)
							.addContainerGap())))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LNslu)
								.addComponent(TNKart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LDatap)
								.addComponent(SDataPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(LDataMes)
								.addComponent(SDataM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(LYavka)
								.addComponent(SYavka, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(LKolp)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(SKolBer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(LKolRod)
									.addComponent(SParRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(LKolAb)
									.addComponent(SKolAb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
											.addComponent(LVozMen)
											.addComponent(SVozMen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
											.addComponent(LProdMen)
											.addComponent(SMenC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
									.addGap(12)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(LKolDet)
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
											.addComponent(SKolDet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(CBKontr))))
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(LpolJ)
										.addComponent(SPolJ, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(27)
									.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(LDataAb)
										.addComponent(TDataab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_4)
										.addComponent(SSrokA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LOslAb)
								.addComponent(CBOslAb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(LPrBer)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(TPrRod, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(54)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(LPlanRod)
											.addGap(18)
											.addComponent(LDataPlRod, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(CBRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(SDataRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
									.addGap(8)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblNewLabel)
										.addComponent(SDataSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblNewLabel_1)
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
											.addComponent(TSSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(lblNewLabel_2)
											.addComponent(TNSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
									.addGap(18)
									.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(LPrish)
										.addComponent(CBPrishSn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(LDataSn)
										.addComponent(TDatasn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 221, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(181)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(LDataOsl)
								.addComponent(SDataOsl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(107))
		);
		
		JLabel lblNewLabel_3 = new JLabel("C.Diag");
		
		JLabel lblCvera = new JLabel("C.vera");
		
		SCDiag = new JSpinner();
		
		SCvera = new JSpinner();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(LVes)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(SVes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(LIndSol)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(SindSol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(LTaz)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblDsp))
								.addComponent(lblCext))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(SCext, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblNewLabel_3)
									.addGap(18)
									.addComponent(SCDiag))
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(SDsp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblDcr)
									.addGap(18)
									.addComponent(SDcr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(18)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(lblDtroch)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(SDtroch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(lblCvera)
									.addGap(18)
									.addComponent(SCvera)))))
					.addContainerGap(120, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(LVes)
								.addComponent(SVes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(LIndSol)
								.addComponent(SindSol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(LTaz)
								.addComponent(lblDsp)
								.addComponent(SDsp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDcr)
								.addComponent(SDcr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblDtroch)
								.addComponent(SDtroch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCvera)
								.addComponent(SCvera, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(59)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblCext)
								.addComponent(SCext, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_3)
								.addComponent(SCDiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(79, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void setDefaultValues() {
		// TODO Auto-generated method stub
	try {
		rdsl.setId_pvizit(Vvod.zapVr.getId_pvizit());
		rdsl.setNpasp(Vvod.zapVr.getNpasp());
		rdsl.setCext(25);
		rdsl.setDsp(25);
		rdsl.setDsr(28);
		rdsl.setDTroch(31);
		rdsl.setIndsol(15);
		rdsl.setKolrod(0);
		rdsl.setShet(1);
		rdsl.setAbort(0);
		rdsl.setDeti(0);
		rdsl.setYavka1(4);
		rdsl.setVozmen(11);
		rdsl.setPrmen(28);
		rdsl.setPolj(18);
		rdsl.setCdiagt(5);
		rdsl.setCvera(11);
//		rdsl.setRost(160);
		rdsl.setVesd(60);
		rdsl.setOslab("");
		rdsl.setPrrod("");
		rdsl.setDataM(System.currentTimeMillis());
		rdsl.setDatay(System.currentTimeMillis());
		rdsl.setDataosl(System.currentTimeMillis());
		rdsl.setDatasn(System.currentTimeMillis());
		rdsl.setDataz(System.currentTimeMillis());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		JOptionPane.showMessageDialog(FormPostBer.this, e.getLocalizedMessage(), "Ошибка создания записи", JOptionPane.ERROR_MESSAGE);
		
	}
	
	}

	private void setPostBerData() {
		//SRost.setValue(rdsl.getRost());
		try {
			SVes.setValue(rdsl.getVesd());
			SDsp.setValue(rdsl.getDsp());
			SDcr.setValue(rdsl.getDsr());
			SDtroch.setValue(rdsl.getDTroch());
			SCext.setValue(rdsl.getCext());
			SindSol.setValue(rdsl.getIndsol());
			SDataPos.setDate(rdsl.getDatay());
			if (rdsl.getDatay() == 0)
			SDataPos.setText(null);
			TDataSn.setDate(rdsl.getDatasn());
			if (rdsl.getDatasn() == 0)
			TDataSn.setText(null);
			SDataRod.setDate(rdsl.getDataZs());
			if (rdsl.getDataZs() == 0)
			SDataRod.setText(null);
			SDataSert.setDate(rdsl.getDatasert());
			if (rdsl.getDatasert() == 0)
			SDataSert.setText(null);
			TSSert.setText(rdsl.ssert);
			TNSert.setText(rdsl.nsert);
			SParRod.setValue(rdsl.getKolrod());
			SKolBer.setValue(rdsl.getShet());
			TDataab.setDate(rdsl.getDataab());
			if (rdsl.getDataab() == 0)
			TDataab.setText(null);
			SDataSert.setDate(rdsl.getDatasert());
			if (rdsl.getDatasert() == 0)
			SDataSert.setText(null);
			TSSert.setText(rdsl.ssert);
			TNSert.setText(rdsl.nsert);
			TPrRod.setText(rdsl.prrod);
			SParRod.setValue(rdsl.getKolrod());
			SKolBer.setValue(rdsl.getShet());
			SDataOsl.setDate(rdsl.getDataosl());
			SYavka.setValue(rdsl.getYavka1());
			SDataM.setDate(rdsl.getDataM());
			if (rdsl.getDataM() == 0)
			SDataM.setText(null);
			SKolAb.setValue(rdsl.getAbort());
			SVozMen.setValue(rdsl.getVozmen());
			SMenC.setValue(rdsl.getPrmen());
			SKolDet.setValue(rdsl.getDeti());
			SPolJ.setValue(rdsl.getPolj());
			SSrokA.setValue(rdsl.getSrokab());
			SCDiag.setValue(rdsl.getCdiagt());
			SCvera.setValue(rdsl.getCvera());
			oslrod = rdsl.getOslrod();
			if(rdsl.isSetOslab())
			CBOslAb.setSelectedPcod(rdsl.getOslab());
			else CBOslAb.setSelectedItem(null);
			if (rdsl.isSetPlrod())
			CBRod.setSelectedPcod(rdsl.getPlrod());
			else CBRod.setSelectedItem(null);
			if (rdsl.isSetIshod())
			CBPrishSn.setSelectedPcod(rdsl.getIshod());
			else CBPrishSn.setSelectedItem(null);
			TNKart.setText(String.valueOf(rdsl.getId()));
			method2();
			CBKrov.setSelected(or1 == 1);
			CBEkl.setSelected(or2 == 1);
			CBGnoin.setSelected(or3 == 1);
			CBTromb.setSelected(or4 == 1);
			CDKesar.setSelected(or5 == 1);
			CBAkush.setSelected(or6 == 1);
			CBIiiiv.setSelected(or7 == 1);
			CBRazrProm.setSelected(or8 == 1);
			CBKontr.setSelected(rdsl.isKont());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(FormPostBer.this, e.getLocalizedMessage(), "Ошибка ссылки на запись", JOptionPane.ERROR_MESSAGE);
		}
}
	private String getTextOrNull(String str) {
		if (str != null)
			if (str.length() > 0)
				return str;
		
		return null;
	}
	private void method2(){
		if ((oslrod-128)<0){
		or8=0; iw1=oslrod;	
		}else {
		or8=1; iw1=oslrod-128;	
		}
		if ((iw1-64)<0){
		or7=0; 
		}else {
		or7=1; iw1=iw1-64;	
		}
		if ((iw1-32)<0){
		or6=0; 
		}else {
		or6=1; iw1=iw1-32;	
		}
		if ((iw1-16)<0){
		or5=0; 
		}else {
		or5=1; iw1=iw1-16;	
		}
		if ((iw1-8)<0){
		or4=0; 	
		}else {
		or4=1; iw1=iw1-8;	
		}
		if ((iw1-4)<0){
		or3=0; 
		}else {
		or3=1; iw1=iw1-4;	
		}
		if ((iw1-2)<0){
		or2=0; 
		}else {
		or2=1; iw1=iw1-2;	
		}
		or1=iw1; 
	}
	
	public void onConnect() throws PatientNotFoundException {
		try {
			PatientCommonInfo inf;
		inf = MainForm.tcl.getPatientCommonInfo(Vvod.zapVr.npasp);
//						tfPatient.setText("Пациент: "+inf.getFam()+" "+inf.getIm()+" "+inf.getOt()+" Номер и серия полиса: "+inf.getPoms_ser()+"  "+inf.getPoms_nom());
//fam.setText(inf.getFam());
//im.setText(inf.getIm());
//ot.setText(inf.getOt());
			CBOslAb.setData(MainForm.tcl.get_n_db9());
			CBRod.setData(MainForm.tcl.get_n_db8());
			CBPrishSn.setData(MainForm.tcl.get_n_db7());
		method2();	
			
		} catch (KmiacServerException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
			MainForm.conMan.reconnect(e);
		}
	}
}
