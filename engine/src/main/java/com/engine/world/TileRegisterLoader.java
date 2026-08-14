package com.engine.world;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import com.engine.graphics.Texture;

public class TileRegisterLoader {
    private TextureManager textureManager;
    private Map<String, Tileset> tilesets = new HashMap<>();

    public TileRegisterLoader(TextureManager textureManager) {
        this.textureManager = textureManager;
    }

    public TileRegistry load(String path) throws IOException {

        TileRegistry registry = new TileRegistry();

        List<String> lines;

        try (InputStream input = TileRegisterLoader.class.getResourceAsStream(path)) {

            if (input == null) {
                throw new IOException(
                        "No se encontró el registro: " + path);
            }

            lines = new BufferedReader(
                    new InputStreamReader(input))
                    .lines()
                    .toList();
        }

        for (String line : lines) {

            line = line.trim();

            if (line.isEmpty() || line.startsWith("#")) {
                continue;
            }

            String[] parts = line.split("=");

            String name = parts[0].trim();

            String[] data = parts[1].split(",");

            int id = Integer.parseInt(data[0].trim());

            String texturePath = data[1].trim();

            int x = Integer.parseInt(data[2].trim());
            int y = Integer.parseInt(data[3].trim());

            Tileset tileset = tilesets.get(texturePath);

            if (tileset == null) {

                Texture texture = textureManager.get(texturePath);

                tileset = new Tileset(texture, 32, 32);

                tilesets.put(texturePath, tileset);
            }

            Tile tile = tileset.getTile(x, y);

            registry.register(id, tile);
        }

        return registry;
    }
}
