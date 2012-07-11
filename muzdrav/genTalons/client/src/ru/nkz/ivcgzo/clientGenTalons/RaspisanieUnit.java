package ru.nkz.ivcgzo.clientGenTalons;

import java.util.ArrayList;
import java.util.List;

import ru.nkz.ivcgzo.thriftGenTalon.Nrasp;
import ru.nkz.ivcgzo.thriftGenTalon.Norm;

public class RaspisanieUnit {

	static void NewRaspisanie(int cpodr, int pcod, String cdol,int cxm){
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
}
