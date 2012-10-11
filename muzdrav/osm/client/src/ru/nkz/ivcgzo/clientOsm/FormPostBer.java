package ru.nkz.ivcgzo.clientOsm;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.KartaBer;
import ru.nkz.ivcgzo.thriftOsm.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.PrdslNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.RdInfStruct;
import ru.nkz.ivcgzo.thriftOsm.RdSlStruct;
//import ru.nkz.ivcgzo.thriftOsm.PsignNotFoundException;
//import ru.nkz.ivcgzo.;

public class FormPostBer extends JFrame {
	private static final long serialVersionUID = -1244773743749481104L;
	public static RdSlStruct rdSlStruct;
	private JPanel contentPane;
	private JTextField TNKart;
    private int oslrod;
    private int osostp;
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
    private JSpinner SRost;
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
	private int mes;
	private Date datr;
	private JCheckBox ChBeko;
	private JCheckBox ChBPred;
	private JCheckBox ChBRub;
	private JCheckBox CHosp1;
	private JCheckBox CHosp2;
	private JCheckBox CHosp3;
	private JCheckBox CHosp4;
	private JCheckBox CHosp5;
	private JCheckBox CHosp6;
	private JCheckBox CHosp7;
	private JCheckBox CHosp8;
	private JCheckBox CHosp9;
	private JCheckBox CHosp10;
	private JLabel lblNewLabel_5;

