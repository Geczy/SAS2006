
package server.org.world.manager;

import java.util.ArrayList;

import server.org.Server;
import server.org.core.util.Misc;
import server.org.engine.character.Client;
import server.org.engine.character.Player;
import server.org.engine.character.PlayerHandler;
import server.org.world.Object;

/**
 * @author Sanity
 */

public class ObjectManager
{

	public boolean objectExists( final int x, final int y )
	{
		for( Object o: objects ) {
			if( o.objectX == x && o.objectY == y ) {
				return true;
			}
		}
		return false;
	}

	public ArrayList<Object> objects = new ArrayList<Object>();
	private final ArrayList<Object> toRemove = new ArrayList<Object>();


	public void process()
	{
		for( final Object o: objects ) {
			if( o.tick > 0 ) {
				o.tick -- ;
			} else {
				updateObject( o );
				toRemove.add( o );
			}
		}
		for( final Object o: toRemove ) {
			if( o.objectId == 2732 ) {
				for( final Player player: PlayerHandler.players ) {
					if( player != null ) {
						final Client c = ( Client )player;
						Server.itemHandler.createGroundItem( c, 592, o.objectX, o.objectY, 1, c.playerId );
					}
				}
			}
			if( isObelisk( o.newId ) ) {
				final int index = getObeliskIndex( o.newId );
				if( activated[index] ) {
					activated[index] = false;
					teleportObelisk( index );
				}
			}
			objects.remove( o );
		}
		toRemove.clear();
	}


	public void removeObject( int x, int y )
	{
		for( Player player: PlayerHandler.players ) {
			if( player != null ) {
				Client c = ( Client )player;
				c.getPA().object( - 1, x, y, 0, 10 );
			}
		}
	}


	public void updateObject( Object o )
	{
		for( Player player: PlayerHandler.players ) {
			if( player != null ) {
				Client c = ( Client )player;
				c.getPA().object( o.newId, o.objectX, o.objectY, o.face, o.type );
			}
		}
	}


	public void placeObject( Object o )
	{
		for( Player player: PlayerHandler.players ) {
			if( player != null ) {
				Client c = ( Client )player;
				if( c.distanceToPoint( o.objectX, o.objectY ) <= 60 ) {
					c.getPA().object( o.objectId, o.objectX, o.objectY, o.face, o.type );
				}
			}
		}
	}


	public Object getObject( int x, int y, int height )
	{
		for( Object o: objects ) {
			if( o.objectX == x && o.objectY == y && o.height == height ) {
				return o;
			}
		}
		return null;
	}


	public void loadObjects( Client c )
	{
		if( c == null ) {
			return;
		}
		for( Object o: objects ) {
			if( loadForPlayer( o, c ) ) {
				c.getPA().object( o.objectId, o.objectX, o.objectY, o.face, o.type );
			}
		}
		loadCustomSpawns( c );
		if( c.distanceToPoint( 2813, 3463 ) <= 60 ) {
			c.getFarming().updateHerbPatch();
		}
	}


	public void loadCustomSpawns( Client c )
	{
		// c.getPA().checkObjectSpawn(2287, 2552, 3559, 1, 10);
	}

	public final int IN_USE_ID = 14825;


	public boolean isObelisk( int id )
	{
		for( int obeliskId: obeliskIds ) {
			if( obeliskId == id ) {
				return true;
			}
		}
		return false;
	}

	public int[] obeliskIds = { 14829, 14830, 14827, 14828, 14826, 14831 };
	public int[][] obeliskCoords = {
		{ 3154, 3618 },
		{ 3225, 3665 },
		{ 3033, 3730 },
		{ 3104, 3792 },
		{ 2978, 3864 },
		{ 3305, 3914 } };
	public boolean[] activated = { false, false, false, false, false, false };


	public void startObelisk( int obeliskId )
	{
		int index = getObeliskIndex( obeliskId );
		if( index >= 0 ) {
			if( ! activated[index] ) {
				activated[index] = true;
				addObject( new Object( 14825, obeliskCoords[index][0], obeliskCoords[index][1], 0, - 1, 10, obeliskId,
						16 ) );
				addObject( new Object( 14825, obeliskCoords[index][0] + 4, obeliskCoords[index][1], 0, - 1, 10,
						obeliskId, 16 ) );
				addObject( new Object( 14825, obeliskCoords[index][0], obeliskCoords[index][1] + 4, 0, - 1, 10,
						obeliskId, 16 ) );
				addObject( new Object( 14825, obeliskCoords[index][0] + 4, obeliskCoords[index][1] + 4, 0, - 1, 10,
						obeliskId, 16 ) );
			}
		}
	}


	public int getObeliskIndex( int id )
	{
		for( int j = 0; j < obeliskIds.length; j ++ ) {
			if( obeliskIds[j] == id ) {
				return j;
			}
		}
		return - 1;
	}


	public void teleportObelisk( int port )
	{
		int random = Misc.random( 5 );
		while( random == port ) {
			random = Misc.random( 5 );
		}
		for( Player player: PlayerHandler.players ) {
			if( player != null ) {
				Client c = ( Client )player;
				int xOffset = c.absX - obeliskCoords[port][0];
				int yOffset = c.absY - obeliskCoords[port][1];
				if( c.goodDistance( c.getX(), c.getY(), obeliskCoords[port][0] + 2, obeliskCoords[port][1] + 2, 1 ) ) {
					c.getPA()
							.startTeleport2( obeliskCoords[random][0] + xOffset, obeliskCoords[random][1] + yOffset, 0 );
				}
			}
		}
	}


	public boolean loadForPlayer( Object o, Client c )
	{
		if( o == null || c == null ) {
			return false;
		}
		return c.distanceToPoint( o.objectX, o.objectY ) <= 60 && Player.heightLevel == o.height;
	}


	public void addObject( Object o )
	{
		if( getObject( o.objectX, o.objectY, o.height ) == null ) {
			objects.add( o );
			placeObject( o );
		}
	}

}
