package hu.shiya.staffGui.listeners;

import hu.shiya.staffGui.StaffGui;
import hu.shiya.staffGui.services.StaffData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class GuiItem implements Listener {
    private final StaffGui plugin;

    public GuiItem( final StaffGui plugin ) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemClick( final InventoryClickEvent event ) {
        HashMap<UUID, StaffData> staffData = plugin.getStaffPlayers();
        if (!(event.getWhoClicked() instanceof Player player)) return;
        if (!staffData.containsKey(player.getUniqueId())) {
            event.setCancelled(true);
            return;
        }
        if (event.isRightClick() || event.isLeftClick() && event.getView().getTitle().equals("Staff Items")) {
            if (event.getClickedInventory().equals(event.getView().getTopInventory())) {
                for (ItemStack invItem : player.getInventory().getContents()) {
                    if (invItem != null && event.getCurrentItem().isSimilar(invItem)) {
                        event.setCancelled(true);
                        player.sendMessage("You already have this item!");
                        return;
                    }
                }
                ItemStack item = event.getCurrentItem();
                event.setCancelled(true);

                if (item == null || item.getType().isAir()) return;

                Inventory inventory = player.getInventory();

                inventory.addItem(item.clone());
                player.updateInventory();
                player.sendMessage("You have recieved " + item.getItemMeta().getDisplayName());
            }
        }
    }
}
