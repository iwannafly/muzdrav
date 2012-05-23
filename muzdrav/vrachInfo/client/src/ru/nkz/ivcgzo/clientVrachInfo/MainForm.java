package ru.nkz.ivcgzo.clientVrachInfo;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TTransportException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTableItemChangeEvent;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTableItemChangeEventListener;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftServerVrachInfo.MestoRab;
import ru.nkz.ivcgzo.thriftServerVrachInfo.VrachInfo;
import ru.nkz.ivcgzo.thriftServerVrachInfo.ThriftServerVrachInfo;

public class MainForm extends Client {
	private final boolean adminMode;
	private final UserAuthInfo authInfo;
	private ThriftServerVrachInfo.Client tcl;
	private JFrame frame;
	private CustomTable<VrachInfo, VrachInfo._Fields> tblVrach;
	private CustomTable<MestoRab, MestoRab._Fields> tblMrab;
	private PermForm permForm;

	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(conMan, authInfo, lncPrm);
		adminMode = lncPrm == 2;
		this.authInfo = authInfo;
		initialize();
		conMan.add(ThriftServerVrachInfo.Client.class, configuration.thrPort);
		conMan.setLocalForm(frame);
		permForm.setConnectionManager(conMan);
		frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(PermForm.class.getResource("/ru/nkz/ivcgzo/clientVrachInfo/resources/icon_2_32x32.png")));
		frame.setTitle(configuration.appName);
		frame.setBounds(100, 100, 600, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JScrollPane spVrach = new JScrollPane();
		tblVrach = new  CustomTable<>(true, true, VrachInfo.class, 1, "Фамилия", 2, "Имя", 3, "Отчество", 4, "Пол", 5, "Дата рождения", 6, "Образование", 7, "СНИЛС", 8, "Диплом");
		tblVrach.setDateField(4);
		tblVrach.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent arg0) {
				try {
					if (!arg0.getValueIsAdjusting()) {
						if (tblVrach.getSelectedItem() != null)
							tblMrab.setData(tcl.GetMrabList(tblVrach.getSelectedItem().pcod));
					}
				} catch (TException e) {
					e.printStackTrace();
				}			
			}
		});
		tblVrach.registerDeleteSelectedRowListener(new CustomTableItemChangeEventListener<VrachInfo>() {
			@Override
			public boolean doAction(CustomTableItemChangeEvent<VrachInfo> event) {
				try {
					tcl.DelVrach(event.getItem().pcod);
//					client.DelVrach(-1);
					return true;
				} catch (TTransportException e) {
					conMan.reconnect(e);
					return false;
				} catch (Exception e) {
					return false;
				}
			}
		});
		tblVrach.registerUpdateSelectedRowListener(new CustomTableItemChangeEventListener<VrachInfo>() {
			@Override
			public boolean doAction(CustomTableItemChangeEvent<VrachInfo> event) {
				try {
					tcl.UpdVrach(event.getItem());
					return true;
				} catch (TTransportException e) {
					conMan.reconnect(e);
					return false;
				} catch (Exception e) {
					return false;
				}
			}
		});
		tblVrach.registerAddRowListener(new CustomTableItemChangeEventListener<VrachInfo>() {
			@Override
			public boolean doAction(CustomTableItemChangeEvent<VrachInfo> event) {
				try {
					event.getItem().setPcod(tcl.AddVrach(event.getItem()));
					return true;
				} catch (TTransportException e) {
					conMan.reconnect(e);
					return false;
				} catch (Exception e) {
					return false;
				}
			}
		});
		tblVrach.setFillsViewportHeight(true);
		
		JButton btnVrAdd = new JButton();
		btnVrAdd.setIcon(new ImageIcon(PermForm.class.getResource("/ru/nkz/ivcgzo/clientVrachInfo/resources/1331789242_Add.png")));
		btnVrAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tblVrach.requestFocusInWindow();
				tblVrach.addItem();
			}
		});
		btnVrAdd.setEnabled(tblVrach.isEditable());
		
		JButton btnVrDel = new JButton();
		btnVrDel.setIcon(new ImageIcon(PermForm.class.getResource("/ru/nkz/ivcgzo/clientVrachInfo/resources/1331789259_Delete.png")));
		btnVrDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tblVrach.deleteSelectedRow();
			}
		});
		btnVrDel.setEnabled(tblVrach.isEditable());
		
		JScrollPane spMrab = new JScrollPane();
		tblMrab = new CustomTable<>(true, true, MestoRab.class, 3, "Вид службы", 4, "Подразделение", 5, "Должность", 6, "Увольнение", 7, "Медперсонал");
		tblMrab.setDateField(3);
