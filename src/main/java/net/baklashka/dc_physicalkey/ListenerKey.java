package net.baklashka.dc_physicalkey;

import com.jodexindustries.donatecase.api.events.PreOpenCaseEvent;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import com.jodexindustries.donatecase.api.Case;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ListenerKey implements Listener {
    @EventHandler
    public void onCaseInteract(PreOpenCaseEvent event){
        Player p = event.getPlayer();
        FileConfiguration config = ConfigManager.getConfig();

        for (ItemStack item : p.getInventory().getContents()) {
            if (item != null) {
                if(item.getType() == Material.TRIPWIRE_HOOK && item.getItemMeta().getDisplayName().contains(ChatColor.translateAlternateColorCodes('&', config.getString("Key.physical-key-name")))){
                    ItemMeta meta = item.getItemMeta();
                    int size = meta.getLore().size();
                    String type = meta.getLore().get(size - 1).split(": ")[1];
                    if(Case.hasCaseByName(type)) {
                        Case.addKeys(type, p.getDisplayName(), 1);
                        item.setAmount(item.getAmount() - 1);
                        p.updateInventory();
                    }
                }
            }
        }
    }
}
