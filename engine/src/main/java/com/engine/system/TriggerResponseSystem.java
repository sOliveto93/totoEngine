package com.engine.system;

import com.engine.component.Tag;
import com.engine.entity.Entity;
import com.engine.event.EventBus;
import com.engine.event.TriggerEvent;

public class TriggerResponseSystem {
    public TriggerResponseSystem(EventBus eventBus) {
            
        eventBus.suscribe(event -> {
            if(!(event instanceof TriggerEvent triger)){
                return;
            }    
            Entity entityA=triger.getEntityA();
            Entity entityB=triger.getEntityB();

            
            System.out.println("se activo el triggerB ->"+entityB.getComponent(Tag.class).getValue()+"activado por el triggerA-> "+entityA.getComponent(Tag.class).getValue());

            });
    }
}
