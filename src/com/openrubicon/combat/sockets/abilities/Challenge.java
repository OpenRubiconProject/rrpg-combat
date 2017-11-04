package com.openrubicon.combat.sockets.abilities;

import com.openrubicon.combat.sockets.CombatCooldownSocket;
import com.openrubicon.combat.sockets.abilities.challenge.cooldowns.ChallengeCooldown;
import com.openrubicon.core.api.cooldowns.CooldownManager;
import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.api.server.players.Players;
import com.openrubicon.core.helpers.Helpers;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import java.util.HashSet;
import java.util.UUID;

public class Challenge extends CombatCooldownSocket {

    private static HashSet<Challenge> challenges = new HashSet<>();

    private ChallengeCooldown challengeCooldown;

    private LivingEntity challenger;
    private LivingEntity challengee;

    @Override
    public boolean generate()
    {
        super.generate();

        this.challengeCooldown = new ChallengeCooldown(this.getUuid().toString(), this.getKey());
        this.challengeCooldown.setLength(Helpers.secondsToTicks(60));

        CooldownManager.add(this.challengeCooldown);

        return true;
    }

    @Override
    public boolean save()
    {
        this.getSocketProperties().addInteger(ChallengeCooldown.COOLDOWN, this.challengeCooldown.getLength());

        String challengerUuid = "none";
        String challengeeUuid = "none";

        if(challenger != null)
            challengerUuid = this.challenger.getUniqueId().toString();

        if(challengee != null)
            challengeeUuid = this.challengee.getUniqueId().toString();

        this.getSocketProperties().add("challenger-uuid", challengerUuid);
        this.getSocketProperties().add("challengee-uuid", challengeeUuid);

        return super.save();
    }

    @Override
    public boolean load() {
        super.load();

        if(CooldownManager.has(new ChallengeCooldown(this.getUuid().toString(), this.getKey())))
        {
            this.challengeCooldown = (ChallengeCooldown)CooldownManager.get(new ChallengeCooldown(this.getUuid().toString(), this.getKey()).getId());
        } else {
            this.challengeCooldown = new ChallengeCooldown(this.getUuid().toString(), this.getKey());
            this.challengeCooldown.setLength(this.getSocketProperties().getInteger(ChallengeCooldown.COOLDOWN));
            CooldownManager.add(this.challengeCooldown);
        }

        Players playersApi = new Players();

        String challengerUuid = this.getSocketProperties().get("challenger-uuid");
        String challengeeUuid = this.getSocketProperties().get("challengee-uuid");

        if(challengerUuid.length() > 8)
        {
            Entity entity = Bukkit.getEntity(UUID.fromString(challengerUuid));
            if(entity instanceof LivingEntity)
            {
                challenger = (LivingEntity)entity;
            }
        }

        if(challengeeUuid.length() > 8)
        {
            Entity entity = Bukkit.getEntity(UUID.fromString(challengeeUuid));
            if(entity instanceof LivingEntity)
            {
                challengee = (LivingEntity)entity;
            }
        }

        return true;
    }

    @Override
    public int getCooldownLengthTicks() {
        return Helpers.secondsToTicks(30 * 60);
    }

    @Override
    public String getKey() {
        return "challenge";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.HAND_HELD;
    }

    @Override
    public String getName() {
        return "Challenge";
    }

    @Override
    public String getDescription() {
        return "Challenge a chosen foe making them the only entity that can damage you.";
    }

    public LivingEntity getChallenger() {
        return challenger;
    }

    public LivingEntity getChallengee() {
        return challengee;
    }

    public ChallengeCooldown getChallengeCooldown() {
        return challengeCooldown;
    }

    public HashSet<Challenge> getChallenges() {
        return challenges;
    }

    public static boolean isInChallenge(LivingEntity livingEntity)
    {
        for(Challenge challenge : challenges) {
            if (challenge.getChallenger() == livingEntity || challenge.getChallengee() == livingEntity) {
                if (challenge.getChallengeCooldown().isOnCooldown())
                    return true;
                else
                    challenges.remove(challenge);
            }
        }
        return false;
    }

    public static Challenge getChallenge(LivingEntity livingEntity)
    {
        for(Challenge challenge : challenges) {
            if (challenge.getChallenger() == livingEntity || challenge.getChallengee() == livingEntity) {
                if (challenge.getChallengeCooldown().isOnCooldown())
                    return challenge;
                else
                    challenges.remove(challenge);
            }
        }
        return null;
    }

    @Override
    public void onPlayerInteractEntity(PlayerInteractEntityEvent e, UniqueItem item, EntityInventorySlotType slot) {
        if(this.isOnCooldown() || this.challengeCooldown.isOnCooldown())
            return;

        if(!e.getPlayer().isSneaking())
            return;

        if(!(e.getRightClicked() instanceof LivingEntity))
            return;

        LivingEntity entity = (LivingEntity)e.getRightClicked();

        for(Challenge challenge : this.getChallenges())
        {
            if(challenge.getChallenger() == e.getPlayer() || challenge.getChallenger() == entity || challenge.getChallengee() == e.getPlayer() || challenge.getChallengee() == entity)
            {
                if(challenge.getChallengeCooldown().isOnCooldown())
                    return;
                else
                    challenges.remove(challenge);
            }
        }

        this.challenger = e.getPlayer();

        this.challengee = entity;

        this.save();

        this.startCooldown(e.getPlayer(), item);

        CooldownManager.start(this.challengeCooldown);

        challenges.add(this);

        this.challengee.sendMessage(e.getPlayer().getDisplayName() + " has challenged you. For the next 60 seconds you are only able to damage them and nothing else.");
        this.challenger.sendMessage("You challenged " + this.challengee.getName() + ", for the next 60 seconds they can only damage you.");
    }
}
