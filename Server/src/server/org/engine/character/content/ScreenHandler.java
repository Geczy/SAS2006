package server.org.engine.character.content;

import server.org.core.util.Misc;
import server.org.engine.character.Client;

/**
 * Screen shaking and cut scences
 * @author Zack.
 *
 */
public class ScreenHandler {

	/**
	 * Where it will zoom
	 * @param c
	 * @param x
	 * @param y
	 * @param height
	 * @param speed
	 * @param angle
	 */
	public static void sendFrame177(Client c,int x, int y, int height, int speed, int angle) {
		c.getOutStream().createFrame(177);
		c.getOutStream().writeByte(x / 64); // X coord within your loaded map area
		c.getOutStream().writeByte(y / 64); // Y coord within your loaded map area
		c.getOutStream().writeWord(height); // HeightLevel
		c.getOutStream().writeByte(speed); //Camera Speed
		c.getOutStream().writeByte(angle); //Angle
	}
	/**
	 * Shaking 
	 * @param c
	 * @param i1
	 * @param i2
	 * @param i3
	 * @param i4
	 */
	public static void sendFrame35(Client c,int i1, int i2, int i3, int i4) {
		c.getOutStream().createFrame(35);
		c.getOutStream().writeByte(i1);
		c.getOutStream().writeByte(i2);
		c.getOutStream().writeByte(i3);
		c.getOutStream().writeByte(i4);
		c.updateRequired = true;
		c.appearanceUpdateRequired = true;
	}

	/**
	 * Reset that shit
	 * @param c
	 */
	public static void ResetScreen(Client c) {
		synchronized(c) {
			if(c.getOutStream() != null && c != null) {
				c.getOutStream().createFrame(107);
				c.flushOutStream();
			}
		}
	}

		public static void Rumble(){
			sendFrame35(null, Misc.random(5), Misc.random(5), Misc.random(15), Misc.random(15));
		}
	
	
}
