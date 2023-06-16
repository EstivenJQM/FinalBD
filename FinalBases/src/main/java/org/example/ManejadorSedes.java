package org.example;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

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
        int codigoFk = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el codigo de la ciudad"));

        // Crear un nuevo documento
        Document sede = new Document("cod_sede", codigo)
                .append("nom_sede", nombre)
                .append("cod_ciudad", codigoFk);

        // Insertar el documento en la colección
        sedesCollection.insertOne(sede);

        // Cerrar la conexión
        mongoClient.close();

    }

    public static void consultarSede() {
        String uri = "mongodb+srv://admin:admin123@cluster0.jijwz3h.mongodb.net/?retryWrites=true&w=majority";

        // Crear una instancia de MongoClient
        MongoClient mongoClient = MongoClients.create(uri);

        // Obtener la base de datos
        MongoDatabase database = mongoClient.getDatabase("Eventos");

        // Obtener la colección "sedes"
        MongoCollection<Document> sedesCollection = database.getCollection("sedes");

        // Se pide el código de la sede a consultar
        int codigoConsulta = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código de la sede a consultar"));

        // Crear un filtro de consulta para buscar por el código de la sede
        Bson filtro = Filters.eq("cod_sede", codigoConsulta);

        // Realizar la consulta utilizando el filtro
        FindIterable<Document> sedesConsulta = sedesCollection.find(filtro);

        // Crear una lista para almacenar los nombres de las sedes encontradas
        List<String> nombresSedesConsulta = new ArrayList<>();

        // Iterar sobre las sedes encontradas y obtener el nombre de cada una
        for (Document sede : sedesConsulta) {
            String nombreSede = sede.getString("nom_sede");
            nombresSedesConsulta.add(nombreSede);
        }

        // Mostrar los nombres de las sedes encontradas en un cuadro de diálogo
        if (!nombresSedesConsulta.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Sede encontrada: " + nombresSedesConsulta);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron sedes con el código especificado.");
        }

    }

    public static void modificarSede() {
        // Lógica para ingresar un nuevo evento en MongoDB
        // Puedes utilizar clases de modelo como `Evento` y métodos de conexión a MongoDB aquí
    }

    public static void eliminarSede() {

        String uri = "mongodb+srv://admin:admin123@cluster0.jijwz3h.mongodb.net/?retryWrites=true&w=majority";

        // Crear una instancia de MongoClient
        MongoClient mongoClient = MongoClients.create(uri);

        // Obtener la base de datos
        MongoDatabase database = mongoClient.getDatabase("Eventos");

        // Obtener la colección "Sedes"
        MongoCollection<Document> sedesCollection = database.getCollection("sedes");

        // Se pide el código de la sede a eliminar
        int codigoEliminar = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código de la sede a eliminar"));

        // Crear un filtro de eliminación para buscar por el código de la sede
        Bson filtro = Filters.eq("cod_sede", codigoEliminar);

        // Eliminar la sede utilizando el filtro
        DeleteResult resultado = sedesCollection.deleteOne(filtro);

        // Verificar si se eliminó correctamente la sede
        if (resultado.getDeletedCount() > 0) {
            JOptionPane.showMessageDialog(null, "La sede se eliminó correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró una sede con el código especificado.");
        }
    }
}
