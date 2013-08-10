package server.org.engine.character;

import java.util.LinkedList;




import java.util.Queue;
import java.util.concurrent.Future;


import org.apache.mina.common.IoSession;

import server.org.Config;
import server.org.Server;
import server.org.core.connect.HostList;
import server.org.core.event.CycleEventHandler;
import server.org.core.event.Event;
import server.org.core.event.EventContainer;
import server.org.core.event.EventManager;
import server.org.core.net.Packet;
import server.org.core.net.StaticPacketBuilder;
import server.org.core.util.Misc;
import server.org.core.util.Stream;
import server.org.engine.character.combat.CombatAssistant;
import server.org.engine.character.consumables.Food;
import server.org.engine.character.consumables.Potions;
import server.org.engine.character.content.Highscores;
import server.org.engine.character.content.HighscoresConfig;
import server.org.engine.character.content.Magic;
import server.org.engine.character.content.ScreenHandler;
import server.org.engine.character.dialogues.DialogueHandler;
import server.org.engine.character.packets.PacketHandler;
import server.org.engine.character.quests.DragonSlayer;
import server.org.engine.character.skilling.Agility;
import server.org.engine.character.skilling.Crafting;
import server.org.engine.character.skilling.Farming;
import server.org.engine.character.skilling.Fishing;
import server.org.engine.character.skilling.Herblore;
import server.org.engine.character.skilling.Prayer;
import server.org.engine.character.skilling.Runecrafting;
import server.org.engine.character.skilling.SkillInterfaces;
import server.org.engine.character.skilling.Slayer;
import server.org.engine.character.skilling.smithing.Smithing;
import server.org.engine.character.skilling.smithing.SmithingInterface;
import server.org.engine.character.trading.TradeAndDuel;
import server.org.engine.item.ItemAssistant;
import server.org.engine.minigame.pestcontrol.PestControl;
import server.org.engine.mob.NPCHandler;
import server.org.world.shop.ShopAssistant;

public class Client extends Player {

	
	
	private DragonSlayer Dragonslayer = new DragonSlayer();
	
	public DragonSlayer getDS() {
		return Dragonslayer;
	}
	public static PlayerSave save;
	public static Client cliento2;
	public byte buffer[] = null;
	public Stream inStream = null, outStream = null;
	private IoSession session;
	private ItemAssistant itemAssistant = new ItemAssistant(this);
	private ShopAssistant shopAssistant = new ShopAssistant(this);
	private TradeAndDuel tradeAndDuel = new TradeAndDuel(this);
	private PlayerAssistant playerAssistant = new PlayerAssistant(this);
	private CombatAssistant combatAssistant = new CombatAssistant(this);
	private ActionHandler actionHandler = new ActionHandler(this);
	private PlayerKilling playerKilling = new PlayerKilling(this);
	private DialogueHandler dialogueHandler = new DialogueHandler(this);
	private Queue<Packet> queuedPackets = new LinkedList<Packet>();
	private Potions potions = new Potions(this);
	private Fishing fish = new Fishing(this);
	private PotionMixing potionMixing = new PotionMixing(this);
	private Food food = new Food(this);
	//private TutorialIsland tutorialIsland = new TutorialIsland(this);
	
	private SkillInterfaces skillInterfaces = new SkillInterfaces(this);
	
	/**
	* Quests
	*/

	
	/**
	 * Skill instances
	 */
	private Slayer slayer = new Slayer(this);
	private Runecrafting runecrafting = new Runecrafting(this);
	private Agility agility = new Agility(this);
	//private Cooking cooking = new Cooking(this);
	private Crafting crafting = new Crafting(this);
	//private Fletching fletching = new Fletching(this);
	private Farming farming = new Farming(this);
	private Prayer prayer = new Prayer(this);
	private SmithingInterface smithInt = new SmithingInterface(this);
	private Smithing smith = new Smithing(this);
	private Magic magic = new Magic(this);
	private Herblore herblore = new Herblore(this);
		
	//private int somejunk;
	public int lowMemoryVersion = 0;
	public int timeOutCounter = 0;		
	public int returnCode = 2; 
	private Future<?> currentTask;
	public int currentRegion = 0;
	
