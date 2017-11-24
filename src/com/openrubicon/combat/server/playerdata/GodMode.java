package com.openrubicon.combat.server.playerdata;

import com.openrubicon.core.api.server.players.interfaces.PlayerData;

public class GodMode implements PlayerData {
    private boolean god = false;

    public GodMode() {
    }

    public GodMode(boolean god) {
        this.god = god;
    }

    public boolean isGod() {
        return god;
    }

    public void setGod(boolean god) {
        this.god = god;
    }

}
