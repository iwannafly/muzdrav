package ru.nkz.ivcgzo.clientViewSelect.modalForms;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.ModalForm;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientViewSelect.MainForm;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftViewSelect.CgospInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientAnamZabInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientCommonInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientDiagAmbInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientDiagZInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientIsslInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientNaprInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientPriemInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientSignInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientVizitAmbInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientVizitInfo;
import ru.nkz.ivcgzo.thriftViewSelect.RdSlInfo;

public class PatientInfoForm extends ModalForm {
	private static final long serialVersionUID = 7025194439882492263L;
	private static final String lineSep = System.lineSeparator();
	private JEditorPane eptxt;
	private JTree treeinfo;
	private StringBuilder sb;
	private CustomDateEditor tfdatn;
	private CustomDateEditor tfDatk;
	private PatientCommonInfo info;
	private DefaultMutableTreeNode root;
	private DefaultMutableTreeNode patinfo;
	private DefaultMutableTreeNode signinfo;
	private DefaultMutableTreeNode posinfo;
	private DefaultMutableTreeNode diaginfo;
	private DefaultMutableTreeNode berinfo;
	private DefaultMutableTreeNode gospinfo;

	public PatientInfoForm() {
		super(true);
		
		setTitle("Просмотр информации на пациента");
		setBounds(100, 100, 822, 732);
		
		JSplitPane splitpinfo = new JSplitPane();
		
		JLabel lblperiod = new JLabel("Период ");
		
		tfdatn = new CustomDateEditor();
		tfdatn.setColumns(10);
		Calendar calendar = GregorianCalendar.getInstance();
		calendar.setTimeInMillis(System.currentTimeMillis());
		tfdatn.setDate("01.01."+calendar.get(Calendar.YEAR));
		
		JLabel label = new JLabel("-");
		
		tfDatk = new CustomDateEditor();
		tfDatk.setColumns(10);
		tfDatk.setDate("31.12."+calendar.get(Calendar.YEAR));
		
		JButton btnOk = new JButton("OK");
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitpinfo)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblperiod)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tfdatn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tfDatk, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnOk))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblperiod)
						.addComponent(tfdatn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label)
						.addComponent(tfDatk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnOk))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(splitpinfo, GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE))
		);
		
		final JPanel pl = new JPanel();
		pl.setMinimumSize(new Dimension(184, 10));
		splitpinfo.setLeftComponent(pl);

		final JScrollPane sptree = new JScrollPane();
		GroupLayout gl_pl = new GroupLayout(pl);
		gl_pl.setHorizontalGroup(
			gl_pl.createParallelGroup(Alignment.LEADING)
				.addComponent(sptree, GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
		);
		gl_pl.setVerticalGroup(
			gl_pl.createParallelGroup(Alignment.LEADING)
				.addComponent(sptree, GroupLayout.DEFAULT_SIZE, 696, Short.MAX_VALUE)
		);	
		
		treeinfo = new JTree();
		treeinfo.setFont(new Font("Arial", Font.PLAIN, 12));
		treeinfo.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
		 		if (e.getNewLeadSelectionPath() == null) {
		 			eptxt.setText("");
		 			return;
		 		}
		 		
		 		sb = new StringBuilder();	
		 		Object lastPath = e.getNewLeadSelectionPath().getLastPathComponent();
		 		try {
		 			if (lastPath.toString() == "Личная информация") {
						addLineToDetailInfo("Уникальный номер", info.isSetNpasp(), info.getNpasp());
		 				addLineToDetailInfo("Фамилия", info.getFam());
		 				addLineToDetailInfo("Имя", info.getIm());
		 				addLineToDetailInfo("Отчество", info.getOt());
		 				addLineToDetailInfo("Дата рождения", info.isSetDatar(), DateFormat.getDateInstance().format(new Date(info.getDatar())));
		 				addLineToDetailInfo("Серия полиса ОМС", info.getPoms_ser());
		 				addLineToDetailInfo("Номер полиса ОМС", info.getPoms_nom());
		 				addLineToDetailInfo("Пол", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_z30), info.isSetPol(), info.getPol()));
		 				addLineToDetailInfo("Место жительства", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_am0), info.isSetJitel(), info.getJitel()));
		 				addLineToDetailInfo("Социальный статус", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_az9), info.isSetSgrp(), info.getSgrp()));
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
		 				addLineToDetailInfo("Место работы/учебы", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_z43), info.isSetMrab(), info.getMrab()));
		 				addLineToDetailInfo("Тип места работы", info.getNcex());
		 				addLineToDetailInfo("Страховая организация ОМС", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_kas), info.isSetPoms_strg(), info.getPoms_strg()));
		 				addLineToDetailInfo("Тип документа ОМС", info.getPoms_tdoc());
		 				addLineToDetailInfo("Номер договора ОМС", info.getPoms_ndog());
		 				addLineToDetailInfo("Страховая организация ДМС", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_kas), info.isSetPdms_strg(), info.getPdms_strg()));
		 				addLineToDetailInfo("Серия полиса ДМС", info.getPdms_ser());
		 				addLineToDetailInfo("Номер полиса ДМС", info.getPdms_nom());
		 				addLineToDetailInfo("Номер договора ДМС", info.getPdms_ndog());
		 				addLineToDetailInfo("Поликлиника прикрепления", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_n00), info.isSetCpol_pr(), info.getCpol_pr()));
		 				addLineToDetailInfo("Территория прикрепления", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_l01), info.isSetTerp(), info.getTerp()));
		 				addLineToDetailInfo("Дата прикрепления", info.isSetDatapr(), DateFormat.getDateInstance().format(new Date(info.getDatapr())));
		 				addLineToDetailInfo("Тип удостоверения личности", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_az0), info.isSetTdoc(), info.getTdoc()));
		 				addLineToDetailInfo("Серия документа", info.getDocser());
		 				addLineToDetailInfo("Номер документа", info.getDocnum());
		 				addLineToDetailInfo("Дата выдачи документа", info.isSetDatadoc(), DateFormat.getDateInstance().format(new Date(info.getDatadoc())));
		 				addLineToDetailInfo("Дата сверки данных", info.isSetDsv(), DateFormat.getDateInstance().format(new Date(info.getDsv())));
		 				addLineToDetailInfo("Кем выдан документ", info.getOdoc());
		 				addLineToDetailInfo("СНИЛС", info.getSnils());
		 				addLineToDetailInfo("Профессия", info.getProf());
		 				addLineToDetailInfo("Телефон", info.getTel());
		 				addLineToDetailInfo("Область проживания", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_l02), info.isSetRegion_liv(), info.getRegion_liv()));
		 				addLineToDetailInfo("Территория проживания", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_l01), info.isSetTer_liv(), info.getTer_liv()));
		 				eptxt.setText(sb.toString());
		 			} else if (lastPath.toString() ==  "Анамнез жизни"){
		 				try {
	 						PatientSignInfo psign = MainForm.tcl.getPatientSignInfo(info.npasp);
							addLineToDetailInfo("Группа крови", psign.getGrup());
							addLineToDetailInfo("Резус-фактор", psign.getPh());
							addLineToDetailInfo("Аллерго-анамнез", psign.getAllerg());
							addLineToDetailInfo("Фармакологический анамнез", psign.getFarmkol());
							addLineToDetailInfo("Анамнез жизни", psign.getVitae());
							addHeader("Вредные привычки");
							if (psign.getVred().charAt(0) == '1') addHeader("курение");
							if (psign.getVred().charAt(1) == '1') addHeader("злоупотребление алкоголем");
							if (psign.getVred().charAt(2) == '1') addHeader("алкоголизм");
							if (psign.getVred().charAt(3) == '1') addHeader("наркотики");
							if (psign.getVred() == "0000") addHeader("-");
						} catch (KmiacServerException e1) {
							System.err.println(e1.getMessage());
							eptxt.setText("");
						} catch (TException e1) {
							MainForm.conMan.reconnect(e1);
						}
						eptxt.setText(sb.toString());
		 			} else if (lastPath instanceof PdiagTreeNode) {
			 			PdiagTreeNode pdiagNode = (PdiagTreeNode) lastPath;
			 			PatientDiagZInfo pdiag = pdiagNode.pdiag;
						addLineToDetailInfo("Поликлиника",getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_n00),pdiag.isSetCpodr(),MainForm.authInfo.getCpodr()));
						addLineToDetailInfo("Медицинское описание", pdiag.isSetNamed(),pdiag.getNamed());
						addLineToDetailInfo("Дата регистрации", pdiag.isSetDatad(),DateFormat.getDateInstance().format(new Date(pdiag.getDatad())));
						addLineToDetailInfo("Обстоятельства регистрации", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_abv),pdiag.isSetNmvd(),pdiag.getNmvd()));
						addLineToDetailInfo("Характер заболевания", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_abx),pdiag.isSetXzab(),pdiag.getXzab()));
						addLineToDetailInfo("Стадия заболевания", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_aby),pdiag.isSetStady(),pdiag.getStady()));
						addLineIfInt("Состоит на д.учете: да", pdiag.isSetDisp(), pdiag.getDisp());
						addLineToDetailInfo("Дата постановки на д/у ", pdiag.isSetD_vz(), DateFormat.getDateInstance().format(new Date(pdiag.getD_vz())));
						addLineToDetailInfo("Группа д/у", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_abc),pdiag.isSetD_grup(),pdiag.getD_grup()));
						addLineToDetailInfo("Исход д/у", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_abb),pdiag.isSetIshod(),pdiag.getIshod()));
			 			addLineToDetailInfo("Дата установления исхода", pdiag.isSetDataish(), DateFormat.getDateInstance().format(new Date(pdiag.getDataish())));
						addLineToDetailInfo("Дата установления группы д/у", pdiag.isSetDatag(), DateFormat.getDateInstance().format(new Date(pdiag.getDatag())));
