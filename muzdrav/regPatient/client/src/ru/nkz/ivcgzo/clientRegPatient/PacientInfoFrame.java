package ru.nkz.ivcgzo.clientRegPatient;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.Color;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;

import org.apache.thrift.TException;
import org.eclipse.wb.swing.FocusTraversalOnArray;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomNumberEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTableItemChangeEvent;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTableItemChangeEventListener;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextComponentWrapper;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextComponentWrapper.DefaultLanguage;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTimeEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftRegPatient.Address;
import ru.nkz.ivcgzo.thriftRegPatient.Agent;
import ru.nkz.ivcgzo.thriftRegPatient.AgentNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.AllGosp;
import ru.nkz.ivcgzo.thriftRegPatient.AllLgota;
import ru.nkz.ivcgzo.thriftRegPatient.Gosp;
import ru.nkz.ivcgzo.thriftRegPatient.GospNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.Info;
import ru.nkz.ivcgzo.thriftRegPatient.Kontingent;
import ru.nkz.ivcgzo.thriftRegPatient.KontingentAlreadyExistException;
import ru.nkz.ivcgzo.thriftRegPatient.KontingentNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.Lgota;
import ru.nkz.ivcgzo.thriftRegPatient.LgotaAlreadyExistException;
import ru.nkz.ivcgzo.thriftRegPatient.LgotaNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.Nambk;
import ru.nkz.ivcgzo.thriftRegPatient.NambkNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.OgrnNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.PatientAlreadyExistException;
import ru.nkz.ivcgzo.thriftRegPatient.PatientBrief;
import ru.nkz.ivcgzo.thriftRegPatient.PatientFullInfo;
import ru.nkz.ivcgzo.thriftRegPatient.PatientGospYesOrNoNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.Polis;
import ru.nkz.ivcgzo.thriftRegPatient.Sign;
import ru.nkz.ivcgzo.thriftRegPatient.SmocodNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.SmorfNotFoundException;

public class PacientInfoFrame extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTabbedPane tbMain;
    private JTabbedPane tpPersonal;
    private JTabbedPane tpLgota;
    private JTabbedPane tpKateg;
    private JTabbedPane tpAgent;
    private JTabbedPane tpPriem;
    public static int curPatientId = 0;
    private int curNgosp = 0;
    private int curId = 0;
    private int curId_otd = 0;
    private int curId_lgt = 0;
    private int Terp = 0;
    private final ButtonGroup btnGroup_pol = new ButtonGroup();
    private final ButtonGroup btnGroup_pol_pr = new ButtonGroup();
    private final ButtonGroup btnGroup_plextr = new ButtonGroup();
    private final ButtonGroup btnGroup_pp = new ButtonGroup();
    private CustomTextField tfFam;
    private CustomTextField tfIm;
    private CustomTextField tfOt;
    private CustomTextField tf_Adp_kv;
    private CustomTextField tf_Adm_kv;
    private CustomTextField tf_oms_ser;
    private CustomTextField tf_dms_ser;
    private CustomTextField tf_oms_nom;
    private CustomTextField tf_dms_nom;
    private CustomNumberEditor tfMr;
    private CustomTextField tfMrname;
    private CustomTextField tfDolj;
    private CustomTextField tfTel;
    private CustomNumberEditor tf_Cpol;
    private CustomTextField tf_Nuch;
    private CustomTextField tf_Nambk;
    private CustomTextField tf_serdoc;
    private CustomTextField tf_nomdoc;
    private CustomTextField tf_Odoc;
    private JFormattedTextField tf_Snils;
    private CustomTextField tf_Fam_pr;
    private CustomTextField tf_Im_pr;
    private CustomTextField tf_Ot_pr;
    private CustomTextField tf_birthplace;
    private CustomTextField tf_Polis_ser_pr;
    private CustomTextField tf_Polis_nom_pr;
    private CustomTextField tf_Ser_doc_pr;
    private CustomTextField tf_Nomdoc_pr;
    private CustomTextField tf_ntalon;
    private CustomTextField tf_diag_n;
    private CustomTextField tf_diag_p;
    private CustomTextField tf_smpn;
    private CustomTextField tf_nist;
    private JRadioButton rbtn_pol_m;
    private JRadioButton rbtn_pol_j;
    private JRadioButton rbtn_pol_pr_m;
    private JRadioButton rbtn_pol_pr_j;
    private JRadioButton rbtn_plan;
    private JRadioButton rbtn_extr;
    private JRadioButton rbtn_vperv;
    private JRadioButton rbtn_povt;
    private JTextArea ta_diag_p;
    private JTextArea ta_diag_n;
    private JCheckBox cbx_gosp;
    private JCheckBox cbx_smp;
    private JCheckBox cbx_ber;
    private JCheckBox cbx_messr;
    private CustomDateEditor tfDr;
    private CustomDateEditor tf_datapr;
    private CustomDateEditor tf_dataot;
    private CustomDateEditor tf_datadoc;
    private CustomDateEditor tf_dr_pr;
    private CustomDateEditor tf_datap;
    private CustomDateEditor tf_dataosm;
    private CustomDateEditor tf_datasmp;
    private CustomDateEditor tf_datagosp;
    private CustomTimeEditor tf_timep;
    private CustomTimeEditor tf_timeosm;
    private CustomTimeEditor tf_timesmp;
    private CustomTimeEditor tf_timegosp;
//    private JButton btnShowTalonSelectModule;
    private CustomDateEditor tfdust;
    private CustomDateEditor tfdotm;
    private CustomNumberEditor tfgr;
    private CustomTextField tfspr;
    private JSpinner sp_sv_time;
    private JSpinner sp_sv_day;
    public List<PatientBrief> pat;
    public static PatientFullInfo PersonalInfo;
    private Nambk NambInfo;
    private Agent AgentInfo;
	private Lgota Info_lgota;
    private AllLgota Id_lgota;
    private List<AllLgota> LgotaInfo;
    private List<Kontingent> KontingentInfo;
    private Sign SignInfo;
    private Gosp Id_gosp;
    private List<AllGosp> AllGospInfo;
//    private CustomTable<PatientBrief, PatientBrief._Fields> tbl_patient;
    private CustomTable<AllLgota, AllLgota._Fields> tbl_lgota;
    private CustomTable<Kontingent, Kontingent._Fields> tbl_kateg;
    private CustomTable<AllGosp, AllGosp._Fields> tbl_priem;
    private AnamnezPanel tpSign;
//    private PervOsmForm pervosm;

    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_status;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_ishod;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_tdoc;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_oms_doc;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_tdoc_pr;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_Polis_doc_pr;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_cotd;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_travm;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_trans;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_otkaz;
    private ThriftStringClassifierCombobox <StringClassifier> cmb_naprav;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_org;
    private ThriftStringClassifierCombobox <StringClassifier> cmb_ogrn;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_oms_smo;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_dms_smo;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_adp_obl;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_adm_obl;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_adp_gorod;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_adm_gorod;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_adp_ul;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_adm_ul;
    private ThriftStringClassifierCombobox <StringClassifier> cmb_adp_dom;
    private ThriftStringClassifierCombobox <StringClassifier> cmb_adm_dom;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_srok;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_obst;
    private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_obr;
//    private PatientBrief newPatBr;
    private AllGosp newPriem;

//    public void refresh(List<PatientBrief> pat) {
//        tbl_patient.requestFocus();
//        tbl_patient.setData(pat);
//    }
    /**
     * Create the frame.
     */
    public PacientInfoFrame(List<PatientBrief> pat) {
    	tpSign = new AnamnezPanel();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
            cmb_adp_obl = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_l02);
            cmb_adp_obl.setFont(new Font("Tahoma", Font.PLAIN, 11));
            cmb_adp_obl.setStrictCheck(false);
            cmb_adp_obl.setIllegibleSearch(false);
            cmb_adp_obl.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    if (cmb_adp_obl.getSelectedItem() != null) {
                        try {
                            cmb_adp_gorod.setData(MainForm.tcl.getL00(cmb_adp_obl.getSelectedPcod()));
                            if (cmb_adm_obl.getText().equals("")) 
                        		cmb_adm_obl.setSelectedPcod(cmb_adp_obl.getSelectedPcod());
                        } catch (KmiacServerException e) {
                            e.printStackTrace();
                        } catch (TException e) {
                            MainForm.conMan.reconnect(e);
                        }
                    } else {
                        cmb_adp_gorod.setData(null);
                    }
                }
            });
            cmb_adm_obl = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_l02);
            cmb_adm_obl.setFont(new Font("Tahoma", Font.PLAIN, 11));
            cmb_adm_obl.setStrictCheck(false);
            cmb_adm_obl.setIllegibleSearch(false);
            cmb_adm_obl.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    if (cmb_adm_obl.getSelectedItem() != null) {
                        try {
                            cmb_adm_gorod.setData(MainForm.tcl.getL00(cmb_adm_obl.getSelectedPcod()));
                        } catch (KmiacServerException e) {
                            e.printStackTrace();
                        } catch (TException e) {
                            MainForm.conMan.reconnect(e);
                        }
                    } else {
                        cmb_adm_gorod.setData(null);
                    }

                }
            });
            cmb_adp_gorod = new ThriftIntegerClassifierCombobox<>(true);
            cmb_adp_gorod.setFont(new Font("Tahoma", Font.PLAIN, 11));
            cmb_adp_gorod.setStrictCheck(false);
            cmb_adp_gorod.setIllegibleSearch(false);
            cmb_adp_gorod.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    if (cmb_adp_gorod.getSelectedItem() != null) {
                    	if (!cmb_adp_gorod.getText().substring(0, cmb_adp_gorod.getText().indexOf('.')+1).equals("НОВОКУЗНЕЦК Г."))
                    		cmb_adp_ul.setData(new ArrayList<IntegerClassifier>());
//                    	if (cmb_adm_gorod.getText().equals(""))
//								cmb_adm_gorod.setSelectedPcod(cmb_adp_gorod.getSelectedPcod());
                    }else
                        cmb_adp_ul.setData(null);
                }
            });
            cmb_adm_gorod = new ThriftIntegerClassifierCombobox<>(true);
            cmb_adm_gorod.setFont(new Font("Tahoma", Font.PLAIN, 11));
            cmb_adm_gorod.setStrictCheck(false);
            cmb_adm_gorod.setIllegibleSearch(false);
            cmb_adm_gorod.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    if (cmb_adm_gorod.getSelectedItem() != null) {
                    	if (!cmb_adm_gorod.getText().substring(0, cmb_adm_gorod.getText().indexOf('.')+1).equals("НОВОКУЗНЕЦК Г."))
                    		cmb_adm_ul.setData(new ArrayList<IntegerClassifier>());
                    }else
                        cmb_adm_ul.setData(null);
                }
            });
            cmb_adp_ul = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_u00);
            cmb_adp_ul.setFont(new Font("Tahoma", Font.PLAIN, 11));
            cmb_adp_ul.setStrictCheck(false);
            cmb_adp_ul.setIllegibleSearch(false);
            cmb_adp_ul.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    if (cmb_adp_ul.getSelectedItem() != null) {
                        try {
                            cmb_adp_dom.setData(MainForm.tcl.getU10(cmb_adp_ul.getText()));
                        } catch (KmiacServerException e) {
                            e.printStackTrace();
                        } catch (TException e) {
                            MainForm.conMan.reconnect(e);
                        }
                    } else {
                        cmb_adp_dom.setData(new ArrayList<StringClassifier>());
                    }
//                    if (cmb_adm_ul.getText().equals("")) 
//                		cmb_adm_ul.setText(cmb_adp_ul.getText());
//                		//cmb_adm_ul.setSelectedPcod(cmb_adp_ul.getSelectedPcod());
                }
            });
            cmb_adm_ul = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_u00);
            cmb_adm_ul.setFont(new Font("Tahoma", Font.PLAIN, 11));
            cmb_adm_ul.setStrictCheck(false);
            cmb_adm_ul.setIllegibleSearch(false);
            cmb_adm_ul.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    if (cmb_adm_ul.getSelectedItem() != null) {
                        try {
                            cmb_adm_dom.setData(MainForm.tcl.getU10(cmb_adm_ul.getText()));
                        } catch (KmiacServerException e) {
                            e.printStackTrace();
                        } catch (TException e) {
                            MainForm.conMan.reconnect(e);
                        }
                    } else {
                        cmb_adm_dom.setData(new ArrayList<StringClassifier>());
                    }

                }
            });
            cmb_adp_dom = new ThriftStringClassifierCombobox<>(true);
            cmb_adp_dom.setFont(new Font("Tahoma", Font.PLAIN, 11));
            cmb_adp_dom.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
//            		if (cmb_adp_dom.getSelectedItem() != null)
            			if (cmb_adm_dom.getText().equals("")) 
                    		cmb_adm_dom.setText(cmb_adp_dom.getText());
//            				cmb_adm_dom.setSelectedPcod(cmb_adp_dom.getSelectedPcod());
            	}
            });
            cmb_adp_dom.setStrictCheck(false);
            cmb_adm_dom = new ThriftStringClassifierCombobox<>(true);
            cmb_adm_dom.setFont(new Font("Tahoma", Font.PLAIN, 11));
            cmb_adm_dom.setStrictCheck(false);

            cmb_cotd = new ThriftIntegerClassifierCombobox<>(true);
            cmb_cotd.setFont(new Font("Tahoma", Font.PLAIN, 11));
            cmb_org = new ThriftIntegerClassifierCombobox<>(true);
            cmb_org.setFont(new Font("Tahoma", Font.PLAIN, 11));
            cmb_ishod = new ThriftIntegerClassifierCombobox<>(true);
            cmb_ishod.setFont(new Font("Tahoma", Font.PLAIN, 11));
            //cmb_ishod = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_abb);
            cmb_status = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_az9);
            cmb_status.setFont(new Font("Tahoma", Font.PLAIN, 11));
            cmb_status.setMaximumRowCount(27);
            cmb_obr = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_z00);
            cmb_obr.setMaximumRowCount(15);
            cmb_tdoc = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_az0);
            cmb_tdoc.setFont(new Font("Tahoma", Font.PLAIN, 11));
            cmb_tdoc.setMaximumRowCount(18);
            cmb_tdoc_pr = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_az0);
            cmb_tdoc_pr.setFont(new Font("Tahoma", Font.PLAIN, 12));
            cmb_tdoc_pr.setMaximumRowCount(18);
            cmb_srok = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0s);
            cmb_srok.setFont(new Font("Tahoma", Font.PLAIN, 12));
            cmb_obst = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0h);
            cmb_obst.setFont(new Font("Tahoma", Font.PLAIN, 12));
            cmb_Polis_doc_pr = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_f008);
            cmb_Polis_doc_pr.setFont(new Font("Tahoma", Font.PLAIN, 12));
            cmb_oms_doc = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_f008);
            cmb_oms_doc.setFont(new Font("Tahoma", Font.PLAIN, 11));
            cmb_oms_smo = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_kas);
            cmb_oms_smo.addActionListener(new ActionListener() {
            	public void actionPerformed(ActionEvent e) {
						try {
		        			if (cmb_oms_smo.getSelectedPcod() != null)
		        				cmb_ogrn.setData(MainForm.tcl.getSmorf(cmb_oms_smo.getSelectedPcod()));
		        			else cmb_ogrn.setData(null);
						} catch (SmorfNotFoundException e1) {
							e1.printStackTrace();
						} catch (KmiacServerException e1) {
							e1.printStackTrace();
						} catch (TException e1) {
							e1.printStackTrace();
	                        MainForm.conMan.reconnect(e1);
						}
            	}
            });
            cmb_oms_smo.setFont(new Font("Tahoma", Font.PLAIN, 11));
//            cmb_oms_smo.setVisible(false);
            cmb_dms_smo = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_kas);
            cmb_dms_smo.setFont(new Font("Tahoma", Font.PLAIN, 11));
            cmb_travm = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_ai0);
            cmb_travm.setFont(new Font("Tahoma", Font.PLAIN, 11));
            cmb_trans = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_vtr);
            cmb_trans.setFont(new Font("Tahoma", Font.PLAIN, 11));
            cmb_otkaz = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_af0);
            cmb_otkaz.setFont(new Font("Tahoma", Font.PLAIN, 11));
            cmb_ogrn = new ThriftStringClassifierCombobox<>(true);
            cmb_ogrn.setFont(new Font("Tahoma", Font.PLAIN, 11));
            cmb_ogrn.setMaximumRowCount(10);
            cmb_naprav = new ThriftStringClassifierCombobox<>(StringClassifiers.n_k02);
            cmb_naprav.setFont(new Font("Tahoma", Font.PLAIN, 11));
            cmb_naprav.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent arg0) {
                    try {
                        cmb_org.setData(null);
                        if (cmb_naprav.getSelectedItem() != null) {
                            if (cmb_naprav.getSelectedPcod().equals("Г"))cmb_org.setData(MainForm.tcl.getAL0());
                            if (cmb_naprav.getSelectedPcod().equals("К"))cmb_org.setData(MainForm.tcl.getN00());
                            if (cmb_naprav.getSelectedPcod().equals("Л"))cmb_org.setData(MainForm.tcl.getM00());
                            if (cmb_naprav.getSelectedPcod().equals("Р"))cmb_org.setData(MainForm.tcl.getW04());
                            if (cmb_naprav.getSelectedPcod().equals("Т"))cmb_org.setData(MainForm.tcl.getO00());
                        }
                    } catch (KmiacServerException e) {
                        e.printStackTrace();
                    } catch (TException e) {
                        e.printStackTrace();
                        MainForm.conMan.reconnect(e);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        setBounds(1, 1, 1032, 730); //ширина, высота
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        JPanel pl_print = new JPanel();
        pl_print.setForeground(Color.BLUE);
        pl_print.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Печать титульного листа", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));

        final JTabbedPane tbMain = new JTabbedPane(JTabbedPane.TOP);
        tbMain.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tbMain.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                if (tbMain.getSelectedIndex() == 1) {
                    changePatientLgotaInfo(curPatientId);
                    InfoForLgotaPatient();
                }
                if (tbMain.getSelectedIndex() == 2) {
                    changePatientKategInfo(curPatientId);
                }
                if (tbMain.getSelectedIndex() == 3) {
                    changePatientAgentInfo(curPatientId);
                }
                if (tbMain.getSelectedIndex() == 4) {
//                    changePatientSignInfo(curPatientId);
                	tpSign.ChangePatientAnamnezInfo();
                }
                if (tbMain.getSelectedIndex() == 5) {
                    selectAllPatientPriemInfo(curPatientId);
                    changePatientPriemInfo(curPatientId);
                    if (tbl_priem.getSelectedItem() !=  null) {
                        try{
                        	String nameOtd = MainForm.tcl.getNameOtdGosp(curPatientId);
                            if  (!nameOtd.isEmpty())
                                JOptionPane.showMessageDialog(tbl_priem, "Пациент: "+tfFam.getText()+" "+tfIm.getText()+" "+tfOt.getText()+"    Д.р. "+tfDr.getText()+"\n\r"+"находится на лечении: "+nameOtd);
                          } catch (PatientGospYesOrNoNotFoundException pgnfe) {
                                System.out.println("Госпитализаций нет.");
                          } catch (Exception e1) {
                            e1.printStackTrace();
                          }
                    }
                }
            }
        });

        JPanel pl_patient = new JPanel();
        pl_patient.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
