package com.engine.component;

public class Velocity implements Component{

    private float x;
    private float y;
    private float speed;
    public Velocity(){}

    public Velocity(float speed){
        this.speed=speed;
    }
     public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    
}
