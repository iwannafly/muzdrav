package ru.nkz.ivcgzo.clientHospital;

//TODO: ДОБАВЛЕНИЕ КАПЕЛЬНИЦ И ИНЪЕКЦИЙ
//TODO: ФУНКЦИОНАЛ ТРЁХ ПАНЕЛЕЙ
//TODO: ЮНИТ-ТЕСТИРОВАНИЕ

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.Collections;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftHospital.TConsult;
import ru.nkz.ivcgzo.thriftHospital.TDiagnostic;
import ru.nkz.ivcgzo.thriftHospital.TDiet;
import ru.nkz.ivcgzo.thriftHospital.TMedication;
import ru.nkz.ivcgzo.thriftHospital.TPatient;
import ru.nkz.ivcgzo.thriftHospital.TProcedures;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;

/**
 * Панель учёта медицинских назначений: препаратов, 
 * исследований, режимов и лечебных процедур
 * @author Балабаев Никита Дмитриевич
 */
public final class Assignments extends JPanel {

	private static final long serialVersionUID = 3513837719265529747L;
    private static final String LINE_SEP = System.lineSeparator();
    private static final URL addIconURL = MainFrame.class.getResource(
    		"/ru/nkz/ivcgzo/clientHospital/resources/1331789242_Add.png");
    private static final URL saveIconURL = MainFrame.class.getResource(
    		"/ru/nkz/ivcgzo/clientHospital/resources/1341981970_Accept.png");
    private static final URL delIconURL = MainFrame.class.getResource(
    		"/ru/nkz/ivcgzo/clientHospital/resources/1331789259_Delete.png");
    private static final Font defaultFont = new Font("Tahoma", Font.PLAIN, 12);
    private TPatient patient;
	private UserAuthInfo userAuth;
    private Icon addIcon, saveIcon, delIcon;
    private Dimension defaultBtnDim, defaultMaxPnlDim;
    private JPanel pnlMain;
    private JPanel pnlMedications, pnlDiagnostics;
    private JPanel pnlDiet, pnlProcedures, pnlConsults;
    private JScrollPane spMain;
    private JScrollPane spMedicationsTbl, spDiagnosticsTbl;
    private JScrollPane spDietTbl, spProceduresTbl, spConsultsTbl;
    private JScrollPane spMedicationsInfo, spDiagnosticsResult;
    private JTextArea taMedicationsInfo, taDiagnosticsResult;
    private Box vbMedicationsTbl, vbMedicationsBtn;
    private Box vbDiagnosticsTbl, vbDiagnosticsBtn;
    private Box vbDietTbl, vbDietBtn;
    private Box vbProceduresTbl, vbProceduresBtn;
    private Box vbConsultsTbl, vbConsultsBtn;
    private CustomTable<TMedication, TMedication._Fields> tblMedications;
    private CustomTable<TDiagnostic, TDiagnostic._Fields> tblDiagnostics;
    private CustomTable<TDiet, TDiet._Fields> tblDiet;
    private CustomTable<TProcedures, TProcedures._Fields> tblProcedures;
    private CustomTable<TConsult, TConsult._Fields> tblConsults;
    private TMedication lastMedItem;
    private TDiagnostic lastDiagItem;
    private JButton btnAddMedication, btnSaveMedication, btnDelMedication;
    private JButton btnAddDiagnostic;
    private JButton btnAddDiet, btnDelDiet;
    private JButton btnAddProcedure, btnDelProcedure;
    private JButton btnAddConsult, btnDelConsult, btnSaveConsult;

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
		this.clearAllTables();
		this.clearAllTextFields();
		if (this.patient != null) {
			this.fillTableMedications();
			this.fillTableDiagnostics();
			/*
			this.fillTableDiet();
			this.fillTableProcedures();
			this.fillTableConsults();
			*/
		}
	}
	
	/**
	 * Очистка всех таблиц
	 */
	private void clearAllTables() {
		this.tblMedications.setData(Collections.<TMedication>emptyList());
		this.tblDiagnostics.setData(Collections.<TDiagnostic>emptyList());
		this.tblDiet.setData(Collections.<TDiet>emptyList());
		this.tblProcedures.setData(Collections.<TProcedures>emptyList());
		this.tblConsults.setData(Collections.<TConsult>emptyList());
	}
	
	/**
	 * Очистка всех текстовых полей
	 */
	private void clearAllTextFields() {
		this.taMedicationsInfo.setText(null);
		this.taDiagnosticsResult.setText(null);
	}
	
	/**
	 * Заполнение таблицы лекарственных назначений данными из БД
	 */
	private void fillTableMedications() {
		if ((this.patient != null) && (ClientHospital.tcl != null))
			try {
				this.lastMedItem = null;
				this.taMedicationsInfo.setText(null);
				this.tblMedications.setData(
						ClientHospital.tcl.getMedications(
								this.patient.getGospitalCod()));
				this.updateMedicationsSelection();
			} catch (TException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Заполнение таблицы исследований данными из БД
	 */
	private void fillTableDiagnostics() {
		if ((this.patient != null) && (ClientHospital.tcl != null))
			try {
				this.lastDiagItem = null;
				this.taDiagnosticsResult.setText(null);
				this.tblDiagnostics.setData(
						ClientHospital.tcl.getDiagnostics(
								this.patient.getGospitalCod()));
				this.updateDiagnosticsSelection();
			} catch (TException e) {
				e.printStackTrace();
			}
	}
	
	/**
	 * Изменение выделения таблицы лекарственных назначений
	 */
	private void updateMedicationsSelection() {
		TMedication curItem = this.tblMedications.getSelectedItem();
		if ((curItem == null) || (this.lastMedItem == curItem))
			return;
		this.lastMedItem = curItem;
		String strInfo = "";
		if (curItem.isSetOpl_name())
			strInfo += "Средства оплаты: " + curItem.getOpl_name() + LINE_SEP;
		if (curItem.isSetDiag_name())
			strInfo += "Диагноз: " + curItem.getDiag_name() + LINE_SEP;
		if (curItem.isSetFlek())
			strInfo += "Лекарственая форма: " + curItem.getFlek() + LINE_SEP;
		if (curItem.isSetKomm() && (curItem.getKomm().length() > 0))
			strInfo += "Указания по приёму: " + curItem.getKomm() + LINE_SEP;
		strInfo += "Назначивший врач: " + curItem.getVrach_name();
		if (curItem.isSetVracho() && curItem.isSetVracho_name())
			strInfo += LINE_SEP + "Отменивший врач: " + curItem.getVracho_name();
		this.taMedicationsInfo.setText(strInfo);
	}
	
	/**
	 * Изменение выделения таблицы исследований
	 */
	private void updateDiagnosticsSelection() {
		TDiagnostic curItem = this.tblDiagnostics.getSelectedItem();
		if ((curItem == null) || (this.lastDiagItem == curItem))
			return;
		this.lastDiagItem = curItem;
		String strInfo = "";
		if (curItem.isSetOp_name())
			strInfo += "Описание исследования: " + curItem.getOp_name() + LINE_SEP;
		if (curItem.isSetRez_name())
			strInfo += "Заключение: " + curItem.getRez_name();
		this.taDiagnosticsResult.setText(strInfo);
	}
	
	/**
	 * Установка параметров заданной панели
	 * @param parentCom Компонент, в который будет добавлена панель
	 * @param curPanel Текущая панель
	 * @param header Заголовок панели
	 */
	private void setCustomPanel(JComponent parentCom, JPanel curPanel,
			final String header) {
		if (curPanel == null)
			return;
		curPanel.setBorder(
				new TitledBorder(
						new LineBorder(null, 1, true),
						header,
						TitledBorder.LEFT,
						TitledBorder.TOP,
						null,
						null));
		curPanel.setLayout(new BoxLayout(curPanel, BoxLayout.X_AXIS));
		curPanel.setMaximumSize(this.defaultMaxPnlDim);
		if (parentCom != null)
			parentCom.add(curPanel);
	}
	
	/**
	 * Установка параметров заданной текстовой области
	 * @param curArea Текстовая область
	 * @param curFont Желаемый шрифт текста
	 * @param curSP Полоса прокрутки текстовой области
	 */
	private void setCustomTextArea(JTextArea curArea,
			final Font curFont, JScrollPane curSP) {
		if (curArea == null)
			return;
		if (curFont != null)
			curArea.setFont(curFont);
		curArea.setLineWrap(true);
		curArea.setWrapStyleWord(true);
		curArea.setEnabled(false);
		curArea.setRows(6);
		if (curSP != null)
			curSP.setViewportView(curArea);
	}
	
	/**
	 * Установка параметров заданной кнопки и добавление её на заданную панель
	 * @param curPanel Панель, в которую будет добавлена кнопка
	 * @param curButton Добавляемая кнопка
	 * @param curDim Размерность кнопки
	 * @param curIcon Иконка, устанавливаемая на кнопку
	 * @param curTooltip Подсказка, устанавливаемая на кнопку
	 */
	private void setCustomButton(JComponent curPanel, JButton curButton,
			final Dimension curDim, final Icon curIcon, final String curTooltip) {
		if (curButton == null)
			return;
		if (curDim != null) {
			curButton.setMaximumSize(curDim);
			curButton.setPreferredSize(curDim);
		}
		if (curIcon != null)
			curButton.setIcon(curIcon);
		if (curTooltip != null)
			curButton.setToolTipText(curTooltip);
		if (curPanel != null)
			curPanel.add(curButton);
	}
    
    /**
     * Отображение пользователю диалога подтверждения
     * с заданным текстом вопроса
     * @param strQuestion Текст вопроса
     * @return Возвращает <code>true</code>, если пользователь подтвердил;
	 * иначе - <code>false</code>
     */
    private boolean getUserAnswer(final String strQuestion) {
		int opResult = JOptionPane.showConfirmDialog(
	            this, strQuestion, "Подтверждение",
	            JOptionPane.YES_NO_OPTION);
		return (opResult == JOptionPane.YES_OPTION);
    }
    
    /**
     * Отображение пользователю диалога ошибки
     * с заданным текстом ошибки
     * @param msgText Текст ошибки
     */
    private void showErrorDialog(final String msgText) {
    	JOptionPane.showMessageDialog(
	            this, msgText, "Ошибка",
	            JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Событие нажатия на кнопку добавления лек. назначения
     */
    private void btnAddMedicationClick() {
        if ((this.patient != null) && (ClientHospital.conMan != null)) {
            ClientHospital.conMan.showMedicationForm(this.patient.getPatientId(),
    		this.patient.getSurname(), this.patient.getName(), this.patient.getMiddlename(),
    		this.patient.getGospitalCod());
            fillTableMedications();
        }
    }
    
    /**
     * Функция проверки лекарственного назначения на корректность
     * @param med Проверяемое лекарственное назначение
     * @return Возвращает <code>true</code>, если назначение корректно;
	 * иначе - <code>false</code>
     */
    private boolean checkMedication(TMedication med) {
		if (med.isSetDatao()) {
			if ((med.getDatao() < med.getDatan()) ||
				(med.getDatao() > med.getDatae())) {
				showErrorDialog("Некорректная дата отмены");
				return false;
			}
			if (!med.isSetVracho())
				med.setVracho(this.userAuth.getPcod());
		}
		if (med.isSetDoza() && (med.getDoza() <= 0)) {
			showErrorDialog("Некорректная разовая доза");
			return false;
		}
		if (med.isSetPereod() && (med.getPereod() <= 0)) {
			showErrorDialog("Некорректная периодичность приёма");
			return false;
		}
		return true;
    }

    /**
     * Событие нажатия на кнопку сохранения лек. назначения
     */
    private void btnSaveMedicationClick() {
    	TMedication curItem = tblMedications.getSelectedItem();
        if ((this.patient != null) && (ClientHospital.tcl != null) &&
        	(curItem != null)) {
        	if (!checkMedication(curItem))
        		return;
			try {
				ClientHospital.tcl.updateMedication(curItem);
                fillTableMedications();
			} catch (TException e1) {
				showErrorDialog("Ошибка при сохранении назначения");
				e1.printStackTrace();
			}
        }
    }
    
    /**
     * Событие нажатия на кнопку удаления лек. назначения
     */
    private void btnDelMedicationClick() {
    	TMedication curItem = tblMedications.getSelectedItem();
        if ((this.patient != null) && (ClientHospital.tcl != null) &&
            (curItem != null) &&
        	getUserAnswer("Вы действительно хотите удалить назначение?"))
			try {
				ClientHospital.tcl.deleteMedication(curItem.getNlek());
                fillTableMedications();
			} catch (TException e1) {
				showErrorDialog("Ошибка при удалении назначения");
				e1.printStackTrace();
			}
    }
    
    /**
     * Событие нажатия на кнопку добавления исследования
     */
    private void btnAddDiagnosticClick() {
        if ((this.patient != null) && (ClientHospital.conMan != null)) {
            ClientHospital.conMan.showLabRecordForm(this.patient.getPatientId(),
    		this.patient.getSurname(), this.patient.getName(), this.patient.getMiddlename(),
    		this.patient.getGospitalCod(), "Л|Д");
            fillTableDiagnostics();
        }
    }
    
    /**
     * Событие нажатия на кнопку добавления лечебной процедуры
     */
    private void btnAddProcedureClick() {
        if ((this.patient != null) && (ClientHospital.conMan != null)) {
            ClientHospital.conMan.showLabRecordForm(this.patient.getPatientId(),
    		this.patient.getSurname(), this.patient.getName(), this.patient.getMiddlename(),
    		this.patient.getGospitalCod(), "Т");
        }
    }
	
	/**
	 * Инициализация пользовательского интерфейса панели
	 */
	private void setInterface() {
		this.addIcon = new ImageIcon(Assignments.addIconURL);
		this.saveIcon = new ImageIcon(Assignments.saveIconURL);
		this.delIcon = new ImageIcon(Assignments.delIconURL);
		this.defaultBtnDim = new Dimension(50, 50);
		this.defaultMaxPnlDim = new Dimension(2560, 256);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.pnlMain = new JPanel();
		this.pnlMain.setLayout(new BoxLayout(this.pnlMain, BoxLayout.Y_AXIS));
		this.setPanelMedications();
		this.setPanelDiagnostics();
		this.setPanelDiet();
		this.setPanelProcedures();
		this.setPanelConsults();
		this.spMain = new JScrollPane();
		this.add(this.spMain);
		this.spMain.setViewportView(this.pnlMain);
	}
	
    /**
     * Инициализация панели медицинских назначений
     */
	private void setPanelMedications() {
		this.pnlMedications = new JPanel();
		this.setCustomPanel(this.pnlMain, this.pnlMedications, "Лекарственные назначения");
		this.vbMedicationsTbl = Box.createVerticalBox();
		this.pnlMedications.add(this.vbMedicationsTbl);
		this.setTableMedication();
		this.setInfoMedication();
		this.vbMedicationsBtn = Box.createVerticalBox();
		this.pnlMedications.add(this.vbMedicationsBtn);
		this.setButtonsMedication();
	}
	
    /**
     * Инициализация панели лабораторных и диагностических исследований
     */
	private void setPanelDiagnostics() {
		this.pnlDiagnostics = new JPanel();
		this.setCustomPanel(this.pnlMain, this.pnlDiagnostics, "Лабораторные и диагностические исследования");
		this.vbDiagnosticsTbl = Box.createVerticalBox();
		this.pnlDiagnostics.add(this.vbDiagnosticsTbl);
		this.setTableDiagnostics();
		this.setInfoDiagnostics();
		this.vbDiagnosticsBtn = Box.createVerticalBox();
		this.pnlDiagnostics.add(this.vbDiagnosticsBtn);
		this.setButtonsDiagnostics();
	}
	
    /**
     * Инициализация панели режимов и диет
     */
	private void setPanelDiet() {
		this.pnlDiet = new JPanel();
		this.setCustomPanel(this.pnlMain, this.pnlDiet, "Режим и диета");
		this.vbDietTbl = Box.createVerticalBox();
		this.pnlDiet.add(this.vbDietTbl);
		this.setTableDiet();
		this.vbDietBtn = Box.createVerticalBox();
		this.pnlDiet.add(this.vbDietBtn);
		this.setButtonsDiet();
	}
	
    /**
     * Инициализация панели лечебных процедур
     */
	private void setPanelProcedures() {
		this.pnlProcedures = new JPanel();
		this.setCustomPanel(this.pnlMain, this.pnlProcedures, "Лечебные процедуры");
		this.vbProceduresTbl = Box.createVerticalBox();
		this.pnlProcedures.add(this.vbProceduresTbl);
		this.setTableProcedures();
		this.vbProceduresBtn = Box.createVerticalBox();
		this.pnlProcedures.add(this.vbProceduresBtn);
		this.setButtonsProcedures();
	}
	
    /**
     * Инициализация панели консультаций
     */
	private void setPanelConsults() {
		this.pnlConsults = new JPanel();
		this.setCustomPanel(this.pnlMain, this.pnlConsults, "Консультации");
		this.vbConsultsTbl = Box.createVerticalBox();
		this.pnlConsults.add(this.vbConsultsTbl);
		this.setTableConsults();
		this.vbConsultsBtn = Box.createVerticalBox();
		this.pnlConsults.add(this.vbConsultsBtn);
		this.setButtonsConsults();
	}
	
	/**
     * Инициализация таблицы медицинских назначений
     */
	private void setTableMedication() {
		this.spMedicationsTbl = new JScrollPane();
		this.vbMedicationsTbl.add(this.spMedicationsTbl);
		this.tblMedications = new CustomTable<TMedication, TMedication._Fields>(
				false, false, TMedication.class, 0, "Наименование",
				4, "Дата назначения", 12, "Дата окончания", 14, "Дата отмены",
				22, "Способ введения", 10, "Схема приёма", 11, "Кол-во в день",
				7, "Разовая доза", 21, "ед.");
		this.tblMedications.addKeyListener(new KeyAdapter() {			
			@Override
		    public void keyReleased(KeyEvent e) {
				if ((e.getKeyCode() == KeyEvent.VK_DOWN) ||
					(e.getKeyCode() == KeyEvent.VK_UP) ||
					(e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) ||
					(e.getKeyCode() == KeyEvent.VK_PAGE_UP)) {
						Assignments.this.updateMedicationsSelection();
					}
			}
		});
		this.tblMedications.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Assignments.this.updateMedicationsSelection();
			}
		});
		this.tblMedications.setDateField(1);
		this.tblMedications.setDateField(2);
		this.tblMedications.setDateField(3);
		this.tblMedications.setIntegerClassifierSelector(5, IntegerClassifiers.n_period);
		this.tblMedications.setEditableFields(true, 3, 5, 6, 7);
		this.spMedicationsTbl.setViewportView(this.tblMedications);
	}

	/**
     * Инициализация таблицы лабораторных и диагностических исследований
     */
	private void setTableDiagnostics() {
		this.spDiagnosticsTbl = new JScrollPane();
		this.vbDiagnosticsTbl.add(this.spDiagnosticsTbl);
		this.tblDiagnostics = new CustomTable<TDiagnostic, TDiagnostic._Fields>(
				false, false, TDiagnostic.class, 1, "Код исследования",
				2, "Наименование исследования", 3, "Результат",
				4, "Дата назначения", 5, "Дата выполнения");
		this.tblDiagnostics.setDateField(3);
		this.tblDiagnostics.setDateField(4);
		this.spDiagnosticsTbl.setViewportView(this.tblDiagnostics);
	}

	/**
     * Инициализация таблицы режимов и диет
     */
	private void setTableDiet() {
		this.spDietTbl = new JScrollPane();
		this.vbDietTbl.add(this.spDietTbl);
		this.tblDiet = new CustomTable<TDiet, TDiet._Fields>(
				false, false, TDiet.class, 0, "Тип",
				1, "№", 2, "Дата назначения", 3, "Дата отмены");
		this.tblDiet.setDateField(2);
		this.tblDiet.setDateField(3);
		this.spDietTbl.setViewportView(this.tblDiet);
	}

	/**
     * Инициализация таблицы лечебных процедур
     */
	private void setTableProcedures() {
		this.spProceduresTbl = new JScrollPane();
		this.vbProceduresTbl.add(this.spProceduresTbl);
		this.tblProcedures = new CustomTable<TProcedures, TProcedures._Fields>(
				false, false, TProcedures.class,
				0, "Наименование процедуры", 1, "Дата начала", 
				2, "Дата окончания", 3, "Кол-во проведённых процедур");
		this.tblProcedures.setDateField(1);
		this.tblProcedures.setDateField(2);
		this.spProceduresTbl.setViewportView(this.tblProcedures);
	}

	/**
     * Инициализация таблицы консультаций
     */
	private void setTableConsults() {
		this.spConsultsTbl = new JScrollPane();
		this.vbConsultsTbl.add(this.spConsultsTbl);
		this.tblConsults = new CustomTable<TConsult, TConsult._Fields>(
				false, false, TConsult.class,
				3, "Вид врачебного документа", 4, "Обоснование", 
				9, "Дата назначения", 11, "Дата отмены");
		this.tblConsults.setDateField(2);
		this.tblConsults.setDateField(3);
		this.spConsultsTbl.setViewportView(this.tblConsults);
	}

	/**
     * Инициализация поля информации о медицинских назначениях
     */
	private void setInfoMedication() {
		this.spMedicationsInfo = new JScrollPane();
		this.vbMedicationsTbl.add(this.spMedicationsInfo);
		this.taMedicationsInfo = new JTextArea();
		this.setCustomTextArea(this.taMedicationsInfo,
				Assignments.defaultFont, this.spMedicationsInfo);
	}

	/**
     * Инициализация поля информации о лабораторных и диагностических исследованиях
     */
	private void setInfoDiagnostics() {
		this.spDiagnosticsResult = new JScrollPane();
		this.vbDiagnosticsTbl.add(this.spDiagnosticsResult);
		this.taDiagnosticsResult = new JTextArea();
		this.setCustomTextArea(this.taDiagnosticsResult,
				Assignments.defaultFont, this.spDiagnosticsResult);
	}

	/**
     * Инициализация кнопок панели медицинских назначений
     */
	private void setButtonsMedication() {
		this.btnAddMedication = new JButton();
		this.setCustomButton(this.vbMedicationsBtn, this.btnAddMedication,
				this.defaultBtnDim, this.addIcon,
				"Добавить лекарственное назначение");
		this.btnAddMedication.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            	Assignments.this.btnAddMedicationClick();
            }
        });
		
		this.btnSaveMedication = new JButton();
		this.setCustomButton(this.vbMedicationsBtn, this.btnSaveMedication,
				this.defaultBtnDim, this.saveIcon,
				"Сохранить лекарственное назначение");
		this.btnSaveMedication.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            	Assignments.this.btnSaveMedicationClick();
            }
        });

		this.btnDelMedication = new JButton();
		this.setCustomButton(this.vbMedicationsBtn, this.btnDelMedication,
				this.defaultBtnDim, this.delIcon,
				"Удалить лекарственное назначение");
		this.btnDelMedication.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            	Assignments.this.btnDelMedicationClick();
            }
        });
	}

	/**
     * Инициализация кнопок панели лабораторных и диагностических исследований
     */
	private void setButtonsDiagnostics() {
		this.btnAddDiagnostic = new JButton();
		this.setCustomButton(this.vbDiagnosticsBtn, this.btnAddDiagnostic,
				this.defaultBtnDim, this.addIcon, "Добавить исследование");
		this.btnAddDiagnostic.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            	Assignments.this.btnAddDiagnosticClick();
            }
        });
	}

	/**
     * Инициализация кнопок панели режима и диеты
     */
	private void setButtonsDiet() {
		this.btnAddDiet = new JButton();
		this.setCustomButton(this.vbDietBtn, this.btnAddDiet,
				this.defaultBtnDim, this.addIcon, "Добавить режим (стол)");
		this.btnAddDiet.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            	//TODO
            }
        });

		this.btnDelDiet = new JButton();
		this.setCustomButton(this.vbDietBtn, this.btnDelDiet,
				this.defaultBtnDim, this.delIcon, "Удалить режим (стол)");
		this.btnDelDiet.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            	//TODO
            }
        });
	}

	/**
     * Инициализация кнопок панели лечебных процедур
     */
	private void setButtonsProcedures() {
		this.btnAddProcedure = new JButton();
		this.setCustomButton(this.vbProceduresBtn, this.btnAddProcedure,
				this.defaultBtnDim, this.addIcon, "Добавить процедуру");
		this.btnAddProcedure.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            	Assignments.this.btnAddProcedureClick();
            }
        });

		this.btnDelProcedure = new JButton();
		this.setCustomButton(this.vbProceduresBtn, this.btnDelProcedure,
				this.defaultBtnDim, this.delIcon, "Удалить процедуру");
		this.btnDelProcedure.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            	//TODO
            }
        });
	}

	/**
     * Инициализация кнопок панели консультаций
     */
	private void setButtonsConsults() {
		this.btnAddConsult = new JButton();
		this.setCustomButton(this.vbConsultsBtn, this.btnAddConsult,
				this.defaultBtnDim, this.addIcon, "Добавить консультацию");
		this.btnAddConsult.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            	//TODO
            }
        });

		this.btnSaveConsult = new JButton();
		this.setCustomButton(this.vbConsultsBtn, this.btnSaveConsult,
				this.defaultBtnDim, this.saveIcon, "Сохранить консультацию");
		this.btnSaveConsult.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            	//TODO
            }
        });

		this.btnDelConsult = new JButton();
		this.setCustomButton(this.vbConsultsBtn, this.btnDelConsult,
				this.defaultBtnDim, this.delIcon, "Удалить консультацию");
		this.btnDelConsult.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            	//TODO
            }
        });
	}
}
