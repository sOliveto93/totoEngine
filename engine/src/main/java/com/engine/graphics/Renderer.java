package com.engine.graphics;

import java.util.List;

import com.engine.world.Sprite;
import com.engine.world.Tile;
import com.engine.world.TileMap;
import com.engine.world.TileRegistry;

public class Renderer {

        private Shader shader;

        private BatchRenderer batch;

        private Camera camera;

        private int screenWidth;
        private int screenHeight;

        public Renderer(Shader shader, int screenWidth,
                        int screenHeight, Camera camera) {
                this.shader = shader;

                this.screenWidth = screenWidth;
                this.screenHeight = screenHeight;

                this.camera = camera;

                this.batch = new BatchRenderer();

        }

        public void draw(TileMap tileMap, TileRegistry registry, List<Sprite> lista) {

                batch.clear();

                Tile firstTile = registry.get(tileMap.getId(0, 0));
                int tileWidth = firstTile.getRegion().getWidth();
                int tileHeight = firstTile.getRegion().getHeight();

                int startX = (int) (camera.getX() / tileWidth);
                int startY = (int) (camera.getY() / tileHeight);
                int endX = (int) Math.ceil((camera.getX() + screenWidth) / tileWidth);
                int endY = (int) Math.ceil((camera.getY() + screenHeight) / tileHeight);

                startX = Math.max(0, startX);
                startY = Math.max(0, startY);
                endX = Math.min(tileMap.getWidth(), endX);
                endY = Math.min(tileMap.getHeight(), endY);

                for (int y = startY; y < endY; y++) {
                        for (int x = startX; x < endX; x++) {

                                int id = tileMap.getId(x, y);
                                Tile tile = registry.get(id);
                                TextureRegion region = tile.getRegion();
                                // esquina superior izquierda del tile
                                float pixelX = x * region.getWidth();

                                float pixelY = y * region.getHeight();

                                // camara
                                pixelX -= camera.getX();

                                pixelY -= camera.getY();

                                // opengl
                                float posX = (pixelX / screenWidth) * 2f - 1f;

                                float posY = 1f - (pixelY / screenHeight) * 2f;

                                float scaleX = (region.getWidth() / (float) screenWidth) * 2f;

                                float scaleY = (region.getHeight() / (float) screenHeight) * 2f;

                                batch.add(region, posX, posY, scaleX, scaleY);
                        }
                }
                if (!lista.isEmpty()) {
                        for (Sprite sprite : lista) {

                                TextureRegion region = sprite.getRegion();

                                float pixelX = sprite.getX() - camera.getX();
                                float pixelY = sprite.getY() - camera.getY();

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
}
