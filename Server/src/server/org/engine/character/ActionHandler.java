package server.org.engine.character;




import server.org.Config;
import server.org.Server;
import server.org.core.util.Misc;
import server.org.core.util.ScriptManager;
import server.org.engine.character.banking.Bank;
import server.org.engine.character.content.LadderHandler;
import server.org.engine.character.content.Sailing;
import server.org.engine.character.content.StairHandler;
import server.org.engine.character.quests.DragonSlayer;
import server.org.engine.character.skilling.Mining;
import server.org.engine.character.skilling.woodcutting.Woodcutting;
import server.org.world.Object;

public class ActionHandler {
	
	private Client c;
	
	public ActionHandler(Client Client) {
		this.c = Client;
	}
	int mad = 0;
	int elvarggate = 0;
	
	public void firstClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		c.turnPlayerTo(obX, obY);
		Misc.println("objectId: "+c.objectId+"  ObjectX: "+c.objectX+ "  objectY: "+c.objectY+" Xoff: "+ (c.getX() - c.objectX)+" Yoff: "+ (c.getY() - c.objectY)+" Distance: "+c.objectDistance); 
		if (Woodcutting.playerTrees(c, objectType)) {
			Woodcutting.attemptData(c, objectType, obX, obY);
			return;
		}
		if (Mining.miningRocks(c, objectType)) {
			Mining.attemptData(c, objectType, obX, obY);
			return;
		}
		if(Mining.miningRocks(c, objectType)) {
			Mining.attemptData(c, objectType, obX, obY);
			return;
		}
		switch(objectType) {
		case 2491:
			Mining.mineEss(c, objectType);
			break;
			
		case 1723:
			c.absY += 4;
			StairHandler.handleStair(objectType, c);
			break;
		case 1722:
			c.absY -= 4;
			StairHandler.handleStair(objectType, c);
			break;
		case 1747:
		case 1746:
		case 1738:
		case 1740:
		case 11727:
		case 11729:
		case 11731:
		case 11728:
			LadderHandler.handleLadder(objectType, c);
			break;
		case 1748:
			LadderHandler.getOption(c);
			break;

		
		case 2412:
			Sailing.startTravel(c, 1);
			break;
		case 2414:
			Sailing.startTravel(c, 2);
			break;
		case 2083:
			Sailing.startTravel(c, 5);
			break;
		case 2081:
			Sailing.startTravel(c, 6);
			break;

		case 2610:
			c.getPA().movePlayer(2833 , 3257, 0);
			break;
		case 1764:
			c.getPA().movePlayer(2858 , 3168, 0);
			break;
		case 492:
			c.getPA().movePlayer(2856,9570, 0);
			break;
			
		case 2606:
			if(c.absX == 2836 && c.absY == 9599 && c.dragonSlayer >= 5){
				c.getPA().movePlayer(2836, 9600, 0);
			}else{
				c.getPA().movePlayer(2836, 9599, 0);
			}
			break;
			
		case 2607:
			if(c.absX == 2846 && c.absY == 9636 && elvarggate == 0 && c.dragonSlayer == 5){
				elvarggate = 1;
				Server.npcHandler.spawnNpc(c, 742,2857,9636,0,1,80,11,198,114, true, true);
				c.getPA().movePlayer(2847, 9636, 0);
			}else{
				c.getPA().movePlayer(2846, 9636, 0);
			}
			break;
		case 2608:
			if(c.absX == 2846 && c.absY == 9637 && elvarggate == 0 && c.dragonSlayer == 5){
				elvarggate = 1;
				Server.npcHandler.spawnNpc(c, 742,2857,9636,0,1,80,11,198,114, true, true);
				c.getPA().movePlayer(2847, 9637, 0);
			}else{
				c.getPA().movePlayer(2846, 9637, 0);
			}
			break;
		
		
		case 2609:
			c.getPA().movePlayer(2834, 9657, 0);
			break;
		
		case 2596:
			if (mad == 0){
				mad = 1;
			Server.npcHandler.spawnNpc(c, 753,2928,3252,0,1,55,12,79,6, true, true);
			}else if(mad == 1){
				c.sendMessage("You have spawned him");
			}
			break;
		
		
		/*
		 * Agility
		 */
		case 2288:
			break;
		case 2309:
			if (c.getX() == 2998 && c.getY() == 3916) {
				c.getAgil().doWildernessEntrance(c);
			}
			break;
		case 2295:
			if (c.getX() == 2474 && c.getY() == 3436) {
				c.getAgil().doGnomeLog(c);
			}
			break;
		case 2285: //NET1
			c.getAgil().doGnomeNet1(c);
				break;
		case 2313: //BRANCH1
			c.getAgil().doGnomeBranch1(c);
				break;
		case 2312: //ROPE
			if (c.getX() == 2477 && c.getY() == 3420) {
				c.getAgil().doGnomeRope(c);
			}
				break;
			case 2314: //BRANCH2
			c.getAgil().doGnomeBranch2(c);
				break;
			case 2286: //NET2
			c.getAgil().doGnomeNet2(c);
				break;
			case 154: //PIPE1
				if (c.getX() ==  2484 && c.getY() == 3430) {
					c.getAgil().doGnomePipe1(c);
				}
				break;
			case 4058: //PIPE2
				if (c.getX() == 2487 && c.getY() == 3430) {
					c.getAgil().doGnomePipe2(c);
				}
				break;
		/*
		 * END OF AGILITY
		 * 
		 * */
		case 9294:
			if (c.objectX == 2879 && c.objectY == 9813) {
			}
			break;
		case 9293:
			if (c.objectX == 2887 && c.objectY == 9799) {
				c.getPA().movePlayer(2892, 9799, 0);
			}
			if (c.objectX == 2890 && c.objectY == 9799) {
				c.getPA().movePlayer(2886, 9799, 0);
			}
			break;
		case 1568:
			if (c.objectX == 3097 && c.objectY == 3468) {
				c.getPA().movePlayer(3117, 9852, 0);
			}
			break;
		case 1755:
			if (c.objectX == 2884 && c.objectY == 9797) {
				c.getPA().movePlayer(2844, 3516, 0);
			}
			if (c.objectX == 3116 && c.objectY == 9852) {
				c.getPA().movePlayer(3096, 3468, 0);
			}
		case 2178:
			if (c.objectX == 2675 && c.objectY == 3170) {
				c.getDH().sendDialogues(79, 0);
			}
			break;
		case 3044:
			
		case 2781:
			c.getSmithing().sendSmelting();
			c.objectDistance = 4;
		break;
		
		case 2:
			c.getPA().movePlayer(3029, 9582, 0);
			break;
			
			/* Bank */
		case 2213:
		case 14367:
				c.getDH().sendDialogues(1000, 494);
			break;

		case 2492:
			if (c.objectX == 2889 && c.objectY == 4813) {
				c.getPA().startTeleport(3252, 3401, 0, "modern");
			}
			break;
		case 9356:
			c.getPA().enterCaves();
			c.sendMessage("Good luck!");
		break;

                case 2557:
                     if(c.getItems().playerHasItem(1523, 1) && c.absX == 3190 && c.absY == 3957) {
                        c.getPA().movePlayer(3190, 3958, 0);
                     } else if(c.getItems().playerHasItem(1523, 1) && c.absX == 3190 && c.absY == 3958) {
                        c.getPA().movePlayer(3190, 3957, 0);
                     }
                break;

                case 2995:
                       c.getPA().startTeleport2(2717, 9801, 0);
                       c.sendMessage("Welcome to the dragon lair, be aware. It's very dangerous.");
                break;

		case 1816:
			c.getPA().startTeleport2(2271, 4680, 0);			
		break;
		case 1817:
			c.getPA().startTeleport(3067, 10253, 0, "modern");
		break;
		case 1814:
			//ardy lever
			c.getPA().startTeleport(3153, 3923, 0, "modern");
		break;

		case 2882:
		case 2883:
			if (c.objectX == 3268) {
				if (c.absX < c.objectX) {
					c.getPA().walkTo(1,0);
				} else {
					c.getPA().walkTo(-1,0);
				}
			}
		break;

		case 1765:
			c.getPA().movePlayer(3067, 10256, 0);
		break;
		case 272:
		c.getPA().movePlayer(c.absX, c.absY, 1);
		break;
		
		case 273:
		c.getPA().movePlayer(c.absX, c.absY, 0);
		break;

		case 245:
		c.getPA().movePlayer(c.absX, c.absY + 2, 2);
		break;

		case 246:
		c.getPA().movePlayer(c.absX, c.absY - 2, 1);
		break;

		case 1766:
		c.getPA().movePlayer(3016, 3849, 0);
		break;

		case 6552:
		if (c.playerMagicBook == 0) {
                        c.playerMagicBook = 1;
                        c.setSidebarInterface(6, 12855);
                        c.autocasting = false;
                        c.sendMessage("An ancient wisdomin fills your mind.");
                        c.getPA().resetAutocast();
		} else {
			c.setSidebarInterface(6, 1151); //modern
			c.playerMagicBook = 0;
                        c.autocasting = false;
			c.sendMessage("You feel a drain on your memory.");
			c.autocastId = -1;
			c.getPA().resetAutocast();
		}	
		break;
		

		case 1733:
			c.getPA().movePlayer(c.absX, c.absY + 6393, 0);
		break;
		
		case 1734:
			c.getPA().movePlayer(c.absX, c.absY - 6396, 0);
		break;
		
		case 9357:
			c.getPA().resetTzhaar();
		break;
		
		case 8959:
			if (c.getX() == 2490 && (c.getY() == 10146 || c.getY() == 10148)) {
				if (c.getPA().checkForPlayer(2490, c.getY() == 10146 ? 10148 : 10146)) {
					new Object(6951, c.objectX, c.objectY, c.heightLevel, 1, 10, 8959, 15);	
				}			
			}
		break;

		
		case 10177:
			c.getPA().movePlayer(1890, 4407, 0);
		break;
		case 10230:
			c.getPA().movePlayer(2900, 4449, 0);
		break;
		case 10229:
			c.getPA().movePlayer(1912, 4367, 0);
		break;

		case 2623:
			if (c.absX >= c.objectX)
				c.getPA().walkTo(-1,0);
			else
				c.getPA().walkTo(-1,0);
		break;
		//pc boat
		case 14315:
			c.getPA().movePlayer(2661,2639,0);
		break;
		case 14314:
			c.getPA().movePlayer(2657,2639,0);
		break;
		
		
		
		case 14235:
		case 14233:
			if (c.objectX == 2670)
				if (c.absX <= 2670)
					c.absX = 2671;
				else
					c.absX = 2670;
			if (c.objectX == 2643)
				if (c.absX >= 2643)
					c.absX = 2642;
				else
					c.absX = 2643;
			if (c.absX <= 2585)
				c.absY += 1;
			else c.absY -= 1;
			c.getPA().movePlayer(c.absX, c.absY, 0);
		break;
		
		case 14829: case 14830: case 14827: case 14828: case 14826: case 14831:
			//Server.objectHandler.startObelisk(objectType);
			Server.objectManager.startObelisk(objectType);
		break;
		
		case 9369:
			if (c.getY() > 5175)
				c.getPA().movePlayer(2399, 5175, 0);
			else
				c.getPA().movePlayer(2399, 5177, 0);
		break;
		
		//barrows
		//Chest
		case 10284:
			//c.shakeScreen(3, 2, 3, 2);
			if(c.barrowsKillCount < 5) {
				c.sendMessage("You must kill all the brothers to receive a reward!");
			}
			if(c.barrowsKillCount == 5 && c.barrowsNpcs[c.randomCoffin][1] == 1) {
				c.sendMessage("I have already awakened this brother.");
			}
			if(c.barrowsNpcs[c.randomCoffin][1] == 0 && c.barrowsKillCount >= 5) {
				Server.npcHandler.spawnNpc(c, c.barrowsNpcs[c.randomCoffin][0], 3551, 9694-1, 0, 0, 120, 30, 200, 200, true, true);
				c.barrowsNpcs[c.randomCoffin][1] = 1;
			}
			if((c.barrowsKillCount > 5 || c.barrowsNpcs[c.randomCoffin][1] == 2) && c.getItems().freeSlots() >= 2) {
				c.resetShaking();
				c.getPA().resetBarrows();
				c.getItems().addItem(c.getPA().randomRunes(), Misc.random(150) + 100);
				if (Misc.random(2) == 1)
					c.getItems().addItem(c.getPA().randomBarrows(), 1);
				c.getPA().startTeleport(3564, 3288, 0, "modern");
			} else if(c.barrowsKillCount > 5 && c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need two empty slots in your inventory to receive the reward.");
			}
			break;
		//doors
		case 6749:
			if(obX == 3562 && obY == 9678) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if(obX == 3558 && obY == 9677) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;
		case 6730:
			if(obX == 3558 && obY == 9677) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if(obX == 3558 && obY == 9678) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;
		case 6727:
			if(obX == 3551 && obY == 9684) {
				c.sendMessage("You can't open this door...");
			}
			break;
		case 6746:
			if(obX == 3552 && obY == 9684) {
				c.sendMessage("You can't open this door...");
			}
			break;
		case 6748:
			if(obX == 3545 && obY == 9678) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if(obX == 3541 && obY == 9677) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;
		case 6729:
			if(obX == 3545 && obY == 9677){
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if(obX == 3541 && obY == 9678) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;
		case 6726:
			if(obX == 3534 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if(obX == 3535 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;
		case 6745:
			if(obX == 3535 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if(obX == 3534 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;
		case 6743:
			if(obX == 3545 && obY == 9695) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if(obX == 3541 && obY == 9694) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;
		case 6724:
			if(obX == 3545 && obY == 9694) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if(obX == 3541 && obY == 9695) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break; 

		case 6707: // verac
			c.getPA().movePlayer(3556, 3298, 0);
			break;
			
		case 6823:
			if(server.org.engine.minigame.barrows.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[0][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2030, c.getX(), c.getY()-1, -1, 0, 120, 25, 200, 200, true, true);
				c.barrowsNpcs[0][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6706: // torag 
			c.getPA().movePlayer(3553, 3283, 0);
			break;
			
		case 6772:
			if(server.org.engine.minigame.barrows.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[1][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2029, c.getX()+1, c.getY(), -1, 0, 120, 20, 200, 200, true, true);
				c.barrowsNpcs[1][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
			
		case 6705: // karil stairs
			c.getPA().movePlayer(3565, 3276, 0);
			break;
		case 6822:
			if(server.org.engine.minigame.barrows.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[2][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2028, c.getX(), c.getY()-1, -1, 0, 90, 17, 200, 200, true, true);
				c.barrowsNpcs[2][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
		case 6704: // guthan stairs
			c.getPA().movePlayer(3578, 3284, 0);
			break;
		case 6773:
			if(server.org.engine.minigame.barrows.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[3][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2027, c.getX(), c.getY()-1, -1, 0, 120, 23, 200, 200, true, true);
				c.barrowsNpcs[3][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
		case 6703: // dharok stairs
			c.getPA().movePlayer(3574, 3298, 0);
			break;
		case 6771:
			if(server.org.engine.minigame.barrows.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[4][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2026, c.getX(), c.getY()-1, -1, 0, 120, 45, 250, 250, true, true);
				c.barrowsNpcs[4][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
		case 6702: // ahrim stairs
			c.getPA().movePlayer(3565, 3290, 0);
			break;
		case 6821:
			if(server.org.engine.minigame.barrows.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[5][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2025, c.getX(), c.getY()-1, -1, 0, 90, 19, 200, 200, true, true);
				c.barrowsNpcs[5][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
		
		
		case 8143:
			if (c.farm[0] > 0 && c.farm[1] > 0) {
				c.getFarming().pickHerb();
			}
		break;
	
			// DOORS
		case 1516:
		case 1519:
			if (c.objectY == 9698) {
				if (c.absY >= c.objectY)
					c.getPA().walkTo(0,-1);
				else
					c.getPA().walkTo(0,1);
				break;
			}
		case 1530:
		case 1531:
		case 1533:
		case 1534:
		case 11712:
		case 11711:
		case 11707:
		case 11708:
		case 6725:
		case 3198:
		case 3197:
			Server.objectHandler.doorHandling(objectType, c.objectX, c.objectY, 0);	
			break;
		
		case 9319:
			if (c.heightLevel == 0)
				c.getPA().movePlayer(c.absX, c.absY, 1);
			else if (c.heightLevel == 1)
				c.getPA().movePlayer(c.absX, c.absY, 2);
		break;
		
		case 9320:
			if (c.heightLevel == 1)
				c.getPA().movePlayer(c.absX, c.absY, 0);
			else if (c.heightLevel == 2)
				c.getPA().movePlayer(c.absX, c.absY, 1);
		break;
		
		case 4496:
		case 4494:
			if (c.heightLevel == 2) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 0);
			}
		break;
		
		case 4493:
			if (c.heightLevel == 0) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
		break;
		
		case 4495:
			if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
		break;
		
		case 5126:
			if (c.absY == 3554)
				c.getPA().walkTo(0,1);
			else
				c.getPA().walkTo(0,-1);
		break;
		
		case 1759:
			if (c.objectX == 2884 && c.objectY == 3397) {
				c.getPA().movePlayer(c.absX, c.absY + 6400, 0);
			} else if (c.objectX == 2845 && c.objectY == 3516) {
				c.getPA().movePlayer(2884, 9798, 0);
			} else if (c.objectX == 2848 && c.objectY == 3513) {
				c.getPA().movePlayer(2884, 9798, 0);
			} else if (c.objectX == 2848 && c.objectY == 3519) {
				c.getPA().movePlayer(2884, 9798, 0);
			}
		break;
		case 1558:
			if (c.absX == 3041 && c.absY == 10308) {
                            c.getPA().movePlayer(3040, 10308, 0);	
                        } else if(c.absX == 3040 && c.absY == 10308) {
                                  c.getPA().movePlayer(3041, 10308, 0);
                        } else if(c.absX == 3040 && c.absY == 10307) {
                                  c.getPA().movePlayer(3041, 10307, 0);
                        } else if(c.absX == 3041 && c.absY == 10307) {
                                  c.getPA().movePlayer(3040, 10307, 0);
                        } else if(c.absX == 3044 && c.absY == 10341) {
                                  c.getPA().movePlayer(3045, 10341, 0);
                        } else if(c.absX == 3045 && c.absY == 10341) {
                                  c.getPA().movePlayer(3044, 10341, 0);
                        } else if(c.absX == 3044 && c.absY == 10342) {
                                  c.getPA().movePlayer(3045, 10342, 0);
                        } else if(c.absX == 3045 && c.absY == 10342) {
                                  c.getPA().movePlayer(3044, 10343, 0);
                        }
		break;
		case 1557:
			if (c.absX == 3023 && c.absY == 10312) {
                            c.getPA().movePlayer(3022, 10312, 0);	
                        } else if(c.absX == 3022 && c.absY == 10312) {
                                  c.getPA().movePlayer(3023, 10312, 0);
                        } else if(c.absX == 3023 && c.absY == 10311) {
                                  c.getPA().movePlayer(3022, 10311, 0);
                        } else if(c.absX == 3022 && c.absY == 10311) {
                                  c.getPA().movePlayer(3023, 10311, 0);
                        }
		break;
 		case 3203: //dueling forfeit
			if (c.duelCount > 0) {
				c.sendMessage("You may not forfeit yet.");
				break;
			}
			Client o = (Client) Server.playerHandler.players[c.duelingWith];				
			if(o == null) {
				c.getTradeAndDuel().resetDuel();
				c.getPA().movePlayer(Config.DUELING_RESPAWN_X+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), Config.DUELING_RESPAWN_Y+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), 0);
				break;
			}
			if(c.duelRule[0]) {
				c.sendMessage("Forfeiting the duel has been disabled!");
				break;
			}
			{
				o.getPA().movePlayer(Config.DUELING_RESPAWN_X+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), Config.DUELING_RESPAWN_Y+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), 0);
				c.getPA().movePlayer(Config.DUELING_RESPAWN_X+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), Config.DUELING_RESPAWN_Y+(Misc.random(Config.RANDOM_DUELING_RESPAWN)), 0);
				o.duelStatus = 6;
				o.getTradeAndDuel().duelVictory();
				c.getTradeAndDuel().resetDuel();
				c.getTradeAndDuel().resetDuelItems();
				o.sendMessage("The other player has forfeited the duel!");
				c.sendMessage("You forfeit the duel!");
				break;
			}
			
		case 409:
			if(c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
				c.startAnimation(645);
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.sendMessage("You recharge your prayer points.");
				c.getPA().refreshSkill(5);
			} else {
				c.sendMessage("You already have full prayer points.");
			}
			break;
		case 2873:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Saradomin blesses you with a cape.");
				c.getItems().addItem(2412, 1);
			}	
		break;
		case 2875:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Guthix blesses you with a cape.");
				c.getItems().addItem(2413, 1);
			}
		break;
		case 2874:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Zamorak blesses you with a cape.");
				c.getItems().addItem(2414, 1);
			}
		break;
		
		default:
			ScriptManager.callFunc("objectClick1_"+objectType, c, objectType, obX, obY);
			break;

		}
	}
	public Bank b;
	
	
	public void secondClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;

		switch(objectType) {
		case 2213:
		case 11758:
		case 14367:
			Bank.openUpBank(c);
			break;
		case 11666:
		case 3044:
		case 2781:
			c.getSmithing().sendSmelting();
		break;

			case 2558:
				if (System.currentTimeMillis() - c.lastLockPick < 3000 || c.freezeTimer > 0)
					break;
				if (c.getItems().playerHasItem(1523,1)) {
						c.lastLockPick = System.currentTimeMillis();
						if (Misc.random(10) <= 3){
							c.sendMessage("You fail to pick the lock.");
							break;
						}
					if (c.objectX == 3044 && c.objectY == 3956) {
						if (c.absX == 3045) {
							c.getPA().walkTo2(-1,0);
						} else if (c.absX == 3044) {
							c.getPA().walkTo2(1,0);
						}
					
					} else if (c.objectX == 3038 && c.objectY == 3956) {
						if (c.absX == 3037) {
							c.getPA().walkTo2(1,0);
						} else if (c.absX == 3038) {
							c.getPA().walkTo2(-1,0);
						}				
					} else if (c.objectX == 3041 && c.objectY == 3959) {
						if (c.absY == 3960) {
							c.getPA().walkTo2(0,-1);
						} else if (c.absY == 3959) {
							c.getPA().walkTo2(0,1);
						}					
					}
				} else {
					c.sendMessage("I need a lockpick to pick this lock.");
				}
			break;
		default:
			ScriptManager.callFunc("objectClick2_"+objectType, c, objectType, obX, obY);
			break;
		}
	}
	
	
	public void thirdClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		c.sendMessage("Object type: " + objectType);
		switch(objectType) {
		//In here
		default:
			ScriptManager.callFunc("objectClick3_"+objectType, c, objectType, obX, obY);
			break;
		}
	}
	
	public void firstClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		c.fishitem = -1;
		if (c.fishitem != -1) {
            if (!c.getItems().playerHasItem(c.fishitem)) {
                c.sendMessage("You need a " + c.getItems().getItemName(c.fishitem) + " to fish for " + c.getItems().getItemName(c.fishies));
                c.fishing = false;
                return;
            }
            if (c.getItems().freeSlots() == 0) {
                c.sendMessage("Your inventory is full.");
                c.fishing = false;
                return;
            }
            if (c.playerFishing < c.fishreqt) {
                c.sendMessage("You need a fishing level of " + c.fishreqt + " to fish here.");
                c.fishing = false;
                return;
            }
            //c.fishtimer = c.getFishing().fishtime(c.fishies, c.fishreqt);
        }
		switch(npcType) {
		case 741:
			c.getDH().sendDialogues(300, npcType);
			break;
		case 284:
			if(c.dorics >= 2){
				c.getDH().sendDialogues(511, npcType);
			}else{
			c.getDH().sendDialogues(500, npcType);
			}
			break;
		case 1787:
			c.getDH().sendDialogues(5000, npcType);
			break;
		
		case 278:
			if(c.cookAss == 0) {
				c.getDH().sendDialogues(600, 278);
			} else if(c.cookAss == 1) {
				c.getDH().sendDialogues(616, 278);
			} else if(c.cookAss == 2) {
				c.getDH().sendDialogues(619, 278);
			} else if(c.cookAss == 3) {
				c.getDH().sendDialogues(624, 278);
			}
			break;
		case 918:
			c.getDH().sendDialogues(81, 918);
			break;
		case 744:
			c.getDH().sendDialogues(74, 744);
			break;
		case 746:
			if(c.dragonSlayer == 2){
				c.sendMessage("Oracle gives you a map peice.");
			c.getItems().addItem(1536, 1);
			}
			break;
		case 198:
			if(c.dragonSlayer == 1){
			c.getDH().sendDialogues(61, 198);
			}else if (c.dragonSlayer == 2){
				c.getDH().sendDialogues(68, 198);
			}
			break;
		case 747:
			c.getDH().sendDialogues(50, 747);
			break;
		case 484:
			c.getDH().sendDialogues(88, 484);
			break;
		/*Quests*/
		case 209:
			c.getDH().sendDialogues(86, 209);
			break;
		case 1917:
			c.getDH().sendDialogues(84, 1917);
			break;
		case 2201:
			c.getDH().sendDialogues(83, 2201);
			break;
		case 462:
			c.getDH().sendDialogues(17, 462);
			break;
		case 1696:
			c.getDH().sendDialogues(81, 1696);
			break;
		case 559:
			c.getDH().sendDialogues(999, 559);
			break;
		case 291:
			c.getDH().sendDialogues(77, 291);
			break;
		case 545:
			c.getDH().sendDialogues(999, 545);
			break;
		case 1658:
			c.getDH().sendDialogues(999, 1658);
			break;
		case 316:
            c.fishing = true;
            c.fishXP = 10 * Config.FISHING_EXPERIENCE;
            c.fishies = 317;
            c.fishreqt = 0;
            c.fishitem = 303;
            c.fishemote = 621;
            //c.fishies2 = 321;
            //c.fishreq2 = 5;
        break;
        case 334:
            c.fishing = true;
            c.fishXP = 317 * Config.FISHING_EXPERIENCE; // manta
            c.fishies = 389;
            c.fishreqt = 81;
            c.fishitem = 303;
            c.fishemote = 621;
        break;
        case 324://cage-harpoon spot choice cage
            c.fishing = true;
            c.fishXP = 90 * Config.FISHING_EXPERIENCE;
            c.fishies = 377;
            c.fishreqt = 40;
            c.fishitem = 301;
            c.fishemote = 619;
           //c.fishies2 = 389;
           //c.fishreq2 = 81;
        break;
        case 326:
            c.fishing = true;
            c.fishitem = 303;
            c.fishemote = 621;
            c.fishies = 363;
           	c.fishreqt = 46;
           	c.fishXP = 120 * Config.FISHING_EXPERIENCE;
            //c.fishies2 = 7944;
            //c.fishreq2 = 62;
            break;
        case 313:
            c.fishing = true;
            c.fishXP = 45 * Config.FISHING_EXPERIENCE;
            c.fishies = 341;
            c.fishreqt = 23;
            c.fishitem = 303;
            c.fishemote = 621;
            //c.fishies2 = 363;
            //c.fishreq2 = 46;
        break;
        case 327:
        	c.fishing = true;
            c.fishXP = 45 * Config.FISHING_EXPERIENCE;
            c.fishies = 7944;
            c.fishreqt = 62;
            c.fishitem = 303;
            c.fishemote = 621;
            break;
		case 455:
			c.getDH().sendDialogues(73, 455);
			break;
		case 692:
			c.getDH().sendDialogues(75, 692);
			break;
		case 706:
			c.getDH().sendDialogues(70, 706);
			break;
		case 308:
			c.getDH().sendDialogues(66, 308);
			break;
		case 599:
			c.getDH().sendDialogues(63, 599);
			break;
		case 802:
			c.getDH().sendDialogues(60, 802);
			break;
		case 1304:
			c.getDH().sendDialogues(58, 1304);
			break;
		case 201:
			c.getDH().sendDialogues(9001, 201);
			break;
		case 494:
		case 495:
		case 496:
		case 497:
			c.getDH().sendDialogues(1000, 494);
		break;
			case 1598:
				if (c.slayerTask <= 0) {
					c.getDH().sendDialogues(11,npcType);
				} else {
					c.getDH().sendDialogues(13,npcType);
				}
			break;

			case 1526:
			c.getDH().sendDialogues(55,npcType);
			break;


			//case 212:
			case 589:
			c.getDH().sendDialogues(56,npcType);
			break;

			case 1152:
				c.getDH().sendDialogues(16,npcType);
			break;

			case 905:
				c.getDH().sendDialogues(5, npcType);
			break;
			case 460:
				c.getDH().sendDialogues(3, npcType);
			break;

			case 904:
				c.sendMessage("You have " + c.magePoints + " points.");
			break;
			
			case 520: case 521: case 550: case 595: case 561: case 531:
			c.getDH().sendDialogues(999, npcType);
			break;
			
			case 812:
			c.getDH().sendDialogues(998, npcType);
			break;
			
			case 546:
				c.getDH().sendDialogues(1004, npcType);
			break;
			case 548:
				c.getDH().sendDialogues(1008, npcType);
				break;
			case 641:
				c.getDH().sendDialogues(997, npcType);
			break;
			case 530:
				c.getDH().sendDialogues(996, npcType);
			break;
		default:
		//c.getDH().sendDialogues(144, npcType);
			c.getDH().sendDialogues(2000, npcType);
			ScriptManager.callFunc("npcClick1_"+npcType, c, npcType);
			if(c.playerRights == 3) 
				Misc.println("First Click Npc : "+npcType);
			break;
		}
	}

	public void secondClickNpc(int npcType) {
		c.fishitem = -1;
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		if (c.fishitem != -1) {
                    if (!c.getItems().playerHasItem(c.fishitem)) {
                        c.sendMessage("You need a " + c.getItems().getItemName(c.fishitem) + " to fish for " + c.getItems().getItemName(c.fishies));
                        c.fishing = false;
                        return;
                    }
                    if (c.getItems().freeSlots() == 0) {
                       c. sendMessage("Your inventory is full.");
                        c.fishing = false;
                        return;
                    }
                    if (c.playerFishing < c.fishreqt) {
                        c.sendMessage("You need a fishing level of " + c.fishreqt + " to fish here.");
                        c.fishing = false;
                        return;
                    }
                    //c.fishtimer = c.getFishing().fishtime(c.fishies, c.fishreqt);
                }
		switch(npcType) {
			
		case 747:
			c.getShops().openShop(107);
			break;
		
		case 333:
            c.fishing = true;
            c.fishXP = 100 * Config.FISHING_EXPERIENCE;
            c.fishies = 359;
            c.fishreqt = 35;
            c.fishitem = 311;
            c.fishemote = 618;
            //c.fishies2 = 371;
            //c.fishreq2 = 50;
			break;
	
        case 312:
            c.fishing = true;
            c.fishXP = 100  * Config.FISHING_EXPERIENCE;
            c.fishies = 359;
            c.fishreqt = 35;
            c.fishitem = 311;
            c.fishemote = 618;
            //c.fishies2 = 371;
           // c.fishreq2 = 50;
			break;
        case 324:
            c.fishing = true;
            c.fishXP = 100  * Config.FISHING_EXPERIENCE;
            c.fishies = 359;
            c.fishreqt = 35;
            c.fishitem = 311;
            c.fishemote = 618;
            //c.fishies2 = 371;
           // c.fishreq2 = 50;
            break;
        case 334:
            c.fishing = true;
            c.fishXP = 110 * Config.FISHING_EXPERIENCE;
            c.fishies = 383;
            c.fishreqt = 76;
            c.fishitem = 311;
            c.fishemote = 618;
            break;
        case 316:
            c.fishing = true;
            c.fishXP = 30 * Config.FISHING_EXPERIENCE;
			c.fishies = 327;
			c.fishreqt = 5;
            c.fishitem = 307;
            c.fishemote = 622;
            //c.fishies2 = 345;
           // c.fishreq2 = 10;
			break;
        case 326:
            c.fishing = true;
            c.fishXP = 30 * Config.FISHING_EXPERIENCE;
			c.fishies = 327;
			c.fishreqt = 5;
            c.fishitem = 307;
            c.fishemote = 622;
            //c.fishies2 = 345;
            //c.fishreq2 = 10;
			break;
       case 331:
            c.fishing = true;
            c.fishXP = 60 * Config.FISHING_EXPERIENCE;
            c.fishies = 349;
            c.fishreqt = 25;
            c.fishitem = 307;
            c.fishemote = 622;
			break;
       case 313:
            c.fishing = true;
            c.fishXP = 110 * Config.FISHING_EXPERIENCE;
            c.fishies = 383;
            c.fishreqt = 76;
            c.fishitem = 311;
            c.fishemote = 618;
        break;

			
			case 588:
				c.getShops().openShop(2);
				break;

				case 550:
				c.getShops().openShop(3);
				break;

				case 575:
				c.getShops().openShop(4);
				break;

				case 2356:
				c.getShops().openShop(5);
				break;

				case 3796:
				c.getShops().openShop(6);
				break;

				case 1860:
				c.getShops().openShop(7);
				break;

				case 519:
				c.getShops().openShop(8); //should sell barrows or something like that
				break;

				case 559:
				c.getShops().openShop(9);
				break;

				case 562:
				c.getShops().openShop(10);
				break;

				case 581:
				c.getShops().openShop(11);
				break;

				case 548:
				c.getShops().openShop(12);
				break;

				case 554:
				c.getShops().openShop(13);
				break;

				case 601:
				c.getShops().openShop(14);
				break;

				case 1301:
				c.getShops().openShop(15);
				break;

				case 1039:
				c.getShops().openShop(16);
				break;

				case 2353:
				c.getShops().openShop(17);
				break;

				case 3166:
				c.getShops().openShop(18);
				break;

				case 2161:
				c.getShops().openShop(19);
				break;

				case 2162:
				c.getShops().openShop(20);
				break;

				case 600:
				c.getShops().openShop(21);
				break;

				case 603:
				c.getShops().openShop(22);
				break;

				case 593:
				c.getShops().openShop(23);
				break;

				case 545:
				c.getShops().openShop(24);
				break;

				case 585:
				c.getShops().openShop(25);
				break;

				case 2305:
				c.getShops().openShop(26);
				break;

				case 2307:
				c.getShops().openShop(27);
				break;

				case 2304:
				c.getShops().openShop(28);
				break;

				case 2306:
				c.getShops().openShop(29);
				break;

				case 517:
				c.getShops().openShop(30);
				break;

				case 558:
				c.getShops().openShop(31);
				break;

				case 576:
				c.getShops().openShop(32);
				break;

				case 1369:
				c.getShops().openShop(33);
				break;

				case 557:
				c.getShops().openShop(34);
				break;

				case 1038:
				c.getShops().openShop(35);
				break;

				case 1433:
				c.getShops().openShop(36);
				break;

				case 584:
				c.getShops().openShop(37);
				break;

				case 540:
				c.getShops().openShop(38);
				break;

				case 2157:
				c.getShops().openShop(39);
				break;

				case 538:
				c.getShops().openShop(40);
				break;

				case 1303:
				c.getShops().openShop(41);
				break;

				case 578:
				c.getShops().openShop(42);
				break;

				case 587:
				c.getShops().openShop(43);
				break;

				case 1398:
				c.getShops().openShop(44);
				break;

				case 556:
				c.getShops().openShop(45);
				break;

				case 1865:
				c.getShops().openShop(46);
				break;

				case 543:
				c.getShops().openShop(47);
				break;

				case 2198:
				c.getShops().openShop(48);
				break;

				case 580:
				c.getShops().openShop(49);
				break;

				case 1862:
				c.getShops().openShop(50);
				break;

				case 583:
				c.getShops().openShop(51);
				break;

				case 553:
				c.getShops().openShop(52);
				break;
	                              
	                        case 461:
				c.getShops().openShop(53);
				break;


				case 903:
				c.getShops().openShop(54);
				break;

				case 2258:
				c.getPA().startTeleport(3039, 4834, 0, "modern"); //first click teleports second click open shops
				break;

				case 1435:
				c.getShops().openShop(56);
				break;

				case 3800:
				c.getShops().openShop(57);
				break;

				case 2623:
				c.getShops().openShop(58);
				break;

				case 594:
				c.getShops().openShop(59);
				break;

				case 579:
				c.getShops().openShop(60);
				break;

				case 2160:
				case 2191:
				c.getShops().openShop(61);
				break;

				case 589:
				c.getShops().openShop(62);
				break;

				case 549:
				c.getShops().openShop(63);
				break;

				case 542:
				c.getShops().openShop(64);
				break;

				case 3038:
				c.getShops().openShop(65);
				break;

				case 544:
				c.getShops().openShop(66);
				break;

				case 541:
				c.getShops().openShop(67);
				break;

				case 1434:
				c.getShops().openShop(68);
				break;

				case 577:
				c.getShops().openShop(69);
				break;

				case 539:
				c.getShops().openShop(70);
				break;

				case 1980:
				c.getShops().openShop(71);
				break;

				case 546:
				c.getShops().openShop(72);
				break;

				case 382:
				c.getShops().openShop(73);
				break;

				case 3541:
				c.getShops().openShop(74);
				break;

				case 520:
				c.getShops().openShop(75);
				break;

				case 1436:
				c.getShops().openShop(76);
				break;

				case 590:
				c.getShops().openShop(77);
				break;

				case 971:
				c.getShops().openShop(78);
				break;

				case 1917:
				c.getShops().openShop(79);
				break;

				case 1040:
				c.getShops().openShop(80);
				break;

				case 563:
				c.getShops().openShop(81);
				break;

				case 522:
				c.getShops().openShop(82);
				break;

				case 524:
				c.getShops().openShop(83);
				break;

				case 526:
				c.getShops().openShop(84);
				break;

				case 2154:
				c.getShops().openShop(85);
				break;

				case 1334:
				c.getShops().openShop(86);
				break;

				case 2552:
				c.getShops().openShop(87);
				break;

				case 528:
				c.getShops().openShop(88);
				break;

				case 1254:
				c.getShops().openShop(89);
				break;

				case 2086:
				c.getShops().openShop(90);
				break;

				case 3824:
				c.getShops().openShop(91);
				break;

				case 1866:
				c.getShops().openShop(92);
				break;

				case 1699:
				c.getShops().openShop(93);
				break;

				case 1282:
				c.getShops().openShop(94);
				break;

				case 530:
				c.getShops().openShop(95);
				break;

				case 516:
				c.getShops().openShop(96);
				break;

				case 560:
				c.getShops().openShop(97);
				break;

				case 471:
				c.getShops().openShop(98);
				break;

				case 1208:
				c.getShops().openShop(99);
				break;

				case 532:
				c.getShops().openShop(100);
				break;

				case 606:
				c.getShops().openShop(101);
				break;

				case 534:
				c.getShops().openShop(102);
				break;

				case 836:
				c.getShops().openShop(103);
				break;
				case 551:
				c.getShops().openShop(104);
				break;
				case 586:
				c.getShops().openShop(105);
				break;
				case 564:
				c.getShops().openShop(106);
				break;
				case 573:
				case 1316:
				case 547:
				c.getShops().openShop(108);
				break;

				case 1787:
				c.getShops().openShop(110);
				break;
			
			
			/* - - Shops - - */
			/* General Store / Assistant Varrock */
			case 494: 
			case 495: 
				case 496: 
					case 497: 
						case 498: 
							case 499:
			c.getPA().openUpBank();
			break;
			default:
			//c.getDH().sendDialogues(144, npcType);
				ScriptManager.callFunc("npcClick2_"+npcType, c, npcType);
				if(c.playerRights == 3) 
					Misc.println("Second Click Npc : "+npcType);
				break;
			
		}
	}
	
	public void thirdClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		switch(npcType) {
		case 70:
		case 1596:
		case 1597:
		case 1598:
		case 1599:
		c.getShops().openShop(109);
		break;

case 836:
		c.getShops().openShop(103);
		break;
			case 553:
			c.getPA().startTeleport2(2898, 4819, 0);
			break;
			default:
			//c.getDH().sendDialogues(144, npcType);
				ScriptManager.callFunc("npcClick3_"+npcType, c, npcType);
				if(c.playerRights == 3) 
					Misc.println("Third Click NPC : "+npcType);
				break;

		}
	}
	

}