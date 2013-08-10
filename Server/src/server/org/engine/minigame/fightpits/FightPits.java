
package server.org.engine.minigame.fightpits;

import server.org.core.util.Misc;
import server.org.engine.character.Client;
import server.org.engine.character.Player;
import server.org.engine.character.PlayerHandler;

/**
 * FightPits.java
 * 
 * @author Izumi
 */

public class FightPits
{

	public int[] playerInPits = new int[200];

	private final int GAME_TIMER = 140;
	private final int GAME_START_TIMER = 40;

	private int gameTime = - 1;
	private int gameStartTimer = 30;
	private int properTimer = 0;
	public int playersRemaining = 0;

	public String pitsChampion = "Nobody";


	public void process()
	{
		if( properTimer > 0 ) {
			properTimer -- ;
			return;
		} else {
			properTimer = 4;
		}
		if( gameStartTimer > 0 ) {
			gameStartTimer -- ;
			updateWaitRoom();
		}
		if( gameStartTimer == 0 ) {
			startGame();
		}
		if( gameTime > 0 ) {
			gameTime -- ;
			if( playersRemaining == 1 ) {
				endPitsGame( getLastPlayerName() );
			}
		} else if( gameTime == 0 ) {
			endPitsGame( "Nobody" );
		}
	}


	public String getLastPlayerName()
	{
		for( int playerInPit: playerInPits ) {
			if( playerInPit > 0 ) {
				return PlayerHandler.players[playerInPit].playerName;
			}
		}
		return "Nobody";
	}


	public void updateWaitRoom()
	{
		for( Player player: PlayerHandler.players ) {
			if( player != null ) {
				Client c = ( Client )player;
				if( c.getPA().inPitsWait() ) {
					c.getPA().sendFrame126(
							"Next Game Begins In : " + ( gameStartTimer * 3 + gameTime * 3 ) + " seconds.",
							6570 );
					c.getPA().sendFrame126( "Champion: " + pitsChampion, 6572 );
					c.getPA().sendFrame126( "", 6664 );
					c.getPA().walkableInterface( 6673 );
				}
			}
		}
	}


	public void startGame()
	{
		if( getWaitAmount() < 2 ) {
			gameStartTimer = GAME_START_TIMER / 2;
			// System.out.println("Unable to start fight pits game due to lack of players.");
			return;
		}
		for( int j = 0; j < PlayerHandler.players.length; j ++ ) {
			if( PlayerHandler.players[j] != null ) {
				Client c = ( Client )PlayerHandler.players[j];
				if( c.getPA().inPitsWait() ) {
					addToPitsGame( j );
				}
			}
		}
		System.out.println( "Fight Pits game started." );
		gameStartTimer = GAME_START_TIMER + GAME_TIMER;
		gameTime = GAME_TIMER;
	}


	public int getWaitAmount()
	{
		int count = 0;
		for( Player player: PlayerHandler.players ) {
			if( player != null ) {
				Client c = ( Client )player;
				if( c.getPA().inPitsWait() ) {
					count ++ ;
				}
			}
		}
		return count;
	}


	public void removePlayerFromPits( int playerId )
	{
		for( int j = 0; j < playerInPits.length; j ++ ) {
			if( playerInPits[j] == playerId ) {
				Client c = ( Client )PlayerHandler.players[playerInPits[j]];
				c.getPA().movePlayer( 2399, 5173, 0 );
				playerInPits[j] = - 1;
				playersRemaining -- ;
				c.inPits = false;
				break;
			}
		}
	}


	public void endPitsGame( String champion )
	{
		if( playersRemaining == 1 ) {
		}
		for( int playerInPit: playerInPits ) {
			if( playerInPit < 0 ) {
				continue;
			}
			if( PlayerHandler.players[playerInPit] == null ) {
				continue;
			}
			Client c = ( Client )PlayerHandler.players[playerInPit];
			c.getPA().movePlayer( 2399, 5173, 0 );
			c.inPits = false;
		}
		playerInPits = new int[200];
		pitsChampion = champion;
		playersRemaining = 0;
		pitsSlot = 0;
		gameStartTimer = GAME_START_TIMER;
		gameTime = - 1;
		System.out.println( "Fight Pits game ended." );
	}

	private int pitsSlot = 0;


	public void addToPitsGame( int playerId )
	{
		if( PlayerHandler.players[playerId] == null ) {
			return;
		}
		playersRemaining ++ ;
		Client c = ( Client )PlayerHandler.players[playerId];
		c.getPA().walkableInterface( - 1 );
		playerInPits[pitsSlot ++ ] = playerId;
		c.getPA().movePlayer( 2392 + Misc.random( 12 ), 5139 + Misc.random( 25 ), 0 );
		c.inPits = true;
	}
}
