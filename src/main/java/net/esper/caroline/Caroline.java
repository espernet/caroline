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

package net.esper.caroline;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.esper.caroline.command.ActCommand;
import net.esper.caroline.command.AddCommand;
import net.esper.caroline.command.CommandManager;
import net.esper.caroline.command.DatabaseCommands;
import net.esper.caroline.command.DeleteCommand;
import net.esper.caroline.command.EditCommand;
import net.esper.caroline.command.HelpCommand;
import net.esper.caroline.command.ListCommand;
import net.esper.caroline.command.SayCommand;
import net.esper.caroline.db.DatabaseEngine;
import net.esper.caroline.listener.CommandListener;
import net.esper.caroline.listener.ConnectListener;
import net.esper.caroline.util.Configuration;

import org.pircbotx.PircBotX;
import org.pircbotx.exception.IrcException;
import org.pircbotx.exception.NickAlreadyInUseException;

/**
 * The main class for the Caroline bot.
 * 
 * @author Kramer Campbell
 */
public class Caroline {

	/**
	 * The logger.
	 */
	private static final Logger logger = Logger.getLogger(Caroline.class
			.getName());

	/**
	 * The bot instance.
	 */
	private static PircBotX bot;

	/**
	 * The configuration instance.
	 */
	private static Configuration configuration;

	public static void main(String[] args) {
		logger.info("Starting Caroline...");

		// Load the configuration.
		configuration = new Configuration();
		configuration.load();

		// Set up the database.
		DatabaseEngine.getInstance().connect();
		DatabaseEngine.getInstance().prepare();

		// Create the bot.
		bot = new PircBotX();

		// Add listeners to the bot.
		bot.getListenerManager().addListener(new CommandListener());
		bot.getListenerManager().addListener(new ConnectListener());

		// Register commands for the bot.
		CommandManager.getInstance().addCommand(ActCommand.class);
		CommandManager.getInstance().addCommand(AddCommand.class);
		CommandManager.getInstance().addCommand(DatabaseCommands.class);
		CommandManager.getInstance().addCommand(DeleteCommand.class);
		CommandManager.getInstance().addCommand(EditCommand.class);
		CommandManager.getInstance().addCommand(HelpCommand.class);
		CommandManager.getInstance().addCommand(ListCommand.class);
		CommandManager.getInstance().addCommand(SayCommand.class);

		// Set up the bot.
		bot.setName((String) configuration.get("nick"));
		bot.setLogin((String) configuration.get("login"));
		bot.setFinger((String) configuration.get("finger", ""));
		bot.setVersion((String) configuration.get("version", ""));
		bot.identify((String) Caroline.getConfiguration().get("identify", ""));
		bot.setMessageDelay(0);

		// Connect the bot.
		try {
			connect();
		} catch (IOException | IrcException e) {
			logger.log(Level.SEVERE, "Unable to connect.", e);
		}
	}

	/**
	 * Connects the bot.
	 * 
	 * @throws NickAlreadyInUseException
	 *             If the nick is already in use.
	 * @throws IOException
	 *             If there's a connection issue.
	 * @throws IrcException
	 *             If there's an issue joining the IRC server.
	 */
	public static void connect() throws NickAlreadyInUseException, IOException,
			IrcException {
		// Connect the bot.
		bot.connect((String) configuration.get("server"),
				(Integer) configuration.get("port"));

		bot.joinChannel((String) configuration.get("help_channel",
				"#carolinetest"));

		if ((String) configuration.get("test_channel") != null) {
			bot.joinChannel((String) configuration.get("test_channel"));
		}
	}

	/**
	 * Gets the bot instance.
	 * 
	 * @return The bot instance.
	 */
	public static PircBotX getBot() {
		return bot;
	}

	/**
	 * Gets the configuration instance.
	 * 
	 * @return The configuration instance.
	 */
	public static Configuration getConfiguration() {
		return configuration;
	}

}
