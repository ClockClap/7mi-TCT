package me.clockclap.tct;

import me.clockclap.tct.api.Utilities;
import me.clockclap.tct.command.CommandAboutTCT;
import me.clockclap.tct.event.ChatEvent;
import me.clockclap.tct.event.PlayerConnectionEvent;
import me.clockclap.tct.event.ProtectWorld;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class NanamiTct extends JavaPlugin {

    public static NanamiTct plugin;

    @Override
    public void onEnable() {
        // Plugin startup logic
        plugin = this;
        getLogger().info("Starting up...");
        PluginManager pm = Bukkit.getServer().getPluginManager();
        Utilities util = NanamiTctApi.utilities;

        //Register Events
        pm.registerEvents(new PlayerConnectionEvent(), this);
        pm.registerEvents(new ChatEvent(), this);
        pm.registerEvents(new ProtectWorld(), this);

        //Add Commands
        util.addCommand("abouttct", new CommandAboutTCT());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("Shutting down...");
    }
}
