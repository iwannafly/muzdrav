package ru.nkz.ivcgzo.clientOutPutInfo;

import java.awt.EventQueue;

import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.border.BevelBorder;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.MatteBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.JScrollPane;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Font;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JSeparator;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JCheckBox;
<<<<<<< HEAD
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
=======
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.Input_info;
import ru.nkz.ivcgzo.thriftOutputInfo.ThriftOutputInfo;
>>>>>>> fa64ae36aa912c23df8ff3c7649cbe60be263b7c
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainForm extends Client<ThriftOutputInfo.Client> {

	private JFrame frame;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	
	public static ThriftOutputInfo.Client tcl;
	public Input_info inputInfo;
	private JTextField t_datn;
	private JTextField t_datk;
	private JTextField t_nuch;
	private JTextField tgodrog;
	private JTextField t_godotch;

	/**
	 * Launch the application.
	 */
	

	/**
	 * Create the application.
	 */
	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		super(conMan, authInfo, ThriftOutputInfo.Client.class, configuration.appId, configuration.thrPort, lncPrm);
		
		initialize();
		inputInfo.setKpolik(authInfo.clpu);
		inputInfo.setNamepol(authInfo.clpu_name);
	}

	@Override
	public String getName() {
		return configuration.appName;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Статистика");
		frame.setBounds(100, 100, 950, 700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 211, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 576, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JPanel panel = new JPanel();
		scrollPane_1.setRowHeaderView(panel);
		
		JLabel label = new JLabel("Период формирования");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel label_1 = new JLabel("с");
		
		JLabel label_2 = new JLabel("по");
		
		t_datn = new CustomDateEditor();
		t_datn.setColumns(10);
		
		t_datk = new CustomDateEditor();
		t_datk.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Номер участка");
		
		t_nuch = new JTextField();
		t_nuch.setColumns(10);
		
		JPanel panel_sv = new JPanel();
		panel_sv.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0421\u0432\u043E\u0434\u043A\u0438", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_vozr = new JPanel();
		panel_vozr.setBorder(new TitledBorder(null, "\u0412\u043E\u0437\u0440\u0430\u0441\u0442\u043D\u044B\u0435 \u043A\u0430\u0442\u0435\u0433\u043E\u0440\u0438\u0438", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_pr = new JPanel();
		panel_pr.setBorder(new TitledBorder(null, "\u041F\u0440\u0438\u043A\u0440\u0435\u043F\u043B\u0435\u043D\u0438\u0435", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_zab = new JPanel();
		panel_zab.setBorder(new TitledBorder(null, "\u0417\u0430\u0431\u043E\u043B\u0435\u0432\u0430\u043D\u0438\u044F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_disp = new JPanel();
		panel_disp.setBorder(new TitledBorder(null, "\u0414\u0438\u0441\u043F\u0430\u043D\u0441\u0435\u0440\u0438\u0437\u0430\u0446\u0438\u044F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(null, "\u0413\u043E\u0434\u043E\u0432\u043E\u0439 \u043E\u0442\u0447\u0435\u0442", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JButton button = new JButton("Выполнить");
		button.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(28)
							.addComponent(label))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
<<<<<<< HEAD
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(18)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(label_1)
											.addGap(12)
											.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(label_2)
											.addGap(18)
											.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(26)
									.addComponent(label_3)
									.addGap(18)
									.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addContainerGap()
									.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(23)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
										.addComponent(button)))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(48)
									.addComponent(label)))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(42)
=======
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(19)
>>>>>>> fa64ae36aa912c23df8ff3c7649cbe60be263b7c
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(lblNewLabel)
											.addGap(32)
											.addComponent(t_nuch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel.createSequentialGroup()
											.addComponent(label_1)
											.addGap(18)
											.addComponent(t_datn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addGap(18)
											.addComponent(label_2)
											.addGap(18)
											.addComponent(t_datk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
								.addGroup(gl_panel.createSequentialGroup()
<<<<<<< HEAD
									.addGap(41)
									.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(137, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(label_2)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
=======
									.addGap(27)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
										.addComponent(panel_zab, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
										.addComponent(panel_vozr, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
							.addGap(46)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_disp, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
								.addComponent(panel_pr, 0, 0, Short.MAX_VALUE)
								.addComponent(panel_sv, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE))))
					.addGap(178))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(248)
					.addComponent(button)
					.addContainerGap(390, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_sv, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
>>>>>>> fa64ae36aa912c23df8ff3c7649cbe60be263b7c
								.addComponent(label_1)
								.addComponent(t_datn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_2)
								.addComponent(t_datk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
<<<<<<< HEAD
								.addComponent(label_3)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(13))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(25)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addGap(18))
=======
								.addComponent(lblNewLabel)
								.addComponent(t_nuch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
>>>>>>> fa64ae36aa912c23df8ff3c7649cbe60be263b7c
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(29)
							.addComponent(panel_pr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(18)
							.addComponent(panel_vozr, GroupLayout.PREFERRED_SIZE, 97, GroupLayout.PREFERRED_SIZE)))
					.addGap(30)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_disp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_zab, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
					.addGap(38)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 132, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
					.addComponent(button)
					.addContainerGap())
		);
		
		JCheckBox ch_tab1000 = new JCheckBox("Таблица 1000");
		
		JCheckBox ch_tab2000 = new JCheckBox("Таблица 2000");
		
		JCheckBox ch_tab3000 = new JCheckBox("Таблица 3000");
		
		JCheckBox ch_tab4000 = new JCheckBox("Таблица 4000");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(ch_tab1000)
						.addComponent(ch_tab2000)
						.addComponent(ch_tab3000)
						.addComponent(ch_tab4000))
					.addContainerGap(153, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(ch_tab1000)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(ch_tab2000)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(ch_tab3000)
					.addPreferredGap(ComponentPlacement.RELATED, 3, Short.MAX_VALUE)
					.addComponent(ch_tab4000))
		);
		panel_1.setLayout(gl_panel_1);
		
		JLabel label_3 = new JLabel("Год рождения");
		
		JLabel label_4 = new JLabel("Отчетный год");
		
		tgodrog = new JTextField();
		tgodrog.setColumns(10);
		
		t_godotch = new JTextField();
		t_godotch.setColumns(10);
		GroupLayout gl_panel_disp = new GroupLayout(panel_disp);
		gl_panel_disp.setHorizontalGroup(
			gl_panel_disp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_disp.createSequentialGroup()
					.addGroup(gl_panel_disp.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_disp.createSequentialGroup()
							.addComponent(label_3)
							.addGap(18)
							.addComponent(tgodrog, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_disp.createSequentialGroup()
							.addComponent(label_4)
							.addGap(18)
							.addComponent(t_godotch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(17, Short.MAX_VALUE))
		);
		gl_panel_disp.setVerticalGroup(
			gl_panel_disp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_disp.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_disp.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_3)
						.addComponent(tgodrog, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
					.addGroup(gl_panel_disp.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_4)
						.addComponent(t_godotch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		panel_disp.setLayout(gl_panel_disp);
		
		JRadioButton rb_nozol = new JRadioButton("по нозологиям");
		
		JRadioButton rb_grzab = new JRadioButton("по группам заболеваний");
		GroupLayout gl_panel_zab = new GroupLayout(panel_zab);
		gl_panel_zab.setHorizontalGroup(
			gl_panel_zab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_zab.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_zab.createParallelGroup(Alignment.LEADING)
						.addComponent(rb_nozol)
						.addComponent(rb_grzab))
					.addContainerGap(64, Short.MAX_VALUE))
		);
		gl_panel_zab.setVerticalGroup(
			gl_panel_zab.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_zab.createSequentialGroup()
					.addComponent(rb_nozol)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(rb_grzab)
					.addContainerGap())
		);
		panel_zab.setLayout(gl_panel_zab);
		
		JRadioButton rb_obrativ = new JRadioButton("по обратившимся всего");
		
		JRadioButton rb_prikrep = new JRadioButton("по прикрепленным к поликлинике");
		GroupLayout gl_panel_pr = new GroupLayout(panel_pr);
		gl_panel_pr.setHorizontalGroup(
			gl_panel_pr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_pr.createSequentialGroup()
					.addGroup(gl_panel_pr.createParallelGroup(Alignment.LEADING)
						.addComponent(rb_obrativ)
						.addComponent(rb_prikrep))
					.addContainerGap(49, Short.MAX_VALUE))
		);
		gl_panel_pr.setVerticalGroup(
			gl_panel_pr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_pr.createSequentialGroup()
					.addComponent(rb_obrativ)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(rb_prikrep))
		);
		panel_pr.setLayout(gl_panel_pr);
		
		JRadioButton rb_deti = new JRadioButton("Дети до 14 лет");
		
		JRadioButton radioButton = new JRadioButton("Подростки 15-18 лет");
		
		JRadioButton rb_vzros = new JRadioButton("Взрослые");
		GroupLayout gl_panel_vozr = new GroupLayout(panel_vozr);
		gl_panel_vozr.setHorizontalGroup(
			gl_panel_vozr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_vozr.createSequentialGroup()
					.addGroup(gl_panel_vozr.createParallelGroup(Alignment.LEADING)
						.addComponent(rb_deti)
						.addComponent(radioButton)
						.addComponent(rb_vzros))
					.addContainerGap(48, Short.MAX_VALUE))
		);
		gl_panel_vozr.setVerticalGroup(
			gl_panel_vozr.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_vozr.createSequentialGroup()
					.addComponent(rb_deti)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(radioButton)
					.addPreferredGap(ComponentPlacement.RELATED, 1, Short.MAX_VALUE)
					.addComponent(rb_vzros))
		);
		panel_vozr.setLayout(gl_panel_vozr);
		
		JRadioButton rb_datpos = new JRadioButton("по дате посещения");
		
		JRadioButton rb_datzap = new JRadioButton("по дате записи в базу");
		GroupLayout gl_panel_sv = new GroupLayout(panel_sv);
		gl_panel_sv.setHorizontalGroup(
			gl_panel_sv.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_sv.createSequentialGroup()
					.addGroup(gl_panel_sv.createParallelGroup(Alignment.LEADING)
						.addComponent(rb_datpos)
						.addComponent(rb_datzap))
					.addContainerGap(69, Short.MAX_VALUE))
		);
		gl_panel_sv.setVerticalGroup(
			gl_panel_sv.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_sv.createSequentialGroup()
					.addComponent(rb_datpos)
					.addPreferredGap(ComponentPlacement.RELATED, 1, Short.MAX_VALUE)
					.addComponent(rb_datzap))
		);
		panel_sv.setLayout(gl_panel_sv);
		panel.setLayout(gl_panel);
		ButtonGroup GBox1 = new ButtonGroup();
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		final JTree tree = new JTree();
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				Object lastPath = e.getNewLeadSelectionPath().getLastPathComponent();
				if(tree.getLastSelectedPathComponent().toString().equals("Табель работы врача"))
				{
					tableVrach tableVrach = new tableVrach();
					tableVrach.frameVr.setVisible(true); 
					tableVrach.frameVr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				}

	
			}
		});
		
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Выходные формы") {
				{
					DefaultMutableTreeNode node_1;
					DefaultMutableTreeNode node_2;
					DefaultMutableTreeNode node_3;
					node_1 = new DefaultMutableTreeNode("Поликлиника");
						node_1.add(new DefaultMutableTreeNode("Табель работы врача"));
						node_2 = new DefaultMutableTreeNode("Регламентные формы");
							node_3 = new DefaultMutableTreeNode("Отчеты по форме 025");
								node_3.add(new DefaultMutableTreeNode("Сводная ведомость учета зарегистрированных заболеваний"));
								node_3.add(new DefaultMutableTreeNode("Факторы, влияющие на состояние здоровья"));
							node_2.add(node_3);
							node_3 = new DefaultMutableTreeNode("Отчеты по форме 039");
								node_3.add(new DefaultMutableTreeNode("Сведения о структуре посещений"));
								node_3.add(new DefaultMutableTreeNode("Отчет о деятельности поликлиники"));
								node_3.add(new DefaultMutableTreeNode("Форма №1, раздел 2, таблица 1 (общ)"));
								node_3.add(new DefaultMutableTreeNode("Форма №1, раздел 2, таблица 1 (врачи и ср.медработники)"));
								node_3.add(new DefaultMutableTreeNode("Форма №1, раздел 2, таблица 2 (общ)"));
								node_3.add(new DefaultMutableTreeNode("Форма №1, раздел 2, таблица 2 (врачи и ср.медработники)"));
								node_3.add(new DefaultMutableTreeNode("Сведения о работе, таблица 1 (общ)"));
								node_3.add(new DefaultMutableTreeNode("Сведения о работе, таблица 1 (врачи и ср.медработники)"));
							node_2.add(node_3);
						node_1.add(node_2);
						node_2 = new DefaultMutableTreeNode(" Запросы");
							node_2.add(new DefaultMutableTreeNode("Формирование запроса"));
							node_3 = new DefaultMutableTreeNode("Запросные отчеты");
								node_3.add(new DefaultMutableTreeNode("Количество посещений и обращений"));
								node_3.add(new DefaultMutableTreeNode("Количество пациентов, впервые посетивших поликлинику"));
								node_3.add(new DefaultMutableTreeNode("Количество детей по группам здоровья"));
								node_3.add(new DefaultMutableTreeNode("Работа врачей в поликлинике"));
								node_3.add(new DefaultMutableTreeNode("Работа врачей на дому"));
								node_3.add(new DefaultMutableTreeNode("Сведения о заболеваниях у детей до 18 лет"));
								node_3.add(new DefaultMutableTreeNode("Список больных сахарным диабетом\t\t\t\t"));
							node_2.add(node_3);
						node_1.add(node_2);
						node_1.add(new DefaultMutableTreeNode("Реестры"));
						node_2 = new DefaultMutableTreeNode("Выгрузка");
							node_2.add(new DefaultMutableTreeNode("Статистический талон (ф.025)"));
							node_2.add(new DefaultMutableTreeNode("Учет работы врача (ф.039)"));
							node_2.add(new DefaultMutableTreeNode("Дневной стационар (ф.003)"));
							node_2.add(new DefaultMutableTreeNode("Диспансерное наблюдение за больными\t\t\t"));
						node_1.add(node_2);
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Стационар");
						node_1.add(new DefaultMutableTreeNode("Регламентные"));
						node_1.add(new DefaultMutableTreeNode("Запросы"));
						node_1.add(new DefaultMutableTreeNode("Реестры"));
						node_1.add(new DefaultMutableTreeNode("Выгрузка"));
					add(node_1);
					node_1 = new DefaultMutableTreeNode("Параслужба");
						node_1.add(new DefaultMutableTreeNode("Отчеты"));
						node_1.add(new DefaultMutableTreeNode("Запросы"));
						node_1.add(new DefaultMutableTreeNode(""));
						node_1.add(new DefaultMutableTreeNode(""));
					add(node_1);
				}
			}
		));
		tree.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
				
		scrollPane.setViewportView(tree);
		frame.getContentPane().setLayout(groupLayout);
		
		
	}

	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		super.onConnect(conn);
		if (conn instanceof ThriftOutputInfo.Client) {
			tcl = thrClient;
			try {
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
		
	}
}
