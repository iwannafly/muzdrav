package ru.nkz.ivcgzo.clientOsm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.Metod;
import ru.nkz.ivcgzo.thriftOsm.Pokaz;
import ru.nkz.ivcgzo.thriftOsm.PokazMet;

public class PrintForm extends JFrame{
	private static final long serialVersionUID = -9078314618826266514L;
	private JRadioButton rbMethods;
	private JRadioButton rbPokaz;
	public ThriftIntegerClassifierCombobox<IntegerClassifier> cbVidIssl;
	public ThriftStringClassifierCombobox<StringClassifier> cbSys;
	public CustomTable<Metod, Metod._Fields> tabMet;
	private CustomTable<PokazMet, PokazMet._Fields> tabPokazMet;
	private CustomTable<Pokaz, Pokaz._Fields> tabPokaz;

	
	public PrintForm() {

		
		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.LEFT);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(tabbedPane, GroupLayout.DEFAULT_SIZE, 302, Short.MAX_VALUE)
		);
		
		JPanel pNaprIssl = new JPanel();
		tabbedPane.addTab("<html>Направление на<br>\r\nисследование</html>", null, pNaprIssl, null);
		
		final JLabel lblVidIssl = new JLabel("Вид исследования");
		
		cbVidIssl = new ThriftIntegerClassifierCombobox<>(true);
		cbVidIssl.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (cbVidIssl.getSelectedItem()!= null){
					try {
						if (rbMethods.isSelected()){//
								tabMet.setData(MainForm.tcl.getMetod(cbVidIssl.getSelectedItem().pcod));
						}
						if (rbPokaz.isSelected()){
							cbSys.setSelectedItem(null);
							cbSys.setData(MainForm.tcl.get_n_nz1(cbVidIssl.getSelectedItem().pcod));	
								}
						} catch (KmiacServerException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (TException e) {
							// TODO Auto-generated catch block
							MainForm.conMan.reconnect(e);
							
						}
					}
										
				}
		});
		
		
		final JScrollPane spMet = new JScrollPane();
		
		final JLabel lblMet = new JLabel("Методы");
		final JLabel lblSys = new JLabel("Органы и системы");
		
		final JLabel lblPokazMet = new JLabel("Показатели");
		
		final JScrollPane spPokazMet = new JScrollPane();
		
		final JScrollPane spPokaz = new JScrollPane();
		
		 rbMethods = new JRadioButton("Методы исследований");
		 rbMethods.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent arg0) {
		 	rbPokaz.setSelected(false);	
		 	spPokaz.setVisible(false);
		 	tabPokaz.setVisible(false);
		 	lblSys.setVisible(false);
		 	cbSys.setVisible(false);
		 	cbVidIssl.setVisible(true);
		 	lblMet.setVisible(true);
		 	spMet.setVisible(true);
		 	tabMet.setVisible(true);
		 	lblPokazMet.setVisible(true);
		 	spPokazMet.setVisible(true);
		 	tabPokazMet.setVisible(true);
		 	}
		 });
		 
		
		
		 rbPokaz = new JRadioButton("Показатели");
		 rbPokaz.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 		rbMethods.setSelected(false);
			 	lblMet.setVisible(false);
			 	spMet.setVisible(false);
			 	tabMet.setVisible(false);
			 	lblPokazMet.setVisible(false);
		 	spPokazMet.setVisible(false);
		 	tabPokazMet.setVisible(false);
			 	lblSys.setVisible(true);
			 	cbSys.setVisible(true);
			 	spPokaz.setVisible(true);
			 	tabPokaz.setVisible(true);}
		 });
		
		
		
		 cbSys = new ThriftStringClassifierCombobox<>(true);
		 cbSys.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
				try {
					if (cbSys.getSelectedItem() != null)
					tabPokaz.setData(MainForm.tcl.getPokaz(cbVidIssl.getSelectedItem().pcod,cbSys.getSelectedItem().pcod));
				} catch (KmiacServerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
}
		 });
		
		JButton btnNewButton = new JButton("Печать");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (rbMethods.isSelected()){
					if ((cbVidIssl.getSelectedItem() != null) && (tabMet.getSelectedItem() != null)) {
						List<String> selItems = new ArrayList<>();
						for (PokazMet pokazMet : tabPokazMet.getData()) {
							if (pokazMet.vybor)
								selItems.add(pokazMet.getPcod());
						}
						if (selItems.size() != 0) {
							String servPath = MainForm.tcl.printIsslMetod(cbVidIssl.getSelectedItem().pcod, MainForm.authInfo.user_id, Vvod.zapVrSave.getNpasp(), tabMet.getSelectedItem().getObst(), selItems);
							String cliPath = File.createTempFile("muzdrav", ".htm").getAbsolutePath();
							MainForm.conMan.transferFileFromServer(servPath, cliPath);	
						}
					}
				}
				/*if (rbPokaz.isSelected()){
					if ((cbVidIssl.getSelectedItem() != null) && (cbSys.getSelectedItem() != null)) {
						List<String> selItems = new ArrayList<>();
						for (Pokaz pokaz : tabPokaz.getData()) {
							if (pokaz.vybor)
								selItems.add(pokaz.getPcod());
						}
						if (selItems.size() != 0) {
							String servPath = MainForm.tcl.printIsslPokaz(cbVidIssl.getSelectedItem().pcod, MainForm.authInfo.user_id, Vvod.zapVrSave.getNpasp(), cbSys.getSelectedItem().pcod, selItems);
							String cliPath = File.createTempFile(null, null).getAbsolutePath();
							MainForm.conMan.transferFileFromServer(servPath, cliPath);	
						}
					}
				}*/	
				}
					catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		
		
		 
		GroupLayout gl_pNaprIssl = new GroupLayout(pNaprIssl);
		gl_pNaprIssl.setHorizontalGroup(
			gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pNaprIssl.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addComponent(rbMethods, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)
							.addGap(683))
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addComponent(spMet, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED, 54, Short.MAX_VALUE)
							.addComponent(spPokaz, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addComponent(spPokazMet, GroupLayout.PREFERRED_SIZE, 408, GroupLayout.PREFERRED_SIZE)
							.addContainerGap(472, Short.MAX_VALUE))
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addComponent(lblPokazMet, GroupLayout.PREFERRED_SIZE, 75, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblMet, Alignment.LEADING)
								.addGroup(gl_pNaprIssl.createSequentialGroup()
									.addComponent(lblVidIssl)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(cbVidIssl, GroupLayout.PREFERRED_SIZE, 302, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED, 29, Short.MAX_VALUE)
									.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
										.addComponent(rbPokaz, GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE)
										.addComponent(lblSys, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 93, GroupLayout.PREFERRED_SIZE))))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(cbSys, GroupLayout.PREFERRED_SIZE, 332, GroupLayout.PREFERRED_SIZE)
							.addContainerGap())
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addComponent(btnNewButton)
							.addContainerGap(791, Short.MAX_VALUE))))
		);
		gl_pNaprIssl.setVerticalGroup(
			gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pNaprIssl.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbMethods, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(rbPokaz, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.BASELINE)
								.addComponent(cbVidIssl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblVidIssl))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblMet))
						.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.BASELINE)
							.addComponent(cbSys, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblSys)))
					.addGap(8)
					.addGroup(gl_pNaprIssl.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pNaprIssl.createSequentialGroup()
							.addComponent(spMet, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblPokazMet)
							.addGap(8)
							.addComponent(spPokazMet, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
						.addComponent(spPokaz, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnNewButton)
					.addGap(181))
		);
		
		tabPokaz = new CustomTable<>(false, true, Pokaz.class, 0,"Код показателя",1,"Наименование",2,"Стоимость",5,"Выбор");
		tabPokaz.setEditableFields(true, 3);
		spPokaz.setViewportView(tabPokaz);
		tabPokaz.setFillsViewportHeight(true);
		
		tabPokazMet = new CustomTable<>(false,true,PokazMet.class,0,"Код",1,"Наименование",2,"Стоимость",4,"Выбор");
		tabPokazMet.setEditableFields(true, 3);
		spPokazMet.setViewportView(tabPokazMet);
		tabPokazMet.setFillsViewportHeight(true);
		
		tabMet = new CustomTable<>(false,true,Metod.class,0,"Код",1,"Наименование");
		spMet.setViewportView(tabMet);
		tabMet.setFillsViewportHeight(true);
			tabMet.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent arg0) {
					if (!arg0.getValueIsAdjusting()){
						if (tabMet.getSelectedItem()!=null){
							try {
								tabPokazMet.setData(MainForm.tcl.getPokazMet(tabMet.getSelectedItem().getObst()));
							} catch (KmiacServerException e) {
								e.printStackTrace();
							} catch (TException e) {
								MainForm.conMan.reconnect(e);
							}

			
						}
					}	
				}
			});
			
		pNaprIssl.setLayout(gl_pNaprIssl);
		getContentPane().setLayout(groupLayout);
		
		rbMethods.doClick();
	}
}
