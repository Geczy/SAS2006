package server.org.engine.character.combat.prayer;

import server.org.engine.character.Client;

public class PrayerHandler {

	static double[] prayerData = {
		0.5, // Thick Skin.
		0.5, // Burst of Strength.
		0.5, // Clarity of Thought.
		0.5, // Sharp Eye.
		0.5, // Mystic Will.
		1, // Rock Skin.
		1, // SuperHuman Strength.
		1, // Improved Reflexes.
		0.15, // Rapid restore
		0.3, // Rapid Heal.
		0.3, // Protect Items
		1, // Hawk eye.
		1, // Mystic Lore.
		2, // Steel Skin.
		2, // Ultimate Strength.
		2, // Incredible Reflexes.
		2, // Protect from Magic.
		2, // Protect from Missiles.
		2, // Protect from Melee.
		2, // Eagle Eye.
		2, // Mystic Might.
		0.5, // Retribution.
		1, // Redemption.
		2, // Smite
		2, // Chivalry.
		4, // Piety.
    };
	
	public static void handlePrayerDrain(Client c) {
		c.usingPrayer = false;
		double toRemove = 0.0;
		for(int i = 0; i < prayerData.length; i++) {
			if(c.prayerActive[i]) { 
				toRemove += prayerData[i]/10;
				c.usingPrayer = true;
			}
		}
		if (toRemove > 0) {
			toRemove /= (1 + (0.035 * c.playerBonus[11]));		
		}
		c.prayerPoint -= toRemove;
		if (c.prayerPoint <= 0) {
			c.prayerPoint = 1.0 + c.prayerPoint;
			reducePrayerLevel(c);
		}
	}
	public static void reducePrayerLevel(Client c) {
		if(c.playerLevel[5] - 1 > 0) {
			c.playerLevel[5] -= 1;
		} else {
			c.sendMessage("You have run out of prayer points!");
			c.playerLevel[5] = 0;
			resetPrayers(c);
			c.prayerId = -1;	
		}
		c.getPA().refreshSkill(5);
	}
	public static void resetPrayers(Client c) {
		for(int i = 0; i < c.prayerActive.length; i++) {
			c.prayerActive[i] = false;
			c.getPA().sendFrame36(c.PRAYER_GLOW[i], 0);
		}
		c.headIcon = -1;
		c.getPA().requestUpdates();
	}
	
}
