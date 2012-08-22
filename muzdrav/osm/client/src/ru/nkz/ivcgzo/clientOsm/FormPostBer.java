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
//import ru.nkz.ivcgzo.thriftOsm.PsignNotFoundException;
//import ru.nkz.ivcgzo.;
import ru.nkz.ivcgzo.thriftOsm.PatientCommonInfo;
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
    JSpinner SRost;
    JSpinner SVes;
    JSpinner SDcp;
    JSpinner SDcr;
    JSpinner SDtroch;
    JSpinner SCext;
    JSpinner SindSol;
    CustomDateEditor SDataPos;
    JSpinner SParRod;
    JSpinner SKolBer;
    CustomDateEditor SDataOsl;
    CustomDateEditor SDataM;
    CustomDateEditor SDataSn;
    CustomDateEditor SDataRod;
    CustomDateEditor SDataSert;
    CustomDateEditor TDataab;
    JSpinner SYavka;
    JSpinner SKolAb;
    JSpinner SVozMen;
    JSpinner SMenC;
    JSpinner SKolDet;
    JSpinner SPolJ;
    JSpinner SSrokA;
    JCheckBox CBKrov; 
    JCheckBox CBEkl; 
    JCheckBox CBGnoin; 
    JCheckBox CBTromb; 
    JCheckBox CBKesar; 
    JCheckBox CBAkush; 
    JCheckBox CBIiiiv; 
    JCheckBox CBRazrProm; 
	private ThriftStringClassifierCombobox<StringClassifier> CBOslAb;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBRod;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBPrishSn;
