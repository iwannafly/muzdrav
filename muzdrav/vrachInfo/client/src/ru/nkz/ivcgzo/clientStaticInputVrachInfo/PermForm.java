package ru.nkz.ivcgzo.clientStaticInputVrachInfo;

import javax.swing.JDialog;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComponent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.UIManager;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftServerStaticInputVrachInfo.MestoRab;
import ru.nkz.ivcgzo.thriftServerStaticInputVrachInfo.VrachInfo;
import ru.nkz.ivcgzo.thriftServerStaticInputVrachInfoAdmin.ThriftServerStaticInputVrachInfoAdmin;

import java.awt.Component;
import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import java.awt.Toolkit;

public class PermForm extends JDialog {
	private static final long serialVersionUID = 5320450245161207797L;
	private ThriftServerStaticInputVrachInfoAdmin.Client client;
	private ConnectionManager conMan;
	private VrachInfo vInf;
	private MestoRab mRab;
	private JTextField tbLog;
	private JTextField tbPass;
	private JPanel gbPerm;
	private JPanel pnlPermChb;
	private JPanel pnlPermBtn;
	private JButton btnPassDel;
	private boolean opened;

	/**
	 * Create the dialog.
	 */
	public PermForm() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(PermForm.class.getResource("/ru/nkz/ivcgzo/clientStaticInputVrachInfo/resources/icon_2_32x32.png")));
		setModalityType(ModalityType.APPLICATION_MODAL);
		setBounds(100, 100, 778, 300);
		
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
						.addComponent(gbPerm, GroupLayout.PREFERRED_SIZE, 750, Short.MAX_VALUE)
						.addComponent(gbPass, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(gbPass, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(gbPerm, GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		pnlPermChb = new JPanel();
		
		pnlPermBtn = new JPanel();
		GroupLayout gl_gbPerm = new GroupLayout(gbPerm);
		gl_gbPerm.setHorizontalGroup(
			gl_gbPerm.createParallelGroup(Alignment.LEADING)
				.addComponent(pnlPermChb, GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE)
				.addComponent(pnlPermBtn, GroupLayout.DEFAULT_SIZE, 734, Short.MAX_VALUE)
		);
		gl_gbPerm.setVerticalGroup(
			gl_gbPerm.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_gbPerm.createSequentialGroup()
					.addContainerGap()
					.addComponent(pnlPermChb, GroupLayout.DEFAULT_SIZE, 105, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(pnlPermBtn, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
		);
		
		JButton btnSetPerm = new JButton("Изменить доступ");
		btnSetPerm.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					client.setPermissions(mRab.pcod, mRab.clpu, mRab.cpodr, getPermissions());
					dispatchEvent(new WindowEvent(PermForm.this, WindowEvent.WINDOW_CLOSING));
				} catch (TTransportException e1) {
					conMan.reconnect(e1);
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		GroupLayout gl_pnlPermBtn = new GroupLayout(pnlPermBtn);
		gl_pnlPermBtn.setHorizontalGroup(
			gl_pnlPermBtn.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlPermBtn.createSequentialGroup()
					.addContainerGap(535, Short.MAX_VALUE)
					.addComponent(btnSetPerm, GroupLayout.PREFERRED_SIZE, 169, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_pnlPermBtn.setVerticalGroup(
			gl_pnlPermBtn.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlPermBtn.createSequentialGroup()
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addComponent(btnSetPerm)
					.addContainerGap())
		);
		pnlPermBtn.setLayout(gl_pnlPermBtn);
		pnlPermChb.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		TaggedJCheckBox chbVrachinfo = new TaggedJCheckBox("Информация о персонале больницы", 1);
		pnlPermChb.add(chbVrachinfo);
		
		TaggedJCheckBox chbStationar = new TaggedJCheckBox("Стационар", 2);
		pnlPermChb.add(chbStationar);
				
		TaggedJCheckBox chbOsm = new TaggedJCheckBox("Врач амбулаторного приема", 3);
		pnlPermChb.add(chbOsm);
				
		gbPerm.setLayout(gl_gbPerm);
		
		tbLog = new JTextField();
		tbLog.setColumns(10);
		
		JLabel lblLog = new JLabel("Логин");
		
		JButton btnPassReq = new JButton("Запросить пароль");
		btnPassReq.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					tbPass.setText(client.setPassword(mRab.pcod, mRab.clpu, mRab.cpodr, tbLog.getText()));
					setDelPassPermEnabled(true);
				} catch (TTransportException e1) {
					conMan.reconnect(e1);
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		tbPass = new JTextField();
		tbPass.setEditable(false);
		tbPass.setColumns(10);
		
		JLabel lblPass = new JLabel("Пароль");
		
		btnPassDel = new JButton("Снять пароль");
		btnPassDel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					client.remPassword(mRab.pcod, mRab.clpu, mRab.cpodr);
					dispatchEvent(new WindowEvent(PermForm.this, WindowEvent.WINDOW_CLOSING));
				} catch (TTransportException e1) {
					conMan.reconnect(e1);
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
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
					.addComponent(btnPassDel, GroupLayout.PREFERRED_SIZE, 163, GroupLayout.PREFERRED_SIZE)
					.addGap(12))
		);
		gl_gbPass.setVerticalGroup(
			gl_gbPass.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_gbPass.createParallelGroup(Alignment.BASELINE)
					.addComponent(lblLog)
					.addComponent(tbLog, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(lblPass)
					.addComponent(tbPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addComponent(btnPassReq)
					.addComponent(btnPassDel))
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
						setTitle(String.format("Установка прав пользователя %s %s %s, код подразделения %d", vInf.fam, vInf.im, vInf.ot, mRab.cpodr));
						tbLog.setText(client.getLogin(mRab.pcod, mRab.clpu, mRab.cpodr));
						setPermissions(client.getPermissions(mRab.pcod, mRab.clpu, mRab.cpodr));
						setDelPassPermEnabled((tbLog.getText().length() == 0) ? false : true);
					} catch (TTransportException e1) {
						conMan.reconnect(e1);
					} catch (TException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
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
	
	public void setClient(ThriftServerStaticInputVrachInfoAdmin.Client client) {
		this.client = client;
	}
	
	public void setConnectionManager(ConnectionManager conMan) {
		this.conMan = conMan;
	}
	
	public void showWindow(VrachInfo vi, MestoRab mr) {
		vInf = vi;
		mRab = mr;
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
	}
	
	private String getPermissions() {
		char[] perm = new char[128];

		perm[0] = '1';
		for (Component cmp : pnlPermChb.getComponents()) {
			TaggedJCheckBox chb = (TaggedJCheckBox) cmp;
			
			perm[chb.getTag()] = (chb.isSelected()) ? '1' : '0';
		}
		
		return new String(perm).replace('\0', ' ').trim();
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
	}
}

class TaggedJCheckBox extends JCheckBox {
	private static final long serialVersionUID = -3972936204425398459L;
	private final int tag;
	
	TaggedJCheckBox(String text, int tag) {
		super(text);
		this.tag = tag;
	}
	
	public int getTag() {
		return tag;
	}
}
