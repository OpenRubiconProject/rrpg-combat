package com.openrubicon.combat.events.attacks;

import com.openrubicon.core.api.events.Event;

public class PostAttackEvent extends Event {

    private AttackEvent attack;

    public PostAttackEvent(AttackEvent attack) {
        this.attack = attack;
    }

    public AttackEvent getAttackEvent() {
        return attack;
    }
}
