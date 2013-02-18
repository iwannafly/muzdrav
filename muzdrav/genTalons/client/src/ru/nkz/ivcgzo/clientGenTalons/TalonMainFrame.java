package ru.nkz.ivcgzo.clientGenTalons;

import java.awt.Cursor;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Calendar;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.JTabbedPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.UIManager;
import javax.swing.event.ListDataListener;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTableItemChangeEvent;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTableItemChangeEventListener;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTimeEditor;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftGenTalon.AztNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Ndv;
import ru.nkz.ivcgzo.thriftGenTalon.NdvNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Norm;
import ru.nkz.ivcgzo.thriftGenTalon.NormNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Nrasp;
import ru.nkz.ivcgzo.thriftGenTalon.NraspNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Spec;
import ru.nkz.ivcgzo.thriftGenTalon.Talon;
import ru.nkz.ivcgzo.thriftGenTalon.TalonNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.VidpNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Vrach;
import ru.nkz.ivcgzo.clientGenTalons.RaspisanieUnit;
import ru.nkz.ivcgzo.clientGenTalons.SvodkiUnit;

import javax.swing.JProgressBar;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.apache.thrift.TException;
import javax.swing.DefaultComboBoxModel;

public class TalonMainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTree treevrach;
	private String curSpec = null;
	private int curVrach = 0;
	private int ind = 0;
	private long getDatep = 0;
	private final ButtonGroup btnGroup_cxema = new ButtonGroup();
	private final ButtonGroup btnGroup_talon = new ButtonGroup();
	private JRadioButton cxm_1;
	private JRadioButton cxm_2;
	private JRadioButton cxm_3;
	private JRadioButton tal_1;
	private JRadioButton tal_2;
	private JRadioButton tal_3;
	private JRadioButton otm_day_1;
	private JRadioButton otm_day_2;
	private JRadioButton otm_day_3;
	private JRadioButton otm_day_4;
	private JRadioButton otm_day_5;
	private JProgressBar pBar;
	private JCheckBox cbx_rasp;
	private List<Nrasp> NraspInfo;
	private List<Norm> NormInfo;
	private List<Ndv> NdvInfo;
	private List<Talon> TalonInfo;
	private CustomTable<Nrasp, Nrasp._Fields> tbl_rasp;
	private CustomTable<Norm, Norm._Fields> tbl_norm;
	private CustomTable<Ndv, Ndv._Fields> tbl_ndv;
	private CustomTable<Talon, Talon._Fields> tbl_talon;
	private CustomDateEditor tf_datn;
	private CustomDateEditor tf_datk;
	private CustomTimeEditor tf_day1_p1;
	private CustomTimeEditor tf_day1_p2;
	private CustomTimeEditor tf_day2_p1;
	private CustomTimeEditor tf_day2_p2;
	private CustomTimeEditor tf_day3_p1;
	private CustomTimeEditor tf_day3_p2;
	private CustomTimeEditor tf_day4_p1;
	private CustomTimeEditor tf_day4_p2;
	private CustomTimeEditor tf_day5_p1;
	private CustomTimeEditor tf_day5_p2;
	private CustomDateEditor tf_sv1;
	private CustomDateEditor tf_sv2;
	private JComboBox<String> cmb_sv;
	private JComboBox<String> cmb_month;
	private JSpinner sp_god;
	private JSpinner sp_day;
	private int nmonth = 0;
	private int nyear = 0;
	private int nday = 0;

	/**
	 * Launch the application.
	 */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					TalonMainFrame frame = new TalonMainFrame();
