package ru.nkz.ivcgzo.clientOsm;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.thrift.TException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.clientOsm.patientInfo.Classifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftOsm.PdiagAmb;
import ru.nkz.ivcgzo.thriftOsm.PdiagZ;
import ru.nkz.ivcgzo.thriftOsm.Priem;
import ru.nkz.ivcgzo.thriftOsm.PriemNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Pvizit;
import ru.nkz.ivcgzo.thriftOsm.PvizitAmb;
import ru.nkz.ivcgzo.thriftOsm.ZapVr;

public class Vvod extends JFrame {
	private static final long serialVersionUID = 4579259944135540676L;
	public static ZapVr zapVrSave;
	private FormSign sign;
	private PrintForm printform;
	private SettingsOsm settingsosm;
	private FormPostBer postber;
	private Pvizit pvizit;
	private PvizitAmb pos;
	private PdiagAmb diag;
	private Priem pr;
	private JPanel pzakl;
	private PdiagZ dz;
	private JPanel Jalob;
	private JTextField tftemp;
	private JTextField tfad;
	private JTextField tfrost;
	private JTextField tfves;
	private JTextField tfchss;
	private JTextField tfkodmkb;
	private JTextField tfname;
	private JEditorPane tpJalob;
	private JEditorPane tpJalobd;
	private JEditorPane tpJalobkrov;
	private JEditorPane tpJalobp;
	private JEditorPane tpJalobmoch;
	private JEditorPane tpJalobendo;
	private JEditorPane tpJalobnerv;
	private JEditorPane tpJalobopor;
	private JEditorPane tpJaloblih;
	private JEditorPane tpJalobobh;
	private JEditorPane tpJalobproch;
	private JEditorPane tpNachzab;
	private JEditorPane tpSympt;
	private JEditorPane tpOtnbol;
	private JEditorPane tpPssit;
	private JEditorPane tpObsost;
	private JEditorPane tpKoj;
	private JEditorPane tpSliz;
	private JEditorPane tpPodkkl;
	private JEditorPane tpLimf;
	private JEditorPane tpKostmysh;
	private JEditorPane tpNervnps;
	private JEditorPane tpTelo;
	private JEditorPane tpSust;
	private JEditorPane tpDyh;
	private JEditorPane tpGrkl;
	private JEditorPane tpPerkl;
	private JEditorPane tpAusl;
	private JEditorPane tpBronho;
	private JEditorPane tpArter;
	private JEditorPane tpObls;
	private JEditorPane tpPerks;
	private JEditorPane tpAuss;
	private JEditorPane tpPolrta;
	private JEditorPane tpJivot;
	private JEditorPane tpPalpjivot;
	private JEditorPane tpJkt;
	private JEditorPane tpPalpjel;
	private JEditorPane tpPalppodjel;
	private JEditorPane tpPechen;
	private JEditorPane tpJelch;
	private JEditorPane tpSelez;
	private JEditorPane tpOblzad;
	private JEditorPane tpPoyasn;
	private JEditorPane tpPochki;
	private JEditorPane tpMoch;
	private JEditorPane tpMoljel;
	private JEditorPane tpGrjel;
	private JEditorPane tpMatka;
	private JEditorPane tpNarpolov;
	private JEditorPane tpChitov;
	private JEditorPane tpFizObsl;
	private JEditorPane tpLocalis;
	private JEditorPane tpOcenka;
	private ThriftStringClassifierCombobox<StringClassifier> c_obr;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cbrez;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cbish;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> vid_opl;
	private CustomTable<PvizitAmb, PvizitAmb._Fields> TabPos;
	private JEditorPane tpStPraes;
	private JEditorPane tprecom;
	private CustomTable<PdiagAmb,PdiagAmb._Fields> TabDiag;
	private JTextField textField;
	
	
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
//		 postber = new FormPostBer();
		 settingsosm = new SettingsOsm();
		 pos = new PvizitAmb();
		 diag = new PdiagAmb();
		 pr = new Priem();
		 dz = new PdiagZ();
		 printform = new PrintForm();
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
		
		final JPanel panel = new JPanel();
		panel.setAlignmentY(Component.TOP_ALIGNMENT);
		panel.setAlignmentX(Component.LEFT_ALIGNMENT);
		scrollPane.setRowHeaderView(panel);
		
		JButton btnAnamz = new JButton("Анамнез жизни");
		btnAnamz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			sign.setVisible(true);}
		});
				
		JButton bA = new JButton("Аллергоанамнез");
		bA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		
		
		JButton bS = new JButton("Сохранить");
		bS.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				diag.setDiag(tfkodmkb.getText());
				diag.setNamed(tfname.getText());
				/*прежде чем сохранить, проделать эту операцию : diag.setNamed(tfname.getText()); со всеми нужными полями*/
				/*а потом вызывать метод из thrift,update*/
