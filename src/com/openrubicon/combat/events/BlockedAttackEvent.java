package com.openrubicon.combat.events;

import com.openrubicon.combat.events.attacks.AttackEvent;
import com.openrubicon.core.api.events.Event;

public class BlockedAttackEvent extends Event {

    private AttackEvent attack;

    public BlockedAttackEvent(AttackEvent attack) {
        this.attack = attack;
    }

    public AttackEvent getAttack() {
        return attack;
    }
}
