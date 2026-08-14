package com.engine.system;

import java.util.List;

import com.engine.component.Collider;
import com.engine.component.Transform;
import com.engine.entity.Entity;
import com.engine.event.CollisionEvent;
import com.engine.event.EventBus;
import com.engine.world.TileLayer;
import com.engine.world.World;

public class CollisionSystem {

    private EventBus eventBus;

    public CollisionSystem(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    // esto a futuro tine que ser optimizado
    public void update(World world) {

        List<Entity> entities = world.getEntities();
        for (int i = 0; i < entities.size(); i++) {
            Entity a = entities.get(i);
            Transform transformA = a.getComponent(Transform.class);
            Collider colliderA = a.getComponent(Collider.class);

            if (transformA == null || colliderA == null) {
                continue;
            }

            for (int j = i + 1; j < entities.size(); j++) {
                Entity b = entities.get(j);

                Transform transformB = b.getComponent(Transform.class);
                Collider colliderB = b.getComponent(Collider.class);

                if (transformB == null || colliderB == null) {
                    continue;
                }

                if (intersects(transformA, colliderA, transformB, colliderB)) {
                    // aca averiguamos quien es quien y enviamos un evento distinto al bus?
                    eventBus.publish(new CollisionEvent(a, b));

                }
            }
        }
    }

    private boolean intersects(
            Transform transformA,
            Collider colliderA,
            Transform transformB,
            Collider colliderB) {

        float leftA = transformA.getX() + colliderA.getOffsetX();
        float rightA = leftA + colliderA.getWidth();
        float topA = transformA.getY() + colliderA.getOffsetY();
        float bottomA = topA + colliderA.getHeight();

        float leftB = transformB.getX() + colliderB.getOffsetX();
        float rightB = leftB + colliderB.getWidth();
        float topB = transformB.getY() + colliderB.getOffsetY();
        float bottomB = topB + colliderB.getHeight();
        // esto para el superposicion
        return leftA < rightB &&
                rightA > leftB &&
                topA < bottomB &&
                bottomA > topB;
    }

    public boolean willCollide(World world, Entity entity, float nextX, float nextY) {

        Collider colliderA = entity.getComponent(Collider.class);

        if (colliderA == null) {
            return false;
        }

        // mapa
        if (collidesWithMap(world, colliderA, nextX, nextY)) {
            return true;
        }

        List<Entity> entities = world.getEntities();

        for (Entity other : entities) {

            if (entity == other) {
                continue;
            }

            Transform transformB = other.getComponent(Transform.class);
            Collider colliderB = other.getComponent(Collider.class);

            if (transformB == null || colliderB == null) {
                continue;
            }

            Transform futureTransform = new Transform(nextX, nextY);

            if (intersects(
                    futureTransform,
                    colliderA,
                    transformB,
                    colliderB)) {

                return true;
            }
        }

        return false;
    }

    private boolean isSolid(World world, int tileX, int tileY) {

        TileLayer collisionLayer = world.getMap().getCollisionLayer();

        if (tileX < 0 || tileY < 0 || tileX >= collisionLayer.getWidth() || tileY >= collisionLayer.getHeight()) {
            return true;
        }
        return collisionLayer.getId(tileX, tileY) != 0;
    }

    private boolean collidesWithMap(World world, Collider collider, float x, float y) {

        float left = x + collider.getOffsetX();
        float right = left + collider.getWidth();

        float top = y + collider.getOffsetY();
        float bottom = top + collider.getHeight();

        int tileWidth = world.getMap().getTileWidth();
        int tileHeight = world.getMap().getTileHeight();

        int startX = (int) Math.floor(left / tileWidth);
        int endX = (int) Math.floor((right - 0.001f) / tileWidth);

        int startY = (int) Math.floor(top / tileHeight);
        int endY = (int) Math.floor((bottom - 0.001f) / tileHeight);

        for (int tileY = startY; tileY <= endY; tileY++) {

            for (int tileX = startX; tileX <= endX; tileX++) {

                if (isSolid(world, tileX, tileY)) {
                    return true;
                }
            }
        }

        return false;
    }

}
