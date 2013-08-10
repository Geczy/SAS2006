package server.org.engine.character.dialogues;

import server.org.core.util.Misc;
import server.org.engine.character.Client;
import server.org.engine.character.content.lottery;
import server.org.engine.character.quests.CooksAssistant;
import server.org.engine.character.quests.DoricsQuest;
import server.org.engine.character.quests.DragonSlayer;
import server.org.engine.mob.NPCHandler;

public class DialogueHandler {

	private Client c;
	
	private DragonSlayer ds;
	public DialogueHandler(DragonSlayer DragonSlayer) {
		this.ds = DragonSlayer;
	}
	
	public DialogueHandler(Client client) {
		this.c = client;
	}
	
	
	public void sendDialogues(int dialogue, int npcId) {
		c.talkingNpc = npcId;
		switch(dialogue) {
		
		case 300:
			sendNpcChat1("Hello there, How may I help?", 741, "Duke Horacio");
			c.nextChat = 301;
			break;
		case 301:
			c.dialogueAction = 301;
			sendOption3("About the lottery", "Can i enter the lottery", "Nothing");
			break;
		case 304:
			sendPlayerChat1("Can i enter the lottery?");
			c.nextChat = 305;
			break;
		case 305:
			this.sendNpcChat3("Yes you can enter, But", "It cost's 2000 Coins to enter.", "Have you got 2000 Coins?", 741, "Duke Horacio");
			c.nextChat = 306;
			break;
		case 306:
			c.dialogueAction = 306;
			this.sendOption2("Yes i have 2000 Coins.", "No i dont have 2000 Coins.");
			break;
		case 307:
			sendNpcChat1("I Have Entered you in the lottery.", 741, "Duke Horacio");
			c.nextChat = 0;
			break;
		case 308:
			this.sendStatement("You do not have Enought Coins."); 
			c.nextChat = 0;
			break;
		case 302:
			sendPlayerChat1("I want to learn about the lottery.");
			c.nextChat = 303;
			break;
		case 303:
			sendNpcChat4("Basically When you enter you will be put ","on a list, and every i think","10 minutes it picks a random player","And gives the jackpot money.", 741, "Duke Horacio");
			c.nextChat = 301;
			break;
			/**
			 * End of enter tut basically
			 */
		case 129:
			sendOption5("Beginner (250x Runescape)", "Jorneyman (100x Runescape)", "Expert (40x Runescape)", "Master (10x Runescape)", "Insane (2x Runescape)");
			c.dialogueAction = 28;
			c.dialogueId = 38;
		break;
		case 0:
			c.talkingNpc = -1;
			c.getPA().removeAllWindows();
			c.nextChat = 0;
			break;
		case 1:
			sendStatement("You found a hidden tunnel! Do you want to enter it?");
			c.dialogueAction = 1;
			c.nextChat = 2;
			break;
		case 2:
			sendOption2("Yea! I'm fearless!",  "No way! That looks scary!");
			c.dialogueAction = 1;
			c.nextChat = 0;
			break;
		case 3:
			sendNpcChat1("'Ello, and what are you after then?", c.talkingNpc, "Mazchna");
			c.nextChat = 4;
		break;
		case 5:
			sendNpcChat4("Hello adventurer...", "My name is Kolodion, the master of this mage bank.", "Would you like to play a minigame in order ", 
						"to earn points towards receiving magic related prizes?", c.talkingNpc, "Kolodion");
			c.nextChat = 6;
		break;
		case 6:
			sendNpcChat4("The way the game works is as follows...", "You will be teleported to the wilderness,", 
			"You must kill mages to recieve points,","redeem points with the chamber guardian.", c.talkingNpc, "Kolodion");
			c.nextChat = 15;
		break;
		case 11:
			sendNpcChat1("'Ello, and what are you after then?", c.talkingNpc, "Mazchna");
			c.nextChat = 12;
		break;
		case 12:
			sendOption2("I need an assignment.", "Err... nothing.");
			c.dialogueAction = 5;
		break;
		case 13:
			sendNpcChat1("'Ello, and what are you after then?", c.talkingNpc, "Mazchna");
			c.nextChat = 14;
		break;
		case 14:
			sendOption2("I need another assignment.", "Err... nothing.");
			c.dialogueAction = 6;
		break;
		case 15:
			sendOption2("Yes I would like to play", "No, sounds too dangerous for me.");
			c.dialogueAction = 7;
		break;
		case 16:
			sendOption2("I would like to reset my barrows count.", "I would like to fix all my barrows");
			c.dialogueAction = 8;
		break;
		case 17:
			sendOption5("Air altar", "Mind altar", "Water altar", "Earth altar", "More");
			c.dialogueAction = 10;
			c.dialogueId = 17;
			c.teleAction = -1;
		break;
		case 18:
			sendOption5("Fire altar", "Body altar", "Cosmic altar", "Astral altar", "More");
			c.dialogueAction = 11;
			c.dialogueId = 18;
			c.teleAction = -1;
		break;
		case 19:
			sendOption5("Nature altar", "Law altar", "Death altar", "Blood altar", "More");
			c.dialogueAction = 12;
			c.dialogueId = 19;
			c.teleAction = -1;
		break;
		case 35:
			sendNpcChat1("Would you like to change your appearance?", c.talkingNpc, "Make-over mage");
			c.nextChat = 36;
			break;
		case 36:
			sendOption2("Yes please.", "No thanks.");
			c.dialogueAction = 36;
			c.dialogueId = 36;
			break;
			
			/**
			 * Dorics dialogues
			 **/
				        case 500:
		    			    sendNpcChat1("Hello, may I ask what you're doing here?", c.talkingNpc, "Doric");	
						    c.nextChat = 501;
						break;
						case 501:
							sendPlayerChat1("I'm looking for a quest!");
							c.nextChat = 502;
						break;
						case 502:
							if(c.dorics == 1){
								sendNpcChat1("You already have a quest...", c.talkingNpc, "Doric");
								c.nextChat = 507;
							}else{
								sendNpcChat1("A quest, huh? I can give you a quest.", c.talkingNpc, "Doric");
								c.nextChat = 503;
							}
						break;
						case 503:
							sendNpcChat3("I want you to get me", "6 clay, 4 copper ore, and 2 iron ore.", "You up for it?", c.talkingNpc, "Doric");
							c.nextChat = 504;	
						break;
						case 504:
							sendPlayerChat1("Sure.");
							c.nextChat = 505;
						break;
						case 505:
							sendNpcChat2("Okay, Come back to", "Me when you have all the Ingredients.", c.talkingNpc, "Doric");
							c.dorics = 1;	
							c.nextChat = -1;
						break;
						case 507:
							sendNpcChat1("Well? Did you get my materials yet?", c.talkingNpc, "Doric");	
							c.nextChat = 508;
						break;
						case 508:
							if(c.getItems().playerHasItem(434, 1) && c.getItems().playerHasItem(436, 1) && c.getItems().playerHasItem(440, 1)) {
								sendPlayerChat1("Yes, I have them all here!");
								c.nextChat = 509;
							} else {
								sendPlayerChat1("No sorry, I'll be getting on with that!");
								c.nextChat = -1;
							}
						break;
						case 509:
							if(c.getItems().playerHasItem(434, 1) && c.getItems().playerHasItem(436, 1) && c.getItems().playerHasItem(440, 1)) {
								sendStatement("You give Doric the materials");
								c.getItems().deleteItem(434, 6);
								c.getItems().deleteItem(436, 4);
								c.getItems().deleteItem(440, 2);
								c.nextChat = 510;
		                                        }
						break;
						case 510:
						c.getPA().removeAllWindows();
						DoricsQuest.doricsFinish(c);
						break;
						case 511:
						sendNpcChat1("Thanks again for helping me", c.talkingNpc, "Doric");
						c.nextChat = -1;
						break;

	/*
	 * Banker dialogues
	 */
		case 1000:
		sendNpcChat1("Hello, how may I help you?", c.talkingNpc, "Banker");
		c.nextChat = 1001;
		break;
		case 1001:
		sendPlayerChat1("I would like to access my bank account.");
		c.nextChat = 1002;
		break;
		case 1002:
		sendNpcChat1("Sure thing.", c.talkingNpc, "Banker");
		c.nextChat = 1003;
		break;
		case 1003:
		c.sendMessage("The banker opens up your bank account.");
		c.getPA().openUpBank();
		c.nextChat = 0;
		break;
		
	 	case 2000:
	 		sendNpcChat1("Sorry cant talk the now.", c.talkingNpc, "");
	 		c.nextChat = 2001;
	 	break;
	 	case 2001:
	 		sendPlayerChat1("Okay.");
	 		c.nextChat = 0;
	 		break;
	 		
	 		
	 		
	 		//////////Drago slayer\\\\\\\\\\

	 	case 87:
	 		DragonSlayer.Reward(c);
	 		break;
	 	case 50:
	 		if(c.dragonSlayer == 6){
	 			sendNpcChat3("Ohh God !!!","You killed the dragon !!!","Have your reward", c.talkingNpc, "Oziach");
				c.nextChat = 87;
	 		}else{
			sendNpcChat1("Hello "+ Misc.capitalize(c.playerName) +", What do you want...", c.talkingNpc, "Oziach");
			c.nextChat = 51;
	 		}
	 		break;
		case 51:
			sendOption4("How are you.", "What do you have to sell.","Any Quests.","Nothing.");
			c.dialogueAction = 50;
		break;
		
		case 53:
			sendNpcChat1("I am fine thanks", c.talkingNpc, "Oziach");
			//c.dialogueAction = 50;
			c.nextChat = 0;
		break;
		case 54:
			sendNpcChat1("Here is what i have to offer.", c.talkingNpc, "Oziach");
			c.nextChat = 56;
		break;
		case 55:
			if(c.dragonSlayer == 0){
			sendNpcChat3(
					"I do have a quest acctually",
					"But this is no easy quest, You must be able to",
					"defeat a level 83 dragon.",
					c.talkingNpc, "Oziach");
			c.nextChat = 57;
			}else if (c.dragonSlayer == 5){
				sendNpcChat1("You have completed my quest", c.talkingNpc, "Oziach");
			}else{
				sendNpcChat1("You have a quest...", c.talkingNpc, "Oziach");
			}
		break;
		case 57:
			sendNpcChat3(
					"This is no easy dragon, It's fiery breath could kill a man, ",
					"You need the right equipment to kill it, the last 3 men",
					"that went to kill it, got killed",
					c.talkingNpc, "Oziach");
			c.nextChat = 58;
		break;
		
		case 58:
			sendNpcChat1(
					"Are you really sure you want to try and kill the dragon?",
					c.talkingNpc, "Oziach");
			c.nextChat = 59;
		break;
		case 59:
			sendOption2("No dragon can take me down.","No thank you, i would not be able to kill it.");
			c.dialogueAction = 59;
			c.nextChat = 0;
		break;
		case 60:
			sendNpcChat3(
					"You are 1 brave person "+ Misc.capitalize(c.playerName),
					", Go and speak to Guildmaster in the in the Champions' Guild.",
					"He will give you further instructions.",
					c.talkingNpc, "Oziach");
			c.nextChat = 0;
			c.dragonSlayer = 1;
		break;
		case 56:
			c.getShops().openShop(107);
			break;
			
	 	case 73:
	 		sendNpcChat3("Now you have to go to Klarence","In Port Sarim and ask her","If she has any boats for sale.", c.talkingNpc, "Guildmaster");
	 		c.nextChat = 0;
	 		break;
	 	case 61://guild
	 	
			sendNpcChat1("Hello "+ Misc.capitalize(c.playerName), c.talkingNpc, "Guildmaster");
			c.nextChat = 62;
			break;
		case 62:
			sendOption2("About the dragon.","Nothing");
			c.dialogueAction = 62;
			c.nextChat = 0;
		break;
	 	case 63://guild
			sendNpcChat1("Ohh god, You want to kill the dragon?", c.talkingNpc, "Guildmaster");
			c.nextChat = 64;
	 		break;
	 	case 64:
	 		sendPlayerChat1("Yes i want to kill the dragon");
	 		c.nextChat = 65;
	 		break;
	 	case 65:
	 		if(c.dragonSlayer >= 2){
	 			sendNpcChat1("You have already said that.", c.talkingNpc, "Guildmaster");
	 		}else{
			sendNpcChat2("If you want to kill the dragon","You need to know a few thing's.", c.talkingNpc, "Guildmaster");
	 		c.nextChat = 66;
	 		c.dragonSlayer = 2;
	 		}
	 		break;
	 	case 66:
			sendNpcChat4("This dragon is Hard to kill.","Its breath can kill human in 1 blow","To prevent this from happening you need","a Anti-Dragon shield and have food.", c.talkingNpc, "Guildmaster");
	 		c.nextChat = 67;
	 		break;
	 	case 67:
	 		sendNpcChat4("You also need 3 map peice's","1 from defeting Melzar the Mad","1 from Oracle","and the last peice from Wormbrain", c.talkingNpc, "Guildmaster");
	 		c.nextChat = 68;
	 		break;
	 	case 68:
	 		if(		c.getItems().playerHasItem(1535) &&
	 				c.getItems().playerHasItem(1536) &&
	 				c.getItems().playerHasItem(1537)){
	 			sendNpcChat1("Give me all 3 peices.", c.talkingNpc, "Guildmaster");
	 			c.dragonSlayer = 3;
	 			c.nextChat = 73;
	 			c.getItems().deleteItem(1535, 1);
	 			c.getItems().deleteItem(1536, 1);
	 			c.getItems().deleteItem(1537, 1);
	 		}else{
	 		sendOption5("Where can i get an anti-dragon shield.", "Where can i find Melzar the Mad.", "Where can i find Oracle", "Where can i find Wormbrain", "Nothing");
	 		c.dialogueAction = 68;
	 		}
	 		break;
	 	case 69:
	 		sendNpcChat3("You can get a Anti-dragon Shield ","From Duke Horacio","In lumbridge castle second floor", c.talkingNpc, "Guildmaster");
	 		c.nextChat = 68;
	 		break;
	 	case 70:
	 		sendNpcChat4("I can find Melzar the mad","Inside Melzar's Maze which ","is south of the Crafting Guild","Bring some food and armour.", c.talkingNpc, "Guildmaster");
	 		c.nextChat = 68;
	 		break;
	 	case 71:
	 		sendNpcChat3("I can find Oracle","At the ice mountains which is","west of Edgeville and north of Falador", c.talkingNpc, "Guildmaster");
	 		c.nextChat = 68;
	 		break;
	 	case 72:
	 		sendNpcChat3("I can find Wormbrain","In Port Sarim jail","He wont come easy thought", c.talkingNpc, "Guildmaster");
	 		c.nextChat = 68;
	 		break;
	 		
	 		
	 	case 74:
	 		sendNpcChat1("Hello "+ Misc.capitalize(c.playerName)+",What are you after?", c.talkingNpc, "Klarence");
	 		c.nextChat = 75;
	 		break;
	 	case 75:
	 		sendOption2("About a boat", "Nothing Bye");
	 		c.dialogueAction = 75;
	 		break;
	 	case 76:
	 		sendPlayerChat1("Do you have any boats for sale");
	 		c.nextChat = 77;
	 		break;
	 	case 77:
	 		if(c.dragonSlayer >= 5){
	 			sendNpcChat1("You already have one.", c.talkingNpc, "Klarence");
		 		c.nextChat = 0;
	 		}else{
	 		sendNpcChat3("Indead i do. i have this","Beauty right beside me going","for, lets say 10k?", c.talkingNpc, "Klarence");
	 		c.nextChat = 78;
	 		}
	 		break;
	 	case 78:
	 		sendOption2("Yes i have a spare 10k","I will be back later");
	 		c.dialogueAction = 78;
	 	//	c.nextChat = 68;
	 		break;
	 	case 79:
	 		sendNpcChat1("The boat is all yours", c.talkingNpc, "Klarence");
	 		c.nextChat = 0;
	 		break;
	 	case 80:
	 		sendNpcChat1("Come back when you got 10k", c.talkingNpc, "Klarence");
	 		break;
			///////////////End of dragon slayer\\\\\\\\\\\\
	 		
	 	case 81:
	 		sendNpcChat1("'Ello Er' Captain.", c.talkingNpc, "Ned");
	 		c.nextChat = 82;
	 		break;
	 	case 82:
	 		sendOption2("Can you sail me to Elvarg's Cave","Nothing.");
	 		c.dialogueAction = 82;
	 		break;
	 	case 83:
	 		sendNpcChat1("Yes i can , just get prepared right now.", c.talkingNpc, "Ned");
	 		c.nextChat = 84;
	 		break;
	 		
	 	case 84:
	 		sendNpcChat1("One last chance to get prepared.", c.talkingNpc, "Ned");
	 		c.nextChat = 85;
	 		break;
	 	case 85:
c.getPA().movePlayer(2853, 3238, 0);
	 		break;
	 		
	 		
	 		
			
			case 600:
				sendNpcChat1("What am I to do?", c.talkingNpc, "Cook");
				c.nextChat = 601;
				break;
			case 601:
				sendOption4("What's wrong?", "Can you cook me a cake?", "You don't look very happy.", "Nice hat.");
				c.dialogueAction = 601;
				break;
			case 602:
				sendPlayerChat1("What's wrong?");
				c.nextChat = 603;
				break;
			case 603:
				sendNpcChat3("Oh dear, oh dear, oh dear, I'm in a terrible terrible", "mess! It's the Duke's birthday today, and I should be", "making him a lovely big birthday cake!", c.talkingNpc, "Cook");
				c.nextChat = 604;
				break;
			case 604:
				sendNpcChat4("I've forgotten to buy the ingredients. I'll never get", "them in time now. He'll sack me! What will I do? I have", "four children and a goat to look after. Would you help", "me? Please?", c.talkingNpc, "Cook");
				c.nextChat = 605;
				break;
			case 605:
				sendOption2("I'm always happy to help a cook in distress.", "I can't right now, Maybe later.");
				c.dialogueAction = 605;
				break;
			case 606:
				sendPlayerChat1("Yes, I'll help you.");
				c.nextChat = 609;
				break;
			case 607:
				sendPlayerChat1("I can't right now, Maybe later.");
				c.nextChat = 608;
				break;
			case 608:
				sendNpcChat1("Oh please! Hurry then!", c.talkingNpc, "Cook");
				c.nextChat = 0;
				break;
			case 609:
				sendNpcChat2("Oh thank you, thank you. I need milk, an egg, and", "flour. I'd be very grateful if you can get them for me.", c.talkingNpc, "Cook");
				c.cookAss = 1;
				c.nextChat = 610;	
				break;
			case 610:
				sendPlayerChat1("So where do I find these ingredients then?");
				c.nextChat = 611;
				break;
			case 611:
				sendNpcChat3("You can find flour in any of the shops here.", "You can find eggs by killing chickens.", "You can find milk by using a bucket on a cow", c.talkingNpc, "Cook");
				c.nextChat = 0;
				break;
			case 612:
				sendNpcChat1("I don't have time for your jibber-jabber!", c.talkingNpc, "Cook");
				c.nextChat = 0;
				break;
			case 613:
				sendNpcChat1("Does it look like I have the time?", c.talkingNpc, "Cook");
				c.nextChat = 0;
				break;
			case 614:
				sendPlayerChat1("You don't look so happy.");
				c.nextChat = 603;
				break;
			case 615:
				sendNpcChat1("How are you getting on with finding the ingredients?", c.talkingNpc, "Cook");
				c.nextChat = 616;
				break;
			case 616:
				if(c.getItems().playerHasItem(1944, 1) && c.getItems().playerHasItem(1927, 1) && c.getItems().playerHasItem(1933, 1)) {
				sendPlayerChat1("Here's all the items!");
				c.nextChat = 618;
				} else {
				sendPlayerChat1("I don't have all the items yet.");
				c.nextChat = 608;
				}
				break;
			case 618:
				c.getItems().deleteItem(1944, 1);
				c.getItems().deleteItem(1927, 1);
				c.getItems().deleteItem(1933, 1);
				c.cookAss = 2;
				sendNpcChat2("You brought me everything I need! I'm saved!", "Thank you!", c.talkingNpc, "Cook");
				c.nextChat = 619;
				break;
			case 619:
				sendPlayerChat1("So do I get to go to the Duke's Party?");
				c.nextChat = 620;
				break;
			case 620:
				sendNpcChat2("I'm agraid not, only the big cheeses get to dine with the", "Duke.", c.talkingNpc, "Cook");
				c.nextChat = 621;
				break;
			case 621:
				sendPlayerChat2("Well, maybe one day I'll be important enough to sit on", "the Duke's table");
				c.nextChat = 622;
				break;
			case 622:
				sendNpcChat1("Maybe, but I won't be holding my breath.", c.talkingNpc, "Cook");
				c.nextChat = 623;
				break;
			case 623:
				CooksAssistant.cookFinish(c);
				break;
			case 624:
				sendNpcChat1("Thanks for helping me out friend!", c.talkingNpc, "Cook");
				c.nextChat = 0;
				break;
	 		
		}
	}
	
	

	
	/*
	 * Information Box
	 */
	
