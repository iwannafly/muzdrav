package ru.nkz.ivcgzo.serverDiary;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
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
import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor.SqlExecutorException;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftDiary.MedicalHistoryNotFoundException;
import ru.nkz.ivcgzo.thriftDiary.Shablon;
import ru.nkz.ivcgzo.thriftDiary.ShablonText;
import ru.nkz.ivcgzo.thriftDiary.TMedicalHistory;
import ru.nkz.ivcgzo.thriftDiary.ThriftDiary;
import ru.nkz.ivcgzo.thriftDiary.ThriftDiary.Iface;

public class ServerDiary extends Server implements Iface {
    private static final Logger log = Logger.getLogger(ServerDiary.class.getName());
    private TServer tServer;
    private TResultSetMapper<TMedicalHistory, TMedicalHistory._Fields> rsmMedicalHistory;
    private TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmIntClas;
    
    private static final String[] MEDICAL_HISTORY_FIELD_NAMES = {
        "id", "id_gosp", "jalob", "morbi", "status_praesense", "status_localis",
        "fisical_obs", "pcod_vrach", "dataz", "timez", "cpodr"
    };
    private static final String[] INT_CLAS_FIELD_NAMES = {
        "pcod", "name"
    };
    private static final Class<?>[] MEDICAL_HISTORY_TYPES = {
    //  id             id_gosp       jalob         morbi          st_praesense  status_localis
        Integer.class, Integer.class, String.class, String.class, String.class, String.class,
    //  fisical_obs   pcod_vrach		dataz		timez		cpodr
        String.class, Integer.class,	Date.class,	Time.class,	Integer.class
    };

