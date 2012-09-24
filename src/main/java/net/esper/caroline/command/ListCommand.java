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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.esper.caroline.db.DatabaseEngine;

import org.pircbotx.User;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.PrivateMessageEvent;

/**
 * Deletes a command from the database.
 * 
 * @author Kramer Campbell
 */
@SuppressWarnings("rawtypes")
public class ListCommand extends Command {

	/**
	 * The logger.
	 */
	private static final Logger logger = Logger.getLogger(ListCommand.class
			.getName());

	public ListCommand(Event event, String[] args) {
		super(event, args);
	}

	@Override
	protected void setup() {
		// Configure command properties.
		triggers.add("LIST");
		triggers.add("LISTRESPONSES");
		privateMessageCommand = true;
	}

	@Override
	public void run() {
		User sender = ((PrivateMessageEvent) event).getUser();

		// List the commands.
		try {
			Statement stmt = DatabaseEngine.getInstance().getConnection()
					.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM commands");
			
			event.getBot().sendNotice(sender, "ID    Trigger              Response");
			event.getBot().sendNotice(sender, "----- -------------------- --------");

			int space; // Space integer needed to align for monospaced text.
			
			while (rs.next()) {
				String id = Integer.toString(rs.getInt("id"));
				String trigger = " " + rs.getString("trigger");
				String response = rs.getString("response");
				
				space = 5 - id.length();
				
				// Append space to the end of ID.
				for (int i = 0; i < space; i++) {
					id += " ";
				}
				
				space = 22 - trigger.length();
				
				// Append space to the end of trigger.
				for (int i = 0; i < space; i++) {
					trigger += " ";
				}
				
				event.getBot().sendNotice(sender, id + trigger + response);
			}
			
			rs.close();
			stmt.close();
		} catch (SQLException e) {
			event.getBot().sendNotice(sender,
					"Unable to list the commands from the database.");
			logger.log(Level.WARNING,
					"Unable to list the commands from the database.", e);
		}
	}

}
