package org.example;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import org.bson.Document;
import org.bson.conversions.Bson;


import javax.swing.*;
import java.util.ArrayList;
import java.util.List;



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
        String uri = "mongodb+srv://admin:admin123@cluster0.jijwz3h.mongodb.net/?retryWrites=true&w=majority";

        // Crear una instancia de MongoClient
        MongoClient mongoClient = MongoClients.create(uri);

        // Obtener la base de datos
        MongoDatabase database = mongoClient.getDatabase("Eventos");

        // Obtener la colección "departamentos"
        MongoCollection<Document> departamentosCollection = database.getCollection("departamentos");

        // Se pide el código de la departamento a consultar
        int codigoConsulta = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código de la departamento a consultar"));

        // Crear un filtro de consulta para buscar por el código de la departamento
        Bson filtro = Filters.eq("cod_departamento", codigoConsulta);

        // Realizar la consulta utilizando el filtro
        FindIterable<Document> departamentosConsulta = departamentosCollection.find(filtro);

        // Crear una lista para almacenar los nombres de las departamentos encontradas
        List<String> nombresDepartamentosConsulta = new ArrayList<>();

        // Iterar sobre las departamentos encontradas y obtener el nombre de cada una
        for (Document departamento : departamentosConsulta) {
            String nombreDepartamento = departamento.getString("nom_departamento");
            nombresDepartamentosConsulta.add(nombreDepartamento);
        }

        // Mostrar los nombres de las departamentos encontradas en un cuadro de diálogo
        if (!nombresDepartamentosConsulta.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Departamento encontrado: " + nombresDepartamentosConsulta);
        } else {
            JOptionPane.showMessageDialog(null, "No se encontraron departamentos con el código especificado.");
        }

    }

    public static void modificarDepartamento() {

        String uri = "mongodb+srv://admin:admin123@cluster0.jijwz3h.mongodb.net/?retryWrites=true&w=majority";

        // Crear una instancia de MongoClient
        MongoClient mongoClient = MongoClients.create(uri);

        // Obtener la base de datos
        MongoDatabase database = mongoClient.getDatabase("Eventos");

        // Obtener la colección "departamentos"
        MongoCollection<Document> departamentosCollection = database.getCollection("departamentos");

        // Se pide el código de el departamento a eliminar
        int codigoEliminar = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código del departamento a eliminar"));

        // Crear un filtro de eliminación para buscar por el código de la departamento
        Bson filtro = Filters.eq("cod_departamento", codigoEliminar);

        // Eliminar el departamento utilizando el filtro
        DeleteResult resultado = departamentosCollection.deleteOne(filtro);

        // Verificar si se eliminó correctamente el departamento
        if (resultado.getDeletedCount() > 0) {
            JOptionPane.showMessageDialog(null, "El departamento se eliminó correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró una departamento con el código especificado.");
        }

    }

    public static void eliminarDepartamento() {

        String uri = "mongodb+srv://admin:admin123@cluster0.jijwz3h.mongodb.net/?retryWrites=true&w=majority";

        // Crear una instancia de MongoClient
        MongoClient mongoClient = MongoClients.create(uri);

        // Obtener la base de datos
        MongoDatabase database = mongoClient.getDatabase("Eventos");

        // Obtener la colección "departamentos"
        MongoCollection<Document> departamentosCollection = database.getCollection("departamentos");

        // Se pide el código de el departamento a eliminar
        int codigoEliminar = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código del departamento a eliminar"));

        // Crear un filtro de eliminación para buscar por el código de la departamento
        Bson filtro = Filters.eq("cod_departamento", codigoEliminar);

        // Eliminar el departamento utilizando el filtro
        DeleteResult resultado = departamentosCollection.deleteOne(filtro);

        // Verificar si se eliminó correctamente el departamento
        if (resultado.getDeletedCount() > 0) {
            JOptionPane.showMessageDialog(null, "El departamento se eliminó correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró una departamento con el código especificado.");
        }

    }
}
