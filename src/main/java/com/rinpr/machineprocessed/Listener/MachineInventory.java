package com.rinpr.machineprocessed.Listener;

import com.rinpr.machineprocessed.MachineSection.MachineConfig;
import com.rinpr.machineprocessed.Utilities.Message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class MachineInventory implements Listener {

    @EventHandler (priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void PlayerInteractGUIEvent(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ArrayList<Integer> space_slot = new ArrayList<>();
        Collections.addAll(space_slot, 0,4,5,6,7,8,9,10,12,13,14,15,17,18,19,21,22,23,24,25,26);

        // Check if it's a machine gui or not.
        if (event.getClickedInventory() == null) return;
        if (!MachineConfig.MachineName().contains(event.getView().getTitle())) return;
        if (event.getClickedInventory().getSize() != 27) return;

        if (space_slot.contains(event.getRawSlot())) {
            event.setCancelled(true); // prevent player from clicking in space slot
//            Message.send(player, "&cYou are not allowed to place an item in this slot!");
        } else {
//            Message.send(player, "This slot will be used for machine processing");
        }
    }
    @EventHandler
    public void PlayerDragEvent(InventoryDragEvent event) {
        Set<Integer> space_slot = new HashSet<>();
        Collections.addAll(space_slot, 0,4,5,6,7,8,9,10,12,13,14,15,17,18,19,21,22,23,24,25,26);
        if (MachineConfig.MachineName().contains(event.getView().getTitle()) && space_slot.contains(event.getRawSlots())) {
            event.setCancelled(true);
        }
    }
    @EventHandler
    public void saveMachineItem(InventoryCloseEvent event) {
        if (!MachineConfig.MachineName().contains(event.getView().getTitle())) return;
        if (event.getInventory().getSize() != 27) return;
        Player player = (Player) event.getPlayer();
        ItemStack slot1 = event.getInventory().getItem(1);
        ItemStack slot2 = event.getInventory().getItem(2);
        ItemStack slot3 = event.getInventory().getItem(3);
        if (slot1 != null && slot2 != null && slot3 != null) {
            String output = slot1.toString() + slot2.toString() + slot3.toString();
            Message.send(player,output);
        }
    }
}