//					frame.setVisible(true);
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	/**
	 * Create the frame.
	 */
	public TalonMainFrame() {
		setTitle("Электронная регистратура");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(1, 1, 1114, 690); //(x,y,width,height)
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "Список врачей", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		final JTabbedPane tbMain = new JTabbedPane(JTabbedPane.TOP);
		tbMain.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				if (tbMain.getSelectedIndex() == 1) {
					ChangeNormInfo();
				}
				if (tbMain.getSelectedIndex() == 2) {
					ChangeDatap();
					ChangeTalonInfo();
//					tal_1.setSelected(true);
				}
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tbMain, GroupLayout.DEFAULT_SIZE, 685, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addComponent(tbMain, GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE))
		);
		
		JPanel tbRasp = new JPanel();
		tbMain.addTab("Расписание приема", null, tbRasp, null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0412\u044B\u0431\u0435\u0440\u0438\u0442\u0435 \u0441\u0445\u0435\u043C\u0443 \u0440\u0430\u0441\u043F\u0438\u0441\u0430\u043D\u0438\u044F :", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_2 = new JPanel();
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u041E\u0442\u043C\u0435\u043D\u0430 \u0444\u043E\u0440\u043C\u0438\u0440\u043E\u0432\u0430\u043D\u0438\u044F \u0442\u0430\u043B\u043E\u043D\u043E\u0432", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_6 = new JPanel();
		panel_6.setBorder(new TitledBorder(null, "\u0420\u0430\u0441\u043F\u0438\u0441\u0430\u043D\u0438\u0435 \u043F\u0440\u0438\u0435\u043C\u0430", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new TitledBorder(null, "\u041F\u0435\u0440\u0435\u0440\u044B\u0432", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JButton btnNewButton_1 = new JButton("Сохранить");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					if (tbl_rasp.getSelectedItem() != null){
						for (int i=0; i <= NraspInfo.size()-1; i++){
							if (NraspInfo.get(i).getDenn() == 1){
								NraspInfo.get(i).setTimep_n(tf_day1_p1.getTime().getTime());
								NraspInfo.get(i).setTimep_k(tf_day1_p2.getTime().getTime());
							}
							if (NraspInfo.get(i).getDenn() == 2){
								NraspInfo.get(i).setTimep_n(tf_day2_p1.getTime().getTime());
								NraspInfo.get(i).setTimep_k(tf_day2_p2.getTime().getTime());
							}
							if (NraspInfo.get(i).getDenn() == 3){
								NraspInfo.get(i).setTimep_n(tf_day3_p1.getTime().getTime());
								NraspInfo.get(i).setTimep_k(tf_day3_p2.getTime().getTime());
							}
							if (NraspInfo.get(i).getDenn() == 4){
								NraspInfo.get(i).setTimep_n(tf_day4_p1.getTime().getTime());
								NraspInfo.get(i).setTimep_k(tf_day4_p2.getTime().getTime());
							}
							if (NraspInfo.get(i).getDenn() == 5){
								NraspInfo.get(i).setTimep_n(tf_day5_p1.getTime().getTime());
								NraspInfo.get(i).setTimep_k(tf_day5_p2.getTime().getTime());
							}
						}
						
						tbl_rasp.setData(NraspInfo);
						MainForm.tcl.updateNrasp(tbl_rasp.getData());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btnNewButton_1.setToolTipText("Сохранить перерыв");
		GroupLayout gl_tbRasp = new GroupLayout(tbRasp);
		gl_tbRasp.setHorizontalGroup(
			gl_tbRasp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tbRasp.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_tbRasp.createParallelGroup(Alignment.LEADING, false)
						.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_6, GroupLayout.PREFERRED_SIZE, 369, GroupLayout.PREFERRED_SIZE))
					.addGap(54)
					.addGroup(gl_tbRasp.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_tbRasp.createSequentialGroup()
							.addComponent(panel_8, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnNewButton_1)))
					.addContainerGap())
		);
		gl_tbRasp.setVerticalGroup(
			gl_tbRasp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tbRasp.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_tbRasp.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_tbRasp.createSequentialGroup()
							.addGroup(gl_tbRasp.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_tbRasp.createSequentialGroup()
									.addComponent(panel_8, GroupLayout.PREFERRED_SIZE, 185, GroupLayout.PREFERRED_SIZE)
									.addGap(18))
								.addGroup(gl_tbRasp.createSequentialGroup()
									.addComponent(btnNewButton_1)
									.addGap(28)))
							.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE))
						.addGroup(gl_tbRasp.createSequentialGroup()
							.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_6, GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)))
					.addContainerGap())
		);
		
		JLabel lblNewLabel_7 = new JLabel("Время");
		JLabel lblNewLabel_2 = new JLabel("Понедельник");
		JLabel lblNewLabel_3 = new JLabel("Вторник");
		JLabel lblNewLabel_4 = new JLabel("Среда");
		JLabel lblNewLabel_5 = new JLabel("Четверг");
		JLabel lblNewLabel_6 = new JLabel("Пятница");

		tf_day1_p1 = new CustomTimeEditor();
		tf_day1_p2 = new CustomTimeEditor();
		tf_day2_p1 = new CustomTimeEditor();
		tf_day2_p2 = new CustomTimeEditor();
		tf_day3_p1 = new CustomTimeEditor();
		tf_day3_p2 = new CustomTimeEditor();
		tf_day4_p1 = new CustomTimeEditor();
		tf_day4_p2 = new CustomTimeEditor();
		tf_day5_p1 = new CustomTimeEditor();
		tf_day5_p2 = new CustomTimeEditor();
		GroupLayout gl_panel_8 = new GroupLayout(panel_8);
		gl_panel_8.setHorizontalGroup(
			gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_8.createSequentialGroup()
					.addGap(113)
					.addComponent(lblNewLabel_7, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
					.addGap(56))
				.addGroup(gl_panel_8.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_8.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_8.createSequentialGroup()
							.addGroup(gl_panel_8.createParallelGroup(Alignment.LEADING)
								.addComponent(lblNewLabel_2)
								.addComponent(lblNewLabel_5)
								.addComponent(lblNewLabel_6))
							.addGap(10)
							.addGroup(gl_panel_8.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(tf_day5_p1)
								.addComponent(tf_day2_p1, Alignment.LEADING)
								.addComponent(tf_day1_p1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 45, Short.MAX_VALUE)
								.addComponent(tf_day3_p1)
								.addComponent(tf_day4_p1, Alignment.LEADING)))
						.addComponent(lblNewLabel_3)
						.addComponent(lblNewLabel_4))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_8.createParallelGroup(Alignment.LEADING, false)
						.addComponent(tf_day5_p2)
						.addComponent(tf_day4_p2)
						.addComponent(tf_day3_p2)
						.addComponent(tf_day2_p2)
						.addComponent(tf_day1_p2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
					.addGap(33))
		);
		gl_panel_8.setVerticalGroup(
			gl_panel_8.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_8.createSequentialGroup()
					.addComponent(lblNewLabel_7)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_8.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day1_p1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_2)
						.addComponent(tf_day1_p2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_8.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_3)
						.addComponent(tf_day2_p1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day2_p2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel_8.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_8.createSequentialGroup()
							.addGap(12)
							.addComponent(lblNewLabel_4))
						.addGroup(gl_panel_8.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_8.createParallelGroup(Alignment.BASELINE)
								.addComponent(tf_day3_p1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(tf_day3_p2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_8.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_8.createSequentialGroup()
							.addGroup(gl_panel_8.createParallelGroup(Alignment.BASELINE)
								.addComponent(tf_day4_p1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(tf_day4_p2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_panel_8.createParallelGroup(Alignment.BASELINE)
								.addComponent(tf_day5_p1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(tf_day5_p2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_8.createSequentialGroup()
							.addGap(7)
							.addComponent(lblNewLabel_5)
							.addGap(18)
							.addComponent(lblNewLabel_6)))
					.addGap(36))
		);
		panel_8.setLayout(gl_panel_8);
		
		JScrollPane sp_rasp = new JScrollPane();
		
		tbl_rasp =new CustomTable<>(true, true, Nrasp.class, 1,"День недели" , 2,"Вид приема",3,"С",4,"По");
//		tbl_rasp.setIntegerClassifierSelector(0, MainForm.tcl.get_intClass());
		tbl_rasp.setPreferredWidths(130,100,60,60);
		tbl_rasp.setEditableFields(false, 0,1);
		tbl_rasp.setTimeField(2);
		tbl_rasp.setTimeField(3);
		tbl_rasp.setColumnSelectionAllowed(true);
        tbl_rasp.setRowSelectionAllowed(true);
		tbl_rasp.setFillsViewportHeight(true);
		sp_rasp.setViewportView(tbl_rasp);

        //изменить
        tbl_rasp.registerUpdateSelectedRowListener(new CustomTableItemChangeEventListener<Nrasp>() {
            @Override
            public boolean doAction(CustomTableItemChangeEvent<Nrasp> event) {
                try {
					if (tbl_rasp.getSelectedItem() != null)
						MainForm.tcl.updateNrasp(tbl_rasp.getData());
                } catch (KmiacServerException e) {
                    e.printStackTrace();
                    return false;
                } catch (TException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        });
		
		JButton btn_save = new JButton("Сохранить");
		btn_save.setToolTipText("Сохранить расписание");
		btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
                    tbl_rasp.updateSelectedItem();
					if (tbl_rasp.getSelectedItem() != null)
						MainForm.tcl.updateNrasp(tbl_rasp.getData());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		JButton btn_del = new JButton("Удалить");
		btn_del.setToolTipText("Удалить расписание");
		btn_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					//if (tbl_rasp.getData() != null){
						MainForm.tcl.deleteNrasp(MainForm.authInfo.cpodr, curVrach, curSpec);
					//}
					tbl_rasp.setData(new ArrayList<Nrasp>());
					ClearOtmetka();
					ClearTimePause();
				} catch (KmiacServerException kse){
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addGroup(gl_panel_6.createParallelGroup(Alignment.LEADING)
						.addComponent(sp_rasp, GroupLayout.PREFERRED_SIZE, 352, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_6.createSequentialGroup()
							.addContainerGap()
							.addComponent(btn_save)
							.addGap(31)
							.addComponent(btn_del)))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addGap(5)
					.addComponent(sp_rasp, GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE)
					.addGap(17)
					.addGroup(gl_panel_6.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_save)
						.addComponent(btn_del)))
		);
		panel_6.setLayout(gl_panel_6);
		
		JScrollPane sp_ndv = new JScrollPane();
		
		
		JPanel panel_7 = new JPanel();
		
		JButton btn_ndv_save = new JButton("Сохранить");
		btn_ndv_save.setToolTipText("сохранить изменения");
		btn_ndv_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					tbl_ndv.updateSelectedItem();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		JButton btn_ndv_add = new JButton("+");
		btn_ndv_add.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				tbl_ndv.requestFocus();
				tbl_ndv.addItem();
			}
		});
		btn_ndv_add.setToolTipText("добавить");

		JButton btn_ndv_del = new JButton("-");
		btn_ndv_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tbl_ndv.requestFocus();
				tbl_ndv.deleteSelectedRow();
			}
		});
		btn_ndv_del.setToolTipText("удалить");
		
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_7, GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(12)
							.addComponent(sp_ndv, GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addContainerGap()
							.addComponent(btn_ndv_add, GroupLayout.PREFERRED_SIZE, 48, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_ndv_del, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_ndv_save)))
					.addContainerGap())
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(sp_ndv, GroupLayout.DEFAULT_SIZE, 321, Short.MAX_VALUE)
					.addGap(16)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
							.addComponent(btn_ndv_del)
							.addComponent(btn_ndv_save, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE))
						.addComponent(btn_ndv_add))
					.addGap(1))
		);
		
		otm_day_1 = new JRadioButton("Понедельник");
		otm_day_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (tbl_rasp.getSelectedItem() != null){
						for (int i=0; i <= NraspInfo.size()-1; i++) 
							if (NraspInfo.get(i).getDenn() == 1)
								NraspInfo.get(i).setPfd(otm_day_1.isSelected());
						tbl_rasp.setData(NraspInfo);
						MainForm.tcl.updateNrasp(tbl_rasp.getData());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		otm_day_2 = new JRadioButton("Вторник");
		otm_day_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (tbl_rasp.getSelectedItem() != null){
						for (int i=0; i <= NraspInfo.size()-1; i++) 
							if (NraspInfo.get(i).getDenn() == 2)
								NraspInfo.get(i).setPfd(otm_day_2.isSelected());
						tbl_rasp.setData(NraspInfo);
						MainForm.tcl.updateNrasp(tbl_rasp.getData());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		otm_day_3 = new JRadioButton("Среда");
		otm_day_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (tbl_rasp.getSelectedItem() != null){
						for (int i=0; i <= NraspInfo.size()-1; i++) 
							if (NraspInfo.get(i).getDenn() == 3)
								NraspInfo.get(i).setPfd(otm_day_3.isSelected());
						tbl_rasp.setData(NraspInfo);
						MainForm.tcl.updateNrasp(tbl_rasp.getData());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		otm_day_4 = new JRadioButton("Четверг");
		otm_day_4.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (tbl_rasp.getSelectedItem() != null){
						for (int i=0; i <= NraspInfo.size()-1; i++){ 
							if (NraspInfo.get(i).getDenn() == 4)
								NraspInfo.get(i).setPfd(otm_day_4.isSelected());
//		    				System.out.println(NraspInfo.get(i).getId()+", "+NraspInfo.get(i).isPfd());
						}
						tbl_rasp.setData(NraspInfo);
//						MainForm.tcl.updateNrasp(NraspInfo);
						MainForm.tcl.updateNrasp(tbl_rasp.getData());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		otm_day_5 = new JRadioButton("Пятница");
		otm_day_5.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					if (tbl_rasp.getSelectedItem() != null){
						for (int i=0; i <= NraspInfo.size()-1; i++) 
							if (NraspInfo.get(i).getDenn() == 5)
								NraspInfo.get(i).setPfd(otm_day_5.isSelected());
						tbl_rasp.setData(NraspInfo);
						MainForm.tcl.updateNrasp(tbl_rasp.getData());
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		GroupLayout gl_panel_7 = new GroupLayout(panel_7);
		gl_panel_7.setHorizontalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_7.createParallelGroup(Alignment.LEADING)
						.addComponent(otm_day_1)
						.addComponent(otm_day_2)
						.addComponent(otm_day_3)
						.addComponent(otm_day_4)
						.addComponent(otm_day_5))
					.addContainerGap(71, Short.MAX_VALUE))
		);
		gl_panel_7.setVerticalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addComponent(otm_day_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(otm_day_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(otm_day_3)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(otm_day_4)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(otm_day_5)
					.addGap(0, 0, Short.MAX_VALUE))
		);
		panel_7.setLayout(gl_panel_7);
		
		tbl_ndv =new CustomTable<>(true, true, Ndv.class, 1,"Дата начала",2,"окончания");
		tbl_ndv.setPreferredWidths(90,90);
		tbl_ndv.setDateField(0);
		tbl_ndv.setDateField(1);
        tbl_ndv.setColumnSelectionAllowed(true);
        tbl_ndv.setRowSelectionAllowed(true);
		tbl_ndv.setFillsViewportHeight(true);
		sp_ndv.setViewportView(tbl_ndv);
		panel_4.setLayout(gl_panel_4);

		tbl_ndv.registerDeleteSelectedRowListener(new CustomTableItemChangeEventListener<Ndv>() {
			@Override
			public boolean doAction(CustomTableItemChangeEvent<Ndv> event) {
	        	try {
	        		if (tbl_ndv.getSelectedItem() !=  null) { 
	        			MainForm.tcl.deleteNdv(tbl_ndv.getSelectedItem().getId());
	        		}
	        	} catch (Exception e) {
	        		e.printStackTrace();
	        		return false;
	        	}
	        	return true;
			}
		});

		tbl_ndv.registerAddRowListener(new CustomTableItemChangeEventListener<Ndv>() {
			@Override
			public boolean doAction(CustomTableItemChangeEvent<Ndv> event) {
		        try {
					Ndv item = event.getItem();
				    item.setId(tbl_ndv.getSelectedItem().id);
				    item.setCpol(MainForm.authInfo.cpodr);
				    item.setCdol(curSpec);
				    item.setPcod(curVrach);
				    item.setDatan(tbl_ndv.getSelectedItem().datan);
				    item.setDatak(tbl_ndv.getSelectedItem().datak);
				    MainForm.tcl.addNdv(event.getItem());
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				return true;
			}
		});

		//изменить
		tbl_ndv.registerUpdateSelectedRowListener(new CustomTableItemChangeEventListener<Ndv>() {
			@Override
			public boolean doAction(CustomTableItemChangeEvent<Ndv> event) {
		        try {
					if (tbl_ndv.getSelectedItem() != null)
						MainForm.tcl.updateNdv(tbl_ndv.getData());
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				return true;
			}
		});
		
		cbx_rasp = new JCheckBox("учитывать четные и нечетные дни ");
		JButton btn_new = new JButton("Создать");
		btn_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cbx_rasp.isSelected()){
					RaspisanieUnit.NewRaspisanie(MainForm.authInfo.cpodr, curVrach, curSpec,1);
				}
				else {
					RaspisanieUnit.NewRaspisanie(MainForm.authInfo.cpodr, curVrach, curSpec, 0);
				}
				ClearOtmetka();
				ShowTimePause();
				ChangeNraspInfo();
			}
		});
		btn_new.setToolTipText("Создать расписание");
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(btn_new, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbx_rasp, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(278, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGap(1)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE, false)
						.addComponent(btn_new)
						.addComponent(cbx_rasp))
					.addGap(4))
		);
		panel_2.setLayout(gl_panel_2);

		cxm_1 = new JRadioButton("на каждый день");
		cxm_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClearOtmetka();
				ChangeCxemaInfo(0);			}
		});
		btnGroup_cxema.add(cxm_1);
		
		cxm_2 = new JRadioButton("четные дни");
		cxm_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClearOtmetka();
				ChangeCxemaInfo(1);			}
		});
		btnGroup_cxema.add(cxm_2);
		
		cxm_3 = new JRadioButton("нечетные дни");
		cxm_3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClearOtmetka();
				ChangeCxemaInfo(2);			}
		});
		btnGroup_cxema.add(cxm_3);
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(cxm_1)
					.addGap(18)
					.addComponent(cxm_2)
					.addGap(18)
					.addComponent(cxm_3)
					.addContainerGap(91, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(cxm_1)
						.addComponent(cxm_2)
						.addComponent(cxm_3))
					.addContainerGap(46, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		tbRasp.setLayout(gl_tbRasp);
		
		JPanel tbNorm = new JPanel();
		tbMain.addTab("Нормы длительности приема", null, tbNorm, null);
		
		JScrollPane sp_norm = new JScrollPane();
		
		JPanel panel_3 = new JPanel();
		GroupLayout gl_tbNorm = new GroupLayout(tbNorm);
		gl_tbNorm.setHorizontalGroup(
			gl_tbNorm.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tbNorm.createSequentialGroup()
					.addGap(27)
					.addGroup(gl_tbNorm.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 264, GroupLayout.PREFERRED_SIZE)
						.addComponent(sp_norm, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(545, Short.MAX_VALUE))
		);
		gl_tbNorm.setVerticalGroup(
			gl_tbNorm.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_tbNorm.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 36, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(sp_norm, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(407, Short.MAX_VALUE))
		);
		
		JButton btn_new_norm = new JButton("Создать");
		btn_new_norm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					RaspisanieUnit.NewNorm(MainForm.authInfo.cpodr, curSpec);
					ChangeNormInfo();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		JButton btn_save_norm = new JButton("Сохранить");
		btn_save_norm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
                    tbl_norm.updateSelectedItem();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JButton btn_del_norm = new JButton("Удалить");
		btn_del_norm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					MainForm.tcl.deleteNorm(MainForm.authInfo.cpodr, curSpec);
					tbl_norm.setData(new ArrayList<Norm>());
				} catch (KmiacServerException kse){
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_del_norm.setToolTipText("Удалить");
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addComponent(btn_new_norm)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_save_norm)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btn_del_norm)
					.addContainerGap())
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap(13, Short.MAX_VALUE)
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_new_norm)
						.addComponent(btn_save_norm)
						.addComponent(btn_del_norm)))
		);
		panel_3.setLayout(gl_panel_3);
		
		tbl_norm =new CustomTable<>(true, true, Norm.class, 1,"Вид приема",2,"Длительность");
		tbl_norm.setFillsViewportHeight(true);
		tbl_norm.setPreferredWidths(90,90);
		tbl_norm.setEditableFields(false, 0);
        tbl_norm.setColumnSelectionAllowed(true);
        tbl_norm.setRowSelectionAllowed(true);
		sp_norm.setViewportView(tbl_norm);
		tbNorm.setLayout(gl_tbNorm);
		
        //изменить
        tbl_norm.registerUpdateSelectedRowListener(new CustomTableItemChangeEventListener<Norm>() {
            @Override
            public boolean doAction(CustomTableItemChangeEvent<Norm> event) {
                try {
					if (tbl_norm.getSelectedItem() != null)
						MainForm.tcl.updateNorm(tbl_norm.getData());
                } catch (KmiacServerException e) {
                    e.printStackTrace();
                    return false;
                } catch (TException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
            }
        });

        JPanel tbTalon = new JPanel();
		tbMain.addTab("Журнал талонов", null, tbTalon, null);
		
		JPanel panel_5 = new JPanel();
		panel_5.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0424\u043E\u0440\u043C\u0438\u0440\u043E\u0432\u0430\u043D\u0438\u0435 \u0442\u0430\u043B\u043E\u043D\u043E\u0432", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_10 = new JPanel();
		panel_10.setBorder(new TitledBorder(null, "\u041F\u0440\u043E\u0441\u043C\u043E\u0442\u0440 \u0442\u0430\u043B\u043E\u043D\u043E\u0432", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_tbTalon = new GroupLayout(tbTalon);
		gl_tbTalon.setHorizontalGroup(
			gl_tbTalon.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_tbTalon.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_tbTalon.createParallelGroup(Alignment.TRAILING)
						.addComponent(panel_5, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 738, Short.MAX_VALUE)
						.addComponent(panel_10, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 738, Short.MAX_VALUE))
					.addGap(73))
		);
		gl_tbTalon.setVerticalGroup(
			gl_tbTalon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tbTalon.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_10, GroupLayout.DEFAULT_SIZE, 432, Short.MAX_VALUE)
					.addContainerGap())
		);
		
        String[] items = {
           		"Январь",
           		"Февраль",
           		"Март",
           		"Апрель",
           		"Май",
           		"Июнь",
           		"Июль",
           		"Август",
           		"Сентябрь",
           		"Октябрь",
           		"Ноябрь",
           		"Декабрь"
            };

        cmb_month = new JComboBox<String>();
        cmb_month.setModel(new DefaultComboBoxModel<>(items));
        cmb_month.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
			    System.out.println(nday+ ",   "+ nmonth+ ",   "+ nyear);
				if (nmonth != 0 && nday != 0 && nyear != 0){
	                nmonth = cmb_month.getSelectedIndex()+1;
	                ChangeDatap();
				}
        	}
        });
        cmb_month.setMaximumRowCount(12);

        sp_god = new JSpinner();
        sp_god.setModel(new SpinnerNumberModel(new Integer(2012), new Integer(2012), null, new Integer(1)));
        sp_god.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent arg0) {
			    System.out.println(nday+ ",   "+ nmonth+ ",   "+ nyear);
				if (nmonth != 0 && nday != 0 && nyear != 0){
	                nyear = Integer.valueOf(sp_god.getValue().toString());
					ChangeDatap();
				}
        	}
        });
		sp_day = new JSpinner();
		sp_day.setModel(new SpinnerNumberModel(2, 1, 31, 1));
		sp_day.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
