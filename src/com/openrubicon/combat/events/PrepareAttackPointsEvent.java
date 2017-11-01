package com.openrubicon.combat.events;

import com.openrubicon.core.api.events.LivingEntityEvent;
import org.bukkit.entity.LivingEntity;

public class PrepareAttackPointsEvent extends LivingEntityEvent {

    double attackPoints;

    public PrepareAttackPointsEvent(LivingEntity livingEntity, double attackPoints) {
        super(livingEntity);
        this.attackPoints = attackPoints;
    }

    public double getAttackPoints() {
        return attackPoints;
    }

    public void setAttackPoints(double attackPoints) {
        this.attackPoints = attackPoints;
    }
}
