package ru.nkz.ivcgzo.serverLab;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
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
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftLab.Gosp;
import ru.nkz.ivcgzo.thriftLab.Isl;
import ru.nkz.ivcgzo.thriftLab.Metod;
import ru.nkz.ivcgzo.thriftLab.Napr;
import ru.nkz.ivcgzo.thriftLab.Pisl;
import ru.nkz.ivcgzo.thriftLab.Pokaz;
import ru.nkz.ivcgzo.thriftLab.PokazMet;
import ru.nkz.ivcgzo.thriftLab.PrezD;
import ru.nkz.ivcgzo.thriftLab.PrezL;
import ru.nkz.ivcgzo.thriftLab.ThriftLab;
import ru.nkz.ivcgzo.thriftLab.ThriftLab.Iface;

public class ServerLab extends Server implements Iface {
    private static Logger log = Logger.getLogger(ServerLab.class.getName());
    private TServer tServer;
    private TResultSetMapper<Metod, Metod._Fields> rsmMetod;
    private TResultSetMapper<PokazMet, PokazMet._Fields> rsmPokazMet;
    private TResultSetMapper<Pokaz, Pokaz._Fields> rsmPokaz;
    @SuppressWarnings("unused")
    private TResultSetMapper<Pisl, Pisl._Fields> rsmPisl;
    @SuppressWarnings("unused")
    private TResultSetMapper<PrezD, PrezD._Fields> rsmPrezD;
    @SuppressWarnings("unused")
    private TResultSetMapper<PrezL, PrezL._Fields> rsmPrezL;
    private TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmIntClass;
    private TResultSetMapper<StringClassifier, StringClassifier._Fields> rsmStrClass;
    @SuppressWarnings("unused")
    private TResultSetMapper<Napr, Napr._Fields> rsmNapr;
    private TResultSetMapper<Gosp, Gosp._Fields> rsmGosp;
    private TResultSetMapper<Isl, Isl._Fields> rsmIsl;

    private static final String[] METOD_FIELD_NAMES = {
        "obst", "name_obst", "c_p0e1", "pcod"
    };
    private static final String[] POKAZ_MET_FIELD_NAMES = {
        "pcod", "name_n"
    };
    private static final String[] POKAZ_FIELD_NAMES = {
        "pcod", "name_n", "stoim", "c_p0e1", "c_n_nz1"
    };
    private static final String[] PISL_FIELD_NAMES = {
        "nisl", "npasp", "cisl", "pcisl", "napravl", "naprotd", "datan", "vrach",
        "diag", "dataz", "kodotd", "pvizit_id", "id_gosp", "datap"
    };
    private static final String[] PREZ_D_FIELD_NAMES = {
        "id", "npasp", "nisl", "kodisl"
    };
    private static final String[] PREZ_L_FIELD_NAMES = {
        "id", "npasp", "nisl", "cpok"
    };
    private static final String[] INT_CLASS_FIELD_NAMES = {
        "pcod", "name"
    };
    private static final String[] STR_CLASS_FIELD_NAMES = {
        "pcod", "name"
    };
    private static final String[] NAPR_FIELD_NAMES = {
        "id", "id_pvizit", "vid_doc", "text", "preds", "zaved", "id_gosp"
    };
    private static final String[] GOSP_FIELD_NAMES = {
        "id_gosp", "npasp", "cotd", "cotd_name", "datap",
        "datav", "ishod", "result", "vrach", "vrach_fio"
    };
    private static final String[] ISL_FIELD_NAMES = {
        "nisl",  "cp0e1", "np0e1", "pcod", "name_n", "zpok", "datav",
        "op_name", "rez_name"
    };

