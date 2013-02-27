package ru.nkz.ivcgzo.serverHospital;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import org.apache.log4j.Level;
import org.apache.thrift.TException;

import ru.nkz.ivcgzo.serverManager.common.AutoCloseableResultSet;
import ru.nkz.ivcgzo.serverManager.common.ISqlSelectExecutor;
import ru.nkz.ivcgzo.serverManager.common.ITransactedSqlExecutor;
import ru.nkz.ivcgzo.serverManager.common.SqlModifyExecutor;
import ru.nkz.ivcgzo.serverManager.common.thrift.TResultSetMapper;
import ru.nkz.ivcgzo.thriftCommon.classifier.IntegerClassifier;
import ru.nkz.ivcgzo.thriftCommon.kmiacServer.KmiacServerException;
import ru.nkz.ivcgzo.thriftHospital.ChildDocNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.ChildbirthNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.PatientNotFoundException;
import ru.nkz.ivcgzo.thriftHospital.TBirthPlace;
import ru.nkz.ivcgzo.thriftHospital.TInfoLPU;
import ru.nkz.ivcgzo.thriftHospital.TPatientCommonInfo;
import ru.nkz.ivcgzo.thriftHospital.TRd_Novor;
import ru.nkz.ivcgzo.thriftHospital.TRd_Svid_Rojd;

/**
 * Серверная часть панели информации о новорождённом,
 * а также заполнения\печати медицинского свидетельства о рождении
 * @author Балабаев Никита Дмитриевич
 */
public final class ServerChildren {

    //TODO: при изменении региона или серии в регионе - поменять:
	private static final String childBirthDocSeries = "32";
	private static final String childBirthDocPath = "\\plugin\\reports\\ChildBirthDocument.htm";
	protected ISqlSelectExecutor sse;
	protected ITransactedSqlExecutor tse;
    private TResultSetMapper<IntegerClassifier, IntegerClassifier._Fields> rsmIntClas;
	private TResultSetMapper<TRd_Novor, TRd_Novor._Fields> rsmRdNovor;
	private TResultSetMapper<TRd_Svid_Rojd, TRd_Svid_Rojd._Fields> rsmRdSvidRojd;
	private TResultSetMapper<TPatientCommonInfo, TPatientCommonInfo._Fields> rsmCommonPatient;
	private TResultSetMapper<TBirthPlace, TBirthPlace._Fields> rsmBirthPlace;
	private TResultSetMapper<TInfoLPU, TInfoLPU._Fields> rsmInfoLPU;
    private static final String[] INT_CLAS_FIELD_NAMES = {
        "pcod", "name"
    };
    private static final String[] RDNOVOR_FIELD_NAMES = {
	   "npasp", "nrod", "timeon", "kolchild", "nreb", "massa", "rost",
	   "apgar1", "apgar5", "krit1", "krit2", "krit3", "krit4", "mert", "donosh", "datazap"
   };
    private static final String[] RDSVID_ROJD_FIELD_NAMES = {
    	"npasp", "ndoc", "famreb", "m_rojd", "zan", "r_proiz", "svid_write", "cdol_write",
    	"clpu", "svid_give", "cdol_give", "dateoff"
   };
    private static final String[] COMMON_PATIENT_FIELD_NAMES = {
        "npasp", "full_name", "datar", "pol", "jitel",
        "adp_obl", "adp_gorod", "adp_ul", "adp_dom", "adp_korp",
        "adp_kv", "obraz", "status"
    };
    private static final String[] LPU_FIELD_NAMES = {
        "name", "adres", "okpo", "zaved"
    };
    private static final String[] BIRTHPLACE_FIELD_NAMES = {
        "region", "city", "type"
    };
    private static final Class<?>[] CHILD_TYPES = new Class<?>[] {
	//	npasp,			nrod,   		timeon,			kolchild,		nreb,			massa,			rost,
    	Integer.class,	Integer.class,	String.class,	Integer.class,	Integer.class,	Integer.class,	Integer.class,
	//	apgar1,			apgar5,			krit1,			krit2,			krit3,			krit4,			mert,
    	Integer.class,	Integer.class,	Boolean.class,	Boolean.class,	Boolean.class,	Boolean.class,	Boolean.class,
	//	donosh,			datazap
    	Boolean.class,	Date.class
    };
    private static final Class<?>[] CHILDBIRTH_DOC_TYPES = new Class<?>[] {
	//	npasp,			ndoc,   		famreb,			m_rojd			zan				r_proiz
    	Integer.class,	Integer.class,	String.class,	Integer.class,	Integer.class,	Integer.class,
    //	svid_write		cdol_write		clpu			svid_give		cdol_give		dateoff
    	Integer.class,	String.class,	Integer.class,	Integer.class,	String.class,	Date.class
    };
	
	public ServerChildren(ISqlSelectExecutor sse, ITransactedSqlExecutor tse) {
		this.sse = sse;
		this.tse = tse;

        rsmIntClas = new TResultSetMapper<>(IntegerClassifier.class, INT_CLAS_FIELD_NAMES);
        rsmRdNovor = new TResultSetMapper<>(TRd_Novor.class, RDNOVOR_FIELD_NAMES);
        rsmRdSvidRojd = new TResultSetMapper<>(TRd_Svid_Rojd.class, RDSVID_ROJD_FIELD_NAMES);
        rsmCommonPatient = new TResultSetMapper<>(TPatientCommonInfo.class,
                COMMON_PATIENT_FIELD_NAMES);
        rsmBirthPlace = new TResultSetMapper<>(TBirthPlace.class, BIRTHPLACE_FIELD_NAMES);
        rsmInfoLPU = new TResultSetMapper<>(TInfoLPU.class, LPU_FIELD_NAMES);
	}
	
