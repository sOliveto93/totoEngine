package com.engine.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class EventBus {

    private List<Consumer<Event>> listeners = new ArrayList<>();

    public void suscribe(Consumer<Event> listener){
        listeners.add(listener);
    }

    public void publish(Event event){
        for(Consumer<Event> listener : listeners){
            listener.accept(event);
        }
    }

}
