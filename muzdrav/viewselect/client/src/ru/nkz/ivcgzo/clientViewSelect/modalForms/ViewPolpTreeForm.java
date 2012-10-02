package ru.nkz.ivcgzo.clientViewSelect.modalForms;

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.ModalForm;
import ru.nkz.ivcgzo.clientViewSelect.MainForm;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftViewSelect.polp_0;
import ru.nkz.ivcgzo.thriftViewSelect.polp_1;

public class ViewPolpTreeForm extends ModalForm {
	private static final long serialVersionUID = -2721787454937988306L;
	private List<polp_0> polpTree;
	private JPanel contentPane;
	private JTree tree;
	
	public ViewPolpTreeForm() {
		super(true);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
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
		Object sel = tree.getSelectionPath().getLastPathComponent();
		
		if (sel instanceof polp_1)
			if (((polp_1) sel).getPolp2Size() == 0)
				results = new int[] { ((polp_0) tree.getSelectionPath().getParentPath().getLastPathComponent()).kdate, ((polp_1) sel).kdlpu, ((polp_1) sel).kdlpu };
			else
				return;
		else if (sel instanceof IntegerClassifier)
			results = new int[] { ((polp_0) tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent()).kdate, ((polp_1) tree.getSelectionPath().getParentPath().getLastPathComponent()).kdlpu, ((IntegerClassifier) sel).pcod };
		else
			return;
		
		super.acceptResults();
	}
	
	@Override
	public int[] getResults() {
		if (results != null)
			return (int[]) results;
		
		return null;
	}
	
	public void prepare(int kdAte, int kdLpu, int kdPol) {
		if (polpTree == null)
			try {
				polpTree = MainForm.ccm.getPolpTreeClassifier();
				setModel();
			} catch (KmiacServerException e) {
				e.printStackTrace();
			} catch (TException e) {
				e.printStackTrace();
				MainForm.conMan.reconnect(e);
			} else
				((DefaultTreeModel) tree.getModel()).reload();
		selectPcod(kdAte, kdLpu, kdPol);
	}
	
	private void setModel() {
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Root")) {
			private static final long serialVersionUID = -5985759695071621123L;

			@Override
			public boolean isLeaf(Object node) {
				if (node instanceof polp_1)
					return ((polp_1) node).getPolp2Size() == 0;
				else
					return node instanceof IntegerClassifier;
			}
			
			@Override
			public int getChildCount(Object parent) {
				if (parent instanceof DefaultMutableTreeNode)
					return polpTree.size();
				else if (parent instanceof polp_0)
					return ((polp_0) parent).getPolp1Size();
				else if (parent instanceof polp_1)
					return ((polp_1) parent).getPolp2Size();
				else
					return -1;
			}
			
			@Override
			public Object getChild(Object parent, int index) {
				if (parent instanceof DefaultMutableTreeNode)
					return new Polp0Str(polpTree.get(index));
				else if (parent instanceof polp_0)
					return new Polp1Str(((polp_0) parent).polp1.get(index));
				else if (parent instanceof polp_1)
					return new IntegerClassifierStr(((polp_1) parent).polp2.get(index));
				else
					return null;
			}
			
			@Override
			public int getIndexOfChild(Object parent, Object child) {
				if (parent instanceof DefaultMutableTreeNode)
					return polpTree.indexOf(child);
				else if (parent instanceof polp_0)
					return ((polp_0) parent).polp1.indexOf(child);
				else if (parent instanceof polp_1)
					return ((polp_1) parent).polp2.indexOf(child);
				else
					return -1;
			}
		});
	}
	
	private void selectPcod(int kdAte, int kdLpu, int kdPol) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		for (int i = 0; i < tree.getModel().getChildCount(root); i++) {
			polp_0 polp0 = (polp_0) tree.getModel().getChild(root, i);
			if (polp0.kdate == kdAte) {
				for (int j = 0; j < polp0.getPolp1Size(); j++) {
					polp_1 polp1 = polp0.polp1.get(j);
					if (polp1.kdlpu == kdLpu) {
						if (polp1.getPolp2Size() == 0) {
							scrollToPath(new TreePath(new Object[] {root, polp0, polp1}));
							return;
						} else
							for (int k = 0; k < polp1.getPolp2Size(); k++) {
								IntegerClassifier ic = polp1.polp2.get(k);
								if (ic.pcod == kdPol) {
									scrollToPath(new TreePath(new Object[] {root, polp0, polp1, ic}));
									return;
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
	
	class Polp0Str extends polp_0 {
		private static final long serialVersionUID = 8055500323615642093L;

		public Polp0Str(polp_0 polp0) {
			super(polp0);
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	class Polp1Str extends polp_1 {
		private static final long serialVersionUID = -5101784151738094228L;

		public Polp1Str(polp_1 polp1) {
			super(polp1);
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	class IntegerClassifierStr extends IntegerClassifier {
		private static final long serialVersionUID = 7623741381685241843L;

		public IntegerClassifierStr(IntegerClassifier ic) {
			super(ic);
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
}
