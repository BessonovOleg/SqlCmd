package ua.juja.sqlcmd.commands;

import ua.juja.sqlcmd.model.DatabaseManager;
import ua.juja.sqlcmd.utils.CommandChecker;
import ua.juja.sqlcmd.views.View;

public class Tables implements Command{

    private DatabaseManager databaseManager;
    private View view;

    public Tables(DatabaseManager databaseManager, View view) {
        this.databaseManager = databaseManager;
        this.view = view;
    }

    @Override
    public boolean canExecute(String command) {
        String COMMAND_TEXT = "tables";
        return CommandChecker.check(command,COMMAND_TEXT);
    }

    @Override
    public void execute(String command) {
        view.write(databaseManager.tables());
    }

    @Override
    public void printHelp() {
        String helpText =  "tables\n" +
                "Команда выводит список всех таблиц\n" +
                "Формат: tables (без параметров)\n" +
                "--------------------------------------------------";

        view.write(helpText);
    }
}
