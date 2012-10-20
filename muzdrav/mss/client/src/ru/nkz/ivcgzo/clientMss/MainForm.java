package ru.nkz.ivcgzo.clientMss;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTimeEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftMss.MestnNotFoundException;
import ru.nkz.ivcgzo.thriftMss.MssNotFoundException;
import ru.nkz.ivcgzo.thriftMss.P_smert;
import ru.nkz.ivcgzo.thriftMss.PatientCommonInfo;
import ru.nkz.ivcgzo.thriftMss.PatientMestn;
import ru.nkz.ivcgzo.thriftMss.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftMss.Psmertdop;
import ru.nkz.ivcgzo.thriftMss.MssdopNotFoundException;
import ru.nkz.ivcgzo.thriftMss.ThriftMss;
import ru.nkz.ivcgzo.thriftMss.UserFio;
import ru.nkz.ivcgzo.thriftMss.UserNotFoundException;

import javax.swing.JCheckBox;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

import org.apache.thrift.TException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainForm extends Client<ThriftMss.Client> {
	public static ThriftMss.Client tcl;
	public static Client<ThriftMss.Client> instance; 
	private JFrame frame;
	private final ButtonGroup BtnGroup_ms = new ButtonGroup();
	private final ButtonGroup BtnGroup_mjit = new ButtonGroup();
	private final ButtonGroup BtnGroup_don = new ButtonGroup();
	private ThriftIntegerClassifierCombobox<IntegerClassifier> tfAds_obl;
	private CustomTextField tfAds_raion;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> tfAds_gorod;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> tfAds_ul;
	private CustomTextField tfAds_dom;
	private CustomTextField tfAds_korp;
	private CustomTextField tfAds_kv;
	private CustomTextField tfves;
	private CustomTextField tfOt_m;
	private CustomTextField tfFam_m;
	private CustomTextField tfIm_m;
	private CustomDateEditor tfdatarm;
	private CustomDateEditor tfDatav;
	private CustomTextField tfNomer;
	private CustomTextField tfSer;
	private CustomDateEditor tfVz_datav;
	private CustomTextField tfVz_nomer;
	private CustomTextField tfVz_ser;
	private CustomTextField tfFam;
	private CustomTextField tfIm;
	private CustomTextField tfOt;
	private CustomTextField tfPol;
	private CustomDateEditor tfDatar;
	private CustomDateEditor tfDatas;
	private CustomTimeEditor tfVrems;
	private JTextField tfAdm_obl;
	private CustomTextField tfAdm_raion;
	private CustomTextField tfAdm_gorod;
	private CustomTextField tfAdm_ul;
	private CustomTextField tfAdm_dom;
	private CustomTextField tfAdm_korp;
	private CustomTextField tfAdm_kv;
	private JRadioButton rdbtnMjit_gor;
	private JRadioButton rdbtnMjit_selo;
	private JRadioButton rdbtnMs_gor;
	private JRadioButton rdbtnMs_selo;
	private JRadioButton rdbtn_don_don;
	private JRadioButton rdbtn_don_ned;
	private JRadioButton rdbtn_don_peren;
	private CustomTextField tfNreb;
	private CustomTextField tfMrojd;
	private CustomDateEditor tfDatatr;
	private CustomTimeEditor tfVrem_tr;
	private CustomTextField tfObst;
	private CustomTextField tfPsm_a;
	private JTextArea tfPsm_an;
	private CustomTextField tfPsm_ak;
	private CustomTextField tfPsm_b;
	private JTextArea tfPsm_bn;
	private CustomTextField tfPsm_bk;
	private CustomTextField tfPsm_v;
	private JTextArea tfPsm_vn;
	private CustomTextField tfPsm_vk;
	private CustomTextField tfPsm_g;
	private JTextArea tfPsm_gn;
	private CustomTextField tfPsm_gk;
	private CustomTextField tfPsm_p;
	private JTextArea tfPsm_pn;
	private CustomTextField tfPsm_pk;
	private CustomTextField tfPsm_p1;
	private JTextArea tfPsm_p1n;
	private CustomTextField tfPsm_p2;
	private JTextArea tfPsm_p2n;
	private CustomTextField tfPsm_p1k;
	private CustomTextField tfPsm_p2k;
	private CustomTextField tfZapolnil;
	private CustomTextField tfFam_pol;
	private CustomTextField tfSdok;
	private CustomTextField tfNdok;
	private CustomDateEditor tfDvdok;
	private CustomTextField tfKvdok;
	private JCheckBox chckbxDtp30;
	private JCheckBox chckbxDtp7;
	private JPanel panel_6;
	
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmbVid;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_semp;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_obraz;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_zan;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmbNastupila;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmbProiz;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmbVid_tr;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmbUstan;
	private ThriftStringClassifierCombobox <StringClassifier> cmbCdol;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmbOsn;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmbPsm_ad;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmbPsm_bd;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmbPsm_vd;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmbPsm_ag;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmbPsm_pd;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmbPsm_p1d;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmbPsm_p2d;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmbVdok;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmbUmerla;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> tfCvrach;
	
	private P_smert Patientsmert;
	private PatientCommonInfo PatientAdres;
	private PatientMestn PatMestn;
	private UserFio UFio;
	private Psmertdop nomerMss;
	
	private int patId = 0;
	private float ktime = 0;
	private long drojd;
	private long dsmert;
	private boolean aBool = false;
	private int cuserId = 0;
	private int cuserPodr = 0;
	private int cuserCslu = 0;
	private int cuserClpu = 0;
	private String cuserFio;
	private DopInfoForm dopInfo;
	private int nextNomer = 0;
	private String nomMonth;
	SimpleDateFormat sdfDay = new SimpleDateFormat("dd"); 
	SimpleDateFormat sdfMonth = new SimpleDateFormat("MM");
	SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");
	SimpleDateFormat sdfTime = new SimpleDateFormat("hh.mm");
	SimpleDateFormat sdfData = new SimpleDateFormat("dd.MM.yyyy");

	private String myPath = "C:\\work\\templates\\ShMss.htm";

	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(conMan, authInfo, ThriftMss.Client.class, configuration.appId, configuration.thrPort, lncPrm);
		
		try {
			cmbVdok = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_az0);
			
		}
			catch (Exception e) {
				e.printStackTrace();
			} 
	
		initialize();
		
		setFrame(frame);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel);
		
		JButton btnNewButton = new JButton("Поиск");
		btnNewButton.setToolTipText("Поиск и выбор пациента");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int[] res = MainForm.conMan.showPatientSearchForm("Поиск пациента", true, true);
				tfDatav.setValue(null);
				cuserId = MainForm.authInfo.getPcod();
				cuserClpu = MainForm.authInfo.getClpu();
				cuserCslu = MainForm.authInfo.getCslu();
				cuserPodr = MainForm.authInfo.getCpodr();
				try {
					nomerMss = MainForm.tcl.getPsmertdop(cuserPodr, cuserCslu, cuserClpu);
					nextNomer = nomerMss.nomer_t+1;
					
				} catch (MssdopNotFoundException | TException e0) {
					
					e0.printStackTrace();
				}
			
					try {
						UFio = MainForm.tcl.getUserFio(cuserId);
						cuserFio = UFio.getFam().trim() + ' '+ UFio.getIm().trim()+ ' ' + UFio.getOt().trim();
					} catch (UserNotFoundException | TException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					if (res != null) {
					try {
						patId = res[0];
						PatientAdres = MainForm.tcl.getPatientCommonInfo(res[0]);
						if (PatientAdres.getFam() != null) {					
							tfFam.setText(PatientAdres.getFam().trim());
						}
						if (PatientAdres.getIm() != null) {
							tfIm.setText(PatientAdres.im.trim());
						}
						if (PatientAdres.getOt() != null) {
							tfOt.setText(PatientAdres.ot.trim());
						}
						if (PatientAdres.getPol() == 1) 
							tfPol.setText("мужской");
						if (PatientAdres.getPol() == 2)
							tfPol.setText("женский");
						if (PatientAdres.isSetDatar()) {
							tfDatar.setDate(PatientAdres.datar);
							drojd = PatientAdres.datar;
						}
						if (PatientAdres.getAdm_obl() != null) {
							tfAdm_obl.setText(PatientAdres.adm_obl.trim());
							tfAds_obl.setText(PatientAdres.adm_obl.trim());
						}
						if (PatientAdres.getAdm_gorod() != null) {
							tfAdm_gorod.setText(PatientAdres.adm_gorod.trim());
							tfAds_gorod.setText(PatientAdres.adm_gorod.trim());
						}
						if (PatientAdres.getAdm_ul() != null) {
							tfAdm_ul.setText(PatientAdres.adm_ul.trim());
							tfAds_ul.setText(PatientAdres.adm_ul.trim());
						}
						if (PatientAdres.getAdm_dom() != null) {
							tfAdm_dom.setText(PatientAdres.adm_dom.trim());
							tfAds_dom.setText(PatientAdres.adm_dom.trim());
						}
						if (PatientAdres.getAdm_korp() != null) {
							tfAdm_korp.setText(PatientAdres.adm_korp.trim());
							tfAds_korp.setText(PatientAdres.adm_korp.trim());
						}
						if (PatientAdres.getAdm_kv() != null) {
							tfAdm_kv.setText(PatientAdres.adm_kv.trim());
							tfAds_kv.setText(PatientAdres.adm_kv.trim());
						}
						int region = PatientAdres.getRegion_liv();
						//System.out.println(PatientAdres.adm_gorod);
						try {
							PatMestn = MainForm.tcl.getL00(region, PatientAdres.adm_gorod.trim());
							//System.out.println(PatMestn.vid_np);

						} catch (MestnNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} 
						if ((PatMestn.getVid_np() == 1) || (PatMestn.getVid_np() == 2)) {
							rdbtnMjit_gor.setSelected(true);
							rdbtnMs_gor.setSelected(true);
							rdbtnMjit_selo.setSelected(false);
							rdbtnMs_selo.setSelected(false);
						}
						if ((PatMestn.getVid_np() != 1) && (PatMestn.getVid_np() != 2)) {
							rdbtnMjit_selo.setSelected(true);
							rdbtnMs_selo.setSelected(true);
						}
						try {
							UFio = MainForm.tcl.getUserFio(cuserId);
							cuserFio = UFio.getFam().trim() + ' '+ UFio.getIm().trim()+ ' ' + UFio.getOt().trim();
							tfZapolnil.setText(cuserFio);
						} catch (UserNotFoundException | TException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						try {
						Patientsmert = MainForm.tcl.getPsmert(res[0]);
						tfSer.setText(String.valueOf(Patientsmert.ser));
						tfNomer.setText(String.valueOf(Patientsmert.nomer));
						if (Patientsmert.isSetDatav()) tfDatav.setDate(Patientsmert.datav);
						if (Patientsmert.getVid() != 0) cmbVid.setSelectedPcod(Patientsmert.vid);
						if (Patientsmert.getVz_ser() != 0) tfVz_ser.setText(String.valueOf(Patientsmert.vz_ser));
						if (Patientsmert.getVz_nomer() != 0) tfVz_nomer.setText(String.valueOf(Patientsmert.vz_nomer));
						if (Patientsmert.isSetVz_datav()) tfVz_datav.setDate(Patientsmert.vz_datav);
						if (Patientsmert.isSetDatas()) tfDatas.setDate(Patientsmert.datas);
						if (Patientsmert.isSetVrems()) tfVrems.setTime(Patientsmert.vrems);
						if (Patientsmert.getAdm_raion() != null) tfAdm_raion.setText(Patientsmert.adm_raion);
						if (Patientsmert.getAds_obl() != null) tfAds_obl.setText(Patientsmert.ads_obl);
						if (Patientsmert.getAds_raion() != null) tfAds_raion.setText(Patientsmert.ads_raion);
						if (Patientsmert.getAds_gorod() != null) tfAds_gorod.setText(Patientsmert.ads_gorod);
						if (Patientsmert.getAds_ul() != null) tfAds_ul.setText(Patientsmert.ads_ul);
						if (Patientsmert.getAds_dom() != null) tfAds_dom.setText(Patientsmert.ads_dom);
						if (Patientsmert.getAds_korp() != null) tfAds_korp.setText(Patientsmert.ads_korp);
						if (Patientsmert.getAds_kv() != null) tfAds_kv.setText(Patientsmert.ads_kv);
						if (Patientsmert.getAds_mestn() == 1) rdbtnMs_gor.setSelected(true);
						if (Patientsmert.getAds_mestn() == 2) rdbtnMs_selo.setSelected(true);
						if (Patientsmert.getSemp() != 0) cmb_semp.setSelectedPcod(Patientsmert.semp);
						if (Patientsmert.getObraz() != 0) cmb_obraz.setSelectedPcod(Patientsmert.obraz);
						if (Patientsmert.getZan() != 0) cmb_zan.setSelectedPcod(Patientsmert.zan);
						if (Patientsmert.getDon() == 1) rdbtn_don_don.setSelected(true);
						if (Patientsmert.getDon() == 2) rdbtn_don_ned.setSelected(true);
						if (Patientsmert.getDon() == 3) rdbtn_don_peren.setSelected(true);
						if (Patientsmert.getVes() != 0) tfves.setText(String.valueOf(Patientsmert.ves));
						if (Patientsmert.getNreb() != 0) tfNreb.setText(String.valueOf(Patientsmert.nreb));
						if (Patientsmert.getFam_m() != null) tfFam_m.setText(Patientsmert.fam_m);
						if (Patientsmert.getIm_m() != null) tfIm_m.setText(Patientsmert.im_m);
						if (Patientsmert.getOt_m() != null) tfOt_m.setText(Patientsmert.ot_m);
						if (Patientsmert.getMrojd() != null) tfMrojd.setText(Patientsmert.mrojd);
						if (Patientsmert.isSetDatarm()) tfdatarm.setDate(Patientsmert.datarm);
						if (Patientsmert.getNastupila() != 0) cmbNastupila.setSelectedPcod(Patientsmert.nastupila);
						if (Patientsmert.getProiz() != 0) cmbProiz.setSelectedPcod(Patientsmert.proiz);
						if (Patientsmert.getVid_tr() != 0) cmbVid_tr.setSelectedPcod(Patientsmert.vid_tr);
						if (Patientsmert.isSetDatatr()) tfDatatr.setDate(Patientsmert.datatr);
						if (Patientsmert.isSetVrem_tr()) tfVrem_tr.setTime(Patientsmert.vrem_tr);
						if (Patientsmert.getObst() != null) tfObst.setText(Patientsmert.obst);
						if (Patientsmert.getUstan() != 0) cmbUstan.setSelectedPcod(Patientsmert.ustan);
						if (Patientsmert.getOsn() != 0) cmbOsn.setSelectedPcod(Patientsmert.osn);
						if (Patientsmert.getCvrach() != 0) tfCvrach.setSelectedPcod(Patientsmert.cvrach);
						if (Patientsmert.getCdol() != null) cmbCdol.setSelectedPcod(Patientsmert.cdol);
						if (Patientsmert.getPsm_a() != null) tfPsm_a.setText(Patientsmert.psm_a);
						if (Patientsmert.getPsm_an() != "") tfPsm_an.setText(Patientsmert.psm_an);
						if (Patientsmert.getPsm_ak() != 0) tfPsm_ak.setText(String.valueOf(Patientsmert.psm_ak));
						if (Patientsmert.getPsm_ad() != 0) cmbPsm_ad.setSelectedPcod(Patientsmert.psm_ad);
						if (Patientsmert.getPsm_b() != null) tfPsm_b.setText(Patientsmert.psm_b);
						if (Patientsmert.getPsm_bn() != null) tfPsm_bn.setText(Patientsmert.psm_bn);
						if (Patientsmert.getPsm_bk() != 0) tfPsm_bk.setText(String.valueOf(Patientsmert.psm_bk));
						if (Patientsmert.getPsm_bd() != 0) cmbPsm_bd.setSelectedPcod(Patientsmert.psm_bd);
						if (Patientsmert.getPsm_v() != null) tfPsm_v.setText(Patientsmert.psm_v);
						if (Patientsmert.getPsm_vn() != null) tfPsm_vn.setText(Patientsmert.psm_vn);
						if (Patientsmert.getPsm_vk() != 0) tfPsm_vk.setText(String.valueOf(Patientsmert.psm_vk));
						if (Patientsmert.getPsm_vd() != 0) cmbPsm_vd.setSelectedPcod(Patientsmert.psm_vd);
						if (Patientsmert.getPsm_g() != null) tfPsm_g.setText(Patientsmert.psm_g);
						if (Patientsmert.getPsm_gn() != null) tfPsm_gn.setText(Patientsmert.psm_gn);
						if (Patientsmert.getPsm_gk() != 0) tfPsm_gk.setText(String.valueOf(Patientsmert.psm_gk));
						if (Patientsmert.getPsm_gd() != 0) cmbPsm_ag.setSelectedPcod(Patientsmert.psm_gd);
						if (Patientsmert.getPsm_p() != null) tfPsm_p.setText(Patientsmert.psm_p);
						if (Patientsmert.getPsm_pn() != null) tfPsm_pn.setText(Patientsmert.psm_pn);
						if (Patientsmert.getPsm_pk() != 0) tfPsm_pk.setText(String.valueOf(Patientsmert.psm_pk));
						if (Patientsmert.getPsm_pd() != 0) cmbPsm_pd.setSelectedPcod(Patientsmert.psm_pd);
						if (Patientsmert.getPsm_p1() != null) tfPsm_p1.setText(Patientsmert.psm_p1);
						if (Patientsmert.getPsm_p1n() != null) tfPsm_p1n.setText(Patientsmert.psm_p1n);
						if (Patientsmert.getPsm_p1k() != 0) tfPsm_p1k.setText(String.valueOf(Patientsmert.psm_p1k));
						if (Patientsmert.getPsm_p1d() != 0) cmbPsm_p1d.setSelectedPcod(Patientsmert.psm_p1d);
						if (Patientsmert.getPsm_p2() != null) tfPsm_p2.setText(Patientsmert.psm_p2);
						if (Patientsmert.getPsm_p2n() != null) tfPsm_p2n.setText(Patientsmert.psm_p2n);
						if (Patientsmert.getPsm_p2k() != 0) tfPsm_p2k.setText(String.valueOf(Patientsmert.psm_p2k));
						if (Patientsmert.getPsm_p2d() != 0) cmbPsm_p2d.setSelectedPcod(Patientsmert.psm_p2d);
						if (Patientsmert.getDtp() == 1) { chckbxDtp30.setSelected(true);
						chckbxDtp7.setSelected(false);}
						if (Patientsmert.getDtp() == 2) { chckbxDtp30.setSelected(true);
						chckbxDtp7.setSelected(true);}
						if (Patientsmert.getUmerla() !=0) cmbUmerla.setSelectedPcod(Patientsmert.umerla); 
						if (Patientsmert.getCuser() != 0) cuserId = Patientsmert.getCuser(); 
						if (Patientsmert.getFio_pol() != null) tfFam_pol.setText(Patientsmert.fio_pol.trim());
						if (Patientsmert.getVdok() != 0) cmbVdok.setSelectedPcod(Patientsmert.vdok);
						if (Patientsmert.getSdok() != null) tfSdok.setText(Patientsmert.sdok.trim());
						if (Patientsmert.getNdok() != null) tfNdok.setText(Patientsmert.ndok.trim());
						if (Patientsmert.getKvdok() != null) tfKvdok.setText(Patientsmert.kvdok.trim());
						if (Patientsmert.isSetDvdok()) tfDvdok.setDate(Patientsmert.dvdok);
						aBool = changeValue();
						} catch (MssNotFoundException e1) {
							tfSer.setText("32");
							tfNomer.setText(String.valueOf(nextNomer).trim());
							
						//	JOptionPane.showMessageDialog(frame, String.format("Нет информации на пациента %d", res[0]));
					}
					} catch (TException e1) {
						e1.printStackTrace();
						MainForm.conMan.reconnect(e1);
					} catch (KmiacServerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (PatientNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		panel.add(btnNewButton);
		
		JButton btnSave = new JButton("Сохранить");
		btnSave.setToolTipText("Сохранить информацию о пациенте");
		btnSave.setVerticalAlignment(SwingConstants.TOP);
		panel.add(btnSave);
		btnSave.addActionListener (new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Patientsmert = new P_smert();
					Patientsmert.setNpasp(patId);
					if (tfSer.getText().isEmpty()) Patientsmert.setSer(32); 
					else
						Patientsmert.setSer(Integer.valueOf(tfSer.getText()));
					if (tfNomer.getText().isEmpty()) Patientsmert.setNomer(0);
					else
						Patientsmert.setNomer(Integer.valueOf(tfNomer.getText()));
					if (cmbVid.getSelectedItem() != null) Patientsmert.setVid(cmbVid.getSelectedPcod());
					else
						Patientsmert.setVidIsSet(false);
					if (tfDatav.getDate() != null) Patientsmert.setDatav(tfDatav.getDate().getTime());
					if (tfVz_ser.getText().isEmpty()) Patientsmert.setVz_serIsSet(false);
					else
						Patientsmert.setVz_ser(Integer.valueOf(tfVz_ser.getText()));
					if (tfVz_nomer.getText().isEmpty()) Patientsmert.setVz_nomerIsSet(false);
					else
						Patientsmert.setVz_nomer(Integer.valueOf(tfVz_nomer.getText()));
					if (tfVz_datav.getDate() != null) Patientsmert.setVz_datav(tfVz_datav.getDate().getTime());
					else Patientsmert.setVz_datavIsSet(false);
					if (tfDatas.getDate() != null) Patientsmert.setDatas(tfDatas.getDate().getTime());
					else Patientsmert.setDatasIsSet(false);
					if (tfVrems.getTime() != null) Patientsmert.setVrems(tfVrems.getTime().getTime());
					else Patientsmert.setVremsIsSet(false);
					Patientsmert.setAdm_raion(tfAdm_raion.getText().trim());
					Patientsmert.setAds_obl(tfAds_obl.getText().trim());
					Patientsmert.setAds_raion(tfAds_raion.getText().trim());
					Patientsmert.setAds_gorod(tfAds_gorod.getText().trim());
					Patientsmert.setAds_ul(tfAds_ul.getText().trim());
					Patientsmert.setAds_dom(tfAds_dom.getText().trim());
					Patientsmert.setAds_korp(tfAds_korp.getText().trim());
					Patientsmert.setAds_kv(tfAds_kv.getText().trim());
					if (rdbtnMs_gor.isSelected()) Patientsmert.setAds_mestn(1);
					if (rdbtnMs_selo.isSelected()) Patientsmert.setAds_mestn(2);
					if (cmb_semp.getSelectedItem() != null) Patientsmert.setSemp(cmb_semp.getSelectedPcod());
					else Patientsmert.setSempIsSet(false);
					if (cmb_obraz.getSelectedItem() != null) Patientsmert.setObraz(cmb_obraz.getSelectedPcod());
					else Patientsmert.setObrazIsSet(false);
					if (cmb_zan.getSelectedItem() != null) Patientsmert.setZan(cmb_zan.getSelectedPcod());
					else Patientsmert.setZanIsSet(false);
					aBool = changeValue();
					if (aBool == true) { 
					if (rdbtn_don_don.isSelected()) Patientsmert.setDon(1);
					if (rdbtn_don_ned.isSelected()) Patientsmert.setDon(2);
					if (rdbtn_don_peren.isSelected()) Patientsmert.setDon(3);
					if (!tfves.getText().isEmpty()) Patientsmert.setVes(Integer.valueOf(tfves.getText()));
					else Patientsmert.setVesIsSet(false);
					if (!tfNreb.getText().isEmpty()) Patientsmert.setNreb(Integer.valueOf(tfNreb.getText()));
					else Patientsmert.setNrebIsSet(false);
					Patientsmert.setFam_m(tfFam_m.getText().trim());
					Patientsmert.setIm_m(tfIm_m.getText().trim());
					Patientsmert.setOt_m(tfOt_m.getText().trim());
					Patientsmert.setMrojd(tfMrojd.getText().trim());
					if (tfdatarm.getDate() != null) Patientsmert.setDatarm(tfdatarm.getDate().getTime());
					else Patientsmert.setDatarmIsSet(false);
					}
					else {
						if (Patientsmert.isSetDon()) Patientsmert.setDon(0);
						if (Patientsmert.isSetVes()) Patientsmert.setVes(0);
						if (Patientsmert.isSetNreb()) Patientsmert.setNreb(0);
						if (Patientsmert.isSetFam_m()) Patientsmert.setFam_m("");
						if (Patientsmert.isSetIm_m()) Patientsmert.setIm_m("");
						if (Patientsmert.isSetOt_m()) Patientsmert.setOt_m("");
						if (Patientsmert.isSetMrojd()) Patientsmert.setMrojd("");
						if (Patientsmert.isSetDatarm()) Patientsmert.setDatarm(0);
						rdbtn_don_don.setSelected(false);
						rdbtn_don_ned.setSelected(false);
						rdbtn_don_peren.setSelected(false);
						tfves.setText("");
						tfNreb.setText("");
						tfFam_m.setText("");
						tfIm_m.setText("");
						tfOt_m.setText("");
						tfMrojd.setText("");
						tfdatarm.setText("");
					}
					if (cmbNastupila.getSelectedItem() != null) Patientsmert.setNastupila(cmbNastupila.getSelectedPcod());
					else Patientsmert.setNastupilaIsSet(false);
					if (cmbProiz.getSelectedItem() != null) Patientsmert.setProiz(cmbProiz.getSelectedPcod());
					else Patientsmert.setProizIsSet(false);
					if (tfDatatr.getDate() != null) Patientsmert.setDatatr(tfDatatr.getDate().getTime());
					else Patientsmert.setDatatrIsSet(false);
					if (tfVrem_tr.getTime() != null) Patientsmert.setVrem_tr(tfVrem_tr.getTime().getTime());
					else Patientsmert.setVrem_trIsSet(false);
					if (cmbVid_tr.getSelectedItem() != null) Patientsmert.setVid_tr(cmbVid_tr.getSelectedPcod());
					else Patientsmert.setVid_trIsSet(false);
					Patientsmert.setObst(tfObst.getText().trim());
					if (cmbUstan.getSelectedItem() != null) Patientsmert.setUstan(cmbUstan.getSelectedPcod());
					else Patientsmert.setUstanIsSet(false);
					if (tfCvrach.getSelectedItem() != null) Patientsmert.setCvrach(tfCvrach.getSelectedPcod());
					else Patientsmert.setCvrachIsSet(false);
					if (cmbCdol.getSelectedItem() != null) Patientsmert.setCdol(cmbCdol.getSelectedPcod());
					else Patientsmert.setCdolIsSet(false);
					if (cmbOsn.getSelectedItem() != null) Patientsmert.setOsn(cmbOsn.getSelectedPcod());
					else Patientsmert.setOsnIsSet(false);
					Patientsmert.setPsm_a(tfPsm_a.getText().trim());
					Patientsmert.setPsm_an(tfPsm_an.getText().trim());
					if (!tfPsm_ak.getText().isEmpty()) Patientsmert.setPsm_ak(Integer.valueOf(tfPsm_ak.getText()));
					else Patientsmert.setPsm_akIsSet(false);
					if (cmbPsm_ad.getSelectedItem() != null) Patientsmert.setPsm_ad(cmbPsm_ad.getSelectedPcod());
					else Patientsmert.setPsm_adIsSet(false);
					Patientsmert.setPsm_b(tfPsm_b.getText().trim());
					Patientsmert.setPsm_bn(tfPsm_bn.getText().trim());
					if (!tfPsm_bk.getText().isEmpty()) Patientsmert.setPsm_bk(Integer.valueOf(tfPsm_bk.getText()));
					else Patientsmert.setPsm_bkIsSet(false);
					if (cmbPsm_bd.getSelectedItem() != null) Patientsmert.setPsm_bd(cmbPsm_bd.getSelectedPcod());
					else Patientsmert.setPsm_bdIsSet(false);
					Patientsmert.setPsm_v(tfPsm_v.getText().trim());
					Patientsmert.setPsm_vn(tfPsm_vn.getText().trim());
					if (!tfPsm_vk.getText().isEmpty()) Patientsmert.setPsm_vk(Integer.valueOf(tfPsm_vk.getText()));
					else Patientsmert.setPsm_vkIsSet(false);
					if (cmbPsm_vd.getSelectedItem() != null) Patientsmert.setPsm_vd(cmbPsm_vd.getSelectedPcod());
					else Patientsmert.setPsm_vdIsSet(false);
					Patientsmert.setPsm_g(tfPsm_g.getText().trim());
					Patientsmert.setPsm_gn(tfPsm_gn.getText().trim());
					if (!tfPsm_gk.getText().isEmpty()) Patientsmert.setPsm_gk(Integer.valueOf(tfPsm_gk.getText()));
					else Patientsmert.setPsm_gkIsSet(false);
					if (cmbPsm_ag.getSelectedItem() != null) Patientsmert.setPsm_gd(cmbPsm_ag.getSelectedPcod());
					else Patientsmert.setPsm_gdIsSet(false);
					Patientsmert.setPsm_p(tfPsm_p.getText().trim());
					Patientsmert.setPsm_pn(tfPsm_pn.getText().trim());
					if (!tfPsm_pk.getText().isEmpty()) Patientsmert.setPsm_pk(Integer.valueOf(tfPsm_pk.getText()));
					else Patientsmert.setPsm_pkIsSet(false);
					if (cmbPsm_pd.getSelectedItem() != null) Patientsmert.setPsm_pd(cmbPsm_pd.getSelectedPcod());
					else Patientsmert.setPsm_pdIsSet(false);
					Patientsmert.setPsm_p1(tfPsm_p1.getText().trim());
					Patientsmert.setPsm_p1n(tfPsm_p1n.getText().trim());
					if (!tfPsm_p1k.getText().isEmpty()) Patientsmert.setPsm_p1k(Integer.valueOf(tfPsm_p1k.getText()));
					else Patientsmert.setPsm_p1kIsSet(false);
					if (cmbPsm_p1d.getSelectedItem() != null) Patientsmert.setPsm_p1d(cmbPsm_p1d.getSelectedPcod());
					else Patientsmert.setPsm_p1dIsSet(false);
					Patientsmert.setPsm_p2(tfPsm_p2.getText().trim());
					Patientsmert.setPsm_p2n(tfPsm_p2n.getText().trim());
					if (!tfPsm_p2k.getText().isEmpty()) Patientsmert.setPsm_p2k(Integer.valueOf(tfPsm_p2k.getText()));
					else Patientsmert.setPsm_p2kIsSet(false);
					if (cmbPsm_p2d.getSelectedItem() != null) Patientsmert.setPsm_p2d(cmbPsm_p2d.getSelectedPcod());
					else Patientsmert.setPsm_p2dIsSet(false);
					if (chckbxDtp30.isSelected()) Patientsmert.setDtp(1);
					if (chckbxDtp7.isSelected()) Patientsmert.setDtp(2);
					if (tfPol.getText() == "мужской") Patientsmert.setUmerlaIsSet(false);
					else {
					if (cmbUmerla.getSelectedItem() != null) Patientsmert.setUmerla(cmbUmerla.getSelectedPcod());
										else Patientsmert.setUmerlaIsSet(false);}
					Patientsmert.setCuser(cuserId);
					Patientsmert.setClpu(cuserClpu);
					Patientsmert.setFio_r("руководитель");
					Patientsmert.setFio_pol(tfFam_pol.getText().trim());
					if (cmbVdok.getSelectedItem() != null) Patientsmert.setVdok(cmbVdok.getSelectedPcod());
					else Patientsmert.setVdokIsSet(false);
					Patientsmert.setSdok(tfSdok.getText().trim());
					Patientsmert.setNdok(tfNdok.getText().trim());
					if (tfDvdok.getDate() != null) Patientsmert.setDvdok(tfDvdok.getDate().getTime());
					else Patientsmert.setDatavIsSet(false);
					Patientsmert.setKvdok(tfKvdok.getText().trim());
					if (Patientsmert.getDataz() == 0) Patientsmert.setDataz(new Date().getTime());
					//System.out.println("STROKA" );
					MainForm.tcl.setPsmert(Patientsmert);
					try {
						nomerMss.setNomer_t(Integer.valueOf(tfNomer.getText()));
						MainForm.tcl.setPsmertdop(nomerMss);	
					} catch (Exception e3) {
						e3.printStackTrace();
					}
					
				} catch (Exception e1) {
					e1.printStackTrace();						
				}						
			}
		});
		
		
		JButton btnDelete = new JButton("Удалить");
		btnDelete.setToolTipText("Удалить информацию о пациенте");
		btnDelete.setVerticalAlignment(SwingConstants.TOP);
		panel.add(btnDelete);
		btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					MainForm.tcl.delPsmert(patId);
					tfSer.setText(null);
					tfNomer.setText(null);
					tfDatav.setValue(null);
					cmbVid.setSelectedItem(null);
					tfVz_ser.setText(null);
					tfVz_nomer.setText(null);
					tfVz_datav.setValue(null);
					tfDatas.setValue(null);
					tfVrems.setValue(null);
					tfAdm_obl.setText(null);
					tfAdm_raion.setText(null);
					tfAdm_gorod.setText(null);
					tfAdm_ul.setText(null);
					tfAdm_dom.setText(null);
					tfAdm_korp.setText(null);
					tfAdm_kv.setText(null);
					BtnGroup_mjit.clearSelection();
					tfAds_obl.setText(null);
					tfAds_raion.setText(null);
					tfAds_gorod.setText(null);
					tfAds_ul.setText(null);
					tfAds_dom.setText(null);
					tfAds_korp.setText(null);
					tfAds_kv.setText(null);
					BtnGroup_ms.clearSelection();
					cmb_semp.setSelectedItem(null);
					cmb_obraz.setSelectedItem(null);
					cmb_zan.setSelectedItem(null);
					BtnGroup_don.clearSelection();
					tfves.setText(null);
					tfNreb.setText(null);
					tfFam_m.setText(null);
					tfIm_m.setText(null);
					tfOt_m.setText(null);
					tfMrojd.setText(null);
					tfdatarm.setValue(null);
					cmbNastupila.setSelectedItem(null);
					cmbProiz.setSelectedItem(null);
					cmbVid_tr.setSelectedItem(null);
					tfDatatr.setValue(null);
					tfVrem_tr.setValue(null);
					tfObst.setText(null);
					cmbUstan.setSelectedItem(null);
					cmbOsn.setSelectedItem(null);
					tfCvrach.setSelectedItem(null);
					cmbCdol.setSelectedItem(null);
					tfPsm_a.setText(null);
					tfPsm_an.setText(null);
					tfPsm_ak.setText(null);
					cmbPsm_ad.setSelectedItem(null);
					tfPsm_b.setText(null);
					tfPsm_bn.setText(null);
					tfPsm_bk.setText(null);
					cmbPsm_bd.setSelectedItem(null);
					tfPsm_v.setText(null);
					tfPsm_vn.setText(null);
					tfPsm_vk.setText(null);
					cmbPsm_vd.setSelectedItem(null);
					tfPsm_g.setText(null);
					tfPsm_gn.setText(null);
					tfPsm_gk.setText(null);
					cmbPsm_ag.setSelectedItem(null);
					tfPsm_p.setText(null);
					tfPsm_pn.setText(null);
					tfPsm_pk.setText(null);
					cmbPsm_pd.setSelectedItem(null);
					tfPsm_p1.setText(null);
					tfPsm_p1n.setText(null);
					tfPsm_p1k.setText(null);
					cmbPsm_p1d.setSelectedItem(null);
					tfPsm_p2.setText(null);
					tfPsm_p2n.setText(null);
					tfPsm_p2k.setText(null);
					cmbPsm_p2d.setSelectedItem(null);
					chckbxDtp30.setSelected(false);
					chckbxDtp7.setSelected(false);
					cmbUmerla.setSelectedItem(null); 
					tfZapolnil.setText(null); 
					tfFam_pol.setText(null);
					cmbVdok.setSelectedItem(null);
					tfSdok.setText(null);
					tfNdok.setText(null);
					tfKvdok.setText(null);
	
				} catch (TException e) {
      	  e.printStackTrace();
        }
			}
		});
		
		JButton btnPrintMss = new JButton("Печать");
		btnPrintMss.setToolTipText("Печать медицинского свидетельства о смерти ");
		panel.add(btnPrintMss);
		btnPrintMss.addActionListener(new ActionListener () {

			@Override
			public void actionPerformed(ActionEvent e) {
				String mssPath;
				int aPrn = CheckInfo();
				if (aPrn == 0)
					try {
						String strVid = "";
						String strNast = "";
						String strDtp = "";
						String strUm = "";
						String stroka_1 = "";
						String stroka_2 ="";
						String docInfo = "";
						int dtr = 0;
					// корешок МСС
						stroka_1 = tfSer.getText().trim()+"#";
						stroka_1 += tfNomer.getText().trim()+"#";
						stroka_1 += sdfDay.format(tfDatav.getDate())+"#";
						nomMonth = sdfMonth.format(tfDatav.getDate()).trim();
						stroka_1 += monthName()+"#";
						stroka_1 += sdfYear.format(tfDatav.getDate()).trim()+"#";
						if (cmbVid.getSelectedPcod() == 1) 
							strVid = "(окончательного, <u>предварительного</u>, взамен предварительного, взамен окончательного)"; 
						if (cmbVid.getSelectedPcod() == 2) 
							strVid = "(окончательного, предварительного, <u>взамен предварительного</u>, взамен окончательного)"; 
						if (cmbVid.getSelectedPcod() == 3) 
							strVid = "(<u>окончательного</u>, предварительного, взамен предварительного, взамен окончательного)"; 
						if (cmbVid.getSelectedPcod() == 4) 
							strVid = "(окончательного, предварительного, взамен предварительного, <u>взамен окончательного</u>)"; 
						stroka_1 +=strVid+"#";
						stroka_1 += tfVz_ser.getText().trim()+"#";
						stroka_1 += tfVz_nomer.getText().trim()+"#";
						if (tfVz_datav.getDate() != null) {
							stroka_1 += sdfDay.format(tfVz_datav.getDate()).trim()+"#";
							stroka_1 += sdfMonth.format(tfVz_datav.getDate()).trim()+"#";
							stroka_1 += sdfYear.format(tfVz_datav.getDate()).trim()+"#";
						} else {
							stroka_1 += "###";
						}
						stroka_1 += tfFam.getText().trim()+" "+tfIm.getText().trim()+" "+tfOt.getText().trim()+"#";
						if (tfPol.getText().equals("МУЖСКОЙ")) { 
							stroka_1 += "<b><u>мужской</u></b> &nbsp<span> 1 </span> &nbspженский&nbsp<span> 2&nbsp </span>#";
						} else
							stroka_1 += "мужской&nbsp<span> 1 </span>&nbsp <b><u>женский</u></b> &nbsp<span> 2&nbsp </span>#";
						stroka_1 += sdfDay.format(tfDatar.getDate()).trim()+"#";
						stroka_1 += sdfMonth.format(tfDatar.getDate()).trim()+"#";
						stroka_1 += sdfYear.format(tfDatar.getDate()).trim()+"#";
						stroka_1 += sdfDay.format(tfDatas.getDate()).trim()+"#";
						stroka_1 += sdfMonth.format(tfDatas.getDate()).trim()+"#";
						stroka_1 += sdfYear.format(tfDatas.getDate()).trim()+"#";
						stroka_1 += sdfTime.format(tfVrems.getTime()).trim()+"#";
						stroka_1 += tfAdm_obl.getText().trim()+"#"+tfAdm_raion.getText().trim()+"#";
						if (rdbtnMjit_gor.isSelected()) {
							stroka_1 += tfAdm_gorod.getText().trim()+"##";
						} else {
							stroka_1 +="#"+tfAdm_gorod.getText().trim()+"#";
						}
						stroka_1 += tfAdm_ul.getText().trim()+"#"+tfAdm_dom.getText().trim()+"#"+tfAdm_kv.getText().trim()+"#";
//						System.out.println(stroka_1);
						docInfo += stroka_1;
						if (cmbNastupila.getSelectedPcod() == 1) 
						strNast = "на месте происшествия&nbsp<span> 1 </span>, в машине скорой помощи&nbsp<span> 2 </span>, <b><u>в стационаре</u></b>&nbsp<span> 3 </span>, дома&nbsp<span> 4 </span>, в другом месте&nbsp<span> 5&nbsp </span>";
						if (cmbNastupila.getSelectedPcod() == 2) 
						strNast = "на месте происшествия&nbsp<span> 1 </span>, в машине скорой помощи&nbsp<span> 2 </span>, в стационаре&nbsp<span> 3 </span>, <b><u>дома</u></b>&nbsp<span> 4 </span>, в другом месте&nbsp<span> 5&nbsp </span>";
						if (cmbNastupila.getSelectedPcod() == 3) 
						strNast = "на месте происшествия&nbsp<span> 1 </span>, в машине скорой помощи&nbsp<span> 2 </span>, в стационаре&nbsp<span> 3 </span>, дома&nbsp<span> 4 </span>, <b><u>в другом месте</u></b>&nbsp<span> 5&nbsp </span>";
						if (cmbNastupila.getSelectedPcod() == 4) 
						strNast = " <b><u>на месте происшествия</u></b>&nbsp<span> 1 </span>, в машине скорой помощи&nbsp<span> 2 </span>, в стационаре&nbsp<span> 3 </span>, дома&nbsp<span> 4 </span>, в другом месте&nbsp<span> 5&nbsp </span>";
						if (cmbNastupila.getSelectedPcod() == 5) 
						strNast = "на месте происшествия&nbsp<span> 1 </span>, <b><u>в машине скорой помощи</u></b>&nbsp<span> 2 </span>, в стационаре&nbsp<span> 3 </span>, дома&nbsp<span> 4 </span>, в другом месте&nbsp<span> 5&nbsp </span>";
						docInfo += strNast+"#";
						aBool = changeValue();
						if (aBool == true) {
							docInfo += sdfDay.format(tfDatar.getDate()).trim()+"#";
							docInfo += sdfMonth.format(tfDatar.getDate()).trim()+"#";
							docInfo += sdfYear.format(tfDatar.getDate()).trim()+"#";
							docInfo += kolMonth()+"#";
							docInfo += tfMrojd.getText().trim()+"#";
							docInfo += tfFam_m.getText().trim()+" "+tfIm_m.getText().trim()+" "+tfOt_m.getText().trim()+"#";
						} else 
							docInfo += "######";
//						// причины смерти
//						stroka_2 = tfPsm_an.getText().trim()+"#"+tfPsm_ak.getText().trim()+"#"+cmbPsm_ad.getText().trim()+"#";
//						stroka_2 += tfPsm_a.getText().trim().substring(1,1)+"#"+tfPsm_a.getText().trim().substring(2,2)+"#"+tfPsm_a.getText().trim().substring(3,3)+"#";
//						if (tfPsm_a.getText().trim().length() > 4)
//							stroka_2 += tfPsm_a.getText().trim().substring(5,5)+"#";
//							else
//								stroka_2 += "#";
//						stroka_2 += tfPsm_bn.getText().trim()+"#"+tfPsm_bk.getText().trim()+"#"+cmbPsm_bd.getText().trim()+"#";
//						stroka_2 += tfPsm_b.getText().trim().substring(1,1)+"#"+tfPsm_b.getText().trim().substring(2,2)+"#"+tfPsm_b.getText().trim().substring(3,3)+"#";
//						if (tfPsm_b.getText().trim().length() > 4)
//							stroka_2 += tfPsm_b.getText().trim().substring(5,5)+"#";
//							else
//								stroka_2 += "#";
//						stroka_2 += tfPsm_vn.getText().trim()+"#"+tfPsm_vk.getText().trim()+"#"+cmbPsm_vd.getText().trim()+"#";
//						stroka_2 += tfPsm_v.getText().trim().substring(1,1)+"#"+tfPsm_v.getText().trim().substring(2,2)+"#"+tfPsm_v.getText().trim().substring(3,3)+"#";
//						if (tfPsm_v.getText().trim().length() > 4)
//							stroka_2 += tfPsm_v.getText().trim().substring(5,5)+"#";
//							else
//								stroka_2 += "#";
//						stroka_2 += tfPsm_gn.getText().trim()+"#"+tfPsm_gk.getText().trim()+"#"+cmbPsm_ag.getText().trim()+"#";
//						stroka_2 += tfPsm_g.getText().trim().substring(1,1)+"#"+tfPsm_g.getText().trim().substring(2,2)+"#"+tfPsm_g.getText().trim().substring(3,3)+"#";
//						if (tfPsm_g.getText().trim().length() > 4)
//							stroka_2 += tfPsm_g.getText().trim().substring(5,5)+"#";
//							else
//								stroka_2 += "#";
//						stroka_2 += tfPsm_pn.getText().trim()+"#"+tfPsm_pk.getText().trim()+"#"+cmbPsm_pd.getText().trim()+"#";
//						stroka_2 += tfPsm_p.getText().trim().substring(1,1)+"#"+tfPsm_p.getText().trim().substring(2,2)+"#"+tfPsm_p.getText().trim().substring(3,3)+"#";
//						if (tfPsm_p.getText().trim().length() > 4)
//							stroka_2 += tfPsm_p.getText().trim().substring(5,5)+"#";
//							else
//								stroka_2 += "#";
//						docInfo += stroka_2;
//						// конец причин смерти
//						if (!chckbxDtp30.isSelected() && !chckbxDtp7.isSelected()) 
//							strDtp = "в течение 30 суток &nbsp<span> 1 </span> &nbsp, из них в течение 7 суток &nbsp<span> 2&nbsp </span>";							
//						if (chckbxDtp30.isSelected() && !chckbxDtp7.isSelected()) 
//							strDtp = "<b><u>в течение 30 суток</u></b>&nbsp<span> 1 </span> &nbsp, из них в течение 7 суток&nbsp<span> 2&nbsp </span>";							
//						if (chckbxDtp30.isSelected() && chckbxDtp7.isSelected()) 
//							strDtp = "<b><u>в течение 30 суток</u></b>&nbsp<span> 1 </span> &nbsp<b><u>из них в течение 7 суток</u></b>&nbsp<span> 2&nbsp </span>";							
//						docInfo += strDtp + "#";
//						if (cmbUmerla.getSelectedItem() != null) {
//						if (cmbUmerla.getSelectedPcod() == 1) 
//							strUm = " <b><u>беременной, независимо от срока и локализации)</u></b>&nbsp<span> 1 </span>, в процессе родов (аборта)&nbsp<span> 2 </span>, в течение 42 дней после окон-#чания беременности, родов (аборта)&nbsp<span> 3 </span>, в течение 43-365 дней после окончания беременности, родов&nbsp<span> 4 </span>";
//						if (cmbNastupila.getSelectedPcod() == 2) 
//							strUm = " беременной, независимо от срока и локализации)&nbsp<span> 1 </span>, <b><u>в процессе родов (аборта)</u></b>&nbsp<span> 2 </span>, в течение 42 дней после окон-#чания беременности, родов (аборта)&nbsp<span> 3 </span>, в течение 43-365 дней после окончания беременности, родов&nbsp<span> 4 </span>";
//						if (cmbNastupila.getSelectedPcod() == 3) 
//							strUm = " беременной, независимо от срока и локализации)&nbsp<span> 1 </span>, в процессе родов (аборта)&nbsp<span> 2 </span>, <b><u>в течение 42 дней после окон-</u></b>#<b><u>чания беременности, родов (аборта)</u></b>&nbsp<span> 3 </span>, в течение 43-365 дней после окончания беременности, родов&nbsp<span> 4 </span>";
//						if (cmbNastupila.getSelectedPcod() == 4) 
//							strUm = " беременной, независимо от срока и локализации)&nbsp<span> 1 </span>, в процессе родов (аборта)&nbsp<span> 2 </span>, в течение 42 дней после окон-#чания беременности, родов (аборта)&nbsp<span> 3 </span>, <b><u>в течение 43-365 дней после окончания беременности, родов</u></b>&nbsp<span> 4 </span>";
//						} else 
//							strUm = " беременной, независимо от срока и локализации)&nbsp<span> 1 </span>, в процессе родов (аборта)&nbsp<span> 2 </span>, в течение 42 дней после окон-#чания беременности, родов (аборта)&nbsp<span> 3 </span>, в течение 43-365 дней после окончания беременности, родов&nbsp<span> 4 </span>";
//						docInfo += strUm+"#";
//						
//						docInfo += tfZapolnil.getText().trim()+"#"+tfFam_pol.getText().trim()+"#";
//						docInfo += cmbVdok.getText().trim()+" "+tfSdok.getText().trim()+" "+tfNomer.getText().trim()+" "+tfKvdok.getText().trim()+"#";
//						docInfo += sdfDay.format(new Date()).trim()+"#";
//						nomMonth = sdfMonth.format(new Date()).trim()+"#";
//						docInfo += monthName()+"#";
//						docInfo += sdfYear.format(new Date()).trim()+"#";
//						
//						// медицинское свидетельство
//								docInfo += stroka_1;
//								if (rdbtnMjit_gor.isSelected()) {
//									docInfo += "<b><u> городская</u></b>&nbsp<span> 1 </span> &nbspсельская&nbsp<span> 2&nbsp </span>#";
//								} else 
//									docInfo += "<b><u> городская</u></b>&nbsp<span> 1 </span> &nbs<b><u>pсельская</u></b>&nbsp<span> 2&nbsp </span>#";
//								docInfo += tfAds_obl.getText().trim()+"#"+tfAds_raion.getText().trim()+"#";
//								if (rdbtnMs_gor.isSelected()) {
//									docInfo += tfAds_gorod.getText().trim()+"##";
//								} else 
//									docInfo +="#"+tfAds_gorod.getText().trim()+"#";
//								docInfo += tfAdm_ul.getText().trim()+"#"+tfAdm_kv.getText().trim()+"#";
//								if (rdbtnMs_gor.isSelected()) {
//									docInfo += "<b><u> городская</u></b>&nbsp<span> 1 </span> &nbspсельская&nbsp<span> 2&nbsp </span>#";
//								} else 
//									docInfo += "<b><u> городская</u></b>&nbsp<span> 1 </span> &nbs<b><u>pсельская</u></b>&nbsp<span> 2&nbsp </span>#";
//								docInfo += strNast+"#";
//								strVid = "&nbsp доношенный (37-41 недель)&nbsp<span> 1 </span>, недоношенный (менее 37 недель)&nbsp<span> 2 </span>,# переношенный (42 недель и более)&nbsp<span> 3 </span>";
//								if (aBool == true) {
//									if (rdbtn_don_don.isSelected())
//										strVid = "&nbsp <b><u>доношенный (37-41 недель)</u></b>&nbsp<span> 1 </span>, недоношенный (менее 37 недель)&nbsp<span> 2 </span>,# переношенный (42 недель и более)&nbsp<span> 3 </span>";
//									if (rdbtn_don_ned.isSelected())
//										strVid = "&nbsp доношенный (37-41 недель)&nbsp<span> 1 </span>, <b><u>недоношенный (менее 37 недель)</u></b>&nbsp<span> 2 </span>,# переношенный (42 недель и более)&nbsp<span> 3 </span>";
//									if (rdbtn_don_peren.isSelected())
//										strVid = "&nbsp доношенный (37-41 недель)&nbsp<span> 1 </span>, недоношенный (менее 37 недель)&nbsp<span> 2 </span>,# <b><u>переношенный (42 недель и более)</u></b>&nbsp<span> 3 </span>";
//									docInfo += strVid+"#";
//									docInfo += tfves.getText().trim()+"#";
//									docInfo += tfNreb.getText().trim()+"#";
//									docInfo += sdfData.format(tfdatarm.getDate()).trim()+"#";
//									docInfo += String.valueOf(Vozrast())+"#";
//									docInfo += tfFam_m.getText().trim()+"#"+tfIm_m.getText().trim()+"#"+tfOt_m.getText().trim()+"#";
//								} else 
//									docInfo += strVid+"######";	
//								if (cmb_semp.getSelectedPcod() == 1)
//									strVid = "<b><u>состоял(а) в зарегистрированном браке</u></b>,&nbsp<span> 1 </span>, не состоял(а) в зарегистрированном браке&nbsp<span> 2 </span>, неизвестно &nbsp<span> 3 </span>";
//								if ((cmb_semp.getSelectedPcod() >= 2) && (cmb_semp.getSelectedPcod() < 5))
//									strVid = "состоял(а) в зарегистрированном браке,&nbsp<span> 1 </span>, <b><u>не состоял(а) в зарегистрированном браке</u></b>&nbsp<span> 2 </span>, неизвестно &nbsp<span> 3 </span>";
//								if (cmb_semp.getSelectedPcod() == 5)
//									strVid = "состоял(а) в зарегистрированном браке,&nbsp<span> 1 </span>, не состоял(а) в зарегистрированном браке&nbsp<span> 2 </span>, <b><u>неизвестно</u></b> &nbsp<span> 3 </span>";
//								docInfo += strVid+"#";
//								if (cmb_obraz.getSelectedPcod() == 1)
//									strVid = "<b><u>высшее</u></b>&nbsp<span> 1 </span>, неполное высшее,&nbsp<span> 2 </span>, среднее&nbsp<span> 3 </span>, начальное&nbsp<span> 4 </span>#"
//											+ "среднее (полное)&nbsp<span> 5 </span>,# основное&nbsp<span> 6 </span>, начальное&nbsp<span> 7 </span>; не имеет начального образования&nbsp<span> 8 </span>; неизвестно&nbsp<span> 9 </span>.";
//								if (cmb_obraz.getSelectedPcod() == 2)
//									strVid = "высшее&nbsp<span> 1 </span>, <b><u>неполное высшее</u></b>,&nbsp<span> 2 </span>, среднее&nbsp<span> 3 </span>, начальное&nbsp<span> 4 </span>#"
//											+ "среднее (полное)&nbsp<span> 5 </span>,# основное&nbsp<span> 6 </span>, начальное&nbsp<span> 7 </span>; не имеет начального образования&nbsp<span> 8 </span>; неизвестно&nbsp<span> 9 </span>.";
//								if (cmb_obraz.getSelectedPcod() == 3)
//									strVid = "высшее&nbsp<span> 1 </span>, неполное высшее,&nbsp<span> 2 </span>, <b><u>среднее</u></b>&nbsp<span> 3 </span>, начальное&nbsp<span> 4 </span>#"
//											+ "среднее (полное)&nbsp<span> 5 </span>,# основное&nbsp<span> 6 </span>, начальное&nbsp<span> 7 </span>; не имеет начального образования&nbsp<span> 8 </span>; неизвестно&nbsp<span> 9 </span>.";
//								if (cmb_obraz.getSelectedPcod() == 4)
//									strVid = "высшее&nbsp<span> 1 </span>, неполное высшее,&nbsp<span> 2 </span>, среднее&nbsp<span> 3 </span>, <b><u>начальное</u></b>&nbsp<span> 4 </span>#"
//											+ "среднее (полное)&nbsp<span> 5 </span>,# основное&nbsp<span> 6 </span>, начальное&nbsp<span> 7 </span>; не имеет начального образования&nbsp<span> 8 </span>; неизвестно&nbsp<span> 9 </span>.";
//								if (cmb_obraz.getSelectedPcod() == 5)
//									strVid = "высшее&nbsp<span> 1 </span>, неполное высшее,&nbsp<span> 2 </span>, среднее&nbsp<span> 3 </span>, начальное&nbsp<span> 4 </span>#"
//											+ "<b><u>среднее (полное)</u></b>&nbsp<span> 5 </span>,# основное&nbsp<span> 6 </span>, начальное&nbsp<span> 7 </span>; не имеет начального образования&nbsp<span> 8 </span>; неизвестно&nbsp<span> 9 </span>.";
//								if (cmb_obraz.getSelectedPcod() == 6)
//									strVid = "высшее&nbsp<span> 1 </span>, неполное высшее,&nbsp<span> 2 </span>, среднее&nbsp<span> 3 </span>, начальное&nbsp<span> 4 </span>#"
//											+ "среднее (полное)&nbsp<span> 5 </span>,# <b><u>основное</u></b>&nbsp<span> 6 </span>, начальное&nbsp<span> 7 </span>; не имеет начального образования&nbsp<span> 8 </span>; неизвестно&nbsp<span> 9 </span>.";
//								if (cmb_obraz.getSelectedPcod() == 7)
//									strVid = "высшее&nbsp<span> 1 </span>, неполное высшее,&nbsp<span> 2 </span>, среднее&nbsp<span> 3 </span>, начальное&nbsp<span> 4 </span>#"
//											+ "среднее (полное)&nbsp<span> 5 </span>,# основное&nbsp<span> 6 </span>, <b><u>начальное</u></b>&nbsp<span> 7 </span>; не имеет начального образования&nbsp<span> 8 </span>; неизвестно&nbsp<span> 9 </span>.";
//								if (cmb_obraz.getSelectedPcod() == 8)
//									strVid = "высшее&nbsp<span> 1 </span>, неполное высшее,&nbsp<span> 2 </span>, среднее&nbsp<span> 3 </span>, начальное&nbsp<span> 4 </span>#"
//											+ "среднее (полное)&nbsp<span> 5 </span>,# основное&nbsp<span> 6 </span>, начальное&nbsp<span> 7 </span>; <b><u>не имеет начального образования</u></b>&nbsp<span> 8 </span>; неизвестно&nbsp<span> 9 </span>.";
//								if (cmb_obraz.getSelectedPcod() == 9)
//									strVid = "высшее&nbsp<span> 1 </span>, неполное высшее,&nbsp<span> 2 </span>, среднее&nbsp<span> 3 </span>, начальное&nbsp<span> 4 </span>#"
//											+ "среднее (полное)&nbsp<span> 5 </span>,# основное&nbsp<span> 6 </span>, начальное&nbsp<span> 7 </span>; не имеет начального образования&nbsp<span> 8 </span>; <b><u>неизвестно</u></b>&nbsp<span> 9 </span>.";
//								docInfo += strVid+"#";
//								if (cmb_zan.getSelectedPcod() == 1)
//									strVid = "<b><u>руководители и специалисты высшего уровня квалификации</u></b>&nbsp<span> 1 </span>, прочие#"
//											+"специалисты,&nbsp<span> 2 </span>, квалифицированные рабочие&nbsp<span> 3 </span>, неквалифицированные рабочие&nbsp<span> 4 </span>, занятые на военной службе&nbsp<span> 5 </span>;#"
//											+"пенсионеры&nbsp<span> 6 </span>, студенты и учащиеся&nbsp<span> 7 </span>, работавшие в личном подсобном хозяйстве&nbsp<span> 8 </span>,#"  
//											+"безработные&nbsp<span> 9 </span>, прочие&nbsp<span> 10 </span>.";
//								if (cmb_zan.getSelectedPcod() == 2)
//									strVid = "руководители и специалисты высшего уровня квалификации&nbsp<span> 1 </span>, <b><u>прочие</u></b>#"
//											+"<b><u>специалисты</u></b>,&nbsp<span> 2 </span>, квалифицированные рабочие&nbsp<span> 3 </span>, неквалифицированные рабочие&nbsp<span> 4 </span>, занятые на военной службе&nbsp<span> 5 </span>;#"
//											+"пенсионеры&nbsp<span> 6 </span>, студенты и учащиеся&nbsp<span> 7 </span>, работавшие в личном подсобном хозяйстве&nbsp<span> 8 </span>,#"  
//											+"безработные&nbsp<span> 9 </span>, прочие&nbsp<span> 10 </span>.";
//								if (cmb_zan.getSelectedPcod() == 3)
//									strVid = "руководители и специалисты высшего уровня квалификации&nbsp<span> 1 </span>, прочие#"
//											+"специалисты,&nbsp<span> 2 </span>, <b><u>квалифицированные рабочие</u></b>&nbsp<span> 3 </span>, неквалифицированные рабочие&nbsp<span> 4 </span>, занятые на военной службе&nbsp<span> 5 </span>;#"
//											+"пенсионеры&nbsp<span> 6 </span>, студенты и учащиеся&nbsp<span> 7 </span>, работавшие в личном подсобном хозяйстве&nbsp<span> 8 </span>,#"  
//											+"безработные&nbsp<span> 9 </span>, прочие&nbsp<span> 10 </span>.";
//								if (cmb_zan.getSelectedPcod() == 4)
//									strVid = "руководители и специалисты высшего уровня квалификации&nbsp<span> 1 </span>, прочие#"
//											+"специалисты,&nbsp<span> 2 </span>, квалифицированные рабочие&nbsp<span> 3 </span>, <b><u>неквалифицированные рабочие</u></b>&nbsp<span> 4 </span>, занятые на военной службе&nbsp<span> 5 </span>;#"
//											+"пенсионеры&nbsp<span> 6 </span>, студенты и учащиеся&nbsp<span> 7 </span>, работавшие в личном подсобном хозяйстве&nbsp<span> 8 </span>,#"  
//											+"безработные&nbsp<span> 9 </span>, прочие&nbsp<span> 10 </span>.";
//								if (cmb_zan.getSelectedPcod() == 5)
//									strVid = "руководители и специалисты высшего уровня квалификации&nbsp<span> 1 </span>, прочие#"
//											+"специалисты,&nbsp<span> 2 </span>, квалифицированные рабочие&nbsp<span> 3 </span>, неквалифицированные рабочие&nbsp<span> 4 </span>, <b><u>занятые на военной службе</u></b>&nbsp<span> 5 </span>;#"
//											+"пенсионеры&nbsp<span> 6 </span>, студенты и учащиеся&nbsp<span> 7 </span>, работавшие в личном подсобном хозяйстве&nbsp<span> 8 </span>,#"  
//											+"безработные&nbsp<span> 9 </span>, прочие&nbsp<span> 10 </span>.";
//								if (cmb_zan.getSelectedPcod() == 6)
//									strVid = "руководители и специалисты высшего уровня квалификации&nbsp<span> 1 </span>, прочие#"
//											+"специалисты,&nbsp<span> 2 </span>, квалифицированные рабочие&nbsp<span> 3 </span>, неквалифицированные рабочие&nbsp<span> 4 </span>, занятые на военной службе&nbsp<span> 5 </span>;#"
//											+"<b><u>пенсионеры</u></b>&nbsp<span> 6 </span>, студенты и учащиеся&nbsp<span> 7 </span>, работавшие в личном подсобном хозяйстве&nbsp<span> 8 </span>,#"  
//											+"безработные&nbsp<span> 9 </span>, прочие&nbsp<span> 10 </span>.";
//								if (cmb_zan.getSelectedPcod() == 7)
//									strVid = "руководители и специалисты высшего уровня квалификации&nbsp<span> 1 </span>, прочие#"
//											+"специалисты,&nbsp<span> 2 </span>, квалифицированные рабочие&nbsp<span> 3 </span>, неквалифицированные рабочие&nbsp<span> 4 </span>, занятые на военной службе&nbsp<span> 5 </span>;#"
//											+"пенсионеры&nbsp<span> 6 </span>, <b><u>студенты и учащиеся</u></b>&nbsp<span> 7 </span>, работавшие в личном подсобном хозяйстве&nbsp<span> 8 </span>,#"  
//											+"безработные&nbsp<span> 9 </span>, прочие&nbsp<span> 10 </span>.";
//								if (cmb_zan.getSelectedPcod() == 8)
//									strVid = "руководители и специалисты высшего уровня квалификации&nbsp<span> 1 </span>, прочие#"
//											+"специалисты,&nbsp<span> 2 </span>, квалифицированные рабочие&nbsp<span> 3 </span>, неквалифицированные рабочие&nbsp<span> 4 </span>, занятые на военной службе&nbsp<span> 5 </span>;#"
//											+"пенсионеры&nbsp<span> 6 </span>, студенты и учащиеся&nbsp<span> 7 </span>, <b><u>работавшие в личном подсобном хозяйстве</u></b>&nbsp<span> 8 </span>,#"  
//											+"безработные&nbsp<span> 9 </span>, прочие&nbsp<span> 10 </span>.";
//								if (cmb_zan.getSelectedPcod() == 9)
//									strVid = "руководители и специалисты высшего уровня квалификации&nbsp<span> 1 </span>, прочие#"
//											+"специалисты,&nbsp<span> 2 </span>, квалифицированные рабочие&nbsp<span> 3 </span>, неквалифицированные рабочие&nbsp<span> 4 </span>, занятые на военной службе&nbsp<span> 5 </span>;#"
//											+"пенсионеры&nbsp<span> 6 </span>, студенты и учащиеся&nbsp<span> 7 </span>, работавшие в личном подсобном хозяйстве&nbsp<span> 8 </span>,#"  
//											+"<b><u>безработные</u></b>&nbsp<span> 9 </span>, прочие&nbsp<span> 10 </span>.";
//								if (cmb_zan.getSelectedPcod() == 10)
//									strVid = "руководители и специалисты высшего уровня квалификации&nbsp<span> 1 </span>, прочие#"
//											+"специалисты,&nbsp<span> 2 </span>, квалифицированные рабочие&nbsp<span> 3 </span>, неквалифицированные рабочие&nbsp<span> 4 </span>, занятые на военной службе&nbsp<span> 5 </span>;#"
//											+"пенсионеры&nbsp<span> 6 </span>, студенты и учащиеся&nbsp<span> 7 </span>, работавшие в личном подсобном хозяйстве&nbsp<span> 8 </span>,#"  
//											+"безработные&nbsp<span> 9 </span>, <b><u>прочие</u></b>&nbsp<span> 10 </span>.";
//								docInfo += strVid +"#";
//								if (cmbProiz.getSelectedPcod() == 1)
//									strVid = "<b><u>от заболевания</u></b>&nbsp<span> 1 </span>;# не связанного с производством&nbsp<span> 2 </span>, связанного с производством&nbsp<span> 3 </span>;#"
//											+" убийства&nbsp<span> 4 </span>; самоубийства&nbsp<span> 5 </span>;# военных&nbsp<span> 6 </span>, террористических&nbsp<span> 7 </span>; род смерти не установлен&nbsp<span> 8 </span>.";
//								if (cmbProiz.getSelectedPcod() == 2) {
//									strVid = "от заболевания&nbsp<span> 1 </span>;# <b><u>не связанного с производством</u></b>&nbsp<span> 2 </span>, связанного с производством&nbsp<span> 3 </span>;#"
//											+" убийства&nbsp<span> 4 </span>; самоубийства&nbsp<span> 5 </span>;# военных&nbsp<span> 6 </span>, террористических&nbsp<span> 7 </span>; род смерти не установлен&nbsp<span> 8 </span>.";
//									dtr = 1;
//								}
//								if (cmbProiz.getSelectedPcod() == 3) {
//									strVid = "от заболевания&nbsp<span> 1 </span>;# не связанного с производством&nbsp<span> 2 </span>, <b><u>связанного с производством</u></b>&nbsp<span> 3 </span>;#"
//											+" убийства&nbsp<span> 4 </span>; самоубийства&nbsp<span> 5 </span>;# военных&nbsp<span> 6 </span>, террористических&nbsp<span> 7 </span>; род смерти не установлен&nbsp<span> 8 </span>.";
//									dtr = 1;
//								}
//								if (cmbProiz.getSelectedPcod() == 4) {
//									strVid = "от заболевания&nbsp<span> 1 </span>;# не связанного с производством&nbsp<span> 2 </span>, связанного с производством&nbsp<span> 3 </span>;#"
//											+" <b><u>убийства</u></b>&nbsp<span> 4 </span>; самоубийства&nbsp<span> 5 </span>;# военных&nbsp<span> 6 </span>, террористических&nbsp<span> 7 </span>; род смерти не установлен&nbsp<span> 8 </span>.";
//								dtr = 1;
//							}
//								if (cmbProiz.getSelectedPcod() == 5) {
//									strVid = "от заболевания&nbsp<span> 1 </span>;# не связанного с производством&nbsp<span> 2 </span>, связанного с производством&nbsp<span> 3 </span>;#"
//											+" убийства&nbsp<span> 4 </span>; <b><u>самоубийства</u></b>&nbsp<span> 5 </span>;# военных&nbsp<span> 6 </span>, террористических&nbsp<span> 7 </span>; род смерти не установлен&nbsp<span> 8 </span>.";
//									dtr = 1;
//								}
//								if (cmbProiz.getSelectedPcod() == 6) {
//									strVid = "от заболевания&nbsp<span> 1 </span>;# не связанного с производством&nbsp<span> 2 </span>, связанного с производством&nbsp<span> 3 </span>;#"
//											+" убийства&nbsp<span> 4 </span>; самоубийства&nbsp<span> 5 </span>;# военных&nbsp<span> 6 </span>, террористических&nbsp<span> 7 </span>; <b><u>род смерти не установлен</u></b>&nbsp<span> 8 </span>.";
//									dtr = 1;
//								}
//								if (cmbProiz.getSelectedPcod() == 7) {
//									strVid = "от заболевания&nbsp<span> 1 </span>;# не связанного с производством&nbsp<span> 2 </span>, связанного с производством&nbsp<span> 3 </span>;#"
//											+" убийства&nbsp<span> 4 </span>; самоубийства&nbsp<span> 5 </span>;# <b><u>военных</u></b>&nbsp<span> 6 </span>, террористических&nbsp<span> 7 </span>; род смерти не установлен&nbsp<span> 8 </span>.";
//									dtr = 1;
//								}
//								if (cmbProiz.getSelectedPcod() == 8) {
//									strVid = "от заболевания&nbsp<span> 1 </span>;# не связанного с производством&nbsp<span> 2 </span>, связанного с производством&nbsp<span> 3 </span>;#"
//											+" убийства&nbsp<span> 4 </span>; самоубийства&nbsp<span> 5 </span>;# военных&nbsp<span> 6 </span>, <b><u>террористических</u></b>&nbsp<span> 7 </span>; род смерти не установлен&nbsp<span> 8 </span>.";
//									dtr = 1;
//								}
//								docInfo += strVid + "#";
//								if (dtr == 1) {
//									docInfo += sdfDay.format(tfDatatr.getDate()).trim()+"#";
//									docInfo += sdfMonth.format(tfDatatr.getDate()).trim()+"#";
//									docInfo += sdfYear.format(tfDatatr.getDate()).trim()+"#";
//									docInfo += sdfTime.format(tfDatatr.getDate()).trim()+"#";
//									docInfo += tfObst.getText().trim()+"#";
//								} else
//									docInfo += "#####";
//								if (cmbUstan.getSelectedPcod() == 1) 
//									strVid = " <b><u>врачом, только установившим смерть</u></b>&nbsp<span> 1 </span>, лечащим врачом&nbsp<span> 2 </span>, фельдшером (акушеркой)&nbsp<span> 3 </span>,#"
//									+ " патологоанатомом&nbsp<span> 4 </span>, судебно-медицинским экспертом&nbsp<span> 5 </span>.";
//								if (cmbUstan.getSelectedPcod() == 2) 
//									strVid = " врачом, только установившим смерть&nbsp<span> 1 </span>, <b><u>лечащим врачом</u></b>&nbsp<span> 2 </span>, фельдшером (акушеркой)&nbsp<span> 3 </span>,#"
//									+ " патологоанатомом&nbsp<span> 4 </span>, судебно-медицинским экспертом&nbsp<span> 5 </span>.";
//								if (cmbUstan.getSelectedPcod() == 3) 
//									strVid = " врачом, только установившим смерть&nbsp<span> 1 </span>, лечащим врачом&nbsp<span> 2 </span>, <b><u>фельдшером (акушеркой)</u></b>&nbsp<span> 3 </span>,#"
//									+ " патологоанатомом&nbsp<span> 4 </span>, судебно-медицинским экспертом&nbsp<span> 5 </span>.";
//								if (cmbUstan.getSelectedPcod() == 4) 
//									strVid = " врачом, только установившим смерть&nbsp<span> 1 </span>, лечащим врачом&nbsp<span> 2 </span>, фельдшером (акушеркой)&nbsp<span> 3 </span>,#"
//									+ " <b><u>патологоанатомом</u></b>&nbsp<span> 4 </span>, судебно-медицинским экспертом&nbsp<span> 5 </span>.";
//								if (cmbUstan.getSelectedPcod() == 5) 
//									strVid = " врачом, только установившим смерть&nbsp<span> 1 </span>, лечащим врачом&nbsp<span> 2 </span>, фельдшером (акушеркой)&nbsp<span> 3 </span>,#"
//									+ " патологоанатомом&nbsp<span> 4 </span>, <b><u>судебно-медицинским экспертом</u></b>&nbsp<span> 5 </span>.";
//								docInfo += strVid + "#";
//								docInfo += tfCvrach.getText().trim()+"#";
//								docInfo += cmbCdol.getText().trim() +"#";
//								if (cmbOsn.getSelectedPcod() == 1)
//									strVid = " <b><u>осмотра трупа<b><u>&nbsp<span> 1 </span>, записей в медицинской документации&nbsp<span> 2 </span>, предшествующего наблюдения# за больным(ой)&nbsp<span> 3 </span>,"
//									+ " вскрытия&nbsp<span> 4 </span> ";
//								if (cmbOsn.getSelectedPcod() == 1)
//									strVid = " осмотра трупа&nbsp<span> 1 </span>, <b><u>записей в медицинской документации</u></b>&nbsp<span> 2 </span>, предшествующего наблюдения# за больным(ой)&nbsp<span> 3 </span>,"
//									+ " вскрытия&nbsp<span> 4 </span> ";
//								if (cmbOsn.getSelectedPcod() == 1)
//									strVid = " осмотра трупа&nbsp<span> 1 </span>, записей в медицинской документации&nbsp<span> 2 </span>, <b><u>предшествующего наблюдения</u></b># <b><u>за больным(ой)</u></b>&nbsp<span> 3 </span>,"
//									+ " вскрытия&nbsp<span> 4 </span> ";
//								if (cmbOsn.getSelectedPcod() == 1)
//									strVid = " осмотра трупа&nbsp<span> 1 </span>, записей в медицинской документации&nbsp<span> 2 </span>, предшествующего наблюдения# за больным(ой)&nbsp<span> 3 </span>,"
//									+ " <b><u>вскрытия&nbsp</u></b><span> 4 </span> ";
//								docInfo += strVid + "#";
//								// причины смерти
//								docInfo += stroka_2;
//								docInfo += strDtp+"#";
//								docInfo += strUm+"#";
//								docInfo += tfZapolnil.getText().trim()+"#"+tfFam_pol.getText().trim()+"#";
//								docInfo += cmbVdok.getText().trim()+" "+tfSdok.getText().trim()+" "+tfNomer.getText().trim()+" "+tfKvdok.getText().trim()+"#";
//								docInfo += sdfDay.format(new Date()).trim()+"#";
//								nomMonth = sdfMonth.format(new Date()).trim()+"#";
//								docInfo += monthName()+"#";
//								docInfo += sdfYear.format(new Date()).trim()+"#";
						mssPath = MainForm.tcl.printMedSS(docInfo);
						System.out.println(docInfo);
	                    String cliPath = File.createTempFile("muzdrav", ".htm").getAbsolutePath();
						System.out.println(cliPath);
	                    MainForm.conMan.transferFileFromServer(mssPath, cliPath);
	                    MainForm.conMan.openFileInEditor(cliPath, false);

						} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
			
		});
		
		JButton btnNomer = new JButton("Номера");
		btnNomer.setToolTipText("Дополнительная информация, необходимая для печати мед. свидетельства о смерти");
		btnNomer.setVerticalAlignment(SwingConstants.TOP);
		panel.add(btnNomer);
		btnNomer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
			dopInfo = new DopInfoForm();
			dopInfo.setVisible(true);
			}
			
		});
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		cmbVid = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_r0l);
		cmb_semp = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_z10);
		cmb_obraz = new ThriftIntegerClassifierCombobox<>(true);
		cmb_zan = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_z42);
		
		JPanel panel_1 = new JPanel();
		panel_1.setToolTipText("");
		tabbedPane.addTab("Сведения о умершем", null, panel_1, null);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		

			JLabel label = new JLabel("Номер");
			
			JLabel label_1 = new JLabel("Дата выдачи");
			
			JLabel label_2 = new JLabel("Серия");
			
			tfDatav = new CustomDateEditor();
			tfDatav.setColumns(10);
			
			tfNomer = new CustomTextField();
			tfNomer.setColumns(10);
			
			tfSer = new CustomTextField();
			tfSer.setColumns(10);
			
			JLabel label_4 = new JLabel("дата выдачи");
			label_4.setFont(new Font("Tahoma", Font.ITALIC, 11));
			
			JLabel label_5 = new JLabel("номер\r\n");
			label_5.setFont(new Font("Tahoma", Font.ITALIC, 11));
			
			tfVz_datav = new CustomDateEditor();
			tfVz_datav.setColumns(10);
			
			tfVz_nomer = new CustomTextField();
			tfVz_nomer.setColumns(10);
			
			tfVz_ser = new CustomTextField();
			tfVz_ser.setColumns(10);
			
			JLabel label_9 = new JLabel("Взамен выданного: серия");
			label_9.setFont(new Font("Tahoma", Font.ITALIC, 11));
			
			JLabel label_10 = new JLabel("Вид свидетельства");
			GroupLayout gl_panel_4 = new GroupLayout(panel_4);
			gl_panel_4.setHorizontalGroup(
				gl_panel_4.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel_4.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel_4.createSequentialGroup()
								.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel_4.createSequentialGroup()
										.addComponent(label_9)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(tfVz_ser, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
										.addGap(14)
										.addComponent(label_5)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(tfVz_nomer, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(label_4))
									.addGroup(gl_panel_4.createSequentialGroup()
										.addComponent(label_10)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(cmbVid, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE)))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tfVz_datav, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panel_4.createSequentialGroup()
								.addComponent(label_2)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tfSer, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(label)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tfNomer, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
								.addGap(15)
								.addComponent(label_1)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tfDatav, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)))
						.addGap(344))
			);
			gl_panel_4.setVerticalGroup(
				gl_panel_4.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel_4.createSequentialGroup()
						.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
							.addComponent(label_2)
							.addComponent(tfSer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label)
							.addComponent(tfNomer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_1)
							.addComponent(tfDatav, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(6)
						.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
							.addComponent(label_10)
							.addComponent(cmbVid, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(5)
						.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
							.addComponent(tfVz_ser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_9)
							.addComponent(tfVz_nomer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_5)
							.addComponent(label_4)
							.addComponent(tfVz_datav, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
			);
			panel_4.setLayout(gl_panel_4);
			GroupLayout gl_panel_1 = new GroupLayout(panel_1);
			gl_panel_1.setHorizontalGroup(
				gl_panel_1.createParallelGroup(Alignment.TRAILING)
					.addComponent(panel_4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 907, Short.MAX_VALUE)
					.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 907, Short.MAX_VALUE)
					.addGroup(gl_panel_1.createSequentialGroup()
						.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 897, Short.MAX_VALUE)
						.addContainerGap())
			);
			gl_panel_1.setVerticalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel_1.createSequentialGroup()
						.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 311, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(47, Short.MAX_VALUE))
			);
			
			JLabel lblNewLabel_24 = new JLabel("Заполняется для детей, умерших в возрасте от 168 час. до 1 года");
			
			rdbtn_don_don = new JRadioButton("доношенный (37-41 недель)");
			BtnGroup_don.add(rdbtn_don_don);
			
			rdbtn_don_ned = new JRadioButton("недоношенный (менее 37 недель)");
			BtnGroup_don.add(rdbtn_don_ned);
			
			rdbtn_don_peren = new JRadioButton("переношенный (42 недель и более)");
			BtnGroup_don.add(rdbtn_don_peren);
			
			JLabel lblNewLabel_26 = new JLabel("вес при рождении (грамм)\r\n");
			
			tfves = new CustomTextField();
			tfves.setColumns(10);
			
			JLabel lblNewLabel_27 = new JLabel("который ребенок (считая умерших и не считая мертворожденных)\r\n");
			
			JLabel lblNewLabel_28 = new JLabel("отчество");
			
			tfOt_m = new CustomTextField();
			tfOt_m.setColumns(10);
			
			JLabel lblNewLabel_29 = new JLabel("Мать: фамилия");
			
			tfFam_m = new CustomTextField();
			tfFam_m.setColumns(10);
			
			JLabel lblNewLabel_30 = new JLabel("имя");
			
			tfIm_m = new CustomTextField();
			tfIm_m.setColumns(10);
			
			JLabel lblNewLabel_31 = new JLabel("дата рождения");
			
			tfdatarm = new CustomDateEditor();
			tfdatarm.setColumns(10);
			
			tfNreb = new CustomTextField();
			tfNreb.setColumns(10);
			
			JLabel lblNewLabel_10 = new JLabel("Место рождения ребенка");
			
			tfMrojd = new CustomTextField();
			tfMrojd.setColumns(10);
			GroupLayout gl_panel_6 = new GroupLayout(panel_6);
			gl_panel_6.setHorizontalGroup(
				gl_panel_6.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel_6.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_panel_6.createSequentialGroup()
								.addComponent(lblNewLabel_26)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tfves, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
								.addGap(16)
								.addComponent(lblNewLabel_27)
								.addGap(32)
								.addComponent(tfNreb, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panel_6.createSequentialGroup()
								.addComponent(lblNewLabel_29)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tfFam_m, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(lblNewLabel_30)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tfIm_m, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(lblNewLabel_28)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tfOt_m, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_panel_6.createSequentialGroup()
								.addComponent(lblNewLabel_31)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tfdatarm, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(lblNewLabel_10)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tfMrojd))
							.addGroup(gl_panel_6.createSequentialGroup()
								.addComponent(rdbtn_don_don)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(rdbtn_don_ned)
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addComponent(rdbtn_don_peren))
							.addComponent(lblNewLabel_24))
						.addContainerGap(95, Short.MAX_VALUE))
			);
			gl_panel_6.setVerticalGroup(
				gl_panel_6.createParallelGroup(Alignment.LEADING)
					.addGroup(gl_panel_6.createSequentialGroup()
						.addComponent(lblNewLabel_24)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
							.addComponent(rdbtn_don_don)
							.addComponent(rdbtn_don_ned)
							.addComponent(rdbtn_don_peren))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel_26)
							.addComponent(tfves, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_27)
							.addComponent(tfNreb, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel_6.createParallelGroup(Alignment.TRAILING)
							.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
								.addComponent(tfIm_m, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_28)
								.addComponent(tfOt_m, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_30))
							.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
								.addComponent(tfFam_m, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_29)))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel_31)
							.addComponent(tfdatarm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_10)
							.addComponent(tfMrojd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(6))
			);
			panel_6.setLayout(gl_panel_6);
			
			JLabel lblNewLabel_13 = new JLabel("республика, край, область");
			
			tfAds_obl = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_l02);
			tfAds_obl.setStrictCheck(false);
			//tfAds_obl.setColumns(10);
