package server.org.engine.item;

import server.org.engine.character.Client;
import server.org.engine.character.packets.PacketType;
import server.org.engine.character.skilling.firemaking.Firemaking;
import server.org.engine.character.skilling.firemaking.LogData.logData;



public class ItemOnGroundItem2 implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		final int itemX = c.getInStream().readSignedWordBigEndian();
		final int itemY = c.getInStream().readSignedWordBigEndianA();
		final int itemId = c.getInStream().readUnsignedWordA();
		System.out.println("ItemClick2OnGroundItem - "+ c.playerName +" - "+ itemId +" - "+ itemX +" - "+ itemY);
		for (logData l : logData.values()) {
			if (itemId == l.getLogId()) {
				Firemaking.attemptFire(c, 590, itemId, itemX, itemY, true);
			}
		}
	}
}