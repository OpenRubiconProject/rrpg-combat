package com.openrubicon.combat.sockets;

import com.openrubicon.combat.events.attacks.AttackEvent;
import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import com.openrubicon.items.classes.sockets.Socket;

abstract public class CombatSocket extends Socket {

    public void onAttack(AttackEvent e, UniqueItem item, EntityInventorySlotType slot) {}

}
