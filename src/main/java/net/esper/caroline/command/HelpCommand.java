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

import org.pircbotx.User;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.events.PrivateMessageEvent;

/**
 * The help command that explains what the bot can do.
 * 
 * @author Kramer Campbell
 */
@SuppressWarnings("rawtypes")
public class HelpCommand extends Command {

	public HelpCommand(Event event, String args[]) {
		super(event, args);
	}

	@Override
	protected void setup() {
		// Configure command properties.
		triggers.add("HELP");
		triggers.add("SHOWCOMMANDS");
		privateMessageCommand = true;
	}

	@Override
	public void run() {
		User sender = ((PrivateMessageEvent) event).getUser();
		
		event.getBot().sendNotice(sender, "HELP          This command.");
		event.getBot().sendNotice(sender, "SHOWCOMMANDS  This command.");
		event.getBot().sendNotice(sender, "LIST          Lists all commands stored in the database.");
		event.getBot().sendNotice(sender, "ADD           Adds a command to the database.");
		event.getBot().sendNotice(sender, "DEL           Removes a command from the database.");
		event.getBot().sendNotice(sender, "EDIT          Changes a response for a certain command in the database.");
		event.getBot().sendNotice(sender, "SAY           Bot says something to a channel or user.");
		event.getBot().sendNotice(sender, "ACT           Bot does an action in a channel or towards an user.");
	}

}
