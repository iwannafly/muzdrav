package ru.nkz.ivcgzo.serverManager.common.DbfReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.NClob;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.RowId;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.SQLXML;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Map;

public class DbfResultSet implements ResultSet, AutoCloseable {
	private FileInputStream str;
	private long startPos;
	private DbfHeader hdr;
	private DbfResultSetMetaData rsmd;
	private DbfColumn[] columns;
	private Hashtable<String, Integer> names;
	private boolean closed;
	private boolean isNull;
	private int row;
	
	@SuppressWarnings("unused")
	private class DbfHeader {
		private static final int hdrLen = 32;
		private static final byte version = 3;
		private byte year;
		private byte month;
		private byte day;
		private int recCnt;
		private short dataPos;
		private short turpleLen;
		
		private DbfHeader() throws IOException {
			if (str.read() != DbfHeader.version)
				throw new IOException("Unsupported dbf version.");
			year = (byte) str.read();
			month = (byte) str.read();
			day = (byte) str.read();
			recCnt = (int) readNumber(11);
			dataPos = (short) readNumber(6);
			turpleLen = (short) readNumber(6);
			
			str.skip(20);
		}
	}
	
	private class DbfColumn {
		private boolean isNull;
		private Object value;
		
		private DbfColumn(boolean isNull, Object value) {
			this.isNull = isNull;
			this.value = value;
		}
		
		@Override
		public String toString() {
			return String.format("isNull: %s, type: %s, value: %s", isNull, (!isNull) ? value.getClass() : "none", value);
		}
	}
	
	public DbfResultSet(String fileName) throws IOException {
		this(new FileInputStream(fileName));
		
		closed = false;
	}
	
	public DbfResultSet(FileInputStream dbfStream) throws IOException {
		str = dbfStream;
		startPos = str.getChannel().position();
		closed = true;
		
		readHeader();
		readMetadata();
		generateNames();
	}
	
	private void readHeader() throws IOException {
		hdr = new DbfHeader();
	}
	
	private void readMetadata() throws IOException {
		rsmd = new DbfResultSetMetaData(str);
	}
	
	private void generateNames() throws IOException {
		try {
			names = new Hashtable<>(getMetaData().getColumnCount());
			
			for (int i = 1; i < getMetaData().getColumnCount() + 1; i++) {
				names.put(getMetaData().getColumnName(i), i);
			}
			
			if (names.size() != getMetaData().getColumnCount())
				System.out.println("Warning: dbf contains duplicate column names.");
		} catch (SQLException e) {
			throw new IOException("Error generating names.");
		}
	}
	
	private void readRow() throws SQLException, IOException {
		byte[] buf;
		boolean isNull;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		columns = new DbfColumn[getMetaData().getColumnCount() + 1];
		
		try {
			for (int i = 1; i < columns.length; i++) {
				buf = new byte[getMetaData().getPrecision(i)];
				str.read(buf);
				isNull = bufferIsNull(buf);
				switch (getMetaData().getColumnType(i)) {
				case java.sql.Types.SMALLINT:
					columns[i] = new DbfColumn(isNull, (isNull) ? 0 : Short.parseShort(new String(buf).trim()));
					break;
				case java.sql.Types.INTEGER:
					columns[i] = new DbfColumn(isNull, (isNull) ? 0 : Integer.parseInt(new String(buf).trim()));
					break;
				case java.sql.Types.BIGINT:
					columns[i] = new DbfColumn(isNull, (isNull) ? 0 : Long.parseLong(new String(buf).trim()));
					break;
				case java.sql.Types.REAL:
					columns[i] = new DbfColumn(isNull, (isNull) ? 0 : Float.parseFloat(new String(buf).trim()));
					break;
				case java.sql.Types.DOUBLE:
					columns[i] = new DbfColumn(isNull, (isNull) ? 0 : Double.parseDouble(new String(buf).trim()));
					break;
				case java.sql.Types.CHAR:
					columns[i] = new DbfColumn(isNull, (isNull) ? null : new String(buf, "Cp866"));
					break;
				case java.sql.Types.DATE:
					columns[i] = new DbfColumn(isNull, (isNull) ? null : sdf.parse(new String(buf)));
					break;
				default:
					throw new SQLException(String.format("Unsupported data type.", getMetaData().getColumnType(i)));
				}
			}
		} catch (NumberFormatException e) {
			throw new SQLException("Error reading number.", e);
		} catch (ParseException e) {
			throw new SQLException("Error reading date.", e);
		}
		
		row++;
	}
	
