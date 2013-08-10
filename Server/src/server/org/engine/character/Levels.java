
package server.org.engine.character;

import server.org.engine.character.content.HighscoresConfig;

public class Levels
{

	public static void levelUp( Client c, int skill )
	{
		c.totalLevel = totalLevel( c );
		c.xpTotal = xpTotal( c );
		HighscoresConfig.updateHighscores( c );
		c.getPA().c.getPA().sendFrame126( "Total Lvl: " + c.totalLevel, 3984 );
		switch( skill ) {
			case 0:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced an attack level!", 6248 );
				c.getPA().c.getPA().sendFrame126(
						"Your attack level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 6249 );
				c.sendMessage( "Congratulations, you just advanced an Attack level." );
				c.getPA().c.getPA().sendFrame164( 6247 );
				break;

			case 1:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced a defence level!", 6254 );
				c.getPA().c.getPA().sendFrame126(
						"Your defence level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 6255 );
				c.sendMessage( "Congratulations, you just advanced a Defence level." );
				c.getPA().c.getPA().sendFrame164( 6253 );
				break;

			case 2:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced a strength level!", 6207 );
				c.getPA().c.getPA().sendFrame126(
						"Your strength level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 6208 );
				c.sendMessage( "Congratulations, you just advanced a Strength level." );
				c.getPA().c.getPA().sendFrame164( 6206 );
				break;

			case 3:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced a hitpoints level!", 6217 );
				c.getPA().c.getPA().sendFrame126(
						"Your hitpoints level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 6218 );
				c.sendMessage( "Congratulations, you just advanced a Hitpoints level." );
				c.getPA().c.getPA().sendFrame164( 6216 );
				break;

			case 4:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced a ranged level!", 5453 );
				c.getPA().c.getPA().sendFrame126(
						"Your ranged level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 6114 );
				c.sendMessage( "Congratulations, you just advanced a Ranged level." );
				c.getPA().c.getPA().sendFrame164( 4443 );
				break;

			case 5:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced a prayer level!", 6243 );
				c.getPA().c.getPA().sendFrame126(
						"Your prayer level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 6244 );
				c.sendMessage( "Congratulations, you just advanced a Prayer level." );
				c.getPA().refreshSkill( 5 );
				c.playerLevel[5] += 1;
				c.getPA().c.getPA().sendFrame164( 6242 );
				break;

			case 6:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced a magic level!", 6212 );
				c.getPA().c.getPA().sendFrame126(
						"Your magic level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 6213 );
				c.sendMessage( "Congratulations, you just advanced a Magic level." );
				c.getPA().c.getPA().sendFrame164( 6211 );
				break;

			case 7:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced a cooking level!", 6227 );
				c.getPA().c.getPA().sendFrame126(
						"Your cooking level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 6228 );
				c.sendMessage( "Congratulations, you just advanced a Cooking level." );
				c.getPA().c.getPA().sendFrame164( 6226 );
				break;

			case 8:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced a woodcutting level!", 4273 );
				c.getPA().c.getPA().sendFrame126(
						"Your woodcutting level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 4274 );
				c.sendMessage( "Congratulations, you just advanced a Woodcutting level." );
				c.getPA().c.getPA().sendFrame164( 4272 );
				break;

			case 9:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced a fletching level!", 6232 );
				c.getPA().c.getPA().sendFrame126(
						"Your fletching level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 6233 );
				c.sendMessage( "Congratulations, you just advanced a Fletching level." );
				c.getPA().c.getPA().sendFrame164( 6231 );
				break;

			case 10:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced a fishing level!", 6259 );
				c.getPA().c.getPA().sendFrame126(
						"Your fishing level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 6260 );
				c.sendMessage( "Congratulations, you just advanced a Fishing level." );
				c.getPA().c.getPA().sendFrame164( 6258 );
				break;

			case 11:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced a fire making level!", 4283 );
				c.getPA().c.getPA().sendFrame126(
						"Your firemaking level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 4284 );
				c.sendMessage( "Congratulations, you just advanced a Firemaking level." );
				c.getPA().c.getPA().sendFrame164( 4282 );
				break;

			case 12:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced a crafting level!", 6264 );
				c.getPA().c.getPA().sendFrame126(
						"Your crafting level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 6265 );
				c.sendMessage( "Congratulations, you just advanced a Crafting level." );
				c.getPA().c.getPA().sendFrame164( 6263 );
				break;

			case 13:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced a smithing level!", 6222 );
				c.getPA().c.getPA().sendFrame126(
						"Your smithing level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 6223 );
				c.sendMessage( "Congratulations, you just advanced a Smithing level." );
				c.getPA().c.getPA().sendFrame164( 6221 );
				break;

			case 14:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced a mining level!", 4417 );
				c.getPA().c.getPA().sendFrame126(
						"Your mining level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 4438 );
				c.sendMessage( "Congratulations, you just advanced a Mining level." );
				c.getPA().c.getPA().sendFrame164( 4416 );
				break;

			case 15:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced a herblore level!", 6238 );
				c.getPA().c.getPA().sendFrame126(
						"Your herblore level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 6239 );
				c.sendMessage( "Congratulations, you just advanced a Herblore level." );
				c.getPA().c.getPA().sendFrame164( 6237 );
				break;

			case 16:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced a agility level!", 4278 );
				c.getPA().c.getPA().sendFrame126(
						"Your agility level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 4279 );
				c.sendMessage( "Congratulations, you just advanced an Agility level." );
				c.getPA().c.getPA().sendFrame164( 4277 );
				break;

			case 17:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced a thieving level!", 4263 );
				c.getPA().c.getPA().sendFrame126(
						"Your thieving level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 4264 );
				c.sendMessage( "Congratulations, you just advanced a Thieving level." );
				c.getPA().c.getPA().sendFrame164( 4261 );
				break;

			case 18:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced a slayer level!", 12123 );
				c.getPA().c.getPA().sendFrame126(
						"Your slayer level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 12124 );
				c.sendMessage( "Congratulations, you just advanced a Slayer level." );
				c.getPA().c.getPA().sendFrame164( 12122 );
				break;

			case 20:
				c.getPA().c.getPA().sendFrame126( "Congratulations, you just advanced a runecrafting level!", 4268 );
				c.getPA().c.getPA().sendFrame126(
						"Your runecrafting level is now " + getLevelForXP( c.playerXP[skill] ) + ".", 4269 );
				c.sendMessage( "Congratulations, you just advanced a Runecrafting level." );
				c.getPA().c.getPA().sendFrame164( 4267 );
				break;
		}
		c.dialogueAction = 0;
		c.nextChat = 0;
	}


	public static void refreshSkill( Client c, int i )
	{
		switch( i ) {
			case 0:
				c.getPA().sendFrame126( "" + c.playerLevel[0] + "", 4004 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[0] ) + "", 4005 );
				c.getPA().sendFrame126( "" + c.playerXP[0] + "", 4044 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[0] ) + 1 ) + "", 4045 );
				break;

			case 1:
				c.getPA().sendFrame126( "" + c.playerLevel[1] + "", 4008 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[1] ) + "", 4009 );
				c.getPA().sendFrame126( "" + c.playerXP[1] + "", 4056 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[1] ) + 1 ) + "", 4057 );
				break;

			case 2:
				c.getPA().sendFrame126( "" + c.playerLevel[2] + "", 4006 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[2] ) + "", 4007 );
				c.getPA().sendFrame126( "" + c.playerXP[2] + "", 4050 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[2] ) + 1 ) + "", 4051 );
				break;

			case 3:
				c.getPA().sendFrame126( "" + c.playerLevel[3] + "", 4016 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[3] ) + "", 4017 );
				c.getPA().sendFrame126( "" + c.playerXP[3] + "", 4080 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[3] ) + 1 ) + "", 4081 );
				break;

			case 4:
				c.getPA().sendFrame126( "" + c.playerLevel[4] + "", 4010 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[4] ) + "", 4011 );
				c.getPA().sendFrame126( "" + c.playerXP[4] + "", 4062 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[4] ) + 1 ) + "", 4063 );
				break;

			case 5:
				c.getPA().sendFrame126( "" + c.playerLevel[5] + "", 4012 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[5] ) + "", 4013 );
				c.getPA().sendFrame126( "" + c.playerXP[5] + "", 4068 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[5] ) + 1 ) + "", 4069 );
				c.getPA().sendFrame126( "" + c.playerLevel[5] + "/" + getLevelForXP( c.playerXP[5] ) + "", 687 );// Prayer
																													// frame
				break;

			case 6:
				c.getPA().sendFrame126( "" + c.playerLevel[6] + "", 4014 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[6] ) + "", 4015 );
				c.getPA().sendFrame126( "" + c.playerXP[6] + "", 4074 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[6] ) + 1 ) + "", 4075 );
				break;

