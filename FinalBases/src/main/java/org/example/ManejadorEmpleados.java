package org.example;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManejadorEmpleados {

    public static void ingresarEmpleado() {

        String uri = "mongodb+srv://admin:admin123@cluster0.jijwz3h.mongodb.net/?retryWrites=true&w=majority";

        // Crear una instancia de MongoClient
        MongoClient mongoClient = MongoClients.create(uri);

        // Obtener la base de datos
        MongoDatabase database = mongoClient.getDatabase("Eventos");

        // Obtener la colección "empleados"
        MongoCollection<Document> empleadosCollection = database.getCollection("empleados");

        // Se piden los datos a ingresar
        String identificacion = JOptionPane.showInputDialog(null, "Por favor ingrese el documento");
        String nombres = JOptionPane.showInputDialog(null, "Por favor ingrese el nombre");
        String apellidos = JOptionPane.showInputDialog(null, "Por favor ingrese los apellidos");
        String email = JOptionPane.showInputDialog(null, "Por favor ingrese el email");
        int codigoFk = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el codigo de la ciudad"));
        int codigoFk2 = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el codigo de la sede"));

        // Crear un nuevo documento
        Document empleado = new Document("id_empleado", identificacion)
                .append("nom_empleado", nombres)
                .append("ape_empleado", apellidos)
                .append("email_empleado", email)
                .append("cod_ciudad", codigoFk)
                .append("cod_sede", codigoFk2);

        // Insertar el documento en la colección
        empleadosCollection.insertOne(empleado);

        // Cerrar la conexión
        mongoClient.close();

        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);

            // Ejemplo de inserción en la tabla EMPLEADOS
            String sqlSedes = "INSERT INTO EMPLEADOS (ID_EMPLEADO, NOMBRES, APELLIDOS, EMAIL, COD_CIUDAD, COD_SEDE) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement declaraciónSedes = conexión.prepareStatement(sqlSedes);
            declaraciónSedes.setString(1, identificacion); // Valor para ID_EMPLEADO
            declaraciónSedes.setString(2, nombres); // Valor para NOMBRES
            declaraciónSedes.setString(3, apellidos); // Valor para APELLIDO
            declaraciónS
        edes.setString(4, email); // Valor para EMAIL
            declaraciónSedes.setInt(5, codigoFk); // Valor para COD_CIUDAD
            declaraciónSedes.setInt(6, codigoFk2); // Valor para COD_SEDE
            declaraciónSedes.executeUpdate();

            // Realiza inserciones similares para las otras tablas

            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void consultarEmpleado() {

        String uri = "mongodb+srv://admin:admin123@cluster0.jijwz3h.mongodb.net/?retryWrites=true&w=majority";

        // Crear una instancia de MongoClient
        MongoClient mongoClient = MongoClients.create(uri);

        // Obtener la base de datos
        MongoDatabase database = mongoClient.getDatabase("Eventos");

        // Obtener la colección "empleados"
        MongoCollection<Document> empleadosCollection = database.getCollection("empleados");

        // Se pide el código de la empleado a consultar
        String codigoConsulta = JOptionPane.showInputDialog(null, "Por favor ingrese el id del empleado a consultar");

        // Crear un filtro de consulta para buscar por el código de la empleado
        Bson filtro = Filters.eq("id_empleado", codigoConsulta);

        // Realizar la consulta utilizando el filtro
        FindIterable<Document> empleadosConsulta = empleadosCollection.find(filtro);

        // Crear una lista para almacenar los nombres de las empleados encontradas
        List<String> nombresEmpleadosConsulta = new ArrayList<>();

        // Iterar sobre los empleados encontrados y obtener los datos de cada uno
        for (Document empleado : empleadosConsulta) {
            String nombreEmpleado = empleado.getString("nom_empleado");
            String apellidoEmpleado = empleado.getString("ape_empleado");
            String correoEmpleado = empleado.getString("email_empleado");
            int sedeEmpleado = empleado.getInteger("cod_sede");
            int ciudadEmpleado = empleado.getInteger("cod_ciudad");

            // Agregar los datos del empleado a la lista
            nombresEmpleadosConsulta.add("Nombre: " + nombreEmpleado +
                    ", Apellido: " + apellidoEmpleado +
                    ", Correo: " + correoEmpleado +
                    ", Sede: " + sedeEmpleado +
                    ", Ciudad: " + ciudadEmpleado);
        }

        // Mostrar los datos de los empleados encontrados en un cuadro de diálogo
        if (!nombresEmpleadosConsulta.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Empleados encontrados:\n" + String.join("\n", nombresEmpleadosConsulta));
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron empleados con el código especificado.");
        }


    }

    public static void modificarEmpleado() {

        String uri = "mongodb+srv://admin:admin123@cluster0.jijwz3h.mongodb.net/?retryWrites=true&w=majority";

        // Crear una instancia de MongoClient
        MongoClient mongoClient = MongoClients.create(uri);

        // Obtener la base de datos
        MongoDatabase database = mongoClient.getDatabase("Eventos");

        // Obtener la colección "empleados"
        MongoCollection<Document> empleadosCollection = database.getCollection("empleados");

        // Se pide la identificación del empleado a modificar
        String identificacionModificar = JOptionPane.showInputDialog(null, "Por favor ingrese la identificación del empleado a modificar");

        // Crear un filtro de búsqueda para encontrar el empleado a modificar
        Bson filtro = Filters.eq("id_empleado", identificacionModificar);

        // Se piden los nuevos datos del empleado
        String nuevoNombre = JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo nombre del empleado");
        String nuevoApellido = JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo apellido del empleado");
        String nuevoEmail = JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo email del empleado");
        int nuevoCodigoSede = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo código de la sede"));
        int nuevoCodigoCiudad = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo código de la ciudad"));

        // Crear un documento con los nuevos datos del empleado
        Document datosActualizados = new Document("$set", new Document("nom_empleado", nuevoNombre)
                .append("ape_empleado", nuevoApellido)
                .append("email_empleado", nuevoEmail)
                .append("cod_sede", nuevoCodigoSede)
                .append("cod_ciudad", nuevoCodigoCiudad));

        // Actualizar el empleado utilizando el filtro y los nuevos datos
        UpdateResult resultado = empleadosCollection.updateOne(filtro, datosActualizados);

        // Verificar si se realizó la modificación correctamente
        if (resultado.getModifiedCount() > 0) {
            JOptionPane.showMessageDialog(null, "El empleado se modificó correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un empleado con la identificación especificada.");
        }
    }

    public static void eliminarEmpleado() {

        String uri = "mongodb+srv://admin:admin123@cluster0.jijwz3h.mongodb.net/?retryWrites=true&w=majority";

        // Crear una instancia de MongoClient
        MongoClient mongoClient = MongoClients.create(uri);

        // Obtener la base de datos
        MongoDatabase database = mongoClient.getDatabase("Eventos");

        // Obtener la colección "empleados"
        MongoCollection<Document> empleadosCollection = database.getCollection("empleados");

        // Se pide el código de el empleado a eliminar
        String codigoEliminar = JOptionPane.showInputDialog(null, "Por favor ingrese el código del empleado a eliminar");

        // Crear un filtro de eliminación para buscar por el código de el empleado
        Bson filtro = Filters.eq("id_empleado", codigoEliminar);

        // Eliminar la empleado utilizando el filtro
        DeleteResult resultado = empleadosCollection.deleteOne(filtro);

        // Verificar si se eliminó correctamente el empleado
        if (resultado.getDeletedCount() > 0) {
            JOptionPane.showMessageDialog(null, "El empleado se eliminó correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró una empleado con el código especificado.");
        }

    }
}
