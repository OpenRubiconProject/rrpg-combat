package com.openrubicon.combat.classes.log;

import com.openrubicon.core.api.interfaces.Observeable;
import com.openrubicon.core.helpers.Helpers;
import org.apache.commons.collections4.queue.CircularFifoQueue;
import org.bukkit.entity.LivingEntity;

import java.util.ArrayList;

public class LivingEntityCombatLog implements Observeable {

    private LivingEntity livingEntity;
    private CircularFifoQueue<CombatLogMessage> combatLogMessages = new CircularFifoQueue<>(10);

    public LivingEntityCombatLog(LivingEntity livingEntity) {
        this.livingEntity = livingEntity;
    }

    public void add(CombatLogMessage combatLogMessage)
    {
        this.combatLogMessages.add(combatLogMessage);
    }

    @Override
    public ArrayList<String> getObservation() {
        ArrayList<String> observation = new ArrayList<>();
        for(int i = 0; i < this.combatLogMessages.size(); i++)
        {
            observation.add("* " + this.combatLogMessages.get(i).getMessage());
        }
        return Helpers.colorize(observation);
    }
}
