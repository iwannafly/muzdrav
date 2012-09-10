package ru.nkz.ivcgzo.clientOsm;

import java.awt.Dimension;
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
import ru.nkz.ivcgzo.thriftOsm.Shablon;
import ru.nkz.ivcgzo.thriftOsm.ShablonText;

public class ShablonForm  extends JDialog {
	private static final long serialVersionUID = -6616098681222163927L;
	private CustomTextField tbSearch;
	private SearchTree trSearch;
	private JTextArea tbView;
	private Shablon sho;
	private boolean accepted;
	
	public ShablonForm() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (!accepted)
					sho = null;
				accepted = false;
			}
		});
		
		setModalityType(ModalityType.TOOLKIT_MODAL);
		setTitle("Поиск шаблона");
		setBounds(100, 100, 714, 574);
		
		JSplitPane splitPaneSh = new JSplitPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPaneSh, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPaneSh, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
		);
		
		JPanel gbSearch = new JPanel();
		gbSearch.setMinimumSize(new Dimension(256, 128));
		gbSearch.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Поиск", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		splitPaneSh.setLeftComponent(gbSearch);
		
		tbSearch = new CustomTextField(true, true, false);
		tbSearch.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				trSearch.requestUpdate(tbSearch.getText());
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				trSearch.requestUpdate(tbSearch.getText());
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				trSearch.requestUpdate(tbSearch.getText());
			}
		});
		tbSearch.setColumns(10);
		
		JScrollPane spSearch = new JScrollPane();
		GroupLayout gl_gbSearch = new GroupLayout(gbSearch);
		gl_gbSearch.setHorizontalGroup(
			gl_gbSearch.createParallelGroup(Alignment.LEADING)
				.addComponent(tbSearch, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
				.addComponent(spSearch, GroupLayout.DEFAULT_SIZE, 256, Short.MAX_VALUE)
		);
		gl_gbSearch.setVerticalGroup(
			gl_gbSearch.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_gbSearch.createSequentialGroup()
					.addComponent(tbSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spSearch, GroupLayout.DEFAULT_SIZE, 488, Short.MAX_VALUE))
		);
		
		trSearch = new SearchTree();
		trSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					acceptShablon();
			}
		});
		trSearch.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					acceptShablon();
			}
		});
		spSearch.setViewportView(trSearch);
		gbSearch.setLayout(gl_gbSearch);
		
		JPanel pnView = new JPanel();
		splitPaneSh.setRightComponent(pnView);
		
		JScrollPane spView = new JScrollPane();
		GroupLayout gl_pnView = new GroupLayout(pnView);
		gl_pnView.setHorizontalGroup(
			gl_pnView.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnView.createSequentialGroup()
					.addContainerGap()
					.addComponent(spView, GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_pnView.setVerticalGroup(
			gl_pnView.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnView.createSequentialGroup()
					.addContainerGap()
					.addComponent(spView, GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tbView = new JTextArea();
		tbView.setWrapStyleWord(true);
		tbView.setLineWrap(true);
		tbView.setEditable(false);
		new CustomTextComponentWrapper(tbView).setPopupMenu();
		spView.setViewportView(tbView);
		pnView.setLayout(gl_pnView);
		getContentPane().setLayout(groupLayout);
	}
	
	public void showShablonForm(String srcTxt) {
		tbSearch.setText(srcTxt);
		trSearch.updateNow(tbSearch.getText());
		
		setVisible(true);
	}
	
	private void clearFields() {
		tbView.setText("");
	}
	
	private void loadShablon(int code) {
		clearFields();
		
		try {
			String str;
			String nl = System.lineSeparator();
			sho = MainForm.tcl.getSh(code);
			
			str = String.format("Динамика: %s%s", sho.din, nl);
			str += nl;
			for (ShablonText st : sho.textList)
				if (st.text.length() > 0) {
					str += String.format("%s:%s%s%s%s", st.grupName, nl, st.text, nl, nl);
				}
			tbView.setText(str);
		} catch (KmiacServerException e) {
			JOptionPane.showMessageDialog(this, "Ошибка загрузка текстов шаблона", "Ошибка", JOptionPane.ERROR_MESSAGE);
		} catch (TException e) {
			MainForm.conMan.reconnect(e);
		}
	}
	
	
	private void acceptShablon() {
		if (trSearch.getSelectionPath() != null)
			if (((DefaultMutableTreeNode) trSearch.getSelectionPath().getLastPathComponent()).getLevel() == 2) {
				accepted = true;
				this.setVisible(false);
			}
	}
	
	public Shablon getShablon() {
		return sho;
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
				public void treeExpanded(TreeExpansionEvent event) {
					Object lp = event.getPath().getLastPathComponent();
					
					if (lp instanceof StrClassTreeNode) {
						StrClassTreeNode node = (StrClassTreeNode) lp;
						
						try {
							node.removeAllChildren();
							for (IntegerClassifier ic : MainForm.tcl.getShByDiag(MainForm.authInfo.cspec, MainForm.authInfo.cslu, node.getCode()))
								node.add(new IntClassTreeNode(ic));
							((DefaultTreeModel) getModel()).reload(node);
						} catch (KmiacServerException e) {
							collapsePath(new TreePath(lp));
							JOptionPane.showMessageDialog(ShablonForm.this, "Ошибка загрузки шаблонов на данный диагноз", "Ошибка", JOptionPane.ERROR_MESSAGE);
						} catch (TException e) {
							collapsePath(new TreePath(lp));
							MainForm.conMan.reconnect(e);
						}
					}
				}
				
				@Override
				public void treeCollapsed(TreeExpansionEvent event) {
				}
			});
		}
		
		private void setSelectionChange() {
			getSelectionModel().addTreeSelectionListener(new TreeSelectionListener() {
				@Override
				public void valueChanged(TreeSelectionEvent e) {
					if (e.getNewLeadSelectionPath() != null) {
						Object lp = e.getNewLeadSelectionPath().getLastPathComponent();
						
						if (lp instanceof IntClassTreeNode)
							loadShablon(((IntClassTreeNode) lp).getCode());
						else
							clearFields();
					} else {
						clearFields();
					}
				}
			});
		}
		
		private void setTimer() {
			timer = new Timer(500, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					update();
				}
			});
		}
		
		@Override
		public DefaultTreeModel getModel() {
			return (DefaultTreeModel) super.getModel();
		}
		
		public void requestUpdate(String srcStr) {
			timer.stop();
			this.srcStr = srcStr;
			timer.start();
		}
		
		public void updateNow(String srcStr) {
			this.srcStr = srcStr;
			update();
		}
		
		private void update() {
			timer.stop();
			clearFields();
			
			if (srcStr.length() < 3)
				srcStr = null;
			else
				srcStr = '%' + srcStr + '%';
			
			try {
				DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
				
				for (StringClassifier sc : MainForm.tcl.getShPoiskDiag(MainForm.authInfo.cspec, MainForm.authInfo.cslu, srcStr)) {
					StrClassTreeNode node = new StrClassTreeNode(sc);
					
					node.add(new IntClassTreeNode(new IntegerClassifier(-1, "Dummy")));
					root.add(node);
				}
				setModel(new DefaultTreeModel(root));
			} catch (KmiacServerException e) {
				JOptionPane.showMessageDialog(ShablonForm.this, "Ошибка загрузки результатов поиска", "Ошибка", JOptionPane.ERROR_MESSAGE);
			} catch (TException e) {
				MainForm.conMan.reconnect(e);
			}
		}
				
		private class StrClassTreeNode extends DefaultMutableTreeNode {
			private static final long serialVersionUID = -5329915904305848896L;
			private StringClassifier sc;
			
			public StrClassTreeNode(StringClassifier sc) {
				this.sc = sc;
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
			
			public IntClassTreeNode(IntegerClassifier ic) {
				this.ic = ic;
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
	
