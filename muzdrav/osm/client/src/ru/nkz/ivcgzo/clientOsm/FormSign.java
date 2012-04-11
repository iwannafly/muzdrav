package ru.nkz.ivcgzo.clientOsm;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.ThriftOsm;
import ru.nkz.ivcgzo.thriftOsm.Psign;
import javax.swing.JScrollPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JButton;
import javax.swing.JTextPane;
import javax.swing.border.BevelBorder;

import org.apache.thrift.TException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FormSign extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static ThriftOsm.Client tcl;
	private JTextField tfgrup;
	private JTextField tfrezus;
	private JTextPane tpallerg;
	private JTextPane tpfarm;
	private JTextPane tpanamnz;
	private Psign psign;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormSign frame = new FormSign();
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
	public FormSign() {
		psign  = new Psign();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 503, 388);
		
		JPanel panel = new JPanel();
		
		JLabel label = new JLabel("Фармакологический анамнез");
		
		JLabel label_1 = new JLabel("Группа крови");
		
		tfgrup = new JTextField();
		tfgrup.setColumns(10);
		
		JLabel label_2 = new JLabel("Резус-фактор");
		
		tfrezus = new JTextField();
		tfrezus.setColumns(10);
		
		JLabel label_3 = new JLabel("Аллергоанамнез");
		
		JLabel label_4 = new JLabel("Анамнез жизни");
		
		JButton button = new JButton("Сохранить");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				psign.setGrup(tfgrup.getText());
				psign.setRezus(tfrezus.getText());
				psign.setAllerg(tpallerg.getText());
				psign.setFarmkol(tpfarm.getText());
				psign.setVitae(tpanamnz.getText());
				try {
					tcl.AddPsign(psign);
				} catch (KmiacServerException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		JTextPane tpallerg = new JTextPane();
		tpallerg.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JTextPane tpfarm = new JTextPane();
		tpfarm.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		JTextPane tpanamnz = new JTextPane();
		tpanamnz.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(label_4)
							.addGap(10)
							.addComponent(tpanamnz, GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(label)
							.addGap(10)
							.addComponent(tpfarm, GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(label_1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tfgrup)
							.addGap(274))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(label_2)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tfrezus)
							.addGap(273))
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(label_3)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tpallerg, GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)))
					.addGap(46))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(34)
					.addComponent(button)
					.addContainerGap(374, Short.MAX_VALUE))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_1)
						.addComponent(tfgrup, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.BASELINE)
						.addComponent(label_2)
						.addComponent(tfrezus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tpallerg, GroupLayout.PREFERRED_SIZE, 69, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(36)
							.addComponent(label_3)))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tpfarm, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED))
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(label)
							.addGap(34)))
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(tpanamnz, GroupLayout.PREFERRED_SIZE, 68, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(27)
							.addComponent(label_4)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(button)
					.addContainerGap(31, Short.MAX_VALUE))
		);
		panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 526, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, GroupLayout.DEFAULT_SIZE, 451, Short.MAX_VALUE)
		);
		getContentPane().setLayout(groupLayout);
	}
}
