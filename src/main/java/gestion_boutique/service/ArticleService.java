package gestion_boutique.service;

import java.util.List;

import gestion_boutique.core.service.Service;
import gestion_boutique.entite.Article;

public interface  ArticleService extends Service<Article> {
    boolean mettreAJourQuantiteStock(String articleId, String nouvelleQuantite, boolean disponible);
    List<Article> listerArticlesDisponibles();
}
