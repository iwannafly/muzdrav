package ru.nkz.ivcgzo.clientKartaRInv;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.IClient;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftKartaRInv.PatientCommonInfo;
import ru.nkz.ivcgzo.thriftKartaRInv.Pinvk;
import ru.nkz.ivcgzo.thriftKartaRInv.PinvkNotFoundException;
import ru.nkz.ivcgzo.thriftKartaRInv.thriftKartaRInv;

public  class MainForm extends Client<thriftKartaRInv.Client> {
	public static thriftKartaRInv.Client tcl;
	public static MainForm instance;
	private JFrame frame;
	public  Pinvk pinvk;
	public  PatientCommonInfo patInf;
	private JLabel lblpatient;
	private CustomDateEditor t_datav;
	private CustomTextField t_vrach;
	private CustomDateEditor t_dataz;
	private CustomTextField t_uchr;
	private CustomTextField t_nom_mse;
	private CustomDateEditor t_d_osv;
	private CustomTextField t_ruk_mse;
	private CustomDateEditor t_d_otpr;
	private CustomDateEditor t_d_inv;
	private CustomDateEditor t_d_invp;
	private CustomDateEditor t_d_srok;
	private CustomTextField t_oslog;
	private CustomTextField t_diag;
	private CustomTextField t_diag_s1;
	private CustomTextField t_diag_s2;
	private CustomTextField t_diag_s3;
	private CustomTextField t_zakl_name;
	private ButtonGroup bg_psih;
	private JRadioButton radioButtonP;
	private JRadioButton radioButtonP_1;
	private JRadioButton radioButtonP_2;
	private JRadioButton radioButtonP_3;
	private JRadioButton radioButtonP_4;
	private ButtonGroup bg_rech;
	private JRadioButton radioButtonR;
	private JRadioButton radioButtonR_1;
	private JRadioButton radioButtonR_2;
	private JRadioButton radioButtonR_3;
	private JRadioButton radioButtonR_4;
	private ButtonGroup bg_sens;
	private JRadioButton radioButtonS;
	private JRadioButton radioButtonS_1;
	private JRadioButton radioButtonS_2;
	private JRadioButton radioButtonS_3;
	private JRadioButton radioButtonS_4;
	private ButtonGroup bg_dinam;
	private JRadioButton radioButtonD;
	private JRadioButton radioButtonD_1;
	private JRadioButton radioButtonD_2;
	private JRadioButton radioButtonD_3;
	private JRadioButton radioButtonD_4;
	private ButtonGroup bg_zab;
	private JRadioButton radioButtonZ;
	private JRadioButton radioButtonZ_1;
	private JRadioButton radioButtonZ_2;
	private JRadioButton radioButtonZ_3;
	private JRadioButton radioButtonZ_4;
	private ButtonGroup bg_urod;
	private JRadioButton radioButtonU;
	private JRadioButton radioButtonU_1;
	private JRadioButton radioButtonU_2;
	private JRadioButton radioButtonU_3;
	private JRadioButton radioButtonU_4;
	private ButtonGroup bg_samob;
	private JRadioButton radioButtonSo;
	private JRadioButton radioButtonSo_1;
	private JRadioButton radioButtonSo_2;
	private JRadioButton radioButtonSo_3;
	private JRadioButton radioButtonSo_4;
	private ButtonGroup bg_dvig;
	private JRadioButton radioButtonSd;
	private JRadioButton radioButtonSd_1;
	private JRadioButton radioButtonSd_2;
	private JRadioButton radioButtonSd_3;
	private JRadioButton radioButtonSd_4;
	private ButtonGroup bg_orient;
	private JRadioButton radioButtonOr;
	private JRadioButton radioButtonOr_1;
	private JRadioButton radioButtonOr_2;
	private JRadioButton radioButtonOr_3;
	private JRadioButton radioButtonOr_4;
	private ButtonGroup bg_obsh;
	private JRadioButton radioButtonO;
	private JRadioButton radioButtonO_1;
	private JRadioButton radioButtonO_2;
	private JRadioButton radioButtonO_3;
	private JRadioButton radioButtonO_4;
	private ButtonGroup bg_poved;
	private JRadioButton radioButtonK;
	private JRadioButton radioButtonK_1;
	private JRadioButton radioButtonK_2;
	private JRadioButton radioButtonK_3;
	private JRadioButton radioButtonK_4;
	private ButtonGroup bg_obuch;
	private JRadioButton radioButtonOb;
	private JRadioButton radioButtonOb_1;
	private JRadioButton radioButtonOb_2;
	private JRadioButton radioButtonOb_3;
	private JRadioButton radioButtonOb_4;
	private ButtonGroup bg_trud;
	private JRadioButton radioButtonT;
	private JRadioButton radioButtonT_1;
	private JRadioButton radioButtonT_2;
	private JRadioButton radioButtonT_3;
	private JRadioButton radioButtonT_4;
	private CustomTextField t_mr1d;
	private CustomTextField t_mr2d;
	private CustomTextField t_mr3d;
	private CustomTextField t_mr4d;
	private CustomTextField t_pr1d;
	private JCheckBox ch_mr1n;
	private JCheckBox ch_mr2n;
	private JCheckBox ch_mr3n;
	private JCheckBox ch_mr4n;
	private JCheckBox ch_mr5n;
	private JCheckBox ch_mr6n;
	private JCheckBox ch_mr7n;
	private JCheckBox ch_mr8n;
	private JCheckBox ch_mr9n;
	private JCheckBox ch_mr10n;
	private JCheckBox ch_mr11n;
	private JCheckBox ch_mr12n;
	private JCheckBox ch_mr13n;
	private JCheckBox ch_mr14n;
	private JCheckBox ch_mr15n;
	private JCheckBox ch_mr16n;
	private JCheckBox ch_mr17n;
	private JCheckBox ch_mr18n;
	private JCheckBox ch_mr19n;
	private JCheckBox ch_mr20n;
	private JCheckBox ch_mr21n;
	private JCheckBox ch_mr22n;
	private JCheckBox ch_mr23n;
	private JCheckBox ch_pr1n;
	private JCheckBox ch_pr2n;
	private JCheckBox ch_pr3n;
	private JCheckBox ch_pr4n;
	private JCheckBox ch_pr5n;
	private JCheckBox ch_pr6n;
	private JCheckBox ch_pr7n;
	private JCheckBox ch_pr8n;
	private JCheckBox ch_pr9n;
	private JCheckBox ch_pr10n;
	private JCheckBox ch_pr11n;
	private JCheckBox ch_pr12n;
	private JCheckBox ch_pr13n;
	private JCheckBox ch_pr14n;
	private JCheckBox ch_pr15n;
	private JCheckBox ch_pr16n;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mesto1;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_preds;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_rez_mse;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_srok_inv;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_factor;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_fact1;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_fact2;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_fact3;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_fact4;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_prognoz;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_potencial;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_med_reab;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_ps_reab;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_prof_reab;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_soc_reab;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_zakl;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_klin_prognoz;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr1v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr2v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr3v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr4v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr5v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr6v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr7v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr8v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr9v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr10v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr11v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr12v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr13v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr14v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr15v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr16v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr17v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr18v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr19v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr20v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr21v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr22v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_mr23v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_pr1v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_pr2v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_pr3v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_pr4v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_pr5v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_pr6v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_pr7v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_pr8v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_pr9v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_pr10v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_pr11v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_pr12v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_pr13v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_pr14v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_pr15v;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_pr16v;
	private int searchedNpasp;
	
	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(conMan, authInfo, thriftKartaRInv.Client.class, configuration.appId, configuration.thrPort, lncPrm);
	
		initialize();
		
