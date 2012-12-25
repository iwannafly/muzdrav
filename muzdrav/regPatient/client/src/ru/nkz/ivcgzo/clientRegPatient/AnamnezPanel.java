package ru.nkz.ivcgzo.clientRegPatient;

import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftRegPatient.Anam;
import ru.nkz.ivcgzo.thriftRegPatient.PokazNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.TipPodrNotFoundException;

public class AnamnezPanel extends JPanel{
	private static final long serialVersionUID = -177961285699171152L;
    private CustomTable<Anam, Anam._Fields> tbl_ane;
    private CustomTable<Anam, Anam._Fields> tbl_anj;
	private int tp = 0;
	private List<Anam> AnamnezEpidInfo;
	
	public AnamnezPanel() {
		super();
		
		JPanel pl_ane = new JPanel();
		pl_ane.setBorder(new TitledBorder(null, "Эпидемиологический анамнез", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel pl_anj = new JPanel();
		pl_anj.setBorder(new TitledBorder(null, "Анамнез жизни", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel pl_btn = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(pl_ane, GroupLayout.DEFAULT_SIZE, 466, Short.MAX_VALUE)
						.addComponent(pl_anj, GroupLayout.PREFERRED_SIZE, 466, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pl_btn, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(pl_btn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(pl_ane, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
							.addGap(2)
							.addComponent(pl_anj, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)))
					.addContainerGap())
		);
		
		JButton btn_Save = new JButton("Сохранить");
		btn_Save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try {
                	if (tbl_ane.getData() != null)
                		MainForm.tcl.updateAnam(AnamnezEpidInfo);
                } catch (KmiacServerException e1) {
                    e1.printStackTrace();
                } catch (TException e1) {
                    e1.printStackTrace();
                }
			}
		});
		
		JButton btn_PrintAn = new JButton("Печать");
		btn_PrintAn.setVisible(false);
		btn_PrintAn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String servPath;
//                try {
//                    servPath = MainForm.tcl.printAnamnez(PacientInfoFrame.PersonalInfo, tbl_ane.getData(), MainForm.authInfo);
//                    String cliPath = File.createTempFile("muzdrav", ".htm").getAbsolutePath();
//                    MainForm.conMan.transferFileFromServer(servPath, cliPath);
//                    MainForm.conMan.openFileInEditor(cliPath, false);
//                } catch (TException e1) {
//                    MainForm.conMan.reconnect((TException) e1);
//                    e1.printStackTrace();
//                } catch (IOException e1) {
//                    e1.printStackTrace();
//                }
			}
		});
		GroupLayout gl_pl_btn = new GroupLayout(pl_btn);
		gl_pl_btn.setHorizontalGroup(
			gl_pl_btn.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pl_btn.createSequentialGroup()
					.addGroup(gl_pl_btn.createParallelGroup(Alignment.TRAILING)
						.addComponent(btn_PrintAn, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE)
						.addComponent(btn_Save, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_pl_btn.setVerticalGroup(
			gl_pl_btn.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pl_btn.createSequentialGroup()
					.addGap(44)
					.addComponent(btn_Save)
					.addGap(18)
					.addComponent(btn_PrintAn)
					.addContainerGap(170, Short.MAX_VALUE))
		);
		pl_btn.setLayout(gl_pl_btn);
		
		JScrollPane sp_anj = new JScrollPane();
		
		tbl_anj =new CustomTable<Anam, Anam._Fields>(true, false, Anam.class, 2, "Показатели", 3, "Выбор", 4, "Описание");
		tbl_anj.setFillsViewportHeight(true);
		tbl_anj.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tbl_anj.setPreferredWidths(550,50,230);
		tbl_anj.setCellSelectionEnabled(false);
		tbl_anj.setSurrendersFocusOnKeystroke(false);
		tbl_anj.setShowVerticalLines(true);
		tbl_anj.setShowHorizontalLines(true);
		tbl_anj.setEditableFields(false, 0);
		tbl_anj.setColumnSelectionAllowed(true);
        tbl_anj.setRowSelectionAllowed(true);
		sp_anj.setViewportView(tbl_anj);
		GroupLayout gl_pl_anj = new GroupLayout(pl_anj);
		gl_pl_anj.setHorizontalGroup(
			gl_pl_anj.createParallelGroup(Alignment.LEADING)
				.addComponent(sp_anj, GroupLayout.DEFAULT_SIZE, 138, Short.MAX_VALUE)
		);
		gl_pl_anj.setVerticalGroup(
			gl_pl_anj.createParallelGroup(Alignment.LEADING)
				.addComponent(sp_anj, GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
		);
		pl_anj.setLayout(gl_pl_anj);
		
		JScrollPane sp_ane = new JScrollPane();
		pl_ane.add(sp_ane);
		
		tbl_ane =new CustomTable<Anam, Anam._Fields>(true, false, Anam.class, 2, "Показатели", 3, "Выбор", 4, "Описание");
		tbl_ane.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tbl_ane.setPreferredWidths(550,50,230);
		tbl_ane.setCellSelectionEnabled(false);
		tbl_ane.setSurrendersFocusOnKeystroke(false);
		tbl_ane.setShowVerticalLines(true);
		tbl_ane.setShowHorizontalLines(true);
		tbl_ane.setEditableFields(false, 0);
		tbl_ane.setColumnSelectionAllowed(true);
        tbl_ane.setRowSelectionAllowed(true);
		tbl_ane.setFillsViewportHeight(true);
		sp_ane.setViewportView(tbl_ane);
		GroupLayout gl_pl_ane = new GroupLayout(pl_ane);
		gl_pl_ane.setHorizontalGroup(
			gl_pl_ane.createParallelGroup(Alignment.TRAILING)
				.addComponent(sp_ane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 450, Short.MAX_VALUE)
		);
		gl_pl_ane.setVerticalGroup(
			gl_pl_ane.createParallelGroup(Alignment.TRAILING)
				.addComponent(sp_ane, GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
		);
		pl_ane.setLayout(gl_pl_ane);
		setLayout(groupLayout);
	
	}
	public void onConnect() throws TException {
		
	}
	
	public void ChangePatientAnamnezInfo() {
		AnamnezEpidInfo = new ArrayList <Anam>();
		tbl_ane.setData(new ArrayList<Anam>());
		tbl_anj.setData(new ArrayList<Anam>());
		try {
			tbl_ane.setIntegerClassifierSelector(0, MainForm.tcl.getPokaz());
			tbl_anj.setIntegerClassifierSelector(0, MainForm.tcl.getPokaz());
		} catch (KmiacServerException e1) {
		} catch (PokazNotFoundException e1) {
            JOptionPane.showMessageDialog(tbl_ane, "Классификатор показателей пуст.");
		} catch (TException e1) {
			e1.printStackTrace();
		}
		try {
			if (PacientInfoFrame.curPatientId != 0) 
				AnamnezEpidInfo = MainForm.tcl.getAnamnez(PacientInfoFrame.curPatientId, MainForm.authInfo.cslu, MainForm.authInfo.cpodr);
			int ie = 0;
			int ij = 0;
			for (int i = 0; i < AnamnezEpidInfo.size(); i++){
				if (AnamnezEpidInfo.get(i).getPranz() != null){
					if (AnamnezEpidInfo.get(i).getPranz().equals("ЭА"))
						tbl_ane.addItem(ie++, AnamnezEpidInfo.get(i));
					if (AnamnezEpidInfo.get(i).getPranz().equals("АЖ"))
						tbl_anj.addItem(ij++, AnamnezEpidInfo.get(i));
				}
			}
//			tbl_ane.setData(AnamnezEpidInfo);
//			tbl_anj.setData(AnamnezEpidInfo);
		} catch (TipPodrNotFoundException tnfe) {
            JOptionPane.showMessageDialog(tbl_ane, "Анамнез отсутствует.");
		} catch (KmiacServerException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
}
