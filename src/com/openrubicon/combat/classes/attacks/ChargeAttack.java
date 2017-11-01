package com.openrubicon.combat.classes.attacks;

import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;

public class ChargeAttack extends BasicAttack {
    public ChargeAttack(LivingEntity damager, LivingEntity damagee, double rawDamage, boolean crit) {
        super(damager, damagee, rawDamage, crit);
    }

    @Override
    public String getName() {
        return "Charge Attack";
    }

    @Override
    public String getDescription() {
        return "Sprinting based attack";
    }

    @Override
    public ArrayList<String> getObservation() {
        return super.getObservation();
    }

    @Override
    public void simulate() {
        super.simulate();


    }
}
