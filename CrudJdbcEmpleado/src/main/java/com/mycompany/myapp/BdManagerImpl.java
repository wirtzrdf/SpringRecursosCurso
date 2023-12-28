package com.mycompany.myapp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class BdManagerImpl implements BdManager {

    private final String URL = "jdbc:sqlite:archivos/empleados.db";

    public int vaciarTabla() throws SQLException {
        int cantFilas = 0;
        try (Connection connection = DriverManager.getConnection(URL)) {
            PreparedStatement ps = connection.prepareStatement("DELETE FROM empleados");
            cantFilas = ps.executeUpdate();
            ps.close();
        }
        return cantFilas;
    }

    public int cargaInicial(ArrayList<Empleado> empleados) throws SQLException {

    }

    public ArrayList<Empleado> consultar() throws SQLException {

    }

    public ArrayList<Empleado> consultar(double minSalar, double maxSalar) throws SQLException {

    }

    public int insertar(Empleado empleado) throws SQLException {

    }

    public int borrar(Long id) throws SQLException {

    }

    public int actualizar(Empleado empleado) throws SQLException {

    }
}
