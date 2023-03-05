package com.rinpr.machineprocessed.MachineSection;

import com.rinpr.machineprocessed.Utilities.ItemIdentifier;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.rinpr.machineprocessed.MachineProcessed.plugin;

public class MachineConfig {
    private static final File folder = new File(plugin.getDataFolder(), "/machine");
    private String id;
    private File Machine;
    private YamlConfiguration yaml;
    public MachineConfig(String id) {
        this.id = id;
        this.Machine = new File(plugin.getDataFolder(), "/machine/" + id + ".yml");
        if (!Machine.exists()) {
            Bukkit.getLogger().warning("No machine with that id!");
        }
        YamlConfiguration MachineData = new YamlConfiguration();
        try {
            MachineData.load(Machine);
        } catch (IOException | InvalidConfigurationException e) {
            throw new RuntimeException(e);
        }
        this.yaml = MachineData;
    }
    public static void generateMachineFolder() {
        if (!folder.exists()) {
            boolean dirCreated = folder.mkdirs();
            if (!dirCreated) { Bukkit.getLogger().warning("Failed to create directory."); }
        }
        Bukkit.getLogger().info("Machine folder found!");
    }
    public static Set<String> MachineList() {
        return Stream.of(Objects.requireNonNull(new File(folder + "/").listFiles()))
                .filter(file -> !file.isDirectory())
                .map(file -> file.getName().replaceFirst("[.][^.]+$", ""))
                .collect(Collectors.toSet());
    }
    public static Set<String> MachineName() {
        Set<String> guiName = new HashSet<>();
        for (String filename : MachineConfig.MachineList()) {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(new File(folder + "/" + filename + ".yml"));
            String name = yaml.getString(filename + ".name");
            guiName.add(name);
            if (name == null) { Bukkit.getLogger().warning("Incomplete configuration of gui name"); }
        }

        return guiName;
    }
    public String getGUIName() {
        return yaml.getString(id + ".name");
    }
    public List<ItemStack> getProduct() {
        return new ItemIdentifier(Objects.requireNonNull(yaml.getString(id + ".product"))).getItemStack();
    }
    public List<ItemStack> getIngredient() {
        return new ItemIdentifier(yaml.getStringList(id + ".ingredients")).getItemStack();
    }
    public List<ItemStack> getFuel() {
        return new ItemIdentifier(Objects.requireNonNull(yaml.getString(id + ".fuel"))).getItemStack();
    }
    public int getTime() { return yaml.getInt(id + ".time"); }
}
