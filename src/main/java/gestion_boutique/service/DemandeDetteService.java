package gestion_boutique.service;

import java.util.List;

import gestion_boutique.core.service.Service;
import gestion_boutique.entite.Demande_dette;

public interface  DemandeDetteService extends Service<Demande_dette> {
   List<Demande_dette> getByEtat(String etat);
}
