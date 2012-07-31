package ru.nkz.ivcgzo.clientMss;

import java.awt.Color;
import java.awt.Font;
import java.lang.reflect.InvocationTargetException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftMss.ThriftMss;
import javax.swing.JCheckBox;

public class MainForm extends Client<ThriftMss.Client> {
	private JFrame frame;
	private JTextField tfAds_obl;
	private JTextField tfAds_raion;
	private JTextField tfAds_gorod;
	private JTextField tfAds_ul;
	private JTextField tfAds_dom;
	private JTextField tfAds_korp;
	private JTextField tfAds_kv;
	private JTextField tfves;
	private JTextField tfOt_m;
	private JTextField tfFam_m;
	private JTextField tfIm_m;
	private JTextField tfdatarm;
	private JTextField tfDatav;
	private JTextField tfNomer;
	private JTextField tfSer;
	private JTextField tfVz_datav;
	private JTextField tfVz_nomer;
	private JTextField tfVz_ser;
	private JTextField tfFam;
	private JTextField tfIm;
	private JTextField tfOt;
	private JTextField tfPol;
	private JTextField tfDatar;
	private JTextField tfDatas;
	private JTextField tfVrems;
	private JTextField tfAdm_obl;
	private JTextField tfAdm_raion;
	private JTextField tfAdm_gorod;
	private JTextField tfAdm_ul;
	private JTextField tfAdm_dom;
	private JTextField tfAdm_korp;
	private JTextField tfAdm_kv;
	private JTextField tfNreb;
	private JTextField tfMrojd;
	private JTextField tfDatatr;
	private JTextField tfVrem_tr;
	private JTextField tfObst;
	private JTextField tfCvrach;
	private JTextField tfPsm_a;
	private JTextField tfPsm_ak;
	private JTextField tfPsm_b;
	private JTextField tfPsm_bk;
	private JTextField tfPsm_v;
	private JTextField tfPsm_vk;
	private JTextField tfPsm_g;
	private JTextField tfPsm_gk;
	private JTextField tfPsm_p;
	private JTextField tfPsm_pk;
	private JTextField tfPsm_p1;
	private JTextField tfPsm_p2;
	private JTextField tfPsm_p1k;
	private JTextField tfPsm_p2k;
	private JTextField tfZapolnil;
	private JTextField tfFam_pol;
	private JTextField tfSdok;
	private JTextField tfNdok;
	private JTextField tfDvdok;
	private JTextField tfKvdok;
	private JTextField tfGpol;
	private JTextField tfUpol;
	private JTextField tfDpol;
	private JTextField tKpol;
	
	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(conMan, authInfo, ThriftMss.Client.class, configuration.appId, configuration.thrPort, lncPrm);
		
		initialize();
		
		setFrame(frame);
		
		JPanel panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		JButton btn_save = new JButton("Сохранить");
		panel.add(btn_save);
		
		JButton btn_del = new JButton("Удалить");
		panel.add(btn_del);
		
