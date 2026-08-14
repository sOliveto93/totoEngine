package com.engine;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;

import com.engine.component.Collider;
import com.engine.component.InputController;
import com.engine.component.Transform;
import com.engine.component.PhysicsBody;
import com.engine.component.Tag;
import com.engine.debug.FpsCounter;
import com.engine.entity.Entity;
import com.engine.event.EventBus;
import com.engine.graphics.Camera;
import com.engine.graphics.Renderer;
import com.engine.graphics.Shader;
import com.engine.graphics.Texture;
import com.engine.graphics.TextureRegion;
import com.engine.input.Input;
import com.engine.system.AnimationSystem;
import com.engine.system.CollisionResponseSystem;
import com.engine.system.CollisionSystem;
import com.engine.system.InputSystem;
import com.engine.system.MovementSystem;
import com.engine.system.PhysicsSystem;
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
    private AnimationSystem animationSystem;
    private InputSystem inputSystem;
    private MovementSystem movementSystem;
    private CollisionSystem collisionSystem;
    private Input input;
    private EventBus eventBus;
    private CollisionResponseSystem collisionResponseSystem;
    private PhysicsSystem physicsSystem;

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
            /*--------------------------------------------- */

            inputSystem.update(world);
            physicsSystem.update(world,deltaTime);
            movementSystem.update(world, deltaTime);
            collisionSystem.update(world);
            animationSystem.update(world, deltaTime);

            /*--------------------------------------------- */
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

        inputSystem = new InputSystem(input);
        eventBus = new EventBus();

        collisionSystem = new CollisionSystem(eventBus);
        collisionResponseSystem = new CollisionResponseSystem(eventBus);
        movementSystem = new MovementSystem(collisionSystem);

        animationSystem = new AnimationSystem();
        physicsSystem= new PhysicsSystem();
        createGraphics();

        loadResources();

        createMap();

        createPlayer();

        createEnemy();

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
        PhysicsBody body = new PhysicsBody(1.0f,100.0f);
        Collider collider = new Collider(32, 32, 0, 0);
        Tag playerComponent=new Tag("player");
        InputController inputController=new InputController();
        Entity entity = new Entity();

        entity.addComponent(sprite);
        entity.addComponent(transform);
        entity.addComponent(animation);
        entity.addComponent(body);
        entity.addComponent(collider);
        entity.addComponent(playerComponent);
entity.addComponent(inputController);
        entities.add(entity);

    }

    public void createEnemy() {
        Texture cat = new Texture(
                "/sprites/animal/Cat 01-2.png");

        SpriteSheet playerSheet = new SpriteSheet(cat, 32, 32);
        TextureRegion[] frames = { playerSheet.getFrame(0, 0), playerSheet.getFrame(1, 0), playerSheet.getFrame(2, 0) };
        Animation animation = new Animation(frames, 0.5f);
        Transform transform = new Transform(200, 200);
        Sprite sprite = new Sprite(frames[0]);
        Collider collider = new Collider(32, 32, 0, 0);
        Tag tag = new Tag("enemigo");
        
        Entity entity = new Entity();

        entity.addComponent(sprite);
        entity.addComponent(transform);
        entity.addComponent(animation);
        entity.addComponent(collider);
        entity.addComponent(tag);
        

        entities.add(entity);
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