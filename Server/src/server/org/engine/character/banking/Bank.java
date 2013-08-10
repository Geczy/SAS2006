package server.org.engine.character.banking;

import server.org.engine.character.Client;
/**
 * Public Class Bank Used
 * In action handler for items
 *
 */
public class Bank {
	
	/**
	 * 
	 * Bank ids in an array
	 * 
	 */
	public int bankIds[]={2213,14367,11758};
/**
 * 
 * Click object method
 * As you can see the name ha
 * 
 */
	public void clickObject(Client c,int objectType){
		if (objectType == bankIds.length){
			openUpBank(c);
		}
	}
	
	/**
	 * 
	 * Open bank bank method
	 * 
	 */
	public static void openUpBank(Client c){
			if(c.getOutStream() != null && c != null) {
				c.getItems().resetItems(5064);
				c.getItems().rearrangeBank();
				c.getItems().resetBank();
				c.getItems().resetTempItems();
				c.getOutStream().createFrame(248);
				c.getOutStream().writeWordA(5292);
				c.getOutStream().writeWord(5063);
				c.flushOutStream();
			}
	}
	
/**
 * 
 * End of class
 * 
 */
	
}
