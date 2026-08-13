package com.engine.system;

import com.engine.component.Transform;
import com.engine.component.Velocity;
import com.engine.entity.Entity;
import com.engine.world.World;

public class MovementSystem {

    public MovementSystem() {
    }

    public void update(World world, float deltaTime) {
        
        for (Entity entity : world.getEntities()) {

            Transform transform = entity.getComponent(Transform.class);
            Velocity velocity = entity.getComponent(Velocity.class);

            if (transform == null || velocity == null) {
                continue;
            }

            transform.setX(
                    transform.getX() + velocity.getX() * deltaTime);

            transform.setY(
                    transform.getY() + velocity.getY() * deltaTime);
        }

    }
}
