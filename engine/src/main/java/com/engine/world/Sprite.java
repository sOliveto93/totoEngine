package com.engine.world;

import com.engine.component.Component;
import com.engine.graphics.TextureRegion;

public class Sprite implements Component{
    
    //fuente grafica
    private TextureRegion regionTexture;


    public Sprite(TextureRegion regionTexture){
        this.regionTexture=regionTexture;
    }

    public TextureRegion getRegion() {
        return regionTexture;
    }

}
