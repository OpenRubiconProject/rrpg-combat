package com.openrubicon.combat.events.attacks;

import org.bukkit.entity.LivingEntity;

public class BasicAttackEvent extends AttackEvent {
    public BasicAttackEvent(LivingEntity damager, LivingEntity damagee, double damage) {
        super(damager, damagee, damage);
    }
}
