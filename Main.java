/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tpi_foodstore;

import tpi_foodstore.entities.Categoria;
import tpi_foodstore.entities.Pedido;
import tpi_foodstore.entities.Producto;
import tpi_foodstore.entities.Usuario;
import tpi_foodstore.enums.Estado;
import tpi_foodstore.enums.FormaPago;
import tpi_foodstore.enums.Rol;
import tpi_foodstore.exceptions.EntidadNoEncontradaException;
import tpi_foodstore.exceptions.StockInvalidoException;
import tpi_foodstore.exceptions.ValidacionException;
import tpi_foodstore.services.CategoriaService;
import tpi_foodstore.services.PedidoService;
import tpi_foodstore.services.ProductoService;
import tpi_foodstore.services.UsuarioService;

import java.util.Scanner;


/**
 *
 * @author cassa
 */
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static CategoriaService categoriaService = new CategoriaService();
    private static ProductoService productoService = new ProductoService();
    private static UsuarioService usuarioService = new UsuarioService();
    private static PedidoService pedidoService = new PedidoService();

    public static void main(String[] args) {
        int opcion = -1;

        do {
            System.out.println("\n=== SISTEMA DE PEDIDOS (FOOD STORE) ===");
            System.out.println("1. Categorías");
            System.out.println("2. Productos");
            System.out.println("3. Usuarios");
            System.out.println("4. Pedidos");
            System.out.println("0. Salir");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());

                switch (opcion) {
                    case 1:
                        menuCategorias();
                        break;
                    case 2:
                        menuProductos();
                        break;
                    case 3:
                        menuUsuarios();
                        break;
                    case 4:
                        menuPedidos();
                        break;
                    case 0:
                        System.out.println("Saliendo del sistema. ¡Hasta luego!");
                        break;
                    default:
                        System.out.println("Opción incorrecta. Intente nuevamente.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Por favor, ingrese un número válido.");
            }
        } while (opcion != 0);
        
        scanner.close();
    }

    // ==========================================================
    // SUBMENÚ: CATEGORÍAS
    // ==========================================================
    private static void menuCategorias() {
        int opcion = -1;
        do {
            System.out.println("\n--- GESTIÓN DE CATEGORÍAS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Editar");
            System.out.println("4. Eliminar");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        categoriaService.listarCategorias();
                        break;
                    case 2:
                        System.out.print("Nombre de la categoría: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Descripción: ");
                        String descripcion = scanner.nextLine();
                        categoriaService.crearCategoria(nombre, descripcion);
                        break;
                    case 3:
                        categoriaService.listarCategorias();
                        System.out.print("ID de la categoría a editar: ");
                        Long idEditar = Long.parseLong(scanner.nextLine());
                        System.out.print("Nuevo nombre (Dejar vacío para omitir): ");
                        String nNombre = scanner.nextLine();
                        System.out.print("Nueva descripción (Dejar vacío para omitir): ");
                        String nDesc = scanner.nextLine();
                        categoriaService.editarCategoria(idEditar, nNombre, nDesc);
                        break;
                    case 4:
                        categoriaService.listarCategorias();
                        System.out.print("ID de la categoría a eliminar: ");
                        Long idEliminar = Long.parseLong(scanner.nextLine());
                        System.out.print("Está seguro? (S/N): ");
                        if (scanner.nextLine().equalsIgnoreCase("S")) {
                            categoriaService.eliminarCategoria(idEliminar);
                        }
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción incorrecta.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingreso inválido, debe ser un número.");
            } catch (ValidacionException | EntidadNoEncontradaException e) {
                System.out.println(e.getMessage());
            }
        } while (opcion != 0);
    }

    // ==========================================================
    // SUBMENÚ: PRODUCTOS
    // ==========================================================
    private static void menuProductos() {
        int opcion = -1;
        do {
            System.out.println("\n--- GESTIÓN DE PRODUCTOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("3. Eliminar");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        productoService.listarProductos();
                        break;
                    case 2:
                        System.out.print("Nombre del producto: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Precio: ");
                        double precio = Double.parseDouble(scanner.nextLine());
                        System.out.print("Descripción: ");
                        String descripcion = scanner.nextLine();
                        System.out.print("Stock inicial: ");
                        int stock = Integer.parseInt(scanner.nextLine());
                        
                        categoriaService.listarCategorias();
                        System.out.print("ID de la Categoría: ");
                        Long idCat = Long.parseLong(scanner.nextLine());
                        Categoria cat = categoriaService.buscarPorId(idCat);

                        productoService.crearProducto(nombre, precio, descripcion, stock, "url_imagen", true, cat);
                        break;
                    case 3:
                        productoService.listarProductos();
                        System.out.print("ID del producto a eliminar: ");
                        Long idEliminar = Long.parseLong(scanner.nextLine());
                        productoService.eliminarProducto(idEliminar);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción incorrecta.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error de formato: asegúrese de ingresar números correctos (ej: precio 1500.50).");
            } catch (ValidacionException | StockInvalidoException | EntidadNoEncontradaException e) {
                System.out.println(e.getMessage());
            }
        } while (opcion != 0);
    }

    // ==========================================================
    // SUBMENÚ: USUARIOS
    // ==========================================================
    private static void menuUsuarios() {
        int opcion = -1;
        do {
            System.out.println("\n--- GESTIÓN DE USUARIOS ---");
            System.out.println("1. Listar");
            System.out.println("2. Crear");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        usuarioService.listarUsuarios();
                        break;
                    case 2:
                        System.out.print("Nombre: ");
                        String nombre = scanner.nextLine();
                        System.out.print("Apellido: ");
                        String apellido = scanner.nextLine();
                        System.out.print("Mail (Único): ");
                        String mail = scanner.nextLine();
                        System.out.print("Celular: ");
                        String celular = scanner.nextLine();
                        System.out.print("Contraseña: ");
                        String clave = scanner.nextLine();
                        System.out.println("Seleccione Rol (1. ADMIN | 2. USUARIO): ");
                        Rol rol = (Integer.parseInt(scanner.nextLine()) == 1) ? Rol.ADMIN : Rol.USUARIO;

                        usuarioService.crearUsuario(nombre, apellido, mail, celular, clave, rol);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción incorrecta.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingreso inválido.");
            } catch (ValidacionException e) {
                System.out.println(e.getMessage());
            }
        } while (opcion != 0);
    }

    // ==========================================================
    // SUBMENÚ: PEDIDOS
    // ==========================================================
    private static void menuPedidos() {
        int opcion = -1;
        do {
            System.out.println("\n--- GESTIÓN DE PEDIDOS ---");
            System.out.println("1. Listar Pedidos");
            System.out.println("2. Crear Pedido (Asignar detalles)");
            System.out.println("0. Volver al menú principal");
            System.out.print("Seleccione: ");

            try {
                opcion = Integer.parseInt(scanner.nextLine());
                switch (opcion) {
                    case 1:
                        pedidoService.listarPedidos();
                        break;
                    case 2:
                        usuarioService.listarUsuarios();
                        System.out.print("\nIngrese ID del Usuario para el pedido: ");
                        Long idUsuario = Long.parseLong(scanner.nextLine());
                        Usuario usuario = usuarioService.buscarPorId(idUsuario);

                        System.out.println("Forma de pago (1. EFECTIVO | 2. TARJETA | 3. TRANSFERENCIA): ");
                        int formaPagoOpt = Integer.parseInt(scanner.nextLine());
                        FormaPago formaPago = switch (formaPagoOpt) {
                            case 2 -> FormaPago.TARJETA;
                            case 3 -> FormaPago.TRANSFERENCIA;
                            default -> FormaPago.EFECTIVO;
                        };

                        // Paso 1: Iniciamos pedido temporal
                        Pedido pedidoTemporal = pedidoService.iniciarPedidoTemporal(usuario, formaPago);

                        // Paso 2: Ciclo para agregar detalles
                        boolean agregando = true;
                        while (agregando) {
                            productoService.listarProductos();
                            System.out.print("Ingrese ID del producto a agregar (0 para finalizar): ");
                            Long idProd = Long.parseLong(scanner.nextLine());
                            
                            if (idProd == 0) {
                                agregando = false;
                            } else {
                                Producto prod = productoService.buscarPorId(idProd);
                                System.out.print("Cantidad: ");
                                int cantidad = Integer.parseInt(scanner.nextLine());
                                
                                pedidoService.agregarDetalleAPedido(pedidoTemporal, prod, cantidad);
                                System.out.println("Producto agregado al carrito.");
                            }
                        }

                        // Paso 3: Confirmamos si tiene detalles
                        pedidoService.confirmarPedido(pedidoTemporal);
                        break;
                    case 0:
                        break;
                    default:
                        System.out.println("Opción incorrecta.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Formato numérico incorrecto.");
            } catch (ValidacionException | StockInvalidoException | EntidadNoEncontradaException e) {
                System.out.println(e.getMessage());
                System.out.println("Creación de pedido abortada para evitar inconsistencias.");
            }
        } while (opcion != 0);
    }
}
