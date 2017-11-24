package com.openrubicon.combat.events;

import com.openrubicon.combat.classes.drops.LivingEntityDrops;
import com.openrubicon.combat.classes.log.CombatLogManager;
import com.openrubicon.combat.classes.log.CombatLogMessage;
import com.openrubicon.combat.classes.log.LivingEntityCombatLog;
import com.openrubicon.combat.events.attacks.*;
import com.openrubicon.combat.server.playerdata.GodMode;
import com.openrubicon.core.RRPGCore;
import com.openrubicon.core.api.actionbar.ActionBarManager;
import com.openrubicon.core.api.actionbar.ActionBarMessage;
import com.openrubicon.core.api.enums.Priority;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.core.helpers.PlayerHelpers;
import com.openrubicon.items.classes.durability.Durability;
import com.openrubicon.items.classes.inventory.LivingEntityInventory;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.SocketEvent;
import org.bukkit.*;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;

public class EventListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e)
    {
        if(e.isCancelled())
            return;

        if(e.getDamager() instanceof LivingEntity && e.getEntity() instanceof LivingEntity)
        {
            boolean cancelled = false;

            AttackEvent attackEvent = null;

            Durability durability = new Durability(((LivingEntity)e.getDamager()).getEquipment().getItemInMainHand());

            if(e.getEntity() instanceof Player)
            {
                Player player = (Player)e.getEntity();

                if(RRPGCore.players.getPlayerData(player, GodMode.class).isGod())
                {
                    cancelled = true;
                    if(e.getDamager() instanceof Player) {
                        ActionBarMessage actionBarMessage = new ActionBarMessage(Constants.YELLOW + e.getEntity().getName() + " is in God Mode.", Priority.HIGH, 20);
                        ActionBarManager.interrupt((Player) e.getDamager(), actionBarMessage);
                    }
                }

            }

            UniqueItem item = new UniqueItem(durability.getItem());
            if(!cancelled && item.isSpecialItem() && item.isValid() && item.isRightItemType() && !durability.hasDurability(1))
            {
                if(e.getDamager() instanceof Player)
                {
                    ActionBarMessage actionBarMessage = new ActionBarMessage(Constants.RED + Constants.BOLD + "Your Tool Doesn't Have Enough Durability", Priority.HIGH, 40);
                    ActionBarManager.interrupt((Player)e.getDamager(), actionBarMessage);
                }
            } else {
                if(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_SWEEP_ATTACK)
                {
                    attackEvent = new SweepAttackEvent((LivingEntity)e.getDamager(), (LivingEntity)e.getEntity(), e.getDamage());
                }

                if(e.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK)
                {
                    if(e.getDamager() instanceof Player)
                    {
                        Player player = (Player)e.getDamager();

                        if(player.isSprinting())
                        {
                            attackEvent = new ChargeAttackEvent((LivingEntity)e.getDamager(), (LivingEntity)e.getEntity(), e.getDamage());
                        } else if (player.isSneaking())
                        {
                            attackEvent = new ChannelAttackEvent((LivingEntity)e.getDamager(), (LivingEntity)e.getEntity(), e.getDamage());
                        } else {
                            attackEvent = new BasicAttackEvent((LivingEntity)e.getDamager(), (LivingEntity)e.getEntity(), e.getDamage());
                        }
                    } else {
                        attackEvent = new BasicAttackEvent((LivingEntity)e.getDamager(), (LivingEntity)e.getEntity(), e.getDamage());
                    }
                }
            }


            if(attackEvent != null && e.getDamager() instanceof Player)
            {
                Player player = (Player)e.getDamager();
                if(PlayerHelpers.isPlayerFalling(player))
                {
                    attackEvent.setCrit(true);
                }
            }

            if(attackEvent != null)
            {
                //attackEvent.setBlocked(true);
                //Bukkit.broadcastMessage(attackEvent.getClass().getTypeName());
                Bukkit.getPluginManager().callEvent(attackEvent);

                attackEvent.prepareSimulation();
                attackEvent.simulate();

                Bukkit.getPluginManager().callEvent(new PostAttackSimulateEvent(attackEvent));

                if(attackEvent.getAttack().isBlocked())
                {
                    Bukkit.getPluginManager().callEvent(new BlockedAttackEvent(attackEvent));
                    attackEvent.setCancelled(true);

                    Bukkit.getPluginManager().callEvent(new LogCombatEvent(attackEvent.getDamagee(), new CombatLogMessage(Constants.GREEN + "Blocked " + Constants.RESET_FORMAT + attackEvent.getDamager().getName() + "'s attack")));
                    Bukkit.getPluginManager().callEvent(new LogCombatEvent(attackEvent.getDamager(), new CombatLogMessage("Your attack on " + attackEvent.getDamagee().getName() + " was " + Constants.RED + "blocked")));
                } else {
                    Bukkit.getPluginManager().callEvent(new PrepareAttackApplicationEvent(attackEvent));

                    e.setDamage(attackEvent.getAttack().getFinalDamage());

                    Bukkit.getPluginManager().callEvent(new LogCombatEvent(attackEvent.getDamagee(), new CombatLogMessage("Took " + Constants.RED + (int) attackEvent.getAttack().getFinalDamage() + Constants.RESET_FORMAT + " damage from " + attackEvent.getDamager().getName())));
                    Bukkit.getPluginManager().callEvent(new LogCombatEvent(attackEvent.getDamager(), new CombatLogMessage("Dealt " + Constants.GREEN + (int) attackEvent.getAttack().getFinalDamage() + Constants.RESET_FORMAT + " damage to " + attackEvent.getDamagee().getName())));

                    Bukkit.getPluginManager().callEvent(new PostAttackEvent(attackEvent));
                }

                e.setCancelled(attackEvent.isCancelled());


            }

            if(cancelled)
                e.setCancelled(true);

        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {

        if(!(e.getEntity() instanceof Player))
        {
            LivingEntityDrops livingEntityDrops = new LivingEntityDrops(e.getEntity());

            livingEntityDrops.addDrops(e.getDrops(), e.getDroppedExp());

            livingEntityDrops.simulateDrops();

            if(livingEntityDrops.isOverride())
            {
                livingEntityDrops.drop();

                e.setDroppedExp(0);

                e.getDrops().clear();
            }

        }

    }

    @EventHandler
    public void onLogCombat(LogCombatEvent e)
    {
        if(!CombatLogManager.getLivingEntityCombatLog().containsKey(e.getLivingEntity()))
            CombatLogManager.getLivingEntityCombatLog().put(e.getLivingEntity(), new LivingEntityCombatLog(e.getLivingEntity()));

        CombatLogManager.getLivingEntityCombatLog().get(e.getLivingEntity()).add(e.getMessage());
    }

    @EventHandler
    public void onAttack(AttackEvent e)
    {
        SocketEvent.handleLivingEntity(e.getDamager(), e);
        SocketEvent.handleLivingEntity(e.getDamagee(), e);
    }

    @EventHandler
    public void onBasicAttack(BasicAttackEvent e)
    {
        SocketEvent.handleLivingEntity(e.getDamager(), e);
        SocketEvent.handleLivingEntity(e.getDamagee(), e);
    }

    @EventHandler
    public void onChannelAttack(ChannelAttackEvent e)
    {;
        SocketEvent.handleLivingEntity(e.getDamager(), e);
        SocketEvent.handleLivingEntity(e.getDamagee(), e);
    }

    @EventHandler
    public void onChargeAttack(ChargeAttackEvent e)
    {
        SocketEvent.handleLivingEntity(e.getDamager(), e);
        SocketEvent.handleLivingEntity(e.getDamagee(), e);
    }

    @EventHandler
    public void onSweepAttack(SweepAttackEvent e)
    {
        SocketEvent.handleLivingEntity(e.getDamager(), e);
        SocketEvent.handleLivingEntity(e.getDamagee(), e);
    }

    @EventHandler
    public void onBlockedAttack(BlockedAttackEvent e)
    {
        AttackEvent attack = e.getAttack();

        // Render particles and sound for damagee
        if(attack.getDamagee() instanceof Player)
        {
            Player p = (Player) attack.getDamagee();

            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, SoundCategory.HOSTILE, 1F, 4F);
            p.spawnParticle(Particle.FIREWORKS_SPARK, p.getLocation(), 5, 0, 1, 0);
        }

        // Render particles and sound for damager
        if (attack.getDamager() instanceof Player) {
            Player p = (Player) attack.getDamager();

            p.playSound(attack.getDamagee().getLocation(), Sound.BLOCK_NOTE_PLING, SoundCategory.HOSTILE, 1F, 4F);
            p.spawnParticle(Particle.FIREWORKS_SPARK, attack.getDamagee().getLocation(), 5, 0, 1, 0);
        }

        // Throw both living entities backwards from each other
        Vector velocityDamager = attack.getDamager().getVelocity();
        Vector velocityDamagee = attack.getDamagee().getVelocity();

        double xDir = attack.getDamagee().getLocation().getX() - attack.getDamager().getLocation().getX();
        double zDir = attack.getDamagee().getLocation().getZ() - attack.getDamager().getLocation().getZ();

        Vector directionDamagee = new Vector(xDir, 1, zDir);
        Vector directionDamager = new Vector((xDir * -1), 1, (zDir * -1));
        directionDamagee.normalize();
        directionDamager.normalize();

        double force = 0.7;

        velocityDamagee.setX(directionDamagee.getX() * force);
        velocityDamagee.setZ(directionDamagee.getZ() * force);

        attack.getDamagee().setVelocity(velocityDamagee);

        velocityDamager.setX(directionDamager.getX() * force);
        velocityDamager.setZ(directionDamager.getZ() * force);

        attack.getDamager().setVelocity(velocityDamager);
    }

    @EventHandler
    public void onPrepareDefensePoints(PrepareDefensePointsEvent e)
    {
        SocketEvent.handleLivingEntity(e.getLivingEntity(), e);
    }

    @EventHandler
    public void onPrepareAttackPoints(PrepareAttackPointsEvent e)
    {
        SocketEvent.handleLivingEntity(e.getLivingEntity(), e);
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e)
    {
        // Generate a random int between 0 and n
        // digit 1: main hand spawn 1
        // digit 2: helmet 2
        // digit 3: chest 4
        // digit 4: legs 8
        // digit 5: boots 16
        if(e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL)
        {
            int itemChance = 0;

            if(e.getEntity() instanceof Spider || e.getEntity() instanceof Creeper || e.getEntity() instanceof Enderman || e.getEntity() instanceof Silverfish || e.getEntity() instanceof WitherSkeleton)
                itemChance = 1;

            if(e.getEntity() instanceof Skeleton || e.getEntity() instanceof Zombie || e.getEntity() instanceof PigZombie || e.getEntity() instanceof Husk)
                itemChance = 31;

            if(itemChance == 0)
                return;

            int chanceChoice = Helpers.rng.nextInt(itemChance + 1);

            if((chanceChoice & 1) > 0)
            {
                int choice = Helpers.rng.nextInt(MaterialGroups.HAND_HELD.size());
                ArrayList<Material> materials = new ArrayList<>();
                materials.addAll(MaterialGroups.HAND_HELD);

                ItemStack i = new ItemStack(materials.get(choice));

                UniqueItem item = new UniqueItem(i, false);
                item.generate();
                item.save();
                e.getEntity().getEquipment().setItemInMainHand(item.getItem());
            }

            if((chanceChoice & 2) > 0)
            {
                int choice = Helpers.rng.nextInt(MaterialGroups.HELMETS.size());
                ArrayList<Material> materials = new ArrayList<>();
                materials.addAll(MaterialGroups.HELMETS);

                ItemStack i = new ItemStack(materials.get(choice));

                UniqueItem item = new UniqueItem(i, false);
                item.generate();
                item.save();
                e.getEntity().getEquipment().setHelmet(item.getItem());
            }

            if((chanceChoice & 4) > 0)
            {
                int choice = Helpers.rng.nextInt(MaterialGroups.CHESTPLATES.size());
                ArrayList<Material> materials = new ArrayList<>();
                materials.addAll(MaterialGroups.CHESTPLATES);

                ItemStack i = new ItemStack(materials.get(choice));

                UniqueItem item = new UniqueItem(i, false);
                item.generate();
                item.save();
                e.getEntity().getEquipment().setChestplate(item.getItem());
            }

            if((chanceChoice & 8) > 0)
            {
                int choice = Helpers.rng.nextInt(MaterialGroups.LEGGINGS.size());
                ArrayList<Material> materials = new ArrayList<>();
                materials.addAll(MaterialGroups.LEGGINGS);

                ItemStack i = new ItemStack(materials.get(choice));

                UniqueItem item = new UniqueItem(i, false);
                item.generate();
                item.save();
                e.getEntity().getEquipment().setLeggings(item.getItem());
            }

            if((chanceChoice & 16) > 0)
            {
                int choice = Helpers.rng.nextInt(MaterialGroups.BOOTS.size());
                ArrayList<Material> materials = new ArrayList<>();
                materials.addAll(MaterialGroups.BOOTS);

                ItemStack i = new ItemStack(materials.get(choice));

                UniqueItem item = new UniqueItem(i, false);
                item.generate();
                item.save();
                e.getEntity().getEquipment().setBoots(item.getItem());
            }

        }
    }

}
