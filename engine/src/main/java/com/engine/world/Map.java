package com.engine.world;

public class Map {

   
    private TileLayer terrain;
    private TileLayer buildings;
    private TileLayer decoration;
    private TileLayer foreground;


    public Map(TileLayer terrain, TileLayer buildings, TileLayer decoration, TileLayer foreground) {
        this.terrain = terrain;
        this.buildings = buildings;
        this.decoration = decoration;
        this.foreground = foreground;
    }

    public TileLayer getTerrain() {
        return terrain;
    }

    public TileLayer getDecoration() {
        return decoration;
    }

    public TileLayer getBuildings() {
        return buildings;
    }

    public TileLayer getForeground() {
        return foreground;
    }

}