	private long readNumber(int precision) throws IOException {
		long number;
		
		if (precision < 7) {
			byte[] buf = new byte[2];
			str.read(buf);
			number = buf[0] & 0xff;
			number |= (buf[1] & 0xff) << 0x08;
		} else if (precision < 12) {
			byte[] buf = new byte[4];
			str.read(buf);
			number = buf[0] & 0xff;
			number |= (buf[1] & 0xff) << 0x08;
			number |= (buf[2] & 0xff) << 0x10;
			number |= (buf[3] & 0xff) << 0x18;
		} else {
			byte[] buf = new byte[8];
			str.read(buf);
			number = buf[0] & 0xff;
			number |= (buf[1] & 0xff) << 0x08;
			number |= (buf[2] & 0xff) << 0x10;
			number |= (buf[3] & 0xff) << 0x18;
			number |= (buf[4] & 0xff) << 0x20;
			number |= (buf[5] & 0xff) << 0x28;
			number |= (buf[6] & 0xff) << 0x30;
			number |= (buf[7] & 0xff) << 0x38;
		}
		
		return number;
	}
	
	private boolean bufferIsNull(final byte[] buffer) {
		for (int i = 0; i < buffer.length; i++) {
			if (buffer[i] != 32)
				return false;
		}
		
		return true;
	}
	
	@Override
	public ResultSetMetaData getMetaData() throws SQLException {
		return rsmd;
	}
	
	@Override
	public void close() throws SQLException {
		try {
			if (!isClosed())
				str.close();
			
			closed = true;
		} catch (IOException e) {
			throw new SQLException("Error closing dbf stream.", e);
		}
	}
	
	@Override
	public boolean isClosed() throws SQLException {
		return closed;
	}
	
	@Override
	public boolean isBeforeFirst() throws SQLException {
		try {
			return (str.getChannel().position() - startPos) < hdr.dataPos;
		} catch (IOException e) {
			throw new SQLException("Could not determine isBeforeFirst.", e);
		}
	}
	
	@Override
	public boolean isAfterLast() throws SQLException {
		try {
			return (str.getChannel().position() - startPos) > hdr.dataPos + (hdr.turpleLen * (hdr.recCnt - 1));
		} catch (IOException e) {
			throw new SQLException("Could not determine isAfterLast.", e);
		}
	}
	
	@Override
	public boolean isFirst() throws SQLException {
		try {
			return (str.getChannel().position() - startPos) == hdr.dataPos;
		} catch (IOException e) {
			throw new SQLException("Could not determine isFirst.", e);
		}
	}
	
	@Override
	public boolean isLast() throws SQLException {
		try {
			return (str.getChannel().position() - startPos) == hdr.dataPos + (hdr.turpleLen * (hdr.recCnt - 1));
		} catch (IOException e) {
			throw new SQLException("Could not determine isLast.", e);
		}
	}
	
	@Override
	public boolean next() throws SQLException {
		if (isAfterLast())
			return false;
		
		try {
			if (isBeforeFirst()) {
				str.getChannel().position(startPos + hdr.dataPos);
				row = 0;
			}
		} catch (IOException e) {
			throw new SQLException("Error setting first position.");
		}
		
		try {
			if (str.read() != 32)
				throw new IOException("Properties are not supported.");
			readRow();
		} catch (IOException e) {
			throw new SQLException("Error reading row.", e);
		}
		
		return true;
	}
	
	@Override
	public boolean wasNull() throws SQLException {
		return isNull;
	}
	
	@Override
	public Object getObject(int columnIndex) throws SQLException {
		if (columns == null)
			throw new SQLException("Result set is not positioned properly.");
		if (columnIndex == 0)
			throw new SQLException("Count in result sets start from 1. Ewww.");
		
		DbfColumn res = columns[columnIndex];
		
		isNull = res.isNull;
		return res.value;
	}
	
	@Override
	public String getString(int columnIndex) throws SQLException {
		return getObject(columnIndex).toString();
	}
	
	@Override
	public short getShort(int columnIndex) throws SQLException {
		return (short) (long) getObject(columnIndex);
	}
	
	@Override
	public int getInt(int columnIndex) throws SQLException {
		return (int) (long) getObject(columnIndex);
	}
	
