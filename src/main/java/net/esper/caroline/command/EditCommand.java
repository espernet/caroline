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
 * Edits a command that is in the database.
 * 
 * @author Kramer Campbell
 */
@SuppressWarnings("rawtypes")
public class EditCommand extends Command {

	/**
	 * The logger.
	 */
	private static final Logger logger = Logger.getLogger(EditCommand.class
			.getName());

	public EditCommand(Event event, String[] args) {
		super(event, args);
	}

	@Override
	protected void setup() {
		// Configure command properties.
		triggers.add("EDIT");
		triggers.add("EDITRESPONSE");
		privateMessageCommand = true;
	}

	@Override
	public void run() {
		if (args.length < 3) {
			// Not enough arguments.
			event.getBot().sendNotice(((GenericMessageEvent) event).getUser(),
					"Usage: EDIT <id> <msg>");
			return;
		}

		// Combine the every argument after the channel into one message string.
		String message = "";
		for (int i = 2; i < args.length; i++) {
			message += args[i] + " ";
		}
		message = message.trim();

		// Edit the command.
		try {
			PreparedStatement stmt = DatabaseEngine
					.getInstance()
					.getConnection()
					.prepareStatement(
							"UPDATE commands SET response = ? WHERE id = ?");

			stmt.setString(1, message);
			stmt.setString(2, args[1]);
			stmt.executeUpdate();

			event.getBot().sendNotice(((GenericMessageEvent) event).getUser(),
					"Command #" + args[1] + " has been edited successfully.");
		} catch (SQLException e) {
			event.getBot().sendNotice(((GenericMessageEvent) event).getUser(),
					"Unable to edit the command in the database.");
			logger.log(Level.WARNING,
					"Unable to edit the command in the database.", e);
		}
	}

}
