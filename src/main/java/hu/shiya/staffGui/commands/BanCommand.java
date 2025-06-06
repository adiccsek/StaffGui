package hu.shiya.staffGui.commands;

import hu.shiya.staffGui.StaffGui;
import hu.shiya.staffGui.classes.BannedPlayer;
import hu.shiya.staffGui.services.StorageManager;
import hu.shiya.staffGui.services.StorageProvider;
import hu.shiya.staffGui.utility.TimeHelper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.sql.Time;
import java.time.Duration;
import java.time.Instant;

public class BanCommand implements CommandExecutor {
    private final StaffGui plugin;
    public BanCommand( final StaffGui plugin ) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] args) {
        if ( commandSender instanceof Player player) {
            if (!player.hasPermission("staff-ban")) {
                player.sendMessage( ChatColor.RED + "You don't have permission to use this command!");
                return true;
            }
            try {
                String playerToBan = args[0];
                Player target = plugin.getServer().getPlayer(playerToBan);
                if (target == null) {
                    player.sendMessage(ChatColor.RED + "Player not found!");
                    return true;
                }
                String reason = args[1];


                BannedPlayer bannedPlayer = new BannedPlayer();
                bannedPlayer.setBannedBy(player.getUniqueId());
                bannedPlayer.setReason(reason);
                bannedPlayer.setIp(target.getAddress().getAddress().getHostAddress());
                bannedPlayer.setUuid(target.getUniqueId());
                bannedPlayer.setName(target.getName());
                bannedPlayer.setTimestamp(System.currentTimeMillis());
                bannedPlayer.setDuration(TimeHelper.parseTimeOffset(args[2]));

                // String storageType = plugin.getConfig().getString("storage-type");
                String storageType = "mysql";
                StorageManager storageManager = new StorageManager(storageType, plugin);

                Bukkit.getScheduler().runTaskAsynchronously( plugin, () -> {
                    storageManager.getPlugin().saveBanAsync(bannedPlayer);
                });
                player.sendMessage(ChatColor.GREEN + "Player " + target.getName() + " has been banned.");
                target.kickPlayer(ChatColor.RED + "You have been banned! \n Reason: " + reason);

            } catch (Exception e) {
                plugin.getLogger().severe( e.getMessage() );
            }
        }
        return true;
    }
}
