package server.org.engine.character.quests;

import server.org.engine.character.Client;
import server.org.engine.character.dialogues.DialogueHandler;

public class DragonSlayer {
	
public static int DragonSlayerComplete = 7;


	public static void Reward(Client c){
 		c.nextChat = 0;
		c.dragonSlayer = 7;
		if(c.getPA().freeSlots() > 0){
			c.getItems().addItem(995, 7500);
		}else{
			c.getItems().addItemToBank(995, 7500);
			c.sendMessage("7.5k has been added to your bank.");
		}
		c.getPA().addSkillXP(18750, 2);
		c.getPA().addSkillXP(18750, 1);
		c.getPA().showInterface(297);
		c.getPA().showInterface(12140);
		c.getPA().sendFrame126("You have completed: Dragon Slayer", 12144);
		c.getPA().sendFrame126("You are awarded with", 12150);
		c.getPA().sendFrame126("18.5k Strength and Defence Xp", 12151);
		c.getPA().sendFrame126("Ability to wear Rune platebody's", 12152);
		c.getPA().sendFrame126(",green Dragonhide Body's", 12153);
		c.getPA().sendFrame126("And 7.5k cash", 12154);
		c.getPA().sendFrame126("", 12155);
	}
	public static void showInformation(Client c) {
		for(int i = 8144; i < 8195; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("@dre@Dragon Slayer", 8144);
			c.getPA().sendFrame126("", 8145);
		if(c.dragonSlayer == 0) {
			c.getPA().sendFrame126("@dre@Dragon Slayer", 8144);
			c.getPA().sendFrame126("I can start this quest by speaking to the Oziach", 8147);
			c.getPA().sendFrame126("who can be found in Edgeville next to the", 8148);
			c.getPA().sendFrame126("General store.", 8149);
			c.getPA().sendFrame126("", 8150);
			c.getPA().sendFrame126("@dre@Note: you must be able to slay a", 8151);
			c.getPA().sendFrame126("@dre@Level 83 dragon", 8152);
		} else if(c.dragonSlayer == 1) {
			c.getPA().sendFrame126("@dre@Dragon Slayer", 8144);
			c.getPA().sendFrame126("@str@I've talked to the Oziach.", 8147);
			c.getPA().sendFrame126("Oziach said i have to go and speak to", 8148);
			c.getPA().sendFrame126("Guildmaster who is located in the ", 8149);
			c.getPA().sendFrame126("Champion's guild which can be found ", 8150);
			c.getPA().sendFrame126("south of varrock.", 8151);
		} else if(c.dragonSlayer == 2) {
			c.getPA().sendFrame126("@dre@Dragon Slayer", 8144);
			c.getPA().sendFrame126("@str@I have spoke to Guildmaster.", 8147);
			c.getPA().sendFrame126("He wants me to get 3 peices of map.", 8148);
			if(c.getItems().playerHasItem(1535)){
				c.getPA().sendFrame126("@str@Map part 1", 8150);
			}else{
				c.getPA().sendFrame126("Map part 1", 8150);
			}
			if(c.getItems().playerHasItem(1536)){
				c.getPA().sendFrame126("@str@Map part 2", 8151);
			}else{
				c.getPA().sendFrame126("Map part 2", 8151);
			}
			if(c.getItems().playerHasItem(1537)){
				c.getPA().sendFrame126("@str@Map part 3", 8152);
			}else{
				c.getPA().sendFrame126("Map part 3", 8152);
			}
			c.getPA().sendFrame126("I can get map part 1 by killing", 8154);
			c.getPA().sendFrame126("Melzar the mad. Who can be located in ", 8155);
			c.getPA().sendFrame126("Rimmington.", 8156);
			c.getPA().sendFrame126("I can get map part 2 by talking to", 8157);
			c.getPA().sendFrame126("Oracle, who is at the white mountains.", 8158);
			c.getPA().sendFrame126("I can get map part 3 by Killing Wormbrain", 8159);
			c.getPA().sendFrame126("who is being held in port Sarim jail.", 8160);
			} else if(c.dragonSlayer == 3) {
			c.getPA().sendFrame126("@dre@Dragon Slayer", 8144);
			c.getPA().sendFrame126("@str@I have gave all the map parts to Guildmaster", 8147);
			c.getPA().sendFrame126("He said i have to go and speak to Klarense.", 8148);
			c.getPA().sendFrame126("He can be found at port sarim dock's. He", 8150);
			c.getPA().sendFrame126("might have a boat he could sell me to travel", 8151);
			c.getPA().sendFrame126("to the dragon itself.", 8152);
			} else if(c.dragonSlayer == 4) {
			c.getPA().sendFrame126("@dre@Dragon Slayer", 8144);
			c.getPA().sendFrame126("I have defeated elvarg", 8147);
			c.getPA().sendFrame126("I will need to ask Klarence to", 8148);
			c.getPA().sendFrame126("Sail me to the dragons island.", 8149);
		} else if(c.dragonSlayer == 5) {
			c.getPA().sendFrame126("@dre@Dragon Slayer", 8144);
			c.getPA().sendFrame126("@str@I have bought the boat off Klarense.", 8147);
			c.getPA().sendFrame126("He mentioned some1 in Draynor that would", 8148);
			c.getPA().sendFrame126("Sail me to the dragon's den. His name was", 8149);
			c.getPA().sendFrame126("Ned or something. Maybe i should go and see ", 8150);
			c.getPA().sendFrame126("Ned in draynor.", 8151);
		} else if(c.dragonSlayer == 6) {
			c.getPA().sendFrame126("@dre@Dragon Slayer", 8144);
			c.getPA().sendFrame126("@str@I have killed the dragon.", 8147);
			c.getPA().sendFrame126("I should go and speak to Oziach again", 8148);
			c.getPA().sendFrame126("and recive my reward.", 8149);
			c.getPA().sendFrame126("", 8150);
			c.getPA().sendFrame126("", 8151);
		} else if(c.dragonSlayer == 7) {
			c.getPA().sendFrame126("@dre@Dragon Slayer", 8144);
			c.getPA().sendFrame126("@red@Quest complete", 8147);
			c.getPA().sendFrame126("Reward:", 8148);
			c.getPA().sendFrame126("18k strength and defence xp", 8149);
			c.getPA().sendFrame126("Ability to wear rune platebodie's", 8150);
			c.getPA().sendFrame126("and green dragon bodies.", 8151);
		}
		c.getPA().showInterface(8134);	
	}
}
