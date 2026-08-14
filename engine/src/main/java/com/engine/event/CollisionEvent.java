package com.engine.event;

import com.engine.entity.Entity;

public class CollisionEvent implements Event{

    private Entity entityA;
    private Entity entityB;

    public CollisionEvent(Entity entityA,Entity entityB){
        this.entityA=entityA;
        this.entityB=entityB;
    }
    public Entity getEntityA() {
        return entityA;
    }

    public Entity getEntityB() {
        return entityB;
    }
}
