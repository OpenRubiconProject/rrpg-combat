package com.openrubicon.combat;

import com.openrubicon.combat.commands.CombatLog;
import com.openrubicon.combat.commands.God;
import com.openrubicon.combat.events.EventListener;
import com.openrubicon.combat.scoreboard.ChallengeStatus;
import com.openrubicon.combat.scoreboard.CombatChance;
import com.openrubicon.combat.server.playerdata.GodMode;
import com.openrubicon.combat.sockets.abilities.Challenge;
import com.openrubicon.combat.sockets.effects.Evasion;
import com.openrubicon.combat.sockets.effects.Jarvis;
import com.openrubicon.combat.sockets.enchants.*;
import com.openrubicon.combat.sockets.events.SocketEventListener;
import com.openrubicon.core.RRPGCore;
import com.openrubicon.core.api.command.Command;
import com.openrubicon.core.api.configuration.ConfigurationProperty;
import com.openrubicon.core.api.scoreboard.ScoreboardSectionService;
import com.openrubicon.core.api.scoreboard.interfaces.ScoreboardSection;
import com.openrubicon.core.api.server.players.interfaces.PlayerData;
import com.openrubicon.core.interfaces.Module;
import com.openrubicon.items.classes.sockets.SocketProvider;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.LinkedList;

public class RRPGCombat extends JavaPlugin implements Module {

    public static Plugin plugin;

    @Override
    public String getKey() {
        return "rrpg-combat";
    }

    @Override
    public String getOverview() {
        return "Combat module";
    }

    @Override
    public String getConfiguration() {
        return this.getDataFolder().getAbsolutePath();
    }

    @Override
    public ArrayList<Command> getCommands() {
        ArrayList<Command> commands = new ArrayList<>();
        commands.add(new CombatLog());
        commands.add(new God());
        return commands;
    }

    @Override
    public ArrayList<ScoreboardSection> getScoreboardSections() {
        ArrayList<ScoreboardSection> scoreboardSections = new ArrayList<>();
        scoreboardSections.add(new CombatChance());
        scoreboardSections.add(new ChallengeStatus());
        return scoreboardSections;
    }

    @Override
    public ArrayList<PlayerData> getPlayerDatas() {
        ArrayList<PlayerData> playerDatas = new ArrayList<>();
        playerDatas.add(new GodMode());
        return playerDatas;
    }

    @Override
    public LinkedList<ConfigurationProperty> getConfigurationProperties() {
        return new LinkedList<>();
    }

    @Override
    public void onLoad()
    {
        RRPGCore.modules.addModule(this);
    }

    @Override
    public void onEnable()
    {
        RRPGCombat.plugin = this;

        getServer().getPluginManager().registerEvents(new EventListener(), this);
        getServer().getPluginManager().registerEvents(new SocketEventListener(), this);
        getLogger().info("Registered Events");

        getLogger().info("Injecting Sockets..");
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Jarvis());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Evasion());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Reinforced());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Challenge());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Piercing());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Strength());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Thickness());
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Thornmail());
        getLogger().info("Sockets Injected.");
    }

    @Override
    public void onDisable()
    {

    }

}
