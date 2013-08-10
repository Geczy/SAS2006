package server.org.engine.character.skilling.firemaking;

import server.org.Config;
import server.org.Server;
import server.org.core.event.CycleEvent;
import server.org.core.event.CycleEventContainer;
import server.org.core.event.CycleEventHandler;
import server.org.core.util.Misc;
import server.org.engine.character.Client;
import server.org.engine.character.Player;
import server.org.engine.character.skilling.firemaking.LogData.logData;
import server.org.engine.character.skilling.woodcutting.Woodcutting;
import server.org.world.Objects;
import server.org.world.clip.Region;
import server.org.world.map.VirtualWorld;
import server.org.world.tile.Tiles;
import server.*;

public class Firemaking extends SkillHandler {

	public static void attemptFire(final Client c, final int itemUsed, final int usedWith, final int x, final int y, final boolean groundObject) {
		if (!c.getItems().playerHasItem(590)) {
			c.sendMessage("You need a tinderbox to light a fire.");
			return;
		}
		if (isSkilling[11] == true) {
			return;
		}
		for (final logData l : logData.values()) {
			final int logId = usedWith == 590 ? itemUsed : usedWith;
			if (logId == l.getLogId()) {
				if (c.playerLevel[11] < l.getLevel()) {
					c.sendMessage("You need a firemaking level of "+ l.getLevel() +" to light "+ c.getItems().getItemName(logId));
					return;
				}
				if (c.inBank()) {
					c.sendMessage("You cannot light a fire in a bank.");
					return;
				}
				if (Server.objectManager.objectExists(c.absX, c.absY)) {
					c.sendMessage("You cannot light a fire here.");
					return;
				}
				isSkilling[11] = true;
				boolean notInstant = (System.currentTimeMillis() - lastSkillingAction) > 2500;
				int cycle = 2;
				if (notInstant) {
					c.sendMessage("You attempt to light a fire.");
					if (groundObject == false) {
						c.getItems().deleteItem(logId, c.getItems().getItemSlot(logId), 1);
						//Server.itemHandler.createGroundItem(c, logId, c.absX, c.absY, 1, c.playerId);
					}
					cycle = 3 + Misc.random(6);
				} else {
					if (groundObject == false) {
						c.getItems().deleteItem(logId, c.getItems().getItemSlot(logId), 1);
					}
				}
				final boolean walk;
				if (Region.getClipping((x - 1), y, Player.heightLevel, -1, 0)) {
					walk = true;
				} else {
					walk = false;
				}
				c.startAnimation(733);
				c.getPA().walkTo(walk == true ? -1 : 1, 0);
				CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
					@Override
					public void execute(CycleEventContainer container) {
						if (isSkilling[11] == true) {
							Server.itemHandler.removeGroundItem(c, logId, x, c.absY, false);
						//	new Object(2732, x, y, 0, 0, 10, -1, 60 + Misc.random(30));
							Server.objectHandler.createAnObject(c, 2732, x, y, -1);
							c.sendMessage("The fire catches and the log beings to burn.");
							CycleEventHandler.getSingleton().addEvent(c, new CycleEvent() {
								@Override
								public void execute(CycleEventContainer container) {
									c.turnPlayerTo(walk == true ? (x + 1) : (x - 1), y);
									container.stop();
								}

								@Override
								public void stop() {

								}				
							}, 2);						
							container.stop();
						} else {
							return;
						}
					}
					@Override
					public void stop() {
						c.startAnimation(65535);
						c.getPA().addSkillXP((int) (l.getXp()), 11);
						lastSkillingAction = System.currentTimeMillis();
						isSkilling[11] = false;
					}
				}, cycle);				
			}
		}
	}
}