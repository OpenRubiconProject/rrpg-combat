package com.openrubicon.combat.sockets.enchants;

import com.openrubicon.combat.events.PrepareAttackPointsEvent;
import com.openrubicon.combat.sockets.CombatSocket;
import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import org.bukkit.Material;

import java.util.HashSet;

public class Piercing extends CombatSocket {
    private int pierce = 1;

    @Override
    public String getKey() {
        return "piercing";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.TOOLS;
    }

    @Override
    public String getName() {
        return "Piercing";
    }

    @Override
    public String getDescription() {
        return "Increases your attack points";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        double min = 0;
        double max = (this.getItemSpecs().getPower()) * (this.getItemSpecs().getRarity() / 2);

        this.pierce = (int) Helpers.scale(Helpers.randomDouble(min, max), min, 61, 1, 30);

        if(this.pierce < 1)
            this.pierce = 1;

        return true;
    }

    @Override
    public boolean save() {

        this.getSocketProperties().addInteger("pierce", this.pierce);
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        this.pierce = this.getSocketProperties().getInteger("pierce");

        return true;
    }


    public int getPierce() {
        return pierce;
    }

    @Override
    public void onPrepareAttackPoints(PrepareAttackPointsEvent e, UniqueItem item, EntityInventorySlotType slot) {

        e.setAttackPoints(e.getAttackPoints() + this.getPierce());
    }
}
