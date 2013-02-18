package ru.nkz.ivcgzo.clientGenTalons;

import org.apache.thrift.TException;
import java.io.File;
import java.io.IOException;

import ru.nkz.ivcgzo.thriftGenTalon.RepStruct;

public class SvodkiUnit {
	private static RepStruct repInfo;

	static void Svodki(int numsv, long datn, long datk, int pcod, String cdol){
		try{
			repInfo = new RepStruct();
			repInfo.setCpol(MainForm.authInfo.getCpodr());
			repInfo.setPcod(pcod);
			repInfo.setCdol(cdol);
			repInfo.setDatan(datn);
			repInfo.setDatak(datk);
			repInfo.setNsv(numsv);
		
			String servPath = MainForm.tcl.printReport(repInfo);
			String cliPath = File.createTempFile("rep1", ".htm").getAbsolutePath();
			MainForm.conMan.transferFileFromServer(servPath, cliPath);
			MainForm.conMan.openFileInEditor(cliPath, false);
		}catch (TException e1) {
			e1.printStackTrace();
			MainForm.conMan.reconnect(e1);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
