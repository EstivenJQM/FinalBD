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

public class ManejadorSedes {

    public static void ingresarSede() {

        // Se piden los datos a ingresar
        int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el codigo de la sede"));
        String nombre = JOptionPane.showInputDialog(null, "Por favor ingrese el nombre de la sede");
        int codigoFk = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el codigo de la ciudad"));


        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);

            // Ejemplo de inserción en la tabla SEDES
            String sqlSedes = "INSERT INTO SEDES (COD_SEDE, NOM_SEDE, COD_CIUDAD) VALUES (?, ?, ?)";
            PreparedStatement declaraciónSedes = conexión.prepareStatement(sqlSedes);
            declaraciónSedes.setInt(1, codigo); // Valor para COD_SEDE
            declaraciónSedes.setString(2, nombre); // Valor para NOM_SEDE
            declaraciónSedes.setInt(3, codigoFk); // Valor para COD_CIUDAD
            int filasInsertadas = declaraciónSedes.executeUpdate();

            // Verificar si se insertó correctamente la sede
            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(null, "La sede se insertó correctamente.");

            } else {
                JOptionPane.showMessageDialog(null, "No se pudo insertar el departamento.");
            }

            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void consultarSede() {

        // Se pide el código de la sede a consultar
        int codigoConsulta = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código de la sede a consultar"));

        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);

            // Consulta SQL para obtener una sola sede
            String sql = "SELECT COD_SEDE, NOM_SEDE FROM SEDES WHERE COD_SEDE = ?";
            PreparedStatement declaración = conexión.prepareStatement(sql);

            // Establecer el valor del parámetro en la consulta
            declaración.setInt(1, codigoConsulta);

            ResultSet resultado = declaración.executeQuery();

            // Verificar si se encontró la sede
            if (resultado.next()) {
                int codSede = resultado.getInt("COD_SEDE");
                String nomSede = resultado.getString("NOM_SEDE");

                JOptionPane.showMessageDialog(null, "Código: " + codSede + ", Nombre: " + nomSede);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la sede con el código especificado.");
            }

            resultado.close();
            declaración.close();
            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void modificarSede() {

        // Se pide el código de la sede a modificar
        int codigoModificar = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código de la sede a modificar"));


        // Se piden los nuevos datos de la sede
        String nuevoNombre = JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo nombre de la sede");
        int nuevoCodigoCiudad = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo código de la ciudad"));

        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);

            // Consulta SQL para actualizar la sede
            String sql = "UPDATE SEDES SET NOM_SEDE = ?, COD_CIUDAD = ? WHERE COD_SEDE = ?";
            PreparedStatement declaración = conexión.prepareStatement(sql);

            // Establecer los valores de los parámetros en la consulta
            declaración.setString(1, nuevoNombre);
            declaración.setInt(2, nuevoCodigoCiudad);
            declaración.setInt(3, codigoModificar);

            int filasActualizadas = declaración.executeUpdate();

            // Verificar si se actualizó correctamente la sede
            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(null, "La sede se actualizó correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la sede a actualizar.");
            }

            declaración.close();
            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void eliminarSede() {

        // Se pide el código de la sede a eliminar
        int codigoEliminar = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código de la sede a eliminar"));


        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);

            // Consulta SQL de eliminación de sede
            String sql = "DELETE FROM SEDES WHERE COD_SEDE = ?";
            PreparedStatement declaración = conexión.prepareStatement(sql);

            // Valor para la eliminación
            declaración.setInt(1, codigoEliminar); // Código de la sede a eliminar

            int filasEliminadas = declaración.executeUpdate();
            if (filasEliminadas > 0) {
                JOptionPane.showMessageDialog(null, "La sede se eliminó correctamente de postgres.");

            } else {
                JOptionPane.showMessageDialog(null, "No se encontró la sede a eliminar.");
            }

            declaración.close();
            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }
}
