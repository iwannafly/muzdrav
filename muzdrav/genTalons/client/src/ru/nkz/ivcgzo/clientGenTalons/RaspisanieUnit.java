package ru.nkz.ivcgzo.clientGenTalons;

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

public class RaspisanieUnit {

	private static List<Nrasp> nrasp;
	private static List<Rasp> rasp;
	private static int TalonCount;

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
					tmpNrasp.setPfd(true);
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
					tmpNrasp.setPfd(true);
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
						List<Nrasp> nrasp = MainForm.tcl.getNraspCpodr(cpodr);
					}
					if (ind ==2 ){
						List<Nrasp> nrasp = MainForm.tcl.getNraspCdol(cpodr, cdol);
					}
					if (ind ==3 ){
						List<Nrasp> nrasp = MainForm.tcl.getNraspVrach(cpodr, pcod, cdol);
					}
					List<Rasp> rasp = new ArrayList<Rasp>();
					List<Rasp> pauselist = new ArrayList<Rasp>();
					for (int i=0; i <= nrasp.size()-1; i++) {
						Rasp tmpRasp = new Rasp();
						Rasp tmpPause = new Rasp();
						tmpRasp.setPcod(nrasp.get(i).getPcod());
						tmpRasp.setDenn(nrasp.get(i).getDenn());
						tmpRasp.setDatap(cal1.getTimeInMillis());
						tmpRasp.setTime_n(nrasp.get(i).getTime_n());
						tmpRasp.setTime_k(nrasp.get(i).getTime_k());
						tmpRasp.setVidp(nrasp.get(i).getVidp());
						tmpRasp.setCpol(nrasp.get(i).getCpol());
						tmpRasp.setCdol(nrasp.get(i).getCdol());
						tmpRasp.setPfd(nrasp.get(i).isPfd());
						if (tmpRasp.isPfd() && getPrrabFromNdv(tmpRasp.getCpol(), tmpRasp.getPcod(), tmpRasp.getCdol(), tmpRasp.getDatap())) {
							rasp.add(tmpRasp);
							//if ()
							
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
		            	
		            }
				}
			}
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
