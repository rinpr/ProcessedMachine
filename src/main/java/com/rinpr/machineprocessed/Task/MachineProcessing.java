package com.rinpr.machineprocessed.Task;

import com.rinpr.machineprocessed.DataManager.SQLiteManager;
import com.rinpr.machineprocessed.MachineSection.MachineConfig;
import com.rinpr.machineprocessed.api.Machine;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MachineProcessing {
    private final int machine_id;
    private final String machine;
    private SQLiteManager database;
    private final Machine config;
    public MachineProcessing(int machine_id) {
        this.machine_id = machine_id;
        this.database = new SQLiteManager();
        this.machine = database.getNamespace(this.machine_id);
        this.config = new Machine(this.machine);
    }

    public boolean isMatch() {
        List<ItemStack> configIngredients = config.getIngredient();
        List<ItemStack> machineIngredients = getIngredient();
        // Check if the first 3 ingredients in machineIngredients match configIngredients
        for (int i = 0; i < 3; i++) {
            if (!configIngredients.get(i).equals(machineIngredients.get(i))) {
                // The ingredients don't match at this position, so it's not a match
                return false;
            }
        }
        // Check if they are greater than or equal to configIngredients
        for (int i = 0; i < 3; i++) {
            ItemStack machineIngredient = machineIngredients.get(i);
            ItemStack configIngredient = configIngredients.get(i);
            if (machineIngredient.getAmount() < configIngredient.getAmount()) {
                // The machine has less than the required amount of this ingredient, so it's not a match
                return false;
            }
        }
        // All 3 ingredients match and the machine has at least the required amount of each, so it's a match
        return true;
    }

    public void updateProduct() {
        if (isMatch()) {
            List<ItemStack> configIngredients = config.getIngredient();
            List<ItemStack> machineIngredients = getIngredient();
            ItemStack fuel = getFuel();
            fuel.setAmount(fuel.getAmount() - config.getFuel().getAmount());
            for (int i = 0; i < configIngredients.size(); i++) {
                ItemStack configItem = configIngredients.get(i);
                ItemStack machineItem = machineIngredients.get(i);
                int amountToRemove = configItem.getAmount();
                int machineAmount = machineItem.getAmount();
                machineItem.setAmount(machineAmount - amountToRemove);
            }
            database.updateMachineInventory(machine_id,
                    machineIngredients.get(0),
                    machineIngredients.get(1),
                    machineIngredients.get(2),
                    fuel,
                    config.getProduct());
            Bukkit.broadcastMessage("update processing success!");
        }
    }

    public void broadcast() {
        Bukkit.broadcastMessage(config.getIngredient().toString());
        Bukkit.broadcastMessage(getIngredient().toString());
        Bukkit.broadcastMessage(String.valueOf(isMatch()));
        Bukkit.broadcastMessage(getFuel().toString());
        Bukkit.broadcastMessage(getProduct().toString());
        updateProduct();
    }

    private List<ItemStack> getIngredient() { return database.getMachineInventory(this.machine_id).subList(0, 3); }

    private ItemStack getFuel() { return database.getMachineInventory(this.machine_id).get(4); }

    private ItemStack getProduct() { return database.getMachineInventory(this.machine_id).get(5); }
}
