package server.org.engine.character.content;

import server.org.core.event.Event;
import server.org.core.event.EventContainer;
import server.org.core.event.EventManager;
import server.org.engine.character.Client;

/**
 * @author bailey <wtf ur zerk>
 * Handles Ladders & stairs
 */
public class LadderHandler {
	

	
	public static boolean handleLadder(final int i,final Client c) {
		ladder(c);
		EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer e) {
				if(c.heightLevel == 0) {
					c.getPA().movePlayer(c.absX, c.absY, 1);
				} else if(c.heightLevel == 1) {
					c.getPA().movePlayer(c.absX, c.absY, 0);
				} else if(c.heightLevel == 2) {
					c.getPA().movePlayer(c.absX, c.absY, 1);
				}
				e.stop();
			}
		}, 1500);
		return false;
	}
	
	public static void ladderOption(final String climbType,final Client c) {
			ladder(c);
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer e) {
					if (climbType.equalsIgnoreCase("up")) {
						c.getPA().movePlayer(c.absX, c.absY, c.heightLevel += 1);
					}
					if (climbType.equalsIgnoreCase("down")) {
						c.getPA().movePlayer(c.absX, c.absY, c.heightLevel -= 1);
					}
					e.stop();
				}
			}, 1500);
	}	
	
	public static void getOption(Client c) {
		String s = "Climb up the ladder.", s1 = "Climb down the ladder.";
		c.getDH().sendOption2(s, s1);
	}
	private static int x, y;
	
	public static void ladder(Client c) {
		x = c.objectX;
		y = c.objectY;
		faceLadder(c, x, y);
		c.startAnimation(828); c.stopMovement(); c.resetWalkingQueue();
		c.getPA().requestUpdates(); c.getPA().removeAllWindows();
	}
	
	public static void faceLadder(Client c,final int x, final int y) {
		c.turnPlayerTo(c.objectX, c.objectY);
	}
	
	
}