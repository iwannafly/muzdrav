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
    private Box hbOperationComplicationControls;
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
    private Box hbAnesthesiaComplicationControls;
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

    public MainFrame(final IController inController,
            final IOperationModel inModel) {
        this.controller = inController;
        this.model = inModel;
        model.registerOperationObserver(this);
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
//                1, "Вид стационара",
                5, "Наименование операции",
                7, "Дата операции", 8, "Время операции");
        tbOperation.setDateField(1);
        tbOperation.setTimeField(2);
//        tbOperation.setIntegerClassifierSelector(0, IntegerClassifiers.n_tip);
        tbOperation.setStringClassifierSelector(0, StringClassifiers.n_ak2);
        spOperation.setViewportView(tbOperation);
        vbOperationTableControls = Box.createVerticalBox();
        vbOperationTableControls.setAlignmentX(Component.CENTER_ALIGNMENT);
        hbOperationControl.add(vbOperationTableControls);
        
        btnOperationAdd = new JButton("");
        btnOperationAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOperationAdd.setMaximumSize(new Dimension(50, 50));
        btnOperationAdd.setPreferredSize(new Dimension(50, 50));
        btnOperationAdd.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1331789242_Add.png")));
        vbOperationTableControls.add(btnOperationAdd);
        
        btnOperationDelete = new JButton("");
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
        
        hbOperationComplicationControls = Box.createHorizontalBox();
        hbOperationComplicationControls.setBorder(new LineBorder(new Color(0, 0, 0)));
        hbOperationComplicationControls.setAlignmentY(0.5f);
        pOperation.add(hbOperationComplicationControls);
        
        spOperationComplications = new JScrollPane();
        hbOperationComplicationControls.add(spOperationComplications);
        
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
        hbOperationComplicationControls.add(vbOperationComplicationTableControls);
        
        btnOperationComplicationAdd = new JButton("");
        btnOperationComplicationAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnOperationComplicationAdd.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1331789242_Add.png")));
        btnOperationComplicationAdd.setPreferredSize(new Dimension(50, 50));
        btnOperationComplicationAdd.setMaximumSize(new Dimension(50, 50));
        vbOperationComplicationTableControls.add(btnOperationComplicationAdd);
        
        btnOperationComplicationDelete = new JButton("");
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
        btnOperationPaymentFundsAdd.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1331789242_Add.png")));
        btnOperationPaymentFundsAdd.setPreferredSize(new Dimension(50, 50));
        btnOperationPaymentFundsAdd.setMaximumSize(new Dimension(50, 50));
        vbOperationPaymentFundsTableControls.add(btnOperationPaymentFundsAdd);
        
        btnOperationPaymentFundsDelete = new JButton("");
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
        vbOperationPaymentFundsTableControls.add(btnOperationPaymentFundsUpdate);
        
        pAnesthesia = new JPanel();
        tbMain.addTab("Анестезия", null, pAnesthesia, null);
        pAnesthesia.setLayout(new BoxLayout(pAnesthesia, BoxLayout.Y_AXIS));

        hbAnesthesiaControl = Box.createHorizontalBox();
        hbAnesthesiaControl.setBorder(new LineBorder(new Color(0, 0, 0)));
        hbAnesthesiaControl.setAlignmentX(Component.LEFT_ALIGNMENT);
        hbAnesthesiaControl.setAlignmentY(Component.CENTER_ALIGNMENT);
        pAnesthesia.add(hbAnesthesiaControl);

        spAnesthesia = new JScrollPane();
        hbAnesthesiaControl.add(spAnesthesia);

        tbAnesthesia = new CustomTable<Anesthesia, Anesthesia._Fields>(true, true, Anesthesia.class,
//                1, "Вид стационара",
                6, "Наименование анестезии",
                8, "Дата операции", 9, "Время операции");
