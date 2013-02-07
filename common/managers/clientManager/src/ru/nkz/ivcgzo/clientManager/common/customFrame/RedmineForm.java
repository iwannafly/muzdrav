package ru.nkz.ivcgzo.clientManager.common.customFrame;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

import ru.nkz.ivcgzo.clientManager.common.redmineManager.Issue;
import ru.nkz.ivcgzo.clientManager.common.redmineManager.RedmineManager;
import ru.nkz.ivcgzo.clientManager.common.redmineManager.Tracker;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextComponentWrapper;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;

public class RedmineForm extends JDialog {
	private static final long serialVersionUID = 5470717605761333268L;
	private String redmineServerAddr;
	private RedmineManager rmg;
	private String prKey;
	private List<Issue> issLst;
	private List<Tracker> trcLst;
//	private List<IssueStatus> staLst;
	private JTable tblIssues;
	private JComboBox<Tracker> cmbTracker;
	private CustomTextField tbSubject;
	private JTextArea tbDesc;
	private JButton btnAdd;
	
	public RedmineForm(String redmineServerAddr) {
		this.redmineServerAddr = redmineServerAddr;
		
		setModalityType(ModalityType.APPLICATION_MODAL);
		setTitle("Просмотр/редактирование задач");
		setBounds(100, 100, 800, 600);
		getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
		
		Box vbIssues = Box.createVerticalBox();
		vbIssues.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(vbIssues);
		
		JScrollPane spIssues = new JScrollPane();
		spIssues.setAlignmentX(Component.LEFT_ALIGNMENT);
		vbIssues.add(spIssues);
		
		tblIssues = new JTable();
		tblIssues.setAlignmentX(Component.LEFT_ALIGNMENT);
		tblIssues.setFillsViewportHeight(true);
		spIssues.setViewportView(tblIssues);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		vbIssues.add(verticalStrut);
		
		Box hbParams = Box.createHorizontalBox();
		hbParams.setAlignmentX(Component.LEFT_ALIGNMENT);
		vbIssues.add(hbParams);
		
		JLabel lblTracker = new JLabel("Тип");
		hbParams.add(lblTracker);
		
		cmbTracker = new JComboBox<>();
		cmbTracker.setAlignmentX(Component.LEFT_ALIGNMENT);
		cmbTracker.setMaximumSize(new Dimension((int) cmbTracker.getMaximumSize().getWidth(), (int) cmbTracker.getMinimumSize().getHeight()));
		hbParams.add(cmbTracker);
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		hbParams.add(rigidArea);
		
		JLabel lblSubject = new JLabel("Тема");
		hbParams.add(lblSubject);
		
		DocumentListener changeListener = new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				checkParams();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				checkParams();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				checkParams();
			}
		};
		
		tbSubject = new CustomTextField(true, true, false);
		tbSubject.getDocument().addDocumentListener(changeListener);
		tbSubject.setAlignmentX(Component.LEFT_ALIGNMENT);
		tbSubject.setMaximumSize(new Dimension((int) tbSubject.getMaximumSize().getWidth(), (int) tbSubject.getMinimumSize().getHeight()));
		tbSubject.setColumns(10);
		hbParams.add(tbSubject);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		vbIssues.add(verticalStrut_1);
		
		Box vbDesc = Box.createVerticalBox();
		vbIssues.add(vbDesc);
		
		JLabel lblDesc = new JLabel("Описание");
		vbDesc.add(lblDesc);
		
		JScrollPane spDesc = new JScrollPane();
		spDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
		vbDesc.add(spDesc);
		
		tbDesc = new JTextArea();
		tbDesc.getDocument().addDocumentListener(changeListener);
		new CustomTextComponentWrapper(tbDesc).setPopupMenu();
		tbDesc.setAlignmentX(Component.LEFT_ALIGNMENT);
		spDesc.setViewportView(tbDesc);
		
		btnAdd = new JButton("Добавить");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					addIssue();
				} catch (Exception e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(RedmineForm.this, "Не удалось добавить задачу.");
				}
			}
		});
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		vbIssues.add(verticalStrut_2);
		btnAdd.setEnabled(false);
		vbIssues.add(btnAdd);
		
		issLst = new ArrayList<>();
		setTableModel();
	}
	
	public void setVisible(String prKey, boolean b) {
		this.prKey = prKey;
		setVisible(b);
	}
	
	@Override
	public void setVisible(boolean b) {
		if (b) {
			if (!((prKey != null) && (prKey.length() > 0))) {
				JOptionPane.showMessageDialog(this, "Не указан ключ проекта.");
				return;
			}
			try {
				redmineConnect();
			} catch (Throwable e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Не удалось подключиться к серверу.");
				return;
			}
			try {
				trcLst = rmg.getTrackerList();
				setComboBoxModel();
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Не удалось загрузить список типов.");
				return;
			}
//			try {
//				staLst = rmg.getIssueStatusList();
//			} catch (Exception e) {
//				e.printStackTrace();
//				JOptionPane.showMessageDialog(this, "Не удалось загрузить список статусов.");
//				return;
//			}
			try {
				issLst = getIssuesList();
				setTableModel();
				clearFields();
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Не удалось загрузить список задач.");
				return;
			}
		} else {
			prKey = null;
		}
		super.setVisible(b);
	}
	
	private void redmineConnect() {
		rmg = new RedmineManager(redmineServerAddr, "1646ce420d7a4651d474c6eaa273b8ff1e251f64");
	}
	
	private List<Issue> getIssuesList() throws Exception {
		return rmg.getIssues(prKey);
	}
	
	private void addIssue() throws Exception {
		issLst.add(0, rmg.addIssue(prKey, (Tracker) cmbTracker.getSelectedItem(), tbSubject.getText(), tbDesc.getText()));
		setTableModel();
		clearFields();
	}
	
