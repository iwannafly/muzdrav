package ru.nkz.ivcgzo.ldsclient;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JSplitPane;
import javax.swing.JTextPane;
import javax.swing.JList;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierList;
import ru.nkz.ivcgzo.ldsThrift.Sh_lds;
import ru.nkz.ivcgzo.ldsThrift.Sh_ldsNotFoundException;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import javax.swing.event.AncestorListener;
import javax.swing.event.AncestorEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.thrift.TException;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Dialog.ModalityType;

public class ShabOpRezName extends JDialog {
	
	PIslForm winPat; 
	
	private JPanel contentPane;
	private JTextField textField;
	public JTextPane tPOpiZak;
	public ThriftStringClassifierList<StringClassifier> listName;
	//public JList listName;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ShabOpRezName frame = new ShabOpRezName();
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
	public ShabOpRezName() {
		setModalityType(ModalityType.APPLICATION_MODAL);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 684, 498);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		
		JSplitPane splitPane = new JSplitPane();
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 666, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 454, Short.MAX_VALUE)
		);
		
		JPanel panel_1 = new JPanel();
		splitPane.setLeftComponent(panel_1);
		
		JPanel panel_3 = new JPanel();
		
		listName = new ThriftStringClassifierList<>();
		listName.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
				if (arg0.getClickCount() == 2) {

				setVisible(false);
				
				}
				
				
			}
		});
		listName.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			
			@Override
			public void valueChanged(ListSelectionEvent arg0){
			
				OpisZakl(listName.getSelectedPcod(), listName.getSelectedValue().name);

			}

		});
		
		
		
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addComponent(panel_3, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
				.addComponent(listName, GroupLayout.DEFAULT_SIZE, 337, Short.MAX_VALUE)
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addComponent(panel_3, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(listName, GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE))
		);
		
		JLabel lblNewLabel = new JLabel("Найти");
		
		textField = new JTextField();
		textField.setColumns(10);
		
		JButton btnNewButton = new JButton("Поиск");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				if (textField.getText().length()>0){
					for (int i = 0; i<listName.getData().size(); i++){
						
						//System.out.print(listName.getData().get(i).name.equals(textField.getText()));
						
						if(textField.getText().toLowerCase().equals(listName.getData().get(i).name.toLowerCase())){
							listName.setSelectedIndex(i);
							break;
						} 
					
					}
				}
				
			}
		});
		GroupLayout gl_panel_3 = new GroupLayout(panel_3);
		gl_panel_3.setHorizontalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton)
					.addContainerGap(31, Short.MAX_VALUE))
		);
		gl_panel_3.setVerticalGroup(
			gl_panel_3.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_3.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_3.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNewLabel)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnNewButton))
					.addContainerGap(28, Short.MAX_VALUE))
		);
		panel_3.setLayout(gl_panel_3);
		panel_1.setLayout(gl_panel_1);
		
		JPanel panel_2 = new JPanel();
		splitPane.setRightComponent(panel_2);
		
		tPOpiZak = new JTextPane();
		GroupLayout gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(tPOpiZak, GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
		);
		gl_panel_2.setVerticalGroup(
			gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addComponent(tPOpiZak, GroupLayout.DEFAULT_SIZE, 370, Short.MAX_VALUE)
		);
		panel_2.setLayout(gl_panel_2);
		panel.setLayout(gl_panel);
	}
	
public void OpisZakl(String isl, String naz){

	try {
		List<Sh_lds> Dann = MainForm.ltc.getDSh_lds(isl, naz);
		
		tPOpiZak.setText("Описание: "+ Dann.get(0).opis +"\n \n Заключение: "+Dann.get(0).zakl);
		//tPOpiZak.ad
	} catch (Sh_ldsNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (TException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	//tPOpisZakl(isl, naz)
	
	
}	

public String VozvOpis(){
	
	String opis = null;
	
	try {
		List<Sh_lds> perDann = MainForm.ltc.getDSh_lds(listName.getSelectedPcod(), listName.getSelectedValue().name);
		
		opis = perDann.get(0).opis;
		//System.out.print(listName.getSelectedValue().name);
		 //opis = perDann.get(listName.getSelectedIndex()).opis;
		
	} catch (Sh_ldsNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (TException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return opis;
	
	
}


public String VozvZak(){
	
	String zak = null;
	
	try {
		List<Sh_lds> perDann = MainForm.ltc.getDSh_lds(listName.getSelectedPcod(), listName.getSelectedValue().name);
		
		zak = perDann.get(0).opis;
		//System.out.print(MainForm.ltc.getDSh_lds(listName.getSelectedPcod(), listName.getSelectedValue().name));
		 //zak = perDann.get(listName.getSelectedIndex()).opis;
		
	} catch (Sh_ldsNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (TException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return zak;
	
	
}


}
