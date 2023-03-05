package com.rinpr.machineprocessed.Command;

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
        String machine = null;
        if (arguments.length != 0) { //check to make sure the person hasn't just left it empty
            for (String machine_list : MachineConfig.MachineList()){
                if(machine_list.equals(arguments[0])) {
                    machine = machine_list;
                    break;
                }
            }
        }else{
            Message.send(sender,"&cMissing command arguments!");
            return true;
        }
        if(machine == null){
            Message.send(sender,"&cNo machine configuration!");
            return true;
        }
        //below will start the command, once it got the right file and machine
        if (label.equalsIgnoreCase("mp") || label.equalsIgnoreCase("mprocess") || label.equalsIgnoreCase("machineprocessed")) {
            if (!(sender instanceof Player)) {
                //do console command
                Message.send(sender, "Hello Console");
                return true;
            } else {
                //do player command
                Player p = (Player) sender;
                if (arguments.length == 1) {
                    new MachineGUI(arguments[0], p).openGUI();
                    return true;
                } else if (arguments.length == 2) {
                    Player target = Bukkit.getServer().getPlayer(arguments[1]);
                    new MachineGUI(arguments[0], target).openGUI();
                    return true;
                }
            }
        }
        Message.send(sender, "&cUsage: /mp <machine>");
        return true;
    }
}
