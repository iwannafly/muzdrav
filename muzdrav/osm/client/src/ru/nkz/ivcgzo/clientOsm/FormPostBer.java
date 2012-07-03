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
import java.util.Date;
import java.util.Calendar;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;

import javax.swing.JOptionPane;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
//import ru.nkz.ivcgzo.thriftOsm.PsignNotFoundException;
//import ru.nkz.ivcgzo.;
import ru.nkz.ivcgzo.thriftOsm.RdSlStruct;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;

import org.apache.thrift.TException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class FormPostBer extends JFrame {

	private JPanel contentPane;
	private JTextField TNKart;
    private RdSlStruct rdsl;
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
    JSpinner SYavka;
    JSpinner SKolAb;
    JSpinner SVozMen;
    JSpinner SMenC;
    JSpinner SKolDet;
    JSpinner SPolJ;
    JComboBox CBPrishSn;
    JComboBox CBRod;
    JComboBox CBOslAb;
    JCheckBox CBKrov; 
    JCheckBox CBEkl; 
    JCheckBox CBGnoin; 
    JCheckBox CBTromb; 
    JCheckBox CBKesar; 
    JCheckBox CBAkush; 
    JCheckBox CBIiiiv; 
    JCheckBox CBRazrProm; 
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
		
		rdsl = new RdSlStruct();
		if (rdsl.vesd == 0)
			setDefaultValues(rdsl);
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
//		rdsl.setOslAb("");
		oslcode = rdsl.getOslAb();
		if (oslcode.equals("N70")){oslname = "Сальпингит и оофорит";}
		if (oslcode.equals("N71")){oslname = "Воспалительные болезни матки";}
		if (oslcode.equals("N72")){oslname = "Воспалительные болезни шейки матки";}
		if (oslcode.equals("N76")){oslname = "Другие воспалительные болезни влагалища и вульвы";}
		if (oslcode==null){oslname = "";}
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
//			rdsl.getAbort(SKolAb.value);
	//	SKolAb.get(rdsl.abort);
			}
		});
		setTitle("Постановка на учет по беременности");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(20)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(55)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 617, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel LNslu = new JLabel("Номер обменной карты");
		
		JLabel LDatap = new JLabel("Дата первого посещения");
		
		JLabel LKolp = new JLabel("Количество беременностей");
		
		JLabel LKolAb = new JLabel("Количество абортов");
		
		JLabel LOslAb = new JLabel("Осложнение после предыдущего аборта");
		
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
		CBKontr.setSelected(rdsl.kont == 1);
		
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
		rdsl.setRost(150);
		SRost.setModel(new SpinnerNumberModel(rdsl.rost, 140, 200, 1));
		/*150 должно быть заменено на 140, т.к.
		 * */
//		rdsl.setAbort((int) SRost.getModel().getValue());
		
		final JSpinner SVes = new JSpinner();
		SVes.setModel(new SpinnerNumberModel(rdsl.vesd, 40,250,1));
		rdsl.setVesd((int) SVes.getModel().getValue());
		
		final JSpinner SDsp = new JSpinner();
		SDsp.setModel(new SpinnerNumberModel(rdsl.dsp, 24, 27,1));
		rdsl.setDsp((int) SDsp.getModel().getValue());
		
		final JSpinner SDcr = new JSpinner();
		SDcr.setModel(new SpinnerNumberModel(rdsl.dsr, 27, 30, 1));
		rdsl.setDsr((int) SDsp.getModel().getValue());
		
		final JSpinner SDtroch = new JSpinner();
		SDtroch.setModel(new SpinnerNumberModel(rdsl.dTroch,30,33,1));
		rdsl.setDTroch((int) SDtroch.getModel().getValue());
		
		final JSpinner SCext = new JSpinner();
		SCext.setModel(new SpinnerNumberModel(rdsl.cext,25, 35, 1));
		rdsl.setCext((int) SCext.getModel().getValue());
		
		final JSpinner SindSol = new JSpinner();
		SindSol.setModel(new SpinnerNumberModel(rdsl.indSol, 13, 20,1));
		rdsl.setIndSol((int) SindSol.getModel().getValue());
		
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
		
//		final JSpinner SDataPos = new JSpinner();
//		SDataPos.setModel(new SpinnerDateModel(new Date(rdsl.datay),new Date(), new Date(), Calendar.DAY_OF_YEAR));
//		rdsl.setDatay((long) SDataPos.getModel().getValue());
		SDataPos = new CustomDateEditor();
		
		final JSpinner SParRod = new JSpinner();
		SParRod.setModel(new SpinnerNumberModel(rdsl.kolRod, 0, 0, 1));
		rdsl.setKolRod((int) SParRod.getModel().getValue());
		
		final JSpinner SKolBer = new JSpinner();
		SKolBer.setModel(new SpinnerNumberModel(rdsl.kolpr, 0, 0, 1));
		rdsl.setKolpr((int) SKolBer.getModel().getValue());
		
