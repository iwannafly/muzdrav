package ru.nkz.ivcgzo.clientAdmin;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.GroupLayout.ParallelGroup;
import javax.swing.GroupLayout.SequentialGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
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
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextComponentWrapper;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierListCheckbox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierListCheckbox.ThriftIntegerClassifierListCheckboxActionEvent;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierListCheckbox.ThriftIntegerClassifierListCheckboxActionListener;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftServerAdmin.ShablonOsm;

public class ShablonPanel extends JPanel {
	private static final long serialVersionUID = 3633761920972893528L;
	private CustomTextField tbSearch;
	private SearchTree trSearch;
	private CustomTextField tbName;
	private CustomTextField tbDiag;
	private ThriftIntegerClassifierCombobox<IntegerClassifier> cbDyn;
	private JCheckBox cbSluPol;
	private JCheckBox cbSluStat;
	private ThriftIntegerClassifierListCheckbox ltSpec;
	private JScrollPane spText;
	private JPanel pnText;
	private List<ShablonTextPanel> shPanList;
	private DocumentListener textListener;
	private JButton btSaveAsNew;
	private JButton btSave;
	private ShablonOsm shOsm;
	private boolean fillingUI;
	
	public ShablonPanel() {
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
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 701, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 467, Short.MAX_VALUE)
		);
		
		JPanel gbSearch = new JPanel();
		gbSearch.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Поиск", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		gbSearch.setMinimumSize(new Dimension(128, 128));
		splitPane.setLeftComponent(gbSearch);
		
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
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_gbSearch = new GroupLayout(gbSearch);
		gl_gbSearch.setHorizontalGroup(
			gl_gbSearch.createParallelGroup(Alignment.LEADING)
				.addComponent(tbSearch, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
		);
		gl_gbSearch.setVerticalGroup(
			gl_gbSearch.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_gbSearch.createSequentialGroup()
					.addComponent(tbSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 415, Short.MAX_VALUE))
		);
		
		trSearch = new SearchTree();
		scrollPane.setViewportView(trSearch);
		gbSearch.setLayout(gl_gbSearch);
		
		JPanel gbEdit = new JPanel();
		gbEdit.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Редактирование", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		gbEdit.setMinimumSize(new Dimension(128, 128));
		splitPane.setRightComponent(gbEdit);
		
		btSaveAsNew = new JButton("Сохранить как новый");
		btSaveAsNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveShablonAsNew();
			}
		});
		
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
		
		tbDiag = new CustomTextField();
		tbDiag.getDocument().addDocumentListener(textListener);
		tbDiag.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					StringClassifier res = MainForm.conMan.showMkbTreeForm("Диагноз для шаблона", tbDiag.getText());
					
					if (res != null) {
						tbDiag.setText(res.pcod);
						tbName.setText(res.name);
					}
				}
			}
		});
		tbDiag.setColumns(10);
		
		JLabel lbDiag = new JLabel("Диагноз");
		
		JPanel gbAvail = new JPanel();
		gbAvail.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Доступность", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel gbText = new JPanel();
		gbText.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Тексты", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblNewLabel = new JLabel("Динамика");
		
		cbDyn = new ThriftIntegerClassifierCombobox<>(IntegerClassifiers.n_din, false, null);
		GroupLayout gl_gbEdit = new GroupLayout(gbEdit);
		gl_gbEdit.setHorizontalGroup(
			gl_gbEdit.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_gbEdit.createSequentialGroup()
					.addGroup(gl_gbEdit.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(lbDiag, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lbName, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_gbEdit.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_gbEdit.createSequentialGroup()
							.addComponent(tbDiag, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cbDyn, 0, 305, Short.MAX_VALUE))
						.addComponent(tbName, GroupLayout.DEFAULT_SIZE, 485, Short.MAX_VALUE)))
				.addGroup(gl_gbEdit.createSequentialGroup()
					.addComponent(btSaveAsNew, GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btSave, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
				.addGroup(gl_gbEdit.createSequentialGroup()
					.addComponent(gbAvail, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(gbText, GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE))
		);
		gl_gbEdit.setVerticalGroup(
			gl_gbEdit.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_gbEdit.createSequentialGroup()
					.addGroup(gl_gbEdit.createParallelGroup(Alignment.BASELINE)
						.addComponent(tbName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbName))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_gbEdit.createParallelGroup(Alignment.BASELINE)
						.addComponent(tbDiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbDiag)
						.addComponent(lblNewLabel)
						.addComponent(cbDyn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_gbEdit.createParallelGroup(Alignment.BASELINE)
						.addComponent(gbAvail, GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE)
						.addComponent(gbText, GroupLayout.DEFAULT_SIZE, 363, Short.MAX_VALUE))
					.addGap(6)
					.addGroup(gl_gbEdit.createParallelGroup(Alignment.BASELINE)
						.addComponent(btSaveAsNew)
						.addComponent(btSave)))
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
		
		pnText = new JPanel();
		spText.setViewportView(pnText);
		GroupLayout gl_pnText = new GroupLayout(pnText);
		gl_pnText.setHorizontalGroup(
			gl_pnText.createParallelGroup(Alignment.LEADING)
				.addGap(0, 200, Short.MAX_VALUE)
		);
		gl_pnText.setVerticalGroup(
			gl_pnText.createParallelGroup(Alignment.LEADING)
				.addGap(0, 200, Short.MAX_VALUE)
		);
		pnText.setLayout(gl_pnText);
		gbText.setLayout(gl_gbText);
		
		JLabel lbAvailSlu = new JLabel("Службы");
		
		cbSluPol = new JCheckBox("Поликлиника");
		
		JLabel lbSpec = new JLabel("Специальности");
		
		cbSluStat = new JCheckBox("Стационар");
		
		JScrollPane spSpec = new JScrollPane();
		GroupLayout gl_gbAvail = new GroupLayout(gbAvail);
		gl_gbAvail.setHorizontalGroup(
			gl_gbAvail.createParallelGroup(Alignment.LEADING)
				.addComponent(lbSpec, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
				.addComponent(lbAvailSlu, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
				.addComponent(cbSluPol, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
				.addComponent(cbSluStat, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
				.addComponent(spSpec, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
		);
		gl_gbAvail.setVerticalGroup(
			gl_gbAvail.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_gbAvail.createSequentialGroup()
					.addComponent(lbAvailSlu)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbSluPol)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbSluStat)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lbSpec)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spSpec, GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
		);
		
		ltSpec = new ThriftIntegerClassifierListCheckbox(IntegerClassifiers.n_spec);
		ltSpec.setCheckboxActionListener(new ThriftIntegerClassifierListCheckboxActionListener() {
			
			@Override
			public void actionPerformed(ThriftIntegerClassifierListCheckboxActionEvent e) {
				checkInput();
			}
		});
		spSpec.setViewportView(ltSpec);
		gbAvail.setLayout(gl_gbAvail);
		
		gbEdit.setLayout(gl_gbEdit);
		setLayout(groupLayout);
	}
	
	public class ShablonTextPanel extends JPanel {
		private static final long serialVersionUID = -1390237786861031813L;
		private int razdId;
		private JLabel lbl;
		private JTextArea txt;
		private CustomTextComponentWrapper wrp;
		
		public ShablonTextPanel(IntegerClassifier razd) {
			super();
			
			razdId = razd.pcod;
			
			lbl = new JLabel(razd.name);
			
			JScrollPane sp = new JScrollPane();
			sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			GroupLayout gl = new GroupLayout(this);
			gl.setHorizontalGroup(
				gl.createParallelGroup(Alignment.LEADING)
					.addGroup(Alignment.TRAILING, gl.createSequentialGroup()
						.addContainerGap()
						.addGroup(gl.createParallelGroup(Alignment.TRAILING)
							.addComponent(sp, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
							.addComponent(lbl, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE))
						.addContainerGap())
			);
			gl.setVerticalGroup(
				gl.createParallelGroup(Alignment.LEADING)
					.addGroup(gl.createSequentialGroup()
						.addComponent(lbl)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(sp, GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE))
			);
			
			txt = new JTextArea();
			txt.getDocument().addDocumentListener(textListener);
			txt.setLineWrap(true);
			sp.setViewportView(txt);
			setLayout(gl);
			
			wrp = new CustomTextComponentWrapper(txt);
			wrp.setPopupMenu();
		}
		
		public String getText() {
			return txt.getText();
		}
		
		public void setText(String text) {
			txt.setText(text);
		}
		
		public void clearText() {
			txt.setText("");
		}
		
		public int getRazdId() {
			return razdId;
		}
		
		public IntegerClassifier getShablonText() {
			if (!getText().isEmpty())
				return new IntegerClassifier(getRazdId(), getText());
			else
				return null;
		}
	}
	
	public void prepareShTextFields() {
		pnText.removeAll();
		shPanList = new ArrayList<>();
		clearFields();
		
		GroupLayout glPnText = new GroupLayout(pnText);
		ParallelGroup horzGroup = glPnText.createParallelGroup(Alignment.LEADING);
		SequentialGroup vertGroup = glPnText.createSequentialGroup();
		
		try {
			ltSpec.reloadClassifier();
			for (IntegerClassifier osmSh : MainForm.tcl.getReqShOsmList()) {
				ShablonTextPanel stp = new ShablonTextPanel(osmSh);
				shPanList.add(stp);
				
				glPnText.setHorizontalGroup(
						horzGroup.addComponent(stp, GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
				);
				
				glPnText.setVerticalGroup(
						vertGroup.addGroup(glPnText.createSequentialGroup()
							.addContainerGap()
							.addComponent(stp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap()
							.addPreferredGap(ComponentPlacement.RELATED))
				);
			}
			trSearch.updateNow(tbSearch.getText());
		} catch (KmiacServerException e) {
			e.printStackTrace();
		} catch (TException e) {
			MainForm.conMan.reconnect(e);
		}
		
		pnText.scrollRectToVisible(new Rectangle());
		pnText.setLayout(glPnText);
	}
	
	private void clearFields() {
		tbName.clear();
		tbDiag.clear();
		cbDyn.setSelectedPcod(0);
		cbSluPol.setSelected(false);
		cbSluStat.setSelected(false);
		ltSpec.unselectAllItems();
		
		for (ShablonTextPanel txtPan : shPanList)
			txtPan.clearText();
	}
	
	private void saveShablonAsNew() {
		shOsm = new ShablonOsm();
		saveShablon();
	}
	
	private void saveShablon() {
		fillShablonFromUI();
		try {
			shOsm.setId(MainForm.tcl.saveShablonOsm(shOsm));
		} catch (KmiacServerException e) {
			
		} catch (TException e) {
			MainForm.conMan.reconnect(e);
		}
	}
	
	private void fillShablonFromUI() {
		shOsm.setName(tbName.getText());
		shOsm.setDiag(tbDiag.getText());
		shOsm.setCDin(cbDyn.getSelectedPcod());
		shOsm.setCslu((cbSluPol.isSelected() ? 1 : 0) | (((cbSluStat.isSelected()) ? 1 : 0) << 1));
		
		List<Integer> specList = new ArrayList<>();
		for (IntegerClassifier spc : ltSpec.getSelectedItems())
			specList.add(spc.pcod);
		shOsm.setSpecList(specList);
		
		List<IntegerClassifier> txtList = new ArrayList<>();
		for (ShablonTextPanel txtPan : shPanList) {
			IntegerClassifier txt = txtPan.getShablonText();
			
			if (txt != null)
				txtList.add(txt);
		}
		shOsm.setTextList(txtList);
	}
	
	private void checkInput() {
		if (fillingUI)
			return;
		
		boolean enb = false;
		
		for (ShablonTextPanel txtPan : shPanList)
			if (!txtPan.getText().isEmpty()) {
				enb = true;
				break;
			}
		
		if (enb) {
			enb &= !tbName.isEmpty();
			enb &= !tbDiag.isEmpty();
			enb &= !ltSpec.isAllItemsUnselected();
		}
		
		btSave.setEnabled(enb);
		btSaveAsNew.setEnabled(enb);
	}
	
	private void loadShablon(int id) {
		try {
			shOsm = MainForm.tcl.getShablonOsm(id);
			fillUIFromShablon();
		} catch (KmiacServerException e) {
			JOptionPane.showMessageDialog(this, "Ошибка загрузки шаблона.", "Ошибка", JOptionPane.ERROR_MESSAGE);
		} catch (TException e) {
			MainForm.conMan.reconnect(e);
		}
	}
	
	private void fillUIFromShablon() {
		fillingUI = true;
		
		try {
			tbName.setText(shOsm.name);
			tbDiag.setText(shOsm.diag);
			cbDyn.setSelectedPcod(shOsm.cDin);
			cbSluPol.setSelected((shOsm.cslu & 1) == 1);
			cbSluStat.setSelected((shOsm.cslu & 2) == 2);
			
			List<IntegerClassifier> icList = new ArrayList<>();
			for (Integer ic : shOsm.specList)
				icList.add(new IntegerClassifier(ic, null));
			ltSpec.unselectAllItems();
			ltSpec.selectItems(icList);
			
			for (ShablonTextPanel txtPan : shPanList) {
				txtPan.clearText();
				for (IntegerClassifier ic : shOsm.textList)
					if (txtPan.getRazdId() == ic.pcod) {
						txtPan.setText(ic.name);
						break;
					}
			}
			
			pnText.scrollRectToVisible(new Rectangle());
			ltSpec.scrollRectToVisible(new Rectangle());
			
			fillingUI = false;
			checkInput();
		} finally {
			fillingUI = false;
		}
	}
	
	private class SearchTree extends JTree {
		private static final long serialVersionUID = -6009216318295458571L;
		private Timer timer;
		private String srcStr;
		
		public SearchTree() {
			setShowsRootHandles(true);
			setRootVisible(false);
			
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
							for (IntegerClassifier ic : MainForm.tcl.getShablonOsmListByDiag(node.getCode()))
								node.add(new IntClassTreeNode(ic));
							((DefaultTreeModel) getModel()).reload(node);
						} catch (KmiacServerException e) {
							collapsePath(new TreePath(lp));
							JOptionPane.showMessageDialog(ShablonPanel.this, "Ошибка загрузки шаблонов на данный диагноз", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
				
				for (StringClassifier sc : MainForm.tcl.getShablonOsmDiagList(srcStr)) {
					StrClassTreeNode node = new StrClassTreeNode(sc);
					
					node.add(new IntClassTreeNode(new IntegerClassifier(-1, "Dummy")));
					root.add(node);
				}
				setModel(new DefaultTreeModel(root));
			} catch (KmiacServerException e) {
				JOptionPane.showMessageDialog(ShablonPanel.this, "Ошибка загрузки результатов поиска", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
