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
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm.getPvizitInfo_result;

import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeExpansionEvent;

public class PInfo extends JFrame {

	private static final long serialVersionUID = 7025194439882492263L;
	private JEditorPane eptxt;
	private JTree treeinfo;
	protected IndexedTreeNode slzab;
	private Pvizit pviz;


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
		 		slzab.add(new IndexedTreeNode(1, "555"));
				slzab.add(new IndexedTreeNode(2, "666"));
				slzab.add(new IndexedTreeNode(3, "777"));
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
		IndexedTreeNode root = new IndexedTreeNode(0, "1");
		
		root.add(new IndexedTreeNode(1, "Личная информация"));
		root.add(new IndexedTreeNode(2, "Сигнальная информация"));
		 slzab = new IndexedTreeNode(3, "Сл.заб");
		root.add(slzab);
		IndexedTreeNode pos = new IndexedTreeNode(4, "");
		slzab.add(pos);
		ArrayList<String> list = new ArrayList<String>();
		list.set(0, MainForm.tcl.getPvizitInfo(2, SimpleDateFormat.getDateInstance().parse("01.02.2012").getTime(), SimpleDateFormat.getDateInstance().parse("01.06.2012").getTime()).toString());
		
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
}
