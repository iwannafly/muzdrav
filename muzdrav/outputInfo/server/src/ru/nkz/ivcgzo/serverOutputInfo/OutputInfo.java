package ru.nkz.ivcgzo.serverOutputInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.IntBuffer;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;

import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOutputInfo.InputAuthInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.InputFacZd;
import ru.nkz.ivcgzo.thriftOutputInfo.InputPasUch;
import ru.nkz.ivcgzo.thriftOutputInfo.InputPlanDisp;
import ru.nkz.ivcgzo.thriftOutputInfo.InputStructPos;
import ru.nkz.ivcgzo.thriftOutputInfo.InputStructPosAuth;
import ru.nkz.ivcgzo.thriftOutputInfo.InputSvodVed;
import ru.nkz.ivcgzo.thriftOutputInfo.ThriftOutputInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.ThriftOutputInfo.Iface;
import ru.nkz.ivcgzo.thriftOutputInfo.UchException;
import ru.nkz.ivcgzo.thriftOutputInfo.UchastokInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.UchastokNum;
import ru.nkz.ivcgzo.thriftOutputInfo.VINotFoundException;
import ru.nkz.ivcgzo.thriftOutputInfo.VTDuplException;
import ru.nkz.ivcgzo.thriftOutputInfo.VTException;
import ru.nkz.ivcgzo.thriftOutputInfo.VrachInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.VrachTabel;
//import ru.nkz.ivcgzo.thriftOutputInfo.Input_info;


public class OutputInfo extends Server implements Iface {
	

	// Информация о врачах
	serverVrachInfo svi;
	// Информация о врачах
	serverUchNum snu;
	
	private TServer thrServ;

	public OutputInfo(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		
		// Информация о врачах
		//svi = new serverVrachInfo (sse, tse);
		
		// Номер участка
		snu = new serverUchNum (sse, tse);
		
	}
	
	// Переназначение методов
	// Паспорт участка
	@Override
	public String printPasUch(InputAuthInfo iaf, InputPasUch ipu) throws KmiacServerException, TException {
		//serverPaspUch spu = new serverPaspUch(sse, tse);
		//return spu.printPasUch(iaf, ipu);
		return new serverPaspUch(sse, tse).printPasUch(iaf, ipu);
	}

	// Сведения о структуре посещений
	@Override
	public String printStructPos(InputStructPosAuth ispa, InputStructPos isp)
			throws KmiacServerException, TException {
		return new serverStructPos (sse, tse).printStructPos(ispa, isp);
	}
	
	// Факторы, влияющие на состояние здоровья
	@Override
	public String printFacZd(InputAuthInfo iaf, InputFacZd ifz)
			throws KmiacServerException, TException {
		return new serverFacZd (sse, tse).printFacZd(iaf, ifz);
	}
	
	// Сводная ведомость зарегестрированных заболеваний
	@Override
	public String printSvodVed(InputAuthInfo iaf, InputSvodVed isv)
			throws KmiacServerException, TException {
		return new serverSvodVed (sse, tse).printSvodVed(iaf, isv);
	}
	
	// Табель врача
	@Override
	public List<VrachInfo> getVrachTableInfo(int cpodr)
			throws VINotFoundException, KmiacServerException, TException {
		return new serverVrachInfo (sse, tse).getVrachTableInfo(cpodr);
	}

	@Override
	public List<VrachTabel> getVrachTabel(int pcod) throws VTException,
			VTDuplException, KmiacServerException, TException {
		return new serverVrachInfo (sse, tse).getVrachTabel(pcod);
	}

	@Override
	public int addVT(VrachTabel vt) throws VTException, VTDuplException,
			KmiacServerException, TException {
		serverVrachInfo svi = new serverVrachInfo (sse, tse);
		return svi.addVT(vt);
	}

	@Override
	public void updateVT(VrachTabel vt) throws KmiacServerException, TException {
		serverVrachInfo svi = new serverVrachInfo (sse, tse);
		svi.updateVT(vt);
	}

	@Override
	public void deleteVT(int vt) throws TException {
		new serverVrachInfo (sse, tse).deleteVT(vt);
	}

	@Override
	public List<UchastokInfo> getUch(int cpol) throws UchException,
			KmiacServerException, TException {
		return new serverUchNum (sse, tse).getUch(cpol);
	}

	@Override
	public List<UchastokNum> getUchNum(int pcod) throws UchException,
			KmiacServerException, TException {
		return new serverUchNum (sse, tse).getUchNum(pcod);
	}

	@Override
	public int addUchNum(UchastokNum uchNum) throws KmiacServerException,
			TException {
		serverUchNum snu = new serverUchNum (sse, tse);
		return snu.addUchNum(uchNum);
	}

	@Override
	public void updateUchNum(UchastokNum uchNum) throws KmiacServerException,
			TException {
		serverUchNum snu = new serverUchNum (sse, tse);
		snu.updateUchNum(uchNum);
	}

	@Override
	public void deleteUchNum(int uchNum) throws TException {
		serverUchNum snu = new serverUchNum (sse, tse);
		snu.deleteUchNum(uchNum);
	}

	@Override
	public String printPlanDisp(InputPlanDisp ipd) throws KmiacServerException,
			TException {
		serverPlanDisp spd = new serverPlanDisp (sse, tse);
		return spd.printPlanDisp(ipd);
	}

	@Override
	public String printSvedDispObs(InputPlanDisp ipd)
			throws KmiacServerException, TException {
		serverSvodDispObs sdo = new serverSvodDispObs (sse, tse);
		return sdo.printSvedDispObs(ipd);
	}

	@Override
	public String printDnevVr() throws KmiacServerException, TException {
		serverDnevVr sdo = new serverDnevVr (sse, tse);
		return sdo.printDnevVr();
	}
	
	@Override
	public String printNoVipPlanDisp(InputPlanDisp ipd) {
		return null;
	}

	@Override
	public String printOtDetPol(InputPlanDisp ipd) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String nagrvr(int cpol) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		return null;
	}

	// Соединение

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
		ThriftOutputInfo.Processor<Iface> proc = new ThriftOutputInfo.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();
	
	}

	@Override
	public void stop() {
		if (thrServ != null)
			thrServ.stop();
	}

}