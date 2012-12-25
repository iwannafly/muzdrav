package ru.nkz.ivcgzo.clientRegPatient;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.thrift.TException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftRegPatient.Anam;
import ru.nkz.ivcgzo.thriftRegPatient.PokazNotFoundException;
import ru.nkz.ivcgzo.thriftRegPatient.TipPodrNotFoundException;

public class AnamnezForm extends JFrame{

	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private CustomDateEditor tf_date;
    private List<Anam> AnamnezInfo;
    private CustomTable<Anam, Anam._Fields> tbl_anz;
    private CustomTable<Anam, Anam._Fields> tbl_aj;
	private int tp = 0;

	/**
	 * Create the frame.
	 */
	public AnamnezForm() {
		setTitle("Анамнез жизни");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(1, 5, 1, 5));
        setContentPane(contentPane);
        
        JPanel pl_ea = new JPanel();
        pl_ea.setBorder(new TitledBorder(null, "Эпидемиологический анамнез:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        
        JPanel pl_btn = new JPanel();
        
                JButton btn_Del = new JButton("Удалить");
                btn_Del.setVisible(false);
                btn_Del.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
//				try{
//					MainForm.tcl.deleteAnam(PacientInfoFrame.curPatientId, MainForm.authInfo.cslu, MainForm.authInfo.cpodr);
//					tbl_anz.setData(new ArrayList<Anam>());
//				} catch (KmiacServerException kse){
//				} catch (Exception e1) {
//					e1.printStackTrace();
//				}
                	}
                });
                
                JButton btn_Save = new JButton("Сохранить");
                btn_Save.setToolTipText("Сохранить изменения");
                btn_Save.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                        try {
                        	if (tbl_anz.getData() != null)
                        		MainForm.tcl.updateAnam(tbl_anz.getData());
                        } catch (KmiacServerException e1) {
                            e1.printStackTrace();
                        } catch (TException e1) {
                            e1.printStackTrace();
                        }
                	}
                });
                
                JLabel label_4 = new JLabel("Дата");
                label_4.setVisible(false);
                label_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
                
                tf_date = new CustomDateEditor();
                tf_date.setVisible(false);
                tf_date.setColumns(10);
                
                JButton btnPrint_anam = new JButton("Печать");
                btnPrint_anam.addActionListener(new ActionListener() {
                	public void actionPerformed(ActionEvent e) {
                        String servPath;
                        try {
                            servPath = MainForm.tcl.printAnamnez(PacientInfoFrame.PersonalInfo, tbl_anz.getData(), MainForm.authInfo);
                            String cliPath = File.createTempFile("muzdrav", ".htm").getAbsolutePath();
                            MainForm.conMan.transferFileFromServer(servPath, cliPath);
                            MainForm.conMan.openFileInEditor(cliPath, false);
                        } catch (TException e1) {
                            MainForm.conMan.reconnect((TException) e1);
                            e1.printStackTrace();
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
                btnPrint_anam.setToolTipText("Печать анамнеза");
                GroupLayout gl_pl_btn = new GroupLayout(pl_btn);
                gl_pl_btn.setHorizontalGroup(
                	gl_pl_btn.createParallelGroup(Alignment.LEADING)
                		.addGroup(gl_pl_btn.createSequentialGroup()
                			.addGap(20)
                			.addComponent(label_4)
                			.addPreferredGap(ComponentPlacement.RELATED)
                			.addComponent(tf_date, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                			.addGap(40)
                			.addComponent(btn_Del)
                			.addGap(181)
                			.addComponent(btn_Save)
                			.addGap(86)
                			.addComponent(btnPrint_anam)
                			.addGap(47))
                );
                gl_pl_btn.setVerticalGroup(
                	gl_pl_btn.createParallelGroup(Alignment.LEADING)
                		.addGroup(gl_pl_btn.createSequentialGroup()
                			.addGap(5)
                			.addGroup(gl_pl_btn.createParallelGroup(Alignment.LEADING)
                				.addGroup(gl_pl_btn.createParallelGroup(Alignment.BASELINE)
                					.addComponent(btn_Save)
                					.addComponent(btnPrint_anam))
                				.addGroup(gl_pl_btn.createSequentialGroup()
                					.addGap(2)
                					.addGroup(gl_pl_btn.createParallelGroup(Alignment.TRAILING)
                						.addGroup(gl_pl_btn.createParallelGroup(Alignment.BASELINE)
                							.addComponent(label_4)
                							.addComponent(tf_date, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                						.addComponent(btn_Del))))
                			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                pl_btn.setLayout(gl_pl_btn);
        
        JPanel pl_aj = new JPanel();
        pl_aj.setBorder(new TitledBorder(null, "\u0410\u043D\u0430\u043C\u043D\u0435\u0437 \u0436\u0438\u0437\u043D\u0438", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
        			.addComponent(pl_btn, GroupLayout.PREFERRED_SIZE, 723, GroupLayout.PREFERRED_SIZE)
        			.addGap(10))
        		.addComponent(pl_ea, GroupLayout.DEFAULT_SIZE, 733, Short.MAX_VALUE)
        		.addComponent(pl_aj, GroupLayout.DEFAULT_SIZE, 733, Short.MAX_VALUE)
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addComponent(pl_btn, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(pl_ea, GroupLayout.PREFERRED_SIZE, 340, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(pl_aj, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE))
        );
        
        JScrollPane sp_aj = new JScrollPane();
        
		tbl_aj =new CustomTable<Anam, Anam._Fields>(true, false, Anam.class, 2, "Показатели", 3, "Выбор", 4, "Описание");
        tbl_aj.setFillsViewportHeight(true);
		tbl_aj.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tbl_aj.setPreferredWidths(400,50,260);
		tbl_aj.setFillsViewportHeight(true);
		tbl_aj.setCellSelectionEnabled(false);
		tbl_aj.setSurrendersFocusOnKeystroke(false);
		tbl_aj.setShowVerticalLines(true);
		tbl_aj.setShowHorizontalLines(true);
		tbl_aj.setEditableFields(false, 0);
		tbl_aj.setColumnSelectionAllowed(true);
        tbl_aj.setRowSelectionAllowed(true);
        sp_aj.setViewportView(tbl_aj);
        GroupLayout gl_pl_aj = new GroupLayout(pl_aj);
        gl_pl_aj.setHorizontalGroup(
        	gl_pl_aj.createParallelGroup(Alignment.LEADING)
        		.addComponent(sp_aj, GroupLayout.DEFAULT_SIZE, 717, Short.MAX_VALUE)
        );
        gl_pl_aj.setVerticalGroup(
        	gl_pl_aj.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_aj.createSequentialGroup()
        			.addComponent(sp_aj, GroupLayout.PREFERRED_SIZE, 263, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pl_aj.setLayout(gl_pl_aj);
        
        JScrollPane sp_tbl = new JScrollPane();
		tbl_anz =new CustomTable<Anam, Anam._Fields>(true, false, Anam.class, 2, "Показатели", 3, "Выбор", 4, "Описание");
		tbl_anz.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tbl_anz.setPreferredWidths(400,50,260);
		tbl_anz.setFillsViewportHeight(true);
		tbl_anz.setCellSelectionEnabled(false);
		tbl_anz.setSurrendersFocusOnKeystroke(false);
		tbl_anz.setShowVerticalLines(true);
		tbl_anz.setShowHorizontalLines(true);
		tbl_anz.setEditableFields(false, 0);
		tbl_anz.setColumnSelectionAllowed(true);
        tbl_anz.setRowSelectionAllowed(true);
        sp_tbl.setViewportView(tbl_anz);
        GroupLayout gl_pl_ea = new GroupLayout(pl_ea);
        gl_pl_ea.setHorizontalGroup(
        	gl_pl_ea.createParallelGroup(Alignment.LEADING)
        		.addComponent(sp_tbl, GroupLayout.DEFAULT_SIZE, 716, Short.MAX_VALUE)
        );
        gl_pl_ea.setVerticalGroup(
        	gl_pl_ea.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_ea.createSequentialGroup()
        			.addComponent(sp_tbl, GroupLayout.PREFERRED_SIZE, 313, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(14, Short.MAX_VALUE))
        );
        pl_ea.setLayout(gl_pl_ea);
        contentPane.setLayout(gl_contentPane);
		setBounds(10, 20, 750, 705);
		
	}

	public void showAnamnezForm() {
//		AnamnezInfo = new ArrayList <Anam>();
		tbl_anz.setData(new ArrayList<Anam>());
		tbl_aj.setData(new ArrayList<Anam>());
		tf_date.setText(null);
		setVisible(true);
		try {
			tbl_anz.setIntegerClassifierSelector(0, MainForm.tcl.getPokaz());
		} catch (KmiacServerException e1) {
		} catch (PokazNotFoundException e1) {
            JOptionPane.showMessageDialog(tbl_anz, "Классификатор показателей пуст.");
		} catch (TException e1) {
			e1.printStackTrace();
		}
		try {
			AnamnezInfo = MainForm.tcl.getAnamnez(PacientInfoFrame.curPatientId, MainForm.authInfo.cslu, MainForm.authInfo.cpodr);
			tbl_anz.setData(AnamnezInfo);
			tf_date.setDate(AnamnezInfo.get(0).getDatap());
		} catch (TipPodrNotFoundException tnfe) {
            JOptionPane.showMessageDialog(tbl_anz, "Анамнез отсутствует.");
		} catch (KmiacServerException e) {
			e.printStackTrace();
		} catch (TException e) {
			e.printStackTrace();
		}
	}
}
