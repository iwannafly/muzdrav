package ru.nkz.ivcgzo.serverreg;

import java.util.List;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.thriftreg.GospId;
import ru.nkz.ivcgzo.thriftreg.PatientAgentInfoStruct;
import ru.nkz.ivcgzo.thriftreg.PatientAllGospInfoStruct;
import ru.nkz.ivcgzo.thriftreg.PatientAllStruct;
import ru.nkz.ivcgzo.thriftreg.PatientGospInfoStruct;
import ru.nkz.ivcgzo.thriftreg.PatientInfoStruct;
import ru.nkz.ivcgzo.thriftreg.PatientKontingentInfoStruct;
import ru.nkz.ivcgzo.thriftreg.PatientLgotaInfoStruct;
import ru.nkz.ivcgzo.thriftreg.PatientPersonalInfoStruct;
import ru.nkz.ivcgzo.thriftreg.PatientSignInfoStruct;
import ru.nkz.ivcgzo.thriftreg.SpravStruct;
import ru.nkz.ivcgzo.thriftreg.Thriftreg.Iface;

public class ServerReg extends Server implements Iface {

	public ServerReg(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getServerVersion() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getClientVersion() throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientAllStruct> getAllPatientInfo(PatientInfoStruct patient)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PatientPersonalInfoStruct getPatientPersonalInfo(int npasp)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PatientAgentInfoStruct getPatientAgentInfo(int npasp)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientLgotaInfoStruct> getPatientLgotaInfo(int npasp)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientKontingentInfoStruct> getPatientKontingentInfo(int npasp)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PatientSignInfoStruct getPatientSignInfo(int npasp)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PatientAllGospInfoStruct> getPatientAllGospInfo(int npasp)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PatientGospInfoStruct getPatientGospInfo(int npasp, int id)
			throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SpravStruct> getSpravInfo(String param) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getAddPatient(PatientPersonalInfoStruct patinfo)
			throws TException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void AddLgota(PatientLgotaInfoStruct lgota) throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void AddKont(PatientKontingentInfoStruct kont) throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void AddAgent(PatientAgentInfoStruct agent) throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void AddSign(PatientSignInfoStruct sign) throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public GospId getAddGosp(PatientGospInfoStruct gosp) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void DeletePatient(int npasp) throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void DeleteLgota(int npasp, int lgota) throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void DeleteKont(int npasp, int kateg) throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void DeleteAgent(int npasp) throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void DeleteSign(int npasp) throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void DeleteGosp(int npasp, int id) throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void UpdatePatient(PatientPersonalInfoStruct patinfo)
			throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void UpdateLgota(int npasp, int lgota) throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void UpdateKont(int npasp, int kateg) throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void UpdateAgent(PatientAgentInfoStruct agent) throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public void UpdateSign(PatientSignInfoStruct sign) throws TException {
		// TODO Auto-generated method stub

	}

	@Override
	public GospId UpdateGosp(PatientGospInfoStruct gosp) throws TException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void start() throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void stop() {
		// TODO Auto-generated method stub

	}

}
