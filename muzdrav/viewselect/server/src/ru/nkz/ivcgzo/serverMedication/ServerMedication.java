package ru.nkz.ivcgzo.serverMedication;

import java.io.File;
import java.sql.SQLException;
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
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftMedication.ThriftMedication;
import ru.nkz.ivcgzo.thriftMedication.ThriftMedication.Iface;

public class ServerMedication extends Server implements Iface {
    private static Logger log = Logger.getLogger(ServerMedication.class.getName());
    private TServer tServer;
    private TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmMedications;
    private TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmMedicationForms;

    private static final String[] INT_CLAS_FIELD_NAMES = {
        "pcod", "name"
    };
    @SuppressWarnings("unused")
    private static final Class<?>[] INT_CLAS_TYPES = {
    //  pcod          name
        Integer.class, String.class
    };

    public ServerMedication(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
        super(sse, tse);

        //Инициализация логгера с конфигом из файла ../../manager/log4j.xml;
        String manPath = new File(this.getClass().getProtectionDomain().getCodeSource()
            .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath();
        DOMConfigurator.configure(new File(manPath, "log4j.xml").getAbsolutePath());

        rsmMedications = new TResultSetMapper<>(IntegerClassifier.class, INT_CLAS_FIELD_NAMES);
        rsmMedicationForms = new TResultSetMapper<>(IntegerClassifier.class, INT_CLAS_FIELD_NAMES);
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
        String sqlQuery = "SELECT pcod, name FROM n_frw WHERE c_med = ?";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, medId)){
            List<IntegerClassifier> medicationForms = rsmMedicationForms.mapToList(acrs.getResultSet());
            if (medicationForms.size() == 0) {
                log.log(Level.INFO, "Medication forms not found exception");
            }
            return medicationForms;
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }
}
