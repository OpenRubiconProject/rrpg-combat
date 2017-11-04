package com.openrubicon.combat.classes.log;

import com.openrubicon.core.api.message.Message;

import java.util.Date;

public class CombatLogMessage extends Message {
    public CombatLogMessage(String message) {
        super(message);
    }

    public CombatLogMessage(String message, Date created_at) {
        super(message, created_at);
    }
}
