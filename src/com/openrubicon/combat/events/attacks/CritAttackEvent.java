package com.openrubicon.combat.events.attacks;

import org.bukkit.entity.LivingEntity;

public class CritAttackEvent extends AttackEvent {
    public CritAttackEvent(LivingEntity damager, LivingEntity damagee, double damage) {
        super(damager, damagee, damage);
    }
}
