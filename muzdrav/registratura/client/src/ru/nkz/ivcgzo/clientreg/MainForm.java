package ru.nkz.ivcgzo.clientRegPatient;

import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftRegPatient.ThriftRegPatient;

public class MainForm extends Client {
    public static ThriftRegPatient.Client tcl;
	private JFrame frame;
	private UserAuthInfo authInfo;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_status;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_ishod;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_tdoc;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_oms_doc;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_Tdoc_pr;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_Polis_doc_pr;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_cotd;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_travm;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_trans;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_otkaz;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_alk;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_naprav;
	private ThriftIntegerClassifierCombobox <IntegerClassifier> cmb_org;
	
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
			try {
				cmb_ishod = new ThriftIntegerClassifierCombobox<>(true);
				cmb_status = new ThriftIntegerClassifierCombobox<>(true);
				cmb_tdoc = new ThriftIntegerClassifierCombobox<>(true);
				cmb_oms_doc = new ThriftIntegerClassifierCombobox<>(true);
				cmb_Tdoc_pr = new ThriftIntegerClassifierCombobox<>(true);
				cmb_Polis_doc_pr = new ThriftIntegerClassifierCombobox<>(true);
				cmb_cotd = new ThriftIntegerClassifierCombobox<>(true);
				cmb_travm = new ThriftIntegerClassifierCombobox<>(true);
				cmb_trans = new ThriftIntegerClassifierCombobox<>(true);
				cmb_otkaz = new ThriftIntegerClassifierCombobox<>(true);
				cmb_alk = new ThriftIntegerClassifierCombobox<>(true);
				cmb_naprav = new ThriftIntegerClassifierCombobox<>(true);
				cmb_org = new ThriftIntegerClassifierCombobox<>(true);
				//cmb_status.setData(tcl);
				//cmb_ishod
				//cmb_tdoc
				//cmb_oms_doc
//				cmb_Tdoc_pr;
//				cmb_Polis_doc_pr;
//				cmb_cotd;
//				cmb_travm;
//				cmb_trans;
//				cmb_otkaz;
//				cmb_alk;
//				cmb_naprav;
//				cmb_org;
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
//				catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
		}
	}

}
