package com.rinpr.machineprocessed.Command;

import com.rinpr.machineprocessed.MachineProcessed;
import com.rinpr.machineprocessed.MachineSection.MachineConfig;
import com.rinpr.machineprocessed.MachineSection.MachineGUI;
import com.rinpr.machineprocessed.Utilities.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class MProcessed implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {
        //below will start the command, once it got the right file and machine
        if (label.equalsIgnoreCase("mp") || label.equalsIgnoreCase("mprocess") || label.equalsIgnoreCase("machineprocessed")) {
            if (!(sender instanceof Player)) {
                //do console command
                if (arguments.length == 1) {
                    if (arguments[0].equalsIgnoreCase("reload")) {
                        MachineProcessed.getPlugin().reloadMachineFiles();
                        Message.send(sender, "&aSuccessfully reloaded machine config!");
                    }
                }
                return true;
            } else {
                //do player command
                if (arguments.length == 1) {
                    if (arguments[0].equalsIgnoreCase("reload")) {
                        MachineProcessed.getPlugin().reloadMachineFiles();
                        Message.send(sender, "&aSuccessfully reloaded machine config!");
                    }
                }
            }
        }
        return true;
    }
}
