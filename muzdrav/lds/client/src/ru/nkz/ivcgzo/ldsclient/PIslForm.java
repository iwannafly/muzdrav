package ru.nkz.ivcgzo.ldsclient;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.ldsThrift.ObInfIsl;
import ru.nkz.ivcgzo.ldsThrift.Patient;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;

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
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cBprichina;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cBpopl;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cBnapravl;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cBvopl;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cBrez;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	
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
		frame.setBounds(100, 100, 795, 760);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		
		JSplitPane splitPane = new JSplitPane();
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 787, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(splitPane, GroupLayout.PREFERRED_SIZE, 666, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(732, Short.MAX_VALUE))
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
		
		cBprichina = new ThriftIntegerClassifierCombobox<>(true);
		
		cBpopl = new ThriftIntegerClassifierCombobox<>(true);
		
		JLabel lblNewLabel_6 = new JLabel("Кем направлен");
		
		JLabel lblNewLabel_7 = new JLabel("Код направившего ЛПУ");
		
		cBnapravl = new ThriftIntegerClassifierCombobox<>(true);
		
		tFnaprotd = new JTextField();
		tFnaprotd.setColumns(10);
		
		JButton btnnaprotd = new JButton(">>");
		
		JLabel lblNewLabel_8 = new JLabel("Ф.И.О. направившего врача");
		
		JLabel lblNewLabel_9 = new JLabel("Вид оплаты");
		
		tFfio = new JTextField();
		tFfio.setColumns(10);
		
		JLabel lblNewLabel_10 = new JLabel("Код врача");
		
		cBvopl = new ThriftIntegerClassifierCombobox<>(true);
		
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
					.addGap(121)
					.addComponent(lblNewLabel)
					.addGap(4)
					.addComponent(cBpcisl, GroupLayout.PREFERRED_SIZE, 297, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(10)
					.addComponent(lblNewLabel_1)
					.addGap(4)
					.addComponent(tFdatap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(lblNewLabel_2)
					.addGap(4)
					.addComponent(tFdatav, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(lblNewLabel_3)
					.addGap(4)
					.addComponent(tFnprob, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(10)
					.addComponent(lblNewLabel_4)
					.addGap(4)
					.addComponent(cBprichina, GroupLayout.PREFERRED_SIZE, 196, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(lblNewLabel_5)
					.addGap(4)
					.addComponent(cBpopl, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(10)
					.addComponent(lblNewLabel_6)
					.addGap(10)
					.addComponent(cBnapravl, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(lblNewLabel_7, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(tFnaprotd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(btnnaprotd, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(10)
					.addComponent(lblNewLabel_8)
					.addGap(4)
					.addComponent(tFfio, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(lblNewLabel_9, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
					.addGap(4)
					.addComponent(cBvopl, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(10)
					.addComponent(lblNewLabel_10)
					.addGap(4)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(41)
							.addComponent(btnkodvr, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addComponent(tFkodvr, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
					.addGap(4)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewLabel_11, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_13))
					.addGap(3)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(1)
							.addComponent(tFkodm, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
						.addComponent(tFkods, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(btnkodm, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnkods, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addComponent(lblNewLabel_14)
					.addGap(4)
					.addComponent(tFdiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel))
						.addComponent(cBpcisl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(11)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel_1))
						.addComponent(tFdatap, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel_2))
						.addComponent(tFdatav, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel_3))
						.addComponent(tFnprob, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(5)
							.addComponent(lblNewLabel_4))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(2)
							.addComponent(cBprichina, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblNewLabel_5, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(2)
							.addComponent(cBpopl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(6)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(4)
							.addComponent(lblNewLabel_6))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(1)
							.addComponent(cBnapravl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(4)
							.addComponent(lblNewLabel_7))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(1)
							.addComponent(tFnaprotd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnnaprotd))
					.addGap(6)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel_8))
						.addComponent(tFfio, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel_9))
						.addComponent(cBvopl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(20)
							.addComponent(lblNewLabel_10))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(16)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(btnkodvr)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(1)
									.addComponent(tFkodvr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(lblNewLabel_11, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
							.addGap(9)
							.addComponent(lblNewLabel_13))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(1)
							.addComponent(tFkodm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(11)
							.addComponent(tFkods, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(btnkodm)
							.addGap(8)
							.addComponent(btnkods))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(23)
							.addComponent(lblNewLabel_14))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(20)
							.addComponent(tFdiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
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
		
		JSpinner spkol = new JSpinner();
		
		JLabel label_3 = new JLabel("Результат");
		
		cBrez = new ThriftIntegerClassifierCombobox<>(true);
		
		JLabel label_4 = new JLabel("Стоимость");
		
		tFrez_name = new JTextField();
		tFrez_name.setColumns(10);
		
		JButton button_3 = new JButton("Добавить");
		
		JButton button_4 = new JButton("Выбрать");
		
		JComboBox cBpcod_m = new JComboBox();
		
		JLabel label_7 = new JLabel("Заключение");
		
		JLabel label_8 = new JLabel("Описание ис-ия");
		
		JTextPane tPop_name = new JTextPane();
		
		JLabel label_2 = new JLabel("Анамнез");
		
		JTextPane textPane = new JTextPane();
		
		JButton button = new JButton("Добавить");
		
		JButton button_1 = new JButton("Выбрать");
		
		JLabel label_5 = new JLabel("Анестезия");
		
		JLabel label_6 = new JLabel("Модель аппарата");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		
		JButton button_2 = new JButton(">>");
		
		JButton button_5 = new JButton(">>");
		
		JLabel label_9 = new JLabel("Анамнез");
		
		JTextPane textPane_1 = new JTextPane();
		
		JButton button_6 = new JButton("Добавить");
		
		JButton button_7 = new JButton("Выбрать");
		
		JLabel label_10 = new JLabel("Анестезия");
		
		JLabel label_11 = new JLabel("Модель аппарата");
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		
		JButton button_8 = new JButton(">>");
		
		JButton button_9 = new JButton(">>");
		
		JLabel label_12 = new JLabel("Анамнез");
		label_12.setEnabled(false);
		
		JTextPane textPane_2 = new JTextPane();
		textPane_2.setEnabled(false);
		
		JButton button_10 = new JButton("Добавить");
		button_10.setEnabled(false);
		
		JButton button_11 = new JButton("Выбрать");
		button_11.setEnabled(false);
		
		JLabel label_13 = new JLabel("Анестезия");
		label_13.setEnabled(false);
		
		JLabel label_14 = new JLabel("Модель аппарата");
		label_14.setEnabled(false);
		
		textField_4 = new JTextField();
		textField_4.setEnabled(false);
		textField_4.setColumns(10);
		
		textField_5 = new JTextField();
		textField_5.setEnabled(false);
		textField_5.setColumns(10);
		
		JButton button_12 = new JButton(">>");
		button_12.setEnabled(false);
		
		JButton button_13 = new JButton(">>");
		button_13.setEnabled(false);
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING, false)
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
							.addComponent(label_8)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tPop_name))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(label_2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addGap(41)
							.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(label_5, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_6, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
							.addGap(4)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
							.addGap(6)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
								.addComponent(button_5, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(94)
							.addComponent(button, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addGap(42)
							.addComponent(button_1, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(label_9, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addGap(41)
							.addComponent(textPane_1, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(label_10, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_11, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
							.addGap(4)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
							.addGap(6)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(button_8, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
								.addComponent(button_9, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(94)
							.addComponent(button_6, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addGap(42)
							.addComponent(button_7, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(label_12, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
							.addGap(41)
							.addComponent(textPane_2, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
							.addGap(6)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(label_13, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
								.addComponent(label_14, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE))
							.addGap(4)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_5, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE))
							.addGap(6)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(button_12, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
								.addComponent(button_13, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(94)
							.addComponent(button_10, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
							.addGap(42)
							.addComponent(button_11, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(149, Short.MAX_VALUE))
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
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(66)
							.addComponent(label_8))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(18)
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
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(35)
							.addComponent(label_12))
						.addComponent(textPane_2, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(4)
							.addComponent(label_13)
							.addGap(19)
							.addComponent(label_14))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(1)
							.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(13)
							.addComponent(textField_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(button_12)
							.addGap(11)
							.addComponent(button_13)))
					.addGap(6)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(button_10)
						.addComponent(button_11))
					.addGap(377)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(35)
							.addComponent(label_9))
						.addComponent(textPane_1, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(4)
							.addComponent(label_10)
							.addGap(19)
							.addComponent(label_11))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(1)
							.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(13)
							.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(button_8)
							.addGap(11)
							.addComponent(button_9)))
					.addGap(6)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(button_6)
						.addComponent(button_7))
					.addGap(110)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(35)
							.addComponent(label_2))
						.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(4)
							.addComponent(label_5)
							.addGap(19)
							.addComponent(label_6))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(1)
							.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(13)
							.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(button_2)
							.addGap(11)
							.addComponent(button_5)))
					.addGap(6)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(button)
						.addComponent(button_1))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
