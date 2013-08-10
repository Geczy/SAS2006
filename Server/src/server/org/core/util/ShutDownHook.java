
package server.org.core.util;

import server.org.engine.character.Client;
import server.org.engine.character.Player;
import server.org.engine.character.PlayerHandler;

public class ShutDownHook extends Thread
{

	@Override
	public void run()
	{
		System.out.println( "Shutdown thread run." );
		for( Player player: PlayerHandler.players ) {
			if( player != null ) {
				Client c = ( Client )player;
				server.org.engine.character.PlayerSave.saveGame( c );
			}
		}
		System.out.println( "Shutting down..." );
	}

}
