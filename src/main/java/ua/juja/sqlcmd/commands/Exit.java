package ua.juja.sqlcmd.commands;

import ua.juja.sqlcmd.model.DatabaseManager;
import ua.juja.sqlcmd.views.View;

public class Exit implements Command {
    private DatabaseManager databaseManager;
    private View view;

    private String helpText =  "exit - выход из программы\n"+
                               "--------------------------------------------------";
    public final String COMMAND_TEXT = "exit";

    public Exit(DatabaseManager databaseManager, View view){
        this.databaseManager = databaseManager;
        this.view = view;
    }

    @Override
    public boolean canExecute(String command) {
        return command.toLowerCase().startsWith(COMMAND_TEXT);
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
