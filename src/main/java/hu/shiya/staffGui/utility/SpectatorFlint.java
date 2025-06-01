package hu.shiya.staffGui.utility;

import hu.shiya.staffGui.StaffGui;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class SpectatorFlint {
    public static ItemStack createSpectatorFlint(JavaPlugin plugin) {
        ItemStack item = new ItemStack(Material.FLINT);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(ChatColor.GRAY + "Spectator Flint" );
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Moves you to spectator gamemode on right click.");
        meta.setLore(lore);

        NamespacedKey key = new NamespacedKey(plugin, "staff_item");
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "spectator_flint");
        item.setItemMeta(meta);

        return item;
    }
}

