package ru.nkz.ivcgzo.clientViewSelect.modalForms;

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

import ru.nkz.ivcgzo.clientManager.common.ModalForm;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientViewSelect.MainForm;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftViewSelect.PatientAnamnez;
import ru.nkz.ivcgzo.thriftViewSelect.TipPodrNotFoundException;

public class PatientAnamnezForm extends ModalForm{
	private static final long serialVersionUID = 868220494097813159L;
	private CustomTable<PatientAnamnez, PatientAnamnez._Fields> tblAne;
    private CustomTable<PatientAnamnez, PatientAnamnez._Fields> tblAnj;
	private List<PatientAnamnez> anam;
	
	public PatientAnamnezForm() {
		super(true);
		
		setBounds(100, 100, 1024, 700);
		
		JPanel pl_ane = new JPanel();
		pl_ane.setBorder(new TitledBorder(null, "Эпидемиологический анамнез", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel pl_anj = new JPanel();
		pl_anj.setBorder(new TitledBorder(null, "Анамнез жизни", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel pl_btn = new JPanel();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
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
		
		JButton btnSave = new JButton("Сохранить");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                try {
                	if (tblAne.getData() != null)
                		MainForm.tcl.updateAnam(anam);
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
//                String servPath;
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
						.addComponent(btnSave, GroupLayout.DEFAULT_SIZE, 87, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_pl_btn.setVerticalGroup(
			gl_pl_btn.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pl_btn.createSequentialGroup()
					.addGap(44)
					.addComponent(btnSave)
					.addGap(18)
					.addComponent(btn_PrintAn)
					.addContainerGap(170, Short.MAX_VALUE))
		);
		pl_btn.setLayout(gl_pl_btn);
		
		JScrollPane sp_anj = new JScrollPane();
		
		tblAnj = new CustomTable<>(true, false, PatientAnamnez.class, 2, "Показатели", 3, "Выбор", 4, "Описание");
		tblAnj.setFillsViewportHeight(true);
		tblAnj.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tblAnj.setPreferredWidths(550,50,230);
		tblAnj.setCellSelectionEnabled(false);
		tblAnj.setSurrendersFocusOnKeystroke(false);
		tblAnj.setShowVerticalLines(true);
		tblAnj.setShowHorizontalLines(true);
		tblAnj.setEditableFields(false, 0);
		tblAnj.setColumnSelectionAllowed(true);
        tblAnj.setRowSelectionAllowed(true);
		sp_anj.setViewportView(tblAnj);
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
		
		tblAne =new CustomTable<>(true, false, PatientAnamnez.class, 2, "Показатели", 3, "Выбор", 4, "Описание");
		tblAne.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tblAne.setPreferredWidths(550,50,230);
		tblAne.setCellSelectionEnabled(false);
		tblAne.setSurrendersFocusOnKeystroke(false);
		tblAne.setShowVerticalLines(true);
		tblAne.setShowHorizontalLines(true);
		tblAne.setEditableFields(false, 0);
		tblAne.setColumnSelectionAllowed(true);
        tblAne.setRowSelectionAllowed(true);
		tblAne.setFillsViewportHeight(true);
		sp_ane.setViewportView(tblAne);
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
		getContentPane().setLayout(groupLayout);
	}
	
	public boolean ChangePatientAnamnezInfo(int npasp) {
		tblAne.setData(new ArrayList<PatientAnamnez>());
		tblAnj.setData(new ArrayList<PatientAnamnez>());
		tblAne.setIntegerClassifierSelector(0, MainForm.conMan.getIntegerClassifier(IntegerClassifiers.n_anz));
		tblAnj.setIntegerClassifierSelector(0, MainForm.conMan.getIntegerClassifier(IntegerClassifiers.n_anz));
		try {
			anam = MainForm.tcl.getAnamnez(npasp, MainForm.authInfo.cslu, MainForm.authInfo.cpodr);
			for (int i = 0; i < anam.size(); i++){
				if (anam.get(i).getPranz() != null){
					if (anam.get(i).getPranz().equals("ЭА"))
						tblAne.addItem(anam.get(i));
					if (anam.get(i).getPranz().equals("АЖ"))
						tblAnj.addItem(anam.get(i));
				}
			}
			return true;
		} catch (TipPodrNotFoundException tnfe) {
            JOptionPane.showMessageDialog(this, "Для данного подразделения анамнез не заполняется.");
		} catch (KmiacServerException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Object getResults() {
		return null;
	}
}
