
package server.org.engine.character.content;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.ArrayList;

import server.org.Server;
import server.org.core.util.Misc;
import server.org.engine.character.Client;
import server.org.engine.character.Player;
import server.org.engine.character.PlayerHandler;

/**
 * @Author chri55w
 */

public class lottery
{

	public static ArrayList<String> lotteryPlayersNames = new ArrayList<String>(); // players
																					// (playername)
	public static ArrayList<String> unclaimedWinners = new ArrayList<String>(); // Winners
																				// that
																				// havent
																				// claimed
	public static int lotteryFund = 0;
	public int prizeAmount = 10000;
	public static int entryPrice = 2000;
	public static int maximumEntryTimes = 1;
	public static long lastAnnouncment;
	public static long nextDraw;
	public long nextDrawFrequency = 3;
	public int announcmentFrequency = 1;


	public static void Process()
	{
		if( System.currentTimeMillis() - lottery.lastAnnouncment > 1000 * 60 * Server.lottery.announcmentFrequency ) {
			announceFund();
			lastAnnouncment = System.currentTimeMillis();
		}
		if( System.currentTimeMillis() - lottery.nextDraw > 1000 * 60 * Server.lottery.nextDrawFrequency
				&& lotteryFund >= 4000 ) {
			drawLottery();
			nextDraw = System.currentTimeMillis();
		}
	}


	public static int checkEntriesCount( Client c )
	{
		int entries = 0;
		entries = 0;
		for( int indexes = 0; indexes < lotteryPlayersNames.size(); indexes ++ ) {
			if( lotteryPlayersNames.get( indexes ).equalsIgnoreCase( "" + c.playerName ) ) {
				entries += 1;
			}
		}
		return entries;
	}


	public static void enterLottery( Client c )
	{
		if( checkEntriesCount( c ) < maximumEntryTimes ) {
			if( c.getItems().playerHasItem( 995, entryPrice ) ) {
				lotteryPlayersNames.add( c.playerName );
				lotteryFund += entryPrice;
				c.getItems().deleteItem2( 995, entryPrice );

				for( Player player: PlayerHandler.players ) {
					if( player != null ) {
						Client all = ( Client )player;
						all.sendMessage( "[@blu@Lottery@bla@] " + c.playerName.toUpperCase()
								+ " Has Entered the lottery." );
					}
				}
				// c.sendMessage("@red@You have been entered into the lottery");
				try {
					BufferedWriter report = new BufferedWriter( new java.io.FileWriter(
							"./Data/lottery/LotteryEnteries.txt", true ) );
					try {
						report.newLine();
						report.write( c.playerName );
					} finally {
						report.close();
					}
				} catch( IOException e ) {
					e.printStackTrace();
				}
			} else {
				c.sendMessage( "You dont have enough cash!" );
			}
		} else {
			c.sendMessage( "You have already Entered the lottery." );
		}
	}


	public static void drawLottery()
	{
		boolean prizeGiven = false;
		int arraySize = lotteryPlayersNames.size() - 1;
		int winner = Misc.random( arraySize );
		try {
			String player = lotteryPlayersNames.get( winner );
			Client c = null;
			for( Player player2: PlayerHandler.players ) {
				if( player2 != null ) {
					if( player2.playerName.equalsIgnoreCase( player ) ) {
						c = ( Client )player2;
						c.sendMessage( "@red@You have won the lottery!" );
						prizeGiven = true;
						if( c.getItems().freeSlots() > 0 ) {
							c.getItems().addItem( 995, lotteryFund );
							try {
								BufferedWriter report = new BufferedWriter( new java.io.FileWriter(
										"./Data/lottery/LotteryWinners.txt", true ) );
								try {
									report.newLine();
									report.write( c.playerName );
								} finally {
									report.close();
								}
							} catch( IOException e ) {
								e.printStackTrace();
							}

						} else {
							c.sendMessage( "You had no space in inventory, " + lotteryFund / 1000
									+ "k has been added to your bank." );
							c.getItems().addItemToBank( 995, lotteryFund );
						}
					}
				}
			}
			if( prizeGiven == false ) {
				unclaimedWinners.add( lotteryPlayersNames.get( winner ) );
				prizeGiven = true;
			}
			for( Player player2: PlayerHandler.players ) {
				if( player2 != null ) {
					Client all = ( Client )player2;
					all.sendMessage( "[@blu@Lottery@bla@] The Lottery has been won by "
							+ lotteryPlayersNames.get( winner ) );
				}
			}
		} catch( Exception e ) {
			for( Player player: PlayerHandler.players ) {

				if( player != null ) {
					Client all = ( Client )player;
					System.out.println( "Lottery draw failed!" );
					all.sendMessage( "[@blu@Lottery@bla@] The Lottery has failed to draw, No one has entered." );

				}
			}
		}
		lotteryFund = 0;
		lotteryPlayersNames.clear();
		prizeGiven = false;
	}


	public static void announceFund()
	{
		int fund = lotteryFund;
		for( Player player: PlayerHandler.players ) {
			if( player != null ) {
				Client all = ( Client )player;
				all.sendMessage( "[@blu@Lottery@bla@] The Lottery Jackpot is currently at @blu@" + fund / 1000
						+ "k@bla@. To Enter speak to Duke Horasio who" );
				all.sendMessage( "can be located at Varrock Center." );
			}
		}
	}


	public void checkUnclaimedWinners( Client c )
	{
		if( unclaimedWinners.contains( c.playerName ) ) {
			if( c.getItems().freeSlots() > 0 ) {
				c.sendMessage( "You have been given your reward for winning the lottery!" );
				c.getItems().addItem( 995, prizeAmount );
				unclaimedWinners.remove( unclaimedWinners.indexOf( c.playerName ) );
			} else {
				c.sendMessage( "You have won the lottery but do not have space for the reward!" );
			}
		}
	}
}
