package ru.nkz.ivcgzo.clientOsm;

import java.awt.Font;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.EmptyBorder;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.RdInfStruct;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JEditorPane;

public class FormRdInf extends JFrame {
	private static final long serialVersionUID = 8843590132227415590L;
	private RdInfStruct RdInfStruct;
	private JPanel contentPane;
	private JLabel LObr;
	private JLabel LSem;
	private JTextField TFio;
	private JTextField TMrab;
	private JTextField TTelef;
	private JTextField TPhf;
	private JSpinner SVozr;
    private RdInfStruct rdinf;
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
	private ThriftStringClassifierCombobox<StringClassifier> CBGrOtec;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBObr;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> CBSem;
	private JTextField fam;
	private JTextField im;
	private JTextField ot;
	private JCheckBox ChBNark;
	private JCheckBox ChBAlk;
	private JCheckBox ChBSmok;
	private JCheckBox ChBNmls;
	private JCheckBox ChBNer;
	private JCheckBox ChBInv;
	private JCheckBox ChBBots;
	private JCheckBox ChBAss;
	private JCheckBox ChBLrp;
	private JCheckBox ChBMnd;
	private JCheckBox ChBCnd;
	private JCheckBox ChBBomg;
	private JCheckBox ChBGorod;
	private JCheckBox ChBOtsb;
	private JCheckBox ChBSelo;
	private JCheckBox ChBots;
	private JEditorPane TSostOtec;
	/**
	 * Create the frame.
	 */
	public FormRdInf() {
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setAutoRequestFocus(false);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
			try {
				rdinf = MainForm.tcl.getRdInfInfo(Vvod.zapVr.npasp);
				setRdInfData();
				fam.setText(Vvod.zapVr.getFam());
				im.setText(Vvod.zapVr.getIm());
				ot.setText(Vvod.zapVr.getOth());
} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			}
		});
		setTitle("Дополнительная информация о беременной");
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 850, 520);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(1, 0, 0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		
		LObr = new JLabel("Образование");
		LObr.setFont(new Font("Tahoma", Font.PLAIN, 12));
		CBObr = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_z00);
		CBObr.setFont(new Font("Tahoma", Font.BOLD, 12));
	
		LSem = new JLabel("Семейное положение");
		LSem.setFont(new Font("Tahoma", Font.PLAIN, 12));
		CBSem = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_z11);		
		CBSem.setFont(new Font("Tahoma", Font.BOLD, 12));
	
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(new Color(0, 0, 0)));
			JButton Sbutton = new JButton("");
		Sbutton.setToolTipText("Сохранить");
		Sbutton.setIcon(new ImageIcon(FormRdInf.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1341981970_Accept.png")));
/*		btnNewButton.setIcon(new ImageIcon(FormRdInf.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					RdInfStruct rdinf = new RdInfStruct();
					rdinf.setNpasp(Vvod.zapVr.getNpasp());
					rdinf.setDataz(System.currentTimeMillis());
					System.out.println(rdinf);		
		            MainForm.tcl.AddRdInf(rdinf);
			} catch (KmiacServerException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(FormRdInf.this, e1.getLocalizedMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
				} catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				}
			}

		});*/
		
		fam = new JTextField();
		fam.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		fam.setColumns(10);
//		fam.setText(Vvod.zapVr.getFam());
		
		im = new JTextField();
		im.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		im.setColumns(10);
//		im.setText(Vvod.zapVr.getIm());
		
		ot = new JTextField();
		ot.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 13));
		ot.setColumns(10);
