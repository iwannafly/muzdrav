package ru.nkz.ivcgzo.clientOsm;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
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
import ru.nkz.ivcgzo.thriftOsm.IsslMet;
import ru.nkz.ivcgzo.thriftOsm.IsslPokaz;
import ru.nkz.ivcgzo.thriftOsm.Metod;
import ru.nkz.ivcgzo.thriftOsm.Napr;
import ru.nkz.ivcgzo.thriftOsm.NaprKons;
import ru.nkz.ivcgzo.thriftOsm.PdiagAmb;
import ru.nkz.ivcgzo.thriftOsm.PdiagNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.PdiagZ;
import ru.nkz.ivcgzo.thriftOsm.Pdisp;
import ru.nkz.ivcgzo.thriftOsm.PdispNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Pokaz;
import ru.nkz.ivcgzo.thriftOsm.PokazMet;
import ru.nkz.ivcgzo.thriftOsm.Priem;
import ru.nkz.ivcgzo.thriftOsm.PriemNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Protokol;
import ru.nkz.ivcgzo.thriftOsm.Pvizit;
import ru.nkz.ivcgzo.thriftOsm.PvizitAmb;
import ru.nkz.ivcgzo.thriftOsm.PvizitNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.ZapVr;
import javax.swing.ScrollPaneConstants;
import javax.swing.JTable;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.border.LineBorder;

public class Vvod extends JFrame {
	private static final long serialVersionUID = 4579259944135540676L;
	public static ZapVr zapVr;
	private static Pvizit pvizit;
	private static PvizitAmb pvizitAmb;
	private static PdiagAmb diagamb;
	private static PdiagZ pdiag;
	private static Priem priem;
	private static AnamZab anamZab;
	private static Pdisp pdisp;
	private FormSign sign;
	private PrintForm printform;
	private FormPostBer postber;
	private JPanel pzakl;
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
	private CustomDateEditor tfDvz;
	public static List<IntegerClassifier> pokNames;
	private JRadioButton rbPoz;
	private JRadioButton rbRan;
	private JRadioButton rbOstr;
	private JRadioButton rbHron;
	private JCheckBox cbPatol;
	private JCheckBox cbPriznb;
	private JCheckBox cbPrizni;
	private CustomDateEditor tfFDatDIsh;
	private JButton butAnamn;
	private JButton butProsm;
	private JButton butBer;
	private JButton PosSave;
	private JButton PosDelete;
	private JTextField tfKab;
	private JRadioButton rbMetodIssl;
	private JRadioButton rbPokaz;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cbVidIssl;
	public ThriftStringClassifierCombobox<StringClassifier> cbOrgan;
	public CustomTable<Metod, Metod._Fields> tabMetod;
	private CustomTable<PokazMet, PokazMet._Fields> tabPokazMet;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cbMesto;
	private JScrollPane spPokaz;
	private CustomTable <Pokaz, Pokaz._Fields> tabPokaz;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cbN00;
	private JComboBox cbVidNapr;
	private JLabel lblObosnov;
	private JButton butPrintNapr;
	private JPopupMenu pmvizit;

	
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
		 postber = new FormPostBer();
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
		
		//JButton btnAnamz = new JButton("Анамнез жизни");
//		btnAnamz.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			sign.setVisible(true);}
//		});
		
		
//		JButton bS = new JButton("Сохранить");
//		bS.addActionListener(new ActionListener() {
//			
//			public void actionPerformed(ActionEvent e) {
//				try {
//					priem = new Priem();
//					anamZab = new AnamZab();
//					priem.setId(zapVr.getId_pvizit());
//					priem.setNpasp(zapVr.getNpasp());
//					priem.setIdpos(pvizitAmb.getId());
//					priem.setT_ad(getTextOrNull(tfad.getText()));
//					priem.setT_chss(getTextOrNull(tfchss.getText()));
//					priem.setT_rost(getTextOrNull(tfrost.getText()));
//					priem.setT_ves(getTextOrNull(tfves.getText()));
//					priem.setT_st_localis(getTextOrNull(tpLocalis.getText()));
//					priem.setT_jalob_d(getTextOrNull(tpJalobd.getText()));
//					priem.setT_jalob_krov(getTextOrNull(tpJalobkrov.getText()));
//					priem.setT_jalob_p(getTextOrNull(tpJalobp.getText()));
//					priem.setT_jalob_moch(getTextOrNull(tpJalobmoch.getText()));
//					priem.setT_jalob_endo(getTextOrNull(tpJalobendo.getText()));
//					priem.setT_jalob_nerv(getTextOrNull(tpJalobnerv.getText()));
//					priem.setT_jalob_opor(getTextOrNull(tpJalobopor.getText()));
//					priem.setT_jalob_lih(getTextOrNull(tpJaloblih.getText()));
//					priem.setT_jalob_obh(getTextOrNull(tpJalobobh.getText()));
//					priem.setT_jalob_proch(getTextOrNull(tpJalobproch.getText()));
//					priem.setT_ob_sost(getTextOrNull(tpObsost.getText()));
//					priem.setT_koj_pokr(getTextOrNull(tpKoj.getText()));
//					priem.setT_sliz(getTextOrNull(tpSliz.getText()));
//					priem.setT_podk_kl(getTextOrNull(tpPodkkl.getText()));
//					priem.setT_limf_uzl(getTextOrNull(tpLimf.getText()));
//					priem.setT_kost_mysh(getTextOrNull(tpKostmysh.getText()));
//					priem.setT_nervn_ps(getTextOrNull(tpNervnps.getText()));
//					priem.setT_telo(getTextOrNull(tpTelo.getText()));
//					priem.setT_sust(getTextOrNull(tpSust.getText()));
//					priem.setT_dyh(getTextOrNull(tpDyh.getText()));
//					priem.setT_gr_kl(getTextOrNull(tpGrkl.getText()));
//					priem.setT_perk_l(getTextOrNull(tpPerkl.getText()));
//					priem.setT_aus_l(getTextOrNull(tpAusl.getText()));
//					priem.setT_bronho(getTextOrNull(tpBronho.getText()));
//					priem.setT_arter(getTextOrNull(tpArter.getText()));
//					priem.setT_obl_s(getTextOrNull(tpObls.getText()));
//					priem.setT_perk_s(getTextOrNull(tpPerks.getText()));
//					priem.setT_aus_s(getTextOrNull(tpAuss.getText()));
//					priem.setT_pol_rta(getTextOrNull(tpPolrta.getText()));
//					priem.setT_jivot(getTextOrNull(tpJivot.getText()));
//					priem.setT_palp_jivot(getTextOrNull(tpPalpjivot.getText()));
//					priem.setT_jel_kish(getTextOrNull(tpJkt.getText()));
//					priem.setT_palp_jel(getTextOrNull(tpPalpjel.getText()));
//					priem.setT_palp_podjjel(getTextOrNull(tpPalppodjel.getText()));
//					priem.setT_pechen(getTextOrNull(tpPechen.getText()));
//					priem.setT_jelch(getTextOrNull(tpJelch.getText()));
//					priem.setT_selez(getTextOrNull(tpSelez.getText()));
//					priem.setT_obl_zad(getTextOrNull(tpOblzad.getText()));
//					priem.setT_poyasn(getTextOrNull(tpPoyasn.getText()));
//					priem.setT_pochk(getTextOrNull(tpPochki.getText()));
//					priem.setT_moch(getTextOrNull(tpMoch.getText()));
//					priem.setT_mol_jel(getTextOrNull(tpMoljel.getText()));
//					priem.setT_gr_jel(getTextOrNull(tpGrjel.getText()));
//					priem.setT_matka(getTextOrNull(tpMatka.getText()));
//					priem.setT_nar_polov(getTextOrNull(tpNarpolov.getText()));
//					priem.setT_chitov(getTextOrNull(tpChitov.getText()));
//					priem.setT_ocenka(getTextOrNull(tpOcenka.getText()));
//					priem.setT_jalob(getTextOrNull(tpJalob.getText()));
//					priem.setT_status_praesense(getTextOrNull(tpStPraes.getText()));
//					priem.setT_fiz_obsl(getTextOrNull(tpFizObsl.getText()));
//					
//					anamZab.setId_pvizit(zapVr.getId_pvizit());
//					anamZab.setNpasp(zapVr.getNpasp());
//					anamZab.setT_nachalo_zab(getTextOrNull(tpNachzab.getText()));
//					anamZab.setT_sympt(getTextOrNull(tpSympt.getText()));
//					anamZab.setT_otn_bol(getTextOrNull(tpOtnbol.getText()));
//					anamZab.setT_ps_syt(getTextOrNull(tpPssit.getText()));
//					
//					pvizit.setZakl(getTextOrNull(tpzakl.getText()));
//					pvizit.setRecomend(getTextOrNull(tprecom.getText()));
//					if (c_obr.getSelectedPcod() != null)
//						pvizit.setCobr(c_obr.getSelectedPcod());
//					if (cbrez.getSelectedPcod() != null)
//						pvizit.setRezult(cbrez.getSelectedPcod());
//					if (cbish.getSelectedPcod() != null)
//						pvizit.setIshod(cbish.getSelectedPcod());
//					
//					if (cbMobsp.getSelectedPcod() != null)
//						pvizitAmb.setMobs(cbMobsp.getSelectedPcod());
//					if (vid_opl.getSelectedPcod() != null)
//						pvizitAmb.setOpl(vid_opl.getSelectedPcod());
//					
//					MainForm.tcl.setPriem(priem);
//					MainForm.tcl.setAnamZab(anamZab);
//					MainForm.tcl.UpdatePvizit(pvizit);
//					MainForm.tcl.UpdatePvizitAmb(pvizitAmb);
//				} catch (KmiacServerException e1) {
//					e1.printStackTrace();
//				} catch (TException e1) {
//					e1.printStackTrace();
//					MainForm.conMan.reconnect(e1);
//				}
//			}
//		});
		
//		JButton bSSh = new JButton("Сохранить как шаблон");
//		
//		JButton bZSh = new JButton("Загрузить из шаблона");
		
//		final JButton bVD = new JButton("Печатные формы");
//		bVD.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				printform.setVisible(true);
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
//}
//		});
		
