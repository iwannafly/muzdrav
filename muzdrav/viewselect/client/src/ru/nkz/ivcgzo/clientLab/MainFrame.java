package ru.nkz.ivcgzo.clientLab;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import ru.nkz.ivcgzo.thriftLab.Gosp;
import ru.nkz.ivcgzo.thriftLab.Isl;
import ru.nkz.ivcgzo.thriftLab.Napr;
import ru.nkz.ivcgzo.thriftLab.Patient;
import ru.nkz.ivcgzo.thriftLab.Pisl;
import ru.nkz.ivcgzo.thriftLab.PokazMet;
import ru.nkz.ivcgzo.thriftLab.PrezD;
import ru.nkz.ivcgzo.thriftLab.PrezL;

import javax.swing.JButton;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;

import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import org.apache.thrift.TException;
import javax.swing.JComboBox;
import javax.swing.JTextArea;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;

import java.awt.FlowLayout;
import javax.swing.JSplitPane;
import javax.swing.JTree;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 4157141004019237277L;
    private static final String LINE_SEP = System.lineSeparator();
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
    private JLabel lblObosn;
    private JLabel lblNapravTo;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> cbxOrganzationTo;
    private JTextArea taObosn;
    private Patient patient;
    private List<IntegerClassifier> vidIssled;
    private JPanel pResult;
    private JPanel pResultDateSelector;
    private CustomDateEditor cdeResultDateFrom;
    private JLabel lblPeriod;
    private JLabel lblResultDash;
    private CustomDateEditor cdeResultDateTo;
    private JButton btnDatePeriodSelect;
    private JSplitPane splitPane;
    private JTree trResult;
    private JTextArea taResultText;
    private StringBuilder sbResulText;

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
        addResultTab();
    }

    private void addMainTabbedPane() {
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        getContentPane().add(tabbedPane);
    }

    public final void fillPatient(final int id, final String surname,
            final String name, final String middlename, final int idGosp) {
        patient = new Patient();
        patient.setId(id);
        patient.setSurname(surname);
        patient.setName(name);
        patient.setMiddlename(middlename);
        patient.setIdGosp(idGosp);
        updateTree(patient.getId());
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
        pisl.setKodotd(cbxLabs.getSelectedPcod());
        pisl.setNapravl(2);
        pisl.setNaprotd(doctorAuthInfo.getCpodr());
        pisl.setDatan(System.currentTimeMillis());
        pisl.setVrach(doctorAuthInfo.getPcod());
        pisl.setDataz(System.currentTimeMillis());
        pisl.setIdGosp(patient.idGosp);
//        pisl.setPvizit_id(tblPos.getSelectedItem().getId_obr());
        pisl.setNisl(ClientLab.tcl.addPisl(pisl));
    }

    private void fillPrezLFields(final Pisl pisl, final PokazMet pokazMet,
            final PrezL prezl) throws KmiacServerException, TException {
        prezl.setNpasp(pisl.getNpasp());
        prezl.setNisl(pisl.getNisl());
        prezl.setCpok(pokazMet.pcod);
        prezl.setId(ClientLab.tcl.addPrezl(prezl));
    }

    private void fillPrezDFields(final Pisl pisl, final PokazMet pokazMet,
            final PrezD prezd) throws KmiacServerException, TException {
        prezd.setNpasp(pisl.getNpasp());
        prezd.setNisl(pisl.getNisl());
        prezd.setKodisl(pokazMet.pcod);
        prezd.setId(ClientLab.tcl.addPrezd(prezd));
    }

    private void addNapravTab() {
        pNaprav = new JPanel();
        tabbedPane.addTab("Направление", null, pNaprav, null);
        
        setNapravTextArea();
        setNapravOrganizationFromComboBoxes();
        setNapravOrganizationToComboBoxes();
        setNapravButtons();

        setNapravPanelGroupLayout();
    }

    private void setNapravOrganizationFromComboBoxes() {
        lblNapravFrom = new JLabel("на");
        cbxOrganizationFrom = new JComboBox<String>();
        cbxOrganizationFrom.setModel(new DefaultComboBoxModel<>(
//                new String[] {"госпитализацию", "консультацию", "обследование"}));
                  new String[] {"консультацию", "обследование"}));
        cbxOrganizationFrom.setSelectedIndex(0);
        cbxOrganizationFrom.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                lblNapravTo.setVisible(true);
                cbxOrganzationTo.setVisible(true);
                try {
                    cbxOrganzationTo.setData(ClientLab.tcl.getPoliclinic());
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    ClientLab.conMan.reconnect(e1);
                    e1.printStackTrace();
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
                try {
                    Napr napr = new Napr();
//                    pnapr.setIdpvizit(tblPos.getSelectedItem().getId_obr());
                    napr.setVidDoc(3);
                    napr.setText(taObosn.getText());
                    napr.setId(ClientLab.tcl.addNapr(napr));
                    napr.setIdGosp(patient.getIdGosp());
//                    NaprKons naprkons = new NaprKons();
//                    naprkons.setUserId(MainForm.authInfo.getUser_id());
//                    naprkons.setNpasp(Vvod.zapVr.getNpasp());
//                    naprkons.setObosnov(tbKonsObosnov.getText());
//                    if (cmbKonsMesto.getSelectedItem() != null)
//                        naprkons.setCpol(cmbKonsMesto.getSelectedItem().getName());
//                    naprkons.setNazv(cmbKonsVidNapr.getSelectedItem().toString());
//                    naprkons.setCdol(MainForm.authInfo.getCdol());
//                    naprkons.setPvizitId(tblPos.getSelectedItem().getId_obr());
//                    naprkons.setCpodr_name(MainForm.authInfo.getCpodr_name());
//                    naprkons.setClpu_name(MainForm.authInfo.getClpu_name());
//                    String servPath = MainForm.tcl.printNaprKons(naprkons);
//                    String cliPath = File.createTempFile("napk", ".htm").getAbsolutePath();
//                    MainForm.conMan.transferFileFromServer(servPath, cliPath);
//                    MainForm.conMan.openFileInEditor(cliPath, false);
                } catch (TException e1) {
                    e1.printStackTrace();
                    ClientLab.conMan.reconnect(e1);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    private void addResultTab() {
        pResult = new JPanel();
        pResult.setLayout(new BoxLayout(pResult, BoxLayout.Y_AXIS));
        tabbedPane.addTab("Результаты исследований", null, pResult, null);

        addDateSelectPanel();
        addResultTreePanel();
    }

    private void addDateSelectPanel() {
        pResultDateSelector = new JPanel();
        pResultDateSelector.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        pResultDateSelector.setAlignmentX(Component.LEFT_ALIGNMENT);
        pResult.add(pResultDateSelector);
        
        lblPeriod = new JLabel("Период ");
        pResultDateSelector.add(lblPeriod);
        
        cdeResultDateFrom = new CustomDateEditor();
        cdeResultDateFrom.setDate("01.01.2012");
        cdeResultDateFrom.setColumns(10);
        pResultDateSelector.add(cdeResultDateFrom);
        
        lblResultDash = new JLabel("-");
        pResultDateSelector.add(lblResultDash);
        
        cdeResultDateTo = new CustomDateEditor();
        cdeResultDateTo.setDate("31.12.2012");
        cdeResultDateTo.setColumns(10);
        pResultDateSelector.add(cdeResultDateTo);
        
        btnDatePeriodSelect = new JButton("OK");
        btnDatePeriodSelect.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateTree(patient.getId());
            }
        });
        pResultDateSelector.add(btnDatePeriodSelect);

    }

    public void updateTree(int npasp) {
//        try {
//            List<Gosp> gosp = ClientLab.tcl.getGospList(patient.getId(), new Date().getTime(), new Date().getTime());
            trResult.setModel(new DefaultTreeModel(createNodes()));
//        } catch (KmiacServerException e) {
//            e.printStackTrace();
//        } catch (TException e) {
//            MainForm.conMan.reconnect(e);
//        }
    }

    private DefaultMutableTreeNode createNodes() {
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Корень");
        DefaultMutableTreeNode gospInfo = new DefaultMutableTreeNode("Случаи госпитализации");
        root.add(gospInfo);
        
        try {
            for (Gosp gosp : ClientLab.tcl.getGospList(patient.getId(), new Date().getTime(),
                    new Date().getTime())) {
                gospInfo.add(new GospTreeNode(gosp));
            }
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (TException e) {
            ClientLab.conMan.reconnect(e);
        } 

        return root;
    }

    private void addResultTreePanel() {
        splitPane = new JSplitPane();
        splitPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        splitPane.setDividerLocation(1.0);
        pResult.add(splitPane);
        
        addResultTree();
        addResultText();
    }

    private void addResultTree() {
        final JScrollPane sptree = new JScrollPane();
        trResult = new JTree();
        trResult.setFont(new Font("Arial", Font.PLAIN, 12));
        sptree.setViewportView(trResult);
        splitPane.setLeftComponent(sptree);

        trResult.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                if (e.getNewLeadSelectionPath() == null) {
                    taResultText.setText("");
                    return;
                }
                sbResulText = new StringBuilder();
                Object lastPath = e.getNewLeadSelectionPath().getLastPathComponent();
                if (lastPath instanceof GospTreeNode) {
                }
                if (lastPath instanceof IssledNode) {
                    Isl tmpIsl = ((IssledNode)lastPath).getIsl();
                    if (tmpIsl.isSetNisl()) {
                        addLineToDetailInfo("Показатель исследования",tmpIsl.isSetPokaz_name(),
                                tmpIsl.getPokaz_name());
                        addLineToDetailInfo("Результат исследования",tmpIsl.isSetRez(),
                                tmpIsl.getRez());
                        addLineToDetailInfo("Описание",tmpIsl.isSetOp_name(),
                                tmpIsl.getOp_name());
                        addLineToDetailInfo("Заключение",tmpIsl.isSetRez_name(),
                                tmpIsl.getRez_name());
                        addLineToDetailInfo("Дата проведения исследования",tmpIsl.isSetDatav(),
                                DateFormat.getDateInstance().format(new Date(tmpIsl.getDatav())));
                    }
                    taResultText.setText(sbResulText.toString());
                }
            }
        });

        trResult.addTreeExpansionListener(new TreeExpansionListener() {
            public void treeCollapsed(TreeExpansionEvent event) {
            }
            
            public void treeExpanded(TreeExpansionEvent event) {
                Object lastPath = event.getPath().getLastPathComponent();
                if (lastPath instanceof GospTreeNode) {
                    try {
                        GospTreeNode gospNode = (GospTreeNode) lastPath;
                        gospNode.removeAllChildren();
                        for (Isl isl : ClientLab.tcl.getIslList(gospNode.gosp.getIdGosp())) {
                            gospNode.add(new IssledNode(isl));
                        }
                        ((DefaultTreeModel) trResult.getModel()).reload(gospNode);
                    } catch (KmiacServerException e) {
                        e.printStackTrace();
                    } catch (TException e) {
                        ClientLab.conMan.reconnect(e);
                    }
                }
            }
         });
        trResult.setShowsRootHandles(true);
        trResult.setRootVisible(false);
        DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) trResult.getCellRenderer();
        renderer.setLeafIcon(null);
        renderer.setClosedIcon(null);
        renderer.setOpenIcon(null);
    }

    private void addLineToDetailInfo(String name, boolean isSet, Object value) {
        if (isSet)
            if ((name != null) && (value != null))
                if ((name.length() > 0) && (value.toString().length() > 0))
                    sbResulText.append(String.format("%s: %s%s", name, value, LINE_SEP));
    }

    class GospTreeNode extends DefaultMutableTreeNode {
        private static final long serialVersionUID = 4212592425962984738L;
        private Gosp gosp;
        
        public GospTreeNode(Gosp inGosp) {
            this.gosp = inGosp;
            this.add(new IssledNode(new Isl()));
//            try {
//                for (Isl isl : ClientLab.tcl.getIslList(gosp.getIdGosp())) {
//                    this.add(new IssledNode(isl));
//                }
//            } catch (KmiacServerException e) {
//                e.printStackTrace();
//            } catch (TException e) {
//                MainForm.conMan.reconnect(e);
//            } 
        }
        
        @Override
        public String toString() {
            return DateFormat.getDateInstance().format(new Date(gosp.getDatap()));
        }
    }
    
    class IssledNode extends DefaultMutableTreeNode{
        private static final long serialVersionUID = -5215124870459111226L;
        private Isl isl;
        
        public IssledNode(Isl inIsl) {
            this.isl = inIsl;
        }
        public Isl getIsl() {
            return isl;
        }
        
        @Override
        public String toString() {
            return isl.getPokaz();
        }
    }

    private void addResultText() {
        final JScrollPane sptree = new JScrollPane();
        taResultText = new JTextArea();
        taResultText.setWrapStyleWord(true);
        taResultText.setLineWrap(true);
        taResultText.setEditable(false);
        taResultText.setFont(new Font("Tahoma", Font.PLAIN, 11));
        sptree.setViewportView(taResultText);
        splitPane.setRightComponent(sptree);
    }

    public final void onConnect() {
        try {
            cbxLabs.setData(ClientLab.tcl.getLabs(doctorAuthInfo.getClpu()));
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
                        .addComponent(lblObosn, GroupLayout.PREFERRED_SIZE, 557,
                                GroupLayout.PREFERRED_SIZE)
                        .addGroup(glPNaprav.createSequentialGroup()
                            .addComponent(spObosn, GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
                            .addGap(3))
                        .addGroup(glPNaprav.createSequentialGroup()
                            .addComponent(lblNapravFrom, GroupLayout.PREFERRED_SIZE,
                                    32, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(ComponentPlacement.RELATED)
                            .addComponent(cbxOrganizationFrom, 0, 633, Short.MAX_VALUE)
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
                    .addGap(20)
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
