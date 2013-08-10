package server.org.engine.character.combat;

import server.org.Config;
import server.org.Server;
import server.org.core.util.Misc;
import server.org.engine.character.Client;
import server.org.engine.character.Player;
import server.org.engine.character.combat.attack.AttackNpcs;
import server.org.engine.character.combat.attack.AttackPlayers;
import server.org.engine.character.combat.attack.Reset;
import server.org.engine.character.combat.attack.SpecialAttack;
import server.org.engine.character.combat.attack.SpecialHandler;
import server.org.engine.character.combat.magic.CheckMagic;
import server.org.engine.character.combat.magic.GetMagic;
import server.org.engine.character.combat.magic.MagicCombat;
import server.org.engine.character.combat.magic.MagicSpell;
import server.org.engine.character.combat.melee.MeleeCombat;
import server.org.engine.character.combat.prayer.PrayerActivate;
import server.org.engine.character.combat.prayer.PrayerHandler;
import server.org.engine.character.combat.ranged.Projectile;
import server.org.engine.character.combat.ranged.RangeCombat;

/**
 * CombatAssistant.java
 *
 * @author Sanity
 * @author Izumi
 *
 */

public class CombatAssistant{
	
	
	private Client c;
	public CombatAssistant(Client Client) {
		this.c = Client;
	}
	public void attackNpc(int i) {		
		AttackNpcs.attackNpc(c, i);
	}
	public void delayedHit(int i) {
		DelayHit.delayedHit(c, i);
	}
	public void applyNpcMeleeDamage(int i, int damageMask) {
		ApplyHit.applyNpcMeleeDamage(c,i,damageMask);
	}
	public void fireProjectileNpc() {
		CombatData.fireProjectileNpc(c);
	}
	public void attackPlayer(int i) {
		AttackPlayers.attackPlayer(c, i);
	}
	public boolean usingCrystalBow() {
		return CombatData.usingCrystalBow(c);	
	}
	public boolean properBolts() {
		return CombatData.properBolts(c);
	}
	public void playerDelayedHit(int i) {
		DelayHit.playerDelayedHit(c,i);
	}
	public boolean multis() {
		return MagicSpell.multis(c);
	}
	public void appendMultiBarrage(int playerId, boolean splashed) {
		MagicSpell.appendMultiBarrage(c, playerId, splashed);
	}
	public void multiSpellEffect(int playerId, int damage) {					
		MagicSpell.multiSpellEffect(c, playerId, damage);
	}
	public void applyPlayerMeleeDamage(int i, int damageMask){
		ApplyHit.applyPlayerMeleeDamage(c, i, damageMask);
	}
	public void applySmite(int index, int damage) {
		CombatData.applySmite(c, index, damage);
	}
	public void fireProjectilePlayer() {
		CombatData.fireProjectilePlayer(c);
	}
	public void activatePrayer(int i) {
		PrayerActivate.activatePrayer(c, i);
	}
	public void activateSpecial(int weapon, int i){
		SpecialAttack.activateSpecial(c, weapon, i);
	}
	public boolean checkSpecAmount(int weapon) {
		return SpecialHandler.checkSpecAmount(c, weapon);
	}
	public void resetPlayerAttack() {
			Reset.resetPlayerAttack(c);
	}
	public int getCombatDifference(int combat1, int combat2) {
		return CombatData.getCombatDifference(combat1, combat2);
	}
	public int getKillerId(int playerId) {
		return CombatData.getKillerId(c, playerId);
	}
	public void handlePrayerDrain() {
		PrayerHandler.handlePrayerDrain(c);
	}
	public void reducePrayerLevel() {
		PrayerHandler.reducePrayerLevel(c);
	}
	public void resetPrayers() {
		PrayerHandler.resetPrayers(c);
	}
	public boolean checkReqs() {
		return CheckMagic.checkReqs(c);
	}
	public boolean checkMultiBarrageReqs(int i) {
			return CheckMagic.checkMultiBarrageReqs(c, i);
	}
	public void getPlayerAnimIndex(String weaponName){
		PlayerEmotes.getPlayerAnimIndex(c, weaponName);
	}
	public int getWepAnim(String weaponName) {
		return PlayerEmotes.getWepAnim(c, weaponName);
	}
	public int getBlockEmote() {
		return PlayerEmotes.getBlockEmote(c);
	}
	public int getAttackDelay(String s) {
		return DelayHit.getAttackDelay(c, s);
	}
	public int getHitDelay(String weaponName) {
		return DelayHit.getHitDelay(c, weaponName);
	}
	public int getRequiredDistance() {
		return CombatData.getRequiredDistance(c);
	}
	public boolean usingHally() {
		return CombatData.usingHally(c);
	}
	public int calculateMeleeAttack() {
		return MeleeCombat.calculateMeleeAttack(c);
	}
	public int bestMeleeAtk(){
        return MeleeCombat.bestMeleeAtk(c);
    }
	public int calculateMeleeMaxHit() {
		return MeleeCombat.calculateMeleeMaxHit(c);
	}
	public int calculateMeleeDefence() {
		return MeleeCombat.calculateMeleeDefence(c);
    }
	public int bestMeleeDef()  {
		return MeleeCombat.bestMeleeDef(c);
    }
	public int calculateRangeAttack() {
		return RangeCombat.calculateRangeAttack(c);
	}
	public int calculateRangeDefence() {
		return RangeCombat.calculateRangeDefence(c);
	}
	public int rangeMaxHit() {
		return RangeCombat.rangeMaxHit(c);
	}
	public int getRangeStr(int i) {
		return RangeCombat.getRangeStr(c, i);
	}
	public int correctBowAndArrows() {
		return RangeCombat.correctBowAndArrows(c);
	}
	public int getRangeStartGFX() {
		return Projectile.getRangeStartGFX(c);
	}
	public int getRangeProjectileGFX() {
		return Projectile.getRangeProjectileGFX(c);
	}
	public int getProjectileSpeed() {
		return Projectile.getProjectileSpeed(c);
	}
	public int getProjectileShowDelay() {
		return Projectile.getProjectileShowDelay(c);
	}
	public int mageAtk() {
        return MagicCombat.mageAtk(c);
    }
	public int mageDef() {
		return MagicCombat.mageDef(c);
    }
	public boolean wearingStaff(int runeId) {
		return CheckMagic.wearingStaff(c, runeId);
	}
	public boolean checkMagicReqs(int spell) {
		return CheckMagic.checkMagicReqs(c, spell);
	}
	public int getFreezeTime() {
		return GetMagic.getFreezeTime(c);
	}
	public int getStartHeight() {
		return GetMagic.getStartHeight(c);
	}
	public int getEndHeight() {
		return GetMagic.getEndHeight(c);
	}
	public int getStartDelay() {
		return GetMagic.getStartDelay(c);
	}
	public int getStaffNeeded() {
		return GetMagic.getStaffNeeded(c);
	}
	public boolean godSpells() {
		return MagicSpell.godSpells(c);
	}
	public int getEndGfxHeight() {
		return GetMagic.getEndGfxHeight(c);
	}
	public int getStartGfxHeight() {
		return GetMagic.getStartGfxHeight(c);
	}
	public void applyRecoil(int damage, int i) {
		CombatData.applyRecoil(c, damage, i);
	}
	
	public int getBonusAttack(int i) {
		return ApplyHit.getBonusAttack(c, i);
	}
	
	public void handleGmaulPlayer() {
		CombatData.handleGmaulPlayer(c);
	}
	public int[][] slayerReqs = {{1648,5},{1612,15},{1643,45},{1618,50},{1624,65},{1610,75},{1613,80},{1615,85},{2783,90}};
	
	public boolean goodSlayer(int i) {
		for (int j = 0; j < slayerReqs.length; j++) {
			if (slayerReqs[j][0] == Server.npcHandler.npcs[i].npcType) {
				if (slayerReqs[j][1] > c.playerLevel[c.playerSlayer]) {
					c.sendMessage("You need a slayer level of " + slayerReqs[j][1] + " to attack this monster.");
					return false;
				}
			}
		}
		return true;
	}
	public boolean armaNpc(int i) {
		return CombatData.armaNpc(i);
	}
	
}