//		ot.setText(Vvod.zapVr.getOth());
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 375, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel.createSequentialGroup()
								.addComponent(fam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(im, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(ot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(38))
							.addGroup(gl_panel.createSequentialGroup()
								.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
									.addGroup(gl_panel.createSequentialGroup()
										.addGap(1)
										.addComponent(LObr, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
										.addComponent(CBObr, GroupLayout.PREFERRED_SIZE, 200, GroupLayout.PREFERRED_SIZE))
									.addGroup(gl_panel.createSequentialGroup()
										.addComponent(LSem)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(CBSem, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE))
									.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
								.addGap(86))))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(59)
							.addComponent(Sbutton, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
						.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 328, GroupLayout.PREFERRED_SIZE))
					.addGap(530))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
							.addComponent(fam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(im, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(ot, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(Sbutton, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 37, Short.MAX_VALUE)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LObr)
								.addComponent(CBObr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(10)
							.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
								.addComponent(LSem)
								.addComponent(CBSem, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 228, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 387, GroupLayout.PREFERRED_SIZE)
							.addGap(15))))
		);
		
		JLabel lblNewLabel_1 = new JLabel("Информация об отце ребенка");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel LFio = new JLabel("Фамилия, имя, отчество");
		LFio.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LVoz = new JLabel("Возраст");
		LVoz.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LGrk = new JLabel("Группа крови");
		LGrk.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel Lph = new JLabel("Резус-фактор");
		Lph.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LMrab = new JLabel("Место работы");
		LMrab.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel LTel = new JLabel("Телефон");
		LTel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		ChBSmok = new JCheckBox("Курение");
		ChBSmok.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		ChBAlk = new JCheckBox("Алкоголизм");
		ChBAlk.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		ChBNark = new JCheckBox("Наркомания");
		ChBNark.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		TFio = new JTextField();
		TFio.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		SVozr = new JSpinner();
		SVozr.setFont(new Font("Tahoma", Font.BOLD, 12));
		SVozr.setModel(new SpinnerNumberModel(0, 0, 80, 1));
		
		TMrab = new JTextField();
		TMrab.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		TTelef = new JTextField();
		TTelef.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		TPhf = new JTextField();
		TPhf.setFont(new Font("Tahoma", Font.BOLD, 13));
		
		CBGrOtec = new ThriftStringClassifierCombobox<>(StringClassifiers.n_r0z);
		CBGrOtec.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		JLabel lblNewLabel_2 = new JLabel("Состояние здоровья");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		
		TSostOtec = new JEditorPane();
		TSostOtec.setForeground(new Color(0, 0, 0));
		TSostOtec.setBackground(Color.WHITE);
		TSostOtec.setFont(new Font("Tahoma", Font.BOLD, 12));

		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
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
										.addComponent(TTelef, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
										.addComponent(TFio, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
										.addComponent(TPhf, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
										.addComponent(TMrab, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_panel_3.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(CBGrOtec, Alignment.LEADING, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(SVozr, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 49, GroupLayout.PREFERRED_SIZE))))
								.addComponent(LGrk)))
						.addGroup(gl_panel_3.createSequentialGroup()
							.addGap(14)
							.addGroup(gl_panel_3.createParallelGroup(Alignment.LEADING)
								.addComponent(TSostOtec, GroupLayout.PREFERRED_SIZE, 286, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblNewLabel_2))))
					.addContainerGap(26, Short.MAX_VALUE))
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
					.addGap(10)
					.addComponent(lblNewLabel_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(TSostOtec, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel_3.setLayout(gl_panel_3);
		
		JLabel label = new JLabel("Условия проживания");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		ChBSelo = new JCheckBox("Сельская жительница");
		ChBSelo.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		ChBOtsb = new JCheckBox("Отсутствие благоустройства");
		ChBOtsb.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		ChBGorod = new JCheckBox("Городская жительница");
		ChBGorod.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		ChBBomg = new JCheckBox("БОМЖ");
		ChBBomg.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addContainerGap()
									.addComponent(ChBOtsb))
								.addGroup(gl_panel_2.createSequentialGroup()
									.addGap(5)
									.addComponent(ChBSelo)))
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addGap(16)
									.addComponent(ChBBomg))
								.addGroup(gl_panel_2.createSequentialGroup()
									.addGap(14)
									.addComponent(ChBGorod))))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(108)
							.addComponent(label)))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap(12, Short.MAX_VALUE)
					.addComponent(label)
					.addGap(7)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
							.addComponent(ChBSelo)
							.addGap(30))
						.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
							.addComponent(ChBGorod)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
									.addComponent(ChBOtsb)
									.addGap(5))
								.addGroup(Alignment.TRAILING, gl_panel_2.createSequentialGroup()
									.addComponent(ChBBomg)
									.addGap(9))))))
		);
		panel_2.setLayout(gl_panel_2);
		
		JLabel lblNewLabel = new JLabel("Отягощающие социальные обязательства");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		ChBAss = new JCheckBox("Асоциальная личность");
		ChBAss.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		ChBots = new JCheckBox("Очаг туберкулеза в семье");
		ChBots.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		ChBInv = new JCheckBox("Инвалидность");
		ChBInv.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		ChBMnd = new JCheckBox("Многодетная семья");
		ChBMnd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		ChBLrp = new JCheckBox("Лишение родительских прав");
		ChBLrp.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		ChBCnd = new JCheckBox("Семья с низким достатком");
		ChBCnd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		ChBNer = new JCheckBox("Неработающие");
		ChBNer.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		ChBNmls = new JCheckBox("Нахождение в местах лишения свободы");
		ChBNmls.setFont(new Font("Tahoma", Font.PLAIN, 12));

		Sbutton.addActionListener(new ActionListener() {
		private void calcoslrod (){
			oslrod = 0;
				if (ChBAss.isSelected()){oslrod=oslrod+1;}
		            if (ChBots.isSelected()){oslrod=oslrod+2;}
		            if (ChBInv.isSelected()){oslrod=oslrod+4;}
		            if (ChBMnd.isSelected()){oslrod=oslrod+8;}
		            if (ChBLrp.isSelected()){oslrod=oslrod+16;}
		            if (ChBCnd.isSelected()){oslrod=oslrod+32;}
		            if (ChBNer.isSelected()){oslrod=oslrod+64;}
		            if (ChBNmls.isSelected()){oslrod=oslrod+128;}
			};
			private void calcuslj (){
				uslj = 0;
		            if (ChBSelo.isSelected())uslj=uslj+1;
		            if (ChBOtsb.isSelected()){uslj=uslj+2;}
		            if (ChBGorod.isSelected()){uslj=uslj+4;}
		            if (ChBBomg.isSelected()){uslj=uslj+8;}
		            System.out.println(uslj);		
			};
			private void calcotec (){
				 otec = 0;
				if (ChBSmok.isSelected())
					otec=otec+1;
				if (ChBAlk.isSelected())
					otec=otec+2;
				if (ChBNark.isSelected())
					otec=otec+4;
				};
			public void actionPerformed(ActionEvent arg0) {
				try {
					System.out.println("Запись");		
rdinf.setFioOtec(TFio.getText());
rdinf.setMrOtec(TMrab.getText());
calcoslrod();
rdinf.setOsoco(oslrod);
calcuslj();
rdinf.setUslpr(uslj);
calcotec();
rdinf.setVredOtec(otec);
rdinf.setTelOtec(TTelef.getText());
rdinf.setPhOtec(TPhf.getText());
rdinf.setVOtec((int) (SVozr.getModel().getValue()));
rdinf.setDataz(System.currentTimeMillis());
rdinf.setZotec(TSostOtec.getText());
if (CBObr.getSelectedPcod() != null)
	rdinf.setObr(CBObr.getSelectedPcod());
	else rdinf.unsetObr();
if (CBSem.getSelectedPcod() != null)
	rdinf.setSem(CBSem.getSelectedPcod());
	else rdinf.unsetSem();
if (CBGrOtec.getSelectedPcod() != null)
	rdinf.setGrotec(CBGrOtec.getSelectedPcod());
	else rdinf.unsetGrotec();
System.out.println(rdinf);		
	MainForm.tcl.UpdateRdInf(rdinf);
} catch (TException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
			}
		});
		
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
	}

	private void method1() {
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
		
	if ((uslj-8)<0){
		us4=0; iw2=uslj;	
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
		if ((otec-4)<0){
		ot3=0; iw2=otec;
		}else {
		ot3=1; iw2=otec-4;	
		}
		if ((iw2-2)<0){
		ot2=0; 
		}else {
		ot2=1; iw2=iw2-2;	
		}
		ot1=iw2;
}

	protected void setRdInfData() throws KmiacServerException, TException {
		// TODO Auto-generated method stub
	try {
		TFio.setText(rdinf.getFioOtec());
		TMrab.setText(rdinf.getMrOtec());
		TTelef.setText(rdinf.getTelOtec());
		TPhf.setText(rdinf.getPhOtec());
		TSostOtec.setText(rdinf.getZotec());
		if (rdinf.isSetGrotec())
			CBGrOtec.setSelectedPcod(rdinf.getGrotec());
		else CBGrOtec.setSelectedItem(null);
		if (rdinf.isSetObr())
			CBObr.setSelectedPcod(rdinf.getObr());
		else CBObr.setSelectedItem(null);
		if (rdinf.isSetSem())
			CBSem.setSelectedPcod(rdinf.getSem());
		else CBSem.setSelectedItem(null);
		oslrod = rdinf.getOsoco();
		uslj = rdinf.getUslpr();
		otec = rdinf.getVredOtec();
		method1();
		ChBAss.setSelected(or1 == 1);
		ChBots.setSelected(or2 == 1);
		ChBInv.setSelected(or3 == 1);
		ChBMnd.setSelected(or4 == 1);
		ChBLrp.setSelected(or5 == 1);
		ChBCnd.setSelected(or6 == 1);
		ChBNer.setSelected(or7 == 1);
		ChBNmls.setSelected(or8 == 1);
		ChBSelo.setSelected(us1 == 1);
		ChBOtsb.setSelected(us2 == 1);
	    ChBGorod.setSelected(us3 == 1);
		ChBBomg.setSelected(us4 == 1);
	    ChBSmok.setSelected(ot1 == 1);
		ChBAlk.setSelected(ot2 == 1);
		ChBNark.setSelected(ot3 == 1);
			SVozr.setValue(rdinf.getVOtec());
		} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		
	}
	public void onConnect() throws PatientNotFoundException {
		fam.setText(Vvod.zapVr.fam);
		im.setText(Vvod.zapVr.im);
		ot.setText(Vvod.zapVr.oth);
	}
}
