
package server.org.engine.character.packets.player;

import server.org.engine.character.Client;
import server.org.engine.character.PlayerHandler;
import server.org.engine.character.packets.PacketType;

public class FollowPlayer implements PacketType
{

	@Override
	public void processPacket( Client c, int packetType, int packetSize )
	{
		int followPlayer = c.getInStream().readUnsignedWordBigEndian();
		if( PlayerHandler.players[followPlayer] == null ) {
			return;
		}
		c.playerIndex = 0;
		c.npcIndex = 0;
		c.mageFollow = false;
		c.usingBow = false;
		c.usingRangeWeapon = false;
		c.followDistance = 1;
		c.followId = followPlayer;
	}
}
