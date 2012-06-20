package ru.nkz.ivcgzo.clientVrachInfo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.Enumeration;
import java.util.EventObject;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.text.JTextComponent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellEditor;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierList;
import ru.nkz.ivcgzo.clientManager.common.swing.TristateCheckBox;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftServerVrachInfo.ShablonPok;
import ru.nkz.ivcgzo.thriftServerVrachInfo.ShablonRazd;
import ru.nkz.ivcgzo.thriftServerVrachInfo.ShablonText;

public class ShablonPanel extends JPanel {
	private static final long serialVersionUID = -450359947377115240L;
//	private static final Border nodeSelectionBorder = UIManager.getBorder("Tree.selectionBorder");
//	private static final Color nodeSelectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
	private static final Color nodeSelectionForeground = UIManager.getColor("Tree.selectionForeground");
	private static final Color nodeSelectionBackground = UIManager.getColor("Tree.selectionBackground");
	private static final Color nodeTextForeground = UIManager.getColor("Tree.textForeground");
	private static final Color nodeTextBackground = UIManager.getColor("Tree.textBackground");
	private static final Border emptyBorder = new EmptyBorder(0, 4, 0, 4);
	private ThriftStringClassifierList<StringClassifier> lstCdol;
	private JTree treeShablon;

	/**
	 * Create the application.
	 */
	public ShablonPanel() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		JSplitPane splitPane = new JSplitPane();
		splitPane.setBorder(null);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JScrollPane scpCdol = new JScrollPane();
		scpCdol.setMinimumSize(new Dimension(256, 256));
		splitPane.setLeftComponent(scpCdol);
		
