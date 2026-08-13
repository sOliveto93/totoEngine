package com.engine.system;

import org.lwjgl.glfw.GLFW;

import com.engine.component.Velocity;
import com.engine.entity.Entity;
import com.engine.input.Controller;
import com.engine.input.Input;

public class InputSystem {

    private Input input;
    private Controller controller;

    public InputSystem(Input input, Controller controller) {
        this.input = input;
        this.controller = controller;
    }

    public void update() {

        Entity entity = controller.getControlledEntity();

        if (entity == null) {
            return;
        }

        Velocity velocity = entity.getComponent(Velocity.class);

        if (velocity == null) {
            return;
        }
        //reset
        velocity.setX(0);
        velocity.setY(0);


        if (input.isKeyDown(GLFW.GLFW_KEY_W)) {
            velocity.setY(-velocity.getSpeed());
        }

        if (input.isKeyDown(GLFW.GLFW_KEY_S)) {
            velocity.setY(velocity.getSpeed());
        }

        if (input.isKeyDown(GLFW.GLFW_KEY_A)) {
            velocity.setX(-velocity.getSpeed());
        }

        if (input.isKeyDown(GLFW.GLFW_KEY_D)) {
            velocity.setX(velocity.getSpeed());
        }

    }

}
