package com.engine.component;

public class PhysicsBody implements Component{

    private float velocityX;
    private float velocityY;
    
    private float mass;
    private float speed;

    private float inputLockTimer;
    private float inputLockDuration=0.05f;
    
    public PhysicsBody(float mass,float speed){
        this.mass=mass;
        this.speed=speed;
    }

    public float getVelocityX() {
        return velocityX;
    }

    public float getVelocityY() {
        return velocityY;
    }

    public void setVelocityX(float velocityX) {
        this.velocityX = velocityX;
    }

    public void setVelocityY(float velocityY) {
        this.velocityY = velocityY;
    }

    public float getMass() {
        return mass;
    }

    public void setMass(float mass) {
        this.mass = mass;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
    public float isInputLockTime() {
        return inputLockTimer;
    }

    public void setInputLockTimer(float inputLockTimer) {
        this.inputLockTimer = inputLockTimer;
    }

    public float getInputLockTimer() {
        return inputLockTimer;
    }

    public float getInputLockDuration() {
        return inputLockDuration;
    }

    public void setInputLockDuration(float inputLockDuration) {
        this.inputLockDuration = inputLockDuration;
    }
    
}
