package com.rinpr.machineprocessed.Listener;

import com.rinpr.machineprocessed.DataManager.SQLiteManager;
import com.rinpr.machineprocessed.MachineSection.MachineConfig;
import com.rinpr.machineprocessed.Utilities.FurnitureLocation;
import com.rinpr.machineprocessed.Utilities.ItemIdentifier;
import com.rinpr.machineprocessed.Utilities.Message;
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

import java.util.Objects;

public class ItemsAdderMachine implements Listener {
    @EventHandler
    public void ItemsadderPlaceMachine(BlockPlaceEvent event) {
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
    public void placeItemsadderMachineEvent(FurniturePlaceSuccessEvent event) {
        if (ItemIdentifier.getNamespacedID(MachineConfig.getAllMachines()).contains(event.getNamespacedID())) {
            Message.send(event.getPlayer(), "You placed at location: ");
            Message.send(event.getPlayer(), new FurnitureLocation(event.getBukkitEntity()).getLocation().toString());
            SQLiteManager sqLiteManager = new SQLiteManager();
            sqLiteManager.addMachine(new FurnitureLocation(event.getBukkitEntity()).getLocation(), MachineConfig.getMachineId(Objects.requireNonNull(event.getFurniture()).getItemStack()));
        }
    }
    @EventHandler
    public void breakItemsadderMachineEvent(FurnitureBreakEvent event) {
        if (ItemIdentifier.getNamespacedID(MachineConfig.getAllMachines()).contains(event.getNamespacedID())) {
            Message.send(event.getPlayer(), "You break at location: ");
            Message.send(event.getPlayer(), new FurnitureLocation(event.getBukkitEntity()).getLocation().toString());
            SQLiteManager sqLiteManager = new SQLiteManager();
            sqLiteManager.deleteMachine(new FurnitureLocation(event.getBukkitEntity()).getLocation());
        }
    }
    @EventHandler
    public void openItemsadderMachineEvent(FurnitureInteractEvent event) {
        if (MachineConfig.getAllMachines().contains(Objects.requireNonNull(event.getFurniture()).getItemStack())) {
            Message.send(event.getPlayer(), MachineConfig.getMachineId(event.getFurniture().getItemStack()));
            Message.send(event.getPlayer(), "You clicked at machine");
        }
    }
}
