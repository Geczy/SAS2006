package server.org.engine.character.combat;

import server.org.Config;
import server.org.Server;
import server.org.core.util.Misc;
import server.org.engine.character.Client;

public class CombatData {

	
	public static boolean usingCrystalBow(Client c) {
		return c.playerEquipment[c.playerWeapon] >= 4212 && c.playerEquipment[c.playerWeapon] <= 4223;	
	}
	public static boolean properBolts(Client c) {
		return c.playerEquipment[c.playerArrows] >= 9140 && c.playerEquipment[c.playerArrows] <= 9144
				|| c.playerEquipment[c.playerArrows] >= 9240 && c.playerEquipment[c.playerArrows] <= 9244;
	}
	public static boolean armaNpc(int i) {
		switch (Server.npcHandler.npcs[i].npcType) {
			case 2558:
			case 2559:
			case 2560:
			case 2561:
			return true;	
		}
		return false;	
	}
	public static int getKillerId(Client c,int playerId) {
		int oldDamage = 0;
		int count = 0;
		int killerId = 0;
		for (int i = 1; i < Config.MAX_PLAYERS; i++) {	
			if (Server.playerHandler.players[i] != null) {
				if(Server.playerHandler.players[i].killedBy == playerId) {
					if (Server.playerHandler.players[i].withinDistance(Server.playerHandler.players[playerId])) {
						if(Server.playerHandler.players[i].totalPlayerDamageDealt > oldDamage) {
							oldDamage = Server.playerHandler.players[i].totalPlayerDamageDealt;
							killerId = i;
						}
					}	
					Server.playerHandler.players[i].totalPlayerDamageDealt = 0;
					Server.playerHandler.players[i].killedBy = 0;
				}	
			}
		}				
		return killerId;
	}
		
