package hu.shiya.staffGui.commands;

import hu.shiya.staffGui.StaffGui;
import hu.shiya.staffGui.services.StaffData;
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
    public StaffCommand( final StaffGui plugin ) {
        this.plugin = plugin;
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

                switchToStaffMode(player);
                player.sendMessage("You are now in staff mode!");
            } else {
                switchToSurvival(player, staffData.get(player.getUniqueId()));
                player.sendMessage("You are now not in staff mode!");
            }
        }
        return true;
    }
    public void switchToStaffMode(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setItemInOffHand(null);
        player.updateInventory();
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.setInvulnerable(true);
        player.setAllowFlight(true);

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            player.setFoodLevel(20);
            player.setSaturation(20);
        }, 0L, 20L);
    }
    public void switchToSurvival(Player player, StaffData staffData) {
        player.getInventory().setContents(staffData.getInventory());
        player.getInventory().setArmorContents(staffData.getArmor());
        player.getInventory().setItemInOffHand(staffData.getOffHand());
        player.updateInventory();
        player.setGameMode(staffData.getGameMode());
        player.setInvulnerable(staffData.getWasVulnerable());
        player.setFoodLevel(staffData.getSavedFoodLevel());
        player.setSaturation(staffData.getSavedSaturation());
        player.setAllowFlight(false);
        plugin.getStaffPlayers().remove(player.getUniqueId());
        Bukkit.getScheduler().cancelTasks(plugin);
    }
}
