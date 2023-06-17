package org.example;
//MONGO

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

//POSTGRESQL

import java.sql.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;



public class ManejadorDepartamentos {

    public static void ingresarDepartamento() {

        // Se piden los datos a ingresar
        int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el codigo del departamento"));
        String nombre = JOptionPane.showInputDialog(null, "Por favor ingrese el nombre del departamento");


        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);

            // Ejemplo de inserción en la tabla DEPARTAMENTOS
            String sqlDepartamentos = "INSERT INTO DEPARTAMENTOS (COD_DEPARTAMENTO, NOM_DEPARTAMENTO) VALUES (?, ?)";
            PreparedStatement declaraciónDepartamentos = conexión.prepareStatement(sqlDepartamentos);

            declaraciónDepartamentos.setInt(1, codigo); // Valor para COD_DEPARTAMENTO
            declaraciónDepartamentos.setString(2, nombre); // Valor para NOM_DEPARTAMENTO

            int filasInsertadas = declaraciónDepartamentos.executeUpdate();

            // Verificar si se insertó correctamente el departamento
            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(null, "El departamento se insertó correctamente.");

            } else {
                JOptionPane.showMessageDialog(null, "No se pudo insertar el departamento.");
            }

            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void consultarDepartamento() {

        // Se pide el código de la departamento a consultar
        int codigoConsulta = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código de la departamento a consultar"));

        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);


            // Consulta SQL para obtener un solo departamento
            String sql = "SELECT COD_DEPARTAMENTO, NOM_DEPARTAMENTO FROM DEPARTAMENTOS WHERE COD_DEPARTAMENTO = ?";
            PreparedStatement declaración = conexión.prepareStatement(sql);

            // Establecer el valor del parámetro en la consulta
            declaración.setInt(1, codigoConsulta);

            ResultSet resultado = declaración.executeQuery();

            // Verificar si se encontró el departamento
            if (resultado.next()) {
                int codDepartamento = resultado.getInt("COD_DEPARTAMENTO");
                String nomDepartamento = resultado.getString("NOM_DEPARTAMENTO");

                JOptionPane.showMessageDialog(null, "Código: " + codDepartamento + ", Nombre: " + nomDepartamento);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el departamento con el código especificado.");
            }

            resultado.close();
            declaración.close();
            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void modificarDepartamento() {

        // Se pide el código del departamento a modificar
        int codigoModificar = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código del departamento a modificar"));

        // Se piden los nuevos datos del departamento
        String nuevoNombre = JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo nombre del departamento");

        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);

            // Consulta SQL de actualización de departamento
            String sql = "UPDATE DEPARTAMENTOS SET NOM_DEPARTAMENTO = ? WHERE COD_DEPARTAMENTO = ?";
            PreparedStatement declaración = conexión.prepareStatement(sql);

            // Valores para la actualización
            declaración.setString(1, nuevoNombre); // Nuevo nombre del departamento
            declaración.setInt(2, codigoModificar); // Código del departamento a actualizar

            int filasActualizadas = declaración.executeUpdate();
            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(null, "Departamento actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el departamento a actualizar.");
            }

            declaración.close();
            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void eliminarDepartamento() {

        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        // Se pide el código de el departamento a eliminar
        int codigoEliminar = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código del departamento a eliminar"));

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);

            // Consulta SQL de eliminación de departamento
            String sql = "DELETE FROM DEPARTAMENTOS WHERE COD_DEPARTAMENTO = ?";
            PreparedStatement declaración = conexión.prepareStatement(sql);

            // Valor para la eliminación
            declaración.setInt(1, codigoEliminar); // Código del departamento a eliminar

            int filasEliminadas = declaración.executeUpdate();
            if (filasEliminadas > 0) {
                JOptionPane.showMessageDialog(null, "El departamento se eliminó correctamente de postgres.");

            } else {

                JOptionPane.showMessageDialog(null, "No se encontró el departamento a eliminar.");
            }

            declaración.close();
            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
