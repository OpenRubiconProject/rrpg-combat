package com.openrubicon.combat.events.attacks;

import org.bukkit.entity.LivingEntity;

public class SweepAttackEvent extends AttackEvent {
    public SweepAttackEvent(LivingEntity damager, LivingEntity damagee, double damage) {
        super(damager, damagee, damage);
    }
}