	/**
	 * Получение строки в заданном регистре (только первый символ заглавный)
	 * @param sourceStr Строка для преобразования
	 * @return Возвращает преобразованную строку, либо
	 * <code>null</code> в случае пустой строки
	 */
	private String setNormalNameReg(final String sourceStr) {
		if (sourceStr == null)
			return null;
		if (sourceStr.length() == 0)
			return sourceStr;
		if (sourceStr.length() == 1)
			return sourceStr.toUpperCase();
		return (sourceStr.substring(0, 1).toUpperCase()).concat(sourceStr.substring(1).toLowerCase());
	}
	
	/**
	 * Получение из строки содержащихся в ней слов в установленном регистре
	 * (только первый символ каждого слова заглавный)
	 * @param sourceStr Исходная строка
	 * @return Возвращает слова в виде массива строк, либо
	 * <code>null</code> в случае пустой строки
	 */
	private String[] getNormalRegWords(final String sourceStr) {
		if ((sourceStr == null) || (sourceStr.length() == 0))
			return null;
		String[] retArr = sourceStr.split(" ");
        for(int i = 0; i < retArr.length; i++)
        	retArr[i] = setNormalNameReg(retArr[i]);
        return retArr;
	}

	/**
	 * Получение из массива строк одной строки с заданным разделителем
	 * @param sourceStrArr Исходный массив строк
	 * @param sepStr Разделитель
	 * @return Возвращает полученную строку
	 */
	private String getConcatStrFromAray(final String[] sourceStrArr, final String sepStr) {
		if ((sourceStrArr == null) || (sourceStrArr.length == 0))
			return "";
		String retStr = "";
        for(int i = 0; i < sourceStrArr.length - 1; i++)
        	retStr += sourceStrArr[i] + sepStr;
        retStr += sourceStrArr[sourceStrArr.length - 1];
        return retStr;
	}

