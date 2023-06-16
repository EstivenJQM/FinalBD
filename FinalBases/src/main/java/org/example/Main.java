package org.example;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import javax.swing.*;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {

        Menu();

        }

    public static void Menu() {

        int opcion = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese una opcion\n\n" +
                "1. Departamentos\n" +
                "2. Cuidades\n" +
                "3. Sedes\n" +
                "4. Empleados\n" +
                "0. salir"));

        do {
            switch (opcion) {

                case 1:
                    OpcionDepartamentos();
                    break;
                case 2:
                    OpcionCuidades();
                    break;
                case 3:
                    OpcionSedes();
                    break;
                case 4:
                    OpcionEmpleados();
                    break;
            }

        } while (opcion != 0);
    }

    public static void OpcionDepartamentos() {

        int opcion = Integer.parseInt(JOptionPane.showInputDialog(null, "Po00r favor ingrese una accion para departamentos\n\n" +
                "1. Ingresar\n" +
                "2. Consultar\n" +
                "3. Modificar\n" +
                "4. Eliminar\n" +
                "0. salir"));

        switch (opcion) {
            case 1:
                ManejadorDepartamentos.ingresarDepartamento();
                break;
            case 2:

                break;
            case 3:

                break;
            case 4:

                break;
            case 0:
                break;
        }
    }

    public static void OpcionCuidades() {

        int opcion = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese una accion para cuidades\n\n" +
                "1. Ingresar\n" +
                "2. Consultar\n" +
                "3. Modificar\n" +
                "4. Eliminar\n" +
                "0. salir"));

        switch (opcion) {
            case 1:
                ManejadorCuidades.ingresarCuidad();
                break;
            case 2:
                ManejadorCuidades.consultarCuidad();
                break;
            case 3:
                ManejadorCuidades.modificarCuidad();
                break;
            case 4:
                ManejadorCuidades.eliminarCuidad();
                break;
            case 0:
                break;
        }
    }

    public static void OpcionSedes() {

        int opcion = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese accion una para sedes\n\n" +
                "1. Ingresar\n" +
                "2. Consultar\n" +
                "3. Modificar\n" +
                "4. Eliminar\n" +
                "0. salir"));

        switch (opcion) {
            case 1:
                ManejadorSedes.ingresarSede();
                break;
            case 2:
                ManejadorSedes.consultarSede();
                break;
            case 3:
                ManejadorSedes.modificarSede();
                break;
            case 4:
                ManejadorSedes.eliminarSede();
                break;
            case 0:
                break;
        }
    }

    public static void OpcionEmpleados() {

        int opcion = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese accion una para empleados\n\n" +
                "1. Ingresar\n" +
                "2. Consultar\n" +
                "3. Modificar\n" +
                "4. Eliminar\n" +
                "0. salir"));

        switch (opcion) {
            case 1:
                ManejadorEmpleados.ingresarEmpleado();
                break;
            case 2:
                ManejadorEmpleados.consultarEmpleado();
                break;
            case 3:
                ManejadorEmpleados.modificarEmpleado();
                break;
            case 4:
                ManejadorEmpleados.eliminarEmpleado();
                break;
            case 0:
                break;
        }
    }



    }

