package gestion_boutique.entite;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Dette {
    private String id;
    private String date;
    private String montant;
    private String montantVerser;
    private String montantRestant;
    private String client_id;
    private String article_id;
    private String quantiteArticle;

     // Ajoute le getter pour ID si tu utilises Lombok, sinon implémente-le manuellement
     public String getId() {
        return id;
    }

    // Assure-toi aussi d’avoir un setter pour `id` si besoin
    public void setId(String id) {
        this.id = id;
    }

    public void setArchive(boolean b) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
