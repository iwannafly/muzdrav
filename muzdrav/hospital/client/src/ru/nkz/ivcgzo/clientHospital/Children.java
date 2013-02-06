package ru.nkz.ivcgzo.clientHospital;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTimeEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftHospital.ChildDocNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.TPatient;
import ru.nkz.ivcgzo.thriftHospital.TRd_Novor;
import ru.nkz.ivcgzo.thriftHospital.TRd_Svid_Rojd;

//TODO: ЮНИТ-ТЕСТИРОВАНИЕ
//TODO: СОВМЕСТИМОСТЬ С LibreOffice
//TODO: НУЖЕН ЛИ toUpperCase В СВИДЕТЕЛЬСТВЕ О РОЖДЕНИИ
/**
 * Панель ввода\редактирования\отображения информации о новорождённом
 * @author Балабаев Никита Дмитриевич
 */
public class Children extends JPanel {
	
	private static final long serialVersionUID = 3513837719265529746L;
	private UserAuthInfo userAuth = null;
    private TPatient patient = null;
    private TRd_Novor childInfo = null;
    private TRd_Svid_Rojd childDoc = null;
    private JPanel panelChildEdit, panelDoc;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> ticcbBirthPlace;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> ticcbMotherWork;
    private JButton btnSaveChild, btnGiveDoc, btnPrintBlank, btnFillDoc;
    private JComboBox<String> cbBirthHappen;
    private CustomTimeEditor cteBirthTime;
    private JTextField tfDocName;
    private JSpinner spinnerDocNum;
    private JSpinner spinnerHeight;
    private JSpinner spinnerWeight;
    private JSpinner spinnerApgar1;
    private JSpinner spinnerApgar5;
    private JSpinner spinnerChildNumGlobal;
    private JSpinner spinnerChildNumLocal;
    private JCheckBox chckBxDead, chckBxFull;
    private JCheckBox chckBxCriteria1;
    private JCheckBox chckBxCriteria2;
    private JCheckBox chckBxCriteria4;
    private JCheckBox chckBxCriteria3;
    private JLabel lblBirthHappen;
    private JLabel lblDocStatus;

