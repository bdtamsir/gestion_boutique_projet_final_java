package gestion_boutique.entite;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Role {

    private int role_id;
    private String nom_role;

     public Role(int role_id, String nom_role) {
        this.role_id = role_id;
        this.nom_role = nom_role;
    }

    public Role() {
    }

}
