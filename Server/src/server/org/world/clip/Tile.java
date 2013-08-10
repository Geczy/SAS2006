
package server.org.world.clip;

/**
 * @author Killamess Used to represent a object or npc tile.
 */

public class Tile
{

	private final int[] pointer = new int[3];


	public Tile( int x, int y, int z )
	{
		pointer[0] = x;
		pointer[1] = y;
		pointer[2] = z;
	}


	public int[] getTile()
	{
		return pointer;
	}


	public int getTileX()
	{
		return pointer[0];
	}


	public int getTileY()
	{
		return pointer[1];
	}


	public int getTileHeight()
	{
		return pointer[2];
	}

}
