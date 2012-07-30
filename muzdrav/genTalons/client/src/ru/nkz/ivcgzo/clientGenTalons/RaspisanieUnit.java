package ru.nkz.ivcgzo.clientGenTalons;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingUtilities;

import ru.nkz.ivcgzo.thriftGenTalon.Ndv;
import ru.nkz.ivcgzo.thriftGenTalon.NdvNotFoundException;
import ru.nkz.ivcgzo.thriftGenTalon.Nrasp;
import ru.nkz.ivcgzo.thriftGenTalon.Norm;
import ru.nkz.ivcgzo.thriftGenTalon.Calend;
import ru.nkz.ivcgzo.thriftGenTalon.Rasp;
import ru.nkz.ivcgzo.thriftGenTalon.Talon;

public class RaspisanieUnit {

	public static  List<Nrasp> nrasp;
	public static List<Nrasp> pauselist;
	public static List<Rasp> rasp;
	public static List<Talon> timelist;
	public static List<Norm> mdlit;
	public static int dlit;
	public static int TalonCount;
	public static long timepause_n = 0;
	public static long timepause_k = 0;

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
	
	static void CreateTalons(JProgressBar pBar,int cpodr, int pcod, String cdol, long datn, long datk, int ind){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy hh:mm");
			Calendar cal1 = Calendar.getInstance();
			Calendar cal2 = Calendar.getInstance();
			cal1.setTimeInMillis(datn);
			cal2.setTimeInMillis(datk);
			
			if (ind ==1) TalonCount = MainForm.tcl.getTalonCountCpodr(cal1.getTimeInMillis(), cal2.getTimeInMillis(), cpodr);
			if (ind ==2) TalonCount = MainForm.tcl.getTalonCountCdol(cal1.getTimeInMillis(), cal2.getTimeInMillis(), cpodr, cdol);
			if (ind ==3) TalonCount = MainForm.tcl.getTalonCountVrach(cal1.getTimeInMillis(), cal2.getTimeInMillis(), cpodr, pcod, cdol);

			if (TalonCount != 0){
				int answer = JOptionPane.showConfirmDialog(null, "За период "+sdf.format(new Date(cal1.getTimeInMillis()))+" - "+sdf.format(new Date(cal2.getTimeInMillis()))+" талоны уже сформированы. \n\r " +
						" Формировать талоны заново?",  null, JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
	            if (answer == JOptionPane.YES_OPTION){
		    		if (ind ==1){
						MainForm.tcl.deleteRaspCpodr(cal1.getTimeInMillis(), cal2.getTimeInMillis(), cpodr);
			    		MainForm.tcl.deleteTalonCpodr(datn, datk, cpodr);
		    		}
		    		if (ind ==2){
						MainForm.tcl.deleteRaspCdol( cal1.getTimeInMillis(), cal2.getTimeInMillis(), cpodr, cdol);
		    			MainForm.tcl.deleteTalonCdol (datn, datk, cpodr, cdol);
		    		}
		    		if (ind ==3){
						MainForm.tcl.deleteRaspVrach(cal1.getTimeInMillis(), cal2.getTimeInMillis(), cpodr, pcod, cdol);
		    			MainForm.tcl.deleteTalonVrach(datn, datk, cpodr, pcod, cdol);
		    		}
	            }else{
	            	return; 
	            }            
			}
//			 appThread.start();
//			 appThread.run();

			if (ind ==1) nrasp = MainForm.tcl.getNraspCpodr(cpodr);
			if (ind ==2) nrasp = MainForm.tcl.getNraspCdol(cpodr, cdol);
			if (ind ==3) nrasp = MainForm.tcl.getNraspVrach(cpodr, pcod, cdol);

			cal1.add(Calendar.DAY_OF_MONTH, -1);
			rasp = new ArrayList<Rasp>();
			pauselist = new ArrayList<Nrasp>();
			while (!cal1.equals(cal2)) {
				cal1.add(Calendar.DAY_OF_MONTH, 1);
				if (getPrrabFromCalendar(cal1.getTimeInMillis())){
					int NumDayOfWeek = cal1.get(Calendar.DAY_OF_WEEK);
					if (NumDayOfWeek == 1) NumDayOfWeek = 7;
					if (NumDayOfWeek >= 2) NumDayOfWeek = NumDayOfWeek-1;
					for (int i=0; i <= nrasp.size()-1; i++) {
						int proc = (((i+1)/(nrasp.size()-1))*100);
						pBar.setValue(proc);
						if(nrasp.get(i).getDenn() == NumDayOfWeek){
							//System.out.println(nrasp.get(i).getDenn()+", "+!nrasp.get(i).isPfd() +",  "+ getPrrabFromNdv(nrasp.get(i).getCpol(), nrasp.get(i).getPcod(), nrasp.get(i).getCdol(), cal1.getTimeInMillis()));
							if (!nrasp.get(i).isPfd()) 
							if (getPrrabFromNdv(nrasp.get(i).getCpol(), nrasp.get(i).getPcod(), nrasp.get(i).getCdol(), cal1.getTimeInMillis())) {
								if((nrasp.get(i).getCxema() == 0) || 
								(nrasp.get(i).getCxema() == 1 && (cal1.get(Calendar.DAY_OF_MONTH) % 2) == 0)|| 		
								(nrasp.get(i).getCxema() == 2 && (cal1.get(Calendar.DAY_OF_MONTH) % 2) != 0)){
									Rasp tmpRasp = new Rasp();
									tmpRasp.setPcod(nrasp.get(i).getPcod());
									tmpRasp.setDenn(nrasp.get(i).getDenn());
									tmpRasp.setDatap(cal1.getTimeInMillis());
									tmpRasp.setTime_n(new Time(nrasp.get(i).getTime_n()).getTime());
									tmpRasp.setTime_k(new Time(nrasp.get(i).getTime_k()).getTime());
									tmpRasp.setVidp(nrasp.get(i).getVidp());
									tmpRasp.setCpol(nrasp.get(i).getCpol());
									tmpRasp.setCdol(nrasp.get(i).getCdol());
									tmpRasp.setPfd(nrasp.get(i).isPfd());
									rasp.add(tmpRasp);
									if (!new Time(nrasp.get(i).getTimep_n()).toString().equalsIgnoreCase("00:00:00") && !new Time(nrasp.get(i).getTimep_k()).toString().equalsIgnoreCase("00:00:00")){
										Nrasp tmpPause = new Nrasp();
										tmpPause.setPcod(tmpRasp.getPcod());
										tmpPause.setDenn(tmpRasp.getDenn());
										tmpPause.setCdol(tmpRasp.getCdol());
										tmpPause.setVidp(tmpRasp.getVidp());
										tmpPause.setTimep_n(new Time(nrasp.get(i).getTimep_n()).getTime());
										tmpPause.setTimep_k(new Time(nrasp.get(i).getTimep_k()).getTime());
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
		        CreateTableTalon(rasp, pauselist);
			}else {
				System.out.println("Неприемный день. Расписание не сформировано.");
				JOptionPane.showMessageDialog(null, "Неприемный день. Расписание не сформировано.", null, JOptionPane.INFORMATION_MESSAGE); 
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void CreateTableTalon(List<Rasp> rasp, List<Nrasp> pauselist) {
		List<Talon> talonlist = new ArrayList<Talon>();
		for (int i=0; i <= rasp.size()-1; i++) {
			try {
				if (!new Time(rasp.get(i).getTime_n()).toString().equalsIgnoreCase("00:00:00") && !new Time(rasp.get(i).getTime_k()).toString().equalsIgnoreCase("00:00:00")){
					dlit = 0;
					timepause_n = 0;
					timepause_k = 0;
					mdlit = MainForm.tcl.getNorm(rasp.get(i).getCpol(), rasp.get(i).getCdol());
					for (int j=0; j <= mdlit.size()-1; j++) 
						if (rasp.get(i).getVidp() == mdlit.get(j).getVidp()){
							dlit = mdlit.get(j).getDlit();
							break;
						}
					if (pauselist.size() != 0) {
						for (int j=0; j <= pauselist.size()-1; j++){
							if (rasp.get(i).getPcod()==pauselist.get(j).getPcod() && 
								rasp.get(i).getDenn()==pauselist.get(j).getDenn() &&
								rasp.get(i).getCdol()==pauselist.get(j).getCdol() &&
								rasp.get(i).getVidp()==pauselist.get(j).getVidp()){
								timepause_n = pauselist.get(j).getTimep_n();
								timepause_k = pauselist.get(j).getTimep_k();
								break;
							}
						}
					}
					
					if (dlit != 0){
						if (timepause_n==0 && timepause_k==0){
							getTalonTime(rasp.get(i).getTime_n(), rasp.get(i).getTime_k(), dlit);
						}else{
							getTalonTime(rasp.get(i).getTime_n(), rasp.get(i).getTime_k(), dlit, timepause_n, timepause_k);
						}
						for (int j=0; j <= timelist.size()-1; j++){
							Talon tmpTalon = new Talon();
							tmpTalon.setCpol(rasp.get(i).getCpol());
							tmpTalon.setPcod_sp(rasp.get(i).getPcod());
							tmpTalon.setCdol(rasp.get(i).getCdol());
							tmpTalon.setVidp(rasp.get(i).getVidp());
							tmpTalon.setTimepn(new Time(rasp.get(i).getTime_n()).getTime());
							tmpTalon.setTimepk(new Time(rasp.get(i).getTime_k()).getTime());
							tmpTalon.setDatap(rasp.get(i).getDatap());
							tmpTalon.setTimep(timelist.get(j).getTimep());
							tmpTalon.setDatapt(tmpTalon.getDatap() + tmpTalon.getTimep());
							talonlist.add(tmpTalon);
						}
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
		timelist = new ArrayList<Talon>();
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTimeInMillis(timepn);
		cal2.setTimeInMillis(timepk);
		cal1.add(Calendar.MINUTE, -dlitp);
		while (cal1.compareTo(cal2)<0) {
			cal1.add(Calendar.MINUTE, dlitp);
			if (cal1.compareTo(cal2)==0) break;
			Talon tmpTime = new Talon();
			tmpTime.setTimep(cal1.getTimeInMillis());
			timelist.add(tmpTime);
		}
		return timelist;
	}

	private static List<Talon> getTalonTime(long timepn, long timepk, int dlitp,
			long timepausen, long timepausek) {
		timelist = new ArrayList<Talon>();
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		Calendar cal3 = Calendar.getInstance();
		Calendar cal4 = Calendar.getInstance();
		cal1.setTimeInMillis(timepn);
		cal2.setTimeInMillis(timepk);
		cal3.setTimeInMillis(timepausen);
		cal4.setTimeInMillis(timepausek);
		cal1.add(Calendar.MINUTE, -dlitp);

		while (cal1.compareTo(cal2)<0) {
			cal1.add(Calendar.MINUTE, dlitp);
			if (cal1.compareTo(cal2)==0) break;
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
			List<Ndv> ndv = MainForm.tcl.getNdv(cpodr, pcod, cdol);
			if(ndv.size() != 0){
				for (int i=0; i <= ndv.size()-1; i++) {
					Ndv tmpNdv = new Ndv();
					tmpNdv.setDatan(ndv.get(i).getDatan());
					tmpNdv.setDatak(ndv.get(i).getDatak());
					Calendar cal1 = Calendar.getInstance();
					Calendar cal2 = Calendar.getInstance();
					Calendar cal3 = Calendar.getInstance();
					cal1.setTimeInMillis(tmpNdv.getDatan());
					cal2.setTimeInMillis(tmpNdv.getDatak());
					cal3.setTimeInMillis(cdate);
//					System.out.println(sdf.format(new Date(cal1.getTimeInMillis())));
					if ((cal3.compareTo(cal1)>0 && cal3.compareTo(cal2)<0) || (cal3.compareTo(cal1)==0 || cal3.compareTo(cal2)==0)){
						return false;
					}
				}
			}
			return true;
		} catch (NdvNotFoundException nnfe) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}
//	 final static Runnable doProgBar = new Runnable() {
//	     public void run() {
//	         System.out.println("Hello World on " + Thread.currentThread());
//	     }
//	 };
//	 public static Thread appThread = new Thread() {
//	     public void run() {
//	         try {
//	             //SwingUtilities.invokeAndWait(doProgBar);
//	         }
//	         catch (Exception e) {
//	             e.printStackTrace();
//	         }
//	         System.out.println("Finished on " + Thread.currentThread());
//	     }
//	 };
}
