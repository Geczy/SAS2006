
package server.org.engine.character.combat;

import server.org.Config;
import server.org.Server;
import server.org.core.util.Misc;
import server.org.engine.character.Client;
import server.org.engine.character.PlayerHandler;
import server.org.engine.mob.NPCHandler;

public class DelayHit
{

	public DelayHit( Client Client )
	{
	}


	public static void delayedHit( final Client c, final int i )
	{ // npc hit delay
		if( NPCHandler.npcs[i] != null ) {
			if( NPCHandler.npcs[i].isDead ) {
				c.npcIndex = 0;
				return;
			}
			NPCHandler.npcs[i].facePlayer( c.playerId );

			if( NPCHandler.npcs[i].underAttackBy > 0 && Server.npcHandler.getsPulled( i ) ) {
				NPCHandler.npcs[i].killerId = c.playerId;
			} else if( NPCHandler.npcs[i].underAttackBy < 0 && ! Server.npcHandler.getsPulled( i ) ) {
				NPCHandler.npcs[i].killerId = c.playerId;
			}
			c.lastNpcAttacked = i;
			if( c.projectileStage == 0 ) { // melee hit damage
				c.getCombat().applyNpcMeleeDamage( i, 1 );
				if( c.doubleHit ) {
					c.getCombat().applyNpcMeleeDamage( i, 2 );
				}
				c.isUsingSpecial = false;
			}

			if( ! c.castingMagic && c.projectileStage > 0 ) { // range hit
																// damage
				int damage = Misc.random( c.getCombat().rangeMaxHit() );
				int damage2 = - 1;
				if( c.lastWeaponUsed == 11235 || c.bowSpecShot == 1 ) {
					damage2 = Misc.random( c.getCombat().rangeMaxHit() );
				}
				boolean ignoreDef = false;
				if( Misc.random( 5 ) == 1 && c.lastArrowUsed == 9243 ) {
					ignoreDef = true;
					NPCHandler.npcs[i].gfx0( 758 );
				}

				if( Misc.random( NPCHandler.npcs[i].defence ) > Misc.random( 10 + c.getCombat()
						.calculateRangeAttack() ) && ! ignoreDef ) {
					damage = 0;
				} else if( NPCHandler.npcs[i].npcType == 2881 || NPCHandler.npcs[i].npcType == 2883
						&& ! ignoreDef ) {
					damage = 0;
				}

				if( Misc.random( 4 ) == 1 && c.lastArrowUsed == 9242 && damage > 0 ) {
					NPCHandler.npcs[i].gfx0( 754 );
					damage = NPCHandler.npcs[i].HP / 5;
					c.handleHitMask( c.playerLevel[3] / 10 );
					c.dealDamage( c.playerLevel[3] / 10 );
					c.gfx0( 754 );
				}

				if( c.lastWeaponUsed == 11235 || c.bowSpecShot == 1 ) {
					if( Misc.random( NPCHandler.npcs[i].defence ) > Misc.random( 10 + c.getCombat()
							.calculateRangeAttack() ) ) {
						damage2 = 0;
					}
				}
				if( c.dbowSpec ) {
					NPCHandler.npcs[i].gfx100( 1100 );
					if( damage < 8 ) {
						damage = 8;
					}
					if( damage2 < 8 ) {
						damage2 = 8;
					}
					c.dbowSpec = false;

				}
				if( damage > 0 && Misc.random( 5 ) == 1 && c.lastArrowUsed == 9244 ) {
					damage *= 1.45;
					NPCHandler.npcs[i].gfx0( 756 );
				}

				if( NPCHandler.npcs[i].HP - damage < 0 ) {
					damage = NPCHandler.npcs[i].HP;
				}
				if( NPCHandler.npcs[i].HP - damage <= 0 && damage2 > 0 ) {
					damage2 = 0;
				}
				if( c.fightMode == 3 ) {
					c.getPA().addSkillXP( damage * Config.RANGE_EXP_RATE / 3, 4 );
					c.getPA().addSkillXP( damage * Config.RANGE_EXP_RATE / 3, 1 );
					c.getPA().addSkillXP( damage * Config.RANGE_EXP_RATE / 3, 3 );
					c.getPA().refreshSkill( 1 );
					c.getPA().refreshSkill( 3 );
					c.getPA().refreshSkill( 4 );
				} else {
					c.getPA().addSkillXP( damage * Config.RANGE_EXP_RATE, 4 );
					c.getPA().addSkillXP( damage * Config.RANGE_EXP_RATE / 3, 3 );
					c.getPA().refreshSkill( 3 );
					c.getPA().refreshSkill( 4 );
				}
				if( damage > 0 ) {
					if( NPCHandler.npcs[i].npcType >= 3777 && NPCHandler.npcs[i].npcType <= 3780 ) {
						c.pcDamage += damage;
					}
				}
				boolean dropArrows = true;

				for( int noArrowId: c.NO_ARROW_DROP ) {
					if( c.lastWeaponUsed == noArrowId ) {
						dropArrows = false;
						break;
					}
				}
				if( dropArrows ) {
					c.getItems().dropArrowNpc();
				}
				NPCHandler.npcs[i].underAttack = true;
				NPCHandler.npcs[i].hitDiff = damage;
				NPCHandler.npcs[i].HP -= damage;
				if( damage2 > - 1 ) {
					NPCHandler.npcs[i].hitDiff2 = damage2;
					NPCHandler.npcs[i].HP -= damage2;
					c.totalDamageDealt += damage2;
				}
				if( c.killingNpcIndex != c.oldNpcIndex ) {
					c.totalDamageDealt = 0;
				}
				c.killingNpcIndex = c.oldNpcIndex;
				c.totalDamageDealt += damage;
				NPCHandler.npcs[i].hitUpdateRequired = true;
				if( damage2 > - 1 ) {
					NPCHandler.npcs[i].hitUpdateRequired2 = true;
				}
				NPCHandler.npcs[i].updateRequired = true;

			} else if( c.projectileStage > 0 ) { // magic hit damage
				int damage = Misc.random( c.MAGIC_SPELLS[c.oldSpellId][6] );
				if( c.getCombat().godSpells() ) {
					if( System.currentTimeMillis() - c.godSpellDelay < Config.GOD_SPELL_CHARGE ) {
						damage += Misc.random( 10 );
					}
				}
				boolean magicFailed = false;
				// c.npcIndex = 0;
				int bonusAttack = c.getCombat().getBonusAttack( i );
				if( Misc.random( NPCHandler.npcs[i].defence ) > 10 + Misc.random( c.getCombat().mageAtk() )
						+ bonusAttack ) {
					damage = 0;
					magicFailed = true;
				} else if( NPCHandler.npcs[i].npcType == 2881 || NPCHandler.npcs[i].npcType == 2882 ) {
					damage = 0;
					magicFailed = true;
				}

				if( NPCHandler.npcs[i].HP - damage < 0 ) {
					damage = NPCHandler.npcs[i].HP;
				}

				c.getPA().addSkillXP( c.MAGIC_SPELLS[c.oldSpellId][7] + damage * Config.MAGIC_EXP_RATE, 6 );
				c.getPA().addSkillXP( c.MAGIC_SPELLS[c.oldSpellId][7] + damage * Config.MAGIC_EXP_RATE / 3, 3 );
				c.getPA().refreshSkill( 3 );
				c.getPA().refreshSkill( 6 );
				if( damage > 0 ) {
					if( NPCHandler.npcs[i].npcType >= 3777 && NPCHandler.npcs[i].npcType <= 3780 ) {
						c.pcDamage += damage;
					}
				}
				if( c.getCombat().getEndGfxHeight() == 100 && ! magicFailed ) { // end
																				// GFX
					NPCHandler.npcs[i].gfx100( c.MAGIC_SPELLS[c.oldSpellId][5] );
				} else if( ! magicFailed ) {
					NPCHandler.npcs[i].gfx0( c.MAGIC_SPELLS[c.oldSpellId][5] );
				}

				if( magicFailed ) {
					NPCHandler.npcs[i].gfx100( 85 );
				}
				if( ! magicFailed ) {
					int freezeDelay = c.getCombat().getFreezeTime();// freeze
					if( freezeDelay > 0 && NPCHandler.npcs[i].freezeTimer == 0 ) {
						NPCHandler.npcs[i].freezeTimer = freezeDelay;
					}
					switch( c.MAGIC_SPELLS[c.oldSpellId][0] ) {
						case 12901:
						case 12919: // blood spells
						case 12911:
						case 12929:
							int heal = Misc.random( damage / 2 );
							if( c.playerLevel[3] + heal >= c.getPA().getLevelForXP( c.playerXP[3] ) ) {
								c.playerLevel[3] = c.getPA().getLevelForXP( c.playerXP[3] );
							} else {
								c.playerLevel[3] += heal;
							}
							c.getPA().refreshSkill( 3 );
							break;
					}

				}
				NPCHandler.npcs[i].underAttack = true;
				if( c.MAGIC_SPELLS[c.oldSpellId][6] != 0 ) {
					NPCHandler.npcs[i].hitDiff = damage;
					NPCHandler.npcs[i].HP -= damage;
					NPCHandler.npcs[i].hitUpdateRequired = true;
					c.totalDamageDealt += damage;
				}
				c.killingNpcIndex = c.oldNpcIndex;
				NPCHandler.npcs[i].updateRequired = true;
				c.usingMagic = false;
				c.castingMagic = false;
				c.oldSpellId = 0;
			}
		}

		if( c.bowSpecShot <= 0 ) {
			c.oldNpcIndex = 0;
			c.projectileStage = 0;
			c.doubleHit = false;
			c.lastWeaponUsed = 0;
			c.bowSpecShot = 0;
		}
		if( c.bowSpecShot >= 2 ) {
			c.bowSpecShot = 0;
			// c.attackTimer =
			// getAttackDelay(c.getItems().getItemName(c.playerEquipment[c.playerWeapon]).toLowerCase());
		}
		if( c.bowSpecShot == 1 ) {
			c.getCombat().fireProjectileNpc();
			c.hitDelay = 2;
			c.bowSpecShot = 0;
		}
	}


