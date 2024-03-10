/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package alemanpluming;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author dsoto
 */
public class conexion {

    public static Connection conectar() throws ClassNotFoundException {
        Connection connection = null;

        try {
            // Establecer la conexión a la base de datos
            connection = DriverManager.getConnection("jdbc:sqlite:aleman.sqlite");
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
