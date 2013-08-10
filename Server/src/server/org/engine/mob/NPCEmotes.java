package server.org.engine.mob;

import server.org.Server;

public class NPCEmotes {
	public static int maxNPCs = 10000;
	public static int maxListedNPCs = 10000;
	public static int maxNPCDrops = 10000;
	public static NPC npcs[] = new NPC[maxNPCs];
	public static NPCList NpcList[] = new NPCList[maxListedNPCs];
	
	

	
	public static int getAttackEmote(int i) {
		switch(Server.npcHandler.npcs[i].npcType) {
		case 89:
			return 289;
		case 438:
		case 439:
		case 440:
		case 441:
		case 442:
		case 443:
			return 94;
		
		case 742:
			if (npcs[i].attackType == 3)
				return 81;
			if (npcs[i].attackType == 0)
				return 80;
		
		/*case 742:
			if(npcs[i].attackType == 3){
				c.gfx100(0);
				return 81;
			}else if(npcs[i].attackType == 0){
				return 80;
			}*/

			
		case 86:
			return 138;
		case 49:
			return 158;
		case 1618:
			return 1551;
		case 1612:
			return 1523;
		case 73:
			return 299;
		case 90:
			return 260;
		case 2607:
			return 2610;
		case 97:
		case 141:
		case 1558:
		case 96:
			return 75;
		case 1459:
			return 1402;
			
		case 72:
		case 396:
			if (npcs[i].attackType == 1)
				return 285;
			if (npcs[i].attackType == 0)
				return 284;
			
		case 795:
			if (npcs[i].attackType == 1)
				return 1979;
			if (npcs[i].attackType == 0)
				return 422;
		case 115:
			return 359;
			case 3066:
				return 1658;
			case 13: //wizards
			return 711;
			
			case 103:
			case 655:
			return 123;
			
			case 1624:
			return 1557;
			
			case 1648:
			return 1590;
			
			case 2783: //dark beast
			return 2733;
			
			case 1615: //abby demon
			return 1537;
			
			case 1613: //nech
			return 1528;
			
			case 1610: case 1611: //garg
			return 1519;
			
			case 1616: //basilisk
			return 1546;
			
			case 50://drags
			case 53:
			case 54:
			case 55:
			case 941:

			case 1590:
			case 1591:
			case 1592:
			return 80;
			
			case 124: //earth warrior
			return 390;
			
			case 803: //monk
			return 422;
			
			case 52: //baby drag
			return 25;			

			case 58: //Shadow Spider
            case 59: //Giant Spider
            case 60: //Giant Spider
            case 61: //Spider
            case 62: //Jungle Spider
            case 63: //Deadly Red Spider
            case 64: //Ice Spider
            case 134:
			return 143;	
			
			case 105: //Bear
            case 106:  //Bear
			return 41;
			
			case 412:
			case 78:
			return 30;
			
			case 2033: //rat
			return 138;	
			
			case 2031: // bloodworm
			return 2070;
			
			case 101: // goblin
			case 1769:
			case 1770:
			case 1771:
			case 1772:
			case 1773:
			case 1774:
			case 1775:
			case 1776:
			return 309;	
			
			case 81: // cow
			return 0x03B;
			
			case 21: // hero
			return 451;	
			
			case 41: // chicken
			return 55;	
			
			case 9: // guard
			case 32: // guard
			case 20: // paladin
			return 451;	
			
			case 1338: // dagannoth
			case 1340:
			case 1342:
			return 1341;
		
			case 19: // white knight
			return 406;
			
			case 110:
			case 111: // ice giant
			case 112:
			case 117:
			return 128;
			
			case 2452:
			return 1312;
			
			case 2889:
			return 2859;
			
			case 118:
			case 119:
			return 99;
			
			case 82://Lesser Demon
            case 83://Greater Demon
            case 84://Black Demon
            case 1472://jungle demon
			return 64;
			
			case 1267:
			case 1265:
			return 1312;
			
			case 125: // ice warrior
			case 178:
			return 451;
			
			case 1153: //Kalphite Worker
            case 1154: //Kalphite Soldier
            case 1155: //Kalphite guardian
            case 1156: //Kalphite worker
            case 1157: //Kalphite guardian
			return 1184;
			
			case 123:
			case 122:
			return 164;
			
			case 2028: // karil
			return 2075;
					
			case 2025: // ahrim
			return 729;
			
			case 2026: // dharok
			return 2067;
			
			case 2027: // guthan
			return 2080;
			
			case 2029: // torag
			return 0x814;
			
			case 2030: // verac
			return 2062;
			
			case 2881: //supreme
			return 2855;
			
			case 2882: //prime
			return 2854;
			
			case 2883: //rex
			return 2851;
			
			case 3200:
			return 3146;
			
			case 2745:
			if (npcs[i].attackType == 2)
			return 2656;
			else if (npcs[i].attackType == 1)
			return 2652;
			else if (npcs[i].attackType == 0)
			return 2655;
			
			
			default:
			return 0x326;		
		}
	}	
}
