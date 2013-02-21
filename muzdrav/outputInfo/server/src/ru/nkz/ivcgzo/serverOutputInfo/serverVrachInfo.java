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
		tableVrachInfo = new TResultSetMapper<>(VrachInfo.class, "fam","im","ot","cdol","pcod");
		VrachInfoTypes = new Class<?>[]{String.class,String.class,String.class,String.class,Integer.class};
		//Таблица VrachTabel
		tableVrachTabel = new TResultSetMapper<>(VrachTabel.class, "datav","timep","timed","timeda","timeprf","timepr","pcod","cpodr","cdol","id");
		VrachTabelTypes = new Class<?>[]{Date.class,Double.class,Double.class,Double.class,Double.class,Double.class,Integer.class,Integer.class,String.class,Integer.class};
	}
	public List<VrachInfo> getVrachTableInfo(int cpodr) throws KmiacServerException, TException {
				String sqlQuery = "SELECT a.fam, a.im, a.ot, a.pcod, b.cdol " 
				+ "FROM s_vrach a, s_mrab b WHERE a.pcod=b.pcod AND cpodr=?";
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cpodr)) {
			ResultSet rs = acrs.getResultSet();
			List<VrachInfo> VrachInfo = tableVrachInfo.mapToList(rs);
			if (VrachInfo.size() > 0) {
				return VrachInfo;				
			} else {
				throw new VTException();
			}
		} catch (SQLException e) {
			log.log(Level.ERROR, "SQL Exception: ", e);
			throw new KmiacServerException();
		}
	}
	
	public List<VrachTabel> getVrachTabel(int pcod) throws KmiacServerException, TException {
				String sqlQuery = "SELECT datav, timep, timed, timeda, timeprf, " 
				+ "timepr, pcod, cpodr, cdol, id FROM s_tabel WHERE pcod=? ORDER BY datav DESC";
		try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, pcod)) {
			ResultSet rs = acrs.getResultSet();
			List<VrachTabel> VrachTabel = tableVrachTabel.mapToList(rs);
			return VrachTabel;	
		} catch (SQLException e) {
			log.log(Level.ERROR, "SQL Exception: ", e);
			throw new KmiacServerException();
		}
	}
	
		//Удалить
		public void deleteVT(int vt) throws TException {
			try (SqlModifyExecutor sme = tse.startTransaction()) {
				sme.execPrepared("DELETE FROM s_tabel WHERE id = ?;", false, vt);
				sme.setCommit();
		    } catch (SqlExecutorException | InterruptedException e) {
				e.printStackTrace();
			}
		}
			
		//Добавить
		public int addVT(VrachTabel vt, int pcod, String cdol, int cpodr) throws VTException,KmiacServerException, TException {
			int id = vt.getId();
			try (SqlModifyExecutor sme = tse.startTransaction()) {
				sme.execPrepared("INSERT INTO s_tabel (datav, timep, timed, timeda, " 
		        				+ "timeprf, timepr, pcod, cpodr, cdol) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);",
		        				true, new Date(vt.datav), vt.timep, vt.timed, vt.timeda, vt.timeprf, vt.timepr, pcod, cpodr, cdol);
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
			int[] indexes = {1, 2, 3, 4, 5, 6, 0};
			try (SqlModifyExecutor sme = tse.startTransaction()) {
				sme.execPreparedT("UPDATE s_tabel SET datav = ?, timep = ?, timed = ?, "
		        				+ "timeda = ?, timeprf = ?, timepr = ?" 
		        				+ "WHERE id = ?", true, vt, VrachTabelTypes, indexes);
				sme.setCommit();
			} catch (SqlExecutorException | InterruptedException e) {
				e.printStackTrace();
			}
		}
}
