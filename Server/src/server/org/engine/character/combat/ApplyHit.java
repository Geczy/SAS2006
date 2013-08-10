
package server.org.engine.character.combat;

import server.org.Config;
import server.org.core.util.Misc;
import server.org.engine.character.Client;
import server.org.engine.character.PlayerHandler;
import server.org.engine.mob.NPCHandler;

public class ApplyHit
{

	public static void applyPlayerMeleeDamage( Client c, int i, int damageMask )
	{
		Client o = ( Client )PlayerHandler.players[i];
		if( o == null ) {
			return;
		}
		int damage = 0;
		boolean veracsEffect = false;
		boolean guthansEffect = false;
		if( c.getPA().fullVeracs() ) {
			if( Misc.random( 4 ) == 1 ) {
				veracsEffect = true;
			}
		}
		if( c.getPA().fullGuthans() ) {
			if( Misc.random( 4 ) == 1 ) {
				guthansEffect = true;
			}
		}
		if( damageMask == 1 ) {
			damage = c.delayedDamage;
			c.delayedDamage = 0;
		} else {
			damage = c.delayedDamage2;
			c.delayedDamage2 = 0;
		}
		if( Misc.random( o.getCombat().calculateMeleeDefence() ) > Misc.random( c.getCombat().calculateMeleeAttack() )
				&& ! veracsEffect ) {
			damage = 0;
			c.bonusAttack = 0;
		} else if( c.playerEquipment[c.playerWeapon] == 5698 && o.poisonDamage <= 0 && Misc.random( 3 ) == 1 ) {
			o.getPA().appendPoison( 13 );
			c.bonusAttack += damage / 3;
		} else {
			c.bonusAttack += damage / 3;
		}
		if( o.prayerActive[18] && System.currentTimeMillis() - o.protMeleeDelay > 1500 && ! veracsEffect ) { // if
																												// prayer
																												// active
																												// reduce
																												// damage
																												// by
																												// 40%
			damage = damage * 60 / 100;
		}
		if( c.maxNextHit ) {
			damage = c.getCombat().calculateMeleeMaxHit();
		}
		if( damage > 0 && guthansEffect ) {
			c.playerLevel[3] += damage;
			if( c.playerLevel[3] > c.getLevelForXP( c.playerXP[3] ) ) {
				c.playerLevel[3] = c.getLevelForXP( c.playerXP[3] );
			}
			c.getPA().refreshSkill( 3 );
			o.gfx0( 398 );
		}
		if( c.ssSpec && damageMask == 2 ) {
			damage = 5 + Misc.random( 11 );
			c.ssSpec = false;
		}
		if( PlayerHandler.players[i].playerLevel[3] - damage < 0 ) {
			damage = PlayerHandler.players[i].playerLevel[3];
		}
		if( damage > 0 ) {
			c.getCombat().applyRecoil( damage, i );
		}
		switch( c.specEffect ) {
			case 1: // dragon scimmy special
				if( damage > 0 ) {
					if( o.prayerActive[16] || o.prayerActive[17] || o.prayerActive[18] ) {
						o.headIcon = - 1;
						o.getPA().sendFrame36( c.PRAYER_GLOW[16], 0 );
						o.getPA().sendFrame36( c.PRAYER_GLOW[17], 0 );
						o.getPA().sendFrame36( c.PRAYER_GLOW[18], 0 );
					}
					o.sendMessage( "You have been injured!" );
					o.stopPrayerDelay = System.currentTimeMillis();
					o.prayerActive[16] = false;
					o.prayerActive[17] = false;
					o.prayerActive[18] = false;
					o.getPA().requestUpdates();
				}
				break;
			case 2:
				if( damage > 0 ) {
					if( o.freezeTimer <= 0 ) {
						o.freezeTimer = 30;
					}
					o.gfx0( 369 );
					o.sendMessage( "You have been frozen." );
					o.frozenBy = c.playerId;
					o.stopMovement();
					c.sendMessage( "You freeze your enemy." );
				}
				break;
			case 3:
				if( damage > 0 ) {
					o.playerLevel[1] -= damage;
					o.sendMessage( "You feel weak." );
					if( o.playerLevel[1] < 1 ) {
						o.playerLevel[1] = 1;
					}
					o.getPA().refreshSkill( 1 );
				}
				break;
			case 4:
				if( damage > 0 ) {
					if( c.playerLevel[3] + damage > c.getLevelForXP( c.playerXP[3] ) ) {
						if( c.playerLevel[3] > c.getLevelForXP( c.playerXP[3] ) ) {
							;
						} else {
							c.playerLevel[3] = c.getLevelForXP( c.playerXP[3] );
						}
					} else {
						c.playerLevel[3] += damage;
					}
					c.getPA().refreshSkill( 3 );
				}
				break;
		}
		c.specEffect = 0;
		if( c.fightMode == 3 ) {
			c.getPA().addSkillXP( damage * Config.MELEE_EXP_RATE / 3, 0 );
			c.getPA().addSkillXP( damage * Config.MELEE_EXP_RATE / 3, 1 );
			c.getPA().addSkillXP( damage * Config.MELEE_EXP_RATE / 3, 2 );
			c.getPA().addSkillXP( damage * Config.MELEE_EXP_RATE / 3, 3 );
			c.getPA().refreshSkill( 0 );
			c.getPA().refreshSkill( 1 );
			c.getPA().refreshSkill( 2 );
			c.getPA().refreshSkill( 3 );
		} else {
			c.getPA().addSkillXP( damage * Config.MELEE_EXP_RATE, c.fightMode );
			c.getPA().addSkillXP( damage * Config.MELEE_EXP_RATE / 3, 3 );
			c.getPA().refreshSkill( c.fightMode );
			c.getPA().refreshSkill( 3 );
		}
		PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
		PlayerHandler.players[i].underAttackBy = c.playerId;
		PlayerHandler.players[i].killerId = c.playerId;
		PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
		if( c.killedBy != PlayerHandler.players[i].playerId ) {
			c.totalPlayerDamageDealt = 0;
		}
		c.killedBy = PlayerHandler.players[i].playerId;
		c.getCombat().applySmite( i, damage );
		switch( damageMask ) {
			case 1:
				/*
				 * if (!Server.playerHandler.players[i].getHitUpdateRequired()){
				 * Server.playerHandler.players[i].setHitDiff(damage);
				 * Server.playerHandler.players[i].setHitUpdateRequired(true); }
				 * else { Server.playerHandler.players[i].setHitDiff2(damage);
				 * Server.playerHandler.players[i].setHitUpdateRequired2(true);
				 * }
				 */
				// Server.playerHandler.players[i].playerLevel[3] -= damage;
				PlayerHandler.players[i].dealDamage( damage );
				PlayerHandler.players[i].damageTaken[c.playerId] += damage;
				c.totalPlayerDamageDealt += damage;
				PlayerHandler.players[i].updateRequired = true;
				o.getPA().refreshSkill( 3 );
				break;

			case 2:
				/*
				 * if
				 * (!Server.playerHandler.players[i].getHitUpdateRequired2()){
				 * Server.playerHandler.players[i].setHitDiff2(damage);
				 * Server.playerHandler.players[i].setHitUpdateRequired2(true);
				 * } else { Server.playerHandler.players[i].setHitDiff(damage);
				 * Server.playerHandler.players[i].setHitUpdateRequired(true); }
				 */
				// Server.playerHandler.players[i].playerLevel[3] -= damage;
				PlayerHandler.players[i].dealDamage( damage );
				PlayerHandler.players[i].damageTaken[c.playerId] += damage;
				c.totalPlayerDamageDealt += damage;
				PlayerHandler.players[i].updateRequired = true;
				c.doubleHit = false;
				o.getPA().refreshSkill( 3 );
				break;
		}
		PlayerHandler.players[i].handleHitMask( damage );
	}


