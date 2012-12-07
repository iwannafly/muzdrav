package ru.nkz.ivcgzo.clientRegPatient;

import java.awt.Font;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JScrollPane;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.thriftRegPatient.Anam;

public class AnamnezForm extends JFrame{

	private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private CustomTextField tf_date;
    private CustomTable<Anam, Anam._Fields> tbl_anz;

	/**
	 * Create the frame.
	 */
	public AnamnezForm() {
		setTitle("Анамнез жизни");
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        
        JPanel pl_tbl = new JPanel();
        pl_tbl.setBorder(new TitledBorder(null, "Эпидемиологический анамнез:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        GroupLayout gl_contentPane = new GroupLayout(contentPane);
        gl_contentPane.setHorizontalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addComponent(pl_tbl, GroupLayout.PREFERRED_SIZE, 511, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap(471, Short.MAX_VALUE))
        );
        gl_contentPane.setVerticalGroup(
        	gl_contentPane.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_contentPane.createSequentialGroup()
        			.addComponent(pl_tbl, GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
        			.addGap(302))
        );
        
        JPanel pl_btn = new JPanel();
        
        JScrollPane sp_tbl = new JScrollPane();
        GroupLayout gl_pl_tbl = new GroupLayout(pl_tbl);
        gl_pl_tbl.setHorizontalGroup(
        	gl_pl_tbl.createParallelGroup(Alignment.TRAILING)
        		.addGroup(gl_pl_tbl.createSequentialGroup()
        			.addComponent(pl_btn, GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
        			.addGap(0))
        		.addGroup(Alignment.LEADING, gl_pl_tbl.createSequentialGroup()
        			.addComponent(sp_tbl, GroupLayout.DEFAULT_SIZE, 495, Short.MAX_VALUE)
        			.addContainerGap())
        );
        gl_pl_tbl.setVerticalGroup(
        	gl_pl_tbl.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_tbl.createSequentialGroup()
        			.addComponent(pl_btn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(sp_tbl, GroupLayout.DEFAULT_SIZE, 315, Short.MAX_VALUE)
        			.addContainerGap())
        );
		tbl_anz =new CustomTable<Anam, Anam._Fields>(true, false, Anam.class, 2, "Показатели", 3, "Выбор", 4, "Описание");
		tbl_anz.setPreferredWidths(300,50,200);
		tbl_anz.setFillsViewportHeight(true);
		tbl_anz.setCellSelectionEnabled(false);
		tbl_anz.setSurrendersFocusOnKeystroke(false);
		tbl_anz.setShowVerticalLines(true);
		tbl_anz.setShowHorizontalLines(true);
		tbl_anz.setEditableFields(false, 0);
		tbl_anz.setColumnSelectionAllowed(true);
        tbl_anz.setRowSelectionAllowed(true);
        sp_tbl.setViewportView(tbl_anz);
        
        JButton btn_Del = new JButton("Удалить");
        
        JButton btn_Save = new JButton("Сохранить");
        
        JLabel label_4 = new JLabel("Дата");
        label_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
        
        tf_date = new CustomTextField();
        tf_date.setColumns(10);
        GroupLayout gl_pl_btn = new GroupLayout(pl_btn);
        gl_pl_btn.setHorizontalGroup(
        	gl_pl_btn.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_btn.createSequentialGroup()
        			.addGap(55)
        			.addComponent(btn_Del)
        			.addGap(54)
        			.addComponent(btn_Save)
        			.addGap(18)
        			.addComponent(label_4)
        			.addGap(4)
        			.addComponent(tf_date, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addGap(11))
        );
        gl_pl_btn.setVerticalGroup(
        	gl_pl_btn.createParallelGroup(Alignment.LEADING)
        		.addGroup(gl_pl_btn.createSequentialGroup()
        			.addGap(5)
        			.addGroup(gl_pl_btn.createParallelGroup(Alignment.LEADING)
        				.addGroup(gl_pl_btn.createSequentialGroup()
        					.addGap(2)
        					.addComponent(label_4))
        				.addComponent(tf_date, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        				.addGroup(gl_pl_btn.createParallelGroup(Alignment.BASELINE)
        					.addComponent(btn_Del)
        					.addComponent(btn_Save)))
        			.addContainerGap())
        );
        pl_btn.setLayout(gl_pl_btn);
        pl_tbl.setLayout(gl_pl_tbl);
        contentPane.setLayout(gl_contentPane);
		setBounds(10, 50, 1000, 741);
		
	}

	public void onConnect() {
//		tbl_anz.setIntegerClassifierSelector(0, MainForm.tcl.getVidp());
	}

	public void showAnamnezForm() {
//		ClearAnamnezForm();
		setVisible(true);

	}
//	public void ClearAnamnezForm() {
//
//	}
}
