package ru.nkz.ivcgzo.clientManager.common.swing;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.DefaultCellEditor;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.RowSorterEvent;
import javax.swing.event.RowSorterEvent.Type;
import javax.swing.event.RowSorterListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.thrift.TBase;
import org.apache.thrift.TFieldIdEnum;

import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;

/**
 * Параметризованный класс для работы с таблицами swing. В качестве параметра
 * должна указываться thrift-структура. На основе этой структуры автоматически
 * сгенерируется модель. Можно менять порядок отображения полей или скрывать их.
 * Позволяет редактировать данные после регистрации слушателей на эти изменения.
 * Поддерживает вертикальную и горизонтальную сортировку.
 * @author bsv798
 *
 * @param <T> - thrift-структура
 */
public class CustomTable<T extends TBase<?, F>, F extends TFieldIdEnum> extends JTable {
	private static final long serialVersionUID = -1276277898761960753L;
	private final Class<T> cls;
	private final F[] thrFields;
	private final int colCount;
	private final String[] colNames;
	private final Class<?>[] colTypes;
	private final int[] colOrder;
	private final int[] colIdx;
	private boolean editable;
	private boolean editableCols[];
	private final boolean sortable;
	private List<T> lst;
	private T cop, sel;
	private int copIdx;
	
	private CustomTableItemChangeEventListener<T> delSelRowLst;
	private CustomTableItemChangeEventListener<T> updSelRowLst;
	private CustomTableItemChangeEventListener<T> addRowLst;
	private boolean itemUpd;
	private boolean itemAdd;
	protected boolean sorting;
	
