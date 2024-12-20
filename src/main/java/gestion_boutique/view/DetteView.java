package gestion_boutique.view;

import java.util.List;

import gestion_boutique.entite.Dette;
import gestion_boutique.service.DetteService;

public class DetteView extends View{
    //Couplage Faible
    private DetteService detteService;

    public DetteView (DetteService detteService){
        this.detteService=detteService;
    }

    public Dette createDette(){
        Dette dette = new Dette();
        System.out.println("Entrer l'Id du client");
        dette.setClient_id(scanner.nextLine());
        System.out.println("Entrer la date");
        dette.setDate(scanner.nextLine());
        System.out.println("Entrer l'Id de l'article");
        dette.setArticle_id(scanner.nextLine());
        System.out.println("Entrer la quantité d'article");
        dette.setQuantiteArticle(scanner.nextLine()); 
        System.out.println("Entrer le montant");
        dette.setMontant(scanner.nextLine());
        System.out.println("Entrer le montant versé");
        dette.setMontantVerser(scanner.nextLine());
        System.out.println("Entrer le montant restant");
        dette.setMontantRestant(scanner.nextLine());
        return dette;
    }
    

    public void addDette(){
        Dette dette = createDette();
        //Utilisation du service
        detteService.create(dette);
    }

    public void listerDette(){
        //Utilisation du service
        detteService.getAll().stream()
        .forEach(System.out::println);

    }

    public void enregistrerPaiement() {
        System.out.print("ID de la dette : ");
        String dette_id = scanner.nextLine();
        System.out.print("Entrer le Montant du paiement : ");
        String montantVerser = scanner.nextLine();
    
        boolean updated = detteService.mettreAJourMontantVerser(dette_id, montantVerser);
        if (updated) {
            System.out.println("Montant du paiement mis à jour avec succès.");
        } else {
            System.out.println("Erreur lors de la mise à jour du montant versé.");
        }
    }

    // Méthode pour archiver les dettes soldées
    public void archiverDettesSoldees() {
        if (detteService.archiverDettesSoldees()) {
            System.out.println("Les dettes soldées ont été archivées avec succès.");
        } else {
            System.out.println("Aucune dette soldée à archiver.");
        }
    }

    public void listerDettesNonSoldees() {
        System.out.print("ID du client : ");
        String client_id = scanner.nextLine();
        List<Dette> dettes = detteService.findNonSoldeesByClientId(client_id);
        
        if (dettes.isEmpty()) {
            System.out.println("Aucune dette non soldée trouvée.");
        } else {
            System.out.println("Dettes non soldées :");
            for (Dette dette : dettes) {
                System.out.println("Montant : " + dette.getMontant() + ", Montant Restant : " + dette.getMontantRestant() + ", Date : " + dette.getDate());
                
                // Récupération et affichage des paiements associés
                List<String> paiements = detteService.getPaiementsByDetteId(dette.getId());
                if (paiements.isEmpty()) {
                    System.out.println("Aucun paiement associé trouvé pour cette dette.");
                } else {
                    System.out.println("Paiements associés :");
                    for (String paiement : paiements) {
                        System.out.println("- " + paiement);
                    }
                }
            }
        }
    }
    
    

}
