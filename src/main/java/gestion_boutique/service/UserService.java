package gestion_boutique.service;

import gestion_boutique.core.service.Service;
import gestion_boutique.entite.User;

public interface UserService extends Service<User>{
    User getUserByLoginAndPassword(String login, String password);
    boolean changerStatutUtilisateur(String user_id, boolean actif);

}