//		final JSpinner SDataOsl = new JSpinner();
//		SDataOsl.setModel(new SpinnerDateModel(new Date(rdsl.dataosl), null, System.currentTimeMillis(), Calendar.DAY_OF_YEAR));
//		rdsl.setDataosl((long) SDataOsl.getModel().getValue());
		SDataOsl = new CustomDateEditor();
		
		final JSpinner SYavka = new JSpinner();
		SYavka.setModel(new SpinnerNumberModel(rdsl.yavka1,2, 40,1));
		rdsl.setYavka1((int) SYavka.getModel().getValue());
		
//		final JSpinner SDataM = new JSpinner();
//		SDataM.setModel(new SpinnerDateModel(new Date(rdsl.dataM), null, System.currentTimeMillis(), Calendar.DAY_OF_YEAR));
//		rdsl.setDataM((long) SDataM.getModel().getValue());
		SDataM = new CustomDateEditor();
		
//		final JSpinner SDataRod = new JSpinner();
//		SDataRod.setModel(new SpinnerDateModel(new Date(rdsl.datay+(40-rdsl.yavka1)*7), System.currentTimeMillis(),(System.currentTimeMillis()+280), Calendar.DAY_OF_YEAR));
		
		final JSpinner SKolAb = new JSpinner();
		SKolAb.setModel(new SpinnerNumberModel(rdsl.abort, 0, 50, 1));
		rdsl.setAbort((int) SKolAb.getModel().getValue());
		
		final JSpinner SVozMen = new JSpinner();
		SVozMen.setModel(new SpinnerNumberModel(rdsl.VozMen , 8, 30, 1));
		rdsl.setLet((int) SVozMen.getModel().getValue());
		
		final JSpinner SMenC = new JSpinner();
		SMenC.setModel(new SpinnerNumberModel(rdsl.prmen, 20,  60, 1));
		rdsl.setPrmen((int) SMenC.getModel().getValue());
		
		final JSpinner SKolDet = new JSpinner();
		SKolDet.setModel(new SpinnerNumberModel(rdsl.deti, 0, 20, 1));
		rdsl.setDeti((int) SKolDet.getModel().getValue());
		
		final JSpinner SPolJ = new JSpinner();
		SPolJ.setModel(new SpinnerNumberModel(rdsl.polj, 9, 40, 1));
		rdsl.setPolj((int) SPolJ.getModel().getValue());
		
//		final JSpinner SDataSn = new JSpinner();
//		SDataSn.setBackground(new Color(212, 208, 200));
//		SDataSn.setModel(new SpinnerDateModel(new Date(rdsl.Datasn), System.currentTimeMillis(),(System.currentTimeMillis()+280), Calendar.DAY_OF_YEAR));
//		rdsl.setDatasn((long) SDataSn.getModel().getValue());
		SDataSn = new CustomDateEditor();
		
		JComboBox CBPrishSn = new JComboBox();
		CBPrishSn.setModel(new DefaultComboBoxModel(new String[] {" ", "Срочные роды", "Мед. аборт", "Выкидыш", "Выбыла"}));
		
		final JComboBox CBRod = new JComboBox();
		CBRod.setModel(new DefaultComboBoxModel(new String[] {"", "Естественные роды", "Кесарево сечение"}));
		CBRod.setSelectedItem(rdsl.getPlrod()); 
