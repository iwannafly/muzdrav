package ru.nkz.ivcgzo.clientViewSelect;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftViewSelect.ThriftViewSelect;

public class ViewTablePcodStringForm extends ViewSelectForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8600318603287809954L;
	public static ThriftViewSelect.Client tcl;
	private static CustomTable<StringClassifier, StringClassifier._Fields> table;
	
	public ViewTablePcodStringForm() {
		createGUI();
		initialize();
		setDefaultCloseOperation(javax.swing.JFrame.DISPOSE_ON_CLOSE);
	}
		/**
		 * Initialize the contents of the frame.
		 */
	private void initialize() {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent arg0) {
				setExtendedState(JFrame.MAXIMIZED_BOTH);
				}
		});
		
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
			
		table = new CustomTable<>(false, true, StringClassifier.class, 0, "Код", 1, "Наименование");
		table.getColumnModel().getColumn(0).setWidth(aws/3);
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
		    	  System.out.print(getViewTableValues().getName());
		    	  System.out.println();
		   
		         }
		      }
		     } );
		
		table.addKeyListener(new KeyListener(){
			public void keyPressed(KeyEvent e){
				if(e.getKeyCode()== KeyEvent.VK_ENTER){ 
		    	  // Вывод значения на консоль
		    	  System.out.print(getViewTableValues().getName());
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
		     } );
	}

	// Заполнение таблицы
	public static void tableFill(String className){
		try { 
			table.setData(MainForm.tcl.getVSStringClassifierView(className));
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	// Запоминание выбранных ячеек
	public StringClassifier getViewTableValues() {
		return table.getSelectedItem();
	
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

}


