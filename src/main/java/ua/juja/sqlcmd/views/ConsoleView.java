package ua.juja.sqlcmd.views;

import java.util.Scanner;

public class ConsoleView implements View{
    @Override
    public String getInput() {
        Scanner scanner = new Scanner(System.in);
        String result = "";
        result = scanner.nextLine();
        return result;
    }

    @Override
    public void write(String text) {
        System.out.println(text);
    }
}
