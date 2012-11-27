package ru.nkz.ivcgzo.serverPbol1;

import java.sql.Date;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftPbol.Pbol;
import ru.nkz.ivcgzo.thriftPbol.ThriftPbol;
import ru.nkz.ivcgzo.thriftPbol.ThriftPbol.Iface;

public class ServerPbol1 extends Server implements Iface {
	private TServer thrServ;
	private final TResultSetMapper<Pbol, Pbol._Fields> rsmPbol;
	private final Class<?>[] pbolTypes; 

	public ServerPbol1(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		rsmPbol = new TResultSetMapper<>(Pbol.class, "id",          "id_obr",      "id_gosp",     "npasp",       "bol_l",       "s_bl", 	"po_bl",    "pol",         "vozr",        "nombl", 	    "cod_sp",      "cdol",       "pcod",        "dataz");
		pbolTypes = new Class<?>[] {                 Integer.class, Integer.class, Integer.class, Integer.class, Integer.class, Date.class, Date.class, Integer.class, Integer.class, String.class, Integer.class, String.class, Integer.class, Date.class};

		// TODO Auto-generated constructor stub
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
		ThriftPbol.Processor<Iface> proc = new ThriftPbol.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();


	}

	@Override
	public void stop() {
		if (thrServ != null)
			thrServ.stop();


	}

	@Override
	public List<Pbol> getPbol(int npasp) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

}