		JPanel panel_Talon = new JPanel();
				panel_Talon.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0422\u0430\u043B\u043E\u043D \u0430\u043C\u0431.\u043F\u0430\u0446\u0438\u0435\u043D\u0442\u0430", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0)));
//		bDelet.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//			}
//		});
		
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
					e1.printStackTrace();
				} catch (TException e1) {
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
		spJalob.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
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
							.addComponent(tpJalob, GroupLayout.DEFAULT_SIZE, 497, Short.MAX_VALUE)
							.addComponent(lblJalob, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
							.addComponent(tpJalobd.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
							.addComponent(tpJalobkrov.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
							.addComponent(tpJalobd,GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE)
							.addComponent(tpJalobkrov,GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE))
						.addComponent(tpJalobp.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobp, GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobmoch.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobmoch, GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobendo.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobendo, GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobnerv.getLabel(), GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobnerv, GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobopor.getLabel(), GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobopor, GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJaloblih.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJaloblih, GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobobh.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobobh, GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobproch.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJalobproch, GroupLayout.PREFERRED_SIZE, 497, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(0, Short.MAX_VALUE))
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
				.addComponent(spAnamnesis, GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
		);
		gl_pAnanmnesis.setVerticalGroup(
			gl_pAnanmnesis.createParallelGroup(Alignment.LEADING)
				.addComponent(spAnamnesis, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
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
						.addComponent(tpNachzab, GroupLayout.PREFERRED_SIZE, 530, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSympt.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSympt, GroupLayout.PREFERRED_SIZE, 530, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpOtnbol.getLabel(), GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpOtnbol, GroupLayout.PREFERRED_SIZE, 530, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPssit, GroupLayout.PREFERRED_SIZE, 530, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpNachzab.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPssit.getLabel(), GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblIstZab, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpIstZab, GroupLayout.PREFERRED_SIZE, 530, GroupLayout.PREFERRED_SIZE))
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
					.addComponent(lbltemp)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tftemp, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblad)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tfad, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblrost)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tfrost, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblves, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tfves, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
					.addGap(138)
					.addComponent(lblchss)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tfchss, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblchss)
						.addComponent(tfchss, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tftemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbltemp)
						.addComponent(lblad)
						.addComponent(tfad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblrost)
						.addComponent(tfrost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblves)
						.addComponent(tfves, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		
		JScrollPane sppraesense = new JScrollPane();
		sppraesense.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GroupLayout gl_pStatuspr = new GroupLayout(pStatuspr);
		gl_pStatuspr.setHorizontalGroup(
			gl_pStatuspr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pStatuspr.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pStatuspr.createParallelGroup(Alignment.LEADING)
						.addComponent(sppraesense, GroupLayout.PREFERRED_SIZE, 536, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_pStatuspr.setVerticalGroup(
			gl_pStatuspr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pStatuspr.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
					.addGap(8)
					.addComponent(sppraesense, GroupLayout.DEFAULT_SIZE, 425, Short.MAX_VALUE)
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
		tpLimf.setBorder(UIManager.getBorder("TextField.border"));
		
		 tpKostmysh = new ShablonTextField(6, 29, listShablon);
		tpKostmysh.setBorder(UIManager.getBorder("TextField.border"));
		
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
						.addComponent(tpStPraes, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStPraes)
						.addComponent(tpObsost, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpKoj, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpKoj.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpObsost.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSliz.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSliz, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPodkkl.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPodkkl, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpLimf.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpLimf, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpKostmysh.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpKostmysh, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpNervnps.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpNervnps, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpTelo.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpTelo, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(0, Short.MAX_VALUE))
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
		spFizik.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GroupLayout gl_pfizikob = new GroupLayout(pfizikob);
		gl_pfizikob.setHorizontalGroup(
			gl_pfizikob.createParallelGroup(Alignment.LEADING)
				.addComponent(spFizik, GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
		);
		gl_pfizikob.setVerticalGroup(
			gl_pfizikob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pfizikob.createSequentialGroup()
					.addComponent(spFizik, GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
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
						.addComponent(tpFizObsl, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSust.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSust, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpDyh.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpDyh, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpGrkl.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpGrkl, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPerkl.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPerkl, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpAusl.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpAusl, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpBronho.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpBronho, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpArter.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpArter, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpObls.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpObls, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPerks.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPerks, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpAuss.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpAuss, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPolrta.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPolrta, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJivot.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJivot, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPalpjivot.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPalpjivot, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJkt.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJkt, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPalpjel.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPalpjel, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPalppodjel.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPalppodjel, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPechen.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPechen, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJelch.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpJelch, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSelez.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpSelez, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpOblzad.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpOblzad, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPoyasn.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPoyasn, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPochki.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpPochki, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpMoch.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpMoch, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpMoljel.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpMoljel, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpGrjel.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpGrjel, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpMatka.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpMatka, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpNarpolov.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpNarpolov, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpChitov.getLabel(), GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
						.addComponent(tpChitov, GroupLayout.PREFERRED_SIZE, 515, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblfizik, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(0, Short.MAX_VALUE))
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
		
		//JButton bDB = new JButton("Наблюдение за берем.");
//		bDB.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent arg0) {
//				postber.setVisible(true);
//			}
//		});
		
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
					e2.printStackTrace();
				} catch (PvizitNotFoundException e2) {
					try {
						MainForm.tcl.AddPvizit(pvizit);
						pvizitAmb.setId(MainForm.tcl.AddPvizitAmb(pvizitAmb));
						TabPos.setData(MainForm.tcl.getPvizitAmb(pvizit.getId()));
						TabPos.setRowSelectionInterval(TabPos.getRowCount() - 1, TabPos.getRowCount() - 1);
					} catch (KmiacServerException e) {
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
		
//		JButton bInfo = new JButton("Просмотр");
//		bInfo.addActionListener(new ActionListener() {
//			public void actionPerformed(ActionEvent e) {
//				try {
//					MainForm.tcl.testConnection();
//					pinfo = new PInfo();
//					pinfo.setVisible(true);
//				} catch (TException e1) {
//					MainForm.conMan.reconnect(e1);
//				}}
//		});
		
		JScrollPane spShab = new JScrollPane();
		
		butAnamn = new JButton("Анамнез жизни");
		butAnamn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sign.setVisible(true);}
		});
		
		butProsm = new JButton("Просмотр");
		butProsm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					MainForm.tcl.testConnection();
					pinfo = new PInfo();
					pinfo.setVisible(true);
				} catch (TException e1) {
					MainForm.conMan.reconnect(e1);
				}}
		});
		
		butBer = new JButton("Набл.за берем.");
		butBer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				postber.setVisible(true);
				}
		});
		GroupLayout gl_panel;
			
			PosSave = new JButton("v");
			PosSave.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
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
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				}
			}

			});
			
			PosDelete = new JButton("-");
			PosDelete.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						MainForm.tcl.DeleteAnamZab(TabPos.getSelectedItem().getId_obr());
						MainForm.tcl.DeletePriem(TabPos.getSelectedItem().getId());
						MainForm.tcl.DeletePvizitAmb(TabPos.getSelectedItem().getId());
						TabPos.setData(MainForm.tcl.getPvizitAmb(Vvod.zapVr.getId_pvizit()));
					} catch (KmiacServerException e) {
						e.printStackTrace();
					} catch (TException e) {
						MainForm.conMan.reconnect(e);
					}
				}
			});
			gl_panel = new GroupLayout(panel);
			gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING, false)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(tabbedPane, 0, 0, Short.MAX_VALUE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(spShab, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE))
										.addComponent(panel_Talon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(sPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
											.addComponent(PosDelete, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(PosSave, 0, 0, Short.MAX_VALUE)
											.addComponent(AddVizit, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
							.addGroup(gl_panel.createSequentialGroup()
								.addGap(34)
								.addComponent(butAnamn)
								.addGap(18)
								.addComponent(butProsm)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(butBer)))
						.addContainerGap(3103, Short.MAX_VALUE))
			);
			gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel.createSequentialGroup()
						.addGap(6)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(butAnamn)
							.addComponent(butProsm)
							.addComponent(butBer))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(sPos, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(AddVizit)
								.addGap(1)
								.addComponent(PosSave)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(PosDelete)))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_Talon, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE)
							.addComponent(spShab, GroupLayout.DEFAULT_SIZE, 509, Short.MAX_VALUE))
						.addContainerGap())
			);
		
		spShab.setViewportView(listShablon);
		
		TabPos = new CustomTable<>(false,false,PvizitAmb.class,3,"Дата",19,"ФИО врача",5,"Должность");
		TabPos.setDateField(0);
		sPos.setViewportView(TabPos);
		TabPos.setFillsViewportHeight(true);
		
		pmvizit = new JPopupMenu();
		addPopup(TabPos, pmvizit);
		
