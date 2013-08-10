
package server.org.engine.character.skilling.fletching;

import server.org.Config;
import server.org.engine.character.Client;
import server.org.engine.character.PlayerAssistant;

public class Fletching
{

	public static void startFletching( Client c, int amount )
	{
		if( c.fletchType <= 0 || c.fletchThis.equals( "" ) ) {
			return;
		}
		c.fletchAmount = amount;
	}


	public static void resetFletching( Client c )
	{
		c.getPA().removeAllWindows();
		c.fletchType = - 1;
		c.fletchAmount = - 1;
		c.fletchThis = "";
	}


	public static void headlessArrow( Client c )
	{
		if( c.getItems().playerHasItem( c.arrowShaft, 1 ) ) {
			if( c.getItems().playerHasItem( c.feather, 1 ) ) {
				int Slot = c.getItems().getItemSlot( c.arrowShaft ), amount = - 1, Slot2 = c.getItems().getItemSlot(
						c.feather ), amount2 = - 1;
				if( Slot != - 1 ) {
					amount = c.playerItemsN[Slot];
				}
				if( Slot2 != - 1 ) {
					amount2 = c.playerItemsN[Slot2];
				}
				if( amount >= 15 && amount2 >= 15 ) {
					c.getItems().deleteItem( c.arrowShaft, 15 );
					c.getItems().deleteItem( c.feather, 15 );
					c.getItems().addItem( c.headlessArrows, 15 );
					c.getPA().addSkillXP( 15 * Config.FLETCHING_EXPERIENCE, c.playerFletching );
				} else {
					c.getItems().deleteItem( c.arrowShaft, 1 );
					c.getItems().deleteItem( c.feather, 1 );
					c.getItems().addItem( c.headlessArrows, 1 );
					c.getPA().addSkillXP( 1 * Config.FLETCHING_EXPERIENCE, c.playerFletching );
				}
			} else {
				c.sendMessage( "You need feathers to make headless arrows!" );
			}
		} else {
			c.sendMessage( "You need arrow shafts to make headless arrows!" );
		}
		c.getPA().removeAllWindows();
	}


	public static void arrows( Client c, int fletchLevel )
	{
		if( FletchingHandler.Arrows.forId( c.fletchType ) != null ) {
			FletchingHandler.Arrows arrow = FletchingHandler.Arrows.forId( c.fletchType );
			if( fletchLevel >= arrow.getReq() ) {
				if( c.getItems().playerHasItem( arrow.getTips(), 1 ) ) {
					if( c.getItems().playerHasItem( c.headlessArrows, 1 ) ) {
						int Slot = c.getItems().getItemSlot( arrow.getTips() ), amount = - 1, Slot2 = c.getItems()
								.getItemSlot( c.headlessArrows ), amount2 = - 1;
						if( Slot != - 1 ) {
							amount = c.playerItemsN[Slot];
						}
						if( Slot2 != - 1 ) {
							amount2 = c.playerItemsN[Slot2];
						}
						if( amount >= 15 && amount2 >= 15 ) {
							c.getItems().deleteItem( arrow.getTips(), 15 );
							c.getItems().deleteItem( c.headlessArrows, 15 );
							c.getItems().addItem( arrow.getArrow(), 15 );
							c.getPA().addSkillXP( arrow.getExp() * Config.FLETCHING_EXPERIENCE * 15,
									c.playerFletching );
						} else {
							c.getItems().deleteItem( arrow.getTips(), 1 );
							c.getItems().deleteItem( c.headlessArrows, 1 );
							c.getItems().addItem( arrow.getArrow(), 1 );
							c.getPA().addSkillXP( arrow.getExp() * Config.FLETCHING_EXPERIENCE, c.playerFletching );
						}
					}
				}
			} else {
				c.getPA().removeAllWindows();
				PlayerAssistant.stopSkilling( c );
				c.sendMessage( "You need a Fletching level of " + arrow.getReq() + " to fletch a "
						+ c.getItems().getItemName( arrow.getArrow() ) );
			}
		} else {
			c.getPA().removeAllWindows();
			PlayerAssistant.stopSkilling( c );
		}
		c.getPA().removeAllWindows();
	}


	public static void shaft( Client c )
	{
		if( c.getItems().playerHasItem( c.fletchType, 1 ) ) {
			c.getItems().deleteItem( c.fletchType, 1 );
			c.getItems().addItem( c.arrowShaft, 15 );
			c.getPA().addSkillXP( 5 * Config.FLETCHING_EXPERIENCE, c.playerFletching );
			c.getPA().removeAllWindows();
		}
	}


