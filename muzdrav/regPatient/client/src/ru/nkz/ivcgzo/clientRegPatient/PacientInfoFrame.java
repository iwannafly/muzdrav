package ru.nkz.ivcgzo.clientRegPatient;

import java.text.SimpleDateFormat;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.TableModel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.JCheckBox;

import ru.nkz.ivcgzo.thriftRegPatient.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
//import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
//import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
//import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;

public class PacientInfoFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTabbedPane tbMain;
	private JTabbedPane tpPersonal;
	private JTabbedPane tpLgota;
	private JTabbedPane tpKateg;
	private JTabbedPane tpAgent;
	private JTabbedPane tpSign;
	private JTabbedPane tpPriem;
	private int curPatientId = 0;
	private int curNgosp = 0;
	private int curId = 0;
	private final ButtonGroup btnGroup_pol = new ButtonGroup();
	private final ButtonGroup btnGroup_gk = new ButtonGroup();
	private final ButtonGroup btnGroup_rf = new ButtonGroup();
	private final ButtonGroup btnGroup_pol_pr = new ButtonGroup();
	private final ButtonGroup btnGroup_plextr = new ButtonGroup();
	private JTextField tfFam;
	private JTextField tfIm;
	private JTextField tfOt;
	private JTextField tfDr;
	private JTextField tf_Adp_obl;
	private JTextField tf_Adp_gorod;
	private JTextField tf_Adp_ul;
	private JTextField tf_Adp_dom;
	private JTextField tf_Adp_kv;
	private JTextField tf_Adm_obl;
	private JTextField tf_Adm_gorod;
	private JTextField tf_Adm_ul;
	private JTextField tf_Adm_dom;
	private JTextField tf_Adm_kv;
	private JTextField tf_oms_ser;
	private JTextField tf_dms_ser;
	private JTextField tf_oms_nom;
	private JTextField tf_dms_nom;
	private JTextField tf_oms_smo;
	private JTextField tf_dms_smo;
	private JTextField tfMr;
	private JTextField tfMrname;
	private JTextField tfDolj;
	private JTextField tfTel;
	private JTextField tf_Cpol;
	private JTextField tf_Datapr;
	private JTextField tf_Nuch;
	private JTextField tf_Nambk;
	private JTextField tf_Dataot;
	private JTextField tf_serdoc;
	private JTextField tf_nomdoc;
	private JTextField tf_datadoc;
	private JTextField tf_Odoc;
	private JTextField tf_Snils;
	private JTextField tf_Fam_pr;
	private JTextField tf_Im_pr;
	private JTextField tf_Ot_pr;
	private JTextField tf_Dr_pr;
	private JTextField tf_Mr_pr;
	private JTextField tf_Polis_ser_pr;
	private JTextField tf_Polis_nom_pr;
	private JTextField tf_Ogrn;
	private JTextField tf_Name_sk_pr;
	private JTextField tf_Ser_doc_pr;
	private JTextField tf_Nomdoc_pr;
	private JTextField tf_ntalon;
	private JTextField tf_diag_n;
	private JTextField tf_diag_p;
	private JTextField tf_toc;
	private JTextField tf_ad;
	private JTextField tf_smpn;
	private JComboBox<String> cmb_tdoc;
	private JComboBox<String> cmb_ishod;
	private JComboBox<String> cmb_oms_doc;
	private JComboBox<String> cmb_status;
	private JComboBox<String> cmb_Tdoc_pr;
	private JComboBox<String> cmb_Polis_doc_pr;
	private JComboBox<String> cmb_cotd;
	private JComboBox<String> cmb_travm;
	private JComboBox<String> cmb_trans;
	private JComboBox<String> cmb_otkaz;
	private JComboBox<String> cmb_alk;
	private JComboBox<String> cmb_naprav;
	private JComboBox<String> cmb_org;
	private JRadioButton rbtn_pol_m;
	private JRadioButton rbtn_pol_j;
	private JRadioButton rbtn_pol_pr_m;
	private JRadioButton rbtn_pol_pr_j;
	private JRadioButton rbtn_vp1;
	private JRadioButton rbtn_vp2;
	private JRadioButton rbtn_vp3;
	private JRadioButton rbtn_rf1;
	private JRadioButton rbtn_rf2;
	private JRadioButton rbtn_gk1;
	private JRadioButton rbtn_gk2;
	private JRadioButton rbtn_gk3;
	private JRadioButton rbtn_gk4;
	private JRadioButton rbtn_plan;
	private JRadioButton rbtn_extr;
	private JTextArea ta_allerg;
	private JTextArea ta_farm;
	private JTextArea ta_vitae;
	private JTextArea ta_jal_pr;
	private JTextArea ta_diag_p;
	private JTextArea ta_diag_n;
	private JCheckBox cbx_messr;
	private JCheckBox cbx_gosp;
	private JCheckBox cbx_smp;
	private JCheckBox cbx_nalz;
	private JCheckBox cbx_nalp;
	private JSpinner sp_sv_time;
	private JSpinner sp_sv_day;
	private JSpinner sp_datagosp;
	private JSpinner sp_datasmp;
	private JSpinner sp_dataosm;
	private JSpinner sp_datap;
	//private List<PatientAllStruct> pat;
	private PatientFullInfo PersonalInfo;
	private Agent AgentInfo;
	private List<Lgota> LgotaInfo;
	private List<Kontingent> KontingentInfo;
	private Sign SignInfo;
	private Gosp Id_gospInfo;
	private List<SpravStruct> StatusList;
	private List<SpravStruct> VidPolisList;
	private List<SpravStruct> TypeDocList;
	private List<AllGosp> GospInfo;
	private TableModel tblmodel;
	private JTable tbl_patient;
	private JTable tbl_lgota;
	private JTable tbl_kateg;
	private JTable tbl_priem;
	//private CustomTable<PatientAllStruct, PatientAllStruct._Fields> tbl_patient;
	//private CustomTable<PatientAllGospInfoStruct, PatientAllGospInfoStruct._Fields> tbl_priem;
	
	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					PacientInfoFrame frame = new PacientInfoFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public void refresh(List<PatientBrief> pat) {
		tblmodel = new PatientTableModel(pat);
		tbl_patient.setModel(tblmodel);
		//tbl_patient = new CustomTable<>(false, true, PatientAllStruct.class, PatientAllStruct._Fields.values(), 2,"Фамилия",3, "Имя", 4, "Отчество",1,"ВН");
	}
	/**
	 * Create the frame.
	 */
	public PacientInfoFrame(List<PatientBrief> pat) {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1, 1, 954, 680); //ширина, высота
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		
		JTabbedPane tbMain = new JTabbedPane(JTabbedPane.TOP);
		//tbMain.setSelectedIndex(0);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 241, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tbMain, GroupLayout.PREFERRED_SIZE, 689, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 627, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
				.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
					.addGap(40)
					.addComponent(tbMain, GroupLayout.PREFERRED_SIZE, 596, GroupLayout.PREFERRED_SIZE))
		);
		
		JPanel tpPersonal = new JPanel();
		tpPersonal.setBorder(new EmptyBorder(0, 0, 0, 0));
		tbMain.addTab("Пациент", null, tpPersonal, null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "\u041E\u0431\u0449\u0430\u044F \u0438\u043D\u0444\u043E\u0440\u043C\u0430\u0446\u0438\u044F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(null, "\u0410\u0434\u0440\u0435\u0441", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "\u041C\u0435\u0434\u0438\u0446\u0438\u043D\u0441\u043A\u0438\u0439 \u043F\u043E\u043B\u0438\u0441", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "\u041C\u0435\u0441\u0442\u043E \u0440\u0430\u0431\u043E\u0442\u044B", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, "\u041F\u0440\u0438\u043A\u0440\u0435\u043F\u043B\u0435\u043D\u0438\u0435", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_7 = new JPanel();
		panel_7.setBorder(new TitledBorder(null, "\u0414\u043E\u043A\u0443\u043C\u0435\u043D\u0442, \u0443\u0434\u043E\u0441\u0442\u043E\u0432\u0435\u0440\u044F\u044E\u0449\u0438\u0439 \u043B\u0438\u0447\u043D\u043E\u0441\u0442\u044C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_tpPersonal = new GroupLayout(tpPersonal);
		gl_tpPersonal.setHorizontalGroup(
			gl_tpPersonal.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_tpPersonal.createSequentialGroup()
					.addGroup(gl_tpPersonal.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 682, Short.MAX_VALUE)
						.addGroup(gl_tpPersonal.createSequentialGroup()
							.addGap(1)
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 680, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_tpPersonal.createSequentialGroup()
							.addGap(3)
							.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 679, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_tpPersonal.createSequentialGroup()
							.addGap(3)
							.addGroup(gl_tpPersonal.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_6, GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
								.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(panel_7, GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE))))
					.addContainerGap())
		);
		gl_tpPersonal.setVerticalGroup(
			gl_tpPersonal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tpPersonal.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 120, GroupLayout.PREFERRED_SIZE)
					.addGap(2)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 94, GroupLayout.PREFERRED_SIZE)
					.addGap(2)
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_7, GroupLayout.DEFAULT_SIZE, 73, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		cmb_tdoc = new JComboBox<String>();
		
		JLabel lblNewLabel_27 = new JLabel("Документ");
		
		JLabel lblNewLabel_28 = new JLabel("Серия");
		
		tf_serdoc = new JTextField();
		tf_serdoc.setColumns(10);
		
		JLabel lblNewLabel_29 = new JLabel("Номер");
		
		tf_nomdoc = new JTextField();
		tf_nomdoc.setColumns(10);
		
		JLabel lblNewLabel_30 = new JLabel("Дата выдачи");
		
		tf_datadoc = new JTextField();
		tf_datadoc.setColumns(10);
		
		tf_Odoc = new JTextField();
		tf_Odoc.setColumns(10);
		
		JLabel lblNewLabel_31 = new JLabel("Кем выдан");
		
		JLabel lblNewLabel_32 = new JLabel("СНИЛС");
		
		tf_Snils = new JTextField();
		tf_Snils.setColumns(10);
		GroupLayout gl_panel_7 = new GroupLayout(panel_7);
		gl_panel_7.setHorizontalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_27)
						.addComponent(lblNewLabel_31))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_7.createSequentialGroup()
							.addComponent(cmb_tdoc, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblNewLabel_28)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_serdoc, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblNewLabel_29)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_nomdoc, GroupLayout.PREFERRED_SIZE, 63, GroupLayout.PREFERRED_SIZE))
						.addComponent(tf_Odoc))
					.addGap(18)
					.addGroup(gl_panel_7.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_30)
						.addComponent(lblNewLabel_32))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
						.addComponent(tf_datadoc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_Snils, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(31, Short.MAX_VALUE))
		);
		gl_panel_7.setVerticalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addGroup(gl_panel_7.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_27)
						.addComponent(cmb_tdoc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_28)
						.addComponent(tf_serdoc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_29)
						.addComponent(tf_nomdoc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_30)
						.addComponent(tf_datadoc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel_7.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_Odoc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_31)
						.addComponent(lblNewLabel_32)
						.addComponent(tf_Snils, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel_7.setLayout(gl_panel_7);
		
		tf_Cpol = new JTextField();
		tf_Cpol.setColumns(10);
		
		JLabel lblNewLabel_21 = new JLabel("Поликлиника");
		
		tf_Datapr = new JTextField();
		tf_Datapr.setColumns(10);
		
		JLabel lblNewLabel_22 = new JLabel("Дата прикрепления");
		
		JLabel lblNewLabel_23 = new JLabel("№ участка");
		
		tf_Nuch = new JTextField();
		tf_Nuch.setText("");
		tf_Nuch.setColumns(10);
		
		JLabel lblNewLabel_24 = new JLabel("№ амб. карты");
		
		tf_Nambk = new JTextField();
		tf_Nambk.setColumns(10);
		
		JLabel lblNewLabel_25 = new JLabel("Дата открепления");
		
		tf_Dataot = new JTextField();
		tf_Dataot.setColumns(10);
		
		JLabel lblNewLabel_26 = new JLabel("Причина");
		
		cmb_ishod = new JComboBox<String>();
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel_6.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_21)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_Cpol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_6.createSequentialGroup()
							.addComponent(lblNewLabel_22)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_Datapr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_6.createSequentialGroup()
							.addComponent(lblNewLabel_23)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_Nuch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblNewLabel_24)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_Nambk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_6.createSequentialGroup()
							.addComponent(lblNewLabel_25)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_Dataot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblNewLabel_26)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmb_ishod, 0, 184, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_Cpol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_21)
						.addComponent(lblNewLabel_23)
						.addComponent(tf_Nuch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_24)
						.addComponent(tf_Nambk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_6.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_22)
						.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
							.addComponent(tf_Datapr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_25)
							.addComponent(tf_Dataot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNewLabel_26)
							.addComponent(cmb_ishod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_6.setLayout(gl_panel_6);
		
		tfMr = new JTextField();
		tfMr.setColumns(10);
		
		JLabel lblNewLabel_18 = new JLabel("Место работы");
		
		tfMrname = new JTextField();
		tfMrname.setColumns(10);
		
		tfDolj = new JTextField();
		tfDolj.setColumns(10);
		
		JLabel lblNewLabel_19 = new JLabel("Должность");
		
		JLabel lblNewLabel_20 = new JLabel("Телефон");
		
		tfTel = new JTextField();
		tfTel.setColumns(10);
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_19)
						.addComponent(lblNewLabel_18))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_5.createSequentialGroup()
							.addComponent(tfDolj, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblNewLabel_20)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfTel, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_5.createSequentialGroup()
							.addComponent(tfMr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfMrname, GroupLayout.PREFERRED_SIZE, 399, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(96, Short.MAX_VALUE))
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_18)
						.addComponent(tfMr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tfMrname, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfDolj, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_19)
						.addComponent(lblNewLabel_20)
						.addComponent(tfTel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		panel_5.setLayout(gl_panel_5);
		
		JLabel lblNewLabel_12 = new JLabel("Серия");
		
		tf_oms_ser = new JTextField();
		tf_oms_ser.setColumns(10);
		
		tf_dms_ser = new JTextField();
		tf_dms_ser.setColumns(10);
		
		tf_oms_nom = new JTextField();
		tf_oms_nom.setColumns(10);
		
		tf_dms_nom = new JTextField();
		tf_dms_nom.setColumns(10);
		
		tf_oms_smo = new JTextField();
		tf_oms_smo.setColumns(10);
		
		tf_dms_smo = new JTextField();
		tf_dms_smo.setColumns(10);
		
		cmb_oms_doc = new JComboBox<String>();
		
		JLabel lblNewLabel_13 = new JLabel("ОМС");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblNewLabel_14 = new JLabel("ДМС");
		lblNewLabel_14.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblNewLabel_15 = new JLabel("Номер");
		
		JLabel lblNewLabel_16 = new JLabel("СМО");
		
		JLabel lblNewLabel_17 = new JLabel("Документ");
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_14)
						.addComponent(lblNewLabel_13))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(tf_dms_ser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_dms_nom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_dms_smo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(tf_oms_ser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_12))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(tf_oms_nom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_15))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(tf_oms_smo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_16))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_17)
								.addComponent(cmb_oms_doc, GroupLayout.PREFERRED_SIZE, 248, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(92, Short.MAX_VALUE))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_12)
						.addComponent(lblNewLabel_15)
						.addComponent(lblNewLabel_16)
						.addComponent(lblNewLabel_17))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_oms_ser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_oms_nom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_oms_smo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cmb_oms_doc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_13))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_dms_ser, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_dms_nom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_dms_smo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_14))
					.addGap(30))
		);
		panel_4.setLayout(gl_panel_4);
		
		JLabel lblNewLabel_5 = new JLabel("Прописка");
		lblNewLabel_5.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblNewLabel_6 = new JLabel("Проживает");
		lblNewLabel_6.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblNewLabel_7 = new JLabel("Область(край, республика)");
		
		JLabel lblNewLabel_8 = new JLabel("Город (село)");
		
		JLabel lblNewLabel_9 = new JLabel("Улица");
		
		tf_Adp_obl = new JTextField();
		tf_Adp_obl.setColumns(10);
		
		tf_Adp_gorod = new JTextField();
		tf_Adp_gorod.setColumns(10);
		
		tf_Adp_ul = new JTextField();
		tf_Adp_ul.setColumns(10);
		
		JLabel lblNewLabel_10 = new JLabel("Дом");
		
		JLabel lblNewLabel_11 = new JLabel("Кв.");
		
		tf_Adp_dom = new JTextField();
		tf_Adp_dom.setColumns(10);
		
		tf_Adp_kv = new JTextField();
		tf_Adp_kv.setColumns(10);
		
		tf_Adm_obl = new JTextField();
		tf_Adm_obl.setColumns(10);
		
		tf_Adm_gorod = new JTextField();
		tf_Adm_gorod.setColumns(10);
		
		tf_Adm_ul = new JTextField();
		tf_Adm_ul.setColumns(10);
		
		tf_Adm_dom = new JTextField();
		tf_Adm_dom.setColumns(10);
		
		tf_Adm_kv = new JTextField();
		tf_Adm_kv.setColumns(10);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(82)
							.addComponent(lblNewLabel_7)
							.addGap(18)
							.addComponent(lblNewLabel_8)
							.addGap(105)
							.addComponent(lblNewLabel_9)
							.addGap(139)
							.addComponent(lblNewLabel_10)
							.addGap(31)
							.addComponent(lblNewLabel_11))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(10)
							.addComponent(lblNewLabel_5)
							.addGap(18)
							.addComponent(tf_Adp_obl, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(tf_Adp_gorod, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(tf_Adp_ul, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(tf_Adp_dom, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(tf_Adp_kv, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(10)
							.addComponent(lblNewLabel_6)
							.addGap(6)
							.addComponent(tf_Adm_obl, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(tf_Adm_gorod, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(tf_Adm_ul, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(tf_Adm_dom, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addGap(3)
							.addComponent(tf_Adm_kv, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
					.addGap(6))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_7)
						.addComponent(lblNewLabel_8)
						.addComponent(lblNewLabel_9)
						.addComponent(lblNewLabel_10)
						.addComponent(lblNewLabel_11))
					.addGap(6)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel_5))
						.addComponent(tf_Adp_obl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_Adp_gorod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_Adp_ul, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_Adp_dom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_Adp_kv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(tf_Adm_kv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel_6))
						.addComponent(tf_Adm_obl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_Adm_gorod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_Adm_ul, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_Adm_dom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel_3.setLayout(gl_panel_3);
		
		tfFam = new JTextField();
		tfFam.setColumns(10);
		
		tfIm = new JTextField();
		tfIm.setColumns(10);
		
		tfOt = new JTextField();
		tfOt.setColumns(10);
		
		tfDr = new JTextField();
		tfDr.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Дата рождения");
		
		JLabel lblNewLabel_1 = new JLabel("Фамилия");
		
		JLabel lblNewLabel_2 = new JLabel("Имя");
		
		JLabel lblNewLabel_3 = new JLabel("Отчество");
		
		JLabel lblNewLabel_4 = new JLabel("Социальный статус");
		
		cmb_status = new JComboBox<String>();
		
		JLabel lblPol = new JLabel("Пол");
		
		rbtn_pol_m = new JRadioButton("мужской");
		btnGroup_pol.add(rbtn_pol_m);
		
		rbtn_pol_j = new JRadioButton("женский");
		btnGroup_pol.add(rbtn_pol_j);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(50)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_1)
						.addComponent(lblNewLabel_2)
						.addComponent(lblNewLabel_3))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(tfDr, Alignment.LEADING)
						.addComponent(tfOt, Alignment.LEADING)
						.addComponent(tfIm, Alignment.LEADING)
						.addComponent(tfFam, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE))
					.addGap(31)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_4)
						.addComponent(lblPol))
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
							.addComponent(cmb_status, GroupLayout.PREFERRED_SIZE, 256, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(rbtn_pol_m)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rbtn_pol_j)
							.addGap(71))))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfFam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfIm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2)
						.addComponent(lblNewLabel_4)
						.addComponent(cmb_status, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfOt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfDr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel)
						.addComponent(lblPol)
						.addComponent(rbtn_pol_m)
						.addComponent(rbtn_pol_j)))
		);
		panel_2.setLayout(gl_panel_2);
		tpPersonal.setLayout(gl_tpPersonal);
		
		JPanel tpLgota = new JPanel();
		tbMain.addTab("Льгота", null, tpLgota, null);
		
		JPanel panel_9 = new JPanel();
		
		JPanel panel_10 = new JPanel();
		GroupLayout gl_tpLgota = new GroupLayout(tpLgota);
		gl_tpLgota.setHorizontalGroup(
			gl_tpLgota.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_tpLgota.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_tpLgota.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_10, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
						.addComponent(panel_9, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_tpLgota.setVerticalGroup(
			gl_tpLgota.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tpLgota.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_9, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_10, GroupLayout.PREFERRED_SIZE, 179, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(339, Short.MAX_VALUE))
		);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_panel_10 = new GroupLayout(panel_10);
		gl_panel_10.setHorizontalGroup(
			gl_panel_10.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_10.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_10.setVerticalGroup(
			gl_panel_10.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_10.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 157, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tbl_lgota = new JTable();
		scrollPane_1.setViewportView(tbl_lgota);
		panel_10.setLayout(gl_panel_10);
		
		JButton btnDel_lgt = new JButton("Удалить");
		btnDel_lgt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					curPatientId =(int) tbl_lgota.getModel().getValueAt(tbl_lgota.getSelectedRow(),3);
					short lgt = (short) tbl_lgota.getModel().getValueAt(tbl_lgota.getSelectedRow(),1);
				    MainForm.tcl.deleteLgota(curPatientId, lgt);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		JButton btnAdd_lgt = new JButton("Добавить");
		btnAdd_lgt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					curPatientId =(int) tbl_patient.getModel().getValueAt(tbl_patient.getSelectedRow(),3);
					//здесь надо добавить пустую строку в табл tbl_lgota
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		JButton btnSave_lgt = new JButton("Сохранить");
		btnSave_lgt.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					short lgt = (short) tbl_lgota.getModel().getValueAt(tbl_lgota.getSelectedRow(),1);
				    MainForm.tcl.updateLgota(curPatientId, lgt);
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
					.addGap(18)
					.addComponent(btnSave_lgt)
					.addContainerGap(359, Short.MAX_VALUE))
		);
		gl_panel_9.setVerticalGroup(
			gl_panel_9.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_9.createSequentialGroup()
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
				.addGroup(Alignment.TRAILING, gl_tpKateg.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_tpKateg.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_12, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
						.addComponent(panel_11, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_tpKateg.setVerticalGroup(
			gl_tpKateg.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tpKateg.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_11, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_12, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(338, Short.MAX_VALUE))
		);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GroupLayout gl_panel_12 = new GroupLayout(panel_12);
		gl_panel_12.setHorizontalGroup(
			gl_panel_12.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_12.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 652, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_12.setVerticalGroup(
			gl_panel_12.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_12.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tbl_kateg = new JTable();
		scrollPane_2.setViewportView(tbl_kateg);
		panel_12.setLayout(gl_panel_12);
		
		JButton btnDel_kat = new JButton("Удалить");
		btnDel_kat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					curPatientId =(int) tbl_kateg.getModel().getValueAt(tbl_kateg.getSelectedRow(),3);
					short kat = (short) tbl_kateg.getModel().getValueAt(tbl_kateg.getSelectedRow(),1);
				    MainForm.tcl.deleteKont(curPatientId, kat);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		JButton btnAdd_kat = new JButton("Добавить");
		btnAdd_kat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					curPatientId =(int) tbl_patient.getModel().getValueAt(tbl_patient.getSelectedRow(),3);
					//здесь надо добавить пустую строку в табл tbl_kateg
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		JButton btnSave_kat = new JButton("Сохранить");
		btnSave_kat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					short kat = (short) tbl_kateg.getModel().getValueAt(tbl_kateg.getSelectedRow(),1);
				    MainForm.tcl.updateKont(curPatientId, kat);
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
					.addContainerGap(359, Short.MAX_VALUE))
		);
		gl_panel_11.setVerticalGroup(
			gl_panel_11.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_11.createSequentialGroup()
					.addGroup(gl_panel_11.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDel_kat)
						.addComponent(btnAdd_kat)
						.addComponent(btnSave_kat))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_11.setLayout(gl_panel_11);
		tpKateg.setLayout(gl_tpKateg);
		
		JPanel tpAgent = new JPanel();
		tbMain.addTab("Представитель", null, tpAgent, null);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Общая информация", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_20 = new JPanel();
		panel_20.setBorder(new TitledBorder(null, "Медицинский полис", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_21 = new JPanel();
		panel_21.setBorder(new TitledBorder(null, "Документ, удостоверяющий личность", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_22 = new JPanel();
		GroupLayout gl_tpAgent = new GroupLayout(tpAgent);
		gl_tpAgent.setHorizontalGroup(
			gl_tpAgent.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_tpAgent.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_tpAgent.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_22, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
						.addComponent(panel_21, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
						.addComponent(panel_20, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
						.addComponent(panel_8, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_tpAgent.setVerticalGroup(
			gl_tpAgent.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tpAgent.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_8, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_20, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_21, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 200, Short.MAX_VALUE)
					.addComponent(panel_22, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JButton btnSave_agent = new JButton("Сохранить");
		btnSave_agent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
			        AgentInfo = new Agent();
					curPatientId =(int) tbl_patient.getModel().getValueAt(tbl_patient.getSelectedRow(),3);
					AgentInfo.npasp = curPatientId;
					AgentInfo.fam = tf_Fam_pr.getText().trim();
					AgentInfo.im = tf_Im_pr.getText().trim();
					AgentInfo.ot = tf_Ot_pr.getText().trim();
					AgentInfo.datar = Date.parse(tf_Dr_pr.getText());
					AgentInfo.birthplace = tf_Mr_pr.getText().trim();
					AgentInfo.spolis = tf_Polis_ser_pr.getText().trim();
					AgentInfo.npolis = tf_Polis_nom_pr.getText().trim();
					AgentInfo.name_str = tf_Name_sk_pr.getText().trim();
					AgentInfo.ogrn_str = tf_Ogrn.getText().trim();
					AgentInfo.docser = tf_Ser_doc_pr.getText().trim();
					AgentInfo.docnum = tf_Nomdoc_pr.getText().trim();
					if (rbtn_pol_pr_m.isSelected()) {
						AgentInfo.pol = 1;
					}
					if (rbtn_pol_pr_j.isSelected()) {
						AgentInfo.pol = 2;
					}
					MainForm.tcl.updateAgent(AgentInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		JButton btnDel_agent = new JButton("Удалить");
		btnDel_agent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					curPatientId =(int) tbl_patient.getModel().getValueAt(tbl_patient.getSelectedRow(),3);
				    MainForm.tcl.deleteAgent(curPatientId);
					tf_Fam_pr.setText(null);
					tf_Im_pr.setText(null);
					tf_Ot_pr.setText(null);
					tf_Dr_pr.setText(null);
					tf_Mr_pr.setText(null);
					tf_Polis_ser_pr.setText(null);
					tf_Polis_nom_pr.setText(null);
					tf_Name_sk_pr.setText(null);
					tf_Ogrn.setText(null);
					tf_Ser_doc_pr.setText(null);
					tf_Nomdoc_pr.setText(null);
					rbtn_pol_pr_m.setSelected(false);
					rbtn_pol_pr_j.setSelected(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		GroupLayout gl_panel_22 = new GroupLayout(panel_22);
		gl_panel_22.setHorizontalGroup(
			gl_panel_22.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_22.createSequentialGroup()
					.addContainerGap(470, Short.MAX_VALUE)
					.addComponent(btnSave_agent)
					.addGap(18)
					.addComponent(btnDel_agent)
					.addGap(6))
		);
		gl_panel_22.setVerticalGroup(
			gl_panel_22.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_22.createSequentialGroup()
					.addGroup(gl_panel_22.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave_agent)
						.addComponent(btnDel_agent))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_22.setLayout(gl_panel_22);
		
		JLabel lblNewLabel_45 = new JLabel("Документ");
		
		cmb_Tdoc_pr = new JComboBox<String>();
		
		JLabel lblNewLabel_46 = new JLabel("Серия");
		
		tf_Ser_doc_pr = new JTextField();
		tf_Ser_doc_pr.setColumns(10);
		
		JLabel lblNewLabel_47 = new JLabel("Номер");
		
		tf_Nomdoc_pr = new JTextField();
		tf_Nomdoc_pr.setColumns(10);
		GroupLayout gl_panel_21 = new GroupLayout(panel_21);
		gl_panel_21.setHorizontalGroup(
			gl_panel_21.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_21.createSequentialGroup()
					.addComponent(lblNewLabel_45)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cmb_Tdoc_pr, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblNewLabel_46)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tf_Ser_doc_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblNewLabel_47)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tf_Nomdoc_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(125, Short.MAX_VALUE))
		);
		gl_panel_21.setVerticalGroup(
			gl_panel_21.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_21.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel_21.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_45)
						.addComponent(cmb_Tdoc_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_46)
						.addComponent(tf_Ser_doc_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_47)
						.addComponent(tf_Nomdoc_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel_21.setLayout(gl_panel_21);
		
		JLabel lblNewLabel_39 = new JLabel("ОМС");
		lblNewLabel_39.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblNewLabel_40 = new JLabel("Серия");
		
		tf_Polis_ser_pr = new JTextField();
		tf_Polis_ser_pr.setColumns(10);
		
		JLabel lblNewLabel_41 = new JLabel("Номер");
		
		tf_Polis_nom_pr = new JTextField();
		tf_Polis_nom_pr.setColumns(10);
		
		cmb_Polis_doc_pr = new JComboBox<String>();
		
		JLabel lblNewLabel_42 = new JLabel("Документ");
		
		tf_Ogrn = new JTextField();
		tf_Ogrn.setColumns(10);
		
		JLabel lblNewLabel_43 = new JLabel("ОГРН СК");
		
		JLabel lblNewLabel_44 = new JLabel("Наименование СК");
		
		tf_Name_sk_pr = new JTextField();
		tf_Name_sk_pr.setColumns(10);
		GroupLayout gl_panel_20 = new GroupLayout(panel_20);
		gl_panel_20.setHorizontalGroup(
			gl_panel_20.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_20.createSequentialGroup()
					.addGroup(gl_panel_20.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_20.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel_39)
							.addGap(18)
							.addGroup(gl_panel_20.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_20.createSequentialGroup()
									.addComponent(tf_Polis_ser_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(tf_Polis_nom_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_20.createSequentialGroup()
									.addComponent(lblNewLabel_40)
									.addGap(69)
									.addComponent(lblNewLabel_41)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_20.createParallelGroup(Alignment.LEADING)
								.addComponent(cmb_Polis_doc_pr, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_42)))
						.addGroup(gl_panel_20.createSequentialGroup()
							.addComponent(lblNewLabel_44)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_Name_sk_pr)))
					.addGap(18)
					.addGroup(gl_panel_20.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_43)
						.addComponent(tf_Ogrn, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(74, Short.MAX_VALUE))
		);
		gl_panel_20.setVerticalGroup(
			gl_panel_20.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_20.createSequentialGroup()
					.addGroup(gl_panel_20.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_40)
						.addComponent(lblNewLabel_41)
						.addComponent(lblNewLabel_42)
						.addComponent(lblNewLabel_43))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_20.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_Polis_ser_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_Polis_nom_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cmb_Polis_doc_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_Ogrn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_39))
					.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
					.addGroup(gl_panel_20.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_44)
						.addComponent(tf_Name_sk_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		panel_20.setLayout(gl_panel_20);
		
		tf_Fam_pr = new JTextField();
		tf_Fam_pr.setColumns(10);
		
		JLabel lblNewLabel_33 = new JLabel("Фамилия");
		
		tf_Im_pr = new JTextField();
		tf_Im_pr.setColumns(10);
		
		tf_Ot_pr = new JTextField();
		tf_Ot_pr.setColumns(10);
		
		tf_Dr_pr = new JTextField();
		tf_Dr_pr.setColumns(10);
		
		JLabel lblNewLabel_34 = new JLabel("Имя");
		
		JLabel lblNewLabel_35 = new JLabel("Отчество");
		
		JLabel lblNewLabel_36 = new JLabel("Дата рождения");
		
		tf_Mr_pr = new JTextField();
		tf_Mr_pr.setColumns(10);
		
		JLabel lblNewLabel_37 = new JLabel("Место рождения");
		
		JLabel lblNewLabel_38 = new JLabel("Пол");
		
		rbtn_pol_pr_m = new JRadioButton("мужской");
		btnGroup_pol_pr.add(rbtn_pol_pr_m);
		
		rbtn_pol_pr_j = new JRadioButton("женский");
		btnGroup_pol_pr.add(rbtn_pol_pr_j);
		GroupLayout gl_panel_8 = new GroupLayout(panel_8);
		gl_panel_8.setHorizontalGroup(
			gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_8.createSequentialGroup()
					.addGap(42)
					.addGroup(gl_panel_8.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_33)
						.addComponent(lblNewLabel_34)
						.addComponent(lblNewLabel_35)
						.addComponent(lblNewLabel_36)
						.addComponent(lblNewLabel_37))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_8.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_8.createSequentialGroup()
							.addComponent(tf_Dr_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(100)
							.addComponent(lblNewLabel_38)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(rbtn_pol_pr_m)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rbtn_pol_pr_j))
						.addComponent(tf_Ot_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_Im_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_Fam_pr, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_Mr_pr, GroupLayout.PREFERRED_SIZE, 441, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(84, Short.MAX_VALUE))
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
						.addComponent(tf_Dr_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_36)
						.addComponent(lblNewLabel_38)
						.addComponent(rbtn_pol_pr_m)
						.addComponent(rbtn_pol_pr_j))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_8.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_Mr_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_37))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_8.setLayout(gl_panel_8);
		tpAgent.setLayout(gl_tpAgent);
		
		JPanel tpSign = new JPanel();
		tbMain.addTab("Сигнальные отметки", null, tpSign, null);

		JPanel panel_13 = new JPanel();
		panel_13.setBorder(new TitledBorder(null, "\u0413\u0440\u0443\u043F\u043F\u0430 \u043A\u0440\u043E\u0432\u0438", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_14 = new JPanel();
		panel_14.setBorder(new TitledBorder(null, "\u0420\u0435\u0437\u0443\u0441 \u0444\u0430\u043A\u0442\u043E\u0440", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_15 = new JPanel();
		panel_15.setBorder(new TitledBorder(null, "\u0410\u043B\u043B\u0435\u0440\u0433\u043E-\u0430\u043D\u0430\u043C\u043D\u0435\u0437", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_16 = new JPanel();
		panel_16.setBorder(new TitledBorder(null, "\u0424\u0430\u0440\u043C\u0430\u043A\u043E\u043B\u043E\u0433\u0438\u0447\u0435\u0441\u043A\u0438\u0439 \u0430\u043D\u0430\u043C\u043D\u0435\u0437", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_17 = new JPanel();
		panel_17.setBorder(new TitledBorder(null, "\u0410\u043D\u0430\u043C\u043D\u0435\u0437 \u0436\u0438\u0437\u043D\u0438", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_18 = new JPanel();
		panel_18.setBorder(new TitledBorder(null, "\u0412\u0440\u0435\u0434\u043D\u044B\u0435 \u043F\u0440\u0438\u0432\u044B\u0447\u043A\u0438", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_19 = new JPanel();
		GroupLayout gl_tpSign = new GroupLayout(tpSign);
		gl_tpSign.setHorizontalGroup(
			gl_tpSign.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tpSign.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_tpSign.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_tpSign.createSequentialGroup()
							.addComponent(panel_19, GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_tpSign.createSequentialGroup()
							.addComponent(panel_13, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(panel_14, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(panel_18, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_tpSign.createSequentialGroup()
							.addComponent(panel_15, GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
							.addGap(158))
						.addGroup(gl_tpSign.createSequentialGroup()
							.addComponent(panel_16, GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
							.addGap(158))
						.addGroup(gl_tpSign.createSequentialGroup()
							.addComponent(panel_17, GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
							.addGap(158))))
		);
		gl_tpSign.setVerticalGroup(
			gl_tpSign.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tpSign.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_tpSign.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_tpSign.createParallelGroup(Alignment.BASELINE, false)
							.addComponent(panel_14, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addComponent(panel_18, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel_13, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_15, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_16, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_17, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
					.addGap(153)
					.addComponent(panel_19, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JButton btnSave_Sign = new JButton("Сохранить");
		btnSave_Sign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
			        SignInfo = new Sign();
					curPatientId =(int) tbl_patient.getModel().getValueAt(tbl_patient.getSelectedRow(),3);
					SignInfo.npasp = curPatientId;
					if (rbtn_gk1.isSelected()) {
						SignInfo.grup = "1";
					}
					if (rbtn_gk2.isSelected()) {
						SignInfo.grup = "2";
					}
					if (rbtn_gk3.isSelected()) {
						SignInfo.grup = "3";
					}
					if (rbtn_gk4.isSelected()) {
						SignInfo.grup = "4";
					}
					if (rbtn_rf1.isSelected()) {
						SignInfo.ph = "+";
					}
					if (rbtn_rf2.isSelected()) {
						SignInfo.ph = "-";
					}
					if (rbtn_vp1.isSelected()) {
						SignInfo.vred += "1";
					}else {
						SignInfo.vred += "0";
					}
					if (rbtn_vp2.isSelected()) {
						SignInfo.vred += "1";
					}else {
						SignInfo.vred += "0";
					}
					if (rbtn_vp3.isSelected()) {
						SignInfo.vred += "1";
					}else {
						SignInfo.vred += "0";
					}
					SignInfo.allerg = ta_allerg.getText().trim();
					SignInfo.farmkol = ta_farm.getText().trim();
					SignInfo.vitae = ta_vitae.getText().trim();
					MainForm.tcl.updateSign(SignInfo);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		JButton btnDel_Sign = new JButton("Удалить");
		btnDel_Sign.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					curPatientId =(int) tbl_patient.getModel().getValueAt(tbl_patient.getSelectedRow(),3);
				    MainForm.tcl.deleteSign(curPatientId);
					rbtn_gk1.setSelected(false);
					rbtn_gk2.setSelected(false);
					rbtn_gk3.setSelected(false);
					rbtn_gk4.setSelected(false);
					rbtn_rf1.setSelected(false);
					rbtn_rf2.setSelected(false);
					rbtn_vp1.setSelected(false);
					rbtn_vp2.setSelected(false);
					rbtn_vp3.setSelected(false);
					ta_allerg.setText(null);
					ta_farm.setText(null);
					ta_vitae.setText(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		GroupLayout gl_panel_19 = new GroupLayout(panel_19);
		gl_panel_19.setHorizontalGroup(
			gl_panel_19.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_19.createSequentialGroup()
					.addContainerGap(472, Short.MAX_VALUE)
					.addComponent(btnSave_Sign)
					.addGap(18)
					.addComponent(btnDel_Sign)
					.addGap(4))
		);
		gl_panel_19.setVerticalGroup(
			gl_panel_19.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_19.createSequentialGroup()
					.addGroup(gl_panel_19.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSave_Sign)
						.addComponent(btnDel_Sign))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_19.setLayout(gl_panel_19);
		
		rbtn_vp1= new JRadioButton("курение");
		
		rbtn_vp2 = new JRadioButton("алкоголь");
		
		rbtn_vp3 = new JRadioButton("наркотики");
		GroupLayout gl_panel_18 = new GroupLayout(panel_18);
		gl_panel_18.setHorizontalGroup(
			gl_panel_18.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_18.createSequentialGroup()
					.addContainerGap()
					.addComponent(rbtn_vp1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rbtn_vp2)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtn_vp3)
					.addContainerGap(30, Short.MAX_VALUE))
		);
		gl_panel_18.setVerticalGroup(
			gl_panel_18.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_18.createSequentialGroup()
					.addGroup(gl_panel_18.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtn_vp1)
						.addComponent(rbtn_vp2)
						.addComponent(rbtn_vp3))
					.addGap(49))
		);
		panel_18.setLayout(gl_panel_18);
		
		ta_allerg = new JTextArea();
		GroupLayout gl_panel_15 = new GroupLayout(panel_15);
		gl_panel_15.setHorizontalGroup(
			gl_panel_15.createParallelGroup(Alignment.LEADING)
				.addComponent(ta_allerg, GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
		);
		gl_panel_15.setVerticalGroup(
			gl_panel_15.createParallelGroup(Alignment.LEADING)
				.addComponent(ta_allerg, GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
		);
		panel_15.setLayout(gl_panel_15);
		
		ta_farm = new JTextArea();
		GroupLayout gl_panel_16 = new GroupLayout(panel_16);
		gl_panel_16.setHorizontalGroup(
			gl_panel_16.createParallelGroup(Alignment.LEADING)
				.addComponent(ta_farm, GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
		);
		gl_panel_16.setVerticalGroup(
			gl_panel_16.createParallelGroup(Alignment.LEADING)
				.addComponent(ta_farm, GroupLayout.DEFAULT_SIZE, 67, Short.MAX_VALUE)
		);
		panel_16.setLayout(gl_panel_16);
		
		ta_vitae = new JTextArea();
		GroupLayout gl_panel_17 = new GroupLayout(panel_17);
		gl_panel_17.setHorizontalGroup(
			gl_panel_17.createParallelGroup(Alignment.LEADING)
				.addComponent(ta_vitae, GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
		);
		gl_panel_17.setVerticalGroup(
			gl_panel_17.createParallelGroup(Alignment.LEADING)
				.addComponent(ta_vitae, GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
		);
		panel_17.setLayout(gl_panel_17);
		
		rbtn_rf1 = new JRadioButton("+");
		btnGroup_rf.add(rbtn_rf1);
		
		rbtn_rf2 = new JRadioButton("-");
		btnGroup_rf.add(rbtn_rf2);
		GroupLayout gl_panel_14 = new GroupLayout(panel_14);
		gl_panel_14.setHorizontalGroup(
			gl_panel_14.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_14.createSequentialGroup()
					.addComponent(rbtn_rf1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtn_rf2)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_14.setVerticalGroup(
			gl_panel_14.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_14.createSequentialGroup()
					.addGroup(gl_panel_14.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtn_rf1)
						.addComponent(rbtn_rf2))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_14.setLayout(gl_panel_14);
		
		rbtn_gk1 = new JRadioButton("I");
		btnGroup_gk.add(rbtn_gk1);
		
		rbtn_gk2 = new JRadioButton("II");
		btnGroup_gk.add(rbtn_gk2);
		
		rbtn_gk3 = new JRadioButton("III");
		btnGroup_gk.add(rbtn_gk3);
		
		rbtn_gk4 = new JRadioButton("IV");
		btnGroup_gk.add(rbtn_gk4);
		GroupLayout gl_panel_13 = new GroupLayout(panel_13);
		gl_panel_13.setHorizontalGroup(
			gl_panel_13.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_13.createSequentialGroup()
					.addComponent(rbtn_gk1)
					.addGap(10)
					.addComponent(rbtn_gk2)
					.addGap(4)
					.addComponent(rbtn_gk3)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbtn_gk4)
					.addContainerGap(37, Short.MAX_VALUE))
		);
		gl_panel_13.setVerticalGroup(
			gl_panel_13.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_13.createSequentialGroup()
					.addGroup(gl_panel_13.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtn_gk1)
						.addComponent(rbtn_gk2)
						.addComponent(rbtn_gk3)
						.addComponent(rbtn_gk4))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_13.setLayout(gl_panel_13);
		tpSign.setLayout(gl_tpSign);
		
		JPanel tpPriem = new JPanel();
		tbMain.addTab("Приемное отделение", null, tpPriem, null);
		
		JPanel panel_23 = new JPanel();
		panel_23.setBorder(new TitledBorder(null, "Обращения", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_24 = new JPanel();
		
		JPanel panel_25 = new JPanel();
		panel_25.setBorder(new TitledBorder(null, "Обращение в приемное отделение", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_34 = new JPanel();
		panel_34.setBorder(new TitledBorder(null, "Госпитализация", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_tpPriem = new GroupLayout(tpPriem);
		gl_tpPriem.setHorizontalGroup(
			gl_tpPriem.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tpPriem.createSequentialGroup()
					.addGroup(gl_tpPriem.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_tpPriem.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_tpPriem.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_24, GroupLayout.DEFAULT_SIZE, 679, Short.MAX_VALUE)
								.addComponent(panel_23, GroupLayout.PREFERRED_SIZE, 679, GroupLayout.PREFERRED_SIZE)))
						.addComponent(panel_25, GroupLayout.PREFERRED_SIZE, 689, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_tpPriem.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_34, GroupLayout.PREFERRED_SIZE, 679, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_tpPriem.setVerticalGroup(
			gl_tpPriem.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tpPriem.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_23, GroupLayout.PREFERRED_SIZE, 108, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_24, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_25, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(panel_34, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addGap(0, 0, Short.MAX_VALUE))
		);
		
		JPanel panel_35 = new JPanel();
		panel_35.setBorder(new TitledBorder(null, "Дата и время", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_36 = new JPanel();
		panel_36.setBorder(new TitledBorder(null, "Своевременность", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		cmb_cotd = new JComboBox<String>();
		
		JLabel lblNewLabel_62 = new JLabel("Отделение");
		
		cbx_messr = new JCheckBox("сообщение родственникам");
		GroupLayout gl_panel_34 = new GroupLayout(panel_34);
		gl_panel_34.setHorizontalGroup(
			gl_panel_34.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_34.createSequentialGroup()
					.addComponent(panel_35, GroupLayout.PREFERRED_SIZE, 136, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_36, GroupLayout.PREFERRED_SIZE, 170, GroupLayout.PREFERRED_SIZE)
					.addGroup(gl_panel_34.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_34.createSequentialGroup()
							.addGap(18)
							.addComponent(lblNewLabel_62)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(cbx_messr))
						.addGroup(gl_panel_34.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmb_cotd, 0, 335, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_34.setVerticalGroup(
			gl_panel_34.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_34.createSequentialGroup()
					.addGroup(gl_panel_34.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_34.createParallelGroup(Alignment.BASELINE)
							.addComponent(panel_35, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(panel_36, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_34.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_34.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_62)
								.addComponent(cbx_messr))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmb_cotd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(11, Short.MAX_VALUE))
		);
		
		JLabel lblNewLabel_59 = new JLabel("от начала заболевания");
		lblNewLabel_59.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		sp_sv_time = new JSpinner();
		
		JLabel lblNewLabel_60 = new JLabel("часов");
		
		JLabel lblNewLabel_61 = new JLabel("суток");
		
		sp_sv_day = new JSpinner();
		GroupLayout gl_panel_36 = new GroupLayout(panel_36);
		gl_panel_36.setHorizontalGroup(
			gl_panel_36.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_36.createSequentialGroup()
					.addGroup(gl_panel_36.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_59)
						.addGroup(gl_panel_36.createSequentialGroup()
							.addComponent(lblNewLabel_60)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(sp_sv_time, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_61)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(sp_sv_day, GroupLayout.DEFAULT_SIZE, 35, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_36.setVerticalGroup(
			gl_panel_36.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_36.createSequentialGroup()
					.addComponent(lblNewLabel_59)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel_36.createParallelGroup(Alignment.BASELINE)
						.addComponent(sp_sv_time, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_60)
						.addComponent(lblNewLabel_61)
						.addComponent(sp_sv_day, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		panel_36.setLayout(gl_panel_36);
		
		sp_datagosp = new JSpinner();
		sp_datagosp.setEnabled(false);
		sp_datagosp.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
		
		cbx_gosp = new JCheckBox("");
		GroupLayout gl_panel_35 = new GroupLayout(panel_35);
		gl_panel_35.setHorizontalGroup(
			gl_panel_35.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_35.createSequentialGroup()
					.addComponent(sp_datagosp, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(cbx_gosp)
					.addContainerGap())
		);
		gl_panel_35.setVerticalGroup(
			gl_panel_35.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_35.createSequentialGroup()
					.addGroup(gl_panel_35.createParallelGroup(Alignment.BASELINE)
						.addComponent(sp_datagosp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cbx_gosp))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_35.setLayout(gl_panel_35);
		panel_34.setLayout(gl_panel_34);
		
		JPanel panel_26 = new JPanel();
		
		JLabel lblNewLabel_48 = new JLabel("N талона");
		
		tf_ntalon = new JTextField();
		tf_ntalon.setColumns(10);
		
		JPanel panel_27 = new JPanel();
		panel_27.setBorder(new TitledBorder(null, "Дата и время", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_28 = new JPanel();
		panel_28.setBorder(new TitledBorder(null, "Кем направлен", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_29 = new JPanel();
		panel_29.setBorder(new TitledBorder(null, "Диагноз направившего учреждения", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_30 = new JPanel();
		panel_30.setBorder(new TitledBorder(null, "\u0414\u0438\u0430\u0433\u043D\u043E\u0437 \u043F\u0440\u0438\u0435\u043C\u043D\u043E\u0433\u043E \u043E\u0442\u0434\u0435\u043B\u0435\u043D\u0438\u044F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_31 = new JPanel();
		panel_31.setBorder(new TitledBorder(null, "Состояние пациента", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_32 = new JPanel();
		panel_32.setBorder(new TitledBorder(null, "Вызов скорой помощи", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblNewLabel_56 = new JLabel("Вид травмы");
		
		cmb_travm = new JComboBox<String>();
		
		JLabel lblNewLabel_57 = new JLabel("Вид транспортировки");
		
		cmb_trans = new JComboBox<String>();
		
		JPanel panel_33 = new JPanel();
		panel_33.setBorder(new TitledBorder(null, "\u0416\u0430\u043B\u043E\u0431\u044B", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		cmb_otkaz = new JComboBox<String>();
		
		JLabel lblNewLabel_58 = new JLabel("Причина отказа в госпитализации");
		GroupLayout gl_panel_25 = new GroupLayout(panel_25);
		gl_panel_25.setHorizontalGroup(
			gl_panel_25.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_25.createSequentialGroup()
					.addGroup(gl_panel_25.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_25.createSequentialGroup()
							.addGroup(gl_panel_25.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_26, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_25.createSequentialGroup()
									.addContainerGap()
									.addComponent(lblNewLabel_48)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(tf_ntalon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(18)
							.addComponent(panel_27, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_28, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_25.createSequentialGroup()
							.addComponent(panel_29, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panel_30, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panel_25.createSequentialGroup()
							.addGroup(gl_panel_25.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(panel_33, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addGroup(Alignment.LEADING, gl_panel_25.createSequentialGroup()
									.addContainerGap()
									.addComponent(panel_31, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(panel_32, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)))
							.addGroup(gl_panel_25.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_25.createSequentialGroup()
									.addGap(16)
									.addGroup(gl_panel_25.createParallelGroup(Alignment.LEADING, false)
										.addComponent(lblNewLabel_56)
										.addComponent(lblNewLabel_57)
										.addComponent(lblNewLabel_58))
									.addGap(50))
								.addGroup(gl_panel_25.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel_25.createParallelGroup(Alignment.LEADING)
										.addComponent(cmb_trans, 0, 234, Short.MAX_VALUE)
										.addComponent(cmb_travm, 0, 234, Short.MAX_VALUE)
										.addComponent(cmb_otkaz, 0, 234, Short.MAX_VALUE))))))
					.addContainerGap())
		);
		gl_panel_25.setVerticalGroup(
			gl_panel_25.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_25.createSequentialGroup()
					.addGroup(gl_panel_25.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_25.createSequentialGroup()
							.addComponent(panel_26, GroupLayout.PREFERRED_SIZE, 22, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_25.createParallelGroup(Alignment.BASELINE)
								.addComponent(tf_ntalon, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_48)))
						.addComponent(panel_27, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 67, Short.MAX_VALUE)
						.addComponent(panel_28, Alignment.TRAILING, 0, 0, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_25.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_30, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
						.addComponent(panel_29, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_25.createParallelGroup(Alignment.BASELINE)
						.addComponent(panel_32, GroupLayout.PREFERRED_SIZE, 98, Short.MAX_VALUE)
						.addGroup(gl_panel_25.createSequentialGroup()
							.addComponent(lblNewLabel_56)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmb_travm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(lblNewLabel_57)
							.addGap(5)
							.addComponent(cmb_trans, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel_31, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_25.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_25.createSequentialGroup()
							.addComponent(lblNewLabel_58)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmb_otkaz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel_33, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)))
		);
		
		ta_jal_pr = new JTextArea();
		ta_jal_pr.setLineWrap(true);
		ta_jal_pr.setWrapStyleWord(true);
		GroupLayout gl_panel_33 = new GroupLayout(panel_33);
		gl_panel_33.setHorizontalGroup(
			gl_panel_33.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_33.createSequentialGroup()
					.addGap(4)
					.addComponent(ta_jal_pr, GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE))
		);
		gl_panel_33.setVerticalGroup(
			gl_panel_33.createParallelGroup(Alignment.LEADING)
				.addComponent(ta_jal_pr, GroupLayout.DEFAULT_SIZE, 31, Short.MAX_VALUE)
		);
		panel_33.setLayout(gl_panel_33);
		
		JLabel lblNewLabel_54 = new JLabel("Дата и время");
		
		sp_datasmp = new JSpinner();
		sp_datasmp.setEnabled(false);
		sp_datasmp.setModel(new SpinnerDateModel(new Date(), new Date(1334772000000L), null, Calendar.DAY_OF_YEAR));
		
		JLabel lblNewLabel_55 = new JLabel("Номер");
		
		tf_smpn = new JTextField();
		tf_smpn.setColumns(10);
		
		cbx_smp = new JCheckBox("");
		GroupLayout gl_panel_32 = new GroupLayout(panel_32);
		gl_panel_32.setHorizontalGroup(
			gl_panel_32.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_32.createSequentialGroup()
					.addGroup(gl_panel_32.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_32.createSequentialGroup()
							.addComponent(sp_datasmp, GroupLayout.PREFERRED_SIZE, 107, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(cbx_smp))
						.addComponent(lblNewLabel_54)
						.addGroup(gl_panel_32.createSequentialGroup()
							.addComponent(lblNewLabel_55)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_smpn, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_32.setVerticalGroup(
			gl_panel_32.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_32.createSequentialGroup()
					.addGroup(gl_panel_32.createParallelGroup(Alignment.TRAILING)
						.addComponent(cbx_smp)
						.addGroup(gl_panel_32.createSequentialGroup()
							.addComponent(lblNewLabel_54)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(sp_datasmp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_32.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_55)
						.addComponent(tf_smpn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_32.setLayout(gl_panel_32);
		
		cmb_alk = new JComboBox<String>();

		cbx_nalz = new JCheckBox("чесотка");
		
		cbx_nalp = new JCheckBox("педикулез");
		
		JLabel lblNewLabel_51 = new JLabel("Опьянение");
		
		
		JLabel lblNewLabel_52 = new JLabel("Температура");
		
		JLabel lblNewLabel_53 = new JLabel("Давление");
		
		tf_toc = new JTextField();
		tf_toc.setColumns(10);
		
		tf_ad = new JTextField();
		tf_ad.setColumns(10);
		GroupLayout gl_panel_31 = new GroupLayout(panel_31);
		gl_panel_31.setHorizontalGroup(
			gl_panel_31.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_31.createSequentialGroup()
					.addComponent(cbx_nalp)
					.addGap(20)
					.addComponent(lblNewLabel_53)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tf_ad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_31.createParallelGroup(Alignment.TRAILING, false)
					.addGroup(gl_panel_31.createSequentialGroup()
						.addGap(5)
						.addComponent(lblNewLabel_51)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(cmb_alk, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGroup(Alignment.LEADING, gl_panel_31.createSequentialGroup()
						.addComponent(cbx_nalz)
						.addGap(18)
						.addComponent(lblNewLabel_52)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(tf_toc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		gl_panel_31.setVerticalGroup(
			gl_panel_31.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_31.createSequentialGroup()
					.addGroup(gl_panel_31.createParallelGroup(Alignment.BASELINE)
						.addComponent(cmb_alk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_51))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_31.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbx_nalz)
						.addComponent(lblNewLabel_52)
						.addComponent(tf_toc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_31.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbx_nalp)
						.addComponent(tf_ad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_53))
					.addGap(0, 0, Short.MAX_VALUE))
		);
		panel_31.setLayout(gl_panel_31);
		
		tf_diag_p = new JTextField();
		tf_diag_p.setColumns(10);
		
		ta_diag_p = new JTextArea();
		ta_diag_p.setWrapStyleWord(true);
		ta_diag_p.setLineWrap(true);
		GroupLayout gl_panel_30 = new GroupLayout(panel_30);
		gl_panel_30.setHorizontalGroup(
			gl_panel_30.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_30.createSequentialGroup()
					.addComponent(tf_diag_p, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ta_diag_p, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE))
		);
		gl_panel_30.setVerticalGroup(
			gl_panel_30.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_30.createSequentialGroup()
					.addContainerGap()
					.addComponent(tf_diag_p, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(35, Short.MAX_VALUE))
				.addComponent(ta_diag_p, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
		);
		panel_30.setLayout(gl_panel_30);
		
		tf_diag_n = new JTextField();
		tf_diag_n.setColumns(10);
		
		ta_diag_n = new JTextArea();
		ta_diag_n.setWrapStyleWord(true);
		ta_diag_n.setLineWrap(true);
		GroupLayout gl_panel_29 = new GroupLayout(panel_29);
		gl_panel_29.setHorizontalGroup(
			gl_panel_29.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_29.createSequentialGroup()
					.addComponent(tf_diag_n, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ta_diag_n, GroupLayout.DEFAULT_SIZE, 261, Short.MAX_VALUE))
		);
		gl_panel_29.setVerticalGroup(
			gl_panel_29.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_29.createSequentialGroup()
					.addContainerGap()
					.addComponent(tf_diag_n, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(35, Short.MAX_VALUE))
				.addComponent(ta_diag_n, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
		);
		panel_29.setLayout(gl_panel_29);
		
		cmb_naprav = new JComboBox<String>();
		cmb_org = new JComboBox<String>();

		GroupLayout gl_panel_28 = new GroupLayout(panel_28);
		gl_panel_28.setHorizontalGroup(
			gl_panel_28.createParallelGroup(Alignment.LEADING)
				.addComponent(cmb_naprav, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE)
				.addComponent(cmb_org, GroupLayout.PREFERRED_SIZE, 267, GroupLayout.PREFERRED_SIZE)
		);
		gl_panel_28.setVerticalGroup(
			gl_panel_28.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_28.createSequentialGroup()
					.addComponent(cmb_naprav, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(cmb_org, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		panel_28.setLayout(gl_panel_28);
		
		sp_dataosm = new JSpinner();
		sp_dataosm.setEnabled(false);
		sp_dataosm.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
		
		JLabel lblNewLabel_49 = new JLabel("Поступления");
		
		JLabel lblNewLabel_50 = new JLabel("Осмотра");
		
		sp_datap = new JSpinner();
		sp_datap.setEnabled(false);
		sp_datap.setModel(new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_YEAR));
		GroupLayout gl_panel_27 = new GroupLayout(panel_27);
		gl_panel_27.setHorizontalGroup(
			gl_panel_27.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_27.createSequentialGroup()
					.addGroup(gl_panel_27.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(Alignment.LEADING, gl_panel_27.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel_50)
							.addGap(18)
							.addComponent(sp_dataosm))
						.addGroup(Alignment.LEADING, gl_panel_27.createSequentialGroup()
							.addComponent(lblNewLabel_49)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(sp_datap, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(176, Short.MAX_VALUE))
		);
		gl_panel_27.setVerticalGroup(
			gl_panel_27.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_27.createSequentialGroup()
					.addGroup(gl_panel_27.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_49)
						.addComponent(sp_datap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_27.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_50)
						.addComponent(sp_dataosm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(223, Short.MAX_VALUE))
		);
		panel_27.setLayout(gl_panel_27);
		
		rbtn_plan = new JRadioButton("плановое");
		btnGroup_plextr.add(rbtn_plan);
		
		rbtn_extr = new JRadioButton("экстренное");
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
					.addGap(0, 0, Short.MAX_VALUE)
					.addGroup(gl_panel_26.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtn_plan, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
						.addComponent(rbtn_extr))
					.addGap(6))
		);
		panel_26.setLayout(gl_panel_26);
		panel_25.setLayout(gl_panel_25);
		
		JButton btnBrowse_priem = new JButton("Просмртр обращений");
		btnBrowse_priem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		      try{
				curPatientId =(int) tbl_patient.getModel().getValueAt(tbl_patient.getSelectedRow(),3);
				selectAllPatientPriemInfo(curPatientId);
		        //MainForm.tcl.getPatientAllGospInfo(curPatientId);
				//tbl_priem = new CustomTable<>(false, false, PatientAllGospInfoStruct.class, PatientAllGospInfoStruct._Fields.values(), 4,"N ист. бол.",5,"Дата",6,"Отделение",7,"DS прием",1,"npasp",2,"ngosp",3,"id");
		      } catch (Exception e) {
				e.printStackTrace();
			  }
			}
		});
		
		JButton btnNew_priem = new JButton("Новое обращение");
		btnNew_priem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NewPriemInfo();
			}
		});
		
		JButton btnSave_priem = new JButton("Сохранить");
		btnSave_priem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SavePriemInfo();
			}
		});
		
		JButton btnDel_priem = new JButton("Удалить");
		btnDel_priem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		      try{
				curPatientId =(int) tbl_patient.getModel().getValueAt(tbl_patient.getSelectedRow(),3);
				curId = (int) tbl_priem.getModel().getValueAt(tbl_priem.getSelectedRow(),7);
				curNgosp = (int) tbl_priem.getModel().getValueAt(tbl_priem.getSelectedRow(),6);
				MainForm.tcl.deleteGosp(curPatientId, curId);
				selectAllPatientPriemInfo(curPatientId);
//				tbl_priem = new CustomTable<>(false, false, PatientAllGospInfoStruct.class, PatientAllGospInfoStruct._Fields.values(), 4,"N ист. бол.",5,"Дата",6,"Отделение",7,"DS прием",1,"npasp",2,"ngosp",3,"id");
		      } catch (Exception e) {
				e.printStackTrace();
			  }
			}
		});
		GroupLayout gl_panel_24 = new GroupLayout(panel_24);
		gl_panel_24.setHorizontalGroup(
			gl_panel_24.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_24.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnBrowse_priem)
					.addGap(18)
					.addComponent(btnNew_priem)
					.addGap(18)
					.addComponent(btnSave_priem)
					.addGap(176)
					.addComponent(btnDel_priem)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_24.setVerticalGroup(
			gl_panel_24.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_24.createSequentialGroup()
					.addGroup(gl_panel_24.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnBrowse_priem)
						.addComponent(btnNew_priem)
						.addComponent(btnSave_priem)
						.addComponent(btnDel_priem))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_24.setLayout(gl_panel_24);
		
		JScrollPane scrollPane_3 = new JScrollPane();
		GroupLayout gl_panel_23 = new GroupLayout(panel_23);
		gl_panel_23.setHorizontalGroup(
			gl_panel_23.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_3, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 672, Short.MAX_VALUE)
		);
		gl_panel_23.setVerticalGroup(
			gl_panel_23.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_3, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
		);
		
		tbl_priem = new JTable();
		//tbl_priem = new CustomTable<>(false, false, PatientAllGospInfoStruct.class, PatientAllGospInfoStruct._Fields.values(), 4,"N ист. бол.",5,"Дата",6,"Отделение",7,"DS прием",1,"npasp",2,"ngosp",3,"id");
		scrollPane_3.setViewportView(tbl_priem);
		panel_23.setLayout(gl_panel_23);
		tpPriem.setLayout(gl_tpPriem);
		
		JPanel panel_1 = new JPanel();
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent arg0) {
		    }
		});
		
		JButton btnDel = new JButton("Удалить");
		btnDel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		      try{
				curPatientId =(int) tbl_patient.getModel().getValueAt(tbl_patient.getSelectedRow(),3);
		        MainForm.tcl.deletePatient(curPatientId);
		      } catch (Exception e) {
				e.printStackTrace();
			  }
		    }
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(2)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 194, Short.MAX_VALUE)))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnDel)
					.addContainerGap(142, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 538, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
					.addComponent(btnDel)
					.addContainerGap())
		);
		
		tbl_patient = new JTable();
		tblmodel = new PatientTableModel(pat);
		tbl_patient.setModel(tblmodel);
		//tbl_patient = new CustomTable<>(false, true, PatientAllStruct.class, PatientAllStruct._Fields.values(), 2,"Фамилия",3,"Имя",4,"Отчество",1,"ВН");
		scrollPane.setViewportView(tbl_patient);
		//tblmodel.initColumnSizes(tbl_patient); 
        
		JButton btnNew = new JButton("Новый");
		btnNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					tfFam.setText(null);
					tfIm.setText(null);
					tfOt.setText(null);
					tfDr.setText(null);
					tf_Adm_obl.setText(null);
					tf_Adm_gorod.setText(null);
					tf_Adm_ul.setText(null);
					tf_Adm_dom.setText(null);
					tf_Adm_kv.setText(null);
					tf_Adp_obl.setText(null);
					tf_Adp_gorod.setText(null);
					tf_Adp_ul.setText(null);
					tf_Adp_dom.setText(null);
					tf_Adp_kv.setText(null);
					tfMrname.setText(null);
					tfMr.setText(null);
					tfDolj.setText(null);
					tfTel.setText(null);
					tf_Cpol.setText(null);
					tf_Nuch.setText(null);
					tf_Nambk.setText(null);
					tf_Datapr.setText(null);
					tf_Dataot.setText(null);
					tf_datadoc.setText(null);
					tf_Snils.setText(null);
					tf_Odoc.setText(null);
					tf_serdoc.setText(null);
					tf_nomdoc.setText(null);
					tf_dms_ser.setText(null);
					tf_dms_nom.setText(null);
					tf_oms_ser.setText(null);
					tf_oms_nom.setText(null);
					tf_oms_smo.setText(null);
					tf_dms_smo.setText(null);
					rbtn_pol_m.setSelected(false);
					rbtn_pol_j.setSelected(false);
					//cmbStatus.setSelectedIndex(0);
					//curPatientId = MainForm.tcl.getAddPatient(patinfo);
					//tblmodel = new PatientTableModel(pat);
				} catch (Exception e) {
					e.printStackTrace();						
				}
			}
		});
		refresh(pat);
		
		JButton btnPoisk = new JButton("Поиск");
		btnPoisk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PacientMainFrame.getInstance().setVisible(true);
			}
		});
		
		JButton btnSave = new JButton("Сохранить");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					PersonalInfo = new PatientFullInfo();
					PersonalInfo.npasp = curPatientId;
					PersonalInfo.fam = tfFam.getText().trim();
					PersonalInfo.im = tfIm.getText().trim();
					PersonalInfo.ot = tfOt.getText().trim();
					PersonalInfo.datar = Date.parse(tfDr.getText());
					PersonalInfo.admAddress.region = tf_Adm_obl.getText().trim();
					PersonalInfo.admAddress.city = tf_Adm_gorod.getText().trim();
					PersonalInfo.admAddress.street = tf_Adm_ul.getText().trim();
					PersonalInfo.admAddress.house = tf_Adm_dom.getText().trim();
					PersonalInfo.admAddress.flat= tf_Adm_kv.getText().trim();
					PersonalInfo.adpAddress.region = tf_Adp_obl.getText().trim();
					PersonalInfo.adpAddress.city = tf_Adp_gorod.getText().trim();
					PersonalInfo.adpAddress.street = tf_Adp_ul.getText().trim();
					PersonalInfo.adpAddress.house = tf_Adp_dom.getText().trim();
					PersonalInfo.adpAddress.flat = tf_Adp_kv.getText().trim();
					PersonalInfo.namemr = tfMrname.getText().trim();
					PersonalInfo.mrab = tfMr.getText().trim();
					PersonalInfo.prof = tfDolj.getText().trim();
					PersonalInfo.tel = tfTel.getText().trim();
					PersonalInfo.cpol_pr = Short.valueOf(tf_Cpol.getText());
					PersonalInfo.nambk.nambk = tf_Nambk.getText();
					PersonalInfo.nambk.nuch = Short.valueOf(tf_Nuch.getText());
					PersonalInfo.nambk.datapr = Date.parse(tf_Datapr.getText());
					PersonalInfo.nambk.dataot = Date.parse(tf_Dataot.getText());
					PersonalInfo.datadoc = Date.parse(tf_datadoc.getText());
					PersonalInfo.snils = tf_Snils.getText().trim();
					PersonalInfo.odoc = tf_Odoc.getText().trim();
					PersonalInfo.docser = tf_serdoc.getText().trim();
					PersonalInfo.docnum = tf_nomdoc.getText().trim();
					PersonalInfo.polis_dms.ser = tf_dms_ser.getText().trim();
					PersonalInfo.polis_dms.nom = tf_dms_nom.getText().trim();
					PersonalInfo.polis_oms.ser = tf_oms_ser.getText().trim();
					PersonalInfo.polis_oms.nom = tf_oms_nom.getText().trim();
					PersonalInfo.polis_oms.strg = Short.valueOf(tf_oms_smo.getText());
					PersonalInfo.polis_dms.strg = Short.valueOf(tf_dms_smo.getText());
					if (rbtn_pol_m.isSelected()) {
						PersonalInfo.pol = 1;
					}
					if (rbtn_pol_j.isSelected()) {
						PersonalInfo.pol = 2;
					}
					//cmbStatus.getSelectedItem();
					curPatientId = MainForm.tcl.addPatient(PersonalInfo);
				} catch (Exception e) {
					e.printStackTrace();						
				}
			}
		});
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(btnNew)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnPoisk)
					.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
					.addComponent(btnSave)
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNew)
						.addComponent(btnPoisk)
						.addComponent(btnSave))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
	//слушатель таб контрола персональной информации о пациенте
	final ChangeListener  tpPersonalChangeListener= new ChangeListener() {
	    public void stateChanged(ChangeEvent changeEvent) {  
	      curPatientId =(int) tbl_patient.getModel().getValueAt(tbl_patient.getSelectedRow(),3);
	      changePatientPersonalInfo(curPatientId);
	    }
	};

	//слушатель таб контрола о льготе
	final ChangeListener  tpLgotaChangeListener= new ChangeListener() {
	    public void stateChanged(ChangeEvent changeEvent) {  
	      curPatientId =(int) tbl_patient.getModel().getValueAt(tbl_patient.getSelectedRow(),3);
	      changePatientLgotaInfo(curPatientId);
	    }
	};

	//слушатель таб контрола о категории
	final ChangeListener  tpKategChangeListener= new ChangeListener() {
	    public void stateChanged(ChangeEvent changeEvent) {  
	      curPatientId =(int) tbl_patient.getModel().getValueAt(tbl_patient.getSelectedRow(),3);
	      changePatientKategInfo(curPatientId);
	    }
	};

	//слушатель таб контрола о представителе
	final ChangeListener  tpAgentChangeListener= new ChangeListener() {
	    public void stateChanged(ChangeEvent changeEvent) {  
	      curPatientId =(int) tbl_patient.getModel().getValueAt(tbl_patient.getSelectedRow(),3);
	      changePatientAgentInfo(curPatientId);
	    }
	};

	//слушатель таб контрола о сигнальн отм
	final ChangeListener  tpSignChangeListener= new ChangeListener() {
	    public void stateChanged(ChangeEvent changeEvent) {  
	      curPatientId =(int) tbl_patient.getModel().getValueAt(tbl_patient.getSelectedRow(),3);
	      changePatientSignInfo(curPatientId);
	    }
	};

	//слушатель таб контрола о приемн отд
	final ChangeListener  tpPriemChangeListener= new ChangeListener() {
	    public void stateChanged(ChangeEvent changeEvent) {  
	    	curPatientId =(int) tbl_patient.getModel().getValueAt(tbl_patient.getSelectedRow(),3);
			curId = (int) tbl_priem.getModel().getValueAt(tbl_priem.getSelectedRow(),7);
			curNgosp = (int) tbl_priem.getModel().getValueAt(tbl_priem.getSelectedRow(),6);
			changePatientPriemInfo(curPatientId);
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
	
//	tbl_patient.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
//		@Override
//		public void valueChanged(ListSelectionEvent arg0) {
//			mainChangeListener.stateChanged(new ChangeEvent(tbMain));
//		
//		}
//	});
//
//	table.addMouseListener(new MouseAdapter() {
//		public void mouseClicked(MouseEvent e) {
//			mainChangeListener.stateChanged(new ChangeEvent(tbMain));
//		}
//	});


	// обновление информации о пациенте
	private <curPatientId> void changePatientPersonalInfo(int PatId){
		try {
			PersonalInfo = new PatientFullInfo();
			PersonalInfo = MainForm.tcl.getPatientFullInfo(PatId);
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			tfFam.setText(PersonalInfo.fam.trim());
			tfIm.setText(PersonalInfo.im.trim());
			tfOt.setText(PersonalInfo.ot.trim());
			tfDr.setText(sdf.format(new Date(PersonalInfo.datar)));
			tf_Adm_obl.setText(PersonalInfo.admAddress.region.trim());
			tf_Adm_gorod.setText(PersonalInfo.admAddress.city.trim());
			tf_Adm_ul.setText(PersonalInfo.admAddress.street.trim());
			tf_Adm_dom.setText(PersonalInfo.admAddress.house.trim());
			tf_Adm_kv.setText(PersonalInfo.admAddress.flat.trim());
			tf_Adp_obl.setText(PersonalInfo.adpAddress.region.trim());
			tf_Adp_gorod.setText(PersonalInfo.adpAddress.city.trim());
			tf_Adp_ul.setText(PersonalInfo.adpAddress.street.trim());
			tf_Adp_dom.setText(PersonalInfo.adpAddress.house.trim());
			tf_Adp_kv.setText(PersonalInfo.adpAddress.flat.trim());
			tfMrname.setText(PersonalInfo.namemr.trim());
			tfMr.setText(PersonalInfo.mrab.trim());
			tfDolj.setText(PersonalInfo.prof.trim());
			tfTel.setText(PersonalInfo.tel.trim());
			tf_Cpol.setText(Integer.toString(PersonalInfo.cpol_pr));
			tf_Nuch.setText(Integer.toString(PersonalInfo.nambk.nuch));
			tf_Nambk.setText(PersonalInfo.nambk.nambk.trim());
			tf_Datapr.setText(sdf.format(new Date(PersonalInfo.nambk.datapr)));
			tf_Dataot.setText(sdf.format(new Date(PersonalInfo.nambk.dataot)));
			tf_datadoc.setText(sdf.format(new Date(PersonalInfo.datadoc)));
			tf_Snils.setText(PersonalInfo.snils.trim());
			tf_Odoc.setText(PersonalInfo.odoc.trim());
			tf_serdoc.setText(PersonalInfo.docser.trim());
			tf_nomdoc.setText(PersonalInfo.docnum.trim());
			tf_dms_ser.setText(PersonalInfo.polis_dms.ser.trim());
			tf_dms_nom.setText(PersonalInfo.polis_dms.nom.trim());
			tf_oms_ser.setText(PersonalInfo.polis_oms.ser.trim());
			tf_oms_nom.setText(PersonalInfo.polis_oms.nom.trim());
			tf_oms_smo.setText(Integer.toString(PersonalInfo.polis_oms.strg));
			tf_dms_smo.setText(Integer.toString(PersonalInfo.polis_dms.strg));
			if (Integer.valueOf(PersonalInfo.pol)==1) {
				rbtn_pol_m.setSelected(true);
				rbtn_pol_j.setSelected(false);
			}
			if (Integer.valueOf(PersonalInfo.pol)==2) {
				rbtn_pol_m.setSelected(false);
				rbtn_pol_j.setSelected(true);
			}
			//cmbStatus.getSelectedItem();
		} catch (Exception e) {
			e.printStackTrace();						
		}
	}
	// обновление информации о льготе
	private void changePatientLgotaInfo(int PatId){
		try {
			LgotaInfo = new ArrayList<Lgota>();
			LgotaInfo = MainForm.tcl.getLgota(PatId);
			tblmodel = new LgotaTableModel(LgotaInfo);
			tbl_lgota.setModel(tblmodel);
			tbl_lgota.getColumnModel().getColumn(0).setPreferredWidth(75);
			tbl_lgota.getColumnModel().getColumn(1).setPreferredWidth(75);
			tbl_lgota.getColumnModel().getColumn(2).setPreferredWidth(350);
			tbl_lgota.getColumnModel().getColumn(3).setPreferredWidth(50);
			tbl_lgota.getColumnModel().getColumn(4).setPreferredWidth(50);
		} catch (Exception e) {
			e.printStackTrace();						
		}
	}
	// обновление информации о категории
	private void changePatientKategInfo(int PatId){
		try {
	        KontingentInfo = new ArrayList<Kontingent>();
	        KontingentInfo = MainForm.tcl.getKontingent(PatId);
			tblmodel = new KontingentTableModel(KontingentInfo);
			tbl_kateg.setModel(tblmodel);
			tbl_kateg.getColumnModel().getColumn(0).setPreferredWidth(75);
			tbl_kateg.getColumnModel().getColumn(1).setPreferredWidth(75);
			tbl_kateg.getColumnModel().getColumn(2).setPreferredWidth(550);
		} catch (Exception e) {
			e.printStackTrace();						
		}
	}
	// обновление информации о представителе
	private void changePatientAgentInfo(int PatId){
		try {
	        AgentInfo = new Agent();
	        AgentInfo = MainForm.tcl.getAgent(PatId);
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			tf_Fam_pr.setText(AgentInfo.fam.trim());
			tf_Im_pr.setText(AgentInfo.im.trim());
			tf_Ot_pr.setText(AgentInfo.ot.trim());
			tf_Dr_pr.setText(sdf.format(new Date(AgentInfo.datar)));
			tf_Mr_pr.setText(AgentInfo.birthplace.trim());
			tf_Polis_ser_pr.setText(AgentInfo.spolis.trim());
			tf_Polis_nom_pr.setText(AgentInfo.npolis.trim());
			tf_Name_sk_pr.setText(AgentInfo.name_str.trim());
			tf_Ogrn.setText(AgentInfo.ogrn_str.trim());
			tf_Ser_doc_pr.setText(AgentInfo.docser.trim());
			tf_Nomdoc_pr.setText(AgentInfo.docnum.trim());
			if (Integer.valueOf(AgentInfo.pol)==1) {
				rbtn_pol_pr_m.setSelected(true);
				rbtn_pol_pr_j.setSelected(false);
			}
			if (Integer.valueOf(AgentInfo.pol)==2) {
				rbtn_pol_pr_m.setSelected(false);
				rbtn_pol_pr_j.setSelected(true);
			}
		} catch (Exception e) {
			e.printStackTrace();						
		}
	}
	// обновление информации сигн отм
	private void changePatientSignInfo(int PatId){
		try {
	        SignInfo = new Sign();
	        SignInfo = MainForm.tcl.getSign(PatId);
			rbtn_gk1.setSelected(false);
			rbtn_gk2.setSelected(false);
			rbtn_gk3.setSelected(false);
			rbtn_gk4.setSelected(false);
			rbtn_rf1.setSelected(false);
			rbtn_rf2.setSelected(false);
			rbtn_vp1.setSelected(false);
			rbtn_vp2.setSelected(false);
			rbtn_vp3.setSelected(false);
			if (SignInfo.grup=="1") {
				rbtn_gk1.setSelected(true);
			}
			if (SignInfo.grup=="2") {
				rbtn_gk2.setSelected(true);
			}
			if (SignInfo.grup=="3") {
				rbtn_gk3.setSelected(true);
			}
			if (SignInfo.grup=="4") {
				rbtn_gk4.setSelected(true);
			}
			if (SignInfo.ph=="+") {
				rbtn_rf1.setSelected(true);
			}
			if (SignInfo.ph=="-") {
				rbtn_rf2.setSelected(true);
			}
			if (SignInfo.vred.substring(0, 1)=="1") {
				rbtn_vp1.setSelected(true);
			}
			if (SignInfo.vred.substring(1, 2)=="1") {
				rbtn_vp2.setSelected(true);
			}
			if (SignInfo.vred.substring(2, 3)=="1") {
				rbtn_vp3.setSelected(true);
			}
			ta_allerg.setText(SignInfo.allerg.trim());
			ta_farm.setText(SignInfo.farmkol.trim());
			ta_vitae.setText(SignInfo.vitae.trim());
			
		} catch (Exception e) {
			e.printStackTrace();						
		}
	}
	// просмотр всех обращений
	private void selectAllPatientPriemInfo(int PatId){
      try{
    	GospInfo = MainForm.tcl.getAllGosp(PatId);
    	//MainForm.tcl.getPatientAllGospInfo(PatId);
		//tbl_priem = new CustomTable<>(false, false, PatientAllGospInfoStruct.class, PatientAllGospInfoStruct._Fields.values(), 4,"N ист. бол.",5,"Дата",6,"Отделение",7,"DS прием",1,"npasp",2,"ngosp",3,"id");
      } catch (Exception e) {
		e.printStackTrace();
	  }
      try {
		GospInfo = new ArrayList<AllGosp>();
		GospInfo = MainForm.tcl.getAllGosp(PatId);
		tblmodel = new GospTableModel(GospInfo);
		tbl_priem.setModel(tblmodel);
		tbl_priem.getColumnModel().getColumn(0).setPreferredWidth(75);
		tbl_priem.getColumnModel().getColumn(1).setPreferredWidth(75);
		tbl_priem.getColumnModel().getColumn(2).setPreferredWidth(75);
		tbl_priem.getColumnModel().getColumn(3).setPreferredWidth(75);
		tbl_priem.getColumnModel().getColumn(4).setPreferredWidth(250);
		tbl_priem.getColumnModel().getColumn(5).setPreferredWidth(50);
		tbl_priem.getColumnModel().getColumn(6).setPreferredWidth(50);
		tbl_priem.getColumnModel().getColumn(7).setPreferredWidth(50);
      } catch (Exception e) {
		e.printStackTrace();						
      }
	}
	// просмотр информации о госпитализациях
	private void changePatientPriemInfo(int PatId){
		try {
	        Id_gospInfo = new Gosp();
	        //TODO метод получения госпитализации требует только 1 аргумент! 
//	        Id_gospInfo = MainForm.tcl.getGosp(PatId, curId);
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");

			rbtn_plan.setSelected(false);
			rbtn_extr.setSelected(false);
			cbx_nalz.setSelected(false);
			cbx_nalp.setSelected(false);
			cbx_messr.setSelected(false);
			cbx_smp.setSelected(false);
			cbx_gosp.setSelected(false);
			sp_datasmp.setEnabled(false);
			sp_datagosp.setEnabled(false);
			tf_smpn.setEnabled(false);

			if (Id_gospInfo.pl_extr==1) {
				rbtn_plan.setSelected(true);
			}
			if (Id_gospInfo.pl_extr==2) {
				rbtn_extr.setSelected(true);
			}
			if (Id_gospInfo.nal_z==1) {
				cbx_nalz.setSelected(true);
			}
			if (Id_gospInfo.nal_p==1) {
				cbx_nalp.setSelected(true);
			}
			if (Id_gospInfo.messr==1) {
				cbx_messr.setSelected(true);
			}
			tf_ntalon.setText(Integer.toString(Id_gospInfo.ntalon));
			tf_diag_n.setText(Id_gospInfo.diag_n.trim());
			tf_diag_p.setText(Id_gospInfo.diag_p.trim());
			ta_diag_n.setText(Id_gospInfo.named_n.trim());
			ta_diag_p.setText(Id_gospInfo.named_p.trim());
			//TODO Jalob теперь отдельный класс
//			ta_jal_pr.setText(Id_gospInfo.jalob.jalob.trim());
			tf_toc.setText(Id_gospInfo.toc.trim());
			tf_ad.setText(Id_gospInfo.ad.trim());
			if (Id_gospInfo.smp_num != 0  ) {
				tf_smpn.setText(Integer.toString(Id_gospInfo.smp_num));
				tf_smpn.setEnabled(true);
			}

			if (Id_gospInfo.datap != 0  ) {
				sp_datap.setValue(sdf.format(new Date(Id_gospInfo.datap)));
			}
			if (Id_gospInfo.dataosm != 0  ) {
				sp_dataosm.setValue(sdf.format(new Date(Id_gospInfo.dataosm)));
			}
			if (Id_gospInfo.smp_data != 0  ) {
				sp_datasmp.setValue(sdf.format(new Date(Id_gospInfo.smp_data)));
				sp_datasmp.setEnabled(true);
				cbx_smp.setSelected(true);
			}
			if (Id_gospInfo.datagos != 0  ) {
				sp_datagosp.setValue(sdf.format(new Date(Id_gospInfo.datagos)));
				sp_datagosp.setEnabled(true);
				cbx_gosp.setSelected(true);
			}
			sp_sv_time.setValue(Id_gospInfo.sv_time);
			sp_sv_day.setValue(Id_gospInfo.sv_day);
			
			cmb_cotd.setSelectedIndex(Id_gospInfo.cotd);
			cmb_alk.setSelectedIndex(Id_gospInfo.alkg);
			//cmb_naprav.setSelectedIndex(Id_gospInfo.naprav);
			cmb_org.setSelectedIndex(Id_gospInfo.n_org);
			cmb_travm.setSelectedIndex(Id_gospInfo.vidtr);
			cmb_trans.setSelectedIndex(Id_gospInfo.vid_trans);
			cmb_otkaz.setSelectedIndex(Id_gospInfo.pr_out);
			
		} catch (Exception e) {
			e.printStackTrace();						
		}
	}
	// новое обращение
	private void NewPriemInfo(){
		try {
			System.out.println("npasp="+Integer.toString(curPatientId)+", ngosp="+Integer.toString(curNgosp)+", id="+Integer.toString(curId));
			rbtn_plan.setSelected(false);
			rbtn_extr.setSelected(false);
			cbx_nalz.setSelected(false);
			cbx_nalp.setSelected(false);
			cbx_messr.setSelected(false);
			cbx_smp.setSelected(false);
			cbx_gosp.setSelected(false);
			sp_datap.setEnabled(true);
			sp_datagosp.setEnabled(false);
			sp_dataosm.setEnabled(true);
			sp_datasmp.setEnabled(false);
			tf_smpn.setEnabled(false);

			tf_ntalon.setText(null);
			tf_diag_n.setText(null);
			tf_diag_p.setText(null);
			ta_diag_n.setText(null);
			ta_diag_p.setText(null);
			ta_jal_pr.setText(null);
			tf_toc.setText(null);
			tf_ad.setText(null);
			sp_sv_time.setValue(0);
			sp_sv_day.setValue(0);
			
			cmb_cotd.setSelectedIndex(-1);
			cmb_alk.setSelectedIndex(-1);
			cmb_naprav.setSelectedIndex(-1);
			cmb_org.setSelectedIndex(-1);
			cmb_travm.setSelectedIndex(-1);
			cmb_trans.setSelectedIndex(-1);
			cmb_otkaz.setSelectedIndex(-1);
		} catch (Exception e) {
			e.printStackTrace();						
		}
	}
	private void ChangeStateCheckbox(){
		try {
			sp_datasmp.setEnabled(false);
			tf_smpn.setEnabled(false);
			sp_datagosp.setEnabled(false);
			if (cbx_smp.isSelected()){
				sp_datasmp.setEnabled(true);
				tf_smpn.setEnabled(true);
			}
			if (cbx_gosp.isSelected()){
				sp_datagosp.setEnabled(true);
			}
		} catch (Exception e) {
			e.printStackTrace();						
		}
	}
	private void SavePriemInfo(){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			Id_gospInfo = new Gosp();
			Id_gospInfo.npasp = curPatientId;
			Id_gospInfo.ngosp = curNgosp;
			Id_gospInfo.id = curId;
			//TODO Jalob теперь отдельный класс
//			Id_gospInfo.jalob.id_gosp = curId;
//			Id_gospInfo.jalob.npasp = curPatientId;
			//Id_gospInfo.nist ???
			//Id_gospInfo.cotd_p ???
			//Id_gospInfo.cuser ???
			System.out.println("npasp="+Integer.toString(Id_gospInfo.npasp)+", ngosp="+Integer.toString(Id_gospInfo.ngosp)+", id="+Integer.toString(Id_gospInfo.id));

			Id_gospInfo.datap = Date.parse(sp_datap.getValue().toString());
			Id_gospInfo.vremp = sp_datap.getValue().toString().substring(9, 14);
			Id_gospInfo.smp_data = Date.parse(sp_datasmp.getValue().toString());
			Id_gospInfo.smp_time = sp_datasmp.getValue().toString().substring(9, 14);
			Id_gospInfo.datagos = Date.parse(sp_datagosp.getValue().toString());
			Id_gospInfo.vremgos = sp_datagosp.getValue().toString().substring(9, 14);
			Id_gospInfo.dataosm = Date.parse(sp_dataosm.getValue().toString());
			Id_gospInfo.vremosm = sp_dataosm.getValue().toString().substring(9, 14);
			Id_gospInfo.dataz = Date.parse(sdf.format(new Date()));

			Id_gospInfo.sv_time = Short.valueOf(sp_sv_time.getValue().toString());
			Id_gospInfo.sv_day  = Short.valueOf(sp_sv_day.getValue().toString());
			Id_gospInfo.ntalon = Short.valueOf(tf_ntalon.getText());
			Id_gospInfo.diag_n = tf_diag_n.getText().trim();
			Id_gospInfo.diag_p = tf_diag_p.getText().trim();
			Id_gospInfo.named_n = ta_diag_n.getText().trim(); 
			Id_gospInfo.named_p = ta_diag_p.getText().trim();
			Id_gospInfo.toc = tf_toc.getText().trim();
			Id_gospInfo.ad = tf_ad.getText().trim();
			Id_gospInfo.smp_num = Integer.valueOf(tf_smpn.getText());
		// TODO Jalob теперь не входят в класс  госпиатлизации - это отдельный класс
//			Id_gospInfo.jalob.dataz = Date.parse(sdf.format(new Date()));
//			Id_gospInfo.jalob.jalob = ta_jal_pr.getText().trim();

			if (rbtn_plan.isSelected()) {
				Id_gospInfo.pl_extr = 1;
			}
			if (rbtn_extr.isSelected()) {
				Id_gospInfo.pl_extr = 2;
			}
			Id_gospInfo.messr = 0;
			if (cbx_messr.isSelected()) {
				Id_gospInfo.messr = 1;
			}
			Id_gospInfo.nal_z = 0;
			Id_gospInfo.nal_p = 0;
			if (cbx_nalz.isSelected()) {
				Id_gospInfo.nal_z = 1;
			}
			if (cbx_nalp.isSelected()) {
				Id_gospInfo.nal_p = 1;
			}

//			Id_gospInfo.vidtr = cmb_travm.getSelectedIndex();
//			Id_gospInfo.pr_out = cmb_otkaz.getSelectedIndex();
//			Id_gospInfo.alkg = cmb_alk.getSelectedIndex();
//			Id_gospInfo.vid_trans = cmb_trans.getSelectedIndex();
//			Id_gospInfo.naprav = Integer.toString(cmb_naprav.getSelectedIndex());
//			Id_gospInfo.n_org = cmb_org.getSelectedIndex();
//			Id_gospInfo.cotd = cmb_cotd.getSelectedIndex();
			
			//TODO addGosp возвращает только один аргумент - возмонжо переделать под два на серверной части!!
//			GospId GospId = MainForm.tcl.addGosp(Id_gospInfo);
//			curId = GospId.id;
//			curNgosp = GospId.ngosp;
		} catch (Exception e) {
			e.printStackTrace();						
		}
	}
	private void NSF(){
		try {
//			StatusList = new ArrayList<SpravStruct>();
//			VidPolisList = new ArrayList<SpravStruct>();
//			TypeDocList = new ArrayList<SpravStruct>();
//			StatusList = MainForm.tcl.getSpravInfo("n_az9");
//			VidPolisList = MainForm.tcl.getSpravInfo("n_f008");
//			TypeDocList = MainForm.tcl.getSpravInfo("n_az0");
//			
//			cbxmodel = new SpravComboBoxModel(VidPolisList);
//			cmb_VPOms = new JComboBox<String> (cbxmodel);
//			cmb_VPOms_pr = new JComboBox<String> (cbxmodel);
//			cbxmodel = new SpravComboBoxModel(TypeDocList);
//			cmb_TDoc = new JComboBox<String> (cbxmodel);
		} catch (Exception e) {
			e.printStackTrace();						
		}
}
}