	public void sendStartInfo(String text, String text1, String text2, String text3, String title) {
		c.getPA().sendFrame126(title, 6180);
		c.getPA().sendFrame126(text, 6181);
		c.getPA().sendFrame126(text1, 6182);
		c.getPA().sendFrame126(text2, 6183);
		c.getPA().sendFrame126(text3, 6184);
		c.getPA().sendFrame164(6179);
	}
	
	/*
	 * Item chat
	 */
	
	public void sendItemChat1(String header, String one, int item, int zoom) {
		c.getPA().sendFrame246(4883, zoom, item);
		c.getPA().sendFrame126(header, 4884);
		c.getPA().sendFrame126(one, 4885);
		c.getPA().sendFrame164(4882);
	}

	public void sendItemChat2(String header, String one, String two, int item, int zoom) {
		c.getPA().sendFrame246(4888, zoom, item);
		c.getPA().sendFrame126(header, 4889);
		c.getPA().sendFrame126(one, 4890);
		c.getPA().sendFrame126(two, 4891);
		c.getPA().sendFrame164(4887);
	}

	public void sendItemChat3(String header, String one, String two, String three, int item, int zoom) {
		c.getPA().sendFrame246(4894, zoom, item);
		c.getPA().sendFrame126(header, 4895);
		c.getPA().sendFrame126(one, 4896);
		c.getPA().sendFrame126(two, 4897);
		c.getPA().sendFrame126(three, 4898);
		c.getPA().sendFrame164(4893);
	}

