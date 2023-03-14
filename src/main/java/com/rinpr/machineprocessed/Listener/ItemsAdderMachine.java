package com.rinpr.machineprocessed.Listener;

import com.rinpr.machineprocessed.MachineSection.MachineConfig;
import com.rinpr.machineprocessed.Utilities.ItemIdentifier;
import dev.lone.itemsadder.api.Events.FurnitureBreakEvent;
import dev.lone.itemsadder.api.Events.FurnitureInteractEvent;
import dev.lone.itemsadder.api.Events.FurniturePlaceEvent;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

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
    @EventHandler
    public void ItemsadderPlaceMachine(BlockPlaceEvent event) {
//        if (MachineConfig.getAllMachines().contains(event.getPlayer().getInventory().getItemInMainHand())) {
//            Message.send(event.getPlayer(), "You placed machine from config.");
//        }
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
    @EventHandler
    public void placeItemsadderMachineEvent(FurniturePlaceEvent event) {
        if (ItemIdentifier.getNamespacedID(MachineConfig.getAllMachines()).contains(event.getNamespacedID())) {
//            SQLiteManager sqLiteManager = new SQLiteManager(plugin);
//            sqLiteManager.addMachine(event.getBlockPlaced().getLocation());
//            Message.send(event.getPlayer(), "You placed a config machine!");
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
}
