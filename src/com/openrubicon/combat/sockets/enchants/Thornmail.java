package com.openrubicon.combat.sockets.enchants;


import com.openrubicon.combat.events.attacks.PostAttackEvent;
import com.openrubicon.combat.sockets.CombatSocket;
import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.core.helpers.PlayerHelpers;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import org.bukkit.Material;

import java.util.HashSet;

public class Thornmail extends CombatSocket {
    private double ratio = 2;

    @Override
    public String getKey() {
        return "thornmail";
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
        return "Thornmail";
    }

    @Override
    public String getDescription() {
        return "Returns incoming damage as true damage to the attacker";
    }

    @Override
    public boolean generate()
    {
        super.generate();

        double min = 0;
        double max = (this.getItemSpecs().getPower() / 2) * this.getItemSpecs().getRarity();

        this.ratio = Helpers.scale(Helpers.randomDouble(min, max), min, 61, 2, 8);

        return true;
    }

    @Override
    public boolean save() {

        this.getSocketProperties().addDouble("ratio", this.ratio);
        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        this.ratio = this.getSocketProperties().getDouble("ratio");

        return true;
    }


    public double getRatio() {
        return ratio;
    }

    @Override
    public void onPostAttack(PostAttackEvent e, UniqueItem item, EntityInventorySlotType slot) {
        if(item.getWhoCurrentlyHas() != e.getAttackEvent().getDamagee())
            return;

        double damage = Math.ceil(e.getAttackEvent().getAttack().getFinalDamage() * (this.getRatio() / 100));

        PlayerHelpers.trueDamage(e.getAttackEvent().getDamager(), damage);
    }
}
