package hu.shiya.staffGui.listeners;

import hu.shiya.staffGui.StaffGui;
import hu.shiya.staffGui.classes.BannedPlayer;
import hu.shiya.staffGui.services.StorageManager;
import hu.shiya.staffGui.services.StorageProvider;
import hu.shiya.staffGui.utility.TimeHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class BanListeners implements Listener {
    private final StaffGui plugin;
    private final StorageManager storageManager;
    public BanListeners( final StaffGui plugin, final StorageManager storageManager ) {
        this.plugin = plugin;
        this.storageManager = storageManager;
    }


    @EventHandler
    public void onPlayerLogin( final PlayerLoginEvent event ) {
        final Player player = event.getPlayer();

        Bukkit.getScheduler().runTaskAsynchronously(plugin, () -> {
        // String storageType = plugin.getConfig().getString("storage-type");
        String storageType = "mysql";
        StorageManager storageManager = new StorageManager(storageType, plugin);
        StorageProvider provider = storageManager.getPlugin();

            BannedPlayer bannedPlayer = storageManager.getPlugin().loadBanAsync(player.getUniqueId());

            Bukkit.getScheduler().runTask(plugin, () -> {
                if (bannedPlayer != null) {
                    player.kickPlayer(bannedPlayer.getReason() + "Eddig:" + TimeHelper.formatTimestamp(bannedPlayer.getDuration() + bannedPlayer.getTimestamp()));
                }
            });
            //ITT egy async delete fuggvenyt kell majd csinalnom és providerre meghivnom
        });


    }
}
