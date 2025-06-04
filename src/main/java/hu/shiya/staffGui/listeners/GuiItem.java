package hu.shiya.staffGui.listeners;

import hu.shiya.staffGui.StaffGui;
import hu.shiya.staffGui.classes.StaffData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class GuiItem implements Listener {
    private final StaffGui plugin;

    public GuiItem( final StaffGui plugin ) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemClick(final InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player player)) return;

        if (!event.getView().getTitle().equals("Staff Items")) return;
        event.setCancelled(true);

        HashMap<UUID, StaffData> staffData = plugin.getStaffPlayers();
        if (!staffData.containsKey(player.getUniqueId())) {
            player.sendMessage("You are not in staff mode.");
            return;
        }

        if (!event.getClickedInventory().equals(event.getView().getTopInventory())) return;

        ItemStack clickedItem = event.getCurrentItem();
        if (clickedItem == null || clickedItem.getType().isAir()) return;

        for (ItemStack invItem : player.getInventory().getContents()) {
            if (invItem != null && clickedItem.isSimilar(invItem)) {
                player.sendMessage("You already have this item!");
                return;
            }
        }

        player.getInventory().addItem(clickedItem.clone());
        player.updateInventory();
        player.sendMessage("You have received " + clickedItem.getItemMeta().getDisplayName());
    }
}
