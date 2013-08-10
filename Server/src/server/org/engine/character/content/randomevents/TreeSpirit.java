package server.org.engine.character.content.randomevents;

import server.org.Server;
import server.org.core.util.Misc;
import server.org.engine.character.Client;

public class TreeSpirit {
	
	public static int spirit = 0;

	public static void PlaceSpirit(Client c){
		int random = 200;
		if(Misc.random(random) >= 199 && spirit == 0){
			spirit++;
			if(c.combatLevel <= 20){
				Server.npcHandler.spawnNpc(c, 438,c.absX,c.absY,c.height,0,23,3,42,12, true, true);
			}else if(c.combatLevel >= 21 && c.combatLevel <= 39){
				Server.npcHandler.spawnNpc(c, 439,c.absX,c.absY,c.height,0,44,7,62,24, true, true);
			}else if(c.combatLevel >= 40 && c.combatLevel <= 66){
				Server.npcHandler.spawnNpc(c, 440,c.absX,c.absY,c.height,0,62,12,79,42, true, true);
			}else if(c.combatLevel >= 67 && c.combatLevel <= 88){
				Server.npcHandler.spawnNpc(c, 441,c.absX,c.absY,c.height,0,104,16,110,72, true, true);
			}else if(c.combatLevel >= 89 && c.combatLevel <= 105){
				Server.npcHandler.spawnNpc(c, 442,c.absX,c.absY,c.height,0,133,19,145,97, true, true);
			}else if(c.combatLevel >= 106){
				Server.npcHandler.spawnNpc(c, 443,c.absX,c.absY,c.height,0,165,23,179,142, true, true);
			}else{
				
				}
		}else{
			
		}
	}
	
}
