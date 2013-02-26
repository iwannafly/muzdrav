package ru.nkz.ivcgzo.clientHospital;

import javax.swing.BoxLayout;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JButton;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftHospital.TPatient;

import java.awt.Dimension;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
public class PrintFrame extends JDialog {

    private static final long serialVersionUID = -5679060070597306575L;
    private CustomDateEditor cdeDateStart;
    private CustomDateEditor cdeDateEnd;
    private Box hbDateOptions;
    private JLabel lblDateStart;
    private JLabel lblDateEnd;
    private Component verticalStrut;
    private Box hbButtons;
    private JButton btnPrint;
    private JButton btnCancel;
    private TPatient patient;
    private Component hgButtonsLeft;
    private Component hgButtonsRight;
    private JPanel panel;

    public PrintFrame() {
        initialization();
        setVerticalComponentsContaner();
        pack();
    }

    private void initialization() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setModalityType(ModalityType.TOOLKIT_MODAL);
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(300, 60));
        setSize(new Dimension(300, 60));
//        setLocationRelativeTo(null);
        java.awt.Toolkit jToolkit = java.awt.Toolkit.getDefaultToolkit();
        Dimension screenSize = jToolkit.getScreenSize();
        setLocation((int) ((screenSize.getWidth() - getWidth()) / 2),
                (int) ((screenSize.getHeight() - getHeight()) / 2));
        setUndecorated(true);
    }

    private void setVerticalComponentsContaner() {
        panel = new JPanel();
        panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
        getContentPane().add(panel);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        addHorizontalTextFields();

        addHorizontalButtons();
    }

    private void addHorizontalTextFields() {
        hbDateOptions = Box.createHorizontalBox();
        panel.add(hbDateOptions);
        hbDateOptions.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.GRAY));

        lblDateStart = new JLabel("  C  ");
        hbDateOptions.add(lblDateStart);

        cdeDateStart = new CustomDateEditor();
        hbDateOptions.add(cdeDateStart);
        cdeDateStart.setAlignmentX(Component.LEFT_ALIGNMENT);
        cdeDateStart.setMaximumSize(new Dimension(300, 20));
        cdeDateStart.setColumns(10);

        lblDateEnd = new JLabel("  По  ");
        hbDateOptions.add(lblDateEnd);

        cdeDateEnd = new CustomDateEditor();
        hbDateOptions.add(cdeDateEnd);
        cdeDateEnd.setAlignmentX(Component.LEFT_ALIGNMENT);
        cdeDateEnd.setMaximumSize(new Dimension(300, 20));
        cdeDateEnd.setColumns(10);
    }

    private void addHorizontalButtons() {
        verticalStrut = Box.createVerticalStrut(10);
        verticalStrut.setPreferredSize(new Dimension(0, 5));
        verticalStrut.setMinimumSize(new Dimension(0, 5));
        verticalStrut.setMaximumSize(new Dimension(32767, 5));
        panel.add(verticalStrut);
        hbButtons = Box.createHorizontalBox();
        hbButtons.setMinimumSize(new Dimension(32000, 25));
        hbButtons.setPreferredSize(new Dimension(32000, 25));
        hbButtons.setMaximumSize(new Dimension(32000, 25));
        panel.add(hbButtons);
        hbButtons.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.GRAY));

        hgButtonsLeft = Box.createHorizontalGlue();
        hbButtons.add(hgButtonsLeft);

        addPrintButton();
        addCancelButton();

        hgButtonsRight = Box.createHorizontalGlue();
        hbButtons.add(hgButtonsRight);
    }

    private void addPrintButton() {
        btnPrint = new JButton("Распечатать");
        hbButtons.add(btnPrint);
        btnPrint.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                if ((patient != null)
                        && (cdeDateStart.getDate() != null)
                        && (cdeDateEnd.getDate() != null)) {
                    try {
                        String servPath =
                            ClientHospital.tcl.printHospitalDiary(patient.getGospitalCod(),
                                cdeDateStart.getDate().getTime(), cdeDateEnd.getDate().getTime());
                        String cliPath = File.createTempFile("muzdrav", ".htm").getAbsolutePath();
                        ClientHospital.conMan.transferFileFromServer(servPath, cliPath);
                        ClientHospital.conMan.openFileInEditor(cliPath, false);
                    } catch (KmiacServerException e1) {
                        e1.printStackTrace();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    } catch (TException e1) {
                        e1.printStackTrace();
                        ClientHospital.conMan.reconnect(e1);
                    }
                }
                PrintFrame.this.dispatchEvent(new WindowEvent(
                        PrintFrame.this, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    private void addCancelButton() {
        btnCancel = new JButton("Отмена");
        hbButtons.add(btnCancel);
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                PrintFrame.this.dispatchEvent(new WindowEvent(
                    PrintFrame.this, WindowEvent.WINDOW_CLOSING));
            }
        });
    }

    public final void setPatient(final TPatient inPatient) {
        patient = inPatient;
    }
}