    @SuppressWarnings("unused")
    private static final Class<?>[] METOD_TYPES = {
    //  obst          name_obst     c_p0e1         pcod
        String.class, String.class, Integer.class, String.class
    };
    @SuppressWarnings("unused")
    private static final Class<?>[] POKAZ_MET_TYPES = {
    //  pcod          name_n
        String.class, String.class
    };
    @SuppressWarnings("unused")
    private static final Class<?>[] POKAZ_TYPES = {
    //  pcod          name_n        stoim         c_p0e1         c_n_nz1
        String.class, String.class, Double.class, Integer.class, String.class
    };
    private static final Class<?>[] PISL_TYPES = {
    //  nisl           npasp          cisl           pcisl
        Integer.class, Integer.class, Integer.class, String.class,
    //  napravl        naprotd        datan       vrach
        Integer.class, Integer.class, Date.class, Integer.class,
    //  diag          dataz       kodotd         pvizit_id
        String.class, Date.class, Integer.class, Integer.class,
    //  id_gosp        datap
        Integer.class, Date.class
    };
    private static final Class<?>[] PREZ_D_TYPES = {
    //  id             npasp          nisl           kodisl
        Integer.class, Integer.class, Integer.class, String.class
    };
    private static final Class<?>[] PREZ_L_TYPES = {
    //  id             npasp          nisl           cpok
        Integer.class, Integer.class, Integer.class, String.class
    };
    @SuppressWarnings("unused")
    private static final Class<?>[] INT_CLASS_TYPES = {
    //  pcod           name
        Integer.class, String.class
    };
    @SuppressWarnings("unused")
    private static final Class<?>[] STR_CLASS_TYPES = {
    //  pcod           name
        String.class, String.class
    };
    private static final Class<?>[] NAPR_TYPES = {
    //  id             id_pvizit      vidDoc         text
        Integer.class, Integer.class, Integer.class, String.class,
    //  preds          zaved          id_gosp
        Integer.class, Integer.class, Integer.class
    };
    @SuppressWarnings("unused")
    private static final Class<?>[] GOSP_TYPES = {
    //  id_gosp        npasp          cotd           cotd_name
        Integer.class, Integer.class, Integer.class, String.class,
    //  datap       datav       ishod          result
        Date.class, Date.class, Integer.class, Integer.class,
    //  vrach          vrach_fio 
        Integer.class, String.class
    };
    @SuppressWarnings("unused")
    private static final Class<?> [] ISL_TYPES = {
    //  nisl            cp0e1          np0e1         cldi          nldi
        Integer.class,  Integer.class, String.class, String.class, String.class,
    //  zpok          datav
        String.class, Date.class
    };

