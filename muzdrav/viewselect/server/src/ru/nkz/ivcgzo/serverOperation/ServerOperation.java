package ru.nkz.ivcgzo.serverOperation;

import java.io.File;
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
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOperation.*;
import ru.nkz.ivcgzo.thriftOperation.ThriftOperation.Iface;

public class ServerOperation extends Server implements Iface {
    private static Logger log = Logger.getLogger(ServerOperation.class.getName());
    private TServer tServer;

    public ServerOperation(final ISqlSelectExecutor sse, final ITransactedSqlExecutor tse) {
        super(sse, tse);

        //Инициализация логгера с конфигом из файла ../../manager/log4j.xml;
        String manPath = new File(this.getClass().getProtectionDomain().getCodeSource()
            .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath();
        DOMConfigurator.configure(new File(manPath, "log4j.xml").getAbsolutePath());
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
        log.log(Level.INFO, "lab server stopped");
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
