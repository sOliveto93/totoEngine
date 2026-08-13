package com.engine.system;

import com.engine.entity.Entity;
import com.engine.world.Animation;
import com.engine.world.World;

public class AnimationSystem {

    public AnimationSystem() {
    }

    public void update(World world, float deltaTime) {
        for (Entity entity : world.getEntities()) {
            Animation animation = entity.getComponent(Animation.class);
            if (animation != null) {
                animation.update(deltaTime);
            }
        }
    }
}
