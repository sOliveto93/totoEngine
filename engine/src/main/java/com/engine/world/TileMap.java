package com.engine.world;

public class TileMap {

    private int[][] map = {
            { 3, 0, 0, 0, 0 },
            { 0, 1, 1, 1, 0 },
            { 0, 1, 2, 1, 0 },
            { 0, 0, 0, 0, 0 } };

    public TileMap() {

    }

    public void setId(int x, int y, int id) {
        map[y][x] = id;
    }

    public int getId(int x, int y) {
        return map[y][x];
    }

    public int getWidth() {
        return map[0].length;
    }

    public int getHeight() {
        return map.length;
    }
}
