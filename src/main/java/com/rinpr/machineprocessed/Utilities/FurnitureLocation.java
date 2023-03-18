package com.rinpr.machineprocessed.Utilities;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

public class FurnitureLocation {
    private Entity entity;
    private World world;
    private double x;
    private double y;
    private double z;
    public FurnitureLocation(Entity entity) {
        this.entity = entity;
        this.world = entity.getWorld();
        this.x = entity.getBoundingBox().getCenterX() - 0.5;
        this.y = Math.ceil(entity.getBoundingBox().getCenterY() - 0.5);
        this.z = entity.getBoundingBox().getCenterZ() - 0.5;
    }
    public FurnitureLocation(Entity entity, boolean isBreaking) {
        this.entity = entity;
        this.world = entity.getWorld();
        this.x = entity.getBoundingBox().getCenterX() - 0.5;
        this.y = Math.round(entity.getBoundingBox().getCenterY() - 0.5);
        this.z = entity.getBoundingBox().getCenterZ() - 0.5;
    }
    public Location getLocation() {
        return new Location(world,x,y,z);
    }
}
