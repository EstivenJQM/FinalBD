package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;

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
        int codigoFk2 = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el codigo de la cuidad"));

        // Crear un nuevo documento
        Document empleado = new Document("id_empleado", identificacion)
                .append("nom_empleado", nombres)
                .append("ape_empleado", apellidos)
                .append("email_empleado", email)
                .append("cod_sede", codigoFk)
                .append("cod_departamento", codigoFk2);

        // Insertar el documento en la colección
        empleadosCollection.insertOne(empleado);

        // Cerrar la conexión
        mongoClient.close();

    }

    public static void consultarEmpleado() {
        // Lógica para ingresar un nuevo evento en MongoDB
        // Puedes utilizar clases de modelo como `Evento` y métodos de conexión a MongoDB aquí
    }

    public static void modificarEmpleado() {
        // Lógica para ingresar un nuevo evento en MongoDB
        // Puedes utilizar clases de modelo como `Evento` y métodos de conexión a MongoDB aquí
    }

    public static void eliminarEmpleado() {
        // Lógica para ingresar un nuevo evento en MongoDB
        // Puedes utilizar clases de modelo como `Evento` y métodos de conexión a MongoDB aquí
    }
}
