package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;

public class ManejadorSedes {

    public static void ingresarSede() {

        String uri = "mongodb+srv://admin:admin123@cluster0.jijwz3h.mongodb.net/?retryWrites=true&w=majority";

        // Crear una instancia de MongoClient
        MongoClient mongoClient = MongoClients.create(uri);

        // Obtener la base de datos
        MongoDatabase database = mongoClient.getDatabase("Eventos");

        // Obtener la colección "Sedes"
        MongoCollection<Document> sedesCollection = database.getCollection("sedes");

        // Se piden los datos a ingresar
        int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el codigo de la sede"));
        String nombre = JOptionPane.showInputDialog(null, "Por favor ingrese el nombre de la sede");
        int codigoFk = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el codigo de la cuidad"));

        // Crear un nuevo documento
        Document sede = new Document("cod_sede", codigo)
                .append("nom_sede", nombre)
                .append("cod_cuidad", codigoFk);

        // Insertar el documento en la colección
        sedesCollection.insertOne(sede);

        // Cerrar la conexión
        mongoClient.close();

    }

    public static void consultarSede() {
        // Lógica para ingresar un nuevo evento en MongoDB
        // Puedes utilizar clases de modelo como `Evento` y métodos de conexión a MongoDB aquí
    }

    public static void modificarSede() {
        // Lógica para ingresar un nuevo evento en MongoDB
        // Puedes utilizar clases de modelo como `Evento` y métodos de conexión a MongoDB aquí
    }

    public static void eliminarSede() {
        // Lógica para ingresar un nuevo evento en MongoDB
        // Puedes utilizar clases de modelo como `Evento` y métodos de conexión a MongoDB aquí
    }
}
