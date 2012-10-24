package ru.nkz.ivcgzo.clientViewSelect.modalForms;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

import ru.nkz.ivcgzo.clientManager.common.ModalForm;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.clientViewSelect.MainForm;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftViewSelect.PatientBriefInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientSearchParams;

public class PatientSearchForm extends ModalForm {
	private static final long serialVersionUID = -8340824528321653697L;
	private List<PatientBriefInfo> fullResults; 
	
	private static int heightWithOptionalParams = 340;
	private static int heightWithoutOptionalParams = 240;
	
	private JPanel pnlTextFields; 
	private JPanel pnlSearchParams;
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
	
	private static int resultsHeight = 242;
	private CustomTable<PatientBriefInfo, PatientBriefInfo._Fields> tblResults;
	private JPanel pnlResults;
	private JButton btnAcceptResults;
	private boolean legibleSearch;
	private JPanel pnlOptionalParams;
	
	/**
	 * Create the application.
	 */
	public PatientSearchForm() {
		super(true);
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		final EmptyParamsChecker epc = new EmptyParamsChecker();
		
		final KeyAdapter enterKeyListener = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					if (btnSearch.isEnabled())
						btnSearch.doClick();
				
				super.keyPressed(e);
			}
		};
		
//		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "keyPreviewEscape");
//		getRootPane().getActionMap().put("keyPreviewEscape", new AbstractAction("keyPreviewEscape") {
//			private static final long serialVersionUID = -3739828423153755300L;
//
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				closeForm();
//			}
//		});
		
		setMinimumSize(new Dimension(568, 640));
		setBounds(100, 100, 532, 640);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Поиск пациентов");
		
		pnlSearchParams = new JPanel();
		pnlSearchParams.setBorder(new TitledBorder(null, "Критерии поиска пациентов", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		pnlResults = new JPanel();
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
					.addComponent(pnlSearchParams, GroupLayout.PREFERRED_SIZE, heightWithOptionalParams, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlResults, GroupLayout.DEFAULT_SIZE, resultsHeight, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JScrollPane scpResults = new JScrollPane();
		
		btnAcceptResults = new JButton("Выбор");
		btnAcceptResults.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acceptResults();
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
		
		tblResults = new CustomTable<>(false, true, PatientBriefInfo.class, 1, "Фамилия", 2, "Имя", 3, "Отчество", 4, "Дата рождения", 5, "Серия полиса", 6, "Номер полиса");
		tblResults.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (btnAcceptResults.isEnabled())
					if (e.getClickCount() == 2)
						btnAcceptResults.doClick();
			}
		});
		tblResults.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					if (btnAcceptResults.isEnabled())
						btnAcceptResults.doClick();
				
				super.keyPressed(e);
			}
		});
		tblResults.setDateField(3);
		tblResults.setFillsViewportHeight(true);
		scpResults.setViewportView(tblResults);
		pnlResults.setLayout(gl_pnlResults);
		
		ActionListener illegibleListener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				tbBirDate2.setVisible(rbtIllegible.isSelected());
				pnlTextFields.revalidate();
				epc.changedUpdate(null);
			}
		};
		
		ButtonGroup bgSearchType = new ButtonGroup();
		
		pnlTextFields = new JPanel();
		
		btnSearch = new JButton("Поиск");
		btnSearch.setEnabled(false);
		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					fullResults = MainForm.tcl.searchPatient(createSearchParams());
					results = fullResults;
					btnAcceptResults.setEnabled(fullResults.size() > 0);
					tblResults.setData(fullResults);
					if (btnAcceptResults.isEnabled())
						tblResults.requestFocusInWindow();
					if (chbAutoClose.isEnabled() && chbAutoClose.isSelected()) {
						if (rbtManyPat.isSelected() && (fullResults.size() > 0))
							btnAcceptResults.doClick();
						else if (rbtOnePat.isSelected() && (fullResults.size() == 1))
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
				tbFam.requestFocusInWindow();
			}
		});
		
		chbAutoClose = new JCheckBox("Автоматически закрывать окно");
		
		pnlOptionalParams = new JPanel();
		GroupLayout gl_pnlSearchParams = new GroupLayout(pnlSearchParams);
		gl_pnlSearchParams.setHorizontalGroup(
			gl_pnlSearchParams.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlSearchParams.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlSearchParams.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_pnlSearchParams.createSequentialGroup()
							.addComponent(chbAutoClose, GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
							.addGap(18)
							.addComponent(btnClearFields, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnSearch, GroupLayout.PREFERRED_SIZE, 123, GroupLayout.PREFERRED_SIZE))
						.addComponent(pnlTextFields, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
						.addComponent(pnlOptionalParams, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_pnlSearchParams.setVerticalGroup(
			gl_pnlSearchParams.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlSearchParams.createSequentialGroup()
					.addContainerGap()
					.addComponent(pnlTextFields, GroupLayout.PREFERRED_SIZE, 161, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlOptionalParams, GroupLayout.DEFAULT_SIZE, 89, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlSearchParams.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnSearch, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
						.addComponent(btnClearFields, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(chbAutoClose, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JPanel gbPatientCount = new JPanel();
		ButtonGroup bgPatientCount = new ButtonGroup();
		
		rbtManyPat = new JRadioButton("Много пациентов");
		
		rbtOnePat = new JRadioButton("Один пациент");
		
		bgPatientCount.add(rbtManyPat);
		bgPatientCount.add(rbtOnePat);
		
		GroupLayout gl_gbPatientCount = new GroupLayout(gbPatientCount);
		gl_gbPatientCount.setHorizontalGroup(
			gl_gbPatientCount.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_gbPatientCount.createSequentialGroup()
					.addContainerGap()
					.addComponent(rbtOnePat, GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(rbtManyPat, GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_gbPatientCount.setVerticalGroup(
			gl_gbPatientCount.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_gbPatientCount.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_gbPatientCount.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbtManyPat)
						.addComponent(rbtOnePat))
					.addContainerGap(16, Short.MAX_VALUE))
		);
		gbPatientCount.setLayout(gl_gbPatientCount);
		rbtOnePat.doClick();
		
		JPanel gbSearchType = new JPanel();
		rbtLegible = new JRadioButton("Четкий поиск");
		rbtLegible.addActionListener(illegibleListener);
		
		rbtIllegible = new JRadioButton("Нечеткий поиск");
		rbtIllegible.addActionListener(illegibleListener);
		
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
		GroupLayout gl_panel = new GroupLayout(pnlOptionalParams);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(gbPatientCount, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
				.addComponent(gbSearchType, GroupLayout.DEFAULT_SIZE, 504, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(gbPatientCount, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(gbSearchType, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE))
		);
		pnlOptionalParams.setLayout(gl_panel);
		
		JLabel lblFam = new JLabel("Фамилия");
		
		tbFam = new CustomTextField();
		tbFam.getDocument().addDocumentListener(epc);
		tbFam.addKeyListener(enterKeyListener);
		tbFam.setColumns(10);
		
		JLabel lblIm = new JLabel("Имя");
		
		tbIm = new CustomTextField();
		tbIm.getDocument().addDocumentListener(epc);
		tbIm.addKeyListener(enterKeyListener);
		tbIm.setColumns(10);
		
		JLabel lblOt = new JLabel("Отчество");
		
		tbOt = new CustomTextField();
		tbOt.getDocument().addDocumentListener(epc);
		tbOt.addKeyListener(enterKeyListener);
		tbOt.setColumns(10);
		
		JLabel lblBirDate = new JLabel("Дата рождения");
		
		tbBirDate = new CustomDateEditor();
		tbBirDate.getDocument().addDocumentListener(epc);
		tbBirDate.addKeyListener(enterKeyListener);
		tbBirDate.setColumns(10);
		
		tbBirDate2 = new CustomDateEditor();
		tbBirDate2.getDocument().addDocumentListener(epc);
		tbBirDate2.addKeyListener(enterKeyListener);
		tbBirDate2.setVisible(false);
		tbBirDate2.setColumns(10);
		
		JLabel lblSerPol = new JLabel("Серия полиса");
		
		tbSerPol = new CustomTextField();
		tbSerPol.getDocument().addDocumentListener(epc);
		tbSerPol.addKeyListener(enterKeyListener);
		tbSerPol.setColumns(10);
		
		JLabel lblNumPol = new JLabel("Номер полиса");
		
		tbNumPol = new CustomTextField();
		tbNumPol.getDocument().addDocumentListener(epc);
		tbNumPol.setColumns(10);
		
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
		
		pnlSearchParams.setLayout(gl_pnlSearchParams);
		getContentPane().setLayout(groupLayout);
		
		rbtOnePat.doClick();
		rbtLegible.doClick();
		btnClearFields.doClick();
		setOptionalParamsEnabledState(true);
	}
	
	@Override
	public void acceptResults() {
		if (rbtOnePat.isSelected()) {
			List<PatientBriefInfo> tmpRes = new ArrayList<>();
			
			tmpRes.add(tblResults.getSelectedItem());
			results = tmpRes;
		} else {
			results = fullResults;
		}
		
		super.acceptResults();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<PatientBriefInfo> getResults() {
		if (results != null)
			return (List<PatientBriefInfo>) results;
		
		return null;
	}
	
	private PatientSearchParams createSearchParams() {
		PatientSearchParams params = new PatientSearchParams();
		
		if (!tbFam.isEmpty()) params.setFam(tbFam.getText().trim());
		if (!tbIm.isEmpty()) params.setIm(tbIm.getText().trim());
		if (!tbOt.isEmpty()) params.setOt(tbOt.getText().trim());
		if (!rbtIllegible.isSelected()) {
			params.fam += '%';
			params.im += '%';
			if (params.isSetOt())
				params.ot += '%';
		}
		if (!rbtIllegible.isSelected() && (tbBirDate.getDate() != null)) {
			params.setDatar(tbBirDate.getDate().getTime());
			params.unsetDatar2();
		} else if ((tbBirDate.getDate() != null) && (tbBirDate2.getDate() != null)) {
			params.setDatar(tbBirDate.getDate().getTime());
			params.setDatar2(tbBirDate2.getDate().getTime());
		}
		if (!tbSerPol.isEmpty()) params.setSpolis(tbSerPol.getText().trim());
		if (!tbNumPol.isEmpty()) params.setNpolis(tbNumPol.getText().trim());
		params.setManyPatients(rbtManyPat.isSelected());
		params.setIllegibleSearch(rbtIllegible.isSelected());
		
		return params;
	}
	
	@SuppressWarnings("unchecked")
	public List<PatientBriefInfo> getSearchResults() {
		return (List<PatientBriefInfo>) results;
	}
	
	public void clearFields() {
		tbFam.clear();
		tbIm.clear();
		tbOt.clear();
		tbBirDate.setValue(null);
		tbBirDate2.setValue(null);
		tbSerPol.clear();
		tbNumPol.clear();
		
		tblResults.setData(new ArrayList<PatientBriefInfo>());
		btnAcceptResults.setEnabled(false);
	}
	
	public void setOptionalParamsEnabledState(boolean enabled) {
		legibleSearch = !enabled;
		
		pnlOptionalParams.setVisible(enabled);
		chbAutoClose.setVisible(enabled);
		
		if (enabled == false) {
			rbtOnePat.doClick();
			rbtLegible.doClick();
		}
		
		if (getContentPane().getLayout() instanceof GroupLayout) {
			int height = (enabled) ? heightWithOptionalParams : heightWithoutOptionalParams;
			GroupLayout groupLayout = (GroupLayout) getContentPane().getLayout();
			
			groupLayout.setVerticalGroup(
					groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(pnlSearchParams, GroupLayout.PREFERRED_SIZE, height, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(pnlResults, GroupLayout.DEFAULT_SIZE, resultsHeight, Short.MAX_VALUE)
							.addContainerGap())
				);
			revalidate();
		}
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
			boolean disabled = !legibleSearch;
			
			if (legibleSearch) {
				disabled |= tbFam.getText().length() < 2;
				disabled |= tbIm.getText().length() < 2;
			} else {
				disabled &= tbFam.isEmpty();
				disabled &= tbIm.isEmpty();
				disabled &= tbOt.isEmpty();
				if (!rbtIllegible.isSelected())
					disabled &= tbBirDate.getDate() == null;
				else
					disabled &= (tbBirDate.getDate() == null) || (tbBirDate2.getDate() == null);
				disabled &= tbSerPol.isEmpty();
				disabled &= tbNumPol.isEmpty();
			}
			
			btnSearch.setEnabled(!disabled);
		}
	}
}
