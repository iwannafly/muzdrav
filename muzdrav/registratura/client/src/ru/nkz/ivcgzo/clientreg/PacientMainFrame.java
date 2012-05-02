package ru.nkz.ivcgzo.clientreg;

import java.awt.EventQueue;
import org.apache.thrift.TException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import ru.nkz.ivcgzo.thriftreg.*;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;

public class PacientMainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static PacientMainFrame instance;
	private JPanel contentPane;
	private JTextField tfFam;
	private JTextField tfIm;
	private JTextField tfOt;
	private JTextField tfDr;
	private JTextField tfSer;
	private JTextField tfNom;
	private PacientInfoFrame pacientInfoFrame;
	public List<PatientAllStruct> pat;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					PacientMainFrame frame = new PacientMainFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public static PacientMainFrame getInstance() {
		return instance;
	}
	/**
	 * Create the frame.
	 */
	public PacientMainFrame() {
		setTitle("Поиск пациента");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 264, 279);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Критерии поиска пациента", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_1 = new JPanel();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
						.addComponent(panel, GroupLayout.PREFERRED_SIZE, 248, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 41, Short.MAX_VALUE))
		);
		
		JButton btnOtkaz = new JButton("Отказ");
		btnOtkaz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				dispose();
			}
		});
		
		JButton btnPoisk = new JButton("Поиск");
		btnPoisk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					instance = PacientMainFrame.this;
					//pat = new ArrayList<PatientAllStruct>();
					pat = MainForm.tcl.getAllPatientInfo(new PatientInfoStruct(
							tfFam.getText().trim(),
							tfIm.getText().trim(),tfOt.getText().trim(),
							new Long(0),tfSer.getText().trim(),
							tfNom.getText().trim()));
					if (pat.isEmpty()){
						System.out.println(tfFam.getText().trim());
						System.out.println("По заданным критериям сведения о пациенте отсутствуют.");
					}
					else {
						//System.out.println(pat.get(0).fam);
						dispose();
						if (pacientInfoFrame == null) {
							pacientInfoFrame = new PacientInfoFrame(pat);
							pacientInfoFrame.pack();
						} else
							pacientInfoFrame.refresh(pat);
							pacientInfoFrame.setVisible(true);
							pacientInfoFrame.setSize(954, 672); //ширина, высота
					}
				//} catch (KmiacServerException | TException e) {
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
					.addGap(26)
					.addComponent(btnOtkaz)
					.addPreferredGap(ComponentPlacement.RELATED, 66, Short.MAX_VALUE)
					.addComponent(btnPoisk)
					.addGap(30))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_1.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnOtkaz)
						.addComponent(btnPoisk))
					.addContainerGap())
		);
		panel_1.setLayout(gl_panel_1);
		
		tfFam = new JTextField();
		tfFam.setColumns(10);
		
		tfIm = new JTextField();
		tfIm.setColumns(10);
		
		tfOt = new JTextField();
		tfOt.setColumns(10);
		
		tfDr = new JTextField();
		tfDr.setColumns(10);
		
		tfSer = new JTextField();
		tfSer.setColumns(10);
		
		tfNom = new JTextField();
		tfNom.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Фамилия");
		
		JLabel lblNewLabel_1 = new JLabel("Имя");
		
		JLabel lblNewLabel_2 = new JLabel("Отчество");
		
		JLabel lblNewLabel_3 = new JLabel("Дата рождения");
		
		JLabel lblNewLabel_4 = new JLabel("Полис ОМС:  Серия");
		
		JLabel lblNewLabel_5 = new JLabel("Номер");
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addContainerGap(10, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_1)
						.addComponent(lblNewLabel_2)
						.addComponent(lblNewLabel_3)
						.addComponent(lblNewLabel_4)
						.addComponent(lblNewLabel_5))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(tfFam, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
						.addComponent(tfIm)
						.addComponent(tfOt)
						.addComponent(tfDr)
						.addComponent(tfSer)
						.addComponent(tfNom))
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel)
						.addComponent(tfFam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_1)
						.addComponent(tfIm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_2)
						.addComponent(tfOt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_3)
						.addComponent(tfDr, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblNewLabel_4)
						.addComponent(tfSer, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(tfNom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_5))
					.addContainerGap(27, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
}
