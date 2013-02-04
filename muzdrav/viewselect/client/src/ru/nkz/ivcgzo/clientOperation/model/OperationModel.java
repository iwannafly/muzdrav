package ru.nkz.ivcgzo.clientOperation.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.clientOperation.ClientOperation;
import ru.nkz.ivcgzo.clientOperation.IOperationObserver;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOperation.Anesthesia;
import ru.nkz.ivcgzo.thriftOperation.AnesthesiaComplication;
import ru.nkz.ivcgzo.thriftOperation.AnesthesiaPaymentFund;
import ru.nkz.ivcgzo.thriftOperation.OperShablon;
import ru.nkz.ivcgzo.thriftOperation.Operation;
import ru.nkz.ivcgzo.thriftOperation.OperationComplication;
import ru.nkz.ivcgzo.thriftOperation.OperationPaymentFund;

public class OperationModel implements IOperationModel{
    private List<IOperationObserver> operationObservers = new ArrayList<IOperationObserver>();
    private Patient currentPatient;
    private Operation currentOperation;
    private OperationComplication currentOperationComplication;
    private OperationPaymentFund currentOperationPaymentFund;
    private Anesthesia currentAnesthesia;
    private AnesthesiaComplication currentAnesthesiaComplication;
    private AnesthesiaPaymentFund currentAnesthesiaPaymentFund;
    private List<Operation> operationsList;
    private List<OperationComplication> operationComplicationsList;
    private List<OperationPaymentFund> operationPaymentFundsList;
    private List<Anesthesia> anesthesiasList;
    private List<AnesthesiaComplication> anesthesiaComplicationsList;
    private List<AnesthesiaPaymentFund> anesthesiaPaymentFundsList;

    @Override
    public void setPatient(Patient patient) {
        currentPatient = patient;
    }

    @Override
    public Patient getPatient() {
        return currentPatient;
    }

    @Override
    public void setCurrentOperation(Operation inOperation) {
        currentOperation = inOperation;
        fireOperationChanged();
    }

    @Override
    public void setCurrentOperationComplication(
            OperationComplication inOperationComplication) {
        currentOperationComplication = inOperationComplication;
    }

    @Override
    public void setCurrentOperationPaymentFund(
            OperationPaymentFund inOperationPaymentFund) {
        currentOperationPaymentFund = inOperationPaymentFund;
    }

    @Override
    public void setCurrentAnesthesia(Anesthesia inAnesthesia) {
        currentAnesthesia = inAnesthesia;
        fireAnesthesiaChanged();
    }

    @Override
    public void setCurrentAnesthesiaComplication(
            AnesthesiaComplication inAnesthesiaComplication) {
        currentAnesthesiaComplication = inAnesthesiaComplication;
    }

    @Override
    public void setCurrentAnesthesiaPaymentFund(
            AnesthesiaPaymentFund inAnesthesiaPaymentFund) {
        currentAnesthesiaPaymentFund = inAnesthesiaPaymentFund;
    }

    @Override
    public Operation getCurrentOperation() {
        return currentOperation;
    }

    @Override
    public OperationComplication getCurrentOperationComplication() {
        return currentOperationComplication;
    }

    @Override
    public OperationPaymentFund getCurrentOperationPaymentFund() {
        return currentOperationPaymentFund;
    }

    @Override
    public Anesthesia getCurrentAnesthesia() {
        return currentAnesthesia;
    }

    @Override
    public AnesthesiaComplication getCurrentAnesthesiaComplication() {
        return currentAnesthesiaComplication;
    }

    @Override
    public AnesthesiaPaymentFund getCurrentAnesthesiaPaymentFund() {
        return currentAnesthesiaPaymentFund;
    }

    @Override
    public List<Operation> getOperationsList() {
        return operationsList;
    }

    @Override
    public List<OperationComplication> getOperationComplicationsList() {
        return operationComplicationsList;
    }

    @Override
    public List<OperationPaymentFund> getOperationPaymentFundsList() {
        return operationPaymentFundsList;
    }

    @Override
    public List<Anesthesia> getAnesthesiasList() {
        return anesthesiasList;
    }

    @Override
    public List<AnesthesiaComplication> getAnesthesiaComplicationsList() {
        return anesthesiaComplicationsList;
    }

    @Override
    public List<AnesthesiaPaymentFund> getAnesthesiaPaymentFundsList() {
        return anesthesiaPaymentFundsList;
    }

    @Override
    public void setOperationsList(int idGosp) throws KmiacServerException, TException {
        operationsList = ClientOperation.tcl.getOperations(idGosp);
        fireOperationChanged();
    }

    @Override
    public void setOperationComplicationsList(int idOper) throws KmiacServerException, TException {
        operationComplicationsList = ClientOperation.tcl.getOperationComplications(idOper);
        fireOperationComlicationChanged();
    }

    @Override
    public void setOperationPaymentFundsList(int idOper) throws KmiacServerException, TException {
        operationPaymentFundsList = ClientOperation.tcl.getOperationPaymentFunds(idOper);
        fireOperationPaymentFundChanged();
    }

    @Override
    public void setAnesthesiasList(int idOper) throws KmiacServerException, TException {
        anesthesiasList = ClientOperation.tcl.getAnesthesias(idOper);
        fireAnesthesiaChanged();
    }

    @Override
    public void setAnesthesiaComplicationsList(int idAnest) throws KmiacServerException, TException {
        anesthesiaComplicationsList = ClientOperation.tcl.getAnesthesiaComplications(idAnest);
        fireAnesthesiaComlicationChanged();
    }

