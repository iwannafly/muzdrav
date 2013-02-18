package ru.nkz.ivcgzo.clientDiary;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTextField;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierList;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftDiary.MedicalHistoryNotFoundException;
import ru.nkz.ivcgzo.thriftDiary.Patient;
import ru.nkz.ivcgzo.thriftDiary.Shablon;
import ru.nkz.ivcgzo.thriftDiary.ShablonText;
import ru.nkz.ivcgzo.thriftDiary.TMedicalHistory;
import ru.nkz.ivcgzo.clientManager.common.swing.ThriftIntegerClassifierCombobox;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;

public class MainFrame extends JFrame {

    private static final long serialVersionUID = 2973952682216394132L;

    private Component hsMedicalHistoryFirst;
    private Component hsMedicalHistorySecond;
    private Component hsMedicalHistoryThird;
    private Box vbMedicalHistoryTextFields;
    private Box hbMedicalHistoryTableControls;
    private JScrollPane spMedHist;
    private CustomTable<TMedicalHistory, TMedicalHistory._Fields> tblMedHist;
    private Box vbMedicalHistoryTableButtons;
    private JButton btnMedHistAdd;
    private JButton btnMedHistDel;
    private JButton btnMedHistUpd;
    private Component vsMedicalHistoryControlsDelim;
    private JTextArea taJalob;
    private JTextArea taDesiaseHistory;
    private JTextArea taStatusPraence;
    private JTextArea taFisicalObs;
    private JTextArea taStatusLocalis;
    private Box vbMedicalHistoryShablonComponents;
    private JLabel lblMedicalHistioryShablonHeader;
    private Box hbMedicalHistoryShablonFind;
    private CustomTextField tfMedHShablonFilter;
    private JButton btnMedicalHistoryShablonFind;
    private ShablonSearchListener medHiSearchListener;
    private ThriftIntegerClassifierList lMedicalHistoryShablonNames;
    private JScrollPane spMedicalHistoryShablonNames;
//    private ShablonForm frmShablon;
    private JPanel pnlJal;
    private JPanel pnlMedicalHist;
    private JPanel pnlStatusPraence;
    private JPanel pnlStatusLocalis;
    private JPanel pnlFisicalObs;
    private Patient patient;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> ticcbOtd;
    private ThriftIntegerClassifierCombobox<IntegerClassifier> ticcbPcodOsm;
    private int ticcbOtdSelIndex = -1;
    
    public MainFrame() {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(980, 600));
        setSize(new Dimension(980, 600));
//        setIconImage(Toolkit.getDefaultToolkit().getImage(MainFrame.class.getResource(
//                "/ru/nkz/ivcgzo/clientLab/resources/issled.png")));
        getContentPane().setLayout(new BoxLayout(getContentPane(), BoxLayout.X_AXIS));
        
