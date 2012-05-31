package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.Component;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Action;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.KeyStroke;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;
import javax.swing.text.TextAction;

/**
 * Обертка для текстовых компонентов, расширяющая их возможности.
 * @author bsv798
 */
public class CustomTextComponentWrapper {
	private JTextComponent textComponent;
	
	private boolean isSetTextSelectionOnFocus;
	private boolean focusGainedByMouseClick;
	private boolean hasFocus;
	
	private boolean isSetPopupMenu;
	
	public CustomTextComponentWrapper(JTextComponent textComponent) {
		this.textComponent = textComponent;
	}
	
	/**
	 * Выделение всего текста в компоненте при получении им фокуса.
	 */
	public void setTextSelectionOnFocus() {
		if (!isSetTextSelectionOnFocus) {
			
			textComponent.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					super.mousePressed(e);
					
					focusGainedByMouseClick = e.getButton() == MouseEvent.BUTTON1;
					focusGainedByMouseClick &= !hasFocus;
				}
				
				@Override
				public void mouseReleased(MouseEvent e) {
					super.mouseReleased(e);
					
					if (focusGainedByMouseClick)
						if (textComponent.getSelectionStart() == textComponent.getSelectionEnd())
							textComponent.selectAll();
					
					focusGainedByMouseClick = false;
				}
			});
			
			textComponent.addFocusListener(new FocusAdapter() {
				@Override
				public void focusGained(FocusEvent e) {
					super.focusGained(e);
					
					if (!(focusGainedByMouseClick || hasFocus))
						textComponent.selectAll();
					
					hasFocus = true;
				}
				
				@Override
				public void focusLost(FocusEvent e) {
					super.focusLost(e);
					
					hasFocus = e.isTemporary();
				}
			});
			
			isSetTextSelectionOnFocus = true;
		}
	}
	
	/**
	 * Установка всплывающего меню со стандартными пунктами "Копировать", "Вставить" и т. д.
	 */
	public void setPopupMenu() {
		if (!isSetPopupMenu) {
			textComponent.setComponentPopupMenu(new CustomTextComponentPopupMenu());
			
			isSetPopupMenu = true;
		}
	}
	
	class CustomTextComponentPopupMenu extends JPopupMenu{
		private static final long serialVersionUID = -1040685008740236830L;
		
		public CustomTextComponentPopupMenu() {
			super();
			
			setMouseListener();
			setPopupListener();
			
			this.add(new PopupMenuItemCut());
			this.add(new PopupMenuItemCopy());
			this.add(new PopupMenuItemPaste());
			this.add(new PopupMenuItemDelete());
			this.addSeparator();
			this.add(new PopupMenuItemSelectAll());
		}
		
		public void setPopupListener() {
			addPopupMenuListener(new PopupMenuListener() {
				
				@Override
				public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
					for (Component item : CustomTextComponentPopupMenu.this.getComponents()) {
						if (item instanceof PopupMenuItem)
						((PopupMenuItem) item).checkEnabled();
					}
				}
				
				@Override
				public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
				}
				
				@Override
				public void popupMenuCanceled(PopupMenuEvent e) {
				}
			});
		}
		
		public void setMouseListener() {
			textComponent.addMouseListener(new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {
					super.mousePressed(e);
					
					if (e.getButton() == MouseEvent.BUTTON3 && !hasFocus)
						textComponent.requestFocusInWindow();
				}
			});
		}
	}
	
	interface PopupMenuItem {
		public void checkEnabled();
	}
	
	class PopupMenuItemCut extends JMenuItem implements PopupMenuItem {
		private static final long serialVersionUID = -2080797409850944008L;
		
		public PopupMenuItemCut() {
			super();
			
			Action action = new DefaultEditorKit.CutAction();
			action.putValue(Action.NAME, "Вырезать");
			setAction(action);
			
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_MASK));
		}
		
		@Override
		public void checkEnabled() {
			setEnabled(textComponent.getSelectionStart() != textComponent.getSelectionEnd());
		}
	}
	
	class PopupMenuItemCopy extends JMenuItem implements PopupMenuItem {
		private static final long serialVersionUID = -2080797409850944008L;
		
		public PopupMenuItemCopy() {
			super();
			
			Action action = new DefaultEditorKit.CopyAction();
			action.putValue(Action.NAME, "Копировать");
			setAction(action);
			
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_MASK));
		}
		
		@Override
		public void checkEnabled() {
			setEnabled(textComponent.getSelectionStart() != textComponent.getSelectionEnd());
		}
	}
	
	class PopupMenuItemPaste extends JMenuItem implements PopupMenuItem {
		private static final long serialVersionUID = -2080797409850944008L;
		
		public PopupMenuItemPaste() {
			super();
			
			Action action = new DefaultEditorKit.PasteAction();
			action.putValue(Action.NAME, "Вставить");
			setAction(action);
			
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, KeyEvent.CTRL_MASK));
		}
		
		@Override
		public void checkEnabled() {
			boolean enabled;
			
			try {
				String text = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
				enabled = text != null;
			} catch (Exception e) {
				enabled = false;
			}
			
			setEnabled(enabled);
		}
	}
	
	class PopupMenuItemDelete extends JMenuItem implements PopupMenuItem {
		private static final long serialVersionUID = -2080797409850944008L;
		
		public PopupMenuItemDelete() {
			super();
			
			setAction(new TextAction("Удалить") {
				private static final long serialVersionUID = -2289203573984021765L;

				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						int len = textComponent.getSelectionEnd() - textComponent.getSelectionStart();
						textComponent.getDocument().remove(textComponent.getSelectionStart(), len);
					} catch (BadLocationException e1) {
					}
				}
			});
			
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0));
		}
		
		@Override
		public void checkEnabled() {
			setEnabled(textComponent.getSelectionStart() != textComponent.getSelectionEnd());
		}
	}
	
	class PopupMenuItemSelectAll extends JMenuItem implements PopupMenuItem {
		private static final long serialVersionUID = -2080797409850944008L;
		
		public PopupMenuItemSelectAll() {
			super();
			
			setAction(new TextAction("Выделить все        ") {
				private static final long serialVersionUID = -2289203573984021765L;

				@Override
				public void actionPerformed(ActionEvent e) {
					textComponent.selectAll();
				}
			});
			
			setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, KeyEvent.CTRL_MASK));
		}
		
		@Override
		public void checkEnabled() {
			setEnabled(!((textComponent.getSelectionStart() == 0) && (textComponent.getSelectionEnd() == textComponent.getText().length())));
		}
	}
}