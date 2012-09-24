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

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.esper.caroline.Caroline;

import org.pircbotx.exception.IrcException;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.DisconnectEvent;

/**
 * The listener that handles connection events.
 * 
 * @author Kramer Campbell
 */
@SuppressWarnings("rawtypes")
public class ConnectListener extends ListenerAdapter {
	
	private static final Logger logger = Logger.getLogger(ConnectListener.class.getName());

	@Override
	public void onDisconnect(DisconnectEvent event) {
		logger.info("Disconnected. Attempting to reconnect...");
		
		try {
			Caroline.connect();
		} catch (IOException | IrcException e) {
			logger.log(Level.SEVERE, "Unable to reconnect.", e);
		}
	}
	
}