    public ServerLab(final ISqlSelectExecutor sse, final ITransactedSqlExecutor tse) {
        super(sse, tse);

        //Инициализация логгера с конфигом из файла ../../manager/log4j.xml;
        String manPath = new File(this.getClass().getProtectionDomain().getCodeSource()
            .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath();
        DOMConfigurator.configure(new File(manPath, "log4j.xml").getAbsolutePath());

        rsmMetod = new TResultSetMapper<>(Metod.class, METOD_FIELD_NAMES);
        rsmPokazMet = new TResultSetMapper<>(PokazMet.class, POKAZ_MET_FIELD_NAMES);
        rsmPokaz = new TResultSetMapper<>(Pokaz.class, POKAZ_FIELD_NAMES);
        rsmPisl = new TResultSetMapper<>(Pisl.class, PISL_FIELD_NAMES);
        rsmPrezD = new TResultSetMapper<>(PrezD.class, PREZ_D_FIELD_NAMES);
        rsmPrezL = new TResultSetMapper<>(PrezL.class, PREZ_L_FIELD_NAMES);
        rsmIntClass = new TResultSetMapper<>(IntegerClassifier.class, INT_CLASS_FIELD_NAMES);
        rsmStrClass = new TResultSetMapper<>(StringClassifier.class, STR_CLASS_FIELD_NAMES);
        rsmNapr = new TResultSetMapper<>(Napr.class, NAPR_FIELD_NAMES);
        rsmGosp = new TResultSetMapper<>(Gosp.class, GOSP_FIELD_NAMES);
        rsmIsl = new TResultSetMapper<>(Isl.class, ISL_FIELD_NAMES);
    }

    @Override
    public final void start() throws Exception {
        ThriftLab.Processor<Iface> proc =
            new ThriftLab.Processor<Iface>(this);

        tServer = new TThreadedSelectorServer(new Args(
            new TNonblockingServerSocket(configuration.labThrPort)).processor(proc));
        log.log(Level.INFO, "lab server started");
        tServer.serve();
    }

    @Override
    public final void stop() {
        tServer.stop();
        log.log(Level.INFO, "lab server stopped");
    }

    @Override
    public void testConnection() throws TException {
    }

    @Override
    public void saveUserConfig(final int id, final String config) throws TException {
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
    public final List<Metod> getMetod(final int kodissl) throws KmiacServerException {
        String sqlQuery = "SELECT DISTINCT np.pcod AS c_p0e1, no.obst, no.nameobst AS name_obst "
            + "FROM n_nsi_obst no JOIN n_stoim ns ON (ns.c_obst = no.obst) "
            + "JOIN n_p0e1 np ON (np.pcod = ns.c_p0e1) "
            + "WHERE np.pcod = ? "
            + "ORDER BY np.pcod, no.obst ";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, kodissl)) {
            return rsmMetod.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<PokazMet> getPokazMet(final String cNnz1, final int cotd)
            throws KmiacServerException {
        String sqlQuery = "SELECT DISTINCT n_ldi.pcod, n_ldi.name_n FROM n_ldi "
            + "JOIN s_ot01 ON (s_ot01.pcod=n_ldi.pcod) WHERE s_ot01.c_nz1 = ? "
            + "AND s_ot01.cotd = ? "
            + "ORDER BY n_ldi.pcod ";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cNnz1, cotd)) {
            return rsmPokazMet.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<Pokaz> getPokaz(final int kodissl, final String kodsyst)
            throws KmiacServerException {
        String sql = "SELECT np.pcod AS c_p0e1, nz.pcod AS c_n_nz1, nl.name_n, ns.pcod, ns.stoim "
            + "FROM n_stoim ns JOIN n_ldi nl ON (nl.pcod = ns.pcod) "
            + "JOIN n_nz1 nz ON (nz.pcod = nl.c_nz1) JOIN n_p0e1 np ON (np.pcod = ns.c_p0e1) "
            + "WHERE np.pcod = ? AND nz.pcod = ? "
            + "ORDER BY np.pcod, nz.pcod ";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sql, kodissl, kodsyst)) {
            return rsmPokaz.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }

    @Override
    public final int addPisl(final Pisl npisl) throws KmiacServerException {
        final int[] indexes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("INSERT INTO p_isl_ld (npasp, cisl, pcisl, napravl, naprotd, "
                + "datan, vrach, diag, dataz, kodotd, pvizit_id, id_gosp, datap) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ",
                true, npisl, PISL_TYPES, indexes);
            int id = sme.getGeneratedKeys().getInt("nisl");
            sme.setCommit();
            return id;
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        } catch (InterruptedException e1) {
            log.log(Level.ERROR, "Exception: ", e1);
            throw new KmiacServerException();
        }
    }

