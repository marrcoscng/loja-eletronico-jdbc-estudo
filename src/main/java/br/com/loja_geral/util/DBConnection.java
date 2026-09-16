package br.com.loja_geral.util;

import br.com.loja_geral.exception.DBException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;


public class DBConnection {


    /**
     * @throws DBException;
     */
    public static Connection getConnection(){
            try {
                Properties prop = loadProperties();
                String url = prop.getProperty("url");
                return DriverManager.getConnection(url, prop);
            }catch(SQLException e){
                throw new DBException("Erro ao tentar conectar com o banco de dados - "+e.getMessage());
            }
    }


    /**
     * @throws DBException;
     */
    private static Properties loadProperties(){
        try(FileInputStream fs = new FileInputStream("db.properties")){
            Properties prop = new Properties();
            prop.load(fs);
            return prop;
        }catch(IOException e){
            throw new DBException("Erro ao carregar arquivo .properties "+e.getMessage());
        }
    }

}
