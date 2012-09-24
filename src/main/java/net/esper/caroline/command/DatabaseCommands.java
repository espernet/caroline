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

package net.esper.caroline.command;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.esper.caroline.db.DatabaseEngine;

import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.MessageEvent;

/**
 * Handles all commands defined in the database.
 * 
 * @author Kramer Campbell
 */
@SuppressWarnings("rawtypes")
public class DatabaseCommands extends Command {

	/**
	 * The logger.
	 */
	private static final Logger logger = Logger
			.getLogger(DatabaseCommands.class.getName());

	public DatabaseCommands(Event event, String[] args) {
		super(event, args);
	}

	@Override
	protected void setup() {
		// Configure command properties.
		try {
			// Load triggers from the database.
			Statement stmt = DatabaseEngine.getInstance().getConnection()
					.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT trigger FROM commands");

			while (rs.next()) {
				triggers.add(rs.getString("trigger").toUpperCase());
			}
			
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			logger.log(Level.WARNING,
					"Unable to query database for command triggers.");
		}

		privateMessageCommand = false;
	}

	@Override
	public void run() {
		try {
			PreparedStatement stmt = DatabaseEngine
					.getInstance()
					.getConnection()
					.prepareStatement(
							"SELECT response FROM commands WHERE UPPER(trigger) = UPPER(?)");

			stmt.setString(1, args[0]);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				event.getBot().sendMessage(((MessageEvent) event).getChannel(),
						rs.getString("response"));
			}
		} catch (SQLException e) {
			logger.log(Level.WARNING,
					"Unable to query database for response to trigger "
							+ args[0] + ".");
		}
	}

}
