package org.example;

import static com.mongodb.client.model.Filters.eq;
import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import javax.swing.*;


public class Main {
    public static void main(String[] args) {

        Menu();

        }

    public static void Menu() {
        int op;

        do {

         op = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese una opcion\n\n" +
                "1. Departamentos\n" +
                "2. Ciudades\n" +
                "3. Sedes\n" +
                "4. Contratos\n" +
                "5. Empleados\n" +
                "0. salir"));



            switch (op) {

                case 1:
                    OpcionDepartamentos();
                    break;
                case 2:
                    OpcionCiudades();
                    break;
                case 3:
                    OpcionSedes();
                    break;
                case 4:
                    OpcionContratos();
                    break;
                case 5:
                    OpcionEmpleados();
                    break;
                case 0:
                    JOptionPane.showMessageDialog(null, "Adios!");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción inválida. Por favor ingrese una opción válida.");

            }

        } while (op != 0);
    }

    public static void OpcionDepartamentos() {

        int opcion = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese una accion para departamentos\n\n" +
                "1. Ingresar\n" +
                "2. Consultar\n" +
                "3. Modificar\n" +
                "4. Eliminar\n" +
                "0. Regresar\n"));

        switch (opcion) {
            case 1:
                ManejadorDepartamentos.ingresarDepartamento();
                break;
            case 2:
                ManejadorDepartamentos.consultarDepartamento();
                break;
            case 3:
                ManejadorDepartamentos.modificarDepartamento();
                break;
            case 4:
                ManejadorDepartamentos.eliminarDepartamento();
                break;
            case 0:
                Menu();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Opción inválida. Por favor ingrese una opción válida.");
        }
    }

    public static void OpcionCiudades() {

        int opcion = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese una accion para cuidades\n\n" +
                "1. Ingresar\n" +
                "2. Consultar\n" +
                "3. Modificar\n" +
                "4. Eliminar\n" +
                "0. Regresar\n"));

        switch (opcion) {
            case 1:
                ManejadorCiudades.ingresarCiudad();
                break;
            case 2:
                ManejadorCiudades.consultarCiudad();
                break;
            case 3:
                ManejadorCiudades.modificarCiudad();
                break;
            case 4:
                ManejadorCiudades.eliminarCiudad();
                break;
            case 0:
                Menu();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Opción inválida. Por favor ingrese una opción válida.");
        }
    }

    public static void OpcionSedes() {

        int opcion = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese accion una para sedes\n\n" +
                "1. Ingresar\n" +
                "2. Consultar\n" +
                "3. Modificar\n" +
                "4. Eliminar\n" +
                "0. Regresar\n"));

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
                Menu();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Opción inválida. Por favor ingrese una opción válida.");
        }
    }

    public static void OpcionContratos() {

        int opcion = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese una accion para contratos\n\n" +
                "1. Ingresar\n" +
                "2. Consultar\n" +
                "3. Modificar\n" +
                "4. Eliminar\n" +
                "0. Regresar\n"));


        switch (opcion) {
            case 1:
                ManejadorContratos.ingresarContrato();
                break;
            case 2:
                ManejadorContratos.consultarContrato();
                break;
            case 3:
                ManejadorContratos.modificarContrato();
                break;
            case 4:
                ManejadorContratos.eliminarContrato();
                break;
            case 0:
                Menu();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Opción inválida. Por favor ingrese una opción válida.");
        }

    }

    public static void OpcionEmpleados() {

        int opcion = Integer.parseInt(JOptionPane.showInputDialog(null, "Por favor ingrese accion una para empleados\n\n" +
                "1. Ingresar\n" +
                "2. Consultar\n" +
                "3. Modificar\n" +
                "4. Eliminar\n" +
                "0. Regresar\n"));

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
                Menu();
                break;
            default:
                JOptionPane.showMessageDialog(null, "Opción inválida. Por favor ingrese una opción válida.");
        }
    }





    }

