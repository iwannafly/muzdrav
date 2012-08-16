package ru.nkz.ivcgzo.clientViewSelect.modalForms;

import java.awt.Rectangle;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.ModalForm;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.clientViewSelect.MainForm;
import ru.nkz.ivcgzo.thriftCommon.classifier.ClassifierSortFields;
import ru.nkz.ivcgzo.thriftCommon.classifier.ClassifierSortOrder;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;

public class ViewIntegerClassifierForm extends ModalForm {
	private static final long serialVersionUID = 693981287168747961L;
	
	private JPanel contentPane;
	private CustomTextField tbFilter;
	private CustomTable<IntegerClassifier, IntegerClassifier._Fields> tblIntClass;

	public ViewIntegerClassifierForm() {
		super(false);
		
		setTitle("Выбор из классификатора");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		tbFilter = new CustomTextField(true, true, false);
		tbFilter.setColumns(10);
		tbFilter.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				filter();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				filter();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				filter();
			}
		});
		tbFilter.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					if (tbFilter.getDocument().getLength() > 0)
						tbFilter.clear();
					else
						declineResults();
				else if (e.getKeyCode() == KeyEvent.VK_DOWN || e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_ENTER)
					tblIntClass.dispatchEvent(e);
			}
		});
		
		JLabel lblFilter = new JLabel("Фильтр");
		
		JScrollPane spnIntClass = new JScrollPane();
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.LEADING)
						.addComponent(spnIntClass, GroupLayout.DEFAULT_SIZE, 602, Short.MAX_VALUE)
						.addGroup(gl_contentPane.createSequentialGroup()
							.addComponent(lblFilter, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(tbFilter, GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPane.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPane.createParallelGroup(Alignment.BASELINE)
						.addComponent(tbFilter, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFilter))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(spnIntClass, GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		tblIntClass = new CustomTable<>(false, false, IntegerClassifier.class, 0, "Код", 1, "Наименование");
		tblIntClass.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2)
					acceptResults();
			}
		});
		tblIntClass.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					acceptResults();
				else if (e.getKeyCode() == KeyEvent.VK_ESCAPE)
					declineResults();
			}
		});
		setFilterModel();
		tblIntClass.setFillsViewportHeight(true);
		spnIntClass.setViewportView(tblIntClass);
		contentPane.setLayout(gl_contentPane);
	}
	
	private void setFilterModel() {
		tblIntClass.setModel(tblIntClass.new CustomTableModel() {
			private static final long serialVersionUID = -4774136922669712220L;
			
			private int[] indexes = new int[] {};
			private int indexesCount = indexes.length;
			private String previousString;
			
			@Override
			public int getRowCount() {
				if (indexes.length != super.getRowCount())
					indexes = new int[super.getRowCount()];
				if (tbFilter.getText().equals(previousString))
					return indexesCount;
				
				previousString = tbFilter.getText().toLowerCase();
				indexesCount = 0;
				for (int i = 0; i < super.getRowCount(); i++) {
					for (int j = 0; j < super.getColumnCount(); j++) {
						if (super.getValueAt(i,  j).toString().toLowerCase().indexOf(previousString) > -1) {
							indexes[indexesCount++] = i;
							break;
						}
					}
				}
				
				return indexesCount;
			}
			
			@Override
			public Object getValueAt(int rowIndex, int columnIndex) {
				return super.getValueAt(indexes[rowIndex], columnIndex);
			}
		});
	}
	
	private void filter() {
		tblIntClass.getModel().fireTableDataChanged();
		if (tblIntClass.getRowCount() > 0) {
			tblIntClass.getSelectionModel().setSelectionInterval(0, 0);
			tblIntClass.scrollRectToVisible(new Rectangle());
		}
	}
	
	@Override
	public void acceptResults() {
		results = tblIntClass.getSelectedItem();
		if (results == null)
			return;
		
		super.acceptResults();
	}
	
	@Override
	public IntegerClassifier getResults() {
		if (results != null)
			return (IntegerClassifier) results;
		
		return null;
	}
	
	public void prepare(IntegerClassifiers cls, ClassifierSortOrder ord, ClassifierSortFields fld) {
		try {
			tblIntClass.setData(MainForm.ccm.getIntegerClassifier(cls, ord, fld));
		} catch (KmiacServerException e) {
			JOptionPane.showMessageDialog(MainForm.conMan.getClient().getFrame(), e.getMessage(), "Ошибка загрузки классификатора", JOptionPane.ERROR_MESSAGE);
		} catch (TException e) {
			MainForm.conMan.reconnect(e);
		}
	}
}
