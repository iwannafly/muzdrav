package ru.nkz.ivcgzo.clientLab;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;

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
import ru.nkz.ivcgzo.thriftLab.Patient;
import ru.nkz.ivcgzo.thriftLab.Pisl;
import ru.nkz.ivcgzo.thriftLab.PokazMet;
import ru.nkz.ivcgzo.thriftLab.PrezD;
import ru.nkz.ivcgzo.thriftLab.PrezL;

import javax.swing.JButton;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;

import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.thrift.TException;
import javax.swing.JComboBox;
import javax.swing.JTextArea;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 4157141004019237277L;
    private UserAuthInfo doctorAuthInfo;
    private JPanel pLabAndDiagIssled;
    private JScrollPane spIssled;
    private JLabel lblIssled;
    private JLabel lblLab;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxLabs;
    private JLabel lblOrgAndSystem;
    private ThriftStringClassifierCombobox<StringClassifier> cbxOrgAndSystem;
    private JButton btnPrintIssled;
    private JLabel lblCabinet;
    private CustomTextField tfCabinet;
    private CustomTable<PokazMet, PokazMet._Fields> tbIssled;
    private JTabbedPane tabbedPane;
    private JPanel pNaprav;
    private JScrollPane spObosn;
    private JLabel lblNapravFrom;
    private JComboBox<String> cbxOrganizationFrom;
    private JButton btnPrintNaprav;
    private JLabel lblVidStac;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxVidStac;
    private JLabel lblObosn;
    private JLabel lblNapravTo;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxOrganzationTo;
    private JTextArea taObosn;
    private Patient patient;
    private List<IntegerClassifier> vidIssled;

    public MainFrame(final UserAuthInfo authInfo) {
        doctorAuthInfo = authInfo;
        initialization();
    }

    private void initialization() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(980, 600));
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));

        addMainTabbedPane();
        addIssledTab();
        addNapravTab();
    }

    private void addMainTabbedPane() {
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane);
    }

    public final void fillPatient(final int id, final String surname,
            final String name, final String middlename) {
        patient = new Patient();
        patient.setId(id);
        patient.setSurname(surname);
        patient.setName(name);
        patient.setMiddlename(middlename);
        setTitle(String.format("%s %s %s", surname, name, middlename));
    }

    private void addIssledTab() {
        pLabAndDiagIssled = new JPanel();
        tabbedPane.addTab("Направление на исследование", null, pLabAndDiagIssled, null);

        setIssledComboBoxes();
        setIssledTable();
        setIssledCabinetTextField();
        setIssledButtons();

        setIssledPanelGroupLayout();
    }

    private void setIssledComboBoxes() {
        lblLab = new JLabel("Лаборатория");
        cbxLabs = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        cbxLabs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    if (cbxLabs.getSelectedItem() != null) {
                        cbxOrgAndSystem.setData(
                                ClientLab.tcl.getOrgAndSys(cbxLabs.getSelectedItem().pcod));
                    }
                } catch (KmiacServerException e1) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Ошибка на сервере", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (TException e1) {
                    ClientLab.conMan.reconnect(e1);
                }
            }
        });
        lblOrgAndSystem = new JLabel("Органы и системы");

        cbxOrgAndSystem = new ThriftStringClassifierCombobox<StringClassifier>(true);
        cbxOrgAndSystem.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent arg0) {
                try {
                    if (cbxOrgAndSystem.getSelectedItem() != null) {
                        tbIssled.setData(
                                ClientLab.tcl.getPokazMet(
                                        cbxOrgAndSystem.getSelectedItem().getPcod(),
                                        cbxLabs.getSelectedItem().getPcod())
                        );
                    }
                } catch (KmiacServerException e) {
                    JOptionPane.showMessageDialog(MainFrame.this,
                            "Ошибка на сервере", "Ошибка", JOptionPane.ERROR_MESSAGE);
                } catch (TException e1) {
                    ClientLab.conMan.reconnect(e1);
                }
            }
        });
    }

    private void setIssledTable() {
        spIssled = new JScrollPane();

        tbIssled = new CustomTable<>(false, true,
                PokazMet.class, 0, "Код", 1, "Наименование", 2, "Выбор");
        tbIssled.setEditableFields(true, 2);
        tbIssled.setFillsViewportHeight(true);
        spIssled.setViewportView(tbIssled);
        lblIssled = new JLabel("Исследования");
    }

    private void setIssledCabinetTextField() {
        lblCabinet = new JLabel("Кабинет");
        tfCabinet = new CustomTextField();
        tfCabinet.setColumns(10);
    }

    private void setIssledButtons() {
        btnPrintIssled = new JButton("Печать");
        btnPrintIssled.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (cbxOrgAndSystem.getSelectedItem() != null) {
                    try {
                        Pisl pisl = new Pisl();
                        fillPislFields(pisl);
                        List<String> selItems = new ArrayList<>();
                        // piece of shit!!! Переписать!
                        for (IntegerClassifier vid : vidIssled) {
                            if (vid.pcod == cbxLabs.getSelectedPcod()) {
                                for (PokazMet pokazMet : tbIssled.getData()) {
                                    if (pokazMet.vybor) {
                                        if (vid.name.equals("Л")) {
                                            PrezL prezl = new PrezL();
                                            fillPrezLFields(pisl, pokazMet, prezl);
                                        } else {
                                            PrezD prezd = new PrezD();
                                            fillPrezDFields(pisl, pokazMet, prezd);
                                        }
                                        selItems.add(pokazMet.getPcod());
                                    }
                                }
                            }
                        }
                        JOptionPane.showMessageDialog(MainFrame.this,
                                "Исследования успешно сохранены!",
                                "Запись исследования", JOptionPane.INFORMATION_MESSAGE);
                    } catch (KmiacServerException e1) {
                        JOptionPane.showMessageDialog(MainFrame.this,
                            "Ошибка записи исследования! Информация может быть не сохранена!",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                        e1.printStackTrace();
                    } catch (TException e1) {
                        JOptionPane.showMessageDialog(MainFrame.this,
                            "Ошибка записи исследования! Информация может быть не сохранена!",
                            "Ошибка", JOptionPane.ERROR_MESSAGE);
                        ClientLab.conMan.reconnect(e1);
                        e1.printStackTrace();
                    }

                }
            }
        });
    }

    private void fillPislFields(final Pisl pisl) throws KmiacServerException, TException {
        pisl.setNpasp(patient.getId());
        pisl.setPcisl(cbxOrgAndSystem.getSelectedPcod());
        pisl.setNapravl(2);
        pisl.setNaprotd(doctorAuthInfo.getCpodr());
        pisl.setDatan(System.currentTimeMillis());
        pisl.setVrach(doctorAuthInfo.getPcod());
        pisl.setDataz(System.currentTimeMillis());
//        pisl.setPvizit_id(tblPos.getSelectedItem().getId_obr());
        pisl.setNisl(ClientLab.tcl.AddPisl(pisl));
    }

    private void fillPrezLFields(final Pisl pisl, final PokazMet pokazMet,
            final PrezL prezl) throws KmiacServerException, TException {
        prezl.setNpasp(pisl.getNpasp());
        prezl.setNisl(pisl.getNisl());
        prezl.setCpok(pokazMet.pcod);
        prezl.setId(ClientLab.tcl.AddPrezl(prezl));
    }

    private void fillPrezDFields(final Pisl pisl, final PokazMet pokazMet,
            final PrezD prezd) throws KmiacServerException, TException {
        prezd.setNpasp(pisl.getNpasp());
        prezd.setNisl(pisl.getNisl());
        prezd.setKodisl(pokazMet.pcod);
        prezd.setId(ClientLab.tcl.AddPrezd(prezd));
    }

    private void addNapravTab() {
        pNaprav = new JPanel();
        tabbedPane.addTab("Направление", null, pNaprav, null);

        setNapravTextArea();
        setNapravOrganizationFromComboBoxes();
        setNapravVidStacComboBoxes();
        setNapravOrganizationToComboBoxes();
        setNapravButtons();

        setNapravPanelGroupLayout();
    }

    private void setNapravOrganizationFromComboBoxes() {
        lblNapravFrom = new JLabel("на");
        cbxOrganizationFrom = new JComboBox<String>();
        cbxOrganizationFrom.setModel(new DefaultComboBoxModel<>(
                new String[] {"госпитализацию", "консультацию", "обследование"}));
        cbxOrganizationFrom.setSelectedIndex(0);
        cbxOrganizationFrom.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                lblNapravTo.setVisible(true);
                cbxOrganzationTo.setVisible(true);
                if (cbxOrganizationFrom.getSelectedIndex() != 0) {
                    cbxVidStac.setVisible(false);
                    lblVidStac.setVisible(false);
                    try {
                        cbxOrganzationTo.setData(ClientLab.tcl.getPoliclinic());
                    } catch (KmiacServerException e1) {
                        e1.printStackTrace();
                    } catch (TException e1) {
                        ClientLab.conMan.reconnect(e1);
                        e1.printStackTrace();
                    }
                } else {
                    cbxVidStac.setSelectedIndex(0);
                    cbxVidStac.setVisible(true);
                    lblVidStac.setVisible(true);
                }
            }
        });
    }

    private void setNapravVidStacComboBoxes() {
        lblVidStac = new JLabel("Вид стационара");
        cbxVidStac = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        cbxVidStac.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if (cbxVidStac.getSelectedItem() != null) {
                    if ((cbxVidStac.getSelectedPcod() == 1)
                            || (cbxVidStac.getSelectedPcod() == 2)) {
                        lblNapravTo.setVisible(true);
                        cbxOrganzationTo.setVisible(true);
                        try {
                            cbxOrganzationTo.setData(ClientLab.tcl.getLpu());
                        } catch (KmiacServerException e1) {
                            e1.printStackTrace();
                        } catch (TException e1) {
                            e1.printStackTrace();
                        }
                    }
                    if ((cbxVidStac.getSelectedPcod() == 3)
                            || (cbxVidStac.getSelectedPcod() == 4)) {
                        lblNapravTo.setVisible(false);
                        cbxOrganzationTo.setVisible(false);
                    }
                }
            }
        });
    }

    private void setNapravOrganizationToComboBoxes() {
        lblNapravTo = new JLabel("Куда");
        cbxOrganzationTo = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
    }

    private void setNapravTextArea() {
        lblObosn = new JLabel("Обоснование для направления");
        spObosn = new JScrollPane();
        taObosn = new JTextArea();
        spObosn.setViewportView(taObosn);
    }

    private void setNapravButtons() {
        btnPrintNaprav = new JButton("Печать");
        btnPrintNaprav.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            }
        });
    }

    public final void onConnect() {
        try {
            cbxLabs.setData(ClientLab.tcl.getLabs(doctorAuthInfo.getClpu()));
            cbxVidStac.setData(ClientLab.tcl.getStacionarTypes());
            vidIssled = ClientLab.tcl.getVidIssled();
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
            ClientLab.conMan.reconnect(e);
        }
    }

    private void setIssledPanelGroupLayout() {
        GroupLayout glPLabAndDiagIssled = new GroupLayout(pLabAndDiagIssled);
        glPLabAndDiagIssled.setHorizontalGroup(
            glPLabAndDiagIssled.createParallelGroup(Alignment.TRAILING)
                .addGap(0, 577, Short.MAX_VALUE)
                .addGroup(glPLabAndDiagIssled.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(glPLabAndDiagIssled.createParallelGroup(Alignment.LEADING)
                        .addComponent(spIssled, GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                        .addComponent(lblIssled, GroupLayout.DEFAULT_SIZE, 557, Short.MAX_VALUE)
                        .addComponent(btnPrintIssled, GroupLayout.PREFERRED_SIZE,
                                87, GroupLayout.PREFERRED_SIZE)
                        .addGroup(glPLabAndDiagIssled.createSequentialGroup()
                            .addComponent(lblCabinet, GroupLayout.PREFERRED_SIZE,
                                    65, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(tfCabinet, GroupLayout.PREFERRED_SIZE,
                                    70, GroupLayout.PREFERRED_SIZE))
                        .addGroup(glPLabAndDiagIssled.createSequentialGroup()
                            .addGroup(glPLabAndDiagIssled.createParallelGroup(Alignment.LEADING)
                                .addComponent(lblLab, GroupLayout.PREFERRED_SIZE, 96,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(lblOrgAndSystem, GroupLayout.PREFERRED_SIZE,
                                        135, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addGroup(glPLabAndDiagIssled.createParallelGroup(Alignment.LEADING)
                                .addComponent(cbxOrgAndSystem, GroupLayout.DEFAULT_SIZE,
                                        574, Short.MAX_VALUE)
                                .addComponent(cbxLabs, 0, 457, Short.MAX_VALUE))))
                    .addContainerGap())
        );
        glPLabAndDiagIssled.setVerticalGroup(
            glPLabAndDiagIssled.createParallelGroup(Alignment.LEADING)
                .addGap(0, 523, Short.MAX_VALUE)
                .addGroup(glPLabAndDiagIssled.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(glPLabAndDiagIssled.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblLab)
                        .addComponent(cbxLabs, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPLabAndDiagIssled.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblOrgAndSystem)
                        .addComponent(cbxOrgAndSystem, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(lblIssled)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(spIssled, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPLabAndDiagIssled.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblCabinet)
                        .addComponent(tfCabinet, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnPrintIssled)
                    .addContainerGap())
        );
        pLabAndDiagIssled.setLayout(glPLabAndDiagIssled);
    }

    private void setNapravPanelGroupLayout() {
        GroupLayout glPNaprav = new GroupLayout(pNaprav);
        glPNaprav.setHorizontalGroup(
            glPNaprav.createParallelGroup(Alignment.LEADING)
                .addGroup(glPNaprav.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(glPNaprav.createParallelGroup(Alignment.LEADING)
                        .addComponent(btnPrintNaprav, GroupLayout.PREFERRED_SIZE,
                                87, GroupLayout.PREFERRED_SIZE)
                        .addGroup(glPNaprav.createSequentialGroup()
                            .addComponent(lblNapravTo, GroupLayout.PREFERRED_SIZE,
                                    58, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(cbxOrganzationTo, GroupLayout.DEFAULT_SIZE,
                                    607, Short.MAX_VALUE))
                        .addComponent(lblObosn, GroupLayout.PREFERRED_SIZE,
                                557, GroupLayout.PREFERRED_SIZE)
                        .addGroup(glPNaprav.createSequentialGroup()
                            .addComponent(spObosn, GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
                            .addGap(3))
                        .addGroup(glPNaprav.createSequentialGroup()
                            .addGroup(glPNaprav.createParallelGroup(Alignment.LEADING)
                                .addGroup(glPNaprav.createSequentialGroup()
                                    .addComponent(lblVidStac, GroupLayout.PREFERRED_SIZE,
                                            81, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(cbxVidStac, GroupLayout.DEFAULT_SIZE,
                                            584, Short.MAX_VALUE))
                                .addGroup(glPNaprav.createSequentialGroup()
                                    .addComponent(lblNapravFrom, GroupLayout.PREFERRED_SIZE,
                                            32, GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(ComponentPlacement.RELATED)
                                    .addComponent(cbxOrganizationFrom, 0, 633, Short.MAX_VALUE)))
                            .addGap(0)))
                    .addContainerGap())
        );
        glPNaprav.setVerticalGroup(
            glPNaprav.createParallelGroup(Alignment.LEADING)
                .addGroup(glPNaprav.createSequentialGroup()
                    .addGap(20)
                    .addGroup(glPNaprav.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblNapravFrom)
                        .addComponent(cbxOrganizationFrom, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addGroup(glPNaprav.createParallelGroup(Alignment.BASELINE)
                        .addComponent(lblVidStac)
                        .addComponent(cbxVidStac, GroupLayout.PREFERRED_SIZE,
                                GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGroup(glPNaprav.createParallelGroup(Alignment.LEADING)
                        .addGroup(glPNaprav.createSequentialGroup()
                            .addGap(125)
                            .addComponent(lblNapravTo))
                        .addGroup(glPNaprav.createSequentialGroup()
                            .addGap(122)
                            .addComponent(cbxOrganzationTo, GroupLayout.PREFERRED_SIZE,
                                    GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                    .addGap(11)
                    .addComponent(lblObosn)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(spObosn, GroupLayout.PREFERRED_SIZE, 183,
                            GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(btnPrintNaprav)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pNaprav.setLayout(glPNaprav);
    }
}
