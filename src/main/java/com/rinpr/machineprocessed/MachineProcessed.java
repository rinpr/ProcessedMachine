package com.rinpr.machineprocessed;

import com.rinpr.machineprocessed.Command.DebugCommand;
import com.rinpr.machineprocessed.Command.MProcessed;
import com.rinpr.machineprocessed.CompleteTab.MachineTabComplete;
import com.rinpr.machineprocessed.DataManager.SQLiteManager;
import com.rinpr.machineprocessed.Listener.ItemsAdderMachine;
import com.rinpr.machineprocessed.Listener.OraxenMachine;
import com.rinpr.machineprocessed.Listener.MachineInventory;
import com.rinpr.machineprocessed.Task.MachineProcessing;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

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
        SQLiteManager.loadSQLite();
        RegisterTask();
    }
    private void RegisterTask() {
        BukkitTask processing = new MachineProcessing(this).runTaskTimer(this,0,20L);
    }
    private void RegisterCommand() {
        new DebugCommand();

        Objects.requireNonNull(this.getCommand("machineprocessed")).setExecutor(new MProcessed());
        Objects.requireNonNull(this.getCommand("machineprocessed")).setTabCompleter(new MachineTabComplete());
    }
    private void RegisterListener() {
        if (isPluginEnabled("Itemsadder") && isPluginEnabled("Oraxen")) return;
        if (isPluginEnabled("Itemsadder")) {
            Bukkit.getLogger().info("Itemsadder found!");
            Bukkit.getPluginManager().registerEvents(new ItemsAdderMachine(), this);
        } else if (isPluginEnabled("Oraxen")) {
            Bukkit.getLogger().info("Oraxen found!");
            Bukkit.getPluginManager().registerEvents(new OraxenMachine(), this);
        }

        Bukkit.getPluginManager().registerEvents(new MachineInventory(), this);
    }
    @Override
    public void onDisable() {
        SQLiteManager.unloadSQLite();
        // Plugin shutdown logic
    }
    public boolean isPluginEnabled(String plugin) { return getServer().getPluginManager().getPlugin(plugin) != null; }
    public static MachineProcessed getPlugin() { return plugin;}
}
