package hu.shiya.staffGui.listeners;

import hu.shiya.staffGui.StaffGui;
import hu.shiya.staffGui.commands.StaffCommand;
import hu.shiya.staffGui.classes.StaffData;
import hu.shiya.staffGui.utility.OriginalGameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.UUID;

public class LeaveListener implements Listener {
    private final StaffCommand command;
    private final StaffGui plugin;
    public LeaveListener( final StaffCommand command, final StaffGui plugin ) {
        this.command = command;
        this.plugin = plugin;
    }
    @EventHandler
    public void onLeave(final PlayerQuitEvent event) {
        try {
            Player player = event.getPlayer();
            HashMap<UUID, StaffData> staffData = plugin.getStaffPlayers();
            OriginalGameMode.originalGameMode(player, staffData.get(player.getUniqueId()), plugin);
        } catch (Exception e) {
            plugin.getLogger().severe( e.getMessage() );
        }
    }
}
