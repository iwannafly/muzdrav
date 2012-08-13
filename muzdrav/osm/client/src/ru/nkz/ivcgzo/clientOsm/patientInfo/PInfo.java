package ru.nkz.ivcgzo.clientOsm.patientInfo;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
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

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientOsm.MainForm;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.AnamZab;
import ru.nkz.ivcgzo.thriftOsm.IsslInfo;
import ru.nkz.ivcgzo.thriftOsm.PatientCommonInfo;
import ru.nkz.ivcgzo.thriftOsm.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.PdiagAmb;
import ru.nkz.ivcgzo.thriftOsm.PdiagZ;
import ru.nkz.ivcgzo.thriftOsm.Priem;
import ru.nkz.ivcgzo.thriftOsm.PriemNotFoundException;
import ru.nkz.ivcgzo.thriftOsm.Psign;
import ru.nkz.ivcgzo.thriftOsm.Pvizit;
import ru.nkz.ivcgzo.thriftOsm.PvizitAmb;
import java.awt.Dimension;

public class PInfo extends JFrame {
	private static final long serialVersionUID = 7025194439882492263L;
	private static final String lineSep = System.lineSeparator();
	private JEditorPane eptxt;
	private JTree treeinfo;
	private StringBuilder sb;
	private CustomDateEditor tfdatn;
	private CustomDateEditor tfDatk;
	private int npasp;

