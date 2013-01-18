package ru.nkz.ivcgzo.clientOperation;

import javax.swing.JFrame;

import ru.nkz.ivcgzo.clientOperation.model.Patient;
import ru.nkz.ivcgzo.thriftOperation.Anesthesia;
import ru.nkz.ivcgzo.thriftOperation.AnesthesiaComplication;
import ru.nkz.ivcgzo.thriftOperation.AnesthesiaPaymentFund;
import ru.nkz.ivcgzo.thriftOperation.Operation;
import ru.nkz.ivcgzo.thriftOperation.OperationComplication;
import ru.nkz.ivcgzo.thriftOperation.OperationPaymentFund;

public interface IController {

    void setPatient(Patient patient);

    void setCurrentOperation(Operation inOperation);

    void setCurrentOperationComplication(OperationComplication inOperationComplication);

    void setCurrentOperationPaymentFund(OperationPaymentFund inOperationPaymentFund);

    void setCurrentAnesthesia(Anesthesia inAnesthesia);

    void setCurrentAnesthesiaComplication(AnesthesiaComplication inAnesthesiaComplication);

    void setCurrentAnesthesiaPaymentFund(AnesthesiaPaymentFund inAnesthesiaPaymentFund);

    void setOperationsList();

    void setOperationComplicationsList();

    void setOperationPaymentFundsList();

    void setAnesthesiasList();

    void setAnesthesiaComplicationsList();

    void setAnesthesiaPaymentFundsList();

    void addOperation();

    void deleteOperation();

    void updateOperation(Operation operation);

    void addOperationComplication();

    void deleteOperationComplication(OperationComplication operationComplication);

    void updateOperationComplication(OperationComplication operationComplication);

    void addOperationPaymentFund();

    void deleteOperationPaymentFund(OperationPaymentFund operationPaymentFund);

    void updateOperationPaymentFund(OperationPaymentFund operationPaymentFund);

    void addAnesthesia();

    void deleteAnesthesia();

    void updateAnesthesia();

    void addAnesthesiaComplication();

    void deleteAnesthesiaComplication(AnesthesiaComplication anesthesiaComplication);

    void updateAnesthesiaComplication(AnesthesiaComplication anesthesiaComplication);

    void addAnesthesiaPaymentFund();

    void deleteAnesthesiaPaymentFund(AnesthesiaPaymentFund anesthesiaPaymentFund);

    void updateAnesthesiaPaymentFund(AnesthesiaPaymentFund anesthesiaPaymentFund);

    //костыль
    JFrame getMainFrame();

    void onConnect();

    void setTitle(String format);

    void fillPatient(int id, String surname, String name, String middlename, int idGosp);
}
