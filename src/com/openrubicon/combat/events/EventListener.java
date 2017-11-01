package com.openrubicon.combat.events;

import com.openrubicon.combat.events.attacks.*;
import com.openrubicon.core.helpers.PlayerHelpers;
import com.openrubicon.items.classes.sockets.SocketEvent;
import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

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
                    } else if (PlayerHelpers.isPlayerFalling(player))
                    {
                        attackEvent = new CritAttackEvent((LivingEntity)e.getDamager(), (LivingEntity)e.getEntity(), e.getDamage());
                    } else {
                        attackEvent = new BasicAttackEvent((LivingEntity)e.getDamager(), (LivingEntity)e.getEntity(), e.getDamage());
                    }
                } else {
                    attackEvent = new BasicAttackEvent((LivingEntity)e.getDamager(), (LivingEntity)e.getEntity(), e.getDamage());
                }
            }

            if(attackEvent != null)
            {
                Bukkit.broadcastMessage(attackEvent.getClass().getTypeName());
                Bukkit.getPluginManager().callEvent(attackEvent);
                e.setDamage(attackEvent.getDamage());
                e.setCancelled(attackEvent.isCancelled());

                if(attackEvent.isBlocked())
                {
                    if(e.getEntity() instanceof Player)
                    {
                        Player p = (Player) e.getEntity();

                        p.playSound(p.getLocation(), Sound.BLOCK_NOTE_PLING, SoundCategory.HOSTILE, 1F, 4F);
                        p.spawnParticle(Particle.FIREWORKS_SPARK, p.getLocation(), 5, 0, 1, 0);
                    }

                    if (e.getDamager() instanceof Player) {
                        Player p = (Player) e.getDamager();

                        p.playSound(e.getEntity().getLocation(), Sound.BLOCK_NOTE_PLING, SoundCategory.HOSTILE, 1F, 4F);
                        p.spawnParticle(Particle.FIREWORKS_SPARK, e.getEntity().getLocation(), 5, 0, 1, 0);
                    }
                }
            }

        }
    }

    @EventHandler
    public void onBasicAttack(BasicAttackEvent e)
    {
        SocketEvent.handleLivingEntity(e.getDamager(), e);
    }

}
