package gestion_boutique.view;

import java.util.List;
import java.util.Scanner;

import gestion_boutique.entite.Client;
import gestion_boutique.entite.Role;
import gestion_boutique.entite.User;
import gestion_boutique.service.ClientService;

public class ClientView extends View {
    private final ClientService clientService;
    public ClientView(ClientService clientService, UserView userView) {
        this.clientService = clientService;
    }

    public Client createClient() {
        Client client = new Client();
        System.out.println("Entrer le Nom");
        client.setNom(scanner.nextLine());
        System.out.println("Entrer le numéro de téléphone");
        client.setTelephone(scanner.nextLine());
        System.out.println("Entrer l'adresse");
        client.setAdresse(scanner.nextLine());
        return client;
    }

    public void addClient() {
        Scanner scanner = new Scanner(System.in);
        
        // Obtenez les informations sur le client
        System.out.print("Nom du client : ");
        String nom = scanner.nextLine();
        System.out.print("Téléphone du client : ");
        String telephone = scanner.nextLine();
        System.out.print("Adresse du client : ");
        String adresse = scanner.nextLine();
    
        Client client = new Client();
        client.setNom(nom);
        client.setTelephone(telephone);
        client.setAdresse(adresse);
        
        
        System.out.print("Voulez-vous créer un compte utilisateur pour ce client ? (oui/non) : ");
        String response = scanner.nextLine();
        
        if ("oui".equalsIgnoreCase(response)) {
           
            System.out.print("Login du client : ");
            String login = scanner.nextLine();
            System.out.print("Email du client : ");
            String email = scanner.nextLine();
            System.out.print("Mot de passe du client : ");
            String password = scanner.nextLine();
    
            User user = new User();
            user.setLogin(login);
            user.setEmail(email);
            user.setPassword(password);
            
           
            Role clientRole = new Role();
            clientRole.setRole_id(3); 
            clientRole.setNom_role("Client");
            user.setRole(clientRole);
    
            
            clientService.createWithUser(client, user);
        } else {
            clientService.create(client);
        }
        
        System.out.println("Client ajouté avec succès !");
    }
    


    public void listerClient() {
        List<Client> clients = clientService.getAllWithUsers();
        clients.forEach(client -> {
            System.out.println("Client : " + client.getNom() + ", Téléphone : " + client.getTelephone() + ", Adresse : " + client.getAdresse());
    
          
            if (client.getUser() != null) { 
                System.out.println("  - Utilisateur associé : " + client.getUser().getLogin() + " (" + client.getUser().getEmail() + ")");
            }
        });
    }
    
    


    public void rechercherClient() {
        System.out.print("Téléphone du client : ");
        String telephone = scanner.nextLine();
        Client client = clientService.rechercherClientParTelephone(telephone);
        if (client != null) {
            System.out.println("Client trouvé : \"" + client.getNom() + "\" \"" + client.getAdresse() + "\"");
        } else {
            System.out.println("Client non trouvé.");
        }
    }
}
