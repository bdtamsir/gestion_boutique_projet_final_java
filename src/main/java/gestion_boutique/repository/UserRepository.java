package gestion_boutique.repository;

import gestion_boutique.core.repository.Repository;
import gestion_boutique.entite.User;

public interface UserRepository extends Repository<User>{
    User selectUserByLoginAndPassword(String login, String password);
    boolean updateStatus(String user_id, boolean actif);

}
