package com.openrubicon.combat.sockets.abilities.challenge.cooldowns;

import com.openrubicon.core.api.cooldowns.Cooldown;
import com.openrubicon.core.helpers.Constants;

public class ChallengeCooldown extends Cooldown {

    public static final String COOLDOWN = Constants.COOLDOWN + "-combatsocket-challenge";

    public ChallengeCooldown(String id, String modifier) {
        super(id, modifier);
        this.setModuleName("combatsocket-challenge");
        this.setId(id, modifier);
    }
}
