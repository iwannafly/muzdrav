package ru.nkz.ivcgzo.clientVrachInfo;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTableItemChangeEvent;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTableItemChangeEventListener;
import ru.nkz.ivcgzo.thriftServerVrachInfo.MestoRab;
import ru.nkz.ivcgzo.thriftServerVrachInfo.VrachInfo;

public class UserPanel extends JPanel {
	private static final long serialVersionUID = 4036696963713695558L;
	private CustomTable<VrachInfo, VrachInfo._Fields> tblVrach;
	private CustomTable<MestoRab, MestoRab._Fields> tblMrab;
	private PermForm permForm;
	
	public UserPanel(boolean adminMode) {
		super();
		
		JScrollPane spVrach = new JScrollPane();
		tblVrach = new  CustomTable<>(true, true, VrachInfo.class, 1, "Фамилия", 2, "Имя", 3, "Отчество", 4, "Пол", 5, "Дата рождения", 6, "Образование", 7, "СНИЛС", 8, "Диплом");
		tblVrach.setDateField(4);
		tblVrach.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				try {
					if (!arg0.getValueIsAdjusting()) {
						if (tblVrach.getSelectedItem() != null)
							tblMrab.setData(MainForm.tcl.GetMrabList(tblVrach.getSelectedItem().pcod));
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
					MainForm.tcl.DelVrach(event.getItem().pcod);
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
		btnVrAdd.setIcon(new ImageIcon(PermForm.class.getResource("/ru/nkz/ivcgzo/clientVrachInfo/resources/1331789242_Add.png")));
		btnVrAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tblVrach.requestFocusInWindow();
				tblVrach.addItem();
			}
		});
		btnVrAdd.setEnabled(tblVrach.isEditable());
		
		JButton btnVrDel = new JButton();
		btnVrDel.setIcon(new ImageIcon(PermForm.class.getResource("/ru/nkz/ivcgzo/clientVrachInfo/resources/1331789259_Delete.png")));
		btnVrDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
					MainForm.tcl.DelMrab(event.getItem().id);
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
					item.pcod = tblVrach.getSelectedItem().pcod;
					item.clpu = 20;
					item.id = MainForm.tcl.AddMrab(item);
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
		
		JButton btnVrPerm = new JButton();
		btnVrPerm.setIcon(new ImageIcon(PermForm.class.getResource("/ru/nkz/ivcgzo/clientVrachInfo/resources/1331789257_User.png")));
		btnVrPerm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					MainForm.tcl.testConnection();
					permForm.setLocationRelativeTo(UserPanel.this);
					permForm.showWindow(tblVrach.getSelectedItem(), tblMrab.getSelectedItem(), ((tblVrach.getSelectedItem().pcod == MainForm.authInfo.pcod) && (tblMrab.getSelectedItem().cpodr == MainForm.authInfo.cpodr) && (tblMrab.getSelectedItem().clpu == MainForm.authInfo.clpu)));
				} catch (TTransportException e1) {
					MainForm.conMan.reconnect(e1);
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JButton btnMrAdd = new JButton();
		btnMrAdd.setIcon(new ImageIcon(PermForm.class.getResource("/ru/nkz/ivcgzo/clientVrachInfo/resources/1331789242_Add.png")));
		btnMrAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tblMrab.requestFocus();
				tblMrab.addItem();
			}
		});
		btnMrAdd.setEnabled(tblMrab.isEditable());
		
		JButton btnMrDel = new JButton();
		btnMrDel.setIcon(new ImageIcon(PermForm.class.getResource("/ru/nkz/ivcgzo/clientVrachInfo/resources/1331789259_Delete.png")));
		btnMrDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
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
		if (!adminMode)
			btnVrPerm.setVisible(false);
		
		spMrab.setViewportView(tblMrab);
		spVrach.setViewportView(tblVrach);
		this.setLayout(groupLayout);
		
		permForm = new PermForm();
	}
	
	public void onConnect() throws TException {
		tblVrach.setData(MainForm.tcl.GetVrachList());
		tblMrab.setIntegerClassifierSelector(4, MainForm.tcl.getPrizndList());
	}
}
