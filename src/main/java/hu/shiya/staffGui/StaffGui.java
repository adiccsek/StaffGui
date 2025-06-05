package hu.shiya.staffGui;

import hu.shiya.staffGui.commands.StaffCommand;
import hu.shiya.staffGui.gui.StaffItems;
import hu.shiya.staffGui.listeners.BlockListeners;
import hu.shiya.staffGui.listeners.GuiItem;
import hu.shiya.staffGui.listeners.LeaveListener;
import hu.shiya.staffGui.listeners.UtilAbilities;
import hu.shiya.staffGui.classes.StaffData;
import hu.shiya.staffGui.services.StorageManager;
import hu.shiya.staffGui.utility.BossBarManager;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

public final class StaffGui extends JavaPlugin {
    private StaffCommand command;
    private final HashMap<UUID, StaffData> staffPlayers = new HashMap<>();

    @Override
    public void onEnable() {
        saveDefaultConfig();
        getConfig().options().copyDefaults(true);
        saveConfig();
        this.command = new StaffCommand(this, new BossBarManager());
        getServer().getPluginManager().registerEvents(new BlockListeners(this), this);
        getServer().getPluginManager().registerEvents(new LeaveListener(command, this), this);
        getServer().getPluginManager().registerEvents(new GuiItem(this), this);
        getServer().getPluginManager().registerEvents(new UtilAbilities(this, this), this);
        getCommand("staff").setExecutor(new StaffCommand(this, new BossBarManager()));
        getCommand("staffitems").setExecutor(new StaffItems(this));

        //String storageType = this.getConfig().getString("storage-type");
        String storageType = "mysql";
        StorageManager storageManager = new StorageManager(storageType, this);
        getLogger().info("Storage type from config: " + storageType); // Debug log
        storageManager.getPlugin().connectStorage();

    }

    @Override
    public void onDisable() {
        String storageType = this.getConfig().getString("storage-type");
        StorageManager storageManager = new StorageManager(storageType, this);
        storageManager.getPlugin().disconnectStorage();
    }

    public void setStaffMode(StaffData staffData, Player player) {
        if (staffData.getStaffMode()) {
            staffPlayers.put(player.getUniqueId(), staffData);
        }
    }

    public HashMap<UUID, StaffData> getStaffPlayers() {
        return staffPlayers;
    }

    public boolean isPlayerStaff( final UUID uuid ) {
        return staffPlayers.containsKey( uuid );
    }
}
