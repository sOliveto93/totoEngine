# totoEngine

Motor de videojuegos 2D desarrollado desde cero en **Java y LWJGL**, utilizando **OpenGL** para el renderizado.

Estoy construyendo este motor desde cero para entender qué pasa realmente detrás de un motor 2D. La idea es aprender haciendo: renderizado con OpenGL, manejo de texturas, batching, mapas, entidades, componentes y sistemas.


## Objetivo

No estoy intentando reemplazar Unity ni hacer un engine gigante. La idea es poder usarlo para distintos tipos de juegos 2D: Zelda, Pokémon, Stardew Valley, plataformas, etc., y al mismo tiempo entender cómo funciona todo por debajo.



## Estado del proyecto

**Versión actual: v0.3**

Ya tengo funcionando el pipeline básico de renderizado, mapas por capas, texturas, sprites y animaciones. Ahora estoy empezando a pasar la arquitectura a Entity + Component + System**.

El proyecto continúa en desarrollo y actualmente se encuentra enfocado en construir una base general para juegos 2D.

## Características actuales

* Renderizado 2D mediante OpenGL
* Gestión de VAO, VBO y EBO
* Shaders de vértices y fragmentos
* Gestión de texturas
* `TextureRegion` para trabajar con regiones de una textura
* `TextureManager` para reutilizar texturas cargadas
* `Tileset`
* `TileRegistry`
* Carga de registros de tiles desde archivos
* Tilemaps
* Mapas compuestos por múltiples layers
* `MapLoader`
* Sprites
* Animaciones mediante frames
* `AnimationSystem`
* Cámara 2D
* Culling básico de tiles fuera de la pantalla
* Batch rendering
* Batching por textura
* Sistema de `flush()` para controlar el tamaño de los batches
* Geometría reutilizable mediante `Quad`
* Mesh persistente entre frames
* Soporte para múltiples texturas dentro del mismo renderizado
* Sistema de entidades
* Sistema de componentes
* `Transform` como componente
* Componentes almacenados por tipo
* Sistemas independientes de las entidades


### Evolución de la arquitectura

Al principio tenía clases como Player y AnimatedSprite. Mientras el motor crecía me di cuenta de que esto me iba a llevar a crear una clase para cada tipo de objeto. Por eso cambié el diseño a Entity + Component + System.

Actualmente una entidad puede construirse combinando componentes como
`Transform`, `Sprite` y `Animation`. Esto permite que el mismo sistema pueda
utilizarse para jugadores, NPCs, enemigos, objetos u otras entidades sin
necesitar una clase específica para cada tipo.

## Arquitectura de entidades
La idea es que los componentes tengan los datos y que los sistemas hagan el
trabajo.

Por ejemplo, `AnimationSystem` busca entidades que tengan `Animation` y
actualiza sus frames.

Una entidad puede estar formada por diferentes componentes:

```text
Entity
   │
   ├── Transform
   ├── Sprite
   └── Animation
          │
          ▼
   AnimationSystem
```

Esta arquitectura permitirá incorporar posteriormente sistemas de física, colisiones, input y otros comportamientos sin acoplarlos directamente al renderer.

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

Los mapas se cargan mediante MapLoader desde archivos externos, así el mapa no queda metido directamente en el código.

## Gestión de recursos

El motor utiliza un `TextureManager` para evitar cargar repetidamente la misma textura.

Los tiles se definen mediante un registro externo:

```text
GRASS=1,/textures/terrain.png,0,0
WATER=2,/textures/terrain.png,0,7
WALL=3,/textures/terrain.png,0,14

TREE=4,/textures/decorations/decorations.png,10,0
FLOWER=5,/textures/decorations/decorations.png,11,0
```

De esta forma, el mapa trabaja con IDs mientras que el `TileRegistry` se encarga de asociar cada ID con su correspondiente `Tile`.

## Pipeline de renderizado

El flujo actual del motor es aproximadamente:

```text
Entity
 ├── Transform
 ├── Sprite
 └── Animation
        │
        ▼
   TextureRegion

Tile
   │
   ▼
TextureRegion

        ↓
     Renderer
        ↓
   BatchRenderer
        ↓
     Quad / Mesh
        ↓
   VAO / VBO / EBO
        ↓
      Shader
        ↓
     OpenGL
        ↓
       GPU
```

El `BatchRenderer` permite agrupar múltiples objetos antes de enviarlos a la GPU, realizando un `flush()` cuando cambia la textura utilizada o cuando se alcanza el tamaño máximo del batch.

## Tecnologías

* **Java**
* **LWJGL**
* **OpenGL**
* **GLFW**
* **Maven**


## Próximos pasos

Entre las próximas funcionalidades se encuentran:

* Sistema de física
* Sistema de colisiones
* Componentes y sistemas adicionales
* Mejoras en el sistema de cámara
* Input
* Gestión de escenas
* Gestión de ciclo de vida de entidades
* Culling y particionado espacial
* Chunks para mundos grandes
* Mejoras de rendimiento
* Audio
* Herramientas para desarrollo de juegos
* Integración con herramientas externas de edición de mapas
* Soporte para diferentes formatos de mapas

  ```text
  TMX ──────┐
  JSON ─────┤
  TXT ──────┼──► MapLoader ──► Map
  Otro ─────┘
  ```

## Licencia

Si llegaste hasta aca felicitaciones ?) es de libre uso.
