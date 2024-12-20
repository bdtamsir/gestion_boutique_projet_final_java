package gestion_boutique.service.impl;

import java.util.List;

import gestion_boutique.entite.Article;
import gestion_boutique.repository.ArticleRepository;
import gestion_boutique.service.ArticleService;

public class ArticleServiceImpl implements ArticleService {

     //Couplage Faible
    private ArticleRepository articleRepository;
    
    //Injection de Dependance
    public ArticleServiceImpl(ArticleRepository articleRepository){
        this.articleRepository=articleRepository;
    }

    @Override
    public List<Article> getAll() {
       
        return articleRepository.selectAll();
    }

  @Override
    public boolean create(Article article){
      
        return articleRepository.insert(article);
    }

    @Override
    public List<Article> listerArticlesDisponibles() {
    return articleRepository.selectAvailableArticles();
}

@Override
public boolean mettreAJourQuantiteStock(String articleId, String nouvelleQuantite, boolean disponible) {
    return articleRepository.updateQuantiteStock(articleId, nouvelleQuantite, disponible);
}




}
