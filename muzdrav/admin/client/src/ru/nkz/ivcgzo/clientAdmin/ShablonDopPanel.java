package ru.nkz.ivcgzo.clientAdmin;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
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
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftServerAdmin.ShablonDop;
import ru.nkz.ivcgzo.thriftServerAdmin.TemplateExistsException;

public class ShablonDopPanel extends JPanel {
	private static final long serialVersionUID = -181023590802237403L;
	private CustomTextField tbSearch;
	private SearchTree trSearch;
	private JButton btDelete;
	private JButton btCreate;
	private CustomTextField tbName;
	private JScrollPane spText;
	private JTextArea tbText;
	private DocumentListener textListener;
	private JButton btSave;
	private ShablonDop shDop;
	private boolean fillingUI;
	private Font defFont;
	
	public ShablonDopPanel() {
		textListener = new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				checkInput();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				checkInput();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				checkInput();
			}
		};
		
		JSplitPane splitPane = new JSplitPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 775, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 426, Short.MAX_VALUE)
					.addGap(0))
		);
		
		JPanel gbSearch = new JPanel();
		gbSearch.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Поиск", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		gbSearch.setMinimumSize(new Dimension(256, 128));
		splitPane.setLeftComponent(gbSearch);
		
		tbSearch = new CustomTextField(true, true, false);
		tbSearch.setVisible(false);
		defFont = tbSearch.getFont();
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
		
		JScrollPane scrollPane = new JScrollPane();
		
		btDelete = new JButton("Удалить");
		btDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(ShablonDopPanel.this, "Удалить шаблон?", "Подтверждение", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
					trSearch.removeSelected();
			}
		});
		
		btCreate = new JButton("Создать");
		btCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				trSearch.createNew();
				tbName.requestFocusInWindow();
			}
		});
		GroupLayout gl_gbSearch = new GroupLayout(gbSearch);
		gl_gbSearch.setHorizontalGroup(
			gl_gbSearch.createParallelGroup(Alignment.LEADING)
				.addComponent(tbSearch, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
				.addGroup(gl_gbSearch.createSequentialGroup()
					.addComponent(btDelete, GroupLayout.PREFERRED_SIZE, 51, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btCreate, GroupLayout.PREFERRED_SIZE, 51, Short.MAX_VALUE))
		);
		gl_gbSearch.setVerticalGroup(
			gl_gbSearch.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_gbSearch.createSequentialGroup()
					.addComponent(tbSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_gbSearch.createParallelGroup(Alignment.BASELINE)
						.addComponent(btDelete)
						.addComponent(btCreate)))
		);
		
		trSearch = new SearchTree();
		scrollPane.setViewportView(trSearch);
		gbSearch.setLayout(gl_gbSearch);
		
		JPanel gbEdit = new JPanel();
		gbEdit.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Редактирование", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		gbEdit.setMinimumSize(new Dimension(512, 128));
		splitPane.setRightComponent(gbEdit);
		
		btSave = new JButton("Сохранить");
		btSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveShablon();
			}
		});
		
		tbName = new CustomTextField(true, true, false);
		tbName.getDocument().addDocumentListener(textListener);
		tbName.setColumns(10);
		
		JLabel lbName = new JLabel("Название");
		
		JPanel gbText = new JPanel();
		gbText.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Тексты", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_gbEdit = new GroupLayout(gbEdit);
		gl_gbEdit.setHorizontalGroup(
			gl_gbEdit.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_gbEdit.createSequentialGroup()
					.addComponent(lbName, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tbName, GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE))
				.addComponent(gbText, GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
				.addComponent(btSave, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 521, Short.MAX_VALUE)
		);
		gl_gbEdit.setVerticalGroup(
			gl_gbEdit.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_gbEdit.createSequentialGroup()
					.addGroup(gl_gbEdit.createParallelGroup(Alignment.BASELINE)
						.addComponent(tbName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbName))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(gbText, GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btSave))
		);
		
		spText = new JScrollPane();
		GroupLayout gl_gbText = new GroupLayout(gbText);
		gl_gbText.setHorizontalGroup(
			gl_gbText.createParallelGroup(Alignment.LEADING)
				.addComponent(spText, GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
		);
		gl_gbText.setVerticalGroup(
			gl_gbText.createParallelGroup(Alignment.LEADING)
				.addComponent(spText, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
		);
		
		tbText = new JTextArea();
		tbText.getDocument().addDocumentListener(textListener);
		tbText.setFont(defFont);
		new CustomTextComponentWrapper(tbText).setPopupMenu();
		spText.setViewportView(tbText);
		gbText.setLayout(gl_gbText);
		
		gbEdit.setLayout(gl_gbEdit);
		setLayout(groupLayout);
	}
	
	public void prepareShTextFields() {
		clearFields();
		
		trSearch.updateNow(tbSearch.getText());
	}
	
	private void clearFields() {
		tbName.clear();
		tbText.setText("");
		
		btSave.setEnabled(false);
		btDelete.setEnabled(false);
	}
	
	private void saveShablon() {
		fillShablonFromUI();
		
		try {
			shDop.setId(MainForm.tcl.saveShDop(shDop));
			trSearch.updateSavedNode();
		} catch (TemplateExistsException e) {
			JOptionPane.showMessageDialog(ShablonDopPanel.this, "Шаблон с таким именем уже существует для данного раздела.", "Ошибка", JOptionPane.ERROR_MESSAGE);
			tbName.requestFocusInWindow();
		} catch (KmiacServerException e) { //FIXME исключение не пробрасывается из-за ошибки в трифте. Если функция возвращает простой тип, то проверка на успешность выполнения проходится раньше, чем на исключение.
			JOptionPane.showMessageDialog(ShablonDopPanel.this, "Ошибка сохранения шаблона.", "Ошибка", JOptionPane.ERROR_MESSAGE);
		} catch (TException e) {
			MainForm.conMan.reconnect(e);
		}
	}
	
	private void fillShablonFromUI() {
		shDop.setName(tbName.getText());
		shDop.setText(tbText.getText());
	}
	
	private void checkInput() {
		if (fillingUI)
			return;
		
		boolean enb = true;
		
		enb &= !tbName.isEmpty();
		enb &= !(tbText.getText().length() == 0);
		enb &= trSearch.getSelectionPath() != null;
		if (enb)
			enb &= ((DefaultMutableTreeNode) trSearch.getSelectionPath().getLastPathComponent()).getLevel() == 2;
		
		btSave.setEnabled(enb);
	}
	
	private void loadShablon(int id) {
		try {
			shDop = MainForm.tcl.getShDop(id);
			fillUIFromShablon();
		} catch (KmiacServerException e) {
			JOptionPane.showMessageDialog(ShablonDopPanel.this, "Ошибка загрузки шаблона.", "Ошибка", JOptionPane.ERROR_MESSAGE);
		} catch (TException e) {
			MainForm.conMan.reconnect(e);
		}
	}
	
	private void fillUIFromShablon() {
		fillingUI = true;
		
		try {
			tbName.setText(shDop.name);
			tbName.setCaretPosition(0);
			tbText.setText(shDop.text);
			tbText.setCaretPosition(0);
			
			fillingUI = false;
			btDelete.setEnabled(true);
			checkInput();
		} finally {
			fillingUI = false;
		}
	}
	
	private class SearchTree extends JTree {
		private static final long serialVersionUID = -6009216318295458571L;
		private Timer timer;
		private String srcStr;
		private boolean changingNodes;
		private IntClassTreeNode newNode;
		
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
					
					if (!changingNodes) {
							IntClassTreeNode node = (IntClassTreeNode) lp;
							
							try {
								node.removeAllChildren();
								for (IntegerClassifier ic : MainForm.tcl.getShDopList(node.getCode()))
									node.add(new IntClassTreeNode(ic));
								((DefaultTreeModel) getModel()).reload(node);
								if (node.getChildCount() == 0) {
									node.add(new IntClassTreeNode(new IntegerClassifier(-1, "Dummy")));
									collapsePath(new TreePath(lp));
								}
							} catch (KmiacServerException e) {
								collapsePath(new TreePath(lp));
								JOptionPane.showMessageDialog(ShablonDopPanel.this, "Ошибка загрузки шаблонов на данную группу.", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
					if (!changingNodes)
						removeUnsavedNode();
					if (e.getNewLeadSelectionPath() != null) {
						Object lp = e.getNewLeadSelectionPath().getLastPathComponent();
						
						if ((lp instanceof IntClassTreeNode) && (((IntClassTreeNode) lp).getLevel() == 2))
							if (!changingNodes)
								loadShablon(((IntClassTreeNode) lp).getCode());
							else
								fillUIFromShablon();
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
			
			try {
				DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root");
				
				for (IntegerClassifier ic : MainForm.tcl.getShDopRazdList()) {
					IntClassTreeNode node = new IntClassTreeNode(ic);
					
					node.add(new IntClassTreeNode(new IntegerClassifier(-1, "Dummy")));
					root.add(node);
				}
				setModel(new DefaultTreeModel(root));
				if (root.getChildCount() > 0)
					setSelectionPath(new TreePath(new Object[] {root, root.getChildAt(0)}));
			} catch (KmiacServerException e) {
				JOptionPane.showMessageDialog(ShablonDopPanel.this, "Ошибка загрузки результатов поиска.", "Ошибка", JOptionPane.ERROR_MESSAGE);
			} catch (TException e) {
				MainForm.conMan.reconnect(e);
			}
		}
		
		public void removeSelected() {
			if (getSelectionPath() != null)
				if (getSelectionPath().getLastPathComponent() != null)
					if ((getSelectionPath().getLastPathComponent() instanceof IntClassTreeNode) && (((IntClassTreeNode) getSelectionPath().getLastPathComponent()).getLevel() == 2)) {
						try {
							if (shDop.id > 0) {
								MainForm.tcl.deleteShDop(shDop.id);
								newNode = (IntClassTreeNode) getSelectionPath().getLastPathComponent();
							}
							
							removeUnsavedNode();
						} catch (KmiacServerException e) {
							JOptionPane.showMessageDialog(ShablonDopPanel.this, "Ошибка удаления шаблона.", "Ошибка", JOptionPane.ERROR_MESSAGE);
						} catch (TException e) {
							e.printStackTrace();
							MainForm.conMan.reconnect(e);
						}
						
					}
		}
		
		public void createNew() {
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) getModel().getRoot();
			IntClassTreeNode parent = null;
			TreePath path;
			
			removeUnsavedNode();
			
			shDop = new ShablonDop().setName("Без названия");
			
			if (getSelectionPath() != null)
				if (getSelectionPath().getLastPathComponent() != null)
					if (getSelectionPath().getLastPathComponent() instanceof IntClassTreeNode)
						if (((IntClassTreeNode) getSelectionPath().getLastPathComponent()).getLevel() == 2) {
						parent = (IntClassTreeNode) getSelectionPath().getParentPath().getLastPathComponent();
					} else if (((IntClassTreeNode) getSelectionPath().getLastPathComponent()).getLevel() == 1) {
						parent = (IntClassTreeNode) getSelectionPath().getLastPathComponent();
						expandPath(new TreePath(parent));
						if (parent.getChildCount() == 1)
							if (((IntClassTreeNode) parent.getChildAt(0)).getCode() == -1)
								parent.remove(0);
					}
			if (parent == null) {
				JOptionPane.showMessageDialog(ShablonDopPanel.this, "Перед созданием шаблона необходимо выбрать раздел.", "Ошибка", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			changingNodes = true;
			try {
				shDop.setIdNshablon(parent.getCode());
				newNode = new IntClassTreeNode(new IntegerClassifier(0, shDop.name));
				parent.add(newNode);
				getModel().reload(parent);
				path = new TreePath(new Object[] {root, parent, newNode});
				setSelectionPath(path);
				scrollPathToVisible(path);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				changingNodes = false;
			}
		}
		
		public void updateSavedNode() {
			IntClassTreeNode child = (IntClassTreeNode) getSelectionPath().getLastPathComponent();
			IntClassTreeNode parent = (IntClassTreeNode) getSelectionPath().getParentPath().getLastPathComponent();
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) getModel().getRoot();
			TreePath path;
			
			newNode = null;
			child.ic.setPcod(shDop.id);
			child.ic.setName(shDop.name);
			getModel().reload(child);
			path = new TreePath(new Object[] {root, parent, child});
			setSelectionPath(path);
			scrollPathToVisible(path);
		}
		
		private void removeUnsavedNode() {
			if (newNode != null) {
				try {
					DefaultMutableTreeNode root = (DefaultMutableTreeNode) getModel().getRoot();
					IntClassTreeNode parent = (IntClassTreeNode) newNode.getParent();
					int childIndex = parent.getIndex(newNode);
					
					changingNodes = true;
					getModel().removeNodeFromParent(newNode);
					if (parent.getChildCount() > 0) {
						if (childIndex >= parent.getChildCount())
							childIndex = parent.getChildCount() - 1;
						setSelectionPath(new TreePath(new Object[] {root, parent, parent.getChildAt(childIndex)}));
					} else {
						parent.add(new IntClassTreeNode(new IntegerClassifier(-1, "Dummy")));
						collapsePath(new TreePath(parent));
						setSelectionPath(new TreePath(new Object[] {root, parent}));
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					newNode = null;
					changingNodes = false;
				}
			}
		}
		
		private class IntClassTreeNode extends DefaultMutableTreeNode {
			private static final long serialVersionUID = -7399608721269705207L;
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
