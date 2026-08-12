package com.engine.world;

import com.engine.graphics.TextureRegion;

public class Sprite {
    
    //fuente grafica
    private TextureRegion regionTexture;

    private float x;
    private float y;

    public Sprite(TextureRegion regionTexture , float x, float y){
        this.regionTexture=regionTexture;
        this.x=x;
        this.y=y;
    }

    public TextureRegion getRegion() {
        return regionTexture;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
