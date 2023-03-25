package com.rinpr.machineprocessed.DataManager;

import com.rinpr.machineprocessed.Utilities.ItemStackSerializer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.rinpr.machineprocessed.MachineProcessed.plugin;

public class SQLiteManager {
    private final String dbFile;
    private final String createMachineTable = "CREATE TABLE IF NOT EXISTS machine_location (machine_id INTEGER PRIMARY KEY AUTOINCREMENT, machine TEXT, world TEXT, x REAL, y REAL, z REAL)";
    private final String createInventoryTable = "CREATE TABLE IF NOT EXISTS machine_inventory (machine_id INTEGER PRIMARY KEY AUTOINCREMENT, slot1 TEXT, slot2 TEXT, slot3 TEXT, slot16 TEXT, slot20 TEXT)";
    private final String addMachine = "INSERT INTO machine_location (machine, world, x, y, z) VALUES (?, ?, ?, ?, ?)";
    private final String addMachineInventory = "INSERT INTO machine_inventory (slot1, slot2, slot3, slot16, slot20) VALUES (?, ?, ?, ?, ?)";
    private final String selectMachineInventory= "SELECT * FROM machine_inventory WHERE machine_id=?";
    private final String updateMachineInventory = "UPDATE machine_inventory SET slot1=?, slot2=?, slot3=?, slot16=?, slot20=? WHERE machine_id=?";
    private final String deleteMachineInventory = "DELETE FROM machine_inventory WHERE machine_id = ?";
    private final String deleteMachine = "DELETE FROM machine_location WHERE world = ? AND x = ? AND y = ? AND z = ?";
    private final String selectMachineID = "SELECT * FROM machine_location WHERE machine_id=?";
    private final String selectMachineFromLocation = "SELECT * FROM machine_location WHERE world = ? AND x = ? AND y = ? AND z = ?";
    public SQLiteManager() {
        this.dbFile = plugin.getDataFolder().getAbsolutePath() + File.separator + "machine.db";
        loadSQLite();
        createTables();
    }
    private void createTables(){
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);) {
            connection.prepareStatement(createMachineTable).execute();
            connection.prepareStatement(createInventoryTable).execute();
        } catch (SQLException exception) { exception.printStackTrace(); }
    }
    public void createMachineInventory() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
        PreparedStatement statement = connection.prepareStatement(addMachineInventory)) {
            for (int i = 1; i < 6 ; i++) { statement.setString(i, new ItemStackSerializer(new ItemStack(Material.DIAMOND)).toItemString()); }
            statement.executeUpdate();
        } catch (SQLException e) { Bukkit.getLogger().severe("An error occurred while adding ItemStack to Database: " + e.getMessage());}
    }
    public List<ItemStack> getMachineInventory(int MachineId) { // always return null bcz ItemStackSerializer
        List<String> item_string = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
             PreparedStatement statement = connection.prepareStatement(selectMachineInventory)) {
            statement.setInt(1, MachineId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                item_string.add(resultSet.getString("slot1"));
                item_string.add(resultSet.getString("slot2"));
                item_string.add(resultSet.getString("slot3"));
                item_string.add(resultSet.getString("slot16"));
                item_string.add(resultSet.getString("slot20"));
            }
        } catch (SQLException e) { Bukkit.getLogger().severe("An error occurred while loading ItemStack from Database: " + e.getMessage()); }
        ItemStackSerializer itemStackSerializer = new ItemStackSerializer(item_string);
        return  itemStackSerializer.toItemStacks();
    }
    public List<String> getRawMachineInventory(int MachineId) {
        List<String> item_string = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
             PreparedStatement statement = connection.prepareStatement(selectMachineInventory)) {
            statement.setInt(1, MachineId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                item_string.add(resultSet.getString("slot1"));
                item_string.add(resultSet.getString("slot2"));
                item_string.add(resultSet.getString("slot3"));
                item_string.add(resultSet.getString("slot16"));
                item_string.add(resultSet.getString("slot20"));
            }
        } catch (SQLException e) { Bukkit.getLogger().severe("An error occurred while loading ItemStack from Database: " + e.getMessage()); }
        return item_string;
    }
    public void updateMachineInventory(int MachineId, ItemStack slot1, ItemStack slot2, ItemStack slot3, ItemStack slot16, ItemStack slot20) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
        PreparedStatement statement = connection.prepareStatement(updateMachineInventory)) {
            statement.setString(1, new ItemStackSerializer(slot1).toItemString());
            statement.setString(2, new ItemStackSerializer(slot2).toItemString());
            statement.setString(3, new ItemStackSerializer(slot3).toItemString());
            statement.setString(4, new ItemStackSerializer(slot16).toItemString());
            statement.setString(5, new ItemStackSerializer(slot20).toItemString());
            statement.setInt(6, MachineId);
            statement.executeUpdate();
        } catch (SQLException e) { Bukkit.getLogger().severe("An error occurred while updating ItemStack to Database: " + e.getMessage()); }
    }
    public void deleteMachineInventory(int MachineId) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
        PreparedStatement statement = connection.prepareStatement(deleteMachineInventory)) {
            statement.setInt(1, MachineId);
            statement.executeUpdate();
        } catch (SQLException e) { Bukkit.getLogger().severe("An error occurred while adding ItemStack to Database: " + e.getMessage()); }
    }
    public void addMachine(Location location, String Machine) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            PreparedStatement statement = connection.prepareStatement(addMachine)) {
            statement.setString(1, Machine);
            statement.setString(2, Objects.requireNonNull(location.getWorld()).toString().substring(16, location.getWorld().toString().length() - 1));
            statement.setDouble(3, location.getX());
            statement.setDouble(4, location.getY());
            statement.setDouble(5, location.getZ());
            statement.executeUpdate();
            Bukkit.getLogger().info("Machine data add successfully");
        } catch (SQLException e) { Bukkit.getLogger().severe("An error occurred while adding data to Database: " + e.getMessage()); }
    }
    public void deleteMachine(Location location) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
             PreparedStatement statement = connection.prepareStatement(deleteMachine)) {
            statement.setString(1, Objects.requireNonNull(location.getWorld()).toString().substring(16, location.getWorld().toString().length() - 1));
            statement.setDouble(2, location.getX());
            statement.setDouble(3, location.getY());
            statement.setDouble(4, location.getZ());
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) { Bukkit.getLogger().info("Machine data deleted successfully");
            } else { Bukkit.getLogger().warning("No machine data found for location: " + location.toString()); }
        } catch (SQLTransientException e) { Bukkit.getLogger().warning("A transient error occurred while deleting data from Database: " + e.getMessage());
        } catch (SQLException e) { Bukkit.getLogger().severe("An error occurred while deleting data from Database: " + e.getMessage()); }
    }
    public Location getMachineLocation(int MachineId) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            PreparedStatement statement = connection.prepareStatement(selectMachineID)) {
            statement.setString(1, String.valueOf(MachineId));
            ResultSet result = statement.executeQuery();
            if (result.next()) {
                String worldName = result.getString("world");
                World world = Bukkit.getWorld(worldName);
                double x = result.getDouble("x");
                double y = result.getDouble("y");
                double z = result.getDouble("z");
                return new Location(world, x, y, z);
            }
        } catch (SQLException e) { Bukkit.getLogger().severe("An error occurred while getting data from Database: " + e.getMessage()); }
        return null;
    }
    public int getMachineId(Location location) {
        int machineId = -1; // default value if no machine is found
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
             PreparedStatement statement = connection.prepareStatement(selectMachineFromLocation)) {
            statement.setString(1, Objects.requireNonNull(location.getWorld()).toString().substring(16, location.getWorld().toString().length() - 1));
            statement.setDouble(2, location.getX());
            statement.setDouble(3, location.getY());
            statement.setDouble(4, location.getZ());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) { machineId = resultSet.getInt("machine_id");
            } else { Bukkit.getLogger().warning("No machine id found for location: " + location); }
        } catch (SQLException e) { Bukkit.getLogger().severe("An error occurred while querying data from Database: " + e.getMessage()); }
        return machineId;
    }
    public boolean containsLocation(int MachineId) { return getMachineLocation(MachineId) != null; }
    public boolean containsMachineId(Location location) { return getMachineId(location) != -1; }
    public static void loadSQLite() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath() + File.separator + "machine.db")){
            Bukkit.getLogger().info("Database loaded successfully");
        } catch (SQLException e) { Bukkit.getLogger().severe("An error occurred while loading Database: " + e.getMessage()); }
    }
    public static void unloadSQLite() {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath() + File.separator + "machine.db")){
            connection.close();
            Bukkit.getLogger().info("Successfully closed Database");
        } catch (SQLException e) { Bukkit.getLogger().severe("Failed to close Database: " + e.getMessage()); }
    }
}
