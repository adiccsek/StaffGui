package hu.shiya.staffGui.listeners;

import hu.shiya.staffGui.StaffGui;
import hu.shiya.staffGui.services.StaffData;
import hu.shiya.staffGui.utility.OriginalGameMode;
import hu.shiya.staffGui.utility.StaffGameMode;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.util.RayTraceResult;

import java.util.HashMap;
import java.util.UUID;

public class UtilAbilities implements Listener {
    private final NamespacedKey key;
    private final StaffGui plugin;
    public UtilAbilities(JavaPlugin plugin, final StaffGui staffGui) {
        this.key = new NamespacedKey( plugin, "staff_item" );
        this.plugin = staffGui;
    }

    @EventHandler
    public void onUseSpectatorFlint(final PlayerInteractEvent event) {
        Player player = event.getPlayer();

        Action action = event.getAction();
        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || !item.hasItemMeta()) { return; }
        ItemMeta meta = item.getItemMeta();

        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (container.has(key, PersistentDataType.STRING)) {
            String value = container.get(key, PersistentDataType.STRING);

            if ("spectator_flint".equals(value)) {
                if (player.getGameMode() != GameMode.SPECTATOR) {
                    event.setCancelled(true);
                    player.setGameMode(GameMode.SPECTATOR);
                    player.sendMessage("You are now in specator mode!");
                } else {
                    event.setCancelled(true);
                    player.setGameMode(GameMode.SURVIVAL); // might need to save what was before.
                    player.sendMessage("You are now not in specator mode!");
                }

            }
        }
    }
    @EventHandler
    public void onInventoryUseSpectatorFlint(final InventoryClickEvent event) {
        HashMap<UUID, StaffData> staffData = plugin.getStaffPlayers();
        Player player = (Player) event.getWhoClicked();

        if (!event.isRightClick() && !event.isLeftClick()) { return; }

        ItemStack item = event.getCurrentItem();
        if (item == null || !item.hasItemMeta()) { return; }
        ItemMeta meta = item.getItemMeta();
        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (container.has(key, PersistentDataType.STRING)) {
            String value = container.get(key, PersistentDataType.STRING);
            if ("spectator_flint".equals(value) && player.getGameMode() == GameMode.SPECTATOR) {
                    StaffGameMode.afterSpecStaffMode(plugin, player, staffData.get(player.getUniqueId()));
            }
        }
    }
    @EventHandler
    public void onUseVanishFeather(final PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();

        if (item == null || !item.hasItemMeta()) { return; }
        ItemMeta meta = item.getItemMeta();

        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (container.has(key, PersistentDataType.STRING)) {
            String value = container.get(key, PersistentDataType.STRING);


            PotionEffect vanish = new PotionEffect(
                    PotionEffectType.INVISIBILITY,
                    6000,
                    3,
                    false,
                    false
            );

            if ("vanish_feather".equals(value)) {
                if (!player.hasPotionEffect(PotionEffectType.INVISIBILITY)) {
                    event.setCancelled(true);
                    player.addPotionEffect(vanish);
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        online.hidePlayer(plugin, player);
                    }
                } else {
                    event.setCancelled(true);
                    player.removePotionEffect(PotionEffectType.INVISIBILITY);
                    for (Player online : Bukkit.getOnlinePlayers()) {
                        online.showPlayer(plugin, player);
                    }
                    player.sendMessage("You are no longer in vanish mode!");
                }

            }
        }
    }

    @EventHandler
    public void onUseTeleportRod(final PlayerInteractEvent event) {
        Player player = event.getPlayer();
        Action action = event.getAction();

        if (action != Action.RIGHT_CLICK_AIR && action != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        if (item == null || !item.hasItemMeta()) { return; }
        ItemMeta meta = item.getItemMeta();

        PersistentDataContainer container = meta.getPersistentDataContainer();
        if (container.has(key, PersistentDataType.STRING)) {
            String value = container.get(key, PersistentDataType.STRING);

            if ("teleport_rod".equals(value)) {
                RayTraceResult result = player.rayTraceBlocks(60);
                if (result != null) {
                    Location lookLocation = result.getHitPosition().toLocation(player.getWorld());
                    player.teleport(lookLocation);
                }
            }
        }
    }
}
