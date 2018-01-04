package ua.juja.sqlcmd.main;

import ua.juja.sqlcmd.controller.CommandController;
import ua.juja.sqlcmd.model.DbManager;


import java.util.Scanner;

public class SqlCmd {

    public static void main(String[] args) {
        DbManager dbManager = new DbManager();
        CommandController commandController = new CommandController(dbManager);
        Scanner scanner = new Scanner(System.in);
        String sentence = "";


        while (!sentence.equals("exit")) {
            sentence = scanner.nextLine();
            System.out.println(commandController.parse(sentence));
        }
    }
}
