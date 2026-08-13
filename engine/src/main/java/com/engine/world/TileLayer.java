package com.engine.world;

public class TileLayer {

    private int[][] layer;

    public TileLayer(int width, int height) {
        layer = new int[height][width];
    }
    public TileLayer(int[][] layer){
        this.layer=layer;
    }

    public void setId(int x, int y, int id) {
        layer[y][x] = id;
    }

    public int getId(int x, int y) {
        return layer[y][x];
    }

    public int getWidth() {
        return layer[0].length;
    }

    public int getHeight() {
        return layer.length;
    }
}
