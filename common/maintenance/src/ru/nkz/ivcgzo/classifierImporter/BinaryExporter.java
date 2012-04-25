package ru.nkz.ivcgzo.classifierImporter;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;

import ru.nkz.ivcgzo.dbConnection.DBConnection;
import ru.nkz.ivcgzo.misc.Misc;

public class BinaryExporter {
	private DBConnection conn;
	
	public BinaryExporter(DBConnection conn) {
		this.conn = conn;
	}
	
	public Path exportToFile(String tableName) throws Exception {
		Path binFile = Misc.createTempFile();
		
		switch (conn.databaseParams.type) {
		case oracle:
			exportOracle(tableName, binFile);
			break;
		default:
			throw new Exception(String.format("Binary table export not yet implemented for %s database.", conn.databaseParams.type));
		}
			
		return binFile;
	}
	
	private void exportOracle(String tableName, Path binFile) throws Exception {
		try (
				RandomAccessFile raf = new RandomAccessFile(binFile.toString(), "rw");
				Statement stm = conn.createStatement()) {
			setHeader(raf);
			writeTurples(raf, stm, tableName);
			putEndTurple(raf);
		}
	}
	
	private void setHeader(RandomAccessFile raf) throws IOException {
		raf.write(new byte[]{0x50, 0x47, 0x43, 0x4F, 0x50, 0x59, 0x0A, (byte) 0xFF, 0x0D, 0x0A, 0x00}); //signature PGCOPY\n\377\r\n\0
		raf.writeInt(0); //flags field, no OIDs
		raf.writeInt(0); //header extension area length
	}
	
	private void writeTurples(RandomAccessFile raf, Statement stm, String tableName) throws SQLException, IOException {
		try (ResultSet data = conn.executeQuery(stm, String.format("SELECT * FROM %s.%s ", conn.databaseParams.name, tableName))) {
			ResultSetMetaData meta = data.getMetaData();
			while (data.next()) {
				putTurple(raf, getTurple(data, meta));
			}
		}
	}
	
	private byte[][] getTurple(ResultSet data, ResultSetMetaData meta) throws SQLException {
		byte[][] turple = new byte[meta.getColumnCount()][];
		
		for (int i = 0; i < turple.length; i++) {
			switch (meta.getColumnType(i + 1)) {
			case Types.NUMERIC:
				if (meta.getScale(i + 1) > 0)
					turple[i] = numericToFloat(data.getDouble(i + 1), meta.getScale(i + 1));
				else
					turple[i] = numericToBytes(data.getLong(i + 1), meta.getPrecision(i + 1));
				break;
			case Types.CHAR:
			case Types.VARCHAR:
				turple[i] = varcharToUtf8(data.getString(i + 1));
				break;
//			case Types.TIMESTAMP:
//				turple[i] = numericToBytes(data.getTimestamp(i + 1).getTime(), 14);
//				break;
			default:
				throw new SQLException(String.format("Unsupportes data type: %d.", meta.getColumnType(i + 1)));
			}
		}
		return turple;
	}
	
	private byte[] numericToFloat(double number, int scale) {
//		if (scale < 25)
//			return numericToBytes(Float.floatToIntBits((float) number), 10);
//		else
		return numericToBytes(Double.doubleToLongBits(number), 11);
	}
	
	private byte[] numericToBytes(long number, int precision) {
//		if (precision < 6) {
//			byte[] buf = new byte[2];
//			buf[0] = (byte) (number >> 0x08);
//			buf[1] = (byte) (number);
//			return buf;
//		} else 
		if (precision < 11) {
			byte[] buf = new byte[4];
			buf[0] = (byte) (number >> 0x18);
			buf[1] = (byte) (number >> 0x10);
			buf[2] = (byte) (number >> 0x08);
			buf[3] = (byte) (number);
			return buf;
		} else {
			byte[] buf = new byte[8];
			buf[0] = (byte) (number >> 0x38);
			buf[1] = (byte) (number >> 0x30);
			buf[2] = (byte) (number >> 0x28);
			buf[3] = (byte) (number >> 0x20);
			buf[4] = (byte) (number >> 0x18);
			buf[5] = (byte) (number >> 0x10);
			buf[6] = (byte) (number >> 0x08);
			buf[7] = (byte) (number);
			return buf;
		}
	}
	
	private byte[] varcharToUtf8(String str) throws SQLException {
		try {
			if (str == null)
				return null;
			else
				return str.getBytes("utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new SQLException(e);
		}
	}
	
	private void putTurple(RandomAccessFile raf, byte[][] turple) throws IOException {
		raf.writeShort(turple.length);
		for (byte[] turpleItem : turple) {
			if (turpleItem != null) {
				raf.writeInt(turpleItem.length);
				raf.write(turpleItem);
			} else
				raf.writeInt(-1);
		}
	}
	
	private void putEndTurple(RandomAccessFile raf) throws IOException {
		raf.writeShort(-1);
	}
}
