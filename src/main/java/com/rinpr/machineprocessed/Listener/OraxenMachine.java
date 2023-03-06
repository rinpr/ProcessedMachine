package com.rinpr.machineprocessed.Listener;

import com.rinpr.machineprocessed.Utilities.Message;
import io.th0rgal.oraxen.api.events.OraxenFurnitureInteractEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OraxenMachine implements Listener {
    @EventHandler
    public void openOraxenMachineEvent(OraxenFurnitureInteractEvent event) {
        Player player = event.getPlayer();
        Message.send(player, String.valueOf(event.getBlock()));
        Message.send(player, String.valueOf(event.getItemFrame()));
    }
}
