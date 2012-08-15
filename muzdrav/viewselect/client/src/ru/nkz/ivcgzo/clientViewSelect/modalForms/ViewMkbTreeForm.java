package ru.nkz.ivcgzo.clientViewSelect.modalForms;

import java.awt.Rectangle;
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

import ru.nkz.ivcgzo.clientViewSelect.MainForm;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftViewSelect.mkb_0;
import ru.nkz.ivcgzo.thriftViewSelect.mkb_1;
import ru.nkz.ivcgzo.thriftViewSelect.mkb_2;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ViewMkbTreeForm extends ModalForm {
	private static final long serialVersionUID = -162973903323760204L;
	private List<mkb_0> mkbTree;
	private JPanel contentPane;
	private JTree tree;
	
	/**
	 * Create the frame.
	 */
	public ViewMkbTreeForm() {
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
		
		if (sel instanceof mkb_2)
			results = new StringClassifier(((mkb_2) sel).pcod, ((mkb_2) sel).name);
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
				mkbTree = MainForm.tcl.getMkb_0();
				setModel();
			} catch (KmiacServerException e) {
				e.printStackTrace();
			} catch (TException e) {
				e.printStackTrace();
				MainForm.conMan.reconnect(e);
			} else {
				((DefaultTreeModel) tree.getModel()).reload();
			}
		if (pcod != null && pcod.length() > 2)
			selectPcod(pcod);
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
					return mkbTree.size();
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
					return new Mkb0Str(mkbTree.get(index));
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
					return mkbTree.indexOf(child);
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
		tree.scrollRectToVisible(bounds);
	}
	class Mkb0Str extends mkb_0 {
		private static final long serialVersionUID = -6810587469602995591L;

		public Mkb0Str(mkb_0 mkb0) {
			super(mkb0);
		}
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	class Mkb1Str extends mkb_1 {
		private static final long serialVersionUID = -8624222080103892087L;

		public Mkb1Str(mkb_1 mkb1) {
			super(mkb1);
		}
		
		@Override
		public String toString() {
			return String.format("%s (%s)", name, pcod);
		}
	}
	
	class Mkb2Str extends mkb_2 {
		private static final long serialVersionUID = -7935255434023824208L;

		public Mkb2Str(mkb_2 mkb2) {
			super(mkb2);
		}
		
		@Override
		public String toString() {
			return String.format("%s (%s)", name, pcod);
		}
	}
	
	class StringClassifierStr extends StringClassifier {
		private static final long serialVersionUID = 1325517499227792334L;
		
		public StringClassifierStr(StringClassifier sc) {
			super(sc);
		}
		
		@Override
		public String toString() {
			return String.format("%s (%s)", name, pcod);
		}
	}
	
}
