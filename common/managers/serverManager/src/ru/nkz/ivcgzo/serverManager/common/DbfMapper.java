package ru.nkz.ivcgzo.serverManager.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Класс для отображения полей результирующего набора в DBF.
 * @author bsv798
 */
public class DbfMapper {
	private ResultSet rs;
	private DbfHeader hdr;
	private List<DbfRecord> recs;
	private List<byte[]> datas;
	private SimpleDateFormat sdf;
	
	private class DbfHeader {
		private static final int hdrLen = 32;
		private static final byte version = 3;
		private final byte year = (byte) (Integer.parseInt(new SimpleDateFormat("yyyy").format(System.currentTimeMillis())) - 1900);
		private final byte month = Byte.parseByte(new SimpleDateFormat("MM").format(System.currentTimeMillis()));
		private final byte day = Byte.parseByte(new SimpleDateFormat("dd").format(System.currentTimeMillis()));
		private int recCnt;
		private short dataPos;
		private short turpleLen;
	}
	
	private class DbfRecord {
		private static final int recLen = 32;
		private static final int nameLen = 11;
		private String name;
		private byte type;
		private int precision;
		private int scale;
	}
	
	/**
	 * Конструктор класса.
	 * @param rs - результирующий набор, на основании которого будет
	 * создаваться DBF
	 */
	public DbfMapper(ResultSet rs) {
		this.rs = rs;
		
		hdr = new DbfHeader();
		sdf = new SimpleDateFormat("yyyyMMdd");
	}
	
