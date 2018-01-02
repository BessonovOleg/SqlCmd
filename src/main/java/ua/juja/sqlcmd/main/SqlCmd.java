package ua.juja.sqlcmd.main;

import ua.juja.sqlcmd.dababase.DbManager;


import java.util.Scanner;

public class SqlCmd {

    public static void main(String[] args) {
        DbManager dbManager = new DbManager();
        Chat chat = new Chat(dbManager);//TODO
        Scanner scanner = new Scanner(System.in);
        String sentence = "";

        System.out.println(chat.getHelloMsg());

        while (!sentence.equals("exit")) {
            sentence = scanner.nextLine();
            System.out.println(chat.parse(sentence));
        }
    }
}
