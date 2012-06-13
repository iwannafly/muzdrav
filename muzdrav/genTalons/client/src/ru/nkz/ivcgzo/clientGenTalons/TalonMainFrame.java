package ru.nkz.ivcgzo.clientGenTalons;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTabbedPane;

import ru.nkz.ivcgzo.thriftGenTalon.Calendar;
import ru.nkz.ivcgzo.thriftGenTalon.Ndv;
import ru.nkz.ivcgzo.thriftGenTalon.Norm;
import ru.nkz.ivcgzo.thriftGenTalon.Nrasp;
import ru.nkz.ivcgzo.thriftGenTalon.Rasp;
import ru.nkz.ivcgzo.thriftGenTalon.Spec;
import ru.nkz.ivcgzo.thriftGenTalon.Talon;
import ru.nkz.ivcgzo.thriftGenTalon.ThriftGenTalons;
import ru.nkz.ivcgzo.thriftGenTalon.ThriftGenTalons.Iface;
import ru.nkz.ivcgzo.thriftGenTalon.Vidp;
import ru.nkz.ivcgzo.thriftGenTalon.Vrach;

public class TalonMainFrame extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTree treevrach;
	private int curSpec = null;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TalonMainFrame frame = new TalonMainFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public TalonMainFrame() {
		setTitle("Электронная регистратура");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1, 1, 958, 690); //(x,y,width,height)
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u0421\u043F\u0438\u0441\u043E\u043A \u0432\u0440\u0430\u0447\u0435\u0439", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JTabbedPane tbMain = new JTabbedPane(JTabbedPane.TOP);
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tbMain, GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(tbMain, GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE))
		);
		
		JPanel panel_1 = new JPanel();
		tbMain.addTab("New tab", null, panel_1, null);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 680, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 618, Short.MAX_VALUE)
		);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		tbMain.addTab("New tab", null, panel_2, null);
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 680, Short.MAX_VALUE)
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGap(0, 618, Short.MAX_VALUE)
		);
		panel_2.setLayout(gl_panel_2);
		
		JPanel panel_3 = new JPanel();
		tbMain.addTab("New tab", null, panel_3, null);
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGap(0, 680, Short.MAX_VALUE)
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGap(0, 618, Short.MAX_VALUE)
		);
		panel_3.setLayout(gl_panel_3);
		
		JPanel panel_4 = new JPanel();
		tbMain.addTab("New tab", null, panel_4, null);
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGap(0, 680, Short.MAX_VALUE)
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGap(0, 618, Short.MAX_VALUE)
		);
		panel_4.setLayout(gl_panel_4);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
		);
		
		treevrach = new JTree(createNodes());
		treevrach.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
		 		Object lastPath = e.getNewLeadSelectionPath().getLastPathComponent();
		 		try {
			 		if (lastPath instanceof SpecTreeNode) {
			 			SpecTreeNode specNode = (SpecTreeNode) lastPath;
			 			Spec spec = specNode.spec;
			 			curSpec = spec.getCdol();
		 			} 
			 		else if (lastPath instanceof VrachTreeNode) {
		 			PvizitAmbNode pvizitAmbNode = (PvizitAmbNode) lastPath;
		 			PvizitAmb pam = pvizitAmbNode.pam;
		 			addLineToDetailInfo("id: ", pam.isSetId(), pam.getId());
		 			addLineToDetailInfo("Должность", getValueFromClassifier(Classifiers.n_s00, pam.isSetCdol(), pam.getCdol()));
		 			eptxt.setText(sb.toString());
			 		}
					} catch (Exception e1) {
						e1.printStackTrace();
					}
			}
		});
		treevrach.addTreeExpansionListener(new TreeExpansionListener() {
			public void treeCollapsed(TreeExpansionEvent event) {
			}
			public void treeExpanded(TreeExpansionEvent event) {
		 		Object lastPath = event.getPath().getLastPathComponent();
		 		if (lastPath instanceof SpecTreeNode) {
		 			try {
						SpecTreeNode specNode = (SpecTreeNode) lastPath;
						specNode.removeAllChildren();
//						for (Vrach vrachChild : MainForm.tcl.getVrachForCurrentSpec(MainForm.authInfo.cpodr,specNode.spec.getCdol())) {
						for (Vrach vrachChild : MainForm.tcl.getVrachForCurrentSpec(specNode.spec.getCdol())) {
							specNode.add(new VrachTreeNode(vrachChild));
						}
						((DefaultTreeModel) treevrach.getModel()).reload(specNode);
					} catch (Exception e) {
						e.printStackTrace();
					}
		 		}
			}
		});
		scrollPane.setViewportView(treevrach);
		treevrach.setShowsRootHandles(true);
		treevrach.setRootVisible(false);
		DefaultTreeCellRenderer renderer =  (DefaultTreeCellRenderer) treevrach.getCellRenderer();
		renderer.setLeafIcon(null);
		renderer.setClosedIcon(null);
		renderer.setOpenIcon(null);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);	
	}		

	private DefaultMutableTreeNode createNodes() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Корень дерева");
		
		try {
			for (Spec spec : MainForm.tcl.getAllSpecForPolikliniki(MainForm.authInfo.cpodr))
				root.add(new SpecTreeNode(spec));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return root;
	}

	class SpecTreeNode extends DefaultMutableTreeNode {
		private static final long serialVersionUID = 4212592425962984738L;
		private Spec spec;
		
		public SpecTreeNode(Spec spec) {
			this.spec = spec;
			this.add(new VrachTreeNode(new Vrach()));
		}
		
		@Override
		public String toString() {
			return spec.getName();
		}
	}
	class VrachTreeNode extends DefaultMutableTreeNode{
		private static final long serialVersionUID = -4684514837066276873L;
		private Vrach vrach;
		
		public VrachTreeNode(Vrach vrach) {
			this.vrach = vrach;
		}
		
		@Override
		public String toString() {
			return vrach.getFam()+" "+vrach.getIm()+" "+vrach.getOt();
		}
	}
}
