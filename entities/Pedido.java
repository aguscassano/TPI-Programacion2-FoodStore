/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpi_foodstore.entities;

import tpi_foodstore.enums.FormaPago;
import tpi_foodstore.enums.Estado;
import tpi_foodstore.interfaces.Calculable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author cassa
 */
public class Pedido extends Base implements Calculable{
    private static Long generadorIdDetalle = 1L;
    private LocalDate fecha;
    private Estado estado;
    private double total;
    private FormaPago formaPago;
    private ArrayList <DetallePedido> detalles = new ArrayList<>();
    private Usuario usuario;

    public Pedido(LocalDate fecha, Estado estado, FormaPago formaPago, Usuario usuario, Long id) {
        super(id);
        this.fecha = fecha;
        this.estado = estado;
        this.formaPago = formaPago;
        this.usuario = usuario;
        this.total = 0.0; 
    }
    
    public Pedido(Long id, LocalDate fecha, Estado estado, FormaPago formaPago, Usuario usuario) {
    super(id);
    this.fecha = fecha;
    this.estado = estado;
    this.formaPago = formaPago;
    this.usuario = usuario;
    this.total = 0.0; 
    }

    

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public ArrayList<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(ArrayList<DetallePedido> detalles) {
        this.detalles = detalles;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    
    public void addDetallePedido(int cantidad, Producto producto) {
        DetallePedido nuevoDetalle = new DetallePedido(generadorIdDetalle++, cantidad, producto);
        this.detalles.add(nuevoDetalle);
        this.calcularTotal();
    }
    
    public DetallePedido findDetallePedidoByProducto(Producto producto){
        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto().getId().equals(producto.getId())){
                return detalle;
            }
        }
        return null;
    }
    
    public void deleteDetallePedidoByProducto(Producto producto){
        DetallePedido detalleEncontrado = this.findDetallePedidoByProducto(producto);
        if (detalleEncontrado != null){
            this.detalles.remove(detalleEncontrado);
            this.calcularTotal();
        }
    }
    
    
    
    @Override
    public void calcularTotal(){
        double suma = 0;
        for (DetallePedido detalle : detalles) {
            suma = suma + detalle.getSubtotal();
        }
        this.setTotal(suma);
    }

    @Override
    public String toString() {
        String reporte = String.format("> Pedido #%d | Fecha: %s | Estado: %s | FormaPago: %s\n", 
            this.getId(), this.fecha, this.getEstado(), this.getFormaPago());
        for (DetallePedido detalle : detalles){
            reporte = reporte + detalle.toString() + "\n";
        }
        reporte = reporte + String.format(" TOTAL DEL PEDIDO: $%.2f\n", this.total);
        return reporte;
    }

    
    
}
