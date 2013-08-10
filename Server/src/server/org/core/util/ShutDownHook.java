package server.org.core.util;

import server.org.Server;
import server.org.engine.character.Client;
import server.org.engine.character.PlayerSave;

public class ShutDownHook extends Thread {

	@Override
	public void run() {
		System.out.println("Shutdown thread run.");
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c = (Client)Server.playerHandler.players[j];
				server.org.engine.character.PlayerSave.saveGame(c);			
			}		
		}
		System.out.println("Shutting down...");
	}

}