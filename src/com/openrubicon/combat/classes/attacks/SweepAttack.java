package com.openrubicon.combat.classes.attacks;

import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;

public class SweepAttack extends BasicAttack {
    public SweepAttack(LivingEntity damager, LivingEntity damagee, double rawDamage, boolean crit) {
        super(damager, damagee, rawDamage, crit);
    }

    @Override
    public String getName() {
        return "Sweep Attack";
    }

    @Override
    public String getDescription() {
        return "Sweeping based attack";
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
