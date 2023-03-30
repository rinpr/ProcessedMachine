package com.rinpr.machineprocessed.Task;

import com.rinpr.machineprocessed.MachineProcessed;
import org.bukkit.scheduler.BukkitRunnable;

public class MachineProcessing extends BukkitRunnable {
    MachineProcessed plugin;
    public MachineProcessing(MachineProcessed plugin) { this.plugin = plugin; }

    @Override
    public void run() {
        System.out.println("Run!");
    }
}
