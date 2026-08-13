package com.engine.world;

import java.util.List;

public class World {

    private Map map;
    private List<Sprite> sprites;

    public World(Map map, List<Sprite> sprites) {
        this.map = map;
        this.sprites = sprites;
    }

    public Map getMap() {
        return map;
    }

    public List<Sprite> getSprites() {
        return sprites;
    }
}
