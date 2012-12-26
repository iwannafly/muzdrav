package ru.nkz.ivcgzo.clientViewSelect.modalForms;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.ModalForm;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientViewSelect.MainForm;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftViewSelect.MedPolErrorInfo;

public class MedPolErrorsForm extends ModalForm {
	private static final long serialVersionUID = 3871310888472373862L;
	private CustomTable<MedPolErrorInfo, MedPolErrorInfo._Fields> tblErr;
	private CustomDateEditor tbSrcFrom;
	private CustomDateEditor tbSrcTo;
	private JButton btnCorrect;
	private JButton btnSrc;
	private JScrollPane spDesc;
	private JLabel lblDesc;
	
	public MedPolErrorsForm() {
		super(true);
		
		setTitle("Ошибки медицинской части поликлиники");
		setBounds(100, 100, 800, 600);
		
		JPanel pnlErr = new JPanel();
		pnlErr.setBorder(new TitledBorder(null, "Ошибки", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel pnlDesc = new JPanel();
		pnlDesc.setBorder(new TitledBorder(null, "Описание", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		btnCorrect = new JButton("Правка");
		btnCorrect.setEnabled(false);
		btnCorrect.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				acceptResults();
			}
		});
		
		JPanel pnlSrcParams = new JPanel();
		pnlSrcParams.setBorder(new TitledBorder(null, "Параметры", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(pnlErr, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(pnlDesc, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnCorrect, GroupLayout.PREFERRED_SIZE, 126, GroupLayout.PREFERRED_SIZE))
						.addComponent(pnlSrcParams, GroupLayout.DEFAULT_SIZE, 586, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(pnlSrcParams, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(pnlErr, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(btnCorrect)
						.addComponent(pnlDesc, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		DocumentListener searchEnabledListener = new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				setSearchEnabled();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				setSearchEnabled();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				setSearchEnabled();
			}
		};
		KeyAdapter searchStartAdapter = new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					if (btnSrc.isEnabled())
						btnSrc.doClick();
			}
		}; 
		
		tbSrcFrom = new CustomDateEditor();
		tbSrcFrom.addKeyListener(searchStartAdapter);
		tbSrcFrom.setDate(System.currentTimeMillis() - 31*86400000L);
		tbSrcFrom.setColumns(10);
		
		tbSrcTo = new CustomDateEditor();
		tbSrcTo.addKeyListener(searchStartAdapter);
		tbSrcTo.setDate(System.currentTimeMillis());
		tbSrcTo.setColumns(10);
		
		tbSrcFrom.getDocument().addDocumentListener(searchEnabledListener);
		tbSrcTo.getDocument().addDocumentListener(searchEnabledListener);
		
		JLabel lblSrcFrom = new JLabel("С");
		
		JLabel lblSrcTo = new JLabel("по");
		
		btnSrc = new JButton("Поиск");
		btnSrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					lblDesc.setText("");
					tblErr.setData(MainForm.tcl.getMedPolErrors(MainForm.authInfo.cpodr, tbSrcFrom.getTime(), tbSrcTo.getTime()));
					btnCorrect.setEnabled(tblErr.getRowCount() > 0);
					if (btnCorrect.isEnabled()) {
						tblErr.requestFocusInWindow();
					} else {
						tbSrcFrom.requestFocusInWindow();
					}
				} catch (KmiacServerException e1) {
					JOptionPane.showMessageDialog(MedPolErrorsForm.this, "Не удалось получить результаты поиска.", "Ошибка", JOptionPane.ERROR_MESSAGE);
				} catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				}
			}
		});
		GroupLayout gl_pnlSrcParams = new GroupLayout(pnlSrcParams);
		gl_pnlSrcParams.setHorizontalGroup(
			gl_pnlSrcParams.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlSrcParams.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblSrcFrom, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tbSrcFrom, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblSrcTo, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tbSrcTo, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, 150, Short.MAX_VALUE)
					.addComponent(btnSrc, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
		);
		gl_pnlSrcParams.setVerticalGroup(
			gl_pnlSrcParams.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlSrcParams.createSequentialGroup()
					.addGroup(gl_pnlSrcParams.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSrcFrom)
						.addComponent(tbSrcFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSrcTo)
						.addComponent(tbSrcTo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSrc))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlSrcParams.setLayout(gl_pnlSrcParams);
		pnlDesc.setLayout(new BoxLayout(pnlDesc, BoxLayout.X_AXIS));
		
		spDesc = new JScrollPane();
		pnlDesc.add(spDesc);
		
		lblDesc = new JLabel("");
		lblDesc.setVerticalAlignment(SwingConstants.TOP);
		spDesc.setViewportView(lblDesc);
		pnlErr.setLayout(new BoxLayout(pnlErr, BoxLayout.X_AXIS));
		
		JScrollPane spErr = new JScrollPane();
		pnlErr.add(spErr);
		
		tblErr = new CustomTable<>(false, true, MedPolErrorInfo.class, 3, "Дата обр.", 4, "Дата пос.", 9, "Номер пациента", 10, "ФИО пациента", 11, "ДР пациента", 6, "ФИО врача", 8, "Должность врача", 12, "Код ошибки");
		tblErr.setDateField(0);
		tblErr.setDateField(1);
		tblErr.setDateField(4);
		tblErr.setFillsViewportHeight(true);
		tblErr.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				if (!e.getValueIsAdjusting() && (tblErr.getSelectedItem() != null))
					lblDesc.setText("<html>" + tblErr.getSelectedItem().err_name + "<br>" + tblErr.getSelectedItem().err_comm + "</html>");
			}
		});
		tblErr.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					acceptResults();
			}
		});
		tblErr.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					acceptResults();
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					declineResults();
			}
		});
		spErr.setViewportView(tblErr);
		getContentPane().setLayout(groupLayout);
	}
	
	private void setSearchEnabled() {
		boolean enabled = true;
		
		enabled &= (tbSrcFrom.getDate() != null) && (tbSrcTo.getDate() != null);
		if (enabled)
			enabled &= (tbSrcTo.getDate().getTime() - tbSrcFrom.getDate().getTime() >= 0) && (tbSrcTo.getDate().getTime() - tbSrcFrom.getDate().getTime() < 365*86400000L);
		btnSrc.setEnabled(enabled);
	}

	@Override
	public void acceptResults() {
		if (tblErr.getSelectedItem() != null) {
			int[] res = new int[3];
			res[0] = tblErr.getSelectedItem().npasp;
			res[1] = tblErr.getSelectedItem().id_obr;
			res[2] = tblErr.getSelectedItem().id_pos;
			
			results = res;
		} else {
			return;
		}
		
		super.acceptResults();
	}
	
	@Override
	public Object getResults() {
		if (results != null) {
			return results;
		}
		return null;
	}
}