	/**
	 * Create the frame.
	 */
	public FormPostBer() {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setTitle("Постановка на учет по беременности");
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
			}
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
            osostp = 0;
            if (CHosp1.isSelected()){osostp=osostp+1;}
            if (CHosp2.isSelected()){osostp=osostp+2;}
            if (CHosp3.isSelected()){osostp=osostp+4;}
            if (CHosp4.isSelected()){osostp=osostp+8;}
            if (CHosp5.isSelected()){osostp=osostp+16;}
            if (CHosp6.isSelected()){osostp=osostp+32;}
            if (CHosp7.isSelected()){osostp=osostp+64;}
            if (CHosp8.isSelected()){osostp=osostp+128;}
            if (CHosp9.isSelected()){osostp=osostp+256;}
            if (CHosp10.isSelected()){osostp=osostp+512;}
//			System.out.println(oslrod);		
            };
			public void actionPerformed(ActionEvent arg0) {
				try {

			rdSlStruct.setId_pvizit(Vvod.zapVr.id_pvizit);
			rdSlStruct.setAbort((int) SKolAb.getValue());
			rdSlStruct.setCext((int) SCext.getModel().getValue());
 			if (SDataM.getDate() != null)
			rdSlStruct.setDataM( SDataM.getDate().getTime());
 			if (SDataOsl.getDate() != null)
			rdSlStruct.setDataosl( SDataOsl.getDate().getTime());
 			if (TDataSn.getDate() != null)
			rdSlStruct.setDatasn( TDataSn.getDate().getTime());
 			if (SDataRod.getDate() != null)
			rdSlStruct.setDataZs( SDataRod.getDate().getTime());
 			if (SDataSert.getDate() != null)
			rdSlStruct.setDatasert( SDataSert.getDate().getTime());
			rdSlStruct.setSsert(getTextOrNull(TSSert.getText()));
			rdSlStruct.setNsert(getTextOrNull(TNSert.getText()));
			rdSlStruct.setPrrod(getTextOrNull(TPrRod.getText()));
//			rdSlStruct.setPrrod(TPrRod.getText());
 			if (TDataab.getDate() != null)
			rdSlStruct.setDataab( TDataab.getDate().getTime());
			rdSlStruct.setSsert(getTextOrNull(TSSert.getText()));
			rdSlStruct.setNsert(getTextOrNull(TNSert.getText()));
 			if (SDataPos.getDate() != null)
			rdSlStruct.setDatay(SDataPos.getDate().getTime());
			rdSlStruct.setKont(CBKontr.isSelected());
			rdSlStruct.setEko(ChBeko.isSelected());
			rdSlStruct.setRub(ChBRub.isSelected());
			rdSlStruct.setPredp(ChBPred.isSelected());
			rdSlStruct.setDeti((int) SKolDet.getModel().getValue());
			rdSlStruct.setDsp((int) (SDsp.getModel()).getValue());
			rdSlStruct.setDsr((int) SDcr.getModel().getValue());
			rdSlStruct.setDTroch((int) SDtroch.getModel().getValue());
			rdSlStruct.setIndsol((int) SindSol.getModel().getValue());
			rdSlStruct.setShet((int) SKolBer.getModel().getValue());
			rdSlStruct.setKolrod((int) SParRod.getModel().getValue());
			rdSlStruct.setPolj((int) SPolJ.getModel().getValue());
			rdSlStruct.setPrmen((int) SMenC.getModel().getValue());
			rdSlStruct.setRost((int) SRost.getModel().getValue());
			rdSlStruct.setVesd((Double) SVes.getModel().getValue());
			rdSlStruct.setYavka1((int) SYavka.getModel().getValue());
           	rdSlStruct.setCdiagt((int) SCDiag.getModel().getValue());
           	rdSlStruct.setCvera((int) SCvera.getModel().getValue());
           	rdSlStruct.setDataz(System.currentTimeMillis());
			calcOslrod();
			rdSlStruct.setOslrod(oslrod);
			rdSlStruct.setOsp(osostp);
			if (CBOslAb.getSelectedPcod() != null)
				rdSlStruct.setOslab(CBOslAb.getSelectedPcod());
				else rdSlStruct.unsetOslab();
			if (CBRod.getSelectedPcod() != null)
				rdSlStruct.setPlrod(CBRod.getSelectedPcod());
				else rdSlStruct.unsetPlrod();
			if (CBPrishSn.getSelectedPcod() != null)
				rdSlStruct.setIshod(CBPrishSn.getSelectedPcod());
				else rdSlStruct.unsetIshod();
	System.out.println(rdSlStruct);		
		//		JOptionPane.showMessageDialog(FormPostBer.this, "Ошибка обновления");
				MainForm.tcl.UpdateRdSl(rdSlStruct);
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
		
		JButton ButDelete = new JButton("");
		ButDelete.setIcon(new ImageIcon(FormPostBer.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789259_Delete.png")));
		ButDelete.setToolTipText("Удалить");
		ButDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					MainForm.tcl.DeleteRdSl(rdSlStruct.getId(), rdSlStruct.getNpasp());
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
					oslname = "kart1"+String.valueOf(rdSlStruct.getId());
					cliPath = File.createTempFile(oslname, ".htm").getAbsolutePath();
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
		
		JButton btnNewButton_1 = new JButton("Динамическое наблюдение");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dinform = new FormRdDin();
				dinform.setVisible(true);
}
		});
		btnNewButton_1.setFont(new Font("Tahoma", Font.BOLD, 12));
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
							.addComponent(ot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnNewButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(ButSave)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(ButDelete)))
					.addGap(18)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(button)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(btnNewButton_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(BPeshOK, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)))
					.addContainerGap())
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 965, Short.MAX_VALUE)
					.addGap(7))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
								.addComponent(fam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(im, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(ot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(9)
							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
								.addComponent(btnNewButton)
								.addComponent(ButSave)
								.addComponent(ButDelete)))
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(button)
							.addGap(3)
							.addComponent(btnNewButton_1)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(BPeshOK)
					.addPreferredGap(ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 603, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JLabel LNslu = new JLabel("Номер обменной карты");
		
		JLabel LDatap = new JLabel("Дата первого посещения");
		
		JLabel LKolp = new JLabel("Паритет беременности");
		
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
		
		JLabel LKolDet = new JLabel("Количество детей");
		
		JLabel LpolJ = new JLabel("Половая жизнь со скольки лет");
		
		 CBKontr = new JCheckBox("Контрацепция");
//		CBKontr.setSelected(rdSlStruct.kont == true);
		
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
		
		SDsp = new JSpinner();
		SDsp.setModel(new SpinnerNumberModel(0, 0, 30, 1));
		
		SDcr = new JSpinner();
		SDcr.setModel(new SpinnerNumberModel(0, 0, 30, 1));
		
		SDtroch = new JSpinner();
		SDtroch.setModel(new SpinnerNumberModel(0,0,33,1));
		
		SCext = new JSpinner();
		SCext.setModel(new SpinnerNumberModel(0, 0, 35, 1));
		
		SindSol = new JSpinner();
		SindSol.setModel(new SpinnerNumberModel(0, 0, 20,1));
		
		CBKrov = new JCheckBox("Кровотечение");
//		CBKrov.setSelected(or1 == 1);
		
		JLabel LIshPoslB = new JLabel("Осложнения предыдущих родов");
		LIshPoslB.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		CBEkl = new JCheckBox("Проэкламсия - экламсия");
//		CBEkl.setSelected(or2 ==1);
		
		CBGnoin = new JCheckBox("Гнойно-септические осложнения");
//		CBGnoin.setSelected(or3 ==1);
		
		CBTromb = new JCheckBox("Тромбоэмболитические осложнения");
//		CBTromb.setSelected(or4 ==1);
		
		CDKesar = new JCheckBox("Кесарево сечение");
//		CDKesar.setSelected(or5 ==1);
		
		CBAkush = new JCheckBox("Акушерские щипцы");
//		CBAkush.setSelected(or6 ==1);
		
		CBIiiiv = new JCheckBox("Разрав промежности III-IV степени");
//		CBIiiiv.setSelected(or7 ==1);
		
		CBRazrProm = new JCheckBox("Разрав шейки матки III степени");
//		CBRazrProm.setSelected(or8 ==1);
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(LIshPoslB)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(CBKrov)
								.addComponent(CBEkl)
								.addComponent(CBGnoin)
								.addComponent(CBTromb))
							.addGap(21)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(CBRazrProm)
								.addComponent(CBIiiiv)
								.addComponent(CBAkush)
								.addComponent(CDKesar))))
					.addContainerGap(92, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(LIshPoslB)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(CBKrov)
						.addComponent(CDKesar))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(CBEkl)
						.addComponent(CBAkush))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(CBGnoin)
						.addComponent(CBIiiiv))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(CBTromb)
						.addComponent(CBRazrProm))
					.addContainerGap(106, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		TNKart = new JTextField();
		TNKart.setColumns(10);
		SDataPos = new CustomDateEditor();
		
		SParRod = new JSpinner();
		SParRod.setModel(new SpinnerNumberModel(0, 0, 20, 1));
		
		SKolBer = new JSpinner();
		SKolBer.setModel(new SpinnerNumberModel(0, 0, 50, 1));
		
		SDataOsl = new CustomDateEditor();
		
		SDataSert = new CustomDateEditor();

		SDataRod = new CustomDateEditor();
		SDataRod.setColumns(10);

		SYavka = new JSpinner();
		SYavka.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent arg0) {
			/*	if (rdSlStruct.getDataZs() = null)*/ {
		        rdSlStruct.setDataZs(rdSlStruct.getDatay()+(280-(rdSlStruct.getYavka1()*7))*864*100000);
			SDataRod.setDate(rdSlStruct.getDataZs());}
			}
		});
		SYavka.setModel(new SpinnerNumberModel(0,0, 40,1));
		
		SDataM = new CustomDateEditor();
		
		SKolAb = new JSpinner();
		SKolAb.setModel(new SpinnerNumberModel(0, 0, 50, 1));