	public void sendItemChat4(String header, String one, String two, String three, String four, int item, int zoom) {
		c.getPA().sendFrame246(4901, zoom, item);
		c.getPA().sendFrame126(header, 4902);
		c.getPA().sendFrame126(one, 4903);
		c.getPA().sendFrame126(two, 4904);
		c.getPA().sendFrame126(three, 4905);
		c.getPA().sendFrame126(four, 4906);
		c.getPA().sendFrame164(4900);
	}
	
	/*
	 * Options
	 */
	
	public void sendOption(String s) {
		c.getPA().sendFrame126("Select an Option", 2470);
	 	c.getPA().sendFrame126(s, 2471);
		c.getPA().sendFrame126("Click here to continue", 2473);
		c.getPA().sendFrame164(13758);
	}	
	
	public void sendOption2(String s, String s1) {
		c.getPA().sendFrame126("Select an Option", 2460);
		c.getPA().sendFrame126(s, 2461);
		c.getPA().sendFrame126(s1, 2462);
		c.getPA().sendFrame164(2459);
	}
	
	public void sendOption3(String s, String s1, String s2) {
		c.getPA().sendFrame126("Select an Option", 2470);
		c.getPA().sendFrame126(s, 2471);
		c.getPA().sendFrame126(s1, 2472);
		c.getPA().sendFrame126(s2, 2473);
		c.getPA().sendFrame164(2469);
	}
	
