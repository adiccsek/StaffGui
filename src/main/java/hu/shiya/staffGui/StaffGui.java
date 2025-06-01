package hu.shiya.staffGui;

import org.bukkit.plugin.java.JavaPlugin;

public final class StaffGui extends JavaPlugin {

    @Override
    public void onEnable() {
        saveDefaultConfig();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
