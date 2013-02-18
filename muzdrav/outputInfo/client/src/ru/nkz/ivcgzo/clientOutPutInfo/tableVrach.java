package ru.nkz.ivcgzo.clientOutPutInfo;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTableItemChangeEvent;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTableItemChangeEventListener;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOutputInfo.VINotFoundException;
import ru.nkz.ivcgzo.thriftOutputInfo.VTDuplException;
import ru.nkz.ivcgzo.thriftOutputInfo.VTException;
import ru.nkz.ivcgzo.thriftOutputInfo.VrachInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.VrachTabel;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

import java.util.List;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.ImageIcon;

public class tableVrach extends JPanel {
	
	private static CustomTable<VrachInfo, VrachInfo._Fields> tableVrachInfo;
	private static CustomTable<VrachTabel, VrachTabel._Fields> tableVrachTabel;
	private List<VrachInfo> vrach;
	private List<VrachTabel> tabel;
	private int codPodr = 0;
	private int pcod = 0;
	private int vt = 0;
	private int id = 0;

/**public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() { 
		public void run() {
			try {
				//tableVrach window = new tableVrach();
				//window.frameVr.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
}*/

public tableVrach(){
	addComponentListener(new ComponentAdapter() {
		@Override
		public void componentShown(ComponentEvent arg0) {
			try {
			    vrach = MainForm.tcl.getVrachTableInfo(codPodr);
			    tableVrachInfo.setData(vrach);
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		    }
		}
	});
	
	//JScrollPane scrollPane = new JScrollPane();
	initialize();
}

public void initialize() {

	JScrollPane vrPane = new JScrollPane();

	codPodr = MainForm.authInfo.cpodr;
	
	tableVrachInfo = new CustomTable<>(false, true, VrachInfo.class, 0, "Код врача", 1, "Фамилия", 2, "Имя", 3, "Отчество", 4, "Должность");
	tableVrachInfo.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent arg0) {
		    try {
		    	int per1 = tableVrachInfo.getSelectedItem().pcod;
				String per2 = tableVrachInfo.getSelectedItem().cdol;
				
		    	pcod = tableVrachInfo.getSelectedItem().pcod;
		    	tabel = MainForm.tcl.getVrachTabel(pcod);
		    	tableVrachTabel.setData(tabel);
		    } catch (VTDuplException e) {
		    	JOptionPane.showMessageDialog(tableVrach.this, "Такой врач не найден в s_tabel", "Ошибка", JOptionPane.ERROR_MESSAGE);
		    } catch (VTException e) {
		    	JOptionPane.showMessageDialog(tableVrach.this, "tableVrach error", "error", JOptionPane.ERROR_MESSAGE);
		    } catch (KmiacServerException e) {
		    	JOptionPane.showMessageDialog(tableVrach.this, "Server error", "error", JOptionPane.ERROR_MESSAGE);
			} catch (TException e) {
				e.printStackTrace();
				MainForm.conMan.reconnect(e);
			}
		}
	});
	tableVrachInfo.setAutoCreateRowSorter(true);
	tableVrachInfo.getRowSorter().toggleSortOrder(0);
	tableVrachInfo.setFillsViewportHeight(true);
	tableVrachInfo.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	tableVrachInfo.getColumnModel().getColumn(0).setMaxWidth(70);
	vrPane.setViewportView(tableVrachInfo);
	
	JScrollPane timePane = new JScrollPane();
	
	tableVrachTabel = new CustomTable<>(true, true, VrachTabel.class, 0, "Код врача", 1, "Должность", 2, "Дата приема", 3, "В поликлинике", 4, "На дому", 5, "На дому актив", 6, "Проф.осмотр", 7, "Прочие");
	//	tableVrachTabel = new CustomTable<>(true, true, VrachTabel.class, 0, "Код врача", 1, "Должность", 2, "Дата приема", 3, "В поликлинике", 4, "На дому", 5, "На дому актив", 6, "Проф.осмотр", 7, "Прочие", 8, "№1 участка", 9, "№2 участка", 10, "№3 участка");
	tableVrachTabel.setAutoCreateRowSorter(true);
	tableVrachTabel.setDateField(2);
	tableVrachTabel.getRowSorter().toggleSortOrder(0);
	tableVrachTabel.setFillsViewportHeight(true);
	tableVrachTabel.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	tableVrachTabel.getColumnModel().getColumn(0).setMaxWidth(70);
	tableVrachTabel.isEditable();
	timePane.setViewportView(tableVrachTabel);
	
	//Удалить
	tableVrachTabel.registerDeleteSelectedRowListener(new CustomTableItemChangeEventListener<VrachTabel>() {
		public boolean doAction(CustomTableItemChangeEvent<VrachTabel> event) {
			try {
				id = tableVrachTabel.getSelectedItem().id;
				MainForm.tcl.deleteVT(id);
			} catch (TException e) {
				e.printStackTrace();
				return false;
			}
			return true;
		}
	});	
	
	//Добавить
	tableVrachTabel.registerAddRowListener(new CustomTableItemChangeEventListener<VrachTabel>() {
		public boolean doAction(CustomTableItemChangeEvent<VrachTabel> event) {
			try {
				VrachTabel item = event.getItem();
				item.setPcod(MainForm.tcl.addVT(event.getItem()));
				int cnt = 0;
				for (int i = 0; i < tableVrachTabel.getData().size(); i++) {
					if ((tableVrachTabel.getData().get(i).pcod == event.getItem().pcod) && (++cnt == 2))
					{
						tableVrachTabel.getData().remove(i);
						tableVrachTabel.getModel().fireTableRowsDeleted(i, i);
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
	
	//Изменить
    tableVrachTabel.registerUpdateSelectedRowListener(new CustomTableItemChangeEventListener<VrachTabel>() {
        public boolean doAction(CustomTableItemChangeEvent<VrachTabel> event) {
            try {
            	MainForm.tcl.updateVT(event.getItem());
            } catch (TException e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }
    });
	
	JPanel butPanel = new JPanel();
		
		JButton butCreate = new JButton("");
		butCreate.setIcon(new ImageIcon(tableVrach.class.getResource("/ru/nkz/ivcgzo/clientOutPutInfo/resources/1331789242_Add.png")));
		butCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				try{
					tableVrachTabel.requestFocus();
                    tableVrachTabel.addItem();
                } catch (Exception e) {
                    e.printStackTrace();
                }
			}
		});
		
		JButton butSave = new JButton("");
		butSave.setIcon(new ImageIcon(tableVrach.class.getResource("/ru/nkz/ivcgzo/clientOutPutInfo/resources/1341981970_Accept.png")));
		butSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
                try{
                    tableVrachTabel.updateSelectedItem();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
		
		try {
			vrach = MainForm.tcl.getVrachTableInfo(codPodr);
			tableVrachInfo.setData(vrach);
		} catch (VINotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (KmiacServerException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (TException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addComponent(timePane, GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(vrPane, GroupLayout.DEFAULT_SIZE, 704, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(butPanel, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
							.addGap(11)
							.addComponent(butPanel, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE))
						.addComponent(vrPane, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(timePane, GroupLayout.DEFAULT_SIZE, 286, Short.MAX_VALUE))
		);
		
		JButton butDelete = new JButton("");
		butDelete.setIcon(new ImageIcon(tableVrach.class.getResource("/ru/nkz/ivcgzo/clientOutPutInfo/resources/1331789259_Delete.png")));
		butDelete.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent arg0) {
                try{
                    tableVrachTabel.requestFocus();
                    tableVrachTabel.deleteSelectedRow();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
		GroupLayout gl_butPanel = new GroupLayout(butPanel);
		gl_butPanel.setHorizontalGroup(
			gl_butPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_butPanel.createSequentialGroup()
					.addGroup(gl_butPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(butDelete, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_butPanel.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(butSave, Alignment.LEADING, 50, 50, Short.MAX_VALUE)
							.addComponent(butCreate, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 50, Short.MAX_VALUE)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_butPanel.setVerticalGroup(
			gl_butPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_butPanel.createSequentialGroup()
					.addComponent(butCreate, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(butSave, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(butDelete, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		butPanel.setLayout(gl_butPanel);
		setLayout(groupLayout);
}
}