	public static void playerDelayedHit( Client c, int i )
	{
		if( PlayerHandler.players[i] != null ) {
			if( PlayerHandler.players[i].isDead || c.isDead
					|| PlayerHandler.players[i].playerLevel[3] <= 0 || c.playerLevel[3] <= 0 ) {
				c.playerIndex = 0;
				return;
			}
			if( PlayerHandler.players[i].respawnTimer > 0 ) {
				c.faceUpdate( 0 );
				c.playerIndex = 0;
				return;
			}
			Client o = ( Client )PlayerHandler.players[i];
			o.getPA().removeAllWindows();
			if( o.playerIndex <= 0 && o.npcIndex <= 0 ) {
				if( o.autoRet == 1 ) {
					o.playerIndex = c.playerId;
				}
			}
			if( o.attackTimer <= 3 || o.attackTimer == 0 && o.playerIndex == 0 && ! c.castingMagic ) { // block
																										// animation
				o.startAnimation( o.getCombat().getBlockEmote() );
			}
			if( o.inTrade ) {
				o.getTradeAndDuel().declineTrade();
			}
			if( c.projectileStage == 0 ) { // melee hit damage
				c.getCombat().applyPlayerMeleeDamage( i, 1 );
				if( c.doubleHit ) {
					c.getCombat().applyPlayerMeleeDamage( i, 2 );
				}
				c.isUsingSpecial = false;
			}

			if( ! c.castingMagic && c.projectileStage > 0 ) { // range hit
																// damage
				int damage = Misc.random( c.getCombat().rangeMaxHit() );
				int damage2 = - 1;
				if( c.lastWeaponUsed == 11235 || c.bowSpecShot == 1 ) {
					damage2 = Misc.random( c.getCombat().rangeMaxHit() );
				}
				boolean ignoreDef = false;
				if( Misc.random( 4 ) == 1 && c.lastArrowUsed == 9243 ) {
					ignoreDef = true;
					o.gfx0( 758 );
				}
				if( Misc.random( 10 + o.getCombat().calculateRangeDefence() ) > Misc.random( 10 + c.getCombat()
						.calculateRangeAttack() ) && ! ignoreDef ) {
					damage = 0;
				}
				if( Misc.random( 4 ) == 1 && c.lastArrowUsed == 9242 && damage > 0 ) {
					PlayerHandler.players[i].gfx0( 754 );
					damage = NPCHandler.npcs[i].HP / 5;
					c.handleHitMask( c.playerLevel[3] / 10 );
					c.dealDamage( c.playerLevel[3] / 10 );
					c.gfx0( 754 );
				}

				if( c.lastWeaponUsed == 11235 || c.bowSpecShot == 1 ) {
					if( Misc.random( 10 + o.getCombat().calculateRangeDefence() ) > Misc.random( 10 + c.getCombat()
							.calculateRangeAttack() ) ) {
						damage2 = 0;
					}
				}

				if( c.dbowSpec ) {
					o.gfx100( 1100 );
					if( damage < 8 ) {
						damage = 8;
					}
					if( damage2 < 8 ) {
						damage2 = 8;
					}
					c.dbowSpec = false;
				}
				if( damage > 0 && Misc.random( 5 ) == 1 && c.lastArrowUsed == 9244 ) {
					damage *= 1.45;
					o.gfx0( 756 );
				}
				if( o.prayerActive[17] && System.currentTimeMillis() - o.protRangeDelay > 1500 ) { // if
																									// prayer
																									// active
																									// reduce
																									// damage
																									// by
																									// half
					damage = damage * 60 / 100;
					if( c.lastWeaponUsed == 11235 || c.bowSpecShot == 1 ) {
						damage2 = damage2 * 60 / 100;
					}
				}
				if( PlayerHandler.players[i].playerLevel[3] - damage < 0 ) {
					damage = PlayerHandler.players[i].playerLevel[3];
				}
				if( PlayerHandler.players[i].playerLevel[3] - damage - damage2 < 0 ) {
					damage2 = PlayerHandler.players[i].playerLevel[3] - damage;
				}
				if( damage < 0 ) {
					damage = 0;
				}
				if( damage2 < 0 && damage2 != - 1 ) {
					damage2 = 0;
				}
				if( damage > 0 ) {
					c.getCombat().applyRecoil( damage, i );
				}
				if( damage2 > 0 ) {
					c.getCombat().applyRecoil( damage2, i );
				}
				if( c.fightMode == 3 ) {
					c.getPA().addSkillXP( damage * Config.RANGE_EXP_RATE / 3, 4 );
					c.getPA().addSkillXP( damage * Config.RANGE_EXP_RATE / 3, 1 );
					c.getPA().addSkillXP( damage * Config.RANGE_EXP_RATE / 3, 3 );
					c.getPA().refreshSkill( 1 );
					c.getPA().refreshSkill( 3 );
					c.getPA().refreshSkill( 4 );
				} else {
					c.getPA().addSkillXP( damage * Config.RANGE_EXP_RATE, 4 );
					c.getPA().addSkillXP( damage * Config.RANGE_EXP_RATE / 3, 3 );
					c.getPA().refreshSkill( 3 );
					c.getPA().refreshSkill( 4 );
				}
				boolean dropArrows = true;

				for( int noArrowId: c.NO_ARROW_DROP ) {
					if( c.lastWeaponUsed == noArrowId ) {
						dropArrows = false;
						break;
					}
				}
				if( dropArrows ) {
					c.getItems().dropArrowPlayer();
				}
				PlayerHandler.players[i].underAttackBy = c.playerId;
				PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
				PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
				PlayerHandler.players[i].killerId = c.playerId;
				// Server.playerHandler.players[i].setHitDiff(damage);
				// Server.playerHandler.players[i].playerLevel[3] -= damage;
				PlayerHandler.players[i].dealDamage( damage );
				PlayerHandler.players[i].damageTaken[c.playerId] += damage;
				c.killedBy = PlayerHandler.players[i].playerId;
				PlayerHandler.players[i].handleHitMask( damage );
				if( damage2 != - 1 ) {
					// Server.playerHandler.players[i].playerLevel[3] -=
					// damage2;
					PlayerHandler.players[i].dealDamage( damage2 );
					PlayerHandler.players[i].damageTaken[c.playerId] += damage2;
					PlayerHandler.players[i].handleHitMask( damage2 );

				}
				o.getPA().refreshSkill( 3 );

				// Server.playerHandler.players[i].setHitUpdateRequired(true);
				PlayerHandler.players[i].updateRequired = true;
				c.getCombat().applySmite( i, damage );
				if( damage2 != - 1 ) {
					c.getCombat().applySmite( i, damage2 );
				}

			} else if( c.projectileStage > 0 ) { // magic hit damage
				int damage = Misc.random( c.MAGIC_SPELLS[c.oldSpellId][6] );
				if( c.getCombat().godSpells() ) {
					if( System.currentTimeMillis() - c.godSpellDelay < Config.GOD_SPELL_CHARGE ) {
						damage += 10;
					}
				}
				// c.playerIndex = 0;
				if( c.magicFailed ) {
					damage = 0;
				}

				if( o.prayerActive[16] && System.currentTimeMillis() - o.protMageDelay > 1500 ) { // if
																									// prayer
																									// active
																									// reduce
																									// damage
																									// by
																									// half
					damage = damage * 60 / 100;
				}
				if( PlayerHandler.players[i].playerLevel[3] - damage < 0 ) {
					damage = PlayerHandler.players[i].playerLevel[3];
				}
				if( damage > 0 ) {
					c.getCombat().applyRecoil( damage, i );
				}
				c.getPA().addSkillXP( c.MAGIC_SPELLS[c.oldSpellId][7] + damage * Config.MAGIC_EXP_RATE, 6 );
				c.getPA().addSkillXP( c.MAGIC_SPELLS[c.oldSpellId][7] + damage * Config.MAGIC_EXP_RATE / 3, 3 );
				c.getPA().refreshSkill( 3 );
				c.getPA().refreshSkill( 6 );

				if( c.getCombat().getEndGfxHeight() == 100 && ! c.magicFailed ) { // end
																					// GFX
					PlayerHandler.players[i].gfx100( c.MAGIC_SPELLS[c.oldSpellId][5] );
				} else if( ! c.magicFailed ) {
					PlayerHandler.players[i].gfx0( c.MAGIC_SPELLS[c.oldSpellId][5] );
				} else if( c.magicFailed ) {
					PlayerHandler.players[i].gfx100( 85 );
				}

				if( ! c.magicFailed ) {
					if( System.currentTimeMillis() - PlayerHandler.players[i].reduceStat > 35000 ) {
						PlayerHandler.players[i].reduceStat = System.currentTimeMillis();
						switch( c.MAGIC_SPELLS[c.oldSpellId][0] ) {
							case 12987:
							case 13011:
							case 12999:
							case 13023:
								PlayerHandler.players[i].playerLevel[0] -= o.getPA().getLevelForXP(
										PlayerHandler.players[i].playerXP[0] ) * 10 / 100;
								break;
						}
					}

					switch( c.MAGIC_SPELLS[c.oldSpellId][0] ) {
						case 12445: // teleblock
							if( System.currentTimeMillis() - o.teleBlockDelay > o.teleBlockLength ) {
								o.teleBlockDelay = System.currentTimeMillis();
								o.sendMessage( "You have been teleblocked." );
								if( o.prayerActive[16] && System.currentTimeMillis() - o.protMageDelay > 1500 ) {
									o.teleBlockLength = 150000;
								} else {
									o.teleBlockLength = 300000;
								}
							}
							break;

						case 12901:
						case 12919: // blood spells
						case 12911:
						case 12929:
							int heal = damage / 4;
							if( c.playerLevel[3] + heal > c.getPA().getLevelForXP( c.playerXP[3] ) ) {
								c.playerLevel[3] = c.getPA().getLevelForXP( c.playerXP[3] );
							} else {
								c.playerLevel[3] += heal;
							}
							c.getPA().refreshSkill( 3 );
							break;

						case 1153:
							PlayerHandler.players[i].playerLevel[0] -= o.getPA().getLevelForXP(
									PlayerHandler.players[i].playerXP[0] ) * 5 / 100;
							o.sendMessage( "Your attack level has been reduced!" );
							PlayerHandler.players[i].reduceSpellDelay[c.reduceSpellId] = System
									.currentTimeMillis();
							o.getPA().refreshSkill( 0 );
							break;

						case 1157:
							PlayerHandler.players[i].playerLevel[2] -= o.getPA().getLevelForXP(
									PlayerHandler.players[i].playerXP[2] ) * 5 / 100;
							o.sendMessage( "Your strength level has been reduced!" );
							PlayerHandler.players[i].reduceSpellDelay[c.reduceSpellId] = System
									.currentTimeMillis();
							o.getPA().refreshSkill( 2 );
							break;

						case 1161:
							PlayerHandler.players[i].playerLevel[1] -= o.getPA().getLevelForXP(
									PlayerHandler.players[i].playerXP[1] ) * 5 / 100;
							o.sendMessage( "Your defence level has been reduced!" );
							PlayerHandler.players[i].reduceSpellDelay[c.reduceSpellId] = System
									.currentTimeMillis();
							o.getPA().refreshSkill( 1 );
							break;

						case 1542:
							PlayerHandler.players[i].playerLevel[1] -= o.getPA().getLevelForXP(
									PlayerHandler.players[i].playerXP[1] ) * 10 / 100;
							o.sendMessage( "Your defence level has been reduced!" );
							PlayerHandler.players[i].reduceSpellDelay[c.reduceSpellId] = System
									.currentTimeMillis();
							o.getPA().refreshSkill( 1 );
							break;

						case 1543:
							PlayerHandler.players[i].playerLevel[2] -= o.getPA().getLevelForXP(
									PlayerHandler.players[i].playerXP[2] ) * 10 / 100;
							o.sendMessage( "Your strength level has been reduced!" );
							PlayerHandler.players[i].reduceSpellDelay[c.reduceSpellId] = System
									.currentTimeMillis();
							o.getPA().refreshSkill( 2 );
							break;

						case 1562:
							PlayerHandler.players[i].playerLevel[0] -= o.getPA().getLevelForXP(
									PlayerHandler.players[i].playerXP[0] ) * 10 / 100;
							o.sendMessage( "Your attack level has been reduced!" );
							PlayerHandler.players[i].reduceSpellDelay[c.reduceSpellId] = System
									.currentTimeMillis();
							o.getPA().refreshSkill( 0 );
							break;
					}
				}

				PlayerHandler.players[i].logoutDelay = System.currentTimeMillis();
				PlayerHandler.players[i].underAttackBy = c.playerId;
				PlayerHandler.players[i].killerId = c.playerId;
				PlayerHandler.players[i].singleCombatDelay = System.currentTimeMillis();
				if( c.MAGIC_SPELLS[c.oldSpellId][6] != 0 ) {
					// Server.playerHandler.players[i].playerLevel[3] -= damage;
					PlayerHandler.players[i].dealDamage( damage );
					PlayerHandler.players[i].damageTaken[c.playerId] += damage;
					c.totalPlayerDamageDealt += damage;
					if( ! c.magicFailed ) {
						// Server.playerHandler.players[i].setHitDiff(damage);
						// Server.playerHandler.players[i].setHitUpdateRequired(true);
						PlayerHandler.players[i].handleHitMask( damage );
					}
				}
				c.getCombat().applySmite( i, damage );
				c.killedBy = PlayerHandler.players[i].playerId;
				o.getPA().refreshSkill( 3 );
				PlayerHandler.players[i].updateRequired = true;
				c.usingMagic = false;
				c.castingMagic = false;
				if( o.inMulti() && c.getCombat().multis() ) {
					c.barrageCount = 0;
					for( int j = 0; j < PlayerHandler.players.length; j ++ ) {
						if( PlayerHandler.players[j] != null ) {
							if( j == o.playerId ) {
								continue;
							}
							if( c.barrageCount >= 9 ) {
								break;
							}
							if( o.goodDistance( o.getX(), o.getY(), PlayerHandler.players[j].getX(),
									PlayerHandler.players[j].getY(), 1 ) ) {
								c.getCombat().appendMultiBarrage( j, c.magicFailed );
							}
						}
					}
				}
				c.getPA().refreshSkill( 3 );
				c.getPA().refreshSkill( 6 );
				c.oldSpellId = 0;
			}
		}
		c.getPA().requestUpdates();
		if( c.bowSpecShot <= 0 ) {
			c.oldPlayerIndex = 0;
			c.projectileStage = 0;
			c.lastWeaponUsed = 0;
			c.doubleHit = false;
			c.bowSpecShot = 0;
		}
		if( c.bowSpecShot != 0 ) {
			c.bowSpecShot = 0;
		}
	}


