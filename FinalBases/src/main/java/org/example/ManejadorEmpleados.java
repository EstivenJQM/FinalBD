package org.example;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.swing.*;
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
        int codigoFk = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el codigo de la sede"));
        int codigoFk2 = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el codigo de la ciudad"));

        // Crear un nuevo documento
        Document empleado = new Document("id_empleado", identificacion)
                .append("nom_empleado", nombres)
                .append("ape_empleado", apellidos)
                .append("email_empleado", email)
                .append("cod_sede", codigoFk)
                .append("cod_ciudad", codigoFk2);

        // Insertar el documento en la colección
        empleadosCollection.insertOne(empleado);

        // Cerrar la conexión
        mongoClient.close();

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
