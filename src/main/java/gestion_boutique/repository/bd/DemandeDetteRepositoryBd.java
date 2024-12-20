package gestion_boutique.repository.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import gestion_boutique.core.bd.DatabaseImpl;
import gestion_boutique.entite.Demande_dette;
import gestion_boutique.repository.DemandeDetteRepository;

public class DemandeDetteRepositoryBd extends DatabaseImpl  implements DemandeDetteRepository{
    
      @Override
    public boolean insert(Demande_dette demande_dette) {
        int line=0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn =null;
            conn = DriverManager.getConnection("jdbc:mysql://localhost:8889/gestion_shop","root","root");
            String query = String.format("INSERT INTO `demande_dette` (`montant`, `client_id`, `article_id`, `date`, `etat`) VALUES ('%s', '%s', '%s', '%s', 'En cours')", demande_dette.getMontant(), demande_dette.getClient_id(), demande_dette.getArticle_id(), demande_dette.getDate(), demande_dette.getEtat());
            Statement statement = conn.createStatement();
            line= statement.executeUpdate(query);
            } catch(ClassNotFoundException e) {
            System.err.println("Driver non chargé !");
            } catch(SQLException e) {
            System.out.println("Connection Error");
            } 
            return line != 0;
    }

    @Override
    public List<Demande_dette> selectAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectAll'");
    }

    @Override
    public List<Demande_dette> selectByEtat(String etat) {
        List<Demande_dette> demandes = new ArrayList<>();
        try {
            String query = String.format("SELECT * FROM demande_dette WHERE etat = '%s'", etat);
            ResultSet rs= this.executeQuery(query);
            while (rs.next()) {
                Demande_dette demande = new Demande_dette();
                demande.setId(rs.getString("id"));
                demande.setMontant(rs.getString("montant"));
                demande.setClient_id(rs.getString("client_id"));
                demande.setArticle_id(rs.getString("article_id"));
                demande.setDate(rs.getString("date"));
                demande.setEtat(rs.getString("etat"));
                demandes.add(demande);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des demandes : " + e.getMessage());
        }finally{
            this.closeConnection();
        }
        return demandes;
    }
}
