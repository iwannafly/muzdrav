package ru.nkz.ivcgzo.serverOutputInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Spring;

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
import ru.nkz.ivcgzo.thriftOutputInfo.InputPlanDisp;
import ru.nkz.ivcgzo.thriftOutputInfo.InputSvodVed;
import ru.nkz.ivcgzo.thriftOutputInfo.ThriftOutputInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.ThriftOutputInfo.Iface;
import ru.nkz.ivcgzo.thriftOutputInfo.VINotFoundException;
import ru.nkz.ivcgzo.thriftOutputInfo.VTDuplException;
import ru.nkz.ivcgzo.thriftOutputInfo.VTException;
import ru.nkz.ivcgzo.thriftOutputInfo.VrachInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.VrachTabel;
//import ru.nkz.ivcgzo.thriftOutputInfo.Input_info;


public class OutputInfo extends Server implements Iface {
	
	private static Logger log = Logger.getLogger(OutputInfo.class.getName());

	private TResultSetMapper<VrachInfo, VrachInfo._Fields> tableVrachInfo;
	private static Class<?>[] VrachInfoTypes;
	private TResultSetMapper<VrachTabel, VrachTabel._Fields> tableVrachTabel;
	private static Class<?>[] VrachTabelTypes;
	
	private TServer thrServ;
	//public Input_info inputInfo;
	public int kolz1, kolz2, kolz3, kolz4, kolz5, kolz6, kolz7, kolz8, xind, perv;
	public String cdiag, kat;
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfo = new SimpleDateFormat("dd.MM.yyyy");
	SimpleDateFormat sdfy = new SimpleDateFormat("yyyy");
	// Текущая дата в формате 12.12.2012
	String curDat = sdfo.format(java.util.Calendar.getInstance().getTime());
	
	

	public OutputInfo(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		// TODO Auto-generated constructor stub
		
		//Таблица VrachInfo
		tableVrachInfo = new TResultSetMapper<>(VrachInfo.class, "pcod","fam","im","ot","cdol");
		VrachInfoTypes = new Class<?>[]{Integer.class,String.class,String.class,String.class,String.class};
		
		//Таблица VrachTabel
		tableVrachTabel = new TResultSetMapper<>(VrachTabel.class, "pcod","cdol","datav","timep","timed","timeda","timeprf","timepr","nuch1","nuch2","nuch3","id");
		VrachTabelTypes = new Class<?>[]{Integer.class,String.class,Date.class,Double.class,Double.class,Double.class, Double.class, Double.class, String.class, String.class,String.class,Integer.class};
	}
	


	/**
	@SuppressWarnings("deprecation")
	public void Vimotchik025() throws TException, ParseException {
		//String d1 = InputSvodVed._Fields.DATEB.toString();
		//String d2 = InputSvodVed._Fields.DATEF.toString();
		//String namepol = InputSvodVed._Fields.NAMEPOL.toString();
		// Предыдущий год
		String yaerr = String.valueOf((sdf.parse(datef).getYear()-1));
		// Текущий год по периоду
		String yaetr = String.valueOf((sdf.parse(datef).getYear()));
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
							try (AutoCloseableResultSet scount = sse.execPreparedQuery("select count (*) from p_visit_amb where npasp= ? and id_diag_amb= ? and datap < ? order by datap", xind, spat.getResultSet().getInt("id_diag_amb"), d2)) {
								while (scount.getResultSet().next()) {
									if (scount.getResultSet().getInt(0)==1) {
										if ((scount.getResultSet().getDate("datad").after(kpo)) && (spat.getResultSet().getDate("datad").before(kpg))) perv=1; else perv=2;
									} else {
										try (AutoCloseableResultSet sdatap = sse.execPreparedQuery("select datap from p_visit_amb where npasp= ? and id_diag_amb= ? and datap < ? order by datap", xind, spat.getResultSet().getInt("id_diag_amb"), d2)) {
											while (scount.getResultSet().next()) {
												if (spat.getResultSet().getString("datap").equals(sdatap.getResultSet().getString(0))) {
													if ((scount.getResultSet().getDate("datad").after(kpo)) && (spat.getResultSet().getDate("datad").before(kpg))) perv=1; else perv=2;
												} else perv=3;
											}	}
									}
							}
						}
						
					}
					}}
			}
		} catch (SQLException e) {
		throw new TException(e);
		}
		
	}	*/
	

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

	
	
