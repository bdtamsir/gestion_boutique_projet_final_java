package gestion_boutique.repository.list;

import java.util.List;
import java.util.stream.Collectors;

import gestion_boutique.core.repository.impl.RepositoryListImpl;
import gestion_boutique.entite.Article;
import gestion_boutique.repository.ArticleRepository;

public class ArticleRepositoryList extends RepositoryListImpl<Article> implements ArticleRepository {

    @Override
    public List<Article> selectAvailableArticles() {
    return data.stream()
               .filter(article -> Integer.parseInt(article.getQuantiteStock()) > 0)
               .collect(Collectors.toList());
}

@Override
public boolean updateQuantiteStock(String articleId, String nouvelleQuantite, boolean disponible) {
    for (Article article : data) {
        if (article.getId().equals(articleId)) {
            article.setQuantiteStock(nouvelleQuantite);
            article.setDisponible(disponible);
            return true;
        }
    }
    return false;
}




}
