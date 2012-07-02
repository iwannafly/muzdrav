package ru.nkz.ivcgzo.clientViewSelect;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftViewSelect.PatientBriefInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientSearchParams;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PatientSearchForm extends JFrame {
	private static final long serialVersionUID = -8340824528321653697L;
	private List<PatientBriefInfo> results; 
	
	private CustomTextField tbFam;
	private CustomTextField tbIm;
	private CustomTextField tbOt;
	private CustomDateEditor tbBirDate;
	private CustomDateEditor tbBirDate2;
	private CustomTextField tbSerPol;
	private CustomTextField tbNumPol;
	private JRadioButton rbtManyPat;
	private JRadioButton rbtOnePat;
	private JRadioButton rbtLegible;
	private JRadioButton rbtIllegible;
	private JCheckBox chbAutoClose;
	private JButton btnClearFields;
	private JButton btnSearch;
	
	private CustomTable<PatientBriefInfo, PatientBriefInfo._Fields> tblResults;
	private JButton btnAcceptResults;
	protected boolean resultsAccepted;
	
	/**
	 * Create the application.
	 */
	public PatientSearchForm() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (!resultsAccepted)
					results = null;
				
				resultsAccepted = false;
			}
		});
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		final EmptyParamsChecker epc = new EmptyParamsChecker();
		
		setMinimumSize(new Dimension(568, 640));
		setBounds(100, 100, 568, 640);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel pnlSearchParams = new JPanel();
		pnlSearchParams.setBorder(new TitledBorder(null, "Критерии поиска пациентов", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel pnlResults = new JPanel();
		pnlResults.setBorder(new TitledBorder(null, "Результаты", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(pnlResults, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
						.addComponent(pnlSearchParams, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(pnlSearchParams, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(pnlResults, GroupLayout.DEFAULT_SIZE, 242, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JScrollPane scpResults = new JScrollPane();
		
		btnAcceptResults = new JButton("Принять");
		btnAcceptResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rbtOnePat.isSelected()) {
					results = new ArrayList<>();
					results.add(tblResults.getSelectedItem());
				}
				
				resultsAccepted = true;
				PatientSearchForm.this.dispatchEvent(new WindowEvent(PatientSearchForm.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		btnAcceptResults.setEnabled(false);
		
		GroupLayout gl_pnlResults = new GroupLayout(pnlResults);
		gl_pnlResults.setHorizontalGroup(
			gl_pnlResults.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlResults.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlResults.createParallelGroup(Alignment.LEADING)
						.addComponent(scpResults, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE)
						.addComponent(btnAcceptResults, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		gl_pnlResults.setVerticalGroup(
			gl_pnlResults.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlResults.createSequentialGroup()
					.addContainerGap()
					.addComponent(scpResults, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnAcceptResults)
					.addContainerGap())
		);
		
		tblResults = new CustomTable<>(false, true, PatientBriefInfo.class, 1, "Фамилия", 2, "Имя", 3, "Отчество", 4, "Дата рождения", 5, "Серия", 6, "Номер");
		tblResults.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnAcceptResults.isEnabled())
					if (e.getClickCount() == 2)
						btnAcceptResults.doClick();
			}
		});
		tblResults.setDateField(3);
		tblResults.setFillsViewportHeight(true);
		scpResults.setViewportView(tblResults);
		pnlResults.setLayout(gl_pnlResults);
		
		JPanel gbPatientCount = new JPanel();
		
		JPanel gbSearchType = new JPanel();
		
		ActionListener illegibleListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tbBirDate2.setVisible(rbtIllegible.isSelected());
				PatientSearchForm.this.revalidate();
				epc.changedUpdate(null);
			}
		};
		rbtLegible = new JRadioButton("Четкий поиск");
		rbtLegible.addActionListener(illegibleListener);
		
		rbtIllegible = new JRadioButton("Нечеткий поиск");
		rbtIllegible.addActionListener(illegibleListener);
		
		ButtonGroup bgSearchType = new ButtonGroup();
		
		bgSearchType.add(rbtLegible);
		bgSearchType.add(rbtIllegible);
		
		GroupLayout gl_gbSearchType = new GroupLayout(gbSearchType);
		gl_gbSearchType.setHorizontalGroup(
			gl_gbSearchType.createParallelGroup(Alignment.TRAILING)
				.addGap(0, 386, Short.MAX_VALUE)
				.addGroup(gl_gbSearchType.createSequentialGroup()
					.addContainerGap()
					.addComponent(rbtLegible, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(rbtIllegible, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_gbSearchType.setVerticalGroup(
			gl_gbSearchType.createParallelGroup(Alignment.LEADING)
				.addGap(0, 46, Short.MAX_VALUE)
				.addGroup(gl_gbSearchType.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_gbSearchType.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtLegible)
						.addComponent(rbtIllegible))
					.addContainerGap(25, Short.MAX_VALUE))
		);
		gbSearchType.setLayout(gl_gbSearchType);
		
		JPanel pnlTextFields = new JPanel();
		
		btnSearch = new JButton("Поиск");
		btnSearch.setEnabled(false);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					results = MainForm.tcl.searchPatient(createSearchParams());
					btnAcceptResults.setEnabled(results.size() > 0);
					tblResults.setData(results);
					if (chbAutoClose.isSelected()) {
						if (rbtManyPat.isSelected() && (results.size() > 0))
							btnAcceptResults.doClick();
						else if (rbtOnePat.isSelected() && (results.size() == 1))
							btnAcceptResults.doClick();
					}
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				}
			}
		});
		
		btnClearFields = new JButton("Очистить поля");
		btnClearFields.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				clearFields();
			}
		});
		
		chbAutoClose = new JCheckBox("Автоматически закрывать окно");
		GroupLayout gl_pnlSearchParams = new GroupLayout(pnlSearchParams);
		gl_pnlSearchParams.setHorizontalGroup(
			gl_pnlSearchParams.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlSearchParams.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlSearchParams.createParallelGroup(Alignment.TRAILING)
						.addComponent(gbSearchType, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
						.addComponent(gbPatientCount, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
						.addGroup(gl_pnlSearchParams.createSequentialGroup()
							.addComponent(chbAutoClose, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnClearFields, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))
						.addComponent(pnlTextFields, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_pnlSearchParams.setVerticalGroup(
			gl_pnlSearchParams.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlSearchParams.createSequentialGroup()
					.addContainerGap()
					.addComponent(pnlTextFields, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(gbPatientCount, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(gbSearchType, GroupLayout.DEFAULT_SIZE, 37, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlSearchParams.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSearch, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
						.addComponent(btnClearFields, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(chbAutoClose, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		tbFam = new CustomTextField();
		tbFam.getDocument().addDocumentListener(epc);
		tbFam.setColumns(10);
		
		JLabel lblFam = new JLabel("Фамилия");
		
		tbIm = new CustomTextField();
		tbIm.getDocument().addDocumentListener(epc);
		tbIm.setColumns(10);
		
		tbOt = new CustomTextField();
		tbOt.getDocument().addDocumentListener(epc);
		tbOt.setColumns(10);
		
		tbBirDate = new CustomDateEditor();
		tbBirDate.getDocument().addDocumentListener(epc);
		tbBirDate.setColumns(10);
		
		tbSerPol = new CustomTextField();
		tbSerPol.getDocument().addDocumentListener(epc);
		tbSerPol.setColumns(10);
		
		tbNumPol = new CustomTextField();
		tbNumPol.getDocument().addDocumentListener(epc);
		tbNumPol.setColumns(10);
		
		JLabel lblIm = new JLabel("Имя");
		
		JLabel lblOt = new JLabel("Отчество");
		
		JLabel lblBirDate = new JLabel("Дата рождения");
		
		JLabel lblSerPol = new JLabel("Серия полиса");
		
		JLabel lblNumPol = new JLabel("Номер полиса");
		
		tbBirDate2 = new CustomDateEditor();
		tbBirDate2.getDocument().addDocumentListener(epc);
		tbBirDate2.setVisible(false);
		tbBirDate2.setColumns(10);
		GroupLayout gl_pnlTextFields = new GroupLayout(pnlTextFields);
		gl_pnlTextFields.setHorizontalGroup(
			gl_pnlTextFields.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlTextFields.createSequentialGroup()
					.addGroup(gl_pnlTextFields.createParallelGroup(Alignment.LEADING, false)
						.addComponent(lblFam, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblIm, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
						.addComponent(lblOt, GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
						.addComponent(lblBirDate, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblSerPol, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblNumPol, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(gl_pnlTextFields.createParallelGroup(Alignment.LEADING)
						.addComponent(tbNumPol, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
						.addComponent(tbSerPol, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
						.addComponent(tbFam, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
						.addComponent(tbIm, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
						.addComponent(tbOt, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_pnlTextFields.createSequentialGroup()
							.addComponent(tbBirDate, GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tbBirDate2, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))))
		);
		gl_pnlTextFields.setVerticalGroup(
			gl_pnlTextFields.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlTextFields.createSequentialGroup()
					.addGroup(gl_pnlTextFields.createParallelGroup(Alignment.BASELINE)
						.addComponent(tbFam, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFam))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlTextFields.createParallelGroup(Alignment.BASELINE)
						.addComponent(tbIm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblIm))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlTextFields.createParallelGroup(Alignment.BASELINE)
						.addComponent(tbOt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblOt))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlTextFields.createParallelGroup(Alignment.BASELINE)
						.addComponent(tbBirDate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblBirDate)
						.addComponent(tbBirDate2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlTextFields.createParallelGroup(Alignment.BASELINE)
						.addComponent(tbSerPol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSerPol))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlTextFields.createParallelGroup(Alignment.BASELINE)
						.addComponent(tbNumPol, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNumPol))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlTextFields.setLayout(gl_pnlTextFields);
		
		rbtManyPat = new JRadioButton("Много пациентов");
		
		rbtOnePat = new JRadioButton("Один пациент");
		
		ButtonGroup bgPatientCount = new ButtonGroup();
		
		bgPatientCount.add(rbtManyPat);
		bgPatientCount.add(rbtOnePat);
		
		GroupLayout gl_gbPatientCount = new GroupLayout(gbPatientCount);
		gl_gbPatientCount.setHorizontalGroup(
			gl_gbPatientCount.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_gbPatientCount.createSequentialGroup()
					.addContainerGap()
					.addComponent(rbtManyPat, GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(rbtOnePat, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_gbPatientCount.setVerticalGroup(
			gl_gbPatientCount.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_gbPatientCount.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_gbPatientCount.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtManyPat)
						.addComponent(rbtOnePat))
					.addContainerGap(25, Short.MAX_VALUE))
		);
		gbPatientCount.setLayout(gl_gbPatientCount);
		pnlSearchParams.setLayout(gl_pnlSearchParams);
		getContentPane().setLayout(groupLayout);
		
		btnClearFields.doClick();
		rbtManyPat.doClick();
		rbtLegible.doClick();
	}
	
	private PatientSearchParams createSearchParams() {
		PatientSearchParams params = new PatientSearchParams();
		
		if (!tbFam.isEmpty()) params.setFam(tbFam.getText());
		if (!tbIm.isEmpty()) params.setIm(tbIm.getText());
		if (!tbOt.isEmpty()) params.setOt(tbOt.getText());
		if (!rbtIllegible.isSelected() && (tbBirDate.getDate() != null)) {
			params.setDatar(tbBirDate.getDate().getTime());
			params.unsetDatar2();
		} else if ((tbBirDate.getDate() != null) && (tbBirDate2.getDate() != null)) {
			params.setDatar(tbBirDate.getDate().getTime());
			params.setDatar2(tbBirDate2.getDate().getTime());
		}
		if (!tbSerPol.isEmpty()) params.setSpolis(tbSerPol.getText());
		if (!tbNumPol.isEmpty()) params.setNpolis(tbNumPol.getText());
		params.setManyPatients(rbtManyPat.isSelected());
		params.setIllegibleSearch(rbtIllegible.isSelected());
		
		return params;
	}
	
	public List<PatientBriefInfo> getSearchResults() {
		return results;
	}
	
	private void clearFields() {
		tbFam.clear();
		tbIm.clear();
		tbOt.clear();
		tbBirDate.setDate(null);
		tbBirDate2.setDate(null);
		tbSerPol.clear();
		tbNumPol.clear();
	}
	
	class EmptyParamsChecker implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {
			setBtnSearchEnabled();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			setBtnSearchEnabled();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			setBtnSearchEnabled();
		}
		
		private void setBtnSearchEnabled() {
			boolean disabled = true;
			
			disabled &= tbFam.isEmpty();
			disabled &= tbIm.isEmpty();
			disabled &= tbOt.isEmpty();
			if (!rbtIllegible.isSelected())
				disabled &= tbBirDate.getDate() == null;
			else
				disabled &= (tbBirDate.getDate() == null) || (tbBirDate2.getDate() == null);
			disabled &= tbSerPol.isEmpty();
			disabled &= tbNumPol.isEmpty();
			
			btnSearch.setEnabled(!disabled);
		}
	}
}
