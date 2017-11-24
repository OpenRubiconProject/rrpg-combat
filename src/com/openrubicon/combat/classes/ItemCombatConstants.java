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
            return 1;

        if(MaterialGroups.DIAMOND.contains(item.getType()))
            return 8;
        if(MaterialGroups.GOLD.contains(item.getType()))
            return 7;
        if(MaterialGroups.IRON.contains(item.getType()))
            return 5;
        if(MaterialGroups.STONE.contains(item.getType()))
            return 4;
        if(MaterialGroups.WOOD.contains(item.getType()))
            return 2;

        return 1;
    }

    public static int getWeaponTypeDebuff(ItemStack item)
    {
        if(item == null)
            return 10;

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

        return 9;
    }

    public static double getMaterialDefaultDamage(ItemStack item)
    {
        if(MaterialGroups.DIAMOND_TOOLS.contains(item.getType()))
        {
            if(MaterialGroups.SWORDS.contains(item.getType()))
                return 7 * 1.6;

            if(MaterialGroups.AXES.contains(item.getType()))
                return 9 * 1;

            if(MaterialGroups.PICKAXES.contains(item.getType()))
                return 5 * 1.2;

            if(MaterialGroups.SPADES.contains(item.getType()))
                return 5.5 * 1;

            if(MaterialGroups.HOES.contains(item.getType()))
                return 1 * 4;
        }

        if(MaterialGroups.GOLD_TOOLS.contains(item.getType()))
        {
            if(MaterialGroups.SWORDS.contains(item.getType()))
                return 4 * 1.6;

            if(MaterialGroups.AXES.contains(item.getType()))
                return 7 * 1;

            if(MaterialGroups.PICKAXES.contains(item.getType()))
                return 2 * 1.2;

            if(MaterialGroups.SPADES.contains(item.getType()))
                return 2.5 * 1;

            if(MaterialGroups.HOES.contains(item.getType()))
                return 1 * 1;
        }

        if(MaterialGroups.IRON_TOOLS.contains(item.getType()))
        {
            if(MaterialGroups.SWORDS.contains(item.getType()))
                return 6 * 1.6;

            if(MaterialGroups.AXES.contains(item.getType()))
                return 9 * 0.9;

            if(MaterialGroups.PICKAXES.contains(item.getType()))
                return 4 * 1.2;

            if(MaterialGroups.SPADES.contains(item.getType()))
                return 4.5 * 1;

            if(MaterialGroups.HOES.contains(item.getType()))
                return 1 * 3;
        }

        if(MaterialGroups.STONE_TOOLS.contains(item.getType()))
        {
            if(MaterialGroups.SWORDS.contains(item.getType()))
                return 5 * 1.6;

            if(MaterialGroups.AXES.contains(item.getType()))
                return 9 * 0.8;

            if(MaterialGroups.PICKAXES.contains(item.getType()))
                return 3 * 1.2;

            if(MaterialGroups.SPADES.contains(item.getType()))
                return 3.5 * 1;

            if(MaterialGroups.HOES.contains(item.getType()))
                return 1 * 2;
        }

        if(MaterialGroups.WOOD_TOOLS.contains(item.getType()))
        {
            if(MaterialGroups.SWORDS.contains(item.getType()))
                return 4 * 1.6;

            if(MaterialGroups.AXES.contains(item.getType()))
                return 7 * 0.8;

            if(MaterialGroups.PICKAXES.contains(item.getType()))
                return 2 * 1.2;

            if(MaterialGroups.SPADES.contains(item.getType()))
                return 2.5 * 1;

            if(MaterialGroups.HOES.contains(item.getType()))
                return 1 * 1;
        }

        return 1;
    }
}
