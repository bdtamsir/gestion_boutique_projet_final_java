package gestion_boutique.repository.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import gestion_boutique.core.bd.DatabaseImpl;
import gestion_boutique.entite.Role;
import gestion_boutique.repository.RoleRepository;

public class RoleRepositoryBd extends DatabaseImpl implements RoleRepository{

   
    @Override
    public boolean insert(Role role) {
        int line = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion_shop", "root", "root");
            String query = String.format("INSERT INTO `role` (`nom_role`) VALUES ('%s')", role.getNom_role());
            Statement statement = conn.createStatement();
            line = statement.executeUpdate(query);
        } catch (ClassNotFoundException e) {
            System.err.println("Driver non chargé !");
        } catch (SQLException e) {
            System.out.println("Connection Error");
        }
        return line != 0;
    }

       

    @Override
    public List<Role> selectAll() {
        List<Role> roles = new ArrayList<>();
        try {
            String query = "SELECT * FROM `role`";
            ResultSet rs= this.executeQuery(query);
            while (rs.next()) {
                int id = rs.getInt("id");
                String nomRole = rs.getString("nom_role");
                Role role = new Role(id, nomRole);
                roles.add(role);
            }
        } catch (SQLException e) {
            System.out.println("Connection Error");
        }finally{
            this.closeConnection();
        }
        return roles;
    }
    

    public Role selectById(int id) {
    Role role = null;
    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion_shop", "root", "root");
        String query = "SELECT * FROM `role` WHERE `id` = ?";
        PreparedStatement preparedStatement = conn.prepareStatement(query);
        preparedStatement.setInt(1, id);
        ResultSet rs = preparedStatement.executeQuery();
        if (rs.next()) {
            String nomRole = rs.getString("nom_role");
            role = new Role(id, nomRole);
        }
    } catch (ClassNotFoundException e) {
        System.err.println("Driver non chargé !");
    } catch (SQLException e) {
        System.out.println("Connection Error");
    }
    return role;
}

}

