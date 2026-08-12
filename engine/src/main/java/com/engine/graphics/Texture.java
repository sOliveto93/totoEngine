package com.engine.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import javax.imageio.ImageIO;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class Texture {

    private int id;
    private int width;
    private int height;

    public Texture(String path) {

        BufferedImage imagen = cargarImage(path);

        crearTextura(imagen);
    }

    private BufferedImage cargarImage(String path) {
        InputStream input = Texture.class.getResourceAsStream(path);

        if (input == null) {
            throw new IllegalStateException("No se pudo cargar la textura " + path);
        }

        try {
            return ImageIO.read(input);
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo la textura " + path, e);
        }

    }

    private void crearTextura(BufferedImage imagen) {
        width = imagen.getWidth();
        height = imagen.getHeight();

        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = imagen.getRGB(x, y);

                byte red = (byte) ((pixel >> 16) & 0xFF);
                byte green = (byte) ((pixel >> 8) & 0xFF);
                byte blue = (byte) (pixel & 0xFF);
                byte alpha = (byte) ((pixel >> 24) & 0xFF);

                buffer.put(red);
                buffer.put(green);
                buffer.put(blue);
                buffer.put(alpha);
            }
        }

        buffer.flip();

        id = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D,
                GL11.GL_TEXTURE_MIN_FILTER,
                GL11.GL_NEAREST);

        GL11.glTexParameteri(
                GL11.GL_TEXTURE_2D,
                GL11.GL_TEXTURE_MAG_FILTER,
                GL11.GL_NEAREST);

        GL11.glTexImage2D(
                GL11.GL_TEXTURE_2D,
                0,
                GL11.GL_RGBA,
                width,
                height,
                0,
                GL11.GL_RGBA,
                GL11.GL_UNSIGNED_BYTE,
                buffer);

    }

    public void bind() {
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
