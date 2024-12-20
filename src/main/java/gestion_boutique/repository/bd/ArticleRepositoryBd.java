package gestion_boutique.repository.bd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import gestion_boutique.core.bd.DatabaseImpl;
import gestion_boutique.entite.Article;
import gestion_boutique.repository.ArticleRepository;

public class ArticleRepositoryBd extends DatabaseImpl implements ArticleRepository {
    
    @Override
    public boolean insert(Article article) {
        int line = 0;
        try {
            String nom = article.getNom().replace("'", "''");
            String description = article.getDescription().replace("'", "''");
            String prix = article.getPrix().replace("'", "''");
            String quantiteStock = article.getQuantiteStock().replace("'", "''");
            String query = String.format(
                "INSERT INTO `article` (`nom`, `description`, `prix`, `quantiteStock`, `disponible`) VALUES ('%s', '%s', '%s', '%s', '%d')",
                nom, description, prix, quantiteStock, article.isDisponible() ? 1 : 0
            );
            line= this.executeUpdate(query);
        } catch (SQLException e) {
            System.out.println("Connection Error: " + e.getMessage());
        }
        return line != 0;
    }
    


@Override
public List<Article> selectAvailableArticles() {
    List<Article> articles = new ArrayList<>();
    try {
        String query = "SELECT * FROM `article` WHERE `quantiteStock` > 0";
        ResultSet rs= this.executeQuery(query);    
        while (rs.next()) {
            Article article = new Article();
            article.setNom(rs.getString("nom"));
            article.setDescription(rs.getString("description"));
            article.setPrix(rs.getString("prix"));
            article.setQuantiteStock(rs.getString("quantiteStock"));
            article.setDisponible(rs.getBoolean("disponible"));
            articles.add(article);
        }    
    } catch (SQLException e) {
        System.out.println("Connection Error: " + e.getMessage());
    }finally{
        this.closeConnection();
    }
    return articles;
}

    @Override
    public List<Article> selectAll() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   
    @Override
    public boolean updateQuantiteStock(String articleId, String nouvelleQuantite, boolean disponible) {
    int line = 0;
    try {
        String query = String.format(
            "UPDATE `article` SET `quantiteStock` = '%s', `disponible` = '%d' WHERE `id` = '%s'",
            nouvelleQuantite, disponible ? 1 : 0, articleId
        );
        line= this.executeUpdate(query);
    } catch (SQLException e) {
        System.out.println("Connection Error: " + e.getMessage());
    }
    return line != 0;
}



}
