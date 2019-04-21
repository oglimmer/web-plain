package de.oglimmer.db.util;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class SingleTableSQLBuilder {

	private String table;
	private String sort;
	private List<String> columns = new ArrayList<>();
	private List<String> where = new ArrayList<>();
	private List<Object> whereParams = new ArrayList<>();
	private Integer limitOffset;
	private Integer limitNumRows;

	public static SingleTableSQLBuilder table(String table) {
		SingleTableSQLBuilder newObj = new SingleTableSQLBuilder();
		newObj.table = table;
		return newObj;
	}

	public SingleTableSQLBuilder columns(String... cols) {
		Collections.addAll(this.columns, cols);
		return this;
	}

	public SingleTableSQLBuilder limit(Integer offset, Integer numRows) {
		limitOffset = offset;
		limitNumRows = numRows;
		return this;
	}

	public SingleTableSQLBuilder where(String sql, String param) {
		where.add(sql);
		whereParams.add(param);
		return this;
	}

	public void setParams(PreparedStatement prst) throws SQLException {
		int i = 1;
		for (Object e : whereParams) {
			prst.setObject(i++, e);
		}
	}

	public SingleTableSQLBuilder sort(String sortColumn, String sortOrder) {
		sort = "order by " + sortColumn + " " + sortOrder;
		return this;
	}

	@Override
	public String toString() {
		String sql = "select " + columns.stream().collect(Collectors.joining(",")) + " from " + table;
		if (where.size() > 0) {
			sql += " where " + where.stream().collect(Collectors.joining(" and "));
		}
		if (sort != null) {
			sql += " " + sort;
		}
		if (limitOffset != null) {
			sql += " limit " + limitOffset + ", " + limitNumRows;
		}
		return sql;
	}

}
