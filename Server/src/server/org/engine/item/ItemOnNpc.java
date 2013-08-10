
package server.org.engine.item;

import server.org.engine.character.Client;
import server.org.engine.character.packets.PacketType;
import server.org.engine.mob.NPCHandler;

public class ItemOnNpc implements PacketType
{

	@Override
	public void processPacket( Client c, int packetType, int packetSize )
	{
		int itemId = c.getInStream().readSignedWordA();
		int i = c.getInStream().readSignedWordA();
		int slot = c.getInStream().readSignedWordBigEndian();
		int npcId = NPCHandler.npcs[i].npcType;
		if( ! c.getItems().playerHasItem( itemId, 1, slot ) ) {
			return;
		}
		UseItem.ItemonNpc( c, itemId, npcId, slot );
	}
}