//		 				addLineToDetailInfo("Врач, ведущий д/у", pdiag.isSetFio_vr(),pdiag.getFio_vr());
//						addLineToDetailInfo("Должность врача, ведущего д/у", getValueFromClassifier(ConnectionManager.instance.getStringClassifier(StringClassifiers.n_s00),pdiag.isSetCdol_ot(),pdiag.getCdol_ot()));
						addLineIfInt("Противопоказания к вынашиванию беременности: есть", pdiag.isSetPat(), pdiag.getPat());
						addLineIfInt("Участие в боевых действиях: да", pdiag.isSetPrizb(), pdiag.getPrizb());
						addLineIfInt("Инвалидизующий диагноз: да", pdiag.isSetPrizi(), pdiag.getPrizi());
						eptxt.setText(sb.toString());
		 			} else if (lastPath instanceof PvizitTreeNode) {
		 				PvizitTreeNode pvizitNode = (PvizitTreeNode) lastPath;
		 				PatientVizitInfo pvizit = pvizitNode.pvizit;
						addLineToDetailInfo("Цель обращения", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_p0c), pvizit.isSetCobr(), pvizit.getCobr()));
		 				addLineToDetailInfo("Должность", getValueFromClassifier(ConnectionManager.instance.getStringClassifier(StringClassifiers.n_s00), pvizit.isSetCdol(), pvizit.getCdol()));
		 				try {
							PatientAnamZabInfo anamnez =  MainForm.tcl.getPatientAnamZabInfo(pvizit.getId(),pvizit.getNpasp());
							addLineToDetailInfo("История настоящего заболевания",anamnez.isSetT_ist_zab(), anamnez.getT_ist_zab());
						} catch (KmiacServerException e1) {
							System.err.println(e1.getMessage());
						} catch (TException e1) {
							MainForm.conMan.reconnect(e1);
						}
						for (PatientIsslInfo issl : MainForm.tcl.getPatientIsslInfoList(pvizit.getId())) {
							
		 					if (issl.isSetNisl()) {
		 	 				addLineToDetailInfo("Показатель исследования",issl.isSetPokaz_name(),issl.getPokaz_name());
		 					addLineToDetailInfo("Результат исследования",issl.isSetRez(),issl.getRez());
		 					addLineToDetailInfo("Дата проведения исследования",issl.isSetDatav(),DateFormat.getDateInstance().format(new Date(issl.getDatav())));}
		 				}
		 				
		 				for (PatientNaprInfo pnapr : MainForm.tcl.getPatientNaprInfoList(pvizit.getId())) {
		 	 				addLineToDetailInfo("Наименование мед.документа, выписанного пациенту",pnapr.isSetName(),pnapr.getName());
		 					addLineToDetailInfo("Обоснование для направления",pnapr.isSetText(),pnapr.getText());
		 					addLineToDetailInfo("Врач, выписавший документ",pnapr.isSetZaved(),pnapr.getZaved());
		 				}
		 				addLineToDetailInfo("Исход", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_ap0), pvizit.isSetIshod(), pvizit.getIshod()));
						addLineToDetailInfo("Результат", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_aq0), pvizit.isSetRezult(), pvizit.getRezult()));
						addLineToDetailInfo("Заключение специалиста",pvizit.isSetZakl(),pvizit.getZakl());
			 			addLineToDetailInfo("Рекомендации", pvizit.isSetRecomend(),pvizit.getRecomend());
			 			addLineToDetailInfo("Назначенное лечение", pvizit.isSetLech(), pvizit.getLech());
	
		 				eptxt.setText(sb.toString());
		 			} else if (lastPath instanceof PvizitAmbNode) {
		 				PvizitAmbNode pvizitAmbNode = (PvizitAmbNode) lastPath;
		 				PatientVizitAmbInfo pam = pvizitAmbNode.pam;
		 				PatientPriemInfo priem =  MainForm.tcl.getPatientPriemInfo(pam.getNpasp(),pam.getId());
//						addLineToDetailInfo("id: ", pam.isSetId(), pam.getId());
						addLineToDetailInfo("Должность врача, ведущего прием",getValueFromClassifier(ConnectionManager.instance.getStringClassifier(StringClassifiers.n_s00), pam.isSetCdol(), pam.getCdol()));
						addLineToDetailInfo("Врач, ведущий прием",pam.isSetFio_vr(),pam.getFio_vr());
						addLineToDetailInfo("Место обслуживания",getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_abs), pam.isSetMobs(), pam.getMobs()));
						addLineToDetailInfo("Цель посещения",getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_p0c), pam.isSetCpos(), pam.getCpos()));
						addLineToDetailInfo("Жалобы",priem.isSetT_jalob(), priem.getT_jalob());
						addLineToDetailInfo("ЧСС",priem.isSetT_chss(), priem.getT_chss());
						addLineToDetailInfo("Температура",priem.isSetT_temp(), priem.getT_temp());
						addLineToDetailInfo("Артериальное давление",priem.isSetT_ad(), priem.getT_ad());
						addLineToDetailInfo("Рост",priem.isSetT_rost(), priem.getT_rost());
						addLineToDetailInfo("Вес",priem.isSetT_ves(), priem.getT_ves());
						addLineToDetailInfo("Status praesense",priem.isSetT_status_praesense(), priem.getT_status_praesense());
						addLineToDetailInfo("Физикальное обследование",priem.isSetT_fiz_obsl(), priem.getT_fiz_obsl());
						addLineToDetailInfo("Рекомендации",priem.isSetT_recom(), priem.getT_recom());
						addLineToDetailInfo("Status localis",priem.isSetT_st_localis(), priem.getT_st_localis());
						addLineToDetailInfo("Оценка данных анамнеза и объективного исследования",priem.isSetT_ocenka(), priem.getT_ocenka());
						for (PatientDiagAmbInfo pdiagamb : MainForm.tcl.getPatientDiagAmbInfoList(pam.getId())) {
		 	 				addLineToDetailInfo("Код МКБ",pdiagamb.isSetDiag(),pdiagamb.getDiag());
		 					addLineToDetailInfo("Медицинское описание диагноза",pdiagamb.isSetNamed(),pdiagamb.getNamed());
		 					addLineToDetailInfo("Статус диагноза",getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_vdi), pdiagamb.isSetDiag_stat(),pdiagamb.getDiag_stat()));
		 					if (pdiagamb.predv==false) addHeader("Вид диагноза: заключительный");
		 					else addHeader("Вид диагноза: предварительный");
		 					}
						addLineToDetailInfo("Результат", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_aq0), pam.isSetRezult(), pam.getRezult()));
						eptxt.setText(sb.toString());
			 		} else if (lastPath instanceof RdslTreeNode) {
		 				RdslTreeNode rdslNode = (RdslTreeNode) lastPath;
		 				RdSlInfo rdsl = rdslNode.rdsl;
			 			addLineToDetailInfo("Номер", rdsl.isSetId(), rdsl.getId());
		 				addLineToDetailInfo("Дата первого посещения по беременности", rdsl.isSetDatay(), DateFormat.getDateInstance().format(new Date(rdsl.getDatay())));
		 				addLineToDetailInfo("Дата первого шевеления плода", rdsl.isSetDataosl(), DateFormat.getDateInstance().format(new Date(rdsl.getDataosl())));
		 				addLineToDetailInfo("Количество абортов", rdsl.isSetAbort(), rdsl.getAbort());
		 				addLineToDetailInfo("Паритет беременности", rdsl.isSetShet(), rdsl.getShet());
		 				addLineToDetailInfo("Дата последних месячных", rdsl.isSetDataM(), DateFormat.getDateInstance().format(new Date(rdsl.getDataM())));
		 				addLineToDetailInfo("Первая явка (кол-во недель срока беременности)", rdsl.isSetYavka1(), rdsl.getYavka1());
		 				addLineToDetailInfo("Причина снятия с учета", rdsl.isSetIshod(), rdsl.getIshod());
		 				addLineToDetailInfo("Дата снятия с учета", rdsl.isSetDatasn(), DateFormat.getDateInstance().format(new Date(rdsl.getDatasn())));
		 				addLineToDetailInfo("Дата планируемых родов", rdsl.isSetDataZs(), DateFormat.getDateInstance().format(new Date(rdsl.getDataZs())));
		 				addLineToDetailInfo("Паритет родов", rdsl.isSetAbort(), rdsl.getAbort());
		 				addLineToDetailInfo("Количество живых детей", rdsl.isSetDeti(), rdsl.getDeti());
	//	 				addLineIf("Контрацепция: да", rdsl.isSetKont(), rdsl.getKont());
		 				addLineToDetailInfo("Вес до беременности", rdsl.isSetVesd(), rdsl.getVesd());
		 				addLineToDetailInfo("Таз: DSP", rdsl.isSetDsp(), rdsl.getDsp());
		 				addLineToDetailInfo("Таз: DSR", rdsl.isSetDsr(),rdsl.getDsr());
		 				addLineToDetailInfo("Таз: DTroch", rdsl.isSetDTroch(), rdsl.getDTroch());
		 				addLineToDetailInfo("Таз: C.ext", rdsl.isSetCext(), rdsl.getCext());
		 				addLineToDetailInfo("Индекс Соловьева", rdsl.isSetIndsol(), rdsl.getIndsol());
		 				addLineToDetailInfo("Продолжительность менструального цикла", rdsl.isSetPrmen(), rdsl.getPrmen());
		 				addLineToDetailInfo("Дата записи", rdsl.isSetDataz(), DateFormat.getDateInstance().format(new Date(rdsl.getDataz())));
		 				addLineToDetailInfo("Дата выдачи сертификата", rdsl.isSetDatasert(), DateFormat.getDateInstance().format(new Date(rdsl.getDatasert())));
		 				addLineToDetailInfo("Номер родового сертификата", rdsl.isSetNsert(), rdsl.getNsert());
		 				addLineToDetailInfo("Серия родового сертификата", rdsl.isSetSsert(), rdsl.getSsert());
		 				addLineToDetailInfo("Осложнение после аборта", rdsl.isSetOslab(), rdsl.getOslab());
		 				addLineToDetailInfo("Планируемые роды", rdsl.isSetPlrod(), rdsl.getPlrod());
		 				addLineToDetailInfo("Описание предыдущих родов", rdsl.isSetPrrod(), rdsl.getPrrod());
		 				addLineToDetailInfo("Возраст Менархе", rdsl.isSetVozmen(), rdsl.getVozmen());
		 				addLineToDetailInfo("Осложнения предыдущих родов", rdsl.isSetOslrod(), rdsl.getOslrod());
		 				addLineToDetailInfo("Со сколько лет половая жизнь", rdsl.isSetPolj(), rdsl.getPolj());
		 				addLineToDetailInfo("Дата предыдущего аборта", rdsl.isSetDataab(), DateFormat.getDateInstance().format(new Date(rdsl.getDataab())));
		 				addLineToDetailInfo("Срок предыдущего аборта", rdsl.isSetSrokab(), rdsl.getSrokab());
		 				addLineToDetailInfo("C.diag", rdsl.isSetCdiagt(), rdsl.getCdiagt());
		 				addLineToDetailInfo("C.vera", rdsl.isSetCvera(), rdsl.getCvera());
						eptxt.setText(sb.toString());
		 			} 	
		 			
			 		else if (lastPath instanceof CgospTreeNode) {
			 			CgospTreeNode gospNode = (CgospTreeNode) lastPath;
		 				CgospInfo gosp = gospNode.gosp;
			 			addLineToDetailInfo("Номер случая госпитализации", gosp.isSetId(), gosp.getId());
		 				addLineToDetailInfo("Номер истории болезни", gosp.isSetNist(), gosp.getNist());
		 				addLineToDetailInfo("Дата поступления в стационар", gosp.isSetDatap(), DateFormat.getDateInstance().format(new Date(gosp.getDatap())));
		 				addLineToDetailInfo("Время поступления в стационар", gosp.isSetVremp(), DateFormat.getTimeInstance().format(new Time(gosp.getVremp())));
		 				addLineToDetailInfo("Вид обращения", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_vgo), gosp.isSetPl_extr(), gosp.getPl_extr()));
		 				//addLineToDetailInfo("Кем направлен", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_k02), gosp.isSetPl_extr(), gosp.getPl_extr()));
		 				addLineToNaprCls("Направившее учреждение", gosp.getNaprav(), gosp.isSetN_org(), gosp.getN_org());
		 				addLineToDetailInfo("Отделение стационара, куда госпитализирован", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_o00), gosp.isSetCotd(), gosp.getCotd()));
		 				addLineToDetailInfo("Своевременность госпитализации (в часах от начала заболевания)", gosp.isSetSv_time(), gosp.getSv_time());
		 				addLineToDetailInfo("Своевременность госпитализации (в сутках от начала госпитализации)", gosp.isSetSv_day(), gosp.getSv_day());
		 				addLineToDetailInfo("Номер талона на плановую госпитализацию", gosp.isSetNtalon(), gosp.getNtalon());
		 				addLineToDetailInfo("Код травмы", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_ai0), gosp.isSetVidtr(), gosp.getVidtr()));
		 				addLineToDetailInfo("Причина отказа в госпитализации", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_af0), gosp.isSetPr_out(), gosp.getPr_out()));
		 				addLineToDetailInfo("Присутствие алкоголя в крови", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_o00), gosp.isSetAlkg(), gosp.getAlkg()));
		 				addLineIfBool("Отметка о сообщении родственникам: да", gosp.isSetMeesr(), gosp.isMeesr());
		 				addLineToDetailInfo("Вид транспортировки", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_vtr), gosp.isSetVid_tran(), gosp.getVid_tran()));
		 				addLineToDetailInfo("Код диагноза направившего учреждения", gosp.isSetDiag_n(), gosp.getDiag_n());
		 				addLineToDetailInfo("Наименование диагноза направившего учреждения", gosp.isSetNamed_n(), gosp.getNamed_n());
		 				addLineToDetailInfo("Код диагноза приемного отделения", gosp.isSetDiag_p(), gosp.getDiag_p());
		 				addLineToDetailInfo("Наименование диагноза приемного отделения", gosp.isSetNamed_p(), gosp.getNamed_p());
		 				addLineIfBool("Наличие чесотки: да", gosp.isSetNal_z(), gosp.isNal_z());
		 				addLineIfBool("Наличие педикулеза: да", gosp.isSetNal_p(), gosp.isNal_p());
		 				//addLineToDetailInfo("Наличие педикулеза", gosp.isSetNal_p(), gosp.isNal_p());
		 				addLineToDetailInfo("Температура при поступлении", gosp.isSetT0c(), gosp.getT0c());
		 				addLineToDetailInfo("Артериальное давление при поступлении", gosp.isSetAd(), gosp.getAd());
		 				addLineToDetailInfo("Дата вызова скорой помощи", gosp.isSetSmp_data(), DateFormat.getDateInstance().format(new Date(gosp.getSmp_data())));
		 				addLineToDetailInfo("Время вызова скорой помощи",  gosp.isSetSmp_time(), DateFormat.getTimeInstance().format(new Time(gosp.getSmp_time())));
		 				addLineToDetailInfo("Номер вызова скорой помощи", gosp.isSetSmp_num(), gosp.getSmp_num());
		 				addLineToDetailInfo("Дата госпитализации в отделение", gosp.isSetDatagos(), DateFormat.getDateInstance().format(new Date(gosp.getDatagos())));
		 				addLineToDetailInfo("Время госпитализации", gosp.isSetVremgos(), DateFormat.getTimeInstance().format(new Time(gosp.getVremgos())));
		 				addLineToDetailInfo("Дата осмотра в приемном отделении", gosp.isSetDataosm(), DateFormat.getDateInstance().format(new Date(gosp.getDataosm())));
		 				addLineToDetailInfo("Время осмотра в приемном отделении", gosp.isSetVremosm(), DateFormat.getTimeInstance().format(new Time(gosp.getVremosm())));
		 				addLineToDetailInfo("Жалобы при поступлении", gosp.isSetJalob(), gosp.getJalob());
		 				addLineToDetailInfo("Вид стационара", getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_tip), gosp.isSetVid_st(), gosp.getVid_st()));
		 				addLineIfBool("Признак беременности: да", gosp.isSetPr_ber(), gosp.isPr_ber());
						eptxt.setText(sb.toString());
		 			} 	
		 			
			 		else if (lastPath.toString() == "Случаи заболевания") {
			 			if (posinfo.getChildCount()==0) eptxt.setText("");
			 		}
		 			else if (lastPath.toString() == "Заключительные диагнозы") {
			 			if (diaginfo.getChildCount()==0) eptxt.setText("");
		 			}
		 			else if (lastPath.toString() == "Случаи беременности") {
			 			if (berinfo.getChildCount()==0) eptxt.setText("");
		 			}
		 			
		 			else if (lastPath.toString() == "Случаи госпитализации") {
			 			if (berinfo.getChildCount()==0) eptxt.setText("");
		 			}
	 			}
		 		
	 			catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
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
						for (PatientVizitAmbInfo pvizAmbChild : MainForm.tcl.getPatientVizitAmbInfoList(pvizitNode.pvizit.getId())) {
							pvizitNode.add(new PvizitAmbNode(pvizAmbChild));
						}
						((DefaultTreeModel) treeinfo.getModel()).reload(pvizitNode);
					} catch (KmiacServerException e) {
						e.printStackTrace();
					} catch (TException e) {
						MainForm.conMan.reconnect(e);
					}
		 		}
		 	}
		 });
		sptree.setViewportView(treeinfo);
		treeinfo.setShowsRootHandles(true);
		treeinfo.setRootVisible(false);
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) treeinfo.getCellRenderer();
		renderer.setLeafIcon(null);
		renderer.setClosedIcon(null);
		renderer.setOpenIcon(null);
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update(info.npasp);
			}
		});
		
		pl.setLayout(gl_pl);
		
		JPanel pr = new JPanel();
		pr.setMinimumSize(new Dimension(256, 10));
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
		eptxt.setFont(new Font("Arial", Font.PLAIN, 12));
		sptxt.setViewportView(eptxt);
		eptxt.setEditable(false);
		pr.setLayout(gl_pr);
		getContentPane().setLayout(groupLayout);
	}
	
	public void update(int npasp) {
		try {
			info = MainForm.tcl.getPatientCommonInfo(npasp);
			treeinfo.setModel(new DefaultTreeModel(createNodes(info.pol,(int) ((System.currentTimeMillis() - info.datar) / 31556952000L))));
		} catch (KmiacServerException e) {
			e.printStackTrace();
		} catch (TException e) {
			MainForm.conMan.reconnect(e);
		}
	}
	
	private DefaultMutableTreeNode createNodes(int pol, int age) {
		root = new DefaultMutableTreeNode("Корень");
		patinfo = new DefaultMutableTreeNode("Личная информация");
		signinfo = new DefaultMutableTreeNode("Анамнез жизни");
		posinfo = new DefaultMutableTreeNode("Случаи заболевания");
		diaginfo = new DefaultMutableTreeNode("Заключительные диагнозы");
		berinfo = new DefaultMutableTreeNode("Случаи беременности");
		gospinfo = new DefaultMutableTreeNode("Случаи госпитализации");
		
		root.add(patinfo);
		root.add(signinfo);
		root.add(posinfo);
		root.add(diaginfo);
		root.add(gospinfo);
		
		try {
			for (PatientVizitInfo pvizit : MainForm.tcl.getPatientVizitInfoList(info.npasp, tfdatn.getDate().getTime(), tfDatk.getDate().getTime()))
					posinfo.add(new PvizitTreeNode(pvizit));
			for (PatientDiagZInfo pdiag : MainForm.tcl.getPatientDiagZInfoList(info.npasp))
				diaginfo.add(new PdiagTreeNode(pdiag));
			if ((pol!=1) & ((age > 13) & (age < 50))) {
				root.add(berinfo);
				for (RdSlInfo rdsl : MainForm.tcl.getRdSlInfoList(info.npasp)) 
					berinfo.add(new RdslTreeNode(rdsl));}
			for (CgospInfo gosp : MainForm.tcl.getCgospinfo(info.npasp))
					gospinfo.add(new CgospTreeNode(gosp));
			

		} catch (KmiacServerException e) {
			e.printStackTrace();
		} catch (TException e) {
			MainForm.conMan.reconnect(e);
		} 

		return root;
	}
	
	class PvizitTreeNode extends DefaultMutableTreeNode {
		private static final long serialVersionUID = 4212592425962984738L;
		private PatientVizitInfo pvizit;
		
		public PvizitTreeNode(PatientVizitInfo pvizit) {
			this.pvizit = pvizit;
			this.add(new PvizitAmbNode(new PatientVizitAmbInfo()));
			
		}
		
		@Override
		public String toString() {
			return DateFormat.getDateInstance().format(new Date(pvizit.getDatao()));
		}
	}
	
	class PvizitAmbNode extends DefaultMutableTreeNode{
		private static final long serialVersionUID = -5215124870459111226L;
		private PatientVizitAmbInfo pam;
		
		public PvizitAmbNode(PatientVizitAmbInfo pam) {
			this.pam = pam;
		}
		
		@Override
		public String toString() {
			return DateFormat.getDateInstance().format(new Date(pam.getDatap()));
		}
	}
	
	class PdiagTreeNode extends DefaultMutableTreeNode {
		private static final long serialVersionUID = -46003968655861926L;
		private PatientDiagZInfo pdiag;
		
		public PdiagTreeNode(PatientDiagZInfo pdiag) {
			this.pdiag = pdiag;
			
		}
		
		@Override
		public String toString() {
			return pdiag.getDiag();
		}
	}
	
	class RdslTreeNode extends DefaultMutableTreeNode {
		private static final long serialVersionUID = -3165163738807727905L;
		private RdSlInfo rdsl;
		
		public RdslTreeNode(RdSlInfo rdsl) {
			this.rdsl = rdsl;
			
		}
		
		@Override
		public String toString() {
			return DateFormat.getDateInstance().format(new Date(rdsl.getDatay()));
		}
	}
	
	class CgospTreeNode extends DefaultMutableTreeNode {
		private static final long serialVersionUID = -46003968655861926L;
		private CgospInfo gosp;
		
		public CgospTreeNode(CgospInfo gosp) {
			this.gosp = gosp;
			
		}
		
		@Override
		public String toString() {
			return DateFormat.getDateInstance().format(new Date(gosp.getDatap()));
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
	
	private void addHeader(String name) {
		sb.append(name + lineSep);
	}
	
	private void addLineIfInt(String txt,boolean isSet, int value) {
		if (isSet)
			if (value == 1)
				if (txt.toString().length() > 0)
					sb.append(txt + lineSep);
	}

	private void addLineIfBool(String txt,boolean isSet, boolean value) {
		if (isSet)
			if (value == true)
				if (txt.toString().length() > 0)
					sb.append(txt + lineSep);
	}
	
	private void addLineToNaprCls(String txt, String value, boolean isSet, int valueCls) {
		if (isSet)
			if ((txt != null) && (value != null))
				if ((txt.length() > 0) && (value.toString().length() > 0))
				{
					if (value.equals("К"))
						sb.append(String.format("%s: %s%s", txt, getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_n00), isSet, valueCls), lineSep));
					if (value.equals("Г"))
						sb.append(String.format("%s: %s%s", txt, getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_al0), isSet, valueCls), lineSep));
					if (value.equals("Л"))
						sb.append(String.format("%s: %s%s", txt, getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_m00), isSet, valueCls), lineSep));
					if (value.equals("Р"))
						sb.append(String.format("%s: %s%s", txt, getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_w04), isSet, valueCls), lineSep));
					if (value.equals("Т"))
						sb.append(String.format("%s: %s%s", txt, getValueFromClassifier(ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_o00), isSet, valueCls), lineSep));
				}
	}
	
	@Override
	public Object getResults() {
		return null;
	}
}
