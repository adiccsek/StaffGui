package hu.shiya.staffGui.services;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class StaffData {
    private boolean staffMode;
    private ItemStack[] inventory;
    private ItemStack offHand;
    private ItemStack[] armor;

    public StaffData( Player player) {
        this.staffMode = true;
        this.inventory = player.getInventory().getContents();
        this.offHand = player.getInventory().getItemInOffHand();
        this.armor = player.getInventory().getArmorContents();
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
    public void setStaffMode( boolean staffMode ) {
        this.staffMode = staffMode;
    }
}

