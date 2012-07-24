package ru.nkz.ivcgzo.clientGenTalons;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;

import javax.swing.JOptionPane;

import org.apache.thrift.protocol.TList;

import ru.nkz.ivcgzo.thriftGenTalon.Ndv;
import ru.nkz.ivcgzo.thriftGenTalon.Nrasp;
import ru.nkz.ivcgzo.thriftGenTalon.Norm;
import ru.nkz.ivcgzo.thriftGenTalon.Calend;
import ru.nkz.ivcgzo.thriftGenTalon.Rasp;
import ru.nkz.ivcgzo.thriftGenTalon.Talon;

public class RaspisanieUnit {

	private static List<Nrasp> nrasp;
	private static List<Nrasp> pauselist;
	private static List<Rasp> rasp;
	private static List<Talon> talon;
	private static List<Talon> timelist;
	private static List<Norm> mdlit;
	private static int dlit;
	private static int TalonCount;
	private static long timepause_n = 0;
	private static long timepause_k = 0;
	private static List<Nrasp> nrasp2;

	static void NewRaspisanie(int cpodr, int pcod, String cdol, int cxm){
		try {
			List<Nrasp> NraspInf = new ArrayList<Nrasp>();
			if (cxm == 0){
				for (int j=1; j <= 3; j++) { 
				for (int i=1; i <= 5; i++) {
					Nrasp tmpNrasp = new Nrasp();
					tmpNrasp.setCpol(cpodr);
					tmpNrasp.setPcod(pcod);
					tmpNrasp.setCdol(cdol);
					tmpNrasp.setCxema(cxm);
					tmpNrasp.setDenn(i);
					tmpNrasp.setVidp(j);
					tmpNrasp.setTime_n(Time.valueOf("00:00:00").getTime());
					tmpNrasp.setTime_k(Time.valueOf("00:00:00").getTime());
					tmpNrasp.setTimep_n(Time.valueOf("00:00:00").getTime());
					tmpNrasp.setTimep_k(Time.valueOf("00:00:00").getTime());
					tmpNrasp.setPfd(false);
					NraspInf.add(tmpNrasp);
				}
				}
			}
			if (cxm == 1){
				for (int k=1; k <= 2; k++) { 
				for (int j=1; j <= 3; j++) { 
				for (int i=1; i <= 5; i++) {
					Nrasp tmpNrasp = new Nrasp();
					tmpNrasp.setCpol(cpodr);
					tmpNrasp.setPcod(pcod);
					tmpNrasp.setCdol(cdol);
					tmpNrasp.setCxema(k);
					tmpNrasp.setDenn(i);
					tmpNrasp.setVidp(j);
					tmpNrasp.setTime_n(Time.valueOf("00:00:00").getTime());
					tmpNrasp.setTime_k(Time.valueOf("00:00:00").getTime());
					tmpNrasp.setTimep_n(Time.valueOf("00:00:00").getTime());
					tmpNrasp.setTimep_k(Time.valueOf("00:00:00").getTime());
					tmpNrasp.setPfd(false);
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
			for (int i=1; i <= 3; i++) {
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
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
			Calendar cal1 = Calendar.getInstance();
			cal1.setTimeInMillis(datn);
			Calendar cal2 = Calendar.getInstance();
			cal2.setTimeInMillis(datk);
			cal1.add(Calendar.DAY_OF_MONTH, -1);
			
			if (ind ==1 ){
				MainForm.tcl.deleteRaspCpodr(cal1.getTimeInMillis(), cal2.getTimeInMillis(), cpodr);
				TalonCount = MainForm.tcl.getTalonCountCpodr(cal1.getTimeInMillis(), cal2.getTimeInMillis(), cpodr);
			}
			if (ind ==2 ){
				MainForm.tcl.deleteRaspCdol( cal1.getTimeInMillis(), cal2.getTimeInMillis(), cpodr, cdol);
				TalonCount = MainForm.tcl.getTalonCountCdol(cal1.getTimeInMillis(), cal2.getTimeInMillis(), cpodr, cdol);
			}
			if (ind ==3 ){
				MainForm.tcl.deleteRaspVrach(cal1.getTimeInMillis(), cal2.getTimeInMillis(), cpodr, pcod, cdol);
				TalonCount = MainForm.tcl.getTalonCountVrach(cal1.getTimeInMillis(), cal2.getTimeInMillis(), cpodr, pcod, cdol);
			}
			while (!cal1.equals(cal2)) {
				cal1.add(Calendar.DAY_OF_MONTH, 1);
				if (getPrrabFromCalendar(cal1.getTimeInMillis())){
					if (ind ==1 ){
						nrasp = MainForm.tcl.getNraspCpodr(cpodr);
					}
					if (ind ==2 ){
						nrasp = MainForm.tcl.getNraspCdol(cpodr, cdol);
					}
					if (ind ==3 ){
						nrasp = MainForm.tcl.getNraspVrach(cpodr, pcod, cdol);
					}
					rasp = new ArrayList<Rasp>();
					List<Nrasp> pauselist = new ArrayList<Nrasp>();
					for (int i=0; i <= nrasp.size()-1; i++) {
						if((nrasp.get(i).getCxema() == 0) || 
						(nrasp.get(i).getCxema() == 1 && (cal1.get(Calendar.DAY_OF_MONTH) % 2) == 0)|| 		
						(nrasp.get(i).getCxema() == 2 && (cal1.get(Calendar.DAY_OF_MONTH) % 2) != 0)){
							Rasp tmpRasp = new Rasp();
							tmpRasp.setPcod(nrasp.get(i).getPcod());
							tmpRasp.setDenn(nrasp.get(i).getDenn());
							tmpRasp.setDatap(cal1.getTimeInMillis());
							tmpRasp.setTime_n(nrasp.get(i).getTime_n());
							tmpRasp.setTime_k(nrasp.get(i).getTime_k());
							tmpRasp.setVidp(nrasp.get(i).getVidp());
							tmpRasp.setCpol(nrasp.get(i).getCpol());
							tmpRasp.setCdol(nrasp.get(i).getCdol());
							tmpRasp.setPfd(nrasp.get(i).isPfd());
							if (!tmpRasp.isPfd() && getPrrabFromNdv(tmpRasp.getCpol(), tmpRasp.getPcod(), tmpRasp.getCdol(), tmpRasp.getDatap())) {
								if (tmpRasp.getTime_n() != 0 && tmpRasp.getTime_k() != 0){
									rasp.add(tmpRasp);
									if (nrasp.get(i).getTimep_n() != 0 && nrasp.get(i).getTimep_k() != 0){
										Nrasp tmpPause = new Nrasp();
										tmpPause.setPcod(tmpRasp.getPcod());
										tmpPause.setDenn(tmpRasp.getDenn());
										tmpPause.setCdol(tmpRasp.getCdol());
										tmpPause.setVidp(tmpRasp.getVidp());
										tmpPause.setTimep_n(nrasp.get(i).getTimep_n());
										tmpPause.setTimep_k(nrasp.get(i).getTimep_k());
										pauselist.add(tmpPause);
									}
								}
							}
						}
					}
				}
			}
			if (rasp.size() != 0) {
				MainForm.tcl.addRasp(rasp);
				if (TalonCount != 0){
					int answer = JOptionPane.showConfirmDialog(null, "За период "+sdf.format(new Date(cal1.getTimeInMillis()))+" - "+sdf.format(new Date(cal2.getTimeInMillis()))+" талоны уже сформированы. \n\r Записано пациентов на прием к врачу "+Integer.toString(TalonCount)+"\n\r Формировать талоны заново?",  null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
		            if (answer != JOptionPane.YES_OPTION)  { 
		            	return; 
		            }else{
		    			if (ind ==1 )MainForm.tcl.deleteTalonCpodr(cal1.getTimeInMillis(), cal2.getTimeInMillis(), cpodr);
		    			if (ind ==2 )MainForm.tcl.deleteTalonCdol( cal1.getTimeInMillis(), cal2.getTimeInMillis(), cpodr, cdol);
		    			if (ind ==3 )MainForm.tcl.deleteTalonVrach(cal1.getTimeInMillis(), cal2.getTimeInMillis(), cpodr, pcod, cdol);
						CreateTableTalon(rasp, pauselist);
		            }
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void CreateTableTalon(List<Rasp> rasp, List<Nrasp> pauselist) {
		List<Talon> talonlist = new ArrayList<Talon>();
		for (int i=0; i <= rasp.size()-1; i++) {
			try {
				mdlit = MainForm.tcl.getNorm(rasp.get(i).getCpol(), rasp.get(i).getCdol());
				for (int j=0; j <= mdlit.size()-1; j++) 
					if (rasp.get(i).getVidp() == mdlit.get(j).getDlit()) dlit = mdlit.get(j).getDlit(); 
				timepause_n = 0;
				timepause_k = 0;
				for (int j=0; j <= pauselist.size()-1; j++)
					if (rasp.get(i).getPcod()==pauselist.get(j).getPcod() && 
						rasp.get(i).getDenn()==pauselist.get(j).getDenn() &&
						rasp.get(i).getCdol()==pauselist.get(j).getCdol() &&
						rasp.get(i).getVidp()==pauselist.get(j).getVidp()){
						timepause_n = pauselist.get(j).getTimep_n();
						timepause_k = pauselist.get(j).getTimep_k();
						break;
					}
				if (dlit != 0){
					Talon tmpTalon = new Talon();
					tmpTalon.setCpol(rasp.get(i).getCpol());
					tmpTalon.setPcod_sp(rasp.get(i).getPcod());
					tmpTalon.setCdol(rasp.get(i).getCdol());
					tmpTalon.setVidp(rasp.get(i).getVidp());
					tmpTalon.setTimepn(rasp.get(i).getTime_n());
					tmpTalon.setTimepk(rasp.get(i).getTime_k());
					tmpTalon.setDatap(rasp.get(i).getDatap());
					if (timepause_n==0 && timepause_k==0){
						List<Talon> timelist = getTalonTime(tmpTalon.getTimepn(), tmpTalon.getTimepk(), dlit);
					}else{
						List<Talon> timelist = getTalonTime(tmpTalon.getTimepn(), tmpTalon.getTimepk(), dlit, timepause_n, timepause_k);
					}
					for (int j=0; j <= timelist.size()-1; j++){
						tmpTalon.setTimep(timelist.get(j).getTimep());
						tmpTalon.setDatapt(tmpTalon.getDatap() + tmpTalon.getTimep());
						talonlist.add(tmpTalon);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		try {
			MainForm.tcl.addTalons(talonlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
    private static List<Talon> getTalonTime(long timepn, long timepk, int dlitp) {
		List<Talon> timelist = new ArrayList<Talon>();
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.add(Calendar.HOUR_OF_DAY, -dlitp);
		while (!cal1.equals(cal2)) {
			cal1.add(Calendar.HOUR_OF_DAY, dlitp);
			Talon tmpTime = new Talon();
			tmpTime.setTimep(cal1.getTimeInMillis());
			timelist.add(tmpTime);
		}
		return timelist;
	}

	private static List<Talon> getTalonTime(long timepn, long timepk, int dlitp,
			long timepausen, long timepausek) {
		List<Talon> timelist = new ArrayList<Talon>();
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		Calendar cal3 = Calendar.getInstance();
		Calendar cal4 = Calendar.getInstance();
		cal1.setTimeInMillis(timepn);
		cal2.setTimeInMillis(timepk);
		cal3.setTimeInMillis(timepausen);
		cal4.setTimeInMillis(timepausek);
		cal1.add(Calendar.HOUR_OF_DAY, -dlitp);

		while (!cal1.equals(cal2)) {
			cal1.add(Calendar.HOUR_OF_DAY, dlitp);
			if ((cal1.compareTo(cal3)>0 && cal1.compareTo(cal4)<0) || (cal1.compareTo(cal3)==0)){
			}else{
				Talon tmpTime = new Talon();
				tmpTime.setTimep(cal1.getTimeInMillis());
				timelist.add(tmpTime);
			}
		}
		return timelist;
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
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
			List<Ndv> ndv = MainForm.tcl.getNdv(cpodr, pcod, cdol);
			if(ndv.size() != 0){
				for (int i=0; i <= ndv.size()-1; i++) {
					Ndv tmpNdv = new Ndv();
					tmpNdv.setDatan(ndv.get(i).getDatan());
					tmpNdv.setDatak(ndv.get(i).getDatak());
					Calendar cal1 = Calendar.getInstance();
					cal1.setTimeInMillis(tmpNdv.getDatan());
					Calendar cal2 = Calendar.getInstance();
					cal2.setTimeInMillis(tmpNdv.getDatak());
					Calendar cal3 = Calendar.getInstance();
					cal3.setTimeInMillis(cdate);
//					System.out.println(sdf.format(new Date(cal1.getTimeInMillis())));
//					System.out.println(sdf.format(new Date(cal2.getTimeInMillis())));
//					System.out.println(sdf.format(new Date(cal3.getTimeInMillis())));
//					if (cal1.after(cal3.getTimeInMillis()) && cal1.before(cal3.getTimeInMillis())){
					if ((cal3.compareTo(cal1)>0 && cal3.compareTo(cal2)<0) || (cal3.compareTo(cal1)==0 || cal3.compareTo(cal2)==0)){
						return false;
					}
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
}