//				pr.setT_ad(tfad.getText());
//				pr.setT_chss(tfchss.getText());
//				pr.setT_rost(tfrost.getText());
//				pr.setT_ves(tfves.getText());
//				pr.setT_st_localis(tpLocalis.getText());
//				pr.setT_jalob_d(tpJalobd.getText());
//				pr.setT_jalob_krov(tpJalobkrov.getText());
//				pr.setT_jalob_p(tpJalobp.getText());
//				pr.setT_jalob_moch(tpJalobmoch.getText());
//				pr.setT_jalob_endo(tpJalobendo.getText());
//				pr.setT_jalob_nerv(tpJalobnerv.getText());
//				pr.setT_jalob_opor(tpJalobopor.getText());
//				pr.setT_jalob_lih(tpJaloblih.getText());
//				pr.setT_jalob_obh(tpJalobobh.getText());
//				pr.setT_jalob_proch(tpJalobproch.getText());
//				pr.setT_nachalo_zab(tpNaczab.getText());
//				pr.setT_sympt(tpSympt.getText());
//				pr.setT_otn_bol(tpOtnbol.getText());
//				pr.setT_ps_syt(tpPssyt.getText());
//				pr.setT_ob_sost(tpObsost.getText());
//				pr.setT_koj_pokr(tpKoj.getText());
//				pr.setT_sliz(tpSliz.getText());
//				pr.setT_podk_kl(tpPodkkl.getText());
//				pr.setT_limf_uzl(tpLimf.getText());
//				pr.setT_kost_mysh(tpKostmysh.getText());
//				pr.setT_nervn_ps(tpNervnps.getText());
//				pr.setT_telo(tpTelo.getText());
//				pr.setT_sust(tpSust.getText());
//				pr.setT_dyh(tpDyh.getText());
//				pr.setT_gr_kl(tpGrkl.getText());
//				pr.setT_perk_l(tpPerkl.getText());
//				pr.setT_aus_l(tpAusl.getText());
//				pr.setT_bronho(tpBronho.getText());
//				pr.setT_arter(tpArter.getText());
//				pr.setT_obl_s(tpObls.getText());
//				pr.setT_perk_s(tpPerks.getText());
//				pr.setT_aus_s(tpAuss.getText());
//				pr.setT_pol_rta(tpPolrta.getText());
//				pr.setT_jivot(tpJivot.getText());
//				pr.setT_palp_jivot(tpPalpjivot.getText());
//				pr.setT_jel_kish(tpJelkish.getText());
//				pr.setT_palp_jel(tpPalpjel.getText());
//				pr.setT_palp_podjel(tpPalppodjel.getText());
//				pr.setT_pechen(tpPechen.getText());
//				pr.setT_jelch(tpJech.getText());
//				pr.setT_selez(tpSelez.getText());
//				pr.setT_obl_zad(tpOblzad.getText());
//				pr.setT_poyasn(tpPoyasn.getText());
//				pr.setT_pochk(tpPochki.getText());
//				pr.setT_moch(tpMoch.getText());
//				pr.setT_mol_jel(tpMoljel.getText());
//				pr.setT_gr_jel(tpGrjel.getText());
//				pr.setT_matka(tpMatka.getText());
//				pr.setT_nar_polov(tpNarpolov.getText());
//				pr.setT_chitov(tpChitov.getText());
//				pr.setT_ocenka(tpOcenka.getText());
			 	
				//pr.setOsmotr("<Жалобы> "+tpJalob.getText()+" <Анамнез заболевания> "+tpanamn.getText()+" <StatusPraesense> Темп. "+tftemp.getText()+"Чсс "+tfchss.getText()+"<StatusPraesense> АД "+tfad.getText()+"Рост "+tfrost.getText()+"Вес "+tfves.getText()+" <Физикальное обсл.> "+tposm.getText()+" "+tpaus.getText()+" "+tppalp.getText()+" "+tpperk.getText());
//				pr.setOsmotr("<Жалобы> "+tpJalob.getText()+" <Анамнез заболевания> "+tpanamn.getText()+" <StatusPraesense> Темп. "+tftemp.getText()+"Чсс "+tfchss.getText()+"<StatusPraesense> АД "+tfad.getText()+"Рост "+tfrost.getText()+"Вес "+tfves.getText()+" <Физикальное обсл.> "+tposm.getText()+" "+tpaus.getText()+" "+tppalp.getText()+" "+tpperk.getText());

			}
		});
		
		JButton bSSh = new JButton("Сохранить как шаблон");
		
		JButton bZSh = new JButton("Загрузить из шаблона");
		
		final JButton bVD = new JButton("Печатные формы");
		bVD.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				printform.setVisible(true);
