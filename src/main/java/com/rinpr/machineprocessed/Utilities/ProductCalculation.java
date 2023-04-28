package com.rinpr.machineprocessed.Utilities;

import com.rinpr.machineprocessed.DataManager.SQLiteManager;
import com.rinpr.machineprocessed.api.Machine;

import java.time.LocalDateTime;

public class ProductCalculation {
    private final Machine config;
    private final LocalDateTime now = LocalDateTime.now();
    private final LocalDateTime input;

    /**
     * @param machine_id The machine's id to get namespace from a database.
     * @param input Time you wanted to compare to.
     */
    public ProductCalculation(int machine_id, LocalDateTime input) {
        SQLiteManager database = new SQLiteManager();
        String machine = database.getNamespace(machine_id);
        this.config = new Machine(machine);
        this.input = input;
    }

    /** This method use to get the difference of present time and input time in seconds as integer.
     * @return Difference of time in second
     */
    private int GetTimeDiff() {
        return (int) java.time.Duration.between(this.input, this.now).getSeconds();
    }

    /**
     * @return The amount of product that should be.
     */
    public int GetProductAmount() {
        return  GetTimeDiff() / config.getTime();
    }
}
