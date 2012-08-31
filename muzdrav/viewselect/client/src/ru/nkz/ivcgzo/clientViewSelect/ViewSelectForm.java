package ru.nkz.ivcgzo.clientViewSelect;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class ViewSelectForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5779413219773068517L;
	public JTextField tfSearch;
	public JScrollPane spClassifier;
	public int aws;
	public int ahs;
	
	public void createGUI() {
		// Размеры окна
		Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
		/**
			* aws - Ширина окна на треть экрана
			* ahs - Высота окна на весь экран
			*/
		aws=dm.width/3;
		ahs=dm.height;
		setTitle("Выбор из классификатора");
		//setBounds(dm.width-aws, 0, aws, ahs);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		tfSearch = new JTextField();
		getContentPane().add(tfSearch, BorderLayout.NORTH);
		tfSearch.setColumns(10);
		spClassifier = new JScrollPane();
		getContentPane().add(spClassifier, BorderLayout.CENTER);
		
	}
}
