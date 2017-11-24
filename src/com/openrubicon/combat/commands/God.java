package com.openrubicon.combat.commands;

import com.openrubicon.combat.server.playerdata.GodMode;
import com.openrubicon.core.RRPGCore;
import com.openrubicon.core.api.command.Command;
import com.openrubicon.core.api.interactables.Player;
import com.openrubicon.core.api.interactables.enums.InteractableType;
import com.openrubicon.core.api.interactables.interfaces.Interactable;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.Helpers;

import java.util.ArrayList;

public class God extends Command {

    @Override
    public String getCommandFormat() {
        return "combat god";
    }

    @Override
    public ArrayList<InteractableType> getAllowedSenderTypes() {
        ArrayList<InteractableType> senders = new ArrayList<>();
        senders.add(InteractableType.PLAYER);
        return senders;
    }

    @Override
    public void handle(Interactable interactable, String[] strings) {

        if(interactable instanceof Player)
        {
            org.bukkit.entity.Player player = ((Player)interactable).getPlayer();

            if(RRPGCore.players.getPlayerData(player, GodMode.class).isGod())
            {
                RRPGCore.players.getPlayerData(player, GodMode.class).setGod(false);
                player.sendMessage(Helpers.colorize(Constants.HEADING_COLOR + "God Mode Disabled."));
            } else {
                RRPGCore.players.getPlayerData(player, GodMode.class).setGod(true);
                player.sendMessage(Helpers.colorize(Constants.HEADING_COLOR + "God Mode Enabled."));
            }


        }

    }

}
