package com.openrubicon.combat.sockets.events;

import com.openrubicon.combat.events.attacks.AttackEvent;
import com.openrubicon.combat.events.attacks.PostAttackEvent;
import com.openrubicon.combat.events.attacks.PostAttackSimulateEvent;
import com.openrubicon.combat.events.attacks.PrepareAttackApplicationEvent;
import com.openrubicon.combat.sockets.abilities.Challenge;
import com.openrubicon.items.classes.sockets.SocketEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SocketEventListener implements Listener {

    @EventHandler
    public void onAttack(AttackEvent e)
    {
        if(Challenge.isInChallenge(e.getDamager()))
        {
            if(Challenge.getChallenge(e.getDamager()).getChallenger() != e.getDamagee() && Challenge.getChallenge(e.getDamager()).getChallengee() != e.getDamagee())
            {
                e.setCancelled(true);
                e.getDamager().sendMessage("Cant damage this thing as you are currently in a challenge.");
            }
        }

    }

    @EventHandler
    public void onPostAttackSimulate(PostAttackSimulateEvent e)
    {
        SocketEvent.handleLivingEntity(e.getAttackEvent().getDamager(), e);
        SocketEvent.handleLivingEntity(e.getAttackEvent().getDamagee(), e);
    }

    @EventHandler
    public void onPrepareAttackApplication(PrepareAttackApplicationEvent e)
    {
        SocketEvent.handleLivingEntity(e.getAttackEvent().getDamager(), e);
        SocketEvent.handleLivingEntity(e.getAttackEvent().getDamagee(), e);
    }

    @EventHandler
    public void onPostAttack(PostAttackEvent e)
    {
        SocketEvent.handleLivingEntity(e.getAttackEvent().getDamager(), e);
        SocketEvent.handleLivingEntity(e.getAttackEvent().getDamagee(), e);
    }

}
