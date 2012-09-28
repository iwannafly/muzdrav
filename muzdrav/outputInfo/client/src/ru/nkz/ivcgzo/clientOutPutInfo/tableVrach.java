package ru.nkz.ivcgzo.clientOutPutInfo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import javax.swing.table.DefaultTableModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class tableVrach {
	
	public JFrame frameVr;

public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
		public void run() {
			try {
				tableVrach window = new tableVrach();
				window.frameVr.setVisible(true);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
}

public tableVrach(){
	initialize();
}

public void initialize() {
	frameVr = new JFrame();
	frameVr.setTitle("Табель работы врача");
	frameVr.setBounds(180, 180, 1000, 700);
	frameVr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	JScrollPane vrPane = new JScrollPane();
	JTable tableVr = new JTable();
	tableVr.setModel(new DefaultTableModel(
			new Object[][] {
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},
					{null, null, null, null},

			},
			new String[] {
					"Фамилия", "Имя", "Отчество", "Должность"
			}
			));
	tableVr.setAutoCreateRowSorter(true);
	tableVr.getRowSorter().toggleSortOrder(0);
	tableVr.setFillsViewportHeight(true);
	tableVr.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	vrPane.setViewportView(tableVr);

	JScrollPane timePane = new JScrollPane();
	
	JTable tableTime = new JTable();
	tableTime.setModel(new DefaultTableModel(
			new Object[][] {
					{null, null, null, null, null, null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null, null, null, null, null, null},
					{null, null, null, null, null, null, null, null, null, null, null, null},

			},
			new String[] {
					"Код врача", "Должность", "Дата приема", "В поликлинике", "На дому", "На дому актив", "Проф.осмотр", "Прочие", "№1 участка", "№2 участка", "№3 участка", "Прим." 
			}
			));
	tableTime.setAutoCreateRowSorter(true);
	tableTime.getRowSorter().toggleSortOrder(0);
	tableTime.setFillsViewportHeight(true);
	tableTime.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
	timePane.setViewportView(tableTime);	
	
	JPanel butPanel = new JPanel();
	
	
	GroupLayout groupLayout = new GroupLayout(frameVr.getContentPane());
	groupLayout.setHorizontalGroup(
		groupLayout.createParallelGroup(Alignment.TRAILING)
			.addGroup(groupLayout.createSequentialGroup()
				.addContainerGap()
				.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
					.addComponent(timePane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
					.addComponent(butPanel, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 546, GroupLayout.PREFERRED_SIZE)
					.addComponent(vrPane, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 717, GroupLayout.PREFERRED_SIZE))
				.addContainerGap())
	);
	groupLayout.setVerticalGroup(
		groupLayout.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout.createSequentialGroup()
				.addContainerGap()
				.addComponent(vrPane, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(butPanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
				.addPreferredGap(ComponentPlacement.RELATED)
				.addComponent(timePane, GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
				.addContainerGap())
	);
		butPanel.setLayout(new GridLayout(1, 1, 5, 0) );
		
		JButton butCreate = new JButton("Создать");
		butCreate.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JOptionPane.showMessageDialog(null, "Создать");
			}
		});
		butPanel.add(butCreate);
		JButton butDelete = new JButton("Удалить");
		butDelete.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JOptionPane.showMessageDialog(null, "Удалить");
			}
		});
		butPanel.add(butDelete);
		JButton butForward = new JButton("Вперед");
		butForward.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JOptionPane.showMessageDialog(null, "Вперед");
			}
		});
		butPanel.add(butForward);
		JButton butBack = new JButton("Назад");
		butBack.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JOptionPane.showMessageDialog(null, "Назад");
			}
		});
		butPanel.add(butBack);
		JButton butSave = new JButton("Сохранить");
		butSave.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				JOptionPane.showMessageDialog(null, "Сохранить");
			}
		});
		butPanel.add(butSave);
	frameVr.getContentPane().setLayout(groupLayout);
	
	
	
}

}