package com.rinpr.machineprocessed.Utilities;

import dev.lone.itemsadder.api.CustomStack;
//import io.th0rgal.oraxen.api.OraxenItems;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.function.BiFunction;

public class ItemIdentifier {
    private List<ItemType> items;
    public ItemIdentifier(List<String> itemStrings) {
        this.items = new ArrayList<>();
        for (String itemString : itemStrings) {
            String[] parts = itemString.split(":");
            items.add(new ItemType(parts[0], parts[1], Integer.parseInt(parts[2])));
        }
    }
    public ItemIdentifier(String item) {
        this.items = new ArrayList<>();
        String[] parts = item.split(":");
        items.add(new ItemType(parts[0], parts[1], Integer.parseInt(parts[2])));
    }

    /**
     * @return List of ItemStack that from config.
     */
    public List<ItemStack> getItemStack() {
        List<ItemStack> result = new ArrayList<>();
        Map<String, BiFunction<String, Integer, ItemStack>> typeToStackFunction = new HashMap<>();
        typeToStackFunction.put("vanilla", this::getVanillaItemStack);
        typeToStackFunction.put("oraxen", this::getOraxenItemStack);
        typeToStackFunction.put("itemsadder", this::getItemsAdderItemStack);
        for (ItemType item : items) {
            BiFunction<String, Integer, ItemStack> stackFunction = typeToStackFunction.get(item.getType());
            if (stackFunction != null) {
                result.add(stackFunction.apply(item.getId(), item.getAmount()));
            }
        }
        return result;
    }

    /**
     * @param items Set of ItemStack you want to check its name space.
     * @return String list of namespace of an item you provided in parameters.
     */
    public static List<String> getNamespacedID(Set<ItemStack> items) {
        List<String> NamespacedID = new ArrayList<>();
        for (ItemStack item : items) {
            NamespacedID.add(CustomStack.byItemStack(item).getNamespacedID());
        }
        return NamespacedID;
    }

    /**
     * @param itemID A Minecraft material.
     * @param amount An amount of item.
     * @return Vanilla ItemStack.
     */
    private ItemStack getVanillaItemStack(String itemID, int amount) {
        ItemStack i = new ItemStack(Objects.requireNonNull(Material.getMaterial(itemID.toUpperCase())));
        i.setAmount(amount);
        return i;
    }

    /**
     * @param itemID An Oraxen item id's.
     * @param amount An amount of item.
     * @return ItemStack from Oraxen plugin.
     */
    private ItemStack getOraxenItemStack(String itemID, int amount) {
//        ItemStack i = OraxenItems.getItemById(itemID).build();
//        i.setAmount(amount);
        return new ItemStack(Material.AIR);
    }

    /**
     * @param itemID An Itemsadder item's id.
     * @param amount An amount of item.
     * @return ItemStack from ItemsAdder plugin.
     */
    private ItemStack getItemsAdderItemStack(String itemID, int amount) {
        ItemStack i = CustomStack.getInstance(itemID).getItemStack();
        i.setAmount(amount);
        return i;
    }

    /**
     * This private class is for storing Item's data from config separating
     * string from "vanilla:STICK:1" by vanilla as a type, STICK as id and 1 as amount.
     */
    private static class ItemType {
        private final String type;
        private final String id;
        private final int amount;

        /**
         * @param type A type of item. Could be vanilla, oraxen or itemsadder.
         * @param id An id of item for oraxen and itemsadder, for vanilla will be material.
         * @param amount An amount of the item's.
         */
        public ItemType(String type, String id, int amount) {
            this.type = type;
            this.id = id;
            this.amount = amount;
        }

        /**
         * @return Item's type such as vanilla, oraxen or itemsadder.
         */
        public String getType() {
            return type;
        }

        /**
         * @return Item's id of oraxen and itemsadder, for vanilla will be material.
         */
        public String getId() {
            return id;
        }

        /**
         * @return Amount of an item's.
         */
        public int getAmount() { return amount; }
    }
}
