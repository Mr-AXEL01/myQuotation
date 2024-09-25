package net.axel;

import net.axel.config.DatabaseConnection;
import net.axel.presentations.ClientUi;
import net.axel.presentations.Menu;
import net.axel.presentations.ProjectUi;
import net.axel.repositories.implementations.ClientRepository;
import net.axel.repositories.implementations.LaborRepository;
import net.axel.repositories.implementations.MaterialRepository;
import net.axel.repositories.implementations.ProjectRepository;
import net.axel.services.implementations.ClientService;
import net.axel.services.implementations.LaborService;
import net.axel.services.implementations.MaterialService;
import net.axel.services.implementations.ProjectService;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        System.out.println("\n \t\t\t'##::::'##:'##:::'##::'#######::'##::::'##::'#######::'########::::'###::::'########:'####::'#######::'##::: ##:\n" +
                " \t\t\t ###::'###:. ##:'##::'##.... ##: ##:::: ##:'##.... ##:... ##..::::'## ##:::... ##..::. ##::'##.... ##: ###:: ##:\n" +
                " \t\t\t ####'####::. ####::: ##:::: ##: ##:::: ##: ##:::: ##:::: ##:::::'##:. ##::::: ##::::: ##:: ##:::: ##: ####: ##:\n" +
                " \t\t\t ## ### ##:::. ##:::: ##:::: ##: ##:::: ##: ##:::: ##:::: ##::::'##:::. ##:::: ##::::: ##:: ##:::: ##: ## ## ##:\n" +
                " \t\t\t ##. #: ##:::: ##:::: ##:'## ##: ##:::: ##: ##:::: ##:::: ##:::: #########:::: ##::::: ##:: ##:::: ##: ##. ####:\n" +
                " \t\t\t ##:.:: ##:::: ##:::: ##:.. ##:: ##:::: ##: ##:::: ##:::: ##:::: ##.... ##:::: ##::::: ##:: ##:::: ##: ##:. ###:\n" +
                " \t\t\t ##:::: ##:::: ##::::: ##### ##:. #######::. #######::::: ##:::: ##:::: ##:::: ##::::'####:. #######:: ##::. ##:\n" +
                "\t\t\t ..:::::..:::::..::::::.....:..:::.......::::.......::::::..:::::..:::::..:::::..:::::....:::.......:::..::::..::");


        Menu menu = new Menu();
        menu.showMainMenu();
    }

}