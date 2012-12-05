package ru.nkz.ivcgzo.clientRegPatient;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.SystemColor;
import javax.swing.ButtonGroup;

public class AnamnezForm extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JPanel pl_farm;
    private JPanel pl_btn;
    private JPanel pl_vr;
    private JPanel pl_five;
    private JPanel pl_jk;
    private JPanel panel;
    private JPanel pl_zab;
    private JPanel pl_date;
    private JButton btn_Save;
    private JButton btn_Del;
    private CustomTextField tf_alerg;
    private CustomTextField tf_f1;
    private CustomTextField tf_f2;
    private CustomTextField tf_f3;
    private CustomTextField tf_f4;
    private CustomTextField tf_f5;
    private CustomTextField tf_vz1;
    private CustomTextField tf_vz2;
    private CustomTextField tf_vz3;
    private CustomTextField tf_vz4;
    private CustomTextField tf_vz5;
    private CustomTextField tf_zgod;
    private CustomTextField tf_vdn;
    private CustomTextField tf_vdk;
    private CustomTextField tf_vfam;
    private CustomTextField tf_ngod;
    private CustomTextField tf_nark;
    private CustomTextField tf_ns1;
    private CustomTextField tf_ns2;
    private CustomTextField tf_kolsig;
    private CustomTextField tf_igod;
    private CustomTextField tf_ireak;
    private CustomTextField tf_ipovod;
    private CustomTextField tf_date;
    private CustomTextField tf_jk1;
    private CustomTextField tf_jk2;
    private CustomTextField tf_jk3;
    private CustomTextField tf_jk4;
    private CustomTextField tf_jk5;
    private CustomTextField tf_jk6;
    private CustomTextField tf_jk7;
    private CustomTextField tf_zinf;
    private JRadioButton rbtn_n1;
    private JRadioButton rbtn_n2;
    private JRadioButton rbtn_n3;
    private JRadioButton rbtn_n4;
    private JRadioButton rbtn_n5;
    private JRadioButton rbtn_z5;
    private JRadioButton rbtn_alk;
    private JRadioButton rbtn_tab;
    private JRadioButton rbtn_i1;
    private JRadioButton rbtn_i2;
    private JRadioButton rbtn_i3;
    private JRadioButton rbtn_i4;
    private JRadioButton rbtn_z1;
    private JRadioButton rbtn_z2;
    private JRadioButton rbtn_z3;
    private JRadioButton rbtn_z4;
    private JRadioButton rbtn_gkm1;
    private JRadioButton rbtn_gkm2;
    private JRadioButton rbtn_gkm3;
    private JRadioButton rbtn_gkm4;
    private JRadioButton rbtn_rfm1;
    private JRadioButton rbtn_rfm2;
    private final ButtonGroup btng_gk = new ButtonGroup();
    private final ButtonGroup btng_rf = new ButtonGroup();
    private final ButtonGroup btng_gkm = new ButtonGroup();
    private final ButtonGroup btng_rfm = new ButtonGroup();
	private JRadioButton rbtn_gep1;
	private JRadioButton rbtn_gep2;
	private JRadioButton rbtn_gep3;
	private JRadioButton rbtn_v1;
	private JRadioButton rbtn_v2;
	private JRadioButton rbtn_v3;

	/**
	 * Create the frame.
	 */
	public AnamnezForm() {
		setTitle("Анамнез жизни");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        JPanel pl_alerg = new JPanel();
        pl_alerg.setBorder(new TitledBorder(null, "Аллерго-анамнез :", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        
        pl_farm = new JPanel();
        pl_farm.setBorder(new TitledBorder(null, "Фармакологический анамнез :", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        
        pl_btn = new JPanel();
        
        JPanel pl_ven = new JPanel();
        pl_ven.setBorder(new TitledBorder(null, "Венерические заболевания (год) :", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        
        JPanel pl_vih = new JPanel();
        pl_vih.setBorder(new TitledBorder(null, "ВИЧ-инфекция :", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        
        JPanel pl_nark = new JPanel();
        pl_nark.setBorder(new TitledBorder(null, "Наркотики :", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        
        pl_vr = new JPanel();
        pl_vr.setBorder(new TitledBorder(null, "Вредные привычки :", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        
        pl_five = new JPanel();
        pl_five.setBorder(new TitledBorder(null, "Информация за последние 5 лет :", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        
        pl_date = new JPanel();
        
        pl_zab = new JPanel();
        pl_zab.setBorder(new TitledBorder(null, "Заболевания :", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        
        JPanel pl_gkr = new JPanel();
        pl_gkr.setBorder(new TitledBorder(null, "Группа крови :", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        
        JPanel pl_gkrm = new JPanel();
        pl_gkrm.setBorder(new TitledBorder(null, "Группа крови мужа :", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        
        pl_jk = new JPanel();
        pl_jk.setBorder(new TitledBorder(null, "Для ж/к, роддомов :", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addComponent(pl_btn, GroupLayout.PREFERRED_SIZE, 479, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(pl_date, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
        						.addGroup(gl_contentPane.createSequentialGroup()
        							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        								.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING, false)
        									.addComponent(pl_ven, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 213, Short.MAX_VALUE)
        									.addComponent(pl_gkr, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 152, Short.MAX_VALUE)
        									.addComponent(pl_vr, Alignment.LEADING, 0, 0, Short.MAX_VALUE))
        								.addComponent(pl_gkrm, GroupLayout.PREFERRED_SIZE, 209, GroupLayout.PREFERRED_SIZE))
        							.addPreferredGap(ComponentPlacement.UNRELATED)
        							.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        								.addComponent(pl_zab, GroupLayout.PREFERRED_SIZE, 254, Short.MAX_VALUE)
        								.addComponent(pl_jk, GroupLayout.PREFERRED_SIZE, 280, GroupLayout.PREFERRED_SIZE)))
        						.addComponent(pl_alerg, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE)
        						.addComponent(pl_farm, GroupLayout.PREFERRED_SIZE, 477, GroupLayout.PREFERRED_SIZE))
        					.addGap(6)
        					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        						.addComponent(pl_nark, GroupLayout.PREFERRED_SIZE, 471, GroupLayout.PREFERRED_SIZE)
        						.addComponent(pl_five, GroupLayout.PREFERRED_SIZE, 339, GroupLayout.PREFERRED_SIZE)
        						.addComponent(pl_vih, GroupLayout.PREFERRED_SIZE, 355, GroupLayout.PREFERRED_SIZE))))
        			.addGap(118))
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING, false)
        				.addComponent(pl_btn, 0, 0, Short.MAX_VALUE)
        				.addComponent(pl_date, GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addComponent(pl_alerg, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(pl_farm, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
        						.addComponent(pl_ven, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
        						.addComponent(pl_zab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
        						.addGroup(gl_contentPane.createSequentialGroup()
        							.addComponent(pl_vr, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(pl_gkr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(pl_gkrm, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))
        						.addComponent(pl_jk, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)))
        				.addGroup(gl_contentPane.createSequentialGroup()
        					.addComponent(pl_vih, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
        					.addGap(21)
        					.addComponent(pl_nark, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE)
        					.addGap(18)
        					.addComponent(pl_five, GroupLayout.PREFERRED_SIZE, 191, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap(45, Short.MAX_VALUE))
        );
        
        JLabel lblNewLabel_18 = new JLabel("Беременность");
        lblNewLabel_18.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_19 = new JLabel("Роды");
        lblNewLabel_19.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_21 = new JLabel("Аборты");
        lblNewLabel_21.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_22 = new JLabel("в т.ч. криминальные");
        JLabel lblNewLabel_23 = new JLabel("Выкидыши");
        lblNewLabel_23.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_24 = new JLabel("Брак по счету");
        lblNewLabel_24.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_25 = new JLabel("Количество живых детей");
        lblNewLabel_25.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        tf_jk1 = new CustomTextField();
        tf_jk1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_jk1.setColumns(10);
        
        tf_jk2 = new CustomTextField();
        tf_jk2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_jk2.setColumns(10);
        
        tf_jk3 = new CustomTextField();
        tf_jk3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_jk3.setColumns(10);
        
        tf_jk4 = new CustomTextField();
        tf_jk4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_jk4.setColumns(10);
        
        tf_jk5 = new CustomTextField();
        tf_jk5.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_jk5.setColumns(10);
        
        tf_jk6 = new CustomTextField();
        tf_jk6.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_jk6.setColumns(10);
        
        tf_jk7 = new CustomTextField();
        tf_jk7.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_jk7.setColumns(10);
        GroupLayout gl_pl_jk = new GroupLayout(pl_jk);
        gl_pl_jk.setHorizontalGroup(
        	gl_pl_jk.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_jk.createSequentialGroup()
        			.addGroup(gl_pl_jk.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_pl_jk.createSequentialGroup()
        					.addComponent(lblNewLabel_25)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(tf_jk7, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_pl_jk.createSequentialGroup()
        					.addGroup(gl_pl_jk.createParallelGroup(Alignment.LEADING, false)
        						.addGroup(gl_pl_jk.createSequentialGroup()
        							.addComponent(lblNewLabel_18)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(tf_jk1, 0, 0, Short.MAX_VALUE))
        						.addGroup(gl_pl_jk.createSequentialGroup()
        							.addGroup(gl_pl_jk.createParallelGroup(Alignment.LEADING)
        								.addComponent(lblNewLabel_24)
        								.addComponent(lblNewLabel_21)
        								.addComponent(lblNewLabel_23)
        								.addComponent(lblNewLabel_19))
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addGroup(gl_pl_jk.createParallelGroup(Alignment.LEADING, false)
        								.addComponent(tf_jk6, 0, 0, Short.MAX_VALUE)
        								.addComponent(tf_jk5, 0, 0, Short.MAX_VALUE)
        								.addComponent(tf_jk3, 0, 0, Short.MAX_VALUE)
        								.addComponent(tf_jk2, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(lblNewLabel_22)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(tf_jk4, GroupLayout.DEFAULT_SIZE, 19, Short.MAX_VALUE)))
        			.addContainerGap())
        );
        gl_pl_jk.setVerticalGroup(
        	gl_pl_jk.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_jk.createSequentialGroup()
        			.addGroup(gl_pl_jk.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_18)
        				.addComponent(tf_jk1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_pl_jk.createParallelGroup(Alignment.BASELINE)
        				.addComponent(tf_jk2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_19))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_pl_jk.createParallelGroup(Alignment.BASELINE)
        				.addComponent(tf_jk3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_21)
        				.addComponent(lblNewLabel_22)
        				.addComponent(tf_jk4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_pl_jk.createParallelGroup(Alignment.BASELINE)
        				.addComponent(tf_jk5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_23))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_pl_jk.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_24)
        				.addComponent(tf_jk6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_pl_jk.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_25)
        				.addComponent(tf_jk7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(33, Short.MAX_VALUE))
        );
        pl_jk.setLayout(gl_pl_jk);
        
        rbtn_gkm1 = new JRadioButton("I");
        btng_gkm.add(rbtn_gkm1);
        
        rbtn_gkm2 = new JRadioButton("II");
        btng_gkm.add(rbtn_gkm2);
        
        rbtn_gkm3 = new JRadioButton("III");
        btng_gkm.add(rbtn_gkm3);
        
        rbtn_gkm4 = new JRadioButton("IV");
        btng_gkm.add(rbtn_gkm4);
        
        rbtn_rfm1 = new JRadioButton("+");
        btng_rfm.add(rbtn_rfm1);
        
        rbtn_rfm2 = new JRadioButton("-");
        btng_rfm.add(rbtn_rfm2);
        GroupLayout gl_pl_gkrm = new GroupLayout(pl_gkrm);
        gl_pl_gkrm.setHorizontalGroup(
        	gl_pl_gkrm.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_gkrm.createSequentialGroup()
        			.addGroup(gl_pl_gkrm.createParallelGroup(Alignment.LEADING)
        				.addComponent(rbtn_gkm1)
        				.addComponent(rbtn_rfm1))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_pl_gkrm.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_pl_gkrm.createSequentialGroup()
        					.addComponent(rbtn_gkm2)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(rbtn_gkm3)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(rbtn_gkm4))
        				.addComponent(rbtn_rfm2))
        			.addContainerGap(55, Short.MAX_VALUE))
        );
        gl_pl_gkrm.setVerticalGroup(
        	gl_pl_gkrm.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_gkrm.createSequentialGroup()
        			.addGroup(gl_pl_gkrm.createParallelGroup(Alignment.BASELINE)
        				.addComponent(rbtn_gkm1)
        				.addComponent(rbtn_gkm2)
        				.addComponent(rbtn_gkm3)
        				.addComponent(rbtn_gkm4))
        			.addPreferredGap(ComponentPlacement.RELATED, 4, Short.MAX_VALUE)
        			.addGroup(gl_pl_gkrm.createParallelGroup(Alignment.BASELINE)
        				.addComponent(rbtn_rfm1)
        				.addComponent(rbtn_rfm2)))
        );
        pl_gkrm.setLayout(gl_pl_gkrm);
        
        JRadioButton rbtn_gk1 = new JRadioButton("I");
        btng_gk.add(rbtn_gk1);
        
        JRadioButton rbtn_gk2 = new JRadioButton("II");
        btng_gk.add(rbtn_gk2);
        
        JRadioButton rbtn_gk3 = new JRadioButton("III");
        btng_gk.add(rbtn_gk3);
        
        JRadioButton rbtn_gk4 = new JRadioButton("IV");
        btng_gk.add(rbtn_gk4);
        
        JRadioButton rbtn_rf1 = new JRadioButton("+");
        btng_rf.add(rbtn_rf1);
        
        JRadioButton rbtn_rf2 = new JRadioButton("-");
        btng_rf.add(rbtn_rf2);
        GroupLayout gl_pl_gkr = new GroupLayout(pl_gkr);
        gl_pl_gkr.setHorizontalGroup(
        	gl_pl_gkr.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_gkr.createSequentialGroup()
        			.addGroup(gl_pl_gkr.createParallelGroup(Alignment.LEADING)
        				.addComponent(rbtn_gk1)
        				.addComponent(rbtn_rf1))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_pl_gkr.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_pl_gkr.createSequentialGroup()
        					.addComponent(rbtn_gk2)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(rbtn_gk3)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(rbtn_gk4))
        				.addComponent(rbtn_rf2))
        			.addContainerGap(53, Short.MAX_VALUE))
        );
        gl_pl_gkr.setVerticalGroup(
        	gl_pl_gkr.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_gkr.createSequentialGroup()
        			.addGroup(gl_pl_gkr.createParallelGroup(Alignment.BASELINE)
        				.addComponent(rbtn_gk1)
        				.addComponent(rbtn_gk2)
        				.addComponent(rbtn_gk3)
        				.addComponent(rbtn_gk4))
        			.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addGroup(gl_pl_gkr.createParallelGroup(Alignment.BASELINE)
        				.addComponent(rbtn_rf1)
        				.addComponent(rbtn_rf2)))
        );
        pl_gkr.setLayout(gl_pl_gkr);
        
        JLabel lblNewLabel_10 = new JLabel("год");
        lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 10));
        JLabel lblNewLabel_26 = new JLabel("контакт с инфекционными больными");
        lblNewLabel_26.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        rbtn_z1 = new JRadioButton("чесотка");
        rbtn_z1.setFont(new Font("Tahoma", Font.PLAIN, 13));
        
        rbtn_z2 = new JRadioButton("педикулез");
        rbtn_z2.setFont(new Font("Tahoma", Font.PLAIN, 13));
        
        rbtn_z3 = new JRadioButton("гепатит");
        rbtn_z3.setFont(new Font("Tahoma", Font.PLAIN, 13));
        
        rbtn_z4 = new JRadioButton("туберкулез");
        rbtn_z4.setFont(new Font("Tahoma", Font.PLAIN, 13));
        
        rbtn_gep1 = new JRadioButton("А");
        rbtn_gep1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        rbtn_gep2 = new JRadioButton("B");
        rbtn_gep2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        rbtn_gep3 = new JRadioButton("C");
        rbtn_gep3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        tf_zgod = new CustomTextField();
        tf_zgod.setFont(new Font("Tahoma", Font.PLAIN, 11));
        tf_zgod.setColumns(10);
        
        rbtn_z5 = new JRadioButton("контакт с больными");
        rbtn_z5.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        tf_zinf = new CustomTextField();
        tf_zinf.setColumns(10);
        GroupLayout gl_pl_zab = new GroupLayout(pl_zab);
        gl_pl_zab.setHorizontalGroup(
        	gl_pl_zab.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_zab.createSequentialGroup()
        			.addGroup(gl_pl_zab.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_pl_zab.createSequentialGroup()
        					.addComponent(rbtn_z3)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(rbtn_gep1)
        					.addGap(2)
        					.addComponent(rbtn_gep2)
        					.addComponent(rbtn_gep3)
        					.addGap(2)
        					.addComponent(tf_zgod, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
        					.addGap(4)
        					.addComponent(lblNewLabel_10))
        				.addComponent(rbtn_z2)
        				.addComponent(rbtn_z1)
        				.addGroup(gl_pl_zab.createParallelGroup(Alignment.TRAILING)
        					.addComponent(lblNewLabel_26)
        					.addGroup(gl_pl_zab.createSequentialGroup()
        						.addComponent(rbtn_z4)
        						.addPreferredGap(ComponentPlacement.UNRELATED)
        						.addComponent(rbtn_z5))))
        			.addContainerGap(14, Short.MAX_VALUE))
        		.addComponent(tf_zinf, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
        );
        gl_pl_zab.setVerticalGroup(
        	gl_pl_zab.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_zab.createSequentialGroup()
        			.addComponent(rbtn_z1)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(rbtn_z2)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_pl_zab.createParallelGroup(Alignment.LEADING)
        				.addComponent(rbtn_z3)
        				.addComponent(rbtn_gep1)
        				.addComponent(rbtn_gep2)
        				.addComponent(rbtn_gep3)
        				.addGroup(gl_pl_zab.createParallelGroup(Alignment.BASELINE)
        					.addComponent(tf_zgod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(lblNewLabel_10)))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_pl_zab.createParallelGroup(Alignment.BASELINE)
        				.addComponent(rbtn_z4)
        				.addComponent(rbtn_z5))
        			.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        			.addComponent(lblNewLabel_26)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(tf_zinf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        );
        pl_zab.setLayout(gl_pl_zab);
        
        JLabel label_4 = new JLabel("Дата");
        label_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        tf_date = new CustomTextField();
        tf_date.setColumns(10);
        GroupLayout gl_pl_date = new GroupLayout(pl_date);
        gl_pl_date.setHorizontalGroup(
        	gl_pl_date.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_date.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(label_4)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(tf_date, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(35, Short.MAX_VALUE))
        );
        gl_pl_date.setVerticalGroup(
        	gl_pl_date.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_pl_date.createSequentialGroup()
        			.addGroup(gl_pl_date.createParallelGroup(Alignment.BASELINE)
        				.addComponent(tf_date, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(label_4))
        			.addContainerGap(14, Short.MAX_VALUE))
        );
        pl_date.setLayout(gl_pl_date);
        
        rbtn_i1 = new JRadioButton("Выезжал(а) за границу РФ");
        rbtn_i1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        rbtn_i2 = new JRadioButton("Находился(ась) в местах лишения свободы");
        rbtn_i2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        rbtn_i3 = new JRadioButton("Пересадка органов и тканей");
        rbtn_i3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        panel = new JPanel();
        panel.setBorder(new LineBorder(SystemColor.inactiveCaptionBorder));
        GroupLayout gl_pl_five = new GroupLayout(pl_five);
        gl_pl_five.setHorizontalGroup(
        	gl_pl_five.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_five.createSequentialGroup()
        			.addGroup(gl_pl_five.createParallelGroup(Alignment.LEADING)
        				.addComponent(rbtn_i1)
        				.addComponent(rbtn_i2)
        				.addComponent(rbtn_i3)
        				.addComponent(panel, GroupLayout.PREFERRED_SIZE, 320, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(94, Short.MAX_VALUE))
        );
        gl_pl_five.setVerticalGroup(
        	gl_pl_five.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_five.createSequentialGroup()
        			.addComponent(rbtn_i1)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(rbtn_i2)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addComponent(rbtn_i3)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(panel, GroupLayout.PREFERRED_SIZE, 82, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        
        JLabel label_6 = new JLabel("реакции");
        label_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_20 = new JLabel("повод");
        lblNewLabel_20.setFont(new Font("Tahoma", Font.PLAIN, 12));

        rbtn_i4 = new JRadioButton("Переливание крови (её компоненты), год");
        rbtn_i4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        tf_igod = new CustomTextField();
        tf_igod.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_igod.setColumns(10);
        
        tf_ireak = new CustomTextField();
        tf_ireak.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_ireak.setColumns(10);
        
        tf_ipovod = new CustomTextField();
        tf_ipovod.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_ipovod.setColumns(10);
        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(lblNewLabel_20)
        					.addGap(24)
        					.addComponent(tf_ipovod, GroupLayout.PREFERRED_SIZE, 259, GroupLayout.PREFERRED_SIZE)
        					.addGap(0, 0, Short.MAX_VALUE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(rbtn_i4)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(tf_igod, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_panel.createSequentialGroup()
        					.addComponent(label_6)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(tf_ireak, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE)))
        			.addContainerGap())
        );
        gl_panel.setVerticalGroup(
        	gl_panel.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_panel.createSequentialGroup()
        			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(rbtn_i4)
        				.addComponent(tf_igod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(label_6)
        				.addComponent(tf_ireak, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(9)
        			.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_20)
        				.addComponent(tf_ipovod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel.setLayout(gl_panel);
        pl_five.setLayout(gl_pl_five);

        JLabel lblNewLabel_17 = new JLabel("сигарет в день");
        lblNewLabel_17.setFont(new Font("Tahoma", Font.PLAIN, 10));
        
        rbtn_alk = new JRadioButton("алкоголь");
        rbtn_alk.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        rbtn_tab = new JRadioButton("табакокурение");
        rbtn_tab.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        tf_kolsig = new CustomTextField();
        tf_kolsig.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_kolsig.setColumns(10);
        GroupLayout gl_pl_vr = new GroupLayout(pl_vr);
        gl_pl_vr.setHorizontalGroup(
        	gl_pl_vr.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_pl_vr.createSequentialGroup()
        			.addGroup(gl_pl_vr.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_pl_vr.createSequentialGroup()
        					.addComponent(rbtn_tab)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(tf_kolsig, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_pl_vr.createSequentialGroup()
        					.addComponent(rbtn_alk)
        					.addGap(38)
        					.addComponent(lblNewLabel_17)))
        			.addGap(54))
        );
        gl_pl_vr.setVerticalGroup(
        	gl_pl_vr.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_vr.createSequentialGroup()
        			.addGroup(gl_pl_vr.createParallelGroup(Alignment.LEADING)
        				.addComponent(rbtn_alk)
        				.addGroup(gl_pl_vr.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(lblNewLabel_17)))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_pl_vr.createParallelGroup(Alignment.BASELINE)
        				.addComponent(rbtn_tab)
        				.addComponent(tf_kolsig, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap())
        );
        pl_vr.setLayout(gl_pl_vr);
        
        rbtn_n1 = new JRadioButton("потребление наркотиков");
        rbtn_n1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        rbtn_n2 = new JRadioButton("в т.ч. внутривенно");
        rbtn_n2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        rbtn_n3 = new JRadioButton("контакт с употребляющим наркотики");
        rbtn_n3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        rbtn_n4 = new JRadioButton("шприцевой");
        rbtn_n4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        rbtn_n5 = new JRadioButton("половой");
        rbtn_n5.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        tf_ngod = new CustomTextField();
        tf_ngod.setColumns(10);
        
        tf_nark = new CustomTextField();
        tf_nark.setColumns(10);
        
        tf_ns1 = new CustomTextField();
        tf_ns1.setColumns(10);
        
        tf_ns2 = new CustomTextField();
        tf_ns2.setColumns(10);
        
        JLabel label_2 = new JLabel("года");
        label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel label_3 = new JLabel("по");
        label_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_13 = new JLabel("с");
        lblNewLabel_13.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_14 = new JLabel("срок контакта : с");
        lblNewLabel_14.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_15 = new JLabel("вид контакта :");
        lblNewLabel_15.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_16 = new JLabel("какие");
        lblNewLabel_16.setFont(new Font("Tahoma", Font.PLAIN, 10));
        JLabel label_7 = new JLabel("год");
        label_7.setFont(new Font("Tahoma", Font.PLAIN, 11));

        GroupLayout gl_pl_nark = new GroupLayout(pl_nark);
        gl_pl_nark.setHorizontalGroup(
        	gl_pl_nark.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_nark.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_pl_nark.createParallelGroup(Alignment.LEADING, false)
        				.addGroup(gl_pl_nark.createSequentialGroup()
        					.addComponent(lblNewLabel_13)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(tf_ngod, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(label_2)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(tf_nark, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_pl_nark.createSequentialGroup()
        					.addComponent(rbtn_n1)
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(rbtn_n2)
        					.addGap(18)
        					.addComponent(lblNewLabel_16))
        				.addComponent(rbtn_n3)
        				.addGroup(gl_pl_nark.createSequentialGroup()
        					.addComponent(lblNewLabel_14)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(tf_ns1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(label_3)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(tf_ns2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(label_7))
        				.addGroup(gl_pl_nark.createSequentialGroup()
        					.addComponent(lblNewLabel_15)
        					.addGap(18)
        					.addComponent(rbtn_n4)
        					.addGap(18)
        					.addComponent(rbtn_n5)))
        			.addGap(30))
        );
        gl_pl_nark.setVerticalGroup(
        	gl_pl_nark.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_nark.createSequentialGroup()
        			.addGroup(gl_pl_nark.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_pl_nark.createParallelGroup(Alignment.BASELINE)
        					.addComponent(rbtn_n1)
        					.addComponent(rbtn_n2))
        				.addGroup(gl_pl_nark.createSequentialGroup()
        					.addContainerGap()
        					.addComponent(lblNewLabel_16)))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_pl_nark.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_13)
        				.addComponent(tf_ngod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(label_2)
        				.addComponent(tf_nark, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(rbtn_n3)
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_pl_nark.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_14)
        				.addComponent(tf_ns1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(label_3)
        				.addComponent(tf_ns2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(label_7))
        			.addPreferredGap(ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
        			.addGroup(gl_pl_nark.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_15)
        				.addComponent(rbtn_n4)
        				.addComponent(rbtn_n5)))
        );
        pl_nark.setLayout(gl_pl_nark);
        
        tf_vdk = new CustomTextField();
        tf_vdk.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_vdk.setColumns(10);
        
        tf_vdn = new CustomTextField();
        tf_vdn.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_vdn.setColumns(10);
        
        tf_vfam = new CustomTextField();
        tf_vfam.setColumns(10);
        
        JLabel lblNewLabel_11 = new JLabel("срок контакта : с");
        lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_12 = new JLabel("по");
        lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel label = new JLabel("вид контакта :");
        label.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel label_1 = new JLabel("ВИЧ-инфекция в семье");
        label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel label_8 = new JLabel("год");
        
        rbtn_v1 = new JRadioButton("контакт с ВИЧ-инфицированным");
        rbtn_v1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        rbtn_v2 = new JRadioButton("шприцевой");
        rbtn_v2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        rbtn_v3 = new JRadioButton("половой");
        rbtn_v3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        GroupLayout gl_pl_vih = new GroupLayout(pl_vih);
        gl_pl_vih.setHorizontalGroup(
        	gl_pl_vih.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_vih.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_pl_vih.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_pl_vih.createSequentialGroup()
        					.addGroup(gl_pl_vih.createParallelGroup(Alignment.LEADING)
        						.addComponent(rbtn_v1)
        						.addGroup(gl_pl_vih.createSequentialGroup()
        							.addGroup(gl_pl_vih.createParallelGroup(Alignment.LEADING)
        								.addGroup(gl_pl_vih.createSequentialGroup()
        									.addComponent(lblNewLabel_11)
        									.addPreferredGap(ComponentPlacement.RELATED)
        									.addComponent(tf_vdn, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE))
        								.addGroup(gl_pl_vih.createSequentialGroup()
        									.addComponent(label)
        									.addGap(18)
        									.addComponent(rbtn_v2)))
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addComponent(lblNewLabel_12)
        							.addPreferredGap(ComponentPlacement.RELATED)
        							.addGroup(gl_pl_vih.createParallelGroup(Alignment.LEADING)
        								.addComponent(tf_vdk, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
        								.addComponent(rbtn_v3))))
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(label_8)
        					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        				.addGroup(gl_pl_vih.createSequentialGroup()
        					.addComponent(label_1)
        					.addPreferredGap(ComponentPlacement.RELATED)
        					.addComponent(tf_vfam, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
        					.addContainerGap(0, Short.MAX_VALUE))))
        );
        gl_pl_vih.setVerticalGroup(
        	gl_pl_vih.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_vih.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(rbtn_v1)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_pl_vih.createParallelGroup(Alignment.TRAILING)
        				.addComponent(lblNewLabel_11)
        				.addGroup(gl_pl_vih.createParallelGroup(Alignment.BASELINE)
        					.addComponent(tf_vdn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(lblNewLabel_12)
        					.addComponent(tf_vdk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        					.addComponent(label_8)))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_pl_vih.createParallelGroup(Alignment.BASELINE)
        				.addComponent(rbtn_v2)
        				.addComponent(label)
        				.addComponent(rbtn_v3))
        			.addPreferredGap(ComponentPlacement.UNRELATED)
        			.addGroup(gl_pl_vih.createParallelGroup(Alignment.BASELINE)
        				.addComponent(label_1)
        				.addComponent(tf_vfam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(17, Short.MAX_VALUE))
        );
        pl_vih.setLayout(gl_pl_vih);
        
        JLabel lblNewLabel_5 = new JLabel("сифилис");
        lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_6 = new JLabel("гонорея");
        lblNewLabel_6.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_7 = new JLabel("хламидиоз");
        lblNewLabel_7.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_8 = new JLabel("трихомониаз");
        lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_9 = new JLabel("генитальный герпес");
        lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        tf_vz1 = new CustomTextField();
        tf_vz1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_vz1.setColumns(10);
        
        tf_vz2 = new CustomTextField();
        tf_vz2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_vz2.setColumns(10);
        
        tf_vz3 = new CustomTextField();
        tf_vz3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_vz3.setColumns(10);
        
        tf_vz4 = new CustomTextField();
        tf_vz4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_vz4.setColumns(10);
        
        tf_vz5 = new CustomTextField();
        tf_vz5.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_vz5.setColumns(10);
        GroupLayout gl_pl_ven = new GroupLayout(pl_ven);
        gl_pl_ven.setHorizontalGroup(
        	gl_pl_ven.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_ven.createSequentialGroup()
        			.addGroup(gl_pl_ven.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_pl_ven.createSequentialGroup()
        					.addGap(37)
        					.addGroup(gl_pl_ven.createParallelGroup(Alignment.TRAILING)
        						.addComponent(lblNewLabel_6)
        						.addComponent(lblNewLabel_5)
        						.addComponent(lblNewLabel_8)
        						.addComponent(lblNewLabel_7)))
        				.addComponent(lblNewLabel_9))
        			.addGroup(gl_pl_ven.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_pl_ven.createSequentialGroup()
        					.addGap(10)
        					.addComponent(tf_vz1, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_pl_ven.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(tf_vz2, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_pl_ven.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addComponent(tf_vz3, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
        				.addGroup(gl_pl_ven.createSequentialGroup()
        					.addPreferredGap(ComponentPlacement.UNRELATED)
        					.addGroup(gl_pl_ven.createParallelGroup(Alignment.LEADING)
        						.addComponent(tf_vz5, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
        						.addComponent(tf_vz4, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE))
        					.addGap(10))))
        );
        gl_pl_ven.setVerticalGroup(
        	gl_pl_ven.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_ven.createSequentialGroup()
        			.addGroup(gl_pl_ven.createParallelGroup(Alignment.BASELINE)
        				.addComponent(tf_vz1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addComponent(lblNewLabel_5))
        			.addGap(7)
        			.addGroup(gl_pl_ven.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_6)
        				.addComponent(tf_vz2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(6)
        			.addGroup(gl_pl_ven.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_7)
        				.addComponent(tf_vz3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(11)
        			.addGroup(gl_pl_ven.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_8)
        				.addComponent(tf_vz4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addGap(6)
        			.addGroup(gl_pl_ven.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_9)
        				.addComponent(tf_vz5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap())
        );
        pl_ven.setLayout(gl_pl_ven);
        
        btn_Save = new JButton("Удалить");
        
        btn_Del = new JButton("Сохранить");
        GroupLayout gl_pl_btn = new GroupLayout(pl_btn);
        gl_pl_btn.setHorizontalGroup(
        	gl_pl_btn.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_btn.createSequentialGroup()
        			.addGap(130)
        			.addComponent(btn_Save)
        			.addGap(84)
        			.addComponent(btn_Del)
        			.addContainerGap(101, Short.MAX_VALUE))
        );
        gl_pl_btn.setVerticalGroup(
        	gl_pl_btn.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_btn.createSequentialGroup()
        			.addGroup(gl_pl_btn.createParallelGroup(Alignment.BASELINE)
        				.addComponent(btn_Del)
        				.addComponent(btn_Save))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pl_btn.setLayout(gl_pl_btn);
        
        JLabel lblNewLabel = new JLabel("анестетики");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_1 = new JLabel("антибиотики");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_2 = new JLabel("обезболивающие");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_3 = new JLabel("спазмолитики");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        JLabel lblNewLabel_4 = new JLabel("прочие");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        tf_f1 = new CustomTextField();
        tf_f1.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_f1.setColumns(10);

        tf_f2 = new CustomTextField();
        tf_f2.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_f2.setColumns(10);
        
        tf_f3 = new CustomTextField();
        tf_f3.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_f3.setColumns(10);
        
        tf_f4 = new CustomTextField();
        tf_f4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_f4.setColumns(10);
        
        tf_f5 = new CustomTextField();
        tf_f5.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_f5.setColumns(10);
        
        GroupLayout gl_pl_farm = new GroupLayout(pl_farm);
        gl_pl_farm.setHorizontalGroup(
        	gl_pl_farm.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_farm.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(gl_pl_farm.createParallelGroup(Alignment.TRAILING)
        				.addComponent(lblNewLabel_2)
        				.addGroup(gl_pl_farm.createParallelGroup(Alignment.LEADING)
        					.addGroup(gl_pl_farm.createSequentialGroup()
        						.addGap(15)
        						.addGroup(gl_pl_farm.createParallelGroup(Alignment.TRAILING)
        							.addComponent(lblNewLabel_1)
        							.addComponent(lblNewLabel)))
        					.addComponent(lblNewLabel_3))
        				.addComponent(lblNewLabel_4))
        			.addGap(18)
        			.addGroup(gl_pl_farm.createParallelGroup(Alignment.LEADING)
        				.addComponent(tf_f2)
        				.addComponent(tf_f1, GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
        				.addComponent(tf_f4)
        				.addComponent(tf_f3)
        				.addComponent(tf_f5, GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE))
        			.addContainerGap())
        );
        gl_pl_farm.setVerticalGroup(
        	gl_pl_farm.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_farm.createSequentialGroup()
        			.addGroup(gl_pl_farm.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel)
        				.addComponent(tf_f1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_pl_farm.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_1)
        				.addComponent(tf_f2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_pl_farm.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_3)
        				.addComponent(tf_f3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_pl_farm.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_2)
        				.addComponent(tf_f4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addGroup(gl_pl_farm.createParallelGroup(Alignment.BASELINE)
        				.addComponent(lblNewLabel_4)
        				.addComponent(tf_f5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pl_farm.setLayout(gl_pl_farm);
        
        tf_alerg = new CustomTextField();
        tf_alerg.setFont(new Font("Tahoma", Font.PLAIN, 12));
        tf_alerg.setColumns(10);
        GroupLayout gl_pl_alerg = new GroupLayout(pl_alerg);
        gl_pl_alerg.setHorizontalGroup(
        	gl_pl_alerg.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_alerg.createSequentialGroup()
        			.addComponent(tf_alerg, GroupLayout.PREFERRED_SIZE, 450, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(30, Short.MAX_VALUE))
        );
        gl_pl_alerg.setVerticalGroup(
        	gl_pl_alerg.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_alerg.createSequentialGroup()
        			.addComponent(tf_alerg, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pl_alerg.setLayout(gl_pl_alerg);
        contentPane.setLayout(gl_contentPane);
		setBounds(10, 50, 1000, 741);
		
	}

	public void showAnamnezForm() {
		ClearAnamnezForm();
		setVisible(true);

	}
	public void ClearAnamnezForm() {
		tf_alerg.setText(null);
        btng_gk.clearSelection();
        btng_rf.clearSelection();
        btng_gkm.clearSelection();
        btng_rfm.clearSelection();

	}
}