//			    System.out.println(nday+ ",   "+ nmonth+ ",   "+ nyear);
//				if (cmb_month.getSelectedIndex() != 0 && Integer.valueOf(sp_day.getValue().toString()) != 0 && Integer.valueOf(sp_god.getValue().toString()) != 0){
				if (nmonth != 0 && nday != 0 && nyear != 0){
			        nday = Integer.valueOf(sp_day.getValue().toString());
					ChangeDatap();
				}
			}
		});
		Calendar cal1 = Calendar.getInstance();
		cal1.setTimeInMillis(System.currentTimeMillis());
		sp_god.setValue(cal1.get(Calendar.YEAR));
		sp_day.setValue(cal1.get(Calendar.DAY_OF_MONTH));
        cmb_month.setSelectedIndex(cal1.get(Calendar.MONTH));
        nyear = Integer.valueOf(sp_god.getValue().toString());
        nday = Integer.valueOf(sp_day.getValue().toString());
        nmonth = cmb_month.getSelectedIndex()+1;
        
		JLabel lblNewLabel_11 = new JLabel("Год");
		JLabel lblNewLabel_12 = new JLabel("Месяц");
		JLabel lblNewLabel_13 = new JLabel("День");
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_panel_10 = new GroupLayout(panel_10);
		gl_panel_10.setHorizontalGroup(
			gl_panel_10.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_10.createSequentialGroup()
					.addGroup(gl_panel_10.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_10.createSequentialGroup()
							.addGap(35)
							.addComponent(lblNewLabel_11)
							.addGap(55)
							.addComponent(lblNewLabel_12)
							.addGap(114)
							.addComponent(lblNewLabel_13))
						.addGroup(gl_panel_10.createSequentialGroup()
							.addGap(24)
							.addGroup(gl_panel_10.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 164, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_10.createSequentialGroup()
									.addComponent(sp_god, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(cmb_month, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(sp_day, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)))))
					.addGap(437))
		);
		gl_panel_10.setVerticalGroup(
			gl_panel_10.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_10.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_10.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_12)
						.addComponent(lblNewLabel_13)
						.addComponent(lblNewLabel_11))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_10.createParallelGroup(Alignment.BASELINE)
						.addComponent(cmb_month, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(sp_day, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(sp_god, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 328, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tbl_talon =new CustomTable<>(true, true, Talon.class, 11,"Время", 6,"Вид приема");
		tbl_talon.setPreferredWidths(70,90);
		tbl_talon.setTimeField(0);
		tbl_talon.setEditableFields(false, 0,1);
		tbl_talon.setFillsViewportHeight(true);
		scrollPane_1.setViewportView(tbl_talon);
		panel_10.setLayout(gl_panel_10);
		
		tf_datn = new CustomDateEditor();
		tf_datk = new CustomDateEditor();
		tf_datn.setDate(System.currentTimeMillis());
		tf_datk.setDate(System.currentTimeMillis());

		JButton btnNewButton = new JButton("Формировать");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				pBar.setMinimum(0);
				pBar.setMaximum(100);
				pBar.setValue(0);
				if (tf_datn.getDate().getTime() <= tf_datk.getDate().getTime()){
					if (tal_1.isSelected()) ind = 1;
					if (tal_2.isSelected()) ind = 2;
					if (tal_3.isSelected()) ind = 3;
					if ((ind == 1) || (curSpec != null  && ind == 2) || (curVrach != 0  && ind == 3)){
						setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
						RaspisanieUnit.CreateTalons(pBar, MainForm.authInfo.cpodr, curVrach, curSpec, tf_datn.getDate().getTime(), tf_datk.getDate().getTime(), ind);
						setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
						JOptionPane.showMessageDialog(null, "Формирование талонов завершено.", null, JOptionPane.INFORMATION_MESSAGE); 
				        nday = Integer.valueOf(sp_day.getValue().toString());
		                nmonth = cmb_month.getSelectedIndex()+1;
		                nyear = Integer.valueOf(sp_god.getValue().toString());
 					    if (nmonth != 0 && nday != 0 && nyear != 0){
			                ChangeDatap();
						}
					}else {
					    if (curSpec == null  && ind == 2) System.out.println("Выберите врачебную специальность.");
					    if (curVrach == 0  && ind == 3) System.out.println("Выберите врача.");
					}
			
				}else{
					System.out.println("Неправильно задан период формирования талонов.");
					JOptionPane.showMessageDialog(null, "Неправильно задан период формирования талонов.", null, JOptionPane.INFORMATION_MESSAGE); 
				}
				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		JLabel lblNewLabel = new JLabel("Период: с");
		JLabel lblNewLabel_1 = new JLabel("по");
		
		tal_1 = new JRadioButton("все специалисты");
		btnGroup_talon.add(tal_1);
		
		tal_2 = new JRadioButton("для выбранной специальности");
		btnGroup_talon.add(tal_2);
		
		tal_3 = new JRadioButton("для выбранного врача");
		btnGroup_talon.add(tal_3);
		
		pBar = new JProgressBar();
		pBar.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
			}
		});
		//progressBar.setIndeterminate(true); //бесконечный
		pBar.setMinimum(0);
		pBar.setMaximum(100);
		pBar.setValue(0);
		pBar.setStringPainted(true);
		
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_5.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addGap(20)
							.addComponent(tf_datn, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblNewLabel_1)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_datk, GroupLayout.PREFERRED_SIZE, 89, GroupLayout.PREFERRED_SIZE))
						.addComponent(pBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton)
						.addComponent(tal_3)
						.addComponent(tal_2)
						.addComponent(tal_1))
					.addContainerGap(230, Short.MAX_VALUE))
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_datn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel)
						.addComponent(tal_1)
						.addComponent(tf_datk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_5.createSequentialGroup()
							.addComponent(tal_2)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tal_3)
							.addGap(18)
							.addComponent(btnNewButton))
						.addComponent(pBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_5.setLayout(gl_panel_5);
		tbTalon.setLayout(gl_tbTalon);
		
		JPanel tbSvodki = new JPanel();
		tbMain.addTab("Сводки", null, tbSvodki, null);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBorder(new TitledBorder(null, "\u0424\u043E\u0440\u043C\u0438\u0440\u043E\u0432\u0430\u043D\u0438\u0435 \u0441\u0432\u043E\u0434\u043E\u043A", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_tbSvodki = new GroupLayout(tbSvodki);
		gl_tbSvodki.setHorizontalGroup(
			gl_tbSvodki.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tbSvodki.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_9, GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
					.addGap(244))
		);
		gl_tbSvodki.setVerticalGroup(
			gl_tbSvodki.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tbSvodki.createSequentialGroup()
					.addGap(23)
					.addComponent(panel_9, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(471, Short.MAX_VALUE))
		);
		
		JLabel lblNewLabel_8 = new JLabel("Вид сводки");
		JLabel lblNewLabel_9 = new JLabel("За период  с");
		JLabel lblNewLabel_10 = new JLabel("по");
		
        String[] items1 = {
       		"1. Списки пациентов, записанных на прием",
       		"2. Количество неиспользованных талонов",
       		"3. Списки пациентов, отмененного приема",
       		"4. Количество выданных талонов",
       		"5. Расписание работы врачей",
       		"6. Печать талонов на прием к врачу"
        };

		cmb_sv = new JComboBox<String>();
		cmb_sv.setModel(new DefaultComboBoxModel<>(items1));
		cmb_sv.addActionListener(new ActionListener() {
			@SuppressWarnings({ "rawtypes", "unused" })
			public void actionPerformed(ActionEvent e) {
				JComboBox cb = (JComboBox) e.getSource();
				String tmpName = (String) cb.getSelectedItem ();
//				System.out.println(tmpName);
			}
		});
		
		tf_sv2 = new CustomDateEditor();
		tf_sv1 = new CustomDateEditor();
		tf_sv1.setDate(System.currentTimeMillis());
		tf_sv2.setDate(System.currentTimeMillis());
		
		JButton btnSvod = new JButton("Создать сводку");
		btnSvod.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				System.out.println(cmb_sv.getSelectedIndex());
				SvodkiUnit.Svodki(cmb_sv.getSelectedIndex(), tf_sv1.getDate().getTime(), tf_sv2.getDate().getTime(), curVrach, curSpec);
			}
		});
		GroupLayout gl_panel_9 = new GroupLayout(panel_9);
		gl_panel_9.setHorizontalGroup(
			gl_panel_9.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_9.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_9.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_9.createSequentialGroup()
							.addComponent(lblNewLabel_8)
							.addGap(18)
							.addComponent(cmb_sv, 0, 470, Short.MAX_VALUE))
						.addGroup(gl_panel_9.createSequentialGroup()
							.addComponent(lblNewLabel_9)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tf_sv1, GroupLayout.PREFERRED_SIZE, 95, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblNewLabel_10)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_sv2, GroupLayout.PREFERRED_SIZE, 90, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addComponent(btnSvod, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_9.setVerticalGroup(
			gl_panel_9.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_9.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_9.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_8)
						.addComponent(cmb_sv, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_9.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel_9)
						.addComponent(tf_sv1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnSvod)
						.addComponent(lblNewLabel_10)
						.addComponent(tf_sv2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(17, Short.MAX_VALUE))
		);
		panel_9.setLayout(gl_panel_9);
		tbSvodki.setLayout(gl_tbSvodki);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 229, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
		);
		
		treevrach = new JTree();
		treevrach.setVisibleRowCount(50);
		treevrach.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
		 		Object lastPath = e.getPath().getLastPathComponent();
		 		if (lastPath == null) return;
				try {
			 		if (lastPath instanceof SpecTreeNode) {
			 			SpecTreeNode specNode = (SpecTreeNode) lastPath;
			 			Spec spec = specNode.spec;
			 			curSpec = spec.getPcod();
			 			curVrach = 0;
		 			} 
			 		else if (lastPath instanceof VrachTreeNode) {
			 			VrachTreeNode vrachNode = (VrachTreeNode) lastPath;
			 			Vrach vrach = vrachNode.vrach;
			 			curSpec = vrach.getCdol();
			 			curVrach = vrach.getPcod();
			 		}
//				    System.out.println("curSpec= "+curSpec+", curVrach= "+Integer.toString(curVrach));
					if (tbMain.getSelectedIndex() == 0) {
						ClearOtmetka();
						ClearTimePause();
						ChangeNraspInfo();
						ChangeNdvInfo();
					}
					if (tbMain.getSelectedIndex() == 1) ChangeNormInfo();
					if (tbMain.getSelectedIndex() == 2) {
						ChangeDatap();
//						ChangeTalonInfo();
//						tal_1.setSelected(true);
					}
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}

		});
		treevrach.addTreeExpansionListener(new TreeExpansionListener() {
			public void treeCollapsed(TreeExpansionEvent event) {
			}
			public void treeExpanded(TreeExpansionEvent event) {
		 		Object lastPath = event.getPath().getLastPathComponent();
		 		if (lastPath == null) return;
		 		if (lastPath instanceof SpecTreeNode) {
		 			try {
						SpecTreeNode specNode = (SpecTreeNode) lastPath;
						specNode.removeAllChildren();
						for (Vrach vrachChild : MainForm.tcl.getVrachForCurrentSpec(MainForm.authInfo.cpodr,specNode.spec.getPcod())) {
							specNode.add(new VrachTreeNode(vrachChild));
						}
						((DefaultTreeModel) treevrach.getModel()).reload(specNode);
					} catch (Exception e) {
						e.printStackTrace();
					}
		 		}
			}
		});
		scrollPane.setViewportView(treevrach);
		treevrach.setShowsRootHandles(true);
		treevrach.setRootVisible(false);
		DefaultTreeCellRenderer renderer =  (DefaultTreeCellRenderer) treevrach.getCellRenderer();
		renderer.setLeafIcon(null);
		renderer.setClosedIcon(null);
		renderer.setOpenIcon(null);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);	
	}

	public void fillTable() {
	}
	
	public void onConnect() {
		treevrach.setModel(new DefaultTreeModel(createNodes()));
		
		try {
			tbl_rasp.setIntegerClassifierSelector(1, MainForm.tcl.getVidp());
			tbl_norm.setIntegerClassifierSelector(0, MainForm.tcl.getVidp());
			tbl_talon.setIntegerClassifierSelector(1, MainForm.tcl.getVidp());
		} catch (KmiacServerException kse){
		} catch (VidpNotFoundException vnfe){
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		try {
			MainForm.tcl.getAzt();
			tbl_rasp.setIntegerClassifierSelector(0, MainForm.tcl.getAzt());
		} catch (KmiacServerException kse){
		} catch (AztNotFoundException anfe){
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	
	  private DefaultMutableTreeNode createNodes() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Корень дерева");
		try {
			for (Spec spec : MainForm.tcl.getAllSpecForPolikliniki(MainForm.authInfo.cpodr))
				root.add(new SpecTreeNode(spec));
		} catch (Exception e) {
		    System.out.println("Нет данных в табл. s_mrab по данному подразделению: "+Integer.toString(MainForm.authInfo.cpodr));
			e.printStackTrace();
		}
		return root;
	}

	class SpecTreeNode extends DefaultMutableTreeNode {
		private static final long serialVersionUID = 4212592425962984738L;
		private Spec spec;
		
		public SpecTreeNode(Spec spec) {
			this.spec = spec;
			this.add(new VrachTreeNode(new Vrach()));
		}
		
		@Override
		public String toString() {
			return spec.getName();
		}
	}
	class VrachTreeNode extends DefaultMutableTreeNode{
		private static final long serialVersionUID = -4684514837066276873L;
		private Vrach vrach;
		
		public VrachTreeNode(Vrach vrach) {
			this.vrach = vrach;
		}
		
		@Override
		public String toString() {
			return vrach.getFam()+" "+vrach.getIm()+" "+vrach.getOt();
		}
	}
	private void ChangeNraspInfo(){
		try {
			btnGroup_cxema.clearSelection();
			tbl_rasp.setData(new ArrayList<Nrasp>());
			try {
				NraspInfo = MainForm.tcl.getNrasp(MainForm.authInfo.cpodr, curVrach, curSpec, 0);
				if (!NraspInfo.isEmpty())
					cxm_1.setSelected(true);
			} catch (NraspNotFoundException nnfe) {
				try {
				NraspInfo = MainForm.tcl.getNrasp(MainForm.authInfo.cpodr, curVrach, curSpec, 1);
				if (!NraspInfo.isEmpty())
					cxm_2.setSelected(true);
				} catch (NraspNotFoundException nnfe1){
					NraspInfo = MainForm.tcl.getNrasp(MainForm.authInfo.cpodr, curVrach, curSpec, 2);
					if (!NraspInfo.isEmpty())
						cxm_3.setSelected(true);
				}
			}
            if (NraspInfo.size() > 0) {
    			tbl_rasp.setData(NraspInfo);
    			ShowOtmetka();
    			ShowTimePause();
            }
		} catch (NraspNotFoundException nnfe3) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void ChangeCxemaInfo(int cxm){
		try {
			tbl_rasp.setData(new ArrayList<Nrasp>());
			NraspInfo = MainForm.tcl.getNrasp(MainForm.authInfo.cpodr, curVrach, curSpec, cxm);
            if (!NraspInfo.isEmpty()) {
    			tbl_rasp.setData(NraspInfo);
    			ShowOtmetka();
    			ShowTimePause();
            }
//			System.out.println();
//		    tbl_rasp.getSelectedItem().setName(pInfo.getName());
		} catch (NraspNotFoundException nnfe) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void ChangeNormInfo(){
		try {
			tbl_norm.setData(new ArrayList<Norm>());
			NormInfo = MainForm.tcl.getNorm(MainForm.authInfo.cpodr, curSpec);
            tbl_norm.setData(NormInfo);
		} catch (NormNotFoundException nnfe) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void ChangeNdvInfo(){
		try {
			tbl_ndv.setData(new ArrayList<Ndv>());
			NdvInfo = MainForm.tcl.getNdv(MainForm.authInfo.cpodr, curVrach, curSpec);
			tbl_ndv.setData(NdvInfo);
		} catch (NdvNotFoundException nnfe) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void ChangeTalonInfo(){
		try {
			tbl_talon.setData(new ArrayList<Talon>());
			TalonInfo = MainForm.tcl.getTalon(MainForm.authInfo.cpodr, curVrach, curSpec, getDatep);
			tbl_talon.setData(TalonInfo);
		} catch (TalonNotFoundException tnfe) {
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void ClearOtmetka(){
		otm_day_1.setSelected(false);
		otm_day_2.setSelected(false);
		otm_day_3.setSelected(false);
		otm_day_4.setSelected(false);
		otm_day_5.setSelected(false);
	}

	private void ClearTimePause() {
		tf_day1_p1.setValue(null);
		tf_day1_p2.setValue(null);
		tf_day2_p1.setValue(null);
		tf_day2_p2.setValue(null);
		tf_day3_p1.setValue(null);
		tf_day3_p2.setValue(null);
		tf_day4_p1.setValue(null);
		tf_day4_p2.setValue(null);
		tf_day5_p1.setValue(null);
		tf_day5_p2.setValue(null);
	}

	private void ShowOtmetka(){
		for (int i=0; i <= NraspInfo.size()-1; i++) {
			if (NraspInfo.get(i).isPfd()){
				if (NraspInfo.get(i).getDenn() == 1) otm_day_1.setSelected(NraspInfo.get(i).isPfd());
				if (NraspInfo.get(i).getDenn() == 2) otm_day_2.setSelected(NraspInfo.get(i).isPfd());
				if (NraspInfo.get(i).getDenn() == 3) otm_day_3.setSelected(NraspInfo.get(i).isPfd());
				if (NraspInfo.get(i).getDenn() == 4) otm_day_4.setSelected(NraspInfo.get(i).isPfd());
				if (NraspInfo.get(i).getDenn() == 5) otm_day_5.setSelected(NraspInfo.get(i).isPfd());
			}
		}
	}

	private void ShowTimePause(){
		for (int i=0; i <= NraspInfo.size()-1; i++){ 
			if (NraspInfo.get(i).getDenn() == 1){
				tf_day1_p1.setTime(NraspInfo.get(i).getTimep_n());
				tf_day1_p2.setTime(NraspInfo.get(i).getTimep_k());
			}
			if (NraspInfo.get(i).getDenn() == 2){
				tf_day2_p1.setTime(NraspInfo.get(i).getTimep_n());
				tf_day2_p2.setTime(NraspInfo.get(i).getTimep_k());
			}
			if (NraspInfo.get(i).getDenn() == 3){
				tf_day3_p1.setTime(NraspInfo.get(i).getTimep_n());
				tf_day3_p2.setTime(NraspInfo.get(i).getTimep_k());
			}
			if (NraspInfo.get(i).getDenn() == 4){
				tf_day4_p1.setTime(NraspInfo.get(i).getTimep_n());
				tf_day4_p2.setTime(NraspInfo.get(i).getTimep_k());
			}
			if (NraspInfo.get(i).getDenn() == 5){
				tf_day5_p1.setTime(NraspInfo.get(i).getTimep_n());
				tf_day5_p2.setTime(NraspInfo.get(i).getTimep_k());
			}
		}
	}

	public void ChangeDatap(){
		try{
			String numday = null;
			String nummonth = null;
			if (nday<10) numday = "0"+String.valueOf(nday);
			else numday = String.valueOf(nday);
			if (nmonth<10) nummonth = "0"+String.valueOf(nmonth);
			else nummonth = String.valueOf(nmonth);
			SimpleDateFormat frm = new SimpleDateFormat("dd.MM.yyyy");
			String strDat = numday+"."+nummonth+"."+String.valueOf(nyear);
			Date dat = frm.parse(strDat);
			getDatep = dat.getTime();
//		    System.out.println(strDat+ ",   "+ dat+ ",   "+ getDatep);
			ChangeTalonInfo();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	public void iterate(){
		     int num = 0;
			while (num < 1000){
		     pBar.setValue(num);
		     try {
		       Thread.sleep(500);
		     }
		       catch (InterruptedException e) {
		     }
		     num += 90;
		   }
	}
}
