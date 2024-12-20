package gestion_boutique.view;

import java.util.List;

import gestion_boutique.entite.Demande_dette;
import gestion_boutique.service.DemandeDetteService;

public class DemandeDetteView extends View {
     //Couplage Faible
    private DemandeDetteService demandeDetteService;

    public DemandeDetteView (DemandeDetteService demandeDetteService){
        this.demandeDetteService=demandeDetteService;
    }

   public Demande_dette createDemandeDette(){
        Demande_dette demande_dette = new Demande_dette();
        System.out.println("Entrer le montant");
        demande_dette.setMontant(scanner.nextLine());
        System.out.println("Entrer l'Id du client");
        demande_dette.setClient_id(scanner.nextLine());
        System.out.println("Entrer l'Id de l'article");
        demande_dette.setArticle_id(scanner.nextLine());
        System.out.println("Entrer la date");
        demande_dette.setDate(scanner.nextLine());
        return demande_dette;
    }

    public void addDemandeDette(){
        Demande_dette demande_dette = createDemandeDette();
        //Utilisation du service
        boolean success = demandeDetteService.create(demande_dette);
        if (success) {
        System.out.println("Demande de dette ajoutée avec succès !");
        } else {
        System.out.println("Erreur lors de l'ajout de la demande de dette.");
        }
    }

    public void listDemandesByEtat() {
        System.out.println("Entrer l'état (En Cours / Annuler) : ");
        String etat = scanner.nextLine();
        List<Demande_dette> demandes = demandeDetteService.getByEtat(etat);
        
        if (demandes.isEmpty()) {
            System.out.println("Aucune demande de dette trouvée avec cet état.");
        } else {
            System.out.println("Demandes de dette : ");
            for (Demande_dette demande : demandes) {
                System.out.println(demande);
            }
        }
    }

  
}

