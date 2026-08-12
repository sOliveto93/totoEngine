package com.engine.world;

import com.engine.graphics.Texture;
import com.engine.graphics.TextureRegion;

public class SpriteSheet {
private Texture texture;
    private int tileWidth;
    private int tileHeight;
public SpriteSheet(
            Texture texture,
            int tileWidth,
            int tileHeight) {

        this.texture = texture;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }
    public TextureRegion getFrame(int column, int row) {
        int x = column * tileWidth;
        int y = row * tileHeight;

        TextureRegion regionTexture = new TextureRegion(
                texture,
                x,
                y,
                tileWidth,
                tileHeight);

        return regionTexture;
    }

}
