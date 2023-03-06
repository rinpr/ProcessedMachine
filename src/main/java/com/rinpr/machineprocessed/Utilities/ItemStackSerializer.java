package com.rinpr.machineprocessed.Utilities;

import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ItemStackSerializer {
    String itemString;
    ItemStack itemStack;
    public ItemStackSerializer(String itemString) { this.itemString = itemString; }
    public ItemStackSerializer(ItemStack itemStack) { this.itemStack = itemStack; }

    public String getItemString() {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);

            // Write the size of the inventory
            dataOutput.writeInt(1);

            // Save every element in the list
            dataOutput.writeObject(itemStack);

            // Serialize that data
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ItemStack getItemStack() {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(itemString));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            ItemStack items;

            // Read the serialized inventory
            items = (ItemStack) dataInput.readObject();

            dataInput.close();
            return items;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
