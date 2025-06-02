package hu.shiya.staffGui.listeners;

import hu.shiya.staffGui.StaffGui;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.UUID;

public class BlockListeners implements Listener {
    private final StaffGui staffGui;

    public BlockListeners( final StaffGui staffGui ) {
        this.staffGui = staffGui;
    }

    @EventHandler
    public void onBlockBreak( final BlockBreakEvent event ) {
        Player player = event.getPlayer();
        UUID playerID = player.getUniqueId();
        if (staffGui.isPlayerStaff( playerID )) {
            event.setCancelled( true );
        }
    }
    @EventHandler
    public void onBlockPlace( final BlockPlaceEvent event ) {
        Player player = event.getPlayer();
        UUID playerID = player.getUniqueId();
        if (staffGui.isPlayerStaff( playerID )) {
            event.setCancelled( true );
        }
    }
}