	/**
	 * Проверка существования пациента
	 * @param npasp Уникальный номер пациента
	 * @return Возвращает <code>true</code>, если пациент существует;
	 * иначе - <code>false</code>
	 */
	private boolean isPatientExist(final int npasp) {
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                "SELECT * FROM patient " +
                "WHERE (npasp = ?);", npasp)) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException (isPatientExist): ", e);
			((SQLException) e.getCause()).printStackTrace();
            return false;
        }
    }

	/**
	 * Проверка существования информации о новорождённом
	 * @param npasp Уникальный номер пациента
	 * @return Возвращает <code>true</code>, если информация о новорождённом существует;
	 * иначе - <code>false</code>
	 */
	private boolean isChildExist(final int npasp) {
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                "SELECT * FROM c_rd_novor " +
                "WHERE (npasp = ?);", npasp)) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException (isChildExist): ", e);
			((SQLException) e.getCause()).printStackTrace();
            return false;
        }
    }

	/**
	 * Проверка существования мед.свидетельства о рождении
	 * @param npasp Уникальный номер пациента
	 * @return Возвращает <code>true</code>, если информация о свидетельстве существует;
	 * иначе - <code>false</code>
	 */
	private boolean isChildDocExist(final int npasp) {
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                "SELECT * FROM c_rd_svid_rojd " +
                "WHERE (npasp = ?);", npasp)) {
            return acrs.getResultSet().next();
        } catch (SQLException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException (isChildDocExist): ", e);
			((SQLException) e.getCause()).printStackTrace();
            return false;
        }
    }

	/**
	 * Проверка уникальности номера мед.свидетельства о рождении
	 * @param ndoc Номер свидетельства
	 * @return Возвращает <code>true</code>, если номер свидетельства уникален;
	 * иначе - <code>false</code>
	 */
	private boolean isChildDocUnique(final int ndoc) {
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
                "SELECT * FROM c_rd_svid_rojd " +
                "WHERE (ndoc = ?);", ndoc)) {
            return !acrs.getResultSet().next();
        } catch (SQLException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException (isChildDocUnique): ", e);
			((SQLException) e.getCause()).printStackTrace();
            return false;
        }
	}
	
	/**
	 * Функция получения информации о мед.свидетельстве о рождении
	 * по номеру свидетельства
	 * @param ndoc Идентификатор свидетельства
	 * @return Возвращает информацию о свидетельстве
	 * @throws ChildDocNotFoundException свидетельство не найдено
	 * @throws KmiacServerException исключение на стороне сервера
	 */
	private TRd_Svid_Rojd getChildDocumentByDoc(final int ndoc)
			throws KmiacServerException, ChildDocNotFoundException {
	    try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
		        "SELECT * FROM c_rd_svid_rojd " +
		        "WHERE (ndoc = ?);", ndoc)) {
			if (acrs.getResultSet().next()) {
                return rsmRdSvidRojd.map(acrs.getResultSet());
            } else
                throw new ChildDocNotFoundException();
		} catch (SQLException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException (getChildDocumentByDoc): ", e);
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}
	
	/**
	 * Функция получения идентификатора матери новорождённого
	 * @param childId Идентификатор новорождённого
	 * @return Идентификатор матери
	 * @throws KmiacServerException исключение на стороне сервера
	 * @throws PatientNotFoundException новорождённый не найден
	 */
	private int getMotherId(final int childId)
			throws KmiacServerException, PatientNotFoundException {
		final String Query = "SELECT c_rd_ishod.npasp " +
				"FROM c_rd_ishod " +
				"JOIN c_rd_novor ON (c_rd_novor.nrod = c_rd_ishod.id) " +
				"WHERE (c_rd_novor.npasp = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
        		Query, childId)) {
        	ResultSet rs = acrs.getResultSet();
        	if (rs.next())
        		return rs.getInt(1);
        	else
                throw new PatientNotFoundException();
        } catch (SQLException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException (getMotherId): ", e);
			((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
	}
	
	/**
	 * Функция получения количества новорождённых в многоплодных родах
	 * @param childbirthId Идентификатор родов
	 * @return Количество новорождённых
	 * @throws KmiacServerException исключение на стороне сервера
	 * @throws ChildbirthNotFoundException роды не найдены
	 */
	private int getChildCountInChildbirth(final int childbirthId)
			throws KmiacServerException, ChildbirthNotFoundException {
		final String Query = "SELECT MAX(nreb) " +
				"FROM c_rd_novor " +
				"WHERE (nrod = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
        		Query, childbirthId)) {
        	ResultSet rs = acrs.getResultSet();
        	if (rs.next())
        		return rs.getInt(1);
        	else
                throw new ChildbirthNotFoundException();
        } catch (SQLException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException (getChildCountInChildbirth): ", e);
			((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
	}
	
	/**
	 * Функция получения срока первой явки матери к врачу
	 * @param nrod Идентификатор родов
	 * @return Срок первой явки (количество недель беременности),
	 * либо <code>-1</code> в случае отсутствия информации
	 * @throws KmiacServerException исключение на стороне сервера
	 */
	private int getFirstConsultWeekNum(final int nrod)
			throws KmiacServerException {
		final String Query = "SELECT p_rd_sl.yavka1 " +
				"FROM c_rd_ishod " +
				"LEFT JOIN p_rd_sl ON (p_rd_sl.id = c_rd_ishod.id_berem) " +
				"WHERE (c_rd_ishod.id = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
        		Query, nrod)) {
        	ResultSet rs = acrs.getResultSet();
        	if (rs.next())
        		return rs.getInt(1);
        	else
        		return -1;
        } catch (SQLException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException (getFirstConsultWeekNum): ", e);
			((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
	}
	
	/**
	 * Функция получения списка кодов занимаемых специалистом должностей
	 * @param doctorId Идентификатор специалиста
	 * @return Массив кодов занимаемых должностей,
	 * либо <code>null</code> в случае отсутствия информации
	 * @throws KmiacServerException исключение на стороне сервера
	 */
	private ArrayList<Integer> getDoctorCod_sp(final int doctorId)
			throws KmiacServerException {
		final String Query = "SELECT n_s00.cod_sp " +
				"FROM s_vrach " +
				"JOIN s_mrab ON (s_mrab.pcod = s_vrach.pcod) " +
				"JOIN n_s00 ON (n_s00.pcod = s_mrab.cdol) " +
				"WHERE (s_vrach.pcod = ?) AND (s_mrab.datau IS NULL);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
        		Query, doctorId)) {
        	ResultSet rs = acrs.getResultSet();
        	if (rs.next()) {
        		ArrayList<Integer> arrCodes = new ArrayList<Integer>();
        		do {
        			arrCodes.add(rs.getInt(1));
        		}while (rs.next());
        		return arrCodes;
        	} else
        		return null;
        } catch (SQLException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException (getDoctorCod_sp): ", e);
			((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
	}

	/**
	 * Функция получения идентификатора специалиста, принимавшего роды
	 * @param childbirthId Идентификатор родов
	 * @return Идентификатор специалиста,
	 * либо <code>-1</code> в случае отсутствия информации
	 * @throws KmiacServerException исключение на стороне сервера
	 */
	private int getDoctorWhoGetChildId(final int childBirthId)
			throws KmiacServerException {
		final String Query = "SELECT prinyl " +
				"FROM c_rd_ishod " +
				"WHERE (id = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
        		Query, childBirthId)) {
        	ResultSet rs = acrs.getResultSet();
        	if (rs.next())
        		return rs.getInt(1);
        	else
        		return -1;
        } catch (SQLException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException (getDoctorWhoGetChildId): ", e);
			((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
	}

	/**
	 * Функция получения информации о месте рождения
	 * @param cityId Идентификатор места рождения
	 * @return Информация о месте рождения (область, нас. пункт и тип нас. пункта)
	 * @throws KmiacServerException исключение на стороне сервера
	 */
	private TBirthPlace getChildBirthCityInfo(final int cityId)
			throws KmiacServerException {
		final String Query = "SELECT n_l02.name AS region, n_l00.name AS city, n_l00.vid_np AS type " +
				"FROM n_l00 " +
				"LEFT JOIN n_l02 ON (n_l02.c_ffomc = n_l00.c_ffomc) " +
				"WHERE (n_l00.pcod = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(Query, cityId)) {
        	ResultSet rs = acrs.getResultSet();
        	if (rs.next())
        		return rsmBirthPlace.map(rs);
        	else
        		throw new KmiacServerException();
        } catch (SQLException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException (getChildBirthCityInfo): ", e);
			((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
	}
	
	/**
	 * Функция получения информации о ЛПУ
	 * @param clpu Код ЛПУ
	 * @return Информация о ЛПУ (наименование, адрес, ОКПО и имя руководителя)
	 * @throws KmiacServerException исключение на стороне сервера
	 */
	private TInfoLPU getLPU_Info(final int clpu)
			throws KmiacServerException {
		final String Query = "SELECT name, adres, kodokpo AS okpo, zaved " +
				"FROM n_m00 " +
				"WHERE (pcod = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(Query, clpu)) {
        	ResultSet rs = acrs.getResultSet();
        	if (rs.next())
        		return rsmInfoLPU.map(rs);
        	else
        		throw new KmiacServerException();
        } catch (SQLException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException (getLPU_Info): ", e);
			((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
	}
	
	/**
	 * Функция получения информации о специалисте и заданной должности
	 * @param pcod Код специалиста
	 * @param dolj Код должности
	 * @return Вектор строк, состоящий из двух элементов - [полное имя специалиста, название должности],
	 * либо <code>null</code> (в случае если специалист или должность не найдены)
	 * @throws KmiacServerException исключение на стороне сервера
	 */
	private Vector<String> getDoctorInfo(final int pcod, final String dolj)
			throws KmiacServerException {
		final String Query = "SELECT (v.fam || ' ' || v.im || ' ' || v.ot) AS name, " +
				"n_s00.name AS dolj " +
	    		"FROM s_vrach v " +
	    		"CROSS JOIN n_s00 " +
	    		"WHERE (v.pcod = ?) AND (n_s00.pcod = ?);";
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(Query, pcod, dolj)) {
        	ResultSet rs = acrs.getResultSet();
        	if (rs.next()) {
        		Vector<String> vecToReturn = new Vector<String>(2);
        		vecToReturn.add(rs.getString(1));
        		vecToReturn.add(rs.getString(2));
        		return vecToReturn;
        	} else
        		return null;
        } catch (SQLException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException (getDoctorInfo): ", e);
			((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
	}

	public List<IntegerClassifier> getChildBirths(final long BirthDate)
			throws KmiacServerException {
		final String SQLQuery = "SELECT a.id AS pcod, " +
				"'Мама - ' || b.fam || ' ' || b.im || ' ' || b.ot AS name " +
				"FROM c_rd_ishod a " +
				"JOIN patient b ON (a.npasp = b.npasp) " +
				"WHERE (a.daterod = ?);";
	    try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
	    		SQLQuery, new Date(BirthDate))) {
	        return rsmIntClas.mapToList(acrs.getResultSet());
        } catch (SQLException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException (getChildBirths): ", e);
        	((SQLException) e.getCause()).printStackTrace();
        	throw new KmiacServerException();
        }
	}

	public void addChildInfo(final TRd_Novor Child)
			throws KmiacServerException, PatientNotFoundException {
        final int[] indexes = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14};
        //Поле "Дата записи" (datazap) не присутствует в списке параметров 
        //из-за наличия соответствующего правила (rule) на СУБД:
        final String sqlQuery = "INSERT INTO c_rd_novor " +
        		"(npasp, nrod, timeon, kolchild, nreb, massa, rost, " +
        		"apgar1, apgar5, krit1, krit2, krit3, krit4, mert, donosh) " +
        		"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (isPatientExist(Child.getNpasp())) {
                sme.execPreparedT(sqlQuery, false, Child, CHILD_TYPES, indexes);
                sme.setCommit();
            } else
                throw new PatientNotFoundException();
        } catch (SQLException | InterruptedException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException | InterruptedException (addChildInfo): ", e);
            throw new KmiacServerException();
        }
	}

	public TRd_Novor getChildInfo(final int npasp)
			throws KmiacServerException, PatientNotFoundException {
	    try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
		        "SELECT * FROM c_rd_novor " +
		        "WHERE (npasp = ?);", npasp)) {
			if (acrs.getResultSet().next()) {
                return rsmRdNovor.map(acrs.getResultSet());
            } else
                throw new PatientNotFoundException();
		} catch (SQLException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException (getChildInfo): ", e);
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	public void updateChildInfo(final TRd_Novor Child)
			throws KmiacServerException, PatientNotFoundException {
        final int[] indexes = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 0};
        final String sqlQuery = "UPDATE c_rd_novor " +
        						"SET timeon = ?, kolchild = ?, nreb = ?, massa = ?, rost = ?, " +
        						"apgar1 = ?, apgar5 = ?, " +
        						"krit1 = ?, krit2 = ?, krit3 = ?, krit4 = ?, " +
        						"mert = ?, donosh = ? " +
        						"WHERE npasp = ?;";
		try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (isPatientExist(Child.getNpasp())) {
				sme.execPreparedT(sqlQuery, false, Child, CHILD_TYPES, indexes);
				sme.setCommit();
            } else
                throw new PatientNotFoundException();
		} catch (SQLException | InterruptedException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException | InterruptedException (updateChildInfo): ", e);
            throw new KmiacServerException();
		}
	}

	public int addChildDocument(final TRd_Svid_Rojd ChildDocument)
			throws KmiacServerException, PatientNotFoundException {
        final int[] indexes = {0, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11};
        final String sqlQuery = "INSERT INTO c_rd_svid_rojd " +
        						"(npasp, famreb, m_rojd, zan, r_proiz, svid_write, cdol_write, " +
        						"clpu, svid_give, cdol_give, dateoff) " +
        						"VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (isChildExist(ChildDocument.getNpasp())) {
                sme.execPreparedT(sqlQuery, true, ChildDocument, CHILDBIRTH_DOC_TYPES, indexes);
                int ndoc = sme.getGeneratedKeys().getInt("ndoc");
                sme.setCommit();
                return ndoc;
            } else
            	throw new PatientNotFoundException();
        } catch (SQLException | InterruptedException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException | InterruptedException (addChildDocument): ", e);
            throw new KmiacServerException();
        }
	}

	public TRd_Svid_Rojd getChildDocument(final int npasp)
			throws KmiacServerException, ChildDocNotFoundException {
	    try (AutoCloseableResultSet acrs = sse.execPreparedQuery(
		        "SELECT * FROM c_rd_svid_rojd " +
		        "WHERE (npasp = ?);", npasp)) {
			if (acrs.getResultSet().next()) {
                return rsmRdSvidRojd.map(acrs.getResultSet());
            } else
                throw new ChildDocNotFoundException();
		} catch (SQLException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException (getChildDocument): ", e);
			((SQLException) e.getCause()).printStackTrace();
			throw new KmiacServerException();
		}
	}

	public void updateChildDocument(final TRd_Svid_Rojd ChildDocument)
			throws KmiacServerException, ChildDocNotFoundException {
        final int[] indexes = {2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 0};
        //Поле ndoc изменять нельзя (в списке параметров не присутствует):
        final String sqlQuery = "UPDATE c_rd_svid_rojd " +
        						"SET famreb = ?, m_rojd = ?, zan = ?, r_proiz = ?, " +
        						"svid_write = ?, cdol_write = ?, clpu = ?, " +
        						"svid_give = ?, cdol_give = ?, dateoff = ? " +
        						"WHERE (npasp = ?);";
        try (SqlModifyExecutor sme = tse.startTransaction()) {
            if (isChildDocExist(ChildDocument.getNpasp())) {
                sme.execPreparedT(sqlQuery, false, ChildDocument, CHILDBIRTH_DOC_TYPES, indexes);
                sme.setCommit();
            } else
                throw new ChildDocNotFoundException();
        } catch (SQLException | InterruptedException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException | InterruptedException (updateChildDocument): ", e);
            throw new KmiacServerException();
        }
	}

	public TPatientCommonInfo getPatientCommonInfo(final int npasp)
			throws KmiacServerException, PatientNotFoundException {
        String sqlQuery = "SELECT p.npasp, p.fam || ' ' || p.im || ' ' || p.ot as full_name, p.datar, "
        		+ "n_z30.name as pol, n_am0.name as jitel, p.adp_obl, p.adp_gorod, p.adp_ul, "
        		+ "p.adp_dom, p.adp_korp, p.adp_kv, p.obraz, p.status "
                + "FROM patient p "
                + "LEFT JOIN n_z30 ON (n_z30.pcod = p.pol) "
                + "LEFT JOIN n_am0 ON (n_am0.pcod = p.jitel) "
                + "WHERE (p.npasp = ?);";
        ResultSet rs = null;
        try (AutoCloseableResultSet acrs = sse.execPreparedQuery(sqlQuery, npasp)) {
            rs = acrs.getResultSet();
            if (rs.next()) {
                return rsmCommonPatient.map(rs);
            } else {
                ServerHospital.log.log(Level.INFO,
                		"PatientNotFoundException (getPatientCommonInfo), patientId = " + npasp);
                throw new PatientNotFoundException();
            }
        } catch (SQLException e) {
            ServerHospital.log.log(Level.ERROR, "SQLException (getPatientCommonInfo): ", e);
			((SQLException) e.getCause()).printStackTrace();
            throw new KmiacServerException();
        }
	}

	public String printChildBirthDocument(final int ndoc)
			throws KmiacServerException, ChildDocNotFoundException,
			ChildbirthNotFoundException, PatientNotFoundException {
        final String pathToReturn;
        final String[] months = {"января", "февраля", "марта", "апреля", "мая", "июня",
        						"июля", "августа", "сентября", "октября", "ноября", "декабря"};
        if (isChildDocUnique(ndoc))	//Свидетельство с таким номером не существует
        	throw new ChildDocNotFoundException();
        //Загрузка данных:
        TRd_Svid_Rojd childDoc = getChildDocumentByDoc(ndoc);
        TRd_Novor childBirthInfo = getChildInfo(childDoc.getNpasp());
        TPatientCommonInfo childInfo = getPatientCommonInfo(childDoc.getNpasp());
        TPatientCommonInfo motherInfo = getPatientCommonInfo(getMotherId(childDoc.getNpasp()));
        int iFirstConsult = getFirstConsultWeekNum(childBirthInfo.getNrod());
        int iChildCount = getChildCountInChildbirth(childBirthInfo.getNrod());
        ArrayList<Integer> arrCod_sp = getDoctorCod_sp(getDoctorWhoGetChildId(childBirthInfo.getNrod()));
        TBirthPlace birthPlace = getChildBirthCityInfo(childDoc.getM_rojd());
        TInfoLPU infoLPU = getLPU_Info(childDoc.getClpu());
        Vector<String> doctorWriteDoc = getDoctorInfo(childDoc.getSvid_write(), childDoc.getCdol_write());
        Vector<String> doctorGiveDoc = getDoctorInfo(childDoc.getSvid_give(), childDoc.getCdol_give());
        //Создание документа:
        try (OutputStreamWriter osw = new OutputStreamWriter(
        		new FileOutputStream(
        			pathToReturn = File.createTempFile("svid_rojd_", ".htm").getAbsolutePath()
        		), "UTF-8")) {
        	File a = new File(this.getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getPath());
            HtmTemplate htmTemplate = new HtmTemplate(a.getParentFile().getParentFile().getAbsolutePath()
                    + ServerChildren.childBirthDocPath);
            //Номер свидетельства:
            String childBirthNumber = String.format("%6d", ndoc);
            childBirthNumber = childBirthNumber.replaceAll(" ", "0");
            //Форматы вывода даты:
            SimpleDateFormat sdfDay = new SimpleDateFormat("dd"), sdfMonth = new SimpleDateFormat("MMMMMMM"),
    			sdfMonthShort = new SimpleDateFormat("MM"), sdfYear = new SimpleDateFormat("yyyy");
            GregorianCalendar dateOff = new GregorianCalendar();
            dateOff.setTimeInMillis(childDoc.getDateoff());	//Дата выдачи мед. свидетельства
            //Местность регистрации матери:
            String city1 = "", city2 = "";
            String country1 = "", country2 = "";
            if (motherInfo.isSetJitel())
            	if (motherInfo.getJitel().toLowerCase().equals("городской"))
            	{
            		city1 = "<u>";
            		city2 = "</u>";
            	}
            	else
            		if (motherInfo.getJitel().toLowerCase().equals("сельский"))
                	{
            			country1 = "<u>";
            			country2 = "</u>";
                	}
            //Роды произошли:
            String birthHappen[] = new String[] {"", "", "", "", "", "", "", ""};
            birthHappen[2*(childDoc.getR_proiz() - 1)] = "<u>";
            birthHappen[2*childDoc.getR_proiz() - 1] = "</u>";
            //Место рождения:
            int iCityType = birthPlace.getType();
            String birthPlaceRegion = birthPlace.getRegion();
            String birthPlaceTown = (iCityType > 0) ? birthPlace.getCity() : "";
            //Местность рождения:
            String cityChild1 = "", cityChild2 = "";
            String countryChild1 = "", countryChild2 = "";
            if ((iCityType == 1) || (iCityType == 2)) {
            	cityChild1 = "<u>";
            	cityChild2 = "</u>";
            } else {
            	countryChild1 = "<u>";
            	countryChild2 = "</u>";
            }
            //Пол новорождённого:
            String boy1 = "", boy2 = "";
            String girl1 = "", girl2 = "";
            if (childInfo.isSetPol()) {
            	String childGenderLower = childInfo.getPol().toLowerCase();
            	if (childGenderLower.equals("мужской"))
            	{
            		boy1 = "<u>";
            		boy2 = "</u>";
            	}
            	else
            		if (childGenderLower.equals("женский"))
                	{
            			girl1 = "<u>";
            			girl2 = "</u>";
                	}
            }
            //Полное имя матери:
            String[] motherNameArr = getNormalRegWords(motherInfo.getFull_name());
            final String motherMiddleName = motherNameArr[1].concat(" ".concat(motherNameArr[2]));
            //Время рождения:
            final String childBirthTime = (childBirthInfo.isSetTimeon()) ? childBirthInfo.getTimeon() : "";
            String childBirthHour = "", childBirthMinute = "";
            if (childBirthInfo.isSetTimeon()) {
	            childBirthHour = childBirthTime.substring(0, 2);
	            childBirthMinute = childBirthTime.substring(3, 5);
            }
            //Образование матери:
            int iEduc = (motherInfo.isSetObraz()) ? motherInfo.getObraz() : 0;
            String[] motherEduc = new String[] {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
            if ((iEduc > 0) && (iEduc < 10)) {
	            motherEduc[2*(iEduc - 1)] = "<u>";
	            motherEduc[2*iEduc - 1] = "</u>";
            } else {	//Неизвестный код образования - ставим "Неизвестно":
	            motherEduc[16] = "<u>";
	            motherEduc[17] = "</u>";
            }
            //Семейное положение матери:
            int iStatus = motherInfo.isSetStatus() ? motherInfo.getStatus() : 0;
            String[] motherStatus = new String[] {"", "", "", "", "", ""};
            if ((iStatus > 0) && (iStatus < 5)) {
            	if (iStatus == 1) {	//Состоит в браке
	            	motherStatus[0] = "<u>";
	            	motherStatus[1] = "</u>";
            	} else {			//Не состоит в браке
	            	motherStatus[2] = "<u>";
	            	motherStatus[3] = "</u>";
            	}
            } else {	//Неизвестный код семейного положения - ставим "Неизвестно":
            	motherStatus[4] = "<u>";
            	motherStatus[5] = "</u>";
            }            
            //Занятость матери:
            String[] motherWork = new String[] {"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", ""};
            if ((childDoc.getZan() > 0) && (childDoc.getZan() < 11)) {
            	motherWork[2*(childDoc.getZan() - 1)] = "<u>";
            	motherWork[2*childDoc.getZan() - 1] = "</u>";
            } else {	//Неизвестный код занятости - ставим "Прочие":
            	motherWork[18] = "<u>";
            	motherWork[19] = "</u>";
            }
            //Срок первой явки к врачу:
            String firstConsult = "  ";
            if (iFirstConsult >= 0)
            	firstConsult = String.format("%2d", iFirstConsult);
            //Который по счёту:
            String nChildren = "  ";
            if (childBirthInfo.isSetKolchild())
            	nChildren = String.format("%2d", childBirthInfo.getKolchild());
            //Вес:
            String weight = "    ";
            if (childBirthInfo.isSetMassa())
            	weight = String.format("%4d", childBirthInfo.getMassa());
            //Рост:
            String height = "  ";
            if (childBirthInfo.isSetRost())
            	height = String.format("%2d", childBirthInfo.getRost());
            //Одноплодные\многоплодные роды:
            String only = "", nreb = "", nreb_all = "";
            if (childBirthInfo.isSetNreb())
            {
            	if (childBirthInfo.getNreb() == 0)
            		only = "V";
            	else {
            		nreb = String.format("%d", childBirthInfo.getNreb());
            		nreb_all = String.format("%d", iChildCount);
            	}
            }
    		//Лицо, принимавшее роды:
            boolean isKnownDoctor = false;
            String[] whoGetChild = new String[] {"", "", "", "", "", ""};
            if (arrCod_sp != null)
	        	for (int curCode : arrCod_sp) {
	        		//Акушер-гинеколог:
					if (curCode == 1) {
						whoGetChild[0] = "<u>";
						whoGetChild[1] = "</u>";
						isKnownDoctor = true;
						break;
					}
					//Фельдшер:
					if (curCode == 167) {
						whoGetChild[2] = "<u>";
						whoGetChild[3] = "</u>";
						isKnownDoctor = true;
						break;
					}
				}
            if (!isKnownDoctor) {
            	whoGetChild[4] = "<u>";
            	whoGetChild[5] = "</u>";
            }
            //Место регистрации матери:
            String[] motherRegPlace = new String[] {
        		(motherInfo.isSetAdp_obl()) ? setNormalNameReg(motherInfo.getAdp_obl()) : "",
        		"",	//РАЙОН РЕГИСТРАЦИИ МАТЕРИ
        		(motherInfo.isSetAdp_gorod()) ? setNormalNameReg(motherInfo.getAdp_gorod()) : "",
				(motherInfo.isSetAdp_ul()) ? setNormalNameReg(motherInfo.getAdp_ul()) : "",
				(motherInfo.isSetAdp_dom()) ? motherInfo.getAdp_dom() : "",
				(motherInfo.isSetAdp_korp()) ? " ".concat(motherInfo.getAdp_korp()) : "",
				(motherInfo.isSetAdp_kv()) ? motherInfo.getAdp_kv() : "",
            };
            //Запись данных в шаблон:
            htmTemplate.replaceLabels(false,
            	//Серия и номер свидетельства:
        		ServerChildren.childBirthDocSeries, childBirthNumber,
        		//Дата выдачи:
        		sdfDay.format(childDoc.getDateoff()), months[dateOff.get(GregorianCalendar.MONTH)],
        		sdfYear.format(childDoc.getDateoff()),
        		//Ребенок родился:
        		sdfDay.format(childInfo.getDatar()), sdfMonth.format(childInfo.getDatar()),
        		sdfYear.format(childInfo.getDatar()),
        		childBirthHour, childBirthMinute,
        		//Фамилия, имя, отчество матери:
        		motherNameArr[0].concat(" ".concat(motherMiddleName)),
        		//Дата рождения матери:
        		sdfDay.format(motherInfo.getDatar()), sdfMonth.format(motherInfo.getDatar()),
        		sdfYear.format(motherInfo.getDatar()),
        		//Место регистрации матери:
        		motherRegPlace[0],
        		motherRegPlace[1],	//РАЙОН РЕГИСТРАЦИИ МАТЕРИ
        		motherRegPlace[2],
        		motherRegPlace[3],
        		motherRegPlace[4].concat(motherRegPlace[5]),
        		motherRegPlace[6],
				//Местность регистрации:
        		city1, city2, country1, country2,
        		//Пол ребёнка:
        		boy1, boy2, girl1, girl2,
        		//Информация об организации:
        		"",	//КОД ФОРМЫ ПО ОКУД
        		(infoLPU.isSetName()) ? infoLPU.getName() : "",
        		(infoLPU.isSetAdres()) ? infoLPU.getAdres() : "",
        		(infoLPU.isSetOkpo() && (infoLPU.getOkpo() > 0)) ? String.format("%d", infoLPU.getOkpo()) : "",
            	//Серия и номер свидетельства:
				ServerChildren.childBirthDocSeries, childBirthNumber,
        		//Дата выдачи:
        		sdfDay.format(childDoc.getDateoff()), months[dateOff.get(GregorianCalendar.MONTH)],
        		sdfYear.format(childDoc.getDateoff()),
        		//Ребенок родился:
        		sdfDay.format(childInfo.getDatar()),
        		sdfMonth.format(childInfo.getDatar()),
        		sdfYear.format(childInfo.getDatar()),
        		childBirthHour, childBirthMinute,
        		//Фамилия, имя, отчество матери:
        		motherNameArr[0], motherMiddleName,
        		//Фамилия ребёнка:
        		setNormalNameReg(childDoc.getFamreb()),
        		//Дата рождения матери:
        		sdfDay.format(motherInfo.getDatar()).substring(0, 1),
        		sdfDay.format(motherInfo.getDatar()).substring(1, 2),
        		sdfMonthShort.format(motherInfo.getDatar()).substring(0, 1),
        		sdfMonthShort.format(motherInfo.getDatar()).substring(1, 2),
        		sdfYear.format(motherInfo.getDatar()).substring(0, 1),
        		sdfYear.format(motherInfo.getDatar()).substring(1, 2),
        		sdfYear.format(motherInfo.getDatar()).substring(2, 3),
        		sdfYear.format(motherInfo.getDatar()).substring(3, 4),
        		//Место регистрации матери:
        		motherRegPlace[0],
        		motherRegPlace[1],	//РАЙОН РЕГИСТРАЦИИ МАТЕРИ
        		motherRegPlace[2],
        		motherRegPlace[3],
        		motherRegPlace[4].concat(motherRegPlace[5]),
        		motherRegPlace[6],
        		//Местность регистрации:
        		city1, city2, country1, country2,
        		//Семейное положение матери:
        		motherStatus[0], motherStatus[1],
        		motherStatus[2], motherStatus[3],
        		motherStatus[4], motherStatus[5],
        		//Место рождения:
        		setNormalNameReg(birthPlaceRegion),
        		"",	//РАЙОН РОЖДЕНИЯ
        		setNormalNameReg(birthPlaceTown),
        		//Местность рождения:
    			cityChild1, cityChild2,
    			countryChild1, countryChild2,
    			//Роды произошли:
    			birthHappen[0], birthHappen[1],
    			birthHappen[2], birthHappen[3],
    			birthHappen[4], birthHappen[5],
    			birthHappen[6], birthHappen[7],
        		//Пол ребёнка:
        		boy1, boy2, girl1, girl2,
    			//Роды произошли:
    			birthHappen[0], birthHappen[1],
    			birthHappen[2], birthHappen[3],
    			birthHappen[4], birthHappen[5],
    			birthHappen[6], birthHappen[7],
        		//Должность и имя врача, выдавшего свидетельство:
        		(doctorGiveDoc != null) ? setNormalNameReg(doctorGiveDoc.get(1)) : "",
        		(doctorGiveDoc != null) ? getConcatStrFromAray(getNormalRegWords(doctorGiveDoc.get(0)), " ") : "",
        		//Образование матери:
        		motherEduc[0], motherEduc[1],
        		motherEduc[2], motherEduc[3],
        		motherEduc[4], motherEduc[5],
        		motherEduc[6], motherEduc[7],
        		motherEduc[8], motherEduc[9],
        		motherEduc[10], motherEduc[11],
        		motherEduc[12], motherEduc[13],
        		motherEduc[14], motherEduc[15],
        		motherEduc[16], motherEduc[17],
        		//Занятость матери:
        		motherWork[0], motherWork[1],
        		motherWork[0], motherWork[1],
        		motherWork[2], motherWork[3],
        		motherWork[4], motherWork[5],
        		motherWork[6], motherWork[7],
        		motherWork[8], motherWork[9],
        		motherWork[10], motherWork[11],
        		motherWork[12], motherWork[13],
        		motherWork[14], motherWork[15],
        		motherWork[14], motherWork[15],
        		motherWork[16], motherWork[17],
        		motherWork[18], motherWork[19],
        		//Срок первой явки к врачу:
        		firstConsult.substring(0, 1), firstConsult.substring(1, 2),
        		//Которым по счету ребенок был рожден у матери:
        		nChildren.substring(0, 1), nChildren.substring(1, 2),
        		//Масса тела при рождении:
        		weight.substring(0, 1), weight.substring(1, 2),
        		weight.substring(2, 3), weight.substring(3, 4),
        		//Длина  тела при рождении:
        		height.substring(0, 1), height.substring(1, 2),
        		only, nreb, nreb_all,
        		//Лицо, принимавшее роды:
        		whoGetChild[0], whoGetChild[1],
        		whoGetChild[2], whoGetChild[3],
        		whoGetChild[4], whoGetChild[5],
        		//Должность и имя врача, заполнившего свидетельство:
        		(doctorWriteDoc != null) ? setNormalNameReg(doctorWriteDoc.get(1)) : "",
        		(doctorWriteDoc != null) ? getConcatStrFromAray(getNormalRegWords(doctorWriteDoc.get(0)), " ") : "",
        		//Имя руководителя организации:
        		(infoLPU.isSetZaved()) ? infoLPU.getZaved() : ""
        		);
            osw.write(htmTemplate.getTemplateText());
            return pathToReturn;
        } catch (Exception e) {
            ServerHospital.log.log(Level.ERROR, "Exception: ", e);
            throw new KmiacServerException();
        }
	}

	public String printChildBirthBlankDocument()
			throws KmiacServerException, TException {
        final String blankDocPath;
        try (OutputStreamWriter osw = new OutputStreamWriter(
        		new FileOutputStream(
        			blankDocPath = File.createTempFile("svid_blank_", ".htm").getAbsolutePath()
        		), "UTF-8")) {
        	File a = new File(this.getClass().getProtectionDomain().getCodeSource()
                    .getLocation().getPath());
            HtmTemplate htmTemplate = new HtmTemplate(a.getParentFile().getParentFile().
            		getAbsolutePath() + ServerChildren.childBirthDocPath);
            htmTemplate.replaceLabel("~seria", ServerChildren.childBirthDocSeries);
            htmTemplate.replaceLabel("~seria", ServerChildren.childBirthDocSeries);
            htmTemplate.replaceLabel("~ndoc", "______");
            htmTemplate.replaceLabel("~ndoc", "______");
            htmTemplate.replaceLabels(true);
	        osw.write(htmTemplate.getTemplateText());
	        return blankDocPath;
	    } catch (Exception e) {
            ServerHospital.log.log(Level.ERROR, "Exception: ", e);
	        throw new KmiacServerException();
	    }
	}
}
