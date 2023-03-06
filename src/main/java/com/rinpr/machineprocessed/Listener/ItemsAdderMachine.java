package com.rinpr.machineprocessed.Listener;

import com.rinpr.machineprocessed.Utilities.Message;
import dev.lone.itemsadder.api.Events.FurnitureInteractEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ItemsAdderMachine implements Listener {
    @EventHandler
    public void openItemsadderMachineEvent(FurnitureInteractEvent event) {
        Player player = event.getPlayer();
        Message.send(player, String.valueOf(event.getFurniture()));
    }
}
