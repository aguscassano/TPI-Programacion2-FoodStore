/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpi_foodstore.entities;

import tpi_foodstore.enums.Rol;
import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 *
 * @author cassa
 */
public class Usuario extends Base{
    private String nombre;
    private String apellido;
    private String mail;
    private String celular;
    private String contrasenia;
    private Rol rol;
    private ArrayList<Pedido> pedidos = new ArrayList <>();

    public Usuario(String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol, Long id, boolean eliminado, LocalDateTime createdAt) {
        super(id, eliminado, createdAt);
        this.nombre = nombre;
        this.apellido = apellido;
        this.mail = mail;
        this.celular = celular;
        this.contrasenia = contrasenia;
        this.rol = rol;
    }
    
    public Usuario(Long id, String nombre, String apellido, String mail, String celular, String contrasenia, Rol rol) {
    super(id);
    this.nombre = nombre;
    this.apellido = apellido;
    this.mail = mail;
    this.celular = celular;
    this.contrasenia = contrasenia;
    this.rol = rol;
    }

    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public ArrayList<Pedido> getPedidos() {
        return pedidos;
    }

    public void setPedidos(ArrayList<Pedido> pedidos) {
        this.pedidos = pedidos;
    }
    
    public void agregarPedido(Pedido pedido){
        pedidos.add(pedido);
        pedido.setUsuario(this);
    }

    @Override
    public String toString() {
        String reporte = "====================================================================\n";
        reporte = reporte + String.format("USUARIO: %s %s | Mail: %s | Rol: %s\n", 
            this.getNombre(), this.getApellido(), this.getMail(), this.getRol());
        reporte = reporte + "========================\n";
        double totalAcumulado = 0;
        for (Pedido pedido : pedidos){
            reporte = reporte + pedido.toString();
            totalAcumulado = totalAcumulado + pedido.getTotal();
        }
        reporte = reporte + String.format("TOTAL ACUMULADO del usuario: $%.2f\n", totalAcumulado);
        reporte = reporte + "====================================================================\n";
        return reporte;
    }
    
    
}
