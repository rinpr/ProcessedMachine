package com.rinpr.machineprocessed.Listener;

import com.rinpr.machineprocessed.DataManager.SQLiteManager;
import com.rinpr.machineprocessed.MachineSection.MachineConfig;
import com.rinpr.machineprocessed.MachineSection.MachineGUI;
import com.rinpr.machineprocessed.Task.MachineProcessing;
import com.rinpr.machineprocessed.Utilities.FurnitureLocation;
import com.rinpr.machineprocessed.Utilities.ItemIdentifier;
import com.rinpr.machineprocessed.Utilities.MachineInventoryManager;
import com.rinpr.machineprocessed.Utilities.ProductCalculation;
import dev.lone.itemsadder.api.Events.FurnitureBreakEvent;
import dev.lone.itemsadder.api.Events.FurnitureInteractEvent;
import dev.lone.itemsadder.api.Events.FurniturePlaceSuccessEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class ItemsAdderMachine implements Listener {

    /**
     * This Map use to store machine's id that the player is currently using
     * this is useful for open and close itemsadder machine event.
     */
    Map<Player, Integer> machinePlayerMap = new HashMap<>();


    /**
     * This Map use to store machine's id that has enough ingredients to produce
     * a product, will save when player closes machine.
     */
    Map<Integer, LocalDateTime> workingMachine = new HashMap<>();

//    @EventHandler
//    public void BlockPlaceDebug(BlockPlaceEvent event) {
//        Block block = event.getBlock();
//        Player player = event.getPlayer();
//        Location b_loc = block.getLocation();
//        player.sendMessage(ChatColor.BLUE + "You Placed: " + ChatColor.LIGHT_PURPLE + block.getType().toString().toUpperCase());
//        player.sendMessage(ChatColor.BLUE + "Location:");
//        player.sendMessage(ChatColor.GOLD + "World: " + ChatColor.WHITE + Objects.requireNonNull(b_loc.getWorld()).getName());
//        player.sendMessage(ChatColor.GOLD + "X: " + ChatColor.WHITE + b_loc.getBlockX());
//        player.sendMessage(ChatColor.GOLD + "Y: " + ChatColor.WHITE + b_loc.getBlockY());
//        player.sendMessage(ChatColor.GOLD + "Z: " + ChatColor.WHITE + b_loc.getBlockZ());
//    }
    @EventHandler
    public void placeItemsadderMachine(FurniturePlaceSuccessEvent event) {
        if (ItemIdentifier.getNamespacedID(MachineConfig.getAllMachines()).contains(event.getNamespacedID())) {
            // for debug
//            Message.send(event.getPlayer(), "You placed at location: ");
//            Message.send(event.getPlayer(), new FurnitureLocation(event.getBukkitEntity()).getLocation().toString());
            SQLiteManager sqLiteManager = new SQLiteManager();
            sqLiteManager.addMachine(new FurnitureLocation(event.getBukkitEntity()).getLocation(), MachineConfig.getMachineId(Objects.requireNonNull(event.getFurniture()).getItemStack()));
            sqLiteManager.createMachineInventory();
        }
    }
    @EventHandler
    public void breakItemsadderMachine(FurnitureBreakEvent event) {
        if (ItemIdentifier.getNamespacedID(MachineConfig.getAllMachines()).contains(event.getNamespacedID())) {
            Location break_location = new FurnitureLocation(event.getBukkitEntity(),true).getLocation();
            SQLiteManager sqLiteManager = new SQLiteManager();
            // if there's item inside drop all
            for (ItemStack item : new MachineInventoryManager(sqLiteManager.getMachineId(break_location)).getAll()) {
                Objects.requireNonNull(break_location.getWorld()).dropItemNaturally(break_location, item);
            }
            sqLiteManager.deleteMachineInventory(sqLiteManager.getMachineId(break_location));
            sqLiteManager.deleteMachine(break_location);
        }
    }
    @EventHandler
    public void openItemsadderMachine(FurnitureInteractEvent event) {
        if (MachineConfig.getAllMachines().contains(Objects.requireNonNull(event.getFurniture()).getItemStack())) {
            SQLiteManager sqLiteManager = new SQLiteManager();
            Location machine_location = new FurnitureLocation(event.getBukkitEntity(),true).getLocation();
            int MachineID = sqLiteManager.getMachineId(machine_location);

            // Check if the machine isn't in use by another player.
            if (machinePlayerMap.containsValue(MachineID)) return;

            // Check if the machine is processing or not.
            if (workingMachine.containsKey(MachineID)) {
                System.out.println("Its processable");
                // Do the machine process if the machine's id is on the processable list.
                MachineProcessing process = new MachineProcessing(MachineID);
                // Get the present time
                LocalDateTime last_open_time = workingMachine.get(MachineID);
                System.out.println("Current time: " + last_open_time);
                // Get the product amount that it should be.
                int times = new ProductCalculation(MachineID, last_open_time).GetProductAmount();
                // Update the machine.
                process.updateProduct(times);
                workingMachine.remove(MachineID);
                System.out.println(200);
            }
            // Open the machine to event player.
            new MachineGUI(MachineConfig.getMachineId(event.getFurniture().getItemStack()), event.getPlayer()).openGUI(MachineID);
            machinePlayerMap.put(event.getPlayer(), MachineID);
        }
    }
    @EventHandler
    public void closeItemsadderMachine(InventoryCloseEvent event) {
        if (!MachineConfig.MachineName().contains(event.getView().getTitle())) return;
        if (event.getInventory().getSize() != 27) return;

        Player player = (Player) event.getPlayer();
        int MachineID = machinePlayerMap.get(player);
        MachineInventoryManager slot = new MachineInventoryManager(event.getInventory());

        SQLiteManager sqLiteManager = new SQLiteManager();
        sqLiteManager.updateMachineInventory(MachineID, slot.getIngredient1(), slot.getIngredient2(), slot.getIngredient3(), slot.getProduct(), slot.getFuel());

        // Check if the machine ingredient is enough or not, Will add to workingMachine if true.
        if (new MachineProcessing(MachineID).isProcessable()) {
            // Get the present time
            LocalDateTime time_now = LocalDateTime.now();
            // Put the machine's id and present time in workingMachine Map
            workingMachine.put(MachineID, time_now);
            System.out.println("Saving time to map : " + time_now);
            // Remove player using machine from Map. (Required when closing machine)
            machinePlayerMap.remove(player);
            System.out.println("added processable into the list");
        }

        // for debug
//        String output = slot.getIngredient1().toString() + slot.getIngredient2().toString() + slot.getIngredient3().toString() + slot.getFuel().toString() + slot.getProduct().toString();
//        Message.send(player,output);

        machinePlayerMap.remove(player);
    }

    /** This method use to get key by the given value.
     * @param map Map you wanted to get key.
     * @param value The value you needed to find it's key.
     * @param <T> Key
     * @param <E> Value
     * @return Key from the value provided.
     */
    private <T, E> Set<T> getKeysByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .collect(Collectors.toSet());
    }
}
