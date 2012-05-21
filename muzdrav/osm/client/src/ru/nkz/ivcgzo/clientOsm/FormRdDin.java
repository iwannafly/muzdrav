package ru.nkz.ivcgzo.clientOsm;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import java.util.Date;
import java.util.Calendar;
import javax.swing.SpinnerNumberModel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;

public class FormRdDin extends JFrame {

	private JPanel contentPane;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormRdDin frame = new FormRdDin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FormRdDin() {
		setTitle("Динамика диспансерного наблюдения за беременной");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 700, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		
		JPanel panel_1 = new JPanel();
		
		JPanel panel_2 = new JPanel();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 281, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(18, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 176, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		
		JLabel LChcc = new JLabel("ЧСС плода");
		
		JLabel LPolPl = new JLabel("Положение плода");
		
		JLabel LPredPl = new JLabel("Предлежание плода");
		
		JLabel LSerd = new JLabel("Сердцебиение плода");
		
		JSpinner SChcc = new JSpinner();
		SChcc.setModel(new SpinnerNumberModel(new Integer(100), new Integer(60), null, new Integer(1)));
		
		JComboBox CBPolPl = new JComboBox();
		CBPolPl.setModel(new DefaultComboBoxModel(new String[] {"", "Продольное", "Поперечное", "Косое"}));
		
		JComboBox CBPredPl = new JComboBox();
		CBPredPl.setModel(new DefaultComboBoxModel(new String[] {"", "Голова", "Таз"}));
		
		JComboBox CBCerd = new JComboBox();
		CBCerd.setModel(new DefaultComboBoxModel(new String[] {"", "Ритмичное", "Аритмичное"}));
		
		JComboBox CBSerd1 = new JComboBox();
		CBSerd1.setModel(new DefaultComboBoxModel(new String[] {"", "Ясное", "Приглушенное", "Глухое"}));
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addContainerGap()
							.addComponent(LChcc))
						.addComponent(LPolPl)
						.addComponent(LSerd)
						.addComponent(LPredPl))
					.addGap(29)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_2.createSequentialGroup()
							.addComponent(CBSerd1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
							.addGroup(gl_panel_2.createSequentialGroup()
								.addComponent(CBPredPl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(115))
							.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
								.addComponent(CBCerd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel_2.createSequentialGroup()
									.addGroup(gl_panel_2.createParallelGroup(Alignment.LEADING)
										.addComponent(CBPolPl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addComponent(SChcc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
									.addContainerGap(108, Short.MAX_VALUE))))))
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(LChcc)
						.addComponent(SChcc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(LPolPl)
						.addComponent(CBPolPl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(18)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.BASELINE)
						.addComponent(LPredPl)
						.addComponent(CBPredPl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_2.createParallelGroup(Alignment.TRAILING)
						.addComponent(LSerd)
						.addComponent(CBCerd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(13)
					.addComponent(CBSerd1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		panel_2.setLayout(gl_panel_2);
		
		JLabel LDataPos = new JLabel("Дата посещения");
		
		JLabel LSrok = new JLabel("Срок беременности");
		
		JLabel LVes = new JLabel("Вес");
		
		JLabel LOkrJ = new JLabel("Окружность живота");
		
		JLabel LVdm = new JLabel("ВДМ");
		
		JLabel LDiag = new JLabel("Диагноз при наблюдении");
		
		JLabel LPdad = new JLabel("Правая ДАД");
		
		JLabel LPsad = new JLabel("Правая САД");
		
		JLabel LLdad = new JLabel("Левая ДАД");
		
		JLabel LLsad = new JLabel("Правая САД");
		
		JLabel LtolPlac = new JLabel("Толщина плаценты");
		
		JLabel LOteki = new JLabel("Отеки");
		
		JLabel LDataSl = new JLabel("Дата следующего посещения");
		
		JSpinner SDataPos = new JSpinner();
		SDataPos.setModel(new SpinnerDateModel(new Date(1335373200000L), null, null, Calendar.DAY_OF_YEAR));
		
		JSpinner SSrok = new JSpinner();
		SSrok.setModel(new SpinnerNumberModel(4, 2, 40, 1));
		
		JSpinner SVes = new JSpinner();
		SVes.setModel(new SpinnerNumberModel(new Integer(60), new Integer(40), null, new Integer(1)));
		
		JSpinner SOkrj = new JSpinner();
		SOkrj.setModel(new SpinnerNumberModel(new Integer(60), new Integer(45), null, new Integer(1)));
		
		JSpinner SVdm = new JSpinner();
		SVdm.setModel(new SpinnerNumberModel(new Integer(0), null, null, new Integer(1)));
		
		JComboBox CBDiag = new JComboBox();
		
		JSpinner SPdad = new JSpinner();
		SPdad.setModel(new SpinnerNumberModel(120, 50, 220, 1));
		
		JSpinner SPsad = new JSpinner();
		SPsad.setModel(new SpinnerNumberModel(80, 40, 120, 1));
		
		JSpinner SLdad = new JSpinner();
		SLdad.setModel(new SpinnerNumberModel(120, 40, 220, 1));
		
		JSpinner SLsad = new JSpinner();
		SLsad.setModel(new SpinnerNumberModel(80, 30, 120, 1));
		
		JSpinner STolP = new JSpinner();
		STolP.setModel(new SpinnerNumberModel(new Integer(2), new Integer(1), null, new Integer(1)));
		
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Нет", "Нижние конечности", "Верхние конечности", "Верхняя брюшная стенка", "Генерализованные"}));
		
		JSpinner SDataSl = new JSpinner();
		SDataSl.setModel(new SpinnerDateModel(new Date(1335373200000L), null, null, Calendar.DAY_OF_YEAR));
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addComponent(LOteki)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(LDataSl)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(SDataSl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(LDataPos)
								.addComponent(LSrok)
								.addComponent(LVes)
								.addComponent(LOkrJ)
								.addComponent(LVdm)
								.addComponent(LDiag)
								.addComponent(LPdad)
								.addComponent(LPsad)
								.addComponent(LLdad)
								.addComponent(LLsad)
								.addComponent(LtolPlac))
							.addGap(33)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addComponent(STolP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(SLsad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(SLdad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(SPdad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(CBDiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(SVdm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(SOkrj, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(SSrok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(SDataPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(SVes, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
								.addComponent(SPsad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(44, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(19)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(LDataPos)
						.addComponent(SDataPos, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addGap(15)
							.addComponent(LSrok)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(LVes)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(LOkrJ)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(LVdm)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(LDiag))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(SSrok, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(SVes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(SOkrj, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(SVdm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(CBDiag, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(LPdad)
						.addComponent(SPdad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(LPsad)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(LLdad)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(LLsad)
								.addComponent(SLsad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(LtolPlac)
								.addComponent(STolP, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
								.addComponent(LOteki)
								.addComponent(comboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panel_1.createSequentialGroup()
							.addComponent(SPsad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(SLdad, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(LDataSl)
						.addComponent(SDataSl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_1.setLayout(gl_panel_1);
		panel.setLayout(gl_panel);
		
		table = new JTable();
		contentPane.add(table, BorderLayout.NORTH);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"\u0414\u0430\u0442\u0430 \u043F\u043E\u0441\u0435\u0449\u0435\u043D\u0438\u044F", "\u0421\u0440\u043E\u043A \u0431\u0435\u0440\u0435\u043C\u0435\u043D\u043D\u043E\u0441\u0442\u0438", "\u0412\u0435\u0441", "\u041E\u043A\u0440\u0443\u0436\u043D\u043E\u0441\u0442\u044C \u0436\u0438\u0432\u043E\u0442\u0430", "\u0412\u0414\u041C", "\u0414\u0438\u0430\u0433\u043D\u043E\u0437", "\u041F\u0440\u0430\u0432\u0430\u044F \u0414\u0410\u0414", "\u041F\u0440\u0430\u0432\u0430\u044F \u0421\u0410\u0414", "\u041B\u0435\u0432\u0430\u044F \u0414\u0410\u0414", "\u041B\u0435\u0432\u0430\u044F \u0421\u0410\u0414", "\u0412\u044B\u0441\u043E\u0442\u0430 \u0441\u0442\u043E\u044F\u043D\u0438\u044F \u0434\u043D\u0430 \u043C\u0430\u0442\u043A\u0438", "\u0422\u043E\u043B\u0449\u0438\u043D\u0430 \u043F\u043B\u0430\u0446\u0435\u043D\u0442\u044B", "\u0414\u0430\u0442\u0430 \u0441\u043B\u0435\u0434\u0443\u0449\u0435\u0433\u043E \u043F\u043E\u0441\u0435\u0449\u0435\u043D\u0438\u044F"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(95);
		table.getColumnModel().getColumn(1).setPreferredWidth(109);
		table.getColumnModel().getColumn(3).setPreferredWidth(116);
		table.getColumnModel().getColumn(10).setPreferredWidth(148);
		table.getColumnModel().getColumn(11).setPreferredWidth(108);
		table.getColumnModel().getColumn(12).setPreferredWidth(167);
		table.getColumnModel().getColumn(12).setMinWidth(32);
	}
}
