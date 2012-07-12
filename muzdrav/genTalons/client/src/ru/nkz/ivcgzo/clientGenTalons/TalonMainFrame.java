package ru.nkz.ivcgzo.clientGenTalons;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;
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
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftGenTalon.Ndv;
import ru.nkz.ivcgzo.thriftGenTalon.Norm;
import ru.nkz.ivcgzo.thriftGenTalon.Nrasp;
import ru.nkz.ivcgzo.thriftGenTalon.Rasp;
import ru.nkz.ivcgzo.thriftGenTalon.Spec;
import ru.nkz.ivcgzo.thriftGenTalon.Talon;
import ru.nkz.ivcgzo.thriftGenTalon.Vidp;
import ru.nkz.ivcgzo.thriftGenTalon.Vrach;
import ru.nkz.ivcgzo.clientGenTalons.RaspisanieUnit;
import javax.swing.JTable;
import java.awt.Font;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;

public class TalonMainFrame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTree treevrach;
	private JTabbedPane tbMain;
	private JTabbedPane tbRasp;
	private String curSpec = null;
	private int curVrach = 0;
	private final ButtonGroup btnGroup_cxema = new ButtonGroup();
	private final ButtonGroup btnGroup_talon = new ButtonGroup();
	private JRadioButton cxm_1;
	private JRadioButton cxm_2;
	private JRadioButton cxm_3;
	private JRadioButton tal_1;
	private JRadioButton tal_2;
	private JRadioButton tal_3;
	private JCheckBox cbx_rasp;
	private List<Nrasp> NraspInfo;
	private List<Norm> NormInfo;
	private List<Ndv> NdvInfo;
	private CustomTable<Nrasp, Nrasp._Fields> tbl_rasp;
	private CustomTable<Norm, Norm._Fields> tbl_norm;
	private CustomTable<Ndv, Ndv._Fields> tbl_ndv;
	private CustomDateEditor tf_datn;
	private CustomDateEditor tf_datk;

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
		setBounds(1, 1, 958, 690); //(x,y,width,height)
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
					ChangeTalonInfo();
					tal_1.setSelected(true);
				}
			}
		});
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 245, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tbMain, GroupLayout.PREFERRED_SIZE, 685, GroupLayout.PREFERRED_SIZE))
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
		
		JScrollPane sp_rasp = new JScrollPane();
		
		JPanel panel_2 = new JPanel();
		
		JPanel panel_4 = new JPanel();
		panel_4.setBorder(new TitledBorder(null, "\u0418\u043D\u0434\u0438\u0432\u0438\u0434\u0443\u0430\u043B\u044C\u043D\u0430\u044F \u043E\u0442\u043C\u0435\u043D\u0430 \u043F\u0440\u0438\u0435\u043C\u0430", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_tbRasp = new GroupLayout(tbRasp);
		gl_tbRasp.setHorizontalGroup(
			gl_tbRasp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tbRasp.createSequentialGroup()
					.addGap(23)
					.addGroup(gl_tbRasp.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 619, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_tbRasp.createSequentialGroup()
							.addGroup(gl_tbRasp.createParallelGroup(Alignment.LEADING)
								.addComponent(sp_rasp, GroupLayout.PREFERRED_SIZE, 331, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 347, GroupLayout.PREFERRED_SIZE))
							.addGap(70)
							.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 222, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		gl_tbRasp.setVerticalGroup(
			gl_tbRasp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tbRasp.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_tbRasp.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_tbRasp.createSequentialGroup()
							.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(sp_rasp, GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE))
						.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 509, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		JScrollPane sp_ndv = new JScrollPane();
		
		JButton btn_add_ndv = new JButton("+");
		btn_add_ndv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					tbl_ndv.requestFocus();
					tbl_ndv.addItem();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_add_ndv.setToolTipText("Добавить");
		btn_add_ndv.setFont(new Font("Tahoma", Font.BOLD, 14));
		
		JButton btn_del_ndv = new JButton("-");
		btn_del_ndv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					tbl_ndv.requestFocus();
					tbl_ndv.deleteSelectedRow();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		btn_del_ndv.setToolTipText("Удалить");
		btn_del_ndv.setFont(new Font("Tahoma", Font.BOLD, 14));
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_4.createSequentialGroup()
							.addContainerGap()
							.addComponent(btn_add_ndv, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btn_del_ndv, GroupLayout.PREFERRED_SIZE, 64, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_4.createSequentialGroup()
							.addGap(12)
							.addComponent(sp_ndv, GroupLayout.DEFAULT_SIZE, 184, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_panel_4.createSequentialGroup()
					.addComponent(sp_ndv, GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_4.createParallelGroup(Alignment.LEADING)
						.addComponent(btn_add_ndv)
						.addComponent(btn_del_ndv, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
		);
		
		tbl_ndv =new CustomTable<>(true, true, Ndv.class, 1,"С",2,"По");
		tbl_ndv.setPreferredWidths(90,90);
		tbl_ndv.setDateField(0);
		tbl_ndv.setDateField(1);
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
				    item.setCpol(tbl_ndv.getSelectedItem().cpol);
				    item.setCdol(tbl_ndv.getSelectedItem().cdol);
				    item.setPcod(tbl_ndv.getSelectedItem().pcod);
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
				ChangeNraspInfo();
			}
		});
		btn_new.setToolTipText("Создать расписание");
		
		JButton btn_save = new JButton("Сохранить");
		btn_save.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					MainForm.tcl.updateNrasp(tbl_rasp.getData());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btn_save.setToolTipText("Сохранить расписание");
		
		JButton btn_del = new JButton("Удалить");
		btn_del.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
				    MainForm.tcl.deleteNrasp(MainForm.authInfo.cpodr, curVrach, curSpec);
					tbl_rasp.setData(new ArrayList<Nrasp>());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		btn_del.setToolTipText("Удалить расписание");
		
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addComponent(btn_new, GroupLayout.PREFERRED_SIZE, 91, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(cbx_rasp, GroupLayout.PREFERRED_SIZE, 238, GroupLayout.PREFERRED_SIZE)
					.addGap(39)
					.addComponent(btn_save)
					.addGap(36)
					.addComponent(btn_del, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(1)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(btn_save, GroupLayout.PREFERRED_SIZE, 21, Short.MAX_VALUE)
								.addComponent(btn_del, GroupLayout.PREFERRED_SIZE, 21, Short.MAX_VALUE)))
						.addGroup(gl_panel_2.createSequentialGroup()
							.addGap(1)
							.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
								.addComponent(btn_new)
								.addComponent(cbx_rasp, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
					.addGap(4))
		);
		panel_2.setLayout(gl_panel_2);
		
		tbl_rasp =new CustomTable<>(true, true, Nrasp.class, 1,"День недели" , 2,"Вид приема",3,"С",4,"По", 5, "Схема");
		tbl_rasp.setPreferredWidths(100,90,60,60,30);
		tbl_rasp.setTimeField(2);
		tbl_rasp.setTimeField(3);
		tbl_rasp.setFillsViewportHeight(true);
		sp_rasp.setViewportView(tbl_rasp);

		cxm_1 = new JRadioButton("на каждый день");
		cxm_1.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				ChangeCxemaInfo(0);			}
		});
		btnGroup_cxema.add(cxm_1);
		
		cxm_2 = new JRadioButton("четные дни");
		cxm_2.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				ChangeCxemaInfo(1);			}
		});
		btnGroup_cxema.add(cxm_2);
		
		cxm_3 = new JRadioButton("нечетные дни");
		cxm_3.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
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
					.addGroup(gl_tbNorm.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_tbNorm.createSequentialGroup()
							.addGap(53)
							.addComponent(sp_norm, GroupLayout.PREFERRED_SIZE, 188, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_tbNorm.createSequentialGroup()
							.addGap(27)
							.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 465, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(188, Short.MAX_VALUE))
		);
		gl_tbNorm.setVerticalGroup(
			gl_tbNorm.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_tbNorm.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(sp_norm, GroupLayout.PREFERRED_SIZE, 135, GroupLayout.PREFERRED_SIZE)
					.addGap(407))
		);
		
		JButton btn_new_norm = new JButton("Создать");
		btn_new_norm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try{
					RaspisanieUnit.NewNorm(MainForm.authInfo.cpodr, curSpec);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		
		JButton btn_save_norm = new JButton("Сохранить");
		btn_save_norm.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
					MainForm.tcl.updateNorm(tbl_norm.getData());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
			}
		});
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addGap(33)
					.addComponent(btn_new_norm)
					.addGap(29)
					.addComponent(btn_save_norm)
					.addGap(225))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
					.addComponent(btn_new_norm)
					.addComponent(btn_save_norm))
		);
		panel_3.setLayout(gl_panel_3);
		
		tbl_norm =new CustomTable<>(true, true, Norm.class, 1,"Вид приема",2,"Длительность");
		tbl_norm.setFillsViewportHeight(true);
		tbl_norm.setPreferredWidths(90,90);
		sp_norm.setViewportView(tbl_norm);
		tbNorm.setLayout(gl_tbNorm);
		
		JPanel tbTalon = new JPanel();
		tbMain.addTab("Журнал талонов", null, tbTalon, null);
		
		JPanel panel_5 = new JPanel();
		GroupLayout gl_tbTalon = new GroupLayout(tbTalon);
		gl_tbTalon.setHorizontalGroup(
			gl_tbTalon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tbTalon.createSequentialGroup()
					.addGap(25)
					.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 582, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(73, Short.MAX_VALUE))
		);
		gl_tbTalon.setVerticalGroup(
			gl_tbTalon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_tbTalon.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 154, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(442, Short.MAX_VALUE))
		);
		
		tf_datn = new CustomDateEditor();
		tf_datk = new CustomDateEditor();
		
		JButton btnNewButton = new JButton("Формировать");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				RaspisanieUnit.CreateTalons(MainForm.authInfo.cpodr, curVrach, curSpec, tf_datn.getDate().getTime(), tf_datk.getDate().getTime(), btnGroup_talon.isSelected(btnGroup_talon.getSelection()));
			}
		});

		JLabel lblNewLabel = new JLabel("Период: с");
		JLabel lblNewLabel_1 = new JLabel("По");
		
		tal_1 = new JRadioButton("все специалисты");
		btnGroup_talon.add(tal_1);
		
		tal_2 = new JRadioButton("для выбранной специальности");
		btnGroup_talon.add(tal_2);
		
		tal_3 = new JRadioButton("для выбранного врача");
		btnGroup_talon.add(tal_3);
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addGap(20)
					.addComponent(tf_datn, GroupLayout.PREFERRED_SIZE, 88, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_1)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tf_datk, GroupLayout.PREFERRED_SIZE, 83, GroupLayout.PREFERRED_SIZE)
					.addGap(32)
					.addGroup(gl_panel_5.createParallelGroup(Alignment.LEADING)
						.addComponent(btnNewButton)
						.addComponent(tal_3)
						.addComponent(tal_2)
						.addComponent(tal_1))
					.addContainerGap(89, Short.MAX_VALUE))
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_5.createParallelGroup(Alignment.BASELINE)
						.addComponent(tf_datn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tf_datk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel)
						.addComponent(lblNewLabel_1)
						.addComponent(tal_1))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tal_2)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tal_3)
					.addGap(26)
					.addComponent(btnNewButton)
					.addContainerGap(26, Short.MAX_VALUE))
		);
		panel_5.setLayout(gl_panel_5);
		tbTalon.setLayout(gl_tbTalon);
		
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
//				Object lastPath = e.getNewLeadSelectionPath().getLastPathComponent();
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
				    System.out.println("curSpec= "+curSpec+", curVrach= "+Integer.toString(curVrach));
					if (tbMain.getSelectedIndex() == 0) {
						ChangeNraspInfo();
						ChangeNdvInfo();
					}
					if (tbMain.getSelectedIndex() == 1) ChangeNormInfo();
					if (tbMain.getSelectedIndex() == 2) {
						ChangeTalonInfo();
						tal_1.setSelected(true);
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
	
	public void onConnect() {
		treevrach.setModel(new DefaultTreeModel(createNodes()));
	}
	
	private DefaultMutableTreeNode createNodes() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Корень дерева");
		try {
//			List<Spec> specList = MainForm.tcl.getAllSpecForPolikliniki(20);
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
			NraspInfo = MainForm.tcl.getNrasp(MainForm.authInfo.cpodr, curVrach, curSpec, 0);
			if (!NraspInfo.isEmpty()){
				cxm_1.setSelected(true);
			}else{
				NraspInfo = MainForm.tcl.getNrasp(MainForm.authInfo.cpodr, curVrach, curSpec, 1);
				if (!NraspInfo.isEmpty()){
					cxm_2.setSelected(true);
				} else{
					NraspInfo = MainForm.tcl.getNrasp(MainForm.authInfo.cpodr, curVrach, curSpec, 2);
					if (!NraspInfo.isEmpty()){
						cxm_3.setSelected(true);
					}
				}
			}
			tbl_rasp.setData(NraspInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void ChangeCxemaInfo(int cxm){
		try {
			tbl_rasp.setData(new ArrayList<Nrasp>());
			NraspInfo = MainForm.tcl.getNrasp(MainForm.authInfo.cpodr, curVrach, curSpec, cxm);
			tbl_rasp.setData(NraspInfo);
//		    tbl_rasp.getSelectedItem().setName(pInfo.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void ChangeNormInfo(){
		try {
			tbl_norm.setData(new ArrayList<Norm>());
			NormInfo = MainForm.tcl.getNorm(MainForm.authInfo.cpodr, curSpec);
			tbl_norm.setData(NormInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void ChangeNdvInfo(){
		try {
			tbl_ndv.setData(new ArrayList<Ndv>());
			NdvInfo = MainForm.tcl.getNdv(MainForm.authInfo.cpodr, curVrach, curSpec);
			tbl_ndv.setData(NdvInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void ChangeTalonInfo(){
		try {
			tbl_norm.setData(new ArrayList<Norm>());
			NormInfo = MainForm.tcl.getNorm(MainForm.authInfo.cpodr, curSpec);
			tbl_norm.setData(NormInfo);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
