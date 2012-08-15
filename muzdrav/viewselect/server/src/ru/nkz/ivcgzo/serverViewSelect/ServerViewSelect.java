package ru.nkz.ivcgzo.serverViewSelect;

import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
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
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftViewSelect.PatientBriefInfo;
import ru.nkz.ivcgzo.thriftViewSelect.PatientSearchParams;
import ru.nkz.ivcgzo.thriftViewSelect.ThriftViewSelect;
import ru.nkz.ivcgzo.thriftViewSelect.ThriftViewSelect.Iface;

public class ServerViewSelect extends Server implements Iface {
	private TServer thrServ;
	public String className = "n_c00";
	
	private TResultSetMapper<PatientBriefInfo, PatientBriefInfo._Fields> rsmPatBrief;

	//public String className;
	
	public ServerViewSelect(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		rsmPatBrief = new TResultSetMapper<>(PatientBriefInfo.class, "npasp", "fam", "im", "ot", "datar", "poms_ser", "poms_nom");
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

	@Override
	public List<IntegerClassifier> getVSIntegerClassifierView(String className) throws TException {
		// TODO Auto-generated method stub
		final String sqlQuery = "SELECT pcod, name FROM + ?";
        final TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmIVS =
                new TResultSetMapper<>(IntegerClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, className)) {
            return rsmIVS.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            throw new TException(e);
        }
	}
	
	@Override
	public List<StringClassifier> getVSStringClassifierView(String className) throws TException {
		// TODO Auto-generated method stub
		final String sqlQuery = "SELECT pcod, name FROM + ?";
        final TResultSetMapper<StringClassifier, StringClassifier._Fields> rsmSVS =
                new TResultSetMapper<>(StringClassifier.class, "pcod", "name");
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, className)) {
            return rsmSVS.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            throw new TException(e);
        }
	}
	
	@Override
	public boolean isClassifierEditable(String className) throws TException {
		// TODO Auto-generated method stub
		if (className.charAt(0)=='c')
			return true;
		else return false;
	}

	@Override
	public boolean isClassifierPcodInteger(String className) throws TException {
		// TODO Auto-generated method stub
		String sqlQueryGetType = "SELECT data_type FROM information_schema.columns where table_name = ? AND column_name = ?";
		try (AutoCloseableResultSet arcs = sse.execPreparedQuery(sqlQueryGetType, className, "pcod")) {
			if (arcs.getResultSet().next()){
				if (arcs.getResultSet().getString(1).equals("integer"))
					return true;
				else return false;
			} else {
				throw new TException();
			}				
		} catch (SQLException e) {
			throw new TException(e);
		}
	}
	///////////////////////// patient search ///////////////////////////////
	
	@Override
	public List<PatientBriefInfo> searchPatient(PatientSearchParams prms) throws KmiacServerException, TException {
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery("SELECT npasp, fam, im, ot, datar, poms_ser, poms_nom FROM patient " + getSearchPatientWhereClause(prms), getSearchPatientParamArray(prms))) {
			return rsmPatBrief.mapToList(acrs.getResultSet());
		} catch (SQLException e) {
			throw new KmiacServerException();
		}
	}
	
	private String getSearchPatientWhereClause(PatientSearchParams prms) {
		String clause = "WHERE ";
		String condStringFormat = ((prms.illegibleSearch) ? "(%s SIMILAR TO ?) " : "(%s = ?) ") + "AND ";
		String condDateFormat = ((prms.illegibleSearch) ? "(%s BETWEEN ? AND ?) " : "(%s = ?) ") + "AND ";
		final int andLen = 4;
		
		if (prms.isSetFam())
			clause += String.format(condStringFormat, "fam");
		if (prms.isSetIm())
			clause += String.format(condStringFormat, "im");
		if (prms.isSetOt())
			clause += String.format(condStringFormat, "ot", prms.getOt());
		if ((!prms.illegibleSearch && prms.isSetDatar()) || (prms.isSetDatar() && prms.isSetDatar2()))
			clause += String.format(condDateFormat, "datar");
		if (prms.isSetSpolis())
			clause += String.format(condStringFormat, "poms_ser");
		if (prms.isSetNpolis())
			clause += String.format(condStringFormat, "poms_nom");
		
		return clause.substring(0, clause.length() - andLen);
	}
	
	private Object[] getSearchPatientParamArray(PatientSearchParams prms) {
		List<Object> list = new ArrayList<>(PatientSearchParams.metaDataMap.size());
		
		if (prms.isSetFam())
			list.add(prms.getFam());
		if (prms.isSetIm())
			list.add(prms.getIm());
		if (prms.isSetOt())
			list.add(prms.getOt());
		if (!prms.illegibleSearch && prms.isSetDatar()) {
			list.add(new Date(prms.getDatar()));
		} else if (prms.isSetDatar() && prms.isSetDatar2()) {
			list.add(new Date(prms.getDatar()));
			list.add(new Date(prms.getDatar2()));
		}
		if (prms.isSetSpolis())
			list.add(prms.getSpolis());
		if (prms.isSetNpolis())
			list.add(prms.getNpolis());
		
		return list.toArray();
	}

	

}
