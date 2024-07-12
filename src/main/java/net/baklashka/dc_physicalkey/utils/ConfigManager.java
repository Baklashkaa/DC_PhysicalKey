package net.baklashka.dc_physicalkey.utils;

import net.baklashka.dc_physicalkey.bootstrap.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class ConfigManager {

    private final File file;
    private YamlConfiguration config;
    public ConfigManager(Main plugin) {
        file = new File(plugin.getDataFolder(), "config.yml");
        if(!file.exists()) {
            plugin.saveResource("config.yml", false);
        }
        config = YamlConfiguration.loadConfiguration(file);
    }

    public YamlConfiguration getFile() {
        return config;
    }

    public void reloadConfig(){
        config = YamlConfiguration.loadConfiguration(file);
    }
}

