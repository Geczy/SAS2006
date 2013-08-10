package server.org.engine.item;

import server.org.Config;
import server.org.Server;
import server.org.engine.character.Client;

public class ItemManager {
	
	public static void dropAllItems(Client c) {
		Client o = (Client) Server.playerHandler.players[c.killerId];
		
		for(int i = 0; i < c.playerItems.length; i++) {
			if(o != null) {
				if (c.getItems().tradeable(c.playerItems[i] - 1)) {
					Server.itemHandler.createGroundItem(o, c.playerItems[i] -1, c.getX(), c.getY(), c.playerItemsN[i], c.killerId);
				} else {
					if (c.getItems().specialCase(c.playerItems[i] - 1))
						Server.itemHandler.createGroundItem(o, 995, c.getX(), c.getY(), c.getItems().getUntradePrice(c.playerItems[i]-1), c.killerId);
					Server.itemHandler.createGroundItem(c, c.playerItems[i] -1, c.getX(), c.getY(), c.playerItemsN[i], c.playerId);
				}
			} else {
				Server.itemHandler.createGroundItem(c, c.playerItems[i] -1, c.getX(), c.getY(), c.playerItemsN[i], c.playerId);
			}
		} 
		for(int e = 0; e < c.playerEquipment.length; e++) {
			if(o != null) {
				if (c.getItems().tradeable(c.playerEquipment[e])) {
					Server.itemHandler.createGroundItem(o, c.playerEquipment[e], c.getX(), c.getY(), c.playerEquipmentN[e], c.killerId);
				} else {
					if (c.getItems().specialCase(c.playerEquipment[e]))
						Server.itemHandler.createGroundItem(o, 995, c.getX(), c.getY(), c.getItems().getUntradePrice(c.playerEquipment[e]), c.killerId);
					Server.itemHandler.createGroundItem(c, c.playerEquipment[e], c.getX(), c.getY(), c.playerEquipmentN[e], c.playerId);
				}
			} else {
				Server.itemHandler.createGroundItem(c, c.playerEquipment[e], c.getX(), c.getY(), c.playerEquipmentN[e], c.playerId);
			}
		}
		if(o != null) {	
			Server.itemHandler.createGroundItem(o, 526, c.getX(), c.getY(), 1, c.killerId);
		}	
	}
	public static void deleteAllItems(Client c) {	
		for(int i1 = 0; i1 < c.playerEquipment.length; i1++) {
			c.getItems().deleteEquipment(c.playerEquipment[i1], i1);
		}
		for(int i = 0; i < c.playerItems.length; i++) {
			c.getItems().deleteItem(c.playerItems[i]-1, c.getItems().getItemSlot(c.playerItems[i]-1), c.playerItemsN[i]);
		}
	}
	public static void resetKeepItems(Client c) {
		for(int i = 0; i < c.itemKeptId.length; i++) {
			c.itemKeptId[i] = -1;
		}
		for(int i1 = 0; i1 < c.invSlot.length; i1++) {
			c.invSlot[i1] = false;
		}
		for(int i2 = 0; i2 < c.equipSlot.length; i2++) {
			c.equipSlot[i2] = false;
		}		
	}

	public static void keepItem(Client c,int keepItem, boolean deleteItem) { 	
		int value = 0;
		int item = 0;
		int slotId = 0;
		boolean itemInInventory = false;
		for(int i = 0; i < c.playerItems.length; i++) {
			if(c.playerItems[i]-1 > 0) {
				int inventoryItemValue = c.getShops().getItemShopValue(c.playerItems[i] - 1);
				if(inventoryItemValue > value && (!c.invSlot[i])) {
					value = inventoryItemValue;
					item = c.playerItems[i] - 1;
					slotId = i;
					itemInInventory = true;			
				}
			}
		}
		for(int i1 = 0; i1 < c.playerEquipment.length; i1++) {
			if(c.playerEquipment[i1] > 0) {
				int equipmentItemValue = c.getShops().getItemShopValue(c.playerEquipment[i1]);
				if(equipmentItemValue > value && (!c.equipSlot[i1])) {
					value = equipmentItemValue;
					item = c.playerEquipment[i1];
					slotId = i1;
					itemInInventory = false;			
				}
			}
		}	
		if(itemInInventory) {
			c.invSlot[slotId] = true;
			if(deleteItem) {					
				c.getItems().deleteItem(c.playerItems[slotId]-1, c.getItems().getItemSlot(c.playerItems[slotId]-1), 1);
			}
		} else {
			c.equipSlot[slotId] = true;
			if(deleteItem) {
				c.getItems().deleteEquipment(item, slotId);
			}		
		}
		c.itemKeptId[keepItem] = item;	
	}
	public static boolean addItem(Client c,int item, int amount) {
		synchronized(c) {
			if (amount < 1) {
				amount = 1;
			}
			if(item <= 0) {
				return false;
			}
			if ((((c.getItems().freeSlots() >= 1) || c.getItems().playerHasItem(item, 1)) && Item.itemStackable[item]) || ((c.getItems().freeSlots() > 0) && !Item.itemStackable[item])) {
				for (int i = 0; i < c.playerItems.length; i++) {
					if ((c.playerItems[i] == (item + 1)) && Item.itemStackable[item]
							&& (c.playerItems[i] > 0)) {
						c.playerItems[i] = (item + 1);
						if (((c.playerItemsN[i] + amount) < Config.MAXITEM_AMOUNT)
								&& ((c.playerItemsN[i] + amount) > -1)) {
							c.playerItemsN[i] += amount;
						} else {
							c.playerItemsN[i] = Config.MAXITEM_AMOUNT;
						}
						if(c.getOutStream() != null && c != null ) {
							c.getOutStream().createFrameVarSizeWord(34);
							c.getOutStream().writeWord(3214);
							c.getOutStream().writeByte(i);
							c.getOutStream().writeWord(c.playerItems[i]);
							if (c.playerItemsN[i] > 254) {
								c.getOutStream().writeByte(255);
								c.getOutStream().writeDWord(c.playerItemsN[i]);
							} else {
								c.getOutStream().writeByte(c.playerItemsN[i]);
							}
							c.getOutStream().endFrameVarSizeWord();
							c.flushOutStream();
						}
						i = 30;
						return true;
					}
				}
				for (int i = 0; i < c.playerItems.length; i++) {
					if (c.playerItems[i] <= 0) {
						c.playerItems[i] = item + 1;
						if ((amount < Config.MAXITEM_AMOUNT) && (amount > -1)) {
							c.playerItemsN[i] = 1;
							if (amount > 1) {
								c.getItems().addItem(item, amount - 1);
								return true;
							}
						} else {
							c.playerItemsN[i] = Config.MAXITEM_AMOUNT;
						}
						/*if(c.getOutStream() != null && c != null ) {
							c.getOutStream().createFrameVarSizeWord(34);
							c.getOutStream().writeWord(3214);
							c.getOutStream().writeByte(i);
							c.getOutStream().writeWord(c.playerItems[i]);
							if (c.playerItemsN[i] > 254) {
								c.getOutStream().writeByte(255);
								c.getOutStream().writeDWord(c.playerItemsN[i]);
							} else {
								c.getOutStream().writeByte(c.playerItemsN[i]);
							}
							c.getOutStream().endFrameVarSizeWord();
							c.flushOutStream();
						}*/
						c.getItems().resetItems(3214);
						i = 30;
						return true;
					}
				}
				return false;
			} else {
				c.getItems().resetItems(3214);
				c.sendMessage("Not enough space in your inventory.");
				return false;
			}
		}
	}
	
}
