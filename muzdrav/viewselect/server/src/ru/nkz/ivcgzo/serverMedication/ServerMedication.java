package ru.nkz.ivcgzo.serverMedication;

import java.io.File;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;
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
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftMedication.Lek;
import ru.nkz.ivcgzo.thriftMedication.LekPriem;
import ru.nkz.ivcgzo.thriftMedication.ThriftMedication;
import ru.nkz.ivcgzo.thriftMedication.ThriftMedication.Iface;

public class ServerMedication extends Server implements Iface {
    private static Logger log = Logger.getLogger(ServerMedication.class.getName());
    private TServer tServer;
    private TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmMedications;
    private TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmMedicationForms;
    private TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmLekShortList;
    private TResultSetMapper<Lek, Lek._Fields> rsmLek;
    private TResultSetMapper<LekPriem, LekPriem._Fields> rsmLekPriem;

    private static final String[] INT_CLAS_FIELD_NAMES = {
        "pcod", "name"
    };
    private static final String[] LEK_FIELD_NAMES = {
        "nlek", "id_gosp", "vrach", "datan", "klek", "flek", "doza", "ed", "sposv",
        "spriem", "pereod", "datae", "komm", "datao", "vracho", "dataz", "lek_name",
        "id_kap", "id_inj", "opl", "diag"
    };
    private static final String[] LEK_PRIEM_FIELD_NAMES = {
        "id", "nlek", "datap", "timep", "status"
    };

    @SuppressWarnings("unused")
    private static final Class<?>[] INT_CLAS_TYPES = {
    //  pcod			name
        Integer.class,	String.class
    };
    private static final Class<?>[] LEK_TYPES = {
    //  nlek           id_gosp        vrach          datan       klek
        Integer.class, Integer.class, Integer.class, Date.class, Integer.class,
    //  flek          doza           ed             sposv
        String.class, Integer.class, Integer.class, Integer.class,
    //  spriem         pereod         datae			komm
        Integer.class, Integer.class, Date.class,	String.class,
    //  datao       vracho         dataz		lek_name
        Date.class, Integer.class, Date.class,	String.class,
    //	id_kap			id_inj			opl				diag
        Integer.class,	Integer.class,	Integer.class,	String.class
    };
    @SuppressWarnings("unused")
    private static final Class<?>[] LEK_PRIEM_TYPES = {
    //  id             nlek           datap       timep       status 
        Integer.class, Integer.class, Date.class, Time.class, Boolean.class
    };