	public void sendOption4(String s, String s1, String s2, String s3) {
		c.getPA().sendFrame126("Select an Option", 2481);
		c.getPA().sendFrame126(s, 2482);
		c.getPA().sendFrame126(s1, 2483);
		c.getPA().sendFrame126(s2, 2484);
		c.getPA().sendFrame126(s3, 2485);
		c.getPA().sendFrame164(2480);
	}
	
	public void sendOption5(String s, String s1, String s2, String s3, String s4) {
		c.getPA().sendFrame126("Select an Option", 2493);
		c.getPA().sendFrame126(s, 2494);
		c.getPA().sendFrame126(s1, 2495);
		c.getPA().sendFrame126(s2, 2496);
		c.getPA().sendFrame126(s3, 2497);
		c.getPA().sendFrame126(s4, 2498);
		c.getPA().sendFrame164(2492);
	}

	/*
	 * Statements
	 */
	
	public void sendStatement(String s) {
		c.getPA().sendFrame126(s, 357);
		c.getPA().sendFrame126("Click here to continue", 358);
		c.getPA().sendFrame164(356);
	}
	
	public void sendStatement2(String s, String s1) {
		c.getPA().sendFrame126(s, 360);
		c.getPA().sendFrame126(s1, 361);
		c.getPA().sendFrame126("Click here to continue", 362);
		c.getPA().sendFrame164(359);
	}
	
