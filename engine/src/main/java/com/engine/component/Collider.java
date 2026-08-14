package com.engine.component;

public class Collider implements Component {

    private float width;
    private float height;

    //reutilizamos el x ,y del transform
    private float offsetX;
    private float offsetY;

    public Collider(float width, float height) {
        this.width = width;
        this.height = height;
    }

    public Collider(float width, float height, float offsetX, float offsetY) {
        this.width = width;
        this.height = height;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
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
}
