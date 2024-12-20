package gestion_boutique.repository.bd;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import gestion_boutique.core.bd.DatabaseImpl;
import gestion_boutique.entite.Client;
import gestion_boutique.entite.User;
import gestion_boutique.repository.ClientRepository;

public class ClientRepositoryBd extends DatabaseImpl implements ClientRepository{


    @Override
    public boolean insert(Client client) {
    int line = 0;
    try {
        // Requête pour insérer le client
        String query = String.format(
            "INSERT INTO `client` (`nom`, `telephone`, `adresse`, `user_id`) VALUES ('%s', '%s', '%s', %d)",
            client.getNom(), client.getTelephone(), client.getAdresse(), client.getUserId() 
        );
        line= this.executeUpdate(query);
    } catch (SQLException e) {
        System.out.println("Connection Error");
        e.printStackTrace();
    }
    return line != 0; 
}


    @Override
    public boolean insert(Client client, User user) {
        boolean userInserted = false;
        boolean clientInserted = false;

        
        String userInsertSQL = "INSERT INTO user (email, login, password, role_id, actif) VALUES ('" 
            + user.getEmail() + "', '" + user.getLogin() + "', '" + user.getPassword() + "', " 
            + user.getRoleId() + ", " + (user.isActif() ? 1 : 0) + ")";

        try {
            Statement stmt = connection.createStatement();
            userInserted = stmt.executeUpdate(userInsertSQL) > 0;

            if (userInserted) {
                // Obtenir l'ID généré pour l'utilisateur
                String getUserIdSQL = "SELECT LAST_INSERT_ID()"; 
                ResultSet rs = stmt.executeQuery(getUserIdSQL);
                if (rs.next()) {
                    int userId = rs.getInt(1); 
                    client.setUserId(userId); 

                    // Insérer le client
                    String clientInsertSQL = "INSERT INTO client (Nom, Telephone, Adresse, user_id) VALUES ('" 
                        + client.getNom() + "', '" + client.getTelephone() + "', '" + client.getAdresse() + "', " 
                        + client.getUserId() + ")";
                    
                    clientInserted = stmt.executeUpdate(clientInsertSQL) > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return userInserted && clientInserted; 
    }

    

    @Override
    public List<Client> selectAll() {
        List<Client> clients= new ArrayList<>();
        try {
            ResultSet rs= this.executeQuery(query);
            while (rs.next()) {
               String Nom= rs.getString("Nom");
               String Telephone= rs.getString("Telephone");
               String Adresse= rs.getString("Adresse");
               Client client= new Client();
               client.setNom(Nom);
               client.setTelephone(Telephone);
               client.setAdresse(Adresse);
               clients.add(client);
            }
            } catch(SQLException e) {
            System.out.println("Connection Error");
            } finally{
                this.closeConnection();
            }
        return clients;
    }

    @Override
    public List<Client> selectAllWithUsers() {
    List<Client> clients = new ArrayList<>();
    try {
        String query = "SELECT c.*, u.login, u.email FROM client c LEFT JOIN user u ON c.user_id = u.id";
        ResultSet rs= this.executeQuery(query);
        while (rs.next()) {
            Client client = new Client();
            client.setNom(rs.getString("nom"));
            client.setTelephone(rs.getString("telephone"));
            client.setAdresse(rs.getString("adresse"));
            client.setUserId(rs.getInt("user_id"));

            // Récupérer l'utilisateur associé
            User user = new User();
            user.setLogin(rs.getString("login"));
            user.setEmail(rs.getString("email"));

            client.setUser(user); 
            clients.add(client);
        }
    } catch (SQLException e) {
        System.out.println("Connection Error");
    }finally{
        this.closeConnection();
    }
    return clients; 
}


    @Override
    public Client findByTelephone(String telephone) {
        Client client = null;
        try {
            String query = String.format("SELECT * FROM `client` WHERE `Telephone` = '%s'", telephone);
            ResultSet rs= this.executeQuery(query);
            if (rs.next()) {
                String nom = rs.getString("Nom");
                String adresse = rs.getString("Adresse");
                client = new Client();
                client.setNom(nom);
                client.setAdresse(adresse);
            }
        } catch (SQLException e) {
            System.out.println("Connection Error");
        }finally{
            this.closeConnection();
        }
        return client;
    }


    
}