	public void sendStatement3(String s, String s1, String s2) {
		c.getPA().sendFrame126(s, 364);
		c.getPA().sendFrame126(s1, 365);
		c.getPA().sendFrame126(s2, 366);
		c.getPA().sendFrame126("Click here to continue", 367);
		c.getPA().sendFrame164(363);
	}
	
	public void sendStatement4(String s, String s1, String s2, String s3) {
		c.getPA().sendFrame126(s, 369);
		c.getPA().sendFrame126(s1, 370);
		c.getPA().sendFrame126(s2, 371);
		c.getPA().sendFrame126(s3, 372);
		c.getPA().sendFrame126("Click here to continue", 373);
		c.getPA().sendFrame164(368);
	}
	
	public void sendStatement5(String s, String s1, String s2, String s3, String s4) {
		c.getPA().sendFrame126(s, 375);
		c.getPA().sendFrame126(s1, 376);
		c.getPA().sendFrame126(s2, 377);
		c.getPA().sendFrame126(s3, 378);
		c.getPA().sendFrame126(s4, 379);
		c.getPA().sendFrame126("Click here to continue", 380);
		c.getPA().sendFrame164(374);
	}
	
	/*
	 * Npc Chatting
	 */
	
	public void sendNpcChat1(String s, int ChatNpc, String name) {
		c.getPA().sendFrame200(4883, 591);
		c.getPA().sendFrame126(name, 4884);
		c.getPA().sendFrame126(s, 4885);
		c.getPA().sendFrame75(ChatNpc, 4883);
		c.getPA().sendFrame164(4882);
	}
	
