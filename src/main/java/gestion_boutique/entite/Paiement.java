package gestion_boutique.entite;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Paiement {
    private String dette_id;
    private String date;
    private String montant;
}
