/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpi_foodstore.services;

import tpi_foodstore.entities.DetallePedido;
import tpi_foodstore.entities.Pedido;
import tpi_foodstore.entities.Producto;
import tpi_foodstore.entities.Usuario;
import tpi_foodstore.enums.Estado;
import tpi_foodstore.enums.FormaPago;
import tpi_foodstore.exceptions.EntidadNoEncontradaException;
import tpi_foodstore.exceptions.StockInvalidoException;
import tpi_foodstore.exceptions.ValidacionException;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author cassa
 */
public class PedidoService {
    private ArrayList<Pedido> pedidos;
    private Long generadorId;

    public PedidoService() {
        this.pedidos = new ArrayList<>();
        this.generadorId = 1L; 
    }

    public void listarPedidos() {
        boolean hayRegistros = false;
        for (Pedido p : pedidos) {
            if (!p.isEliminado()) {
                System.out.printf("ID: %d | Usuario: %s %s | Estado: %s | Pago: %s | Total: $%.2f | Fecha: %s\n",
                        p.getId(), p.getUsuario().getNombre(), p.getUsuario().getApellido(),
                        p.getEstado(), p.getFormaPago(), p.getTotal(), p.getFecha());
                hayRegistros = true;
            }
        }
        if (!hayRegistros) {
            System.out.println("No hay pedidos registrados en el sistema.");
        }
    }

    public Pedido iniciarPedidoTemporal(Usuario usuario, FormaPago formaPago) throws ValidacionException {
        if (usuario == null || usuario.isEliminado()) {
            throw new ValidacionException("Error: El usuario seleccionado no es válido o está eliminado.");
        }

        return new Pedido(generadorId++, LocalDate.now(), Estado.PENDIENTE, formaPago, usuario);
    }

    public void agregarDetalleAPedido(Pedido pedidoTemporal, Producto producto, int cantidad) 
            throws ValidacionException, StockInvalidoException {
        
        if (producto == null || producto.isEliminado()) {
            throw new ValidacionException("Error: El producto seleccionado no está disponible.");
        }
        if (cantidad <= 0) {
            throw new ValidacionException("Error: La cantidad a pedir debe ser mayor a 0.");
        }
        if (producto.getStock() < cantidad) {
            throw new StockInvalidoException("Error: Stock insuficiente para '" + producto.getNombre() + 
                                             "'. Stock actual: " + producto.getStock());
        }

        producto.setStock(producto.getStock() - cantidad);

        pedidoTemporal.addDetallePedido(cantidad, producto);
    }

    public void confirmarPedido(Pedido pedidoTemporal) throws ValidacionException {
        if (pedidoTemporal.getDetalles().isEmpty()) {
            throw new ValidacionException("Error: No se puede registrar un pedido sin productos.");
        }

        this.pedidos.add(pedidoTemporal);
        pedidoTemporal.getUsuario().agregarPedido(pedidoTemporal); // Relación bidireccional
        
        System.out.println("Pedido #" + pedidoTemporal.getId() + " registrado con éxito. Total: $" + pedidoTemporal.getTotal());
    }

    public Pedido buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Pedido p : pedidos) {
            if (p.getId().equals(id) && !p.isEliminado()) {
                return p;
            }
        }
        throw new EntidadNoEncontradaException("Error: No se encontró un pedido activo con el ID " + id);
    }

    public void actualizarPedido(Long id, Estado nuevoEstado, FormaPago nuevaFormaPago) throws EntidadNoEncontradaException {
        Pedido pedido = buscarPorId(id);
        
        if (nuevoEstado != null) {
            pedido.setEstado(nuevoEstado);
        }
        if (nuevaFormaPago != null) {
            pedido.setFormaPago(nuevaFormaPago);
        }
        System.out.println("Estado y/o Forma de Pago del pedido actualizados correctamente.");
    }

    public void eliminarPedido(Long id) throws EntidadNoEncontradaException {
        Pedido pedido = buscarPorId(id);
        pedido.setEliminado(true); // Soft delete


        for (DetallePedido detalle : pedido.getDetalles()) {
            Producto prod = detalle.getProducto();
            prod.setStock(prod.getStock() + detalle.getCantidad());
        }

        System.out.println("Pedido eliminado. El stock de los productos ha sido restituido.");
    }
}