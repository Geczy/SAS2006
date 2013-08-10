package server.org.engine.character.content.randomevents;

import server.org.Server;
import server.org.core.util.Misc;
import server.org.engine.character.Client;

public class BirdsNest {
	
	public static int nests[] = {5070,5071,5072,5073,5074,5075};
	
	public static int Nests() {
		return nests[(int) (Math.random()*nests.length)];
	}
	
	
	public static void DropNest(Client c){
		int birdnest = 200;
		if(Misc.random(birdnest) >= 199){
			c.sendMessage("@red@A bird nest fall's out of the tree and hits the ground.");
			Server.itemHandler.createGroundItem(c, Nests(), c.absX, c.absY, 1, c.playerId);
		}else{
			
		}
	}

}
