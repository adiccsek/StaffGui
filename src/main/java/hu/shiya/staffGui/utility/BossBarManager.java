package hu.shiya.staffGui.utility;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.UUID;

public class BossBarManager {
    private final HashMap<UUID, BossBar> bossBars = new HashMap<>();

    public void showStaffBar(Player player) {
        BossBar bar = Bukkit.createBossBar("§fYou are in §lSTAFF MODE", BarColor.WHITE, BarStyle.SOLID);
        bar.setVisible(true);
        bar.addPlayer(player);
        bossBars.put(player.getUniqueId(), bar);
    }

    public void hideStaffBar(Player player) {
        BossBar bar = bossBars.remove(player.getUniqueId());
        if (bar != null) {
            bar.removeAll();
        }
    }

    public boolean hasBar(Player player) {
        return bossBars.containsKey(player.getUniqueId());
    }
}
