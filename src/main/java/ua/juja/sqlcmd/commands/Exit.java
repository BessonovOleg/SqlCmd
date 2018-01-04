package ua.juja.sqlcmd.commands;

import ua.juja.sqlcmd.model.DatabaseManager;
import ua.juja.sqlcmd.views.View;

public class Exit implements Command {
    private DatabaseManager databaseManager;
    private View view;
    private String helpText = "\nExit - Выход из программы\n";

    public Exit(DatabaseManager databaseManager, View view){
        this.databaseManager = databaseManager;
        this.view = view;
    }

    @Override
    public boolean canExecute(String command) {
        return command.startsWith("Exit");
    }

    @Override
    public void execute(String command) {
        view.write("До свидания!");
        databaseManager.closeConnection();
        System.exit(0);
    }

    @Override
    public void printHelp() {
        view.write(helpText);
    }
}
