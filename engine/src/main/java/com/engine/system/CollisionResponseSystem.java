package com.engine.system;

import com.engine.component.PhysicsBody;
import com.engine.entity.Entity;
import com.engine.event.CollisionEvent;
import com.engine.event.EventBus;

public class CollisionResponseSystem {

    public CollisionResponseSystem(EventBus eventBus) {

        eventBus.suscribe(event -> {

            if (!(event instanceof CollisionEvent collision)) {
                return;
            }
            System.out.println("chocamos");
            
            Entity a = collision.getEntityA();
            Entity b = collision.getEntityB();

            PhysicsBody bodyA = a.getComponent(PhysicsBody.class);
            PhysicsBody bodyB = b.getComponent(PhysicsBody.class);

            
            if (bodyA != null) {
                bodyA.setVelocityX(-bodyA.getVelocityX());
                bodyA.setVelocityY(-bodyA.getVelocityY());
                bodyA.setInputLockTimer(bodyA.getInputLockDuration());
            }

            if (bodyB != null) {
                bodyB.setVelocityX(-bodyB.getVelocityX());
                bodyB.setVelocityY(-bodyB.getVelocityY());
                bodyA.setInputLockTimer(bodyA.getInputLockDuration());
            }
        });
    }
}
