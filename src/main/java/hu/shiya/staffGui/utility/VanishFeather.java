package hu.shiya.staffGui.utility;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class VanishFeather {
    public static ItemStack createVanishFeather(JavaPlugin plugin) {
        ItemStack item = new ItemStack(Material.FEATHER);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName( "Vanish Feather" );
        ArrayList<String> lore = new ArrayList<>();
        lore.add( "Vanish Feather" );
        meta.setLore( lore );

        NamespacedKey key = new NamespacedKey( plugin, "staff_item" );
        meta.getPersistentDataContainer().set(key, PersistentDataType.STRING, "vanish_feather");
        item.setItemMeta( meta );

        
        return item;
    };
}
