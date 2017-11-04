package com.openrubicon.combat.classes.log;

import org.bukkit.entity.LivingEntity;

import java.util.HashMap;

public class CombatLogManager {

    private static HashMap<LivingEntity, LivingEntityCombatLog> livingEntityCombatLog = new HashMap<>();

    public static HashMap<LivingEntity, LivingEntityCombatLog> getLivingEntityCombatLog() {
        return livingEntityCombatLog;
    }
}
