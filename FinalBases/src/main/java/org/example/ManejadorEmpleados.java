package org.example;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;



public class ManejadorEmpleados {

    public static void ingresarEmpleado() {

        String urlPostgreSQL = "jdbc:postgresql://localhost:5432/Eventos";
        String usuarioPostgreSQL = "postgres";
        String contraseñaPostgreSQL = "admin123";
        String uriMongoDB = "mongodb+srv://admin:admin123@cluster0.jijwz3h.mongodb.net/?retryWrites=true&w=majority";

        try {
            // Conexión a PostgreSQL
            Connection conexiónPostgreSQL = DriverManager.getConnection(urlPostgreSQL, usuarioPostgreSQL,
                    contraseñaPostgreSQL);

            // Consultas SQL para obtener las ciudades, sedes y contratos
            String sqlCiudades = "SELECT NOM_CIUDAD FROM CIUDADES";
            String sqlSedes = "SELECT NOM_SEDE FROM SEDES";
            String sqlContratos = "SELECT NOM_CONTRATO FROM CONTRATOS";
            PreparedStatement declaraciónCiudades = conexiónPostgreSQL.prepareStatement(sqlCiudades);
            PreparedStatement declaraciónSedes = conexiónPostgreSQL.prepareStatement(sqlSedes);
            PreparedStatement declaraciónContratos = conexiónPostgreSQL.prepareStatement(sqlContratos);
            ResultSet resultadoCiudades = declaraciónCiudades.executeQuery();
            ResultSet resultadoSedes = declaraciónSedes.executeQuery();
            ResultSet resultadoContratos = declaraciónContratos.executeQuery();

            // Crear arrays de strings para almacenar las opciones
            List<String> ciudades = new ArrayList<>();
            List<String> sedes = new ArrayList<>();
            List<String> contratos = new ArrayList<>();

            // Agregar las ciudades al array de ciudades
            while (resultadoCiudades.next()) {
                String nombreCiudad = resultadoCiudades.getString("NOM_CIUDAD");
                ciudades.add(nombreCiudad);
            }

            // Agregar las sedes al array de sedes
            while (resultadoSedes.next()) {
                String nombreSede = resultadoSedes.getString("NOM_SEDE");
                sedes.add(nombreSede);
            }

            // Agregar los contratos al array de contratos
            while (resultadoContratos.next()) {
                String nombreContrato = resultadoContratos.getString("NOM_CONTRATO");
                contratos.add(nombreContrato);
            }

            // Cerrar conexiones y declaraciones de PostgreSQL
            resultadoCiudades.close();
            resultadoSedes.close();
            resultadoContratos.close();
            declaraciónCiudades.close();
            declaraciónSedes.close();
            declaraciónContratos.close();
            conexiónPostgreSQL.close();

            // Cuadros de diálogo para ingresar los datos
            String id = JOptionPane.showInputDialog(null, "Ingrese el ID:");
            String nombre = JOptionPane.showInputDialog(null, "Ingrese el nombre:");
            String apellido = JOptionPane.showInputDialog(null, "Ingrese el apellido:");
            String email = JOptionPane.showInputDialog(null, "Ingrese el email:");

            String sedeSeleccionada = (String) JOptionPane.showInputDialog(null, "Seleccione la sede:",
                    "Selección de Sede", JOptionPane.PLAIN_MESSAGE, null, sedes.toArray(), null);

            String ciudadSeleccionada = (String) JOptionPane.showInputDialog(null, "Seleccione la ciudad:",
                    "Selección de Ciudad", JOptionPane.PLAIN_MESSAGE, null, ciudades.toArray(), null);

            String contratoSeleccionado = (String) JOptionPane.showInputDialog(null, "Seleccione el tipo de contrato:",
                    "Selección de Tipo de Contrato", JOptionPane.PLAIN_MESSAGE, null, contratos.toArray(), null);

            // Conexión a MongoDB
            MongoClient mongoClient = MongoClients.create(uriMongoDB);
            MongoDatabase database = mongoClient.getDatabase("Eventos");
            MongoCollection<Document> collection = database.getCollection("empleados");

            // Crear un documento de MongoDB con los datos del empleado
            Document documento = new Document("id", id)
                    .append("nombre", nombre)
                    .append("apellido", apellido)
                    .append("email", email)
                    .append("sede", sedeSeleccionada)
                    .append("ciudad", ciudadSeleccionada)
                    .append("tipo_contrato", contratoSeleccionado);

            // Insertar el documento en la colección de MongoDB
            collection.insertOne(documento);

            // Cerrar conexión de MongoDB
            mongoClient.close();

            JOptionPane.showMessageDialog(null, "Empleado guardado en MongoDB.");
        } catch (SQLException e) {
            e.printStackTrace();
        }

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
        Bson filtro = Filters.eq("id", codigoConsulta);

        // Realizar la consulta utilizando el filtro
        FindIterable<Document> empleadosConsulta = empleadosCollection.find(filtro);

        // Crear una lista para almacenar los nombres de las empleados encontradas
        List<String> nombresEmpleadosConsulta = new ArrayList<>();

        // Iterar sobre los empleados encontrados y obtener los datos de cada uno
        for (Document empleado : empleadosConsulta) {
            String idEmpleado = empleado.getString("id");
            String nombreEmpleado = empleado.getString("nombre");
            String apellidoEmpleado = empleado.getString("apellido");
            String correoEmpleado = empleado.getString("email");
            String ciudadEmpleado = empleado.getString("ciudad");
            String sedeEmpleado = empleado.getString("sede");
            String contratoEmpleado = empleado.getString("tipo_contrato");

            // Agregar los datos del empleado a la lista
            nombresEmpleadosConsulta.add("ID: " + idEmpleado + "\n" +
                    "Nombre: " + nombreEmpleado + "\n" +
                    "Apellido: " + apellidoEmpleado + "\n" +
                    "Correo: " + correoEmpleado + "\n" +
                    "Ciudad: " + ciudadEmpleado + "\n" +
                    "Sede: " + sedeEmpleado + "\n" +
                    "Tipo contrato: " + contratoEmpleado);
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

        /*// Obtener de postgresql

        // Conexión a PostgreSQL
        String urlPostgreSQL = "jdbc:postgresql://localhost:5432/Eventos";
        String usuarioPostgreSQL = "postgres";
        String contraseñaPostgreSQL = "admin123";

        Connection conexiónPostgreSQL = DriverManager.getConnection(urlPostgreSQL, usuarioPostgreSQL, contraseñaPostgreSQL);

        // Consultas SQL para obtener los valores de sede, ciudad y tipo de contrato
        String sqlSedes = "SELECT NOM_SEDE FROM SEDES";
        String sqlCiudades = "SELECT NOM_CIUDAD FROM CIUDADES";
        String sqlTiposContrato = "SELECT NOM_CONTRATO FROM CONTRATOS";

        PreparedStatement declaraciónSedes = conexiónPostgreSQL.prepareStatement(sqlSedes);
        PreparedStatement declaraciónCiudades = conexiónPostgreSQL.prepareStatement(sqlCiudades);
        PreparedStatement declaraciónTiposContrato = conexiónPostgreSQL.prepareStatement(sqlTiposContrato);

        ResultSet resultadoSedes = declaraciónSedes.executeQuery();
        ResultSet resultadoCiudades = declaraciónCiudades.executeQuery();
        ResultSet resultadoTiposContrato = declaraciónTiposContrato.executeQuery();

        // Obtener los valores de sede, ciudad y tipo de contrato y almacenarlos en listas
        List<String> sedes = new ArrayList<>();
        List<String> ciudades = new ArrayList<>();
        List<String> tiposContrato = new ArrayList<>();

        while (resultadoSedes.next()) {
            String nombreSede = resultadoSedes.getString("NOM_SEDE");
            sedes.add(nombreSede);
        }

        while (resultadoCiudades.next()) {
            String nombreCiudad = resultadoCiudades.getString("NOM_CIUDAD");
            ciudades.add(nombreCiudad);
        }

        while (resultadoTiposContrato.next()) {
            String nombreContrato = resultadoTiposContrato.getString("NOM_CONTRATO");
            tiposContrato.add(nombreContrato);
        }

        // Cerrar conexiones y declaraciones de PostgreSQL
        resultadoSedes.close();
        resultadoCiudades.close();
        resultadoTiposContrato.close();
        declaraciónSedes.close();
        declaraciónCiudades.close();
        declaraciónTiposContrato.close();
        conexiónPostgreSQL.close();*/

        // Se pide la identificación del empleado a modificar
        String identificacionModificar = JOptionPane.showInputDialog(null, "Por favor ingrese la identificación del empleado a modificar");

        // Crear un filtro de búsqueda para encontrar el empleado a modificar
        Bson filtro = Filters.eq("id", identificacionModificar);

        // Se piden los nuevos datos del empleado
        String nuevoNombre = JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo nombre del empleado");
        String nuevoApellido = JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo apellido del empleado");
        String nuevoEmail = JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo email del empleado");
        String nuevoSede = JOptionPane.showInputDialog(null, "Por favor ingrese la nueva sede");
        String nuevoCiudad = JOptionPane.showInputDialog(null, "Por favor ingrese la nueva ciudad");
        String nuevoContrato = JOptionPane.showInputDialog(null, "Por favor ingrese el nuevo tipo de contrato");

        // Crear un documento con los nuevos datos del empleado
        Document datosActualizados = new Document("$set", new Document("nombre", nuevoNombre)
                .append("apellido", nuevoApellido)
                .append("email", nuevoEmail)
                .append("sede", nuevoSede)
                .append("ciudad", nuevoCiudad)
                .append("tipo_contrato", nuevoContrato));

        // Actualizar el empleado utilizando el filtro y los nuevos datos
        UpdateResult resultado = empleadosCollection.updateOne(filtro, datosActualizados);

        // Verificar si se realizó la modificación correctamente
        if (resultado.getModifiedCount() > 0) {
            JOptionPane.showMessageDialog(null, "El empleado se modificó correctamente.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró un empleado con la identificación especificada.");
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
        Bson filtro = Filters.eq("id", codigoEliminar);

        // Eliminar la empleado utilizando el filtro
        DeleteResult resultado = empleadosCollection.deleteOne(filtro);

        // Verificar si se eliminó correctamente el empleado
        if (resultado.getDeletedCount() > 0) {
            JOptionPane.showMessageDialog(null, "El empleado se eliminó correctamente de mongo.");
        } else {
            JOptionPane.showMessageDialog(null, "No se encontró una empleado con el código especificado.");
        }

    }
}
