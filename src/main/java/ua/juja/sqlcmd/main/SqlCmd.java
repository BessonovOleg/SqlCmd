package ua.juja.sqlcmd.main;

import ua.juja.sqlcmd.controller.CommandController;
import ua.juja.sqlcmd.model.DatabaseManager;
import ua.juja.sqlcmd.model.PostgresDatabaseManager;
import ua.juja.sqlcmd.views.ConsoleView;
import ua.juja.sqlcmd.views.View;


public class SqlCmd {
    public static void main(String[] args) {
        DatabaseManager databaseManager = new PostgresDatabaseManager("127.0.0.1:5432");
        View view = new ConsoleView();
        view.write("Привет пользователь, введи команду или Help для получения списка команд");
        CommandController commandController = new CommandController(databaseManager,view);
        commandController.run();
    }
}
