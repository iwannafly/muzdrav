package ru.nkz.ivcgzo.ldsclient;

import javax.sound.midi.SysexMessage;
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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.ldsThrift.ObInfIsl;
import ru.nkz.ivcgzo.ldsThrift.Patient;
import ru.nkz.ivcgzo.ldsThrift.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
//import sun.text.resources.FormatData;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

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
	private CustomTable<ObInfIsl, ObInfIsl._Fields> tn_ldi;
	private JTable table_1;
	private JTextField tFkodisl;
	private JTextField tFrez_name;
	public ThriftStringClassifierCombobox<StringClassifier> cBpcisl;
	public ThriftStringClassifierCombobox<StringClassifier> cBkodisl;
	public ThriftStringClassifierCombobox<StringClassifier> cBpcod_m;
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
	private JTable table;
	
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
		frame.setBounds(100, 100, 861, 849);
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
					.addComponent(splitPane, GroupLayout.PREFERRED_SIZE, 755, Short.MAX_VALUE))
		);
		
		JButton btnNewButton = new JButton("Поиск");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int[] npasp = MainForm.conMan.showPatientSearchForm("Поиск пациента", true, false);
				
				if (npasp != null){
					try {
						tpatient.setData(MainForm.ltc.getPatient(Arrays.toString(npasp).replace(']', ')').replace('[', '(')));
					} catch (PatientNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} 
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(19)
					.addComponent(btnNewButton)
					.addContainerGap(705, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNewButton)
					.addContainerGap(20, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		tpatient = new CustomTable<>(false, true, Patient.class, 0, "Код", 1, "Фамилия", 2, "Имя", 3, "Отчество", 4, "Дата рождения");
		
		tpatient.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
			
				try {
					tn_ldi.setData(MainForm.ltc.GetObInfIslt(tpatient.getSelectedItem().npasp, MainForm.authInfo.cpodr));
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		
		tpatient.setDateField(4);
		scrollPane.setViewportView(tpatient);

		
		
		JPanel panel1 = new JPanel();
		splitPane.setRightComponent(panel1);		
		
		
		JSplitPane splitPane_1 = new JSplitPane();
		splitPane_1.setOrientation(JSplitPane.VERTICAL_SPLIT);
		
		JSplitPane splitPane_2 = new JSplitPane();
		splitPane_2.setOrientation(JSplitPane.VERTICAL_SPLIT);
		splitPane_1.setRightComponent(splitPane_2);
		
		JPanel panel_1 = new JPanel();
		splitPane_2.setLeftComponent(panel_1);
		
		JLabel lblNewLabel = new JLabel("Органы и системы");
		
		cBpcisl = new ThriftStringClassifierCombobox<>(true);
		cBpcisl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (cBpcisl.getSelectedItem() != null){
				 try {
					//cBkodisl.setData(MainForm.ltc.GetKlasIsS_ot01(MainForm.authInfo.cpodr, cBpcisl.getSelectedPcod()));
					 cBkodisl.setData(MainForm.ltc.GetKlasIsS_ot01(2000004, cBpcisl.getSelectedPcod()));
					// System.out.print(cBpcisl.getSelectedPcod());
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
				
			}
		});
	
		
		
		
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
		
		
		JPanel panel2 = new JPanel();
		splitPane_2.setRightComponent(panel2);
		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
		/*JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		splitPane_2.setRightComponent(tabbedPane);*/
		
		JLayeredPane layeredPane = new JLayeredPane();
		tabbedPane.addTab("Диагностика", null, layeredPane, null);
		
		JPanel panel_4 = new JPanel();
		GroupLayout gl_layeredPane = new GroupLayout(layeredPane);
		gl_layeredPane.setHorizontalGroup(
			gl_layeredPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
		);
		gl_layeredPane.setVerticalGroup(
			gl_layeredPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_layeredPane.createSequentialGroup()
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 494, Short.MAX_VALUE)
					.addGap(2))
		);
		
		JButton brez_name = new JButton(">>");
		
		JLabel label = new JLabel("Исследование");
		
		tFkodisl = new JTextField();
		tFkodisl.setColumns(10);
		
		cBkodisl = new ThriftStringClassifierCombobox<>(true);
		cBkodisl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					if ((cBpcisl.getSelectedItem() != null) && (cBkodisl.getSelectedItem() != null)){
						//cBpcod_m.setData(MainForm.ltc.GetKlasMetS_ot01(MainForm.authInfo.cpodr,cBpcisl.getSelectedPcod(), cBkodisl.getSelectedPcod()));
						cBpcod_m.setData(MainForm.ltc.GetKlasMetS_ot01(2000004, cBpcisl.getSelectedPcod(), cBkodisl.getSelectedPcod()));
						//System.out.print(cBpcisl.getSelectedPcod() + "  " + cBkodisl.getSelectedPcod());
						
					}
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		JLabel label_1 = new JLabel("Количество");
		
		JSpinner spkol = new JSpinner();
		
		JLabel label_3 = new JLabel("Результат");
		
		cBrez = new ThriftIntegerClassifierCombobox<>(true);
		
		JLabel label_4 = new JLabel("Стоимость");
		
		tFrez_name = new JTextField();
		tFrez_name.setColumns(10);
		
		JButton button_3 = new JButton("Добавить");
		
		JButton button_4 = new JButton("Выбрать");
		
		cBpcod_m = new ThriftStringClassifierCombobox<>(true);
		
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
		textField_4.setVisible(false);
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
					.addGap(10)
					.addComponent(label_8)
					.addGap(4)
					.addComponent(tPop_name, GroupLayout.PREFERRED_SIZE, 506, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(104)
					.addComponent(button_3)
					.addGap(41)
					.addComponent(button_4))
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(10)
					.addComponent(label_7)
					.addGap(18)
					.addComponent(tFrez_name, GroupLayout.PREFERRED_SIZE, 415, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addComponent(brez_name, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(10)
					.addComponent(label_12)
					.addGap(41)
					.addComponent(textPane_2, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(label_13)
						.addComponent(label_14))
					.addGap(4)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(button_12, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_13, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)))
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(104)
					.addComponent(button_10)
					.addGap(42)
					.addComponent(button_11))
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(10)
					.addComponent(label_9)
					.addGap(41)
					.addComponent(textPane_1, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(label_10)
						.addComponent(label_11))
					.addGap(4)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(textField_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(button_8, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_9, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)))
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(104)
					.addComponent(button_6)
					.addGap(42)
					.addComponent(button_7))
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(10)
					.addComponent(label_2)
					.addGap(41)
					.addComponent(textPane, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
					.addGap(6)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(label_5)
						.addComponent(label_6))
					.addGap(4)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(button_2, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)
						.addComponent(button_5, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE)))
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(104)
					.addComponent(button)
					.addGap(42)
					.addComponent(button_1))
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addComponent(label)
							.addGap(4)
							.addComponent(tFkodisl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cBkodisl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_panel_4.createSequentialGroup()
							.addComponent(label_1)
							.addGap(4)
							.addComponent(spkol, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(label_3)
							.addGap(4)
							.addComponent(cBrez, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(label_4)
							.addGap(6)
							.addComponent(cBpcod_m, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE))))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(11)
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
					.addGap(18)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(48)
							.addComponent(label_8))
						.addComponent(tPop_name, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(button_3)
						.addComponent(button_4))
					.addGap(10)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(4)
							.addComponent(label_7))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(1)
							.addComponent(tFrez_name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(brez_name))
					.addGap(6)
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
						.addComponent(button_1)))
		);
		panel_4.setLayout(gl_panel_4);
		layeredPane.setLayout(gl_layeredPane);
		
		JLayeredPane layeredPane_2 = new JLayeredPane();
		tabbedPane.addTab("Лаборатория", null, layeredPane_2, null);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 804, 461);
		layeredPane_2.add(panel_2);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		JPanel panel_3 = new JPanel();
		
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
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
				.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE))
		);
		
		table_1 = new JTable();
		scrollPane_2.setViewportView(table_1);
		panel_2.setLayout(gl_panel_2);
		GroupLayout gl_panel2 = new GroupLayout(panel2);
		gl_panel2.setHorizontalGroup(
			gl_panel2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel2.createSequentialGroup()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel2.setVerticalGroup(
			gl_panel2.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.PREFERRED_SIZE, 515, Short.MAX_VALUE)
		);
		panel2.setLayout(gl_panel2);
		GroupLayout gl_panel1 = new GroupLayout(panel1);
		gl_panel1.setHorizontalGroup(
			gl_panel1.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane_1)
		);
		gl_panel1.setVerticalGroup(
			gl_panel1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel1.createSequentialGroup()
					.addGap(5)
					.addComponent(splitPane_1, GroupLayout.DEFAULT_SIZE, 748, Short.MAX_VALUE))
		);
		
		JPanel panel_5 = new JPanel();
		splitPane_1.setLeftComponent(panel_5);
		
		JPanel panel_6 = new JPanel();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.TRAILING)
				.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE)
				.addComponent(panel_6, GroupLayout.DEFAULT_SIZE, 821, Short.MAX_VALUE)
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 23, GroupLayout.PREFERRED_SIZE))
		);
		
		JButton btnNewButton_1 = new JButton("Добавить");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				
			}
		});
		
		JButton btnNewButton_2 = new JButton("Сохранить");
		
		JButton btnNewButton_4 = new JButton("Удалить");
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnNewButton_1)
					.addPreferredGap(ComponentPlacement.RELATED, 315, Short.MAX_VALUE)
					.addComponent(btnNewButton_2)
					.addGap(239)
					.addComponent(btnNewButton_4)
					.addContainerGap())
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton_1)
						.addComponent(btnNewButton_4)
						.addComponent(btnNewButton_2))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_6.setLayout(gl_panel_6);
		
		tn_ldi = new CustomTable<>(false, true, ObInfIsl.class, 2, "Код отделения", 3, "№ пробы", 4, "Орган. и системы", 5, "Исследование", 6, "Дата пост.", 7, "Дата выпол.", 8, "Причина", 9, "Обстоятельства", 10, "Направлен", 11, "Код направ. ЛПУ", 12, "ФИО направ. врача", 13, "Вид оплаты", 14, "Диагноз", 15, "Код врача", 16,"Код медсес.", 17, "Код санит.", 18, "Дата за полнения");
		tn_ldi.setFillsViewportHeight(true);
		scrollPane_1.setViewportView(tn_ldi);
		panel_5.setLayout(gl_panel_5);
		panel1.setLayout(gl_panel1);
		frame.getContentPane().setLayout(groupLayout);
		
	
	}
	
	public void filtPat() {
		try {
			tpatient.setData(MainForm.ltc.getPatDat(new SimpleDateFormat("dd.MM.yyyy").parse("14.08.2012").getTime(), 2000004));
			
		//			tpatient.setData(MainForm.ltc.getPatDat(System.currentTimeMillis(), MainForm.authInfo.cpodr));
			
			tn_ldi.setData(MainForm.ltc.GetObInfIslt( tpatient.getSelectedItem().npasp, 2000004));
		//	table.setData(MainForm.ltc.GetObInfIslt(tpatient.getSelectedItem().npasp, MainForm.authInfo.cpodr));
			
		} catch (PatientNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
