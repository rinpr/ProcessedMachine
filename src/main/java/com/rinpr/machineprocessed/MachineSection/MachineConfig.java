package com.rinpr.machineprocessed.MachineSection;

import com.rinpr.machineprocessed.Utilities.ItemIdentifier;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.rinpr.machineprocessed.MachineProcessed.plugin;

public class MachineConfig {
    private static final File folder = new File(plugin.getDataFolder() + File.separator + "machine");

    /**
     * This method is used to generate a folder for storing machine's configuration files,
     * Will only work if the folder hasn't been created yet.
     */
    @Deprecated
    public static void generateMachineFolder() {
        if (!folder.exists()) {
            boolean dirCreated = folder.mkdirs();
            if (!dirCreated) { Bukkit.getLogger().warning("Failed to create directory."); }
        }
    }

    /**
     * This method is used to get list of machine's configuration files.
     * @return String Set of machine's config files.
     */
    public static Set<String> MachineList() {
        return Stream.of(Objects.requireNonNull(new File(folder + "/").listFiles()))
                .filter(file -> !file.isDirectory())
                .map(file -> file.getName().replaceFirst("[.][^.]+$", ""))
                .collect(Collectors.toSet());
    }

    /**
     * This method is used to get a machine's title aka GUI name of a machine.
     * @return String Set of machine's title name.
     */
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

    /**
     * This method is used to get an ItemStack of the machine's furniture blocks.
     * @return ItemStack Set of machine's furniture blocks.
     */
    public static Set<ItemStack> getAllMachines() {
        Set<ItemStack> machine = new HashSet<>();
        for (String filename : MachineConfig.MachineList()) {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(new File(folder + "/" + filename + ".yml"));
            List<ItemStack> name = new ItemIdentifier(Objects.requireNonNull(yaml.getString(filename + ".machine"))).getItemStack();
            machine.add(name.get(0));
            if (name.get(0) == null) { Bukkit.getLogger().warning("Incomplete configuration of gui machine"); }
        }
        return machine;
    }
    /**
     * This method is used to get machine's id from a ItemStack.
     * @param itemStack of machine itself.
     * @return A String of machine's id
     */
    public static String getMachineId(ItemStack itemStack) {
        for (String fileId : MachineConfig.MachineList()) {
            YamlConfiguration yaml = YamlConfiguration.loadConfiguration(new File(folder + "/" + fileId + ".yml"));
            List<ItemStack> name = new ItemIdentifier(Objects.requireNonNull(yaml.getString(fileId + ".machine"))).getItemStack();
            if (itemStack.equals(name.get(0))) { return fileId; }
        }
        return null;
    }
}