	@Override
	public long getLong(int columnIndex) throws SQLException {
		return (long) getObject(columnIndex);
	}
	
	@Override
	public float getFloat(int columnIndex) throws SQLException {
		return (float) (double) getObject(columnIndex);
	}
	
	@Override
	public double getDouble(int columnIndex) throws SQLException {
		return (double) getObject(columnIndex);
	}
	
	@Override
	public Date getDate(int columnIndex) throws SQLException {
		return (Date) getObject(columnIndex);
	}
	
	@Override
	public Time getTime(int columnIndex) throws SQLException {
		return new Time(((Date) getObject(columnIndex)).getTime());
	}
	
	@Override
	public Timestamp getTimestamp(int columnIndex) throws SQLException {
		return new Timestamp(((Date) getObject(columnIndex)).getTime());
	}
	
	@Override
	public Object getObject(String columnLabel) throws SQLException {
		return getObject(findColumn(columnLabel));
	}
	
	@Override
	public String getString(String columnLabel) throws SQLException {
		return getObject(columnLabel).toString();
	}
	
	@Override
	public short getShort(String columnLabel) throws SQLException {
		return (short) (long) getObject(columnLabel);
	}
	
	@Override
	public int getInt(String columnLabel) throws SQLException {
		return (int) (long) getObject(columnLabel);
	}
	
	@Override
	public long getLong(String columnLabel) throws SQLException {
		return (long) getObject(columnLabel);
	}
	
	@Override
	public float getFloat(String columnLabel) throws SQLException {
		return (float) (double) getObject(columnLabel);
	}

	@Override
	public double getDouble(String columnLabel) throws SQLException {
		return (double) getObject(columnLabel);
	}
	
	@Override
	public Date getDate(String columnLabel) throws SQLException {
		return (Date) getObject(columnLabel);
	}
	
	@Override
	public Time getTime(String columnLabel) throws SQLException {
		return new Time(((Date) getObject(columnLabel)).getTime());
	}
	
	@Override
	public Timestamp getTimestamp(String columnLabel) throws SQLException {
		return new Timestamp(((Date) getObject(columnLabel)).getTime());
	}
	
	@Override
	public int findColumn(String columnLabel) throws SQLException {
		if (!names.containsKey(columnLabel))
			throw new SQLException("Wrong column name.");
		
		return names.get(columnLabel);
	}
	
	@Override
	public int getRow() throws SQLException {
		return row;
	}
	
