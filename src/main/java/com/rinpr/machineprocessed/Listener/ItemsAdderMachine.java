package com.rinpr.machineprocessed.Listener;

import com.rinpr.machineprocessed.DataManager.SQLiteManager;
import com.rinpr.machineprocessed.MachineSection.MachineConfig;
import com.rinpr.machineprocessed.MachineSection.MachineGUI;
import com.rinpr.machineprocessed.Utilities.FurnitureLocation;
import com.rinpr.machineprocessed.Utilities.ItemIdentifier;
import com.rinpr.machineprocessed.Utilities.MachineInventoryManager;
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

import java.util.*;

public class ItemsAdderMachine implements Listener {
    Map<Player, Integer> machinePlayerMap = new HashMap<>();
    Set<Integer> workingMachine = new HashSet<Integer>();
    @EventHandler
    public void BlockPlaceDebug(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();
        Location b_loc = block.getLocation();
        player.sendMessage(ChatColor.BLUE + "You Placed: " + ChatColor.LIGHT_PURPLE + block.getType().toString().toUpperCase());
        player.sendMessage(ChatColor.BLUE + "Location:");
        player.sendMessage(ChatColor.GOLD + "World: " + ChatColor.WHITE + Objects.requireNonNull(b_loc.getWorld()).getName());
        player.sendMessage(ChatColor.GOLD + "X: " + ChatColor.WHITE + b_loc.getBlockX());
        player.sendMessage(ChatColor.GOLD + "Y: " + ChatColor.WHITE + b_loc.getBlockY());
        player.sendMessage(ChatColor.GOLD + "Z: " + ChatColor.WHITE + b_loc.getBlockZ());
    }
//    @EventHandler
//    public void startMachine(ChunkLoadEvent event) {
//        Bukkit.broadcastMessage("someone enter in chunk");
//    }
//    @EventHandler
//    public void stopMachine(ChunkUnloadEvent event) {
//        Bukkit.broadcastMessage("someone left chunk");
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
            sqLiteManager.deleteMachineInventory(sqLiteManager.getMachineId(break_location));
            sqLiteManager.deleteMachine(break_location);
            // if there's item inside drop all
        }
    }
    @EventHandler
    public void openItemsadderMachine(FurnitureInteractEvent event) {
        if (MachineConfig.getAllMachines().contains(Objects.requireNonNull(event.getFurniture()).getItemStack())) {
            SQLiteManager sqLiteManager = new SQLiteManager();
            Location machine_location = new FurnitureLocation(event.getBukkitEntity(),true).getLocation();
            int MachineID = sqLiteManager.getMachineId(machine_location);
            new MachineGUI(MachineConfig.getMachineId(event.getFurniture().getItemStack()), event.getPlayer()).openGUI(MachineID);
            machinePlayerMap.put(event.getPlayer(), MachineID);
            // when open check if the ingredient is match in config or not if yes do the process
//            MachineProcessing task = new MachineProcessing(MachineID);
//            task.broadcast();
        }
    }
    @EventHandler
    public void saveItemsAdderMachineInventory(InventoryCloseEvent event) {
        if (!MachineConfig.MachineName().contains(event.getView().getTitle())) return;
        if (event.getInventory().getSize() != 27) return;

        Player player = (Player) event.getPlayer();
        MachineInventoryManager slot = new MachineInventoryManager(event.getInventory());

        SQLiteManager sqLiteManager = new SQLiteManager();
        sqLiteManager.updateMachineInventory(machinePlayerMap.get(player), slot.getIngredient1(), slot.getIngredient2(), slot.getIngredient3(), slot.getFuel(), slot.getProduct());

        // for debug
//        String output = slot.getIngredient1().toString() + slot.getIngredient2().toString() + slot.getIngredient3().toString() + slot.getFuel().toString() + slot.getProduct().toString();
//        Message.send(player,output);

        machinePlayerMap.remove(player);
    }
}