//			tfAds_obl.addActionListener(new ActionListener() {

//				@Override
//				public void actionPerformed(ActionEvent arg0) {
//					if (tfAds_obl.getSelectedItem() != null) {
//						try {
//							tfAds_gorod.setData(MainForm.tcl.getL00(tfAds_obl.getSelectedPcod()));// TODO Auto-generated method stub
//						} catch (TException e) {
//							MainForm.conMan.reconnect(e);
//						}
//					}
					
//				}
				
//			});
				
			
			
			JLabel lblNewLabel_14 = new JLabel("район");
			
			tfAds_raion = new CustomTextField();
			tfAds_raion.setColumns(10);
			
			JLabel lblNewLabel_15 = new JLabel("город (населенный пункт)");
			
			tfAds_gorod = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_l00);
			tfAds_gorod.setStrictCheck(false);
			tfAds_gorod.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {

            	if (!tfAds_gorod.getText().trim().equals("НОВОКУЗНЕЦК"))
            	tfAds_ul.setData(new ArrayList<IntegerClassifier>());
                else
                    tfAds_ul.setData(null);
            }
        });
			//tfAds_gorod.setColumns(10);
			
			JLabel lblNewLabel_16 = new JLabel("улица");
			
			tfAds_ul = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_u00);
			tfAds_ul.setStrictCheck(false);

			
			JLabel lblNewLabel_17 = new JLabel("дом, корпус, квартира");
			
			tfAds_dom = new CustomTextField();
			tfAds_dom.setColumns(10);
			
			tfAds_korp = new CustomTextField();
			tfAds_korp.setColumns(10);
			
			tfAds_kv = new CustomTextField();
			tfAds_kv.setColumns(10);
			
			JLabel lblNewLabel_20 = new JLabel("Местность");
			
			rdbtnMs_gor = new JRadioButton("городская");
			BtnGroup_ms.add(rdbtnMs_gor);
			
			rdbtnMs_selo = new JRadioButton("сельская");
			BtnGroup_ms.add(rdbtnMs_selo);
			
			JLabel lblNewLabel_21 = new JLabel("*Семейное положение");
			
				
					JLabel lblNewLabel_22 = new JLabel("*Образование");
					
					
					JLabel lblNewLabel_23 = new JLabel("*Занятость");
					
					
					JLabel lblNewLabel = new JLabel("Фамилия\r\n");
					
					tfFam = new CustomTextField();
					tfFam.setFont(new Font("Tahoma", Font.PLAIN, 11));
					tfFam.setEditable(false);
					tfFam.setColumns(10);
					
					JLabel lblNewLabel_1 = new JLabel("Имя");
					
					tfIm = new CustomTextField();
					tfIm.setFont(new Font("Tahoma", Font.PLAIN, 11));
					tfIm.setEditable(false);
					tfIm.setColumns(10);
					
					JLabel lblNewLabel_2 = new JLabel("Отчество");
					
					tfOt = new CustomTextField();
					tfOt.setFont(new Font("Tahoma", Font.PLAIN, 11));
					tfOt.setEditable(false);
					tfOt.setColumns(10);
					
					JLabel lblNewLabel_3 = new JLabel("Пол");
					
					tfPol = new CustomTextField();
					tfPol.setFont(new Font("Tahoma", Font.PLAIN, 11));
					tfPol.setEditable(false);
					tfPol.setColumns(10);
					
					JLabel lblNewLabel_4 = new JLabel("Дата рождения\r\n");
					
					tfDatar = new CustomDateEditor();
					tfDatar.setFont(new Font("Tahoma", Font.PLAIN, 11));
					tfDatar.setEditable(false);
					tfDatar.setColumns(10);
					
					JLabel lblNewLabel_5 = new JLabel("Дата смерти");
					
					tfDatas = new CustomDateEditor();
					tfDatas.setColumns(10);
					//tfDatas.addActionListener(new ActionListener() {
					//	
					//	@Override
					//	public void actionPerformed(ActionEvent e) {
					//		aBool = changeValue();
					//		System.out.println("data");
					//		//panel_6.setVisible(aBool);
					//		
					//	}
					//});
							
					JLabel lblNewLabel_6 = new JLabel("время");
					
					tfVrems = new CustomTimeEditor();
					tfVrems.setColumns(10);
					
					tfAdm_obl = new JTextField();
					tfAdm_obl.setEditable(false);
					tfAdm_obl.setColumns(10);
					
					tfAdm_raion = new CustomTextField();
					tfAdm_raion.setColumns(10);
					
					tfAdm_gorod = new CustomTextField();
					tfAdm_gorod.setEditable(false);
					tfAdm_gorod.setColumns(10);
					
					tfAdm_ul = new CustomTextField();
					tfAdm_ul.setEditable(false);
					tfAdm_ul.setColumns(10);
					
					tfAdm_dom = new CustomTextField();
					tfAdm_dom.setEditable(false);
					tfAdm_dom.setColumns(10);
					
					tfAdm_korp = new CustomTextField();
					tfAdm_korp.setEditable(false);
					tfAdm_korp.setColumns(10);
					
					tfAdm_kv = new CustomTextField();
					tfAdm_kv.setEditable(false);
					tfAdm_kv.setColumns(10);
					
					rdbtnMjit_gor = new JRadioButton("городская");
					BtnGroup_mjit.add(rdbtnMjit_gor);
					
					rdbtnMjit_selo = new JRadioButton("сельская");
					BtnGroup_mjit.add(rdbtnMjit_selo);
					
					JLabel lblNewLabel_7 = new JLabel("Место постоянного жительства:");
					lblNewLabel_7.setFont(new Font("Tahoma", Font.BOLD, 11));
					
					JLabel lblNewLabel_8 = new JLabel("Место смерти:");
					lblNewLabel_8.setFont(new Font("Tahoma", Font.BOLD, 11));
					
					JLabel lblNewLabel_9 = new JLabel("В случае смерти детей в возрасте от 168 час. до 1 года пункты, отмеченные * , заполняются в отношении матери");
					lblNewLabel_9.setForeground(Color.BLUE);
					GroupLayout gl_panel_5 = new GroupLayout(panel_5);
					gl_panel_5.setHorizontalGroup(
						gl_panel_5.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel_5.createSequentialGroup()
								.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel_5.createSequentialGroup()
										.addContainerGap()
										.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_panel_5.createSequentialGroup()
												.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
													.addGroup(gl_panel_5.createSequentialGroup()
														.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING)
															.addGroup(gl_panel_5.createSequentialGroup()
																.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING)
																	.addComponent(lblNewLabel_14)
																	.addComponent(lblNewLabel_15)
																	.addComponent(lblNewLabel_17)
																	.addComponent(lblNewLabel_16))
																.addPreferredGap(ComponentPlacement.RELATED))
															.addGroup(gl_panel_5.createSequentialGroup()
																.addComponent(lblNewLabel_13)
																.addGap(1))
															.addGroup(gl_panel_5.createSequentialGroup()
																.addComponent(lblNewLabel_20)
																.addPreferredGap(ComponentPlacement.RELATED)))
														.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
															.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING, false)
																.addComponent(tfAdm_ul, Alignment.LEADING)
																.addComponent(tfAdm_gorod, Alignment.LEADING)
																.addComponent(tfAdm_raion, Alignment.LEADING)
																.addComponent(tfAdm_obl, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 272, Short.MAX_VALUE))
															.addGroup(gl_panel_5.createSequentialGroup()
																.addComponent(tfAdm_dom, GroupLayout.PREFERRED_SIZE, 67, GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(tfAdm_korp, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(tfAdm_kv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
															.addGroup(gl_panel_5.createSequentialGroup()
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(rdbtnMjit_gor)
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addComponent(rdbtnMjit_selo)))
														.addGap(18)
														.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
															.addGroup(gl_panel_5.createSequentialGroup()
																.addComponent(rdbtnMs_gor)
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addComponent(rdbtnMs_selo))
															.addGroup(gl_panel_5.createSequentialGroup()
																.addComponent(tfAds_dom, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addComponent(tfAds_korp, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.UNRELATED)
																.addComponent(tfAds_kv, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
															.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING, false)
																.addComponent(tfAds_raion, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
																.addComponent(tfAds_gorod)
																.addComponent(tfAds_obl, GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
																.addComponent(tfAds_ul))))
													.addGroup(gl_panel_5.createSequentialGroup()
														.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING, false)
															.addGroup(Alignment.LEADING, gl_panel_5.createSequentialGroup()
																.addComponent(lblNewLabel_23)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(cmb_zan, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
															.addGroup(Alignment.LEADING, gl_panel_5.createSequentialGroup()
																.addComponent(lblNewLabel_21)
																.addPreferredGap(ComponentPlacement.RELATED)
																.addComponent(cmb_semp, GroupLayout.PREFERRED_SIZE, 311, GroupLayout.PREFERRED_SIZE)))
														.addGap(18)
														.addComponent(lblNewLabel_22)
														.addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(cmb_obraz, GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
														.addPreferredGap(ComponentPlacement.RELATED)))
												.addGap(52))
											.addGroup(gl_panel_5.createSequentialGroup()
												.addComponent(lblNewLabel)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(tfFam, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(lblNewLabel_1)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(tfIm, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(lblNewLabel_2)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(tfOt, GroupLayout.PREFERRED_SIZE, 249, GroupLayout.PREFERRED_SIZE))
											.addGroup(gl_panel_5.createSequentialGroup()
												.addComponent(lblNewLabel_3)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(tfPol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(lblNewLabel_4)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(tfDatar, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(lblNewLabel_5)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(tfDatas, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(lblNewLabel_6)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(tfVrems, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))))
									.addGroup(gl_panel_5.createSequentialGroup()
										.addGap(185)
										.addComponent(lblNewLabel_7)
										.addGap(116)
										.addComponent(lblNewLabel_8))
									.addGroup(gl_panel_5.createSequentialGroup()
										.addContainerGap()
										.addComponent(lblNewLabel_9)))
								.addGap(111))
					);
					gl_panel_5.setVerticalGroup(
						gl_panel_5.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel_5.createSequentialGroup()
								.addContainerGap()
								.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel)
									.addComponent(tfFam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_1)
									.addComponent(tfIm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_2)
									.addComponent(tfOt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(4)
								.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
									.addComponent(tfPol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_3)
									.addComponent(lblNewLabel_4)
									.addComponent(tfDatar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_5)
									.addComponent(tfDatas, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_6)
									.addComponent(tfVrems, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel_7)
									.addComponent(lblNewLabel_8))
								.addGap(4)
								.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel_13)
									.addComponent(tfAdm_obl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(tfAds_obl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel_14)
									.addComponent(tfAdm_raion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(tfAds_raion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel_15)
									.addComponent(tfAdm_gorod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(tfAds_gorod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel_16)
									.addComponent(tfAdm_ul, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(tfAds_ul, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel_17)
									.addComponent(tfAdm_dom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(tfAdm_korp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(tfAdm_kv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(tfAds_dom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(tfAds_korp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(tfAds_kv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel_20)
									.addComponent(rdbtnMjit_gor)
									.addComponent(rdbtnMjit_selo)
									.addComponent(rdbtnMs_gor)
									.addComponent(rdbtnMs_selo))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel_21)
									.addComponent(cmb_semp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_22)
									.addComponent(cmb_obraz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGap(9)
								.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel_23)
									.addComponent(cmb_zan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
								.addComponent(lblNewLabel_9))
					);
					panel_5.setLayout(gl_panel_5);
					panel_1.setLayout(gl_panel_1);
		cmbNastupila = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_z60);
		cmbProiz = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_z70);
		cmbVid_tr = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_z01);
		cmbUstan = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_z80);
		cmbCdol = new ThriftStringClassifierCombobox<>(true);
		cmbOsn = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_z90);
		cmbPsm_ad = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_p07);
		cmbPsm_bd = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_p07);
		cmbPsm_vd = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_p07);
		cmbPsm_ag = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_p07);
		cmbPsm_pd = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_p07);
		cmbPsm_p1d = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_p07);
		cmbPsm_p2d = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_p07);
		cmbUmerla = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_u50);
		tfCvrach = new ThriftIntegerClassifierCombobox<>(true);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Обстоятельства и причины смерти", null, panel_2, null);
		
		JLabel lblNewLabel_11 = new JLabel("Смерть: наступила");
		
		
		JLabel lblNewLabel_12 = new JLabel("произошла от");
		
		
		JLabel lblNewLabel_18 = new JLabel("Дата травмы ");
		
		tfDatatr = new CustomDateEditor();
		tfDatatr.setColumns(10);
		
		JLabel lblNewLabel_19 = new JLabel("время");
		
		tfVrem_tr = new CustomTimeEditor();
		tfVrem_tr.setColumns(10);
		
		JLabel lblNewLabel_25 = new JLabel("вид травмы");
		
		
		JLabel lblNewLabel_32 = new JLabel("Обстоятельства");
		
		tfObst = new CustomTextField();
		tfObst.setColumns(10);
		
		JLabel lblNewLabel_33 = new JLabel("Причины установлены");
		
		
		JLabel lblNewLabel_34 = new JLabel("врач: ФИО");
		
		//tfCvrach = new JTextField();
		//tfCvrach.setColumns(10);
		
		JLabel lblNewLabel_35 = new JLabel("должность");
		
		
		JLabel lblNewLabel_36 = new JLabel("на основании");
		
		
		JLabel lblNewLabel_37 = new JLabel("Причины смерти:");
		lblNewLabel_37.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel_38 = new JLabel("Длительность пат. процесса");
		
		JLabel lblNewLabel_39 = new JLabel("I.a)");
		lblNewLabel_39.setFont(new Font("Tahoma", Font.BOLD, 11));
		