	@Override
	public BigDecimal getBigDecimal(int columnIndex, int scale) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}
	
	@Override
	public BigDecimal getBigDecimal(String columnLabel, int scale) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}
	
	@Override
	public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}
	
	@Override
	public BigDecimal getBigDecimal(String columnLabel) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}
	
	@Override
	public Date getDate(int columnIndex, Calendar cal) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Date getDate(String columnLabel, Calendar cal) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Time getTime(int columnIndex, Calendar cal) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Time getTime(String columnLabel, Calendar cal) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Timestamp getTimestamp(int columnIndex, Calendar cal) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Timestamp getTimestamp(String columnLabel, Calendar cal) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Object getObject(int columnIndex, Map<String, Class<?>> map) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Object getObject(String columnLabel, Map<String, Class<?>> map) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public <T> T getObject(int columnIndex, Class<T> type) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public <T> T getObject(String columnLabel, Class<T> type) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public boolean getBoolean(int columnIndex) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public boolean getBoolean(String columnLabel) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void beforeFirst() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void afterLast() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public boolean first() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public boolean last() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public boolean previous() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public byte getByte(int columnIndex) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public <T> T unwrap(Class<T> iface) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public boolean isWrapperFor(Class<?> iface) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public byte[] getBytes(int columnIndex) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public InputStream getAsciiStream(int columnIndex) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public InputStream getUnicodeStream(int columnIndex) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public InputStream getBinaryStream(int columnIndex) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public byte getByte(String columnLabel) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public byte[] getBytes(String columnLabel) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public InputStream getAsciiStream(String columnLabel) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public InputStream getUnicodeStream(String columnLabel) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public InputStream getBinaryStream(String columnLabel) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public SQLWarning getWarnings() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void clearWarnings() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public String getCursorName() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Reader getCharacterStream(int columnIndex) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Reader getCharacterStream(String columnLabel) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public boolean absolute(int row) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public boolean relative(int rows) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void setFetchDirection(int direction) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public int getFetchDirection() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void setFetchSize(int rows) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public int getFetchSize() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public int getType() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public int getConcurrency() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public boolean rowUpdated() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public boolean rowInserted() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public boolean rowDeleted() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateNull(int columnIndex) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateBoolean(int columnIndex, boolean x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateByte(int columnIndex, byte x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateShort(int columnIndex, short x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateInt(int columnIndex, int x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateLong(int columnIndex, long x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateFloat(int columnIndex, float x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateDouble(int columnIndex, double x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateBigDecimal(int columnIndex, BigDecimal x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateString(int columnIndex, String x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateBytes(int columnIndex, byte[] x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateDate(int columnIndex, Date x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateTime(int columnIndex, Time x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateTimestamp(int columnIndex, Timestamp x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, int length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, int length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateObject(int columnIndex, Object x, int scaleOrLength) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateObject(int columnIndex, Object x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateNull(String columnLabel) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateBoolean(String columnLabel, boolean x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateByte(String columnLabel, byte x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateShort(String columnLabel, short x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateInt(String columnLabel, int x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateLong(String columnLabel, long x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateFloat(String columnLabel, float x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateDouble(String columnLabel, double x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateBigDecimal(String columnLabel, BigDecimal x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateString(String columnLabel, String x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateBytes(String columnLabel, byte[] x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateDate(String columnLabel, Date x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateTime(String columnLabel, Time x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateTimestamp(String columnLabel, Timestamp x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, int length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, int length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateObject(String columnLabel, Object x, int scaleOrLength) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateObject(String columnLabel, Object x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void insertRow() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateRow() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void deleteRow() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void refreshRow() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void cancelRowUpdates() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void moveToInsertRow() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void moveToCurrentRow() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Statement getStatement() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Ref getRef(int columnIndex) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Blob getBlob(int columnIndex) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Clob getClob(int columnIndex) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Array getArray(int columnIndex) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Ref getRef(String columnLabel) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Blob getBlob(String columnLabel) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Clob getClob(String columnLabel) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Array getArray(String columnLabel) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public URL getURL(int columnIndex) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public URL getURL(String columnLabel) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateRef(int columnIndex, Ref x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateRef(String columnLabel, Ref x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateBlob(int columnIndex, Blob x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateBlob(String columnLabel, Blob x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateClob(int columnIndex, Clob x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateClob(String columnLabel, Clob x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateArray(int columnIndex, Array x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateArray(String columnLabel, Array x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public RowId getRowId(int columnIndex) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public RowId getRowId(String columnLabel) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateRowId(int columnIndex, RowId x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateRowId(String columnLabel, RowId x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public int getHoldability() throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateNString(int columnIndex, String nString) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateNString(String columnLabel, String nString) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateNClob(int columnIndex, NClob nClob) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateNClob(String columnLabel, NClob nClob) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public NClob getNClob(int columnIndex) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public NClob getNClob(String columnLabel) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public SQLXML getSQLXML(int columnIndex) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public SQLXML getSQLXML(String columnLabel) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateSQLXML(int columnIndex, SQLXML xmlObject) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateSQLXML(String columnLabel, SQLXML xmlObject) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public String getNString(int columnIndex) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public String getNString(String columnLabel) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Reader getNCharacterStream(int columnIndex) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public Reader getNCharacterStream(String columnLabel) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x, long length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x, long length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x, long length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader, long length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream, long length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream, long length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader, long length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader, long length) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateNCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateNCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateAsciiStream(int columnIndex, InputStream x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateBinaryStream(int columnIndex, InputStream x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateCharacterStream(int columnIndex, Reader x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateAsciiStream(String columnLabel, InputStream x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateBinaryStream(String columnLabel, InputStream x) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateCharacterStream(String columnLabel, Reader reader) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateBlob(int columnIndex, InputStream inputStream) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateBlob(String columnLabel, InputStream inputStream) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateClob(int columnIndex, Reader reader) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateClob(String columnLabel, Reader reader) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateNClob(int columnIndex, Reader reader) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public void updateNClob(String columnLabel, Reader reader) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}
}
