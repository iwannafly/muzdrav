package ru.nkz.ivcgzo.clientOperation.model;

import java.util.List;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientOperation.IOperationObserver;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOperation.Anesthesia;
import ru.nkz.ivcgzo.thriftOperation.AnesthesiaComplication;
import ru.nkz.ivcgzo.thriftOperation.AnesthesiaPaymentFund;
import ru.nkz.ivcgzo.thriftOperation.Operation;
import ru.nkz.ivcgzo.thriftOperation.OperationComplication;
import ru.nkz.ivcgzo.thriftOperation.OperationPaymentFund;

public interface IOperationModel {

    void setPatient(Patient patient);

    Patient getPatient();

    void setCurrentOperation(Operation inOperation);

    void setCurrentOperationComplication(OperationComplication inOperationComplication);

    void setCurrentOperationPaymentFund(OperationPaymentFund inOperationPaymentFund);

    void setCurrentAnesthesia(Anesthesia inAnesthesia);

    void setCurrentAnesthesiaComplication(AnesthesiaComplication inAnesthesiaComplication);

    void setCurrentAnesthesiaPaymentFund(AnesthesiaPaymentFund inAnesthesiaPaymentFund);

    Operation getCurrentOperation();

    OperationComplication getCurrentOperationComplication();

    OperationPaymentFund getCurrentOperationPaymentFund();

    Anesthesia getCurrentAnesthesia();

    AnesthesiaComplication getCurrentAnesthesiaComplication();

    AnesthesiaPaymentFund getCurrentAnesthesiaPaymentFund();

    List<Operation> getOperationsList();

    List<OperationComplication> getOperationComplicationsList();

    List<OperationPaymentFund> getOperationPaymentFundsList();

    List<Anesthesia> getAnesthesiasList();

    List<AnesthesiaComplication> getAnesthesiaComplicationsList();

    List<AnesthesiaPaymentFund> getAnesthesiaPaymentFundsList();

    void setOperationsList(int idGosp) throws KmiacServerException, TException;

    void setOperationComplicationsList(int idOper) throws KmiacServerException, TException;

    void setOperationPaymentFundsList(int idOper) throws KmiacServerException, TException;

    void setAnesthesiasList(int idOper) throws KmiacServerException, TException;

    void setAnesthesiaComplicationsList(int idAnest) throws KmiacServerException, TException;

    void setAnesthesiaPaymentFundsList(int idAnest) throws KmiacServerException, TException;

    int addOperation(Operation operation) throws KmiacServerException, TException;

    void deleteOperation(Operation operation) throws KmiacServerException, TException;

    void updateOperation(Operation operation) throws KmiacServerException, TException;

    int addOperationComplication(OperationComplication operationComplication) throws KmiacServerException, TException;

    void deleteOperationComplication(OperationComplication operationComplication) throws KmiacServerException, TException;

    void updateOperationComplication(OperationComplication operationComplication) throws KmiacServerException, TException;

    int addOperationPaymentFund(OperationPaymentFund operationPaymentFund) throws KmiacServerException, TException;

    void deleteOperationPaymentFund(OperationPaymentFund operationPaymentFund) throws KmiacServerException, TException;

    void updateOperationPaymentFund(OperationPaymentFund operationPaymentFund) throws KmiacServerException, TException;

    int addAnesthesia(Anesthesia anesthesia) throws KmiacServerException, TException;

    void deleteAnesthesia(Anesthesia anesthesia) throws KmiacServerException, TException;

    void updateAnesthesia(Anesthesia anesthesia) throws KmiacServerException, TException;

    int addAnesthesiaComplication(AnesthesiaComplication anesthesiaComplication) throws KmiacServerException, TException;

    void deleteAnesthesiaComplication(AnesthesiaComplication anesthesiaComplication) throws KmiacServerException, TException;

    void updateAnesthesiaComplication(AnesthesiaComplication anesthesiaComplication) throws KmiacServerException, TException;

    int addAnesthesiaPaymentFund(AnesthesiaPaymentFund anesthesiaPaymentFund) throws KmiacServerException, TException;

    void deleteAnesthesiaPaymentFund(AnesthesiaPaymentFund anesthesiaPaymentFund) throws KmiacServerException, TException;

    void updateAnesthesiaPaymentFund(AnesthesiaPaymentFund anesthesiaPaymentFund) throws KmiacServerException, TException;

    void registerOperationObserver(IOperationObserver obs);

    void removeOperationObserver(IOperationObserver obs);

}
