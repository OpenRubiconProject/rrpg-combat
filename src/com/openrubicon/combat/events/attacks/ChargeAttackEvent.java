package com.openrubicon.combat.events.attacks;

import org.bukkit.entity.LivingEntity;

public class ChargeAttackEvent extends AttackEvent {
    public ChargeAttackEvent(LivingEntity damager, LivingEntity damagee, double damage) {
        super(damager, damagee, damage);
    }
}
