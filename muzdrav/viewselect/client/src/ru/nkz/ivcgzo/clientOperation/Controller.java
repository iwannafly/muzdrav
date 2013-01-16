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
//        view.createControls();
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
        // TODO Auto-generated method stub
        
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
            } 
        }
    }

    @Override
    public void addOperation(Operation operation) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteOperation(Operation operation) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateOperation(Operation operation) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addOperationComplication(
            OperationComplication operationComplication) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteOperationComplication(
            OperationComplication operationComplication) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateOperationComplication(
            OperationComplication operationComplication) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addOperationPaymentFund(
            OperationPaymentFund operationPaymentFund) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteOperationPaymentFund(
            OperationPaymentFund operationPaymentFund) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateOperationPaymentFund(
            OperationPaymentFund operationPaymentFund) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addAnesthesia(Anesthesia anesthesia) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteAnesthesia(Anesthesia anesthesia) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateAnesthesia(Anesthesia anesthesia) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addAnesthesiaComplication(
            AnesthesiaComplication anesthesiaComplication) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteAnesthesiaComplication(
            AnesthesiaComplication anesthesiaComplication) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateAnesthesiaComplication(
            AnesthesiaComplication anesthesiaComplication) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void addAnesthesiaPaymentFund(
            AnesthesiaPaymentFund anesthesiaPaymentFund) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteAnesthesiaPaymentFund(
            AnesthesiaPaymentFund anesthesiaPaymentFund) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void updateAnesthesiaPaymentFund(
            AnesthesiaPaymentFund anesthesiaPaymentFund) {
        // TODO Auto-generated method stub

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
        view.fillPatient(id, surname, name, middlename, idGosp);
        this.setOperationsList();
        view.updateOperationTable();
//        view.removeOperationTableSelection();
    }

}