		JButton btn_prnt = new JButton("Печать");
		panel.add(btn_prnt);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 932, Short.MAX_VALUE)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 1016, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tabbedPane)
					.addContainerGap())
		);
		
		JButton btn_param = new JButton("Параметры");
		panel.add(btn_param);
		
		JPanel panel_1 = new JPanel();
		panel_1.setToolTipText("");
		tabbedPane.addTab("Сведения о умершем", null, panel_1, null);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JComboBox cmbVid = new JComboBox();
		
		JLabel label = new JLabel("Номер");
		
		JLabel label_1 = new JLabel("Дата выдачи");
		
		JLabel label_2 = new JLabel("Серия");
		
		tfDatav = new JTextField();
		tfDatav.setColumns(10);
		
		tfNomer = new JTextField();
		tfNomer.setColumns(10);
		
		tfSer = new JTextField();
		tfSer.setColumns(10);
		
		JLabel label_4 = new JLabel("дата выдачи");
		
		JLabel label_5 = new JLabel("номер\r\n");
		
		tfVz_datav = new JTextField();
		tfVz_datav.setColumns(10);
		
		tfVz_nomer = new JTextField();
		tfVz_nomer.setColumns(10);
		
		tfVz_ser = new JTextField();
		tfVz_ser.setColumns(10);
		
		JLabel label_9 = new JLabel("Взамен выданного: серия");
		
		JLabel label_10 = new JLabel("Вид свидетельства");
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_panel_4.createSequentialGroup()
							.addComponent(label_9)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfVz_ser, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE)
							.addGap(14)
							.addComponent(label_5)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfVz_nomer, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(label_10)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbVid, 0, 289, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_panel_4.createSequentialGroup()
							.addComponent(label_2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfSer, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfNomer, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
							.addGap(15)
							.addComponent(label_1)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(tfDatav, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(label_4)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfVz_datav, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(238, Short.MAX_VALUE))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGap(0, 189, Short.MAX_VALUE)
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
					.addContainerGap(110, Short.MAX_VALUE))
		);
		panel_4.setLayout(gl_panel_4);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel_4, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 877, Short.MAX_VALUE)
				.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 877, Short.MAX_VALUE)
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
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		
		JLabel lblNewLabel_24 = new JLabel("Заполняется для детей, умерших в возрасте от 168 час. до 1 года");
		
		JRadioButton rdbtn_don_don = new JRadioButton("доношенный (37-41 недель)");
		
		JRadioButton rdbtn_don_ned = new JRadioButton("недоношенный (менее 37 недель)");
		
		JRadioButton rdbtn_don_peren = new JRadioButton("переношенный (42 недель и более)");
		
		JLabel lblNewLabel_26 = new JLabel("вес при рождении (грамм)\r\n");
		
		tfves = new JTextField();
		tfves.setColumns(10);
		
		JLabel lblNewLabel_27 = new JLabel("который ребенок (считая умерших и не считая мертворожденных)\r\n");
		
		JLabel lblNewLabel_28 = new JLabel("отчество");
		
		tfOt_m = new JTextField();
		tfOt_m.setColumns(10);
		
		JLabel lblNewLabel_29 = new JLabel("Мать: фамилия");
		
		tfFam_m = new JTextField();
		tfFam_m.setColumns(10);
		
		JLabel lblNewLabel_30 = new JLabel("имя");
		
		tfIm_m = new JTextField();
		tfIm_m.setColumns(10);
		
		JLabel lblNewLabel_31 = new JLabel("дата рождения");
		
		tfdatarm = new JTextField();
		tfdatarm.setColumns(10);
		
		tfNreb = new JTextField();
		tfNreb.setColumns(10);
		
		JLabel lblNewLabel_10 = new JLabel("Место рождения ребенка");
		
		tfMrojd = new JTextField();
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
		
		tfAds_obl = new JTextField();
		tfAds_obl.setColumns(10);
		
		JLabel lblNewLabel_14 = new JLabel("район");
		
		tfAds_raion = new JTextField();
		tfAds_raion.setColumns(10);
		
		JLabel lblNewLabel_15 = new JLabel("город (населенный пункт)");
		
		tfAds_gorod = new JTextField();
		tfAds_gorod.setColumns(10);
		
		JLabel lblNewLabel_16 = new JLabel("улица");
		
		tfAds_ul = new JTextField();
		tfAds_ul.setColumns(10);
		
		JLabel lblNewLabel_17 = new JLabel("дом, корпус, квартира");
		
		tfAds_dom = new JTextField();
		tfAds_dom.setColumns(10);
		
		tfAds_korp = new JTextField();
		tfAds_korp.setColumns(10);
		
		tfAds_kv = new JTextField();
		tfAds_kv.setColumns(10);
		
		JLabel lblNewLabel_20 = new JLabel("Местность");
		
		JRadioButton rdbtnMs_gor = new JRadioButton("городская");
		
		JRadioButton rdbtnMs_selo = new JRadioButton("сельская\r\n");
		
		JLabel lblNewLabel_21 = new JLabel("*Семейное положение");
		
		JComboBox cmb_semp = new JComboBox();
		
		JLabel lblNewLabel_22 = new JLabel("*Образование");
		
		JComboBox cmb_obraz = new JComboBox();
		
		JLabel lblNewLabel_23 = new JLabel("*Занятость");
		
		JComboBox cmb_zan = new JComboBox();
		
		JLabel lblNewLabel = new JLabel("Фамилия\r\n");
		
		tfFam = new JTextField();
		tfFam.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Имя");
		
		tfIm = new JTextField();
		tfIm.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Отчество");
		
		tfOt = new JTextField();
		tfOt.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Пол");
		
		tfPol = new JTextField();
		tfPol.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Дата рождения\r\n");
		
		tfDatar = new JTextField();
		tfDatar.setColumns(10);
		
		JLabel lblNewLabel_5 = new JLabel("Дата смерти");
		
		tfDatas = new JTextField();
		tfDatas.setColumns(10);
		
		JLabel lblNewLabel_6 = new JLabel("время");
		
		tfVrems = new JTextField();
		tfVrems.setColumns(10);
		
		tfAdm_obl = new JTextField();
		tfAdm_obl.setColumns(10);
		
		tfAdm_raion = new JTextField();
		tfAdm_raion.setColumns(10);
		
		tfAdm_gorod = new JTextField();
		tfAdm_gorod.setColumns(10);
		
		tfAdm_ul = new JTextField();
		tfAdm_ul.setColumns(10);
		
		tfAdm_dom = new JTextField();
		tfAdm_dom.setColumns(10);
		
		tfAdm_korp = new JTextField();
		tfAdm_korp.setColumns(10);
		
		tfAdm_kv = new JTextField();
		tfAdm_kv.setColumns(10);
		
		JRadioButton rdbtnMjit_gor = new JRadioButton("городская");
		
		JRadioButton rdbtnMjit_selo = new JRadioButton("сельская");
		
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
								.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING, false)
									.addGroup(Alignment.LEADING, gl_panel_5.createSequentialGroup()
										.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING, false)
											.addGroup(gl_panel_5.createSequentialGroup()
												.addComponent(lblNewLabel_23)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(cmb_zan, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
											.addGroup(Alignment.LEADING, gl_panel_5.createSequentialGroup()
												.addComponent(lblNewLabel_21)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(cmb_semp, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)))
										.addGap(18)
										.addComponent(lblNewLabel_22)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(cmb_obraz, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
									.addGroup(Alignment.LEADING, gl_panel_5.createSequentialGroup()
										.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING)
											.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
												.addGroup(Alignment.TRAILING, gl_panel_5.createSequentialGroup()
													.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING)
														.addComponent(lblNewLabel_14)
														.addComponent(lblNewLabel_15)
														.addComponent(lblNewLabel_17)
														.addComponent(lblNewLabel_16))
													.addPreferredGap(ComponentPlacement.RELATED))
												.addGroup(gl_panel_5.createSequentialGroup()
													.addComponent(lblNewLabel_13)
													.addGap(1)))
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
												.addComponent(tfAds_ul)))))
								.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING)
									.addGroup(Alignment.LEADING, gl_panel_5.createSequentialGroup()
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
									.addGroup(Alignment.LEADING, gl_panel_5.createSequentialGroup()
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
										.addComponent(tfVrems, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)))))
						.addGroup(gl_panel_5.createSequentialGroup()
							.addGap(185)
							.addComponent(lblNewLabel_7)
							.addGap(116)
							.addComponent(lblNewLabel_8))
						.addGroup(gl_panel_5.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel_9)))
					.addContainerGap(101, Short.MAX_VALUE))
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
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_23)
						.addComponent(cmb_zan, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(lblNewLabel_9))
		);
		panel_5.setLayout(gl_panel_5);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		tabbedPane.addTab("Обстоятельства и причины смерти", null, panel_2, null);
		
		JLabel lblNewLabel_11 = new JLabel("Смерть: наступила");
		
		JComboBox cmbNastupila = new JComboBox();
		
		JLabel lblNewLabel_12 = new JLabel("произошла от");
		
		JComboBox cmbProiz = new JComboBox();
		
		JLabel lblNewLabel_18 = new JLabel("Дата травмы ");
		
		tfDatatr = new JTextField();
		tfDatatr.setColumns(10);
		
		JLabel lblNewLabel_19 = new JLabel("время");
		
		tfVrem_tr = new JTextField();
		tfVrem_tr.setColumns(10);
		
		JLabel lblNewLabel_25 = new JLabel("вид травмы");
		
		JComboBox cmbVid_tr = new JComboBox();
		
		JLabel lblNewLabel_32 = new JLabel("Обстоятельства");
		
		tfObst = new JTextField();
		tfObst.setColumns(10);
		
		JLabel lblNewLabel_33 = new JLabel("Причины установлены");
		
		JComboBox cmbUstan = new JComboBox();
		
		JLabel lblNewLabel_34 = new JLabel("врач: ФИО");
		
		tfCvrach = new JTextField();
		tfCvrach.setColumns(10);
		
		JLabel lblNewLabel_35 = new JLabel("должность");
		
		JComboBox cmbCdol = new JComboBox();
		
		JLabel lblNewLabel_36 = new JLabel("на основании");
		
		JComboBox cmbOsn = new JComboBox();
		
		JLabel lblNewLabel_37 = new JLabel("Причины смерти:");
		lblNewLabel_37.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblNewLabel_38 = new JLabel("Длительность пат. процесса");
		
		JLabel lblNewLabel_39 = new JLabel("I.a)");
		lblNewLabel_39.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		tfPsm_a = new JTextField();
		tfPsm_a.setColumns(10);
		
		JTextArea taPsm_an = new JTextArea();
		
		tfPsm_ak = new JTextField();
		tfPsm_ak.setColumns(10);
		
		JComboBox cmbPsm_ad = new JComboBox();
		
		JLabel lblNewLabel_40 = new JLabel("б)");
		lblNewLabel_40.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		tfPsm_b = new JTextField();
		tfPsm_b.setColumns(10);
		
		JTextArea taPsm_bn = new JTextArea();
		
		tfPsm_bk = new JTextField();
		tfPsm_bk.setColumns(10);
		
		JComboBox cmbPsm_bd = new JComboBox();
		
		JLabel lblNewLabel_41 = new JLabel("в)");
		lblNewLabel_41.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		tfPsm_v = new JTextField();
		tfPsm_v.setColumns(10);
		
		JTextArea taPsm_vn = new JTextArea();
		
		tfPsm_vk = new JTextField();
		tfPsm_vk.setColumns(10);
		
		JComboBox cmbPsm_vd = new JComboBox();
		
		JLabel lblNewLabel_42 = new JLabel("г)");
		lblNewLabel_42.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		tfPsm_g = new JTextField();
		tfPsm_g.setColumns(10);
		
		JTextArea taPsm_gn = new JTextArea();
		
		tfPsm_gk = new JTextField();
		tfPsm_gk.setColumns(10);
		
		JComboBox cmbPsm_ag = new JComboBox();
		
		JLabel lblNewLabel_43 = new JLabel("II.");
		lblNewLabel_43.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		tfPsm_p = new JTextField();
		tfPsm_p.setColumns(10);
		
		JTextArea taPsm_pn = new JTextArea();
		
		tfPsm_pk = new JTextField();
		tfPsm_pk.setColumns(10);
		
		JComboBox cmbPsm_pd = new JComboBox();
		
		tfPsm_p1 = new JTextField();
		tfPsm_p1.setColumns(10);
		
		tfPsm_p2 = new JTextField();
		tfPsm_p2.setColumns(10);
		
		JTextArea taPsm_p1n = new JTextArea();
		
		tfPsm_p1k = new JTextField();
		tfPsm_p1k.setColumns(10);
		
		JComboBox cmbPsm_p1d = new JComboBox();
		
		JTextArea taPsm_p2n = new JTextArea();
		
		tfPsm_p2k = new JTextField();
		tfPsm_p2k.setColumns(10);
		
		JComboBox cmbPsm_p2d = new JComboBox();
		
		JLabel lblNewLabel_44 = new JLabel("ДТП:  ");
		
		JCheckBox chckbxDtp30 = new JCheckBox("смерть наступила в течение 30 суток");
		
		JCheckBox chckbxDtp7 = new JCheckBox("из низ в течение 7 суток");
		
		JLabel lblNewLabel_45 = new JLabel("В случае смерти во время беременности....");
		
		JComboBox cmbUmerla = new JComboBox();
		
		JLabel lblNewLabel_46 = new JLabel("Заполнил свидетельство");
		
		tfZapolnil = new JTextField();
		tfZapolnil.setColumns(10);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addComponent(lblNewLabel_11)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cmbNastupila, GroupLayout.PREFERRED_SIZE, 201, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblNewLabel_12)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cmbProiz, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE))
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
									.addComponent(cmbVid_tr, GroupLayout.PREFERRED_SIZE, 261, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_2.createSequentialGroup()
									.addComponent(lblNewLabel_32)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfObst, GroupLayout.PREFERRED_SIZE, 573, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_2.createSequentialGroup()
									.addComponent(lblNewLabel_33)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cmbUstan, GroupLayout.PREFERRED_SIZE, 216, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblNewLabel_34)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfCvrach, GroupLayout.PREFERRED_SIZE, 288, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_2.createSequentialGroup()
									.addComponent(lblNewLabel_35)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cmbCdol, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblNewLabel_36)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cmbOsn, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)))
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblNewLabel_37)
							.addPreferredGap(ComponentPlacement.RELATED, 436, Short.MAX_VALUE)
							.addComponent(lblNewLabel_38)
							.addGap(43))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblNewLabel_44)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(chckbxDtp30)
							.addGap(18)
							.addComponent(chckbxDtp7)
							.addContainerGap(361, Short.MAX_VALUE))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING, gl_panel_2.createSequentialGroup()
									.addComponent(lblNewLabel_45)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cmbUmerla, 0, 300, Short.MAX_VALUE))
								.addGroup(gl_panel_2.createSequentialGroup()
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
											.addComponent(lblNewLabel_42)
											.addComponent(lblNewLabel_40)
											.addComponent(lblNewLabel_39)
											.addComponent(lblNewLabel_41))
										.addComponent(lblNewLabel_43))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
										.addComponent(tfPsm_p, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
										.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
											.addComponent(tfPsm_v, 0, 0, Short.MAX_VALUE)
											.addComponent(tfPsm_b, 0, 0, Short.MAX_VALUE)
											.addComponent(tfPsm_a, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
										.addComponent(tfPsm_g, 0, 0, Short.MAX_VALUE)
										.addComponent(tfPsm_p1, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE)
										.addComponent(tfPsm_p2, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 62, Short.MAX_VALUE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addComponent(taPsm_p2n, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
										.addComponent(taPsm_p1n, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
										.addComponent(taPsm_pn, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
										.addComponent(taPsm_gn, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
										.addComponent(taPsm_vn, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
										.addComponent(taPsm_bn, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
										.addComponent(taPsm_an, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE))))
							.addGap(10)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING, false)
								.addComponent(tfPsm_p2k, 0, 0, Short.MAX_VALUE)
								.addComponent(tfPsm_p1k, 0, 0, Short.MAX_VALUE)
								.addComponent(tfPsm_pk, 0, 0, Short.MAX_VALUE)
								.addComponent(tfPsm_gk, 0, 0, Short.MAX_VALUE)
								.addComponent(tfPsm_vk, 0, 0, Short.MAX_VALUE)
								.addComponent(tfPsm_bk, 0, 0, Short.MAX_VALUE)
								.addComponent(tfPsm_ak, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(cmbPsm_bd, 0, 129, Short.MAX_VALUE)
								.addComponent(cmbPsm_ad, 0, 129, Short.MAX_VALUE)
								.addComponent(cmbPsm_vd, 0, 129, Short.MAX_VALUE)
								.addComponent(cmbPsm_ag, 0, 129, Short.MAX_VALUE)
								.addComponent(cmbPsm_pd, 0, 129, Short.MAX_VALUE)
								.addComponent(cmbPsm_p1d, 0, 129, Short.MAX_VALUE)
								.addComponent(cmbPsm_p2d, 0, 129, Short.MAX_VALUE))
							.addContainerGap())
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(lblNewLabel_46)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfZapolnil, GroupLayout.PREFERRED_SIZE, 311, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())))
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
						.addComponent(lblNewLabel_36)
						.addComponent(cmbOsn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
						.addComponent(taPsm_an, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel_40)
							.addComponent(tfPsm_b, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
							.addComponent(tfPsm_bk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(cmbPsm_bd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(taPsm_bn, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_41)
						.addComponent(tfPsm_v, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
							.addComponent(tfPsm_vk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(cmbPsm_vd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(taPsm_vn, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_42)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
							.addComponent(taPsm_gn, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
							.addComponent(tfPsm_g, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(tfPsm_gk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(cmbPsm_ag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblNewLabel_43)
							.addComponent(tfPsm_p, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
							.addComponent(tfPsm_pk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(cmbPsm_pd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(taPsm_pn, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(tfPsm_p1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
							.addComponent(taPsm_p1n, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
							.addComponent(tfPsm_p1k, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(cmbPsm_p1d, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(tfPsm_p2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(taPsm_p2n, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
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
					.addContainerGap(28, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setToolTipText("");
		tabbedPane.addTab("Сведения о получателе", null, panel_3, null);
		
		JLabel lblNewLabel_47 = new JLabel("Получатель: ФИО");
		
		tfFam_pol = new JTextField();
		tfFam_pol.setColumns(10);
		
		JLabel lblNewLabel_48 = new JLabel("Документ, удостоверяющий личность:");
		
		tfSdok = new JTextField();
		tfSdok.setColumns(10);
		
		JComboBox cmbVdok = new JComboBox();
		
		JLabel lblNewLabel_49 = new JLabel("серия");
		
		JLabel lblNewLabel_50 = new JLabel("номер");
		
		tfNdok = new JTextField();
		tfNdok.setColumns(10);
		
		JLabel lblNewLabel_51 = new JLabel("дата выдачи");
		
		tfDvdok = new JTextField();
		tfDvdok.setColumns(10);
		
		JLabel lblNewLabel_52 = new JLabel("кем выдан");
		
		tfKvdok = new JTextField();
		tfKvdok.setColumns(10);
		
		JLabel lblNewLabel_53 = new JLabel("Адрес: город");
		
		tfGpol = new JTextField();
		tfGpol.setColumns(10);
		
		JLabel lblNewLabel_54 = new JLabel("улица");
		
		tfUpol = new JTextField();
		tfUpol.setColumns(10);
		
		JLabel lblNewLabel_55 = new JLabel("дом");
		
		tfDpol = new JTextField();
		tfDpol.setColumns(10);
		
		JLabel lblNewLabel_56 = new JLabel("квартира");
		
		tKpol = new JTextField();
		tKpol.setColumns(10);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(lblNewLabel_47)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfFam_pol, GroupLayout.PREFERRED_SIZE, 412, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(lblNewLabel_48)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmbVdok, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(Alignment.LEADING, gl_panel_3.createSequentialGroup()
								.addComponent(lblNewLabel_52)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(tfKvdok))
							.addGroup(Alignment.LEADING, gl_panel_3.createSequentialGroup()
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
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(lblNewLabel_53)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfGpol, GroupLayout.PREFERRED_SIZE, 271, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addComponent(lblNewLabel_54)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfUpol, GroupLayout.PREFERRED_SIZE, 262, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblNewLabel_55)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfDpol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblNewLabel_56)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tKpol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(160, Short.MAX_VALUE))
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
					.addGap(18)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_53)
						.addComponent(tfGpol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_54)
						.addComponent(tfUpol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_55)
						.addComponent(tfDpol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_56)
						.addComponent(tKpol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(371, Short.MAX_VALUE))
		);
		panel_3.setLayout(gl_panel_3);

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
		// TODO Auto-generated method stub

	}
}
