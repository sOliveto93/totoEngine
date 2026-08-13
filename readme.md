# totoEngine

Motor de videojuegos 2D desarrollado desde cero en **Java y LWJGL**, utilizando **OpenGL** para el renderizado.

El proyecto nace como una implementación experimental para comprender cómo funciona internamente un motor 2D: desde la gestión de texturas y geometría hasta el envío de batches a la GPU y la organización de entidades y sistemas.

## Estado del proyecto

**Versión actual: v0.3**

El pipeline básico de renderizado 2D se encuentra funcionando. El motor cuenta con un sistema básico de mapas por capas, gestión de recursos, sprites y animaciones, y una arquitectura inicial basada en **entidades, componentes y sistemas**.

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

Las clases específicas como `Player` y `AnimatedSprite` fueron eliminadas
para evitar acoplar el motor a tipos concretos de entidades.

Actualmente una entidad puede construirse combinando componentes como
`Transform`, `Sprite` y `Animation`. Esto permite que el mismo sistema pueda
utilizarse para jugadores, NPCs, enemigos, objetos u otras entidades sin
necesitar una clase específica para cada tipo.

## Arquitectura de entidades

El motor comenzó a evolucionar hacia una arquitectura basada en **Entity + Component + System**, buscando separar los datos de las entidades de la lógica que los procesa.

Una entidad puede estar formada por diferentes componentes:

```text
Entity
 ├── Transform
 ├── Sprite
 └── Animation
```

Los componentes representan características de una entidad, mientras que los sistemas procesan las entidades que poseen los componentes necesarios.

Por ejemplo:

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

El `AnimationSystem` se encarga de actualizar las animaciones sin que `Main` tenga que conocer los detalles internos de cada entidad.

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

Los mapas se cargan mediante `MapLoader` desde archivos externos, permitiendo separar los datos del mapa del código del motor.

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
Entity / Tile
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

La prioridad actual es construir una arquitectura suficientemente general para desarrollar diferentes tipos de juegos 2D, manteniendo el motor simple y entendible.

El proyecto se encuentra en desarrollo y muchas funcionalidades todavía están pendientes.

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

Proyecto experimental y educativo. La licencia se definirá en una versión posterior.
