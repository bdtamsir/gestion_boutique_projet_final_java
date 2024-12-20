package gestion_boutique;

import java.util.Scanner;

import gestion_boutique.entite.User;
import gestion_boutique.repository.ArticleRepository;
import gestion_boutique.repository.ClientRepository;
import gestion_boutique.repository.DemandeDetteRepository;
import gestion_boutique.repository.DetteRepository;
import gestion_boutique.repository.UserRepository;
import gestion_boutique.repository.bd.ArticleRepositoryBd;
import gestion_boutique.repository.bd.ClientRepositoryBd;
import gestion_boutique.repository.bd.DemandeDetteRepositoryBd;
import gestion_boutique.repository.bd.DetteRepositoryBd;
import gestion_boutique.repository.bd.UserRepositoryBd;
import gestion_boutique.service.ArticleService;
import gestion_boutique.service.ClientService;
import gestion_boutique.service.DemandeDetteService;
import gestion_boutique.service.DetteService;
import gestion_boutique.service.UserService;
import gestion_boutique.service.impl.ArticleServiceImpl;
import gestion_boutique.service.impl.ClientServiceImpl;
import gestion_boutique.service.impl.DemandeDetteServiceImpl;
import gestion_boutique.service.impl.DetteServiceImpl;
import gestion_boutique.service.impl.UserServiceImpl;
import gestion_boutique.view.ArticleView;
import gestion_boutique.view.ClientView;
import gestion_boutique.view.DemandeDetteView;
import gestion_boutique.view.DetteView;
import gestion_boutique.view.UserView;
import gestion_boutique.view.View;

public class Main {
    private static Scanner scanner = new Scanner (System.in);
    public static void main(String[] args) {
        //Hidratation du Scanner
        View.setScanner(scanner);

       //Repository
        ClientRepository clientRepository = new ClientRepositoryBd();
        UserRepository userRepository = new UserRepositoryBd();
        DetteRepository detteRepository = new DetteRepositoryBd();
        ArticleRepository articleRepository = new ArticleRepositoryBd();
        DemandeDetteRepository demandeDetteRepository = new DemandeDetteRepositoryBd();

       //Service
        ClientService clientService = new ClientServiceImpl(clientRepository, userRepository);
        UserService userService =new UserServiceImpl(userRepository);
        DetteService detteService = new DetteServiceImpl(detteRepository);
        ArticleService articleService = new ArticleServiceImpl(articleRepository);
        DemandeDetteService demandeDetteService = new DemandeDetteServiceImpl(demandeDetteRepository);

       //View
       UserView userView = new UserView(userService); 
       ClientView clientView = new ClientView(clientService, userView); 
        DetteView detteView = new DetteView(detteService);
        ArticleView articleView = new ArticleView(articleService);
        DemandeDetteView demandeDetteView = new DemandeDetteView(demandeDetteService);

        //Application
        User user = null;

        do {
            user = userView.getLogin();  // Authentification
            if (user != null) {
                switch (user.getRole().getNom_role()) {
                    case "ADMIN" -> {
                        System.out.println("MENU ADMINISTRATEUR");
                        flowAdmin(userView, articleView, detteView);  
                    }
                    case "BOUTIQUIER" -> {
                        System.out.println("MENU BOUTIQUIER");
                        flowBoutiquier(clientView, detteView);  
                    }
                    case "CLIENT" -> {
                        System.out.println("MENU CLIENT"); 
                        flowClient(detteView, demandeDetteView);
                    }
                    default -> System.out.println("Rôle inconnu.");
                }
            }
        } while (user != null);
       
        
    }

    public static int menuBoutiquier(){
        System.out.println("1. Créer un client & User");
        System.out.println("2. Lister les clients & Users");
        System.out.println("3. Rechercher un client par téléphone");
        System.out.println("4. Créer une dette  ");
        System.out.println("5. Lister les dettes ");
        System.out.println("6. Lister les dettes non soldées d’un client ");
        System.out.println("7. Enregistrer un paiement");
        System.out.println("8. Quitter");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;
    }

    public static void flowBoutiquier(ClientView clientView, DetteView detteView){
        int choice;
        do {
            choice = menuBoutiquier();
            switch (choice) {
                case 1 -> {
                    clientView.addClient();
                }


                case 2 -> {
                    clientView.listerClient();
                }


                case 3 -> {
                    clientView.rechercherClient();
                }


                case 4 -> {
                    detteView.addDette();
                }


                case 5 -> {
                    detteView.listerDette();
                }

                case 6 -> {
                   detteView.listerDettesNonSoldees();
                }

                case 7 -> {
                    detteView.enregistrerPaiement();
                }


                case 8 -> {
                    System.out.println("Fin du Programme");
                }

                default -> {
                    System.out.println("Choix Invalide");
                }
                    
            
            }
        } while (choice!=8);
    }

    public static int menuAdmin(){
        System.out.println("1. Créer un compte utilisateur");
        System.out.println("2. Activer/Désactiver un compte utilisateur");
        System.out.println("3. Afficher les comptes utilisateurs");
        System.out.println("4. Créer un article");
        System.out.println("5. Lister les articles disponibles");
        System.out.println("6. Mettre à jour la quantité en stock d’un article");
        System.out.println("7. Archiver les dettes soldées");
        System.out.println("8. Quitter");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice; 
    }

    public static void flowAdmin(UserView userView, ArticleView articleView, DetteView detteView){
        int choice;
        do {
            choice = menuAdmin();
            switch (choice) {
                case 1 -> {
                    userView.addUser();
                }


                case 2 -> {
                   userView.changerStatutUtilisateur();
                }


                case 3 -> {
                    userView.listerUser();
                }


                case 4 -> {
                    articleView.addArticle();
                }


                case 5 -> {
                    articleView.listerArticlesDisponibles();
                }


                case 6 -> {
                    articleView.mettreAJourQuantiteStock();
                }


                case 7 -> {
                    detteView.archiverDettesSoldees();
                }

                default -> {
                    System.out.println("Fin du Programme");
                }
                    
            
            }
        } while (choice!=8);
    }


    public static int menuClient(){
        System.out.println("1. Lister ses dettes non soldées");
        System.out.println("2. Faire une demande de dette");
        System.out.println("3. Lister ses demandes de dette");
        System.out.println("5. Quitter");
        int choice = scanner.nextInt();
        scanner.nextLine();
        return choice;    
    }

    public static void flowClient(DetteView detteView, DemandeDetteView demandeDetteView){
        int choice;
        do {
            choice = menuClient();
            switch (choice) {
                case 1 -> {
                    detteView.listerDettesNonSoldees();
                }


                case 2 -> {
                   demandeDetteView.addDemandeDette();
                }


                case 3 -> {
                   demandeDetteView.listDemandesByEtat();
                }

                default -> {
                    System.out.println("Choix Invalide");
                }
                    
            
            }
        } while (choice!=5);
    }
}