package com.engine.system;

import org.lwjgl.glfw.GLFW;

import com.engine.component.InputController;
import com.engine.entity.Entity;
import com.engine.input.Input;
import com.engine.world.World;

public class InputSystem {

    private Input input;

    public InputSystem(Input input) {
        this.input = input;
    }

    public void update(World world) {
        for (Entity entity : world.getEntities()) {

            InputController controller = entity.getComponent(InputController.class);

            if (controller == null) {
                continue;
            }

            controller.setDirectionX(0);
            controller.setDirectionY(0);
            
            if (input.isKeyDown(GLFW.GLFW_KEY_W)) {
                controller.setDirectionY(-1);
            }

            if (input.isKeyDown(GLFW.GLFW_KEY_S)) {
                controller.setDirectionY(1);
            }

            if (input.isKeyDown(GLFW.GLFW_KEY_A)) {
                controller.setDirectionX(-1);
            }

            if (input.isKeyDown(GLFW.GLFW_KEY_D)) {
                controller.setDirectionX(1);
            }

        }

    }

}