    @Override
    public final int addPrezd(final PrezD di) throws KmiacServerException {
        final int[] indexes = {1, 2, 3};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("INSERT INTO p_rez_d (npasp, nisl, kodisl) VALUES (?, ?, ?) ",
                true, di, PREZ_D_TYPES, indexes);
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            return id;
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        } catch (InterruptedException e1) {
            log.log(Level.ERROR, "Exception: ", e1);
            throw new KmiacServerException();
        }
    }

    @Override
    public final int addPrezl(final PrezL li) throws KmiacServerException {
        final int[] indexes = {1, 2, 3};
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT("INSERT INTO p_rez_l (npasp, nisl, cpok) VALUES (?, ?, ?) ",
                true, li, PREZ_L_TYPES, indexes);
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            return id;
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        } catch (InterruptedException e1) {
            log.log(Level.ERROR, "Exception: ", e1);
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getLabs(final int clpu)
            throws KmiacServerException {
        final String sqlQuery = "SELECT pcod, name FROM n_lds WHERE clpu = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, clpu)) {
            return rsmIntClass.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            ((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<StringClassifier> getOrgAndSys(final int cotd)
            throws KmiacServerException, TException {
        final String sqlQuery = "SELECT DISTINCT n_nz1.pcod, n_nz1.name "
            + "FROM n_nz1 JOIN s_ot01 ON (n_nz1.pcod=s_ot01.c_nz1) WHERE s_ot01.cotd = ?;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cotd)) {
            return rsmStrClass.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            ((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getPoliclinic() throws KmiacServerException {
        final String sqlQuery = "SELECT DISTINCT n_n00.pcod, "
            + "(n_m00.name_s || ', ' || n_n00.name) as name "
            + "FROM n_n00 INNER JOIN n_m00 ON n_m00.pcod = n_n00.clpu ";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery)) {
            return rsmIntClass.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            ((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getStacionarTypes()
            throws KmiacServerException, TException {
        final String sqlQuery = "SELECT pcod, name FROM n_tip WHERE pcod != 9 ";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery)) {
            return rsmIntClass.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            ((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getLpu() throws KmiacServerException,
            TException {
        final String sqlQuery = "SELECT pcod, name FROM n_m00;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery)) {
            return rsmIntClass.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            ((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
    }

    @Override
    public final List<IntegerClassifier> getVidIssled() throws KmiacServerException,
            TException {
        final String sqlQuery = "SELECT pcod, tip as name FROM n_lds;";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery)) {
            return rsmIntClass.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            e.getCause().printStackTrace();
            throw new KmiacServerException();
        }
    }

    @Override
    public final int addNapr(final Napr napr) throws KmiacServerException, TException {
        final int[] indexes = {1, 2, 3, 4, 5, 6};
        final String sqlQuery = "INSERT INTO p_napr "
            + "(id_pvizit, vid_doc, text, preds, zaved, id_gosp) "
            + "VALUES (?, ?, ?, ?, ?, ?) ";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            sme.execPreparedT(sqlQuery, true, napr, NAPR_TYPES, indexes);
            int id = sme.getGeneratedKeys().getInt("id");
            sme.setCommit();
            return id;
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            e.getCause().printStackTrace();
            throw new KmiacServerException();
        } catch (InterruptedException e1) {
            log.log(Level.ERROR, "Exception: ", e1);
            e1.printStackTrace();
            throw new KmiacServerException();
        }
    }

    @Override
    public List<Gosp> getGospList(int npasp, long dateStart, long dateEnd)
            throws KmiacServerException {
        final String sqlQuery = "SELECT c_gosp.id as id_gosp, c_gosp.npasp, c_gosp.cotd,"
            + "n_o00.name_u as cotd_name, "
            + "c_gosp.datap, c_otd.datav, c_otd.ishod, "
            + "c_otd.result, c_otd.vrach, s_vrach.fam as vrach_fio "
            + "FROM c_gosp "
            + "LEFT JOIN c_otd ON c_gosp.id = c_otd.id_gosp "
            + "LEFT JOIN n_o00 ON c_gosp.cotd = n_o00.pcod "
            + "LEFT JOIN s_vrach ON c_otd.vrach = s_vrach.pcod "
            + "WHERE c_gosp.npasp = ? ORDER BY datap";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, npasp)) {
            return rsmGosp.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            e.getCause().printStackTrace();
            throw new KmiacServerException();
        }
    }

    @Override
    public List<Isl> getIslList(int gospId) throws KmiacServerException {
        final String sql = "SELECT p_isl_ld.nisl, n_ldi.pcod, n_ldi.name_n, p_rez_l.zpok, "
    		+ "p_isl_ld.datav, '' as op_name, '' as rez_name  "
            + "FROM p_isl_ld "
            + "JOIN p_rez_l ON (p_rez_l.nisl = p_isl_ld.nisl) "
    		+ "JOIN n_ldi ON (n_ldi.pcod = p_rez_l.cpok) "
            + "WHERE (p_isl_ld.id_gosp = ?) "
            + "UNION "
            + "SELECT p_isl_ld.nisl, n_ldi.pcod, n_ldi.name_n, n_arez.name, "
            + "p_isl_ld.datav, p_rez_d.op_name, p_rez_d.rez_name "
            + "FROM p_isl_ld "
            + "JOIN p_rez_d ON (p_rez_d.nisl = p_isl_ld.nisl) "
            + "JOIN n_ldi ON (n_ldi.pcod = p_rez_d.kodisl) "
            + "LEFT JOIN n_arez ON (n_arez.pcod = p_rez_d.rez) "
            + "WHERE (p_isl_ld.id_gosp = ?);";
       try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sql, gospId, gospId)) {
           return rsmIsl.mapToList(acrs.getResultSet());
       } catch (SQLException e) {
           log.log(Level.ERROR, "Exception: ", e);
           throw new KmiacServerException(e.getMessage());
       }
    }

    @Override
    public String printIssl(int patId, String cabinet, String labName,
            String lpuNaprName, String vrachName, List<String> issledItems, String diag)
            throws KmiacServerException {
        final String path;
        final AutoCloseableResultSet acrs;
        String address;
        String fio;
        String datar;
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(
                path = File.createTempFile("muzdrav", ".htm").getAbsolutePath()), "utf-8")) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
            HtmTemplate htmTemplate = new HtmTemplate(
                new File(this.getClass().getProtectionDomain().getCodeSource()
                .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath()
                + "\\plugin\\reports\\LabNapr.htm");
            acrs = sse.execPreparedQuery(
                "SELECT fam, im, ot, datar, adm_ul, adm_dom FROM patient WHERE npasp = ?", patId);
            if (!acrs.getResultSet().next()) {
                throw new KmiacServerException("Logged user info not found.");
            } else {
                address = String.format("%s, %s", acrs.getResultSet().getString("adm_ul"),
                    acrs.getResultSet().getString("adm_dom"));
                fio = String.format("%s %s %s",  acrs.getResultSet().getString("fam"),
                    acrs.getResultSet().getString("im"), acrs.getResultSet().getString("ot"));
                datar = dateFormat.format(acrs.getResultSet().getDate("datar"));
            }
            acrs.close();
            htmTemplate.replaceLabels(
                true,
                labName,
                cabinet,
                " ", // дата исследования
                " ", // время исследования
                lpuNaprName,
                fio, // фио
                datar, // дата рождения
                address, // адрес
                diag, // диагноз
                vrachName, 
                "~issledItems", // исследования
                dateFormat.format(new Date(System.currentTimeMillis())) // дата направления
            );
            htmTemplate.refindLabels();
            for (String isslItem: issledItems) {
                htmTemplate.replaceLabel("~issledItems", "<br/>" + isslItem + "~issledItems");
                htmTemplate.refindLabels();
            }
            htmTemplate.replaceLabel("~issledItems", "");
            osw.write(htmTemplate.getTemplateText());
            return path;
        } catch (Exception e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new  KmiacServerException();
        }
    }

    @Override
    public List<IntegerClassifier> getParaotdLpu() throws KmiacServerException {
        final String sqlQuery = "SELECT DISTINCT n_m00.pcod, n_m00.name_s as name "
            + "FROM n_m00 JOIN n_lds ON (n_m00.pcod = n_lds.clpu);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery)) {
            return rsmIntClass.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
    }
}
