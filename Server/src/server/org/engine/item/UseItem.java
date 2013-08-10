package server.org.engine.item;

import server.org.Config;
import server.org.core.util.Misc;
import server.org.engine.character.Client;
import server.org.engine.character.content.CrystalChest;
import server.org.engine.character.skilling.firemaking.Firemaking;
import server.org.engine.character.skilling.fletching.FletchingHandler;



/**
 * 
 * @author Ryan / Lmctruck30 & 1% Izumi
 *
 */

public class UseItem {

	public static void ItemonObject(Client c, int objectID, int objectX, int objectY, int itemId) {
		if (!c.getItems().playerHasItem(itemId, 1))
			return;
		if (itemId == CrystalChest.toothHalf()
				&& objectID == CrystalChest.loopHalf()
				|| itemId == CrystalChest.loopHalf()
				&& objectID == CrystalChest.toothHalf()) {
			CrystalChest.makeKey(c);
		}
		switch(objectID) {
		
		case 172:
			if(itemId == CrystalChest.KEY)
				CrystalChest.searchChest(c, objectID, objectX, objectY);
			break;
			
			case 8151:
			case 8389:
				c.getFarming().checkItemOnObject(itemId);
			break;
			case 2783:
				c.getSmithingInt().showSmithInterface(itemId);
			break;
			case 409:
				if (c.getPrayer().IsABone(itemId))
					c.getPrayer().bonesOnAltar(itemId);
			break;
		default:
			if(c.playerRights == 3)
				Misc.println("Player At Object id: "+objectID+" with Item id: "+itemId);
			break;
		}
		
	}

	public static void ItemonItem(Client c, int itemUsed, int useWith) {
		if(itemUsed == 946 || useWith == 946)
		    if(itemUsed == 946)
		        FletchingHandler.appendFletch(c, useWith, "useitem");
		    else
		        FletchingHandler.appendFletch(c, itemUsed, "useitem");
		if(itemUsed == c.arrowShaft || useWith == c.arrowShaft)
		    if(itemUsed == c.arrowShaft)
		        FletchingHandler.appendFletch(c, useWith, "arrow");
		    else
		        FletchingHandler.appendFletch(c, itemUsed, "arrow");
		if(itemUsed == c.headlessArrows || useWith == c.headlessArrows)
		    if(itemUsed == c.headlessArrows)
		        FletchingHandler.appendFletch(c, useWith, "arrow");
		    else
		        FletchingHandler.appendFletch(c, itemUsed, "arrow");
		if((itemUsed == c.bowString || useWith == c.bowString) || (itemUsed == c.cBowString || useWith == c.cBowString))
		    if(itemUsed == c.bowString || itemUsed == c.cBowString)
		        FletchingHandler.appendFletch(c, useWith, "string");
		    else
		        FletchingHandler.appendFletch(c, itemUsed, "string");
		if(itemUsed >= 9420 && itemUsed <= 9431)
		    if(FletchingHandler.CrossBow.forLimbID(itemUsed) != null)
		        FletchingHandler.appendFletch(c,itemUsed,"limb");
		if(useWith >= 9420 && useWith <= 9431)
		    if(FletchingHandler.CrossBow.forLimbID(useWith) != null)
		        FletchingHandler.appendFletch(c,useWith,"limb");

		if (itemUsed == 590 || useWith == 590) {
			Firemaking.attemptFire(c, itemUsed, useWith, c.absX, c.absY, false);
		}
		if (itemUsed == 227 || useWith == 227)
			c.getHerblore().handlePotMaking(itemUsed,useWith);
		if (c.getItems().getItemName(itemUsed).contains("(") && c.getItems().getItemName(useWith).contains("("))
			c.getPotMixing().mixPotion2(itemUsed, useWith);
		if (itemUsed == 1733 || useWith == 1733)
			c.getCrafting().handleLeather(itemUsed, useWith);
		if (itemUsed == 1755 || useWith == 1755)
			c.getCrafting().handleChisel(itemUsed,useWith);
		if ((itemUsed == 1540 && useWith == 11286) || (itemUsed == 11286 && useWith == 1540)) {
			if (c.playerLevel[c.playerSmithing] >= 95) {
				c.getItems().deleteItem(1540, c.getItems().getItemSlot(1540), 1);
				c.getItems().deleteItem(11286, c.getItems().getItemSlot(11286), 1);
				c.getItems().addItem(11284,1);
				c.sendMessage("You combine the two materials to create a dragonfire shield.");
				c.getPA().addSkillXP(500 * Config.SMITHING_EXPERIENCE, c.playerSmithing);
			} else {
				c.sendMessage("You need a smithing level of 95 to create a dragonfire shield.");
			}
		}
		
		if (itemUsed == 2368 && useWith == 2366 || itemUsed == 2366 && useWith == 2368) {
			c.getItems().deleteItem(2368, c.getItems().getItemSlot(2368),1);
			c.getItems().deleteItem(2366, c.getItems().getItemSlot(2366),1);
			c.getItems().addItem(1187,1);
		}
		
		if (c.getItems().isHilt(itemUsed) || c.getItems().isHilt(useWith)) {
			int hilt = c.getItems().isHilt(itemUsed) ? itemUsed : useWith;
			int blade = c.getItems().isHilt(itemUsed) ? useWith : itemUsed;
			if (blade == 11690) {
				c.getItems().makeGodsword(hilt);
			}
		}
		
		switch(itemUsed) {
			
		default:
			if(c.playerRights == 3)
				Misc.println("Player used Item id: "+itemUsed+" with Item id: "+useWith);
			break;
		}	
	}
	public static void ItemonNpc(Client c, int itemId, int npcId, int slot) {
		switch(itemId) {
		
		default:
			if(c.playerRights == 3)
				Misc.println("Player used Item id: "+itemId+" with Npc id: "+npcId+" With Slot : "+slot);
			break;
		}
		
	}


}
