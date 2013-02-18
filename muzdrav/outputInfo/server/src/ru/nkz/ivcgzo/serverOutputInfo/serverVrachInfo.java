package ru.nkz.ivcgzo.serverOutputInfo;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.thrift.TException;

import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor.SqlExecutorException;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOutputInfo.VINotFoundException;
import ru.nkz.ivcgzo.thriftOutputInfo.VTDuplException;
import ru.nkz.ivcgzo.thriftOutputInfo.VTException;
import ru.nkz.ivcgzo.thriftOutputInfo.VrachInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.VrachTabel;

public class serverVrachInfo extends serverTemplate {
	private static Logger log = Logger.getLogger(serverVrachInfo.class.getName());
	private TResultSetMapper<VrachInfo, VrachInfo._Fields> tableVrachInfo;
	private static Class<?>[] VrachInfoTypes;
	private TResultSetMapper<VrachTabel, VrachTabel._Fields> tableVrachTabel;
	private static Class<?>[] VrachTabelTypes;
	
	public serverVrachInfo (ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		super(sse, tse);

		//Таблица VrachInfo
		tableVrachInfo = new TResultSetMapper<>(VrachInfo.class, "pcod","fam","im","ot","cdol");
		VrachInfoTypes = new Class<?>[]{Integer.class,String.class,String.class,String.class,String.class};
		
		//Таблица VrachTabel
		tableVrachTabel = new TResultSetMapper<>(VrachTabel.class, "pcod","cdol","datav","timep","timed","timeda","timeprf","timepr","id");
		VrachTabelTypes = new Class<?>[]{Integer.class,String.class,Date.class,Double.class,Double.class,Double.class, Double.class, Double.class, Integer.class};

			
	}
	public List<VrachInfo> getVrachTableInfo(int cpodr) throws VINotFoundException,
			KmiacServerException, TException {
	
				String sqlQuery = "SELECT a.pcod, a.fam, a.im, a.ot, b.cdol " 
				+ "FROM s_vrach a, s_mrab b WHERE a.pcod=b.pcod AND cpodr=?";
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cpodr)) {
			ResultSet rs = acrs.getResultSet();
			List<VrachInfo> VrachInfo = tableVrachInfo.mapToList(rs);
			if (VrachInfo.size() > 0) {
				return VrachInfo;				
			} else {
				throw new VINotFoundException();
			}
		} catch (SQLException e) {
			log.log(Level.ERROR, "SQL Exception: ", e);
			throw new KmiacServerException();
		}
	}
	
	public List<VrachTabel> getVrachTabel(int pcod) throws VTDuplException,
			KmiacServerException, TException {
		
				String sqlQuery = "SELECT pcod, cdol, datav, timep, timed, timeda, timeprf, " 
				+ "timepr, id FROM s_tabel WHERE pcod=?";
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, pcod)) {
			ResultSet rs = acrs.getResultSet();
			List<VrachTabel> VrachTabel = tableVrachTabel.mapToList(rs);
			if (VrachTabel.size() > 0) {
				return VrachTabel;				
			} else {
				throw new VTDuplException();
			}
		} catch (SQLException e) {
			log.log(Level.ERROR, "SQL Exception: ", e);
			throw new KmiacServerException();
		}
	}
	
	//Удалить
		public void deleteVT(int vt) throws TException {
			System.out.println(vt);
			try (SqlModifyExecutor sme = tse.startTransaction()) {
				sme.execPrepared("DELETE FROM s_tabel WHERE id = ?;", false, vt);
				sme.setCommit();
		    } catch (SqlExecutorException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		//Добавить
		public int addVT(VrachTabel vt) throws VTException, VTDuplException,
				KmiacServerException, TException {
			int id = vt.getId();
			try (SqlModifyExecutor sme = tse.startTransaction()) {
//				sme.execPreparedQuery("SELECT id FROM s_tabel WHERE id=?", id).getResultSet().next(); 
				int[] indexes = {0, 1, 2, 3, 4, 5, 6, 7};
				sme.execPreparedT("INSERT INTO s_tabel (pcod, cdol, datav, timep, timed, timeda, " 
		        				+ "timeprf, timepr) VALUES (?, ?, ?, ?, ?, ?, ?, ?);",
		        				true, vt, VrachTabelTypes, indexes);
				id = sme.getGeneratedKeys().getInt("id");
				sme.setCommit();
				return id;			
			} catch (SQLException | InterruptedException e) {
				e.printStackTrace();
				throw new KmiacServerException("Ошибка сервера");
			}
		}
		
		
		
		//Изменить
		public void updateVT(VrachTabel vt) throws KmiacServerException, TException {
			int[] indexes = {1, 2, 3, 4, 5, 6, 7, 0};
			try (SqlModifyExecutor sme = tse.startTransaction()) {
				sme.execPreparedT("UPDATE s_tabel SET cdol = ?, datav = ?, timep = ?, timed = ?, "
		        				+ "timeda = ?, timeprf = ?, timepr = ? " 
		        				+ "WHERE id = ?", true, vt, VrachTabelTypes, indexes);
				sme.setCommit();
			} catch (SqlExecutorException | InterruptedException e) {
				e.printStackTrace();
			}
		}
		

}
