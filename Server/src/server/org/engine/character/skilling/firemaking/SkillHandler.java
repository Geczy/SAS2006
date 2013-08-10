package server.org.engine.character.skilling.firemaking;

public class SkillHandler {
	
	public static boolean[] isSkilling = new boolean[25];
	
	public static long lastSkillingAction;
	
	public static void resetSkillingVariables() {
		for (int skill = 0; skill < isSkilling.length; skill++) {
			isSkilling[skill] = false;
		}
	}
}