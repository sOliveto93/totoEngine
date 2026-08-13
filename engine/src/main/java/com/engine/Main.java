package com.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.engine.component.Transform;
import com.engine.component.Velocity;
import com.engine.debug.FpsCounter;
import com.engine.entity.Entity;
import com.engine.graphics.Camera;
import com.engine.graphics.Renderer;
import com.engine.graphics.Shader;
import com.engine.graphics.Texture;
import com.engine.graphics.TextureRegion;
import com.engine.input.Controller;
import com.engine.input.Input;
import com.engine.system.AnimationSystem;
import com.engine.system.InputSystem;
import com.engine.system.MovementSystem;
import com.engine.world.Animation;
import com.engine.world.Map;
import com.engine.world.MapLoader;
import com.engine.world.World;
import com.engine.world.Sprite;
import com.engine.world.SpriteSheet;
import com.engine.world.TextureManager;
import com.engine.world.TileRegisterLoader;
import com.engine.world.TileRegistry;

public class Main {
    private static long window;

    private Renderer renderer;

    private Camera camera;

    private FpsCounter fpsCounter;
    private long lastTime;

    private World world;
    private Map map;
    private TileRegistry tileRegistry;
    private List<Entity> entities;
    private Shader shader;
    AnimationSystem animationSystem;
    InputSystem inputSystem;
    MovementSystem movementSystem;
    Controller controller;
    Input input;

    public void initOpenGL() {

        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("No se pudo inicializar GLFW");
        }

        window = GLFW.glfwCreateWindow(800, 600, "Game Engine", 0, 0);

        if (window == 0) {
            throw new IllegalStateException("No se pudo crear la ventana");
        }

        GLFW.glfwMakeContextCurrent(window);

        /* INPUT */

        input = new Input();

        GLFW.glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if (action == GLFW.GLFW_PRESS) {
                input.setKey(key, true);
            }
            if (action == GLFW.GLFW_RELEASE) {
                input.setKey(key, false);
            }
        });
        /*-------------------------------- */

        GL.createCapabilities();
        // VSync OFF
        GLFW.glfwSwapInterval(0);

        // Transparencias
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(
                GL11.GL_SRC_ALPHA,
                GL11.GL_ONE_MINUS_SRC_ALPHA);
    }

    public void run() {

        lastTime = System.nanoTime();

        while (!GLFW.glfwWindowShouldClose(window)) {

            long currentTime = System.nanoTime();

            float deltaTime = (currentTime - lastTime) / 1_000_000_000f;

            lastTime = currentTime;

            fpsCounter.update();

            GLFW.glfwPollEvents();

            // update para animaciones ira por aca en un futuro y las fisicas etc
            inputSystem.update();
            movementSystem.update(world, deltaTime);
            animationSystem.update(world, deltaTime);

            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            renderer.draw(
                    world);
            camera.setPosition(
                    camera.getX(),
                    camera.getY());
            GLFW.glfwSwapBuffers(window);
            
        }
    }

    public void createGame() {
        fpsCounter = new FpsCounter();

        entities = new ArrayList<>();

        controller=new Controller();

        inputSystem=new InputSystem(input,controller);

        movementSystem=new MovementSystem();

        animationSystem = new AnimationSystem();

        createGraphics();

        loadResources();

        createMap();

        createPlayer();

        renderer = new Renderer(
                shader,
                800,
                600,
                camera,
                tileRegistry);

        createWorld();
    }

    public void createWorld() {

        world = new World(
                map,
                entities);

    }

    public void createPlayer() {
        Texture cat = new Texture(
                "/sprites/animal/Cat 01-1.png");

        SpriteSheet playerSheet = new SpriteSheet(cat, 32, 32);
        TextureRegion[] frames = { playerSheet.getFrame(0, 0), playerSheet.getFrame(1, 0), playerSheet.getFrame(2, 0) };
        Animation animation = new Animation(frames, 0.5f);
        Transform transform = new Transform(100, 100);
        Sprite sprite = new Sprite(frames[0]);
        Velocity velocity=new Velocity(100.0f);
        Entity entity = new Entity();

        entity.addComponent(sprite);
        entity.addComponent(transform);
        entity.addComponent(animation);
        entity.addComponent(velocity);

        entities.add(entity);
        controller.setControlledEntity(entity); 
    }

    public void loadResources() {

        TextureManager textureManager = new TextureManager();

        TileRegisterLoader tileLoader = new TileRegisterLoader(textureManager);

        try {
            tileRegistry = tileLoader.load("/tilesRegister/tiles.txt");
        } catch (IOException e) {
            throw new RuntimeException(
                    "No se pudo cargar el registro de tiles", e);
        }

    }

    public void createMap() {
        MapLoader mapLoader = new MapLoader();
        try {
            map = mapLoader.load("/maps/map01.txt");
        } catch (IOException e) {
            throw new RuntimeException("No se pudo cargar el mapa", e);
        }

    }

    public void createGraphics() {
        shader = new Shader(
                "/shaders/vertex.glsl",
                "/shaders/fragment.glsl");

        // activamos el shader para configurar el uniform

        shader.use();
        shader.setUniform("textureSampler", 0);

        camera = new Camera(0, 0, 200, 600);

    }

    public void close() {
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }

    public static void main(String[] args) {

        Main main = new Main();
        main.initOpenGL();
        main.createGame();

        main.run();
        main.close();

    }
}