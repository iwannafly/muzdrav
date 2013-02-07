package ru.nkz.ivcgzo.clientAdmin;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractAction;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.thriftServerAdmin.MestoRab;
import ru.nkz.ivcgzo.thriftServerAdmin.UserIdPassword;
import ru.nkz.ivcgzo.thriftServerAdmin.VrachInfo;

public class PermForm extends JDialog {
	private static final long serialVersionUID = 5320450245161207797L;
	
	private static final String frameTitle = "Установка прав пользователя";
	private VrachInfo vInf;
	private MestoRab mRab;
	private CustomTextField tbLog;
	private CustomTextField tbPass;
	private JPanel gbPerm;
	private JPanel pnlPermChb;
	private JPanel pnlPermBtn;
	private JButton btnPassDel;
	private JButton btnPassReq;
	private boolean opened;
	private boolean ownRecord;
	private CsluPdost csluPdost;
	private boolean hasLog;

	/**
	 * Create the dialog.
	 */
	public PermForm() {
		setTitle("Доступ к компонентам системы");
		setIconImage(Toolkit.getDefaultToolkit().getImage(PermForm.class.getResource("/ru/nkz/ivcgzo/clientAdmin/resources/icon_2_32x32.png")));
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 778, 419);
		
		JPanel gbPass = new JPanel();
		gbPass.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Управление паролями к системе", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		gbPerm = new JPanel();
		gbPerm.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Управление доступом к приложениям", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(gbPass, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE)
						.addComponent(gbPerm, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 750, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(gbPass, GroupLayout.PREFERRED_SIZE, 78, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(gbPerm, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		pnlPermChb = new JPanel();
		
		pnlPermBtn = new JPanel();
		GroupLayout gl_gbPerm = new GroupLayout(gbPerm);
		gl_gbPerm.setHorizontalGroup(
			gl_gbPerm.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_gbPerm.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_gbPerm.createParallelGroup(Alignment.LEADING)
						.addComponent(pnlPermBtn, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 714, Short.MAX_VALUE)
						.addComponent(pnlPermChb, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 714, Short.MAX_VALUE))
					.addContainerGap())
		);
		gl_gbPerm.setVerticalGroup(
			gl_gbPerm.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_gbPerm.createSequentialGroup()
					.addContainerGap()
					.addComponent(pnlPermChb, GroupLayout.DEFAULT_SIZE, 185, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(pnlPermBtn, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		
		JButton btnSetPerm = new JButton("Изменить доступ");
		btnSetPerm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					MainForm.tcl.setPermissions(mRab.user_id, getPermissions());
					dispatchEvent(new WindowEvent(PermForm.this, WindowEvent.WINDOW_CLOSING));
				} catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				}
			}
		});
		GroupLayout gl_pnlPermBtn = new GroupLayout(pnlPermBtn);
		gl_pnlPermBtn.setHorizontalGroup(
			gl_pnlPermBtn.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlPermBtn.createSequentialGroup()
					.addContainerGap(545, Short.MAX_VALUE)
					.addComponent(btnSetPerm, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_pnlPermBtn.setVerticalGroup(
			gl_pnlPermBtn.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlPermBtn.createSequentialGroup()
					.addContainerGap()
					.addComponent(btnSetPerm, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
					.addContainerGap())
		);
		pnlPermBtn.setLayout(gl_pnlPermBtn);
		pnlPermChb.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		TaggedJCheckBox chbVrachinfo = new TaggedJCheckBox("Администрирование", 1, CsluPdost.CsluAll);
		pnlPermChb.add(chbVrachinfo);
		
		TaggedJCheckBox chbStationar = new TaggedJCheckBox("Стационар", 2, CsluPdost.CsluAll);
		pnlPermChb.add(chbStationar);
				
		TaggedJCheckBox chbOsm = new TaggedJCheckBox("Врач амбулаторного приема", 3, CsluPdost.CsluAll);
		pnlPermChb.add(chbOsm);
				
		TaggedJCheckBox chbLds = new TaggedJCheckBox("Параотделение", 4, CsluPdost.CsluAll);
		pnlPermChb.add(chbLds);

		TaggedJCheckBox chbRegPat = new TaggedJCheckBox("Регистрация пациентов больницы", 5, CsluPdost.CsluAll);
		pnlPermChb.add(chbRegPat);

		TaggedJCheckBox chbMss = new TaggedJCheckBox("Медицинское свидетельство о смерти", 6, CsluPdost.CsluAll);
		pnlPermChb.add(chbMss);

		TaggedJCheckBox chbGenTal = new TaggedJCheckBox("Формирование талонов", 8, CsluPdost.CsluAll);
		pnlPermChb.add(chbGenTal);

		TaggedJCheckBox chbOutInf = new TaggedJCheckBox("Выходная информация системы", 9, CsluPdost.CsluAll);
		pnlPermChb.add(chbOutInf);

		TaggedJCheckBox chbRebInv = new TaggedJCheckBox("Карта ребенка-инвалида", 11, CsluPdost.CsluAll);
		pnlPermChb.add(chbRebInv);

		TaggedJCheckBox chbGenReestr = new TaggedJCheckBox("Формирование реестров", 12, CsluPdost.CsluAll);
		pnlPermChb.add(chbGenReestr);

		TaggedJCheckBox chbVgr = new TaggedJCheckBox("Выгрузки информации", 14, CsluPdost.CsluAll);
		pnlPermChb.add(chbVgr);

		TaggedJCheckBox chbDisp = new TaggedJCheckBox("Диспансеризация", 16, CsluPdost.CsluAll);
		pnlPermChb.add(chbDisp);

		gbPerm.setLayout(gl_gbPerm);
		
		tbLog = new CustomTextField(true, true, false);
		tbLog.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				check();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				check();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				check();
			}
			
			private void check() {
				btnPassReq.setEnabled(!tbLog.isEmpty());
			}
		});
		tbLog.setColumns(10);
		
		JLabel lblLog = new JLabel("Логин");
		
		btnPassReq = new JButton("Запросить пароль");
		btnPassReq.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (hasLog)
						if (JOptionPane.showConfirmDialog(PermForm.this, "На учетную запись уже заведен пароль. Изменить его?", "Подтверждение", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) != JOptionPane.YES_OPTION)
							return;
					
					UserIdPassword idPass = MainForm.tcl.setPassword(mRab.id, tbLog.getText()); 
					tbPass.setText(idPass.getPassword());
					mRab.setUser_id(idPass.getUser_id());
					setDelPassPermEnabled(true);
					hasLog = false;
				} catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				}
			}
		});
		
		tbPass = new CustomTextField(true, true, false);
		tbPass.setEditable(false);
		tbPass.setColumns(10);
		
		JLabel lblPass = new JLabel("Пароль");
		
		btnPassDel = new JButton("Снять пароль");
		btnPassDel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					if (JOptionPane.showConfirmDialog(PermForm.this, "Снятие пароля приведет к удалению учетной записи. Продолжить?", "Подтверждение", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
						MainForm.tcl.remPassword(mRab.user_id);
						dispatchEvent(new WindowEvent(PermForm.this, WindowEvent.WINDOW_CLOSING));
					}
				} catch (TException e1) {
					e1.printStackTrace();
					MainForm.conMan.reconnect(e1);
				}
			}
		});
		
		GroupLayout gl_gbPass = new GroupLayout(gbPass);
		gl_gbPass.setHorizontalGroup(
			gl_gbPass.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_gbPass.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblLog, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tbLog, GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(lblPass, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(tbPass, GroupLayout.DEFAULT_SIZE, 108, Short.MAX_VALUE)
					.addGap(18)
					.addComponent(btnPassReq, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addComponent(btnPassDel, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_gbPass.setVerticalGroup(
			gl_gbPass.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_gbPass.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_gbPass.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnPassDel, GroupLayout.DEFAULT_SIZE, 23, Short.MAX_VALUE)
						.addComponent(btnPassReq, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(lblPass)
						.addGroup(gl_gbPass.createSequentialGroup()
							.addGap(3)
							.addComponent(tbLog, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addComponent(lblLog))
					.addGap(12))
				.addGroup(gl_gbPass.createSequentialGroup()
					.addGap(14)
					.addComponent(tbPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(12, Short.MAX_VALUE))
		);
		gbPass.setLayout(gl_gbPass);
		getContentPane().setLayout(groupLayout);
		
		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "PermFormEscapeClose");
		getRootPane().getActionMap().put("PermFormEscapeClose", new AbstractAction() {
			private static final long serialVersionUID = -6353854542797400850L;

			@Override
			public void actionPerformed(ActionEvent e) {
				dispatchEvent(new WindowEvent(PermForm.this, WindowEvent.WINDOW_CLOSING));
			}
		});
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowActivated(WindowEvent e) {
				super.windowActivated(e);
				
				if (!opened) {
					opened = true;
					try {
						tbPass.setText("");
						setTitle(String.format("%s %s %s %s, код подразделения %d", frameTitle, vInf.fam, vInf.im, vInf.ot, mRab.cpodr));
						tbLog.setText(MainForm.tcl.getLogin(mRab.user_id));
						hasLog = !tbLog.isEmpty();
						setPermissions(MainForm.tcl.getPermissions(mRab.user_id));
						setDelPassPermEnabled((tbLog.getText().length() == 0) ? false : true);
					} catch (TException e1) {
						e1.printStackTrace();
						MainForm.conMan.reconnect(e1);
					}
				}
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				opened = false;
				tbPass.setText("");
				super.windowClosing(e);
			}
		});
	}
	
	public void showWindow(VrachInfo vi, MestoRab mr, boolean ownRecord) {
		vInf = vi;
		mRab = mr;
		this.ownRecord = ownRecord;
		csluPdost = new CsluPdost(mRab.getCslu());
		
		setVisible(true);
	}
	
	private void setPermissions(String perm) {
		for (Component cmp : pnlPermChb.getComponents()) {
			TaggedJCheckBox chb = (TaggedJCheckBox) cmp;
			
			if (chb.getTag() < perm.length())
				chb.setSelected((perm.charAt(chb.getTag()) == '0') ? false : true);
			else
				chb.setSelected(false);
		}

		if (ownRecord)
			((TaggedJCheckBox) pnlPermChb.getComponents()[0]).setSelected(true);
}
	
	private String getPermissions() {
		char[] perm = new char[128];

		perm[0] = '1';
		perm[7] = '1';
		for (Component cmp : pnlPermChb.getComponents()) {
			TaggedJCheckBox chb = (TaggedJCheckBox) cmp;
			
			perm[chb.getTag()] = (chb.isSelected() & chb.isEnabled()) ? '1' : '0';
		}
		
		if (ownRecord)
			perm[1] = '2';
		
		return new String(perm).replace('\0', ' ').trim().replace(' ', '0');
	}
	
	/**
	 * Нельзя рекурсивно бегать по контейнерам и выставлять компонентам свойство
	 * setEnabled. В свинге невозможно определить, составной это компонент или нет
	 * (JTextField не составной, JComboBox составной: содержит кнопку и текстовое поле).
	 * Когда рекурсия дойдет до составного компонента и начнет менять setEnabled
	 * его... компонентов, то выполнение программы может поломаться. Подробности
	 * по ссылкам ниже.
	 * @see <a href="http://stackoverflow.com/questions/305527/how-to-disable-a-container-and-its-children-in-swing">one</a> 
	 * @see <a href="http://java.net/jira/browse/SWINGX-275">two</a> 
	 */
	private void setDelPassPermEnabled(boolean enabled) {
		btnPassDel.setEnabled(enabled);
		gbPerm.setEnabled(enabled);
		for (Component cmp : pnlPermChb.getComponents()) {
			cmp.setEnabled(enabled);
		}
		for (Component cmp : pnlPermBtn.getComponents()) {
			cmp.setEnabled(enabled);
		}
		
		if (enabled) {
			for (Component cmp : pnlPermChb.getComponents()) {
				TaggedJCheckBox chb = (TaggedJCheckBox) cmp;
				
				chb.setEnabled(csluPdost.check(chb.getSlu()));
				if (!chb.isEnabled())
					chb.setSelected(false);
			}
		}
		
		if (ownRecord)
			pnlPermChb.getComponents()[0].setEnabled(false);
	}
}

class TaggedJCheckBox extends JCheckBox {
	private static final long serialVersionUID = -3972936204425398459L;
	private final int tag;
	private final int slu;
	
	TaggedJCheckBox(String text, int tag, int slu) {
		super(text);
		
		this.tag = tag;
		this.slu = slu;
	}
	
	public int getTag() {
		return tag;
	}
	
	public int getSlu() {
		return slu;
	}
}

class CsluPdost {
	public static final int CsluNone = 0;
	public static final int CsluStat = 1;
	public static final int CsluPol = 2;
	public static final int CsluLds = 4;
	public static final int CsluAll = -1;
	private int curCslu;
	
	public CsluPdost(int curCslu) {
		this.curCslu = (int) Math.pow(2, curCslu - 1);
	}
	
	public boolean check(int cslu) {
		return (cslu & curCslu) == curCslu;
	}
}