	public static void stock( Client c, int fletchLevel )
	{
		if( FletchingHandler.CrossBow.forID( c.fletchType ) != null ) {
			FletchingHandler.CrossBow bow = FletchingHandler.CrossBow.forID( c.fletchType );
			if( c.getItems().playerHasItem( bow.getLog(), 1 ) ) {
				if( fletchLevel >= bow.getReq() ) {
					c.getItems().deleteItem( bow.getLog(), 1 );
					c.getItems().addItem( bow.getStock(), 1 );
					c.getPA().addSkillXP( bow.getExp1() * Config.FLETCHING_EXPERIENCE, c.playerFletching );
					c.getPA().removeAllWindows();
				} else {
					c.getPA().removeAllWindows();
					PlayerAssistant.stopSkilling( c );
					c.sendMessage( "You need a Fletching level of " + bow.getReq() + " to fletch a "
							+ c.getItems().getItemName( bow.getStock() ) );
				}
			}
		} else {
			c.getPA().removeAllWindows();
			PlayerAssistant.stopSkilling( c );
			c.sendMessage( "This log type is not yet supported." );
		}
	}


	public static void addLimbs( Client c, int fletchLevel )
	{
		if( FletchingHandler.CrossBow.forLimbID( c.fletchType ) != null ) {
			FletchingHandler.CrossBow bow = FletchingHandler.CrossBow.forLimbID( c.fletchType );
			if( c.getItems().playerHasItem( bow.getLimbs(), 1 ) ) {
				if( c.getItems().playerHasItem( bow.getStock(), 1 ) ) {
					if( fletchLevel >= bow.getReq() ) {
						c.getItems().deleteItem( bow.getLimbs(), 1 );
						c.getItems().deleteItem( bow.getStock(), 1 );
						c.getItems().addItem( bow.getBowU(), 1 );
						c.getPA().addSkillXP( bow.getExp2() * Config.FLETCHING_EXPERIENCE, c.playerFletching );
						c.getPA().removeAllWindows();
					} else {
						c.getPA().removeAllWindows();
						PlayerAssistant.stopSkilling( c );
						c.sendMessage( "You need a Fletching level of " + bow.getReq() + " to fletch a "
								+ c.getItems().getItemName( bow.getBowU() ) );
					}
				} else {
					c.getPA().removeAllWindows();
					PlayerAssistant.stopSkilling( c );
				}
			} else {
				c.getPA().removeAllWindows();
				PlayerAssistant.stopSkilling( c );
			}
		} else {
			c.getPA().removeAllWindows();
			PlayerAssistant.stopSkilling( c );
			c.sendMessage( "This log type is not yet supported." );
		}
	}


	public static void stringCrossbow( Client c, int fletchLevel )
	{
		if( FletchingHandler.CrossBow.forUID( c.fletchType ) != null ) {
			FletchingHandler.CrossBow bow = FletchingHandler.CrossBow.forUID( c.fletchType );
			if( c.getItems().playerHasItem( c.cBowString, 1 ) ) {
				if( c.getItems().playerHasItem( bow.getBowU(), 1 ) ) {
					if( fletchLevel >= bow.getReq() ) {
						c.getItems().deleteItem( bow.getBowU(), 1 );
						c.getItems().deleteItem( c.cBowString, 1 );
						c.getItems().addItem( bow.getBow(), 1 );
						c.getPA().addSkillXP( bow.getExp1() * Config.FLETCHING_EXPERIENCE, c.playerFletching );
						c.getPA().removeAllWindows();
					} else {
						c.getPA().removeAllWindows();
						PlayerAssistant.stopSkilling( c );
						c.sendMessage( "You need a Fletching level of " + bow.getReq() + " to string a "
								+ c.getItems().getItemName( bow.getBow() ) );
					}
				}
			} else {
				c.getPA().removeAllWindows();
				PlayerAssistant.stopSkilling( c );
			}
		}
	}


	public static void shortbow( Client c, int fletchLevel )
	{
		if( FletchingHandler.ShortBow.forID( c.fletchType ) != null ) {
			FletchingHandler.ShortBow bow = FletchingHandler.ShortBow.forID( c.fletchType );
			if( c.getItems().playerHasItem( bow.getLog(), 1 ) ) {
				if( fletchLevel >= bow.getReq() ) {
					c.getItems().deleteItem( bow.getLog(), 1 );
					c.getItems().addItem( bow.getBowU(), 1 );
					c.getPA().addSkillXP( bow.getExp() * Config.FLETCHING_EXPERIENCE, c.playerFletching );
					c.getPA().removeAllWindows();
				} else {
					c.getPA().removeAllWindows();
					PlayerAssistant.stopSkilling( c );
					c.sendMessage( "You need a Fletching level of " + bow.getReq() + " to fletch a "
							+ c.getItems().getItemName( bow.getBowU() ) );
				}
			}
		} else {
			c.getPA().removeAllWindows();
			PlayerAssistant.stopSkilling( c );
			c.sendMessage( "This log type is not yet supported." );
		}
	}


