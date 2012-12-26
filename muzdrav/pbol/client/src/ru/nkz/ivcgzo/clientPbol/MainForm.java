package ru.nkz.ivcgzo.clientPbol;

import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftPbol.Pbol;
import ru.nkz.ivcgzo.thriftPbol.ThriftPbol;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;

import org.apache.thrift.TException;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.ImageIcon;

public class MainForm extends Client<ThriftPbol.Client>{
	public static ThriftPbol.Client tcl;
	public static MainForm instance;
	private JFrame frame;
	private CustomTable<Pbol, Pbol._Fields> tblPbol;
	private Pbol pbol;
	

	public MainForm (ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		super(conMan, authInfo, ThriftPbol.Client.class, configuration.appId, configuration.thrPort, lncPrm);
		initialize();
		
		setFrame(frame);
		
		JScrollPane spPbol = new JScrollPane();
		
		JButton button = new JButton("");
		button.setIcon(new ImageIcon(MainForm.class.getResource("/ru/nkz/ivcgzo/clientPbol/resources/1331789242_Add.png")));
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pbol = new Pbol();
				pbol.setNpasp(1003336);
				pbol.setPcod(MainForm.authInfo.getCpodr());
				pbol.setId_obr(1958);
				pbol.setId_gosp(0);
				pbol.setDataz(System.currentTimeMillis());
				pbol.setCdol(MainForm.authInfo.getCdol());
				pbol.setCod_sp(MainForm.authInfo.getPcod());
				try {
					pbol.setId(MainForm.tcl.AddPbol(pbol));
				} catch (KmiacServerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				tblPbol.addItem(pbol);
				
			}
		});
		
		final JButton button_1 = new JButton("");
		button_1.setIcon(new ImageIcon(MainForm.class.getResource("/ru/nkz/ivcgzo/clientPbol/resources/1331789259_Delete.png")));
		button_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
	  			try {				
	  				if (tblPbol.getSelectedItem()!= null)
	  					if (JOptionPane.showConfirmDialog(button_1, "Удалить запись?", "Удаление записи", JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION){
	  						MainForm.tcl.DeletePbol(tblPbol.getSelectedItem().getId());
	  						tblPbol.setData(MainForm.tcl.getPbol(1003336));
	  					}
				} catch (KmiacServerException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}


			}
		});
		
		JButton btnV = new JButton("");
		btnV.setIcon(new ImageIcon(MainForm.class.getResource("/ru/nkz/ivcgzo/clientPbol/resources/1341981970_Accept.png")));
		btnV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			try {
				pbol = tblPbol.getSelectedItem();
				MainForm.tcl.UpdatePbol(pbol);
			} catch (KmiacServerException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (TException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			}
		});
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(spPbol, GroupLayout.DEFAULT_SIZE, 967, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(button)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(button_1)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnV)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(spPbol, GroupLayout.PREFERRED_SIZE, 246, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(button)
						.addComponent(button_1)
						.addComponent(btnV))
					.addContainerGap(412, Short.MAX_VALUE))
		);
		
		tblPbol = new CustomTable<>(true, true, Pbol.class, 4,"Причина открытия б/л",5,"Дата открытия б/л",6,"Дата закрытия б/л",7,"Пол (по уходу)",8,"Возраст (по уходу)",9,"Номер б/л") ;
		tblPbol.setDateField(1);
		tblPbol.setDateField(2);		
		tblPbol.setFillsViewportHeight(true);
		spPbol.setViewportView(tblPbol);
		frame.getContentPane().setLayout(groupLayout);
		
	}

	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Больничный лист");
		frame.setBounds(100, 100, 995, 737);
	}

	@Override
	public String getName() {
		return configuration.appName;
	}
	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		super.onConnect(conn);
		if (conn instanceof ThriftPbol.Client) {
			tcl = thrClient;
			onTclConnect();
		}
		
	}
	
	public void onTclConnect() {
			try {
				tblPbol.setData(MainForm.tcl.getPbol(1003336));
			} catch (KmiacServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (TException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	//		tblPbol.setIntegerClassifierSelector(0, ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_bl1));
			tblPbol.setIntegerClassifierSelector(3, ConnectionManager.instance.getIntegerClassifier(IntegerClassifiers.n_z30));
	}
}
