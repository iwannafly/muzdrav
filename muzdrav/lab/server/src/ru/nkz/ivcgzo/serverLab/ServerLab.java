package ru.nkz.ivcgzo.serverLab;

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
import ru.nkz.ivcgzo.thriftLab.Metod;
import ru.nkz.ivcgzo.thriftLab.Pisl;
import ru.nkz.ivcgzo.thriftLab.Pokaz;
import ru.nkz.ivcgzo.thriftLab.PokazMet;
import ru.nkz.ivcgzo.thriftLab.PrezD;
import ru.nkz.ivcgzo.thriftLab.PrezL;
import ru.nkz.ivcgzo.thriftLab.ThriftLab;
import ru.nkz.ivcgzo.thriftLab.ThriftLab.Iface;

public class ServerLab extends Server implements Iface{
    private static Logger log = Logger.getLogger(ServerLab.class.getName());
    private TServer tServer;

    public ServerLab(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
        super(sse, tse);

        //Инициализация логгера с конфигом из файла ../../manager/log4j.xml;
        String manPath = new File(this.getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath();
        DOMConfigurator.configure(new File(manPath, "log4j.xml").getAbsolutePath());
    }

    @Override
    public void start() throws Exception {
        ThriftLab.Processor<Iface> proc =
                new ThriftLab.Processor<Iface>(this);

        tServer = new TThreadedSelectorServer(new Args(
                new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
        log.log(Level.INFO, "lab server started");
        tServer.serve();
    }

    @Override
    public void stop() {
        tServer.stop();
        log.log(Level.INFO, "lab server stopped");
    }

    @Override
    public void testConnection() throws TException {
    }

    @Override
    public void saveUserConfig(int id, String config) throws TException {
    }

    @Override
    public List<Metod> getMetod(int kodissl) throws KmiacServerException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<PokazMet> getPokazMet(String cNnz1)
            throws KmiacServerException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Pokaz> getPokaz(int kodissl, String kodsyst)
            throws KmiacServerException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int AddPisl(Pisl npisl) throws KmiacServerException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int AddPrezd(PrezD di) throws KmiacServerException {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int AddPrezl(PrezL li) throws KmiacServerException {
        // TODO Auto-generated method stub
        return 0;
    }
}
