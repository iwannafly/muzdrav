package ru.nkz.ivcgzo.serverMedication;

import java.io.File;

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
import ru.nkz.ivcgzo.thriftMedication.ThriftMedication;
import ru.nkz.ivcgzo.thriftMedication.ThriftMedication.Iface;

public class ServerMedication extends Server implements Iface {
    private static Logger log = Logger.getLogger(ServerMedication.class.getName());
    private TServer tServer;

    public ServerMedication(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
        super(sse, tse);

        //Инициализация логгера с конфигом из файла ../../manager/log4j.xml;
        String manPath = new File(this.getClass().getProtectionDomain().getCodeSource()
            .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath();
        DOMConfigurator.configure(new File(manPath, "log4j.xml").getAbsolutePath());
    }

    @Override
    public void start() throws Exception {
        ThriftMedication.Processor<Iface> proc =
            new ThriftMedication.Processor<Iface>(this);

        tServer = new TThreadedSelectorServer(new Args(
            new TNonblockingServerSocket(configuration.medThrPort)).processor(proc));
        log.log(Level.INFO, "medication server started");
        tServer.serve();
    }

    @Override
    public void stop() {
        tServer.stop();
        log.log(Level.INFO, "medication server stopped");
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
    public int getInt() throws TException {
        // TODO Auto-generated method stub
        return 0;
    }
}
