package server.org.world;

import server.org.engine.character.Client;

/**
 * @author Sanity
 */

public class Clan {

	public Clan(Client c, String name) {
		this.owner = c.playerName;
		this.name = name;
	}
		
	public int[] members = new int [50];
	public String name;
	public String owner;
	public boolean lootshare;
}