//    private JTextField SDataRod;
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					FormPostBer frame = new FormPostBer();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
	protected AbstractButton CBKontr;
	protected AbstractButton SDsp;
	private JTextField fam;
	private JTextField im;
	private JTextField ot;
	private JTextField TSSert;
	private JTextField TNSert;

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
//			rdsl.getAbort(SKolAb.value);
	//	SKolAb.get(rdsl.abort);
			}
		});
		setTitle("Постановка на учет по беременности");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 700);
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
					rdsl.setId_pvizit(Vvod.zapVr.getId_pvizit());
					rdsl.setNpasp(Vvod.zapVr.getNpasp());
					setPostBerData(rdsl);
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
			private AbstractButton sVozMen;
            private int getoslrod(int oslrod){
            	oslrod=0;
            if (CBKrov.isSelected()){oslrod=oslrod+1;}
            if (CBEkl.isSelected()){oslrod=oslrod+2;}
            if (CBGnoin.isSelected()){oslrod=oslrod+4;}
            if (CBTromb.isSelected()){oslrod=oslrod+8;}
            if (CBKesar.isSelected()){oslrod=oslrod+16;}
            if (CBAkush.isSelected()){oslrod=oslrod+32;}
            if (CBIiiiv.isSelected()){oslrod=oslrod+64;}
            if (CBRazrProm.isSelected()){oslrod=oslrod+128;}
			return oslrod;	
            };
			private int kontrac(int iw3){
				if (CBKontr.isSelected()){iw3 = 1;}
				return iw3;
			};
			public void actionPerformed(ActionEvent arg0) {
	/*			patient.setFam((String) fam.getText());
				patient.setIm((String)im.getText());
				patient.setOt((String) ot.getText());*/
			rdsl.setAbort((int) SKolAb.getValue());
			rdsl.setCext((int) SCext.getModel().getValue());
			rdsl.setDataM( SDataM.getDate().getTime());
			rdsl.setDataosl( SDataOsl.getDate().getTime());
			rdsl.setDatasn( SDataSn.getDate().getTime());
			rdsl.setDatasn( SDataRod.getDate().getTime());
			rdsl.setDatasert( SDataSert.getDate().getTime());
			rdsl.setSsert(getTextOrNull(TSSert.getText()));
			rdsl.setNsert(getTextOrNull(TNSert.getText()));
			rdsl.setDatasn( TDataab.getDate().getTime());
			rdsl.setDatasert( SDataSert.getDate().getTime());
			rdsl.setSsert(getTextOrNull(TSSert.getText()));
			rdsl.setNsert(getTextOrNull(TNSert.getText()));
			rdsl.setDatay(SDataPos.getDate().getTime());
//			rdsl.setKont();
			rdsl.setDeti((int) SKolDet.getModel().getValue());
			rdsl.setDsp((int) ((JSpinner) SDsp.getModel()).getValue());
			rdsl.setDsr((int) SDcr.getModel().getValue());
			rdsl.setDTroch((int) SDtroch.getModel().getValue());
			rdsl.setIndsol((int) SindSol.getModel().getValue());
			rdsl.setShet((int) SKolBer.getModel().getValue());
			rdsl.setKolrod((int) SParRod.getModel().getValue());
			rdsl.setPolj((int) SPolJ.getModel().getValue());
			rdsl.setPrmen((int) SMenC.getModel().getValue());
//			rdsl.setRost((int) SRost.getModel().getValue());
			rdsl.setVesd((int) SVes.getModel().getValue());
			rdsl.setYavka1((int) SYavka.getModel().getValue());
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
			
			try {
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
		im.setText("");
		im.setColumns(10);
//		im.setText(Vvod.zapVr.im);
		
		ot = new JTextField();
		ot.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		ot.setText("");
		ot.setColumns(10);
//		ot.setText(Vvod.zapVr.oth);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(btnNewButton)
							.addGap(18)
							.addComponent(ButSave)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(ButDelete)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnNewButton_1)
							.addContainerGap())
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 1032, Short.MAX_VALUE)))
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(fam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(im, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(682))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(fam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(im, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(ot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnNewButton)
						.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
							.addComponent(ButSave)
							.addComponent(button)
							.addComponent(ButDelete)
							.addComponent(btnNewButton_1)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 617, Short.MAX_VALUE)
					.addContainerGap())
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
		
		JLabel LDataPlRod = new JLabel("Дата планируемых родов");
		
		JLabel LPrish = new JLabel("Причина снятия с учета");
		
		JLabel LKolRod = new JLabel("Паритет родов");
		
		JLabel LVozMen = new JLabel("Возраст Менархе");
		
		JLabel LProdMen = new JLabel("Продолжительность менстр. цикла");
		
		JLabel LKolDet = new JLabel("Количество живых детей");
		
		JLabel LpolJ = new JLabel("Половая жизнь со скольки лет");
		
		final JCheckBox CBKontr = new JCheckBox("Контрацепция");
//		CBKontr.setSelected(rdsl.kont == true);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(212, 208, 200));
		
		JLabel LDataSn = new JLabel("Дата снятия с учета");
		
		JPanel panel_2 = new JPanel();
		
		JLabel LRost = new JLabel("Рост");
		
		JLabel LVes = new JLabel("Вес до беременности");
		
		JLabel LTaz = new JLabel("Таз:");
		
		JLabel lblDsp = new JLabel("DSP");
		
		JLabel lblDcr = new JLabel("DCR");
		
		JLabel lblDtroch = new JLabel("DTROCH");
		
		JLabel lblCext = new JLabel("C.ext");
		
		JLabel LIndSol = new JLabel("Индекс Соловьева");
		
		SRost = new JSpinner();

		final JSpinner SVes = new JSpinner();
		SVes.setModel(new SpinnerNumberModel(0, 0, 250, 1));
//		rdsl.setVesd((int) SVes.getModel().getValue());
		
		final JSpinner SDsp = new JSpinner();
		SDsp.setModel(new SpinnerNumberModel(0,0, 27,1));
//		rdsl.setDsp((int) SDsp.getModel().getValue());
		
		final JSpinner SDcr = new JSpinner();
		SDcr.setModel(new SpinnerNumberModel(0,0, 30, 1));
//		rdsl.setDsr((int) SDsp.getModel().getValue());
		
		final JSpinner SDtroch = new JSpinner();
		SDtroch.setModel(new SpinnerNumberModel(0,0,33,1));
//		rdsl.setDTroch((int) SDtroch.getModel().getValue());
		
		final JSpinner SCext = new JSpinner();
		SCext.setModel(new SpinnerNumberModel(0,0, 35, 1));
//		rdsl.setCext((int) SCext.getModel().getValue());
		
		final JSpinner SindSol = new JSpinner();
		SindSol.setModel(new SpinnerNumberModel(0, 0, 20,1));
//		rdsl.setIndsol((int) SindSol.getModel().getValue());
		
		final JCheckBox CBKrov = new JCheckBox("Кровотечение");
		CBKrov.setSelected(or1 == 1);
		
		JLabel LIshPoslB = new JLabel("Исход последней беременности");
		LIshPoslB.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		final JCheckBox CBEkl = new JCheckBox("Проэкламсия - экламсия");
		CBEkl.setSelected(or2 ==1);
		
		final JCheckBox CBGnoin = new JCheckBox("Гнойно-септические осложнения");
		CBGnoin.setSelected(or3 ==1);
		
		final JCheckBox CBTromb = new JCheckBox("Тромбоэмболитические осложнения");
		CBTromb.setSelected(or4 ==1);
		
		final JCheckBox CDKesar = new JCheckBox("Кесарево сечение");
		CDKesar.setSelected(or5 ==1);
		
		final JCheckBox CBAkush = new JCheckBox("Акушерские щипцы");
		CBAkush.setSelected(or6 ==1);
		
		final JCheckBox CBIiiiv = new JCheckBox("Разрав промежности III-IV степени");
		CBIiiiv.setSelected(or7 ==1);
		
		final JCheckBox CBRazrProm = new JCheckBox("Разрав шейки матки III степени");
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
		
		final JSpinner SParRod = new JSpinner();
		SParRod.setModel(new SpinnerNumberModel(0, 0, 20, 1));
//		rdsl.setKolrod((int) SParRod.getModel().getValue());
		
		final JSpinner SKolBer = new JSpinner();
		SKolBer.setModel(new SpinnerNumberModel(0, 0, 50, 1));
//		rdsl.setShet((int) SKolBer.getModel().getValue());
		
		SDataOsl = new CustomDateEditor();
		
		SDataSert = new CustomDateEditor();

		final JSpinner SYavka = new JSpinner();
		SYavka.setModel(new SpinnerNumberModel(0,0, 40,1));
//		rdsl.setYavka1((int) SYavka.getModel().getValue());
		
		SDataM = new CustomDateEditor();
		
		SDataRod = new CustomDateEditor();
		SDataRod.setColumns(10);
//		Calendar cal1 = Calendar.getInstance();
//		cal1.setTimeInMillis(rdsl.getDatay());
//		cal1.add(Calendar.DAY_OF_MONTH, (280-(rdsl.getYavka1()*7)));
//		SDataRod.setDate(cal1);
		
		final JSpinner SKolAb = new JSpinner();
		SKolAb.setModel(new SpinnerNumberModel(0, 0, 50, 1));
//		rdsl.setAbort((int) SKolAb.getModel().getValue());
		
		final JSpinner SVozMen = new JSpinner();
		SVozMen.setModel(new SpinnerNumberModel(8 , 8, 30, 1));
//		rdsl.setVozmen((int) SVozMen.getModel().getValue());
		
		final JSpinner SMenC = new JSpinner();
		SMenC.setModel(new SpinnerNumberModel(20, 20,  60, 1));
//		rdsl.setPrmen((int) SMenC.getModel().getValue());
		
		final JSpinner SKolDet = new JSpinner();
		SKolDet.setModel(new SpinnerNumberModel(0, 0, 20, 1));
//		rdsl.setDeti((int) SKolDet.getModel().getValue());
		
		final JSpinner SPolJ = new JSpinner();
		SPolJ.setModel(new SpinnerNumberModel(9, 9, 40, 1));
//		rdsl.setPolj((int) SPolJ.getModel().getValue());
		
//		final JSpinner SDataSn = new JSpinner();
//		SDataSn.setBackground(new Color(212, 208, 200));
//		SDataSn.setModel(new SpinnerDateModel(new Date(rdsl.Datasn), System.currentTimeMillis(),(System.currentTimeMillis()+280), Calendar.DAY_OF_YEAR));
//		rdsl.setDatasn((long) SDataSn.getModel().getValue());
		SDataSn = new CustomDateEditor();
		
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
		
		final JSpinner SSrokA = new JSpinner();
		SSrokA.setModel(new SpinnerNumberModel(0,0, 40,1 ));
//		rdsl.setSrokab((int) SSrokA.getModel().getValue());

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
														.addGroup(gl_panel.createSequentialGroup()
															.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
																.addGroup(gl_panel.createSequentialGroup()
																	.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
																		.addGroup(gl_panel.createSequentialGroup()
																			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
																				.addComponent(LNslu)
																				.addComponent(LDatap)
																				.addComponent(LYavka)
																				.addComponent(LDataMes)
																				.addComponent(LKolAb)
																				.addComponent(LKolp)
																				.addComponent(LKolRod))
																			.addGap(9))
																		.addGroup(gl_panel.createSequentialGroup()
																			.addComponent(LOslAb)
																			.addGap(18)))
																	.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
																		.addComponent(CBOslAb, GroupLayout.PREFERRED_SIZE, 292, GroupLayout.PREFERRED_SIZE)
																		.addGroup(gl_panel.createSequentialGroup()
																			.addComponent(lblNewLabel_4)
																			.addPreferredGap(ComponentPlacement.RELATED)
																			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
																				.addComponent(SKolAb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																				.addComponent(SSrokA, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
																		.addGroup(gl_panel.createSequentialGroup()
																			.addGap(31)
																			.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
																				.addComponent(SDataM, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																				.addComponent(SParRod, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																				.addGroup(Alignment.LEADING, gl_panel.createParallelGroup(Alignment.TRAILING, false)
																					.addComponent(SYavka, Alignment.LEADING)
																					.addComponent(SKolBer, Alignment.LEADING))
																				.addGroup(Alignment.LEADING, gl_panel.createParallelGroup(Alignment.TRAILING)
																					.addComponent(TNKart, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
																					.addComponent(SDataPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))))
																.addComponent(LPrish)
																.addGroup(gl_panel.createSequentialGroup()
																	.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
																		.addComponent(LPlanRod)
																		.addComponent(LDataSn)
																		.addComponent(LDataPlRod)
																		.addComponent(TSSert, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																	.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
																		.addGroup(gl_panel.createSequentialGroup()
																			.addGap(67)
																			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
																				.addComponent(SDataRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
																					.addComponent(CBRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
																						.addComponent(CBPrishSn, Alignment.TRAILING, 0, 246, Short.MAX_VALUE)
																						.addComponent(SDataSn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
																							.addComponent(SVozMen, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)
																							.addComponent(SKolDet, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																							.addComponent(SMenC, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																							.addComponent(SPolJ, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
																							.addComponent(SDataSert, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
																							.addComponent(SDataOsl, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
																		.addGroup(gl_panel.createSequentialGroup()
																			.addGap(18)
																			.addComponent(lblNewLabel_2)
																			.addGap(18)
																			.addComponent(TNSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
															.addGap(18))
														.addGroup(gl_panel.createSequentialGroup()
															.addComponent(LProdMen)
															.addPreferredGap(ComponentPlacement.RELATED)))
													.addGroup(gl_panel.createSequentialGroup()
														.addComponent(LpolJ)
														.addPreferredGap(ComponentPlacement.RELATED)))
												.addGroup(gl_panel.createSequentialGroup()
													.addComponent(LDataOsl)
													.addPreferredGap(ComponentPlacement.RELATED)))
											.addGroup(gl_panel.createSequentialGroup()
												.addComponent(LKolDet)
												.addPreferredGap(ComponentPlacement.RELATED)))
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(CBKontr)
											.addPreferredGap(ComponentPlacement.RELATED)))
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(lblNewLabel)
										.addPreferredGap(ComponentPlacement.RELATED)))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblNewLabel_1)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(LVozMen)
								.addGap(364)))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(LDataAb)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(TDataab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE))
					.addGap(310))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LNslu)
								.addComponent(TNKart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LDatap)
								.addComponent(SDataPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LDataMes)
								.addComponent(SDataM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LYavka)
								.addComponent(SYavka, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LKolp)
								.addComponent(SKolBer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(LKolRod)
									.addGap(12))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(SParRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(LKolAb)
								.addGroup(gl_panel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(SKolAb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(LDataAb)
									.addComponent(TDataab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_4))
								.addComponent(SSrokA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(CBOslAb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(LOslAb))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LVozMen)
								.addComponent(SVozMen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(5)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(1)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LProdMen)
								.addComponent(SMenC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LpolJ)
								.addComponent(SPolJ, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LKolDet)
								.addComponent(SKolDet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(CBKontr)
							.addGap(9)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LDataOsl)
								.addComponent(SDataOsl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel)
								.addComponent(SDataSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_1)
								.addComponent(TSSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_2)
								.addComponent(TNSert, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(50)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LPlanRod)
								.addComponent(CBRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(LDataPlRod, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
									.addGap(16))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(SDataRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)))
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(LPrish)
								.addComponent(CBPrishSn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LDataSn, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
								.addComponent(SDataSn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(14)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(21, Short.MAX_VALUE))
		);
		
		JLabel lblNewLabel_3 = new JLabel("C.Diag");
		
		JLabel lblCvera = new JLabel("C.vera");
		
		JSpinner SCDiag = new JSpinner();
		
		JSpinner SCvera = new JSpinner();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(18)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_panel_1.createSequentialGroup()
								.addComponent(LVes)
								.addGap(10))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addComponent(LRost)
								.addGap(94))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addComponent(LTaz)
								.addGap(10)
								.addComponent(lblDsp)
								.addGap(29))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addComponent(lblDcr)
								.addGap(29))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addComponent(lblDtroch)
								.addGap(25))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addComponent(lblCext)
								.addGap(25))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addComponent(LIndSol)
								.addGap(22)))
						.addComponent(lblNewLabel_3)
						.addComponent(lblCvera))
					.addGap(20)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(SRost, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE)
							.addGroup(Alignment.LEADING, gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(SVes, Alignment.LEADING)
								.addComponent(SDsp, Alignment.LEADING)
								.addComponent(SDcr, Alignment.LEADING)
								.addComponent(SDtroch, Alignment.LEADING)
								.addComponent(SCext, Alignment.LEADING)
								.addComponent(SindSol, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)))
						.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(SCDiag, Alignment.LEADING)
							.addComponent(SCvera, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)))
					.addContainerGap(33, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(LRost)
						.addComponent(SRost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(8)
							.addComponent(LVes))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(SVes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(2)
							.addComponent(LTaz))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(2)
							.addComponent(lblDsp))
						.addComponent(SDsp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(14)
							.addComponent(lblDcr))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(6)
							.addComponent(SDcr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(10)
							.addComponent(lblDtroch))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(SDtroch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(14)
							.addComponent(lblCext))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(6)
							.addComponent(SCext, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(LIndSol)
						.addComponent(SindSol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_3)
						.addComponent(SCDiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblCvera)
						.addComponent(SCvera, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void setDefaultValues() {
		// TODO Auto-generated method stub
	try {
		rdsl.setNpasp(Vvod.zapVr.npasp);
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
//		rdsl.setRost(160);
		rdsl.setVesd(60);
		rdsl.setOslab("");
		rdsl.setDataM(System.currentTimeMillis());
		rdsl.setDatay(System.currentTimeMillis());
		rdsl.setDataosl(System.currentTimeMillis());
		rdsl.setDatasn(System.currentTimeMillis());
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		JOptionPane.showMessageDialog(FormPostBer.this, e.getLocalizedMessage(), "Ошибка создания записи", JOptionPane.ERROR_MESSAGE);
		
	}
	
	}

	private void setPostBerData(RdSlStruct rdsl) {
		//SRost.setValue(rdsl.getRost());
		SVes.setValue(rdsl.getVesd());
		SDcp.setValue(rdsl.getDsp());
		SDcr.setValue(rdsl.getDsr());
		SDtroch.setValue(rdsl.getDTroch());
		SCext.setValue(rdsl.getCext());
		SindSol.setValue(rdsl.getIndsol());
		SDataPos.setDate(rdsl.getDatay());
		SDataSn.setDate(rdsl.getDatasn());
		SDataRod.setDate(rdsl.getDataZs());
		SDataSert.setDate(rdsl.getDatasert());
		TSSert.setText(rdsl.ssert);
		TNSert.setText(rdsl.nsert);
		SParRod.setValue(rdsl.getKolrod());
		SKolBer.setValue(rdsl.getShet());
		TDataab.setDate(rdsl.getDataab());
  	    SDataRod.setDate(rdsl.getDataZs());
		SDataSert.setDate(rdsl.getDatasert());
		TSSert.setText(rdsl.ssert);
		TNSert.setText(rdsl.nsert);
		SParRod.setValue(rdsl.getKolrod());
		SKolBer.setValue(rdsl.getShet());
		SDataOsl.setDate(rdsl.getDataosl());
		SYavka.setValue(rdsl.getYavka1());
		SDataM.setDate(rdsl.getDataM());
		SKolAb.setValue(rdsl.getAbort());
		SVozMen.setValue(rdsl.getVozmen());
		SMenC.setValue(rdsl.getPrmen());
		SKolDet.setValue(rdsl.getDeti());
		SPolJ.setValue(rdsl.getPolj());
		SSrokA.setValue(rdsl.getSrokab());
}
	private String getTextOrNull(String str) {
		if (str != null)
			if (str.length() > 0)
				return str;
		
		return null;
	}
	private void method2() throws KmiacServerException{
		try {
			rdsl = MainForm.tcl.getRdSlInfo(Vvod.zapVr.id_pvizit, Vvod.zapVr.npasp);
if (rdsl.vesd == 0)
			setDefaultValues();
oslrod =rdsl.getOslrod();
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
		}catch (KmiacServerException e) {
	e.printStackTrace();
} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			MainForm.conMan.reconnect(e);
		} 
	}
	
	public void onConnect() {
		try {
			
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
