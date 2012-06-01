package ru.nkz.ivcgzo.ldsclient;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JComboBox;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.ldsThrift.LdiNotFoundException;
import ru.nkz.ivcgzo.ldsThrift.Metod;
import ru.nkz.ivcgzo.ldsThrift.MetodNotFoundException;
import ru.nkz.ivcgzo.ldsThrift.N_ldi;
import ru.nkz.ivcgzo.ldsThrift.S_ot01;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Option {

	public JFrame frame;
	private CustomTable<N_ldi, N_ldi._Fields> tn_ldi;
	private CustomTable<Metod, Metod._Fields> tmetod;
	private CustomTable<S_ot01, S_ot01._Fields> ts_ot01;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> p0e1;
	public ThriftStringClassifierCombobox<StringClassifier> n_nz1;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Option window = new Option();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Option() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 784, 670);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		JPanel panel = new JPanel();
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 776, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 484, Short.MAX_VALUE)
					.addGap(0))
		);
		
		JLabel lblNewLabel = new JLabel("Органы и системы");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
		
		p0e1 = new ThriftIntegerClassifierCombobox<>(false);		
		p0e1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filtN_ldi();
			}
		});
		
		n_nz1 = new ThriftStringClassifierCombobox<>(false);
		n_nz1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				filtN_ldi();
			}
		});
		
		JLabel label = new JLabel("Исследования");
		label.setFont(new Font("Tahoma", Font.BOLD, 12));
		

		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(p0e1, GroupLayout.PREFERRED_SIZE, 239, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(n_nz1, GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
					.addContainerGap())
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(label, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
						.addComponent(p0e1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNewLabel)
						.addComponent(n_nz1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		
		JPanel panel_1 = new JPanel();
		splitPane.setLeftComponent(panel_1);
		
		JSplitPane splitPane_1 = new JSplitPane();
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane_1, GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane_1, GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
		);
		
		JPanel panel_2 = new JPanel();
		splitPane_1.setLeftComponent(panel_2);
		
		JPanel panel_4 = new JPanel();
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
				.addComponent(panel_4, GroupLayout.DEFAULT_SIZE, 327, Short.MAX_VALUE)
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
					.addComponent(panel_4, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 59, Short.MAX_VALUE))
		);
		
		tn_ldi = new CustomTable<>(false, true, N_ldi.class, 0, "Список анализов", 2, "Название", 6, "Выбор");
		tn_ldi.setEditableFields(true, 2);
		
		tn_ldi.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				if (!arg0.getValueIsAdjusting())
					filtN_stoim();
			}
		});
		
		
		tn_ldi.setFillsViewportHeight(true);
		scrollPane.setViewportView(tn_ldi);
		
		JLabel lblNewLabel_1 = new JLabel("Список исследований");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.BOLD, 12));
		GroupLayout gl_panel_4 = new GroupLayout(panel_4);
		gl_panel_4.setHorizontalGroup(
			gl_panel_4.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addGap(102)
					.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, 148, Short.MAX_VALUE)
					.addGap(67))
		);
		gl_panel_4.setVerticalGroup(
			gl_panel_4.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_4.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_4.setLayout(gl_panel_4);
		panel_2.setLayout(gl_panel_2);
		
		JPanel panel_3 = new JPanel();
		splitPane_1.setRightComponent(panel_3);
		
		JPanel panel_5 = new JPanel();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
				.addComponent(panel_5, GroupLayout.DEFAULT_SIZE, 440, Short.MAX_VALUE)
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addComponent(panel_5, GroupLayout.PREFERRED_SIZE, 52, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 58, Short.MAX_VALUE))
		);
		
		tmetod = new CustomTable<>(false, true, Metod.class, 2, "Код", 3, "Наименование", 4, "Стоимость", 5, "Выбор");
		tmetod.setEditableFields(true, 3);
		tmetod.setFillsViewportHeight(true);
		scrollPane_1.setViewportView(tmetod);
		
		JLabel lblNewLabel_2 = new JLabel("Методы исследования");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD, 12));
		GroupLayout gl_panel_5 = new GroupLayout(panel_5);
		gl_panel_5.setHorizontalGroup(
			gl_panel_5.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addGap(138)
					.addComponent(lblNewLabel_2)
					.addContainerGap(159, Short.MAX_VALUE))
		);
		gl_panel_5.setVerticalGroup(
			gl_panel_5.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panel_5.createSequentialGroup()
					.addContainerGap(26, Short.MAX_VALUE)
					.addComponent(lblNewLabel_2)
					.addContainerGap())
		);
		panel_5.setLayout(gl_panel_5);
		panel_3.setLayout(gl_panel_3);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_6 = new JPanel();
		splitPane.setRightComponent(panel_6);
		
		JPanel panel_7 = new JPanel();
		
		JScrollPane scrollPane_2 = new JScrollPane();
		GroupLayout gl_panel_6 = new GroupLayout(panel_6);
		gl_panel_6.setHorizontalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_7, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
				.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
		);
		gl_panel_6.setVerticalGroup(
			gl_panel_6.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_6.createSequentialGroup()
					.addComponent(panel_7, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane_2, GroupLayout.DEFAULT_SIZE, 377, Short.MAX_VALUE))
		);
		
		JLabel lblNewLabel_3 = new JLabel("Шаблоны исследований на отделение");
		lblNewLabel_3.setFont(new Font("Tahoma", Font.BOLD, 12));
		GroupLayout gl_panel_7 = new GroupLayout(panel_7);
		gl_panel_7.setHorizontalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addGap(243)
					.addComponent(lblNewLabel_3)
					.addContainerGap(287, Short.MAX_VALUE))
		);
		gl_panel_7.setVerticalGroup(
			gl_panel_7.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_7.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_3)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		panel_7.setLayout(gl_panel_7);
		
		ts_ot01 = new CustomTable<>(false, true, S_ot01.class, 0, "Код отделения", 1, "Код исследования", 2, "Код метода ис-ия", 3, "Код органов и систем");
		ts_ot01.setFillsViewportHeight(true);
		scrollPane_2.setViewportView(ts_ot01);
		panel_6.setLayout(gl_panel_6);
		frame.getContentPane().setLayout(groupLayout);
	}
	
	private void filtN_ldi() {
		try {
			if ((p0e1.getSelectedItem() != null) && (n_nz1.getSelectedItem() != null)){
				tn_ldi.setData(MainForm.ltc.getN_ldi(n_nz1.getSelectedPcod(), p0e1.getSelectedPcod()));
			}
		} catch (LdiNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private void filtN_stoim() {
		try {
			if ((p0e1.getSelectedItem() != null) && (tn_ldi.getSelectedItem() != null)) {
				tmetod.setData(MainForm.ltc.getMetod(p0e1.getSelectedPcod(), tn_ldi.getSelectedItem().getPcod(), String.format("%%.%02d.%%", p0e1.getSelectedPcod())));
			}
		} catch (MetodNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
}
