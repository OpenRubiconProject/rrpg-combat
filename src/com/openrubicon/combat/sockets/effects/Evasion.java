package com.openrubicon.combat.sockets.effects;

import com.openrubicon.combat.events.attacks.PrepareAttackApplicationEvent;
import com.openrubicon.combat.sockets.CombatCooldownSocket;
import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

import java.util.HashSet;

public class Evasion extends CombatCooldownSocket {
    public double force = 5;

    @Override
    public String getKey() {
        return "evasion";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.LEGGINGS;
    }

    @Override
    public String getName() {
        return "Evasion";
    }

    @Override
    public String getDescription() {
        return "Upon taking a killing blow, fire straight up into the air, negating the killing blow damage.";
    }

    @Override
    public boolean generate() {
        super.generate();

        double min = 1;
        double max = this.getItemSpecs().getPower() * this.getItemSpecs().getRarity();
        if (max <= min)
            max = 2;
        this.force = (int) Helpers.scale(Helpers.randomDouble(min, max), min, 121, 5, 10);

        return true;
    }

    @Override
    public int getCooldownLengthTicks() {
        return Helpers.secondsToTicks(10);
    }

    @Override
    public boolean save() {

        this.getSocketProperties().addDouble("force", this.force);
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        this.force = this.getSocketProperties().getDouble("force");

        return true;
    }

    @Override
    public void onPrepareAttackApplication(PrepareAttackApplicationEvent e, UniqueItem item, EntityInventorySlotType slot) {
        if(this.isOnCooldown())
            return;

        if(item.getWhoCurrentlyHas() != e.getAttackEvent().getDamagee())
            return;

        if (e.getAttackEvent().getDamagee().getHealth() - e.getAttackEvent().getAttack().getFinalDamage() >= 1)
            return;

        e.getAttackEvent().setCancelled(true);

        Vector velocity = e.getAttackEvent().getDamagee().getVelocity();

        velocity.setY(velocity.getY() + (this.force));

        e.getAttackEvent().getDamagee().getWorld().spawnParticle(Particle.SMOKE_NORMAL, e.getAttackEvent().getDamagee().getLocation(), 750);

        e.getAttackEvent().getDamagee().setVelocity(velocity);

        this.startCooldown(e.getAttackEvent().getDamagee(), item);
    }

}