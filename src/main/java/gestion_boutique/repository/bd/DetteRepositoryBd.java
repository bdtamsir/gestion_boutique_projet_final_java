package gestion_boutique.repository.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date; 
import java.util.List;

import gestion_boutique.core.bd.DatabaseImpl;
import gestion_boutique.entite.Dette;
import gestion_boutique.repository.DetteRepository;

public class DetteRepositoryBd extends DatabaseImpl implements DetteRepository {
    @Override
    public boolean insert(Dette dette) {
        int line = 0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion_shop", "root", "root");
    
            // Insertion de la dette dans la table dette
            String insertDetteQuery = String.format(
                "INSERT INTO `dette` (`client_id`, `date`, `montant`, `montantVerser`, `montantRestant`, `article_id`, `quantiteArticle`) " +
                "VALUES ('%s', '%s', '%s', '%s', '%s', '%s', '%s')", 
                dette.getClient_id(), dette.getDate(), dette.getMontant(), dette.getMontantVerser(), 
                dette.getMontantRestant(), dette.getArticle_id(), dette.getQuantiteArticle()
            );
    
            Statement statement = conn.createStatement();
            line = statement.executeUpdate(insertDetteQuery);
    
            // Mettre à jour la quantité en stock de l'article si l'insertion de la dette est réussie
            if (line > 0) { 
                int quantiteArticle = Integer.parseInt(dette.getQuantiteArticle());
    
                // Mettre à jour la quantité de l'article dans la table article
                String updateArticleQuery = String.format(
                    "UPDATE `article` SET `quantiteStock` = `quantiteStock` - '%d' WHERE `id` = '%s'", 
                    quantiteArticle, dette.getArticle_id()
                );
    
                line = statement.executeUpdate(updateArticleQuery);
            }
    
        } catch (ClassNotFoundException e) {
            System.err.println("Driver non chargé !");
        } catch (SQLException e) {
            System.out.println("Erreur de connexion ou requête SQL : " + e.getMessage());
        }
    
        return line != 0;
    }
    

    @Override
    public List<Dette> selectAll() {
        List<Dette> dettes= new ArrayList<>();
        try {
            String query = "SELECT * FROM `dette`";
            ResultSet rs= this.executeQuery(query);
            while (rs.next()) {
               String id= rs.getString("id");
               String client_id= rs.getString("client_id");
               String date= rs.getString("date");
               String montant= rs.getString("montant");
               String montantVerser= rs.getString("montantVerser");
               String montantRestant= rs.getString("montantRestant");
               String article_id= rs.getString("article_id");
               String quantiteArticle= rs.getString("quantiteArticle");
               Dette dette= new Dette();
               dette.setId(id);
               dette.setClient_id(client_id);
               dette.setDate(date);
               dette.setMontant(montant);
               dette.setMontantVerser(montantVerser);
               dette.setMontantRestant(montantRestant);
               dette.setArticle_id(article_id);
               dette.setQuantiteArticle(quantiteArticle);
               dettes.add(dette);
            }
            } catch(SQLException e) {
            System.out.println("Connection Error");
            }finally{
                this.closeConnection();
            }
        return dettes;
    }

    @Override
    public List<Dette> findNonSoldeesByClientId(String client_id) {
    List<Dette> dettesNonSoldees = new ArrayList<>();
    try {
        String query = String.format("SELECT * FROM `dette` WHERE `client_id` = %s AND `montantRestant` > 0", client_id);
        ResultSet rs= this.executeQuery(query);
        while (rs.next()) {
            Dette dette = new Dette();
            dette.setClient_id(rs.getString("client_id"));
            dette.setDate(rs.getString("date"));
            dette.setMontant(rs.getString("montant"));
            dette.setMontantVerser(rs.getString("montantVerser"));
            dette.setMontantRestant(rs.getString("montantRestant"));
            dettesNonSoldees.add(dette);
        }
    } catch (SQLException e) {
        System.out.println("Connection Error: " + e.getMessage());
    }finally{
        this.closeConnection();
    }
    return dettesNonSoldees;
}




    @Override
    public boolean updateMontantVerser(String dette_id, String montantVerser) {
    int line = 0;
    String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date()); // Date actuelle

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion_shop", "root", "root");

        // Récupérer le montantVerser actuel
        String selectQuery = String.format("SELECT `montantVerser`, `montantRestant` FROM `dette` WHERE `id` = '%s'", dette_id);
        Statement selectStatement = conn.createStatement();
        ResultSet rs = selectStatement.executeQuery(selectQuery);
        
        double currentMontantVerser = 0;
        double montantRestant = 0;

        if (rs.next()) {
            currentMontantVerser = rs.getDouble("montantVerser");
            montantRestant = rs.getDouble("montantRestant");
        }

        // Calculer le nouveau montant versé total
        double newMontantVerser = currentMontantVerser + Double.parseDouble(montantVerser);
        
        // Mettre à jour la dette
        String updateQuery = String.format(
            "UPDATE `dette` SET `montantVerser` = '%.2f', `montantRestant` = '%.2f' WHERE `id` = '%s'",
            newMontantVerser, montantRestant - Double.parseDouble(montantVerser), dette_id
        );
        
        Statement updateStatement = conn.createStatement();
        line = updateStatement.executeUpdate(updateQuery);

        // Insertion du paiement
        String insertPaymentQuery = String.format(
            "INSERT INTO `paiement` (`dette_id`, `date`, `montant`) VALUES ('%s', '%s', '%s')",
            dette_id, date, montantVerser
        );
        line = updateStatement.executeUpdate(insertPaymentQuery);
        
    } catch (ClassNotFoundException e) {
        System.err.println("Driver non chargé !");
    } catch (SQLException e) {
        System.out.println("Connection Error: " + e.getMessage());
    }
    return line != 0;
}


