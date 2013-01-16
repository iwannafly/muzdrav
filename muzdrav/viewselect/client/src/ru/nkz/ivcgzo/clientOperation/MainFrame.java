package ru.nkz.ivcgzo.clientOperation;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Box;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientOperation.model.IOperationModel;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftMedication.Patient;
import ru.nkz.ivcgzo.thriftOperation.Anesthesia;
import ru.nkz.ivcgzo.thriftOperation.AnesthesiaComplication;
import ru.nkz.ivcgzo.thriftOperation.AnesthesiaPaymentFund;
import ru.nkz.ivcgzo.thriftOperation.Operation;
import ru.nkz.ivcgzo.thriftOperation.OperationComplication;
import ru.nkz.ivcgzo.thriftOperation.OperationPaymentFund;

import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import org.apache.thrift.TException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Font;

public class MainFrame extends JFrame implements IOperationObserver {

    private static final long serialVersionUID = -3201408430800941030L;
    private IController controller;
    private IOperationModel model;
    private JPanel pAnesthesia;
    private JPanel pOperation;
    private JTabbedPane tbMain;
    private Box hbOperationControl;
    private CustomTable<Operation, Operation._Fields> tbOperation;
    private Box vbOperationTableControls;
    private JButton btnOperationAdd;
    private JButton btnOperationDelete;
    private JButton btnOperationUpdate;
    private JScrollPane spOperation;
    private Box hbOperationComlicationControls;
    private JScrollPane spOperationComplications;
    private Box vbOperationComplicationTableControls;
    private JButton btnOperationComplicationAdd;
    private JButton btnOperationComplicationDelete;
    private JButton btnOperationComplicationUpdate;
    private CustomTable<OperationComplication, OperationComplication._Fields> tbOperationComplications;
    private Box hbOperationPaymentFundsControls;
    private JScrollPane spOperationPaymentFunds;
    private Box vbOperationPaymentFundsTableControls;
    private JButton btnOperationPaymentFundsAdd;
    private JButton btnOperationPaymentFundsDelete;
    private JButton btnOperationPaymentFundsUpdate;
    private CustomTable<OperationPaymentFund, OperationPaymentFund._Fields> tbOperationPaymentFunds;
    private Box hbAnesthesiaControl;
    private JScrollPane spAnesthesia;
    private CustomTable<Anesthesia, Anesthesia._Fields> tbAnesthesia;
    private Box vbAnesthesiaTableControls;
    private JButton btnAnesthesiaAdd;
    private JButton btnAnesthesiaDelete;
    private JButton btnAnesthesiaUpdate;
    private Box hbAnesthesiaComlicationControls;
    private JScrollPane spAnesthesiaComplications;
    private CustomTable<AnesthesiaComplication, AnesthesiaComplication._Fields> tbAnesthesiaComplications;
    private Box vbAnesthesiaComplicationTableControls;
    private JButton btnAnesthesiaComplicationAdd;
    private JButton btnAnesthesiaComplicationDelete;
    private JButton btnAnesthesiaComplicationUpdate;
    private Box hbAnesthesiaPaymentFundsControls;
    private JScrollPane spAnesthesiaPaymentFunds;
    private CustomTable<AnesthesiaPaymentFund, AnesthesiaPaymentFund._Fields> tbAnesthesiaPaymentFunds;
    private Box vbAnesthesiaPaymentFundsTableControls;
    private JButton btnAnesthesiaPaymentFundsAdd;
    private JButton btnAnesthesiaPaymentFundsDelete;
    private JButton btnAnesthesiaPaymentFundsUpdate;
    private JLabel lblOperationDescription;
    private Box verticalBox;
    private JTextArea taOperationDescription;
    private JScrollPane spOperationDescription;
    private JLabel lblOperationEpicris;
    private JTextArea taOperationEpicris;
    private JScrollPane spOperationEpicris;
    private JLabel lblOperationMaterial;
    private JTextArea taOperationMaterial;
    private JScrollPane spOperationMaterial;
    private Patient patient;

    public MainFrame(final IController inController,
            final IOperationModel inModel) {
        this.controller = inController;
        this.model = inModel;
        model.registerOperationObserver((IOperationObserver) this);
        setDefaults();
        initialization();
    }

    public void createFrame() {
        initialization();
    }

