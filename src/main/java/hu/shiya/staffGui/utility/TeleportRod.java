package hu.shiya.staffGui.utility;

import hu.shiya.staffGui.StaffGui;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class TeleportRod {
    public static ItemStack createTeleportRod(JavaPlugin plugin) {
        ItemStack item = new ItemStack(Material.BLAZE_ROD);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName( " Teleport Rod " );
        ArrayList<String> lore = new ArrayList<>();
        lore.add("Teleports where you look.");
        meta.setLore( lore );

        NamespacedKey key = new NamespacedKey( plugin, "staff_item" );
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING,"teleport_rod" );
        item.setItemMeta( meta );

        return item;
    };
}
