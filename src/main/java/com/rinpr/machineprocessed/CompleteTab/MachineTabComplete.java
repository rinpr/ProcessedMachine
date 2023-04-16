package com.rinpr.machineprocessed.CompleteTab;

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
                argument.add("reload");
                StringUtil.copyPartialMatches(args[0], argument, completions);
                Collections.sort(completions);
            }
            Collections.sort(completions);
            return completions;
        } else {
            return null;
        }
    }
}
