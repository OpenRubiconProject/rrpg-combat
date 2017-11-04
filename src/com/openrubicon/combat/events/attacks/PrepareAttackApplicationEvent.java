package com.openrubicon.combat.events.attacks;

import com.openrubicon.core.api.events.Event;

public class PrepareAttackApplicationEvent extends Event {

    private AttackEvent attack;

    public PrepareAttackApplicationEvent(AttackEvent attack) {
        this.attack = attack;
    }

    public AttackEvent getAttackEvent() {
        return attack;
    }
}
