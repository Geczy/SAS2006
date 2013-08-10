package server.org.engine.character.content;

import server.org.Server;
import server.org.engine.character.Client;
import server.org.world.Objects;
import server.org.world.clip.Region;
import server.org.world.handler.ObjectHandler;

public class MithrilSeeds {
	
	
	
        private static long FlowerDelay = 0;
        public static int Action = 1;
		public static int FlowerDialogue = 1;
		
        public static int Flower[] = {2980,2981,2982,2983,2984,2985,2986,2987,2988};
        public static int randomFlower() {
        return Flower[(int)(Math.random()*Flower.length)];
        } 
        public static void plant(final Client c) {
                final int[] coords = new int[2];
        		for (Objects o : Server.objectHandler.globalObjects) {
        			if (o.getObjectX() == c.getX() && o.getObjectY() == c.getY() && o.objectId != -1) {
        				c.sendMessage("You cannot plant a flower here.");
        				return;
        			}
        		}
        		if (System.currentTimeMillis() - FlowerDelay > 1600) {
        			FlowerDelay = System.currentTimeMillis();
                coords[0] = c.absX;
                coords[1] = c.absY;
                c.getDH().sendOption2("Pick up","Leave");
                FlowerDialogue = Action;
                ObjectHandler.createAnObject(c, randomFlower(), coords[0], coords[1]);
                c.turnPlayerTo(c.getX() + 1, c.getY());
                c.getItems().deleteItem(299, c.getItems().getItemSlot(299), 1);
                c.getPA().walkTo(-1,0);
        if (Region.getClipping(c.getX() - 1, c.getY(), c.heightLevel, -1, 0)) {
                c.getPA().walkTo(-1, 0);
        } else if (Region.getClipping(c.getX() + 1, c.getY(), c.heightLevel, 1, 0)) {
                c.getPA().walkTo(1, 0);
        } else if (Region.getClipping(c.getX(), c.getY() - 1, c.heightLevel, 0, -1)) {
                c.getPA().walkTo(0, -1);
        } else if (Region.getClipping(c.getX(), c.getY() + 1, c.heightLevel, 0, 1)) {
                c.getPA().walkTo(0, 1);
        }
        		}
}
        
}