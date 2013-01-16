package ru.nkz.ivcgzo.serverOperation;

import java.io.File;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
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
import ru.nkz.ivcgzo.serverManager.common.*;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOperation.*;
import ru.nkz.ivcgzo.thriftOperation.ThriftOperation.Iface;

public class ServerOperation extends Server implements Iface {
    private static final Logger log = Logger.getLogger(ServerOperation.class.getName());
    private TServer tServer;
    private TResultSetMapper<Operation, Operation._Fields> rsmOperation;
    private TResultSetMapper<OperationComplication,
            OperationComplication._Fields> rsmOperationComplication;
    private TResultSetMapper<OperationPaymentFund,
            OperationPaymentFund._Fields> rsmOperationPaymentFund;
    private TResultSetMapper<Anesthesia, Anesthesia._Fields> rsmAnesthesia;
    private TResultSetMapper<AnesthesiaComplication,
            AnesthesiaComplication._Fields> rsmAnesthesiaComplication;
    private TResultSetMapper<AnesthesiaPaymentFund,
            AnesthesiaPaymentFund._Fields> rsmAnesthesiaPaymentFund;

    private static final String[] OPERATION_FIELD_NAMES = {
            "id", "vid_st", "cotd", "id_gosp", "npasp", "pcod", "name_oper", "date", "vrem",
            "pred_ep", "op_oper", "material", "dlit", "dataz"
    };
    private static final String[] OPERATION_COMPLICATION_FIELD_NAMES = {
            "id", "id_oper", "name_osl", "pcod", "dataz"
    };
    private static final String[] OPERATION_PAYMENT_FUND_FIELD_NAMES = {
            "id", "id_oper", "pcod", "dataz"
    };
    private static final String[] ANESTHESIA_FIELD_NAMES = {
            "id", "vid_st", "cotd", "id_gosp", "npasp", "id_oper", "pcod", "name_an", "date",
            "vrem", "dataz"
    };
    private static final String[] ANESTHESIA_COMPLICATION_FIELD_NAMES = {
            "id", "id_anast", "name", "pcod", "dataz"
    };
    private static final String[] ANESTHESIA_PAYMENT_FUND_FIELD_NAMES = {
            "id", "id_anast", "pcod", "dataz"
    };

    private static final Class<?>[] OPERATION_TYPES ={
//          id             vid_st         cotd           id_gosp        npasp
            Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
//          pcod          name_oper     date        vrem        pred_ep       op_oper
            String.class, String.class, Date.class, Time.class, String.class, String.class,
//          material      dlit           dataz
            String.class, Integer.class, Date.class
    };
    private static final Class<?>[] OPERATION_COMPLICATION_TYPES ={
//          id           id_oper          name_osl      pcod           dataz
            Integer.class, Integer.class, String.class, String.class, Date.class
    };
    private static final Class<?>[] OPERATION_PAYMENT_FUND_TYPES ={
//          id             id_oper        pcod           dataz
            Integer.class, Integer.class, Integer.class, Date.class
    };
    private static final Class<?>[] ANESTHESIA_TYPES ={
//          id             vid_st         cotd           id_gosp        npasp
            Integer.class, Integer.class, Integer.class, Integer.class, Integer.class,
//          id_oper        pcod           name_an       date        vrem        dataz
            Integer.class, Integer.class, String.class, Date.class, Time.class, Date.class
    };
    private static final Class<?>[] ANESTHESIA_COMPLICATION_TYPES ={
//          id             id_anast       name          pcod           dataz
            Integer.class, Integer.class, String.class, String.class, Date.class
    };

    private static final Class<?>[] ANESTHESIA_PAYMENT_FUND_TYPES ={
//          id             id_anast       pcod           dataz
            Integer.class, Integer.class, Integer.class, Date.class
    };


