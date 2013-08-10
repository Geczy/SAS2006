
package server.org.world.clip;

public class StateObject
{

	private final int objectType;
	private final int objectX;
	private final int objectY;
	private final int objectHeight;
	private final int objectFace;
	private final int objectStateChange;
	private final int objectVType;


	public StateObject( int objectType, int objectX, int objectY, int objectFace, int objectHeight,
			int objectStateChange, int objectVType )
	{
		this.objectType = objectType;
		this.objectX = objectX;
		this.objectY = objectY;
		this.objectFace = objectFace;
		this.objectHeight = objectHeight;
		this.objectStateChange = objectStateChange;
		this.objectVType = objectVType;
	}


	public int getType()
	{
		return objectType;
	}


	public int getX()
	{
		return objectX;
	}


	public int getY()
	{
		return objectY;
	}


	public int getHeight()
	{
		return objectHeight;
	}


	public int getFace()
	{
		return objectFace;
	}


	public int getStatedObject()
	{
		return objectStateChange;
	}


	public int getVType()
	{
		return objectVType;
	}

}