		lstCdol = new ThriftStringClassifierList<>();
		lstCdol.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		lstCdol.addListSelectionListener(new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting())
					if (lstCdol.getSelectedValue() != null)
						createShablonTree(lstCdol.getSelectedValue().getPcod());
			}
		});
		scpCdol.setViewportView(lstCdol);
		
		JScrollPane scpShablon = new JScrollPane();
		scpShablon.setMinimumSize(new Dimension(256, 256));
		splitPane.setRightComponent(scpShablon);
		
		treeShablon = new JTree();
		treeShablon.setShowsRootHandles(true);
		treeShablon.setRootVisible(false);
		treeShablon.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		treeShablon.addTreeExpansionListener(new TreeExpansionListener() {
			public void treeCollapsed(TreeExpansionEvent event) {
			}
			
			public void treeExpanded(TreeExpansionEvent event) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();
				
				if (node instanceof ShabonPokTreeItem)
					showShablonTextNodes((ShabonPokTreeItem) node);
			}
		});
		treeShablon.setCellRenderer(new TreeNodeRenderer());
		treeShablon.setCellEditor(new TreeNodeEditor(treeShablon, (DefaultTreeCellRenderer) treeShablon.getCellRenderer()));
		treeShablon.setEditable(true);
		treeShablon.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "startEditAtPath");
		treeShablon.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0), "startEditAtPath");
		treeShablon.getActionMap().put("startEditAtPath", new AbstractAction() {
			private static final long serialVersionUID = -8809761160709260277L;

			@Override
			public void actionPerformed(ActionEvent e) {
				TreePath path = treeShablon.getSelectionPath();
				
				if (path != null) {
					if (path.getLastPathComponent() instanceof IShablonTreeItemRendererEditor) {
						IShablonTreeItemRendererEditor node = (IShablonTreeItemRendererEditor) path.getLastPathComponent();
						
						if (node.getEditor() instanceof TreeCheckBox) {
							TreeCheckBox chb = (TreeCheckBox) node.getEditor(); 
							
							if (chb.isEnabled())
								chb.doClick();
						} else
							treeShablon.startEditingAtPath(path);
					}
				}
	        }
	    });
		scpShablon.setViewportView(treeShablon);
		this.setLayout(groupLayout);
	}
	
	public void setCdolList(List<StringClassifier> lst) {
		lstCdol.setData(lst);
		if (lstCdol.getModel().getSize() > 0)
			lstCdol.setSelectedIndex(0);
	}
	
	private void createShablonTree(String cdol) {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("root of evil");
		
		try {
			for (ShablonRazd razd : MainForm.tcl.getShabRazd()) {
				ShablonRazdTreeItem shRazdTreeItem = new ShablonRazdTreeItem(razd);
				root.add(shRazdTreeItem);
				for (ShablonPok pok : MainForm.tcl.getShabPok(razd.getId(), cdol))
					shRazdTreeItem.add(new ShabonPokTreeItem(pok));
				shRazdTreeItem.setState();
			}
		} catch (TException e) {
			e.printStackTrace();
			MainForm.conMan.reconnect(e);
		}
		
		treeShablon.setModel(new DefaultTreeModel(root));
		treeShablon.setSelectionPath(new TreePath(root.getPath()));
	}
	
	private void showShablonTextNodes(ShabonPokTreeItem shPokNode) {
		try {
			List<ShablonText> lstShText = MainForm.tcl.getShablonTextsEdit(shPokNode.shPok, lstCdol.getSelectedPcod());
			
			shPokNode.removeAllChildren();
			shPokNode.addEmptyShablonTextNode();
			for (ShablonText shText : lstShText)
				shPokNode.add(new ShablonTextTreeItem(shText));
			((DefaultTreeModel) treeShablon.getModel()).reload(shPokNode);
		} catch (TException e) {
			e.printStackTrace();
			MainForm.conMan.reconnect(e);
		}
	}
	
	class ShablonRazdTreeItem extends DefaultMutableTreeNode implements IShablonTreeItemRendererEditor {
		private static final long serialVersionUID = -3302984346461329807L;
		private ShablonRazd shRazd = new ShablonRazd();
		private TreeCheckBox chb;
		private boolean intermediate;
		
		public ShablonRazdTreeItem(ShablonRazd shRazd) {
			chb = new TreeCheckBox(shRazd.getName(), this); 
			setActionListener();
			
			setShablonRazd(shRazd);
		}
		
		public void setShablonRazd(ShablonRazd shRazd) {
			this.shRazd = shRazd;
		}
		
		private void setActionListener() {
			chb.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					//TODO stub
					try {
						MainForm.tcl.setShabPokGrup(shRazd, lstCdol.getSelectedPcod(), chb.isSelected());
						for (Enumeration<?> en = ShablonRazdTreeItem.this.children(); en.hasMoreElements();) {
							TristateCheckBox pokChb = (TristateCheckBox) ((IShablonTreeItemRendererEditor) en.nextElement()).getEditor();
							
							pokChb.setSelected(chb.isSelected());
						}
						repaint();
					} catch (TException e1) {
						if (intermediate)
							chb.setIntermediateState();
						else
							chb.setSelected(!chb.isSelected());
						
						MainForm.conMan.reconnect(e1);
						e1.printStackTrace();
					}
				}
			});
		}
		
		@Override
		public Component getEditor() {
			return chb;
		}

		@Override
		public Component getRenderer() {
			return chb;
		}
		
		@Override
		public String toString() {
			return shRazd.getName();
		}
		
		public void setState() {
			boolean allSelected = true;
			boolean allDeselected = true;
			intermediate = false;
			
			for (int i = 0; i < getChildCount(); i++) {
				ShabonPokTreeItem shPokTreeItem = (ShabonPokTreeItem) getChildAt(i);
				
				allSelected &= shPokTreeItem.shPok.checked;
				allDeselected &= !shPokTreeItem.shPok.checked;
			}
			if ((allSelected == false) && (allDeselected == false)) {
				chb.setIntermediateState();
				intermediate = true;
			} else if (allSelected == allDeselected) {
				chb.setSelected(false);
				chb.setEnabled(false);
			} else
				chb.setSelected(allSelected);
			repaint();
		}

		private void repaint() {
			treeShablon.repaint();
		}
	}

	class ShabonPokTreeItem extends DefaultMutableTreeNode implements IShablonTreeItemRendererEditor {
		private static final long serialVersionUID = 2594372947119884143L;
		private TreeCheckBox chb;
		private ShablonPok shPok;
		
		public ShabonPokTreeItem(ShablonPok shPok) {
			chb = new TreeCheckBox(shPok.getName(), this);
			setActionListener();
			
			setShablonPok(shPok);
			addEmptyShablonTextNode();
		}
		
		private void setActionListener() {
			chb.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						shPok.setChecked(chb.isSelected());
						MainForm.tcl.setShabPok(shPok, lstCdol.getSelectedValue().getPcod());
						((ShablonRazdTreeItem) getParent()).setState();
					} catch (TException e1) {
						chb.setSelected(!chb.isSelected());
						
						e1.printStackTrace();
						MainForm.conMan.reconnect(e1);
					}
				}
			});
		}
		
		private void setShablonPok(ShablonPok shPok) {
			this.shPok = shPok;
			
			chb.setSelected(shPok.checked);
		}
		
		@Override
		public Component getEditor() {
			return chb;
		}

		@Override
		public Component getRenderer() {
			return chb;
		}
		
		@Override
		public String toString() {
			return shPok.getName();
		}
		
		public void addEmptyShablonTextNode() {
			this.add(new ShablonTextTreeItem(new ShablonText().setId_razd(shPok.getId_razd()).setId_pok(shPok.getId()).setPcod_s00(lstCdol.getSelectedPcod())));
		}
	}
	
	class ShablonTextTreeItem extends DefaultMutableTreeNode implements IShablonTreeItemRendererEditor {
		private static final long serialVersionUID = -9160828241062604472L;
		private static final String emptyNodeText = "Добавить шаблон";
		private boolean emptyNode;
		private CustomTextField txt;
		private JLabel lbl;
		private ShablonText shabText;
		
		public ShablonTextTreeItem(ShablonText shabText) {
			txt = new CustomTextField();
			setActionListener();
			
			lbl = new JLabel();
			lbl.setBorder(emptyBorder);
			lbl.setOpaque(true);
			
			setShablonText(shabText);
		}
		
		public void setShablonText(ShablonText shabText) {
			emptyNode = shabText.getId() == 0;
			if (emptyNode)
				shabText.setText(emptyNodeText);
			
			this.shabText = shabText;
			
			setText(this.toString());
		}
		
		public void setText(String text) {
			txt.setText(text);
			lbl.setText(text);
		}
		
		private void setActionListener() {
		    txt.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelEdit");
		    txt.getActionMap().put("cancelEdit", new AbstractAction() {
				private static final long serialVersionUID = -798462897958834499L;

				@Override
				public void actionPerformed(ActionEvent e) {
					setText(ShablonTextTreeItem.this.toString());
					treeShablon.cancelEditing();
		        }
		    });
		    
		    txt.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "commitEdit");
		    txt.getActionMap().put("commitEdit", new AbstractAction() {
				private static final long serialVersionUID = -3233764341434428998L;
				
				@Override
				public void actionPerformed(ActionEvent e) {
					if (txt.getText().length() == 0) {
						JOptionPane.showMessageDialog(ShablonPanel.this, "Текст шаблона не может быть пустым." , "Ошибка", JOptionPane.WARNING_MESSAGE);
						return;
					} else if (isClone()) {
						JOptionPane.showMessageDialog(ShablonPanel.this, "Шаблон с таким текстом уже существует." , "Ошибка", JOptionPane.WARNING_MESSAGE);
						return;
					}
					
					if (!txt.getText().toLowerCase().equals(emptyNodeText.toLowerCase())) {
						ShablonText shabTextCopy = new ShablonText(shabText);
						
						shabTextCopy.setText(txt.getText());
						if (emptyNode) {
							try {
								shabTextCopy.setId(MainForm.tcl.addShablonText(shabTextCopy));
								setShablonText(shabTextCopy);
								treeShablon.stopEditing();
								ShabonPokTreeItem root = (ShabonPokTreeItem) getParent();
								showShablonTextNodes(root);
								treeShablon.setSelectionPath(new TreePath(root.getFirstLeaf().getPath()));
								treeShablon.repaint();
							} catch (TException e1) {
								e1.printStackTrace();
								MainForm.conMan.reconnect(e1);
							}
						} else {
							try {
								MainForm.tcl.updateShablonText(shabTextCopy);
								setShablonText(shabTextCopy);
								treeShablon.stopEditing();
							} catch (TException e1) {
								e1.printStackTrace();
								MainForm.conMan.reconnect(e1);
							}
						}
					}
		        }
				
				private boolean isClone() {
					ShabonPokTreeItem root = (ShabonPokTreeItem) getParent();
					String lowText = txt.getText().toLowerCase();
					
					for (Enumeration<?> e = root.children(); e.hasMoreElements();) {
						ShablonTextTreeItem node = (ShablonTextTreeItem) e.nextElement();
						
						if (!node.equals(ShablonTextTreeItem.this))
							if (node.toString().toLowerCase().equals(lowText))
								return true;
					}
					
					return false;
				}
		    });
		}
		
		@Override
		public Component getEditor() {
			return txt;
		}

		@Override
		public Component getRenderer() {
			return lbl;
		}
		
		@Override
		public String toString() {
			return shabText.getText();
		}
	}
	
	interface IShablonTreeItemRendererEditor {
		Component getEditor();
		Component getRenderer();
	}

	class TreeNodeRenderer extends DefaultTreeCellRenderer {
		private static final long serialVersionUID = -6572056531466536339L;
		
		@Override
		public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
			if (value instanceof IShablonTreeItemRendererEditor) {
				Component cmp = (Component) ((IShablonTreeItemRendererEditor) value).getRenderer();
				
				if (selected) {
					cmp.setForeground(nodeSelectionForeground);
					cmp.setBackground(nodeSelectionBackground);
//					BasicGraphicsUtils.drawDashedRect(chb.getGraphics(), 0, 0, chb.getWidth(), chb.getHeight());
				} else {
					cmp.setForeground(nodeTextForeground);
					cmp.setBackground(nodeTextBackground);
				}
				
				return cmp;
			} else
				return super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);
		}
	}

	class TreeNodeEditor extends DefaultTreeCellEditor {

		public TreeNodeEditor(JTree tree, DefaultTreeCellRenderer renderer) {
			super(tree, renderer);
		}
		
		@Override
		public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
			if (value instanceof IShablonTreeItemRendererEditor) {
				Component cmp = ((IShablonTreeItemRendererEditor) value).getEditor();
				
				if (cmp instanceof JTextComponent) {
					Rectangle r = tree.getPathBounds(new TreePath(((DefaultMutableTreeNode) value).getPath()));
					cmp.setPreferredSize(new Dimension(tree.getWidth() - r.x, cmp.getHeight()));
					tree.revalidate();
				}
				return cmp;
			} else
				return super.getTreeCellEditorComponent(tree, value, isSelected, expanded, leaf, row);
		}
		
		@Override
		public boolean isCellEditable(EventObject event) {
			DefaultMutableTreeNode node = null;
			
			if (event == null) {
				node = (DefaultMutableTreeNode) treeShablon.getSelectionPath().getLastPathComponent();
			} else {
				MouseEvent me = (MouseEvent) event;
				node = (DefaultMutableTreeNode) treeShablon.getPathForLocation(me.getX(), me.getY()).getLastPathComponent();
			}
			
			if (node instanceof IShablonTreeItemRendererEditor)
				return ((IShablonTreeItemRendererEditor) node).getEditor().isEnabled();
			else
				return false;
		}
	}
	
	class TreeCheckBox extends TristateCheckBox {
		private static final long serialVersionUID = 493364588247351255L;
		DefaultMutableTreeNode node;

		public TreeCheckBox(String text, DefaultMutableTreeNode node) {
			this(text, false, node);
		}
		
		public TreeCheckBox(String text, boolean checked, DefaultMutableTreeNode node) {
			super(text, checked);
			
			this.node = node; 
			
			setFocus();
		}
		
		private void setFocus() {
			setFocusPainted(false);
			
			addFocusListener(new FocusListener() {
				
				@Override
				public void focusLost(FocusEvent e) {
				}
				
				@Override
				public void focusGained(FocusEvent e) {
					setForeground(nodeSelectionForeground);
					setBackground(nodeSelectionBackground);
					
					transferFocusBackward();
					treeShablon.setSelectionPath(new TreePath(node.getPath()));
				}
			});
		}
	}
}
