package com.openrubicon.combat.sockets.effects;

import com.openrubicon.combat.classes.attacks.BasicAttack;
import com.openrubicon.combat.sockets.CombatSocket;
import com.openrubicon.core.api.actionbar.ActionBarManager;
import com.openrubicon.core.api.actionbar.ActionBarMessage;
import com.openrubicon.core.api.attributes.AttributeModifier;
import com.openrubicon.core.api.attributes.AttributeModifiers;
import com.openrubicon.core.api.attributes.enums.AttributeModifierType;
import com.openrubicon.core.api.inventory.entities.enums.EntityInventorySlotType;
import com.openrubicon.core.api.nbt.NBT;
import com.openrubicon.core.events.PlayerLookingAtEntityEvent;
import com.openrubicon.core.helpers.Constants;
import com.openrubicon.core.helpers.MaterialGroups;
import com.openrubicon.items.classes.items.unique.UniqueItem;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.HashSet;

public class Jarvis extends CombatSocket {
    @Override
    public String getKey() {
        return "jarvis";
    }

    @Override
    public HashSet<Material> getMaterials() {
        return MaterialGroups.HELMETS;
    }

    @Override
    public String getName() {
        return "Jarvis";
    }

    @Override
    public String getDescription() {
        return "Alerts you of your chance of survival when taking on a target";
    }

    @Override
    public void onPlayerLookingAtEntity(PlayerLookingAtEntityEvent e, UniqueItem item, EntityInventorySlotType slot) {
        if(e.getLivingEntity() == null)
            return;

        String jarvis = Constants.HEADING_COLOR + Constants.BOLD + e.getLivingEntity().getName() + ": ";

        ItemStack myWeapon = e.getPlayer().getEquipment().getItemInMainHand();
        ItemStack yourWeapon = e.getLivingEntity().getEquipment().getItemInMainHand();

        double attackingDamage;
        double defendingDamage;

        if(myWeapon == null) {
            attackingDamage = 1;
        } else {
            AttributeModifiers attackingAttributeModifiers = new AttributeModifiers();
            attackingAttributeModifiers.load(new NBT(myWeapon));
            if(attackingAttributeModifiers.getAttribute(AttributeModifierType.ATTACK_DAMAGE) == null)
                attackingDamage = 1;
            else
                attackingDamage = attackingAttributeModifiers.getAttribute(AttributeModifierType.ATTACK_DAMAGE).getAmount();
        }

        if(yourWeapon == null) {
            defendingDamage = 1;
        } else {
            AttributeModifiers defendingAttributeModifiers = new AttributeModifiers();
            defendingAttributeModifiers.load(new NBT(yourWeapon));
            if(defendingAttributeModifiers.getAttribute(AttributeModifierType.ATTACK_DAMAGE) == null)
                defendingDamage = 1;
            else
                defendingDamage = defendingAttributeModifiers.getAttribute(AttributeModifierType.ATTACK_DAMAGE).getAmount();
        }

        BasicAttack attackingBasicAttack = new BasicAttack(e.getPlayer(), e.getLivingEntity(), attackingDamage, false);
        BasicAttack defendingBasicAttack = new BasicAttack(e.getLivingEntity(), e.getPlayer(), defendingDamage, false);

        attackingBasicAttack.prepareSimulatulation();
        defendingBasicAttack.prepareSimulatulation();

        int attackingPierceChance = attackingBasicAttack.getPercentPiece();
        int defendingPierceChance = defendingBasicAttack.getPercentPiece();

        int pierceDifference = attackingPierceChance - defendingPierceChance;
        double damageDifference = attackingDamage - defendingDamage;

        damageDifference *= 4;

        if(attackingDamage <= 1 && damageDifference > 3)
            damageDifference += 20;

        int attackingHitsToDeath = (int)Math.ceil(e.getLivingEntity().getHealth() / attackingDamage);
        int defendingHitsToDeath = (int)Math.ceil(e.getPlayer().getHealth() / defendingDamage);

        int hitsToDeathDifference = defendingHitsToDeath - attackingHitsToDeath;
        hitsToDeathDifference *= 4;

        double winFactor = pierceDifference + damageDifference + hitsToDeathDifference;

        if(winFactor < -20)
        {
            jarvis += Constants.RED + Constants.BOLD + " DIFFICULT";
        } else if(winFactor > 20)
        {
            jarvis += Constants.GREEN + Constants.BOLD + " EASY";
        } else {
            jarvis += Constants.YELLOW + Constants.BOLD + " MODERATE";
        }

        ActionBarMessage actionBarMessage = new ActionBarMessage(jarvis, 10);
        ActionBarManager.interrupt(e.getPlayer(), actionBarMessage);
    }
}