    public ServerOperation(final ISqlSelectExecutor sse, final ITransactedSqlExecutor tse) {
        super(sse, tse);

        //Инициализация логгера с конфигом из файла ../../manager/log4j.xml;
        String manPath = new File(this.getClass().getProtectionDomain().getCodeSource()
            .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath();
        DOMConfigurator.configure(new File(manPath, "log4j.xml").getAbsolutePath());

        rsmOperation = new TResultSetMapper<>(Operation.class, OPERATION_FIELD_NAMES);
        rsmOperationComplication = new TResultSetMapper<>(OperationComplication.class,
                OPERATION_COMPLICATION_FIELD_NAMES);
        rsmOperationPaymentFund = new TResultSetMapper<>(OperationPaymentFund.class, 
                OPERATION_PAYMENT_FUND_FIELD_NAMES);
        rsmAnesthesia = new TResultSetMapper<>(Anesthesia.class, ANESTHESIA_FIELD_NAMES);
        rsmAnesthesiaComplication = new TResultSetMapper<>(AnesthesiaComplication.class,
                ANESTHESIA_COMPLICATION_FIELD_NAMES);
        rsmAnesthesiaPaymentFund = new TResultSetMapper<>(AnesthesiaPaymentFund.class,
                ANESTHESIA_PAYMENT_FUND_FIELD_NAMES);
    }

    @Override
    public void start() throws Exception {
        ThriftOperation.Processor<Iface> proc =
            new ThriftOperation.Processor<Iface>(this);

        tServer = new TThreadedSelectorServer(new Args(
            new TNonblockingServerSocket(configuration.opThrPort)).processor(proc));
        log.log(Level.INFO, "operation server started");
        tServer.serve();
    }

    @Override
    public void stop() {
        tServer.stop();
        log.log(Level.INFO, "operation server stopped");
    }

    @Override
    public void testConnection() throws TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void saveUserConfig(int id, String config) throws TException {
        // TODO Auto-generated method stub
    }

    /**
     * Возвращает список всех операций для данной записи госпитализации
     *
     * @param idGosp - номер госпитализации
     */
    @Override
    public List<Operation> getOperations(int idGosp) throws KmiacServerException {
        String sqlQuery = "SELECT * FROM p_oper WHERE p_oper.id_gosp = ? "
                + "ORDER BY p_oper.date, p_oper.vrem ";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idGosp)) {
            return rsmOperation.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Добавляет новую операцию
     *
     * @param curOperation - выбранная операция
     */
    @Override
    public int addOperation(Operation curOperation) throws KmiacServerException {
        final int[] indexes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("INSERT INTO p_oper (vid_st, cotd, id_gosp, npasp, pcod, "
                    + "name_oper, date, vrem, pred_ep, op_oper, material, dlit, dataz) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                    true, curOperation, OPERATION_TYPES, indexes);
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            return id;
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Обновляет информацию о выбранной операции
     *
     * @param curOperation - выбранная операция
     */
    @Override
    public void updateOperation(Operation curOperation) throws KmiacServerException {
        final int[] indexes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 0};
        final String sqlQuery = "UPDATE p_oper SET vid_st = ?, cotd = ?, id_gosp = ?, "
                + "npasp = ?, pcod = ?, name_oper = ?, date = ?, vrem = ?, pred_ep = ?, "
                + "op_oper = ?, material = ?, dlit = ?, dataz = ? "
                + "WHERE id = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT(sqlQuery, false, curOperation, OPERATION_TYPES, indexes);
            sme.setCommit();
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Удаляет операцию
     *
     * @param id - уникальный идентификатор операции
     */
    @Override
    public void deleteOperation(int id) throws KmiacServerException {
        final String sqlQuery = "DELETE FROM p_oper WHERE id = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, id);
            sme.setCommit();
        } catch (SqlSelectExecutor.SqlExecutorException | InterruptedException e) {
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Возвращает список всех осложнений данной операции
     *
     * @param idOper - уникальный идентификатор операции
     */
    @Override
    public List<OperationComplication> getOperationComplications(int idOper)
            throws KmiacServerException {
        String sqlQuery = "SELECT * FROM p_oper_osl WHERE p_oper_osl.id_oper = ? "
                + "ORDER BY p_oper_osl.dataz ";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idOper)) {
            return rsmOperationComplication.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Добавляет новое осложнение
     *
     * @param curCompl - текущее осложнение операции
     */
    @Override
    public int addOperationComplication(OperationComplication curCompl)
            throws KmiacServerException {
        final int[] indexes = {1, 2, 3, 4};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("INSERT INTO p_oper_osl (id_oper, name_osl, pcod, dataz) "
                    + "VALUES (?, ?, ?, ?);",
                    true, curCompl, OPERATION_COMPLICATION_TYPES, indexes);
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            return id;
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Обновляет информацию об осложнении
     *
     * @param curCompl - текущее осложнение операции
     */
    @Override
    public void updateOperationComplication(OperationComplication curCompl)
            throws KmiacServerException {
        final int[] indexes = {1, 2, 3, 4, 0};
        final String sqlQuery = "UPDATE p_oper_osl SET id_oper = ?, name_osl = ?, pcod = ?, "
                + "dataz = ? WHERE id = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT(sqlQuery, false, curCompl, OPERATION_COMPLICATION_TYPES, indexes);
            sme.setCommit();
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Удаляет информацию об осложнении
     *
     * @param id - уникальный идентификатор осложнения операции
     */
    @Override
    public void deleteOperationComplication(int id) throws KmiacServerException {
        final String sqlQuery = "DELETE FROM p_oper_osl WHERE id = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, id);
            sme.setCommit();
        } catch (SqlSelectExecutor.SqlExecutorException | InterruptedException e) {
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Возвращает список всех источников оплаты данной операции
     *
     * @param idOper - уникальный идентификатор операции
     */
    @Override
    public List<OperationPaymentFund> getOperationPaymentFunds(int idOper)
            throws KmiacServerException {
        String sqlQuery = "SELECT * FROM p_oper_opl WHERE p_oper_opl.id_oper = ? "
                + "ORDER BY p_oper_opl.dataz ";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idOper)) {
            return rsmOperationPaymentFund.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Добавляет новый источник оплаты
     *
     * @param curPaymentFund - текущий источник оплаты
     */
    @Override
    public int addOperationPaymentFund(OperationPaymentFund curPaymentFund)
            throws KmiacServerException {
        final int[] indexes = {1, 2, 3};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("INSERT INTO p_oper_opl (id_oper, pcod, dataz) "
                    + "VALUES (?, ?, ?);",
                    true, curPaymentFund, OPERATION_PAYMENT_FUND_TYPES, indexes);
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            return id;
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Обновляет источник оплаты
     *
     * @param curPaymentFund - текущий источник оплаты
     */
    @Override
    public void updateOperationPaymentFund(OperationPaymentFund curPaymentFund)
            throws KmiacServerException {
        final int[] indexes = {1, 2, 3, 0};
        final String sqlQuery = "UPDATE p_oper_opl SET id_oper = ?, pcod = ?, "
                + "dataz = ? WHERE id = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT(sqlQuery, false, curPaymentFund,
                    OPERATION_PAYMENT_FUND_TYPES, indexes);
            sme.setCommit();
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Удаляет источник оплаты
     *
     * @param id - уникальный идентификатор метода оплаты операции
     */
    @Override
    public void deleteOperationPaymentFund(int id) throws KmiacServerException {
        final String sqlQuery = "DELETE FROM p_oper_opl WHERE id = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, id);
            sme.setCommit();
        } catch (SqlSelectExecutor.SqlExecutorException | InterruptedException e) {
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Возвращает список всех назначений анастезии для данной записи госпитализации
     *
     * @param idOper - уникальный идентификатор операции
     */
    @Override
    public List<Anesthesia> getAnesthesias(int idOper) throws KmiacServerException {
        String sqlQuery = "SELECT * FROM p_anast WHERE p_anast.id_oper = ? "
                + "ORDER BY p_anast.date, p_anast.vrem ";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idOper)) {
            return rsmAnesthesia.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Добавляет новое назначений анастезии
     *
     * @param curAnesthesia - текущая анестезия
     */
    @Override
    public int addAnesthesia(Anesthesia curAnesthesia) throws KmiacServerException {
        final int[] indexes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("INSERT INTO p_anast (vid_st, cotd, id_gosp, npasp, id_oper, pcod, "
                    + "name_an, date, vrem, dataz) "
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                    true, curAnesthesia, ANESTHESIA_TYPES, indexes);
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            return id;
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Обновляет назначений анастезии
     *
     * @param curAnesthesia - текущая анестезия
     */
    @Override
    public void updateAnesthesia(Anesthesia curAnesthesia) throws KmiacServerException {
        final int[] indexes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0};
        final String sqlQuery = "UPDATE p_anast SET vid_st = ?, cotd = ?, id_gosp = ?, npasp = ?, "
                + "id_oper = ?, pcod = ?, name_an = ?, date = ?, vrem = ?,  dataz = ? "
                + "WHERE id = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT(sqlQuery, false, curAnesthesia,
                    ANESTHESIA_TYPES, indexes);
            sme.setCommit();
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Удаляет назначений анастезии
     *
     * @param id - уникальный идентификатор анестезии
     */
    @Override
    public void deleteAnesthesia(int id) throws KmiacServerException {
        final String sqlQuery = "DELETE FROM p_anast WHERE id = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, id);
            sme.setCommit();
        } catch (SqlSelectExecutor.SqlExecutorException | InterruptedException e) {
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Возвращает список всех осложнений данной анастезии
     *
     * @param idAnest - уникальный идентификатор анестезии
     */
    @Override
    public List<AnesthesiaComplication> getAnesthesiaComplications(int idAnest)
            throws KmiacServerException {
        String sqlQuery = "SELECT * FROM p_anast_osl WHERE p_anast_osl.id_anast = ? "
                + "ORDER BY p_anast_osl.dataz ";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idAnest)) {
            return rsmAnesthesiaComplication.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Добавляет новое осложнение после анастезии
     *
     * @param curCompl - текущая анестезия
     */
    @Override
    public int addAnesthesiaComplication(AnesthesiaComplication curCompl)
            throws KmiacServerException {
        final int[] indexes = {1, 2, 3, 4};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("INSERT INTO p_anast_osl (id_anast, name, pcod, dataz) "
                    + "VALUES (?, ?, ?, ?);",
                    true, curCompl, ANESTHESIA_COMPLICATION_TYPES, indexes);
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            return id;
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Обновляет осложнение после анастезии
     *
     * @param curCompl - текущее осложнение после анестезии
     */
    @Override
    public void updateAnesthesiaComplication(AnesthesiaComplication curCompl)
            throws KmiacServerException {
        final int[] indexes = {1, 2, 3, 4, 0};
        final String sqlQuery = "UPDATE p_anast_osl SET id_anast = ?, name = ?, "
                + "pcod = ?, dataz = ? WHERE id = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT(sqlQuery, false, curCompl,
                    ANESTHESIA_COMPLICATION_TYPES, indexes);
            sme.setCommit();
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Удаляет осложнение после анастезии
     *
     * @param id - уникальный идентификатор текущего осложнения после анестезии
     */
    @Override
    public void deleteAnesthesiaComplication(int id) throws KmiacServerException {
        final String sqlQuery = "DELETE FROM p_anast_osl WHERE id = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, id);
            sme.setCommit();
        } catch (SqlSelectExecutor.SqlExecutorException | InterruptedException e) {
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Возвращает список всех источников оплаты данной анастезии
     *
     * @param idAnest - уникальный идентификатор операции
     */
    @Override
    public List<AnesthesiaPaymentFund> getAnesthesiaPaymentFunds(int idAnest)
            throws KmiacServerException {
        String sqlQuery = "SELECT * FROM p_anast_opl WHERE p_anast_opl.id_anast = ? "
                + "ORDER BY p_anast_opl.dataz ";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, idAnest)) {
            return rsmAnesthesiaPaymentFund.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Добавляет новый источник оплаты анастезии
     *
     * @param curPaymentFund - текущий метод оплаты
     */
    @Override
    public int addAnesthesiaPaymentFund(AnesthesiaPaymentFund curPaymentFund)
            throws KmiacServerException {
        final int[] indexes = {1, 2, 3};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("INSERT INTO p_anast_opl (id_anast, pcod, dataz) "
                    + "VALUES (?, ?, ?);",
                    true, curPaymentFund, ANESTHESIA_PAYMENT_FUND_TYPES, indexes);
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            return id;
        } catch (SQLException | InterruptedException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Обновляет источник оплаты анастезии
     *
     * @param curPaymentFund - текущий метод оплаты
     */
    @Override
    public void updateAnesthesiaPaymentFund(AnesthesiaPaymentFund curPaymentFund)
            throws KmiacServerException {
        final int[] indexes = {1, 2, 3, 0};
        final String sqlQuery = "UPDATE p_anast_opl SET id_anast = ?, "
                + "pcod = ?, dataz = ? WHERE id = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT(sqlQuery, false, curPaymentFund,
                    ANESTHESIA_PAYMENT_FUND_TYPES, indexes);
            sme.setCommit();
        } catch (InterruptedException | SQLException e) {
            e.printStackTrace();
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }

    /**
     * Удаляет источник оплаты анастезии
     *
     * @param id - уникальный идентификатор текущего источника оплаты анестезии
     */
    @Override
    public void deleteAnesthesiaPaymentFund(int id) throws KmiacServerException {
        final String sqlQuery = "DELETE FROM p_anast_opl WHERE id = ?;";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPrepared(sqlQuery, false, id);
            sme.setCommit();
        } catch (SqlSelectExecutor.SqlExecutorException | InterruptedException e) {
            log.log(Level.ERROR, "SqlException", e);
            throw new KmiacServerException();
        }
    }
}
