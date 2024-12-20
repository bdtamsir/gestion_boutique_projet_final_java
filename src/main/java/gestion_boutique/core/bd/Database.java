package gestion_boutique.core.bd;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Database {
    void openConnetion();
    void closeConnection();
    ResultSet executeQuery(String query)throws SQLException;
    int executeUpdate(String query)throws SQLException;
    void initCreateStatement(String query) throws SQLException;
}
