package server.org.engine.character.quests;

import server.org.engine.character.Client;

public class DoricsQuest {

	static int money = 180;
	static int ID = 995;
	static int XpAdd = 1300;
	static String Msg = "You had no space in inventory. The money went to your bank";
	
	public static void doricsFinish(Client c) {
		c.dorics = 2;
		c.getPA().addSkillXP(XpAdd, 14);
		if(c.getItems().freeSlots() > 0){
			c.getItems().addItem(ID, money);
		}else{
			c.sendMessage(Msg);
			c.getItems().addItemToBank(ID, money);
		}
		c.getPA().showInterface(297);
		c.getPA().showInterface(12140);
		c.getPA().sendFrame126("You have completed: Doric's Quest", 12144);
		c.getPA().sendFrame126("1300 Mining experience", 12150);
		c.getPA().sendFrame126("1 quest point", 12151);
		c.getPA().sendFrame126("Access to Doric's anvils", 12152);
		c.getPA().sendFrame126("180 Coins", 12153);
		c.getPA().sendFrame126("", 12154);
		c.getPA().sendFrame126("", 12155);
		c.getPA().sendFrame126("@gre@Doric's Quest", 7332);
	}
	public static void showInformation(Client c) {
		for(int i = 8144; i < 8195; i++) {
			c.getPA().sendFrame126("", i);
		}
			c.getPA().sendFrame126("@dre@Doric's Quest", 8144);
			c.getPA().sendFrame126("", 8145);
		if(c.dorics == 0) {
			c.getPA().sendFrame126("Doric's Quest", 8144);
			c.getPA().sendFrame126("I can start this quest by speaking to Doric", 8147);
			c.getPA().sendFrame126("who is located in a shack north of Falador.", 8148);
			c.getPA().sendFrame126("There are no requirements, but 15 mining", 8149);
			c.getPA().sendFrame126("is highly recommended.", 8150);
		} else if(c.dorics == 1) {
			c.getPA().sendFrame126("Doric's Quest", 8144);
			c.getPA().sendFrame126("@str@I've talked to Doric.", 8147);
			c.getPA().sendFrame126("He wants me to gather the following materials:", 8148);
			if(c.getItems().playerHasItem(434, 6)){
				c.getPA().sendFrame126("@str@6 clay", 8149);	
			}else{
				c.getPA().sendFrame126("6 clay", 8149);
			}
			if(c.getItems().playerHasItem(436, 4)){
				c.getPA().sendFrame126("@str@4 Copper ore", 8150);	
			}else{
				c.getPA().sendFrame126("4 Copper ore", 8150);
			}
			if(c.getItems().playerHasItem(440, 2)){
				c.getPA().sendFrame126("@str@2 Iron ore", 8151);	
			}else{
				c.getPA().sendFrame126("2 Iron ore", 8151);
			}
			c.getPA().sendFrame126("I can find all of these materials in", 8153);
			c.getPA().sendFrame126("the underground dwarf mine. Or ", 8154);
			c.getPA().sendFrame126("Buy from players.", 8155);
		} else if(c.dorics == 2) {
			c.getPA().sendFrame126("Doric's Quest", 8144);
			c.getPA().sendFrame126("@str@Doric told me to mine 6 clay, 4 copper ores,", 8147);
			c.getPA().sendFrame126("@str@and 2 iron ores. I have acquired them", 8148);
			c.getPA().sendFrame126("@str@and given them to Doric.", 8149);
			c.getPA().sendFrame126("@red@     QUEST COMPLETE", 8151);
			c.getPA().sendFrame126("As a reward, I gained 1300 Mining experience,", 8152);
			c.getPA().sendFrame126("180 coins, and permission to use Doric's anvils.", 8153);
		}
		c.getPA().showInterface(8134);
	}

	
}