//		tbl_patient = new CustomTable<>(true, true, PatientBrief.class, 1,"Фамилия",2,"Имя",3,"Отчество",0,"ВН");
//      tbl_patient.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                if (!e.getValueIsAdjusting()) {
//                    if (tbl_patient.getSelectedItem() != null) {
//                    curPatientId = tbl_patient.getSelectedItem().npasp;
//                    if (tbMain.getSelectedIndex() == 0) {
//                        changePatientPersonalInfo(curPatientId);
//                    }
//                    if (tbMain.getSelectedIndex() == 1) {
//                        changePatientLgotaInfo(curPatientId);
//                    }
//                    if (tbMain.getSelectedIndex() == 2) {
//                        changePatientKategInfo(curPatientId);
//                    }
//                    if (tbMain.getSelectedIndex() == 3) {
//                        changePatientAgentInfo(curPatientId);
//                    }
//                    if (tbMain.getSelectedIndex() == 4) {
//                        changePatientSignInfo(curPatientId);
//                    }
//                    if (tbMain.getSelectedIndex() == 5) {
//                        selectAllPatientPriemInfo(curPatientId);
//                    }
//                    }
//                }
//            }
//        });
//        tbl_patient.registerDeleteSelectedRowListener(new CustomTableItemChangeEventListener<PatientBrief>() {
//
//            @Override
//            public boolean doAction(CustomTableItemChangeEvent<PatientBrief> event) {
//                try {
//                    MainForm.tcl.deletePatient(curPatientId);
//                } catch (TException e) {
//                    e.printStackTrace();
//                    return false;
//                }
//                return true;
//            }
//        });
//
//        tbl_patient.setFillsViewportHeight(true);
//        tbl_patient.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
//
//        scrollPane.setViewportView(tbl_patient);
////        refresh(pat);
//
//        //удалить пациента
//        btnDel.addActionListener(new ActionListener() {
//            public void actionPerformed(ActionEvent arg0) {
//              try{
//                curPatientId = tbl_patient.getSelectedItem().npasp;
//                tbl_patient.requestFocus();
//                tbl_patient.deleteSelectedRow();
//              } catch (Exception e) {
//                e.printStackTrace();
//              }
//            }
//        });

        JButton btnNew = new JButton("Новый");
        btnNew.setToolTipText("Создать карту нового пациента");
        btnNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
//                    newPatBr = tbl_patient.addExternalItem();
                    curPatientId = 0;
                    NewPatient();
//                    NewAgent();
                    tfFam.requestFocus();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        JButton btnPoisk = new JButton("Поиск");
        btnPoisk.setToolTipText("Поиск пациента");
        btnPoisk.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
                 int[] res = MainForm.conMan.showPatientSearchForm("Поиск", false, true);

                if (res != null) {
                    tbMain.setSelectedIndex(0);
                    curPatientId = res[0];
                    changePatientPersonalInfo(curPatientId);
//                    changePatientLgotaInfo(curPatientId);
//                    changePatientKategInfo(curPatientId);
//                    changePatientAgentInfo(curPatientId);
////                    changePatientSignInfo(curPatientId);
//                    selectAllPatientPriemInfo(curPatientId);
//                    changePatientPriemInfo(curPatientId);
                    setTitle(String.format("%s %s %s , ВН %s", PersonalInfo.getFam(), PersonalInfo.getIm(), PersonalInfo.getOt(), PersonalInfo.getNpasp()));

                  SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            cmb_adp_obl.setText(PersonalInfo.adpAddress.region);
                            cmb_adm_obl.setText(PersonalInfo.admAddress.region);
//                            cmb_adp_obl.setSelectedPcod(PersonalInfo.getRegion_liv());
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    cmb_adm_gorod.setText(PersonalInfo.admAddress.city);
                                    cmb_adp_gorod.setText(PersonalInfo.adpAddress.city);
                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            cmb_adm_ul.setText(PersonalInfo.admAddress.street);
                                            cmb_adp_ul.setText(PersonalInfo.adpAddress.street);
                                            SwingUtilities.invokeLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    cmb_adm_dom.setText(PersonalInfo.admAddress.house);
                                                    cmb_adp_dom.setText(PersonalInfo.adpAddress.house);
                                                }
                                            });
                                        }
                                    });
                                }
                            });
                        }
                    });

                }
          }
        });

        JButton btnSave = new JButton("Сохранить");
        btnSave.setToolTipText("Сохранить изменения карты пациента");
        btnSave.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent arg0) {
                try {
                    PersonalInfo = new PatientFullInfo();
                    NambInfo = new Nambk();
                    PersonalInfo.admAddress = new Address();
                    PersonalInfo.adpAddress = new Address();
                    PersonalInfo.polis_oms = new Polis();
                    PersonalInfo.polis_dms = new Polis();
                    //PersonalInfo.nambk = new Nambk();
                    PersonalInfo.setDataz(new Date().getTime());
                    PersonalInfo.setNpasp(curPatientId);
                    if (!tfFam.getText().isEmpty()) PersonalInfo.setFam(tfFam.getText());
                    if (!tfIm.getText().isEmpty()) PersonalInfo.setIm(tfIm.getText());
                    if (!tfOt.getText().isEmpty()) PersonalInfo.setOt(tfOt.getText());
                    if (!tf_Adm_kv.getText().isEmpty()) PersonalInfo.admAddress.setFlat(tf_Adm_kv.getText());
                    if (!tf_Adp_kv.getText().isEmpty()) PersonalInfo.adpAddress.setFlat(tf_Adp_kv.getText());
                    if (!tfMrname.getText().isEmpty()) PersonalInfo.setNamemr(tfMrname.getText());
                    if (!tfMr.getText().isEmpty()) PersonalInfo.setMrab(Integer.valueOf(tfMr.getText()));
                    if (!tfDolj.getText().isEmpty()) PersonalInfo.setProf(tfDolj.getText());
                    if (!tfTel.getText().isEmpty()) PersonalInfo.setTel(tfTel.getText());
                    if (!tf_Snils.getText().isEmpty()) PersonalInfo.setSnils(tf_Snils.getText());
                    if (!tf_Odoc.getText().isEmpty()) PersonalInfo.setOdoc(tf_Odoc.getText());
                    if (!tf_serdoc.getText().isEmpty()) PersonalInfo.setDocser(tf_serdoc.getText());
                    if (!tf_nomdoc.getText().isEmpty()) PersonalInfo.setDocnum(tf_nomdoc.getText());
                    if (!tf_dms_ser.getText().isEmpty()) PersonalInfo.polis_dms.setSer(tf_dms_ser.getText());
                    if (!tf_dms_nom.getText().isEmpty()) PersonalInfo.polis_dms.setNom(tf_dms_nom.getText());
                    if (!tf_oms_ser.getText().isEmpty()) PersonalInfo.polis_oms.setSer(tf_oms_ser.getText());
                    if (!tf_oms_nom.getText().isEmpty()) PersonalInfo.polis_oms.setNom(tf_oms_nom.getText());

                    if (tfDr.getDate() != null) PersonalInfo.setDatar(tfDr.getDate().getTime());
                    if (tf_datadoc.getDate() != null) PersonalInfo.setDatadoc(tf_datadoc.getDate().getTime());

//					if (!tfDr.getText().isEmpty()) PersonalInfo.setDatar(sdf.parse(tfDr.getText()).getTime());
//					if (!tf_datadoc.getText().isEmpty()) PersonalInfo.setDatadoc(sdf.parse(tf_datadoc.getText()).getTime());
//					if (!tf_datapr.getText().isEmpty()) PersonalInfo.nambk.setDatapr(sdf.parse(tf_datapr.getText()).getTime());
//					if (!tf_dataot.getText().isEmpty()) PersonalInfo.nambk.setDataot(sdf.parse(tf_dataot.getText()).getTime());
                    if (rbtn_pol_m.isSelected()) PersonalInfo.setPol(1);
                    if (rbtn_pol_j.isSelected()) PersonalInfo.setPol(2);
                    if (cmb_dms_smo.getSelectedItem() != null) PersonalInfo.polis_dms.setStrg(cmb_dms_smo.getSelectedPcod());

                    if (cmb_oms_smo.getSelectedItem() != null)	PersonalInfo.polis_oms.setStrg(cmb_oms_smo.getSelectedPcod());
                    if (cmb_status.getSelectedItem() != null) PersonalInfo.setSgrp(cmb_status.getSelectedPcod());
                    if (cmb_obr.getSelectedItem() != null) PersonalInfo.setObraz(cmb_obr.getSelectedPcod());
                    if (cmb_oms_doc.getSelectedItem() != null) PersonalInfo.polis_oms.setTdoc(cmb_oms_doc.getSelectedPcod());
                    if (cmb_tdoc.getSelectedItem() != null) PersonalInfo.setTdoc(cmb_tdoc.getSelectedPcod());
//				    PersonalInfo.adpAddress.setRegion(tf_Adp_obl.getText().toUpperCase());
                    if (cmb_adp_obl.getSelectedItem() != null) PersonalInfo.setRegion_liv(cmb_adp_obl.getSelectedPcod());
                    PersonalInfo.adpAddress.setRegion(cmb_adp_obl.getText());
                    PersonalInfo.admAddress.setRegion(cmb_adm_obl.getText());
                    if (cmb_adp_obl.getSelectedPcod() != null){
                        if (cmb_adp_obl.getSelectedPcod() == 42)
                            PersonalInfo.adpAddress.setCity(cmb_adp_gorod.getText().substring(0,cmb_adp_gorod.getText().indexOf('.')+1));
                        else
                            PersonalInfo.adpAddress.setCity(cmb_adp_gorod.getText());
                    }
                    if (cmb_adm_obl.getSelectedPcod() != null){
                        if (cmb_adm_obl.getSelectedPcod() == 42)
                            PersonalInfo.admAddress.setCity(cmb_adm_gorod.getText().substring(0,cmb_adm_gorod.getText().indexOf('.')+1));
                        else
                            PersonalInfo.admAddress.setCity(cmb_adm_gorod.getText());
                    }
                    if (!cmb_adm_ul.getText().isEmpty()) PersonalInfo.admAddress.setStreet(cmb_adm_ul.getText());
                    if (!cmb_adp_ul.getText().isEmpty()) PersonalInfo.adpAddress.setStreet(cmb_adp_ul.getText());
                    if (cmb_adp_gorod.getSelectedItem() != null) PersonalInfo.setTer_liv(MainForm.tcl.getTerLive(cmb_adp_gorod.getSelectedPcod()));
                    if (!cmb_adm_dom.getText().isEmpty()) PersonalInfo.admAddress.setHouse(cmb_adm_dom.getText());
                    if (!cmb_adp_dom.getText().isEmpty()) PersonalInfo.adpAddress.setHouse(cmb_adp_dom.getText());
                    if (Terp != 0) PersonalInfo.setTerp(Terp); else PersonalInfo.setTerp(0);
                    if (!tf_Cpol.getText().isEmpty()) PersonalInfo.setCpol_pr(Integer.valueOf(tf_Cpol.getText()));

                    if (!tf_Nambk.getText().isEmpty()) NambInfo.setNambk(tf_Nambk.getText());
                    if (!tf_Nuch.getText().isEmpty())  NambInfo.setNuch(Integer.valueOf(tf_Nuch.getText()));
                    if (tf_datapr.getDate() != null)   NambInfo.setDatapr(tf_datapr.getDate().getTime());
                    if (tf_dataot.getDate() != null)   NambInfo.setDataot(tf_dataot.getDate().getTime());
                    if (cmb_ishod.getSelectedItem() != null) NambInfo.setIshod(cmb_ishod.getSelectedPcod());
                    NambInfo.setCpol(MainForm.authInfo.getCpodr());
                    NambInfo.setNpasp(curPatientId);

                    PersonalInfo.setBirthplace(tf_birthplace.getText());
                    try{
                        if (cmb_ogrn.getSelectedItem() != null) PersonalInfo.setOgrn_smo(MainForm.tcl.getOgrn(cmb_ogrn.getSelectedPcod()));
                    } catch (OgrnNotFoundException onfe) {
                        onfe.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (curPatientId == 0){
                        curPatientId = MainForm.tcl.addPatient(PersonalInfo);
                        NambInfo.setNpasp(curPatientId);
                       	MainForm.tcl.addNambk(NambInfo);
                    }
                    else{
                        MainForm.tcl.updatePatient(PersonalInfo);
                       	MainForm.tcl.addNambk(NambInfo);
//                        MainForm.tcl.updateNambk(NambInfo);
                    }
                } catch (PatientAlreadyExistException paee) {
                	JOptionPane.showMessageDialog(tfFam, "Пациент существует. Сделайте поиск.");
                    System.out.println("Пациент существует. Сделайте поиск.");
                } catch (Exception e) {
                	e.printStackTrace();
                }
             }
            });

            JButton btnDel = new JButton("Удалить");
            btnDel.setToolTipText("Удалить карту пациента");
            btnDel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                   	  int res = JOptionPane.showConfirmDialog(null, "Действительно удалить ?");
                   	  if (res == JOptionPane.YES_OPTION){
//                       	  MainForm.tcl.deletePatient(curPatientId, MainForm.authInfo.cpodr);
                       	  MainForm.tcl.deleteNambk(curPatientId, MainForm.authInfo.cpodr);
                       	  NewPatient();
                   	  }
	             } catch (KmiacServerException e) {
	            	 e.printStackTrace();
	             } catch (TException e) {
	                 e.printStackTrace();
	                 MainForm.conMan.reconnect(e);
	             }
                }
             });

            JButton  btnShowTalonSelectModule = new JButton("Запись на приём");
            btnShowTalonSelectModule.setToolTipText("Записать пациента на приём");
            btnShowTalonSelectModule.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                if (curPatientId != 0) {
                    MainForm.conMan.showReceptionRecordForm(curPatientId, tfFam.getText(), tfIm.getText(), tfOt.getText(), 0);
                } else {
                  JOptionPane.showMessageDialog(
                  PacientInfoFrame.this.getContentPane(), "Пациент не выбран!",
                  "Ошибка!", JOptionPane.ERROR_MESSAGE);
                }}
                });
                
                JButton btnShowError = new JButton("Контроль");
                btnShowError.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                		//MainForm.conMan.showPaspErrorsForm();
                        Integer res = MainForm.conMan.showPaspErrorsForm();

                        if (res != null) {
                            tbMain.setSelectedIndex(0);
                            curPatientId = res;
                            changePatientPersonalInfo(curPatientId);
//                            changePatientLgotaInfo(curPatientId);
//                            changePatientKategInfo(curPatientId);
//                            changePatientAgentInfo(curPatientId);
////                            changePatientSignInfo(curPatientId);
//                            selectAllPatientPriemInfo(curPatientId);
//                            changePatientPriemInfo(curPatientId);
                            setTitle(String.format("%s %s %s , ВН %s", PersonalInfo.getFam(), PersonalInfo.getIm(), PersonalInfo.getOt(), PersonalInfo.getNpasp()));

                          SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    cmb_adp_obl.setText(PersonalInfo.adpAddress.region);
                                    cmb_adm_obl.setText(PersonalInfo.admAddress.region);
//                                    cmb_adp_obl.setSelectedPcod(PersonalInfo.getRegion_liv());
                                    SwingUtilities.invokeLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            cmb_adm_gorod.setText(PersonalInfo.admAddress.city);
                                            cmb_adp_gorod.setText(PersonalInfo.adpAddress.city);
                                            SwingUtilities.invokeLater(new Runnable() {
                                                @Override
                                                public void run() {
                                                    cmb_adm_ul.setText(PersonalInfo.admAddress.street);
                                                    cmb_adp_ul.setText(PersonalInfo.adpAddress.street);
                                                    SwingUtilities.invokeLater(new Runnable() {
                                                        @Override
                                                        public void run() {
                                                            cmb_adm_dom.setText(PersonalInfo.admAddress.house);
                                                            cmb_adp_dom.setText(PersonalInfo.adpAddress.house);
                                                        }
                                                    });
                                                }
                                            });
                                        }
                                    });
                                }
                            });

                        }
                  }
