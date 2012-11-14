package ru.nkz.ivcgzo.clientViewSelect.modalForms;

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.ModalForm;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.clientViewSelect.MainForm;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftViewSelect.mkb_0;
import ru.nkz.ivcgzo.thriftViewSelect.mkb_1;
import ru.nkz.ivcgzo.thriftViewSelect.mkb_2;
import javax.swing.JLabel;

public class ViewMkbTreeForm extends ModalForm {
	private static final long serialVersionUID = -162973903323760204L;
	private List<mkb_0> mkbTree, lowMkbTree, fltMkbTree;
	private JPanel contentPane;
	private JTree tree;
	private CustomTextField textField;
	
	/**
	 * Create the frame.
	 */
	public ViewMkbTreeForm() {
		super(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		
		textField = new CustomTextField(true, true, false);
		textField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				filter(textField.getText());
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				filter(textField.getText());
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				filter(textField.getText());
			}
		});
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					if (textField.getDocument().getLength() > 0)
						textField.clear();
					else
						declineResults();
				else
					switch (e.getKeyCode()) {
					case KeyEvent.VK_UP:
					case KeyEvent.VK_DOWN:
					case KeyEvent.VK_LEFT:
					case KeyEvent.VK_RIGHT:
					case KeyEvent.VK_ENTER:
						tree.dispatchEvent(e);
						break;
					}
			}
		});
		textField.setColumns(10);
		
		JLabel label = new JLabel("Фильтр");
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, gl_contentPane.createSequentialGroup()
							.addComponent(label, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 671, Short.MAX_VALUE))
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 762, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 503, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tree = new JTree();
		tree.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					acceptResults();
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					declineResults();
			}
		});
		tree.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					acceptResults();
			}
		});
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) tree.getCellRenderer();
		renderer.setLeafIcon(null);
		renderer.setClosedIcon(null);
		renderer.setOpenIcon(null);
		
		scrollPane.setViewportView(tree);
		contentPane.setLayout(gl_contentPane);
	}
	
	@Override
	public void acceptResults() {
		if (tree.getSelectionPath() == null)
			return;
		
		Object sel = tree.getSelectionPath().getLastPathComponent();
		
		if (sel instanceof mkb_2)
			if (((mkb_2) sel).getMlb3Size() == 0)
				results = new StringClassifier(((mkb_2) sel).pcod, ((mkb_2) sel).name);
			else
				return;
		else if (sel instanceof StringClassifier)
			results = sel;
		else
			return;
		
		super.acceptResults();
	}
	
	@Override
	public StringClassifier getResults() {
		if (results != null)
			return (StringClassifier) results;
		
		return null;
	}
	
	public void prepare(String pcod) {
		if (mkbTree == null)
			try {
				mkbTree = MainForm.ccm.getMkbTreeClassifier();
				fltMkbTree = mkbTree;
				copyMkbTree();
				setModel();
			} catch (KmiacServerException e) {
				e.printStackTrace();
			} catch (TException e) {
				e.printStackTrace();
				MainForm.conMan.reconnect(e);
			}
		else
			((DefaultTreeModel) tree.getModel()).reload();
		textField.clear();
		tree.setSelectionPath(new TreePath(new Object[] {tree.getModel().getRoot(), tree.getModel().getChild(tree.getModel().getRoot(), 0)}));
		if (pcod != null && pcod.length() > 2)
			selectPcod(pcod);
	}
	
	private void copyMkbTree() {
		lowMkbTree = new ArrayList<>();
		
		for (mkb_0 m0 : mkbTree) {
			mkb_0 ml0 = new mkb_0(null, m0.name.toLowerCase(), null, new ArrayList<mkb_1>());
			for (mkb_1 m1 : m0.mlb1) {
				mkb_1 ml1 = new mkb_1(null, null, m1.name.toLowerCase(), new ArrayList<mkb_2>());
				for (mkb_2 m2 : m1.mkb2) {
					mkb_2 ml2 = new mkb_2(m2.pcod.toLowerCase(), m2.name.toLowerCase(), new ArrayList<StringClassifier>());
					for (StringClassifier sc : m2.mlb3) {
						ml2.addToMlb3(new StringClassifier(sc.pcod.toLowerCase(), sc.name.toLowerCase()));
					}
					ml1.addToMkb2(ml2);
				}
				ml0.addToMlb1(ml1);
			}
			lowMkbTree.add(ml0);
		}
	}

	private void setModel() {
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Root")) {
			private static final long serialVersionUID = -5985759695071621123L;

			@Override
			public boolean isLeaf(Object node) {
				if (node instanceof mkb_2)
					return ((mkb_2) node).mlb3.size() == 0;
				else
					return node instanceof StringClassifier;
			}
			
			@Override
			public int getChildCount(Object parent) {
				if (parent instanceof DefaultMutableTreeNode)
					return fltMkbTree.size();
				else if (parent instanceof mkb_0)
					return ((mkb_0) parent).mlb1.size();
				else if (parent instanceof mkb_1)
					return ((mkb_1) parent).mkb2.size();
				else if (parent instanceof mkb_2)
					return ((mkb_2) parent).mlb3.size();
				else
					return -1;
			}
			
			@Override
			public Object getChild(Object parent, int index) {
				if (parent instanceof DefaultMutableTreeNode)
					return new Mkb0Str(fltMkbTree.get(index));
				else if (parent instanceof mkb_0)
					return new Mkb1Str(((mkb_0) parent).mlb1.get(index));
				else if (parent instanceof mkb_1)
					return new Mkb2Str(((mkb_1) parent).mkb2.get(index));
				else if (parent instanceof mkb_2)
					return new StringClassifierStr(((mkb_2) parent).mlb3.get(index));
				else
					return null;
			}
			
			@Override
			public int getIndexOfChild(Object parent, Object child) {
				if (parent instanceof DefaultMutableTreeNode)
					return fltMkbTree.indexOf(child);
				else if (parent instanceof mkb_0)
					return ((mkb_0) parent).mlb1.indexOf(child);
				else if (parent instanceof mkb_1)
					return ((mkb_1) parent).mkb2.indexOf(child);
				else if (parent instanceof mkb_2)
					return ((mkb_2) parent).mlb3.indexOf(child);
				else
					return -1;
			}
		});
	}
	
	private void selectPcod(String pcod) {
		String grup = pcod.substring(0, 3);
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		for (int i = 0; i < tree.getModel().getChildCount(root); i++) {
			mkb_0 mkb0 = (mkb_0) tree.getModel().getChild(root, i);
			if (grup.compareTo(mkb0.kod_mkb.substring(0, 3)) >=0) {
				for (int j = 0; j < mkb0.mlb1.size(); j++) {
					mkb_1 mkb1 = mkb0.mlb1.get(j);
					if (grup.compareTo(mkb1.pcod.substring(0, 3)) >=0) {
						for (int k = 0; k < mkb1.mkb2.size(); k++) {
							mkb_2 mkb2 = mkb1.mkb2.get(k);
							if (mkb2.pcod.startsWith(grup)) {
								if (mkb2.pcod.equals(pcod)) {
									scrollToPath(new TreePath(new Object[] {root, mkb0, mkb1, mkb2}));
									return;
								} else
									for (int l = 0; l < mkb2.mlb3.size(); l++) {
										StringClassifier sc = mkb2.mlb3.get(l);
										if (sc.pcod.equals(pcod)) {
											scrollToPath(new TreePath(new Object[] {root, mkb0, mkb1, mkb2, sc}));
											return;
										}
									}
							}
						}
					}
				}
			}
		}
		
		scrollToPath(new TreePath(new Object[] {root, tree.getModel().getChild(root, 0)}));
	}
	
	private void scrollToPath(TreePath path) {
		tree.setSelectionPath(path);
		Rectangle bounds = tree.getPathBounds(path);
		bounds.x = 0;
		bounds.y -= (tree.getPreferredSize().height - bounds.height) / 4;
		if (bounds.y < 0)
			bounds.y = 0;
		tree.scrollRectToVisible(bounds);
	}
	
	public void filter(String cond) {
		if (cond.length() == 0) {
			fltMkbTree = mkbTree;
		} else {
			fltMkbTree = new ArrayList<>();
			
			cond = cond.toLowerCase();
			for (int i0 = 0; i0 < mkbTree.size(); i0++) {
				mkb_0 m0 = mkbTree.get(i0);
				mkb_0 ml0 = lowMkbTree.get(i0);
				if (ml0.name.indexOf(cond) > -1)
					fltMkbTree.add(m0);
				else {
					mkb_0 mn0 = new mkb_0(m0.pcod, m0.name, m0.kod_mkb, new ArrayList<mkb_1>());
					for (int i1 = 0; i1 < m0.getMlb1Size(); i1++) {
						mkb_1 m1 = m0.mlb1.get(i1);
						mkb_1 ml1 = ml0.mlb1.get(i1);
						if (ml1.name.indexOf(cond) > -1) {
							mn0.addToMlb1(m1);
						} else {
							mkb_1 mn1 = new mkb_1(m1.pcod, m1.klass, m1.name, new ArrayList<mkb_2>());
							for (int i2 = 0; i2 < m1.getMkb2Size(); i2++) {
								mkb_2 m2 = m1.mkb2.get(i2);
								mkb_2 ml2 = ml1.mkb2.get(i2);
								if ((ml2.name.indexOf(cond) > -1) || (ml2.pcod.indexOf(cond) > -1)) {
									mn1.addToMkb2(m2);
								} else {
									List<StringClassifier> mn3 = new ArrayList<>();
									mkb_2 mn2 = new mkb_2(m2.pcod, m2.name, mn3);
									for (int i3 = 0; i3 < m2.getMlb3Size(); i3++) {
										StringClassifier sc = m2.mlb3.get(i3);
										StringClassifier sl = ml2.mlb3.get(i3);
										if ((sl.name.indexOf(cond) > -1) || (sl.pcod.indexOf(cond) > -1))
											mn3.add(sc);
									}
									if (mn3.size() > 0) {
										mn2.mlb3 = mn3;
										mn1.addToMkb2(mn2);
									}
								}
							}
							if (mn1.getMkb2Size() > 0) {
								mn0.addToMlb1(mn1);
							}
						}
					}
					if (mn0.getMlb1Size() > 0) {
						fltMkbTree.add(mn0);
					}
				}
			}
		}
		((DefaultTreeModel) tree.getModel()).reload();
		if (fltMkbTree.size() > 0)
			tree.setSelectionPath(new TreePath(new Object[] {tree.getModel().getRoot(), fltMkbTree.get(0)}));
	}
	
	class Mkb0Str extends mkb_0 {
		private static final long serialVersionUID = -6810587469602995591L;

		public Mkb0Str(mkb_0 mkb0) {
			super(mkb0);
		}
		
		@Override
		public String toString() {
			return String.format("(%s) %s", kod_mkb, name.substring(0, name.length() - 10));
		}
	}
	
	class Mkb1Str extends mkb_1 {
		private static final long serialVersionUID = -8624222080103892087L;

		public Mkb1Str(mkb_1 mkb1) {
			super(mkb1);
		}
		
		@Override
		public String toString() {
			return String.format("(%s) %s", pcod, name);
		}
	}
	
	class Mkb2Str extends mkb_2 {
		private static final long serialVersionUID = -7935255434023824208L;

		public Mkb2Str(mkb_2 mkb2) {
			super(mkb2);
		}
		
		@Override
		public String toString() {
			return String.format("%-7s %s", pcod, name);
		}
	}
	
	class StringClassifierStr extends StringClassifier {
		private static final long serialVersionUID = 1325517499227792334L;
		
		public StringClassifierStr(StringClassifier sc) {
			super(sc);
		}
		
		@Override
		public String toString() {
			return String.format("%-7s %s", pcod, name);
		}
	}
}
