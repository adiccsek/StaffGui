package hu.shiya.staffGui.services;

import hu.shiya.staffGui.StaffGui;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.ObjectInputFilter;

public class StorageManager {
    private final StaffGui pluginInstance;
    private final StorageProvider plugin;
    public StorageManager(String storageType, StaffGui pluginInstance) {
        this.pluginInstance = pluginInstance;
        if ("mysql".equalsIgnoreCase(storageType)) {
            this.plugin = new MySqlManager(pluginInstance);
        } else if ("config".equalsIgnoreCase(storageType)) {
            this.plugin = new ConfigManager(pluginInstance);
        } else {
            this.plugin = null;
            pluginInstance.getLogger().severe("Please use the correct storage type.");
        }
    }
    public StorageProvider getPlugin() {
        return plugin;
    }

}
