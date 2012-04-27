package ru.nkz.ivcgzo.clientRegPatient;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
//import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
//import ru.nkz.ivcgzo.thriftreg.Thriftreg;
import ru.nkz.ivcgzo.thriftRegPatient.ThriftRegPatient;

public class MainForm extends Client {
    public static ThriftRegPatient.Client tcl;
	//public static Thriftreg.Client tcl;
	private JFrame frame;
//	private UserAuthInfo authInfo;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					new MainForm(null, null, 0);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException, UnsupportedLookAndFeelException {
		super(conMan, authInfo, lncPrm);
		this.authInfo = authInfo;
		
		if (conMan != null) {
			conMan.add(ThriftRegPatient.Client.class, configuration.thrPort);
			conMan.setLocalForm(frame);
		} else //такой подход рекомендуется только на начальных этапах разработки
			try {
				TTransport transport = new TFramedTransport(new TSocket("localhost", configuration.thrPort));
				transport.open();
				onConnect(new ThriftRegPatient.Client(new TBinaryProtocol(transport)));
			} catch (TTransportException e) {
				e.printStackTrace();
				System.exit(1);
			}
		initialize();
		//frame.setVisible(true);
	}
	/**
	 * Initialize the contents of the frame.
	 * @throws UnsupportedLookAndFeelException 
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws ClassNotFoundException 
	 */
	private void initialize() throws ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedLookAndFeelException {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		PacientMainFrame pacientMainFrame = new PacientMainFrame();
		pacientMainFrame.pack();
		pacientMainFrame.setVisible(true);
	}
	@Override
	public String getVersion() {
		return configuration.appVersion;
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
		if (conn instanceof ThriftRegPatient.Client) {
			tcl = (ThriftRegPatient.Client) conn;
//			try {
////				table.setData(tcl.getZapVr(6, "3", SimpleDateFormat.getDateInstance().parse("27.03.2012").getTime()));
//			} catch (KmiacServerException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (TException | ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}

}