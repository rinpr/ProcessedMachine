package com.rinpr.machineprocessed.Utilities;

import com.rinpr.machineprocessed.DataManager.SQLiteManager;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * This class helps you manage machine's inventory easier and less confusing when you tried to
 * get the machine's slot. This class has a method to get the slot with recognizable method name.
 * Simply just input the machine's inventory or machine's id in constructor, and now you can get the slot you wanted by
 * slot you wanted by a public method provided in this class.
 */
public class MachineInventoryManager {
    private final ItemStack slot1;
    private final ItemStack slot2;
    private final ItemStack slot3;
    private final ItemStack slot16;
    private final ItemStack slot20;

    /**
     * @param inventory From the machine's inventory.
     */
    public MachineInventoryManager(Inventory inventory) {
        this.slot1 = inventory.getItem(1) != null ? inventory.getItem(1) : new ItemStack(Material.AIR);
        this.slot2 = inventory.getItem(2) != null ? inventory.getItem(2) : new ItemStack(Material.AIR);
        this.slot3 = inventory.getItem(3) != null ? inventory.getItem(3) : new ItemStack(Material.AIR);
        this.slot16 = inventory.getItem(16) != null ? inventory.getItem(16) : new ItemStack(Material.AIR);
        this.slot20 = inventory.getItem(20) != null ? inventory.getItem(20) : new ItemStack(Material.AIR);
    }

    /**
     * @param MachineID The machine id you wanted to get it's content.
     */
    public MachineInventoryManager(int MachineID) {
        SQLiteManager database = new SQLiteManager();
        this.slot1 = database.getMachineInventory(MachineID).get(0);
        this.slot2 = database.getMachineInventory(MachineID).get(1);
        this.slot3 = database.getMachineInventory(MachineID).get(2);
        this.slot16 = database.getMachineInventory(MachineID).get(4);
        this.slot20 = database.getMachineInventory(MachineID).get(5);
    }

    /**
     * @return First ingredient in the machine. (slot1) if there's nothing will return Air ItemStack.
     */
    public ItemStack getIngredient1() { return slot1; }

    /**
     * @return Second ingredient in the machine. (slot2) if there's nothing will return Air ItemStack.
     */
    public ItemStack getIngredient2() { return slot2; }

    /**
     * @return Third ingredient in the machine. (slot3) if there's nothing will return Air ItemStack.
     */
    public ItemStack getIngredient3() { return slot3; }

    /**
     * @return Fuel in the machine. (slot16) if there's nothing will return Air ItemStack.
     */
    public ItemStack getFuel() { return slot20; }

    /**
     * @return Product in the machine. (slot20) if there's nothing will return Air ItemStack.
     */
    public ItemStack getProduct() { return slot16; }

    /**
     * @return All items in the machine.
     */
    public List<ItemStack> getAll() {
        List<ItemStack> result = new ArrayList<>();
        result.add(this.slot1);
        result.add(this.slot2);
        result.add(this.slot3);
        result.add(this.slot16);
        result.add(this.slot20);
        return result;
    }
}
