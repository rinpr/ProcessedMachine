package com.rinpr.machineprocessed.Listener;

import com.rinpr.machineprocessed.MachineSection.MachineConfig;
import com.rinpr.machineprocessed.Utilities.Message;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;
import java.util.Collections;

public class openMachine implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = false)
    public void machineClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ArrayList<Integer> space_slot = new ArrayList<>();
        Collections.addAll(space_slot, 0,4,5,6,7,8,9,10,12,13,14,15,17,18,19,21,22,23,24,25,26);

        // Check if the clicked inventory is the machine GUI
        Inventory clickedInv = event.getClickedInventory();
        if (clickedInv == null) return; // player clicked outside inventory
        if (!MachineConfig.MachineName().contains(event.getView().getTitle())) return; // not a machine
        if (clickedInv.getSize() != 27) return; // not a machine

        int rawSlot = event.getRawSlot();
        if (space_slot.contains(rawSlot)) {
            event.setCancelled(true); // prevent player from moving items in other slots
            Message.send(player, "&cYou are not allowed to place an item in this slot!");
        } else {
            Message.send(player, "This slot will be used for machine processing");
        }
    }
}

