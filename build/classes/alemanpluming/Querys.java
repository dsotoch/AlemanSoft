/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alemanpluming;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Querys {

    public static String GetTerminos() throws SQLException, ClassNotFoundException {

        try (Connection con = conexion.conectar(); PreparedStatement pr = con.prepareStatement("SELECT * FROM configuraciones"); ResultSet rs = pr.executeQuery()) {

            if (rs.next()) {
                return rs.getString("termino");
            } else {
                return "0";

            }

        }

    }

    public static String getFolio() throws SQLException, ClassNotFoundException {
        try (Connection con = conexion.conectar(); Statement pr = con.createStatement(); ResultSet rs = pr.executeQuery("SELECT MAX(id) FROM factura")) {

            if (rs.next()) {
                return String.format("%04d", rs.getInt(1) + 1);
            } else {
                return "0";
            }

        }

    }

    public static int getCantidad() throws SQLException, ClassNotFoundException {
        try (Connection con = conexion.conectar(); Statement pr = con.createStatement(); ResultSet rs = pr.executeQuery("SELECT count(id) FROM factura")) {
            if (rs.next()) {
                return rs.getInt(1);
            } else {
                return 0;
            }

        }

    }

    public static ArrayList<String> UltimaFactura() throws SQLException, ClassNotFoundException {
        Connection con = null;
        Statement pr = null;
        ResultSet rs = null;
        ArrayList<String> da = new ArrayList<>();
        try {
            con = conexion.conectar();
            String sql = "SELECT f.id as factura_id , f.trab_fecha as fecha, "
                    + "cl.id as cliente_id, cl.nombre as cliente_nombre, "
                    + "df.id as detalle_id, df.total as total "
                    + "FROM factura f "
                    + "INNER JOIN clientes cl ON cl.id = f.cliente_id "
                    + "INNER JOIN detalle_factura df ON df.factura_id = f.id "
                    + "ORDER BY f.id DESC LIMIT 1;";
            pr = con.createStatement();
            rs = pr.executeQuery(sql);

            if (rs.next()) {
                da.add(rs.getString("cliente_nombre"));
                da.add(rs.getDate("fecha").toString());
                da.add(String.valueOf(rs.getDouble("total")));

            }
            // Process the ResultSet or return it, as needed
            return da;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            // Close resources in reverse order to avoid potential issues
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (pr != null) {
                try {
                    pr.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }

            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
        return null;

    }

    public static String GetCliente(String nombre) throws SQLException, ClassNotFoundException {
        try (Connection con = conexion.conectar(); PreparedStatement pr = con.prepareStatement("SELECT * FROM clientes WHERE nombre = ?");) {
            pr.setString(1, nombre);

            try (ResultSet r = pr.executeQuery()) {
                if (r.next()) {
                    // Obtén el valor deseado antes de cerrar el ResultSet
                    String direccion = r.getString("direccion");

                    return direccion;
                } else {
                    // No necesitas cerrar el ResultSet en el else ya que estás saliendo del bloque try-with-resources
                    return "0";
                }
            }
        }
    }

    public static clientr GetCliente2(int nombre) throws SQLException, ClassNotFoundException {
        try (Connection con = conexion.conectar(); PreparedStatement pr = con.prepareStatement("SELECT * FROM clientes WHERE id = ?");) {
            pr.setInt(1, nombre);

            try (ResultSet r3 = pr.executeQuery();) {
                return new clientr(r3.getString("nombre"), r3.getString("direccion"), new java.util.Date());

            }
        }
    }

    public static factura getFactura(String factura) throws SQLException, ClassNotFoundException {
        try (Connection con = conexion.conectar(); PreparedStatement pr = con.prepareStatement("SELECT * FROM factura WHERE factura = ?");) {
            pr.setString(1, factura);

            try (ResultSet r = pr.executeQuery()) {

                trabajo trabajo = new trabajo(r.getString("tipoorden"), r.getString("trabajo"), r.getDouble("costoviaje"), r.getDouble("total_labor"));

                costos costos = getCostos(r.getInt("id"));
                clientr cliente = GetCliente2(r.getInt("cliente_id"));
                otros otros = new otros(r.getString("trabajador"), r.getDate("trab_fecha"), r.getString("comentario"), r.getString("terminos"));

                ArrayList<materiales> arr = getMateriales(r.getInt("id"));
                return new factura(cliente, trabajo, otros, costos, r.getString("factura"), arr);
            }
        }

    }

    public static costos getCostos(int factura) throws SQLException, ClassNotFoundException {
        try (Connection con = conexion.conectar(); PreparedStatement pr = con.prepareStatement("SELECT * FROM detalle_factura WHERE factura_id = ?")) {
            pr.setInt(1, factura);
            try (ResultSet r4 = pr.executeQuery()) {
                return new costos(r4.getDouble("costo_material"), r4.getDouble("impuesto"), r4.getDouble("lumberfee"), r4.getDouble("laborcharge"), r4.getDouble("snakecharge"), r4.getDouble("hidrojetter"), r4.getDouble("total"));

            }

        } catch (SQLException e) {
            // Handle or log the exception
            throw e;
        }

    }

    public static int existeCliente(String nombre, String direccion) throws SQLException, ClassNotFoundException {
        Connection con = conexion.conectar();
        String sql = "SELECT * FROM clientes WHERE nombre = ? AND direccion= ?";
        PreparedStatement pr = con.prepareStatement(sql);
        pr.setString(1, nombre);
        pr.setString(2, direccion);

        try (ResultSet re = pr.executeQuery()) {
            if (re.next()) {
                return re.getInt("id");
            } else {
                return 0;
            }
        } finally {
            con.close();

        }

    }

    public static ArrayList<materiales> getMateriales(int id) {
        try {
            ArrayList<materiales> ar = new ArrayList<>();
            Connection con = conexion.conectar();
            String sql = "SELECT * FROM materiales WHERE factura_id = '" + id + "'";
            Statement stmt = con.createStatement();
            ResultSet r = stmt.executeQuery(sql);
            while (r.next()) {
                materiales ma = new materiales(r.getString("nombre"), r.getDouble("cantidad"), r.getDouble("costo"));
                ar.add(ma);
            }
            if (r != null) {
                r.close();
            }

            if (con != null) {
                con.close();
            }

            return ar;
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return null;
    }

    public static factura GuardarFactura(ArrayList<materiales> mate, factura fa) {

        factura fas = new factura(fa.getCliente(), fa.getMateriales(), fa.getTrabajo(), fa.getOtros(), fa.getCostos(), fa.getFactura());
        factura fac = fas.guardarFactura();

        try (Connection connection = conexion.conectar(); PreparedStatement pstmt1 = connection.prepareStatement(
                "INSERT INTO factura (trabajo, tipoorden, fechacliente, trabajador, trab_fecha, comentario, terminos, costoviaje, total_labor,cliente_id,factura) VALUES (?,?,?,?,?,?,?,?,?,?,?)",
                Statement.RETURN_GENERATED_KEYS); PreparedStatement pstmt2 = connection.prepareStatement(
                        "INSERT INTO clientes (nombre, direccion) VALUES (?,?)",
                        Statement.RETURN_GENERATED_KEYS); PreparedStatement pstmt3 = connection.prepareStatement(
                        "INSERT INTO detalle_factura (costo_material, impuesto, lumberfee, laborcharge, snakecharge, hidrojetter, total, factura_id) VALUES (?,?,?,?,?,?,?,?)"); PreparedStatement pstmt4 = connection.prepareStatement(
                        "INSERT INTO materiales (nombre, costo, cantidad, total, factura_id) VALUES (?,?,?,?,?)")) {

            int id_cliente = existeCliente(fa.getCliente().getOrderBy(), fa.getCliente().getAddress());

            if (id_cliente == 0) {
                pstmt2.setString(1, fac.getCliente().getOrderBy());
                pstmt2.setString(2, fac.getCliente().getAddress());
                pstmt2.executeUpdate();
                ResultSet generatedKeys = pstmt2.getGeneratedKeys();

                if (generatedKeys.next()) {
                    id_cliente = generatedKeys.getInt(1);
                }
            }

            pstmt1.setString(1, fac.getTrabajo().getTrabajo());
            pstmt1.setString(2, fac.getTrabajo().getDetalles());
            pstmt1.setDate(3, (Date) fac.getCliente().getDate());
            pstmt1.setString(4, fac.getOtros().getTrabajador());
            pstmt1.setDate(5, (Date) fac.getOtros().getFecha());
            pstmt1.setString(6, fac.getOtros().getComentarios());
            pstmt1.setString(7, fac.getOtros().getTerminos());
            pstmt1.setDouble(8, fac.getTrabajo().getCargo_viaje());
            pstmt1.setDouble(9, fac.getTrabajo().getTotal());
            pstmt1.setInt(10, id_cliente);
            pstmt1.setString(11, fac.getFactura());
            pstmt1.executeUpdate();

            pstmt3.setDouble(1, fac.getCostos().getCosto_materiales());
            pstmt3.setDouble(2, fac.getCostos().getImpuesto());
            pstmt3.setDouble(3, fac.getCostos().getTarifa_madera());
            pstmt3.setDouble(4, fac.getCostos().getCargo_trabajo());
            pstmt3.setDouble(5, fac.getCostos().getTarifa_cobros());
            pstmt3.setDouble(6, fac.getCostos().getTarifa_cobros_idroject());
            pstmt3.setDouble(7, fac.getCostos().getTotal());
            pstmt3.setInt(8, pstmt1.getGeneratedKeys().getInt(1));
            pstmt3.executeUpdate();

            if (!mate.isEmpty()) {
                int generatedKey = pstmt1.getGeneratedKeys().getInt(1);

                for (materiales m : mate) {
                    pstmt4.setString(1, m.getNombre());
                    pstmt4.setDouble(2, m.getCosto());
                    pstmt4.setDouble(3, m.getCantidad());
                    pstmt4.setDouble(4, m.getCosto() * m.getCantidad());
                    pstmt4.setInt(5, generatedKey);
                    pstmt4.executeUpdate();
                }
            }

        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace(); // Log or handle the exception
            fac = null;
        }

        return fac;

    }

    public static void eliminarTermino() {
        try (Connection c = conexion.conectar(); Statement s = c.createStatement()) {
        s.executeUpdate("DELETE FROM configuraciones");
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Querys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static boolean InsertarTermino(String termino) throws ClassNotFoundException, SQLException {
        eliminarTermino();
        try (Connection con = conexion.conectar()) {
            // Create the database and table if they don't exist
            crearBaseDeDatosYTabla(con);

            // Insert query
            String sql = "INSERT INTO configuraciones (termino) VALUES (?)";

            // Create a PreparedStatement
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {

                // Set the parameter value
                preparedStatement.setString(1, termino);

                // Execute the insert query
                int rowsAffected = preparedStatement.executeUpdate();

                // Check if any rows were affected (success)
                return rowsAffected != 0;
            }
        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace(); // Log or handle the exception
            return false; // Return false in case of an exception
        }

    }

    private static void crearBaseDeDatosYTabla(Connection connection) throws SQLException {
        // Consulta para crear la base de datos

        // Consulta para seleccionar la base de datos
        // Consulta para crear la tabla
        String createTableSQL = "CREATE TABLE IF NOT EXISTS configuraciones (id INT AUTO_INCREMENT PRIMARY KEY, termino TEXT)";

        try (Statement statement = connection.createStatement()) {

            // Seleccionar la base de datos
            // Crear la tabla si no existe
            statement.executeUpdate(createTableSQL);
            statement.close();
        }
    }

    public static void crearTablaFactura(Connection connection) throws SQLException {
        // Consulta para crear la base de datos

        // Consulta para seleccionar la base de datos
        // Consulta para crear la tabla
        String createTableSQL = "CREATE TABLE IF NOT EXISTS factura ("
                + "id INTEGER   PRIMARY KEY, "
                + "trabajo varchar(255), "
                + "tipoorden TEXT, "
                + "fechacliente DATE, "
                + "trabajador varchar(255), "
                + "trab_fecha DATE, "
                + "comentario TEXT, "
                + "terminos TEXT, "
                + "costoviaje double(9,2), "
                + "total_labor double(9,2),"
                + "cliente_id INTEGER ,"
                + "factura VARCHAR(255),"
                + "FOREIGN KEY (cliente_id) REFERENCES clientes(id)"
                + ")";
        String crea = "CREATE TABLE IF NOT EXISTS clientes ("
                + "id INTEGER  PRIMARY KEY, "
                + "nombre varchar(255), "
                + "direccion varchar(255))";

        String crea2 = "CREATE TABLE IF NOT EXISTS detalle_factura ("
                + "id INTEGER  PRIMARY KEY, "
                + "costo_material double(9,2), "
                + "impuesto double(9,2), "
                + "lumberfee double(9,2), "
                + "laborcharge double(9,2), "
                + "snakecharge double(9,2), "
                + "hidrojetter double(9,2), "
                + "total double(9,2), "
                + "factura_id INTEGER , "
                + // Agrega la columna de la clave foránea
                "FOREIGN KEY (factura_id) REFERENCES factura(id)"
                + ")";
        String crea3 = "CREATE TABLE IF NOT EXISTS materiales ("
                + "id INTEGER  PRIMARY KEY, "
                + "nombre varchar(255), "
                + "costo double(9,2), "
                + "cantidad double(9,2), "
                + "total double(9,2), "
                + "factura_id INTEGER , "
                + // Agrega la columna de la clave foránea
                "FOREIGN KEY (factura_id) REFERENCES factura(id)"
                + ")";

        try (Statement statement = connection.createStatement()) {

            // Seleccionar la base de datos
            // Crear la tabla si no existe
            statement.executeUpdate(crea);

            statement.executeUpdate(createTableSQL);
            statement.executeUpdate(crea2);
            statement.executeUpdate(crea3);

            statement.close();
        }
    }
}
