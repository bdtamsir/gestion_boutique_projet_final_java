package gestion_boutique.service.impl;

import java.util.List;

import gestion_boutique.entite.Demande_dette;
import gestion_boutique.repository.DemandeDetteRepository;
import gestion_boutique.service.DemandeDetteService;

public class DemandeDetteServiceImpl implements DemandeDetteService {
       //Couplage Faible
    private DemandeDetteRepository demandeDetteRepository;
    
    //Injection de Dependance
    public DemandeDetteServiceImpl(DemandeDetteRepository demandeDetteRepository){
        this.demandeDetteRepository=demandeDetteRepository;
    }

    @Override
    public boolean create(Demande_dette demande_dette){
        //Utilisation du Repository
        return demandeDetteRepository.insert(demande_dette);
    }

    @Override
    public List<Demande_dette> getAll(){
        //   //Utilisation du Repository
        return demandeDetteRepository.selectAll();
    }

    @Override
    public List<Demande_dette> getByEtat(String etat) {
        return demandeDetteRepository.selectByEtat(etat);
    }
}
