package com.openrubicon.combat.sockets;

import com.openrubicon.combat.events.PrepareAttackPointsEvent;
import com.openrubicon.combat.events.PrepareDefensePointsEvent;
import com.openrubicon.combat.events.attacks.*;
import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.items.classes.items.unique.UniqueItem;

public interface CombatSocketEvents {

    default void onAttack(AttackEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onBasicAttack(BasicAttackEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onChannelAttack(ChannelAttackEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onChargeAttack(ChargeAttackEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onSweepAttack(SweepAttackEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onPrepareDefensePoints(PrepareDefensePointsEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onPrepareAttackPoints(PrepareAttackPointsEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onPostAttackSimulation(PostAttackSimulateEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onPrepareAttackApplication(PrepareAttackApplicationEvent e, UniqueItem item, EntityInventorySlotType slot) {}

    default void onPostAttack(PostAttackEvent e, UniqueItem item, EntityInventorySlotType slot) {}
}
