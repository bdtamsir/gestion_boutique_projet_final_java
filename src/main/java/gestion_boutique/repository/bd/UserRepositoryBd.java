package gestion_boutique.repository.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import gestion_boutique.core.bd.DatabaseImpl;
import gestion_boutique.entite.Role;
import gestion_boutique.entite.User;
import gestion_boutique.repository.UserRepository;

public class UserRepositoryBd extends DatabaseImpl  implements UserRepository{
    
    private final RoleRepositoryBd roleRepository; 

    // Constructeur
    public UserRepositoryBd() {
        this.roleRepository = new RoleRepositoryBd(); 
    }
    
    @Override
    public boolean insert(User user) {
        int line = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion_shop", "root", "root");
            String query = String.format(
                "INSERT INTO `user` (`email`, `login`, `password`, `role_id`) VALUES ('%s', '%s', '%s', %d)",
                user.getEmail(), user.getLogin(), user.getPassword(), user.getRole().getRole_id()
            );
            Statement statement = conn.createStatement();
            line = statement.executeUpdate(query, Statement.RETURN_GENERATED_KEYS);
            
            if (line > 0) {
                ResultSet generatedKeys = statement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setId(String.valueOf(generatedKeys.getInt(1))); // Assigner l'ID généré à l'utilisateur
                }
            }
        } catch (ClassNotFoundException e) {
            System.err.println("Driver non chargé !");
        } catch (SQLException e) {
            System.out.println("Erreur de connexion ou d'insertion dans la base de données.");
        }
        return line > 0;
    }
    


    @Override
    public List<User> selectAll() {
        List<User> users = new ArrayList<>();
        try {
            String query = "SELECT * FROM `user`";
            ResultSet rs= this.executeQuery(query);
            while (rs.next()) {
                String email = rs.getString("email");
                String login = rs.getString("login");
                int roleId = rs.getInt("role_id"); // Récupérer l'ID du rôle
                String password = rs.getString("password");
                
                User user = new User();
                user.setEmail(email);
                user.setLogin(login);
                user.setPassword(password);
                
                // Récupérer le rôle à partir de RoleRepositoryBd
                Role role = roleRepository.selectById(roleId); 
                user.setRole(role);
                users.add(user);
            }
            rs.close(); 
            statement.close(); 
            this.closeConnection();
        } catch (SQLException e) {
            System.out.println("Connection Error: " + e.getMessage()); 
        }
        return users;
    }
    

    @Override
    public User selectUserByLoginAndPassword(String login, String password) {
        User user = null;
        try { 
            String query = String.format("SELECT * FROM `user` WHERE `login` = '%s' AND `password` = '%s'", login, password);
            ResultSet rs= this.executeQuery(query);
            if (rs.next()) {
                user = new User();
                user.setLogin(rs.getString("login"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setActif(rs.getBoolean("actif")); 
    
                int role_id = rs.getInt("role_id");
                Role role = selectById(role_id); 
                user.setRole(role); 
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion ou de requête SQL.");
            e.printStackTrace();
        } finally {
           this.closeConnection();
        }
        return user; 
    }
    



    // Récupérer un rôle par ID
    public Role selectById(int id) {
        Role role = null;
        try {
            String query = String.format("SELECT * FROM `role` WHERE `id` = %d", id);
            ResultSet rs= this.executeQuery(query);
            if (rs.next()) {
                String nom_role = rs.getString("nom_role");
                role = new Role();
                role.setNom_role(nom_role);
            }
        } catch (SQLException e) {
            System.out.println("Erreur de connexion ou de requête SQL.");
            e.printStackTrace();
        } finally {
            this.closeConnection();
        }
        return role;
    }


    @Override
    public boolean updateStatus(String user_id, boolean actif) {
    int line = 0;
    try {
        String query = String.format("UPDATE `user` SET `actif` = '%d' WHERE `id` = '%s'", actif ? 1 : 0, user_id);
        line= this.executeUpdate(query);
    } catch (SQLException e) {
        System.out.println("Connection Error: " + e.getMessage());
    }
    return line != 0;
}

   
    
    


    
    
    

    
}

