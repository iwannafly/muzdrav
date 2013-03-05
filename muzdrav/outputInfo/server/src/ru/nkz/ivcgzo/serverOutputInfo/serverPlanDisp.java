package ru.nkz.ivcgzo.serverOutputInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.SQLException;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOutputInfo.InputPlanDisp;

public class serverPlanDisp extends serverTemplate {
	public serverPlanDisp (ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
	}
	

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
			
			zapznach.getResultSet().next();
			
			poldv = zapznach.getResultSet().getInt("c_nom");
			
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		String sqlQueryPlanDis = "select pn.nambk, (p.fam||' '||p.im||' '||p.ot) as fio, p.datar, p.adm_ul,p.adm_dom,p.adm_korp," +
				"p.adm_kv,	pm.diag, na.name, pm.pdat, pn.nuch, pd.d_grup, pm.pdat, pd.d_uch, pm.cod_sp, pm.cpol,pm.fdat, pd.ishod " +
				"from patient p join p_nambk pn on(p.npasp = pn.npasp) join p_mer pm on(p.npasp =pm.npasp) " +
				"join p_disp pd on(p.npasp = pd.npasp) join n_abd na on(pm.pmer = na.pcod) " +
				"where (pm.pdat between '"+ dn+"' and '"+ dk+"')and(pd.diag = pm.diag)and(pm.fdat is null)and(pd.ishod is null) and(pn.dataot is null)" +
						"and(pm.cpol = "+kodpol+")";
		if (uc.length() > 0) sqlQueryPlanDis = sqlQueryPlanDis+"and(pd.d_uch ="+uc+")";
		if (poldv == 1){
			sqlQueryPlanDis = sqlQueryPlanDis + "Order by pm.cod_sp, pd.d_uch, pm.cpol, p.fam, p.im, p.ot, p.datar";
			}else{
				sqlQueryPlanDis = sqlQueryPlanDis + "Order by pd.d_uch, pm.cpol, p.fam, p.im, p.ot, p.datar";
			
		}
		
		System.out.println(sqlQueryPlanDis);
		
		
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
		
			spat.getResultSet().next();
			
			int spuch = 0;
			int spuch1 = 0;
			
			if (poldv == 1){
				spuch = Integer.parseInt(spat.getResultSet().getString("cod_sp")+"0"+ spat.getResultSet().getString("d_uch"));
				spuch1 = Integer.parseInt(spat.getResultSet().getString("cod_sp")+"0"+ spat.getResultSet().getString("d_uch"));
			}else{
				spuch = Integer.parseInt(spat.getResultSet().getString("d_uch"));
				spuch1 = Integer.parseInt(spat.getResultSet().getString("d_uch"));
				
			}
			
			sb.append(String.format(ZagShap(dn,dk,namepol,spuch)));
			String adres = null; 
			
			while (spat.getResultSet().next()){
				
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
	
}
