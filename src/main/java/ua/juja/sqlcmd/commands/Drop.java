package ua.juja.sqlcmd.commands;

import ua.juja.sqlcmd.model.DatabaseManager;
import ua.juja.sqlcmd.utils.CommandChecker;
import ua.juja.sqlcmd.utils.CommandParser;
import ua.juja.sqlcmd.views.View;

public class Drop implements Command{

    private DatabaseManager databaseManager;
    private View view;

    public Drop(DatabaseManager databaseManager, View view) {
        this.databaseManager = databaseManager;
        this.view = view;
    }

    @Override
    public boolean canExecute(String command) {
        String COMMAND_TEXT = "drop";
        return CommandChecker.check(command,COMMAND_TEXT);
    }

    @Override
    public void execute(String command) {
        String tableName;
        try {
            String[] arrayCommand = CommandParser.getArray(command);
            tableName = arrayCommand[1];
        }catch (Exception ex){
            view.write("Ошибка формата команды");
            return;
        }

        view.write(databaseManager.drop(tableName));
    }

    @Override
    public void printHelp() {
        String helpText =  "drop\n"+
                "Команда удаляет заданную таблицу\n"+
                "Формат: drop|tableName\n"+
                "\tгде tableName - имя удаляемой таблицы\n"+
                "--------------------------------------------------";
        view.write(helpText);
    }
}
