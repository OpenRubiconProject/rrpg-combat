package com.openrubicon.combat.events.attacks;

import com.openrubicon.core.api.events.Event;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.Cancellable;

abstract public class AttackEvent extends Event implements Cancellable {

    private LivingEntity damager;
    private LivingEntity damagee;

    private double damage;

    private boolean blocked = false;
    private boolean cancelled = true;

    public AttackEvent(LivingEntity damager, LivingEntity damagee, double damage) {
        this.damager = damager;
        this.damagee = damagee;
        this.damage = damage;
    }

    public LivingEntity getDamager() {
        return damager;
    }

    public LivingEntity getDamagee() {
        return damagee;
    }

    public double getDamage() {
        return damage;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
