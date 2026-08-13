package com.engine.world;

import java.util.List;

import com.engine.entity.Entity;

public class World {

    private Map map;
    private List<Entity> entities;

    public World(Map map, List<Entity> entities) {
        this.map = map;
        this.entities = entities;
    }

    public Map getMap() {
        return map;
    }

    public List<Entity> getEntities() {
        return entities;
    }
}