	public PInfo() {
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
		
		
		
		treeinfo = new JTree(createNodes());
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
		 			if (lastPath.toString() ==  "Личная информация"){
		 				PatientCommonInfo info;
						try {
							info = MainForm.tcl.getPatientCommonInfo(npasp);
							addLineToDetailInfo("Уникальный номер", info.isSetNpasp(), info.getNpasp());
		 				addLineToDetailInfo("Фамилия", info.getFam());
		 				addLineToDetailInfo("Имя", info.getIm());
		 				addLineToDetailInfo("Отчество", info.getOt());
		 				addLineToDetailInfo("Дата рождения", info.isSetDatar(), DateFormat.getDateInstance().format(new Date(info.getDatar())));
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
		 				//addLineToDetailInfo("Место работы", getValueFromClassifier(Classifiers.n_z43, info.isSetMrab()(), info.getName_mr());
		 				addLineToDetailInfo("Тип места работы", info.getNcex());
		 				addLineToDetailInfo("Страховая организация ОМС", getValueFromClassifier(Classifiers.n_kas, info.isSetPoms_strg(), info.getPoms_strg()));
		 				addLineToDetailInfo("Тип документа ОМС", info.getPoms_tdoc());
		 				addLineToDetailInfo("Номер договора ОМС", info.getPoms_ndog());
		 				addLineToDetailInfo("Страховая организация ДМС", getValueFromClassifier(Classifiers.n_kas, info.isSetPdms_strg(), info.getPdms_strg()));
		 				addLineToDetailInfo("Серия полиса ДМС", info.getPdms_ser());
		 				addLineToDetailInfo("Номер полиса ДМС", info.getPdms_nom());
		 				addLineToDetailInfo("Номер договора ДМС", info.getPdms_ndog());
		 				addLineToDetailInfo("Поликлиника прикрепления", getValueFromClassifier(Classifiers.n_n00, info.isSetCpol_pr(), info.getCpol_pr()));
		 				addLineToDetailInfo("Территория прикрепления", getValueFromClassifier(Classifiers.n_l01, info.isSetTerp(), info.getTerp()));
		 				addLineToDetailInfo("Дата прикрепления", info.isSetDatapr(), DateFormat.getDateInstance().format(new Date(info.getDatapr())));
		 				addLineToDetailInfo("Тип удостоверения личности", getValueFromClassifier(Classifiers.n_az0, info.isSetTdoc(), info.getTdoc()));
		 				addLineToDetailInfo("Серия документа", info.getDocser());
		 				addLineToDetailInfo("Номер документа", info.getDocnum());
		 				addLineToDetailInfo("Дата выдачи документа", info.isSetDatadoc(), DateFormat.getDateInstance().format(new Date(info.getDatadoc())));
		 				addLineToDetailInfo("Дата сверки данных", info.isSetDsv(), DateFormat.getDateInstance().format(new Date(info.getDsv())));
		 				addLineToDetailInfo("Кем выдан документ", info.getOdoc());
		 				addLineToDetailInfo("СНИЛС", info.getSnils());
		 				addLineToDetailInfo("Профессия", info.getProf());
		 				addLineToDetailInfo("Телефон", info.getTel());
		 				addLineToDetailInfo("Область проживания", getValueFromClassifier(Classifiers.n_l02, info.isSetRegion_liv(), info.getRegion_liv()));
		 				addLineToDetailInfo("Территория проживания", getValueFromClassifier(Classifiers.n_l01, info.isSetTer_liv(), info.getTer_liv()));
		 				eptxt.setText(sb.toString());

						} catch (PatientNotFoundException e1) {
							e1.printStackTrace();
						}
		 						 			}
		 			else
		 				if (lastPath.toString() ==  "Анамнез жизни"){
		 					Psign psign;
		 					try {
								psign = MainForm.tcl.getPatientMiscInfo(npasp);
								addLineToDetailInfo("Группа крови", psign.getGrup());
								addLineToDetailInfo("Резус-фактор", psign.getPh());
								addLineToDetailInfo("Аллерго-анамнез", psign.getAllerg());
								addLineToDetailInfo("Фармакологический анамнез", psign.getFarmkol());
								addLineToDetailInfo("Анамнез жизни", psign.getVitae());
								addLineToDetailInfo("Вредные привычки", psign.getVred());
								addLineToDetailInfo("Развитие", psign.getRazv());
								addLineToDetailInfo("Условия проживания", psign.getUslov());
								addLineToDetailInfo("Перенесенные заболевания", psign.getPer_zab());
								addLineToDetailInfo("Перенесенные операции", psign.getPer_oper());
								addLineToDetailInfo("Гемотрансфузия", psign.getGemotrans());
								addLineToDetailInfo("Наследственность", psign.getNasl());
								addLineToDetailInfo("Гинекологический анамнез", psign.getGinek());
								addLineToDetailInfo("Прием лекарственных средств", psign.getPriem_lek());
								addLineToDetailInfo("Применение гормональных аппаратов", psign.getPrim_gorm());
								eptxt.setText(sb.toString());
							} catch (PatientNotFoundException e1) {
								e1.printStackTrace();
							}
		 				}
		 				else
		 		if (lastPath instanceof PdiagTreeNode) {
		 			PdiagTreeNode pdiagNode = (PdiagTreeNode) lastPath;
		 			PdiagZ pdiag = pdiagNode.pdiag;
					addLineToDetailInfo("Поликлиника",getValueFromClassifier(MainForm.tcl.get_n_n00(),pdiag.isSetCpodr(),MainForm.authInfo.getCpodr()));
					addLineToDetailInfo("Медицинское описание", pdiag.isSetNamed(),pdiag.getNamed());
					addLineToDetailInfo("Дата регистрации", pdiag.isSetDatad(),pdiag.getDatad());
					addLineToDetailInfo("Обстоятельства регистрации", getValueFromClassifier(MainForm.tcl.get_n_abv(),pdiag.isSetNmvd(),pdiag.getNmvd()));
					addLineToDetailInfo("Характер заболевания", getValueFromClassifier(MainForm.tcl.get_n_abx(),pdiag.isSetXzab(),pdiag.getXzab()));
					addLineToDetailInfo("Стадия заболевания", getValueFromClassifier(MainForm.tcl.get_n_aby(),pdiag.isSetStady(),pdiag.getStady()));
					addLineIf("Состоит на д.учете: да", pdiag.isSetDisp(), pdiag.getDisp());
					addLineToDetailInfo("Дата постановки на д/у ", pdiag.isSetD_vz(), DateFormat.getDateInstance().format(new Date(pdiag.getD_vz())));
					addLineToDetailInfo("Группа д/у", getValueFromClassifier(MainForm.tcl.get_n_abc(),pdiag.isSetD_grup(),pdiag.getD_grup()));
					addLineToDetailInfo("Исход д/у", getValueFromClassifier(MainForm.tcl.get_n_abb(),pdiag.isSetIshod(),pdiag.getIshod()));
		 			addLineToDetailInfo("Дата установления исхода", pdiag.isSetDataish(), DateFormat.getDateInstance().format(new Date(pdiag.getDataish())));
					addLineToDetailInfo("Дата установления группы д/у", pdiag.isSetDatag(), DateFormat.getDateInstance().format(new Date(pdiag.getDatag())));
	 				addLineToDetailInfo("Код врача, ведущего д/у", pdiag.isSetCod_sp(),pdiag.getCod_sp());
					addLineToDetailInfo("Должность врача, ведущего д/у", getValueFromClassifier(MainForm.tcl.get_n_s00(),pdiag.isSetCdol_ot(),pdiag.getCdol_ot()));
					addLineIf("Противопоказания к вынашиванию беременности: есть", pdiag.isSetPat(), pdiag.getPat());
					addLineIf("Участие в боевых действиях: да", pdiag.isSetPrizb(), pdiag.getPrizb());
					addLineIf("Инвалидизующий диагноз: да", pdiag.isSetPrizi(), pdiag.getPrizi());
					eptxt.setText(sb.toString());
		 			} 			
		 				else
		 					
		 		if (lastPath instanceof PvizitTreeNode) {
		 				PvizitTreeNode pvizitNode = (PvizitTreeNode) lastPath;
		 			Pvizit pvizit = pvizitNode.pvizit;
		 			AnamZab anamnez =  MainForm.tcl.getAnamZab(pvizit.getId(),pvizit.getNpasp());
		 			//addLineToDetailInfo("id: ", pvizit.isSetId(), pvizit.getId());
					addLineToDetailInfo("Цель обращения", getValueFromClassifier(MainForm.tcl.getP0c(), pvizit.isSetCobr(), pvizit.getCobr()));
					addLineToDetailInfo("Должность", getValueFromClassifier(Classifiers.n_s00, pvizit.isSetCdol(), pvizit.getCdol()));
		 			//addLineToDetailInfo("Врач", pvizit.isSetVrach_fio(),pvizit.getVrach_fio());
										//addLineToDetailInfo("Дата записи в базу", pvizit.isSetDataz(), DateFormat.getDateInstance().format(new Date(pvizit.getDataz())));
					//addHeader("Анамнез заболевания");
					addLineToDetailInfo("Начало заболевания",anamnez.isSetT_nachalo_zab(), anamnez.getT_nachalo_zab());
					addLineToDetailInfo("Симптомы",anamnez.isSetT_sympt(), anamnez.getT_sympt());
					addLineToDetailInfo("Отношение больного",anamnez.isSetT_otn_bol(), anamnez.getT_otn_bol());
					addLineToDetailInfo("Психологическая ситуация",anamnez.isSetT_ps_syt(), anamnez.getT_ps_syt());
					addHeader("Назначенные исследования");
	 				for (IsslInfo issl : MainForm.tcl.getIsslInfo(pvizit.getId())) {
	 					if (issl.isSetNisl()){
	 	 				addLineToDetailInfo("Показатель",issl.isSetPokaz_name(),issl.getPokaz_name());
	 					addLineToDetailInfo("Результат",issl.isSetRez(),issl.getRez());
	 					addLineToDetailInfo("Дата",issl.isSetDatav(),DateFormat.getDateInstance().format(new Date(issl.getDatav())));}
	 					else addLine("Исследований нет");
	 				}
	 				addHeader("Поставленные диагнозы");//getPdiagAmb
	 				for (PdiagAmb pdiagamb : MainForm.tcl.getPdiagAmb(pvizit.getId())) {
	 	 				addLineToDetailInfo("Код МКБ",pdiagamb.isSetDiag(),pdiagamb.getDiag());
	 					addLineToDetailInfo("Медицинское описание",pdiagamb.isSetNamed(),pdiagamb.getNamed());
	 					addLineToDetailInfo("Статус",getValueFromClassifier(MainForm.tcl.getVdi(), pdiagamb.isSetDiag_stat(),pdiagamb.getDiag_stat()));
	 				}
	 				addLineToDetailInfo("Исход", getValueFromClassifier(MainForm.tcl.getAq0(), pvizit.isSetIshod(), pvizit.getIshod()));
					addLineToDetailInfo("Результат", getValueFromClassifier(MainForm.tcl.getAp0(), pvizit.isSetRezult(), pvizit.getRezult()));
					addLineToDetailInfo("Заключение специалиста",pvizit.isSetZakl(),pvizit.getZakl());
		 			addLineToDetailInfo("Рекомендации", pvizit.isSetRecomend(),pvizit.getRecomend());

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
						//addHeader("Жалобы");
						//addLineToDetailInfo("Жалобы",priem.isSetT_jalob(), priem.getT_jalob());
						addLineToDetailInfo("Место обслуживания",getValueFromClassifier(MainForm.tcl.get_n_abs(), pam.isSetMobs(), pam.getMobs()));
						addLineToDetailInfo("Жалобы (дыхательная система)",priem.isSetT_jalob_d(), priem.getT_jalob_d());
						addLineToDetailInfo("Жалобы (система кровообращения)",priem.isSetT_jalob_krov(), priem.getT_jalob_krov());
						addLineToDetailInfo("Жалобы (система пищеварения)",priem.isSetT_jalob_p(), priem.getT_jalob_p());
						addLineToDetailInfo("Жалобы (мочеполовая система)",priem.isSetT_jalob_moch(), priem.getT_jalob_moch());
						addLineToDetailInfo("Жалобы (эндокринная система)",priem.isSetT_jalob_endo(), priem.getT_jalob_endo());
						addLineToDetailInfo("Жалобы (нервная система и органы чувств)",priem.isSetT_jalob_nerv(), priem.getT_jalob_nerv());
						addLineToDetailInfo("Жалобы (опорно-двигательная система)",priem.isSetT_jalob_opor(), priem.getT_jalob_opor());
						addLineToDetailInfo("Жалобы (лихорадка)",priem.isSetT_jalob_lih(), priem.getT_jalob_lih());
						addLineToDetailInfo("Жалобы (общего характера)",priem.isSetT_jalob_obh(), priem.getT_jalob_obh());
						addLineToDetailInfo("Жалобы (прочие)",priem.isSetT_jalob_proch(), priem.getT_jalob_proch());
						//addHeader("Status Praesense");
						//addLineToDetailInfo("Жалобы (прочие)",priem.isSetT_status_praesense(), priem.getT_status_praesense());
						addLineToDetailInfo("Общее состояние",priem.isSetT_ob_sost(), priem.getT_ob_sost());
						addLineToDetailInfo("Кожные покровы",priem.isSetT_koj_pokr(), priem.getT_koj_pokr());
						addLineToDetailInfo("Видимые слизистые",priem.isSetT_sliz(), priem.getT_sliz());
						addLineToDetailInfo("Подкожная клетчатка",priem.isSetT_podk_kl(), priem.getT_podk_kl());
						addLineToDetailInfo("Лимфатические узлы",priem.isSetT_limf_uzl(), priem.getT_limf_uzl());
						addLineToDetailInfo("Костно-мышечная система",priem.isSetT_kost_mysh(), priem.getT_kost_mysh());
						addLineToDetailInfo("Нервно-психический статус",priem.isSetT_nervn_ps(), priem.getT_nervn_ps());
						addLineToDetailInfo("ЧСС",priem.isSetT_chss(), priem.getT_chss());
						addLineToDetailInfo("Температура",priem.isSetT_temp(), priem.getT_temp());
						addLineToDetailInfo("Артериальное давление",priem.isSetT_ad(), priem.getT_ad());
						addLineToDetailInfo("Рост",priem.isSetT_rost(), priem.getT_rost());
						addLineToDetailInfo("Вес",priem.isSetT_ves(), priem.getT_ves());
						addLineToDetailInfo("Телосложение",priem.isSetT_telo(), priem.getT_telo());
						//addHeader("Физикальное обследование");
						//addLineToDetailInfo("Жалобы (прочие)",priem.isSetT_fiz_obsl(), priem.getT_fiz_obsl());
						addLineToDetailInfo("Суставы",priem.isSetT_sust(), priem.getT_sust());
						addLineToDetailInfo("Дыхание",priem.isSetT_dyh(), priem.getT_dyh());
						addLineToDetailInfo("Грудная клетка",priem.isSetT_gr_kl(), priem.getT_gr_kl());
						addLineToDetailInfo("Перкуссия легких",priem.isSetT_perk_l(), priem.getT_perk_l());
						addLineToDetailInfo("Аускультация легких",priem.isSetT_aus_l(), priem.getT_aus_l());
						addLineToDetailInfo("Бронхофония",priem.isSetT_bronho(), priem.getT_bronho());
						addLineToDetailInfo("Артерии и шейные вены",priem.isSetT_arter(), priem.getT_arter());
						addLineToDetailInfo("Осмотр области сердца",priem.isSetT_obl_s(), priem.getT_obl_s());
						addLineToDetailInfo("Перкуссия сердца",priem.isSetT_perk_s(), priem.getT_perk_s());
						addLineToDetailInfo("Аускультация сердца",priem.isSetT_aus_s(), priem.getT_aus_s());
						addLineToDetailInfo("Полость рта",priem.isSetT_pol_rta(), priem.getT_pol_rta());
						addLineToDetailInfo("Живот",priem.isSetT_jivot(), priem.getT_jivot());
						addLineToDetailInfo("Пальпация живота",priem.isSetT_palp_jivot(), priem.getT_palp_jivot());
						addLineToDetailInfo("Пальпация, перкуссия и аускультация ЖКТ",priem.isSetT_jel_kish(), priem.getT_jel_kish());
						addLineToDetailInfo("Пальпация желудка",priem.isSetT_palp_jel(), priem.getT_palp_jel());
						addLineToDetailInfo("Пальпация поджелудочной железы",priem.isSetT_palp_podjjel(), priem.getT_palp_podjjel());
						addLineToDetailInfo("Печень",priem.isSetT_pechen(), priem.getT_pechen());
						addLineToDetailInfo("Желчный пузырь",priem.isSetT_jelch(), priem.getT_jelch());
						addLineToDetailInfo("Селезенка",priem.isSetT_selez(), priem.getT_selez());
						addLineToDetailInfo("Область заднего прохода",priem.isSetT_obl_zad(), priem.getT_obl_zad());
						addLineToDetailInfo("Поясничная область",priem.isSetT_poyasn(), priem.getT_poyasn());
						addLineToDetailInfo("Почки",priem.isSetT_pochk(), priem.getT_pochk());
						addLineToDetailInfo("Мочевой пузырь",priem.isSetT_moch(), priem.getT_moch());
						addLineToDetailInfo("Молочные железы",priem.isSetT_mol_jel(), priem.getT_mol_jel());
						addLineToDetailInfo("Грудные железы мужчин",priem.isSetT_gr_jel(), priem.getT_gr_jel());
						addLineToDetailInfo("Матка и ее придатки",priem.isSetT_matka(), priem.getT_matka());
						addLineToDetailInfo("Наружные половые органы у мужчин",priem.isSetT_nar_polov(), priem.getT_nar_polov());
						addLineToDetailInfo("Щитовидная железа",priem.isSetT_chitov(), priem.getT_chitov());
						//addHeader("Status localis");
						addLineToDetailInfo("Status localis",priem.isSetT_st_localis(), priem.getT_st_localis());
						//addHeader("Оценка данных анамнеза и объективного исследования");
						addLineToDetailInfo("Оценка данных анамнеза и объективного исследования",priem.isSetT_ocenka(), priem.getT_ocenka());
						addLineToDetailInfo("Результат", getValueFromClassifier(MainForm.tcl.getAp0(), pam.isSetRezult(), pam.getRezult()));
						eptxt.setText(sb.toString());
					} catch (PriemNotFoundException e1) {
						e1.printStackTrace();
					}
		 			
		 		}
		 			}
		 			catch (KmiacServerException e1) {
						e1.printStackTrace();
					} catch (TException e1) {
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
		DefaultTreeCellRenderer renderer = (DefaultTreeCellRenderer) treeinfo.getCellRenderer();
		renderer.setLeafIcon(null);
		renderer.setClosedIcon(null);
		renderer.setOpenIcon(null);
		
		
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				update(npasp);
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
		this.npasp = npasp;
		
		treeinfo.setModel(new DefaultTreeModel(createNodes()));
		
		setVisible(true);
	}
	
	private DefaultMutableTreeNode createNodes() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Корень");
		DefaultMutableTreeNode patinfo = new DefaultMutableTreeNode("Личная информация");
		DefaultMutableTreeNode signinfo = new DefaultMutableTreeNode("Анамнез жизни");
		DefaultMutableTreeNode posinfo = new DefaultMutableTreeNode("Случаи заболевания");
		DefaultMutableTreeNode diaginfo = new DefaultMutableTreeNode("Диагнозы");
		DefaultMutableTreeNode berinfo = new DefaultMutableTreeNode("Случаи беременности");
		root.add(patinfo);
		root.add(signinfo);
		root.add(posinfo);
		root.add(diaginfo);
		root.add(berinfo);
		
		try {
			for (Pvizit pvizit : MainForm.tcl.getPvizitInfo(npasp, tfdatn.getDate().getTime(), tfDatk.getDate().getTime()))
					posinfo.add(new PvizitTreeNode(pvizit));
			for (PdiagZ pdiag : MainForm.tcl.getPdiagzProsm(npasp))
				diaginfo.add(new PdiagTreeNode(pdiag));

		} catch (KmiacServerException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
			MainForm.conMan.reconnect(e);
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
		private static final long serialVersionUID = -5215124870459111226L;
		private PvizitAmb pam;
		
		public PvizitAmbNode(PvizitAmb pam) {
			this.pam = pam;
		}
		
		@Override
		public String toString() {
			return DateFormat.getDateInstance().format(new Date(pam.getDatap()));
		}
	}
	
	class PdiagTreeNode extends DefaultMutableTreeNode {
		private static final long serialVersionUID = -46003968655861926L;
		private PdiagZ pdiag;
		
		public PdiagTreeNode(PdiagZ pdiag) {
			this.pdiag = pdiag;
			
		}
		
		@Override
		public String toString() {
			return pdiag.getDiag();
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
	
	private void addLineIf(String txt,boolean isSet, int value) {
		if (isSet)
			if (value == 1)
				if (txt.toString().length() > 0)
					sb.append(txt+lineSep);
	}
	
	private void addLine(String txt) {
		sb.append(txt);
	}
}
