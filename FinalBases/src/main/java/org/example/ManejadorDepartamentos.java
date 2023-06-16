package org.example;
//MONGO

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

//POSTGRESQL

import java.sql.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;



public class ManejadorDepartamentos {

    public static void ingresarDepartamento() {

        //MONGO
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

        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);

            // Ejemplo de inserción en la tabla DEPARTAMENTOS
            String sqlDepartamentos = "INSERT INTO DEPARTAMENTOS (COD_DEPARTAMENTO, NOM_DEPARTAMENTO) VALUES (?, ?)";
            PreparedStatement declaraciónDepartamentos = conexión.prepareStatement(sqlDepartamentos);
            declaraciónDepartamentos.setInt(1, codigo); // Valor para COD_DEPARTAMENTO
            declaraciónDepartamentos.setString(2, nombre); // Valor para NOM_DEPARTAMENTO
            declaraciónDepartamentos.executeUpdate();

            // Realiza inserciones similares para las otras tablas

            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

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



        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);

            // Consulta SQL para obtener los departamentos
            String sql = "SELECT COD_DEPARTAMENTO, NOM_DEPARTAMENTO FROM DEPARTAMENTOS";
            PreparedStatement declaración = conexión.prepareStatement(sql);

            ResultSet resultado = declaración.executeQuery();

            // Recorre los resultados e imprime los datos de los departamentos
            while (resultado.next()) {
                int codDepartamento = resultado.getInt("COD_DEPARTAMENTO");
                String nomDepartamento = resultado.getString("NOM_DEPARTAMENTO");
                System.out.println("POSTGRESQL");
                System.out.println("Código: " + codDepartamento + ", Nombre: " + nomDepartamento);
            }


        } catch (SQLException e) {
            e.printStackTrace();
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

        // Se pide el código del departamento a modificar
        int codigoModificar = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese el código del departamento a modificar"));

        // Crear un filtro de búsqueda para encontrar el departamento a modificar
        Bson filtro = Filters.eq("cod_departamento", codigoModificar);

        // Se piden los nuevos datos del departamento
        String nuevoNombre = JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo nombre del departamento");

        // Crear un documento con los nuevos datos del departamento
        Document datosActualizados = new Document("$set", new Document("nom_departamento", nuevoNombre));

        // Actualizar el departamento utilizando el filtro y los nuevos datos
        UpdateResult resultado = departamentosCollection.updateOne(filtro, datosActualizados);

        // Verificar si se realizó la modificación correctamente
        if (resultado.getModifiedCount() > 0) {
            JOptionPane.showMessageDialog(null, "El departamento se modificó correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un departamento con el código especificado.");
        }


        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);

            // Consulta SQL de actualización de departamento
            String sql = "UPDATE DEPARTAMENTOS SET NOM_DEPARTAMENTO = ? WHERE COD_DEPARTAMENTO = ?";
            PreparedStatement declaración = conexión.prepareStatement(sql);

            // Valores para la actualización
            declaración.setString(1, nuevoNombre); // Nuevo nombre del departamento
            declaración.setInt(2, codigoModificar); // Código del departamento a actualizar

            int filasActualizadas = declaración.executeUpdate();
            if (filasActualizadas > 0) {
                System.out.println("Departamento actualizado correctamente.");
            } else {
                System.out.println("No se encontró el departamento a actualizar.");
            }

            declaración.close();
            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
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



        //POSTGRESQL
        String url = "jdbc:postgresql://localhost:5432/Eventos";
        String usuario = "postgres";
        String contraseña = "admin123";

        try {
            Connection conexión = DriverManager.getConnection(url, usuario, contraseña);

            // Consulta SQL de eliminación de departamento
            String sql = "DELETE FROM DEPARTAMENTOS WHERE COD_DEPARTAMENTO = ?";
            PreparedStatement declaración = conexión.prepareStatement(sql);

            // Valor para la eliminación
            declaración.setInt(1, codigoEliminar); // Código del departamento a eliminar

            int filasEliminadas = declaración.executeUpdate();
            if (filasEliminadas > 0) {
                JOptionPane.showMessageDialog(null, "El departamento se eliminó correctamente de postgres.");

                //Borrado mongo

                // Crear un filtro de eliminación para buscar por el código de la departamento
                Bson filtro = Filters.eq("cod_departamento", codigoEliminar);

                // Eliminar el departamento utilizando el filtro
                DeleteResult resultado = departamentosCollection.deleteOne(filtro);

                // Verificar si se eliminó correctamente el departamento
                if (resultado.getDeletedCount() > 0) {
                    JOptionPane.showMessageDialog(null, "El departamento se eliminó correctamente de mongo.");
                } else {
                    JOptionPane.showMessageDialog(null, "No se encontró una departamento con el código especificado.");
                }
            } else {
                System.out.println("No se encontró el departamento a eliminar.");
            }

            declaración.close();
            conexión.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