		setFrame(frame);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Карта ребенка-инвалида");
		frame.setBounds(100, 100, 717, 678);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		final JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		final JButton btnAdd = new JButton("Добавить");
		btnAdd.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			//addDiag(MainForm.conMan.showMkbTreeForm("Диагноз", ""));
			pinvk  = new Pinvk();
				pinvk.setNpasp(searchedNpasp);
				System.out.println(searchedNpasp); //inv_id = pinvk.getNinv();
				try {
				//	pinvk.setNinv(MainForm.tcl.addPinvk(pinvk));
				//} catch (KmiacServerException e1) {
			//		// TODO Auto-generated catch block
			//		e1.printStackTrace();
			//	} catch (TException e1) {
			//		// TODO Auto-generated catch block
			//		e1.printStackTrace();
			//	}
				pinvk.setDatav(System.currentTimeMillis());
				pinvk.setDataz(System.currentTimeMillis());
				t_dataz.setDate(System.currentTimeMillis());
				t_datav.setDate(System.currentTimeMillis());
				//pinvk.setD_inv(t_d_inv.getDate().getTime());
				//pinvk.setD_invp(t_d_invp.getDate().getTime());
				//pinvk.setD_osv(t_d_osv.getDate().getTime());
				//pinvk.setD_otpr(t_d_otpr.getDate().getTime());
				//pinvk.setD_srok(t_d_srok.getDate().getTime());
				//pinvk.setDiag(getTextOrNull(t_diag.getText()));
				//pinvk.setDiag_s1(getTextOrNull(t_diag_s1.getText()));
				//pinvk.setDiag_s2(getTextOrNull(t_diag_s2.getText()));
				//pinvk.setDiag_s3(getTextOrNull(t_diag_s3.getText()));
				//pinvk.setOslog(getTextOrNull(t_oslog.getText()));
				//pinvk.setVrach(getTextOrNull(t_vrach.getText()));
				//pinvk.setUchr(getTextOrNull(t_uchr.getText()));
				//pinvk.setNom_mse(getTextOrNull(t_nom_mse.getText()));
				//pinvk.setRuk_mse(getTextOrNull(t_ruk_mse.getText()));
				//pinvk.setZakl_name(getTextOrNull(t_zakl_name.getText()));
				//pinvk.setMr1d(getTextOrNull(t_mr1d.getText()));
				//pinvk.setMr2d(getTextOrNull(t_mr2d.getText()));
				//pinvk.setMr3d(getTextOrNull(t_mr3d.getText()));
				//pinvk.setMr4d(getTextOrNull(t_mr4d.getText()));
				//pinvk.setPr1d(getTextOrNull(t_pr1d.getText()));
			
			//	pinvk.setD_srok(cb_srok.getDate().getTime());
				MainForm.tcl.addPinvk(pinvk);
				} catch (KmiacServerException e1) {
					//		// TODO Auto-generated catch block
							e1.printStackTrace();
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		}
		});
		final JButton btnSave = new JButton("Сохранить");
		btnSave.setIcon(null);
		btnSave.setToolTipText("Сохранить");
		btnSave.addActionListener (new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				pinvk = new Pinvk();
				pinvk.setNpasp(patInf.npasp);
				if (radioButtonP.isSelected()) pinvk.setNar1(0);
				if (radioButtonP_1.isSelected()) pinvk.setNar1(1);
				if (radioButtonP_2.isSelected()) pinvk.setNar1(2);
				if (radioButtonP_3.isSelected()) pinvk.setNar1(3);
				if (radioButtonP_4.isSelected()) pinvk.setNar1(4);
				
				if (radioButtonR.isSelected()) pinvk.setNar2(0);
				if (radioButtonR_1.isSelected()) pinvk.setNar2(1);
				if (radioButtonR_2.isSelected()) pinvk.setNar2(2);
				if (radioButtonR_3.isSelected()) pinvk.setNar2(3);
				if (radioButtonR_4.isSelected()) pinvk.setNar2(4);
				
				if (radioButtonS.isSelected()) pinvk.setNar3(0);
				if (radioButtonS_1.isSelected()) pinvk.setNar3(1);
				if (radioButtonS_2.isSelected()) pinvk.setNar3(2);
				if (radioButtonS_3.isSelected()) pinvk.setNar3(3);
				if (radioButtonS_4.isSelected()) pinvk.setNar3(4);
			
				if (radioButtonD.isSelected()) pinvk.setNar4(0);
				if (radioButtonD_1.isSelected()) pinvk.setNar4(1);
				if (radioButtonD_2.isSelected()) pinvk.setNar4(2);
				if (radioButtonD_3.isSelected()) pinvk.setNar4(3);
				if (radioButtonD_4.isSelected()) pinvk.setNar4(4);
				
				if (radioButtonZ.isSelected()) pinvk.setNar5(0);
				if (radioButtonZ_1.isSelected()) pinvk.setNar5(1);
				if (radioButtonZ_2.isSelected()) pinvk.setNar5(2);
				if (radioButtonZ_3.isSelected()) pinvk.setNar5(3);
				if (radioButtonZ_4.isSelected()) pinvk.setNar5(4);
				
				if (radioButtonU.isSelected()) pinvk.setNar6(0);
				if (radioButtonU_1.isSelected()) pinvk.setNar6(1);
				if (radioButtonU_2.isSelected()) pinvk.setNar6(2);
				if (radioButtonU_3.isSelected()) pinvk.setNar6(3);
				if (radioButtonU_4.isSelected()) pinvk.setNar6(4);
				
				if (radioButtonSo.isSelected()) pinvk.setOgr1(0);
				if (radioButtonSo_1.isSelected()) pinvk.setOgr1(1);
				if (radioButtonSo_2.isSelected()) pinvk.setOgr1(2);
				if (radioButtonSo_3.isSelected()) pinvk.setOgr1(3);
				if (radioButtonSo_4.isSelected()) pinvk.setOgr1(4);
				
				if (radioButtonSd.isSelected()) pinvk.setOgr2(0);
				if (radioButtonSd_1.isSelected()) pinvk.setOgr2(1);
				if (radioButtonSd_2.isSelected()) pinvk.setOgr2(2);
				if (radioButtonSd_3.isSelected()) pinvk.setOgr2(3);
				if (radioButtonSd_4.isSelected()) pinvk.setOgr2(4);
				
				if (radioButtonOr.isSelected()) pinvk.setOgr3(0);
				if (radioButtonOr_1.isSelected()) pinvk.setOgr3(1);
				if (radioButtonOr_2.isSelected()) pinvk.setOgr3(2);
				if (radioButtonOr_3.isSelected()) pinvk.setOgr3(3);
				if (radioButtonOr_4.isSelected()) pinvk.setOgr3(4);
											
				if (radioButtonO.isSelected()) pinvk.setOgr4(0);
				if (radioButtonO_1.isSelected()) pinvk.setOgr4(1);
				if (radioButtonO_2.isSelected()) pinvk.setOgr4(2);
				if (radioButtonO_3.isSelected()) pinvk.setOgr4(3);
				if (radioButtonO_4.isSelected()) pinvk.setOgr4(4);
			
				if (radioButtonK.isSelected()) pinvk.setOgr5(0);
				if (radioButtonK_1.isSelected()) pinvk.setOgr5(1);
				if (radioButtonK_2.isSelected()) pinvk.setOgr5(2);
				if (radioButtonK_3.isSelected()) pinvk.setOgr5(3);
				if (radioButtonK_4.isSelected()) pinvk.setOgr5(4);
				
				if (radioButtonOb.isSelected()) pinvk.setOgr6(0);
				if (radioButtonOb_1.isSelected()) pinvk.setOgr6(1);
				if (radioButtonOb_2.isSelected()) pinvk.setOgr6(2);
				if (radioButtonOb_3.isSelected()) pinvk.setOgr6(3);
				if (radioButtonOb_4.isSelected()) pinvk.setOgr6(4);
				
				if (radioButtonT.isSelected()) pinvk.setOgr7(0);
				if (radioButtonT_1.isSelected()) pinvk.setOgr7(1);
				if (radioButtonT_2.isSelected()) pinvk.setOgr7(2);
				if (radioButtonT_3.isSelected()) pinvk.setOgr7(3);
				if (radioButtonT_4.isSelected()) pinvk.setOgr7(4);
				
				if (ch_mr1n.isSelected()) pinvk.setMr1n(1);
				if (ch_mr2n.isSelected()) pinvk.setMr2n(1);
				if (ch_mr3n.isSelected()) pinvk.setMr3n(1);
				if (ch_mr4n.isSelected()) pinvk.setMr4n(1);
				if (ch_mr5n.isSelected()) pinvk.setMr5n(1);
				if (ch_mr6n.isSelected()) pinvk.setMr6n(1);
				if (ch_mr7n.isSelected()) pinvk.setMr7n(1);
				if (ch_mr8n.isSelected()) pinvk.setMr8n(1);
				if (ch_mr9n.isSelected()) pinvk.setMr9n(1);
				if (ch_mr10n.isSelected()) pinvk.setMr10n(1);
				if (ch_mr11n.isSelected()) pinvk.setMr11n(1);
				if (ch_mr12n.isSelected()) pinvk.setMr12n(1);
				if (ch_mr13n.isSelected()) pinvk.setMr13n(1);
				if (ch_mr14n.isSelected()) pinvk.setMr14n(1);
				if (ch_mr15n.isSelected()) pinvk.setMr15n(1);
				if (ch_mr16n.isSelected()) pinvk.setMr16n(1);
				if (ch_mr17n.isSelected()) pinvk.setMr17n(1);
				if (ch_mr18n.isSelected()) pinvk.setMr18n(1);
				if (ch_mr19n.isSelected()) pinvk.setMr19n(1);
				if (ch_mr20n.isSelected()) pinvk.setMr20n(1);
				if (ch_mr21n.isSelected()) pinvk.setMr21n(1);
				if (ch_mr22n.isSelected()) pinvk.setMr22n(1);
				if (ch_mr23n.isSelected()) pinvk.setMr23n(1);
				if (ch_pr1n.isSelected()) pinvk.setPr1n(1);
				if (ch_pr2n.isSelected()) pinvk.setPr2n(1);
				if (ch_pr3n.isSelected()) pinvk.setPr3n(1);
				if (ch_pr4n.isSelected()) pinvk.setPr4n(1);
				if (ch_pr5n.isSelected()) pinvk.setPr5n(1);
				if (ch_pr6n.isSelected()) pinvk.setPr6n(1);
				if (ch_pr7n.isSelected()) pinvk.setPr7n(1);
				if (ch_pr8n.isSelected()) pinvk.setPr8n(1);
				if (ch_pr9n.isSelected()) pinvk.setPr9n(1);
				if (ch_pr10n.isSelected()) pinvk.setPr10n(1);
				if (ch_pr11n.isSelected()) pinvk.setPr11n(1);
				if (ch_pr12n.isSelected()) pinvk.setPr12n(1);
				if (ch_pr13n.isSelected()) pinvk.setPr13n(1);
				if (ch_pr14n.isSelected()) pinvk.setPr14n(1);
				if (ch_pr15n.isSelected()) pinvk.setPr15n(1);
				if (ch_pr16n.isSelected()) pinvk.setPr16n(1);
				
				if (t_dataz.getDate()!=null)
					pinvk.setDataz(t_dataz.getDate().getTime());
				if (t_datav.getDate()!=null)
					pinvk.setDatav(t_datav.getDate().getTime());
				if (t_d_osv.getDate()!=null)
					pinvk.setD_osv(t_d_osv.getDate().getTime());
				if (t_d_otpr.getDate()!=null)
					pinvk.setD_otpr(t_d_otpr.getDate().getTime());
				if (t_d_inv.getDate()!=null)
					pinvk.setD_inv(t_d_inv.getDate().getTime());
				if (t_d_invp.getDate()!=null)
					pinvk.setD_invp(t_d_invp.getDate().getTime());
				if (t_d_srok.getDate()!=null)
					pinvk.setD_srok(t_d_srok.getDate().getTime());
				
				pinvk.setVrach(t_vrach.getText());
				pinvk.setUchr(t_uchr.getText());
				pinvk.setNom_mse(t_nom_mse.getText());
				pinvk.setRuk_mse(t_ruk_mse.getText());
				pinvk.setDiag(t_diag.getText());
				pinvk.setDiag_s1(t_diag_s1.getText());
				pinvk.setDiag_s2(t_diag_s2.getText());
				pinvk.setDiag_s3(t_diag_s3.getText());
				pinvk.setOslog(t_oslog.getText());
				pinvk.setZakl_name(t_zakl_name.getText());
				pinvk.setMr1d(t_mr1d.getText());
				pinvk.setMr2d(t_mr2d.getText());
				pinvk.setMr3d(t_mr3d.getText());
				pinvk.setMr4d(t_mr4d.getText());
				pinvk.setPr1d(t_pr1d.getText());
				
				if (cb_mesto1.getSelectedPcod() != null) pinvk.setMesto1(cb_mesto1.getSelectedPcod()); else pinvk.unsetMesto1();
				if (cb_preds.getSelectedPcod() != null) pinvk.setPreds(cb_preds.getSelectedPcod()); else pinvk.unsetPreds();
				if (cb_rez_mse.getSelectedPcod() != null) pinvk.setRez_mse(cb_rez_mse.getSelectedPcod()); else pinvk.unsetRez_mse();
				if (cb_srok_inv.getSelectedPcod() != null) pinvk.setSrok_inv(cb_srok_inv.getSelectedPcod()); else pinvk.unsetSrok_inv();
				if (cb_factor.getSelectedPcod() != null) pinvk.setFactor(cb_factor.getSelectedPcod()); else pinvk.unsetFactor();
				if (cb_fact1.getSelectedPcod() != null) pinvk.setFact1(cb_fact1.getSelectedPcod()); else pinvk.unsetFact1();
				if (cb_fact2.getSelectedPcod() != null) pinvk.setFact2(cb_fact2.getSelectedPcod()); else pinvk.unsetFact2();
				if (cb_fact3.getSelectedPcod() != null) pinvk.setFact3(cb_fact3.getSelectedPcod()); else pinvk.unsetFact3();
				if (cb_fact4.getSelectedPcod() != null) pinvk.setFact4(cb_fact4.getSelectedPcod()); else pinvk.unsetFact4();
				if (cb_prognoz.getSelectedPcod() != null) pinvk.setPrognoz(cb_prognoz.getSelectedPcod()); else pinvk.unsetPrognoz();
				if (cb_potencial.getSelectedPcod() != null) pinvk.setPotencial(cb_potencial.getSelectedPcod()); else pinvk.unsetPotencial();
				if (cb_med_reab.getSelectedPcod() != null) pinvk.setMed_reab(cb_med_reab.getSelectedPcod()); else pinvk.unsetMed_reab();
				if (cb_ps_reab.getSelectedPcod() != null) pinvk.setPs_reab(cb_ps_reab.getSelectedPcod()); else pinvk.unsetPs_reab();
				if (cb_prof_reab.getSelectedPcod() != null) pinvk.setProf_reab(cb_prof_reab.getSelectedPcod()); else pinvk.unsetProf_reab();
				if (cb_soc_reab.getSelectedPcod() != null) pinvk.setSoc_reab(cb_soc_reab.getSelectedPcod()); else pinvk.unsetSoc_reab();
				if (cb_zakl.getSelectedPcod() != null) pinvk.setZakl(cb_zakl.getSelectedPcod()); else pinvk.unsetZakl();
				if (cb_klin_prognoz.getSelectedPcod() != null) pinvk.setKlin_prognoz(cb_klin_prognoz.getSelectedPcod()); else pinvk.unsetKlin_prognoz();
				if (cb_mr1v.getSelectedPcod() != null) pinvk.setMr1v(cb_mr1v.getSelectedPcod()); else pinvk.unsetMr1v();
				if (cb_mr2v.getSelectedPcod() != null) pinvk.setMr2v(cb_mr2v.getSelectedPcod()); else pinvk.unsetMr2v();
				if (cb_mr3v.getSelectedPcod() != null) pinvk.setMr3v(cb_mr3v.getSelectedPcod()); else pinvk.unsetMr3v();
				if (cb_mr4v.getSelectedPcod() != null) pinvk.setMr4v(cb_mr4v.getSelectedPcod()); else pinvk.unsetMr4v();
				if (cb_mr5v.getSelectedPcod() != null) pinvk.setMr5v(cb_mr5v.getSelectedPcod()); else pinvk.unsetMr5v();
				if (cb_mr6v.getSelectedPcod() != null) pinvk.setMr6v(cb_mr6v.getSelectedPcod()); else pinvk.unsetMr6v();
				if (cb_mr7v.getSelectedPcod() != null) pinvk.setMr7v(cb_mr7v.getSelectedPcod()); else pinvk.unsetMr7v();
				if (cb_mr8v.getSelectedPcod() != null) pinvk.setMr8v(cb_mr8v.getSelectedPcod()); else pinvk.unsetMr8v();
				if (cb_mr9v.getSelectedPcod() != null) pinvk.setMr9v(cb_mr9v.getSelectedPcod()); else pinvk.unsetMr9v();
				if (cb_mr10v.getSelectedPcod() != null) pinvk.setMr10v(cb_mr10v.getSelectedPcod()); else pinvk.unsetMr10v();
				if (cb_mr11v.getSelectedPcod() != null) pinvk.setMr11v(cb_mr11v.getSelectedPcod()); else pinvk.unsetMr11v();
				if (cb_mr12v.getSelectedPcod() != null) pinvk.setMr12v(cb_mr12v.getSelectedPcod()); else pinvk.unsetMr12v();
				if (cb_mr13v.getSelectedPcod() != null) pinvk.setMr13v(cb_mr13v.getSelectedPcod()); else pinvk.unsetMr13v();
				if (cb_mr14v.getSelectedPcod() != null) pinvk.setMr14v(cb_mr14v.getSelectedPcod()); else pinvk.unsetMr14v();
				if (cb_mr15v.getSelectedPcod() != null) pinvk.setMr15v(cb_mr15v.getSelectedPcod()); else pinvk.unsetMr15v();
				if (cb_mr16v.getSelectedPcod() != null) pinvk.setMr16v(cb_mr16v.getSelectedPcod()); else pinvk.unsetMr16v();
				if (cb_mr17v.getSelectedPcod() != null) pinvk.setMr17v(cb_mr17v.getSelectedPcod()); else pinvk.unsetMr17v();
				if (cb_mr18v.getSelectedPcod() != null) pinvk.setMr18v(cb_mr18v.getSelectedPcod()); else pinvk.unsetMr18v();
				if (cb_mr19v.getSelectedPcod() != null) pinvk.setMr19v(cb_mr19v.getSelectedPcod()); else pinvk.unsetMr19v();
				if (cb_mr20v.getSelectedPcod() != null) pinvk.setMr20v(cb_mr20v.getSelectedPcod()); else pinvk.unsetMr20v();
				if (cb_mr21v.getSelectedPcod() != null) pinvk.setMr21v(cb_mr21v.getSelectedPcod()); else pinvk.unsetMr21v();
				if (cb_mr22v.getSelectedPcod() != null) pinvk.setMr22v(cb_mr22v.getSelectedPcod()); else pinvk.unsetMr22v();
				if (cb_mr23v.getSelectedPcod() != null) pinvk.setMr23v(cb_mr23v.getSelectedPcod()); else pinvk.unsetMr23v();
				if (cb_pr1v.getSelectedPcod() != null) pinvk.setPr1v(cb_pr1v.getSelectedPcod()); else pinvk.unsetPr1v();
				if (cb_pr2v.getSelectedPcod() != null) pinvk.setPr2v(cb_pr2v.getSelectedPcod()); else pinvk.unsetPr2v();
				if (cb_pr3v.getSelectedPcod() != null) pinvk.setPr3v(cb_pr3v.getSelectedPcod()); else pinvk.unsetPr3v();
				if (cb_pr4v.getSelectedPcod() != null) pinvk.setPr4v(cb_pr4v.getSelectedPcod()); else pinvk.unsetPr4v();
				if (cb_pr5v.getSelectedPcod() != null) pinvk.setPr5v(cb_pr5v.getSelectedPcod()); else pinvk.unsetPr5v();
				if (cb_pr6v.getSelectedPcod() != null) pinvk.setPr6v(cb_pr6v.getSelectedPcod()); else pinvk.unsetPr6v();
				if (cb_pr7v.getSelectedPcod() != null) pinvk.setPr7v(cb_pr7v.getSelectedPcod()); else pinvk.unsetPr7v();
				if (cb_pr8v.getSelectedPcod() != null) pinvk.setPr8v(cb_pr8v.getSelectedPcod()); else pinvk.unsetPr8v();
				if (cb_pr9v.getSelectedPcod() != null) pinvk.setPr9v(cb_pr9v.getSelectedPcod()); else pinvk.unsetPr9v();
				if (cb_pr10v.getSelectedPcod() != null) pinvk.setPr10v(cb_pr10v.getSelectedPcod()); else pinvk.unsetPr10v();
				if (cb_pr11v.getSelectedPcod() != null) pinvk.setPr11v(cb_pr11v.getSelectedPcod()); else pinvk.unsetPr11v();
				if (cb_pr12v.getSelectedPcod() != null) pinvk.setPr12v(cb_pr12v.getSelectedPcod()); else pinvk.unsetPr12v();
				if (cb_pr13v.getSelectedPcod() != null) pinvk.setPr13v(cb_pr13v.getSelectedPcod()); else pinvk.unsetPr13v();
				if (cb_pr14v.getSelectedPcod() != null) pinvk.setPr14v(cb_pr14v.getSelectedPcod()); else pinvk.unsetPr14v();
				if (cb_pr15v.getSelectedPcod() != null) pinvk.setPr15v(cb_pr15v.getSelectedPcod()); else pinvk.unsetPr15v();
				if (cb_pr16v.getSelectedPcod() != null) pinvk.setPr16v(cb_pr16v.getSelectedPcod()); else pinvk.unsetPr16v();
				try {
					MainForm.tcl.setPinvk(pinvk);
				} catch (KmiacServerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});		
		JButton btnDelete = new JButton("Удалить");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
			MainForm.tcl.DeletePinvk(pinvk.getNinv());
				} catch (KmiacServerException e) {
					e.printStackTrace();
				} catch (TException e) {
				MainForm.conMan.reconnect(e);
				}
			}
			
		});
		
		 lblpatient = new JLabel("Пациент:");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 704, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblpatient)
					.addPreferredGap(ComponentPlacement.RELATED, 338, Short.MAX_VALUE)
					.addComponent(btnAdd)
					.addGap(18)
					.addComponent(btnSave)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnDelete)
					.addGap(44))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnDelete)
						.addComponent(btnSave)
						.addComponent(btnAdd)
						.addComponent(lblpatient))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 619, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		cb_mesto1 = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0m);
		cb_preds = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0p);
		cb_rez_mse = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0r);
		cb_srok_inv = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0s);
		cb_factor = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0a);
		cb_fact1 = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0b);
		cb_fact2 = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0c);
		cb_fact3 = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0d);
		cb_fact4 = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0e);
		cb_prognoz = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0f);
		cb_potencial = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0g);
		cb_med_reab = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0n);
		cb_ps_reab = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0n);
		cb_prof_reab = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0n);
		cb_soc_reab = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0n);
		cb_zakl = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0h);
		cb_klin_prognoz = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0f);
		cb_mr1v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr2v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr3v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr4v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr5v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr6v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr7v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr8v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr9v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr10v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr11v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr12v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr13v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr14v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr15v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr16v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr17v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr18v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr19v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr20v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr21v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr22v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_mr23v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_pr1v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_pr2v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_pr3v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_pr4v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_pr5v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_pr6v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_pr7v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_pr8v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_pr9v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_pr10v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_pr11v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_pr12v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_pr13v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_pr14v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_pr15v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		cb_pr16v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		
		JPanel p_rez_exp = new JPanel();
		tabbedPane.addTab("Результаты экспертизы", null, p_rez_exp, null);
		
		JLabel l_datzap = new JLabel("Дата заполнения карты");
		l_datzap.setForeground(Color.RED);
		
		t_datav = new CustomDateEditor();
		t_datav.setColumns(10);
		
		JLabel l_glvrach = new JLabel("Главный врач");
		l_glvrach.setForeground(Color.RED);
		
		t_vrach = new CustomTextField();
		t_vrach.setColumns(10);
		
		JLabel l_datvbaz = new JLabel("Дата записи в базу");
		
		t_dataz = new CustomDateEditor();
		t_dataz.setColumns(10);
		
		JLabel l_mpreb = new JLabel("Место пребывания ребенка");
		l_mpreb.setForeground(Color.RED);
		
		//JComboBox cB_mesto1 = new JComboBox();
		cb_mesto1 = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0m);
		
		JLabel l_predst = new JLabel("Родители / представители ребенка");
		l_predst.setForeground(Color.RED);
		
		//JComboBox cB_preds = new JComboBox();
		cb_preds = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0p);
		
		JLabel label = new JLabel("ФИО / название учреждения");
		label.setForeground(Color.RED);
		
		t_uchr = new CustomTextField();
		t_uchr.setColumns(10);
		
		JPanel p_medsoc = new JPanel();
		p_medsoc.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0420\u0435\u0437\u0443\u043B\u044C\u0442\u0430\u0442\u044B \u043C\u0435\u0434\u0438\u043A\u043E-\u0441\u043E\u0446\u0438\u0430\u043B\u044C\u043D\u043E\u0439 \u044D\u043A\u0441\u043F\u0435\u0440\u0442\u0438\u0437\u044B", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		
		
		GroupLayout gl_p_rez_exp = new GroupLayout(p_rez_exp);
		gl_p_rez_exp.setHorizontalGroup(
			gl_p_rez_exp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_rez_exp.createSequentialGroup()
					.addGroup(gl_p_rez_exp.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_p_rez_exp.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_p_rez_exp.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_p_rez_exp.createParallelGroup(Alignment.TRAILING)
									.addGroup(gl_p_rez_exp.createSequentialGroup()
										.addComponent(l_mpreb)
										.addGap(34))
									.addGroup(gl_p_rez_exp.createSequentialGroup()
										.addComponent(l_predst)
										.addGap(18)))
								.addGroup(gl_p_rez_exp.createSequentialGroup()
									.addComponent(l_datzap)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(t_datav, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_p_rez_exp.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_p_rez_exp.createSequentialGroup()
									.addGap(13)
									.addComponent(l_glvrach)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(t_vrach, GroupLayout.PREFERRED_SIZE, 134, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(l_datvbaz)
									.addGap(18)
									.addComponent(t_dataz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_p_rez_exp.createParallelGroup(Alignment.LEADING, false)
									.addComponent(cb_mesto1, 0, 168, Short.MAX_VALUE)
									.addComponent(cb_preds, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(t_uchr, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_p_rez_exp.createSequentialGroup()
							.addGap(38)
							.addComponent(label))
						.addGroup(gl_p_rez_exp.createSequentialGroup()
							.addGap(19)
							.addComponent(p_medsoc, GroupLayout.PREFERRED_SIZE, 634, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(22, Short.MAX_VALUE))
		);
		gl_p_rez_exp.setVerticalGroup(
			gl_p_rez_exp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_rez_exp.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_p_rez_exp.createParallelGroup(Alignment.BASELINE)
						.addComponent(l_datzap)
						.addComponent(t_datav, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(l_glvrach)
						.addComponent(t_vrach, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(l_datvbaz)
						.addComponent(t_dataz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_p_rez_exp.createParallelGroup(Alignment.BASELINE)
						.addComponent(cb_mesto1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(l_mpreb))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_p_rez_exp.createParallelGroup(Alignment.BASELINE)
						.addComponent(l_predst)
						.addComponent(cb_preds, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_p_rez_exp.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(t_uchr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(p_medsoc, GroupLayout.PREFERRED_SIZE, 435, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(34, Short.MAX_VALUE))
		);
		
		JLabel label_1 = new JLabel("Акт МСЭ №");
		
		t_nom_mse = new CustomTextField();
		t_nom_mse.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Дата освидетельствования");
		
		t_d_osv = new CustomDateEditor();
		t_d_osv.setColumns(10);
		
		JLabel label_2 = new JLabel("Рук.Фед.учрежд.МСЭ");
		
		t_ruk_mse = new CustomTextField();
		t_ruk_mse.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Дата отправки");
		
		t_d_otpr = new CustomDateEditor();
		t_d_otpr.setColumns(10);
		
		JLabel label_3 = new JLabel("Заключение МСЭ");
		label_3.setForeground(Color.RED);
		
		t_d_inv = new CustomDateEditor();
		t_d_inv.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Дата установления инвалидности");
		lblNewLabel_2.setForeground(Color.RED);
		
	//	JComboBox cb_rez_mse = new JComboBox();
		cb_rez_mse = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0r);
	//	cb_rez_mse = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0r);
		JLabel label_4 = new JLabel("Дата повтор. установления  инв-ти");
		label_4.setForeground(Color.RED);
		
		t_d_invp = new CustomDateEditor();
		t_d_invp.setColumns(10);
		
		JLabel label_5 = new JLabel("Срок, на который дана инвалидность");
		label_5.setForeground(Color.RED);
		
//		JComboBox cb_srok = new JComboBox();
		cb_srok_inv = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0s);
		
		JLabel label_6 = new JLabel("До какой даты инвалидность");
		t_d_srok = new CustomDateEditor();
		t_d_srok.setColumns(10);
		
		JButton button = new JButton("Экспорт карты в МСЭ");
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		button.setEnabled(false);
		GroupLayout gl_p_medsoc = new GroupLayout(p_medsoc);
		gl_p_medsoc.setHorizontalGroup(
			gl_p_medsoc.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_medsoc.createSequentialGroup()
					.addGroup(gl_p_medsoc.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_p_medsoc.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_p_medsoc.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel)
								.addComponent(label_2)
								.addComponent(lblNewLabel_1)
								.addComponent(label_3)
								.addComponent(label_1))
							.addGap(105)
							.addGroup(gl_p_medsoc.createParallelGroup(Alignment.LEADING)
								.addComponent(t_d_otpr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(t_d_osv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(t_nom_mse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(t_ruk_mse, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
								.addComponent(cb_rez_mse, GroupLayout.PREFERRED_SIZE, 224, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_p_medsoc.createSequentialGroup()
							.addComponent(lblNewLabel_2)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(t_d_inv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(28)
							.addComponent(label_4)
							.addGap(18)
							.addComponent(t_d_invp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_p_medsoc.createSequentialGroup()
							.addGroup(gl_p_medsoc.createParallelGroup(Alignment.LEADING)
								.addComponent(label_5)
								.addGroup(gl_p_medsoc.createSequentialGroup()
									.addContainerGap()
									.addComponent(label_6)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_p_medsoc.createParallelGroup(Alignment.LEADING)
								.addComponent(t_d_srok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(cb_srok_inv, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_p_medsoc.createSequentialGroup()
							.addGap(186)
							.addComponent(button, GroupLayout.PREFERRED_SIZE, 153, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		gl_p_medsoc.setVerticalGroup(
			gl_p_medsoc.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_medsoc.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_p_medsoc.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(t_nom_mse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(9)
					.addGroup(gl_p_medsoc.createParallelGroup(Alignment.BASELINE)
						.addComponent(t_d_osv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_p_medsoc.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_2)
						.addComponent(t_ruk_mse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_p_medsoc.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(t_d_otpr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(8)
					.addGroup(gl_p_medsoc.createParallelGroup(Alignment.LEADING)
						.addComponent(label_3)
						.addComponent(cb_rez_mse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_p_medsoc.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(t_d_inv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_4)
						.addComponent(t_d_invp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_p_medsoc.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_5)
						.addComponent(cb_srok_inv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(30)
					.addGroup(gl_p_medsoc.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_6)
						.addComponent(t_d_srok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(58)
					.addComponent(button)
					.addContainerGap(61, Short.MAX_VALUE))
		);
		p_medsoc.setLayout(gl_p_medsoc);
		p_rez_exp.setLayout(gl_p_rez_exp);
		
		JPanel p_nar = new JPanel();
		tabbedPane.addTab("Нарушения", null, p_nar, null);
		
		JLabel l_diagosn = new JLabel("<html>Диагноз основного  заболевания, <br> приведший к ограничению жизнедеятельности (МКБ-10) </html>");
		l_diagosn.setForeground(Color.RED);
		l_diagosn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		l_diagosn.setBackground(new Color(236, 233, 216));
		
		JLabel l_namdiag = new JLabel(" ");
		
		JLabel label_14 = new JLabel("Сопутствующие заболевания");
		
		JLabel label_15 = new JLabel("<html>Осложнения основного<br> диагноза (вписать)</html>");
		label_15.setForeground(Color.BLACK);
		
		t_oslog = new CustomTextField();
		t_oslog.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("<html>Причинные факторы,обусловившие<br>возникновение инвалидности</html>");
		lblNewLabel_9.setForeground(Color.RED);
		lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		//JComboBox cb_factor = new JComboBox();
		cb_factor = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0a);
		
		JLabel label_16 = new JLabel("Главное нарушение в состоянии здоровья");
		label_16.setForeground(Color.RED);
		
		//JComboBox cb_fact1 = new JComboBox();
		cb_fact1 = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0b);
		
		//JComboBox cb_fact2 = new JComboBox();
		cb_fact2 = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0c);
		
		JLabel lblNewLabel_10 = new JLabel("Ведущее ограничение жизнедеятельности");
		lblNewLabel_10.setForeground(Color.RED);
		
		//JComboBox cb_fact3 = new JComboBox();
		cb_fact3 = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0d);
		
		//JComboBox cB_fact4 = new JComboBox();
		cb_fact4 = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0e);
		
		JLabel label_17 = new JLabel("Реабилитационный прогноз");
		label_17.setForeground(Color.RED);
		
		JLabel label_18 = new JLabel("Реабилитационный потенциал");
		
		JLabel label_19 = new JLabel("Клинический прогноз");
		
		cb_prognoz = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0f);
		
		cb_potencial = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0g);
		
		cb_klin_prognoz = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0f);
		
		t_diag = new CustomTextField();
		t_diag.setColumns(10);
		t_diag.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					StringClassifier res = MainForm.conMan.showMkbTreeForm("МКБ",(( JTextField) e.getSource()).getText());
					if (res != null) {
						t_diag.setText(res.pcod);
						if (t_diag.getText().isEmpty());
					//	 t_diag.setText(res.name)
					}
				}
			}
			
		});
		
		
		
		t_diag_s1 = new CustomTextField();
		t_diag_s1.setColumns(10);
		t_diag_s1.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					StringClassifier res = MainForm.conMan.showMkbTreeForm("МКБ",(( JTextField) e.getSource()).getText());
					if (res != null) {
						t_diag_s1.setText(res.pcod);
						if (t_diag_s1.getText().isEmpty());
					}
				}
			}
			
		});
		
		t_diag_s2 = new CustomTextField();
		t_diag_s2.setColumns(10);
		t_diag_s2.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					StringClassifier res = MainForm.conMan.showMkbTreeForm("МКБ",(( JTextField) e.getSource()).getText());
					if (res != null) {
						t_diag_s2.setText(res.pcod);
						if (t_diag_s2.getText().isEmpty());
					}
				}
			}
			
		});
		t_diag_s3 = new CustomTextField();
		t_diag_s3.setColumns(10);
		t_diag_s3.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					StringClassifier res = MainForm.conMan.showMkbTreeForm("МКБ",(( JTextField) e.getSource()).getText());
					if (res != null) {
						t_diag_s3.setText(res.pcod);
						if (t_diag_s3.getText().isEmpty());
					//	 t_diag.setText(res.name)
					}
				}
			}
			
		});
		
		GroupLayout gl_p_nar = new GroupLayout(p_nar);
		gl_p_nar.setHorizontalGroup(
			gl_p_nar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_nar.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_p_nar.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_p_nar.createSequentialGroup()
							.addGap(0)
							.addGroup(gl_p_nar.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_p_nar.createParallelGroup(Alignment.LEADING, false)
									.addGroup(gl_p_nar.createSequentialGroup()
										.addComponent(l_diagosn, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(t_diag, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addComponent(l_namdiag))
									.addGroup(gl_p_nar.createSequentialGroup()
										.addGroup(gl_p_nar.createParallelGroup(Alignment.LEADING)
											.addComponent(label_14)
											.addComponent(label_15, GroupLayout.PREFERRED_SIZE, 128, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_p_nar.createParallelGroup(Alignment.LEADING)
											.addGroup(gl_p_nar.createSequentialGroup()
												.addGap(24)
												.addComponent(t_diag_s1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(t_diag_s2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(t_diag_s3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											.addGroup(Alignment.TRAILING, gl_p_nar.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
												.addComponent(t_oslog, GroupLayout.PREFERRED_SIZE, 363, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED))))
									.addGroup(gl_p_nar.createSequentialGroup()
										.addComponent(lblNewLabel_9, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(cb_factor, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
								.addGroup(gl_p_nar.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(cb_fact1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(cb_fact2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE))))
						.addComponent(label_16)
						.addComponent(lblNewLabel_10)
						.addGroup(gl_p_nar.createSequentialGroup()
							.addGroup(gl_p_nar.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_p_nar.createSequentialGroup()
									.addGroup(gl_p_nar.createParallelGroup(Alignment.LEADING)
										.addComponent(label_17)
										.addComponent(label_19))
									.addGap(18))
								.addGroup(gl_p_nar.createSequentialGroup()
									.addComponent(label_18)
									.addPreferredGap(ComponentPlacement.UNRELATED)))
							.addGroup(gl_p_nar.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_p_nar.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cb_potencial, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
								.addGroup(gl_p_nar.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cb_prognoz, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))
								.addComponent(cb_klin_prognoz, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addGroup(gl_p_nar.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(cb_fact4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(cb_fact3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)))
					.addContainerGap(219, Short.MAX_VALUE))
		);
		gl_p_nar.setVerticalGroup(
			gl_p_nar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_nar.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_p_nar.createParallelGroup(Alignment.LEADING)
						.addComponent(l_diagosn)
						.addComponent(l_namdiag)
						.addComponent(t_diag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_p_nar.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_14)
						.addComponent(t_diag_s1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(t_diag_s3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(t_diag_s2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_p_nar.createParallelGroup(Alignment.TRAILING)
						.addComponent(label_15)
						.addComponent(t_oslog, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addGroup(gl_p_nar.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_9)
						.addComponent(cb_factor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(label_16)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(cb_fact1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(cb_fact2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_10)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(cb_fact3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(cb_fact4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(29)
					.addGroup(gl_p_nar.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_17)
						.addComponent(cb_prognoz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_p_nar.createParallelGroup(Alignment.BASELINE)
						.addComponent(cb_potencial, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_18))
					.addGap(21)
					.addGroup(gl_p_nar.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_19)
						.addComponent(cb_klin_prognoz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(96, Short.MAX_VALUE))
		);
		p_nar.setLayout(gl_p_nar);
		
		JPanel p_nar_pr = new JPanel();
		tabbedPane.addTab("Нарушения (прод.)", null, p_nar_pr, null);
		
		JPanel p_nar_funor = new JPanel();
		p_nar_funor.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u041D\u0430\u0440\u0443\u0448\u0435\u043D\u0438\u044F \u0444\u0443\u043D\u043A\u0446\u0438\u0439 \u043E\u0440\u0433\u0430\u043D\u0438\u0437\u043C\u0430 \u0438 \u0441\u0442\u0435\u043F\u0435\u043D\u044C  \u0432\u044B\u0440\u0430\u0436\u0435\u043D\u043D\u043E\u0441\u0442\u0438", TitledBorder.CENTER, TitledBorder.TOP, null, Color.RED));
		
		JPanel p_ogrkatj = new JPanel();
		p_ogrkatj.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u041E\u0433\u0440\u0430\u043D\u0438\u0447\u0435\u043D\u0438\u0435 \u043E\u0441\u043D\u043E\u0432\u043D\u044B\u0445 \u043A\u0430\u0442\u0435\u0433\u043E\u0440\u0438\u0439 \u0436\u0438\u0437\u043D\u0435\u0434\u0435\u044F\u0442\u0435\u043B\u044C\u043D\u043E\u0441\u0442\u0438", TitledBorder.CENTER, TitledBorder.TOP, null, Color.RED));
		GroupLayout gl_p_nar_pr = new GroupLayout(p_nar_pr);
		gl_p_nar_pr.setHorizontalGroup(
			gl_p_nar_pr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_nar_pr.createSequentialGroup()
					.addComponent(p_nar_funor, GroupLayout.PREFERRED_SIZE, 325, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(p_ogrkatj, GroupLayout.PREFERRED_SIZE, 340, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(16, Short.MAX_VALUE))
		);
		gl_p_nar_pr.setVerticalGroup(
			gl_p_nar_pr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_nar_pr.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_p_nar_pr.createParallelGroup(Alignment.BASELINE)
						.addComponent(p_nar_funor, GroupLayout.PREFERRED_SIZE, 509, GroupLayout.PREFERRED_SIZE)
						.addComponent(p_ogrkatj, GroupLayout.PREFERRED_SIZE, 509, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		JLabel lblNewLabel_6 = new JLabel("Способности к самообслуживанию");
		lblNewLabel_6.setForeground(Color.BLUE);
		
		JLabel lblNewLabel_7 = new JLabel("Способности к самостоятельному передвижению");
		lblNewLabel_7.setForeground(Color.BLUE);
		
		JLabel lblNewLabel_8 = new JLabel("Способности к ориентации");
		lblNewLabel_8.setForeground(Color.BLUE);
		
		JLabel label_10 = new JLabel("Способности к общению");
		label_10.setForeground(Color.BLUE);
		
		JLabel label_11 = new JLabel("Способности контролировать свое поведение");
		label_11.setForeground(Color.BLUE);
		
		JLabel label_12 = new JLabel("Способности к обучению");
		label_12.setForeground(Color.BLUE);
		
		JLabel label_13 = new JLabel("Способности к трудовой деятельности");
		label_13.setForeground(Color.BLUE);
		
		JPanel p_samob = new JPanel();
		p_samob.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bg_samob = new ButtonGroup();
		
		JPanel p_dvig = new JPanel();
		p_dvig.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bg_dvig = new ButtonGroup();
		
		JPanel p_orient = new JPanel();
		p_orient.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bg_orient = new ButtonGroup();
		
		JPanel p_obsh = new JPanel();
		p_obsh.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bg_obsh = new ButtonGroup();
		
		JPanel p_poved = new JPanel();
		p_poved.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bg_poved = new ButtonGroup();
		
		JPanel p_obuch = new JPanel();
		p_obuch.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bg_obuch = new ButtonGroup();
		
		JPanel p_trud = new JPanel();
		p_trud.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bg_trud = new ButtonGroup();
		
		GroupLayout gl_p_ogrkatj = new GroupLayout(p_ogrkatj);
		gl_p_ogrkatj.setHorizontalGroup(
			gl_p_ogrkatj.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_ogrkatj.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_p_ogrkatj.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_p_ogrkatj.createSequentialGroup()
							.addComponent(p_trud, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_p_ogrkatj.createSequentialGroup()
							.addGroup(gl_p_ogrkatj.createParallelGroup(Alignment.TRAILING)
								.addComponent(label_11, Alignment.LEADING)
								.addGroup(gl_p_ogrkatj.createSequentialGroup()
									.addGroup(gl_p_ogrkatj.createParallelGroup(Alignment.LEADING)
										.addComponent(p_obuch, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
										.addComponent(p_poved, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
										.addComponent(p_obsh, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
										.addComponent(p_orient, GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
										.addGroup(gl_p_ogrkatj.createParallelGroup(Alignment.LEADING, false)
											.addComponent(lblNewLabel_6)
											.addComponent(label_12)
											.addComponent(label_13)
											.addComponent(lblNewLabel_8)
											.addComponent(p_dvig, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(p_samob, 0, 0, Short.MAX_VALUE)
											.addComponent(lblNewLabel_7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(label_10)))
									.addGap(103)))
							.addGap(0))))
		);
		gl_p_ogrkatj.setVerticalGroup(
			gl_p_ogrkatj.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_ogrkatj.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_6)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(p_samob, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel_7)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(p_dvig, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_8)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(p_orient, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_10)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(p_obsh, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(label_11)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(p_poved, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(label_12)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(p_obuch, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(label_13)
					.addGap(17)
					.addComponent(p_trud, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(65, Short.MAX_VALUE))
		);
		
		radioButtonT = new JRadioButton("0");
		
		radioButtonT_1 = new JRadioButton("1");
		
		radioButtonT_2 = new JRadioButton("2");
		
		radioButtonT_3 = new JRadioButton("3");
		
		radioButtonT_4 = new JRadioButton("4");
		bg_trud.add(radioButtonT);
		bg_trud.add(radioButtonT_1);
		bg_trud.add(radioButtonT_2);
		bg_trud.add(radioButtonT_3);
		bg_trud.add(radioButtonT_4);
		
		GroupLayout gl_p_trud = new GroupLayout(p_trud);
		gl_p_trud.setHorizontalGroup(
			gl_p_trud.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_trud.createSequentialGroup()
					.addComponent(radioButtonT)
					.addGap(18)
					.addComponent(radioButtonT_1)
					.addGap(33)
					.addComponent(radioButtonT_2)
					.addGap(28)
					.addComponent(radioButtonT_3)
					.addGap(18)
					.addComponent(radioButtonT_4)
					.addContainerGap(113, Short.MAX_VALUE))
		);
		gl_p_trud.setVerticalGroup(
			gl_p_trud.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_trud.createSequentialGroup()
					.addGroup(gl_p_trud.createParallelGroup(Alignment.BASELINE)
						.addComponent(radioButtonT)
						.addComponent(radioButtonT_1)
						.addComponent(radioButtonT_2)
						.addComponent(radioButtonT_3)
						.addComponent(radioButtonT_4))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		p_trud.setLayout(gl_p_trud);
		
		radioButtonOb = new JRadioButton("0");
		
		radioButtonOb_1 = new JRadioButton("1");
		
		radioButtonOb_2 = new JRadioButton("2");
		
		radioButtonOb_3 = new JRadioButton("3");
		
		radioButtonOb_4 = new JRadioButton("4");
		bg_obuch.add(radioButtonOb);
		bg_obuch.add(radioButtonOb_1);
		bg_obuch.add(radioButtonOb_2);
		bg_obuch.add(radioButtonOb_3);
		bg_obuch.add(radioButtonOb_4);
		
		GroupLayout gl_p_obuch = new GroupLayout(p_obuch);
		gl_p_obuch.setHorizontalGroup(
			gl_p_obuch.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_obuch.createSequentialGroup()
					.addComponent(radioButtonOb)
					.addGap(18)
					.addComponent(radioButtonOb_1)
					.addGap(31)
					.addComponent(radioButtonOb_2)
					.addGap(29)
					.addComponent(radioButtonOb_3)
					.addGap(18)
					.addComponent(radioButtonOb_4)
					.addContainerGap(11, Short.MAX_VALUE))
		);
		gl_p_obuch.setVerticalGroup(
			gl_p_obuch.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_obuch.createSequentialGroup()
					.addGroup(gl_p_obuch.createParallelGroup(Alignment.BASELINE)
						.addComponent(radioButtonOb)
						.addComponent(radioButtonOb_1)
						.addComponent(radioButtonOb_2)
						.addComponent(radioButtonOb_3)
						.addComponent(radioButtonOb_4))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		p_obuch.setLayout(gl_p_obuch);
		
		radioButtonK = new JRadioButton("0");
		
		radioButtonK_1 = new JRadioButton("1");
		
		radioButtonK_2 = new JRadioButton("2");
		
		radioButtonK_3 = new JRadioButton("3");
		
		radioButtonK_4 = new JRadioButton("4");
		bg_poved.add(radioButtonK);
		bg_poved.add(radioButtonK_1);
		bg_poved.add(radioButtonK_2);
		bg_poved.add(radioButtonK_3);
		bg_poved.add(radioButtonK_4);
		
		GroupLayout gl_p_poved = new GroupLayout(p_poved);
		gl_p_poved.setHorizontalGroup(
			gl_p_poved.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_poved.createSequentialGroup()
					.addComponent(radioButtonK)
					.addGap(18)
					.addComponent(radioButtonK_1)
					.addGap(30)
					.addComponent(radioButtonK_2)
					.addGap(29)
					.addComponent(radioButtonK_3)
					.addGap(18)
					.addComponent(radioButtonK_4)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_p_poved.setVerticalGroup(
			gl_p_poved.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_poved.createSequentialGroup()
					.addGroup(gl_p_poved.createParallelGroup(Alignment.BASELINE)
						.addComponent(radioButtonK)
						.addComponent(radioButtonK_1)
						.addComponent(radioButtonK_2)
						.addComponent(radioButtonK_3)
						.addComponent(radioButtonK_4))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		p_poved.setLayout(gl_p_poved);
		
		radioButtonO = new JRadioButton("0");
		
		radioButtonO_1 = new JRadioButton("1");
		
		radioButtonO_2 = new JRadioButton("2");
		
		radioButtonO_3 = new JRadioButton("3");
		
		radioButtonO_4 = new JRadioButton("4");
		bg_obsh.add(radioButtonO);
		bg_obsh.add(radioButtonO_1);
		bg_obsh.add(radioButtonO_2);
		bg_obsh.add(radioButtonO_3);
		bg_obsh.add(radioButtonO_4); 
		
		GroupLayout gl_p_obsh = new GroupLayout(p_obsh);
		gl_p_obsh.setHorizontalGroup(
			gl_p_obsh.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_obsh.createSequentialGroup()
					.addComponent(radioButtonO)
					.addGap(18)
					.addComponent(radioButtonO_1)
					.addGap(27)
					.addComponent(radioButtonO_2)
					.addPreferredGap(ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
					.addComponent(radioButtonO_3)
					.addGap(18)
					.addComponent(radioButtonO_4)
					.addGap(18))
		);
		gl_p_obsh.setVerticalGroup(
			gl_p_obsh.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_obsh.createSequentialGroup()
					.addGroup(gl_p_obsh.createParallelGroup(Alignment.BASELINE)
						.addComponent(radioButtonO)
						.addComponent(radioButtonO_2)
						.addComponent(radioButtonO_4)
						.addComponent(radioButtonO_1)
						.addComponent(radioButtonO_3))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		p_obsh.setLayout(gl_p_obsh);
		
		radioButtonOr = new JRadioButton("0");
		
		radioButtonOr_1 = new JRadioButton("1");
		
		radioButtonOr_2 = new JRadioButton("2");
		
		radioButtonOr_3 = new JRadioButton("3");
		
		radioButtonOr_4 = new JRadioButton("4");
		bg_orient.add(radioButtonOr);
		bg_orient.add(radioButtonOr_1);
		bg_orient.add(radioButtonOr_2);
		bg_orient.add(radioButtonOr_3);
		bg_orient.add(radioButtonOr_4); 
		
		GroupLayout gl_p_orient = new GroupLayout(p_orient);
		gl_p_orient.setHorizontalGroup(
			gl_p_orient.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_orient.createSequentialGroup()
					.addComponent(radioButtonOr)
					.addGap(18)
					.addComponent(radioButtonOr_1)
					.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
					.addComponent(radioButtonOr_2)
					.addGap(28)
					.addComponent(radioButtonOr_3)
					.addGap(18)
					.addComponent(radioButtonOr_4)
					.addGap(20))
		);
		gl_p_orient.setVerticalGroup(
			gl_p_orient.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_orient.createSequentialGroup()
					.addGroup(gl_p_orient.createParallelGroup(Alignment.BASELINE)
						.addComponent(radioButtonOr)
						.addComponent(radioButtonOr_4)
						.addComponent(radioButtonOr_2)
						.addComponent(radioButtonOr_1)
						.addComponent(radioButtonOr_3))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		p_orient.setLayout(gl_p_orient);
		
		radioButtonSd = new JRadioButton("0");
		
		radioButtonSd_1 = new JRadioButton("1");
		
		radioButtonSd_2 = new JRadioButton("2");
		
		radioButtonSd_3 = new JRadioButton("3");
		
		radioButtonSd_4 = new JRadioButton("4");
		bg_dvig.add(radioButtonSd);
		bg_dvig.add(radioButtonSd_1);
		bg_dvig.add(radioButtonSd_2);
		bg_dvig.add(radioButtonSd_3);
		bg_dvig.add(radioButtonSd_4); 
		
		GroupLayout gl_p_dvig = new GroupLayout(p_dvig);
		gl_p_dvig.setHorizontalGroup(
			gl_p_dvig.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_dvig.createSequentialGroup()
					.addComponent(radioButtonSd)
					.addGap(18)
					.addComponent(radioButtonSd_1)
					.addGap(20)
					.addComponent(radioButtonSd_2)
					.addPreferredGap(ComponentPlacement.RELATED, 31, Short.MAX_VALUE)
					.addComponent(radioButtonSd_3)
					.addGap(18)
					.addComponent(radioButtonSd_4)
					.addGap(20))
		);
		gl_p_dvig.setVerticalGroup(
			gl_p_dvig.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_dvig.createSequentialGroup()
					.addGroup(gl_p_dvig.createParallelGroup(Alignment.BASELINE)
						.addComponent(radioButtonSd)
						.addComponent(radioButtonSd_4)
						.addComponent(radioButtonSd_2)
						.addComponent(radioButtonSd_1)
						.addComponent(radioButtonSd_3, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		p_dvig.setLayout(gl_p_dvig);
		
		radioButtonSo = new JRadioButton("0");
		
		radioButtonSo_1 = new JRadioButton("1");
		
		radioButtonSo_2 = new JRadioButton("2");
		
		radioButtonSo_3 = new JRadioButton("3");
		
		radioButtonSo_4 = new JRadioButton("4");
		bg_samob.add(radioButtonSo);
		bg_samob.add(radioButtonSo_1);
		bg_samob.add(radioButtonSo_2);
		bg_samob.add(radioButtonSo_3);
		bg_samob.add(radioButtonSo_4);
				
		GroupLayout gl_p_samob = new GroupLayout(p_samob);
		gl_p_samob.setHorizontalGroup(
			gl_p_samob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_samob.createSequentialGroup()
					.addComponent(radioButtonSo)
					.addGap(18)
					.addComponent(radioButtonSo_1)
					.addGap(18)
					.addComponent(radioButtonSo_2)
					.addGap(27)
					.addComponent(radioButtonSo_3)
					.addGap(18)
					.addComponent(radioButtonSo_4)
					.addContainerGap(26, Short.MAX_VALUE))
		);
		gl_p_samob.setVerticalGroup(
			gl_p_samob.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_samob.createSequentialGroup()
					.addGroup(gl_p_samob.createParallelGroup(Alignment.BASELINE)
						.addComponent(radioButtonSo)
						.addComponent(radioButtonSo_1)
						.addComponent(radioButtonSo_2)
						.addComponent(radioButtonSo_4)
						.addComponent(radioButtonSo_3))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		p_samob.setLayout(gl_p_samob);
		p_ogrkatj.setLayout(gl_p_ogrkatj);
		
		JLabel lblNewLabel_3 = new JLabel("Психических функций");
		lblNewLabel_3.setForeground(Color.BLUE);
		lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JLabel lblNewLabel_4 = new JLabel("Языковых и речевых функций");
		lblNewLabel_4.setForeground(Color.BLUE);
		
		JLabel lblNewLabel_5 = new JLabel("Сенсорных функций");
		lblNewLabel_5.setForeground(Color.BLUE);
		
		JLabel label_7 = new JLabel("Статодинамических функций");
		label_7.setForeground(Color.BLUE);
		
		JLabel label_8 = new JLabel("<html>Функций кровообращения,дыхания, пищеварения,<br>выделения,кроветворения,обмена веществ,<br>внутренней секреции,иммунитета</html>");
		label_8.setForeground(Color.BLUE);
		
		JLabel label_9 = new JLabel("Обусловленные физическим уродством");
		label_9.setForeground(Color.BLUE);
		
		JPanel p_psih = new JPanel();
		p_psih.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bg_psih = new ButtonGroup();
		
		JPanel p_rech = new JPanel();
		p_rech.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bg_rech = new ButtonGroup();
		
		JPanel p_sens = new JPanel();
		p_sens.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bg_sens = new ButtonGroup();
		
		JPanel p_dinam = new JPanel();
		p_dinam.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bg_dinam = new ButtonGroup();
		
		JPanel p_zab = new JPanel();
		p_zab.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bg_zab = new ButtonGroup();
		
		JPanel p_urod = new JPanel();
		p_urod.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		bg_urod = new ButtonGroup();
		
		GroupLayout gl_p_nar_funor = new GroupLayout(p_nar_funor);
		gl_p_nar_funor.setHorizontalGroup(
			gl_p_nar_funor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_nar_funor.createSequentialGroup()
					.addGroup(gl_p_nar_funor.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_3)
						.addComponent(lblNewLabel_5)
						.addComponent(lblNewLabel_4)
						.addComponent(label_7)
						.addGroup(gl_p_nar_funor.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_p_nar_funor.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(p_rech, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(p_psih, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
								.addComponent(p_sens, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(p_dinam, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
						.addComponent(label_8, GroupLayout.PREFERRED_SIZE, 283, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_9)
						.addGroup(gl_p_nar_funor.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_p_nar_funor.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(p_urod, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(p_zab, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE))))
					.addContainerGap(26, Short.MAX_VALUE))
		);
		gl_p_nar_funor.setVerticalGroup(
			gl_p_nar_funor.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_nar_funor.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_3)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(p_psih, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_4)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(p_rech, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_5)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(p_sens, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(label_7)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(p_dinam, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(label_8)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(p_zab, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(label_9)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(p_urod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		radioButtonU = new JRadioButton("0");
		
		radioButtonU_1 = new JRadioButton("1");
		
		radioButtonU_2 = new JRadioButton("2");
		
		radioButtonU_3 = new JRadioButton("3");
		
		radioButtonU_4 = new JRadioButton("4");
		bg_urod.add(radioButtonU);
		bg_urod.add(radioButtonU_1);
		bg_urod.add(radioButtonU_2);
		bg_urod.add(radioButtonU_3);
		bg_urod.add(radioButtonU_4);
		
		GroupLayout gl_p_urod = new GroupLayout(p_urod);
		gl_p_urod.setHorizontalGroup(
			gl_p_urod.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_urod.createSequentialGroup()
					.addComponent(radioButtonU)
					.addGap(18)
					.addComponent(radioButtonU_1)
					.addGap(18)
					.addComponent(radioButtonU_2)
					.addGap(18)
					.addComponent(radioButtonU_3)
					.addGap(18)
					.addComponent(radioButtonU_4)
					.addContainerGap(25, Short.MAX_VALUE))
		);
		gl_p_urod.setVerticalGroup(
			gl_p_urod.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_urod.createSequentialGroup()
					.addGroup(gl_p_urod.createParallelGroup(Alignment.BASELINE)
						.addComponent(radioButtonU)
						.addComponent(radioButtonU_1)
						.addComponent(radioButtonU_2)
						.addComponent(radioButtonU_3)
						.addComponent(radioButtonU_4))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		p_urod.setLayout(gl_p_urod);
		
		radioButtonZ = new JRadioButton("0");
		
		radioButtonZ_1 = new JRadioButton("1");
		
		radioButtonZ_2 = new JRadioButton("2");
		
		radioButtonZ_3 = new JRadioButton("3");
		
		radioButtonZ_4 = new JRadioButton("4");
		bg_zab.add(radioButtonZ);
		bg_zab.add(radioButtonZ_1);
		bg_zab.add(radioButtonZ_2);
		bg_zab.add(radioButtonZ_3);
		bg_zab.add(radioButtonZ_4);
		
		GroupLayout gl_p_zab = new GroupLayout(p_zab);
		gl_p_zab.setHorizontalGroup(
			gl_p_zab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_zab.createSequentialGroup()
					.addContainerGap()
					.addComponent(radioButtonZ)
					.addPreferredGap(ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
					.addComponent(radioButtonZ_1)
					.addGap(18)
					.addComponent(radioButtonZ_2)
					.addGap(18)
					.addComponent(radioButtonZ_3)
					.addGap(18)
					.addComponent(radioButtonZ_4)
					.addGap(24))
		);
		gl_p_zab.setVerticalGroup(
			gl_p_zab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_zab.createSequentialGroup()
					.addGroup(gl_p_zab.createParallelGroup(Alignment.BASELINE)
						.addComponent(radioButtonZ)
						.addComponent(radioButtonZ_4)
						.addComponent(radioButtonZ_3)
						.addComponent(radioButtonZ_2)
						.addComponent(radioButtonZ_1))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		p_zab.setLayout(gl_p_zab);
		
		radioButtonD = new JRadioButton("0");
		
		radioButtonD_1 = new JRadioButton("1");
		
		radioButtonD_2 = new JRadioButton("2");
		
		radioButtonD_3 = new JRadioButton("3");
		
		radioButtonD_4 = new JRadioButton("4");
		bg_dinam.add(radioButtonD);
		bg_dinam.add(radioButtonD_1);
		bg_dinam.add(radioButtonD_2);
		bg_dinam.add(radioButtonD_3);
		bg_dinam.add(radioButtonD_4);
		
		GroupLayout gl_p_dinam = new GroupLayout(p_dinam);
		gl_p_dinam.setHorizontalGroup(
			gl_p_dinam.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_p_dinam.createSequentialGroup()
					.addContainerGap()
					.addComponent(radioButtonD)
					.addPreferredGap(ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
					.addComponent(radioButtonD_1)
					.addGap(18)
					.addComponent(radioButtonD_2)
					.addGap(18)
					.addComponent(radioButtonD_3)
					.addGap(18)
					.addComponent(radioButtonD_4)
					.addGap(17))
		);
		gl_p_dinam.setVerticalGroup(
			gl_p_dinam.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_dinam.createSequentialGroup()
					.addGroup(gl_p_dinam.createParallelGroup(Alignment.BASELINE)
						.addComponent(radioButtonD_4)
						.addComponent(radioButtonD_3)
						.addComponent(radioButtonD_2)
						.addComponent(radioButtonD_1)
						.addComponent(radioButtonD))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		p_dinam.setLayout(gl_p_dinam);
		
		radioButtonS = new JRadioButton("0");
		
		radioButtonS_1 = new JRadioButton("1");
		
		radioButtonS_2 = new JRadioButton("2");
		
		radioButtonS_3 = new JRadioButton("3");
		
		radioButtonS_4 = new JRadioButton("4");
		bg_sens.add(radioButtonS);
		bg_sens.add(radioButtonS_1);
		bg_sens.add(radioButtonS_2);
		bg_sens.add(radioButtonS_3);
		bg_sens.add(radioButtonS_4);
		
		GroupLayout gl_p_sens = new GroupLayout(p_sens);
		gl_p_sens.setHorizontalGroup(
			gl_p_sens.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_sens.createSequentialGroup()
					.addContainerGap()
					.addComponent(radioButtonS)
					.addGap(18)
					.addComponent(radioButtonS_1)
					.addGap(18)
					.addComponent(radioButtonS_2)
					.addGap(18)
					.addComponent(radioButtonS_3)
					.addGap(18)
					.addComponent(radioButtonS_4)
					.addContainerGap(19, Short.MAX_VALUE))
		);
		gl_p_sens.setVerticalGroup(
			gl_p_sens.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_sens.createSequentialGroup()
					.addGroup(gl_p_sens.createParallelGroup(Alignment.BASELINE)
						.addComponent(radioButtonS)
						.addComponent(radioButtonS_1)
						.addComponent(radioButtonS_2)
						.addComponent(radioButtonS_3)
						.addComponent(radioButtonS_4))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		p_sens.setLayout(gl_p_sens);
		
		radioButtonR = new JRadioButton("0");
		
		radioButtonR_1 = new JRadioButton("1");
		
		radioButtonR_2 = new JRadioButton("2");
		
		radioButtonR_3 = new JRadioButton("3");
		
		radioButtonR_4 = new JRadioButton("4");
		
		bg_rech.add(radioButtonR);
		bg_rech.add(radioButtonR_1);
		bg_rech.add(radioButtonR_2);
		bg_rech.add(radioButtonR_3);
		bg_rech.add(radioButtonR_4);
		
		GroupLayout gl_p_rech = new GroupLayout(p_rech);
		gl_p_rech.setHorizontalGroup(
			gl_p_rech.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_rech.createSequentialGroup()
					.addComponent(radioButtonR)
					.addGap(26)
					.addComponent(radioButtonR_1)
					.addGap(18)
					.addComponent(radioButtonR_2)
					.addGap(18)
					.addComponent(radioButtonR_3)
					.addGap(18)
					.addComponent(radioButtonR_4)
					.addContainerGap(17, Short.MAX_VALUE))
		);
		gl_p_rech.setVerticalGroup(
			gl_p_rech.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_rech.createSequentialGroup()
					.addGroup(gl_p_rech.createParallelGroup(Alignment.BASELINE)
						.addComponent(radioButtonR)
						.addComponent(radioButtonR_2)
						.addComponent(radioButtonR_3)
						.addComponent(radioButtonR_4)
						.addComponent(radioButtonR_1))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		p_rech.setLayout(gl_p_rech);
		
		radioButtonP = new JRadioButton("0");
		
		radioButtonP_1 = new JRadioButton("1");
		
		
		radioButtonP_2 = new JRadioButton("2");
		
		radioButtonP_3 = new JRadioButton("3");
		
		radioButtonP_4 = new JRadioButton("4");
		
		bg_psih.add(radioButtonP);
		bg_psih.add(radioButtonP_1);
		bg_psih.add(radioButtonP_2);
		bg_psih.add(radioButtonP_3);
		bg_psih.add(radioButtonP_4);
		
		GroupLayout gl_p_psih = new GroupLayout(p_psih);
		gl_p_psih.setHorizontalGroup(
			gl_p_psih.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_psih.createSequentialGroup()
					.addContainerGap()
					.addComponent(radioButtonP)
					.addGap(18)
					.addComponent(radioButtonP_1)
					.addGap(18)
					.addComponent(radioButtonP_2)
					.addGap(18)
					.addComponent(radioButtonP_3)
					.addGap(18)
					.addComponent(radioButtonP_4)
					.addContainerGap(38, Short.MAX_VALUE))
		);
		gl_p_psih.setVerticalGroup(
			gl_p_psih.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_psih.createSequentialGroup()
					.addGroup(gl_p_psih.createParallelGroup(Alignment.BASELINE)
						.addComponent(radioButtonP)
						.addComponent(radioButtonP_1)
						.addComponent(radioButtonP_2)
						.addComponent(radioButtonP_3)
						.addComponent(radioButtonP_4))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		p_psih.setLayout(gl_p_psih);
		p_nar_funor.setLayout(gl_p_nar_funor);
		p_nar_pr.setLayout(gl_p_nar_pr);
		
		JPanel p_ipr1 = new JPanel();
		tabbedPane.addTab("ИПР", null, p_ipr1, null);
		
		JLabel lblNewLabel_11 = new JLabel("Медицинская реабилитация");
		lblNewLabel_11.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel label_26 = new JLabel("Назначение");
		label_26.setFont(new Font("Tahoma", Font.BOLD, 11));
		label_26.setBackground(new Color(236, 233, 216));
		
		JLabel label_27 = new JLabel("Выполнение");
		label_27.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel label_28 = new JLabel("Медикаментозная терапия");
		
		//JComboBox cb_mr1v = new JComboBox();
		cb_mr1v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		t_mr1d = new CustomTextField();
		t_mr1d.setColumns(10);
		
		//JComboBox cb_mr2v = new JComboBox();
		cb_mr2v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		t_mr2d = new CustomTextField();
		t_mr2d.setColumns(10);
		
		JLabel label_31 = new JLabel("Физические методы");
		
		//JComboBox cb_mr3v = new JComboBox();
		cb_mr3v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_mr4v = new JComboBox();
		cb_mr4v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		t_mr3d = new CustomTextField();
		t_mr3d.setColumns(10);
		
		JLabel label_37 = new JLabel("Механические методы");
		
		//JComboBox cb_mr5v = new JComboBox();
		cb_mr5v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_mr6v = new JComboBox();
		cb_mr6v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_mr7v = new JComboBox();
		cb_mr7v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_mr9v = new JComboBox();
		cb_mr9v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		JLabel label_40 = new JLabel("Назначение");
		label_40.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel label_41 = new JLabel("Выполнение");
		label_41.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		//JComboBox cb_mr10v = new JComboBox();
		cb_mr10v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_mr11v = new JComboBox();
		cb_mr11v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_mr12v = new JComboBox();
		cb_mr12v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		JLabel label_44 = new JLabel("Водо- и бальнеотерапия");
		
		//JComboBox cb_mr13v = new JComboBox();
		cb_mr13v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_mr14v = new JComboBox();
		cb_mr14v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_mr15v = new JComboBox();
		cb_mr15v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_mr16v = new JComboBox();
		cb_mr16v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_mr17v = new JComboBox();
		cb_mr17v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		t_mr4d = new CustomTextField();
		t_mr4d.setColumns(10);
		
		//JComboBox cb_mr21v = new JComboBox();
		cb_mr21v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_mr22v = new JComboBox();
		cb_mr22v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_mr23v = new JComboBox();
		cb_mr23v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		
		ch_mr1n = new JCheckBox("медикаменты                ");
		ch_mr1n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_mr2n = new JCheckBox("спец.продукты             ");
		ch_mr2n.setHorizontalAlignment(SwingConstants.LEFT);
		ch_mr2n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_mr3n = new JCheckBox("электролечение          ");
		ch_mr3n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_mr4n = new JCheckBox("электростимуляция    ");
		ch_mr4n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_mr6n = new JCheckBox("баротерапия                ");
		ch_mr6n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_mr5n = new JCheckBox("лазеротерапия            ");
		ch_mr5n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_mr7n = new JCheckBox("другие(указать)          ");
		ch_mr7n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_mr8n = new JCheckBox("механотерапия              ");
		ch_mr8n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		cb_mr8v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		
		ch_mr9n = new JCheckBox("кинезотерапия               ");
		ch_mr9n.setHorizontalTextPosition(SwingConstants.LEFT);
			
		ch_mr10n = new JCheckBox("Массаж                        ");
		ch_mr10n.setHorizontalTextPosition(SwingConstants.LEFT);
			
		ch_mr11n = new JCheckBox("Рефлексотерапия        ");
		ch_mr11n.setHorizontalTextPosition(SwingConstants.LEFT);
			
		ch_mr12n = new JCheckBox("Трудотерапия              ");
		ch_mr12n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_mr13n = new JCheckBox("бассейн                         ");
		ch_mr13n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_mr14n = new JCheckBox("лечебные ванны          ");
		ch_mr14n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_mr15n = new JCheckBox("лечебные души            ");
		ch_mr15n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_mr16n = new JCheckBox("грязелечение               ");
		ch_mr16n.setHorizontalTextPosition(SwingConstants.LEFT);
			
		ch_mr18n = new JCheckBox("Логопедич.помощь       ");
		ch_mr18n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_mr19n = new JCheckBox("Лечебная физ-ра           ");
		ch_mr19n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_mr20n = new JCheckBox("СКЛ                                 ");
		ch_mr20n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_mr17n = new JCheckBox("другие(указать)          ");
		ch_mr17n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_mr21n = new JCheckBox("Реконстр.хирургия       ");
		ch_mr21n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_mr22n = new JCheckBox("Протезирование            ");
		ch_mr22n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_mr23n = new JCheckBox("Ортезирование              ");
		ch_mr23n.setHorizontalTextPosition(SwingConstants.LEFT);
			
		//JComboBox cb_mr18v = new JComboBox();
		cb_mr18v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_mr19v = new JComboBox();
		cb_mr19v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_mr20v = new JComboBox();
		cb_mr20v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		
		GroupLayout gl_p_ipr1 = new GroupLayout(p_ipr1);
		gl_p_ipr1.setHorizontalGroup(
			gl_p_ipr1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_p_ipr1.createSequentialGroup()
					.addGroup(gl_p_ipr1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_p_ipr1.createSequentialGroup()
							.addContainerGap()
							.addComponent(t_mr2d, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
						.addGroup(gl_p_ipr1.createSequentialGroup()
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_p_ipr1.createSequentialGroup()
									.addGap(20)
									.addComponent(lblNewLabel_11))
								.addGroup(gl_p_ipr1.createSequentialGroup()
									.addContainerGap()
									.addGroup(gl_p_ipr1.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(label_26)
										.addComponent(label_28, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(ch_mr1n, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addGap(51)
									.addComponent(label_27)))
							.addPreferredGap(ComponentPlacement.RELATED, 46, Short.MAX_VALUE))
						.addGroup(gl_p_ipr1.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_p_ipr1.createSequentialGroup()
									.addComponent(ch_mr2n, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(cb_mr2v, 0, 161, Short.MAX_VALUE))
								.addComponent(t_mr1d, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)))
						.addGroup(gl_p_ipr1.createSequentialGroup()
							.addGap(161)
							.addComponent(cb_mr1v, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_p_ipr1.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_p_ipr1.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
									.addGroup(gl_p_ipr1.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(ch_mr6n, 0, 0, Short.MAX_VALUE)
										.addComponent(ch_mr5n, 0, 0, Short.MAX_VALUE)
										.addComponent(ch_mr7n, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(label_31, Alignment.LEADING)
										.addComponent(ch_mr3n, Alignment.LEADING)
										.addComponent(ch_mr4n, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_p_ipr1.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(cb_mr7v, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(cb_mr6v, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(cb_mr5v, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(cb_mr4v, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(cb_mr3v, 0, 157, Short.MAX_VALUE)))
								.addComponent(t_mr3d, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)))
						.addGroup(gl_p_ipr1.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.LEADING)
								.addComponent(label_37)
								.addGroup(gl_p_ipr1.createSequentialGroup()
									.addGroup(gl_p_ipr1.createParallelGroup(Alignment.TRAILING)
										.addComponent(ch_mr9n, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
										.addComponent(ch_mr8n, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 146, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_p_ipr1.createParallelGroup(Alignment.LEADING)
										.addComponent(cb_mr8v, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_p_ipr1.createSequentialGroup()
											.addComponent(cb_mr9v, 0, 162, Short.MAX_VALUE)
											.addPreferredGap(ComponentPlacement.RELATED)))))))
					.addGroup(gl_p_ipr1.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_p_ipr1.createSequentialGroup()
							.addGap(104)
							.addComponent(label_40)
							.addGap(95)
							.addComponent(label_41))
						.addGroup(gl_p_ipr1.createSequentialGroup()
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_p_ipr1.createSequentialGroup()
									.addGap(58)
									.addGroup(gl_p_ipr1.createParallelGroup(Alignment.TRAILING)
										.addGroup(gl_p_ipr1.createParallelGroup(Alignment.LEADING, false)
											.addComponent(ch_mr18n, GroupLayout.PREFERRED_SIZE, 143, Short.MAX_VALUE)
											.addComponent(ch_mr19n, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
										.addComponent(ch_mr20n)
										.addComponent(ch_mr21n)
										.addComponent(ch_mr22n)
										.addComponent(ch_mr23n))
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_p_ipr1.createParallelGroup(Alignment.LEADING)
										.addComponent(cb_mr18v, 0, 140, Short.MAX_VALUE)
										.addComponent(cb_mr19v, 0, 140, Short.MAX_VALUE)
										.addComponent(cb_mr20v, 0, 140, Short.MAX_VALUE)
										.addComponent(cb_mr21v, 0, 140, Short.MAX_VALUE)
										.addComponent(cb_mr22v, 0, 140, Short.MAX_VALUE)
										.addComponent(cb_mr23v, 0, 140, Short.MAX_VALUE)))
								.addGroup(gl_p_ipr1.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_p_ipr1.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_p_ipr1.createSequentialGroup()
											.addGap(59)
											.addGroup(gl_p_ipr1.createParallelGroup(Alignment.TRAILING, false)
												.addComponent(ch_mr10n, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
												.addGroup(gl_p_ipr1.createParallelGroup(Alignment.TRAILING)
													.addComponent(ch_mr12n, GroupLayout.DEFAULT_SIZE, 141, Short.MAX_VALUE)
													.addComponent(ch_mr11n, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
													.addComponent(label_44, Alignment.LEADING)
													.addComponent(ch_mr13n, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
													.addComponent(ch_mr14n, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)
													.addComponent(ch_mr15n, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 149, Short.MAX_VALUE)))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addGroup(gl_p_ipr1.createParallelGroup(Alignment.LEADING)
												.addComponent(cb_mr11v, 0, 141, Short.MAX_VALUE)
												.addComponent(cb_mr12v, 0, 141, Short.MAX_VALUE)
												.addComponent(cb_mr14v, 0, 141, Short.MAX_VALUE)
												.addComponent(cb_mr15v, 0, 141, Short.MAX_VALUE)
												.addComponent(cb_mr13v, 0, 141, Short.MAX_VALUE)
												.addComponent(cb_mr10v, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)))
										.addGroup(gl_p_ipr1.createSequentialGroup()
											.addGap(61)
											.addGroup(gl_p_ipr1.createParallelGroup(Alignment.LEADING)
												.addGroup(gl_p_ipr1.createSequentialGroup()
													.addComponent(ch_mr16n, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(cb_mr16v, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_p_ipr1.createSequentialGroup()
													.addComponent(ch_mr17n, GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(cb_mr17v, GroupLayout.PREFERRED_SIZE, 137, GroupLayout.PREFERRED_SIZE)))))))
							.addGap(16))
						.addGroup(gl_p_ipr1.createSequentialGroup()
							.addGap(34)
							.addComponent(t_mr4d, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_p_ipr1.setVerticalGroup(
			gl_p_ipr1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_p_ipr1.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblNewLabel_11)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_27)
						.addComponent(label_26)
						.addComponent(label_41)
						.addComponent(label_40))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label_28)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
						.addComponent(cb_mr1v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(ch_mr1n)
						.addComponent(ch_mr10n)
						.addComponent(cb_mr10v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_p_ipr1.createParallelGroup(Alignment.LEADING)
						.addComponent(t_mr1d, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
							.addComponent(ch_mr11n)
							.addComponent(cb_mr11v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_p_ipr1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_p_ipr1.createSequentialGroup()
							.addGap(11)
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
								.addComponent(cb_mr2v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(ch_mr2n)))
						.addGroup(gl_p_ipr1.createSequentialGroup()
							.addGap(4)
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
								.addComponent(ch_mr12n)
								.addComponent(cb_mr12v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_p_ipr1.createParallelGroup(Alignment.LEADING)
						.addComponent(label_44)
						.addComponent(t_mr2d, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_p_ipr1.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_p_ipr1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(label_31)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
								.addComponent(cb_mr3v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(ch_mr3n))
							.addGap(18)
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
								.addComponent(cb_mr4v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(ch_mr4n))
							.addGap(18)
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
								.addComponent(cb_mr5v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(ch_mr5n))
							.addGap(18)
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_p_ipr1.createSequentialGroup()
									.addGap(48)
									.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
										.addComponent(cb_mr7v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(ch_mr7n)))
								.addGroup(gl_p_ipr1.createSequentialGroup()
									.addGap(3)
									.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
										.addComponent(cb_mr6v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(ch_mr6n))))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(t_mr3d, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(label_37)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
								.addComponent(ch_mr8n)
								.addComponent(cb_mr8v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
								.addComponent(ch_mr9n)
								.addComponent(cb_mr9v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_p_ipr1.createSequentialGroup()
							.addGap(1)
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
								.addComponent(ch_mr13n)
								.addComponent(cb_mr13v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(14)
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
								.addComponent(ch_mr14n)
								.addComponent(cb_mr14v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(15)
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
								.addComponent(ch_mr15n)
								.addComponent(cb_mr15v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
								.addComponent(ch_mr16n)
								.addComponent(cb_mr16v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
								.addComponent(ch_mr17n)
								.addComponent(cb_mr17v, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(t_mr4d, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
								.addComponent(ch_mr18n)
								.addComponent(cb_mr18v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_p_ipr1.createSequentialGroup()
									.addGap(9)
									.addComponent(ch_mr19n))
								.addGroup(gl_p_ipr1.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cb_mr19v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
										.addComponent(cb_mr20v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(ch_mr20n))))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
								.addComponent(cb_mr21v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(ch_mr21n))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
								.addComponent(cb_mr22v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(ch_mr22n))
							.addGap(5)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_p_ipr1.createParallelGroup(Alignment.BASELINE)
						.addComponent(cb_mr23v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(ch_mr23n))
					.addGap(76))
		);
		p_ipr1.setLayout(gl_p_ipr1);
		
		JPanel p_ipr2 = new JPanel();
		tabbedPane.addTab("ИПР(прод.)", null, p_ipr2, null);
		
		JLabel lblNewLabel_12 = new JLabel("Психологическая реабилитация");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel label_29 = new JLabel("Назначение");
		label_29.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel label_30 = new JLabel("Выполнение");
		label_30.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel label_32 = new JLabel("Назначение");
		label_32.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel label_33 = new JLabel("Выполнение");
		label_33.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblNewLabel_14 = new JLabel("Профессиональная подготовка");
		lblNewLabel_14.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		ch_pr1n = new JCheckBox("психотерапия                           ");
		ch_pr1n.setHorizontalTextPosition(SwingConstants.LEFT);
			
		ch_pr2n = new JCheckBox("психокоррекция                       ");
		ch_pr2n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_pr3n = new JCheckBox("психол. помощь семье             ");
		ch_pr3n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_pr4n = new JCheckBox("профориентация                       ");
		ch_pr4n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_pr5n = new JCheckBox("проф.обучение                          ");
		ch_pr5n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		//JComboBox cb_pr1v = new JComboBox();
		cb_pr1v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_pr2v = new JComboBox();
		cb_pr2v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_pr3v = new JComboBox();
		cb_pr3v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_pr4v = new JComboBox();
		cb_pr4v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_pr5v = new JComboBox();
		cb_pr5v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		JLabel label_34 = new JLabel("Социальная реабилитация");
		label_34.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel label_35 = new JLabel("<html>Обучение <br>самообслуживанию</html>");
		
		JLabel label_36 = new JLabel("<html>Обучение пользоваться<br>тех.средствами</html>");
		
		ch_pr6n = new JCheckBox(" ");
		//ch_pr6n.setSelected(pr6 == 1);
		
		ch_pr7n = new JCheckBox(" ");
		//ch_pr7n.setSelected(pr7 == 1);
		
		//JComboBox cb_pr6v = new JComboBox();
		 cb_pr6v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_pr7v = new JComboBox();
		 cb_pr7v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		JLabel label_38 = new JLabel("Поддержка медико-социальной реабилитации");
		
		JLabel label_39 = new JLabel("Протезно-ортопедическая помощь");
		
		ch_pr8n = new JCheckBox("Корсет                       ");
		ch_pr8n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_pr9n = new JCheckBox("Спец. обувь              ");
		ch_pr9n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		//JComboBox cb_pr8v = new JComboBox();
		cb_pr8v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_pr9v = new JComboBox();
		cb_pr9v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		JLabel label_42 = new JLabel("Технические средства реабилитации");
		label_42.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		ch_pr10n = new JCheckBox("Калоприемник            ");
		ch_pr10n.setHorizontalTextPosition(SwingConstants.LEFT);
			
		ch_pr11n = new JCheckBox("Мочеприемник            ");
		ch_pr11n.setHorizontalTextPosition(SwingConstants.LEFT);
			
		ch_pr12n = new JCheckBox("Сурдотехника            ");
		ch_pr12n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		//JComboBox cb_pr10v = new JComboBox();
		cb_pr10v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_pr11v = new JComboBox();
		cb_pr11v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		//JComboBox cb_pr12v = new JComboBox();
		cb_pr12v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		ch_pr13n = new JCheckBox("Инвалидные коляски");
		ch_pr13n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_pr14n = new JCheckBox("Ходунки                      ");
		ch_pr14n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		cb_pr13v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
	
		cb_pr14v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		
		ch_pr15n = new JCheckBox("Очки, линзы               ");
		ch_pr15n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		ch_pr16n = new JCheckBox("Другое(указать)        ");
		ch_pr16n.setHorizontalTextPosition(SwingConstants.LEFT);
				
		cb_pr15v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
	
		cb_pr16v = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0t);
		t_pr1d = new CustomTextField();
		t_pr1d.setColumns(10);
		GroupLayout gl_p_ipr2 = new GroupLayout(p_ipr2);
		gl_p_ipr2.setHorizontalGroup(
			gl_p_ipr2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_p_ipr2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_p_ipr2.createParallelGroup(Alignment.LEADING)
						.addComponent(ch_pr5n)
						.addGroup(gl_p_ipr2.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(ch_pr4n, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblNewLabel_14, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_p_ipr2.createParallelGroup(Alignment.LEADING)
								.addComponent(ch_pr7n)
								.addComponent(ch_pr6n))))
					.addContainerGap(503, Short.MAX_VALUE))
				.addGroup(gl_p_ipr2.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_34)
					.addContainerGap(534, Short.MAX_VALUE))
				.addGroup(gl_p_ipr2.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_35)
					.addContainerGap(581, Short.MAX_VALUE))
				.addGroup(gl_p_ipr2.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_36)
					.addContainerGap(575, Short.MAX_VALUE))
				.addGroup(gl_p_ipr2.createSequentialGroup()
					.addGroup(gl_p_ipr2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_p_ipr2.createSequentialGroup()
							.addGap(72)
							.addComponent(label_29)
							.addGap(102)
							.addComponent(label_30))
						.addGroup(gl_p_ipr2.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_p_ipr2.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_12)
								.addGroup(gl_p_ipr2.createSequentialGroup()
									.addGroup(gl_p_ipr2.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(ch_pr1n, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(ch_pr2n, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(ch_pr3n, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE))
									.addGap(36)
									.addGroup(gl_p_ipr2.createParallelGroup(Alignment.LEADING, false)
										.addComponent(cb_pr3v, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(cb_pr2v, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(cb_pr1v, 0, 129, Short.MAX_VALUE)
										.addComponent(cb_pr4v, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(cb_pr5v, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(cb_pr6v, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(cb_pr7v, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
					.addGroup(gl_p_ipr2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_p_ipr2.createSequentialGroup()
							.addGap(77)
							.addComponent(label_32)
							.addPreferredGap(ComponentPlacement.RELATED, 85, Short.MAX_VALUE)
							.addComponent(label_33)
							.addGap(43))
						.addGroup(gl_p_ipr2.createSequentialGroup()
							.addGap(46)
							.addGroup(gl_p_ipr2.createParallelGroup(Alignment.LEADING)
								.addComponent(label_39)
								.addComponent(label_38)
								.addGroup(gl_p_ipr2.createSequentialGroup()
									.addGroup(gl_p_ipr2.createParallelGroup(Alignment.LEADING)
										.addComponent(ch_pr8n)
										.addComponent(ch_pr9n))
									.addGap(33)
									.addGroup(gl_p_ipr2.createParallelGroup(Alignment.LEADING)
										.addComponent(cb_pr9v, 0, 126, Short.MAX_VALUE)
										.addComponent(cb_pr8v, 0, 126, Short.MAX_VALUE)))
								.addComponent(label_42)
								.addGroup(gl_p_ipr2.createSequentialGroup()
									.addGroup(gl_p_ipr2.createParallelGroup(Alignment.LEADING)
										.addComponent(ch_pr10n)
										.addComponent(ch_pr11n)
										.addComponent(ch_pr12n)
										.addComponent(ch_pr13n)
										.addComponent(ch_pr14n)
										.addComponent(ch_pr15n)
										.addComponent(ch_pr16n))
									.addGap(42)
									.addGroup(gl_p_ipr2.createParallelGroup(Alignment.LEADING)
										.addComponent(cb_pr11v, 0, 113, Short.MAX_VALUE)
										.addComponent(cb_pr10v, 0, 113, Short.MAX_VALUE)
										.addComponent(cb_pr12v, 0, 113, Short.MAX_VALUE)
										.addComponent(cb_pr13v, 0, 113, Short.MAX_VALUE)
										.addComponent(cb_pr14v, 0, 113, Short.MAX_VALUE)
										.addComponent(cb_pr15v, 0, 113, Short.MAX_VALUE)
										.addComponent(cb_pr16v, 0, 113, Short.MAX_VALUE))))
							.addContainerGap())))
				.addGroup(gl_p_ipr2.createSequentialGroup()
					.addContainerGap(352, Short.MAX_VALUE)
					.addComponent(t_pr1d, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_p_ipr2.setVerticalGroup(
			gl_p_ipr2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_ipr2.createSequentialGroup()
					.addGap(20)
					.addGroup(gl_p_ipr2.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_29)
						.addComponent(label_30)
						.addComponent(label_32)
						.addComponent(label_33))
					.addGap(18)
					.addGroup(gl_p_ipr2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_12)
						.addComponent(label_38))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_p_ipr2.createParallelGroup(Alignment.BASELINE)
						.addComponent(ch_pr1n)
						.addComponent(cb_pr1v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_39))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_p_ipr2.createParallelGroup(Alignment.BASELINE)
						.addComponent(ch_pr2n)
						.addComponent(cb_pr2v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(ch_pr8n)
						.addComponent(cb_pr8v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_p_ipr2.createParallelGroup(Alignment.TRAILING)
						.addComponent(ch_pr3n)
						.addGroup(gl_p_ipr2.createParallelGroup(Alignment.BASELINE)
							.addComponent(cb_pr3v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(ch_pr9n)
							.addComponent(cb_pr9v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(13)
					.addGroup(gl_p_ipr2.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_14)
						.addComponent(label_42))
					.addGap(18)
					.addGroup(gl_p_ipr2.createParallelGroup(Alignment.BASELINE)
						.addComponent(ch_pr4n)
						.addComponent(cb_pr4v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(ch_pr10n)
						.addComponent(cb_pr10v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_p_ipr2.createParallelGroup(Alignment.BASELINE)
						.addComponent(ch_pr5n)
						.addComponent(cb_pr5v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(ch_pr11n)
						.addComponent(cb_pr11v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_p_ipr2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_p_ipr2.createSequentialGroup()
							.addGap(30)
							.addGroup(gl_p_ipr2.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_p_ipr2.createSequentialGroup()
									.addComponent(label_34)
									.addGap(18)
									.addComponent(label_35))
								.addGroup(gl_p_ipr2.createParallelGroup(Alignment.BASELINE)
									.addComponent(ch_pr6n)
									.addComponent(cb_pr6v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_p_ipr2.createParallelGroup(Alignment.TRAILING)
								.addComponent(label_36)
								.addGroup(gl_p_ipr2.createParallelGroup(Alignment.BASELINE)
									.addComponent(ch_pr7n)
									.addComponent(cb_pr7v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_p_ipr2.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_p_ipr2.createParallelGroup(Alignment.BASELINE)
								.addComponent(ch_pr12n)
								.addComponent(cb_pr12v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_p_ipr2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_p_ipr2.createSequentialGroup()
									.addComponent(ch_pr13n)
									.addGap(18)
									.addGroup(gl_p_ipr2.createParallelGroup(Alignment.BASELINE)
										.addComponent(ch_pr14n)
										.addComponent(cb_pr14v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addComponent(cb_pr13v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_p_ipr2.createParallelGroup(Alignment.BASELINE)
								.addComponent(ch_pr15n)
								.addComponent(cb_pr15v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGap(18)
					.addGroup(gl_p_ipr2.createParallelGroup(Alignment.LEADING)
						.addComponent(ch_pr16n)
						.addComponent(cb_pr16v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(t_pr1d, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(100, Short.MAX_VALUE))
		);
		p_ipr2.setLayout(gl_p_ipr2);
		
		JPanel p_ocen = new JPanel();
		tabbedPane.addTab("Оценка ИПР и исходы", null, p_ocen, null);
		
		JLabel lblNewLabel_13 = new JLabel("Оценка результатов выполнения индивидуальной программы реабилитации");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel label_20 = new JLabel("Медицинская реабилитация");
		label_20.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		cb_med_reab = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0n);
		
		JLabel label_21 = new JLabel("Психологическая реабилитация");
		label_21.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel label_22 = new JLabel("Профессиональная подготовка");
		label_22.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel label_23 = new JLabel("Социальная реабилитация");
		label_23.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel label_24 = new JLabel("Движение детей-инвалидов");
		label_24.setForeground(Color.RED);
		label_24.setBackground(Color.BLACK);
		label_24.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel label_25 = new JLabel("Причина смерти");
		
		cb_ps_reab = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0n);
		
		cb_prof_reab = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0n);
		
		cb_soc_reab = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0n);
		
		cb_zakl = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_v0h);
		
		t_zakl_name = new CustomTextField();
		t_zakl_name.setColumns(10);
		GroupLayout gl_p_ocen = new GroupLayout(p_ocen);
		gl_p_ocen.setHorizontalGroup(
			gl_p_ocen.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_ocen.createSequentialGroup()
					.addGap(28)
					.addGroup(gl_p_ocen.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblNewLabel_13)
						.addGroup(gl_p_ocen.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_p_ocen.createSequentialGroup()
								.addGroup(gl_p_ocen.createParallelGroup(Alignment.LEADING)
									.addComponent(label_22)
									.addComponent(label_23)
									.addComponent(label_24)
									.addComponent(label_25))
								.addGap(18)
								.addGroup(gl_p_ocen.createParallelGroup(Alignment.LEADING)
									.addComponent(t_zakl_name, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_p_ocen.createParallelGroup(Alignment.LEADING)
										.addComponent(cb_zakl, 0, 252, Short.MAX_VALUE)
										.addComponent(cb_soc_reab, 0, 252, Short.MAX_VALUE)
										.addComponent(cb_prof_reab, 0, 252, Short.MAX_VALUE))))
							.addGroup(gl_p_ocen.createSequentialGroup()
								.addGroup(gl_p_ocen.createParallelGroup(Alignment.LEADING)
									.addComponent(label_21)
									.addComponent(label_20))
								.addGap(18)
								.addGroup(gl_p_ocen.createParallelGroup(Alignment.LEADING)
									.addComponent(cb_med_reab, GroupLayout.PREFERRED_SIZE, 337, GroupLayout.PREFERRED_SIZE)
									.addComponent(cb_ps_reab, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
					.addContainerGap(130, Short.MAX_VALUE))
		);
		gl_p_ocen.setVerticalGroup(
			gl_p_ocen.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_ocen.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_13)
					.addGap(18)
					.addGroup(gl_p_ocen.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_20)
						.addComponent(cb_med_reab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_p_ocen.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_21)
						.addComponent(cb_ps_reab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addGroup(gl_p_ocen.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_22)
						.addComponent(cb_prof_reab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(20)
					.addGroup(gl_p_ocen.createParallelGroup(Alignment.TRAILING)
						.addComponent(label_23)
						.addComponent(cb_soc_reab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(27)
					.addGroup(gl_p_ocen.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_24)
						.addComponent(cb_zakl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(28)
					.addGroup(gl_p_ocen.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_25)
						.addComponent(t_zakl_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(309, Short.MAX_VALUE))
		);
		p_ocen.setLayout(gl_p_ocen);
		frame.getContentPane().setLayout(groupLayout);
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		super.onConnect(conn);
		if (conn instanceof thriftKartaRInv.Client) {
			tcl = thrClient;
		}
	}
	
	@Override
	public Object showModal(IClient parent, Object... params) {
		JDialog dialog = prepareModal(parent);
		showKart((int) params[0]);
		dialog.setVisible(true);
		disposeModal();
		return null;
	}
	
	private void showKart(int npasp) {
		try {
			pinvk = new Pinvk();
			patInf = new PatientCommonInfo();	
			patInf = MainForm.tcl.getPatientCommonInfo(npasp);
			
			lblpatient.setText(patInf.fam+" "+patInf.im+" "+patInf.ot);
			
			pinvk = MainForm.tcl.getPinvk(patInf.npasp);
			//} catch (KmiacServerException e1) {
			// TODO Auto-generated catch block
		//	e1.printStackTrace();
		
//	} catch (TException e1) {
			// TODO Auto-generated catch block
//		e1.printStackTrace();
//	}
//	try { 
			//pinvk = MainForm.tcl.getPinvk(searchedNpasp);
							
			bg_psih.clearSelection();
			radioButtonP.setSelected(pinvk.getNar1() == 0);
			radioButtonP_1.setSelected(pinvk.getNar1() == 1);
			radioButtonP_2.setSelected(pinvk.getNar1() == 2);
			radioButtonP_3.setSelected(pinvk.getNar1() == 3);
			radioButtonP_4.setSelected(pinvk.getNar1() == 4);
			bg_rech.clearSelection();
			radioButtonR.setSelected(pinvk.getNar2() == 0);
			radioButtonR_1.setSelected(pinvk.getNar2() == 1);
			radioButtonR_2.setSelected(pinvk.getNar2() == 2);
			radioButtonR_3.setSelected(pinvk.getNar2() == 3);
			radioButtonR_4.setSelected(pinvk.getNar2() == 4);
			bg_sens.clearSelection();
			radioButtonS.setSelected(pinvk.getNar3() == 0);
			radioButtonS_1.setSelected(pinvk.getNar3() == 1);
			radioButtonS_2.setSelected(pinvk.getNar3() == 2);
			radioButtonS_3.setSelected(pinvk.getNar3() == 3);
			radioButtonS_4.setSelected(pinvk.getNar3() == 4);
			bg_dinam.clearSelection();
			radioButtonD.setSelected(pinvk.getNar4() == 0);
			radioButtonD_1.setSelected(pinvk.getNar4() == 1);
			radioButtonD_2.setSelected(pinvk.getNar4() == 2);
			radioButtonD_3.setSelected(pinvk.getNar4() == 3);
			radioButtonD_4.setSelected(pinvk.getNar4() == 4);
			bg_zab.clearSelection();
			radioButtonZ.setSelected(pinvk.getNar5() == 0);
			radioButtonZ_1.setSelected(pinvk.getNar5() == 1);
			radioButtonZ_2.setSelected(pinvk.getNar5() == 2);
			radioButtonZ_3.setSelected(pinvk.getNar5() == 3);
			radioButtonZ_4.setSelected(pinvk.getNar5() == 4);
			bg_urod.clearSelection();
			radioButtonU.setSelected(pinvk.getNar6() == 0);
			radioButtonU_1.setSelected(pinvk.getNar6() == 1);
			radioButtonU_2.setSelected(pinvk.getNar6() == 2);
			radioButtonU_3.setSelected(pinvk.getNar6() == 3);
			radioButtonU_4.setSelected(pinvk.getNar6() == 4);
			bg_samob.clearSelection();
			radioButtonSo.setSelected(pinvk.getOgr1() == 0);
			radioButtonSo_1.setSelected(pinvk.getOgr1() == 1);
			radioButtonSo_2.setSelected(pinvk.getOgr1() == 2);
			radioButtonSo_3.setSelected(pinvk.getOgr1() == 3);
			radioButtonSo_4.setSelected(pinvk.getOgr1() == 4);
			bg_dvig.clearSelection();
			radioButtonSd.setSelected(pinvk.getOgr2() == 0);
			radioButtonSd_1.setSelected(pinvk.getOgr2() == 1);
			radioButtonSd_2.setSelected(pinvk.getOgr2() == 2);
			radioButtonSd_3.setSelected(pinvk.getOgr2() == 3);
			radioButtonSd_4.setSelected(pinvk.getOgr2() == 4);
			bg_orient.clearSelection();
			radioButtonOr.setSelected(pinvk.getOgr3() == 0);
			radioButtonOr_1.setSelected(pinvk.getOgr3() == 1);
			radioButtonOr_2.setSelected(pinvk.getOgr3() == 2);
			radioButtonOr_3.setSelected(pinvk.getOgr3() == 3);
			radioButtonOr_4.setSelected(pinvk.getOgr3() == 4);
			bg_obsh.clearSelection();
			radioButtonO.setSelected(pinvk.getOgr4() == 0);
			radioButtonO_1.setSelected(pinvk.getOgr4() == 1);
			radioButtonO_2.setSelected(pinvk.getOgr4() == 2);
			radioButtonO_3.setSelected(pinvk.getOgr4() == 3);
			radioButtonO_4.setSelected(pinvk.getOgr4() == 4);
			bg_poved.clearSelection();
			radioButtonK.setSelected(pinvk.getOgr5() == 0);
			radioButtonK_1.setSelected(pinvk.getOgr5() == 1);
			radioButtonK_2.setSelected(pinvk.getOgr5() == 2);
			radioButtonK_3.setSelected(pinvk.getOgr5() == 3);
			radioButtonK_4.setSelected(pinvk.getOgr5() == 4);
			bg_obuch.clearSelection();
			radioButtonOb.setSelected(pinvk.getOgr6() == 0);
			radioButtonOb_1.setSelected(pinvk.getOgr6() == 1);
			radioButtonOb_2.setSelected(pinvk.getOgr6() == 2);
			radioButtonOb_3.setSelected(pinvk.getOgr6() == 3);
			radioButtonOb_4.setSelected(pinvk.getOgr6() == 4);
			bg_trud.clearSelection();
			radioButtonT.setSelected(pinvk.getOgr7() == 0);
			radioButtonT_1.setSelected(pinvk.getOgr7() == 1);
			radioButtonT_2.setSelected(pinvk.getOgr7() == 2);
			radioButtonT_3.setSelected(pinvk.getOgr7() == 3);
			radioButtonT_4.setSelected(pinvk.getOgr7() == 4);
			
			
			//t_vrach.setText(String.valueOf(pinvk.getVrach()));
			t_vrach.setText(pinvk.getVrach());
			t_uchr.setText(pinvk.getUchr());
			t_nom_mse.setText(pinvk.getNom_mse());
			t_ruk_mse.setText(pinvk.getRuk_mse());
			t_diag.setText(pinvk.getDiag());
			t_diag_s1.setText(pinvk.getDiag_s1());
			t_diag_s2.setText(pinvk.getDiag_s2());
			t_diag_s3.setText(pinvk.getDiag_s3());
			t_oslog.setText(pinvk.getOslog());
			t_zakl_name.setText(pinvk.getZakl_name());
			t_mr1d.setText(pinvk.getMr1d());
			t_mr2d.setText(pinvk.getMr2d());
			t_mr3d.setText(pinvk.getMr3d());
			t_mr4d.setText(pinvk.getMr4d());
			t_pr1d.setText(pinvk.getPr1d());
			
			if (pinvk.isSetDatav()) t_datav.setDate(pinvk.getDatav());
			if (pinvk.isSetDataz()) t_dataz.setDate(pinvk.getDataz());
			if (pinvk.isSetD_osv()) t_d_osv.setDate(pinvk.getD_osv());
			if (pinvk.isSetD_otpr()) t_d_otpr.setDate(pinvk.getD_otpr());
			if (pinvk.isSetD_inv()) t_d_inv.setDate(pinvk.getD_inv());
			if (pinvk.isSetD_invp()) t_d_invp.setDate(pinvk.getD_invp());
			if (pinvk.isSetD_srok()) t_d_srok.setDate(pinvk.getD_srok());
			
			if (pinvk.getMesto1() != 0) cb_mesto1.setSelectedPcod(pinvk.mesto1);
			if (pinvk.getPreds() != 0) cb_preds.setSelectedPcod(pinvk.preds);
			if (pinvk.getRez_mse() != 0) cb_rez_mse.setSelectedPcod(pinvk.rez_mse);
			if (pinvk.getSrok_inv() != 0) cb_srok_inv.setSelectedPcod(pinvk.srok_inv);
			if (pinvk.getFactor() != 0) cb_factor.setSelectedPcod(pinvk.factor);
			if (pinvk.getFact1() != 0) cb_fact1.setSelectedPcod(pinvk.fact1);
			if (pinvk.getFact2() != 0) cb_fact2.setSelectedPcod(pinvk.fact2);
			if (pinvk.getFact3() != 0) cb_fact3.setSelectedPcod(pinvk.fact3);
			if (pinvk.getFact4() != 0) cb_fact4.setSelectedPcod(pinvk.fact4);
			if (pinvk.getPrognoz() != 0) cb_prognoz.setSelectedPcod(pinvk.prognoz);
			if (pinvk.getPotencial() != 0) cb_potencial.setSelectedPcod(pinvk.potencial);
			if (pinvk.getMed_reab() != 0) cb_med_reab.setSelectedPcod(pinvk.med_reab);
			if (pinvk.getPs_reab() != 0) cb_ps_reab.setSelectedPcod(pinvk.ps_reab);
			if (pinvk.getProf_reab() != 0) cb_prof_reab.setSelectedPcod(pinvk.prof_reab);
			if (pinvk.getSoc_reab() != 0) cb_soc_reab.setSelectedPcod(pinvk.soc_reab);
			if (pinvk.getKlin_prognoz() != 0) cb_klin_prognoz.setSelectedPcod(pinvk.klin_prognoz);		
			if (pinvk.getZakl() != 0) cb_zakl.setSelectedPcod(pinvk.zakl);
			if (pinvk.getMr1v() != 0) cb_mr1v.setSelectedPcod(pinvk.mr1v);
			if (pinvk.getMr2v() != 0) cb_mr2v.setSelectedPcod(pinvk.mr2v);							
			if (pinvk.getMr3v() != 0) cb_mr3v.setSelectedPcod(pinvk.mr3v);
			if (pinvk.getMr4v() != 0) cb_mr4v.setSelectedPcod(pinvk.mr4v);
			if (pinvk.getMr5v() != 0) cb_mr5v.setSelectedPcod(pinvk.mr5v);
			if (pinvk.getMr6v() != 0) cb_mr6v.setSelectedPcod(pinvk.mr6v);
			if (pinvk.getMr7v() != 0) cb_mr7v.setSelectedPcod(pinvk.mr7v);
			if (pinvk.getMr8v() != 0) cb_mr8v.setSelectedPcod(pinvk.mr8v);
			if (pinvk.getMr9v() != 0) cb_mr9v.setSelectedPcod(pinvk.mr9v);
			if (pinvk.getMr10v() != 0) cb_mr10v.setSelectedPcod(pinvk.mr10v);
			if (pinvk.getMr11v() != 0) cb_mr11v.setSelectedPcod(pinvk.mr11v);
			if (pinvk.getMr12v() != 0) cb_mr12v.setSelectedPcod(pinvk.mr12v);
			if (pinvk.getMr13v() != 0) cb_mr13v.setSelectedPcod(pinvk.mr13v);
			if (pinvk.getMr14v() != 0) cb_mr14v.setSelectedPcod(pinvk.mr14v);
			if (pinvk.getMr15v() != 0) cb_mr15v.setSelectedPcod(pinvk.mr15v);
			if (pinvk.getMr16v() != 0) cb_mr16v.setSelectedPcod(pinvk.mr16v);
			if (pinvk.getMr17v() != 0) cb_mr17v.setSelectedPcod(pinvk.mr17v);
			if (pinvk.getMr18v() != 0) cb_mr18v.setSelectedPcod(pinvk.mr18v);
			if (pinvk.getMr19v() != 0) cb_mr19v.setSelectedPcod(pinvk.mr19v);
			if (pinvk.getMr20v() != 0) cb_mr20v.setSelectedPcod(pinvk.mr20v);
			if (pinvk.getMr21v() != 0) cb_mr21v.setSelectedPcod(pinvk.mr21v);
			if (pinvk.getMr22v() != 0) cb_mr22v.setSelectedPcod(pinvk.mr22v);
			if (pinvk.getMr23v() != 0) cb_mr23v.setSelectedPcod(pinvk.mr23v);
			if (pinvk.getPr1v() != 0) cb_pr1v.setSelectedPcod(pinvk.pr1v);
			if (pinvk.getPr2v() != 0) cb_pr2v.setSelectedPcod(pinvk.pr2v);
			if (pinvk.getPr3v() != 0) cb_pr3v.setSelectedPcod(pinvk.pr3v);
			if (pinvk.getPr4v() != 0) cb_pr4v.setSelectedPcod(pinvk.pr4v);
			if (pinvk.getPr5v() != 0) cb_pr5v.setSelectedPcod(pinvk.pr5v);
			if (pinvk.getPr6v() != 0) cb_pr6v.setSelectedPcod(pinvk.pr6v);
			if (pinvk.getPr7v() != 0) cb_pr7v.setSelectedPcod(pinvk.pr7v);
			if (pinvk.getPr8v() != 0) cb_pr8v.setSelectedPcod(pinvk.pr8v);
			if (pinvk.getPr9v() != 0) cb_pr9v.setSelectedPcod(pinvk.pr9v);
			if (pinvk.getPr10v() != 0) cb_pr10v.setSelectedPcod(pinvk.pr10v);
			if (pinvk.getPr11v() != 0) cb_pr11v.setSelectedPcod(pinvk.pr11v);
			if (pinvk.getPr12v() != 0) cb_pr12v.setSelectedPcod(pinvk.pr12v);
			if (pinvk.getPr13v() != 0) cb_pr13v.setSelectedPcod(pinvk.pr13v);
			if (pinvk.getPr14v() != 0) cb_pr14v.setSelectedPcod(pinvk.pr14v);
			if (pinvk.getPr15v() != 0) cb_pr15v.setSelectedPcod(pinvk.pr15v);
			if (pinvk.getPr16v() != 0) cb_pr16v.setSelectedPcod(pinvk.pr16v);
			
			ch_mr1n.setSelected(pinvk.getMr1n() == 1);
			ch_mr2n.setSelected(pinvk.getMr2n() == 1);
			ch_mr3n.setSelected(pinvk.getMr3n() == 1);
			ch_mr4n.setSelected(pinvk.getMr4n() == 1);
			ch_mr5n.setSelected(pinvk.getMr5n() == 1);
			ch_mr6n.setSelected(pinvk.getMr6n() == 1);
			ch_mr7n.setSelected(pinvk.getMr7n() == 1);
			ch_mr8n.setSelected(pinvk.getMr8n() == 1);
			ch_mr9n.setSelected(pinvk.getMr9n() == 1);
			ch_mr10n.setSelected(pinvk.getMr10n() == 1);
			ch_mr11n.setSelected(pinvk.getMr11n() == 1);
			ch_mr12n.setSelected(pinvk.getMr12n() == 1);
			ch_mr13n.setSelected(pinvk.getMr13n() == 1);
			ch_mr14n.setSelected(pinvk.getMr14n() == 1);
			ch_mr15n.setSelected(pinvk.getMr15n() == 1);
			ch_mr16n.setSelected(pinvk.getMr16n() == 1);
			ch_mr17n.setSelected(pinvk.getMr17n() == 1);
			ch_mr18n.setSelected(pinvk.getMr18n() == 1);
			ch_mr19n.setSelected(pinvk.getMr19n() == 1);
			ch_mr20n.setSelected(pinvk.getMr20n() == 1);
			ch_mr21n.setSelected(pinvk.getMr21n() == 1);
			ch_mr22n.setSelected(pinvk.getMr22n() == 1);
			ch_mr23n.setSelected(pinvk.getMr23n() == 1);
			ch_pr1n.setSelected(pinvk.getPr1n() == 1);
			ch_pr2n.setSelected(pinvk.getPr2n() == 1);
			ch_pr3n.setSelected(pinvk.getPr3n() == 1);
			ch_pr4n.setSelected(pinvk.getPr4n() == 1);
			ch_pr5n.setSelected(pinvk.getPr5n() == 1);
			ch_pr6n.setSelected(pinvk.getPr6n() == 1);
			ch_pr7n.setSelected(pinvk.getPr7n() == 1);
			ch_pr8n.setSelected(pinvk.getPr8n() == 1);
			ch_pr9n.setSelected(pinvk.getPr9n() == 1);
			ch_pr10n.setSelected(pinvk.getPr10n() == 1);
			ch_pr11n.setSelected(pinvk.getPr11n() == 1);
			ch_pr12n.setSelected(pinvk.getPr12n() == 1);
			ch_pr13n.setSelected(pinvk.getPr13n() == 1);
			ch_pr14n.setSelected(pinvk.getPr14n() == 1);
			ch_pr15n.setSelected(pinvk.getPr15n() == 1);
			ch_pr16n.setSelected(pinvk.getPr16n() == 1);
			
		} catch (KmiacServerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			} catch (PinvkNotFoundException e1) {
			//	t_dataz.setText(System.currentTimeMillis());
			lblpatient.setText(patInf.fam+" "+patInf.im+" "+patInf.ot);
			pinvk.setNpasp(patInf.npasp);
			pinvk.getNinv();
			pinvk.setDatav(System.currentTimeMillis());
			pinvk.setDataz(System.currentTimeMillis());
								
		} catch (TException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
	
	