	public static boolean usingHally(Client c) {
		switch(c.playerEquipment[c.playerWeapon]) {
			case 3190:
			case 3192:
			case 3194:
			case 3196:
			case 3198:
			case 3200:
			case 3202:
			case 3204:
			return true;
			
			default:
			return false;
		}
	}
	public static int getRequiredDistance(Client c) {
		if (c.followId > 0 && c.freezeTimer <= 0 && !c.isMoving)
			return 2;
		else if(c.followId > 0 && c.freezeTimer <= 0 && c.isMoving) {
			return 3;
		} else {
			return 1;
		}
	}
	public static void applyRecoil(Client c,int damage, int i) {
		if (damage > 0 && Server.playerHandler.players[i].playerEquipment[c.playerRing] == 2550) {
			int recDamage = damage/10 + 1;
			if (!c.getHitUpdateRequired()) {
				c.setHitDiff(recDamage);
				c.setHitUpdateRequired(true);				
			} else if (!c.getHitUpdateRequired2()) {
				c.setHitDiff2(recDamage);
				c.setHitUpdateRequired2(true);
			}
			c.dealDamage(recDamage);
			c.updateRequired = true;
		}	
	}
	public static void handleGmaulPlayer(Client c) {
		if (c.playerIndex > 0) {
			Client o = (Client)Server.playerHandler.players[c.playerIndex];
			if (c.goodDistance(c.getX(), c.getY(), o.getX(), o.getY(), c.getCombat().getRequiredDistance())) {
 				if (c.getCombat().checkReqs()) {
					if (c.getCombat().checkSpecAmount(4153)) {						
 						boolean hit = Misc.random(c.getCombat().calculateMeleeAttack()) > Misc.random(o.getCombat().calculateMeleeDefence());
						int damage = 0;
						if (hit)
							damage = Misc.random(c.getCombat().calculateMeleeMaxHit());
						if (o.prayerActive[18] && System.currentTimeMillis() - o.protMeleeDelay > 1500)
							damage *= .6;
						if(o.playerLevel[3] - damage <= 0) {
							damage = o.playerLevel[3];
						}
						if(o.playerLevel[3] > 0) {
							o.handleHitMask(damage);
							c.startAnimation(1667);
							o.gfx100(337);
							o.dealDamage(damage);
						}
					}	
				}	
			}			
		} else if(c.npcIndex > 0) {
			int x = Server.npcHandler.npcs[c.npcIndex].absX;
			int y = Server.npcHandler.npcs[c.npcIndex].absY;
			if (c.goodDistance(c.getX(), c.getY(), x, y, 2)) {
				if (c.getCombat().checkReqs()) {
					if (c.getCombat().checkSpecAmount(4153)) {
						int damage = Misc.random(c.getCombat().calculateMeleeMaxHit());
						if(Server.npcHandler.npcs[c.npcIndex].HP - damage < 0) {
							damage = Server.npcHandler.npcs[c.npcIndex].HP;
						}
						if(Server.npcHandler.npcs[c.npcIndex].HP > 0) {
							Server.npcHandler.npcs[c.npcIndex].HP -= damage;
							Server.npcHandler.npcs[c.npcIndex].handleHitMask(damage);
							c.startAnimation(1667);
							c.gfx100(337);
						}
					}
				}
			}
		}
	}
	public static int getCombatDifference(int combat1, int combat2) {
		if(combat1 > combat2) {
			return (combat1 - combat2);
		}
		if(combat2 > combat1) {
			return (combat2 - combat1);
		}	
		return 0;
	}
	public static void fireProjectileNpc(Client c) {
		if(c.oldNpcIndex > 0) {
			if(Server.npcHandler.npcs[c.oldNpcIndex] != null) {
				c.projectileStage = 2;
				int pX = c.getX();
				int pY = c.getY();
				int nX = Server.npcHandler.npcs[c.oldNpcIndex].getX();
				int nY = Server.npcHandler.npcs[c.oldNpcIndex].getY();
				int offX = (pY - nY)* -1;
				int offY = (pX - nX)* -1;
				c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, c.getCombat().getProjectileSpeed(), c.getCombat().getRangeProjectileGFX(), 43, 31, c.oldNpcIndex + 1, c.getCombat().getStartDelay());
			}
		}
	}
	public static void fireProjectilePlayer(Client c) {
		if(c.oldPlayerIndex > 0) {
			if(Server.playerHandler.players[c.oldPlayerIndex] != null) {
				c.projectileStage = 2;
				int pX = c.getX();
				int pY = c.getY();
				int oX = Server.playerHandler.players[c.oldPlayerIndex].getX();
				int oY = Server.playerHandler.players[c.oldPlayerIndex].getY();
				int offX = (pY - oY)* -1;
				int offY = (pX - oX)* -1;	
				if (!c.msbSpec)
					c.getPA().createPlayersProjectile(pX, pY, offX, offY, 50, c.getCombat().getProjectileSpeed(), c.getCombat().getRangeProjectileGFX(), 43, 31, - c.oldPlayerIndex - 1, c.getCombat().getStartDelay());
				else if (c.msbSpec) {
					c.getPA().createPlayersProjectile2(pX, pY, offX, offY, 50, c.getCombat().getProjectileSpeed(), c.getCombat().getRangeProjectileGFX(), 43, 31, - c.oldPlayerIndex - 1, c.getCombat().getStartDelay(), 10);
					c.msbSpec = false;
				}
			}
		}
	}
	public static void applySmite(Client c,int index, int damage) {
		if (!c.prayerActive[23])
			return;
		if (damage <= 0)
			return;
		if (Server.playerHandler.players[index] != null) { 
			Client c2 = (Client)Server.playerHandler.players[index];
			c2.playerLevel[5] -= (int)(damage/4);
			if (c2.playerLevel[5] <= 0) {
				c2.playerLevel[5] = 0;
				c2.getCombat().resetPrayers();
			}
			c2.getPA().refreshSkill(5);
		}
	
	}
	
}
