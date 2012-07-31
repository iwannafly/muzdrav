package ru.nkz.ivcgzo.clientGenTalons;

import org.apache.thrift.TException;
import java.io.File;

public class SvodkiUnit {

	static void Svodki(int num, long datn, long datk){
		try{
//			Protokol protokol = new Protokol();
//			protokol.setUserId(MainForm.authInfo.getUser_id());
//			protokol.setNpasp(Vvod.zapVr.getNpasp());
//			protokol.setPvizit_id(TabPos.getSelectedItem().id_obr);
//			protokol.setPvizit_ambId(TabPos.getSelectedItem().id);
//			protokol.setCpol(MainForm.authInfo.getCpodr());
//			String servPath = MainForm.tcl.printProtokol(protokol);
//			String cliPath;
//			cliPath = File.createTempFile("svod1", ".htm").getAbsolutePath();
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
