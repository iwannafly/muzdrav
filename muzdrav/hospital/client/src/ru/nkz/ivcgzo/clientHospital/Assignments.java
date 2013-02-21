package ru.nkz.ivcgzo.clientHospital;

//TODO: ВЫТАЩИТЬ СТРУКТУРУ Patient ИЗ Thrift-ФАЙЛОВ
//TODO: РАЗОБРАТЬСЯ СО СТРУКТУРОЙ Lek ИЗ Thrift-ФАЙЛОВ
import java.awt.Color;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftHospital.TPatient;

/**
 * Панель учёта медицинских назначений: препаратов, 
 * исследований, режимов и лечебных процедур
 * @author Балабаев Никита Дмитриевич
 */
public final class Assignments extends JPanel {

	private static final long serialVersionUID = 3513837719265529747L;
	private UserAuthInfo userAuth = null;
    private TPatient patient = null;
    private JPanel pnlMedications, pnlResearch, pnlDiet, pnlProcedures;
    private CustomTable<Lek, Lek._Fields> tblMedications;

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
		//TODO:
		this.pnlMedications = new JPanel();
		this.pnlMedications.setBorder(
				new TitledBorder(
						new LineBorder(new Color(0, 0, 0), 1, true),
						"Лекарственные назначения", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		this.pnlResearch = new JPanel();
		this.pnlResearch.setBorder(
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
		this.add(this.pnlResearch);
		this.add(this.pnlDiet);
		this.add(this.pnlProcedures);
	}
}
