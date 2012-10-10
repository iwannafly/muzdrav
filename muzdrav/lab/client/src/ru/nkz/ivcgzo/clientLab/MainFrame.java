package ru.nkz.ivcgzo.clientLab;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JScrollPane;
import javax.swing.JLabel;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftLab.PokazMet;

import javax.swing.JButton;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;

import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.thrift.TException;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 4157141004019237277L;
    private UserAuthInfo doctorAuthInfo;
    private JPanel pMain;
    private JScrollPane spIssled;
    private JLabel lblIssled;
    private JLabel lblLab;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxLabs;
    private JLabel lblOrgAndSystem;
    private ThriftStringClassifierCombobox<StringClassifier> cbxOrgAndSystem;
    private JButton btnPrint;
    private JLabel lblCabinet;
    private CustomTextField tfCabinet;
    private CustomTable<PokazMet, PokazMet._Fields> tbIssled;

    public MainFrame(UserAuthInfo authInfo) {
        doctorAuthInfo = authInfo;
        initialization();
    }

    private void initialization() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(980, 600));
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        pMain = new JPanel();
        getContentPane().add(pMain);

        setComboBoxes();
        setIssledTable();
        setCabinetTextField();
        setButtons();

        setMainPanelGroupLayout();

    }

    private void setComboBoxes() {
        lblLab = new JLabel("Лаборатория");
        cbxLabs = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        cbxLabs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (cbxLabs.getSelectedItem() != null)
                        cbxOrgAndSystem.setData(ClientLab.tcl.getOrgAndSys(cbxLabs.getSelectedItem().pcod));
                } catch (KmiacServerException e1) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Ошибка на сервере", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (TException e1) {
                    ClientLab.conMan.reconnect(e1);
                }
            }
        });
        lblOrgAndSystem = new JLabel("Органы и системы");

        cbxOrgAndSystem = new ThriftStringClassifierCombobox<StringClassifier>(true);
        cbxOrgAndSystem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                try {
                    if (cbxOrgAndSystem.getSelectedItem()!= null)
                        tbIssled.setData(ClientLab.tcl.getPokazMet(cbxOrgAndSystem.getSelectedItem().getPcod(),
                                cbxLabs.getSelectedItem().getPcod()));
                } catch (KmiacServerException e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Ошибка на сервере", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (TException e1) {
                    ClientLab.conMan.reconnect(e1);
                }
            }
        });
    }

    private void setIssledTable() {
        spIssled = new JScrollPane();

        tbIssled = new CustomTable<>(false, true, PokazMet.class, 0, "Код", 1, "Наименование", 2, "Выбор");
        tbIssled.setEditableFields(true, 2);
        tbIssled.setFillsViewportHeight(true);
        spIssled.setViewportView(tbIssled);
        lblIssled = new JLabel("Исследования");
    }

    private void setCabinetTextField() {
        lblCabinet = new JLabel("Кабинет");
        tfCabinet = new CustomTextField();
        tfCabinet.setColumns(10);
    }

    private void setButtons() {
        btnPrint = new JButton("Печать");
    }

    public void onConnect() {
        try {
            cbxLabs.setData(ClientLab.tcl.getLabs(doctorAuthInfo.getClpu()));
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
            ClientLab.conMan.reconnect(e);
        }
    }

    private void setMainPanelGroupLayout() {
        GroupLayout glPMain = new GroupLayout(pMain);
        glPMain.setHorizontalGroup(
            glPMain.createParallelGroup(Alignment.TRAILING)
                .addGap(0, 577, Short.MAX_VALUE)
                .addGroup(glPMain.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(glPMain.createParallelGroup(Alignment.LEADING)
                        .addComponent(spIssled, GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                        .addComponent(lblIssled, GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                        .addComponent(btnPrint, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
                        .addGroup(glPMain.createSequentialGroup()
                            .addComponent(lblCabinet, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(tfCabinet, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE))
                        .addGroup(glPMain.createSequentialGroup()
                            .addGroup(glPMain.createParallelGroup(Alignment.LEADING)
                                .addComponent(lblLab, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblOrgAndSystem, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(glPMain.createParallelGroup(Alignment.LEADING)
                                .addComponent(cbxOrgAndSystem, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
                                .addComponent(cbxLabs, 0, 457, Short.MAX_VALUE))))
                    .addContainerGap())
        );
        glPMain.setVerticalGroup(
            glPMain.createParallelGroup(Alignment.LEADING)
                .addGap(0, 523, Short.MAX_VALUE)
                .addGroup(glPMain.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(glPMain.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblLab)
                        .addComponent(cbxLabs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPMain.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblOrgAndSystem)
                        .addComponent(cbxOrgAndSystem, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblIssled)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(spIssled, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPMain.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblCabinet)
                        .addComponent(tfCabinet, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnPrint)
                    .addContainerGap())
        );
        pMain.setLayout(glPMain);
    }
}
