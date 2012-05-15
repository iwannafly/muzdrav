package ru.nkz.ivcgzo.clientOsm;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.thriftOsm.PdiagAmb;
import ru.nkz.ivcgzo.thriftOsm.PdiagAmbNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.PdiagZ;
import ru.nkz.ivcgzo.thriftOsm.Priem;
import ru.nkz.ivcgzo.thriftOsm.Pvizit;
import ru.nkz.ivcgzo.thriftOsm.PvizitAmb;
import ru.nkz.ivcgzo.thriftOsm.ZapVr;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextPane;
import java.awt.Component;
import javax.swing.JTable;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.UIManager;
import java.awt.Color;


public class Vvod extends JFrame {

	private static final long serialVersionUID = 1L;
	public static ZapVr zapVrSave;
	private FormSign sign;
	private SettingsOsm settingsosm;
	private FormPostBer postber;
	private Pvizit pvizit;
	private PvizitAmb pos;
	private PdiagAmb diag;
	private Priem pr;
	private PdiagZ dz;
	private JTextField tfdiag;
	private JTextField tfnamed;
	private JTextField tftemp;
	private JTextField tfad;
	private JTextField tfrost;
	private JTextField tfves;
	private JTextField tfchss;
	private JTextField tfkodmkb;
	private JTextField tfname;
	private JTextPane tpzakl;
	private JTextPane tprecom;
	private JTextPane tpanamn;
	private JTextPane tppalp;
	private JTextPane tpperk;
	private JTextPane tposm;
	private JTextPane tpaus;
	private JTextPane tpLocalis;
	private JTextPane tpJalob;
	private JTextPane tpJalobd;
	private JTextPane tpJalobkrov;
	private JTextPane tpJalobp;
	private JTextPane tpJalobmoch;
	private JTextPane tpJalobendo;
	private JTextPane tpJalobnerv;
	private JTextPane tpJalobopor;
	private JTextPane tpJalobloh;
	private JTextPane tpJalobobh;
	private JTextPane tpJalobproch;
	private JTextPane tpNaczab;
	private JTextPane tpSympt;
	private JTextPane tpOtnbol;
	private JTextPane tpPssyt;
	private JTextPane tpObsost;
	private JTextPane tpKoj;
	private JTextPane tpSliz;
	private JTextPane tpPodkkl;
	private JTextPane tpLimf;
	private JTextPane tpKostmysh;
	private JTextPane tpNervnps;
	private JTextPane tpTelo;
	private JTextPane tpSust;
	private JTextPane tpDyh;
	private JTextPane tpGrkl;
	private JTextPane tpPerkl;
	private JTextPane tpAusl;
	private JTextPane tpBronho;
	private JTextPane tpArter;
	private JTextPane tpObls;
	private JTextPane tpPerks;
	private JTextPane tpAuss;
	private JTextPane tpPolrta;
	private JTextPane tpJivot;
	private JTextPane tpPalpjivot;
	private JTextPane tpJelkish;
	private JTextPane tpPalpjel;
	private JTextPane tpPalppodjel;
	private JTextPane tpPechen;
	private JTextPane tpJech;
	private JTextPane tpSelez;
	private JTextPane tpOblzad;
	private JTextPane tpPoyasn;
	private JTextPane tpPochki;
	private JTextPane tpMoch;
	private JTextPane tpMoljel;
	private JTextPane tpGrjel;
	private JTextPane tpMatka;
	private JTextPane tpNarpolov;
	private JTextPane tpChitov;
	private JTextPane tpOcenka;
	private CustomTable<PdiagAmb, PdiagAmb._Fields> tableDiag;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> c_obr;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cbrez;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cbish;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> vid_opl;
	private CustomTable<PvizitAmb, PvizitAmb._Fields> TabPos;
	
	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the frame.
	 */
	public Vvod() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowActivated(WindowEvent arg0) {

			}
		});
		 pvizit = new Pvizit();
		 sign = new FormSign();
		 postber = new FormPostBer();
		 settingsosm = new SettingsOsm();
		 pos = new PvizitAmb();
		 diag = new PdiagAmb();
		 pr = new Priem();
		 dz = new PdiagZ();
		setBounds(100, 100, 1029, 747);
		//JPanel JPanel = new JPanel();
		setBorder(new EmptyBorder(5, 5, 5, 5));
		//setContentPane();
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(getContentPane());
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
		);
		
		JPanel panel = new JPanel();
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollPane.setRowHeaderView(panel);
		
		JButton btnAnamz = new JButton("Анамнез жизни");
		btnAnamz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			sign.setVisible(true);}
		});
				
		JButton button = new JButton("Аллергоанамнез");
		
		JButton button_1 = new JButton("Сохранить");
		button_1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				diag.setDiag(tfkodmkb.getText());
				diag.setNamed(tfname.getText());
				pr.setT_ad(tfad.getText());
				pr.setT_chss(tfchss.getText());
				pr.setT_rost(tfrost.getText());
				pr.setT_ves(tfves.getText());
				pr.setT_st_localis(tpLocalis.getText());
				pr.setT_jalob_d(tpJalobd.getText());
				pr.setT_jalob_krov(tpJalobkrov.getText());
				pr.setT_jalob_p(tpJalobp.getText());
				pr.setT_jalob_moch(tpJalobmoch.getText());
				pr.setT_jalob_endo(tpJalobendo.getText());
				pr.setT_jalob_nerv(tpJalobnerv.getText());
				pr.setT_jalob_opor(tpJalobopor.getText());
				pr.setT_jalob_lih(tpJalobloh.getText());
				pr.setT_jalob_obh(tpJalobobh.getText());
				pr.setT_jalob_proch(tpJalobproch.getText());
				pr.setT_nachalo_zab(tpNaczab.getText());
				pr.setT_sympt(tpSympt.getText());
				pr.setT_otn_bol(tpOtnbol.getText());
				pr.setT_ps_syt(tpPssyt.getText());
				pr.setT_ob_sost(tpObsost.getText());
				pr.setT_koj_pokr(tpKoj.getText());
				pr.setT_sliz(tpSliz.getText());
				pr.setT_podk_kl(tpPodkkl.getText());
				pr.setT_limf_uzl(tpLimf.getText());
				pr.setT_kost_mysh(tpKostmysh.getText());
				pr.setT_nervn_ps(tpNervnps.getText());
				pr.setT_telo(tpTelo.getText());
				pr.setT_sust(tpSust.getText());
				pr.setT_dyh(tpDyh.getText());
				pr.setT_gr_kl(tpGrkl.getText());
				pr.setT_perk_l(tpPerkl.getText());
				pr.setT_aus_l(tpAusl.getText());
				pr.setT_bronho(tpBronho.getText());
				pr.setT_arter(tpArter.getText());
				pr.setT_obl_s(tpObls.getText());
				pr.setT_perk_s(tpPerks.getText());
				pr.setT_aus_s(tpAuss.getText());
				pr.setT_pol_rta(tpPolrta.getText());
				pr.setT_jivot(tpJivot.getText());
				pr.setT_palp_jivot(tpPalpjivot.getText());
				pr.setT_jel_kish(tpJelkish.getText());
				pr.setT_palp_jel(tpPalpjel.getText());
				pr.setT_palp_podjel(tpPalppodjel.getText());
				pr.setT_pechen(tpPechen.getText());
				pr.setT_jelch(tpJech.getText());
				pr.setT_selez(tpSelez.getText());
				pr.setT_obl_zad(tpOblzad.getText());
				pr.setT_poyasn(tpPoyasn.getText());
				pr.setT_pochk(tpPochki.getText());
				pr.setT_moch(tpMoch.getText());
				pr.setT_mol_jel(tpMoljel.getText());
				pr.setT_gr_jel(tpGrjel.getText());
				pr.setT_matka(tpMatka.getText());
				pr.setT_nar_polov(tpNarpolov.getText());
				pr.setT_chitov(tpChitov.getText());
				pr.setT_ocenka(tpOcenka.getText());
			 	
				//pr.setOsmotr("<Жалобы> "+tpJalob.getText()+" <Анамнез заболевания> "+tpanamn.getText()+" <StatusPraesense> Темп. "+tftemp.getText()+"Чсс "+tfchss.getText()+"<StatusPraesense> АД "+tfad.getText()+"Рост "+tfrost.getText()+"Вес "+tfves.getText()+" <Физикальное обсл.> "+tposm.getText()+" "+tpaus.getText()+" "+tppalp.getText()+" "+tpperk.getText());
