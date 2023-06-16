package org.example;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class ManejadorCiudades {

    public static void ingresarCiudad() {
        String uri = "mongodb+srv://admin:admin123@cluster0.jijwz3h.mongodb.net/?retryWrites=true&w=majority";

        // Crear una instancia de MongoClient
        MongoClient mongoClient = MongoClients.create(uri);

        // Obtener la base de datos
        MongoDatabase database = mongoClient.getDatabase("Eventos");

        // Obtener la colección "ciudades"
        MongoCollection<Document> ciudadesCollection = database.getCollection("ciudades");

        // Se piden los datos a ingresar
        int codigo = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el codigo de la ciudad"));
        String nombre = JOptionPane.showInputDialog(null, "Por favor ingrese el nombre de la ciudad");
        int codigoFk = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el codigo del departamento"));

        // Crear un nuevo documento
        Document ciudad = new Document("cod_ciudad", codigo)
                .append("nom_ciudad", nombre)
                .append("cod_departamento", codigoFk);

        // Insertar el documento en la colección
        ciudadesCollection.insertOne(ciudad);

        // Cerrar la conexión
        mongoClient.close();
    }

    public static void consultarCiudad() {
        String uri = "mongodb+srv://admin:admin123@cluster0.jijwz3h.mongodb.net/?retryWrites=true&w=majority";

        // Crear una instancia de MongoClient
        MongoClient mongoClient = MongoClients.create(uri);

        // Obtener la base de datos
        MongoDatabase database = mongoClient.getDatabase("Eventos");

        // Obtener la colección "ciudades"
        MongoCollection<Document> ciudadesCollection = database.getCollection("ciudades");

        // Se pide el código de la ciudad a consultar
        int codigoConsulta = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código de la ciudad a consultar"));

        // Crear un filtro de consulta para buscar por el código de la ciudad
        Bson filtro = Filters.eq("cod_ciudad", codigoConsulta);

        // Realizar la consulta utilizando el filtro
        FindIterable<Document> ciudadesConsulta = ciudadesCollection.find(filtro);

        // Crear una lista para almacenar los nombres de las ciudades encontradas
        List<String> nombresCiudadesConsulta = new ArrayList<>();

        // Iterar sobre las ciudades encontradas y obtener el nombre de cada una
        for (Document ciudad : ciudadesConsulta) {
            String nombreCiudad = ciudad.getString("nom_ciudad");
            nombresCiudadesConsulta.add(nombreCiudad);
        }

        // Mostrar los nombres de las ciudades encontradas en un cuadro de diálogo
        if (!nombresCiudadesConsulta.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Ciudad encontrada: " + nombresCiudadesConsulta);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron ciudades con el código especificado.");
        }

    }

    public static void modificarCiudad() {

        String uri = "mongodb+srv://admin:admin123@cluster0.jijwz3h.mongodb.net/?retryWrites=true&w=majority";

        // Crear una instancia de MongoClient
        MongoClient mongoClient = MongoClients.create(uri);

        // Obtener la base de datos
        MongoDatabase database = mongoClient.getDatabase("Eventos");


        // Obtener la colección "Ciudades"
        MongoCollection<Document> ciudadesCollection = database.getCollection("ciudades");

        // Se pide el código de la ciudad a modificar
        int codigoModificar = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código de la ciudad a modificar"));

        // Crear un filtro de búsqueda para encontrar la ciudad a modificar
        Bson filtro = Filters.eq("cod_ciudad", codigoModificar);

        // Se piden los nuevos datos de la ciudad
        String nuevoNombre = JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo nombre de la ciudad");
        int nuevoCodigoDepartamento = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo código del departamento"));

        // Crear un documento con los nuevos datos de la ciudad
        Document datosActualizados = new Document("$set", new Document("nom_ciudad", nuevoNombre).append("cod_departamento", nuevoCodigoDepartamento));

        // Actualizar la ciudad utilizando el filtro y los nuevos datos
        UpdateResult resultado = ciudadesCollection.updateOne(filtro, datosActualizados);

        // Verificar si se realizó la modificación correctamente
        if (resultado.getModifiedCount() > 0) {
            JOptionPane.showMessageDialog(null, "La ciudad se modificó correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró una ciudad con el código especificado.");
        }


    }

    public static void eliminarCiudad() {

        String uri = "mongodb+srv://admin:admin123@cluster0.jijwz3h.mongodb.net/?retryWrites=true&w=majority";

        // Crear una instancia de MongoClient
        MongoClient mongoClient = MongoClients.create(uri);

        // Obtener la base de datos
        MongoDatabase database = mongoClient.getDatabase("Eventos");


        // Obtener la colección "Ciudades"
        MongoCollection<Document> ciudadesCollection = database.getCollection("ciudades");

        // Se pide el código de la ciudad a eliminar
        int codigoEliminar = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código de la ciudad a eliminar"));

        // Crear un filtro de eliminación para buscar por el código de la cuidad
        Bson filtro = Filters.eq("cod_ciudad", codigoEliminar);

        // Eliminar la ciudad utilizando el filtro
        DeleteResult resultado = ciudadesCollection.deleteOne(filtro);

        // Verificar si se eliminó correctamente la cuidad
        if (resultado.getDeletedCount() > 0) {
            JOptionPane.showMessageDialog(null, "La ciudad se eliminó correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró una ciudad con el código especificado.");
        }
    }
}
