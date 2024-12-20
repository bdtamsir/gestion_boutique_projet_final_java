package gestion_boutique.entite;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private String login;
    private String password;
    private String email;
    private Role role;
    private boolean actif;
    private int role_id;


    
    private String id; 

    public String getId() {
        return id; 
    }

    public int getRoleId() {
        return role_id; 
    }
    
}