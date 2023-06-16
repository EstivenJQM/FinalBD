package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.swing.*;

public class ManejadorCuidades {

    public static void ingresarCuidad() {
        String uri = "mongodb+srv://admin:admin123@cluster0.jijwz3h.mongodb.net/?retryWrites=true&w=majority";

        // Crear una instancia de MongoClient
        MongoClient mongoClient = MongoClients.create(uri);

        // Obtener la base de datos
        MongoDatabase database = mongoClient.getDatabase("Eventos");

        // Obtener la colección "cuidades"
        MongoCollection<Document> cuidadesCollection = database.getCollection("cuidades");

        // Se piden los datos a ingresar
        int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el codigo de la cuidad"));
        String nombre = JOptionPane.showInputDialog(null, "Por favor ingrese el nombre de la cuidad");
        int codigoFk = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el codigo del departamento"));

        // Crear un nuevo documento
        Document cuidad = new Document("cod_cuidad", codigo)
                .append("nom_cuidad", nombre)
                .append("cod_departamento", codigoFk);

        // Insertar el documento en la colección
        cuidadesCollection.insertOne(cuidad);

        // Cerrar la conexión
        mongoClient.close();
    }

    public static void consultarCuidad() {
        // Lógica para ingresar un nuevo evento en MongoDB
        // Puedes utilizar clases de modelo como `Evento` y métodos de conexión a MongoDB aquí
    }

    public static void modificarCuidad() {
        // Lógica para ingresar un nuevo evento en MongoDB
        // Puedes utilizar clases de modelo como `Evento` y métodos de conexión a MongoDB aquí
    }

    public static void eliminarCuidad() {
        // Lógica para ingresar un nuevo evento en MongoDB
        // Puedes utilizar clases de modelo como `Evento` y métodos de conexión a MongoDB aquí
    }
}