    private void setDefaults() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(980, 600));
        setSize(new Dimension(980, 600));
        getContentPane().setLayout(new BorderLayout(0, 0));
    }

    private void initialization() {
        tbMain = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tbMain, BorderLayout.CENTER);
        
        pOperation = new JPanel();
        tbMain.addTab("Операции", null, pOperation, null);
        pOperation.setLayout(new BoxLayout(pOperation, BoxLayout.Y_AXIS));
        
        hbOperationControl = Box.createHorizontalBox();
        hbOperationControl.setBorder(new LineBorder(new Color(0, 0, 0)));
        hbOperationControl.setAlignmentY(Component.CENTER_ALIGNMENT);
        pOperation.add(hbOperationControl);
        
        spOperation = new JScrollPane();
        hbOperationControl.add(spOperation);
        
        tbOperation = new CustomTable<Operation, Operation._Fields>(true, true, Operation.class,
                1, "Вид стационара", 5, "Наименование операции",
                7, "Дата операции", 8, "Время операции");
        tbOperation.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (tbOperation.getSelectedItem() != null) {
                        System.out.println("опа-опа");
                        controller.setCurrentOperation(tbOperation.getSelectedItem());
                        taOperationDescription.setText(tbOperation.getSelectedItem().getOpOper());
                        taOperationEpicris.setText(tbOperation.getSelectedItem().getPredP());
                        taOperationMaterial.setText(tbOperation.getSelectedItem().getMaterial());
                    }
                }
            }
        });
