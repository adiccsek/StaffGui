package hu.shiya.staffGui.services;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StaffData {
    private boolean staffMode;
    private ItemStack[] inventory;
    private ItemStack offHand;
    private ItemStack[] armor;
    private GameMode gamemode;
    private boolean wasVulnerable;
    private int savedFoodLevel;
    private float savedSaturation;

    public StaffData( Player player) {
        this.staffMode = true;
        this.inventory = player.getInventory().getContents();
        this.offHand = player.getInventory().getItemInOffHand();
        this.armor = player.getInventory().getArmorContents();
        this.gamemode = player.getGameMode();
        this.wasVulnerable = player.isInvulnerable();
        this.savedFoodLevel = player.getFoodLevel();
        this.savedSaturation = player.getSaturation();
    }
    public boolean getStaffMode() {
        return staffMode;
    }
    public ItemStack[] getInventory() {
        return inventory;
    }
    public ItemStack getOffHand() {
        return offHand;
    }
    public ItemStack[] getArmor() {
        return armor;
    }
    public GameMode getGameMode() {
        return gamemode;
    }
    public boolean getWasVulnerable() {
        return wasVulnerable;
    }
    public int getSavedFoodLevel() {
        return savedFoodLevel;
    }
    public float getSavedSaturation() {
        return savedSaturation;
    }
}

