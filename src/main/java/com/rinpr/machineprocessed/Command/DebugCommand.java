package com.rinpr.machineprocessed.Command;

import com.rinpr.machineprocessed.Utilities.CommandBase;
import com.rinpr.machineprocessed.Utilities.ItemIdentifier;
import com.rinpr.machineprocessed.Utilities.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DebugCommand {
    public DebugCommand() {
        new CommandBase("print") {
            @Override
            public boolean onCommand(CommandSender sender, String [] arguments) {
                Player player = (Player) sender;
//                new SmartGive(player).give(MachineConfig.Machines());
                Message.send(sender, ItemIdentifier.getItemType(((Player) sender).getInventory().getItemInMainHand()));
                return true;
            }

            @Override
            public String getUsage() {
                return "/print";
            }
        }.enableDelay(1);
    }
}
