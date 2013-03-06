package ru.nkz.ivcgzo.clientHospital.controllers;

import org.apache.thrift.TException;

/**
 * Исключение для обертки исключений бросаемых при обмене данных.
 * Скрывает от контроллеров тонкости работы с данными.
 */
public class HospitalDataTransferException extends TException {
    private static final long serialVersionUID = 5844614901055611960L;

    private TException hiddenException;

    public HospitalDataTransferException(final String errorText, final TException excp) {
        super(errorText);
        hiddenException = excp;
    }

    /**
     * Возвращает скрытое исключение
     */
    public final TException getHiddenException() {
        return hiddenException;
    }
}
