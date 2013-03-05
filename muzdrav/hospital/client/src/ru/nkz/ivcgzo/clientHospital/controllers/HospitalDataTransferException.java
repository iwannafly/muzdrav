package ru.nkz.ivcgzo.clientHospital.controllers;

import org.apache.thrift.TException;

public class HospitalDataTransferException extends TException {
    private static final long serialVersionUID = 5844614901055611960L;

    private TException hiddenException;

    public HospitalDataTransferException(final String errorText, final TException excp) {
        super(errorText);
        hiddenException = excp;
    }

    public final TException getHiddenException() {
        return hiddenException;
    }
}