	public Client(IoSession s, int _playerId) {
		super(_playerId);
		this.session = s;
		synchronized(this) {
			outStream = new Stream(new byte[Config.BUFFER_SIZE]);
			outStream.currentOffset = 0;
		
		inStream = new Stream(new byte[Config.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		buffer = new byte[Config.BUFFER_SIZE];
		}
	}
	
	public int LotteryDraw = 0;
	
	/**
     * Shakes the player's screen.
     * Parameters 1, 0, 0, 0 to reset.
     * @param verticleAmount How far the up and down shaking goes (1-4).
     * @param verticleSpeed How fast the up and down shaking is.
     * @param horizontalAmount How far the left-right tilting goes.
     * @param horizontalSpeed How fast the right-left tiling goes..
     */
	
    public void shakeScreen(int verticleAmount, int verticleSpeed, int horizontalAmount, int horizontalSpeed) {
            outStream.createFrame(35); // Creates frame 35.
            outStream.writeByte(verticleAmount);
            outStream.writeByte(verticleSpeed);
            outStream.writeByte(horizontalAmount);
            outStream.writeByte(horizontalSpeed);
    }
    
    /**
    * Resets the shaking of the player's screen.
    */
   public void resetShaking() {
           shakeScreen(0, 0, 0, 0);
   }
	
	public void flushOutStream() {	
		if(disconnected || outStream.currentOffset == 0) return;
		synchronized(this) {	
			StaticPacketBuilder out = new StaticPacketBuilder().setBare(true);
			byte[] temp = new byte[outStream.currentOffset]; 
			System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);
			out.addBytes(temp);
			session.write(out.toPacket());
			outStream.currentOffset = 0;
		}
		}
	public Fishing getFishing() {
		return fish;
	}
	
	public void sendClan(String name, String message, String clan, int rights) {
		outStream.createFrameVarSizeWord(217);
		outStream.writeString(name);
		outStream.writeString(message);
		outStream.writeString(clan);
		outStream.writeWord(rights);
		outStream.endFrameVarSize();
	}
	
	public static final int PACKET_SIZES[] = {
		0, 0, 0, 1, -1, 0, 0, 0, 0, 0, //0
		0, 0, 0, 0, 8, 0, 6, 2, 2, 0,  //10
		0, 2, 0, 6, 0, 12, 0, 0, 0, 0, //20
		0, 0, 0, 0, 0, 8, 4, 0, 0, 2,  //30
		2, 6, 0, 6, 0, -1, 0, 0, 0, 0, //40
		0, 0, 0, 12, 0, 0, 0, 8, 8, 12, //50
		8, 8, 0, 0, 0, 0, 0, 0, 0, 0,  //60
		6, 0, 2, 2, 8, 6, 0, -1, 0, 6, //70
		0, 0, 0, 0, 0, 1, 4, 6, 0, 0,  //80
		0, 0, 0, 0, 0, 3, 0, 0, -1, 0, //90
		0, 13, 0, -1, 0, 0, 0, 0, 0, 0,//100
		0, 0, 0, 0, 0, 0, 0, 6, 0, 0,  //110
		1, 0, 6, 0, 0, 0, -1, 0, 2, 6, //120
		0, 4, 6, 8, 0, 6, 0, 0, 0, 2,  //130
		0, 0, 0, 0, 0, 6, 0, 0, 0, 0,  //140
		0, 0, 1, 2, 0, 2, 6, 0, 0, 0,  //150
		0, 0, 0, 0, -1, -1, 0, 0, 0, 0,//160
		0, 0, 0, 0, 0, 0, 0, 0, 0, 0,  //170
		0, 8, 0, 3, 0, 2, 0, 0, 8, 1,  //180
		0, 0, 12, 0, 0, 0, 0, 0, 0, 0, //190
		2, 0, 0, 0, 0, 0, 0, 0, 4, 0,  //200
		4, 0, 0, 0, 7, 8, 0, 0, 10, 0, //210
		0, 0, 0, 0, 0, 0, -1, 0, 6, 0, //220
		1, 0, 0, 0, 6, 0, 6, 8, 1, 0,  //230
		0, 4, 0, 0, 0, 0, -1, 0, -1, 4,//240
		0, 0, 6, 6, 0, 0, 0            //250
	};

	public void destruct() {
		if(session == null) 
			return;
		//PlayerSaving.getSingleton().requestSave(playerId);
		getPA().removeFromCW();
		if (inPits)
			Server.fightPits.removePlayerFromPits(playerId);
		if (clanId >= 0)
			Server.clanChat.leaveClan(playerId, clanId);
		Misc.println("[OFFLINE]: "+ Misc.capitalize(playerName) +"");
		CycleEventHandler.getSingleton().stopEvents(this);
		HostList.getHostList().remove(session);
		disconnected = true;
		session.close();
		session = null;
		inStream = null;
		outStream = null;
		isActive = false;
		buffer = null;
		super.destruct();
	}
	
	
	public void sendMessage(String s) {
		//synchronized (this) {
			if(getOutStream() != null) {
				outStream.createFrameVarSize(253);
				outStream.writeString(s);
				outStream.endFrameVarSize();
			}
		
	}

	public void setSidebarInterface(int menuId, int form) {
		//synchronized (this) {
			if(getOutStream() != null) {
				outStream.createFrame(71);
				outStream.writeWord(form);
				outStream.writeByteA(menuId);
			}
		
	}	
	
	public void initialize() {
		Server.lottery.checkUnclaimedWinners(this);
		
		//synchronized (this) 
			//welcomeScreenInterface();
			//getPA().loadQuests();
			outStream.createFrame(249);
			outStream.writeByteA(1);		// 1 for members, zero for free
			outStream.writeWordBigEndianA(playerId);
			if(this.mode == 0){
				this.getDH().sendDialogues(129, 0);
			}
			for (int j = 0; j < Server.playerHandler.players.length; j++) {
				if (j == playerId)
					continue;
				if (Server.playerHandler.players[j] != null) {
					if (Server.playerHandler.players[j].playerName.equalsIgnoreCase(playerName))
						disconnected = true;
				}
			}
			//getPA().setSidebarInterfaces(this);
			//if(showWelcomeScreen){
				//getPA().showInterface(15244);
			//}
			for (int i = 0; i < 25; i++) {
				getPA().setSkillLevel(i, playerLevel[i], playerXP[i]);
				getPA().refreshSkill(i);
			}
			for(int p = 0; p < PRAYER.length; p++) { // reset prayer glows 
				prayerActive[p] = false;
				getPA().sendFrame36(PRAYER_GLOW[p], 0);	
			}
			getPA().handleWeaponStyle();
			getPA().handleLoginText();
			accountFlagged = getPA().checkForFlags();
			//getPA().sendFrame36(43, fightMode-1);
			getPA().sendFrame36(108, 0);//resets autocast button
			getPA().sendFrame36(172, 1);
			getPA().sendFrame107(); // reset screen
			getPA().setChatOptions(0, 0, 0); // reset private messaging options
			setSidebarInterface(1, 3917);
			setSidebarInterface(2, 638);
			setSidebarInterface(3, 3213);
			setSidebarInterface(4, 1644);
			setSidebarInterface(5, 5608);
			if(playerMagicBook == 0) {
				setSidebarInterface(6, 1151); //modern
			} else {
				setSidebarInterface(6, 12855); // ancient
			}
			correctCoordinates();
			setSidebarInterface(7, 18128);		
			setSidebarInterface(8, 5065);
			setSidebarInterface(9, 5715);
			setSidebarInterface(10, 2449);
			//setSidebarInterface(11, 4445); // wrench tab
			setSidebarInterface(11, 904); // wrench tab
			setSidebarInterface(12, 147); // run tab
			setSidebarInterface(13, -1);
			setSidebarInterface(0, 2423);
			sendMessage("Welcome to "+Config.SERVER_NAME+".");
			/*if(!membership) {
				sendMessage("You are currently not a member.");
			} else {
				sendMessage("You are currently a member. Membership never expires.");
			}*/
			getPA().showOption(4, 0,"Follow", 4);
			getPA().showOption(5, 0,"Trade With", 3);
			getItems().resetItems(3214);
			getItems().sendWeapon(playerEquipment[playerWeapon], getItems().getItemName(playerEquipment[playerWeapon]));
			getItems().resetBonus();
			getItems().getBonus();
			getItems().writeBonus();
			getItems().setEquipment(playerEquipment[playerHat],1,playerHat);
			getItems().setEquipment(playerEquipment[playerCape],1,playerCape);
			getItems().setEquipment(playerEquipment[playerAmulet],1,playerAmulet);
			getItems().setEquipment(playerEquipment[playerArrows],playerEquipmentN[playerArrows],playerArrows);
			getItems().setEquipment(playerEquipment[playerChest],1,playerChest);
			getItems().setEquipment(playerEquipment[playerShield],1,playerShield);
			getItems().setEquipment(playerEquipment[playerLegs],1,playerLegs);
			getItems().setEquipment(playerEquipment[playerHands],1,playerHands);
			getItems().setEquipment(playerEquipment[playerFeet],1,playerFeet);
			getItems().setEquipment(playerEquipment[playerRing],1,playerRing);
			getItems().setEquipment(playerEquipment[playerWeapon],playerEquipmentN[playerWeapon],playerWeapon);
			getCombat().getPlayerAnimIndex(getItems().getItemName(playerEquipment[playerWeapon]).toLowerCase());
			getPA().logIntoPM();
			getItems().addSpecialBar(playerEquipment[playerWeapon]);
			saveTimer = Config.SAVE_TIMER;
			saveCharacter = true;
			Misc.println("[ONLINE]: "+Misc.capitalize(playerName)+"");
			handler.updatePlayer(this, outStream);
			handler.updateNPC(this, outStream);
			flushOutStream();
			getPA().clearClanChat();
			getPA().resetFollow();
			if (addStarter)
				getPA().addStarter();
			if (autoRet == 1)
				getPA().sendFrame36(172, 1);
			else
				getPA().sendFrame36(172, 0);
				totalLevel = getPA().totalLevel();
				xpTotal = getPA().xpTotal();
				HighscoresConfig.updateHighscores(this);
		}
	
	private Highscores highscores = new Highscores(this);

	public Highscores getHighscores() {
		return highscores;
	}
	
	


	public void update() {
		//synchronized (this) {
			handler.updatePlayer(this, outStream);
			handler.updateNPC(this, outStream);
			flushOutStream();
		
	}
	
	public void logout() {
		//synchronized (this) {
			if(System.currentTimeMillis() - logoutDelay > 10000) {
				outStream.createFrame(109);
				CycleEventHandler.getSingleton().stopEvents(this);
				properLogout = true;
			} else {
				sendMessage("You must wait a few seconds from being out of combat to logout.");
			}
		
	}
	
	public int packetSize = 0, packetType = -1;
	public int totalPlaytime(){
		return (pTime / 2);}
	public String getPlaytime(){
		int DAY = (totalPlaytime() / 86400);
		int HR = (totalPlaytime() / 3600) - (DAY * 24);
		int MIN = (totalPlaytime() / 60) - (DAY * 1440) - (HR * 60);
		return (DAY+" days "+HR+" hours "+MIN+" minutes");
	}
	public String getSmallPlaytime(){
		int DAY = (totalPlaytime() / 86400);
		int HR = (totalPlaytime() / 3600) - (DAY * 24);
		int MIN = (totalPlaytime() / 60) - (DAY * 1440) - (HR * 60);
		return ("Day:"+DAY+"/Hr:"+HR+"/Min:"+MIN+"");
	}
		
	PestControl pc;	
	public void process() {
		
		if (System.currentTimeMillis() - Server.lottery.lastAnnouncment > (1000 * 60 * Server.lottery.announcmentFrequency)) {
			Server.lottery.announceFund();
			Server.lottery.lastAnnouncment = System.currentTimeMillis();
		}
		if (System.currentTimeMillis() - Server.lottery.nextDraw > (1000 * 60 * Server.lottery.nextDrawFrequency)) {
			Server.lottery.drawLottery();
			Server.lottery.nextDraw = System.currentTimeMillis();
		}
		getPA().loadQuests();
		if (smeltTimer > 0 && smeltType > 0) {
			smeltTimer--;
		} else if (smeltTimer == 0 && smeltType > 0) {
			getSmithing().smelt(smeltType);
		}
			getPA().writeEnergy();
		if(System.currentTimeMillis() - specDelay > Config.INCREASE_SPECIAL_AMOUNT) {
			specDelay = System.currentTimeMillis();
			if(specAmount < 10) {
				specAmount += .5;
				if (specAmount > 10)
					specAmount = 10;
				getItems().addSpecialBar(playerEquipment[playerWeapon]);
			}
		}
		
		if(followId > 0) {
			getPA().followPlayer();
		} else if (followId2 > 0) {
			getPA().followNpc();
		}
		getFishing().FishingProcess();
		getCombat().handlePrayerDrain();
		if(System.currentTimeMillis() - singleCombatDelay >  3300) {
			underAttackBy = 0;
		}
		if (System.currentTimeMillis() - singleCombatDelay2 > 3300) {
			underAttackBy2 = 0;
		}
		
		if(System.currentTimeMillis() - restoreStatsDelay >  60000) {
			restoreStatsDelay = System.currentTimeMillis();
			for (int level = 0; level < playerLevel.length; level++)  {
				if (playerLevel[level] < getLevelForXP(playerXP[level])) {
					if(level != 5) { // prayer doesn't restore
						playerLevel[level] += 1;
						getPA().setSkillLevel(level, playerLevel[level], playerXP[level]);
						getPA().refreshSkill(level);
					}
				} else if (playerLevel[level] > getLevelForXP(playerXP[level])) {
					playerLevel[level] -= 1;
					getPA().setSkillLevel(level, playerLevel[level], playerXP[level]);
					getPA().refreshSkill(level);
				}
			}
		}

		if(inWild()) {
			int modY = absY > 6400 ?  absY - 6400 : absY;
			wildLevel = (((modY - 3520) / 8) + 1);
			getPA().walkableInterface(197);
			if(Config.SINGLE_AND_MULTI_ZONES) {
				if(inMulti()) {
					getPA().sendFrame126("@yel@Level: "+wildLevel, 199);
				} else {
					getPA().sendFrame126("@yel@Level: "+wildLevel, 199);
				}
			} else {
				getPA().multiWay(-1);
				getPA().sendFrame126("@yel@Level: "+wildLevel, 199);
			}
			getPA().showOption(3, 0, "Attack", 1);
		} else if(inPcBoat()) {
		    getPA().walkableInterface(21119);
			getPA().sendFrame126("Next Departure: ", 21120);
			getPA().sendFrame126("Players Ready: ", 21121);
			getPA().sendFrame126("(Need 3 to 25 players)", 21122);
			getPA().sendFrame126("Points: "+pcPoints+"", 21123);
		} else if(inPcGame()) {
		    getPA().walkableInterface(21100);
		    for (int j = 0; j < PlayerHandler.players.length; j++)
		    for (j = 0; j < NPCHandler.npcs.length; j++) {
				if (NPCHandler.npcs[j] != null) {
				if (NPCHandler.npcs[j].npcType == 3777)
					getPA().sendFrame126("" + NPCHandler.npcs[j].HP + "", 21111);
				if (NPCHandler.npcs[j].npcType == 3778)
					getPA().sendFrame126("" + NPCHandler.npcs[j].HP + "", 21112);
				if (NPCHandler.npcs[j].npcType == 3779)
					getPA().sendFrame126("" + NPCHandler.npcs[j].HP + "", 21113);
				if (NPCHandler.npcs[j].npcType == 3780)
					getPA().sendFrame126("" + NPCHandler.npcs[j].HP + "", 21114);
				}
		    }
		} else if (inDuelArena()) {
			getPA().walkableInterface(201);
			if(duelStatus == 5) {
				getPA().showOption(3, 0, "Attack", 1);
			} else {
				getPA().showOption(3, 0, "Challenge", 1);
			}
		} else if(inBarrows()){
			getPA().sendFrame99(2);
			getPA().sendFrame126("Kill Count: "+barrowsKillCount, 4536);
			getPA().walkableInterface(4535);
		} else if (inCwGame || inPits) {
			getPA().showOption(3, 0, "Attack", 1);	
		} else if (getPA().inPitsWait()) {
			getPA().showOption(3, 0, "Null", 1);
		}else if (!inCwWait) {
			getPA().sendFrame99(0);
			getPA().walkableInterface(-1);
			getPA().showOption(3, 0, "Null", 1);
		}
		
		if(!hasMultiSign && inMulti()) {
			hasMultiSign = true;
			getPA().multiWay(1);
		}
		
		if(hasMultiSign && !inMulti()) {
			hasMultiSign = false;
			getPA().multiWay(-1);
		}

		if(skullTimer > 0) {
			skullTimer--;
			if(skullTimer == 1) {
				isSkulled = false;
				attackedPlayers.clear();
				headIconPk = -1;
				skullTimer = -1;
				getPA().requestUpdates();
			}	
		}
		
		if(isDead && respawnTimer == -6) {
			getPA().applyDead();
		}
		
		if(respawnTimer == 7) {
			respawnTimer = -6;
			getPA().giveLife();
		} else if(respawnTimer == 12) {
			respawnTimer--;
			startAnimation(0x900);
			poisonDamage = -1;
		}	
		
		if(respawnTimer > -6) {
			respawnTimer--;
		}
		if(freezeTimer > -6) {
			freezeTimer--;
			if (frozenBy > 0) {
				if (Server.playerHandler.players[frozenBy] == null) {
					freezeTimer = -1;
					frozenBy = -1;
				} else if (!goodDistance(absX, absY, Server.playerHandler.players[frozenBy].absX, Server.playerHandler.players[frozenBy].absY, 20)) {
					freezeTimer = -1;
					frozenBy = -1;
				}
			}
		}
		
		if(hitDelay > 0) {
			hitDelay--;
		}
		if(pTime != 2147000000){
			pTime++;}
		if(teleTimer > 0) {
			teleTimer--;
			if (!isDead) {
				if(teleTimer == 1 && newLocation > 0) {
					teleTimer = 0;
					getPA().changeLocation();
				}
				if(teleTimer == 5) {
					teleTimer--;
					getPA().processTeleport();
				}
				if(teleTimer == 9 && teleGfx > 0) {
					teleTimer--;
					gfx100(teleGfx);
				}
			} else {
				teleTimer = 0;
			}
		}	

		if(hitDelay == 1) {
			if(oldNpcIndex > 0) {
				getCombat().delayedHit(oldNpcIndex);
			}
			if(oldPlayerIndex > 0) {
				getCombat().playerDelayedHit(oldPlayerIndex);				
			}		
		}
		
		if(attackTimer > 0) {
			attackTimer--;
		}
		
		if(attackTimer == 1){
			if(npcIndex > 0 && clickNpcType == 0) {
				getCombat().attackNpc(npcIndex);
			}
			if(playerIndex > 0) {
				getCombat().attackPlayer(playerIndex);
			}
		} else if (attackTimer <= 0 && (npcIndex > 0 || playerIndex > 0)) {
			if (npcIndex > 0) {
				attackTimer = 0;
				getCombat().attackNpc(npcIndex);
			} else if (playerIndex > 0) {
				attackTimer = 0;
				getCombat().attackPlayer(playerIndex);
			}
		}
		
		if(timeOutCounter > Config.TIMEOUT) {
			disconnected = true;
		}
		
		timeOutCounter++;
		
	}
	
	public void setCurrentTask(Future<?> task) {
		currentTask = task;
	}

	public Future<?> getCurrentTask() {
		return currentTask;
	}
	
	public synchronized Stream getInStream() {
		return inStream;
	}
	
	public synchronized int getPacketType() {
		return packetType;
	}
	
	public synchronized int getPacketSize() {
		return packetSize;
	}
	
	public synchronized Stream getOutStream() {
		return outStream;
	}
	
	public ItemAssistant getItems() {
		return itemAssistant;
	}
		
	public PlayerAssistant getPA() {
		return playerAssistant;
	}
	
	public DialogueHandler getDH() {
		return dialogueHandler;
	}
	
	public ShopAssistant getShops() {
		return shopAssistant;
	}
	
	public TradeAndDuel getTradeAndDuel() {
		return tradeAndDuel;
	}
	
	public CombatAssistant getCombat() {
		return combatAssistant;
	}
	
	public ActionHandler getActions() {
		return actionHandler;
	}
  
	public PlayerKilling getKill() {
		return playerKilling;
	}
	
	public IoSession getSession() {
		return session;
	}
	
	public Potions getPotions() {
		return potions;
	}
	
	public PotionMixing getPotMixing() {
		return potionMixing;
	}
	
	public Food getFood() {
		return food;
	}
	
	private boolean isBusy = false;
	private boolean isBusyHP = false;
	public boolean isBusyFollow = false;

	public boolean checkBusy() {
		/*if (getCombat().isFighting()) {
			return true;
		}*/
		if (isBusy) {
			//actionAssistant.sendMessage("You are too busy to do that.");
		}
		return isBusy;
	}

	public boolean checkBusyHP() {
		return isBusyHP;
	}

	public boolean checkBusyFollow() {
		return isBusyFollow;
	}

	public void setBusy(boolean isBusy) {
		this.isBusy = isBusy;
	}

	public boolean isBusy() {
		return isBusy;
	}

	public void setBusyFollow(boolean isBusyFollow) {
		this.isBusyFollow = isBusyFollow;
	}

	public void setBusyHP(boolean isBusyHP) {
		this.isBusyHP = isBusyHP;
	}

	public boolean isBusyHP() {
		return isBusyHP;
	}
	public boolean isBusyFollow() {
		return isBusyFollow;
	}	

	private boolean canWalk = true;

	public boolean canWalk() {
		return canWalk;
	}

	public void setCanWalk(boolean canWalk) {
		this.canWalk = canWalk;
	}
	
	public PlayerAssistant getPlayerAssistant() {
		return playerAssistant;
	}
	
	
	public SkillInterfaces getSI() {
		return skillInterfaces;
	}
	
	/**
	 * Skill Constructors
	 */
	public Slayer getSlayer() {
		return slayer;
	}
	
	public Runecrafting getRunecrafting() {
		return runecrafting;
	}
	
	

	/*public Cooking getCooking() {
		return cooking;
	}*/
	
	public Agility getAgil() {
		return agility;
	}
	

	public Crafting getCrafting() {
		return crafting;
	}
	
	public SmithingInterface getSmithingInt() {
		return smithInt;
	}
	
	public Smithing getSmithing() {
		return smith;
	}

	public Farming getFarming() {
		return farming;
	}
	

	
	public Herblore getHerblore() {
		return herblore;
	}
	
	/*public Fletching getFletching() { 
		return fletching;
	}*/
	
	public Magic getMagic() {
		return magic;
	}
	
	public Prayer getPrayer() { 
		return prayer;
	}
	
	/**
	 * End of Skill Constructors
	 */
	
	public void queueMessage(Packet arg1) {
		synchronized(queuedPackets) {
			//if (arg1.getId() != 41)
				queuedPackets.add(arg1);
			//else
				//processPacket(arg1);
		}
	}
	
	public synchronized boolean processQueuedPackets() {
		Packet p = null;
		synchronized(queuedPackets) {
			p = queuedPackets.poll();
		}
		if(p == null) {
			return false;
		}
		inStream.currentOffset = 0;
		packetType = p.getId();
		packetSize = p.getLength();
		inStream.buffer = p.getData();
		if(packetType > 0) {
			//sendMessage("PacketType: " + packetType);
			PacketHandler.processPacket(this, packetType, packetSize);
		}
		timeOutCounter = 0;
		return true;
	}
	
	public synchronized boolean processPacket(Packet p) {
		synchronized (this) {
			if(p == null) {
				return false;
			}
			inStream.currentOffset = 0;
			packetType = p.getId();
			packetSize = p.getLength();
			inStream.buffer = p.getData();
			if(packetType > 0) {
				//sendMessage("PacketType: " + packetType);
				PacketHandler.processPacket(this, packetType, packetSize);
			}
			timeOutCounter = 0;
			return true;
		}
	}
		
	
	
	
	
	public void correctCoordinates() {
		if (inPcGame()) {
			getPA().movePlayer(2657, 2639, 0);
		}
		if (inFightCaves()) {
			getPA().movePlayer(absX, absY, playerId * 4);
			sendMessage("Your wave will start in 10 seconds.");
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer c) {
					Server.fightCaves.spawnNextWave((Client)Server.playerHandler.players[playerId]);
					c.stop();
				}
				}, 10000);
		
		}
	
	}
	
	public void ResetScreen() {
			ScreenHandler.ResetScreen(this);
	}
	public void sendFrame35(int i1, int i2, int i3, int i4) {
			ScreenHandler.sendFrame35(this, i1, i2, i3, i4);
	}
	public void sendFrame177(int x, int y, int height, int speed, int angle) {
			ScreenHandler.sendFrame177(this, x, y, height, speed, angle);
	}
	public void Rumble(){
			ScreenHandler.Rumble();
	}
	
}
