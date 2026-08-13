package com.engine.graphics;

import java.util.List;

import com.engine.component.Transform;
import com.engine.entity.Entity;
import com.engine.world.Animation;
import com.engine.world.Map;
import com.engine.world.World;
import com.engine.world.Sprite;
import com.engine.world.Tile;
import com.engine.world.TileLayer;
import com.engine.world.TileRegistry;

public class Renderer {

        private Shader shader;

        private BatchRenderer batch;

        private Camera camera;

        private int screenWidth;
        private int screenHeight;
        private TileRegistry tileRegistry;

        public Renderer(Shader shader, int screenWidth,
                        int screenHeight, Camera camera, TileRegistry registry) {
                this.shader = shader;

                this.screenWidth = screenWidth;
                this.screenHeight = screenHeight;

                this.camera = camera;
                this.tileRegistry = registry;
                this.batch = new BatchRenderer();

        }

        public void draw(World gameWorld) {

                Map map = gameWorld.getMap();
                TileLayer terrain = map.getTerrain();
                List<Entity> entities = gameWorld.getEntities();

                batch.clear();

                // Culling basado en el tamaño del tile
                Tile firstTile = tileRegistry.get(terrain.getId(0, 0));

                int tileWidth = firstTile.getRegion().getWidth();
                int tileHeight = firstTile.getRegion().getHeight();

                int startX = (int) (camera.getX() / tileWidth);
                int startY = (int) (camera.getY() / tileHeight);

                int endX = (int) Math.ceil(
                                (camera.getX() + camera.getWidth()) / tileWidth);

                int endY = (int) Math.ceil(
                                (camera.getY() + camera.getHeight()) / tileHeight);

                startX = Math.max(0, startX);
                startY = Math.max(0, startY);

                endX = Math.min(terrain.getWidth(), endX);
                endY = Math.min(terrain.getHeight(), endY);

                // Capas
                drawLayer(
                                map.getTerrain(),
                                startX,
                                startY,
                                endX,
                                endY);

                drawLayer(
                                map.getBuildings(),
                                startX,
                                startY,
                                endX,
                                endY);

                drawLayer(
                                map.getDecoration(),
                                startX,
                                startY,
                                endX,
                                endY);

                drawLayer(
                                map.getForeground(),
                                startX,
                                startY,
                                endX,
                                endY);

                // Sprites
                if (!entities.isEmpty()) {

                        for (Entity entity : entities) {

                                Sprite sprite = entity.getComponent(Sprite.class);

                                TextureRegion region = sprite.getRegion();
                                
                                Transform transform = entity.getComponent(Transform.class);

                                if (sprite == null || transform == null) {
                                        continue;
                                }

                                Animation animation = entity.getComponent(Animation.class);

                                

                                if (animation != null) {
                                        region = animation.getCurrentFrame();
                                }

                                float pixelX = transform.getX() - camera.getX();
                                float pixelY = transform.getY() - camera.getY();

                                float posX = (pixelX / screenWidth) * 2f - 1f;

                                float posY = 1f - (pixelY / screenHeight) * 2f;

                                float scaleX = (region.getWidth() / (float) screenWidth) * 2f;

                                float scaleY = (region.getHeight() / (float) screenHeight) * 2f;

                                batch.add(
                                                region,
                                                posX,
                                                posY,
                                                scaleX,
                                                scaleY);
                        }
                }

                shader.use();

                batch.flush();
        }

        private void drawLayer(
                        TileLayer layer,
                        int startX,
                        int startY,
                        int endX,
                        int endY) {

                for (int y = startY; y < endY; y++) {

                        for (int x = startX; x < endX; x++) {

                                int id = layer.getId(x, y);

                                Tile tile = tileRegistry.get(id);

                                if (tile == null) {
                                        continue;
                                }

                                drawTile(tile, x, y);
                        }
                }
        }

        private void drawTile(Tile tile, int x, int y) {

                TextureRegion region = tile.getRegion();

                float pixelX = x * region.getWidth() - camera.getX();

                float pixelY = y * region.getHeight() - camera.getY();

                float posX = (pixelX / screenWidth) * 2f - 1f;

                float posY = 1f - (pixelY / screenHeight) * 2f;

                float scaleX = (region.getWidth() / (float) screenWidth) * 2f;

                float scaleY = (region.getHeight() / (float) screenHeight) * 2f;

                batch.add(
                                region,
                                posX,
                                posY,
                                scaleX,
                                scaleY);
        }
}
