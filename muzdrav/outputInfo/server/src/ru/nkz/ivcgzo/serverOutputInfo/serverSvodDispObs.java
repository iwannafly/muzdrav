package ru.nkz.ivcgzo.serverOutputInfo;

import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

import org.apache.thrift.TException;

import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOutputInfo.InputPlanDisp;

public class serverSvodDispObs extends serverTemplate {
	public serverSvodDispObs (ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
	}
	

	public String printSvedDispObs(InputPlanDisp ipd) throws KmiacServerException,
			TException {
		String svod = null;
		
		// Дата от ...
		Date dn;
		// Дата до ...
		Date dk;
		try {
			dn = (Date) sdfo.parse(ipd.getDaten());
			dk = (Date) sdfo.parse(ipd.getDatek());

			// Код полеклиники
			int kodpol = ipd.getKpolik();
			
			// Код ЛПУ
			int kodlpu = ipd.getClpu();
			// Вид больницы (Д/В)
			int poldv = 0;
			
			final String sqlQueryDetVzPol = "select c_nom from n_m00 where pcod ="+String.valueOf(kodlpu);
			
			try (AutoCloseableResultSet zapznach = sse.execPreparedQuery(sqlQueryDetVzPol)) {
				
				poldv = zapznach.getResultSet().getInt("c_nom");
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			
			// Наименование полеклиники
			String namepol = ipd.getNamepol();
			// № участка
			String uc = ipd.getUchas(); 
		
			final String sqlQuerySvedDis = "select pd.d_uch, pm.cod_sp, pm.pmer, p.npasp, pm.pdat, pm.fdat, pd.ishod, pn.dataot " +
				"from patient p join p_mer pm on(p.npasp = pm.npasp)"+
						"join p_nambk pn on(p.npasp =pn.npasp) join p_disp pd on(p.npasp = pd.npasp)"+
				"where ((pm.pdat between "+ dn+" and "+ dk+")or(pm.fdat between "+ dn+" and "+ dk+"))and(pd.diag = pm.diag)and(pd.ishod is null) and(pn.dataot is null)" +
						"and(pm.cpol = "+kodpol+")"+
				"Order by pm.cod_sp,pd.d_uch, pm.pmer";
		
			try (AutoCloseableResultSet spat = sse.execPreparedQuery(sqlQuerySvedDis)) {
			
				float [] mas = new float [26]; 
				float [] sum = new float [26];
				//spat.getResultSet().first();
					
	 
			
				while (spat.getResultSet().next()){
					//Обследование
					if ((spat.getResultSet().getInt("pmer") == 1)||(spat.getResultSet().getInt("pmer") == 18)||(spat.getResultSet().getInt("pmer") == 24)){
						if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
							mas[1]= mas[1]+1;
							sum[1]= sum[1]+1;
						}
						if (( spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
							mas[2]= mas[2]+1;
							sum[1]= sum[1]+1;
						}
					}
					//Явки
					if (spat.getResultSet().getInt("pmer")==2){
						if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
							mas[4]= mas[4]+1;
							sum[4]= mas[4]+1;
						}
						if ((spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
							mas[5]= mas[5]+1;
							mas[5]= mas[5]+1;
						}
										
					}
					//Госпитализация
					if ((spat.getResultSet().getInt("pmer")==3)||(spat.getResultSet().getInt("pmer")==12)){
						if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
							mas[7]= mas[7]+1;
							sum[7]= mas[7]+1;
						}
						if ((spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
							mas[8]= mas[8]+1;
							mas[8]= mas[8]+1;
						}
										
					}	
					//Противрец. лечение
					if ((spat.getResultSet().getInt("pmer")==4)||(spat.getResultSet().getInt("pmer")==10)||(spat.getResultSet().getInt("pmer")==11)
							||(spat.getResultSet().getInt("pmer")==13)||(spat.getResultSet().getInt("pmer")==25)||(spat.getResultSet().getInt("pmer")==27)
							||(spat.getResultSet().getInt("pmer")==29)){
						if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
							mas[10]= mas[10]+1;
							sum[10]= mas[10]+1;
						}
						if ((spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
							mas[11]= mas[11]+1;
							mas[11]= mas[11]+1;
						}
										
					}		
					//СКЛ
					if ((spat.getResultSet().getInt("pmer")==5)||(spat.getResultSet().getInt("pmer")==16)){
						if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
							mas[13]= mas[13]+1;
							sum[13]= mas[13]+1;
						}
						if ((spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
							mas[14]= mas[14]+1;
							mas[14]= mas[14]+1;
						}
										
					}	
					//Консультация
					if ((spat.getResultSet().getInt("pmer")==7)||(spat.getResultSet().getInt("pmer")==28)){
						if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
							mas[16]= mas[16]+1;
							sum[16]= mas[16]+1;
						}
						if ((spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
							mas[17]= mas[17]+1;
							mas[17]= mas[17]+1;
						}
										
					}
					
					//Санации
					if ((spat.getResultSet().getInt("pmer")==9)||(spat.getResultSet().getInt("pmer")==14)||(spat.getResultSet().getInt("pmer")==15)){
						if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
							mas[19]= mas[19]+1;
							sum[19]= mas[19]+1;
						}
						if ((spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
							mas[20]= mas[20]+1;
							mas[20]= mas[20]+1;
						}
										
					}
					
					//Проф. мероп.
					if ((spat.getResultSet().getInt("pmer")==8)||(spat.getResultSet().getInt("pmer")==17)||(spat.getResultSet().getInt("pmer")==26)){
						if ((spat.getResultSet().getDate("pdat").after(dn))&&(spat.getResultSet().getDate("pdat").before(dk))){
							mas[22]= mas[22]+1;
							sum[22]= mas[22]+1;
						}
						if ((spat.getResultSet().getDate("fdat").after(dn))&&(spat.getResultSet().getDate("fdat").before(dk))){
							mas[23]= mas[23]+1;
							mas[23]= mas[23]+1;
						}
										
					}
					
					
					
				}	
			
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		return null;
	} 


}
