package server.org.engine.character.combat.melee;

import server.org.engine.character.Client;

public class MeleeCombat {

	

	public static int bestMeleeDef(Client c) {
        if(c.playerBonus[5] > c.playerBonus[6] && c.playerBonus[5] > c.playerBonus[7])
            return 5;
        if(c.playerBonus[6] > c.playerBonus[5] && c.playerBonus[6] > c.playerBonus[7])
            return 6;
        return c.playerBonus[7] <= c.playerBonus[5] || c.playerBonus[7] <= c.playerBonus[6] ? 5 : 7;
    }
	public static int bestMeleeAtk(Client c)
    {
        if(c.playerBonus[0] > c.playerBonus[1] && c.playerBonus[0] > c.playerBonus[2])
            return 0;
        if(c.playerBonus[1] > c.playerBonus[0] && c.playerBonus[1] > c.playerBonus[2])
            return 1;
        return c.playerBonus[2] <= c.playerBonus[1] || c.playerBonus[2] <= c.playerBonus[0] ? 0 : 2;
    }
	public static int calculateMeleeAttack(Client c) {
		return MeleeCalculator.calculateMeleeAttack(c);
	}
	
	public static int calculateMeleeDefence(Client c) {
		return MeleeCalculator.calculateMeleeDefence(c);
     }
	
	public static int calculateMeleeMaxHit(Client c) {
		return MeleeCalculator.calculateMeleeMaxHit(c);
	}
	
}
