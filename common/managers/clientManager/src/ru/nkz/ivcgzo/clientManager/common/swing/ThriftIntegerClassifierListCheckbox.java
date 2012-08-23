package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;

/**
 * Параметризированный класс для работы со списками swing. В качестве параметра должна
 * указываться thrift-структура.
 * @author bsv798
 *
 * @param <T> - thrift-структура {@link IntegerClassifier}
 */
public class ThriftIntegerClassifierListCheckbox extends ThriftIntegerClassifierList {
	private static final long serialVersionUID = 8432209421914611376L;
	private JCheckBox[] boxes;
	
	/**
	 * Конструктор списка.
	 */
	public ThriftIntegerClassifierListCheckbox() {
		super();
		
		init();
	}
	
	/**
	 * Конструктор списка.
	 * @param list - список из thrift-структур для отображения
	 */
	public ThriftIntegerClassifierListCheckbox(List<IntegerClassifier> l) {
		super(l);
		
		init();
	}
	
	/**
	 * Конструктор списка.
	 * @param classifierName - название классификатора для загрузки
	 */
	public ThriftIntegerClassifierListCheckbox(IntegerClassifiers classifierName) {
		super(classifierName);
		
		init();
	}
	
	private void init() {
		setCellRenderer();
		setMouseListener();
		setKeyListener();
	}
	
	@Override
	public void setData(List<IntegerClassifier> list) {
		super.setData(list);
		
		boxes = new JCheckBox[getModel().getSize()];
		for (int i = 0; i < boxes.length; i++)
			boxes[i] = new JCheckBox(list.get(i).name);
	}
	
	private void setCellRenderer() {
		setCellRenderer(new ListCellRenderer<IntegerClassifier>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends IntegerClassifier> list, IntegerClassifier value, int index, boolean isSelected, boolean cellHasFocus) {
				JCheckBox chb = boxes[index];
				
				chb.setEnabled(list.isEnabled());
				chb.setFont(list.getFont());
				if (isSelected) {
					chb.setForeground(list.getSelectionForeground());
					chb.setBackground(list.getSelectionBackground());
				} else {
					chb.setForeground(list.getForeground());
					chb.setBackground(list.getBackground());
				}
				
				return chb;
			}
		});
	}
	
	private void setMouseListener() {
		addMouseListener(new MouseAdapter() {
			private void transferEvent(MouseEvent e) {
				int idx = ThriftIntegerClassifierListCheckbox.this.locationToIndex(e.getPoint());
				
				if (idx > -1) {
					JCheckBox chb = boxes[idx];
					Rectangle bounds = ThriftIntegerClassifierListCheckbox.this.getCellBounds(idx, idx);
					
					chb.setBounds(bounds);
					chb.dispatchEvent(new MouseEvent(chb, e.getID(), e.getWhen(), e.getModifiers(), 0, 0, e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton()));
					ThriftIntegerClassifierListCheckbox.this.repaint(bounds);
				}
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				transferEvent(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				transferEvent(e);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				transferEvent(e);
			}
		});
	}
	
	private void setKeyListener() {
		addKeyListener(new KeyListener() {
			private void transferEvent(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_SPACE) {
					int idx = ThriftIntegerClassifierListCheckbox.this.getSelectedIndex();
					
					if (idx > -1) {
						JCheckBox chb = boxes[idx];
						Rectangle bounds = ThriftIntegerClassifierListCheckbox.this.getCellBounds(idx, idx);
						
						e.setSource(chb);
						chb.dispatchEvent(e);
						ThriftIntegerClassifierListCheckbox.this.repaint(bounds);
					}
				}
			}
			
			@Override
			public void keyTyped(KeyEvent e) {
				transferEvent(e);
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				transferEvent(e);
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				transferEvent(e);
			}
		});
	}
	
//	FIXME this focus frame applies for windows only
//	@Override
//	public void paint(Graphics g) {
//		super.paint(g);
//		
//		drawFocus(g);
//	}
//	
//	private void drawFocus(Graphics g) {
//		int idx = getSelectedIndex();
//		
//		if (hasFocus() && idx > -1) {
//			Rectangle bounds = getCellBounds(idx, idx);
//			
//			g.setColor(new Color(~getSelectionBackground().getRGB()));
//			BasicGraphicsUtils.drawDashedRect(g, bounds.x, bounds.y, bounds.width, bounds.height);
//		}
//	}
	
	/**
	 * Получает список отмеченных строк.
	 */
	public List<IntegerClassifier> getSelectedItems() {
		return getSelectedItems(true);
	}
	
	/**
	 * Получает список неотмеченных строк.
	 */
	public List<IntegerClassifier> getUnselectedItems() {
		return getSelectedItems(false);
	}
	
	private List<IntegerClassifier> getSelectedItems(boolean sel) {
		List<IntegerClassifier> list = new ArrayList<>();
		
		for (int i = 0; i < getModel().getSize(); i++)
			if (boxes[i].isSelected() == sel)
				list.add(getModel().getElementAt(i));
		
		return list;
	}
	
	/**
	 * Отмечает все строки.
	 */
	public void selectAllItems() {
		selectAllItems(true);
	}
	
	/**
	 * Снимает отметки со всех строк.
	 */
	public void unselectAllItems() {
		selectAllItems(false);
	}
	
	private void selectAllItems(boolean val) {
		for (int i = 0; i < boxes.length; i++)
			boxes[i].setSelected(val);
		repaint();
	}
	
	/**
	 * Отмечает строки, указанные в списке.
	 */
	public void selectItems(List<IntegerClassifier> list) {
		selectItems(list, true);
	}
	
	/**
	 * Снимает отметки со строк, указанных в списке.
	 */
	public void unselectItems(List<IntegerClassifier> list) {
		selectItems(list, false);
	}
	
	private void selectItems(List<IntegerClassifier> list, boolean value) {
		for (IntegerClassifier ic : list)
			for (int i = 0; i < getModel().getSize(); i++)
				if (ic.pcod == getModel().getElementAt(i).pcod) {
					boxes[i].setSelected(value);
					break;
				}
		repaint();
	}
}