//                	}
                });
                btnShowError.setToolTipText("Контроль реестров паспортной информации пациентов");

                GroupLayout gl_pl_patient = new GroupLayout(pl_patient);
                gl_pl_patient.setHorizontalGroup(
                	gl_pl_patient.createParallelGroup(Alignment.LEADING)
                		.addGroup(gl_pl_patient.createSequentialGroup()
                			.addContainerGap()
                			.addComponent(btnDel)
                			.addGap(77)
                			.addComponent(btnNew)
                			.addPreferredGap(ComponentPlacement.RELATED)
                			.addComponent(btnPoisk)
                			.addGap(8)
                			.addComponent(btnSave)
                			.addGap(23)
                			.addComponent(btnShowTalonSelectModule)
                			.addPreferredGap(ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                			.addComponent(btnShowError)
                			.addContainerGap())
                );
                gl_pl_patient.setVerticalGroup(
                	gl_pl_patient.createParallelGroup(Alignment.TRAILING)
                		.addGroup(gl_pl_patient.createSequentialGroup()
                			.addContainerGap()
                			.addGroup(gl_pl_patient.createParallelGroup(Alignment.LEADING)
                				.addGroup(gl_pl_patient.createParallelGroup(Alignment.BASELINE)
                					.addComponent(btnDel)
                					.addComponent(btnNew)
                					.addComponent(btnPoisk)
                					.addComponent(btnSave)
                					.addComponent(btnShowTalonSelectModule))
                					.addComponent(btnShowError))
                			.addContainerGap(13, Short.MAX_VALUE))
                );
        pl_patient.setLayout(gl_pl_patient);

        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
        				.addComponent(tbMain, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 974, Short.MAX_VALUE)
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addComponent(pl_patient, GroupLayout.PREFERRED_SIZE, 669, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addComponent(pl_print, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        				.addComponent(pl_print, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        				.addComponent(pl_patient, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(tbMain, GroupLayout.PREFERRED_SIZE, 630, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(27, Short.MAX_VALUE))
        );
        pl_patient.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{btnPoisk, btnSave, btnShowTalonSelectModule, btnShowError, btnNew, btnDel}));

        JPanel tpPersonal = new JPanel();
        tpPersonal.setBorder(new EmptyBorder(0, 0, 0, 0));
        tbMain.addTab("Пациент", null, tpPersonal, null);

        JPanel panel_2 = new JPanel();
        panel_2.setBorder(new TitledBorder(null, "Общая информация :", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        JPanel panel_3 = new JPanel();
        panel_3.setBorder(new TitledBorder(null, "Адрес :", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        JPanel panel_4 = new JPanel();
        panel_4.setBorder(new TitledBorder(null, "Медицинский полис :", TitledBorder.LEADING, TitledBorder.TOP, null, null));
//        panel_4.remove(tf_dms_ser);
//        panel_4.remove(tf_dms_nom);
//        panel_4.remove(cmb_dms_smo);
        
        JPanel panel_5 = new JPanel();
        panel_5.setBorder(new TitledBorder(null, "Место работы :", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        JPanel panel_6 = new JPanel();
        panel_6.setBorder(new TitledBorder(null, "Прикрепление :", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        JPanel panel_7 = new JPanel();
        panel_7.setBorder(new TitledBorder(null, "Документ, удостоверяющий личность :", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        
        JPanel panel_37 = new JPanel();
        panel_37.setBorder(new TitledBorder(null, "Для инообластных граждан :", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GroupLayout gl_tpPersonal = new GroupLayout(tpPersonal);
        gl_tpPersonal.setHorizontalGroup(
        	gl_tpPersonal.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_tpPersonal.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_tpPersonal.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(panel_37, GroupLayout.PREFERRED_SIZE, 973, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_tpPersonal.createParallelGroup(Alignment.TRAILING, false)
        					.addComponent(panel_7, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
        					.addComponent(panel_6, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(panel_2, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
        					.addComponent(panel_3, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
        					.addComponent(panel_4, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
        					.addComponent(panel_5, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 973, Short.MAX_VALUE)))
        			.addContainerGap())
        );
        gl_tpPersonal.setVerticalGroup(
        	gl_tpPersonal.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_tpPersonal.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel_37, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        JLabel lblNewLabel_70 = new JLabel("СМО ФФ ОМС");
        lblNewLabel_70.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        GroupLayout gl_panel_37 = new GroupLayout(panel_37);
        gl_panel_37.setHorizontalGroup(
        	gl_panel_37.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_panel_37.createSequentialGroup()
        			.addGap(34)
        			.addComponent(lblNewLabel_70)
        			.addGap(20)
        			.addComponent(cmb_ogrn, GroupLayout.DEFAULT_SIZE, 802, Short.MAX_VALUE)
        			.addGap(58))
        );
        gl_panel_37.setVerticalGroup(
        	gl_panel_37.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_37.createSequentialGroup()
        			.addGroup(gl_panel_37.createParallelGroup(Alignment.BASELINE)
        				.addComponent(cmb_ogrn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_70))
        			.addGap(29))
        );
        panel_37.setLayout(gl_panel_37);

        JLabel lblNewLabel_27 = new JLabel("Документ");
        lblNewLabel_27.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_28 = new JLabel("Серия");
        lblNewLabel_28.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_29 = new JLabel("Номер");
        lblNewLabel_29.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_30 = new JLabel("Дата");
        lblNewLabel_30.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_31 = new JLabel("Кем выдан");
        lblNewLabel_31.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_32 = new JLabel("СНИЛС");
        lblNewLabel_32.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_69 = new JLabel("Место рождения");
        lblNewLabel_69.setFont(new Font("Tahoma", Font.PLAIN, 12));

        tf_serdoc = new CustomTextField();
        tf_serdoc.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_serdoc.setColumns(10);

        tf_nomdoc = new CustomTextField();
        tf_nomdoc.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_nomdoc.setColumns(10);

        tf_Odoc = new CustomTextField();
        tf_Odoc.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_Odoc.setColumns(10);

        tf_datadoc = new CustomDateEditor();
        tf_datadoc.setFont(new Font("Tahoma", Font.PLAIN, 12));

        tf_birthplace = new CustomTextField();
        tf_birthplace.setFont(new Font("Tahoma", Font.PLAIN, 11));
        tf_birthplace.setColumns(10);
        
        try {
			MaskFormatter mf = new MaskFormatter("###-###-### ##");
	        tf_Snils = new JFormattedTextField(mf);
	        tf_Snils.setFont(new Font("Tahoma", Font.PLAIN, 12));
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        tf_Snils.setColumns(10);
        
        GroupLayout gl_panel_7 = new GroupLayout(panel_7);
        gl_panel_7.setHorizontalGroup(
        	gl_panel_7.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_7.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel_7.createParallelGroup(Alignment.TRAILING)
        				.addGroup(gl_panel_7.createSequentialGroup()
        					.addComponent(lblNewLabel_31)
        					.addPreferredGap(ComponentPlacement.RELATED))
        				.addGroup(gl_panel_7.createSequentialGroup()
        					.addComponent(tf_serdoc, GroupLayout.PREFERRED_SIZE, 73, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED))
        				.addGroup(gl_panel_7.createSequentialGroup()
        					.addComponent(lblNewLabel_28)
        					.addGap(26)))
        			.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING, false)
        				.addGroup(gl_panel_7.createSequentialGroup()
        					.addComponent(tf_Odoc, 295, 295, 295)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(lblNewLabel_30)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(tf_datadoc, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel_7.createSequentialGroup()
        					.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
        						.addComponent(tf_nomdoc, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
        						.addComponent(lblNewLabel_29))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
        						.addComponent(cmb_tdoc, GroupLayout.PREFERRED_SIZE, 415, GroupLayout.PREFERRED_SIZE)
        						.addComponent(lblNewLabel_27))))
        			.addGap(10)
        			.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel_7.createSequentialGroup()
        					.addComponent(lblNewLabel_32)
        					.addGap(18)
        					.addComponent(tf_Snils, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addComponent(lblNewLabel_69)
        				.addComponent(tf_birthplace, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE))
        			.addContainerGap())
        );
        gl_panel_7.setVerticalGroup(
        	gl_panel_7.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_7.createSequentialGroup()
        			.addGroup(gl_panel_7.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_28)
        				.addComponent(lblNewLabel_27)
        				.addComponent(lblNewLabel_29)
        				.addComponent(lblNewLabel_69))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel_7.createParallelGroup(Alignment.BASELINE)
        					.addComponent(cmb_tdoc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(tf_nomdoc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(tf_birthplace, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addComponent(tf_serdoc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addGroup(gl_panel_7.createParallelGroup(Alignment.TRAILING)
        				.addGroup(gl_panel_7.createParallelGroup(Alignment.BASELINE)
        					.addComponent(tf_Odoc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(lblNewLabel_30)
        					.addComponent(tf_Snils, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(tf_datadoc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(lblNewLabel_32))
        				.addComponent(lblNewLabel_31))
        			.addContainerGap())
        );
        panel_7.setLayout(gl_panel_7);
        panel_7.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tf_serdoc, tf_nomdoc, cmb_tdoc, tf_Odoc, tf_datadoc, tf_Snils}));

        tf_Cpol = new CustomNumberEditor();
        tf_Cpol.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_Cpol.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                if (arg0.getClickCount() == 2) {
                	int[] res = null;
                	if (tf_Cpol.getNumber() != null )
                		if (PersonalInfo.getTerp() == 10) res = MainForm.conMan.showPolpTreeForm("Классификатор подразделений ЛПУ", PersonalInfo.getTerp(), Integer.valueOf(Integer.toString(PersonalInfo.getCpol_pr()).substring(0,2)), PersonalInfo.getCpol_pr());
                		else res = MainForm.conMan.showPolpTreeForm("Классификатор подразделений ЛПУ", 0, 0, 0);
                	else
                		res = MainForm.conMan.showPolpTreeForm("Классификатор подразделений ЛПУ", 0, 0, 0);
                    if (res != null) {
                    	tf_Cpol.setText(Integer.toString(res[2]));
                    	Terp = res[0];
                    }
                }
            }
        });
        tf_Cpol.setColumns(10);

        JLabel lblNewLabel_21 = new JLabel("Поликлиника");
        lblNewLabel_21.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_22 = new JLabel("Дата прикрепления");
        lblNewLabel_22.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_23 = new JLabel("№ участка");
        lblNewLabel_23.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_24 = new JLabel("№ амб. карты");
        lblNewLabel_24.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_25 = new JLabel("Дата открепления");
        lblNewLabel_25.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_26 = new JLabel("Причина");
        lblNewLabel_26.setFont(new Font("Tahoma", Font.PLAIN, 12));

        tf_Nuch = new CustomTextField();
        tf_Nuch.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_Nuch.setText("");
        tf_Nuch.setColumns(10);

        tf_Nambk = new CustomTextField();
        tf_Nambk.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_Nambk.setColumns(10);

        tf_datapr = new CustomDateEditor();
        tf_datapr.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_dataot = new CustomDateEditor();
        tf_dataot.setFont(new Font("Tahoma", Font.PLAIN, 12));

        GroupLayout gl_panel_6 = new GroupLayout(panel_6);
        gl_panel_6.setHorizontalGroup(
        	gl_panel_6.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_6.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING, false)
        				.addGroup(gl_panel_6.createSequentialGroup()
        					.addComponent(lblNewLabel_21)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(tf_Cpol, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(lblNewLabel_23))
        				.addGroup(gl_panel_6.createSequentialGroup()
        					.addComponent(lblNewLabel_25)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(tf_dataot, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(tf_Nuch, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel_6.createSequentialGroup()
        					.addComponent(lblNewLabel_24)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(tf_Nambk, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(lblNewLabel_22)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(tf_datapr, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel_6.createSequentialGroup()
        					.addComponent(lblNewLabel_26)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(cmb_ishod, GroupLayout.PREFERRED_SIZE, 387, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap(248, GroupLayout.PREFERRED_SIZE))
        );
        gl_panel_6.setVerticalGroup(
        	gl_panel_6.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_6.createSequentialGroup()
        			.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_21)
        				.addComponent(tf_Cpol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_23)
        				.addComponent(tf_Nuch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_24)
        				.addComponent(tf_Nambk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_22)
        				.addComponent(tf_datapr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_25)
        				.addComponent(tf_dataot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_26)
        				.addComponent(cmb_ishod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_6.setLayout(gl_panel_6);
        panel_6.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tf_Cpol, tf_Nuch, tf_Nambk, tf_datapr, tf_dataot, cmb_ishod}));
        JLabel lblNewLabel_19 = new JLabel("Должность");
        lblNewLabel_19.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_20 = new JLabel("Телефон");
        lblNewLabel_20.setFont(new Font("Tahoma", Font.PLAIN, 12));

        tfMrname = new CustomTextField();
        tfMrname.setFont(new Font("Tahoma", Font.PLAIN, 11));
        tfMrname.setColumns(10);

        tfMr = new CustomNumberEditor();
        tfMr.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tfMr.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                if (arg0.getClickCount() == 2) {
                	IntegerClassifier res = null;
                	if(tfMr.getNumber() != null){
                    	res = MainForm.conMan.showMrabTreeForm("место работы", Integer.valueOf(tfMr.getText()));
                	} else {
                		res = MainForm.conMan.showMrabTreeForm("место работы", 0);
					} 
					if (res != null) {
                        tfMr.setText(Integer.toString(res.pcod));
                        tfMrname.setText(res.name);
                      }
                }
            }
        });
        tfMr.setColumns(10);

        tfDolj = new CustomTextField();
        tfDolj.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tfDolj.setColumns(10);

        tfTel = new CustomTextField();
        tfTel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tfTel.setColumns(10);
        
        JLabel label = new JLabel("Образование");
        label.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        GroupLayout gl_panel_5 = new GroupLayout(panel_5);
        gl_panel_5.setHorizontalGroup(
        	gl_panel_5.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_5.createSequentialGroup()
        			.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel_5.createSequentialGroup()
        					.addGap(15)
        					.addComponent(lblNewLabel_19))
        				.addGroup(gl_panel_5.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(tfMr, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)))
        			.addGap(4)
        			.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING, false)
        				.addGroup(gl_panel_5.createSequentialGroup()
        					.addComponent(tfDolj, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addComponent(label)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(cmb_obr, GroupLayout.PREFERRED_SIZE, 285, GroupLayout.PREFERRED_SIZE))
        				.addComponent(tfMrname, GroupLayout.PREFERRED_SIZE, 586, GroupLayout.PREFERRED_SIZE))
        			.addGap(29)
        			.addComponent(lblNewLabel_20)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(tfTel, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
        			.addGap(20))
        );
        gl_panel_5.setVerticalGroup(
        	gl_panel_5.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_5.createSequentialGroup()
        			.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
        				.addComponent(tfMr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tfMrname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(8)
        			.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
        				.addComponent(tfDolj, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_19)
        				.addComponent(label)
        				.addComponent(cmb_obr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_20)
        				.addComponent(tfTel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(0, 0, Short.MAX_VALUE))
        );
        panel_5.setLayout(gl_panel_5);
        panel_5.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tfMr, tfMrname, tfDolj, cmb_obr, tfTel}));

        JLabel lblNewLabel_12 = new JLabel("Серия");
        lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_13 = new JLabel("ОМС");
        lblNewLabel_13.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_14 = new JLabel("ДМС");
//        lblNewLabel_14.setVisible(false);
        lblNewLabel_14.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_15 = new JLabel("Номер");
        lblNewLabel_15.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_16 = new JLabel("СМО");
        lblNewLabel_16.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_17 = new JLabel("Документ");
        lblNewLabel_17.setFont(new Font("Tahoma", Font.PLAIN, 12));

        tf_oms_ser = new CustomTextField();
        tf_oms_ser.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_oms_ser.setColumns(10);

        tf_dms_ser = new CustomTextField();
//        tf_dms_ser.setVisible(false);
        tf_dms_ser.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_dms_ser.setColumns(10);

        tf_oms_nom = new CustomTextField();
        tf_oms_nom.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_oms_nom.setColumns(10);

        tf_dms_nom = new CustomTextField();
//        tf_dms_nom.setVisible(false);
        tf_dms_nom.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_dms_nom.setColumns(10);

        GroupLayout gl_panel_4 = new GroupLayout(panel_4);
        gl_panel_4.setHorizontalGroup(
        	gl_panel_4.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_4.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING, false)
        				.addGroup(gl_panel_4.createSequentialGroup()
        					.addGap(33)
        					.addComponent(lblNewLabel_12, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
        					.addGap(46)
        					.addComponent(lblNewLabel_15, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
        					.addGap(47)
        					.addComponent(lblNewLabel_17)
        					.addGap(138)
        					.addComponent(lblNewLabel_16))
        				.addGroup(gl_panel_4.createSequentialGroup()
        					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING, false)
        						.addGroup(gl_panel_4.createSequentialGroup()
        							.addComponent(lblNewLabel_14)
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addComponent(tf_dms_ser, 0, 0, Short.MAX_VALUE))
        						.addGroup(gl_panel_4.createSequentialGroup()
        							.addComponent(lblNewLabel_13)
        							.addGap(6)
        							.addComponent(tf_oms_ser, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)))
        					.addGap(2)
        					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING, false)
        						.addGroup(gl_panel_4.createSequentialGroup()
        							.addComponent(tf_oms_nom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(cmb_oms_doc, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE))
        						.addGroup(gl_panel_4.createSequentialGroup()
        							.addComponent(tf_dms_nom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(cmb_dms_smo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(cmb_oms_smo, GroupLayout.PREFERRED_SIZE, 495, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap(82, Short.MAX_VALUE))
        );
        gl_panel_4.setVerticalGroup(
        	gl_panel_4.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_4.createSequentialGroup()
        			.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING, false)
        				.addGroup(gl_panel_4.createSequentialGroup()
        					.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
        						.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
        							.addComponent(lblNewLabel_12)
        							.addComponent(lblNewLabel_15)
        							.addComponent(lblNewLabel_17))
        						.addComponent(lblNewLabel_16))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
        						.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
        							.addComponent(tf_oms_ser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addComponent(tf_oms_nom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addComponent(cmb_oms_doc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        						.addComponent(cmb_oms_smo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        				.addComponent(lblNewLabel_13))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_14)
        				.addComponent(tf_dms_ser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tf_dms_nom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(cmb_dms_smo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_4.setLayout(gl_panel_4);
        panel_4.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tf_oms_ser, tf_oms_nom, cmb_oms_doc, cmb_oms_smo, tf_dms_ser, tf_dms_nom, cmb_dms_smo}));

        JLabel lblNewLabel_5 = new JLabel("Прописка");
        lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_6 = new JLabel("Проживает");
        lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_7 = new JLabel("Область(край, республика)");
        lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_8 = new JLabel("Город (село)");
        lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_9 = new JLabel("Улица");
        lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_10 = new JLabel("Дом");
        lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_11 = new JLabel("Кв.");
        lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 12));

        tf_Adp_kv = new CustomTextField();
        tf_Adp_kv.setFont(new Font("Tahoma", Font.PLAIN, 11));
        tf_Adp_kv.setColumns(10);

        tf_Adm_kv = new CustomTextField();
        tf_Adm_kv.setFont(new Font("Tahoma", Font.PLAIN, 11));
        tf_Adm_kv.setColumns(10);

        GroupLayout gl_panel_3 = new GroupLayout(panel_3);
        gl_panel_3.setHorizontalGroup(
        	gl_panel_3.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_3.createSequentialGroup()
        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING, false)
        				.addGroup(gl_panel_3.createSequentialGroup()
        					.addGap(72)
        					.addComponent(lblNewLabel_7))
        				.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING, false)
        					.addComponent(lblNewLabel_6)
        					.addGroup(gl_panel_3.createSequentialGroup()
        						.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
        						.addPreferredGap(ComponentPlacement.UNRELATED)
        						.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING, false)
        							.addComponent(cmb_adp_obl, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
        							.addComponent(cmb_adm_obl, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE)))))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(lblNewLabel_8)
        				.addGroup(gl_panel_3.createSequentialGroup()
        					.addGap(2)
        					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(cmb_adm_gorod, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cmb_adp_gorod, GroupLayout.PREFERRED_SIZE, 206, GroupLayout.PREFERRED_SIZE))))
        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING, false)
        				.addGroup(gl_panel_3.createSequentialGroup()
        					.addGap(29)
        					.addComponent(lblNewLabel_9, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel_3.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
        						.addComponent(cmb_adm_ul, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE)
        						.addComponent(cmb_adp_ul, GroupLayout.PREFERRED_SIZE, 227, GroupLayout.PREFERRED_SIZE))))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING, false)
        					.addComponent(cmb_adm_dom, Alignment.TRAILING, 0, 0, Short.MAX_VALUE)
        					.addComponent(cmb_adp_dom, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
        				.addComponent(lblNewLabel_10, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblNewLabel_11)
        				.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
        					.addComponent(tf_Adm_kv, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
        					.addComponent(tf_Adp_kv, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap(82, Short.MAX_VALUE))
        );
        gl_panel_3.setVerticalGroup(
        	gl_panel_3.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_3.createSequentialGroup()
        			.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
        				.addComponent(lblNewLabel_8)
        				.addComponent(lblNewLabel_7)
        				.addComponent(lblNewLabel_9)
        				.addComponent(lblNewLabel_11)
        				.addComponent(lblNewLabel_10))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel_3.createSequentialGroup()
        					.addComponent(lblNewLabel_5)
        					.addGap(18)
        					.addComponent(lblNewLabel_6))
        				.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
        					.addGroup(gl_panel_3.createSequentialGroup()
        						.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
        							.addComponent(cmb_adp_gorod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addComponent(tf_Adp_kv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addComponent(cmb_adp_ul, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addComponent(cmb_adp_dom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        						.addPreferredGap(ComponentPlacement.RELATED)
        						.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
        							.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
        								.addComponent(cmb_adm_gorod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        								.addComponent(cmb_adm_ul, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        							.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
        								.addComponent(tf_Adm_kv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        								.addComponent(cmb_adm_dom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
        					.addGroup(gl_panel_3.createSequentialGroup()
        						.addComponent(cmb_adp_obl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addPreferredGap(ComponentPlacement.RELATED)
        						.addComponent(cmb_adm_obl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_3.setLayout(gl_panel_3);
        panel_3.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{cmb_adp_obl, cmb_adp_gorod, cmb_adp_ul, cmb_adp_dom, tf_Adp_kv, cmb_adm_obl, cmb_adm_gorod, cmb_adm_ul, cmb_adm_dom, tf_Adm_kv}));

        tfFam = new CustomTextField();
        tfFam.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tfFam.setColumns(10);

        tfIm = new CustomTextField();
        tfIm.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tfIm.setColumns(10);

        tfOt = new CustomTextField();
        tfOt.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tfOt.setColumns(10);

        tfDr = new CustomDateEditor();
        tfDr.setFont(new Font("Tahoma", Font.PLAIN, 12));

        rbtn_pol_m = new JRadioButton("мужской");
        rbtn_pol_m.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnGroup_pol.add(rbtn_pol_m);

        rbtn_pol_j = new JRadioButton("женский");
        rbtn_pol_j.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnGroup_pol.add(rbtn_pol_j);

        JLabel lblNewLabel = new JLabel("Дата рождения");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_1 = new JLabel("Фамилия");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_2 = new JLabel("Имя");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_3 = new JLabel("Отчество");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_4 = new JLabel("Социальный статус");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblPol = new JLabel("Пол");
        lblPol.setFont(new Font("Tahoma", Font.PLAIN, 12));

        GroupLayout gl_panel_2 = new GroupLayout(panel_2);
        gl_panel_2.setHorizontalGroup(
        	gl_panel_2.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_2.createSequentialGroup()
        			.addGap(22)
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
        				.addComponent(lblNewLabel)
        				.addComponent(lblNewLabel_1))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(tfDr, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(tfFam, GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
        			.addGap(18)
        			.addComponent(lblNewLabel_2)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel_2.createSequentialGroup()
        					.addComponent(tfIm, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addComponent(lblNewLabel_3)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(tfOt, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(lblPol)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(rbtn_pol_m)
        					.addGap(18)
        					.addComponent(rbtn_pol_j))
        				.addGroup(gl_panel_2.createSequentialGroup()
        					.addComponent(lblNewLabel_4, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(cmb_status, GroupLayout.PREFERRED_SIZE, 287, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap(166, Short.MAX_VALUE))
        );
        gl_panel_2.setVerticalGroup(
        	gl_panel_2.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_2.createSequentialGroup()
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
        				.addComponent(tfFam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_1)
        				.addComponent(lblNewLabel_2)
        				.addComponent(tfIm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_3)
        				.addComponent(tfOt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblPol)
        				.addComponent(rbtn_pol_m)
        				.addComponent(rbtn_pol_j))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel)
        				.addComponent(tfDr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_4)
        				.addComponent(cmb_status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(49, Short.MAX_VALUE))
        );
        panel_2.setLayout(gl_panel_2);
        panel_2.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tfFam, tfIm, tfOt, tfDr, rbtn_pol_m, rbtn_pol_j, cmb_status}));
        tpPersonal.setLayout(gl_tpPersonal);

        JPanel tpLgota = new JPanel();
        tbMain.addTab("Льгота", null, tpLgota, null);

        JPanel panel_9 = new JPanel();

        JPanel panel_10 = new JPanel();
        
        JPanel panel_1 = new JPanel();
        panel_1.setBorder(new TitledBorder(null, "Инвалидность", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GroupLayout gl_tpLgota = new GroupLayout(tpLgota);
        gl_tpLgota.setHorizontalGroup(
        	gl_tpLgota.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_tpLgota.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_tpLgota.createParallelGroup(Alignment.LEADING)
        				.addComponent(panel_10, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(panel_9, GroupLayout.DEFAULT_SIZE, 949, Short.MAX_VALUE)
        				.addGroup(gl_tpLgota.createSequentialGroup()
        					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 656, Short.MAX_VALUE)
        					.addGap(293)))
        			.addContainerGap())
        );
        gl_tpLgota.setVerticalGroup(
        	gl_tpLgota.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_tpLgota.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(panel_9, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(panel_10, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
        			.addGap(18)
        			.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(239, Short.MAX_VALUE))
        );
        
        JLabel lblNewLabel_18 = new JLabel("Группа");
        lblNewLabel_18.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_63 = new JLabel("Дата установления");
        lblNewLabel_63.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_64 = new JLabel("№ справки");
        lblNewLabel_64.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_65 = new JLabel("Срок");
        lblNewLabel_65.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_66 = new JLabel("Установлена");
        lblNewLabel_66.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_67 = new JLabel("Дата отмены");
        lblNewLabel_67.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_68 = new JLabel("Обстоятельства");
        lblNewLabel_68.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        tfgr = new CustomNumberEditor();
        tfgr.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tfspr = new CustomTextField();
        tfspr.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tfdust = new CustomDateEditor();
        tfdust.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tfdotm = new CustomDateEditor();
        tfdotm.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        rbtn_vperv = new JRadioButton("впервые");
        rbtn_vperv.setFont(new Font("Tahoma", Font.PLAIN, 12));
        rbtn_povt = new JRadioButton("повторно");
        rbtn_povt.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnGroup_pp.add(rbtn_vperv);
        btnGroup_pp.add(rbtn_povt);
        
        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
        	gl_panel_1.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_1.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel_1.createSequentialGroup()
        					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
        						.addGroup(gl_panel_1.createSequentialGroup()
        							.addComponent(lblNewLabel_18)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(tfgr, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addComponent(lblNewLabel_63))
        						.addGroup(gl_panel_1.createSequentialGroup()
        							.addComponent(lblNewLabel_65)
        							.addGap(18)
        							.addComponent(cmb_srok, 0, 0, Short.MAX_VALUE)))
        					.addPreferredGap(ComponentPlacement.UNRELATED))
        				.addGroup(gl_panel_1.createSequentialGroup()
        					.addComponent(lblNewLabel_67)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(tfdotm, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
        					.addGap(34)))
        			.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(tfdust, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(lblNewLabel_66)
        				.addComponent(lblNewLabel_68, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
        				.addComponent(cmb_obst, 0, 263, Short.MAX_VALUE)
        				.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
        					.addGroup(gl_panel_1.createSequentialGroup()
        						.addComponent(lblNewLabel_64)
        						.addPreferredGap(ComponentPlacement.RELATED)
        						.addComponent(tfspr, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE))
        					.addGroup(gl_panel_1.createSequentialGroup()
        						.addComponent(rbtn_vperv)
        						.addPreferredGap(ComponentPlacement.UNRELATED)
        						.addComponent(rbtn_povt))))
        			.addGap(72))
        );
        gl_panel_1.setVerticalGroup(
        	gl_panel_1.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_1.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_18)
        				.addComponent(tfgr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_63)
        				.addComponent(tfdust, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_64)
        				.addComponent(tfspr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(18)
        			.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_65)
        				.addComponent(cmb_srok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_66)
        				.addComponent(rbtn_vperv)
        				.addComponent(rbtn_povt))
        			.addPreferredGap(ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
        			.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
        				.addComponent(lblNewLabel_67)
        				.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
        					.addComponent(tfdotm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(lblNewLabel_68)
        					.addComponent(cmb_obst, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        );
        panel_1.setLayout(gl_panel_1);


        JScrollPane scrollPane_1 = new JScrollPane();
        GroupLayout gl_panel_10 = new GroupLayout(panel_10);
        gl_panel_10.setHorizontalGroup(
        	gl_panel_10.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_10.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 711, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(228, Short.MAX_VALUE))
        );
        gl_panel_10.setVerticalGroup(
        	gl_panel_10.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_10.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
        			.addContainerGap())
        );

        tbl_lgota =new CustomTable<>(true, true, AllLgota.class, 3,"Дата",2,"Льгота");
//        tbl_lgota.addMouseListener(new MouseAdapter() {
//        	@Override
//        	public void mouseClicked(MouseEvent arg0) {
//                if (arg0.getClickCount() == 2) {
//                	IntegerClassifier res = null;
//                	res = MainForm.conMan.showIntegerClassifierSelector();
//                }
//        	}
//        });
        tbl_lgota.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tbl_lgota.setDateField(0);
        tbl_lgota.setFillsViewportHeight(true);
        tbl_lgota.setPreferredWidths(75,600);
        tbl_lgota.setColumnSelectionAllowed(true);
        tbl_lgota.setRowSelectionAllowed(true);
        //tbl_lgota.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        scrollPane_1.setViewportView(tbl_lgota);
        panel_10.setLayout(gl_panel_10);

		tbl_lgota.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
			if (!e.getValueIsAdjusting()) {
				if (tbl_lgota.getSelectedItem() !=  null)
					InfoForLgotaPatient();  
				}
			}
		});

        //удалить
        tbl_lgota.registerDeleteSelectedRowListener(new CustomTableItemChangeEventListener<AllLgota>() {

            @Override
            public boolean doAction(CustomTableItemChangeEvent<AllLgota> event) {
                try {
                    if (tbl_lgota.getSelectedItem().getId() != 0)curId_lgt = tbl_lgota.getSelectedItem().id;
                    MainForm.tcl.deleteLgota(curId_lgt);
                    NewLgotaInfo();
                } catch (KmiacServerException e) {
                    e.printStackTrace();
                    return false;
                } catch (TException e) {
                    e.printStackTrace();
                    MainForm.conMan.reconnect(e);
                    return false;
                }
                return true;
            }
        });
        //добавить
        tbl_lgota.registerAddRowListener(new CustomTableItemChangeEventListener<AllLgota>() {

            @Override
            public boolean doAction(CustomTableItemChangeEvent<AllLgota> event) {
                try {
                    AllLgota item = event.getItem();
                    item.setNpasp(curPatientId);
                    item.setLgota(tbl_lgota.getSelectedItem().lgota);
                    item.setDatau(tbl_lgota.getSelectedItem().datau);
					if (tfgr.getText().length() > 0) item.setGri(Integer.valueOf(tfgr.getText())); 
                    if (tfspr.getText().length() > 0) item.setNdoc(tfspr.getText());
                    if (rbtn_vperv.isSelected())item.setPp(1);
                    if (rbtn_povt.isSelected()) item.setPp(2);
                    if (tfdust.getDate() != null) item.setDrg(tfdust.getDate().getTime());
                    if (tfdotm.getDate() != null) item.setDot(tfdotm.getDate().getTime());
                    if (cmb_obst.getSelectedItem() != null) item.setObo(cmb_obst.getSelectedPcod());
                    if (cmb_srok.getSelectedItem() != null) item.setSin(cmb_srok.getSelectedPcod());
                    Info pInfo = MainForm.tcl.addLgota(event.getItem());
                    tbl_lgota.getSelectedItem().setName(pInfo.getName());
                    curId_lgt = pInfo.getId();
                    changePatientLgotaInfo(curPatientId);
                } catch (LgotaAlreadyExistException laee) {
                    laee.printStackTrace();
                    return false;
                } catch (KmiacServerException e) {
                    e.printStackTrace();
                    return false;
                } catch (TException e) {
                    e.printStackTrace();
                    MainForm.conMan.reconnect(e);
                    return false;
                }
                return true;
            }
        });
        //изменить
        tbl_lgota.registerUpdateSelectedRowListener(new CustomTableItemChangeEventListener<AllLgota>() {
            @Override
            public boolean doAction(CustomTableItemChangeEvent<AllLgota> event) {
                try {
                    AllLgota item = event.getItem();
                    item.setNpasp(curPatientId);
                    if (tbl_lgota.getSelectedItem().getId() != 0)curId_lgt = tbl_lgota.getSelectedItem().id;
                    item.setId(curId_lgt);
                    item.setLgota(tbl_lgota.getSelectedItem().lgota);
                    item.setDatau(tbl_lgota.getSelectedItem().datau);
					if (tfgr.getText().length() > 0) item.setGri(Integer.valueOf(tfgr.getText())); 
                    if (tfspr.getText().length() > 0) item.setNdoc(tfspr.getText());
                    if (rbtn_vperv.isSelected())item.setPp(1);
                    if (rbtn_povt.isSelected()) item.setPp(2);
                    if (tfdust.getDate() != null) item.setDrg(tfdust.getDate().getTime());
                    if (tfdotm.getDate() != null) item.setDot(tfdotm.getDate().getTime());
                    if (cmb_obst.getSelectedItem() != null) item.setObo(cmb_obst.getSelectedPcod());
                    if (cmb_srok.getSelectedItem() != null) item.setSin(cmb_srok.getSelectedPcod());
                	MainForm.tcl.updateLgota(event.getItem());
                } catch (KmiacServerException e) {
                    e.printStackTrace();
                    return false;
                } catch (TException e) {
                    e.printStackTrace();
                    MainForm.conMan.reconnect(e);
                    return false;
                }
                return true;
            }
        });

        JButton btnDel_lgt = new JButton("Удалить");
        btnDel_lgt.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnDel_lgt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try{
                    tbl_lgota.requestFocus();
                    tbl_lgota.deleteSelectedRow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        JButton btnAdd_lgt = new JButton("Добавить");
        btnAdd_lgt.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnAdd_lgt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try{
                    curId_lgt = 0;
                    NewLgotaInfo();
                	tbl_lgota.requestFocus();
                    tbl_lgota.addItem();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        JButton btnSave_lgt = new JButton("Сохранить");
        btnSave_lgt.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnSave_lgt.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try{
                	tbl_lgota.updateSelectedItem();
                	SaveForLgotaPatient();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        GroupLayout gl_panel_9 = new GroupLayout(panel_9);
        gl_panel_9.setHorizontalGroup(
        	gl_panel_9.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_9.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(btnDel_lgt)
        			.addGap(18)
        			.addComponent(btnAdd_lgt)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(btnSave_lgt)
        			.addContainerGap(664, Short.MAX_VALUE))
        );
        gl_panel_9.setVerticalGroup(
        	gl_panel_9.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_9.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_panel_9.createParallelGroup(Alignment.BASELINE)
        				.addComponent(btnDel_lgt)
        				.addComponent(btnAdd_lgt)
        				.addComponent(btnSave_lgt))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_9.setLayout(gl_panel_9);
        tpLgota.setLayout(gl_tpLgota);

        JPanel tpKateg = new JPanel();
        tbMain.addTab("Категория", null, tpKateg, null);

        JPanel panel_11 = new JPanel();

        JPanel panel_12 = new JPanel();
        GroupLayout gl_tpKateg = new GroupLayout(tpKateg);
        gl_tpKateg.setHorizontalGroup(
        	gl_tpKateg.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_tpKateg.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_tpKateg.createParallelGroup(Alignment.LEADING)
        				.addComponent(panel_12, GroupLayout.PREFERRED_SIZE, 733, GroupLayout.PREFERRED_SIZE)
        				.addComponent(panel_11, GroupLayout.PREFERRED_SIZE, 668, GroupLayout.PREFERRED_SIZE))
        			.addGap(226))
        );
        gl_tpKateg.setVerticalGroup(
        	gl_tpKateg.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_tpKateg.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(panel_11, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(panel_12, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(433, Short.MAX_VALUE))
        );

        JScrollPane scrollPane_2 = new JScrollPane();
        GroupLayout gl_panel_12 = new GroupLayout(panel_12);
        gl_panel_12.setHorizontalGroup(
            gl_panel_12.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_12.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 648, Short.MAX_VALUE)
                    .addContainerGap())
        );
        gl_panel_12.setVerticalGroup(
            gl_panel_12.createParallelGroup(Alignment.TRAILING)
                .addGroup(gl_panel_12.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                    .addContainerGap())
        );

        tbl_kateg =new CustomTable<>(true, true, Kontingent.class, 3,"Дата",2,"Категория");
        tbl_kateg.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tbl_kateg.setDateField(0);
        tbl_kateg.setPreferredWidths(75,600);
        tbl_kateg.setColumnSelectionAllowed(true);
        tbl_kateg.setRowSelectionAllowed(true);
        tbl_kateg.setFillsViewportHeight(true);
        scrollPane_2.setViewportView(tbl_kateg);
        panel_12.setLayout(gl_panel_12);

//		tbl_kateg.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//			@Override
//			public void valueChanged(ListSelectionEvent e) {
//				if (!e.getValueIsAdjusting()) {
//					curPatientId = tbl_patient.getSelectedItem().npasp;
//					changePatientKategInfo(curPatientId);
//				}
//			}
//		});
        //удалить
        tbl_kateg.registerDeleteSelectedRowListener(new CustomTableItemChangeEventListener<Kontingent>() {
            @Override
            public boolean doAction(CustomTableItemChangeEvent<Kontingent> event) {
                try {
                    curId = tbl_kateg.getSelectedItem().id;
                    MainForm.tcl.deleteKont(curId);
                } catch (TException e) {
                    e.printStackTrace();
                    MainForm.conMan.reconnect(e);
                    return false;
                }
                return true;
            }
        });
        //добавить
        tbl_kateg.registerAddRowListener(new CustomTableItemChangeEventListener<Kontingent>() {

            @Override
            public boolean doAction(CustomTableItemChangeEvent<Kontingent> event) {
                try {
                    Kontingent item = event.getItem();
                    item.setNpasp(curPatientId);
                    item.setKateg(tbl_kateg.getSelectedItem().kateg);
                    item.setDatau(tbl_kateg.getSelectedItem().datau);
//				    item.setName(tbl_kateg.getSelectedItem().name);
                    Info pInfo = MainForm.tcl.addKont(event.getItem());
                    tbl_kateg.getSelectedItem().setName(pInfo.getName());
                    curId = pInfo.getId();
                } catch (KontingentAlreadyExistException kaee) {
                    kaee.printStackTrace();
                    return false;
                } catch (KmiacServerException e) {
                    e.printStackTrace();
                    return false;
                } catch (TException e) {
                    e.printStackTrace();
                    MainForm.conMan.reconnect(e);
                    return false;
                }
                return true;
            }
        });
        //изменить
        tbl_kateg.registerUpdateSelectedRowListener(new CustomTableItemChangeEventListener<Kontingent>() {
            @Override
            public boolean doAction(CustomTableItemChangeEvent<Kontingent> event) {
                try {
                    MainForm.tcl.updateKont(event.getItem());
                } catch (TException e) {
                    e.printStackTrace();
                    MainForm.conMan.reconnect(e);
                    return false;
                }
                return true;
            }
        });

        JButton btnDel_kat = new JButton("Удалить");
        btnDel_kat.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnDel_kat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try{
                    tbl_kateg.requestFocus();
                    tbl_kateg.deleteSelectedRow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        JButton btnAdd_kat = new JButton("Добавить");
        btnAdd_kat.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnAdd_kat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try{
                    tbl_kateg.requestFocus();
                    tbl_kateg.addItem();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        JButton btnSave_kat = new JButton("Сохранить");
        btnSave_kat.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnSave_kat.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try{
                    tbl_kateg.updateSelectedItem();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        GroupLayout gl_panel_11 = new GroupLayout(panel_11);
        gl_panel_11.setHorizontalGroup(
            gl_panel_11.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_11.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btnDel_kat)
                    .addGap(18)
                    .addComponent(btnAdd_kat)
                    .addGap(18)
                    .addComponent(btnSave_kat)
                    .addContainerGap(375, Short.MAX_VALUE))
        );
        gl_panel_11.setVerticalGroup(
            gl_panel_11.createParallelGroup(Alignment.LEADING)
                .addGroup(Alignment.TRAILING, gl_panel_11.createSequentialGroup()
                    .addContainerGap(12, Short.MAX_VALUE)
                    .addGroup(gl_panel_11.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnDel_kat)
                        .addComponent(btnAdd_kat)
                        .addComponent(btnSave_kat))
                    .addContainerGap())
        );
        panel_11.setLayout(gl_panel_11);
        tpKateg.setLayout(gl_tpKateg);

        JPanel tpAgent = new JPanel();
        tbMain.addTab("Доп. сведения", null, tpAgent, null);

        JPanel panel_8 = new JPanel();
        panel_8.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0421\u0432\u0435\u0434\u0435\u043D\u0438\u044F \u043E \u043F\u0440\u0435\u0434\u0441\u0442\u0430\u0432\u0438\u0442\u0435\u043B\u0435 \u0440\u0435\u0431\u0435\u043D\u043A\u0430 :", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        JPanel panel_22 = new JPanel();

        JLabel lblNewLabel_39 = new JLabel("Дополнительные сведения для представителей детей, не имеющих полиса ОМС");
        lblNewLabel_39.setFont(new Font("Tahoma", Font.PLAIN, 12));
        GroupLayout gl_tpAgent = new GroupLayout(tpAgent);
        gl_tpAgent.setHorizontalGroup(
        	gl_tpAgent.createParallelGroup(Alignment.TRAILING)
        		.addGroup(Alignment.LEADING, gl_tpAgent.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_tpAgent.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_tpAgent.createSequentialGroup()
        					.addComponent(panel_8, GroupLayout.DEFAULT_SIZE, 969, Short.MAX_VALUE)
        					.addContainerGap())
        				.addGroup(gl_tpAgent.createSequentialGroup()
        					.addComponent(lblNewLabel_39)
        					.addContainerGap(511, Short.MAX_VALUE))
        				.addGroup(gl_tpAgent.createSequentialGroup()
        					.addComponent(panel_22, GroupLayout.DEFAULT_SIZE, 924, Short.MAX_VALUE)
        					.addGap(55))))
        );
        gl_tpAgent.setVerticalGroup(
        	gl_tpAgent.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_tpAgent.createSequentialGroup()
        			.addGap(12)
        			.addComponent(lblNewLabel_39)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(panel_8, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel_22, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(224, Short.MAX_VALUE))
        );

        JButton btnSave_agent = new JButton("Сохранить");
        btnSave_agent.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnSave_agent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try{
                    AgentInfo = new Agent();
                    AgentInfo.setNpasp(curPatientId);
                    AgentInfo.setFam(tf_Fam_pr.getText());
                    AgentInfo.setIm(tf_Im_pr.getText());
                    AgentInfo.setOt(tf_Ot_pr.getText());
                    if (tf_dr_pr.getDate() != null)  AgentInfo.setDatar(tf_dr_pr.getDate().getTime());
//                    AgentInfo.setBirthplace(tf_Mr_pr.getText());
                    AgentInfo.setSpolis(tf_Polis_ser_pr.getText());
                    AgentInfo.setNpolis(tf_Polis_nom_pr.getText());
//                    AgentInfo.setName_str(tf_Name_sk_pr.getText());
//                    AgentInfo.setName_str(tf_name_smo.getText());
                    if (rbtn_pol_pr_m.isSelected()) AgentInfo.setPol(1);
                    else if (rbtn_pol_pr_j.isSelected()) AgentInfo.setPol(2);
                    else AgentInfo.setPol(0);
                    if (cmb_Polis_doc_pr.getSelectedItem() != null) AgentInfo.setVpolis(cmb_Polis_doc_pr.getSelectedPcod());
                    if (cmb_tdoc_pr.getSelectedItem() != null) AgentInfo.setTdoc(cmb_tdoc_pr.getSelectedPcod());
                    AgentInfo.setDocser(tf_Ser_doc_pr.getText());
                    AgentInfo.setDocnum(tf_Nomdoc_pr.getText());
                    MainForm.tcl.addOrUpdateAgent(AgentInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        JButton btnDel_agent = new JButton("Удалить");
        btnDel_agent.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnDel_agent.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try{
                    MainForm.tcl.deleteAgent(curPatientId);
                    NewAgent();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        GroupLayout gl_panel_22 = new GroupLayout(panel_22);
        gl_panel_22.setHorizontalGroup(
            gl_panel_22.createParallelGroup(Alignment.TRAILING)
                .addGroup(gl_panel_22.createSequentialGroup()
                    .addGap(455)
                    .addComponent(btnSave_agent)
                    .addGap(34)
                    .addComponent(btnDel_agent)
                    .addContainerGap(24, Short.MAX_VALUE))
        );
        gl_panel_22.setVerticalGroup(
            gl_panel_22.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_22.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(gl_panel_22.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnDel_agent)
                        .addComponent(btnSave_agent))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_22.setLayout(gl_panel_22);

        JLabel lblNewLabel_33 = new JLabel("Фамилия");
        lblNewLabel_33.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_34 = new JLabel("Имя");
        lblNewLabel_34.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_35 = new JLabel("Отчество");
        lblNewLabel_35.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_36 = new JLabel("Дата рождения");
        lblNewLabel_36.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_38 = new JLabel("Пол");
        lblNewLabel_38.setFont(new Font("Tahoma", Font.PLAIN, 12));

        tf_Fam_pr = new CustomTextField();
        tf_Fam_pr.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_Fam_pr.setColumns(10);

        tf_Im_pr = new CustomTextField();
        tf_Im_pr.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_Im_pr.setColumns(10);

        tf_Ot_pr = new CustomTextField();
        tf_Ot_pr.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_Ot_pr.setColumns(10);

        rbtn_pol_pr_m = new JRadioButton("мужской");
        rbtn_pol_pr_m.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnGroup_pol_pr.add(rbtn_pol_pr_m);

        rbtn_pol_pr_j = new JRadioButton("женский");
        rbtn_pol_pr_j.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnGroup_pol_pr.add(rbtn_pol_pr_j);

        tf_dr_pr = new CustomDateEditor();
        tf_dr_pr.setFont(new Font("Tahoma", Font.PLAIN, 12));

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Медицинский полис ОМС:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        
        JPanel panel_21 = new JPanel();
        panel_21.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0414\u043E\u043A\u0443\u043C\u0435\u043D\u0442, \u0443\u0434\u043E\u0441\u0442\u043E\u0432\u0435\u0440\u044F\u044E\u0449\u0438\u0439 \u043B\u0438\u0447\u043D\u043E\u0441\u0442\u044C:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
                
        JLabel lblNewLabel_45 = new JLabel("Документ");
        lblNewLabel_45.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_46 = new JLabel("Серия");
        lblNewLabel_46.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_47 = new JLabel("Номер");
        lblNewLabel_47.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_Ser_doc_pr = new CustomTextField();
        tf_Ser_doc_pr.setColumns(10);
        tf_Nomdoc_pr = new CustomTextField();
        tf_Nomdoc_pr.setColumns(10);
                        
                                GroupLayout gl_panel_21 = new GroupLayout(panel_21);
                                gl_panel_21.setHorizontalGroup(
                                	gl_panel_21.createParallelGroup(Alignment.LEADING)
                                		.addGroup(gl_panel_21.createSequentialGroup()
                                			.addGroup(gl_panel_21.createParallelGroup(Alignment.LEADING)
                                				.addGroup(gl_panel_21.createSequentialGroup()
                                					.addComponent(tf_Ser_doc_pr, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
                                					.addPreferredGap(ComponentPlacement.UNRELATED)
                                					.addComponent(tf_Nomdoc_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                					.addPreferredGap(ComponentPlacement.UNRELATED)
                                					.addComponent(cmb_tdoc_pr, GroupLayout.PREFERRED_SIZE, 392, GroupLayout.PREFERRED_SIZE))
                                				.addGroup(gl_panel_21.createSequentialGroup()
                                					.addComponent(lblNewLabel_46)
                                					.addGap(40)
                                					.addComponent(lblNewLabel_47)
                                					.addGap(70)
                                					.addComponent(lblNewLabel_45)))
                                			.addContainerGap(102, Short.MAX_VALUE))
                                );
                                gl_panel_21.setVerticalGroup(
                                	gl_panel_21.createParallelGroup(Alignment.TRAILING)
                                		.addGroup(gl_panel_21.createSequentialGroup()
                                			.addGroup(gl_panel_21.createParallelGroup(Alignment.BASELINE)
                                				.addComponent(lblNewLabel_46)
                                				.addComponent(lblNewLabel_47)
                                				.addComponent(lblNewLabel_45))
                                			.addPreferredGap(ComponentPlacement.RELATED)
                                			.addGroup(gl_panel_21.createParallelGroup(Alignment.BASELINE)
                                				.addComponent(tf_Ser_doc_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                				.addComponent(tf_Nomdoc_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                				.addComponent(cmb_tdoc_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                );
                                panel_21.setLayout(gl_panel_21);
        GroupLayout gl_panel_8 = new GroupLayout(panel_8);
        gl_panel_8.setHorizontalGroup(
        	gl_panel_8.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_panel_8.createSequentialGroup()
        			.addGroup(gl_panel_8.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel_8.createSequentialGroup()
        					.addGap(47)
        					.addGroup(gl_panel_8.createParallelGroup(Alignment.TRAILING)
        						.addComponent(lblNewLabel_33)
        						.addComponent(lblNewLabel_34)
        						.addComponent(lblNewLabel_35)
        						.addComponent(lblNewLabel_36))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(gl_panel_8.createParallelGroup(Alignment.TRAILING, false)
        						.addComponent(tf_dr_pr)
        						.addComponent(tf_Ot_pr, Alignment.LEADING)
        						.addComponent(tf_Im_pr, Alignment.LEADING)
        						.addComponent(tf_Fam_pr, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE))
        					.addGap(68)
        					.addComponent(lblNewLabel_38)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(rbtn_pol_pr_m)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(rbtn_pol_pr_j))
        				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 739, Short.MAX_VALUE)
        				.addComponent(panel_21, GroupLayout.DEFAULT_SIZE, 943, Short.MAX_VALUE))
        			.addContainerGap())
        );
        gl_panel_8.setVerticalGroup(
        	gl_panel_8.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_8.createSequentialGroup()
        			.addGroup(gl_panel_8.createParallelGroup(Alignment.BASELINE)
        				.addComponent(tf_Fam_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_33))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_8.createParallelGroup(Alignment.BASELINE)
        				.addComponent(tf_Im_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_34))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_8.createParallelGroup(Alignment.BASELINE)
        				.addComponent(tf_Ot_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_35))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel_8.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_36)
        				.addComponent(lblNewLabel_38)
        				.addComponent(rbtn_pol_pr_m)
        				.addComponent(rbtn_pol_pr_j)
        				.addComponent(tf_dr_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(panel, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel_21, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

                JLabel lblNewLabel_40 = new JLabel("Серия");
                lblNewLabel_40.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_41 = new JLabel("Номер");
        lblNewLabel_41.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_42 = new JLabel("Документ");
        lblNewLabel_42.setFont(new Font("Tahoma", Font.PLAIN, 12));

        tf_Polis_ser_pr = new CustomTextField();
        tf_Polis_ser_pr.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_Polis_ser_pr.setColumns(10);

        tf_Polis_nom_pr = new CustomTextField();
        tf_Polis_nom_pr.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_Polis_nom_pr.setColumns(10);
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
        				.addGroup(gl_panel.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(lblNewLabel_40)
        					.addGap(45)
        					.addComponent(lblNewLabel_41)
        					.addGap(56)
        					.addComponent(lblNewLabel_42))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(tf_Polis_ser_pr, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE)
        					.addGap(10)
        					.addComponent(tf_Polis_nom_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
        					.addComponent(cmb_Polis_doc_pr, GroupLayout.PREFERRED_SIZE, 373, GroupLayout.PREFERRED_SIZE)))
        			.addGap(145))
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
        					.addComponent(lblNewLabel_41)
        					.addComponent(lblNewLabel_40))
        				.addComponent(lblNewLabel_42))
        			.addGap(6)
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addComponent(tf_Polis_ser_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(tf_Polis_nom_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(cmb_Polis_doc_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel.setLayout(gl_panel);
        panel_8.setLayout(gl_panel_8);
        tpAgent.setLayout(gl_tpAgent);

        tbMain.addTab("Анамнез", null, tpSign, null);

        JPanel tpPriem = new JPanel();
        tbMain.addTab("Приемное отделение", null, tpPriem, null);

        JPanel panel_23 = new JPanel();
        panel_23.setBorder(new TitledBorder(null, "Обращения", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        JPanel panel_25 = new JPanel();
        panel_25.setBorder(new TitledBorder(null, "Обращение в приемное отделение", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        JPanel panel_34 = new JPanel();
        panel_34.setBorder(new TitledBorder(null, "Госпитализация", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        
        JPanel panel_20 = new JPanel();
        GroupLayout gl_tpPriem = new GroupLayout(tpPriem);
        gl_tpPriem.setHorizontalGroup(
        	gl_tpPriem.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_tpPriem.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_tpPriem.createParallelGroup(Alignment.LEADING)
        				.addComponent(panel_25, GroupLayout.PREFERRED_SIZE, 969, Short.MAX_VALUE)
        				.addGroup(gl_tpPriem.createSequentialGroup()
        					.addComponent(panel_23, GroupLayout.PREFERRED_SIZE, 780, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(panel_20, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
        					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        				.addComponent(panel_34, GroupLayout.DEFAULT_SIZE, 979, Short.MAX_VALUE)))
        );
        gl_tpPriem.setVerticalGroup(
        	gl_tpPriem.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_tpPriem.createSequentialGroup()
        			.addGap(6)
        			.addGroup(gl_tpPriem.createParallelGroup(Alignment.LEADING)
        				.addComponent(panel_23, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
        				.addComponent(panel_20, GroupLayout.PREFERRED_SIZE, 119, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel_25, GroupLayout.PREFERRED_SIZE, 317, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel_34, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(36, Short.MAX_VALUE))
        );
        
                JButton btnNew_priem = new JButton("Новое обращение");
                btnNew_priem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        if (curPatientId != 0){
                            curId = 0;
                            curNgosp = 0;
                            curId_otd = 0;
                            newPriem = tbl_priem.addExternalItem();
                            rbtn_plan.requestFocus();
                            NewPriemInfo();
                        }
                    }
                });
        
                JButton btnSave_priem = new JButton("Сохранить");
                btnSave_priem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                        if (curPatientId != 0)
                            if (tbl_priem.getSelectedItem() !=  null)
                            	SavePriemInfo();
                            else 
                            	JOptionPane.showMessageDialog(tbl_priem, "Нажмите кнопку <Новое обращение>.");
                    }
                });
        
        JButton btnOsm = new JButton("Первичный осмотр");
        btnOsm.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
//        		pervosm.onConnect();
        	}
        });
//      btnAnam.addActionListener(new ActionListener() {
//  	  	public void actionPerformed(ActionEvent e) {
//     		azfrm.showAnamnezForm();
//	    	}
//      });
        
        JButton btnDel_priem = new JButton("Удалить");
                btnDel_priem.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent arg0) {
                      try{
                          if (curPatientId != 0 && tbl_priem.getSelectedItem().id != 0){
                              curId = tbl_priem.getSelectedItem().id;
                              tbl_priem.requestFocus();
                              tbl_priem.deleteSelectedRow();
                          }else
                            JOptionPane.showMessageDialog(tbl_priem, "Отсутствуют обращения пациента.");
                      } catch (Exception e) {
                        e.printStackTrace();
                      }
                    }
                });
        GroupLayout gl_panel_20 = new GroupLayout(panel_20);
        gl_panel_20.setHorizontalGroup(
        	gl_panel_20.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_20.createSequentialGroup()
        			.addGroup(gl_panel_20.createParallelGroup(Alignment.TRAILING, false)
        				.addComponent(btnNew_priem, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(btnSave_priem, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(btnOsm, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        			.addContainerGap(40, Short.MAX_VALUE))
        		.addGroup(Alignment.TRAILING, gl_panel_20.createSequentialGroup()
        			.addGap(77)
        			.addComponent(btnDel_priem, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gl_panel_20.setVerticalGroup(
        	gl_panel_20.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_20.createSequentialGroup()
        			.addComponent(btnNew_priem)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnSave_priem)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(btnOsm)
        			.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
        			.addComponent(btnDel_priem))
        );
        panel_20.setLayout(gl_panel_20);

        JPanel panel_35 = new JPanel();
        panel_35.setBorder(new TitledBorder(null, "Дата и время", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        JPanel panel_36 = new JPanel();
        panel_36.setBorder(new TitledBorder(null, "Своевременность", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        JLabel lblNewLabel_62 = new JLabel("Отделение");
        lblNewLabel_62.setFont(new Font("Tahoma", Font.PLAIN, 12));

        cbx_messr = new JCheckBox("сообщение родственникам");
        cbx_messr.setFont(new Font("Tahoma", Font.PLAIN, 12));
        GroupLayout gl_panel_34 = new GroupLayout(panel_34);
        gl_panel_34.setHorizontalGroup(
        	gl_panel_34.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_34.createSequentialGroup()
        			.addGroup(gl_panel_34.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel_34.createSequentialGroup()
        					.addComponent(lblNewLabel_62)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(cmb_cotd, GroupLayout.DEFAULT_SIZE, 690, Short.MAX_VALUE))
        				.addGroup(gl_panel_34.createSequentialGroup()
        					.addComponent(panel_35, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(panel_36, GroupLayout.PREFERRED_SIZE, 312, GroupLayout.PREFERRED_SIZE)
        					.addGap(57)
        					.addComponent(cbx_messr)
        					.addGap(0, 0, Short.MAX_VALUE)))
        			.addGap(143))
        );
        gl_panel_34.setVerticalGroup(
        	gl_panel_34.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_34.createSequentialGroup()
        			.addGroup(gl_panel_34.createParallelGroup(Alignment.TRAILING)
        				.addGroup(gl_panel_34.createSequentialGroup()
        					.addGroup(gl_panel_34.createParallelGroup(Alignment.BASELINE)
        						.addComponent(panel_35, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
        						.addComponent(panel_36, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
        					.addGap(9))
        				.addGroup(gl_panel_34.createSequentialGroup()
        					.addComponent(cbx_messr)
        					.addGap(18)))
        			.addGroup(gl_panel_34.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_62)
        				.addComponent(cmb_cotd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(3, Short.MAX_VALUE))
        );

        JLabel lblNewLabel_59 = new JLabel("от начала заболевания");
        lblNewLabel_59.setFont(new Font("Tahoma", Font.PLAIN, 12));

        sp_sv_time = new JSpinner();
        sp_sv_time.setFont(new Font("Tahoma", Font.PLAIN, 12));
        sp_sv_time.setModel(new SpinnerNumberModel(0, 0, 23, 1));

        JLabel lblNewLabel_60 = new JLabel("часов");
        lblNewLabel_60.setFont(new Font("Tahoma", Font.PLAIN, 12));

        JLabel lblNewLabel_61 = new JLabel("суток");
        lblNewLabel_61.setFont(new Font("Tahoma", Font.PLAIN, 11));

        sp_sv_day = new JSpinner();
        sp_sv_day.setFont(new Font("Tahoma", Font.PLAIN, 12));
        sp_sv_day.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
        GroupLayout gl_panel_36 = new GroupLayout(panel_36);
        gl_panel_36.setHorizontalGroup(
            gl_panel_36.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_36.createSequentialGroup()
                    .addComponent(lblNewLabel_59)
                    .addPreferredGap(ComponentPlacement.UNRELATED)
                    .addComponent(lblNewLabel_60)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(sp_sv_time, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblNewLabel_61)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(sp_sv_day, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
                    .addGap(19))
        );
        gl_panel_36.setVerticalGroup(
            gl_panel_36.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_36.createParallelGroup(Alignment.BASELINE)
                    .addComponent(lblNewLabel_59)
                    .addComponent(lblNewLabel_60)
                    .addComponent(sp_sv_time, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNewLabel_61)
                    .addComponent(sp_sv_day, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        panel_36.setLayout(gl_panel_36);

        cbx_gosp = new JCheckBox("");
        cbx_gosp.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                ChangeStateCheckbox();
            }
        });

        tf_datagosp = new CustomDateEditor();
        tf_datagosp.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_timegosp = new CustomTimeEditor();
        tf_timegosp.setFont(new Font("Tahoma", Font.PLAIN, 12));
        GroupLayout gl_panel_35 = new GroupLayout(panel_35);
        gl_panel_35.setHorizontalGroup(
        	gl_panel_35.createParallelGroup(Alignment.TRAILING)
        		.addGroup(Alignment.LEADING, gl_panel_35.createSequentialGroup()
        			.addComponent(tf_datagosp, GroupLayout.PREFERRED_SIZE, 101, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(tf_timegosp, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(cbx_gosp)
        			.addContainerGap(15, Short.MAX_VALUE))
        );
        gl_panel_35.setVerticalGroup(
        	gl_panel_35.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_35.createSequentialGroup()
        			.addGroup(gl_panel_35.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel_35.createParallelGroup(Alignment.BASELINE)
        					.addComponent(tf_datagosp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(tf_timegosp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addComponent(cbx_gosp))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_35.setLayout(gl_panel_35);
        panel_34.setLayout(gl_panel_34);

        JPanel panel_26 = new JPanel();

        tf_ntalon = new CustomTextField();
        tf_ntalon.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_ntalon.setColumns(10);

        JPanel panel_27 = new JPanel();
        panel_27.setBorder(new TitledBorder(null, "Дата и время", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        JPanel panel_28 = new JPanel();
        panel_28.setBorder(new TitledBorder(null, "Кем направлен", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        JPanel panel_29 = new JPanel();
        panel_29.setBorder(new TitledBorder(null, "Диагноз направившего учреждения", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        JPanel panel_30 = new JPanel();
        panel_30.setBorder(new TitledBorder(null, "Диагноз приемного отделения", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        JPanel panel_32 = new JPanel();
        panel_32.setBorder(new TitledBorder(null, "Доставлен СМП:", TitledBorder.LEADING, TitledBorder.TOP, null, null));

        JLabel lblNewLabel_48 = new JLabel("№ талона");
        lblNewLabel_48.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_56 = new JLabel("Вид травмы");
        lblNewLabel_56.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_57 = new JLabel("Вид транспортировки");
        lblNewLabel_57.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_58 = new JLabel("Причина отказа в госпитализации");
        lblNewLabel_58.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNist = new JLabel("№ ист. бол.");
        lblNist.setFont(new Font("Tahoma", Font.PLAIN, 12));

        tf_nist = new CustomTextField();
        tf_nist.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_nist.setColumns(10);
        
        cbx_ber = new JCheckBox("Беременность");
        cbx_ber.setFont(new Font("Tahoma", Font.PLAIN, 12));
        GroupLayout gl_panel_25 = new GroupLayout(panel_25);
        gl_panel_25.setHorizontalGroup(
        	gl_panel_25.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_25.createSequentialGroup()
        			.addGroup(gl_panel_25.createParallelGroup(Alignment.LEADING, false)
        				.addGroup(gl_panel_25.createSequentialGroup()
        					.addGroup(gl_panel_25.createParallelGroup(Alignment.LEADING, false)
        						.addGroup(gl_panel_25.createSequentialGroup()
        							.addGroup(gl_panel_25.createParallelGroup(Alignment.LEADING)
        								.addComponent(panel_26, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        								.addGroup(gl_panel_25.createSequentialGroup()
        									.addGroup(gl_panel_25.createParallelGroup(Alignment.LEADING)
        										.addComponent(lblNist)
        										.addComponent(lblNewLabel_48))
        									.addPreferredGap(ComponentPlacement.UNRELATED)
        									.addGroup(gl_panel_25.createParallelGroup(Alignment.LEADING)
        										.addComponent(tf_ntalon, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
        										.addComponent(tf_nist, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))))
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(panel_27, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE))
        						.addComponent(panel_29, 0, 0, Short.MAX_VALUE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(gl_panel_25.createParallelGroup(Alignment.LEADING, false)
        						.addComponent(panel_28, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(panel_30, GroupLayout.DEFAULT_SIZE, 489, Short.MAX_VALUE)))
        				.addGroup(gl_panel_25.createParallelGroup(Alignment.TRAILING, false)
        					.addGroup(Alignment.LEADING, gl_panel_25.createSequentialGroup()
        						.addComponent(lblNewLabel_58)
        						.addPreferredGap(ComponentPlacement.RELATED)
        						.addComponent(cmb_otkaz, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        					.addGroup(Alignment.LEADING, gl_panel_25.createSequentialGroup()
        						.addComponent(panel_32, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
        						.addGap(14)
        						.addGroup(gl_panel_25.createParallelGroup(Alignment.LEADING, false)
        							.addGroup(gl_panel_25.createSequentialGroup()
        								.addGroup(gl_panel_25.createParallelGroup(Alignment.TRAILING)
        									.addComponent(lblNewLabel_56, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
        									.addComponent(lblNewLabel_57, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE))
        								.addPreferredGap(ComponentPlacement.RELATED)
        								.addGroup(gl_panel_25.createParallelGroup(Alignment.LEADING)
        									.addComponent(cmb_trans, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
        									.addComponent(cmb_travm, GroupLayout.PREFERRED_SIZE, 345, GroupLayout.PREFERRED_SIZE)))
        							.addComponent(cbx_ber, GroupLayout.PREFERRED_SIZE, 117, GroupLayout.PREFERRED_SIZE)))))
        			.addContainerGap())
        );
        gl_panel_25.setVerticalGroup(
        	gl_panel_25.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_panel_25.createSequentialGroup()
        			.addGroup(gl_panel_25.createParallelGroup(Alignment.TRAILING, false)
        				.addGroup(gl_panel_25.createSequentialGroup()
        					.addComponent(panel_26, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addGroup(gl_panel_25.createParallelGroup(Alignment.BASELINE)
        						.addComponent(lblNewLabel_48)
        						.addComponent(tf_ntalon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addGroup(gl_panel_25.createParallelGroup(Alignment.BASELINE)
        						.addComponent(lblNist)
        						.addComponent(tf_nist, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        				.addGroup(gl_panel_25.createParallelGroup(Alignment.BASELINE)
        					.addComponent(panel_27, GroupLayout.PREFERRED_SIZE, 67, Short.MAX_VALUE)
        					.addComponent(panel_28, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_panel_25.createParallelGroup(Alignment.LEADING)
        				.addComponent(panel_29, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
        				.addComponent(panel_30, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE))
        			.addGroup(gl_panel_25.createParallelGroup(Alignment.TRAILING)
        				.addGroup(gl_panel_25.createSequentialGroup()
        					.addGap(11)
        					.addGroup(gl_panel_25.createParallelGroup(Alignment.BASELINE)
        						.addComponent(cmb_travm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(lblNewLabel_56))
        					.addGroup(gl_panel_25.createSequentialGroup()
        						.addPreferredGap(ComponentPlacement.UNRELATED)
        						.addGroup(gl_panel_25.createParallelGroup(Alignment.BASELINE)
        							.addComponent(lblNewLabel_57)
        							.addComponent(cmb_trans, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        					.addComponent(cbx_ber))
        				.addGroup(gl_panel_25.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(panel_32, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_panel_25.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_58)
        				.addComponent(cmb_otkaz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(91))
        );

        JLabel lblNewLabel_54 = new JLabel("Дата и время");
        lblNewLabel_54.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_55 = new JLabel("Номер");
        lblNewLabel_55.setFont(new Font("Tahoma", Font.PLAIN, 12));

        tf_smpn = new CustomTextField();
        tf_smpn.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_smpn.setColumns(10);

        cbx_smp = new JCheckBox("");
        cbx_smp.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent arg0) {
                ChangeStateCheckbox();
            }
        });

        tf_datasmp = new CustomDateEditor();
        tf_datasmp.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_timesmp = new CustomTimeEditor();
        tf_timesmp.setFont(new Font("Tahoma", Font.PLAIN, 12));
        GroupLayout gl_panel_32 = new GroupLayout(panel_32);
        gl_panel_32.setHorizontalGroup(
        	gl_panel_32.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_32.createSequentialGroup()
        			.addGroup(gl_panel_32.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel_32.createSequentialGroup()
        					.addComponent(lblNewLabel_55)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(tf_smpn, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel_32.createSequentialGroup()
        					.addGroup(gl_panel_32.createParallelGroup(Alignment.LEADING)
        						.addComponent(lblNewLabel_54)
        						.addComponent(tf_datasmp, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(tf_timesmp, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(cbx_smp)))
        			.addContainerGap())
        );
        gl_panel_32.setVerticalGroup(
        	gl_panel_32.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_32.createSequentialGroup()
        			.addGroup(gl_panel_32.createParallelGroup(Alignment.TRAILING)
        				.addGroup(gl_panel_32.createSequentialGroup()
        					.addComponent(lblNewLabel_54)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(tf_datasmp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addComponent(cbx_smp)
        				.addComponent(tf_timesmp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_panel_32.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_55)
        				.addComponent(tf_smpn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_32.setLayout(gl_panel_32);

        tf_diag_p = new CustomTextField();
        tf_diag_p.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_diag_p.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                if (arg0.getClickCount() == 2) {
                    StringClassifier res = MainForm.conMan.showMkbTreeForm("Классификатор МКБ-10", tf_diag_p.getText());

                    if (res != null) {
                        tf_diag_p.setText(res.pcod);
                        ta_diag_p.setText(res.name);
                    }
                }
            }
        });
        tf_diag_p.setColumns(10);
        tf_diag_p.setDefaultLanguage(DefaultLanguage.English);
        
        JScrollPane scrollPane_5 = new JScrollPane();
        GroupLayout gl_panel_30 = new GroupLayout(panel_30);
        gl_panel_30.setHorizontalGroup(
        	gl_panel_30.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_30.createSequentialGroup()
        			.addComponent(tf_diag_p, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(scrollPane_5, GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE))
        );
        gl_panel_30.setVerticalGroup(
        	gl_panel_30.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_30.createSequentialGroup()
        			.addGroup(gl_panel_30.createParallelGroup(Alignment.TRAILING)
        				.addGroup(Alignment.LEADING, gl_panel_30.createSequentialGroup()
        					.addGap(2)
        					.addComponent(scrollPane_5, GroupLayout.DEFAULT_SIZE, 29, Short.MAX_VALUE))
        				.addGroup(Alignment.LEADING, gl_panel_30.createSequentialGroup()
        					.addGap(13)
        					.addComponent(tf_diag_p, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        );
        
                ta_diag_p = new JTextArea();
                scrollPane_5.setViewportView(ta_diag_p);
                new CustomTextComponentWrapper(ta_diag_p).setDefaultLanguage(DefaultLanguage.Russian);
                ta_diag_p.setFont(new Font("Tahoma", Font.PLAIN, 12));
                ta_diag_p.setWrapStyleWord(true);
                ta_diag_p.setLineWrap(true);
        panel_30.setLayout(gl_panel_30);
        panel_30.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tf_diag_p}));

        tf_diag_n = new CustomTextField();
        tf_diag_n.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_diag_n.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                if (arg0.getClickCount() == 2) {
                    StringClassifier res = MainForm.conMan.showMkbTreeForm("Классификатор МКБ-10", tf_diag_n.getText());

                    if (res != null) {
                        tf_diag_n.setText(res.pcod);
                        ta_diag_n.setText(res.name);
                    }
                }
            }
        });
        tf_diag_n.setColumns(10);
        tf_diag_n.setDefaultLanguage(DefaultLanguage.English);
        
        JScrollPane scrollPane_4 = new JScrollPane();
        GroupLayout gl_panel_29 = new GroupLayout(panel_29);
        gl_panel_29.setHorizontalGroup(
        	gl_panel_29.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_29.createSequentialGroup()
        			.addComponent(tf_diag_n, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(scrollPane_4, GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE))
        );
        gl_panel_29.setVerticalGroup(
        	gl_panel_29.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_29.createSequentialGroup()
        			.addGroup(gl_panel_29.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel_29.createSequentialGroup()
        					.addGap(13)
        					.addComponent(tf_diag_n, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel_29.createSequentialGroup()
        					.addGap(2)
        					.addComponent(scrollPane_4, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE)))
        			.addContainerGap())
        );
        
                ta_diag_n = new JTextArea();
                scrollPane_4.setViewportView(ta_diag_n);
                new CustomTextComponentWrapper(ta_diag_n).setDefaultLanguage(DefaultLanguage.Russian);
                ta_diag_n.setFont(new Font("Tahoma", Font.PLAIN, 12));
                ta_diag_n.setWrapStyleWord(true);
                ta_diag_n.setLineWrap(true);
        panel_29.setLayout(gl_panel_29);
        panel_29.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tf_diag_n}));

        GroupLayout gl_panel_28 = new GroupLayout(panel_28);
        gl_panel_28.setHorizontalGroup(
            gl_panel_28.createParallelGroup(Alignment.LEADING)
                .addComponent(cmb_naprav, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                .addComponent(cmb_org, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
        );
        gl_panel_28.setVerticalGroup(
            gl_panel_28.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_28.createSequentialGroup()
                    .addComponent(cmb_naprav, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(15)
                    .addComponent(cmb_org, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE))
        );
        panel_28.setLayout(gl_panel_28);

        JLabel lblNewLabel_49 = new JLabel("Поступления");
        lblNewLabel_49.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_50 = new JLabel("Осмотра");
        lblNewLabel_50.setFont(new Font("Tahoma", Font.PLAIN, 12));

        tf_datap = new CustomDateEditor();
        tf_datap.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_timep = new CustomTimeEditor();
        tf_timep.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_dataosm = new CustomDateEditor();
        tf_dataosm.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_timeosm = new CustomTimeEditor();
        tf_timeosm.setFont(new Font("Tahoma", Font.PLAIN, 12));
        GroupLayout gl_panel_27 = new GroupLayout(panel_27);
        gl_panel_27.setHorizontalGroup(
        	gl_panel_27.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_27.createSequentialGroup()
        			.addGroup(gl_panel_27.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblNewLabel_49)
        				.addComponent(lblNewLabel_50))
        			.addGroup(gl_panel_27.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel_27.createSequentialGroup()
        					.addGap(10)
        					.addComponent(tf_datap, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel_27.createSequentialGroup()
        					.addGap(11)
        					.addComponent(tf_dataosm, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)))
        			.addGap(18)
        			.addGroup(gl_panel_27.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(tf_timeosm, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        				.addComponent(tf_timep, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(20))
        );
        gl_panel_27.setVerticalGroup(
        	gl_panel_27.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_27.createSequentialGroup()
        			.addGroup(gl_panel_27.createParallelGroup(Alignment.LEADING)
        				.addComponent(lblNewLabel_49)
        				.addGroup(gl_panel_27.createSequentialGroup()
        					.addComponent(tf_timep, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(tf_timeosm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel_27.createSequentialGroup()
        					.addComponent(tf_datap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(gl_panel_27.createParallelGroup(Alignment.BASELINE)
        						.addComponent(tf_dataosm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        						.addComponent(lblNewLabel_50))))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel_27.setLayout(gl_panel_27);
        panel_27.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tf_datap, tf_timep, tf_dataosm, tf_timeosm}));

        rbtn_plan = new JRadioButton("плановое");
        rbtn_plan.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnGroup_plextr.add(rbtn_plan);

        rbtn_extr = new JRadioButton("экстренное");
        rbtn_extr.setFont(new Font("Tahoma", Font.PLAIN, 12));
        btnGroup_plextr.add(rbtn_extr);
        GroupLayout gl_panel_26 = new GroupLayout(panel_26);
        gl_panel_26.setHorizontalGroup(
            gl_panel_26.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_26.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(rbtn_plan)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(rbtn_extr)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        gl_panel_26.setVerticalGroup(
            gl_panel_26.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_26.createSequentialGroup()
                    .addGroup(gl_panel_26.createParallelGroup(Alignment.BASELINE)
                        .addComponent(rbtn_plan, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
                        .addComponent(rbtn_extr))
                    .addContainerGap(6, Short.MAX_VALUE))
        );
        panel_26.setLayout(gl_panel_26);
        panel_25.setLayout(gl_panel_25);
        panel_25.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{tf_ntalon, tf_nist, cmb_naprav, cmb_org, cbx_ber, cmb_travm, cmb_trans, cmb_otkaz}));

        JScrollPane scrollPane_3 = new JScrollPane();
        GroupLayout gl_panel_23 = new GroupLayout(panel_23);
        gl_panel_23.setHorizontalGroup(
        	gl_panel_23.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel_23.createSequentialGroup()
        			.addComponent(scrollPane_3, GroupLayout.PREFERRED_SIZE, 769, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(83, Short.MAX_VALUE))
        );
        gl_panel_23.setVerticalGroup(
        	gl_panel_23.createParallelGroup(Alignment.LEADING)
        		.addComponent(scrollPane_3, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
        );

        tbl_priem = new CustomTable<>(true, false, AllGosp.class, 3,"N ист. бол.",4,"Дата",5,"Отделение",6,"Диагноз", 7, "Наименование");
        tbl_priem.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tbl_priem.setDateField(1);
        tbl_priem.setPreferredWidths(70,70,65,60,500);
        tbl_priem.setFillsViewportHeight(true);
        tbl_priem.setShowVerticalLines(true);
        tbl_priem.setShowHorizontalLines(true);
        scrollPane_3.setViewportView(tbl_priem);
        panel_23.setLayout(gl_panel_23);
        tpPriem.setLayout(gl_tpPriem);


        tbl_priem.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
        @Override
        public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                if (tbl_priem.getSelectedItem() !=  null) {
                    changePatientPriemInfo(curPatientId);
                    newPriem = tbl_priem.getSelectedItem();
                }
            }
        }
        });

        //удалить
        tbl_priem.registerDeleteSelectedRowListener(new CustomTableItemChangeEventListener<AllGosp>() {
        @Override
        public boolean doAction(CustomTableItemChangeEvent<AllGosp> event) {
            try {
            	System.out.println(curId);
                MainForm.tcl.deleteGosp(curId);
                NewPriemInfo();
            } catch (TException e) {
                MainForm.conMan.reconnect(e);
                e.printStackTrace();
                return false;
            }
            return true;
            }
        });

//        tbl_priem.registerUpdateSelectedRowListener(new CustomTableItemChangeEventListener<AllGosp>() {
//            @Override
//            public boolean doAction(CustomTableItemChangeEvent<AllGosp> event) {
//                    newPriem.setId(curId);
//                    newPriem.setNist(Id_gosp.getNist());
//                    newPriem.setDatap(Id_gosp.getDatap());
//                    newPriem.setCotd(Id_gosp.getCotd());
//                    newPriem.setDiag_p(Id_gosp.getDiag_p());
//                    newPriem.setNamed_p(Id_gosp.getNamed_p());
//                return true;
//            }
//        });

        JButton btnPrint_ambk = new JButton("Амбул.карты");
        btnPrint_ambk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String servPath;
                try {
                    String docInfo = cmb_tdoc.getText() + " " + tf_serdoc.getText() + " " + tf_nomdoc.getText();
                    String omsOrg = cmb_oms_smo.getText();
                    String lgot = "";

                    if (tbl_lgota.getData() != null)
                    	LgotaInfo = tbl_lgota.getData();
                    if (LgotaInfo.size() > 0) {
                        for (AllLgota lg : LgotaInfo) {
                            if (lg.isSetLgota()) {
                                lgot += ", " + String.valueOf(lg.getLgota());
                            }
                        }
                        lgot = lgot.substring(1);
                    } else {
                        lgot = "";
                    }
                    servPath = MainForm.tcl.printMedCart(NambInfo, PersonalInfo, MainForm.authInfo, docInfo, omsOrg, lgot);
                    String cliPath = File.createTempFile("muzdrav", ".htm").getAbsolutePath();
                    MainForm.conMan.transferFileFromServer(servPath, cliPath);
                    MainForm.conMan.openFileInEditor(cliPath, false);
                } catch (TException e) {
                    MainForm.conMan.reconnect((TException) e);
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        btnPrint_ambk.setToolTipText("Печать титульного листа амбулаторной карты");

        JButton btnPrint_istb = new JButton("Ист.болезни");
        btnPrint_istb.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String servPath;
                if ((Id_gosp != null) && (PersonalInfo != null)) {
                    try {
    //                    String docInfo = cmb_tdoc.getText() + " " + tf_serdoc.getText() + " " + tf_nomdoc.getText();
    //                    String omsOrg = cmb_oms_smo.getText();
    //                    String lgot = "";
    //                    Gosp = new Gosp()
                        String cotdName = "";
                        if (cmb_cotd.getSelectedItem() != null) {
                            cotdName = cmb_cotd.getSelectedItem().getName();
                        }
                        String naprName = "";
                        if (cmb_naprav.getSelectedItem() != null) {
                            naprName = cmb_naprav.getSelectedItem().getName();
                        }
                        if (cmb_org.getSelectedItem() != null) {
                            naprName += ", " +cmb_org.getSelectedItem().getName();
                        }
                        String vidTrans = "";
                        if (cmb_trans.getSelectedItem() != null) {
                            vidTrans += cmb_trans.getSelectedItem().getName();
                        }
                        String grBl = "";
                        String rezus = "";
                        if (SignInfo != null) {
                            if (SignInfo.isSetGrup()) {
                                grBl = SignInfo.getGrup();
                            }
                            if (SignInfo.isSetPh()) {
                                rezus = SignInfo.getPh();
                            }
                        }
                        servPath = MainForm.tcl.printStacCart(PersonalInfo, Id_gosp, cotdName, naprName,
                                vidTrans, grBl, rezus);
                        String cliPath = File.createTempFile("muzdrav", ".htm").getAbsolutePath();
                        MainForm.conMan.transferFileFromServer(servPath, cliPath);
                        MainForm.conMan.openFileInEditor(cliPath, false);
                    } catch (TException e) {
                        MainForm.conMan.reconnect((TException) e);
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    JOptionPane.showMessageDialog(PacientInfoFrame.this, "Пациент не выбран, либо у выбранного пациента нет записей госпитализации.");
                }
            }
        });
        btnPrint_istb.setToolTipText("Печать титульного листа истории болезни");

        GroupLayout gl_pl_print = new GroupLayout(pl_print);
        gl_pl_print.setHorizontalGroup(
            gl_pl_print.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_pl_print.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(btnPrint_ambk)
                    .addGap(26)
                    .addComponent(btnPrint_istb)
                    .addContainerGap(41, Short.MAX_VALUE))
        );
        gl_pl_print.setVerticalGroup(
            gl_pl_print.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_pl_print.createSequentialGroup()
                    .addGroup(gl_pl_print.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnPrint_ambk)
                        .addComponent(btnPrint_istb))
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

//FIXME
        if (MainForm.authInfo.getCslu() != 1) {
            tbMain.remove(tpSign);
            tbMain.remove(tpPriem);
            btnPrint_istb.setVisible(false);
            btnShowTalonSelectModule.setVisible(true);
        }
        if (MainForm.authInfo.getCslu() == 1) {
            btnPrint_ambk.setVisible(false);
            btnPrint_istb.setVisible(true);
            btnShowTalonSelectModule.setVisible(false);
        }

        pl_print.setLayout(gl_pl_print);
        contentPane.setLayout(gl_contentPane);
    }

    public void onConnect() {
        try {
            cmb_cotd.setData(MainForm.tcl.getOtdForCurrentLpu(MainForm.authInfo.clpu));
            cmb_org.setSelectedItem(null);
            cmb_ishod.setData(MainForm.tcl.getABB());
//            cmb_adm_obl.setData(null);
//            cmb_ogrn.setData(null);
            tbl_lgota.setIntegerClassifierSelector(1, MainForm.tcl.getLKN());
            tbl_kateg.setIntegerClassifierSelector(1, MainForm.tcl.getLKR());

        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
            MainForm.conMan.reconnect(e);
        }
    }
    //слушатель таб контрола персональной информации о пациенте
    final ChangeListener  tpPersonalChangeListener= new ChangeListener() {
        public void stateChanged(ChangeEvent changeEvent) {
//            curPatientId = tbl_patient.getSelectedItem().npasp;
            changePatientPersonalInfo(curPatientId);
        }
    };

    //слушатель таб контрола о льготе
    final ChangeListener  tpLgotaChangeListener= new ChangeListener() {
        public void stateChanged(ChangeEvent changeEvent) {
            changePatientLgotaInfo(curPatientId);
        }
    };

    //слушатель таб контрола о категории
    final ChangeListener  tpKategChangeListener= new ChangeListener() {
        public void stateChanged(ChangeEvent changeEvent) {
            changePatientKategInfo(curPatientId);
        }
    };

    //слушатель таб контрола о представителе
    final ChangeListener  tpAgentChangeListener= new ChangeListener() {
        public void stateChanged(ChangeEvent changeEvent) {
            changePatientAgentInfo(curPatientId);
        }
    };

    //слушатель таб контрола о сигнальн отм
    final ChangeListener  tpSignChangeListener= new ChangeListener() {
        public void stateChanged(ChangeEvent changeEvent) {
//            changePatientSignInfo(curPatientId);
        	tpSign.ChangePatientAnamnezInfo();
        }
    };

    //слушатель таб контрола о приемн отд
    final ChangeListener  tpPriemChangeListener= new ChangeListener() {
        public void stateChanged(ChangeEvent changeEvent) {
            selectAllPatientPriemInfo(curPatientId);
        }
    };

    // слушатель главного таб контрола
    final ChangeListener  mainChangeListener= new ChangeListener() {

        public void stateChanged(ChangeEvent changeEvent) {
                if (tbMain.getSelectedIndex() == 0){
                  tpPersonalChangeListener.stateChanged(new ChangeEvent(tpPersonal));
                }
                if (tbMain.getSelectedIndex() == 1){
                  tpLgotaChangeListener.stateChanged(new ChangeEvent(tpLgota));
                }
                if (tbMain.getSelectedIndex() == 2){
                  tpKategChangeListener.stateChanged(new ChangeEvent(tpKateg));
                }
                if (tbMain.getSelectedIndex() == 3){
                  tpAgentChangeListener.stateChanged(new ChangeEvent(tpAgent));
                }
                if (tbMain.getSelectedIndex() == 4){
                  tpSignChangeListener.stateChanged(new ChangeEvent(tpSign));
                }
                if (tbMain.getSelectedIndex() == 5){
                  tpPriemChangeListener.stateChanged(new ChangeEvent(tpPriem));
                }
          }
    };

    //TODO просмотр  информации о пациенте
    private void changePatientPersonalInfo(int PatId){
        try {
            if (PatId == 0)
                return;
            NewPatient();
            PersonalInfo = MainForm.tcl.getPatientFullInfo(PatId);
            try {
                NambInfo = new Nambk();
                NambInfo = MainForm.tcl.getNambk(PatId, MainForm.authInfo.getCpodr());
            } catch (NambkNotFoundException nnfe) {
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (PersonalInfo.getFam() != null) tfFam.setText(PersonalInfo.getFam());
            if (PersonalInfo.getIm() != null) tfIm.setText(PersonalInfo.getIm());
            if (PersonalInfo.getOt() != null) tfOt.setText(PersonalInfo.getOt());
            if (PersonalInfo.isSetDatar()) tfDr.setDate(PersonalInfo.datar);
            if (PersonalInfo.getAdmAddress().flat != null) tf_Adm_kv.setText(PersonalInfo.admAddress.flat);
            if (PersonalInfo.getAdpAddress().flat != null) tf_Adp_kv.setText(PersonalInfo.adpAddress.flat);
            if (PersonalInfo.getNamemr() != null) tfMrname.setText(PersonalInfo.namemr);
            if (PersonalInfo.getMrab() != 0) tfMr.setText(Integer.toString(PersonalInfo.getMrab()));
            if (PersonalInfo.getProf() != null) tfDolj.setText(PersonalInfo.prof);
            if (PersonalInfo.getTel() != null)tfTel.setText(PersonalInfo.tel);
            if (PersonalInfo.getSnils() != null)tf_Snils.setText(PersonalInfo.snils);
            if (PersonalInfo.odoc != null) tf_Odoc.setText(PersonalInfo.odoc);
            if (PersonalInfo.getDocser() != null) tf_serdoc.setText(PersonalInfo.docser);
            if (PersonalInfo.getDocnum() != null) tf_nomdoc.setText(PersonalInfo.docnum);
            if (PersonalInfo.getPolis_dms().ser != null)tf_dms_ser.setText(PersonalInfo.polis_dms.ser);
            if (PersonalInfo.getPolis_dms().nom != null)tf_dms_nom.setText(PersonalInfo.polis_dms.nom);
            if (PersonalInfo.getPolis_oms().ser != null)tf_oms_ser.setText(PersonalInfo.polis_oms.ser);
            if (PersonalInfo.getPolis_oms().nom != null)tf_oms_nom.setText(PersonalInfo.polis_oms.nom);
            if (PersonalInfo.isSetCpol_pr())tf_Cpol.setText(Integer.toString(PersonalInfo.cpol_pr));

            if (NambInfo.nambk != null)tf_Nambk.setText(NambInfo.getNambk());
            if (NambInfo.getNuch() != 0)tf_Nuch.setText(Integer.toString(NambInfo.getNuch()));
            if (NambInfo.getDatapr() != 0)tf_datapr.setDate(NambInfo.getDatapr());
            if (NambInfo.getDataot() != 0)tf_dataot.setDate(NambInfo.getDataot());
            if (NambInfo.getIshod() != 0) cmb_ishod.setSelectedPcod(NambInfo.getIshod());

            if (PersonalInfo.isSetDatadoc())tf_datadoc.setDate(PersonalInfo.datadoc);
            if (PersonalInfo.isSetPol()){
                rbtn_pol_m.setSelected(PersonalInfo.getPol() == 1);
                rbtn_pol_j.setSelected(PersonalInfo.getPol() == 2);
            }
            if (PersonalInfo.getSgrp() != 0) cmb_status.setSelectedPcod(PersonalInfo.getSgrp());
            if (PersonalInfo.getObraz() != 0) cmb_obr.setSelectedPcod(PersonalInfo.getObraz());
            if (PersonalInfo.getPolis_oms().tdoc != 0)cmb_oms_doc.setSelectedPcod(PersonalInfo.getPolis_oms().getTdoc());
            if (PersonalInfo.getTdoc() != 0) cmb_tdoc.setSelectedPcod(PersonalInfo.getTdoc());
            if (PersonalInfo.getPolis_oms().strg != 0)cmb_oms_smo.setSelectedPcod(PersonalInfo.getPolis_oms().getStrg());
            if (PersonalInfo.getPolis_dms().strg != 0)cmb_dms_smo.setSelectedPcod(PersonalInfo.getPolis_dms().getStrg());

//			if (cmb_oms_smo.getSelectedPcod() != null)
//				cmb_ogrn.setData(MainForm.tcl.getSmorf(cmb_oms_smo.getSelectedPcod()));

			if (PersonalInfo.getBirthplace() != null) tf_birthplace.setText(PersonalInfo.getBirthplace());
            try{
                if (PersonalInfo.getOgrn_smo() != null) {
                	cmb_ogrn.setSelectedPcod(MainForm.tcl.getSmocod(PersonalInfo.getOgrn_smo(),PersonalInfo.getPolis_oms().strg));
                }
            } catch (SmocodNotFoundException snfe) {
                System.out.println("ОГРН отсутствует.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void NewPatient(){
        try {
            tfFam.setText(null);
            tfIm.setText(null);
            tfOt.setText(null);
            tfDr.setValue(null);
            tf_Adm_kv.setText(null);
            tf_Adp_kv.setText(null);
            tfMrname.setText(null);
            tfMr.setText(null);
            tfDolj.setText(null);
            tfTel.setText(null);
            tf_Cpol.setText(null);
            tf_Nuch.setText(null);
            tf_Nambk.setText(null);
            tf_datapr.setValue(null);
            tf_dataot.setValue(null);
            tf_datadoc.setValue(null);
            tf_Snils.setText(null);
            tf_Odoc.setText(null);
            tf_serdoc.setText(null);
            tf_nomdoc.setText(null);
            tf_dms_ser.setText(null);
            tf_dms_nom.setText(null);
            tf_oms_ser.setText(null);
            tf_oms_nom.setText(null);
            btnGroup_pol.clearSelection();
            cmb_status.setSelectedIndex(-1);
            cmb_obr.setSelectedIndex(-1);
            cmb_oms_doc.setSelectedIndex(-1);
            cmb_ishod.setSelectedIndex(-1);
            cmb_tdoc.setSelectedIndex(-1);
            cmb_oms_smo.setSelectedIndex(-1);
            cmb_dms_smo.setSelectedIndex(-1);
            cmb_adm_obl.setSelectedIndex(-1);
            cmb_adp_obl.setSelectedIndex(-1);
            cmb_adm_gorod.setSelectedIndex(-1);
            cmb_adp_gorod.setSelectedIndex(-1);
            cmb_adm_ul.setData(null);
            cmb_adp_ul.setData(null);
            cmb_adp_dom.setData(null);
            cmb_adm_dom.setData(null);
            tf_birthplace.setText(null);
//            cmb_adm_obl.setText(null);
//            cmb_adp_obl.setText(null);
//            cmb_adm_gorod.setText(null);
//            cmb_adp_gorod.setText(null);
//            cmb_adm_ul.setText(null);
//            cmb_adp_ul.setText(null);
//            cmb_adm_dom.setText(null);
//            cmb_adp_dom.setText(null);

			LgotaInfo = new ArrayList<AllLgota>();
			KontingentInfo =  new ArrayList<Kontingent>();
			AgentInfo = new Agent();
            SignInfo = new Sign();
            AllGospInfo = new ArrayList<AllGosp>();
			Id_lgota = new AllLgota();
			Info_lgota = new Lgota();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // обновление информации о льготе
    private void changePatientLgotaInfo(int PatId){
        try {
            tbl_lgota.setData(new ArrayList<AllLgota>());
            LgotaInfo = MainForm.tcl.getAllLgota(PatId);
            tbl_lgota.setData(LgotaInfo);
        } catch (LgotaNotFoundException lnfe) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // сохранение информации о льготе
    private void SaveForLgotaPatient(){
        try {
            if (tbl_lgota.getSelectedItem() == null){
                return;
            }
			Id_lgota = new AllLgota();
			Id_lgota.setNpasp(curPatientId);
            if (tbl_lgota.getSelectedItem().getId() != 0)curId_lgt = tbl_lgota.getSelectedItem().id;
            Id_lgota.setId(curId_lgt);
            Id_lgota.setLgota(tbl_lgota.getSelectedItem().lgota);
            Id_lgota.setDatau(tbl_lgota.getSelectedItem().datau);
			if (tfgr.getText().length() > 0) Id_lgota.setGri(Integer.valueOf(tfgr.getText())); 
            if (tfspr.getText().length() > 0) Id_lgota.setNdoc(tfspr.getText());
            if (rbtn_vperv.isSelected())Id_lgota.setPp(1);
            if (rbtn_povt.isSelected()) Id_lgota.setPp(2);
            if (tfdust.getDate() != null) Id_lgota.setDrg(tfdust.getDate().getTime());
            if (tfdotm.getDate() != null) Id_lgota.setDot(tfdotm.getDate().getTime());
            if (cmb_obst.getSelectedItem() != null) Id_lgota.setObo(cmb_obst.getSelectedPcod());
            if (cmb_srok.getSelectedItem() != null) Id_lgota.setSin(cmb_srok.getSelectedPcod());
        	MainForm.tcl.updateLgota(Id_lgota);
        } catch (LgotaNotFoundException lnfe) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // обновление информации о льготе
    private void InfoForLgotaPatient(){
        try {
            NewLgotaInfo();
            if (tbl_lgota.getSelectedItem() == null){
                return;
            }
            if (tbl_lgota.getSelectedItem().getId() != 0) curId_lgt = tbl_lgota.getSelectedItem().getId();
            if (curId_lgt != 0){
                Info_lgota = MainForm.tcl.getLgota(curId_lgt);
                if (Info_lgota.getGri() != 0) tfgr.setText(Integer.toString(Info_lgota.getGri()));
                if (Info_lgota.getNdoc() != null) tfspr.setText(Info_lgota.getNdoc());
                if (Info_lgota.getDrg() != 0) tfdust.setDate(Info_lgota.getDrg());
                if (Info_lgota.getDot() != 0) tfdotm.setDate(Info_lgota.getDot());
                if (Info_lgota.isSetPp()){
                    rbtn_vperv.setSelected(Info_lgota.getPp() == 1);
                    rbtn_povt.setSelected(Info_lgota.getPp() == 2);
                }
                if (Info_lgota.getSin() != 0) cmb_srok.setSelectedPcod(Info_lgota.getSin());
                if (Info_lgota.getObo() != 0) cmb_obst.setSelectedPcod(Info_lgota.getObo());
            }
        } catch (LgotaNotFoundException lnfe) {
            System.out.println("Информации о льготе нет.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // обновление информации о категории
    private void changePatientKategInfo(int PatId){
        try {
            tbl_kateg.setData(new ArrayList<Kontingent>());
            KontingentInfo = MainForm.tcl.getKontingent(PatId);
            tbl_kateg.setData(KontingentInfo);
        } catch (KontingentNotFoundException knfe) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // обновление информации о представителе
    private void changePatientAgentInfo(int PatId){
        try {
            NewAgent();
            AgentInfo = MainForm.tcl.getAgent(PatId);
            if (AgentInfo.getFam() != null)	tf_Fam_pr.setText(AgentInfo.fam);
            if (AgentInfo.getIm() != null) tf_Im_pr.setText(AgentInfo.im);
            if (AgentInfo.getOt() != null) tf_Ot_pr.setText(AgentInfo.ot);
            if (AgentInfo.isSetDatar())	tf_dr_pr.setDate(AgentInfo.datar);
//            if (AgentInfo.getBirthplace() != null) tf_Mr_pr.setText(AgentInfo.birthplace);
            if (AgentInfo.getSpolis() != null) tf_Polis_ser_pr.setText(AgentInfo.getSpolis());
            if (AgentInfo.getNpolis() != null) tf_Polis_nom_pr.setText(AgentInfo.getNpolis());
//            if (AgentInfo.getName_str() != null) tf_Name_sk_pr.setText(AgentInfo.name_str);
//            if (AgentInfo.getName_str() != null) tf_name_smo.setText(AgentInfo.name_str);
            if (AgentInfo.isSetPol()){
                rbtn_pol_pr_m.setSelected(AgentInfo.pol == 1);
                rbtn_pol_pr_j.setSelected(AgentInfo.pol == 2);
            }
            if (AgentInfo.getVpolis() != 0) cmb_Polis_doc_pr.setSelectedPcod(AgentInfo.getVpolis());
            if (AgentInfo.getTdoc() != 0) cmb_tdoc_pr.setSelectedPcod(AgentInfo.getTdoc());
            if (AgentInfo.getDocser() != null) tf_Ser_doc_pr.setText(AgentInfo.getDocser());
            if (AgentInfo.getDocnum() != null) tf_Nomdoc_pr.setText(AgentInfo.getDocnum());
        } catch (AgentNotFoundException anfe) {
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void NewLgotaInfo(){
        try {
			Info_lgota = new Lgota();
            tfgr.setText(null);
            tfspr.setText(null);
            tfdust.setValue(null);
            tfdotm.setValue(null);
            btnGroup_pp.clearSelection();
            cmb_srok.setSelectedIndex(-1);
            cmb_obst.setSelectedIndex(-1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void NewAgent(){
       tf_Fam_pr.setText(null);
       tf_Im_pr.setText(null);
       tf_Ot_pr.setText(null);
       tf_dr_pr.setValue(null);
       tf_Polis_ser_pr.setText(null);
       tf_Polis_nom_pr.setText(null);
//     tf_name_smo.setText(null);
       btnGroup_pol_pr.clearSelection();
       cmb_tdoc_pr.setSelectedIndex(-1);
       tf_Ser_doc_pr.setText(null);
       tf_Nomdoc_pr.setText(null);
       cmb_Polis_doc_pr.setSelectedIndex(-1);
//       tf_birthplace.setText(null);
    }

    // обновление информации сигн отм
    private void changePatientSignInfo(int PatId){
//        try {
            NewSign();
//            SignInfo = MainForm.tcl.getSign(PatId);
//            if (SignInfo.getGrup() != null){
////                rbtn_gk1.setSelected(SignInfo.grup.charAt(0) == '1');
////                rbtn_gk2.setSelected(SignInfo.grup.charAt(0) == '2');
////                rbtn_gk3.setSelected(SignInfo.grup.charAt(0) == '3');
////                rbtn_gk4.setSelected(SignInfo.grup.charAt(0) == '4');
//            }
//            if (SignInfo.getPh() != null){
////                rbtn_rf1.setSelected(SignInfo.ph.charAt(0) == '+');
////                rbtn_rf2.setSelected(SignInfo.ph.charAt(0) == '-');
//            }
//            if (SignInfo.getVred() != null){
////                rbtn_vp1.setSelected(SignInfo.vred.charAt(0) == '1');
////                rbtn_vp2.setSelected(SignInfo.vred.charAt(1) == '1');
////                rbtn_vp3.setSelected(SignInfo.vred.charAt(2) == '1');
//            }
//            if (SignInfo.getAllerg() != null){
////                ta_allerg.setText(SignInfo.allerg);
//            }
//            if (SignInfo.getFarmkol() != null){
////                ta_farm.setText(SignInfo.farmkol);
//            }
//            if (SignInfo.getVitae() != null){
////                ta_vitae.setText(SignInfo.vitae);
//            }
//        } catch (SignNotFoundException snfe) {
//            System.out.println("Сигнальной информации нет.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    private void NewSign(){
        try {
//            btnGroup_gk.clearSelection();
//            btnGroup_rf.clearSelection();
//            rbtn_vp1.setSelected(false);
//            rbtn_vp2.setSelected(false);
//            rbtn_vp3.setSelected(false);
//            ta_allerg.setText(null);
//            ta_farm.setText(null);
//            ta_vitae.setText(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // просмотр всех обращений
    private void selectAllPatientPriemInfo(int PatId){
      try{
        tbl_priem.setData(new ArrayList<AllGosp>());
        AllGospInfo = MainForm.tcl.getAllGosp(PatId);
        tbl_priem.setData(AllGospInfo);
      } catch (GospNotFoundException gnfe) {
//            System.out.println("Обращений нет.");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
    // просмотр информации о госпитализациях
    private void changePatientPriemInfo(int PatId){
        NewPriemInfo();
        if (tbl_priem.getSelectedItem() == null){
            return;
        }
           try {
            curId = tbl_priem.getSelectedItem().id;
            curNgosp = tbl_priem.getSelectedItem().ngosp;
            Id_gosp = MainForm.tcl.getGosp(curId);
//			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
//            if (Id_gosp.getJalob() != null){
//                ta_jal_pr.setText(Id_gosp.getJalob());
//            }
            if (Id_gosp.isSetPl_extr()){
                rbtn_plan.setSelected(Id_gosp.pl_extr == 2);
                rbtn_extr.setSelected(Id_gosp.pl_extr == 1);
            }
//            if (Id_gosp.isSetNal_z()){
//                cbx_nalz.setSelected(Id_gosp.nal_z);
//            }
//            if (Id_gosp.isSetNal_p()){
//                cbx_nalp.setSelected(Id_gosp.nal_p);
//            }
            if (Id_gosp.isSetPr_ber()){
                cbx_ber.setSelected(Id_gosp.pr_ber);
            }
            if (Id_gosp.isSetMessr()){
                cbx_messr.setSelected(Id_gosp.messr);
            }
            if (Id_gosp.getDiag_n() != null){
                tf_diag_n.setText(Id_gosp.getDiag_n());
            }
            if (Id_gosp.getDiag_p() != null){
                tf_diag_p.setText(Id_gosp.getDiag_p());
            }
            if (Id_gosp.getNamed_n() != null){
                ta_diag_n.setText(Id_gosp.getNamed_n());
            }
            if (Id_gosp.getNamed_p() != null){
                ta_diag_p.setText(Id_gosp.getNamed_p());
            }
//            if (Id_gosp.getToc() != null){
//                tf_toc.setText(Id_gosp.getToc());
//            }
//            if (Id_gosp.getAd() != null){
//                tf_ad.setText(Id_gosp.getAd());
//            }

            if (Id_gosp.getSmp_num() != 0) {
                tf_smpn.setText(Integer.toString(Id_gosp.getSmp_num()));
                tf_smpn.setEnabled(true);
            }
            if (Id_gosp.getSmp_data() != 0) {
                tf_datasmp.setDate(Id_gosp.getSmp_data());
                tf_timesmp.setTime(Id_gosp.getSmp_time());
                tf_datasmp.setEnabled(true);
                tf_timesmp.setEnabled(true);
                cbx_smp.setSelected(true);
            }

            if (Id_gosp.getDatap() != 0) {
//				sp_datap.setValue(sdf.format(new Date(Id_gosp.datap)));
                tf_datap.setDate(Id_gosp.getDatap());
                tf_timep.setTime(Id_gosp.getVremp());
            }
            if (Id_gosp.getDataosm() != 0) {
                tf_dataosm.setDate(Id_gosp.getDataosm());
                tf_timeosm.setTime(Id_gosp.getVremosm());
            }
            if (Id_gosp.getDatagos()!= 0) {
                tf_datagosp.setDate(Id_gosp.getDatagos());
                tf_timegosp.setTime(Id_gosp.getVremgos());
                tf_datagosp.setEnabled(true);
                tf_timegosp.setEnabled(true);
                cbx_gosp.setSelected(true);
            }
            if (Id_gosp.isSetNtalon()) {
                tf_ntalon.setText(Integer.toString(Id_gosp.getNtalon()));
            }
            if (Id_gosp.isSetNist()) {
                tf_nist.setText(Integer.toString(Id_gosp.getNist()));
            }
            if (Id_gosp.isSetSv_time()) {
                sp_sv_time.setValue(Id_gosp.getSv_time());
            }
            if (Id_gosp.isSetSv_day()) {
                sp_sv_day.setValue(Id_gosp.getSv_day());
            }
            if (Id_gosp.getCotd() != 0) {
                cmb_cotd.setSelectedPcod(Id_gosp.getCotd());
            }
//            if (Id_gosp.getAlkg() != 0) {
//                cmb_alk.setSelectedPcod(Id_gosp.getAlkg());
//            }
            if (Id_gosp.getVidtr() != 0) {
                cmb_travm.setSelectedPcod(Id_gosp.getVidtr());
            }
            if (Id_gosp.getVid_trans() != 0) {
                cmb_trans.setSelectedPcod(Id_gosp.getVid_trans());
            }
            if (Id_gosp.getPr_out() != 0) {
                cmb_otkaz.setSelectedPcod(Id_gosp.getPr_out());
            }

            if (Id_gosp.getNaprav() != null) {
                cmb_naprav.setSelectedPcod(Id_gosp.getNaprav());
                if (Id_gosp.getN_org() != 0) {
                    cmb_org.setSelectedPcod(Id_gosp.getN_org());
                }
            }
        } catch (GospNotFoundException gnfe) {
            System.out.println("Информации нет.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void ChangeStateCheckbox(){
        try {
            tf_datasmp.setEnabled(cbx_smp.isSelected());
            tf_timesmp.setEnabled(cbx_smp.isSelected());
            tf_smpn.setEnabled(cbx_smp.isSelected());
            tf_datagosp.setEnabled(cbx_gosp.isSelected());
            tf_timegosp.setEnabled(cbx_gosp.isSelected());
            if (cbx_gosp.isSelected()){
            	if (tf_datagosp.getDate() == null) tf_datagosp.setDate(System.currentTimeMillis());
                if (tf_timegosp.getTime() == null) tf_timegosp.setTime(System.currentTimeMillis());
            }else{
                tf_datagosp.setValue(null);
                tf_timegosp.setValue(null);
            }
            if (cbx_smp.isSelected()){
            	if (tf_datasmp.getDate() == null) tf_datasmp.setDate(System.currentTimeMillis());
            	if (tf_timesmp.getTime() == null) tf_timesmp.setTime(System.currentTimeMillis());
            }else{
                tf_datasmp.setValue(null);
                tf_timesmp.setValue(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // новое обращение
    private void NewPriemInfo(){
        try {
            btnGroup_plextr.clearSelection();
//            cbx_nalz.setSelected(false);
//            cbx_nalp.setSelected(false);
            cbx_messr.setSelected(false);
            cbx_smp.setSelected(false);
            cbx_gosp.setSelected(false);
            cbx_ber.setSelected(false);
            tf_smpn.setEnabled(false);
            tf_datap.setEnabled(true);
            tf_dataosm.setEnabled(true);
            tf_datagosp.setEnabled(false);
            tf_datasmp.setEnabled(false);
            tf_timep.setEnabled(true);
            tf_timeosm.setEnabled(true);
            tf_timegosp.setEnabled(false);
            tf_timesmp.setEnabled(false);

            tf_datap.setDate(System.currentTimeMillis());
            tf_dataosm.setDate(System.currentTimeMillis());
            tf_timep.setTime(System.currentTimeMillis());
            tf_timeosm.setTime(System.currentTimeMillis());
            tf_datagosp.setValue(null);
            tf_datasmp.setValue(null);
            tf_timegosp.setValue(null);
            tf_timesmp.setValue(null);
            tf_ntalon.setText(null);
            tf_nist.setText(null);
            tf_diag_n.setText(null);
            tf_diag_p.setText(null);
            ta_diag_n.setText(null);
            ta_diag_p.setText(null);
//            ta_jal_pr.setText(null);
//            tf_toc.setText(null);
//            tf_ad.setText(null);
            tf_smpn.setText(null);
            sp_sv_time.setValue(0);
            sp_sv_day.setValue(0);

            cmb_naprav.setSelectedIndex(-1);
            cmb_org.setSelectedItem(null);
            cmb_cotd.setSelectedIndex(-1);
//            cmb_alk.setSelectedIndex(-1);
            cmb_travm.setSelectedIndex(-1);
            cmb_trans.setSelectedIndex(-1);
            cmb_otkaz.setSelectedIndex(-1);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void SavePriemInfo(){
        try {
//			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
//			SimpleDateFormat stf = new SimpleDateFormat("HH:mm");
            Id_gosp = new Gosp();
            Id_gosp.setNpasp(curPatientId);
            Id_gosp.setNgosp(curNgosp);
            Id_gosp.setId(curId);
            Id_gosp.setDataz(new Date().getTime());

            Id_gosp.setCotd_p(MainForm.authInfo.cpodr);
            Id_gosp.setCuser(MainForm.authInfo.pcod);
            //Id_gosp.setNist(1); //подумать
            if (!tf_nist.getText().isEmpty()) Id_gosp.setNist(Integer.valueOf(tf_nist.getText())); 
            Id_gosp.setVid(1);
//		    System.out.println(((Date) sp_dataosm.getValue()).getTime());
//		    System.out.println(stf.format((Date) sp_dataosm.getValue()));
            if (tf_datap.getDate() != null) Id_gosp.setDatap(tf_datap.getDate().getTime());
            if (tf_dataosm.getDate() != null) Id_gosp.setDataosm(tf_dataosm.getDate().getTime());

            Id_gosp.setVremosm(tf_timeosm.getTime().getTime());
            Id_gosp.setVremp(tf_timep.getTime().getTime());
            if (cbx_smp.isSelected()){
                if (tf_datasmp.getDate() != null) Id_gosp.setSmp_data(tf_datasmp.getDate().getTime());
                Id_gosp.setSmp_time(tf_timesmp.getTime().getTime());
            }
            if (cbx_gosp.isSelected()){
                if (tf_datagosp.getDate() != null) Id_gosp.setDatagos(tf_datagosp.getDate().getTime());
                Id_gosp.setVremgos(tf_timegosp.getTime().getTime());
            }

            if (sp_sv_time.getValue() != null) Id_gosp.setSv_time(Integer.valueOf(sp_sv_time.getValue().toString()));
            if (sp_sv_day.getValue() != null)Id_gosp.setSv_day(Integer.valueOf(sp_sv_day.getValue().toString()));
            if (tf_diag_n.getText() != null) Id_gosp.setDiag_n(tf_diag_n.getText());
            if (tf_diag_p.getText() != null) Id_gosp.setDiag_p(tf_diag_p.getText());
            if (ta_diag_n.getText() != null) Id_gosp.setNamed_n(ta_diag_n.getText());
            if (ta_diag_p.getText() != null) Id_gosp.setNamed_p(ta_diag_p.getText());
//            if (tf_toc.getText() != null) Id_gosp.setToc(tf_toc.getText());
//            if (tf_ad.getText() != null) Id_gosp.setAd(tf_ad.getText());
//            if (ta_jal_pr.getText() != null) Id_gosp.setJalob(ta_jal_pr.getText());
            if (!tf_smpn.getText().isEmpty()) Id_gosp.setSmp_num(Integer.valueOf(tf_smpn.getText()));
            if (!tf_ntalon.getText().isEmpty()) Id_gosp.setNtalon(Integer.valueOf(tf_ntalon.getText()));

            if (rbtn_plan.isSelected()) Id_gosp.setPl_extr(2);
            if (rbtn_extr.isSelected()) Id_gosp.setPl_extr(1);

            Id_gosp.setMessr(cbx_messr.isSelected());
//            Id_gosp.setNal_z(cbx_nalz.isSelected());
//            Id_gosp.setNal_p(cbx_nalp.isSelected());
            Id_gosp.setPr_ber(cbx_ber.isSelected());

            if (cmb_travm.getSelectedItem() != null) Id_gosp.setVidtr(cmb_travm.getSelectedPcod());
            if (cmb_otkaz.getSelectedItem() != null) Id_gosp.setPr_out(cmb_otkaz.getSelectedPcod());
//            if (cmb_alk.getSelectedItem() != null) Id_gosp.setAlkg(cmb_alk.getSelectedPcod());
            if (cmb_trans.getSelectedItem() != null) Id_gosp.setVid_trans(cmb_trans.getSelectedPcod());
            if (cmb_naprav.getSelectedItem() != null) Id_gosp.setNaprav(cmb_naprav.getSelectedPcod());
            if (cmb_org.getSelectedItem() != null) Id_gosp.setN_org(cmb_org.getSelectedPcod());
            if (cmb_cotd.getSelectedItem() != null) Id_gosp.setCotd(cmb_cotd.getSelectedPcod());
            //else	Id_gosp.setCotd(Id_gosp.getCotd_p());

            //System.out.println(Id_gosp.getPr_out());
            CheckNotNullTableCgosp();
            if (curId == 0){
                curId = MainForm.tcl.addGosp(Id_gosp);
                newPriem.setId(curId);
                newPriem.setNist(Id_gosp.getNist());
                newPriem.setDatap(Id_gosp.getDatap());
                newPriem.setCotd(Id_gosp.getCotd());
                newPriem.setDiag_p(Id_gosp.getDiag_p());
                newPriem.setNamed_p(Id_gosp.getNamed_p());
                //if (Id_gosp.getCotd() != 0)
                  // 	curId_otd = MainForm.tcl.addToOtd(curId, Id_gosp.getNist(), Id_gosp.getCotd());
            }
            else{
                MainForm.tcl.updateGosp(Id_gosp);
                newPriem.setNist(Id_gosp.getNist());
                newPriem.setDatap(Id_gosp.getDatap());
                newPriem.setCotd(Id_gosp.getCotd());
                newPriem.setDiag_p(Id_gosp.getDiag_p());
                newPriem.setNamed_p(Id_gosp.getNamed_p());
                //if (Id_gosp.getCotd() != 0)
                	//MainForm.tcl.updateOtd(curId_otd, curId, Id_gosp.getNist(), Id_gosp.getCotd());
            }
            if (Id_gosp.getCotd() != 0)
               	MainForm.tcl.addOrUpdateOtd(curId, Id_gosp.getNist(), Id_gosp.getCotd());
            tbl_priem.updateChangedSelectedItem();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    @SuppressWarnings("unused")
//    private void RefreshTablePatient(){
//        try {
//            PatientBrief patBr = new PatientBrief();
//            if (!tfFam.getText().isEmpty()) patBr.setFam(tfFam.getText().toUpperCase());
//            if (!tfIm.getText().isEmpty()) patBr.setIm(tfIm.getText().toUpperCase());
//            if (!tfOt.getText().isEmpty()) patBr.setOt(tfOt.getText().toUpperCase());
//            if (!tf_oms_ser.getText().isEmpty()) patBr.setSpolis(tf_oms_ser.getText().toUpperCase());
//            if (!tf_oms_nom.getText().isEmpty()) patBr.setNpolis(tf_oms_nom.getText().toUpperCase());
//            try {
//                pat = MainForm.tcl.getAllPatientBrief(patBr);
//                refresh(pat);
//            }
//            catch (PatientNotFoundException e) {
//                JOptionPane.showMessageDialog(tfFam, "По заданным критериям сведения о пациенте отсутствуют.");
//                System.out.println(patBr.setFam(tfFam.getText())+", "+patBr.setIm(tfIm.getText())+", "+patBr.setOt(tfOt.getText()));
//                System.out.println("По заданным критериям сведения о пациенте отсутствуют.");
//            }
//        } catch (Exception e) {
//            System.out.println("эксэпшн");
//            e.printStackTrace();
//        }
//    }
    private void CheckNotNullTableCgosp(){
        try {
            String strerr = "";
            if (Id_gosp.getPl_extr() == 0)
                strerr += "плановое/экстренное; \n\r";
//            if (Id_gosp.getPl_extr() == 2 && Id_gosp.getNtalon() == 0)
//                strerr += "плановый больной без талона; \n\r";
            if (Id_gosp.getNist() == 0 && Id_gosp.getCotd() != 0)
                strerr += "отсутствует номер истории болезни; \n\r";
            if (Id_gosp.getNaprav() == null)
                strerr += "кем направлен; \n\r";
            if ((Id_gosp.getDiag_p().isEmpty()) || (Id_gosp.getNamed_p().isEmpty()))
                strerr += "диагноз приемного отделения; \n\r";
            if ((Id_gosp.getCuser() == 0) || (Id_gosp.getCotd_p() == 0))
                strerr += "нет информации о пользователе; \n\r";
            if ((Id_gosp.getDataosm() == 0) || (Id_gosp.getVremosm() == 0))
                strerr += "дата и время осмотра; \n\r";
            if (Id_gosp.getPr_out() == 0 && Id_gosp.getCotd() == 0)
                strerr += "не указаны причина отказа в госпитализации или отделение госпитализации; \n\r";
            if (Id_gosp.getPr_out() != 0 && Id_gosp.getCotd() != 0)
                strerr += "указаны причина отказа в госпитализации и отделение госпитализации; \n\r";
            if  (!strerr.isEmpty()){
                JOptionPane.showMessageDialog(tbl_priem, "Данные поля обязательно надо заполнить: \n\r"+ strerr);
                System.out.println("Данные поля обязательно надо заполнить: \n\r"+ strerr);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

