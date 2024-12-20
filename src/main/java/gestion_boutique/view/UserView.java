package gestion_boutique.view;


import java.util.List;
import java.util.Scanner;

import gestion_boutique.entite.Role;
import gestion_boutique.entite.User;
import gestion_boutique.repository.bd.RoleRepositoryBd;
import gestion_boutique.repository.bd.UserRepositoryBd;
import gestion_boutique.service.UserService;

public class UserView extends View {
    //Couplage Faible
    private final UserService userService;

    public UserView (UserService userService){
        this.userService=userService;
    }

    public User createUser(){
        User user = new User();
        System.out.println("Entrer le Login");
        user.setLogin(scanner.nextLine());
        System.out.println("Entrer le mot de passe");
        user.setPassword(scanner.nextLine());
        System.out.println("Entrer l'email");
        user.setEmail(scanner.nextLine());
        System.out.println("Choisissez le Rôle");
        user.setRole(askRole()); 
        return user;
    }

    public User createUserForClient() {
    Scanner scanner = new Scanner(System.in);
    User user = new User();

    System.out.print("Entrez le login : ");
    user.setLogin(scanner.nextLine());

    System.out.print("Entrez le mot de passe : ");
    user.setPassword(scanner.nextLine());

    System.out.print("Entrez l'email : ");
    user.setEmail(scanner.nextLine());

    // Définir le rôle "Client" par défaut
    Role clientRole = new Role();
    clientRole.setRole_id(3); 
    user.setRole(clientRole);

    return user;
}

    

    public void addUser(){
        User user = createUser();
        //Utilisation du service
        userService.create(user);
    }

    public void listerUser(){
        //Utilisation du service
        userService.getAll().stream()
        .forEach(System.out::println);

    }
    
    private final UserRepositoryBd userRepo = new UserRepositoryBd();

    public User getLogin() {
        System.out.println("Veuillez entrer votre login :");
        String login = scanner.nextLine();  
        
        System.out.println("Veuillez entrer votre mot de passe :");
        String password = scanner.nextLine();  
    
        User user = userRepo.selectUserByLoginAndPassword(login, password); 
    
        // Vérification de l'existence de l'utilisateur
        if (user == null) {
            System.out.println("Identifiants incorrects. Veuillez réessayer.");
            return null; 
        }
    
        // Vérification si le compte est actif
        if (!user.isActif()) {
            System.out.println("Compte désactivé. Veuillez contacter l'administrateur.");
            return null;  
        }
    
        // Connexion réussie
        System.out.println("Connexion réussie !");
        return user;  
    }
    

   
    private final RoleRepositoryBd roleRepo = new RoleRepositoryBd();

    public Role askRole() {
        List<Role> roles = roleRepo.selectAll();
        int position;
        do {
            for (int i = 0; i < roles.size(); i++) {
                System.out.println((i + 1) + ". " + roles.get(i).getNom_role());
            }
            position = scanner.nextInt();
        } while (position < 1 || position > roles.size());

        return roles.get(position - 1); 
    }


    public void changerStatutUtilisateur() {
        System.out.print("Entrez l'ID de l'utilisateur : ");
        String userId = scanner.nextLine();
    
        System.out.print("Voulez-vous activer (1) ou désactiver (0) cet utilisateur ? ");
        boolean actif = scanner.nextLine().equals("1");
    
        if (userService.changerStatutUtilisateur(userId, actif)) {
            System.out.println("Statut de l'utilisateur mis à jour avec succès.");
        } else {
            System.out.println("Échec de la mise à jour du statut.");
        }
    }
    
}
