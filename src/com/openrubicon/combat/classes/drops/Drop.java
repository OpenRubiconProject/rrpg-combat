package com.openrubicon.combat.classes.drops;

import com.openrubicon.core.helpers.Helpers;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ExperienceOrb;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

public class Drop {
    private ItemStack itemStack;
    private int xp = 0;

    public Drop(ItemStack itemStack, int xp) {
        this.itemStack = itemStack;
        this.xp = xp;
    }

    public void setItemStack(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }


    public ItemStack getItemStack() {
        return itemStack;
    }

    public int getXp() {
        return xp;
    }

    public boolean hasDrops()
    {
        if(itemStack != null || xp > 0)
            return true;
        return false;
    }

    public void drop(World world, Location dropLocation)
    {
        int factor = 20;

        int max = (11 + 11 + 11);

        if(xp < 2)
        {
            xp = 1;
        } else {
            xp = Helpers.randomInt(xp / 2, xp);

            xp = (int) Helpers.scale(xp, 1, max, 1, factor);
        }

        if(this.getXp() > 0)
        {
            int xpPerOrb = 5;
            int orbs = (int)Math.ceil(this.xp / xpPerOrb);
            for(int i = xp; i > 0;)
            {
                int dropXP = xpPerOrb;
                if(dropXP > i)
                    dropXP = i;

                ExperienceOrb experienceOrb = world.spawn(dropLocation, ExperienceOrb.class);

                experienceOrb.setExperience(dropXP);

                experienceOrb.setVelocity(new Vector(Helpers.randomDouble(-0.5, 0.5), Helpers.randomDouble(0.1, 0.5), Helpers.randomDouble(-0.5, 0.5)));

                i -= dropXP;
            }
        }

        if(this.getItemStack() != null)
            world.dropItemNaturally(dropLocation, this.getItemStack());
    }
}
