package hu.shiya.staffGui.services;

import hu.shiya.staffGui.StaffGui;
import hu.shiya.staffGui.classes.BannedPlayer;
import hu.shiya.staffGui.classes.MutedPlayer;
import hu.shiya.staffGui.classes.WarnedPlayer;
import hu.shiya.staffGui.utility.TimeHelper;
import org.bukkit.configuration.ConfigurationSection;

import java.util.UUID;

public class ConfigManager implements StorageProvider{
    private final StaffGui plugin;
    private  ConfigurationSection config;
    private String currentPath;
    public ConfigManager( final StaffGui plugin ) {
        this.plugin = plugin;
    }

    public int generateKey() {
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
           if( plugin.getConfig().getString("config-storage.logs" + i, null) == null ) {
               return i;
           }
        }
        return 0;
    }

    @Override
    public void connectStorage() {
        try {
            int key = generateKey();
            currentPath = "config-storage.logs" + key;
            config = plugin.getConfig().createSection( currentPath );
            plugin.getLogger().info("Config section created at: " + currentPath);
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
        plugin.saveConfig();
    }

    @Override
    public void saveWarningAsync(WarnedPlayer warnedPlayer) {
        ConfigurationSection warningsSection = plugin.getConfig().getConfigurationSection(currentPath + ".warnings");
        if (warningsSection == null) {
            warningsSection = plugin.getConfig().createSection(currentPath + ".warnings");
        }

        int nextId = warningsSection.getKeys(false).size();
        ConfigurationSection newWarning = warningsSection.createSection(String.valueOf(nextId));

        newWarning.set("uuid", warnedPlayer.getUuid());
        newWarning.set("playername", warnedPlayer.getName());
        newWarning.set("reason", warnedPlayer.getReason());
        newWarning.set("warned_by", warnedPlayer.getWarnedBy().toString());
        newWarning.set("timestamp", warnedPlayer.getTimestamp());
        newWarning.set("duration", warnedPlayer.getDuration());

        plugin.saveConfig();
    }

    @Override
    public WarnedPlayer loadWarningAsync(UUID uuid) {
        ConfigurationSection warningsSection = plugin.getConfig().getConfigurationSection(currentPath + ".warnings");
        if (warningsSection == null) {
            plugin.getLogger().severe("No warnings section found.");
            return null;
        }

        for (String key : warningsSection.getKeys(false)) {
            ConfigurationSection warning = warningsSection.getConfigurationSection(key);
            if (warning == null) continue;

            String storedUuid = warning.getString("uuid");
            if (storedUuid != null && storedUuid.equals(uuid.toString())) {
                WarnedPlayer warnedPlayer = new WarnedPlayer();
                warnedPlayer.setName(warning.getString("playername"));
                warnedPlayer.setReason(warning.getString("reason"));
                warnedPlayer.setWarnedBy(UUID.fromString(warning.getString("warned_by")));
                warnedPlayer.setTimestamp(warning.getLong("timestamp"));
                warnedPlayer.setDuration(warning.getInt("duration"));
                return warnedPlayer;
            }
        }
        return null;
    }

    @Override
    public void saveMuteAsync(MutedPlayer mutedPlayer) {
        ConfigurationSection mutesSection = plugin.getConfig().getConfigurationSection(currentPath + ".mutes");
        if (mutesSection == null) {
            mutesSection = plugin.getConfig().createSection(currentPath + ".mutes");
        }

        int nextId = mutesSection.getKeys(false).size();
        ConfigurationSection newMute = mutesSection.createSection(String.valueOf(nextId));

        newMute.set("uuid", mutedPlayer.getUuid().toString());
        newMute.set("playername", mutedPlayer.getName());
        newMute.set("ip", mutedPlayer.getIp());
        newMute.set("timestamp", mutedPlayer.getTimestamp());
        newMute.set("duration", mutedPlayer.getDuration());
        newMute.set("reason", mutedPlayer.getReason());
        newMute.set("muted_by", mutedPlayer.getMutedBy().toString());
        plugin.saveConfig();
    }

    @Override
    public MutedPlayer loadMuteAsync(UUID uuid) {
        ConfigurationSection mutesSection = plugin.getConfig().getConfigurationSection(currentPath + ".mutes");
        if (mutesSection == null) {
            return null;
        }

        for (String key : mutesSection.getKeys(false)) {
            ConfigurationSection mute = mutesSection.getConfigurationSection(key);
            if (mute == null) continue;
            if (mute.getString("uuid").equals(uuid.toString())) {
                MutedPlayer mutedPlayer = new MutedPlayer();
                mutedPlayer.setUuid(UUID.fromString(mute.getString("uuid")));
                mutedPlayer.setName(mute.getString("playername"));
                mutedPlayer.setIp(mute.getString("ip"));
                mutedPlayer.setTimestamp(mute.getLong("timestamp"));
                mutedPlayer.setDuration(mute.getInt("duration"));
                mutedPlayer.setReason(mute.getString("reason"));
                mutedPlayer.setMutedBy(UUID.fromString(mute.getString("muted_by")));
                return mutedPlayer;
            }
        }
        return null;
    }

    @Override
    public void saveBanAsync(BannedPlayer bannedPlayer) {
        ConfigurationSection bansSection = plugin.getConfig().getConfigurationSection(currentPath + ".bans");
        if (bansSection == null) {
            bansSection = plugin.getConfig().createSection(currentPath + ".bans");
        }

        int nextId = bansSection.getKeys(false).size();
        ConfigurationSection newBan = bansSection.createSection(String.valueOf(nextId));

        newBan.set("uuid", bannedPlayer.getUuid().toString());
        newBan.set("playername", bannedPlayer.getName());
        newBan.set("ip", bannedPlayer.getIp());
        newBan.set("timestamp", bannedPlayer.getTimestamp());
        newBan.set("duration", bannedPlayer.getDuration());
        newBan.set("reason", bannedPlayer.getReason());
        newBan.set("banned_by", bannedPlayer.getBannedBy().toString());
        plugin.saveConfig();
    }

    @Override
    public BannedPlayer loadBanAsync(UUID uuid) {
        ConfigurationSection bansSection = plugin.getConfig().getConfigurationSection(currentPath + ".bans");
        if (bansSection == null) return null;

        for (String key : bansSection.getKeys(false)) {
            ConfigurationSection ban = bansSection.getConfigurationSection(key);
            if (ban == null) continue;

            String storedUuid = ban.getString("uuid");
            if (storedUuid != null && storedUuid.equals(uuid.toString())) {
                long timestamp = ban.getLong("timestamp");
                long duration = ban.getLong("duration");

                if (!TimeHelper.isExpired(timestamp, duration)) {
                    BannedPlayer bannedPlayer = new BannedPlayer();
                    bannedPlayer.setName(ban.getString("playername"));
                    bannedPlayer.setIp(ban.getString("ip"));
                    bannedPlayer.setTimestamp(timestamp);
                    bannedPlayer.setDuration(duration);
                    bannedPlayer.setReason(ban.getString("reason"));
                    bannedPlayer.setBannedBy(UUID.fromString(ban.getString("banned_by")));
                    return bannedPlayer;
                }
            }
        }
        return null;
    }
}
