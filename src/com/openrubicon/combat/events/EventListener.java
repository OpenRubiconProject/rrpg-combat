package com.openrubicon.combat.events;

import com.openrubicon.combat.events.attacks.*;
import com.openrubicon.core.helpers.PlayerHelpers;
import com.openrubicon.items.classes.sockets.SocketEvent;
import org.bukkit.*;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.util.Vector;

public class EventListener implements Listener {

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e)
    {
        if(e.getDamager() instanceof LivingEntity && e.getEntity() instanceof LivingEntity)
        {
            AttackEvent attackEvent = null;

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

                e.setDamage(attackEvent.getAttack().getFinalDamage());

                if(attackEvent.getAttack().isBlocked())
                {
                    Bukkit.getPluginManager().callEvent(new BlockedAttackEvent(attackEvent));
                    attackEvent.setCancelled(true);
                }

                e.setCancelled(attackEvent.isCancelled());
            }

        }
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

}