	/**
	 * Отображение набора в поток.
	 */
	public InputStream mapToStream() throws IOException, SQLException {
		try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
			createRecordList();
			createDataList();
			writeToStream(bos);
			
			return new ByteArrayInputStream(bos.toByteArray());
		}
	}
	
	/**
	 * Отображение набора в указанный файл.
	 */
	public void mapToFile(String path) throws FileNotFoundException, IOException, SQLException {
		try (ByteArrayInputStream bis = (ByteArrayInputStream) mapToStream();
				FileOutputStream fos = new FileOutputStream(path)) {
			byte[] buf = new byte[2048];
			int read;
			
			while ((read = bis.read(buf)) > -1)
				fos.write(buf, 0, read);
		}
	}
	
	private void createRecordList() throws SQLException {
		ResultSetMetaData rm = rs.getMetaData();
		int colCnt = rm.getColumnCount();
		
		recs = new ArrayList<>(colCnt);
		for (int i = 0; i < colCnt; i++) {
			DbfRecord rec = new DbfRecord();
			
			rec.name = rm.getColumnName(i + 1);
			if (rec.name.length() > DbfRecord.nameLen)
				rec.name = rec.name.substring(0, DbfRecord.nameLen);
			rec.name = rec.name.toUpperCase();
			rec.type = 'N';
			rec.precision = (rm.getPrecision(i + 1) < 256) ? rm.getPrecision(i + 1) : 255;
			rec.scale = (rm.getScale(i + 1) < 3) ? rm.getScale(i + 1) : 2;
			
			switch (rm.getColumnType(i + 1)) {
			case Types.NUMERIC:
				break;
			case Types.SMALLINT:
			case Types.REAL:
				rec.precision = 6;
				break;
			case Types.INTEGER:
			case Types.DOUBLE:
				rec.precision = 11;
				break;
			case Types.BIGINT:
				rec.precision = 20;
				break;
			case Types.CHAR:
			case Types.VARCHAR:
				rec.type = 'C';
				break;
			case Types.DATE:
				rec.type = 'D';
				rec.precision = 8;
				break;
			default:
				throw new SQLException(String.format("Unsupported data type: %d.", rm.getColumnType(i + 1)));
			}
			hdr.turpleLen += rec.precision;
			recs.add(rec);
		}
		hdr.dataPos = (short) (DbfHeader.hdrLen + (DbfRecord.recLen * recs.size()) + 1);
	}

	private void createDataList() throws SQLException {
		byte[] buf;
		int pos;
		
		datas = new ArrayList<>();
		while (rs.next()) {
			pos = 1;
			buf = new byte[hdr.turpleLen + pos];
			for (int i = 0; i < recs.size(); i++) {
				DbfRecord rec = recs.get(i);
				
				switch (rec.type) {
				case 'N':
					if (rec.scale == 0)
						writeNumber(buf, pos, rs.getLong(i + 1), rec.precision);
					else
						writeFloat(buf, pos, rs.getDouble(i + 1), rec.precision, rec.scale);
					break;
				case 'C':
					writeString(buf, pos, rs.getString(i + 1), rec.precision);
					break;
				case 'D':
					writeString(buf, pos, sdf.format(rs.getDate(i + 1)), rec.precision);
					break;
				default:
					throw new SQLException(String.format("Unsupported data type: %s.", rec.type));
				}
				pos += rec.precision;
			}
			hdr.recCnt++;
			datas.add(buf);
		}
	}
	
	private void writeNumber(byte[] buf, int pos, long num, int len) {
		String str = String.format(String.format("%% %dd", len), num);
		
		for (int i = 0; i < len; i++)
			buf[pos++] = (byte) str.charAt(i);
	}
	
	private void writeFloat(byte[] buf, int pos, double num, int prc, int scl) {
		String str = String.format(Locale.US, String.format("%%%d.", prc) + String.format("%df", scl), num);
		
		for (int i = 0; i < prc; i++)
			buf[pos++] = (byte) str.charAt(i);
	}
	
	private void writeString(byte[] buf, int pos, String str, int len) {
		byte[] sb;
		
		try {
			if (str != null)
				sb = str.getBytes("Cp866");
			else
				sb = new byte[] {};
		} catch (UnsupportedEncodingException e) {
			sb = str.getBytes();
		}
		
		for (int i = 0; i < sb.length; i++)
			buf[pos++] = sb[i];
		
		if (sb.length < len)
			for (int i = sb.length; i < len; i++)
				buf[pos++] = 32;
	}
	
	private void writeToStream(ByteArrayOutputStream bos) throws IOException {
			bos.write(DbfHeader.version);
			bos.write(hdr.year);
			bos.write(hdr.month);
			bos.write(hdr.day);
			bos.write(numericToBytes(hdr.recCnt, 10));
			bos.write(numericToBytes(hdr.dataPos, 5));
			bos.write(numericToBytes(hdr.turpleLen, 5));
			bos.write(new byte[20]);
			
			for (DbfRecord rec : recs) {
				bos.write(rec.name.getBytes());
				bos.write(new byte[DbfRecord.nameLen - rec.name.length()]);
				bos.write(rec.type);
				bos.write(new byte[4]);
				bos.write(rec.precision);
				bos.write(rec.scale);
				bos.write(new byte[14]);
			}
			bos.write(13);
			
			for (byte[] data : datas)
				bos.write(data);
			bos.write(26);
	}
	
	private byte[] numericToBytes(long number, int precision) {
		if (precision < 6) {
			byte[] buf = new byte[2];
			buf[1] = (byte) (number >> 0x08);
			buf[0] = (byte) (number);
			return buf;
		} else if (precision < 11) {
			byte[] buf = new byte[4];
			buf[3] = (byte) (number >> 0x18);
			buf[2] = (byte) (number >> 0x10);
			buf[1] = (byte) (number >> 0x08);
			buf[0] = (byte) (number);
			return buf;
		} else {
			byte[] buf = new byte[8];
			buf[7] = (byte) (number >> 0x38);
			buf[6] = (byte) (number >> 0x30);
			buf[5] = (byte) (number >> 0x28);
			buf[4] = (byte) (number >> 0x20);
			buf[3] = (byte) (number >> 0x18);
			buf[2] = (byte) (number >> 0x10);
			buf[1] = (byte) (number >> 0x08);
			buf[0] = (byte) (number);
			return buf;
		}
	}
}
