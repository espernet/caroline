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

import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.types.GenericMessageEvent;

/**
 * The act command that makes the bot say given text to a channel or user.
 * 
 * @author Kramer Campbell
 */
@SuppressWarnings("rawtypes")
public class ActCommand extends Command {

	public ActCommand(Event event, String[] args) {
		super(event, args);
	}

	@Override
	protected void setup() {
		// Configure command properties.
		triggers.add("ACT");
		privateMessageCommand = true;
	}

	@Override
	public void run() {
		if (args.length < 3) {
			// Not enough arguments.
			event.getBot().sendNotice(((GenericMessageEvent) event).getUser(),
					"Usage: ACT <#channel/nick> <msg>");
			return;
		}

		// Combine the every argument after the channel into one message string.
		String message = "";
		for (int i = 2; i < args.length; i++) {
			message += args[i] + " ";
		}
		message = message.trim();

		event.getBot().sendAction(args[1], message);
	}

}
