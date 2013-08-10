package server.org.engine.character.packets;

import server.org.engine.character.Client;
import server.org.engine.character.packets.report.ReportHandler;
import server.org.world.Connection;

/**
 * Chat
 **/
public class Chat implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.setChatTextEffects(c.getInStream().readUnsignedByteS());
		c.setChatTextColor(c.getInStream().readUnsignedByteS());
		ReportHandler.addText(c.playerName, c.getChatText(), packetSize - 2);
        c.setChatTextSize((byte)(c.packetSize - 2));
        c.inStream.readBytes_reverseA(c.getChatText(), c.getChatTextSize(), 0);
		if (!Connection.isMuted(c))
			c.setChatTextUpdateRequired(true);
	}	
}
