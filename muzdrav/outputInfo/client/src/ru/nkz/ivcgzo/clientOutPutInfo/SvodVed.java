package ru.nkz.ivcgzo.clientOutPutInfo;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class SvodVed extends JPanel {
	public SvodVed() {
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGap(0, 450, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGap(0, 300, Short.MAX_VALUE)
		);
		setLayout(groupLayout);
		
		JScrollPane scrollPane = new JScrollPane();
		//scrollPane.setViewportView(this);
		//addTab("New tab", null, scrollPane, null);
	}

}
