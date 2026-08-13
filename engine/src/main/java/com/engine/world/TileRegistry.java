package com.engine.world;

import java.util.HashMap;
import java.util.Map;

public class TileRegistry {

    private Map<Integer, Tile> tiles;

    public TileRegistry() {
        tiles = new HashMap<>();
    }

    public void register(int id, Tile tile) {
        tiles.put(id, tile);
    }

    public Tile get(int id) {
        Tile tile = tiles.get(id);

        if (tile == null) {
            return tiles.get(0);
        }

        return tile;
    }
}
