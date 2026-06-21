/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpi_foodstore.services;

import tpi_foodstore.entities.Categoria;
import tpi_foodstore.entities.Producto;
import tpi_foodstore.exceptions.EntidadNoEncontradaException;
import tpi_foodstore.exceptions.StockInvalidoException;
import tpi_foodstore.exceptions.ValidacionException;
import java.util.ArrayList;

/**
 *
 * @author cassa
 */
public class ProductoService {
    private ArrayList<Producto> productos;
    private Long generadorId;

    public ProductoService() {
        this.productos = new ArrayList<>();
        this.generadorId = 1L; 
    }

    public void listarProductos() {
        boolean hayRegistros = false;
        for (Producto p : productos) {
            if (!p.isEliminado()) {
               
                String nombreCategoria = (p.getCategoria() != null) ? p.getCategoria().getNombre() : "Sin Categoría";
                
                System.out.printf("ID: %d | Nombre: %s | Precio: $%.2f | Stock: %d | Categoría: %s\n", 
                                  p.getId(), p.getNombre(), p.getPrecio(), p.getStock(), nombreCategoria);
                hayRegistros = true;
            }
        }
        if (!hayRegistros) {
            System.out.println("No hay productos cargados en el catálogo.");
        }
    }

    public void crearProducto(String nombre, double precio, String descripcion, int stock, 
                              String imagen, boolean disponible, Categoria categoria) 
                              throws ValidacionException, StockInvalidoException {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new ValidacionException("Error: El nombre del producto no puede estar vacío.");
        }
        if (precio < 0) {
            throw new ValidacionException("Error: El precio no puede ser menor a 0.");
        }
        if (stock < 0) {
            throw new StockInvalidoException("Error: El stock inicial no puede ser negativo.");
        }
        if (categoria == null || categoria.isEliminado()) {
            throw new ValidacionException("Error: La categoría asignada no es válida o se encuentra eliminada.");
        }

        Producto nuevoProducto = new Producto(generadorId++, nombre, precio, descripcion, stock, imagen, disponible, categoria);
        productos.add(nuevoProducto);
        System.out.println("Producto creado con éxito. ID asignado: " + nuevoProducto.getId());
    }

    public Producto buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Producto p : productos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("Error: No se encontró un producto activo con el ID " + id);
    }

    public void editarProducto(Long id, String nuevoNombre, Double nuevoPrecio, String nuevaDescripcion, 
                               Integer nuevoStock, String nuevaImagen, Boolean nuevaDisponibilidad, Categoria nuevaCategoria) 
                               throws EntidadNoEncontradaException, ValidacionException, StockInvalidoException {
        
        Producto producto = buscarPorId(id);

        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            producto.setNombre(nuevoNombre);
        }
        if (nuevoPrecio != null) {
            if (nuevoPrecio < 0) throw new ValidacionException("Error: El precio no puede ser menor a 0.");
            producto.setPrecio(nuevoPrecio);
        }
        if (nuevaDescripcion != null && !nuevaDescripcion.trim().isEmpty()) {
            producto.setDescripcion(nuevaDescripcion);
        }
        if (nuevoStock != null) {
            if (nuevoStock < 0) throw new StockInvalidoException("Error: El stock no puede ser negativo.");
            producto.setStock(nuevoStock);
        }
        if (nuevaImagen != null && !nuevaImagen.trim().isEmpty()) {
            producto.setImagen(nuevaImagen);
        }
        if (nuevaDisponibilidad != null) {
            producto.setDisponible(nuevaDisponibilidad);
        }
        if (nuevaCategoria != null) {
            if (nuevaCategoria.isEliminado()) throw new ValidacionException("Error: La categoría asignada está eliminada.");
            producto.setCategoria(nuevaCategoria);
        }
        
        System.out.println("Producto actualizado correctamente.");
    }

    public void eliminarProducto(Long id) throws EntidadNoEncontradaException {
        Producto producto = buscarPorId(id);
        producto.setEliminado(true);
        System.out.println("Producto eliminado del catalogo");
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }
}