//		rdsl.setPlrod(CBRod.getSelectedItem().toString());
		
		final JComboBox CBOslAb = new JComboBox();
		CBOslAb.setModel(new DefaultComboBoxModel(new String[] {"", "Сальпингит и оофорит", "Воспалительные болезни матки", "Воспалительные болезни шейки матки", "Другие воспалительные болезни влагалища и вульвы " }));
		CBOslAb.setSelectedItem(oslname);
		
		JButton ButSave = new JButton("Сохранить");
		ButSave.addActionListener(new ActionListener() {
			private AbstractButton sVozMen;
            private int getoslrod(int oslrod){
            	oslrod=0;
            if (CBKrov.isSelected()){oslrod=oslrod+1;}
            if (CBEkl.isSelected()){oslrod=oslrod+2;}
            if (CBGnoin.isSelected()){oslrod=oslrod+4;}
            if (CBTromb.isSelected()){oslrod=oslrod+8;}
            if (CDKesar.isSelected()){oslrod=oslrod+16;}
            if (CBAkush.isSelected()){oslrod=oslrod+32;}
            if (CBIiiiv.isSelected()){oslrod=oslrod+64;}
            if (CBRazrProm.isSelected()){oslrod=oslrod+128;}
			return oslrod;	
            };
			private int kontrac(int iw3){
				if (CBKontr.isSelected()){iw3 = 1;}
				return iw3;
			};
			private String oslcode(String oslcode){
				if (CBOslAb.getSelectedItem().equals("Сальпингит и оофорит")){oslcode = "N70";}
				if (CBOslAb.getSelectedItem().equals("Воспалительные болезни матки")){oslcode = "N71";}
				if (CBOslAb.getSelectedItem().equals("Воспалительные болезни шейки матки")){oslcode = "N72";}
				if (CBOslAb.getSelectedItem().equals("Другие воспалительные болезни влагалища и вульвы")){oslcode = "N76";}
				if (CBOslAb.getSelectedItem().equals("")){oslcode = null;}
				return oslcode;
			};
			public void actionPerformed(ActionEvent arg0) {
			rdsl.setAbort((int) SKolAb.getModel().getValue());
			rdsl.setCext((int) SCext.getModel().getValue());
			rdsl.setDataM( SDataM.getDate().getTime());
			rdsl.setDataosl( SDataOsl.getDate().getTime());
			rdsl.setDatasn( SDataSn.getDate().getTime());
			rdsl.setDatay(SDataPos.getDate().getTime());
			rdsl.setKont(iw3);
			rdsl.setDeti((int) SKolDet.getModel().getValue());
			rdsl.setDsp((int) SDsp.getModel().getValue());
			rdsl.setDsr((int) SDcr.getModel().getValue());
			rdsl.setDTroch((int) SDtroch.getModel().getValue());
			rdsl.setIndSol((int) SindSol.getModel().getValue());
			rdsl.setKolpr((int) SKolBer.getModel().getValue());
			rdsl.setKolRod((int) SParRod.getModel().getValue());
			rdsl.setPolj((int) SPolJ.getModel().getValue());
			rdsl.setPrmen((int) SMenC.getModel().getValue());
			rdsl.setRost((int) SRost.getModel().getValue());
			rdsl.setVesd((int) SVes.getModel().getValue());
			rdsl.setYavka1((int) SYavka.getModel().getValue());
			rdsl.setOslrod(oslrod);
			rdsl.setOslAb(oslcode);
			
			}
		});
		
		JButton btnNewButton = new JButton("Постановка на учет");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					RdSlStruct rdsl = new RdSlStruct();
					setDefaultValues(rdsl);
					MainForm.tcl.AddRdSl(rdsl);
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
//		inform = new FormRdInf;
		JButton button = new JButton("Дополнительная информация");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				inform.setVisible(true);
			}
		});
		

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(LNslu)
								.addComponent(LDatap)
								.addComponent(LYavka)
								.addComponent(LDataMes)
								.addComponent(LKolAb)
								.addComponent(LKolp)
								.addComponent(LKolRod))
							.addGap(9)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(SKolBer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(SDataM, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(SDataPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
							/*		.addComponent(tdatapos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)*/)
								.addComponent(TNKart, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_panel.createSequentialGroup()
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(SKolAb, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
									.addComponent(SParRod, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(SYavka, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)))
						.addComponent(LPrish)
						.addComponent(LOslAb)
						.addComponent(CBOslAb, GroupLayout.PREFERRED_SIZE, 334, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(LProdMen)
								.addComponent(LKolDet)
								.addComponent(LVozMen)
								.addComponent(LDataOsl)
								.addComponent(LpolJ)
								.addComponent(CBKontr)
								.addComponent(LPlanRod)
								.addComponent(LDataPlRod)
								.addComponent(LDataSn))
							.addGap(18)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(CBPrishSn, Alignment.TRAILING, 0, 136, Short.MAX_VALUE)
								.addComponent(SDataSn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(CBRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(SDataOsl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(SKolDet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(SDataRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(SPolJ, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(SMenC, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(SVozMen, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)))))
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE)
						.addComponent(ButSave)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(btnNewButton)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button)))
					.addGap(139))
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
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(LYavka)
										.addComponent(SYavka, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(LKolp)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(LKolRod)
									.addGap(12))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(SKolBer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(SParRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LKolAb)
								.addComponent(SKolAb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(LOslAb))
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(6)
							.addComponent(CBOslAb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LDataOsl)
								.addComponent(SDataOsl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LKolDet)
								.addComponent(SKolDet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LVozMen)
								.addComponent(SVozMen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LProdMen)
								.addComponent(SMenC, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LpolJ)
								.addComponent(SPolJ, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(8)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(CBKontr)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(LPlanRod)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(LDataPlRod, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED))
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(CBRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(SDataRod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(16)
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(LPrish)
								.addComponent(CBPrishSn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(14)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(btnNewButton)
								.addComponent(button))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(LDataSn, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
						.addComponent(SDataSn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(ButSave))
					.addContainerGap(123, Short.MAX_VALUE))
		);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(10)
							.addComponent(LIndSol)
							.addGap(12)
							.addComponent(SindSol))
						.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
							.addGap(37)
							.addComponent(lblCext)
							.addGap(53)
							.addComponent(SCext))
						.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
							.addGap(22)
							.addComponent(lblDtroch)
							.addGap(53)
							.addComponent(SDtroch))
						.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
							.addGap(39)
							.addComponent(lblDcr)
							.addGap(57)
							.addComponent(SDcr))
						.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
							.addGap(10)
							.addComponent(LTaz)
							.addGap(10)
							.addComponent(lblDsp)
							.addGap(57)
							.addComponent(SDsp))
						.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
							.addComponent(LVes)
							.addGap(10)
							.addComponent(SVes))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(10)
							.addComponent(LRost)
							.addGap(84)
							.addComponent(SRost, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)))
					.addGap(534))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(13)
							.addComponent(LRost))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addComponent(SRost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
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
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(8)
							.addComponent(lblDcr))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(SDcr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(10)
							.addComponent(lblDtroch))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(SDtroch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(8)
							.addComponent(lblCext))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(SCext, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(8)
							.addComponent(LIndSol))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(SindSol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void setDefaultValues(RdSlStruct rdsl2) {
		// TODO Auto-generated method stub
	if (rdsl.cext == 0) rdsl.setCext(25);
	if (rdsl.dsp == 0) rdsl.setDsp(25);
	if (rdsl.dsr == 0) rdsl.setDsr(28);
	if (rdsl.dTroch == 0) rdsl.setDTroch(31);
	if (rdsl.indSol == 0) rdsl.setIndSol(15);
	if (rdsl.kolRod == 0) rdsl.setKolRod(0);
	if (rdsl.kolpr == 0){ rdsl.setKolpr(0);} 
	else {rdsl.setKolpr(rdsl.kolpr+1);}
	if (rdsl.abort == 0) rdsl.setAbort(0);
	if (rdsl.deti == 0) rdsl.setDeti(0);
	if (rdsl.yavka1 == 0) rdsl.setYavka1(4);
	if (rdsl.VozMen == 0) rdsl.setVozMen(11);
	if (rdsl.prmen == 0) rdsl.setPrmen(28);
	if (rdsl.polj == 0) rdsl.setPolj(18);
	if (rdsl.rost == 0) rdsl.setRost(160);
	if (rdsl.vesd == 0) rdsl.setVesd(60);
	    rdsl.setOslAb("");
		rdsl.setDataM(System.currentTimeMillis());
		rdsl.setDatay(System.currentTimeMillis());
		rdsl.setDataosl(System.currentTimeMillis());
		rdsl.setDatasn(System.currentTimeMillis());
	
	}

	private void setPostBerData(RdSlStruct rdsl) {
		SRost.setValue(rdsl.getRost());
		SVes.setValue(rdsl.getVesd());
		SDcp.setValue(rdsl.getDsp());
		SDcr.setValue(rdsl.getDsr());
		SDtroch.setValue(rdsl.getDTroch());
		SCext.setValue(rdsl.getCext());
		SindSol.setValue(rdsl.getIndSol());
		SDataPos.setDate(rdsl.getDatay());
		SDataSn.setDate(rdsl.getDatasn());
//		SDataRod.setDate(rdsl.getDataRod());
		SParRod.setValue(rdsl.getKolRod());
		SKolBer.setValue(rdsl.getKolpr());
		SDataOsl.setDate(rdsl.getDataosl());
		SYavka.setValue(rdsl.getYavka1());
		SDataM.setDate(rdsl.getDataM());
		SKolAb.setValue(rdsl.getAbort());
		SVozMen.setValue(rdsl.getVozMen());
		SMenC.setValue(rdsl.getPrmen());
		SKolDet.setValue(rdsl.getDeti());
		SPolJ.setValue(rdsl.getPolj());
	}
}
