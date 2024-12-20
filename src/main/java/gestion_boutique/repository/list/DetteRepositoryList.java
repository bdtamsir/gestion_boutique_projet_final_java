package gestion_boutique.repository.list;

import java.util.List;
import java.util.stream.Collectors;

import gestion_boutique.core.repository.impl.RepositoryListImpl;
import gestion_boutique.entite.Dette;
import gestion_boutique.repository.DetteRepository;

public class DetteRepositoryList extends RepositoryListImpl<Dette> implements DetteRepository {
    
    @Override
    public boolean updateMontantVerser(String dette_id, String montantVerser) {
        for (Dette dette : data) {
            if (dette.getId().equals(dette_id)) { 
                dette.setMontantVerser(montantVerser);
                return true;
            }
        }
        return false;
    }


    @Override
    public List<Dette> findNonSoldeesByClientId(String client_id) {
        return data.stream()
            .filter(dette -> dette.getClient_id().equals(client_id) && Double.parseDouble(dette.getMontantRestant()) > 0)
            .collect(Collectors.toList());
    }

    @Override
    public boolean archiverDettesSoldees() {
        for (Dette dette : data) {
            if (Double.parseDouble(dette.getMontantRestant()) == 0) {
                dette.setArchive(true);
            }
        }
        return true;
    }



    @Override
    public List<String> getPaiementsByDetteId(String dette_id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPaiementsByDetteId'");
    }
    

    
    
}
