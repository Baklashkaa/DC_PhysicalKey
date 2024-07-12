package net.baklashka.dc_physicalkey;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ConfigManager {
    /**
     * Чтобы юзать эту парашу, тебе надо в своём главном классе (onEnable) прописать вот такую фигню: <p>
     * new ConfigManager(this).setup();
     * <p>
     * Там, где хочешь юзать этот конфиг (Data.yml) нужно вверху всех методов прописать такое: <p>
     * ConfigManager configmanager = new ConfigManager(plugin); где plugin - instance твоего плагина <p>
     * Ну а дальше юзаешь вот так: configmanager.getData(); - чтобы получить конфиг или configmanager.saveData(); - чтобы сохранить
     */
    public static File data;
    public static File config;
    public static YamlConfiguration dataconfig;
    public static YamlConfiguration configfile;
    public static Plugin plugin;
    public ConfigManager(Plugin instance) {
        plugin = instance;
        data = new File(Bukkit.getServer().getPluginManager().getPlugin(plugin.getName()).getDataFolder(), "data.yml");
        dataconfig = YamlConfiguration.loadConfiguration(data);
        config = new File(Bukkit.getServer().getPluginManager().getPlugin(plugin.getName()).getDataFolder(), "config.yml");
        configfile = YamlConfiguration.loadConfiguration(config);
    }
    public static FileConfiguration getData() {
        return dataconfig;
    }
    public static FileConfiguration getConfig() {
        return configfile;
    }
    public static void saveData() {
        try {
            dataconfig.save(data);
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save data.yml!");
            throw new RuntimeException(e);
        }
    }
    public static void saveConfig() {
        try {
            configfile.save(config);
        } catch (IOException e) {
            plugin.getLogger().warning("Failed to save config.yml!");
            throw new RuntimeException(e);
        }
    }
    public static void reloadData() {
        dataconfig = YamlConfiguration.loadConfiguration(data);
    }
    public static void reloadConfig(){
        configfile = YamlConfiguration.loadConfiguration(config);
    }
}

