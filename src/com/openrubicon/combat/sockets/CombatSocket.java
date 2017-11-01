package com.openrubicon.combat.sockets;

import com.openrubicon.combat.events.PrepareAttackPointsEvent;
import com.openrubicon.combat.events.PrepareDefensePointsEvent;
import com.openrubicon.combat.events.attacks.*;
import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.Socket;

abstract public class CombatSocket extends Socket {

    public void onAttack(AttackEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    public void onBasicAttack(BasicAttackEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    public void onChannelAttack(ChannelAttackEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    public void onChargeAttack(ChargeAttackEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    public void onSweepAttack(SweepAttackEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    public void onPrepareDefensePoints(PrepareDefensePointsEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    public void onPrepareAttackPoints(PrepareAttackPointsEvent e, UniqueItem item, EntityInventorySlotType slot) {}

}
