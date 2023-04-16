package com.rinpr.machineprocessed.api;

import com.rinpr.machineprocessed.MachineProcessed;
import com.rinpr.machineprocessed.Utilities.ItemIdentifier;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class Machine {
    MachineProcessed plugin = JavaPlugin.getPlugin(MachineProcessed.class);
    private YamlConfiguration machineConfig;
    private String machineName;
    private File machineFile;

    public Machine(File file, String name) {
        this.machineFile = file;
        this.machineConfig = YamlConfiguration.loadConfiguration(file);
        this.machineName = name;
    }

    public Machine(YamlConfiguration config, String name){
        this.machineName= name;
        this.machineConfig = config;
    }
    public Machine(String name){
        this.machineName= name;
        this.machineFile = new File(plugin.getDataFolder() + File.separator + "machine" +  File.separator + name + ".yml");
        this.machineConfig = YamlConfiguration.loadConfiguration(this.machineFile);
    }

    //set elements of the machine
    public void setName(String name){
        this.machineName = name;
    }

    public void setConfig(YamlConfiguration config){
        this.machineConfig = config;
    }

    public void setFile(File file){
        this.machineFile = file;
        this.machineConfig = YamlConfiguration.loadConfiguration(file);
    }

    /**
     * @return A machine's name.
     */
    public String getName(){
        return this.machineName;
    }

    /**
     * @return Configuration of this config.
     */
    public YamlConfiguration getConfig(){
        return this.machineConfig;
    }

    /**
     * @return Machine's file
     */
    public File getFile(){
        return this.machineFile;
    }

    /**
     * @return Machine Block.
     */
    public ItemStack getMachine() { return new ItemIdentifier(Objects.requireNonNull(this.getConfig().getString(this.machineName + ".machine"))).getItemStack().get(0); }

    /**
     * @return GUI name.
     */
    public String getGUIName() { return this.getConfig().getString(this.machineName + ".name"); }

    /**
     * @return Machine's product.
     */
    public ItemStack getProduct() { return new ItemIdentifier(Objects.requireNonNull(this.getConfig().getString(this.machineName + ".product"))).getItemStack().get(0); }

    /**
     * @return List of machine's ingredients.
     */
    public List<ItemStack> getIngredient() { return new ItemIdentifier(this.getConfig().getStringList(this.machineName + ".ingredients")).getItemStack(); }

    /**
     * @return Machine's fuel.
     */
    public ItemStack getFuel() { return new ItemIdentifier(Objects.requireNonNull(this.getConfig().getString(this.machineName + ".fuel"))).getItemStack().get(0); }

    /**
     * @return A time processed of the machine.
     */
    public int getTime() { return this.getConfig().getInt(this.machineName + ".time"); }
}