//		tblMrab.sortColumns(4, 3, 2, 1, 0);
		tblMrab.registerDeleteSelectedRowListener(new CustomTableItemChangeEventListener<MestoRab>() {
			@Override
			public boolean doAction(CustomTableItemChangeEvent<MestoRab> event) {
				try {
					tcl.DelMrab(event.getItem().id);
//					client.DelMrab(-1);
					return true;
				} catch (TTransportException e) {
					conMan.reconnect(e);
					return false;
				} catch (Exception e) {
					return false;
				}
			}
		});
		tblMrab.registerUpdateSelectedRowListener(new CustomTableItemChangeEventListener<MestoRab>() {
			@Override
			public boolean doAction(CustomTableItemChangeEvent<MestoRab> event) {
				try {
					tcl.UpdMrab(event.getItem());
					return true;
				} catch (TTransportException e) {
					conMan.reconnect(e);
					return false;
				} catch (Exception e) {
					return false;
				}
			}
		});
		tblMrab.registerAddRowListener(new CustomTableItemChangeEventListener<MestoRab>() {
			@Override
			public boolean doAction(CustomTableItemChangeEvent<MestoRab> event) {
				try {
					MestoRab item = event.getItem();
					item.pcod = tblVrach.getSelectedItem().pcod;
					item.clpu = 20;
					item.id = tcl.AddMrab(item);
					return true;
				} catch (TTransportException e) {
					conMan.reconnect(e);
					return false;
				} catch (Exception e) {
					return false;
				}
			}
		});
		tblMrab.setFillsViewportHeight(true);
		
		JButton btnVrPerm = new JButton();
		btnVrPerm.setIcon(new ImageIcon(PermForm.class.getResource("/ru/nkz/ivcgzo/clientVrachInfo/resources/1331789257_User.png")));
		btnVrPerm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					tcl.getServerVersion();
					permForm.setLocationRelativeTo(frame);
					permForm.showWindow(tblVrach.getSelectedItem(), tblMrab.getSelectedItem(), ((tblVrach.getSelectedItem().pcod == authInfo.pcod) && (tblMrab.getSelectedItem().cpodr == authInfo.cpodr) && (tblMrab.getSelectedItem().clpu == authInfo.clpu)));
				} catch (TTransportException e1) {
					conMan.reconnect(e1);
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		JButton btnMrAdd = new JButton();
		btnMrAdd.setIcon(new ImageIcon(PermForm.class.getResource("/ru/nkz/ivcgzo/clientVrachInfo/resources/1331789242_Add.png")));
		btnMrAdd.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tblMrab.requestFocus();
				tblMrab.addItem();
			}
		});
		btnMrAdd.setEnabled(tblMrab.isEditable());
		
		JButton btnMrDel = new JButton();
		btnMrDel.setIcon(new ImageIcon(PermForm.class.getResource("/ru/nkz/ivcgzo/clientVrachInfo/resources/1331789259_Delete.png")));
		btnMrDel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				tblMrab.deleteSelectedRow();
			}
		});
		btnMrDel.setEnabled(tblMrab.isEditable());
		
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(spMrab)
						.addComponent(spVrach, GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnVrPerm, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnVrAdd, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnVrDel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnMrDel, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnMrAdd, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE))
					.addGap(6))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnVrDel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnVrAdd))
						.addComponent(spVrach, GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(btnVrPerm)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnMrDel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnMrAdd))
						.addComponent(spMrab, GroupLayout.PREFERRED_SIZE, 204, GroupLayout.PREFERRED_SIZE))
					.addGap(10))
		);
		if (!adminMode)
			btnVrPerm.setVisible(false);
		
		spMrab.setViewportView(tblMrab);
		spVrach.setViewportView(tblVrach);
		frame.getContentPane().setLayout(groupLayout);
		
		frame.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				if (conMan != null)
					conMan.remove();
				super.windowClosing(e);
			}
		});
		
		frame.setLocationRelativeTo(null);
		
		permForm = new PermForm();
	}

	public void onConnect() throws TException {
	}

	@Override
	public String getVersion() {
		return "0.1";
	}

	@Override
	public int getId() {
		return configuration.appId;
	}

	@Override
	public String getName() {
		return configuration.appName;
	}

	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		if (conn instanceof ThriftServerVrachInfo.Client) {
			tcl = (ThriftServerVrachInfo.Client) conn;
			permForm.setClient(tcl);
			try {
				tblVrach.setData(tcl.GetVrachList());
				tblMrab.setIntegerClassifierSelector(4, tcl.getPrizndList());
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