//		MouseAdapter mkbAdapt = new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				if (e.getClickCount() == 2) {
//					StringClassifier res = MainForm.conMan.showMkbTreeForm("МКБ", ((JTextField) e.getSource()).getText());
//					
//					if (res != null) {
//						((JTextField) e.getSource()).setText(res.pcod);
//						opDiag = res.name;
//					}
//				}
//			}
//		};
		
		tfPsm_a = new CustomTextField();
		tfPsm_a.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					StringClassifier res = MainForm.conMan.showMkbTreeForm("МКБ",(( JTextField) e.getSource()).getText());
					if (res != null) {
						tfPsm_a.setText(res.pcod);
						if (tfPsm_an.getText().isEmpty()) tfPsm_an.setText(res.name);
					}
				}
			}
			
		});
		//		tfPsm_a.addKeyListener(new KeyAdapter() {
		//			public void keyPressed(KeyEvent e) {
		//				if ((e.getKeyCode() == KeyEvent.VK_ENTER))
		//					System.out.println(opDiag);
		//			}
		//			public void keyReleased(KeyEvent e) {
		//				if ((e.getKeyCode() == KeyEvent.VK_TAB))
		//					System.out.println(opDiag);
		//			}
		//		});
				tfPsm_a.setColumns(10);
				
				tfPsm_an = new JTextArea();
				tfPsm_an.setLineWrap(true);
				
				tfPsm_ak = new CustomTextField();
				tfPsm_ak.setColumns(10);
				
				
				JLabel lblNewLabel_40 = new JLabel("б)");
				lblNewLabel_40.setFont(new Font("Tahoma", Font.BOLD, 11));
				
				tfPsm_b = new CustomTextField();
				tfPsm_b.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 2) {
							StringClassifier res = MainForm.conMan.showMkbTreeForm("МКБ",(( JTextField) e.getSource()).getText());
							if (res != null) {
								tfPsm_b.setText(res.pcod);
								if (tfPsm_bn.getText().isEmpty()) tfPsm_bn.setText(res.name);
							}
						}
					}
					
				});
				tfPsm_b.setColumns(10);
				
				tfPsm_bn = new JTextArea();
				tfPsm_bn.setLineWrap(true);
				
				tfPsm_bk = new CustomTextField();
				tfPsm_bk.setColumns(10);
				
				
				JLabel lblNewLabel_41 = new JLabel("в)");
				lblNewLabel_41.setFont(new Font("Tahoma", Font.BOLD, 11));
				
				tfPsm_v = new CustomTextField();
				tfPsm_v.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 2) {
							StringClassifier res = MainForm.conMan.showMkbTreeForm("МКБ",(( JTextField) e.getSource()).getText());
							if (res != null) {
								tfPsm_v.setText(res.pcod);
								if (tfPsm_vn.getText().isEmpty()) tfPsm_vn.setText(res.name);
							}
						}
					}
					
				});
				tfPsm_v.setColumns(10);
				
				tfPsm_vn = new JTextArea();
				tfPsm_vn.setLineWrap(true);
				
				tfPsm_vk = new CustomTextField();
				tfPsm_vk.setColumns(10);
				
				
				JLabel lblNewLabel_42 = new JLabel("г)");
				lblNewLabel_42.setFont(new Font("Tahoma", Font.BOLD, 11));
				
				tfPsm_g = new CustomTextField();
				tfPsm_g.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 2) {
							StringClassifier res = MainForm.conMan.showMkbTreeForm("МКБ",(( JTextField) e.getSource()).getText());
							if (res != null) {
								tfPsm_g.setText(res.pcod);
								if (tfPsm_gn.getText().isEmpty()) tfPsm_gn.setText(res.name);
							}
						}
					}
					
				});
				tfPsm_g.setColumns(10);
				
				tfPsm_gn = new JTextArea();
				tfPsm_gn.setLineWrap(true);
				
				tfPsm_gk = new CustomTextField();
				tfPsm_gk.setColumns(10);
				
				
				JLabel lblNewLabel_43 = new JLabel("II.");
				lblNewLabel_43.setFont(new Font("Tahoma", Font.BOLD, 11));
				
				tfPsm_p = new CustomTextField();
				tfPsm_p.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 2) {
							StringClassifier res = MainForm.conMan.showMkbTreeForm("МКБ",(( JTextField) e.getSource()).getText());
							if (res != null) {
								tfPsm_p.setText(res.pcod);
								if (tfPsm_pn.getText().isEmpty()) tfPsm_pn.setText(res.name);
							}
						}
					}
					
				});
				tfPsm_p.setColumns(10);
				
				tfPsm_pn = new JTextArea();
				tfPsm_pn.setLineWrap(true);
				
				tfPsm_pk = new CustomTextField();
				tfPsm_pk.setColumns(10);
				
				
				tfPsm_p1 = new CustomTextField();
				tfPsm_p1.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 2) {
							StringClassifier res = MainForm.conMan.showMkbTreeForm("МКБ",(( JTextField) e.getSource()).getText());
							if (res != null) {
								tfPsm_p1.setText(res.pcod);
								if (tfPsm_p1n.getText().isEmpty()) tfPsm_p1n.setText(res.name);
							}
						}
					}
					
				});
				tfPsm_p1.setColumns(10);
				
				tfPsm_p2 = new CustomTextField();
				tfPsm_p2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (e.getClickCount() == 2) {
							StringClassifier res = MainForm.conMan.showMkbTreeForm("МКБ",(( JTextField) e.getSource()).getText());
							if (res != null) {
								tfPsm_p2.setText(res.pcod);
								if (tfPsm_p2n.getText().isEmpty()) tfPsm_p2n.setText(res.name);
							}
						}
					}
					
				});
				tfPsm_p2.setColumns(10);
				
				tfPsm_p1n = new JTextArea();
				tfPsm_p1n.setLineWrap(true);
				
				tfPsm_p1k = new CustomTextField();
				tfPsm_p1k.setColumns(10);
				
				
				tfPsm_p2n = new JTextArea();
				tfPsm_p2n.setLineWrap(true);
				
				tfPsm_p2k = new CustomTextField();
				tfPsm_p2k.setColumns(10);
				
				
				JLabel lblNewLabel_44 = new JLabel("ДТП:  ");
				
				chckbxDtp30 = new JCheckBox("смерть наступила в течение 30 суток");
				
				chckbxDtp7 = new JCheckBox("из них в течение 7 суток");
				
				JLabel lblNewLabel_45 = new JLabel("В случае смерти во время беременности....");
				
				
				JLabel lblNewLabel_46 = new JLabel("Заполнил свидетельство");
				
				tfZapolnil = new CustomTextField();
				tfZapolnil.setEditable(false);
				tfZapolnil.setColumns(10);
				GroupLayout gl_panel_2 = new GroupLayout(panel_2);
				gl_panel_2.setHorizontalGroup(
					gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addComponent(lblNewLabel_44)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(chckbxDtp30)
									.addGap(18)
									.addComponent(chckbxDtp7)
									.addContainerGap(305, Short.MAX_VALUE))
								.addGroup(gl_panel_2.createSequentialGroup()
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_2.createSequentialGroup()
											.addComponent(lblNewLabel_45)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(cmbUmerla, 0, 381, Short.MAX_VALUE))
										.addGroup(gl_panel_2.createSequentialGroup()
											.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
												.addGroup(gl_panel_2.createSequentialGroup()
													.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
														.addComponent(lblNewLabel_42)
														.addComponent(lblNewLabel_40)
														.addComponent(lblNewLabel_39)
														.addComponent(lblNewLabel_43))
													.addPreferredGap(ComponentPlacement.RELATED)
													.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
														.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
															.addComponent(tfPsm_p, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
															.addComponent(tfPsm_a, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
															.addComponent(tfPsm_p1, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
															.addComponent(tfPsm_p2, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE))
														.addComponent(tfPsm_b, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)))
												.addGroup(gl_panel_2.createSequentialGroup()
													.addComponent(lblNewLabel_41)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
														.addComponent(tfPsm_v, Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
														.addComponent(tfPsm_g, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
												.addComponent(tfPsm_gn, GroupLayout.PREFERRED_SIZE, 513, Short.MAX_VALUE)
												.addComponent(tfPsm_an, GroupLayout.PREFERRED_SIZE, 513, Short.MAX_VALUE)
												.addComponent(tfPsm_bn, GroupLayout.PREFERRED_SIZE, 513, Short.MAX_VALUE)
												.addComponent(tfPsm_p2n, GroupLayout.PREFERRED_SIZE, 513, Short.MAX_VALUE)
												.addComponent(tfPsm_p1n, GroupLayout.PREFERRED_SIZE, 513, Short.MAX_VALUE)
												.addComponent(tfPsm_pn, GroupLayout.PREFERRED_SIZE, 513, Short.MAX_VALUE)
												.addComponent(tfPsm_vn, GroupLayout.PREFERRED_SIZE, 513, Short.MAX_VALUE))))
									.addGap(10)
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
										.addComponent(tfPsm_p2k, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addComponent(tfPsm_gk, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addComponent(tfPsm_vk, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addComponent(tfPsm_bk, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addComponent(tfPsm_ak, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addComponent(tfPsm_pk, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addComponent(tfPsm_p1k, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
										.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
											.addComponent(cmbPsm_p2d, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(cmbPsm_p1d, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(cmbPsm_pd, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(cmbPsm_ad, GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE))
										.addComponent(cmbPsm_bd, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
										.addComponent(cmbPsm_ag, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
										.addComponent(cmbPsm_vd, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
									.addContainerGap())
								.addGroup(gl_panel_2.createSequentialGroup()
									.addComponent(lblNewLabel_46)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfZapolnil, GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
									.addGap(311))
								.addGroup(gl_panel_2.createSequentialGroup()
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_2.createSequentialGroup()
											.addComponent(lblNewLabel_11)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(cmbNastupila, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(lblNewLabel_12)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(cmbProiz, GroupLayout.PREFERRED_SIZE, 384, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel_2.createSequentialGroup()
											.addComponent(lblNewLabel_18)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(tfDatatr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(lblNewLabel_19)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(tfVrem_tr, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(lblNewLabel_25)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(cmbVid_tr, GroupLayout.PREFERRED_SIZE, 284, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel_2.createSequentialGroup()
											.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_panel_2.createSequentialGroup()
													.addComponent(lblNewLabel_35)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(cmbCdol, GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
													.addGap(18)
													.addComponent(lblNewLabel_36)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(cmbOsn, GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE))
												.addGroup(gl_panel_2.createSequentialGroup()
													.addComponent(lblNewLabel_33)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(cmbUstan, GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE)
													.addGap(18)
													.addComponent(lblNewLabel_34)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(tfCvrach, GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE))
												.addGroup(gl_panel_2.createSequentialGroup()
													.addComponent(lblNewLabel_32)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(tfObst, GroupLayout.DEFAULT_SIZE, 639, Short.MAX_VALUE))
												.addGroup(gl_panel_2.createSequentialGroup()
													.addComponent(lblNewLabel_37)
													.addPreferredGap(ComponentPlacement.RELATED, 472, Short.MAX_VALUE)
													.addComponent(lblNewLabel_38)))
											.addGap(51)))
									.addGap(43))))
				);
				gl_panel_2.setVerticalGroup(
					gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_11)
								.addComponent(cmbNastupila, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_12)
								.addComponent(cmbProiz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_18)
								.addComponent(tfDatatr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_19)
								.addComponent(tfVrem_tr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_25)
								.addComponent(cmbVid_tr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_32)
								.addComponent(tfObst, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_33)
								.addComponent(cmbUstan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_34)
								.addComponent(tfCvrach, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_35)
								.addComponent(cmbCdol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cmbOsn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_36))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_37)
								.addComponent(lblNewLabel_38))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel_39)
									.addComponent(tfPsm_a, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
									.addComponent(tfPsm_ak, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(cmbPsm_ad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(tfPsm_an, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE))
							.addGap(6)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(tfPsm_bn, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfPsm_b, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfPsm_bk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cmbPsm_bd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_40))
							.addGap(13)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(tfPsm_vn, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfPsm_vk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cmbPsm_vd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfPsm_v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_41))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_42)
								.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
									.addComponent(tfPsm_gn, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
									.addComponent(tfPsm_g, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
									.addComponent(tfPsm_gk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(cmbPsm_ag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
									.addComponent(tfPsm_p, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_43))
								.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
									.addComponent(tfPsm_pk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(cmbPsm_pd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(tfPsm_pn, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(tfPsm_p1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
									.addComponent(tfPsm_p1n, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
									.addComponent(tfPsm_p1k, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(cmbPsm_p1d, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(tfPsm_p2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(tfPsm_p2n, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
									.addComponent(tfPsm_p2k, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(cmbPsm_p2d, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(18)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_44)
								.addComponent(chckbxDtp30)
								.addComponent(chckbxDtp7))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_45)
								.addComponent(cmbUmerla, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_46)
								.addComponent(tfZapolnil, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
				panel_2.setLayout(gl_panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setToolTipText("");
		tabbedPane.addTab("Сведения о получателе", null, panel_3, null);
		
		JLabel lblNewLabel_47 = new JLabel("Получатель: ФИО");
		
		tfFam_pol = new CustomTextField();
		tfFam_pol.setColumns(10);
		
		JLabel lblNewLabel_48 = new JLabel("Документ, удостоверяющий личность:");
		
		tfSdok = new CustomTextField();
		tfSdok.setColumns(10);
		
		JLabel lblNewLabel_49 = new JLabel("серия");
		
		JLabel lblNewLabel_50 = new JLabel("номер");
		
		tfNdok = new CustomTextField();
		tfNdok.setColumns(10);
		
		JLabel lblNewLabel_51 = new JLabel("дата выдачи");
		
		tfDvdok = new CustomDateEditor();
		tfDvdok.setColumns(10);
		
		JLabel lblNewLabel_52 = new JLabel("кем выдан");
		
		tfKvdok = new CustomTextField();
		tfKvdok.setColumns(10);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_panel_3.createSequentialGroup()
								.addComponent(lblNewLabel_52)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tfKvdok))
							.addGroup(gl_panel_3.createSequentialGroup()
								.addComponent(lblNewLabel_49)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tfSdok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(lblNewLabel_50)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tfNdok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(18)
								.addComponent(lblNewLabel_51)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tfDvdok, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING, false)
							.addGroup(gl_panel_3.createSequentialGroup()
								.addComponent(lblNewLabel_48)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(cmbVdok, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
							.addGroup(gl_panel_3.createSequentialGroup()
								.addComponent(lblNewLabel_47)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tfFam_pol, GroupLayout.PREFERRED_SIZE, 412, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(145, Short.MAX_VALUE))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGap(14)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
						.addComponent(tfFam_pol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_47))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_48)
						.addComponent(cmbVdok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_49)
						.addComponent(tfSdok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_50)
						.addComponent(tfNdok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_51)
						.addComponent(tfDvdok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_52)
						.addComponent(tfKvdok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(431, Short.MAX_VALUE))
		);
		panel_3.setLayout(gl_panel_3);
		frame.getContentPane().add(tabbedPane);

			}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 747, 648);
		frame.setExtendedState(frame.MAXIMIZED_BOTH);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle(configuration.appName);
	}
	
	@Override
	public String getName() {
		return configuration.appName;
	}
	
	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		super.onConnect(conn);
		if (conn instanceof ThriftMss.Client) {
			tcl = thrClient;
		}
		onTclConnect();

	}
	
	public void onTclConnect() {
		try {
			cmb_obraz.setData(MainForm.tcl.get_n_z00());
			cuserClpu = MainForm.authInfo.getClpu();
			cuserCslu = MainForm.authInfo.getCslu();
			cuserPodr = MainForm.authInfo.getCpodr();
			tfCvrach.setData(MainForm.tcl.gets_vrach(cuserClpu, cuserCslu, cuserPodr));
			cmbCdol.setData(MainForm.tcl.gets_dolj(cuserClpu, cuserCslu, cuserPodr));
			
		} catch (KmiacServerException | TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

		public boolean changeValue() {
		boolean aBool = false;			
		if ((tfDatas.getDate() != null) && (tfDatar.getDate() != null)) {
						dsmert = tfDatas.getDate().getTime();
						ktime = (dsmert - drojd)/3600000;
						if (ktime >= 168 && ktime <= 8766) aBool = true;
		}
						return aBool;		
		
	}
		public String monthName() {
			String mName = "";
			if (nomMonth.equals("01")) mName = "января";
			if (nomMonth.equals("02")) mName = "февраля";
			if (nomMonth.equals("03")) mName = "марта";
			if (nomMonth.equals("04")) mName = "апреля";
			if (nomMonth.equals("05")) mName = "мая";
			if (nomMonth.equals("06")) mName = "июня";
			if (nomMonth.equals("07")) mName = "июля";
			if (nomMonth.equals("08")) mName = "августа";
			if (nomMonth.equals("09")) mName = "сентября";
			if (nomMonth.equals("10")) mName = "октября";
			if (nomMonth.equals("11")) mName = "ноября";
			if (nomMonth.equals("12")) mName = "декабря";
			return mName;
			
		}
		
		/** kolMonth() - число полных месяцев жизни и дней ребенка до 1 года
		 * возвращает символьную строку в виде "мм#дд"
		 */
		public String kolMonth() {
			String month_day = "";
			int mesr = 0;
			int denr = 0;
			int godr = 0;
			int mess = 0;
			int dens = 0;
			int gods = 0;
			int mes = 0;
			int den = 0;
			denr = Integer.valueOf(sdfDay.format(tfDatar.getDate()));
			mesr = Integer.valueOf(sdfMonth.format(tfDatar.getDate()));
			godr = Integer.valueOf(sdfYear.format(tfDatar.getDate()));
			dens = Integer.valueOf(sdfDay.format(tfDatas.getDate()));
			mess = Integer.valueOf(sdfMonth.format(tfDatas.getDate()));
			gods = Integer.valueOf(sdfYear.format(tfDatas.getDate()));
			if (mess > mesr) 
				mes = mess-mesr;
				else mes = 12+mess-mesr;
			if (dens < denr) {
					mes = mess-mesr-1;
					if ((mess-1 == 4) || (mess-1 == 6) || (mess-1 == 9) || (mess-1 == 11)) den = 30+dens-denr;
					else if (mess-1 == 2) den = 28+dens-denr;
					else den = 31+dens-denr;
				}
				else den = dens-denr;
			month_day = String.valueOf(mes)+'#'+String.valueOf(den);
			return month_day;
		}
		
		/**Vozrast() - полных лет
		 * возвращает количество полных лет
		 */
		public int Vozrast() {
			int mesr = 0;
			int denr = 0;
			int godr = 0;
			int mess = 0;
			int dens = 0;
			int gods = 0;
			int god = 0;
			int den = 0;
			denr = Integer.valueOf(sdfDay.format(tfdatarm.getDate()));
			mesr = Integer.valueOf(sdfMonth.format(tfdatarm.getDate()));
			godr = Integer.valueOf(sdfYear.format(tfdatarm.getDate()));
			dens = Integer.valueOf(sdfDay.format(tfDatas.getDate()));
			mess = Integer.valueOf(sdfMonth.format(tfDatas.getDate()));
			gods = Integer.valueOf(sdfYear.format(tfDatas.getDate()));
			god = gods-godr;
			if ((mess == mesr) && (dens < denr)) god = god-1;
			if (mess < mesr) god = god -1;
			return god;
		}

	    /** CheckInfo - контроль на корректность заполнения медицинской информации о смерти
	     *  возвращает 0 - если информация заполнена корректно, любое число > 0 - есть ошибки
	     */
		public int CheckInfo() {
			String s_pro = "";
			int mistake = 0;
			if ((tfNomer.getText().isEmpty()) || (tfNomer.getText().trim() == "0")) {
				s_pro += "номер свидетельства о смерти не заполнен" + System.lineSeparator();
				mistake = mistake+1;
			}
			if (tfDatav.getDate() == null) {
				s_pro += "дата выдачи не заполнена" + System.lineSeparator();
				mistake = mistake+1;
			}
			if (cmbVid.getText().isEmpty()) {
				s_pro += "вид свидетельства не заполнен" + System.lineSeparator();
				mistake = mistake+1;
			}
			if (tfDatas.getDate() == null) {
				s_pro += "дата смерти не заполнена" + System.lineSeparator();
				mistake = mistake+1;
			}
			if (mistake >0) {
				JOptionPane.showMessageDialog(panel_6, s_pro, "Ошибки", JOptionPane.ERROR_MESSAGE);
			}
			return mistake;
		}
}
