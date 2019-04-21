package de.oglimmer.db.util;

import java.sql.DriverManager;
import java.sql.SQLException;

import de.oglimmer.config.Properties;

public enum ConnectionProvider {
	INSTANCE;

	static {
		try {
			Class.forName(Properties.getInstance().getDBDriver());
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		}
	}

	public java.sql.Connection getConnection() throws SQLException {
		Properties prop = Properties.getInstance();
		return DriverManager.getConnection(prop.getDBUrl(), prop.getDBUser(), prop.getDBPassword());
	}

}
