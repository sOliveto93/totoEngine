package com.engine.system;

import com.engine.component.InputController;
import com.engine.component.PhysicsBody;
import com.engine.entity.Entity;
import com.engine.world.World;

public class PhysicsSystem {


    public void update(World world, float deltaTime){
        for (Entity entity : world.getEntities()) {
            InputController controller = entity.getComponent(InputController.class);
            PhysicsBody body = entity.getComponent(PhysicsBody.class);

            if(controller == null || body == null){
                continue;
            }
            // Reducimos el tiempo de bloqueo
        if (body.getInputLockTimer() > 0) {

            body.setInputLockTimer(
                    body.getInputLockTimer() - deltaTime);

            continue;
        }

            body.setVelocityX(controller.getDirectionX() * body.getSpeed());
            body.setVelocityY(controller.getDirectionY() * body.getSpeed());
        }
    }
}
