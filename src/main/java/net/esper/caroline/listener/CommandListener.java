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

package net.esper.caroline.listener;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.esper.caroline.Caroline;
import net.esper.caroline.command.Command;
import net.esper.caroline.command.CommandManager;

import org.pircbotx.Channel;
import org.pircbotx.User;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PrivateMessageEvent;
import org.pircbotx.hooks.types.GenericMessageEvent;

/**
 * The listener that handles triggering of bot commands.
 * 
 * @author Kramer Campbell
 */
@SuppressWarnings("rawtypes")
public class CommandListener extends ListenerAdapter {

	/**
	 * The logger.
	 */
	private static final Logger logger = Logger.getLogger(CommandListener.class
			.getName());

	/**
	 * The thread pool for executing commands.
	 */
	private ExecutorService threadPool;

	public CommandListener() {
		threadPool = Executors.newSingleThreadExecutor();
	}

	@Override
	public void onMessage(MessageEvent event) {
		handleCommand(event);
	}

	@Override
	public void onPrivateMessage(PrivateMessageEvent event) {
		handleCommand(event);
	}

	/**
	 * Handles command triggers.
	 * 
	 * @param event
	 *            A message event.
	 */
	private void handleCommand(GenericMessageEvent event) {
		// Only listen to oped and voiced in the help channel.
		if (!isAllowed(event.getUser())) {
			return;
		}

		// Split the message for arguments.
		String[] args = event.getMessage().split(" ");

		// Iterate over all commands.
		for (Class<?> commandClass : CommandManager.getInstance().getCommands()) {
			// Initialize the command.
			Command command;

			try {
				command = (Command) commandClass.getDeclaredConstructor(
						Event.class, String[].class).newInstance(event, args);
			} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException | InvocationTargetException
					| NoSuchMethodException | SecurityException e) {
				logger.log(Level.WARNING, "Unable to initialize command.", e);
				event.getBot().sendNotice(event.getUser(),
						"WARNING: Unable to initialize command.");
				continue;
			}

			// Execute the command only if it matches certain conditions.
			if (event instanceof MessageEvent) {
				if (command.getTriggers().contains(args[0].toUpperCase())
						&& !command.isPrivateMessageCommand()) {
					threadPool.submit(command);
				}
			} else if (event instanceof PrivateMessageEvent) {
				if (command.getTriggers().contains(args[0].toUpperCase())
						&& command.isPrivateMessageCommand()) {
					threadPool.submit(command);
				}
			}
		}
	}

	/**
	 * Checks if the user is allowed to perform commands. Allowed users are
	 * those that are either opped or voiced in the help channel.
	 * 
	 * @param user
	 *            The user to check.
	 * @return true if they are allowed, false otherwise.
	 */
	private boolean isAllowed(User user) {
		Channel helpChannel = Caroline.getBot().getChannel(
				(String) Caroline.getConfiguration().get("help_channel"));
		return user.getChannelsOpIn().contains(helpChannel)
				|| user.getChannelsVoiceIn().contains(helpChannel);
	}
}