    public ServerMedication(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
        super(sse, tse);

        //Инициализация логгера с конфигом из файла ../../manager/log4j.xml;
        String manPath = new File(this.getClass().getProtectionDomain().getCodeSource()
            .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath();
        DOMConfigurator.configure(new File(manPath, "log4j.xml").getAbsolutePath());

        rsmMedications = new TResultSetMapper<>(IntegerClassifier.class, INT_CLAS_FIELD_NAMES);
        rsmMedicationForms = new TResultSetMapper<>(IntegerClassifier.class, INT_CLAS_FIELD_NAMES);
        rsmLekShortList = new TResultSetMapper<>(IntegerClassifier.class, INT_CLAS_FIELD_NAMES);
        rsmLek = new TResultSetMapper<>(Lek.class, LEK_FIELD_NAMES);
        rsmLekPriem = new TResultSetMapper<>(LekPriem.class, LEK_PRIEM_FIELD_NAMES);
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
    public List<IntegerClassifier> getMedications() throws KmiacServerException {
        String sqlQuery = "SELECT pcod, name FROM n_med ORDER BY name;";
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            List<IntegerClassifier> medications = rsmMedications.mapToList(acrs.getResultSet());
            if (medications.size() == 0) {
                log.log(Level.INFO, "Medications not found");
            }
            return medications;
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public List<IntegerClassifier> getMedicationsUsingTemplate(String template)
            throws KmiacServerException {
        String sqlQuery = "SELECT pcod, name FROM n_med "
            + "WHERE lower(name) LIKE lower(?) ORDER BY name;";
        String regexp = "%"+template+"%";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, regexp)) {
            List<IntegerClassifier> medications = rsmMedications.mapToList(acrs.getResultSet());
            if (medications.size() == 0) {
                log.log(Level.INFO, "Medications not found (using template)");
            }
            return medications;
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public List<IntegerClassifier> getMedicationForms(int medId)
            throws KmiacServerException {
        String sqlQuery = "SELECT pcod, name FROM n_frw WHERE (c_med = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, medId)) {
            List<IntegerClassifier> medicationForms =
                rsmMedicationForms.mapToList(acrs.getResultSet());
            if (medicationForms.size() == 0) {
                log.log(Level.INFO, "Medication forms not found");
            }
            return medicationForms;
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public List<LekPriem> getLekPriem(int nlek) throws KmiacServerException {
        String sqlQuery = "SELECT * FROM c_lek_priem WHERE nlek = ?";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, nlek)) {
            List<LekPriem> lekPriems =
                rsmLekPriem.mapToList(acrs.getResultSet());
            if (lekPriems.size() == 0) {
                log.log(Level.INFO, "lekPriem not found");
            }
            return lekPriems;
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public Lek getLek(int nlek) throws KmiacServerException {
        String sqlQuery = "SELECT * FROM c_lek WHERE (nlek = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, nlek)) {
            if (acrs.getResultSet().next()) {
                return rsmLek.map(acrs.getResultSet());
            } else {
                log.log(Level.INFO, "Lek not found");
                throw new KmiacServerException();
            }
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public void addLekPriem(List<LekPriem> lekPriems)
            throws KmiacServerException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteLekPriem(List<LekPriem> lekPriems)
            throws KmiacServerException {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void changeLekPriemStatus(int id, boolean status) throws KmiacServerException {
        final String sqlQuery = "UPDATE c_lek_priem SET status = ? WHERE (id = ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedQuery(sqlQuery, status, id);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public int addLek(Lek lek) throws KmiacServerException {
        final int[] indexes = 
        	{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20};
        final String sqlQuery = "INSERT INTO c_lek (id_gosp, vrach, datan, klek,"
            + "flek, doza, ed, sposv, spriem, pereod, datae, komm, datao, vracho, "
            + "dataz, lek_name, id_kap, id_inj, opl, diag) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT(sqlQuery, true, lek,
                 LEK_TYPES, indexes);
            int id = sme.getGeneratedKeys().getInt("nlek");
            sme.setCommit();
            return id;
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public void deleteLek(int nlek) throws KmiacServerException  {
        final String sqlQuery = "DELETE FROM c_lek WHERE (nlek = ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, nlek);
            sme.setCommit();
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public List<IntegerClassifier> getLekShortList(int idGosp)
            throws KmiacServerException {
        String sqlQuery = "SELECT c_lek.nlek as pcod, n_med.name as name "
            + "FROM c_lek "
            + "INNER JOIN n_med ON (c_lek.klek = n_med.pcod) "
            + "WHERE (c_lek.id_gosp = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idGosp)) {
            List<IntegerClassifier> lekShortList = rsmLekShortList.mapToList(acrs.getResultSet());
            if (lekShortList.size() == 0) {
                log.log(Level.INFO, "Lek short list not found");
            }
            return lekShortList;
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public List<IntegerClassifier> getPeriods() throws KmiacServerException {
        String sqlQuery = "SELECT pcod, name FROM n_period ORDER BY name;";
        try (AutoCloseableResultSet acrs = sse.execQuery(sqlQuery)) {
            List<IntegerClassifier> medications = rsmMedications.mapToList(acrs.getResultSet());
            if (medications.size() == 0) {
                log.log(Level.INFO, "Periods not found");
            }
            return medications;
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }
}
