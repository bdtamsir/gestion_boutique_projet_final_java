package gestion_boutique.service;

import java.util.List;

import gestion_boutique.core.service.Service;
import gestion_boutique.entite.Dette;

public interface DetteService extends Service<Dette>{
    boolean mettreAJourMontantVerser(String dette_id, String montantVerser);
    List<Dette> findNonSoldeesByClientId(String client_id);
    boolean archiverDettesSoldees();
    List<String> getPaiementsByDetteId(String dette_id);

}
