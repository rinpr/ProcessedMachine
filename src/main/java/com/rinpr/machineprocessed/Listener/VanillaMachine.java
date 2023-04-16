package com.rinpr.machineprocessed.Listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class VanillaMachine implements Listener {
    @EventHandler
    public void placeMachine(BlockPlaceEvent event) {
    }
    @EventHandler
    public void breakMachine(BlockBreakEvent event) {

    }
    @EventHandler
    public void openMachine(PlayerInteractEvent event) {
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {

        }
    }
}
