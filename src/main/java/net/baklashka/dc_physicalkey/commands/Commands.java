package net.baklashka.dc_physicalkey.commands;

import com.jodexindustries.donatecase.api.SubCommandManager;
import com.jodexindustries.donatecase.api.data.SubCommandType;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommand;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommandExecutor;
import com.jodexindustries.donatecase.api.data.subcommand.SubCommandTabCompleter;
import net.baklashka.dc_physicalkey.utils.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class Commands implements SubCommandExecutor, SubCommandTabCompleter {
    public ConfigManager configManager;

    public Commands(SubCommandManager manager, ConfigManager configManager) {
        this.configManager = configManager;
        SubCommand subCommand = manager.builder("phisicalkey")
                .type(SubCommandType.PLAYER)
                .executor(this)
                .tabCompleter(this)
                .build();
        manager.registerSubCommand(subCommand);
    }

    @Override
    public void execute(CommandSender sender, String label, String[] args) {
        if(args.length >= 1) {
            if (args[0].equalsIgnoreCase("givekey") && sender.hasPermission("dc_physicalkey.give")) {
                if (args.length <= 3) {
                    sender.sendMessage(ChatColor.GREEN + "=============HELP=============");
                    sender.sendMessage(ChatColor.BLUE + "/dc physicalkey givekey" + ChatColor.GREEN + " <player name> <case type> <amount> " + ChatColor.AQUA + "-" + ChatColor.DARK_GREEN + " give the player a physical key");
                    sender.sendMessage(ChatColor.BLUE + "/dc physicalkey reload " + ChatColor.AQUA + "-" + ChatColor.DARK_GREEN + " reload plugin config");
                } else {
                    String playername = args[1];
                    String amountst = args[3];
                    String type = args[2];
                    int amount = Integer.parseInt(amountst);
                    Player p = Bukkit.getServer().getPlayer(playername);
                    ItemStack item = new ItemStack(Material.TRIPWIRE_HOOK, amount);
                    YamlConfiguration config = configManager.getFile();
                    if (config != null && config.contains("Key.physical-key-lore") && config.contains("Key.physical-key-name")) {
                        String name = ChatColor.translateAlternateColorCodes('&', config.getString("Key.physical-key-name"));
                        List<String> lore = config.getStringList("Key.physical-key-lore");
                        lore.add(" ");
                        lore.add(config.getString("Key.physical-key-lore-case-type") + ": " + type);
                        ItemMeta meta = item.getItemMeta();
                        if (meta != null) {
                            meta.setDisplayName(name);
                            meta.setLore(lore);
                            item.setItemMeta(meta);
                        } else {
                            sender.sendMessage(ChatColor.RED + "ERROR! (001)");
                        }
                    } else {
                        sender.sendMessage(ChatColor.RED + "ERROR! (002)");
                    }
                    if (p != null) { // Check if the player is not null
                        p.getInventory().addItem(item);
                        String amountstring = Integer.toString(amount);
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("give-key-message").replaceAll("%player%", playername).replaceAll("%amount%", amountstring)));
                    } else {
                        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("player-not-found").replaceAll("%player%", playername)));
                    }
                }
            }
            if (args[0].equalsIgnoreCase("reload") && sender.hasPermission("dc_physicalkey.reload")) {
                configManager.reloadConfig();
                sender.sendMessage(ChatColor.GREEN + "The config has been successfully reloaded!");
            }
        }
    }

    @Override
    public List<String> getTabCompletions(CommandSender sender, String label, String[] args) {
        if(args.length == 1){
            List<String> arguments = new ArrayList<>();
            arguments.add("givekey");
            arguments.add("reload");
            return arguments;
        }
        if (args.length == 2) {
            List<String> list = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                list.add(p.getName());
            }
            return list;
        }
        if(args.length == 3 || args.length == 4){
            return new ArrayList<>();
        }
        return null;
    }

}
