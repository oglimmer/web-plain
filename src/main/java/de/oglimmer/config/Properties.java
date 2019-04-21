package de.oglimmer.config;

import de.oglimmer.utils.AbstractProperties;

public class Properties extends AbstractProperties {

	private static final Properties SINGLETON = new Properties();

	public static final Properties getInstance() {
		return SINGLETON;
	}

	private Properties() {
		super("plain");
	}

	public String getDBUrl() {
		return getJson().getString("db.url");
	}

	public String getDBDriver() {
		return getJson().getString("db.driver");
	}

	public String getDBUser() {
		return getJson().getString("db.user");
	}

	public String getDBPassword() {
		return getJson().getString("db.password");
	}

}
