package com.rinpr.machineprocessed.MachineSection;

import com.rinpr.machineprocessed.DataManager.SQLiteManager;
import com.rinpr.machineprocessed.MachineProcessed;
import com.rinpr.machineprocessed.api.Machine;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public class MachineGUI {
    MachineProcessed plugin = JavaPlugin.getPlugin(MachineProcessed.class);
    Machine machine;
    private final String guiName;
    private final Player player;
    public MachineGUI(String machineId, Player player) {
        for (Machine machines : plugin.machineList) {
            if (machines.getNamespace().equalsIgnoreCase(machineId)) {
                this.machine = machines;
                break;
            }
        }
        this.guiName = machine.getGUIName();
        this.player = player;
    }
    public void openGUI(int MachineId) {
        SQLiteManager item_slot = new SQLiteManager();
        int[] space_slot = new int[]{0,4,5,6,7,8,9,10,12,13,14,15,17,18,19,21,22,23,24,25,26};
        int[] machine_slot = new int[]{1,2,3,11,16,20};
        List<ItemStack> itemList = item_slot.getMachineInventory(MachineId);
        Inventory inv = Bukkit.createInventory(player, 27, guiName);

        for (int i = 0 ; i <= 5; i++) { inv.setItem(machine_slot[i],itemList.get(i)); }

        ItemStack space = new ItemStack(Material.LIGHT_GRAY_STAINED_GLASS_PANE);
        ItemMeta space_meta = space.getItemMeta();
        assert space_meta != null;
        space_meta.setDisplayName(" ");
        space_meta.setCustomModelData(1);
        space.setItemMeta(space_meta);
        for (int slot : space_slot) { inv.setItem(slot,space); }

        player.openInventory(inv);
    }
}
