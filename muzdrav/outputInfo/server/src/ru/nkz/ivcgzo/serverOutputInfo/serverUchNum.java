package ru.nkz.ivcgzo.serverOutputInfo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.thrift.TException;

import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlSelectExecutor.SqlExecutorException;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftOutputInfo.UchException;
import ru.nkz.ivcgzo.thriftOutputInfo.UchastokInfo;
import ru.nkz.ivcgzo.thriftOutputInfo.UchastokNum;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

public class serverUchNum extends serverTemplate {
	private ISqlSelectExecutor sse;
	ITransactedSqlExecutor tse;
	private TResultSetMapper<UchastokInfo, UchastokInfo._Fields> tableUchastok;
	private static Class<?>[] UchastokTypes;
	private TResultSetMapper<UchastokNum, UchastokNum._Fields> tableUchNum;
	private static Class<?>[] UchNumTypes;
	private static Logger log = Logger.getLogger(serverUchNum.class.getName());

	public serverUchNum(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		this.sse = sse;
		this.tse = tse;
		//Таблица справочник участков 1
		tableUchastok = new TResultSetMapper<>(UchastokInfo.class, "fam","im","ot","pcod");
		UchastokTypes = new Class<?>[]{String.class,String.class,String.class,Integer.class};
		
		//Таблица справочник участков 2
		tableUchNum= new TResultSetMapper<>(UchastokNum.class, "uch");
		UchNumTypes = new Class<?>[]{Integer.class};
	}
	
	//Справочник участков 
		public List<UchastokInfo> getUch(int cpodr) throws KmiacServerException, TException {
			String sqlQuery = "SELECT a.fam, a.im, a.ot, a.pcod, b.cpodr "
					+"FROM s_vrach a, s_mrab b WHERE a.pcod=b.pcod AND b.cpodr = ?";
			try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, cpodr)) {
				ResultSet rs = acrs.getResultSet();

//				List<UchastokInfo> uchastokInfo = new ArrayList<UchastokInfo>();
//				while (rs.next()) {
//					UchastokInfo uchInfo = new UchastokInfo();
//					uchInfo.setFam(rs.getString("fam"));
//					uchInfo.setIm(rs.getString("im"));
//					uchInfo.setOt(rs.getString("ot"));
//					uchInfo.setPcod(rs.getInt("pcod"));
//					uchInfo.setCpol(rs.getInt("cpol"));
//					uchInfo.setUch(rs.getInt("uch"));
//					uchastokInfo.add(uchInfo);
//				}
				
				List<UchastokInfo> uchastokInfo = tableUchastok.mapToList(rs);
				if (uchastokInfo.size() > 0) {
					return uchastokInfo;				
				} else {
					throw new UchException();
				}
			} catch (SQLException e) {
				log.log(Level.ERROR, "SQL Exception: ", e);
				throw new KmiacServerException();	
			}
		}

		public List<UchastokNum> getUchNum(int pcod) throws UchException,KmiacServerException, TException {
			String sqlQuery = "SELECT a.uch "
					+"FROM s_uch a, s_vrach b WHERE a.pcod=b.pcod AND a.pcod = ?";
			try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, pcod)) {
				ResultSet rs = acrs.getResultSet();
				List<UchastokNum> uchastokInfo = tableUchNum.mapToList(rs);
				if (uchastokInfo.size() > 0) {
					return uchastokInfo;				
				} else {
					return null;
					//throw new UchException();
				}
			} catch (SQLException e) {
				log.log(Level.ERROR, "SQL Exception: ", e);
				throw new KmiacServerException();	
			}
		}

		//Добавление
		public int addUchNum(UchastokNum uchNum) throws KmiacServerException,
				TException {
			int id = uchNum.getId();
			try (SqlModifyExecutor sme = tse.startTransaction()) {
				sme.execPreparedQuery("SELECT id FROM s_tabel WHERE id=?", id).getResultSet().next(); 
				int[] indexes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
				sme.execPreparedT("INSERT INTO s_tabel (pcod, cdol, datav, timep, timed, timeda, " 
		        				+ "timeprf, timepr, nuch1, nuch2, nuch3) " 
		        				+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
		        				true, uchNum, UchastokTypes, indexes);
				id = sme.getGeneratedKeys().getInt("id");
				sme.setCommit();
				return id;			
			} catch (SQLException | InterruptedException e) {
				e.printStackTrace();
				throw new KmiacServerException("Ошибка сервера");
			}
		}

		//Изменение
		public void updateUchNum(UchastokNum uchNum) throws KmiacServerException,
				TException {
			int[] indexes = {1, 2, 3, 4, 5, 6, 7};
			try (SqlModifyExecutor sme = tse.startTransaction()) {
				sme.execPreparedT("SELECT a.pcod, a.fam, a.im, a.ot " 
					+ "FROM s_vrach a WHERE cpodr=?", true, uchNum, UchastokTypes, indexes);
				sme.setCommit();
			} catch (SqlExecutorException | InterruptedException e) {
				e.printStackTrace();
			}		
		}

		//Удаление
		public void deleteUchNum(int uchNum) throws TException {
			try (SqlModifyExecutor sme = tse.startTransaction()) {
				sme.execPrepared("DELETE FROM s_tabel WHERE id = ?;", false, uchNum);
				sme.setCommit();
		    } catch (SqlExecutorException | InterruptedException e) {
				e.printStackTrace();
			}			
		}

	
}
