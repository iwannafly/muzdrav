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

import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;

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
	
	/**
	 * Конструктор списка.
	 * @param list - список из thrift-структур для отображения
	 */
	public ThriftStringClassifierListCheckbox(List<StringClassifier> l) {
		super(l);
		
		setCellRenderer();
		setMouseListener();
		setKeyListener();
	}

	@Override
	public void setData(List<StringClassifier> list) {
		super.setData(list);
		
		boxes = new JCheckBox[getModel().getSize()];
		for (int i = 0; i < boxes.length; i++)
			boxes[i] = new JCheckBox(list.get(i).name);
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
}
