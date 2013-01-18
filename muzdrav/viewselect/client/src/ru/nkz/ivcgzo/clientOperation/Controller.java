package ru.nkz.ivcgzo.clientOperation;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientOperation.model.IOperationModel;
import ru.nkz.ivcgzo.clientOperation.model.Patient;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOperation.Anesthesia;
import ru.nkz.ivcgzo.thriftOperation.AnesthesiaComplication;
import ru.nkz.ivcgzo.thriftOperation.AnesthesiaPaymentFund;
import ru.nkz.ivcgzo.thriftOperation.Operation;
import ru.nkz.ivcgzo.thriftOperation.OperationComplication;
import ru.nkz.ivcgzo.thriftOperation.OperationPaymentFund;

public class Controller implements IController{
    private IOperationModel model;
    private MainFrame view;

    public Controller(final IOperationModel curModel) {
        this.model = curModel;
        this.view = new MainFrame(this, model);
        view.createFrame();
        view.setControls();
    }

    @Override
    public void setPatient(Patient patient) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setCurrentOperation(Operation inOperation) {
        model.setCurrentOperation(inOperation);
    }

    @Override
    public void setCurrentOperationComplication(
            OperationComplication inOperationComplication) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setCurrentOperationPaymentFund(
            OperationPaymentFund inOperationPaymentFund) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setCurrentAnesthesia(Anesthesia inAnesthesia) {
        model.setCurrentAnesthesia(inAnesthesia);
    }

    @Override
    public void setCurrentAnesthesiaComplication(
            AnesthesiaComplication inAnesthesiaComplication) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setCurrentAnesthesiaPaymentFund(
            AnesthesiaPaymentFund inAnesthesiaPaymentFund) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void setOperationsList() {
        try {
            model.setOperationsList(model.getPatient().getIdGosp());
        } catch (KmiacServerException e) {
            JOptionPane.showMessageDialog(null, "Ошибка при загрузке списка операций");
        } catch (TException e) {
            JOptionPane.showMessageDialog(null, "Ошибка при загрузке списка операций");
            ClientOperation.conMan.reconnect(e);
        }
    }