	public static void applyNpcMeleeDamage( Client c, int i, int damageMask )
	{
		int damage = Misc.random( c.getCombat().calculateMeleeMaxHit() );
		boolean fullVeracsEffect = c.getPA().fullVeracs() && Misc.random( 3 ) == 1;
		if( NPCHandler.npcs[i].HP - damage < 0 ) {
			damage = NPCHandler.npcs[i].HP;
		}
		if( ! fullVeracsEffect ) {
			if( Misc.random( NPCHandler.npcs[i].defence ) > 10 + Misc.random( c.getCombat()
					.calculateMeleeAttack() ) ) {
				damage = 0;
			} else if( NPCHandler.npcs[i].npcType == 2882 || NPCHandler.npcs[i].npcType == 2883 ) {
				damage = 0;
			}
		}
		boolean guthansEffect = false;
		if( c.getPA().fullGuthans() ) {
			if( Misc.random( 3 ) == 1 ) {
				guthansEffect = true;
			}
		}
		if( c.fightMode == 3 ) {
			c.getPA().addSkillXP( damage * Config.MELEE_EXP_RATE / 3, 0 );
			c.getPA().addSkillXP( damage * Config.MELEE_EXP_RATE / 3, 1 );
			c.getPA().addSkillXP( damage * Config.MELEE_EXP_RATE / 3, 2 );
			c.getPA().addSkillXP( damage * Config.MELEE_EXP_RATE / 3, 3 );
			c.getPA().refreshSkill( 0 );
			c.getPA().refreshSkill( 1 );
			c.getPA().refreshSkill( 2 );
			c.getPA().refreshSkill( 3 );
		} else {
			c.getPA().addSkillXP( damage * Config.MELEE_EXP_RATE, c.fightMode );
			c.getPA().addSkillXP( damage * Config.MELEE_EXP_RATE / 3, 3 );
			c.getPA().refreshSkill( c.fightMode );
			c.getPA().refreshSkill( 3 );
		}
		if( damage > 0 ) {
			if( NPCHandler.npcs[i].npcType >= 3777 && NPCHandler.npcs[i].npcType <= 3780 ) {
				c.pcDamage += damage;
			}
		}
		if( damage > 0 && guthansEffect ) {
			c.playerLevel[3] += damage;
			if( c.playerLevel[3] > c.getLevelForXP( c.playerXP[3] ) ) {
				c.playerLevel[3] = c.getLevelForXP( c.playerXP[3] );
			}
			c.getPA().refreshSkill( 3 );
			NPCHandler.npcs[i].gfx0( 398 );
		}
		NPCHandler.npcs[i].underAttack = true;
		// Server.npcHandler.npcs[i].killerId = c.playerId;
		c.killingNpcIndex = c.npcIndex;
		c.lastNpcAttacked = i;
		switch( c.specEffect ) {
			case 4:
				if( damage > 0 ) {
					if( c.playerLevel[3] + damage > c.getLevelForXP( c.playerXP[3] ) ) {
						if( c.playerLevel[3] > c.getLevelForXP( c.playerXP[3] ) ) {
							;
						} else {
							c.playerLevel[3] = c.getLevelForXP( c.playerXP[3] );
						}
					} else {
						c.playerLevel[3] += damage;
					}
					c.getPA().refreshSkill( 3 );
				}
				break;

		}
		switch( damageMask ) {
			case 1:
				NPCHandler.npcs[i].hitDiff = damage;
				NPCHandler.npcs[i].HP -= damage;
				c.totalDamageDealt += damage;
				NPCHandler.npcs[i].hitUpdateRequired = true;
				NPCHandler.npcs[i].updateRequired = true;
				break;

			case 2:
				NPCHandler.npcs[i].hitDiff2 = damage;
				NPCHandler.npcs[i].HP -= damage;
				c.totalDamageDealt += damage;
				NPCHandler.npcs[i].hitUpdateRequired2 = true;
				NPCHandler.npcs[i].updateRequired = true;
				c.doubleHit = false;
				break;

		}
	}


	public static int getBonusAttack( Client c, int i )
	{
		switch( NPCHandler.npcs[i].npcType ) {
			case 2883:
				return Misc.random( 50 ) + 30;
			case 2026:
			case 2027:
			case 2029:
			case 2030:
				return Misc.random( 50 ) + 30;
		}
		return 0;
	}

}