//TODO fix later
//	private com.taskadapter.redmineapi.bean.Attachment uploadScreenShot() {
//		com.taskadapter.redmineapi.bean.Attachment res = null;
//		
//		try {
//			BufferedImage rawImgStr = new Robot().createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
//			ByteArrayOutputStream outPngStr = new ByteArrayOutputStream();
//			ImageIO.write(rawImgStr, "png", outPngStr);
//			ByteArrayInputStream inpPngStr = new ByteArrayInputStream(outPngStr.toByteArray());
//			outPngStr.close();
//			res = rmg1.uploadAttachment("alala.png", "application/octet-stream", inpPngStr);
//			inpPngStr.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		
//		return res;
//	}
	
	private void setTableModel() {
		tblIssues.setModel(new TableModel() {
			private final String[] colNames = new String[] {"Тип", "Статус", "Тема", "Описание", "Дата обновления", "Комментарии"};
			private final Class<?>[] colClasses = new Class<?>[] {String.class, String.class, String.class, String.class, Date.class, String.class};
			
			@Override
			public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			}
			
			@Override
			public void removeTableModelListener(TableModelListener l) {
			}
			
			@Override
			public boolean isCellEditable(int rowIndex, int columnIndex) {
				return false;
			}
			
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				Issue iss = issLst.get(rowIndex);
				
				switch (columnIndex) {
				case 0:
					return iss.getTracker().getName();
				case 1:
					return iss.getStatus().getName();
				case 2:
					return iss.getSubject();
				case 3:
					return iss.getDescription();
				case 4:
					return iss.getUpdatedOn();
//				case 5:
//					return iss.getNotes();
				default:
					return null;
				}
			}
			
			@Override
			public int getRowCount() {
				return issLst.size();
			}
			
			@Override
			public String getColumnName(int columnIndex) {
				return colNames[columnIndex];
			}
			
			@Override
			public int getColumnCount() {
				return colNames.length;
			}
			
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return colClasses[columnIndex];
			}
			
			@Override
			public void addTableModelListener(TableModelListener l) {
			}
		});
		
		if (tblIssues.getRowCount() > 0)
			tblIssues.setRowSelectionInterval(0, 0);
	}
	
	private void setComboBoxModel() {
		cmbTracker.setModel(new DefaultComboBoxModel<Tracker>() {
			private static final long serialVersionUID = 571106359560166547L;

			@Override
			public int getSize() {
				return trcLst.size();
			}
			
			@Override
			public Tracker getElementAt(int index) {
				return trcLst.get(index);
			}
		});
		
		if (cmbTracker.getItemCount() > 0)
			cmbTracker.setSelectedIndex(0);
	}
	
	private void clearFields() {
		cmbTracker.setSelectedIndex(0);
		tbSubject.setText(null);
		tbDesc.setText(null);
	}
	
	private void checkParams() {
		boolean res;
		
		res = CustomFrame.authInfo != null;
		res &= tbSubject.getText().length() > 0;
		res &= tbDesc.getText().length() > 0;
		
		btnAdd.setEnabled(res);
	}
}