			case 7:
				c.getPA().sendFrame126( "" + c.playerLevel[7] + "", 4034 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[7] ) + "", 4035 );
				c.getPA().sendFrame126( "" + c.playerXP[7] + "", 4134 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[7] ) + 1 ) + "", 4135 );
				break;

			case 8:
				c.getPA().sendFrame126( "" + c.playerLevel[8] + "", 4038 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[8] ) + "", 4039 );
				c.getPA().sendFrame126( "" + c.playerXP[8] + "", 4146 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[8] ) + 1 ) + "", 4147 );
				break;

			case 9:
				c.getPA().sendFrame126( "" + c.playerLevel[9] + "", 4026 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[9] ) + "", 4027 );
				c.getPA().sendFrame126( "" + c.playerXP[9] + "", 4110 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[9] ) + 1 ) + "", 4111 );
				break;

			case 10:
				c.getPA().sendFrame126( "" + c.playerLevel[10] + "", 4032 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[10] ) + "", 4033 );
				c.getPA().sendFrame126( "" + c.playerXP[10] + "", 4128 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[10] ) + 1 ) + "", 4129 );
				break;

			case 11:
				c.getPA().sendFrame126( "" + c.playerLevel[11] + "", 4036 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[11] ) + "", 4037 );
				c.getPA().sendFrame126( "" + c.playerXP[11] + "", 4140 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[11] ) + 1 ) + "", 4141 );
				break;

			case 12:
				c.getPA().sendFrame126( "" + c.playerLevel[12] + "", 4024 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[12] ) + "", 4025 );
				c.getPA().sendFrame126( "" + c.playerXP[12] + "", 4104 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[12] ) + 1 ) + "", 4105 );
				break;

			case 13:
				c.getPA().sendFrame126( "" + c.playerLevel[13] + "", 4030 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[13] ) + "", 4031 );
				c.getPA().sendFrame126( "" + c.playerXP[13] + "", 4122 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[13] ) + 1 ) + "", 4123 );
				break;

			case 14:
				c.getPA().sendFrame126( "" + c.playerLevel[14] + "", 4028 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[14] ) + "", 4029 );
				c.getPA().sendFrame126( "" + c.playerXP[14] + "", 4116 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[14] ) + 1 ) + "", 4117 );
				break;

			case 15:
				c.getPA().sendFrame126( "" + c.playerLevel[15] + "", 4020 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[15] ) + "", 4021 );
				c.getPA().sendFrame126( "" + c.playerXP[15] + "", 4092 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[15] ) + 1 ) + "", 4093 );
				break;

			case 16:
				c.getPA().sendFrame126( "" + c.playerLevel[16] + "", 4018 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[16] ) + "", 4019 );
				c.getPA().sendFrame126( "" + c.playerXP[16] + "", 4086 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[16] ) + 1 ) + "", 4087 );
				break;

			case 17:
				c.getPA().sendFrame126( "" + c.playerLevel[17] + "", 4022 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[17] ) + "", 4023 );
				c.getPA().sendFrame126( "" + c.playerXP[17] + "", 4098 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[17] ) + 1 ) + "", 4099 );
				break;

			case 18:
				c.getPA().sendFrame126( "" + c.playerLevel[18] + "", 12166 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[18] ) + "", 12167 );
				c.getPA().sendFrame126( "" + c.playerXP[18] + "", 12171 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[18] ) + 1 ) + "", 12172 );
				break;

			case 19:
				c.getPA().sendFrame126( "" + c.playerLevel[19] + "", 13926 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[19] ) + "", 13927 );
				c.getPA().sendFrame126( "" + c.playerXP[19] + "", 13921 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[19] ) + 1 ) + "", 13922 );
				break;

			case 20:
				c.getPA().sendFrame126( "" + c.playerLevel[20] + "", 4152 );
				c.getPA().sendFrame126( "" + getLevelForXP( c.playerXP[20] ) + "", 4153 );
				c.getPA().sendFrame126( "" + c.playerXP[20] + "", 4157 );
				c.getPA().sendFrame126( "" + getXPForLevel( getLevelForXP( c.playerXP[20] ) + 1 ) + "", 4158 );
				break;
		}
	}


	public static int getXPForLevel( int level )
	{
		int points = 0;
		int output = 0;

		for( int lvl = 1; lvl <= level; lvl ++ ) {
			points += Math.floor( lvl + 300.0 * Math.pow( 2.0, lvl / 7.0 ) );
			if( lvl >= level ) {
				return output;
			}
			output = ( int )Math.floor( points / 4 );
		}
		return 0;
	}


	public static int getLevelForXP( int exp )
	{
		int points = 0;
		int output = 0;
		if( exp > 13034430 ) {
			return 99;
		}
		for( int lvl = 1; lvl <= 99; lvl ++ ) {
			points += Math.floor( lvl + 300.0
					* Math.pow( 2.0, lvl / 7.0 ) );
			output = ( int )Math.floor( points / 4 );
			if( output >= exp ) {
				return lvl;
			}
		}
		return 0;
	}


	public static int totalLevel( Client c )
	{
		int total = 0;
		for( int i = 0; i <= 20; i ++ ) {
			total += getLevelForXP( c.playerXP[i] );
		}
		return total;
	}


	public static int xpTotal( Client c )
	{
		int xp = 0;
		for( int i = 0; i <= 20; i ++ ) {
			xp += c.playerXP[i];
		}
		return xp;
	}


	public static boolean addSkillXP( Client c, int amount, int skill )
	{
		if( amount + c.playerXP[skill] < 0 || c.playerXP[skill] > 200000000 ) {
			if( c.playerXP[skill] > 200000000 ) {
				c.playerXP[skill] = 200000000;
			}
			return false;
		}
		if( c.mode == 1 ) {
			amount *= 250;
		} else if( c.mode == 2 ) {
			amount *= 100;
		} else if( c.mode == 3 ) {
			amount *= 40;
		} else if( c.mode == 4 ) {
			amount *= 10;
		} else if( c.mode == 5 ) {
			amount *= 2;
		}
		int oldLevel = getLevelForXP( c.playerXP[skill] );
		c.playerXP[skill] += amount;
		if( oldLevel < getLevelForXP( c.playerXP[skill] ) ) {
			if( c.playerLevel[skill] < c.getLevelForXP( c.playerXP[skill] ) && skill != 3 && skill != 5 ) {
				c.playerLevel[skill] = c.getLevelForXP( c.playerXP[skill] );
			}
			levelUp( c, skill );
			c.gfx100( 199 );
			c.getPA().requestUpdates();
		}
		setSkillLevel( c, skill, c.playerLevel[skill], c.playerXP[skill] );
		refreshSkill( c, skill );
		return true;
	}


	public static void setSkillLevel( Client c, int skillNum, int currentLevel, int XP )
	{
		synchronized( c ) {
			if( c.getOutStream() != null && c != null ) {
				c.getOutStream().createFrame( 134 );
				c.getOutStream().writeByte( skillNum );
				c.getOutStream().writeDWord_v1( XP );
				c.getOutStream().writeByte( currentLevel );
				c.flushOutStream();
			}
		}
	}

}
