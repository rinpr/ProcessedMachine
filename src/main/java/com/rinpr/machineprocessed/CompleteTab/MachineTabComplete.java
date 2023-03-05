package com.rinpr.machineprocessed.CompleteTab;

import com.rinpr.machineprocessed.MachineSection.MachineConfig;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MachineTabComplete implements TabCompleter {
    @Nullable
    @Override
    public List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            List<String> completions = new ArrayList<>();
            List<String> argument = new ArrayList<>();
            if (args.length == 1) {
                argument.addAll(MachineConfig.MachineList());
                StringUtil.copyPartialMatches(args[0], argument, completions);
                Collections.sort(completions);
            } else if (args.length == 2) {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    argument.add(p.getName());
                }
                StringUtil.copyPartialMatches(args[1], argument, completions);
            }
            Collections.sort(completions);
            return completions;
        } else {
            return null;
        }
    }
}