    public ServerDiary(final ISqlSelectExecutor sse, final ITransactedSqlExecutor tse) {
        super(sse, tse);

        //Инициализация логгера с конфигом из файла ../../manager/log4j.xml;
        String manPath = new File(this.getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath();
        DOMConfigurator.configure(new File(manPath, "log4j.xml").getAbsolutePath());

        rsmMedicalHistory = new TResultSetMapper<>(TMedicalHistory.class,
                MEDICAL_HISTORY_FIELD_NAMES);
        rsmIntClas = new TResultSetMapper<>(IntegerClassifier.class, INT_CLAS_FIELD_NAMES);
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
	public int getId() {
		return configuration.appId;
	}
	
	@Override
	public int getPort() {
		return configuration.thrPort;
	}
	
	@Override
	public String getName() {
		return configuration.appName;
	}
	
    @Override
    public final List<TMedicalHistory> getMedicalHistory(final int idGosp)
            throws KmiacServerException, MedicalHistoryNotFoundException {
        final String sqlQuery = "SELECT * FROM c_osmotr " +
        		"WHERE (id_gosp = ?) " +
        		"ORDER BY dataz, timez;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idGosp)) {
            List<TMedicalHistory> tmpMedHistories =
                rsmMedicalHistory.mapToList(acrs.getResultSet());
            if (tmpMedHistories.size() > 0) {
                return tmpMedHistories;
            } else {
                log.log(Level.INFO,  "MedicalHistoryNotFoundException, idGosp = " + idGosp);
                throw new MedicalHistoryNotFoundException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final int addMedicalHistory(final TMedicalHistory medHist)
            throws KmiacServerException {
        final int[] indexes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        final String sqlQuery = "INSERT INTO c_osmotr (id_gosp, jalob, "
            + "morbi, status_praesense, status_localis, "
            + "fisical_obs, pcod_vrach, dataz, timez, cpodr) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT(sqlQuery, true, medHist, MEDICAL_HISTORY_TYPES, indexes);
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            return id;
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void updateMedicalHistory(final TMedicalHistory medHist)
            throws KmiacServerException {
        final int[] indexes = {2, 3, 4, 5, 6, 8, 9, 0};
        //Изменить номер отделения и врача нельзя (в списке параметров не присутствуют):
        final String sqlQuery = "UPDATE c_osmotr SET jalob = ?, "
            + "morbi = ?, status_praesense = ?, "
            + "status_localis = ?, fisical_obs = ?, dataz = ?, timez = ? "
            + "WHERE (id = ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT(sqlQuery, false, medHist, MEDICAL_HISTORY_TYPES, indexes);
            sme.setCommit();
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
            log.log(Level.ERROR, "SqlException (updateMedicalHistory)", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final void deleteMedicalHistory(final int id) throws KmiacServerException {
        final String sqlQuery = "DELETE FROM c_osmotr WHERE (id = ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, id);
            sme.setCommit();
        } catch (SqlExecutorException | InterruptedException e) {
            log.log(Level.ERROR, "SqlException (deleteMedicalHistory)", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getShablonNames(final int cspec, final int cslu,
            final String srcText) throws KmiacServerException {
        String sql = "SELECT DISTINCT sho.id AS pcod, sho.name, "
            + "sho.diag || ' ' || sho.name AS name "
            + "FROM sh_osm sho JOIN sh_ot_spec shp ON (shp.id_sh_osm = sho.id) "
            + "JOIN sh_osm_text sht ON (sht.id_sh_osm = sho.id) "
            + "JOIN n_c00 c00 ON (c00.pcod = sho.diag) "
            + "JOIN n_t00 t00 ON (t00.spec = shp.cspec) "
            + "JOIN n_n45 n45 ON (n45.codprof = t00.pcod) "
            + "WHERE (n45.codotd = ?) AND ((sho.cslu = 1) OR (sho.cslu =3)) ";

        if (srcText != null) {
            sql += "AND ((sho.diag LIKE ?) OR (sho.name LIKE ?) "
                    + "OR (c00.name LIKE ?) OR (sht.sh_text LIKE ?)) ";
        }

        sql += "ORDER BY sho.name;";
        try (AutoCloseableResultSet acrs = (srcText == null)
                ? sse.execPreparedQuery(sql, cspec)
                : sse.execPreparedQuery(sql, cspec,
                        srcText, srcText, srcText, srcText)) {
            return rsmIntClas.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Template searching error", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final Shablon getShablon(final int idSh) throws KmiacServerException {
        final String sqlQuery = "SELECT sho.id, nd.name, sho.next, nsh.pcod,nsh.name, sht.sh_text "
            + "FROM sh_osm sho "
            + "JOIN n_din nd ON (nd.pcod = sho.cdin) "
            + "JOIN sh_osm_text sht ON (sht.id_sh_osm = sho.id) "
            + "JOIN n_shablon nsh ON (nsh.pcod = sht.id_n_shablon) "
            + "WHERE (sho.id = ?) ORDER BY nsh.pcod;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idSh)) {
            if (acrs.getResultSet().next()) {
                Shablon sho = new Shablon(acrs.getResultSet().getString(2),
                    acrs.getResultSet().getString(3), new ArrayList<ShablonText>(),
                    acrs.getResultSet().getInt(1));
                do {
                    sho.textList.add(new ShablonText(acrs.getResultSet().getInt(4),
                            acrs.getResultSet().getString(5), acrs.getResultSet().getString(6)));
                } while (acrs.getResultSet().next());
                return sho;
            } else {
                throw new SQLException("No templates with this id");
            }
        } catch (SQLException e) {
            System.err.println(e.getCause());
            throw new KmiacServerException("Error loading template by its id");
        }
    }

	@Override
	public List<IntegerClassifier> getOtdFromLPU(final int clpu)
			throws KmiacServerException {
		final String SQLQuery = "SELECT pcod, name " +
				"FROM n_o00 " +
				"WHERE (clpu = ?);";
	    try (AutoCloseableResultSet acrs = sse.execPreparedQuery(SQLQuery, clpu)) {
    		return rsmIntClas.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQLException (getOtdFromLPU): ", e);
        	((SQLException) e.getCause()).printStackTrace();
        	throw new KmiacServerException();
        }
	}

	@Override
	public List<IntegerClassifier> getDoctorsFromOtd(final int clpu, final int cpodr)
			throws KmiacServerException {
		final String SQLQuery = "SELECT v.pcod, (v.fam || ' ' || v.im || ' ' || v.ot) AS name " +
				"FROM s_vrach v " +
				"JOIN s_mrab mr ON (mr.pcod = v.pcod) " +
				"WHERE (mr.clpu = ?) AND (mr.cpodr = ?) AND (mr.datau IS NULL);";
	    try (AutoCloseableResultSet acrs = sse.execPreparedQuery(SQLQuery, clpu, cpodr)) {
    		return rsmIntClas.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "SQLException (getDoctorsFromOtd): ", e);
        	((SQLException) e.getCause()).printStackTrace();
        	throw new KmiacServerException();
        }
	}

    @Override
    public void start() throws Exception {
        ThriftDiary.Processor<Iface> proc =
                new ThriftDiary.Processor<Iface>(this);

        tServer = new TThreadedSelectorServer(new Args(
                new TNonblockingServerSocket(configuration.diaryThrPort)).processor(proc));
        log.log(Level.INFO, "diary server started");
        tServer.serve();
    }

    @Override
    public void stop() {
        tServer.stop();
        log.log(Level.INFO, "diary server stopped");
    }

}
