package ru.nkz.ivcgzo.serverPrint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;

import org.apache.thrift.TException;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadedSelectorServer;
import org.apache.thrift.transport.TNonblockingServerSocket;
import org.apache.thrift.server.TThreadedSelectorServer.Args;

import ru.nkz.ivcgzo.configuration;

import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftPrint.ThriftPrint;
import ru.nkz.ivcgzo.thriftPrint.ThriftPrint.Iface;

public class ServerPrint extends Server implements Iface {
	private TServer thrServ;

	public ServerPrint(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
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
		ThriftPrint.Processor<Iface> proc = new ThriftPrint.Processor<Iface>(this);
		thrServ = new TThreadedSelectorServer(new Args(new TNonblockingServerSocket(configuration.thrPort)).processor(proc));
		thrServ.serve();

	}

	@Override
	public void stop() {
		if (thrServ != null)
			thrServ.stop();

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
	public Object executeServerMethod(int id, Object... params) throws Exception {
		switch (id) {
		case 2101:
			return printSprBass((int) params[0], (int) params[1]);
		default:
			throw new Exception();
		}
	}
	

	@Override
	public String printSprBass(int npasp, int pol) throws KmiacServerException,
			TException {
		String path;
		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("spravBass", ".htm").getAbsolutePath()), "utf-8")) {
			AutoCloseableResultSet acrs;
			
			StringBuilder sb = new StringBuilder(0x10000);
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
			sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");

		sb.append("<title>Справка в бассейн</title></head>");
		sb.append("<body>");
		sb.append("<h4 align=center>Справка </h4>");
		acrs = sse.execPreparedQuery("select fam, im, ot from patient where npasp = ? ", npasp);
							if (acrs.getResultSet().next())
		sb.append(String.format("Дана: %s %s %s<br>", acrs.getResultSet().getString(1),acrs.getResultSet().getString(2),acrs.getResultSet().getString(3)));
		if (pol==1)	sb.append("в том, что он \"___\" ___________ 20___ г. прошел медицинское <br>");
		if (pol==2)	sb.append("в том, что она \"___\" ___________ 20___ г. прошла медицинское <br>");
		sb.append("обследование в __________________________________________________, <br />");
		sb.append("необходимое для посещения бассейна<br />");
		if (pol==1) sb.append("<b>Заключение терапевта: </b> <i>Практически здоров<br>");
		if (pol==2)	sb.append("<b>Заключение терапевта: </b> <i>Практически здоров(а)<br>");
		sb.append(" - анализ кала на яйца глист от \"___\" _______ 20___ г. - отрицательный<br>");
		sb.append(" - соскоб на энтеробиоз от \"___\" _______ 20___ г. - отрицательный</i><br><br>");
		sb.append("<center><b>Заниматься плаванием не противопоказано</b></center><br>");
		sb.append("Справка дана для предъявления в бассейн<br>");
		sb.append("<p align=\"left\"></p><br>МП &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp_____________");
		sb.append("<br><font size=1 color=black>&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
		sb.append("&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp");
		sb.append("Подпись врача</font>");
		sb.append("<br>");
		sb.append("Справка действительна до \"___\" _______ 20___ г.");
		
		osw.write(sb.toString());
		return path;
		}
	catch (SQLException e) {
		 ((SQLException) e.getCause()).printStackTrace();
		throw new KmiacServerException();
	}
	 catch (IOException e) {
		e.printStackTrace();
		throw new KmiacServerException();
	}
	}

	@Override
	public String printProtokol(int npasp, int userId, int pvizit_id,
			int pvizit_ambId, int cpol, int clpu, int nstr)
			throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		return null;
	}

}
