package com.openrubicon.combat.classes.attacks;

import com.openrubicon.combat.classes.ItemCombatConstants;
import com.openrubicon.combat.events.PrepareAttackPointsEvent;
import com.openrubicon.combat.events.PrepareDefensePointsEvent;
import com.openrubicon.core.api.actionbar.interfaces.Actionbarable;
import com.openrubicon.core.api.interfaces.Metable;
import com.openrubicon.core.api.interfaces.Observeable;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import org.bukkit.Bukkit;
import org.bukkit.entity.Horse;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.HorseInventory;
import org.bukkit.inventory.ItemStack;

abstract public class Attack implements Metable, Observeable, Actionbarable {
    private double rawDamage = 0;
    private double finalDamage = 0;

    private double attackPoints = 0;
    private double defensePoints = 0;

    private boolean crit = false;

    private boolean blocked = false;

    private LivingEntity damager;
    private LivingEntity damagee;

    public Attack(LivingEntity damager, LivingEntity damagee, double rawDamage, boolean crit) {
        this.rawDamage = rawDamage;
        this.damager = damager;
        this.damagee = damagee;
        this.crit = crit;
    }

    public double getRawDamage() {
        return rawDamage;
    }

    public double getFinalDamage() {
        return finalDamage;
    }

    public void setFinalDamage(double finalDamage) {
        this.finalDamage = finalDamage;
    }

    public double getAttackPoints() {
        return attackPoints;
    }

    public void setAttackPoints(double attackPoints) {
        this.attackPoints = attackPoints;
    }

    public double getDefensePoints() {
        return defensePoints;
    }

    public void setDefensePoints(double defensePoints) {
        this.defensePoints = defensePoints;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public LivingEntity getDamager() {
        return damager;
    }

    public LivingEntity getDamagee() {
        return damagee;
    }

    public void prepareSimulatulation()
    {
        this.setDefense();
        this.setAttack();
    }

    abstract public void simulate();

    protected int getScaledWeaponDebuff(ItemStack item, int max)
    {
        return (int) Helpers.scale(ItemCombatConstants.getWeaponTypeDebuff(item), 0, 6, 0, max);
    }

    protected int getScaledWeaponDebuff(ItemStack item)
    {
        return this.getScaledWeaponDebuff(item, 16);
    }

    public void setDefense()
    {
        //TODO: Add special item power level factor

        EntityEquipment entityEquipment = this.getDamagee().getEquipment();

        if(this.getDamagee() instanceof Horse && entityEquipment != null) {
            Horse horse = (Horse) this.getDamagee();
            HorseInventory horseInventory = horse.getInventory();
            entityEquipment.setChestplate(horseInventory.getArmor());
        }

        if (entityEquipment == null)
            return;

        UniqueItem helmet = new UniqueItem(entityEquipment.getHelmet());
        UniqueItem chestplate = new UniqueItem(entityEquipment.getChestplate());
        UniqueItem leggings = new UniqueItem(entityEquipment.getLeggings());
        UniqueItem boots = new UniqueItem(entityEquipment.getBoots());

        defensePoints += ItemCombatConstants.getDefenseLevel(helmet.getItem());
        defensePoints += 3 * ItemCombatConstants.getDefenseLevel(chestplate.getItem());
        defensePoints += 2 * ItemCombatConstants.getDefenseLevel(leggings.getItem());
        defensePoints += ItemCombatConstants.getDefenseLevel(boots.getItem());

        PrepareDefensePointsEvent prepareDefensePointsEvent = new PrepareDefensePointsEvent(this.getDamagee(), defensePoints);

        Bukkit.getPluginManager().callEvent(prepareDefensePointsEvent);

        defensePoints = prepareDefensePointsEvent.getDefensePoints();
    }

    public void setAttack()
    {
        //TODO: Add special item power level factor
        EntityEquipment entityEquipment = this.getDamager().getEquipment();

        ItemStack i = entityEquipment.getItemInMainHand();

        int total = ItemCombatConstants.getAttackLevel(i) * 7;
        total -= this.getScaledWeaponDebuff(i);

        if(total < 0)
            total = 0;

        double modifier = 1 / 12;

        UniqueItem item = new UniqueItem(i);
        if(item.isValid() && item.isSpecialItem())
        {
            modifier = (1 + item.getItemSpecs().getPower()) / 12;
        }

        total *= modifier;

        this.attackPoints = total;

        PrepareAttackPointsEvent prepareAttackPointsEvent = new PrepareAttackPointsEvent(this.getDamager(), attackPoints);

        Bukkit.getPluginManager().callEvent(prepareAttackPointsEvent);

        attackPoints = prepareAttackPointsEvent.getAttackPoints();
    }

    public int getPercentPiece()
    {
        int percentChance;

        if(defensePoints == 0)
            percentChance = 100;
        else
            percentChance = (int) ((attackPoints / defensePoints) * 100);

        if(percentChance > 100)
            percentChance = 100;
        if(percentChance < 0)
            percentChance = 0;

        return percentChance;
    }

}