	public static int getAttackDelay( Client c, String s )
	{
		if( c.usingMagic ) {
			switch( c.MAGIC_SPELLS[c.spellId][0] ) {
				case 12871: // ice blitz
				case 13023: // shadow barrage
				case 12891: // ice barrage
					return 5;// 5

				default:
					return 5;// 5
			}
		}
		if( c.playerEquipment[c.playerWeapon] == - 1 )
		{
			return 4;// unarmed
		}

		switch( c.playerEquipment[c.playerWeapon] ) {
			case 11235:
				return 9;
			case 11730:
				return 4;
			case 6528:
				return 7;
		}

		if( s.endsWith( "greataxe" ) ) {
			return 7;
		}
		if( s.endsWith( "axe 100" ) ) {
			return 7;
		}
		if( s.endsWith( "axe 75" ) ) {
			return 7;
		}
		if( s.endsWith( "axe 50" ) ) {
			return 7;
		}
		if( s.endsWith( "axe 25" ) ) {
			return 7;
		}
		if( s.endsWith( "axe 0" ) ) {
			return 7;
		} else if( s.equals( "torags hammers" ) ) {
			return 5;
		} else if( s.equals( "guthans warspear" ) ) {
			return 5;
		} else if( s.equals( "veracs flail" ) ) {
			return 5;
		} else if( s.equals( "ahrims staff" ) ) {
			return 6;
		} else if( s.contains( "staff" ) ) {
			if( s.contains( "zamarok" ) || s.contains( "guthix" ) || s.contains( "saradomian" )
					|| s.contains( "slayer" ) || s.contains( "ancient" ) ) {
				return 4;
			} else {
				return 5;
			}
		} else if( s.contains( "bow" ) ) {
			if( s.contains( "composite" ) || s.equals( "seercull" ) ) {
				return 5;
			} else if( s.contains( "aril" ) ) {
				return 4;
			} else if( s.contains( "Ogre" ) ) {
				return 8;
			} else if( s.contains( "short" ) || s.contains( "hunt" ) || s.contains( "sword" ) ) {
				return 4;
			} else if( s.contains( "long" ) || s.contains( "crystal" ) ) {
				return 6;
			} else if( s.contains( "'bow" ) ) {
				return 7;
			}

			return 5;
		}
		else if( s.contains( "dagger" ) ) {
			return 4;
		} else if( s.contains( "godsword" ) || s.contains( "2h" ) ) {
			return 6;
		} else if( s.contains( "longsword" ) ) {
			return 5;
		} else if( s.contains( "sword" ) ) {
			return 4;
		} else if( s.contains( "scimitar" ) ) {
			return 4;
		} else if( s.contains( "mace" ) ) {
			return 5;
		} else if( s.contains( "battleaxe" ) ) {
			return 6;
		} else if( s.contains( "pickaxe" ) ) {
			return 5;
		} else if( s.contains( "thrownaxe" ) ) {
			return 5;
		} else if( s.contains( "axe" ) ) {
			return 5;
		} else if( s.contains( "warhammer" ) ) {
			return 6;
		} else if( s.contains( "2h" ) ) {
			return 7;
		} else if( s.contains( "spear" ) ) {
			return 5;
		} else if( s.contains( "claw" ) ) {
			return 4;
		} else if( s.contains( "halberd" ) ) {
			return 7;
		} else if( s.equals( "granite maul" ) ) {
			return 7;
		} else if( s.equals( "toktz-xil-ak" ) ) {
			return 4;
		} else if( s.equals( "tzhaar-ket-em" ) ) {
			return 5;
		} else if( s.equals( "tzhaar-ket-om" ) ) {
			return 7;
		} else if( s.equals( "toktz-xil-ek" ) ) {
			return 4;
		} else if( s.equals( "toktz-xil-ul" ) ) {
			return 4;
		} else if( s.equals( "toktz-mej-tal" ) ) {
			return 6;
		} else if( s.contains( "whip" ) ) {
			return 4;
		} else if( s.contains( "dart" ) ) {
			return 3;
		} else if( s.contains( "knife" ) ) {
			return 3;
		} else if( s.contains( "javelin" ) ) {
			return 6;
		}
		return 5;
	}


	public static int getHitDelay( Client c, String weaponName )
	{
		if( c.usingMagic ) {
			switch( c.MAGIC_SPELLS[c.spellId][0] ) {
				case 12891:
					return 4;
				case 12871:
					return 6;
				default:
					return 4;
			}
		} else {

			if( weaponName.contains( "knife" ) || weaponName.contains( "dart" ) || weaponName.contains( "javelin" )
					|| weaponName.contains( "thrownaxe" ) ) {
				return 3;
			}
			if( weaponName.contains( "cross" ) || weaponName.contains( "c'bow" ) ) {
				return 4;
			}
			if( weaponName.contains( "bow" ) && ! c.dbowSpec ) {
				return 4;
			} else if( c.dbowSpec ) {
				return 4;
			}

			switch( c.playerEquipment[c.playerWeapon] ) {
				case 6522: // Toktz-xil-ul
					return 3;

				default:
					return 2;
			}
		}
	}

}
