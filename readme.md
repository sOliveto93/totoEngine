# totoEngine

Motor de videojuegos 2D desarrollado desde cero en **Java y LWJGL**, utilizando **OpenGL** para el renderizado.

El proyecto nace como una implementación experimental para comprender cómo funciona internamente un motor 2D: desde la gestión de texturas y geometría hasta el envío de batches a la GPU.

## Estado del proyecto

Versión actual: v0.2

El pipeline básico de renderizado 2D se encuentra funcionando y el motor ya cuenta con un sistema básico de mapas por capas y carga de recursos.

## Características actuales

* Renderizado 2D mediante OpenGL
* Gestión de VAO, VBO y EBO
* Shaders de vértices y fragmentos
* Gestión de texturas
* TextureRegion para trabajar con regiones de una textura
* TextureManager para reutilizar texturas cargadas
* Tileset
* TileRegistry
* Carga de registros de tiles desde archivos
* Tilemaps
* Mapas compuestos por múltiples layers
* MapLoader
* Sprites
* Sprites animados
* Sistema básico de animaciones
* Cámara 2D
* Culling básico de tiles fuera de la pantalla
* Batch rendering
* Batching por textura
* Sistema de flush() para controlar el tamaño de los batches
* Geometría reutilizable mediante Quad
* Mesh persistente entre frames
* Soporte para múltiples texturas dentro del mismo renderizado


## Sistema de mapas

Los mapas pueden dividirse actualmente en diferentes capas de renderizado:
```text
Map
 ├── Terrain
 ├── Buildings
 ├── Decoration
 └── Foreground
```
Cada layer almacena los IDs de los tiles que lo componen.

Los mapas se cargan mediante MapLoader desde archivos externos, permitiendo separar los datos del mapa del código del motor.


## Gestión de recursos

El motor utiliza un TextureManager para evitar cargar repetidamente la misma textura.

Los tiles se definen mediante un registro externo:

GRASS=1,/textures/terrain.png,0,0
WATER=2,/textures/terrain.png,0,7
WALL=3,/textures/terrain.png,0,14

TREE=4,/textures/decorations/decorations.png,10,0
FLOWER=5,/textures/decorations/decorations.png,11,0

De esta forma, el mapa trabaja con IDs mientras que el TileRegistry se encarga de asociar cada ID con su correspondiente Tile.

## Pipeline de renderizado

El flujo actual del motor es aproximadamente:

```text
Tile / Sprite
      │
      ▼
TextureRegion
      │
      ▼
Renderer
      │
      ▼
BatchRenderer
      │
      ▼
Quad / Mesh
      │
      ▼
VAO / VBO / EBO
      │
      ▼
Shader
      │
      ▼
OpenGL
      │
      ▼
GPU
```

El `BatchRenderer` permite agrupar múltiples objetos antes de enviarlos a la GPU, realizando un `flush()` cuando cambia la textura utilizada o cuando se alcanza el tamaño máximo del batch.


## Tecnologías

* **Java**
* **LWJGL**
* **OpenGL**
* **GLFW**
* **Maven**

## Objetivo

El objetivo principal de `totoEngine` es construir progresivamente un motor 2D entendiendo cada una de sus partes, en lugar de depender de un framework de alto nivel.

El proyecto se encuentra en desarrollo y muchas funcionalidades todavía están pendientes.

## Próximos pasos

Entre las próximas funcionalidades se encuentran:

* Sistema de entidades
* Sistema de colisiones
* Mejoras en el sistema de cámara
* Transformaciones
* Gestión de escenas
* Input
* Mejoras de rendimiento
* Audio
* Herramientas para desarrollo de juegos
* Integración con herramientas externas de edición de mapas

## Licencia

Proyecto experimental y educativo. La licencia se definirá en una versión posterior.