//        tbAnesthesia.setIntegerClassifierSelector(0, IntegerClassifiers.n_tip);
        tbAnesthesia.setIntegerClassifierSelector(0, IntegerClassifiers.n_aj0);
        tbAnesthesia.setDateField(1);
        tbAnesthesia.setTimeField(2);
        spAnesthesia.setViewportView(tbAnesthesia);
        vbAnesthesiaTableControls = Box.createVerticalBox();
        vbAnesthesiaTableControls.setAlignmentX(Component.CENTER_ALIGNMENT);
        hbAnesthesiaControl.add(vbAnesthesiaTableControls);

        btnAnesthesiaAdd = new JButton("");
        btnAnesthesiaAdd.setMaximumSize(new Dimension(50, 50));
        btnAnesthesiaAdd.setPreferredSize(new Dimension(50, 50));
        btnAnesthesiaAdd.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1331789242_Add.png")));
        vbAnesthesiaTableControls.add(btnAnesthesiaAdd);

        btnAnesthesiaDelete = new JButton("");
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
        vbAnesthesiaTableControls.add(btnAnesthesiaUpdate);

        hbAnesthesiaComplicationControls = Box.createHorizontalBox();
        hbAnesthesiaComplicationControls.setBorder(new LineBorder(new Color(0, 0, 0)));
        hbAnesthesiaComplicationControls.setAlignmentY(0.5f);
        hbAnesthesiaComplicationControls.setAlignmentX(0.0f);
        pAnesthesia.add(hbAnesthesiaComplicationControls);

        spAnesthesiaComplications = new JScrollPane();
        hbAnesthesiaComplicationControls.add(spAnesthesiaComplications);

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
        hbAnesthesiaComplicationControls.add(vbAnesthesiaComplicationTableControls);

        btnAnesthesiaComplicationAdd = new JButton("");
        btnAnesthesiaComplicationAdd.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1331789242_Add.png")));
        btnAnesthesiaComplicationAdd.setPreferredSize(new Dimension(50, 50));
        btnAnesthesiaComplicationAdd.setMaximumSize(new Dimension(50, 50));
        vbAnesthesiaComplicationTableControls.add(btnAnesthesiaComplicationAdd);

        btnAnesthesiaComplicationDelete = new JButton("");
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
        btnAnesthesiaPaymentFundsAdd.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1331789242_Add.png")));
        btnAnesthesiaPaymentFundsAdd.setPreferredSize(new Dimension(50, 50));
        btnAnesthesiaPaymentFundsAdd.setMaximumSize(new Dimension(50, 50));
        vbAnesthesiaPaymentFundsTableControls.add(btnAnesthesiaPaymentFundsAdd);

        btnAnesthesiaPaymentFundsDelete = new JButton("");
        btnAnesthesiaPaymentFundsDelete.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1331789259_Delete.png")));
        btnAnesthesiaPaymentFundsDelete.setPreferredSize(new Dimension(50, 50));
        btnAnesthesiaPaymentFundsDelete.setMaximumSize(new Dimension(50, 50));
        vbAnesthesiaPaymentFundsTableControls.add(btnAnesthesiaPaymentFundsDelete);

        btnAnesthesiaPaymentFundsUpdate = new JButton("");
        btnAnesthesiaPaymentFundsUpdate.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientOperation/resources/1341981970_Accept.png")));
        btnAnesthesiaPaymentFundsUpdate.setPreferredSize(new Dimension(50, 50));
        btnAnesthesiaPaymentFundsUpdate.setMaximumSize(new Dimension(50, 50));
        vbAnesthesiaPaymentFundsTableControls.add(btnAnesthesiaPaymentFundsUpdate);
    }

    public void onConnect() {
    }

    public void setControls() {
        setTbOperationsControls();
        setTbOperationComplicationControls();
        setTbOperationPaymentFundsControls();
        setTbAnesthesiasControls();
        setTbAnesthesiaComplicationsControls();
        setTbAnesthesiaPaymentFundsControls();
    }

    private void setTbOperationsControls() {
        tbOperation.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
            if (!e.getValueIsAdjusting()) {
                if (tbOperation.getSelectedItem() != null) {
                    controller.setCurrentOperation(tbOperation.getSelectedItem());
                    taOperationDescription.setText(tbOperation.getSelectedItem().getOpOper());
                    taOperationEpicris.setText(tbOperation.getSelectedItem().getPredP());
                    taOperationMaterial.setText(tbOperation.getSelectedItem().getMaterial());
                }
            }
            }
        });

        btnOperationAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            controller.addOperation();
            tbOperation.setData(model.getOperationsList());

            if (tbOperation.getRowCount() > 0) {
                tbOperation.setRowSelectionInterval(tbOperation.getRowCount() - 1,
                        tbOperation.getRowCount() - 1);
            }
            }
        });

        btnOperationDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tbOperation.getSelectedItem() != null) {
                    controller.deleteOperation();
                    tbOperation.setData(model.getOperationsList());
                }

                if (tbOperation.getRowCount() > 0) {
                    tbOperation.setRowSelectionInterval(tbOperation.getRowCount() - 1,
                        tbOperation.getRowCount() - 1);
                }
            }
        });

        btnOperationUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tbOperation.getSelectedItem() != null) {
                    tbOperation.getSelectedItem().setOpOper(taOperationDescription.getText());
                    tbOperation.getSelectedItem().setPredP(taOperationEpicris.getText());
                    tbOperation.getSelectedItem().setMaterial(taOperationMaterial.getText());
                    controller.updateOperation(tbOperation.getSelectedItem());
                    tbOperation.setData(model.getOperationsList());
                }
            }
        });
    }

    private void setTbOperationComplicationControls() {
        btnOperationComplicationAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tbOperation.getSelectedItem() != null) {
                    controller.addOperationComplication();

                    if (tbOperationComplications.getRowCount() > 0) {
                        tbOperationComplications.setRowSelectionInterval(
                                tbOperationComplications.getRowCount() - 1,
                                tbOperationComplications.getRowCount() - 1);
                    }
                }
            }
        });

        btnOperationComplicationDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tbOperationComplications.getSelectedItem() != null) {
                    controller.deleteOperationComplication(
                            tbOperationComplications.getSelectedItem()
                    );

                    if (tbOperationComplications.getRowCount() > 0) {
                        tbOperationComplications.setRowSelectionInterval(
                                tbOperationComplications.getRowCount() - 1,
                                tbOperationComplications.getRowCount() - 1);
                    }
                }
            }
        });

        btnOperationComplicationUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tbOperationComplications.getSelectedItem() != null) {
                    controller.updateOperationComplication(
                            tbOperationComplications.getSelectedItem()
                    );
                }
            }
        });
    }

    private void setTbOperationPaymentFundsControls() {
        btnOperationPaymentFundsAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tbOperation.getSelectedItem() != null) {
                    controller.addOperationPaymentFund();

                    if (tbOperationPaymentFunds.getRowCount() > 0) {
                        tbOperationPaymentFunds.setRowSelectionInterval(
                                tbOperationPaymentFunds.getRowCount() - 1,
                                tbOperationPaymentFunds.getRowCount() - 1);
                    }
                }
            }
        });

        btnOperationPaymentFundsDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tbOperationPaymentFunds.getSelectedItem() != null) {
                    controller.deleteOperationPaymentFund(
                            tbOperationPaymentFunds.getSelectedItem()
                    );

                    if (tbOperationPaymentFunds.getRowCount() > 0) {
                        tbOperationPaymentFunds.setRowSelectionInterval(
                                tbOperationPaymentFunds.getRowCount() - 1,
                                tbOperationPaymentFunds.getRowCount() - 1);
                    }
                }
            }
        });

        btnOperationPaymentFundsUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tbOperationPaymentFunds.getSelectedItem() != null) {
                    controller.updateOperationPaymentFund(
                            tbOperationPaymentFunds.getSelectedItem()
                    );
                }
            }
        });
    }

    private void setTbAnesthesiasControls() {
        tbAnesthesia.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    if (tbAnesthesia.getSelectedItem() != null) {
                        controller.setCurrentAnesthesia(tbAnesthesia.getSelectedItem());
                    }
                }
            }
        });

        btnAnesthesiaAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
              if (tbOperation.getSelectedItem() != null){
                  controller.addAnesthesia();
                  tbAnesthesia.setData(model.getAnesthesiasList());

                  if (tbAnesthesia.getRowCount() > 0) {
                      tbAnesthesia.setRowSelectionInterval(tbAnesthesia.getRowCount() - 1,
                              tbAnesthesia.getRowCount() - 1);
                  }
              }
            }
        });

        btnAnesthesiaDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tbAnesthesia.getSelectedItem() != null) {
                    controller.deleteAnesthesia();
                    tbAnesthesia.setData(model.getAnesthesiasList());

                    if (tbAnesthesia.getRowCount() > 0) {
                        tbAnesthesia.setRowSelectionInterval(
                                tbAnesthesia.getRowCount() - 1,
                                tbAnesthesia.getRowCount() - 1);
                    }
                }
            }
        });

        btnAnesthesiaUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tbAnesthesia.getSelectedItem() != null) {
                  controller.updateAnesthesia();
                  tbAnesthesia.setData(model.getAnesthesiasList());
                }
            }
        });
    }

    private void setTbAnesthesiaComplicationsControls() {
        btnAnesthesiaComplicationAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tbAnesthesia.getSelectedItem() != null) {
                    controller.addAnesthesiaComplication();

                    if (tbAnesthesiaComplications.getRowCount() > 0) {
                        tbAnesthesiaComplications.setRowSelectionInterval(
                                tbAnesthesiaComplications.getRowCount() - 1,
                                tbAnesthesiaComplications.getRowCount() - 1);
                    }
                }
            }
        });

        btnAnesthesiaComplicationDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tbAnesthesiaComplications.getSelectedItem() != null) {
                    controller.deleteAnesthesiaComplication(
                            tbAnesthesiaComplications.getSelectedItem()
                    );

                    if (tbAnesthesiaComplications.getRowCount() > 0) {
                        tbAnesthesiaComplications.setRowSelectionInterval(
                                tbAnesthesiaComplications.getRowCount() - 1,
                                tbAnesthesiaComplications.getRowCount() - 1);
                    }
                }
            }
        });

        btnAnesthesiaComplicationUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tbAnesthesiaComplications.getSelectedItem() != null) {
                    controller.updateAnesthesiaComplication(
                            tbAnesthesiaComplications.getSelectedItem()
                    );
                }
            }
        });
    }

    private void setTbAnesthesiaPaymentFundsControls() {
        btnAnesthesiaPaymentFundsAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tbAnesthesia.getSelectedItem() != null){
                    controller.addAnesthesiaPaymentFund();

                    if (tbAnesthesiaPaymentFunds.getRowCount() > 0) {
                        tbAnesthesiaPaymentFunds.setRowSelectionInterval(
                                tbAnesthesiaPaymentFunds.getRowCount() - 1,
                                tbAnesthesiaPaymentFunds.getRowCount() - 1);
                    }
                }
            }
        });

        btnAnesthesiaPaymentFundsDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tbAnesthesiaPaymentFunds.getSelectedItem() != null) {
                    controller.deleteAnesthesiaPaymentFund(
                            tbAnesthesiaPaymentFunds.getSelectedItem()
                    );

                    if (tbAnesthesiaPaymentFunds.getRowCount() > 0) {
                        tbAnesthesiaPaymentFunds.setRowSelectionInterval(
                                tbAnesthesiaPaymentFunds.getRowCount() - 1,
                                tbAnesthesiaPaymentFunds.getRowCount() - 1);
                    }
                }
            }
        });

        btnAnesthesiaPaymentFundsUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (tbAnesthesiaPaymentFunds.getSelectedItem() != null) {
                    controller.updateAnesthesiaPaymentFund(
                            tbAnesthesiaPaymentFunds.getSelectedItem()
                    );
                }
            }
        });
    }

    @Override
    public void currentOperationChanged() {
//        tbOperation.setData(model.getOperationsList());
        controller.setOperationComplicationsList();
        controller.setOperationPaymentFundsList();
        controller.setAnesthesiasList();

        if (model.getAnesthesiasList() != null) {
            tbAnesthesia.setData(model.getAnesthesiasList());
        }
//        controller.setAnesthesiaComplicationsList();
//        controller.setAnesthesiaPaymentFundsList();
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
//        tbAnesthesia.setData(model.getAnesthesiasList());
        if (model.getAnesthesiasList() != null) {
            controller.setAnesthesiaComplicationsList();
            controller.setAnesthesiaPaymentFundsList();
        }
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
