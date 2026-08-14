package com.engine.component;

public class InputController implements Component {

    private float directionX;
    private float directionY;

    public float getDirectionX() {
        return directionX;
    }

    public float getDirectionY() {
        return directionY;
    }

    public void setDirectionX(float directionX) {
        this.directionX = directionX;
    }

    public void setDirectionY(float directionY) {
        this.directionY = directionY;
    }
}
