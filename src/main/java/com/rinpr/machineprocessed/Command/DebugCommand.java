package com.rinpr.machineprocessed.Command;

import com.rinpr.machineprocessed.DataManager.SQLiteManager;
import com.rinpr.machineprocessed.Utilities.*;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

import static com.rinpr.machineprocessed.MachineProcessed.plugin;

public class DebugCommand {
    public DebugCommand() {
        new CommandBase("print") {
            @Override
            public boolean onCommand(CommandSender sender, String [] arguments) {
                Player player = (Player) sender;
                MachineInChunk a = new MachineInChunk(player.getLocation().getChunk());
                Message.send(player, a.getMachine().toString());
                return true;
            }

            @Override
            public String getUsage() {
                return "/print";
            }
        }.enableDelay(1);
        new CommandBase("getitemstring") {
            @Override
            public boolean onCommand(CommandSender sender, String [] arguments) {
                Player player = (Player) sender;
                Message.send(new ItemStackSerializer(player.getInventory().getItemInMainHand()).toItemString());
                return true;
            }
            @Override
            public String getUsage() {
                return "get itemstring from player's hand and send it to console";
            }
        };
        new CommandBase("getitem") {
            @Override
            public boolean onCommand(CommandSender sender, String [] arguments) {
                SQLiteManager sqLiteManager = new SQLiteManager();
                Player player = (Player) sender;
//                List<String> a = sqLiteManager.getRawMachineInventory(1);
//                String single = a.get(1);
//                for (String no : a ) {
//                    new SmartGive(player).give(new ItemStackSerializer(no).toItemStack());
//                }
                new SmartGive(player).give(sqLiteManager.getMachineInventory(1));
//                Message.send(player, String.valueOf(a.size()));
//                new SmartGive(player).give(new ItemStackSerializer(single).toItemStack());
                return true;
            }
            @Override
            public String getUsage() {
                return "get itemstack from config";
            }
        };
    }
}
