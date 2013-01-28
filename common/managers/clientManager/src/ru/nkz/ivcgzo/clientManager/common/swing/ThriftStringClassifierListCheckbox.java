package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.Component;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;

/**
 * Параметризированный класс для работы со списками swing. В качестве параметра должна
 * указываться thrift-структура.
 * @author bsv798
 *
 * @param <T> - thrift-структура {@link StringClassifier}
 */
public class ThriftStringClassifierListCheckbox extends ThriftStringClassifierList<StringClassifier> {
	private static final long serialVersionUID = -1232286636830958779L;
	private JCheckBox[] boxes;
	private ActionListener boxListener;
	private ThriftStringClassifierListCheckboxActionListener actionListener;
	
	/**
	 * Конструктор списка.
	 */
	public ThriftStringClassifierListCheckbox() {
		super();
		
		init();
	}
	
	/**
	 * Конструктор списка.
	 * @param list - список из thrift-структур для отображения
	 */
	public ThriftStringClassifierListCheckbox(List<StringClassifier> l) {
		super(l);
		
		init();
	}
	
	/**
	 * Конструктор списка.
	 * @param classifierName - название классификатора для загрузки
	 */
	public ThriftStringClassifierListCheckbox(StringClassifiers classifierName) {
		super(classifierName);
		
		init();
	}
	
	private void init() {
		setCellRenderer();
		setMouseListener();
		setKeyListener();
		
		boxListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				notifyActionListener(e);
			}
		};
	}
	
	@Override
	public void setData(List<StringClassifier> list) {
		if (list == null)
			list = new ArrayList<>();
			
		boxes = new JCheckBox[list.size()];
		for (int i = 0; i < boxes.length; i++) {
			boxes[i] = new JCheckBox(list.get(i).name);
			boxes[i].addActionListener(boxListener);
		}
		
		super.setData(list);
	}
	
	private void setCellRenderer() {
		setCellRenderer(new ListCellRenderer<StringClassifier>() {
			@Override
			public Component getListCellRendererComponent(JList<? extends StringClassifier> list, StringClassifier value, int index, boolean isSelected, boolean cellHasFocus) {
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
				int idx = ThriftStringClassifierListCheckbox.this.locationToIndex(e.getPoint());
				
				if (idx > -1) {
					JCheckBox chb = boxes[idx];
					Rectangle bounds = ThriftStringClassifierListCheckbox.this.getCellBounds(idx, idx);
					
					chb.setBounds(bounds);
					chb.dispatchEvent(new MouseEvent(chb, e.getID(), e.getWhen(), e.getModifiers(), 0, 0, e.getXOnScreen(), e.getYOnScreen(), e.getClickCount(), e.isPopupTrigger(), e.getButton()));
					ThriftStringClassifierListCheckbox.this.repaint(bounds);
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
					int idx = ThriftStringClassifierListCheckbox.this.getSelectedIndex();
					
					if (idx > -1) {
						JCheckBox chb = boxes[idx];
						Rectangle bounds = ThriftStringClassifierListCheckbox.this.getCellBounds(idx, idx);
						
						e.setSource(chb);
						chb.dispatchEvent(e);
						ThriftStringClassifierListCheckbox.this.repaint(bounds);
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
	
	private void notifyActionListener(ActionEvent e) {
		if (actionListener != null) {
			int idx = getSelectedIndex();

			actionListener.actionPerformed(new ThriftStringClassifierListCheckboxActionEvent(boxes[idx].isSelected(), getModel().getElementAt(idx)));
		}
	}
	
	public class ThriftStringClassifierListCheckboxActionEvent {
		private boolean selected;
		private StringClassifier value;
		
		public ThriftStringClassifierListCheckboxActionEvent(boolean selected, StringClassifier value) {
			this.selected = selected;
			this.value = value;
		}
		
		public boolean isSelected() {
			return selected;
		}
		
		public StringClassifier getValue() {
			return value;
		}
	}
	
	public interface ThriftStringClassifierListCheckboxActionListener {
		public void actionPerformed(ThriftStringClassifierListCheckboxActionEvent e);
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
	 * Устанавливает слушателя, реагирующего на изменение меток строк. 
	 */
	public void setCheckboxActionListener(ThriftStringClassifierListCheckboxActionListener lst) {
		actionListener = lst;
	}
	
	/**
	 * Получает список отмеченных строк.
	 */
	public List<StringClassifier> getSelectedItems() {
		return getSelectedItems(true);
	}
	
	/**
	 * Получает список неотмеченных строк.
	 */
	public List<StringClassifier> getUnselectedItems() {
		return getSelectedItems(false);
	}
	
	private List<StringClassifier> getSelectedItems(boolean sel) {
		List<StringClassifier> list = new ArrayList<>();
		
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
	public void selectItems(List<StringClassifier> list) {
		selectItems(list, true);
	}
	
	/**
	 * Снимает отметки со строк, указанных в списке.
	 */
	public void unselectItems(List<StringClassifier> list) {
		selectItems(list, false);
	}
	
	private void selectItems(List<StringClassifier> list, boolean value) {
		for (StringClassifier ic : list)
			for (int i = 0; i < getModel().getSize(); i++)
				if (ic.pcod.equals(getModel().getElementAt(i).pcod)) {
					boxes[i].setSelected(value);
					break;
				}
		repaint();
	}
	
	/**
	 * Проверяет, все ли строки отмечены. 
	 */
	public boolean isAllItemsSelected() {
		return isAllItemsSelected(true);
	}
	
	/**
	 * Проверяет, все ли строки не отмечены. 
	 */
	public boolean isAllItemsUnselected() {
		return isAllItemsSelected(false);
	}
	
	private boolean isAllItemsSelected(boolean val) {
		for (int i = 0; i < boxes.length; i++)
			if (boxes[i].isSelected() != val)
				return false;
		
		return true;
	}
}
