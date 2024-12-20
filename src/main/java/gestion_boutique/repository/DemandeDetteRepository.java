package gestion_boutique.repository;

import java.util.List;

import gestion_boutique.core.repository.Repository;
import gestion_boutique.entite.Demande_dette;

public interface  DemandeDetteRepository extends Repository<Demande_dette> {
   List<Demande_dette> selectByEtat(String etat);
}
