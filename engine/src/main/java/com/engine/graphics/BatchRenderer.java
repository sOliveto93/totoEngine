package com.engine.graphics;



public class BatchRenderer {

    private static final int MAX_TILES = 1000;
    private static final int FLOATS_PER_TILE = 16;
    private static final int INDICES_PER_TILE = 6;

    private float[] vertices;
    private int[] indices;

    private int tileCount;
    
    private Texture currentTexture;
    // queremos que sea persistente entre frames
    private Mesh mesh;

    public BatchRenderer() {

        vertices = new float[MAX_TILES * FLOATS_PER_TILE];
        indices = new int[MAX_TILES * INDICES_PER_TILE];
        tileCount = 0;
        mesh = new Mesh(vertices, indices);
    }

    public void add(TextureRegion region, float x, float y, float width, float height) {

        Texture tileTexture=region.getTexture();

        if(currentTexture != null && currentTexture != tileTexture){
            flush();
        }
        

        if (tileCount >= MAX_TILES) {
            flush();
        }
        
        currentTexture = tileTexture;

        addVertices(region, x, y, width, height);
        addIndices();
        tileCount++;

    }

    private void addVertices(TextureRegion region, float x, float y, float width, float height) {
        int offset = tileCount * FLOATS_PER_TILE;

        float x0 = x;
        float y0 = y;

        float x1 = x + width;
        float y1 = y - height;

        float u0 = region.getUvMinX();
        float v0 = region.getUvMinY();

        float u1 = region.getUvMaxX();
        float v1 = region.getUvMaxY();

        vertices[offset + 0] = x0;
        vertices[offset + 1] = y0;
        vertices[offset + 2] = u0;
        vertices[offset + 3] = v0;

        vertices[offset + 4] = x1;
        vertices[offset + 5] = y0;
        vertices[offset + 6] = u1;
        vertices[offset + 7] = v0;

        vertices[offset + 8] = x1;
        vertices[offset + 9] = y1;
        vertices[offset + 10] = u1;
        vertices[offset + 11] = v1;

        vertices[offset + 12] = x0;
        vertices[offset + 13] = y1;
        vertices[offset + 14] = u0;
        vertices[offset + 15] = v1;
    }

    private void addIndices() {

        int vertexOffset = tileCount * 4;
        int indexOffset = tileCount * 6;

        indices[indexOffset + 0] = vertexOffset;
        indices[indexOffset + 1] = vertexOffset + 1;
        indices[indexOffset + 2] = vertexOffset + 2;

        indices[indexOffset + 3] = vertexOffset + 2;
        indices[indexOffset + 4] = vertexOffset + 3;
        indices[indexOffset + 5] = vertexOffset;
    }

    public void clear() {
        tileCount = 0;

    }

    public void flush() {

        if (tileCount == 0) {
            return;
        }

        currentTexture.bind();

        mesh.update(vertices, indices, tileCount * INDICES_PER_TILE);

        mesh.draw();

        tileCount = 0;
    }
}
