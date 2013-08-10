package server.org.engine.character.packets;

import server.org.engine.character.Client;


	
public interface PacketType {
	public void processPacket(Client c, int packetType, int packetSize);
	
}

