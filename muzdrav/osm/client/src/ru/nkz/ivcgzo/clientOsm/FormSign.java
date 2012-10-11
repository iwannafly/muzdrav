package ru.nkz.ivcgzo.clientOsm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import javax.swing.text.BadLocationException;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextComponentWrapper;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOsm.Psign;
import ru.nkz.ivcgzo.thriftOsm.PsignNotFoundException;

public class FormSign extends JFrame {
	private static final long serialVersionUID = -5267798845014525253L;
	private ShablonDopEditorPane tpfarm;
	private ShablonDopEditorPane tpanamnz;
	private ShablonDopEditorPane tpallerg;
	private static ShablonDopEditorPane selShabPane;
	private ThriftIntegerClassifierList listShablon;
	private Psign psign;
	private JCheckBox cbk;
	private JCheckBox cba;
	private JCheckBox cba1;
	private JCheckBox cbn;
	private ButtonGroup BGgrk;
	private JRadioButton rb1g;
	private JRadioButton rb2g;
	private JRadioButton rb3g;
	private JRadioButton rb4g;
	private ButtonGroup BGRez;
	private JRadioButton rbpol;
	private JRadioButton rbotr;
	private String vrp;
	public static List<IntegerClassifier> pokNames;

	/**
	 * Create the frame.
	 */
	public FormSign() {
		setTitle("Анамнез жизни");
		setBounds(100, 100, 1011, 511);
		
		final JScrollPane spAnamn = new JScrollPane();
		spAnamn.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
		spAnamn.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		
		listShablon = new ThriftIntegerClassifierList();
		listShablon.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					if (listShablon.getSelectedValue() != null)
						if (selShabPane != null)
							try {
								selShabPane.getDocument().insertString(selShabPane.getText().length(), MainForm.tcl.getShDop(listShablon.getSelectedPcod()).name, null);
							} catch (BadLocationException e1) {
								e1.printStackTrace();
							} catch (KmiacServerException e1) {
								JOptionPane.showMessageDialog(FormSign.this, "Ошибка загрузка текста шаблона", "Ошибка", JOptionPane.ERROR_MESSAGE);
							} catch (TException e1) {
								e1.printStackTrace();
								MainForm.conMan.reconnect(e1);
							}
			}
		});
		
		JScrollPane spSh = new JScrollPane();
		
		spSh.setViewportView(listShablon);
		
		JLabel lblVr = new JLabel("Вредные привычки");
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		
		cbk = new JCheckBox("Курение");
		
		cba = new JCheckBox("Злоупотребление алкоголем");
		
		cbn = new JCheckBox("Наркотики");
		
		cba1 = new JCheckBox("Алкоголизм");
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addComponent(cbk)
					.addGap(18)
					.addComponent(cba)
					.addGap(18)
					.addComponent(cba1)
					.addGap(18)
					.addComponent(cbn)
					.addContainerGap(60, Short.MAX_VALUE))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel_1.createParallelGroup(Alignment.BASELINE)
						.addComponent(cbk)
						.addComponent(cba)
						.addComponent(cba1)
						.addComponent(cbn))
					.addContainerGap(8, Short.MAX_VALUE))
		);
		
		final JLabel lblGrkr = new JLabel("Группа крови");
		
		JPanel pgrk = new JPanel();
		pgrk.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		rb1g = new JRadioButton("I", true);
		pgrk.add(rb1g);
		rb2g = new JRadioButton("II", true);
		pgrk.add(rb2g);
		rb3g = new JRadioButton("III", true);
		pgrk.add(rb3g);
		rb4g = new JRadioButton("IV", true);
		pgrk.add(rb4g);
		BGgrk = new ButtonGroup();
		BGgrk.add(rb1g);
		BGgrk.add(rb2g);
		BGgrk.add(rb3g);
		BGgrk.add(rb4g);
		
		final JLabel lblRezus = new JLabel("Резус-фактор");
		
		JPanel prezus = new JPanel();
		prezus.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		rbpol = new JRadioButton("+", true);
		prezus.add(rbpol);
		rbotr = new JRadioButton("-", true);
		prezus.add(rbotr);
		BGRez = new ButtonGroup();		
		BGRez.add(rbpol);
		BGRez.add(rbotr);
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblGrkr)
									.addGap(5)
									.addComponent(pgrk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addGap(18)
									.addComponent(lblRezus)
									.addGap(4)
									.addComponent(prezus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
								.addComponent(spAnamn, GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(spSh, GroupLayout.DEFAULT_SIZE, 417, Short.MAX_VALUE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(475)
							.addComponent(lblVr)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(11)
									.addComponent(lblGrkr))
								.addComponent(pgrk, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addGap(13)
									.addComponent(lblRezus))
								.addComponent(prezus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(20)
							.addComponent(lblVr)))
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(spSh, GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
						.addComponent(spAnamn, GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JPanel pAnamn = new JPanel();
		spAnamn.setViewportView(pAnamn);
		getContentPane().setLayout(groupLayout);
		
		
		final JLabel lblFarm = new JLabel("Фармакологический анамнез");
		
		final JLabel lblAnamnz = new JLabel("Анамнез жизни");
		
		tpfarm = new ShablonDopEditorPane(5); //5
		tpfarm.setBorder(UIManager.getBorder("TextField.border"));
		
		tpanamnz = new ShablonDopEditorPane(4); //4
		tpanamnz.setBorder(UIManager.getBorder("TextField.border"));
		
		JLabel label = new JLabel("Аллергоанамнез");
		
		tpallerg = new ShablonDopEditorPane(3); //3
		tpallerg.setBorder(UIManager.getBorder("TextField.border"));
		
		JButton button = new JButton("Сохранить");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (rb1g.isSelected()) {
					psign.setGrup("1");
				}
				if (rb2g.isSelected()) {
					psign.setGrup("2");
				}
				if (rb3g.isSelected()) {
					psign.setGrup("3");
				}
				if (rb4g.isSelected()) {
					psign.setGrup("4");
				}
				if (rbpol.isSelected()) {
					psign.setPh("+");
				}
				if (rbotr.isSelected()) {
					psign.setPh("-");
				}
				
				psign.setVitae(tpanamnz.getText());
				psign.setAllerg(tpallerg.getText());
				psign.setFarmkol(tpfarm.getText());
				psign.setVitae(tpanamnz.getText());
				psign.setVred(getVrPr());
				
				try {
					MainForm.tcl.setPsign(psign);
				} catch (KmiacServerException e1) {
					e1.printStackTrace();
				} catch (TException e1) {
					e1.printStackTrace();
				}
			}
		});
		GroupLayout gl_pAnamn = new GroupLayout(pAnamn);
		gl_pAnamn.setHorizontalGroup(
			gl_pAnamn.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pAnamn.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pAnamn.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFarm, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_pAnamn.createParallelGroup(Alignment.TRAILING, false)
							.addComponent(lblAnamnz, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
							.addComponent(tpanamnz, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 524, Short.MAX_VALUE)
							.addComponent(label, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 113, GroupLayout.PREFERRED_SIZE)
							.addComponent(tpallerg, Alignment.LEADING)
							.addComponent(tpfarm, Alignment.LEADING))
						.addComponent(button, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(13, Short.MAX_VALUE))
		);
		gl_pAnamn.setVerticalGroup(
			gl_pAnamn.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pAnamn.createSequentialGroup()
					.addGap(18)
					.addComponent(lblAnamnz)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tpanamnz, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(label)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpallerg, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(lblFarm)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tpfarm, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(button)
					.addContainerGap(42, Short.MAX_VALUE))
		);
		pAnamn.setLayout(gl_pAnamn);
		getContentPane().setLayout(groupLayout);
	}
	
	private class ShablonDopEditorPane extends JEditorPane {
		private static final long serialVersionUID = -2297529095988741139L;
		private int idRazd;
		
		public ShablonDopEditorPane(int idRazd) {
			super();
			
			this.idRazd = idRazd;
			
			setFocusListener();
			new CustomTextComponentWrapper(this).setPopupMenu();
		}
		
		private void setFocusListener() {
			addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					try {
						selShabPane = ShablonDopEditorPane.this;
						listShablon.setData(MainForm.tcl.getShDopNames(getIdRazd()));
					} catch (KmiacServerException e1) {
						JOptionPane.showMessageDialog(FormSign.this, "Ошибка загрузка названий шаблонов", "Ошибка", JOptionPane.ERROR_MESSAGE);
						handleError();
					} catch (TException e1) {
						e1.printStackTrace();
						handleError();
						MainForm.conMan.reconnect(e1);
					}
				}
				
				private void handleError() {
					selShabPane = null;
					listShablon.setData(null);
					listShablon.requestFocusInWindow();
				}
			});
		}
		
		private int getIdRazd() {
			return idRazd;
		}
	}
		
		private String getVrPr() {
			String prv,s1,s2,s3,s4;
			if (cbk.isSelected()){
				s1 = "1";	
			}else {
				s1 = "0";
			}
	
			if (cba.isSelected()){
				s2 = "1";	
				}else {
					s2 = "0";
				}
			if (cba1.isSelected()){
				s3 = "1";	
				}else {
					s3 = "0";
				}
			if (cbn.isSelected()){
				s4 = "1";	
				}else {
					s4 = "0";
				}
			
			prv = s1+s2+s3+s4;

			return prv;
		}
		
		public void showPsign() {
			BGgrk.clearSelection();
			BGRez.clearSelection();
			cbk.setSelected(false);
			cba.setSelected(false);
			cbn.setSelected(false);
			tpallerg.setText("");
			tpfarm.setText("");
			tpanamnz.setText("");
		try {
			psign = MainForm.tcl.getPsign(Vvod.zapVr.npasp);
			
			rb1g.setSelected(psign.grup.charAt(0) == '1');
			rb2g.setSelected(psign.grup.charAt(0) == '2');
			rb3g.setSelected(psign.grup.charAt(0) == '3');
			rb4g.setSelected(psign.grup.charAt(0) == '4');
			
			rbpol.setSelected(psign.ph.charAt(0) == '+');
			rbotr.setSelected(psign.ph.charAt(0) == '-');
			
			tpallerg.setText(psign.allerg);
			tpanamnz.setText(psign.vitae);
			tpfarm.setText(psign.farmkol);
			
			vrp = psign.getVred();
			cbk.setSelected(vrp.charAt(0) == '1');
			cba.setSelected(vrp.charAt(1) == '1');
			cba1.setSelected(vrp.charAt(2) == '1');
			cbn.setSelected(vrp.charAt(3) == '1');
		} catch (KmiacServerException e1) {
			e1.printStackTrace();
		} catch (PsignNotFoundException e1) {
			psign = new Psign();
			psign.setNpasp(Vvod.zapVr.npasp);
		} catch (TException e1) {
			MainForm.conMan.reconnect(e1);
		}
		
		setVisible(true);
	}
}

