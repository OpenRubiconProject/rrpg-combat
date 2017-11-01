package com.openrubicon.combat.events.attacks;

import com.openrubicon.combat.classes.attacks.ChargeAttack;
import org.bukkit.entity.LivingEntity;

public class ChargeAttackEvent extends AttackEvent {
    public ChargeAttackEvent(LivingEntity damager, LivingEntity damagee, double damage) {
        super(damager, damagee, damage);
    }

    @Override
    public void prepareSimulation() {
        this.setAttack(new ChargeAttack(this.getDamager(), this.getDamagee(), this.getRawDamage(), this.isCrit()));
        super.prepareSimulation();
    }
}
