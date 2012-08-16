package ru.nkz.ivcgzo.clientViewSelect;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftViewSelect.ThriftViewSelect;

public class ViewTablePcodIntForm extends ViewSelectForm {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6323530520847534835L;
	public static ThriftViewSelect.Client tcl;
	private static CustomTable<IntegerClassifier, IntegerClassifier._Fields> table;
	
	public ViewTablePcodIntForm() {
		createGUI();
		initialize();
	}
		/**
		 * Initialize the contents of the frame.
		 */
	private void initialize() {
		
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
			
		table = new CustomTable<>(false, true, IntegerClassifier.class, 0, "Код", 1, "Наименование");
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

	public static void tableFill(String className){
		try { 
			table.setData(MainForm.tcl.getVSIntegerClassifierView(className));
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}
	
	// Запоминание выбранных ячеек
	public IntegerClassifier getViewTableValues() {
		return table.getSelectedItem();
	
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
