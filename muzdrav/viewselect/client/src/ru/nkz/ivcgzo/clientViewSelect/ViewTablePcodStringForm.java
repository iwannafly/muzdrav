package ru.nkz.ivcgzo.clientViewSelect;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;
import ru.nkz.ivcgzo.thriftViewSelect.ThriftViewSelect;

public class ViewTablePcodStringForm extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8600318603287809954L;
	public static ThriftViewSelect.Client tcl;
	public JTextField tfSearch;
	public JScrollPane spClassifier;
	private static CustomTable<StringClassifier, StringClassifier._Fields> table;
	
	public ViewTablePcodStringForm() {
		initialize();
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
		setTitle("Выбор из классификатора");
		setBounds(dm.width-aws, 0, aws, ahs);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
			
		tfSearch = new JTextField();
		tfSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				table.requestFocus();
			}
		});
					
		tfSearch.getDocument().addDocumentListener(new DocumentListener ()
	       {
			  public void changedUpdate(DocumentEvent documentEvent) {
			      search();
			  }
			  public void insertUpdate(DocumentEvent documentEvent) {
			      search();
			  }
			  public void removeUpdate(DocumentEvent documentEvent) {
			      search();
			  }
	       });
		getContentPane().add(tfSearch, BorderLayout.NORTH);
		tfSearch.setColumns(10);
			
		spClassifier = new JScrollPane();
		getContentPane().add(spClassifier, BorderLayout.CENTER);
			
		table = new CustomTable<>(false, true, StringClassifier.class, 0, "Код", 1, "Наименование");
		table.getColumnModel().getColumn(0).setWidth(aws/3);
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(0);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		spClassifier.setViewportView(table);
		}

	public static void tableFill(){
		try { 
			table.setData(MainForm.tcl.getVSStringClassifierView());
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
    private void search()
    {
    	String target = tfSearch.getText();
    	for(int row = 0; row < table.getRowCount(); row++)
    		for(int col = 0; col < table.getColumnCount(); col++)
    		{
    			String next = table.getValueAt(row, col).toString();
    			Pattern pt = Pattern.compile(target.toLowerCase());
    			Matcher mt = pt.matcher(next.toLowerCase());
    			if (mt.find())
    			{
    				table.setRowSelectionInterval(row, row);
    				table.setLocation(0, -((table.getRowHeight() * row) - 100));
    				return;
    			}
    		}
    
    }

}


