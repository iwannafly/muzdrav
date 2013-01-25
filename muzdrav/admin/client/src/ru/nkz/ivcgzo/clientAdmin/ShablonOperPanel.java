package ru.nkz.ivcgzo.clientAdmin;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextComponentWrapper;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTimeEditor;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.thriftCommon.classifier.ClassifierSortFields;
import ru.nkz.ivcgzo.thriftCommon.classifier.ClassifierSortOrder;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftServerAdmin.ShablonOper;
import ru.nkz.ivcgzo.thriftServerAdmin.TemplateExistsException;

public class ShablonOperPanel extends JPanel {
	private static final long serialVersionUID = -181023590802237403L;
	private CustomTextField tbSearch;
	private ButtonGroup bgStatType;
	private JRadioButton rbStat;
	private JRadioButton rbPol;
	private SearchList lbSearch;
	private JButton btDelete;
	private JButton btCreate;
	private CustomTextField tbName;
	private JScrollPane spText;
	private JTextArea tbText;
	private DocumentListener textListener;
	private JButton btSave;
	private ShablonOper shOper;
	private boolean fillingUI;
	private CustomTextField tbOperCode;
	private CustomTextField tbOperName;
	private CustomTimeEditor tbDur;
	private CustomTextField tbMat;
	
	private List<IntegerClassifier> statList;
	
