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
import javax.swing.JSeparator;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import javax.swing.JCheckBox;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;

public class MainForm {

	private JFrame frame;
	private CustomDateEditor t_datn;
	private CustomDateEditor t_datk;
	private JTextField t_nuch;
	private JTextField t_godrog;
	private JTextField t_godotch;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainForm window = new MainForm();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Статистика");
		frame.setBounds(100, 100, 821, 651);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 207, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 654, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JPanel panel = new JPanel();
		scrollPane_1.setViewportView(panel);
		
		JButton button = new JButton("Выполнить");
		
		JLabel label = new JLabel("Период формирования");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel label_1 = new JLabel("с");
		label_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel label_2 = new JLabel("по");
		label_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		t_datn = new CustomDateEditor();
		t_datn.setColumns(10);
		
		t_datk = new CustomDateEditor();
		t_datk.setColumns(10);
		
		JSeparator separator = new JSeparator();
		
		final JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0412\u043E\u0437\u0440\u0430\u0441\u0442\u043D\u044B\u0435 \u043A\u0430\u0442\u0435\u0433\u043E\u0440\u0438\u0438", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		final JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Дети до 14 лет");
		
		final JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Подростки 15-18 лет");
		
		final JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("Взрослые");
		final ButtonGroup GBox1 = new ButtonGroup();
		panel_1.add(rdbtnNewRadioButton_1);
		panel_1.add(rdbtnNewRadioButton_2);
		panel_1.add(rdbtnNewRadioButton_3);
		GBox1.add(rdbtnNewRadioButton_1);
		GBox1.add(rdbtnNewRadioButton_2);
		GBox1.add(rdbtnNewRadioButton_3);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u041F\u0440\u0438\u043A\u0440\u0435\u043F\u043B\u0435\u043D\u0438\u0435", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel label_3 = new JLabel("Номер участка");
		
		t_nuch = new JTextField();
		t_nuch.setColumns(10);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "\u0417\u0430\u0431\u043E\u043B\u0435\u0432\u0430\u043D\u0438\u044F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0414\u0438\u0441\u043F\u0430\u043D\u0441\u0435\u0440\u0438\u0437\u0430\u0446\u0438\u044F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, "\u0413\u043E\u0434\u043E\u0432\u043E\u0439 \u043E\u0442\u0447\u0435\u0442", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(separator, GroupLayout.PREFERRED_SIZE, 483, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel.createSequentialGroup()
										.addGap(49)
										.addComponent(label))
									.addGroup(gl_panel.createSequentialGroup()
										.addGap(18)
										.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
											.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE)
											.addGroup(gl_panel.createSequentialGroup()
												.addComponent(label_1)
												.addGap(12)
												.addComponent(t_datn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
												.addGap(18)
												.addComponent(label_2)
												.addGap(18)
												.addComponent(t_datk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
									.addGroup(gl_panel.createSequentialGroup()
										.addGap(26)
										.addComponent(label_3)
										.addGap(18)
										.addComponent(t_nuch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_panel.createSequentialGroup()
									.addContainerGap()
									.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 231, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(43)
									.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
										.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE)
										.addComponent(button))))
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(42)
									.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(34)
											.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel.createSequentialGroup()
											.addGap(7)
											.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE))))
								.addGroup(gl_panel.createSequentialGroup()
									.addGap(41)
									.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(68, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addContainerGap()
							.addComponent(label)
							.addPreferredGap(ComponentPlacement.UNRELATED, 11, Short.MAX_VALUE)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(label_2)
								.addComponent(t_datk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_1)
								.addComponent(t_datn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(label_3)
								.addComponent(t_nuch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(13))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(25)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(separator, GroupLayout.PREFERRED_SIZE, 2, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 92, GroupLayout.PREFERRED_SIZE)
							.addGap(18))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
							.addGap(39)))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
							.addGap(49)
							.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE)
							.addGap(57)
							.addComponent(button))
						.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(108))
		);
		
		JCheckBox chckbxNewCheckBox = new JCheckBox(" Таблица 1000");
		
		JCheckBox checkBox = new JCheckBox(" Таблица 2000");
		
		JCheckBox checkBox_1 = new JCheckBox(" Таблица 3000");
		
		JCheckBox chckbxNewCheckBox_1 = new JCheckBox(" Таблица 4000");
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
						.addComponent(chckbxNewCheckBox)
						.addComponent(checkBox)
						.addComponent(checkBox_1)
						.addComponent(chckbxNewCheckBox_1))
					.addContainerGap(97, Short.MAX_VALUE))
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addContainerGap()
					.addComponent(chckbxNewCheckBox)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(checkBox)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(checkBox_1)
					.addPreferredGap(ComponentPlacement.RELATED, 2, Short.MAX_VALUE)
					.addComponent(chckbxNewCheckBox_1))
		);
		panel_6.setLayout(gl_panel_6);
		
		JLabel lblNewLabel = new JLabel("Год рождения");
		
		JLabel label_4 = new JLabel("Отчетный год");
		
		t_godrog = new JTextField();
		t_godrog.setColumns(10);
		
		t_godotch = new JTextField();
		t_godotch.setColumns(10);
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_5.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addGap(18)
							.addComponent(t_godrog, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_5.createSequentialGroup()
							.addComponent(label_4)
							.addGap(18)
							.addComponent(t_godotch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(t_godrog, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_4)
						.addComponent(t_godotch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(13, Short.MAX_VALUE))
		);
		panel_5.setLayout(gl_panel_5);
		
		JRadioButton radioButton_2 = new JRadioButton("по нозологиям");
		
		JRadioButton rdbtnNewRadioButton_5 = new JRadioButton("по группам заболеваний");
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(radioButton_2)
						.addComponent(rdbtnNewRadioButton_5))
					.addContainerGap(86, Short.MAX_VALUE))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addComponent(radioButton_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnNewRadioButton_5)
					.addContainerGap(19, Short.MAX_VALUE))
		);
		panel_4.setLayout(gl_panel_4);
		
		JRadioButton radioButton = new JRadioButton("по обратившимся всего");
		
		JRadioButton radioButton_1 = new JRadioButton("по приклепленным к поликлинике");
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(radioButton)
						.addComponent(radioButton_1))
					.addContainerGap(54, Short.MAX_VALUE))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addComponent(radioButton)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(radioButton_1)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_3.setLayout(gl_panel_3);
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("по дате посещения");
		
		JRadioButton rdbtnNewRadioButton_4 = new JRadioButton("по дате записи в базу");
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnNewRadioButton)
						.addComponent(rdbtnNewRadioButton_4))
					.addContainerGap(81, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(rdbtnNewRadioButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnNewRadioButton_4)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_2.setLayout(gl_panel_2);
		

		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(rdbtnNewRadioButton_3)
						.addComponent(rdbtnNewRadioButton_2)
						.addComponent(rdbtnNewRadioButton_1))
					.addContainerGap(73, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(rdbtnNewRadioButton_1)
					.addGap(3)
					.addComponent(rdbtnNewRadioButton_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnNewRadioButton_3)
					.addContainerGap(23, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		
		JTree tree = new JTree();
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				Object lastPath = e.getNewLeadSelectionPath().getLastPathComponent();
		 			if (lastPath.toString() ==  "Факторы, влияющие на состояние здоровья");
		 			JOptionPane.showMessageDialog(frame, "juerhyweutr");
	
			}
		});
		tree.setModel(new DefaultTreeModel(
			new DefaultMutableTreeNode("Выходные формы") {
				{
					DefaultMutableTreeNode node_1;
					DefaultMutableTreeNode node_2;
					DefaultMutableTreeNode node_3;
					node_1 = new DefaultMutableTreeNode("Поликлиника");
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
}
