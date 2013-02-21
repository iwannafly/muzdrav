package ru.nkz.ivcgzo.clientHospital;

//TOFO: ИСПРАВИТЬ ДОБАВЛЕНИЕ ЛЕКАРСТВЕННЫХ НАЗНАЧЕНИЙ

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
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
    private static final String addIconPath = "/ru/nkz/ivcgzo/clientHospital/resources/1331789242_Add.png";
    private static final String delIconPath = "/ru/nkz/ivcgzo/clientHospital/resources/1331789259_Delete.png";
    private Icon addIcon, delIcon;
    private Dimension defaultDimension;
	private UserAuthInfo userAuth = null;
    private TPatient patient = null;
    private JPanel pnlMedications, pnlDiagnostics, pnlDiet, pnlProcedures;
    private JScrollPane spMedications, spDiagnostics, spDiet, spProcedures;
    private CustomTable<TMedication, TMedication._Fields> tblMedications;
    private CustomTable<TDiagnostic, TDiagnostic._Fields> tblDiagnostics;
    private CustomTable<TDiet, TDiet._Fields> tblDiet;
    private CustomTable<TProcedures, TProcedures._Fields> tblProcedures;
    private JButton btnAddMedication, btnDelMedication;
    private JButton btnAddDiagnostic, btnDelDiagnostic;
    private JButton btnAddDiet, btnDelDiet;
    private JButton btnAddProcedure, btnDelProcedure;

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
		if (this.patient != null) {
			/*
			this.fillTableMedications();
			this.fillTableDiagnostics();
			this.fillTableDiet();
			this.fillTableProcedures();
			*/
		}
	}
	
	/**
	 * Очистка всех таблиц
	 */
	private void clearAllTables() {
		this.tblMedications.setData(new ArrayList<TMedication> (0));
		this.tblDiagnostics.setData(new ArrayList<TDiagnostic> (0));
		this.tblDiet.setData(new ArrayList<TDiet> (0));
		this.tblProcedures.setData(new ArrayList<TProcedures> (0));
	}
	
	/**
	 * Инициализация пользовательского интерфейса панели
	 */
	private void setInterface() {
		this.addIcon = new ImageIcon(MainFrame.class.getResource(Assignments.addIconPath));
		this.delIcon = new ImageIcon(MainFrame.class.getResource(Assignments.delIconPath));
		this.defaultDimension = new Dimension(50, 50);
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setPanelMedications();
		this.setPanelDiagnostics();
		this.setPanelDiet();
		this.setPanelProcedures();
	}
	
	/**
	 * Установка параметров заданной панели
	 * @param parentPanel Родительская панель, в которую будет добавлена текущая
	 * @param curPanel Текущая панель
	 * @param header Заголовок текущей панели
	 */
	private void setCustomPanel(JPanel parentPanel, JPanel curPanel, String header) {
		curPanel.setBorder(
				new TitledBorder(
						new LineBorder(new Color(0, 0, 0), 1, true),
						header, TitledBorder.LEFT,
						TitledBorder.TOP, null, null));
		curPanel.setLayout(new BoxLayout(curPanel, BoxLayout.X_AXIS));
		parentPanel.add(curPanel);
	}
	
	private void setPanelMedications() {
		this.pnlMedications = new JPanel();
		this.setCustomPanel(this, this.pnlMedications, "Лекарственные назначения");
		this.setTableMedication();
		this.setButtonsMedication();
	}
	
	private void setPanelDiagnostics() {
		this.pnlDiagnostics = new JPanel();
		this.setCustomPanel(this, this.pnlDiagnostics, "Исследования");
		this.setTableDiagnostics();
		this.setButtonsDiagnostics();
	}
	
	private void setPanelDiet() {
		this.pnlDiet = new JPanel();
		this.setCustomPanel(this, this.pnlDiet, "Режим и диета");
		this.setTableDiet();
		this.setButtonsDiet();
	}
	
	private void setPanelProcedures() {
		this.pnlProcedures = new JPanel();
		this.setCustomPanel(this, this.pnlDiet, "Лечебные процедуры");
		this.setTableProcedures();
		this.setButtonsProcedures();
	}
	
	private void setTableMedication() {
		this.spMedications = new JScrollPane();
		this.pnlMedications.add(this.spMedications);
		this.tblMedications = new CustomTable<TMedication, TMedication._Fields>(
				false, false, TMedication.class, 2, "Наименование",
				5, "Дата назначения", 13, "Длительность", 15, "Дата отмены");
		this.spMedications.setViewportView(this.tblMedications);
		this.tblMedications.setDateField(1);
		this.tblMedications.setDateField(3);
	}
	
	private void setTableDiagnostics() {
		this.spDiagnostics = new JScrollPane();
		this.pnlDiagnostics.add(this.spDiagnostics);
		this.tblDiagnostics = new CustomTable<TDiagnostic, TDiagnostic._Fields>(
				false, false, TDiagnostic.class, 3, "Наименование",
				7, "Дата назначения", 7, "Дата выполнения");
		this.spDiagnostics.setViewportView(this.tblDiagnostics);
		this.tblDiagnostics.setDateField(1);
		this.tblDiagnostics.setDateField(2);
	}
	
	private void setTableDiet() {
		this.spDiet = new JScrollPane();
		this.pnlDiet.add(this.spDiet);
		this.tblDiet = new CustomTable<TDiet, TDiet._Fields>(
				false, false, TDiet.class);
		this.spDiet.setViewportView(this.tblDiet);
	}
	
	private void setTableProcedures() {
		this.spProcedures = new JScrollPane();
		this.pnlProcedures.add(this.spProcedures);
		this.tblProcedures = new CustomTable<TProcedures, TProcedures._Fields>(
				false, false, TProcedures.class);
		this.spProcedures.setViewportView(this.tblProcedures);
	}
	
	/**
	 * Установка параметров заданной кнопки и добавление её на заданную панель
	 * @param curPanel Панель, в которую будет добавлена кнопка
	 * @param curButton Добавляемая кнопка
	 * @param curDim Размерность кнопки
	 * @param curIcon Иконка, устанавливаемая на кнопку
	 */
	private void setCustomButton(JPanel curPanel, JButton curButton, Dimension curDim, Icon curIcon) {
		if (curButton == null)
			return;
		if (curDim != null) {
			curButton.setMaximumSize(curDim);
			curButton.setPreferredSize(curDim);
		}
		if (curIcon != null)
			curButton.setIcon(curIcon);
		if (curPanel != null)
			curPanel.add(curButton);
	}
	
	private void setButtonsMedication() {
		this.btnAddMedication = new JButton();
		this.setCustomButton(this.pnlMedications, this.btnAddMedication,
				this.defaultDimension, this.addIcon);
		this.btnAddMedication.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if ((patient != null) && (ClientHospital.conMan != null)) {
                    ClientHospital.conMan.showMedicationForm(patient.getPatientId(),
                        patient.getSurname(), patient.getName(), patient.getMiddlename(),
                        patient.getGospitalCod());
                }
            }
        });

		this.btnDelMedication = new JButton();
		this.setCustomButton(this.pnlMedications, this.btnDelMedication,
				this.defaultDimension, this.delIcon);
		this.btnDelMedication.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            	//TODO
            }
        });
	}
	
	private void setButtonsDiagnostics() {
		this.btnAddDiagnostic = new JButton();
		this.setCustomButton(this.pnlDiagnostics, this.btnAddDiagnostic,
				this.defaultDimension, this.addIcon);
		this.btnAddDiagnostic.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                if ((patient != null) && (ClientHospital.conMan != null)) {
                    ClientHospital.conMan.showLabRecordForm(patient.getPatientId(),
                        patient.getSurname(), patient.getName(), patient.getMiddlename(),
                        patient.getGospitalCod());
                }
            }
        });

		this.btnDelDiagnostic = new JButton();
		this.setCustomButton(this.pnlDiagnostics, this.btnDelDiagnostic,
				this.defaultDimension, this.delIcon);
		this.btnDelDiagnostic.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            	//TODO
            }
        });
	}
	
	private void setButtonsDiet() {
		this.btnAddDiet = new JButton();
		this.setCustomButton(this.pnlDiet, this.btnAddDiet,
				this.defaultDimension, this.addIcon);
		this.btnAddDiet.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            	//TODO
            }
        });

		this.btnDelDiet = new JButton();
		this.setCustomButton(this.pnlDiet, this.btnDelDiet,
				this.defaultDimension, this.delIcon);
		this.btnDelDiet.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            	//TODO
            }
        });
	}
	
	private void setButtonsProcedures() {
		this.btnAddProcedure = new JButton();
		this.setCustomButton(this.pnlProcedures, this.btnAddProcedure,
				this.defaultDimension, this.addIcon);
		this.btnAddProcedure.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            	//TODO
            }
        });

		this.btnDelProcedure = new JButton();
		this.setCustomButton(this.pnlProcedures, this.btnDelProcedure,
				this.defaultDimension, this.delIcon);
		this.btnDelProcedure.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
            	//TODO
            }
        });
	}
}
