package com.engine.graphics;

public class Camera {

    private float x;
    private float y;

    public Camera(float x,float y){
        this.x=x;
        this.y=y;
    }
    
     public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setPosition(float x, float y) {
        this.x = x;
        this.y = y;
    }
}
