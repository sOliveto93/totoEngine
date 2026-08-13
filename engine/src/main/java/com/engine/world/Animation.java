package com.engine.world;

import com.engine.component.Component;
import com.engine.graphics.TextureRegion;

public class Animation  implements Component{
TextureRegion[] frames;
    private int currentFrame;
    private float timer;
    private float frameDuration;

    public Animation(TextureRegion[] frames,float frameDuration){

        this.frames=frames;
        this.frameDuration=frameDuration;
    }

    public void update(float deltaTime){
        timer+=deltaTime;

        if(timer >= frameDuration){
            timer-= frameDuration;
            currentFrame++;
        }
        if(currentFrame >= frames.length){
            currentFrame=0;
        }
    }
    public TextureRegion getCurrentFrame() {
        return frames[currentFrame];
    }
}