	/**
	 * Создание экземпляра панели отображения информации новорождённого
	 * @param authInfo Информация о вошедшем в систему пользователе
	 * @param patientInfo Информация о пациенте
	 */
	public Children(final UserAuthInfo authInfo, final TPatient patientInfo) {
		this.userAuth = authInfo;
		this.patient = patientInfo;
		//Инициализация интерфейса:
		this.setInterface();
		this.setDefaultChildValues();
		this.setDefaultDocValues();
		this.updatePanel();	//Обновление панели
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
	 * Установка начальных значений элементов панели информации о новорождённом
	 */
	private void setDefaultChildValues() {
		this.cteBirthTime.setText(null);
		this.chckBxDead.setSelected(false);
		this.chckBxFull.setSelected(true);
		this.chckBxCriteria1.setSelected(true);
		this.chckBxCriteria1.setSelected(true);
		this.chckBxCriteria2.setSelected(true);
		this.chckBxCriteria3.setSelected(true);
		this.chckBxCriteria4.setSelected(true);
		this.spinnerApgar1.setValue(0);
		this.spinnerApgar5.setValue(0);
		this.spinnerHeight.setValue(0);
		this.spinnerWeight.setValue(0);
		this.spinnerChildNumGlobal.setValue(0);
		this.spinnerChildNumLocal.setValue(0);
		this.chckBxDead();
	}
	
	/**
	 * Установка начальных значений элементов панели информации о свидетельстве
	 */
	private void setDefaultDocValues() {
		this.tfDocName.setText((this.patient != null) ? this.patient.getSurname() : "");
		this.spinnerDocNum.setValue(0);
		this.cbBirthHappen.setSelectedIndex(-1);
		this.ticcbMotherWork.setSelectedItem(null);
		try{
			this.ticcbBirthPlace.setSelectedPcod(42);	//По умолчанию выбран г. Новокузнецк
		} catch(Exception e) {
			this.ticcbBirthPlace.setSelectedItem(null);
		}
	}
	
	/**
	 * Загрузка показателей о новорождённом из интерфейса
	 * @return Возвращает <code>true</code>, если поля заполнены корректно;
	 * иначе - <code>false</code>
	 */
	private boolean loadChildInfoFromPanel() {
		if (this.childInfo == null)
			return false;
		//Установка значений полей childInfo:
		this.childInfo.setMert(this.chckBxDead.isSelected());
		this.childInfo.setDonosh(this.chckBxFull.isSelected());
		if (!this.chckBxDead.isSelected()) {	//Живорождённый:
			this.childInfo.setKrit1(this.chckBxCriteria1.isSelected());
			this.childInfo.setKrit2(this.chckBxCriteria2.isSelected());
			this.childInfo.setKrit3(this.chckBxCriteria3.isSelected());
			this.childInfo.setKrit4(this.chckBxCriteria4.isSelected());
		} else {								//Мертворождённый:
			this.childInfo.unsetKrit1();
			this.childInfo.unsetKrit2();
			this.childInfo.unsetKrit3();
			this.childInfo.unsetKrit4();
		}
		//В случае, если числовое значение равно нулю, то оно игнорируется:
		if ((int) this.spinnerApgar1.getValue() > 0)
			this.childInfo.setApgar1((int) this.spinnerApgar1.getValue());
		else
			this.childInfo.unsetApgar1();
		if ((int) this.spinnerApgar5.getValue() > 0)
			this.childInfo.setApgar5((int) this.spinnerApgar5.getValue());
		else
			this.childInfo.unsetApgar5();
		if ((int) this.spinnerHeight.getValue() > 0)
			this.childInfo.setRost((int) this.spinnerHeight.getValue());
		else
			this.childInfo.unsetRost();
		if ((int) this.spinnerWeight.getValue() > 0)
			this.childInfo.setMassa((int) this.spinnerWeight.getValue());
		else
			this.childInfo.unsetMassa();
		if ((int) this.spinnerChildNumGlobal.getValue() > 0)
			this.childInfo.setKolchild((int) this.spinnerChildNumGlobal.getValue());
		else
			this.childInfo.unsetKolchild();
		//Если номер новорождённого в многоплодных родах равен 0, то
		//роды считаются одноплодными (параметр не игнорируется):
		this.childInfo.setNreb((int) this.spinnerChildNumLocal.getValue());
		//Время рождения:
		final String childBirthTime = this.cteBirthTime.getText();
		if (!childBirthTime.equals("__:__"))
			this.childInfo.setTimeon(childBirthTime);
		else	//Время рождения не установлено
			this.childInfo.unsetTimeon();
		return true;
	}
	
	/**
	 * Загрузка информации о свидетельстве из интерфейса
	 * @return Возвращает <code>true</code>, если поля заполнены корректно;
	 * иначе - <code>false</code>
	 */
	private boolean loadChildDocFromPanel() {
		if (this.childDoc == null)
			return false;
		String childName = this.tfDocName.getText().trim().toUpperCase();
		if (!childName.matches("^[А-Я]{1,20}$")) {
			JOptionPane.showMessageDialog(this,
					"Поле 'Фамилия новорождённого' может состоять " +
					"только из русскоязычных букв и не может быть пустым или " +
					"превышать длину в 20 символов", "Ошибка", JOptionPane.WARNING_MESSAGE);
			return false;	
		}
		if (this.ticcbBirthPlace.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this,
					"Поле 'Место рождения' не может быть пустым",
					"Ошибка", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (this.cbBirthHappen.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this,
					"Поле 'Роды произошли' не может быть пустым",
					"Ошибка", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (this.ticcbMotherWork.getSelectedItem() == null) {
			JOptionPane.showMessageDialog(this,
					"Поле 'Занятость матери' не может быть пустым",
					"Ошибка", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		//Установка значений полей childDoc:
		this.childDoc.setFamreb(childName);
		this.childDoc.setM_rojd(this.ticcbBirthPlace.getSelectedPcod());
		this.childDoc.setR_proiz(this.cbBirthHappen.getSelectedIndex() + 1);
		this.childDoc.setZan(this.ticcbMotherWork.getSelectedPcod());
		this.childDoc.setSvid_write(this.userAuth.getPcod());
		this.childDoc.setCdol_write(this.userAuth.getCdol());
		this.childDoc.setClpu(this.userAuth.getClpu());
		//Загрузка фамилии ребёнка в интерфейс в верхнем регистре:
		this.tfDocName.setText(childName);
		return true;
	}
	
	/**
	 * Загрузка показателей о новорождённом в интерфейс
	 */
	private void loadChildInfoIntoPanel() {
		this.setDefaultChildValues();
		if (this.childInfo == null)
			return;
		if (this.childInfo.isSetTimeon())
			this.cteBirthTime.setText(this.childInfo.getTimeon());
		if (this.childInfo.isSetMert())
			this.chckBxDead.setSelected(this.childInfo.isMert());
		if (this.childInfo.isSetDonosh())
			this.chckBxFull.setSelected(this.childInfo.isDonosh());
		if (this.childInfo.isSetKrit1())
			this.chckBxCriteria1.setSelected(this.childInfo.isKrit1());
		if (this.childInfo.isSetKrit2())
			this.chckBxCriteria2.setSelected(this.childInfo.isKrit2());
		if (this.childInfo.isSetKrit3())
			this.chckBxCriteria3.setSelected(this.childInfo.isKrit3());
		if (this.childInfo.isSetKrit4())
			this.chckBxCriteria4.setSelected(this.childInfo.isKrit4());
		if (this.childInfo.isSetApgar1())
			this.spinnerApgar1.setValue(this.childInfo.getApgar1());
		if (this.childInfo.isSetApgar5())
			this.spinnerApgar5.setValue(this.childInfo.getApgar5());
		if (this.childInfo.isSetRost())
			this.spinnerHeight.setValue(this.childInfo.getRost());
		if (this.childInfo.isSetMassa())
			this.spinnerWeight.setValue(this.childInfo.getMassa());
		if (this.childInfo.isSetKolchild())
			this.spinnerChildNumGlobal.setValue(this.childInfo.getKolchild());
		if (this.childInfo.isSetNreb())
			this.spinnerChildNumLocal.setValue(this.childInfo.getNreb());
		this.chckBxDead();
	}
	
	/**
	 * Загрузка информации о свидетельстве в интерфейс
	 */
	private void loadChildDocIntoPanel() {
		if (this.childDoc == null) {
			this.setDefaultDocValues();
			return;
		}
		this.tfDocName.setText(this.childDoc.getFamreb());
		this.cbBirthHappen.setSelectedIndex(this.childDoc.getR_proiz() - 1);
		this.ticcbMotherWork.setSelectedPcod(this.childDoc.getZan());
		this.ticcbBirthPlace.setSelectedPcod(this.childDoc.getM_rojd());
		this.spinnerDocNum.setValue(this.childDoc.getNdoc());
		this.UpdateDocStatus();
	}

	/**
	 * Обновление информации о новорождённом
	 * @throws TException исключение общего вида
	 * @throws PatientNotFoundException пациент не найден
	 * @throws KmiacServerException исключение на стороне сервера
	 */
	private void updateChildInfo()
			throws KmiacServerException, PatientNotFoundException, TException {
		if (!this.loadChildInfoFromPanel())	//Не все данные введены
			return;
		//Вызов функции сервера на обновление информации:
		ClientHospital.tcl.updateChildInfo(this.childInfo);
	}

	/**
	 * Добавление информации о новорождённом
	 * @throws TException исключение общего вида
	 * @throws PatientNotFoundException пациент не найден
	 * @throws KmiacServerException исключение на стороне сервера
	 */
	private void addChildInfo()
			throws KmiacServerException, PatientNotFoundException, TException {
		this.childInfo = new TRd_Novor();
		if (!this.loadChildInfoFromPanel()) {	//Не все данные введены
			this.childInfo = null;
			return;
		}
		this.childInfo.setNpasp(this.patient.getPatientId());
		//Дата первой записи - текущая дата:
		this.childInfo.setDatazap(new Date().getTime());
		//Небольшие танцы с бубном:
		TRd_Novor tmpInfo = this.childInfo;
		//Поле childInfo останется null, если сервер вернёт исключение:
		this.childInfo = null;
		//Вызов функции сервера на добавление информации:
		ClientHospital.tcl.addChildInfo(tmpInfo);
		//Выполняется только в случае успешного добавления:
		this.childInfo = tmpInfo;
	}
	
	/**
	 * Проверка - выбран ли пациент (если нет, то уведомляет пользователя)
	 * @return Возвращает <code>true</code>, если пациент выбран;
	 * иначе - <code>false</code>
	 */
	private boolean checkPatient() {
		if (this.patient != null) {
			return true;
		} else {
			JOptionPane.showMessageDialog(this, "Пациент не выбран",
					"Предупреждение", JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
	
	/**
	 * Сохранение изменений о новорождённом
	 */
	private void btnSaveChildClick() {
		if (this.checkPatient()) {
			try {
				if (this.childInfo != null)
					this.updateChildInfo();	//Обновление информации о новорождённом
				else
					this.addChildInfo();	//Добавление информации о новорождённом
				return;
			} catch (KmiacServerException|PatientNotFoundException e) {
				e.printStackTrace();
			} catch (TException e) {
				e.printStackTrace();
			}
			JOptionPane.showMessageDialog(this, "Операция не была выполнена",
					"Ошибка", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Заполнение полей мед.свидетельства о рождении при выдаче:
	 */
	private void fillChildBirthGiveFields() {
		//Установка даты выдачи, кода выдающего специалиста и его должности:
		this.childDoc.setSvid_give(this.userAuth.getPcod());
		this.childDoc.setCdol_give(this.userAuth.getCdol());
		this.childDoc.setDateoff(new Date().getTime());
	}

	/**
	 * Обновление информации о мед.свидетельстве
	 * @throws TException исключение общего вида
	 * @throws ChildDocNotFoundException мед.свидетельство
	 * о рождении не найдено
	 * @throws KmiacServerException исключение на стороне сервера
	 * @return Возвращает <code>true</code>, если информация обновлена;
	 * иначе - <code>false</code>
	 */
	private boolean updateChildDocument(boolean isGive)
			throws KmiacServerException, ChildDocNotFoundException, TException {
		TRd_Svid_Rojd oldDoc = this.childDoc.deepCopy();
		if (!this.loadChildDocFromPanel())	//Не все данные введены
			return false;
		if (!oldDoc.equals(this.childDoc)) {	//Данные были изменены
			int answer = JOptionPane.showConfirmDialog(this,
					"Вы действительно хотите изменить данные свидетельства",
					"Подтверждение изменений", JOptionPane.YES_NO_OPTION);
			//Пользователь подтвердил изменение данных:
			if (answer == JOptionPane.YES_OPTION) {
				ClientHospital.tcl.updateChildDocument(this.childDoc);
			} else {	//Пользователь отменил изменение данных:
				this.childDoc = oldDoc;	//Откат изменений (элементы интерфейса не будут затронуты)
			}
		} else {	//Данные не изменены
			if (isGive && !this.childDoc.isSetSvid_give()) {
				this.fillChildBirthGiveFields();	//Процесс выдачи заполненного свидетельства
				ClientHospital.tcl.updateChildDocument(this.childDoc);
			} else {
				//Повторная печать уже выданного свидетельства
			}
		}
		return true;
	}

	/**
	 * Занесение нового мед.свидетельства в БД
	 * @param isGive Передать <code>true</code>, если свидетельство выдаётся;
	 * иначе - <code>false</code>
	 * @return Возвращает <code>true</code>, если свидетельство внесено;
	 * иначе - <code>false</code>
	 * @throws TException исключение общего вида
	 * @throws PatientNotFoundException пациент не найден
	 * @throws ChildDocNumAlreadyExistException такой номер 
	 * мед.свидетельства о рождении уже существует
	 * @throws KmiacServerException исключение на стороне сервера
	 */
	private boolean addChildDocument(boolean isGive)
			throws KmiacServerException, PatientNotFoundException, TException {
		this.childDoc = new TRd_Svid_Rojd();
		if (!this.loadChildDocFromPanel())	//Не все данные введены
		{
			this.childDoc = null;
			return false;
		}
		//Установка идентификатора новорождённого:
		this.childDoc.setNpasp(this.patient.getPatientId());
		if (isGive)
			this.fillChildBirthGiveFields();
		//Небольшие танцы с бубном:
		TRd_Svid_Rojd tmpDoc = this.childDoc;
		//Поле childDoc останется null, если сервер вернёт исключение:
		this.childDoc = null;
		//Вызов функции сервера на добавление информации о свидетельстве:
		final int ndoc = ClientHospital.tcl.addChildDocument(tmpDoc);
		//Выполняется только в случае успешного добавления:
		this.childDoc = tmpDoc;
		//Установка номера выданного свидетельства в поле свидетельства:
		this.childDoc.setNdoc(ndoc);
		//Загрузка номера выданного свидетельства в интерфейс:
		this.spinnerDocNum.setValue(ndoc);
		return true;
	}
	
	/**
	 * Печать мед.свидетельства о рождении
	 * @throws TException исключение общего вида
	 * @throws ChildDocNotFoundException мед.свидетельство
	 * о рождении не найдено
	 * @throws KmiacServerException исключение на стороне сервера
	 * @throws IOException ошибка создания документа
	 * @throws FileNotFoundException ошибка передачи документа с сервера
	 */
	private void printChildDocument()
			throws KmiacServerException, ChildDocNotFoundException,
			FileNotFoundException, IOException, TException {
		if ((this.childInfo != null) && (this.childDoc != null)) {
			String clientPath;
			final int ndoc = this.childDoc.getNdoc();
			clientPath = File.createTempFile("svid_rojd_", ".htm").getAbsolutePath();
            ClientHospital.conMan.transferFileFromServer(
            		ClientHospital.tcl.printChildBirthDocument(ndoc),
            		clientPath);
            ClientHospital.conMan.openFileInEditor(clientPath, false);
		} else
			JOptionPane.showMessageDialog(this, "Информация о новорождённом " +
					"отсутствует или свидетельство не найдено",
					"Ошибка", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Нажатие на кнопку заполнения мед.свидетельства о рождении
	 * @param isGive Передать <code>true</code>, если свидетельство выдаётся;
	 * иначе - <code>false</code>
	 * @return Возвращает <code>true</code>, если свидетельство заполнено;
	 * иначе - <code>false</code>
	 */
	private boolean btnFillChildBirthDoc(boolean isGive) {
		if (!this.checkPatient())
			return false;
		if (this.childInfo == null)
		{
			JOptionPane.showMessageDialog(this, "Информация о новорождённом " +
					"отсутствует", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		boolean isOk = false;
		try {
			if (this.childDoc != null) {	//Обновление информации о мед.свидетельстве
				isOk = this.updateChildDocument(isGive);
			} else {						//Добавление информации о мед.свидетельстве
				isOk = this.addChildDocument(isGive);
			}
		} catch (PatientNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Информация о новорождённом " +
					"отсутствует", "Ошибка", JOptionPane.ERROR_MESSAGE);
			this.childInfo = null;
		} catch (ChildDocNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Свидетельство не найдено",
					"Ошибка", JOptionPane.ERROR_MESSAGE);
			this.childDoc = null;
		} catch (TException e) {		//Поглощает KmiacServerException
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Операция не была выполнена",
					"Ошибка", JOptionPane.ERROR_MESSAGE);
		}
		this.UpdateDocStatus();
		return isOk;
	}
	
	/**
	 * Нажатие на кнопку выдачи мед.свидетельства о рождении
	 */
	private void btnGiveDocClick() {
		if (this.btnFillChildBirthDoc(true)) {
			try{
				this.printChildDocument();	//Печать мед.свидетельства
			} catch (IOException e) {	//Поглощает FileNotFoundException
				JOptionPane.showMessageDialog(this, "Сбой во время печати " +
						"мед.свидетельства о рождении",
						"Ошибка", JOptionPane.ERROR_MESSAGE);
				return;
			} catch (TException e) {	//Поглощает KmiacServerException
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Сбой во время печати " +
						"мед.свидетельства о рождении",
						"Ошибка", JOptionPane.ERROR_MESSAGE);
			}
			this.UpdateDocStatus();
		}
	}
	
	/**
	 * Нажатие на кнопку печати бланка мед.свидетельства о рождении
	 */
	private void btnPrintBlankDocClick() {
		String clientPath;
		try {
			clientPath = File.createTempFile("svid_blank_", ".htm").getAbsolutePath();
	        ClientHospital.conMan.transferFileFromServer(
	        		ClientHospital.tcl.printChildBirthBlankDocument(),
	        		clientPath);
	        ClientHospital.conMan.openFileInEditor(clientPath, true);
			return;
		} catch (KmiacServerException e) {
			e.printStackTrace();
		} catch (IOException e) {	//Поглощает FileNotFoundException
			JOptionPane.showMessageDialog(this, "Ошибка при создания бланка",
					"Ошибка", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (TException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(this, "Операция не была выполнена",
				"Ошибка", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Событие, вызываемое при изменении состояния чекбокса "Мертворождённый"
	 */
	private void chckBxDead() {
		final boolean isLiveChild = !this.chckBxDead.isSelected();
		this.chckBxCriteria1.setEnabled(isLiveChild);
		this.chckBxCriteria2.setEnabled(isLiveChild);
		this.chckBxCriteria3.setEnabled(isLiveChild);
		this.chckBxCriteria4.setEnabled(isLiveChild);
	}
	
	/**
	 * Процедура обновления статуса мед.свидетельства о рождении
	 */
	private void UpdateDocStatus() {
		if (this.childDoc == null)
			this.lblDocStatus.setText("Не заполнено");
		else
			if (this.childDoc.isSetSvid_give() &&
				this.childDoc.isSetCdol_give() &&
				this.childDoc.isSetDateoff())
				this.lblDocStatus.setText("Выдано");
			else
				this.lblDocStatus.setText("Заполнено");
	}
	
	/**
	 * Обновление пользователського интерфейса панели
	 */
	private void updatePanel() {
		if (this.patient != null)
		{
			final int childId = this.patient.getPatientId();
			this.childInfo = null;
			this.childDoc = null;
			try {
				this.childInfo = ClientHospital.tcl.getChildInfo(childId);
				this.loadChildInfoIntoPanel();
				this.childDoc = ClientHospital.tcl.getChildDocument(childId);
				this.loadChildDocIntoPanel();
			} catch (KmiacServerException kse) {
				kse.printStackTrace();
			} catch (PatientNotFoundException e) {
				//Новорождённый не найден, его нужно добавить в таблицу:
				this.setDefaultChildValues();
				this.setDefaultDocValues();
			} catch (ChildDocNotFoundException e) {
				//Свидетельство ещё не было выдано:
				this.setDefaultDocValues();
			} catch (TException e) {
				e.printStackTrace();
			}
		}
		this.UpdateDocStatus();
	}
	
	/**
	 * Инициализация пользовательского интерфейса панели
	 */
	private void setInterface() {
		this.panelChildEdit = new JPanel();
		this.panelChildEdit.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "\u0418\u043D\u0444\u043E\u0440\u043C\u0430\u0446\u0438\u044F \u043E \u043D\u043E\u0432\u043E\u0440\u043E\u0436\u0434\u0451\u043D\u043D\u043E\u043C", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		this.panelDoc = new JPanel();
		this.panelDoc.setBorder(new TitledBorder(new LineBorder(new Color(0, 0, 0), 1, true), "\u041C\u0435\u0434\u0438\u0446\u0438\u043D\u0441\u043A\u043E\u0435 \u0441\u0432\u0438\u0434\u0435\u0442\u0435\u043B\u044C\u0441\u0442\u0432\u043E \u043E \u0440\u043E\u0436\u0434\u0435\u043D\u0438\u0438", TitledBorder.LEFT, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(this.panelDoc, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(this.panelChildEdit, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 820, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(this.panelChildEdit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(this.panelDoc, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel lblDocNum = new JLabel("Номер свидетельства:");
		lblDocNum.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		this.spinnerDocNum = new JSpinner();
		this.spinnerDocNum.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		this.spinnerDocNum.setEnabled(false);
		lblDocNum.setLabelFor(this.spinnerDocNum);
		
		JLabel lblDocName = new JLabel("Фамилия новорождённого:");
		lblDocName.setFont(new Font("Tahoma", Font.PLAIN, 11));
		
		this.tfDocName = new JTextField();
		this.tfDocName.setHorizontalAlignment(SwingConstants.CENTER);
		lblDocName.setLabelFor(this.tfDocName);
		this.tfDocName.setColumns(20);
		
		this.btnGiveDoc = new JButton("Выдать свидетельство");
		this.btnGiveDoc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnGiveDocClick();
			}
		});
		
		JLabel lblBirthPlace = new JLabel("Место рождения:");
		
		JLabel lblMotherWork = new JLabel("Занятость матери:");
		
		this.ticcbBirthPlace = new ThriftIntegerClassifierCombobox<IntegerClassifier>(IntegerClassifiers.n_l00);
		lblBirthPlace.setLabelFor(this.ticcbBirthPlace);
		
		this.ticcbMotherWork = new ThriftIntegerClassifierCombobox<IntegerClassifier>(IntegerClassifiers.n_z42);
		lblMotherWork.setLabelFor(this.ticcbMotherWork);
		
		this.lblBirthHappen = new JLabel("Роды произошли:");
		
		this.cbBirthHappen = new JComboBox<String>(
				new DefaultComboBoxModel<String> (
						new String[] {"в стационаре", "дома", "в другом месте", "неизвестно"}));

		this.lblBirthHappen.setLabelFor(this.cbBirthHappen);
		
		this.btnPrintBlank = new JButton("Распечатать бланк");
		this.btnPrintBlank.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnPrintBlankDocClick();
			}
		});
		
		JLabel lblDocStatusText = new JLabel("Статус:");
		lblDocStatusText.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		this.lblDocStatus = new JLabel("Не заполнено");
		lblDocStatusText.setLabelFor(this.lblDocStatus);
		
		this.btnFillDoc = new JButton("Заполнить свидетельство");
		this.btnFillDoc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				btnFillChildBirthDoc(false);
			}
		});
		GroupLayout gl_panelDoc = new GroupLayout(this.panelDoc);
		gl_panelDoc.setHorizontalGroup(
			gl_panelDoc.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelDoc.createSequentialGroup()
					.addGroup(gl_panelDoc.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panelDoc.createSequentialGroup()
							.addContainerGap()
							.addComponent(this.btnPrintBlank, GroupLayout.PREFERRED_SIZE, 131, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelDoc.createSequentialGroup()
							.addGap(134)
							.addGroup(gl_panelDoc.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelDoc.createSequentialGroup()
									.addComponent(lblDocStatusText, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(this.lblDocStatus)
									.addPreferredGap(ComponentPlacement.RELATED, 218, Short.MAX_VALUE)
									.addComponent(lblDocNum)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(this.spinnerDocNum, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panelDoc.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addGroup(gl_panelDoc.createParallelGroup(Alignment.LEADING)
										.addGroup(gl_panelDoc.createSequentialGroup()
											.addComponent(lblMotherWork)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(this.ticcbMotherWork, 0, 457, Short.MAX_VALUE))
										.addGroup(gl_panelDoc.createSequentialGroup()
											.addComponent(lblBirthPlace)
											.addPreferredGap(ComponentPlacement.UNRELATED)
											.addComponent(this.ticcbBirthPlace, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
											.addComponent(this.lblBirthHappen, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(this.cbBirthHappen, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE))
										.addGroup(gl_panelDoc.createSequentialGroup()
											.addComponent(lblDocName)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(this.tfDocName, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE))
										.addGroup(gl_panelDoc.createSequentialGroup()
											.addComponent(this.btnGiveDoc)
											.addGap(44)
											.addComponent(this.btnFillDoc)))))))
					.addGap(119))
		);
		gl_panelDoc.setVerticalGroup(
			gl_panelDoc.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDoc.createSequentialGroup()
					.addGap(17)
					.addGroup(gl_panelDoc.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelDoc.createParallelGroup(Alignment.BASELINE)
							.addComponent(this.spinnerDocNum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblDocNum))
						.addComponent(lblDocStatusText)
						.addComponent(this.lblDocStatus))
					.addGap(22)
					.addGroup(gl_panelDoc.createParallelGroup(Alignment.BASELINE)
						.addComponent(this.tfDocName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDocName))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelDoc.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelDoc.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblBirthPlace)
							.addComponent(this.ticcbBirthPlace, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelDoc.createSequentialGroup()
							.addGap(3)
							.addGroup(gl_panelDoc.createParallelGroup(Alignment.BASELINE)
								.addComponent(this.cbBirthHappen, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(this.lblBirthHappen))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelDoc.createParallelGroup(Alignment.BASELINE)
						.addComponent(this.ticcbMotherWork, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMotherWork))
					.addPreferredGap(ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
					.addGroup(gl_panelDoc.createParallelGroup(Alignment.LEADING, false)
						.addComponent(this.btnFillDoc, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(this.btnPrintBlank, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE)
						.addComponent(this.btnGiveDoc, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
					.addContainerGap())
		);
		this.panelDoc.setLayout(gl_panelDoc);
		
		this.chckBxDead = new JCheckBox("Мертворождённый");
		this.chckBxDead.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				chckBxDead();
			}
		});
		this.chckBxFull = new JCheckBox("Доношенный");
		JSeparator separatorCB = new JSeparator();
		
		this.spinnerWeight = new JSpinner();
		this.spinnerWeight.setToolTipText("При значении 0 поле игнорируется");
		this.spinnerWeight.setModel(new SpinnerNumberModel(0, 0, 9999, 1));
		
		JLabel lblWeight = new JLabel("Вес при рождении (г):");
		lblWeight.setToolTipText("При значении 0 поле игнорируется");
		lblWeight.setLabelFor(this.spinnerWeight);
		
		JLabel lblHeight = new JLabel("Рост при рождении (см):");
		lblHeight.setToolTipText("При значении 0 поле игнорируется");
		
		JPanel panelApgar = new JPanel();
		panelApgar.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JLabel lblApgar = new JLabel("Оценка по шкале Апгар:");
		lblApgar.setToolTipText("При значении 0 поле игнорируется");
		panelApgar.add(lblApgar);
		
		JPanel panelChildNumber = new JPanel();
		panelChildNumber.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JLabel lblChildNumGlobal = new JLabel("По счёту новорождённый у матери:");
		lblChildNumGlobal.setToolTipText("При значении 0 поле игнорируется");
		lblChildNumGlobal.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblBirthTime = new JLabel("Время рождения:");
		
		JPanel panelCriteria = new JPanel();
		panelCriteria.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		this.cteBirthTime = new CustomTimeEditor();
		this.cteBirthTime.setText("__:__ ");
		lblBirthTime.setLabelFor(this.cteBirthTime);
		
		this.spinnerHeight = new JSpinner();
		this.spinnerHeight.setToolTipText("При значении 0 поле игнорируется");
		this.spinnerHeight.setModel(new SpinnerNumberModel(0, 0, 99, 1));
		lblHeight.setLabelFor(this.spinnerHeight);
		
		this.btnSaveChild = new JButton("");
		this.btnSaveChild.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnSaveChildClick();
			}
		});
		this.btnSaveChild.setIcon(new ImageIcon(Children.class.getResource("/ru/nkz/ivcgzo/clientHospital/resources/1341981970_Accept.png")));
		this.btnSaveChild.setToolTipText("Занести информацию");
		
		GroupLayout gl_panelChildEdit = new GroupLayout(this.panelChildEdit);
		gl_panelChildEdit.setHorizontalGroup(
			gl_panelChildEdit.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelChildEdit.createSequentialGroup()
					.addGroup(gl_panelChildEdit.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelChildEdit.createSequentialGroup()
							.addGap(61)
							.addComponent(separatorCB, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelChildEdit.createSequentialGroup()
							.addGap(35)
							.addComponent(this.btnSaveChild)))
					.addGap(45)
					.addGroup(gl_panelChildEdit.createParallelGroup(Alignment.LEADING)
						.addComponent(panelCriteria, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
						.addComponent(panelChildNumber, GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
						.addComponent(panelApgar, GroupLayout.DEFAULT_SIZE, 553, Short.MAX_VALUE)
						.addGroup(gl_panelChildEdit.createSequentialGroup()
							.addGroup(gl_panelChildEdit.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_panelChildEdit.createSequentialGroup()
									.addComponent(lblBirthTime)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(this.cteBirthTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblHeight))
							.addGroup(gl_panelChildEdit.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panelChildEdit.createSequentialGroup()
									.addGap(4)
									.addComponent(this.spinnerHeight, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 195, Short.MAX_VALUE))
								.addGroup(gl_panelChildEdit.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(this.chckBxDead, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
									.addGap(13)))
							.addGroup(gl_panelChildEdit.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panelChildEdit.createSequentialGroup()
									.addComponent(lblWeight)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(this.spinnerWeight, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
								.addComponent(this.chckBxFull))))
					.addGap(120))
		);
		gl_panelChildEdit.setVerticalGroup(
			gl_panelChildEdit.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelChildEdit.createSequentialGroup()
					.addGroup(gl_panelChildEdit.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelChildEdit.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panelChildEdit.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panelChildEdit.createParallelGroup(Alignment.BASELINE)
									.addComponent(this.chckBxFull)
									.addComponent(this.chckBxDead))
								.addGroup(gl_panelChildEdit.createParallelGroup(Alignment.BASELINE)
									.addComponent(lblBirthTime)
									.addComponent(this.cteBirthTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(18)
							.addGroup(gl_panelChildEdit.createParallelGroup(Alignment.BASELINE)
								.addComponent(this.spinnerWeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblWeight)
								.addComponent(lblHeight)
								.addComponent(this.spinnerHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panelChildEdit.createSequentialGroup()
							.addGap(31)
							.addComponent(separatorCB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(gl_panelChildEdit.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelChildEdit.createSequentialGroup()
							.addGap(18)
							.addComponent(panelApgar, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(panelChildNumber, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(panelCriteria, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panelChildEdit.createSequentialGroup()
							.addGap(48)
							.addComponent(this.btnSaveChild, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(17, Short.MAX_VALUE))
		);
		
		JLabel lblCriteria = new JLabel("Критерии живорождения:");
		this.chckBxCriteria1 = new JCheckBox("дыхание");
		this.chckBxCriteria2 = new JCheckBox("сердцебиение");
		this.chckBxCriteria3 = new JCheckBox("мышечная деятельность");
		this.chckBxCriteria4 = new JCheckBox("пульсация пуповины");
		GroupLayout gl_panelCriteria = new GroupLayout(panelCriteria);
		gl_panelCriteria.setHorizontalGroup(
			gl_panelCriteria.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCriteria.createSequentialGroup()
					.addGap(5)
					.addComponent(lblCriteria)
					.addGap(5)
					.addGroup(gl_panelCriteria.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelCriteria.createSequentialGroup()
							.addComponent(this.chckBxCriteria1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(26))
						.addComponent(this.chckBxCriteria2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(8)
					.addGroup(gl_panelCriteria.createParallelGroup(Alignment.LEADING)
						.addComponent(this.chckBxCriteria3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_panelCriteria.createSequentialGroup()
							.addComponent(this.chckBxCriteria4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(22)))
					.addGap(23))
		);
		gl_panelCriteria.setVerticalGroup(
			gl_panelCriteria.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCriteria.createSequentialGroup()
					.addGroup(gl_panelCriteria.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelCriteria.createSequentialGroup()
							.addGap(9)
							.addComponent(lblCriteria))
						.addGroup(gl_panelCriteria.createSequentialGroup()
							.addGap(5)
							.addGroup(gl_panelCriteria.createParallelGroup(Alignment.BASELINE)
								.addComponent(this.chckBxCriteria1)
								.addComponent(this.chckBxCriteria4))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelCriteria.createParallelGroup(Alignment.BASELINE)
								.addComponent(this.chckBxCriteria2)
								.addComponent(this.chckBxCriteria3))))
					.addContainerGap())
		);
		panelCriteria.setLayout(gl_panelCriteria);
		
		this.spinnerChildNumGlobal = new JSpinner();
		this.spinnerChildNumGlobal.setToolTipText("При значении 0 поле игнорируется");
		lblChildNumGlobal.setLabelFor(this.spinnerChildNumGlobal);
		spinnerChildNumGlobal.setModel(new SpinnerNumberModel(1, 0, 999, 1));
		
		JLabel lblChildNumLocal = new JLabel("Номер новорождённого в многоплодных родах:");
		lblChildNumLocal.setToolTipText("При одноплодных родах следует указать 0");
		lblChildNumLocal.setHorizontalAlignment(SwingConstants.CENTER);
		
		this.spinnerChildNumLocal = new JSpinner();
		this.spinnerChildNumLocal.setToolTipText("При одноплодных родах следует указать 0");
		lblChildNumLocal.setLabelFor(this.spinnerChildNumLocal);
		this.spinnerChildNumLocal.setModel(new SpinnerNumberModel(1, 0, 9, 1));
		GroupLayout gl_panelChildNumber = new GroupLayout(panelChildNumber);
		gl_panelChildNumber.setHorizontalGroup(
			gl_panelChildNumber.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelChildNumber.createSequentialGroup()
					.addGap(101)
					.addComponent(lblChildNumGlobal, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(spinnerChildNumGlobal, GroupLayout.PREFERRED_SIZE, 43, GroupLayout.PREFERRED_SIZE)
					.addGap(90))
				.addGroup(gl_panelChildNumber.createSequentialGroup()
					.addGap(71)
					.addComponent(lblChildNumLocal, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
					.addGap(5)
					.addComponent(spinnerChildNumLocal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(71))
		);
		gl_panelChildNumber.setVerticalGroup(
			gl_panelChildNumber.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelChildNumber.createSequentialGroup()
					.addGroup(gl_panelChildNumber.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelChildNumber.createSequentialGroup()
							.addGap(8)
							.addComponent(lblChildNumGlobal))
						.addGroup(gl_panelChildNumber.createSequentialGroup()
							.addGap(5)
							.addComponent(spinnerChildNumGlobal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelChildNumber.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelChildNumber.createSequentialGroup()
							.addGap(3)
							.addComponent(lblChildNumLocal))
						.addComponent(spinnerChildNumLocal, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
		);
		panelChildNumber.setLayout(gl_panelChildNumber);
		
		JLabel lblApgar1 = new JLabel("на 1 минуте");
		lblApgar1.setToolTipText("При значении 0 поле игнорируется");
		panelApgar.add(lblApgar1);
		
		this.spinnerApgar1 = new JSpinner();
		this.spinnerApgar1.setToolTipText("При значении 0 поле игнорируется");
		lblApgar1.setLabelFor(this.spinnerApgar1);
		this.spinnerApgar1.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		panelApgar.add(this.spinnerApgar1);
		
		JLabel lblApgar5 = new JLabel(";  через 5 минут");
		lblApgar5.setToolTipText("При значении 0 поле игнорируется");
		panelApgar.add(lblApgar5);
		
		this.spinnerApgar5 = new JSpinner();
		this.spinnerApgar5.setToolTipText("При значении 0 поле игнорируется");
		lblApgar5.setLabelFor(this.spinnerApgar5);
		this.spinnerApgar5.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		panelApgar.add(this.spinnerApgar5);
		
		this.panelChildEdit.setLayout(gl_panelChildEdit);
		setLayout(groupLayout);
	}
}
