package ru.nkz.ivcgzo.serverOutputInfo;

import java.sql.Date;
import java.text.SimpleDateFormat;

import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;

public class serverTemplate {
	protected ISqlSelectExecutor sse;
	protected ITransactedSqlExecutor tse;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	SimpleDateFormat sdfo = new SimpleDateFormat("dd.MM.yyyy");
	SimpleDateFormat sdfy = new SimpleDateFormat("yyyy");
	// Текущая дата в формате 12.12.2012
	String curDat = sdfo.format(java.util.Calendar.getInstance().getTime()); 
	
	public serverTemplate(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		this.sse = sse;
		this.tse = tse;
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
