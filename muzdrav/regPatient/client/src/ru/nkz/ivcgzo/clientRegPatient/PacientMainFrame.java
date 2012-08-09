package ru.nkz.ivcgzo.clientRegPatient;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.thriftRegPatient.PatientBrief;
import ru.nkz.ivcgzo.thriftRegPatient.PatientNotFoundException;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JFormattedTextField;

public class PacientMainFrame extends JFrame {
    private static final long serialVersionUID = 8528181014663112901L;
    public static PacientMainFrame instance;
    private JPanel contentPane;
    private JTextField tfFam;
    private JTextField tfIm;
    private JTextField tfOt;
    private JTextField tfSer;
    private JTextField tfNom;
    private CustomDateEditor tfDr;
    private PacientInfoFrame pacientInfoFrame;
    public List<PatientBrief> pat;

    public static PacientMainFrame getInstance() {
        return instance;
    }
    /**
     * Create the frame.
     */
    public PacientMainFrame() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setFont(new Font("Tahoma", Font.PLAIN, 11));
        setTitle("Поиск пациента");
        setBounds(200, 200, 308, 279);
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
        btnOtkaz.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnOtkaz.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                dispose();
            }
        });

        JButton btnPoisk = new JButton("Поиск");
        btnPoisk.setFont(new Font("Tahoma", Font.PLAIN, 11));
        btnPoisk.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    instance = PacientMainFrame.this;
                    PatientBrief patBr = new PatientBrief();
                    if (!tfFam.getText().isEmpty()) patBr.setFam(tfFam.getText().toUpperCase().trim());
                    if (!tfIm.getText().isEmpty()) patBr.setIm(tfIm.getText().toUpperCase().trim());
                    if (!tfOt.getText().isEmpty()) patBr.setOt(tfOt.getText().toUpperCase().trim());
                    if (!tfSer.getText().isEmpty()) patBr.setSpolis(tfSer.getText().toUpperCase().trim());
                    if (!tfNom.getText().isEmpty()) patBr.setNpolis(tfNom.getText().toUpperCase().trim());
                    //if ( tfDr.getDate().getTime() != 0) patBr.setDatar(tfDr.getDate().getTime());
                    try {
                        pat = MainForm.tcl.getAllPatientBrief(patBr);
                        dispose();
                        if (pacientInfoFrame == null) {
                            pacientInfoFrame = new PacientInfoFrame(pat);
                            MainForm.instance.addChildFrame(pacientInfoFrame);
                            pacientInfoFrame.pack();
                        } else
                            pacientInfoFrame.refresh(pat);
                            pacientInfoFrame.setVisible(true);
                            pacientInfoFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
                        }
                    catch (PatientNotFoundException e) {
                        JOptionPane.showMessageDialog(pacientInfoFrame, "По заданным критериям сведения о пациенте отсутствуют.");
                        System.out.println("По заданным критериям сведения о пациенте отсутствуют.");
                    }
                } catch (TException e) {
                    // TODO Auto-generated catch block
                    System.out.println("поймали тэиксэпшн");
                    e.printStackTrace();
                }
            }
        });
        GroupLayout gl_panel_1 = new GroupLayout(panel_1);
        gl_panel_1.setHorizontalGroup(
            gl_panel_1.createParallelGroup(Alignment.LEADING)
                .addGroup(gl_panel_1.createSequentialGroup()
                    .addGap(26)
                    .addComponent(btnOtkaz, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                    .addGap(42)
                    .addComponent(btnPoisk, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(30, Short.MAX_VALUE))
        );
        gl_panel_1.setVerticalGroup(
            gl_panel_1.createParallelGroup(Alignment.TRAILING)
                .addGroup(gl_panel_1.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
                        .addComponent(btnOtkaz)
                        .addComponent(btnPoisk))
                    .addContainerGap())
        );
        panel_1.setLayout(gl_panel_1);

        tfFam = new JTextField();
        tfFam.setFont(new Font("Tahoma", Font.PLAIN, 11));
        tfFam.setColumns(10);

        tfIm = new JTextField();
        tfIm.setFont(new Font("Tahoma", Font.PLAIN, 11));
        tfIm.setColumns(10);

        tfOt = new JTextField();
        tfOt.setFont(new Font("Tahoma", Font.PLAIN, 11));
        tfOt.setColumns(10);

        tfDr = new CustomDateEditor();

        tfSer = new JTextField();
        tfSer.setFont(new Font("Tahoma", Font.PLAIN, 11));
        tfSer.setColumns(10);

        tfNom = new JTextField();
        tfNom.setFont(new Font("Tahoma", Font.PLAIN, 11));
        tfNom.setColumns(10);

        JLabel lblNewLabel = new JLabel("Фамилия");
        lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 11));

        JLabel lblNewLabel_1 = new JLabel("Имя");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 11));

        JLabel lblNewLabel_2 = new JLabel("Отчество");
        lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 11));

        JLabel lblNewLabel_3 = new JLabel("Дата рождения");
        lblNewLabel_3.setFont(new Font("Tahoma", Font.PLAIN, 11));

        JLabel lblNewLabel_4 = new JLabel("Полис ОМС:  Серия");
        lblNewLabel_4.setFont(new Font("Tahoma", Font.PLAIN, 11));

        JLabel lblNewLabel_5 = new JLabel("Номер");
        lblNewLabel_5.setFont(new Font("Tahoma", Font.PLAIN, 11));

        GroupLayout gl_panel = new GroupLayout(panel);
        gl_panel.setHorizontalGroup(
            gl_panel.createParallelGroup(Alignment.TRAILING)
                .addGroup(gl_panel.createSequentialGroup()
                    .addGap(10)
                    .addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
                        .addComponent(lblNewLabel)
                        .addComponent(lblNewLabel_1)
                        .addComponent(lblNewLabel_2)
                        .addComponent(lblNewLabel_3)
                        .addComponent(lblNewLabel_4)
                        .addComponent(lblNewLabel_5))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
                        .addComponent(tfDr)
                        .addComponent(tfFam, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
                        .addComponent(tfIm)
                        .addComponent(tfOt)
                        .addComponent(tfSer)
                        .addComponent(tfNom))
                    .addContainerGap(32, Short.MAX_VALUE))
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
                    .addGap(9)
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
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panel.setLayout(gl_panel);
        contentPane.setLayout(gl_contentPane);
    }
    public void ClearPatientMainFrame(){
        try {
            tfFam.setText(null);
            tfIm.setText(null);
            tfOt.setText(null);
            tfSer.setText(null);
            tfNom.setText(null);
            tfDr.setValue(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
