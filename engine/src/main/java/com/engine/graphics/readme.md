# totoEngine

Motor de videojuegos 2D desarrollado desde cero en **Java y LWJGL**, utilizando **OpenGL** para el renderizado.

El proyecto nace como una implementación experimental para comprender cómo funciona internamente un motor 2D: desde la gestión de texturas y geometría hasta el envío de batches a la GPU.

## Estado del proyecto

**Versión actual: v0.1.0**

El pipeline básico de renderizado 2D ya se encuentra funcionando.

## Características actuales

* Renderizado 2D mediante OpenGL
* Gestión de `VAO`, `VBO` y `EBO`
* Shaders de vértices y fragmentos
* Texturas
* `TextureRegion` para trabajar con regiones de una textura
* Tilesets
* Tilemaps
* Sprites
* Cámara 2D
* Culling básico de tiles fuera de la pantalla
* Batch rendering
* Batching por textura
* Sistema de `flush()` para controlar el tamaño de los batches
* Geometría reutilizable mediante `Quad`
* Mesh persistente entre frames
* Soporte para múltiples texturas dentro del mismo renderizado

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

* Animaciones de sprites
* Transformaciones
* Layers de renderizado
* Mejoras en el sistema de cámara
* Sistema de entidades
* Colisiones
* Gestión de escenas
* Input
* Mejoras de rendimiento
* Audio
* Más herramientas para desarrollo de juegos

## Licencia

Proyecto experimental y educativo. La licencia se definirá en una versión posterior.
