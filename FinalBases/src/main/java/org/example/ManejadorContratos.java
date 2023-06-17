package org.example;

import javax.swing.*;
import java.sql.*;

public class ManejadorContratos {

    public static void ingresarContrato() {

        // Se piden los datos a ingresar
        int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el codigo del contrato"));
        String nombre = JOptionPane.showInputDialog(null, "Por favor ingrese el tipo de contrato");

        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);

            // Ejemplo de inserción en la tabla CONTRATOS
            String sqlContratos = "INSERT INTO CONTRATOS (COD_CONTRATO, NOM_CONTRATO) VALUES (?, ?)";
            PreparedStatement declaraciónContratos = conexión.prepareStatement(sqlContratos);

            declaraciónContratos.setInt(1, codigo); // Valor para COD_DEPARTAMENTO
            declaraciónContratos.setString(2, nombre); // Valor para NOM_DEPARTAMENTO

            int filasInsertadas = declaraciónContratos.executeUpdate();

            // Verificar si se insertó correctamente el departamento
            if (filasInsertadas > 0) {
                JOptionPane.showMessageDialog(null, "El tipo de contrato se insertó correctamente.");

            } else {
                JOptionPane.showMessageDialog(null, "No se pudo insertar el tipo de contrato.");
            }

            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void consultarContrato() {

        // Se pide el código del tipo de contrato a consultar
        int codigoConsulta = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código del tipo de contrato a consultar"));

        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);


            // Consulta SQL para obtener un solo contrato
            String sql = "SELECT COD_CONTRATO, NOM_CONTRATO FROM CONTRATOS WHERE COD_CONTRATO = ?";
            PreparedStatement declaración = conexión.prepareStatement(sql);

            // Establecer el valor del parámetro en la consulta
            declaración.setInt(1, codigoConsulta);

            ResultSet resultado = declaración.executeQuery();

            // Verificar si se encontró el contrato
            if (resultado.next()) {
                int codContrato = resultado.getInt("COD_CONTRATO");
                String nomContrato = resultado.getString("NOM_CONTRATO");

                JOptionPane.showMessageDialog(null, "Código: " + codContrato + ", Nombre: " + nomContrato);
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el tipo de contrato con el código especificado.");
            }

            resultado.close();
            declaración.close();
            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public static void modificarContrato() {

        // Se pide el código del contrato a modificar
        int codigoModificar = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código del tipo de contrato a modificar"));

        // Se piden los nuevos datos del departamento
        String nuevoNombre = JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo nombre del tipo de contrato");

        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);

            // Consulta SQL de actualización de departamento
            String sql = "UPDATE CONTRATOS SET NOM_CONTRATO = ? WHERE COD_CONTRATO = ?";
            PreparedStatement declaración = conexión.prepareStatement(sql);

            // Valores para la actualización
            declaración.setString(1, nuevoNombre); // Nuevo nombre del Contrato
            declaración.setInt(2, codigoModificar); // Código del Contrato a actualizar

            int filasActualizadas = declaración.executeUpdate();
            if (filasActualizadas > 0) {
                JOptionPane.showMessageDialog(null, "Tipo de contrato actualizado correctamente.");
            } else {
                JOptionPane.showMessageDialog(null, "No se encontró el departamento a actualizar.");
            }

            declaración.close();
            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void eliminarContrato() {

        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        // Se pide el código de tipo de contrato a eliminar
        int codigoEliminar = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código del tipo de contrato a eliminar"));

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);

            // Consulta SQL de eliminación de tipo de contrato
            String sql = "DELETE FROM CONTRATOS WHERE COD_CONTRATO = ?";
            PreparedStatement declaración = conexión.prepareStatement(sql);

            // Valor para la eliminación
            declaración.setInt(1, codigoEliminar); // Código del departamento a eliminar

            int filasEliminadas = declaración.executeUpdate();
            if (filasEliminadas > 0) {
                JOptionPane.showMessageDialog(null, "El tipo de contrato se eliminó correctamente de postgres.");

            } else {

                JOptionPane.showMessageDialog(null, "No se encontró el tipo de contrato a eliminar.");
            }

            declaración.close();
            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
