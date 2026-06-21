/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpi_foodstore.services;

import tpi_foodstore.entities.Categoria;
import tpi_foodstore.exceptions.EntidadNoEncontradaException;
import tpi_foodstore.exceptions.ValidacionException;
import java.util.ArrayList;

/**
 *
 * @author cassa
 */
public class CategoriaService {
    private ArrayList<Categoria> categorias;
    private Long generadorId;

    public CategoriaService() {
        this.categorias = new ArrayList<>();
        this.generadorId = 1L; 
    }

    public void listarCategorias() {
        boolean hayRegistros = false;
        for (Categoria c : categorias) {
            if (!c.isEliminado()) {
                System.out.printf("ID: %d | Nombre: %s | Descripción: %s\n", 
                                  c.getId(), c.getNombre(), c.getDescripcion());
                hayRegistros = true;
            }
        }
        if (!hayRegistros) {
            System.out.println("No hay categorías cargadas.");
        }
    }

    public void crearCategoria(String nombre, String descripcion) throws ValidacionException {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("Error: El nombre de la categoría no puede estar vacío.");
        }
        
        for (Categoria c : categorias) {
            if (c.getNombre().equalsIgnoreCase(nombre) && !c.isEliminado()) {
                throw new ValidacionException("Error: Ya existe una categoría con el nombre '" + nombre + "'.");
            }
        }

        Categoria nuevaCategoria = new Categoria(generadorId++, nombre, descripcion);
        categorias.add(nuevaCategoria);
        System.out.println("Categoría creada con éxito. ID asignado: " + nuevaCategoria.getId());
    }

    public Categoria buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Categoria c : categorias) {
            if (c.getId().equals(id) && !c.isEliminado()) {
                return c;
            }
        }
        throw new EntidadNoEncontradaException("Error: No se encontró una categoría activa con el ID " + id);
    }

    public void editarCategoria(Long id, String nuevoNombre, String nuevaDescripcion) throws EntidadNoEncontradaException {
        Categoria categoria = buscarPorId(id); // Lanza excepción si no existe
        
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            categoria.setNombre(nuevoNombre);
        }
        if (nuevaDescripcion != null && !nuevaDescripcion.trim().isEmpty()) {
            categoria.setDescripcion(nuevaDescripcion);
        }
        System.out.println("Categoría actualizada correctamente.");
    }

    public void eliminarCategoria(Long id) throws EntidadNoEncontradaException {
        Categoria categoria = buscarPorId(id);
        categoria.setEliminado(true); // Soft delete
        System.out.println("Categoría eliminada del sistema.");
    }

    public ArrayList<Categoria> getCategorias() {
        return categorias;
    }
}