//		JPopupMenu pmvizit = new JPopupMenu();
		
		priem = new Priem();
		anamZab = new AnamZab();
				JMenuItem mi1 = new JMenuItem("Протокол посещения");
				pmvizit.add(mi1);
				JMenuItem mi2 = new JMenuItem("Выписка из карты");
				pmvizit.add(mi2);
				JMenuItem mi3 = new JMenuItem("Протокол заключения КЭК");
				pmvizit.add(mi3);
mi1.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try{
				Protokol protokol = new Protokol();
				protokol.setUserId(MainForm.authInfo.getUser_id());
				protokol.setNpasp(Vvod.zapVr.getNpasp());
				protokol.setPvizit_id(TabPos.getSelectedItem().id_obr);
				protokol.setPvizit_ambId(TabPos.getSelectedItem().id);
				protokol.setCpol(MainForm.authInfo.getCpodr());
				String servPath = MainForm.tcl.printProtokol(protokol);
				String cliPath;
				cliPath = File.createTempFile("pr", ".htm").getAbsolutePath();
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

mi2.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try{
				String servPath = MainForm.tcl.printVypis(Vvod.zapVr.getNpasp(), TabPos.getSelectedItem().id_obr, MainForm.authInfo.getUser_id());
				String cliPath;
				cliPath = File.createTempFile("vypis", ".htm").getAbsolutePath();
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

mi3.addActionListener(new ActionListener() {
	@Override
	public void actionPerformed(ActionEvent arg0) {
		try{
				String servPath = MainForm.tcl.printKek(Vvod.zapVr.getNpasp(), TabPos.getSelectedItem().id_obr);
				String cliPath;
				cliPath = File.createTempFile("kek", ".htm").getAbsolutePath();
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
			
			/*@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()){
					if (TabDiag.getSelectedItem()!= null) {
						diagamb = TabDiag.getSelectedItem();
						GroupBox1.clearSelection();
						GroupBox2.clearSelection();
							jbosn.setSelected(diagamb.diag_stat == 1);
							jbsoput.setSelected(diagamb.diag_stat == 3);
							jbosl.setSelected(diagamb.diag_stat == 2);
							if (diagamb.predv)
								jbpredv.setSelected(true);
							else
								jbzakl.setSelected(true);
							if (diagamb.getObstreg()!=0) cbObstreg.setSelectedPcod(diagamb.getObstreg());
							if (diagamb.getVid_tr()!=0) vid_travm.setSelectedPcod(diagamb.getVid_tr());
							
						pdiag = new PdiagZ();*/
		
			TabPos.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()){
					if (TabPos.getSelectedItem()!= null) {
						pvizitAmb = TabPos.getSelectedItem();
//						priem = new Priem();
//						anamZab = new AnamZab();
						try {
							
							priem = MainForm.tcl.getPriem(TabPos.getSelectedItem().npasp,TabPos.getSelectedItem().id);
							anamZab = MainForm.tcl.getAnamZab(TabPos.getSelectedItem().npasp,TabPos.getSelectedItem().id_obr);
							} catch (KmiacServerException e) {
								e.printStackTrace();
							} catch (PriemNotFoundException e) {
								e.printStackTrace();
							} catch (TException e) {
								MainForm.conMan.reconnect(e);
							}
						tpJalob.setText(priem.getT_jalob());	
						tpJalobd.setText(priem.getT_jalob_d());	
						tpJalobkrov.setText(priem.getT_jalob_krov());	
						tpJalobp.setText(priem.getT_jalob_p());	
						tpJalobmoch.setText(priem.getT_jalob_moch());	
						tpJalobendo.setText(priem.getT_jalob_endo());	
						tpJalobnerv.setText(priem.getT_jalob_nerv());	
						tpJalobopor.setText(priem.getT_jalob_opor());	
						tpJaloblih.setText(priem.getT_jalob_lih());	
						tpJalobobh.setText(priem.getT_jalob_obh());	
						tpJalobproch.setText(priem.getT_jalob_proch());	
						tpNachzab.setText(anamZab.getT_nachalo_zab());	
						tpSympt.setText(anamZab.getT_sympt());	
						tpOtnbol.setText(anamZab.getT_sympt());	
						tpPssit.setText(anamZab.getT_ps_syt());	
						tfad.setText(priem.getT_ad());	
						tftemp.setText(priem.getT_temp());	
						tfchss.setText(priem.getT_chss());	
						tfrost.setText(priem.getT_rost());	
						tfves.setText(priem.getT_ves());	
						tpObsost.setText(priem.getT_ob_sost());	
						tpKoj.setText(priem.getT_koj_pokr());	
						tpSliz.setText(priem.getT_sliz());	
						tpPodkkl.setText(priem.getT_podk_kl());	
						tpLimf.setText(priem.getT_limf_uzl());	
						tpKostmysh.setText(priem.getT_kost_mysh());	
						tpNervnps.setText(priem.getT_nervn_ps());	
						tpSust.setText(priem.getT_sust());	
						tpDyh.setText(priem.getT_sust());	
						tpGrkl.setText(priem.getT_sust());	
						tpPerkl.setText(priem.getT_perk_l());	
						tpAusl.setText(priem.getT_aus_l());	
						tpBronho.setText(priem.getT_bronho());	
						tpArter.setText(priem.getT_arter());	
						tpObls.setText(priem.getT_obl_s());	
						tpPerks.setText(priem.getT_perk_s());	
						tpAuss.setText(priem.getT_aus_s());	
						tpPolrta.setText(priem.getT_pol_rta());	
						tpJivot.setText(priem.getT_jivot());	
						tpPalpjivot.setText(priem.getT_palp_jivot());	
						tpJkt.setText(priem.getT_jel_kish());	
						tpPalpjel.setText(priem.getT_palp_jel());	
						tpPalppodjel.setText(priem.getT_palp_podjjel());	
						tpPechen.setText(priem.getT_pechen());	
						tpJelch.setText(priem.getT_jelch());	
						tpSelez.setText(priem.getT_selez());	
						tpOblzad.setText(priem.getT_obl_zad());	
						tpPoyasn.setText(priem.getT_poyasn());	
						tpPochki.setText(priem.getT_pochk());	
						tpMoch.setText(priem.getT_moch());	
						tpMoljel.setText(priem.getT_mol_jel());	
						tpGrjel.setText(priem.getT_gr_jel());	
						tpMatka.setText(priem.getT_matka());	
						tpNarpolov.setText(priem.getT_nar_polov());	
						tpChitov.setText(priem.getT_chitov());	
						tpLocalis.setText(priem.getT_st_localis());	
						tpOcenka.setText(priem.getT_ocenka());	

					}
				}
			}
		});
			
		JPanel plocst = new JPanel();
		tabbedPane.addTab("Localis status", null, plocst, null);
		
		 tpLocalis = new JEditorPane();
		tpLocalis.setBorder(UIManager.getBorder("TextField.border"));
		GroupLayout gl_plocst = new GroupLayout(plocst);
		gl_plocst.setHorizontalGroup(
			gl_plocst.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_plocst.createSequentialGroup()
					.addContainerGap()
					.addComponent(tpLocalis)
					.addContainerGap())
		);
		gl_plocst.setVerticalGroup(
			gl_plocst.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_plocst.createSequentialGroup()
					.addContainerGap()
					.addComponent(tpLocalis, GroupLayout.PREFERRED_SIZE, 85, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(408, Short.MAX_VALUE))
		);
		plocst.setLayout(gl_plocst);
		final ButtonGroup GroupBox1 = new ButtonGroup();
		final ButtonGroup GroupBox2 = new ButtonGroup();;
		final ButtonGroup GBoxStady = new ButtonGroup();
		final ButtonGroup GBoxHar = new ButtonGroup();
		
		JPanel pds = new JPanel();
		tabbedPane.addTab("Диагноз", null, pds, null);
		
		JPanel pvidd = new JPanel();
		pvidd.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		final JRadioButton jbosn = new JRadioButton("Основной",true);
		final JRadioButton jbsoput = new JRadioButton("Сопутствующий",true);
		final JRadioButton jbosl = new JRadioButton("Осложнение основного",true);
		pvidd.add(jbosn);
		pvidd.add(jbsoput);
		pvidd.add(jbosl);
		GroupBox1.add(jbosn);
		GroupBox1.add(jbsoput);
		GroupBox1.add(jbosl);
		
		JPanel ppredv = new JPanel();
		ppredv.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		final JRadioButton jbpredv = new JRadioButton("Предварительный",true);
		final JRadioButton jbzakl = new JRadioButton("Заключительный",true);
		ppredv.add(jbpredv);
		ppredv.add(jbzakl);
		GroupBox2.add(jbpredv);
		GroupBox2.add(jbzakl);
		
		JScrollPane spDiag = new JScrollPane();
		TabDiag = new CustomTable<>(true,true,PdiagAmb.class,7,"Дата",3,"Код МКБ",4,"Описание");
		TabDiag.setDateField(0);
		spDiag.setViewportView(TabDiag);
		TabDiag.setFillsViewportHeight(true);
		TabDiag.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			PdiagZ pdiag;
			Pdisp pdisp;
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()){
					if (TabDiag.getSelectedItem()!= null) {
						diagamb = TabDiag.getSelectedItem();
						GroupBox1.clearSelection();
						GroupBox2.clearSelection();
							jbosn.setSelected(diagamb.diag_stat == 1);
							jbsoput.setSelected(diagamb.diag_stat == 3);
							jbosl.setSelected(diagamb.diag_stat == 2);
							if (diagamb.predv)
								jbpredv.setSelected(true);
							else
								jbzakl.setSelected(true);
							if (diagamb.getObstreg()!=0) cbObstreg.setSelectedPcod(diagamb.getObstreg());
							if (diagamb.getVid_tr()!=0) vid_travm.setSelectedPcod(diagamb.getVid_tr());
							
						pdiag = new PdiagZ();
						GBoxStady.clearSelection();
						GBoxHar.clearSelection();
						try {
							pdiag = MainForm.tcl.getPdiagZ(TabDiag.getSelectedItem().getId());
						} catch (KmiacServerException e) {
							e.printStackTrace();
						} catch (PdiagNotFoundException e) {
							System.out.println("diagZ not found");
						} catch (TException e) {
							MainForm.conMan.reconnect(e);
						}
						rbPoz.setSelected(pdiag.getStady() == 1);
						rbRan.setSelected(pdiag.getStady() == 2);
						rbOstr.setSelected(pdiag.getXzab() == 1);
						rbHron.setSelected(pdiag.getXzab() == 2);
						cbPatol.setSelected(pdiag.getPat() == 1);
						cbPriznb.setSelected(pdiag.getPrizb() == 1);
						cbPrizni.setSelected(pdiag.getPrizi() == 1);
						
						pdisp = new Pdisp();
						try {
							pdisp = MainForm.tcl.getPdisp(TabDiag.getSelectedItem().getId());
						} catch (KmiacServerException e) {
							e.printStackTrace();
						} catch (PdispNotFoundException e) {
							System.out.println("disp not found");
						} catch (TException e) {
							MainForm.conMan.reconnect(e);
						}
						
						if (pdisp.isSetD_vz())
							tfDvz.setDate(pdisp.getD_vz());
						else
							tfDvz.setValue(null);
						
						if (pdisp.isSetDataish())
							tfFDatDIsh.setDate(pdisp.getDataish());
						else
							tfFDatDIsh.setValue(null);
						
						if (pdisp.isSetIshod())
							cbDish.setSelectedPcod(pdisp.getIshod());
						else
							cbDish.setSelectedItem(null);
						if (pdisp.isSetD_grup())
							cbDgrup.setSelectedPcod(pdisp.getD_grup());
						else
							cbDgrup.setSelectedItem(null);
						}	
					}
				}
		});			
		
				
				 vid_travm = new ThriftIntegerClassifierCombobox<>(true);
				 
				 JLabel lblvidtravm = new JLabel("Вид травмы");
				 
				 JButton bAddDiag = new JButton("+");
				 bAddDiag.addActionListener(new ActionListener() {
				 	public void actionPerformed(ActionEvent e) {
				 		TabDiag.requestFocus();
				  		try {
					  		diagamb = new PdiagAmb();
					  		diagamb.setId_obr(zapVr.getId_pvizit());
					  		diagamb.setNpasp(zapVr.getNpasp());
					  		diagamb.setDatap(System.currentTimeMillis());
					  		diagamb.setDatad(System.currentTimeMillis());
					  		diagamb.setCod_sp(MainForm.authInfo.getPcod());
					  		diagamb.setCdol(MainForm.authInfo.getCdol());
					  		diagamb.setPredv(true);
							diagamb.setId(MainForm.tcl.AddPdiagAmb(diagamb));
				 			TabDiag.addItem(diagamb);
						} catch (KmiacServerException e1) {
							e1.printStackTrace();
						} catch (TException e1) {
							MainForm.conMan.reconnect(e1);
						}


				 	}	

				 });
				 
				 JLabel lblObstreg = new JLabel("Обстоятельства регистрации");
				 
				  cbObstreg = new ThriftIntegerClassifierCombobox<>(true);
				  
				  JPanel pStady = new JPanel();
				  pStady.setBorder(new TitledBorder(null, "\u0421\u0442\u0430\u0434\u0438\u044F \u0437\u0430\u0431\u043E\u043B\u0435\u0432\u0430\u043D\u0438\u044F", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
				  rbRan = new JRadioButton("Ранняя", false);
				  
				  rbPoz = new JRadioButton("Поздняя", false);
				  pStady.add(rbRan);
				  pStady.add(rbPoz);
				  GBoxStady.add(rbRan);
				  GBoxStady.add(rbPoz);
				  
				  JPanel pXzab = new JPanel();
				  pXzab.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0425\u0430\u0440\u0430\u043A\u0442\u0435\u0440 \u0437\u0430\u0431\u043E\u043B\u0435\u0432\u0430\u043D\u0438\u044F", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
				  rbOstr = new JRadioButton("Острое", false);
				  pXzab.add(rbOstr);
				  
				  rbHron = new JRadioButton("Хроническое", false);
				  pXzab.add(rbHron);
				  GBoxHar.add(rbOstr);
				  GBoxHar.add(rbHron);
				  
				  JPanel pDisp = new JPanel();
				  pDisp.setBorder(new TitledBorder(null, "\u0414\u0438\u0441\u043F\u0430\u043D\u0441\u0435\u0440\u043D\u044B\u0439 \u0443\u0447\u0435\u0442", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
				  
				  cbPatol = new JCheckBox("Патология, имеющая абсолютные противопоказания к вынашиванию беременности");
				  
				  cbPriznb = new JCheckBox("Связь с участием в боевых действиях");
				  
				  cbPrizni = new JCheckBox("Инвалидность");
				  
				  JButton bSaveDiag = new JButton("v");
				  bSaveDiag.addActionListener(new ActionListener() {
				  	public void actionPerformed(ActionEvent e) {
				  		try {
					  		diagamb.setDiag(TabDiag.getSelectedItem().getDiag());
					  		diagamb.setNamed(TabDiag.getSelectedItem().getNamed());
					  		diagamb.setDatad(TabDiag.getSelectedItem().getDatad());
					  		if (jbosn.isSelected()) diagamb.setDiag_stat(1);
					  		if (jbsoput.isSelected())diagamb.setDiag_stat(3);
					  		if (jbosl.isSelected()) diagamb.setDiag_stat(2);
					  		if (cbObstreg.getSelectedPcod() != null) diagamb.setObstreg(cbObstreg.getSelectedPcod());
					  		if (vid_travm.getSelectedPcod() != null) diagamb.setVid_tr(vid_travm.getSelectedPcod());
					  		
					  		pdiag = new PdiagZ();
					  		pdisp = new Pdisp();
					  		if (jbpredv.isSelected()) {
					  			diagamb.setPredv(true);
					  		}
					  		if (jbzakl.isSelected()) {
					  			diagamb.setPredv(false);
					  			pdiag.setId_diag_amb(diagamb.getId());
					  			pdiag.setNpasp(diagamb.getNpasp());
					  			pdiag.setDiag(diagamb.getDiag());
					  			pdiag.setCpodr(MainForm.authInfo.getCpodr());
					  			pdiag.setNmvd(diagamb.getObstreg());
					  			pdiag.setCod_sp(diagamb.getCod_sp());
					  			pdiag.setCdol_ot(diagamb.getCdol());
					  			pdiag.setNamed(diagamb.getNamed());
					  			if (rbOstr.isSelected()) pdiag.setXzab(1);
					  			if (rbHron.isSelected()) pdiag.setXzab(2);
					  			if (rbPoz.isSelected()) pdiag.setStady(2);
					  			if (rbRan.isSelected()) pdiag.setStady(1);
					  			if (cbPatol.isSelected()) pdiag.setPat(1);
					  			if (cbPriznb.isSelected()) pdiag.setPrizb(1);
					  			if (cbPrizni.isSelected()) pdiag.setPrizi(1);
					  			if (tfDvz.getDate() != null) pdiag.setDisp(1);
					  			MainForm.tcl.setPdiag(pdiag);
					  		}
				  		if (TabDiag.getSelectedItem().getDiag()!=null && TabDiag.getSelectedItem().getNamed()!=null) MainForm.tcl.UpdatePdiagAmb(diagamb);
				  		//else JOptionPane.showMessageDialog(panel, "Введите недостающее значение");
				  		
			  			if (tfDvz.getDate() != null){
				  			pdisp.setId_diag(diagamb.getId());
				  			pdiag.setId_diag_amb(diagamb.getId());
				  			pdisp.setNpasp(diagamb.getNpasp());
				  			pdisp.setDiag(diagamb.getDiag());
				  			pdisp.setPcod(MainForm.authInfo.getCpodr());
				  			if (tfDvz.getDate() != null)
								pdisp.setD_vz(tfDvz.getDate().getTime());
				  			if (tfFDatDIsh.getDate() != null)
								pdisp.setDataish(tfFDatDIsh.getDate().getTime());
					  		if (cbDish.getSelectedPcod() != null) pdisp.setIshod(cbDish.getSelectedPcod());
					  		if (cbDgrup.getSelectedPcod() != null) pdisp.setD_grup(cbDgrup.getSelectedPcod());
	
				  			pdisp.setCod_sp(diagamb.getCod_sp());
				  			pdisp.setCdol_ot(diagamb.getCdol());
				  			MainForm.tcl.setPdisp(pdisp);
				  			/*pdiag*/
				  				pdiag.setD_vz(pdisp.getD_vz());
				  				pdiag.setDataish(pdisp.getDataish());
					  		pdiag.setIshod(pdisp.getIshod());
					  		pdiag.setD_grup(pdisp.getD_grup());
					  		MainForm.tcl.setPdiag(pdiag);	
			  			}
 				  	} catch (KmiacServerException e1) {
				  		e1.printStackTrace();
				  	} catch (TException e1) {
				  		MainForm.conMan.reconnect(e1);
				  	}
				  	}
				  });
				  
				  JButton DeleteDiag = new JButton("-");
				  DeleteDiag.addActionListener(new ActionListener() {
				  	public void actionPerformed(ActionEvent arg0) {
				  		try {
							MainForm.tcl.DeletePdiagAmb(TabDiag.getSelectedItem().getId());
							TabDiag.setData(MainForm.tcl.getPdiagAmb(Vvod.zapVr.getId_pvizit()));
						} catch (KmiacServerException e) {
							e.printStackTrace();
						} catch (TException e) {
							MainForm.conMan.reconnect(e);
						}
					}
				  });
				  GroupLayout gl_pds = new GroupLayout(pds);
				  gl_pds.setHorizontalGroup(
				  	gl_pds.createParallelGroup(Alignment.LEADING)
				  		.addGroup(gl_pds.createSequentialGroup()
				  			.addContainerGap()
				  			.addGroup(gl_pds.createParallelGroup(Alignment.LEADING)
				  				.addComponent(pDisp, GroupLayout.PREFERRED_SIZE, 458, GroupLayout.PREFERRED_SIZE)
				  				.addComponent(cbPatol, GroupLayout.PREFERRED_SIZE, 500, GroupLayout.PREFERRED_SIZE)
				  				.addComponent(pvidd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				  				.addGroup(gl_pds.createSequentialGroup()
				  					.addComponent(cbPriznb)
				  					.addPreferredGap(ComponentPlacement.RELATED)
				  					.addComponent(cbPrizni))
				  				.addGroup(gl_pds.createSequentialGroup()
				  					.addComponent(spDiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				  					.addPreferredGap(ComponentPlacement.RELATED)
				  					.addGroup(gl_pds.createParallelGroup(Alignment.LEADING)
				  						.addComponent(DeleteDiag, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
				  						.addComponent(bSaveDiag, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
				  						.addComponent(bAddDiag)))
				  				.addComponent(ppredv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				  				.addGroup(gl_pds.createSequentialGroup()
				  					.addComponent(lblvidtravm)
				  					.addPreferredGap(ComponentPlacement.RELATED)
				  					.addComponent(vid_travm, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
				  					.addPreferredGap(ComponentPlacement.UNRELATED)
				  					.addComponent(lblObstreg)
				  					.addPreferredGap(ComponentPlacement.UNRELATED)
				  					.addComponent(cbObstreg, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE))
				  				.addGroup(gl_pds.createSequentialGroup()
				  					.addComponent(pStady, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				  					.addGap(18)
				  					.addComponent(pXzab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
				  			.addGap(425))
				  );
				  gl_pds.setVerticalGroup(
				  	gl_pds.createParallelGroup(Alignment.LEADING)
				  		.addGroup(gl_pds.createSequentialGroup()
				  			.addContainerGap()
				  			.addGroup(gl_pds.createParallelGroup(Alignment.BASELINE)
				  				.addComponent(spDiag, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
				  				.addGroup(gl_pds.createSequentialGroup()
				  					.addComponent(bAddDiag)
				  					.addGap(4)
				  					.addComponent(bSaveDiag)
				  					.addGap(7)
				  					.addComponent(DeleteDiag)))
				  			.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
				  			.addComponent(pvidd, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
				  			.addPreferredGap(ComponentPlacement.RELATED)
				  			.addComponent(ppredv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				  			.addPreferredGap(ComponentPlacement.RELATED)
				  			.addGroup(gl_pds.createParallelGroup(Alignment.LEADING)
				  				.addComponent(pStady, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				  				.addComponent(pXzab, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
				  			.addPreferredGap(ComponentPlacement.RELATED)
				  			.addGroup(gl_pds.createParallelGroup(Alignment.BASELINE)
				  				.addComponent(lblvidtravm)
				  				.addComponent(vid_travm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				  				.addComponent(lblObstreg)
				  				.addComponent(cbObstreg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				  			.addGap(18)
				  			.addComponent(pDisp, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
				  			.addPreferredGap(ComponentPlacement.UNRELATED)
				  			.addGroup(gl_pds.createParallelGroup(Alignment.BASELINE)
				  				.addComponent(cbPriznb)
				  				.addComponent(cbPrizni))
				  			.addPreferredGap(ComponentPlacement.RELATED)
				  			.addComponent(cbPatol, GroupLayout.PREFERRED_SIZE, 18, GroupLayout.PREFERRED_SIZE)
				  			.addGap(31))
				  );
				  
				  JLabel lblDish = new JLabel("Исход д/у");
				  
				   cbDish = new ThriftIntegerClassifierCombobox<>(true);
				   
				   JLabel lblDvz = new JLabel("Дата взятия на д/у");
				   
				   tfDvz = new CustomDateEditor();
				   tfDvz.setColumns(10);
				   
				   JLabel lblDGrup = new JLabel("Группа д/у");
				   
				    cbDgrup = new ThriftIntegerClassifierCombobox<>(true);
				    
				    JLabel lblDatDIsh = new JLabel("Дата установления исхода");
				    
				    tfFDatDIsh = new CustomDateEditor();
				    tfFDatDIsh.setColumns(10);
				    GroupLayout gl_pDisp = new GroupLayout(pDisp);
				    gl_pDisp.setHorizontalGroup(
				    	gl_pDisp.createParallelGroup(Alignment.LEADING)
				    		.addGroup(gl_pDisp.createSequentialGroup()
				    			.addContainerGap()
				    			.addGroup(gl_pDisp.createParallelGroup(Alignment.LEADING)
				    				.addGroup(gl_pDisp.createSequentialGroup()
				    					.addGroup(gl_pDisp.createParallelGroup(Alignment.LEADING)
				    						.addGroup(gl_pDisp.createSequentialGroup()
				    							.addComponent(lblDvz)
				    							.addGap(4)
				    							.addComponent(tfDvz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				    						.addGroup(gl_pDisp.createSequentialGroup()
				    							.addComponent(lblDGrup)
				    							.addPreferredGap(ComponentPlacement.RELATED)
				    							.addComponent(cbDgrup, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)))
				    					.addGap(156))
				    				.addGroup(gl_pDisp.createSequentialGroup()
				    					.addComponent(lblDatDIsh, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
				    					.addPreferredGap(ComponentPlacement.RELATED)
				    					.addComponent(tfFDatDIsh, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
				    				.addGroup(gl_pDisp.createSequentialGroup()
				    					.addComponent(lblDish)
				    					.addPreferredGap(ComponentPlacement.RELATED)
				    					.addComponent(cbDish, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)))
				    			.addContainerGap())
				    );
				    gl_pDisp.setVerticalGroup(
				    	gl_pDisp.createParallelGroup(Alignment.LEADING)
				    		.addGroup(gl_pDisp.createSequentialGroup()
				    			.addGroup(gl_pDisp.createParallelGroup(Alignment.LEADING)
				    				.addGroup(gl_pDisp.createSequentialGroup()
				    					.addGap(3)
				    					.addComponent(lblDvz))
				    				.addComponent(tfDvz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				    			.addPreferredGap(ComponentPlacement.RELATED)
				    			.addGroup(gl_pDisp.createParallelGroup(Alignment.BASELINE)
				    				.addComponent(lblDGrup)
				    				.addComponent(cbDgrup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				    			.addPreferredGap(ComponentPlacement.UNRELATED)
				    			.addGroup(gl_pDisp.createParallelGroup(Alignment.BASELINE)
				    				.addComponent(lblDish)
				    				.addComponent(cbDish, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				    			.addGap(8)
				    			.addGroup(gl_pDisp.createParallelGroup(Alignment.BASELINE)
				    				.addComponent(lblDatDIsh)
				    				.addComponent(tfFDatDIsh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				    			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				    );
				    pDisp.setLayout(gl_pDisp);
				    pds.setLayout(gl_pds);

		
		JPanel pprint = new JPanel();
		tabbedPane.addTab("Печать", null, pprint, null);
		
		JScrollPane spPrint = new JScrollPane();
		GroupLayout gl_pprint = new GroupLayout(pprint);
		gl_pprint.setHorizontalGroup(
			gl_pprint.createParallelGroup(Alignment.LEADING)
				.addComponent(spPrint, GroupLayout.DEFAULT_SIZE, 549, Short.MAX_VALUE)
		);
		gl_pprint.setVerticalGroup(
			gl_pprint.createParallelGroup(Alignment.LEADING)
				.addComponent(spPrint, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
		);
		
		JTabbedPane tabPrint = new JTabbedPane(JTabbedPane.TOP);
		spPrint.setViewportView(tabPrint);
		
		JPanel pNaprIssl = new JPanel();
		tabPrint.addTab("Направление на иссл.", null, pNaprIssl, null);
		
		final JLabel lblmet = new JLabel("Методы");
		
		final JScrollPane spMetod = new JScrollPane();
		
		final JLabel lblPokazMet = new JLabel("Показатели");
		
		final JScrollPane spPokazMet = new JScrollPane();
		
		final JLabel lblMesto = new JLabel("Место");
		
		cbMesto = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
		
		final JLabel lblKab = new JLabel("Каб.");
		
		tfKab = new JTextField();
		tfKab.setColumns(10);
		
		final JScrollPane spPokaz = new JScrollPane();
		
		final JLabel lblOrgan = new JLabel("Органы и системы");
		
rbPokaz = new JRadioButton("Показатели");
rbPokaz.addActionListener(new ActionListener() {
 	public void actionPerformed(ActionEvent e) {
 		rbMetodIssl.setSelected(false);
	 	lblmet.setVisible(false);
	 	spPokazMet.setVisible(false);
	 	tabMetod.setVisible(false);
	 	lblPokazMet.setVisible(false);
	 	spPokazMet.setVisible(false);
	 	tabPokazMet.setVisible(false);
	 	lblOrgan.setVisible(true);
	 	cbOrgan.setVisible(true);
	 	spPokaz.setVisible(true);
	 	tabPokaz.setVisible(true);}
 });
		
		
		
		cbOrgan = new ThriftStringClassifierCombobox<StringClassifier>(true);
		cbOrgan.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
				try {
					if (cbOrgan.getSelectedItem() != null)
					tabPokaz.setData(MainForm.tcl.getPokaz(cbVidIssl.getSelectedItem().pcod,cbOrgan.getSelectedItem().pcod));
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
				}	
}
		 });
		
		
		
		 rbMetodIssl = new JRadioButton("Методы исследований");
		 rbMetodIssl.addActionListener(new ActionListener() {
			 	public void actionPerformed(ActionEvent arg0) {
			 	rbPokaz.setSelected(false);	
			 	spPokaz.setVisible(false);
			 	tabPokaz.setVisible(false);
			 	lblOrgan.setVisible(false);
			 	cbOrgan.setVisible(false);
			 	cbVidIssl.setVisible(true);
			 	lblmet.setVisible(true);
			 	spMetod.setVisible(true);
			 	tabMetod.setVisible(true);
			 	lblPokazMet.setVisible(true);
			 	spPokazMet.setVisible(true);
			 	tabPokazMet.setVisible(true);
			 	}
			 });
		
		JLabel lblVidIssl = new JLabel("Вид исследования");
		
		cbVidIssl = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
		cbVidIssl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cbVidIssl.getSelectedItem()!= null){
					try {
						if (rbMetodIssl.isSelected()){//
								tabMetod.setData(MainForm.tcl.getMetod(cbVidIssl.getSelectedItem().pcod));
						}
						if (rbPokaz.isSelected()){
							cbOrgan.setSelectedItem(null);
							cbOrgan.setData(MainForm.tcl.get_n_nz1(cbVidIssl.getSelectedItem().pcod));	
								}
						} catch (KmiacServerException e) {
							e.printStackTrace();
						} catch (TException e) {
							MainForm.conMan.reconnect(e);
							
						}
					}
										
				}
		});
		
		JButton butPrint = new JButton("Печать");
		butPrint.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rbMetodIssl.isSelected()){
					if ((cbVidIssl.getSelectedItem() != null) && (tabMetod.getSelectedItem() != null)) {
						List<String> selItems = new ArrayList<>();
						for (PokazMet pokazMet : tabPokazMet.getData()) {
							if (pokazMet.vybor)
								selItems.add(pokazMet.getPcod());
						}
						if (selItems.size() != 0) {
							IsslMet isslmet = new IsslMet();
							isslmet.setKodVidIssl(cbVidIssl.getSelectedItem().getPcod());
							isslmet.setUserId(MainForm.authInfo.getUser_id());
							isslmet.setNpasp(Vvod.zapVr.getNpasp());
							isslmet.setKodMetod(tabMetod.getSelectedItem().getObst());
							isslmet.setPokaz(selItems);
							if (cbMesto.getSelectedItem()!=null)isslmet.setMesto(cbMesto.getSelectedItem().getName());
							isslmet.setKab(getTextOrNull(tfKab.getText()));
							String servPath = MainForm.tcl.printIsslMetod(isslmet);
							String cliPath = File.createTempFile("muzdrav", ".htm").getAbsolutePath();
							MainForm.conMan.transferFileFromServer(servPath, cliPath);	
						}
					}
				}
					/*Napr napr=new Napr();
					 * MainForm.tcl.printNapr(napr)
					 * napr.setnpasp(Vvod.ZapVr.getNpasp());
					 * napr.setuserId(MainForm.authInfo.pcod);
					 * napr.setobosnov(tfob.gettext);
					 * napr.setclpu(cblpu.getselectedpcod)*/
					
					/*
					 * NaprKons naprkons=new NaprKons
					 * * MainForm.tcl.printNaprKons(naprkons)
					 * naprkons.setnpasp(Vvod.ZapVr.getNpasp());
					 * naprkons.setuserId(MainForm.authInfo.pcod);
					 * naprkons.setobosnov(tfob.gettext);
					 * naprkons.setcpol(cbpol.getselectedpcod)*/
					
					/*
					 * * MainForm.tcl.printVypis(Vvod.ZapVr.getNpasp(),MainForm.authInfo.pcod(),vvod.TabPos.getSelectedItem.getId_obr);
				*/
					
						/*
					 * * MainForm.tcl.printKek(Vvod.ZapVr.getNpasp(),vvod.TabPos.getSelectedItem.getId_obr);
				*/
				if (rbPokaz.isSelected()){
					if ((cbVidIssl.getSelectedItem() != null) && (cbOrgan.getSelectedItem() != null)) {
							List<String> selItems = new ArrayList<>();
							for (Pokaz pokaz : tabPokaz.getData()) {
								if (pokaz.vybor)
									selItems.add(pokaz.getPcod());
							}
							if (selItems.size() != 0) {
								IsslPokaz pokaz = new IsslPokaz();
								pokaz.setKodVidIssl(cbVidIssl.getSelectedItem().getPcod());
								pokaz.setUserId(MainForm.authInfo.getUser_id());
								pokaz.setNpasp(Vvod.zapVr.getNpasp());
								pokaz.setPokaz(selItems);
								pokaz.setMesto(cbMesto.getSelectedItem().getName());
								pokaz.setKab(tfKab.getText());
								String servPath = MainForm.tcl.printIsslPokaz(pokaz);
								String cliPath = File.createTempFile("muzdrav", ".htm").getAbsolutePath();
								MainForm.conMan.transferFileFromServer(servPath, cliPath);
							
							}
						}
					}
//						List<String> selItems = new ArrayList<>();
//						for (Pokaz pokaz : tabPokaz.getData()) {
//							if (pokaz.vybor)
//								selItems.add(pokaz.getPcod());
//						}
//						if (selItems.size() != 0) {
//							String servPath = MainForm.tcl.printIsslPokaz(cbVidIssl.getSelectedItem().pcod, MainForm.authInfo.user_id, Vvod.zapVrSave.getNpasp(), cbSys.getSelectedItem().pcod, selItems);
//							String cliPath = File.createTempFile("muzdrav", ".htm").getAbsolutePath();
//							MainForm.conMan.transferFileFromServer(servPath, cliPath);	
//						}
//					}
//				}
				}
					catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		
		
		GroupLayout gl_pNaprIssl = new GroupLayout(pNaprIssl);
		gl_pNaprIssl.setHorizontalGroup(
			gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pNaprIssl.createSequentialGroup()
					.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
								.addComponent(lblmet, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_pNaprIssl.createSequentialGroup()
									.addComponent(spMetod, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(spPokaz, GroupLayout.PREFERRED_SIZE, 368, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblPokazMet, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_pNaprIssl.createSequentialGroup()
									.addComponent(spPokazMet, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(butPrint))
								.addGroup(gl_pNaprIssl.createSequentialGroup()
									.addComponent(lblMesto, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(cbMesto, GroupLayout.PREFERRED_SIZE, 302, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_pNaprIssl.createSequentialGroup()
									.addComponent(lblKab, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE)
									.addGap(4)
									.addComponent(tfKab, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
								.addComponent(rbMetodIssl)
								.addGroup(gl_pNaprIssl.createSequentialGroup()
									.addContainerGap()
									.addComponent(lblVidIssl, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_pNaprIssl.createSequentialGroup()
									.addGap(10)
									.addComponent(cbVidIssl, GroupLayout.PREFERRED_SIZE, 391, GroupLayout.PREFERRED_SIZE)))
							.addGap(34)
							.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
								.addComponent(lblOrgan, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
								.addComponent(rbPokaz, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
								.addComponent(cbOrgan, GroupLayout.PREFERRED_SIZE, 272, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap())
		);
		gl_pNaprIssl.setVerticalGroup(
			gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pNaprIssl.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addComponent(rbPokaz)
							.addGap(2)
							.addComponent(lblOrgan)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbOrgan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addComponent(rbMetodIssl)
							.addGap(2)
							.addComponent(lblVidIssl)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbVidIssl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(11)
					.addComponent(lblmet)
					.addGap(8)
					.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addComponent(spMetod, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(lblPokazMet)
							.addGap(8)
							.addComponent(spPokazMet, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pNaprIssl.createSequentialGroup()
									.addGap(3)
									.addComponent(lblMesto))
								.addComponent(cbMesto, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(11)
							.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pNaprIssl.createSequentialGroup()
									.addGap(3)
									.addComponent(lblKab))
								.addComponent(tfKab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addComponent(spPokaz, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
							.addGap(57)
							.addComponent(butPrint)))
					.addContainerGap(159, Short.MAX_VALUE))
		);
		
		tabPokaz = new CustomTable<>(false, true, Pokaz.class, 0,"Код показателя",1,"Наименование",2,"Стоимость",5,"Выбор");
		tabPokaz.setEditableFields(true, 3);
		spPokaz.setViewportView(tabPokaz);
		tabPokaz.setFillsViewportHeight(true);
		
		tabPokazMet = new CustomTable<>(false,true,PokazMet.class,0,"Код",1,"Наименование",2,"Стоимость",4,"Выбор");
		tabPokazMet.setEditableFields(true, 3);
		spPokazMet.setViewportView(tabPokazMet);
		tabPokazMet.setFillsViewportHeight(true);
		
		tabMetod = new CustomTable<>(false,true,Metod.class,0,"Код",1,"Наименование");;
		spMetod.setViewportView(tabMetod);
		tabMetod.setFillsViewportHeight(true);
		tabMetod.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting()){
					if (tabMetod.getSelectedItem()!=null){
						try {
							tabPokazMet.setData(MainForm.tcl.getPokazMet(tabMetod.getSelectedItem().getObst()));
						} catch (KmiacServerException e) {
							e.printStackTrace();
						} catch (TException e) {
							MainForm.conMan.reconnect(e);
						}
					}
				}	
			}
		});
		

		pNaprIssl.setLayout(gl_pNaprIssl);
		
		final JPanel pKons = new JPanel();
		tabPrint.addTab("Направление", null, pKons, null);
		
		JLabel lblN00 = new JLabel("Куда");
		
		 cbN00 = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
		
		cbVidNapr = new JComboBox();
		cbVidNapr.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					try {
						if (cbVidNapr.getSelectedIndex() != 0)
						cbN00.setData(MainForm.tcl.get_n_n00());
					}
					 catch (KmiacServerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TException e1) {
						// TODO Auto-generated catch block
						MainForm.conMan.reconnect(e1);
					}
				}
		});
		cbVidNapr.setModel(new DefaultComboBoxModel(new String[] {"госпитализацию", "консультацию", "обследование"}));
		
		lblObosnov = new JLabel("<html>Обоснование для <br>\r\nнаправления");
		
		final JEditorPane tpObosnov = new JEditorPane();
		tpObosnov.setBorder(UIManager.getBorder("TextField.border"));
		
		butPrintNapr = new JButton("Печать");
		butPrintNapr.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			try{
				if (cbVidNapr.getSelectedIndex() != 0){
					NaprKons naprkons = new NaprKons();
					naprkons.setUserId(MainForm.authInfo.getUser_id());
					naprkons.setNpasp(Vvod.zapVr.getNpasp());
					naprkons.setObosnov(tpObosnov.getText());
					if (cbN00.getSelectedItem()!=null) naprkons.setCpol(cbN00.getSelectedItem().getPcod());
					naprkons.setNazv(cbVidNapr.getSelectedItem().toString());
					naprkons.setCdol(MainForm.authInfo.getCdol());
					naprkons.setPvizitId(TabPos.getSelectedItem().getId_obr());
					String servPath = MainForm.tcl.printNaprKons(naprkons);
					String cliPath;
					cliPath = File.createTempFile("napr", ".htm").getAbsolutePath();
					MainForm.conMan.transferFileFromServer(servPath, cliPath);
				}
				else {
					Napr napr = new Napr();
					napr.setUserId(MainForm.authInfo.getUser_id());
					napr.setNpasp(Vvod.zapVr.getNpasp());
					napr.setObosnov(tpObosnov.getText());
					napr.setClpu(cbN00.getSelectedItem().getPcod());
					String servPath = MainForm.tcl.printNapr(napr);
					String cliPath;
					cliPath = File.createTempFile("napr", ".htm").getAbsolutePath();
					MainForm.conMan.transferFileFromServer(servPath, cliPath);	
				}
				
				
				
			}
			catch (TException e1) {
				e1.printStackTrace();
				MainForm.conMan.reconnect(e1);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			}
		});
		
		JLabel label = new JLabel("на");
		
		GroupLayout gl_pKons = new GroupLayout(pKons);
		gl_pKons.setHorizontalGroup(
			gl_pKons.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pKons.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pKons.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pKons.createSequentialGroup()
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(cbVidNapr, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pKons.createSequentialGroup()
							.addComponent(lblN00)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbN00, GroupLayout.PREFERRED_SIZE, 480, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pKons.createSequentialGroup()
							.addComponent(lblObosnov, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tpObosnov, GroupLayout.PREFERRED_SIZE, 412, GroupLayout.PREFERRED_SIZE))
						.addComponent(butPrintNapr))
					.addContainerGap(294, Short.MAX_VALUE))
		);
		gl_pKons.setVerticalGroup(
			gl_pKons.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pKons.createSequentialGroup()
					.addGap(5)
					.addGroup(gl_pKons.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(cbVidNapr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pKons.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblN00)
						.addComponent(cbN00, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_pKons.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pKons.createSequentialGroup()
							.addGap(18)
							.addComponent(tpObosnov, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pKons.createSequentialGroup()
							.addGap(37)
							.addComponent(lblObosnov)))
					.addGap(17)
					.addComponent(butPrintNapr)
					.addContainerGap(577, Short.MAX_VALUE))
		);
		pKons.setLayout(gl_pKons);
		pprint.setLayout(gl_pprint);
		
		JPanel pOcenka = new JPanel();
		tabbedPane.addTab("Оценка данных анамнеза", null, pOcenka, null);
		
		 tpOcenka = new JEditorPane();
		 tpOcenka.setBorder(UIManager.getBorder("TextField.border"));
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
			TabDiag.setData(MainForm.tcl.getPdiagAmb(zapVr.getId_pvizit()));
			//c_obr.setSelectedPcod(TabPos.getSelectedItem().getId_obr());
		} catch (KmiacServerException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
			MainForm.conMan.reconnect(e);
		} catch (PvizitNotFoundException e) {
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
			cbVidIssl.setData(MainForm.tcl.get_n_p0e1());
			cbMesto.setData(MainForm.tcl.get_n_lds(MainForm.authInfo.clpu));
			cbN00.setData(MainForm.tcl.get_n_m00(MainForm.authInfo.clpu));
			
			
		} catch (KmiacServerException e) {
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
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}
