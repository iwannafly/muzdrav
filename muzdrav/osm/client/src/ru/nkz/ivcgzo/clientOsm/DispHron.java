package ru.nkz.ivcgzo.clientOsm;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomDateEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftStringClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.Pmer;
import ru.nkz.ivcgzo.thriftOsm.Pobost;

public class DispHron extends JFrame{
	private static final long serialVersionUID = -2929416282414434095L;
	private CustomTable<Pmer,Pmer._Fields> tblDispHron;
	private Pmer pmer;
	private ThriftStringClassifierCombobox<StringClassifier> cmbDiag;
	private CustomTable<Pobost,Pobost._Fields> tabObost;
	private Pobost obostr;
	private CustomDateEditor tfDnl;
	private JTextField tfNaprLpu;
	private CustomDateEditor tfDkl;
	private int ter;


	public DispHron() {
		setTitle("Диспансеризация");
		setBounds(100, 100, 1011, 739);
		JScrollPane spDispHron = new JScrollPane();
		spDispHron.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		spDispHron.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(spDispHron, GroupLayout.DEFAULT_SIZE, 442, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(spDispHron, GroupLayout.DEFAULT_SIZE, 266, Short.MAX_VALUE)
		);
		
		JPanel pnlDispHron = new JPanel();
		spDispHron.setViewportView(pnlDispHron);
		
		JScrollPane scrollPane = new JScrollPane();
		
		JButton bAddDispHron = new JButton("");
		bAddDispHron.setIcon(new ImageIcon(DispHron.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
		bAddDispHron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					tblDispHron.requestFocus();
					pmer = new Pmer();
					pmer.setNpasp(Vvod.zapVr.getNpasp());
					pmer.setDiag(cmbDiag.getSelectedPcod());
					pmer.setCpol(MainForm.authInfo.getCpodr());
					pmer.setDataz(System.currentTimeMillis());
					pmer.setId(MainForm.tcl.AddPmer(pmer));
					tblDispHron.addItem(pmer);
					//tblDispHron.setData(MainForm.tcl.getPmer(Vvod.zapVr.getNpasp(), cmbDiag.getSelectedPcod()));
				} catch (KmiacServerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
		
			}
		});
		
