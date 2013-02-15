package ru.nkz.ivcgzo.serverAutoProc;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.json.JSONException;
import org.json.JSONObject;

import ru.nkz.ivcgzo.configuration;
import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.Server;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor;

public class ServerAutoProc extends Server {
	private static final boolean DEBUG = Boolean.valueOf(System.getProperty("isDebug", "true"));
	private static final String vr42erWorkAddr = "http://www.vrach42.ru/Services/er/mis";
	private static final String vr42erTestAddr = "http://www.vrach42.ru/DemoServices/er/mis";
	private Properties prop;
	private final JsonHttpTransport jtr;
	
	public ServerAutoProc(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);
		
		jtr = new JsonHttpTransport();
		
		if (DEBUG)
			System.out.println("ServerAutoProc DEBUG is set!!!");
	}

	@Override
	public int getId() {
		return configuration.appId;
	}
	
	@Override
	public int getPort() {
		return -1;
	}
	
	@Override
	public String getName() {
		return configuration.appName;
	}
	
	@Override
	public void start() throws Exception {
		prop = new Properties();
		prop.put("charSet","Cp866");
		Class.forName("com.hxtt.sql.dbf.DBFDriver");
	}

	@Override
	public void stop() {
		
	}

	@Override
	public Object executeServerMethod(int id, Object... params) throws Exception {
		String methodName = null;
		
		try {
			switch (id) {
			case 1801:
				methodName = "addReceptionTicketToVr42";
				addReceptionTicketToVr42((int) params[0]);
				break;
			case 1802:
				methodName = "remReceptionTicketFromVr42";
				remReceptionTicketFromVr42((int) params[0]);
				break;
			default:
				throw new Exception();
			}
			
			return null;
		} catch (Exception e) {
			if (methodName != null)
				throw new Exception(String.format("Error executing server method with id %d (%s).", id, methodName), e);
			else
				throw new Exception(String.format("Unknown server method id %d.", id));
		}
	}
	
	
	private void scheduleOperation(int addrType, String text) throws Exception {
		try (SqlModifyExecutor sme = tse.startTransaction()) {
			sme.execPrepared("INSERT INTO scheduled (type, text) VALUES (?, ?)", false, addrType, text);
			sme.setCommit();
		}
	}
	
	public void addReceptionTicketToVr42(int etalonId) throws Exception {
		sendReceptionTicketToVr42(etalonId, 1);
	}
	
	public void remReceptionTicketFromVr42(int etalonId) throws Exception {
		sendReceptionTicketToVr42(etalonId, 2);
	}
	
	private void sendReceptionTicketToVr42(int etalonId, int operId) throws Exception {
		if (DEBUG)
			return;
		
		String text;
		
		try {
			text = new JsonTicketInfo(sse, etalonId, operId).toString();
		} catch (JSONException e) {
			e.printStackTrace();
			throw new Exception("Can't make json for add reception ticket.", e);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception("Can't query db to make json for add reception ticket.", e);
		}
		
		try {
			JSONObject resp = new JSONObject(jtr.sendPostRequest((DEBUG) ? vr42erTestAddr : vr42erWorkAddr, text));
			if (!resp.getBoolean("IsSuccessful"))
				throw new JSONException(String.format("Responce from server: %s", resp.getString("Error")));
		} catch (JSONException e) {
			throw new Exception("Malformed json.", e);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				scheduleOperation(1, text);
			} catch (Exception e1) {
				e1.printStackTrace();
				throw new Exception("Can't schedule operation for add reception ticket.", e);
			}
		}
		
	}
	
	
	
	public String getPL(String pl) {
		String sqlpl;
		String pathname;
		String fname;
		String sspl,fampl,impl,otpl,wpl,adrespl,katpl,strp;
		Date datbl, datel,drpl;
		int ter = 0, npaspl = 0, idlgt = 0, kol = 0, koln = 0;
		fname = "rr_pl";
		
		sqlpl = "select id_lg, ss, fam, im, ot, w, dr, adres, c_katl, date_bl, date_el from "+fname;
		try {
			SqlSelectExecutor dbffile = new SqlSelectExecutor(String.format("jdbc:dbf:/%s", pl), prop);
        	
			try (OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(pathname = File.createTempFile("rr_pl", ".htm").getAbsolutePath()), "utf-8");
        			AutoCloseableResultSet acrs = dbffile.execQuery(sqlpl)) {
   				StringBuilder sb = new StringBuilder(0x10000);
   				sb.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.1//EN\" \"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd\">");
   				sb.append("<html xmlns=\"http://www.w3.org/1999/xhtml\">");
   				sb.append("<head>");
     			sb.append("<meta http-equiv=\"Content-Type\" content=\"application/xhtml+xml; charset=utf-8\" />");
   				sb.append("<title>Файл льготников из ПФ</title>");
   				sb.append("</head>");
   				sb.append("<body>");
				sb.append("Льготники, имеющие право на бесплатное получение лекарственных средств");
				sb.append(String.format(" от %1$td.%1$tm.%1$tY  <br><br>", new Date(System.currentTimeMillis())));
				sb.append("Имя архивного файла:  "+pl);
				sb.append("<br>Протокол подгрузки: <br><br>");
				
				ResultSet rs = acrs.getResultSet();
				while (rs.next()) {
					sspl=rs.getString("ss");
					fampl=rs.getString("fam");
					impl=rs.getString("im");
					otpl=rs.getString("ot");
					wpl=rs.getString("w");
					drpl=rs.getDate("dr");
					adrespl=rs.getString("adres");
					katpl=rs.getString("c_katl");
					datbl=rs.getDate("date_bl");
					datel=rs.getDate("date_el");
					ter=rs.getInt("kd_ter");
					/* поиск льготника осуществляется по ключу: фамилия+имя+отчество+дата рождения+территория проживания
					 * если найден - проверяется снилс, снилсы совпали - проверяются категории, если не совпали - в протокол подгрузки
					 * записывается информация о том, что льготник найден, но снилсы различаются - необходимо проверить снилс 
					 */
					try (AutoCloseableResultSet acrp = sse.execPreparedQuery("select * from patient where fam=? and im=? and ot=? and datar = ? and ter_liv = ?", fampl,impl,otpl,drpl,ter)){
						if (acrp.getResultSet().next()) {
						if (acrp.getResultSet().getString("snils").equals(sspl)) {
							npaspl = acrp.getResultSet().getInt("npasp");
							kol = kol+1;
							try (AutoCloseableResultSet acrk = sse.execPreparedQuery("select p.id,p.npasp,p.lgot,k,kod_pf from p_kov p, n_lkn k where npasp = ? and p.lgota=k.pcod and k.kod_pf = ? ", npaspl,katpl)){
								if (acrk.getResultSet().next()){
									idlgt = acrk.getResultSet().getInt("id");
									try (SqlModifyExecutor sme = tse.startTransaction()){
										sme.execPrepared("update p_kov set drg = ?, dot = ? where id = ?",false, datbl,datel,idlgt);
										sme.setCommit();
									} catch (Exception e2) {
										e2.printStackTrace();
									} 
								} else {
										try (SqlModifyExecutor sme = tse.startTransaction()){
											sme.execPrepared("insert into p_kov (npasp,lgot,datal,drg,dot) values(?,?,?,?,?)", false, npaspl,katpl,datbl,datbl,datel);
											sme.setCommit();
										} catch (Exception e3){
											e3.printStackTrace();
										}
									}
							}
						} else {
							strp = fampl.trim()+" "+impl.trim()+" "+otpl.trim()+" дата рождения: "+drpl.toString()+" "+adrespl.trim();
							koln=koln+1;
						}
						// записать в протокол		
						}
					}
				}
				
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		return null;
	}

}
