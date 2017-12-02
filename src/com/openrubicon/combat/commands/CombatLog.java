package com.openrubicon.combat.commands;

import com.openrubicon.combat.classes.log.CombatLogManager;
import com.openrubicon.core.api.command.Command;
import com.openrubicon.core.api.interactables.Player;
import com.openrubicon.core.api.interactables.enums.InteractableType;
import com.openrubicon.core.api.interactables.interfaces.Interactable;
import com.openrubicon.core.api.utility.DynamicPrimitive;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.Helpers;

import java.util.ArrayList;

public class CombatLog extends Command {
    @Override
    public String getCommandFormat() {
        return "combat log";
    }

    @Override
    public ArrayList<InteractableType> getAllowedSenderTypes() {
        ArrayList<InteractableType> senders = new ArrayList<>();
        senders.add(InteractableType.PLAYER);
        return senders;
    }

    @Override
    public void handle(Interactable interactable, ArrayList<DynamicPrimitive> args) {

        if(interactable instanceof Player)
        {
            Player player = (Player)interactable;
            player.sendMessage("");
            player.sendMessage(Helpers.colorize(Constants.HEADING_COLOR + Constants.BOLD + "==Combat Log=="));
            player.sendMessage(CombatLogManager.getLivingEntityCombatLog().get(player.getPlayer()).getObservation());
        }

    }
}
