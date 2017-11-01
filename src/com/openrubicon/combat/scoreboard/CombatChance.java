package com.openrubicon.combat.scoreboard;

import com.openrubicon.combat.classes.attacks.BasicAttack;
import com.openrubicon.core.api.attributes.AttributeModifiers;
import com.openrubicon.core.api.attributes.enums.AttributeModifierType;
import com.openrubicon.core.api.enums.Priority;
import com.openrubicon.core.api.nbt.NBT;
import com.openrubicon.core.api.scoreboard.interfaces.ScoreboardSection;
import com.openrubicon.core.helpers.Constants;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

public class CombatChance implements ScoreboardSection{
    @Override
    public Priority getPriority() {
        return Priority.NORMAL;
    }

    @Override
    public String getTitle() {
        return "Combat";
    }

    @Override
    public ArrayList<String> getLines(Player player) {
        ArrayList<String> lines = new ArrayList<>();

        ItemStack myWeapon = player.getEquipment().getItemInMainHand();

        double attackingDamage;

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

        BasicAttack basicAttack = new BasicAttack(player, player, attackingDamage, false);
        basicAttack.prepareSimulatulation();

        lines.add(Constants.PRIMARY_COLOR + "Attack Points: " + Constants.SECONDARY_COLOR + (int)basicAttack.getAttackPoints());
        lines.add(Constants.PRIMARY_COLOR + "Defense Points: " + Constants.SECONDARY_COLOR + (int)basicAttack.getDefensePoints());
        lines.add(Constants.PRIMARY_COLOR + "Damage Estimate: " + Constants.SECONDARY_COLOR + (int)basicAttack.getRawDamage());

        return lines;
    }
}
