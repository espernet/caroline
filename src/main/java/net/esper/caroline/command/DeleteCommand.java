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
 * Deletes a command from the database.
 * 
 * @author Kramer Campbell
 */
@SuppressWarnings("rawtypes")
public class DeleteCommand extends Command {

	/**
	 * The logger.
	 */
	private static final Logger logger = Logger.getLogger(DeleteCommand.class
			.getName());

	public DeleteCommand(Event event, String[] args) {
		super(event, args);
	}

	@Override
	protected void setup() {
		// Configure command properties.
		triggers.add("DEL");
		triggers.add("DELETE");
		triggers.add("DELETERESPONSE");
		privateMessageCommand = true;
	}

	@Override
	public void run() {
		if (args.length < 2) {
			// Not enough arguments.
			event.getBot().sendNotice(((GenericMessageEvent) event).getUser(),
					"Usage: DEL <id>");
			return;
		}

		// Delete the command.
		try {
			PreparedStatement stmt = DatabaseEngine.getInstance()
					.getConnection()
					.prepareStatement("DELETE FROM commands WHERE id = ?");

			stmt.setString(1, args[1]);
			stmt.executeUpdate();

			event.getBot().sendNotice(((GenericMessageEvent) event).getUser(),
					"Command #" + args[1] + " has been deleted successfully.");
			
			stmt.close();
		} catch (SQLException e) {
			event.getBot().sendNotice(((GenericMessageEvent) event).getUser(),
					"Unable to delete the command from the database.");
			logger.log(Level.WARNING,
					"Unable to delete the command from the database.", e);
		}
	}

}
