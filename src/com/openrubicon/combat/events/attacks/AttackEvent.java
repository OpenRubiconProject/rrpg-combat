package com.openrubicon.combat.events.attacks;

import com.openrubicon.combat.classes.attacks.Attack;
import com.openrubicon.core.api.events.Event;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;

abstract public class AttackEvent extends Event implements Cancellable {

    private LivingEntity damager;
    private LivingEntity damagee;

    private double rawDamage;

    private boolean crit = false;

    private boolean cancelled = false;

    private Attack attack;

    public AttackEvent(LivingEntity damager, LivingEntity damagee, double damage) {
        this.damager = damager;
        this.damagee = damagee;
        this.rawDamage = damage;
    }

    public LivingEntity getDamager() {
        return damager;
    }

    public LivingEntity getDamagee() {
        return damagee;
    }

    protected double getRawDamage() {
        return rawDamage;
    }

    public boolean isCrit() {
        return crit;
    }

    public void setCrit(boolean crit) {
        this.crit = crit;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    protected void setAttack(Attack attack) {
        this.attack = attack;
    }

    public Attack getAttack() {
        return attack;
    }

    public void prepareSimulation()
    {
        this.getAttack().prepareSimulatulation();
    }

    public void simulate()
    {
        this.attack.simulate();
    }
}
