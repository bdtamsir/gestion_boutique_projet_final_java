package gestion_boutique.service.impl;

import java.util.List;

import gestion_boutique.entite.Dette;
import gestion_boutique.repository.DetteRepository;
import gestion_boutique.service.DetteService;

public class DetteServiceImpl implements DetteService{
     //Couplage Faible
    private DetteRepository detteRepository;
    
    //Injection de Dependance
    public DetteServiceImpl(DetteRepository detteRepository){
        this.detteRepository=detteRepository;
    }

    @Override
    public boolean create(Dette dette){
        //Utilisation du Repository
        return detteRepository.insert(dette);
    }

    @Override
    public List<Dette> getAll(){
        //   //Utilisation du Repository
        return detteRepository.selectAll();
    }

    @Override
    public boolean mettreAJourMontantVerser(String dette_id, String montantVerser) {
    return detteRepository.updateMontantVerser(dette_id, montantVerser);
}

    @Override
    public List<Dette> findNonSoldeesByClientId(String client_id) {
        return detteRepository.findNonSoldeesByClientId(client_id);
}

@Override
public boolean archiverDettesSoldees() {
    return detteRepository.archiverDettesSoldees();
}


@Override
public List<String> getPaiementsByDetteId(String dette_id) {
    return detteRepository.getPaiementsByDetteId(dette_id);
}



}
