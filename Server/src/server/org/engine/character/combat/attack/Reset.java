package server.org.engine.character.combat.attack;

import server.org.engine.character.Client;

public class Reset {
	
	public static void resetPlayerAttack(Client c) {
		c.usingMagic = false;
		c.npcIndex = 0;
		c.faceUpdate(0);
		c.playerIndex = 0;
		c.getPA().resetFollow();
		//c.sendMessage("Reset attack.");
	}
}