	public void sendNpcChat2(String s, String s1, int ChatNpc, String name) {
		c.getPA().sendFrame200(4888, 591);
		c.getPA().sendFrame126(name, 4889);
		c.getPA().sendFrame126(s, 4890);
		c.getPA().sendFrame126(s1, 4891);
		c.getPA().sendFrame75(ChatNpc, 4888);
		c.getPA().sendFrame164(4887);
	}

	public void sendNpcChat3(String s, String s1, String s2, int ChatNpc, String name) {
		c.getPA().sendFrame200(4894, 591);
		c.getPA().sendFrame126(name, 4895);
		c.getPA().sendFrame126(s, 4896);
		c.getPA().sendFrame126(s1, 4897);
		c.getPA().sendFrame126(s2, 4898);
		c.getPA().sendFrame75(ChatNpc, 4894);
		c.getPA().sendFrame164(4893);
	}
	
	public void sendNpcChat4(String s, String s1, String s2, String s3, int ChatNpc, String name) {
		c.getPA().sendFrame200(4901, 591);
		c.getPA().sendFrame126(name, 4902);
		c.getPA().sendFrame126(s, 4903);
		c.getPA().sendFrame126(s1, 4904);
		c.getPA().sendFrame126(s2, 4905);
		c.getPA().sendFrame126(s3, 4906);
		c.getPA().sendFrame75(ChatNpc, 4901);
		c.getPA().sendFrame164(4900);
	}
	
