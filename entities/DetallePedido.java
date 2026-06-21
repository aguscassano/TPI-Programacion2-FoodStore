/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpi_foodstore.entities;

import java.time.LocalDateTime;

/**
 *
 * @author cassa
 */
public class DetallePedido extends Base{
    private int cantidad;
    private double subtotal;
    private Producto producto;

    public DetallePedido(Long id, int cantidad, Producto producto) {
        super(id); 
        this.cantidad = cantidad;
        this.producto = producto;
        this.calcularSubtotal(); // Calcula al nacer
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        calcularSubtotal();
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        calcularSubtotal();
    }
    
    private double calcularSubtotal(){
        if (this.producto != null){
            this.subtotal = this.cantidad * this.producto.getPrecio();
        }else{
            this.subtotal = 0.0;
        }
        return subtotal;
    }

    @Override
    public String toString() {
        return String.format(" - DetallePedido #%d: %s x %d => Subtotal: $%.2f", 
            this.getId(), this.producto.getNombre(), this.cantidad, this.subtotal);
    }
    
}
