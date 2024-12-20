package gestion_boutique.view;

import gestion_boutique.entite.Article;
import gestion_boutique.service.ArticleService;

public class ArticleView extends View {
      //Couplage Faible
    private ArticleService articleService;

    public ArticleView (ArticleService articleService){
        this.articleService=articleService;
    }

    public Article createArticle() {
        Article article = new Article();
        System.out.println("Entrer le nom de l'article");
        article.setNom(scanner.nextLine());
        System.out.println("Entrer la description de l'article");
        article.setDescription(scanner.nextLine());
        System.out.println("Entrer le prix de l'article");
        article.setPrix(scanner.nextLine());
        System.out.println("Entrer la quantité en stock");
        article.setQuantiteStock(scanner.nextLine());
        System.out.println("L'article est-il disponible ? (true/false)");
        article.setDisponible(Boolean.parseBoolean(scanner.nextLine()));
        return article;
    }

    
    public void addArticle() {
        Article article = createArticle();
        // Utilisation du service
        articleService.create(article);
        System.out.println("Article ajouté avec succès !");
    }


    public void listerArticlesDisponibles() {
        // Utilisation du service
        articleService.listerArticlesDisponibles().stream()
            .forEach(System.out::println);
    }

     
      public void mettreAJourQuantiteStock() {
        System.out.print("Entrer l'ID de l'article à mettre à jour : ");
        String articleId = scanner.nextLine();
        
        System.out.print("Entrer la nouvelle quantité en stock : ");
        String nouvelleQuantite = scanner.nextLine();
        
        System.out.print("L'article est-il disponible (true/false) ? ");
        boolean disponible = Boolean.parseBoolean(scanner.nextLine());

        if (articleService.mettreAJourQuantiteStock(articleId, nouvelleQuantite, disponible)) {
            System.out.println("Quantité en stock mise à jour avec succès !");
        } else {
            System.out.println("Échec de la mise à jour. Vérifiez que l'article existe.");
        }
    }

}
