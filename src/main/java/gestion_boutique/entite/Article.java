package gestion_boutique.entite;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Article {
    private String nom;
    private String description;
    private String prix;
    private String quantiteStock;
    private boolean disponible;

    public String getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public boolean isDisponible() {
        return disponible;
    }
}
