package com.engine.world;

public class Map {

    private TileLayer terrain;
    private TileLayer buildings;
    private TileLayer decoration;
    private TileLayer foreground;
    private TileLayer collisionLayer;

    private int tileWidth;
    private int tileHeight;


    public Map(TileLayer terrain, TileLayer buildings, TileLayer decoration, TileLayer foreground,
            TileLayer collisionLayer,int tileWidth,
            int tileHeight) {
        this.terrain = terrain;
        this.buildings = buildings;
        this.decoration = decoration;
        this.foreground = foreground;
        this.collisionLayer = collisionLayer;
         this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
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

    public TileLayer getCollisionLayer() {
        return collisionLayer;
    }

    public int getTileWidth() {
        return tileWidth;
    }

    public int getTileHeight() {
        return tileHeight;
    }
    

}
