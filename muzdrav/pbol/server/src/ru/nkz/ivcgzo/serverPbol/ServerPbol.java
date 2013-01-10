package ru.nkz.ivcgzo.serverPbol;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;

import org.apache.thrift.server.TThreadedSelectorServer.Args;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftPbol.Pbol;
import ru.nkz.ivcgzo.thriftPbol.ThriftPbol;
import ru.nkz.ivcgzo.thriftPbol.ThriftPbol.Iface;

public class ServerPbol extends Server implements Iface {
	private TServer thrServ;
	private final TResultSetMapper<Pbol, Pbol._Fields> rsmPbol;
	private final Class<?>[] pbolTypes; 

	public ServerPbol(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
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
	public List<Pbol> getPbol(int npasp) throws KmiacServerException,TException {
		try (AutoCloseableResultSet	acrs = sse.execPreparedQuery("select * from p_bol where npasp = ? ", npasp)) 
		{
			return rsmPbol.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
	}
	}

	@Override
	public int AddPbol(Pbol pbol) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("insert into p_bol (id_obr, id_gosp, npasp, cod_sp, cdol, pcod, dataz) values (?, ?, ?, ?, ?, ?, ?) ", true, pbol, pbolTypes, 1, 2, 3, 10, 11, 12, 13);
			int id = sme.getGeneratedKeys().getInt("id");
			sme.setCommit();
			return id;
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void UpdatePbol(Pbol pbol) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPreparedT("update p_bol set bol_l = ?, s_bl = ?, po_bl = ?, pol = ?, vozr = ?, nombl = ? where id = ? ", false, pbol, pbolTypes, 4, 5, 6, 7, 8, 9, 0);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
	}

	@Override
	public void DeletePbol(int id) throws KmiacServerException, TException {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("delete from p_bol where id = ? ", false, id);
			sme.setCommit();
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			throw new KmiacServerException();
		}
		
	}

}
