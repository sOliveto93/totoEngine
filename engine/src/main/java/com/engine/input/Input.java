package com.engine.input;

import org.lwjgl.glfw.GLFW;

public class Input {

    private final boolean[] keys;

    public Input(){
        keys = new boolean[GLFW.GLFW_KEY_LAST +1];    
    }

    public void setKey(int key,boolean pressed){
        keys[key]=pressed;
    }
    public boolean isKeyDown(int key){
        return keys[key];
    }
}
