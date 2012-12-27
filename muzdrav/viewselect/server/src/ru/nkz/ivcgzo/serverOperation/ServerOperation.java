package ru.nkz.ivcgzo.serverOperation;

import java.io.File;
import java.sql.Date;
import java.sql.Time;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TNonblockingServerSocket;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOperation.*;
import ru.nkz.ivcgzo.thriftOperation.ThriftOperation.Iface;

public class ServerOperation extends Server implements Iface {
    private static final Logger log = Logger.getLogger(ServerOperation.class.getName());
    private TServer tServer;
    private TResultSetMapper<Operation, Operation._Fields> rsmOperation;
    private TResultSetMapper<OperationComplication,
            OperationComplication._Fields> rsmOperationComplication;
    private TResultSetMapper<OperationPaymentFund,
            OperationPaymentFund._Fields> rsmOperationPaymentFund;
    private TResultSetMapper<Anesthesia, Anesthesia._Fields> rsmAnesthesia;
    private TResultSetMapper<AnesthesiaComplication,
            AnesthesiaComplication._Fields> rsmAnesthesiaComplication;
    private TResultSetMapper<AnesthesiaPaymentFund,
            AnesthesiaPaymentFund._Fields> rsmAnesthesiaPaymentFund;

    private static final String[] OPERATION_FIELD_NAMES = {
            "id", "vid_st", "cotd", "id_gosp", "npasp", "pcod", "name_oper", "date", "vrem",
            "pred_ep", "op_oper", "material", "dlit", "dataz"
    };
    private static final String[] OPERATION_COMPLICATION_FIELD_NAMES = {
            "id", "id_oper", "name_osl", "pcod", "dataz"
    };
    private static final String[] OPERATION_PAYMENT_FUND_FIELD_NAMES = {
            "id", "id_oper", "pcod", "dataz"
    };
    private static final String[] ANESTHESIA_FIELD_NAMES = {
            "id", "vid_st", "cotd", "id_gosp", "npasp", "id_oper", "pcod", "name_an", "date",
            "vrem", "dataz"
    };
    private static final String[] ANESTHESIA_COMPLICATION_FIELD_NAMES = {
            "id", "id_anast", "name", "pcod", "dataz"
    };
    private static final String[] ANESTHESIA_PAYMENT_FUND_FIELD_NAMES = {
            "id", "id_anast", "pcod", "dataz"
    };

    private static final Class<?>[] OPERATION_TYPES ={
//          id             vid_st         cotd           id_gosp        npasp
            Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
//          pcod           name_oper     date        vrem        pred_ep       op_oper
            Integer.class, String.class, Date.class, Time.class, String.class, String.class,
//          material      dlit           dataz
            String.class, Integer.class, Date.class
    };
    private static final Class<?>[] OPERATION_COMPLICATION_TYPES ={
//          id           id_oper          name_osl      pcod           dataz
            Integer.class, Integer.class, String.class, Integer.class, Date.class
    };
    private static final Class<?>[] OPERATION_PAYMENT_FUND_TYPES ={
//          id             id_oper        pcod           dataz
            Integer.class, Integer.class, Integer.class, Date.class
    };
    private static final Class<?>[] ANESTHESIA_TYPES ={
//          id             vid_st         cotd           id_gosp        npasp
            Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
//          id_oper        pcod           name_an       date        vrem        dataz
            Integer.class, Integer.class, String.class, Date.class, Time.class, Date.class
    };
    private static final Class<?>[] ANESTHESIA_COMPLICATION_TYPES ={
//          id             id_anast       name          pcod           dataz
            Integer.class, Integer.class, String.class, Integer.class, Date.class
    };

    private static final Class<?>[] ANESTHESIA_PAYMENT_FUND_TYPES ={
//          id             id_anast       pcod           dataz
            Integer.class, Integer.class, Integer.class, Date.class
    };


    public ServerOperation(final ISqlSelectExecutor sse, final ITransactedSqlExecutor tse) {
        super(sse, tse);

        //Инициализация логгера с конфигом из файла ../../manager/log4j.xml;
        String manPath = new File(this.getClass().getProtectionDomain().getCodeSource()
            .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath();
        DOMConfigurator.configure(new File(manPath, "log4j.xml").getAbsolutePath());

        rsmOperation = new TResultSetMapper<>(Operation.class, OPERATION_FIELD_NAMES);
        rsmOperationComplication = new TResultSetMapper<>(OperationComplication.class,
                OPERATION_COMPLICATION_FIELD_NAMES);
        rsmOperationPaymentFund = new TResultSetMapper<>(OperationPaymentFund.class, 
                OPERATION_PAYMENT_FUND_FIELD_NAMES);
        rsmAnesthesia = new TResultSetMapper<>(Anesthesia.class, ANESTHESIA_FIELD_NAMES);
        rsmAnesthesiaComplication = new TResultSetMapper<>(AnesthesiaComplication.class,
                ANESTHESIA_COMPLICATION_FIELD_NAMES);
        rsmAnesthesiaPaymentFund = new TResultSetMapper<>(AnesthesiaPaymentFund.class,
                ANESTHESIA_PAYMENT_FUND_FIELD_NAMES);
    }

    @Override
    public void start() throws Exception {
        ThriftOperation.Processor<Iface> proc =
            new ThriftOperation.Processor<Iface>(this);

        tServer = new TThreadedSelectorServer(new Args(
            new TNonblockingServerSocket(configuration.opThrPort)).processor(proc));
        log.log(Level.INFO, "operation server started");
        tServer.serve();
    }

    @Override
    public void stop() {
        tServer.stop();
        log.log(Level.INFO, "operation server stopped");
    }

    @Override
    public void testConnection() throws TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void saveUserConfig(int id, String config) throws TException {
        // TODO Auto-generated method stub
    }

    @Override
    public List<Operation> getOperations(int idGosp) throws KmiacServerException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int addOperation(Operation curOperation) throws KmiacServerException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int updateOperation(Operation curOperation) throws KmiacServerException{
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteOperation(int id) throws KmiacServerException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int addOperationComplication(OperationComplication curCompl)
            throws KmiacServerException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int updateOperationComplication(OperationComplication curCompl)
            throws KmiacServerException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteOperationComplication(int id) throws KmiacServerException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int addOperationPaymentFund(OperationPaymentFund curPaymentFund)
            throws KmiacServerException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int updateOperationPaymentFund(OperationPaymentFund curPaymentFund)
            throws KmiacServerException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteOperationPaymentFund(int id) throws KmiacServerException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Anesthesia> getAnesthesias(int idGosp) throws KmiacServerException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int addAnesthesia(Anesthesia curAnesthesia) throws KmiacServerException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int updateAnesthesia(Anesthesia curAnesthesia) throws KmiacServerException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteAnesthesia(int id) throws KmiacServerException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int addAnesthesiaComplication(AnesthesiaComplication curCompl) throws KmiacServerException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int updateAnesthesiaComplication(AnesthesiaComplication curCompl) throws KmiacServerException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteAnesthesiaComplication(int id) throws KmiacServerException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int addAnesthesiaPaymentFund(AnesthesiaPaymentFund curPaymentFund) throws KmiacServerException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int updateAnesthesiaPaymentFund(AnesthesiaPaymentFund curPaymentFund) throws KmiacServerException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void deleteAnesthesiaPaymentFund(int id) throws KmiacServerException {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