//				pr.setOsmotr("<Жалобы> "+tpJalob.getText()+" <Анамнез заболевания> "+tpanamn.getText()+" <StatusPraesense> Темп. "+tftemp.getText()+"Чсс "+tfchss.getText()+"<StatusPraesense> АД "+tfad.getText()+"Рост "+tfrost.getText()+"Вес "+tfves.getText()+" <Физикальное обсл.> "+tposm.getText()+" "+tpaus.getText()+" "+tppalp.getText()+" "+tpperk.getText());

			}
		});
		
		JButton button_2 = new JButton("Сохранить как шаблон");
		
		JButton button_3 = new JButton("Загрузить из шаблона");
		
		JButton button_4 = new JButton("Печатные формы");
		
		JPanel panel_Talon = new JPanel();
				panel_Talon.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0422\u0430\u043B\u043E\u043D \u0430\u043C\u0431.\u043F\u0430\u0446\u0438\u0435\u043D\u0442\u0430", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JLabel label_14 = new JLabel("Диагнозы");
		
		JCheckBox checkBox_7 = new JCheckBox("Скрыть/раскрыть");
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JButton button_5 = new JButton("Удалить");
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		
		JPanel Jalob = new JPanel();
		tabbedPane.addTab("Жалобы", null, Jalob, null);
		
		JPanel panel_jalob = new JPanel();
		
		JTextPane tpJalob = new JTextPane();
		GroupLayout gl_panel_jalob = new GroupLayout(panel_jalob);
		gl_panel_jalob.setHorizontalGroup(
			gl_panel_jalob.createParallelGroup(Alignment.LEADING)
				.addComponent(tpJalob, GroupLayout.DEFAULT_SIZE, 845, Short.MAX_VALUE)
		);
		gl_panel_jalob.setVerticalGroup(
			gl_panel_jalob.createParallelGroup(Alignment.LEADING)
				.addComponent(tpJalob, GroupLayout.DEFAULT_SIZE, 218, Short.MAX_VALUE)
		);
		panel_jalob.setLayout(gl_panel_jalob);
		
		JLabel lbljalob = new JLabel("Жалобы на:");
		GroupLayout gl_Jalob = new GroupLayout(Jalob);
		gl_Jalob.setHorizontalGroup(
			gl_Jalob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Jalob.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_Jalob.createParallelGroup(Alignment.LEADING)
						.addComponent(lbljalob)
						.addComponent(panel_jalob, GroupLayout.PREFERRED_SIZE, 663, GroupLayout.PREFERRED_SIZE)))
		);
		gl_Jalob.setVerticalGroup(
			gl_Jalob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Jalob.createSequentialGroup()
					.addGap(11)
					.addComponent(lbljalob)
					.addGap(7)
					.addComponent(panel_jalob, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		Jalob.setLayout(gl_Jalob);
		
		JPanel pAnanmnesis = new JPanel();
		tabbedPane.addTab("Anamnesis Morbi", null, pAnanmnesis, null);
		
		JPanel panel_anamn = new JPanel();
		panel_anamn.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JTextPane tpanamn = new JTextPane();
		
		JLabel lblanamn = new JLabel("Анамнез заболевания");
		GroupLayout gl_panel_anamn = new GroupLayout(panel_anamn);
		gl_panel_anamn.setHorizontalGroup(
			gl_panel_anamn.createParallelGroup(Alignment.LEADING)
				.addComponent(tpanamn, GroupLayout.DEFAULT_SIZE, 827, Short.MAX_VALUE)
		);
		gl_panel_anamn.setVerticalGroup(
			gl_panel_anamn.createParallelGroup(Alignment.LEADING)
				.addComponent(tpanamn, GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
		);
		panel_anamn.setLayout(gl_panel_anamn);
		GroupLayout gl_pAnanmnesis = new GroupLayout(pAnanmnesis);
		gl_pAnanmnesis.setHorizontalGroup(
			gl_pAnanmnesis.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pAnanmnesis.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_pAnanmnesis.createParallelGroup(Alignment.LEADING)
						.addComponent(lblanamn, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_anamn, GroupLayout.PREFERRED_SIZE, 663, GroupLayout.PREFERRED_SIZE)))
		);
		gl_pAnanmnesis.setVerticalGroup(
			gl_pAnanmnesis.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pAnanmnesis.createSequentialGroup()
					.addGap(11)
					.addComponent(lblanamn)
					.addGap(6)
					.addComponent(panel_anamn, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
		);
		pAnanmnesis.setLayout(gl_pAnanmnesis);
		
		JPanel pStatuspr = new JPanel();
		tabbedPane.addTab("Status Praesense", null, pStatuspr, null);
		
		JPanel panel_1 = new JPanel();
		
		JLabel lbltemp = new JLabel("Температура");
		
		tftemp = new JTextField();
		tftemp.setColumns(10);
		
		JLabel lblad = new JLabel("АД");
		
		tfad = new JTextField();
		tfad.setColumns(10);
		
		JLabel lblrost = new JLabel("Рост");
		
		tfrost = new JTextField();
		tfrost.setColumns(10);
		
		JLabel lblves = new JLabel("Вес");
		
		tfves = new JTextField();
		tfves.setColumns(10);
		
		JLabel lblchss = new JLabel("ЧСС");
		
		tfchss = new JTextField();
		tfchss.setColumns(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lbltemp)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tftemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblad)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tfad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblrost)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tfrost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblves, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfves, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblchss)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tfchss, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(92, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(tftemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbltemp)
						.addComponent(lblad)
						.addComponent(tfad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblrost)
						.addComponent(tfrost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblves)
						.addComponent(tfves, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblchss)
						.addComponent(tfchss, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(38, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		GroupLayout gl_pStatuspr = new GroupLayout(pStatuspr);
		gl_pStatuspr.setHorizontalGroup(
			gl_pStatuspr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pStatuspr.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 1819, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pStatuspr.setVerticalGroup(
			gl_pStatuspr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pStatuspr.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(139, Short.MAX_VALUE))
		);
		pStatuspr.setLayout(gl_pStatuspr);
		
		JPanel pfizikob = new JPanel();
		tabbedPane.addTab("Физикальное обследование", null, pfizikob, null);
		
		JLabel lblObosm = new JLabel("Общий осмотр");
		
		JTextPane tpObOsm = new JTextPane();
		
		JLabel lblPalp = new JLabel("Пальпация");
		
		JTextPane tpPalp = new JTextPane();
		
		JLabel lblPerk = new JLabel("Перкуссия");
		
		JTextPane tpPerk = new JTextPane();
		
		JLabel lblAus = new JLabel("Аускультация");
		
		JTextPane tpAus = new JTextPane();
		GroupLayout gl_pfizikob = new GroupLayout(pfizikob);
		gl_pfizikob.setHorizontalGroup(
			gl_pfizikob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pfizikob.createSequentialGroup()
					.addGroup(gl_pfizikob.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pfizikob.createSequentialGroup()
							.addGap(20)
							.addGroup(gl_pfizikob.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPerk)
								.addComponent(lblPalp)
								.addComponent(lblAus)))
						.addGroup(gl_pfizikob.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblObosm)))
					.addGap(6)
					.addGroup(gl_pfizikob.createParallelGroup(Alignment.LEADING)
						.addComponent(tpPerk, GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
						.addComponent(tpObOsm, GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
						.addComponent(tpAus, GroupLayout.DEFAULT_SIZE, 721, Short.MAX_VALUE)
						.addComponent(tpPalp, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 733, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_pfizikob.setVerticalGroup(
			gl_pfizikob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pfizikob.createSequentialGroup()
					.addGroup(gl_pfizikob.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pfizikob.createSequentialGroup()
							.addContainerGap()
							.addComponent(tpObOsm, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pfizikob.createSequentialGroup()
							.addGap(35)
							.addComponent(lblObosm)))
					.addGroup(gl_pfizikob.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pfizikob.createSequentialGroup()
							.addGap(45)
							.addComponent(lblPalp))
						.addGroup(gl_pfizikob.createSequentialGroup()
							.addGap(18)
							.addComponent(tpPalp, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pfizikob.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pfizikob.createSequentialGroup()
							.addGap(27)
							.addComponent(lblPerk))
						.addGroup(gl_pfizikob.createSequentialGroup()
							.addGap(9)
							.addComponent(tpPerk, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_pfizikob.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pfizikob.createSequentialGroup()
							.addGap(32)
							.addComponent(lblAus))
						.addGroup(gl_pfizikob.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tpAus, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(261, Short.MAX_VALUE))
		);
		pfizikob.setLayout(gl_pfizikob);
		
		JLabel label_15 = new JLabel("Код");
		
		tfdiag = new JTextField();
		tfdiag.setColumns(10);
		
		JLabel label_16 = new JLabel("Медицинское описание");
		
		tfnamed = new JTextField();
		tfnamed.setColumns(10);
		GroupLayout gl_panel_7 = new GroupLayout(panel_7);
		gl_panel_7.setHorizontalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_15)
					.addGap(45)
					.addComponent(tfdiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(109)
					.addComponent(label_16)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tfnamed, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(173, Short.MAX_VALUE))
		);
		gl_panel_7.setVerticalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_7.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_15)
						.addComponent(tfdiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_16)
						.addComponent(tfnamed, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(24, Short.MAX_VALUE))
		);
		panel_7.setLayout(gl_panel_7);
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGap(0, 847, Short.MAX_VALUE)
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGap(0, 75, Short.MAX_VALUE)
		);
		panel_6.setLayout(gl_panel_6);
		
		JLabel lblvid_opl = new JLabel("Вид оплаты");
		
		 vid_opl = new ThriftIntegerClassifierCombobox<>(true);
		
		JLabel lblcobr = new JLabel("Цель обращения");
		
		c_obr = new ThriftIntegerClassifierCombobox<>(true);
		c_obr.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(Vvod.this, c_obr.getSelectedItem().pcod);
			}
		});
		
		JLabel lblrez = new JLabel("Результат");
		
		cbrez = new ThriftIntegerClassifierCombobox<>(true);
		
		
		JLabel lblish = new JLabel("Исход");
		
		 cbish = new ThriftIntegerClassifierCombobox<>(true);
		 
		GroupLayout gl_panel_Talon = new GroupLayout(panel_Talon);
		gl_panel_Talon.setHorizontalGroup(
			gl_panel_Talon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_Talon.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_Talon.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_Talon.createSequentialGroup()
							.addComponent(lblcobr)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(c_obr, GroupLayout.PREFERRED_SIZE, 333, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_Talon.createSequentialGroup()
							.addComponent(lblvid_opl)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(vid_opl, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_Talon.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_Talon.createSequentialGroup()
							.addComponent(lblrez, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(cbrez, GroupLayout.PREFERRED_SIZE, 359, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_Talon.createSequentialGroup()
							.addComponent(lblish, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbish, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addContainerGap(359, Short.MAX_VALUE))
		);
		gl_panel_Talon.setVerticalGroup(
			gl_panel_Talon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_Talon.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel_Talon.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_Talon.createSequentialGroup()
							.addGroup(gl_panel_Talon.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_Talon.createSequentialGroup()
									.addGap(3)
									.addComponent(lblrez))
								.addComponent(cbrez, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(9)
							.addGroup(gl_panel_Talon.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblish)
								.addComponent(cbish, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_Talon.createSequentialGroup()
							.addGroup(gl_panel_Talon.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblvid_opl)
								.addComponent(vid_opl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_Talon.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblcobr)
								.addComponent(c_obr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_Talon.setLayout(gl_panel_Talon);
		
		JButton button_6 = new JButton("Настройка");
		button_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				settingsosm.setVisible(true);}
		});
		
		JScrollPane sPos = new JScrollPane();
		
		JButton button_7 = new JButton("Наблюдение за берем.");
		button_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				postber.setVisible(true);
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(10)
							.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, 1864, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(label_14)
							.addGap(6)
							.addComponent(checkBox_7)
							.addGap(2)
							.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 1859, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(10)
							.addComponent(btnAnamz, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(10)
							.addComponent(button)
							.addGap(10)
							.addComponent(button_1)
							.addGap(10)
							.addComponent(button_2)
							.addGap(10)
							.addComponent(button_3)
							.addGap(10)
							.addComponent(button_4)
							.addGap(6)
							.addComponent(button_5)
							.addGap(18)
							.addComponent(button_6)
							.addGap(2938)))
					.addGap(114))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_Talon, 0, 0, Short.MAX_VALUE)
					.addGap(3012))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 1004, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(3012, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(sPos, GroupLayout.DEFAULT_SIZE, 995, Short.MAX_VALUE)
					.addGap(3021))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(button_7)
					.addContainerGap(3927, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnAnamz)
						.addComponent(button)
						.addComponent(button_1)
						.addComponent(button_2)
						.addComponent(button_3)
						.addComponent(button_4)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(button_5)
							.addComponent(button_6)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(button_7)
					.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
					.addComponent(sPos, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_Talon, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 495, GroupLayout.PREFERRED_SIZE)
					.addGap(72)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(30)
							.addComponent(label_14))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(26)
							.addComponent(checkBox_7))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(51)
							.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE))))
		);
		
		TabPos = new CustomTable<>(false,true,PvizitAmb.class,3,"Дата",4,"Код спец.",5,"Должность");
		TabPos.setDateField(0);
		sPos.setViewportView(TabPos);
		TabPos.setFillsViewportHeight(true);
			TabPos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()){
					if (TabPos.getSelectedItem()!=null){
						try {
							PdiagAmb pda = MainForm.tcl.getPdiagAmb(TabPos.getSelectedItem().id_obr);
							tfdiag.setText(pda.getDiag());
						} catch (KmiacServerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (PdiagAmbNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (TException e) {
							MainForm.conMan.reconnect(e);
						} 
					}
				}
			}
		});
			
		JPanel plocst = new JPanel();
		tabbedPane.addTab("Localis status", null, plocst, null);
		
		JTextPane tpLocalis = new JTextPane();
		GroupLayout gl_plocst = new GroupLayout(plocst);
		gl_plocst.setHorizontalGroup(
			gl_plocst.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_plocst.createSequentialGroup()
					.addContainerGap()
					.addComponent(tpLocalis, GroupLayout.DEFAULT_SIZE, 791, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_plocst.setVerticalGroup(
			gl_plocst.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_plocst.createSequentialGroup()
					.addContainerGap()
					.addComponent(tpLocalis, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(381, Short.MAX_VALUE))
		);
		plocst.setLayout(gl_plocst);
		
		JPanel pds = new JPanel();
		tabbedPane.addTab("Диагноз", null, pds, null);
		
		JLabel lblkod = new JLabel("Код по МКБ");
		
		JLabel lblnamed = new JLabel("Медицинское описание");
		
		tfkodmkb = new JTextField();
		tfkodmkb.setColumns(10);
		
		tfname = new JTextField();
		tfname.setColumns(10);
		
		JPanel pvidd = new JPanel();
		pvidd.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		ButtonGroup GroupBox1 = new ButtonGroup();
		JRadioButton jbosn = new JRadioButton("Основной",true);
		JRadioButton jbsoput = new JRadioButton("Сопутствующий",true);
		JRadioButton jbosl = new JRadioButton("Осложнение основного",true);
		pvidd.add(jbosn);
		pvidd.add(jbsoput);
		pvidd.add(jbosl);
		GroupBox1.add(jbosn);
		GroupBox1.add(jbsoput);
		GroupBox1.add(jbosl);
		
		JPanel ppredv = new JPanel();
		ppredv.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		ButtonGroup GroupBox2 = new ButtonGroup();
		JRadioButton jbpredv = new JRadioButton("Предварительный",true);
		JRadioButton jbzakl = new JRadioButton("Заключительный",true);
		ppredv.add(jbpredv);
		ppredv.add(jbzakl);
		GroupBox2.add(jbpredv);
		GroupBox2.add(jbzakl);
		
		tableDiag = new CustomTable<>(false,true,PdiagAmb.class,5,"Код МКБ",6, "Наименование");
//		table = new CustomTable<>(false, true, ZapVr.class, 3,"Фамилия",4, "Имя", 5, "Отчество",6,"Серия полиса",7,"Номер полиса");
		GroupLayout gl_pds = new GroupLayout(pds);
		gl_pds.setHorizontalGroup(
			gl_pds.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pds.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pds.createParallelGroup(Alignment.TRAILING)
						.addComponent(tableDiag, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_pds.createSequentialGroup()
							.addComponent(lblkod)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfkodmkb, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.LEADING, gl_pds.createSequentialGroup()
							.addComponent(lblnamed)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfname, GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_pds.createSequentialGroup()
							.addComponent(pvidd, GroupLayout.PREFERRED_SIZE, 358, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(ppredv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_pds.setVerticalGroup(
			gl_pds.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pds.createSequentialGroup()
					.addContainerGap()
					.addComponent(tableDiag, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_pds.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblkod)
						.addComponent(tfkodmkb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_pds.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblnamed)
						.addComponent(tfname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addGroup(gl_pds.createParallelGroup(Alignment.LEADING)
						.addComponent(ppredv, 0, 0, Short.MAX_VALUE)
						.addComponent(pvidd, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
					.addGap(356))
		);
		pds.setLayout(gl_pds);
		
		JPanel pnazn = new JPanel();
		tabbedPane.addTab("Назначения", null, pnazn, null);
		GroupLayout gl_pnazn = new GroupLayout(pnazn);
		gl_pnazn.setHorizontalGroup(
			gl_pnazn.createParallelGroup(Alignment.LEADING)
				.addGap(0, 683, Short.MAX_VALUE)
		);
		gl_pnazn.setVerticalGroup(
			gl_pnazn.createParallelGroup(Alignment.LEADING)
				.addGap(0, 250, Short.MAX_VALUE)
		);
		pnazn.setLayout(gl_pnazn);
		
		JPanel pzakl = new JPanel();
		tabbedPane.addTab("Заключение", null, pzakl, null);
		
		JLabel lblzakl = new JLabel("Заключение специалиста");
		
		JTextPane tpzakl = new JTextPane();
		
		JLabel lblrecom = new JLabel("Медицинские рекомендации");
		
		JTextPane tprecom = new JTextPane();
		GroupLayout gl_pzakl = new GroupLayout(pzakl);
		gl_pzakl.setHorizontalGroup(
			gl_pzakl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pzakl.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pzakl.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pzakl.createSequentialGroup()
							.addComponent(lblzakl)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tpzakl, GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE))
						.addGroup(gl_pzakl.createSequentialGroup()
							.addComponent(lblrecom)
							.addGap(18)
							.addComponent(tprecom, GroupLayout.DEFAULT_SIZE, 629, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_pzakl.setVerticalGroup(
			gl_pzakl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pzakl.createSequentialGroup()
					.addGroup(gl_pzakl.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pzakl.createSequentialGroup()
							.addContainerGap()
							.addComponent(tpzakl, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pzakl.createSequentialGroup()
							.addGap(43)
							.addComponent(lblzakl)))
					.addGroup(gl_pzakl.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pzakl.createSequentialGroup()
							.addGap(36)
							.addComponent(lblrecom))
						.addGroup(gl_pzakl.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tprecom, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)))
					.addGap(367))
		);
		pzakl.setLayout(gl_pzakl);
		panel.setLayout(gl_panel);
		getContentPane().setLayout(gl_contentPane);
	}
	private void setBorder(EmptyBorder emptyBorder) {
		// TODO Auto-generated method stub
		
	}
	public void showVvod(UserAuthInfo authInfo, ZapVr zapVr) {
		zapVrSave = zapVr;
		pvizit = new Pvizit();
		pvizit.setNpasp(zapVr.npasp);
		pos.setNpasp(zapVr.npasp);
		diag.setNpasp(zapVr.npasp);
		pr.setNpasp(zapVr.npasp);
		pvizit.setCod_sp(authInfo.pcod);
		pvizit.setCdol("11");
		pos.setCod_sp(authInfo.pcod);
		diag.setCod_sp(authInfo.pcod);
		try {
			pvizit.setDatao(SimpleDateFormat.getDateInstance().parse("01.02.2012").getTime());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		pvizit.setDataz(System.currentTimeMillis());
		pos.setDatap(System.currentTimeMillis());
		diag.setDatap(System.currentTimeMillis());
			
		if (zapVr.vid_p == 1) {
			try {
				MainForm.tcl.AddPvizit(pvizit);
				MainForm.tcl.AddPvizitAmb(pos);
				MainForm.tcl.AddPdiagAmb(diag);
			} catch (KmiacServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
		try {
			MainForm.tcl.AddPvizitAmb(pos);
			MainForm.tcl.UpdatePvizit(pvizit);
			MainForm.tcl.AddPdiagAmb(diag);
			if (diag.prizn == 2){//присвоить значение полей данным.вписать в табл.
			MainForm.tcl.AddPdiagZ(dz);	
			}
			//Addpdiagamb может не быть.может и быть update.то же самое и с осмотром
		} catch (KmiacServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			
		}
		
		setVisible(true);
	}
	
	public void onConnect() {
		try {
			//c_obr.setData(MainForm.tcl.getAp0());
			cbrez.setData(MainForm.tcl.getAp0());
			cbish.setData(MainForm.tcl.getAq0());
			TabPos.setData(MainForm.tcl.getPvizitAmb(6));
			//vid_opl.setData(MainForm.tcl.getOpl());потом убрать знак комментария
		} catch (KmiacServerException | TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
