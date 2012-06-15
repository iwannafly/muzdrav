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
import ru.nkz.ivcgzo.thriftOsm.AnamZab;
import ru.nkz.ivcgzo.thriftOsm.Priem;
import ru.nkz.ivcgzo.thriftOsm.PriemNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Pvizit;
import ru.nkz.ivcgzo.thriftOsm.PvizitAmb;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;

public class PInfo extends JFrame {
	private static final long serialVersionUID = 7025194439882492263L;
	private static final String lineSep = System.lineSeparator();
	private JEditorPane eptxt;
	private JTree treeinfo;
	private StringBuilder sb;
	private JTextField tfdat;

	public PInfo() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 822, 732);
		
		JSplitPane splitpinfo = new JSplitPane();
		
		JLabel lblperiod = new JLabel("Период ");
		
		tfdat = new JTextField();
		tfdat.setColumns(10);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitpinfo)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblperiod)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tfdat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(673, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblperiod)
						.addComponent(tfdat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(splitpinfo, GroupLayout.PREFERRED_SIZE, 668, GroupLayout.PREFERRED_SIZE))
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
		 			AnamZab anamnez =  MainForm.tcl.getAnamZab(pvizit.getId(),pvizit.getNpasp());
		 			addLineToDetailInfo("id: ", pvizit.isSetId(), pvizit.getId());
					addLineToDetailInfo("Цель обращения", getValueFromClassifier(MainForm.tcl.getP0c(), pvizit.isSetCobr(), pvizit.getCobr()));
					addLineToDetailInfo("Должность", getValueFromClassifier(Classifiers.n_s00, pvizit.isSetCdol(), pvizit.getCdol()));
		 			//addLineToDetailInfo("Врач", pvizit.isSetFio_vr(),pvizit.getFio_vr());
					addLineToDetailInfo("Исход", getValueFromClassifier(MainForm.tcl.getAq0(), pvizit.isSetIshod(), pvizit.getIshod()));
					addLineToDetailInfo("Результат", getValueFromClassifier(MainForm.tcl.getAp0(), pvizit.isSetRezult(), pvizit.getRezult()));
					addLineToDetailInfo("Заключение специалиста",pvizit.isSetZakl(),pvizit.getZakl());
		 			addLineToDetailInfo("Рекомендации", pvizit.isSetRecomend(),pvizit.getRecomend());
					addLineToDetailInfo("Дата записи в базу", pvizit.isSetDataz(), DateFormat.getDateInstance().format(new Date(pvizit.getDataz())));
					addHeader("Анамнез заболевания");
					addDetailInfo(anamnez.isSetT_nachalo_zab(), anamnez.getT_nachalo_zab());
					addDetailInfo(anamnez.isSetT_sympt(), anamnez.getT_sympt());
					addDetailInfo(anamnez.isSetT_otn_bol(), anamnez.getT_otn_bol());
					addDetailInfo(anamnez.isSetT_ps_syt(), anamnez.getT_ps_syt());
					eptxt.setText(sb.toString());
		 			} 
		 		else if (lastPath instanceof PvizitAmbNode) {
		 			
		 			try {
		 				PvizitAmbNode pvizitAmbNode = (PvizitAmbNode) lastPath;
		 			PvizitAmb pam = pvizitAmbNode.pam;
						Priem priem =  MainForm.tcl.getPriem(pam.getNpasp(),pam.getId());
						addLineToDetailInfo("id: ", pam.isSetId(), pam.getId());
						addLineToDetailInfo("Должность",getValueFromClassifier(Classifiers.n_s00, pam.isSetCdol(), pam.getCdol()));
						addLineToDetailInfo("Врач",pam.isSetFio_vr(),pam.getFio_vr());
						
						addHeader("Жалобы");
						addDetailInfo(priem.isSetT_jalob(), priem.getT_jalob());
						addDetailInfo(priem.isSetT_jalob_d(), priem.getT_jalob_d());
						addDetailInfo(priem.isSetT_jalob_krov(), priem.getT_jalob_krov());
						addDetailInfo(priem.isSetT_jalob_p(), priem.getT_jalob_p());
						addDetailInfo(priem.isSetT_jalob_moch(), priem.getT_jalob_moch());
						addDetailInfo(priem.isSetT_jalob_endo(), priem.getT_jalob_endo());
						addDetailInfo(priem.isSetT_jalob_nerv(), priem.getT_jalob_nerv());
						addDetailInfo(priem.isSetT_jalob_opor(), priem.getT_jalob_opor());
						addDetailInfo(priem.isSetT_jalob_lih(), priem.getT_jalob_lih());
						addDetailInfo(priem.isSetT_jalob_proch(), priem.getT_jalob_proch());
						addHeader("Status Praesense");
						addDetailInfo(priem.isSetT_status_praesense(), priem.getT_status_praesense());
						addDetailInfo(priem.isSetT_ob_sost(), priem.getT_ob_sost());
						addDetailInfo(priem.isSetT_koj_pokr(), priem.getT_koj_pokr());
						addDetailInfo(priem.isSetT_sliz(), priem.getT_sliz());
						addDetailInfo(priem.isSetT_podk_kl(), priem.getT_podk_kl());
						addDetailInfo(priem.isSetT_limf_uzl(), priem.getT_limf_uzl());
						addDetailInfo(priem.isSetT_kost_mysh(), priem.getT_kost_mysh());
						addDetailInfo(priem.isSetT_nervn_ps(), priem.getT_nervn_ps());
						addDetailInfo(priem.isSetT_chss(), priem.getT_chss());
						addDetailInfo(priem.isSetT_temp(), priem.getT_temp());
						addDetailInfo(priem.isSetT_ad(), priem.getT_ad());
						addDetailInfo(priem.isSetT_rost(), priem.getT_rost());
						addDetailInfo(priem.isSetT_ves(), priem.getT_ves());
						addDetailInfo(priem.isSetT_telo(), priem.getT_telo());
						addHeader("Физикальное обследование");
						addDetailInfo(priem.isSetT_fiz_obsl(), priem.getT_fiz_obsl());
						addDetailInfo(priem.isSetT_sust(), priem.getT_sust());
						addDetailInfo(priem.isSetT_dyh(), priem.getT_dyh());
						addDetailInfo(priem.isSetT_gr_kl(), priem.getT_gr_kl());
						addDetailInfo(priem.isSetT_perk_l(), priem.getT_perk_l());
						addDetailInfo(priem.isSetT_aus_l(), priem.getT_aus_l());
						addDetailInfo(priem.isSetT_bronho(), priem.getT_bronho());
						addDetailInfo(priem.isSetT_arter(), priem.getT_arter());
						addDetailInfo(priem.isSetT_obl_s(), priem.getT_obl_s());
						addDetailInfo(priem.isSetT_perk_s(), priem.getT_perk_s());
						addDetailInfo(priem.isSetT_aus_s(), priem.getT_aus_s());
						addDetailInfo(priem.isSetT_pol_rta(), priem.getT_pol_rta());
						addDetailInfo(priem.isSetT_jivot(), priem.getT_jivot());
						addDetailInfo(priem.isSetT_palp_jivot(), priem.getT_palp_jivot());
						addDetailInfo(priem.isSetT_jel_kish(), priem.getT_jel_kish());
						addDetailInfo(priem.isSetT_palp_jel(), priem.getT_palp_jel());
						addDetailInfo(priem.isSetT_palp_podjjel(), priem.getT_palp_podjjel());
						addDetailInfo(priem.isSetT_pechen(), priem.getT_pechen());
						addDetailInfo(priem.isSetT_jelch(), priem.getT_jelch());
						addDetailInfo(priem.isSetT_selez(), priem.getT_selez());
						addDetailInfo(priem.isSetT_obl_zad(), priem.getT_obl_zad());
						addDetailInfo(priem.isSetT_poyasn(), priem.getT_poyasn());
						addDetailInfo(priem.isSetT_pochk(), priem.getT_pochk());
						addDetailInfo(priem.isSetT_moch(), priem.getT_moch());
						addDetailInfo(priem.isSetT_mol_jel(), priem.getT_mol_jel());
						addDetailInfo(priem.isSetT_gr_jel(), priem.getT_gr_jel());
						addDetailInfo(priem.isSetT_matka(), priem.getT_matka());
						addDetailInfo(priem.isSetT_nar_polov(), priem.getT_nar_polov());
						addDetailInfo(priem.isSetT_chitov(), priem.getT_chitov());
						addHeader("Status localis");
						addDetailInfo(priem.isSetT_st_localis(), priem.getT_st_localis());
						addHeader("Оценка данных анамнеза и объективного исследования");
						addDetailInfo(priem.isSetT_ocenka(), priem.getT_ocenka());
						eptxt.setText(sb.toString());
					} catch (PriemNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		 			
		 		}
		 			}
		 			catch (KmiacServerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						MainForm.conMan.reconnect(e1);
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
	
	private void addDetailInfo(boolean isSet, Object value) {
		if (isSet)
			if (value != null)
				if (value.toString().length() > 0)
					sb.append(value + ". ");
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



	private void addHeader(String name) {
		sb.append(name + lineSep);
	}
}