    @Override
    public void setOperationComplicationsList() {
        if (model.getCurrentOperation() != null) {
            try {
                model.setOperationComplicationsList(model.getCurrentOperation().getId());
            } catch (KmiacServerException e) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при загрузке списка осложнений выбранной операции");
            } catch (TException e) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при загрузке списка осложнений выбранной операции");
                ClientOperation.conMan.reconnect(e);
            }
        }
    }

    @Override
    public void setOperationPaymentFundsList() {
        if (model.getCurrentOperation() != null) {
            try {
                model.setOperationPaymentFundsList(model.getCurrentOperation().getId());
            } catch (KmiacServerException e) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при загрузке списка фондов оплаты выбранной операции");
            } catch (TException e) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при загрузке списка фондов оплаты выбранной операции");
                ClientOperation.conMan.reconnect(e);
            }
        }
    }

    @Override
    public void setAnesthesiasList() {
        if (model.getCurrentOperation() != null) {
            try {
                model.setAnesthesiasList(model.getCurrentOperation().getId());
            } catch (KmiacServerException e) {
                JOptionPane.showMessageDialog(null, "Ошибка при загрузке списка анестезии");
            } catch (TException e) {
                JOptionPane.showMessageDialog(null, "Ошибка при загрузке списка анестезии");
                ClientOperation.conMan.reconnect(e);
            }
        }
    }

    @Override
    public void setAnesthesiaComplicationsList() {
        if (model.getCurrentAnesthesia() != null) {
            try {
                model.setAnesthesiaComplicationsList(model.getCurrentAnesthesia().getId());
            } catch (KmiacServerException e) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при загрузке списка осложнений выбранной анестезии");
            } catch (TException e) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при загрузке списка осложнений выбранной анестезии");
                ClientOperation.conMan.reconnect(e);
            }
        }
    }

    @Override
    public void setAnesthesiaPaymentFundsList() {
        if (model.getCurrentAnesthesia() != null) {
            try {
                model.setAnesthesiaPaymentFundsList(model.getCurrentAnesthesia().getId());
            } catch (KmiacServerException e) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при загрузке списка фондов оплаты выбранной анестезии");
            } catch (TException e) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при загрузке списка фондов оплаты выбранной анестезии");
                ClientOperation.conMan.reconnect(e);
            } 
        }
    }

    @Override
    public void addOperation() {
        try {
            Operation curOper = new Operation();
            curOper.setIdGosp(model.getPatient().getIdGosp());
            curOper.setCotd(ClientOperation.authInfo.getCpodr());
            curOper.setDataz(System.currentTimeMillis());
            curOper.setVrem(System.currentTimeMillis());
            curOper.setDate(System.currentTimeMillis());
            curOper.setNpasp(model.getPatient().getPcod());
            curOper.setId(model.addOperation(curOper));
            model.setOperationsList(model.getPatient().getIdGosp());
        } catch (KmiacServerException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при добавлении операции");
            e1.printStackTrace();
        } catch (TException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при добавлении операции");
            e1.printStackTrace();
            ClientOperation.conMan.reconnect(e1);
        }
    }

    @Override
    public void deleteOperation() {
        int opResult = JOptionPane.showConfirmDialog(
                null, "Удалить запись?",
                "Удаление записи", JOptionPane.YES_NO_OPTION);
        if (opResult == JOptionPane.YES_OPTION) {
            try {
                model.deleteOperation(model.getCurrentOperation());
                model.setOperationsList(model.getPatient().getIdGosp());
            } catch (KmiacServerException e1) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при удалении выбранной операции");
                e1.printStackTrace();
            } catch (TException e1) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при удалении выбранной операции");
                e1.printStackTrace();
                ClientOperation.conMan.reconnect(e1);
            }
        }
    }

    @Override
    public void updateOperation(Operation operation) {
        try {
            model.updateOperation(operation);
            model.setOperationsList(model.getPatient().getIdGosp());
        } catch (KmiacServerException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при обновлении выбранной операции");
            e1.printStackTrace();
        } catch (TException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при обновлении выбранной операции");
            e1.printStackTrace();
            ClientOperation.conMan.reconnect(e1);
        }
    }

    @Override
    public void addOperationComplication() {
        try {
            OperationComplication curOperComplication = new OperationComplication();
            curOperComplication.setDataz(System.currentTimeMillis());
            curOperComplication.setIdOper(model.getCurrentOperation().getId());
            curOperComplication.setId(
                    model.addOperationComplication(curOperComplication));
            model.setOperationsList(model.getCurrentOperation().getIdGosp());
        } catch (KmiacServerException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при добавлении осложнения выбранной операции");
            e1.printStackTrace();
        } catch (TException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при добавлении осложнения выбранной операции");
            ClientOperation.conMan.reconnect(e1);
        }
    }

    @Override
    public void deleteOperationComplication(
            OperationComplication operationComplication) {
        int opResult = JOptionPane.showConfirmDialog(null, "Удалить запись?",
                "Удаление записи", JOptionPane.YES_NO_OPTION);
        if (opResult == JOptionPane.YES_OPTION) {
            try {
                model.deleteOperationComplication(operationComplication);
                model.setOperationComplicationsList(model.getCurrentOperation().getId());
            } catch (KmiacServerException e1) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при удалении осложнения выбранной операции");
                e1.printStackTrace();
            } catch (TException e1) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при удалении осложнения выбранной операции");
                e1.printStackTrace();
                ClientOperation.conMan.reconnect(e1);
            }
        }
    }

    @Override
    public void updateOperationComplication(
            OperationComplication operationComplication) {
        try {
            model.updateOperationComplication(operationComplication);
            model.setOperationComplicationsList(model.getCurrentOperation().getId());
        } catch (KmiacServerException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при обновлении осложнения выбранной операции");
            e1.printStackTrace();
        } catch (TException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при обновлении осложнения выбранной операции");
            ClientOperation.conMan.reconnect(e1);
        }
    }

    @Override
    public void addOperationPaymentFund() {
        try {
            OperationPaymentFund curOperPaymentFunds = new OperationPaymentFund();
            curOperPaymentFunds.setDataz(System.currentTimeMillis());
            curOperPaymentFunds.setIdOper(model.getCurrentOperation().getId());
            curOperPaymentFunds.setId(
                    model.addOperationPaymentFund(curOperPaymentFunds));
            model.setOperationPaymentFundsList(model.getCurrentOperation().getId());
        } catch (KmiacServerException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при добавлении фонда оплаты выбранной операции");
            e1.printStackTrace();
        } catch (TException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при добавлении фонда оплаты выбранной операции");
            e1.printStackTrace();
            ClientOperation.conMan.reconnect(e1);
        }
    }

    @Override
    public void deleteOperationPaymentFund(
            OperationPaymentFund operationPaymentFund) {
        int opResult = JOptionPane.showConfirmDialog(null, "Удалить запись?",
                "Удаление записи", JOptionPane.YES_NO_OPTION);
        if (opResult == JOptionPane.YES_OPTION) {
            try {
                model.deleteOperationPaymentFund(operationPaymentFund);
                model.setOperationPaymentFundsList(model.getCurrentOperation().getId());
            } catch (KmiacServerException e1) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при удалении фонда оплаты выбранной операции");
                e1.printStackTrace();
            } catch (TException e1) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при удалении фонда оплаты выбранной операции");
                e1.printStackTrace();
                ClientOperation.conMan.reconnect(e1);
            }
        }
    }

    @Override
    public void updateOperationPaymentFund(
            OperationPaymentFund operationPaymentFund) {
        try {
            model.updateOperationPaymentFund(operationPaymentFund);
            model.setOperationPaymentFundsList(model.getCurrentOperation().getId());
        } catch (KmiacServerException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при обновлении фонда оплаты выбранной операции");
            e1.printStackTrace();
        } catch (TException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при обновлении фонда оплаты выбранной операции");
            e1.printStackTrace();
            ClientOperation.conMan.reconnect(e1);
        }
    }

    @Override
    public void addAnesthesia() {
        try {
            Anesthesia curAnesthesia = new Anesthesia();
            curAnesthesia.setDataz(System.currentTimeMillis());
            curAnesthesia.setIdOper(model.getCurrentOperation().getId());
            curAnesthesia.setCotd(ClientOperation.authInfo.getCpodr());
            curAnesthesia.setDate(System.currentTimeMillis());
            curAnesthesia.setIdGosp(model.getPatient().getIdGosp());
            curAnesthesia.setVrem(System.currentTimeMillis());
            curAnesthesia.setNpasp(model.getPatient().getPcod());
            curAnesthesia.setId(model.addAnesthesia(curAnesthesia));
            model.setAnesthesiasList(model.getCurrentOperation().getId());
        } catch (KmiacServerException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при добавлении анестезии");
            e1.printStackTrace();
        } catch (TException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при добавлении анестезии");
            e1.printStackTrace();
            ClientOperation.conMan.reconnect(e1);
        }
    }

    @Override
    public void deleteAnesthesia() {
        int opResult = JOptionPane.showConfirmDialog(null, "Удалить запись?",
                "Удаление записи", JOptionPane.YES_NO_OPTION);
        if (opResult == JOptionPane.YES_OPTION) {
            try {
                model.deleteAnesthesia(model.getCurrentAnesthesia());
                model.setAnesthesiasList(model.getCurrentAnesthesia().getId());
            } catch (KmiacServerException e1) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при удалении анестезии");
                e1.printStackTrace();
            } catch (TException e1) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при удалении анестезии");
                e1.printStackTrace();
                ClientOperation.conMan.reconnect(e1);
            }
        }
    }

    @Override
    public void updateAnesthesia() {
        try {
            model.updateAnesthesia(model.getCurrentAnesthesia());
            model.setAnesthesiasList(model.getCurrentAnesthesia().getId());
        } catch (KmiacServerException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при обновлении анестезии");
            e1.printStackTrace();
        } catch (TException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при обновлении анестезии");
            e1.printStackTrace();
            ClientOperation.conMan.reconnect(e1);
        }
    }

    @Override
    public void addAnesthesiaComplication() {
        try {
            AnesthesiaComplication curAnesthesiaComplication =
                    new AnesthesiaComplication();
            curAnesthesiaComplication.setDataz(System.currentTimeMillis());
            curAnesthesiaComplication.setIdAnast(model.getCurrentAnesthesia().getId());
            curAnesthesiaComplication.setId(
                    model.addAnesthesiaComplication(curAnesthesiaComplication)
            );
            model.setAnesthesiaComplicationsList(model.getCurrentAnesthesia().getId());
        } catch (KmiacServerException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при добавлении осложнения анестезии");
            e1.printStackTrace();
        } catch (TException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при добавлении осложнения анестезии");
            e1.printStackTrace();
            ClientOperation.conMan.reconnect(e1);
        }
    }

    @Override
    public void deleteAnesthesiaComplication(
            AnesthesiaComplication anesthesiaComplication) {
        int opResult = JOptionPane.showConfirmDialog(
                null, "Удалить запись?",
                "Удаление записи", JOptionPane.YES_NO_OPTION);
        if (opResult == JOptionPane.YES_OPTION) {
            try {
                model.deleteAnesthesiaComplication(anesthesiaComplication);
                model.setAnesthesiaComplicationsList(model.getCurrentAnesthesia().getId());
            } catch (KmiacServerException e1) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при удалении осложнения анестезии");
                e1.printStackTrace();
            } catch (TException e1) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при удалении осложнения анестезии");
                e1.printStackTrace();
                ClientOperation.conMan.reconnect(e1);
            }
        }
    }

    @Override
    public void updateAnesthesiaComplication(
            AnesthesiaComplication anesthesiaComplication) {
        try {
            model.updateAnesthesiaComplication(anesthesiaComplication);
            model.setAnesthesiaComplicationsList(model.getCurrentAnesthesia().getId());
        } catch (KmiacServerException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при обновлении осложнения анестезии");
            e1.printStackTrace();
        } catch (TException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при обновлении осложнения анестезии");
            e1.printStackTrace();
            ClientOperation.conMan.reconnect(e1);
        }
    }

    @Override
    public void addAnesthesiaPaymentFund() {
        try {
            AnesthesiaPaymentFund curAnesthesiaPaymentFund =
                    new AnesthesiaPaymentFund();
            curAnesthesiaPaymentFund.setDataz(System.currentTimeMillis());
            curAnesthesiaPaymentFund.setIdAnast(model.getCurrentAnesthesia().getId());
            curAnesthesiaPaymentFund.setId(
                    model.addAnesthesiaPaymentFund(curAnesthesiaPaymentFund)
            );
            model.setAnesthesiaPaymentFundsList(model.getCurrentAnesthesia().getId());
        } catch (KmiacServerException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при добавлении фонда оплаты анестезии");
            e1.printStackTrace();
        } catch (TException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при добавлении фонда оплаты анестезии");
            e1.printStackTrace();
            ClientOperation.conMan.reconnect(e1);
        }
    }

    @Override
    public void deleteAnesthesiaPaymentFund(
            AnesthesiaPaymentFund anesthesiaPaymentFund) {
        int opResult = JOptionPane.showConfirmDialog(null, "Удалить запись?",
                "Удаление записи", JOptionPane.YES_NO_OPTION);
        if (opResult == JOptionPane.YES_OPTION) {
            try {
                model.deleteAnesthesiaPaymentFund(anesthesiaPaymentFund);
                model.setAnesthesiaPaymentFundsList(model.getCurrentAnesthesia().getId());
            } catch (KmiacServerException e1) {

                JOptionPane.showMessageDialog(null,
                        "Ошибка при удалении фонда оплаты анестезии");
                e1.printStackTrace();
            } catch (TException e1) {
                JOptionPane.showMessageDialog(null,
                        "Ошибка при удалении фонда оплаты анестезии");
                e1.printStackTrace();
                ClientOperation.conMan.reconnect(e1);
            }
        }
    }

    @Override
    public void updateAnesthesiaPaymentFund(
            AnesthesiaPaymentFund anesthesiaPaymentFund) {
        try {
            model.updateAnesthesiaPaymentFund(anesthesiaPaymentFund);
            model.setAnesthesiaPaymentFundsList(model.getCurrentAnesthesia().getId());
        } catch (KmiacServerException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при обновлении фонда оплаты анестезии");
            e1.printStackTrace();
        } catch (TException e1) {
            JOptionPane.showMessageDialog(null,
                    "Ошибка при обновлении фонда оплаты анестезии");
            e1.printStackTrace();
            ClientOperation.conMan.reconnect(e1);
        }
    }

    @Override
    public JFrame getMainFrame() {
        return view.getMainFrame();
    }

    @Override
    public void onConnect() {
    }

    @Override
    public void setTitle(String title) {
        view.setTitle(title);
    }

    @Override
    public void fillPatient(int id, String surname, String name,
            String middlename, int idGosp) {
        model.setPatient(new Patient(id,surname, name, middlename, idGosp));
        this.setOperationsList();
        view.updateOperationTable();
    }

}
