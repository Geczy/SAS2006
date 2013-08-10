package server.org.engine.character.packets.change;

import server.org.engine.character.Client;
import server.org.engine.character.packets.PacketType;

public class ChangeRegion implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.getPA().removeObjects();
		//Server.objectManager.loadObjects(c);
	}

}
