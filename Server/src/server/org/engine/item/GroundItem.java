
package server.org.engine.item;

public class GroundItem
{

	public int itemId;
	public int itemX;
	public int itemY;
	public int itemAmount;
	public int itemController;
	public int hideTicks;
	public int removeTicks;
	public String ownerName;
	public int heightLevel;


	public GroundItem( int id, int x, int y, int amount, int controller, int hideTicks, String name )
	{
		itemId = id;
		itemX = x;
		itemY = y;
		itemAmount = amount;
		itemController = controller;
		this.hideTicks = hideTicks;
		ownerName = name;
	}


	public GroundItem( int id, int x, int y, int height, int amount, int controller, int hideTicks, String name )
	{
		itemId = id;
		itemX = x;
		itemY = y;
		heightLevel = height;
		itemAmount = amount;
		itemController = controller;
		this.hideTicks = hideTicks;
		ownerName = name;
	}


	public int getItemId()
	{
		return itemId;
	}


	public int getItemX()
	{
		return itemX;
	}


	public int getItemY()
	{
		return itemY;
	}


	public int getItemAmount()
	{
		return itemAmount;
	}


	public int getItemController()
	{
		return itemController;
	}


	public String getName()
	{
		return ownerName;
	}

}
