package com.engine.input;

import com.engine.entity.Entity;

public class Controller {

    private Entity controlledEntity;

    public Controller(){}
    
    public void setControlledEntity(Entity entity){
        this.controlledEntity=entity;
    }
    public Entity getControlledEntity(){
        return controlledEntity;
    }
}
