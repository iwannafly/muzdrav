package ru.nkz.ivcgzo.clientOsm;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.StringReader;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JViewport;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
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
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.clientOsm.patientInfo.Classifiers;
import ru.nkz.ivcgzo.clientOsm.patientInfo.PInfo;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.AnamZab;
import ru.nkz.ivcgzo.thriftOsm.PdiagAmb;
import ru.nkz.ivcgzo.thriftOsm.PdiagZ;
import ru.nkz.ivcgzo.thriftOsm.Priem;
import ru.nkz.ivcgzo.thriftOsm.Pvizit;
import ru.nkz.ivcgzo.thriftOsm.PvizitAmb;
import ru.nkz.ivcgzo.thriftOsm.PvizitNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.ZapVr;
import javax.swing.JComboBox;
import javax.swing.ScrollPaneConstants;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.JList;

public class Vvod extends JFrame {
	private static final long serialVersionUID = 4579259944135540676L;
	public static ZapVr zapVr;
	private static Pvizit pvizit;
	private static PvizitAmb pvizitAmb;
	private static PdiagAmb diagamb;
	private static PdiagZ pdiagz;
	private static Priem priem;
	private static AnamZab anamZab;
	private FormSign sign;
	private PrintForm printform;
	private SettingsOsm settingsosm;
	private FormPostBer postber;
	private JPanel pzakl;
	private PdiagZ dz;
	private JPanel Jalob;
	private JTextField tftemp;
	private JTextField tfad;
	private JTextField tfrost;
	private JTextField tfves;
	private JTextField tfchss;
	private JEditorPane tpJalob;
	private ShablonTextField tpJalobd;
	private ShablonTextField tpJalobkrov;
	private ShablonTextField tpJalobp;
	private ShablonTextField tpJalobmoch;
	private ShablonTextField tpJalobendo;
	private ShablonTextField tpJalobnerv;
	private ShablonTextField tpJalobopor;
	private ShablonTextField tpJaloblih;
	private ShablonTextField tpJalobobh;
	private ShablonTextField tpJalobproch;
	private ShablonTextField tpNachzab;
	private ShablonTextField tpSympt;
	private ShablonTextField tpOtnbol;
	private ShablonTextField tpPssit;
	private ShablonTextField tpObsost;
	private ShablonTextField tpKoj;
	private ShablonTextField tpSliz;
	private ShablonTextField tpPodkkl;
	private ShablonTextField tpLimf;
	private ShablonTextField tpKostmysh;
	private ShablonTextField tpNervnps;
	private ShablonTextField tpTelo;
	private ShablonTextField tpSust;
	private ShablonTextField tpDyh;
	private ShablonTextField tpGrkl;
	private ShablonTextField tpPerkl;
	private ShablonTextField tpAusl;
	private ShablonTextField tpBronho;
	private ShablonTextField tpArter;
	private ShablonTextField tpObls;
	private ShablonTextField tpPerks;
	private ShablonTextField tpAuss;
	private ShablonTextField tpPolrta;
	private ShablonTextField tpJivot;
	private ShablonTextField tpPalpjivot;
	private ShablonTextField tpJkt;
	private ShablonTextField tpPalpjel;
	private ShablonTextField tpPalppodjel;
	private ShablonTextField tpPechen;
	private ShablonTextField tpJelch;
	private ShablonTextField tpSelez;
	private ShablonTextField tpOblzad;
	private ShablonTextField tpPoyasn;
	private ShablonTextField tpPochki;
	private ShablonTextField tpMoch;
	private ShablonTextField tpMoljel;
	private ShablonTextField tpGrjel;
	private ShablonTextField tpMatka;
	private ShablonTextField tpNarpolov;
	private ShablonTextField tpChitov;
	private JEditorPane tpFizObsl;
	private JEditorPane tpLocalis;
	private JEditorPane tpOcenka;
	private JEditorPane tpIstZab;
	private ThriftStringClassifierCombobox<StringClassifier> c_obr;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cbrez;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cbish;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> vid_opl;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> vid_travm;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cbMobsp;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cbDgrup;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cbObstreg;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cbDish;
	private CustomTable<PvizitAmb, PvizitAmb._Fields> TabPos;
	private JEditorPane tpStPraes;
	private JEditorPane tprecom;
	private CustomTable<PdiagAmb,PdiagAmb._Fields> TabDiag;
	private JEditorPane tpzakl;
	private PInfo pinfo;
	private JTextField tfDvz;
	public static List<IntegerClassifier> pokNames;
	
	
	/**
	 * Create the frame.
	 */
	public Vvod() {
		try {
			pokNames = MainForm.tcl.getPokNames();
		} catch (Exception e3) {
			e3.printStackTrace();
			pokNames = new ArrayList<>();
		}
		
		 sign = new FormSign();
//		 postber = new FormPostBer();
		 settingsosm = new SettingsOsm();
		 dz = new PdiagZ();
		 printform = new PrintForm();
		setBounds(100, 100, 1029, 747);
		
		final ThriftIntegerClassifierList listShablon = new ThriftIntegerClassifierList();
		listShablon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					ShablonTextField.instance.setText(listShablon.getSelectedValue().getName());
				}
			}
		});

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
		
		
		JButton bS = new JButton("Сохранить");
		bS.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
