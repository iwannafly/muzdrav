package ru.nkz.ivcgzo.clientAuth;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
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
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.IClient;
import ru.nkz.ivcgzo.thriftServerAuth.ThriftServerAuth;
import ru.nkz.ivcgzo.thriftServerAuth.ThriftServerAuth.Client;
import ru.nkz.ivcgzo.thriftServerAuth.UserAuthInfo;
import ru.nkz.ivcgzo.thriftServerAuth.UserNotFoundException;

public class MainForm {
	private static JFrame instance;
	
	private JFrame frame;
	private JPanel pnlLogin;
	private JTextField tbLogin;
	private JTextField tbPass;
	
	private JPanel pnlSysSelect;
	private JLabel lblFio;
	private JLabel lblLpu;
	private JLabel lblPodr;
	private JList<String> lbxAvailSys;
	private JButton btnLaunch;
	
	private static ConnectionManager conMan;
	private static ThriftServerAuth.Client client; 
	private IClient plug;
	private PluginLoader pldr;

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
					
					conMan = new ConnectionManager(instance, ThriftServerAuth.Client.class, configuration.thrPort);
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
		instance = frame;
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("Аутентификация");
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(MainForm.class.getResource("/ru/nkz/ivcgzo/clientAuth/resources/icon_2_32x32.png")));
		frame.setResizable(false);
		frame.setBounds(100, 100, 600, 378);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		pnlLogin = new JPanel();
		frame.getContentPane().add(pnlLogin, BorderLayout.CENTER);
		JLabel lblLogo = new JLabel("New label");
		lblLogo.setHorizontalAlignment(SwingConstants.LEFT);
		lblLogo.setVerticalAlignment(SwingConstants.TOP);
		lblLogo.setIcon(new ImageIcon(MainForm.class.getResource("/ru/nkz/ivcgzo/clientAuth/resources/kmiacLogo_small.png")));
		
		JLabel lblDesc = new JLabel("<html>\r\n<p style=\"text-align:justify\">Муниципальная информационная система для медицинского и административного персонала, обеспечивающая ведение медицинской истории болезни, формирование аналитических и статистических отчетов. Объединяет лечебно-профилактические учреждения г. Новокузнецка в единое информационное пространство с централизованным хранилищем информации, упрощая обмен данными между ними.</p>\r\n</html>");
		lblDesc.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDesc.setVerticalAlignment(SwingConstants.TOP);
		
		JLabel lblName = new JLabel("МИС \"Инфо МуЗдрав\"");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 24));
		lblName.setHorizontalAlignment(SwingConstants.CENTER);
		
		tbLogin = new JTextField();
		tbLogin.setText("log");
		tbLogin.setColumns(10);
		
		JLabel lblLogin = new JLabel("Логин");
		
		tbPass = new JTextField();
		tbPass.setText("pas");
		tbPass.setColumns(10);
		
		JLabel lblPass = new JLabel("Пароль");
		
		JButton btnEnter = new JButton("Вход");
		btnEnter.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					showSelectionPane(client.auth(tbLogin.getText(), tbPass.getText()));
				} catch (UserNotFoundException e1) {
					JOptionPane.showMessageDialog(frame, "Пользователя с таким логином и паролем не существует");
					tbLogin.selectAll();
					tbLogin.requestFocus();
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
		lblEnterAs.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		lblFio = new JLabel("ФИО:");
		
		lblLpu = new JLabel("ЛПУ:");
		
		lblPodr = new JLabel("Подр:");
		
		JLabel lblAvailSys = new JLabel("Вам доступны следующие модули:");
		lblAvailSys.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		lbxAvailSys = new JList<>();
		
		btnLaunch = new JButton("Запуск");
		btnLaunch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (lbxAvailSys.getModel().getSize() == 0)
					frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
				else
					try {
						plug = pldr.loadPlugin(lbxAvailSys.getSelectedIndex());
						frame.setVisible(false);
						conMan.setClient(plug);
						conMan.connect();
					} catch (MalformedURLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (ClassNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (NoSuchMethodException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (SecurityException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InstantiationException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalAccessException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (IllegalArgumentException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (InvocationTargetException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					} catch (TException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
			}
		});
		
		GroupLayout gl_pnlSysSelect = new GroupLayout(pnlSysSelect);
		gl_pnlSysSelect.setHorizontalGroup(
			gl_pnlSysSelect.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_pnlSysSelect.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_pnlSysSelect.createParallelGroup(Alignment.TRAILING)
						.addComponent(lbxAvailSys, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
						.addComponent(lblEnterAs, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
						.addComponent(lblFio, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
						.addComponent(lblLpu, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
						.addComponent(lblPodr, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
						.addComponent(lblAvailSys, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE)
						.addComponent(btnLaunch, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 574, Short.MAX_VALUE))
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
					.addComponent(lbxAvailSys, GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnLaunch)
					.addContainerGap())
		);
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
	
	public static JFrame getInstance() {
		return instance;
	}
	
	private void showSelectionPane(UserAuthInfo ui) {
		frame.setTitle("Выбор модуля");
		lblFio.setText(String.format("%s %s", lblFio.getText(), ui.name));
		lblLpu.setText(String.format("%s %d", lblLpu.getText(), ui.clpu));
		lblPodr.setText(String.format("%s %d", lblPodr.getText(), ui.cpodr));
		
		frame.getContentPane().remove(pnlLogin);
		frame.getContentPane().add(pnlSysSelect, BorderLayout.CENTER);
		frame.getContentPane().validate();
		
		showPluginList(ui.pdost);
	}
	
	private void showPluginList(String pdost) {
		try {
			pldr = new PluginLoader(conMan, pdost);
			
			lbxAvailSys.setModel(new DefaultListModel<String>() {
				private static final long serialVersionUID = 7809752864081111668L;
				
				@Override
				public int getSize() {
					if (pldr != null)
						return pldr.getPluginList().size();
					else
						return 0;
				}
				
				@Override
				public String getElementAt(int index) {
					return pldr.getPluginList().get(index).getName();
				}
			});
		} catch (java.io.FileNotFoundException e1) {
			btnLaunch.setText("Выход");
			JOptionPane.showMessageDialog(frame, e1.getMessage(), "Ошибка загрузки модуля", JOptionPane.ERROR_MESSAGE);
		}

		if (lbxAvailSys.getModel().getSize() == 0)
			btnLaunch.setText("Выход");
		else {
			lbxAvailSys.setSelectedIndex(0);
			
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
