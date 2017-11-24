package com.openrubicon.combat.classes.drops;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.ArrayList;
import java.util.List;

public class Drops {

    private List<Drop> drops = new ArrayList<>();

    public List<Drop> getDrops() {
        return drops;
    }

    public void drop(World world, Location dropLocation)
    {
        for(Drop drop : this.getDrops())
        {
            if(drop.hasDrops())
                drop.drop(world, dropLocation);
        }
    }
}