		JButton bSaveDispHron = new JButton("");
		bSaveDispHron.setIcon(new ImageIcon(DispHron.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1341981970_Accept.png")));
		bSaveDispHron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					pmer.setCod_sp(tblDispHron.getSelectedItem().getCod_sp());
					pmer.setPmer(tblDispHron.getSelectedItem().getPmer());
					pmer.setPdat(tblDispHron.getSelectedItem().getPdat());
					pmer.setFdat(tblDispHron.getSelectedItem().getFdat());
					pmer.setRez(tblDispHron.getSelectedItem().getRez());
					pmer.setCdol(tblDispHron.getSelectedItem().getCdol());
					if (tfDkl.getDate() != null)pmer.setDkl(tfDkl.getDate().getTime());
					if (tfDkl.getDate() != null)pmer.setDnl(tfDnl.getDate().getTime());
					pmer.setLpu(Integer.valueOf(tfNaprLpu.getText())); 
					pmer.setTer(ter);
					if (!Dsph()) {
						JOptionPane.showMessageDialog(DispHron.this, "Плановая дата не может быть меньше фактической", "Предупреждение", JOptionPane.ERROR_MESSAGE);
							return;
					}
					MainForm.tcl.UpdatePmer(pmer);
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
				}
				}
		});
		
		final JButton bDelDispHron = new JButton("");
		bDelDispHron.setIcon(new ImageIcon(DispHron.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789259_Delete.png")));
		bDelDispHron.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		  		try {
						if (tblDispHron.getSelectedItem()!= null)
						if (JOptionPane.showConfirmDialog(bDelDispHron, "Удалить запись?", "Удаление записи", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
			  			MainForm.tcl.DeletePmer(tblDispHron.getSelectedItem().getId());
						tblDispHron.setData(MainForm.tcl.getPmer(Vvod.zapVr.getNpasp(), cmbDiag.getSelectedPcod()));}
					} catch (KmiacServerException e1) {
						e1.printStackTrace();
					} catch (TException e1) {
						MainForm.conMan.reconnect(e1);
					}
			}
		});
		
		 cmbDiag = new ThriftStringClassifierCombobox<>(true);
		 cmbDiag.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent e) {
		 			try {
		 			tblDispHron.setData(MainForm.tcl.getPmer(Vvod.zapVr.getNpasp(), cmbDiag.getSelectedPcod()));
		 			tabObost.setData(MainForm.tcl.getPobost(Vvod.zapVr.getNpasp(), cmbDiag.getSelectedPcod()));
		 			tblDispHron.setIntegerClassifierSelector(0, ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_abd));
		 			tblDispHron.setStringClassifierSelector(1, MainForm.tcl.get_n_s00(MainForm.authInfo.getClpu()));
		 			tblDispHron.setIntegerClassifierSelector(4, ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_arez));
		 			tabObost.setIntegerClassifierSelector(0, ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_v10));
		 			tabObost.setIntegerClassifierSelector(1, ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_v10));
		 		} catch (KmiacServerException e1) {
		 			e1.printStackTrace();
		 		} catch (TException e1) {
		 			e1.printStackTrace();
		 			MainForm.conMan.reconnect(e1);
		 		}
		 	}
		 });
		 
		
		JLabel lblDiag = new JLabel("Диагноз");
		
		JScrollPane spObost = new JScrollPane();
		
		JLabel lblObost = new JLabel("Случаи обострения");
		
		JButton bAddObost = new JButton("");
		bAddObost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				obostr.setNpasp(Vvod.zapVr.getNpasp());
				obostr.setDiag(cmbDiag.getSelectedPcod());
				obostr.setCod_sp(MainForm.authInfo.getPcod());
				obostr.setCdol(MainForm.authInfo.getCdol());
				obostr.setDataz(System.currentTimeMillis());
				try {
					obostr.setId(MainForm.tcl.AddPobost(obostr));
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
				}
	 			tabObost.addItem(obostr);
			}			
		});
		bAddObost.setIcon(new ImageIcon(DispHron.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789242_Add.png")));
		
		JButton bSaveObost = new JButton("");
		bSaveObost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				tabObost.updateSelectedItem();
				try {	
				obostr.setId(tabObost.getSelectedItem().getId());
			obostr.setSl_hron(tabObost.getSelectedItem().sl_hron);
			obostr.setSl_obostr(tabObost.getSelectedItem().sl_obostr);
		
				MainForm.tcl.UpdatePobost(obostr);
			} catch (KmiacServerException e1) {
				e1.printStackTrace();
			} catch (TException e1) {
				e1.printStackTrace();
				MainForm.conMan.reconnect(e1);
			}
			}
		});
		bSaveObost.setIcon(new ImageIcon(DispHron.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1341981970_Accept.png")));
		
		JButton bDelObost = new JButton("");
		bDelObost.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
		  		try {
						if (tabObost.getSelectedItem()!= null)
						if (JOptionPane.showConfirmDialog(bDelDispHron, "Удалить запись?", "Удаление записи", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
			  			MainForm.tcl.DeletePobost(tabObost.getSelectedItem().getId());
						tabObost.setData(MainForm.tcl.getPobost(Vvod.zapVr.getNpasp(), cmbDiag.getSelectedPcod()));}
					} catch (KmiacServerException e1) {
						e1.printStackTrace();
					} catch (TException e1) {
						MainForm.conMan.reconnect(e1);
					}

			}
		});
		bDelObost.setIcon(new ImageIcon(DispHron.class.getResource("/ru/nkz/ivcgzo/clientOsm/resources/1331789259_Delete.png")));
		
		JLabel lblDnl = new JLabel("Дата начала лечения");
		
		tfDnl = new CustomDateEditor();
		tfDnl.setColumns(10);
		
		JLabel lblDkl = new JLabel("Дата конца лечения");
		
		tfDkl = new CustomDateEditor();
		tfDkl.setColumns(10);
		
		JLabel lblNaprLpu = new JLabel("Направлен в ЛПУ");
		
		tfNaprLpu = new JTextField();
		tfNaprLpu.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					 int[] res = MainForm.conMan.showPolpTreeForm("Выбор ЛПУ", 0, 0, 0);
					 if (res != null) {
                         tfNaprLpu.setText(Integer.toString(res[1]));
                         ter=res[0];
                  }
				}
			}
		});
		tfNaprLpu.setColumns(10);
		GroupLayout gl_pnlDispHron = new GroupLayout(pnlDispHron);
		gl_pnlDispHron.setHorizontalGroup(
			gl_pnlDispHron.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDispHron.createSequentialGroup()
					.addGroup(gl_pnlDispHron.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlDispHron.createSequentialGroup()
							.addGap(5)
							.addComponent(lblDiag)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(cmbDiag, GroupLayout.PREFERRED_SIZE, 58, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlDispHron.createSequentialGroup()
							.addContainerGap()
							.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 983, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlDispHron.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblDnl)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfDnl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblDkl, GroupLayout.PREFERRED_SIZE, 110, GroupLayout.PREFERRED_SIZE)
							.addGap(4)
							.addComponent(tfDkl, GroupLayout.PREFERRED_SIZE, 86, GroupLayout.PREFERRED_SIZE)
							.addGap(33)
							.addComponent(lblNaprLpu)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tfNaprLpu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlDispHron.createSequentialGroup()
							.addContainerGap()
							.addComponent(bAddDispHron)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(bSaveDispHron)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(bDelDispHron))
						.addGroup(gl_pnlDispHron.createSequentialGroup()
							.addContainerGap()
							.addGroup(gl_pnlDispHron.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_pnlDispHron.createSequentialGroup()
									.addComponent(bAddObost)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(bSaveObost)
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(bDelObost))
								.addComponent(spObost, GroupLayout.PREFERRED_SIZE, 969, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblObost))))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		gl_pnlDispHron.setVerticalGroup(
			gl_pnlDispHron.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlDispHron.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlDispHron.createParallelGroup(Alignment.BASELINE)
						.addComponent(cmbDiag, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblDiag))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.PREFERRED_SIZE, 254, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_pnlDispHron.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_pnlDispHron.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblDnl)
							.addComponent(tfDnl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_pnlDispHron.createSequentialGroup()
							.addGap(3)
							.addComponent(lblDkl))
						.addGroup(gl_pnlDispHron.createParallelGroup(Alignment.BASELINE)
							.addComponent(tfDkl, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblNaprLpu)
							.addComponent(tfNaprLpu, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_pnlDispHron.createParallelGroup(Alignment.BASELINE)
						.addComponent(bAddDispHron)
						.addComponent(bSaveDispHron)
						.addComponent(bDelDispHron))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblObost)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(spObost, GroupLayout.PREFERRED_SIZE, 215, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_pnlDispHron.createParallelGroup(Alignment.LEADING)
						.addComponent(bAddObost)
						.addComponent(bSaveObost)
						.addComponent(bDelObost))
					.addContainerGap(892, Short.MAX_VALUE))
		);
		
		tabObost = new CustomTable<>(true, true, Pobost.class,4,"Случай обострения",5,"Случай хронического очага",8,"Дата обострения");
		tabObost.setDateField(2);
		tabObost.setFillsViewportHeight(true);
		spObost.setViewportView(tabObost);
		
		tblDispHron = new CustomTable<>(true,true,Pmer.class,3,"Мероприятие",10,"Специалист",4,"Дата план.",5,"Дата факт.",9,"Результат");
		tblDispHron.setDateField(2);
		tblDispHron.setDateField(3);
		
		tblDispHron.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			if (!arg0.getValueIsAdjusting()){
				if (tblDispHron.getSelectedItem()!= null) {
					pmer = tblDispHron.getSelectedItem();	
					if (pmer.isSetDkl())
						tfDkl.setDate(pmer.getDkl());
					else
						tfDkl.setValue(null);
					if (pmer.isSetDnl())
						tfDnl.setDate(pmer.getDnl());
					else
						tfDnl.setValue(null);
					tfNaprLpu.setText(String.valueOf(pmer.getLpu()));
				}
			}
		}
				});
	
		tblDispHron.setFillsViewportHeight(true);
		scrollPane.setViewportView(tblDispHron);
		pnlDispHron.setLayout(gl_pnlDispHron);
		getContentPane().setLayout(groupLayout);
	}
	
	public void ShowDispHron(){
			try {
				pmer = new Pmer();
				obostr = new Pobost();
				cmbDiag.setData(MainForm.tcl.get_n_c00(Vvod.zapVr.getNpasp()));
			} catch (KmiacServerException e) {
				e.printStackTrace();
			} catch (TException e) {
				e.printStackTrace();
			}
		setVisible(true);	
	}
	
	public boolean Dsph() throws TException{
		if (tblDispHron.getData().size() > 0){
			if (tblDispHron.getSelectedItem().getPdat()>tblDispHron.getSelectedItem().getFdat()) return false;
		}
		return true;
	}
}