	/*
	 * Player Chating Back
	 */
	
	public void sendPlayerChat1(String s) {
		c.getPA().sendFrame200(969, 591);
		c.getPA().sendFrame126(Misc.capitalize(c.playerName), 970);
		c.getPA().sendFrame126(s, 971);
		c.getPA().sendFrame185(969);
		c.getPA().sendFrame164(968);
	}
	
	public void sendPlayerChat2(String s, String s1) {
		c.getPA().sendFrame200(974, 591);
		c.getPA().sendFrame126(Misc.capitalize(c.playerName), 975);
		c.getPA().sendFrame126(s, 976);
		c.getPA().sendFrame126(s1, 977);
		c.getPA().sendFrame185(974);
		c.getPA().sendFrame164(973);
	}
	
	public void sendPlayerChat3(String s, String s1, String s2) {
		c.getPA().sendFrame200(980, 591);
		c.getPA().sendFrame126(Misc.capitalize(c.playerName), 981);
		c.getPA().sendFrame126(s, 982);
		c.getPA().sendFrame126(s1, 983);
		c.getPA().sendFrame126(s2, 984);
		c.getPA().sendFrame185(980);
		c.getPA().sendFrame164(979);
	}
	
	public void sendPlayerChat4(String s, String s1, String s2, String s3) {
		c.getPA().sendFrame200(987, 591);
		c.getPA().sendFrame126(Misc.capitalize(c.playerName), 988);
		c.getPA().sendFrame126(s, 989);
		c.getPA().sendFrame126(s1, 990);
		c.getPA().sendFrame126(s2, 991);
		c.getPA().sendFrame126(s3, 992);
		c.getPA().sendFrame185(987);
		c.getPA().sendFrame164(986);
	}
}
