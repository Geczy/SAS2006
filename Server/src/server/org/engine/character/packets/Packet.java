package server.org.engine.character.packets;

import server.org.engine.character.Client;

/**
 * Packet interface.
 * 
 * @author Graham
 * 
 */
public interface Packet {

	public void handlePacket(Client client, int packetType, int packetSize);

}
