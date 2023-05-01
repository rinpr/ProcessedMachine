package com.rinpr.machineprocessed.Listener;

import com.rinpr.machineprocessed.Utilities.Message;
import io.th0rgal.oraxen.api.events.OraxenFurnitureBreakEvent;
import io.th0rgal.oraxen.api.events.OraxenFurnitureInteractEvent;
import io.th0rgal.oraxen.api.events.OraxenFurniturePlaceEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class OraxenMachine implements Listener {
    @EventHandler
    public void openOraxenMachine(OraxenFurnitureInteractEvent event) {
        Player player = event.getPlayer();
        Message.send(player, String.valueOf(event.getBlock()));
//        Message.send(player, String.valueOf(event.getItemFrame()));
    }

    @EventHandler
    public void closeOraxenMachine(InventoryCloseEvent event) {

    }

    @EventHandler
    public void placeOraxenMachine(OraxenFurniturePlaceEvent event) {

    }

    @EventHandler
    public void breakOraxenMachine(OraxenFurnitureBreakEvent event) {

    }

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
}
