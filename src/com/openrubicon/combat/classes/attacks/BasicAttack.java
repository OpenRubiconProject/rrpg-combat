package com.openrubicon.combat.classes.attacks;

import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.Helpers;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;

public class BasicAttack extends Attack {

    public BasicAttack(LivingEntity damager, LivingEntity damagee, double rawDamage, boolean crit) {
        super(damager, damagee, rawDamage, crit);
    }

    @Override
    public String getName() {
        return "Basic Attack";
    }

    @Override
    public String getDescription() {
        return "An attack without sweeping, sprinting, or crouching.. A basic attack.";
    }

    @Override
    public ArrayList<String> getObservation() {
        return new ArrayList<>();
    }

    @Override
    public void simulate() {
        if(this.getAttackPoints() >= this.getDefensePoints())
        {
            this.setBlocked(false);
            this.setFinalDamage(this.getRawDamage());

            return;
        }

        int blockChance = Helpers.randomInt(0, (int)this.getDefensePoints() + 1);

        if(this.getAttackPoints() >= blockChance)
        {
            this.setBlocked(false);
            this.setFinalDamage(this.getRawDamage());
        } else {
            this.setBlocked(true);
            this.setFinalDamage(0);
        }
    }

    @Override
    public String getActionbarText() {
        return Helpers.colorize(Constants.PRIMARY_COLOR + "Pierce Chance: " + Constants.SECONDARY_COLOR + this.getPercentPiece() + "% " + Constants.PRIMARY_COLOR + "DMG On Piece: " + Constants.SECONDARY_COLOR + this.getFinalDamage());
    }
}
