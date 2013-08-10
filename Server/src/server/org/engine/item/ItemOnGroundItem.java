package server.org.engine.item;

import server.*;
import server.org.Server;
import server.org.core.util.Misc;
import server.org.engine.character.Client;
import server.org.engine.character.packets.PacketType;
import server.org.engine.character.skilling.firemaking.Firemaking;

public class ItemOnGroundItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int a1 = c.getInStream().readSignedWordBigEndian();
		int itemUsed = c.getInStream().readSignedWordA();
		int groundItem = c.getInStream().readUnsignedWord();
		int gItemY = c.getInStream().readSignedWordA();
		int itemUsedSlot = c.getInStream().readSignedWordBigEndianA();
		int gItemX = c.getInStream().readUnsignedWord();
		if(!c.getItems().playerHasItem(itemUsed, 1, itemUsedSlot)) {
			return;
		}
		if(!Server.itemHandler.itemExists(groundItem, gItemX, gItemY)) {
			return;
		}
		
		switch(itemUsed) {
		case 590:
			Firemaking.attemptFire(c, itemUsed, groundItem, gItemX, gItemY, true);
			break;
		default:
			if(c.playerRights == 3)
				Misc.println("ItemUsed "+itemUsed+" on Ground Item "+groundItem);
			break;
		}
	}

}
