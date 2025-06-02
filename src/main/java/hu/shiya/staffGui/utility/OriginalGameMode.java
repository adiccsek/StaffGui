package hu.shiya.staffGui.utility;

import hu.shiya.staffGui.StaffGui;
import hu.shiya.staffGui.services.StaffData;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class OriginalGameMode {
    public static void originalGameMode(Player player, StaffData staffData, StaffGui plugin) {
        player.getInventory().setContents(staffData.getInventory());
        player.getInventory().setArmorContents(staffData.getArmor());
        player.getInventory().setItemInOffHand(staffData.getOffHand());
        player.updateInventory();
        player.setGameMode(staffData.getGameMode());
        player.setInvulnerable(staffData.getWasVulnerable());
        player.setFoodLevel(staffData.getSavedFoodLevel());
        player.setSaturation(staffData.getSavedSaturation());
        player.setAllowFlight(staffData.getWasAbleFly());
        player.setInvulnerable(staffData.getWasVulnerable());
        plugin.getStaffPlayers().remove(player.getUniqueId());
        Bukkit.getScheduler().cancelTasks(plugin);
    }
}
