package com.engine.world;

import java.util.HashMap;
import java.util.Map;

import com.engine.graphics.Texture;

public class TextureManager {

    private Map<String, Texture> textures = new HashMap<>();

    public Texture get(String path) {

        Texture texture = textures.get(path);

        if (texture == null) {
            texture = new Texture(path);
            textures.put(path, texture);
        }

        return texture;
    }
}
