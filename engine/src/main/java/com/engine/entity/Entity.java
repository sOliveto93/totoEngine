package com.engine.entity;

import java.util.HashMap;
import java.util.Map;

import com.engine.component.Component;

public class Entity {
    private Map<Class<?>, Component> components = new HashMap<>();

    public Entity() {
    }

    public void addComponent(Component component) {
        components.put(component.getClass(), component);
    }

    public <T extends Component> T getComponent(Class<T> type) {
        return type.cast(components.get(type));
    }
    /*
     * public Component getComponent(Class<?> type) {
     * Component component = components.get(type);
     * 
     * if (component != null) {
     * return component;
     * }
     * 
     * for (Component candidate : components.values()) {
     * 
     * if (type.isAssignableFrom(candidate.getClass())) {
     * return candidate;
     * }
     * }
     * 
     * return null;
     * }
     */

    public boolean hasComponent(Class<?> type) {
        return components.containsKey(type);
    }

    public void removeComponent(Class<?> type) {
        components.remove(type);
    }
}
