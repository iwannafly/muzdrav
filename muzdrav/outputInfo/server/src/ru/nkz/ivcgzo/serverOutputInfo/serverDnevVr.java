package ru.nkz.ivcgzo.serverOutputInfo;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.sql.SQLException;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;

public class serverDnevVr extends serverTemplate {
	private ISqlSelectExecutor sse;
	ITransactedSqlExecutor tse;
	
	public serverDnevVr (ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		this.sse = sse;
		this.tse = tse;
	}
	

	public String printDnevVr() throws KmiacServerException, TException {
		AutoCloseableResultSet acrs = null, acrs2 = null;
		Date data = null;
		Date data1 = null;
		
		String path;
		
		try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(path = File.createTempFile("kart1", ".htm").getAbsolutePath()), "utf-8")) {
	
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
				sb.append("<TD colspan=4 align=center>Посещения всего</TD>");
	//			sb.append("<TD rowspan=2 align=center>Подпись врача</TD>");
				sb.append("</TR>");
				sb.append("<TR>");
				sb.append("<TD>план</TD>");
				sb.append("<TD>факт</TD>");
				sb.append("<TD>процент выполнения плана</TD>");
				sb.append("</TR>");
				sb.append("<TR>");
				sb.append("</TR>");
				sb.append("<TR>");
				sb.append("<TD>план</TD>");
				sb.append("<TD>факт</TD>");
				sb.append("<TD>процент выполнения плана</TD>");
				sb.append("</TR>");
				sb.append("<TR>");
				sb.append("</TR>");
				sb.append("<TR>");
				sb.append("<TD>план</TD>");
				sb.append("<TD>факт</TD>");
				sb.append("<TD>процент выполнения плана</TD>");
				sb.append("</TR>");
				sb.append("<TR>");
				sb.append("</TR>");
				sb.append("<TR>");
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
				double ipp = 0; Integer ipf = 0; 
				Integer codvr = 0; Integer codpol = 0;
	
				acrs = sse.execPreparedQuery("select count(*),a.cdol,a.mobs,a.opl,a.cpos,v.cobr,(v.datao-p.datar)/365.25,p.pol,s.fam,s.im,s.ot,p.jitel,v.id,c0.name,v.cpol,v.datao,a.cod_sp "+
	//                                                     1       2      3     4      5      6                        7     8      9   10   11     12   13       14    15      16       17
				"from p_vizit_amb a,p_vizit v,patient p,s_vrach s,n_s00 c0 "+
	"where a.id_obr=v.id and a.npasp=p.npasp and a.cod_sp=s.pcod and a.cdol=c0.pcod "+
	"group by a.id_obr,a.cdol,c0.name,a.mobs,a.opl,a.cpos,v.cpol,v.cobr,v.datao,(v.datao-p.datar)/365.25,p.pol,s.fam,s.im,s.ot,v.id,p.jitel,a.cod_sp "+
	"order by a.cod_sp,s.fam,s.im,s.ot,a.cdol,a.id_obr,v.id,p.jitel");
				if (acrs.getResultSet().next()) {
	            codvr = acrs.getResultSet().getInt(17);
				while (acrs.getResultSet().next()){
				if (codvr == acrs.getResultSet().getInt(17)){
				if(acrs.getResultSet().getInt(3)==1) {ppf = ppf + acrs.getResultSet().getInt(1);
				ippf = ippf + acrs.getResultSet().getInt(1);}
				if(acrs.getResultSet().getInt(3)==2) {pdf = pdf + acrs.getResultSet().getInt(1);
				ipdf = ipdf + acrs.getResultSet().getInt(1);}
				if(acrs.getResultSet().getInt(6)!=1) {ppfp = ppfp + acrs.getResultSet().getInt(1);
				ippfp = ippfp + acrs.getResultSet().getInt(1);}
				pf = pf + acrs.getResultSet().getInt(1);
				ipf = ipf + acrs.getResultSet().getInt(1);
				}
				}
				n1 = n1 + 1;
				//посчитать процент
				acrs2 = sse.execPreparedQuery("select pospol*prpol,posprof*prprof,posdom*prdom,rabden,koldn,colst "+
	//                                                              1              2            3       4    5     6  
				"from n_n63 where codpol=? and codvrdol=? ",codpol,acrs.getResultSet().getString(2));
				if (acrs2.getResultSet().next()) {
				ppp = acrs2.getResultSet().getDouble(1);
				pdp = acrs2.getResultSet().getDouble(3);
				ppfp = acrs2.getResultSet().getDouble(2);
				}
				acrs2.close();
				sb.append(String.format("<td> %d/TD>",n1));
				sb.append(String.format("<td> %s %s %s</TD>",acrs.getResultSet().getString(9),acrs.getResultSet().getString(10),acrs.getResultSet().getString(11)));
				sb.append(String.format("<TD> %s</TD>",acrs2.getResultSet().getString(2)));
				acrs2 = sse.execPreparedQuery("select sum(timep),sum(timed),sum(timeda),sum(timeprf),sum(timepr) from s_tabel where pcod = ?",codvr);
				if (acrs.getResultSet().next()) {
				sb.append(String.format("<TD>%.2f </TD>",(acrs2.getResultSet().getDouble(1)+acrs2.getResultSet().getDouble(2)+acrs2.getResultSet().getDouble(3)+acrs2.getResultSet().getDouble(4))));
	//			sb.append(String.format("<TD>%.2f ставок</TD>",acrs2.getResultSet().getInt(9),acrs2.getResultSet().getInt(10)));
				sb.append(String.format("<TD>%.2f </TD>",(acrs2.getResultSet().getDouble(1)*ppp)));//план в поликлинике
				ippp = ippp+acrs2.getResultSet().getDouble(1)*ppp;
				pp = pp+acrs2.getResultSet().getDouble(1)*ppp;
				ipp = ipp+acrs2.getResultSet().getDouble(1)*ppp;
				    sb.append(String.format("<TD> %d</TD>",ppf));//факт в поликлинике
				proc= ppf*100/(acrs2.getResultSet().getDouble(1)*ppp);
				sb.append(String.format("<TD> %.2f</TD>",proc));//процент
				sb.append(String.format("<TD>%.2f </TD>",(acrs2.getResultSet().getDouble(2)*pdp)));//план на дому
				ipdp = ipdp+acrs2.getResultSet().getDouble(2)*pdp;
				pp = pp+acrs2.getResultSet().getDouble(2)*pdp;
				ipp = ipp+acrs2.getResultSet().getDouble(2)*pdp;
				    sb.append(String.format("<TD> %d</TD>",pdf));//факт на дому
				proc= pdf*100/(acrs2.getResultSet().getDouble(2)*pdp);
				sb.append(String.format("<TD> %.2f</TD>",proc));//процент
				sb.append(String.format("<TD>%.2f </TD>",(acrs2.getResultSet().getDouble(4)*ppfp)));//план профцель
				ipdp = ipdp+acrs2.getResultSet().getDouble(4)*ppfp;
				pp = pp+acrs2.getResultSet().getDouble(4)*ppfp;//что с прочими?
				ipp = ipp+acrs2.getResultSet().getDouble(4)*ppfp;//что с прочими?
			    sb.append(String.format("<TD> %d</TD>",ppff));//факт профцель
				proc= ppff*100/(acrs2.getResultSet().getDouble(4)*ppfp);
				sb.append(String.format("<TD> %.2f</TD>",proc));//процент
				sb.append(String.format("<TD>%.2f </TD>",pp));//план всего
			    sb.append(String.format("<TD> %d</TD>",pf));//факт всего
				proc= pf*100/(acrs2.getResultSet().getDouble(4)*pp);
				sb.append(String.format("<TD> %.2f</TD>",proc));//процент
				}
				acrs2.close();
				sb.append("<TD> </TD>");
				sb.append("</TR>");
				pp=0; ppf=0; pdf=0; ppff=0;
				}
				acrs.close();
				acrs2 = sse.execPreparedQuery("select sum(pospol*prpol*colst),sum(posprof*prprof*colst),sum(posdom*prdom*colst),rabden,koldn "+ 
						"from n_n63 where codpol=? group by rabden,koldn ",codpol);
				if (acrs2.getResultSet().next()) {
	//разместить строку ИТОГО	
					sb.append("<td> /TD>");
					sb.append("<td> ИТОГО</TD>");
					sb.append("<TD> </TD>");
					sb.append(String.format("<TD>%.2f </TD>",(acrs2.getResultSet().getDouble(1)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500)));//план в поликлинике
	 			    sb.append(String.format("<TD> %d</TD>",ippf));//факт в поликлинике
					proc= ppf*100/((acrs2.getResultSet().getDouble(1)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500));
					sb.append(String.format("<TD> %.2f</TD>",proc));//процент
					sb.append(String.format("<TD>%.2f </TD>",((acrs2.getResultSet().getDouble(2)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500))));//план на дому
	 			    sb.append(String.format("<TD> %d</TD>",ipdf));//факт на дому
					proc= pdf*100/((acrs2.getResultSet().getDouble(2)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500));
					sb.append(String.format("<TD> %.2f</TD>",proc));//процент
					sb.append(String.format("<TD>%.2f </TD>",((acrs2.getResultSet().getDouble(3)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500))));//план профцель
				    sb.append(String.format("<TD> %d</TD>",ippff));//факт профцель
					proc= ppff*100/((acrs2.getResultSet().getDouble(3)*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500));
					sb.append(String.format("<TD> %.2f</TD>",proc));//процент
					ipp = (acrs2.getResultSet().getDouble(1)+acrs2.getResultSet().getDouble(2)+acrs2.getResultSet().getDouble(3))*acrs2.getResultSet().getDouble(4)*acrs2.getResultSet().getDouble(5)/36500;
					sb.append(String.format("<TD>%.2f </TD>",ipp));//план всего
				    sb.append(String.format("<TD> %d</TD>",ipf));//факт всего
					proc= pf*100/ipp;
					sb.append(String.format("<TD> %.2f</TD>",proc));//процент
				}
				sb.append("</TABLE>");
			sb.append("</body>"); 
			sb.append("</html>");
			
			osw.write(sb.toString());
			return path;
		} catch (SQLException e) {
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		} catch (IOException e) {
			e.printStackTrace();
			throw new KmiacServerException();
		} finally {
			if (acrs != null)
				acrs.close();
			if (acrs2 != null)
				acrs2.close();
		}
	//		return null;
	}

}
