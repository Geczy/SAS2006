package server.org.engine.character.packets.report;

import server.org.engine.character.Client;
import server.org.engine.character.packets.PacketType;


public class Report implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		try {
			ReportHandler.handleReport(c);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}