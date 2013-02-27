package ru.nkz.ivcgzo.serverOutputInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor.SqlExecutorException;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOutputInfo.InputStructPos;
import ru.nkz.ivcgzo.thriftOutputInfo.InputStructPosAuth;

public class serverStructPos {
	private ISqlSelectExecutor sse;
	ITransactedSqlExecutor tse;

	public serverStructPos(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		this.sse = sse;
		this.tse = tse;
	}

	public String printStructPos(InputStructPosAuth ispa, InputStructPos isp)
			throws KmiacServerException, TException {
		String kodForm = "BBPG139";
		String path = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfo = new SimpleDateFormat("dd.MM.yyyy");
		SimpleDateFormat sdfy = new SimpleDateFormat("yyyy");
		// Считывает входные значения с формы
		String Date1 = isp.getDate1();
		String Date2 = isp.getDate2();
		String dateChange = isp.getDateChange();
		AutoCloseableResultSet bok = null;
		// Предыдущий год
		String year1 = null;
		// Текущий год
		String year2 = null;
		// Конец закрытия предыдущего отчетного периода
		java.util.Date kpo = null;
		// Текущая дата
		Date curDate = new java.sql.Date(System.currentTimeMillis());
		// Выходные графы
		String graph1 = null;
		int graph2 = 0, sum2 = 0, 
			graph3 = 0, sum3 = 0, 
			graph4 = 0, sum4 = 0, 
			graph5 = 0, sum5 = 0, 
			graph6 = 0, sum6 = 0, 
			graph7 = 0, sum7 = 0, 
			graph12 = 0, sum12 = 0, 
			graph13 = 0, sum13 = 0;
		double  graph8 = 0, sum8 = 0, 
				graph9 = 0, sum9 = 0, 
				graph10 = 9, sum10 = 0, 
				graph11 = 0, sum11 = 0;
		// Время в минутах из табеля врача
		double  hpol = 0, 
				hprof = 0, 
				hdom = 0, 
				hproch = 0, 
				hall = 0;
		// Время из норматива n63
		double  pospol = 0, 
				posprof = 0, 
				posdom = 0;
		// Рассчетные переменные
		int fondf = 0, 
			fondr = 0, 
			kzag = 0,
			rasch = 0;

		java.util.Date kpg = null;

		final String sqlQueryBok = 
				  "SELECT DISTINCT ON (a.cod_sp) a.datap, a.dataz, b.pcod, b.fam "
				+ "FROM p_vizit_amb a, s_vrach b WHERE a.cod_sp=b.pcod AND a.cpol=? AND "
				+ dateChange + " BETWEEN ?::date AND ?::date "
				+ "ORDER BY a.cod_sp";
	
		final String sqlQuerySpat = 
				  "SELECT a.cod_sp, a.mobs, a.datap, a.dataz, a.id_obr, a.stoim, a.npasp, "
				+ "b.cobr, b.id, c.id_obr, c.datad, d.npasp, d.ter_liv "
				+ "FROM p_vizit_amb a, p_vizit b, p_diag_amb c, patient d "
				+ "WHERE a.id_obr=b.id AND a.id_obr=c.id_obr AND a.npasp=d.npasp AND "
				+ dateChange + " BETWEEN ?::date AND ?::date " 
				+ "ORDER BY a.cod_sp";

		final String sqlQueryTabel = "SELECT timep, timed, timeprf, timepr, pcod, cdol FROM s_tabel";

		final String sqlQueryN63 = "SELECT pospol, posprof, posdom, codvrdol FROM n_n63";

		String sdfoDate1 = null;
		String sdfoDate2 = null;
		try {
			sdfoDate1 = sdfo.format(sdf.parse(Date1));
			sdfoDate2 = sdfo.format(sdf.parse(Date2));
			// Предыдущий год
			year2 = sdfy.format(sdf.parse(Date2));
			// Текущий год по периоду
			year1 = String.valueOf(Integer.parseInt(year2) - 1);
			// Конец отчетного периода
			kpo = sdf.parse(year1 + "-12-25");
			// Конец года по периоду
			kpg = sdf.parse(year2 + "-12-31");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		try (OutputStreamWriter osw = new OutputStreamWriter(
				new FileOutputStream(path = File.createTempFile("test", ".htm").getAbsolutePath()), "utf-8")) {
			bok = sse.execPreparedQuery(sqlQueryBok, ispa.getCpodr(), sdfoDate1, sdfoDate2);
			StringBuilder sb = new StringBuilder(0x10000);
			// Шапка сводки
			sb.append(String.format("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">"));
			sb.append(String.format("<HTML>"));
			sb.append(String.format("<HEAD>"));
			sb.append(String.format("	<META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\"text/html; charset=utf-8\">"));
			sb.append(String.format("	<TITLE></TITLE>"));
			sb.append(String.format("	<META NAME=\"GENERATOR\" CONTENT=\"LibreOffice 3.6  (Windows)\">"));
			sb.append(String.format("	<META NAME=\"CREATED\" CONTENT=\"20121112;9413876\">"));
			sb.append(String.format("	<META NAME=\"CHANGED\" CONTENT=\"20121112;9503901\">"));
			sb.append(String.format("	<STYLE TYPE=\"text/css\">"));
			sb.append(String.format("	<!--"));
			sb.append(String.format("		@page { size: 29.7cm 21cm; margin: 2cm }"));
			sb.append(String.format("		P { margin-bottom: 0.21cm }"));
			sb.append(String.format("	-->"));
			sb.append(String.format("	</STYLE>"));
			sb.append(String.format("</HEAD>"));
			sb.append(String.format("<BODY LANG=\"ru-RU\" LINK=\"#000080\" VLINK=\"#800000\" DIR=\"LTR\">"));
			// Код формы
			sb.append(String.format("<p align=right>Код формы: %s", kodForm));
			// Текущая дата
			sb.append(String.format("<p align=right>от %s", curDate));
			// Заголовок
			sb.append(String.format("<p align=center> <b>Сведения о структуре посещений и использовании рабочего времени."));
			sb.append(String.format("<p align=center> <b>За период с %s по %s", sdfoDate1, sdfoDate2));
			sb.append(String.format("<p align=center><b>%s %s", ispa.clpu_name, ispa.cpodr_name));
			// Формирование таблицы
			sb.append(String.format("<TABLE WIDTH=986 CELLPADDING=4 CELLSPACING=0>"));
			sb.append(String.format("	<COL WIDTH=66>"));
			sb.append(String.format("	<COL WIDTH=53>"));
			sb.append(String.format("	<COL WIDTH=86>"));
			sb.append(String.format("	<COL WIDTH=48>"));
			sb.append(String.format("	<COL WIDTH=54>"));
			sb.append(String.format("	<COL WIDTH=50>"));
			sb.append(String.format("	<COL WIDTH=72>"));
			sb.append(String.format("	<COL WIDTH=98>"));
			sb.append(String.format("	<COL WIDTH=60>"));
			sb.append(String.format("	<COL WIDTH=74>"));
			sb.append(String.format("	<COL WIDTH=78>"));
			sb.append(String.format("	<COL WIDTH=76>"));
			sb.append(String.format("	<COL WIDTH=65>"));
			sb.append(String.format("	<TR VALIGN=TOP>"));
			sb.append(String.format("		<TD ROWSPAN=3 WIDTH=80 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0.1cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>ВРАЧ</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD COLSPAN=5 WIDTH=323 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0.1cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>Количество обслуженных посещений</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD ROWSPAN=3 WIDTH=72 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0.1cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>С профилак-тической целью</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD ROWSPAN=3 WIDTH=98 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0.1cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>Удельный вес проф. обслуживания</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD COLSPAN=2 WIDTH=142 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0.1cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>Фонд времени (ч.м)</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD ROWSPAN=3 WIDTH=78 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0.1cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>Коэффициент загрузки</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD ROWSPAN=3 WIDTH=76 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0.1cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>Посещения иногородних</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD ROWSPAN=3 WIDTH=65 STYLE=\"border: 1px solid #000000; padding: 0.1cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>Платные посещения</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("	</TR>"));
			sb.append(String.format("	<TR VALIGN=TOP>"));
			sb.append(String.format("		<TD ROWSPAN=2 WIDTH=53 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>Всего</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD ROWSPAN=2 WIDTH=86 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>В поликлинике</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD ROWSPAN=2 WIDTH=48 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>На дому</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD COLSPAN=2 WIDTH=112 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>Из них по поводу заболевания</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD ROWSPAN=2 WIDTH=60 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>расчетный</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD ROWSPAN=2 WIDTH=74 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>фактический</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("	</TR>"));
			sb.append(String.format("	<TR VALIGN=TOP>"));
			sb.append(String.format("		<TD WIDTH=54 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>всего</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=50 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>Первич.</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("	</TR>"));
			sb.append(String.format("	<TR VALIGN=TOP>"));
			sb.append(String.format("		<TD WIDTH=66 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>1</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=53 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>2</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=86 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>3</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=48 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>4</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=54 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>5</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=50 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>6</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=72 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>7</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=98 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>8</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=60 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>9</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=74 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>10</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=78 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>11</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=76 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>12</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=65 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0.1cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>13</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("	</TR>"));
			// Расчет значений
			while (bok.getResultSet().next()) {
				String spec = bok.getResultSet().getString("pcod");
				String fam = bok.getResultSet().getString("fam");
				graph1 = fam;
				graph3 = 0;
				graph4 = 0;
				graph5 = 0;
				graph6 = 0;
				graph9 = 0;
				graph10 = 0;
				graph11 = 0;
				graph12 = 0;
				graph13 = 0;
				graph8 = 0;
				graph2 = 0;
				graph7 = 0;
				try {AutoCloseableResultSet spat = sse.execPreparedQuery(sqlQuerySpat, sdfoDate1, sdfoDate2);
					while (spat.getResultSet().next()) {
						String specSpat = spat.getResultSet().getString("cod_sp");
						Integer mobs = spat.getResultSet().getInt("mobs");
						Integer cobr = spat.getResultSet().getInt("cobr");
						Double stoim = spat.getResultSet().getDouble("stoim");
						Date datad = spat.getResultSet().getDate("datad");
						Integer ter_liv = spat.getResultSet().getInt("ter_liv");
						if (specSpat.equals(spec)) {
							// в поликлинике
							if (mobs == 1) {
								graph3++;
								sum3++;
							}
							// на дому
							if (mobs == 2 || mobs == 3) {
								graph4++;
								sum4++;
							}
							// всего посещений
							graph2 = graph3 + graph4;
							sum2 = sum3 + sum4;
							// по заболеванию
							if (cobr == 2 || cobr == 3 || cobr == 4 || cobr == 5 || cobr == 9 || cobr == 10 || cobr == 11) {
								// всего
								graph5++;
								sum5++;
								// первич.
								if (datad.after(kpo) && datad.before(kpg)) {
									graph6++;
									sum6++;
								}
							}
							// с проф. целью
							if (cobr == 1 || cobr == 7) {
								graph7++;
								sum7++;
							}
							if (graph7 != 0 && graph2 != 0) {
								graph8 = (double) graph7 / graph2;
							}
							// Посещения иногородних
							if (ter_liv != 10) {
								graph12++;
								sum12++;
							}
							// Платные посещения
							if (stoim > 0) {
								graph13++;
								sum13++;
							}
						}
					}
				} catch (SqlExecutorException e1) {
					e1.printStackTrace();
				}

				// Расчет из s_tabel
				try {AutoCloseableResultSet s_tabel = sse.execPreparedQuery(sqlQueryTabel);
					while (s_tabel.getResultSet().next()) {
						String pcodTab = s_tabel.getResultSet().getString("pcod");
						String cdol = s_tabel.getResultSet().getString("cdol");
						if (pcodTab.equals(spec)) {
							hpol = hpol
									+ (int) (s_tabel.getResultSet().getFloat("timep") * 60)
									+ (int) ((s_tabel.getResultSet().getFloat("timep") 
									- (int) s_tabel.getResultSet().getFloat("timep")) * 100);
							hprof = hprof
									+ (int) (s_tabel.getResultSet().getFloat("timeprf") * 60)
									+ (int) ((s_tabel.getResultSet().getFloat("timeprf") 
									- (int) s_tabel.getResultSet().getFloat("timeprf")) * 100);
							hdom = hdom
									+ (int) (s_tabel.getResultSet().getFloat("timed") * 60)
									+ (int) ((s_tabel.getResultSet().getFloat("timed") 
									- (int) s_tabel.getResultSet().getFloat("timed")) * 100);
							hproch = hproch
									+ (int) (s_tabel.getResultSet().getFloat("timepr") * 60)
									+ (int) ((s_tabel.getResultSet().getFloat("timepr") 
									- (int) s_tabel.getResultSet().getFloat("timepr")) * 100);
							// Фактический фонд времени
							graph10 = (int) ((hpol + hprof + hdom) / 60) + (((hpol + hprof + hdom) % 60) / 100);
							// Расчет из n_n63
							try {AutoCloseableResultSet n_n63 = sse.execPreparedQuery(sqlQueryN63);
								while (n_n63.getResultSet().next()) {
									String codvrdol = n_n63.getResultSet().getString("codvrdol").trim();
									pospol = n_n63.getResultSet().getFloat("pospol");
									posdom = n_n63.getResultSet().getFloat("posdom");
									posprof = n_n63.getResultSet().getFloat("posprof");
									if (codvrdol.equals(cdol)) {
										// Расчетный фонд времени
										if (pospol != 0 && posdom != 0) {
											graph9 = (double) graph3 / pospol + (double) graph4 / posdom;
											graph11 = graph9 / graph10;
										}
										if (pospol != 0) {
											graph9 = (double) graph3 / pospol;
											graph11 = graph9 / graph10;
										}
										if (posdom != 0) {
											graph9 = (double) graph4 / posdom;
											graph11 = graph9 / graph10;
										}
									}
								}
							} catch (SqlExecutorException e1) {
								e1.printStackTrace();
							}
						}
					}
				} catch (SqlExecutorException e1) {
					e1.printStackTrace();
				}
				sum9 = sum9 + graph9;
				sum10 = sum10 + graph10;
				sum11 = sum9 / sum10;

				// Вывод значений
				sb.append(String.format("	<TR VALIGN=TOP>"));
				sb.append(String.format("		<TD WIDTH=66 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%s</FONT></P>", graph1));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=53 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%s</FONT></P>", graph2));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=86 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%s</FONT></P>", graph3));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=48 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%s</FONT></P>", graph4));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=54 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%s</FONT></P>", graph5));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=50 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%s</FONT></P>", graph6));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=72 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%s</FONT></P>", graph7));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=98 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%.2f</FONT></P>", graph8));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=60 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%.2f</FONT></P>", graph9));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=74 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%.2f</FONT></P>", graph10));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=78 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%.2f</FONT></P>", graph11));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=76 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%s</FONT></P>", graph12));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("		<TD WIDTH=65 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0.1cm\">"));
				sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%s</FONT></P>", graph13));
				sb.append(String.format("		</TD>"));
				sb.append(String.format("	</TR>"));
			}
			sum8 = (double) sum7 / sum2;
			// Всего:
			sb.append(String.format("	<TR VALIGN=TOP>"));
			sb.append(String.format("		<TD WIDTH=66 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>Всего:</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=53 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%s</FONT></P>", sum2));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=86 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%s</FONT></P>", sum3));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=48 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%s</FONT></P>", sum4));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=54 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%s</FONT></P>", sum5));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=50 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%s</FONT></P>", sum6));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=72 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%s</FONT></P>", sum7));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=98 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%.2f</FONT></P>", sum8));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=60 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%.2f</FONT></P>", sum9));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=74 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%.2f</FONT></P>", sum10));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=78 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%.2f</FONT></P>", sum11));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=76 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%s</FONT></P>", sum12));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=65 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0.1cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT SIZE=2>%s</FONT></P>", sum13));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("	</TR>"));
			// Подвал документа
			sb.append(String.format("</TABLE>"));
			sb.append(String.format("<P STYLE=\"margin-bottom: 0cm\"><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("</BODY>"));
			sb.append(String.format("</HTML>"));
			osw.write(sb.toString());
		} catch (IOException | SQLException e) {
			e.printStackTrace();
		}
		return path;
	}
}
