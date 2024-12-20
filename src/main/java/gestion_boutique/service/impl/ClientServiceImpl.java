package gestion_boutique.service.impl;

import java.util.List;

import gestion_boutique.entite.Client;
import gestion_boutique.entite.Role;
import gestion_boutique.entite.User;
import gestion_boutique.repository.ClientRepository;
import gestion_boutique.repository.UserRepository;
import gestion_boutique.service.ClientService;

public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;

    public ClientServiceImpl(ClientRepository clientRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
    }

    @Override
    public boolean create(Client client) {
        return clientRepository.insert(client);
    }

    @Override
    public boolean createWithUser(Client client, User user) {
        Role clientRole = new Role();
        clientRole.setRole_id(3); // Assigner l'ID correspondant au rôle Client
        clientRole.setNom_role("Client");
        user.setRole(clientRole);
    
        // Insérer l'utilisateur
        boolean userInserted = userRepository.insert(user);
        
        if (userInserted && user.getId() != null) {
            try {
                int userId = Integer.parseInt(user.getId());
                client.setUserId(userId);
    
                boolean clientInserted = clientRepository.insert(client);
                if (!clientInserted) {
                    System.out.println("Erreur lors de l'ajout du client.");
                    return false;
                }
            } catch (NumberFormatException e) {
                System.out.println("Erreur : L'ID de l'utilisateur est invalide ou nul.");
                return false;
            }
        } else {
            System.out.println("Erreur lors de l'ajout de l'utilisateur.");
            return false;
        }
    
        return true;
    }
    

    
    


    @Override
    public List<Client> getAll() {
        return clientRepository.selectAll();
    }

    @Override
    public List<Client> getAllWithUsers() {
    return clientRepository.selectAllWithUsers();
}

    @Override
    public Client rechercherClientParTelephone(String telephone) {
        return clientRepository.findByTelephone(telephone);
    }

    @Override
    public void createClientOnly(Client client) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createClientOnly'");
    }

    @Override
    public void insert(Client client) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

   
}