//			int x = bVD.getX();
//	                int y = bVD.getY() + bVD.getHeight();
//	                JPopupMenu jPopupMenu = new JPopupMenu();
//
//	                JMenuItem mi1 = new JMenuItem("Мед.карта амб.больного");
//	                jPopupMenu.add(mi1);
//	                mi1.addActionListener(new ActionListener() {
//	        			public void actionPerformed(ActionEvent e) {
//	        			}
//	        		});
//	                JMenuItem mi2 = new JMenuItem("Протокол посещения");
//	                jPopupMenu.add(mi2);
//	                mi2.addActionListener(new ActionListener() {
//	        			public void actionPerformed(ActionEvent e) {
//	        			}
//	        		});
//	                JMenuItem mi3 = new JMenuItem("Выписка из амб.карты");
//	                jPopupMenu.add(mi3);
//	                mi3.addActionListener(new ActionListener() {
//	        			public void actionPerformed(ActionEvent e) {
//	        			}
//	        		});
//	                JMenuItem mi4 = new JMenuItem("Направление на госпитализацию");
//	                jPopupMenu.add(mi4);
//	                mi4.addActionListener(new ActionListener() {
//	        			public void actionPerformed(ActionEvent e) {
//	        			}
//	        		});
//	                  JMenuItem mi5 = new JMenuItem("Направление на консультацию");
//	                jPopupMenu.add(mi5);
//	                mi5.addActionListener(new ActionListener() {
//	        			public void actionPerformed(ActionEvent e) {
//	        			}
//	        		});
//	                JMenuItem mi6 = new JMenuItem("Направление на исследование");
//	                jPopupMenu.add(mi6);
//	                mi6.addActionListener(new ActionListener() {
//	        			public void actionPerformed(ActionEvent e) {
//	        			}
//	        		});
//	                JMenuItem mi7 = new JMenuItem("Направление на МСЭК");
//	                jPopupMenu.add(mi7);
//	                mi7.addActionListener(new ActionListener() {
//	        			public void actionPerformed(ActionEvent e) {
//	        			}
//	        		});
//	                JMenuItem mi8 = new JMenuItem("Заключение ВК");
//	                jPopupMenu.add(mi8);
//	                mi8.addActionListener(new ActionListener() {
//	        			public void actionPerformed(ActionEvent e) {
//	        			}
//	        		});
//	                jPopupMenu.show(panel, x, y);
}
		});
		
		JPanel panel_Talon = new JPanel();
				panel_Talon.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0422\u0430\u043B\u043E\u043D \u0430\u043C\u0431.\u043F\u0430\u0446\u0438\u0435\u043D\u0442\u0430", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		JButton bDelet = new JButton("Удалить");
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		
		 Jalob = new JPanel();
		
		 Jalob.setAutoscrolls(true);
		tabbedPane.addTab("Жалобы", null, Jalob, null);
		
		JScrollPane spJalob = new JScrollPane();
		GroupLayout gl_Jalob = new GroupLayout(Jalob);
		gl_Jalob.setHorizontalGroup(
			gl_Jalob.createParallelGroup(Alignment.TRAILING)
				.addComponent(spJalob, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE)
		);
		gl_Jalob.setVerticalGroup(
			gl_Jalob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_Jalob.createSequentialGroup()
					.addComponent(spJalob, GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
					.addGap(16))
		);
		
		JPanel panJalob = new JPanel();
		spJalob.setViewportView(panJalob);
		
		final JLabel lbljalob = new JLabel("Жалобы на:");
		
		 tpJalob = new JEditorPane();
		tpJalob.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lbljald = new JLabel("Дыхательная система");
		
		 tpJalobd = new JEditorPane();
		tpJalobd.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		 tpJalobkrov = new JEditorPane();
		tpJalobkrov.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lbljalp = new JLabel("Система пищеварения");
		
		final JLabel lbljalkr = new JLabel("Система кровообращения");
		
		 tpJalobp = new JEditorPane();
		tpJalobp.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lbljalm = new JLabel("Мочеполовая система");
		
		final JLabel lbljalendo = new JLabel("Эндокринная система");
		
		 tpJalobendo = new JEditorPane();
		
		final JLabel lbljalnerv = new JLabel("Нервная система и органы чувств");
		
		 tpJalobnerv = new JEditorPane();
		tpJalobnerv.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lbljalop = new JLabel("Опорно-двигательная система");
		
		 tpJalobopor = new JEditorPane();
		tpJalobopor.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lbljallih = new JLabel("Лихорадка");
		
		 tpJaloblih = new JEditorPane();
		tpJaloblih.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblja_lob = new JLabel("Жалобы общего характера");
		
		 tpJalobobh = new JEditorPane();
		tpJalobobh.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lbljalpr = new JLabel("Прочие жалобы");
		
		 tpJalobproch = new JEditorPane();
		tpJalobproch.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		tpJalobendo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		 tpJalobmoch = new JEditorPane();
		tpJalobmoch.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GroupLayout gl_panJalob = new GroupLayout(panJalob);
		gl_panJalob.setHorizontalGroup(
			gl_panJalob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panJalob.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panJalob.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panJalob.createParallelGroup(Alignment.LEADING, false)
							.addComponent(tpJalob, GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
							.addComponent(lbljalob, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
							.addComponent(lbljald, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
							.addComponent(lbljalkr, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
							.addComponent(tpJalobd,GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
							.addComponent(tpJalobkrov,GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE))
						.addComponent(lbljalp, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobp, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbljalm, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobmoch, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbljalendo, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobendo, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbljalnerv, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobnerv, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbljalop, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobopor, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbljallih, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJaloblih, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblja_lob, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobobh, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbljalpr, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobproch, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(336, Short.MAX_VALUE))
		);
		gl_panJalob.setVerticalGroup(
			gl_panJalob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panJalob.createSequentialGroup()
					.addComponent(lbljalob)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalob, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lbljald)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobd, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lbljalkr)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobkrov, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lbljalp)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobp, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lbljalm)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobmoch, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addComponent(lbljalendo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobendo, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lbljalnerv)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobnerv, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lbljalop)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobopor, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lbljallih)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJaloblih, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblja_lob)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobobh, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lbljalpr)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobproch, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addContainerGap(22, Short.MAX_VALUE))
		);
		panJalob.setLayout(gl_panJalob);
		Jalob.setLayout(gl_Jalob);
		
		JPanel pAnanmnesis = new JPanel();
		tabbedPane.addTab("Anamnesis Morbi", null, pAnanmnesis, null);
		
		JScrollPane spAnamnesis = new JScrollPane();
		GroupLayout gl_pAnanmnesis = new GroupLayout(pAnanmnesis);
		gl_pAnanmnesis.setHorizontalGroup(
			gl_pAnanmnesis.createParallelGroup(Alignment.LEADING)
				.addComponent(spAnamnesis, GroupLayout.DEFAULT_SIZE, 841, Short.MAX_VALUE)
		);
		gl_pAnanmnesis.setVerticalGroup(
			gl_pAnanmnesis.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pAnanmnesis.createSequentialGroup()
					.addComponent(spAnamnesis, GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel panAnamnes = new JPanel();
		spAnamnesis.setColumnHeaderView(panAnamnes);
		
		final JLabel lblNacZab = new JLabel("Начало заболевания");
		
		 tpNachzab = new JEditorPane();
		tpNachzab.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblSympt = new JLabel("Симптомы");
		
		final JLabel lblotnbol = new JLabel("Отношение больного к болезни");
		
		 tpOtnbol = new JEditorPane();
		tpOtnbol.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		 tpSympt = new JEditorPane();
		tpSympt.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblpssit = new JLabel("Психологическая ситуация в связи с болезнью");
		
		 tpPssit = new JEditorPane();
		tpPssit.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GroupLayout gl_panAnamnes = new GroupLayout(panAnamnes);
		gl_panAnamnes.setHorizontalGroup(
			gl_panAnamnes.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panAnamnes.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panAnamnes.createParallelGroup(Alignment.LEADING)
						.addComponent(tpNachzab, GroupLayout.PREFERRED_SIZE, 542, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSympt, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSympt, GroupLayout.PREFERRED_SIZE, 542, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblotnbol, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpOtnbol, GroupLayout.PREFERRED_SIZE, 542, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPssit, GroupLayout.PREFERRED_SIZE, 542, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNacZab, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblpssit, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(888, Short.MAX_VALUE))
		);
		gl_panAnamnes.setVerticalGroup(
			gl_panAnamnes.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panAnamnes.createSequentialGroup()
					.addComponent(lblNacZab)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpNachzab, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblSympt)
					.addGap(6)
					.addComponent(tpSympt, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblotnbol)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpOtnbol, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGap(18)
					.addComponent(lblpssit)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPssit, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(547, Short.MAX_VALUE))
		);
		panAnamnes.setLayout(gl_panAnamnes);
		pAnanmnesis.setLayout(gl_pAnanmnesis);
		
		JPanel pStatuspr = new JPanel();
		tabbedPane.addTab("Status Praesense", null, pStatuspr, null);
		
		JPanel panel_1 = new JPanel();
		
		final JLabel lbltemp = new JLabel("Температура");
		
		tftemp = new JTextField();
		tftemp.setColumns(10);
		
		final JLabel lblad = new JLabel("АД");
		
		tfad = new JTextField();
		tfad.setColumns(10);
		
		final JLabel lblrost = new JLabel("Рост");
		
		tfrost = new JTextField();
		tfrost.setColumns(10);
		
		final JLabel lblves = new JLabel("Вес");
		
		tfves = new JTextField();
		tfves.setColumns(10);
		
		final JLabel lblchss = new JLabel("ЧСС");
		
		tfchss = new JTextField();
		tfchss.setColumns(10);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
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
					.addComponent(tfves, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(31)
					.addComponent(lblchss)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tfchss, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(102, Short.MAX_VALUE))
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
						.addComponent(tfves, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblchss)
						.addComponent(tfchss, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(69, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JScrollPane sppraesense = new JScrollPane();
		GroupLayout gl_pStatuspr = new GroupLayout(pStatuspr);
		gl_pStatuspr.setHorizontalGroup(
			gl_pStatuspr.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pStatuspr.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pStatuspr.createParallelGroup(Alignment.TRAILING)
						.addComponent(sppraesense, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_pStatuspr.setVerticalGroup(
			gl_pStatuspr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pStatuspr.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addGap(8)
					.addComponent(sppraesense, GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel panPraesense = new JPanel();
		sppraesense.setViewportView(panPraesense);
		
		final JLabel lblStPraes = new JLabel("Status praesense");
		
		 tpStPraes = new JEditorPane();
		tpStPraes.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblobsost = new JLabel("Общее состояние");
		
		 tpObsost = new JEditorPane();
		tpObsost.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblkoj = new JLabel("Кожные покровы");
		
		 tpKoj = new JEditorPane();
		tpKoj.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblsl = new JLabel("Видимые слизистые");
		
		 tpSliz = new JEditorPane();
		tpSliz.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblpodkkl = new JLabel("Подкожная клетчатка");
		
		 tpPodkkl = new JEditorPane();
		tpPodkkl.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lbllimf = new JLabel("Лимфатические узлы");
		
		 tpLimf = new JEditorPane();
		tpPodkkl.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblkostm = new JLabel("Костно-мышечная система");
		
		 tpKostmysh = new JEditorPane();
		tpPodkkl.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblnervps = new JLabel("Нервно-психический статус");
		
		 tpNervnps = new JEditorPane();
		 tpNervnps.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lbltelo = new JLabel("Телосложение");
		
		 tpTelo = new JEditorPane();
		tpTelo.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		GroupLayout gl_panPraesense = new GroupLayout(panPraesense);
		gl_panPraesense.setHorizontalGroup(
			gl_panPraesense.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panPraesense.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panPraesense.createParallelGroup(Alignment.LEADING)
						.addComponent(tpStPraes, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStPraes)
						.addComponent(tpObsost, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpKoj, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblkoj, GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblobsost, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblsl, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSliz, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblpodkkl, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPodkkl, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbllimf, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpLimf, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblkostm, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpKostmysh, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblnervps, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpNervnps, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbltelo, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpTelo, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(261, Short.MAX_VALUE))
		);
		gl_panPraesense.setVerticalGroup(
			gl_panPraesense.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panPraesense.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblStPraes)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpStPraes, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblobsost)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpObsost, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblkoj)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpKoj, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblsl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpSliz, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblpodkkl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPodkkl, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lbllimf)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpLimf, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblkostm)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpKostmysh, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblnervps)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpNervnps, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lbltelo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpTelo, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addContainerGap(367, Short.MAX_VALUE))
		);
		panPraesense.setLayout(gl_panPraesense);
		pStatuspr.setLayout(gl_pStatuspr);
		
		JPanel pfizikob = new JPanel();
		tabbedPane.addTab("Физикальное обследование", null, pfizikob, null);
		
		JScrollPane spFizik = new JScrollPane();
		GroupLayout gl_pfizikob = new GroupLayout(pfizikob);
		gl_pfizikob.setHorizontalGroup(
			gl_pfizikob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pfizikob.createSequentialGroup()
					.addComponent(spFizik, GroupLayout.DEFAULT_SIZE, 831, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pfizikob.setVerticalGroup(
			gl_pfizikob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pfizikob.createSequentialGroup()
					.addComponent(spFizik, GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JPanel panFiz = new JPanel();
		spFizik.setViewportView(panFiz);
		
		final JLabel lblfizik = new JLabel("Физикальное обследование");
		
		tpFizObsl = new JEditorPane();
		tpFizObsl.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblsust = new JLabel("Суставы");
		
		 tpSust = new JEditorPane();
		tpSust.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lbldyh = new JLabel("Дыхание");
		
		 tpDyh = new JEditorPane();
		tpDyh.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblgrkl = new JLabel("Грудная клетка");
		
		 tpGrkl = new JEditorPane();
		tpGrkl.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblperl = new JLabel("Перкуссия легких");
		
		 tpPerkl = new JEditorPane();
		tpPerkl.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblausl = new JLabel("Аускультация легких");
		
		 tpAusl = new JEditorPane();
		tpAusl.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblbronh = new JLabel("Бронхофония");
		
		 tpBronho = new JEditorPane();
		tpBronho.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblarter = new JLabel("Артерии и шейные вены");
		
		 tpArter = new JEditorPane();
		tpArter.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblobls = new JLabel("Область сердца");
		
		 tpObls = new JEditorPane();
		tpObls.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblpers = new JLabel("Перкуссия сердца");
		
		 tpPerks = new JEditorPane();
		tpPerks.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblauss = new JLabel("Аускультация сердца");
		
		 tpAuss = new JEditorPane();
		tpAuss.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblpolrta = new JLabel("Полость рта");
		
		 tpPolrta = new JEditorPane();
		tpPolrta.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lbljivot = new JLabel("Живот");
		
		 tpJivot = new JEditorPane();
		tpJivot.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblpalpjiv = new JLabel("Пальпация живота");
		
		 tpPalpjivot = new JEditorPane();
		tpPalpjivot.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lbljkt = new JLabel("Пальпация, перкуссия и аускультация желудочно-кишечного тракта");
		
		 tpJkt = new JEditorPane();
		tpJkt.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblpalpjel = new JLabel("Пальпация желудка");
		
		 tpPalpjel = new JEditorPane();
		tpPalpjel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblpalppodjel = new JLabel("Пальпация поджелудочной железы");
		
		 tpPalppodjel = new JEditorPane();
		tpPalppodjel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblpech = new JLabel("Печень");
		
		 tpPechen = new JEditorPane();
		tpPechen.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lbljelch = new JLabel("Желчный пузырь");
		
		 tpJelch = new JEditorPane();
		tpJelch.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblselez = new JLabel("Селезенка");
		
		 tpSelez = new JEditorPane();
		tpSelez.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lbloblzad = new JLabel("Область заднего прохода");
		
		 tpOblzad = new JEditorPane();
		tpOblzad.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				
		final JLabel lblpoyasn = new JLabel("Поясничная область");
		
		 tpPoyasn = new JEditorPane();
		tpPoyasn.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblpochk = new JLabel("Почки");
		
		 tpPochki = new JEditorPane();
		tpPochki.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblmoch = new JLabel("Мочевой пузырь");
		
		 tpMoch = new JEditorPane();
		tpMoch.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblmoljel = new JLabel("Молочные железы");
		
		 tpMoljel = new JEditorPane();
		tpMoljel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblgrjel = new JLabel("Грудные железы мужчин");
		
		 tpGrjel = new JEditorPane();
		tpGrjel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblmatka = new JLabel("Матка и ее придатки");
		
		 tpMatka = new JEditorPane();
		tpMatka.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblnarpolov = new JLabel("Наружные половые органы у мужчин");
		
		 tpNarpolov = new JEditorPane();
		tpNarpolov.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblchitov = new JLabel("Щитовидная железа");
		
		 tpChitov = new JEditorPane();
		tpChitov.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		GroupLayout gl_panFiz = new GroupLayout(panFiz);
		gl_panFiz.setHorizontalGroup(
			gl_panFiz.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panFiz.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panFiz.createParallelGroup(Alignment.LEADING)
						.addComponent(tpFizObsl, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblfizik, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblsust,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSust, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbldyh,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpDyh, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblgrkl,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpGrkl, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblperl,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPerkl, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblausl,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpAusl, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblbronh,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpBronho, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblarter,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpArter, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblobls,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpObls, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblpers,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPerks, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblauss,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpAuss, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblpolrta,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPolrta, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbljivot,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJivot, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblpalpjiv,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPalpjivot, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbljkt,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJkt, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblpalpjel,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPalpjel, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblpalppodjel,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPalppodjel, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblpech,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPechen, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbljelch,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJelch, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblselez,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSelez, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbloblzad,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpOblzad, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblpoyasn,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPoyasn, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblpochk,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPochki, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblmoch,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpMoch, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblmoljel,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpMoljel, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblgrjel,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpGrjel, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblmatka,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpMatka, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblnarpolov,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpNarpolov, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblchitov,GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpChitov, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(423, Short.MAX_VALUE))
		);
		gl_panFiz.setVerticalGroup(
			gl_panFiz.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panFiz.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblfizik)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpFizObsl, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblsust)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpSust, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lbldyh)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpDyh, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblgrkl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpGrkl, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblperl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPerkl, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblausl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpAusl, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblbronh)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpBronho, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblarter)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpArter, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblobls)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpObls, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblpers)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPerks, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblauss)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpAuss, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblpolrta)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPolrta, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lbljivot)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJivot, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblpalpjiv)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPalpjivot, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lbljkt)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJkt, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblpalpjel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPalpjel, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblpalppodjel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPalppodjel, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblpech)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPechen, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lbljelch)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJelch, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblselez)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpSelez, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lbloblzad)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpOblzad, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblpoyasn)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPoyasn, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblpochk)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPochki, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblmoch)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpMoch, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblmoljel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpMoljel, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblgrjel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpGrjel, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblmatka)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpMatka, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblnarpolov)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpNarpolov, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblchitov)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpChitov, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addContainerGap(243, Short.MAX_VALUE))
		);
		panFiz.setLayout(gl_panFiz);
		pfizikob.setLayout(gl_pfizikob);
		
		JLabel lblvid_opl = new JLabel("Вид оплаты");
		
		 vid_opl = new ThriftIntegerClassifierCombobox<>(true);
		
		JLabel lblcobr = new JLabel("Цель обращения");
		
		c_obr = new ThriftStringClassifierCombobox<>(true);
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
		
		JButton bSet = new JButton("Настройка");
		bSet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				settingsosm.setVisible(true);}
		});
		
		JScrollPane sPos = new JScrollPane();
		
		JButton bDB = new JButton("Наблюдение за берем.");
		bDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				postber.setVisible(true);
			}
		});
		
		JButton button_8 = new JButton("+");
		button_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				PvizitAmb pa = TabPos.addExternalItem();
				pa.setCdol(MainForm.authInfo.getPdost());//TODO потом изменить на должность
				pa.setDatap(System.currentTimeMillis());
				pa.setFio_vr(MainForm.authInfo.getName());
				TabPos.updateSelectedItem();
				
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(sPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(button_8))
						.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(panel_Talon, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
							.addComponent(tabbedPane, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 1004, Short.MAX_VALUE)))
					.addContainerGap(3072, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(bDB, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnAnamz, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(10)
					.addComponent(bA)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(bS)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(bSSh)
					.addGap(10)
					.addComponent(bZSh)
					.addGap(10)
					.addComponent(bVD)
					.addGap(6)
					.addComponent(bDelet)
					.addGap(18)
					.addComponent(bSet)
					.addGap(3076))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
											.addComponent(btnAnamz)
											.addComponent(bA)
											.addComponent(bS))
										.addComponent(bSSh))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(bDB)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(sPos, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
								.addComponent(button_8)))
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(bDelet)
								.addComponent(bSet))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(bZSh)
								.addComponent(bVD))))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(panel_Talon, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 495, GroupLayout.PREFERRED_SIZE)
					.addGap(198))
		);
		
		TabPos = new CustomTable<>(false,true,PvizitAmb.class,3,"Дата",19,"ФИО врача",5,"Должность");
		TabPos.setDateField(0);
		sPos.setViewportView(TabPos);
		TabPos.setFillsViewportHeight(true);
		
		JPopupMenu pmvizit = new JPopupMenu();
		//addPopup(TabPos, pmvizit);
		
		JMenuItem mi1 = new JMenuItem("Печать протокола посещения");
		pmvizit.add(mi1);
		
			TabPos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()){
					if (TabPos.getSelectedItem()!=null){
						try {
							Priem pri = MainForm.tcl.getPriem(TabPos.getSelectedItem().id_obr,TabPos.getSelectedItem().npasp,TabPos.getSelectedItem().id);
							if (pri.getT_jalob()!=null){
							lbljalob.setVisible(true);
							tpJalob.setVisible(true);
							tpJalob.setText(pri.getT_jalob());	
							}else{
							tpJalob.setVisible(false);
							lbljalob.setVisible(false);
							}
							if (pri.getT_jalob_d()!=null){
							lbljald.setVisible(true);
							tpJalobd.setVisible(true);
							tpJalobd.setText(pri.getT_jalob_d());	
							}else{
							tpJalobd.setVisible(false);
							lbljald.setVisible(false);
							}
							if (pri.getT_jalob_krov()!=null){
							lbljalkr.setVisible(true);
							tpJalobkrov.setVisible(true);
							tpJalobkrov.setText(pri.getT_jalob_krov());	
							}else{
							tpJalobkrov.setVisible(false);
							lbljalkr.setVisible(false);
							}
							if (pri.getT_jalob_p()!=null){
							lbljalp.setVisible(true);
							tpJalobp.setVisible(true);
							tpJalobp.setText(pri.getT_jalob_p());	
							}else{
							tpJalobp.setVisible(false);
							lbljalp.setVisible(false);
							}
							if (pri.getT_jalob_moch()!=null){
							lbljalm.setVisible(true);
							tpJalobmoch.setVisible(true);
							tpJalobmoch.setText(pri.getT_jalob_moch());	
							}else{
							tpJalobmoch.setVisible(false);
							lbljalm.setVisible(false);
							}
							if (pri.getT_jalob_endo()!=null){
							lbljalendo.setVisible(true);
							tpJalobendo.setVisible(true);
							tpJalobendo.setText(pri.getT_jalob_endo());	
							}else{
							tpJalobendo.setVisible(false);
							lbljalendo.setVisible(false);
							}
							if (pri.getT_jalob_nerv()!=null){
							lbljalnerv.setVisible(true);
							tpJalobnerv.setVisible(true);
							tpJalobnerv.setText(pri.getT_jalob_nerv());	
							}else{
							tpJalobnerv.setVisible(false);
							lbljalnerv.setVisible(false);
							}
							if (pri.getT_jalob_opor()!=null){
							lbljalop.setVisible(true);
							tpJalobopor.setVisible(true);
							tpJalobopor.setText(pri.getT_jalob_opor());	
							}else{
							tpJalobopor.setVisible(false);
							lbljalop.setVisible(false);
							}
							if (pri.getT_jalob_lih()!=null){
							lbljallih.setVisible(true);
							tpJaloblih.setVisible(true);
							tpJaloblih.setText(pri.getT_jalob_lih());	
							}else{
							tpJaloblih.setVisible(false);
							lbljallih.setVisible(false);
							}
							if (pri.getT_jalob_obh()!=null){
							lblja_lob.setVisible(true);
							tpJalobobh.setVisible(true);
							tpJalobobh.setText(pri.getT_jalob_obh());	
							}else{
							tpJalobobh.setVisible(false);
							lblja_lob.setVisible(false);
							}
							if (pri.getT_jalob_proch()!=null){
							lbljalpr.setVisible(true);
							tpJalobproch.setVisible(true);
							tpJalobproch.setText(pri.getT_jalob_proch());	
							}else{
							tpJalobproch.setVisible(false);
							lbljalpr.setVisible(false);
							}
							if (pri.getT_nachalo_zab()!=null){
							lblNacZab.setVisible(true);
							tpNachzab.setVisible(true);
							tpNachzab.setText(pri.getT_nachalo_zab());	
							}else{
							tpNachzab.setVisible(false);
							lblNacZab.setVisible(false);
							}
							if (pri.getT_sympt()!=null){
							lblSympt.setVisible(true);
							tpSympt.setVisible(true);
							tpSympt.setText(pri.getT_sympt());	
							}else{
							tpSympt.setVisible(false);
							lblSympt.setVisible(false);
							}
							if (pri.getT_otn_bol()!=null){
							lblotnbol.setVisible(true);
							tpOtnbol.setVisible(true);
							tpOtnbol.setText(pri.getT_sympt());	
							}else{
							tpOtnbol.setVisible(false);
							lblotnbol.setVisible(false);
							}
							if (pri.getT_ps_syt()!=null){
							lblpssit.setVisible(true);
							tpPssit.setVisible(true);
							tpPssit.setText(pri.getT_ps_syt());	
							}else{
							tpPssit.setVisible(false);
							lblpssit.setVisible(false);
							}
							if (pri.getT_ad()!=null){
							lblad.setVisible(true);
							tfad.setVisible(true);
							tfad.setText(pri.getT_ad());	
							}else{
							tfad.setVisible(false);
							lblad.setVisible(false);
							}
							if (pri.getT_temp()!=null){
							lbltemp.setVisible(true);
							tftemp.setVisible(true);
							tftemp.setText(pri.getT_temp());	
							}else{
							tftemp.setVisible(false);
							lbltemp.setVisible(false);
							}
							if (pri.getT_chss()!=null){
							lblchss.setVisible(true);
							tfchss.setVisible(true);
							tfchss.setText(pri.getT_chss());	
							}else{
							tfchss.setVisible(false);
							lblchss.setVisible(false);
							}
							if (pri.getT_rost()!=null){
							lblrost.setVisible(true);
							tfrost.setVisible(true);
							tfrost.setText(pri.getT_rost());	
							}else{
							tfrost.setVisible(false);
							lblrost.setVisible(false);
							}
							if (pri.getT_ves()!=null){
							lblves.setVisible(true);
							tfves.setVisible(true);
							tfves.setText(pri.getT_ves());	
							}else{
							tfves.setVisible(false);
							lblves.setVisible(false);
							}
							if (pri.getT_ob_sost()!=null){
							lblobsost.setVisible(true);
							tpObsost.setVisible(true);
							tpObsost.setText(pri.getT_ob_sost());	
							}else{
							tpObsost.setVisible(false);
							lblobsost.setVisible(false);
							}
							if (pri.getT_koj_pokr()!=null){
							lblkoj.setVisible(true);
							tpKoj.setVisible(true);
							tpKoj.setText(pri.getT_koj_pokr());	
							}else{
							tpKoj.setVisible(false);
							lblkoj.setVisible(false);
							}
							if (pri.getT_sliz()!=null){
							lblsl.setVisible(true);
							tpSliz.setVisible(true);
							tpSliz.setText(pri.getT_sliz());	
							}else{
							tpSliz.setVisible(false);
							lblsl.setVisible(false);
							}
							if (pri.getT_podk_kl()!=null){
							lblpodkkl.setVisible(true);
							tpPodkkl.setVisible(true);
							tpPodkkl.setText(pri.getT_podk_kl());	
							}else{
							tpPodkkl.setVisible(false);
							lblpodkkl.setVisible(false);
							}
							if (pri.getT_limf_uzl()!=null){
							lbllimf.setVisible(true);
							tpLimf.setVisible(true);
							tpLimf.setText(pri.getT_limf_uzl());	
							}else{
							tpLimf.setVisible(false);
							lbllimf.setVisible(false);
							}
							if (pri.getT_kost_mysh()!=null){
							lblkostm.setVisible(true);
							tpKostmysh.setVisible(true);
							tpKostmysh.setText(pri.getT_kost_mysh());	
							}else{
							tpKostmysh.setVisible(false);
							lblkostm.setVisible(false);
							}
							if (pri.getT_nervn_ps()!=null){
							lblnervps.setVisible(true);
							tpNervnps.setVisible(true);
							tpNervnps.setText(pri.getT_nervn_ps());	
							}else{
							tpNervnps.setVisible(false);
							lblnervps.setVisible(false);
							}
							if (pri.getT_sust()!=null){
							lblsust.setVisible(true);
							tpSust.setVisible(true);
							tpSust.setText(pri.getT_sust());	
							}else{
							tpSust.setVisible(false);
							lblsust.setVisible(false);
							}
							if (pri.getT_dyh()!=null){
							lbldyh.setVisible(true);
							tpDyh.setVisible(true);
							tpDyh.setText(pri.getT_sust());	
							}else{
							tpDyh.setVisible(false);
							lbldyh.setVisible(false);
							}
							if (pri.getT_gr_kl()!=null){
							lblgrkl.setVisible(true);
							tpGrkl.setVisible(true);
							tpGrkl.setText(pri.getT_sust());	
							}else{
							tpGrkl.setVisible(false);
							lblgrkl.setVisible(false);
							}
							if (pri.getT_perk_l()!=null){
							lblperl.setVisible(true);
							tpPerkl.setVisible(true);
							tpPerkl.setText(pri.getT_perk_l());	
							}else{
							tpPerkl.setVisible(false);
							lblperl.setVisible(false);
							}
							if (pri.getT_aus_l()!=null){
							lblausl.setVisible(true);
							tpAusl.setVisible(true);
							tpAusl.setText(pri.getT_aus_l());	
							}else{
							tpAusl.setVisible(false);
							lblausl.setVisible(false);
							}
							if (pri.getT_bronho()!=null){
							lblbronh.setVisible(true);
							tpBronho.setVisible(true);
							tpBronho.setText(pri.getT_bronho());	
							}else{
							tpBronho.setVisible(false);
							lblbronh.setVisible(false);
							}
							if (pri.getT_arter()!=null){
							lblarter.setVisible(true);
							tpArter.setVisible(true);
							tpArter.setText(pri.getT_arter());	
							}else{
							tpArter.setVisible(false);
							lblarter.setVisible(false);
							}
							if (pri.getT_obl_s()!=null){
							lblobls.setVisible(true);
							tpObls.setVisible(true);
							tpObls.setText(pri.getT_obl_s());	
							}else{
							tpObls.setVisible(false);
							lblobls.setVisible(false);
							}
							if (pri.getT_perk_s()!=null){
							lblpers.setVisible(true);
							tpPerks.setVisible(true);
							tpPerks.setText(pri.getT_perk_s());	
							}else{
							tpPerks.setVisible(false);
							lblpers.setVisible(false);
							}
							if (pri.getT_aus_s()!=null){
							lblauss.setVisible(true);
							tpAuss.setVisible(true);
							tpAuss.setText(pri.getT_aus_s());	
							}else{
							tpAuss.setVisible(false);
							lblauss.setVisible(false);
							}
							if (pri.getT_pol_rta()!=null){
							lblpolrta.setVisible(true);
							tpPolrta.setVisible(true);
							tpPolrta.setText(pri.getT_pol_rta());	
							}else{
							tpPolrta.setVisible(false);
							lblpolrta.setVisible(false);
							}
							if (pri.getT_jivot()!=null){
							lbljivot.setVisible(true);
							tpJivot.setVisible(true);
							tpJivot.setText(pri.getT_jivot());	
							}else{
							tpJivot.setVisible(false);
							lbljivot.setVisible(false);
							}
							if (pri.getT_palp_jivot()!=null){
							lblpalpjiv.setVisible(true);
							tpPalpjivot.setVisible(true);
							tpPalpjivot.setText(pri.getT_palp_jivot());	
							}else{
							tpPalpjivot.setVisible(false);
							lblpalpjiv.setVisible(false);
							}
							if (pri.getT_jel_kish()!=null){
							lbljkt.setVisible(true);
							tpJkt.setVisible(true);
							tpJkt.setText(pri.getT_jel_kish());	
							}else{
							tpJkt.setVisible(false);
							lbljkt.setVisible(false);
							}
							if (pri.getT_palp_jel()!=null){
							lblpalpjel.setVisible(true);
							tpPalpjel.setVisible(true);
							tpPalpjel.setText(pri.getT_palp_jel());	
							}else{
							tpPalpjel.setVisible(false);
							lblpalpjel.setVisible(false);
							}
							if (pri.getT_palp_podjel()!=null){
							lblpalppodjel.setVisible(true);
							tpPalppodjel.setVisible(true);
							tpPalppodjel.setText(pri.getT_palp_podjel());	
							}else{
							tpPalppodjel.setVisible(false);
							lblpalppodjel.setVisible(false);
							}
							if (pri.getT_pechen()!=null){
							lblpech.setVisible(true);
							tpPechen.setVisible(true);
							tpPechen.setText(pri.getT_pechen());	
							}else{
							tpPechen.setVisible(false);
							lblpech.setVisible(false);
							}
							if (pri.getT_jelch()!=null){
							lbljelch.setVisible(true);
							tpJelch.setVisible(true);
							tpJelch.setText(pri.getT_jelch());	
							}else{
							tpJelch.setVisible(false);
							lbljelch.setVisible(false);
							}
							if (pri.getT_selez()!=null){
							lblselez.setVisible(true);
							tpSelez.setVisible(true);
							tpSelez.setText(pri.getT_selez());	
							}else{
							tpSelez.setVisible(false);
							lblselez.setVisible(false);
							}
							if (pri.getT_obl_zad()!=null){
							lbloblzad.setVisible(true);
							tpOblzad.setVisible(true);
							tpOblzad.setText(pri.getT_obl_zad());	
							}else{
							tpOblzad.setVisible(false);
							lbloblzad.setVisible(false);
							}
							if (pri.getT_poyasn()!=null){
							lblpoyasn.setVisible(true);
							tpPoyasn.setVisible(true);
							tpPoyasn.setText(pri.getT_poyasn());	
							}else{
							tpPoyasn.setVisible(false);
							lblpoyasn.setVisible(false);
							}
							if (pri.getT_pochk() !=null){
							lblpochk.setVisible(true);
							tpPochki.setVisible(true);
							tpPochki.setText(pri.getT_pochk());	
							}else{
							tpPochki.setVisible(false);
							lblpochk.setVisible(false);
							}
							if (pri.getT_moch() !=null){
							lblmoch.setVisible(true);
							tpMoch.setVisible(true);
							tpMoch.setText(pri.getT_moch());	
							}else{
							tpMoch.setVisible(false);
							lblmoch.setVisible(false);
							}
							if (pri.getT_mol_jel() !=null){
							lblmoljel.setVisible(true);
							tpMoljel.setVisible(true);
							tpMoljel.setText(pri.getT_mol_jel());	
							}else{
							tpMoljel.setVisible(false);
							lblmoljel.setVisible(false);
							}
							if (pri.getT_gr_jel() !=null){
							lblgrjel.setVisible(true);
							tpGrjel.setVisible(true);
							tpGrjel.setText(pri.getT_gr_jel());	
							}else{
							tpGrjel.setVisible(false);
							lblgrjel.setVisible(false);
							}
							if (pri.getT_matka() !=null){
							lblmatka.setVisible(true);
							tpMatka.setVisible(true);
							tpMatka.setText(pri.getT_matka());	
							}else{
							tpMatka.setVisible(false);
							lblmatka.setVisible(false);
							}
							if (pri.getT_nar_polov() !=null){
							lblnarpolov.setVisible(true);
							tpNarpolov.setVisible(true);
							tpNarpolov.setText(pri.getT_nar_polov());	
							}else{
							tpNarpolov.setVisible(false);
							lblnarpolov.setVisible(false);
							}
							if (pri.getT_chitov() !=null){
							lblchitov.setVisible(true);
							tpChitov.setVisible(true);
							tpChitov.setText(pri.getT_chitov());	
							}else{
							tpChitov.setVisible(false);
							lblchitov.setVisible(false);
							}
							if (pri.getT_st_localis() !=null){
							tpLocalis.setVisible(true);
							tpLocalis.setText(pri.getT_st_localis());	
							}else{
							tpLocalis.setVisible(false);
							}
							if (pri.getT_ocenka() !=null){
							tpOcenka.setVisible(true);
							tpOcenka.setText(pri.getT_ocenka());	
							}else{
							tpOcenka.setVisible(false);
							}
							} catch (KmiacServerException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (PriemNotFoundException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (TException e) {
								// TODO Auto-generated catch block
								MainForm.conMan.reconnect(e);
							}
						
		
					}
				}
			}
		});
			
		JPanel plocst = new JPanel();
		tabbedPane.addTab("Localis status", null, plocst, null);
		
		 tpLocalis = new JEditorPane();
		tpLocalis.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
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
		
		JScrollPane spDiag = new JScrollPane();
		TabDiag = new CustomTable<>(false,true,PdiagAmb.class,3,"Дата",4,"Код МКБ",5,"Описание");
		TabDiag.setDateField(0);
		spDiag.setViewportView(TabDiag);
		TabDiag.setFillsViewportHeight(true);		
		
		textField = new JTextField();
		textField.setText(DateFormat.getDateInstance().format(new Date(111111111)));
		textField.setColumns(10);
		GroupLayout gl_pds = new GroupLayout(pds);
		gl_pds.setHorizontalGroup(
			gl_pds.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pds.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pds.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pds.createSequentialGroup()
							.addComponent(lblkod)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfkodmkb, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
							.addGap(54)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pds.createSequentialGroup()
							.addComponent(lblnamed)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfname, GroupLayout.DEFAULT_SIZE, 699, Short.MAX_VALUE))
						.addGroup(gl_pds.createSequentialGroup()
							.addComponent(pvidd, GroupLayout.PREFERRED_SIZE, 358, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(ppredv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pds.createSequentialGroup()
							.addComponent(spDiag, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
							.addGap(369)))
					.addContainerGap())
		);
		gl_pds.setVerticalGroup(
			gl_pds.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pds.createSequentialGroup()
					.addGap(5)
					.addComponent(spDiag, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pds.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblkod)
						.addComponent(tfkodmkb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
		
		JPanel pOcenka = new JPanel();
		tabbedPane.addTab("Оценка данных анамнеза", null, pOcenka, null);
		
		 tpOcenka = new JEditorPane();
		GroupLayout gl_pOcenka = new GroupLayout(pOcenka);
		gl_pOcenka.setHorizontalGroup(
			gl_pOcenka.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pOcenka.createSequentialGroup()
					.addContainerGap()
					.addComponent(tpOcenka, GroupLayout.PREFERRED_SIZE, 592, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(239, Short.MAX_VALUE))
		);
		gl_pOcenka.setVerticalGroup(
			gl_pOcenka.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pOcenka.createSequentialGroup()
					.addContainerGap()
					.addComponent(tpOcenka, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(389, Short.MAX_VALUE))
		);
		pOcenka.setLayout(gl_pOcenka);
		
		pzakl = new JPanel();
		tabbedPane.addTab("Заключение", null, pzakl, null);
		
		final JLabel lblzakl = new JLabel("Заключение специалиста");
		
		JEditorPane tpzakl = new JEditorPane();
		tpzakl.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		final JLabel lblrecom = new JLabel("Медицинские рекомендации");
		
		 tprecom = new JEditorPane();
		tprecom.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GroupLayout gl_pzakl = new GroupLayout(pzakl);
		gl_pzakl.setHorizontalGroup(
			gl_pzakl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pzakl.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pzakl.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pzakl.createSequentialGroup()
							.addComponent(lblzakl)
							.addContainerGap(701, Short.MAX_VALUE))
						.addGroup(gl_pzakl.createSequentialGroup()
							.addComponent(tpzakl, GroupLayout.DEFAULT_SIZE, 687, Short.MAX_VALUE)
							.addGap(144))
						.addGroup(gl_pzakl.createSequentialGroup()
							.addComponent(lblrecom)
							.addContainerGap(687, Short.MAX_VALUE))
						.addGroup(gl_pzakl.createSequentialGroup()
							.addComponent(tprecom, GroupLayout.DEFAULT_SIZE, 659, Short.MAX_VALUE)
							.addContainerGap())))
		);
		gl_pzakl.setVerticalGroup(
			gl_pzakl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pzakl.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblzakl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpzakl, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblrecom)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tprecom, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
					.addGap(255))
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
			if (diag.diag_stat == 2){//присвоить значение полей данным.вписать в табл.
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
			TabPos.setStringClassifierSelector(2, Classifiers.n_s00);
			c_obr.setData(MainForm.tcl.getP0c());
			cbrez.setData(MainForm.tcl.getAp0());
			cbish.setData(MainForm.tcl.getAq0());
			TabPos.setData(MainForm.tcl.getPvizitAmb(6));
			//TabDiag.setData(MainForm.tcl.getPdiagAmb(6));потом откомментарить
			vid_opl.setData(MainForm.tcl.getOpl());
			printform.cbVidIssl.setData(MainForm.tcl.get_n_p0e1());
			
		} catch (KmiacServerException | TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadConfig() {
		try {
			StringReader sr = new StringReader(MainForm.authInfo.config);
			StreamSource src = new StreamSource(sr);
			DOMResult res = new DOMResult();
			TransformerFactory.newInstance().newTransformer().transform(src, res);
			Document document = (Document) res.getNode();
					if (getElement(document, "jalob_dyh")!=null){
						JEditorPane tpJalobd = new JEditorPane();
						Jalob.add(tpJalobd);
											
			}
					else {
						
					}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private Element getElement(Document doc, String name){
		NodeList nls;
		nls = doc.getElementsByTagName(name);
		if (nls.getLength()>0) {
			return (Element) nls.item(0);
		}
		return null;
	}
}
