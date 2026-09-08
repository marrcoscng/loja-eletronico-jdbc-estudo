package br.com.loja_geral.model.entidade.util;

import br.com.loja_geral.exception.DBException;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


public class DBConnection {

    private static Connection conn = null;


    public static Connection getConnection(){
        if(conn == null){
            try {
                Properties prop = loadProperties();
                String url = prop.getProperty("url");
                conn = DriverManager.getConnection(url, prop);
            }catch(SQLException e){
                throw new DBException("Erro ao tentar conectar com o banco de dados - "+e.getMessage());
            }
        }
        return conn;
    }

    public static void closeConnection(Connection conn){
        if(conn!=null){
            try{
                conn.close();
            }catch(SQLException e){
                throw new DBException("Erro ao fechar acesso ao banco de dados - "+e.getMessage());
            }
        }
    }

    public static void closePreparedStatement(Statement st){
        if(st!=null){
            try{
                st.close();
            }catch(SQLException e){
                throw new DBException("Erro ao fechar ponte Statement - "+e.getMessage());
            }
        }
    }

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
