package gestion_boutique.repository;

import java.util.List;

import gestion_boutique.core.repository.Repository;
import gestion_boutique.entite.Dette;

public interface DetteRepository extends Repository<Dette>{
    boolean updateMontantVerser(String dette_id, String montantVerser);
    List<Dette> findNonSoldeesByClientId(String client_id);
    boolean archiverDettesSoldees();
    List<String> getPaiementsByDetteId(String dette_id);
}
