package com.template;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;

public class Conexao {
    static String conexao = "jdbc:postgresql://localhost:5432/db_crud";
    static String usuario = "postgres";
    static String senha = "postgres";

    public static Connection conectaBD() throws SQLException
    {
        return DriverManager.getConnection(conexao, usuario, senha);
    }
}
