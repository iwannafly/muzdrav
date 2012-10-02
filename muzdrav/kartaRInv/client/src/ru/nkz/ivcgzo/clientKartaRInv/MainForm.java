package ru.nkz.ivcgzo.clientKartaRInv;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftKartaRInv.Pinvk;
import ru.nkz.ivcgzo.thriftKartaRInv.thriftKartaRInv;

import javax.swing.JTabbedPane;
import java.awt.BorderLayout;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.border.BevelBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.JCheckBox;
import javax.swing.SwingConstants;
import java.awt.ComponentOrientation;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends Client<thriftKartaRInv.Client> {

	private JFrame frame;
	private int nr1;
	private int og1;
	private int mr1;
	private int mr2;
	private int mr3;
	private int mr4;
	private int mr5;
	private int mr6;
	private int mr7;
	private int mr8;
	private int mr9;
	private int mr10;
	private int mr11;
	private int mr12;
	private int mr13;
	private int mr14;
	private int mr15;
	private int mr16;
	private int mr17;
	private int mr18;
	private int mr19;
	private int mr20;
	private int mr21;
	private int mr22;
	private int mr23;
	private int pr1;
	private int pr2;
	private int pr3;
	private int pr4;
	private int pr5;
	private int pr6;
	private int pr7;
	private int pr8;
	private int pr9;
	private int pr10;
	private int pr11;
	private int pr12;
	private int pr13;
	private int pr14;
	private int pr15;
	private int pr16;
	private CustomDateEditor t_datav;
	private JTextField t_vrach;
	private CustomDateEditor t_dataz;
	private JTextField t_uchr;
	private JTextField t_nom_mse;
	private CustomDateEditor t_d_osv;
	private JTextField t_ruk_mse;
	private CustomDateEditor t_d_otpr;
	private CustomDateEditor t_d_inv;
	private CustomDateEditor t_d_invp;
	private CustomDateEditor t_d_srok;
	private JTextField t_oslog;
	private JTextField t_diag;
	private JTextField t_diag_s1;
	private JTextField t_diag_s2;
	private JTextField t_diag_s3;
	private JTextField t_zakl_name;
	private ButtonGroup bg_psih;
	private ButtonGroup bg_rech;
	private ButtonGroup bg_sens;
	private ButtonGroup bg_dinam;
	private ButtonGroup bg_zab;
	private ButtonGroup bg_urod;
	private ButtonGroup bg_samob;
	private ButtonGroup bg_dvig;
	private ButtonGroup bg_orient;
	private ButtonGroup bg_obsh;
	private ButtonGroup bg_poved;
	private ButtonGroup bg_obuch;
	private ButtonGroup bg_trud;
	private JTextField t_mr1d;
	private JTextField t_mr2d;
	private JTextField t_mr3d;
	private JTextField t_mr4d;
	private JTextField t_pr1d;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cb_srok;
	/*private CustomDateEditor t_datav;*/

	/**
	 * Launch the application.
	 */

	/**
	 * Create the application.
	 */
	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(conMan, authInfo, thriftKartaRInv.Client.class, configuration.appId, configuration.thrPort, lncPrm);
	
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Карта ребенка-инвалида");
		frame.setBounds(100, 100, 717, 678);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 704, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 640, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(34, Short.MAX_VALUE))
		);
		
		JPanel p_rez_exp = new JPanel();
		tabbedPane.addTab("Результаты экспертизы", null, p_rez_exp, null);
		
		JLabel l_datzap = new JLabel("Дата заполнения карты");
		l_datzap.setForeground(Color.RED);
		
		t_datav = new CustomDateEditor();
		t_datav.setColumns(10);
		
		JLabel l_glvrach = new JLabel("Главный врач");
		l_glvrach.setForeground(Color.RED);
		
		t_vrach = new JTextField();
		t_vrach.setColumns(10);
		
		JLabel l_datvbaz = new JLabel("Дата записи в базу");
		
		t_dataz = new CustomDateEditor();
		t_dataz.setColumns(10);
		
		JLabel l_mpreb = new JLabel("Место пребывания ребенка");
		l_mpreb.setForeground(Color.RED);
		
		JComboBox cB_mesto1 = new JComboBox();
		
		JLabel l_predst = new JLabel("Родители / представители ребенка");
		l_predst.setForeground(Color.RED);
		
		JComboBox cB_preds = new JComboBox();
		
		JLabel label = new JLabel("ФИО / название учреждения");
		label.setForeground(Color.RED);
		
		t_uchr = new JTextField();
		t_uchr.setColumns(10);
		
		JPanel p_medsoc = new JPanel();
		p_medsoc.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0420\u0435\u0437\u0443\u043B\u044C\u0442\u0430\u0442\u044B \u043C\u0435\u0434\u0438\u043A\u043E-\u0441\u043E\u0446\u0438\u0430\u043B\u044C\u043D\u043E\u0439 \u044D\u043A\u0441\u043F\u0435\u0440\u0442\u0438\u0437\u044B", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		
		JButton btn_expkart = new JButton("Экспорт карты в МСЭ");
		btn_expkart.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JButton btnNewButton = new JButton("Добавить");
		btnNewButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent paee) {
				Pinvk invk = new Pinvk();
				invk.setD_inv(t_d_inv.getDate().getTime());
				invk.setD_invp(t_d_invp.getDate().getTime());
				invk.setD_osv(t_d_osv.getDate().getTime());
				invk.setD_otpr(t_d_otpr.getDate().getTime());
				invk.setDatav(t_datav.getDate().getTime());
				invk.setDataz(t_dataz.getDate().getTime());
				invk.setD_srok(t_d_srok.getDate().getTime());
				invk.setDiag("");
				invk.setDiag_s1("");
				invk.setDiag_s2("");
				invk.setDiag_s3("");
				invk.setVrach(getTextOrNull(t_vrach.getText()));
				invk.setUchr(getTextOrNull(t_uchr.getText()));
				invk.setNom_mse(getTextOrNull(t_nom_mse.getText()));
				invk.setRuk_mse(getTextOrNull(t_ruk_mse.getText()));
				invk.setZakl_name(getTextOrNull(t_zakl_name.getText()));
				invk.setMr1d(getTextOrNull(t_mr1d.getText()));
				invk.setMr2d(getTextOrNull(t_mr2d.getText()));
				invk.setMr3d(getTextOrNull(t_mr3d.getText()));
				invk.setMr4d(getTextOrNull(t_mr4d.getText()));
				invk.setPr1d(getTextOrNull(t_mr4d.getText()));
				
			//	invk.setD_srok(cb_srok.getDate().getTime());
			}

		private String getTextOrNull(String text) {
			// TODO Auto-generated method stub
			return null;
		}
		});
		JButton button = new JButton("Сохранить");
		
		JButton btnNewButton_1 = new JButton("Удалить");
		
		
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
									.addComponent(cB_mesto1, 0, 168, Short.MAX_VALUE)
									.addComponent(cB_preds, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addComponent(t_uchr, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_p_rez_exp.createSequentialGroup()
							.addGap(38)
							.addComponent(label))
						.addGroup(gl_p_rez_exp.createSequentialGroup()
							.addGap(19)
							.addGroup(gl_p_rez_exp.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_p_rez_exp.createSequentialGroup()
									.addGap(10)
									.addComponent(btnNewButton)
									.addGap(18)
									.addComponent(button)
									.addGap(18)
									.addComponent(btnNewButton_1)
									.addGap(48)
									.addComponent(btn_expkart))
								.addComponent(p_medsoc, GroupLayout.PREFERRED_SIZE, 634, GroupLayout.PREFERRED_SIZE))))
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
						.addComponent(cB_mesto1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(l_mpreb))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_p_rez_exp.createParallelGroup(Alignment.BASELINE)
						.addComponent(l_predst)
						.addComponent(cB_preds, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_p_rez_exp.createParallelGroup(Alignment.BASELINE)
						.addComponent(label)
						.addComponent(t_uchr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(p_medsoc, GroupLayout.PREFERRED_SIZE, 435, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_p_rez_exp.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(button)
						.addComponent(btn_expkart)
						.addComponent(btnNewButton_1))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		JLabel label_1 = new JLabel("Акт МСЭ №");
		
		t_nom_mse = new JTextField();
		t_nom_mse.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Дата освидетельствования");
		
		t_d_osv = new CustomDateEditor();
		t_d_osv.setColumns(10);
		
		JLabel label_2 = new JLabel("Рук.Фед.учрежд.МСЭ");
		
		t_ruk_mse = new JTextField();
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
		
		JComboBox cb_rez_mse = new JComboBox();
		
		JLabel label_4 = new JLabel("Дата повтор. установления  инв-ти");
		label_4.setForeground(Color.RED);
		
		t_d_invp = new CustomDateEditor();
		t_d_invp.setColumns(10);
		
		JLabel label_5 = new JLabel("Срок, на который дана инвалидность");
		label_5.setForeground(Color.RED);
		
//		JComboBox cb_srok = new JComboBox();
		JComboBox cb_srok_inv = new ThriftStringClassifierCombobox<>(true);
		
		JLabel label_6 = new JLabel("До какой даты инвалидность");
		t_d_srok = new CustomDateEditor();
		t_d_srok.setColumns(10);
		GroupLayout gl_p_medsoc = new GroupLayout(p_medsoc);
		gl_p_medsoc.setHorizontalGroup(
			gl_p_medsoc.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_medsoc.createSequentialGroup()
					.addGroup(gl_p_medsoc.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_p_medsoc.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_p_medsoc.createParallelGroup(Alignment.LEADING)
								.addComponent(label_1)
								.addComponent(lblNewLabel)
								.addComponent(label_2)
								.addComponent(lblNewLabel_1)
								.addComponent(label_3))
							.addGap(29)
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
								.addComponent(cb_srok_inv, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(32, Short.MAX_VALUE))
		);
		gl_p_medsoc.setVerticalGroup(
			gl_p_medsoc.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_medsoc.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_p_medsoc.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(t_nom_mse, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
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
					.addContainerGap(70, Short.MAX_VALUE))
		);
		p_medsoc.setLayout(gl_p_medsoc);
		p_rez_exp.setLayout(gl_p_rez_exp);
		
		JPanel p_nar = new JPanel();
		tabbedPane.addTab("Нарушения", null, p_nar, null);
		
		JLabel l_diagosn = new JLabel("<html>Диагноз основного  заболевания, <br> приведший к ограничению жизнедеятельности (МКБ-10) </html>");
		l_diagosn.setForeground(Color.RED);
		l_diagosn.setFont(new Font("Tahoma", Font.PLAIN, 11));
		l_diagosn.setBackground(new Color(236, 233, 216));
		
		JLabel l_namdiag = new JLabel("наим.диагноза");
		
		JLabel label_14 = new JLabel("Сопутствующие заболевания");
		
		JLabel label_15 = new JLabel("<html>Осложнения основного<br> диагноза (вписать)</html>");
		label_15.setForeground(Color.BLACK);
		
		t_oslog = new JTextField();
		t_oslog.setColumns(10);
		
		JLabel lblNewLabel_9 = new JLabel("<html>Причинные факторы,обусловившие<br>возникновение инвалидности</html>");
		lblNewLabel_9.setForeground(Color.RED);
		lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		JComboBox cb_faktor = new JComboBox();
		
		JLabel label_16 = new JLabel("Главное нарушение в состоянии здоровья");
		label_16.setForeground(Color.RED);
		
		JComboBox cb_fact1 = new JComboBox();
		
		JComboBox cb_fact2 = new JComboBox();
		
		JLabel lblNewLabel_10 = new JLabel("Ведущее ограничение жизнедеятельности");
		lblNewLabel_10.setForeground(Color.RED);
		
		JComboBox cb_fact3 = new JComboBox();
		
		JComboBox cB_fact4 = new JComboBox();
		
		JLabel label_17 = new JLabel("Реабилитационный прогноз");
		label_17.setForeground(Color.RED);
		
		JLabel label_18 = new JLabel("Реабилитационный потенциал");
		
		JLabel label_19 = new JLabel("Клинический прогноз");
		
		JComboBox cb_prognoz = new JComboBox();
		
		JComboBox cb_potencial = new JComboBox();
		
		JComboBox cb_klin_prognoz = new JComboBox();
		
		t_diag = new JTextField();
		t_diag.setColumns(10);
		
		t_diag_s1 = new JTextField();
		t_diag_s1.setColumns(10);
		
		t_diag_s2 = new JTextField();
		t_diag_s2.setColumns(10);
		
		t_diag_s3 = new JTextField();
		t_diag_s3.setColumns(10);
		GroupLayout gl_p_nar = new GroupLayout(p_nar);
		gl_p_nar.setHorizontalGroup(
			gl_p_nar.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_nar.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_p_nar.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_p_nar.createSequentialGroup()
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
										.addGap(18)
										.addGroup(gl_p_nar.createParallelGroup(Alignment.LEADING, false)
											.addGroup(gl_p_nar.createSequentialGroup()
												.addGap(6)
												.addComponent(t_diag_s1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(t_diag_s2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
											.addComponent(t_oslog))
										.addGap(18)
										.addComponent(t_diag_s3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_p_nar.createSequentialGroup()
										.addComponent(lblNewLabel_9, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(cb_faktor, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
								.addGroup(gl_p_nar.createSequentialGroup()
									.addGroup(gl_p_nar.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(cb_fact2, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(cb_fact1, Alignment.LEADING, 0, 280, Short.MAX_VALUE))
									.addGap(62)
									.addGroup(gl_p_nar.createParallelGroup(Alignment.LEADING, false)
										.addComponent(cB_fact4, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(cb_fact3, 0, 261, Short.MAX_VALUE))))
							.addContainerGap(89, Short.MAX_VALUE))
						.addGroup(gl_p_nar.createSequentialGroup()
							.addComponent(label_16)
							.addPreferredGap(ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
							.addComponent(lblNewLabel_10)
							.addGap(104))
						.addGroup(gl_p_nar.createSequentialGroup()
							.addGroup(gl_p_nar.createParallelGroup(Alignment.LEADING)
								.addComponent(label_17)
								.addComponent(label_18)
								.addComponent(label_19))
							.addGap(33)
							.addGroup(gl_p_nar.createParallelGroup(Alignment.LEADING, false)
								.addComponent(cb_klin_prognoz, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(cb_potencial, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(cb_prognoz, 0, 208, Short.MAX_VALUE))
							.addContainerGap(296, Short.MAX_VALUE))))
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
						.addComponent(t_diag_s2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(t_diag_s3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_p_nar.createParallelGroup(Alignment.TRAILING)
						.addComponent(label_15)
						.addComponent(t_oslog, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(21)
					.addGroup(gl_p_nar.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_9)
						.addComponent(cb_faktor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_p_nar.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_16)
						.addComponent(lblNewLabel_10))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_p_nar.createParallelGroup(Alignment.BASELINE)
						.addComponent(cb_fact1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cb_fact3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_p_nar.createParallelGroup(Alignment.BASELINE)
						.addComponent(cb_fact2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cB_fact4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(35)
					.addGroup(gl_p_nar.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_17)
						.addComponent(cb_prognoz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_p_nar.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_18)
						.addComponent(cb_potencial, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_p_nar.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_19)
						.addComponent(cb_klin_prognoz, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(69, Short.MAX_VALUE))
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
		
		JRadioButton radioButtonT = new JRadioButton("0");
		
		JRadioButton radioButtonT_1 = new JRadioButton("1");
		
		JRadioButton radioButtonT_2 = new JRadioButton("2");
		
		JRadioButton radioButtonT_3 = new JRadioButton("3");
		
		JRadioButton radioButtonT_4 = new JRadioButton("4");
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
		
		JRadioButton radioButtonOb = new JRadioButton("0");
		
		JRadioButton radioButtonOb_1 = new JRadioButton("1");
		
		JRadioButton radioButtonOb_2 = new JRadioButton("2");
		
		JRadioButton radioButtonOb_3 = new JRadioButton("3");
		
		JRadioButton radioButtonOb_4 = new JRadioButton("4");
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
		
		JRadioButton radioButtonK = new JRadioButton("0");
		
		JRadioButton radioButtonK_1 = new JRadioButton("1");
		
		JRadioButton radioButtonK_2 = new JRadioButton("2");
		
		JRadioButton radioButtonK_3 = new JRadioButton("3");
		
		JRadioButton radioButtonK_4 = new JRadioButton("4");
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
		
		JRadioButton radioButtonO = new JRadioButton("0");
		
		JRadioButton radioButtonO_1 = new JRadioButton("1");
		
		JRadioButton radioButtonO_2 = new JRadioButton("2");
		
		JRadioButton radioButtonO_3 = new JRadioButton("3");
		
		JRadioButton radioButtonO_4 = new JRadioButton("4");
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
		
		JRadioButton radioButtonOr = new JRadioButton("0");
		
		JRadioButton radioButtonOr_1 = new JRadioButton("1");
		
		JRadioButton radioButtonOr_2 = new JRadioButton("2");
		
		JRadioButton radioButtonOr_3 = new JRadioButton("3");
		
		JRadioButton radioButtonOr_4 = new JRadioButton("4");
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
		
		JRadioButton radioButtonSd = new JRadioButton("0");
		
		JRadioButton radioButtonSd_1 = new JRadioButton("1");
		
		JRadioButton radioButtonSd_2 = new JRadioButton("2");
		
		JRadioButton radioButtonSd_3 = new JRadioButton("3");
		
		JRadioButton radioButtonSd_4 = new JRadioButton("4");
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
		
		JRadioButton radioButtonSo = new JRadioButton("0");
		
		JRadioButton radioButtonSo_1 = new JRadioButton("1");
		
		JRadioButton radioButtonSo_2 = new JRadioButton("2");
		
		JRadioButton radioButtonSo_3 = new JRadioButton("3");
		
		JRadioButton radioButtonSo_4 = new JRadioButton("4");
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
		
		JRadioButton radioButtonU = new JRadioButton("0");
		
		JRadioButton radioButtonU_1 = new JRadioButton("1");
		
		JRadioButton radioButtonU_2 = new JRadioButton("2");
		
		JRadioButton radioButtonU_3 = new JRadioButton("3");
		
		JRadioButton radioButtonU_4 = new JRadioButton("4");
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
		
		JRadioButton radioButtonZ = new JRadioButton("0");
		
		JRadioButton radioButtonZ_1 = new JRadioButton("1");
		
		JRadioButton radioButtonZ_2 = new JRadioButton("2");
		
		JRadioButton radioButtonZ_3 = new JRadioButton("3");
		
		JRadioButton radioButtonZ_4 = new JRadioButton("4");
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
		
		JRadioButton radioButtonD = new JRadioButton("0");
		
		JRadioButton radioButtonD_1 = new JRadioButton("1");
		
		JRadioButton radioButtonD_2 = new JRadioButton("2");
		
		JRadioButton radioButtonD_3 = new JRadioButton("3");
		
		JRadioButton radioButtonD_4 = new JRadioButton("4");
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
		
		JRadioButton radioButtonS = new JRadioButton("0");
		
		JRadioButton radioButtonS_1 = new JRadioButton("1");
		
		JRadioButton radioButtonS_2 = new JRadioButton("2");
		
		JRadioButton radioButtonS_3 = new JRadioButton("3");
		
		JRadioButton radioButtonS_4 = new JRadioButton("4");
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
		
		JRadioButton radioButtonR = new JRadioButton("0");
		
		JRadioButton radioButtonR_1 = new JRadioButton("1");
		
		JRadioButton radioButtonR_2 = new JRadioButton("2");
		
		JRadioButton radioButtonR_3 = new JRadioButton("3");
		
		JRadioButton radioButtonR_4 = new JRadioButton("4");
		
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
		
		JRadioButton radioButtonP = new JRadioButton("0");
		
		JRadioButton radioButtonP_1 = new JRadioButton("1");
		
		
		JRadioButton radioButtonP_2 = new JRadioButton("2");
		
		JRadioButton radioButtonP_3 = new JRadioButton("3");
		
		JRadioButton radioButtonP_4 = new JRadioButton("4");
		
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
		
		JComboBox cb_mr1v = new JComboBox();
		
		t_mr1d = new JTextField();
		t_mr1d.setColumns(10);
		
		JComboBox cb_mr2v = new JComboBox();
		
		t_mr2d = new JTextField();
		t_mr2d.setColumns(10);
		
		JLabel label_31 = new JLabel("Физические методы");
		
		JComboBox cb_mr3v = new JComboBox();
		
		JComboBox cb_mr4v = new JComboBox();
		
		t_mr3d = new JTextField();
		t_mr3d.setColumns(10);
		
		JLabel label_37 = new JLabel("Механические методы");
		
		JComboBox cb_mr5v = new JComboBox();
		
		JComboBox cb_mr6v = new JComboBox();
		
		JComboBox cb_mr7v = new JComboBox();
		
		JComboBox cb_mr9v = new JComboBox();
		
		JLabel label_40 = new JLabel("Назначение");
		label_40.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel label_41 = new JLabel("Выполнение");
		label_41.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JComboBox cb_mr10v = new JComboBox();
		
		JComboBox cb_mr11v = new JComboBox();
		
		JComboBox cb_mr12v = new JComboBox();
		
		JLabel label_44 = new JLabel("Водо- и бальнеотерапия");
		
		JComboBox cb_mr13v = new JComboBox();
		
		JComboBox cb_mr14v = new JComboBox();
		
		JComboBox cb_mr15v = new JComboBox();
		
		JComboBox cb_mr16v = new JComboBox();
		
		JComboBox cb_mr17v = new JComboBox();
		
		t_mr4d = new JTextField();
		t_mr4d.setColumns(10);
		
		JComboBox cb_mr21v = new JComboBox();
		
		JComboBox cb_mr22v = new JComboBox();
		
		JComboBox cb_mr23v = new JComboBox();
		
		final JCheckBox ch_mr1n = new JCheckBox("медикаменты                ");
		ch_mr1n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr1n.setSelected(mr1 == 1);
		
		final JCheckBox ch_mr2n = new JCheckBox("спец.продукты             ");
		ch_mr2n.setHorizontalAlignment(SwingConstants.LEFT);
		ch_mr2n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr2n.setSelected(mr2 == 1);
		
		final JCheckBox ch_mr3n = new JCheckBox("электролечение          ");
		ch_mr3n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr3n.setSelected(mr3 == 1);
		
		final JCheckBox ch_mr4n = new JCheckBox("электростимуляция    ");
		ch_mr4n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr4n.setSelected(mr4 == 1);
		
		final JCheckBox ch_mr6n = new JCheckBox("баротерапия                ");
		ch_mr6n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr6n.setSelected(mr6 == 1);
		
		final JCheckBox ch_mr5n = new JCheckBox("лазеротерапия            ");
		ch_mr5n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr5n.setSelected(mr5 == 1);
		
		final JCheckBox ch_mr7n = new JCheckBox("другие(указать)          ");
		ch_mr7n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr7n.setSelected(mr7 == 1);
		
		final JCheckBox ch_mr8n = new JCheckBox("механотерапия              ");
		ch_mr8n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr8n.setSelected(mr8 == 1);
		
		JComboBox cb_mr8v = new JComboBox();
		
		final JCheckBox ch_mr9n = new JCheckBox("кинезотерапия               ");
		ch_mr9n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr9n.setSelected(mr9 == 1);
		
		final JCheckBox ch_mr10n = new JCheckBox("Массаж                        ");
		ch_mr10n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr10n.setSelected(mr10 == 1);
		
		final JCheckBox ch_mr11n = new JCheckBox("Рефлексотерапия        ");
		ch_mr11n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr11n.setSelected(mr11 == 1);
		
		final JCheckBox ch_mr12n = new JCheckBox("Трудотерапия              ");
		ch_mr12n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr12n.setSelected(mr12 == 1);
		
		final JCheckBox ch_mr13n = new JCheckBox("бассейн                         ");
		ch_mr13n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr13n.setSelected(mr13 == 1);
		
		final JCheckBox ch_mr14n = new JCheckBox("лечебные ванны          ");
		ch_mr14n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr14n.setSelected(mr14 == 1);
		
		final JCheckBox ch_mr15n = new JCheckBox("лечебные души            ");
		ch_mr15n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr15n.setSelected(mr15 == 1);
		
		final JCheckBox ch_mr16n = new JCheckBox("грязелечение               ");
		ch_mr16n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr16n.setSelected(mr16 == 1);
		
		final JCheckBox ch_mr18n = new JCheckBox("Логопедич.помощь       ");
		ch_mr18n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr18n.setSelected(mr18 == 1);
		
		final JCheckBox ch_mr19n = new JCheckBox("Лечебная физ-ра           ");
		ch_mr19n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr19n.setSelected(mr19 == 1);
		
		final JCheckBox ch_mr20n = new JCheckBox("СКЛ                                 ");
		ch_mr20n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr20n.setSelected(mr20 == 1);
		
		final JCheckBox ch_mr17n = new JCheckBox("другие(указать)          ");
		ch_mr17n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr17n.setSelected(mr17 == 1);
		
		final JCheckBox ch_mr21n = new JCheckBox("Реконстр.хирургия       ");
		ch_mr21n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr21n.setSelected(mr21 == 1);
		
		final JCheckBox ch_mr22n = new JCheckBox("Протезирование            ");
		ch_mr22n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr22n.setSelected(mr22 == 1);
		
		final JCheckBox ch_mr23n = new JCheckBox("Ортезирование              ");
		ch_mr23n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_mr23n.setSelected(mr23 == 1);
		
		JComboBox cb_mr18v = new JComboBox();
		
		JComboBox cb_mr19v = new JComboBox();
		
		JComboBox cb_mr20v = new JComboBox();
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
		
		final JCheckBox ch_pr1n = new JCheckBox("психотерапия                           ");
		ch_pr1n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_pr1n.setSelected(pr1 == 1);
		
		final JCheckBox ch_pr2n = new JCheckBox("психокоррекция                       ");
		ch_pr2n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_pr2n.setSelected(pr2 == 1);
		
		final JCheckBox ch_pr3n = new JCheckBox("психол. помощь семье             ");
		ch_pr3n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_pr3n.setSelected(pr3 == 1);
		
		final JCheckBox ch_pr4n = new JCheckBox("профориентация                       ");
		ch_pr4n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_pr4n.setSelected(pr4 == 1);
		
		final JCheckBox ch_pr5n = new JCheckBox("проф.обучение                          ");
		ch_pr5n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_pr5n.setSelected(pr5 == 1);
		
		JComboBox cb_pr1v = new JComboBox();
		
		JComboBox cb_pr2v = new JComboBox();
		
		JComboBox cb_pr3v = new JComboBox();
		
		JComboBox cb_pr4v = new JComboBox();
		
		JComboBox cb_pr5v = new JComboBox();
		
		JLabel label_34 = new JLabel("Социальная реабилитация");
		label_34.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel label_35 = new JLabel("<html>Обучение <br>самообслуживанию</html>");
		
		JLabel label_36 = new JLabel("<html>Обучение пользоваться<br>тех.средствами</html>");
		
		final JCheckBox ch_pr6n = new JCheckBox(" ");
		ch_pr6n.setSelected(pr6 == 1);
		
		final JCheckBox ch_pr7n = new JCheckBox(" ");
		ch_pr7n.setSelected(pr7 == 1);
		
		JComboBox cb_pr6v = new JComboBox();
		
		JComboBox cb_pr7v = new JComboBox();
		
		JLabel label_38 = new JLabel("Поддержка медико-социальной реабилитации");
		
		JLabel label_39 = new JLabel("Протезно-ортопедическая помощь");
		
		final JCheckBox ch_pr8n = new JCheckBox("Корсет                       ");
		ch_pr8n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_pr8n.setSelected(pr8 == 1);
		
		final JCheckBox ch_pr9n = new JCheckBox("Спец. обувь              ");
		ch_pr9n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_pr9n.setSelected(pr9 == 1);
		
		JComboBox cb_pr8v = new JComboBox();
		
		JComboBox cb_pr9v = new JComboBox();
		
		JLabel label_42 = new JLabel("Технические средства реабилитации");
		label_42.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		final JCheckBox ch_pr10n = new JCheckBox("Калоприемник            ");
		ch_pr10n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_pr10n.setSelected(pr10 == 1);
		
		final JCheckBox ch_pr11n = new JCheckBox("Мочеприемник            ");
		ch_pr11n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_pr11n.setSelected(pr11 == 1);
		
		final JCheckBox ch_pr12n = new JCheckBox("Сурдотехника            ");
		ch_pr12n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_pr12n.setSelected(pr12 == 1);
		
		JComboBox cb_pr10v = new JComboBox();
		
		JComboBox cb_pr11v = new JComboBox();
		
		JComboBox cb_pr12v = new JComboBox();
		
		final JCheckBox ch_pr13n = new JCheckBox("Инвалидные коляски");
		ch_pr13n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_pr13n.setSelected(pr13 == 1);
		
		final JCheckBox ch_pr14n = new JCheckBox("Ходунки                      ");
		ch_pr14n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_pr14n.setSelected(pr14 == 1);
		
		JComboBox cb_pr13v = new JComboBox();
		
		JComboBox cb_pr14v = new JComboBox();
		
		final JCheckBox ch_pr15n = new JCheckBox("Очки, линзы               ");
		ch_pr15n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_pr15n.setSelected(pr15 == 1);
		
		final JCheckBox ch_pr16n = new JCheckBox("Другое(указать)        ");
		ch_pr16n.setHorizontalTextPosition(SwingConstants.LEFT);
		ch_pr16n.setSelected(pr16 == 1);
		
		JComboBox cb_pr15v = new JComboBox();
		
		JComboBox cb_pr16v = new JComboBox();
		
		t_pr1d = new JTextField();
		t_pr1d.setColumns(10);
		GroupLayout gl_p_ipr2 = new GroupLayout(p_ipr2);
		gl_p_ipr2.setHorizontalGroup(
			gl_p_ipr2.createParallelGroup(Alignment.LEADING)
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
					.addContainerGap(674, Short.MAX_VALUE))
				.addGroup(gl_p_ipr2.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_34)
					.addContainerGap(534, Short.MAX_VALUE))
				.addGroup(gl_p_ipr2.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_35)
					.addContainerGap(573, Short.MAX_VALUE))
				.addGroup(gl_p_ipr2.createSequentialGroup()
					.addContainerGap()
					.addComponent(label_36)
					.addContainerGap(585, Short.MAX_VALUE))
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
										.addComponent(cb_pr11v, 0, 119, Short.MAX_VALUE)
										.addComponent(cb_pr10v, 0, 119, Short.MAX_VALUE)
										.addComponent(cb_pr12v, 0, 119, Short.MAX_VALUE)
										.addComponent(cb_pr13v, 0, 113, Short.MAX_VALUE)
										.addComponent(cb_pr14v, 0, 113, Short.MAX_VALUE)
										.addComponent(cb_pr15v, 0, 113, Short.MAX_VALUE)
										.addComponent(cb_pr16v, 0, 113, Short.MAX_VALUE))))
							.addContainerGap())))
				.addGroup(Alignment.TRAILING, gl_p_ipr2.createSequentialGroup()
					.addContainerGap(407, Short.MAX_VALUE)
					.addComponent(t_pr1d, GroupLayout.PREFERRED_SIZE, 282, GroupLayout.PREFERRED_SIZE)
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
					.addContainerGap(121, Short.MAX_VALUE))
		);
		p_ipr2.setLayout(gl_p_ipr2);
		
		JPanel p_ocen = new JPanel();
		tabbedPane.addTab("Оценка ИПР и исходы", null, p_ocen, null);
		
		JLabel lblNewLabel_13 = new JLabel("Оценка результатов выполнения индивидуальной программы реабилитации");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel label_20 = new JLabel("Медицинская реабилитация");
		label_20.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JComboBox cb_med_reab = new JComboBox();
		
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
		
		JComboBox cb_ps_reab = new JComboBox();
		
		JComboBox cb_prof_reab = new JComboBox();
		
		JComboBox cb_soc_reab = new JComboBox();
		
		JComboBox cb_zakl = new JComboBox();
		
		t_zakl_name = new JTextField();
		t_zakl_name.setColumns(10);
		GroupLayout gl_p_ocen = new GroupLayout(p_ocen);
		gl_p_ocen.setHorizontalGroup(
			gl_p_ocen.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_p_ocen.createSequentialGroup()
					.addGap(28)
					.addGroup(gl_p_ocen.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblNewLabel_13)
						.addGroup(gl_p_ocen.createParallelGroup(Alignment.TRAILING)
							.addGroup(Alignment.LEADING, gl_p_ocen.createSequentialGroup()
								.addGroup(gl_p_ocen.createParallelGroup(Alignment.LEADING)
									.addComponent(label_22)
									.addComponent(label_23)
									.addComponent(label_24)
									.addComponent(label_25))
								.addGap(18)
								.addGroup(gl_p_ocen.createParallelGroup(Alignment.LEADING)
									.addComponent(t_zakl_name, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE)
									.addGroup(gl_p_ocen.createParallelGroup(Alignment.LEADING)
										.addComponent(cb_zakl, 0, 252, Short.MAX_VALUE)
										.addComponent(cb_soc_reab, 0, 252, Short.MAX_VALUE)
										.addComponent(cb_prof_reab, 0, 252, Short.MAX_VALUE))))
							.addGroup(Alignment.LEADING, gl_p_ocen.createSequentialGroup()
								.addGroup(gl_p_ocen.createParallelGroup(Alignment.LEADING)
									.addComponent(label_21)
									.addComponent(label_20))
								.addGap(18)
								.addGroup(gl_p_ocen.createParallelGroup(Alignment.LEADING)
									.addComponent(cb_ps_reab, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(cb_med_reab, GroupLayout.PREFERRED_SIZE, 252, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(181, Short.MAX_VALUE))
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
					.addContainerGap(180, Short.MAX_VALUE))
		);
		p_ocen.setLayout(gl_p_ocen);
		frame.getContentPane().setLayout(groupLayout);
	}

	@Override
	public String getName() {
		return configuration.appName;
	}
}
