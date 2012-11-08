package ru.nkz.ivcgzo.clientOutPutInfo;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JButton;

import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import java.text.SimpleDateFormat;

import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ButtonGroup;
import javax.swing.JTable;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JScrollBar;
import javax.swing.JFormattedTextField;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;

public class Ot039 extends JPanel {
	private CustomDateEditor Date1;
	private CustomDateEditor Date2;
	private final ButtonGroup butGroup = new ButtonGroup();
	
	public Ot039() {

		JLabel label = new JLabel("Сведения о структуре посещений и использования рабочего времени");
		
		JLabel label_1 = new JLabel("Период формирования:");
		label_1.setFont(new Font("Tahoma", Font.BOLD, 14));

		final JRadioButton rbutP = new JRadioButton("По дате посещения");
		final JRadioButton rbutB = new JRadioButton("По дате записи в базу");
		butGroup.add(rbutP);
		butGroup.add(rbutB);
		rbutP.setSelected(true);
		
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date1 = new CustomDateEditor();
		Date1.setText("01012012");
		Date2 = new CustomDateEditor();
		Date2.setText("25122012");
		
		JLabel label_2 = new JLabel("С");
		JLabel label_3 = new JLabel("По");
		
		JButton button = new JButton("Выполнить");
		
		JButton button_1 = new JButton("Закрыть");
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(32)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(106)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
								.addComponent(label_1)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(label_2)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(Date1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addGap(18)
									.addComponent(label_3)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(Date2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(8))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(button)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(button_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(rbutP)
									.addComponent(rbutB))))
						.addComponent(label))
					.addContainerGap(175, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(label)
					.addGap(82)
					.addComponent(label_1)
					.addPreferredGap(ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
					.addComponent(rbutP)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbutB)
					.addGap(37)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(Date2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(label_3)
						.addComponent(label_2)
						.addComponent(Date1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(41)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(button)
						.addComponent(button_1))
					.addGap(130))
		);
		setLayout(groupLayout);
		
		JScrollPane scrollPane = new JScrollPane();
	}

}
