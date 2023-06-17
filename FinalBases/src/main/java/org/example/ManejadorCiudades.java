package org.example;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManejadorCiudades {

    public static void ingresarCiudad() {


        // Se piden los datos a ingresar
        int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el codigo de la ciudad"));
        String nombre = JOptionPane.showInputDialog(null, "Por favor ingrese el nombre de la ciudad");
        int codigoFk = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el codigo del departamento"));

        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);

            // Ejemplo de inserción en la tabla CIUDADES
            String sqlCiudades = "INSERT INTO CIUDADES (COD_CIUDAD, NOM_CIUDAD, COD_DEPARTAMENTO) VALUES (?, ?, ?)";
            PreparedStatement declaraciónCiudades = conexión.prepareStatement(sqlCiudades);
            declaraciónCiudades.setInt(1, codigo); // Valor para COD_CIUDAD
            declaraciónCiudades.setString(2, nombre); // Valor para NOM_CIUDAD
            declaraciónCiudades.setInt(3, codigoFk); // Valor para COD_DEPARTAMENTO
            int filasInsertadas = declaraciónCiudades.executeUpdate();

            // Verificar si se insertó correctamente la ciudad
            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(null, "La ciudad se insertó correctamente.");


            } else {
                JOptionPane.showMessageDialog(null, "No se pudo insertar el departamento.");

            }

            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void consultarCiudad() {

        // Se pide el código de la ciudad a consultar
        int codigoConsulta = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código de la ciudad a consultar"));

        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);


            // Consulta SQL para obtener una sola ciudad
            String sql = "SELECT COD_CIUDAD, NOM_CIUDAD FROM CIUDADES WHERE COD_CIUDAD = ?";
            PreparedStatement declaración = conexión.prepareStatement(sql);

            // Establecer el valor del parámetro en la consulta
            declaración.setInt(1, codigoConsulta);

            ResultSet resultado = declaración.executeQuery();

            // Verificar si se encontró la ciudad
            if (resultado.next()) {
                int codCiudad = resultado.getInt("COD_CIUDAD");
                String nomCiudad = resultado.getString("NOM_CIUDAD");
                JOptionPane.showMessageDialog(null, "Código: " + codCiudad + ", Nombre: " + nomCiudad);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la ciudad con el código especificado.");
            }

            resultado.close();
            declaración.close();
            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void modificarCiudad() {



        // Se pide el código de la ciudad a modificar
        int codigoModificar = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código de la ciudad a modificar"));


        // Se piden los nuevos datos de la ciudad
        String nuevoNombre = JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo nombre de la ciudad");
        int nuevoCodigoDepartamento = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo código del departamento"));


        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";


        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);

            // Consulta SQL para actualizar la ciudad
            String sql = "UPDATE CIUDADES SET NOM_CIUDAD = ?, COD_DEPARTAMENTO = ? WHERE COD_CIUDAD = ?";
            PreparedStatement declaración = conexión.prepareStatement(sql);

            // Establecer los valores de los parámetros en la consulta
            declaración.setString(1, nuevoNombre);
            declaración.setInt(2, nuevoCodigoDepartamento);
            declaración.setInt(3, codigoModificar);

            int filasActualizadas = declaración.executeUpdate();

            // Verificar si se actualizó correctamente la ciudad
            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(null, "La ciudad se actualizó correctamente.");

            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la ciudad a actualizar.");

            }

            declaración.close();
            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void eliminarCiudad() {



        // Se pide el código de la ciudad a eliminar
        int codigoEliminar = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código de la ciudad a eliminar"));


        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);

            // Consulta SQL de eliminación de ciudad
            String sql = "DELETE FROM CIUDADES WHERE COD_CIUDAD = ?";
            PreparedStatement declaración = conexión.prepareStatement(sql);

            // Valor para la eliminación
            declaración.setInt(1, codigoEliminar); // Código de la ciudad a eliminar

            int filasEliminadas = declaración.executeUpdate();
            if (filasEliminadas > 0) {
                JOptionPane.showMessageDialog(null, "La ciudad se eliminó correctamente de postgres.");

            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la ciudad a eliminar.");

            }

            declaración.close();
            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
