package net.baklashka.dc_physicalkey.bootstrap;

import com.jodexindustries.donatecase.api.CaseManager;
import org.bukkit.plugin.Plugin;

import java.io.File;

public interface Main {
    Plugin getPlugin();
    CaseManager getCaseManager();
    File getDataFolder();
    void saveResource(String resource, boolean replace);
}
