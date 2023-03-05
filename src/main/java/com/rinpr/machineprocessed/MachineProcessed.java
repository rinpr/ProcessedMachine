package com.rinpr.machineprocessed;

import com.rinpr.machineprocessed.Command.DebugCommand;
import com.rinpr.machineprocessed.Command.MProcessed;
import com.rinpr.machineprocessed.CompleteTab.MachineTabComplete;
import com.rinpr.machineprocessed.Listener.openMachine;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

import static com.rinpr.machineprocessed.MachineSection.MachineConfig.generateMachineFolder;

public final class MachineProcessed extends JavaPlugin {
    public static MachineProcessed plugin;
    public MachineProcessed() {
        plugin = this;
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
        saveDefaultConfig();
        generateMachineFolder();
        RegisterCommand();
        RegisterListener();
    }
    private void RegisterCommand() {
        new DebugCommand();

        Objects.requireNonNull(this.getCommand("machineprocessed")).setExecutor(new MProcessed());
        Objects.requireNonNull(this.getCommand("machineprocessed")).setTabCompleter(new MachineTabComplete());
    }
    private void RegisterListener() {
        Bukkit.getPluginManager().registerEvents(new openMachine(), this);
    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
    public static MachineProcessed getPlugin() { return plugin;}
}