@Override
public boolean archiverDettesSoldees() {
    String selectQuery = "SELECT client_id, montantVerser, montantRestant FROM dette WHERE montantRestant = 0"; // Sélection des dettes soldées
    String insertQuery = "INSERT INTO archive_dette (client_id, montantVerser, montantRestant) VALUES (?, ?, ?)"; // Requête d'insertion

    try {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion_shop", "root", "root");

        // Récupérer les dettes soldées
        Statement selectStatement = conn.createStatement();
        ResultSet rs = selectStatement.executeQuery(selectQuery);

        // Vérifiez s'il y a des dettes soldées à archiver
        if (!rs.next()) {
            System.out.println("Aucune dette soldée à archiver.");
            return false; // Aucune dette à archiver
        }

        // Archiver chaque dette soldée
        PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
        
        do {
            insertStatement.setString(1, rs.getString("client_id")); 
            
            // Convertir le montantVerser pour utiliser '.' au lieu de ','
            String montantVerser = rs.getString("montantVerser").replace(",", ".");
            insertStatement.setString(2, montantVerser); 
            
            // Convertir le montantRestant pour utiliser '.' au lieu de ','
            String montantRestant = rs.getString("montantRestant").replace(",", ".");
            insertStatement.setString(3, montantRestant); 

            insertStatement.executeUpdate();
        } while (rs.next());

        return true; 
    } catch (ClassNotFoundException | SQLException e) {
        System.out.println("Connection Error: " + e.getMessage());
    }
    return false; 
}


@Override
public List<String> getPaiementsByDetteId(String dette_id) {
    List<String> paiements = new ArrayList<>();
    try {
        String query = String.format("SELECT * FROM `paiement` WHERE `dette_id` = '%s'", dette_id); 
        ResultSet rs= this.executeQuery(query);
        while (rs.next()) {
            paiements.add(rs.getString("montant") + " - " + rs.getString("date"));
        }
    } catch (SQLException e) {
        System.out.println("Connection Error: " + e.getMessage());
    }finally{
        this.closeConnection();
    }
    return paiements;
}






}
