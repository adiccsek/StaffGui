package hu.shiya.staffGui.commands;

import hu.shiya.staffGui.StaffGui;
import hu.shiya.staffGui.services.StaffData;
import hu.shiya.staffGui.utility.BossBarManager;
import hu.shiya.staffGui.utility.OriginalGameMode;
import hu.shiya.staffGui.utility.StaffGameMode;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class StaffCommand implements CommandExecutor {
    final private StaffGui plugin;
    final private BossBarManager bossBarManager;
    public StaffCommand( final StaffGui plugin, final BossBarManager bossBarManager ) {
        this.plugin = plugin;
        this.bossBarManager = bossBarManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if ( commandSender instanceof Player player) {
            if (!player.hasPermission( "staff" )) {
                player.sendMessage("You do not have permission to use this command.");
                return true;
            }

            HashMap<UUID, StaffData> staffData = plugin.getStaffPlayers();
            if (!staffData.containsKey(player.getUniqueId())) {
                StaffData storedPlayer = new StaffData(player);
                plugin.setStaffMode(storedPlayer, player);

                StaffGameMode.applyStaffMode(plugin, player);
                player.sendMessage("You are now in staff mode!");
                bossBarManager.showStaffBar(player);

            } else {
                OriginalGameMode.originalGameMode(player, staffData.get(player.getUniqueId()), plugin);
                player.sendMessage("You are now not in staff mode!");
                bossBarManager.hideStaffBar(player);
            }
        }
        return true;
    }
}
