package gestion_boutique.service;

import java.util.List;

import gestion_boutique.core.service.Service;
import gestion_boutique.entite.Client;
import gestion_boutique.entite.User;

public interface  ClientService extends Service<Client>{
    Client rechercherClientParTelephone(String telephone);
     boolean createWithUser(Client client, User user);
    List<Client> getAllWithUsers();
    void createClientOnly(Client client);

    public void insert(Client client);

}