	public ShablonOperPanel() {
		textListener = new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				checkInput();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				checkInput();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				checkInput();
			}
		};
		
		JSplitPane splitPane = new JSplitPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, GroupLayout.PREFERRED_SIZE, 681, Short.MAX_VALUE)
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(splitPane, GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
		);
		
		JPanel gbSearch = new JPanel();
		gbSearch.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Поиск", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		gbSearch.setMinimumSize(new Dimension(256, 128));
		splitPane.setLeftComponent(gbSearch);
		
		tbSearch = new CustomTextField(true, true, false);
		tbSearch.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				lbSearch.requestUpdate(tbSearch.getText());
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				lbSearch.requestUpdate(tbSearch.getText());
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				lbSearch.requestUpdate(tbSearch.getText());
			}
		});
		tbSearch.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		
		btDelete = new JButton("Удалить");
		btDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (JOptionPane.showConfirmDialog(ShablonOperPanel.this, "Удалить шаблон?", "Подтверждение", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION)
					lbSearch.removeSelected();
			}
		});
		
		btCreate = new JButton("Создать");
		btCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lbSearch.createNew();
				tbName.requestFocusInWindow();
			}
		});
		
		rbStat = new JRadioButton("New radio button");
		rbStat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lbSearch.updateNow(statList.get(0).pcod, tbSearch.getText());
			}
		});
		
		rbPol = new JRadioButton("New radio button");
		rbPol.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				lbSearch.updateNow(statList.get(1).pcod, tbSearch.getText());
			}
		});
		
		bgStatType = new ButtonGroup();
		bgStatType.add(rbStat);
		bgStatType.add(rbPol);
		
		GroupLayout gl_gbSearch = new GroupLayout(gbSearch);
		gl_gbSearch.setHorizontalGroup(
			gl_gbSearch.createParallelGroup(Alignment.LEADING)
				.addComponent(tbSearch, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
				.addGroup(gl_gbSearch.createSequentialGroup()
					.addComponent(btDelete, GroupLayout.PREFERRED_SIZE, 115, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btCreate, GroupLayout.PREFERRED_SIZE, 115, Short.MAX_VALUE))
				.addGroup(gl_gbSearch.createSequentialGroup()
					.addComponent(rbStat, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rbPol, GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE))
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 240, Short.MAX_VALUE)
		);
		gl_gbSearch.setVerticalGroup(
			gl_gbSearch.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_gbSearch.createSequentialGroup()
					.addComponent(tbSearch, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_gbSearch.createParallelGroup(Alignment.BASELINE)
						.addComponent(rbStat)
						.addComponent(rbPol))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_gbSearch.createParallelGroup(Alignment.BASELINE)
						.addComponent(btDelete)
						.addComponent(btCreate)))
		);
		
		lbSearch = new SearchList();
		scrollPane.setViewportView(lbSearch);
		gbSearch.setLayout(gl_gbSearch);
		
		JPanel gbEdit = new JPanel();
		gbEdit.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Редактирование", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		gbEdit.setMinimumSize(new Dimension(512, 128));
		splitPane.setRightComponent(gbEdit);
		
		btSave = new JButton("Сохранить");
		btSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				saveShablon();
			}
		});
		
		tbName = new CustomTextField(true, true, false);
		tbName.getDocument().addDocumentListener(textListener);
		tbName.setColumns(10);
		
		JLabel lbName = new JLabel("Название");
		
		JPanel gbText = new JPanel();
		gbText.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Тексты", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lbOper = new JLabel("Код операци");
		
		tbOperCode = new CustomTextField();
		tbOperCode.setColumns(10);
		tbOperCode.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				fillOperName();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				fillOperName();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				fillOperName();
			};
			
			private void fillOperName() {
				String opName = null;
				
				try {
					if (rbStat.isSelected()) {
						opName = MainForm.conMan.getNameFromPcodString(StringClassifiers.n_ak2, tbOperCode.getText());
					} else {
						opName = MainForm.conMan.getNameFromPcodInteger(IntegerClassifiers.n_akd, Integer.parseInt(tbOperCode.getText()));
					}
				} catch (NumberFormatException e) {
					
				} catch (Exception e) {
					e.printStackTrace();
					JOptionPane.showMessageDialog(ShablonOperPanel.this, "Какая-то ошибка, которой быть не должно.");
				}
				
				tbOperName.setText(opName);
				if (tbName.isEmpty())
					tbName.setText(opName);
			}
		});
		tbOperCode.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					String code = null;
					
					if (rbStat.isSelected()) {
						StringClassifier res = MainForm.conMan.showStringClassifierSelector(StringClassifiers.n_ak2, ClassifierSortOrder.ascending, ClassifierSortFields.pcod);
						
						if (res != null)
							code = res.pcod;
					} else {
						IntegerClassifier res = MainForm.conMan.showIntegerClassifierSelector(IntegerClassifiers.n_akd, ClassifierSortOrder.ascending, ClassifierSortFields.pcod);
						
						if (res != null)
							code = String.valueOf(res.pcod);
					}
					if (code != null)
						tbOperCode.setText(code);
				}
			}
		});
		
		tbOperName = new CustomTextField();
		tbOperName.getDocument().addDocumentListener(textListener);
		tbOperName.setEditable(false);
		tbOperName.setFocusable(false);
		tbOperName.setColumns(10);
		
		JLabel lblDur = new JLabel("Длител. операци");
		
		tbDur = new CustomTimeEditor();
		tbDur.getDocument().addDocumentListener(textListener);
		tbDur.setColumns(10);
		
		JLabel lblMat = new JLabel("Шовный материал");
		
		tbMat = new CustomTextField();
		tbMat.getDocument().addDocumentListener(textListener);
		tbMat.setColumns(10);
		GroupLayout gl_gbEdit = new GroupLayout(gbEdit);
		gl_gbEdit.setHorizontalGroup(
			gl_gbEdit.createParallelGroup(Alignment.TRAILING)
				.addComponent(btSave, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
				.addGroup(gl_gbEdit.createSequentialGroup()
					.addGroup(gl_gbEdit.createParallelGroup(Alignment.TRAILING)
						.addComponent(gbText, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
						.addGroup(Alignment.LEADING, gl_gbEdit.createSequentialGroup()
							.addComponent(lblDur, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tbDur, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblMat, GroupLayout.PREFERRED_SIZE, 102, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(tbMat, GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
						.addGroup(Alignment.LEADING, gl_gbEdit.createSequentialGroup()
							.addGroup(gl_gbEdit.createParallelGroup(Alignment.LEADING, false)
								.addComponent(lbOper, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(lbName, GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_gbEdit.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_gbEdit.createSequentialGroup()
									.addComponent(tbOperCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tbOperName, GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE))
								.addComponent(tbName, GroupLayout.DEFAULT_SIZE, 293, Short.MAX_VALUE))))
					.addGap(0))
		);
		gl_gbEdit.setVerticalGroup(
			gl_gbEdit.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_gbEdit.createSequentialGroup()
					.addGroup(gl_gbEdit.createParallelGroup(Alignment.BASELINE)
						.addComponent(tbName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lbName))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_gbEdit.createParallelGroup(Alignment.BASELINE)
						.addComponent(lbOper)
						.addComponent(tbOperName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(tbOperCode, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_gbEdit.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDur)
						.addComponent(tbDur, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMat)
						.addComponent(tbMat, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(gbText, GroupLayout.DEFAULT_SIZE, 146, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btSave))
		);
		
		spText = new JScrollPane();
		GroupLayout gl_gbText = new GroupLayout(gbText);
		gl_gbText.setHorizontalGroup(
			gl_gbText.createParallelGroup(Alignment.LEADING)
				.addComponent(spText, GroupLayout.DEFAULT_SIZE, 534, Short.MAX_VALUE)
		);
		gl_gbText.setVerticalGroup(
			gl_gbText.createParallelGroup(Alignment.LEADING)
				.addComponent(spText, GroupLayout.DEFAULT_SIZE, 244, Short.MAX_VALUE)
		);
		
		tbText = new JTextArea();
		tbText.getDocument().addDocumentListener(textListener);
		tbText.setFont(new Font("Tahoma", Font.PLAIN, 12));
		new CustomTextComponentWrapper(tbText).setPopupMenu();
		spText.setViewportView(tbText);
		gbText.setLayout(gl_gbText);
		
		gbEdit.setLayout(gl_gbEdit);
		setLayout(groupLayout);
	}
	
	public void prepareShTextFields() {
		try {
			clearFields();
			loadStatList();
			
			lbSearch.updateNow((rbStat.isSelected()) ? statList.get(0).pcod : statList.get(1).pcod, tbSearch.getText());
		} catch (KmiacServerException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Ошибка инициализации шаблонов.");
		} catch (TException e) {
			e.printStackTrace();
			MainForm.conMan.reconnect(e);
		}
	}
	
	private void loadStatList() throws KmiacServerException, TException {
		if (statList == null) {
			statList = MainForm.tcl.getShOperStatList();
			rbStat.setText(statList.get(0).name);
			rbPol.setText(statList.get(1).name);
			bgStatType.clearSelection();
			rbStat.setSelected(true);
		}
	}
	
	private void clearFields() {
		tbName.clear();
		tbOperCode.clear();
		tbDur.setText(null);
		tbMat.setText(null);
		tbText.setText(null);
		
		btSave.setEnabled(false);
		btDelete.setEnabled(false);
	}
	
	private void saveShablon() {
		fillShablonFromUI();
		
		try {
			shOper.setId(MainForm.tcl.saveShOper(shOper));
			lbSearch.updateSavedNode();
		} catch (TemplateExistsException e) {
			JOptionPane.showMessageDialog(ShablonOperPanel.this, "Шаблон с таким именем уже существует для данного раздела.", "Ошибка", JOptionPane.ERROR_MESSAGE);
			tbName.requestFocusInWindow();
		} catch (KmiacServerException e) { //FIXME исключение не пробрасывается из-за ошибки в трифте. Если функция возвращает простой тип, то проверка на успешность выполнения проходится раньше, чем на исключение.
			JOptionPane.showMessageDialog(ShablonOperPanel.this, "Ошибка сохранения шаблона.", "Ошибка", JOptionPane.ERROR_MESSAGE);
		} catch (TException e) {
			MainForm.conMan.reconnect(e);
		}
	}
	
	private void fillShablonFromUI() {
		shOper.setStat_tip((rbStat.isSelected()) ? statList.get(0).pcod : statList.get(1).pcod);
		shOper.setName(tbName.getText());
		shOper.setOper_pcod(tbOperCode.getText());
		shOper.setOper_dlit(tbDur.getTime().getTime());
		shOper.setMat(tbMat.getText());
		shOper.setText(tbText.getText());
	}
	
	private void checkInput() {
		if (fillingUI)
			return;
		
		boolean enb = true;
		
		enb &= !tbName.isEmpty();
		enb &= !tbOperCode.isEmpty();
		enb &= !tbOperName.isEmpty();
		enb &= tbDur.getTime() != null;
		enb &= !(tbText.getText().length() == 0);
		enb &= lbSearch.getSelectedIndex() > -1;
		
		btSave.setEnabled(enb);
	}
	
	private void loadShablon(int id) {
		try {
			shOper = MainForm.tcl.getShOper(id);
			fillUIFromShablon();
		} catch (KmiacServerException e) {
			JOptionPane.showMessageDialog(ShablonOperPanel.this, "Ошибка загрузки шаблона.", "Ошибка", JOptionPane.ERROR_MESSAGE);
		} catch (TException e) {
			MainForm.conMan.reconnect(e);
		}
	}
	
	private void fillUIFromShablon() {
		fillingUI = true;
		
		try {
			tbName.setText(shOper.name);
			tbName.setCaretPosition(0);
			tbOperCode.setText(shOper.oper_pcod);
			tbDur.setTime(shOper.oper_dlit);
			tbMat.setText(shOper.mat);
			tbMat.setCaretPosition(0);
			tbText.setText(shOper.text);
			tbText.setCaretPosition(0);
			
			fillingUI = false;
			btDelete.setEnabled(true);
			checkInput();
		} finally {
			fillingUI = false;
		}
	}
	
	private class SearchList extends ThriftIntegerClassifierList {
		private static final long serialVersionUID = 5243243248510889657L;
		private Timer timer;
		private int statType;
		private String srcStr;
		private IntegerClassifier newNode;
		private boolean changingNodes;
		private int newNodeIdx;
		
		public SearchList() {
			setSelectionChange();
			setTimer();
		}
		
		private void setSelectionChange() {
			getSelectionModel().addListSelectionListener(new ListSelectionListener() {
				
				@Override
				public void valueChanged(ListSelectionEvent e) {
					if (!e.getValueIsAdjusting() && !changingNodes) {
						if (newNode != null) {
								clearFields();
								btDelete.setEnabled(true);
						} else {
							changingNodes = false;
							if (getSelectedValue() != null) {
								newNodeIdx = getSelectedIndex();
								loadShablon(getSelectedPcod());
							}
						}
					} else if (!e.getValueIsAdjusting()) {
						removeUnsavedNode(false);
						if (getSelectedValue() != null) {
							newNodeIdx = getSelectedIndex();
							loadShablon(getSelectedPcod());
						}
					}
				}
			});
		}
		
		private void setTimer() {
			timer = new Timer(500, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					update();
				}
			});
		}
		
		public void requestUpdate(String srcStr) {
			timer.stop();
			this.srcStr = srcStr;
			timer.start();
		}
		
		public void updateNow(int statType, String srcStr) {
			this.statType = statType;
			this.srcStr = srcStr;
			update();
		}
		
		private void update() {
			timer.stop();
			newNode = null;
			clearFields();
			
			if (srcStr.length() < 3)
				srcStr = null;
			else
				srcStr = '%' + srcStr + '%';
			
			try {
				setData(MainForm.tcl.getShOperList(statType, srcStr));
			} catch (KmiacServerException e) {
				JOptionPane.showMessageDialog(ShablonOperPanel.this, "Ошибка загрузки результатов поиска.", "Ошибка", JOptionPane.ERROR_MESSAGE);
			} catch (TException e) {
				e.printStackTrace();
				MainForm.conMan.reconnect(e);
			}
		}
		
		public void removeSelected() {
			if (getSelectedIndex() > -1) {
				try {
					if (shOper.id > 0) {
						MainForm.tcl.deleteShOper(shOper.id);
						newNode = new IntegerClassifier();
						changingNodes = true;
					}
					
					removeUnsavedNode(true);
				} catch (KmiacServerException e) {
					JOptionPane.showMessageDialog(ShablonOperPanel.this, "Ошибка удаления шаблона.", "Ошибка", JOptionPane.ERROR_MESSAGE);
				} catch (TException e) {
					e.printStackTrace();
					MainForm.conMan.reconnect(e);
				}
			}
		}
		
		public void createNew() {
			removeUnsavedNode(false);
			
			shOper = new ShablonOper().setName("Без названия");
			
			try {
				newNode = new IntegerClassifier(0, shOper.name);
				newNodeIdx = getModel().getSize();
				((DefaultListModel<IntegerClassifier>) getModel()).addElement(newNode);
				setSelectedIndex(newNodeIdx);
				changingNodes = true;
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
			}
		}
		
		public void updateSavedNode() {
			IntegerClassifier item = getSelectedValue();
			
			newNode = null;
			item.setPcod(shOper.id);
			item.setName(String.format("%s %s", shOper.oper_pcod, shOper.name));
			((DefaultListModel<IntegerClassifier>) getModel()).set(getSelectedIndex(), item);
			changingNodes = false;
		}
		
		private void removeUnsavedNode(boolean updSelection) {
			if (newNode != null) {
				try {
					int idx = newNodeIdx;
					
					newNode = null;
					((DefaultListModel<IntegerClassifier>) getModel()).remove(idx);
					changingNodes = false;
					newNodeIdx = -1;
					if (updSelection && (getModel().getSize() > 0)) {
						if (idx >= getModel().getSize())
							idx = getModel().getSize() - 1;
						setSelectedIndex(idx);
					}
					if ((getModel().getSize() == 0))
						clearFields();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
