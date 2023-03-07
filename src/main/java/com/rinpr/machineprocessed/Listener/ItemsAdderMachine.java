package com.rinpr.machineprocessed.Listener;

import com.rinpr.machineprocessed.MachineSection.MachineConfig;
import com.rinpr.machineprocessed.Utilities.ItemIdentifier;
import com.rinpr.machineprocessed.Utilities.Message;
import dev.lone.itemsadder.api.Events.CustomBlockPlaceEvent;
import dev.lone.itemsadder.api.Events.FurnitureBreakEvent;
import dev.lone.itemsadder.api.Events.FurnitureInteractEvent;
import dev.lone.itemsadder.api.Events.FurniturePlaceEvent;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.HashMap;

public class ItemsAdderMachine implements Listener {
//    @EventHandler
//    public void placeItemsadderMachineEvent(BlockPlaceEvent event) {
//        Player player = event.getPlayer();
//        Location block_location = event.getBlock().getLocation();
//        ItemStack ignore_inhand_amount = player.getInventory().getItemInMainHand().clone();
//        ignore_inhand_amount.setAmount(1);
//        for (ItemStack item : MachineConfig.Machines()) {
//            if (item.equals(ignore_inhand_amount)) { //
//                SQLiteManager sqLiteManager = new SQLiteManager(plugin);
//                sqLiteManager.addMachine(block_location);
//                Message.send(player, "&ait is a machine block from config");
//                return;
//            }
//        }
//    }
//    @EventHandler
//    public void breakItemsadderMachineEvent(BlockBreakEvent event) {
//        Player player = event.getPlayer();
//        Location block_loc = event.getBlock().getLocation();
//        if (event.getBlock().getType() == Material.BARRIER) {
//            SQLiteManager sqLiteManager = new SQLiteManager(plugin);
//            if (sqLiteManager.hasMachineId(block_loc)) {
//                sqLiteManager.deleteMachine(block_loc);
//                Message.send(player, "You broke a machine!");
//            }
//        }
//    }
    // instead of doing 2 event in the same method, I simply created 2 different method with one event in the same class.
    public HashMap<String, Location> FurnitureData = new HashMap<String, Location>();
    @EventHandler
    public void placeItemsadderMachineEvent(FurniturePlaceEvent event) {
        if (ItemIdentifier.getNamespacedID(MachineConfig.getAllMachines()).contains(event.getNamespacedID())) {
//            SQLiteManager sqLiteManager = new SQLiteManager(plugin);
//            sqLiteManager.addMachine(event.getBlockPlaced().getLocation());
            Message.send(event.getPlayer(), event.getHandlers().toString());
            Message.send(event.getPlayer(), "You placed a config machine!");
        }
    }
    @EventHandler
    public void breakItemsadderMachineEvent(FurnitureBreakEvent event) {
        //Message.send(event.getPlayer(), event.getNamespacedID());
    }
    @EventHandler
    public void openItemsadderMachineEvent(FurnitureInteractEvent event) {
        //Message.send(event.getPlayer(), event.getNamespacedID());
    }
    @EventHandler
    public void asd(CustomBlockPlaceEvent e) {
        e.getBlock().getLocation();
        e.getNamespacedID();
        Message.send(e.getPlayer(), String.valueOf(e.getBlock().getLocation()));
        Message.send(e.getPlayer(), e.getNamespacedID());
    }
}
