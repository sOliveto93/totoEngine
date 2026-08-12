package com.engine.graphics;


public class Quad extends Mesh{

    public Quad() {
super(
        new float[]  {
                -0.5f, 0.5f, 0.0f, 0.0f,
                0.5f, 0.5f, 1.0f, 0.0f,
                0.5f, -0.5f, 1.0f, 1.0f,
                -0.5f, -0.5f, 0.0f, 1.0f
        },

        new int[]  {
                0, 1, 2,
                2, 3, 0
        }
    );
    }

      
}
