package ru.nkz.ivcgzo.serverViewSelect;

import java.sql.SQLException;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TNonblockingServerSocket;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftViewSelect.ThriftViewSelect;
import ru.nkz.ivcgzo.thriftViewSelect.ThriftViewSelect.Iface;

public class ServerViewSelect extends Server implements Iface {
	
	private TServer thrServ;
	public String classname = "n_l01";
	public ServerViewSelect(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
	}

	@Override
	public void testConnection() throws TException {
		
	}

	@Override
	public void saveUserConfig(int id, String config) throws TException {
		
	}

    @Override
    public void start() throws Exception {
        ThriftViewSelect.Processor<Iface> proc = new ThriftViewSelect.Processor<Iface>(this);
        thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
        thrServ.serve();
    }

    @Override
    public void stop() {
        if (thrServ != null)
            thrServ.stop();
    }

    // Берет инфу из классификатора c целочисленным полем pcod
    
	@Override
	public List<IntegerClassifier> getVSIntegerClassifierView() throws TException {
		// TODO Auto-generated method stub
		final String sqlQuery = "SELECT pcod, name FROM "+classname;
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmIVS =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmIVS.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            throw new TException(e);
        }
	}
	
    // Берет инфу из классификатора cо строковым полем pcod
	
	@Override
	public List<StringClassifier> getVSStringClassifierView() throws TException {
		// TODO Auto-generated method stub
		final String sqlQuery = "SELECT pcod, name FROM "+classname;
        final TResultSetMapper<StringClassifier, StringClassifier._Fields> rsmSVS =
                new TResultSetMapper<>(StringClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            return rsmSVS.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            throw new TException(e);
        }
	}

}
