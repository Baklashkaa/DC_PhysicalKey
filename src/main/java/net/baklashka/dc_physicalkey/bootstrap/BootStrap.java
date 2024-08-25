package net.baklashka.dc_physicalkey.bootstrap;

import net.baklashka.dc_physicalkey.commands.Commands;
import net.baklashka.dc_physicalkey.utils.ConfigManager;
import net.baklashka.dc_physicalkey.listener.ListenerKey;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class BootStrap {
    private final Main plugin;
    private final ConfigManager configManager;
    private ListenerKey listenerKey;

    public BootStrap(Main plugin) {
        this.plugin = plugin;
        this.configManager = new  ConfigManager(plugin);
    }

    public void load() {
        listenerKey = new ListenerKey(configManager);
        new Commands(plugin.getCaseManager().getSubCommandManager(), configManager);
        Bukkit.getServer().getPluginManager().registerEvents(listenerKey, plugin.getPlugin());
    }

    public void unload() {
        plugin.getCaseManager().getSubCommandManager().unregisterSubCommand("physicalkey");
        HandlerList.unregisterAll(listenerKey);
    }
}
