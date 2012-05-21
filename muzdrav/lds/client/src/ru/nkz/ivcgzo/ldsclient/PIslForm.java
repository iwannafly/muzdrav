package ru.nkz.ivcgzo.ldsclient;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTabbedPane;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;
import java.awt.Window.Type;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.table.DefaultTableModel;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.ldsThrift.ObInfIsl;
import ru.nkz.ivcgzo.ldsThrift.Patient;

public class PIslForm {

	public JFrame frame;
	private CustomTable<Patient, Patient._Fields> tpatient;
	private JTextField tFdatap;
	private JTextField tFdatav;
	private JTextField tFnprob;
	private JTextField tFnaprotd;
	private JTextField tFfio;
	private JTextField tFkodvr;
	private JTextField tFkodm;
	private JTextField tFkods;
	private JTextField tFdiag;
	private CustomTable<ObInfIsl, ObInfIsl._Fields> table;
	private JTable table_1;
	private JTextField tFkodisl;
	private JTextField tFrez_name;
	private JTextField tFmodel;
	private JTextField tFanestez;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					PIslForm window = new PIslForm();
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
	public PIslForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 711, 763);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		
		JSplitPane splitPane = new JSplitPane();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 653, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 530, Short.MAX_VALUE))
		);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		tpatient = new CustomTable<>(false, true, Patient.class, 1, "Фамилия", 2, "Имя", 3, "Отчество", 4, "Дата рождения");
		scrollPane.setViewportView(tpatient);
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane.setRightComponent(splitPane_1);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		splitPane_1.setLeftComponent(scrollPane_1);
		
		table = new CustomTable<>(false, true, ObInfIsl.class, 2, "Код отделения", 3, "№ пробы", 4, "Орган. и системы", 5, "Исследование", 6, "Дата пост.", 7, "Дата выпол.", 8, "Причина", 9, "Обстоятельства", 10, "Направлен", 11, "Код направ. ЛПУ", 12, "ФИО направ. врача", 13, "Вид оплаты", 14, "Диагноз", 15, "Код врача", 16,"Код медсес.", 17, "Код санит.", 18, "Дата за полнения");
		table.setFillsViewportHeight(true);
		scrollPane_1.setViewportView(table);
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setRightComponent(splitPane_2);
		
		JPanel panel_1 = new JPanel();
		splitPane_2.setLeftComponent(panel_1);
		
		JLabel lblNewLabel = new JLabel("Органы и системы");
		
		JComboBox cBpcisl = new JComboBox();
		
		JLabel lblNewLabel_1 = new JLabel("Дата поступления");
		
		JLabel lblNewLabel_2 = new JLabel("Дата выполнения");
		
		tFdatap = new JTextField();
		tFdatap.setColumns(10);
		
		tFdatav = new JTextField();
		tFdatav.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Помер пробы");
		
		tFnprob = new JTextField();
		tFnprob.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Причина обращения");
		
		JLabel lblNewLabel_5 = new JLabel("Обстоятельства обращения");
		
		JComboBox cBprichina = new JComboBox();
		
		JComboBox cBpopl = new JComboBox();
		
		JLabel lblNewLabel_6 = new JLabel("Кем направлен");
		
		JLabel lblNewLabel_7 = new JLabel("Код направившего ЛПУ");
		
		JComboBox cBnapravl = new JComboBox();
		
		tFnaprotd = new JTextField();
		tFnaprotd.setColumns(10);
		
		JButton btnnaprotd = new JButton(">>");
		
		JLabel lblNewLabel_8 = new JLabel("Ф.И.О. направившего врача");
		
		JLabel lblNewLabel_9 = new JLabel("Вид оплаты");
		
		tFfio = new JTextField();
		tFfio.setColumns(10);
		
		JLabel lblNewLabel_10 = new JLabel("Код врача");
		
		JComboBox cBvopl = new JComboBox();
		
		tFkodvr = new JTextField();
		tFkodvr.setColumns(10);
		
		JLabel lblNewLabel_11 = new JLabel("<html>Код медсестры-лаборанта</html>");
		
		tFkodm = new JTextField();
		tFkodm.setColumns(10);
		
		JButton btnkodvr = new JButton(">>");
		
		JButton btnkodm = new JButton(">>");
		
		JLabel lblNewLabel_13 = new JLabel("Код санитарки");
		
		tFkods = new JTextField();
		tFkods.setColumns(10);
		
		JButton btnkods = new JButton(">>");
		
		JLabel lblNewLabel_14 = new JLabel("Диагноз");
		
		tFdiag = new JTextField();
		tFdiag.setColumns(10);
		
		JButton btnNewButton_3 = new JButton(">>");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(121)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cBpcisl, GroupLayout.PREFERRED_SIZE, 297, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_1.createSequentialGroup()
											.addGap(98)
											.addComponent(btnkodvr, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel_1.createSequentialGroup()
											.addComponent(lblNewLabel_10)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(tFkodvr, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel_13)
									.addGap(13)
									.addComponent(tFkods, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnkods, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 251, Short.MAX_VALUE)
									.addComponent(btnNewButton_3, GroupLayout.PREFERRED_SIZE, 0, GroupLayout.PREFERRED_SIZE)
									.addGap(55))
								.addGroup(gl_panel_1.createSequentialGroup()
									.addComponent(lblNewLabel_1)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tFdatap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel_2)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tFdatav, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel_3)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(tFnprob, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(70))))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_6)
								.addComponent(lblNewLabel_8)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_1.createSequentialGroup()
											.addComponent(lblNewLabel_4)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(cBprichina, 0, 190, Short.MAX_VALUE))
										.addGroup(gl_panel_1.createSequentialGroup()
											.addGap(86)
											.addComponent(cBnapravl, 0, 211, Short.MAX_VALUE))
										.addGroup(gl_panel_1.createSequentialGroup()
											.addGap(148)
											.addComponent(tFfio, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panel_1.createSequentialGroup()
											.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
												.addGroup(gl_panel_1.createSequentialGroup()
													.addComponent(lblNewLabel_7)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(tFnaprotd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
												.addGroup(gl_panel_1.createSequentialGroup()
													.addComponent(lblNewLabel_9)
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(cBvopl, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(btnnaprotd, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panel_1.createSequentialGroup()
											.addGap(15)
											.addComponent(lblNewLabel_5)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(cBpopl, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE))))
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(139)
									.addComponent(lblNewLabel_11, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tFkodm, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(btnkodm, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(lblNewLabel_14)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tFdiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
					.addGap(33))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(cBpcisl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_1)
						.addComponent(tFdatap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2)
						.addComponent(tFdatav, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_3)
						.addComponent(tFnprob, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_4)
						.addComponent(cBprichina, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cBpopl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_6)
						.addComponent(lblNewLabel_7)
						.addComponent(cBnapravl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tFnaprotd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnnaprotd))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_8)
						.addComponent(tFfio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_9)
						.addComponent(cBvopl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel_1.createSequentialGroup()
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel_1.createSequentialGroup()
										.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
											.addComponent(tFkodm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
											.addComponent(btnkodm))
										.addPreferredGap(ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
										.addComponent(btnkods)
										.addGap(12))
									.addGroup(gl_panel_1.createSequentialGroup()
										.addComponent(lblNewLabel_11, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
											.addComponent(lblNewLabel_13)
											.addComponent(tFkods, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGap(14))))
							.addGroup(gl_panel_1.createSequentialGroup()
								.addGap(22)
								.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblNewLabel_10)
									.addComponent(tFkodvr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(btnkodvr))
								.addContainerGap()))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(26)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_14)
								.addComponent(tFdiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnNewButton_3))
							.addContainerGap())))
		);
		panel_1.setLayout(gl_panel_1);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane_2.setRightComponent(tabbedPane);
		
		JLayeredPane layeredPane = new JLayeredPane();
		tabbedPane.addTab("Диагностика", null, layeredPane, null);
		
		JPanel panel_4 = new JPanel();
		GroupLayout gl_layeredPane = new GroupLayout(layeredPane);
		gl_layeredPane.setHorizontalGroup(
			gl_layeredPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
		);
		gl_layeredPane.setVerticalGroup(
			gl_layeredPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 390, Short.MAX_VALUE)
		);
		
		JButton brez_name = new JButton(">>");
		
		JLabel label = new JLabel("Исследование");
		
		tFkodisl = new JTextField();
		tFkodisl.setColumns(10);
		
		JComboBox cBkodisl = new JComboBox();
		
		JLabel label_1 = new JLabel("Количество");
		
		JLabel label_2 = new JLabel("Анамнез");
		
		JSpinner spkol = new JSpinner();
		
		JLabel label_3 = new JLabel("Результат");
		
		JComboBox cBrez = new JComboBox();
		
		JLabel label_4 = new JLabel("Стоимость");
		
		JTextPane tPanamnez = new JTextPane();
		
		JButton button_1 = new JButton("Добавить");
		
		JButton button_2 = new JButton("Выбрать");
		
		tFrez_name = new JTextField();
		tFrez_name.setColumns(10);
		
		JButton button_3 = new JButton("Добавить");
		
		JButton button_4 = new JButton("Выбрать");
		
		JComboBox cBpcod_m = new JComboBox();
		
		JLabel label_5 = new JLabel("Модель аппарата");
		
		tFmodel = new JTextField();
		tFmodel.setColumns(10);
		
		JButton bmodel = new JButton(">>");
		
		JLabel label_6 = new JLabel("Анестезия");
		
		tFanestez = new JTextField();
		tFanestez.setColumns(10);
		
		JButton banestez = new JButton(">>");
		
		JLabel label_7 = new JLabel("Заключение");
		
		JLabel label_8 = new JLabel("Описание ис-ия");
		
		JTextPane tPop_name = new JTextPane();
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(tFkodisl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addComponent(cBkodisl, GroupLayout.PREFERRED_SIZE, 389, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(label_1, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(spkol, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(label_3)
							.addGap(4)
							.addComponent(cBrez, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(label_4)
							.addGap(6)
							.addComponent(cBpcod_m, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(94)
							.addComponent(button_1)
							.addGap(42)
							.addComponent(button_2))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(94)
							.addComponent(button_3)
							.addGap(41)
							.addComponent(button_4))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(label_7)
							.addGap(18)
							.addComponent(tFrez_name, GroupLayout.PREFERRED_SIZE, 415, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(brez_name, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(label_8)
								.addComponent(label_2))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(tPanamnez, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
								.addComponent(tPop_name, Alignment.LEADING))
							.addGap(6)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(label_6)
								.addComponent(label_5))
							.addGap(4)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(tFanestez, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(tFmodel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(6)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING, false)
								.addComponent(bmodel, 0, 0, Short.MAX_VALUE)
								.addComponent(banestez, GroupLayout.PREFERRED_SIZE, 41, Short.MAX_VALUE))))
					.addContainerGap(65, Short.MAX_VALUE))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(3)
							.addComponent(label))
						.addComponent(tFkodisl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(cBkodisl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(3)
							.addComponent(label_1))
						.addComponent(spkol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(3)
							.addComponent(label_3))
						.addComponent(cBrez, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(3)
							.addComponent(label_4))
						.addComponent(cBpcod_m, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(35)
							.addComponent(label_2))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(4)
							.addComponent(label_6)
							.addGap(19)
							.addComponent(label_5))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(1)
							.addComponent(tFanestez, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(13)
							.addComponent(tFmodel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(banestez)
							.addGap(11)
							.addComponent(bmodel))
						.addComponent(tPanamnez, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(button_1)
						.addComponent(button_2))
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(48)
							.addComponent(label_8))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tPop_name, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(button_3)
						.addComponent(button_4))
					.addGap(10)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_7)
						.addComponent(tFrez_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(brez_name))
					.addContainerGap(16, Short.MAX_VALUE))
		);
		panel_4.setLayout(gl_panel_4);
		layeredPane.setLayout(gl_layeredPane);
		
		JLayeredPane layeredPane_2 = new JLayeredPane();
		tabbedPane.addTab("Лаборатория", null, layeredPane_2, null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 664, 350);
		layeredPane_2.add(panel_2);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 664, Short.MAX_VALUE)
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 350, Short.MAX_VALUE)
		);
		
		table_1 = new JTable();
		scrollPane_2.setViewportView(table_1);
		panel_2.setLayout(gl_panel_2);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(0, 352, 664, 48);
		layeredPane_2.add(panel_3);
		
		JLabel lblNewLabel_23 = new JLabel("Метод исследования");
		
		JComboBox cBpcod_m_1 = new JComboBox();
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_23)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(cBpcod_m_1, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(345, Short.MAX_VALUE))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_23)
						.addComponent(cBpcod_m_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(23, Short.MAX_VALUE))
		);
		panel_3.setLayout(gl_panel_3);
		frame.getContentPane().setLayout(groupLayout);
	}
}
