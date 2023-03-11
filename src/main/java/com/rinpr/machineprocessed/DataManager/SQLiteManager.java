package com.rinpr.machineprocessed.DataManager;

import com.rinpr.machineprocessed.MachineProcessed;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;
import java.sql.*;
import java.util.Objects;

import static com.rinpr.machineprocessed.MachineProcessed.plugin;

public class SQLiteManager {
    private final String dbFile;
    private final String createTable = "CREATE TABLE IF NOT EXISTS machine_location (machine_id INTEGER PRIMARY KEY AUTOINCREMENT, world TEXT, x REAL, y REAL, z REAL)";
    private final String addMachine = "INSERT INTO machine_location (world, x, y, z) VALUES (?, ?, ?, ?)";
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
            connection.prepareStatement(createTable).execute();
        } catch (SQLException exception) { exception.printStackTrace(); }
    }
    public void addMachine(Location location) {
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            PreparedStatement statement = connection.prepareStatement(addMachine)) {
            statement.setString(1, Objects.requireNonNull(location.getWorld()).toString().substring(16, location.getWorld().toString().length() - 1));
            statement.setDouble(2, location.getX());
            statement.setDouble(3, location.getY());
            statement.setDouble(4, location.getZ());
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
        try (Connection connection = DriverManager.getConnection("jdbc:sqlite:" + plugin.getDataFolder().getAbsolutePath() + File.separator + "machine.db");
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
    public boolean hasLocation(int MachineId) { return getMachineLocation(MachineId) != null; }
    public boolean hasMachineId(Location location) { return getMachineId(location) != -1; }
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
