package server.org.engine.character;

public class ArrowManager {

	public static void createArrow(Client c,int type, int id) {
	if(c != null){
		c.getOutStream().createFrame(254); //The packet ID
		c.getOutStream().writeByte(type); //1=NPC, 10=Player
		c.getOutStream().writeWord(id); //NPC/Player ID
		c.getOutStream().write3Byte(0); //Junk
			}
	}
	public static void createArrow(Client c,int x, int y, int height, int pos) {
		if(c != null){
			c.getOutStream().createFrame(254); //The packet ID
			c.getOutStream().writeByte(pos); //Position on Square(2 = middle, 3 = west, 4 = east, 5 = south, 6 = north)
			c.getOutStream().writeWord(x); //X-Coord of Object
			c.getOutStream().writeWord(y); //Y-Coord of Object
			c.getOutStream().writeByte(height); //Height off Ground
		}
		}
	
}
