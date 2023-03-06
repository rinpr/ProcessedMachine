package com.rinpr.machineprocessed.Command;

import com.rinpr.machineprocessed.MachineSection.MachineConfig;
import com.rinpr.machineprocessed.Utilities.CommandBase;
import com.rinpr.machineprocessed.Utilities.ItemStackSerializer;
import com.rinpr.machineprocessed.Utilities.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommand {
    public DebugCommand() {
        new CommandBase("print") {
            @Override
            public boolean onCommand(CommandSender sender, String [] arguments) {
                Player player = (Player) sender;
                Message.send(player, new ItemStackSerializer(player.getInventory().getItemInMainHand()).getItemString());
//                Message.send(player, MachineConfig.MachineList().toString());
//                Message.send(player, MachineConfig.MachineName().toString());
                return true;
            }

            @Override
            public String getUsage() {
                return "/print";
            }
        }.enableDelay(1);
    }
}