        setDiaryPanel();
    }

    private void setDiaryPanel() {
//        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        hsMedicalHistoryFirst = Box.createHorizontalStrut(5);
        getContentPane().add(hsMedicalHistoryFirst);

        setMedicalHistoryVerticalTextComponents();

        hsMedicalHistorySecond = Box.createHorizontalStrut(5);
        getContentPane().add(hsMedicalHistorySecond);

        setMedicalHistoryVerticalShablonPanel();

        hsMedicalHistoryThird = Box.createHorizontalStrut(5);
        getContentPane().add(hsMedicalHistoryThird);
    }

    private void setMedicalHistoryVerticalTextComponents() {
        vbMedicalHistoryTextFields = Box.createVerticalBox();
        vbMedicalHistoryTextFields.setBorder(
            new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.GRAY));
        vbMedicalHistoryTextFields.setPreferredSize(new Dimension(500, 0));
        getContentPane().add(vbMedicalHistoryTextFields);

        setMedicalHistoryHorizontalTableComponents();
        setMedicalHistoryTabPaneComponents();
    }

    private void setMedicalHistoryHorizontalTableComponents() {
        hbMedicalHistoryTableControls = Box.createHorizontalBox();
        hbMedicalHistoryTableControls.setBorder(
            new MatteBorder(0, 0, 1, 0, (Color) new Color(0, 0, 0)));
        vbMedicalHistoryTextFields.add(hbMedicalHistoryTableControls);

        setMedicalHistoryTableScrollPane();
        setMedicalHistoryTableButtonsPanel();
    }

    private void setMedicalHistoryTableScrollPane() {
        spMedHist = new JScrollPane();
        spMedHist.setBorder(new MatteBorder(0, 0, 0, 1, (Color) new Color(0, 0, 0)));
        spMedHist.setPreferredSize(new Dimension(200, 150));
        hbMedicalHistoryTableControls.add(spMedHist);

        addMedicalHistoryTable();
    }

    private void addMedicalHistoryTable() {
        tblMedHist = new CustomTable<TMedicalHistory, TMedicalHistory._Fields>(
            true, true, TMedicalHistory.class, 8, "Дата осмотра", 9, "Время осмотра");
        spMedHist.setViewportView(tblMedHist);
        tblMedHist.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (tblMedHist.getSelectedItem() != null) {
                    setMedicalHistoryText();
                }
            }
        });
        tblMedHist.setDateField(0);
        tblMedHist.setTimeField(1);
    }

    private void setMedicalHistoryTableButtonsPanel() {
        vbMedicalHistoryTableButtons = Box.createVerticalBox();
        hbMedicalHistoryTableControls.add(vbMedicalHistoryTableButtons);

        addMedicalHistoryButtons();
    }

    private void addMedicalHistoryButtons() {
        addMedicalHistoryAddButton();
        addMedicalHistoryDeleteButton();
        addMedicalHistoryUpdateButton();
    }

    private void addMedicalHistoryAddButton() {
        btnMedHistAdd = new JButton();
        btnMedHistAdd.setPreferredSize(new Dimension(50, 50));
        btnMedHistAdd.setMaximumSize(new Dimension(50, 50));
        vbMedicalHistoryTableButtons.add(btnMedHistAdd);
        btnMedHistAdd.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                addMedHistoryToTable();
            }
        });
        btnMedHistAdd.setIcon(new ImageIcon(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientViewSelect/resources/1331789242_Add.png")));
    }

    private void addMedicalHistoryDeleteButton() {
        btnMedHistDel = new JButton();
        btnMedHistDel.setMaximumSize(new Dimension(50, 50));
        btnMedHistDel.setPreferredSize(new Dimension(50, 50));
        vbMedicalHistoryTableButtons.add(btnMedHistDel);
        btnMedHistDel.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                deleteMedHistoryFormTable();
            }
        });
        btnMedHistDel.setIcon(new ImageIcon(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientViewSelect/resources/1331789259_Delete.png")));
    }

    private void addMedicalHistoryUpdateButton() {
        btnMedHistUpd = new JButton();
        btnMedHistUpd.setPreferredSize(new Dimension(50, 50));
        btnMedHistUpd.setMaximumSize(new Dimension(50, 50));
        vbMedicalHistoryTableButtons.add(btnMedHistUpd);
        btnMedHistUpd.addActionListener(new ActionListener() {
            public void actionPerformed(final ActionEvent e) {
                updateMedHistoryToTable();
            }
        });
        btnMedHistUpd.setIcon(new ImageIcon(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientViewSelect/resources/1341981970_Accept.png")));
    }


    private void setMedicalHistoryTabPaneComponents() {
        vsMedicalHistoryControlsDelim = Box.createVerticalStrut(20);
        vbMedicalHistoryTextFields.add(vsMedicalHistoryControlsDelim);

        addMedicalHistoryTabbedPane();
    }

    private void addMedicalHistoryTabbedPane() {
        JPanel pnlOsm = new JPanel();
        vbMedicalHistoryTextFields.add(pnlOsm);

        JScrollPane spOsm = new JScrollPane();
        spOsm.setFont(new Font("Tahoma", Font.PLAIN, 12));
        GroupLayout glPnlOsm = new GroupLayout(pnlOsm);
        glPnlOsm.setHorizontalGroup(
            glPnlOsm.createParallelGroup(Alignment.LEADING)
                .addComponent(spOsm, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
        );
        glPnlOsm.setVerticalGroup(
            glPnlOsm.createParallelGroup(Alignment.LEADING)
                .addComponent(spOsm, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
        );
        JPanel pnlOsmOsm = new JPanel();
        spOsm.setViewportView(pnlOsmOsm);

        addJalobComponents();
        addDesiaseHistoryComponents();
        addStatusPraenceComponents();
        addStatusLocalis();
        addFisicalObs();
        
        ticcbOtd = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        ticcbOtd.addItemListener(new ItemListener() {
        	public void itemStateChanged(ItemEvent ie) {
        		ticcbOtdItemStateChanged(ie);
        	}
        });
        
        ticcbPcodOsm = new ThriftIntegerClassifierCombobox<IntegerClassifier>(true);
        
        JLabel lblOtd = new JLabel("Отделение:");
        lblOtd.setLabelFor(ticcbOtd);
        
        JLabel lblOsm = new JLabel("Кто осматривал:");
        lblOsm.setLabelFor(ticcbPcodOsm);

        GroupLayout glPnlOsmOsm = new GroupLayout(pnlOsmOsm);
        glPnlOsmOsm.setHorizontalGroup(
        	glPnlOsmOsm.createParallelGroup(Alignment.LEADING)
        		.addGroup(glPnlOsmOsm.createSequentialGroup()
        			.addContainerGap()
        			.addGroup(glPnlOsmOsm.createParallelGroup(Alignment.LEADING)
        				.addGroup(glPnlOsmOsm.createSequentialGroup()
        					.addGroup(glPnlOsmOsm.createParallelGroup(Alignment.TRAILING)
        						.addComponent(pnlJal, GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
        						.addComponent(pnlMedicalHist, GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
        						.addComponent(pnlStatusPraence, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        						.addComponent(pnlStatusLocalis, GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
        						.addComponent(pnlFisicalObs, GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)
        						.addGroup(glPnlOsmOsm.createSequentialGroup()
        							.addComponent(lblOtd)
        							.addPreferredGap(ComponentPlacement.RELATED, 484, Short.MAX_VALUE))
        						.addGroup(Alignment.LEADING, glPnlOsmOsm.createSequentialGroup()
        							.addGroup(glPnlOsmOsm.createParallelGroup(Alignment.TRAILING, false)
        								.addComponent(ticcbPcodOsm, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 519, GroupLayout.PREFERRED_SIZE)
        								.addComponent(ticcbOtd, Alignment.LEADING, GroupLayout.PREFERRED_SIZE, 519, GroupLayout.PREFERRED_SIZE))
        							.addGap(26)))
        					.addGap(0))
        				.addGroup(glPnlOsmOsm.createSequentialGroup()
        					.addComponent(lblOsm)
        					.addContainerGap(460, Short.MAX_VALUE))))
        );
        glPnlOsmOsm.setVerticalGroup(
        	glPnlOsmOsm.createParallelGroup(Alignment.LEADING)
        		.addGroup(glPnlOsmOsm.createSequentialGroup()
        			.addContainerGap()
        			.addComponent(lblOtd)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(ticcbOtd, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(lblOsm)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(ticcbPcodOsm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(pnlJal, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(pnlMedicalHist, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(pnlStatusPraence, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(pnlStatusLocalis, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
        			.addPreferredGap(ComponentPlacement.RELATED)
        			.addComponent(pnlFisicalObs, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
        			.addContainerGap())
        );
        pnlOsmOsm.setLayout(glPnlOsmOsm);
        pnlOsm.setLayout(glPnlOsm);
    }

    private void addJalobComponents() {
        pnlJal = new JPanel();
        pnlJal.setBorder(new TitledBorder(null, "Жалобы",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        JScrollPane spJal = new JScrollPane();
        GroupLayout glPnlJal = new GroupLayout(pnlJal);
        glPnlJal.setHorizontalGroup(
            glPnlJal.createParallelGroup(Alignment.LEADING)
                .addGap(0, 300, Short.MAX_VALUE)
                .addComponent(spJal, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
        );
        glPnlJal.setVerticalGroup(
            glPnlJal.createParallelGroup(Alignment.LEADING)
                .addGap(0, 45, Short.MAX_VALUE)
                .addComponent(spJal, GroupLayout.DEFAULT_SIZE, 25, Short.MAX_VALUE)
        );
        taJalob = new JTextArea();
        taJalob.setFont(new Font("Tahoma", Font.PLAIN, 12));
        taJalob.setLineWrap(true);
        taJalob.setWrapStyleWord(true);
        spJal.setViewportView(taJalob);
        pnlJal.setLayout(glPnlJal);
    }

    private void addFisicalObs() {
        pnlMedicalHist = new JPanel();
        pnlMedicalHist.setBorder(new TitledBorder(null, "История заболевания (anamnesis morbi)",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        JScrollPane spAnam = new JScrollPane();
        GroupLayout glPnlAnam = new GroupLayout(pnlMedicalHist);
        glPnlAnam.setHorizontalGroup(
            glPnlAnam.createParallelGroup(Alignment.LEADING).addGap(0, 578, Short.MAX_VALUE)
                .addComponent(spAnam, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
        );
        glPnlAnam.setVerticalGroup(
            glPnlAnam.createParallelGroup(Alignment.LEADING).addGap(0, 51, Short.MAX_VALUE)
                .addComponent(spAnam, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
        );
        taDesiaseHistory = new JTextArea();
        taDesiaseHistory.setWrapStyleWord(true);
        taDesiaseHistory.setLineWrap(true);
        taDesiaseHistory.setFont(new Font("Tahoma", Font.PLAIN, 12));
        spAnam.setViewportView(taDesiaseHistory);
        pnlMedicalHist.setLayout(glPnlAnam);
    }

    private void addStatusLocalis() {
        pnlStatusPraence = new JPanel();
        pnlStatusPraence.setBorder(new TitledBorder(null, "Объективный статус (status praesense)",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        JScrollPane spStat = new JScrollPane();
        taStatusPraence = new JTextArea();
        taStatusPraence.setWrapStyleWord(true);
        taStatusPraence.setLineWrap(true);
        taStatusPraence.setFont(new Font("Tahoma", Font.PLAIN, 12));
        GroupLayout glPnlStat = new GroupLayout(pnlStatusPraence);
        glPnlStat.setHorizontalGroup(
            glPnlStat.createParallelGroup(Alignment.LEADING).addComponent(spStat));
        glPnlStat.setVerticalGroup(
            glPnlStat.createParallelGroup(Alignment.LEADING)
            .addGroup(glPnlStat.createSequentialGroup()
                    .addComponent(spStat, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE))
        );
        spStat.setViewportView(taStatusPraence);
        pnlStatusPraence.setLayout(glPnlStat);
    }

    private void addStatusPraenceComponents() {
        pnlStatusLocalis = new JPanel();
        pnlStatusLocalis.setBorder(new TitledBorder(null, "Локальный статус (localis status)",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        JScrollPane spFiz = new JScrollPane();
        GroupLayout glPnlFiz = new GroupLayout(pnlStatusLocalis);
        glPnlFiz.setHorizontalGroup(
            glPnlFiz.createParallelGroup(Alignment.LEADING)
                .addGap(0, 578, Short.MAX_VALUE).addGap(0, 562, Short.MAX_VALUE)
                .addComponent(spFiz, GroupLayout.DEFAULT_SIZE, 304, Short.MAX_VALUE)
        );
        glPnlFiz.setVerticalGroup(
            glPnlFiz.createParallelGroup(Alignment.LEADING)
                .addGap(0, 51, Short.MAX_VALUE).addGap(0, 27, Short.MAX_VALUE)
                .addComponent(spFiz, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
        );
        taStatusLocalis = new JTextArea();
        taStatusLocalis.setWrapStyleWord(true);
        taStatusLocalis.setLineWrap(true);
        taStatusLocalis.setFont(new Font("Tahoma", Font.PLAIN, 12));
        spFiz.setViewportView(taStatusLocalis);
        pnlStatusLocalis.setLayout(glPnlFiz);
    }

    private void addDesiaseHistoryComponents() {
        pnlFisicalObs = new JPanel();
        pnlFisicalObs.setBorder(new TitledBorder(null, "Физикальное обследование",
                TitledBorder.LEADING, TitledBorder.TOP, null, null));
        JScrollPane spLoc = new JScrollPane();
        GroupLayout glPnlLoc = new GroupLayout(pnlFisicalObs);
        glPnlLoc.setHorizontalGroup(
            glPnlLoc.createParallelGroup(Alignment.LEADING)
                .addGap(0, 578, Short.MAX_VALUE).addGap(0, 562, Short.MAX_VALUE)
                .addComponent(spLoc, GroupLayout.DEFAULT_SIZE, 562, Short.MAX_VALUE)
        );
        glPnlLoc.setVerticalGroup(
            glPnlLoc.createParallelGroup(Alignment.LEADING)
                .addGap(0, 51, Short.MAX_VALUE).addGap(0, 27, Short.MAX_VALUE)
                .addComponent(spLoc, GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)
        );
        taFisicalObs = new JTextArea();
        taFisicalObs.setWrapStyleWord(true);
        taFisicalObs.setLineWrap(true);
        taFisicalObs.setFont(new Font("Tahoma", Font.PLAIN, 12));
        spLoc.setViewportView(taFisicalObs);
        pnlFisicalObs.setLayout(glPnlLoc);
    }

    private void deleteMedHistoryFormTable() {
        try {
            if (tblMedHist.getSelectedItem() != null) {
                int opResult = JOptionPane.showConfirmDialog(
                    btnMedHistDel, "Удалить запись?",
                    "Удаление записи", JOptionPane.YES_NO_OPTION);
                if (opResult == JOptionPane.YES_OPTION) {
                    ClientDiary.tcl.deleteMedicalHistory(
                        tblMedHist.getSelectedItem().getId());
                    //Заполнение таблицы:
                    fillMedHistoryTable();
                    //Очистка текстовых полей:
                    clearMedicalHistoryTextAreas();
                }
                if (tblMedHist.getRowCount() > 0) {
                    tblMedHist.setRowSelectionInterval(tblMedHist.getRowCount() - 1,
                        tblMedHist.getRowCount() - 1);
                }
            }
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (MedicalHistoryNotFoundException e) {
        	//Повторная загрузка данных, если запись осмотра не найдена:
        	clearMedicalHistory();
        	fillMedHistoryTable();
        } catch (TException e1) {
            ClientDiary.conMan.reconnect(e1);
        }

    }

    private void updateMedHistoryToTable() {
        try {
        	TMedicalHistory curHist = tblMedHist.getSelectedItem();
            if (curHist != null) {
                int opResult = JOptionPane.showConfirmDialog(
                    btnMedHistUpd, "Обновить информацию о диагнозе?",
                    "Изменение диагноза", JOptionPane.YES_NO_OPTION);
                if (opResult == JOptionPane.YES_OPTION) {
                	//При обновлении записи нельзя изменять номер
                	//отделения и врачей, проводившего осмотр и добавившего запись:
                	getMedicalMedicalHistoryText(curHist);
                    ClientDiary.tcl.updateMedicalHistory(curHist);
                }
            }
        } catch (KmiacServerException e1) {
            e1.printStackTrace();
        } catch (MedicalHistoryNotFoundException e) {
        	//Повторная загрузка данных, если запись осмотра не найдена:
        	clearMedicalHistory();
        	fillMedHistoryTable();
        } catch (TException e) {
            ClientDiary.conMan.reconnect(e);
        } catch (NullPointerException e) { }
    }

    private void addMedHistoryToTable() {
        try {
            if (patient != null) {
            	if ((ticcbOtd.getSelectedItem() == null) ||
            		(ticcbPcodOsm.getSelectedItem() == null)) {
					JOptionPane.showMessageDialog(this, "Не выбрано отделение или врач",
							"Ошибка", JOptionPane.ERROR_MESSAGE);
					return;
            	}
            	//Загрузка данных осмотра из текстовых полей:
                TMedicalHistory newMedHist = new TMedicalHistory(); 
                getMedicalMedicalHistoryText(newMedHist);
                //Установка "скрытых значений":
                newMedHist.setDataz(System.currentTimeMillis());
                newMedHist.setTimez(System.currentTimeMillis());
                newMedHist.setPcodVrach(ticcbPcodOsm.getSelectedPcod());
                newMedHist.setCpodr(ticcbOtd.getSelectedPcod());
                newMedHist.setPcodAdded(ClientDiary.authInfo.getPcod());
                newMedHist.setIdGosp(patient.getIdGosp());
                //Добавление информации об осмотре в БД:
                newMedHist.setId(ClientDiary.tcl.addMedicalHistory(newMedHist));
                //Заполнение таблицы:
                fillMedHistoryTable();
                //Очистка текстовых полей:
                clearMedicalHistoryTextAreas();
            }
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (MedicalHistoryNotFoundException e) {
            tblMedHist.setData(new ArrayList<TMedicalHistory>());
        } catch (TException e) {
            ClientDiary.conMan.reconnect(e);
        } catch (NullPointerException e) {
        	//Ошибка создания нового объекта медицинской истории
			e.printStackTrace();
		}
    }
    
    /**
     * Событие, вызываемое при смене состояния элемента
     * списка отделений
     * @param ie Информация о произошедшем событии смены элемента
     * @author Балабаев Никита Дмитриевич
     */
    private void ticcbOtdItemStateChanged(ItemEvent ie) {
		int newIndex = ticcbOtd.getSelectedIndex();
		if ((ie != null) && (ticcbOtd != null) &&
			(ie.getStateChange() == ItemEvent.SELECTED) &&
			(ticcbOtd.getSelectedItem() != null) &&
			(newIndex != ticcbOtdSelIndex))
	    	try {
	    		ticcbOtdSelIndex = newIndex;
				ticcbPcodOsm.setData(
					ClientDiary.tcl.getDoctorsFromOtd(
						ClientDiary.authInfo.getClpu(), ticcbOtd.getSelectedPcod()));
			} catch (TException e) {
				ticcbPcodOsm.setData(null);
			}
    }
    
    /**
     * Получение текстовой информации о текущем осмотре
     * из текстовых полей
     * @param medHist Объект медицинской истории, в который будет
     * загружена информация
     * @author Балабаев Никита Дмитриевич
     * @throws NullPointerException переданный объект был null-объектом
     */
    private void getMedicalMedicalHistoryText(TMedicalHistory medHist)
    		throws NullPointerException {
    	if (medHist == null)
    		throw new NullPointerException();
        if (taJalob != null)
        	medHist.setJalob(taJalob.getText());
        if (taDesiaseHistory != null)
        	medHist.setMorbi(taDesiaseHistory.getText());
        if (taFisicalObs != null)
        	medHist.setFisicalObs(taFisicalObs.getText());
        if (taStatusLocalis != null)
        	medHist.setStatusLocalis(taStatusLocalis.getText());
        if (taStatusPraence != null)
        	medHist.setStatusPraesense(taStatusPraence.getText());
    }

    private void setMedicalHistoryText() {
    	TMedicalHistory curMedHist = tblMedHist.getSelectedItem();
    	if (curMedHist == null)
    		return;
    	ticcbOtd.setSelectedPcod(curMedHist.getCpodr());
    	ticcbPcodOsm.setSelectedPcod(curMedHist.getPcodVrach());
        if (taJalob != null)
            taJalob.setText(curMedHist.getJalob());
        if (taDesiaseHistory != null)
            taDesiaseHistory.setText(curMedHist.getMorbi());
        if (taFisicalObs != null)
            taFisicalObs.setText(curMedHist.getFisicalObs());
        if (taStatusLocalis != null)
            taStatusLocalis.setText(curMedHist.getStatusLocalis());
        if (taStatusPraence != null)
            taStatusPraence.setText(curMedHist.getStatusPraesense());
    }

    private void clearMedicalHistory() {
        tblMedHist.setData(Collections.<TMedicalHistory>emptyList());
        clearMedicalHistoryTextAreas();
    }

    private void clearMedicalHistoryTextAreas() {
        if (taJalob != null) {
            taJalob.setText("");
        }
        if (taDesiaseHistory != null) {
            taDesiaseHistory.setText("");
        }
        if (taFisicalObs != null) {
            taFisicalObs.setText("");
        }
        if (taStatusLocalis != null) {
            taStatusLocalis.setText("");
        }
        if (taStatusPraence != null) {
            taStatusPraence.setText("");
        }
        ticcbOtd.setSelectedItem(null);
        ticcbPcodOsm.setSelectedItem(null);
    }

    private void setMedicalHistoryVerticalShablonPanel() {
        vbMedicalHistoryShablonComponents = Box.createVerticalBox();
        vbMedicalHistoryShablonComponents.setBorder(
            new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.GRAY));
        vbMedicalHistoryShablonComponents.setPreferredSize(new Dimension(300, 0));
        getContentPane().add(vbMedicalHistoryShablonComponents);

        setMedicalHistoryShablonLabel();
        setMedicalHistoryShablonHorizontalBox();
        setMedicalHistoryShablonScrollPane();
        setMedicalHistoryShablonListener();
    }

    private void setMedicalHistoryShablonLabel() {
        lblMedicalHistioryShablonHeader = new JLabel("Строка поиска шаблона");
        lblMedicalHistioryShablonHeader.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblMedicalHistioryShablonHeader.setFont(new Font("Tahoma", Font.BOLD, 13));
        vbMedicalHistoryShablonComponents.add(lblMedicalHistioryShablonHeader);
        lblMedicalHistioryShablonHeader.setHorizontalTextPosition(SwingConstants.LEFT);
        lblMedicalHistioryShablonHeader.setHorizontalAlignment(SwingConstants.LEFT);
    }

    private void setMedicalHistoryShablonHorizontalBox() {
        hbMedicalHistoryShablonFind = Box.createHorizontalBox();
        vbMedicalHistoryShablonComponents.add(hbMedicalHistoryShablonFind);

        setMedicalHistoryShablonTextField();
        setMedicalHistoryShablonButton();
    }

    private void setMedicalHistoryShablonTextField() {
        tfMedHShablonFilter = new CustomTextField(true, true, false);
        tfMedHShablonFilter.setMaximumSize(new Dimension(450, 50));
        hbMedicalHistoryShablonFind.add(tfMedHShablonFilter);
        tfMedHShablonFilter.setColumns(10);
    }

    private void setMedicalHistoryShablonButton() {
        btnMedicalHistoryShablonFind = new JButton("...");
//        btnMedicalHistoryShablonFind.addActionListener(new ActionListener() {
//
//            public void actionPerformed(final ActionEvent e) {
//                frmShablon.showShablonForm(tfMedHShablonFilter.getText(),
//                    lMedicalHistoryShablonNames.getSelectedValue());
//                syncShablonList(frmShablon.getSearchString(), frmShablon.getShablon(),
//                    medHiSearchListener, lMedicalHistoryShablonNames);
//                pasteSelectedShablon(frmShablon.getShablon());
//            }
//        });
        btnMedicalHistoryShablonFind.setMinimumSize(new Dimension(63, 23));
        btnMedicalHistoryShablonFind.setMaximumSize(new Dimension(63, 23));
        btnMedicalHistoryShablonFind.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnMedicalHistoryShablonFind.setPreferredSize(new Dimension(63, 23));
        hbMedicalHistoryShablonFind.add(btnMedicalHistoryShablonFind);
    }

    private void setMedicalHistoryShablonScrollPane() {
        spMedicalHistoryShablonNames = new JScrollPane();
        vbMedicalHistoryShablonComponents.add(spMedicalHistoryShablonNames);

        setMedicalHistoryShablonList();
    }

    private void setMedicalHistoryShablonList() {
        lMedicalHistoryShablonNames = new ThriftIntegerClassifierList();
        spMedicalHistoryShablonNames.setViewportView(lMedicalHistoryShablonNames);
        lMedicalHistoryShablonNames.setBorder(new LineBorder(new Color(0, 0, 0)));
        lMedicalHistoryShablonNames.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(final MouseEvent e) {
                if (e.getClickCount() == 2) {
                    if (lMedicalHistoryShablonNames.getSelectedValue() != null) {
                        try {
                            pasteSelectedShablon(ClientDiary.tcl.getShablon(
                                lMedicalHistoryShablonNames.getSelectedValue().pcod));
                        } catch (KmiacServerException e1) {
                            JOptionPane.showMessageDialog(null,
                                "Ошибка загрузки шаблона", "Ошибка", JOptionPane.ERROR_MESSAGE);
                        } catch (TException e1) {
                            ClientDiary.conMan.reconnect(e1);
                        }
                    }
                }
            }
        });
    }

    private void setMedicalHistoryShablonListener() {
        medHiSearchListener =
                new ShablonSearchListener(tfMedHShablonFilter, lMedicalHistoryShablonNames);
        tfMedHShablonFilter.getDocument().addDocumentListener(medHiSearchListener);
    }

    private void pasteSelectedShablon(final Shablon shablon) {
        if (shablon == null) {
            return;
        }

        clearMedicalHistoryTextAreas();

        for (ShablonText shText : shablon.textList) {
            switch (shText.grupId) {
                case 1:
                    taJalob.setText(shText.getText());
                    break;
                case 2:
                    taDesiaseHistory.setText(shText.getText());
                    break;
                case 6:
                    taStatusPraence.setText(shText.getText());
                    break;
                case 7:
                    taFisicalObs.setText(shText.getText());
                    break;
                case 8:
                    taStatusLocalis.setText(shText.getText());
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * Заполнение таблицы осмотров данными из БД и
     * очистка выделения в таблице
     */
    private void fillMedHistoryTable() {
        if (patient != null) {
            try {
                tblMedHist.setData(
                    ClientDiary.tcl.getMedicalHistory(patient.getIdGosp()));
                tblMedHist.clearSelection();
            } catch (KmiacServerException e) {
                e.printStackTrace();
            } catch (MedicalHistoryNotFoundException e) {
            	clearMedicalHistory();
            } catch (TException e) {
                ClientDiary.conMan.reconnect(e);
            }
        }
    }

    private class ShablonSearchListener implements DocumentListener {
        private CustomTextField ctf;
        private ThriftIntegerClassifierList ticl;

        public ShablonSearchListener(final CustomTextField inCtf,
                final ThriftIntegerClassifierList inTicl) {
            ctf = inCtf;
            ticl = inTicl;
        }

        private Timer timer = new Timer(500, new ActionListener() {

            @Override
            public void actionPerformed(final ActionEvent e) {
                updateNow();
            }
        });

        @Override
        public void removeUpdate(final DocumentEvent e) {
            timer.restart();
        }

        @Override
        public void insertUpdate(final DocumentEvent e) {
            timer.restart();
        }

        @Override
        public void changedUpdate(final DocumentEvent e) {
            timer.restart();
        }

        public void updateNow() {
            timer.stop();
            loadShablonList(ctf, ticl);
        }

        public void updateNow(final String searchString) {
            ctf.setText(searchString);
            updateNow();
        }
    }

    private void loadShablonList(final CustomTextField inCtf,
            final ThriftIntegerClassifierList inTicl) {
        try {
            List<IntegerClassifier> intClassif = ClientDiary.tcl.getShablonNames(
                ClientDiary.authInfo.getCpodr(), ClientDiary.authInfo.getCslu(),
                (inCtf.getText().length() < 3)
                ? null : '%' + inCtf.getText() + '%');
            inTicl.setData(intClassif);
        } catch (KmiacServerException e1) {
            JOptionPane.showMessageDialog(null,
                "Ошибка загрузки результатов поиска", "Ошибка", JOptionPane.ERROR_MESSAGE);
        } catch (TException e1) {
            ClientDiary.conMan.reconnect(e1);
        }
    }

    private void syncShablonList(final String searchString, final Shablon shablon,
            final ShablonSearchListener shSl, final ThriftIntegerClassifierList ticl) {
        if (shablon != null) {
            shSl.updateNow(searchString);
            for (int i = 0; i < ticl.getData().size(); i++) {
                if (ticl.getData().get(i).pcod == shablon.getId()) {
                    ticl.setSelectedIndex(i);
                    break;
                }
            }
        } else {
            ticl.setSelectedIndex(-1);
        }
    }

    public final Component getComponent() {
        return this;
    }

    public final void setMedicalHistoryShablons(
            final List<IntegerClassifier> medicalHistoryShablonList) {
        lMedicalHistoryShablonNames.setData(medicalHistoryShablonList);
    }

    public void fillPatient(final int id, final String surname,
            final String name, final String middlename, final int idGosp) {
        patient = new Patient();
        patient.setId(id);
        patient.setSurname(surname);
        patient.setName(name);
        patient.setMiddlename(middlename);
        patient.setIdGosp(idGosp);
        
        clearMedicalHistory();
        fillMedHistoryTable();
        try {
            setMedicalHistoryShablons(ClientDiary.tcl.getShablonNames(ClientDiary.authInfo.getCpodr(), 3, null));
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (TException e) {
            e.printStackTrace();
            ClientDiary.conMan.reconnect(e);
        }
    }

    public void onConnect() {
    	try {
			ticcbOtd.setData(ClientDiary.tcl.getOtdFromLPU(ClientDiary.authInfo.getClpu()));
		} catch (TException e) {
			ticcbOtd.setData(null);
		}
    }
}
