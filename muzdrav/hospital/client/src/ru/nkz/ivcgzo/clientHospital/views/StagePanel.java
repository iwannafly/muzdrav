package ru.nkz.ivcgzo.clientHospital.views;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EtchedBorder;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientHospital.ClientHospital;
import ru.nkz.ivcgzo.clientHospital.MainFrame;
import ru.nkz.ivcgzo.clientHospital.controllers.StageController;
import ru.nkz.ivcgzo.clientHospital.model.IHospitalModel;
import ru.nkz.ivcgzo.clientHospital.model.IPatientObserver;
import ru.nkz.ivcgzo.clientManager.common.swing.CustomTable;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifiers;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftHospital.MesNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.TStage;
import ru.nkz.ivcgzo.thriftHospital.TStage._Fields;

public class StagePanel extends JPanel implements IPatientObserver {
    private static final long serialVersionUID = -1144680238391649525L;
    private static final String TOOLTIP_TEXT =
            "<html><b>Панель этапов лечения</b> - позволяет врачу стационара  "
            + "сохранять информацию об этапах лечения, необходимую для "
            + "формирования реестров.</html>";
    private static final URL ICON = MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/stages.png");
    private static final String TITLE = "Этапы";
    @SuppressWarnings("unused")
    private StageController controller;
    private IHospitalModel model;
    private JScrollPane spStageTable;
    private CustomTable<TStage, _Fields> tbStages;
    private JPanel pStageButtons;
    private JButton btnAddStage;
    private JButton btnUpdateStage;
    private JButton btnDeleteStage;

    public StagePanel(final StageController inController,
            final IHospitalModel inModel) {
        this.controller = inController;
        this.model = inModel;
        model.registerPatientObserver((IPatientObserver) this);

        setStagePanel();
    }


    private void setStagePanel() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

