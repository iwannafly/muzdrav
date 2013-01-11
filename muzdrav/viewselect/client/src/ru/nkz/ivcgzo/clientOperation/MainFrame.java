package ru.nkz.ivcgzo.clientOperation;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.Box;

import java.awt.BorderLayout;
import javax.swing.JTabbedPane;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftOperation.Anesthesia;
import ru.nkz.ivcgzo.thriftOperation.AnesthesiaComplication;
import ru.nkz.ivcgzo.thriftOperation.AnesthesiaPaymentFund;
import ru.nkz.ivcgzo.thriftOperation.Operation;
import ru.nkz.ivcgzo.thriftOperation.OperationComplication;
import ru.nkz.ivcgzo.thriftOperation.OperationPaymentFund;

import java.awt.Component;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;

import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextArea;


public class MainFrame extends JFrame {

    private static final long serialVersionUID = -3201408430800941030L;
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

    public MainFrame() {
        initialization();
    }

    private void initialization() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(980, 600));
        getContentPane().setLayout(new BorderLayout(0, 0));
        
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
                1, "Вид стационара", 5, "Код операции", 6, "Наименование операции",
                8, "Дата операции", 9, "Время операции");
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
        spOperationDescription.setViewportView(taOperationDescription);
        
        lblOperationEpicris = new JLabel("Предоперационный эпикриз");
        lblOperationEpicris.setAlignmentX(0.5f);
        verticalBox.add(lblOperationEpicris);
        
        spOperationEpicris = new JScrollPane();
        verticalBox.add(spOperationEpicris);
        
        taOperationEpicris = new JTextArea();
        spOperationEpicris.setViewportView(taOperationEpicris);
        
        lblOperationMaterial = new JLabel("Шовный материал");
        verticalBox.add(lblOperationMaterial);
        lblOperationMaterial.setAlignmentX(0.5f);
        
        spOperationMaterial = new JScrollPane();
        verticalBox.add(spOperationMaterial);
        
        taOperationMaterial = new JTextArea();
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
                        3, "Код осложнения", 2, "Имя осложнения", 4, "Дата"
                );
        spOperationComplications.setViewportView(tbOperationComplications);
        
        vbOperationComplicationTableControls = Box.createVerticalBox();
        vbOperationComplicationTableControls.setAlignmentX(0.5f);
        hbOperationComlicationControls.add(vbOperationComplicationTableControls);
        
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
                1, "Вид стационара", 6, "Код анастезии", 7, "Наименование анастезии",
                8, "Дата операции", 9, "Время операции");
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
                        3, "Код осложнения", 2, "Имя осложнения", 4, "Дата"
                );
        spAnesthesiaComplications.setViewportView(tbAnesthesiaComplications);

        vbAnesthesiaComplicationTableControls = Box.createVerticalBox();
        vbAnesthesiaComplicationTableControls.setAlignmentX(0.5f);
        hbAnesthesiaComlicationControls.add(vbAnesthesiaComplicationTableControls);

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
        // TODO Auto-generated method stub
    }
}
