package com.rinpr.machineprocessed;

import com.rinpr.machineprocessed.Command.DebugCommand;
import com.rinpr.machineprocessed.Command.MProcessed;
import com.rinpr.machineprocessed.CompleteTab.MachineTabComplete;
import com.rinpr.machineprocessed.DataManager.SQLiteManager;
import com.rinpr.machineprocessed.Listener.ItemsAdderMachine;
import com.rinpr.machineprocessed.Listener.OraxenMachine;
import com.rinpr.machineprocessed.Listener.MachineInventory;
import com.rinpr.machineprocessed.api.Machine;
import net.md_5.bungee.api.ChatColor;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.input.CharSequenceReader;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.io.Reader;

public final class MachineProcessed extends JavaPlugin {
    public static MachineProcessed plugin;
    public List<Machine> machineList = new ArrayList<>(); //contains all the machines that are included in the machine folder
    public File machinecfg = new File(this.getDataFolder() + File.separator + "machine");
    public MachineProcessed() {
        plugin = this;
    }
    @Override
    public void onEnable() {
        // Plugin startup logic
//        saveDefaultConfig();
//        generateMachineFolder();
        generateSample();
        RegisterCommand();
        RegisterListener();
        SQLiteManager.loadSQLite();
        reloadMachineFiles();
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

    private void generateSample() {
        //save the sample machine file
        if (!this.machinecfg.exists()) {
            try {
                // first
                FileConfiguration exampleFileConfiguration = YamlConfiguration.loadConfiguration(getReaderFromStream(this.getResource("Sample.yml")));
                exampleFileConfiguration.save(new File(this.machinecfg + File.separator + "Sample.yml"));
                // second
                exampleFileConfiguration = YamlConfiguration.loadConfiguration(getReaderFromStream(this.getResource("Mortal.yml")));
                exampleFileConfiguration.save(new File(this.machinecfg + File.separator + "Mortal.yml"));
                Bukkit.getConsoleSender().sendMessage("[MachineProcessed]" + ChatColor.GREEN + " NOTICE: Successfully generated a sample files!");
            } catch (IOException var11) {
                Bukkit.getConsoleSender().sendMessage("[MachineProcessed]" + ChatColor.RED + " WARNING: Could not save the example file!");
            }
        }
    }

    private Reader getReaderFromStream(InputStream initialStream) throws IOException {
        //this reads the encrypted resource files in the jar file
        byte[] buffer = IOUtils.toByteArray(initialStream);
        return new CharSequenceReader(new String(buffer));
    }

    private boolean checkMachines(YamlConfiguration temp) {
        try {
            return temp.contains(temp.getName());
        } catch (Exception var3) {
            return false;
        }
    }

    //look through all files in all folders
    public void fileNamesFromDirectory(File directory) {
        for (String fileName : Objects.requireNonNull(directory.list())) {
            if(new File(directory + File.separator + fileName).isDirectory()){
                fileNamesFromDirectory(new File(directory + File.separator + fileName));
                continue;
            }
            try {
                int ind = fileName.lastIndexOf(".");
                if (!fileName.substring(ind).equalsIgnoreCase(".yml") && !fileName.substring(ind).equalsIgnoreCase(".yaml")) {
                    continue;
                }
            }catch (Exception ex) { continue; }
            //check before adding the file to machineprocessed
            if (!checkMachines(YamlConfiguration.loadConfiguration(new File(directory + File.separator + fileName)))) {
                this.getServer().getConsoleSender().sendMessage("[MachineProcessed]" + ChatColor.RED + " Error in: " + fileName);
                continue;
            }
            machineList.add(new Machine(fileName.replace(".yml", "")));
        }
    }

    public void reloadMachineFiles() {
        machineList.clear();
        //load panel files
        fileNamesFromDirectory(machinecfg);
    }
    @Override
    public void onDisable() {
        SQLiteManager.unloadSQLite();
        // Plugin shutdown logic
    }
    public boolean isPluginEnabled(String plugin) { return getServer().getPluginManager().getPlugin(plugin) != null; }
    public static MachineProcessed getPlugin() { return plugin;}
}
