package ru.nkz.ivcgzo.clientHospital;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextComponentWrapper;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftHospital.Shablon;
import ru.nkz.ivcgzo.thriftHospital.ShablonText;

import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JEditorPane;

public class ShablonForm  extends JDialog {
    private static final long serialVersionUID = -6616098681222163927L;
    private final CustomTextField tbSearch;
    private final SearchTree trSearch;
    private final JTextPane tbView;
    private Shablon sho;
    private boolean accepted;

    public ShablonForm() {
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(final WindowEvent e) {
                if (!accepted) {
                    sho = null;
                }
                accepted = false;
            }
        });

        setModalityType(ModalityType.TOOLKIT_MODAL);
        setTitle("Поиск шаблона");
        setBounds(100, 100, 714, 574);
        setPreferredSize(new Dimension(650, 650));
        setSize(new Dimension(650, 650));
        JSplitPane splitPaneSh = new JSplitPane();
        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addComponent(splitPaneSh, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
        );
        groupLayout.setVerticalGroup(
            groupLayout.createParallelGroup(Alignment.LEADING)
                .addComponent(splitPaneSh, Alignment.TRAILING,
                    GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
        );

        JPanel gbSearch = new JPanel();
        gbSearch.setMinimumSize(new Dimension(256, 128));
        gbSearch.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
                "Поиск", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        splitPaneSh.setLeftComponent(gbSearch);

        tbSearch = new CustomTextField(true, true, false);
        tbSearch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void removeUpdate(final DocumentEvent e) {
                trSearch.requestUpdate(tbSearch.getText());
            }

            @Override
            public void insertUpdate(final DocumentEvent e) {
                trSearch.requestUpdate(tbSearch.getText());
            }

            @Override
            public void changedUpdate(final DocumentEvent e) {
                trSearch.requestUpdate(tbSearch.getText());
            }
        });
        tbSearch.setColumns(10);

        JScrollPane spSearch = new JScrollPane();
        GroupLayout glGbSearch = new GroupLayout(gbSearch);
        glGbSearch.setHorizontalGroup(
            glGbSearch.createParallelGroup(Alignment.LEADING)
                .addComponent(tbSearch, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
                .addComponent(spSearch, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
        );
        glGbSearch.setVerticalGroup(
            glGbSearch.createParallelGroup(Alignment.LEADING)
                .addGroup(glGbSearch.createSequentialGroup()
                    .addComponent(tbSearch, GroupLayout.PREFERRED_SIZE,
                        GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(ComponentPlacement.RELATED)
                    .addComponent(spSearch, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE))
        );

        trSearch = new SearchTree();
        trSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(final KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    acceptShablon();
                }
            }
        });
        trSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    acceptShablon();
                }
            }
        });
        spSearch.setViewportView(trSearch);
        gbSearch.setLayout(glGbSearch);

        JPanel pnView = new JPanel();
        splitPaneSh.setRightComponent(pnView);

        JScrollPane spView = new JScrollPane();
        GroupLayout glPnView = new GroupLayout(pnView);
        glPnView.setHorizontalGroup(
            glPnView.createParallelGroup(Alignment.LEADING)
                .addGroup(glPnView.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(spView, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
                    .addContainerGap())
        );
        glPnView.setVerticalGroup(
            glPnView.createParallelGroup(Alignment.LEADING)
                .addGroup(glPnView.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(spView, GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
                    .addContainerGap())
        );

        tbView = new JTextPane();
        tbView.setContentType("text/html");
        tbView.setFont(new Font("Tahoma", Font.PLAIN, 11));
        tbView.setEditable(false);
        new CustomTextComponentWrapper(tbView).setPopupMenu();
        spView.setViewportView(tbView);
        pnView.setLayout(glPnView);
        getContentPane().setLayout(groupLayout);
    }

    public final void showShablonForm(final String srcTxt, final IntegerClassifier shOsm) {
        tbSearch.setText(srcTxt);
        trSearch.updateNow(tbSearch.getText());
        trSearch.select(shOsm);

        setVisible(true);
    }

    private void clearFields() {
        tbView.setText("");
    }

    private void loadShablon(final int code) {
        clearFields();

        try {
            String str;
            String nl = System.lineSeparator();
            sho = ClientHospital.tcl.getShablon(code);

            str = String.format("<FONT SIZE=3><b>Динамика</b>: %s%s<br>", sho.din, nl);
            str += nl;
            for (ShablonText st : sho.textList) {
                if (st.text.length() > 0) {
                    str += String.format("<b>%s</b>:<br>%s%s%s%s<br>",
                        st.grupName, nl, st.text, nl, nl);
                }
            }
            str += "</FONT SIZE>";
            tbView.setText(str);
        } catch (KmiacServerException e) {
            JOptionPane.showMessageDialog(this, "Ошибка загрузки текстов шаблона",
                "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
        }
    }

    private void acceptShablon() {
        if (trSearch.getSelectionPath() != null) {
            if (((DefaultMutableTreeNode) trSearch.getSelectionPath().getLastPathComponent())
                    .getLevel() == 2) {
                accepted = true;
                this.setVisible(false);
            }
        }
    }

    public final Shablon getShablon() {
        return sho;
    }

    public final String getSearchString() {
        return tbSearch.getText();
    }

    private class SearchTree extends JTree {
        private static final long serialVersionUID = 8411964778613690597L;
        private Timer timer;
        private String srcStr;

        public SearchTree() {
            setShowsRootHandles(true);
            setRootVisible(false);
            DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) getCellRenderer();
            renderer.setLeafIcon(null);
            renderer.setClosedIcon(null);
            renderer.setOpenIcon(null);

            setExpandedChange();
            setSelectionChange();
            setTimer();
        }

        private void setExpandedChange() {
            addTreeExpansionListener(new TreeExpansionListener() {
                @Override
                public void treeExpanded(final TreeExpansionEvent event) {
                    Object lp = event.getPath().getLastPathComponent();

                    if (lp instanceof StrClassTreeNode) {
                        StrClassTreeNode node = (StrClassTreeNode) lp;

                        try {
                            node.removeAllChildren();
                            for (IntegerClassifier ic
                                    : ClientHospital.tcl.getShablonBySelectedDiagnosis(
                                        ClientHospital.authInfo.cspec,
                                        ClientHospital.authInfo.cslu, node.getCode(), srcStr)) {
                                node.add(new IntClassTreeNode(ic));
                            }
                            ((DefaultTreeModel) getModel()).reload(node);
                        } catch (KmiacServerException e) {
                            collapsePath(new TreePath(lp));
                            JOptionPane.showMessageDialog(ShablonForm.this,
                                "Ошибка загрузки шаблонов на данный диагноз",
                                "Ошибка", JOptionPane.ERROR_MESSAGE);
                        } catch (TException e) {
                            collapsePath(new TreePath(lp));
                            ClientHospital.conMan.reconnect(e);
                        }
                    }
                }

                @Override
                public void treeCollapsed(final TreeExpansionEvent event) {
                }
            });
        }

        private void setSelectionChange() {
            getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
                @Override
                public void valueChanged(final TreeSelectionEvent e) {
                    if (e.getNewLeadSelectionPath() != null) {
                        Object lp = e.getNewLeadSelectionPath().getLastPathComponent();

                        if (lp instanceof IntClassTreeNode) {
                            loadShablon(((IntClassTreeNode) lp).getCode());
                        } else {
                            clearFields();
                        }
                    } else {
                        clearFields();
                    }
                }
            });
        }

        private void setTimer() {
            timer = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    update();
                }
            });
        }

        @Override
        public DefaultTreeModel getModel() {
            return (DefaultTreeModel) super.getModel();
        }

        public void requestUpdate(final String inSrcStr) {
            timer.stop();
            this.srcStr = getSearchString(inSrcStr);
            timer.start();
        }

        public void updateNow(final String inSrcStr) {
            this.srcStr = getSearchString(inSrcStr);
            update();
        }

        private void update() {
            timer.stop();
            clearFields();

            try {
                DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");

                for (StringClassifier sc : ClientHospital.tcl.getShablonDiagnosis(
                        ClientHospital.authInfo.cspec, ClientHospital.authInfo.cslu, srcStr)) {
                    StrClassTreeNode node = new StrClassTreeNode(sc);

                    node.add(new IntClassTreeNode(new IntegerClassifier(-1, "Dummy")));
                    root.add(node);
                }
                setModel(new DefaultTreeModel(root));
            } catch (KmiacServerException e) {
                JOptionPane.showMessageDialog(ShablonForm.this,
                    "Ошибка загрузки результатов поиска", "Ошибка", JOptionPane.ERROR_MESSAGE);
            } catch (TException e) {
                ClientHospital.conMan.reconnect(e);
            }
        }

        public void select(final IntegerClassifier shOsm) {
            if ((shOsm != null) && (shOsm.isSetName()) && (shOsm.name.indexOf(' ') > -1)) {
                String diag = shOsm.name.substring(0, shOsm.name.indexOf(' '));
                DefaultMutableTreeNode root = (DefaultMutableTreeNode) getModel().getRoot();
                int childCount = root.getChildCount();

                for (int i = 0; i < childCount; i++) {
                    StrClassTreeNode diagGroupNode = (StrClassTreeNode) root.getChildAt(i);

                    if (diagGroupNode.toString().indexOf(diag) == 0) {
                        int shOsmChildCount;
                        TreePath path = new TreePath(new Object[] {root, diagGroupNode});
                        Rectangle bounds = getPathBounds(path);

                        expandPath(path);
                        shOsmChildCount = diagGroupNode.getChildCount();
                        for (int j = 0; j < shOsmChildCount; j++) {
                            if (((IntClassTreeNode) diagGroupNode.getChildAt(j))
                                    .getCode() == shOsm.pcod) {
                                setSelectionPath(new TreePath(
                                    new Object[] {root, diagGroupNode,
                                        diagGroupNode.getChildAt(j)}));
                                bounds.x = 0;
                                scrollRectToVisible(bounds);
                                return;
                            }
                        }
                        collapsePath(path);
                        return;
                    }
                }
            }
        }

        private String getSearchString(final String srcInStr) {
            if (srcInStr.length() < 3) {
                return null;
            } else {
                return '%' + srcInStr + '%';
            }
        }

        private class StrClassTreeNode extends DefaultMutableTreeNode {
            private static final long serialVersionUID = -5329915904305848896L;
            private StringClassifier sc;

            public StrClassTreeNode(final StringClassifier inSc) {
                this.sc = inSc;
            }

            public String getCode() {
                return sc.pcod;
            }

            @Override
            public String toString() {
                return String.format("%s %s", sc.pcod.trim(), sc.name);
            }
        }

        private class IntClassTreeNode extends DefaultMutableTreeNode {
            private static final long serialVersionUID = 542109909158320095L;
            private IntegerClassifier ic;

            public IntClassTreeNode(final IntegerClassifier inC) {
                this.ic = inC;
            }

            public int getCode() {
                return ic.pcod;
            }

            @Override
            public String toString() {
                return ic.name;
            }
        }
    }
}

