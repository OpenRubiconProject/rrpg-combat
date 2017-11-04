package com.openrubicon.combat.events.attacks;

import com.openrubicon.core.api.events.Event;

public class PostAttackSimulateEvent extends Event {

    private AttackEvent attack;

    public PostAttackSimulateEvent(AttackEvent attack) {
        this.attack = attack;
    }

    public AttackEvent getAttackEvent() {
        return attack;
    }
}
