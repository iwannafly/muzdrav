package ru.nkz.ivcgzo.serverOutputInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
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
	

	@SuppressWarnings("deprecation")
	public void PlanovDisp() throws TException, ParseException {
		// Дата от ...
		String d1 = InputPlanDisp._Fields.DATEB.toString();
		// Дата до ...
		String d2 = InputPlanDisp._Fields.DATEF.toString();
		// Код полеклиники
		int kodpol = Integer.parseInt(InputPlanDisp._Fields.KPOLIK.toString());
		// Наименование полеклиники
		String namepol = InputPlanDisp._Fields.NAMEPOL.toString();
		// № участка
		String uc = InputPlanDisp._Fields.UCHAS.toString(); 
		
		final String sqlQueryPlanDis = "select pn.nambk, (p.fam||' '||p.im||' '||p.ot) as fio, p.datar, p.adm_ul,p.adm_dom,p.adm_korp," +
				"p.adm_kv,	pm.diag, na.name, pm.pdat, pn.nuch, pd.d_grup, pm.pdat, pd.d_uch, pm.cod_sp, pm.cpol,pm.fdat, pd.ishod " +
				"from patient p join p_nambk pn on(p.npasp = pn.npasp) join p_mer pm on(p.npasp =pm.npasp) " +
				"join p_disp pd on(p.npasp = pd.npasp) join n_abd na on(pm.pmer = na.pcod) " +
				"where (pm.pdata between "+ d1+" and "+ d2+")and(pd.diag = pm.diag)and(pm.fdat is null)and(pd.ishod is null) and(pn.dataot is null)";
		
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
		Date kpg = null;
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
		
		String sqlQueryPlanDis = "select pn.nambk, (p.fam||' '||p.im||' '||p.ot) as fio, p.datar, p.adm_ul,p.adm_dom,p.adm_korp," +
				"p.adm_kv,	pm.diag, na.name, pm.pdat, pn.nuch, pd.d_grup, pm.pdat, pd.d_uch, pm.cod_sp, pm.cpol,pm.fdat, pd.ishod " +
				"from patient p join p_nambk pn on(p.npasp = pn.npasp) join p_mer pm on(p.npasp =pm.npasp) " +
				"join p_disp pd on(p.npasp = pd.npasp) join n_abd na on(pm.pmer = na.pcod) " +
				"where (pm.pdata between "+ dn+" and "+ dk+")and(pd.diag = pm.diag)and(pm.fdat is null)and(pd.ishod is null) and(pn.dataot is null)" +
						"and(pm.cpol = "+kodpol+")";
		if (uc !=null) sqlQueryPlanDis = sqlQueryPlanDis+"and(pd.d_uch ="+uc+")";
		
		sqlQueryPlanDis = sqlQueryPlanDis + "Order by pm.cpol, p.fam, p.im, p.ot, p.datar";
		
		
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
		
			spat.getResultSet().first();
			int spuch = Integer.parseInt(spat.getResultSet().getString("cod_sp")+"0"+ spat.getResultSet().getString("d_uch"));
			int spuch1 = Integer.parseInt(spat.getResultSet().getString("cod_sp")+"0"+ spat.getResultSet().getString("d_uch"));
			
			sb.append(String.format(ZagShap(dn,dk,namepol,spuch)));
			String adres = null; 
			
			while (spat.getResultSet().next()){
				
				spuch1 = Integer.parseInt(spat.getResultSet().getString("cod_sp")+"0"+ spat.getResultSet().getString("d_uch"));
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
				sb.append(String.format("			<P CLASS=\"western\" ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",spat.getResultSet().getString("nambk")));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=105 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P CLASS=\"western\" ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",spat.getResultSet().getString("fio")));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=59 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P CLASS=\"western\" ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",spat.getResultSet().getString("datar")));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=105 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P CLASS=\"western\" ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>", adres));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=59 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P CLASS=\"western\" ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>", spat.getResultSet().getString("diag")));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=174 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P CLASS=\"western\" ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",spat.getResultSet().getString("name")));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=105 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P CLASS=\"western\" ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>", spat.getResultSet().getString("pdat")));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=62 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P CLASS=\"western\" ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",spat.getResultSet().getString("nuch")));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=105 STYLE=\"border: 1px solid #000000; padding: 0cm 0.19cm\">"));
				sb.append(String.format("			<P CLASS=\"western\" ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">%s</FONT></P>",spat.getResultSet().getString("d_grup")));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("	</TR>"));
			}
			
			sb.append(String.format("</TABLE>"));
			sb.append(String.format("<P CLASS=\"western\" ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P CLASS=\"western\" ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><BR>"));
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
	sb.append(String.format("			<P CLASS=\"western\" ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">N"));
	sb.append(String.format("			амб.карты</FONT></P>"));
	sb.append(String.format("		</TD>"));
	sb.append(String.format("		<TD WIDTH=105 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
	sb.append(String.format("			<P CLASS=\"western\" ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Ф.И.О."));
	sb.append(String.format("			</FONT>"));
	sb.append(String.format("			</P>"));
	sb.append(String.format("		</TD>"));
	sb.append(String.format("		<TD WIDTH=59 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
	sb.append(String.format("			<P CLASS=\"western\" ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Дата"));
	sb.append(String.format("			рождения</FONT></P>"));
	sb.append(String.format("		</TD>"));
	sb.append(String.format("		<TD WIDTH=105 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
	sb.append(String.format("			<P CLASS=\"western\" ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Адрес</FONT></P>"));
	sb.append(String.format("		</TD>"));
	sb.append(String.format("		<TD WIDTH=59 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
	sb.append(String.format("			<P CLASS=\"western\" ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Повод"));
	sb.append(String.format("			дисп-ции</FONT></P>"));
	sb.append(String.format("		</TD>"));
	sb.append(String.format("		<TD WIDTH=174 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
	sb.append(String.format("			<P CLASS=\"western\" ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Планируемые"));
	sb.append(String.format("			мероприятия</FONT></P>"));
	sb.append(String.format("		</TD>"));
	sb.append(String.format("		<TD WIDTH=105 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
	sb.append(String.format("			<P CLASS=\"western\" ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Дата"));
	sb.append(String.format("			мероприятия</FONT></P>"));
	sb.append(String.format("		</TD>"));
	sb.append(String.format("		<TD WIDTH=62 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0cm; padding-left: 0.19cm; padding-right: 0cm\">"));
	sb.append(String.format("			<P CLASS=\"western\" ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Тер.участок</FONT></P>"));
	sb.append(String.format("		</TD>"));
	sb.append(String.format("		<TD WIDTH=105 STYLE=\"border: 1px solid #000000; padding: 0cm 0.19cm\">"));
	sb.append(String.format("			<P CLASS=\"western\" ALIGN=CENTER><FONT SIZE=2 STYLE=\"font-size: 9pt\">Группа"));
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
	// TODO Auto-generated method stub
	return null;
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
    

}

