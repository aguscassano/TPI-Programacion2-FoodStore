# Food Store - Sistema de Gestión de Pedidos

Trabajo Práctico Integrador desarrollado para la materia **Programación 2**. 
Se trata de una aplicación de consola en Java basada fuertemente en los pilares de la Programación Orientada a Objetos (POO) para gestionar de manera integral el flujo de un negocio de comidas.

##  Datos Académicos
- **Institución:** Universidad Tecnológica Nacional (UTN)
- **Carrera:** Tecnicatura en Programación
- **Materia:** Programación 2
- **Alumno:** Agustín Cassano

##  Entregables

-  **Video Demostrativo:** [https://youtu.be/06CR0Cj2TuE]
-  **Documentación Técnica (PDF):** [Incluido en la raíz del proyecto]
---

## Tecnologías y Herramientas Utilizadas
- **Lenguaje:** Java 21.
- **Paradigma:** Programación Orientada a Objetos (Herencia, Polimorfismo, Encapsulamiento, Abstracción).
- **Almacenamiento:** Volátil en memoria mediante Java Collections (`ArrayList`).
- **Control de Versiones:** Git y GitHub.
- **IDE Sugerido:** NetBeans / IntelliJ IDEA / Eclipse.

## Arquitectura y Estructura del Código
El proyecto está modularizado aplicando una separación clara de responsabilidades:
1. **Entities (Modelo de Dominio):** Entidades como `Usuario`, `Producto`, `Pedido` y `Categoria`, las cuales heredan de una clase `Base` abstracta para estandarizar identificadores y fechas de creación.
2. **Services (Lógica de Negocio):** Clases encargadas de gestionar las colecciones, validar reglas de negocio (como unicidad de correos o control de stock) y ejecutar operaciones CRUD con bajas lógicas (Soft Delete).
3. **Exceptions:** Manejo robusto de errores mediante excepciones propias (`EntidadNoEncontradaException`, `StockInvalidoException`, `ValidacionException`).
4. **Main (Interfaz de Consola):** Menú interactivo estructurado con sentencias `Switch` y bloques `Try-Catch` para garantizar un flujo ininterrumpido.

## Como ejecutar el proyecto
1. Clonar el repositorio en tu máquina local:
```bash
   git clone [https://github.com/aguscassano/TPI-Programacion2-FoodStore.git](https://github.com/aguscassano/TPI-Programacion2-FoodStore.git)
2. Abrir el proyecto desde tu IDE de preferencia (ej. NetBeans).
3. Navegar hasta la clase principal ubicada en src/tpi_foodstore/Main.java.
4. Compilar y ejecutar (Run File).
5. Interactuar con el sistema a través de la consola de salida.