	public static void stringShortbow( Client c, int fletchLevel )
	{
		if( FletchingHandler.ShortBow.forUID( c.fletchType ) != null ) {
			FletchingHandler.ShortBow bow = FletchingHandler.ShortBow.forUID( c.fletchType );
			if( c.getItems().playerHasItem( c.bowString, 1 ) ) {
				if( c.getItems().playerHasItem( bow.getBowU(), 1 ) ) {
					if( fletchLevel >= bow.getReq() ) {
						c.getItems().deleteItem( bow.getBowU(), 1 );
						c.getItems().deleteItem( c.bowString, 1 );
						c.getItems().addItem( bow.getBow(), 1 );
						c.getPA().addSkillXP( bow.getExp() * Config.FLETCHING_EXPERIENCE, c.playerFletching );
						c.getPA().removeAllWindows();
					} else {
						c.getPA().removeAllWindows();
						PlayerAssistant.stopSkilling( c );
						c.sendMessage( "You need a Fletching level of " + bow.getReq() + " to string a "
								+ c.getItems().getItemName( bow.getBow() ) );
					}
				}
			} else {
				c.getPA().removeAllWindows();
				PlayerAssistant.stopSkilling( c );
			}
		}
	}


	public static void longbow( Client c, int fletchLevel )
	{
		if( FletchingHandler.LongBow.forID( c.fletchType ) != null ) {
			FletchingHandler.LongBow bow = FletchingHandler.LongBow.forID( c.fletchType );
			if( c.getItems().playerHasItem( bow.getLog(), 1 ) ) {
				if( fletchLevel >= bow.getReq() ) {
					c.getItems().deleteItem( bow.getLog(), 1 );
					c.getItems().addItem( bow.getBowU(), 1 );
					c.getPA().addSkillXP( bow.getExp() * Config.FLETCHING_EXPERIENCE, c.playerFletching );
					c.getPA().removeAllWindows();
				} else {
					c.getPA().removeAllWindows();
					PlayerAssistant.stopSkilling( c );
					c.sendMessage( "You need a Fletching level of " + bow.getReq() + " to fletch a "
							+ c.getItems().getItemName( bow.getBowU() ) );
				}
			}
		} else {
			c.getPA().removeAllWindows();
			PlayerAssistant.stopSkilling( c );
			c.sendMessage( "This log type is not yet supported." );
		}
	}


	public static void stringLongbow( Client c, int fletchLevel )
	{
		if( FletchingHandler.LongBow.forUID( c.fletchType ) != null ) {
			FletchingHandler.LongBow bow = FletchingHandler.LongBow.forUID( c.fletchType );
			if( c.getItems().playerHasItem( c.bowString, 1 ) ) {
				if( c.getItems().playerHasItem( bow.getBowU(), 1 ) ) {
					if( fletchLevel >= bow.getReq() ) {
						c.getItems().deleteItem( bow.getBowU(), 1 );
						c.getItems().deleteItem( c.bowString, 1 );
						c.getItems().addItem( bow.getBow(), 1 );
						c.getPA().addSkillXP( bow.getExp() * Config.FLETCHING_EXPERIENCE, c.playerFletching );
						c.getPA().removeAllWindows();
					} else {
						c.getPA().removeAllWindows();
						PlayerAssistant.stopSkilling( c );
						c.sendMessage( "You need a Fletching level of " + bow.getReq() + " to string a "
								+ c.getItems().getItemName( bow.getBow() ) );
					}
				}
			} else {
				c.getPA().removeAllWindows();
				PlayerAssistant.stopSkilling( c );
			}
		}
	}


	public static void appendDelay( Client c )
	{
		int fletchLevel = c.getPA().getLevelForXP( c.playerXP[c.playerFletching] );
		if( c.fletchAmount > 0 ) {
			c.fletchAmount -- ;
			if( c.fletchThis.equals( "headlessarrow" ) ) {
				headlessArrow( c );
			}
			if( c.fletchThis.equals( "arrows" ) ) {
				arrows( c, fletchLevel );
			}
			if( c.fletchThis.equals( "shaft" ) ) {
				shaft( c );
			}
			if( c.fletchThis.equals( "stock" ) ) {
				stock( c, fletchLevel );
			}
			if( c.fletchThis.equals( "limb" ) ) {
				addLimbs( c, fletchLevel );
			}
			if( c.fletchThis.equals( "stringCrossbow" ) ) {
				stringCrossbow( c, fletchLevel );
			}
			if( c.fletchThis.equals( "shortbow" ) ) {
				shortbow( c, fletchLevel );
			}
			if( c.fletchThis.equals( "stringShortbow" ) ) {
				stringShortbow( c, fletchLevel );
			}
			if( c.fletchThis.equals( "longbow" ) ) {
				longbow( c, fletchLevel );
			}
			if( c.fletchThis.equals( "stringLongbow" ) ) {
				stringLongbow( c, fletchLevel );
			}
		}
	}
}
