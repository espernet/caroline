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

import java.util.ArrayList;
import java.util.List;

import org.pircbotx.hooks.Event;

/**
 * An abstract class that holds the basics of a command.
 * 
 * @author Kramer Campbell
 */
@SuppressWarnings("rawtypes")
public abstract class Command implements Runnable {

	/**
	 * Words that triggers the command.
	 */
	protected List<String> triggers;

	/**
	 * Defines it is performed over a private message to the bot.
	 */
	protected boolean privateMessageCommand;

	/**
	 * The event instance for the commmand.
	 */
	protected Event event;

	/**
	 * Arguments for the command.
	 */
	protected String[] args;

	/**
	 * Creates an instance of the command.
	 * 
	 * @param event
	 *            The event instance.
	 * @param args
	 *            The command arguments.
	 */
	public Command(Event event, String[] args) {
		this.event = event;
		this.triggers = new ArrayList<String>();
		this.args = args;

		setup();
	}

	/**
	 * Setups the command with its properties.
	 */
	protected abstract void setup();

	/**
	 * Gets the triggers for the command.
	 * 
	 * @return The triggers.
	 */
	public List<String> getTriggers() {
		return triggers;
	}

	/**
	 * Gets whether this command is to be performed over private messsage.
	 * 
	 * @return The private message flag.
	 */
	public boolean isPrivateMessageCommand() {
		return privateMessageCommand;
	}

}
