package gestion_boutique.repository;

import java.util.List;

import gestion_boutique.core.repository.Repository;
import gestion_boutique.entite.Client;
import gestion_boutique.entite.User;

public interface ClientRepository extends Repository<Client> {
    Client findByTelephone(String telephone);
    @Override
    boolean insert(Client client); 
    boolean insert(Client client, User user); 
    @Override
    List<Client> selectAll();
    List<Client> selectAllWithUsers();
}