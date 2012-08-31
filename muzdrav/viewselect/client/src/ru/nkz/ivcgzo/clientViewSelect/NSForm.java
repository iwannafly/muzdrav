package ru.nkz.ivcgzo.clientViewSelect;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.RowFilter;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.InvocationTargetException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableRowSorter;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftViewSelect.ThriftViewSelect;
import ru.nkz.ivcgzo.clientManager.common.Client;
import ru.nkz.ivcgzo.clientManager.common.ConnectionManager;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.UserAuthInfo;

public class NSForm extends Client<ThriftViewSelect.Client> {

	private JFrame frame;
	private JTextField tfSearch;
	public static ThriftViewSelect.Client tcl;
	private static CustomTable<StringClassifier, StringClassifier._Fields> table;

	/**
	 * Launch the application.
	 */
	public NSForm(ConnectionManager conMan, UserAuthInfo authInfo, int lncPrm) throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, ClassNotFoundException {
		super(conMan, authInfo, ThriftViewSelect.Client.class, configuration.appId, configuration.thrPort, lncPrm);

		initialize();
		setFrame(frame);
		//setFrame(new ViewTablePcodStringForm());
		//setFrame(new ViewTablePcodIntForm());
	}


	/**
	 * Create the application.
	 */
	//public NSForm() {
	//	initialize();
	//}

	@Override
	public String getName() {
		return configuration.appName;
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		
		frame = new JFrame();
		frame.setTitle("Нормативно-справочная информация");
		frame.setBounds(100, 100, 750, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		ButtonGroup group = new ButtonGroup();
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		JMenu mnNewMenu = new JMenu("Показать");
		menuBar.add(mnNewMenu);
		
		final JMenuItem rbMenuItemAll = new JRadioButtonMenuItem("Все");
		mnNewMenu.add(rbMenuItemAll);
		group.add(rbMenuItemAll);
		rbMenuItemAll.setSelected(true);
		
		rbMenuItemAll.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rbMenuItemAll.isSelected()) {
					table.setRowSorter(null);
					}
				
				
			}
		});
		
		final JMenuItem rbMenuItemStat = new JRadioButtonMenuItem("Статические");
		//cbMenuItem.setMnemonic(KeyEvent.VK_C);

		mnNewMenu.add(rbMenuItemStat);
		group.add(rbMenuItemStat);

		rbMenuItemStat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				/**TableRowSorter sorter = new TableRowSorter(table.getModel());
				table.setRowSorter(sorter);
				RowFilter rf;
				try {
				rf = RowFilter.regexFilter("^n_", 0);
				} catch (java.util.regex.PatternSyntaxException e) {
				return;
				}
				sorter.setRowFilter(rf);*/
				if (rbMenuItemStat.isSelected()) {
					table.setRowSorter(null);
					setClassifierFilter("n_");
					}
				
				
			}
		});


		
		final JMenuItem rbMenuItemDyn = new JRadioButtonMenuItem("Редактируемые");
		//cbMenuItem.setMnemonic(KeyEvent.VK_H);
		mnNewMenu.add(rbMenuItemDyn);
		group.add(rbMenuItemDyn);

		rbMenuItemDyn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (rbMenuItemDyn.isSelected()) {
					table.setRowSorter(null);					
					setClassifierFilter("s_");
				}
				//else setClassifierFilter();
				//setClassifierFilter("s_");
			}
			
		});
		
		tfSearch = new JTextField();
		frame.getContentPane().add(tfSearch, BorderLayout.NORTH);
		tfSearch.setColumns(10);
		
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
		
		JScrollPane spClassifier = new JScrollPane();
		frame.getContentPane().add(spClassifier, BorderLayout.CENTER);
		table = new CustomTable<>(false, true, StringClassifier.class, 0, "Код", 1, "Наименование");
		table.setAutoCreateRowSorter(true);
		table.getRowSorter().toggleSortOrder(0);
		table.setFillsViewportHeight(true);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
		table.getColumnModel().getColumn(0).setMaxWidth(100);
		spClassifier.setViewportView(table);
		
		table.addMouseListener(new MouseAdapter(){
		     public void mouseClicked(MouseEvent e){
		      if (e.getClickCount() == 2){
		    	  // Вывод значения на консоль
		    	  //System.out.print(getViewTableValues().getName());
		    	  String className = table.getSelectedItem().pcod;
		    	  try {
					if (tcl.isClassifierPcodInteger(className)) {
						  ViewTablePcodIntForm VSPIForm = new ViewTablePcodIntForm();
						  ViewTablePcodIntForm.tableFill(className);
						  VSPIForm.setVisible(true);
						//System.out.print(className);
					  }
					else {
						  ViewTablePcodStringForm VSPSForm = new ViewTablePcodStringForm();
						  ViewTablePcodStringForm.tableFill(className);
						  VSPSForm.setVisible(true);
					}
				} catch (TException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		   
		         }
		      }
		     } );
		
		table.addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode()== KeyEvent.VK_ENTER) { 
		    	  // Вывод значения на консоль
		    	  //System.out.print(getViewTableValues().getName());
		    	  System.out.println();
		         }
		      }
			
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
	}

	@Override
	public void onConnect(ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServer.Client conn) {
		super.onConnect(conn);
		if (conn instanceof ThriftViewSelect.Client) {
			tcl = thrClient;
			try { 
				table.setData(tcl.getVSStringClassifierView("n_spr"));
			} catch (Exception e) {
				e.printStackTrace();
			} 
			
		}
		
	}
	
	// Быстрый поиск по таблице
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
    
    //Фильтрация классификаторов
	public void setClassifierFilter (String beginsWith) {
		TableRowSorter sorter = new TableRowSorter(table.getModel());
		table.setRowSorter(sorter);
		RowFilter rf;
		try {
		rf = RowFilter.regexFilter("^"+beginsWith, 0);
		} catch (java.util.regex.PatternSyntaxException e) {
		return;
		}
		sorter.setRowFilter(rf);
		
	}
	
}