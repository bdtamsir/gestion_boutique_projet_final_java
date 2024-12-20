package gestion_boutique.core.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseImpl implements Database{

    private static final String url="jdbc:mysql://localhost:8889/gestion_shop";
    private static final String username="root";
    private static final String password="root";

    protected Connection connection;
    protected Statement statement;
    protected String query;

    @Override
    public void openConnetion() {
      try {
        DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        connection = DriverManager.getConnection(url,username,password);
      } catch (SQLException e) {
          e.printStackTrace();
      }
    }

    @Override
    public void closeConnection() {
      try {
         if (connection!=null && !connection.isClosed()) {
          connection.close();
         }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    @Override
    public ResultSet executeQuery(String query)throws SQLException {
           initCreateStatement(query);
           return statement.executeQuery(query);     
    }

    @Override
    public int executeUpdate(String query) throws SQLException{  
           initCreateStatement(query);
           int line= statement.executeUpdate(query);
           this.closeConnection();
           return line;
    }

    @Override
    public void initCreateStatement(String query) throws SQLException {
        this.openConnetion();
        statement = connection.createStatement();
    }

   

    
}
