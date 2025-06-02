package hu.shiya.staffGui.utility;

import hu.shiya.staffGui.services.StaffData;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class StaffGameMode {
    public static void applyStaffMode(JavaPlugin plugin, Player player) {
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

    public static void afterSpecStaffMode(JavaPlugin plugin, Player player, StaffData staffData) {
        player.setGameMode(staffData.getGameMode());
        player.setFoodLevel(20);
        player.setSaturation(20);
        player.setInvulnerable(true);
        player.setAllowFlight(true);

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            player.setFoodLevel(20);
            player.setSaturation(20);
        }, 0L, 20L);
    }
}
