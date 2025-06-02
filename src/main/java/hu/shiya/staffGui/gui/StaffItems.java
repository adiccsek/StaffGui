package hu.shiya.staffGui.gui;

import hu.shiya.staffGui.StaffGui;
import hu.shiya.staffGui.services.StaffData;
import hu.shiya.staffGui.utility.SpectatorFlint;
import hu.shiya.staffGui.utility.TeleportRod;
import hu.shiya.staffGui.utility.VanishFeather;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class StaffItems implements CommandExecutor {
    private final StaffGui plugin;

    public StaffItems( final StaffGui plugin ) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String @NotNull [] strings) {
        HashMap<UUID, StaffData> staffData = plugin.getStaffPlayers();

        if ( commandSender instanceof Player player) {
            if ( staffData.containsKey( player.getUniqueId() ) ) {
                Inventory gui = Bukkit.createInventory(player, 9, "Staff Items");

                ItemStack spectatorFlint = SpectatorFlint.createSpectatorFlint(plugin);
                ItemStack teleportRod = TeleportRod.createTeleportRod(plugin);
                ItemStack vanishFeather = VanishFeather.createVanishFeather(plugin);

                gui.setItem( 1, spectatorFlint );
                gui.setItem( 4, teleportRod );
                gui.setItem( 7, vanishFeather );

                player.openInventory(gui);
            }
        }
        return true;
    }
}
