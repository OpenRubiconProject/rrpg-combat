package com.openrubicon.combat.events.attacks;

import com.openrubicon.combat.classes.attacks.SweepAttack;
import org.bukkit.entity.LivingEntity;

public class SweepAttackEvent extends AttackEvent {
    public SweepAttackEvent(LivingEntity damager, LivingEntity damagee, double damage) {
        super(damager, damagee, damage);
    }

    @Override
    public void prepareSimulation() {
        this.setAttack(new SweepAttack(this.getDamager(), this.getDamagee(), this.getRawDamage(), this.isCrit()));
        super.prepareSimulation();
    }
}