//				diag.setDiag(tfkodmkb.getText());
//				diag.setNamed(tfname.getText());
				/*прежде чем сохранить, проделать эту операцию : diag.setNamed(tfname.getText()); со всеми нужными полями*/
				/*а потом вызывать метод из thrift,update*/
				
				try {
					priem = new Priem();
					anamZab = new AnamZab();
					priem.setId(zapVr.getId_pvizit());
					priem.setNpasp(zapVr.getNpasp());
					priem.setIdpos(pvizitAmb.getId());
					priem.setT_ad(getTextOrNull(tfad.getText()));
					priem.setT_chss(getTextOrNull(tfchss.getText()));
					priem.setT_rost(getTextOrNull(tfrost.getText()));
					priem.setT_ves(getTextOrNull(tfves.getText()));
					priem.setT_st_localis(getTextOrNull(tpLocalis.getText()));
					priem.setT_jalob_d(getTextOrNull(tpJalobd.getText()));
					priem.setT_jalob_krov(getTextOrNull(tpJalobkrov.getText()));
					priem.setT_jalob_p(getTextOrNull(tpJalobp.getText()));
					priem.setT_jalob_moch(getTextOrNull(tpJalobmoch.getText()));
					priem.setT_jalob_endo(getTextOrNull(tpJalobendo.getText()));
					priem.setT_jalob_nerv(getTextOrNull(tpJalobnerv.getText()));
					priem.setT_jalob_opor(getTextOrNull(tpJalobopor.getText()));
					priem.setT_jalob_lih(getTextOrNull(tpJaloblih.getText()));
					priem.setT_jalob_obh(getTextOrNull(tpJalobobh.getText()));
					priem.setT_jalob_proch(getTextOrNull(tpJalobproch.getText()));
					priem.setT_ob_sost(getTextOrNull(tpObsost.getText()));
					priem.setT_koj_pokr(getTextOrNull(tpKoj.getText()));
					priem.setT_sliz(getTextOrNull(tpSliz.getText()));
					priem.setT_podk_kl(getTextOrNull(tpPodkkl.getText()));
					priem.setT_limf_uzl(getTextOrNull(tpLimf.getText()));
					priem.setT_kost_mysh(getTextOrNull(tpKostmysh.getText()));
					priem.setT_nervn_ps(getTextOrNull(tpNervnps.getText()));
					priem.setT_telo(getTextOrNull(tpTelo.getText()));
					priem.setT_sust(getTextOrNull(tpSust.getText()));
					priem.setT_dyh(getTextOrNull(tpDyh.getText()));
					priem.setT_gr_kl(getTextOrNull(tpGrkl.getText()));
					priem.setT_perk_l(getTextOrNull(tpPerkl.getText()));
					priem.setT_aus_l(getTextOrNull(tpAusl.getText()));
					priem.setT_bronho(getTextOrNull(tpBronho.getText()));
					priem.setT_arter(getTextOrNull(tpArter.getText()));
					priem.setT_obl_s(getTextOrNull(tpObls.getText()));
					priem.setT_perk_s(getTextOrNull(tpPerks.getText()));
					priem.setT_aus_s(getTextOrNull(tpAuss.getText()));
					priem.setT_pol_rta(getTextOrNull(tpPolrta.getText()));
					priem.setT_jivot(getTextOrNull(tpJivot.getText()));
					priem.setT_palp_jivot(getTextOrNull(tpPalpjivot.getText()));
					priem.setT_jel_kish(getTextOrNull(tpJkt.getText()));
					priem.setT_palp_jel(getTextOrNull(tpPalpjel.getText()));
					priem.setT_palp_podjjel(getTextOrNull(tpPalppodjel.getText()));
					priem.setT_pechen(getTextOrNull(tpPechen.getText()));
					priem.setT_jelch(getTextOrNull(tpJelch.getText()));
					priem.setT_selez(getTextOrNull(tpSelez.getText()));
					priem.setT_obl_zad(getTextOrNull(tpOblzad.getText()));
					priem.setT_poyasn(getTextOrNull(tpPoyasn.getText()));
					priem.setT_pochk(getTextOrNull(tpPochki.getText()));
					priem.setT_moch(getTextOrNull(tpMoch.getText()));
					priem.setT_mol_jel(getTextOrNull(tpMoljel.getText()));
					priem.setT_gr_jel(getTextOrNull(tpGrjel.getText()));
					priem.setT_matka(getTextOrNull(tpMatka.getText()));
					priem.setT_nar_polov(getTextOrNull(tpNarpolov.getText()));
					priem.setT_chitov(getTextOrNull(tpChitov.getText()));
					priem.setT_ocenka(getTextOrNull(tpOcenka.getText()));
					priem.setT_jalob(getTextOrNull(tpJalob.getText()));
					priem.setT_status_praesense(getTextOrNull(tpStPraes.getText()));
					priem.setT_fiz_obsl(getTextOrNull(tpFizObsl.getText()));
					
					anamZab.setId_pvizit(zapVr.getId_pvizit());
					anamZab.setNpasp(zapVr.getNpasp());
					anamZab.setT_nachalo_zab(getTextOrNull(tpNachzab.getText()));
					anamZab.setT_sympt(getTextOrNull(tpSympt.getText()));
					anamZab.setT_otn_bol(getTextOrNull(tpOtnbol.getText()));
					anamZab.setT_ps_syt(getTextOrNull(tpPssit.getText()));
					
					pvizit.setZakl(getTextOrNull(tpzakl.getText()));
					pvizit.setRecomend(getTextOrNull(tprecom.getText()));
					if (c_obr.getSelectedPcod() != null)
						pvizit.setCobr(c_obr.getSelectedPcod());
					if (cbrez.getSelectedPcod() != null)
						pvizit.setRezult(cbrez.getSelectedPcod());
					if (cbish.getSelectedPcod() != null)
						pvizit.setIshod(cbish.getSelectedPcod());
					
					if (cbMobsp.getSelectedPcod() != null)
						pvizitAmb.setMobs(cbMobsp.getSelectedPcod());
					if (vid_opl.getSelectedPcod() != null)
						pvizitAmb.setOpl(vid_opl.getSelectedPcod());
					
					MainForm.tcl.setPriem(priem);
					MainForm.tcl.setAnamZab(anamZab);
					MainForm.tcl.UpdatePvizit(pvizit);
					MainForm.tcl.UpdatePvizitAmb(pvizitAmb);
				} catch (KmiacServerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				}
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
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		tabbedPane.addChangeListener(new ChangeListener() {
			List<IntegerClassifier> lstIdRazd;
			
			@Override
			public void stateChanged(ChangeEvent e) {
				try {
					JPanel selPan = (JPanel) tabbedPane.getComponentAt(tabbedPane.getSelectedIndex());
					if (selPan instanceof ShablonTextPanel) {
						lstIdRazd = MainForm.tcl.getShablonCdol(((ShablonTextPanel) selPan).getRazdId(), MainForm.authInfo.getCdol());
						disableTextFields(selPan);
					}
				} catch (KmiacServerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				System.out.println(tabbedPane.getSelectedIndex());
			}
			
			private void disableTextFields(Container c) {
				if (c.getComponentCount() > 0)
					lbl: for (int i = 0; i < c.getComponentCount(); i++) {
						if (c.getComponent(i) instanceof ShablonTextField) {
							ShablonTextField txt = (ShablonTextField)c.getComponent(i);
							for (IntegerClassifier razd : lstIdRazd) {
								if (razd.getPcod() == txt.getIdPok()) {
									txt.setVisible(true);
									continue lbl;
								}
								txt.setVisible(false);
							}
							((ShablonTextField)c.getComponent(i)).setVisible(false);
						} else if (c.getComponent(i) instanceof Container)
							disableTextFields((Container) c.getComponent(i));
					}
			}
		});
		
		 Jalob = new ShablonTextPanel(1);
		
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
		
		final JLabel lblJalob = new JLabel("Жалобы");
		
		tpJalob = new ShablonTextField(1, 1, listShablon);
		tpJalob.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpJalobd = new ShablonTextField(1, 1, listShablon);
		tpJalobd.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpJalobkrov = new ShablonTextField(1, 2, listShablon);
		tpJalobkrov.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpJalobp = new ShablonTextField(1, 3, listShablon);
		tpJalobp.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpJalobendo = new ShablonTextField(1, 5, listShablon);
		
		 tpJalobnerv = new ShablonTextField(1, 6, listShablon);
		tpJalobnerv.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpJalobopor = new ShablonTextField(1, 7, listShablon);
		tpJalobopor.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpJaloblih = new ShablonTextField(1, 8, listShablon);
		tpJaloblih.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpJalobobh = new ShablonTextField(1, 9, listShablon);
		tpJalobobh.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpJalobproch = new ShablonTextField(1, 10, listShablon);
		tpJalobproch.setBorder(UIManager.getBorder("TextField.border"));
		
		tpJalobendo.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpJalobmoch = new ShablonTextField(1, 4, listShablon);
		tpJalobmoch.setBorder(UIManager.getBorder("TextField.border"));
		
		GroupLayout gl_panJalob = new GroupLayout(panJalob);
		gl_panJalob.setHorizontalGroup(
			gl_panJalob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panJalob.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panJalob.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panJalob.createParallelGroup(Alignment.LEADING, false)
							.addComponent(tpJalob, GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
							.addComponent(lblJalob, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
							.addComponent(tpJalobd.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
							.addComponent(tpJalobkrov.getLabel(), GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
							.addComponent(tpJalobd,GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
							.addComponent(tpJalobkrov,GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE))
						.addComponent(tpJalobp.getLabel(), GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobp, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobmoch.getLabel(), GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobmoch, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobendo.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobendo, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobnerv.getLabel(), GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobnerv, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobopor.getLabel(), GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobopor, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJaloblih.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJaloblih, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobobh.getLabel(), GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobobh, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobproch.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobproch, GroupLayout.PREFERRED_SIZE, 493, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(115, Short.MAX_VALUE))
		);
		gl_panJalob.setVerticalGroup(
			gl_panJalob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panJalob.createSequentialGroup()
					.addComponent(lblJalob)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalob, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobd.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobd, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobkrov.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobkrov, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpJalobp.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobp, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpJalobmoch.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobmoch, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addComponent(tpJalobendo.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobendo, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpJalobnerv.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobnerv, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpJalobopor.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobopor, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpJaloblih.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJaloblih, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpJalobobh.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobobh, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpJalobproch.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJalobproch, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addContainerGap(22, Short.MAX_VALUE))
		);
		panJalob.setLayout(gl_panJalob);
		Jalob.setLayout(gl_Jalob);
		
		ShablonTextPanel pAnanmnesis = new ShablonTextPanel(2);
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
					.addComponent(spAnamnesis, GroupLayout.PREFERRED_SIZE, 482, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(56, Short.MAX_VALUE))
		);
		
		JPanel panAnamnes = new JPanel();
		spAnamnesis.setColumnHeaderView(panAnamnes);
		
		 tpNachzab = new ShablonTextField(2, 11, listShablon);
		tpNachzab.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpOtnbol = new ShablonTextField(2, 13, listShablon);
		tpOtnbol.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpSympt = new ShablonTextField(2, 12, listShablon);
		tpSympt.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpPssit = new ShablonTextField(2, 14, listShablon);
		tpPssit.setBorder(UIManager.getBorder("TextField.border"));
		
		JLabel lblIstZab = new JLabel("История заболевания");
		
		 tpIstZab = new JEditorPane();
		tpIstZab.setBorder(UIManager.getBorder("TextField.border"));
		GroupLayout gl_panAnamnes = new GroupLayout(panAnamnes);
		gl_panAnamnes.setHorizontalGroup(
			gl_panAnamnes.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panAnamnes.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panAnamnes.createParallelGroup(Alignment.LEADING)
						.addComponent(tpNachzab, GroupLayout.PREFERRED_SIZE, 542, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSympt.getLabel(), GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSympt, GroupLayout.PREFERRED_SIZE, 542, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpOtnbol.getLabel(), GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpOtnbol, GroupLayout.PREFERRED_SIZE, 542, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPssit, GroupLayout.PREFERRED_SIZE, 542, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpNachzab.getLabel(), GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPssit.getLabel(), GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblIstZab, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpIstZab, GroupLayout.PREFERRED_SIZE, 542, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(315, Short.MAX_VALUE))
		);
		gl_panAnamnes.setVerticalGroup(
			gl_panAnamnes.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panAnamnes.createSequentialGroup()
					.addComponent(lblIstZab)
					.addGap(6)
					.addComponent(tpIstZab, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addGap(9)
					.addComponent(tpNachzab.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpNachzab, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpSympt.getLabel())
					.addGap(6)
					.addComponent(tpSympt, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpOtnbol.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpOtnbol, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpPssit.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPssit, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(441, Short.MAX_VALUE))
		);
		panAnamnes.setLayout(gl_panAnamnes);
		pAnanmnesis.setLayout(gl_pAnanmnesis);
		
		ShablonTextPanel pStatuspr = new ShablonTextPanel(6);
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
					.addContainerGap(15, Short.MAX_VALUE))
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
		tpStPraes.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpObsost = new ShablonTextField(6, 24, listShablon);
		tpObsost.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpKoj = new ShablonTextField(6, 25, listShablon);
		tpKoj.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpSliz = new ShablonTextField(6, 26, listShablon);
		tpSliz.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpPodkkl = new ShablonTextField(6, 27, listShablon);
		tpPodkkl.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpLimf = new ShablonTextField(6, 28, listShablon);
		tpPodkkl.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpKostmysh = new ShablonTextField(6, 29, listShablon);
		tpPodkkl.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpNervnps = new ShablonTextField(6, 30, listShablon);
		 tpNervnps.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpTelo = new ShablonTextField(6, 36, listShablon);
		tpTelo.setBorder(UIManager.getBorder("TextField.border"));
		
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
						.addComponent(tpKoj.getLabel(), GroupLayout.PREFERRED_SIZE, 112, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpObsost.getLabel(), GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSliz.getLabel(), GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSliz, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPodkkl.getLabel(), GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPodkkl, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpLimf.getLabel(), GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpLimf, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpKostmysh.getLabel(), GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpKostmysh, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpNervnps.getLabel(), GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpNervnps, GroupLayout.PREFERRED_SIZE, 531, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpTelo.getLabel(), GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
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
					.addComponent(tpObsost.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpObsost, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpKoj.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpKoj, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpSliz.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpSliz, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpPodkkl.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPodkkl, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpLimf.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpLimf, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpKostmysh.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpKostmysh, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpNervnps.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpNervnps, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpTelo.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpTelo, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addContainerGap(15, Short.MAX_VALUE))
		);
		panPraesense.setLayout(gl_panPraesense);
		pStatuspr.setLayout(gl_pStatuspr);
		
		ShablonTextPanel pfizikob = new ShablonTextPanel(7);
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
		tpFizObsl.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpSust = new ShablonTextField(7, 37, listShablon);
		tpSust.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpDyh = new ShablonTextField(7, 38, listShablon);
		tpDyh.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpGrkl = new ShablonTextField(7, 39, listShablon);
		tpGrkl.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpPerkl = new ShablonTextField(7, 40, listShablon);
		tpPerkl.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpAusl = new ShablonTextField(7, 41, listShablon);
		tpAusl.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpBronho = new ShablonTextField(7, 42, listShablon);
		tpBronho.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpArter = new ShablonTextField(7, 43, listShablon);
		tpArter.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpObls = new ShablonTextField(7, 44, listShablon);
		tpObls.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpPerks = new ShablonTextField(7, 45, listShablon);
		tpPerks.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpAuss = new ShablonTextField(7, 46, listShablon);
		tpAuss.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpPolrta = new ShablonTextField(7, 47, listShablon);
		tpPolrta.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpJivot = new ShablonTextField(7, 48, listShablon);
		tpJivot.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpPalpjivot = new ShablonTextField(7, 49, listShablon);
		tpPalpjivot.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpJkt = new ShablonTextField(7, 50, listShablon);
		tpJkt.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpPalpjel = new ShablonTextField(7, 51, listShablon);
		tpPalpjel.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpPalppodjel = new ShablonTextField(7, 52, listShablon);
		tpPalppodjel.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpPechen = new ShablonTextField(7, 53, listShablon);
		tpPechen.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpJelch = new ShablonTextField(7, 54, listShablon);
		tpJelch.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpSelez = new ShablonTextField(7, 55, listShablon);
		tpSelez.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpOblzad = new ShablonTextField(7, 56, listShablon);
		tpOblzad.setBorder(UIManager.getBorder("TextField.border"));
				
		 tpPoyasn = new ShablonTextField(7, 57, listShablon);
		tpPoyasn.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpPochki = new ShablonTextField(7, 58, listShablon);
		tpPochki.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpMoch = new ShablonTextField(7, 59, listShablon);
		tpMoch.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpMoljel = new ShablonTextField(7, 60, listShablon);
		tpMoljel.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpGrjel = new ShablonTextField(7, 61, listShablon);
		tpGrjel.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpMatka = new ShablonTextField(7, 62, listShablon);
		tpMatka.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpNarpolov = new ShablonTextField(7, 63, listShablon);
		tpNarpolov.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpChitov = new ShablonTextField(7, 64, listShablon);
		tpChitov.setBorder(UIManager.getBorder("TextField.border"));
		
		GroupLayout gl_panFiz = new GroupLayout(panFiz);
		gl_panFiz.setHorizontalGroup(
			gl_panFiz.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panFiz.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panFiz.createParallelGroup(Alignment.LEADING)
						.addComponent(tpFizObsl, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSust.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSust, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpDyh.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpDyh, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpGrkl.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpGrkl, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPerkl.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPerkl, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpAusl.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpAusl, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpBronho.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpBronho, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpArter.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpArter, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpObls.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpObls, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPerks.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPerks, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpAuss.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpAuss, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPolrta.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPolrta, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJivot.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJivot, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPalpjivot.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPalpjivot, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJkt.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJkt, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPalpjel.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPalpjel, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPalppodjel.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPalppodjel, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPechen.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPechen, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJelch.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJelch, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSelez.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSelez, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpOblzad.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpOblzad, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPoyasn.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPoyasn, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPochki.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPochki, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpMoch.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpMoch, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpMoljel.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpMoljel, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpGrjel.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpGrjel, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpMatka.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpMatka, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpNarpolov.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpNarpolov, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpChitov.getLabel(), GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpChitov, GroupLayout.PREFERRED_SIZE, 492, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblfizik, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(325, Short.MAX_VALUE))
		);
		gl_panFiz.setVerticalGroup(
			gl_panFiz.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panFiz.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblfizik)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpFizObsl, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpSust.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpSust, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpDyh.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpDyh, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpGrkl.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpGrkl, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpPerkl.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPerkl, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpAusl.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpAusl, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpBronho.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpBronho, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpArter.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpArter, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpObls.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpObls, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpPerks.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPerks, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpAuss.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpAuss, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpPolrta.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPolrta, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpJivot.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJivot, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpPalpjivot.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPalpjivot, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpJkt.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJkt, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpPalpjel.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPalpjel, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpPalppodjel.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPalppodjel, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpPechen.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPechen, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpJelch.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpJelch, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpSelez.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpSelez, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpOblzad.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpOblzad, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpPoyasn.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPoyasn, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpPochki.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpPochki, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpMoch.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpMoch, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpMoljel.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpMoljel, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpGrjel.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpGrjel, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpMatka.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpMatka, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpNarpolov.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpNarpolov, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpChitov.getLabel())
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpChitov, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(15, Short.MAX_VALUE))
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
		
		cbMobsp = new ThriftIntegerClassifierCombobox<>(true);
		
		JLabel lblmobs = new JLabel("Место");
		 
		GroupLayout gl_panel_Talon = new GroupLayout(panel_Talon);
		gl_panel_Talon.setHorizontalGroup(
			gl_panel_Talon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_Talon.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_Talon.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_Talon.createSequentialGroup()
							.addComponent(lblcobr)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(c_obr, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_Talon.createSequentialGroup()
							.addComponent(lblvid_opl)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(vid_opl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_Talon.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_Talon.createSequentialGroup()
							.addComponent(lblrez, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(cbrez, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_Talon.createSequentialGroup()
							.addComponent(lblish, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbish, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
					.addGap(18)
					.addComponent(lblmobs, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(cbMobsp, GroupLayout.PREFERRED_SIZE, 223, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_Talon.setVerticalGroup(
			gl_panel_Talon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_Talon.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_panel_Talon.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_Talon.createSequentialGroup()
							.addGap(3)
							.addComponent(lblmobs))
						.addComponent(cbMobsp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
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
		
		JScrollPane sPos = new JScrollPane();
		
		JButton bDB = new JButton("Наблюдение за берем.");
		bDB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				postber.setVisible(true);
			}
		});
		
		JButton AddVizit = new JButton("+");
		AddVizit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pvizit = new Pvizit();
				pvizit.setId(zapVr.getId_pvizit());
				pvizit.setNpasp(zapVr.getNpasp());
				pvizit.setCpol(MainForm.authInfo.getCpodr());
				pvizit.setDatao(System.currentTimeMillis());
				pvizit.setCod_sp(MainForm.authInfo.getPcod());
				pvizit.setCdol(MainForm.authInfo.getCdol());
				pvizit.setCuser(MainForm.authInfo.getUser_id());
				pvizit.setDataz(System.currentTimeMillis());
				
				pvizitAmb = new PvizitAmb();
				pvizitAmb.setId_obr(pvizit.getId());
				pvizitAmb.setNpasp(zapVr.getNpasp());
				pvizitAmb.setDatap(System.currentTimeMillis());
				pvizitAmb.setCod_sp(MainForm.authInfo.getPcod());
				pvizitAmb.setCdol(MainForm.authInfo.getCdol());
				
				try {
					Vvod.pvizit = MainForm.tcl.getPvizit(pvizit.getId());
					pvizitAmb.setId(MainForm.tcl.AddPvizitAmb(pvizitAmb));
					TabPos.setData(MainForm.tcl.getPvizitAmb(pvizit.getId()));
					TabPos.setRowSelectionInterval(TabPos.getRowCount() - 1, TabPos.getRowCount() - 1);
				} catch (KmiacServerException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				} catch (PvizitNotFoundException e2) {
					try {
						MainForm.tcl.AddPvizit(pvizit);
						pvizitAmb.setId(MainForm.tcl.AddPvizitAmb(pvizitAmb));
						TabPos.setData(MainForm.tcl.getPvizitAmb(pvizit.getId()));
						TabPos.setRowSelectionInterval(TabPos.getRowCount() - 1, TabPos.getRowCount() - 1);
					} catch (KmiacServerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TException e) {
						MainForm.conMan.reconnect(e);
						e.printStackTrace();
					}
				} catch (TException e2) {
					MainForm.conMan.reconnect(e2);
					e2.printStackTrace();
				}
			}
		});
		
		JButton bInfo = new JButton("Просмотр");
		bInfo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					MainForm.tcl.testConnection();
					pinfo = new PInfo();
					pinfo.setVisible(true);
				} catch (TException e1) {
					MainForm.conMan.reconnect(e1);
				}}
		});
		
		JScrollPane spShab = new JScrollPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(bDB, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnAnamz, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(bInfo, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(bS)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(bSSh)
							.addGap(10)
							.addComponent(bZSh)
							.addGap(10)
							.addComponent(bVD)
							.addGap(6)
							.addComponent(bDelet)
							.addGap(3181))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(sPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(AddVizit)
							.addContainerGap(3577, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(Alignment.LEADING, gl_panel.createSequentialGroup()
									.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 683, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(spShab))
								.addComponent(panel_Talon, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addContainerGap())))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(11)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
									.addComponent(btnAnamz)
									.addComponent(bS)
									.addComponent(bInfo))
								.addComponent(bSSh))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(bDB)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(sPos, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
								.addComponent(AddVizit)))
						.addComponent(bDelet)
						.addComponent(bZSh)
						.addComponent(bVD))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(panel_Talon, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 495, GroupLayout.PREFERRED_SIZE)
						.addComponent(spShab, GroupLayout.PREFERRED_SIZE, 464, GroupLayout.PREFERRED_SIZE))
					.addGap(198))
		);
		
		spShab.setViewportView(listShablon);
		
		TabPos = new CustomTable<>(false,false,PvizitAmb.class,3,"Дата",19,"ФИО врача",5,"Должность");
		TabPos.setDateField(0);
		//TabPos.add
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
					if (TabPos.getSelectedItem()!= null) {
						pvizitAmb = TabPos.getSelectedItem();
//						try {
//							Priem pri = MainForm.tcl.getPriem(TabPos.getSelectedItem().id_obr,TabPos.getSelectedItem().npasp,TabPos.getSelectedItem().id);
//							if (pri.getT_jalob()!=null){
//							lbljalob.setVisible(true);
//							tpJalob.setVisible(true);
//							tpJalob.setText(pri.getT_jalob());	
//							}else{
//							tpJalob.setVisible(false);
//							lbljalob.setVisible(false);
//							}
//							if (pri.getT_jalob_d()!=null){
//							lbljald.setVisible(true);
//							tpJalobd.setVisible(true);
//							tpJalobd.setText(pri.getT_jalob_d());	
//							}else{
//							tpJalobd.setVisible(false);
//							lbljald.setVisible(false);
//							}
//							if (pri.getT_jalob_krov()!=null){
//							lbljalkr.setVisible(true);
//							tpJalobkrov.setVisible(true);
//							tpJalobkrov.setText(pri.getT_jalob_krov());	
//							}else{
//							tpJalobkrov.setVisible(false);
//							lbljalkr.setVisible(false);
//							}
//							if (pri.getT_jalob_p()!=null){
//							lbljalp.setVisible(true);
//							tpJalobp.setVisible(true);
//							tpJalobp.setText(pri.getT_jalob_p());	
//							}else{
//							tpJalobp.setVisible(false);
//							lbljalp.setVisible(false);
//							}
//							if (pri.getT_jalob_moch()!=null){
//							lbljalm.setVisible(true);
//							tpJalobmoch.setVisible(true);
//							tpJalobmoch.setText(pri.getT_jalob_moch());	
//							}else{
//							tpJalobmoch.setVisible(false);
//							lbljalm.setVisible(false);
//							}
//							if (pri.getT_jalob_endo()!=null){
//							lbljalendo.setVisible(true);
//							tpJalobendo.setVisible(true);
//							tpJalobendo.setText(pri.getT_jalob_endo());	
//							}else{
//							tpJalobendo.setVisible(false);
//							lbljalendo.setVisible(false);
//							}
//							if (pri.getT_jalob_nerv()!=null){
//							lbljalnerv.setVisible(true);
//							tpJalobnerv.setVisible(true);
//							tpJalobnerv.setText(pri.getT_jalob_nerv());	
//							}else{
//							tpJalobnerv.setVisible(false);
//							lbljalnerv.setVisible(false);
//							}
//							if (pri.getT_jalob_opor()!=null){
//							lbljalop.setVisible(true);
//							tpJalobopor.setVisible(true);
//							tpJalobopor.setText(pri.getT_jalob_opor());	
//							}else{
//							tpJalobopor.setVisible(false);
//							lbljalop.setVisible(false);
//							}
//							if (pri.getT_jalob_lih()!=null){
//							lbljallih.setVisible(true);
//							tpJaloblih.setVisible(true);
//							tpJaloblih.setText(pri.getT_jalob_lih());	
//							}else{
//							tpJaloblih.setVisible(false);
//							lbljallih.setVisible(false);
//							}
//							if (pri.getT_jalob_obh()!=null){
//							lblja_lob.setVisible(true);
//							tpJalobobh.setVisible(true);
//							tpJalobobh.setText(pri.getT_jalob_obh());	
//							}else{
//							tpJalobobh.setVisible(false);
//							lblja_lob.setVisible(false);
//							}
//							if (pri.getT_jalob_proch()!=null){
//							lbljalpr.setVisible(true);
//							tpJalobproch.setVisible(true);
//							tpJalobproch.setText(pri.getT_jalob_proch());	
//							}else{
//							tpJalobproch.setVisible(false);
//							lbljalpr.setVisible(false);
//							}
//							if (pri.getT_nachalo_zab()!=null){
//							lblNacZab.setVisible(true);
//							tpNachzab.setVisible(true);
//							tpNachzab.setText(pri.getT_nachalo_zab());	
//							}else{
//							tpNachzab.setVisible(false);
//							lblNacZab.setVisible(false);
//							}
//							if (pri.getT_sympt()!=null){
//							lblSympt.setVisible(true);
//							tpSympt.setVisible(true);
//							tpSympt.setText(pri.getT_sympt());	
//							}else{
//							tpSympt.setVisible(false);
//							lblSympt.setVisible(false);
//							}
//							if (pri.getT_otn_bol()!=null){
//							lblotnbol.setVisible(true);
//							tpOtnbol.setVisible(true);
//							tpOtnbol.setText(pri.getT_sympt());	
//							}else{
//							tpOtnbol.setVisible(false);
//							lblotnbol.setVisible(false);
//							}
//							if (pri.getT_ps_syt()!=null){
//							lblpssit.setVisible(true);
//							tpPssit.setVisible(true);
//							tpPssit.setText(pri.getT_ps_syt());	
//							}else{
//							tpPssit.setVisible(false);
//							lblpssit.setVisible(false);
//							}
//							if (pri.getT_ad()!=null){
//							lblad.setVisible(true);
//							tfad.setVisible(true);
//							tfad.setText(pri.getT_ad());	
//							}else{
//							tfad.setVisible(false);
//							lblad.setVisible(false);
//							}
//							if (pri.getT_temp()!=null){
//							lbltemp.setVisible(true);
//							tftemp.setVisible(true);
//							tftemp.setText(pri.getT_temp());	
//							}else{
//							tftemp.setVisible(false);
//							lbltemp.setVisible(false);
//							}
//							if (pri.getT_chss()!=null){
//							lblchss.setVisible(true);
//							tfchss.setVisible(true);
//							tfchss.setText(pri.getT_chss());	
//							}else{
//							tfchss.setVisible(false);
//							lblchss.setVisible(false);
//							}
//							if (pri.getT_rost()!=null){
//							lblrost.setVisible(true);
//							tfrost.setVisible(true);
//							tfrost.setText(pri.getT_rost());	
//							}else{
//							tfrost.setVisible(false);
//							lblrost.setVisible(false);
//							}
//							if (pri.getT_ves()!=null){
//							lblves.setVisible(true);
//							tfves.setVisible(true);
//							tfves.setText(pri.getT_ves());	
//							}else{
//							tfves.setVisible(false);
//							lblves.setVisible(false);
//							}
//							if (pri.getT_ob_sost()!=null){
//							lblobsost.setVisible(true);
//							tpObsost.setVisible(true);
//							tpObsost.setText(pri.getT_ob_sost());	
//							}else{
//							tpObsost.setVisible(false);
//							lblobsost.setVisible(false);
//							}
//							if (pri.getT_koj_pokr()!=null){
//							lblkoj.setVisible(true);
//							tpKoj.setVisible(true);
//							tpKoj.setText(pri.getT_koj_pokr());	
//							}else{
//							tpKoj.setVisible(false);
//							lblkoj.setVisible(false);
//							}
//							if (pri.getT_sliz()!=null){
//							lblsl.setVisible(true);
//							tpSliz.setVisible(true);
//							tpSliz.setText(pri.getT_sliz());	
//							}else{
//							tpSliz.setVisible(false);
//							lblsl.setVisible(false);
//							}
//							if (pri.getT_podk_kl()!=null){
//							lblpodkkl.setVisible(true);
//							tpPodkkl.setVisible(true);
//							tpPodkkl.setText(pri.getT_podk_kl());	
//							}else{
//							tpPodkkl.setVisible(false);
//							lblpodkkl.setVisible(false);
//							}
//							if (pri.getT_limf_uzl()!=null){
//							lbllimf.setVisible(true);
//							tpLimf.setVisible(true);
//							tpLimf.setText(pri.getT_limf_uzl());	
//							}else{
//							tpLimf.setVisible(false);
//							lbllimf.setVisible(false);
//							}
//							if (pri.getT_kost_mysh()!=null){
//							lblkostm.setVisible(true);
//							tpKostmysh.setVisible(true);
//							tpKostmysh.setText(pri.getT_kost_mysh());	
//							}else{
//							tpKostmysh.setVisible(false);
//							lblkostm.setVisible(false);
//							}
//							if (pri.getT_nervn_ps()!=null){
//							lblnervps.setVisible(true);
//							tpNervnps.setVisible(true);
//							tpNervnps.setText(pri.getT_nervn_ps());	
//							}else{
//							tpNervnps.setVisible(false);
//							lblnervps.setVisible(false);
//							}
//							if (pri.getT_sust()!=null){
//							lblsust.setVisible(true);
//							tpSust.setVisible(true);
//							tpSust.setText(pri.getT_sust());	
//							}else{
//							tpSust.setVisible(false);
//							lblsust.setVisible(false);
//							}
//							if (pri.getT_dyh()!=null){
//							lbldyh.setVisible(true);
//							tpDyh.setVisible(true);
//							tpDyh.setText(pri.getT_sust());	
//							}else{
//							tpDyh.setVisible(false);
//							lbldyh.setVisible(false);
//							}
//							if (pri.getT_gr_kl()!=null){
//							lblgrkl.setVisible(true);
//							tpGrkl.setVisible(true);
//							tpGrkl.setText(pri.getT_sust());	
//							}else{
//							tpGrkl.setVisible(false);
//							lblgrkl.setVisible(false);
//							}
//							if (pri.getT_perk_l()!=null){
//							lblperl.setVisible(true);
//							tpPerkl.setVisible(true);
//							tpPerkl.setText(pri.getT_perk_l());	
//							}else{
//							tpPerkl.setVisible(false);
//							lblperl.setVisible(false);
//							}
//							if (pri.getT_aus_l()!=null){
//							lblausl.setVisible(true);
//							tpAusl.setVisible(true);
//							tpAusl.setText(pri.getT_aus_l());	
//							}else{
//							tpAusl.setVisible(false);
//							lblausl.setVisible(false);
//							}
//							if (pri.getT_bronho()!=null){
//							lblbronh.setVisible(true);
//							tpBronho.setVisible(true);
//							tpBronho.setText(pri.getT_bronho());	
//							}else{
//							tpBronho.setVisible(false);
//							lblbronh.setVisible(false);
//							}
//							if (pri.getT_arter()!=null){
//							lblarter.setVisible(true);
//							tpArter.setVisible(true);
//							tpArter.setText(pri.getT_arter());	
//							}else{
//							tpArter.setVisible(false);
//							lblarter.setVisible(false);
//							}
//							if (pri.getT_obl_s()!=null){
//							lblobls.setVisible(true);
//							tpObls.setVisible(true);
//							tpObls.setText(pri.getT_obl_s());	
//							}else{
//							tpObls.setVisible(false);
//							lblobls.setVisible(false);
//							}
//							if (pri.getT_perk_s()!=null){
//							lblpers.setVisible(true);
//							tpPerks.setVisible(true);
//							tpPerks.setText(pri.getT_perk_s());	
//							}else{
//							tpPerks.setVisible(false);
//							lblpers.setVisible(false);
//							}
//							if (pri.getT_aus_s()!=null){
//							lblauss.setVisible(true);
//							tpAuss.setVisible(true);
//							tpAuss.setText(pri.getT_aus_s());	
//							}else{
//							tpAuss.setVisible(false);
//							lblauss.setVisible(false);
//							}
//							if (pri.getT_pol_rta()!=null){
//							lblpolrta.setVisible(true);
//							tpPolrta.setVisible(true);
//							tpPolrta.setText(pri.getT_pol_rta());	
//							}else{
//							tpPolrta.setVisible(false);
//							lblpolrta.setVisible(false);
//							}
//							if (pri.getT_jivot()!=null){
//							lbljivot.setVisible(true);
//							tpJivot.setVisible(true);
//							tpJivot.setText(pri.getT_jivot());	
//							}else{
//							tpJivot.setVisible(false);
//							lbljivot.setVisible(false);
//							}
//							if (pri.getT_palp_jivot()!=null){
//							lblpalpjiv.setVisible(true);
//							tpPalpjivot.setVisible(true);
//							tpPalpjivot.setText(pri.getT_palp_jivot());	
//							}else{
//							tpPalpjivot.setVisible(false);
//							lblpalpjiv.setVisible(false);
//							}
//							if (pri.getT_jel_kish()!=null){
//							lbljkt.setVisible(true);
//							tpJkt.setVisible(true);
//							tpJkt.setText(pri.getT_jel_kish());	
//							}else{
//							tpJkt.setVisible(false);
//							lbljkt.setVisible(false);
//							}
//							if (pri.getT_palp_jel()!=null){
//							lblpalpjel.setVisible(true);
//							tpPalpjel.setVisible(true);
//							tpPalpjel.setText(pri.getT_palp_jel());	
//							}else{
//							tpPalpjel.setVisible(false);
//							lblpalpjel.setVisible(false);
//							}
//							if (pri.getT_palp_podjel()!=null){
//							lblpalppodjel.setVisible(true);
//							tpPalppodjel.setVisible(true);
//							tpPalppodjel.setText(pri.getT_palp_podjel());	
//							}else{
//							tpPalppodjel.setVisible(false);
//							lblpalppodjel.setVisible(false);
//							}
//							if (pri.getT_pechen()!=null){
//							lblpech.setVisible(true);
//							tpPechen.setVisible(true);
//							tpPechen.setText(pri.getT_pechen());	
//							}else{
//							tpPechen.setVisible(false);
//							lblpech.setVisible(false);
//							}
//							if (pri.getT_jelch()!=null){
//							lbljelch.setVisible(true);
//							tpJelch.setVisible(true);
//							tpJelch.setText(pri.getT_jelch());	
//							}else{
//							tpJelch.setVisible(false);
//							lbljelch.setVisible(false);
//							}
//							if (pri.getT_selez()!=null){
//							lblselez.setVisible(true);
//							tpSelez.setVisible(true);
//							tpSelez.setText(pri.getT_selez());	
//							}else{
//							tpSelez.setVisible(false);
//							lblselez.setVisible(false);
//							}
//							if (pri.getT_obl_zad()!=null){
//							lbloblzad.setVisible(true);
//							tpOblzad.setVisible(true);
//							tpOblzad.setText(pri.getT_obl_zad());	
//							}else{
//							tpOblzad.setVisible(false);
//							lbloblzad.setVisible(false);
//							}
//							if (pri.getT_poyasn()!=null){
//							lblpoyasn.setVisible(true);
//							tpPoyasn.setVisible(true);
//							tpPoyasn.setText(pri.getT_poyasn());	
//							}else{
//							tpPoyasn.setVisible(false);
//							lblpoyasn.setVisible(false);
//							}
//							if (pri.getT_pochk() !=null){
//							lblpochk.setVisible(true);
//							tpPochki.setVisible(true);
//							tpPochki.setText(pri.getT_pochk());	
//							}else{
//							tpPochki.setVisible(false);
//							lblpochk.setVisible(false);
//							}
//							if (pri.getT_moch() !=null){
//							lblmoch.setVisible(true);
//							tpMoch.setVisible(true);
//							tpMoch.setText(pri.getT_moch());	
//							}else{
//							tpMoch.setVisible(false);
//							lblmoch.setVisible(false);
//							}
//							if (pri.getT_mol_jel() !=null){
//							lblmoljel.setVisible(true);
//							tpMoljel.setVisible(true);
//							tpMoljel.setText(pri.getT_mol_jel());	
//							}else{
//							tpMoljel.setVisible(false);
//							lblmoljel.setVisible(false);
//							}
//							if (pri.getT_gr_jel() !=null){
//							lblgrjel.setVisible(true);
//							tpGrjel.setVisible(true);
//							tpGrjel.setText(pri.getT_gr_jel());	
//							}else{
//							tpGrjel.setVisible(false);
//							lblgrjel.setVisible(false);
//							}
//							if (pri.getT_matka() !=null){
//							lblmatka.setVisible(true);
//							tpMatka.setVisible(true);
//							tpMatka.setText(pri.getT_matka());	
//							}else{
//							tpMatka.setVisible(false);
//							lblmatka.setVisible(false);
//							}
//							if (pri.getT_nar_polov() !=null){
//							lblnarpolov.setVisible(true);
//							tpNarpolov.setVisible(true);
//							tpNarpolov.setText(pri.getT_nar_polov());	
//							}else{
//							tpNarpolov.setVisible(false);
//							lblnarpolov.setVisible(false);
//							}
//							if (pri.getT_chitov() !=null){
//							lblchitov.setVisible(true);
//							tpChitov.setVisible(true);
//							tpChitov.setText(pri.getT_chitov());	
//							}else{
//							tpChitov.setVisible(false);
//							lblchitov.setVisible(false);
//							}
//							if (pri.getT_st_localis() !=null){
//							tpLocalis.setVisible(true);
//							tpLocalis.setText(pri.getT_st_localis());	
//							}else{
//							tpLocalis.setVisible(false);
//							}
//							if (pri.getT_ocenka() !=null){
//							tpOcenka.setVisible(true);
//							tpOcenka.setText(pri.getT_ocenka());	
//							}else{
//							tpOcenka.setVisible(false);
//							}
//							} catch (KmiacServerException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							} catch (PriemNotFoundException e) {
//								// TODO Auto-generated catch block
//								e.printStackTrace();
//							} catch (TException e) {
//								// TODO Auto-generated catch block
//								MainForm.conMan.reconnect(e);
//							}
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
		TabDiag = new CustomTable<>(false,true,PdiagAmb.class,7,"Дата",3,"Код МКБ",4,"Описание");
		TabDiag.setDateField(0);
		spDiag.setViewportView(TabDiag);
		TabDiag.setFillsViewportHeight(true);
		
		 vid_travm = new ThriftIntegerClassifierCombobox<>(true);
		
		JLabel lblvidtravm = new JLabel("Вид травмы");
		
		JButton bAddDiag = new JButton("+");
		bAddDiag.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				diagamb = new PdiagAmb();
				diagamb.setId_obr(zapVr.getId_pvizit());
				diagamb.setNpasp(zapVr.getNpasp());
//				diagamb.setDiag(tf);
//				pvizitAmb.setDatap(System.currentTimeMillis());
//				pvizitAmb.setCod_sp(MainForm.authInfo.getPcod());
//				pvizitAmb.setCdol(MainForm.authInfo.getCdol());
//				

			}	

		});
		
		JLabel lblObstreg = new JLabel("Обстоятельства регистрации");
		
		 cbObstreg = new ThriftIntegerClassifierCombobox<>(true);;
		
		JPanel pStady = new JPanel();
		pStady.setBorder(new TitledBorder(null, "\u0421\u0442\u0430\u0434\u0438\u044F \u0437\u0430\u0431\u043E\u043B\u0435\u0432\u0430\u043D\u0438\u044F", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		ButtonGroup GBoxStady = new ButtonGroup();
		JRadioButton rbRan = new JRadioButton("Ранняя", false);
		
		JRadioButton rbPoz = new JRadioButton("Поздняя", false);
		pStady.add(rbRan);
		pStady.add(rbPoz);
		GBoxStady.add(rbRan);
		GBoxStady.add(rbPoz);
		
		JPanel pXzab = new JPanel();
		pXzab.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0425\u0430\u0440\u0430\u043A\u0442\u0435\u0440 \u0437\u0430\u0431\u043E\u043B\u0435\u0432\u0430\u043D\u0438\u044F", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		ButtonGroup GBoxHar = new ButtonGroup();
		JRadioButton rbOstr = new JRadioButton("Острое", false);
		pXzab.add(rbOstr);
		
		JRadioButton rbHron = new JRadioButton("Хроническое", false);
		pXzab.add(rbHron);
		GBoxHar.add(rbOstr);
		GBoxHar.add(rbHron);
		
		JPanel pDisp = new JPanel();
		pDisp.setBorder(new TitledBorder(null, "\u0414\u0438\u0441\u043F\u0430\u043D\u0441\u0435\u0440\u043D\u044B\u0439 \u0443\u0447\u0435\u0442", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		
		JCheckBox cbPatol = new JCheckBox("Патология, имеющая абсолютные противопоказания к вынашиванию беременности");
		
		JCheckBox cbPriznb = new JCheckBox("Связь с участием в боевых действиях");
		
		JCheckBox cbPrizni = new JCheckBox("Инвалидность");
		
		JButton bSaveDiag = new JButton("v");
		GroupLayout gl_pds = new GroupLayout(pds);
		gl_pds.setHorizontalGroup(
			gl_pds.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pds.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pds.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pds.createSequentialGroup()
							.addComponent(cbPrizni)
							.addContainerGap())
						.addGroup(gl_pds.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_pds.createSequentialGroup()
								.addComponent(cbPriznb)
								.addContainerGap())
							.addGroup(gl_pds.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pds.createSequentialGroup()
									.addComponent(cbPatol, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
									.addContainerGap())
								.addGroup(gl_pds.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_pds.createSequentialGroup()
										.addComponent(pDisp, GroupLayout.PREFERRED_SIZE, 458, GroupLayout.PREFERRED_SIZE)
										.addContainerGap())
									.addGroup(gl_pds.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_pds.createSequentialGroup()
											.addComponent(spDiag, GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE)
											.addGap(17)
											.addGroup(gl_pds.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_pds.createSequentialGroup()
													.addComponent(bAddDiag)
													.addGap(9))
												.addGroup(gl_pds.createSequentialGroup()
													.addComponent(bSaveDiag, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
													.addContainerGap())))
										.addGroup(gl_pds.createSequentialGroup()
											.addGroup(gl_pds.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_pds.createSequentialGroup()
													.addComponent(pvidd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
													.addGap(18)
													.addComponent(ppredv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_pds.createSequentialGroup()
													.addComponent(pStady, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
													.addGap(18)
													.addComponent(pXzab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(lblObstreg)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(cbObstreg, GroupLayout.PREFERRED_SIZE, 278, GroupLayout.PREFERRED_SIZE)))
											.addContainerGap(30, Short.MAX_VALUE))
										.addGroup(gl_pds.createSequentialGroup()
											.addComponent(lblvidtravm)
											.addGap(18)
											.addComponent(vid_travm, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE)
											.addGap(467))))))))
		);
		gl_pds.setVerticalGroup(
			gl_pds.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pds.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_pds.createParallelGroup(Alignment.BASELINE)
						.addGroup(gl_pds.createSequentialGroup()
							.addComponent(bAddDiag)
							.addGap(14)
							.addComponent(bSaveDiag))
						.addComponent(spDiag, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pds.createParallelGroup(Alignment.TRAILING)
						.addComponent(pvidd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(ppredv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_pds.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pds.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_pds.createParallelGroup(Alignment.LEADING)
								.addComponent(pStady, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(pXzab, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_pds.createSequentialGroup()
							.addGap(24)
							.addGroup(gl_pds.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblObstreg)
								.addComponent(cbObstreg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pds.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblvidtravm)
						.addComponent(vid_travm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(pDisp, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(cbPatol, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(cbPriznb)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(cbPrizni)
					.addContainerGap(46, Short.MAX_VALUE))
		);
		
		JLabel lblDish = new JLabel("Исход д/у");
		
		 cbDish = new ThriftIntegerClassifierCombobox<>(true);
		
		JLabel lblDvz = new JLabel("Дата взятия на д/у");
		
		tfDvz = new JTextField();
		tfDvz.setColumns(10);
		
		JLabel lblDGrup = new JLabel("Группа д/у");
		
		 cbDgrup = new ThriftIntegerClassifierCombobox<>(true);
		GroupLayout gl_pDisp = new GroupLayout(pDisp);
		gl_pDisp.setHorizontalGroup(
			gl_pDisp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pDisp.createSequentialGroup()
					.addGroup(gl_pDisp.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pDisp.createSequentialGroup()
							.addComponent(lblDvz)
							.addGap(4)
							.addComponent(tfDvz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pDisp.createSequentialGroup()
							.addComponent(lblDGrup)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbDgrup, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pDisp.createSequentialGroup()
							.addComponent(lblDish)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbDish, GroupLayout.PREFERRED_SIZE, 350, GroupLayout.PREFERRED_SIZE)))
					.addGap(27))
		);
		gl_pDisp.setVerticalGroup(
			gl_pDisp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pDisp.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pDisp.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pDisp.createSequentialGroup()
							.addGap(14)
							.addComponent(lblDvz))
						.addGroup(gl_pDisp.createSequentialGroup()
							.addGap(11)
							.addComponent(tfDvz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_pDisp.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDGrup)
						.addComponent(cbDgrup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pDisp.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDish)
						.addComponent(cbDish, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(86, Short.MAX_VALUE))
		);
		pDisp.setLayout(gl_pDisp);
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
		
		pzakl = new ShablonTextPanel(10);
		tabbedPane.addTab("Заключение", null, pzakl, null);
		
		final JLabel lblzakl = new JLabel("Заключение специалиста");
		
		tpzakl = new JEditorPane();
		tpzakl.setBorder(UIManager.getBorder("TextField.border"));
		
		final JLabel lblrecom = new JLabel("Медицинские рекомендации");
		
		 tprecom = new JEditorPane();
		tprecom.setBorder(UIManager.getBorder("TextField.border"));
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
							.addComponent(lblrecom)
							.addContainerGap(687, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_pzakl.createSequentialGroup()
							.addGroup(gl_pzakl.createParallelGroup(Alignment.TRAILING)
								.addComponent(tpzakl, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE)
								.addComponent(tprecom))
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
	
	public void showVvod(ZapVr zapVr) {
		Vvod.zapVr = zapVr;
				
		try {
			Vvod.pvizit = MainForm.tcl.getPvizit(zapVr.getId_pvizit());
			TabPos.setData(MainForm.tcl.getPvizitAmb(zapVr.getId_pvizit()));
			TabPos.setRowSelectionInterval(TabPos.getRowCount() - 1, TabPos.getRowCount() - 1);
		} catch (KmiacServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
			MainForm.conMan.reconnect(e);
		} catch (PvizitNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setVisible(true);
	}
	
	public void onConnect() {
		try {
			TabPos.setStringClassifierSelector(2, Classifiers.n_s00);
			c_obr.setData(MainForm.tcl.getP0c());
			cbrez.setData(MainForm.tcl.getAp0());
			cbish.setData(MainForm.tcl.getAq0());
			cbMobsp.setData(MainForm.tcl.get_n_abs());
			cbObstreg.setData(MainForm.tcl.get_n_abv());
			cbDgrup.setData(MainForm.tcl.get_n_abc());
			cbDish.setData(MainForm.tcl.get_n_abb());
			vid_opl.setData(MainForm.tcl.getOpl());
			vid_travm.setData(MainForm.tcl.get_n_ai0());
			printform.cbVidIssl.setData(MainForm.tcl.get_n_p0e1());
			printform.cbMesto.setData(MainForm.tcl.get_n_lds(MainForm.authInfo.clpu));
			
			
		} catch (KmiacServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
			MainForm.conMan.reconnect(e);
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
	
	private String getTextOrNull(String str) {
		if (str != null)
			if (str.length() > 0)
				return str;
		
		return null;
	}
}
