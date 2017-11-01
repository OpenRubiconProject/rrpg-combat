package com.openrubicon.combat.events.attacks;

import org.bukkit.entity.LivingEntity;

public class ChannelAttackEvent extends AttackEvent {
    public ChannelAttackEvent(LivingEntity damager, LivingEntity damagee, double damage) {
        super(damager, damagee, damage);
    }
}
