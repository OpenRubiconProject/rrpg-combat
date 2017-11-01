package com.openrubicon.combat.events.attacks;

import com.openrubicon.combat.classes.attacks.ChannelAttack;
import org.bukkit.entity.LivingEntity;

public class ChannelAttackEvent extends AttackEvent {
    public ChannelAttackEvent(LivingEntity damager, LivingEntity damagee, double damage) {
        super(damager, damagee, damage);
    }

    @Override
    public void prepareSimulation() {
        this.setAttack(new ChannelAttack(this.getDamager(), this.getDamagee(), this.getRawDamage(), this.isCrit()));
        super.prepareSimulation();
    }
}
