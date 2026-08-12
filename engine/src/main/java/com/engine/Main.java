package com.engine;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.engine.debug.FpsCounter;
import com.engine.graphics.Camera;
import com.engine.graphics.Renderer;
import com.engine.graphics.Shader;
import com.engine.graphics.Texture;
import com.engine.graphics.TextureRegion;
import com.engine.world.Sprite;
import com.engine.world.TileMap;
import com.engine.world.TileRegistry;
import com.engine.world.Tileset;

public class Main {
    private static long window;

    private Renderer renderer;
    private TileMap tileMap;
    private TileRegistry tileRegistry;
    Camera camera;
    List<Sprite> listaSprites;
    private FpsCounter fpsCounter;

    public void inicializarOpenGL() {

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("No se pudo inicializar GLFW");
        }

        window = GLFW.glfwCreateWindow(800, 600, "Game Engine", 0, 0);

        if (window == 0) {
            throw new IllegalStateException("No se pudo crear la ventana");
        }

        GLFW.glfwMakeContextCurrent(window);

        GL.createCapabilities();
        //VSync OFF
        GLFW.glfwSwapInterval(0);
    }

    public void iniciarLoop() {
        while (!GLFW.glfwWindowShouldClose(window)) {
            fpsCounter.update();
            
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            renderer.draw(
                    tileMap, tileRegistry,listaSprites);
            camera.setPosition(
                    camera.getX(),
                    camera.getY()+0.1f);
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
    }

    public void inicializarRecursos() {

        fpsCounter=new FpsCounter();


        Shader shader = new Shader(
                "/shaders/vertex.glsl",
                "/shaders/fragment.glsl");

        // activamos el shader para configurar el uniform

        shader.use();
        shader.setUniform("textureSampler", 0);

        Texture texture = new Texture(
                "/textures/tileset.png");

                 Texture texture2 = new Texture(
                "/textures/prueba.png");
        TextureRegion playerRegion = new TextureRegion(
                texture2,
                0,
                0,
                32,
                32);

        Sprite player = new Sprite(
                playerRegion,
                100,
                100);

        listaSprites = new ArrayList<>();
        listaSprites.add(player);
        tileRegistry = new TileRegistry();

        camera = new Camera(0, 0,800,600);
        renderer = new Renderer(shader, 800, 600, camera);

        Tileset tileset = new Tileset(texture, 32, 32);

        tileRegistry.register(0, tileset.getTile(0, 3));
        tileRegistry.register(1, tileset.getTile(1, 0));
        tileRegistry.register(2, tileset.getTile(2, 0));
        tileRegistry.register(3, tileset.getTile(10, 0));

        tileMap = new TileMap();

    }

    public void cerrar() {
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    public static void main(String[] args) {

        Main main = new Main();
        main.inicializarOpenGL();
        main.inicializarRecursos();

        main.iniciarLoop();
        main.cerrar();

    }
}