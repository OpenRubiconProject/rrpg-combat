package com.openrubicon.combat.scoreboard;

import com.openrubicon.combat.sockets.abilities.Challenge;
import com.openrubicon.core.api.enums.Priority;
import com.openrubicon.core.api.scoreboard.interfaces.ScoreboardSection;
import com.openrubicon.core.helpers.Constants;
import org.bukkit.entity.Player;

import java.util.ArrayList;

public class ChallengeStatus implements ScoreboardSection {
    @Override
    public Priority getPriority() {
        return Priority.HIGH;
    }

    @Override
    public String getTitle() {
        return "Challenge";
    }

    @Override
    public ArrayList<String> getLines(Player player) {
        if(!Challenge.isInChallenge(player))
            return new ArrayList<>();

        Challenge challenge = Challenge.getChallenge(player);

        String name = "";

        if(challenge.getChallenger() == player)
            name = challenge.getChallengee().getName();
        else if(challenge.getChallengee() == player)
            name = challenge.getChallenger().getName();

        ArrayList<String> lines = new ArrayList<>();
        lines.add(Constants.MYSTIC_SECONDARY_COLOR + name + ": " + Constants.MYSTIC_PRIMARY_COLOR + challenge.getChallengeCooldown().getCooldownSeconds());
        return lines;
    }
}
