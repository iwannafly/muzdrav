package ru.nkz.ivcgzo.clientOsm.patientInfo;

import java.awt.Container;
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientOsm.MainForm;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.Pvizit;
import ru.nkz.ivcgzo.thriftOsm.PvizitAmb;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm.getPvizitInfo_result;

import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeExpansionEvent;

public class PInfo extends JFrame {

	private static final long serialVersionUID = 7025194439882492263L;
	private JEditorPane eptxt;
	private JTree treeinfo;
	protected IndexedTreeNode slzab;

	public PInfo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 822, 732);
		
		JSplitPane splitpinfo = new JSplitPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitpinfo, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitpinfo, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
		);
		
		JPanel pl = new JPanel();
		splitpinfo.setLeftComponent(pl);

		
		JScrollPane sptree = new JScrollPane();
		GroupLayout gl_pl = new GroupLayout(pl);
		gl_pl.setHorizontalGroup(
			gl_pl.createParallelGroup(Alignment.LEADING)
				.addComponent(sptree, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
		);
		gl_pl.setVerticalGroup(
			gl_pl.createParallelGroup(Alignment.LEADING)
				.addComponent(sptree, GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
		);
		
		 treeinfo = new JTree(createNodes());
		 treeinfo.addTreeExpansionListener(new TreeExpansionListener() {
		 	public void treeCollapsed(TreeExpansionEvent event) {
		 	}
		 	public void treeExpanded(TreeExpansionEvent event) {
		 		Object lastPath = event.getPath().getLastPathComponent();
		 		if (lastPath instanceof PvizitTreeNode) {
		 			try {
						PvizitTreeNode pvizitNode = (PvizitTreeNode) lastPath;
						pvizitNode.removeAllChildren();
						for (PvizitAmb pvizAmbChild : MainForm.tcl.getPvizitAmb(pvizitNode.pvizit.getId())) {
							pvizitNode.add(new PvizitAmbNode(pvizAmbChild));
						}
						treeinfo.repaint();
					} catch (KmiacServerException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (TException e) {
						e.printStackTrace();
						MainForm.conMan.reconnect(e);
					}
		 		}
		 	}
		 });
		sptree.setViewportView(treeinfo);
		treeinfo.setShowsRootHandles(true);
		treeinfo.setRootVisible(false);
		DefaultTreeCellRenderer renderer =  (DefaultTreeCellRenderer) treeinfo.getCellRenderer();
		renderer.setLeafIcon(null);
		renderer.setClosedIcon(null);
		renderer.setOpenIcon(null);
		

		pl.setLayout(gl_pl);
		
		JPanel pr = new JPanel();
		splitpinfo.setRightComponent(pr);
		
		JScrollPane sptxt = new JScrollPane();
		GroupLayout gl_pr = new GroupLayout(pr);
		gl_pr.setHorizontalGroup(
			gl_pr.createParallelGroup(Alignment.LEADING)
				.addComponent(sptxt, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
		);
		gl_pr.setVerticalGroup(
			gl_pr.createParallelGroup(Alignment.LEADING)
				.addComponent(sptxt, GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
		);
		
		 eptxt = new JEditorPane();
		sptxt.setViewportView(eptxt);
		eptxt.setEditable(false);
		pr.setLayout(gl_pr);
		getContentPane().setLayout(groupLayout);
	}


	
	private IndexedTreeNode createNodes() {
		IndexedTreeNode root = new IndexedTreeNode(0, "Корень зла");
		
//		root.add(new IndexedTreeNode(1, "Личная информация"));
//		root.add(new IndexedTreeNode(2, "Сигнальная информация"));
//		slzab = new IndexedTreeNode(3, "Сл.заб");
//		root.add(slzab);
//		slzab1 = new IndexedTreeNode(4, "");
//		slzab.add
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			for (Pvizit pvizit : MainForm.tcl.getPvizitInfo(2, sdf.parse("01.02.2012").getTime(), sdf.parse("02.06.2012").getTime())) {
				root.add(new PvizitTreeNode(pvizit));
				
			}
		} catch (KmiacServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
			MainForm.conMan.reconnect(e);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
//		list.set(0, MainForm.tcl.getPvizitInfo(2, SimpleDateFormat.getDateInstance().parse("01.02.2012").getTime(), SimpleDateFormat.getDateInstance().parse("01.06.2012").getTime()).toString());
		
		//slzab.add(new IndexedTreeNode(5, list.toString()));
//		slzab.add(new IndexedTreeNode(6, "666"));
//		slzab.add(new IndexedTreeNode(7, "777"));

		
		
		return root;
	}
	
	class IndexedTreeNode extends DefaultMutableTreeNode {
	
		private static final long serialVersionUID = 2143788492556249212L;
		private int index;
		private String name;
		
		public IndexedTreeNode(int index, String name) {
			this.index = index;
			this.name = name;
		}
		
		public int getIndex() {
			return index;
		}

		public String getName() {
			return name;
		}
		
		@Override
		public String toString() {
			return getName();
		}
	}
	
	class PvizitTreeNode extends DefaultMutableTreeNode {
		private static final long serialVersionUID = 4212592425962984738L;
		private Pvizit pvizit;
		
		public PvizitTreeNode(Pvizit pvizit) {
			this.pvizit = pvizit;
			this.add(new PvizitAmbNode(new PvizitAmb()));
			
		}
		
		@Override
		public String toString() {
			return Integer.toString(pvizit.getId());
		}
	}
	
	class PvizitAmbNode extends DefaultMutableTreeNode{
		private static final long serialVersionUID = -4684514837066276873L;
		private PvizitAmb pam;
		
		public PvizitAmbNode(PvizitAmb pam) {
			this.pam = pam;
		}
		
		@Override
		public String toString() {
			return Integer.toString(pam.getId());
		}
	}
}
