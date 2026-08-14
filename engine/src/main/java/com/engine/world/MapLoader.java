package com.engine.world;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MapLoader {

    public Map load(String path,int tileWidth,int tileHeight) throws IOException {

        List<String> lines;

        try (InputStream input = MapLoader.class.getResourceAsStream(path)) {

            if (input == null) {
                throw new IOException("No se encontró el mapa: " + path);
            }

            lines = new BufferedReader(
                    new InputStreamReader(input))
                    .lines()
                    .toList();
        }

        List<List<Integer>> terrain = new ArrayList<>();
        List<List<Integer>> buildings = new ArrayList<>();
        List<List<Integer>> decoration = new ArrayList<>();
        List<List<Integer>> foreground = new ArrayList<>();
        List<List<Integer>> collision = new ArrayList<>();

        List<List<Integer>> currentLayer = null;

        for (String line : lines) {

            line = line.trim();

            if (line.isEmpty()) {
                continue;
            }

            switch (line) {

                case "[TERRAIN]":
                    currentLayer = terrain;
                    break;

                case "[BUILDINGS]":
                    currentLayer = buildings;
                    break;

                case "[DECORATION]":
                    currentLayer = decoration;
                    break;

                case "[FOREGROUND]":
                    currentLayer = foreground;
                    break;

                case "[COLLISION]":
                    currentLayer = collision;
                    break;    

                default:
                    List<Integer> row = parseRow(line);
                    //verificamos diferencias internas del layer
                    if (!currentLayer.isEmpty() &&
                            row.size() != currentLayer.get(0).size()) {

                        throw new IOException(
                                "Una fila del layer tiene un ancho diferente");
                    }

                    currentLayer.add(row);

                    break;
                   
            }
        }

        TileLayer terrainLayer = new TileLayer(toArray(terrain));
        TileLayer buildingsLayer = new TileLayer(toArray(buildings));
        TileLayer decorationLayer = new TileLayer(toArray(decoration));
        TileLayer foregroundLayer = new TileLayer(toArray(foreground));
        TileLayer collisionLayer = new TileLayer(toArray(collision));

        int width = terrainLayer.getWidth();
        int height = terrainLayer.getHeight();

        validateLayer("BUILDINGS", buildingsLayer, width, height);
        validateLayer("DECORATION", decorationLayer, width, height);
        validateLayer("FOREGROUND", foregroundLayer, width, height);
        validateLayer("COLLISION", collisionLayer, width, height);
        return new Map(
                terrainLayer,
                buildingsLayer,
                decorationLayer,
                foregroundLayer,
                collisionLayer,tileWidth,tileHeight);
    }

    private List<Integer> parseRow(String line) {
//        para aceptar mas espacios "\\s+"
        String[] values = line.split(" ");

        List<Integer> row = new ArrayList<>();

        for (String value : values) {
            row.add(Integer.parseInt(value));
        }

        return row;
    }

    private int[][] toArray(List<List<Integer>> layer) {

        int[][] map = new int[layer.size()][];

        for (int y = 0; y < layer.size(); y++) {

            List<Integer> row = layer.get(y);

            map[y] = new int[row.size()];

            for (int x = 0; x < row.size(); x++) {
                map[y][x] = row.get(x);
            }
        }

        return map;

    }

    private void validateLayer(
            String name,
            TileLayer layer,
            int width,
            int height) throws IOException {

        if (layer.getWidth() != width || layer.getHeight() != height) {

            throw new IOException("El layer " + name + " tiene dimensiones diferentes al mapa");
        }
    }
}
