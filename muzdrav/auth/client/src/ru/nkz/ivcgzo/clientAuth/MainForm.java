package ru.nkz.ivcgzo.clientAuth;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.IClient;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftServerAuth.ThriftServerAuth;
import ru.nkz.ivcgzo.thriftServerAuth.ThriftServerAuth.Client;
import ru.nkz.ivcgzo.thriftServerAuth.UserNotFoundException;

public class MainForm {
	private JFrame frame;
	private JPanel pnlLogin;
	private JTextField tbLogin;
	private JTextField tbPass;
	private JButton btnEnter;
	
	private JPanel pnlSysSelect;
	private JLabel lblFio;
	private JLabel lblLpu;
	private JLabel lblPodr;
	private JList<String> lbxAvailSys;
	private JButton btnLaunch;
	
	private static ConnectionManager conMan;
	private static ThriftServerAuth.Client client; 
	private UserAuthInfo authInfo;
	private IClient plug;
//	private ModulesUpdater modUpd;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					MainForm window = new MainForm();
					window.frame.getInputContext().selectInputMethod(new Locale("ru", "RU"));
					window.frame.setVisible(true);
					
					conMan = new ConnectionManager(window.frame, ThriftServerAuth.Client.class, configuration.thrPort);
					client = (Client) conMan.get(configuration.thrPort);
					conMan.connect();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainForm() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Аутентификация");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainForm.class.getResource("/ru/nkz/ivcgzo/clientAuth/resources/icon_2_32x32.png")));
		frame.setResizable(false);
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		pnlLogin = new JPanel();
		frame.getContentPane().add(pnlLogin, BorderLayout.CENTER);
		JLabel lblLogo = new JLabel();
		lblLogo.setHorizontalAlignment(SwingConstants.LEFT);
		lblLogo.setVerticalAlignment(SwingConstants.TOP);
		lblLogo.setIcon(new ImageIcon(MainForm.class.getResource("/ru/nkz/ivcgzo/clientAuth/resources/kmiacLogo_small.png")));
		
		JLabel lblDesc = new JLabel("<html>\r\n<p style=\"text-align:justify\">Муниципальная информационная система для медицинского и административного персонала, обеспечивающая ведение медицинской истории болезни, формирование аналитических и статистических отчетов. Объединяет лечебно-профилактические учреждения г. Новокузнецка в единое информационное пространство с централизованным хранилищем информации, упрощая обмен данными между ними.</p>\r\n</html>");
		lblDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDesc.setVerticalAlignment(SwingConstants.TOP);
		
