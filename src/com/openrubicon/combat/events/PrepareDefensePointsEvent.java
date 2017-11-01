package com.openrubicon.combat.events;

import com.openrubicon.core.api.events.LivingEntityEvent;
import org.bukkit.entity.LivingEntity;

public class PrepareDefensePointsEvent extends LivingEntityEvent {

    double defensePoints;

    public PrepareDefensePointsEvent(LivingEntity livingEntity, double defensePoints) {
        super(livingEntity);
        this.defensePoints = defensePoints;
    }

    public double getDefensePoints() {
        return defensePoints;
    }

    public void setDefensePoints(double defensePoints) {
        this.defensePoints = defensePoints;
    }
}
