package ru.nkz.ivcgzo.clientRegPatient;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.Timer;
import javax.swing.border.TitledBorder;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.JTextArea;
import javax.swing.JLabel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftRegPatient.ShablonText;
import ru.nkz.ivcgzo.thriftRegPatient.Shablon;

public class PervOsmPanel extends JPanel{
	private static final long serialVersionUID = 3983672050577075155L;
	private ThriftIntegerClassifierList lbShabSrc;
	private ShablonSearchListener shablonSearchListener;
	private ShablonForm shablonform;
	private CustomTextField tbShabSrc;
	private CustomTextField tbStatTemp;
	private CustomTextField tbStatAd;
	private CustomTextField tbStatChss;
	private CustomTextField tbStatRost;
	private CustomTextField tbStatVes;
	private JTextArea tbJal;
	private JTextArea tbFiz;
	private JTextArea tbLoc;
	private JTextArea tbZakl;
	private JTextArea tbStat;
	private JTextArea tbAnam;
	private JTextArea tbLech;
	private JLabel lblLastShab;

	public PervOsmPanel() {
		
		shablonform = new ShablonForm();
//		MainForm.instance.addChildFrame(shablonform);

		JPanel pnlOsm = new JPanel();
		
		JPanel pnlShablon = new JPanel();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(pnlOsm, GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlShablon, GroupLayout.PREFERRED_SIZE, 244, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addComponent(pnlShablon, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
						.addComponent(pnlOsm, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 435, Short.MAX_VALUE))
					.addContainerGap(171, Short.MAX_VALUE))
		);
		
