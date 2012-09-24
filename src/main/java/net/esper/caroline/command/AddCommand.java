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
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.esper.caroline.db.DatabaseEngine;

import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.types.GenericMessageEvent;

/**
 * Adds a command to the database.
 * 
 * @author Kramer Campbell
 */
@SuppressWarnings("rawtypes")
public class AddCommand extends Command {

	/**
	 * The logger.
	 */
	private static final Logger logger = Logger.getLogger(AddCommand.class
			.getName());

	public AddCommand(Event event, String[] args) {
		super(event, args);
	}

	@Override
	protected void setup() {
		// Configure command properties.
		triggers.add("ADD");
		triggers.add("ADDRESPONSE");
		privateMessageCommand = true;
	}

	@Override
	public void run() {
		if (args.length < 3) {
			// Not enough arguments.
			event.getBot().sendNotice(((GenericMessageEvent) event).getUser(),
					"Usage: ADD <trigger> <msg>");
			return;
		}

		// Combine the every argument after the channel into one message string.
		String message = "";
		for (int i = 2; i < args.length; i++) {
			message += args[i] + " ";
		}
		message = message.trim();

		// Add the command.
		try {
			PreparedStatement stmt = DatabaseEngine
					.getInstance()
					.getConnection()
					.prepareStatement(
							"INSERT INTO commands (trigger, response) VALUES (?, ?)");

			stmt.setString(1, args[1]);
			stmt.setString(2, message);
			stmt.executeUpdate();

			event.getBot().sendNotice(((GenericMessageEvent) event).getUser(),
					args[1] + " has been added successfully.");
			
			stmt.close();
		} catch (SQLException e) {
			event.getBot().sendNotice(((GenericMessageEvent) event).getUser(),
					"Unable to add the command to the database.");
			logger.log(Level.WARNING, "Unable to add the command to the database.",
					e);
		}
	}

}
