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
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextComponentWrapper.DefaultLanguage;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftServerAdmin.ShablonLds;
import ru.nkz.ivcgzo.thriftServerAdmin.TemplateExistsException;

public class ShablonLdsPanel extends JPanel {
	private static final long serialVersionUID = -181023590802237403L;
	private CustomTextField tbSearch;
	private SearchTree trSearch;
	private JButton btDelete;
	private JButton btCreate;
	private CustomTextField tbName;
	private CustomTextField tbIssl;
	private JLabel lblIsslName;
	private JScrollPane spOpis;
	private JTextArea tbOpis;
	private JTextArea tbZakl;
	private DocumentListener textListener;
	private JButton btSave;
	private ShablonLds shLds;
	private boolean fillingUI;
	private String prevIssl, prevIsslName;
	
	public ShablonLdsPanel() {
		prevIssl = "";
		prevIsslName = null;
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
				.addComponent(splitPane, GroupLayout.PREFERRED_SIZE, 683, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
		);
		
		JPanel gbSearch = new JPanel();
		gbSearch.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Поиск", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		gbSearch.setMinimumSize(new Dimension(256, 128));
		splitPane.setLeftComponent(gbSearch);
		
		tbSearch = new CustomTextField(true, true, false);
		tbSearch.setVisible(false);
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
				if (JOptionPane.showConfirmDialog(ShablonLdsPanel.this, "Удалить шаблон?", "Подтверждение", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
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
		
		tbIssl = new CustomTextField();
		tbIssl.setDefaultLanguage(DefaultLanguage.English);
		tbIssl.getDocument().addDocumentListener(textListener);
		tbIssl.setColumns(10);
		
		JLabel lblIssl = new JLabel("Исследование");
		
		lblIsslName = new JLabel("");
		GroupLayout gl_gbEdit = new GroupLayout(gbEdit);
		gl_gbEdit.setHorizontalGroup(
			gl_gbEdit.createParallelGroup(Alignment.LEADING)
				.addComponent(btSave, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE)
				.addGroup(gl_gbEdit.createSequentialGroup()
					.addGroup(gl_gbEdit.createParallelGroup(Alignment.LEADING)
						.addComponent(lbName, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblIssl, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_gbEdit.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_gbEdit.createSequentialGroup()
							.addComponent(tbIssl, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblIsslName, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE))
						.addComponent(tbName, GroupLayout.DEFAULT_SIZE, 428, Short.MAX_VALUE)))
				.addComponent(gbText, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
		);
		gl_gbEdit.setVerticalGroup(
			gl_gbEdit.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_gbEdit.createSequentialGroup()
					.addGroup(gl_gbEdit.createParallelGroup(Alignment.BASELINE)
						.addComponent(tbName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbName))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_gbEdit.createParallelGroup(Alignment.BASELINE)
						.addComponent(tbIssl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblIssl)
						.addComponent(lblIsslName))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(gbText, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btSave))
		);
		
		spOpis = new JScrollPane();
		
		JLabel lblOpis = new JLabel("Описание");
		
		JLabel lblZakl = new JLabel("Заключение");
		
		JScrollPane spZakl = new JScrollPane();
		GroupLayout gl_gbText = new GroupLayout(gbText);
		gl_gbText.setHorizontalGroup(
			gl_gbText.createParallelGroup(Alignment.LEADING)
				.addComponent(lblOpis, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
				.addComponent(spOpis, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
				.addComponent(lblZakl, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
				.addComponent(spZakl, GroupLayout.DEFAULT_SIZE, 516, Short.MAX_VALUE)
		);
		gl_gbText.setVerticalGroup(
			gl_gbText.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_gbText.createSequentialGroup()
					.addComponent(lblOpis)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spOpis, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblZakl)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spZakl, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE))
		);
		
		tbZakl = new JTextArea();
		tbZakl.getDocument().addDocumentListener(textListener);
		tbZakl.setFont(new Font("Tahoma", Font.PLAIN, 12));
		new CustomTextComponentWrapper(tbZakl).setPopupMenu();
		spZakl.setViewportView(tbZakl);
		
		tbOpis = new JTextArea();
		tbOpis.getDocument().addDocumentListener(textListener);
		tbOpis.setFont(new Font("Tahoma", Font.PLAIN, 12));
		new CustomTextComponentWrapper(tbOpis).setPopupMenu();
		spOpis.setViewportView(tbOpis);
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
		lblIsslName.setText("");
		prevIssl = "";
		prevIsslName = null;
		tbIssl.clear();
		tbIssl.setEditable(true);
		tbOpis.setText("");
		tbZakl.setText("");
		
		btSave.setEnabled(false);
		btDelete.setEnabled(false);
	}
	
	private void saveShablon() {
		fillShablonFromUI();
		
		try {
			shLds.setId(MainForm.tcl.saveShLds(shLds));
			trSearch.updateSavedNode();
		} catch (TemplateExistsException e) {
			JOptionPane.showMessageDialog(ShablonLdsPanel.this, "Шаблон с таким именем уже существует для данного исследования.", "Ошибка", JOptionPane.ERROR_MESSAGE);
			tbName.requestFocusInWindow();
		} catch (KmiacServerException e) {
			JOptionPane.showMessageDialog(ShablonLdsPanel.this, "Ошибка сохранения шаблона.", "Ошибка", JOptionPane.ERROR_MESSAGE);
		} catch (TException e) {
			MainForm.conMan.reconnect(e);
		}
	}
	
	private void fillShablonFromUI() {
		shLds.setName(tbName.getText());
		shLds.setC_ldi(tbIssl.getText());
		shLds.setOpis(tbOpis.getText());
		shLds.setZakl(tbZakl.getText());
	}
	
	private void checkInput() {
		if (fillingUI)
			return;
		
		boolean enb = true;
		
		if (!prevIssl.equals(tbIssl.getText())) {
			prevIssl = tbIssl.getText();
			prevIsslName = MainForm.conMan.getNameFromPcodString(StringClassifiers.n_ldi, prevIssl);
			lblIsslName.setText(prevIsslName);
		}
		enb &= !tbName.isEmpty();
		enb &= !(tbOpis.getText().length() == 0);
//		enb &= !(tbZakl.getText().length() == 0);
		enb &= trSearch.getSelectionPath() != null;
		if (enb)
			enb &= ((DefaultMutableTreeNode) trSearch.getSelectionPath().getLastPathComponent()).getLevel() == 3;
		enb &= prevIsslName != null;
		
		btSave.setEnabled(enb);
	}
	
	private void loadShablon(int id) {
		try {
			shLds = MainForm.tcl.getShLds(id);
			fillUIFromShablon();
		} catch (KmiacServerException e) {
			JOptionPane.showMessageDialog(ShablonLdsPanel.this, "Ошибка загрузки шаблона.", "Ошибка", JOptionPane.ERROR_MESSAGE);
		} catch (TException e) {
			MainForm.conMan.reconnect(e);
		}
	}
	
	private void fillUIFromShablon() {
		fillingUI = true;
		
		try {
			tbName.setText(shLds.name);
			tbName.setCaretPosition(0);
			tbIssl.setText(shLds.c_ldi);
			tbOpis.setText(shLds.opis);
			tbOpis.setCaretPosition(0);
			tbZakl.setText(shLds.zakl);
			tbZakl.setCaretPosition(0);
			
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
						DefaultMutableTreeNode node = (DefaultMutableTreeNode) lp;
						
						if (node.getLevel() == 1) {
							try {
								node.removeAllChildren();
								for (StringClassifier sc : MainForm.tcl.getShLdsIsslList(((IntClassTreeNode) node).getCode())) {
									StrClassTreeNode sct = new StrClassTreeNode(sc);
									sct.add(new IntClassTreeNode(new IntegerClassifier(-1, "Dummy")));
									node.add(sct);
								}
								((DefaultTreeModel) getModel()).reload(node);
								if (node.getChildCount() == 0) {
									node.add(new StrClassTreeNode(new StringClassifier("-1", "Dummy")));
									collapsePath(new TreePath(lp));
								}
							} catch (KmiacServerException e) {
								collapsePath(new TreePath(lp));
								JOptionPane.showMessageDialog(ShablonLdsPanel.this, "Ошибка загрузки исследований.", "Ошибка", JOptionPane.ERROR_MESSAGE);
							} catch (TException e) {
								collapsePath(new TreePath(lp));
								MainForm.conMan.reconnect(e);
							}
						} else if (node.getLevel() == 2) {
							try {
								node.removeAllChildren();
								for (IntegerClassifier ic : MainForm.tcl.getShLdsList(((IntClassTreeNode) node.getParent()).getCode(), ((StrClassTreeNode) node).getCode()))
									node.add(new IntClassTreeNode(ic));
								((DefaultTreeModel) getModel()).reload(node);
								if (node.getChildCount() == 0) {
									node.add(new IntClassTreeNode(new IntegerClassifier(-1, "Dummy")));
									collapsePath(new TreePath(lp));
								}
							} catch (KmiacServerException e) {
								collapsePath(new TreePath(lp));
								JOptionPane.showMessageDialog(ShablonLdsPanel.this, "Ошибка загрузки шаблонов на данное исследование.", "Ошибка", JOptionPane.ERROR_MESSAGE);
							} catch (TException e) {
								collapsePath(new TreePath(lp));
								MainForm.conMan.reconnect(e);
							}
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
						DefaultMutableTreeNode lp = (DefaultMutableTreeNode) e.getNewLeadSelectionPath().getLastPathComponent();
						
						if ((lp instanceof IntClassTreeNode) && (((IntClassTreeNode) lp).getLevel() == 3))
							if (!changingNodes) {
								loadShablon(((IntClassTreeNode) lp).getCode());
								tbIssl.setEditable(false);
							} else {
								fillUIFromShablon();
								tbIssl.setEditable(((StrClassTreeNode)lp.getParent()).getCode().equals("-1"));
							}
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
				
				for (IntegerClassifier ic : MainForm.tcl.getShLdsVidIsslList()) {
					IntClassTreeNode node = new IntClassTreeNode(ic);
					
					node.add(new StrClassTreeNode(new StringClassifier("-1", "Dummy")));
					root.add(node);
				}
				setModel(new DefaultTreeModel(root));
				if (root.getChildCount() > 0)
					setSelectionPath(new TreePath(new Object[] {root, root.getChildAt(0)}));
			} catch (KmiacServerException e) {
				JOptionPane.showMessageDialog(ShablonLdsPanel.this, "Ошибка загрузки результатов поиска.", "Ошибка", JOptionPane.ERROR_MESSAGE);
			} catch (TException e) {
				MainForm.conMan.reconnect(e);
			}
		}
		
		public void removeUnsavedNode() {
			if (newNode != null)
				try {
					DefaultMutableTreeNode root = (DefaultMutableTreeNode) getModel().getRoot();
					StrClassTreeNode tnIssl = (StrClassTreeNode) newNode.getParent();
					IntClassTreeNode tnVidIssl = (IntClassTreeNode) tnIssl.getParent();
					int tempIdx = tnIssl.getIndex(newNode);
					int vidIsslIdx = tnVidIssl.getIndex(tnIssl);
					
					changingNodes = true;
					getModel().removeNodeFromParent(newNode);
					newNode = null;
					if (tnIssl.getChildCount() > 0) {
						if (tempIdx >= tnIssl.getChildCount())
							tempIdx = tnIssl.getChildCount() - 1;
						changingNodes = false;
						if (getSelectionPath() == null)
							setSelectionPath(new TreePath(new Object[] {root, tnVidIssl, tnIssl, tnIssl.getChildAt(tempIdx)}));
					} else {
						getModel().removeNodeFromParent(tnIssl);
						if (tnVidIssl.getChildCount() > 0) {
							if (vidIsslIdx >= tnVidIssl.getChildCount())
								vidIsslIdx = tnVidIssl.getChildCount() - 1;
							changingNodes = false;
							if (getSelectionPath() == null)
								setSelectionPath(new TreePath(new Object[] {root, tnVidIssl, tnVidIssl.getChildAt(vidIsslIdx)}));
						} else {
							tnVidIssl.add(new StrClassTreeNode(new StringClassifier("-1", "Dummy")));
							collapsePath(new TreePath(tnIssl));
							if (getSelectionPath() == null)
								setSelectionPath(new TreePath(new Object[] {root, tnVidIssl}));
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					newNode = null;
					changingNodes = false;
				}
		}
		
		public void removeSelected() {
			if (getSelectionPath() != null)
				if (getSelectionPath().getLastPathComponent() != null)
					if ((getSelectionPath().getLastPathComponent() instanceof IntClassTreeNode) && (((IntClassTreeNode) getSelectionPath().getLastPathComponent()).getLevel() == 3)) {
						try {
							if (shLds.id > 0) {
								MainForm.tcl.deleteShLds(shLds.id);
								newNode = (IntClassTreeNode) getSelectionPath().getLastPathComponent();
							}
							
							removeUnsavedNode();
						} catch (KmiacServerException e) {
							JOptionPane.showMessageDialog(ShablonLdsPanel.this, "Ошибка удаления шаблона.", "Ошибка", JOptionPane.ERROR_MESSAGE);
						} catch (TException e) {
							e.printStackTrace();
							MainForm.conMan.reconnect(e);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
		}
		
		public void createNew() {
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) getModel().getRoot();
			IntClassTreeNode tnVidIssl = null;
			StrClassTreeNode tnIssl = null;
			TreePath path;
			
			removeUnsavedNode();
			
			shLds = new ShablonLds().setName("Без названия");
			
			if (getSelectionPath() != null)
				if (getSelectionPath().getLastPathComponent() != null) {
					int level = ((DefaultMutableTreeNode) getSelectionPath().getLastPathComponent()).getLevel();
					if (level == 3) {
						tnIssl = (StrClassTreeNode) getSelectionPath().getParentPath().getLastPathComponent();
					} else if (level == 2) {
						tnIssl = (StrClassTreeNode) getSelectionPath().getLastPathComponent();
						expandPath(new TreePath(tnIssl));
						if (tnIssl.getChildCount() == 1)
							if (((IntClassTreeNode) tnIssl.getChildAt(0)).getCode() == -1)
								tnIssl.remove(0);
					} else if (level == 1) {
						tnIssl = new StrClassTreeNode(new StringClassifier("-1", "Без названия"));
						tnVidIssl = (IntClassTreeNode) getSelectionPath().getLastPathComponent();
						getModel().reload(tnVidIssl);
						expandPath(new TreePath(tnVidIssl));
						if  (((StrClassTreeNode)tnVidIssl.getChildAt(0)).getCode().equals("-1")) {
							tnVidIssl.remove(0);
						}
						getModel().insertNodeInto(tnIssl, tnVidIssl, tnVidIssl.getChildCount());
					}
				}
			if (tnIssl == null) {
				JOptionPane.showMessageDialog(ShablonLdsPanel.this, "Перед созданием шаблона необходимо выбрать исследование.", "Ошибка", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			tnVidIssl = (IntClassTreeNode) tnIssl.getParent();
			changingNodes = true;
			try {
				shLds.setC_p0e1(tnVidIssl.getCode());
				shLds.setC_ldi(tnIssl.getCode());
				newNode = new IntClassTreeNode(new IntegerClassifier(0, shLds.name));
				tnIssl.add(newNode);
				getModel().reload(tnIssl);
				path = new TreePath(new Object[] {root, tnVidIssl, tnIssl, newNode});
				setSelectionPath(path);
				scrollPathToVisible(path);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				changingNodes = false;
			}
		}
		
		public void updateSavedNode() {
			DefaultMutableTreeNode root = (DefaultMutableTreeNode) getModel().getRoot();
			StrClassTreeNode tnIssl = (StrClassTreeNode) getSelectionPath().getParentPath().getLastPathComponent();
			IntClassTreeNode tnTemp = (IntClassTreeNode) getSelectionPath().getLastPathComponent();
			TreePath path;
			
			newNode = null;
			tnIssl.sc.setPcod(shLds.c_ldi);
			tnIssl.sc.setName(lblIsslName.getText());
			tnTemp.ic.setPcod(shLds.id);
			tnTemp.ic.setName(shLds.name);
			getModel().reload(tnIssl);
			getModel().reload(tnTemp);
			path = new TreePath(new Object[] {root, tnIssl.getParent(), tnIssl, tnTemp});
			setSelectionPath(path);
			scrollPathToVisible(path);
		}
		
		private class StrClassTreeNode extends DefaultMutableTreeNode {
			private static final long serialVersionUID = -1181358595138642104L;
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
