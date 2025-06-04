package hu.shiya.staffGui.services;

import hu.shiya.staffGui.StaffGui;
import hu.shiya.staffGui.classes.BannedPlayer;
import hu.shiya.staffGui.classes.MutedPlayer;
import hu.shiya.staffGui.classes.WarnedPlayer;
import org.bukkit.configuration.ConfigurationSection;

public class ConfigManager implements StorageProvider{
    private final StaffGui plugin;
    public ConfigManager( final StaffGui plugin ) {
        this.plugin = plugin;
    }

    @Override
    public void connectStorage() {
        try {
            ConfigurationSection config = plugin.getConfig().getConfigurationSection("config-storage.logs");
            if (config != null) {
                String message = "Config found!";
                plugin.getLogger().info(message);
            }
        } catch (Exception e) {
            plugin.getLogger().severe(e.getMessage());
        }
    }

    @Override
    public void disconnectStorage() {
        String message = "Disconnected from config storage";
        plugin.getLogger().info( message );
    }

    @Override
    public void saveWarningAsync(WarnedPlayer warnedPlayer) {

    }

    @Override
    public WarnedPlayer loadWarningAsync() {
        return null;
    }

    @Override
    public void saveMuteAsync(MutedPlayer mutedPlayer) {

    }

    @Override
    public MutedPlayer loadMuteAsync() {
        return null;
    }

    @Override
    public void saveBanAsync(BannedPlayer bannedPlayer) {

    }

    @Override
    public BannedPlayer loadBanAsync() {
        return null;
    }
}
