
package server.org;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.text.DecimalFormat;

import org.apache.mina.common.IoAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptor;
import org.apache.mina.transport.socket.nio.SocketAcceptorConfig;

import server.org.core.connect.ConnectionHandler;
import server.org.core.connect.ConnectionThrottleFilter;
import server.org.core.event.CycleEventHandler;
import server.org.core.event.EventManager;
import server.org.core.log.Logger;
import server.org.core.util.SimpleTimer;
import server.org.engine.character.Client;
import server.org.engine.character.Player;
import server.org.engine.character.PlayerHandler;
import server.org.engine.character.PlayerSave;
import server.org.engine.character.content.Doors;
import server.org.engine.character.content.DoubleDoors;
import server.org.engine.character.content.HighscoresConfig;
import server.org.engine.character.content.lottery;
import server.org.engine.minigame.castlewars.CastleWars;
import server.org.engine.minigame.fightcaves.FightCaves;
import server.org.engine.minigame.fightpits.FightPits;
import server.org.engine.minigame.pestcontrol.PestControl;
import server.org.engine.mob.NPCDrops;
import server.org.engine.mob.NPCHandler;
import server.org.world.Connection;
import server.org.world.WalkingCheck;
import server.org.world.clip.ObjectDef;
import server.org.world.clip.Region;
import server.org.world.handler.ClanChatHandler;
import server.org.world.handler.ItemHandler;
import server.org.world.handler.ObjectHandler;
import server.org.world.handler.ShopHandler;
import server.org.world.manager.ObjectManager;
import server.org.world.manager.PlayerManager;
import server.org.world.manager.StillGraphicsManager;

/**
 * Server.java
 * 
 * @author Sanity
 * @author Graham
 * @author Blake
 * @author Ryan Lmctruck30
 * @author Izumi
 */

public class Server
{

	public static PlayerManager playerManager = null;
	private static StillGraphicsManager stillGraphicsManager = null;
	public static NPCDrops npcDrops = new NPCDrops();
	public static boolean sleeping;
	public static final int cycleRate;
	public static boolean UpdateServer = false;
	public static long lastMassSave = System.currentTimeMillis();
	private static IoAcceptor acceptor;
	private static ConnectionHandler connectionHandler;
	private static ConnectionThrottleFilter throttleFilter;
	private static SimpleTimer engineTimer, debugTimer;
	private static long cycleTime, cycles, totalCycleTime, sleepTime;
	private static DecimalFormat debugPercentFormat;
	public static boolean shutdownServer = false;
	public static boolean shutdownClientHandler;
	public static int serverlistenerPort;
	public static ItemHandler itemHandler = new ItemHandler();
	public static PlayerHandler playerHandler = new PlayerHandler();
	public static NPCHandler npcHandler = new NPCHandler();
	public static ShopHandler shopHandler = new ShopHandler();
	public static ObjectHandler objectHandler = new ObjectHandler();
	public static ObjectManager objectManager = new ObjectManager();
	public static CastleWars castleWars = new CastleWars();
	public static FightPits fightPits = new FightPits();
	public static PestControl pestControl = new PestControl();
	public static ClanChatHandler clanChat = new ClanChatHandler();
	public static FightCaves fightCaves = new FightCaves();
	public static lottery lottery = new lottery();
	static {
		if( ! Config.SERVER_DEBUG ) {
			serverlistenerPort = 43594;
		} else {
			serverlistenerPort = 43594;
		}
		cycleRate = 600;
		shutdownServer = false;
		engineTimer = new SimpleTimer();
		debugTimer = new SimpleTimer();
		sleepTime = 0;
		debugPercentFormat = new DecimalFormat( "0.0#%" );
	}


	public static void main( java.lang.String args[] ) throws NullPointerException, IOException
	{
		/**
		 * Starting Up Server
		 */
		ObjectDef.loadConfig();
		Region.load();
		server.org.engine.character.content.lottery.Process();
		WalkingCheck.load();
		System.setOut( new Logger( System.out ) );
		System.setErr( new Logger( System.err ) );
		/**
		 * Accepting Connections
		 */
		acceptor = new SocketAcceptor();
		connectionHandler = new ConnectionHandler();

		playerManager = PlayerManager.getSingleton();
		playerManager.setupRegionPlayers();
		stillGraphicsManager = new StillGraphicsManager();
		SocketAcceptorConfig sac = new SocketAcceptorConfig();
		sac.getSessionConfig().setTcpNoDelay( false );
		sac.setReuseAddress( true );
		sac.setBacklog( 100 );

		throttleFilter = new ConnectionThrottleFilter( Config.CONNECTION_DELAY );
		sac.getFilterChain().addFirst( "throttleFilter", throttleFilter );
		acceptor.bind( new InetSocketAddress( serverlistenerPort ), connectionHandler, sac );

		/**
		 * Initialise Handlers
		 */
		EventManager.initialize();
		Doors.getSingleton().load();
		DoubleDoors.getSingleton().load();
		Connection.initialize();
		HighscoresConfig.loadHighscores();

		/**
		 * Server Successfully Loaded
		 */
		System.out.println( "SAS Remake has been launched on localhost:" + serverlistenerPort + "..." );
		/**
		 * Main Server Tick
		 */
		try {
			while( ! Server.shutdownServer ) {
				if( sleepTime >= 0 ) {
					Thread.sleep( sleepTime );
				} else {
					Thread.sleep( 600 );
				}
				engineTimer.reset();
				itemHandler.process();
				playerHandler.process();
				npcHandler.process();
				shopHandler.process();
				CycleEventHandler.getSingleton().process();
				objectManager.process();
				fightPits.process();
				pestControl.process();
				cycleTime = engineTimer.elapsed();
				sleepTime = cycleRate - cycleTime;
				totalCycleTime += cycleTime;
				cycles ++ ;
				debug();
				if( System.currentTimeMillis() - lastMassSave > 3 ) {
					for( Player p: PlayerHandler.players ) {
						if( p == null ) {
							continue;
						}
						PlayerSave.saveGame( ( Client )p );
						lastMassSave = System.currentTimeMillis();
					}

				}
			}
		} catch( Exception ex ) {
			ex.printStackTrace();
			System.out.println( "A fatal exception has been thrown!" );
			for( Player p: PlayerHandler.players ) {
				if( p == null ) {
					continue;
				}
				PlayerSave.saveGame( ( Client )p );
			}
		}
		acceptor = null;
		connectionHandler = null;
		sac = null;
		System.exit( 0 );
	}


	public static void processAllPackets()
	{
		for( Player player: PlayerHandler.players ) {
			if( player != null ) {
				while( player.processQueuedPackets() ) {
					;
				}
			}
		}
	}

	public static boolean playerExecuted = false;


	private static void debug()
	{
		if( debugTimer.elapsed() > 360 * 1000 || playerExecuted ) {
			long averageCycleTime = totalCycleTime / cycles;
			double engineLoad = ( double )averageCycleTime / ( double )cycleRate;
			System.out.println( "Currently online: " + PlayerHandler.playerCount + ", engine load: "
					+ debugPercentFormat.format( engineLoad ) );
			totalCycleTime = 0;
			cycles = 0;
			System.gc();
			System.runFinalization();
			debugTimer.reset();
			playerExecuted = false;
		}
	}


	public static long getSleepTimer()
	{
		return sleepTime;
	}


	public static StillGraphicsManager getStillGraphicsManager()
	{
		return stillGraphicsManager;
	}


	public static PlayerManager getPlayerManager()
	{
		return playerManager;
	}


	public static ObjectManager getObjectManager()
	{
		return objectManager;
	}

}
