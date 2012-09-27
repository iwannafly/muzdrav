package ru.nkz.ivcgzo.clientReception;

import java.sql.Time;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TFramedTransport;
import org.apache.thrift.transport.TSocket;
import org.junit.Before;
import org.junit.Test;

import ru.nkz.ivcgzo.thriftReception.Talon;
import ru.nkz.ivcgzo.thriftReception.ThriftReception;

public class TestTalonList {
    private TFramedTransport frt;
    private TProtocol protocol;
    private ThriftReception.Client client;
    private TalonList testTalonList;
    private TSocket sct;

    @Before
    public final void setUp() throws Exception {
        sct = new TSocket("localhost", 55010);
        frt = new TFramedTransport(sct);
        protocol = new TBinaryProtocol(frt);
        client = new ThriftReception.Client(protocol);
        frt.open();
        System.out.println(client.getTalon(2000004, "8", 48).size());
        testTalonList = new TalonList(client.getTalon(2000004, "8", 48));
    }

    @Test
    public final void getMondayTalons_IsValuesCorrect() {
        for (Talon mondayTalon:testTalonList.getTuesdayTalonList()) {
            System.out.println(new Time(mondayTalon.getTimep()));
        }
    }

}
