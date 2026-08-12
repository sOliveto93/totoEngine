package com.engine.graphics;

import java.io.IOException;
import java.io.InputStream;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Shader {

    private int vertexShader;
    private int fragmentShader;
    private int shaderProgram;

    public Shader(String vertexPath, String fragmentPath) {

        String vertexSource = cargarSource(vertexPath);
        String fragmentSource = cargarSource(fragmentPath);

        vertexShader = compilarShader(GL20.GL_VERTEX_SHADER, vertexSource);
        fragmentShader = compilarShader(GL20.GL_FRAGMENT_SHADER, fragmentSource);

        shaderProgram = createProgram(vertexShader, fragmentShader);
    }

    private String cargarSource(String path) {
        InputStream input = Shader.class.getResourceAsStream(path);
        if (input == null) {
            throw new IllegalStateException("No se encontro el shader " + path);
        }
        try {
            String source = new String(input.readAllBytes());

            return source;
        } catch (IOException e) {
            throw new RuntimeException("Error leyendo vertex shader");
        }
    }

    private int compilarShader(int tipo, String source) {

        int shader = GL20.glCreateShader(tipo);

        GL20.glShaderSource(shader, source);

        GL20.glCompileShader(shader);

        int success = GL20.glGetShaderi(shader, GL20.GL_COMPILE_STATUS);

        if (success == GL20.GL_FALSE) {
            String log = GL20.glGetShaderInfoLog(shader);

            throw new IllegalStateException(
                    "Error compilando shader:\n" + log);
        }
        return shader;
    }

    private int createProgram(int vertexShader, int fragmentShader) {
        int program = GL20.glCreateProgram();

        GL20.glAttachShader(program, vertexShader);
        GL20.glAttachShader(program, fragmentShader);

        GL20.glLinkProgram(program);

        int success = GL20.glGetProgrami(program, GL20.GL_LINK_STATUS);

        if (success == GL11.GL_FALSE) {
            String log = GL20.glGetProgramInfoLog(program);

            throw new IllegalStateException(
                    "Error linkeando shader program:\n" + log);
        }

        GL20.glDeleteShader(vertexShader);
        GL20.glDeleteShader(fragmentShader);

        return program;
    }

    public void use() {
        GL20.glUseProgram(shaderProgram);
    }

    public void setUniform(String name, float x, float y) {
        int location = GL20.glGetUniformLocation(shaderProgram, name);

        GL20.glUniform2f(location, x, y);
    }

    public void setUniform(String nombre, boolean valor) {
        int location = GL20.glGetUniformLocation(shaderProgram, nombre);
        GL20.glUniform1i(location, valor ? 1 : 0);
    }
    public void setUniform(String nombre,int valor){
        int location=GL20.glGetUniformLocation(shaderProgram, nombre);
        GL20.glUniform1i(location, valor);
    }

}
