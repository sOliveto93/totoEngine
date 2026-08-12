package com.engine.graphics;

public class TextureRegion {

    private Texture texture;

    private int x;
    private int y;
    private int width;
    private int height;

    public TextureRegion(
            Texture texture,
            int x,
            int y,
            int width,
            int height) {

        this.texture = texture;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public float getUvMinX() {
        return (float) x / texture.getWidth();
    }

    public float getUvMinY() {
        return (float) y / texture.getHeight();
    }

    public float getUvMaxX() {
        return (float) (x + width) / texture.getWidth();
    }

    public float getUvMaxY() {
        return (float) (y + height) / texture.getHeight();
    }

    public Texture getTexture() {
        return texture;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
