package ru.nkz.ivcgzo.clientAdmin;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.EventObject;

import javax.swing.AbstractCellEditor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTableItemChangeEvent;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTableItemChangeEventListener;
import ru.nkz.ivcgzo.clientManager.common.swing.TableComboBoxIntegerEditor;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftServerAdmin.MestoRab;
import ru.nkz.ivcgzo.thriftServerAdmin.VrachExistsException;
import ru.nkz.ivcgzo.thriftServerAdmin.VrachInfo;

public class UserPanel extends JPanel {
	private static final long serialVersionUID = 4036696963713695558L;
	private CustomTable<VrachInfo, VrachInfo._Fields> tblVrach;
	private VrachInfo prevSelVrachItem;
	private CustomTable<MestoRab, MestoRab._Fields> tblMrab;
	private PermForm permForm;
	private JButton btnVrPerm;
	private JButton btnMrAdd;
	private JButton btnMrDel;
	
	public UserPanel() {
		super();
		
		JScrollPane spVrach = new JScrollPane();
		tblVrach = new  CustomTable<>(true, true, VrachInfo.class, 1, "Фамилия", 2, "Имя", 3, "Отчество", 4, "Пол", 5, "Дата рождения", 6, "Образование", 7, "СНИЛС", 8, "Диплом");
		tblVrach.setDateField(4);
		tblVrach.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				try {
					if (!arg0.getValueIsAdjusting()) {
						if (tblVrach.getSelectedItem() != null) {
							tblMrab.setData(MainForm.tcl.GetMrabList(tblVrach.getSelectedItem().pcod, MainForm.authInfo.clpu));
							prevSelVrachItem = tblVrach.getSelectedItem();
							btnVrPerm.setEnabled(tblMrab.getRowCount() > 0);
							btnMrDel.setEnabled(btnVrPerm.isEnabled());
						}
					}
				} catch (TException e) {
					e.printStackTrace();
				}			
			}
		});
		tblVrach.registerDeleteSelectedRowListener(new CustomTableItemChangeEventListener<VrachInfo>() {
			@Override
			public boolean doAction(CustomTableItemChangeEvent<VrachInfo> event) {
				try {
					MainForm.tcl.DelVrach(event.getItem().pcod, MainForm.authInfo.clpu);
					return true;
				} catch (TTransportException e) {
					MainForm.conMan.reconnect(e);
					return false;
				} catch (Exception e) {
					return false;
				}
			}
		});
		tblVrach.registerUpdateSelectedRowListener(new CustomTableItemChangeEventListener<VrachInfo>() {
			@Override
			public boolean doAction(CustomTableItemChangeEvent<VrachInfo> event) {
				try {
					MainForm.tcl.UpdVrach(event.getItem());
					return true;
				} catch (VrachExistsException e) {
					JOptionPane.showMessageDialog(UserPanel.this, "Врач с такими данными уже существует.", "Ошибка", JOptionPane.ERROR_MESSAGE);
					return false;
				} catch (TTransportException e) {
					MainForm.conMan.reconnect(e);
					return false;
				} catch (Exception e) {
					return false;
				}
			}
		});
		tblVrach.registerAddRowListener(new CustomTableItemChangeEventListener<VrachInfo>() {
			@Override
			public boolean doAction(CustomTableItemChangeEvent<VrachInfo> event) {
				try {
					event.getItem().setPcod(MainForm.tcl.AddVrach(event.getItem()));
					int cnt = 0;
					for (int i = 0; i < tblVrach.getData().size(); i++) {
						if ((tblVrach.getData().get(i).pcod == event.getItem().pcod) && (++cnt == 2))
						{
							tblVrach.getData().remove(i);
							tblVrach.getModel().fireTableRowsDeleted(i, i);
							break;
						}
					}
					return true;
				} catch (TTransportException e) {
					MainForm.conMan.reconnect(e);
					return false;
				} catch (Exception e) {
					return false;
				}
			}
		});
		tblVrach.setFillsViewportHeight(true);
		
		JButton btnVrAdd = new JButton();
		btnVrAdd.setToolTipText("Добавить пользователя");
		btnVrAdd.setIcon(new ImageIcon(PermForm.class.getResource("/ru/nkz/ivcgzo/clientAdmin/resources/1331789242_Add.png")));
		btnVrAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tblVrach.requestFocusInWindow();
				tblVrach.addItem();
			}
		});
		btnVrAdd.setEnabled(tblVrach.isEditable());
		
		JButton btnVrDel = new JButton();
		btnVrDel.setToolTipText("Удалить пользователя");
		btnVrDel.setIcon(new ImageIcon(PermForm.class.getResource("/ru/nkz/ivcgzo/clientAdmin/resources/1331789259_Delete.png")));
		btnVrDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(UserPanel.this, "Удалить пользователя? Так же произойдет удаление информации о профилях и учетных записях.", "Подтверждение", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
					tblVrach.deleteSelectedRow();
			}
		});
		btnVrDel.setEnabled(tblVrach.isEditable());
		
		JScrollPane spMrab = new JScrollPane();
		tblMrab = new CustomTable<>(true, true, MestoRab.class, 3, "Вид службы", 4, "Подразделение", 5, "Должность", 6, "Увольнение", 7, "Медперсонал");
		tblMrab.setDateField(3);
		tblMrab.registerDeleteSelectedRowListener(new CustomTableItemChangeEventListener<MestoRab>() {
			@Override
			public boolean doAction(CustomTableItemChangeEvent<MestoRab> event) {
				try {
					MainForm.tcl.DelMrab(event.getItem());
					btnVrPerm.setEnabled(tblMrab.getRowCount() > 1);
					btnMrDel.setEnabled(btnVrPerm.isEnabled());
					return true;
				} catch (TTransportException e) {
					MainForm.conMan.reconnect(e);
					return false;
				} catch (Exception e) {
					return false;
				}
			}
		});
		tblMrab.registerUpdateSelectedRowListener(new CustomTableItemChangeEventListener<MestoRab>() {
			@Override
			public boolean doAction(CustomTableItemChangeEvent<MestoRab> event) {
				try {
					MainForm.tcl.UpdMrab(event.getItem());
					return true;
				} catch (TTransportException e) {
					MainForm.conMan.reconnect(e);
					return false;
				} catch (Exception e) {
					return false;
				}
			}
		});
		tblMrab.registerAddRowListener(new CustomTableItemChangeEventListener<MestoRab>() {
			@Override
			public boolean doAction(CustomTableItemChangeEvent<MestoRab> event) {
				try {
					MestoRab item = event.getItem();
					item.setPcod(prevSelVrachItem.getPcod());
					item.setClpu(MainForm.authInfo.getClpu());
					item.id = MainForm.tcl.AddMrab(item);
					btnVrPerm.setEnabled(true);
					btnMrDel.setEnabled(btnVrPerm.isEnabled());
					return true;
				} catch (TTransportException e) {
					MainForm.conMan.reconnect(e);
					return false;
				} catch (Exception e) {
					return false;
				}
			}
		});
		tblMrab.setFillsViewportHeight(true);
		
		btnVrPerm = new JButton();
		btnVrPerm.setToolTipText("Права доступа");
		btnVrPerm.setIcon(new ImageIcon(PermForm.class.getResource("/ru/nkz/ivcgzo/clientAdmin/resources/1331789257_User.png")));
		btnVrPerm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					MainForm.tcl.testConnection();
					permForm.setLocationRelativeTo(UserPanel.this);
					permForm.showWindow(tblVrach.getSelectedItem(), tblMrab.getSelectedItem(), tblMrab.getSelectedItem().user_id == MainForm.authInfo.user_id);
				} catch (TTransportException e1) {
					MainForm.conMan.reconnect(e1);
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnMrAdd = new JButton();
		btnMrAdd.setToolTipText("Добавить профиль");
		btnMrAdd.setIcon(new ImageIcon(PermForm.class.getResource("/ru/nkz/ivcgzo/clientAdmin/resources/1331789242_Add.png")));
		btnMrAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tblVrach.updateSelectedItem();
				tblMrab.requestFocus();
				tblMrab.addItem();
			}
		});
		btnMrAdd.setEnabled(tblMrab.isEditable());
		
		btnMrDel = new JButton();
		btnMrDel.setToolTipText("Удалить профиль");
		btnMrDel.setIcon(new ImageIcon(PermForm.class.getResource("/ru/nkz/ivcgzo/clientAdmin/resources/1331789259_Delete.png")));
		btnMrDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(UserPanel.this, "Удалить профиль? Так же произойдет удаление информации об учетных записях.", "Подтверждение", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
					tblMrab.deleteSelectedRow();
			}
		});
		btnMrDel.setEnabled(tblMrab.isEditable());
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(spMrab, GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
						.addComponent(spVrach))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnVrPerm, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnVrAdd, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnVrDel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnMrDel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnMrAdd, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnVrDel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnVrAdd))
						.addComponent(spVrach, GroupLayout.DEFAULT_SIZE, 72, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnVrPerm)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnMrDel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnMrAdd))
						.addComponent(spMrab, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE))
					.addGap(10))
		);
		
		spMrab.setViewportView(tblMrab);
		spVrach.setViewportView(tblVrach);
		this.setLayout(groupLayout);
		
		permForm = new PermForm();
	}
	
	public void onConnect() throws TException {
		updateVrachTable();
		tblVrach.setIntegerClassifierSelector(3, MainForm.conMan.getIntegerClassifier(IntegerClassifiers.n_z30));
		tblVrach.setStringClassifierSelector(5, MainForm.tcl.get_n_z00());
		
		tblMrab.setIntegerClassifierSelector(0, MainForm.tcl.get_n_p0s15());
		new TableVidSluComboBoxEditorRenderer(tblMrab, 0, 1);
		tblMrab.setIntegerClassifierSelector(4, MainForm.conMan.getIntegerClassifier(IntegerClassifiers.n_priznd));
		tblMrab.setStringClassifierSelector(2, MainForm.conMan.getStringClassifier(StringClassifiers.n_s00));
	}

	public void updateVrachTable() {
		try {
			if (MainForm.tcl != null)
				tblVrach.setData(MainForm.tcl.GetVrachList(MainForm.authInfo.clpu));
		} catch (TException e) {
			MainForm.conMan.reconnect(e);
		}
	}
	
	class TableVidSluComboBoxEditorRenderer extends AbstractCellEditor implements TableCellEditor, TableCellRenderer {
		private static final long serialVersionUID = -5851458127435781104L;
		private static final int sluCount = 6;
		private int sluColIdx;
		private int colIdx;
		private CustomTable<MestoRab, MestoRab._Fields> table;
		private TableComboBoxIntegerEditor[] cmbs;
		private TableComboBoxIntegerEditor curCmb;

		public TableVidSluComboBoxEditorRenderer(CustomTable<MestoRab, MestoRab._Fields> table, int sluColIdx, int colIdx) throws TException {
			this.sluColIdx = sluColIdx;
			this.colIdx = colIdx;
			this.table = table;
			
			cmbs = new TableComboBoxIntegerEditor[sluCount];
			
			cmbs[0] = new TableComboBoxIntegerEditor(null, true, new ArrayList<IntegerClassifier>());
			cmbs[1] = new TableComboBoxIntegerEditor(null, true, MainForm.tcl.get_n_o00(MainForm.authInfo.clpu));
			cmbs[2] = new TableComboBoxIntegerEditor(null, true, MainForm.tcl.get_n_n00(MainForm.authInfo.clpu));
			cmbs[3] = new TableComboBoxIntegerEditor(null, true, MainForm.tcl.get_n_lds(MainForm.authInfo.clpu));
			cmbs[4] = new TableComboBoxIntegerEditor(IntegerClassifiers.n_t40, true, null);
			cmbs[5] = new TableComboBoxIntegerEditor(IntegerClassifiers.n_t40, true, null);
			
			resetColumnModel();
		}
		
		public void resetColumnModel() {
			table.getColumnModel().getColumn(colIdx).setCellEditor(this);
			table.getColumnModel().getColumn(colIdx).setCellRenderer(this);
		}
		
		@Override
		public Object getCellEditorValue() {
			return curCmb.getCellEditorValue();
		}
		
		@Override
		public boolean isCellEditable(EventObject e) {
			return curCmb.isCellEditable(e);
		}
		
		@Override
		public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
			curCmb = cmbs[(int) this.table.getValueAt(row, sluColIdx)];
			
			return curCmb.getTableCellEditorComponent(table, value, isSelected, row, column);
		}
		
		@Override
		public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
			Object sluValue = this.table.getValueAt(row, sluColIdx);
			
			if (sluValue != null)
				curCmb = cmbs[(int) sluValue];
			else
				curCmb = cmbs[0];
			
			return curCmb.getRender().getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		}
	}
}
