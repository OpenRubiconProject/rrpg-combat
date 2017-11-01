package com.openrubicon.combat;

import com.openrubicon.combat.events.EventListener;
import com.openrubicon.combat.sockets.effects.Jarvis;
import com.openrubicon.combat.sockets.enchants.Reinforced;
import com.openrubicon.combat.sockets.events.SocketEventListener;
import com.openrubicon.core.RRPGCore;
import com.openrubicon.core.api.command.Command;
import com.openrubicon.core.api.configuration.ConfigurationProperty;
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
        return new ArrayList<>();
    }

    @Override
    public ArrayList<PlayerData> getPlayerDatas() {
        return new ArrayList<>();
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
        RRPGCore.services.getSerivce(SocketProvider.class).add(new Reinforced());
        getLogger().info("Sockets Injected.");
    }

    @Override
    public void onDisable()
    {

    }

    private void loadServices()
    {
        getLogger().info("Establishing Services..");


        getLogger().info("Established Services.");
    }
}