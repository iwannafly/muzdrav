package ru.nkz.ivcgzo.clientGenTalons;

import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

import ru.nkz.ivcgzo.thriftGenTalon.Ndv;
import ru.nkz.ivcgzo.thriftGenTalon.Nrasp;
import ru.nkz.ivcgzo.thriftGenTalon.Norm;
import ru.nkz.ivcgzo.thriftGenTalon.Calend;

public class RaspisanieUnit {

	static void NewRaspisanie(int cpodr, int pcod, String cdol, int cxm){
		try {
			List<Nrasp> NraspInf = new ArrayList<Nrasp>();
			if (cxm == 0){
				for (int j=1; j <= 5; j++) { 
				for (int i=1; i <= 5; i++) {
					Nrasp tmpNrasp = new Nrasp();
					tmpNrasp.setCpol(cpodr);
					tmpNrasp.setPcod(pcod);
					tmpNrasp.setCdol(cdol);
					tmpNrasp.setCxema(cxm);
					tmpNrasp.setDenn(i);
					tmpNrasp.setVidp(j);
					tmpNrasp.setTime_n(0);
					tmpNrasp.setTime_k(0);
					NraspInf.add(tmpNrasp);
				}
				}
			}
			if (cxm == 1){
				for (int k=1; k <= 2; k++) { 
				for (int j=1; j <= 5; j++) { 
				for (int i=1; i <= 5; i++) {
					Nrasp tmpNrasp = new Nrasp();
					tmpNrasp.setCpol(cpodr);
					tmpNrasp.setPcod(pcod);
					tmpNrasp.setCdol(cdol);
					tmpNrasp.setCxema(k);
					tmpNrasp.setDenn(i);
					tmpNrasp.setVidp(j);
					tmpNrasp.setTime_n(0);
					tmpNrasp.setTime_k(0);
					NraspInf.add(tmpNrasp);
				}
				}
				}
			}
			MainForm.tcl.addNrasp(NraspInf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	static void NewNorm(int cpodr, String cdol){
		try {
			List<Norm> NormInf = new ArrayList<Norm>();
				for (int i=1; i <= 5; i++) {
					Norm tmpNorm = new Norm();
					tmpNorm.setCpol(cpodr);
					tmpNorm.setCdol(cdol);
					tmpNorm.setVidp(i);
					tmpNorm.setDlit(0);
					NormInf.add(tmpNorm);
				}
			MainForm.tcl.addNorm(NormInf);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	static void CreateTalons(int cpodr, int pcod, String cdol, long datn, long datk, int ind){
		try {
			Calendar cal1 = Calendar.getInstance();
			cal1.setTimeInMillis(datn);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTimeInMillis(datk);
			
			while (!cal1.equals(cal2)) {
				cal1.add(Calendar.DAY_OF_MONTH, 1);
			    System.out.println(Long.toString(cal1.getTimeInMillis()));
				if (getPrrabFromCalendar(cal1.getTimeInMillis())){
					if (ind ==1 ){
						
					}
				}
			}
			
//			for (long idat=datn; idat <= datk; idat++) {
//				if (getPr_rabFromCalendar(idat)){
//					
//				}
//			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static boolean getPrrabFromCalendar(long cdate) {
		try {
			Calend cal = MainForm.tcl.getCalendar(cdate);
			if (cal.isPr_rab())
				return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	private static boolean getPrrabFromNdv(int cpodr, int pcod, String cdol, long cdate) {
		try {
			List<Ndv> ndv = MainForm.tcl.getNdv(cpodr, pcod, cdol);
			if(ndv.size() != 0){
				for (int i=0; i <= ndv.size();) {
					Ndv tmpNdv = new Ndv();
					tmpNdv.setDatak(ndv.get(i).getDatak());
					Calendar cal1 = Calendar.getInstance();
					cal1.setTimeInMillis(tmpNdv.getDatan());
					Calendar cal2 = Calendar.getInstance();
					cal2.setTimeInMillis(tmpNdv.getDatak());
					Calendar cal3 = Calendar.getInstance();
					cal3.setTimeInMillis(cdate);
//					if (){
//						return true;
//					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
