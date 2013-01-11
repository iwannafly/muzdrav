package ru.nkz.ivcgzo.clientOutPutInfo;

import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTableItemChangeEvent;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTableItemChangeEventListener;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOutputInfo.UchException;
import ru.nkz.ivcgzo.thriftOutputInfo.UchastokInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.UchastokNum;
import javax.swing.table.DefaultTableModel;

public class Uchastok extends JPanel {

	private static CustomTable<UchastokInfo, UchastokInfo._Fields> tableUchastok;
	private List<UchastokInfo> Uchast;
	private static CustomTable<UchastokNum, UchastokNum._Fields> tableUchNum;
	private List<UchastokNum> UchNumList;
	private int cpodr = 0;
	private int cpol = 0;
	private int pcod = 0;
	private int id = 0;
		
	public Uchastok() {
		
		cpodr = MainForm.authInfo.cpodr;
	    try {
			Uchast = MainForm.tcl.getUch(cpodr);
	    } catch (UchException e1) {
	    	e1.printStackTrace();
		} catch (KmiacServerException e1) {
			e1.printStackTrace();
		}catch (TException e1) {
			e1.printStackTrace();
		}
		
		JPanel ButPanel = new JPanel();
		JPanel UchPanel = new JPanel();
		JPanel NumPanel = new JPanel();
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(UchPanel, GroupLayout.DEFAULT_SIZE, 375, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(NumPanel, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(ButPanel, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(UchPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
						.addComponent(ButPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
						.addComponent(NumPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		tableUchNum = new CustomTable<>(true, true, UchastokNum.class, 2, "Участок");
		tableUchNum.setAutoCreateRowSorter(true);
		tableUchNum.getRowSorter().toggleSortOrder(0);
		tableUchNum.setFillsViewportHeight(true);
		tableUchNum.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);

		tableUchastok = new CustomTable<>(true, true, UchastokInfo.class, 0, "Фамилия", 1, "Имя", 2, "Отчество", 3, "Код врача");
		tableUchastok.setAutoCreateRowSorter(true);
		tableUchastok.getRowSorter().toggleSortOrder(0);
		tableUchastok.setFillsViewportHeight(true);
		tableUchastok.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		tableUchastok.setData(Uchast);
		
		
		GroupLayout gl_NumPanel = new GroupLayout(NumPanel);
		gl_NumPanel.setHorizontalGroup(
			gl_NumPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(tableUchNum, GroupLayout.DEFAULT_SIZE, 66, Short.MAX_VALUE)
		);
		gl_NumPanel.setVerticalGroup(
			gl_NumPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(tableUchNum, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
		);
		NumPanel.setLayout(gl_NumPanel);
		
		GroupLayout gl_UchPanel = new GroupLayout(UchPanel);
		gl_UchPanel.setHorizontalGroup(
			gl_UchPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(tableUchastok, GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
		);
		gl_UchPanel.setVerticalGroup(
			gl_UchPanel.createParallelGroup(Alignment.LEADING)
				.addComponent(tableUchastok, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
		);
		UchPanel.setLayout(gl_UchPanel);


		
    	//Добавление
		tableUchNum.registerAddRowListener(new CustomTableItemChangeEventListener<UchastokNum>() {
		public boolean doAction(CustomTableItemChangeEvent<UchastokNum> event) {
			return false;/*
			try {
				UchastokNum item = event.getItem();
				item.setPcod(MainForm.tcl.addUchNum(event.getItem()));
				int cnt = 0;
				for (int i = 0; i < tableUchastok.getData().size(); i++) {
					if ((tableUchastok.getData().get(i).pcod == event.getItem().pcod) && (++cnt == 2))
					{
						tableUchastok.getData().remove(i);
						tableUchastok.getModel().fireTableRowsDeleted(i, i);
						break;
					}
				}
				return true;
			} catch (TTransportException e) {
				MainForm.conMan.reconnect(e);
				return false;
			} catch (TException e) {
				return false;
			}*/
		}
		});
		
		
		//Изменение
		tableUchastok.registerUpdateSelectedRowListener(new CustomTableItemChangeEventListener<UchastokInfo>() {
			public boolean doAction(CustomTableItemChangeEvent<UchastokInfo> event){
				return false;/*
				try {
					MainForm.tcl.updateUch(event.getItem());
				} catch (TException e) {
					e.printStackTrace();
					return false;
				}
				return true;*/
			}
		});
		

		//Удаление
		tableUchastok.registerDeleteSelectedRowListener(new CustomTableItemChangeEventListener<UchastokInfo>() {
		public boolean doAction(CustomTableItemChangeEvent<UchastokInfo> event) {
			return false;
			/*
			try {
				id = tableUchastok.getSelectedItem().id;
				MainForm.tcl.deleteUch(id);
			} catch (TException e) {
				e.printStackTrace();
				return false;
			}
			return true;*/
		}
		});
				
		  
		JButton addBut = new JButton("Добавить");
		addBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableUchastok.requestFocus();
                tableUchastok.addItem();
				
			}
		});
		ButPanel.add(addBut);
		
		JButton updBut = new JButton("Изменить");
		updBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tableUchastok.updateSelectedItem();
			}
		});
		ButPanel.add(updBut);
		
		JButton delBut = new JButton("Удалить");
		delBut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                tableUchastok.requestFocus();
                tableUchastok.deleteSelectedRow();
			}
		});
		ButPanel.add(delBut);
		
		setLayout(groupLayout);

	}
}
