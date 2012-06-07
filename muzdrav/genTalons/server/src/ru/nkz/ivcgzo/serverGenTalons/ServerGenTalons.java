package ru.nkz.ivcgzo.serverGenTalons;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TNonblockingServerSocket;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.thriftGenTalon.ThriftGenTalons;
import ru.nkz.ivcgzo.thriftGenTalon.ThriftGenTalons.Iface;

public class ServerGenTalons extends Server implements Iface {
    private TServer thrServ;

    public ServerGenTalons(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
        super(sse, tse);
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
    public void start() throws Exception {
        ThriftGenTalons.Processor<Iface> proc =
                new ThriftGenTalons.Processor<Iface>(this);
        thrServ = new TThreadedSelectorServer(new Args(
                new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
        thrServ.serve();
    }

    @Override
    public void stop() {
        if (thrServ != null) {
            thrServ.stop();
        }
    }

}
