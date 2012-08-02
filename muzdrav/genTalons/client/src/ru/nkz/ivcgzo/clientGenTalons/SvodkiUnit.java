package ru.nkz.ivcgzo.clientGenTalons;

import org.apache.thrift.TException;
import java.io.File;
//import ru.nkz.ivcgzo.thriftGenTalon.Report1;

public class SvodkiUnit {

	static void Svodki(int num, long datn, long datk, int pcod, String cdol){
		try{
//			Report1 report = new Report1();
//			report.setCpol(MainForm.authInfo.getCpodr());
//			report.setPcod(pcod);
//			report.setCdol(cdol);
//			report.setDatan(datn);
//			report.setDatak(datk);
//			report.setNpp(num);
//		
//			String servPath = MainForm.tcl.printReport(report);
//			String cliPath = File.createTempFile("rep1", ".htm").getAbsolutePath();
//			MainForm.conMan.transferFileFromServer(servPath, cliPath);

	        switch (num) {
	        case 0: 
	        	break;        
	        case 1: 
	        	break;        
	        default: 
	        	break;
	        }
//		}catch (TException e1) {
//			e1.printStackTrace();
//			MainForm.conMan.reconnect(e1);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
