package ru.nkz.ivcgzo.clientOsm;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.AbstractButton;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.SpinnerNumberModel;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Color;

import javax.swing.JOptionPane;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.thriftOsm.PatientCommonInfo;
import ru.nkz.ivcgzo.thriftOsm.RdInfStruct;
import ru.nkz.ivcgzo.thriftOsm.RdSlStruct;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;

import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;

import org.apache.thrift.TException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.UIManager;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

public class FormRdInf extends JFrame {

	private JPanel contentPane;
	private JLabel LObr;
	private JLabel LSem;
	private JTextField TFio;
	private JTextField TMrab;
	private JTextField TTelef;
	private JTextField TPhf;
    private RdInfStruct rdinf;
	private PatientCommonInfo patient;
    private int oslrod;
    private int or1;
    private int or2;
    private int or3;
    private int or4;
    private int or5;
    private int or6;
    private int or7;
    private int or8;
    private int iw1;
    private int iw2;
    private int iw3;
    private int uslj;
    private int us1;
    private int us2;
    private int us3;
    private int us4;
    private int otec;
    private int ot1;
    private int ot2;
    private int ot3;
    private int codobr;
    private String namobr;
    private int codsem;
    private String namsem;
	private int npasp;
	private ThriftStringClassifierCombobox<StringClassifier> CBGrOtec;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBObr;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBSem;
	private JTextField fam;
	private JTextField im;
	private JTextField ot;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormRdInf frame = new FormRdInf();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FormRdInf() {
		addComponentListener(new ComponentAdapter() {
			@Override
			public void componentShown(ComponentEvent arg0) {
			}
		});
		setTitle("Дополнительная информация о беременной");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		rdinf = new RdInfStruct();
		patient = new PatientCommonInfo();
		
/*		patient.setFam((String) fam.getText());
		patient.setIm((String)im.getText());
		patient.setOt((String) ot.getText());*/
		oslrod = rdinf.getOSocO();
		if ((oslrod-128)<0){
		or8=0; iw1=oslrod;	
		}else {
		or8=1; iw1=oslrod-128;	
		}
		if ((iw1-64)<0){
		or7=0; 
		}else {
		or7=1; iw1=iw1-64;	
		}
		if ((iw1-32)<0){
		or6=0; 
		}else {
		or6=1; iw1=iw1-32;	
		}
		if ((iw1-16)<0){
		or5=0; 
		}else {
		or5=1; iw1=iw1-16;	
		}
		if ((iw1-8)<0){
		or4=0; 	
		}else {
		or4=1; iw1=iw1-8;	
		}
		if ((iw1-4)<0){
		or3=0; 
		}else {
		or3=1; iw1=iw1-4;	
		}
		if ((iw1-2)<0){
		or2=0; 
		}else {
		or2=1; iw1=iw1-2;	
		}
		or1=iw1; 
		
		uslj = rdinf.getUslPr();
		if ((uslj-8)<0){
		us4=0; 	
		}else {
		us4=1; iw2=uslj-8;	
		}
		if ((iw2-4)<0){
		us3=0; 
		}else {
		us3=1; iw2=iw2-4;	
		}
		if ((iw2-2)<0){
		us2=0; 
		}else {
		us2=1; iw2=iw2-2;	
		}
		us1=iw2; 
		otec = rdinf.getVredOtec();
		if ((otec-4)<0){
		ot3=0; 
		}else {
		ot3=1; iw2=otec-4;	
		}
		if ((iw2-2)<0){
		ot2=0; 
		}else {
		ot2=1; iw2=iw2-2;	
		}
		ot1=iw2; 
		JPanel panel = new JPanel();
		contentPane.add(panel);
		
		LObr = new JLabel("Образование");
		CBObr = new ThriftIntegerClassifierCombobox<>(true);
		
		LSem = new JLabel("Семейное положение");
		CBSem = new ThriftIntegerClassifierCombobox<>(true);		
		JPanel panel_1 = new JPanel();
		
		JPanel panel_2 = new JPanel();
		
		JPanel panel_3 = new JPanel();
		
		JButton Sbutton = new JButton("");
		Sbutton.setToolTipText("Сохранить");
		Sbutton.setIcon(new ImageIcon(FormRdInf.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1341981970_Accept.png")));
		
		JButton btnNewButton = new JButton("");
		btnNewButton.setToolTipText("Новая запись");
		btnNewButton.setIcon(new ImageIcon(FormRdInf.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					RdInfStruct rdinf = new RdInfStruct();
					rdinf.setNpasp(npasp);
					MainForm.tcl.AddRdInf(rdinf);
					setRdInfData(rdinf);
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(FormRdInf.this, e1.getLocalizedMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
				} catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				}
			}

		});
		
		fam = new JTextField();
		fam.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		fam.setColumns(10);
		fam.setText(patient.getFam());
		
		im = new JTextField();
		im.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		im.setColumns(10);
		im.setText(patient.getIm());
		
		ot = new JTextField();
		ot.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		ot.setColumns(10);
		ot.setText(patient.getOt());
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addComponent(fam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(im, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(ot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(Sbutton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 361, Short.MAX_VALUE)
						.addComponent(panel_1, 0, 0, Short.MAX_VALUE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(LSem)
								.addComponent(LObr))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(CBObr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(CBSem, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(55)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE)
					.addGap(77))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel.createSequentialGroup()
									.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(fam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(im, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(ot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addGap(33)
									.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(LObr)
										.addComponent(CBObr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
										.addComponent(LSem)
										.addComponent(CBSem, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
								.addComponent(Sbutton)
								.addComponent(btnNewButton))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel lblNewLabel_1 = new JLabel("Информация об отце ребенка");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LFio = new JLabel("Фамилия, имя, отчество");
		
		JLabel LVoz = new JLabel("Возраст");
		
		JLabel LGrk = new JLabel("Группа крови");
		
		JLabel Lph = new JLabel("Резус-фактор");
		
		JLabel LMrab = new JLabel("Место работы");
		
		JLabel LTel = new JLabel("Телефон");
		
		final JCheckBox ChBSmok = new JCheckBox("Курение");
		ChBSmok.setSelected(ot1 ==1);
		
		final JCheckBox ChBAlk = new JCheckBox("Алкоголизм");
		ChBAlk.setSelected(ot2 ==1);
		
		final JCheckBox ChBNark = new JCheckBox("Наркомания");
		ChBNark.setSelected(ot3 ==1);
		
		TFio = new JTextField();
		TFio.setText(rdinf.fioOtec);
		
		JSpinner SVozr = new JSpinner();
		SVozr.setModel(new SpinnerNumberModel(new Integer(rdinf.vOtec), null, null, new Integer(1)));
		rdinf.setVOtec((int) SVozr.getModel().getValue());
		
		TMrab = new JTextField();
		TMrab.setText(rdinf.mrOtec);
		
		TTelef = new JTextField();
		TTelef.setText(rdinf.telOtec);
//		TGrk.setText(rdinf.grOtec);
		
		TPhf = new JTextField();
		TPhf.setText(rdinf.phOtec);
		
		CBGrOtec = new ThriftStringClassifierCombobox<>(true);
		
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
						.addComponent(ChBNark)
						.addComponent(lblNewLabel_1)
						.addComponent(ChBSmok)
						.addComponent(ChBAlk)
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addComponent(LFio)
								.addComponent(LVoz)
								.addComponent(LMrab)
								.addComponent(LTel)
								.addComponent(Lph))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addComponent(TFio, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING)
									.addComponent(TPhf, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
									.addComponent(TMrab, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
									.addComponent(TTelef, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING, false)
									.addComponent(CBGrOtec, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(SVozr, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))))
						.addComponent(LGrk))
					.addContainerGap(37, Short.MAX_VALUE))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(LFio)
						.addComponent(TFio, GroupLayout.PREFERRED_SIZE, 19, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(LVoz)
						.addComponent(SVozr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(LGrk)
						.addComponent(CBGrOtec, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(Lph)
						.addComponent(TPhf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(LMrab)
						.addComponent(TMrab, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(LTel)
						.addComponent(TTelef, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(ChBSmok)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ChBAlk)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ChBNark)
					.addContainerGap(61, Short.MAX_VALUE))
		);
		panel_3.setLayout(gl_panel_3);
		
		final JLabel label = new JLabel("Условия проживания");
		label.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		final JCheckBox ChBSelo = new JCheckBox("Сельская жительница");
		ChBSelo.setSelected(us1 ==1);
		
		final JCheckBox ChBOtsB = new JCheckBox("Отсутствие благоустройства");
		ChBOtsB.setSelected(us2 ==1);
		
		final JCheckBox ChBGorod = new JCheckBox("Городская жительница");
		ChBGorod.setSelected(us3 ==1);
		
		final JCheckBox ChBBomg = new JCheckBox("БОМЖ");
		ChBBomg.setSelected(us4 ==1);
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addComponent(ChBBomg)
						.addComponent(ChBGorod)
						.addComponent(ChBOtsB)
						.addComponent(ChBSelo)
						.addComponent(label))
					.addContainerGap(82, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ChBSelo)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ChBOtsB)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ChBGorod)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(ChBBomg))
		);
		panel_2.setLayout(gl_panel_2);
		
		JLabel lblNewLabel = new JLabel("Отягощающие социальные обязательства");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
/*		final JCheckBox CBKrov = new JCheckBox("Кровотечение");
		CBKrov.setSelected(or1 == 1);
*/		
		final JCheckBox ChBAss = new JCheckBox("Асоциальная личность");
		ChBAss.setSelected(or1 ==1);
		
		final JCheckBox ChBots = new JCheckBox("Очаг туберкулеза в семье");
		ChBots.setSelected(or2 ==1);
		
		final JCheckBox ChBInv = new JCheckBox("Инвалидность");
		ChBInv.setSelected(or3 ==1);
		
		final JCheckBox ChBMnd = new JCheckBox("Многодетная семья");
		ChBMnd.setSelected(or4 ==1);
		
		final JCheckBox ChBLrp = new JCheckBox("Лишение родительских прав");
		ChBLrp.setSelected(or5 ==1);
		
		final JCheckBox ChBCnd = new JCheckBox("Семья с низким достатком");
		ChBCnd.setSelected(or6 ==1);
		
		final JCheckBox ChBNer = new JCheckBox("Неработающие");
		ChBNer.setSelected(or7 ==1);
		
		final JCheckBox ChBNmls = new JCheckBox("Нахождение в местах лишения свободы");
		ChBNmls.setSelected(or8 ==1);

		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(ChBNmls)
						.addComponent(ChBNer)
						.addComponent(ChBInv)
						.addComponent(ChBots)
						.addComponent(lblNewLabel)
						.addComponent(ChBAss)
						.addComponent(ChBLrp)
						.addComponent(ChBMnd)
						.addComponent(ChBCnd))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(ChBAss)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(ChBots)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ChBInv)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ChBMnd)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(ChBLrp)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ChBCnd)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ChBNer)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ChBNmls)
					.addContainerGap(125, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		Sbutton.addActionListener(new ActionListener() {
/*			private int codobr (int codobr){
				if (CBObr.getSelectedItem().equals("Высшее")){codobr = 1;}
				if (CBObr.getSelectedItem().equals("Незаконченное высшее")){codobr = 2;}
				if (CBObr.getSelectedItem().equals("Среднее специальное")){codobr = 3;}
				if (CBObr.getSelectedItem().equals("Среднее")){codobr = 4;}
				if (CBObr.getSelectedItem().equals("Начальное")){codobr = 5;}
				if (CBObr.getSelectedItem().equals("")){codobr = 0;}
							return codobr;
			};
 		    private int codsem (int codsem){
				if (CBObr.getSelectedItem().equals("Регистрированный")){codsem = 1;}
				if (CBObr.getSelectedItem().equals("Не регистрированный")){codsem = 2;}
				if (CBObr.getSelectedItem().equals("Одна")){codsem = 3;}
				if (CBObr.getSelectedItem().equals("")){codsem = 0;}
							return codsem;
			};*/
			private int oslrod (int oslrod){
		           if (ChBAss.isSelected()){oslrod=oslrod+1;}
		            if (ChBots.isSelected()){oslrod=oslrod+2;}
		            if (ChBInv.isSelected()){oslrod=oslrod+4;}
		            if (ChBMnd.isSelected()){oslrod=oslrod+8;}
		            if (ChBLrp.isSelected()){oslrod=oslrod+16;}
		            if (ChBCnd.isSelected()){oslrod=oslrod+32;}
		            if (ChBNer.isSelected()){oslrod=oslrod+64;}
		            if (ChBNmls.isSelected()){oslrod=oslrod+128;}
			return oslrod;	
			};
			private int uslj (int uslj){
		           if (ChBSelo.isSelected()){uslj=uslj+1;}
		            if (ChBots.isSelected()){uslj=uslj+2;}
		            if (ChBGorod.isSelected()){uslj=uslj+4;}
		            if (ChBBomg.isSelected()){uslj=uslj+8;}
			return uslj;
			};
			private int otec (int otec){
		           if (ChBSmok.isSelected()){otec=otec+1;}
		            if (ChBAlk.isSelected()){otec=otec+2;}
		            if (ChBNark.isSelected()){otec=otec+4;}
			return otec;	
			};
			public void actionPerformed(ActionEvent arg0) {
rdinf.setFioOtec(TFio.getText());
rdinf.setMrOtec(TMrab.getText());
//rdinf.setObr(CBObr.setData(MainForm.tcl.getn_z00()));
//rdinf.setSem(CBSem.setData(MainForm.tcl.getn_z11()));
rdinf.setOSocO(oslrod);
rdinf.setUslPr(uslj);
rdinf.setVredOtec(otec);
rdinf.setTelOtec(TTelef.getText());
//rdinf.setGrOtec(CBGrOtec.setData(MainForm.tcl.getn_R0z()));//классификатор
rdinf.setPhOtec(TPhf.getText());
			}
		});
	}

	protected void setRdInfData(RdInfStruct rdinf2) {
		// TODO Auto-generated method stub
		
	}
}
