package com.openrubicon.combat.sockets.effects;

import com.openrubicon.combat.sockets.CombatSocket;
import com.openrubicon.core.api.actionbar.ActionBarManager;
import com.openrubicon.core.api.actionbar.ActionBarMessage;
import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.events.PlayerLookingAtEntityEvent;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import org.bukkit.Material;

import java.util.HashSet;

public class Jarvis extends CombatSocket {
    @Override
    public String getKey() {
        return "jarvis";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.HELMETS;
    }

    @Override
    public String getName() {
        return "Jarvis";
    }

    @Override
    public String getDescription() {
        return "Alerts you of your chance of survival when taking on a target";
    }

    @Override
    public void onPlayerLookingAtEntity(PlayerLookingAtEntityEvent e, UniqueItem item, EntityInventorySlotType slot) {
        if(e.getLivingEntity() == null)
            return;
        ActionBarMessage actionBarMessage = new ActionBarMessage( Constants.HEADING_COLOR + Constants.BOLD + e.getLivingEntity().getName(), 10);
        ActionBarManager.interrupt(e.getPlayer(), actionBarMessage);
    }
}
