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
import com.engine.world.AnimatedSprite;
import com.engine.world.Animation;
import com.engine.world.Sprite;
import com.engine.world.SpriteSheet;
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
    private long lastTime;
    AnimatedSprite player;

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
        // VSync OFF
        GLFW.glfwSwapInterval(0);
    }

    public void iniciarLoop() {

        lastTime = System.nanoTime();

        while (!GLFW.glfwWindowShouldClose(window)) {

            long currentTime = System.nanoTime();

            float deltaTime = (currentTime - lastTime) / 1_000_000_000f;

            lastTime = currentTime;

            fpsCounter.update();

            // update para animaciones ira por aca en un futuro y las fisicas etc
            player.update(deltaTime);

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            renderer.draw(
                    tileMap, tileRegistry, listaSprites);
            camera.setPosition(
                    camera.getX(),
                    camera.getY());
            GLFW.glfwSwapBuffers(window);
            GLFW.glfwPollEvents();
        }
    }

    public void inicializarRecursos() {

        fpsCounter = new FpsCounter();

        incializarGraficos();
        inicializarMundo();
        inicializarSprites();

    }

    public void inicializarSprites() {
        Texture cat = new Texture(
                "/textures/Animal/Cat 01-1.png");

        SpriteSheet playerSheet=new SpriteSheet(cat, 32, 32);
        TextureRegion[] frames = { playerSheet.getFrame(0 ,0),playerSheet.getFrame(1 , 0),playerSheet.getFrame(2 , 0) };
        Animation animation=new Animation(frames, 0.5f);
        
        player = new AnimatedSprite(
                animation,
                100,
                100);

        listaSprites = new ArrayList<>();
        listaSprites.add(player);

    }

    public void inicializarMundo() {

        Texture texture = new Texture(
                "/textures/tileset.png");

        tileRegistry = new TileRegistry();

        Tileset tileset = new Tileset(texture, 32, 32);
        tileRegistry.register(0, tileset.getTile(0, 3));
        tileRegistry.register(1, tileset.getTile(1, 0));
        tileRegistry.register(2, tileset.getTile(2, 0));
        tileRegistry.register(3, tileset.getTile(10, 0));

        tileMap = new TileMap();

    }

    public void incializarGraficos() {
        Shader shader = new Shader(
                "/shaders/vertex.glsl",
                "/shaders/fragment.glsl");

        // activamos el shader para configurar el uniform

        shader.use();
        shader.setUniform("textureSampler", 0);

        camera = new Camera(0, 0, 800, 600);
        renderer = new Renderer(shader, 800, 600, camera);
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