package net.baklashka.dc_physicalkey;

import com.jodexindustries.donatecase.api.SubCommandManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class DC_PhysicalKey extends JavaPlugin {
    public static Plugin plugin;

    @Override
    public void onEnable() {
        plugin = this;
        SubCommandManager.registerSubCommand("physicalkey", new Commands());
        Bukkit.getServer().getPluginManager().registerEvents(new ListenerKey(), this);
        if(!new File(Bukkit.getServer().getPluginManager().getPlugin(plugin.getName()).getDataFolder(), "config.yml").exists()) {
            saveResource("config.yml", false);
        }
        new ConfigManager(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
