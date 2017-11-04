package com.openrubicon.combat.events;

import com.openrubicon.combat.classes.log.CombatLogMessage;
import com.openrubicon.core.api.events.Event;
import org.bukkit.entity.LivingEntity;

public class LogCombatEvent extends Event {
    private LivingEntity livingEntity;
    private CombatLogMessage message;

    public LogCombatEvent(LivingEntity livingEntity, CombatLogMessage message) {
        this.livingEntity = livingEntity;
        this.message = message;
    }

    public LivingEntity getLivingEntity() {
        return livingEntity;
    }

    public CombatLogMessage getMessage() {
        return message;
    }
}