	public String printSvodVed(InputAuthInfo iaf, InputSvodVed isv) throws KmiacServerException, TException {
		// TODO Auto-generated method stub
		String path = null;
		String bokl = null;
		// Считывает входные значения с формы
		String dateb = isv.getDateb();
		String datef = isv.getDatef();
		int vozcat = isv.getVozcat();
		AutoCloseableResultSet bok = null;
		//AutoCloseableResultSet spat = null;
		List<IntegerClassifier> patList = new ArrayList<IntegerClassifier>();
		
		// Первичность
		int perv = 0;
		// Предыдущий год
		String yaerr = null;
		// Текущий год
		String yaetr = null;
		// Конец закрытия предыдущего отчетного периода
		java.util.Date kpo = null;
		// Конец года по периоду
		//Date kpg = null;
		// Текущая дата
		Date curDate = new java.sql.Date(System.currentTimeMillis());
		// Выходные графы
		
		String graph1 = null,graph2 = null;
		int graph3 = 0,graph4 = 0,graph5 = 0,graph6 = 0,graph7 = 0;
		

		java.util.Date kpg = null;

		int kolz = 0;
		
		//Запросы\
		final String sqlQuerySpat = "select a.id,a.npasp,a.dataz,a.ishod,a.cpol,b.datar,c.id_obr,c.diag,c.datad,d.xzab,d.disp " +
				"from p_vizit a, patient b, p_diag_amb c, p_diag d " +
				"where a.npasp=b.npasp and a.id=c.id_obr and c.id=d.id_diag_amb and c.predv!=true " +
				"and a.dataz between ?::date and ?::date order by a.npasp,c.diag,a.dataz";
		/**final String sqlQuerySpat = "select f.npasp,f.diag,f.datap,d.id_diag_amb,k.fam,k.im,k.ot," +
				"k.pol,k.jitel,k.cpol_pr,d.disp,f.cdol,k.adp_ul,k.adp_dom,k.adp_kv,k.mrab,k.poms_ndog," +
				"k.sgrp,f.opl,d.datad,d.xzab,f.cpos,f.dataz,d.datad,d.ishod from p_vizit_amb f," +
				"patient k,p_diag d where f.npasp = k.npasp and f.diag = d.diag and " +
				"f.dataz between ?::date and ?::date and substr(f.cdol,1,2)<>'33' and substr(f.cdol,1,2)<>'34' and" +
				" substr(f.cdol,1,3)<>'142' and substr(f.cdol,1,3)<>'143' and substr(f.cdol,1,3)<>'172' and " +
				"substr(f.cdol,1,3)<>'212' order by f.npasp,f.diag,f.datap desc,d.disp desc";
		*/
		//final String sqlQuerySpat = "select f.npasp,f.diag,f.datap,d.id_diag_amb,k.fam,k.im,k.ot,k.pol,k.jitel,k.cpol_pr,d.disp,f.cdol,k.adp_ul,k.adp_dom,k.adp_kv,k.mrab,k.poms_ndog,k.sgrp,f.opl,d.datad,d.xzab,f.cpos,f.dataz from p_vizit_amb f,patient k,p_diag d where f.npasp = k.npasp and f.diag = d.diag and f.dataz between'",datab,"'::date and '2012-12-31'::date and substr(f.cdol,1,2)<>'33' and substr(f.cdol,1,2)<>'34' and substr(f.cdol,1,3)<>'142' and substr(f.cdol,1,3)<>'143' and substr(f.cdol,1,3)<>'172' and substr(f.cdol,1,3)<>'212' order by f.npasp,f.diag,f.datap desc,d.disp desc";
		//final String sqlQuerySkat = "select lgot from p_kov";
		final String sqlQueryBok = "select name,diagsrpt from n_bz5";
		
		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("test", ".htm").getAbsolutePath()), "utf-8")) {
			//AutoCloseableResultSet acrs;
			//AutoCloseableResultSet bok;
			
			
						
			//spat = sse.execPreparedQuery("select f.npasp,f.diag,f.datap,d.id_diag_amb,k.fam,k.im,k.ot,k.pol,k.jitel,k.cpol_pr,d.disp,f.cdol,k.adp_ul,k.adp_dom,k.adp_kv,k.mrab,k.poms_ndog,k.sgrp,f.opl,d.datad,d.xzab,f.cpos,f.dataz from p_vizit_amb f,patient k,p_diag d where f.npasp = k.npasp and f.diag = d.diag and f.dataz between ? ::date and ? ::date and substr(f.cdol,1,2)<>'33' and substr(f.cdol,1,2)<>'34' and substr(f.cdol,1,3)<>'142' and substr(f.cdol,1,3)<>'143' and substr(f.cdol,1,3)<>'172' and substr(f.cdol,1,3)<>'212' order by f.npasp,f.diag,f.datap desc,d.disp desc", dateb, datef);
			
			// Код формы, н-р, BIPG14J - для взрослых
			String kodForm = null;
			// Тип сводки, н-р, У детей до 14-и лет
			String vozcatType = null;
			// Дата начала и конца периода в формате 12.12.2012
			String sdfoDateB = null, sdfoDateF = null;
			
			

			try {
				bok = sse.execPreparedQuery(sqlQueryBok);
				//spat = sse.execPreparedQuery(sqlQuerySpat ,dateb, datef);
			} catch (SqlExecutorException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		
			
			// Преобразование переменных
			
				try {
					sdfoDateB = sdfo.format(sdf.parse(dateb));
					sdfoDateF = sdfo.format(sdf.parse(datef));
					// Предыдущий год
					yaetr = sdfy.format(sdf.parse(datef));
					
					yaerr = String.valueOf(Integer.parseInt(yaetr)-1);
					// Текущий год по периоду
					//String yaetr = String.valueOf((sdf.parse(datef).getYear()));
					// Конец отчетного периода
					kpo = sdf.parse(yaerr+"-12-25");
					// Конец года по периоду
					kpg = sdf.parse(yaetr+"-12-31"); 
				} catch (ParseException e) {
				//	// TODO Auto-generated catch block
					e.printStackTrace();
				}				
			
			switch ( vozcat ) 
			{ 
			case 1:
					kodForm = "BIPG44J";
					vozcatType = "у детей до 14 лет";
					break; 
			case 2: 
					kodForm = "BIPG34J";
					vozcatType = "у подростков (с 15 до 18 лет)";
					break; 
			case 3: 
					kodForm = "BIPG14J";
					vozcatType = "у взрослых";
					break; 
			//default: 
			}
			
			StringBuilder sb = new StringBuilder(0x10000);
			// Шапка сводки
			sb.append(String.format("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">"));
			sb.append(String.format("<HTML>"));
			sb.append(String.format("<HEAD>"));
			sb.append(String.format("	<META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\"text/html; charset=utf-8\">"));
			sb.append(String.format("	<TITLE></TITLE>"));
			sb.append(String.format("	<META NAME=\"GENERATOR\" CONTENT=\"LibreOffice 3.5  (Linux)\">"));
			sb.append(String.format("	<META NAME=\"CREATED\" CONTENT=\"20121007;16130900\">"));
			sb.append(String.format("	<META NAME=\"CHANGED\" CONTENT=\"20121007;16403000\">"));
			sb.append(String.format("	<STYLE TYPE=\"text/css\">"));
			sb.append(String.format("	<!--"));
			sb.append(String.format("		@page { size: 21cm 29.7cm; margin-left: 2cm; margin-right: 1cm; margin-top: 1cm; margin-bottom: 1cm }"));
			sb.append(String.format("		P { margin-bottom: 0.21cm; color: #000000; font-family: \"Linux Libertine\"; font-size: 10pt }"));
			sb.append(String.format("		TD P { color: #000000; font-family: \"Linux Libertine\"; font-size: 10pt }"));
			sb.append(String.format("		A:link { color: #000080; so-language: zxx; text-decoration: underline }"));
			sb.append(String.format("		A:visited { color: #800000; so-language: zxx; text-decoration: underline }"));
			sb.append(String.format("	-->"));
			sb.append(String.format("	</STYLE>"));
			sb.append(String.format("</HEAD>"));
			sb.append(String.format("<BODY LANG=\"ru-RU\" TEXT=\"#000000\" LINK=\"#000080\" VLINK=\"#800000\" DIR=\"LTR\">"));
			// Код формы
			sb.append(String.format("<p align=right>код формы: %s", kodForm));
			// Текущая дата
			sb.append(String.format("<p align=right>от %s", curDat));
			// Заголовок
			sb.append(String.format("<p align=center> <b>Сводная ведомость учета зарегистрированных заболеваний %s", vozcatType));
			sb.append(String.format("<p align=center> <b>За период с %s по %s", sdfoDateB, sdfoDateF));
			sb.append(String.format("<p align=center><b>%s %s",iaf.clpu_name,iaf.cpodr_name));
			
			// Формирование таблицы
			
			sb.append(String.format("<TABLE WIDTH=624 CELLPADDING=2 CELLSPACING=0>"));
			sb.append(String.format("	<COL WIDTH=165>"));
			sb.append(String.format("	<COL WIDTH=64>"));
			sb.append(String.format("	<COL WIDTH=59>"));
			sb.append(String.format("	<COL WIDTH=75>"));
			sb.append(String.format("	<COL WIDTH=69>"));
			sb.append(String.format("	<COL WIDTH=68>"));
			sb.append(String.format("	<COL WIDTH=94>"));
			sb.append(String.format("	<TR VALIGN=TOP>"));
			sb.append(String.format("		<TD ROWSPAN=3 WIDTH=165 HEIGHT=26 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">Наименование"));
			sb.append(String.format("			классов и отдельных болезней</FONT></FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD ROWSPAN=3 WIDTH=64 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">Шифр"));
			sb.append(String.format("			по МКБ X пересмотра</FONT></FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD COLSPAN=4 WIDTH=283 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">Зарегистрировано"));
			sb.append(String.format("			больных с данным заболеванием</FONT></FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD ROWSPAN=3 WIDTH=94 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=3 STYLE=\"font-size: 11pt\">Состоит"));
			sb.append(String.format("			под диспансерным наблюдением на конец"));
			sb.append(String.format("			отчетного периода</FONT></FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("	</TR>"));
			sb.append(String.format("	<TR VALIGN=TOP>"));
			sb.append(String.format("		<TD ROWSPAN=2 WIDTH=59 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">Всего</FONT></FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD ROWSPAN=2 WIDTH=75 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">в"));
			sb.append(String.format("			том числе детей до 1 года</FONT></FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD COLSPAN=2 WIDTH=141 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">в"));
			sb.append(String.format("			том числе с диагнозами, установленными"));
			sb.append(String.format("			впервые в жизни</FONT></FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("	</TR>"));
			sb.append(String.format("	<TR VALIGN=TOP>"));
			sb.append(String.format("		<TD WIDTH=69 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">Всего</FONT></FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=68 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">в"));
			sb.append(String.format("			том числе детей до 1 года</FONT></FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("	</TR>"));
			sb.append(String.format("	<TR VALIGN=TOP>"));
			sb.append(String.format("		<TD WIDTH=165 HEIGHT=17 STYLE=\"border: 1px solid #000000; padding: 0.05cm\" SDVAL=\"1\" SDNUM=\"1049;\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">1</FONT></FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=64 STYLE=\"border: 1px solid #000000; padding: 0.05cm\" SDVAL=\"2\" SDNUM=\"1049;\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">2</FONT></FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=59 STYLE=\"border: 1px solid #000000; padding: 0.05cm\" SDVAL=\"3\" SDNUM=\"1049;\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">3</FONT></FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=75 STYLE=\"border: 1px solid #000000; padding: 0.05cm\" SDVAL=\"4\" SDNUM=\"1049;\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">4</FONT></FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=69 STYLE=\"border: 1px solid #000000; padding: 0.05cm\" SDVAL=\"5\" SDNUM=\"1049;\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">5</FONT></FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=68 STYLE=\"border: 1px solid #000000; padding: 0.05cm\" SDVAL=\"6\" SDNUM=\"1049;\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">6</FONT></FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=94 STYLE=\"border: 1px solid #000000; padding: 0.05cm\" SDVAL=\"7\" SDNUM=\"1049;\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">7</FONT></FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("	</TR>"));
			
						// Расчет значений
			while (bok.getResultSet().next()) {
				String namebok = bok.getResultSet().getString("name");
				String diagDiap = bok.getResultSet().getString("diagsrpt");
				graph3=0;
				graph4=0;
				graph5=0;
				graph6=0;
				graph7=0;
				graph1=namebok;
				graph2=diagDiap;
							
				try {
					AutoCloseableResultSet spat = sse.execPreparedQuery(sqlQuerySpat ,dateb, datef);
				
					while (spat.getResultSet().next()) {
						String xdiag = spat.getResultSet().getString("diag");
						//String xdatar = spat.getResultSet().getString("datar");
						int xpasp = spat.getResultSet().getInt("npasp");
						int xishod = spat.getResultSet().getInt("ishod");
						int xxzab = spat.getResultSet().getInt("xzab");
						int xdisp = spat.getResultSet().getInt("disp");
						Date xdatad = spat.getResultSet().getDate("datad");
						Date xdatar = spat.getResultSet().getDate("datar");
						if (xdiag.trim().charAt(0)!='Z' && xishod!=0) {
							// Проверка на первичность
							if (xxzab==1) perv=1;
							else if (xdatad.after(kpo) && xdatad.before(kpg)) perv=1;
							else perv=2;
							
							if (isIncludesDiag(xdiag,diagDiap)) {
								if (perv==1) {
									//Зарегестрировано всего
									graph3++;
									//В том числе с диагнозами, установленными впервые в жизни
									graph5++;
									// В том числе детей до 1 года
									if (getYearDiff(xdatad, xdatar)<1) { graph4++; graph6++;}
									// Состоит под диспансерным наблюдением
									if (xdisp==1) graph7++;
								}   // Если первичность равна 2, то больной учитывается с одним заболеванием только один раз
									else if (!patList.contains(new IntegerClassifier(xpasp,xdiag))) {
										patList.add(new IntegerClassifier(xpasp,xdiag));
										// Зарегестрировано всего
										graph3++;
										// В том числе детей до 1 года, всего
										if (getYearDiff(curDate, xdatar)<1) graph4++;
										
										
								}
							}
							
							}
					}
				} catch (SqlExecutorException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				//perv++;
				System.out.println(namebok+" "+diagDiap+" "+graph3);
				//spat.getResultSet().beforeFirst();
				
						// Считаем значения
					
						//try (AutoCloseableResultSet bok = sse.execPreparedQuery(sqlQueryBok))
						//{
						
							
						
						/**while (bok.getResultSet().next()) {
							String namebok = bok.getResultSet().getString("name");
							String diagDiap = bok.getResultSet().getString("diagsrpt");
							if (isIncludesDiag(xdiag,diagDiap)) {
								if (getYearDiff(curDate, xdatar)<15) {
									graph1=namebok;
									graph2=diagDiap;
									if (perv==1) {
										
										graph3++;
										graph5++;
										if (xdisp==1) graph7++;
									} else if (!patList.contains(new IntegerClassifier(xpasp,xdiag))) {
										patList.add(new IntegerClassifier(xpasp,xdiag));
										graph3++;
										if (xdisp==1) graph7++;
										}
								} else if (getYearDiff(curDate, xdatar)<1) {
									graph1=namebok;
									graph2=diagDiap;
									if (perv==1) {
										
										graph4++;
										graph6++;
									} else if (!patList.contains(new IntegerClassifier(xpasp,xdiag))) {
										patList.add(new IntegerClassifier(xpasp,xdiag));
										graph4++;
									}
								}
							} */
							if (graph3!=0 || graph4!=0 || graph5!=0 || graph6!=0 || graph7 !=0)
							{
								sb.append(String.format("	<TR VALIGN=TOP>"));
								sb.append(String.format("		<TD WIDTH=165 HEIGHT=17 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
								sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">%s</FONT></FONT></P>", graph1 ));
								sb.append(String.format("		</TD>"));
								sb.append(String.format("		<TD WIDTH=64 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
								sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">%s</FONT></FONT></P>", graph2));
								sb.append(String.format("		</TD>"));
								sb.append(String.format("		<TD WIDTH=59 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
								sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">%s</FONT></FONT></P>", graph3));
								sb.append(String.format("		</TD>"));
								sb.append(String.format("		<TD WIDTH=75 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
								sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">%s</FONT></FONT></P>", graph4));
								sb.append(String.format("		</TD>"));
								sb.append(String.format("		<TD WIDTH=69 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
								sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">%s</FONT></FONT></P>", graph5));
								sb.append(String.format("		</TD>"));
								sb.append(String.format("		<TD WIDTH=68 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
								sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">%s</FONT></FONT></P>", graph6));
								sb.append(String.format("		</TD>"));
								sb.append(String.format("		<TD WIDTH=94 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
								sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">%s</FONT></FONT></P>", graph7));
								sb.append(String.format("		</TD>"));
								sb.append(String.format("	</TR>"));
							}
						} // Подстановка значений в таблицу

					//}
				
				//}
				
				/** Попытка порта старого кода
				while (spat.getResultSet().next()) {
					if (spat.getResultSet().getString("diag").trim().charAt(0)!='Z' && spat.getResultSet().getInt("ishod")!=0) {
					//if (spat.getResultSet().getString("diag").trim().charAt(0)!='Z') {
						
						xind = spat.getResultSet().getInt("npasp");
						cdiag = spat.getResultSet().getString("diag");
						int ndiag = spat.getResultSet().getInt("id_diag_amb");
						//System.out.println(cdiag);
						// Здесь должна быть проверка, связанная с таблицей PBOL
						// Здесь должна быть проверка, связанная со льготами
						bok = sse.execPreparedQuery(sqlQueryBok);
						bok.getResultSet().next();
						
						bokl = bok.getResultSet().getString("name");
						
						try (AutoCloseableResultSet skat = sse.execPreparedQuery(sqlQuerySkat))
						{
							while (skat.getResultSet().next()) {
								kat = skat.getResultSet().getString("lgot");
							}
							
						} 	catch (SQLException e) {
							((SQLException) e.getCause()).printStackTrace();
							throw new KmiacServerException();
						}
						// Проверка на условие первичности посещения
						if (spat.getResultSet().getInt("xzab")==1) perv=1;
						else if (spat.getResultSet().getDate("dataz").after(kpo) && spat.getResultSet().getDate("dataz").before(kpg)) {perv=4;
						// Добавить проверку на ishod<>0 и ndiag
						try (AutoCloseableResultSet arcs = sse.execPreparedQuery("select count(*) from p_vizit_amb f,p_diag_amb d, p_diag b where " +
								"f.id_obr=d.id_obr and b.id_diag_amb=d.id and f.npasp=? and b.id_diag_amb=? and " +
								"f.datap between '2012-01-01'::date and '2012-12-31'::date", xind, ndiag))
							{ 
								while (arcs.getResultSet().next()) {
									if (arcs.getResultSet().getInt(0)==1) { perv=5;
										//if (spat.getResultSet().getDate("datad").after(kpo) && spat.getResultSet().getDate("datad").before(kpg)) perv=1; else perv=2;
									} else {
											try (AutoCloseableResultSet arcss = sse.execPreparedQuery("select datep from p_vizit_amb where npasp= ? and datap between '2012-01-01'::date and '2012-12-31'::date order by datap", xind, ndiag, kpo, datef))
										{
											while (arcss.getResu ltSet().next()) {
												if (spat.getResultSet().getString("datad").equals(arcss.getResultSet().getString(0))) {
													if (spat.getResultSet().getDate("datad").after(kpo) && spat.getResultSet().getDate("datad").before(kpg)) perv=1; else perv=2;
												} else perv=3;
											}
										} catch (SQLException e) {
											((SQLException) e.getCause()).printStackTrace();
											throw new KmiacServerException();
										}
										
																			
									
								}
							} catch (SQLException e) {
								((SQLException) e.getCause()).printStackTrace();
								throw new KmiacServerException();
							}
						} else perv=3;
					} else perv=3; 
					System.out.println(spat.getResultSet().getString("npasp"));
					System.out.println(String.valueOf(perv));	
					//sb.append(String.format("<P><BR>%s<BR>", kolz));
				}*/

			//System.out.println(graph1);
			//System.out.println(graph2);
			//System.out.println(String.valueOf(graph3));
			//System.out.println(String.valueOf(graph4));
			//System.out.println(String.valueOf(graph5));
			//System.out.println(String.valueOf(graph6));
			//System.out.println(String.valueOf(graph7));
					
			

			
			// Подвал документа
			sb.append(String.format("</TABLE>"));
			
			sb.append(String.format("<P><BR>%s<BR>", yaerr));
			sb.append(String.format("</P>"));
			sb.append(String.format("</BODY>"));
			sb.append(String.format("</HTML>"));
			//sb.append(String.format("<p>%s", kolz));
				//try
				
			

			
			
			osw.write(sb.toString());
			//return path;

	
		
		} catch (IOException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		//} catch (ParseException e) {
			// TODO Auto-generated catch block
		//	e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return path;
	}

	@Override
	public String printPlanDisp(InputPlanDisp ipd)
			throws KmiacServerException, TException {
		
		String svod = null;
		
		// Дата от ...
		String dn = ipd.getDaten();
		// Дата до ...
		String dk = ipd.getDatek();
		// Код полеклиники
		int kodpol = ipd.getKpolik();
		// Наименование полеклиники
		String namepol = ipd.getNamepol();
		// № участка
		String uc = ipd.getUchas(); 
		
		// Код ЛПУ
		int kodlpu = ipd.getClpu();
		
		int poldv = 0;
		
		final String sqlQueryDetVzPol = "select c_nom from n_m00 where pcod ="+String.valueOf(kodlpu);
		
		try (AutoCloseableResultSet zapznach = sse.execPreparedQuery(sqlQueryDetVzPol)) {
			
			poldv = zapznach.getResultSet().getInt("c_nom");
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		String sqlQueryPlanDis = "select pn.nambk, (p.fam||' '||p.im||' '||p.ot) as fio, p.datar, p.adm_ul,p.adm_dom,p.adm_korp," +
				"p.adm_kv,	pm.diag, na.name, pm.pdat, pn.nuch, pd.d_grup, pm.pdat, pd.d_uch, pm.cod_sp, pm.cpol,pm.fdat, pd.ishod " +
				"from patient p join p_nambk pn on(p.npasp = pn.npasp) join p_mer pm on(p.npasp =pm.npasp) " +
				"join p_disp pd on(p.npasp = pd.npasp) join n_abd na on(pm.pmer = na.pcod) " +
				"where (pm.pdat between "+ dn+" and "+ dk+")and(pd.diag = pm.diag)and(pm.fdat is null)and(pd.ishod is null) and(pn.dataot is null)" +
						"and(pm.cpol = "+kodpol+")";
		if (uc !=null) sqlQueryPlanDis = sqlQueryPlanDis+"and(pd.d_uch ="+uc+")";
		if (poldv == 1){
			sqlQueryPlanDis = sqlQueryPlanDis + "Order by pm.cod_sp, pd.d_uch, pm.cpol, p.fam, p.im, p.ot, p.datar";
			}else{
				sqlQueryPlanDis = sqlQueryPlanDis + "Order by pd.d_uch, pm.cpol, p.fam, p.im, p.ot, p.datar";
			
		}
		
		
		
		
		try (AutoCloseableResultSet spat = sse.execPreparedQuery(sqlQueryPlanDis)) {
			
		
			try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(svod = File.createTempFile("test", ".htm").getAbsolutePath()), "utf-8")) {
			
			
			StringBuilder sb = new StringBuilder(0x10000);
		
			sb.append(String.format("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">"));
			sb.append(String.format("<HTML>"));
			sb.append(String.format("<HEAD>"));
			sb.append(String.format("	<META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\"text/html; charset=utf-8\">"));
			sb.append(String.format("	<TITLE>План диспансерного обслуживания</TITLE>"));
			sb.append(String.format("	<META NAME=\"GENERATOR\" CONTENT=\"LibreOffice 3.5  (Linux)\">"));
			sb.append(String.format("	<META NAME=\"CREATED\" CONTENT=\"20121010;10560000\">"));
			sb.append(String.format("	<META NAME=\"CHANGED\" CONTENT=\"20121010;10560000\">"));
			sb.append(String.format("	<STYLE TYPE=\"text/css\">"));
			sb.append(String.format("	<!--"));
			sb.append(String.format("		@page { size: 29.7cm 21cm; margin-right: 1.06cm; margin-top: 3cm; margin-bottom: 1.5cm }"));
			sb.append(String.format("		P { margin-bottom: 0.21cm; direction: ltr; color: #000000; widows: 2; orphans: 2 }"));
			sb.append(String.format("		P.western { font-family: \"Times New Roman\", serif; font-size: 12pt; so-language: ru-RU }"));
			sb.append(String.format("		P.cjk { font-family: \"Times New Roman\", serif; font-size: 12pt }"));
			sb.append(String.format("		P.ctl { font-family: \"Times New Roman\", serif; font-size: 12pt; so-language: ar-SA }"));
			sb.append(String.format("	-->"));
			sb.append(String.format("	</STYLE>"));
			sb.append(String.format("</HEAD>"));
			sb.append(String.format("<BODY LANG=\"ru-RU\" TEXT=\"#000000\" LINK=\"#000080\" VLINK=\"#800000\" DIR=\"LTR\">"));
		
			//spat.getResultSet().first();
			
			int spuch = 0;
			int spuch1 = 0;
			

			
			sb.append(String.format(ZagShap(dn,dk,namepol,spuch)));
			String adres = null; 
			
			while (spat.getResultSet().next()){

				if (spat.getResultSet().first()){
					if (poldv != 2){
						spuch = Integer.parseInt(spat.getResultSet().getString("cod_sp")+"0"+ spat.getResultSet().getString("d_uch"));
						spuch1 = Integer.parseInt(spat.getResultSet().getString("cod_sp")+"0"+ spat.getResultSet().getString("d_uch"));
					}else{
						spuch = Integer.parseInt(spat.getResultSet().getString("d_uch"));
						spuch1 = Integer.parseInt(spat.getResultSet().getString("d_uch"));
					
					}
				}
				
				if (poldv == 1){
					spuch1 = Integer.parseInt(spat.getResultSet().getString("cod_sp")+"0"+ spat.getResultSet().getString("d_uch"));
				}else{
				
					spuch1 = Integer.parseInt(spat.getResultSet().getString("d_uch"));
				}
				
				adres = spat.getResultSet().getString("adm_ul")+" "+spat.getResultSet().getString("adm_dom")+" "+spat.getResultSet().getString("adm_korp")+" "+spat.getResultSet().getString("adm_kv");
				if(spuch != spuch1){
					spuch = spuch1;
					sb.append(String.format("</TABLE>"));
					sb.append(String.format("<p></p><p></p><p></p><p></p><p></p>"));
					sb.append(String.format(ZagShap(dn,dk,namepol,spuch)));
					
				}
				
				// Данные в таблице
				sb.append(String.format("	<TR VALIGN=TOP>"));
				sb.append(String.format("		<TD WIDTH=52 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",spat.getResultSet().getString("nambk")));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=105 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",spat.getResultSet().getString("fio")));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=59 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",spat.getResultSet().getString("datar")));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=105 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>", adres));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=59 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>", spat.getResultSet().getString("diag")));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=174 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",spat.getResultSet().getString("name")));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=105 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>", spat.getResultSet().getString("pdat")));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=62 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",spat.getResultSet().getString("nuch")));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=105 STYLE=\"border: 1px solid #000000; padding: 0cm 0.19cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",spat.getResultSet().getString("d_grup")));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("	</TR>"));
			}
			
			sb.append(String.format("</TABLE>"));
			sb.append(String.format("<P ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("</BODY>"));
			sb.append(String.format("</HTML>"));
			
			osw.write(sb.toString());
			
		} catch (IOException e) {
			e.printStackTrace();
			throw new KmiacServerException();
			
		}	
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return svod;
	}

	@Override
	public List<VrachInfo> getVrachTableInfo(int cpodr) throws VINotFoundException,
			KmiacServerException, TException {

		String sqlQuery = "SELECT a.pcod, a.fam, a.im, a.ot, b.cdol " 
						+ "FROM s_vrach a, s_mrab b WHERE cpodr=?";
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cpodr)) {
			ResultSet rs = acrs.getResultSet();
			List<VrachInfo> VrachInfo = tableVrachInfo.mapToList(rs);
			if (VrachInfo.size() > 0) {
				return VrachInfo;				
			} else {
				throw new VINotFoundException();
			}
		} catch (SQLException e) {
			log.log(Level.ERROR, "SQL Exception: ", e);
			throw new KmiacServerException();
		}
	}
	
	@Override
	public List<VrachTabel> getVrachTabel(int pcod) throws VTDuplException,
			KmiacServerException, TException {
		
		String sqlQuery = "SELECT pcod, cdol, datav, timep, timed, timeda, timeprf, " 
						+ "timepr, nuch1, nuch2, nuch3, id FROM s_tabel WHERE pcod=?;";
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, pcod)) {
			ResultSet rs = acrs.getResultSet();
			List<VrachTabel> VrachTabel = tableVrachTabel.mapToList(rs);
			if (VrachTabel.size() > 0) {
				return VrachTabel;				
			} else {
				throw new VTDuplException();
			}
		} catch (SQLException e) {
			log.log(Level.ERROR, "SQL Exception: ", e);
			throw new KmiacServerException();
		}
	}

	
	private String ZagShap(String d1, String d2, String npol, int uchas){
	
	String shap = null;
	
	StringBuilder sb = new StringBuilder(0x10000);
	
	sb.append(String.format("<P ALIGN=CENTER>План диспансерного обслуживания</P>"));
	sb.append(String.format("<P ALIGN=CENTER>за период с %s по %s </FONT></P>",d1,d2));
	sb.append(String.format("<P ALIGN=CENTER>%s</P>", npol));
	sb.append(String.format("<P ALIGN=CENTER>Участок № %s</P>",uchas));
	sb.append(String.format("<P ALIGN=CENTER><BR>"));
	sb.append(String.format("</P>"));

	

	// шапка таблицы
	sb.append(String.format("<TABLE WIDTH=953 CELLPADDING=7 CELLSPACING=0>"));
	sb.append(String.format("	<COL WIDTH=52>"));
	sb.append(String.format("	<COL WIDTH=105>"));
	sb.append(String.format("	<COL WIDTH=59>"));
	sb.append(String.format("	<COL WIDTH=105>"));
	sb.append(String.format("	<COL WIDTH=59>"));
	sb.append(String.format("	<COL WIDTH=174>"));
	sb.append(String.format("	<COL WIDTH=105>"));
	sb.append(String.format("	<COL WIDTH=62>"));
	sb.append(String.format("	<COL WIDTH=105>"));
	sb.append(String.format("	<TR VALIGN=TOP>"));
	sb.append(String.format("		<TD WIDTH=52 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
	sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">N"));
	sb.append(String.format("			амб.карты</FONT></P>"));
	sb.append(String.format("		</TD>"));
	sb.append(String.format("		<TD WIDTH=105 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
	sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Ф.И.О."));
	sb.append(String.format("			</FONT>"));
	sb.append(String.format("			</P>"));
	sb.append(String.format("		</TD>"));
	sb.append(String.format("		<TD WIDTH=59 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
	sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Дата"));
	sb.append(String.format("			рождения</FONT></P>"));
	sb.append(String.format("		</TD>"));
	sb.append(String.format("		<TD WIDTH=105 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
	sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Адрес</FONT></P>"));
	sb.append(String.format("		</TD>"));
	sb.append(String.format("		<TD WIDTH=59 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
	sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Повод"));
	sb.append(String.format("			дисп-ции</FONT></P>"));
	sb.append(String.format("		</TD>"));
	sb.append(String.format("		<TD WIDTH=174 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
	sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Планируемые"));
	sb.append(String.format("			мероприятия</FONT></P>"));
	sb.append(String.format("		</TD>"));
	sb.append(String.format("		<TD WIDTH=105 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
	sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Дата"));
	sb.append(String.format("			мероприятия</FONT></P>"));
	sb.append(String.format("		</TD>"));
	sb.append(String.format("		<TD WIDTH=62 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
	sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Тер.участок</FONT></P>"));
	sb.append(String.format("		</TD>"));
	sb.append(String.format("		<TD WIDTH=105 STYLE=\"border: 1px solid #000000; padding: 0cm 0.19cm\">"));
	sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Группа"));
	sb.append(String.format("			учета</FONT></P>"));
	sb.append(String.format("		</TD>"));
	sb.append(String.format("	</TR>"));
	
	return shap = sb.toString();
	}

//Удалить
public void deleteVT(int vt) throws TException {
	System.out.println(vt);
	try (SqlModifyExecutor sme = tse.startTransaction()) {
		sme.execPrepared("DELETE FROM s_tabel WHERE id = ?;", false, vt);
		sme.setCommit();
    } catch (SqlExecutorException | InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}


//Добавить
public int addVT(VrachTabel vt) throws VTException, VTDuplException,
		KmiacServerException, TException {
	int id = vt.getId();
	try (SqlModifyExecutor sme = tse.startTransaction()) {
		sme.execPreparedQuery("SELECT id FROM s_tabel WHERE id=?", id).getResultSet().next(); 
		int[] indexes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		sme.execPreparedT("INSERT INTO s_tabel (pcod, cdol, datav, timep, timed, timeda, " 
        				+ "timeprf, timepr, nuch1, nuch2, nuch3) " 
        				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
        				true, vt, VrachTabelTypes, indexes);
		id = sme.getGeneratedKeys().getInt("id");
		sme.setCommit();
		return id;			
	} catch (SQLException | InterruptedException e) {
		e.printStackTrace();
		throw new KmiacServerException("Ошибка сервера");
	}
}



//Изменить
public void updateVT(VrachTabel vt) throws KmiacServerException, TException {
	int[] indexes = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 0};
	try (SqlModifyExecutor sme = tse.startTransaction()) {
		sme.execPreparedT("UPDATE s_tabel SET cdol = ?, datav = ?, timep = ?, timed = ?, "
        				+ "timeda = ?, timeprf = ?, timepr = ?, nuch1 = ?, nuch2 = ?, " 
        				+ "nuch3 = ? WHERE pcod = ?;", true, vt, VrachTabelTypes, indexes);
		sme.setCommit();
	} catch (SqlExecutorException | InterruptedException e) {
		e.printStackTrace();
	}
}

@Override
public String printNoVipPlanDisp(InputPlanDisp ipd)
		throws KmiacServerException, TException {
	// TODO Auto-generated method stub
	return null;
}



@Override
public String printSvedDispObs(InputPlanDisp ipd) throws KmiacServerException,
		TException {
	String svod = null;
	
	// Дата от ...
	Date dn;
	// Дата до ...
	Date dk;
	try {
		dn = (Date) sdfo.parse(ipd.getDaten());
		dk = (Date) sdfo.parse(ipd.getDatek());

		// Код полеклиники
		int kodpol = ipd.getKpolik();
		
		// Код ЛПУ
		int kodlpu = ipd.getClpu();
		// Вид больницы (Д/В)
		int poldv = 0;
		
		final String sqlQueryDetVzPol = "select c_nom from n_m00 where pcod ="+String.valueOf(kodlpu);
		
		try (AutoCloseableResultSet zapznach = sse.execPreparedQuery(sqlQueryDetVzPol)) {
			
			poldv = zapznach.getResultSet().getInt("c_nom");
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		String sqlQueryObost =null;
		
		if (poldv ==2){
			sqlQueryObost = "select  count(p_obost.diag) as kol,p_disp.d_uch"+
			"from p_obost  join p_disp  on (p_obost.npasp=p_disp.npasp)"+
			"where (p_obost.diag=p_disp.diag) and (sl_obostr=1) and (p_disp.pcod="+String.valueOf(kodpol)+")and "+
			"(p_obost.dataz between "+dn+" and "+dk+")"+
			"group by p_disp.d_uch"+
			"order by p_disp.d_uch";
		}else{
			sqlQueryObost = "select  count(p_obost.diag) as kol,p_disp.cod_sp , p_disp.d_uch"+
			"from p_obost  join p_disp  on (p_obost.npasp=p_disp.npasp)"+
			"where (p_obost.diag=p_disp.diag) and (sl_obostr=1) and p_disp.pcod="+String.valueOf(kodpol)+")and"+
			"(p_obost.dataz between "+dn+" and "+dk+")"+
			"group by p_disp.cod_sp,p_disp.d_uch"+
			"order by p_disp.cod_sp,p_disp.d_uch";			
		}
		
		
		
		String [][] obos = null;
		try (AutoCloseableResultSet oslog = sse.execPreparedQuery(sqlQueryObost)) {
			
			//String [][] prom = new String [1][2]; 
			try {
				int i =0;
				while (oslog.getResultSet().next())i++;				
				
				obos = new String[i][0];
				
				int j=1;
				
				oslog.getResultSet().first();
				if (poldv ==2){
					obos [0][0] = oslog.getResultSet().getString("d_ush");
					obos [0][1] = oslog.getResultSet().getString("kol");
				}else{
					obos [0][0] = oslog.getResultSet().getString("cod_sp")+"-"+oslog.getResultSet().getString("d_ush");
					obos [0][1] = oslog.getResultSet().getString("kol");
				}
				
				while (oslog.getResultSet().next()){
					
					if (poldv ==2){
						obos [j][0] = oslog.getResultSet().getString("d_ush");
						obos [j][1] = oslog.getResultSet().getString("kol");
					}else{
						obos [j][0] = oslog.getResultSet().getString("cod_sp")+"-"+oslog.getResultSet().getString("d_ush");
						obos [j][1] = oslog.getResultSet().getString("kol");
					} 
							
					j++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (SqlExecutorException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// Наименование полеклиники
		String namepol = ipd.getNamepol();
		// № участка
		String uc = ipd.getUchas(); 
	
		final String sqlQuerySvedDis = "select pd.d_uch, pm.cod_sp, pm.pmer, p.npasp, pm.pdat, pm.fdat, pd.ishod, pn.dataot " +
			"from patient p join p_mer pm on(p.npasp = pm.npasp)"+
					"join p_nambk pn on(p.npasp =pn.npasp) join p_disp pd on(p.npasp = pd.npasp)"+
			"where ((pm.pdat between "+ dn+" and "+ dk+")or(pm.fdat between "+ dn+" and "+ dk+"))and(pd.diag = pm.diag)and(pd.ishod is null) and(pn.dataot is null)" +
					"and(pm.cpol = "+kodpol+")"+
			"Order by pm.cod_sp,pd.d_uch, pm.pmer";
	
		try (AutoCloseableResultSet spat = sse.execPreparedQuery(sqlQuerySvedDis)) {
		
			float [] mas = new float [26]; 
			float [] sum = new float [26];
			//spat.getResultSet().first();
				
			String nuch = null;
			String nuchn =null;
			boolean chek = false;
			
			try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(svod = File.createTempFile("test", ".htm").getAbsolutePath()), "utf-8")) {
			
			
				StringBuilder sb = new StringBuilder(0x10000);

			  sb.append(String.format("!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">"));
			  sb.append(String.format("<HTML>"));
			  sb.append(String.format("<HEAD>"));
			  sb.append(String.format("	<META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\"text/html; charset=windows-1251\">"));
			  sb.append(String.format("	<TITLE>Сведения о диспансерном обслуживании населения</TITLE>"));
			  sb.append(String.format("	<META NAME=\"GENERATOR\" CONTENT=\"LibreOffice 3.5  (Windows)\">"));
			  sb.append(String.format("	<META NAME=\"CREATED\" CONTENT=\"20121017;13540000\">"));
			  sb.append(String.format("	<META NAME=\"CHANGED\" CONTENT=\"20121102;14361071\">"));

			  sb.append(String.format("	<STYLE TYPE=\"text/css\">"));
			  sb.append(String.format("	<!--"));
			  sb.append(String.format("		@page { size: 29.7cm 21cm; margin-right: 0.35cm; margin-top: 3cm; margin-bottom: 1.5cm }"));
			  sb.append(String.format("		P { margin-bottom: 0.21cm; direction: ltr; color: #000000; widows: 2; orphans: 2 }"));
			  sb.append(String.format("		P.western { font-family: \"Times New Roman\", serif; font-size: 12pt; so-language: ru-RU }"));
			  sb.append(String.format("		P.cjk { font-family: \"Times New Roman\", serif; font-size: 12pt }"));
			  sb.append(String.format("		P.ctl { font-family: \"Times New Roman\", serif; font-size: 12pt; so-language: ar-SA }"));
			  sb.append(String.format("	-->"));
			  sb.append(String.format("	</STYLE>"));
			  sb.append(String.format("</HEAD>"));
			  sb.append(String.format("<BODY LANG=\"ru-RU\" TEXT=\"#000000\" LINK=\"#000080\" VLINK=\"#800000\" DIR=\"LTR\">"));
			  sb.append(String.format("<P STYLE=\"margin-bottom: 0cm\"><BR></P>"));
			  sb.append(String.format("<P ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><FONT SIZE=2 STYLE=\"font-size: 9pt\"><B>Сведения о диспансерном обслуживании населения</B></FONT></P>"));
			  sb.append(String.format("<P></P>"));
			  sb.append(String.format("<P ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><FONT SIZE=2 STYLE=\"font-size: 9pt\"><B>за период с %s по %s</B></FONT></P>",dn,dk));
			  sb.append(String.format("<P ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><FONT SIZE=2 STYLE=\"font-size: 9pt\"><B>Поликлиника прикрепления:  %s</B></FONT></P>",namepol));
			  sb.append(String.format("<P></P>"));
			  sb.append(String.format("<P></P>"));
			  sb.append(String.format("<TABLE WIDTH=1120 CELLPADDING=7 CELLSPACING=0>"));
			  sb.append(String.format("	<COL WIDTH=42>"));
			  sb.append(String.format("	<COL WIDTH=25>"));
			  sb.append(String.format("	<COL WIDTH=25>"));
			  sb.append(String.format("	<COL WIDTH=33>"));
			  sb.append(String.format("	<COL WIDTH=25>"));
			  sb.append(String.format("	<COL WIDTH=25>"));
			  sb.append(String.format("	<COL WIDTH=33>"));
			  sb.append(String.format("	<COL WIDTH=25>"));
			  sb.append(String.format("	<COL WIDTH=25>"));
			  sb.append(String.format("	<COL WIDTH=33>"));
			  sb.append(String.format("	<COL WIDTH=25>"));
			  sb.append(String.format("	<COL WIDTH=25>"));
			  sb.append(String.format("	<COL WIDTH=33>"));
			  sb.append(String.format("	<COL WIDTH=25>"));
			  sb.append(String.format("	<COL WIDTH=25>"));
			  sb.append(String.format("	<COL WIDTH=33>"));
			  sb.append(String.format("	<COL WIDTH=25>"));
			  sb.append(String.format("	<COL WIDTH=25>"));
			  sb.append(String.format("	<COL WIDTH=33>"));
			  sb.append(String.format("	<COL WIDTH=25>"));
			  sb.append(String.format("	<COL WIDTH=25>"));
			  sb.append(String.format("	<COL WIDTH=33>"));
			  sb.append(String.format("	<COL WIDTH=25>"));
			  sb.append(String.format("	<COL WIDTH=25>"));
			  sb.append(String.format("	<COL WIDTH=33>"));
			  sb.append(String.format("	<COL WIDTH=51>"));
			  sb.append(String.format("	<TR VALIGN=TOP>"));
			  sb.append(String.format("		<TD ROWSPAN=2 WIDTH=42 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Участок</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD COLSPAN=3 WIDTH=111 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Обследования</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD COLSPAN=3 WIDTH=111 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Явки</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD COLSPAN=3 WIDTH=111 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Госпит-ция</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD COLSPAN=3 WIDTH=111 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Противорец.лечение</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD COLSPAN=3 WIDTH=111 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">СКЛ</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD COLSPAN=3 WIDTH=111 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Конс-ции</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD COLSPAN=3 WIDTH=111 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Санация</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD COLSPAN=3 WIDTH=111 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Проф.мер-ия</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD ROWSPAN=2 WIDTH=51 STYLE=\"border: 1px solid #000000; padding: 0cm 0.19cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER> <FONT SIZE=2 STYLE=\"font-size: 9pt\">Выявлено"));
			  sb.append(String.format("			очагов</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("	</TR>"));
			  sb.append(String.format("	<TR VALIGN=TOP>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">план</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">факт</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">план</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">факт</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">план"));
			  sb.append(String.format("			</FONT>"));
			  sb.append(String.format("			</P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">факт</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">план</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">факт"));
			  sb.append(String.format("			</FONT>"));
			  sb.append(String.format("			</P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%"));
			  sb.append(String.format("			</FONT>"));
			  sb.append(String.format("			</P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">план</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">факт"));
			  sb.append(String.format("			</FONT>"));
			  sb.append(String.format("			</P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">план"));
			  sb.append(String.format("			</FONT>"));
			  sb.append(String.format("			</P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">факт"));
			  sb.append(String.format("			</FONT>"));
			  sb.append(String.format("			</P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%"));
			  sb.append(String.format("			</FONT>"));
			  sb.append(String.format("			</P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">план"));
			  sb.append(String.format("			</FONT>"));
			  sb.append(String.format("			</P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">факт"));
			  sb.append(String.format("			</FONT>"));
			  sb.append(String.format("			</P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%"));
			  sb.append(String.format("			</FONT>"));
			  sb.append(String.format("			</P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">план"));
			  sb.append(String.format("			</FONT>"));
			  sb.append(String.format("			</P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">факт"));
			  sb.append(String.format("			</FONT>"));
			  sb.append(String.format("			</P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%"));
			  sb.append(String.format("			</FONT>"));
			  sb.append(String.format("			</P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("	</TR>"));
			  sb.append(String.format("	<TR VALIGN=TOP>"));
			  sb.append(String.format("		<TD WIDTH=42 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">1</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">2</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">3</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">4</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">5</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">6</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">7</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">8</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">9</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">10</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">11</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">12</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">13</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">14</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">15</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">16</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">17</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">18</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">19</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">20</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">21</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">22</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">23</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">24</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">25</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("		<TD WIDTH=51 STYLE=\"border: 1px solid #000000; padding: 0cm 0.19cm\">"));
			  sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">26</FONT></P>"));
			  sb.append(String.format("		</TD>"));
			  sb.append(String.format("	</TR>"));


			 
				while (spat.getResultSet().next()){

					if (spat.getResultSet().first()){
						if (poldv==2){
							nuch = spat.getResultSet().getString("d_uch");
						}else{
							nuch =spat.getResultSet().getString("cod_sp")+"-"+spat.getResultSet().getString("d_uch");					
						}
					}

					if (poldv==2){
						nuchn = spat.getResultSet().getString("d_uch");
					}else{
						nuchn =spat.getResultSet().getString("cod_sp")+"-"+spat.getResultSet().getString("d_uch");					
					}
				
				
					if (!nuch.equals(nuchn)){
						if(mas[1] != 0){
							mas[3]=(mas[2]/mas[1]*100);
						}else{mas[3]=0;}
						
						if(mas[4] != 0){
							mas[6]=(mas[5]/mas[4]*100);
						}else{mas[6]=0;}
						
						if(mas[7] != 0){
							mas[9]=(mas[8]/mas[7]*100);
						}else{mas[9]=0;}
						
						if(mas[10] != 0){
							mas[12]=(mas[11]/mas[10]*100);
						}else{mas[12]=0;}
						
						if(mas[13] != 0){
							mas[15]=(mas[14]/mas[13]*100);
						}else{mas[15]=0;}
						
						if(mas[16] != 0){
							mas[18]=(mas[17]/mas[16]*100);
						}else{mas[18]=0;}
						
						if(mas[19] != 0){
							mas[21]=(mas[20]/mas[19]*100);
						}else{mas[21]=0;}
						
						if(mas[22] != 0){
							mas[24]=(mas[23]/mas[22]*100);
						}else{mas[24]=0;}
						
						
						/*Наполнение таблицы*/
						sb.append(String.format(ZapTab(nuch, mas)));
						
						
						
						nuch=nuchn;
						mas = null;
						chek = false;
					}
				
				
				
					//Обострения
					if (chek==false){
					
						for (int i = 0 ; i<obos.length; i++){
							if (nuch.equals(obos[i][0])){
								mas[25] = Integer.valueOf(obos[i][1]);
								sum[25] = sum[25] + Integer.valueOf(obos[i][1]);
								break;
							}
						
						}
						chek=true;
					}
				
					//Обследование
					if ((spat.getResultSet().getInt("pmer") == 1)||((spat.getResultSet().getInt("pmer") >= 18)&&(spat.getResultSet().getInt("pmer") <= 24))){
						if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
							mas[1]= mas[1]+1;
							sum[1]= sum[1]+1;
						}
						if (( spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
							mas[2]= mas[2]+1;
							sum[1]= sum[1]+1;
						}
					}
					//Явки
					if (spat.getResultSet().getInt("pmer")==2){
						if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
							mas[4]= mas[4]+1;
							sum[4]= mas[4]+1;
						}
						if ((spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
							mas[5]= mas[5]+1;
							mas[5]= mas[5]+1;
						}
									
					}
					//Госпитализация
					if ((spat.getResultSet().getInt("pmer")==3)||(spat.getResultSet().getInt("pmer")==12)){
						if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
							mas[7]= mas[7]+1;
							sum[7]= mas[7]+1;
						}
						if ((spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
							mas[8]= mas[8]+1;
							mas[8]= mas[8]+1;
						}
									
					}	
					//Противрец. лечение
					if ((spat.getResultSet().getInt("pmer")==4)||(spat.getResultSet().getInt("pmer")==10)||(spat.getResultSet().getInt("pmer")==11)
							||(spat.getResultSet().getInt("pmer")==13)||(spat.getResultSet().getInt("pmer")==25)||(spat.getResultSet().getInt("pmer")==27)
							||(spat.getResultSet().getInt("pmer")==29)){
						if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
							mas[10]= mas[10]+1;
							sum[10]= mas[10]+1;
						}
						if ((spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
							mas[11]= mas[11]+1;
							mas[11]= mas[11]+1;
						}
									
					}		
					//СКЛ
					if ((spat.getResultSet().getInt("pmer")==5)||(spat.getResultSet().getInt("pmer")==16)){
						if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
							mas[13]= mas[13]+1;
							sum[13]= mas[13]+1;
						}
						if ((spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
							mas[14]= mas[14]+1;
							mas[14]= mas[14]+1;
						}
									
					}	
					//Консультация
					if ((spat.getResultSet().getInt("pmer")==7)||(spat.getResultSet().getInt("pmer")==28)){
						if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
							mas[16]= mas[16]+1;
							sum[16]= mas[16]+1;
						}
						if ((spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
							mas[17]= mas[17]+1;
							mas[17]= mas[17]+1;
						}
					
					}
				
					//Санации
					if ((spat.getResultSet().getInt("pmer")==9)||(spat.getResultSet().getInt("pmer")==14)||(spat.getResultSet().getInt("pmer")==15)){
						if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
							mas[19]= mas[19]+1;
							sum[19]= mas[19]+1;
						}
						if ((spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
							mas[20]= mas[20]+1;
							mas[20]= mas[20]+1;
						}
									
					}
				
					//Проф. мероп.
					if ((spat.getResultSet().getInt("pmer")==8)||(spat.getResultSet().getInt("pmer")==17)||(spat.getResultSet().getInt("pmer")==26)){
						if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
							mas[22]= mas[22]+1;
							sum[22]= mas[22]+1;
						}
						if ((spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
							mas[23]= mas[23]+1;
							mas[23]= mas[23]+1;
						}
									
					}
					if (spat.getResultSet().last()){
						sb.append(String.format(ZapTab(nuch, mas)));
						sb.append(String.format(ZapTab("Итого", sum)));
					}
				
				}	
				sb.append(String.format("</TABLE>"));
				sb.append(String.format("<P></P>"));
				sb.append(String.format("<P></P>"));
				sb.append(String.format("</BODY>"));
				sb.append(String.format("</HTML>"));
				
			} catch (IOException e) {
				e.printStackTrace();
				throw new KmiacServerException();
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}	
	
	return svod;
} 


private String ZapTab(String nuch, float [] mas){
	
String zap = null;

StringBuilder sb = new StringBuilder(0x10000);

sb.append(String.format("	<TR VALIGN=TOP>"));
sb.append(String.format("		<TD WIDTH=42 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",nuch));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[1])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[2])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[3])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[4])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[5])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[6])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[7])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[8])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[9])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[10])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[11])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[12])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[13])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[14])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[15])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[16])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[17])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[18])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[19])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[20])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[21])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[22])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=25 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[23])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=33 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[24])));
sb.append(String.format("		</TD>"));
sb.append(String.format("		<TD WIDTH=51 STYLE=\"border: 1px solid #000000; padding: 0cm 0.19cm\">"));
sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",String.valueOf(mas[25])));
sb.append(String.format("		</TD>"));
sb.append(String.format("	</TR>"));

return zap = sb.toString();
}






/**
 * Метод, возвращающий количество полных лет
 */
public int getYearDiff(Date a, Date b) {
	//int yearDiff = (a.getTime() - b.getTime())/(24*60*60*1000*365);
	int yearDiff = (int) ((a.getTime() - b.getTime()) / 31556952000L);
	return yearDiff;
	
	
}


/**
 * Метод, возвращающий true при совпадении диагноза с диапазоном
 */
public static boolean isIncludesDiag(String diag, String diagsrpt) {
	
 	String[] vals,vals2;
	 
		vals=diagsrpt.split(",");

		boolean result = false;
		int i = 0;
		while (vals!=null && i<vals.length) {
			// Диапазон вида A00-B00 или A00.0-B00.0
			if (vals[i].length()>=7) {
				vals2 = vals[i].split("-");
				if (vals2[0].compareTo(diag)<=0 & vals2[1].compareTo(diag)>=0) result = true; 
			}
			
			// Диапазон вида A00 или A00.0
			else if (vals[i].compareTo(diag)<=0 & (vals[i]+".99").compareTo(diag)>=0) result = true;
			
			i++;
			
		}
		return result;
	}



@Override
public String printFacZd(InputAuthInfo iaf, InputFacZd ifz)
		throws KmiacServerException, TException {
	// TODO Auto-generated method stub
	return null;
}



@Override
public String printDnevVr() throws KmiacServerException, TException {
	AutoCloseableResultSet acrs = null, acrs2 = null;
	Date data = null;
	Date data1 = null;
	Integer codvr = 0; Integer codpol = 206;
	String codsp = "";
	String fio = "";
	String path = null;
	
	try 
	(OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("posvr", ".htm").getAbsolutePath()), "utf-8")) 
//		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("posvr", ".htm").getAbsolutePath()), "utf-8")) {
//			AutoCloseableResultSet acrs;
			{
		StringBuilder sb = new StringBuilder(0x10000);
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		sb.append("<head>");
			sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
			sb.append("<title>Посещения врачей поликлиники</title>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<p align=center>ИНФО МУЗДРАВ<br></p>");
			sb.append("<h3 align=center>Нагрузка по врачам<br></h3>");
			sb.append("<p align=center>Поликлиники</p>");
			sb.append("<br>");
			sb.append("<TABLE BORDER=2>");
			sb.append("<TR>");
			sb.append("<TD rowspan=2 align=center>N п/п.</TD>");
			sb.append("<TD rowspan=2 align=center>Фамилия, имя, отчество</TD>");
			sb.append("<TD rowspan=2 align=center>Должность</TD>");
			sb.append("<TD rowspan=2 align=center>Время</TD>");
//			sb.append("<TD rowspan=2 align=center>Ставок</TD>");
			sb.append("<TD colspan=3 align=center>Посещения в поликлинике</TD>");
			sb.append("<TD colspan=3 align=center>Посещения на дому</TD>");
			sb.append("<TD colspan=3 align=center>Посещения с профцелью</TD>");
			sb.append("<TD colspan=3 align=center>Посещения всего</TD>");
			sb.append("</TR>");
			sb.append("<TR>");
			sb.append("<TD>план</TD>");
			sb.append("<TD>факт</TD>");
			sb.append("<TD>процент выполнения плана</TD>");
			sb.append("<TD>план</TD>");
			sb.append("<TD>факт</TD>");
			sb.append("<TD>процент выполнения плана</TD>");
			sb.append("<TD>план</TD>");
			sb.append("<TD>факт</TD>");
			sb.append("<TD>процент выполнения плана</TD>");
			sb.append("<TD>план</TD>");
			sb.append("<TD>факт</TD>");
			sb.append("<TD>процент выполнения плана</TD>");
			sb.append("</TR>");
			sb.append("<TR>");
			Integer n1 = 0;Integer vr = 0;
			Double vrem = 0.0; 	Double st = 0.0;
			double ppp = 0; Integer ppf = 0; 
			double pdp = 0; Integer pdf = 0; 
			double ppfp = 0; Integer ppff = 0; 
			double pp = 0; Integer pf = 0; Double proc = 0.0;
			double ippp = 0; Integer ippf = 0; 
			double ipdp = 0; Integer ipdf = 0; 
			double ippfp = 0; Integer ippff = 0; 
			double ipp = 0; Integer ipf = 0; double itime = 0;

			System.out.println(codpol);		
			acrs = sse.execPreparedQuery("select count(*),a.cdol,a.mobs,a.opl,a.cpos,v.cobr,(v.datao-p.datar)/365.25,p.pol,s.fam,s.im,s.ot,p.jitel,v.id,c0.name,v.cpol,v.datao,a.cod_sp "+
//                                                     1       2      3     4      5      6                        7     8      9   10   11     12   13       14    15      16       17
			"from p_vizit_amb a,p_vizit v,patient p,s_vrach s,n_s00 c0 "+
"where a.id_obr=v.id and a.npasp=p.npasp and a.cod_sp=s.pcod and a.cdol=c0.pcod and a.cpol = ? "+
"group by a.id_obr,a.cdol,c0.name,a.mobs,a.opl,a.cpos,v.cpol,v.cobr,v.datao,(v.datao-p.datar)/365.25,p.pol,s.fam,s.im,s.ot,v.id,p.jitel,a.cod_sp "+
"order by a.cod_sp,s.fam,s.im,s.ot,a.cdol,a.id_obr,v.id,p.jitel ",codpol);
			while (acrs.getResultSet().next()) {
            codvr = acrs.getResultSet().getInt(17);
            codsp = acrs.getResultSet().getString(2);
            fio = acrs.getResultSet().getString(9)+' '+acrs.getResultSet().getString(10)+' '+acrs.getResultSet().getString(11);
			while (codvr == acrs.getResultSet().getInt(17)&& (acrs.getResultSet().next())){
			if(acrs.getResultSet().getInt(3)==1) {ppf = ppf + acrs.getResultSet().getInt(1);
			ippf = ippf + acrs.getResultSet().getInt(1);}
			if(acrs.getResultSet().getInt(3)==2) {pdf = pdf + acrs.getResultSet().getInt(1);
			ipdf = ipdf + acrs.getResultSet().getInt(1);}
			if(acrs.getResultSet().getInt(6)!=1) {ppfp = ppfp + acrs.getResultSet().getInt(1);
			ippfp = ippfp + acrs.getResultSet().getInt(1);}
			pf = pf + acrs.getResultSet().getInt(1);
			ipf = ipf + acrs.getResultSet().getInt(1);
			}
			n1 = n1 + 1;
			//посчитать процент
			ppp = 0;
			pdp = 0;
			ppfp = 0;
			acrs2 = sse.execPreparedQuery("select pospol*prpol,posprof*prprof,posdom*prdom,rabden,koldn,colst "+
//                  1              2            3      4    5      6  
			"from n_n63 where codpol= ? and codvrdol = ? ",codpol,codsp);
			if (acrs2.getResultSet().next()) {
			ppp = acrs2.getResultSet().getDouble(1);
			pdp = acrs2.getResultSet().getDouble(3);
			ppfp = acrs2.getResultSet().getDouble(2);
			}
			acrs2.close();
			sb.append(String.format("<td> %d",n1));
			sb.append(String.format("<td> %s",fio));
			sb.append(String.format("<td> %s",codsp));
//			sb.append(String.format("<TD> %s>",ppfp));
			acrs2 = sse.execPreparedQuery("select sum(timep),sum(timed),sum(timeda),sum(timeprf),sum(timepr) from s_tabel where pcod = ?",codvr);
			if (acrs2.getResultSet().next()) {
				//время
			sb.append(String.format("<TD>%.2f </TD>",(acrs2.getResultSet().getDouble(1)+acrs2.getResultSet().getDouble(2)+acrs2.getResultSet().getDouble(3)+acrs2.getResultSet().getDouble(4))));
			itime = itime + acrs2.getResultSet().getDouble(1)+acrs2.getResultSet().getDouble(2)+acrs2.getResultSet().getDouble(3)+acrs2.getResultSet().getDouble(4);
//			sb.append(String.format("<TD>%.2f ставок</TD>",acrs2.getResultSet().getInt(9),acrs2.getResultSet().getInt(10)));
			sb.append(String.format("<TD>%.2f </TD>",(acrs2.getResultSet().getDouble(1)*ppp)));//план в поликлинике
			ippp = ippp+acrs2.getResultSet().getDouble(1)*ppp;
			pp = pp+acrs2.getResultSet().getDouble(1)*ppp;
			ipp = ipp+acrs2.getResultSet().getDouble(1)*ppp;
			    sb.append(String.format("<TD> %d</TD>",ppf));//факт в поликлинике
			if ((acrs2.getResultSet().getDouble(1)*ppp)!=0)
			    proc= ppf*100/(acrs2.getResultSet().getDouble(1)*ppp);
			sb.append(String.format("<TD> %.2f</TD>",proc));//процент
			sb.append(String.format("<TD>%.2f </TD>",(acrs2.getResultSet().getDouble(2)*pdp)));//план на дому
			ipdp = ipdp+acrs2.getResultSet().getDouble(2)*pdp;
			pp = pp+acrs2.getResultSet().getDouble(2)*pdp;
			ipp = ipp+acrs2.getResultSet().getDouble(2)*pdp;
			    sb.append(String.format("<TD> %d</TD>",pdf));//факт на дому
			if ((acrs2.getResultSet().getDouble(2)*pdp)!=0)
			    proc= pdf*100/(acrs2.getResultSet().getDouble(2)*pdp);
			sb.append(String.format("<TD> %.2f</TD>",proc));//процент
			sb.append(String.format("<TD>%.2f </TD>",(acrs2.getResultSet().getDouble(4)*ppfp)));//план профцель
			ipdp = ipdp+acrs2.getResultSet().getDouble(4)*ppfp;
			pp = pp+acrs2.getResultSet().getDouble(4)*ppfp;//что с прочими?
			ipp = ipp+acrs2.getResultSet().getDouble(4)*ppfp;//что с прочими?
		    sb.append(String.format("<TD> %d</TD>",ppff));//факт профцель
			if ((acrs2.getResultSet().getDouble(4)*ppfp)!=0)
		    proc= ppff*100/(acrs2.getResultSet().getDouble(4)*ppfp);
			sb.append(String.format("<TD> %.2f</TD>",proc));//процент
			sb.append(String.format("<TD>%.2f </TD>",pp));//план всего
		    sb.append(String.format("<TD> %d</TD>",pf));//факт всего
			if ((acrs2.getResultSet().getDouble(4)*pp)!=0)
		    proc= pf*100/(acrs2.getResultSet().getDouble(4)*pp);
			sb.append(String.format("<TD> %.2f</TD>",proc));//процент
			}
			acrs2.close();
			sb.append("<TD> </TD>");
			sb.append("</TR>");
			pp=0; ppf=0; pdf=0; ppff=0; pf=0;
			}
//			}
			acrs.close();
			acrs2 = sse.execPreparedQuery("select sum(pospol*prpol*colst),sum(posprof*prprof*colst),sum(posdom*prdom*colst),rabden,koldn "+ 
					"from n_n63 where codpol = ? group by rabden,koldn ",codpol);
			if (acrs2.getResultSet().next()) {
//разместить строку ИТОГО	
				sb.append("<td> /TD>");
				sb.append("<td> ИТОГО</TD>");
				sb.append("<TD> </TD>");
				sb.append(String.format("<TD> %.2f",itime));//время
				sb.append(String.format("<TD>%.2f </TD>",(acrs2.getResultSet().getDouble(1)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500)));//план в поликлинике
 			    sb.append(String.format("<TD> %d</TD>",ippf));//факт в поликлинике
				proc= ppf*100/((acrs2.getResultSet().getDouble(1)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500));
				sb.append(String.format("<TD> %.2f</TD>",proc));//процент
				sb.append(String.format("<TD>%.2f </TD>",((acrs2.getResultSet().getDouble(2)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500))));//план на дому
 			    sb.append(String.format("<TD> %d</TD>",ipdf));//факт на дому
				if ((acrs2.getResultSet().getDouble(2)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5))!=0)
 			    proc= pdf*100/((acrs2.getResultSet().getDouble(2)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500));
				sb.append(String.format("<TD> %.2f</TD>",proc));//процент
				sb.append(String.format("<TD>%.2f </TD>",((acrs2.getResultSet().getDouble(3)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500))));//план профцель
			    sb.append(String.format("<TD> %d</TD>",ippff));//факт профцель
				if (((acrs2.getResultSet().getDouble(3)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500))!=0)
			    proc= ppff*100/((acrs2.getResultSet().getDouble(3)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500));
				sb.append(String.format("<TD> %.2f</TD>",proc));//процент
				ipp = (acrs2.getResultSet().getDouble(1)+acrs2.getResultSet().getDouble(2)+acrs2.getResultSet().getDouble(3))*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500;
				sb.append(String.format("<TD>%.2f </TD>",ipp));//план всего
			    sb.append(String.format("<TD> %d</TD>",ipf));//факт всего
				if (ipp!=0)
			    proc= pf*100/ipp;
				sb.append(String.format("<TD> %.2f</TD>",proc));//процент
			}
			sb.append("</TABLE>");
		sb.append("</body>"); 
		sb.append("</html>");
		
		osw.write(sb.toString());
		System.out.println(sb);		
		return path;
//		return path = sb.toString();
	} catch (SQLException e) {
		((SQLException) e.getCause()).printStackTrace();
		throw new KmiacServerException();
	}
//	catch (UnsupportedEncodingException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//		throw new KmiacServerException();
//	} catch (FileNotFoundException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//		throw new KmiacServerException();
//	} 
	catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		throw new KmiacServerException();
	} finally {
		if (acrs != null)
			acrs.close();
		if (acrs2 != null)
			acrs2.close();
	}
}



@Override
public String nagrvr(int cpol) throws KmiacServerException, TException {
	AutoCloseableResultSet acrs = null, acrs2 = null;
	Date data = null;
	Date data1 = null;
//	String dateb = isv.getDateb();
//	String datef = isv.getDatef();
	Integer codpol = cpol;
	String fio = "";
	String path = null;
	Integer n1 = 0;Integer vr = 0;
	Double timep = 0.0; 
	Double timed = 0.0; 
	Double timeda = 0.0; 
	Double timeprf = 0.0; 
	Double timepr = 0.0; 
	Double st = 0.0;
	double ppp = 0; Integer ppf = 0; 
	double pdp = 0; Integer pdf = 0; 
	double ppfp = 0; Integer ppff = 0; 
	double pp = 0; Integer pf = 0; Double proc = 0.0;
	double ippp = 0; Integer ippf = 0; 
	double ipdp = 0; Integer ipdf = 0; 
	double ippfp = 0; Integer ippff = 0; 
	double ipp = 0; Integer ipf = 0; 
	double itime = 0;
	
	try 
	(OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("posvr", ".htm").getAbsolutePath()), "utf-8")) 
//		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("posvr", ".htm").getAbsolutePath()), "utf-8")) {
//			AutoCloseableResultSet acrs;
			{
		StringBuilder sb = new StringBuilder(0x10000);
		sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
		sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
		sb.append("<head>");
			sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
			sb.append("<title>Посещения врачей поликлиники</title>");
		sb.append("</head>");
		sb.append("<body>");
		sb.append("<p align=center>ИНФО МУЗДРАВ<br></p>");
			sb.append("<h3 align=center>Нагрузка по врачам<br></h3>");
			sb.append("<p align=center>Поликлиники</p>");
			sb.append("<br>");
			sb.append("<TABLE BORDER=2>");
			sb.append("<TR>");
			sb.append("<TD rowspan=2 align=center>N п/п.</TD>");
			sb.append("<TD rowspan=2 align=center>Фамилия, имя, отчество</TD>");
			sb.append("<TD rowspan=2 align=center>Должность</TD>");
			sb.append("<TD rowspan=2 align=center>Время</TD>");
//			sb.append("<TD rowspan=2 align=center>Ставок</TD>");
			sb.append("<TD colspan=3 align=center>Посещения в поликлинике</TD>");
			sb.append("<TD colspan=3 align=center>Посещения на дому</TD>");
			sb.append("<TD colspan=3 align=center>Посещения с профцелью</TD>");
			sb.append("<TD colspan=3 align=center>Посещения всего</TD>");
			sb.append("</TR>");
			sb.append("<TR>");
			sb.append("<TD>план</TD>");
			sb.append("<TD>факт</TD>");
			sb.append("<TD>процент выполнения плана</TD>");
			sb.append("<TD>план</TD>");
			sb.append("<TD>факт</TD>");
			sb.append("<TD>процент выполнения плана</TD>");
			sb.append("<TD>план</TD>");
			sb.append("<TD>факт</TD>");
			sb.append("<TD>процент выполнения плана</TD>");
			sb.append("<TD>план</TD>");
			sb.append("<TD>факт</TD>");
			sb.append("<TD>процент выполнения плана</TD>");
			sb.append("</TR>");
			sb.append("<TR>");

			System.out.println(codpol);	
			n1 = 0;
			acrs = sse.execPreparedQuery("select sum(timep),sum(timed),sum(timeda),sum(timeprf),sum(timepr),t.pcod,t.cdol,v.fam,v.im,v.ot,v.idv "+
//			                                              1         2           3            4           5       6      7     8    9    10   11  
			"from s_tabel t,s_users u,s_vrach v "+
			"where t.pcod=u.pcod and t.pcod=v.pcod and u.cpodr = ? "+
			"group by t.pcod,t.cdol,v.fam,v.im,v.ot,v.idv ",codpol);
			while (acrs.getResultSet().next()){
				//выполнение на всех врачей, у которых есть часы	
			n1 = n1+1;
			fio = acrs.getResultSet().getString(8)+ ' '+ acrs.getResultSet().getString(9)+ ' '+acrs.getResultSet().getString(10);	
			timep = acrs.getResultSet().getDouble(1);
			timed = acrs.getResultSet().getDouble(2);
			timeda = acrs.getResultSet().getDouble(3);
			timeprf = acrs.getResultSet().getDouble(4);
			timepr = acrs.getResultSet().getDouble(5);
//          план			
			acrs2 = sse.execPreparedQuery("select pospol*prpol,posprof*prprof,posdom*prdom,pospol*prproch,rabden,koldn,colst "+
//                                                           1              2            3              4      5     6     7  
			"from n_n63 where codpol= ? and codvrdol = ? ",codpol,acrs.getResultSet().getString(7));
			if (acrs2.getResultSet().next()) {
			ppp = acrs2.getResultSet().getDouble(1) * timep/100;	
			pdp = acrs2.getResultSet().getDouble(3) * timed/100;	
			ppfp = acrs2.getResultSet().getDouble(2) * timeprf/100;	
			pp = acrs2.getResultSet().getDouble(1) * timep/100 +
			acrs2.getResultSet().getDouble(3) * timed/100 +
			acrs2.getResultSet().getDouble(2) * timeprf/100 +
			acrs2.getResultSet().getDouble(4) * timepr/100;
			}
			acrs2.close();
//          факт
			acrs2 = sse.execPreparedQuery("select count(*),mobs,cpos from p_vizit_amb where cod_sp=? group by mobs,cpos",acrs.getResultSet().getInt(6));
//                                                      1     2    3 
			while (acrs2.getResultSet().next()) {
				if(acrs2.getResultSet().getInt(2)==1) ppf = ppf + acrs2.getResultSet().getInt(1);
				if(acrs2.getResultSet().getInt(2)==2) pdf = pdf + acrs2.getResultSet().getInt(1);
				if(acrs2.getResultSet().getInt(3)!=1) ppff = ppff + acrs2.getResultSet().getInt(1);
				pf = pf + acrs2.getResultSet().getInt(1);
             }
             acrs2.close();
//           строка на врача             
 			sb.append(String.format("<td> %d",n1));
 			sb.append(String.format("<td> %s",fio));
 			sb.append(String.format("<td> %s",acrs.getResultSet().getString(7)));
// 			sb.append(String.format("<TD> %s>",ppfp));
 			sb.append(String.format("<TD>%.2f </TD>",(timep + timed + timeprf + timepr)));
 			itime = itime + timep + timed + timeprf + timepr;
 			sb.append(String.format("<TD>%.2f </TD>",(ppp)));//план в поликлинике
		    sb.append(String.format("<TD> %d</TD>",ppf));//факт в поликлинике
		    ippf = ippf + ppf;
		    if (ppp!=0)	proc= ppf*100/ppp; else proc = 0.0;
 			sb.append(String.format("<TD> %.2f</TD>",proc));//процент
 			sb.append(String.format("<TD>%.2f </TD>",pdp));//план на дому
 			sb.append(String.format("<TD> %d</TD>",pdf));//факт на дому
 			ipdf = ipdf + pdf;
 			if (pdp!=0)	proc= pdf*100/pdp;  else proc = 0.0;
 			sb.append(String.format("<TD> %.2f</TD>",proc));//процент
 			sb.append(String.format("<TD>%.2f </TD>",ppfp));//план профцель
 		    sb.append(String.format("<TD> %d</TD>",ppff));//факт профцель
			ippff = ippff + ppff;
 			if (ppfp!=0) proc= ppff*100/ppfp;  else proc = 0.0;
 			sb.append(String.format("<TD> %.2f</TD>",proc));//процент
 			sb.append(String.format("<TD>%.2f </TD>",pp));//план всего
 		    sb.append(String.format("<TD> %d</TD>",pf));//факт всего
			ipf = ipf + pf;
 			if (pp!=0) proc= pf*100/pp; else proc = 0.0;
 			sb.append(String.format("<TD> %.2f</TD>",proc));//процент
			sb.append("<TD> </TD>");
			sb.append("</TR>");
			 ppp = 0;  ppf = 0; 
			 pdp = 0;  pdf = 0; 
			 ppfp = 0;  ppff = 0; 
			 pp = 0;  pf = 0;
			timep = 0.0; 
			timed = 0.0; 
			timeprf = 0.0; 
			timepr = 0.0; 
             }
			//разместить строку ИТОГО вставить период по формуле	
			acrs2 = sse.execPreparedQuery("select sum(pospol*prpol*colst),sum(posprof*prprof*colst),sum(posdom*prdom*colst),rabden,koldn "+ 
					"from n_n63 where codpol = ? group by rabden,koldn ",codpol);
			if (acrs2.getResultSet().next()) {
				sb.append("<td> /TD>");
				sb.append("<td> ИТОГО</TD>");
				sb.append("<TD> </TD>");
				sb.append(String.format("<TD> %.2f",itime));//время
				sb.append(String.format("<TD>%.2f </TD>",(acrs2.getResultSet().getDouble(1)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500)));//план в поликлинике
 			    sb.append(String.format("<TD> %d</TD>",ippf));//факт в поликлинике
 			    if((acrs2.getResultSet().getDouble(1)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500) !=0)
				proc= ppf*100/((acrs2.getResultSet().getDouble(1)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500));
 			    else proc = 0.0;
				sb.append(String.format("<TD> %.2f</TD>",proc));//процент
				sb.append(String.format("<TD>%.2f </TD>",((acrs2.getResultSet().getDouble(2)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500))));//план на дому
 			    sb.append(String.format("<TD> %d</TD>",ipdf));//факт на дому
				if ((acrs2.getResultSet().getDouble(2)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5))!=0)
 			    proc= pdf*100/((acrs2.getResultSet().getDouble(2)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500));
 			    else proc = 0.0;
				sb.append(String.format("<TD> %.2f</TD>",proc));//процент
				sb.append(String.format("<TD>%.2f </TD>",((acrs2.getResultSet().getDouble(3)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500))));//план профцель
			    sb.append(String.format("<TD> %d</TD>",ippff));//факт профцель
				if (((acrs2.getResultSet().getDouble(3)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500))!=0)
			    proc= ppff*100/((acrs2.getResultSet().getDouble(3)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500));
 			    else proc = 0.0;
				sb.append(String.format("<TD> %.2f</TD>",proc));//процент
				ipp = (acrs2.getResultSet().getDouble(1)+acrs2.getResultSet().getDouble(2)+acrs2.getResultSet().getDouble(3))*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500;
				sb.append(String.format("<TD>%.2f </TD>",ipp));//план всего
			    sb.append(String.format("<TD> %d</TD>",ipf));//факт всего
				if (ipp!=0)
			    proc= pf*100/ipp;  else proc = 0.0;
				sb.append(String.format("<TD> %.2f</TD>",proc));//процент
			}
			sb.append("</TABLE>");
		sb.append("</body>"); 
		sb.append("</html>");
		
		osw.write(sb.toString());
		System.out.println(sb);		
		return path;
//		return path = sb.toString();
	} catch (SQLException e) {
		((SQLException) e.getCause()).printStackTrace();
		throw new KmiacServerException();
	}
//	catch (UnsupportedEncodingException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//		throw new KmiacServerException();
//	} catch (FileNotFoundException e1) {
//		// TODO Auto-generated catch block
//		e1.printStackTrace();
//		throw new KmiacServerException();
//	} 
	catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		throw new KmiacServerException();
	} finally {
		if (acrs != null)
			acrs.close();
		if (acrs2 != null)
			acrs2.close();
	}
}



@Override
public String printOtDetPol(InputPlanDisp ipd) throws KmiacServerException,
		TException {
	String svod = null;
	
	// Дата от ...
	Date dn;
	// Дата до ...
	Date dk;
	try {
		dn = (Date) sdfo.parse(ipd.getDaten());
		dk = (Date) sdfo.parse(ipd.getDatek());

		// Код полеклиники
		int kodpol = ipd.getKpolik();
		
		// Код ЛПУ
		int kodlpu = ipd.getClpu();
		// Вид больницы (Д/В)
		int poldv = 0;
		
		final String sqlQueryDetVzPol = "select c_nom from n_m00 where pcod ="+String.valueOf(kodlpu);
		
		try (AutoCloseableResultSet zapznach = sse.execPreparedQuery(sqlQueryDetVzPol)) {
			
			poldv = zapznach.getResultSet().getInt("c_nom");
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		final String sqlQueryKolDis ="select nd.name_s, count(distinct(pd.npasp)) as kol"+
									 "from p_diag pd join n_ot100 ot on (pd.cdol_ot = ot.pcod)"+
									 "		join n_d0s nd on (nd.pcod = ot.pcod1)"+
									 "		join p_nambk pn on (pn.npasp = pd.npasp)"+
									 "where (pn.ishod is null)and(pn.dataot is null) and"+
									 "		(((pd.d_grup>2)and(pd.d_grup<10))or(pd.d_grup=22)) and"+
									 "		(pn.cpol="+ String.valueOf(kodpol)+") and"+
									 "		(pd.disp in(1,2,3))and"+
									 "		(pd.d_vz is not null)and(pd.d_vz<'"+String.valueOf(dk)+"')"+
									 "group by ot.pcod1, nd.name_s"+
									 "order by ot.pcod1, nd.name_s";
		
		
		
		
		String [][] mas = null;
		try (AutoCloseableResultSet koldisp = sse.execPreparedQuery(sqlQueryKolDis)) {
			
			//String [][] prom = new String [1][2]; 
			try {
				int i =0;
				while (koldisp.getResultSet().next())i++;				
				
				mas = new String[i+1][26];
				
				int j=1;
				
				koldisp.getResultSet().first();
				
					mas [0][0] = koldisp.getResultSet().getString("name_s");
					mas [0][1] = koldisp.getResultSet().getString("kol");
					mas [i-1] [0] = "Итого";
				
				while (koldisp.getResultSet().next()){
					
					mas [j][0] = koldisp.getResultSet().getString("name_s");
					mas [j][1] = koldisp.getResultSet().getString("kol");
					
					j++;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (SqlExecutorException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}		
		
		
		final String sqlQuerySvedDisp ="select nd.name_s, count(pm.pmer) as kol, pm.pmer, pm.pdat, pm.fdat"+
				 "from p_mer pm join n_ot100 ot on(pm.cdol =ot.pcod)"+
				 "		join n_d0s nd on (nd.pcod = ot.pcod1)"+
				 "where ((pm.pdat between '"+ dn +"' and '"+ dk+"') or"+
				 "		(pm.fdat between '"+ dn +"' and '"+ dk+"')) and"+
				 "		(pm.cpol="+ String.valueOf(kodpol)+")"+
				 "group by nd.name_s, pm.pmer, pm.pdat, pm.fdat"+
				 "order by nd.name_s, pm.pmer, pm.pdat, pm.fdat";		
		
		try (AutoCloseableResultSet spat = sse.execPreparedQuery(sqlQuerySvedDisp)) {
			
			
			
			try {
				while (spat.getResultSet().next()){

/*				if (!nuch.equals(nuchn)){
						mas[3]=(mas[2]/mas[1]*100);
						mas[6]=(mas[5]/mas[4]*100);
						mas[9]=(mas[8]/mas[7]*100);
						mas[12]=(mas[11]/mas[10]*100);
						mas[15]=(mas[14]/mas[13]*100);
						mas[18]=(mas[17]/mas[16]*100);
						mas[21]=(mas[20]/mas[19]*100);
						mas[24]=(mas[23]/mas[22]*100);
					
						
						/*Наполнение таблицы*/
/*					sb.append(String.format(ZapTab(nuch, mas)));
						
						
						
						nuch=nuchn;
						mas = null;
						chek = false;
					}
*/			
				
				
/*				//Обострения
					if (chek==false){
					
						for (int i = 0 ; i<obos.length; i++){
							if (nuch.equals(obos[i][0])){
								mas[25] = Integer.valueOf(obos[i][1]);
								sum[25] = sum[25] + Integer.valueOf(obos[i][1]);
								break;
							}
						
						}
						chek=true;
					}
				*/
					
					
					for (int i=0; i<mas.length;i++){
						if(spat.getResultSet().getString("name_s").equals(mas[i][0])){
							
							
							//Обследование
							if ((spat.getResultSet().getInt("pmer") == 1)||((spat.getResultSet().getInt("pmer") >= 18)&&(spat.getResultSet().getInt("pmer") <= 24))){
								if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
									mas[i][2]= mas[i][2]+spat.getResultSet().getInt("kol");
									mas[mas.length-1][2]= mas[mas.length-1][2]+spat.getResultSet().getInt("kol");
								}
								if (( spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
									mas[i][3]= mas[i][3]+spat.getResultSet().getInt("kol");
									mas[mas.length-1][3]= mas[mas.length-1][3]+spat.getResultSet().getInt("kol");	
									}
							}
							//Явки
							if (spat.getResultSet().getInt("pmer")==2){
								if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
									mas[i][5]= mas[i][5]+spat.getResultSet().getInt("kol");
									mas[mas.length-1][5]= mas[mas.length-1][5]+spat.getResultSet().getInt("kol");
								}
								if (( spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
									mas[i][6]= mas[i][6]+spat.getResultSet().getInt("kol");
									mas[mas.length-1][6]= mas[mas.length-1][6]+spat.getResultSet().getInt("kol");
								}
											
							}
							//Госпитализация
							if ((spat.getResultSet().getInt("pmer")==3)||(spat.getResultSet().getInt("pmer")==12)){
								if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
									mas[i][8]= mas[i][8]+spat.getResultSet().getInt("kol");
									mas[mas.length-1][8]= mas[mas.length-1][8]+spat.getResultSet().getInt("kol");
								}
								if (( spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
									mas[i][9]= mas[i][9]+spat.getResultSet().getInt("kol");
									mas[mas.length-1][9]= mas[mas.length-1][9]+spat.getResultSet().getInt("kol");
								}
											
							}	
							//Противрец. лечение
							if ((spat.getResultSet().getInt("pmer")==4)||(spat.getResultSet().getInt("pmer")==10)||(spat.getResultSet().getInt("pmer")==11)
									||(spat.getResultSet().getInt("pmer")==13)||(spat.getResultSet().getInt("pmer")==25)||(spat.getResultSet().getInt("pmer")==27)
									||(spat.getResultSet().getInt("pmer")==29)){
								if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
									mas[i][11]= mas[i][11]+spat.getResultSet().getInt("kol");
									mas[mas.length-1][11]= mas[mas.length-1][11]+spat.getResultSet().getInt("kol");
								}
								if (( spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
									mas[i][12]= mas[i][12]+spat.getResultSet().getInt("kol");
									mas[mas.length-1][12]= mas[mas.length-1][12]+spat.getResultSet().getInt("kol");
								}
											
							}		
							//СКЛ
							if ((spat.getResultSet().getInt("pmer")==5)||(spat.getResultSet().getInt("pmer")==16)){
								if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
									mas[i][14]= mas[i][14]+spat.getResultSet().getInt("kol");
									mas[mas.length-1][14]= mas[mas.length-1][14]+spat.getResultSet().getInt("kol");
								}
								if (( spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
									mas[i][15]= mas[i][15]+spat.getResultSet().getInt("kol");
									mas[mas.length-1][15]= mas[mas.length-1][15]+spat.getResultSet().getInt("kol");
								}
											
							}	
							//Консультация
							if ((spat.getResultSet().getInt("pmer")==7)||(spat.getResultSet().getInt("pmer")==28)){
								if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
									mas[i][17]= mas[i][17]+spat.getResultSet().getInt("kol");
									mas[mas.length-1][17]= mas[mas.length-1][17]+spat.getResultSet().getInt("kol");
								}
								if (( spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
									mas[i][18]= mas[i][18]+spat.getResultSet().getInt("kol");
									mas[mas.length-1][18]= mas[mas.length-1][18]+spat.getResultSet().getInt("kol");
								}
							
							}
						
							//Санации
							if ((spat.getResultSet().getInt("pmer")==9)||(spat.getResultSet().getInt("pmer")==14)||(spat.getResultSet().getInt("pmer")==15)){
								if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
									mas[i][20]= mas[i][20]+spat.getResultSet().getInt("kol");
									mas[mas.length-1][20]= mas[mas.length-1][20]+spat.getResultSet().getInt("kol");
								}
								if (( spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
									mas[i][21]= mas[i][21]+spat.getResultSet().getInt("kol");
									mas[mas.length-1][21]= mas[mas.length-1][21]+spat.getResultSet().getInt("kol");
								}
											
							}
						
							//Проф. мероп.
							if ((spat.getResultSet().getInt("pmer")==8)||(spat.getResultSet().getInt("pmer")==17)||(spat.getResultSet().getInt("pmer")==26)){
								if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
									mas[i][23]= mas[i][23]+spat.getResultSet().getInt("kol");
									mas[mas.length-1][23]= mas[mas.length-1][5]+spat.getResultSet().getInt("kol");
								}
								if (( spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
									mas[i][24]= mas[i][24]+spat.getResultSet().getInt("kol");
									mas[mas.length-1][24]= mas[mas.length-1][24]+spat.getResultSet().getInt("kol");
								}
											
							}
//						if (spat.getResultSet().last()){
//							sb.append(String.format(ZapTab(nuch, mas)));
//							sb.append(String.format(ZapTab("Итого", sum)));
//						}

							
							
							
						}
						

						
						
					}
						
				
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			
			
			
		} catch (SqlExecutorException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
// Формирование таблицы		
		
		
		
	} catch (ParseException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}	
	
	return svod;
}
    

}

