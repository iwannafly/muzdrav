package ru.nkz.ivcgzo.clientViewSelect;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Rectangle;
import java.awt.Toolkit;

import javax.swing.JFrame;
import java.lang.reflect.InvocationTargetException;
import javax.swing.JScrollPane;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftViewSelect.ThriftViewSelect;
import javax.swing.JTable;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends Client {
	public static ThriftViewSelect.Client tcl;
	public static UserAuthInfo authInfo;
	public static ConnectionManager conMan;
	public JFrame frame;
	public JTextField tfSearch;
	public JScrollPane spClassifier;
	private CustomTable<IntegerClassifier, IntegerClassifier._Fields> table;

	public MainForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		super(conMan, authInfo, lncPrm);
		
		this.authInfo = authInfo;
		
		if (conMan != null) {
			conMan.add(ThriftViewSelect.Client.class, configuration.thrPort);
			conMan.setLocalForm(frame);
		} else 
			try {
				TTransport transport = new TFramedTransport(new TSocket("localhost", configuration.thrPort));
				transport.open();
				onConnect(new ThriftViewSelect.Client(new TBinaryProtocol(transport)));
			} catch (TTransportException e) {
				e.printStackTrace();
				System.exit(1);
			}
		initialize();
		frame.setVisible(true);
	}


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

	/**
	 * Create the application.
	 */

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

	public class metaClassifier {
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		Dimension dm = Toolkit.getDefaultToolkit().getScreenSize();
		/**
		 * aws - Ширина окна на треть экрана
		 * ahs - Высота окна на весь экран
		 */
		int aws=dm.width/3;
		int ahs=dm.height;
		frame = new JFrame();
		frame.setTitle("Выбор из классификатора");
		frame.setBounds(dm.width-aws, 0, aws, ahs);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		tfSearch = new JTextField();
		tfSearch.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                search();
            }
        });
		frame.getContentPane().add(tfSearch, BorderLayout.NORTH);
		tfSearch.setColumns(10);
		
		spClassifier = new JScrollPane();
		frame.getContentPane().add(spClassifier, BorderLayout.CENTER);
		
		table = new CustomTable<>(false, true, IntegerClassifier.class, 0, "Код", 1, "Наименование");
		table.getColumnModel().getColumn(0).setWidth(100);
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(0);
		table.setFillsViewportHeight(true);
		spClassifier.setViewportView(table);
		}

	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		if (conn instanceof ThriftViewSelect.Client) {
			tcl = (ThriftViewSelect.Client) conn;
			try { 
				table.setData(tcl.getVSIntegerClassifierView());
				frame.setTitle( table.getModel().getColumnClass(1).toString());
				
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
		
	}
	
	//Поиск по таблице
	private void search()
    {
        String target = tfSearch.getText();
        for(int row = 0; row < table.getRowCount(); row++)
            for(int col = 0; col < table.getColumnCount(); col++)
            {
                String next = (String)table.getValueAt(row, col);
                if(next.equals(target))
                {
                    showSearchResults(row, col);
                    return;
                }
            }
		
	}
	
	//Отображение результатов поиска
	private void showSearchResults(int row, int col)
    {
        table.setRowSelectionInterval(row, row);
    }
	
}
