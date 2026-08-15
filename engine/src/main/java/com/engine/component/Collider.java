package com.engine.component;

public class Collider implements Component {

    private float width;
    private float height;

    //reutilizamos el x ,y del transform
    private float offsetX;
    private float offsetY;

    private boolean trigger;

    public Collider(float width, float height,boolean trigger) {
        this.width = width;
        this.height = height;
        this.trigger=trigger;
    }

    public Collider(float width, float height, float offsetX, float offsetY,boolean trigger) {
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.trigger=trigger;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public float getOffsetX() {
        return offsetX;
    }

    public float getOffsetY() {
        return offsetY;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public void setOffsetX(float offsetX) {
        this.offsetX = offsetX;
    }

    public void setOffsetY(float offsetY) {
        this.offsetY = offsetY;
    }

    public boolean isTrigger() {
        return trigger;
    }

    public void setTrigger(boolean trigger) {
        this.trigger = trigger;
    }
    
}
