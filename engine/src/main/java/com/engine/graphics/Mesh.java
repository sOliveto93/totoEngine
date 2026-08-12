package com.engine.graphics;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class Mesh {
    private int vao;
    private int vbo;
    private int ebo;
    private int indiceCount;

    public Mesh(float[] vertices, int[] indices) {

        indiceCount = indices.length;

        crearVAO();
        crearVBO(vertices);
        crearEBO(indices);
        configurarAtributos();

    }

    private void crearEBO(int[] indices) {
        /* --------------- EBO ----------------- */

        ebo = GL15.glGenBuffers();

        GL15.glBindBuffer(
                GL15.GL_ELEMENT_ARRAY_BUFFER,
                ebo);

        GL15.glBufferData(
                GL15.GL_ELEMENT_ARRAY_BUFFER,
                indices,
                GL15.GL_STATIC_DRAW);
    }

    private void configurarAtributos() {
        /*---------- atributos posicion -------------------- */

        GL20.glVertexAttribPointer(0, 2, GL11.GL_FLOAT, false, 4 * Float.BYTES, 0);

        GL20.glEnableVertexAttribArray(0);

        /*---------- atributos UV -------------------- */

        GL20.glVertexAttribPointer(1, 2, GL11.GL_FLOAT, false, 4 * Float.BYTES, 2 * Float.BYTES);

        GL20.glEnableVertexAttribArray(1);
    }

    private void crearVBO(float[] vertices) {
        /* --------------- VBO ----------------- */

        vbo = GL15.glGenBuffers();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);

        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, vertices, GL15.GL_STATIC_DRAW);
    }

    private void crearVAO() {
        /* --------------- VAO ----------------- */

        vao = GL30.glGenVertexArrays();

        GL30.glBindVertexArray(vao);

    }

    public void draw() {
        GL30.glBindVertexArray(vao);

        GL11.glDrawElements(
                GL11.GL_TRIANGLES,
                indiceCount,
                GL11.GL_UNSIGNED_INT,
                0);
    }

    public void update(float[] vertices, int[] indices,int indiceCount) {
        GL15.glBindBuffer(
                GL15.GL_ARRAY_BUFFER,
                vbo);

        GL15.glBufferSubData(
                GL15.GL_ARRAY_BUFFER,
                0,
                vertices);

        GL15.glBindBuffer(
                GL15.GL_ELEMENT_ARRAY_BUFFER,
                ebo);

        GL15.glBufferSubData(
                GL15.GL_ELEMENT_ARRAY_BUFFER,
                0,
                indices);

        this.indiceCount = indiceCount;
    }
}
