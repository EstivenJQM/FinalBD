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

        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);

            // Ejemplo de inserción en la tabla SEDES
            String sqlSedes = "INSERT INTO SEDES (COD_SEDE, NOM_SEDE, COD_CIUDAD) VALUES (?, ?, ?)";
            PreparedStatement declaraciónSedes = conexión.prepareStatement(sqlSedes);
            declaraciónSedes.setInt(1, codigo); // Valor para COD_SEDE
            declaraciónSedes.setString(2, nombre); // Valor para NOM_SEDE
            declaraciónSedes.setInt(3, codigoFk); // Valor para COD_CIUDAD
            declaraciónSedes.executeUpdate();

            // Realiza inserciones similares para las otras tablas

            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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

        String uri = "mongodb+srv://admin:admin123@cluster0.jijwz3h.mongodb.net/?retryWrites=true&w=majority";

        // Crear una instancia de MongoClient
        MongoClient mongoClient = MongoClients.create(uri);

        // Obtener la base de datos
        MongoDatabase database = mongoClient.getDatabase("Eventos");

        // Obtener la colección "Sedes"
        MongoCollection<Document> sedesCollection = database.getCollection("sedes");


        // Se pide el código de la sede a modificar
        int codigoModificar = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código de la sede a modificar"));

        // Crear un filtro de búsqueda para encontrar la sede a modificar
        Bson filtro = Filters.eq("cod_sede", codigoModificar);

        // Se piden los nuevos datos de la sede
        String nuevoNombre = JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo nombre de la sede");
        int nuevoCodigoCiudad = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo código de la ciudad"));

        // Crear un documento con los nuevos datos de la sede
        Document datosActualizados = new Document("$set", new Document("nom_sede", nuevoNombre).append("cod_ciudad", nuevoCodigoCiudad));

        // Actualizar la sede utilizando el filtro y los nuevos datos
        UpdateResult resultado = sedesCollection.updateOne(filtro, datosActualizados);

        // Verificar si se realizó la modificación correctamente
        if (resultado.getModifiedCount() > 0) {
            JOptionPane.showMessageDialog(null, "La sede se modificó correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró una sede con el código especificado.");
        }
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
