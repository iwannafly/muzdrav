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
			   doc_lgot = "", mesto_rab = "", inv = "", dog_oms = "";
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
            							"n_az0.name, patient.docser, patient.docnum, n_kas.name, patient.tel, patient.poms_ndog, " +
            							"patient.mrab, patient.name_mr " +
            							"from patient left join p_nambk on (patient.npasp=p_nambk.npasp) left join n_az0 on (patient.tdoc=n_az0.pcod) " +
            							"left join n_kas on (patient.poms_strg=n_kas.pcod) " +
            							"where patient.npasp = ? ", npasp);
                 if (acrs.getResultSet().next()){ 
                    if (acrs.getResultSet().getString(2) != null) nombk = acrs.getResultSet().getString(2); else nombk = acrs.getResultSet().getString(1);
                    if (acrs.getResultSet().getString(3) != null) fam = acrs.getResultSet().getString(3);
                    if (acrs.getResultSet().getString(4) != null) im = acrs.getResultSet().getString(4);
                    if (acrs.getResultSet().getString(5) != null) ot = acrs.getResultSet().getString(5);
                    if (acrs.getResultSet().getString(6) != null) datar = acrs.getResultSet().getString(6);
                    if (acrs.getResultSet().getString(10)!=null)
                    adp_adress = acrs.getResultSet().getString(7) + ", " + acrs.getResultSet().getString(8) + ", " +
                    			 acrs.getResultSet().getString(9) + ", " + acrs.getResultSet().getString(10);
                    else adp_adress = acrs.getResultSet().getString(7) + ", " + acrs.getResultSet().getString(8) + ", " +
                    			 acrs.getResultSet().getString(9);
                    if (acrs.getResultSet().getString(14)!=null)
                    adm_adress = acrs.getResultSet().getString(11) + ", " + acrs.getResultSet().getString(12) + ", " +
                    			 acrs.getResultSet().getString(13) + ", " + acrs.getResultSet().getString(14);
                    else adm_adress = acrs.getResultSet().getString(11) + ", " + acrs.getResultSet().getString(12) + ", " +
                    			 acrs.getResultSet().getString(13);
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
					if (acrs.getResultSet().getInt(25) != 0) mesto_rab = acrs.getResultSet().getString(26);
					
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
			sb.append("<STYLE TYPE=\"text/css\">");
			sb.append("	<!--");
			sb.append("@page { size: 29.7cm 21cm; margin-left: 1cm; margin-right: 1.06cm; margin-top: 1.01cm; margin-bottom: 1.2cm }");
			sb.append("P { margin-bottom: 0.21cm; direction: ltr; color: #000000; widows: 2; orphans: 2 }");
			sb.append("P.western { font-family: \"Times New Roman\", serif; font-size: 11pt; so-language: ru-RU }");
			sb.append("P.cjk { font-family: \"Times New Roman\", serif; font-size: 11pt }");
			sb.append("P.ctl { font-family: \"Times New Roman\", serif; font-size: 11pt; so-language: ar-SA }");
			sb.append("A:link { color: #000080; so-language: zxx; text-decoration: underline }");
			sb.append("A:visited { color: #800000; so-language: zxx; text-decoration: underline }");
			sb.append("-->");
			sb.append("</STYLE>");
			sb.append("</head>");
			sb.append("<body>");
			sb.append("<table>");
			sb.append("<tr style=\"height:normal;\">");
			sb.append("<td style=\"border-top: 1px solid white; border-bottom: 1px solid white; border-left: 1px solid white; border-right: none; padding: 5px; \">");
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
			sb.append("<br/><br/><br/><br/><br/><br/><br/><br/><br/>");
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
			sb.append("<br>4. Код льготы");
			acrs.close();
			acrs = sse.execPreparedQuery("select lgot, gri, drg from p_kov where npasp = ? ", npasp);
			if (acrs.getResultSet().next()) {
				if (acrs.getResultSet().getString(2)!= null )inv = acrs.getResultSet().getString(2)+", "+acrs.getResultSet().getString(3);
				do {
					if (acrs.getResultSet().getString(1)!=null) sb.append(String.format(" %s, <br>", acrs.getResultSet().getString(1)));

					} while (acrs.getResultSet().next());
			}
			sb.append("</div>");
			sb.append("<br/>");
			sb.append("<h2 style=\"text-align:center\">");
			sb.append(String.format("%s %s %s", fam, im, ot));
			sb.append("</h2>");
			sb.append("<table border=\"1\" style=\"border-collapse: collapse;\">");	
			sb.append("<tr bgcolor=\"white\">");
			sb.append(String.format("<td style=\"font: 15px times new roman;\">8.Пол: %s</td>", gender));
			sb.append(String.format("<td style=\"font: 15px times new roman;\">9.Дата рождения: %s</td>", datar));
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<td style=\"font: 15px times new roman;\">10.Адрес места жительства</td>");
			sb.append(String.format("<td style=\"font: 15px times new roman;\">%s</td>", adp_adress));
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<td style=\"font: 15px times new roman;\">11.Адрес по прописке</td>");
			sb.append(String.format("<td style=\"font: 15px times new roman;\"> %s</td>", adm_adress));
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<td style=\"font: 15px times new roman;\">12.Телефоны: </td>");
			sb.append(String.format("<td> %s</td>", tel));
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<td style=\"font: 15px times new roman;\">Документ, удостоверяющий личность: </td>");
			sb.append(String.format("<td style=\"font: 15px times new roman;\"> %s</td>", doc_pat));
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<td style=\"font: 15px times new roman;\">13.Документ, удостоверяющий право на льготное обеспечение: </td>");
			sb.append("<td style=\"font: 15px times new roman;\">______________________________________________________</td>");
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("	<td style=\"font: 15px times new roman;\">14.Инвалидность</td>");
			sb.append(String.format("<td>%s</td>", inv));
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<td style=\"font: 15px times new roman;\">15.Место работы</td>");
			sb.append(String.format("<td style=\"font: 15px times new roman;\">%s </td>", mesto_rab));
			sb.append("</tr>");
			sb.append("</table>");
			sb.append("</td>");
			sb.append("<td style=\"border-top: 1px solid white; border-bottom: 1px solid white; border-left: 1px solid white; border-right: none; padding: 5px;\">");
			sb.append("<h3 style=\"text-align:center\">");
			sb.append("16. Перемена адреса и места работы");
			sb.append("</h3>");
			sb.append("<table border=\"1\" style=\"border-collapse: collapse;\">");		
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<th style=\"font: 15px times new roman;\">Дата</th>");
			sb.append("<th style=\"font: 15px times new roman;\">Новый адрес (новое место работы)</th>");
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("</table>");
			sb.append("<h3 style=\"text-align:center\">");
			sb.append("17.  ЗАБОЛЕВАНИЯ, ПОДЛЕЖАЩИЕ ДИСПАНСЕРНОМУ НАБЛЮДЕНИЮ");
			sb.append("</h3>");
			sb.append("<table border=\"1\" cellspacing=\"1\" bgcolor=\"#000000\">");		
			sb.append("<tr bgcolor=\"white\" style=\"border-top: 1px solid black; border-bottom: 1px solid black; border-left: 1px solid black; border-right: 1px solid black; padding: 5px;\">");
			sb.append("<th style=\"font: 15px times new roman;\">№ п/п</th>");
			sb.append("<th style=\"font: 15px times new roman;\">Наименование заболевания</th>");
			sb.append("<th style=\"font: 15px times new roman;\">Дата постановки на д/у</th>");
			sb.append("<th style=\"font: 15px times new roman;\">Должность подпись врача</th>");
			sb.append("<th style=\"font: 15px times new roman;\">Дата снятия с д/у</th>");
			sb.append("<th style=\"font: 15px times new roman;\">Должность подпись врача</th>");
			sb.append("</tr>");
			acrs.close();
			acrs = sse.execPreparedQuery("select p_disp.diag, n_c00.name, p_disp.d_vz, n_s00.name, p_disp.dataish from p_disp inner join n_c00 on (p_disp.diag=n_c00.pcod) " +
										 "inner join n_s00 on (p_disp.cdol_ot=n_s00.pcod) " +
										 "where p_disp.npasp = ? and p_disp.pcod = ? ", npasp, cpol);
					while (acrs.getResultSet().next()) {
						do {
							sb.append(String.format("<tr bgcolor=\"white\"><th style=\"font: 15px times new roman;\"> </th><th style=\"font: 15px times new roman;\"> " +
									"%s %s </th><th style=\"font: 15px times new roman;\"> " +
									"%3$td.%3$tm.%3$tY </th><th style=\"font: 15px times new roman;\"> " +
									"%s </th><th style=\"font: 15px times new roman;\">  " +
									"</th><th style=\"font: 15px times new roman;\"> " +
									" </th></tr>", 
									acrs.getResultSet().getString(1), acrs.getResultSet().getString(2), 
									acrs.getResultSet().getDate(3),
									acrs.getResultSet().getString(4)
									));
						} 
						while (acrs.getResultSet().next());
						}
			
			sb.append("</table>");
			acrs.close();
			acrs = sse.execPreparedQuery("select numstr, vybor, comment "+
			"from p_anamnez "+
			"where npasp=? and numstr=180 ", npasp);
			if (acrs.getResultSet().next())
				if (acrs.getResultSet().getString(3)!=null)
					sb.append(String.format("<br>18. ГРУППА КРОВИ %s ", acrs.getResultSet().getString(3)));
				else sb.append("<br>18. ГРУППА КРОВИ_______ ");
			
			acrs.close();
			acrs = sse.execPreparedQuery("select numstr, vybor, comment "+
										"from p_anamnez "+
										"where npasp=? and numstr=181 ", npasp);
			if (acrs.getResultSet().next())
				if (acrs.getResultSet().getString(3)!=null)
					sb.append(String.format(" Rh %s ", acrs.getResultSet().getString(3)));
				else sb.append(" Rh__________");
			acrs.close();
			acrs = sse.execPreparedQuery("select n_anz.name, vybor, comment "+
										"from  p_anamnez left join n_anz on (p_anamnez.numstr=n_anz.nstr) "+
										"where " +
										"npasp=? and (p_anamnez.numstr=11 or p_anamnez.numstr=12 or p_anamnez.numstr=13 or p_anamnez.numstr=14 or p_anamnez.numstr=15 or p_anamnez.numstr=16) ", npasp);
					sb.append("<br>19. ЛЕКАРСТВЕННАЯ НЕПЕРЕНОСИМОСТЬ :");
					while (acrs.getResultSet().next()) {
						if ((acrs.getResultSet().getBoolean(2)==true) && (acrs.getResultSet().getString(3)!=null))
							do {
								sb.append(String.format("<br> %s", acrs.getResultSet().getString(1), acrs.getResultSet().getString(3)));
							} 
						while (acrs.getResultSet().next());
						}
					
			sb.append("<br>");
			sb.append("<h3 align=center>ПРОВЕДЕНИЕ ПРОФИЛАКТИЧЕСКИХ ПРИВИВОК");
			sb.append("</h3>");
			sb.append("<table border=\"1\" style=\"border-collapse: collapse;\">	");	
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<th style=\"font: 15px times new roman;\">Дата</th>");
			sb.append("<th style=\"font: 15px times new roman;\">Наименование прививки</th>");
			sb.append("<th style=\"font: 15px times new roman;\">Вакцина</th>");
			sb.append("<th style=\"font: 15px times new roman;\">Серия</th>");
			sb.append("<th style=\"font: 15px times new roman;\">Доза</th>");
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("<td></td>");
			sb.append("</tr>");
			sb.append("<tr bgcolor=\"white\">");
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
			sb.append("</font");
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
