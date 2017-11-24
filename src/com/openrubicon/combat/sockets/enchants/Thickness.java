package com.openrubicon.combat.sockets.enchants;

import com.openrubicon.combat.events.attacks.PrepareAttackApplicationEvent;
import com.openrubicon.combat.sockets.CombatSocket;
import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import org.bukkit.Material;

import java.util.HashSet;

public class Thickness extends CombatSocket {
    private double multiplier = 1.1;

    @Override
    public String getKey() {
        return "thickness";
    }

    @Override
    public HashSet<Material> getMaterials() {
        HashSet<Material> materials = new HashSet<>();
        materials.addAll(MaterialGroups.CHESTPLATES);
        materials.addAll(MaterialGroups.LEGGINGS);
        return materials;
    }

    @Override
    public String getName() {
        return "Thickness";
    }

    @Override
    public String getDescription() {
        return "Reduces damage taken.";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        double min = 0;
        double max = (this.getItemSpecs().getPower() / 2) * this.getItemSpecs().getRarity();

        this.multiplier = Helpers.scale(Helpers.randomDouble(min, max), min, 61, 1.1, 2);

        return true;
    }

    @Override
    public boolean save() {

        this.getSocketProperties().addDouble("multiplier", this.multiplier);
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        this.multiplier = this.getSocketProperties().getDouble("multiplier");

        return true;
    }


    public double getMultiplier() {
        return multiplier;
    }

    @Override
    public void onPrepareAttackApplication(PrepareAttackApplicationEvent e, UniqueItem item, EntityInventorySlotType slot) {
        if(item.getWhoCurrentlyHas() != e.getAttackEvent().getDamagee())
            return;

        if(slot == EntityInventorySlotType.MAINHAND || slot == EntityInventorySlotType.OFFHAND)
            return;

        e.getAttackEvent().getAttack().setFinalDamage(e.getAttackEvent().getAttack().getFinalDamage() / this.getMultiplier());
    }
}
