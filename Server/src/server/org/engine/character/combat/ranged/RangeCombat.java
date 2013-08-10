package server.org.engine.character.combat.ranged;

import server.org.engine.character.Client;

public class RangeCombat {

	public static int calculateRangeAttack(Client c) {
		int attackLevel = c.playerLevel[4];
		attackLevel *= c.specAccuracy;
        if (c.fullVoidRange())
            attackLevel += c.getLevelForXP(c.playerXP[c.playerRanged]) * 0.1;
		if (c.prayerActive[3])
			attackLevel *= 1.05;
		else if (c.prayerActive[11])
			attackLevel *= 1.10;
		else if (c.prayerActive[19])
			attackLevel *= 1.15;
		if (c.fullVoidRange() && c.specAccuracy > 1.15) {
			attackLevel *= 1.75;		
		}
        return (int) (attackLevel + (c.playerBonus[4] * 1.95));
	}
	
	public static int calculateRangeDefence(Client c) {
		int defenceLevel = c.playerLevel[1];
        if (c.prayerActive[0]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.05;
        } else if (c.prayerActive[5]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.1;
        } else if (c.prayerActive[13]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.15;
        } else if (c.prayerActive[24]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.2;
        } else if (c.prayerActive[25]) {
            defenceLevel += c.getLevelForXP(c.playerXP[c.playerDefence]) * 0.25;
        }
        return (int) (defenceLevel + c.playerBonus[9] + (c.playerBonus[9] / 2));
	}
	
	public static int rangeMaxHit(Client c) {
		int rangeLevel = c.playerLevel[4];
		double modifier = 1.0;
		double wtf = c.specDamage;
		int itemUsed = c.usingBow ? c.lastArrowUsed : c.lastWeaponUsed;
		if (c.prayerActive[3])
			modifier += 0.05;
		else if (c.prayerActive[11])
			modifier += 0.10;
		else if (c.prayerActive[19])
			modifier += 0.15;
		if (c.fullVoidRange())
			modifier += 0.20;
		double c1 = modifier * rangeLevel;
		int rangeStr = getRangeStr(c, itemUsed);
		double max =(c1 + 8) * (rangeStr + 64) / 640;
		if (wtf != 1)
			max *= wtf;
		if (max < 1)
			max = 1;
		return (int)max;
	}
	
	public static int getRangeStr(Client c,int i) {
		if (i == 4214)
			return 70;
		switch (i) {
			//bronze to rune bolts
			case 877:
			return 10;
			case 9140:
			return 46;
			case 9141:
			return 64;
			case 9142:
			case 9241:
			case 9240:
			return 82;
			case 9143:
			case 9243:
			case 9242:
			return 100;
			case 9144:
			case 9244:
			case 9245:
			return 115;
			//bronze to dragon arrows
			case 882:
			return 7;
			case 884:
			return 10;
			case 886:
			return 16;
			case 888:
			return 22;
			case 890:
			return 31;
			case 892:
			case 4740:
			return 49;
			case 11212:
			return 60;
			//knifes
			case 864:
			return 3;
			case 863:
			return 4;
			case 865:
			return 7;
			case 866:
			return 10;
			case 867:
			return 14;
			case 868:
			return 24;
		}
		return 0;
	}
	
	public static int correctBowAndArrows(Client c) {
		switch(c.playerEquipment[c.playerWeapon]) {
			
			case 839:
			case 841:
			return 882;
			
			case 843:
			case 845:
			return 884;
			
			case 847:
			case 849:
			return 886;
			
			case 851:
			case 853:
			return 888;        
			
			case 855:
			case 857:
			return 890;
			
			case 859:
			case 861:
			return 892;
			
			case 4734:
			case 4935:
			case 4936:
			case 4937:
			return 4740;
			
			case 11235:
			return 11212;
		}
		return -1;
	}
	
}
