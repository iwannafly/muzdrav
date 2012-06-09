package ru.nkz.ivcgzo.serverGenTalons;

import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TNonblockingServerSocket;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.thriftGenTalon.Calendar;
import ru.nkz.ivcgzo.thriftGenTalon.Ndv;
import ru.nkz.ivcgzo.thriftGenTalon.Norm;
import ru.nkz.ivcgzo.thriftGenTalon.Nrasp;
import ru.nkz.ivcgzo.thriftGenTalon.Rasp;
import ru.nkz.ivcgzo.thriftGenTalon.Spec;
import ru.nkz.ivcgzo.thriftGenTalon.Talon;
import ru.nkz.ivcgzo.thriftGenTalon.ThriftGenTalons;
import ru.nkz.ivcgzo.thriftGenTalon.ThriftGenTalons.Iface;
import ru.nkz.ivcgzo.thriftGenTalon.Vidp;
import ru.nkz.ivcgzo.thriftGenTalon.Vrach;

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

	@Override
	public List<Spec> getAllSpecForPolikliniki(int cpol) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Vrach> getVrachForCurrentSpec(String cdol) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Calendar> getCalendar(int cyear) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Ndv> getNdv(int pcodvrach, int cpol) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Norm> getNorm(int pcodvrach, int cpol) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Nrasp> getNrasp(int pcodvrach, int cpol, int cxema)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Rasp> getRasp(int pcodvrach, int cpol) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Talon> getTalon(int pcodvrach, int cpol, long datap)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Vidp> getVidp() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

}
