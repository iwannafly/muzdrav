package ru.nkz.ivcgzo.clientOutPutInfo;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTableItemChangeEvent;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTableItemChangeEventListener;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOutputInfo.VTException;
import ru.nkz.ivcgzo.thriftOutputInfo.VrachInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.VrachTabel;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JPanel;
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
	private int id = 0;
	private String cdol = null;

public tableVrach(){
	addComponentListener(new ComponentAdapter() {
		public void componentShown(ComponentEvent arg0) {
			try {
			    vrach = MainForm.tcl.getVrachTableInfo(codPodr);
			    tableVrachInfo.setData(vrach);
			} catch (TException e) {e.printStackTrace();}
		}
	});
	initialize();
}

public void initialize() {
	JScrollPane vrPane = new JScrollPane();
	codPodr = MainForm.authInfo.cpodr;
	tableVrachInfo = new CustomTable<>(false, true, VrachInfo.class, 0, "Фамилия", 1, "Имя", 2, "Отчество", 3, "Долж.");
	tableVrachInfo.addMouseListener(new MouseAdapter() {
		public void mouseClicked(MouseEvent arg0) {
		    try {
		    	pcod = tableVrachInfo.getSelectedItem().pcod;
		    	cdol = tableVrachInfo.getSelectedItem().cdol;
		    	tableVrachTabel.setData(MainForm.tcl.getVrachTabel(pcod, cdol));
		    }  catch (VTException e) {
		    	JOptionPane.showMessageDialog(tableVrach.this, "tableVrach error", "error", JOptionPane.ERROR_MESSAGE);
		    } catch (KmiacServerException e) {
		    	JOptionPane.showMessageDialog(tableVrach.this, "Ошибка сервера", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
	tableVrachInfo.getColumnModel().getColumn(3).setMaxWidth(40);
	vrPane.setViewportView(tableVrachInfo);
	JScrollPane timePane = new JScrollPane();
	tableVrachTabel = new CustomTable<>(true, true, VrachTabel.class, 0, "Дата приема", 1, "В поликлинике", 2, "На дому", 3, "На дому актив", 4, "Проф.осмотр", 5, "Прочие");
	tableVrachTabel.setDateField(0);
	tableVrachTabel.setFillsViewportHeight(true);
	tableVrachTabel.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
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
				item.setPcod(MainForm.tcl.addVT(event.getItem(), tableVrachInfo.getSelectedItem().pcod, tableVrachInfo.getSelectedItem().cdol, MainForm.authInfo.cpodr));
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
		} catch (KmiacServerException e1) {
			e1.printStackTrace();
		} catch (TException e1) {
			e1.printStackTrace();
		}
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(vrPane, GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(timePane, GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE))
						.addComponent(butPanel, GroupLayout.PREFERRED_SIZE, 167, GroupLayout.PREFERRED_SIZE))
					.addGap(5))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(butPanel, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(vrPane, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE)
						.addComponent(timePane, GroupLayout.DEFAULT_SIZE, 394, Short.MAX_VALUE))
					.addContainerGap())
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
					.addComponent(butCreate, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(butSave, 50, 50, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(butDelete, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addGap(79))
		);
		gl_butPanel.setVerticalGroup(
			gl_butPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_butPanel.createSequentialGroup()
					.addGroup(gl_butPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(butCreate, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(butSave, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
						.addComponent(butDelete, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(112, Short.MAX_VALUE))
		);
		butPanel.setLayout(gl_butPanel);
		setLayout(groupLayout);
}
}