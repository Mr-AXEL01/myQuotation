package net.axel.helpers;

import net.axel.utils.Validation;

import java.util.Scanner;

public class ProjectHelper {

    private final Scanner scanner;

    public ProjectHelper(Scanner scanner) {
        this.scanner = scanner;
    }

    public String promptForName() {
        String name;
        do {
            System.out.print("Enter project name: ");
            name = scanner.nextLine();
            if (!Validation.isNotEmpty(name)) {
                System.out.println("Name cannot be empty. Please enter a valid name.");
            }
        } while (!Validation.isNotEmpty(name));
        return name;
    }
}
