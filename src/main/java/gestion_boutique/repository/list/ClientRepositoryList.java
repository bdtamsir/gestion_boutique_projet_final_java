package gestion_boutique.repository.list;

import java.util.List;

import gestion_boutique.core.repository.impl.RepositoryListImpl;
import gestion_boutique.entite.Client;
import gestion_boutique.entite.User;
import gestion_boutique.repository.ClientRepository;

public class ClientRepositoryList extends RepositoryListImpl<Client> implements ClientRepository {

    @Override
    public Client findByTelephone(String telephone) {
        return data.stream()
                .filter(client -> client.getTelephone().equals(telephone))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Client> selectAllWithUsers() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean insert(Client client, User user) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}