        addStageTablePanel();
        addStageButtonPanel();
    }

    private void addStageTablePanel() {
        spStageTable = new JScrollPane();
        spStageTable.setBorder(new EtchedBorder(EtchedBorder.LOWERED, Color.BLACK, Color.GRAY));
        add(spStageTable);

        tbStages = new CustomTable<TStage, TStage._Fields>(
            true, true, TStage.class, 4, "Дата начала", 9, "Время начала", 5, "Дата окончания",
            10, "Время окончания", 2, "Этап", 3, "МЭС", 6, "УКЛ", 7, "Исход", 8, "Результат");
        tbStages.setDateField(0);
        tbStages.setTimeField(1);
        tbStages.setDateField(2);
        tbStages.setTimeField(3);
        tbStages.setIntegerClassifierSelector(4, IntegerClassifiers.n_etp);
        tbStages.setIntegerClassifierSelector(7, IntegerClassifiers.n_ap0);
        tbStages.setIntegerClassifierSelector(8, IntegerClassifiers.n_aq0);
        spStageTable.setViewportView(tbStages);
    }

    private void addStageButtonPanel() {
        pStageButtons = new JPanel();
        pStageButtons.setMaximumSize(new Dimension(50, 32767));
        add(pStageButtons);
        pStageButtons.setLayout(new BoxLayout(pStageButtons, BoxLayout.Y_AXIS));

        addStageAddButton();
        addStageDeleteButton();
        addStageUpdateButton();
    }

    private void addStageAddButton() {
        btnAddStage = new JButton();
        btnAddStage.setMaximumSize(new Dimension(50, 50));
        btnAddStage.setIcon(new ImageIcon(MainFrame.class.getResource(
            "/ru/nkz/ivcgzo/clientHospital/resources/1331789242_Add.png")));
        btnAddStage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                addStageToTable();
            }
        });
        pStageButtons.add(btnAddStage);
    }

    //есть ли в таблице этапов несохраненные элементы
    private boolean isStageTableSaved() {
        if (tbStages.getData().size() != 0) {
            for (TStage stage: tbStages.getData()) {
                if (!stage.isSetId()) {
                    JOptionPane.showMessageDialog(null,
                        "Последний добавленный этап не сохранен! "
                        + "Сохраните его перед добавлением нового этапа "
                        + "или переходом на другую вкладку!",
                        "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } else {
            return true;
        }
        return true;
    }

    @SuppressWarnings("unused")
    private boolean isStageTableReadyToOut() {
        if (tbStages.getData().size() != 0) {
            for (TStage stage: tbStages.getData()) {
                if (!stage.isSetId()) {
                    JOptionPane.showMessageDialog(null,
                        "Последний добавленный этап не сохранен! "
                        + "Сохраните его перед добавлением нового этапа "
                        + "или переходом на другую вкладку!",
                        "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    return false;
                } else {
                    if (!isStageUpdateRequiredFieldsSet(stage)) {
                        return false;
                    }
                }
            }
        } else {
            return true;
//            правильный вариант этот, в последний момент попросили заменить верхним костылем
//            return false;
        }
        return true;
    }

    @SuppressWarnings("unused")
    private boolean isStageDatesCorrect() {
        if (tbStages.getData().size() == 0) {
            return true;
        }
        //FIXME удалить
        return true;
//FIXME поправить (раскомментарить)
//        return ((tbStages.getData().get(0).getDateStart() == model.getPriemInfo().getDatap())
//            && (tbStages.getData().get(tbStages.getData().size() - 1).getDateEnd()
//                == cdeZaklDate.getDate().getTime()));
    }

    private boolean isLastStageItemSetCorrectly() {
        if (tbStages.getData().size() != 0) {
            return isStageUpdateRequiredFieldsSet(
                tbStages.getData().get(tbStages.getData().size() - 1));
        } else {
            return true;
        }
    }

    private long calcCorrectStageDateStart() {
        if (tbStages.getData().size() == 0) {
            return model.getPriemInfo().getDatap();
        } else {
            return tbStages.getData().get(tbStages.getData().size() - 1).getDateEnd();
        }
    }

    private boolean isStageUpdateRequiredFieldsSet(final TStage stage) {
        if (isStageAddRequiredFieldsSet(stage)) {
            if (!stage.isSetDateEnd()) {
                JOptionPane.showMessageDialog(null,
                    "Дата окончания этапа не заполнена! ",
                    "Ошибка!", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (!stage.isSetIshod()) {
                JOptionPane.showMessageDialog(null,
                        "Исход этапа не заполнен! ",
                        "Ошибка!", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (!stage.isSetResult()) {
                JOptionPane.showMessageDialog(null,
                    "Результат этапа не заполнен! ",
                    "Ошибка!", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (!stage.isSetUkl()) {
                JOptionPane.showMessageDialog(null,
                    "Укл этапа не заполнен! ",
                    "Ошибка!", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (stage.getStage() == 1) {
                if (!stage.isSetTimeEnd()) {
                    JOptionPane.showMessageDialog(null,
                        "Время конца этапа не заполнено! ",
                        "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        } else {
            return false;
        }
        return true;
    }

    private boolean isStageAddRequiredFieldsSet(final TStage stage) {
        if (!stage.isSetDateStart()) {
            JOptionPane.showMessageDialog(null,
                "Дата начала этапа не заполнена! ",
                "Ошибка!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!stage.isSetIdGosp()) {
            JOptionPane.showMessageDialog(null,
                "Идентификатор госпитализации этапа не заполнен! ",
                "Ошибка!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!stage.isSetStage()) {
            JOptionPane.showMessageDialog(null,
                "Этап не заполнен! ",
                "Ошибка!", JOptionPane.ERROR_MESSAGE);
            return false;
        } else {
            if (stage.getStage() == 1) {
                if (!stage.isSetTimeStart()) {
                    JOptionPane.showMessageDialog(null,
                        "Время начала этапа не заполнено! ",
                        "Ошибка!", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
        }
        if (!stage.isSetMes()) {
            JOptionPane.showMessageDialog(null,
                "Код МЭС этапа не заполнен! ",
                "Ошибка!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    private void addStageToTable() {
        if ((model.getPatient() != null)
                && (isStageTableSaved())
                && (isLastStageItemSetCorrectly())) {
            TStage stage = new TStage();
            stage.setDateStart(calcCorrectStageDateStart());
            stage.setIdGosp(model.getPatient().getGospitalCod());
//                stage.setId(ClientHospital.tcl.addStage(stage));
            tbStages.addItem(stage);
//                tbStages.setData(
//                    ClientHospital.tcl.getStage(model.getPatient()getGospitalCod()));
            if (tbStages.getData().size() > 1) {
                tbStages.getData().get(
                    tbStages.getData().size() - 1).setMes(tbStages.getData().get(0).getMes());
            }
        }
    }

    private void addStageDeleteButton() {
        btnDeleteStage = new JButton();
        btnDeleteStage.setMaximumSize(new Dimension(50, 50));
        btnDeleteStage.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientHospital/resources/1331789259_Delete.png")));
        btnDeleteStage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                deleteStageFromTable();
            }
        });
        pStageButtons.add(btnDeleteStage);
    }

    private void deleteStageFromTable() {
        try {
            if (tbStages.getSelectedItem() != null) {
                int opResult = JOptionPane.showConfirmDialog(
                    null, "Удалить этап лечения?",
                    "Удаление этапа лечения", JOptionPane.YES_NO_OPTION);
                if (opResult == JOptionPane.YES_OPTION) {
                    ClientHospital.tcl.deleteStage(tbStages.getSelectedItem().getId());
                    tbStages.setData(
                        ClientHospital.tcl.getStage(model.getPatient().getGospitalCod()));
                }
                if (tbStages.getRowCount() > 0) {
                    tbStages.setRowSelectionInterval(tbStages.getRowCount() - 1,
                        tbStages.getRowCount() - 1);
                }
            }
        } catch (KmiacServerException e1) {
            e1.printStackTrace();
        } catch (TException e1) {
            ClientHospital.conMan.reconnect(e1);
        }
    }

    private void addStageUpdateButton() {
        btnUpdateStage = new JButton();
        btnUpdateStage.setMaximumSize(new Dimension(50, 50));
        btnUpdateStage.setIcon(new ImageIcon(MainFrame.class.getResource(
                "/ru/nkz/ivcgzo/clientHospital/resources/1341981970_Accept.png")));
        btnUpdateStage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                saveStageToTable();
            }
        });
        pStageButtons.add(btnUpdateStage);
    }

    private void saveStageToTable() {
        try {
            if (tbStages.getSelectedItem() != null) {
                int opResult = JOptionPane.showConfirmDialog(
                    null, "Добавить информацию об этапе лечения?",
                    "Изменение этапа лечения", JOptionPane.YES_NO_OPTION);
                if (opResult == JOptionPane.YES_OPTION) {
                    if (!tbStages.getSelectedItem().isSetId()) {
                        if (isStageAddRequiredFieldsSet(tbStages.getSelectedItem())) {
                            ClientHospital.tcl.addStage(tbStages.getSelectedItem());
                            tbStages.setData(
                                ClientHospital.tcl.getStage(model.getPatient().getGospitalCod()));
                        }
                    } else {
                        if (isStageAddRequiredFieldsSet(tbStages.getSelectedItem())) {
                            ClientHospital.tcl.updateStage(tbStages.getSelectedItem());
                            tbStages.setData(
                                ClientHospital.tcl.getStage(model.getPatient().getGospitalCod()));
                        }
                    }
                }
            }
        } catch (MesNotFoundException e) {
            JOptionPane.showMessageDialog(null,
                "Данного кода МЭС не существует!", "Ошибка!", JOptionPane.ERROR_MESSAGE);
        } catch (KmiacServerException e) {
            e.printStackTrace();
        } catch (TException e) {
            ClientHospital.conMan.reconnect(e);
        }
    }

    private void fillStageTable() {
        if (model.getPatient() != null) {
            try {
                List<TStage> tmpStages =
                    ClientHospital.tcl.getStage(model.getPatient().getGospitalCod());
                tbStages.setData(tmpStages);
            } catch (KmiacServerException e) {
                e.printStackTrace();
            } catch (TException e) {
                ClientHospital.conMan.reconnect(e);
            }
        }
    }

    public final Component getComponent() {
        return this;
    }

    public final String getPanelToolTipText() {
        return TOOLTIP_TEXT;
    }

    public final String getTitle() {
        return TITLE;
    }

    public final URL getIconURL() {
        return ICON;
    }

    @Override
    public final void patientChanged() {
        fillStageTable();
    }

}
