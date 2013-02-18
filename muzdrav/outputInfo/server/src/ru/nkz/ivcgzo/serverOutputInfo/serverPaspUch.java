package ru.nkz.ivcgzo.serverOutputInfo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOutputInfo.InputAuthInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.InputPasUch;

public class serverPaspUch extends serverTemplate {
	
	public serverPaspUch(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
	}
	
	public String printPasUch(InputAuthInfo iaf, InputPasUch ipu) throws KmiacServerException, TException {
		String path = null;
		// Считывает входные значения с формы
		// Начало и конец периода
		String dateb = ipu.getDateb();
		String datef = ipu.getDatef();
		
		String uchTypeName = null;
		String uchFormName = null;
		String uchAgencyName = null;
		AutoCloseableResultSet arcs = null;
		boolean isChildHosp = false;
		
		String sdfoDateB = null;
		String sdfoDateF = null;
		// Номер участка
		int unum = ipu.getUchnum();
		
		// Запросы
		// Название подразделения / лпу
		String uchAgencyNameQuery = "select name_u from n_n00 where pcod = ?";
		// 1 - взрослое учреждение, 2 - детское
		String uchTypeQuery = "select c_nom from n_m00 where pcod= ?";
		
		
		try {
			 arcs = sse.execPreparedQuery(uchAgencyNameQuery,iaf.getCpodr());
			 
			 arcs.getResultSet().next();
			 
			 uchAgencyName = arcs.getResultSet().getString(1);
			 
			//spat = sse.execPreparedQuery(sqlQuerySpat ,dateb, datef);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		try {
			 arcs = sse.execPreparedQuery(uchTypeQuery,iaf.getCpodr());
			 
			 arcs.getResultSet().next();
			 
			 if (arcs.getResultSet().getInt(1) == 2) {
				 isChildHosp = true;
				 uchTypeName = "ПЕДИАТРИЧЕСКОГО";
				 uchFormName = "пед";
			 }
			 	else {
			 	 isChildHosp = false;
			 	 uchTypeName = "ТЕРАПЕВТИЧЕСКОГО";
			 	uchFormName = "тер";
			 }
			 
			//spat = sse.execPreparedQuery(sqlQuerySpat ,dateb, datef);
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		try {
			sdfoDateB = sdfo.format(sdf.parse(dateb));
			sdfoDateF = sdfo.format(sdf.parse(datef));
			// Предыдущий год
			
		} catch (ParseException e) {
		//	// TODO Auto-generated catch block
			e.printStackTrace();
		}				
		
		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("test", ".htm").getAbsolutePath()), "utf-8")) {
			
			StringBuilder sb = new StringBuilder(0x10000);
			
			// Титульный лист
			sb.append(String.format("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">"));
			sb.append(String.format("<HTML>"));
			sb.append(String.format("<HEAD>"));
			sb.append(String.format("	<STYLE TYPE=\"text/css\">"));
			sb.append(String.format("	<!--"));
			sb.append(String.format("		@page { size: 29.7cm 21cm; margin-left: 2cm; margin-right: 1.06cm; margin-top: 2.12cm; margin-bottom: 1.76cm }"));
			sb.append(String.format("		P { margin-bottom: 0.21cm; direction: ltr; color: #000000; widows: 2; orphans: 2 }"));
			sb.append(String.format("		P.western { font-family: \"Times New Roman\", serif; font-size: 12pt; so-language: ru-RU }"));
			sb.append(String.format("		P.cjk { font-family: \"Times New Roman\", serif; font-size: 12pt }"));
			sb.append(String.format("		P.ctl { font-family: \"Times New Roman\", serif; font-size: 12pt; so-language: ar-SA }"));
			sb.append(String.format("		A:link { color: #000080; so-language: zxx; text-decoration: underline }"));
			sb.append(String.format("		A:visited { color: #800000; so-language: zxx; text-decoration: underline }"));
			sb.append(String.format("	-->"));
			sb.append(String.format("	</STYLE>"));
			sb.append(String.format("</HEAD>"));
			sb.append(String.format("<BODY LANG=\"ru-RU\" TEXT=\"#000000\" LINK=\"#000080\" VLINK=\"#800000\" DIR=\"LTR\">"));
			sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><FONT SIZE=2>Министерство здравоохраниеня Российской Федерации</FONT></P>"));
			sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><FONT SIZE=2>распечатано из МИС ИнфоМуЗдрав</FONT></P>"));
	        sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><FONT SIZE=2>%s</FONT></P>",uchAgencyName));
			sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><FONT SIZE=2>Медицинская документация</FONT></P>"));
			sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><FONT SIZE=2>Форма № 030/у-%s</FONT></P>",uchFormName));
			sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><FONT SIZE=2>утвержден приказом Минздравсоцразвития России</FONT></P>"));
			sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><FONT SIZE=2>от __________ №_______________________</FONT></P>"));
			sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P CLASS=\"western\" ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><FONT SIZE=2><B>ПАСПОРТ ВРАЧЕБНОГО УЧАСТКА № %s</B></FONT></P>",unum));
			sb.append(String.format("<P CLASS=\"western\" ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><FONT SIZE=2><B>(%s)</B></FONT></P>",uchTypeName));
			sb.append(String.format("<P CLASS=\"western\" ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P CLASS=\"western\" ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P CLASS=\"western\" ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><FONT SIZE=2>"));
			sb.append(String.format("ЗА ПЕРИОД С %s ПО %s</FONT></P>",sdfoDateB,sdfoDateF));
			sb.append(String.format("<P CLASS=\"western\" ALIGN=CENTER STYLE=\"margin-bottom: 0cm\"><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><FONT SIZE=2><B>I. Характеристика врачебного участка</B></FONT></P>"));
			sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><FONT SIZE=2>Численность прикрепленного населения ZZZ человек</FONT></P>"));
			sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><BR>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P CLASS=\"western\" STYLE=\"margin-bottom: 0cm\"><FONT SIZE=2>Местонахождение: г. Новокузнецк</FONT></P>"));

			// Паспорт педиатрического участка
			if (isChildHosp) {
				
			
			// Численность детского населения по годам рождения
			sb.append(String.format("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.0 Transitional//EN\">"));
			sb.append(String.format("<HTML>"));
			sb.append(String.format("<HEAD>"));
			sb.append(String.format("	<META HTTP-EQUIV=\"CONTENT-TYPE\" CONTENT=\"text/html; charset=utf-8\">"));
			sb.append(String.format("	<TITLE></TITLE>"));
			sb.append(String.format("	<META NAME=\"GENERATOR\" CONTENT=\"LibreOffice 3.5  (Linux)\">"));
			sb.append(String.format("	<META NAME=\"CREATED\" CONTENT=\"20130128;13332500\">"));
			sb.append(String.format("	<META NAME=\"CHANGED\" CONTENT=\"20130204;13211500\">"));
			sb.append(String.format("	<STYLE TYPE=\"text/css\">"));
			sb.append(String.format("	<!--"));
			sb.append(String.format("		@page { size: 29.7cm 21cm; margin: 2cm }"));
			sb.append(String.format("		P { margin-bottom: 0.21cm }"));
			sb.append(String.format("		H3 { margin-bottom: 0.21cm; page-break-after: avoid }"));
			sb.append(String.format("		H3.western { font-family: \"Linux Libertine\", sans-serif; font-size: 14pt; font-weight: bold }"));
			sb.append(String.format("		H3.cjk { font-family: \"Droid Sans Fallback\"; font-size: 14pt; font-weight: bold }"));
			sb.append(String.format("		H3.ctl { font-family: \"unifont\"; font-size: 14pt; font-weight: bold }"));
			sb.append(String.format("		A:link { color: #000080; so-language: zxx; text-decoration: underline }"));
			sb.append(String.format("		A:visited { color: #800000; so-language: zxx; text-decoration: underline }"));
			sb.append(String.format("	-->"));
			sb.append(String.format("	</STYLE>"));
			sb.append(String.format("</HEAD>"));
			sb.append(String.format("<BODY LANG=\"ru-RU\" LINK=\"#000080\" VLINK=\"#800000\" DIR=\"LTR\">"));
			sb.append(String.format("<H3 CLASS=\"western\" ALIGN=CENTER>II раздел. Характеристика"));
			sb.append(String.format("прикрепленного контингента "));
			sb.append(String.format("</H3>"));
			sb.append(String.format("<P ALIGN=RIGHT><A NAME=\"e1926\"></A><I>Таблица N 1 </I>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("<P ALIGN=CENTER><A NAME=\"2c03a\"></A><B>Численность"));
			sb.append(String.format("детского населения по годам рождения </B>"));
			sb.append(String.format("</P>"));
			sb.append(String.format("<TABLE WIDTH=100% CELLPADDING=2 CELLSPACING=0>"));
			sb.append(String.format("	<COL WIDTH=8*>"));
			sb.append(String.format("	<COL WIDTH=21*>"));
			sb.append(String.format("	<COL WIDTH=16*>"));
			sb.append(String.format("	<COL WIDTH=29*>"));
			sb.append(String.format("	<COL WIDTH=25*>"));
			sb.append(String.format("	<COL WIDTH=23*>"));
			sb.append(String.format("	<COL WIDTH=13*>"));
			sb.append(String.format("	<COL WIDTH=15*>"));
			sb.append(String.format("	<COL WIDTH=26*>"));
			sb.append(String.format("	<COL WIDTH=10*>"));
			sb.append(String.format("	<COL WIDTH=12*>"));
			sb.append(String.format("	<COL WIDTH=12*>"));
			sb.append(String.format("	<COL WIDTH=12*>"));
			sb.append(String.format("	<COL WIDTH=12*>"));
			sb.append(String.format("	<COL WIDTH=21*>"));
			sb.append(String.format("	<TR VALIGN=TOP>"));
			sb.append(String.format("		<TD ROWSPAN=2 WIDTH=3% STYLE=\"border-top: 1px solid #808080; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0.05cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><A NAME=\"cd6b8\"></A><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>N"));
			sb.append(String.format("			п/п </FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD ROWSPAN=2 WIDTH=8% STYLE=\"border-top: 1px solid #808080; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0.05cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>ФИО"));
			sb.append(String.format("			ребенка </FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD ROWSPAN=2 WIDTH=6% STYLE=\"border-top: 1px solid #808080; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0.05cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>Дата"));
			sb.append(String.format("			рождения </FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD ROWSPAN=2 WIDTH=11% STYLE=\"border-top: 1px solid #808080; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0.05cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>Адрес"));
			sb.append(String.format("			места жительства, телефон </FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD ROWSPAN=2 WIDTH=10% STYLE=\"border-top: 1px solid #808080; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0.05cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>Наименование"));
			sb.append(String.format("			страховой медицинской организации </FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD ROWSPAN=2 WIDTH=9% STYLE=\"border-top: 1px solid #808080; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0.05cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>N"));
			sb.append(String.format("			страхового полиса ОМС </FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD COLSPAN=2 WIDTH=11% STYLE=\"border-top: 1px solid #808080; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0.05cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>Прибыл"));
			sb.append(String.format("			на врачебный участок </FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD COLSPAN=2 WIDTH=14% STYLE=\"border-top: 1px solid #808080; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0.05cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>Посещает"));
			sb.append(String.format("			образовательное учреждение </FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD COLSPAN=2 WIDTH=10% STYLE=\"border-top: 1px solid #808080; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0.05cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>Численность"));
			sb.append(String.format("			детского населения </FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD COLSPAN=2 WIDTH=10% STYLE=\"border-top: 1px solid #808080; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0.05cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>Убыл"));
			sb.append(String.format("			с врачебного участка </FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD ROWSPAN=2 WIDTH=8% STYLE=\"border: 1px solid #808080; padding: 0.05cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>Примечания"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("	</TR>"));
			sb.append(String.format("	<TR VALIGN=TOP>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>дата"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=6% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>откуда"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=10% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>наименование"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=4% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>N"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>на"));
			sb.append(String.format("			01.04. </FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>на"));
			sb.append(String.format("			01.11. </FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>дата"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>куда"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("	</TR>"));
			// Расчет значений
			sb.append(String.format("	<TR VALIGN=TOP>"));
			sb.append(String.format("		<TD WIDTH=3% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>1"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=8% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>2"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=6% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>3"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=11% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>4"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=10% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>5"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=9% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>6"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>7"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=6% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>8"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=10% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>9"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=4% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>10"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>11"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>12"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>13"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>14"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=8% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: 1px solid #808080; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0.05cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>15"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("	</TR>"));
			sb.append(String.format("	<TR VALIGN=TOP>"));
			sb.append(String.format("		<TD WIDTH=3% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P><BR>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=8% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P><BR>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=6% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P><BR>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=11% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P><BR>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=10% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P><BR>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=9% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P><BR>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P><BR>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=6% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P><BR>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=10% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P><BR>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=4% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P><BR>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>X"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>X"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P><BR>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P><BR>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=8% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: 1px solid #808080; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0.05cm\">"));
			sb.append(String.format("			<P><BR>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("	</TR>"));
			// Суммирование
			sb.append(String.format("	<TR VALIGN=TOP>"));
			sb.append(String.format("		<TD WIDTH=3% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P><BR>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=8% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>Всего"));
			sb.append(String.format("			детей на участке </FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=6% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>X"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=11% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>X"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=10% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>X"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=9% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>X"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>X"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=6% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>X"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=10% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>X"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=4% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>X"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P><BR>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P><BR>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>X"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=5% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: none; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0cm\">"));
			sb.append(String.format("			<P ALIGN=CENTER><FONT FACE=\"Times New Roman, serif\"><FONT SIZE=2>X"));
			sb.append(String.format("			</FONT></FONT>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("		<TD WIDTH=8% STYLE=\"border-top: none; border-bottom: 1px solid #808080; border-left: 1px solid #808080; border-right: 1px solid #808080; padding-top: 0cm; padding-bottom: 0.05cm; padding-left: 0.05cm; padding-right: 0.05cm\">"));
			sb.append(String.format("			<P><BR>"));
			sb.append(String.format("			</P>"));
			sb.append(String.format("		</TD>"));
			sb.append(String.format("	</TR>"));
			sb.append(String.format("</TABLE>"));
			sb.append(String.format("<HR>"));
			}

			//Подвал документа
			sb.append(String.format("</BODY>"));
			sb.append(String.format("</HTML>"));
			
			osw.write(sb.toString());

			
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		return path;
		
	}

}
