package com.engine.system;

import com.engine.component.Transform;
import com.engine.component.PhysicsBody;
import com.engine.entity.Entity;
import com.engine.world.World;

public class MovementSystem {

    private CollisionSystem collisionSystem;

    public MovementSystem(CollisionSystem collisionSystem) {
        this.collisionSystem = collisionSystem;
    }

    public void update(World world, float deltaTime) {

        for (Entity entity : world.getEntities()) {

            Transform transform = entity.getComponent(Transform.class);
            PhysicsBody body = entity.getComponent(PhysicsBody.class);

            if (transform == null || body == null) {
                continue;
            }

            float nextX = transform.getX()
                    + body.getVelocityX() * deltaTime;

            if (!collisionSystem.willCollide(
                    world,
                    entity,
                    nextX,
                    transform.getY())) {

                transform.setX(nextX);
            }

            float nextY = transform.getY()
                    + body.getVelocityY() * deltaTime;

            if (!collisionSystem.willCollide(
                    world,
                    entity,
                    transform.getX(),
                    nextY)) {

                transform.setY(nextY);
            }
        }

    }
}
