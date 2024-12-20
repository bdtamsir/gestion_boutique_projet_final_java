package gestion_boutique.repository.list;


import java.util.List;

import gestion_boutique.core.repository.impl.RepositoryListImpl;
import gestion_boutique.entite.Demande_dette;
import gestion_boutique.repository.DemandeDetteRepository;

public class DemandeDetteRepositoryList extends RepositoryListImpl<Demande_dette> implements DemandeDetteRepository{

    @Override
    public List<Demande_dette> selectByEtat(String etat) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'selectByEtat'");
    }
     
}
