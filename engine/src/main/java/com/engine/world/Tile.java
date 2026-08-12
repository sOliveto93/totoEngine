package com.engine.world;

import com.engine.graphics.TextureRegion;

public class Tile {

    private TextureRegion regionTexture;


    public Tile(TextureRegion regionTexture) {

        this.regionTexture = regionTexture;
       
    }

    public TextureRegion getRegion() {
        return regionTexture;
    }

}