	/**
	 * Конструктор таблицы.
	 * @param editable - можно ли редактировать таблицу. Для работы необходима
	 * регистрация слушателей функциями {@link #registerDeleteSelectedRowListener(CustomTableItemChangeEventListener)},
	 * {@link #registerAddRowListener(CustomTableItemChangeEventListener)} и {@link #registerUpdateSelectedRowListener(CustomTableItemChangeEventListener)}
	 * @param sortable - можно ли сортировать таблицу. Влияет только на вертикальную
	 * сортировку
	 * @param thrCls - класс thrift-структуры, указанной в качестве при параметризации.
	 * Типы полей, полученные из этого класса можно переопределить на <code>Date</code>
	 * методом {@link #setDateField(int)} для редактирования дат и на <code>TableComboBoxIntegerEditor</code>
	 * методом {@link #setIntegerClassifierSelector(int, List)} для отображения/редатирования
	 * классификаторов в виде выпадающего списка
	 * @param fldIdName - чередующиеся пары значений типа <code>Integer</code> и
	 * <code>String</code>, где первое значение - это индекс поля в массиве
	 * <b>thrFlds</b>, а второе - название поля, отображаемое в заголовке
	 * @see TableDateEditor
	 * @see TableComboBoxIntegerEditor
	 */
	public CustomTable(boolean editable, boolean sortable, Class<T> thrCls, Object... fldIdName) {
		this.editable = editable;
		this.sortable = sortable;
		this.cls = thrCls;
		this.thrFields = getThriftFields(thrCls);
		colCount = fldIdName.length / 2;
		colNames = new String[colCount];
		colTypes = new Class<?>[colCount];
		colOrder = new int[colCount];
		colIdx = new int[colCount];
		editableCols = new boolean[colCount];
		for (int i = 0; i < colCount; i++)
			colOrder[i] = i;
		Field[] fld = thrCls.getFields();
		for (int i = 0; i < colCount; i++) {
			colIdx[i] = (int)fldIdName[i * 2];
			colTypes[i] = getClassFromField(fld[colIdx[i]]);
			colNames[i] = (String)fldIdName[i * 2 + 1];
			editableCols[i] = editable;
		}
		
		setModel();
		
		TableNumberEditor tne = new TableNumberEditor();
		this.setDefaultRenderer(Double.class, tne.getRenderer());
		
		TableDateEditor tde = new TableDateEditor();
		this.setDefaultRenderer(Date.class, tde.getRenderer());
		
		TableTimeEditor tte = new TableTimeEditor();
		this.setDefaultRenderer(Time.class, tte.getRenderer());
		
		if (editable) {
			this.setDefaultEditor(Object.class, new CustomTableDefaultEditor());
			
			this.setDefaultEditor(Number.class, tne);
			
			this.setDefaultEditor(Date.class, tde);
			
			this.setDefaultEditor(Time.class, tte);
			
			this.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if (!e.getValueIsAdjusting() && !sorting) {
						if (getSelectedRow() > -1)
							updateSelectedItem();
						sel = getSelectedItem();
						cop = getDeepCopySelectedItem();
					} else if (!e.getValueIsAdjusting() && sorting) {
						sorting = false;
					}
				}
			});
			
		    this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, KeyEvent.CTRL_DOWN_MASK), "delSelRow");
		    this.getActionMap().put("delSelRow", new AbstractAction() {
				private static final long serialVersionUID = 8883313793725297972L;
				@Override
				public void actionPerformed(ActionEvent e) {
					if (!isEditing())
		                deleteSelectedRow();
		        }
		    });
		    
		    this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "updSelRowForward");
		    this.getActionMap().put("updSelRowForward", new AbstractAction() {
				private static final long serialVersionUID = -626232829510479827L;
				@Override
				public void actionPerformed(ActionEvent e) {
					int selRow = getSelectedRow();
					int selCol = getSelectedColumn();
					
					if (lst.size() > 0) {
						if (isEditing()) {
							if (!getCellEditor().stopCellEditing())
								return;
						} else {
							while (selCol < getColumnCount()) {
								if (isCellEditable(selRow, selCol)) {
									changeSelection(selRow, selCol, false, false);
									editCellAt(selRow, selCol, null);
									return;
								}
								selCol++;
							}
						}
						
						while (++selCol < getColumnCount()) {
							if (isCellEditable(selRow, selCol))
								break;
						}
						
						if (selCol == getColumnCount()) {
							++selRow;
							if (selRow == getRowCount())
								addItem();
							else {
								selCol = 0;
								while (selCol < getColumnCount()) {
									if (isCellEditable(selRow, selCol))
										break;
									selCol++;
								}
								updateSelectedItem();
								changeSelection(selRow - 1, selCol, false, false);
							}
						} else {
							changeSelection(selRow, selCol, false, false);
							editCellAt(selRow, selCol, null);
						}
					} else
						addItem();
				}
			});
		    
		    this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, KeyEvent.SHIFT_DOWN_MASK), "updSelRowBack");
		    this.getActionMap().put("updSelRowBack", new AbstractAction() {
				private static final long serialVersionUID = -626232829510479827L;
				@Override
				public void actionPerformed(ActionEvent e) {
					int selRow = getSelectedRow();
					int selCol = getSelectedColumn();
					
					if (lst.size() > 0) {
						if (isEditing()) {
							if (!getCellEditor().stopCellEditing())
								return;
						} else {
							while (selCol > -1) {
								if (isCellEditable(selRow, selCol)) {
									changeSelection(selRow, selCol, false, false);
									editCellAt(selRow, selCol, null);
									return;
								}
								selCol--;
							}
						}
						
						while (--selCol > -1) {
							if (isCellEditable(selRow, selCol))
								break;
						}
						
						if (selCol > -1) {
							changeSelection(selRow, selCol, false, false);
							editCellAt(selRow, selCol, null);
						}
					}
				}
			});
		    
		    this.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "cancelSelRow");
		    this.getActionMap().put("cancelSelRow", new AbstractAction() {
				private static final long serialVersionUID = 8883313793725297972L;
				@Override
				public void actionPerformed(ActionEvent e) {
					cancelEdit();
		        }
		    });
		}
		
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setAutoCreateColumnsFromModel(false);
		this.createDefaultColumnsFromModel();
	}
	
	private F[] getThriftFields(Class<T> cls) {
		try {
			T thrObj = cls.newInstance();
			int objFldCnt = cls.getFields().length - 1;
			@SuppressWarnings("unchecked")
			F[] thrFlds = (F[]) new TFieldIdEnum[objFldCnt];
			for (int i = 0; i < objFldCnt; i++)
				thrFlds[i] = thrObj.fieldForId(i + 1);
			return thrFlds;
		} catch (Exception e) {
			throw new RuntimeException();
		}
	}
	
	/**
	 * Получает класс из класса) К сожалению, в Java'е <code>int.class</code>
	 * и <code>Integer.class</code> - это разные вещи.
	 */
	private Class<?> getClassFromField(Field fld) {
		Class<?> typ = fld.getType();
		if (typ == int.class)
			return Integer.class;
		else if (typ == long.class)
			return Long.class;
		else if (typ == short.class)
			return Short.class;
		else if (typ == boolean.class)
			return Boolean.class;
		else if (typ == float.class)
			return Float.class;
		else if (typ == double.class)
			return Double.class;
		else
			return typ;
	}
	
	/**
	 * При сборке модели на основе класса T порядок полей модели соответствует
	 * порядку полей класса. Этот метод позволяет переопределять порядок.
	 * @param idx - набор индексов. Например, набор <code>(1, 0)</code> означает,
	 * что первое и второе поле поменяются местами.
	 */
	public void sortColumns(int... idx) {
		for (int i = 0; i < colCount; i++)
			colOrder[i] = idx[i];
		setModel();
	}
	
	/**
	 * Генерирует на основе списка <code>JComboBox</code> и заменяет им
	 * указанный столбец. Номер столбца должен указываться без учета сортировки
	 * методом {@link #sortColumns(int...)}.
	 * @param colIdx
	 * @param lst
	 */
	public void setStringClassifierSelector(int colIdx, final List<StringClassifier> lst) {
		for (int i = 0; i < colCount; i++)
			if (colOrder[i] == colIdx) {
				colIdx = i;
				break;
			}
		TableComboBoxStringEditor edt = new TableComboBoxStringEditor(null, true, lst);
		this.getColumnModel().getColumn(colIdx).setCellEditor(edt);
		this.getColumnModel().getColumn(colIdx).setCellRenderer(edt.getRender());
	}
	
	/**
	 * Генерирует на основе списка <code>JComboBox</code> и заменяет им
	 * указанный столбец. Номер столбца должен указываться без учета сортировки
	 * методом {@link #sortColumns(int...)}.
	 * @param colIdx
	 * @param className
	 */
	public void setStringClassifierSelector(int colIdx, StringClassifiers className) {
		for (int i = 0; i < colCount; i++)
			if (colOrder[i] == colIdx) {
				colIdx = i;
				break;
			}
		TableComboBoxStringEditor edt = new TableComboBoxStringEditor(className, true, null);
		this.getColumnModel().getColumn(colIdx).setCellEditor(edt);
		this.getColumnModel().getColumn(colIdx).setCellRenderer(edt.getRender());
	}
	
	/**
	 * Генерирует на основе списка <code>JComboBox</code> и заменяет им
	 * указанный столбец. Номер столбца должен указываться без учета сортировки
	 * методом {@link #sortColumns(int...)}.
	 * @param colIdx
	 * @param lst
	 */
	public void setIntegerClassifierSelector(int colIdx, final List<IntegerClassifier> lst) {
		for (int i = 0; i < colCount; i++)
			if (colOrder[i] == colIdx) {
				colIdx = i;
				break;
			}
		TableComboBoxIntegerEditor edt = new TableComboBoxIntegerEditor(null, true, lst);
		this.getColumnModel().getColumn(colIdx).setCellEditor(edt);
		this.getColumnModel().getColumn(colIdx).setCellRenderer(edt.getRender());
	}
	
	/**
	 * Генерирует на основе списка <code>JComboBox</code> и заменяет им
	 * указанный столбец. Номер столбца должен указываться без учета сортировки
	 * методом {@link #sortColumns(int...)}.
	 * @param colIdx
	 * @param className
	 */
	public void setIntegerClassifierSelector(int colIdx, IntegerClassifiers className) {
		for (int i = 0; i < colCount; i++)
			if (colOrder[i] == colIdx) {
				colIdx = i;
				break;
			}
		TableComboBoxIntegerEditor edt = new TableComboBoxIntegerEditor(className, true, null);
		this.getColumnModel().getColumn(colIdx).setCellEditor(edt);
		this.getColumnModel().getColumn(colIdx).setCellRenderer(edt.getRender());
	}
	
	/**
	 * Устанавливает выбранному полю тип <code>Date.class</code> для
	 * корректного отображения и редактирования дат.
	 * @param colIdx
	 * @see TableDateEditor
	 */
	public void setDateField(int colIdx) {
		colTypes[colOrder[colIdx]] = Date.class;
	}
	
	/**
	 * Устанавливает выбранному полю тип <code>Time.class</code> для
	 * корректного отображения и редактирования времени.
	 * @param colIdx
	 * @see TableDateEditor
	 */
	public void setTimeField(int colIdx) {
		colTypes[colOrder[colIdx]] = Time.class;
	}
	
	/**
	 * Определяет, какие поля можно изменять. Переопределяет настройку <b>editable</b> для
	 * всей таблицы, указанную в конструкторе.
	 * @param value - доступность для изменения указанных далее полей
	 * @param idx - индексы полей
	 */
	public void setEditableFields(boolean value, int...idx) {
		int i;
		
		for (i = 0; i < idx.length; i++)
			editableCols[idx[i]] = value;
		
		editable = false;
		for (i = 0; i < colCount; i++)
			editable |= editableCols[i];
	}
	
	/**
	 * Возвращает фактический индекс строки в сортированной таблице.
	 */
	public int getSortedRowIndex() {
		if (this.getSelectedRow() > -1)
			if (sortable)
				return this.convertRowIndexToModel(this.getSelectedRow());
			else
				return this.getSelectedRow();
		else
			return -1;
	}
	
	/**
	 * Возвращает текущую строку.
	 */
	public T getSelectedItem() {
			if (this.getSelectedRow() > -1)
				return lst.get(getSortedRowIndex());
			else
				return null;
	}
	
	public void replaceSelectedItem(T item) {
		if (this.getSelectedRow() > -1)
			lst.set(getSortedRowIndex(), item);
	}
	
	/**
	 * Доступна ли таблица для редактирования.
	 */
	public boolean isEditable() {
		return editable;
	}
	
	/**
	 * Создает глубокую копию активной строки. Копия будет использоваться для отмены
	 * изменений строки в случае ошибки синхронизации с сервером.
	 */
	private T getDeepCopySelectedItem() {
		return getDeepCopyItem(getSelectedItem());
	}
	
	private T getDeepCopyItem(T item) {
		T copy;
		
		if (item != null) {
			try {
				F thrFld;
				copy = cls.newInstance();
				for (int i = 0; i < thrFields.length; i++) {
					thrFld = thrFields[i];
					if (item.isSet(thrFld))
						copy.setFieldValue(thrFld, item.getFieldValue(thrFld));
				}
				copIdx = getSortedRowIndex();
				return copy;
			} catch (Exception e) {
				return null;
			}
		} else
			return null;
	}
	
	/**
	 * Сравнивает две строки на соответствие друг другу.
	 */
	private boolean deepEquals(T i1, T i2) {
		if (i1 == null && i2 == null)
			return true;
		else if (i1 == null || i2 == null)
			return false;
		else {
			F thrFld;
			for (int i = 0; i < thrFields.length; i++) {
				thrFld = thrFields[i];
				if (i1.isSet(thrFld) != i2.isSet(thrFld))
					return false;
				if (i1.isSet(thrFld))
					if (!i1.getFieldValue(thrFld).equals(i2.getFieldValue(thrFld)))
						return false;
			}
		}
		return true;
	}
	
	/**
	 * Проверяет, пуста ли строка.
	 */
	private boolean checkEmpty(T item) {
		if (item == null)
			return true;
		else {
			boolean res = false;
			for (int i = 0; i < colCount; i++)
				res |= item.isSet(thrFields[colIdx[colOrder[i]]]);
			return !res;
		}
	}
	
	/**
	 * Обновляет данные в таблице.
	 */
	public void setData(List<T> list) {
		cop = null;
		updateSelectedItem();
		
		lst = list;
		getModel().fireTableDataChanged();
		if (lst.size() > 0)
			changeSelection(0, 0, false, false);
	}
	
	/**
	 * Получает список данных. 
	 */
	public List<T> getData() {
		return lst;
	}
	
	/**
	 * Генерирует модель.
	 */
	private void setModel() {
		lst = new ArrayList<>();
		this.setModel(new CustomTableModel());

		if (sortable) {
			this.setRowSorter(new TableRowSorter<>((TableModel)this.getModel()));
			this.getRowSorter().addRowSorterListener(new RowSorterListener() {
				
				@Override
				public void sorterChanged(RowSorterEvent e) {
					if (e.getType() == Type.SORT_ORDER_CHANGED)
						sorting = true;
				}
			});
		}
	}

	@Override
	public AbstractTableModel getModel() {
		return (AbstractTableModel) super.getModel();
	}
	
	@Override
	protected boolean processKeyBinding(KeyStroke ks, KeyEvent e, int condition, boolean pressed) {
		if (pressed && (e.getKeyCode() == KeyEvent.VK_ENTER)) {
			editForward();
			return true;
		} else if (pressed && (e.getKeyCode() == KeyEvent.VK_TAB)) {
			if (CustomTable.this.isEditing() && pressed) {
				if ((ks.getModifiers() & KeyEvent.SHIFT_DOWN_MASK) == KeyEvent.SHIFT_DOWN_MASK) {
					editBackwards();
					return true;
				} else if (ks.getModifiers() == 0) {
					editForward();
					return true;
				} else {
					return super.processKeyBinding(ks, e, condition, pressed);
				}
			}
		} else if (!CustomTable.this.isEditing()) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_ESCAPE:
			case KeyEvent.VK_F2:
			case KeyEvent.VK_DELETE:
			case KeyEvent.VK_TAB:
			case KeyEvent.VK_LEFT:
			case KeyEvent.VK_RIGHT:
			case KeyEvent.VK_UP:
			case KeyEvent.VK_DOWN:
			case KeyEvent.VK_PAGE_UP:
			case KeyEvent.VK_PAGE_DOWN:
			case KeyEvent.VK_HOME:
			case KeyEvent.VK_END:
				return super.processKeyBinding(ks, e, condition, pressed);
			default:
				return true;
			}
		}
		
		return super.processKeyBinding(ks, e, condition, pressed);
	}
	
	private void editBackwards() {
		int selRow = getSelectedRow();
		int selCol = getSelectedColumn();
		
		if (lst.size() > 0) {
			if (isEditing()) {
				if (!getCellEditor().stopCellEditing())
					return;
			} else {
				while (selCol > -1) {
					if (isCellEditable(selRow, selCol)) {
						changeSelection(selRow, selCol, false, false);
						if (editCellAt(selRow, selCol, null))
							getEditorComponent().requestFocusInWindow();
						return;
					}
					selCol--;
				}
			}
			
			while (--selCol > -1) {
				if (isCellEditable(selRow, selCol))
					break;
			}
			
			if (selCol > -1) {
				changeSelection(selRow, selCol, false, false);
				if (editCellAt(selRow, selCol, null))
					getEditorComponent().requestFocusInWindow();
			}
		}
	}

	private void editForward() {
		int selRow = getSelectedRow();
		int selCol = getSelectedColumn();
		
		if (lst.size() > 0) {
			if (isEditing()) {
				if (!getCellEditor().stopCellEditing())
					return;
			} else {
				while (selCol < getColumnCount()) {
					if (isCellEditable(selRow, selCol)) {
						changeSelection(selRow, selCol, false, false);
						if (editCellAt(selRow, selCol, null))
							getEditorComponent().requestFocusInWindow();
						return;
					}
					selCol++;
				}
			}
			
			while (++selCol < getColumnCount()) {
				if (isCellEditable(selRow, selCol))
					break;
			}
			
			if (selCol == getColumnCount()) {
				++selRow;
				if (selRow == getRowCount())
					addItem();
				else {
					selCol = 0;
					while (selCol < getColumnCount()) {
						if (isCellEditable(selRow, selCol))
							break;
						selCol++;
					}
					updateSelectedItem();
					changeSelection(selRow, selCol, false, false);
				}
			} else {
				changeSelection(selRow, selCol, false, false);
				if (editCellAt(selRow, selCol, null))
					getEditorComponent().requestFocusInWindow();
			}
		} else {
			if (editable)
				addItem();
		}
	}

	/**
	 * Регистрация слушателя, принимающего сообщения о том, что текущая строка
	 * будет удалена.
	 * @param listener
	 */
	public void registerDeleteSelectedRowListener(CustomTableItemChangeEventListener<T> listener) {
		delSelRowLst = listener;
	}
	
	/**
	 * Удаление текущей строки. Перед удалением слушателю будет послано сообщение.
	 */
	public void deleteSelectedRow() {
		if (editable) {
			if (lst.size() == 0)
				return;
			
			if (isEditing())
				getCellEditor().cancelCellEditing();
			
			int row = getSelectedRow();
			int col = getSelectedColumn();
			if (!itemAdd) {
				boolean res = (delSelRowLst != null) ? delSelRowLst.doAction(new CustomTableItemChangeEvent<>(this, sel)) : true;
				if (res) {
					lst.remove(copIdx);
					itemUpd = false;
					updateSelectedIndex(row, col, copIdx, 0);
				}
			} else {
				lst.remove(copIdx);
				itemAdd = false;
				updateSelectedIndex(row, col, copIdx, 0);
			}
		}
	}
	
	/**
	 * Устанавливает курсор в зависимости от операции и вызывает соответствующие
	 * события.
	 * @param selRow - обзорный индекс обновляемой строки
	 * @param selCol - обзорный индекс обновляемого столбца
	 * @param updRow - модельный индекс обновляемой строки
	 * @param act - идентификатор операции
	 */
	private void updateSelectedIndex(int selRow, int selCol, int updRow, int act) {
		switch (act) {
		case 0: //delete
			if (selRow == lst.size()) {
				updRow = lst.size();
				selRow = updRow - 1;
			}
			getSelectionModel().setValueIsAdjusting(true);
			getModel().fireTableRowsDeleted(updRow, updRow);
			this.changeSelection(selRow, selCol, false, false);
			getSelectionModel().setValueIsAdjusting(false);
			break;
		case 1: //add
			if (selRow == lst.size()) {
				updRow = lst.size() - 1;
				selRow = updRow;
			}
			getSelectionModel().setValueIsAdjusting(true);
			getModel().fireTableRowsInserted(updRow, updRow);
			selRow = convertRowIndexToView(selRow);
			this.changeSelection(selRow, 0, false, false);
			getSelectionModel().setValueIsAdjusting(false);
			this.editCellAt(selRow, 0);
			this.getEditorComponent().requestFocusInWindow();
			break;
		case 2: //update
			getSelectionModel().setValueIsAdjusting(true);
			getModel().fireTableRowsUpdated(updRow, updRow);
			this.changeSelection(selRow, selCol, false, false);
			getSelectionModel().setValueIsAdjusting(false);
			break;
		}
	}
	
	/**
	 * Регистрация слушателя, принимающего сообщения о том, что данные текущей
	 * строки изменились.
	 * @param listener
	 */
	public void registerUpdateSelectedRowListener(CustomTableItemChangeEventListener<T> listener) {
		updSelRowLst = listener;
	}
	
	/**
	 * Обновление текущей строки. Обновление происходит при добавлении строки,
	 * переходе на другую строку или обновлении данных
	 * методом {@link #setData(List)}.
	 */
	public void updateSelectedItem() {
		if (isEditing())
			this.getCellEditor().stopCellEditing();
		
		if (itemUpd) {
			itemUpd = false;
			if (!itemAdd) {
				if (!deepEquals(sel, cop)) {
					boolean res = (updSelRowLst != null) ? updSelRowLst.doAction(new CustomTableItemChangeEvent<>(this, sel)) : true;
					if (!res)
						lst.set(copIdx, cop);
				}
				updateSelectedIndex(getSelectedRow(), getSelectedColumn(), copIdx, 2);
			} else {
				if (!checkEmpty(sel)) {
					if (addRowLst != null)
						addRowLst.doAction(new CustomTableItemChangeEvent<>(this, sel));
					else
						updateSelectedIndex(getSelectedRow(), getSelectedColumn(), copIdx, 2);
				} else {
					deleteSelectedRow();
				}
				itemAdd = false;
			}
		}
	}
	
	/**
	 * Обновление текущей строки, измененной <b>программно</b>.
	 */
	public void updateChangedSelectedItem() {
		itemUpd = true;
		updateSelectedItem();
	}
	
	/**
	 * Регистрация слушателя, принимающего сообщения о том, что произошло
	 * добавление новой строки.
	 * @param listener
	 */
	public void registerAddRowListener(CustomTableItemChangeEventListener<T> listener) {
		addRowLst = listener;
	}
	
	/**
	 * Добавление новой строки.
	 */
	public void addItem() {
		addItem(null);
	}
	
	/**
	 * Добавление новой строки.
	 */
	public void addItem(T item) {
		addItem(lst.size(), item);
	}
	
	/**
	 * Добавление новой строки.
	 */
	public void addItem(int idx, T item) {
		if (itemAdd && !itemUpd)
			deleteSelectedRow();
		else if (itemAdd)
			updateSelectedItem();
		
		try {
			if (item == null)
				lst.add(idx, cls.newInstance());
			else
				lst.add(idx, item);
			updateSelectedIndex(idx, getSelectedColumn(), idx, 1);
			itemUpd = false;
			itemAdd = true;
		} catch (Exception e) {
		}
	}
	
	/**
	 * Добавление новой строки. Строку можно изменять вне таблицы. Изменения подтверждать
	 * методом {@link #updateSelectedItem()}.
	 */
	public T addExternalItem() {
		addItem();
		
		if (isEditing())
			this.getCellEditor().stopCellEditing();
		
		return getSelectedItem();
	}
	
	/**
	 * Установка ширин столбцов и, как следствие, выключение автоматического ресайзинга.
	 * @param wdt - массив ширин столбцов. Должен совпадать по размеру с количеством столбцов.
	 */
	public void setPreferredWidths(int... wdt) {
		if (wdt.length != colOrder.length)
			throw new RuntimeException("Width count doesn't match column count");
		
		this.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		for (int i = 0; i < colOrder.length; i++) {
			this.getColumnModel().getColumn(colOrder[i]).setPreferredWidth(wdt[i]);
		}
	}
	
	/**
	 * Отменяет изменения в текущей строке.
	 */
	public void cancelEdit() {
		if (getSelectedRow() < 0)
			return;
		
		if (isEditing()) {
			getCellEditor().cancelCellEditing();
			return;
		}
		
		itemUpd = true;
		if (itemAdd) {
			sel = null;
		} else {
			sel = getDeepCopyItem(cop);
			itemAdd = false;
		}
		lst.set(copIdx, sel);
		updateSelectedItem();
	}

	public class CustomTableModel extends AbstractTableModel {
		private static final long serialVersionUID = -7716830847522898209L;

		@Override
		public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
			try {
				if (lst.get(rowIndex) != null)
					if (aValue != null)
						if (aValue.toString().length() == 0)
							lst.get(rowIndex).setFieldValue(thrFields[colIdx[colOrder[columnIndex]]], null);
						else if (colTypes[colOrder[columnIndex]] == Date.class || colTypes[colOrder[columnIndex]] == Time.class)
							lst.get(rowIndex).setFieldValue(thrFields[colIdx[colOrder[columnIndex]]], ((Date)aValue).getTime());
						else
							lst.get(rowIndex).setFieldValue(thrFields[colIdx[colOrder[columnIndex]]], aValue);
					else
						lst.get(rowIndex).setFieldValue(thrFields[colIdx[colOrder[columnIndex]]], null);
				itemUpd = true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			return editableCols[colOrder[columnIndex]];
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			try {
				if (lst.get(rowIndex).isSet(thrFields[colIdx[colOrder[columnIndex]]]))
					if (colTypes[colOrder[columnIndex]] == Date.class || colTypes[colOrder[columnIndex]] == Time.class)
						return new Date((long)lst.get(rowIndex).getFieldValue(thrFields[colIdx[colOrder[columnIndex]]]));
					else
						return lst.get(rowIndex).getFieldValue(thrFields[colIdx[colOrder[columnIndex]]]);
				else
					return null;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		
		@Override
		public int getRowCount() {
			return lst.size();
		}
		
		@Override
		public String getColumnName(int columnIndex) {
			return colNames[colOrder[columnIndex]];
		}
		
		@Override
		public int getColumnCount() {
			return colCount;
		}
		
		@Override
		public Class<?> getColumnClass(int columnIndex) {
			return colTypes[colOrder[columnIndex]];
		}
		
	}
	
	private class CustomTableDefaultEditor extends DefaultCellEditor {
		private static final long serialVersionUID = 8972780790646236150L;
		private CustomTextField ctf;

		public CustomTableDefaultEditor() {
			super(new CustomTextField());
			
			ctf = (CustomTextField) getComponent();
			
			setActionPerformed();
			setFocusListener();
		}
		
		private void setActionPerformed() {
			ctf.setBorder(new LineBorder(Color.black));
			ctf.removeActionListener(delegate);
			ctf.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					CustomTable.this.dispatchEvent(new KeyEvent(CustomTable.this, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_ENTER, KeyEvent.CHAR_UNDEFINED));
				}
			});
			
		}
		
		private void setFocusListener() {
			ctf.addFocusListener(new FocusAdapter() {
				@Override
				public void focusLost(FocusEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							if (!CustomTable.this.hasFocus())
								stopCellEditing();
						}
					});
				}
			});
		}
	}
}

class Time {
	
}
