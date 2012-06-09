package ru.nkz.ivcgzo.clientOsm.patientInfo;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientOsm.MainForm;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.Priem;
import ru.nkz.ivcgzo.thriftOsm.Pvizit;
import ru.nkz.ivcgzo.thriftOsm.PvizitAmb;

public class PInfo extends JFrame {
	private static final long serialVersionUID = 7025194439882492263L;
	private static final String lineSep = System.lineSeparator();
	private JEditorPane eptxt;
	private JTree treeinfo;
	private StringBuilder sb;

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
		 treeinfo.addTreeSelectionListener(new TreeSelectionListener() {
		 	public void valueChanged(TreeSelectionEvent e) {
		 		Object lastPath = e.getNewLeadSelectionPath().getLastPathComponent();
		 		sb = new StringBuilder();	
		 		try {
		 		if (lastPath instanceof PvizitTreeNode) {
		 				PvizitTreeNode pvizitNode = (PvizitTreeNode) lastPath;
		 			Pvizit pvizit = pvizitNode.pvizit;
		 			Priem priem =  MainForm.tcl.getPriem(pvizit.getId(),pvizit.getNpasp(),pvizit.getId());
		 			addLineToDetailInfo("id: ", pvizit.isSetId(), pvizit.getId());
					addLineToDetailInfo("Цель обращения", getValueFromClassifier(MainForm.tcl.getP0c(), pvizit.isSetCobr(), pvizit.getCobr()));
					addLineToDetailInfo("Должность", getValueFromClassifier(Classifiers.n_s00, pvizit.isSetCdol(), pvizit.getCdol()));
		 			addLineToDetailInfo("Врач", getValueFromClassifier(MainForm.tcl.getP0c(), pvizit.isSetCobr(), pvizit.getCobr()));
					addLineToDetailInfo("Исход", getValueFromClassifier(MainForm.tcl.getAq0(), pvizit.isSetIshod(), pvizit.getIshod()));
					addLineToDetailInfo("Результат", getValueFromClassifier(MainForm.tcl.getAp0(), pvizit.isSetRezult(), pvizit.getRezult()));
					addLineToDetailInfo("Заключение специалиста",pvizit.isSetZakl(),pvizit.getZakl());
		 			addLineToDetailInfo("Рекомендации", pvizit.isSetRecomend(),pvizit.getRecomend());
					addLineToDetailInfo("Дата записи в базу", pvizit.isSetDataz(), DateFormat.getDateInstance().format(new Date(pvizit.getDataz())));
					addLineToDetailInfo("Дата записи в базу", pvizit.isSetDataz(), DateFormat.getDateInstance().format(new Date(pvizit.getDataz())));

					eptxt.setText(sb.toString());
		 			} 
		 		else if (lastPath instanceof PvizitAmbNode) {
		 			PvizitAmbNode pvizitAmbNode = (PvizitAmbNode) lastPath;
		 			PvizitAmb pam = pvizitAmbNode.pam;
		 			addLineToDetailInfo("id: ", pam.isSetId(), pam.getId());
		 			addLineToDetailInfo("Должность", getValueFromClassifier(Classifiers.n_s00, pam.isSetCdol(), pam.getCdol()));
		 			eptxt.setText(sb.toString());
		 		}
		 			}
		 			catch (KmiacServerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		 			
		 		
		 	}
		 });
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
						((DefaultTreeModel) treeinfo.getModel()).reload(pvizitNode);
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


	
	private DefaultMutableTreeNode createNodes() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Корень зла");
		
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
			for (Pvizit pvizit : MainForm.tcl.getPvizitInfo(2, sdf.parse("01.01.1970").getTime(), sdf.parse("31.12.2070").getTime()))
				root.add(new PvizitTreeNode(pvizit));

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

		return root;
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
			return DateFormat.getDateInstance().format(new Date(pvizit.getDatao()));
			//return Integer.toString(pvizit.getId());
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
			return DateFormat.getDateInstance().format(new Date(pam.getDatap()));
		}
	}
	private void addLineToDetailInfo(String name, boolean isSet, Object value) {
		if (isSet)
			if ((name != null) && (value != null))
				if ((name.length() > 0) && (value.toString().length() > 0))
					sb.append(String.format("%s: %s%s", name, value, lineSep));
	}
	
	private void addLineToDetailInfo(String name, Object value) {
		addLineToDetailInfo(name, true, value);
	}
	
	private String getValueFromClassifier(List<IntegerClassifier> list, boolean isSet, int pcod) {
		if (isSet)
			if (pcod != 0)
				for (IntegerClassifier item : list) {
					if (item.getPcod() == pcod)
						return item.getName();
				}
		
		return null;
	}
	
	private String getValueFromClassifier(List<StringClassifier> list, boolean isSet, String pcod) {
		if (isSet)
			if (pcod != null)
				if (!pcod.equals(""))
					for (StringClassifier item : list) {
						if (item.getPcod().equals(pcod))
							return item.getName();
					}
		
		return null;
	}
}
