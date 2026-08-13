package com.engine.world;

import com.engine.graphics.TextureRegion;

public class AnimatedSprite extends Sprite{

    Animation animation;

    public AnimatedSprite(Animation animation,float x, float y) {
        super(animation.getCurrentFrame(), x, y);
        this.animation=animation;
        
    }

    public void update(float deltaTime){

        animation.update(deltaTime);
    
    }

    @Override
    public TextureRegion getRegion(){
        return animation.getCurrentFrame();
    }

}
