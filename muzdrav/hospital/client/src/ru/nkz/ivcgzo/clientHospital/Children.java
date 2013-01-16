package ru.nkz.ivcgzo.clientHospital;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JSeparator;
import javax.swing.JCheckBox;
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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Date;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTimeEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftHospital.ChildDocNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.TPatient;
import ru.nkz.ivcgzo.thriftHospital.TRd_Novor;
import ru.nkz.ivcgzo.thriftHospital.TRd_Svid;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

/**
 * Панель ввода\редактирования\отображения информации о новорождённом
 * @author Балабаев Никита Дмитриевич
 */
public class Children extends JPanel {
	
	private static final long serialVersionUID = 3513837719265529746L;
	private UserAuthInfo userAuth = null;
    private TPatient patient = null;
    private TRd_Novor childInfo = null;
    private TRd_Svid childDoc = null;
    private JPanel panelChildEdit, panelDoc;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> ticcbDocGiven;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> ticcbChildBirth;
    private JButton btnSaveChild, btnGiveDoc;
    private JTextField tfDocName;
    private CustomDateEditor cdeDocDate;
    private JCheckBox chckBxDead, chckBxFull;
    private JSpinner spinnerDocNum;
    private JSpinner spinnerHeight;
    private JSpinner spinnerWeight;
    private JSpinner spinnerApgar1;
    private JSpinner spinnerApgar5;
    private JSpinner spinnerChildNumGlobal;
    private JSpinner spinnerChildNumLocal;
    private CustomTimeEditor cteBirthTime;
    private JLabel lblChildBirth;
    private JCheckBox chckBxCriteria1;
    private JCheckBox chckBxCriteria2;
    private JCheckBox chckBxCriteria4;
    private JCheckBox chckBxCriteria3;
    private JButton btnPrintBlankDoc;
    private JLabel lblDocStatus;
    private JComboBox<Object> cbDocType;

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
	}
	
	/**
	 * Смена пациента
	 * @param newPatient Информация о пациенте
	 */
	public void setPatient(final TPatient newPatient) {
		this.patient = newPatient;
		if (this.patient != null)
			try {
				this.ticcbChildBirth.setData(	//Загрузка списка родов
					ClientHospital.tcl.getChildBirths(this.patient.getBirthDate()));
				this.updatePanel();	//Обновление панели
			} catch (KmiacServerException e) {
				e.printStackTrace();
			} catch (TException e) {
				e.printStackTrace();
			}
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
	 * Установка списка врачей в элемент управления
	 * @param doctorsList Список врачей
	 */
	public void setDoctors(final List<IntegerClassifier> doctorsList) {
		this.ticcbDocGiven.setData(doctorsList);
	}
	
	/**
	 * Установка начальных значений элементов панели информации о новорождённом
	 */
	private void setDefaultChildValues() {
		this.ticcbChildBirth.setSelectedIndex(-1);
		this.cteBirthTime.setText("__:__");
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
		this.spinnerChildNumGlobal.setValue(1);
		this.spinnerChildNumLocal.setValue(1);
	}
	
	/**
	 * Установка начальных значений элементов панели информации о свидетельстве
	 */
	private void setDefaultDocValues() {
		this.cdeDocDate.setDate(new Date().getTime());
		this.tfDocName.setText("");
		this.ticcbDocGiven.setText(this.userAuth.getName());
		this.spinnerDocNum.setValue(0);
		this.lblDocStatus.setText("Не выдано");
		this.cbDocType.setSelectedIndex(0);
		this.cbDocType.setEnabled(true);
	}
	
	/**
	 * Загрузка показателей о новорождённом из интерфейса
	 * @return Возвращает <code>true</code>, если поля заполнены корректно;
	 * иначе - <code>false</code>
	 */
	private boolean loadChildInfoFromPanel() {
		if (this.childInfo == null)
			return false;
		if (this.ticcbChildBirth.getSelectedItem() == null)
		{
			JOptionPane.showMessageDialog(this, "Не выбраны роды",
					"Ошибка", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		//Установка значений полей childInfo:
		this.childInfo.setNrod(this.ticcbChildBirth.getSelectedPcod());
		this.childInfo.setMert(this.chckBxDead.isSelected());
		this.childInfo.setDonosh(this.chckBxFull.isSelected());
		this.childInfo.setApgar1((int) this.spinnerApgar1.getValue());
		this.childInfo.setApgar5((int) this.spinnerApgar5.getValue());
		this.childInfo.setRost((int) this.spinnerHeight.getValue());
		this.childInfo.setMassa((int) this.spinnerWeight.getValue());
		this.childInfo.setKolchild((int) this.spinnerChildNumGlobal.getValue());
		this.childInfo.setNreb((int) this.spinnerChildNumLocal.getValue());
		this.childInfo.setKrit1(this.chckBxCriteria1.isSelected());
		this.childInfo.setKrit2(this.chckBxCriteria2.isSelected());
		this.childInfo.setKrit3(this.chckBxCriteria3.isSelected());
		this.childInfo.setKrit4(this.chckBxCriteria4.isSelected());
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
		if (this.cdeDocDate.getDate() == null)
		{
			JOptionPane.showMessageDialog(this,
					"Поле 'Дата выдачи свидетельства' не может быть пустым",
					"Ошибка", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (this.tfDocName.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(this,
					"Поле 'Фамилия новорождённого' не может быть пустым",
					"Ошибка", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (this.tfDocName.getText().length() > 20)
		{
			JOptionPane.showMessageDialog(this,
					"Длина поля 'Фамилия новорождённого' не может превышать " +
					"20 символов", "Ошибка", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if (this.ticcbDocGiven.getSelectedItem() == null)
		{
			JOptionPane.showMessageDialog(this,
					"Поле 'Кем выдано' не может быть пустым",
					"Ошибка", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		//Установка значений полей childDoc:
		this.childDoc.setDateoff(this.cdeDocDate.getDate().getTime());
		this.childDoc.setFamreb(this.tfDocName.getText().toUpperCase());
		this.childDoc.setSvidvrach(this.ticcbDocGiven.getSelectedPcod());
		this.childDoc.setNdoc((int) this.spinnerDocNum.getValue());
		this.childDoc.setDoctype(this.cbDocType.getSelectedIndex() == 0);
		//Загрузка фамилии ребёнка в интерфейс в верхнем регистре:
		this.tfDocName.setText(this.childDoc.getFamreb());
		return true;
	}
	
	/**
	 * Загрузка показателей о новорождённом в интерфейс
	 */
	private void loadChildInfoIntoPanel() {
		if (this.childInfo == null)
		{
			this.setDefaultChildValues();
			return;
		}
		this.ticcbChildBirth.setSelectedPcod(this.childInfo.getNrod());
		this.cteBirthTime.setText(this.childInfo.getTimeon());
		this.chckBxDead.setSelected(this.childInfo.isMert());
		this.chckBxFull.setSelected(this.childInfo.isDonosh());
		this.chckBxCriteria1.setSelected(this.childInfo.isKrit1());
		this.chckBxCriteria2.setSelected(this.childInfo.isKrit2());
		this.chckBxCriteria3.setSelected(this.childInfo.isKrit3());
		this.chckBxCriteria4.setSelected(this.childInfo.isKrit4());
		this.spinnerApgar1.setValue(this.childInfo.getApgar1());
		this.spinnerApgar5.setValue(this.childInfo.getApgar5());
		this.spinnerHeight.setValue(this.childInfo.getRost());
		this.spinnerWeight.setValue(this.childInfo.getMassa());
		this.spinnerChildNumGlobal.setValue(this.childInfo.getKolchild());
		this.spinnerChildNumLocal.setValue(this.childInfo.getNreb());
	}
	
	/**
	 * Загрузка информации о свидетельстве в интерфейс
	 */
	private void loadChildDocIntoPanel() {
		if (this.childDoc == null)
		{
			this.setDefaultDocValues();
			return;
		}
		this.cdeDocDate.setDate(this.childDoc.getDateoff());
		this.tfDocName.setText(this.childDoc.getFamreb());
		this.ticcbDocGiven.setSelectedPcod(this.childDoc.getSvidvrach());
		this.spinnerDocNum.setValue(this.childDoc.getNdoc());
		this.lblDocStatus.setText("Выдано");
		this.cbDocType.setSelectedIndex(this.childDoc.isDoctype() ? 0 : 1);
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
		if (!this.loadChildInfoFromPanel())	//Не все данные введены
		{
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
		//Выполняется в случае успешного добавления:
		this.childInfo = tmpInfo;
	}
	
	/**
	 * Сохранение изменений о новорождённом
	 */
	private void btnSaveChildClick() {
		try {
			if (this.childInfo != null)
				this.updateChildInfo();	//Обновление информации о новорождённом
			else
				this.addChildInfo();	//Добавление информации о новорождённом
			this.btnSaveChild.setToolTipText("Записать изменения");
			return;
		} catch (KmiacServerException|PatientNotFoundException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(this, "Операция не была выполнена",
				"Ошибка", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Обновление информации о свидетельстве
	 * @throws TException исключение общего вида
	 * @throws ChildDocNotFoundException свидетельство
	 * о рождении/перинатальной смерти не найдено
	 * @throws KmiacServerException исключение на стороне сервера
	 * @return Возвращает <code>true</code>, если информация обновлена;
	 * иначе - <code>false</code>
	 */
	private boolean updateChildDocument()
			throws KmiacServerException, ChildDocNotFoundException, TException {
		TRd_Svid oldDoc = this.childDoc.deepCopy();
		if (!this.loadChildDocFromPanel())	//Не все данные введены
			return false;
		if (!oldDoc.equals(this.childDoc)) {	//Данные были изменены
			int answer = JOptionPane.showConfirmDialog(this,
					"Вы действительно хотите изменить данные свидетельства",
					"Подтверждение изменения", JOptionPane.YES_NO_OPTION);
			//Пользователь подтвердил изменение данных:
			if (answer == JOptionPane.YES_OPTION)
				ClientHospital.tcl.updateChildDocument(this.childDoc);
		} else {
			//Данные не изменены - ничего не происходит
		}
		return true;
	}

	/**
	 * Занесение нового свидетельства в БД
	 * @throws TException исключение общего вида
	 * @throws PatientNotFoundException пациент не найден
	 * @throws ChildDocNumAlreadyExistException такой номер свидетельства
	 * о рождении/перинатальной
	 * смерти уже существует
	 * @throws KmiacServerException исключение на стороне сервера
	 * @return Возвращает <code>true</code>, если свидетельство внесено;
	 * иначе - <code>false</code>
	 */
	private boolean addChildDocument()
			throws KmiacServerException, PatientNotFoundException, TException {
		this.childDoc = new TRd_Svid();
		if (!this.loadChildDocFromPanel())	//Не все данные введены
		{
			this.childDoc = null;
			return false;
		}
		this.childDoc.setNpasp(this.patient.getPatientId());
		//Небольшие танцы с бубном:
		TRd_Svid tmpDoc = this.childDoc;
		//Поле childDoc останется null, если сервер вернёт исключение:
		this.childDoc = null;
		//Вызов функции сервера на добавление информации о свидетельстве:
		final int ndoc = ClientHospital.tcl.addChildDocument(tmpDoc);
		//Выполняется в случае успешного добавления:
		this.lblDocStatus.setText("Выдано");
		this.childDoc = tmpDoc;
		//Установка номера выданного свидетельства в поле экземпляра:
		this.childDoc.setNdoc(ndoc);
		//Загрузка номера выданного свидетельства в интерфейс:
		this.spinnerDocNum.setValue(ndoc);
		return true;
	}
	
	/**
	 * Печать свидетельства о рождении/перинатальной смерти
	 * @throws TException исключение общего вида
	 * @throws ChildDocNotFoundException свидетельство
	 * о рождении/перинатальной смерти не найдено
	 * @throws KmiacServerException исключение на стороне сервера
	 * @throws IOException ошибка создания документа
	 * @throws FileNotFoundException ошибка передачи документа с сервера
	 */
	private void printChildDocument()
			throws KmiacServerException, ChildDocNotFoundException,
			FileNotFoundException, IOException, TException {
		if ((this.childInfo != null) && (this.childDoc != null)) {
			String serverPath, clientPath;
			final int ndoc = this.childDoc.getNdoc();
			if (this.childDoc.isDoctype()) {	//Свидетельство о рождении
				serverPath = ClientHospital.tcl.printChildBirthDocument(ndoc);
			} else {							//Свидетельство о перинатальной смерти
				serverPath = ClientHospital.tcl.printChildDeathDocument(ndoc);
			}
			clientPath = File.createTempFile("muzdrav", ".htm").getAbsolutePath();
            ClientHospital.conMan.transferFileFromServer(serverPath, clientPath);
            ClientHospital.conMan.openFileInEditor(clientPath, false);
		} else
			JOptionPane.showMessageDialog(this, "Информация о новорождённом " +
					"отсутствует или свидетельство не найдено",
					"Ошибка", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Выдача свидетельства
	 */
	private void btnGiveDocClick() {
		if (this.childInfo == null)
		{
			JOptionPane.showMessageDialog(this, "Информация о новорождённом " +
					"отсутствует", "Ошибка", JOptionPane.ERROR_MESSAGE);
			return;
		}
		try {
			boolean needPrint;
			if (this.childDoc != null)	//Обновление информации о свидетельстве
				needPrint = this.updateChildDocument();
			else						//Выдача нового свидетельства
				needPrint = this.addChildDocument();
			if (needPrint)
				this.printChildDocument();	//Печать свидетельства
			return;
		} catch (KmiacServerException e) {
			e.printStackTrace();
		} catch (PatientNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Информация о новорождённом " +
					"отсутствует", "Ошибка", JOptionPane.ERROR_MESSAGE);
			this.childInfo = null;
			this.lblDocStatus.setText("Не выдано");
			return;
		} catch (ChildDocNotFoundException e) {
			JOptionPane.showMessageDialog(this, "Свидетельство не найдено",
					"Ошибка", JOptionPane.ERROR_MESSAGE);
			this.childDoc = null;
			this.lblDocStatus.setText("Не выдано");
			return;
		} catch (IOException e) {	//Поглощает FileNotFoundException
			JOptionPane.showMessageDialog(this, "Сбой во время загрузки " +
					"свидетельства о рождении/перинатальной смерти",
					"Ошибка", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (TException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(this, "Операция не была выполнена",
				"Ошибка", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Печать бланка свидетельства
	 */
	private void btnPrintBlankDocClick() {
		final boolean isLiveChild = (this.cbDocType.getSelectedIndex() == 0);
		try {
			String serverPath, clientPath;
			serverPath = ClientHospital.tcl.printChildBlankDocument(isLiveChild);
			clientPath = File.createTempFile("muzdrav", ".htm").getAbsolutePath();
            ClientHospital.conMan.transferFileFromServer(serverPath, clientPath);
            ClientHospital.conMan.openFileInEditor(clientPath, false);
			return;
		} catch (KmiacServerException e) {
			e.printStackTrace();
		} catch (IOException e) {	//Поглощает FileNotFoundException
			JOptionPane.showMessageDialog(this, "Сбой во время печати бланка " +
					"свидетельства о рождении/перинатальной смерти",
					"Ошибка", JOptionPane.ERROR_MESSAGE);
			return;
		} catch (TException e) {
			e.printStackTrace();
		}
		JOptionPane.showMessageDialog(this, "Операция не была выполнена",
				"Ошибка", JOptionPane.ERROR_MESSAGE);
	}
	
	/**
	 * Обновление пользователського интерфейса панели
	 */
	private void updatePanel() {
		final boolean isPatientSet = (this.patient != null);
		this.panelChildEdit.setVisible(isPatientSet);
		this.panelDoc.setVisible(isPatientSet);
		this.btnSaveChild.setToolTipText("Занести информацию");
		if (isPatientSet)
		{
			final int childId = this.patient.getPatientId();
			try {
				this.childInfo = ClientHospital.tcl.getChildInfo(childId);
				this.loadChildInfoIntoPanel();
				this.btnSaveChild.setToolTipText("Записать изменения");
				this.childDoc = ClientHospital.tcl.getChildDocument(childId);
				this.loadChildDocIntoPanel();
				this.cbDocType.setEnabled(false);
			} catch (KmiacServerException kse) {
				kse.printStackTrace();
			} catch (PatientNotFoundException e) {
				//Новорождённый не найден, его нужно добавить в таблицу:
				this.childInfo = null;
				this.setDefaultChildValues();
				this.childDoc = null;
				this.setDefaultDocValues();
			} catch (ChildDocNotFoundException e) {
				//Свидетельство ещё не было выдано:
				this.childDoc = null;
				this.setDefaultDocValues();
			} catch (TException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Инициализация пользовательского интерфейса панели
	 */
	private void setInterface() {
		this.panelChildEdit = new JPanel();
		this.panelChildEdit.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		this.panelDoc = new JPanel();
		this.panelDoc.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		this.btnSaveChild = new JButton("");
		this.btnSaveChild.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnSaveChildClick();
			}
		});
		this.btnSaveChild.setIcon(new ImageIcon(Children.class.getResource("/ru/nkz/ivcgzo/clientHospital/resources/1341981970_Accept.png")));
		this.btnSaveChild.setToolTipText("Сохранить изменения");
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(panelDoc, GroupLayout.DEFAULT_SIZE, 816, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(23)
							.addComponent(btnSaveChild)
							.addGap(27)
							.addComponent(panelChildEdit, GroupLayout.PREFERRED_SIZE, 723, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(171)
							.addComponent(btnSaveChild, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(panelChildEdit, GroupLayout.PREFERRED_SIZE, 337, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelDoc, GroupLayout.DEFAULT_SIZE, 335, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel lblDocTypeText = new JLabel("Тип свидетельства:");
		lblDocTypeText.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		JLabel lblDocNum = new JLabel("Номер свидетельства:");
		
		this.spinnerDocNum = new JSpinner();
		spinnerDocNum.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		this.spinnerDocNum.setEnabled(false);
		lblDocNum.setLabelFor(spinnerDocNum);
		
		JLabel lblDocName = new JLabel("Фамилия новорождённого:");
		
		this.tfDocName = new JTextField();
		tfDocName.setHorizontalAlignment(SwingConstants.LEFT);
		lblDocName.setLabelFor(tfDocName);
		this.tfDocName.setColumns(10);
		
		JLabel lblDocDate = new JLabel("Дата выдачи свидетельства:");
		
		cdeDocDate = new CustomDateEditor();
		cdeDocDate.setText("");
		lblDocDate.setLabelFor(cdeDocDate);
		
		JLabel lblDocGiven = new JLabel("Кем выдано:");
		
		this.ticcbDocGiven = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
		lblDocGiven.setLabelFor(this.ticcbDocGiven);
		
		btnGiveDoc = new JButton("Выдать свидетельство");
		this.btnGiveDoc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnGiveDocClick();
			}
		});
		
		this.btnPrintBlankDoc = new JButton("Распечатать бланк");
		this.btnPrintBlankDoc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnPrintBlankDocClick();
			}
		});
		
		JLabel lblDocStatusText = new JLabel("Статус:");
		lblDocStatusText.setFont(new Font("Tahoma", Font.BOLD, 11));
		
		lblDocStatus = new JLabel("Не выдано");
		lblDocStatusText.setLabelFor(lblDocStatus);
		
		cbDocType = new JComboBox<Object>();
		cbDocType.setModel(new DefaultComboBoxModel<Object>(new String[] {"Свидетельство о рождении", "Свидетельство о перинатальной смерти"}));
		cbDocType.setSelectedIndex(0);
		GroupLayout gl_panelDoc = new GroupLayout(panelDoc);
		gl_panelDoc.setHorizontalGroup(
			gl_panelDoc.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDoc.createSequentialGroup()
					.addGap(198)
					.addGroup(gl_panelDoc.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelDoc.createSequentialGroup()
							.addComponent(lblDocDate, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_panelDoc.createSequentialGroup()
							.addGroup(gl_panelDoc.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panelDoc.createSequentialGroup()
									.addComponent(btnGiveDoc)
									.addPreferredGap(ComponentPlacement.RELATED, 192, Short.MAX_VALUE)
									.addComponent(btnPrintBlankDoc))
								.addGroup(gl_panelDoc.createSequentialGroup()
									.addGroup(gl_panelDoc.createParallelGroup(Alignment.LEADING)
										.addComponent(lblDocNum)
										.addGroup(gl_panelDoc.createSequentialGroup()
											.addComponent(lblDocTypeText)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(cbDocType, 0, 160, Short.MAX_VALUE)))
									.addGap(89)
									.addGroup(gl_panelDoc.createParallelGroup(Alignment.TRAILING)
										.addComponent(cdeDocDate, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
										.addGroup(gl_panelDoc.createSequentialGroup()
											.addComponent(lblDocStatusText)
											.addPreferredGap(ComponentPlacement.RELATED)
											.addComponent(lblDocStatus))
										.addComponent(spinnerDocNum, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_panelDoc.createSequentialGroup()
									.addComponent(lblDocGiven)
									.addGap(76)
									.addComponent(ticcbDocGiven, GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE))
								.addGroup(gl_panelDoc.createSequentialGroup()
									.addComponent(lblDocName)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tfDocName, GroupLayout.DEFAULT_SIZE, 333, Short.MAX_VALUE)))
							.addGap(146))))
		);
		gl_panelDoc.setVerticalGroup(
			gl_panelDoc.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDoc.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDoc.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDocTypeText)
						.addComponent(cbDocType, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDocStatusText)
						.addComponent(lblDocStatus))
					.addGap(29)
					.addGroup(gl_panelDoc.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDocNum)
						.addComponent(spinnerDocNum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panelDoc.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDocName)
						.addComponent(tfDocName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(8)
					.addGroup(gl_panelDoc.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDocDate)
						.addComponent(cdeDocDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panelDoc.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDocGiven)
						.addComponent(ticcbDocGiven, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panelDoc.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnPrintBlankDoc, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnGiveDoc, GroupLayout.DEFAULT_SIZE, 32, Short.MAX_VALUE))
					.addContainerGap(118, Short.MAX_VALUE))
		);
		this.panelDoc.setLayout(gl_panelDoc);
		
		chckBxDead = new JCheckBox("Мертворождённый");
		chckBxFull = new JCheckBox("Доношенный");
		JSeparator separatorCB = new JSeparator();
		
		spinnerWeight = new JSpinner();
		spinnerWeight.setModel(new SpinnerNumberModel(0, 0, 9999, 1));
		
		JLabel lblWeight = new JLabel("Вес при рождении (г):");
		lblWeight.setLabelFor(spinnerWeight);
		
		JLabel lblHeight = new JLabel("Рост при рождении (см):");
		
		JPanel panelApgar = new JPanel();
		panelApgar.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JLabel lblApgar = new JLabel("Оценка по шкале Апгар:");
		panelApgar.add(lblApgar);
		
		JPanel panelChildNumber = new JPanel();
		panelChildNumber.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		JLabel lblChildNumGlobal = new JLabel("По счёту новорождённый у матери:");
		lblChildNumGlobal.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblBirthTime = new JLabel("Время рождения:");
		
		lblChildBirth = new JLabel("Выберите роды:");
		
		ticcbChildBirth = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
		lblChildBirth.setLabelFor(ticcbChildBirth);
		
		JPanel panelCriteria = new JPanel();
		panelCriteria.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		
		cteBirthTime = new CustomTimeEditor();
		cteBirthTime.setText("__:__ ");
		lblBirthTime.setLabelFor(cteBirthTime);
		
		spinnerHeight = new JSpinner();
		spinnerHeight.setModel(new SpinnerNumberModel(0, 0, 99, 1));
		lblHeight.setLabelFor(spinnerHeight);
		
		GroupLayout gl_panelChildEdit = new GroupLayout(this.panelChildEdit);
		gl_panelChildEdit.setHorizontalGroup(
			gl_panelChildEdit.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panelChildEdit.createSequentialGroup()
					.addGap(61)
					.addComponent(separatorCB, GroupLayout.PREFERRED_SIZE, 1, GroupLayout.PREFERRED_SIZE)
					.addGap(75)
					.addGroup(gl_panelChildEdit.createParallelGroup(Alignment.LEADING)
						.addComponent(panelCriteria, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
						.addGroup(gl_panelChildEdit.createSequentialGroup()
							.addComponent(lblChildBirth)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(ticcbChildBirth, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE))
						.addComponent(panelChildNumber, GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
						.addComponent(panelApgar, GroupLayout.DEFAULT_SIZE, 462, Short.MAX_VALUE)
						.addGroup(gl_panelChildEdit.createSequentialGroup()
							.addGroup(gl_panelChildEdit.createParallelGroup(Alignment.LEADING, false)
								.addGroup(gl_panelChildEdit.createSequentialGroup()
									.addComponent(lblBirthTime)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(cteBirthTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblHeight))
							.addGap(4)
							.addComponent(spinnerHeight, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGroup(gl_panelChildEdit.createParallelGroup(Alignment.TRAILING, false)
								.addGroup(gl_panelChildEdit.createSequentialGroup()
									.addComponent(lblWeight)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(spinnerWeight, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
								.addGroup(gl_panelChildEdit.createSequentialGroup()
									.addComponent(chckBxDead, GroupLayout.PREFERRED_SIZE, 124, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
									.addComponent(chckBxFull)))))
					.addGap(122))
		);
		gl_panelChildEdit.setVerticalGroup(
			gl_panelChildEdit.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panelChildEdit.createSequentialGroup()
					.addGap(31)
					.addComponent(separatorCB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				.addGroup(Alignment.LEADING, gl_panelChildEdit.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelChildEdit.createSequentialGroup()
						.addGroup(gl_panelChildEdit.createParallelGroup(Alignment.BASELINE, false)
							.addComponent(lblChildBirth)
							.addComponent(ticcbChildBirth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGap(18))
					.addGroup(gl_panelChildEdit.createParallelGroup(Alignment.TRAILING)
						.addComponent(chckBxFull)
						.addGroup(gl_panelChildEdit.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblBirthTime)
							.addComponent(chckBxDead)
							.addComponent(cteBirthTime, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_panelChildEdit.createParallelGroup(Alignment.BASELINE)
						.addComponent(spinnerWeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblWeight)
						.addComponent(lblHeight)
						.addComponent(spinnerHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(panelApgar, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panelChildNumber, GroupLayout.PREFERRED_SIZE, 61, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(panelCriteria, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
		);
		
		JLabel lblCriteria = new JLabel("Критерии живорождения:");
		chckBxCriteria1 = new JCheckBox("дыхание");
		chckBxCriteria2 = new JCheckBox("сердцебиение");
		chckBxCriteria3 = new JCheckBox("мышечная деятельность");
		chckBxCriteria4 = new JCheckBox("пульсация пуповины");
		GroupLayout gl_panelCriteria = new GroupLayout(panelCriteria);
		gl_panelCriteria.setHorizontalGroup(
			gl_panelCriteria.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCriteria.createSequentialGroup()
					.addGap(5)
					.addComponent(lblCriteria)
					.addGap(5)
					.addGroup(gl_panelCriteria.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelCriteria.createSequentialGroup()
							.addComponent(chckBxCriteria1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addGap(26))
						.addComponent(chckBxCriteria2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(8)
					.addGroup(gl_panelCriteria.createParallelGroup(Alignment.LEADING)
						.addComponent(chckBxCriteria3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(gl_panelCriteria.createSequentialGroup()
							.addComponent(chckBxCriteria4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
								.addComponent(chckBxCriteria1)
								.addComponent(chckBxCriteria4))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panelCriteria.createParallelGroup(Alignment.BASELINE)
								.addComponent(chckBxCriteria2)
								.addComponent(chckBxCriteria3))))
					.addContainerGap())
		);
		panelCriteria.setLayout(gl_panelCriteria);
		
		spinnerChildNumGlobal = new JSpinner();
		lblChildNumGlobal.setLabelFor(spinnerChildNumGlobal);
		spinnerChildNumGlobal.setModel(new SpinnerNumberModel(1, 1, 999, 1));
		
		JLabel lblChildNumLocal = new JLabel("Номер новорождённого в многоплодных родах:");
		lblChildNumLocal.setHorizontalAlignment(SwingConstants.CENTER);
		
		spinnerChildNumLocal = new JSpinner();
		lblChildNumLocal.setLabelFor(spinnerChildNumLocal);
		spinnerChildNumLocal.setModel(new SpinnerNumberModel(1, 1, 9, 1));
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
		panelApgar.add(lblApgar1);
		
		spinnerApgar1 = new JSpinner();
		lblApgar1.setLabelFor(spinnerApgar1);
		spinnerApgar1.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		panelApgar.add(spinnerApgar1);
		
		JLabel lblApgar5 = new JLabel(";  через 5 минут");
		panelApgar.add(lblApgar5);
		
		spinnerApgar5 = new JSpinner();
		lblApgar5.setLabelFor(spinnerApgar5);
		spinnerApgar5.setModel(new SpinnerNumberModel(0, 0, 10, 1));
		panelApgar.add(spinnerApgar5);
		
		this.panelChildEdit.setLayout(gl_panelChildEdit);
		setLayout(groupLayout);
	}
}
