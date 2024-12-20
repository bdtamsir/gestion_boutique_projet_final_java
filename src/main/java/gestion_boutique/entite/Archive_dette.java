package gestion_boutique.entite;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Archive_dette {
    private String client_id;
    private String montantVerser;
    private String montantRestant;

    public String getId() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
