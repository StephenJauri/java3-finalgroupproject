package com.armanjaurigue.dataaccess.database;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static String connectionString;
    private static String dbUser;
    private static String dbPassword;
    static {
        Dotenv dotEnv = Dotenv.load();
        String db_driver = dotEnv.get("DB_DRIVER");
        String db_host = dotEnv.get("DB_HOST");
        String db_server = dotEnv.get("DB_SERVER_NAME");
        String db_port = dotEnv.get("DB_PORT");
        String db_schema = dotEnv.get("DB_SCHEMA");
        dbUser = dotEnv.get("DB_USER");
        dbPassword = dotEnv.get("DB_PASSWORD");
        String db_properties = dotEnv.get("DB_PROPERTIES");
        connectionString = String.format("jdbc:%s://%s.%s:%s/%s?%s",db_driver,db_server,db_host, db_port,db_schema,db_properties);
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(connectionString, dbUser, dbPassword);
        }
        catch (ClassNotFoundException ex)
        {
            conn = null;
            System.out.println(ex.getMessage());
        }
        catch (SQLException ex)
        {
            throw ex;
        }
        return conn;
    }
}
