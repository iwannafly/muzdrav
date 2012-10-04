package ru.nrz.ivcgzo.serverOutputInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
//import java.util.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

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
import ru.nkz.ivcgzo.thriftCommon.classifier.StringClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOutputInfo.InputSvodVed;
import ru.nkz.ivcgzo.thriftOutputInfo.OutputSvodVed;
import ru.nkz.ivcgzo.thriftOutputInfo.ThriftOutputInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.ThriftOutputInfo.Iface;
//import ru.nkz.ivcgzo.thriftOutputInfo.Input_info;

public class OutputInfo extends Server implements Iface {
	private TServer thrServ;
	//public Input_info inputInfo;
	public int kolz1, kolz2, kolz3, kolz4, kolz5, kolz6, kolz7, kolz8, xind, perv;
	public String cdiag, kat;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
	
	
	public OutputInfo(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		// TODO Auto-generated constructor stub
	}

	@SuppressWarnings("deprecation")
	public void Vimotchik025() throws TException, ParseException {
		String d1 = InputSvodVed._Fields.DATEB.toString();
		String d2 = InputSvodVed._Fields.DATEF.toString();
		String namepol = InputSvodVed._Fields.NAMEPOL.toString();
		// Предыдущий год
		String yaerr = String.valueOf((sdf.parse(d2).getYear()-1));
		// Текущий год по периоду
		String yaetr = String.valueOf((sdf.parse(d2).getYear()));
		// Конец отчетного периода
		Date kpo = (Date) sdf.parse(yaerr+"-12-25");
		// Конец года по периоду
		Date kpg = (Date) sdf.parse(yaetr+"-12-31"); 
		xind =0;
		final String sqlQuerySpat = "select f.npasp,f.diag,f.datap,d.id_diag_amb,k.fam,k.im,k.ot,k.pol,k.jitel,k.cpol_pr,d.disp,f.cdol,k.adp_ul,k.adp_dom,k.adp_kv,k.mrab,k.poms_ndog,k.sgrp,f.opl,d.datad,d.xzab,f.cpos,f.dataz from p_vizit_amb f,patient k,p_diag d where f.npasp = k.npasp and f.diag = d.diag and f.dataz between '2012-01-01'::date and '2012-12-31'::date and substr(f.cdol,1,2)<>'33' and substr(f.cdol,1,2)<>'34' and substr(f.cdol,1,3)<>'142' and substr(f.cdol,1,3)<>'143' and substr(f.cdol,1,3)<>'172' and substr(f.cdol,1,3)<>'212' order by f.npasp,f.diag,f.datap desc,d.disp desc";
		try (AutoCloseableResultSet spat = sse.execPreparedQuery(sqlQuerySpat)) { // Добавить параметры даты
			spat.getResultSet().first();
			int xdiag0=spat.getResultSet().getInt("id_diag_amb");
			while (spat.getResultSet().next()) {
				//if (spat.getResultSet().getInt(ishod))<>0) and
				if (spat.getResultSet().getString("diag").trim().charAt(0)=='Z') {
					// Проверка на первичность
					if (spat.getResultSet().getInt("xzab")==1) kolz1 = kolz1++;
					if (spat.getResultSet().getInt("xzab")==2) kolz2 = kolz3++;
					xind = spat.getResultSet().getInt("npasp");
					cdiag = spat.getResultSet().getString("diag");
					// Здесь должны выбираться данные из pbol - ?
					final String sqlQuerySkat = "select lgot from p_kov where npasp=",xind = null;
					try (AutoCloseableResultSet skat = sse.execPreparedQuery(sqlQuerySkat)) {
						while (skat.getResultSet().next()) {
							kat=kat+"-"+skat.getResultSet().getString("lgot");
						}
					} catch (SQLException e) {
						throw new TException(e);
					}
					perv=3;
					if (spat.getResultSet().getInt("xzab")==1) perv=1;
					else {
						if ((spat.getResultSet().getDate("dataz").after(kpo)) && (spat.getResultSet().getDate("dataz").before(kpg))); {
							// Добавить в sql условие, что ishod<>0
							try (AutoCloseableResultSet scount = sse.execPreparedQuery("select count (*) from p_visit_amb where npasp= ? and id_diag_amb= ? and datap < ?", xind, spat.getResultSet().getInt("id_diag_amb"), d2)) {
								while (scount.getResultSet().next()) {
									if (scount.getResultSet().getInt(0)==1) {
										if ((scount.getResultSet().getDate("datad").after(kpo)) && (spat.getResultSet().getDate("datad").before(kpg))) perv=1; else perv=2;
									} else {
										
									}
							}
						}
						
					}
					}}
			}
		} catch (SQLException e) {
		throw new TException(e);
		}
		
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
		ThriftOutputInfo.Processor<Iface> proc = new ThriftOutputInfo.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();
	
	}

	@Override
	public void stop() {
		if (thrServ != null)
			thrServ.stop();
	}

	@Override
	public String printSvodVed(OutputSvodVed osv) throws KmiacServerException,
			TException {
		// TODO Auto-generated method stub
		return null;
	}

	

	
}
