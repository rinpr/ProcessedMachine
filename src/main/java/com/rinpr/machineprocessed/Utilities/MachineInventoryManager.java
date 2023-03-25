package com.rinpr.machineprocessed.Utilities;

import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class MachineInventoryManager {
    private final ItemStack slot1;
    private final ItemStack slot2;
    private final ItemStack slot3;
    private final ItemStack slot16;
    private final ItemStack slot20;
    public MachineInventoryManager(Inventory inventory) {
        this.slot1 = inventory.getItem(1) != null ? inventory.getItem(1) : new ItemStack(Material.AIR);
        this.slot2 = inventory.getItem(2) != null ? inventory.getItem(2) : new ItemStack(Material.AIR);
        this.slot3 = inventory.getItem(3) != null ? inventory.getItem(3) : new ItemStack(Material.AIR);
        this.slot16 = inventory.getItem(16) != null ? inventory.getItem(16) : new ItemStack(Material.AIR);
        this.slot20 = inventory.getItem(20) != null ? inventory.getItem(20) : new ItemStack(Material.AIR);
    }
    public ItemStack getIngredient1() { return slot1; }
    public ItemStack getIngredient2() { return slot2; }
    public ItemStack getIngredient3() { return slot3; }
    public ItemStack getFuel() { return slot16; }
    public ItemStack getProduct() { return slot20; }
}
