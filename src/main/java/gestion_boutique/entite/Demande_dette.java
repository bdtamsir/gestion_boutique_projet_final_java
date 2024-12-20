package gestion_boutique.entite;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Demande_dette {
    private String id;
    private String montant;
    private String client_id;
    private String article_id;
    private String date;
    private String etat; // "En Cours", "Annuler", etc.
}
