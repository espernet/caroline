/*
 * Caroline - IRC Help Bot
 * Copyright (c) 2012 Kramer Campbell and the EsperNet IRC Network.
 *   
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *    
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package net.esper.caroline.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.esper.caroline.Caroline;

/**
 * A database engine that utilizes SQLite.
 * 
 * @author Kramer Campbell
 */
public class DatabaseEngine {

	/**
	 * The logger.
	 */
	private static final Logger logger = Logger.getLogger(DatabaseEngine.class
			.getName());

	/**
	 * The singleton instance.
	 */
	private static DatabaseEngine instance;

	/**
	 * The database connection.
	 */
	private Connection connection;

	public DatabaseEngine() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			logger.log(Level.SEVERE,
					"Unable to initialize load database engine.", e);
		}
	}

	/**
	 * Creates a connection to the database.
	 * 
	 * @throws SQLException
	 *             If unable to connect to the database.
	 */
	public synchronized void connect() {
		try {
			connection = DriverManager.getConnection((String) Caroline
					.getConfiguration().get("db_url"));
		} catch (SQLException e) {
			logger.log(Level.SEVERE,
					"Unable to create a connection to the database.", e);
		}
	}

	/**
	 * Prepares the database with default tables if they do not already exist.
	 */
	public synchronized void prepare() {
		try {
			Statement stmt = connection.createStatement();
			stmt.executeUpdate("CREATE TABLE IF NOT EXISTS commands (id INTEGER PRIMARY KEY, trigger TEXT, response TEXT)");

		} catch (SQLException e) {
			logger.log(Level.SEVERE, "Unable to prepare the database.", e);
		}
	}

	/**
	 * Gets the connection to the database.
	 * 
	 * @return The connection.
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Gets the singleton instance of {@code DatabaseEngine}
	 * 
	 * @return The singleton instance.
	 */
	public static DatabaseEngine getInstance() {
		if (instance == null) {
			instance = new DatabaseEngine();
		}

		return instance;
	}

}
