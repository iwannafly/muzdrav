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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.log4j.chainsaw.Main;
import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.ldsThrift.DIslExistsException;
import ru.nkz.ivcgzo.ldsThrift.DIslNotFoundException;
import ru.nkz.ivcgzo.ldsThrift.DiagIsl;
import ru.nkz.ivcgzo.ldsThrift.IslExistsException;
import ru.nkz.ivcgzo.ldsThrift.LIslExistsException;
import ru.nkz.ivcgzo.ldsThrift.LIslNotFoundException;
import ru.nkz.ivcgzo.ldsThrift.LabIsl;
import ru.nkz.ivcgzo.ldsThrift.Metod;
import ru.nkz.ivcgzo.ldsThrift.ObInfIsl;
import ru.nkz.ivcgzo.ldsThrift.Patient;
import ru.nkz.ivcgzo.ldsThrift.PatientNotFoundException;
import ru.nkz.ivcgzo.ldsThrift.S_ot01;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
//import sun.text.resources.FormatData;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PIslForm {

	public JFrame frame;
	private CustomTable<Patient, Patient._Fields> tpatient;
	private CustomDateEditor tFdatap;
	private CustomDateEditor tFdatav;
	private JTextField tFnprob;
	private JTextField tFnaprotd;
	private JTextField tFdiag;
	private CustomTable<ObInfIsl, ObInfIsl._Fields> tn_ldi;
	private CustomTable<LabIsl, LabIsl._Fields> tlab_isl;
	private JTextField tFkodisl;
	private JTextField tFrez_name;
	public ThriftStringClassifierCombobox<StringClassifier> cBpcisl;
	public ThriftStringClassifierCombobox<StringClassifier> cBkodisl;
	public ThriftStringClassifierCombobox<StringClassifier> cBpcod_m;
	public ThriftStringClassifierCombobox<StringClassifier> cBLpcod_m;
	public ThriftStringClassifierCombobox<StringClassifier> cBprichina;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cBSvrach;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cBVrach;
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
	public JTabbedPane tabbedPane;
	
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
						tpatient.requestFocus();
						tpatient.setRowSelectionInterval(0, 0);
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
		
		JButton btnNewButton_8 = new JButton("Протокол");
		btnNewButton_8.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			}
		});
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(19)
					.addComponent(btnNewButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_8)
					.addContainerGap(676, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton)
						.addComponent(btnNewButton_8))
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
					
					if (tpatient.getSelectedItem() != null){
					//System.out.print(MainForm.ltc.GetObInfIslt(tpatient.getSelectedItem().npasp, MainForm.authInfo.cpodr));
					tn_ldi.setData(MainForm.ltc.GetObInfIslt(tpatient.getSelectedItem().npasp, MainForm.authInfo.cpodr));
					}else{
						tn_ldi.setData(new ArrayList<ObInfIsl>());
					}
					
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
					cBkodisl.setData(MainForm.ltc.GetKlasIsS_ot01(MainForm.authInfo.cpodr, cBpcisl.getSelectedPcod()));
					 //cBkodisl.setData(MainForm.ltc.GetKlasIsS_ot01(2000004, cBpcisl.getSelectedPcod()));
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
		
		tFdatap = new CustomDateEditor();
		tFdatap.setColumns(10);
		
		tFdatav = new CustomDateEditor();
		tFdatav.setColumns(10);
		
		JLabel lblNewLabel_3 = new JLabel("Номер пробы");
		
		tFnprob = new JTextField();
		tFnprob.setColumns(10);
		
		JLabel lblNewLabel_4 = new JLabel("Причина обращения");
		
		JLabel lblNewLabel_5 = new JLabel("Обстоятельства обращения");
		
		cBprichina = new ThriftStringClassifierCombobox<>(true);
		
		cBpopl = new ThriftIntegerClassifierCombobox<>(true);
		
		JLabel lblNewLabel_6 = new JLabel("Кем направлен");
		
		JLabel lblNewLabel_7 = new JLabel("Код направившего ЛПУ");
		
		cBnapravl = new ThriftIntegerClassifierCombobox<>(true);
		
		tFnaprotd = new JTextField();
		tFnaprotd.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent arg0) {
				
					try {
						//System.out.print("tFnaprotd.getText() = "+tFnaprotd.getText()+ ";");
						if ((tFnaprotd.getText() != null)&&(tFnaprotd.getText().length() > 0)&&(Integer.parseInt(tFnaprotd.getText()) != 0)){
							
							cBVrach.setData(MainForm.ltc.GetKlasSvrach(Integer.parseInt(tFnaprotd.getText())));
							
						}else {cBVrach.setSelectedItem(null); }
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				
			}
			
			@Override
			public void insertUpdate(DocumentEvent arg0) {
				if ((tFnaprotd.getText() != null)&&(tFnaprotd.getText() !="")&&(tFnaprotd.getText() != "0")){
					try {
						cBVrach.setData(MainForm.ltc.GetKlasSvrach(Integer.parseInt(tFnaprotd.getText())));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
			
			@Override
			public void changedUpdate(DocumentEvent arg0) {
				if ((tFnaprotd.getText() != null)&&(tFnaprotd.getText() !="")&&(tFnaprotd.getText() != "0")){
					try {
						cBVrach.setData(MainForm.ltc.GetKlasSvrach(Integer.parseInt(tFnaprotd.getText())));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});
/*		tFnaprotd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if ((tFnaprotd.getText() != null)||(tFnaprotd.getText() !="")){
					try {
						cBVrach.setData(MainForm.ltc.GetKlasSvrach(Integer.parseInt(tFnaprotd.getText())));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			}
		});*/
		tFnaprotd.setColumns(10);
		
		JButton btnnaprotd = new JButton(">>");
		btnnaprotd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (cBnapravl.getSelectedPcod() == 1){
					IntegerClassifier res = MainForm.conMan.showIntegerClassifierSelector(IntegerClassifiers.n_o00);
					
					if (res != null)
						tFnaprotd.setText(String.valueOf(res.pcod));
				}else{
					if (cBnapravl.getSelectedPcod() == 2){
						IntegerClassifier res = MainForm.conMan.showIntegerClassifierSelector(IntegerClassifiers.n_n00);
						
						if (res != null)
							tFnaprotd.setText(String.valueOf(res.pcod));
					}else{
							IntegerClassifier res = MainForm.conMan.showIntegerClassifierSelector(IntegerClassifiers.n_m00);
						
							if (res != null)
								tFnaprotd.setText(String.valueOf(res.pcod));
					}
					
				}
				
			}
		});
		
		JLabel lblNewLabel_8 = new JLabel("Ф.И.О. направившего врача");
		
		JLabel lblNewLabel_9 = new JLabel("Вид оплаты");
		
		JLabel lblNewLabel_10 = new JLabel("Код врача");
		
		cBvopl = new ThriftIntegerClassifierCombobox<>(true);
		
		JLabel lblNewLabel_14 = new JLabel("Диагноз");
		
		tFdiag = new JTextField();
		tFdiag.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if (arg0.getClickCount() == 2) {
					StringClassifier res = MainForm.conMan.showStringClassifierSelector(StringClassifiers.n_c00);
					
					if (res != null)
						tFdiag.setText(String.valueOf(res.pcod));
				}
				
			}
		});
		tFdiag.setColumns(10);
		
		JButton btnNewButton_3 = new JButton(">>");
		
		cBSvrach = new ThriftIntegerClassifierCombobox<>(true);
		
		cBVrach = new ThriftIntegerClassifierCombobox<>(true);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
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
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cBVrach, GroupLayout.PREFERRED_SIZE, 237, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_9, GroupLayout.PREFERRED_SIZE, 62, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cBvopl, GroupLayout.PREFERRED_SIZE, 220, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel_10)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cBSvrach, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
							.addGap(28)
							.addComponent(lblNewLabel_14)
							.addGap(4)
							.addComponent(tFdiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(114, Short.MAX_VALUE))
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
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(6)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_panel_1.createSequentialGroup()
									.addGap(3)
									.addComponent(lblNewLabel_8))
								.addGroup(gl_panel_1.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
										.addComponent(cBvopl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(lblNewLabel_9)))))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(9)
							.addComponent(cBVrach, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(4)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(lblNewLabel_10)
								.addComponent(cBSvrach, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(7)
							.addComponent(lblNewLabel_14))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(4)
							.addComponent(tFdiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(7))
		);
		panel_1.setLayout(gl_panel_1);
		
		
		JPanel panel2 = new JPanel();
		splitPane_2.setRightComponent(panel2);
		
		tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		
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
						cBpcod_m.setData(MainForm.ltc.GetKlasMetS_ot01(MainForm.authInfo.cpodr,cBpcisl.getSelectedPcod(), cBkodisl.getSelectedPcod()));
						//cBpcod_m.setData(MainForm.ltc.GetKlasMetS_ot01(2000004, cBpcisl.getSelectedPcod(), cBkodisl.getSelectedPcod()));
						//System.out.print(cBpcisl.getSelectedPcod() + "  " + cBkodisl.getSelectedPcod());
						
					}
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		JLabel label_1 = new JLabel("Количество");
		
		final JSpinner spkol = new JSpinner();
		
		JLabel label_3 = new JLabel("Результат");
		
		cBrez = new ThriftIntegerClassifierCombobox<>(true);
		
		JLabel label_4 = new JLabel("Стоимость");
		
		tFrez_name = new JTextField();
		tFrez_name.setColumns(10);
		
		JButton button_3 = new JButton("Добавить");
		
		JButton button_4 = new JButton("Выбрать");
		
		cBpcod_m = new ThriftStringClassifierCombobox<>(true);
		
		JLabel label_7 = new JLabel("Заключение");
		
		JLabel label_8 = new JLabel("Описание");
		
		final JTextPane tPop_name = new JTextPane();
		
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
		
		JPanel panel_7 = new JPanel();
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
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
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_4.createSequentialGroup()
									.addGap(10)
									.addComponent(label_12))
								.addGroup(gl_panel_4.createSequentialGroup()
									.addGap(93)
									.addComponent(textPane_2, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)))
							.addGap(214)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(label_13)
								.addComponent(label_14))
							.addGap(4)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
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
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_panel_4.createSequentialGroup()
									.addComponent(label)
									.addGap(4)
									.addComponent(tFkodisl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(cBkodisl, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(gl_panel_4.createSequentialGroup()
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
									.addComponent(cBpcod_m, GroupLayout.PREFERRED_SIZE, 217, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(205, Short.MAX_VALUE))
				.addComponent(panel_7, GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
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
							.addComponent(textPane_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(15)
							.addComponent(label_12))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(4)
							.addComponent(label_13)
							.addGap(19)
							.addComponent(label_14))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(1)
							.addComponent(textField_4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addGap(13)
							.addComponent(textField_5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(122)
					.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
					.addGap(293)
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
		
		JButton button_10 = new JButton("Сохранить");
		button_10.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				DiagIsl upDisl = new DiagIsl();
				
				if (cBkodisl.getSelectedPcod() != null){
					upDisl.setKodisl(cBkodisl.getSelectedPcod());
					tFkodisl.setText(cBkodisl.getSelectedPcod());
				} else{
					if ((cBkodisl.getSelectedPcod() == null)&&(tFkodisl.getText() != null)){
						
						upDisl.setKodisl(tFkodisl.getText());
						cBkodisl.setSelectedPcod(tFkodisl.getText());
					}
						
				}
				
				upDisl.setNisl(tn_ldi.getSelectedItem().nisl);
				upDisl.setOp_name(tPop_name.getText());
				upDisl.setRez_name(tFrez_name.getText());
				upDisl.setRez(cBrez.getSelectedPcod());
			
				upDisl.setKol(Integer.parseInt(spkol.getValue().toString()));
				
				upDisl.setPcod_m(cBpcod_m.getSelectedPcod());
				
				if ((cBkodisl.getSelectedPcod() != null) && (cBpcod_m.getSelectedPcod() != null)){
				
					List<Metod> Cena;
					
					try {
						Cena = MainForm.ltc.GetStoim(cBkodisl.getSelectedPcod(), cBpcod_m.getSelectedPcod(), MainForm.authInfo.cpodr);
						
						if (Cena.size() !=0){
							upDisl.setStoim(Cena.get(0).stoim);
							//System.out.print(MainForm.ltc.GetStoim(cBkodisl.getSelectedPcod(), cBpcod_m.getSelectedPcod(), 2000004));
						}
						
					} catch (TException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} 
				
				}
				
				//System.out.print(upDisl);
				try {
					
					MainForm.ltc.UpdDIsl(upDisl);
					
				} catch (DIslExistsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		JButton button_11 = new JButton("Удалить");
		button_11.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
						
				try {
					MainForm.ltc.DelDIsl(tn_ldi.getSelectedItem().nisl, tFkodisl.getText());
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		GroupLayout gl_panel_7 = new GroupLayout(panel_7);
		gl_panel_7.setHorizontalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addGap(343)
					.addComponent(button_10)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(button_11)
					.addContainerGap(279, Short.MAX_VALUE))
		);
		gl_panel_7.setVerticalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addGroup(gl_panel_7.createParallelGroup(Alignment.BASELINE)
						.addComponent(button_10)
						.addComponent(button_11))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_7.setLayout(gl_panel_7);
		panel_4.setLayout(gl_panel_4);
		layeredPane.setLayout(gl_layeredPane);
		
		JLayeredPane layeredPane_2 = new JLayeredPane();
		tabbedPane.addTab("Лаборатория", null, layeredPane_2, null);
		
		JPanel panel_2 = new JPanel();
		
		JScrollPane scrollPane_2 = new JScrollPane();
		
		JPanel panel_3 = new JPanel();
		
		JPanel panel_8 = new JPanel();
		
		JPanel panel_9 = new JPanel();
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_8, GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
				.addComponent(panel_9, GroupLayout.DEFAULT_SIZE, 784, Short.MAX_VALUE)
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addComponent(panel_8, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_9, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
					.addGap(5))
		);
		
		JButton btnNewButton_5 = new JButton("Сохранить");
		btnNewButton_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				tlab_isl.updateSelectedItem();
				
				LabIsl uplab = new LabIsl();
				
				for (int i=0; i<tlab_isl.getRowCount(); i++){
					if (tlab_isl.getData().get(i).zpok != null){
						uplab.setCpok(tlab_isl.getData().get(i).cpok);
						uplab.setZpok(tlab_isl.getData().get(i).zpok);
						uplab.setStoim(tlab_isl.getData().get(i).stoim);
						uplab.setNisl(tn_ldi.getSelectedItem().nisl);
						uplab.setNpasp(tn_ldi.getSelectedItem().npasp);
						uplab.setPcod_m(tlab_isl.getData().get(i).pcod_m);
						uplab.setStoim(tlab_isl.getData().get(i).stoim);
					
						System.out.print(uplab);
						try {
							MainForm.ltc.UpdLIsl(uplab);
						} catch (LIslExistsException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (TException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					/*if (tlab_isl.getData().get(i).zpok == null){
						
					}*/
					}else{
						
						try {
							MainForm.ltc.DelLIsl(tn_ldi.getSelectedItem().nisl, tlab_isl.getData().get(i).cpok);
						} catch (TException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}
				}

				try {
					tlab_isl.setData(MainForm.ltc.GetLabIsl(tn_ldi.getSelectedItem().nisl));
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		JButton btnNewButton_6 = new JButton("Удалить");
		btnNewButton_6.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					
					MainForm.ltc.DelLIsl(tn_ldi.getSelectedItem().nisl, tlab_isl.getSelectedItem().cpok);
					
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				try {
					
					tlab_isl.setData(MainForm.ltc.GetLabIsl(tn_ldi.getSelectedItem().nisl));
					
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		});
		
		JButton btnNewButton_7 = new JButton("Добавить");
		btnNewButton_7.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				
				List<S_ot01> dnli;
				List<Metod> dstoim;
				LabIsl nLI = new LabIsl();


				try {
					dnli = MainForm.ltc.GetS_ot01(MainForm.authInfo.cpodr, tn_ldi.getSelectedItem().pcisl);
					
					dstoim = MainForm.ltc.GetLabStoim(tn_ldi.getSelectedItem().pcisl, MainForm.authInfo.cpodr);
					boolean check;
					
					for(int i = 0; i<dnli.size(); i++){
						check = false;
						for (int j = 0; j<tlab_isl.getRowCount(); j++){
							if (dnli.get(i).pcod.equals(tlab_isl.getData().get(j).cpok)){
								check = true;
								break;
							}
						}
						
						if (check == false){
							
							nLI.setNisl(tn_ldi.getSelectedItem().nisl);
							nLI.setNpasp(tn_ldi.getSelectedItem().npasp);
							nLI.setCpok(dnli.get(i).pcod);
							nLI.setPcod_m(dnli.get(i).c_obst);
							
							for (int j = 0; j<dstoim.size(); j++){
								
								if (dnli.get(i).pcod.equals(dstoim.get(j).pcod)){
									nLI.setStoim(dstoim.get(j).stoim);
									break;
								}
								
							}
							
							try {
								
								MainForm.ltc.AddLIsl(nLI);
								
							} catch (LIslExistsException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					}
					
					tlab_isl.setData(MainForm.ltc.GetLabIsl(tn_ldi.getSelectedItem().nisl));
					
					
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}
		});
		GroupLayout gl_panel_9 = new GroupLayout(panel_9);
		gl_panel_9.setHorizontalGroup(
			gl_panel_9.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_9.createSequentialGroup()
					.addGap(205)
					.addComponent(btnNewButton_7)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_5)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_6)
					.addContainerGap(314, Short.MAX_VALUE))
		);
		gl_panel_9.setVerticalGroup(
			gl_panel_9.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_9.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_9.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnNewButton_5)
						.addComponent(btnNewButton_6)
						.addComponent(btnNewButton_7))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_9.setLayout(gl_panel_9);
		
		JLabel lblNewLabel_23 = new JLabel("Метод исследования");
		
		cBLpcod_m = new ThriftStringClassifierCombobox<>(true);
		cBLpcod_m.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
			
				
			if((cBLpcod_m.getSelectedItem() != null)&&(tlab_isl.getSelectedItem().name_m != null)&&(!tlab_isl.getSelectedItem().pcod_m.equals(cBLpcod_m.getSelectedPcod()))){
				LabIsl upLabMet = new LabIsl();
				try {
					upLabMet.setPcod_m(cBLpcod_m.getSelectedPcod());
					upLabMet.setZpok(tlab_isl.getSelectedItem().zpok);
					upLabMet.setNisl(tn_ldi.getSelectedItem().nisl);
					upLabMet.setCpok(tlab_isl.getSelectedItem().cpok);
					
						List<Metod> Cena;
						
						try {
							Cena = MainForm.ltc.GetStoim(tlab_isl.getSelectedItem().cpok, cBLpcod_m.getSelectedPcod(), MainForm.authInfo.cpodr);
							
							if (Cena.size() !=0){
								upLabMet.setStoim(Cena.get(0).stoim);
								tlab_isl.getData().get(tlab_isl.getSelectedRow()).setStoim(Cena.get(0).stoim);
							}
							
						} catch (TException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} 
					
					
					
					
					//System.out.print(upLabMet);
				
					MainForm.ltc.UpdLIsl(upLabMet);
				
					//tlab_isl.setData(MainForm.ltc.GetLabIsl(tn_ldi.getSelectedItem().nisl));
				
					tlab_isl.setValueAt(cBLpcod_m.getSelectedItem().name, tlab_isl.getSelectedRow(), 2);
				
					tlab_isl.getData().get(tlab_isl.getSelectedRow()).setPcod_m(cBLpcod_m.getSelectedPcod());
				
					tlab_isl.updateSelectedItem();
				
				
				} catch (LIslExistsException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
								
				}	
			}
		});
		GroupLayout gl_panel_8 = new GroupLayout(panel_8);
		gl_panel_8.setHorizontalGroup(
			gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_8.createSequentialGroup()
					.addGap(207)
					.addComponent(lblNewLabel_23)
					.addGap(10)
					.addComponent(cBLpcod_m, GroupLayout.PREFERRED_SIZE, 253, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(207, Short.MAX_VALUE))
		);
		gl_panel_8.setVerticalGroup(
			gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_8.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_8.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_8.createSequentialGroup()
							.addGap(3)
							.addComponent(lblNewLabel_23))
						.addComponent(cBLpcod_m, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_8.setLayout(gl_panel_8);
		panel_3.setLayout(gl_panel_3);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
				.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE))
		);
		
		tlab_isl = new CustomTable<>(true, true, LabIsl.class, 2, "Код показателя", 3, "Наименование показателя", 8, "Метод исследования", 4, "Значение");
		
		
		
		tlab_isl.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				
                try {
                    if ((cBpcisl.getSelectedItem() != null) && (tlab_isl.getSelectedItem() !=null)){
                        cBLpcod_m.setData(MainForm.ltc.GetKlasMetS_ot01(MainForm.authInfo.cpodr,cBpcisl.getSelectedPcod(), tlab_isl.getSelectedItem().cpok));
                        if (tlab_isl.getSelectedItem().pcod_m != null){
                        	cBLpcod_m.setSelectedPcod(tlab_isl.getSelectedItem().pcod_m);
                        }
                    }
                } catch (TException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
				
				
				
			}	
		});
		//tlab_isl.setDate
		
		scrollPane_2.setViewportView(tlab_isl);
		panel_2.setLayout(gl_panel_2);
		GroupLayout gl_layeredPane_2 = new GroupLayout(layeredPane_2);
		gl_layeredPane_2.setHorizontalGroup(
			gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_layeredPane_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 804, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_layeredPane_2.setVerticalGroup(
			gl_layeredPane_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_layeredPane_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(15))
		);
		layeredPane_2.setLayout(gl_layeredPane_2);
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
					.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JButton btnNewButton_1 = new JButton("Добавить");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			
			try {
				
//				ObInfIsl pisl_ld = new ObInfIsl(tpatient.getSelectedItem().npasp, ,MainForm.authInfo.cpodr, null, null, null, System.currentTimeMillis(), System.currentTimeMillis(), null, null, 2, 0, null, 2, null, MainForm.authInfo.pcod, null, null, System.currentTimeMillis());
				ObInfIsl pisl_ld = new ObInfIsl();
				pisl_ld.setNpasp(tpatient.getSelectedItem().npasp);
				//pisl_ld.setNisl(2);
				pisl_ld.setKodotd(MainForm.authInfo.cpodr);
				pisl_ld.setDatap(System.currentTimeMillis());
				pisl_ld.setDatav(System.currentTimeMillis());
				pisl_ld.setNapravl(2);
				pisl_ld.setNaprotd(0);
				pisl_ld.setVopl(2);
				pisl_ld.setDataz(System.currentTimeMillis());				
				pisl_ld.setCuser(MainForm.authInfo.pcod);
				
				//System.out.print(pisl_ld);
				
				pisl_ld.setNisl(MainForm.ltc.AddIsl(pisl_ld));
				tn_ldi.addItem(pisl_ld);
				//System.out.print(tn_ldi.getSelectedItem().nisl);
				
			} catch (IslExistsException | TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
				
			}
		});
		
		JButton btnNewButton_2 = new JButton("Сохранить");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
			try{	
				
				ObInfIsl upnisl = new ObInfIsl(/*tn_ldi.getSelectedItem().npasp, tn_ldi.getSelectedItem().nisl, tn_ldi.getSelectedItem().kodotd, "nprob", "pcisl", "cisl", "datap", "datav", "prichina", "popl", "napravl", "naprotd", "vrach", "vopl", "diag", "kodvr", "dataz", "cuser"*/);
				
				upnisl.setNisl(tn_ldi.getSelectedItem().nisl);
				
				if (tFnprob.getText() != null){
					upnisl.setNprob(Integer.parseInt(tFnprob.getText()));
				}
				
				upnisl.setPcisl(cBpcisl.getSelectedPcod());
				upnisl.setDatap(tFdatap.getDate().getTime());
				upnisl.setDatav(tFdatav.getDate().getTime());
				
				if (cBprichina.getSelectedPcod() != null){
					upnisl.setPrichina(Integer.parseInt(cBprichina.getSelectedPcod()));
				}
				
				if (cBpopl.getSelectedPcod() != null){
					upnisl.setPopl(cBpopl.getSelectedPcod());
				}
				
				if (cBnapravl.getSelectedPcod() != null){
					upnisl.setNapravl(cBnapravl.getSelectedPcod());
				}
				
				if (tFnaprotd.getText() != null){	
					upnisl.setNaprotd(Integer.parseInt(tFnaprotd.getText()));
				}
				if (cBVrach.getSelectedPcod() != null){
					upnisl.setVrach(cBVrach.getSelectedPcod());
				}else {upnisl.setVrach(0);}
				
				upnisl.setVopl(cBvopl.getSelectedPcod());
				upnisl.setDiag(tFdiag.getText());
				
				if (cBSvrach.getSelectedPcod() != null){
				upnisl.setKodvr(cBSvrach.getSelectedPcod());
				}
				
				//System.out.print(upnisl);
// , , , , , , , , , , , 				
				MainForm.ltc.UpdIsl(upnisl);
				
				
				if (tFnprob.getText() != null){
					tn_ldi.getSelectedItem().setNprob(Integer.parseInt(tFnprob.getText()));
				}
				
				tn_ldi.getSelectedItem().setPcisl(cBpcisl.getSelectedPcod());
				tn_ldi.getSelectedItem().setDatap(tFdatap.getDate().getTime());
				tn_ldi.getSelectedItem().setDatav(tFdatav.getDate().getTime());
				
				if (cBprichina.getSelectedPcod() != null){
					tn_ldi.getSelectedItem().setPrichina(Integer.parseInt(cBprichina.getSelectedPcod()));
				}
				
				if (cBpopl.getSelectedPcod() != null){
					tn_ldi.getSelectedItem().setPopl(cBpopl.getSelectedPcod());
				}
				
				if (cBnapravl.getSelectedPcod() != null){
					tn_ldi.getSelectedItem().setNapravl(cBnapravl.getSelectedPcod());
				}
				
				if (tFnaprotd.getText() != null){	
					tn_ldi.getSelectedItem().setNaprotd(Integer.parseInt(tFnaprotd.getText()));
				}
				if (cBVrach.getSelectedPcod() != null){
					tn_ldi.getSelectedItem().setVrach(cBVrach.getSelectedPcod());
				}else{tn_ldi.getSelectedItem().setVrach(0);}
				
				tn_ldi.getSelectedItem().setVopl(cBvopl.getSelectedPcod());
				tn_ldi.getSelectedItem().setDiag(tFdiag.getText());
				
				if (cBSvrach.getSelectedPcod() != null){
					tn_ldi.getSelectedItem().setKodvr(cBSvrach.getSelectedPcod());
				}				
				
				tn_ldi.getSelectedItem();
				tn_ldi.repaint();
//UPDATE p_isl_ld SET nprob = ?, pcisl = ?, datap = ?, datav = ?, prichina = ?, popl = ?, napravl = ?, naprotd = ?, vrach = ?, vopl = ?, diag = ?, kodvr = ?, WHERE nisl = ?				
			}catch (IslExistsException | TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				
				
			if (!PostPer.tip.equals("Л")){			
				//System.out.print(PostPer.tip);
				DiagIsl spDIsl;
				try {
					spDIsl = MainForm.ltc.GetDIsl(tn_ldi.getSelectedItem().nisl);
				
				
					if (spDIsl.isSetNisl() == false){
					
						DiagIsl inDisl = new DiagIsl();
					
						try {
						
							inDisl.setNpasp(tn_ldi.getSelectedItem().npasp);
							inDisl.setNisl(tn_ldi.getSelectedItem().nisl);
							inDisl.setKol(1);
							spkol.setValue(1);
						
							//System.out.print(inDisl);
						
							MainForm.ltc.AddDIsl(inDisl);
						} catch (DIslExistsException e) {
						// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
						spDIsl = MainForm.ltc.GetDIsl(tn_ldi.getSelectedItem().nisl);
				
				
					}
				
				
				} catch (DIslNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}else{
				
				
				List<LabIsl> lbIs;
				
				try {
					lbIs = MainForm.ltc.GetLabIsl(tn_ldi.getSelectedItem().nisl);
					
					if (lbIs.size() == 0){
					
					
						List<StringClassifier> sot01;
					
						sot01 = MainForm.ltc.GetKlasIsS_ot01(MainForm.authInfo.cpodr, tn_ldi.getSelectedItem().pcisl);
						
						if (sot01.size() > 0) {
							
							List<Metod> srS_ot01;
							srS_ot01 = MainForm.ltc.GetLabStoim(tn_ldi.getSelectedItem().pcisl, MainForm.authInfo.cpodr);
							
							//System.out.print("srS_ot01= "+srS_ot01);
							
							LabIsl addLbIsl = new LabIsl();
							
							for ( int i = 0; i<sot01.size(); i++){
								
								addLbIsl.setNpasp(tn_ldi.getSelectedItem().npasp);
								addLbIsl.setNisl(tn_ldi.getSelectedItem().nisl);
								addLbIsl.setCpok(sot01.get(i).pcod);
								
								for (int j = 0; j<srS_ot01.size(); j++){
									
									if (sot01.get(i).pcod.equals(srS_ot01.get(j).pcod)){
										//System.out.print(sot01.get(i).pcod+" == "+srS_ot01.get(j).pcod);
										addLbIsl.setPcod_m(srS_ot01.get(j).c_obst);
										addLbIsl.setStoim(srS_ot01.get(j).stoim);
										break;
									}
									
								}
								
								//System.out.print(addLbIsl);
								//addLbIsl.setNpasp(tn_ldi.getSelectedItem().npasp);
								try {
									
									MainForm.ltc.AddLIsl(addLbIsl);
									
								} catch (LIslExistsException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							lbIs = MainForm.ltc.GetLabIsl(tn_ldi.getSelectedItem().nisl);
							
							tlab_isl.setData(lbIs);
							
						}
					}
					
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				
			}	
			
			
			}
		});
		
		JButton btnNewButton_4 = new JButton("Удалить");
		btnNewButton_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					if (!PostPer.tip.equals("Л")){
						MainForm.ltc.DelDIslP(tn_ldi.getSelectedItem().nisl);
					}else{
						MainForm.ltc.DelLIslD(tn_ldi.getSelectedItem().nisl);
					}
					
					MainForm.ltc.DelIsl(tn_ldi.getSelectedItem().nisl);
					tn_ldi.setData(MainForm.ltc.GetObInfIslt(tpatient.getSelectedItem().npasp, MainForm.authInfo.cpodr));
					
					
				} catch (TException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//(MainForm.authInfo.cpodr, tn_ldi.getSelectedItem().pcod, n_nz1.getSelectedPcod());
				
			}
		});
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addGap(281)
					.addComponent(btnNewButton_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton_4)
					.addContainerGap(281, Short.MAX_VALUE))
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addGap(7)
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton_1, Alignment.CENTER)
						.addComponent(btnNewButton_2, Alignment.CENTER)
						.addComponent(btnNewButton_4, Alignment.CENTER))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_6.setLayout(gl_panel_6);
		
		tn_ldi = new CustomTable<>(false, true, ObInfIsl.class, 2, "Код отделения", 3, "№ пробы", 4, "Орган. и системы", 6, "Дата пост.", 7, "Дата выпол.", 8, "Причина", 9, "Обстоятельства", 10, "Направлен", 11, "Код направ. ЛПУ", 12, "ФИО направ. врача", 13, "Вид оплаты", 14, "Диагноз", 15, "Код врача", 16, "Дата за полнения");
		tn_ldi.setFillsViewportHeight(true);
		
		tn_ldi.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
			
			  if (tn_ldi.getSelectedItem() != null){	
				if (tn_ldi.getSelectedItem().pcisl != null ){
					cBpcisl.setSelectedPcod(tn_ldi.getSelectedItem().pcisl);
				} else{
					cBpcisl.setSelectedItem(null);
				}
				
				SimpleDateFormat dp = new SimpleDateFormat("dd.MM.yyyy");
				tFdatap.setText(dp.format(tn_ldi.getSelectedItem().datap));
				tFdatav.setText(dp.format(tn_ldi.getSelectedItem().datav));
				
				tFnprob.setText(String.valueOf(tn_ldi.getSelectedItem().nprob));
				if(tn_ldi.getSelectedItem().prichina != 0){
					cBprichina.setSelectedPcod(String.valueOf(tn_ldi.getSelectedItem().prichina));
				}else{
					cBprichina.setSelectedItem(null);
				}
				//cbpopl
				cBnapravl.setSelectedPcod(tn_ldi.getSelectedItem().napravl);
				
				//if (tn_ldi.getSelectedItem().naprotd != 0)
				tFnaprotd.setText(String.valueOf(tn_ldi.getSelectedItem().naprotd));
				

				if ((tFnaprotd.getText() != null)&&(tFnaprotd.getText() !="")&&(tFnaprotd.getText() != "0")){
					try {
						cBVrach.setData(MainForm.ltc.GetKlasSvrach(Integer.parseInt(tFnaprotd.getText())));
					} catch (NumberFormatException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				if (tn_ldi.getSelectedItem().vrach != 0 ){
					cBVrach.setSelectedPcod(tn_ldi.getSelectedItem().vrach);
				} else{
					cBVrach.setSelectedItem(null);
				}
				
				
				
				cBvopl.setSelectedPcod(tn_ldi.getSelectedItem().vopl);
				
				if ((tn_ldi.getSelectedItem().kodvr !=0) && (String.valueOf(tn_ldi.getSelectedItem().kodvr) != null)){
				cBSvrach.setSelectedPcod(tn_ldi.getSelectedItem().kodvr);
				} else {cBSvrach.setSelectedItem(null);}
				
				tFdiag.setText(tn_ldi.getSelectedItem().diag);
				
				
				
					//DiagIsl spDisl = new DiagIsl();
				if (!PostPer.tip.equals("Л")){	
					DiagIsl spDisl;
					try {
						spDisl = MainForm.ltc.GetDIsl(tn_ldi.getSelectedItem().nisl);

						
						if (String.valueOf(spDisl.getNpasp()) != null) {
							
							tFkodisl.setText(spDisl.getKodisl());
							
							if(spDisl.kodisl != null){ 
								cBkodisl.setSelectedPcod(spDisl.kodisl);
							}else{
								cBkodisl.setSelectedItem(null);
								}
							
							spkol.setValue(spDisl.kol);
							cBrez.setSelectedPcod(spDisl.rez);
							
							if (spDisl.pcod_m != null){
								cBpcod_m.setSelectedPcod(spDisl.pcod_m);
							}else{
								cBpcod_m.setSelectedItem(null);
							}
							
							tPop_name.setText(spDisl.op_name);
							tFrez_name.setText(spDisl.rez_name);
						}					
					
					
					
					} catch (DIslNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}else{
					try {
						
						tlab_isl.setData(MainForm.ltc.GetLabIsl(tn_ldi.getSelectedItem().nisl));
						
					} catch (TException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}	
					

				
			}	
			}
		});
		
		
		
		tn_ldi.setDateField(3);
		tn_ldi.setDateField(4);
		tn_ldi.setDateField(13);
		
		scrollPane_1.setViewportView(tn_ldi);
		panel_5.setLayout(gl_panel_5);
		panel1.setLayout(gl_panel1);
		frame.getContentPane().setLayout(groupLayout);
		
	
	}
	
	public void filtPat() {
		try {
			//tpatient.setData(MainForm.ltc.getPatDat(new SimpleDateFormat("dd.MM.yyyy").parse("14.08.2012").getTime(), 2000004));
			
				tpatient.setData(MainForm.ltc.getPatDat(System.currentTimeMillis(), MainForm.authInfo.cpodr));
			
			//tn_ldi.setData(MainForm.ltc.GetObInfIslt( tpatient.getSelectedItem().npasp, 2000004));
			if (tpatient.getSelectedItem()!= null){
			//System.out.print(MainForm.ltc.GetObInfIslt(tpatient.getSelectedItem().npasp, MainForm.authInfo.cpodr));
			tn_ldi.setData(MainForm.ltc.GetObInfIslt(tpatient.getSelectedItem().npasp, MainForm.authInfo.cpodr));
			}
			
		} catch (PatientNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
}
