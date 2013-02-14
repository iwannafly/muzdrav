package ru.nkz.ivcgzo.serverOutputInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor.SqlExecutorException;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOutputInfo.InputAuthInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.InputFacZd;

public class serverFacZd extends serverTemplate {
	
	public serverFacZd(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
	}
	
	public String printFacZd(InputAuthInfo iaf, InputFacZd ifz) throws KmiacServerException,
			TException {
		
		String path = null;
		String bokl = null;
		// Считывает входные значения с формы
		String dateb = ifz.getDateb();
		String datef = ifz.getDatef();
		int vozcat = ifz.getVozcat();
		int kvar = ifz.getKvar();
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
		
	
		final String sqlQuerySpat = "select a.id,a.npasp,a.dataz,a.cpol,b.datar,c.id_obr,c.diag,d.disp " +
				"from p_vizit a, patient b, p_diag_amb c, p_diag d " +
				"where a.npasp=b.npasp and a.id=c.id_obr and c.id=d.id_diag_amb and c.predv!=true and c.diag like 'Z%'" +
				"and a.dataz between '2012-01-01'::date and '2012-12-31'::date order by a.npasp,c.diag,a.dataz";
		final String sqlQueryBok = "select name,diagsrpt from n_az51";
		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("test", ".htm").getAbsolutePath()), "utf-8")) 
		{
			// Код формы, н-р, BIPG14J - для взрослых
			String kodForm = null;
			// Тип сводки, н-р, У детей до 14-и лет
			String vozcatType = null;
			// Квартал, 1 - I квартал и т.д.
			String kvarType = null;
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
					kodForm = "BIPG74J";
					vozcatType = "у детей до 14 лет";
					break; 
			case 2: 
					kodForm = "BIPG64J";
					vozcatType = "у подростков (с 15 до 18 лет)";
					break; 
			case 3: 
					kodForm = "BIPG54J";
					vozcatType = "у взрослых";
					break; 
			//default: 
			}
			
			switch ( kvar ) 
			{ 
			case 1:
					kvarType = "I КВАРТАЛ";
					break; 
			case 2: 
					kvarType = "ПОЛУГОДИЕ";
					break; 
			case 3: 
					kvarType = "III КВАРТАЛ";
					break; 
			case 4: 
					kvarType = "IV КВАРТАЛ";
					break; 
			//default: 
			}
			
			StringBuilder sb = new StringBuilder(0x10000);
			
			//Шапка сводки
			sb.append(String.format("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">"));
			sb.append(String.format("<HTML>"));
			sb.append(String.format("<HEAD>"));
			sb.append(String.format("	<META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\"text/html; charset=utf-8\">"));
			sb.append(String.format("	<TITLE></TITLE>"));
			sb.append(String.format("	<META NAME=\"GENERATOR\" CONTENT=\"LibreOffice 3.5  (Linux)\">"));
			sb.append(String.format("	<META NAME=\"CREATED\" CONTENT=\"20121101;14445900\">"));
			sb.append(String.format("	<META NAME=\"CHANGED\" CONTENT=\"20121101;14542200\">"));
			sb.append(String.format("	<STYLE TYPE=\"text/css\">"));
			sb.append(String.format("	<!--"));
			sb.append(String.format("		@page { size: 21cm 29.7cm; margin: 2cm }"));
			sb.append(String.format("		P { margin-bottom: 0.21cm; text-align: center; page-break-before: auto }"));
			sb.append(String.format("		A:link { color: #000080; so-language: zxx; text-decoration: underline }"));
			sb.append(String.format("		A:visited { color: #800000; so-language: zxx; text-decoration: underline }"));
			sb.append(String.format("	-->"));
			sb.append(String.format("	</STYLE>"));
			sb.append(String.format("</HEAD>"));
			sb.append(String.format("<BODY LANG=\"ru-RU\" LINK=\"#000080\" VLINK=\"#800000\" DIR=\"LTR\">"));
			sb.append(String.format("<P ALIGN=RIGHT STYLE=\"margin-bottom: 0cm\">Код формы: %s</P>",kodForm));
			sb.append(String.format("<P ALIGN=RIGHT STYLE=\"margin-bottom: 0cm\">от %s", curDat));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><B>Факторы,"));
			sb.append(String.format("влияющие на состояние здоровья и"));
			sb.append(String.format(" обращения в учреждения здравоохранения %s</B></P>",vozcatType));
			sb.append(String.format("<P ALIGN=CENTER STYLE=\"margin-bottom: 0cm\">за %s",kvarType));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P ALIGN=CENTER STYLE=\"margin-bottom: 0cm\">%s",iaf.clpu_name));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P ALIGN=CENTER STYLE=\"margin-bottom: 0cm\">%s",iaf.cpodr_name));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P ALIGN=CENTER STYLE=\"margin-bottom: 0cm\">Новокузнецкий "));
			sb.append(String.format(" Горздравотдел</P>"));
			sb.append(String.format("<P STYLE=\"margin-bottom: 0cm\"><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P STYLE=\"margin-bottom: 0cm\"><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("<TABLE WIDTH=643 CELLPADDING=4 CELLSPACING=0>"));
			sb.append(String.format("	<COL WIDTH=309>"));
			sb.append(String.format("	<COL WIDTH=102>"));
			sb.append(String.format("	<COL WIDTH=112>"));
			sb.append(String.format("	<COL WIDTH=86>"));
			sb.append(String.format("	<TR VALIGN=TOP>"));
			sb.append(String.format("		<TD WIDTH=309 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0.1cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\">Наименование"));
			sb.append(String.format("			классов</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=102 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0.1cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER STYLE=\"margin-bottom: 0.5cm\"><FONT FACE=\"Times New Roman, serif\">Шифр"));
			sb.append(String.format("			по </FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("			<P ALIGN=CENTER STYLE=\"margin-bottom: 0.5cm\"><FONT FACE=\"Times New Roman, serif\">МКБ-Х</FONT></P>"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\">пересмотра</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=112 STYLE=\"border-top: 1px solid #000000; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0.1cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\">Обращений"));
			sb.append(String.format("			всего</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=86 STYLE=\"border: 1px solid #000000; padding: 0.1cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\">Состоит"));
			sb.append(String.format("			на диспансерном учете</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("	</TR>"));
			sb.append(String.format("	<TR VALIGN=TOP>"));
			sb.append(String.format("		<TD WIDTH=309 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\">1</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=102 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\">2</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=112 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: none; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\">3</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=86 STYLE=\"border-top: none; border-bottom: 1px solid #000000; border-left: 1px solid #000000; border-right: 1px solid #000000; padding-top: 0cm; padding-bottom: 0.1cm; padding-left: 0.1cm; padding-right: 0.1cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\">4</FONT></P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("	</TR>"));
	
			while (bok.getResultSet().next()) {
				String namebok = bok.getResultSet().getString("name");
				String diagDiap = bok.getResultSet().getString("diagsrpt");
				graph3=0;
				graph4=0;
				graph1=namebok;
				graph2=diagDiap;
							
				try {
					AutoCloseableResultSet spat = sse.execPreparedQuery(sqlQuerySpat);
				
					while (spat.getResultSet().next()) {
						String xdiag = spat.getResultSet().getString("diag");
						//String xdatar = spat.getResultSet().getString("datar");
						int xid = spat.getResultSet().getInt("id");
						int xpasp = spat.getResultSet().getInt("npasp");
						int xdisp = spat.getResultSet().getInt("disp");
						Date xdatar = spat.getResultSet().getDate("datar");
			
							
						if (isIncludesDiag(xdiag,diagDiap)) {
								//Обращений всего
								graph3++;
								// Состоит под диспансерным наблюдением
								if (xdisp==1) graph4++;
							}   // Если первичность равна 2, то больной учитывается с одним заболеванием только один раз
						}	
							
				} catch (SqlExecutorException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}			
			
				
				// Считаем значения
	
				if (graph3!=0 || graph4!=0 )
				{
					sb.append(String.format("	<TR VALIGN=TOP>"));
					sb.append(String.format("		<TD WIDTH=165 HEIGHT=17 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
					sb.append(String.format("			<P ALIGN=LEFT><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">%s</FONT></FONT></P>", graph1 ));
					sb.append(String.format("		</TD>"));
					sb.append(String.format("		<TD WIDTH=64 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
					sb.append(String.format("			<P ALIGN=LEFT><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">%s</FONT></FONT></P>", graph2));
					sb.append(String.format("		</TD>"));
					sb.append(String.format("		<TD WIDTH=59 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
					sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">%s</FONT></FONT></P>", graph3));
					sb.append(String.format("		</TD>"));
					sb.append(String.format("		<TD WIDTH=75 STYLE=\"border: 1px solid #000000; padding: 0.05cm\">"));
					sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman\"><FONT SIZE=2 STYLE=\"font-size: 11pt\">%s</FONT></FONT></P>", graph4));
					sb.append(String.format("		</TD>"));
					sb.append(String.format("	</TR>"));
				}
			}
	
				
			// Подвал документа
			sb.append(String.format("</TABLE>"));
			
			sb.append(String.format("<P><BR><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("</BODY>"));
			sb.append(String.format("</HTML>"));
			
			osw.write(sb.toString());
			
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


}
