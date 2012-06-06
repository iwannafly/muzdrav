package ru.nkz.ivcgzo.clientOsm.patientInfo;

import java.awt.Font;
import java.sql.Date;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreePath;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientOsm.MainForm;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftOsm.PatientCommonInfo;
import ru.nkz.ivcgzo.thriftOsm.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Psign;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm;

public class PatientInfoViewMainForm {
	private static final String lineSep = System.lineSeparator();
	private ThriftOsm.Client tcl;
	private int npasp;
	
	private JFrame frame;
	private JTree tree;
	private JTable table;
	private JTextArea tbDetailInfo;
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public PatientInfoViewMainForm(ConnectionManager conMan, UserAuthInfo authInfo) {
		initForm();
	}
	
	private void initForm() {
		frame = new JFrame();
		frame.setBounds(640, 480, 640, 480);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u041B\u0438\u0447\u043D\u044B\u0435 \u0434\u0430\u043D\u043D\u044B\u0435", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u041A\u0430\u0442\u0435\u0433\u043E\u0440\u0438\u044F \u043F\u0440\u043E\u0441\u043C\u043E\u0442\u0440\u0430", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_2 = new JPanel();
		panel_2.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0412\u044B\u0431\u043E\u0440 \u0447\u0435\u0433\u043E-\u043D\u0438\u0431\u0443\u0434\u044C", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0414\u0435\u0442\u0430\u043B\u044C\u043D\u0430\u044F \u0438\u043D\u0444\u043E\u0440\u043C\u0430\u0446\u0438\u044F", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 214, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 392, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
							.addGap(0))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 127, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)))
					.addContainerGap())
		);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		table = new JTable();
		table.setFillsViewportHeight(true);
		scrollPane_2.setViewportView(table);
		panel_2.setLayout(gl_panel_2);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tbDetailInfo = new JTextArea();
		tbDetailInfo.setEditable(false);
		tbDetailInfo.setFont(new Font("Tahoma", Font.PLAIN, 11));
		scrollPane_1.setViewportView(tbDetailInfo);
		panel_3.setLayout(gl_panel_3);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 178, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tree = new JTree(createNodes());
		tree.setShowsRootHandles(true);
		tree.setRootVisible(false);
		DefaultTreeCellRenderer renderer =  (DefaultTreeCellRenderer) tree.getCellRenderer();
		renderer.setLeafIcon(null);
		renderer.setClosedIcon(null);
		renderer.setOpenIcon(null);
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				try {
					showInfo(((IndexedTreeNode) e.getPath().getLastPathComponent()).getIndex());
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(frame, e1.getMessage(), "Необработанная ошибка", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		
		scrollPane.setViewportView(tree);
		panel_1.setLayout(gl_panel_1);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	private IndexedTreeNode createNodes() {
		IndexedTreeNode root = new IndexedTreeNode(0, "Корень зла");
		
		root.add(new IndexedTreeNode(1, "Основная информация"));
		root.add(new IndexedTreeNode(2, "Дополнительная информация"));
		
		return root;
	}
	
	public void showForm(ThriftOsm.Client tcl, int npasp) {
		this.tcl = tcl;
		this.npasp = npasp;
		
		if (!Classifiers.load(tcl)) {
			JOptionPane.showMessageDialog(frame, "Ошибка загрузки классификаторов", "Необработанная ошибка", JOptionPane.ERROR_MESSAGE);
			return;
		}
		frame.setVisible(true);
		
		IndexedTreeNode root =(IndexedTreeNode) tree.getModel().getRoot();
		IndexedTreeNode child = (IndexedTreeNode) root.getFirstChild();
		if (child != null) {
			TreePath path = new TreePath(new Object[]{root, child});
			tree.setSelectionPath(new TreePath(root));
			tree.scrollPathToVisible(path);
			tree.setSelectionPath(path);
		}
	}
	
	private void showInfo(int nodeIdx) throws Exception {
		clearInfoControls();
		
		try {
			switch (nodeIdx) {
			case 0:
				break;
			case 1:
				showCommonInfo(tcl.getPatientCommonInfo(npasp));
				break;
			case 2:
				showMiscInfo(tcl.getPatientMiscInfo(npasp));
				break;
			default:
				throw new Exception(String.format("Wrong node idx '%d'.", nodeIdx));
			}
		} catch (KmiacServerException e) {
			throw new Exception("Server exception", e);
		} catch (PatientNotFoundException e) {
			throw new Exception(String.format("Patient with npasp '%d' not found.", nodeIdx), e);
		} catch (TException e) {
			MainForm.conMan.reconnect(e);
		}
	}
	
	private void showCommonInfo(PatientCommonInfo info) {
		addLineToDetailInfo("Уникальный номер", info.isSetNpasp(), info.getNpasp());
		addLineToDetailInfo("Фамилия", info.getFam());
		addLineToDetailInfo("Имя", info.getIm());
		addLineToDetailInfo("Отчество", info.getOt());
		addLineToDetailInfo("Дата рождения", getDate(info.isSetDatar(), info.getDatar()));
		addLineToDetailInfo("Серия полиса ОМС", info.getPoms_ser());
		addLineToDetailInfo("Номер полиса ОМС", info.getPoms_nom());
		addLineToDetailInfo("Пол", getValueFromClassifier(Classifiers.n_z30, info.isSetPol(), info.getPol()));
		addLineToDetailInfo("Место жительства", getValueFromClassifier(Classifiers.n_am0, info.isSetJitel(), info.getJitel()));
		addLineToDetailInfo("Социальный статус", getValueFromClassifier(Classifiers.n_az9, info.isSetSgrp(), info.getSgrp()));
		addLineToDetailInfo("Область (прописка)", info.getAdp_obl());
		addLineToDetailInfo("Город (прописка)", info.getAdp_gorod());
		addLineToDetailInfo("Улица (прописка)", info.getAdp_ul());
		addLineToDetailInfo("Дом (прописка)", info.getAdp_dom());
		addLineToDetailInfo("Корпус (прописка)", info.getAdp_korp());
		addLineToDetailInfo("Квартира (прописка)", info.getAdp_kv());
		addLineToDetailInfo("Область (проживание)", info.getAdm_obl());
		addLineToDetailInfo("Город (проживание)", info.getAdm_gorod());
		addLineToDetailInfo("Улица (проживание)", info.getAdm_ul());
		addLineToDetailInfo("Дом (проживание)", info.getAdm_dom());
		addLineToDetailInfo("Корпус (проживание)", info.getAdm_korp());
		addLineToDetailInfo("Квартира (проживание)", info.getAdm_kv());
//		addLineToDetailInfo("Место работы", getValueFromClassifier(Classifiers.n_z43, info.isSetMrab(), info.getMrab()));
		addLineToDetailInfo("Место работы (иногородние)", info.getName_mr());
//		addLineToDetailInfo("Тип места работы", info.getNcex());
		addLineToDetailInfo("Страховая организация ОМС", getValueFromClassifier(Classifiers.n_kas, info.isSetPoms_strg(), info.getPoms_strg()));
//		addLineToDetailInfo("Тип документа ОМС", info.getPoms_tdoc());
		addLineToDetailInfo("Номер договора ОМС", info.getPoms_ndog());
		addLineToDetailInfo("Страховая организация ДМС", getValueFromClassifier(Classifiers.n_kas, info.isSetPdms_strg(), info.getPdms_strg()));
		addLineToDetailInfo("Серия полиса ДМС", info.getPdms_ser());
		addLineToDetailInfo("Номер полиса ДМС", info.getPdms_nom());
		addLineToDetailInfo("Номер договора ДМС", info.getPdms_ndog());
		addLineToDetailInfo("Поликлиника прикрепления", getValueFromClassifier(Classifiers.n_n00, info.isSetCpol_pr(), info.getCpol_pr()));
		addLineToDetailInfo("Территория прикрепления", getValueFromClassifier(Classifiers.n_l01, info.isSetTerp(), info.getTerp()));
		addLineToDetailInfo("Дата прикрепления", getDate(info.isSetDatapr(), info.getDatapr()));
		addLineToDetailInfo("Тип удостоверения личности", getValueFromClassifier(Classifiers.n_az0, info.isSetTdoc(), info.getTdoc()));
		addLineToDetailInfo("Серия документа", info.getDocser());
		addLineToDetailInfo("Номер документа", info.getDocnum());
		addLineToDetailInfo("Дата выдачи документа", getDate(info.isSetDatadoc(), info.getDatadoc()));
		addLineToDetailInfo("Дата сверки данных", getDate(info.isSetDsv(), info.getDsv()));
		addLineToDetailInfo("Кем выдан документ", info.getOdoc());
		addLineToDetailInfo("СНИЛС", info.getSnils());
		addLineToDetailInfo("Профессия", info.getProf());
		addLineToDetailInfo("Телефон", info.getTel());
		addLineToDetailInfo("Область проживания", getValueFromClassifier(Classifiers.n_l02, info.isSetRegion_liv(), info.getRegion_liv()));
		addLineToDetailInfo("Территория проживания", getValueFromClassifier(Classifiers.n_l01, info.isSetTer_liv(), info.getTer_liv()));
	}
	
	private void showMiscInfo(Psign info) {
			addLineToDetailInfo("Группа крови", info.getGrup());
			addLineToDetailInfo("Резус-фактор", info.getPh());
			addLineToDetailInfo("Аллерго-анамнез", info.getAllerg());
			addLineToDetailInfo("Фармакологический анамнез", info.getFarmkol());
			addLineToDetailInfo("Анаменез жизни", info.getVitae());
			addLineToDetailInfo("Вредные привычки", info.getVred());
	}
	
	private void clearInfoControls() {
		tbDetailInfo.setText(null);
	}
	
	private Date getDate(boolean isSet, long value) {
		if (isSet)
			return new Date(value);
		else
			return null;
	}
	
	private void addLineToDetailInfo(String name, boolean isSet, Object value) {
		if ((name != null) && (value != null))
			if ((name.length() > 0) && (value.toString().length() > 0))
				tbDetailInfo.append(String.format("%s: %s%s", name, value, lineSep));
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
}

class IndexedTreeNode extends DefaultMutableTreeNode {
	private static final long serialVersionUID = 5378789946108562335L;
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