package ru.nkz.ivcgzo.clientOsm;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.Shablon;

import javax.swing.border.TitledBorder;
import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.thrift.TException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JEditorPane;

public class ShablonForm  extends JFrame{
	private static final long serialVersionUID = -6616098681222163927L;
	private JTextField tfsh;
	private ThriftIntegerClassifierList shlist;
	private JEditorPane editorPane;

	/**
	 * Create the application.
	 */
	public ShablonForm() {
		setBounds(100, 100, 714, 574);
		
		JSplitPane splitPaneSh = new JSplitPane();
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPaneSh, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPaneSh, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
		);
		
		JPanel lp = new JPanel();
		lp.setBorder(new TitledBorder(null, "\u041F\u043E\u0438\u0441\u043A", TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLACK));
		splitPaneSh.setLeftComponent(lp);
		
		tfsh = new JTextField();
		tfsh.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					shlist.setData(MainForm.tcl.getShPoisk(tfsh.getText()));
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
				}
	
			}
		});
		tfsh.setColumns(10);
		
		JScrollPane spsh = new JScrollPane();
		GroupLayout gl_lp = new GroupLayout(lp);
		gl_lp.setHorizontalGroup(
			gl_lp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_lp.createSequentialGroup()
					.addGap(0, 0, Short.MAX_VALUE)
					.addComponent(tfsh, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
					.addGap(76))
				.addComponent(spsh, GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
		);
		gl_lp.setVerticalGroup(
			gl_lp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_lp.createSequentialGroup()
					.addComponent(tfsh, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(spsh, GroupLayout.DEFAULT_SIZE, 212, Short.MAX_VALUE)
					.addContainerGap())
		);
		editorPane = new JEditorPane();
		
		 shlist = new ThriftIntegerClassifierList();
		 shlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		 shlist.addListSelectionListener(new ListSelectionListener() {
		 	public void valueChanged(ListSelectionEvent e) {
		 		if (!e.getValueIsAdjusting()){
		 			try {
		 				List<String> sp = new ArrayList<>();
		 				for (Shablon sha : MainForm.tcl.getSh(shlist.getSelectedValue().getPcod())) {
		 					sp.add(sha.razd);
		 				}

							editorPane.setText(sp.toString());
					
					} catch (KmiacServerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
		 			
		 		}
			 			
		 	}
		 });;
		spsh.setViewportView(shlist);
		lp.setLayout(gl_lp);
		
		JPanel rp = new JPanel();
		splitPaneSh.setRightComponent(rp);
		
		 
		GroupLayout gl_rp = new GroupLayout(rp);
		gl_rp.setHorizontalGroup(
			gl_rp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rp.createSequentialGroup()
					.addContainerGap()
					.addComponent(editorPane, GroupLayout.PREFERRED_SIZE, 274, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(179, Short.MAX_VALUE))
		);
		gl_rp.setVerticalGroup(
			gl_rp.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_rp.createSequentialGroup()
					.addGap(5)
					.addComponent(editorPane, GroupLayout.PREFERRED_SIZE, 369, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(164, Short.MAX_VALUE))
		);
		rp.setLayout(gl_rp);
		getContentPane().setLayout(groupLayout);
	}
	
	public void showShablonForm() {
		try {
			shlist.setData(MainForm.tcl.getShablon());
		} catch (KmiacServerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setVisible(true);
	}
	

}
	
