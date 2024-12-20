package gestion_boutique.service.impl;

import java.util.List;

import gestion_boutique.entite.User;
import gestion_boutique.repository.UserRepository;
import gestion_boutique.service.UserService;

public class UserServiceImpl implements UserService {
      //Couplage Faible
    private UserRepository userRepository;
    
    //Injection de Dependance
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Override
    public boolean create(User user){
        //Utilisation du Repository
        return userRepository.insert(user);
    }

    @Override
    public List<User> getAll(){
        //   //Utilisation du Repository
        return userRepository.selectAll();
    }

    @Override
    public User getUserByLoginAndPassword(String login, String password){
        return userRepository.selectUserByLoginAndPassword(login, password);
    }

    @Override
    public boolean changerStatutUtilisateur(String user_id, boolean actif) {
    return userRepository.updateStatus(user_id, actif);
}

}
