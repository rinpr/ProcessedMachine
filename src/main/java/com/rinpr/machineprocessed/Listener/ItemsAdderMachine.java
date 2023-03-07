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
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Objects;

public class ItemsAdderMachine implements Listener {
    @EventHandler
    public void ItemsadderMachineEvent(PlayerInteractEvent event) {
        /* Placing a machine
        * First I need to check an item in player's hand to check if it's matches any machine's ItemStack from machine
        * then get a location of clicked block to place a machine above it or side.
        * after place a machine block then save that location to database, so it can be toggled again later
        */

        /* Breaking a machine
        * First I need to
        */
    }
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
