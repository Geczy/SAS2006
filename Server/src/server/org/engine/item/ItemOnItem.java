package server.org.engine.item;

/**
 * @author Ryan / Lmctruck30
 */

import server.org.engine.character.Client;
import server.org.engine.character.packets.PacketType;
import server.org.engine.character.skilling.firemaking.Firemaking;
import server.org.engine.character.skilling.fletching.Fletching;

public class ItemOnItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int usedWithSlot = c.getInStream().readUnsignedWord();
		int itemUsedSlot = c.getInStream().readUnsignedWordA();
		int useWith = c.playerItems[usedWithSlot] - 1;
		int itemUsed = c.playerItems[itemUsedSlot] - 1;
		if(!c.getItems().playerHasItem(useWith, 1, usedWithSlot) || !c.getItems().playerHasItem(itemUsed, 1, itemUsedSlot)) {
			return;
		}

		/*} else if((useWith == 1511 || itemUsed == 1511) && (useWith == 946 || itemUsed == 946)) {
			Fletching.normal(c, itemUsed, useWith);
		} else if(useWith == 946 || itemUsed == 946) {
			Fletching.others(c, itemUsed, useWith);
		} else if(Fletching.arrows(c, itemUsed, useWith)) {
			Fletching.makeArrows(c, itemUsed, useWith);
		}*/
		UseItem.ItemonItem(c, itemUsed, useWith);
		}
	}

