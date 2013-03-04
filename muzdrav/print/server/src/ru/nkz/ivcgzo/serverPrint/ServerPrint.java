package ru.nkz.ivcgzo.serverPrint;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

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
		case 2102:
			return printMedCart((int) params[0], (int) params[1], (int) params[2]);
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

	@Override
	public String printMedCart(int npasp, int clpu, int cpol)
			throws KmiacServerException, TException {
		String path;
		String ogrn = "", gender = "", name_clpu = "", name_cpol = "",
			   fam = "", im = "", ot = "", nombk = "", snils = "", 
			   name_kas = "", adm_adress = "", adp_adress = "", tel = "",
			   doc_pat = "", polis = "", name_lgot = "", datar = "",
			   doc_lgot = "", mesto_rab = "", gr_inv = "", dog_oms = null;
		int pol = 0, id_pat = 0;
        try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(
                path = File.createTempFile("muzdrav", ".htm").getAbsolutePath()), "utf-8")) {
            AutoCloseableResultSet acrs;
            acrs = sse.execPreparedQuery("select c_ogrn, name_s from n_m00 where pcod = ? ", clpu);
                 if (acrs.getResultSet().next()){ 
                    if (acrs.getResultSet().getString(1) != null) ogrn = acrs.getResultSet().getString(1);
                    if (acrs.getResultSet().getString(2) != null) name_clpu = acrs.getResultSet().getString(2);
                 }
            acrs.close();
            
             acrs = sse.execPreparedQuery("select name_u from n_n00 where pcod = ? ", cpol);
                 if (acrs.getResultSet().next()){ 
                    if (acrs.getResultSet().getString(1) != null) name_cpol = acrs.getResultSet().getString(1);
                 }
            acrs.close();
            
            acrs = sse.execPreparedQuery("select patient.npasp, p_nambk.nambk, patient.fam, patient.im, " +
            							"patient.ot, patient.datar, patient.adp_gorod, patient.adp_ul, " +
            							"patient.adp_dom, patient.adp_kv, " +
            							"patient.adm_gorod,patient.adm_ul, patient.adm_dom, patient.adm_kv, " +
            							"patient.poms_ser, patient.poms_nom, patient.pol, patient.snils, " + 
            							"n_az0.name, patient.docser, patient.docnum, n_kas.name, patient.tel, patient.poms_ndog " +
            							"from patient left join p_nambk on (patient.npasp=p_nambk.npasp) left join n_az0 on (patient.tdoc=n_az0.pcod) " +
            							"left join n_kas on (patient.poms_strg=n_kas.pcod) " +
            							"where patient.npasp = ? and p_nambk.cpol = ? ", npasp, cpol);
                 if (acrs.getResultSet().next()){ 
                    if (acrs.getResultSet().getString(2) != null) nombk = acrs.getResultSet().getString(2); else nombk = acrs.getResultSet().getString(1);
                    if (acrs.getResultSet().getString(3) != null) fam = acrs.getResultSet().getString(3);
                    if (acrs.getResultSet().getString(4) != null) im = acrs.getResultSet().getString(4);
                    if (acrs.getResultSet().getString(5) != null) ot = acrs.getResultSet().getString(5);
                    if (acrs.getResultSet().getString(6) != null) datar = acrs.getResultSet().getString(6);
                    adp_adress = acrs.getResultSet().getString(7) + ", " + acrs.getResultSet().getString(8) + ", " +
                    			 acrs.getResultSet().getString(9) + ", " + acrs.getResultSet().getString(10);
                    adm_adress = acrs.getResultSet().getString(11) + ", " + acrs.getResultSet().getString(12) + ", " +
                    			 acrs.getResultSet().getString(13) + ", " + acrs.getResultSet().getString(14);
                	if (acrs.getResultSet().getString(15)==null) polis=acrs.getResultSet().getString(16);
					if (acrs.getResultSet().getString(16)==null)polis=acrs.getResultSet().getString(15);
					if ((acrs.getResultSet().getString(16)!=null) && (acrs.getResultSet().getString(15)!=null)) polis=acrs.getResultSet().getString(15)+ " " +acrs.getResultSet().getString(16);
					if ((acrs.getResultSet().getString(16)==null) && (acrs.getResultSet().getString(15)==null)) polis="";
					if (acrs.getResultSet().getInt(17) != 0) pol = acrs.getResultSet().getInt(17);
					if (acrs.getResultSet().getString(18) != null) snils = acrs.getResultSet().getString(18);
					if (acrs.getResultSet().getString(19) != null) doc_pat=acrs.getResultSet().getString(19) + ", " 
																	+ acrs.getResultSet().getString(20) + ", " 
																	+ acrs.getResultSet().getString(21);
					if (acrs.getResultSet().getString(22) != null) name_kas = acrs.getResultSet().getString(22);
					if (acrs.getResultSet().getString(23) != null) tel = acrs.getResultSet().getString(23);
					if (acrs.getResultSet().getString(24) != null) dog_oms = acrs.getResultSet().getString(24);
					
                 }
            acrs.close();
            

            
           if (pol == 1) {
                gender = "мужской";
            } else if (pol == 2) {
                gender = "женский";
            } else {
                gender = "";
            }
 			
			StringBuilder sb = new StringBuilder(0x10000);
			sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
			sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
			sb.append("<head>");
			sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
			sb.append("<title>Медицинская карта амбулаторного больного</title>");
			sb.append("<style type=\"text/css\">");
			sb.append("	body {font-size:11px}");
			sb.append("table {\");");
			sb.append("width: 100%; /* Ширина таблицы */");
			sb.append("sb.append(\"border-collapse:collapse;");
			sb.append("}");
			sb.append("tr {height: 20px;}\");");
			sb.append("td {\");");
			sb.append("padding:0px;\");");
			sb.append("vertical-align: top;\");");
			sb.append("}\");");
			sb.append("	</style>");
			sb.append("</head>");
			sb.append("<body>");
			sb.append("<table>");
			sb.append("<tr style=\"height:normal;\">");
			sb.append("<td style=\"width:50%;\">");
			sb.append("<div style=\"width:49%; float:left;\">");
			sb.append("Министерство здравоохранения и социального");
			sb.append("<br />развития Российской Федерации");
			sb.append(String.format("<br /> %s %s", name_clpu, name_cpol));
			sb.append(String.format("<br />Код ОГРН %s", ogrn));
			sb.append("</div>");
			sb.append("<div  style=\"width:49%; float:right; text-align:right\">");
			sb.append("	Медицинская документация");
			sb.append("<br />Форма № 025/у-04");
			sb.append("<br />Утверждена приказом Минздравсоцразвития");
			sb.append("<br />Российской Федерации");
			sb.append("</div>");
			sb.append("<br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>");
			sb.append("<h3 style=\"text-align:center\">");
			sb.append("	Медицинская карта амбулаторного больного");
			sb.append(String.format("<br/>№ %s", nombk));
			sb.append("</h3>");
			sb.append(String.format("1. Страховая медицинская организация: %s", name_kas));
			sb.append("<br/>");
			sb.append("<div style=\"width:49%; float:left;\">");
			sb.append(String.format("2. Номер полиса ОМС: %s", polis));
			sb.append(String.format("<br />3. СНИЛС: %s", snils));
			sb.append("</div>");
			sb.append("<div  style=\"width:50%; float:right;\">");
			sb.append(String.format("Номер договора:  %s", dog_oms));
			sb.append("</div>");
			sb.append("<br/><br/><br/><br/>");
			sb.append("<h2 style=\"text-align:center\">");
			sb.append(String.format("%s %s %s", fam, im, ot));
			sb.append("</h2>");
			sb.append("<br/>");
			sb.append("<table>");	
			sb.append("<tr>");
			sb.append(String.format("<td>8.Пол: %s</td>", gender));
			sb.append(String.format("<td>9.Дата рождения: %s</td>", datar));
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td>10.Адрес места жительства</td>");
			sb.append(String.format("<td>%s</td>", adp_adress));
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td>11.Адрес по прописке</td>");
			sb.append(String.format("<td> %s</td>", adm_adress));
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td>12.Телефоны: </td>");
			sb.append(String.format("<td> %s</td>", tel));
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td>Документ, удостоверяющий личность: </td>");
			sb.append(String.format("<td> %s</td>", doc_pat));
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td>13.Документ, удостоверяющий право на льготное обеспечение: </td>");
			sb.append("<td>______________________________________________________</td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("	<td>14.Инвалидность</td>");
			sb.append("<td>___________________________</td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td>15.Место работы</td>");
			sb.append("<td>______________________________________________________</td>");
			sb.append("</tr>");
			sb.append("</table>");
			sb.append("<h3 style=\"text-align:center\">");
			sb.append("16. Перемена адреса и места работы");
			sb.append("</h3>");
			sb.append("<table border=\"1px solid black\">");		
			sb.append("<tr>");
			sb.append("<th>Дата</th>");
			sb.append("<th>Новый адрес (новое место работы)</th>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("</table>");
			sb.append("</td>");
			sb.append("<td style=\"width:50%;\">");
			sb.append("<h3 style=\"text-align:center\">");
			sb.append("17.  ЗАБОЛЕВАНИЯ, ПОДЛЕЖАЩИЕ ДИСПАНСЕРНОМУ НАБЛЮДЕНИЮ");
			sb.append("</h3>");
			sb.append("<table border=\"1px solid black\">");		
			sb.append("<tr>");
			sb.append("<th>№ п/п</th>");
			sb.append("<th>Наименование заболевания</th>");
			sb.append("<th>Дата постановки на д/у</th>");
			sb.append("<th>Должность подпись врача</th>");
			sb.append("<th>Дата снятия с д/у</th>");
			sb.append("<th>Должность подпись врача</th>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("</table>");
			sb.append("<br/>18. ГРУППА КРОВИ _______________________");
			sb.append("<br/>19. ЛЕКАРСТВЕННАЯ НЕПЕРЕНОСИМОСТЬ :");
			sb.append("<br/>19.1. _____________________________________");
			sb.append("<br/>19.2. _____________________________________");
			sb.append("<br/>19.3. _____________________________________<br/>");
			sb.append("<h3 style=\"text-align:center\">");
			sb.append("ПРОВЕДЕНИЕ ПРОФИЛАКТИЧЕСКИХ ПРИВИВОК");
			sb.append("</h3>");
			sb.append("<table border=\"1px solid black\">	");	
			sb.append("<tr>");
			sb.append("<th>Дата</th>");
			sb.append("<th>Наименование прививки</th>");
			sb.append("<th>Вакцина</th>");
			sb.append("<th>Серия</th>");
			sb.append("<th>Доза</th>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("</table>");
			sb.append("</td>");
			sb.append("</tr>");
			sb.append("</table>");
			sb.append("</body>");
			sb.append("</html>");

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

}
