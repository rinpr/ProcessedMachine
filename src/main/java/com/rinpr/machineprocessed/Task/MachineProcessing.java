package com.rinpr.machineprocessed.Task;

import com.rinpr.machineprocessed.DataManager.SQLiteManager;
import com.rinpr.machineprocessed.api.Machine;
import org.bukkit.Bukkit;
import org.bukkit.Material;
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

    /** This private method use to check the machine's ingredient that if it matches
     * in the config or not, And also check if it's enough amount for product to update or not.
     * @return True if the ingredient is matched from config and enough amount ot update, else false.
     */
    private boolean isProcessable() {
        List<ItemStack> configIngredients = config.getIngredient();
        List<ItemStack> machineIngredients = getIngredient();

        // Check if the first 3 ingredients in machineIngredients match configIngredients
        for (int i = 0; i < 3; i++) {
            if (!configIngredients.get(i).isSimilar(machineIngredients.get(i))) {
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

        // Check if the fuel in the machine is enough or not compared to required fuel in config
        if (getFuel().getAmount() < config.getFuel().getAmount()) return false;

        // Check if the product is almost stack or already stacked it will terminate this update process.
        if (getProduct().getAmount() + config.getProduct().getAmount() > 64) return false;

        // Check if the product slot is not filled with other item than product itself and air.
        if (getProduct().getType() != Material.AIR && !getProduct().isSimilar(config.getProduct())) return false;

        // All 3 ingredients match and the machine has at least the required amount of each, so it's a match
        return true;
    }

    /**
     * This method use to update product in the machine's database automatically.
     * Will consume material needed and give 1 product as ItemStack.
     */
    public void updateProduct() {
        if (isProcessable()) {
            List<ItemStack> configIngredients = config.getIngredient();
            List<ItemStack> machineIngredients = getIngredient();

            // Remove fuel from machine's inventory by x amount based on config
            ItemStack fuel = getFuel();
            fuel.setAmount(getFuel().getAmount() - config.getFuel().getAmount());

            // Remove ingredient from machine's inventory by x amount based on config
            for (int i = 0; i < configIngredients.size(); i++) {
                ItemStack configItem = configIngredients.get(i);
                ItemStack machineItem = machineIngredients.get(i);
                int amountToRemove = configItem.getAmount();
                int machineAmount = machineItem.getAmount();
                machineItem.setAmount(machineAmount - amountToRemove);
            }

            /* Set new amount of product if it's already exists will increment the amount by 1
             * else if there's nothing ( Air ItemStack ) will set to product.
            */
            ItemStack product = config.getProduct();
            if (!getProduct().isSimilar(new ItemStack(Material.AIR))) {
                if (getProduct().isSimilar(config.getProduct())) {
                    product.setAmount(getProduct().getAmount() + config.getProduct().getAmount());
                }
            }

            // Update the final machine inventory to database.
            database.updateMachineInventory(machine_id,
                    machineIngredients.get(0),
                    machineIngredients.get(1),
                    machineIngredients.get(2),
                    product,
                    fuel);
        }
    }

    public void broadcast() {
        Bukkit.broadcastMessage(getFuel().toString());
        Bukkit.broadcastMessage("");
        Bukkit.broadcastMessage(getProduct().toString());
    }

    private List<ItemStack> getIngredient() { return database.getMachineInventory(this.machine_id).subList(0, 3); }

    private ItemStack getFuel() { return database.getMachineInventory(this.machine_id).get(5); }

    private ItemStack getProduct() { return database.getMachineInventory(this.machine_id).get(4); }
}
