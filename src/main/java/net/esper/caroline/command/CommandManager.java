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

/**
 * A manager for every command.
 * 
 * @author Kramer Campbell
 */
public class CommandManager {

	/**
	 * The singleton instance.
	 */
	private static CommandManager instance;

	/**
	 * List of commands.
	 */
	private List<Class<?>> commands;

	private CommandManager() {
		commands = new ArrayList<Class<?>>();
	}

	/**
	 * Adds a command to the list.
	 * 
	 * @param command
	 *            The command's class object.
	 */
	public void addCommand(Class<?> command) {
		commands.add(command);
	}

	/**
	 * Gets the list of commands.
	 * 
	 * @return The list of commands.
	 */
	public List<Class<?>> getCommands() {
		return commands;
	}

	/**
	 * Gets the singleton instance of {@code CommandManager}
	 * 
	 * @return The singleton instance.
	 */
	public static CommandManager getInstance() {
		if (instance == null) {
			instance = new CommandManager();
		}

		return instance;
	}

}
