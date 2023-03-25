package com.rinpr.machineprocessed.Utilities;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ItemStackSerializer {
    String itemString;
    ItemStack itemStack;
    List<String> itemStrings;
    public ItemStackSerializer() {}

    /**
     * Single item string.
     * @param itemString Base64 String of an ItemStack to use.
     */
    public ItemStackSerializer(String itemString) { this.itemString = itemString; }

    /**
     * Single ItemStack.
     * @param itemStack an ItemStack to use.
     */
    public ItemStackSerializer(ItemStack itemStack) { this.itemStack = itemStack; }

    /**
     * Multiple item string.
     * @param listOfString List of Base64 String to use.
     */
    public ItemStackSerializer(List<String> listOfString) { this.itemStrings = listOfString; }

    /**
     * This method is used encode ItemStack into base64 string.
     * @return Base64 string of an ItemStack.
     */
    public String toItemString() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeInt(1);
            dataOutput.writeObject(itemStack);
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is used to turn base64 string into ItemStack.
     * @return ItemStack decoded from base64 string.
     */
    public ItemStack toItemStack() {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(itemString));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack[] items = new ItemStack[dataInput.readInt()];
            for (int i = 0; i < items.length; i++) { items[i] = (ItemStack) dataInput.readObject(); }
            dataInput.close();
            return items[0];
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * This method is used to turn list of base64 strings into list of ItemStack.
     * @return List of ItemStack that decoded from base64 string list.
     */
    public List<ItemStack> toItemStacks() {
        List<ItemStack> items = new ArrayList<>();
        for (String itemString : itemStrings) {
            ItemStackSerializer serializer = new ItemStackSerializer(itemString);
            items.add(serializer.toItemStack());
        }
        return items;
    }

    /**
     * This method is used to turn list of base64 strings into list of ItemStack.
     * @param itemStrings is a list of base64 encoded string.
     * @return List of ItemStack that decoded from base64 string list.
     */
    public static List<ItemStack> toItemStacks(List<String> itemStrings) {
        List<ItemStack> items = new ArrayList<>();
        for (String itemString : itemStrings) {
            ItemStackSerializer serializer = new ItemStackSerializer(itemString);
            items.add(serializer.toItemStack());
        }
        return items;
    }
}