//		rdSlStruct.setAbort((int) SKolAb.getModel().getValue());
		
		SVozMen = new JSpinner();
		SVozMen.setModel(new SpinnerNumberModel(8 , 8, 30, 1));
//		rdSlStruct.setVozmen((int) SVozMen.getModel().getValue());
		
		SMenC = new JSpinner();
		SMenC.setModel(new SpinnerNumberModel(20, 20,  60, 1));
//		rdSlStruct.setPrmen((int) SMenC.getModel().getValue());
		
		SKolDet = new JSpinner();
		SKolDet.setModel(new SpinnerNumberModel(0, 0, 20, 1));
		
		SPolJ = new JSpinner();
		SPolJ.setModel(new SpinnerNumberModel(9, 9, 40, 1));
		
		CBPrishSn = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db7);
		
		CBRod = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_db8);
		
		CBOslAb = new ThriftStringClassifierCombobox<>(StringClassifiers.n_db9);
		
		JLabel lblNewLabel = new JLabel("Дата выдачи родового сертификата");
		
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
		
		TDataSn = new CustomDateEditor();
		
		ChBeko = new JCheckBox("Беременность после ЕКО");
		
		ChBPred = new JCheckBox("Предгравидарная подготовка");
		
		ChBRub = new JCheckBox("Рубец на матке");
		
		lblNewLabel_5 = new JLabel("Оценка состояния плода");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JCheckBox CHosp1 = new JCheckBox("Несоответствие ВДМ гистационному сроку");
		
		JCheckBox CHosp2 = new JCheckBox("Отставание фетометрических показателей от гестационного срока");
		
		JCheckBox Chosp3 = new JCheckBox("ЧСС плода 110 ударов в минуту и менее");
		
		JCheckBox CHosp4 = new JCheckBox("ЧСС плода 160 ударов в минуту и более");
		
		JCheckBox CHosp5 = new JCheckBox("Многоводие");
		
		JCheckBox CHosp6 = new JCheckBox("Маловодие");
		
		JCheckBox CHosp7 = new JCheckBox("Нарушение кровотока в артерии пуповины");
		
		JCheckBox CHosp8 = new JCheckBox("Нулевой или реверсивный кровоток");
		
		JCheckBox CHosp9 = new JCheckBox("Средняя оценка КТГ по Fisher 6 и менее баллов");
		
		JCheckBox CHosp10 = new JCheckBox("Ареактивный нестрассовый тест");
	
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(LNslu)
										.addComponent(LDatap))
									.addGap(51)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(TNKart, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
										.addComponent(SDataPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(4)
									.addComponent(LDataMes)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(SDataM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(LYavka)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(SYavka, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(LKolp)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(SKolBer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(LKolRod)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(SParRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(LKolDet)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(SKolDet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(LKolAb)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(SKolAb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(LVozMen)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(SVozMen, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(LProdMen)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(SMenC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(LpolJ)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(SPolJ, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(TDataab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(lblNewLabel_4)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(SSrokA, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panel.createSequentialGroup()
													.addComponent(ChBPred)
													.addPreferredGap(ComponentPlacement.UNRELATED)
													.addComponent(ChBRub))
												.addGroup(gl_panel.createSequentialGroup()
													.addComponent(LOslAb)
													.addPreferredGap(ComponentPlacement.UNRELATED)
													.addComponent(CBOslAb, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE))))))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(CBKontr)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(ChBeko))
								.addComponent(LDataAb)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(LPrish)
										.addComponent(LDataSn))
									.addGap(24)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(TDataSn, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
										.addComponent(CBPrishSn, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
									.addGap(31)
									.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 465, GroupLayout.PREFERRED_SIZE))
								.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 692, GroupLayout.PREFERRED_SIZE)
								.addComponent(LPrBer)
								.addComponent(TPrRod, GroupLayout.PREFERRED_SIZE, 349, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(LPlanRod)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(CBRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(LDataPlRod)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(SDataRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(lblNewLabel_5)
										.addGroup(gl_panel.createSequentialGroup()
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
												.addGroup(gl_panel.createSequentialGroup()
													.addComponent(LDataOsl)
													.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.addComponent(SDataOsl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_panel.createSequentialGroup()
													.addComponent(lblNewLabel)
													.addPreferredGap(ComponentPlacement.UNRELATED)
													.addComponent(SDataSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
											.addGap(18)
											.addComponent(lblNewLabel_1)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(TSSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(lblNewLabel_2)))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(TNSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(64)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(CHosp2)
								.addComponent(CHosp1)
								.addComponent(Chosp3)
								.addComponent(CHosp4)
								.addComponent(CHosp5))
							.addGap(86)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(CHosp6)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(CHosp7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addGap(85))
										.addComponent(CHosp8)
										.addComponent(CHosp9))
									.addGap(73))
								.addComponent(CHosp10))))
					.addGap(37))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
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
						.addComponent(SYavka, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(LKolp)
						.addComponent(SKolBer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(LKolRod)
						.addComponent(SParRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(SKolAb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(LKolAb)
							.addComponent(SKolDet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(LVozMen)
							.addComponent(SVozMen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(LProdMen)
							.addComponent(SMenC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(LpolJ)
							.addComponent(SPolJ, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(LKolDet))
					.addGap(7)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(LDataAb)
						.addComponent(TDataab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_4)
						.addComponent(SSrokA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(LOslAb)
						.addComponent(CBOslAb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(CBKontr)
						.addComponent(ChBeko)
						.addComponent(ChBPred)
						.addComponent(ChBRub))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(LPrBer)
							.addGap(2)
							.addComponent(TPrRod, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
							.addGap(54)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(LPlanRod)
										.addComponent(CBRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(18)
									.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNewLabel)
										.addComponent(SDataSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_1)
										.addComponent(TSSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblNewLabel_2)
										.addComponent(TNSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(LDataPlRod, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
										.addComponent(SDataRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(41))))
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LDataOsl)
								.addComponent(SDataOsl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(22))
						.addComponent(lblNewLabel_5))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(CHosp1)
						.addComponent(CHosp6))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(CHosp2)
						.addComponent(CHosp7))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(Chosp3)
						.addComponent(CHosp8))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(CHosp4)
						.addComponent(CHosp9))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(CHosp5)
							.addGap(88)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LPrish)
								.addComponent(CBPrishSn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LDataSn)
								.addComponent(TDataSn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(CHosp10)))
		);
		
		JLabel lblNewLabel_3 = new JLabel("C.Diag");
		
		JLabel lblCvera = new JLabel("C.vera");
		
		SCDiag = new JSpinner();
		SCDiag.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		
		SCvera = new JSpinner();
		
		JLabel LRost = new JLabel("Рост");
		
		SRost = new JSpinner();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(LTaz)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblDsp)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(SDsp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblDcr)
							.addGap(18)
							.addComponent(SDcr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(LRost)
							.addGap(18)
							.addComponent(SRost, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(LVes)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(SVes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(LIndSol)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(SindSol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblDtroch)
							.addGap(18)
							.addComponent(SDtroch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblCext)
							.addGap(18)
							.addComponent(SCext, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(lblNewLabel_3)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(SCDiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblCvera)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(SCvera, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(334))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(SindSol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(SRost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(LVes)
						.addComponent(SVes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(LIndSol)
						.addComponent(LRost))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(LTaz)
						.addComponent(lblDsp)
						.addComponent(SDsp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDcr)
						.addComponent(SDcr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDtroch)
						.addComponent(SDtroch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCext)
						.addComponent(SCext, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3)
						.addComponent(SCDiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblCvera)
						.addComponent(SCvera, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void setDefaultValues() {
	try {
		rdSlStruct.setId_pvizit(Vvod.zapVr.getId_pvizit());
		rdSlStruct.setNpasp(Vvod.zapVr.getNpasp());
		rdSlStruct.setCext(25);
		rdSlStruct.setDsp(25);
		rdSlStruct.setDsr(28);
		rdSlStruct.setDTroch(31);
		rdSlStruct.setIndsol(15);
		rdSlStruct.setKolrod(0);
		rdSlStruct.setShet(1);
		rdSlStruct.setAbort(0);
		rdSlStruct.setDeti(0);
		rdSlStruct.setRost(160);
		rdSlStruct.setYavka1(4);
		rdSlStruct.setVozmen(11);
		rdSlStruct.setPrmen(28);
		rdSlStruct.setPolj(18);
		rdSlStruct.setCdiagt(5);
		rdSlStruct.setCvera(11);
		rdSlStruct.setVesd(60);
		rdSlStruct.setOslab(null);
		rdSlStruct.setPrrod(null);
		rdSlStruct.setDataM(System.currentTimeMillis());
		rdSlStruct.setDatay(System.currentTimeMillis());
		rdSlStruct.setDataosl(System.currentTimeMillis());
		rdSlStruct.setDatasn(System.currentTimeMillis());
		rdSlStruct.setDataz(System.currentTimeMillis());
        rdSlStruct.setDataZs(System.currentTimeMillis()+217728*100000);
		Calendar cal1 = Calendar.getInstance();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		JOptionPane.showMessageDialog(FormPostBer.this, e.getLocalizedMessage(), "Ошибка создания записи", JOptionPane.ERROR_MESSAGE);
		
	}
	
	}

	private void setPostBerData() {
		//SRost.setValue(rdSlStruct.getRost());
		try {
			SVes.setValue(rdSlStruct.getVesd());
			SDsp.setValue(rdSlStruct.getDsp());
			SDcr.setValue(rdSlStruct.getDsr());
			SDtroch.setValue(rdSlStruct.getDTroch());
			SCext.setValue(rdSlStruct.getCext());
			SindSol.setValue(rdSlStruct.getIndsol());
			SDataPos.setDate(rdSlStruct.getDatay());
			if (rdSlStruct.getDatay() == 0)
			SDataPos.setText(null);
			TDataSn.setDate(rdSlStruct.getDatasn());
			if (rdSlStruct.getDatasn() == 0)
			TDataSn.setText(null);
			SDataRod.setDate(rdSlStruct.getDataZs());
			if (rdSlStruct.getDataZs() == 0)
			SDataRod.setText(null);
			SDataSert.setDate(rdSlStruct.getDatasert());
			if (rdSlStruct.getDatasert() == 0)
			SDataSert.setText(null);
			TSSert.setText(rdSlStruct.ssert);
			TNSert.setText(rdSlStruct.nsert);
			SParRod.setValue(rdSlStruct.getKolrod());
			SKolBer.setValue(rdSlStruct.getShet());
			TDataab.setDate(rdSlStruct.getDataab());
			if (rdSlStruct.getDataab() == 0)
			TDataab.setText(null);
			SDataSert.setDate(rdSlStruct.getDatasert());
			if (rdSlStruct.getDatasert() == 0)
			SDataSert.setText(null);
			TSSert.setText(rdSlStruct.ssert);
			TNSert.setText(rdSlStruct.nsert);
			TPrRod.setText(rdSlStruct.prrod);
			SParRod.setValue(rdSlStruct.getKolrod());
			SKolBer.setValue(rdSlStruct.getShet());
			SDataOsl.setDate(rdSlStruct.getDataosl());
			SYavka.setValue(rdSlStruct.getYavka1());
			SRost.setValue(rdSlStruct.getRost());
			SDataM.setDate(rdSlStruct.getDataM());
			if (rdSlStruct.getDataM() == 0)
			SDataM.setText(null);
			SKolAb.setValue(rdSlStruct.getAbort());
			SVozMen.setValue(rdSlStruct.getVozmen());
			SMenC.setValue(rdSlStruct.getPrmen());
			SKolDet.setValue(rdSlStruct.getDeti());
			SPolJ.setValue(rdSlStruct.getPolj());
			SSrokA.setValue(rdSlStruct.getSrokab());
			SCDiag.setValue(rdSlStruct.getCdiagt());
			SCvera.setValue(rdSlStruct.getCvera());
			oslrod = rdSlStruct.getOslrod();
			osostp = rdSlStruct.getOsp();
			if(rdSlStruct.isSetOslab())
			CBOslAb.setSelectedPcod(rdSlStruct.getOslab());
			else CBOslAb.setSelectedItem(null);
			if (rdSlStruct.isSetPlrod())
			CBRod.setSelectedPcod(rdSlStruct.getPlrod());
			else CBRod.setSelectedItem(null);
			if (rdSlStruct.isSetIshod())
			CBPrishSn.setSelectedPcod(rdSlStruct.getIshod());
			else CBPrishSn.setSelectedItem(null);
			TNKart.setText(String.valueOf(rdSlStruct.getId()));
			method2();
/*			CBKrov.setSelected(or1 == 1);
			CBEkl.setSelected(or2 == 1);
			CBGnoin.setSelected(or3 == 1);
			CBTromb.setSelected(or4 == 1);
			CDKesar.setSelected(or5 == 1);
			CBAkush.setSelected(or6 == 1);
			CBIiiiv.setSelected(or7 == 1);
			CBRazrProm.setSelected(or8 == 1);*/
			CBKontr.setSelected(rdSlStruct.isKont());
			ChBeko.setSelected(rdSlStruct.isEko());
			ChBRub.setSelected(rdSlStruct.isRub());
			ChBPred.setSelected(rdSlStruct.isPredp());
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
		if ((oslrod-128)>=0)
		{CBRazrProm.setSelected(true);   iw1=oslrod-128;}	
		if ((iw1-64)>=0)
		{CBIiiiv.setSelected(true);		iw1=iw1-64;}	
		if ((iw1-32)>=0)
		{CBAkush.setSelected(true);		iw1=iw1-32;}	
		if ((iw1-16)>=0)
		{CDKesar.setSelected(true);		iw1=iw1-16;}	
		if ((iw1-8)>=0)
		{CBTromb.setSelected(true);		iw1=iw1-8;}	
		if ((iw1-4)>=0) 
		{CBGnoin.setSelected(true);		iw1=iw1-4;}	
		if ((iw1-2)>=0)
		{CBEkl.setSelected(true);		iw1=iw1-2;}	
		CBKrov.setSelected(iw1 ==1 );
		if ((osostp-512)>=0)
		{CHosp10.setSelected(true);   iw1=osostp-512;}	
		if ((iw1-256)>=0)
		{CHosp9.setSelected(true);   iw1=iw1-256;}	
		if ((iw1-128)>=0)
		{CHosp8.setSelected(true);   iw1=iw1-128;}	
		if ((iw1-64)>=0)
		{CHosp7.setSelected(true);   iw1=iw1-64;}	
		if ((iw1-32)>=0)
		{CHosp6.setSelected(true);   iw1=iw1-32;}	
		if ((iw1-16)>=0)
		{CHosp5.setSelected(true);   iw1=iw1-16;}	
		if ((iw1-8)>=0)
		{CHosp4.setSelected(true);   iw1=iw1-8;}	
		if ((iw1-4)>=0)
		{CHosp3.setSelected(true);   iw1=iw1-4;}	
		if ((iw1-2)>=0)
		{CHosp2.setSelected(true);   iw1=iw1-2;}	
		CHosp1.setSelected(iw1 ==1 );
	}
	
	public void showForm() {
		fam.setText(Vvod.zapVr.getFam());
		im.setText(Vvod.zapVr.getIm());
		ot.setText(Vvod.zapVr.getOth());
		
		try {
			rdSlStruct = new RdSlStruct();
			setDefaultValues();
			RdInfStruct rdinf = new RdInfStruct();
			rdinf.setNpasp(Vvod.zapVr.getNpasp());
			rdinf.setDataz(System.currentTimeMillis());
            MainForm.tcl.AddRdInf(rdinf);
			rdSlStruct = MainForm.tcl.getRdSlInfo(Vvod.zapVr.getId_pvizit(), Vvod.zapVr.getNpasp());
			setPostBerData();
		} catch (PrdslNotFoundException e1) {
			try {
				rdSlStruct.setId(MainForm.tcl.AddRdSl(rdSlStruct));
				setPostBerData();
			} catch (KmiacServerException e2) {
				JOptionPane.showMessageDialog(FormPostBer.this, "Не удалось поставить на учет", "Ошибка", JOptionPane.ERROR_MESSAGE);
			} catch (TException e2) {
				e2.printStackTrace();
				MainForm.conMan.reconnect(e2);
			}
		} catch (KmiacServerException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(FormPostBer.this, e1.getLocalizedMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
		} catch (TException e1) {
			e1.printStackTrace();
			MainForm.conMan.reconnect(e1);
		}
		
		setVisible(true);	
	}
	
	public void onConnect() throws PatientNotFoundException {
		method2();	
	}
}
