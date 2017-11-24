package com.openrubicon.combat.classes.drops;

import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.items.classes.durability.Durability;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class LivingEntityDrops {

    private Drops drops = new Drops();

    private LivingEntity livingEntity;

    private boolean override = false;

    public LivingEntityDrops(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
    }

    public LivingEntityDrops(LivingEntity livingEntity, Drops drops) {
        this.drops = drops;
        this.livingEntity = livingEntity;
    }

    public Drops getDrops() {
        return drops;
    }

    public LivingEntity getLivingEntity() {
        return livingEntity;
    }

    public boolean isOverride() {
        return override;
    }

    private void simulateDrop(UniqueItem uniqueItem, double factor)
    {
        int xp = 0;

        ItemStack item = null;

        if (uniqueItem.isSpecialItem() && uniqueItem.isValid())
        {
            xp = (int)(uniqueItem.getItemSpecs().getPower() + uniqueItem.getItemSpecs().getSockets() + uniqueItem.getItemSpecs().getRarity());

            int points = (int) uniqueItem.getItemSpecs().getAttributePoints();
            points = (int)Math.ceil(Math.pow(points, factor));
            if(points < 1)
                points = 1;

            if(Helpers.randomInt(0, points) == 0)
            {
                Durability durability = new Durability(uniqueItem.getItem());
                int adjustment = Helpers.randomInt(0, durability.getDurability()) * -1;
                durability.adjustDurability(adjustment);
                item = durability.getItem();
            }
        }

        this.getDrops().getDrops().add(new Drop(item, xp));
    }

    public void simulateDrops()
    {
        UniqueItem mainhand = new UniqueItem(this.getLivingEntity().getEquipment().getItemInMainHand());
        UniqueItem offhand = new UniqueItem(this.getLivingEntity().getEquipment().getItemInOffHand());
        UniqueItem helmet = new UniqueItem(this.getLivingEntity().getEquipment().getHelmet());
        UniqueItem chestplate = new UniqueItem(this.getLivingEntity().getEquipment().getChestplate());
        UniqueItem leggings = new UniqueItem(this.getLivingEntity().getEquipment().getLeggings());
        UniqueItem boots = new UniqueItem(this.getLivingEntity().getEquipment().getBoots());

        if(mainhand.isSpecialItem() || offhand.isSpecialItem() || helmet.isSpecialItem() || chestplate.isSpecialItem() || leggings.isSpecialItem() || boots.isSpecialItem())
        {
            this.override = true;

            // Increase this to make it less likely for mobs to drop.
            // Lower this number to make it more likely for items to drop.
            // Uses an exponential approach, so small changes will have large in game impacts
            double dropFactor = 0.89;

            this.simulateDrop(mainhand, dropFactor);
            this.simulateDrop(offhand, dropFactor);
            this.simulateDrop(helmet, dropFactor);
            this.simulateDrop(chestplate, dropFactor);
            this.simulateDrop(leggings, dropFactor);
            this.simulateDrop(boots, dropFactor);
        }
    }

    public void addDrop(ItemStack itemDrop)
    {
        this.getDrops().getDrops().add(new Drop(itemDrop, 0));
    }

    public void addDrop(ItemStack itemDrop, int xp)
    {
        this.getDrops().getDrops().add(new Drop(itemDrop, xp));
    }

    public void addDrop(int xp)
    {
        this.getDrops().getDrops().add(new Drop(null, xp));
    }

    public void addDrops(List<ItemStack> itemDrops)
    {
        for(ItemStack item : itemDrops)
        {
            this.addDrop(item);
        }
    }

    public void addDrops(List<ItemStack> itemDrops, int xp)
    {
        for(ItemStack item : itemDrops)
        {
            this.addDrop(item);
        }

        this.addDrop(xp);
    }

    public void drop()
    {
        this.getDrops().drop(this.getLivingEntity().getWorld(), this.getLivingEntity().getLocation());
    }
}