    @Override
    public void setAnesthesiaPaymentFundsList(int idAnest) throws KmiacServerException, TException {
        anesthesiaPaymentFundsList = ClientOperation.tcl.getAnesthesiaPaymentFunds(idAnest);
        fireAnesthesiaPaymentFundChanged();
    }

    @Override
    public int addOperation(Operation operation) throws KmiacServerException, TException {
        return ClientOperation.tcl.addOperation(operation);        
    }

    @Override
    public void deleteOperation(Operation operation) throws KmiacServerException, TException {
        ClientOperation.tcl.deleteOperation(operation.getId());
    }

    @Override
    public void updateOperation(Operation operation) throws KmiacServerException, TException {
        ClientOperation.tcl.updateOperation(operation);
    }

    @Override
    public int addOperationComplication(
            OperationComplication operationComplication) throws KmiacServerException, TException {
        return ClientOperation.tcl.addOperationComplication(operationComplication);
    }

    @Override
    public void deleteOperationComplication(
            OperationComplication operationComplication) throws KmiacServerException, TException {
        ClientOperation.tcl.deleteOperationComplication(operationComplication.getId());
    }

    @Override
    public void updateOperationComplication(
            OperationComplication operationComplication) throws KmiacServerException, TException {
        ClientOperation.tcl.updateOperationComplication(operationComplication);
    }

    @Override
    public int addOperationPaymentFund(
            OperationPaymentFund operationPaymentFund) throws KmiacServerException, TException {
        return ClientOperation.tcl.addOperationPaymentFund(operationPaymentFund);
    }

    @Override
    public void deleteOperationPaymentFund(
            OperationPaymentFund operationPaymentFund) throws KmiacServerException, TException {
        ClientOperation.tcl.deleteOperationPaymentFund(operationPaymentFund.getId());
    }

    @Override
    public void updateOperationPaymentFund(
            OperationPaymentFund operationPaymentFund) throws KmiacServerException, TException {
        ClientOperation.tcl.updateOperationPaymentFund(operationPaymentFund);
    }

    @Override
    public int addAnesthesia(Anesthesia anesthesia) throws KmiacServerException, TException {
        return ClientOperation.tcl.addAnesthesia(anesthesia);
    }

    @Override
    public void deleteAnesthesia(Anesthesia anesthesia) throws KmiacServerException, TException {
        ClientOperation.tcl.deleteAnesthesia(anesthesia.getId());
    }

    @Override
    public void updateAnesthesia(Anesthesia anesthesia) throws KmiacServerException, TException {
        ClientOperation.tcl.updateAnesthesia(anesthesia);
    }

    @Override
    public int addAnesthesiaComplication(
            AnesthesiaComplication anesthesiaComplication) throws KmiacServerException, TException {
        return ClientOperation.tcl.addAnesthesiaComplication(anesthesiaComplication);
    }

    @Override
    public void deleteAnesthesiaComplication(
            AnesthesiaComplication anesthesiaComplication) throws KmiacServerException, TException {
        ClientOperation.tcl.deleteAnesthesiaComplication(anesthesiaComplication.getId());
    }

    @Override
    public void updateAnesthesiaComplication(
            AnesthesiaComplication anesthesiaComplication) throws KmiacServerException, TException {
        ClientOperation.tcl.updateAnesthesiaComplication(anesthesiaComplication);
    }

    @Override
    public int addAnesthesiaPaymentFund(
            AnesthesiaPaymentFund anesthesiaPaymentFund) throws KmiacServerException, TException {
        return ClientOperation.tcl.addAnesthesiaPaymentFund(anesthesiaPaymentFund);
    }

    @Override
    public void deleteAnesthesiaPaymentFund(
            AnesthesiaPaymentFund anesthesiaPaymentFund) throws KmiacServerException, TException {
        ClientOperation.tcl.deleteAnesthesiaPaymentFund(anesthesiaPaymentFund.getId());
    }

    @Override
    public void updateAnesthesiaPaymentFund(
            AnesthesiaPaymentFund anesthesiaPaymentFund) throws KmiacServerException, TException {
        ClientOperation.tcl.updateAnesthesiaPaymentFund(anesthesiaPaymentFund);
    }

    @Override
    public void registerOperationObserver(IOperationObserver obs) {
        operationObservers.add(obs);
    }

    @Override
    public void removeOperationObserver(IOperationObserver obs) {
        operationObservers.remove(obs);
    }

    private void fireOperationChanged() {
        for (IOperationObserver obs: operationObservers) {
            obs.currentOperationChanged();
        }
    }

    private void fireAnesthesiaChanged() {
        for (IOperationObserver obs: operationObservers) {
            obs.currentAnesthesiaChanged();
        }
    }

    private void fireOperationComlicationChanged() {
        for (IOperationObserver obs: operationObservers) {
            obs.operationComplicationChanged();
        }
    }

    private void fireOperationPaymentFundChanged() {
        for (IOperationObserver obs: operationObservers) {
            obs.operationPaymentFundChanged();
        }
    }

    private void fireAnesthesiaComlicationChanged() {
        for (IOperationObserver obs: operationObservers) {
            obs.anesthesiaComplicationChanged();
        }
    }

    private void fireAnesthesiaPaymentFundChanged() {
        for (IOperationObserver obs: operationObservers) {
            obs.anesthesiaPaymentFundChanged();
        }
    }

    @Override
    public List<IntegerClassifier> getOperationShablonList()
            throws KmiacServerException, TException {
        if (currentOperation != null) {
            return ClientOperation.tcl.getShablonNames(currentOperation.getPcod());
        } else {
            return Collections.<IntegerClassifier>emptyList();
        }
    }

    @Override
    public OperShablon getShablon(int id) throws KmiacServerException,
            TException {
        return ClientOperation.tcl.getShablon(id);
    }
}
