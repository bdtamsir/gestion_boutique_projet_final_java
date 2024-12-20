package gestion_boutique.entite;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Client {
    private String Nom;
    private String Telephone;
    private String Adresse;
    private int user_id;
    private String id;
    private User user;

    public int getUserId() {
        return user_id; 
    }

    public void setUserId(int userId) {
        this.user_id = userId; 
    }

    public User getUser() { 
        return user; 
    }

    public void setUser(User user) { 
        this.user = user;
    }
}