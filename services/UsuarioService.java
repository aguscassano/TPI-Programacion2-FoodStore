/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tpi_foodstore.services;

import tpi_foodstore.entities.Usuario;
import tpi_foodstore.enums.Rol;
import tpi_foodstore.exceptions.EntidadNoEncontradaException;
import tpi_foodstore.exceptions.ValidacionException;
import java.util.ArrayList;

/**
 *
 * @author cassa
 */
public class UsuarioService {
    private ArrayList<Usuario> usuarios;
    private Long generadorId;

    public UsuarioService() {
        this.usuarios = new ArrayList<>();
        this.generadorId = 1L; 
    }

    // HU-USR-01 – Listar usuarios
    public void listarUsuarios() {
        boolean hayRegistros = false;
        for (Usuario u : usuarios) {
            if (!u.isEliminado()) {
                System.out.printf("ID: %d | Nombre: %s %s | Mail: %s | Rol: %s\n", 
                                  u.getId(), u.getNombre(), u.getApellido(), u.getMail(), u.getRol());
                hayRegistros = true;
            }
        }
        if (!hayRegistros) {
            System.out.println("No hay usuarios cargados en el sistema.");
        }
    }

    // HU-USR-02 – Crear usuario
    public void crearUsuario(String nombre, String apellido, String mail, String celular, 
                             String contrasenia, Rol rol) throws ValidacionException {
        
        if (mail == null || mail.trim().isEmpty()) {
            throw new ValidacionException("Error: El mail no puede estar vacío.");
        }
        
        // Validar unicidad del mail
        validarMailUnico(mail, null);

        Usuario nuevoUsuario = new Usuario(generadorId++, nombre, apellido, mail, celular, contrasenia, rol);
        usuarios.add(nuevoUsuario);
        System.out.println("Usuario creado con éxito. ID asignado: " + nuevoUsuario.getId());
    }

    // Método auxiliar interno para buscar por ID
    public Usuario buscarPorId(Long id) throws EntidadNoEncontradaException {
        for (Usuario u : usuarios) {
            if (u.getId().equals(id) && !u.isEliminado()) {
                return u;
            }
        }
        throw new EntidadNoEncontradaException("Error: No se encontró un usuario activo con el ID " + id);
    }

    // Método auxiliar para validar si el mail ya existe (ignora al propio usuario si se está editando)
    private void validarMailUnico(String mail, Long idUsuarioExcluido) throws ValidacionException {
        for (Usuario u : usuarios) {
            // Si no está eliminado y el mail coincide
            if (!u.isEliminado() && u.getMail().equalsIgnoreCase(mail)) {
                // Si estamos creando (idUsuarioExcluido es null) o editando (el ID es distinto al que editamos)
                if (idUsuarioExcluido == null || !u.getId().equals(idUsuarioExcluido)) {
                    throw new ValidacionException("Error: El mail '" + mail + "' ya se encuentra registrado.");
                }
            }
        }
    }

    // HU-USR-03 – Editar usuario
    public void editarUsuario(Long id, String nuevoNombre, String nuevoApellido, String nuevoMail, 
                              String nuevoCelular, String nuevaContrasenia, Rol nuevoRol) 
                              throws EntidadNoEncontradaException, ValidacionException {
        
        Usuario usuario = buscarPorId(id);

        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            usuario.setNombre(nuevoNombre);
        }
        if (nuevoApellido != null && !nuevoApellido.trim().isEmpty()) {
            usuario.setApellido(nuevoApellido);
        }
        if (nuevoMail != null && !nuevoMail.trim().isEmpty()) {
            // Validamos que el nuevo mail no lo esté usando otro usuario
            validarMailUnico(nuevoMail, id);
            usuario.setMail(nuevoMail);
        }
        if (nuevoCelular != null && !nuevoCelular.trim().isEmpty()) {
            usuario.setCelular(nuevoCelular);
        }
        if (nuevaContrasenia != null && !nuevaContrasenia.trim().isEmpty()) {
            usuario.setContrasenia(nuevaContrasenia);
        }
        if (nuevoRol != null) {
            usuario.setRol(nuevoRol);
        }
        
        System.out.println("Usuario actualizado correctamente.");
    }

    // HU-USR-04 – Eliminar usuario (baja lógica)
    public void eliminarUsuario(Long id) throws EntidadNoEncontradaException {
        Usuario usuario = buscarPorId(id);
        usuario.setEliminado(true); 
        System.out.println("Usuario eliminado del sistema.");
    }

    public ArrayList<Usuario> getUsuarios() {
        return usuarios;
    }
}
