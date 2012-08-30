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
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.ModalForm;
import ru.nkz.ivcgzo.clientViewSelect.MainForm;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftViewSelect.mrab_0;
import ru.nkz.ivcgzo.thriftViewSelect.mrab_0;
import ru.nkz.ivcgzo.thriftViewSelect.polp_0;
import ru.nkz.ivcgzo.thriftViewSelect.polp_1;

public class ViewMrabTreeForm extends ModalForm {
	private static final long serialVersionUID = -2721787454937988306L;
	private List<mrab_0> mrabTree;
	private JPanel contentPane;
	private JTree tree;
	
	public ViewMrabTreeForm() {
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
		scrollPane.setViewportView(tree);
		contentPane.setLayout(gl_contentPane);
	}
	
	@Override
	public void acceptResults() {
		Object sel = tree.getSelectionPath().getLastPathComponent();
		
		//if (sel instanceof mrab_1)
		//	if (((mrab_1) sel).getmrab2Size() == 0)
		//		results = new int[] { ((mrab_0) tree.getSelectionPath().getParentPath().getLastPathComponent()).kdate, ((mrab_1) sel).kdlpu, ((mrab_1) sel).kdlpu };
		//	else
		//		return;
		//else 
		if (sel instanceof IntegerClassifier)
			results = new int[] { ((mrab_0) tree.getSelectionPath().getParentPath().getParentPath().getLastPathComponent()).pGruppa, ((IntegerClassifier) sel).pcod };
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
	
	public void prepare(int pGruppa, int pMrab) {
		if (mrabTree == null)
			try {
				mrabTree = MainForm.ccm.getMrabTreeClassifier();
				setModel();
			} catch (KmiacServerException e) {
				e.printStackTrace();
			} catch (TException e) {
				e.printStackTrace();
				MainForm.conMan.reconnect(e);
			} else
				((DefaultTreeModel) tree.getModel()).reload();
		selectPcod(pGruppa, pMrab);
	}
	
	private void setModel() {
		tree.setRootVisible(false);
		tree.setShowsRootHandles(true);
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Root")) {
			

			/**
			 * 
			 */
			private static final long serialVersionUID = -1143272828997564673L;

			@Override
			public boolean isLeaf(Object node) {
				if (node instanceof mrab_0)
					return ((mrab_0) node).getMrab1Size() == 0;
				else
					return node instanceof IntegerClassifier;
			}
			
			@Override
			public int getChildCount(Object parent) {
				if (parent instanceof DefaultMutableTreeNode)
					return mrabTree.size();
				else if (parent instanceof mrab_0)
					return ((mrab_0) parent).getMrab1Size();
				//else if (parent instanceof mrab_1)
				//	return ((mrab_1) parent).getmrab2Size();
				else
					return -1;
			}
			
			@Override
			public Object getChild(Object parent, int index) {
				if (parent instanceof DefaultMutableTreeNode)
					return new mrab0Str(mrabTree.get(index));
				//else if (parent instanceof mrab_0)
				//	return new mrab1Str(((mrab_0) parent).mrab1.get(index));
				else if (parent instanceof mrab_0)
					return new IntegerClassifierStr(((mrab_0) parent).mrab1.get(index));
				else
					return null;
			}
			
			@Override
			public int getIndexOfChild(Object parent, Object child) {
				if (parent instanceof DefaultMutableTreeNode)
					return mrabTree.indexOf(child);
				else if (parent instanceof mrab_0)
					return ((mrab_0) parent).mrab1.indexOf(child);
				//else if (parent instanceof mrab_1)
				//	return ((mrab_1) parent).mrab2.indexOf(child);
				else
					return -1;
			}
		});
	}
	
	private void selectPcod(int pGruppa, int pMrab) {
		DefaultMutableTreeNode root = (DefaultMutableTreeNode) tree.getModel().getRoot();
		for (int i = 0; i < tree.getModel().getChildCount(root); i++) {
			mrab_0 mrab0 = (mrab_0) tree.getModel().getChild(root, i);
			if (mrab0.pGruppa == pGruppa) {
				for (int j = 0; j < mrab0.getMrab1Size(); j++) {
					IntegerClassifier mrab1 = mrab0.mrab1.get(j);
					if (mrab1.pcod == pMrab) {
						//if (polp1.getPolp2Size() == 0) {
							scrollToPath(new TreePath(new Object[] {root, mrab0, mrab1}));
							return;
						//}// else
							//for (int k = 0; k < polp1.getPolp2Size(); k++) {
								//IntegerClassifier ic = polp1.polp2.get(k);
								//if (ic.pcod == kdPol) {
								//	scrollToPath(new TreePath(new Object[] {root, polp0, polp1, ic}));
								//	return;
								//}
							//}
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
		tree.scrollRectToVisible(bounds);
	}
	
	class mrab0Str extends mrab_0 {
		private static final long serialVersionUID = 8055500323615642093L;

		public mrab0Str(mrab_0 mrab0) {
			super(mrab0);
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	/**class mrab1Str extends mrab_1 {
		private static final long serialVersionUID = -5101784151738094228L;

		public mrab1Str(mrab_1 mrab1) {
			super(mrab1);
		}
		
		@Override
		public String toString() {
			return name;
		}
	}*/
	
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
