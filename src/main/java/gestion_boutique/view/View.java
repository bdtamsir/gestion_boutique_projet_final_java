package gestion_boutique.view;

import java.util.Scanner;

import lombok.Setter;

public abstract class View {
    @Setter
    protected static Scanner scanner;

    public String askString(String message){
        String string;
        do {
            System.out.println(message);
            string= scanner.nextLine();
        } while (string.isEmpty());
        return string;
    }
}
