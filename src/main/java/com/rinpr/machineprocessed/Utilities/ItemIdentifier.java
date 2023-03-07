package com.rinpr.machineprocessed.Utilities;

import dev.lone.itemsadder.api.CustomStack;
import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.Function;

import static com.rinpr.machineprocessed.MachineProcessed.plugin;

public class ItemIdentifier {
    private List<ItemType> items;
    public ItemIdentifier(List<String> itemStrings) {
        this.items = new ArrayList<>();
        for (String itemString : itemStrings) {
            String[] parts = itemString.split(":");
            items.add(new ItemType(parts[0], parts[1]));
        }
    }
    public ItemIdentifier(String item) {
        this.items = new ArrayList<>();
        String[] parts = item.split(":");
        items.add(new ItemType(parts[0], parts[1]));
    }
    public List<ItemStack> getItemStack() {
        List<ItemStack> result = new ArrayList<>();
        Map<String, Function<String, ItemStack>> typeToStackFunction = new HashMap<>();
        typeToStackFunction.put("vanilla", this::getVanillaItemStack);
        typeToStackFunction.put("oraxen", this::getOraxenItemStack);
        typeToStackFunction.put("itemsadder", this::getItemsAdderItemStack);
        for (ItemType item : items) {
            Function<String, ItemStack> stackFunction = typeToStackFunction.get(item.getType());
            if (stackFunction != null) {
                result.add(stackFunction.apply(item.getId()));
            }
        }
        return result;
    }
    public static String getItemType(ItemStack itemStack) {
        if (plugin.isPluginEnabled("Itemsadder") && plugin.isPluginEnabled("Oraxen")) return null;
        if (plugin.isPluginEnabled("Itemsadder")) {
            if (CustomStack.byItemStack(itemStack) != null) return "itemsadder";
        } else if (plugin.isPluginEnabled("Oraxen")) {
            if (OraxenItems.getIdByItem(itemStack) != null) return "oraxen"; }
        return "vanilla";
    }
    public static String getNamespacedID(ItemStack itemStack) { return CustomStack.byItemStack(itemStack).getNamespacedID(); }
    public static List<String> getNamespacedID(Set<ItemStack> items) {
        List<String> NamespacedID = new ArrayList<>();
        for (ItemStack item : items) {
            NamespacedID.add(CustomStack.byItemStack(item).getNamespacedID());
        }
        return NamespacedID;
    }
    private ItemStack getVanillaItemStack(String itemID) {
        return new ItemStack(Objects.requireNonNull(Material.getMaterial(itemID.toUpperCase())));
    }
    private ItemStack getOraxenItemStack(String itemID) {
        return OraxenItems.getItemById(itemID).build();
    }
    private ItemStack getItemsAdderItemStack(String itemID) {
        return CustomStack.getInstance(itemID).getItemStack();
    }
    private static class ItemType {
        private final String type;
        private final String id;
        public ItemType(String type, String id) {
            this.type = type;
            this.id = id;
        }
        public String getType() {
            return type;
        }
        public String getId() {
            return id;
        }
    }
}
