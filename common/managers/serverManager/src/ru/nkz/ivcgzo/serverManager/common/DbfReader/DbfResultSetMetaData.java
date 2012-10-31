package ru.nkz.ivcgzo.serverManager.common.DbfReader;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DbfResultSetMetaData implements ResultSetMetaData {
	private int colCount;
	private DbfRecord[] records;
	
	private class DbfRecord {
		private static final int terminator = 13;
		private static final int recLen = 32;
		private static final int nameLen = 11;
		private String name;
		private int type;
		private int precision;
		private int scale;
	}
	
	protected DbfResultSetMetaData(FileInputStream str) throws IOException {
		calcColumnCount(str);
		readMetadata(str);
	}
	
	private void calcColumnCount(FileInputStream str) throws IOException {
		long pos = str.getChannel().position();
		
		while (str.available() > 0) {
			if (str.read() == DbfRecord.terminator)
				break;
			str.skip(DbfRecord.recLen - 1);
			colCount++;
		}
		
		str.getChannel().position(pos);
	}
	
	private void readMetadata(FileInputStream str) throws IOException {
		records = new DbfRecord[getColumnCount() + 1];
		
		for (int i = 1; i < records.length; i++) {
			DbfRecord rec = new DbfRecord();
			int type;
			
			rec.name = readNullTerminatedString(str, DbfRecord.nameLen);
			
			type = str.read();
			str.skip(4);
			rec.precision = str.read();
			rec.scale = str.read();
			str.skip(14);
			
			switch (type) {
			case 'N':
				if (rec.scale == 0) {
					if (rec.precision < 7)
						rec.type = java.sql.Types.SMALLINT;
					else if (rec.precision < 12)
						rec.type = java.sql.Types.INTEGER;
					else if (rec.precision < 21)
						rec.type = java.sql.Types.BIGINT;
					else
						rec.type = java.sql.Types.NUMERIC;
				} else {
					if (rec.precision < 7)
						rec.type = java.sql.Types.REAL;
					else if (rec.precision < 12)
						rec.type = java.sql.Types.DOUBLE;
					else
						rec.type = java.sql.Types.NUMERIC;
				}
				break;
			case 'C':
				rec.type = java.sql.Types.CHAR;
				break;
			case 'D':
				rec.type = java.sql.Types.DATE;
				break;
			default:
				throw new IOException(String.format("Unsupported data type at %d.", str.getChannel().position()));
			}
			
			records[i] = rec;
		}
	}
	
	private String readNullTerminatedString(FileInputStream str, int length) throws IOException {
		byte[] buf = new byte[length];
		int zeroPos;
		
		str.read(buf);
		for (zeroPos = 0; zeroPos < buf.length; zeroPos++) {
			if (buf[zeroPos] == 0)
				break;
		}
		
		return new String(buf, 0, zeroPos, "Cp866");
	}
	
	@Override
	public int getColumnCount() {
		return colCount;
	}
	
	@Override
	public String getColumnName(int column) throws SQLException {
		return records[column].name;
	}

	@Override
	public int getPrecision(int column) throws SQLException {
		return records[column].precision;
	}
	
	@Override
	public int getScale(int column) throws SQLException {
		return records[column].scale;
	}
	
	@Override
	public int getColumnType(int column) throws SQLException {
		return records[column].type;
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
	public boolean isAutoIncrement(int column) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public boolean isCaseSensitive(int column) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public boolean isSearchable(int column) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public boolean isCurrency(int column) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public int isNullable(int column) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public boolean isSigned(int column) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public int getColumnDisplaySize(int column) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public String getColumnLabel(int column) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public String getSchemaName(int column) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public String getTableName(int column) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public String getCatalogName(int column) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public String getColumnTypeName(int column) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public boolean isReadOnly(int column) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public boolean isWritable(int column) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public boolean isDefinitelyWritable(int column) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

	@Override
	public String getColumnClassName(int column) throws SQLException {
		throw new RuntimeException("Method is not yet implemented.");
	}

}
