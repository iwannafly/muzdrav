package ru.nkz.ivcgzo.clientHospital;

//TODO: РАЗОБРАТЬСЯ СО СТРУКТУРОЙ Lek ИЗ Thrift-ФАЙЛОВ
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftAssignments.*;
import ru.nkz.ivcgzo.thriftHospital.TPatient;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;

/**
 * Панель учёта медицинских назначений: препаратов, 
 * исследований, режимов и лечебных процедур
 * @author Балабаев Никита Дмитриевич
 */
public final class Assignments extends JPanel {

	private static final long serialVersionUID = 3513837719265529747L;
	private UserAuthInfo userAuth = null;
    private TPatient patient = null;
    private JPanel pnlMedications, pnlDiagnostics, pnlDiet, pnlProcedures;
    private JScrollPane spMedictaions, spDiagnostics, spDiet, spProcedures;
    private CustomTable<Medication, Medication._Fields> tblMedications;
    private CustomTable<Diagnostic, Diagnostic._Fields> tblDiagnostics;

	/**
	 * Создание экземпляра панели учёта медицинских назначений
	 * @param authInfo Информация о вошедшем в систему пользователе
	 * @param patientInfo Информация о пациенте
	 */
	public Assignments(final UserAuthInfo authInfo, final TPatient patientInfo) {
		this.userAuth = authInfo;
		this.patient = patientInfo;
		this.setInterface();	//Инициализация интерфейса
		this.updatePanel();		//Обновление панели
	}
	
	/**
	 * Смена пациента
	 * @param newPatient Информация о пациенте
	 */
	public void setPatient(final TPatient newPatient) {
		this.patient = newPatient;
		this.updatePanel();	//Обновление панели
	}

	/**
	 * Получение информации о пациенте
	 * @return Информация о пациенте
	 */
	public TPatient getPatient() {
		return (this.patient != null) ? this.patient.deepCopy() : null;
	}
	
	/**
	 * Смена пользователя
	 * @param userAuth Информация о вошедшем в систему пользователе
	 */
	public void setUserAuth(final UserAuthInfo userAuth) {
		this.userAuth = userAuth;
	}

	/**
	 * Получение информации о пользователе
	 * @return Информация о пользователе
	 */
	public UserAuthInfo getUserAuth() {
		return (this.userAuth != null) ? this.userAuth.deepCopy() : null;
	}
	
	/**
	 * Обновление пользователського интерфейса панели
	 */
	private void updatePanel() {
		if (this.patient != null) {
			//TODO:
		}
	}
	
	/**
	 * Инициализация пользовательского интерфейса панели
	 */
	private void setInterface() {
		this.pnlMedications = new JPanel();
		this.pnlMedications.setBorder(
				new TitledBorder(
						new LineBorder(new Color(0, 0, 0), 1, true),
						"Лекарственные назначения", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		this.pnlDiagnostics = new JPanel();
		this.pnlDiagnostics.setBorder(
				new TitledBorder(
						new LineBorder(new Color(0, 0, 0), 1, true),
						"Исследования", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		this.pnlDiet = new JPanel();
		this.pnlDiet.setBorder(
				new TitledBorder(
						new LineBorder(new Color(0, 0, 0), 1, true),
						"Режим и диета", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		this.pnlProcedures = new JPanel();
		this.pnlProcedures.setBorder(
				new TitledBorder(
						new LineBorder(new Color(0, 0, 0), 1, true),
						"Лечебные процедуры", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.add(this.pnlMedications);
		this.add(this.pnlDiagnostics);
		this.add(this.pnlDiet);
		this.add(this.pnlProcedures);
		this.setAllTables();
	}
	
	private void setAllTables() {
		this.setTableMedication();
		this.setTableDiagnostics();
	}
	
	private void setTableMedication() {
		this.spMedictaions = new JScrollPane();
		this.pnlMedications.add(this.spMedictaions);
		this.tblMedications = new CustomTable<Medication, Medication._Fields>(
				false, false, Medication.class, 6, "Наименование", 4, "Дата назначения",
				12, "Длительность", 14, "Дата отмены");
		this.spMedictaions.setViewportView(this.tblMedications);
		this.tblMedications.setDateField(1);
		this.tblMedications.setDateField(3);
	}
	
	private void setTableDiagnostics() {
		this.spDiagnostics = new JScrollPane();
		this.pnlDiagnostics.add(this.spDiagnostics);
		this.tblDiagnostics = new CustomTable<Diagnostic, Diagnostic._Fields>(
				false, false, Diagnostic.class, 3, "Наименование",
				7, "Дата назначения", 7, "Дата выполнения");
		this.spDiagnostics.setViewportView(this.tblDiagnostics);
		this.tblDiagnostics.setDateField(1);
		this.tblDiagnostics.setDateField(2);
	}
}
