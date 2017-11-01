package com.openrubicon.combat.classes;

import com.openrubicon.core.helpers.MaterialGroups;
import org.bukkit.inventory.ItemStack;

public class ItemCombatConstants {
    public static int getDefenseLevel(ItemStack item)
    {
        if(item == null)
            return 0;

        if(MaterialGroups.DIAMOND.contains(item.getType()))
            return 5;
        if(MaterialGroups.GOLD.contains(item.getType()))
            return 4;
        if(MaterialGroups.CHAINMAIL.contains(item.getType()))
            return 3;
        if(MaterialGroups.IRON.contains(item.getType()))
            return 2;
        if(MaterialGroups.LEATHER.contains(item.getType()))
            return 1;

        return 0;
    }

    public static int getAttackLevel(ItemStack item)
    {
        if(item == null)
            return 0;

        if(MaterialGroups.DIAMOND.contains(item.getType()))
            return 5;
        if(MaterialGroups.GOLD.contains(item.getType()))
            return 4;
        if(MaterialGroups.IRON.contains(item.getType()))
            return 3;
        if(MaterialGroups.STONE.contains(item.getType()))
            return 2;
        if(MaterialGroups.WOOD.contains(item.getType()))
            return 1;

        return 0;
    }

    public static int getWeaponTypeDebuff(ItemStack item)
    {
        if(item == null)
            return 8;

        if(MaterialGroups.HOES.contains(item.getType()))
            return 4;
        if(MaterialGroups.SPADES.contains(item.getType()))
            return 3;
        if(MaterialGroups.PICKAXES.contains(item.getType()))
            return 2;
        if(MaterialGroups.AXES.contains(item.getType()))
            return 1;
        if(MaterialGroups.SWORDS.contains(item.getType()))
            return 0;

        return 7;
    }
}
