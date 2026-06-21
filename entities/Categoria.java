/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpi_foodstore.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author cassa
 */
public class Categoria extends Base{
    private String nombre;
    private String descripcion;
    private ArrayList<Producto> productos = new ArrayList<>();

    public Categoria(Long id, boolean eliminado, LocalDateTime createdAt, String nombre, String descripcion) {
    super(id, eliminado, createdAt); 
    this.nombre = nombre;
    this.descripcion = descripcion;   
    }
    
    public Categoria(Long id, String nombre, String descripcion) {
    super(id);
    this.nombre = nombre;
    this.descripcion = descripcion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public ArrayList<Producto> getProductos() {
        return productos;
    }

    public void setProductos(ArrayList<Producto> productos) {
        this.productos = productos;
    }
    
    public void agregarProducto(Producto producto){
        productos.add(producto);
    }

    @Override
    public String toString() {
        return "Categoria{" + "nombre=" + nombre + ", descripcion=" + descripcion + ", productos=" + productos + '}';
    }
    
    
}
