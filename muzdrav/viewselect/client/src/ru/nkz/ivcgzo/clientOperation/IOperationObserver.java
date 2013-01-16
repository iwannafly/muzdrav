package ru.nkz.ivcgzo.clientOperation;

public interface IOperationObserver {
    void currentOperationChanged();
    void operationComplicationChanged();
    void operationPaymentFundChanged();
    void currentAnesthesiaChanged();
    void anesthesiaComplicationChanged();
    void anesthesiaPaymentFundChanged();
}
