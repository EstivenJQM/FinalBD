package org.example;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import javax.swing.*;

public class ManejadorDepartamentos {

    public static void ingresarDepartamento() {

        String uri = "mongodb+srv://admin:admin123@cluster0.jijwz3h.mongodb.net/?retryWrites=true&w=majority";

        // Crear una instancia de MongoClient
        MongoClient mongoClient = MongoClients.create(uri);

        // Obtener la base de datos
        MongoDatabase database = mongoClient.getDatabase("Eventos");

        // Obtener la colección "departamentos"
        MongoCollection<Document> departamentosCollection = database.getCollection("departamentos");

        // Se piden los datos a ingresar
        int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el codigo del departamento"));
        String nombre = JOptionPane.showInputDialog(null, "Por favor ingrese el nombre del departamento");

        // Crear un nuevo documento
        Document departamento = new Document("cod_departamento", codigo)
                .append("nom_departamento", nombre);

        // Insertar el documento en la colección
        departamentosCollection.insertOne(departamento);

        // Cerrar la conexión
        mongoClient.close();

    }

    public static void consultarDepartamento() {
        // Lógica para ingresar un nuevo evento en MongoDB
        // Puedes utilizar clases de modelo como `Evento` y métodos de conexión a MongoDB aquí
    }

    public static void modificarDepartamento() {
        // Lógica para ingresar un nuevo evento en MongoDB
        // Puedes utilizar clases de modelo como `Evento` y métodos de conexión a MongoDB aquí
    }

    public static void eliminarDepartamento() {
        // Lógica para ingresar un nuevo evento en MongoDB
        // Puedes utilizar clases de modelo como `Evento` y métodos de conexión a MongoDB aquí
    }
}
