package gestion_boutique.repository.list;


import java.util.ArrayList;
import java.util.List;

import gestion_boutique.core.repository.impl.RepositoryListImpl;
import gestion_boutique.entite.User;
import gestion_boutique.repository.UserRepository;

public class UserRepositoryList extends RepositoryListImpl <User> implements UserRepository {

    @Override
    public User selectUserByLoginAndPassword(String login, String password){
        return data.stream() 
        .filter (user -> user.getLogin().compareTo(login)== 0 && user.getPassword().compareTo(password)== 0)
        .findFirst()
        .orElse(null);
    }

    // Liste pour stocker les utilisateurs
    private final List<User> users = new ArrayList<>(); 

    @Override
    public boolean updateStatus(String user_id, boolean actif) {
    for (User user : users) {
        if (user.getId().equals(user_id)) {
            user.setActif(actif);
            return true;
        }
    }
    return false;
}



}
