package ru.nkz.ivcgzo.clientGenTalons;

import java.awt.Cursor;
//import java.util.Date;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import ru.nkz.ivcgzo.thriftGenTalon.VidpNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Vrach;
import ru.nkz.ivcgzo.clientGenTalons.RaspisanieUnit;
import ru.nkz.ivcgzo.clientGenTalons.SvodkiUnit;
import ru.nkz.ivcgzo.thriftGenTalon.TalonNotFoundException;

import javax.swing.JProgressBar;
import javax.swing.JComboBox;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import org.apache.thrift.TException;
import com.toedter.calendar.JCalendar;

import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.SwingConstants;

public class TalonMainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private static JCalendar calendar;
//	private static boolean dateSelected;
//	private long getDatep = 0;
	private JPanel contentPane;
	private JTree treevrach;
	private String curSpec = null;
	private int curVrach = 0;
	private int ind = 0;
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
	private CustomTable<Norm, Norm._Fields> tbl_norm;
	private CustomTable<Ndv, Ndv._Fields> tbl_ndv;
	private CustomTable<Talon, Talon._Fields> tbl_talon;
	private CustomDateEditor tf_datn;
	private CustomDateEditor tf_datk;
	private CustomDateEditor tf_sv1;
	private CustomDateEditor tf_sv2;
	private JComboBox<String> cmb_sv;
	private JComboBox<String> cmb_month;
	private JSpinner sp_god;
	private JSpinner sp_day;
	private int nmonth = 0;
	private int nyear = 0;
	private int nday = 0;
	private CustomTimeEditor tf_day1_pp1;
	private CustomTimeEditor tf_day1_pp2;
	private CustomTimeEditor tf_day1_int1;
	private CustomTimeEditor tf_day1_pd1;
	private CustomTimeEditor tf_day1_pause1;
	private CustomTimeEditor tf_day1_pd2;
	private CustomTimeEditor tf_day1_int2;
	private CustomTimeEditor tf_day1_pause2;
	private CustomTimeEditor tf_day1_pv1;
	private CustomTimeEditor tf_day1_pv2;
	private CustomTimeEditor tf_day2_pp1;
	private CustomTimeEditor tf_day2_pp2;
	private CustomTimeEditor tf_day2_pv2;
	private CustomTimeEditor tf_day2_pd2;
	private CustomTimeEditor tf_day2_int2;
	private CustomTimeEditor tf_day2_pv1;
	private CustomTimeEditor tf_day2_pd1;
	private CustomTimeEditor tf_day2_int1;
	private CustomTimeEditor tf_day2_pause1;
	private CustomTimeEditor tf_day2_pause2;
	private CustomTimeEditor tf_day3_pp1;
	private CustomTimeEditor tf_day3_pp2;
	private CustomTimeEditor tf_day3_pv1;
	private CustomTimeEditor tf_day3_pv2;
	private CustomTimeEditor tf_day3_pd1;
	private CustomTimeEditor tf_day3_pd2;
	private CustomTimeEditor tf_day3_int1;
	private CustomTimeEditor tf_day3_int2;
	private CustomTimeEditor tf_day3_pause1;
	private CustomTimeEditor tf_day3_pause2;
	private CustomTimeEditor tf_day4_int1;
	private CustomTimeEditor tf_day4_int2;
	private CustomTimeEditor tf_day4_pause1;
	private CustomTimeEditor tf_day4_pause2;
	private CustomTimeEditor tf_day4_pp1;
	private CustomTimeEditor tf_day4_pp2;
	private CustomTimeEditor tf_day4_pv1;
	private CustomTimeEditor tf_day4_pv2;
	private CustomTimeEditor tf_day4_pd1;
	private CustomTimeEditor tf_day4_pd2;
	private CustomTimeEditor tf_day5_int1;
	private CustomTimeEditor tf_day5_int2;
	private CustomTimeEditor tf_day5_pause1;
	private CustomTimeEditor tf_day5_pause2;
	private CustomTimeEditor tf_day5_pp1;
	private CustomTimeEditor tf_day5_pp2;
	private CustomTimeEditor tf_day5_pv1;
	private CustomTimeEditor tf_day5_pv2;
	private CustomTimeEditor tf_day5_pd1;
	private CustomTimeEditor tf_day5_pd2;
	private JPanel panel_13;
	private JPanel panel_14;

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
		setBounds(1, 1, 1036, 708); //(x,y,width,height)
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
//					ChangeDatap();
					ChangeTalonInfo();
				}
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tbMain, GroupLayout.PREFERRED_SIZE, 756, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(85, Short.MAX_VALUE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 646, Short.MAX_VALUE)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addGap(11)
					.addComponent(tbMain, GroupLayout.DEFAULT_SIZE, 635, Short.MAX_VALUE))
		);
		
		JPanel tbRasp = new JPanel();
		tbMain.addTab("Расписание приема", null, tbRasp, null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0412\u044B\u0431\u0435\u0440\u0438\u0442\u0435 \u0441\u0445\u0435\u043C\u0443 \u0440\u0430\u0441\u043F\u0438\u0441\u0430\u043D\u0438\u044F :", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_2 = new JPanel();
		
		panel_13 = new JPanel();
		panel_13.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0420\u0430\u0441\u043F\u0438\u0441\u0430\u043D\u0438\u0435 \u043F\u0440\u0438\u0435\u043C\u0430:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u041E\u0442\u043C\u0435\u043D\u0430 \u0444\u043E\u0440\u043C\u0438\u0440\u043E\u0432\u0430\u043D\u0438\u044F \u0442\u0430\u043B\u043E\u043D\u043E\u0432", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
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
						.addGroup(Alignment.LEADING, gl_panel_4.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, 138, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(sp_ndv, GroupLayout.PREFERRED_SIZE, 198, GroupLayout.PREFERRED_SIZE)
							.addGap(18)
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addComponent(btn_ndv_save)
								.addGroup(Alignment.TRAILING, gl_panel_4.createSequentialGroup()
									.addComponent(btn_ndv_add, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(btn_ndv_del, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)))
							.addGap(6))
				);
				gl_panel_4.setVerticalGroup(
					gl_panel_4.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
									.addComponent(sp_ndv, GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
									.addGroup(gl_panel_4.createSequentialGroup()
										.addGap(33)
										.addGroup(gl_panel_4.createParallelGroup(Alignment.BASELINE)
											.addComponent(btn_ndv_add)
											.addComponent(btn_ndv_del))
										.addPreferredGap(ComponentPlacement.RELATED, 23, Short.MAX_VALUE)
										.addComponent(btn_ndv_save, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
										.addGap(10)))
								.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, 115, GroupLayout.PREFERRED_SIZE))
							.addGap(32))
				);
				
				otm_day_1 = new JRadioButton("Понедельник");
				otm_day_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
				otm_day_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try{
							if (!NraspInfo.isEmpty()){
								for (int i=0; i <= NraspInfo.size()-1; i++) 
									if (NraspInfo.get(i).getDenn() == 1)
										NraspInfo.get(i).setPfd(otm_day_1.isSelected());
//								tbl_rasp.setData(NraspInfo);
								MainForm.tcl.updateNrasp(NraspInfo);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
				
				otm_day_2 = new JRadioButton("Вторник");
				otm_day_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
				otm_day_2.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try{
							if (!NraspInfo.isEmpty()){
								for (int i=0; i <= NraspInfo.size()-1; i++) 
									if (NraspInfo.get(i).getDenn() == 2)
										NraspInfo.get(i).setPfd(otm_day_2.isSelected());
//								tbl_rasp.setData(NraspInfo);
								MainForm.tcl.updateNrasp(NraspInfo);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
				
				otm_day_3 = new JRadioButton("Среда");
				otm_day_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
				otm_day_3.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try{
							if (!NraspInfo.isEmpty()){
								for (int i=0; i <= NraspInfo.size()-1; i++) 
									if (NraspInfo.get(i).getDenn() == 3)
										NraspInfo.get(i).setPfd(otm_day_3.isSelected());
//								tbl_rasp.setData(NraspInfo);
								MainForm.tcl.updateNrasp(NraspInfo);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
				
				otm_day_4 = new JRadioButton("Четверг");
				otm_day_4.setFont(new Font("Tahoma", Font.PLAIN, 12));
				otm_day_4.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try{
//							if (tbl_rasp.getSelectedItem() != null){
							if (!NraspInfo.isEmpty()){
								for (int i=0; i <= NraspInfo.size()-1; i++){ 
									if (NraspInfo.get(i).getDenn() == 4)
										NraspInfo.get(i).setPfd(otm_day_4.isSelected());
//		    				System.out.println(NraspInfo.get(i).getId()+", "+NraspInfo.get(i).isPfd());
								}
								MainForm.tcl.updateNrasp(NraspInfo);
							}
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					}
				});
				
				otm_day_5 = new JRadioButton("Пятница");
				otm_day_5.setFont(new Font("Tahoma", Font.PLAIN, 12));
				otm_day_5.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						try{
							if (!NraspInfo.isEmpty()){
								for (int i=0; i <= NraspInfo.size()-1; i++) 
									if (NraspInfo.get(i).getDenn() == 5)
										NraspInfo.get(i).setPfd(otm_day_5.isSelected());
//								tbl_rasp.setData(NraspInfo);
								MainForm.tcl.updateNrasp(NraspInfo);
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
				tbl_ndv.setFont(new Font("Tahoma", Font.PLAIN, 12));
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
		GroupLayout gl_tbRasp = new GroupLayout(tbRasp);
		gl_tbRasp.setHorizontalGroup(
			gl_tbRasp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tbRasp.createSequentialGroup()
					.addGroup(gl_tbRasp.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_tbRasp.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_13, GroupLayout.DEFAULT_SIZE, 731, Short.MAX_VALUE))
						.addGroup(gl_tbRasp.createSequentialGroup()
							.addContainerGap()
							.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 495, GroupLayout.PREFERRED_SIZE))
						.addGroup(Alignment.TRAILING, gl_tbRasp.createSequentialGroup()
							.addGap(23)
							.addGroup(gl_tbRasp.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 367, GroupLayout.PREFERRED_SIZE)
								.addGroup(Alignment.TRAILING, gl_tbRasp.createSequentialGroup()
									.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 359, GroupLayout.PREFERRED_SIZE)
									.addGap(333)))))
					.addContainerGap())
		);
		gl_tbRasp.setVerticalGroup(
			gl_tbRasp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tbRasp.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_13, GroupLayout.PREFERRED_SIZE, 242, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 257, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JButton btn_save = new JButton("Сохранить");
		btn_save.setToolTipText("Сохранить расписание");
		btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				try{
//                    tbl_rasp.updateSelectedItem();
//					if (tbl_rasp.getSelectedItem() != null)
//						MainForm.tcl.updateNrasp(tbl_rasp.getData());
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
				try{
//					if (tbl_rasp.getSelectedItem() != null){
						for (int i=0; i <= NraspInfo.size()-1; i++){
							if (NraspInfo.get(i).getDenn() == 1){
								if (NraspInfo.get(i).getVidp() == 1){
									NraspInfo.get(i).setTime_n(tf_day1_pp1.getTime().getTime());
									NraspInfo.get(i).setTime_k(tf_day1_pp2.getTime().getTime());
								}
								if (NraspInfo.get(i).getVidp() == 2){
									NraspInfo.get(i).setTime_n(tf_day1_pv1.getTime().getTime());
									NraspInfo.get(i).setTime_k(tf_day1_pv2.getTime().getTime());
								}
								if (NraspInfo.get(i).getVidp() == 3){
									NraspInfo.get(i).setTime_n(tf_day1_pd1.getTime().getTime());
									NraspInfo.get(i).setTime_k(tf_day1_pd2.getTime().getTime());
								}
								NraspInfo.get(i).setTime_int_n(tf_day1_int1.getTime().getTime());
								NraspInfo.get(i).setTime_int_k(tf_day1_int2.getTime().getTime());
								NraspInfo.get(i).setTimep_n(tf_day1_pause1.getTime().getTime());
								NraspInfo.get(i).setTimep_k(tf_day1_pause2.getTime().getTime());
							}
							if (NraspInfo.get(i).getDenn() == 2){
								if (NraspInfo.get(i).getVidp() == 1){
									NraspInfo.get(i).setTime_n(tf_day2_pp1.getTime().getTime());
									NraspInfo.get(i).setTime_k(tf_day2_pp2.getTime().getTime());
								}
								if (NraspInfo.get(i).getVidp() == 2){
									NraspInfo.get(i).setTime_n(tf_day2_pv1.getTime().getTime());
									NraspInfo.get(i).setTime_k(tf_day2_pv2.getTime().getTime());
								}
								if (NraspInfo.get(i).getVidp() == 3){
									NraspInfo.get(i).setTime_n(tf_day2_pd1.getTime().getTime());
									NraspInfo.get(i).setTime_k(tf_day2_pd2.getTime().getTime());
								}
								NraspInfo.get(i).setTime_int_n(tf_day2_int1.getTime().getTime());
								NraspInfo.get(i).setTime_int_k(tf_day2_int2.getTime().getTime());
								NraspInfo.get(i).setTimep_n(tf_day2_pause1.getTime().getTime());
								NraspInfo.get(i).setTimep_k(tf_day2_pause2.getTime().getTime());
							}
							if (NraspInfo.get(i).getDenn() == 3){
								if (NraspInfo.get(i).getVidp() == 1){
									NraspInfo.get(i).setTime_n(tf_day3_pp1.getTime().getTime());
									NraspInfo.get(i).setTime_k(tf_day3_pp2.getTime().getTime());
								}
								if (NraspInfo.get(i).getVidp() == 2){
									NraspInfo.get(i).setTime_n(tf_day3_pv1.getTime().getTime());
									NraspInfo.get(i).setTime_k(tf_day3_pv2.getTime().getTime());
								}
								if (NraspInfo.get(i).getVidp() == 3){
									NraspInfo.get(i).setTime_n(tf_day3_pd1.getTime().getTime());
									NraspInfo.get(i).setTime_k(tf_day3_pd2.getTime().getTime());
								}
								NraspInfo.get(i).setTime_int_n(tf_day3_int1.getTime().getTime());
								NraspInfo.get(i).setTime_int_k(tf_day3_int2.getTime().getTime());
								NraspInfo.get(i).setTimep_n(tf_day3_pause1.getTime().getTime());
								NraspInfo.get(i).setTimep_k(tf_day3_pause2.getTime().getTime());
							}
							if (NraspInfo.get(i).getDenn() == 4){
								if (NraspInfo.get(i).getVidp() == 1){
									NraspInfo.get(i).setTime_n(tf_day4_pp1.getTime().getTime());
									NraspInfo.get(i).setTime_k(tf_day4_pp2.getTime().getTime());
								}
								if (NraspInfo.get(i).getVidp() == 2){
									NraspInfo.get(i).setTime_n(tf_day4_pv1.getTime().getTime());
									NraspInfo.get(i).setTime_k(tf_day4_pv2.getTime().getTime());
								}
									if (NraspInfo.get(i).getVidp() == 3){
									NraspInfo.get(i).setTime_n(tf_day4_pd1.getTime().getTime());
									NraspInfo.get(i).setTime_k(tf_day4_pd2.getTime().getTime());
								}
								NraspInfo.get(i).setTime_int_n(tf_day4_int1.getTime().getTime());
								NraspInfo.get(i).setTime_int_k(tf_day4_int2.getTime().getTime());
								NraspInfo.get(i).setTimep_n(tf_day4_pause1.getTime().getTime());
								NraspInfo.get(i).setTimep_k(tf_day4_pause2.getTime().getTime());
							}
							if (NraspInfo.get(i).getDenn() == 5){
								if (NraspInfo.get(i).getVidp() == 1){
									NraspInfo.get(i).setTime_n(tf_day5_pp1.getTime().getTime());
									NraspInfo.get(i).setTime_k(tf_day5_pp2.getTime().getTime());
								}
								if (NraspInfo.get(i).getVidp() == 2){
									NraspInfo.get(i).setTime_n(tf_day5_pv1.getTime().getTime());
									NraspInfo.get(i).setTime_k(tf_day5_pv2.getTime().getTime());
								}
								if (NraspInfo.get(i).getVidp() == 3){
									NraspInfo.get(i).setTime_n(tf_day5_pd1.getTime().getTime());
									NraspInfo.get(i).setTime_k(tf_day5_pd2.getTime().getTime());
								}
								NraspInfo.get(i).setTime_int_n(tf_day5_int1.getTime().getTime());
								NraspInfo.get(i).setTime_int_k(tf_day5_int2.getTime().getTime());
								NraspInfo.get(i).setTimep_n(tf_day5_pause1.getTime().getTime());
								NraspInfo.get(i).setTimep_k(tf_day5_pause2.getTime().getTime());
							}
						}
						
//						tbl_rasp.setData(NraspInfo);
						MainForm.tcl.updateNrasp(NraspInfo);
//					}
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
					MainForm.tcl.deleteNrasp(MainForm.authInfo.cpodr, curVrach, curSpec);
//					tbl_rasp.setData(new ArrayList<Nrasp>());
					ClearOtmetka();
					ClearTimePause();
				} catch (KmiacServerException kse){
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		
		JPanel panel_17 = new JPanel();
		panel_17.setBorder(new TitledBorder(UIManager.getBorder("CheckBoxMenuItem.border"), "\u0412\u0438\u0434 \u043F\u0440\u0438\u0435\u043C\u0430:", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		
		JPanel panel_18 = new JPanel();
		panel_18.setBorder(new TitledBorder(null, "\u0412\u0440\u0435\u043C\u044F \u043F\u0440\u0438\u0435\u043C\u0430 (\u0427\u0427 : \u041C\u041C)", TitledBorder.CENTER, TitledBorder.TOP, null, null));
		GroupLayout gl_panel_13 = new GroupLayout(panel_13);
		gl_panel_13.setHorizontalGroup(
			gl_panel_13.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel_13.createSequentialGroup()
					.addGroup(gl_panel_13.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(Alignment.LEADING, gl_panel_13.createSequentialGroup()
							.addGap(232)
							.addComponent(btn_save)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btn_del))
						.addGroup(Alignment.LEADING, gl_panel_13.createSequentialGroup()
							.addComponent(panel_17, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel_18, GroupLayout.PREFERRED_SIZE, 575, GroupLayout.PREFERRED_SIZE)))
					.addGap(47))
		);
		gl_panel_13.setVerticalGroup(
			gl_panel_13.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_13.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_13.createParallelGroup(Alignment.BASELINE)
						.addComponent(panel_17, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
						.addComponent(panel_18, GroupLayout.PREFERRED_SIZE, 172, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_13.createParallelGroup(Alignment.BASELINE)
						.addComponent(btn_save)
						.addComponent(btn_del))
					.addContainerGap())
		);
		
		panel_14 = new JPanel();
		panel_14.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u041F\u043E\u043D\u0435\u0434\u0435\u043B\u044C\u043D\u0438\u043A", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		tf_day1_pp1 = new CustomTimeEditor();
		tf_day1_pp1.setColumns(10);
		
		tf_day1_pp2 = new CustomTimeEditor();
		tf_day1_pp2.setColumns(10);
		
		tf_day1_int1 = new CustomTimeEditor();
		tf_day1_int1.setColumns(10);
		
		tf_day1_pd1 = new CustomTimeEditor();
		tf_day1_pd1.setColumns(10);
		
		tf_day1_pause1 = new CustomTimeEditor();
		tf_day1_pause1.setColumns(10);
		
		tf_day1_pd2 = new CustomTimeEditor();
		tf_day1_pd2.setColumns(10);
		
		tf_day1_int2 = new CustomTimeEditor();
		tf_day1_int2.setColumns(10);
		
		tf_day1_pause2 = new CustomTimeEditor();
		tf_day1_pause2.setColumns(10);
		
		tf_day1_pv1 = new CustomTimeEditor();
		tf_day1_pv1.setColumns(10);
		
		tf_day1_pv2 = new CustomTimeEditor();
		tf_day1_pv2.setColumns(10);
		GroupLayout gl_panel_14 = new GroupLayout(panel_14);
		gl_panel_14.setHorizontalGroup(
			gl_panel_14.createParallelGroup(Alignment.LEADING)
				.addGap(0, 102, Short.MAX_VALUE)
				.addGroup(gl_panel_14.createSequentialGroup()
					.addGroup(gl_panel_14.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_14.createSequentialGroup()
							.addComponent(tf_day1_pp1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_day1_pp2, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_14.createSequentialGroup()
							.addGroup(gl_panel_14.createParallelGroup(Alignment.LEADING)
								.addComponent(tf_day1_int1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
								.addComponent(tf_day1_pd1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
								.addComponent(tf_day1_pause1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_14.createParallelGroup(Alignment.LEADING)
								.addComponent(tf_day1_pd2, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
								.addComponent(tf_day1_int2, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
								.addComponent(tf_day1_pause2, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_14.createSequentialGroup()
							.addComponent(tf_day1_pv1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_day1_pv2, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)))
					.addGap(34))
		);
		gl_panel_14.setVerticalGroup(
			gl_panel_14.createParallelGroup(Alignment.LEADING)
				.addGap(0, 152, Short.MAX_VALUE)
				.addGroup(gl_panel_14.createSequentialGroup()
					.addGroup(gl_panel_14.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day1_pp1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day1_pp2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_14.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day1_pv1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day1_pv2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_panel_14.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day1_pd1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day1_pd2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_14.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day1_int1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day1_int2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_14.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day1_pause1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day1_pause2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(63, Short.MAX_VALUE))
		);
		panel_14.setLayout(gl_panel_14);
		
		JPanel panel_12 = new JPanel();
		panel_12.setBorder(new TitledBorder(null, "\u0421\u0440\u0435\u0434\u0430", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		tf_day3_pp1 = new CustomTimeEditor();
		tf_day3_pp1.setColumns(10);
		
		tf_day3_pp2 = new CustomTimeEditor();
		tf_day3_pp2.setColumns(10);
		
		tf_day3_pv1 = new CustomTimeEditor();
		tf_day3_pv1.setColumns(10);
		
		tf_day3_pv2 = new CustomTimeEditor();
		tf_day3_pv2.setColumns(10);
		
		tf_day3_pd1 = new CustomTimeEditor();
		tf_day3_pd1.setColumns(10);
		
		tf_day3_pd2 = new CustomTimeEditor();
		tf_day3_pd2.setColumns(10);
		
		tf_day3_int1 = new CustomTimeEditor();
		tf_day3_int1.setColumns(10);
		
		tf_day3_int2 = new CustomTimeEditor();
		tf_day3_int2.setColumns(10);
		
		tf_day3_pause1 = new CustomTimeEditor();
		tf_day3_pause1.setColumns(10);
		
		tf_day3_pause2 = new CustomTimeEditor();
		tf_day3_pause2.setColumns(10);
		GroupLayout gl_panel_12 = new GroupLayout(panel_12);
		gl_panel_12.setHorizontalGroup(
			gl_panel_12.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_12.createSequentialGroup()
					.addGroup(gl_panel_12.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_12.createSequentialGroup()
							.addComponent(tf_day3_pp1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_day3_pp2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_12.createSequentialGroup()
							.addComponent(tf_day3_pv1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_day3_pv2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_12.createSequentialGroup()
							.addGroup(gl_panel_12.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(tf_day3_pause1, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(tf_day3_int1, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(tf_day3_pd1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_12.createParallelGroup(Alignment.LEADING, false)
								.addComponent(tf_day3_pause2, 0, 0, Short.MAX_VALUE)
								.addComponent(tf_day3_int2, 0, 0, Short.MAX_VALUE)
								.addComponent(tf_day3_pd2, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))))
					.addGap(3))
		);
		gl_panel_12.setVerticalGroup(
			gl_panel_12.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_12.createSequentialGroup()
					.addGroup(gl_panel_12.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day3_pp1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day3_pp2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_12.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day3_pv1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day3_pv2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_12.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day3_pd1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day3_pd2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_12.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day3_int1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day3_int2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_12.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day3_pause1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day3_pause2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_12.setLayout(gl_panel_12);
		
		JPanel panel_15 = new JPanel();
		panel_15.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u0427\u0435\u0442\u0432\u0435\u0440\u0433", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		tf_day4_int1 = new CustomTimeEditor();
		tf_day4_int1.setColumns(10);
		
		tf_day4_int2 = new CustomTimeEditor();
		tf_day4_int2.setColumns(10);
		
		tf_day4_pause1 = new CustomTimeEditor();
		tf_day4_pause1.setColumns(10);
		
		tf_day4_pause2 = new CustomTimeEditor();
		tf_day4_pause2.setColumns(10);
		
		tf_day4_pp1 = new CustomTimeEditor();
		tf_day4_pp1.setColumns(10);
		
		tf_day4_pp2 = new CustomTimeEditor();
		tf_day4_pp2.setColumns(10);
		
		tf_day4_pv1 = new CustomTimeEditor();
		tf_day4_pv1.setColumns(10);
		
		tf_day4_pv2 = new CustomTimeEditor();
		tf_day4_pv2.setColumns(10);
		
		tf_day4_pd1 = new CustomTimeEditor();
		tf_day4_pd1.setColumns(10);
		
		tf_day4_pd2 = new CustomTimeEditor();
		tf_day4_pd2.setColumns(10);
		GroupLayout gl_panel_15 = new GroupLayout(panel_15);
		gl_panel_15.setHorizontalGroup(
			gl_panel_15.createParallelGroup(Alignment.LEADING)
				.addGap(0, 111, Short.MAX_VALUE)
				.addGroup(gl_panel_15.createSequentialGroup()
					.addGroup(gl_panel_15.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_15.createSequentialGroup()
							.addComponent(tf_day4_pp1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_day4_pp2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_15.createSequentialGroup()
							.addComponent(tf_day4_pv1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_day4_pv2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_15.createSequentialGroup()
							.addGroup(gl_panel_15.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(tf_day4_pause1, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(tf_day4_int1, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(tf_day4_pd1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_15.createParallelGroup(Alignment.LEADING, false)
								.addComponent(tf_day4_pause2, 0, 0, Short.MAX_VALUE)
								.addComponent(tf_day4_int2, 0, 0, Short.MAX_VALUE)
								.addComponent(tf_day4_pd2, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))))
					.addGap(3))
		);
		gl_panel_15.setVerticalGroup(
			gl_panel_15.createParallelGroup(Alignment.LEADING)
				.addGap(0, 152, Short.MAX_VALUE)
				.addGroup(gl_panel_15.createSequentialGroup()
					.addGroup(gl_panel_15.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day4_pp1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day4_pp2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_15.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day4_pv1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day4_pv2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_15.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day4_pd1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day4_pd2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_15.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day4_int1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day4_int2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_15.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day4_pause1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day4_pause2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_15.setLayout(gl_panel_15);
		
		JPanel panel_16 = new JPanel();
		panel_16.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "\u041F\u044F\u0442\u043D\u0438\u0446\u0430", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		tf_day5_int1 = new CustomTimeEditor();
		tf_day5_int1.setColumns(10);
		
		tf_day5_int2 = new CustomTimeEditor();
		tf_day5_int2.setColumns(10);
		
		tf_day5_pause1 = new CustomTimeEditor();
		tf_day5_pause1.setColumns(10);
		
		tf_day5_pause2 = new CustomTimeEditor();
		tf_day5_pause2.setColumns(10);
		
		tf_day5_pp1 = new CustomTimeEditor();
		tf_day5_pp1.setColumns(10);
		
		tf_day5_pp2 = new CustomTimeEditor();
		tf_day5_pp2.setColumns(10);
		
		tf_day5_pv1 = new CustomTimeEditor();
		tf_day5_pv1.setColumns(10);
		
		tf_day5_pv2 = new CustomTimeEditor();
		tf_day5_pv2.setColumns(10);
		
		tf_day5_pd1 = new CustomTimeEditor();
		tf_day5_pd1.setColumns(10);
		
		tf_day5_pd2 = new CustomTimeEditor();
		tf_day5_pd2.setColumns(10);
		GroupLayout gl_panel_16 = new GroupLayout(panel_16);
		gl_panel_16.setHorizontalGroup(
			gl_panel_16.createParallelGroup(Alignment.LEADING)
				.addGap(0, 111, Short.MAX_VALUE)
				.addGroup(gl_panel_16.createSequentialGroup()
					.addGroup(gl_panel_16.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_16.createSequentialGroup()
							.addComponent(tf_day5_pp1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_day5_pp2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_16.createSequentialGroup()
							.addComponent(tf_day5_pv1, GroupLayout.PREFERRED_SIZE, 44, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tf_day5_pv2, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_16.createSequentialGroup()
							.addGroup(gl_panel_16.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(tf_day5_pause1, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(tf_day5_int1, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
								.addComponent(tf_day5_pd1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_16.createParallelGroup(Alignment.LEADING, false)
								.addComponent(tf_day5_pause2, 0, 0, Short.MAX_VALUE)
								.addComponent(tf_day5_int2, 0, 0, Short.MAX_VALUE)
								.addComponent(tf_day5_pd2, GroupLayout.DEFAULT_SIZE, 42, Short.MAX_VALUE))))
					.addGap(3))
		);
		gl_panel_16.setVerticalGroup(
			gl_panel_16.createParallelGroup(Alignment.LEADING)
				.addGap(0, 152, Short.MAX_VALUE)
				.addGroup(gl_panel_16.createSequentialGroup()
					.addGroup(gl_panel_16.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day5_pp1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day5_pp2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_16.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day5_pv1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day5_pv2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_16.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day5_pd1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day5_pd2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_16.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day5_int1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day5_int2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_16.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day5_pause1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day5_pause2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_16.setLayout(gl_panel_16);
		
		JPanel panel_11 = new JPanel();
		panel_11.setBorder(new TitledBorder(null, "\u0412\u0442\u043E\u0440\u043D\u0438\u043A", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		tf_day2_pp1 = new CustomTimeEditor();
		tf_day2_pp1.setColumns(10);
		
		tf_day2_pp2 = new CustomTimeEditor();
		tf_day2_pp2.setColumns(10);
		
		tf_day2_pv2 = new CustomTimeEditor();
		tf_day2_pv2.setColumns(10);
		
		tf_day2_pd2 = new CustomTimeEditor();
		tf_day2_pd2.setColumns(10);
		
		tf_day2_int2 = new CustomTimeEditor();
		tf_day2_int2.setColumns(10);
		
		tf_day2_pv1 = new CustomTimeEditor();
		tf_day2_pv1.setColumns(10);
		
		tf_day2_pd1 = new CustomTimeEditor();
		tf_day2_pd1.setColumns(10);
		
		tf_day2_int1 = new CustomTimeEditor();
		tf_day2_int1.setColumns(10);
		
		tf_day2_pause1 = new CustomTimeEditor();
		tf_day2_pause1.setColumns(10);
		
		tf_day2_pause2 = new CustomTimeEditor();
		tf_day2_pause2.setColumns(10);
		GroupLayout gl_panel_11 = new GroupLayout(panel_11);
		gl_panel_11.setHorizontalGroup(
			gl_panel_11.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_11.createSequentialGroup()
					.addComponent(tf_day2_pp1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tf_day2_pp2, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_11.createParallelGroup(Alignment.TRAILING)
					.addGroup(gl_panel_11.createSequentialGroup()
						.addGroup(gl_panel_11.createParallelGroup(Alignment.LEADING)
							.addComponent(tf_day2_int1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addComponent(tf_day2_pd1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addComponent(tf_day2_pause1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(gl_panel_11.createParallelGroup(Alignment.LEADING)
							.addComponent(tf_day2_pd2, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
							.addComponent(tf_day2_int2, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
							.addComponent(tf_day2_pause2, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
						.addGap(34))
					.addGroup(Alignment.LEADING, gl_panel_11.createSequentialGroup()
						.addComponent(tf_day2_pv1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(tf_day2_pv2, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)))
		);
		gl_panel_11.setVerticalGroup(
			gl_panel_11.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_11.createSequentialGroup()
					.addGroup(gl_panel_11.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day2_pp1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day2_pp2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_11.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day2_pv1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day2_pv2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(6)
					.addGroup(gl_panel_11.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day2_pd1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day2_pd2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_11.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day2_int1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day2_int2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_11.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_day2_pause1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_day2_pause2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(63, Short.MAX_VALUE))
		);
		panel_11.setLayout(gl_panel_11);
		GroupLayout gl_panel_18 = new GroupLayout(panel_18);
		gl_panel_18.setHorizontalGroup(
			gl_panel_18.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_18.createSequentialGroup()
					.addComponent(panel_14, GroupLayout.PREFERRED_SIZE, 106, GroupLayout.PREFERRED_SIZE)
					.addGap(1)
					.addComponent(panel_11, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_12, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_15, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_16, GroupLayout.PREFERRED_SIZE, 111, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(12, Short.MAX_VALUE))
		);
		gl_panel_18.setVerticalGroup(
			gl_panel_18.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_18.createSequentialGroup()
					.addGroup(gl_panel_18.createParallelGroup(Alignment.BASELINE)
						.addComponent(panel_14, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_11, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_12, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_15, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_16, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel_18.setLayout(gl_panel_18);
		
		JLabel lblNewLabel_14 = new JLabel("Первичный");
		lblNewLabel_14.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblNewLabel_15 = new JLabel("Повторный");
		lblNewLabel_15.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblNewLabel_16 = new JLabel("На дому");
		lblNewLabel_16.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblNewLabel_17 = new JLabel("Интернет");
		lblNewLabel_17.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblNewLabel_18 = new JLabel("Перерыв");
		lblNewLabel_18.setFont(new Font("Tahoma", Font.PLAIN, 12));
		GroupLayout gl_panel_17 = new GroupLayout(panel_17);
		gl_panel_17.setHorizontalGroup(
			gl_panel_17.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_17.createSequentialGroup()
					.addGroup(gl_panel_17.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_panel_17.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(lblNewLabel_14))
						.addGroup(gl_panel_17.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_panel_17.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel_16)
								.addComponent(lblNewLabel_15)))
						.addGroup(gl_panel_17.createSequentialGroup()
							.addGap(14)
							.addComponent(lblNewLabel_17, GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE))
						.addGroup(gl_panel_17.createSequentialGroup()
							.addContainerGap(24, Short.MAX_VALUE)
							.addComponent(lblNewLabel_18)))
					.addGap(5))
		);
		gl_panel_17.setVerticalGroup(
			gl_panel_17.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel_17.createSequentialGroup()
					.addGap(19)
					.addComponent(lblNewLabel_14)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_15)
					.addGap(11)
					.addComponent(lblNewLabel_16)
					.addGap(11)
					.addComponent(lblNewLabel_17)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_18)
					.addContainerGap(25, Short.MAX_VALUE))
		);
		panel_17.setLayout(gl_panel_17);
		panel_13.setLayout(gl_panel_13);
		
		cbx_rasp = new JCheckBox("учитывать четные и нечетные дни ");
		cbx_rasp.setFont(new Font("Tahoma", Font.PLAIN, 12));
		JButton btn_new = new JButton("Создать");
		btn_new.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					MainForm.tcl.deleteNrasp(MainForm.authInfo.cpodr, curVrach, curSpec);
					ClearOtmetka();
					ClearTimePause();
				} catch (KmiacServerException kse){
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				if (cbx_rasp.isSelected()){
					RaspisanieUnit.NewRaspisanie(MainForm.authInfo.cpodr, curVrach, curSpec,1);
				}
				else {
					RaspisanieUnit.NewRaspisanie(MainForm.authInfo.cpodr, curVrach, curSpec, 0);
				}
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
		cxm_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cxm_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClearOtmetka();
				ChangeCxemaInfo(0);			}
		});
		btnGroup_cxema.add(cxm_1);
		
		cxm_2 = new JRadioButton("четные дни");
		cxm_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cxm_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ClearOtmetka();
				ChangeCxemaInfo(1);			}
		});
		btnGroup_cxema.add(cxm_2);
		
		cxm_3 = new JRadioButton("нечетные дни");
		cxm_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
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
		tbl_norm.setFont(new Font("Tahoma", Font.PLAIN, 12));
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
			gl_tbTalon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tbTalon.createSequentialGroup()
					.addGap(25)
					.addGroup(gl_tbTalon.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_tbTalon.createSequentialGroup()
							.addGap(10)
							.addComponent(panel_10, 0, 0, Short.MAX_VALUE)
							.addContainerGap())
						.addGroup(gl_tbTalon.createSequentialGroup()
							.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
							.addGap(129))))
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
        cmb_month.setFont(new Font("Tahoma", Font.PLAIN, 12));
        cmb_month.setModel(new DefaultComboBoxModel<>(items));
        cmb_month.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
			    System.out.println(nday+ ",   "+ nmonth+ ",   "+ nyear);
				if (nmonth != 0 && nday != 0 && nyear != 0){
	                nmonth = cmb_month.getSelectedIndex()+1;
	                sp_day.setValue(1);
//	                ChangeDatap();
				}
        	}
        });
        cmb_month.setMaximumRowCount(12);
        cmb_month.setVisible(false);

        sp_god = new JSpinner();
        sp_god.setVisible(false);
        sp_god.setFont(new Font("Tahoma", Font.PLAIN, 12));
        sp_god.setModel(new SpinnerNumberModel(new Integer(2012), new Integer(2012), null, new Integer(1)));
        sp_god.addChangeListener(new ChangeListener() {
        	public void stateChanged(ChangeEvent arg0) {
			    System.out.println(nday+ ",   "+ nmonth+ ",   "+ nyear);
				if (nmonth != 0 && nday != 0 && nyear != 0){
	                nyear = Integer.valueOf(sp_god.getValue().toString());
//					ChangeDatap();
				}
        	}
        });
		sp_day = new JSpinner();
		sp_day.setVisible(false);
		sp_day.setFont(new Font("Tahoma", Font.PLAIN, 12));
		sp_day.setModel(new SpinnerNumberModel(2, 1, 31, 1));
		sp_day.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
//			    System.out.println(nday+ ",   "+ nmonth+ ",   "+ nyear);
//				if (cmb_month.getSelectedIndex() != 0 && Integer.valueOf(sp_day.getValue().toString()) != 0 && Integer.valueOf(sp_god.getValue().toString()) != 0){
				if (nmonth != 0 && nday != 0 && nyear != 0){
			        nday = Integer.valueOf(sp_day.getValue().toString());
//					ChangeDatap();
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
		lblNewLabel_11.setFont(new Font("Tahoma", Font.PLAIN, 12));
		JLabel lblNewLabel_12 = new JLabel("Месяц");
		lblNewLabel_12.setFont(new Font("Tahoma", Font.PLAIN, 12));
		JLabel lblNewLabel_13 = new JLabel("День");
		lblNewLabel_13.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblNewLabel_11.setVisible(false);
		lblNewLabel_12.setVisible(false);
		lblNewLabel_13.setVisible(false);
		JScrollPane scrollPane_1 = new JScrollPane();
		
		calendar = new JCalendar();
		calendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
//				dateSelected = true;
//				ChangeDatap();
				ChangeTalonInfo();
			}
		});
		calendar.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		GroupLayout gl_panel_10 = new GroupLayout(panel_10);
		gl_panel_10.setHorizontalGroup(
			gl_panel_10.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_10.createSequentialGroup()
					.addGap(24)
					.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 171, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_panel_10.createParallelGroup(Alignment.LEADING)
						.addComponent(calendar, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_10.createSequentialGroup()
							.addGap(19)
							.addComponent(lblNewLabel_13))
						.addComponent(sp_day, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_10.createSequentialGroup()
							.addGap(11)
							.addComponent(lblNewLabel_11))
						.addComponent(sp_god, GroupLayout.PREFERRED_SIZE, 59, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel_10.createSequentialGroup()
							.addGap(17)
							.addComponent(lblNewLabel_12))
						.addComponent(cmb_month, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(303, Short.MAX_VALUE))
		);
		gl_panel_10.setVerticalGroup(
			gl_panel_10.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_10.createSequentialGroup()
					.addGroup(gl_panel_10.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_10.createSequentialGroup()
							.addComponent(calendar, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_11)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(sp_god, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_13)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(sp_day, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblNewLabel_12)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(cmb_month, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPane_1, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 416, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		tbl_talon =new CustomTable<>(true, true, Talon.class, 11,"Время", 6,"Вид приема");
		tbl_talon.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tbl_talon.setPreferredWidths(70,90);
		tbl_talon.setTimeField(0);
		tbl_talon.setEditableFields(false, 0,1);
		tbl_talon.setFillsViewportHeight(true);
		scrollPane_1.setViewportView(tbl_talon);
		panel_10.setLayout(gl_panel_10);
		
		tf_datn = new CustomDateEditor();
		tf_datn.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tf_datk = new CustomDateEditor();
		tf_datk.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tf_datn.setDate(System.currentTimeMillis());
		tf_datk.setDate(System.currentTimeMillis());

		JButton btnNewButton = new JButton("Формировать");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 12));
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
// 					    if (nmonth != 0 && nday != 0 && nyear != 0){
//			                ChangeDatap();
//						}
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
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
		JLabel lblNewLabel_1 = new JLabel("по");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		tal_1 = new JRadioButton("все специалисты");
		tal_1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnGroup_talon.add(tal_1);
		
		tal_2 = new JRadioButton("для выбранной специальности");
		tal_2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnGroup_talon.add(tal_2);
		
		tal_3 = new JRadioButton("для выбранного врача");
		tal_3.setHorizontalAlignment(SwingConstants.CENTER);
		tal_3.setFont(new Font("Tahoma", Font.PLAIN, 12));
		btnGroup_talon.add(tal_3);
		tal_3.setSelected(true);
		
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
		lblNewLabel_8.setFont(new Font("Tahoma", Font.PLAIN, 12));
		JLabel lblNewLabel_9 = new JLabel("За период  с");
		lblNewLabel_9.setFont(new Font("Tahoma", Font.PLAIN, 12));
		JLabel lblNewLabel_10 = new JLabel("по");
		lblNewLabel_10.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
        String[] items1 = {
       		"1. Списки пациентов, записанных на прием",
       		"2. Количество неиспользованных талонов",
       		"3. Списки пациентов, отмененного приема",
       		"4. Количество выданных талонов",
       		"5. Расписание работы врачей",
       		"6. Печать талонов на прием к врачу"
        };

		cmb_sv = new JComboBox<String>();
		cmb_sv.setFont(new Font("Tahoma", Font.PLAIN, 12));
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
		tf_sv2.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tf_sv1 = new CustomDateEditor();
		tf_sv1.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tf_sv1.setDate(System.currentTimeMillis());
		tf_sv2.setDate(System.currentTimeMillis());
		
		JButton btnSvod = new JButton("Создать сводку");
		btnSvod.setFont(new Font("Tahoma", Font.PLAIN, 12));
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
					if (tbMain.getSelectedIndex() == 2) ChangeTalonInfo();
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
//			tbl_rasp.setIntegerClassifierSelector(1, MainForm.tcl.getVidp());
			tbl_norm.setIntegerClassifierSelector(0, MainForm.tcl.getVidp());
			tbl_talon.setIntegerClassifierSelector(1, MainForm.tcl.getVidp());
		} catch (KmiacServerException kse){
		} catch (VidpNotFoundException vnfe){
		} catch (Exception e1) {
			e1.printStackTrace();
		}
//		try {
//			MainForm.tcl.getAzt();
//			tbl_rasp.setIntegerClassifierSelector(0, MainForm.tcl.getAzt());
//		} catch (KmiacServerException kse){
//		} catch (AztNotFoundException anfe){
//		} catch (Exception e1) {
//			e1.printStackTrace();
//		}
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
//			tbl_rasp.setData(new ArrayList<Nrasp>());
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
//    			tbl_rasp.setData(NraspInfo);
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
//			tbl_rasp.setData(new ArrayList<Nrasp>());
			NraspInfo = MainForm.tcl.getNrasp(MainForm.authInfo.cpodr, curVrach, curSpec, cxm);
            if (!NraspInfo.isEmpty()) {
//    			tbl_rasp.setData(NraspInfo);
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
			TalonInfo = MainForm.tcl.getTalon(MainForm.authInfo.cpodr, curVrach, curSpec, calendar.getDate().getTime());
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
		tf_day1_pause1.setValue(null);
		tf_day1_pause2.setValue(null);
		tf_day2_pause1.setValue(null);
		tf_day2_pause2.setValue(null);
		tf_day3_pause1.setValue(null);
		tf_day3_pause2.setValue(null);
		tf_day4_pause1.setValue(null);
		tf_day4_pause2.setValue(null);
		tf_day5_pause1.setValue(null);
		tf_day5_pause2.setValue(null);
		tf_day1_pause1.setValue(null);
		tf_day1_pause1.setValue(null);

		tf_day1_pp1.setValue(null);
		tf_day1_pp2.setValue(null);
		tf_day2_pp1.setValue(null);
		tf_day2_pp2.setValue(null);
		tf_day3_pp1.setValue(null);
		tf_day3_pp2.setValue(null);
		tf_day4_pp1.setValue(null);
		tf_day4_pp2.setValue(null);
		tf_day5_pp1.setValue(null);
		tf_day5_pp2.setValue(null);

		tf_day1_pv1.setValue(null);
		tf_day1_pv2.setValue(null);
		tf_day2_pv1.setValue(null);
		tf_day2_pv2.setValue(null);
		tf_day3_pv1.setValue(null);
		tf_day3_pv2.setValue(null);
		tf_day4_pv1.setValue(null);
		tf_day4_pv2.setValue(null);
		tf_day5_pv1.setValue(null);
		tf_day5_pv2.setValue(null);

		tf_day1_pd1.setValue(null);
		tf_day1_pd2.setValue(null);
		tf_day2_pd1.setValue(null);
		tf_day2_pd2.setValue(null);
		tf_day3_pd1.setValue(null);
		tf_day3_pd2.setValue(null);
		tf_day4_pd1.setValue(null);
		tf_day4_pd2.setValue(null);
		tf_day5_pd1.setValue(null);
		tf_day5_pd2.setValue(null);
		
		tf_day1_int1.setValue(null);
		tf_day1_int2.setValue(null);
		tf_day2_int1.setValue(null);
		tf_day2_int2.setValue(null);
		tf_day3_int1.setValue(null);
		tf_day3_int2.setValue(null);
		tf_day4_int1.setValue(null);
		tf_day4_int2.setValue(null);
		tf_day5_int1.setValue(null);
		tf_day5_int2.setValue(null);
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
				if (NraspInfo.get(i).getVidp() == 1){
					tf_day1_pp1.setTime(NraspInfo.get(i).getTime_n());
					tf_day1_pp2.setTime(NraspInfo.get(i).getTime_k());
				}
				if (NraspInfo.get(i).getVidp() == 2){
					tf_day1_pv1.setTime(NraspInfo.get(i).getTime_n());
					tf_day1_pv2.setTime(NraspInfo.get(i).getTime_k());
				}
				if (NraspInfo.get(i).getVidp() == 3){
					tf_day1_pd1.setTime(NraspInfo.get(i).getTime_n());
					tf_day1_pd2.setTime(NraspInfo.get(i).getTime_k());
				}
				tf_day1_pause1.setTime(NraspInfo.get(i).getTimep_n());
				tf_day1_pause2.setTime(NraspInfo.get(i).getTimep_k());
				tf_day1_int1.setTime(NraspInfo.get(i).getTime_int_n());
				tf_day1_int2.setTime(NraspInfo.get(i).getTime_int_k());
			}
			if (NraspInfo.get(i).getDenn() == 2){
				if (NraspInfo.get(i).getVidp() == 1){
					tf_day2_pp1.setTime(NraspInfo.get(i).getTime_n());
					tf_day2_pp2.setTime(NraspInfo.get(i).getTime_k());
				}
				if (NraspInfo.get(i).getVidp() == 2){
					tf_day2_pv1.setTime(NraspInfo.get(i).getTime_n());
					tf_day2_pv2.setTime(NraspInfo.get(i).getTime_k());
				}
				if (NraspInfo.get(i).getVidp() == 3){
					tf_day2_pd1.setTime(NraspInfo.get(i).getTime_n());
					tf_day2_pd2.setTime(NraspInfo.get(i).getTime_k());
				}
				tf_day2_pause1.setTime(NraspInfo.get(i).getTimep_n());
				tf_day2_pause2.setTime(NraspInfo.get(i).getTimep_k());
				tf_day2_int1.setTime(NraspInfo.get(i).getTime_int_n());
				tf_day2_int2.setTime(NraspInfo.get(i).getTime_int_k());
			}
			if (NraspInfo.get(i).getDenn() == 3){
				if (NraspInfo.get(i).getVidp() == 1){
					tf_day3_pp1.setTime(NraspInfo.get(i).getTime_n());
					tf_day3_pp2.setTime(NraspInfo.get(i).getTime_k());
				}
				if (NraspInfo.get(i).getVidp() == 2){
					tf_day3_pv1.setTime(NraspInfo.get(i).getTime_n());
					tf_day3_pv2.setTime(NraspInfo.get(i).getTime_k());
				}
				if (NraspInfo.get(i).getVidp() == 3){
					tf_day3_pd1.setTime(NraspInfo.get(i).getTime_n());
					tf_day3_pd2.setTime(NraspInfo.get(i).getTime_k());
				}
				tf_day3_pause1.setTime(NraspInfo.get(i).getTimep_n());
				tf_day3_pause2.setTime(NraspInfo.get(i).getTimep_k());
				tf_day3_int1.setTime(NraspInfo.get(i).getTime_int_n());
				tf_day3_int2.setTime(NraspInfo.get(i).getTime_int_k());
			}
			if (NraspInfo.get(i).getDenn() == 4){
				if (NraspInfo.get(i).getVidp() == 1){
					tf_day4_pp1.setTime(NraspInfo.get(i).getTime_n());
					tf_day4_pp2.setTime(NraspInfo.get(i).getTime_k());
				}
				if (NraspInfo.get(i).getVidp() == 2){
					tf_day4_pv1.setTime(NraspInfo.get(i).getTime_n());
					tf_day4_pv2.setTime(NraspInfo.get(i).getTime_k());
				}
				if (NraspInfo.get(i).getVidp() == 3){
					tf_day4_pd1.setTime(NraspInfo.get(i).getTime_n());
					tf_day4_pd2.setTime(NraspInfo.get(i).getTime_k());
				}
				tf_day4_pause1.setTime(NraspInfo.get(i).getTimep_n());
				tf_day4_pause2.setTime(NraspInfo.get(i).getTimep_k());
				tf_day4_int1.setTime(NraspInfo.get(i).getTime_int_n());
				tf_day4_int2.setTime(NraspInfo.get(i).getTime_int_k());
			}
			if (NraspInfo.get(i).getDenn() == 5){
				if (NraspInfo.get(i).getVidp() == 1){
					tf_day5_pp1.setTime(NraspInfo.get(i).getTime_n());
					tf_day5_pp2.setTime(NraspInfo.get(i).getTime_k());
				}
				if (NraspInfo.get(i).getVidp() == 2){
					tf_day5_pv1.setTime(NraspInfo.get(i).getTime_n());
					tf_day5_pv2.setTime(NraspInfo.get(i).getTime_k());
				}
				if (NraspInfo.get(i).getVidp() == 3){
					tf_day5_pd1.setTime(NraspInfo.get(i).getTime_n());
					tf_day5_pd2.setTime(NraspInfo.get(i).getTime_k());
				}
				tf_day5_pause1.setTime(NraspInfo.get(i).getTimep_n());
				tf_day5_pause2.setTime(NraspInfo.get(i).getTimep_k());
				tf_day5_int1.setTime(NraspInfo.get(i).getTime_int_n());
				tf_day5_int2.setTime(NraspInfo.get(i).getTime_int_k());
			}
		}
	}

//	public void ChangeDatap(){
//		getDatep = calendar.getDate().getTime();
//		try{
//			String numday = null;
//			String nummonth = null;
//			if (nday<10) numday = "0"+String.valueOf(nday);
//			else numday = String.valueOf(nday);
//			if (nmonth<10) nummonth = "0"+String.valueOf(nmonth);
//			else nummonth = String.valueOf(nmonth);
//			SimpleDateFormat frm = new SimpleDateFormat("dd.MM.yyyy");
//			String strDat = numday+"."+nummonth+"."+String.valueOf(nyear);
//			Date dat = frm.parse(strDat);
//			getDatep = dat.getTime();
//		    System.out.println(strDat+ ",   "+ dat+ ",   "+ getDatep);
//			ChangeTalonInfo();
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//	}
}
