package server.org.engine.character.commands;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import server.org.Config;
import server.org.Server;
import server.org.core.util.Misc;
import server.org.engine.character.Client;
import server.org.engine.character.PlayerHandler;
import server.org.engine.character.content.lottery;
import server.org.engine.character.packets.PacketType;
import server.org.world.Connection;



public class Commands implements PacketType {

	@Override
    public void processPacket(Client c, int packetType, int packetSize) {
		String playerCommand = c.getInStream().readString();
        if(c.playerRights >= 0){//Player Commands
        	
        	if (playerCommand.startsWith("suggest")/* && playerCommand.length() > 13*/) {
	   			try {
		 			BufferedWriter report = new BufferedWriter(new FileWriter("./Data/Suggestions.txt", true));
					String Report = playerCommand.substring(7);
					try {	
					report.newLine();
					report.write(c.playerName + ": " + Report);
					c.sendMessage("You have successfully submitted your suggestion.");
					} finally {
					report.close();
					}
				} catch (IOException e) {
	            				e.printStackTrace();
			}
		}
        	if (playerCommand.equalsIgnoreCase("players")) {
                c.sendMessage("Current amount of players online: @red@" + PlayerHandler.getPlayerCount() + "@bla@.");
        	}
        	if (playerCommand.equalsIgnoreCase("enterlot")) {
        		lottery.enterLottery(c);
               
        	}
        	if (playerCommand.equalsIgnoreCase("drawlot")) {
        		lottery.drawLottery();
                c.sendMessage("You draw the lottery");
        	}
            if (playerCommand.startsWith("changepass") && playerCommand.length() > 15) {
                c.playerPass = playerCommand.substring(15);
                c.sendMessage("Your password is now: @red@" + c.playerPass);
            }
            if (playerCommand.equalsIgnoreCase("timeplayed")) {
            	c.sendMessage("Time played: @red@" + c.getPlaytime() + ".");
            }
            if (playerCommand.startsWith("yell")) {
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						if (c.playerRights == 0) {
    							Client c2 = (Client)Server.playerHandler.players[j];
        						c2.sendMessage("@dbl@" + Misc.capitalize(c.playerName) +": " + Misc.optimizeText(playerCommand.substring(5)) +"");
    						}
						if (c.playerRights == 1) {
    						Client c2 = (Client)Server.playerHandler.players[j];
    						c2.sendMessage("[MOD] @dbl@" + Misc.capitalize(c.playerName) +": " + Misc.optimizeText(playerCommand.substring(5)) +"");
    						}
						if (c.playerRights == 2) {
							Client c2 = (Client)Server.playerHandler.players[j];
							c2.sendMessage("[ADMIN] @dbl@" + Misc.capitalize(c.playerName) +": " + Misc.optimizeText(playerCommand.substring(5)) +"");
						}
						if (c.playerRights == 3) {
							Client c2 = (Client)Server.playerHandler.players[j];
							c2.sendMessage("[Owner] @dbl@" + Misc.capitalize(c.playerName) +": " + Misc.optimizeText(playerCommand.substring(5)) +"");
						}
					}
				}
        }
        }
        if(c.playerRights >= 1){//Moderator Commands
        	if (playerCommand.startsWith("bank")) {
                c.getPA().openUpBank();
           }
        	if(playerCommand.startsWith("pnpc")) {
            	int npc = Integer.parseInt(playerCommand.substring(5));
            	if(npc < 9999){
            		c.npcId2 = npc;
            		c.isNpc = true;
            		c.updateRequired = true;
            		c.appearanceUpdateRequired = true;
            	}
    	}
        	if(playerCommand.startsWith("unpc")) {
            	c.isNpc = false;
            	c.updateRequired = true;
            	c.appearanceUpdateRequired = true;
    	}
        	if (playerCommand.startsWith("smsg")) {
        		String[] args = playerCommand.split(" ");
        		for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						c2.sendMessage("" + Misc.optimizeText(playerCommand.substring(5)));
					}
        		}
        	}
        	if (playerCommand.equalsIgnoreCase("mypos")) {
                c.sendMessage("Your position is X: " + c.absX + " Y: " +c.absY);
        	}
        	if (playerCommand.startsWith("xteletome")) {
				if (c.inWild())
				return;
				try {	
					String playerToBan = playerCommand.substring(9);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client)Server.playerHandler.players[i];
								c2.teleportToX = c.absX;
								c2.teleportToY = c.absY;
								c2.heightLevel = c.heightLevel;
								c.sendMessage("You have teleported " + Misc.capitalize(c2.playerName) + " to you.");
								c2.sendMessage("You have been teleported to " + Misc.capitalize(c.playerName) + "");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player is probably offline.");
				}
			}
        	if (playerCommand.startsWith("xteleto")) {
				String name = playerCommand.substring(8);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(name)) {
							c.getPA().movePlayer(Server.playerHandler.players[i].getX(), Server.playerHandler.players[i].getY(), Server.playerHandler.players[i].heightLevel);
						}
					}
				}			
			}
        	if (playerCommand.startsWith("kick")) {
				try {	
					String playerToKick = playerCommand.substring(5);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToKick)) {
								Server.playerHandler.players[i].disconnected = true;
								Server.playerHandler.players[i].properLogout = true;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player is not online.");
				}
			}
        	if (playerCommand.startsWith("mute")) {
                try {
                    String playerToBan = playerCommand.substring(5);
                    Connection.addNameToMuteList(playerToBan);
                    for (int i = 0; i < Config.MAX_PLAYERS; i++) {
                        if (Server.playerHandler.players[i] != null) {
                            if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                                Client c2 = (Client) Server.playerHandler.players[i];
                                c2.sendMessage("You have been muted by: " + Misc.capitalize(c.playerName) + ".");
                                break;
                            }
                        }
                    }
                } catch (Exception e) {
                    c.sendMessage("Player is probably offline.");
                }
        	}
        	if (playerCommand.startsWith("unmute")) {
        		try {
        			String playerToBan = playerCommand.substring(7);
        			Connection.unMuteUser(playerToBan);
        		} catch (Exception e) {
        			c.sendMessage("Player is probably offline.");
        		}
        	}
        	if (playerCommand.startsWith("empty")) {
            	c.getItems().removeAllItems();
            	c.sendMessage("You empty your inventory");
        	}
        }
        
        if(c.playerRights >= 2){//Administrator commands
            if (playerCommand.startsWith("anim")) {
                String[] args = playerCommand.split(" ");
                c.startAnimation(Integer.parseInt(args[1]));
                c.getPA().requestUpdates();
           }
            if (playerCommand.startsWith("setlevel")) {
                try {
                    String[] args = playerCommand.split(" ");
                    int skill = Integer.parseInt(args[1]);
                    int level = Integer.parseInt(args[2]);
                    if (level > 99) {
                        level = 99;
                    } else if (level < 0) {
                        level = 1;
                    }
                    c.playerXP[skill] = c.getPA().getXPForLevel(level) + 5;
                    c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
                    c.getPA().refreshSkill(skill);
                } catch (Exception e) {
                }	
           }
        	  if (playerCommand.startsWith("gfx")) {
                  String[] args = playerCommand.split(" ");
                  c.gfx0(Integer.parseInt(args[1]));
             }
        	  if (playerCommand.startsWith("npc")) {
                  try {
                      int newNPC = Integer.parseInt(playerCommand.substring(4));
                      if (newNPC > 0) {
                          Server.npcHandler.spawnNpc(c, newNPC, c.absX, c.absY, 0, 0, 120, 7, 70, 70, false, false);
                      } else {
                          c.sendMessage("Requested NPC does not exist.");
                      }
                  } catch (Exception e) {
                  }
              }
        	if (playerCommand.startsWith("tele")) {
				String[] arg = playerCommand.split(" ");
				if (arg.length > 3)
					c.getPA().movePlayer(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),Integer.parseInt(arg[3]));
				else if (arg.length == 3)
					c.getPA().movePlayer(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),c.heightLevel);
        	}
        	if (playerCommand.startsWith("switch")) {
                if (c.playerMagicBook == 0) {
                    c.playerMagicBook = 1;
                    c.setSidebarInterface(6, 12855);
                    c.sendMessage("An ancient wisdomin fills your mind.");
                    c.getPA().resetAutocast();
                } else {
                    c.setSidebarInterface(6, 1151);
                    c.playerMagicBook = 0;
                    c.sendMessage("You feel a drain on your memory.");
                    c.autocastId = -1;
                    c.getPA().resetAutocast();
                }
        	}
        	 if (playerCommand.startsWith("interface")) {
                 try {
                     String[] args = playerCommand.split(" ");
                     int a = Integer.parseInt(args[1]);
                     c.getPA().showInterface(a);
                 } catch (Exception e) {
                     c.sendMessage("::interface id");
                 }
             }
        	if (playerCommand.startsWith("object")) {
    			String[] args = playerCommand.split(" ");				
    			c.getPA().object(Integer.parseInt(args[1]), c.absX, c.absY, 0, 10);
        	}
        	if(playerCommand.startsWith("ogrecage")){
        		c.getPA().startTeleport(2530, 3369, 0, "modern");
        	}
            if (playerCommand.equalsIgnoreCase("infenergy")) {
            	c.playerEnergy = 50000;
            	c.sendMessage("@red@you energy is "+c.playerEnergy);
            }
        	if (playerCommand.startsWith("item")) {
                try {
                    String[] args = playerCommand.split(" ");
                    if (args.length == 3) {
                        int newItemID = Integer.parseInt(args[1]);
                        int newItemAmount = Integer.parseInt(args[2]);
                        if ((newItemID <= 8000) && (newItemID >= 0)) {
                            c.getItems().addItem(newItemID, newItemAmount);
                            c.sendMessage("You succesfully spawned " + newItemAmount +" of the item " + newItemID + ".");
                            System.out.println("Spawned: " + newItemID + " by: " + Misc.capitalize(c.playerName));
                        } else {
                            c.sendMessage("Could not complete spawn request.");
                        }
                    } else {
                        c.sendMessage("Use as ::item 4151 1");
                    }
                } catch (Exception e) {
                }
        	}
        	if (playerCommand.startsWith("unipmute")) {
        		try {
        			String playerToBan = playerCommand.substring(9);
        			for (int i = 0; i < Config.MAX_PLAYERS; i++) {
        				if (Server.playerHandler.players[i] != null) {
        					if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
        						Connection.unIPMuteUser(Server.playerHandler.players[i].connectedFrom);
        						c.sendMessage("You have un IP-muted the user: " + Server.playerHandler.players[i].playerName);
                            break;
        					}
        				}
        			}
        		} catch (Exception e) {
        			c.sendMessage("Player is probably offline.");
        		}
        	}
        	if (playerCommand.startsWith("ban")) { // use as ::ban name
				try {	
					String playerToBan = playerCommand.substring(8);
					Connection.addNameToBanList(playerToBan);
					Connection.addNameToFile(playerToBan);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Server.playerHandler.players[i].disconnected = true;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player is not online.");
				}
			}
            if (playerCommand.startsWith("unban")) {
                try {
                    String playerToBan = playerCommand.substring(6);
                    Connection.removeNameFromBanList(playerToBan);
                    c.sendMessage(playerToBan + " has been unbanned.");
                } catch (Exception e) {
                    c.sendMessage("Player is probably offline.");
                }
           }
        	 if (playerCommand.startsWith("ipban")) {
                 try {
                     String playerToBan = playerCommand.substring(6);
                     for (int i = 0; i < Config.MAX_PLAYERS; i++) {
                         if (Server.playerHandler.players[i] != null) {
                             if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                                 Connection.addIpToBanList(Server.playerHandler.players[i].connectedFrom);
                                 Connection.addIpToFile(Server.playerHandler.players[i].connectedFrom);
                                 c.sendMessage("You have IP banned the user: " + Server.playerHandler.players[i].playerName + " with the host: " + Server.playerHandler.players[i].connectedFrom);
                                 Server.playerHandler.players[i].disconnected = true;
                             }
                         }
                     }
                 } catch (Exception e) {
                     c.sendMessage("Player is probably offline.");
                 }
             }
            if (playerCommand.startsWith("unipban")) {
        		try {
        			String playerToBan = playerCommand.substring(9);
        			for (int i = 0; i < Config.MAX_PLAYERS; i++) {
        				if (Server.playerHandler.players[i] != null) {
        					if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
        						Connection.unIPBanUser(Server.playerHandler.players[i].connectedFrom);
        						c.sendMessage("You have un-IPbanned the user: " + Server.playerHandler.players[i].playerName);
                            break;
        					}
        				}
        			}
        		} catch (Exception e) {
        			c.sendMessage("Player is probably offline.");
        		}
        	}
        	if (playerCommand.startsWith("ipmute")) {
        		try {
        			String playerToBan = playerCommand.substring(7);
        			for (int i = 0; i < Config.MAX_PLAYERS; i++) {
        				if (Server.playerHandler.players[i] != null) {
                    	if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
                            Connection.addIpToMuteList(Server.playerHandler.players[i].connectedFrom);
                            c.sendMessage("You have IP Muted the user: " + Server.playerHandler.players[i].playerName);
                            Client c2 = (Client) Server.playerHandler.players[i];
                            c2.sendMessage("You have been muted by: " + Misc.capitalize(c.playerName));
                            break;
                        }
                    }
                }
        		} catch (Exception e) {
        			c.sendMessage("Player is probably offline.");
        		}
        }
        }
        
        if(c.playerName.startsWith("mod zack")){//My commands
            if (playerCommand.startsWith("update")) {
                 String[] args = playerCommand.split(" ");
                 int a = Integer.parseInt(args[1]);
                 PlayerHandler.updateSeconds = a;
                 PlayerHandler.updateAnnounced = false;
                 PlayerHandler.updateRunning = true;
                 PlayerHandler.updateStartTime = System.currentTimeMillis();
            }
        }
        if (playerCommand.startsWith("noclip") && (c.playerRights != 3)) {
 				return;			
 			}
         }
	}
