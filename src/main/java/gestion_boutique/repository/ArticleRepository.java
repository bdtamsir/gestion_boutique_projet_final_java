package gestion_boutique.repository;

import java.util.List;

import gestion_boutique.core.repository.Repository;
import gestion_boutique.entite.Article;

public interface  ArticleRepository extends Repository<Article>{
    boolean updateQuantiteStock(String articleId, String nouvelleQuantite, boolean disponible);
    List<Article> selectAvailableArticles();
}