//        tbOperation.addMouseListener(new MouseAdapter() {
//            @Override
//            public void mouseClicked(MouseEvent e) {
//                if (tbOperation.getSelectedItem() != null) {
//                    try {
//                        tbOperationComplications.setData(
//                                ClientOperation.tcl.getOperationComplications(
//                                        tbOperation.getSelectedItem().getId())
//                        );
//                        tbOperationPaymentFunds.setData(
//                                ClientOperation.tcl.getOperationPaymentFunds(
//                                        tbOperation.getSelectedItem().getId())
//                        );
//                        tbAnesthesia.setData(ClientOperation.tcl.getAnesthesias(
//                                tbOperation.getSelectedItem().getId()));
//                    } catch (KmiacServerException e1) {
//                        e1.printStackTrace();
//                    } catch (TException e1) {
//                        e1.printStackTrace();
//                        ClientOperation.conMan.reconnect(e1);
//                    }
//                }
//            }
//        });
        tbOperation.setDateField(2);
        tbOperation.setTimeField(3);
        tbOperation.setIntegerClassifierSelector(0, IntegerClassifiers.n_tip);
        tbOperation.setStringClassifierSelector(1, StringClassifiers.n_ak2);
        spOperation.setViewportView(tbOperation);
        vbOperationTableControls = Box.createVerticalBox();
        vbOperationTableControls.setAlignmentX(Component.CENTER_ALIGNMENT);
        hbOperationControl.add(vbOperationTableControls);
        
        btnOperationAdd = new JButton("");
        btnOperationAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (patient != null) {
                        Operation curOper = new Operation();
                        curOper.setIdGosp(patient.getIdGosp());
                        curOper.setCotd(ClientOperation.authInfo.getCpodr());
                        curOper.setDataz(System.currentTimeMillis());
                        curOper.setVrem(System.currentTimeMillis());
                        curOper.setDate(System.currentTimeMillis());
                        curOper.setNpasp(patient.getId());
                        curOper.setId(ClientOperation.tcl.addOperation(curOper));
                        tbOperation.addItem(curOper);
                        tbOperation.setData(
                                ClientOperation.tcl.getOperations(patient.getIdGosp()));
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientOperation.conMan.reconnect(e1);
                }
            }
        });
        btnOperationAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOperationAdd.setMaximumSize(new Dimension(50, 50));
        btnOperationAdd.setPreferredSize(new Dimension(50, 50));
        btnOperationAdd.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1331789242_Add.png")));
        vbOperationTableControls.add(btnOperationAdd);
        
        btnOperationDelete = new JButton("");
        btnOperationDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tbOperation.getSelectedItem() != null) {
                        int opResult = JOptionPane.showConfirmDialog(
                            MainFrame.this, "Удалить запись?",
                            "Удаление записи", JOptionPane.YES_NO_OPTION);
                        if (opResult == JOptionPane.YES_OPTION) {
                            ClientOperation.tcl.deleteOperation(
                                tbOperation.getSelectedItem().getId());
                            tbOperation.setData(
                                ClientOperation.tcl.getOperations((patient.getIdGosp())));
                        }
                        if (tbOperation.getRowCount() > 0) {
                            tbOperation.setRowSelectionInterval(tbOperation.getRowCount() - 1,
                                tbOperation.getRowCount() - 1);
                        }
//                        clearMedicalHistoryTextAreas();
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientOperation.conMan.reconnect(e1);
                }
            }
        });
        btnOperationDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOperationDelete.setMaximumSize(new Dimension(50, 50));
        btnOperationDelete.setPreferredSize(new Dimension(50, 50));
        btnOperationDelete.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1331789259_Delete.png")));
        vbOperationTableControls.add(btnOperationDelete);
        
        btnOperationUpdate = new JButton("");
        btnOperationUpdate.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOperationUpdate.setMaximumSize(new Dimension(50, 50));
        btnOperationUpdate.setPreferredSize(new Dimension(50, 50));
        btnOperationUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tbOperation.getSelectedItem() != null) {
                        tbOperation.getSelectedItem().setOpOper(taOperationDescription.getText());
                        tbOperation.getSelectedItem().setPredP(taOperationEpicris.getText());
                        tbOperation.getSelectedItem().setMaterial(taOperationMaterial.getText());
                        ClientOperation.tcl.updateOperation(
                            tbOperation.getSelectedItem());
                        tbOperation.setData(
                            ClientOperation.tcl.getOperations((patient.getIdGosp())));
//                        clearMedicalHistoryTextAreas();
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientOperation.conMan.reconnect(e1);
                }
            }
        });
        btnOperationUpdate.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1341981970_Accept.png")));
        vbOperationTableControls.add(btnOperationUpdate);
        
        verticalBox = Box.createVerticalBox();
        verticalBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        pOperation.add(verticalBox);
        
        lblOperationDescription = new JLabel("Описание операции");
        lblOperationDescription.setAlignmentX(Component.CENTER_ALIGNMENT);
        verticalBox.add(lblOperationDescription);
        
        spOperationDescription = new JScrollPane();
        verticalBox.add(spOperationDescription);
        
        taOperationDescription = new JTextArea();
        taOperationDescription.setFont(new Font("Monospaced", Font.PLAIN, 11));
        spOperationDescription.setViewportView(taOperationDescription);
        
        lblOperationEpicris = new JLabel("Предоперационный эпикриз");
        lblOperationEpicris.setAlignmentX(0.5f);
        verticalBox.add(lblOperationEpicris);
        
        spOperationEpicris = new JScrollPane();
        verticalBox.add(spOperationEpicris);
        
        taOperationEpicris = new JTextArea();
        taOperationEpicris.setFont(new Font("Monospaced", Font.PLAIN, 11));
        spOperationEpicris.setViewportView(taOperationEpicris);
        
        lblOperationMaterial = new JLabel("Шовный материал");
        verticalBox.add(lblOperationMaterial);
        lblOperationMaterial.setAlignmentX(0.5f);
        
        spOperationMaterial = new JScrollPane();
        verticalBox.add(spOperationMaterial);
        
        taOperationMaterial = new JTextArea();
        taOperationMaterial.setFont(new Font("Monospaced", Font.PLAIN, 11));
        spOperationMaterial.setViewportView(taOperationMaterial);
        
        hbOperationComlicationControls = Box.createHorizontalBox();
        hbOperationComlicationControls.setBorder(new LineBorder(new Color(0, 0, 0)));
        hbOperationComlicationControls.setAlignmentY(0.5f);
        pOperation.add(hbOperationComlicationControls);
        
        spOperationComplications = new JScrollPane();
        hbOperationComlicationControls.add(spOperationComplications);
        
        tbOperationComplications =
                new CustomTable<OperationComplication, OperationComplication._Fields>(
                        true, true, OperationComplication.class,
                        3, "Имя осложнения", 4, "Дата"
                );
        tbOperationComplications.setStringClassifierSelector(0, StringClassifiers.n_c00);
        tbOperationComplications.setDateField(1);
        spOperationComplications.setViewportView(tbOperationComplications);
        
        vbOperationComplicationTableControls = Box.createVerticalBox();
        vbOperationComplicationTableControls.setAlignmentX(0.5f);
        hbOperationComlicationControls.add(vbOperationComplicationTableControls);
        
        btnOperationComplicationAdd = new JButton("");
        btnOperationComplicationAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if ((patient != null) && (tbOperation.getSelectedItem() != null)){
                        OperationComplication curOperComplication = new OperationComplication();
                        curOperComplication.setDataz(System.currentTimeMillis());
                        curOperComplication.setIdOper(tbOperation.getSelectedItem().getId());
                        curOperComplication.setId(
                                ClientOperation.tcl.addOperationComplication(curOperComplication));
                        tbOperationComplications.addItem(curOperComplication);
                        tbOperationComplications.setData(
                                ClientOperation.tcl.getOperationComplications(
                                        tbOperation.getSelectedItem().getId()));
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientOperation.conMan.reconnect(e1);
                }
            }
        });
        btnOperationComplicationAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOperationComplicationAdd.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1331789242_Add.png")));
        btnOperationComplicationAdd.setPreferredSize(new Dimension(50, 50));
        btnOperationComplicationAdd.setMaximumSize(new Dimension(50, 50));
        vbOperationComplicationTableControls.add(btnOperationComplicationAdd);
        
        btnOperationComplicationDelete = new JButton("");
        btnOperationComplicationDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tbOperationComplications.getSelectedItem() != null) {
                        int opResult = JOptionPane.showConfirmDialog(
                            MainFrame.this, "Удалить запись?",
                            "Удаление записи", JOptionPane.YES_NO_OPTION);
                        if (opResult == JOptionPane.YES_OPTION) {
                            ClientOperation.tcl.deleteOperationComplication(
                                tbOperationComplications.getSelectedItem().getId());
                            tbOperationComplications.setData(
                                ClientOperation.tcl.getOperationComplications(
                                        tbOperation.getSelectedItem().getId())
                            );
                        }
                        if (tbOperationComplications.getRowCount() > 0) {
                            tbOperationComplications.setRowSelectionInterval(
                                    tbOperationComplications.getRowCount() - 1,
                                    tbOperationComplications.getRowCount() - 1);
                        }
//                        clearMedicalHistoryTextAreas();
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientOperation.conMan.reconnect(e1);
                }
            }
        });
        btnOperationComplicationDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOperationComplicationDelete.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1331789259_Delete.png")));
        btnOperationComplicationDelete.setPreferredSize(new Dimension(50, 50));
        btnOperationComplicationDelete.setMaximumSize(new Dimension(50, 50));
        vbOperationComplicationTableControls.add(btnOperationComplicationDelete);
        
        btnOperationComplicationUpdate = new JButton("");
        btnOperationComplicationUpdate.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOperationComplicationUpdate.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1341981970_Accept.png")));
        btnOperationComplicationUpdate.setPreferredSize(new Dimension(50, 50));
        btnOperationComplicationUpdate.setMaximumSize(new Dimension(50, 50));
        btnOperationComplicationUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tbOperationComplications.getSelectedItem() != null) {
                        ClientOperation.tcl.updateOperationComplication(
                            tbOperationComplications.getSelectedItem());
                        tbOperationComplications.setData(
                                ClientOperation.tcl.getOperationComplications(
                                        tbOperation.getSelectedItem().getId()));
//                        clearMedicalHistoryTextAreas();
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientOperation.conMan.reconnect(e1);
                }
            }
        });
        vbOperationComplicationTableControls.add(btnOperationComplicationUpdate);
        
        hbOperationPaymentFundsControls = Box.createHorizontalBox();
        hbOperationPaymentFundsControls.setBorder(new LineBorder(new Color(0, 0, 0)));
        hbOperationPaymentFundsControls.setAlignmentY(0.5f);
        pOperation.add(hbOperationPaymentFundsControls);
        
        spOperationPaymentFunds = new JScrollPane();
        hbOperationPaymentFundsControls.add(spOperationPaymentFunds);
        
        tbOperationPaymentFunds =
                new CustomTable<OperationPaymentFund, OperationPaymentFund._Fields>(
                        true, true, OperationPaymentFund.class,
                        2, "Наименование источника оплаты",  3, "Дата"
                );
        tbOperationPaymentFunds.setIntegerClassifierSelector(0, IntegerClassifiers.n_opl);
        tbOperationPaymentFunds.setDateField(1);
        spOperationPaymentFunds.setViewportView(tbOperationPaymentFunds);
        
        vbOperationPaymentFundsTableControls = Box.createVerticalBox();
        vbOperationPaymentFundsTableControls.setAlignmentX(0.5f);
        hbOperationPaymentFundsControls.add(vbOperationPaymentFundsTableControls);
        
        btnOperationPaymentFundsAdd = new JButton("");
        btnOperationPaymentFundsAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if ((patient != null) && (tbOperation.getSelectedItem() != null)){
                        OperationPaymentFund curOperPaymentFunds = new OperationPaymentFund();
                        curOperPaymentFunds.setDataz(System.currentTimeMillis());
                        curOperPaymentFunds.setIdOper(tbOperation.getSelectedItem().getId());
                        curOperPaymentFunds.setId(
                                ClientOperation.tcl.addOperationPaymentFund(curOperPaymentFunds));
                        tbOperationPaymentFunds.addItem(curOperPaymentFunds);
                        tbOperationPaymentFunds.setData(
                                ClientOperation.tcl.getOperationPaymentFunds(
                                        tbOperation.getSelectedItem().getId()));
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientOperation.conMan.reconnect(e1);
                }
            }
        });
        btnOperationPaymentFundsAdd.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1331789242_Add.png")));
        btnOperationPaymentFundsAdd.setPreferredSize(new Dimension(50, 50));
        btnOperationPaymentFundsAdd.setMaximumSize(new Dimension(50, 50));
        vbOperationPaymentFundsTableControls.add(btnOperationPaymentFundsAdd);
        
        btnOperationPaymentFundsDelete = new JButton("");
        btnOperationPaymentFundsDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tbOperationPaymentFunds.getSelectedItem() != null) {
                        int opResult = JOptionPane.showConfirmDialog(
                            MainFrame.this, "Удалить запись?",
                            "Удаление записи", JOptionPane.YES_NO_OPTION);
                        if (opResult == JOptionPane.YES_OPTION) {
                            ClientOperation.tcl.deleteOperationPaymentFund(
                                tbOperationPaymentFunds.getSelectedItem().getId());
                            tbOperationPaymentFunds.setData(
                                ClientOperation.tcl.getOperationPaymentFunds(
                                        tbOperation.getSelectedItem().getId())
                            );
                        }
                        if (tbOperationPaymentFunds.getRowCount() > 0) {
                            tbOperationPaymentFunds.setRowSelectionInterval(
                                    tbOperationPaymentFunds.getRowCount() - 1,
                                    tbOperationPaymentFunds.getRowCount() - 1);
                        }
//                        clearMedicalHistoryTextAreas();
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientOperation.conMan.reconnect(e1);
                }
            }
        });
        btnOperationPaymentFundsDelete.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1331789259_Delete.png")));
        btnOperationPaymentFundsDelete.setPreferredSize(new Dimension(50, 50));
        btnOperationPaymentFundsDelete.setMaximumSize(new Dimension(50, 50));
        vbOperationPaymentFundsTableControls.add(btnOperationPaymentFundsDelete);
        
        btnOperationPaymentFundsUpdate = new JButton("");
        btnOperationPaymentFundsUpdate.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1341981970_Accept.png")));
        btnOperationPaymentFundsUpdate.setPreferredSize(new Dimension(50, 50));
        btnOperationPaymentFundsUpdate.setMaximumSize(new Dimension(50, 50));
        btnOperationPaymentFundsUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tbOperationPaymentFunds.getSelectedItem() != null) {
                        ClientOperation.tcl.updateOperationPaymentFund(
                            tbOperationPaymentFunds.getSelectedItem());
                        tbOperationPaymentFunds.setData(
                                ClientOperation.tcl.getOperationPaymentFunds(
                                        tbOperation.getSelectedItem().getId()));
//                        clearMedicalHistoryTextAreas();
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientOperation.conMan.reconnect(e1);
                }
            }
        });
        vbOperationPaymentFundsTableControls.add(btnOperationPaymentFundsUpdate);
        
        pAnesthesia = new JPanel();
        tbMain.addTab("Анастезия", null, pAnesthesia, null);
        pAnesthesia.setLayout(new BoxLayout(pAnesthesia, BoxLayout.Y_AXIS));

        hbAnesthesiaControl = Box.createHorizontalBox();
        hbAnesthesiaControl.setBorder(new LineBorder(new Color(0, 0, 0)));
        hbAnesthesiaControl.setAlignmentX(Component.LEFT_ALIGNMENT);
        hbAnesthesiaControl.setAlignmentY(Component.CENTER_ALIGNMENT);
        pAnesthesia.add(hbAnesthesiaControl);

        spAnesthesia = new JScrollPane();
        hbAnesthesiaControl.add(spAnesthesia);

        tbAnesthesia = new CustomTable<Anesthesia, Anesthesia._Fields>(true, true, Anesthesia.class,
                1, "Вид стационара", 6, "Наименование анестезии",
                8, "Дата операции", 9, "Время операции");
        tbAnesthesia.setIntegerClassifierSelector(0, IntegerClassifiers.n_tip);
        tbAnesthesia.setIntegerClassifierSelector(1, IntegerClassifiers.n_aj0);
        tbAnesthesia.setDateField(2);
        tbAnesthesia.setTimeField(3);
        spAnesthesia.setViewportView(tbAnesthesia);
        vbAnesthesiaTableControls = Box.createVerticalBox();
        vbAnesthesiaTableControls.setAlignmentX(Component.CENTER_ALIGNMENT);
        hbAnesthesiaControl.add(vbAnesthesiaTableControls);

        tbAnesthesia.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
              if (tbAnesthesia.getSelectedItem() != null) {
                  try {
                      tbAnesthesiaComplications.setData(
                              ClientOperation.tcl.getAnesthesiaComplications(
                                      tbAnesthesia.getSelectedItem().getId())
                      );
                      tbAnesthesiaPaymentFunds.setData(
                              ClientOperation.tcl.getAnesthesiaPaymentFunds(
                                      tbAnesthesia.getSelectedItem().getId())
                      );
                  } catch (KmiacServerException e1) {
                      e1.printStackTrace();
                  } catch (TException e1) {
                      e1.printStackTrace();
                      ClientOperation.conMan.reconnect(e1);
                  }
              }
          }
        });
        btnAnesthesiaAdd = new JButton("");
        btnAnesthesiaAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if ((patient != null) && (tbOperation.getSelectedItem() != null)){
                        Anesthesia curAnesthesia = new Anesthesia();
                        curAnesthesia.setDataz(System.currentTimeMillis());
                        curAnesthesia.setIdOper(tbOperation.getSelectedItem().getId());
                        curAnesthesia.setCotd(ClientOperation.authInfo.getCpodr());
                        curAnesthesia.setDate(System.currentTimeMillis());
                        curAnesthesia.setIdGosp(patient.getIdGosp());
                        curAnesthesia.setVrem(System.currentTimeMillis());
                        curAnesthesia.setNpasp(patient.getId());
                        curAnesthesia.setId(
                                ClientOperation.tcl.addAnesthesia(curAnesthesia));
                        tbAnesthesia.addItem(curAnesthesia);
                        tbAnesthesia.setData(
                                ClientOperation.tcl.getAnesthesias(
                                        tbOperation.getSelectedItem().getId()));
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientOperation.conMan.reconnect(e1);
                }
            }
        });
        btnAnesthesiaAdd.setMaximumSize(new Dimension(50, 50));
        btnAnesthesiaAdd.setPreferredSize(new Dimension(50, 50));
        btnAnesthesiaAdd.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1331789242_Add.png")));
        vbAnesthesiaTableControls.add(btnAnesthesiaAdd);

        btnAnesthesiaDelete = new JButton("");
        btnAnesthesiaDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tbAnesthesia.getSelectedItem() != null) {
                        int opResult = JOptionPane.showConfirmDialog(
                            MainFrame.this, "Удалить запись?",
                            "Удаление записи", JOptionPane.YES_NO_OPTION);
                        if (opResult == JOptionPane.YES_OPTION) {
                            ClientOperation.tcl.deleteAnesthesia(
                                tbAnesthesia.getSelectedItem().getId());
                            tbAnesthesia.setData(
                                ClientOperation.tcl.getAnesthesias(
                                        tbOperation.getSelectedItem().getId())
                            );
                        }
                        if (tbAnesthesia.getRowCount() > 0) {
                            tbAnesthesia.setRowSelectionInterval(
                                    tbAnesthesia.getRowCount() - 1,
                                    tbAnesthesia.getRowCount() - 1);
                        }
//                        clearMedicalHistoryTextAreas();
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientOperation.conMan.reconnect(e1);
                }
            }
        });
        btnAnesthesiaDelete.setMaximumSize(new Dimension(50, 50));
        btnAnesthesiaDelete.setPreferredSize(new Dimension(50, 50));
        btnAnesthesiaDelete.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1331789259_Delete.png")));
        vbAnesthesiaTableControls.add(btnAnesthesiaDelete);

        btnAnesthesiaUpdate = new JButton("");
        btnAnesthesiaUpdate.setMaximumSize(new Dimension(50, 50));
        btnAnesthesiaUpdate.setPreferredSize(new Dimension(50, 50));
        btnAnesthesiaUpdate.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1341981970_Accept.png")));
        btnAnesthesiaUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tbAnesthesia.getSelectedItem() != null) {
                        ClientOperation.tcl.updateAnesthesia(
                            tbAnesthesia.getSelectedItem());
                        tbAnesthesia.setData(
                                ClientOperation.tcl.getAnesthesias(
                                        tbOperation.getSelectedItem().getId()));
//                        clearMedicalHistoryTextAreas();
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientOperation.conMan.reconnect(e1);
                }
            }
        });
        vbAnesthesiaTableControls.add(btnAnesthesiaUpdate);

        hbAnesthesiaComlicationControls = Box.createHorizontalBox();
        hbAnesthesiaComlicationControls.setBorder(new LineBorder(new Color(0, 0, 0)));
        hbAnesthesiaComlicationControls.setAlignmentY(0.5f);
        hbAnesthesiaComlicationControls.setAlignmentX(0.0f);
        pAnesthesia.add(hbAnesthesiaComlicationControls);

        spAnesthesiaComplications = new JScrollPane();
        hbAnesthesiaComlicationControls.add(spAnesthesiaComplications);

        tbAnesthesiaComplications =
                new CustomTable<AnesthesiaComplication, AnesthesiaComplication._Fields>(
                        true, true, AnesthesiaComplication.class,
                        3, "Имя осложнения", 4, "Дата"
                );
        spAnesthesiaComplications.setViewportView(tbAnesthesiaComplications);
        tbAnesthesiaComplications.setDateField(1);
        tbAnesthesiaComplications.setStringClassifierSelector(0, StringClassifiers.n_c00);

        vbAnesthesiaComplicationTableControls = Box.createVerticalBox();
        vbAnesthesiaComplicationTableControls.setAlignmentX(0.5f);
        hbAnesthesiaComlicationControls.add(vbAnesthesiaComplicationTableControls);

        btnAnesthesiaComplicationAdd = new JButton("");
        btnAnesthesiaComplicationAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if ((patient != null) && (tbAnesthesia.getSelectedItem() != null)){
                        AnesthesiaComplication curAnesthesiaComplication =
                                new AnesthesiaComplication();
                        curAnesthesiaComplication.setDataz(System.currentTimeMillis());
                        curAnesthesiaComplication.setIdAnast(
                                tbAnesthesia.getSelectedItem().getId());
                        curAnesthesiaComplication.setId(
                                ClientOperation.tcl.addAnesthesiaComplication(
                                        curAnesthesiaComplication));
                        tbAnesthesiaComplications.addItem(curAnesthesiaComplication);
                        tbAnesthesiaComplications.setData(
                                ClientOperation.tcl.getAnesthesiaComplications(
                                        tbAnesthesia.getSelectedItem().getId()));
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientOperation.conMan.reconnect(e1);
                }
            }
        });
        btnAnesthesiaComplicationAdd.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1331789242_Add.png")));
        btnAnesthesiaComplicationAdd.setPreferredSize(new Dimension(50, 50));
        btnAnesthesiaComplicationAdd.setMaximumSize(new Dimension(50, 50));
        vbAnesthesiaComplicationTableControls.add(btnAnesthesiaComplicationAdd);

        btnAnesthesiaComplicationDelete = new JButton("");
        btnAnesthesiaComplicationDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tbAnesthesiaComplications.getSelectedItem() != null) {
                        int opResult = JOptionPane.showConfirmDialog(
                            MainFrame.this, "Удалить запись?",
                            "Удаление записи", JOptionPane.YES_NO_OPTION);
                        if (opResult == JOptionPane.YES_OPTION) {
                            ClientOperation.tcl.deleteAnesthesiaComplication(
                                tbAnesthesiaComplications.getSelectedItem().getId());
                            tbAnesthesiaComplications.setData(
                                ClientOperation.tcl.getAnesthesiaComplications(
                                        tbAnesthesia.getSelectedItem().getId())
                            );
                        }
                        if (tbAnesthesiaComplications.getRowCount() > 0) {
                            tbAnesthesiaComplications.setRowSelectionInterval(
                                    tbAnesthesiaComplications.getRowCount() - 1,
                                    tbAnesthesiaComplications.getRowCount() - 1);
                        }
//                        clearMedicalHistoryTextAreas();
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientOperation.conMan.reconnect(e1);
                }
            }
        });
        btnAnesthesiaComplicationDelete.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1331789259_Delete.png")));
        btnAnesthesiaComplicationDelete.setPreferredSize(new Dimension(50, 50));
        btnAnesthesiaComplicationDelete.setMaximumSize(new Dimension(50, 50));
        vbAnesthesiaComplicationTableControls.add(btnAnesthesiaComplicationDelete);

        btnAnesthesiaComplicationUpdate = new JButton("");
        btnAnesthesiaComplicationUpdate.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1341981970_Accept.png")));
        btnAnesthesiaComplicationUpdate.setPreferredSize(new Dimension(50, 50));
        btnAnesthesiaComplicationUpdate.setMaximumSize(new Dimension(50, 50));
        btnAnesthesiaComplicationUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tbAnesthesiaComplications.getSelectedItem() != null) {
                        ClientOperation.tcl.updateAnesthesiaComplication(
                            tbAnesthesiaComplications.getSelectedItem());
                        tbAnesthesiaComplications.setData(
                                ClientOperation.tcl.getAnesthesiaComplications(
                                        tbAnesthesia.getSelectedItem().getId()));
//                        clearMedicalHistoryTextAreas();
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientOperation.conMan.reconnect(e1);
                }
            }
        });
        vbAnesthesiaComplicationTableControls.add(btnAnesthesiaComplicationUpdate);

        hbAnesthesiaPaymentFundsControls = Box.createHorizontalBox();
        hbAnesthesiaPaymentFundsControls.setBorder(new LineBorder(new Color(0, 0, 0)));
        hbAnesthesiaPaymentFundsControls.setAlignmentY(0.5f);
        hbAnesthesiaPaymentFundsControls.setAlignmentX(0.0f);
        pAnesthesia.add(hbAnesthesiaPaymentFundsControls);

        spAnesthesiaPaymentFunds = new JScrollPane();
        hbAnesthesiaPaymentFundsControls.add(spAnesthesiaPaymentFunds);

        tbAnesthesiaPaymentFunds =
                new CustomTable<AnesthesiaPaymentFund, AnesthesiaPaymentFund._Fields>(
                        true, true, AnesthesiaPaymentFund.class,
                        2, "Наименование источника оплаты",  3, "Дата"
                );
        tbAnesthesiaPaymentFunds.setIntegerClassifierSelector(0, IntegerClassifiers.n_opl);
        tbAnesthesiaPaymentFunds.setDateField(1);
        spAnesthesiaPaymentFunds.setViewportView(tbAnesthesiaPaymentFunds);

        vbAnesthesiaPaymentFundsTableControls = Box.createVerticalBox();
        vbAnesthesiaPaymentFundsTableControls.setAlignmentX(0.5f);
        hbAnesthesiaPaymentFundsControls.add(vbAnesthesiaPaymentFundsTableControls);

        btnAnesthesiaPaymentFundsAdd = new JButton("");
        btnAnesthesiaPaymentFundsAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if ((patient != null) && (tbAnesthesia.getSelectedItem() != null)){
                        AnesthesiaPaymentFund curAnesthesiaPaymentFund =
                                new AnesthesiaPaymentFund();
                        curAnesthesiaPaymentFund.setDataz(System.currentTimeMillis());
                        curAnesthesiaPaymentFund.setIdAnast(
                                tbAnesthesia.getSelectedItem().getId());
                        curAnesthesiaPaymentFund.setId(
                                ClientOperation.tcl.addAnesthesiaPaymentFund(
                                        curAnesthesiaPaymentFund));
                        tbAnesthesiaPaymentFunds.addItem(curAnesthesiaPaymentFund);
                        tbAnesthesiaPaymentFunds.setData(
                                ClientOperation.tcl.getAnesthesiaPaymentFunds(
                                        tbAnesthesia.getSelectedItem().getId()));
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientOperation.conMan.reconnect(e1);
                }
            }
        });
        btnAnesthesiaPaymentFundsAdd.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1331789242_Add.png")));
        btnAnesthesiaPaymentFundsAdd.setPreferredSize(new Dimension(50, 50));
        btnAnesthesiaPaymentFundsAdd.setMaximumSize(new Dimension(50, 50));
        vbAnesthesiaPaymentFundsTableControls.add(btnAnesthesiaPaymentFundsAdd);

        btnAnesthesiaPaymentFundsDelete = new JButton("");
        btnAnesthesiaPaymentFundsDelete.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1331789259_Delete.png")));
        btnAnesthesiaPaymentFundsDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tbAnesthesiaPaymentFunds.getSelectedItem() != null) {
                        int opResult = JOptionPane.showConfirmDialog(
                            MainFrame.this, "Удалить запись?",
                            "Удаление записи", JOptionPane.YES_NO_OPTION);
                        if (opResult == JOptionPane.YES_OPTION) {
                            ClientOperation.tcl.deleteAnesthesiaPaymentFund(
                                tbAnesthesiaPaymentFunds.getSelectedItem().getId());
                            tbAnesthesiaPaymentFunds.setData(
                                ClientOperation.tcl.getAnesthesiaPaymentFunds(
                                        tbAnesthesia.getSelectedItem().getId())
                            );
                        }
                        if (tbAnesthesiaPaymentFunds.getRowCount() > 0) {
                            tbAnesthesiaPaymentFunds.setRowSelectionInterval(
                                    tbAnesthesiaPaymentFunds.getRowCount() - 1,
                                    tbAnesthesiaPaymentFunds.getRowCount() - 1);
                        }
//                        clearMedicalHistoryTextAreas();
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientOperation.conMan.reconnect(e1);
                }
            }
        });
        btnAnesthesiaPaymentFundsDelete.setPreferredSize(new Dimension(50, 50));
        btnAnesthesiaPaymentFundsDelete.setMaximumSize(new Dimension(50, 50));
        vbAnesthesiaPaymentFundsTableControls.add(btnAnesthesiaPaymentFundsDelete);

        btnAnesthesiaPaymentFundsUpdate = new JButton("");
        btnAnesthesiaPaymentFundsUpdate.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1341981970_Accept.png")));
        btnAnesthesiaPaymentFundsUpdate.setPreferredSize(new Dimension(50, 50));
        btnAnesthesiaPaymentFundsUpdate.setMaximumSize(new Dimension(50, 50));
        btnAnesthesiaPaymentFundsUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    if (tbAnesthesiaPaymentFunds.getSelectedItem() != null) {
                        ClientOperation.tcl.updateAnesthesiaPaymentFund(
                            tbAnesthesiaPaymentFunds.getSelectedItem());
                        tbAnesthesiaPaymentFunds.setData(
                                ClientOperation.tcl.getAnesthesiaPaymentFunds(
                                        tbAnesthesia.getSelectedItem().getId()));
//                        clearMedicalHistoryTextAreas();
                    }
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientOperation.conMan.reconnect(e1);
                }
            }
        });
        vbAnesthesiaPaymentFundsTableControls.add(btnAnesthesiaPaymentFundsUpdate);
    }

    public void onConnect() {
    }

    public void fillPatient(int id, String surname, String name,
            String middlename, int idGosp) {
        patient = new Patient();
        patient.setId(id);
        patient.setSurname(surname);
        patient.setName(name);
        patient.setMiddlename(middlename);
        patient.setIdGosp(idGosp);

        try {
            tbOperation.setData(ClientOperation.tcl.getOperations(patient.getIdGosp()));
        } catch (KmiacServerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (TException e) {
            // TODO Auto-generated catch block
            ClientOperation.conMan.reconnect(e);
        }
    }

    @Override
    public void currentOperationChanged() {
//        tbOperation.setData(model.getOperationsList());
        controller.setOperationComplicationsList();
        controller.setOperationPaymentFundsList();
        controller.setAnesthesiasList();
        
        controller.setAnesthesiaComplicationsList();
        controller.setAnesthesiaPaymentFundsList();
    }

    @Override
    public void operationComplicationChanged() {
        tbOperationComplications.setData(model.getOperationComplicationsList());
    }

    @Override
    public void operationPaymentFundChanged() {
        tbOperationPaymentFunds.setData(model.getOperationPaymentFundsList());
    }

    @Override
    public void currentAnesthesiaChanged() {
        tbAnesthesia.setData(model.getAnesthesiasList());
        controller.setAnesthesiaComplicationsList();
        controller.setAnesthesiaPaymentFundsList();
    }

    @Override
    public void anesthesiaComplicationChanged() {
        tbAnesthesiaComplications.setData(model.getAnesthesiaComplicationsList());
    }

    @Override
    public void anesthesiaPaymentFundChanged() {
        tbAnesthesiaPaymentFunds.setData(model.getAnesthesiaPaymentFundsList());
    }

    public JFrame getMainFrame() {
        return this;
    }

    public void removeOperationTableSelection() {
        tbOperation.getSelectionModel().clearSelection();
    }

    public void updateOperationTable() {
        tbOperation.setData(model.getOperationsList());
    }
}
