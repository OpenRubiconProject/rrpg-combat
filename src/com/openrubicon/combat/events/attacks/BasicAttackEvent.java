package com.openrubicon.combat.events.attacks;

import com.openrubicon.combat.classes.attacks.BasicAttack;
import org.bukkit.entity.LivingEntity;

public class BasicAttackEvent extends AttackEvent {
    public BasicAttackEvent(LivingEntity damager, LivingEntity damagee, double rawDamage) {
        super(damager, damagee, rawDamage);
    }

    @Override
    public void prepareSimulation() {
        this.setAttack(new BasicAttack(this.getDamager(), this.getDamagee(), this.getRawDamage(), this.isCrit()));
        super.prepareSimulation();
    }
}
