
package server.org.world.handler;

import server.org.engine.character.Client;
import server.org.engine.character.PlayerHandler;
import server.org.world.Clan;

/**
 * @author Sanity
 */

public class ClanChatHandler
{

	public ClanChatHandler()
	{

	}

	public Clan[] clans = new Clan[100];


	public void handleClanChat( Client c, String name )
	{
		for( int j = 0; j < clans.length; j ++ ) {
			if( clans[j] != null ) {
				if( clans[j].name.equalsIgnoreCase( name ) ) {
					addToClan( c.playerId, j );
					return;
				}
			}
		}
		makeClan( c, name );
	}


	public void makeClan( Client c, String name )
	{
		if( openClan() >= 0 ) {
			if( validName( name ) ) {
				c.clanId = openClan();
				clans[c.clanId] = new Clan( c, name );
				addToClan( c.playerId, c.clanId );
			} else {
				c.sendMessage( "A clan with this name already exists." );
			}
		} else {
			c.sendMessage( "Your clan chat request could not be completed." );
		}
	}


	public void updateClanChat( int clanId )
	{
		for( int member: clans[clanId].members ) {
			if( member <= 0 ) {
				continue;
			}
			if( PlayerHandler.players[member] != null ) {
				Client c = ( Client )PlayerHandler.players[member];
				c.getPA().sendFrame126( "Talking in: " + clans[clanId].name, 18139 );
				c.getPA().sendFrame126( "Owner: " + clans[clanId].owner, 18140 );
				int slotToFill = 18144;
				for( int member2: clans[clanId].members ) {
					if( member2 > 0 ) {
						if( PlayerHandler.players[member2] != null ) {
							c.getPA().sendFrame126( PlayerHandler.players[member2].playerName,
									slotToFill );
							slotToFill ++ ;
						}
					}
				}
				for( int k = slotToFill; k < 18244; k ++ ) {
					c.getPA().sendFrame126( "", k );
				}
			}
		}
	}


	public int openClan()
	{
		for( int j = 0; j < clans.length; j ++ ) {
			if( clans[j] == null || clans[j].owner == "" ) {
				return j;
			}
		}
		return - 1;
	}


	public boolean validName( String name )
	{
		for( Clan clan: clans ) {
			if( clan != null ) {
				if( clan.name.equalsIgnoreCase( name ) ) {
					return false;
				}
			}
		}
		return true;
	}


	public void addToClan( int playerId, int clanId )
	{
		if( clans[clanId] != null ) {
			for( int j = 0; j < clans[clanId].members.length; j ++ ) {
				if( clans[clanId].members[j] <= 0 ) {
					clans[clanId].members[j] = playerId;
					PlayerHandler.players[playerId].clanId = clanId;
					// c.sendMessage("You have joined the clan chat: " +
					// clans[clanId].name);
					messageToClan( PlayerHandler.players[playerId].playerName + " has joined the channel.",
							clanId );
					updateClanChat( clanId );
					return;
				}
			}
		}
	}


	public void leaveClan( int playerId, int clanId )
	{
		if( clanId < 0 ) {
			Client c = ( Client )PlayerHandler.players[playerId];
			c.sendMessage( "You are not in a clan." );
			return;
		}
		if( clans[clanId] != null ) {
			if( PlayerHandler.players[playerId].playerName.equalsIgnoreCase( clans[clanId].owner ) ) {
				messageToClan( "The clan has been deleted by the owner.", clanId );
				destructClan( PlayerHandler.players[playerId].clanId );
				return;
			}
			for( int j = 0; j < clans[clanId].members.length; j ++ ) {
				if( clans[clanId].members[j] == playerId ) {
					clans[clanId].members[j] = - 1;
				}
			}
			if( PlayerHandler.players[playerId] != null ) {
				Client c = ( Client )PlayerHandler.players[playerId];
				PlayerHandler.players[playerId].clanId = - 1;
				c.sendMessage( "You have left the clan." );
				c.getPA().clearClanChat();
			}
			updateClanChat( clanId );
		} else {
			Client c = ( Client )PlayerHandler.players[playerId];
			PlayerHandler.players[playerId].clanId = - 1;
			c.sendMessage( "You are not in a clan." );
		}
	}


	public void destructClan( int clanId )
	{
		if( clanId < 0 ) {
			return;
		}
		for( int member: clans[clanId].members ) {
			if( clanId < 0 ) {
				continue;
			}
			if( member <= 0 ) {
				continue;
			}
			if( PlayerHandler.players[member] != null ) {
				Client c = ( Client )PlayerHandler.players[member];
				c.clanId = - 1;
				c.getPA().clearClanChat();
			}
		}
		clans[clanId].members = new int[50];
		clans[clanId].owner = "";
		clans[clanId].name = "";
	}


	public void messageToClan( String message, int clanId )
	{
		if( clanId < 0 ) {
			return;
		}
		for( int member: clans[clanId].members ) {
			if( member < 0 ) {
				continue;
			}
			if( PlayerHandler.players[member] != null ) {
				Client c = ( Client )PlayerHandler.players[member];
				c.sendMessage( "@red@" + message );
			}
		}
	}


	public void playerMessageToClan( int playerId, String message, int clanId )
	{
		if( clanId < 0 ) {
			return;
		}
		for( int member: clans[clanId].members ) {
			if( member <= 0 ) {
				continue;
			}
			if( PlayerHandler.players[member] != null ) {
				Client c = ( Client )PlayerHandler.players[member];
				// c.sendMessage("["+Server.playerHandler.players[playerId].playerName+"] - "
				// + message");
				// sendClan(String name, String message, String clan, int
				// rights)
				c.sendClan( PlayerHandler.players[playerId].playerName, message, clans[clanId].name,
						PlayerHandler.players[playerId].playerRights );
			}
		}
	}


	public void sendLootShareMessage( int clanId, String message )
	{
		if( clanId >= 0 ) {
			for( int member: clans[clanId].members ) {
				if( member <= 0 ) {
					continue;
				}
				if( PlayerHandler.players[member] != null ) {
					Client c = ( Client )PlayerHandler.players[member];
					c.sendClan( "Lootshare", message, clans[clanId].name, 2 );
				}
			}
		}
	}

	public int[] unallowed = { 592, 530, 526, 536, 1333, 995, 1247, 1089, 1047, 1319 };


	public void handleLootShare( Client c, int item, int amount )
	{
		for( int element: unallowed ) {
			if( item == element ) {
				return;
			}
		}
		if( c.getShops().getItemShopValue( item, 1, c.getItems().getItemSlot( item ) ) > 5000 ) {
			sendLootShareMessage( c.clanId, c.playerName + " has received " + amount + "x "
					+ server.org.engine.item.Item.getItemName( item ) + "." );
		}
	}

}
