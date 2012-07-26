package ru.nkz.ivcgzo.serverReception;

import java.io.File;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.server.TThreadedSelectorServer.Args;
import org.apache.thrift.transport.TNonblockingServerSocket;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftReception.Patient;
import ru.nkz.ivcgzo.thriftReception.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftReception.Policlinic;
import ru.nkz.ivcgzo.thriftReception.PoliclinicNotFoundException;
import ru.nkz.ivcgzo.thriftReception.ReserveTalonOperationFailedException;
import ru.nkz.ivcgzo.thriftReception.Spec;
import ru.nkz.ivcgzo.thriftReception.SpecNotFoundException;
import ru.nkz.ivcgzo.thriftReception.Talon;
import ru.nkz.ivcgzo.thriftReception.TalonNotFoundException;
import ru.nkz.ivcgzo.thriftReception.ThriftReception;
import ru.nkz.ivcgzo.thriftReception.ThriftReception.Iface;
import ru.nkz.ivcgzo.thriftReception.Vidp;
import ru.nkz.ivcgzo.thriftReception.VidpNotFoundException;
import ru.nkz.ivcgzo.thriftReception.Vrach;
import ru.nkz.ivcgzo.thriftReception.VrachNotFoundException;

public class ServerReception extends Server implements Iface {

////////////////////////////////////////////////////////////////////////
//                          Fields                                    //
////////////////////////////////////////////////////////////////////////

    private TServer thrServ;

    private static Logger log = Logger.getLogger(ServerReception.class.getName());

////////////////////////////////Mappers /////////////////////////////////

    private TResultSetMapper<Patient, Patient._Fields> rsmPatient;
    private TResultSetMapper<Policlinic, Policlinic._Fields> rsmPoliclinic;
    private TResultSetMapper<Spec, Spec._Fields> rsmSpec;
    private TResultSetMapper<Vrach, Vrach._Fields> rsmVrach;
    private TResultSetMapper<Vidp, Vidp._Fields> rsmVidp;
    private TResultSetMapper<Talon, Talon._Fields> rsmTalon;

////////////////////////////Field Name Arrays ////////////////////////////

    private static final String[] PATIENT_FIELD_NAMES = {
        "npasp", "fam", "im", "ot", "datar", "poms_ser", "poms_nom"
    };
    private static final String[] POLICLINIC_FIELD_NAMES = {
        "pcod", "name"
    };
    private static final String[] SPEC_FIELD_NAMES = {
        "pcod", "name"
    };
    private static final String[] VIDP_FIELD_NAMES = {
        "pcod", "name", "vcolor"
    };
    private static final String[] VRACH_FIELD_NAMES = {
        "pcod", "fam", "im", "ot"
    };
    private static final String[] TALON_FIELD_NAMES = {
        "id", "ntalon", "vidp", "timepn", "timepk", "datap", "npasp", "dataz", "prv"
    };

////////////////////////////////////////////////////////////////////////
//                         Constructors                               //
////////////////////////////////////////////////////////////////////////

    public ServerReception(final ISqlSelectExecutor sse, final ITransactedSqlExecutor tse) {
        super(sse, tse);

        //Инициализация логгера с конфигом из файла ../../manager/log4j.xml;
        String manPath = new File(this.getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getPath()).getParentFile().getParentFile().getAbsolutePath();
        DOMConfigurator.configure(new File(manPath, "log4j.xml").getAbsolutePath());

        rsmPatient = new TResultSetMapper<>(Patient.class, PATIENT_FIELD_NAMES);
        rsmPoliclinic = new TResultSetMapper<>(Policlinic.class, POLICLINIC_FIELD_NAMES);
        rsmSpec = new TResultSetMapper<>(Spec.class, SPEC_FIELD_NAMES);
        rsmVidp = new TResultSetMapper<>(Vidp.class, VIDP_FIELD_NAMES);
        rsmVrach = new TResultSetMapper<>(Vrach.class, VRACH_FIELD_NAMES);
        rsmTalon = new TResultSetMapper<>(Talon.class, TALON_FIELD_NAMES);
    }

////////////////////////////////////////////////////////////////////////
//                       Private Methods                              //
////////////////////////////////////////////////////////////////////////



////////////////////////////////////////////////////////////////////////
//                       Public Methods                               //
////////////////////////////////////////////////////////////////////////

////////////////////////Start/Stop Methods /////////////////////////////

    @Override
    public final void start() throws Exception {
        ThriftReception.Processor<Iface> proc =
                new ThriftReception.Processor<Iface>(this);
        thrServ = new TThreadedSelectorServer(new Args(
                new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
        log.info("Start serverReception");
        thrServ.serve();
    }

    @Override
    public final void stop() {
        if (thrServ != null) {
            thrServ.stop();
            log.info("Stop serverReception");
        }
    }

//////////////////// Configuration Methods /////////////////////////////

    @Override
    public void testConnection() throws TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void saveUserConfig(final int id, final String config) throws TException {
        // TODO Auto-generated method stub
    }

/////////////////////// Select Methods //////////////////////////////////

    @Override
    public final Patient getPatient(final String omsSer, final String omsNum)
            throws KmiacServerException, PatientNotFoundException, TException {
        return null;
    }

    @Override
    public final List<Policlinic> getPoliclinic() throws KmiacServerException,
            PoliclinicNotFoundException, TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final List<Spec> getSpec(final int cpol) throws KmiacServerException,
            SpecNotFoundException, TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final List<Vrach> getVrach(final int cpol, final String cdol)
            throws KmiacServerException, VrachNotFoundException, TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final List<Vidp> getVidp() throws KmiacServerException,
            VidpNotFoundException, TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public final List<Talon> getTalon(final int cpol, final String cdol, final int pcod)
            throws KmiacServerException, TalonNotFoundException, TException {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void reserveTalon(final Patient pat, final Talon talon)
            throws KmiacServerException, ReserveTalonOperationFailedException,
            TException {
        // TODO Auto-generated method stub
    }

    @Override
    public void releaseTalon(final Talon talon) throws TException {
        // TODO Auto-generated method stub
    }

}
