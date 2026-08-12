package com.engine.world;

import com.engine.graphics.Texture;
import com.engine.graphics.TextureRegion;

public class Tileset {
    private Texture texture;
    private int tileWidth;
    private int tileHeight;

    public Tileset(
            Texture texture,
            int tileWidth,
            int tileHeight) {

        this.texture = texture;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public Tile getTile(int column, int row) {
        int x = column * tileWidth;
        int y = row * tileHeight;

        TextureRegion regionTexture = new TextureRegion(
                texture,
                x,
                y,
                tileWidth,
                tileHeight);

        return new Tile(regionTexture);
    }

}