		JLabel lblName = new JLabel("МИС \"Инфо МуЗдрав\"");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		
		tbLogin = new CustomTextField();
		tbLogin.setText("log");
		tbLogin.setColumns(10);
		tbLogin.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					btnEnter.doClick();
			}
		});
		
		JLabel lblLogin = new JLabel("Логин");
		
		tbPass = new CustomTextField();
		tbPass.setText("pas");
		tbPass.setColumns(10);
		tbPass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					btnEnter.doClick();
			}
		});
		
		JLabel lblPass = new JLabel("Пароль");
		
		btnEnter = new JButton("Вход");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					authInfo = client.auth(tbLogin.getText(), tbPass.getText());
					conMan.createPluginLoader(authInfo);
					showSelectionPane();
				} catch (UserNotFoundException e1) {
					JOptionPane.showMessageDialog(frame, "Пользователя с таким логином и паролем не существует");
					tbLogin.selectAll();
					tbLogin.requestFocusInWindow();
				} catch (TException e1) {
					conMan.reconnect(e1);
				}
			}
		});
		
		GroupLayout gl_pnlLogin = new GroupLayout(pnlLogin);
		gl_pnlLogin.setHorizontalGroup(
			gl_pnlLogin.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlLogin.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlLogin.createParallelGroup(Alignment.LEADING)
						.addComponent(btnEnter, GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
						.addComponent(lblName, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 572, Short.MAX_VALUE)
						.addGroup(Alignment.TRAILING, gl_pnlLogin.createSequentialGroup()
							.addComponent(lblLogo, GroupLayout.PREFERRED_SIZE, 192, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblDesc, GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE))
						.addGroup(Alignment.TRAILING, gl_pnlLogin.createSequentialGroup()
							.addComponent(lblLogin, GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tbLogin, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)
							.addGap(16)
							.addComponent(lblPass, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tbPass, GroupLayout.PREFERRED_SIZE, 172, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap())
		);
		gl_pnlLogin.setVerticalGroup(
			gl_pnlLogin.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlLogin.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblName, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(gl_pnlLogin.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblDesc, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 192, Short.MAX_VALUE)
						.addComponent(lblLogo, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addGap(18)
					.addGroup(gl_pnlLogin.createParallelGroup(Alignment.BASELINE)
						.addComponent(tbLogin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblLogin)
						.addComponent(tbPass, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblPass))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(btnEnter)
						.addContainerGap())
		);
		pnlLogin.setLayout(gl_pnlLogin);

		pnlSysSelect = new JPanel();
//		frame.getContentPane().add(pnlSysSelect, BorderLayout.CENTER);
		
		JLabel lblEnterAs = new JLabel("Вы вошли как:");
		lblEnterAs.setFont(new Font(lblEnterAs.getFont().getFontName(), lblEnterAs.getFont().getStyle(), 16));
		
		lblFio = new JLabel("ФИО:");
		
		lblLpu = new JLabel("ЛПУ:");
		
		lblPodr = new JLabel("Подр:");
		
		JLabel lblAvailSys = new JLabel("Вам доступны следующие модули:");
		lblAvailSys.setFont(new Font(lblAvailSys.getFont().getFontName(), lblAvailSys.getFont().getStyle(), 16));
		
		btnLaunch = new JButton("Запуск");
		btnLaunch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (lbxAvailSys.getModel().getSize() == 0)
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				else
					try {
						plug = conMan.getPluginLoader().loadPluginByIndex(lbxAvailSys.getSelectedIndex());
						conMan.setClient(plug);
						plug.showNormal();
						conMan.connect();
						frame.setVisible(false);
					} catch (Exception e1) {
						e1.printStackTrace();
						JOptionPane.showMessageDialog(frame, e1.getLocalizedMessage(), "Ошибка загрузки модуля", JOptionPane.ERROR_MESSAGE);
					}
			}
		});
		
		JScrollPane scrollPane = new JScrollPane();
		
		GroupLayout gl_pnlSysSelect = new GroupLayout(pnlSysSelect);
		gl_pnlSysSelect.setHorizontalGroup(
			gl_pnlSysSelect.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_pnlSysSelect.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlSysSelect.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblFio, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
						.addComponent(lblLpu, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
						.addComponent(lblPodr, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
						.addComponent(btnLaunch, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
						.addGroup(gl_pnlSysSelect.createSequentialGroup()
							.addGap(24)
							.addComponent(lblEnterAs, GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE))
						.addGroup(gl_pnlSysSelect.createSequentialGroup()
							.addGap(24)
							.addComponent(lblAvailSys, GroupLayout.DEFAULT_SIZE, 552, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_pnlSysSelect.setVerticalGroup(
			gl_pnlSysSelect.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_pnlSysSelect.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblEnterAs)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblFio)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblLpu)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblPodr)
					.addGap(18)
					.addComponent(lblAvailSys, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLaunch)
					.addContainerGap())
		);
		
		lbxAvailSys = new JList<>();
		scrollPane.setViewportView(lbxAvailSys);
		pnlSysSelect.setLayout(gl_pnlSysSelect);
		
		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (conMan != null)
					conMan.remove();
				
				super.windowClosing(e);
			}
		});
		
		frame.setLocationRelativeTo(null);
	}
	
	private void showSelectionPane() {
		frame.setTitle("Выбор модуля");
		lblFio.setText(String.format("%s, %s", authInfo.getName(), authInfo.getCdol_name()));
		lblLpu.setText(authInfo.getClpu_name());
		lblPodr.setText(String.format("%s, %s", authInfo.getCpodr_name(), authInfo.getCslu_name()));
		
		frame.getContentPane().remove(pnlLogin);
		frame.getContentPane().add(pnlSysSelect, BorderLayout.CENTER);
		frame.getContentPane().validate();
		
		showPluginList();
	}
	
	private void showPluginList() {
//			modUpd = new ModulesUpdater(conMan);
			while (true) {
				try {
					
//					TODO На этапе разработки апдейтер будет только мешать
//					modUpd.checkAndUpdate(authInfo.pdost);
					conMan.getPluginLoader().loadPluginList(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath());
					break;
				} catch (Exception e) {
					e.printStackTrace();
					if (JOptionPane.showConfirmDialog(frame, String.format("%s%sПовторить операцию?", e.getMessage(), System.lineSeparator()), "Ошибка обновления модулей", JOptionPane.ERROR_MESSAGE, JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
						break;
				}
			}
			
			lbxAvailSys.setModel(new DefaultListModel<String>() {
				private static final long serialVersionUID = 7809752864081111668L;
				
				@Override
				public int getSize() {
					if (conMan.getPluginLoader() != null)
						return conMan.getPluginLoader().getPluginList().size();
					else
						return 0;
				}
				
				@Override
				public String getElementAt(int index) {
					return conMan.getPluginLoader().getPluginList().get(index).getName();
				}
			});

		if (lbxAvailSys.getModel().getSize() == 0)
			btnLaunch.setText("Выход");
		else {
			lbxAvailSys.setSelectedIndex(0);
			lbxAvailSys.requestFocusInWindow();
			
			lbxAvailSys.addKeyListener(new KeyAdapter() {
				@Override
				public void keyPressed(KeyEvent e) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER)
						btnLaunch.doClick();
				}
			});
			
			lbxAvailSys.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2)
						btnLaunch.doClick();
				}
			});
		}
	}
}