		JScrollPane spOsm = new JScrollPane();
		GroupLayout gl_pnlOsm = new GroupLayout(pnlOsm);
		gl_pnlOsm.setHorizontalGroup(
			gl_pnlOsm.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlOsm.createSequentialGroup()
					.addGap(2)
					.addComponent(spOsm, GroupLayout.DEFAULT_SIZE, 532, Short.MAX_VALUE))
		);
		gl_pnlOsm.setVerticalGroup(
			gl_pnlOsm.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlOsm.createSequentialGroup()
					.addComponent(spOsm, GroupLayout.PREFERRED_SIZE, 431, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(312, Short.MAX_VALUE))
		);
		
		JPanel pnlOsmOsm = new JPanel();
		spOsm.setViewportView(pnlOsmOsm);
		
		JPanel pnlJal = new JPanel();
		pnlJal.setBorder(new TitledBorder(null, "Жалобы:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel pnlAnam = new JPanel();
		pnlAnam.setBorder(new TitledBorder(null, "Анамнез заболевания:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel pnlStat = new JPanel();
		pnlStat.setBorder(new TitledBorder(null, "Объективный статус:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel pnlFiz = new JPanel();
		pnlFiz.setBorder(new TitledBorder(null, "Физкальное обследование:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel pnlLoc = new JPanel();
		pnlLoc.setBorder(new TitledBorder(null, "Локальный статус:", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_pnlOsmOsm = new GroupLayout(pnlOsmOsm);
		gl_pnlOsmOsm.setHorizontalGroup(
			gl_pnlOsmOsm.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlOsmOsm.createSequentialGroup()
					.addGroup(gl_pnlOsmOsm.createParallelGroup(Alignment.TRAILING)
						.addComponent(pnlLoc, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
						.addComponent(pnlFiz, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
						.addComponent(pnlStat, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(pnlAnam, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
						.addComponent(pnlJal, GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE))
					.addGap(20))
		);
		gl_pnlOsmOsm.setVerticalGroup(
			gl_pnlOsmOsm.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlOsmOsm.createSequentialGroup()
					.addContainerGap()
					.addComponent(pnlJal, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlAnam, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlStat, GroupLayout.PREFERRED_SIZE, 99, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlFiz, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlLoc, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addGap(301))
		);
		
		JScrollPane spLoc = new JScrollPane();
		GroupLayout gl_pnlLoc = new GroupLayout(pnlLoc);
		gl_pnlLoc.setHorizontalGroup(
			gl_pnlLoc.createParallelGroup(Alignment.LEADING)
				.addComponent(spLoc, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
		);
		gl_pnlLoc.setVerticalGroup(
			gl_pnlLoc.createParallelGroup(Alignment.LEADING)
				.addComponent(spLoc, GroupLayout.PREFERRED_SIZE, 18, Short.MAX_VALUE)
		);
		
		tbLoc = new JTextArea();
		tbLoc.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tbLoc.setLineWrap(true);
		tbLoc.setWrapStyleWord(true);
		spLoc.setViewportView(tbLoc);
		pnlLoc.setLayout(gl_pnlLoc);
		
		JScrollPane spFiz = new JScrollPane();
		GroupLayout gl_pnlFiz = new GroupLayout(pnlFiz);
		gl_pnlFiz.setHorizontalGroup(
			gl_pnlFiz.createParallelGroup(Alignment.LEADING)
				.addComponent(spFiz, GroupLayout.DEFAULT_SIZE, 448, Short.MAX_VALUE)
		);
		gl_pnlFiz.setVerticalGroup(
			gl_pnlFiz.createParallelGroup(Alignment.LEADING)
				.addComponent(spFiz, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
		);
		
		tbFiz = new JTextArea();
		tbFiz.setWrapStyleWord(true);
		tbFiz.setLineWrap(true);
		tbFiz.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spFiz.setViewportView(tbFiz);
		pnlFiz.setLayout(gl_pnlFiz);
		
		JLabel lblStatTemp = new JLabel("Tемп.");
		lblStatTemp.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblStatAd = new JLabel("АД");
		lblStatAd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblStatChss = new JLabel("Чсс");
		lblStatChss.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblStatRost = new JLabel("Рост");
		lblStatRost.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		JLabel lblStatVes = new JLabel("Вес");
		lblStatVes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		
		tbStatTemp = new CustomTextField();
		tbStatTemp.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tbStatTemp.setColumns(10);
		
		tbStatAd = new CustomTextField();
		tbStatAd.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tbStatAd.setColumns(10);
		
		tbStatChss = new CustomTextField();
		tbStatChss.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tbStatChss.setColumns(10);
		
		tbStatRost = new CustomTextField();
		tbStatRost.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tbStatRost.setColumns(10);
		
		tbStatVes = new CustomTextField();
		tbStatVes.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tbStatVes.setColumns(10);
		
		JScrollPane spStat = new JScrollPane();
		GroupLayout gl_pnlStat = new GroupLayout(pnlStat);
		gl_pnlStat.setHorizontalGroup(
			gl_pnlStat.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlStat.createSequentialGroup()
					.addComponent(lblStatTemp)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tbStatTemp, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblStatAd)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tbStatAd, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblStatChss)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tbStatChss, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblStatRost)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tbStatRost, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblStatVes)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(tbStatVes, GroupLayout.PREFERRED_SIZE, 56, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(29, Short.MAX_VALUE))
				.addComponent(spStat, GroupLayout.DEFAULT_SIZE, 477, Short.MAX_VALUE)
		);
		gl_pnlStat.setVerticalGroup(
			gl_pnlStat.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlStat.createSequentialGroup()
					.addGroup(gl_pnlStat.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblStatTemp)
						.addComponent(lblStatVes)
						.addComponent(tbStatTemp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStatAd)
						.addComponent(tbStatAd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStatChss)
						.addComponent(tbStatChss, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblStatRost)
						.addComponent(tbStatRost, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tbStatVes, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spStat, GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE))
		);
		
		tbStat = new JTextArea();
		tbStat.setWrapStyleWord(true);
		tbStat.setLineWrap(true);
		tbStat.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spStat.setViewportView(tbStat);
		pnlStat.setLayout(gl_pnlStat);
		
		JScrollPane spAnam = new JScrollPane();
		GroupLayout gl_pnlAnam = new GroupLayout(pnlAnam);
		gl_pnlAnam.setHorizontalGroup(
			gl_pnlAnam.createParallelGroup(Alignment.LEADING)
				.addComponent(spAnam, GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
		);
		gl_pnlAnam.setVerticalGroup(
			gl_pnlAnam.createParallelGroup(Alignment.LEADING)
				.addComponent(spAnam, GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
		);
		
		tbAnam = new JTextArea();
		tbAnam.setWrapStyleWord(true);
		tbAnam.setLineWrap(true);
		tbAnam.setFont(new Font("Tahoma", Font.PLAIN, 12));
		spAnam.setViewportView(tbAnam);
		pnlAnam.setLayout(gl_pnlAnam);
		
		JScrollPane spJal = new JScrollPane();
		GroupLayout gl_pnlJal = new GroupLayout(pnlJal);
		gl_pnlJal.setHorizontalGroup(
			gl_pnlJal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlJal.createSequentialGroup()
					.addGap(2)
					.addComponent(spJal, GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
		);
		gl_pnlJal.setVerticalGroup(
			gl_pnlJal.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlJal.createSequentialGroup()
					.addGap(2)
					.addComponent(spJal, GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
		);
		
		tbJal = new JTextArea();
		tbJal.setFont(new Font("Tahoma", Font.PLAIN, 12));
		tbJal.setLineWrap(true);
		tbJal.setWrapStyleWord(true);
		spJal.setViewportView(tbJal);
		pnlJal.setLayout(gl_pnlJal);
		pnlOsmOsm.setLayout(gl_pnlOsmOsm);
		pnlOsm.setLayout(gl_pnlOsm);
		
		JPanel pnlBtn = new JPanel();
		
		JPanel pnlFindShablon = new JPanel();
		pnlFindShablon.setBorder(new TitledBorder(null, "\u041F\u043E\u0438\u0441\u043A \u0448\u0430\u0431\u043B\u043E\u043D\u0430", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout gl_pnlShablon = new GroupLayout(pnlShablon);
		gl_pnlShablon.setHorizontalGroup(
			gl_pnlShablon.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlShablon.createSequentialGroup()
					.addGroup(gl_pnlShablon.createParallelGroup(Alignment.TRAILING)
						.addComponent(pnlFindShablon, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(pnlBtn, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(0))
		);
		gl_pnlShablon.setVerticalGroup(
			gl_pnlShablon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlShablon.createSequentialGroup()
					.addComponent(pnlBtn, GroupLayout.PREFERRED_SIZE, 38, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlFindShablon, GroupLayout.PREFERRED_SIZE, 391, Short.MAX_VALUE))
		);
		
		shablonSearchListener = new ShablonSearchListener();
		
		tbShabSrc = new CustomTextField(true, true, false);
		tbShabSrc.getDocument().addDocumentListener(shablonSearchListener);
		tbShabSrc.setColumns(10);
		
		JButton btnShabSrc = new JButton("...");
		btnShabSrc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shablonform.showShablonForm(tbShabSrc.getText(), lbShabSrc.getSelectedValue());
				syncShablonList(shablonform.getSearchString(), shablonform.getShablon());
				pasteShablon(shablonform.getShablon());
			}
		});
		
		JScrollPane spShabSrc = new JScrollPane();
		lblLastShab = new JLabel("<html>Последний выбранный шаблон: </html>");
		
		GroupLayout gl_pnlFindShablon = new GroupLayout(pnlFindShablon);
		gl_pnlFindShablon.setHorizontalGroup(
			gl_pnlFindShablon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlFindShablon.createSequentialGroup()
					.addComponent(tbShabSrc, GroupLayout.DEFAULT_SIZE, 176, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnShabSrc, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_pnlFindShablon.createSequentialGroup()
					.addComponent(lblLastShab)
					.addContainerGap())
				.addComponent(spShabSrc, GroupLayout.DEFAULT_SIZE, 228, Short.MAX_VALUE)
		);
		gl_pnlFindShablon.setVerticalGroup(
			gl_pnlFindShablon.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlFindShablon.createSequentialGroup()
					.addGroup(gl_pnlFindShablon.createParallelGroup(Alignment.BASELINE)
						.addComponent(tbShabSrc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnShabSrc))
					.addPreferredGap(ComponentPlacement.RELATED, 7, Short.MAX_VALUE)
					.addComponent(lblLastShab)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spShabSrc, GroupLayout.PREFERRED_SIZE, 316, GroupLayout.PREFERRED_SIZE)
					.addGap(228))
		);
		
		lbShabSrc = new ThriftIntegerClassifierList();
		lbShabSrc.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) 
					if (lbShabSrc.getSelectedValue() != null) {
						try {
							pasteShablon(MainForm.tcl.getShOsm(lbShabSrc.getSelectedValue().pcod));
						} catch (KmiacServerException e1) {
							JOptionPane.showMessageDialog(null, "Ошибка загрузка шаблона", "Ошибка", JOptionPane.ERROR_MESSAGE);
						} catch (TException e1) {
							MainForm.conMan.reconnect(e1);
						}
					}
			}
		});
		spShabSrc.setViewportView(lbShabSrc);
		pnlFindShablon.setLayout(gl_pnlFindShablon);
		
		JButton btnSave = new JButton("Сохранить");
		GroupLayout gl_pnlBtn = new GroupLayout(pnlBtn);
		gl_pnlBtn.setHorizontalGroup(
			gl_pnlBtn.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlBtn.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnSave)
					.addContainerGap(147, Short.MAX_VALUE))
		);
		gl_pnlBtn.setVerticalGroup(
			gl_pnlBtn.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlBtn.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnSave)
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		pnlBtn.setLayout(gl_pnlBtn);
		pnlShablon.setLayout(gl_pnlShablon);
		setLayout(groupLayout);
	}

	private class ShablonSearchListener implements DocumentListener {
		Timer timer = new Timer(500, new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updateNow();
			}
		});
		
		@Override
		public void removeUpdate(DocumentEvent e) {
			timer.restart();
		}
		
		@Override
		public void insertUpdate(DocumentEvent e) {
			timer.restart();
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			timer.restart();
		}
		
		public void updateNow() {
			timer.stop();
			loadShablonList();
		}
		
		public void updateNow(String searchString) {
			tbShabSrc.setText(searchString);
			updateNow();
		}
	}
	
	public void onConnect() {
		try {
			lbShabSrc.setData(MainForm.tcl.getShOsmPoiskName(MainForm.authInfo.cspec, MainForm.authInfo.cslu,  null));

		} catch (KmiacServerException e) {
			JOptionPane.showMessageDialog(this, "Ошибка на сервере", "Ошибка", JOptionPane.ERROR_MESSAGE);
		} catch (TException e) {
			MainForm.conMan.reconnect(e);
		}
	}
	private void syncShablonList(String searchString, Shablon shablon) {
		if (shablon != null) {
			shablonSearchListener.updateNow(searchString);
			for (int i = 0; i < lbShabSrc.getData().size(); i++)
				if (lbShabSrc.getData().get(i).pcod == shablon.id)
				{
					lbShabSrc.setSelectedIndex(i);
					break;
				}
		} else {
			lbShabSrc.setSelectedIndex(-1);
		}
	}
	
	private void loadShablonList() {
		try {
			lbShabSrc.setData(MainForm.tcl.getShOsmPoiskName(MainForm.authInfo.cspec, MainForm.authInfo.cslu, (tbShabSrc.getText().length() < 3) ? null : '%' + tbShabSrc.getText() + '%'));
		} catch (KmiacServerException e1) {
			JOptionPane.showMessageDialog(this, "Ошибка загрузки результатов поиска", "Ошибка", JOptionPane.ERROR_MESSAGE);
		} catch (TException e1) {
			MainForm.conMan.reconnect(e1);
		}
	}

	private String getTextOrNull(String str) {
		if (str != null)
			if (str.length() > 0)
				return str;
		
		return null;
	}
	
	private void pasteShablon(Shablon sh) {
		if (sh == null)
			return;
		
		tbJal.setText("");
		tbAnam.setText("");
		tbStat.setText("");
		tbLoc.setText("");
		tbLech.setText("");
		tbZakl.setText("");

		for (ShablonText st : sh.textList) {
			switch (st.grupId) {
			case 1:
				tbJal.setText(st.text);
				break;
			case 2:
				tbAnam.setText(st.text);
				break;
			case 6:
				tbStat.setText(st.text);
				break;
			case 8:
				tbLoc.setText(st.text);
				break;
			case 10:
				tbLech.setText(st.text);
				break;
			case 13:
				tbZakl.setText(st.text);
				break;
			case 12:
				break;
			default:
				break;
			}
		}
		
		lblLastShab.setText(String.format("<html>Последний выбранный шаблон: %s %s</html>", sh.diag.trim(), sh.name));
	}
}
